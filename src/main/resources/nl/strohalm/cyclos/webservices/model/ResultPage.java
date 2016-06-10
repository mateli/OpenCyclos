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
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlTransient;

import nl.strohalm.cyclos.webservices.utils.ObjectHelper;

/**
 * Contains a page of results
 * @author luis
 */
public abstract class ResultPage<T> implements Serializable, Iterable<T> {
    private static final long serialVersionUID = -7387157723771363227L;
    private Integer           currentPage      = 0;
    private Integer           pageSize         = 0;
    private Integer           totalCount       = 0;

    @XmlTransient
    private List<T>           elements;

    public ResultPage() {
    }

    public ResultPage(final int currentPage, final int pageSize, final int totalCount, final List<T> elements) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.elements = elements;
    }

    public int getCurrentPage() {
        return ObjectHelper.valueOf(currentPage);
    }

    @XmlTransient
    public List<T> getElements() {
        return elements;
    }

    public int getPageSize() {
        return ObjectHelper.valueOf(pageSize);
    }

    public int getTotalCount() {
        return ObjectHelper.valueOf(totalCount);
    }

    @Override
    public final Iterator<T> iterator() {
        return elements == null ? Collections.<T> emptyList().iterator() : elements.iterator();
    }

    public void setCurrentPage(final int currentPage) {
        this.currentPage = currentPage;
    }

    public void setElements(final List<T> elements) {
        this.elements = elements;
    }

    public void setPageSize(final int pageSize) {
        this.pageSize = pageSize;
    }

    public void setTotalCount(final int totalCount) {
        this.totalCount = totalCount;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        boolean first = true;
        sb.append(getClass().getSimpleName()).append(" [");
        if (elements != null) {
            for (final T vo : elements) {
                if (!first) {
                    sb.append(", ");
                }
                sb.append(vo.toString());
                first = false;
            }
        }
        sb.append("]");
        return sb.toString();
    }

}
