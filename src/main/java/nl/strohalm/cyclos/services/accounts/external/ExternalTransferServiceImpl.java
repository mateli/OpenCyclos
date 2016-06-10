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

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import nl.strohalm.cyclos.dao.accounts.external.ExternalTransferDAO;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.access.User;
import nl.strohalm.cyclos.entities.accounts.MemberAccount;
import nl.strohalm.cyclos.entities.accounts.SystemAccountOwner;
import nl.strohalm.cyclos.entities.accounts.external.ExternalTransfer;
import nl.strohalm.cyclos.entities.accounts.external.ExternalTransferAction;
import nl.strohalm.cyclos.entities.accounts.external.ExternalTransferImport;
import nl.strohalm.cyclos.entities.accounts.external.ExternalTransferQuery;
import nl.strohalm.cyclos.entities.accounts.external.ExternalTransferType;
import nl.strohalm.cyclos.entities.accounts.loans.Loan;
import nl.strohalm.cyclos.entities.accounts.transactions.Payment;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomField;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomFieldValue;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.exceptions.UnexpectedEntityException;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.MemberQuery;
import nl.strohalm.cyclos.services.accounts.external.exceptions.CannotDeleteExternalTransferException;
import nl.strohalm.cyclos.services.accounts.external.exceptions.CannotMarkExternalTransferAsCheckedException;
import nl.strohalm.cyclos.services.accounts.external.exceptions.CannotMarkExternalTransferAsUncheckedException;
import nl.strohalm.cyclos.services.customization.MemberCustomFieldServiceLocal;
import nl.strohalm.cyclos.services.elements.ElementServiceLocal;
import nl.strohalm.cyclos.services.fetch.FetchServiceLocal;
import nl.strohalm.cyclos.services.permissions.PermissionServiceLocal;
import nl.strohalm.cyclos.services.transactions.LoanServiceLocal;
import nl.strohalm.cyclos.services.transactions.PaymentServiceLocal;
import nl.strohalm.cyclos.services.transactions.TransferDTO;
import nl.strohalm.cyclos.utils.CustomFieldHelper;
import nl.strohalm.cyclos.utils.MessageResolver;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.query.PageParameters;
import nl.strohalm.cyclos.utils.transactionimport.TransactionImportDTO;
import nl.strohalm.cyclos.utils.validation.PropertyValidation;
import nl.strohalm.cyclos.utils.validation.RequiredValidation;
import nl.strohalm.cyclos.utils.validation.ValidationError;
import nl.strohalm.cyclos.utils.validation.Validator;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Implementation for ExternalTransferService
 * 
 * @author luis
 */
public class ExternalTransferServiceImpl implements ExternalTransferServiceLocal {

    /**
     * Validates the member as required when the action is not ignored
     * 
     * @author luis
     */
    private class RequiredWhenNotIgnoreValidation implements PropertyValidation {

        private static final long serialVersionUID = -2629175142234917511L;

        @Override
        public ValidationError validate(final Object object, final Object property, final Object value) {
            final ExternalTransfer transfer = (ExternalTransfer) object;
            final ExternalTransferType type = fetchService.fetch(transfer.getType());
            if (type == null) {
                return null;
            }
            if (type.getAction() == ExternalTransferType.Action.IGNORE) {
                return null;
            } else {
                return RequiredValidation.instance().validate(object, property, value);
            }
        }

    }

    private static final Relationship[]      EXTERNAL_TRANSFER_FETCH = { RelationshipHelper.nested(ExternalTransfer.Relationships.TYPE, ExternalTransferType.Relationships.ACCOUNT), RelationshipHelper.nested(ExternalTransfer.Relationships.TYPE, ExternalTransferType.Relationships.TRANSFER_TYPE) };
    private static final Relationship[]      LOAN_FETCH              = { RelationshipHelper.nested(Loan.Relationships.TRANSFER, Payment.Relationships.TO, MemberAccount.Relationships.MEMBER), RelationshipHelper.nested(ExternalTransfer.Relationships.TYPE, ExternalTransferType.Relationships.TRANSFER_TYPE) };
    private static final Relationship[]      TRANSFER_FETCH          = { RelationshipHelper.nested(Payment.Relationships.FROM, MemberAccount.Relationships.MEMBER), RelationshipHelper.nested(Payment.Relationships.TO) };

