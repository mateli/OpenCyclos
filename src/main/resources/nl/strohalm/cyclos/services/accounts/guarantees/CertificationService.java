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

import java.math.BigDecimal;
import java.util.List;

import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.Currency;
import nl.strohalm.cyclos.entities.accounts.guarantees.Certification;
import nl.strohalm.cyclos.entities.accounts.guarantees.CertificationQuery;
import nl.strohalm.cyclos.entities.accounts.guarantees.PaymentObligation;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.services.Service;

public interface CertificationService extends Service {

    public boolean canChangeStatus(final Certification certification, final Certification.Status newStatus);

    public boolean canDelete(Certification certification);

    public void changeStatus(final Long certificationId, final Certification.Status newStatus);

    /**
     * @return the unique active certification to the tuple (buyer, issuer, currency)
     */
    public Certification getActiveCertification(final Currency currency, final Member buyer, final Member issuer);

    /**
     * @return the members (Issuers) who have an (unique) active certification issued to the specified payment obligation's buyer and with the payment
     * obligation's currency.
     */
    public List<Member> getCertificationIssuers(PaymentObligation paymentObligation);

    /**
     * @param includePendingGuarantees if true include the pending guarantees in the used amount
     * @return the used certification amount according to the associated guarantees
     */
    public BigDecimal getUsedAmount(Certification certification, boolean includePendingGuarantees);

    /**
     * Loads the Certification, fetching the specified relationships.
     */
    public Certification load(Long id, Relationship... fetch);

    public int remove(Long certificationId);

    public Certification save(Certification certification);

    public List<Certification> search(CertificationQuery queryParameters);

    /**
     * @param queryParameters
     * @return the list of certifications with additional data: the certifications's used amount
     */
    public List<CertificationDTO> searchWithUsedAmount(final CertificationQuery queryParameters);

    public void validate(Certification certification);
}
