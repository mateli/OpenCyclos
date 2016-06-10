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

import nl.strohalm.cyclos.dao.accounts.transactions.PaymentFilterDAO;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.transactions.PaymentFilter;
import nl.strohalm.cyclos.entities.accounts.transactions.PaymentFilterQuery;
import nl.strohalm.cyclos.utils.validation.Validator;
import nl.strohalm.cyclos.webservices.model.PaymentFilterVO;
import nl.strohalm.cyclos.webservices.utils.AccountHelper;

/**
 * Implementation class for payment filter service
 * @author rafael
 */
public class PaymentFilterServiceImpl implements PaymentFilterServiceLocal {

    private PaymentFilterDAO paymentFilterDao;
    private AccountHelper    accountHelper;

    @Override
    public Collection<PaymentFilter> load(final Collection<Long> ids, final Relationship... fetch) {
        return paymentFilterDao.load(ids, fetch);
    }

    @Override
    public PaymentFilter load(final Long id, final Relationship... fetch) {
        return paymentFilterDao.load(id, fetch);
    }

    @Override
    public int remove(final Long... ids) {
        return paymentFilterDao.delete(ids);
    }

    @Override
    public PaymentFilter save(final PaymentFilter paymentFilter) {
        validate(paymentFilter);
        if (paymentFilter.isTransient()) {
            return paymentFilterDao.insert(paymentFilter);
        } else {
            return paymentFilterDao.update(paymentFilter);
        }
    }

    @Override
    public List<PaymentFilter> search(final PaymentFilterQuery query) {
        return paymentFilterDao.search(query);
    }

    public void setAccountHelper(final AccountHelper accountHelper) {
        this.accountHelper = accountHelper;
    }

    public void setPaymentFilterDao(final PaymentFilterDAO paymentFilterDao) {
        this.paymentFilterDao = paymentFilterDao;
    }

    @Override
    public List<PaymentFilterVO> getPaymentFilterVOs(final List<PaymentFilter> paymentFilters) {
        return accountHelper.toPaymentFilterVOs(paymentFilters);
    }

    @Override
    public void validate(final PaymentFilter paymentFilter) {
        getValidator().validate(paymentFilter);
    }

    private Validator getValidator() {
        final Validator validator = new Validator("paymentFilter");
        validator.property("name").required().maxLength(100);
        validator.property("description").maxLength(1000);
        return validator;
    }

}
