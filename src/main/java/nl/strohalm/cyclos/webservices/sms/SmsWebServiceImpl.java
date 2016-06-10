/*
    This file is part of Cyclos (www.cyclos.org).
    A project of the Social Trade Organisation (www.socialtrade.org).

    Cyclos is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    Cyclos is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Cyclos; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA

 */
package nl.strohalm.cyclos.webservices.sms;

import javax.jws.WebService;

import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.sms.SmsLog;
import nl.strohalm.cyclos.entities.sms.SmsLog.ErrorType;
import nl.strohalm.cyclos.entities.sms.SmsType;
import nl.strohalm.cyclos.services.elements.MessageServiceLocal;
import nl.strohalm.cyclos.services.elements.SendSmsDTO;
import nl.strohalm.cyclos.services.infotexts.InfoTextServiceLocal;
import nl.strohalm.cyclos.services.sms.SmsLogServiceLocal;
import nl.strohalm.cyclos.webservices.WebServiceContext;
import nl.strohalm.cyclos.webservices.utils.MemberHelper;
import nl.strohalm.cyclos.webservices.utils.WebServiceHelper;

import org.apache.commons.lang.StringUtils;

/**
 * Implementation for {@link SmsWebService}
 * 
 * @author luis
 */
@WebService(name = "sms", serviceName = "sms")
public class SmsWebServiceImpl implements SmsWebService {

    private MessageServiceLocal  messageServiceLocal;
    private MemberHelper         memberHelper;
    private WebServiceHelper     webServiceHelper;
    private InfoTextServiceLocal infoTextServiceLocal;
    private SmsLogServiceLocal   smsLogServiceLocal;

    @Override
    public SendSmsResult sendSms(final SendSmsParameters params) {
        if (params == null || (StringUtils.isEmpty(params.getText())) && !params.getInfoText()) {
            throw new IllegalArgumentException();
        }
        final Member restrictedMember = WebServiceContext.getMember();
        SendSmsStatus status = null;

        // Get the target member
        Member target = null;
        try {
            target = memberHelper.loadByPrincipal(params.getTargetPrincipalType(), params.getTargetPrincipal());
            // Ensure the target is found and not the restricted member, if any
            if (target == null || target.equals(restrictedMember)) {
                throw new Exception();
            }
            // Ensure the target participates on this channel
            if (!memberHelper.isChannelEnabledForMember(target)) {
                status = SendSmsStatus.CHANNEL_DISABLED_FOR_TARGET;
            }
        } catch (final Exception e) {
            webServiceHelper.error(e);
            status = SendSmsStatus.TARGET_NOT_FOUND;
        }

        // Get the member to be charged
        Member charged = null;
        if (status == null) {
            if (restrictedMember != null) {
                charged = restrictedMember;
            } else {
                try {
                    charged = memberHelper.loadByPrincipal(params.getToChargePrincipalType(), params.getToChargePrincipal());
                    if (charged == null) {
                        charged = target;
                    } else {
                        if (!memberHelper.isChannelEnabledForMember(charged)) {
                            status = SendSmsStatus.CHANNEL_DISABLED_FOR_CHARGED;
                        }
                    }
                } catch (final Exception e) {
                    webServiceHelper.error(e);
                    status = SendSmsStatus.CHARGED_NOT_FOUND;
                }
            }
        }

        String textToSend = params.getText();
        if (status == null && params.getInfoText()) {
            textToSend = infoTextServiceLocal.getInfoTextSubject(params.getText());
            if (textToSend == null) {
                webServiceHelper.trace("Info text's subject null for alias '" + params.getText() + "'");
                status = SendSmsStatus.INFO_TEXT_NOT_FOUND;
            }
        }
        // Send the message
        final SendSmsResult result = new SendSmsResult();
        if (status == null) {
            final SendSmsDTO send = new SendSmsDTO();
            send.setTargetMember(target);
            send.setChargedMember(charged);
            send.setText(textToSend);
            send.setTraceData(params.getTraceData());
            send.setSmsTypeArgs(params.getSmsTypeArgs());
            try {
                send.setSmsType(smsLogServiceLocal.loadSmsTypeByCode(params.getSmsTypeCode()));
                final SmsLog log = messageServiceLocal.sendSms(send);
                if (log == null) {
                    throw new IllegalStateException("No SMS log returned from MessageServiceLocal.sendSms()");
                }
                final ErrorType errorType = log.getErrorType();
                result.setSmsId(log.getId());

                if (errorType != null) {
                    if (errorType == ErrorType.SEND_ERROR) {
                        status = SendSmsStatus.SEND_ERROR;
                    } else {
                        status = SendSmsStatus.CHARGE_COULD_NOT_BE_DONE;
                    }
                } else {
                    status = SendSmsStatus.SUCCESS;
                }
            } catch (final EntityNotFoundException e) {
                webServiceHelper.error(e);
                if (e.getEntityType().equals(SmsType.class)) {
                    status = SendSmsStatus.INVALID_SMS_TYPE_CODE;
                } else {
                    status = SendSmsStatus.INTERNAL_ERROR;
                }
            } catch (final Exception e) {
                webServiceHelper.error(e);
                status = SendSmsStatus.INTERNAL_ERROR;
            }
        }
        result.setStatus(status);
        return result;
    }

    public void setInfoTextServiceLocal(final InfoTextServiceLocal infoTextService) {
        infoTextServiceLocal = infoTextService;
    }

    public void setMemberHelper(final MemberHelper memberHelper) {
        this.memberHelper = memberHelper;
    }

    public void setMessageServiceLocal(final MessageServiceLocal messageService) {
        messageServiceLocal = messageService;
    }

    public void setSmsLogServiceLocal(final SmsLogServiceLocal smsLogService) {
        smsLogServiceLocal = smsLogService;
    }

    public void setWebServiceHelper(final WebServiceHelper webServiceHelper) {
        this.webServiceHelper = webServiceHelper;
    }

}