    private LoanServiceLocal                 loanService;
    private FetchServiceLocal                fetchService;
    private PaymentServiceLocal              paymentService;
    private ElementServiceLocal              elementService;
    private MemberCustomFieldServiceLocal    memberCustomFieldService;
    private ExternalTransferTypeServiceLocal externalTransferTypeService;
    private ExternalTransferDAO              externalTransferDao;
    private CustomFieldHelper                customFieldHelper;
    private PermissionServiceLocal           permissionService;
    private MessageResolver                  messageResolver;

    @Override
    public ExternalTransfer importNew(final ExternalTransferImport transferImport, final TransactionImportDTO dto) {
        // Check if the logged user manages the member involved in the transaction.
        Member member = resolveMember(dto);
        if (member != null && !permissionService.manages(member)) {
            // The imported member is not managed by the current admin. Assume empty
            member = null;
        }

        final ExternalTransfer transfer = new ExternalTransfer();
        transfer.setLineNumber(dto.getLineNumber());
        transfer.setAccount(transferImport.getAccount());
        transfer.setMember(resolveMember(dto));
        transfer.setDate(dto.getDate());
        transfer.setAmount(dto.getAmount());
        transfer.setStatus(ExternalTransfer.Status.PENDING);
        transfer.setTransferImport(transferImport);
        transfer.setType(resolveType(transferImport, dto));
        transfer.setDescription(dto.getDescription());
        String comments = dto.getComments();

        // If the member was null and there is data about him, create import comments
        if (member == null) {
            Long id = dto.getMemberId();
            String username = dto.getMemberUsername();
            Map.Entry<String, String> fieldValue = MapUtils.isEmpty(dto.getMemberFieldValues()) ? null : dto.getMemberFieldValues().entrySet().iterator().next();
            String newComments = null;
            if (id != null) {
                newComments = messageResolver.message("externalTransferImport.error.importing.invalidMemberId", id);
            } else if (StringUtils.isNotEmpty(username)) {
                newComments = messageResolver.message("externalTransferImport.error.importing.invalidMemberUsername", username);
            } else if (fieldValue != null) {
                newComments = messageResolver.message("externalTransferImport.error.importing.invalidMemberField", fieldValue.getKey(), fieldValue.getValue());
            }
            if (comments != null) {
                comments = newComments + "\n" + comments;
            } else {
                comments = newComments;
            }
        }

        // If the payment type was null and there is a payment type code, create import comments
        if (transfer.getType() == null && StringUtils.isNotEmpty(dto.getTypeCode())) {
            String newComments = messageResolver.message("externalTransferImport.error.importing.invalidTypeCode", dto.getTypeCode());
            if (comments != null) {
                comments = newComments + "\n" + comments;
            } else {
                comments = newComments;
            }
        }

        transfer.setComments(comments);

        return externalTransferDao.insert(transfer);
    }

    @Override
    public ExternalTransfer load(final Long id, final Relationship... fetch) {
        return externalTransferDao.load(id, fetch);
    }

