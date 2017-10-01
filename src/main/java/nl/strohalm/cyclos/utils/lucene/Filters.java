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
package nl.strohalm.cyclos.utils.lucene;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import nl.strohalm.cyclos.entities.exceptions.QueryParseException;
import nl.strohalm.cyclos.utils.DateHelper;
import nl.strohalm.cyclos.entities.utils.Period;
import nl.strohalm.cyclos.utils.conversion.CoercionHelper;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.CachingWrapperFilter;
import org.apache.lucene.queries.ChainedFilter;
import org.apache.lucene.search.DocIdSet;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.FilteredQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.QueryWrapperFilter;
import org.apache.lucene.search.TermRangeFilter;
import org.apache.lucene.queries.TermsFilter;
import org.apache.lucene.util.Bits;
import org.apache.lucene.util.BytesRef;

/**
 * A filter collection
 *
 * @author luis
 */
public class Filters extends Filter implements Cloneable {

    private static final long serialVersionUID = -5904251497348657910L;

    /**
     * Returns all filters in a AND operation
     */
    public static Filter and(final Filter... filters) {
        return chain(ChainedFilter.AND, filters);
    }

    /**
     * Returns all filters in a ANDNOT operation
     */
    public static Filter andNot(final Filter... filters) {
        return chain(ChainedFilter.ANDNOT, filters);
    }

    /**
     * Returns a filter that applies a query to an specific field
     */
    public static Filter fieldQuery(final Analyzer analyzer, final String field, final String query) {
        if (StringUtils.isEmpty(query)) {
            return null;
        }
        QueryParser parser = new QueryParser(LuceneUtils.LUCENE_VERSION, field, analyzer);
        try {
            Query q = parser.parse(query);
            return new QueryWrapperFilter(q);
        } catch (ParseException e) {
            throw new QueryParseException();
        }
    }

    /**
     * Returns a FilteredQuery using the given filter
     */
    public static Query filter(final Query query, final Filter filter) {
        return new FilteredQuery(query, new CachingWrapperFilter(filter));
    }

    /**
     * Returns all filters in a OR operation
     */
    public static Filter or(final Filter... filters) {
        return chain(ChainedFilter.OR, filters);
    }

    /**
     * Returns a filter for a date range including the min and max values
     */
    public static TermRangeFilter range(final String field, final Calendar min, final Calendar max) {
        return range(field, min, max, true, true);
    }

    /**
     * Returns a filter for a date range, optionally including the min and max
     * values
     */
    public static TermRangeFilter range(final String field, final Calendar min, final Calendar max, final boolean includeMin, final boolean includeMax) {
        if (min == null && max == null) {
            return null;
        }
        final String minStr = min == null ? LuceneFormatter.MIN_DATE : LuceneFormatter.format(min);
        final String maxStr = max == null ? LuceneFormatter.MAX_DATE : LuceneFormatter.format(max);
        return new TermRangeFilter(field, new BytesRef(minStr), new BytesRef(maxStr), includeMin, includeMax);
    }

    /**
     * Returns a filter for a number range including the min and max values
     */
    public static TermRangeFilter range(final String field, final Number min, final Number max) {
        return range(field, min, max, true, true);
    }

    /**
     * Returns a filter for a number range, optionally including the min and max
     * values
     */
    public static TermRangeFilter range(final String field, final Number min, final Number max, final boolean includeMin, final boolean includeMax) {
        if (min == null && max == null) {
            return null;
        }
        final String minStr = min == null ? LuceneFormatter.MIN_DECIMAL : LuceneFormatter.format(min);
        final String maxStr = max == null ? LuceneFormatter.MAX_DECIMAL : LuceneFormatter.format(max);
        return new TermRangeFilter(field, new BytesRef(minStr), new BytesRef(maxStr), includeMin, includeMax);
    }

    /**
     * Returns a filter for a string range including the min and max values
     * (both are required, or no filter will be applied
     */
    public static TermRangeFilter range(final String field, final String min, final String max) {
        return range(field, min, max, true, true);
    }

    /**
     * Returns a filter for a string range, optionally including the min and max
     * values (both are required, or no filter will be applied
     */
    public static TermRangeFilter range(final String field, final String min, final String max, final boolean includeMin, final boolean includeMax) {
        if (StringUtils.isEmpty(min) || StringUtils.isEmpty(max)) {
            return null;
        }
        return new TermRangeFilter(field, new BytesRef(min), new BytesRef(max), includeMin, includeMax);
    }

