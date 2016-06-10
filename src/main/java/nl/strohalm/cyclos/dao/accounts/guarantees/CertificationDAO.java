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
package nl.strohalm.cyclos.dao.accounts.guarantees;

import java.math.BigDecimal;
import java.util.List;

import nl.strohalm.cyclos.dao.BaseDAO;
import nl.strohalm.cyclos.dao.DeletableDAO;
import nl.strohalm.cyclos.dao.InsertableDAO;
import nl.strohalm.cyclos.dao.UpdatableDAO;
import nl.strohalm.cyclos.entities.accounts.Currency;
import nl.strohalm.cyclos.entities.accounts.guarantees.Certification;
import nl.strohalm.cyclos.entities.accounts.guarantees.CertificationQuery;
import nl.strohalm.cyclos.entities.exceptions.DaoException;
import nl.strohalm.cyclos.entities.members.Member;

/**
 * Interface for certification DAO
 * @author ameyer
 * 
 */
public interface CertificationDAO extends BaseDAO<Certification>, InsertableDAO<Certification>, UpdatableDAO<Certification>, DeletableDAO<Certification> {

    /**
     * @return the active certifications issued to the specified buyer and according to the currency There is only one active certification by issuer
     */
    public List<Certification> getActiveCertificationsForBuyer(Member buyer, Currency currency);

    /**
     * This method must be used only if the loan repay doesn't modify the certification's used amount It only takes into account the guarantee status
     * (not the repay)
     * @param certification
     * @return the used certification amount according to the associated guarantees
     * @throws DaoException
     */
    public BigDecimal getUsedAmount(Certification certification) throws DaoException;

    public List<Certification> seach(CertificationQuery queryParameters) throws DaoException;
}
