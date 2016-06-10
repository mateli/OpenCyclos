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
package nl.strohalm.cyclos.controls.groups.permissions;

import java.util.Collections;
import java.util.Map;

import nl.strohalm.cyclos.controls.BaseBindingForm;

/**
 * Form used to edit a group's permissions
 * @author luis
 */
public class EditGroupPermissionsForm extends BaseBindingForm {
    private static final long serialVersionUID = -1732265279003894996L;
    private long              groupId;

    public EditGroupPermissionsForm() {
        setPermission("operations", Collections.emptyList());
        setPermission("managesGroups", Collections.emptyList());
        setPermission("viewInformationOf", Collections.emptyList());
        setPermission("viewConnectedAdminsOf", Collections.emptyList());
        setPermission("canViewProfileOfGroups", Collections.emptyList());
        setPermission("canViewInformationOf", Collections.emptyList());
        setPermission("brokerCanViewInformationOf", Collections.emptyList());
        setPermission("canViewAdsOfGroups", Collections.emptyList());
        setPermission("grantLoanTTs", Collections.emptyList());
        setPermission("systemChargebackTTs", Collections.emptyList());
        setPermission("memberChargebackTTs", Collections.emptyList());
        setPermission("chargebackTTs", Collections.emptyList());
        setPermission("asMemberToMemberTTs", Collections.emptyList());
        setPermission("asMemberToSelfTTs", Collections.emptyList());
        setPermission("asMemberToSystemTTs", Collections.emptyList());
        setPermission("systemToMemberTTs", Collections.emptyList());
        setPermission("systemToSystemTTs", Collections.emptyList());
        setPermission("systemToSystemTTs", Collections.emptyList());
        setPermission("memberToMemberTTs", Collections.emptyList());
        setPermission("memberToSystemTTs", Collections.emptyList());
        setPermission("selfPaymentTTs", Collections.emptyList());
        setPermission("externalPaymentTTs", Collections.emptyList());
        setPermission("conversionSimulationTTs", Collections.emptyList());
        setPermission("brokerConversionSimulationTTs", Collections.emptyList());
        setPermission("documents", Collections.emptyList());
        setPermission("brokerDocuments", Collections.emptyList());
        setPermission("messageCategories", Collections.emptyList());
        setPermission("brokerMemberRecordTypes", Collections.emptyList());
        setPermission("brokerCreateMemberRecordTypes", Collections.emptyList());
        setPermission("brokerModifyMemberRecordTypes", Collections.emptyList());
        setPermission("brokerDeleteMemberRecordTypes", Collections.emptyList());
        setPermission("viewAdminRecordTypes", Collections.emptyList());
        setPermission("createAdminRecordTypes", Collections.emptyList());
        setPermission("modifyAdminRecordTypes", Collections.emptyList());
        setPermission("deleteAdminRecordTypes", Collections.emptyList());
        setPermission("viewMemberRecordTypes", Collections.emptyList());
        setPermission("createMemberRecordTypes", Collections.emptyList());
        setPermission("modifyMemberRecordTypes", Collections.emptyList());
        setPermission("deleteMemberRecordTypes", Collections.emptyList());
        setPermission("requestPaymentByChannels", Collections.emptyList());
        setPermission("guaranteeTypes", Collections.emptyList());
        setPermission("canIssueCertificationToGroups", Collections.emptyList());
        setPermission("canBuyWithPaymentObligationsFromGroups", Collections.emptyList());
    }

    public long getGroupId() {
        return groupId;
    }

    public Map<String, Object> getPermission() {
        return values;
    }

    public Object getPermission(final String key) {
        return values.get(key);
    }

    public void setGroupId(final long groupId) {
        this.groupId = groupId;
    }

    public void setPermission(final Map<String, Object> map) {
        values = map;
    }

    public void setPermission(final String key, final Object value) {
        values.put(key, value);
    }
}
