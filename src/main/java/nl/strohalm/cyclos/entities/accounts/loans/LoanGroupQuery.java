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
package nl.strohalm.cyclos.entities.accounts.loans;

import java.util.Collection;

import nl.strohalm.cyclos.entities.customization.fields.CustomFieldValue;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.utils.query.QueryParameters;

/**
 * Query parameters for loan groups
 * @author luis
 */
public class LoanGroupQuery extends QueryParameters {

    private static final long                      serialVersionUID = 7532711515162377497L;

    private Collection<? extends CustomFieldValue> customValues;
    private String                                 description;
    private Member                                 member;
    private String                                 name;
    private Boolean                                noLoans;
    private boolean                                notOfMember;

    public Collection<? extends CustomFieldValue> getCustomValues() {
        return customValues;
    }

    public String getDescription() {
        return description;
    }

    public Member getMember() {
        return member;
    }

    public String getName() {
        return name;
    }

    public Boolean getNoLoans() {
        return noLoans;
    }

    public boolean isNotOfMember() {
        return notOfMember;
    }

    public void setCustomValues(final Collection<? extends CustomFieldValue> customValues) {
        this.customValues = customValues;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setMember(final Member member) {
        this.member = member;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setNoLoans(final Boolean noLoans) {
        this.noLoans = noLoans;
    }

    public void setNotOfMember(final boolean notOfMember) {
        this.notOfMember = notOfMember;
    }
}
