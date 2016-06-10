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
package nl.strohalm.cyclos.entities.reports;

import nl.strohalm.cyclos.utils.DataObject;

public class AdReportVO extends DataObject {

    private static final long serialVersionUID = -8565586503274321947L;

    private Integer           numberActiveMembersWithAds;
    private Integer           numberActiveAdvertisements;
    private Integer           numberExpiredAdvertisements;
    private Integer           numberPermanentAdvertisements;
    private Integer           numberScheduledAdvertisements;

    public Integer getNumberActiveAdvertisements() {
        return numberActiveAdvertisements;
    }

    public Integer getNumberActiveMembersWithAds() {
        return numberActiveMembersWithAds;
    }

    public Integer getNumberExpiredAdvertisements() {
        return numberExpiredAdvertisements;
    }

    public Integer getNumberPermanentAdvertisements() {
        return numberPermanentAdvertisements;
    }

    public Integer getNumberScheduledAdvertisements() {
        return numberScheduledAdvertisements;
    }

    public void setNumberActiveAdvertisements(final Integer numberActiveAdvertisements) {
        this.numberActiveAdvertisements = numberActiveAdvertisements;
    }

    public void setNumberActiveMembersWithAds(final Integer numberActiveMembersWithAds) {
        this.numberActiveMembersWithAds = numberActiveMembersWithAds;
    }

    public void setNumberExpiredAdvertisements(final Integer numberExpiredAdvertisements) {
        this.numberExpiredAdvertisements = numberExpiredAdvertisements;
    }

    public void setNumberPermanentAdvertisements(final Integer numberPermanentAdvertisements) {
        this.numberPermanentAdvertisements = numberPermanentAdvertisements;
    }

    public void setNumberScheduledAdvertisements(final Integer numberScheduledAdvertisements) {
        this.numberScheduledAdvertisements = numberScheduledAdvertisements;
    }

}
