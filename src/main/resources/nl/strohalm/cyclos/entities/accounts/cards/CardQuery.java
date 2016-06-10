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
import java.util.Collection;

import nl.strohalm.cyclos.entities.customization.fields.MemberCustomField;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.utils.Period;
import nl.strohalm.cyclos.utils.query.QueryParameters;

/**
 * Query parameters for Cards
 * @author rodrigo
 */
public class CardQuery extends QueryParameters {

    private static final long             serialVersionUID = 7039471571091900371L;

    private BigInteger                    number;
    private CardType                      cardType;
    private Collection<Card.Status>       status;
    private Collection<MemberCustomField> customValues;
    private Collection<MemberGroup>       groups;
    private Member                        broker;
    private Period                        expiration;
    private Member                        member;

    public Member getBroker() {
        return broker;
    }

    public CardType getCardType() {
        return cardType;
    }

    public Collection<MemberCustomField> getCustomValues() {
        return customValues;
    }

    public Period getExpiration() {
        return expiration;
    }

    public Collection<MemberGroup> getGroups() {
        return groups;
    }

    public Member getMember() {
        return member;
    }

    public BigInteger getNumber() {
        return number;
    }

    public Collection<Card.Status> getStatus() {
        return status;
    }

    public void setBroker(final Member broker) {
        this.broker = broker;
    }

    public void setCardType(final CardType cardType) {
        this.cardType = cardType;
    }

    public void setCustomValues(final Collection<MemberCustomField> customValues) {
        this.customValues = customValues;
    }

    public void setExpiration(final Period expiration) {
        this.expiration = expiration;
    }

    public void setGroups(final Collection<MemberGroup> groups) {
        this.groups = groups;
    }

    public void setMember(final Member member) {
        this.member = member;
    }

    public void setNumber(final BigInteger number) {
        this.number = number;
    }

    public void setStatus(final Collection<Card.Status> status) {
        this.status = status;
    }

}
