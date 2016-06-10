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

/**
 * The possible statistical categories which can be shown in any statistical form. In the action's <code>prepareForm</code> method you can decide to
 * set this via
 * 
 * <pre>
 * request.setAttribute(&quot;whatToShow&quot;, Arrays.asList(StatisticsWhatToShow.COMPARE_PERIODS, StatisticsWhatToShow.THROUGH_TIME));
 * </pre>
 * 
 * This would only allow the listed options. If you do not set this in the action, then all options are shown in the whatToShow dropdown.
 * 
 * @author luis
 */
public enum StatisticsWhatToShow {
    SINGLE_PERIOD, COMPARE_PERIODS, THROUGH_TIME, DISTRIBUTION
}
