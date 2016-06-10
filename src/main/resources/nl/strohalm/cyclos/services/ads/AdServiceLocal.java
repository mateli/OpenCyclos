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
package nl.strohalm.cyclos.services.ads;

import java.util.Calendar;
import java.util.Collection;

import nl.strohalm.cyclos.entities.ads.Ad;
import nl.strohalm.cyclos.entities.ads.Ad.TradeType;
import nl.strohalm.cyclos.entities.groups.MemberGroup;

/**
 * Local interface. It must be used only from other services.
 */
public interface AdServiceLocal extends AdService {

    /**
     * Count for active ads with AdCategory = adCategoryId and TradeType = type and external publication = true. Used in WS.
     */
    public int countExternalAds(Long adCategoryId, TradeType type);

    /**
     * Returns the number of active members which have ads
     */
    Integer countMembersWithAds(Collection<MemberGroup> memberGroups, Calendar timePoint);

    /**
     * Returns the next advertisement which should be notified
     */
    Ad getNextAdToNotify();

    /**
     * Returns the number of ads of a given status
     * @param status The status of the ads
     * @return the number of ads
     */
    int getNumberOfAds(Collection<MemberGroup> memberGroups, Ad.Status status, Calendar timePoint);

    /**
     * Invalidates the cache for ad category counters
     */
    void invalidateCountersCache();

    /**
     * Marks this ad as members already notified
     */
    void markMembersNotified(Ad ad);

    /**
     * Generates an alert for each ad expired today
     */
    void notifyExpiredAds(Calendar time);

    /**
     * Remove the given advertisements with no permission check. Should be called from internal procedures
     */
    int remove(Long[] ids);

    /**
     * Returns the groups the logged user can see ads
     */
    Collection<MemberGroup> visibleGroupsForAds();

}
