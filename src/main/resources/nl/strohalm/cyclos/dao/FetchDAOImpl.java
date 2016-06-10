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

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.exceptions.DaoException;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.exceptions.UnexpectedEntityException;
import nl.strohalm.cyclos.utils.EntityHelper;
import nl.strohalm.cyclos.utils.PropertyHelper;
import nl.strohalm.cyclos.utils.binding.PropertyException;
import nl.strohalm.cyclos.utils.hibernate.HibernateQueryHandler;

import org.apache.commons.lang.ArrayUtils;
import org.hibernate.Hibernate;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * Implementation for fetch DAO
 * @author luis
 */
public class FetchDAOImpl extends HibernateDaoSupport implements FetchDAO {

    private HibernateQueryHandler hibernateQueryHandler;

    public void clearCache() {
        final HibernateTemplate ht = getHibernateTemplate();
        ht.flush();
        ht.clear();
    }

    public <E extends Entity> E fetch(final E inputEntity, final Relationship... fetch) {
        return doFetch(inputEntity, fetch);
    }

    public boolean isInitialized(final Object value) {
        return !(value instanceof HibernateProxy) && Hibernate.isInitialized(value);
    }

    @SuppressWarnings("unchecked")
    public <E extends Entity> E reload(final E entity, final Relationship... fetch) throws UnexpectedEntityException, EntityNotFoundException, DaoException {
        if (entity == null || entity.getId() == null) {
            throw new UnexpectedEntityException();
        }
        final HibernateTemplate ht = getHibernateTemplate();
        final E current = (E) ht.load(EntityHelper.getRealClass(entity), entity.getId());
        ht.refresh(current);
        return doFetch(current, fetch);
    }

    public void removeFromCache(final Entity entity) {
        getHibernateTemplate().evict(entity);
    }

    public void setHibernateQueryHandler(final HibernateQueryHandler hibernateQueryHandler) {
        this.hibernateQueryHandler = hibernateQueryHandler;
    }

    /**
     * Does the actual fetch from the database
     */
    @SuppressWarnings("unchecked")
    private <E extends Entity> E doFetch(final E inputEntity, final Relationship... fetch) {
        if (inputEntity == null || inputEntity.getId() == null) {
            throw new UnexpectedEntityException();
        }
        E entity;

        // Discover the entity real class and id
        final Class<? extends Entity> entityType = EntityHelper.getRealClass(inputEntity);
        final Long id = inputEntity.getId();

        // Load and initialize the entity
        try {
            entity = (E) getHibernateTemplate().load(entityType, id);
            entity = (E) hibernateQueryHandler.initialize(entity);
        } catch (final ObjectRetrievalFailureException e) {
            throw new EntityNotFoundException(entityType, id);
        } catch (final ObjectNotFoundException e) {
            throw new EntityNotFoundException(entityType, id);
        }

        // ... and fetch each relationship
        if (!ArrayUtils.isEmpty(fetch)) {
            for (final Relationship relationship : fetch) {
                if (relationship == null) {
                    continue;
                }
                try {
                    final String name = relationship.getName();
                    Object bean = entity;
                    String first = PropertyHelper.firstProperty(name);
                    String nested = PropertyHelper.nestedPath(name);
                    while (bean != null && first != null) {
                        final Object value = hibernateQueryHandler.initializeProperty(bean, first);
                        bean = value;
                        first = PropertyHelper.firstProperty(nested);
                        nested = PropertyHelper.nestedPath(nested);
                    }
                } catch (final PropertyException e) {
                    // Ok - nonexisting property. Probably fetching a relationship that only exists in one of the subclasses, and trying to use it no
                    // another one
                } catch (final Exception e) {
                    throw new PropertyException(entity, relationship.getName(), e);
                }
            }
        }
        return entity;
    }
}
