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
package nl.strohalm.cyclos.entities.accounts.pos;

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.utils.StringValuedEnum;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Calendar;

/**
 * 
 * @author rodrigo
 */
@Table(name = "member_pos")
@javax.persistence.Entity
public class MemberPos extends Entity {

    public static enum Relationships implements Relationship {
        POS("pos"), MEMBER("member");
        private final String name;

        private Relationships(final String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public enum Status implements StringValuedEnum {
        PENDING("P"), ACTIVE("A"), BLOCKED("B"), PIN_BLOCKED("K");

        private final String value;

        private Status(final String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

    }

    private static final long serialVersionUID = 7644873097426472662L;

    @Column(name = "allow_make_payment", nullable = false)
    private boolean           allowMakePayment;

    @Column(name = "date", nullable = false)
    private Calendar          date;

    @Column(name = "max_scheduling_payments", nullable = false)
    private Integer           maxSchedulingPayments;

    @Column(name = "number_of_copies", nullable = false)
    private Integer           numberOfCopies;

    @Column(name = "result_page_size", nullable = false)
    private Integer           resultPageSize;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
	private Member            member;

    @OneToOne(mappedBy = "memberPos")
    @JoinColumn(nullable = false)
	private Pos               pos;

    @Column(name = "status", nullable = false, length = 1)
	private Status            status;

    @Column(name = "pos_name", length = 64)
    private String            posName;

    @Column(name = "pos_pin", length = 64)
    private String            posPin;

	public Calendar getDate() {
        return date;
    }

    public Integer getMaxSchedulingPayments() {
        return maxSchedulingPayments;
    }

    public Member getMember() {
        return member;
    }

    public Integer getNumberOfCopies() {
        return numberOfCopies;
    }

    public Pos getPos() {
        return pos;
    }

    public String getPosName() {
        return posName;
    }

    public String getPosPin() {
        return posPin;
    }

    public Integer getResultPageSize() {
        return resultPageSize;
    }

    public Status getStatus() {
        return status;
    }

    public boolean isAllowMakePayment() {
        return allowMakePayment;
    }

    public void setAllowMakePayment(final boolean allowMakePayment) {
        this.allowMakePayment = allowMakePayment;
    }

    public void setDate(final Calendar date) {
        this.date = date;
    }

    public void setMaxSchedulingPayments(final Integer maxSchedulingPayments) {
        this.maxSchedulingPayments = maxSchedulingPayments;
    }

    public void setMember(final Member member) {
        this.member = member;
    }

    public void setNumberOfCopies(final Integer numberOfCopies) {
        this.numberOfCopies = numberOfCopies;
    }

    public void setPos(final Pos pos) {
        this.pos = pos;
    }

    public void setPosName(final String posName) {
        this.posName = posName;
    }

    public void setPosPin(final String posPin) {
        this.posPin = posPin;
    }

    public void setResultPageSize(final Integer resultPageSize) {
        this.resultPageSize = resultPageSize;
    }

    public void setStatus(final Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return getId() + " - " + posName;
    }

}
