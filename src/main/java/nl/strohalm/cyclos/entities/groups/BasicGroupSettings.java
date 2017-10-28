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
package nl.strohalm.cyclos.entities.groups;

import nl.strohalm.cyclos.entities.access.TransactionPassword;
import nl.strohalm.cyclos.entities.utils.RangeConstraint;
import nl.strohalm.cyclos.entities.utils.TimePeriod;
import nl.strohalm.cyclos.utils.DataObject;
import nl.strohalm.cyclos.utils.StringValuedEnum;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

/**
 * Settings of a group
 * @author luis
 */
@Embeddable
public class BasicGroupSettings extends DataObject {

    /**
     * Specifies the policy for passwords.
     * @author luis
     */
    public static enum PasswordPolicy implements StringValuedEnum {

        /**
         * The password is not enforced at all
         */
        NONE("N", false),

        /**
         * Obvious patterns are forbidden, like:
         * <ul>
         * <li>Sequential characters (there must be more than 1 different differences between each 2 sequential characters)</li>
         * <li>Same as any word of the full name</li>
         * <li>Same as username</li>
         * <li>Same as the e-mail user</li>
         * <li>Contained in a custom field value (of types date, integer or string)</li>
         * </ul>
         */
        AVOID_OBVIOUS("O", false),

        /**
         * Besides to the previous, enforces the password to contain both letters and numbers. Special care must be taken when the global setting
         * "Numeric password" is turned on.
         */
        AVOID_OBVIOUS_LETTERS_NUMBERS("L", true),

        /**
         * Besides to the previous, enforces the password to contain letters, numbers and special characters. Special care must be taken when the
         * global setting "Numeric password" is turned on or when the virtual keyboard is enabled, as it does not allow entering special characters.
         */
        AVOID_OBVIOUS_LETTERS_NUMBERS_SPECIAL("S", true);

        private final String  value;
        private final boolean forceCharacters;

        private PasswordPolicy(final String value, final boolean forceCharacters) {
            this.value = value;
            this.forceCharacters = forceCharacters;
        }

        @Override
        public String getValue() {
            return value;
        }

        public boolean isForceCharacters() {
            return forceCharacters;
        }
    }

    private static final long   serialVersionUID                  = 1292266261711753784L;

    @AttributeOverrides({
            @AttributeOverride(name = "min", column=@Column(name="min_password_length")),
            @AttributeOverride(name = "max", column=@Column(name="max_password_length"))
    })
    @Embedded
    private RangeConstraint     passwordLength                    = new RangeConstraint(4, 12);

    @Column(name = "password_policy", nullable = false, length = 1)
    private PasswordPolicy      passwordPolicy                    = PasswordPolicy.AVOID_OBVIOUS;

    @Column(name = "max_password_tries")
    private int                 maxPasswordWrongTries             = 3;

    @AttributeOverrides({
            @AttributeOverride(name = "number", column=@Column(name="deactivation_number")),
            @AttributeOverride(name = "field", column=@Column(name="deactivation_field"))
    })
    @Embedded
    private TimePeriod          deactivationAfterMaxPasswordTries = new TimePeriod(10, TimePeriod.Field.MINUTES);

    @AttributeOverrides({
            @AttributeOverride(name = "number", column=@Column(name="password_expiration_number")),
            @AttributeOverride(name = "field", column=@Column(name="password_expiration_field"))
    })
    @Embedded
    private TimePeriod          passwordExpiresAfter              = new TimePeriod(0, TimePeriod.Field.MONTHS);

    @Column(name = "transaction_password", length = 1)
    private TransactionPassword transactionPassword               = TransactionPassword.NOT_USED;

    @Column(name = "transaction_password_length", nullable = false)
    private int                 transactionPasswordLength         = 4;

    @Column(name = "max_tp_tries", nullable = false)
    private int                 maxTransactionPasswordWrongTries  = 3;

    @Column(name = "hide_currency_on_pmt", nullable = false)
    private boolean             hideCurrencyOnPayments;

    public TimePeriod getDeactivationAfterMaxPasswordTries() {
        return deactivationAfterMaxPasswordTries;
    }

    public int getMaxPasswordWrongTries() {
        return maxPasswordWrongTries;
    }

    public int getMaxTransactionPasswordWrongTries() {
        return maxTransactionPasswordWrongTries;
    }

    public TimePeriod getPasswordExpiresAfter() {
        return passwordExpiresAfter;
    }

    public RangeConstraint getPasswordLength() {
        return passwordLength;
    }

    public PasswordPolicy getPasswordPolicy() {
        return passwordPolicy;
    }

    public TransactionPassword getTransactionPassword() {
        return transactionPassword;
    }

    public int getTransactionPasswordLength() {
        return transactionPasswordLength;
    }

    public boolean isHideCurrencyOnPayments() {
        return hideCurrencyOnPayments;
    }

    public void setDeactivationAfterMaxPasswordTries(final TimePeriod deactivationAfterWrongPasswords) {
        deactivationAfterMaxPasswordTries = deactivationAfterWrongPasswords;
    }

    public void setHideCurrencyOnPayments(final boolean hideCurrencyOnPayments) {
        this.hideCurrencyOnPayments = hideCurrencyOnPayments;
    }

    public void setMaxPasswordWrongTries(final int maxPasswordWrongTries) {
        this.maxPasswordWrongTries = maxPasswordWrongTries;
    }

    public void setMaxTransactionPasswordWrongTries(final int maxTransactionPasswordWrongTrials) {
        maxTransactionPasswordWrongTries = maxTransactionPasswordWrongTrials;
    }

    public void setPasswordExpiresAfter(final TimePeriod passwordExpiresAfter) {
        this.passwordExpiresAfter = passwordExpiresAfter;
    }

    public void setPasswordLength(final RangeConstraint passwordLength) {
        this.passwordLength = passwordLength;
    }

    public void setPasswordPolicy(final PasswordPolicy passwordPolicy) {
        this.passwordPolicy = passwordPolicy == null ? PasswordPolicy.NONE : passwordPolicy;
    }

    public void setTransactionPassword(final TransactionPassword transactionPassword) {
        this.transactionPassword = transactionPassword;
    }

    public void setTransactionPasswordLength(final int transactionPasswordLength) {
        this.transactionPasswordLength = transactionPasswordLength;
    }
}
