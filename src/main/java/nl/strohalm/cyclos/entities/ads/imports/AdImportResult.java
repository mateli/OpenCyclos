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

import nl.strohalm.cyclos.utils.DataObject;

/**
 * Contains summarized results for an advertisement import
 * 
 * @author luis
 */
public class AdImportResult extends DataObject {

    private static final long serialVersionUID = 2010704976544935803L;

    private int               total;
    private int               errors;
    private int               newCategories;

    public int getErrors() {
        return errors;
    }

    public int getNewCategories() {
        return newCategories;
    }

    public int getTotal() {
        return total;
    }

    public void setErrors(final int errors) {
        this.errors = errors;
    }

    public void setNewCategories(final int newCategories) {
        this.newCategories = newCategories;
    }

    public void setTotal(final int total) {
        this.total = total;
    }

}
