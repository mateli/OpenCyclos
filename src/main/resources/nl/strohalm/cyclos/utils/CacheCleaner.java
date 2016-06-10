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
package nl.strohalm.cyclos.utils;

import nl.strohalm.cyclos.services.fetch.FetchService;

/**
 * A helper class that invokes {@link FetchService#clearCache()} when needed
 * 
 * @author luis
 */
public class CacheCleaner {
    private static final int   COUNT = 20;
    private int                invocations;
    private final FetchService fetchService;

    public CacheCleaner(final FetchService fetchService) {
        this.fetchService = fetchService;
    }

    public void clearCache() {
        invocations++;
        if (invocations % COUNT == 0) {
            fetchService.clearCache();
        }
    }

    public int getInvocations() {
        return invocations;
    }

    public void setInvocations(final int invocations) {
        this.invocations = invocations;
    }
}
