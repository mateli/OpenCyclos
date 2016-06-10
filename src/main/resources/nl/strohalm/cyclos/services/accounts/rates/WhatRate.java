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
package nl.strohalm.cyclos.services.accounts.rates;

import nl.strohalm.cyclos.utils.DataObject;

/**
 * Wrapper class around three booleans, specifying what rate to be used and what to be ignored.
 * @author rinke
 */
public class WhatRate extends DataObject {
    private static final long serialVersionUID = 8362264401827064802L;
    private boolean           aRate;
    private boolean           dRate;
    private boolean           iRate;

    public boolean isAny() {
        return aRate || dRate || iRate;
    }

    public boolean isaRate() {
        return aRate;
    }

    public boolean isdRate() {
        return dRate;
    }

    public boolean isiRate() {
        return iRate;
    }

    public void setaRate(final boolean aRate) {
        this.aRate = aRate;
    }

    public void setdRate(final boolean dRate) {
        this.dRate = dRate;
    }

    public void setiRate(final boolean iRate) {
        this.iRate = iRate;
    }

}
