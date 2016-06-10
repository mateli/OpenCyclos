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
import nl.strohalm.cyclos.controls.members.references.MemberReferencesForm;
import nl.strohalm.cyclos.entities.members.Reference;
import nl.strohalm.cyclos.entities.members.Reference.Nature;
import nl.strohalm.cyclos.struts.access.ActionDescriptor;
import nl.strohalm.cyclos.struts.access.ActionPolicy;
import nl.strohalm.cyclos.struts.access.policies.utils.AbstractActionPolicy;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.utils.conversion.CoercionHelper;

public class MemberReferencesActionPolicy extends AbstractActionPolicy {
    private final static MemberReferencesActionPolicy INSTANCE = new MemberReferencesActionPolicy();

    public static ActionPolicy instance() {
        return INSTANCE;
    }

    private MemberReferencesActionPolicy() {
    }

    @Override
    protected boolean doCheck(final ActionDescriptor descriptor) {
        final MemberReferencesForm form = getForm();
        Reference.Nature nature = CoercionHelper.coerce(Nature.class, form.getNature());
        nature = nature == null ? Nature.GENERAL : nature;

        if (form.getMemberId() > 0) { // other member's references
            if (Reference.Nature.GENERAL == nature) {
                // we should improve this in case of broker ensuring the memberId is brokered by him!
                return hasPermission(AdminMemberPermission.REFERENCES_VIEW, MemberPermission.REFERENCES_VIEW, BrokerPermission.REFERENCES_MANAGE, OperatorPermission.REFERENCES_VIEW);
            } else if (Reference.Nature.TRANSACTION == nature) {
                return LoggedUser.isMember() || LoggedUser.isOperator() || hasPermission(AdminMemberPermission.TRANSACTION_FEEDBACKS_VIEW);
            } else { // unknown nature
                return false;
            }
        } else {
            if (LoggedUser.isMember()) {
                return true;
            } else if (LoggedUser.isOperator()) {
                return hasPermission(Reference.Nature.GENERAL == nature ? OperatorPermission.REFERENCES_MANAGE_MEMBER_REFERENCES : OperatorPermission.REFERENCES_MANAGE_MEMBER_TRANSACTION_FEEDBACKS);
            } else {
                return false;
            }
        }
    }
}