    /**
     * Returns a filter that forces a given field to be one of the given values
     */
    public static Filter terms(final String field, final Collection<?> values) {
        if (CollectionUtils.isEmpty(values)) {
            return null;
        }
        final TermsFilter filter = new TermsFilter();
        int count = 0;
        for (final Object object : values) {
            final String term = object == null ? null : StringUtils.trimToNull("" + object);
            if (term != null) {
                /* todo addterm*/
                //filter.addTerm(new Term(field, term));
                count++;
            }
        }
        return count == 0 ? null : filter;
    }

    /**
     * Returns a filter that forces a given field to be one of the given values
     */
    public static Filter terms(final String field, final Object... values) {
        return values == null ? null : terms(field, Arrays.asList(values));
    }

    /**
     * Returns all filters in a OR operation
     */
    private static Filter chain(final int logic, Filter... filters) {
        filters = normalize(filters);
        if (ArrayUtils.isEmpty(filters)) {
            return null;
        }
        return new ChainedFilter(filters, logic);
    }

    /**
     * Normalizes the filters, returning null on empty array and removing null
     * values
     */
    private static Filter[] normalize(final Filter[] filters) {
        if (ArrayUtils.isEmpty(filters)) {
            return null;
        }
        final List<Filter> list = new ArrayList<Filter>(filters.length);
        for (final Filter filter : filters) {
            if (filter != null) {
                list.add(filter);
            }
        }
        return list.isEmpty() ? null : list.toArray(new Filter[list.size()]);
    }

    private final List<Filter> filters = new ArrayList<Filter>();

    /**
     * Adds a custom filter
     */
    public void add(final Filter filter) {
        if (filter != null) {
            filters.add(filter);
        }
    }

    /**
     * Adds a query text for a given field. Internally uses a
     * {@link QueryWrapperFilter}, parsing the query with the given analyzer
     */
    public void addFieldQuery(final Analyzer analyzer, final String field, final String query) {
        add(fieldQuery(analyzer, field, query));
    }

    /**
     * Adds a period for the given query
     */
    public void addPeriod(final String field, final Period period) {
        if (period != null) {
            addRange(field, period.getBegin(), DateHelper.truncateNextDay(period.getEnd()), true, false);
        }
    }

    /**
     * Adds a date range filter including min and max values
     */
    public void addRange(final String field, final Calendar min, final Calendar max) {
        addRange(field, min, max, true, true);
    }

    /**
     * Adds a date range filter, optionally including min and max values
     */
    public void addRange(final String field, final Calendar min, final Calendar max, final boolean includeMin, final boolean includeMax) {
        add(range(field, min, max, includeMin, includeMax));
    }

    /**
     * Adds a numeric range filter including min and max values
     */
    public void addRange(final String field, final Number min, final Number max) {
        addRange(field, min, max, true, true);
    }

    /**
     * Adds a numeric range filter, optionally including min and max values
     */
    public void addRange(final String field, final Number min, final Number max, final boolean includeMin, final boolean includeMax) {
        add(range(field, min, max, includeMin, includeMax));
    }

    /**
     * Returns a filter that forces a given field to be one of the given values
     */
    public void addTerms(final String field, final Collection<?> values) {
        if (CollectionUtils.isEmpty(values)) {
            return;
        }
        boolean used = false;
        final TermsFilter filter = new TermsFilter();
        for (final Object object : values) {
            final String term = CoercionHelper.coerce(String.class, object);
            if (StringUtils.isNotEmpty(term)) {
                /* todo addterm */
                //filter.addTerm(new Term(field, term));
                used = true;
            }
        }
        if (used) {
            add(filter);
        }
    }

    /**
     * Returns a filter that forces a given field to be one of the given values
     */
    public void addTerms(final String field, final Object... values) {
        if (values != null && values.length > 0) {
            addTerms(field, Arrays.asList(values));
        }
    }

    @Override
    public Object clone() {
        Filters clone = new Filters();
        clone.filters.addAll(filters);
        return clone;
    }


    /**
     * Returns if this filter collection is valid (there's at least one filter
     * on it)
     */
    public boolean isValid() {
        return CollectionUtils.isNotEmpty(filters);
    }

    @Override
    public DocIdSet getDocIdSet(AtomicReaderContext arc, Bits bits) throws IOException {
        if (!isValid()) {
            return null;
        }
        final Filter[] array = filters.toArray(new Filter[filters.size()]);
        return and(array).getDocIdSet(arc, bits);
    }

}
