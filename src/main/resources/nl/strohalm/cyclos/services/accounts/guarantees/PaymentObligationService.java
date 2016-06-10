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

import java.util.List;

import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.guarantees.PaymentObligation;
import nl.strohalm.cyclos.entities.accounts.guarantees.PaymentObligationQuery;
import nl.strohalm.cyclos.services.Service;

public interface PaymentObligationService extends Service {

    public boolean canChangeStatus(final PaymentObligation paymentObligation, final PaymentObligation.Status newStatus);

    public boolean canDelete(PaymentObligation paymentObligation);

    public PaymentObligation changeStatus(final Long paymentObligationId, final PaymentObligation.Status newStatus);

    /**
     * @param dto
     * @return the payment obligation's ids of these that exceed the payment obligation period of the guarantee type associated to the (there is only
     * one) active issuer's certification. the periods' bounds are: inclusive, exclusive
     */
    public Long[] checkPaymentObligationPeriod(PaymentObligationPackDTO dto);

    /**
     * @return the status showed in the search payment obligation page allowed to the logged user (e.g. in case of seller group this method restricts
     * the states)
     */
    public PaymentObligation.Status[] getStatusToFilter();

    /**
     * Loads the Payment Obligation, fetching the specified relationships.
     */
    public PaymentObligation load(Long id, Relationship... fetch);

    public int remove(Long paymentObligationId);

    /**
     * Saves the payment obligation
     */
    public PaymentObligation save(PaymentObligation paymentObligation, boolean validateBeforeSave);

    public List<PaymentObligation> search(PaymentObligationQuery queryParameters);

    public void validate(PaymentObligation paymentObligation);
}
