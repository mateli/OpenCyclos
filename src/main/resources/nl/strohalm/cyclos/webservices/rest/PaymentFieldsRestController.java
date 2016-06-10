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
package nl.strohalm.cyclos.webservices.rest;

import java.util.ArrayList;
import java.util.List;

import nl.strohalm.cyclos.entities.accounts.Account;
import nl.strohalm.cyclos.entities.accounts.MemberAccount;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.customization.fields.PaymentCustomField;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.services.accounts.AccountService;
import nl.strohalm.cyclos.services.customization.PaymentCustomFieldService;
import nl.strohalm.cyclos.services.transfertypes.TransferTypeService;
import nl.strohalm.cyclos.utils.EntityHelper;
import nl.strohalm.cyclos.webservices.model.FieldVO;
import nl.strohalm.cyclos.webservices.model.PossibleValueVO;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller which handles /paymentFields paths
 * 
 * @author luis
 */
@Controller
public class PaymentFieldsRestController extends BaseFieldsRestController<PaymentCustomField> {

    private AccountService            accountService;
    private TransferTypeService       transferTypeService;
    private PaymentCustomFieldService paymentCustomFieldService;

    /**
     * Lists the custom fields for a given transfer type
     */
    @RequestMapping(value = "paymentFields/byTransferType/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<FieldVO> listByTransferTypeId(@PathVariable final Long id) {
        TransferType transferType;
        try {
            transferType = transferTypeService.load(id);
        } catch (Exception e) {
            throw new EntityNotFoundException(TransferType.class);
        }
        List<PaymentCustomField> fields = paymentCustomFieldService.list(transferType, false);
        ArrayList<Long> customFieldIds = new ArrayList<Long>(EntityHelper.toIdsAsList(fields));
        return paymentCustomFieldService.getFieldVOs(customFieldIds);
    }

    /**
     * Lists the custom fields used on the result list the given account's account history
     */
    @RequestMapping(value = "paymentFields/forList/{accountId}", method = RequestMethod.GET)
    @ResponseBody
    public List<FieldVO> listForList(@PathVariable final Long accountId) {
        Account account = loadAccount(accountId);
        List<PaymentCustomField> fields = paymentCustomFieldService.listForList(account, false);
        ArrayList<Long> customFieldIds = new ArrayList<Long>(EntityHelper.toIdsAsList(fields));
        return paymentCustomFieldService.getFieldVOs(customFieldIds);
    }

    /**
     * Lists the custom fields used on the result list the default account's account history
     */
    @RequestMapping(value = "paymentFields/forList/default", method = RequestMethod.GET)
    @ResponseBody
    public List<FieldVO> listForListOnDefaultAccount() {
        MemberAccount account = accountService.getDefaultAccount();
        List<PaymentCustomField> fields = paymentCustomFieldService.listForList(account, false);
        ArrayList<Long> customFieldIds = new ArrayList<Long>(EntityHelper.toIdsAsList(fields));
        return paymentCustomFieldService.getFieldVOs(customFieldIds);
    }

    /**
     * Lists the custom fields used to search the given account's history
     */
    @RequestMapping(value = "paymentFields/forSearch/{accountId}", method = RequestMethod.GET)
    @ResponseBody
    public List<FieldVO> listForSearch(@PathVariable final Long accountId) {
        MemberAccount account = loadAccount(accountId);
        List<PaymentCustomField> fields = paymentCustomFieldService.listForSearch(account, false);
        ArrayList<Long> customFieldIds = new ArrayList<Long>(EntityHelper.toIdsAsList(fields));
        return paymentCustomFieldService.getFieldVOs(customFieldIds);
    }

    /**
     * Lists the custom fields used to search the default account's history
     */
    @RequestMapping(value = "paymentFields/forSearch/default", method = RequestMethod.GET)
    @ResponseBody
    public List<FieldVO> listForSearchOnDefaultAccount() {
        MemberAccount account = accountService.getDefaultAccount();
        List<PaymentCustomField> fields = paymentCustomFieldService.listForSearch(account, false);
        ArrayList<Long> customFieldIds = new ArrayList<Long>(EntityHelper.toIdsAsList(fields));
        return paymentCustomFieldService.getFieldVOs(customFieldIds);
    }

    /**
     * Returns the possible values of a given custom field
     */
    @RequestMapping(value = "paymentFields/{id}/possibleValues", method = RequestMethod.GET)
    @ResponseBody
    public List<PossibleValueVO> listPossibleValues(@PathVariable final Long id) {
        return paymentCustomFieldService.getPossibleValueVOs(id, null);
    }

    /**
     * Returns the possible values of a given custom field by internal name
     */
    @RequestMapping(value = "paymentFields/name/{name}/possibleValues", method = RequestMethod.GET)
    @ResponseBody
    public List<PossibleValueVO> listPossibleValuesByInternalName(@PathVariable final String name) {
        PaymentCustomField field = load(name);
        return paymentCustomFieldService.getPossibleValueVOs(field.getId(), null);
    }

    /**
     * Returns the possible values of a given custom field and parent value id
     */
    @RequestMapping(value = "paymentFields/name/{name}/possibleValues/{parentValueId}", method = RequestMethod.GET)
    @ResponseBody
    public List<PossibleValueVO> listPossibleValuesByInternalNameAndParent(@PathVariable final String name, @PathVariable final Long parentValueId) {
        PaymentCustomField field = load(name);
        return paymentCustomFieldService.getPossibleValueVOs(field.getId(), parentValueId);
    }

    /**
     * Returns the possible values of a given custom field by internal name and parent value id
     */
    @RequestMapping(value = "paymentFields/{fieldId}/possibleValues/{parentValueId}", method = RequestMethod.GET)
    @ResponseBody
    public List<PossibleValueVO> listPossibleValuesByParent(@PathVariable final Long fieldId, @PathVariable final Long parentValueId) {
        return paymentCustomFieldService.getPossibleValueVOs(fieldId, parentValueId);
    }

    /**
     * Returns the details about a custom field
     */
    @RequestMapping(value = "paymentFields/{id}", method = RequestMethod.GET)
    @ResponseBody
    public FieldVO loadById(@PathVariable final Long id) {
        return paymentCustomFieldService.getFieldVO(id);
    }

    /**
     * Returns the details about a custom field
     */
    @RequestMapping(value = "paymentFields/name/{name}", method = RequestMethod.GET)
    @ResponseBody
    public FieldVO loadByInternalName(@PathVariable final String name) {
        PaymentCustomField paymentField = load(name);
        return paymentCustomFieldService.getFieldVO(paymentField.getId());
    }

    public void setAccountService(final AccountService accountService) {
        this.accountService = accountService;
    }

    public void setPaymentCustomFieldService(final PaymentCustomFieldService paymentCustomFieldService) {
        this.paymentCustomFieldService = paymentCustomFieldService;
    }

    public void setTransferTypeService(final TransferTypeService transferTypeService) {
        this.transferTypeService = transferTypeService;
    }

    private PaymentCustomField load(final String name) {
        return paymentCustomFieldService.loadByInternalName(name);
    }

    private MemberAccount loadAccount(final Long accountId) {
        MemberAccount account;
        try {
            account = accountService.load(accountId);
        } catch (Exception e) {
            throw new EntityNotFoundException(Account.class);
        }
        return account;
    }

}
