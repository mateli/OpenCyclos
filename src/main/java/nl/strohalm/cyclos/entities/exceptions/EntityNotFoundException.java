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
package nl.strohalm.cyclos.entities.exceptions;

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.utils.ClassHelper;

/**
 * Exception thrown when an entity was not found
 * @author luis
 */
public class EntityNotFoundException extends DaoException {
    private static final long serialVersionUID = -140981929520364831L;

    /**
     * Build a message
     */
    private static String messageFor(final Class<? extends Entity> entityType, final Long id, String message) {
        if (message == null) {
            message = "";
        }
        if (entityType == null) {
            return "The specified entity was not found. " + message;
        } else if (id == null) {
            return "The specified " + ClassHelper.getClassName(entityType) + " was not found. " + message;
        } else {
            return "Entity not found: " + ClassHelper.getClassName(entityType) + "#" + id + ". " + message;
        }
    }

    private final Class<? extends Entity> entityType;
    private final Long                    id;

    public EntityNotFoundException() {
        this(null, null, null);
    }

    public EntityNotFoundException(final Class<? extends Entity> entityType) {
        this(entityType, null, null);
    }

    public EntityNotFoundException(final Class<? extends Entity> entityType, final Long id) {
        this(entityType, id, null);
    }

    public EntityNotFoundException(final Class<? extends Entity> entityType, final Long id, final String message) {
        super(messageFor(entityType, id, message));
        this.entityType = entityType;
        this.id = id;
    }

    public EntityNotFoundException(final String message) {
        this(null, null, message);
    }

    public Class<? extends Entity> getEntityType() {
        return entityType;
    }

    public Long getId() {
        return id;
    }

}
