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
package nl.strohalm.cyclos.webservices.utils;

import java.util.ArrayList;
import java.util.List;

import nl.strohalm.cyclos.utils.conversion.Transformer;
import nl.strohalm.cyclos.utils.query.Page;
import nl.strohalm.cyclos.utils.query.PageParameters;
import nl.strohalm.cyclos.utils.query.QueryParameters;
import nl.strohalm.cyclos.utils.query.QueryParameters.ResultType;
import nl.strohalm.cyclos.webservices.model.ResultPage;
import nl.strohalm.cyclos.webservices.model.SearchParameters;

/**
 * Helper class for queries
 * @author luis
 */
public class QueryHelper {

    private static final int MAX_PAGE_SIZE = 100;

    public void fill(final SearchParameters searchParameters, final QueryParameters query) {
        if (query != null) {
            query.setResultType(ResultType.PAGE);
            // Ensure the page size is not greater than the maximum allowed
            int pageSize = searchParameters.getPageSize();
            if (pageSize > MAX_PAGE_SIZE) {
                pageSize = MAX_PAGE_SIZE;
            }
            query.setPageParameters(new PageParameters(pageSize, searchParameters.getCurrentPage()));
        }
    }

    /**
     * Creates a {@link ResultPage}, filling from a given {@link Page} (actually received as a {@link List}), converting each element with the given
     * {@link Transformer}
     */
    public <In, Out, P extends ResultPage<Out>> P toResultPage(final Class<P> resultType, final List<In> input, final Transformer<In, Out> transformer) {
        P result;
        try {
            result = resultType.newInstance();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        if (input instanceof Page) {
            final Page<?> page = (Page<?>) input;
            result.setCurrentPage(page.getCurrentPage());
            result.setTotalCount(page.getTotalCount());
            result.setPageSize(page.getPageSize());
        }
        List<Out> list = new ArrayList<Out>(input == null ? 0 : input.size());
        if (input != null) {
            for (In in : input) {
                Out out = transformer.transform(in);
                if (out != null) {
                    list.add(out);
                }
            }
        }
        result.setElements(list);
        return result;
    }

}
