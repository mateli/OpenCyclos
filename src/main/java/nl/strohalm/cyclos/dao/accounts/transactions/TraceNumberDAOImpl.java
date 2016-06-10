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
package nl.strohalm.cyclos.dao.accounts.transactions;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import nl.strohalm.cyclos.dao.BaseDAOImpl;
import nl.strohalm.cyclos.entities.accounts.transactions.TraceNumber;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;

public class TraceNumberDAOImpl extends BaseDAOImpl<TraceNumber> implements TraceNumberDAO {

    public TraceNumberDAOImpl() {
        super(TraceNumber.class);
    }

    @Override
    public void delete(final Calendar upperBound) {
        String hql = "DELETE FROM TraceNumber t WHERE t.date <= :_date_";
        Map<String, Object> namedParameters = new HashMap<String, Object>();
        namedParameters.put("_date_", upperBound);
        bulkUpdate(hql, namedParameters);
    }

    @Override
    public TraceNumber load(final Long clientId, final String traceNumber) throws EntityNotFoundException {
        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("clientId", clientId);
        parameters.put("traceNumber", traceNumber);
        final TraceNumber tn = uniqueResult("from TraceNumber r where r.clientId = :clientId and r.traceNumber = :traceNumber", parameters);
        if (tn == null) {
            throw new EntityNotFoundException();
        }
        return tn;
    }
}
