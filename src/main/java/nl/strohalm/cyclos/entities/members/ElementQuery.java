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
package nl.strohalm.cyclos.entities.members;

import java.util.Collection;

import nl.strohalm.cyclos.entities.customization.fields.CustomFieldValue;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.settings.LocalSettings.MemberResultDisplay;
import nl.strohalm.cyclos.utils.FormatObject;
import nl.strohalm.cyclos.utils.Period;
import nl.strohalm.cyclos.utils.query.QueryParameters;

/**
 * Contains data for searching elements
 * @author rafael
 */
public abstract class ElementQuery extends QueryParameters {

    private static final long                      serialVersionUID = -4552332410065261081L;

    // If it isn't not null indicates that we want the elements that can view this group
    private Long                                   viewableGroup;
    private Collection<? extends CustomFieldValue> customValues;
    private Boolean                                enabled;
    private boolean                                excludeRemoved;
    private Collection<? extends Group>            groups;
    private String                                 name;
    private String                                 username;
    private String                                 email;
    private Period                                 creationPeriod;
    private Collection<Element>                    excludeElements;
    private MemberResultDisplay                    order;
    private boolean                                randomOrder;

    public Period getCreationPeriod() {
        return creationPeriod;
    }

    public Collection<? extends CustomFieldValue> getCustomValues() {
        return customValues;
    }

    public String getEmail() {
        return email;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public Collection<Element> getExcludeElements() {
        return excludeElements;
    }

    public Collection<? extends Group> getGroups() {
        return groups;
    }

    public String getName() {
        return name;
    }

    public MemberResultDisplay getOrder() {
        return order;
    }

    public String getUsername() {
        return username;
    }

    public Long getViewableGroup() {
        return viewableGroup;
    }

    public boolean isExcludeRemoved() {
        return excludeRemoved;
    }

    public boolean isRandomOrder() {
        return randomOrder;
    }

    public void setCreationPeriod(final Period creationPeriod) {
        this.creationPeriod = creationPeriod;
    }

    public void setCustomValues(final Collection<? extends CustomFieldValue> customValues) {
        this.customValues = customValues;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public void setEnabled(final Boolean enabled) {
        this.enabled = enabled;
    }

    public void setExcludeElements(final Collection<Element> excludeElements) {
        this.excludeElements = excludeElements;
    }

    public void setExcludeRemoved(final boolean excludeRemoved) {
        this.excludeRemoved = excludeRemoved;
    }

    public void setGroups(final Collection<? extends Group> groups) {
        this.groups = groups;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setOrder(final MemberResultDisplay order) {
        this.order = order;
    }

    public void setRandomOrder(final boolean randomOrder) {
        this.randomOrder = randomOrder;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public void setViewableGroup(final Long viewableGroup) {
        this.viewableGroup = viewableGroup;
    }

    @Override
    public String toString() {
        return FormatObject.formatVO(this);
    }

}
