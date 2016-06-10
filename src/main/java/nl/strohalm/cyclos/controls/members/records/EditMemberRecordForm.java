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

import java.util.Map;

import nl.strohalm.cyclos.controls.BaseBindingForm;
import nl.strohalm.cyclos.utils.binding.MapBean;

/**
 * Form used to create member records
 * @author Jefferson Magno
 */
public class EditMemberRecordForm extends BaseBindingForm {

    private static final long serialVersionUID = -9097175412702879991L;
    private boolean           global;
    private long              memberRecordId;
    private long              memberRecordTypeId;
    private long              baseMemberRecordId;

    public EditMemberRecordForm() {
        setMemberRecord("customValues", new MapBean(true, "field", "value"));
    }

    public long getAdminId() {
        return getElementId();
    }

    public long getBaseMemberRecordId() {
        return baseMemberRecordId;
    }

    public long getElementId() {
        try {
            return (Long) getMemberRecord("element");
        } catch (final Exception e) {
            return 0L;
        }
    }

    public long getMemberId() {
        return getElementId();
    }

    public Map<String, Object> getMemberRecord() {
        return values;
    }

    public Object getMemberRecord(final String key) {
        return values.get(key);
    }

    public long getMemberRecordId() {
        return memberRecordId;
    }

    public long getMemberRecordTypeId() {
        return memberRecordTypeId;
    }

    public long getTypeId() {
        try {
            return (Long) getMemberRecord("type");
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

    public void setBaseMemberRecordId(final long baseMemberRecordId) {
        this.baseMemberRecordId = baseMemberRecordId;
    }

    public void setElementId(final long elementId) {
        setMemberRecord("element", elementId);
    }

    public void setGlobal(final boolean global) {
        this.global = global;
    }

    public void setMemberId(final long memberId) {
        setElementId(memberId);
    }

    public void setMemberRecord(final Map<String, Object> map) {
        values = map;
    }

    public void setMemberRecord(final String key, final Object value) {
        values.put(key, value);
    }

    public void setMemberRecordId(final long memberRecordId) {
        this.memberRecordId = memberRecordId;
    }

    public void setMemberRecordTypeId(final long memberRecordTypeId) {
        this.memberRecordTypeId = memberRecordTypeId;
    }

    public void setTypeId(final long typeId) {
        setMemberRecord("type", typeId);
    }

}
