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
package nl.strohalm.cyclos.controls.ads.imports;

import nl.strohalm.cyclos.controls.BaseQueryForm;

/**
 * Form used to show details for imported ads
 * @author luis
 */
public class ImportedAdCategoriesForm extends BaseQueryForm {
    private static final long serialVersionUID = 7650974469826338967L;

    public long getImportId() {
        try {
            return Long.parseLong("" + getQuery("adImport"));
        } catch (final Exception e) {
            return 0L;
        }
    }

    public String getStatus() {
        return (String) getQuery("status");
    }

    public void setImportId(final long id) {
        setQuery("adImport", "" + id);
    }

    public void setStatus(final String status) {
        setQuery("status", status);
    }

}
