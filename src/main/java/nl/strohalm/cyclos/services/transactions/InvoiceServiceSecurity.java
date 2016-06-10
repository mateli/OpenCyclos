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
package nl.strohalm.cyclos.services.transactions;

import java.util.List;

import nl.strohalm.cyclos.access.AdminMemberPermission;
import nl.strohalm.cyclos.access.BrokerPermission;
import nl.strohalm.cyclos.access.MemberPermission;
import nl.strohalm.cyclos.access.OperatorPermission;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.AccountOwner;
import nl.strohalm.cyclos.entities.accounts.SystemAccountOwner;
import nl.strohalm.cyclos.entities.accounts.transactions.Invoice;
import nl.strohalm.cyclos.entities.accounts.transactions.InvoiceQuery;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.exceptions.UnexpectedEntityException;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.BaseServiceSecurity;
import nl.strohalm.cyclos.services.transactions.exceptions.MaxAmountPerDayExceededException;
import nl.strohalm.cyclos.services.transactions.exceptions.NotEnoughCreditsException;
import nl.strohalm.cyclos.services.transactions.exceptions.UpperCreditLimitReachedException;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.utils.access.PermissionHelper;
import nl.strohalm.cyclos.utils.validation.ValidationException;

/**
 * Security implementation for {@link InvoiceService}
 * 
 * @author luis
 */
public class InvoiceServiceSecurity extends BaseServiceSecurity implements InvoiceService {

    private InvoiceServiceLocal invoiceService;

    @Override
    public Invoice accept(final Invoice inputInvoice) throws NotEnoughCreditsException, UpperCreditLimitReachedException, MaxAmountPerDayExceededException, UnexpectedEntityException {
        Invoice invoice = fetch(inputInvoice);
        if (invoice.isToSystem()) {
            // From member to system
            permissionService.permission(invoice.getFromMember())
                    .admin(AdminMemberPermission.INVOICES_ACCEPT)
                    .check();
        } else if (invoice.isFromSystem()) {
            // From system to member
            permissionService.permission(invoice.getToMember())
                    .admin(AdminMemberPermission.INVOICES_ACCEPT_AS_MEMBER_FROM_SYSTEM)
                    .broker(BrokerPermission.INVOICES_ACCEPT_AS_MEMBER_FROM_SYSTEM)
                    .member() // There is no permission for a member to manage his own invoices
                    .operator(OperatorPermission.INVOICES_MANAGE)
                    .check();
        } else {
            // From member to member
            permissionService.permission(invoice.getToMember())
                    .admin(AdminMemberPermission.INVOICES_ACCEPT_AS_MEMBER_FROM_MEMBER)
                    .broker(BrokerPermission.INVOICES_ACCEPT_AS_MEMBER_FROM_MEMBER)
                    .member() // There is no permission for a member to manage his own invoices
                    .operator(OperatorPermission.INVOICES_MANAGE)
                    .check();
        }
        // We should pass the original invoice, as it should contain the selected transfer type, and the current loaded invoice may have a null TT
        return invoiceService.accept(inputInvoice);
    }

    @Override
    public boolean canAccept(final Invoice invoice) {
        return invoiceService.canAccept(invoice);
    }

    @Override
    public boolean canCancel(final Invoice invoice) {
        return invoiceService.canCancel(invoice);
    }

    @Override
    public Invoice cancel(Invoice invoice) throws UnexpectedEntityException {
        invoice = fetch(invoice);
        if (invoice.isFromSystem()) {
            // From system to member
            permissionService.permission(invoice.getToMember())
                    .admin(AdminMemberPermission.INVOICES_CANCEL)
                    .check();
        } else {
            // From member
            permissionService.permission(invoice.getFromMember())
                    .admin(AdminMemberPermission.INVOICES_CANCEL_AS_MEMBER)
                    .broker(BrokerPermission.INVOICES_CANCEL_AS_MEMBER)
                    .member() // There is no permission for a member to manage his own invoices
                    .operator(OperatorPermission.INVOICES_MANAGE)
                    .check();
        }
        return invoiceService.cancel(invoice);
    }

    @Override
    public boolean canDeny(final Invoice invoice) {
        return invoiceService.canDeny(invoice);
    }

    @Override
    public Invoice deny(Invoice invoice) throws UnexpectedEntityException, PermissionDeniedException {
        invoice = fetch(invoice);
        if (invoice.isToSystem()) {
            // From member to system
            permissionService.permission(invoice.getFromMember())
                    .admin(AdminMemberPermission.INVOICES_DENY)
                    .check();
        } else if (invoice.isFromSystem()) {
            // Invoices from system to member cannot be denied
            throw new PermissionDeniedException();
        } else {
            // From member to member
            permissionService.permission(invoice.getToMember())
                    .admin(AdminMemberPermission.INVOICES_DENY_AS_MEMBER)
                    .broker(BrokerPermission.INVOICES_DENY_AS_MEMBER)
                    .member() // There is no permission for a member to manage his own invoices
                    .operator(OperatorPermission.INVOICES_MANAGE)
                    .check();
        }
        return invoiceService.deny(invoice);
    }

