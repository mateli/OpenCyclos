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
package nl.strohalm.cyclos.services.accounts;

import java.math.BigDecimal;

import nl.strohalm.cyclos.entities.accounts.MemberAccountType;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.utils.DataObject;

/**
 * dto for bulk updates on creditlimits.
 * @author rinke
 */
public class BulkUpdateAccountDTO extends DataObject {

    private static final long serialVersionUID = -1403606697989142292L;

    private MemberAccountType type;
    private MemberGroup       group;
    private BigDecimal        creditLimit;
    private BigDecimal        upperCreditLimit;

    public BulkUpdateAccountDTO() {

    }

    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    public MemberGroup getGroup() {
        return group;
    }

    public MemberAccountType getType() {
        return type;
    }

    public BigDecimal getUpperCreditLimit() {
        return upperCreditLimit;
    }

    public void setCreditLimit(final BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
    }

    public void setGroup(final MemberGroup group) {
        this.group = group;
    }

    public void setType(final MemberAccountType type) {
        this.type = type;
    }

    public void setUpperCreditLimit(final BigDecimal upperCreditLimit) {
        this.upperCreditLimit = upperCreditLimit;
    }

}
