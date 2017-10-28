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
package nl.strohalm.cyclos.entities.members.records;

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.Indexable;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.customization.fields.MemberRecordCustomField;
import nl.strohalm.cyclos.entities.customization.fields.MemberRecordCustomFieldValue;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.utils.CustomFieldsContainer;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Calendar;
import java.util.Collection;

/**
 * A member record is a set of values for a member record type
 * @author Jefferson Magno
 */
@Table(name = "member_records")
@javax.persistence.Entity
public class MemberRecord extends Entity implements CustomFieldsContainer<MemberRecordCustomField, MemberRecordCustomFieldValue>, Indexable {

    public static enum Relationships implements Relationship {
        TYPE("type"), ELEMENT("element"), BY("by"), MODIFIED_BY("modifiedBy"), CUSTOM_VALUES("customValues");
        private final String name;

        private Relationships(final String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    private static final long                        serialVersionUID = -3314730704013643086L;

    @ManyToOne
    @JoinColumn(name = "member_record_type_id", nullable = false)
	private MemberRecordType                         type;

    @ManyToOne
    @JoinColumn(name = "element_id", nullable = false)
	private Element                                  element;

    @ManyToOne
    @JoinColumn(name = "by_id", nullable = false)
	private Element                                  by;

    @Column(name = "date", nullable = false, updatable = false)
    private Calendar                                 date;

    @ManyToOne
    @JoinColumn(name = "modified_by_id")
	private Element                                  modifiedBy;

    @Column(name = "last_modified")
    private Calendar                                 lastModified;

    @OneToMany(mappedBy = "memberRecord", cascade = CascadeType.REMOVE)
	private Collection<MemberRecordCustomFieldValue> customValues;

	public Element getBy() {
        return by;
    }

    @Override
    public Class<MemberRecordCustomField> getCustomFieldClass() {
        return MemberRecordCustomField.class;
    }

    @Override
    public Class<MemberRecordCustomFieldValue> getCustomFieldValueClass() {
        return MemberRecordCustomFieldValue.class;
    }

    @Override
    public Collection<MemberRecordCustomFieldValue> getCustomValues() {
        return customValues;
    }

    public Calendar getDate() {
        return date;
    }

    public Element getElement() {
        return element;
    }

    public Calendar getLastModified() {
        return lastModified;
    }

    public Member getMember() {
        final Element element = getElement();
        return element instanceof Member ? (Member) element : null;
    }

    public Element getModifiedBy() {
        return modifiedBy;
    }

    public MemberRecordType getType() {
        return type;
    }

    public void setBy(final Element by) {
        this.by = by;
    }

    @Override
    public void setCustomValues(final Collection<MemberRecordCustomFieldValue> customValues) {
        this.customValues = customValues;
    }

    public void setDate(final Calendar date) {
        this.date = date;
    }

    public void setElement(final Element receiver) {
        element = receiver;
    }

    public void setLastModified(final Calendar lastModified) {
        this.lastModified = lastModified;
    }

    public void setModifiedBy(final Element modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public void setType(final MemberRecordType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return getId() + " type: " + type + " for " + element;
    }

}
