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
package nl.strohalm.cyclos.entities.ads.imports;

import java.util.Calendar;
import java.util.Collection;

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.Currency;
import nl.strohalm.cyclos.entities.members.Administrator;
import nl.strohalm.cyclos.utils.FormatObject;

/**
 * Contains data about a whole advertisement import
 * 
 * @author luis
 */
public class AdImport extends Entity {

    public static enum Relationships implements Relationship {
        BY("by"), CURRENCY("currency"), ADS("ads"), CATEGORIES("categories");

        private final String name;

        private Relationships(final String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    private static final long              serialVersionUID = 1257547309418826441L;
    private Calendar                       date;
    private Administrator                  by;
    private Currency                       currency;
    private Collection<ImportedAd>         ads;
    private Collection<ImportedAdCategory> categories;

    public Collection<ImportedAd> getAds() {
        return ads;
    }

    public Administrator getBy() {
        return by;
    }

    public Collection<ImportedAdCategory> getCategories() {
        return categories;
    }

    public Currency getCurrency() {
        return currency;
    }

    public Calendar getDate() {
        return date;
    }

    public void setAds(final Collection<ImportedAd> ads) {
        this.ads = ads;
    }

    public void setBy(final Administrator by) {
        this.by = by;
    }

    public void setCategories(final Collection<ImportedAdCategory> categories) {
        this.categories = categories;
    }

    public void setCurrency(final Currency currency) {
        this.currency = currency;
    }

    public void setDate(final Calendar date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return getId() + " at " + FormatObject.formatObject(date);
    }

}
