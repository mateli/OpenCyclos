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
package nl.strohalm.cyclos.controls.accounts.transfertypes;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseAction;
import nl.strohalm.cyclos.entities.access.Channel;
import nl.strohalm.cyclos.entities.accounts.AccountOwner;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferTypeQuery;
import nl.strohalm.cyclos.entities.customization.fields.PaymentCustomField;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.services.customization.PaymentCustomFieldService;
import nl.strohalm.cyclos.services.transfertypes.TransferTypeService;
import nl.strohalm.cyclos.utils.StringHelper;
import nl.strohalm.cyclos.utils.conversion.CoercionHelper;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.struts.action.ActionForward;

/**
 * Action used to retrieve the HTML code for custom fields of a payment, given a transfer type as argument
 * 
 * @author luis
 */
public class PaymentCustomFieldsAjaxAction extends BaseAction {

    private PaymentCustomFieldService paymentCustomFieldService;
    private TransferTypeService       transferTypeService;

    @Inject
    public void setPaymentCustomFieldService(final PaymentCustomFieldService paymentCustomFieldService) {
        this.paymentCustomFieldService = paymentCustomFieldService;
    }

    @Inject
    public void setTransferTypeService(final TransferTypeService transferTypeService) {
        this.transferTypeService = transferTypeService;
    }

    @Override
    protected ActionForward executeAction(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();
        final TransferType transferType = resolveTransferType(context);
        if (transferType == null) {
            // TT not found
            return null;
        }

        final Collection<PaymentCustomField> customFields = paymentCustomFieldService.list(transferType, false);
        if (CollectionUtils.isEmpty(customFields)) {
            // No custom fields
            return null;
        }

        // Set some specific use cases attributes
        final boolean isForLoan = CoercionHelper.coerce(boolean.class, request.getParameter("forLoan"));
        final String fieldName = isForLoan ? "loan(customValues).field" : "customValues.field";
        final String valueName = isForLoan ? "loan(customValues).value" : "customValues.value";
        request.setAttribute("isForLoan", isForLoan);
        request.setAttribute("fieldName", fieldName);
        request.setAttribute("valueName", valueName);
        request.setAttribute("columnWidth", ObjectUtils.defaultIfNull(StringHelper.removeMarkupTags(request.getParameter("columnWidth")), "25%"));

        // Delegate the rendering to a jsp
        request.setAttribute("customFields", customFields);
        context.getResponse().setContentType("text/html");
        return new ActionForward("/pages/payments/paymentCustomFields.jsp", false);
    }

    private TransferType resolveTransferType(final ActionContext context) {
        final HttpServletRequest request = context.getRequest();

        // Try by transfer type directly
        final TransferType transferType = CoercionHelper.coerce(TransferType.class, request.getParameter("typeId"));
        if (transferType != null) {
            return transferType;
        }

        // Try by invoice details: destination account type, currencty, from and to
        final AccountType accountType = CoercionHelper.coerce(AccountType.class, request.getParameter("destinationAccountTypeId"));
        final Member fromMember = CoercionHelper.coerce(Member.class, request.getParameter("fromId"));
        final AccountOwner from = fromMember != null ? fromMember : context.getAccountOwner();
        final Member toMember = CoercionHelper.coerce(Member.class, request.getParameter("toId"));
        final AccountOwner to = toMember != null ? toMember : context.getAccountOwner();

        // Find the first TT with the matching criteria. It's important that only return one if A SINGLE TT is found, because when there are multiple
        // possible TTs, custom fields won't be used
        final TransferTypeQuery query = new TransferTypeQuery();
        query.setUsePriority(true);
        query.setChannel(Channel.WEB);
        query.setFromOwner(from);
        query.setToOwner(to);
        query.setToAccountType(accountType);
        final List<TransferType> transferTypes = transferTypeService.search(query);
        if (transferTypes.size() == 1) {
            return transferTypes.iterator().next();
        }
        return null;
    }

}
