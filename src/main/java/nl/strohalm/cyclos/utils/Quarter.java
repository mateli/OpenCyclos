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
package nl.strohalm.cyclos.utils;

public enum Quarter implements IntValuedEnum {

    FIRST(1), SECOND(2), THIRD(3), FOURTH(4);

    public static Quarter getQuarter(final int value) {
        final Quarter[] quarters = Quarter.values();
        for (final Quarter quarter : quarters) {
            if (value == quarter.getValue()) {
                return quarter;
            }
        }
        return null;
    }

    private final int value;

    private Quarter(final int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public String toStringRepresentation() {
        String ret = "";
        switch (value) {
            case 1:
                ret = "I";
                break;
            case 2:
                ret = "II";
                break;
            case 3:
                ret = "III";
                break;
            case 4:
                ret = "IV";
                break;
        }
        return ret;
    }

}
