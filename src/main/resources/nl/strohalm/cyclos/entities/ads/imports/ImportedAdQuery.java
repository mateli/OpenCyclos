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

import nl.strohalm.cyclos.utils.query.QueryParameters;

/**
 * Query parameters for imported advertisements
 * 
 * @author luis
 */
public class ImportedAdQuery extends QueryParameters {
    public static enum Status {
        ALL, SUCCESS, ERROR
    }

    private static final long serialVersionUID = 2076204103180756883L;
    private AdImport          adImport;
    private Status            status;
    private Integer           lineNumber;

    public AdImport getAdImport() {
        return adImport;
    }

    public Integer getLineNumber() {
        return lineNumber;
    }

    public Status getStatus() {
        return status;
    }

    public void setAdImport(final AdImport adImport) {
        this.adImport = adImport;
    }

    public void setLineNumber(final Integer lineNumber) {
        this.lineNumber = lineNumber;
    }

    public void setStatus(final Status status) {
        this.status = status;
    }
}
