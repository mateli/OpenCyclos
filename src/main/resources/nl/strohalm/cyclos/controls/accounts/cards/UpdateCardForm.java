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
package nl.strohalm.cyclos.controls.accounts.cards;

import org.apache.struts.action.ActionForm;

/**
 * 
 * @author rodrigo
 */
public class UpdateCardForm extends ActionForm {

    private static final long serialVersionUID = 7503472851176856243L;

    private String            cardCode1;
    private String            cardCode2;
    private String            password;
    private long              cardId;
    private long              memberId;

    public String getCardCode1() {
        return cardCode1;
    }

    public String getCardCode2() {
        return cardCode2;
    }

    public long getCardId() {
        return cardId;
    }

    public long getMemberId() {
        return memberId;
    }

    public String getPassword() {
        return password;
    }

    public void setCardCode1(final String cardCode1) {
        this.cardCode1 = cardCode1;
    }

    public void setCardCode2(final String cardCode2) {
        this.cardCode2 = cardCode2;
    }

    public void setCardId(final long cardId) {
        this.cardId = cardId;
    }

    public void setMemberId(final long memberId) {
        this.memberId = memberId;
    }

    public void setPassword(final String password) {
        this.password = password;
    }
}
