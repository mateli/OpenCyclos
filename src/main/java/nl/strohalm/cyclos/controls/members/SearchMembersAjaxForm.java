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
package nl.strohalm.cyclos.controls.members;

import org.apache.struts.action.ActionForm;

/**
 * Form used for Ajax member search
 * @author luis
 */
public class SearchMembersAjaxForm extends ActionForm {

    private static final long serialVersionUID = 9179278424615452918L;
    private boolean           brokered;
    private boolean           brokers;
    private boolean           enabled;
    private boolean           maxScheduledPayments;
    private String            name;
    private String            username;
    private Long              exclude;
    private Long[]            groupIds;
    private Long              viewableGroup;

    public SearchMembersAjaxForm() {
        groupIds = new Long[0];
    }

    public Long getExclude() {
        return exclude;
    }

    public Long[] getGroupIds() {
        return groupIds;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public Long getViewableGroup() {
        return viewableGroup;
    }

    public boolean isBrokered() {
        return brokered;
    }

    public boolean isBrokers() {
        return brokers;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public boolean isMaxScheduledPayments() {
        return maxScheduledPayments;
    }

    public void setBrokered(final boolean brokered) {
        this.brokered = brokered;
    }

    public void setBrokers(final boolean brokers) {
        this.brokers = brokers;
    }

    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }

    public void setExclude(final Long exclude) {
        this.exclude = exclude;
    }

    public void setGroupIds(final Long[] groupIds) {
        this.groupIds = groupIds;
    }

    public void setMaxScheduledPayments(final boolean maxScheduledPayments) {
        this.maxScheduledPayments = maxScheduledPayments;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public void setViewableGroup(final Long viewableGroup) {
        this.viewableGroup = viewableGroup;
    }

}
