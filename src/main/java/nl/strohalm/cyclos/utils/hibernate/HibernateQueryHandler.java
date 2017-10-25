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
package nl.strohalm.cyclos.utils.hibernate;

import nl.strohalm.cyclos.dao.FetchDAO;
import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.EntityReference;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.utils.ClassHelper;
import nl.strohalm.cyclos.utils.EntityHelper;
import nl.strohalm.cyclos.utils.FetchingIteratorListImpl;
import nl.strohalm.cyclos.utils.IteratorListImpl;
import nl.strohalm.cyclos.utils.PropertyHelper;
import nl.strohalm.cyclos.utils.query.IteratorList;
import nl.strohalm.cyclos.utils.query.Page;
import nl.strohalm.cyclos.utils.query.PageImpl;
import nl.strohalm.cyclos.utils.query.PageParameters;
import nl.strohalm.cyclos.utils.query.QueryParameters.ResultType;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Query;
import org.hibernate.collection.spi.PersistentCollection;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.LazyInitializer;

import javax.persistence.EntityManager;
import javax.persistence.Parameter;
import javax.persistence.PersistenceContext;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.ManagedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Handler for entity queries using Hibernate
 * @author luis
 */
public class HibernateQueryHandler {

    private static Pattern FIRST_ALIAS     = Pattern.compile("^ *(from +[^ ]+|select(?: +distinct)?) +([^ ]+).*");
    private static Pattern LEFT_JOIN_FETCH = Pattern.compile("\\^left\\s+join\\s+fetch(\\s+[\\w\\.]+)?(\\s*[\\w]+)?");

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Returns an HQL query without the fetch part
     */
    private static String stripFetch(String hql) {
        // This is done so we don't confuse the matcher, i.e.: from X x left join fetch x.a *left* join fetch ... -> that *left* could be an alias
        hql = hql.replaceAll("left join", "^left join");

        Matcher matcher = LEFT_JOIN_FETCH.matcher(hql);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String path = StringUtils.trimToEmpty(matcher.group(1));
            String alias = StringUtils.trimToEmpty(matcher.group(2));
            boolean nextIsLeft = "left".equalsIgnoreCase(alias);
            boolean safeToRemove = alias.isEmpty() || nextIsLeft || "where".equalsIgnoreCase(alias) || "order".equalsIgnoreCase(alias) || "group".equalsIgnoreCase(alias);
            String replacement;
            if (safeToRemove) {
                // No alias - we can just remove the entire left join fetch
                replacement = nextIsLeft ? "" : " " + alias;
            } else {
                // Just remove the 'fetch'
                replacement = " left join " + path + " " + alias;
            }
            matcher.appendReplacement(sb, replacement);
        }
        matcher.appendTail(sb);
        return sb.toString().replaceAll("\\^left join", "left join");
    }

    /**
     * Return a count HQL. Should handle fetches and order by, removing them
     */
    private static String transformToCount(String hql) {
        hql = stripFetch(hql);
        final int fromIndex = hql.indexOf("from ");
        final int orderIndex = hql.indexOf("order by ");
        final StringBuilder sb = new StringBuilder();
        final boolean isDistinct = hql.indexOf(" distinct ") >= 0;
        final Matcher matcher = FIRST_ALIAS.matcher(hql);
        if (matcher.matches()) {
            final String firstAlias = matcher.group(2);
            sb.append("select count(").append(isDistinct ? " distinct " : "").append(firstAlias).append(".id) ");
        } else {
            sb.append("select count(*)");
        }
        if (orderIndex < 0) {
            // select a from A a where 1=1
            // -> select count(*) from A a where 1=1
            sb.append(hql.substring(fromIndex));
        } else {
            // select a, b from A a where 1=1 order by a.name
            // -> select count(*) from A a where 1=1
            sb.append(hql.substring(fromIndex, orderIndex));
        }
        return sb.toString();
    }

    private FetchDAO          fetchDao;

    /**
     * Applies the result limits to the given query
     */
    public void applyPageParameters(final PageParameters pageParameters, final javax.persistence.Query query) {
        Integer firstResult = pageParameters == null ? null : pageParameters.getFirstResult();
        if (firstResult != null && firstResult >= 0) {
            query.setFirstResult(firstResult);
        }
        Integer maxResults = pageParameters == null ? null : pageParameters.getMaxResults();
        if (maxResults != null && maxResults > 0) {
            query.setMaxResults(maxResults);
        }
    }

    /**
     * Apply the page parameters to an in-memory collection
     */
    public <E> List<E> applyResultParameters(final ResultType resultType, final PageParameters pageParameters, final Collection<E> records) {
        switch (resultType) {
            case LIST:
            case ITERATOR:
                final int maxResults = pageParameters == null ? Integer.MAX_VALUE : pageParameters.getMaxResults();
                List<E> list = new ArrayList<E>();
                if (maxResults > 0) {
                    int i = 0;
                    for (final E item : records) {
                        list.add(item);
                        i++;
                        if (i >= maxResults) {
                            break;
                        }
                    }
                }
                if (resultType == ResultType.ITERATOR) {
                    list = new IteratorListImpl<E>(list.iterator());
                }
                return list;
            case PAGE:
                final int currentPage = pageParameters == null ? 0 : pageParameters.getCurrentPage();
                final int pageSize = pageParameters == null ? 15 : pageParameters.getPageSize();
                final int firstIndex = currentPage * pageSize;
                final int lastIndex = firstIndex + pageSize;
                final List<E> pageElements = new ArrayList<E>(pageSize);
                int index = -1;
                for (final E payment : records) {
                    index++;
                    if (index < firstIndex) {
                        continue;
                    }
                    if (index > lastIndex) {
                        break;
                    }
                    pageElements.add(payment);
                }
                return new PageImpl<E>(pageParameters, records.size(), pageElements);
            default:
                throw new IllegalStateException(resultType + "?");
        }
    }

    /**
     * Copies the persistent properties from the source to the destination entity
     */
    public void copyProperties(final Entity source, final Entity dest) {
        if (source == null || dest == null) {
            return;
        }

        final ManagedType<? extends Entity> metaData = getClassMetamodel(source);
        for (Attribute<?, ?> attribute : metaData.getAttributes()) {
            // Skip the collections
            if (attribute.isCollection()) {
                PropertyHelper.set(dest, attribute.getName(), null);
            } else {
                PropertyHelper.set(dest, attribute.getName(), PropertyHelper.get(source, attribute.getName()));
            }
        }

    }

    /**
     * Execute a query based on the parameters
     * @param <E> The entity type
     * @param resultType The desired result type
     * @param hql The HQL query
     * @param namedParameters The HQL named parameter values (normally a Map or a Bean)
     * @param pageParameters The page parameters. Affects all ResultTypes, by limiting the number of results
     * @param fetch The relationships to fetch, if any
     * @return A List of the expected entity type. It will be:
     * <ul>
     * <li>A {@link Page} when ResultType == PAGE</li>
     * <li>A {@link IteratorList} when ResultType == ITERATOR</li>
     * <li>A {@link List} when ResultType == LIST</li>
     * </ul>
     */
    public <E> List<E> executeQuery(final String cacheRegion, final ResultType resultType, final String hql, final Object namedParameters, final PageParameters pageParameters, final Relationship... fetch) {

        if (1==1){ // todo enorrmann
            return list(cacheRegion, hql, namedParameters, pageParameters, fetch);
        }

        // Check the result type
        switch (resultType) {
            case LIST:
                return list(cacheRegion, hql, namedParameters, pageParameters, fetch);
            case PAGE:
                return page(cacheRegion, hql, namedParameters, pageParameters, fetch);
            case ITERATOR:
                // Iterators shouldn't use cache to avoid too many objects in memory
                return iterator(hql, namedParameters, pageParameters, fetch);
            default:
                throw new IllegalArgumentException("Unknown result type: " + resultType);
        }
    }

    /**
     * Returns the class meta data for the given entity
     */
    public ManagedType<? extends Entity> getClassMetamodel(final Entity entity) {
        return entityManager.getMetamodel().managedType(entity.getClass());
    }

    public FetchDAO getFetchDao() {
        return fetchDao;
    }

    /**
     * Initialize an entity or collection
     */
    public Object initialize(final Object object) {
        if (object instanceof HibernateProxy) {
            // Reassociate the entity with the current session
            Entity entity = (Entity) object;
            entity = entityManager.find(EntityHelper.getRealClass(entity), entity.getId());
            // Return the implementation associated with the proxy
            if (entity instanceof HibernateProxy) {
                final LazyInitializer lazyInitializer = ((HibernateProxy) entity).getHibernateLazyInitializer();
                lazyInitializer.initialize();
                return lazyInitializer.getImplementation();
            } else {
                return entity;
            }
        } else if (object instanceof PersistentCollection) {
            // Reassociate the collection with the current session
//            return getHibernateTemplate().execute(new HibernateCallback<Object>() {
//                @Override
//                public Object doInHibernate(final Session session) throws HibernateException{
//                    final PersistentCollection persistentCollection = ((PersistentCollection) object);
//                    Entity owner = (Entity) persistentCollection.getOwner();
//                    final String role = persistentCollection.getRole();
//                    if (owner == null || role == null) {
//                        return persistentCollection;
//                    }
//                    // Retrieve the owner of this persistent collection, associated with the current session
//                    owner = (Entity) entityManager.find(EntityHelper.getRealClass(owner), owner.getId());
//                    // Retrieve the collection through it's role (property name)
//                    final String propertyName = PropertyHelper.lastProperty(role);
//                    final Object currentCollection = PropertyHelper.get(owner, propertyName);
//                    if (currentCollection instanceof PersistentCollection) {
//                        Hibernate.initialize(currentCollection);
//                    }
//                    return currentCollection;
//                }
//            });
        }
        try {
            Hibernate.initialize(object);
        } catch (final ObjectNotFoundException e) {
            throw new EntityNotFoundException();
        }
        return object;
    }

    /**
     * Initializes a lazy property
     */
    public Object initializeProperty(final Object bean, final String relationshipName) {
        final String first = PropertyHelper.firstProperty(relationshipName);
        Object value = PropertyHelper.get(bean, first);
        value = initialize(value);
        PropertyHelper.set(bean, first, value);
        return value;
    }

    @SuppressWarnings("unchecked")
    public void resolveReferences(final Entity entity) {
        final ManagedType<? extends Entity> metaData = getClassMetamodel(entity);
        for (Attribute<?, ?> attribute : metaData.getAttributes()) {
            if (attribute.isAssociation()) {
                // Properties that are relationships to other entities
                Entity rel = PropertyHelper.get(entity, attribute.getName());
                if (rel instanceof EntityReference) {
                    rel = entityManager.find(EntityHelper.getRealClass(rel), rel.getId());
                    PropertyHelper.set(entity, attribute.getName(), rel);
                }
            } else if (attribute.isCollection() && !Map.class.isAssignableFrom(attribute.getJavaType())) {
                // Properties that are collections of other entities
                final Collection<?> current = PropertyHelper.get(entity, attribute.getName());
                if (current != null && !(current instanceof PersistentCollection)) {
                    // We must check that the collection is made of entities, since Hibernate supports collections os values
                    boolean isEntityCollection = true;
                    final Collection<Entity> resolved = ClassHelper.instantiate(current.getClass());
                    for (final Object object : current) {
                        if (object != null && !(object instanceof Entity)) {
                            isEntityCollection = false;
                            break;
                        }
                        Entity e = (Entity) object;
                        if (object instanceof EntityReference) {
                            e = entityManager.find(EntityHelper.getRealClass(e), e.getId());
                        }
                        resolved.add(e);
                    }
                    if (isEntityCollection) {
                        PropertyHelper.set(entity, attribute.getName(), resolved);
                    }
                }
            }
        }
    }

    public void setFetchDao(final FetchDAO fetchDao) {
        this.fetchDao = fetchDao;
    }

    /**
     * Sets the query bind named parameters
     */
    public void setQueryParameters(final javax.persistence.Query query, final Object parameters) {
        if (parameters != null) {
            if (parameters instanceof Map<?, ?>) {
                for (final Parameter<?> param : query.getParameters()) {
                    final Object value = query.getParameterValue(param.getName());
                    if (value instanceof Collection<?>) {
                        final Collection<Object> values = new ArrayList<>(((Collection<?>) value).size());
                        for (final Object object : (Collection<?>) value) {
                            if (object instanceof EntityReference) {
                                values.add(fetchDao.fetch((Entity) object));
                            } else {
                                values.add(object);
                            }
                        }
                        query.setParameter(param.getName(), values);
                    } else if (value instanceof EntityReference) {
                        query.setParameter(param.getName(), fetchDao.fetch((Entity) value));
                    } else {
                        query.setParameter(param.getName(), value);
                    }
                }
            } else {
                query.setParameter(1, parameters);
            }
        }
    }

    /**
     * Iterate the query with hibernate
     */
    @Deprecated
    public <E> Iterator<E> simpleIterator(final String hql, final Object namedParameters, final PageParameters pageParameters) {
        String strippedHql = stripFetch(hql);
        javax.persistence.Query query = entityManager.createQuery(strippedHql);
        applyPageParameters(pageParameters, query);
        setQueryParameters(query, namedParameters);
        return query.getResultList().iterator();
    }

    /**
     * List the query with hibernate
     */
    @SuppressWarnings("unchecked")
    public <E> List<E> simpleList(final String cacheRegion, final String hql, final Object namedParameters, final PageParameters pageParameters, final Relationship... fetch) {

        final javax.persistence.Query query = entityManager.createQuery(hql);
        setQueryParameters(query, namedParameters);
        applyPageParameters(pageParameters, query);
        setCacheRegion(query, cacheRegion);
        final List<E> list = query.getResultList();

        if (fetch != null && fetch.length > 0) {
            for (int i = 0; i < list.size(); i++) {
                final Entity entity = (Entity) list.get(i);
                list.set(i, (E) fetchDao.fetch(entity, fetch));
            }
        }
        return list;
    }

    /**
     * Execute the query for ResultType == ITERATOR
     */
    private <E> List<E> iterator(final String hql, final Object namedParameters, final PageParameters pageParameters, final Relationship[] fetch) {
        final Iterator<E> iterator = simpleIterator(hql, namedParameters, pageParameters);
        return new FetchingIteratorListImpl<E>(iterator, fetchDao, fetch);
    }

    /**
     * Execute the query for ResultType == LIST
     * @param pageParameters
     */
    private <E> List<E> list(final String cacheRegion, final String hql, final Object namedParameters, final PageParameters pageParameters, final Relationship... fetch) {
        return simpleList(cacheRegion, hql, namedParameters, pageParameters, fetch);
    }

    /**
     * Execute the query for ResultType == PAGE
     */
    private <E> List<E> page(final String cacheRegion, final String hql, final Object namedParameters, final PageParameters pageParameters, final Relationship[] fetch) {

        // Count all records
        final javax.persistence.Query query = entityManager.createQuery(transformToCount(hql.toString()), Long.class);
        setQueryParameters(query, namedParameters);
        setCacheRegion(query, cacheRegion);
        final Integer totalCount = ((Long) query.getSingleResult()).intValue();

        // Get only the page records
        List<E> list;
        if (pageParameters.getMaxResults() == 0) {
            // Max results == 0 means only to count
            list = Collections.emptyList();
        } else {
            list = simpleList(cacheRegion, hql, namedParameters, pageParameters, fetch);
        }

        // Create a page instance
        return new PageImpl<>(pageParameters, totalCount, list);
    }

    private void setCacheRegion(final javax.persistence.Query query, final String cacheRegion) {
        if (cacheRegion != null) {
            query.setHint("org.hibernate.cacheable", true);
            query.setHint("org.hibernate.cacheRegion", cacheRegion);
        }
    }

}
