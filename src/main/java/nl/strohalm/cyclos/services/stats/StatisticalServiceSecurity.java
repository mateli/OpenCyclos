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
package nl.strohalm.cyclos.services.stats;

import nl.strohalm.cyclos.access.AdminSystemPermission;
import nl.strohalm.cyclos.services.BaseServiceSecurity;

/**
 * Security implementation for {@link StatisticalService}
 * 
 * @author Rinke
 */
public abstract class StatisticalServiceSecurity extends BaseServiceSecurity implements StatisticalService {

    private StatisticalServiceLocal statisticalService;

    @Override
    public void setMaximumDataPoints(final int maximumDataPoints) {
        // no permissions needed; is a form of validation
        statisticalService.setMaximumDataPoints(maximumDataPoints);
    }

    @Override
    public void validate(final Object query) {
        // no permissions needed
        statisticalService.validate(query);
    }

    protected void checkPermission() {
        permissionService.permission().admin(AdminSystemPermission.REPORTS_STATISTICS).check();
    }

    protected StatisticalServiceLocal getStatisticalService() {
        return statisticalService;
    }

    protected void setStatisticalServiceLocal(final StatisticalServiceLocal statisticalService) {
        this.statisticalService = statisticalService;
    }

}
