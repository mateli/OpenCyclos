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

import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.customization.fields.CustomFieldValue;

/**
 * Holds a custom field value for temporary imported members
 * 
 * @author luis
 */
public class ImportedAdCustomFieldValue extends CustomFieldValue {

    public static enum Relationships implements Relationship {
        AD("ad");
        private final String name;

        private Relationships(final String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    private static final long serialVersionUID = -1298123213176775308L;

    private ImportedAd        ad;

    public ImportedAd getAd() {
        return ad;
    }

    @Override
    public Object getOwner() {
        return getAd();
    }

    public void setAd(final ImportedAd ad) {
        this.ad = ad;
    }

    @Override
    public void setOwner(final Object owner) {
        if (owner instanceof ImportedAd) {
            setAd((ImportedAd) owner);
        } else {
            throw new IllegalArgumentException(String.format("Invalid owner (%1$s) for custom field value: %2$s", owner, getClass().getSimpleName()));
        }
    }
}
