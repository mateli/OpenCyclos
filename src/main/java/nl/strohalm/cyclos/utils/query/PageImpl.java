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

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

/**
 * Page implementation
 * @author luis
 */
public class PageImpl<E> extends LinkedList<E> implements Page<E> {

    private static final long serialVersionUID = 3721714371412617795L;

    private static <E> Collection<E> pageElements(final PageParameters parameters, final Collection<E> elements) {
        if (elements == null) {
            return Collections.emptyList();
        }
        final int maxSize = parameters.getPageSize();
        if (elements.size() > maxSize) {
            final Collection<E> col = new LinkedList<E>();
            int i = 0;
            for (final E e : elements) {
                if (i == maxSize) {
                    break;
                }
                col.add(e);
                i++;
            }
            return col;
        }
        return elements;
    }

    private final int currentPage;
    private final int pageSize;

    private final int totalCount;

    public PageImpl(final PageParameters parameters, final int totalCount, final Collection<E> elements) {
        super(pageElements(parameters, elements));
        this.pageSize = Math.max(1, parameters.getPageSize());
        this.totalCount = totalCount;
        this.currentPage = Math.max(0, Math.min(parameters.getCurrentPage(), getPageCount() - 1));
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getCurrentPageSize() {
        return size();
    }

    public int getPageCount() {
        return (int) Math.ceil((float) totalCount / (float) pageSize);
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public boolean isLastPage() {
        return currentPage >= (getPageCount() - 1);
    }
}
