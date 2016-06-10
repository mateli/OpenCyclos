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
package nl.strohalm.cyclos.services.elements;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import nl.strohalm.cyclos.access.AdminMemberPermission;
import nl.strohalm.cyclos.access.MemberPermission;
import nl.strohalm.cyclos.access.OperatorPermission;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.transactions.Payment;
import nl.strohalm.cyclos.entities.accounts.transactions.PaymentAwaitingFeedbackDTO;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.GeneralReference;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.PaymentsAwaitingFeedbackQuery;
import nl.strohalm.cyclos.entities.members.Reference;
import nl.strohalm.cyclos.entities.members.Reference.Level;
import nl.strohalm.cyclos.entities.members.Reference.Nature;
import nl.strohalm.cyclos.entities.members.ReferenceQuery;
import nl.strohalm.cyclos.entities.members.TransactionFeedback;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.BaseServiceSecurity;
import nl.strohalm.cyclos.utils.Period;
import nl.strohalm.cyclos.utils.access.PermissionHelper;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.commons.collections.CollectionUtils;

/**
 * Security implementation for {@link ReferenceService}
 * 
 * @author jcomas
 */
public class ReferenceServiceSecurity extends BaseServiceSecurity implements ReferenceService {

    private ReferenceServiceLocal referenceService;

    @Override
    public boolean canGiveGeneralReference(final Member member) {
        // Nothing to check.
        return referenceService.canGiveGeneralReference(member);
    }

    @Override
    public boolean canManage(final Reference ref) {
        // Nothing to check.
        return referenceService.canManage(ref);
    }

    @Override
    public boolean canManageGeneralReference(final Member member) {
        // Nothing to check
        return referenceService.canManageGeneralReference(member);
    }

    @Override
    public boolean canReplyFeedbackNow(final TransactionFeedback transactionFeedback) {
        // Nothing to check.
        return referenceService.canReplyFeedbackNow(transactionFeedback);
    }

    @Override
    public Map<Level, Integer> countReferencesByLevel(final Reference.Nature nature, final Member member, final boolean received) {
        permissionService.checkRelatesTo(member);
        return referenceService.countReferencesByLevel(nature, member, received);
    }

    @Override
    public Map<Level, Integer> countReferencesHistoryByLevel(final Reference.Nature nature, final Member member, final Period date, final boolean received) {
        permissionService.checkRelatesTo(member);
        return referenceService.countReferencesHistoryByLevel(nature, member, date, received);
    }

    @Override
    public Collection<Nature> getNaturesByGroup(final MemberGroup group) {
        if (!permissionService.getAllVisibleGroups().contains(group)) {
            throw new PermissionDeniedException();
        }
        return referenceService.getNaturesByGroup(group);
    }

    @Override
    public TransactionFeedbackAction getPossibleAction(final TransactionFeedback transactionFeedback) {
        // Nothing to check.
        return referenceService.getPossibleAction(transactionFeedback);
    }

    @Override
    public Reference load(final Long id, final Relationship... fetch) throws EntityNotFoundException {
        Reference ref = referenceService.load(id, fetch);
        checkView(ref);
        return ref;
    }

    @Override
    public GeneralReference loadGeneral(final Member from, final Member to, final Relationship... fetch) throws EntityNotFoundException {
        GeneralReference ref = referenceService.loadGeneral(from, to, fetch);
        checkView(ref);
        return ref;
    }

    @Override
    public TransactionFeedback loadTransactionFeedback(final Payment payment, final Relationship... fetch) throws EntityNotFoundException {
        TransactionFeedback tf = referenceService.loadTransactionFeedback(payment, fetch);
        checkView(tf);
        return tf;
    }

    @Override
    public int remove(final Long... ids) {
        for (Long id : ids) {
            Reference ref = referenceService.load(id);
            if (ref instanceof TransactionFeedback) {
                throw new PermissionDeniedException();
            }
            checkManage(ref);
        }
        return referenceService.remove(ids);
    }

    @Override
    public GeneralReference save(final GeneralReference reference) {
        checkManage(reference);
        return referenceService.save(reference);
    }

    @Override
    public TransactionFeedback save(final TransactionFeedback transactionFeedback) {
        checkManage(transactionFeedback);
        return referenceService.save(transactionFeedback);
    }

    @Override
    public List<? extends Reference> search(final ReferenceQuery query) {
        if (!applyQueryRestrictions(query)) {
            return Collections.emptyList();
        }
        return referenceService.search(query);
    }

    @Override
    public List<PaymentAwaitingFeedbackDTO> searchPaymentsAwaitingFeedback(final PaymentsAwaitingFeedbackQuery query) {
        if (query.getMember() != null) {
            permissionService.checkManages(query.getMember());
        }
        return referenceService.searchPaymentsAwaitingFeedback(query);
    }

    public void setReferenceServiceLocal(final ReferenceServiceLocal referenceService) {
        this.referenceService = referenceService;
    }

    @Override
    public void validate(final Reference reference) throws ValidationException {
        // Nothing to check.
        referenceService.validate(reference);
    }

    private boolean applyQueryRestrictions(final ReferenceQuery query) {

        if (!CollectionUtils.isEmpty(query.getGroups())) {
            query.setGroups(PermissionHelper.checkSelection(permissionService.getAllVisibleGroups(), query.getGroups()));
            if (query.getGroups().isEmpty()) {
                return false;
            }
        } else {
            Collection<Group> groups = new ArrayList<Group>();
            groups.addAll(permissionService.getAllVisibleGroups());
            query.setGroups(groups);
        }
        return hasViewPermission(query.getNature());
    }

    private void checkManage(final Reference ref) {
        if (!canManage(ref)) {
            throw new PermissionDeniedException();
        }
    }

    private void checkView(final Reference ref) {
        if (!(permissionService.relatesTo(ref.getFrom()) || permissionService.relatesTo(ref.getTo())) || !hasViewPermission(ref.getNature())) {
            throw new PermissionDeniedException();
        }
    }

    /**
     * Returns true if the logged user has the corresponding permissions to view the general reference or the transaction fee.
     * @param nature The reference nature
     * @return
     */
    private boolean hasViewPermission(final Nature nature) {
        switch (nature) {
            case GENERAL:
                return permissionService.permission()
                        .admin(AdminMemberPermission.REFERENCES_VIEW)
                        .member(MemberPermission.REFERENCES_VIEW) // Brokers are allowed to see a reference if they have the member's REFERENCES_VIEW
                        .operator(OperatorPermission.REFERENCES_VIEW).hasPermission();

            case TRANSACTION:
                return permissionService.permission()
                        .admin(AdminMemberPermission.TRANSACTION_FEEDBACKS_VIEW)
                        .member()
                        .operator(OperatorPermission.REFERENCES_MANAGE_MEMBER_TRANSACTION_FEEDBACKS).hasPermission();
            default:
                return false;
        }
    }

}
