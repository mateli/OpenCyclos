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
package nl.strohalm.cyclos.utils.transactionimport;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import nl.strohalm.cyclos.utils.DataObject;

/**
 * Contains data for each transaction
 * @author luis
 */
public class TransactionImportDTO extends DataObject {
    private static final long   serialVersionUID  = -4645196565607678765L;
    private String              memberUsername;
    private Long                memberId;
    private Map<String, String> memberFieldValues = new HashMap<String, String>();
    private Calendar            date;
    private BigDecimal          amount;
    private boolean             negateAmount;
    private String              typeCode;
    private String              description;
    private Integer             lineNumber;
    private String              comments;

    public BigDecimal getAmount() {
        if (amount == null) {
            return null;
        }
        return negateAmount ? amount.negate() : amount;
    }

    public String getComments() {
        return comments;
    }

    public Calendar getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public Integer getLineNumber() {
        return lineNumber;
    }

    public Map<String, String> getMemberFieldValues() {
        return memberFieldValues;
    }

    public Long getMemberId() {
        return memberId;
    }

    public String getMemberUsername() {
        return memberUsername;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public boolean isNegateAmount() {
        return negateAmount;
    }

    public void setAmount(final BigDecimal amount) {
        this.amount = amount;
    }

    public void setComments(final String comments) {
        this.comments = comments;
    }

    public void setDate(final Calendar date) {
        this.date = date;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setLineNumber(final Integer lineNumber) {
        this.lineNumber = lineNumber;
    }

    public void setMemberFieldValue(final String fieldInternalName, final String value) {
        memberFieldValues.put(fieldInternalName, value);
    }

    public void setMemberFieldValues(final Map<String, String> memberFieldValues) {
        if (memberFieldValues == null) {
            this.memberFieldValues.clear();
        } else {
            this.memberFieldValues = memberFieldValues;
        }
    }

    public void setMemberId(final Long memberId) {
        this.memberId = memberId;
    }

    public void setMemberUsername(final String memberUsername) {
        this.memberUsername = memberUsername;
    }

    public void setNegateAmount(final boolean negateAmount) {
        this.negateAmount = negateAmount;
    }

    public void setTypeCode(final String typeCode) {
        this.typeCode = typeCode;
    }
}
