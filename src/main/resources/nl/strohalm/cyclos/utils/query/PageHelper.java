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

import java.util.List;

/**
 * Helper class for working with pages
 * @author luis
 */
public class PageHelper {

    /**
     * Returns the current page for this list. If it is a page, returns the {@link Page#getCurrentPage()} result, otherwise, zero.
     */
    public static int getCurrentPage(final List<?> list) {
        if (list instanceof Page<?>) {
            return ((Page<?>) list).getCurrentPage();
        } else {
            return 0;
        }
    }

    /**
     * Returns the total count for this list. If it is a page, returns the {@link Page#getTotalCount()} result, otherwise, the list size.
     */
    public static int getTotalCount(final List<?> list) {
        if (list == null) {
            return 0;
        } else if (list instanceof Page<?>) {
            return ((Page<?>) list).getTotalCount();
        } else {
            return list.size();
        }
    }

    /**
     * Checks whether the given list has at least one result
     */
    public static boolean hasResults(final List<?> list) {
        return getTotalCount(list) > 0;
    }
}
