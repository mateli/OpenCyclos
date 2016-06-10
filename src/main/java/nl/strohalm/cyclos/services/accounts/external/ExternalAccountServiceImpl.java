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
package nl.strohalm.cyclos.services.accounts.external;

import java.util.Collections;
import java.util.List;

import nl.strohalm.cyclos.dao.accounts.external.ExternalAccountDAO;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.external.ExternalAccount;
import nl.strohalm.cyclos.entities.accounts.external.ExternalAccountDetailsVO;
import nl.strohalm.cyclos.entities.accounts.external.ExternalAccountQuery;
import nl.strohalm.cyclos.entities.groups.AdminGroup;
import nl.strohalm.cyclos.services.fetch.FetchServiceLocal;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.utils.validation.Validator;

import org.apache.commons.collections.CollectionUtils;

/**
 * External Account service implementation
 * @author Lucas Geiss
 */
public class ExternalAccountServiceImpl implements ExternalAccountServiceLocal {

    private ExternalAccountDAO externalAccountDao;

    private FetchServiceLocal       fetchService;

    public List<ExternalAccountDetailsVO> externalAccountOverview() {

        AdminGroup group = LoggedUser.group();
        group = fetchService.fetch(group, AdminGroup.Relationships.VIEW_INFORMATION_OF);
        if (CollectionUtils.isEmpty(group.getViewInformationOf())) {
            return Collections.emptyList();
        } else {
            final ExternalAccountQuery query = new ExternalAccountQuery();
            query.setSystemAccountTypes(group.getViewInformationOf());
            return externalAccountDao.listExternalAccountOverview(query);
        }
    }

    public List<ExternalAccount> listAll() {
        return externalAccountDao.listAll();
    }

    public ExternalAccount load(final Long id, final Relationship... fetch) {
        return externalAccountDao.load(id, fetch);
    }

    public int remove(final Long... ids) {
        return externalAccountDao.delete(ids);
    }

    public ExternalAccount save(final ExternalAccount externalAccount) {
        validate(externalAccount);
        if (externalAccount.isTransient()) {
            return externalAccountDao.insert(externalAccount);
        } else {
            final ExternalAccount current = load(externalAccount.getId(), ExternalAccount.Relationships.FILE_MAPPING);
            externalAccount.setFileMapping(current.getFileMapping());
            return externalAccountDao.update(externalAccount);
        }
    }

    public List<ExternalAccount> search() {
        AdminGroup group = LoggedUser.group();
        group = fetchService.fetch(group, AdminGroup.Relationships.VIEW_INFORMATION_OF);
        if (CollectionUtils.isEmpty(group.getViewInformationOf())) {
            return Collections.emptyList();
        } else {
            final ExternalAccountQuery queryEA = new ExternalAccountQuery();
            queryEA.setSystemAccountTypes(group.getViewInformationOf());
            return externalAccountDao.search(queryEA);
        }

    }

    public void setExternalAccountDao(final ExternalAccountDAO externalAccountDao) {
        this.externalAccountDao = externalAccountDao;
    }

    public void setFetchServiceLocal(final FetchServiceLocal fetchService) {
        this.fetchService = fetchService;
    }

    public void validate(final ExternalAccount externalAccount) {
        getValidator().validate(externalAccount);
    }

    private Validator getValidator() {
        final Validator validator = new Validator("externalAccount");
        validator.property("name").required().maxLength(50);
        validator.property("systemAccountType").required();
        validator.property("memberAccountType").required();
        return validator;
    }

}
