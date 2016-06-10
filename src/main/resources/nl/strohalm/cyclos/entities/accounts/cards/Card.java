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

import java.math.BigInteger;
import java.util.Calendar;
import java.util.Collection;

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.utils.StringValuedEnum;

/**
 * Represents a card
 * @author jefferson
 */
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

    private CardType            cardType;
    private BigInteger          cardNumber;
    private String              cardSecurityCode;
    private Calendar            creationDate;
    private Calendar            activationDate;
    private Calendar            expirationDate;
    private Calendar            cardSecurityCodeBlockedUntil;
    private Member              owner;
    private Status              status;
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
