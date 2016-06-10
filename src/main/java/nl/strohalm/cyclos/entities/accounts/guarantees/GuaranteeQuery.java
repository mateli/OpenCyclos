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
package nl.strohalm.cyclos.entities.accounts.guarantees;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import nl.strohalm.cyclos.entities.accounts.guarantees.Guarantee.Status;
import nl.strohalm.cyclos.entities.customization.fields.PaymentCustomFieldValue;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.utils.Period;
import nl.strohalm.cyclos.utils.query.QueryParameters;

/**
 * Parameters for guarantee queries
 * @author ameyer
 * @author elemener
 * 
 */
public class GuaranteeQuery extends QueryParameters {
    public static enum LoanFilter {
        WITH_LOAN, WITHOUT_LOAN, ALL;
    }

    private static final long                   serialVersionUID = 1141777792283895467L;
    private List<Status>                        statusList;
    private Member                              issuer;
    private Member                              buyer;
    private Member                              seller;
    private Member                              member;                                 // this property is used only when all guarantee types are
                                                                                         // queried to
    // search guarantees with this member as seller or buyer
    private Period                              startIn;
    private Period                              endIn;
    private Period                              registeredIn;
    private BigDecimal                          amountLowerLimit;
    private BigDecimal                          amountUpperLimit;
    private GuaranteeType                       guaranteeType;
    private Collection<GuaranteeType>           allowedGuaranteeTypes;
    private Certification                       certification;
    private LoanFilter                          loanFilter;
    private Collection<PaymentCustomFieldValue> customValues;

    // this property is used to add an OR filter in the resulting HQL query
    // to restrict the result to this member
    private Member                              loggedMember;

    // this flag is used to flter by guarantees whose model is WITH_BUYER_ONLY
    private boolean                             withBuyerOnly;

    // this collection is used to add an IN operator to the query to restrict the result to those guarantees of buyers or seller whose groups belong
    // to this collection
    private Collection<MemberGroup>             managedMemberGroups;

    public Collection<GuaranteeType> getAllowedGuaranteeTypes() {
        return allowedGuaranteeTypes;
    }

    public BigDecimal getAmountLowerLimit() {
        return amountLowerLimit;
    }

    public BigDecimal getAmountUpperLimit() {
        return amountUpperLimit;
    }

    public Member getBuyer() {
        return buyer;
    }

    public Certification getCertification() {
        return certification;
    }

    public Collection<PaymentCustomFieldValue> getCustomValues() {
        return customValues;
    }

    public Period getEndIn() {
        return endIn;
    }

    public GuaranteeType getGuaranteeType() {
        return guaranteeType;
    }

    public Member getIssuer() {
        return issuer;
    }

    public LoanFilter getLoanFilter() {
        return loanFilter;
    }

    public Member getLoggedMember() {
        return loggedMember;
    }

    public Collection<MemberGroup> getManagedMemberGroups() {
        return managedMemberGroups;
    }

    public Member getMember() {
        return member;
    }

    public Period getRegisteredIn() {
        return registeredIn;
    }

    public Member getSeller() {
        return seller;
    }

    public Period getStartIn() {
        return startIn;
    }

    public List<Status> getStatusList() {
        return statusList;
    }

    public boolean isWithBuyerOnly() {
        return withBuyerOnly;
    }

    public void setAllowedGuaranteeTypes(final Collection<GuaranteeType> guaranteeTypes) {
        allowedGuaranteeTypes = guaranteeTypes;
    }

    public void setAmountLowerLimit(final BigDecimal amountFrom) {
        amountLowerLimit = amountFrom;
    }

    public void setAmountUpperLimit(final BigDecimal amountTo) {
        amountUpperLimit = amountTo;
    }

    public void setBuyer(final Member buyer) {
        this.buyer = buyer;
    }

    public void setCertification(final Certification certification) {
        this.certification = certification;

    }

    public void setCustomValues(final Collection<PaymentCustomFieldValue> customValues) {
        this.customValues = customValues;
    }

    public void setEndIn(final Period endIn) {
        this.endIn = endIn;
    }

    public void setGuaranteeType(final GuaranteeType guaranteeType) {
        this.guaranteeType = guaranteeType;
    }

    public void setIssuer(final Member issuer) {
        this.issuer = issuer;
    }

    public void setLoanFilter(final LoanFilter loanFilter) {
        this.loanFilter = loanFilter;
    }

    public void setLoggedMember(final Member loggedMember) {
        this.loggedMember = loggedMember;
    }

    public void setManagedMemberGroups(final Collection<MemberGroup> groups) {
        managedMemberGroups = groups;
    }

    public void setMember(final Member member) {
        this.member = member;
    }

    public void setRegisteredIn(final Period registeredIn) {
        this.registeredIn = registeredIn;
    }

    public void setSeller(final Member seller) {
        this.seller = seller;
    }

    public void setStartIn(final Period startIn) {
        this.startIn = startIn;
    }

    public void setStatusList(final List<Status> statusList) {
        this.statusList = statusList;
    }

    public void setWithBuyerOnly(final boolean withBuyerOnly) {
        this.withBuyerOnly = withBuyerOnly;
    }

}
