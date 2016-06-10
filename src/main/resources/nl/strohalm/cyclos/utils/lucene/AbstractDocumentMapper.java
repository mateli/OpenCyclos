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

import nl.strohalm.cyclos.dao.FetchDAO;
import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.Indexable;

import org.apache.lucene.document.Document;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.SessionFactoryUtils;

/**
 * Abstract implementation for document mappers
 * @author luis
 */
public abstract class AbstractDocumentMapper<E extends Entity & Indexable> implements DocumentMapper {

    protected SessionFactory sessionFactory;
    protected FetchDAO       fetchDao;

    @Override
    @SuppressWarnings("unchecked")
    public final Document map(final Indexable indexable) {
        final E entity = (E) indexable;
        final DocumentBuilder document = newDocumentBuilder();
        document.add("id", entity);
        process(document, entity);
        return document.getDocument();
    }

    public void setFetchDao(final FetchDAO fetchDao) {
        this.fetchDao = fetchDao;
    }

    public void setSessionFactory(final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    protected Session getSession() {
        return SessionFactoryUtils.getSession(sessionFactory, true);
    }

    protected DocumentBuilder newDocumentBuilder() {
        return new DocumentBuilder(fetchDao);
    }

    /**
     * Should be implemented in order to add fields to the given document
     */
    protected abstract void process(DocumentBuilder document, E entity);
}
