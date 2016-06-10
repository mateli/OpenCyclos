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
import nl.strohalm.cyclos.utils.binding.MapBean;

/**
 * 
 * @author rodrigo
 */
public class EditCardTypeForm extends BaseBindingForm {

    private static final long serialVersionUID = -4416977715735587463L;
    private long              cardTypeId;

    public EditCardTypeForm() {
        setCardType("defaultExpiration", new MapBean("number", "field"));
        setCardType("securityCodeBlockTime", new MapBean("number", "field"));
        setCardType("cardSecurityCodeLength", new MapBean("min", "max"));
    }

    public Map<String, Object> getCardType() {
        return values;
    }

    public Object getCardType(final String key) {
        return values.get(key);
    }

    public long getCardTypeId() {
        return cardTypeId;
    }

    public void setCardType(final Map<String, Object> map) {
        values = map;
    }

    public void setCardType(final String key, final Object value) {
        values.put(key, value);
    }

    public void setCardTypeId(final long cardTypeId) {
        this.cardTypeId = cardTypeId;
    }

}
