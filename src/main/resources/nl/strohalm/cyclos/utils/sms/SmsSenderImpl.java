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
package nl.strohalm.cyclos.utils.sms;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.strohalm.cyclos.entities.access.Channel;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomField;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.entities.settings.MessageSettings;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsChangeListener;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsEvent;
import nl.strohalm.cyclos.services.access.ChannelServiceLocal;
import nl.strohalm.cyclos.services.alerts.ErrorLogServiceLocal;
import nl.strohalm.cyclos.services.customization.MemberCustomFieldServiceLocal;
import nl.strohalm.cyclos.services.elements.ElementServiceLocal;
import nl.strohalm.cyclos.services.settings.SettingsServiceLocal;
import nl.strohalm.cyclos.services.sms.SmsMailingServiceLocal;
import nl.strohalm.cyclos.utils.CustomFieldHelper;
import nl.strohalm.cyclos.utils.MessageProcessingHelper;
import nl.strohalm.cyclos.webservices.external.ExternalWebServiceHelper;
import nl.strohalm.cyclos.webservices.external.sms.SendSmsSenderParameters;
import nl.strohalm.cyclos.webservices.external.sms.SmsSenderWebService;
import nl.strohalm.cyclos.webservices.model.MemberVO;
import nl.strohalm.cyclos.webservices.utils.MemberHelper;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

/**
 * Send SMS using a Web Service, whose URL is set in the local settings
 * @author Jefferson Magno
 */
public class SmsSenderImpl implements SmsSender, LocalSettingsChangeListener, BeanFactoryAware {

    private static class VoidSender implements SmsSenderWebService {
        @Override
        public boolean send(final String cyclosId, final SendSmsSenderParameters params) {
            return false;
        }
    }

    private boolean                       initialized;
    private BeanFactory                   beanFactory;
    private SmsSenderWebService           smsWebService;
    private ElementServiceLocal           elementService;
    private ErrorLogServiceLocal          errorLogService;
    private MemberCustomFieldServiceLocal memberCustomFieldService;
    private MemberHelper                  memberHelper;
    private SettingsServiceLocal          settingsService;
    private ChannelServiceLocal           channelService;
    private SmsMailingServiceLocal        smsMailingService;
    private CustomFieldHelper             customFieldHelper;

    @Override
    public void onLocalSettingsUpdate(final LocalSettingsEvent event) {
        smsWebService = null;
    }

    @Override
    public boolean send(Member member, String text, final String traceData) {
        maybeInitialize();
        Channel smsChannel = channelService.getSmsChannel();
        if (smsChannel == null) {
            IllegalStateException e = new IllegalStateException("No channel was set on Local Settings as SMS channel");
            logError(member, text, e);
            return false;
        }

        MemberCustomField smsCustomField = settingsService.getSmsCustomField();
        if (smsCustomField == null) {
            IllegalStateException e = new IllegalStateException("No custom field was set on Local Settings as SMS field");
            logError(member, text, e);
            return false;
        }

        Set<MemberCustomField> requiredCustomFields = smsChannel.getPrincipalCustomFields();
        requiredCustomFields.add(smsCustomField);

        final LocalSettings localSettings = settingsService.getLocalSettings();
        final String cyclosId = localSettings.getCyclosId();
        member = elementService.load(member.getId(), Element.Relationships.GROUP);
        final List<MemberCustomField> memberCustomFields = memberCustomFieldService.list();
        List<MemberCustomField> customFields = customFieldHelper.onlyForGroup(memberCustomFields, member.getMemberGroup());
        customFields = customFieldHelper.onlyBasic(memberCustomFields);
        final MemberVO memberVO = memberHelper.toVO(member, customFields, requiredCustomFields, false);

        final MessageSettings messageSettings = settingsService.getMessageSettings();
        String prefix = messageSettings.getSmsMessagePrefix();
        final Map<String, String> variables = smsMailingService.getSmsTextVariables(null, member, false);

        prefix = StringUtils.trimToEmpty(MessageProcessingHelper.processVariables(prefix, variables));
        text = StringUtils.trimToEmpty(MessageProcessingHelper.processVariables(text, variables));
        text = StringUtils.trimToEmpty(prefix + " " + text);
        try {
            SendSmsSenderParameters params = new SendSmsSenderParameters();
            params.setMember(memberVO);
            params.setText(text);
            params.setTraceData(traceData);
            return getSmsWebService().send(cyclosId, params);
        } catch (final Exception e) {
            logError(member, text, e);
            return false;
        }
    }

    @Override
    public void setBeanFactory(final BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    public void setChannelServiceLocal(final ChannelServiceLocal channelService) {
        this.channelService = channelService;
    }

    public void setCustomFieldHelper(final CustomFieldHelper customFieldHelper) {
        this.customFieldHelper = customFieldHelper;
    }

    public void setSmsMailingServiceLocal(final SmsMailingServiceLocal smsMailingService) {
        this.smsMailingService = smsMailingService;
    }

    private SmsSenderWebService getSmsWebService() throws IOException {
        if (smsWebService == null) {
            final String url = settingsService.getLocalSettings().getSendSmsWebServiceUrl();
            if (StringUtils.isEmpty(url)) {
                smsWebService = new VoidSender();
            } else {
                // Create the proxy
                smsWebService = ExternalWebServiceHelper.proxyFor(SmsSenderWebService.class, url);
            }
        }
        return smsWebService;
    }

    private void logError(final Member member, final String sms, final Exception e) {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("username", member.getUsername());
        params.put("name", member.getName());
        params.put("sms", sms);
        errorLogService.insert(e, settingsService.getLocalSettings().getSendSmsWebServiceUrl(), params);
    }

    private synchronized void maybeInitialize() {
        // As the standard setter injection was causing problems with other beans, for recursive injection,
        // which caused unproxied instances to be injected, the setter injection is no longer used here
        if (!initialized) {
            settingsService = beanFactory.getBean(SettingsServiceLocal.class);
            settingsService.addListener(this);
            memberCustomFieldService = beanFactory.getBean(MemberCustomFieldServiceLocal.class);
            elementService = beanFactory.getBean(ElementServiceLocal.class);
            memberHelper = beanFactory.getBean(MemberHelper.class);
            errorLogService = beanFactory.getBean(ErrorLogServiceLocal.class);

            initialized = true;
        }
    }

}
