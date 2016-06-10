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
package nl.strohalm.cyclos.entities.groups;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;

import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.members.Member;

/**
 * A group for member's operators
 * @author luis
 */
public class OperatorGroup extends Group {

    public static enum Relationships implements Relationship {
        MEMBER("member"), MAX_AMOUNT_PER_DAY_BY_TRANSFER_TYPE("maxAmountPerDayByTransferType"), CAN_VIEW_INFORMATION_OF("canViewInformationOf");
        private final String name;

        private Relationships(final String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    private static final long             serialVersionUID = 6092174598805612471L;
    private Member                        member;
    private Map<TransferType, BigDecimal> maxAmountPerDayByTransferType;
    private Collection<AccountType>       canViewInformationOf;

    @Override
    public BasicGroupSettings getBasicSettings() {
        return member.getGroup().getBasicSettings();
    }

    public Collection<AccountType> getCanViewInformationOf() {
        return canViewInformationOf;
    }

    public Map<TransferType, BigDecimal> getMaxAmountPerDayByTransferType() {
        return maxAmountPerDayByTransferType;
    }

    public Member getMember() {
        return member;
    }

    @Override
    public Nature getNature() {
        return Nature.OPERATOR;
    }

    public void setCanViewInformationOf(final Collection<AccountType> canViewInformationOf) {
        this.canViewInformationOf = canViewInformationOf;
    }

    public void setMaxAmountPerDayByTransferType(final Map<TransferType, BigDecimal> maxAmountPerDayByTransferType) {
        this.maxAmountPerDayByTransferType = maxAmountPerDayByTransferType;
    }

    public void setMember(final Member member) {
        this.member = member;
    }

}
