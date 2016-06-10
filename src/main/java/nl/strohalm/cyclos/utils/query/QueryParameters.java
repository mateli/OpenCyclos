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
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.services.fetch.FetchService;
import nl.strohalm.cyclos.utils.FormatObject;

/**
 * Contains common parameters for any kind of search: result type, page parameters and relationships to fetch automatically for entities.
 * 
 * <p>
 * The attribute <code>fetch</code> may contain a List of Relationships, identifying those which should be automatically loaded by the persistence
 * mechanism. It provides an external way to lazily load such relationships whenever is necessary. For more information, see FetchService
 * documentation.
 * 
 * @author luis
 * @see FetchService
 * @see Relationship
 */
public abstract class QueryParameters implements Serializable, Cloneable {

    /**
     * Determine the result type for a query
     * @author luis
     */
    public static enum ResultType {
        /**
         * All results are assembled in a list and returned
         */
        LIST,

        /**
         * A Page instance is returned, containing only the page results plus the total result count
         * @see Page
         */
        PAGE,

        /**
         * A IteratorList instance is returned, allowing iteration over results, lazily fetching them
         * @see IteratorList
         */
        ITERATOR;
    }

    private static final long serialVersionUID = -151092190863904194L;
    private Set<Relationship> fetch;
    private PageParameters    pageParameters;
    private ResultType        resultType       = ResultType.LIST;

    public void clearFetch() {
        fetch = null;
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (final CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public void fetch(final Relationship... relationships) {
        if (fetch == null) {
            fetch = new LinkedHashSet<Relationship>();
        }
        if (relationships != null) {
            fetch.addAll(Arrays.asList(relationships));
        }
    }

    public Set<Relationship> getFetch() {
        return fetch;
    }

    public PageParameters getPageParameters() {
        return pageParameters;
    }

    public ResultType getResultType() {
        return resultType;
    }

    public boolean isPaged() {
        return pageParameters != null && resultType == ResultType.PAGE;
    }

    public void limitResults(final int maxResults) {
        pageParameters = new PageParameters(maxResults, 0);
    }

    public void setFetch(final Set<Relationship> fetch) {
        this.fetch = fetch;
    }

    public void setIterateAll() {
        setResultType(ResultType.ITERATOR);
        setPageParameters(PageParameters.all());
    }

    public void setPageForCount() {
        setResultType(ResultType.PAGE);
        setPageParameters(PageParameters.count());
    }

    public void setPageParameters(final PageParameters pageParameters) {
        this.pageParameters = pageParameters;
    }

    public void setResultType(final ResultType resultType) {
        this.resultType = resultType == null ? ResultType.LIST : resultType;
    }

    public void setUniqueResult() {
        setResultType(ResultType.LIST);
        setPageParameters(PageParameters.unique());
    }

    @Override
    public String toString() {
        return FormatObject.formatVO(this, "fetch", "pageParameters", "paged");
    }
}
