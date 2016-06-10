/*
   This file is part of Cyclos.

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

import java.text.DecimalFormatSymbols;
import java.util.List;

import nl.strohalm.cyclos.entities.access.Channel;
import nl.strohalm.cyclos.entities.access.Channel.Credentials;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferTypeQuery;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.services.settings.SettingsService;
import nl.strohalm.cyclos.services.transfertypes.TransferTypeService;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.webservices.WebServiceContext;
import nl.strohalm.cyclos.webservices.model.MemberAccountVO;
import nl.strohalm.cyclos.webservices.model.MyProfileVO;
import nl.strohalm.cyclos.webservices.rest.TransferTypesRestController.Destination;
import nl.strohalm.cyclos.webservices.rest.TransferTypesRestController.TransferTypeSearchParams;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller which handles /access paths
 * 
 * @author luis
 */
@Controller
public class AccessRestController extends BaseRestController {

    public static class InitialData {
        private MyProfileVO           profile;
        private boolean               requireTransactionPassword;
        private List<MemberAccountVO> accounts;
        private boolean               canMakeMemberPayments;
        private boolean               canMakeSystemPayments;
        private int                   decimalCount;
        private char                  decimalSeparator;

        public List<MemberAccountVO> getAccounts() {
            return accounts;
        }

        public int getDecimalCount() {
            return decimalCount;
        }

        public char getDecimalSeparator() {
            return decimalSeparator;
        }

        public MyProfileVO getProfile() {
            return profile;
        }

        public boolean isCanMakeMemberPayments() {
            return canMakeMemberPayments;
        }

        public boolean isCanMakeSystemPayments() {
            return canMakeSystemPayments;
        }

        public boolean isRequireTransactionPassword() {
            return requireTransactionPassword;
        }

        public void setAccounts(final List<MemberAccountVO> accounts) {
            this.accounts = accounts;
        }

        public void setCanMakeMemberPayments(final boolean canMakeMemberPayments) {
            this.canMakeMemberPayments = canMakeMemberPayments;
        }

        public void setCanMakeSystemPayments(final boolean canMakeSystemPayments) {
            this.canMakeSystemPayments = canMakeSystemPayments;
        }

        public void setDecimalCount(final int decimalCount) {
            this.decimalCount = decimalCount;
        }

        public void setDecimalSeparator(final char decimalSeparator) {
            this.decimalSeparator = decimalSeparator;
        }

        public void setProfile(final MyProfileVO profile) {
            this.profile = profile;
        }

        public void setRequireTransactionPassword(final boolean requireTransactionPassword) {
            this.requireTransactionPassword = requireTransactionPassword;
        }

        @Override
        public String toString() {
            return "InitialData [profile=" + profile + ", requireTransactionPassword=" + requireTransactionPassword + ", accounts=" + accounts + ", canMakeMemberPayments=" + canMakeMemberPayments + ", canMakeSystemPayments=" + canMakeSystemPayments + ", decimalCount=" + decimalCount + ", decimalSeparator=" + decimalSeparator + "]";
        }

    }

    private MembersRestController       membersRestController;
    private AccountsRestController      accountsRestController;
    private TransferTypeService         transferTypeService;
    private SettingsService             settingsService;
    private TransferTypesRestController transferTypesRestController;

    /**
     * Returns general data about the authenticated user
     */
    @RequestMapping(value = "access/initialData", method = RequestMethod.GET)
    @ResponseBody
    public InitialData getInitialData() {
        InitialData data = new InitialData();
        data.setProfile(membersRestController.getMyProfile());
        data.setAccounts(accountsRestController.listAccounts());
        data.setRequireTransactionPassword(isRequireTransactionPassword());

        // Can make payment to member or to system
        TransferTypeSearchParams params = new TransferTypeSearchParams();
        params.setDestination(Destination.MEMBER);
        TransferTypeQuery query = transferTypesRestController.toTransferTypeQuery(params);
        List<TransferType> toMemberTTs = transferTypeService.search(query);
        // Remove the self payments
        for (TransferType tt : toMemberTTs) {
            if (tt.getContext().isSelfPayment()) {
                toMemberTTs.remove(tt);
            }
        }
        params.setDestination(Destination.SYSTEM);
        query = transferTypesRestController.toTransferTypeQuery(params);
        List<TransferType> toSystemTTs = transferTypeService.search(query);
        data.setCanMakeMemberPayments(CollectionUtils.isNotEmpty(toMemberTTs));
        data.setCanMakeSystemPayments(CollectionUtils.isNotEmpty(toSystemTTs));

        // Local settings
        final LocalSettings localSettings = settingsService.getLocalSettings();
        data.setDecimalCount(localSettings.getPrecision().getValue());
        data.setDecimalSeparator(new DecimalFormatSymbols(localSettings.getNumberLocale().getLocale()).getDecimalSeparator());
        return data;
    }

    @RequestMapping(value = "access/requireTransactionPassword", method = RequestMethod.GET)
    @ResponseBody
    public boolean isRequireTransactionPassword() {
        Channel channel = WebServiceContext.getChannel();
        // Only the default credentials require an extra transaction password
        if (channel.getCredentials() != Credentials.DEFAULT) {
            return false;
        }

        MemberGroup group = LoggedUser.group();
        return group.getBasicSettings().getTransactionPassword().isUsed();
    }

    public void setAccountsRestController(final AccountsRestController accountsRestController) {
        this.accountsRestController = accountsRestController;
    }

    public void setMembersRestController(final MembersRestController membersRestController) {
        this.membersRestController = membersRestController;
    }

    public void setSettingsService(final SettingsService settingsService) {
        this.settingsService = settingsService;
    }

    public void setTransferTypeService(final TransferTypeService transferTypeService) {
        this.transferTypeService = transferTypeService;
    }

    public void setTransferTypesRestController(final TransferTypesRestController transferTypesRestController) {
        this.transferTypesRestController = transferTypesRestController;
    }

}
