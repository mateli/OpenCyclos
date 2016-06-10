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

import java.io.Closeable;
import java.util.Iterator;

import nl.strohalm.cyclos.dao.FetchDAO;
import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.Indexable;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.exceptions.DaoException;
import nl.strohalm.cyclos.utils.EntityHelper;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;

/**
 * An iterator over a search index
 * @author luis
 */
public class IndexIterator<T extends Entity & Indexable> implements Iterator<T>, Closeable {

    private Class<T>          entityType;
    private Iterator<Integer> documentIterator;
    private IndexReader       reader;
    private FetchDAO          fetchDao;
    private Relationship[]    fetch;

    public IndexIterator(final Class<T> entityType, final Iterator<Integer> documentIterator, final IndexReader reader, final FetchDAO fetchDao, final Relationship... fetch) {
        this.entityType = entityType;
        this.documentIterator = documentIterator;
        this.reader = reader;
        this.fetchDao = fetchDao;
        this.fetch = fetch;
    }

    public void close() {
        try {
            reader.close();
        } catch (final Exception e) {
            // Silently ignore
        }
    }

    public boolean hasNext() {
        return documentIterator.hasNext();
    }

    public T next() {
        final Integer documentNumber = documentIterator.next();
        Document document;
        try {
            document = reader.document(documentNumber);
        } catch (final Exception e) {
            throw new DaoException(e);
        }
        final Long id = Long.parseLong(document.get("id"));
        final T entity = EntityHelper.reference(entityType, id);
        return fetchDao.fetch(entity, fetch);
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }
}
