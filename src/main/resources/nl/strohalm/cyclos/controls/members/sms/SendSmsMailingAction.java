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
package nl.strohalm.cyclos.controls.members.sms;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.access.AdminMemberPermission;
import nl.strohalm.cyclos.access.BrokerPermission;
import nl.strohalm.cyclos.access.Permission;
import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.entities.groups.AdminGroup;
import nl.strohalm.cyclos.entities.groups.GroupQuery;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.sms.SmsMailing;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.sms.SmsMailingService;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.binding.SimpleCollectionBinder;

/**
 * Action used to send an SMS mailing
 * @author luis
 */
public class SendSmsMailingAction extends BaseFormAction {

    private DataBinder<SmsMailing> dataBinder;
    private SmsMailingService      smsMailingService;

    @Inject
    public void setSmsMailingService(final SmsMailingService smsMailingService) {
        this.smsMailingService = smsMailingService;
    }

    @Override
    protected void formAction(final ActionContext context) throws Exception {
        final SendSmsMailingForm form = context.getForm();
        final SmsMailing smsMailing = getDataBinder().readFromString(form.getSmsMailing());
        Permission permission;
        if (context.isAdmin()) {
            permission = smsMailing.isFree() ? AdminMemberPermission.SMS_MAILINGS_FREE_SMS_MAILINGS : AdminMemberPermission.SMS_MAILINGS_PAID_SMS_MAILINGS;
        } else {
            permission = smsMailing.isFree() ? BrokerPermission.SMS_MAILINGS_FREE_SMS_MAILINGS : BrokerPermission.SMS_MAILINGS_PAID_SMS_MAILINGS;
        }

        if (!permissionService.hasPermission(permission)) {
            throw new PermissionDeniedException();
        }

        smsMailingService.send(smsMailing);

        context.sendMessage("smsMailing.sent");
    }

    @Override
    protected void prepareForm(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();
        final SendSmsMailingForm form = context.getForm();

        boolean canSendFree;
        boolean canSendPaid;
        if (context.isAdmin()) {
            canSendFree = permissionService.hasPermission(AdminMemberPermission.SMS_MAILINGS_FREE_SMS_MAILINGS);
            canSendPaid = permissionService.hasPermission(AdminMemberPermission.SMS_MAILINGS_PAID_SMS_MAILINGS);

            final GroupQuery query = new GroupQuery();
            query.setManagedBy((AdminGroup) context.getGroup());
            query.setOnlyActive(true);
            request.setAttribute("groups", groupService.search(query));
        } else {
            canSendFree = permissionService.hasPermission(BrokerPermission.SMS_MAILINGS_FREE_SMS_MAILINGS);
            canSendPaid = permissionService.hasPermission(BrokerPermission.SMS_MAILINGS_PAID_SMS_MAILINGS);
        }

        request.setAttribute("canSendFree", canSendFree);
        request.setAttribute("canSendPaid", canSendPaid);
        if (canSendFree && canSendPaid) {
            form.setSmsMailing("free", "true");
        }
    }

    @Override
    protected void validateForm(final ActionContext context) {
        final SendSmsMailingForm form = context.getForm();
        final SmsMailing smsMailing = getDataBinder().readFromString(form.getSmsMailing());
        smsMailingService.validate(smsMailing, form.isSingleMember());
    }

    private DataBinder<SmsMailing> getDataBinder() {
        if (dataBinder == null) {
            final BeanBinder<SmsMailing> binder = BeanBinder.instance(SmsMailing.class);
            binder.registerBinder("free", PropertyBinder.instance(Boolean.TYPE, "free"));
            binder.registerBinder("text", PropertyBinder.instance(String.class, "text"));
            binder.registerBinder("member", PropertyBinder.instance(Member.class, "member"));
            binder.registerBinder("groups", SimpleCollectionBinder.instance(MemberGroup.class, "groups"));
            dataBinder = binder;
        }
        return dataBinder;
    }

}
