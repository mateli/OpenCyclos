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

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.strohalm.cyclos.dao.BaseDAOImpl;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.transactions.Ticket;
import nl.strohalm.cyclos.entities.accounts.transactions.TicketQuery;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.utils.hibernate.HibernateHelper;

import org.apache.commons.lang.ArrayUtils;

/**
 * Implementation class for ticket DAO
 * @author rafael
 */
public class TicketDAOImpl extends BaseDAOImpl<Ticket> implements TicketDAO {

    public TicketDAOImpl() {
        super(Ticket.class);
    }

    @Override
    public boolean exists(final String ticket) {
        try {
            load(ticket);
            return true;
        } catch (final Exception e) {
            return false;
        }
    }

    @Override
    public <T extends Ticket> T load(final String value, final Relationship... fetch) {
        final Map<String, ?> params = Collections.singletonMap("ticket", value);
        T ticket = this.<T> uniqueResult("from " + getEntityType().getName() + " t where t.ticket = :ticket", params);
        if (ticket == null) {
            throw new EntityNotFoundException(Ticket.class);
        }
        if (ArrayUtils.isNotEmpty(fetch)) {
            ticket = getFetchDao().fetch(ticket, fetch);
        }
        return ticket;
    }

    @Override
    public List<? extends Ticket> search(final TicketQuery query) {
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        final StringBuilder hql = HibernateHelper.getInitialQuery(getEntityType(), "t", query.getFetch());
        if (query.getNature() != null) {
            HibernateHelper.addParameterToQuery(hql, namedParameters, "t.class", query.getNature().getValue());
        }
        HibernateHelper.addParameterToQuery(hql, namedParameters, "t.status", query.getStatus());
        if (query.getGroupedStatus() != null) {
            HibernateHelper.addInParameterToQuery(hql, namedParameters, "t.status", (Object[]) query.getGroupedStatus().getNormalStatus());
        }
        HibernateHelper.addParameterToQueryOperator(hql, namedParameters, "t.creationDate", "<", query.getCreatedBefore());
        HibernateHelper.addParameterToQuery(hql, namedParameters, "t.from", query.getFrom());
        HibernateHelper.addParameterToQuery(hql, namedParameters, "t.to", query.getTo());
        HibernateHelper.appendOrder(hql, "t.creationDate desc");
        return list(query, hql.toString(), namedParameters);
    }
}
