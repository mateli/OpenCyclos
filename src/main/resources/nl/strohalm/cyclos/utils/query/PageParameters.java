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
package nl.strohalm.cyclos.utils.query;

import java.io.Serializable;

/**
 * Contains parameters about pagination.
 * @author luis
 */
public class PageParameters implements Serializable {

    private static final long           serialVersionUID = 5377756881002779499L;
    private static final PageParameters UNIQUE           = new PageParameters(1, 0);
    private static final PageParameters COUNT            = new PageParameters(0, 0);
    private static final PageParameters ALL              = new PageParameters(Integer.MAX_VALUE, 0);

    /**
     * Returns an instance that returns all results (currentPage = Integer.MAX_VALUE and pageSize = 0)
     */
    public static PageParameters all() {
        return ALL;
    }

    /**
     * Returns an instance that returns no results, just to count (currentPage = 0 and pageSize = 0)
     */
    public static PageParameters count() {
        return COUNT;
    }

    /**
     * Returns an instance that limits the results to the given number
     */
    public static PageParameters max(final int results) {
        return new PageParameters(results, 0);
    }

    /**
     * Returns an instance that can return a single result (currentPage = 0 and pageSize = 1)
     */
    public static PageParameters unique() {
        return UNIQUE;
    }

    private int currentPage;
    private int pageSize;

    public PageParameters() {
    }

    public PageParameters(final int pageSize, final int currentPage) {
        setPageSize(pageSize);
        setCurrentPage(currentPage);
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getFirstResult() {
        return currentPage * pageSize;
    }

    public int getMaxResults() {
        return pageSize;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setCurrentPage(final int currentPage) {
        this.currentPage = Math.max(0, currentPage);
    }

    public void setPageSize(final int pageSize) {
        this.pageSize = Math.max(0, pageSize);
    }

    @Override
    public String toString() {
        return currentPage + " / " + pageSize;
    }
}
