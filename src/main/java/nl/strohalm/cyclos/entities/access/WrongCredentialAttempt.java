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
package nl.strohalm.cyclos.entities.access;

import java.util.Calendar;

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.accounts.cards.Card;
import nl.strohalm.cyclos.entities.accounts.pos.MemberPos;
import nl.strohalm.cyclos.utils.FormatObject;

/**
 * Entity used to track the number of invalid credential usage attempts
 * 
 * @author luis
 */
public class WrongCredentialAttempt extends Entity {

    private static final long   serialVersionUID = -4758213362595274664L;
    private User                user;
    private Card                card;
    private MemberPos           memberPos;
    private Channel.Credentials credentialType;
    private Calendar            date;

    public Card getCard() {
        return card;
    }

    public Channel.Credentials getCredentialType() {
        return credentialType;
    }

    public Calendar getDate() {
        return date;
    }

    public MemberPos getMemberPos() {
        return memberPos;
    }

    public User getUser() {
        return user;
    }

    public void setCard(final Card card) {
        this.card = card;
    }

    public void setCredentialType(final Channel.Credentials credentialType) {
        this.credentialType = credentialType;
    }

    public void setDate(final Calendar date) {
        this.date = date;
    }

    public void setMemberPos(final MemberPos memberPos) {
        this.memberPos = memberPos;
    }

    public void setUser(final User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return getId() + " - date: " + FormatObject.formatObject(date) + ", credentialType: " + credentialType + ", " + (card != null ? "card: " + card : "user: " + user);
    }

}
