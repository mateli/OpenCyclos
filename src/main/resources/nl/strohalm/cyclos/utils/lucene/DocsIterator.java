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
import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.Indexable;
import nl.strohalm.cyclos.entities.Relationship;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

/**
 * An iterator for returned documents in a full text search
 * @author luis
 */
public class DocsIterator<E extends Entity & Indexable> implements Closeable, Iterator<E> {

    private final LuceneQueryHandler luceneQueryHandler;
    private final Class<E>           entityType;
    private final IndexReader        reader;
    private final TopDocs            topDocs;
    private final Relationship[]     fetch;
    private int                      index;
    private E                        entity;

    public DocsIterator(final LuceneQueryHandler luceneQueryHandler, final Class<E> entityType, final IndexReader reader, final TopDocs topDocs, final int firstResult, final Relationship[] fetch) {
        this.luceneQueryHandler = luceneQueryHandler;
        this.entityType = entityType;
        this.reader = reader;
        this.topDocs = topDocs;
        this.fetch = fetch;
        index = firstResult;
        entity = getNextEntity();
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }

    @Override
    public boolean hasNext() {
        return entity != null;
    }

    @Override
    public E next() {
        if (entity == null) {
            throw new NoSuchElementException();
        }
        E result = entity;
        entity = getNextEntity();
        return result;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    private E getNextEntity() {
        E entity = null;
        try {
            ScoreDoc scoreDoc = topDocs.scoreDocs[index++];
            entity = luceneQueryHandler.toEntity(reader, scoreDoc.doc, entityType, fetch);
            if (entity == null) {
                // There could be an entity which was removed. In that case, we just skip it and get the next one
                entity = getNextEntity();
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            entity = null;
        }
        return entity;
    }

}
