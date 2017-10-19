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
package nl.strohalm.cyclos.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.exceptions.DaoException;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.exceptions.UnexpectedEntityException;
import nl.strohalm.cyclos.exceptions.ApplicationException;
import nl.strohalm.cyclos.utils.ClassHelper;
import nl.strohalm.cyclos.utils.DataIteratorHelper;
import nl.strohalm.cyclos.utils.EntityHelper;
import nl.strohalm.cyclos.utils.hibernate.HibernateHelper;
import nl.strohalm.cyclos.utils.hibernate.HibernateQueryHandler;
import nl.strohalm.cyclos.utils.query.PageParameters;
import nl.strohalm.cyclos.utils.query.QueryParameters;
import nl.strohalm.cyclos.utils.query.QueryParameters.ResultType;
import nl.strohalm.cyclos.utils.transaction.CurrentTransactionData;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.hibernate.NonUniqueObjectException;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Cache;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.sql.rowset.serial.SerialBlob;

/**
 * Basic implementation for DAOs, extending Spring Framework support for Hibernate 3.
 * 
 * @author rafael
 * @author Ivan "Fireblade" Diana
 */
@Transactional
public abstract class BaseDAOImpl<E extends Entity> /* extends HibernateDaoSupport */ implements BaseDAO<E>, InsertableDAO<E>, UpdatableDAO<E>, DeletableDAO<E> {

    private FetchDAO              fetchDao;
    private HibernateQueryHandler hibernateQueryHandler;
    private boolean               hasCache;
    protected Class<E>            entityClass;
    private String                queryCacheRegion;

    @PersistenceContext
    protected EntityManager entityManager;

