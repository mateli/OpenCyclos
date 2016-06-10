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
package nl.strohalm.cyclos.entities.ads;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.Relationship;

import org.apache.commons.collections.CollectionUtils;

/**
 * A hierarchical ad category
 * @author luis
 */
public class AdCategory extends Entity implements Comparable<AdCategory> {

    public static enum Relationships implements Relationship {
        PARENT("parent"), CHILDREN("children");
        private final String name;

        private Relationships(final String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    /**
     * The maximum level for nesting categories<br>
     * Note: take into account the calculations in getGlobalOrder when increasing MAX_LEVEL. MAX_LEVEL cannot be increased infinitely.
     */
    public static final int        MAX_LEVEL        = 3;

    private static final long      serialVersionUID = -4371587757348684782L;
    private String                 name;
    private AdCategory             parent;
    private Collection<AdCategory> children;
    private boolean                active;
    private Integer                order            = 0;
    private BigInteger             globalOrder;

    @Override
    public int compareTo(final AdCategory other) {
        return getFullName().compareTo(other.getFullName());
    }

    public Collection<AdCategory> getChildren() {
        return children;
    }

    public String getFullName() {
        if (parent == null) {
            return name;
        } else {
            return parent.getFullName() + ": " + name;
        }
    }

    public String getFullNameButRoot() {
        if (getLevel() <= 2) {
            return name;
        } else {
            return parent.getFullNameButRoot() + ": " + name;
        }
    }

    /**
     * returns a BigInteger indicating the global order index of the category. In the global order, whenever an item with children is encountered, all
     * child nodes are first handled before going on with the next item/node on the same level.<br>
     * Note that this method only works correctly with a maximum of 999 subcategories for a certain category.
     * @author rinke
     */
    public BigInteger getGlobalOrder() {
        if (globalOrder == null) {
            final int correctedLevel = AdCategory.MAX_LEVEL - getLevel();
            final BigInteger levelFactor = new BigInteger("1000").pow(correctedLevel);
            // specified indexes are always > 0. Unspecified indexes of 0 mess up the calculation, so...
            final Integer index = (getOrder() == 0) ? 1 : getOrder();
            final BigInteger parentGlobalOrder = (getParent() == null) ? BigInteger.ZERO : getParent().getGlobalOrder();
            globalOrder = parentGlobalOrder.add(levelFactor.multiply(new BigInteger(index.toString())));
        }
        return globalOrder;
    }

    public int getLevel() {
        if (parent == null) {
            return 1;
        }
        return 1 + parent.getLevel();
    }

    @Override
    public String getName() {
        return name;
    }

    public Integer getOrder() {
        return order;
    }

    public AdCategory getParent() {
        return parent;
    }

    /**
     * Returns a list, beggining of the level 1 category to this one
     */
    public List<AdCategory> getPathFromRoot() {
        final List<AdCategory> path = new ArrayList<AdCategory>();
        AdCategory current = this;
        while (current != null) {
            path.add(current);
            current = current.getParent();
        }
        Collections.reverse(path);
        return path;
    }

    public AdCategory getRootCategory() {
        if (parent == null) {
            return this;
        }
        return parent.getRootCategory();
    }

    /**
     * tests if the category is disabled or not. Note that it does not test this for parent categories. If any parent category is disabled, then
     * logically the child categories are also disabled, even if this is not always updated to these child categories. For this reason, in particular
     * cases it's better to use {@link #isEnabled()}.
     * @return true if the category is not disabled.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * tests if this category or any of its parents is active. If a parent category is disabled, then logically the child categories are also
     * disabled, even though these might not be updated. For this reason, this method might be more appropriate than {@link #isActive()}.
     * @return true if and only if this category and all of it's parents in line are active.
     */
    public boolean isEnabled() {
        AdCategory current = this;
        while (current != null) {
            if (!current.isActive()) {
                return false;
            }
            current = current.getParent();
        }
        return true;
    }

    public boolean isLeaf() {
        return CollectionUtils.isEmpty(children);
    }

    public boolean isRoot() {
        return parent == null;
    }

    public void setActive(final boolean active) {
        this.active = active;
        globalOrder = null;
    }

    public void setChildren(final Collection<AdCategory> children) {
        this.children = children;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setOrder(final Integer orderIndex) {
        order = orderIndex;
        globalOrder = null;
    }

    public void setParent(final AdCategory parent) {
        this.parent = parent;
        globalOrder = null;
    }

    @Override
    public String toString() {
        return getId() + " - " + name;
    }
}
