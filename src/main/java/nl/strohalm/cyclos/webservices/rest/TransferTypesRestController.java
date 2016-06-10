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

import nl.strohalm.cyclos.entities.access.Channel;
import nl.strohalm.cyclos.entities.accounts.Account;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.accounts.SystemAccountOwner;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferTypeQuery;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.services.accounts.AccountService;
import nl.strohalm.cyclos.services.elements.MemberService;
import nl.strohalm.cyclos.services.transactions.TransactionContext;
import nl.strohalm.cyclos.services.transfertypes.TransferTypeService;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.webservices.model.TransferTypeVO;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller which handles /transferTypes paths
 * 
 * @author luis
 */
@Controller
public class TransferTypesRestController extends BaseRestController {

    public static enum Destination {
        MEMBER, /* SELF, */SYSTEM
    }

    /**
     * Parameters used to search for transfer types
     * @author luis
     */
    public static class TransferTypeSearchParams {
        private Destination destination;
        private Long        fromAccountId;
        private Long        toMemberId;
        private String      toMemberPrincipal;
        private Long        currencyId;
        private String      currencySymbol;

        public Long getCurrencyId() {
            return currencyId;
        }

        public String getCurrencySymbol() {
            return currencySymbol;
        }

        public Destination getDestination() {
            return destination;
        }

        public Long getFromAccountId() {
            return fromAccountId;
        }

        public Long getToMemberId() {
            return toMemberId;
        }

        public String getToMemberPrincipal() {
            return toMemberPrincipal;
        }

        public void setCurrencyId(final Long currencyId) {
            this.currencyId = currencyId;
        }

        public void setCurrencySymbol(final String currencySymbol) {
            this.currencySymbol = currencySymbol;
        }

        public void setDestination(final Destination destination) {
            this.destination = destination;
        }

        public void setFromAccountId(final Long fromAccountId) {
            this.fromAccountId = fromAccountId;
        }

        public void setToMemberId(final Long toMemberId) {
            this.toMemberId = toMemberId;
        }

        public void setToMemberPrincipal(final String toMemberPrincipal) {
            this.toMemberPrincipal = toMemberPrincipal;
        }
    }

    private TransferTypeService    transferTypeService;
    private AccountService         accountService;
    private MemberService          memberService;
    private AccountsRestController accountsRestController;

    /**
     * Searches for transfer types the authenticated user may use to perform payments
     */
    @RequestMapping(value = "transferTypes", method = RequestMethod.GET)
    @ResponseBody
    public List<TransferTypeVO> listTransferTypes(final TransferTypeSearchParams params) {
        TransferTypeQuery query = toTransferTypeQuery(params);
        List<TransferType> transferTypes = transferTypeService.search(query);
        List<TransferTypeVO> transferTypeVOs = new ArrayList<TransferTypeVO>(transferTypes.size());
        for (TransferType transferType : transferTypes) {
            transferTypeVOs.add(transferTypeService.getTransferTypeVO(transferType.getId(), false));
        }
        return transferTypeVOs;
    }

    /**
     * Loads a transfer type by id
     */
    @RequestMapping(value = "transferTypes/{id}", method = RequestMethod.GET)
    @ResponseBody
    public TransferTypeVO loadTransferType(@PathVariable final Long id) {
        return transferTypeService.getTransferTypeVO(id, false);
    }

    public void setAccountService(final AccountService accountService) {
        this.accountService = accountService;
    }

    public void setAccountsRestController(final AccountsRestController accountsRestController) {
        this.accountsRestController = accountsRestController;
    }

    public void setMemberService(final MemberService memberService) {
        this.memberService = memberService;
    }

    public void setTransferTypeService(final TransferTypeService transferTypeService) {
        this.transferTypeService = transferTypeService;
    }

    public TransferTypeQuery toTransferTypeQuery(final TransferTypeSearchParams params) {
        Destination destination = params.getDestination();
        if (destination == null) {
            destination = Destination.MEMBER;
        }
        Member loggedMember = LoggedUser.member();

        TransferTypeQuery query = new TransferTypeQuery();
        query.setChannel(Channel.REST);
        // query.setContext(destination == Destination.SELF ? TransactionContext.SELF_PAYMENT : TransactionContext.PAYMENT);
        query.setContext(TransactionContext.PAYMENT);
        query.setFromOwner(loggedMember);
        query.setCurrency(accountsRestController.loadCurrencyByIdOrSymbol(params.getCurrencyId(), params.getCurrencySymbol()));
        switch (destination) {
            case MEMBER:
                query.setToOwner(memberService.loadByIdOrPrincipal(params.getToMemberId(), null, params.getToMemberPrincipal()));
                query.setToNature(AccountType.Nature.MEMBER);
                break;
            case SYSTEM:
                query.setToOwner(SystemAccountOwner.instance());
                query.setToNature(AccountType.Nature.SYSTEM);
                break;
        // case SELF:
        // query.setToOwner(loggedMember);
        // break;
        }
        if (params.getFromAccountId() != null && params.getFromAccountId() > 0) {
            Account account = accountService.load(params.getFromAccountId());
            query.setToAccountType(account.getType());
        }
        return query;

    }
}
