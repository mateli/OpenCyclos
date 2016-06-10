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
package nl.strohalm.cyclos.entities;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.utils.EntityHelper;
import nl.strohalm.cyclos.utils.EntityVO;

import org.springframework.beans.BeanWrapperImpl;

/**
 * Common class for all domain objects
 * @author luis
 */
public abstract class Entity implements Serializable, Cloneable {

    private static final long serialVersionUID = -7165508156631393668L;
    private Long              id;

    /**
     * Clones this entity
     */
    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (final CloneNotSupportedException e) {
            // Will NEVER happen, since entity is cloneable
            return null;
        }
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof Entity)) {
            return false;
        }
        final Long id = getId();
        final Class<? extends Entity> thisRealClass = EntityHelper.getRealClass(this);
        final Class<? extends Entity> otherRealClass = EntityHelper.getRealClass((Entity) obj);
        if (id != null && (thisRealClass.isAssignableFrom(otherRealClass) || otherRealClass.isAssignableFrom(thisRealClass))) {
            final Entity other = (Entity) obj;
            return id.equals(other.getId());
        }
        return false;
    }

    public Long getId() {
        return id;
    }

    /**
     * This method could be overridden by subclasses to return the entity's name<br>
     * By default it invoke by reflection a getName() method, if not, returns id#simple_class_name
     */
    public String getName() {
        try {
            return new BeanWrapperImpl(this).getPropertyValue("name").toString();
        } catch (final Exception e) {
            return id + "#" + getClass().getSimpleName();
        }
    }

    /**
     * Returns a map containing placeholder values for messages
     */
    public Map<String, Object> getVariableValues(final LocalSettings localSettings) {
        final Map<String, Object> values = new HashMap<String, Object>();
        appendVariableValues(values, localSettings);
        return values;
    }

    @Override
    public int hashCode() {
        final Long id = getId();
        return id == null ? super.hashCode() : id.hashCode();
    }

    /**
     * Return if this entity was already persisted - It has a valid identifier
     */
    public boolean isPersistent() {
        return id != null;
    }

    /**
     * Return if this entity was not yet persisted - It has a null identifier
     */
    public boolean isTransient() {
        return id == null;
    }

    /**
     * @return a read-only view of this entity
     * @see #getName()
     */
    public EntityVO readOnlyView() {
        return new EntityVO(getId(), getName());
    }

    /**
     * Set the id
     * @throws IllegalStateException When the entity already had an identifier
     */
    public void setId(final Long id) throws IllegalStateException {
        this.id = id;
    }

    @Override
    public abstract String toString();

    /**
     * Should be overriden by subclasses to append variable values on the given map
     */
    protected void appendVariableValues(final Map<String, Object> variables, final LocalSettings localSettings) {
    }

    @SuppressWarnings("unchecked")
    protected Object writeReplace() {
        if (this instanceof EntityReference) {
            return new EntityReference.Resolver((Class<? extends Entity>) getClass().getSuperclass(), getId());
        }
        return this;
    }
}