    @Override
    public void performAction(final ExternalTransferAction action, final Long... ids) {
        for (final Long id : ids) {
            final ExternalTransfer externalTransfer = externalTransferDao.load(id, ExternalTransfer.Relationships.TYPE);
            final ExternalTransfer.Status status = externalTransfer.getStatus();
            if (action == ExternalTransferAction.DELETE) {
                if (status != ExternalTransfer.Status.PENDING) {
                    throw new CannotDeleteExternalTransferException();
                }
                externalTransferDao.delete(id);
            } else if (action == ExternalTransferAction.MARK_AS_CHECKED) {
                if (status != ExternalTransfer.Status.PENDING || !externalTransfer.isComplete()) {
                    throw new CannotMarkExternalTransferAsCheckedException();
                }
                final ExternalTransferType type = externalTransfer.getType();
                if (type != null && type.getAction() == ExternalTransferType.Action.IGNORE) {
                    // Ignored types are processed as soon as it's checked
                    externalTransfer.setStatus(ExternalTransfer.Status.PROCESSED);
                } else {
                    externalTransfer.setStatus(ExternalTransfer.Status.CHECKED);
                }
                externalTransferDao.update(externalTransfer);
            } else { // action == ExternalTransferAction.MARK_AS_UNCHECKED
                if (status != ExternalTransfer.Status.CHECKED) {
                    throw new CannotMarkExternalTransferAsUncheckedException();
                }
                externalTransfer.setStatus(ExternalTransfer.Status.PENDING);
                externalTransferDao.update(externalTransfer);
            }
        }
    }

    @Override
    public int process(final Collection<ProcessExternalTransferDTO> dtos) throws UnexpectedEntityException {
        if (CollectionUtils.isEmpty(dtos)) {
            return 0;
        }
        int count = 0;
        for (final ProcessExternalTransferDTO dto : dtos) {
            final ExternalTransfer externalTransfer = fetchService.fetch(dto.getExternalTransfer(), EXTERNAL_TRANSFER_FETCH);
            if (externalTransfer.getStatus() != ExternalTransfer.Status.CHECKED) {
                throw new UnexpectedEntityException();
            }
            dto.setExternalTransfer(externalTransfer);
            dto.setLoan(fetchService.fetch(dto.getLoan(), LOAN_FETCH));
            dto.setTransfer(fetchService.fetch(dto.getTransfer(), TRANSFER_FETCH));
            process(dto);
            count++;
        }
        return count;
    }

    /**
     * Resolve the member of the given DTO
     */
    @Override
    @SuppressWarnings("unchecked")
    public Member resolveMember(final TransactionImportDTO dto) {
        final Long id = dto.getMemberId();
        final String username = dto.getMemberUsername();
        final Map<String, String> fieldValues = dto.getMemberFieldValues();
        Member member = null;
        // Try by id
        if (id != null) {
            try {
                member = (Member) elementService.load(id, Element.Relationships.GROUP);
            } catch (final EntityNotFoundException e) {
                // Id was not of a valid member - ignore it
            }
        }
        // Try by username
        if (member == null && StringUtils.isNotEmpty(username)) {
            try {
                member = (Member) elementService.loadUser(username, RelationshipHelper.nested(User.Relationships.ELEMENT, Element.Relationships.GROUP)).getElement();
            } catch (final EntityNotFoundException e) {
                // Username was not of a valid member - ignore it
            }
        }
        // Try by custom fields
        if (member == null && MapUtils.isNotEmpty(fieldValues)) {
            final List<MemberCustomField> allMemberFields = memberCustomFieldService.list();
            final Collection<MemberCustomFieldValue> values = customFieldHelper.buildValues(MemberCustomFieldValue.class, allMemberFields, fieldValues);

            final MemberQuery query = new MemberQuery();
            // Fetch a maximum of 2 members - we need a single one. If more than one found, none should be used
            query.setPageParameters(new PageParameters(2, 0));
            query.setCustomValues(values);
            final Iterator<Member> members = ((List<Member>) elementService.search(query)).iterator();
            if (members.hasNext()) {
                // Get the first one...
                member = members.next();
                // ... but, if there's more than one, ignore it
                if (members.hasNext()) {
                    member = null;
                }
            }
        }
        if (member != null && !member.getGroup().getStatus().isEnabled()) {
            // Cannot be a removed member
            member = null;
        }
        return member;
    }

    @Override
    public ExternalTransfer save(final ExternalTransfer externalTransfer) {
        validate(externalTransfer);
        if (externalTransfer.isTransient()) {
            externalTransfer.setStatus(ExternalTransfer.Status.PENDING);
            return externalTransferDao.insert(externalTransfer);
        } else {
            return externalTransferDao.update(externalTransfer);
        }
    }

