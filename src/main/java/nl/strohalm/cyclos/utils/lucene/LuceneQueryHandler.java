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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import nl.strohalm.cyclos.dao.FetchDAO;
import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.Indexable;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.exceptions.DaoException;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.exceptions.ApplicationException;
import nl.strohalm.cyclos.utils.DataIteratorHelper;
import nl.strohalm.cyclos.utils.EntityHelper;
import nl.strohalm.cyclos.utils.IteratorListImpl;
import nl.strohalm.cyclos.utils.query.PageImpl;
import nl.strohalm.cyclos.utils.query.PageParameters;
import nl.strohalm.cyclos.utils.query.QueryParameters.ResultType;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.commons.lang.ArrayUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TotalHitCountCollector;

/**
 * Handler for entity queries using Lucene
 * @author luis
 */
public class LuceneQueryHandler {

    private IndexHandler indexHandler;
    private FetchDAO     fetchDao;

    /**
     * Executes a lucene query
     */
    public <E extends Entity & Indexable> List<E> executeQuery(final Class<E> entityType, final Query query, Filter filter, final Sort sort, final ResultType resultType, final PageParameters pageParameters, final Relationship... fetch) {
        if (filter instanceof Filters && !((Filters) filter).isValid()) {
            filter = null;
        }
        switch (resultType) {
            case ITERATOR:
                return iterator(entityType, query, filter, sort, pageParameters, fetch);
            default:
                return listOrPage(entityType, query, filter, sort, resultType, pageParameters, fetch);
        }
    }

    public void setFetchDao(final FetchDAO fetchDao) {
        this.fetchDao = fetchDao;
    }

    public void setIndexHandler(final IndexHandler indexHandler) {
        this.indexHandler = indexHandler;
    }

    public <E extends Entity & Indexable> E toEntity(final IndexReader reader, final int docId, final Class<E> entityType, final Relationship... fetch) {
        try {
            Document doc = reader.document(docId, IdFieldSelector.getInstance());
            long id = Long.parseLong(doc.get("id"));
            E entity = EntityHelper.reference(entityType, id);
            entity = fetchDao.fetch(entity, fetch);
            return entity;
        } catch (EntityNotFoundException e) {
            return null;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    private <E extends Entity & Indexable> List<E> iterator(final Class<E> entityType, final Query query, final Filter filter, final Sort sort, final PageParameters pageParameters, final Relationship... fetch) {
        IndexSearcher searcher = null;
        // Prepare the parameters
        IndexReader reader;
        try {
            reader = indexHandler.openReader(entityType);
        } catch (final DaoException e) {
            // Probably index files don't exist
            return new IteratorListImpl<E>(Collections.<E> emptyList().iterator());
        }
        final int firstResult = pageParameters == null ? 0 : pageParameters.getFirstResult();
        int maxResults = (pageParameters == null || pageParameters.getMaxResults() == 0) ? Integer.MAX_VALUE : pageParameters.getMaxResults() + firstResult;

        try {
            // Run the search
            searcher = new IndexSearcher(reader);
            TopDocs topDocs;
            if (sort == null || ArrayUtils.isEmpty(sort.getSort())) {
                topDocs = searcher.search(query, filter, maxResults);
            } else {
                topDocs = searcher.search(query, filter, maxResults, sort);
            }
            // Open the iterator
            Iterator<E> iterator = new DocsIterator<E>(this, entityType, reader, topDocs, firstResult, fetch);
            DataIteratorHelper.registerOpen(iterator, false);

            // Wrap the iterator
            return new IteratorListImpl<E>(iterator);

        } catch (final Exception e) {
            throw new DaoException(e);
        } finally {
            try {
                searcher.close();
            } catch (final Exception e) {
                // Silently ignore
            }
        }
    }

    private <E extends Entity & Indexable> List<E> listOrPage(final Class<E> entityType, final Query query, final Filter filter, final Sort sort, final ResultType resultType, final PageParameters pageParameters, final Relationship... fetch) {
        IndexSearcher searcher = null;
        // Prepare the parameters
        IndexReader reader;
        try {
            reader = indexHandler.openReader(entityType);
        } catch (final DaoException e) {
            // Probably index files don't exist
            return Collections.emptyList();
        }
        final int firstResult = pageParameters == null ? 0 : pageParameters.getFirstResult();
        int maxResults = pageParameters == null ? Integer.MAX_VALUE : pageParameters.getMaxResults() + firstResult;
        try {
            searcher = new IndexSearcher(reader);
            if (maxResults == 0 && resultType == ResultType.PAGE) {
                // We just want the total hit count.
                TotalHitCountCollector collector = new TotalHitCountCollector();
                searcher.search(query, filter, collector);
                int totalHits = collector.getTotalHits();
                return new PageImpl<E>(pageParameters, totalHits, Collections.<E> emptyList());
            } else {
                if (maxResults == 0) {
                    maxResults = Integer.MAX_VALUE;
                }
                // Run the search
                TopDocs topDocs;
                if (sort == null || ArrayUtils.isEmpty(sort.getSort())) {
                    topDocs = searcher.search(query, filter, maxResults);
                } else {
                    topDocs = searcher.search(query, filter, maxResults, sort);
                }

                // Build the list
                ScoreDoc[] scoreDocs = topDocs.scoreDocs;
                List<E> list = new ArrayList<E>(Math.min(firstResult, scoreDocs.length));
                for (int i = firstResult; i < scoreDocs.length; i++) {
                    ScoreDoc scoreDoc = scoreDocs[i];
                    E entity = toEntity(reader, scoreDoc.doc, entityType, fetch);
                    if (entity != null) {
                        list.add(entity);
                    }
                }

                // When result type is page, get the additional data
                if (resultType == ResultType.PAGE) {
                    list = new PageImpl<E>(pageParameters, topDocs.totalHits, list);
                }
                return list;
            }
        } catch (final EntityNotFoundException e) {
            throw new ValidationException("general.error.indexedRecordNotFound");
        } catch (ApplicationException e) {
            throw e;
        } catch (final Exception e) {
            throw new DaoException(e);
        } finally {
            // Close resources
            try {
                searcher.close();
            } catch (final Exception e) {
                // Silently ignore
            }
            try {
                reader.close();
            } catch (final Exception e) {
                // Silently ignore
            }
        }
    }

}
