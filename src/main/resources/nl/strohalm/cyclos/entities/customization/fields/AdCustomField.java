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
package nl.strohalm.cyclos.entities.customization.fields;

import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.utils.StringValuedEnum;

/**
 * A custom field for advertisements
 * @author luis
 */
public class AdCustomField extends CustomField {

    public static enum Visibility implements StringValuedEnum {
        ADMIN("A"), BROKER("B"), WEB_SERVICE("W"), MEMBER("M");

        private final String value;

        private Visibility(final String value) {
            this.value = value;
        }

        @Override
        public String getValue() {
            return value;
        }

        public boolean granted(final Group.Nature nature) {
            switch (nature) {
                case ADMIN:
                    return true;
                case BROKER:
                    return this == BROKER || this == WEB_SERVICE || this == MEMBER;
                default:
                    return this == MEMBER;
            }
        }
    }

    private static final long serialVersionUID = 444181817416712379L;
    private boolean           showInSearch;
    private boolean           indexed;
    private Visibility        visibility       = Visibility.MEMBER;

    public Visibility getVisibility() {
        return visibility;
    }

    public boolean isIndexed() {
        return indexed;
    }

    public boolean isShowInSearch() {
        return showInSearch;
    }

    public void setIndexed(final boolean indexed) {
        this.indexed = indexed;
    }

    public void setShowInSearch(final boolean showInSearch) {
        this.showInSearch = showInSearch;
    }

    public void setVisibility(final Visibility visibility) {
        this.visibility = visibility;
    }
}
