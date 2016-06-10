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
package nl.strohalm.cyclos.struts.access.policies;

import nl.strohalm.cyclos.access.AdminMemberPermission;
import nl.strohalm.cyclos.access.BrokerPermission;
import nl.strohalm.cyclos.access.MemberPermission;
import nl.strohalm.cyclos.access.OperatorPermission;
import nl.strohalm.cyclos.controls.invoices.SendInvoiceForm;
import nl.strohalm.cyclos.struts.access.ActionDescriptor;
import nl.strohalm.cyclos.struts.access.ActionPolicy;
import nl.strohalm.cyclos.struts.access.policies.utils.AbstractActionPolicy;

import org.apache.commons.lang.StringUtils;

public class SendInvoiceActionPolicy extends AbstractActionPolicy {
    private final static SendInvoiceActionPolicy INSTANCE = new SendInvoiceActionPolicy();

    public static ActionPolicy instance() {
        return INSTANCE;
    }

    private SendInvoiceActionPolicy() {

    }

    @Override
    protected boolean doCheck(final ActionDescriptor descriptor) {
        final SendInvoiceForm form = getForm();
        if (form.isToSystem()) {
            if (StringUtils.isEmpty(form.getFrom())) {
                return hasPermission(MemberPermission.INVOICES_SEND_TO_SYSTEM, OperatorPermission.INVOICES_SEND_TO_SYSTEM);
            } else {
                return hasPermission(BrokerPermission.INVOICES_SEND_AS_MEMBER_TO_SYSTEM, AdminMemberPermission.INVOICES_SEND_AS_MEMBER_TO_SYSTEM);
            }
        } else {
            if (StringUtils.isNotEmpty(form.getTo()) || form.isSelectMember()) {
                return hasPermission(AdminMemberPermission.INVOICES_SEND, MemberPermission.INVOICES_SEND_TO_MEMBER, OperatorPermission.INVOICES_SEND_TO_MEMBER);
            } else if (StringUtils.isNotEmpty(form.getFrom())) {
                return hasPermission(AdminMemberPermission.INVOICES_SEND_AS_MEMBER_TO_MEMBER, BrokerPermission.INVOICES_SEND_AS_MEMBER_TO_MEMBER);
            } else {
                return false;
            }
        }
    }
}
