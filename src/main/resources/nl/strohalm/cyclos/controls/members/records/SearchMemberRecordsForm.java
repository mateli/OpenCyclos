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
package nl.strohalm.cyclos.controls.members.records;

import nl.strohalm.cyclos.controls.BaseQueryForm;
import nl.strohalm.cyclos.utils.binding.MapBean;

/**
 * Form used to search member records
 * @author Jefferson Magno
 */
public class SearchMemberRecordsForm extends BaseQueryForm {

    private static final long serialVersionUID = 8721839908296581427L;
    private boolean           global;
    private long              queryElementId;

    public SearchMemberRecordsForm() {
        setQuery("period", new MapBean(false, "begin", "end"));
        setQuery("customValues", new MapBean(true, "field", "value"));
    }

    public long getAdminId() {
        return getElementId();
    }

    public long getElementId() {
        try {
            return (Long) getQuery("element");
        } catch (final Exception e) {
            return 0L;
        }
    }

    public long getMemberId() {
        return getElementId();
    }

    public long getQueryElementId() {
        return queryElementId;
    }

    public long getTypeId() {
        try {
            return (Long) getQuery("type");
        } catch (final Exception e) {
            return 0L;
        }
    }

    public boolean isGlobal() {
        return global;
    }

    public void setAdminId(final long adminId) {
        setElementId(adminId);
    }

    public void setElementId(final long elementId) {
        setQuery("element", elementId);
    }

    public void setGlobal(final boolean global) {
        this.global = global;
    }

    public void setMemberId(final long memberId) {
        setElementId(memberId);
    }

    public void setQueryElementId(final long queryElementId) {
        this.queryElementId = queryElementId;
    }

    public void setTypeId(final long typeId) {
        setQuery("type", typeId);
    }

}
