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
package nl.strohalm.cyclos.entities.accounts.fees.transaction;

import java.math.BigDecimal;

import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.members.Member;

/**
 * A simple fee is applied over the source or destination of a transfer
 * @author luis
 */
public class SimpleTransactionFee extends TransactionFee {

    /**
     * Defines the possible value for the relation between A-Rate and the fee
     * @author luis
     */
    public static enum ARateRelation {
        LINEAR, ASYMPTOTICAL
    }

    public static enum Relationships implements Relationship {
        TO_FIXED_MEMBER("toFixedMember");
        private final String name;

        private Relationships(final String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    private static final long serialVersionUID = -5227967083951048166L;
    private Subject           receiver;
    private Member            toFixedMember;

    /**
     * the highest (start) value of the A-rate based fast conversion tax F; null if A-rate is disabled. This is the value of the fast conversion tax
     * if conversion would take place immediately after creation.
     */
    private BigDecimal        h;
    /**
     * the time in days after which the fast conversion tax F reaches zero. The fast conversion tax F is A-rate based.
     */
    private BigDecimal        aFIsZero;
    /**
     * value of the fast conversion tax F at day 1.
     */
    private BigDecimal        f1;
    /**
     * the value of the horizontal asymptote to which the fast conversion tax F diminishes.
     */
    private BigDecimal        fInfinite;
    /**
     * the minimal value of the fast conversion tax F. If a calculated tax is smaller than fMinimal, the fast conversion tax F will be set to
     * fMinimal. Usually, this is 0 (zero).
     */
    private BigDecimal        fMinimal;
    /**
     * the percentage of the total guarantee period after which the fast conversion tax F reaches 0. This is only used in case of a combined A-rate
     * and D-rate, which are both used to determine a diminishing fast conversion tax F.
     */
    private BigDecimal        gFIsZero;

    public BigDecimal getaFIsZero() {
        return aFIsZero;
    }

    public BigDecimal getF1() {
        return f1;
    }

    public BigDecimal getfInfinite() {
        return fInfinite;
    }

    public BigDecimal getfMinimal() {
        return fMinimal;
    }

    public BigDecimal getgFIsZero() {
        return gFIsZero;
    }

    public BigDecimal getH() {
        return h;
    }

    @Override
    public Nature getNature() {
        return Nature.SIMPLE;
    }

    public Subject getReceiver() {
        return receiver;
    }

    public Member getToFixedMember() {
        return toFixedMember;
    }

    public void setaFIsZero(final BigDecimal aFIsZero) {
        this.aFIsZero = aFIsZero;
    }

    public void setF1(final BigDecimal f1) {
        this.f1 = f1;
    }

    public void setfInfinite(final BigDecimal fInfinite) {
        this.fInfinite = fInfinite;
    }

    public void setfMinimal(final BigDecimal fMinimal) {
        this.fMinimal = fMinimal;
    }

    public void setgFIsZero(final BigDecimal gFIsZero) {
        this.gFIsZero = gFIsZero;
    }

    public void setH(final BigDecimal h) {
        this.h = h;
    }

    public void setReceiver(final Subject receiver) {
        this.receiver = receiver;
    }

    public void setToFixedMember(final Member toFixedMember) {
        this.toFixedMember = toFixedMember;
    }

}
