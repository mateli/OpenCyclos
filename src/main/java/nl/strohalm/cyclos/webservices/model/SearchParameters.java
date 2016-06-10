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

import java.io.Serializable;

import nl.strohalm.cyclos.webservices.utils.ObjectHelper;

/**
 * Base class for search parameters
 * @author luis
 */
public abstract class SearchParameters implements Serializable {
    public static final int   DEFAULT_CURRENT_PAGE = 0;
    public static final int   DEFAULT_PAGE_SIZE    = 10;
    private static final long serialVersionUID     = 3364295578835243059L;
    private Integer           currentPage          = DEFAULT_CURRENT_PAGE;
    private Integer           pageSize             = DEFAULT_PAGE_SIZE;

    public int getCurrentPage() {
        return ObjectHelper.valueOf(currentPage);
    }

    public int getPageSize() {
        return ObjectHelper.valueOf(pageSize);
    }

    public void setCurrentPage(final int currentPage) {
        if (currentPage >= 0) {
            this.currentPage = currentPage;
        }
    }

    public void setPageSize(final int pageSize) {
        if (pageSize > 0) {
            this.pageSize = pageSize;
        }
    }

    @Override
    public String toString() {
        return "SearchParameters [currentPage=" + currentPage + ", pageSize=" + pageSize + "]";
    }
}
