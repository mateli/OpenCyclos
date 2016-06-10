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
package nl.strohalm.cyclos.controls.payments;

import nl.strohalm.cyclos.utils.binding.MapBean;

import org.apache.struts.action.ActionForm;

/**
 * Base form for payments
 * @author luis
 */
public abstract class BasePaymentForm extends ActionForm {

    private static final long serialVersionUID = 2801372362831598845L;
    private String            amount;
    private String            description;
    private String            type;
    private String            date;
    private String            currency;
    private String            from;
    private MapBean           customValues     = new MapBean(true, "field", "value");

    public String getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public MapBean getCustomValues() {
        return customValues;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getFrom() {
        return from;
    }

    public String getType() {
        return type;
    }

    public void setAmount(final String amount) {
        this.amount = amount;
    }

    public void setCurrency(final String currency) {
        this.currency = currency;
    }

    public void setCustomValues(final MapBean customValues) {
        this.customValues = customValues;
    }

    public void setDate(final String date) {
        this.date = date;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setFrom(final String from) {
        this.from = from;
    }

    public void setType(final String type) {
        this.type = type;
    }
}
