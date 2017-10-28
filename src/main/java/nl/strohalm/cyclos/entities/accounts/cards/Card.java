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
package nl.strohalm.cyclos.entities.accounts.cards;

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.utils.StringValuedEnum;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Collection;

/**
 * Represents a card
 * @author jefferson
 */
@Table(name = "cards")
@javax.persistence.Entity
public class Card extends Entity {

    public static enum Relationships implements Relationship {
        CARD_TYPE("cardType"), OWNER("owner"), LOGS("logs");
        private final String name;

        private Relationships(final String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    public enum Status implements StringValuedEnum {
        PENDING("P"), ACTIVE("A"), BLOCKED("B"), CANCELED("C"), EXPIRED("E");

        private final String value;

        private Status(final String value) {
            this.value = value;
        }

        @Override
        public String getValue() {
            return value;
        }
    }

    private static final long   serialVersionUID = -2401825447192351066L;

    @ManyToOne
    @JoinColumn(name = "card_type_id", nullable = false)
	private CardType            cardType;

    @Column(name = "card_number", unique = true, scale = 0)
    private BigInteger          cardNumber;

    @Column(name = "card_security_code", length = 64)
    private String              cardSecurityCode;

    @Column(name = "creation_date")
    private Calendar            creationDate;

    @Column(name = "activation_date")
    private Calendar            activationDate;

    @Column(name = "expiration_date")
    private Calendar            expirationDate;

    @Column(name = "card_security_code_blocked_until")
    private Calendar            cardSecurityCodeBlockedUntil;

    @ManyToOne
    @JoinColumn(nullable = false)
	private Member              owner;

    @Column(length = 1, nullable = false)
	private Status              status;

    @OneToMany(mappedBy = "card")
    @OrderBy("date desc")
	private Collection<CardLog> logs;

	public Calendar getActivationDate() {
        return activationDate;
    }

    public BigInteger getCardNumber() {
        return cardNumber;
    }

    public String getCardSecurityCode() {
        return cardSecurityCode;
    }

    public Calendar getCardSecurityCodeBlockedUntil() {
        return cardSecurityCodeBlockedUntil;
    }

    public CardType getCardType() {
        return cardType;
    }

    public Calendar getCreationDate() {
        return creationDate;
    }

    public Calendar getExpirationDate() {
        return expirationDate;
    }

    public Collection<CardLog> getLogs() {
        return logs;
    }

    public Member getOwner() {
        return owner;
    }

    public Status getStatus() {
        return status;
    }

    public void setActivationDate(final Calendar activationDate) {
        this.activationDate = activationDate;
    }

    public void setCardNumber(final BigInteger cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setCardSecurityCode(final String cardSecurityCode) {
        this.cardSecurityCode = cardSecurityCode;
    }

    public void setCardSecurityCodeBlockedUntil(final Calendar cardSecurityCodeBlockedUntil) {
        this.cardSecurityCodeBlockedUntil = cardSecurityCodeBlockedUntil;
    }

    public void setCardType(final CardType cardType) {
        this.cardType = cardType;
    }

    public void setCreationDate(final Calendar creationDate) {
        this.creationDate = creationDate;
    }

    public void setExpirationDate(final Calendar expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setLogs(final Collection<CardLog> logs) {
        this.logs = logs;
    }

    public void setOwner(final Member owner) {
        this.owner = owner;
    }

    public void setStatus(final Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return getId() + " - " + cardNumber;
    }

}
