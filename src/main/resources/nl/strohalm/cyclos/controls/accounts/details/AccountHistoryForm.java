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
package nl.strohalm.cyclos.controls.accounts.details;

import java.util.Collections;

import nl.strohalm.cyclos.controls.BaseQueryForm;
import nl.strohalm.cyclos.utils.binding.MapBean;

/**
 * Form used to retrieve the account details
 * @author luis
 */
public class AccountHistoryForm extends BaseQueryForm {
    private static final long serialVersionUID = -8125090272508894166L;
    private boolean           advanced;
    private long              memberId;
    private long              typeId;
    private boolean           singleAccount;

    public AccountHistoryForm() {
        setQuery("period", new MapBean("begin", "end"));
        setQuery("groups", Collections.emptyList());
        setQuery("groupFilters", Collections.emptyList());
        setQuery("customValues", new MapBean(true, "field", "value"));
    }

    public long getMemberId() {
        return memberId;
    }

    public long getTypeId() {
        return typeId;
    }

    public boolean isAdvanced() {
        return advanced;
    }

    public boolean isSingleAccount() {
        return singleAccount;
    }

    public void setAdvanced(final boolean advanced) {
        this.advanced = advanced;
    }

    public void setMemberId(final long memberId) {
        this.memberId = memberId;
    }

    public void setSingleAccount(final boolean singleAccount) {
        this.singleAccount = singleAccount;
    }

    public void setTypeId(final long typeId) {
        this.typeId = typeId;
    }
}
