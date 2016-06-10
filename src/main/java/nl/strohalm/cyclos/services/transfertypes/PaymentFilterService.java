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
package nl.strohalm.cyclos.services.transfertypes;

import java.util.Collection;
import java.util.List;

import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.transactions.PaymentFilter;
import nl.strohalm.cyclos.entities.accounts.transactions.PaymentFilterQuery;
import nl.strohalm.cyclos.services.Service;
import nl.strohalm.cyclos.webservices.model.PaymentFilterVO;

/**
 * Service interface for payment filters
 * @author luis
 */
public interface PaymentFilterService extends Service {

    /**
     * Loads the specified payment filters, fetching the specified relationships for each one
     */
    Collection<PaymentFilter> load(final Collection<Long> ids, final Relationship... fetch);

    /**
     * Loads the specified payment filter, fetching the specified relationships
     */
    PaymentFilter load(Long id, Relationship... fetch);

    /**
     * Removes the specified payment filters
     * @return The number of removed objects
     */
    int remove(Long... ids);

    /**
     * Saves the payment filter, returning the resulting object
     */
    PaymentFilter save(PaymentFilter paymentFilter);

    /**
     * Searches existing payment filters according to the specified query parameters
     */
    List<PaymentFilter> search(PaymentFilterQuery query);

    /**
     * Converts payment filters into VOs
     * @param paymentFilters
     * @return
     */
    List<PaymentFilterVO> getPaymentFilterVOs(List<PaymentFilter> paymentFilters);

    /**
     * Validate the specified payment filter
     */
    void validate(PaymentFilter paymentFilter);
}
