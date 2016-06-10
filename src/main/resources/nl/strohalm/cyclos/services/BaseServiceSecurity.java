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
package nl.strohalm.cyclos.services;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import nl.strohalm.cyclos.access.Permission;
import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.fetch.FetchServiceLocal;
import nl.strohalm.cyclos.services.permissions.PermissionServiceLocal;
import nl.strohalm.cyclos.utils.access.LoggedUser;

import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.SessionFactoryUtils;

/**
 * Base class for implementations of service security layer
 * @author luis
 */
public abstract class BaseServiceSecurity implements ServiceSecurity {

    protected PermissionServiceLocal permissionService;
    protected FetchServiceLocal      fetchService;
    private SessionFactory           sessionFactory;

    public void setFetchServiceLocal(final FetchServiceLocal fetchService) {
        this.fetchService = fetchService;
    }

    public final void setPermissionServiceLocal(final PermissionServiceLocal permissionService) {
        this.permissionService = permissionService;
    }

    public final void setSessionFactory(final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * adds a Relationship to a fetch list. If it already is present, it is not added. Comes in handy with load methods.
     * @param fetch Relationship... to which the tobeAdded Relationship is added.
     * @param toAdd The Relationship to be added.
     * @return an array of Relationships with the original array plus the newly added.
     */
    protected Relationship[] addToFetch(final Relationship[] fetch, final Relationship... toAdd) {
        Set<Relationship> relationships = new HashSet<Relationship>(Arrays.asList(fetch));
        relationships.addAll(Arrays.asList(toAdd));
        Relationship[] newFetch = relationships.toArray(new Relationship[relationships.size()]);
        return newFetch;
    }

    /**
     * It checks if there is a logged user
     */
    protected void checkHasUser() {
        if (!LoggedUser.hasUser()) {
            throw new PermissionDeniedException();
        }
    }

    /**
     * performs a check if the logged user is the system. To be called from methods which are only to be run via system.
     * @throws PermissionDeniedException in case the logged user isn't system.
     */
    protected void checkIsSystem() {
        if (!LoggedUser.isSystem()) {
            throw new PermissionDeniedException();
        }
    }

    protected PermissionServiceLocal getPermissionServiceLocal() {
        return permissionService;
    }

    protected Session getSession() {
        return SessionFactoryUtils.getSession(sessionFactory, true);
    }

    protected SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * @return if the logged user has or not at least one of the requested permissions
     */
    protected boolean hasPermission(final Permission... permissions) {
        return permissionService.hasPermission(permissions);
    }

    /**
     * @return if the logged user has permission for all required entities
     */
    protected boolean hasPermissionFor(final Permission permission, final Entity... required) {
        return permissionService.hasPermissionFor(permission, required);
    }

    @SuppressWarnings("unchecked")
    protected <T extends Entity> T load(final Class<T> type, final Long id) {
        try {
            return (T) getSession().load(type, id);
        } catch (final ObjectNotFoundException e) {
            throw new EntityNotFoundException(type, id);
        }
    }
}
