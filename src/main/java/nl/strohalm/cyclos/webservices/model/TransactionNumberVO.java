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
package nl.strohalm.cyclos.webservices.model;

import nl.strohalm.cyclos.webservices.utils.ObjectHelper;

/**
 * The parameters for transaction number generation
 * @author luis
 */
public class TransactionNumberVO {

    private String  prefix;
    private Integer padLength = 0;
    private String  suffix;

    public TransactionNumberVO() {
    }

    public TransactionNumberVO(final String prefix, final Integer padLength, final String suffix) {
        this.prefix = prefix;
        this.padLength = padLength;
        this.suffix = suffix;
    }

    public int getPadLength() {
        return ObjectHelper.valueOf(padLength);
    }

    public String getPrefix() {
        return prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setPadLength(final int padLength) {
        this.padLength = padLength;
    }

    public void setPrefix(final String prefix) {
        this.prefix = prefix;
    }

    public void setSuffix(final String suffix) {
        this.suffix = suffix;
    }

}