    public BaseDAOImpl(final Class<E> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public Blob createBlob(final InputStream stream, final int length) {
        try {
            return new SerialBlob(IOUtils.toByteArray(stream));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int delete(final boolean flush, final Long... ids) {
        try {
            if (ids != null && ids.length > 0) {
                int count = 0;
                for (final Long id : ids) {
                    final Object element = entityManager.find(getEntityType(), id);
                    if (element != null) {
                        entityManager.remove(element);
                        count++;
                    }
                }
                if (count > 0 && flush) {
                    flush();
                }

                // Ensure the second level cache is not getting stale
                evictSecondLevelCache();

                return count;
            }
            return 0;
        } catch (final ApplicationException e) {
            throw e;
        } catch (final Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public final int delete(final Long... ids) throws DaoException {
        return delete(true, ids);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends E> T duplicate(final T entity) {
        if (entity == null) {
            return null;
        }
        final T duplicate = (T) ClassHelper.instantiate(entity.getClass());
        getHibernateQueryHandler().copyProperties(entity, duplicate);
        return duplicate;
    }

    @Override
    public Class<E> getEntityType() {
        return this.entityClass;
    }

    public FetchDAO getFetchDao() {
        return fetchDao;
    }

    public HibernateQueryHandler getHibernateQueryHandler() {
        return hibernateQueryHandler;
    }

    @Override
    public final <T extends E> T insert(final T entity) throws UnexpectedEntityException, DaoException {
        return insert(entity, true);
    }

    @Override
    public <T extends E> T insert(final T entity, final boolean flush) {
        try {
            if (entity == null || entity.isPersistent()) {
                throw new UnexpectedEntityException();
            }
            getHibernateQueryHandler().resolveReferences(entity);
            entityManager.persist(entity);
            if (flush) {
                flush();
            }

            // Ensure the second level cache is not getting stale
            evictSecondLevelCache(entity);

            return entity;
        } catch (final ApplicationException e) {
            throw e;
        } catch (final Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public <T extends E> Collection<T> load(final Collection<Long> ids, final Relationship... fetch) {
        if (ids == null) {
            return null;
        }
        final Collection<T> toReturn = new ArrayList<>();
        for (final Long id : ids) {
            T entity = this.load(id, fetch);
            toReturn.add(entity);
        }
        return toReturn;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends E> T load(final Long id, final Relationship... fetch) {
        if (id == null) {
            throw new EntityNotFoundException(getEntityType());
        }
        try {
            // Determine the best way to load an entity.
            // 1. No second level cache and fetch is used: hql query - bypasses the cache, but can include relationships in a single select
            // 2. With cache: load - probably a cache hit. Then fetch the relationships if any
            T entity = null;
            if (!hasCache && !ArrayUtils.isEmpty(fetch)) {
                // Perform a query
                final Map<String, Object> namedParams = new HashMap<>();
                final StringBuilder hql = HibernateHelper.getInitialQuery(getEntityType(), "e", Arrays.asList(fetch));
                HibernateHelper.addParameterToQuery(hql, namedParams, "e.id", id);
                final List<E> list = list(ResultType.LIST, hql.toString(), namedParams, PageParameters.unique(), fetch);
                if (list.isEmpty()) {
                    throw new EntityNotFoundException(this.getEntityType(), id);
                } else {
                    // We must call the fetch DAO anyway because there may be other fetches not retrieved by the hql
                    entity = (T) list.iterator().next();
                }
            } else {
                // Perform a normal load
                // Although there are no fetch relationships we must call the fetch DAO
                // to initialize the entity itself
                entity = (T) entityManager.find(getEntityType(), id);
            }

            return fetchDao.fetch(entity, fetch);
        } catch (final ApplicationException e) {
            throw e;
        } catch (final ObjectNotFoundException e) {
            throw new EntityNotFoundException(getEntityType(), id);
        } catch (final Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    // TODO: Review if this implementation is OK
    public <T extends E> T reload(final Long id, final Relationship... fetch) {
        if (id == null) {
            return null;
        }
        final T entity = (T) EntityHelper.reference(getEntityType(), id);
        return fetchDao.reload(entity, fetch);
    }

    public final void setFetchDao(final FetchDAO fetchDao) {
        this.fetchDao = fetchDao;
    }

    public void setHibernateQueryHandler(final HibernateQueryHandler hibernateQueryHandler) {
        this.hibernateQueryHandler = hibernateQueryHandler;
    }

    @Override
    public final <T extends E> T update(final T entity) throws DaoException {
        return update(entity, true);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends E> T update(T entity, final boolean flush) {
        if (entity == null || entity.isTransient()) {
            throw new UnexpectedEntityException();
        }
        try {
            hibernateQueryHandler.resolveReferences(entity);
            try {
                entityManager.persist(entity);
            } catch (final NonUniqueObjectException e) {
                // If there is another instance associated with the same id,
                // merge the data on the associated instance...
                // ... and return it
                entity = entityManager.merge(entity);
            }
            if (flush) {
                flush();
            }

            // Ensure the second level cache is not getting stale
            evictSecondLevelCache(entity);

            return entity;
        } catch (final ApplicationException e) {
            throw e;
        } catch (final Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     * Executes a bulk update
     */
    protected int bulkUpdate(final String hql, final Object namedParameters) {
        try {
            final Query query = entityManager.createQuery(hql);
            hibernateQueryHandler.setQueryParameters(query, namedParameters);
            int rows = query.executeUpdate();
            if (rows > 0) {
                CurrentTransactionData.setWrite();
            }
            return rows;
        } catch (final ApplicationException e) {
            throw e;
        } catch (final Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     * Return the Hibernate SessionFactory used by this DAO.
     */
    @Deprecated
    // FIXME: MIG_JPA
    public final SessionFactory getSessionFactory() {
        return getSession().getSessionFactory();
    }

    @Deprecated
    // FIXME: MIG_JPA
    protected Session getSession(){
        return entityManager.unwrap(Session.class);
    }

    /**
     * Evicts all second-level cache elements which could get stale on entity updates
     */
    protected void evictSecondLevelCache(E entity) {
        if (hasCache) {
            Cache cache = entityManager.getEntityManagerFactory().getCache();
            synchronized (cache) {
                // We must invalidate all collection regions, as we don't know which other entities have many-to-many relationships with this one
                cache.evict(getEntityType(), entity.getId());
            }
        }
    }

    /**
     * Evicts all second-level cache elements which could get stale on entity updates
     */
    protected void evictSecondLevelCache() {
        Cache cache = entityManager.getEntityManagerFactory().getCache();

        // If this DAO is cached, evict the collection regions, as we don't know which ones will point out to it
        if (hasCache) {
            synchronized (cache) {
                // We must invalidate all collection regions, as we don't know which other entities have many-to-many relationships with this one
                cache.evict(getEntityType());
            }
        }

        // Evict the query cache region
        if (queryCacheRegion != null) {
            synchronized (cache) {
                cache.evict(getEntityType());
            }
        }
    }

    /**
     * Flushes the hibernate session
     */
    protected void flush() {
        entityManager.flush();
    }

    /**
     * Creates an iterator
     */
    protected <T> Iterator<T> iterate(final String hql, final Object namedParameters) {
        try {
            return hibernateQueryHandler.simpleIterator(hql, namedParameters, null);
        } catch (final ApplicationException e) {
            throw e;
        } catch (final Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     * Executes a query with minimal parameters, treating the query object as the source for named parameters
     * @param query The query parameters contains the result type, named parameters as bean properties, the pagination parameters and the properties
     * to fetch
     * @param hql The HQL query
     * @return A list, as returned by {@link HibernateQueryHandler}
     */
    protected <T> List<T> list(final QueryParameters query, final String hql) {
        return list(query, hql, query);
    }

    /**
     * Executes a query with a query parameters and a separate named parameters object
     * @param query The query parameters contains the result type, the pagination parameters and the properties to fetch
     * @param hql The HQL query
     * @return A list, as returned by {@link HibernateQueryHandler}
     */
    protected <T> List<T> list(final QueryParameters query, final String hql, final Object namedParameters) {
        return list(query.getResultType(), hql, namedParameters, query.getPageParameters(), fetchArray(query));
    }

    /**
     * Execute a list with all possible parameters
     * @param resultType The expected result type
     * @param hql The HQL query
     * @param namedParameters The HQL named parameters - May be a Map or a Bean
     * @param pageParameters The pagination parameters, if any. It affects any ResultType, by limiting the number of results the same way as pages
     * @param fetch The relationships to fetch
     * @return A list, as returned by {@link HibernateQueryHandler}
     */
    protected <T> List<T> list(final ResultType resultType, final String hql, final Object namedParameters, final PageParameters pageParameters, final Relationship... fetch) {
        try {
            return hibernateQueryHandler.executeQuery(queryCacheRegion, resultType, hql, namedParameters, pageParameters, fetch);
        } catch (final ApplicationException e) {
            throw e;
        } catch (final Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     * Execute a simple list, binding parameters to the query
     */
    protected <T> List<T> list(final String hql, final Object namedParameters) {
        try {
            final Query query = entityManager.createQuery(hql, getEntityType());
            process(query, namedParameters);
            return query.getResultList();
        } catch (final ApplicationException e) {
            throw e;
        } catch (final Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     * Executes a query, returning results as a Map. The result is expected to be an array, where the first element is the key and the second is the
     * value
     */
    @SuppressWarnings("unchecked")
    protected <K, V> Map<K, V> map(final String hql, final Object namedParameters) {
        Map<K, V> map = new LinkedHashMap<>();
        Iterator<Object[]> iterator = this.iterate(hql, namedParameters);
        try {
            while (iterator.hasNext()) {
                Object[] row = iterator.next();
                map.put((K) row[0], (V) row[1]);
            }
        } finally {
            DataIteratorHelper.close(iterator);
        }
        return map;
    }

    /**
     * Runs something directly in the database connection using a {@link JDBCCallback}
     */
    protected void runNative(final String sql, final Object... namedParameters) {
        Query query = entityManager.createNativeQuery(sql);
        for (int i = 0; i < namedParameters.length; i++) {
            query.setParameter(i+1, namedParameters[i]);
        }
        int modifiedEntities = query.executeUpdate();
        // As there is no way to know whether the native execution performed writes, assume yes
        if (modifiedEntities > 0) {
            CurrentTransactionData.setWrite();
        }
    }

    /**
     * Runs something directly in the database connection using a {@link JDBCCallback}
     */
    protected void runNative(final String sql, Map<String, Object> parameters) {
        Query query = entityManager.createNativeQuery(sql);
        for(Map.Entry<String, Object> entry : parameters.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
        int modifiedEntities = query.executeUpdate();
        // As there is no way to know whether the native execution performed writes, assume yes
        if (modifiedEntities > 0) {
            CurrentTransactionData.setWrite();
        }
    }

    /**
     * May be overridden in order to determine whether the query cache will be used
     */
    protected boolean shouldUseQueryCache() {
        // By default, use the query cache when the second level cache is enabled
        return hasCache;
    }

    /**
     * Execute a simple unique result, binding parameters to the query
     */
    @SuppressWarnings("unchecked")
    protected <T> T uniqueResult(final String hql, final Object namedParameters) {
        try {
            final Query query = entityManager.createQuery(hql);
            process(query, namedParameters);
            query.setMaxResults(1);
            return (T) query.getSingleResult();
        } catch (final NoResultException e) {
            // To emulate previous Hibernate behavior
            return null;
        } catch (final ApplicationException e) {
            throw e;
        } catch (final Exception e) {
            throw new DaoException(e);
        }
    }

    private Relationship[] fetchArray(final QueryParameters query) {
        Relationship[] fetch;
        if (query.getFetch() == null || query.getFetch().isEmpty()) {
            fetch = new Relationship[0];
        } else {
            fetch = query.getFetch().toArray(new Relationship[query.getFetch().size()]);
        }
        return fetch;
    }

    private void process(final Query query, final Object namedParameters) {
        hibernateQueryHandler.setQueryParameters(query, namedParameters);
        if (queryCacheRegion != null) {
            query.setHint("org.hibernate.cacheable", true);
            query.setHint("org.hibernate.cacheRegion", queryCacheRegion);
        }
    }
}
