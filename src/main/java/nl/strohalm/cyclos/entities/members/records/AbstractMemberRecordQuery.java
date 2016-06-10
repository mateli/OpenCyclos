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

import java.util.Collection;

import nl.strohalm.cyclos.entities.customization.fields.MemberRecordCustomFieldValue;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.utils.Period;
import nl.strohalm.cyclos.utils.query.QueryParameters;

/**
 * Base query parameters for member records
 * @author luis
 */
public abstract class AbstractMemberRecordQuery extends QueryParameters {

    public static enum WriterNature {
        ANY, ADMIN, BROKER;
    }

    private static final long                        serialVersionUID = -7648038622779430775L;
    private String                                   keywords;
    private Collection<MemberRecordCustomFieldValue> customValues;
    private Element                                  by;
    private Element                                  element;
    private Member                                   broker;
    private Collection<Group>                        groups;
    private MemberRecordType                         type;
    private Period                                   period;

    public Member getBroker() {
        return broker;
    }

    public Element getBy() {
        return by;
    }

    public Collection<MemberRecordCustomFieldValue> getCustomValues() {
        return customValues;
    }

    public Element getElement() {
        return element;
    }

    public Collection<Group> getGroups() {
        return groups;
    }

    public String getKeywords() {
        return keywords;
    }

    public Period getPeriod() {
        return period;
    }

    public MemberRecordType getType() {
        return type;
    }

    public void setBroker(final Member broker) {
        this.broker = broker;
    }

    public void setBy(final Element by) {
        this.by = by;
    }

    public void setCustomValues(final Collection<MemberRecordCustomFieldValue> customValues) {
        this.customValues = customValues;
    }

    public void setElement(final Element element) {
        this.element = element;
    }

    public void setGroups(final Collection<Group> groups) {
        this.groups = groups;
    }

    public void setKeywords(final String keywords) {
        this.keywords = keywords;
    }

    public void setPeriod(final Period period) {
        this.period = period;
    }

    public void setType(final MemberRecordType memberRecordType) {
        type = memberRecordType;
    }

}
