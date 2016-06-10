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
package nl.strohalm.cyclos.entities.settings;

import nl.strohalm.cyclos.utils.DataObject;
import nl.strohalm.cyclos.utils.RangeConstraint;
import nl.strohalm.cyclos.utils.TimePeriod;
import nl.strohalm.cyclos.utils.WhitelistValidator;

/**
 * Groups login / access settings
 * @author luis
 */
public class AccessSettings extends DataObject {

    public static enum UsernameGeneration {
        NONE, RANDOM;
    }

    private static final long            serialVersionUID                   = 5443049128576812703L;

    private boolean                      virtualKeyboard                    = false;
    private boolean                      virtualKeyboardTransactionPassword = false;
    private boolean                      numericPassword                    = false;
    private boolean                      allowOperatorLogin                 = false;
    private boolean                      allowMultipleLogins                = true;

    private TimePeriod                   adminTimeout                       = new TimePeriod(15, TimePeriod.Field.MINUTES);
    private String                       administrationWhitelist            = "#Any host";

    private UsernameGeneration           usernameGeneration                 = UsernameGeneration.NONE;
    private RangeConstraint              usernameLength                     = new RangeConstraint(4, 12);

    private int                          generatedUsernameLength            = 5;
    private TimePeriod                   memberTimeout                      = new TimePeriod(10, TimePeriod.Field.MINUTES);
    private String                       transactionPasswordChars           = "ABCDEFGHIJ";
    private TimePeriod                   poswebTimeout                      = new TimePeriod(1, TimePeriod.Field.DAYS);

    private String                       usernameRegex                      = "^[\\w\\.]*$";

    private transient WhitelistValidator administrationWhitelistValidator;

    public String getAdministrationWhitelist() {
        return administrationWhitelist;
    }

    public WhitelistValidator getAdministrationWhitelistValidator() {
        if (administrationWhitelistValidator == null) {
            administrationWhitelistValidator = new WhitelistValidator(administrationWhitelist);
        }
        return administrationWhitelistValidator;
    }

    public TimePeriod getAdminTimeout() {
        return adminTimeout;
    }

    public int getGeneratedUsernameLength() {
        return generatedUsernameLength;
    }

    public TimePeriod getMemberTimeout() {
        return memberTimeout;
    }

    public TimePeriod getPoswebTimeout() {
        return poswebTimeout;
    }

    public String getTransactionPasswordChars() {
        return transactionPasswordChars;
    }

    public UsernameGeneration getUsernameGeneration() {
        return usernameGeneration;
    }

    public RangeConstraint getUsernameLength() {
        return usernameLength;
    }

    public String getUsernameRegex() {
        return usernameRegex;
    }

    public boolean isAllowMultipleLogins() {
        return allowMultipleLogins;
    }

    public boolean isAllowOperatorLogin() {
        return allowOperatorLogin;
    }

    public boolean isNumericPassword() {
        return numericPassword;
    }

    public boolean isUsernameGenerated() {
        return usernameGeneration != UsernameGeneration.NONE;
    }

    public boolean isVirtualKeyboard() {
        return virtualKeyboard;
    }

    public boolean isVirtualKeyboardTransactionPassword() {
        return virtualKeyboardTransactionPassword;
    }

    public void setAdministrationWhitelist(final String administrationWhitelist) {
        this.administrationWhitelist = administrationWhitelist;
        administrationWhitelistValidator = null;
    }

    public void setAdminTimeout(final TimePeriod adminTimeout) {
        this.adminTimeout = adminTimeout;
    }

    public void setAllowMultipleLogins(final boolean allowMultipleLogins) {
        this.allowMultipleLogins = allowMultipleLogins;
    }

    public void setAllowOperatorLogin(final boolean allowOperatorLogin) {
        this.allowOperatorLogin = allowOperatorLogin;
    }

    public void setGeneratedUsernameLength(final int generatedUsernameLength) {
        this.generatedUsernameLength = generatedUsernameLength;
    }

    public void setMemberTimeout(final TimePeriod memberTimeout) {
        this.memberTimeout = memberTimeout;
    }

    public void setNumericPassword(final boolean numericPassword) {
        this.numericPassword = numericPassword;
    }

    public void setPoswebTimeout(final TimePeriod poswebTimeout) {
        this.poswebTimeout = poswebTimeout;
    }

    public void setTransactionPasswordChars(final String transactionPasswordChars) {
        this.transactionPasswordChars = transactionPasswordChars;
    }

    public void setUsernameGeneration(final UsernameGeneration usernameGeneration) {
        this.usernameGeneration = usernameGeneration;
    }

    public void setUsernameLength(final RangeConstraint usernameLength) {
        this.usernameLength = usernameLength;
    }

    public void setUsernameRegex(final String usernameRegex) {
        this.usernameRegex = usernameRegex;
    }

    public void setVirtualKeyboard(final boolean appletLogin) {
        virtualKeyboard = appletLogin;
    }

    public void setVirtualKeyboardTransactionPassword(final boolean virtualKeyboardTransactionPassword) {
        this.virtualKeyboardTransactionPassword = virtualKeyboardTransactionPassword;
    }

}
