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
import nl.strohalm.cyclos.controls.ads.MemberAdsForm;
import nl.strohalm.cyclos.struts.access.ActionDescriptor;
import nl.strohalm.cyclos.struts.access.ActionPolicy;
import nl.strohalm.cyclos.struts.access.policies.utils.AbstractActionPolicy;
import nl.strohalm.cyclos.utils.access.LoggedUser;

public class MemberAdsActionPolicy extends AbstractActionPolicy {
    private final static MemberAdsActionPolicy INSTANCE = new MemberAdsActionPolicy();

    public static ActionPolicy instance() {
        return INSTANCE;
    }

    private MemberAdsActionPolicy() {
    }

    @Override
    protected boolean doCheck(final ActionDescriptor descriptor) {
        final MemberAdsForm form = getForm();
        if (form.getMemberId() > 0) {
            if (LoggedUser.isBroker()) {
                return hasPermission(BrokerPermission.ADS_MANAGE);
            } else {
                return hasPermission(AdminMemberPermission.ADS_VIEW, MemberPermission.ADS_VIEW);
            }
        } else {
            return hasPermission(MemberPermission.ADS_PUBLISH, OperatorPermission.ADS_PUBLISH);
        }
    }
}