    @Override
    public List<ExternalTransfer> search(final ExternalTransferQuery query) {
        return externalTransferDao.search(query);
    }

    public void setCustomFieldHelper(final CustomFieldHelper customFieldHelper) {
        this.customFieldHelper = customFieldHelper;
    }

    public void setElementServiceLocal(final ElementServiceLocal elementService) {
        this.elementService = elementService;
    }

    public void setExternalTransferDao(final ExternalTransferDAO externalTransferDao) {
        this.externalTransferDao = externalTransferDao;
    }

    public void setExternalTransferTypeServiceLocal(final ExternalTransferTypeServiceLocal externalTransferTypeService) {
        this.externalTransferTypeService = externalTransferTypeService;
    }

    public void setFetchServiceLocal(final FetchServiceLocal fetchService) {
        this.fetchService = fetchService;
    }

    public void setLoanServiceLocal(final LoanServiceLocal loanService) {
        this.loanService = loanService;
    }

    public void setMemberCustomFieldServiceLocal(final MemberCustomFieldServiceLocal memberCustomFieldService) {
        this.memberCustomFieldService = memberCustomFieldService;
    }

    public void setMessageResolver(final MessageResolver messageResolver) {
        this.messageResolver = messageResolver;
    }

    public void setPaymentServiceLocal(final PaymentServiceLocal paymentService) {
        this.paymentService = paymentService;
    }

    public void setPermissionServiceLocal(final PermissionServiceLocal permissionService) {
        this.permissionService = permissionService;
    }

    @Override
    public void validate(final ExternalTransfer externalTransfer) {
        getValidator().validate(externalTransfer);
    }

    private Validator getValidator() {
        final Validator validator = new Validator("externalTransfer");
        validator.property("account").required();
        validator.property("type").required();
        validator.property("date").required();
        validator.property("amount").required();
        validator.property("member").add(new RequiredWhenNotIgnoreValidation());
        return validator;
    }

    private void process(final ProcessExternalTransferDTO dto) {
        final ExternalTransfer externalTransfer = dto.getExternalTransfer();
        final ExternalTransferType type = externalTransfer.getType();
        switch (type.getAction()) {
            case GENERATE_SYSTEM_PAYMENT:
            case GENERATE_MEMBER_PAYMENT:
                final boolean toMember = type.getAction() == ExternalTransferType.Action.GENERATE_MEMBER_PAYMENT;
                final TransferDTO payment = new TransferDTO();
                payment.setAutomatic(true);
                payment.setDate(dto.getDate());
                payment.setFromOwner(toMember ? SystemAccountOwner.instance() : externalTransfer.getMember());
                payment.setToOwner(toMember ? externalTransfer.getMember() : SystemAccountOwner.instance());
                payment.setTransferType(type.getTransferType());
                payment.setAmount(dto.getAmount());
                payment.setDescription(externalTransfer.getDescription() != null ? externalTransfer.getDescription() : type.getName());
                payment.setExternalTransfer(externalTransfer);
                paymentService.insertWithNotification(payment);
                break;
            case DISCARD_LOAN:
                loanService.discardByExternalTransfer(dto.getLoan(), externalTransfer);
                break;
            case CONCILIATE_PAYMENT:
                paymentService.conciliate(dto.getTransfer(), externalTransfer);
                break;
        }
        externalTransfer.setStatus(ExternalTransfer.Status.PROCESSED);
        externalTransferDao.update(externalTransfer);
    }

    /**
     * Resolve the type of the given DTO on the given account
     */
    private ExternalTransferType resolveType(final ExternalTransferImport transferImport, final TransactionImportDTO dto) {
        try {
            return externalTransferTypeService.load(transferImport.getAccount(), dto.getTypeCode());
        } catch (final Exception e) {
            return null;
        }
    }

}
