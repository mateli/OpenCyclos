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
package nl.strohalm.cyclos.services.loangroups;

import java.util.Collection;
import java.util.List;

import nl.strohalm.cyclos.dao.accounts.loans.LoanGroupDAO;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.loans.LoanGroup;
import nl.strohalm.cyclos.entities.accounts.loans.LoanGroupQuery;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.services.customization.LoanGroupCustomFieldServiceLocal;
import nl.strohalm.cyclos.services.fetch.FetchServiceLocal;
import nl.strohalm.cyclos.services.loangroups.exceptions.LoanGroupHasLoansException;
import nl.strohalm.cyclos.services.loangroups.exceptions.MemberAlreadyInListException;
import nl.strohalm.cyclos.services.loangroups.exceptions.MemberNotInListException;
import nl.strohalm.cyclos.utils.validation.Validator;

/**
 * Implementation for loan group service
 * @author luis
 */
public class LoanGroupServiceImpl implements LoanGroupServiceLocal {

    private LoanGroupCustomFieldServiceLocal loanGroupLoanGroupCustomFieldService;
    private FetchServiceLocal                fetchService;
    private LoanGroupDAO                     loanGroupDao;

    @Override
    public void addMember(final Member member, LoanGroup loanGroup) {
        loanGroup = fetchService.fetch(loanGroup, LoanGroup.Relationships.MEMBERS);
        final Collection<Member> members = loanGroup.getMembers();
        if (members.contains(member)) {
            throw new MemberAlreadyInListException();
        }
        members.add(member);
        loanGroupDao.update(loanGroup);
    }

    @Override
    public LoanGroup load(final Long id, final Relationship... fetch) {
        return loanGroupDao.load(id, fetch);
    }

    @Override
    public int remove(final Long... ids) {
        for (final Long id : ids) {
            final LoanGroup lg = new LoanGroup();
            lg.setId(id);
            failIfHasLoans(lg);
        }
        return loanGroupDao.delete(ids);
    }

    @Override
    public void removeMember(final Member member, LoanGroup loanGroup) {
        loanGroup = fetchService.fetch(loanGroup, LoanGroup.Relationships.MEMBERS);
        final Collection<Member> members = loanGroup.getMembers();
        if (!members.contains(member)) {
            throw new MemberNotInListException();
        }
        members.remove(member);
        loanGroupDao.update(loanGroup);
    }

    @Override
    public LoanGroup save(LoanGroup loanGroup) {
        validate(loanGroup);
        if (loanGroup.isTransient()) {
            loanGroup = loanGroupDao.insert(loanGroup);
        } else {
            final LoanGroup toSave = load(loanGroup.getId(), LoanGroup.Relationships.CUSTOM_VALUES, LoanGroup.Relationships.LOANS, LoanGroup.Relationships.MEMBERS);
            if (toSave.getLoans() != null && !toSave.getLoans().isEmpty()) {
                throw new LoanGroupHasLoansException();
            }
            toSave.setDescription(loanGroup.getDescription());
            toSave.setName(loanGroup.getName());
            loanGroup = loanGroupDao.update(toSave);
        }
        loanGroupLoanGroupCustomFieldService.saveValues(loanGroup);
        return loanGroup;
    }

    @Override
    public List<LoanGroup> search(final LoanGroupQuery query) {
        return loanGroupDao.search(query);
    }

    public void setFetchServiceLocal(final FetchServiceLocal fetchService) {
        this.fetchService = fetchService;
    }

    public void setLoanGroupCustomFieldServiceLocal(final LoanGroupCustomFieldServiceLocal loanGroupLoanGroupCustomFieldService) {
        this.loanGroupLoanGroupCustomFieldService = loanGroupLoanGroupCustomFieldService;
    }

    public void setLoanGroupDao(final LoanGroupDAO loanGroupDao) {
        this.loanGroupDao = loanGroupDao;
    }

    @Override
    public void validate(final LoanGroup loanGroup) {
        getValidator().validate(loanGroup);
    }

    private void failIfHasLoans(LoanGroup loanGroup) {
        loanGroup = fetchService.fetch(loanGroup, LoanGroup.Relationships.LOANS);
        if (loanGroup != null && loanGroup.getLoans() != null && !loanGroup.getLoans().isEmpty()) {
            throw new LoanGroupHasLoansException();
        }
    }

    private Validator getValidator() {
        final Validator validator = new Validator("loanGroup");
        validator.property("name").required().maxLength(100);
        validator.property("description").maxLength(1000);
        return validator;
    }

}