    @Override
    public List<TransferType> getPossibleTransferTypes(Invoice invoice) {
        invoice = fetch(invoice);
        checkView(invoice);
        return invoiceService.getPossibleTransferTypes(invoice);
    }

    @Override
    public Invoice load(final Long id, final Relationship... fetch) {
        Invoice invoice = invoiceService.load(id, addToFetch(fetch, Invoice.Relationships.FROM_MEMBER, Invoice.Relationships.TO_MEMBER));
        checkView(invoice);
        return invoice;
    }

    @Override
    public List<Invoice> search(final InvoiceQuery query) {
        AccountOwner owner = query.getOwner();
        if (owner == null) {
            // An owner is required here
            throw new ValidationException();
        }
        if (owner instanceof SystemAccountOwner) {
            // Only admins can view system invoices
            permissionService.permission().admin(AdminMemberPermission.INVOICES_VIEW).check();
        } else {
            // A member's invoices
            permissionService.permission((Member) owner)
                    .admin(AdminMemberPermission.INVOICES_VIEW)
                    .broker(BrokerPermission.INVOICES_VIEW)
                    .member()
                    .operator(OperatorPermission.INVOICES_VIEW)
                    .check();
        }
        Member relatedMember = query.getRelatedMember();
        if (relatedMember != null) {
            permissionService.checkRelatesTo(relatedMember);
        }
        if (LoggedUser.isAdministrator()) {
            // Only admins can filter by group - Ensure the groups are visible
            query.setGroups(PermissionHelper.checkSelection(permissionService.getVisibleMemberGroups(), query.getGroups()));
        }
        return invoiceService.search(query);
    }

    @Override
    public Invoice send(final Invoice invoice) throws UnexpectedEntityException {
        if (invoice.isFromSystem()) {
            // From system to member
            Member toMember = invoice.getToMember();
            if (toMember == null) {
                // We need a to member here
                throw new ValidationException();
            }
            permissionService.permission(toMember)
                    .admin(AdminMemberPermission.INVOICES_SEND)
                    .check();
        } else if (invoice.isToSystem()) {
            // From member to system
            permissionService.permission(invoice.getFromMember())
                    .admin(AdminMemberPermission.INVOICES_SEND_AS_MEMBER_TO_SYSTEM)
                    .broker(BrokerPermission.INVOICES_SEND_AS_MEMBER_TO_SYSTEM)
                    .member(MemberPermission.INVOICES_SEND_TO_SYSTEM)
                    .operator(OperatorPermission.INVOICES_SEND_TO_SYSTEM)
                    .check();
        } else {
            // From member to member
            permissionService.permission(invoice.getFromMember())
                    .admin(AdminMemberPermission.INVOICES_SEND_AS_MEMBER_TO_MEMBER)
                    .broker(BrokerPermission.INVOICES_SEND_AS_MEMBER_TO_MEMBER)
                    .member(MemberPermission.INVOICES_SEND_TO_MEMBER)
                    .operator(OperatorPermission.INVOICES_SEND_TO_MEMBER)
                    .check();
            permissionService.checkRelatesTo(invoice.getToMember());
        }
        return invoiceService.send(invoice);
    }

    public void setInvoiceServiceLocal(final InvoiceServiceLocal invoiceService) {
        this.invoiceService = invoiceService;
    }

    @Override
    public void validate(final Invoice invoice) {
        // No permission needed for validate
        invoiceService.validate(invoice);
    }

    @Override
    public void validateForAccept(final Invoice invoice) {
        // No permission needed for validate
        invoiceService.validateForAccept(invoice);
    }

    private void checkView(final Invoice invoice) {
        // To view, either the from owner or to owner must be managed
        boolean manageFrom;
        if (invoice.isFromSystem()) {
            manageFrom = permissionService.permission().admin(AdminMemberPermission.INVOICES_VIEW).hasPermission();
        } else {
            manageFrom = permissionService.permission(invoice.getFromMember())
                    .admin(AdminMemberPermission.INVOICES_VIEW)
                    .broker(BrokerPermission.INVOICES_VIEW)
                    .member()
                    .operator(OperatorPermission.INVOICES_VIEW)
                    .hasPermission();
        }
        if (manageFrom) {
            return;
        }
        boolean manageTo;
        if (invoice.isToSystem()) {
            manageTo = permissionService.permission().admin(AdminMemberPermission.INVOICES_VIEW).hasPermission();
        } else {
            manageTo = permissionService.permission(invoice.getToMember())
                    .admin(AdminMemberPermission.INVOICES_VIEW)
                    .broker(BrokerPermission.INVOICES_VIEW)
                    .member()
                    .operator(OperatorPermission.INVOICES_VIEW)
                    .hasPermission();
        }
        if (!manageTo) {
            throw new PermissionDeniedException();
        }
    }

    private Invoice fetch(Invoice invoice) {
        invoice = fetchService.fetch(invoice, Invoice.Relationships.TO_MEMBER, Invoice.Relationships.FROM_MEMBER);
        return invoice;
    }

}
