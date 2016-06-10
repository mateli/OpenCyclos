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

import java.util.Map;

import nl.strohalm.cyclos.controls.BaseBindingForm;

/**
 * 
 * @author rodrigo
 */
public class CardForm extends BaseBindingForm {

    private static final long serialVersionUID = 1298519324197161002L;
    private long              memberId;
    private long              cardId;
    private String            securityCode;
    private String            securityCodeConfirmation;
    private String            password;
    private String            listOnly;
    private String            operation;

    public Map<String, Object> getCard() {
        return values;
    }

    public Object getCard(final String key) {
        return values.get(key);
    }

    public long getCardId() {
        return cardId;
    }

    public String getListOnly() {
        return listOnly;
    }

    public long getMemberId() {
        return memberId;
    }

    public String getOperation() {
        return operation;
    }

    public String getPassword() {
        return password;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public String getSecurityCodeConfirmation() {
        return securityCodeConfirmation;
    }

    public void setCard(final Map<String, Object> map) {
        values = map;
    }

    public void setCard(final String key, final Object value) {
        values.put(key, value);
    }

    public void setCardId(final long cardId) {
        this.cardId = cardId;
    }

    public void setListOnly(final String listOnly) {
        this.listOnly = listOnly;
    }

    public void setMemberId(final long memberId) {
        this.memberId = memberId;
    }

    public void setOperation(final String operation) {
        this.operation = operation;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public void setSecurityCode(final String securityCode) {
        this.securityCode = securityCode;
    }

    public void setSecurityCodeConfirmation(final String securityCodeConfirmation) {
        this.securityCodeConfirmation = securityCodeConfirmation;
    }

}
