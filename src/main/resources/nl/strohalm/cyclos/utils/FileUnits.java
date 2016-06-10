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

/**
 * Contains the possible units for upload size
 * @author luis
 */
public enum FileUnits {
    BYTES(1, "Bytes"), KILO_BYTES(1024, "KB"), MEGA_BYTES(1024 * 1024, "MB");
    private final int    multiplier;
    private final String display;

    private FileUnits(final int multiplier, final String display) {
        this.multiplier = multiplier;
        this.display = display;
    }

    public int calculate(final int length) {
        return length * multiplier;
    }

    public String getDisplay() {
        return display;
    }

    public int getMultiplier() {
        return multiplier;
    }
}
