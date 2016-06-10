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
import java.util.Collection;
import java.util.List;

import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.guarantees.Certification;
import nl.strohalm.cyclos.entities.accounts.guarantees.Guarantee;
import nl.strohalm.cyclos.entities.accounts.guarantees.GuaranteeQuery;
import nl.strohalm.cyclos.entities.accounts.guarantees.GuaranteeType;
import nl.strohalm.cyclos.entities.accounts.transactions.Transfer;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.services.Service;
import nl.strohalm.cyclos.utils.query.PageParameters;

public interface GuaranteeService extends Service {

    public Guarantee acceptGuarantee(Guarantee guarantee, final boolean automaticLoanAuthorization);

    /**
     * It calculates the fee's value according to the specified DTO parameters. Called by CalculateGuaranteeFeeAjaxAction
     */
    public BigDecimal calculateFee(GuaranteeFeeCalculationDTO dto);

    public boolean canChangeStatus(final Guarantee guarantee, final Guarantee.Status newStatus);

    public boolean canRemoveGuarantee(final Guarantee guarantee);

    public Guarantee changeStatus(final Long guaranteeId, final Guarantee.Status newStatus);

    /**
     * It returns the associated buyers according to the logged user. If the user is an administrator then it returns all buyer groups. If the user is
     * an issuer it returns all groups according to the 'issueCertifications' permission. Otherwise it returns all groups which contains the logged
     * user in its 'buyWithPaymentObligations' collection permission.
     */
    public Collection<? extends MemberGroup> getBuyers();

    /**
     * Returns guarantees matching the given criteria
     */
    public List<Guarantee> getGuarantees(Certification certification, PageParameters pageParameters, List<Guarantee.Status> statusList);

    /**
     * It returns the associated issuers according to the logged user. If the user is an administrator then it returns all issuer groups. If the user
     * is a buyer then returns all groups which contains the logged user in its 'issueCertifications' collection permission. Otherwise (if the logged
     * user is a seller) return all issuers according to all its buyers. There is only a "@MemberAction" annotation without permissions because all
     * the members could view the issuers.
     */
    public Collection<? extends MemberGroup> getIssuers();

    /**
     * It returns the issuer groups which has permission over the specified guarantee type.
     */
    public Collection<? extends MemberGroup> getIssuers(GuaranteeType guaranteeType);

    /**
     * Lists guarantee's models which has the logged member (or operator's own member) as buyer, seller or issuer without take into account any other
     * property.
     */
    public Collection<GuaranteeType.Model> getRelatedGuaranteeModels();

    /**
     * It returns the associated seller groups according to the logged user. If the user is an administrator then return all seller groups. If the
     * user is an issuer return all sellers according to all (buyer) groups associated to its 'issueCertifications' permission. Otherwise returns all
     * groups according to its 'buyWithPaymentObligations' permission
     */
    public Collection<? extends MemberGroup> getSellers();

    /**
     * It checks if the logged user belongs to a buyer group
     * @return true only if the logged user's group has the 'buyWithPaymentObligations' permission enabled.
     */
    public boolean isBuyer();

    /**
     * It checks if the logged user belongs to an issuer group
     * @return true only if the logged user's group has the 'issueGuarantees' permission enabled
     */
    public boolean isIssuer();

    /**
     * It checks if the logged user belongs to an seller group
     * @return true only if the logged user's group has the 'sellWithPaymentObligations' permission enabled
     */
    public boolean isSeller();

    /**
     * It loads the guarantee with the specified id, fetching the specified relationships<br>
     * <b>Security note:</b> The implementation of this method must carry out the permissions control.
     */
    public Guarantee load(Long id, Relationship... fetch);

    /**
     * @return the guarantee (if any) that generated the specified transfer.
     */
    public Guarantee loadFromTransfer(final Transfer transfer);

    /**
     * Registers a new guarantee
     */
    public Guarantee registerGuarantee(Guarantee guarantee);

    public int remove(Long guaranteeId);

    /**
     * It creates a new guarantee from a pack of payment obligations
     * @param pack a pack of payment obligations
     * @return the new created guarantee
     */
    public Guarantee requestGuarantee(PaymentObligationPackDTO pack);

    /**
     * It searches for Guarantees
     * @return the list of guarantees according to the query parameters
     */
    public List<Guarantee> search(GuaranteeQuery queryParameters);

    /**
     * It makes guarantee's validation
     * @param isAuthorization if true it validates the guarantee for authorization otherwise validates for registration
     */
    public void validate(Guarantee guarantee, boolean isAuthorization);
}
