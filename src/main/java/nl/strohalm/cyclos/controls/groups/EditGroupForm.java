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
package nl.strohalm.cyclos.controls.groups;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import nl.strohalm.cyclos.controls.BaseBindingForm;
import nl.strohalm.cyclos.utils.binding.MapBean;

/**
 * Form used to edit a group
 * @author luis
 * @author Jefferson Magno
 */
public class EditGroupForm extends BaseBindingForm {
    private static final long serialVersionUID = 6979009527066941L;
    private long              groupId;
    private long              baseGroupId;
    private boolean           forceAccept;
    private boolean           useCustomSMSContextClass;

    public EditGroupForm() {
        // Since we don't know now what type of group will it be, prepare each group settings

        final MapBean basicSettings = new MapBean("maxPasswordWrongTries", "passwordPolicy", "transactionPassword", "transactionPasswordLength", "maxTransactionPasswordWrongTries");
        basicSettings.set("passwordLength", new MapBean("min", "max"));
        basicSettings.set("deactivationAfterMaxPasswordTries", new MapBean("field", "number"));
        basicSettings.set("passwordExpiresAfter", new MapBean("field", "number"));
        setGroup("basicSettings", basicSettings);

        final MapBean memberSettings = new MapBean("maxAdsPerMember", "maxAdImagesPerMember", "maxImagesPerMember", "sendPasswordByEmail", "viewLoansByGroup", "repayLoanByGroup", "groupAfterExpiration");
        memberSettings.set("emailValidation", Collections.emptySet());
        memberSettings.setType("emailValidation", Set.class);
        memberSettings.set("pinLength", new MapBean("min", "max"));
        memberSettings.set("defaultAdPublicationTime", new MapBean("field", "number"));
        memberSettings.set("maxAdPublicationTime", new MapBean("field", "number"));
        memberSettings.set("maxSchedulingPeriod", new MapBean("field", "number"));
        memberSettings.set("expireMembersAfter", new MapBean("field", "number"));
        memberSettings.set("pinBlockTimeAfterMaxTries", new MapBean("field", "number"));
        memberSettings.set("smsAdditionalChargedPeriod", new MapBean("field", "number"));
        setGroup("memberSettings", memberSettings);

        final MapBean brokerSettings = new MapBean("initialGroupForRegisteredMembers");
        setGroup("brokerSettings", brokerSettings);

        setGroup("transferTypeIds", Collections.emptyList());
        setGroup("maxAmountPerDayByTT", Collections.emptyList());
        setGroup("defaultMailMessages", Collections.emptyList());
        setGroup("smsMessages", Collections.emptyList());
        setGroup("defaultSmsMessages", Collections.emptyList());
        setGroup("channels", Collections.emptyList());
        setGroup("defaultChannels", Collections.emptyList());
        setGroup("possibleInitialGroups", Collections.emptyList());
    }

    public long getBaseGroupId() {
        return baseGroupId;
    }

    public Map<String, Object> getGroup() {
        return values;
    }

    public Object getGroup(final String key) {
        return values.get(key);
    }

    public long getGroupId() {
        return groupId;
    }

    public boolean isForceAccept() {
        return forceAccept;
    }

    public boolean isUseCustomSMSContextClass() {
        return useCustomSMSContextClass;
    }

    public void setBaseGroupId(final long baseGroupId) {
        this.baseGroupId = baseGroupId;
    }

    public void setForceAccept(final boolean forceAccept) {
        this.forceAccept = forceAccept;
    }

    public void setGroup(final Map<String, Object> group) {
        values = group;
    }

    public void setGroup(final String key, final Object value) {
        values.put(key, value);
    }

    public void setGroupId(final long groupId) {
        this.groupId = groupId;
    }

    public void setUseCustomSMSContextClass(final boolean useCustomSMSContextClass) {
        this.useCustomSMSContextClass = useCustomSMSContextClass;
    }
}
