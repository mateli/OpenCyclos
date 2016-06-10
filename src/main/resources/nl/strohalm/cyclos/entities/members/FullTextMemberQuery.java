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

import nl.strohalm.cyclos.entities.groups.GroupFilter;
import nl.strohalm.cyclos.entities.members.Element.Nature;
import nl.strohalm.cyclos.entities.settings.LocalSettings.SortOrder;
import nl.strohalm.cyclos.utils.Period;

/**
 * Query parameters for full-text member searches
 * @author luis
 */
public class FullTextMemberQuery extends FullTextElementQuery {

    private static final long       serialVersionUID = -139939459605872187L;
    private Member                  broker;
    private Period                  activationPeriod;
    private Collection<GroupFilter> groupFilters;
    private SortOrder               memberSortOrder;
    private boolean                 withImagesOnly;

    public Period getActivationPeriod() {
        return activationPeriod;
    }

    public Member getBroker() {
        return broker;
    }

    public Collection<GroupFilter> getGroupFilters() {
        return groupFilters;
    }

    public SortOrder getMemberSortOrder() {
        return memberSortOrder;
    }

    @Override
    public Nature getNature() {
        return Nature.MEMBER;
    }

    @Override
    public Class<? extends ElementQuery> getQueryClass() {
        return MemberQuery.class;
    }

    public boolean isWithImagesOnly() {
        return withImagesOnly;
    }

    public void setActivationPeriod(final Period activationPeriod) {
        this.activationPeriod = activationPeriod;
    }

    public void setBroker(final Member broker) {
        this.broker = broker;
    }

    public void setGroupFilters(final Collection<GroupFilter> groupFilters) {
        this.groupFilters = groupFilters;
    }

    public void setMemberSortOrder(final SortOrder memberSortOrder) {
        this.memberSortOrder = memberSortOrder;
    }

    public void setWithImagesOnly(final boolean withImagesOnly) {
        this.withImagesOnly = withImagesOnly;
    }

}
