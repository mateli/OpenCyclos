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
package nl.strohalm.cyclos.services.accounts.guarantees;

import java.util.Calendar;
import java.util.List;

import nl.strohalm.cyclos.entities.accounts.guarantees.Guarantee;

/**
 * Local interface. It must be used only from other services.
 */
public interface GuaranteeServiceLocal extends GuaranteeService {

    /**
     * Used from scheduled task: it searches for the guarantees to change its status This method is used in conjunction with the processGuarantees
     * method
     */
    public List<Guarantee> guaranteesToProcess(Calendar time);

    /**
     * Used from scheduled task: it changes the guarantee's status
     * @see #guaranteesToProcess(Calendar)
     */
    public Guarantee processGuarantee(Guarantee guarantee, Calendar time);

    /**
     * Used from scheduled task: It generates a new loan for each guarantee which status is ACCEPTED and become valid before or at the time parameter
     * @param time the times used as the current time
     */
    public void processGuaranteeLoans(Calendar time);

}
