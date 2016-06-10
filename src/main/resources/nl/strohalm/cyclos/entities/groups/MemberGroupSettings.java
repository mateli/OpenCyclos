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

import java.math.BigDecimal;
import java.util.Set;

import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.utils.DataObject;
import nl.strohalm.cyclos.utils.RangeConstraint;
import nl.strohalm.cyclos.utils.StringValuedEnum;
import nl.strohalm.cyclos.utils.TimePeriod;

/**
 * Settings of a member group
 * @author luis
 */
public class MemberGroupSettings extends DataObject {

    /**
     * Determines in which registrations the e-mail validation will be used
     * 
     * @author luis
     */
    public static enum EmailValidation implements StringValuedEnum {
        /**
         * Either a public registration or an user editing his own profile
         */
        USER("U"),

        /**
         * An admin registering an user / editing an user profile
         */
        ADMIN("A"),

        /**
         * A broker registering an user / editing an user profile
         */
        BROKER("B"),

        /**
         * Either a registration or profile modification by web service
         */
        WEB_SERVICE("W");

        private final String value;

        private EmailValidation(final String value) {
            this.value = value;
        }

        @Override
        public String getValue() {
            return value;
        }
    }

    /**
     * Controls the external advertisement publication
     * @author luis
     */
    public static enum ExternalAdPublication implements StringValuedEnum {
        ALLOW_CHOICE("C"), ENABLED("E"), DISABLED("D");

        private final String value;

        private ExternalAdPublication(final String value) {
            this.value = value;
        }

        @Override
        public String getValue() {
            return value;
        }
    }

    private static final long     serialVersionUID             = -5279193799739646568L;

    // Access and external access
    private Set<EmailValidation>  emailValidation;
    private RangeConstraint       pinLength                    = new RangeConstraint(4, 4);
    private int                   maxPinWrongTries             = 3;
    private TimePeriod            pinBlockTimeAfterMaxTries    = new TimePeriod(1, TimePeriod.Field.DAYS);

    // Notifications
    private TransferType          smsChargeTransferType;

    // this is the amount to be charged for the a smsAdditionalCharged
    private BigDecimal            smsChargeAmount;
    private int                   smsFree                      = 0;
    private int                   smsAdditionalCharged         = 1;
    private int                   smsShowFreeThreshold         = 50;
    private TimePeriod            smsAdditionalChargedPeriod   = new TimePeriod(1, TimePeriod.Field.MONTHS);
    private String                smsContextClassName;

    // Registration
    private boolean               sendPasswordByEmail          = true;

    private TimePeriod            expireMembersAfter           = null;

    private MemberGroup           groupAfterExpiration         = null;

    private int                   maxImagesPerMember           = 3;

    // Advertisements
    private int                   maxAdsPerMember              = 10;

    private boolean               enablePermanentAds           = true;
    private TimePeriod            defaultAdPublicationTime     = new TimePeriod(1, TimePeriod.Field.MONTHS);
    private TimePeriod            maxAdPublicationTime         = new TimePeriod(3, TimePeriod.Field.MONTHS);

    private ExternalAdPublication externalAdPublication        = ExternalAdPublication.ENABLED;
    private int                   maxAdImagesPerMember         = 3;
    private int                   maxAdDescriptionSize         = 2048;
    // Scheduled payments
    private int                   maxSchedulingPayments        = 36;

    private TimePeriod            maxSchedulingPeriod          = new TimePeriod(3, TimePeriod.Field.YEARS);
    // Loans
    private boolean               viewLoansByGroup             = true;
    private boolean               repayLoanByGroup             = true;
    // Pos
    private boolean               allowMakePayment             = false;
    private int                   maxPosSchedulingPayments     = 6;
    private int                   numberOfCopies               = 2;
    private int                   resultPageSize               = 5;
    private boolean               showPosWebPaymentDescription = false;

    public TimePeriod getDefaultAdPublicationTime() {
        return defaultAdPublicationTime;
    }

    public Set<EmailValidation> getEmailValidation() {
        return emailValidation;
    }

    public TimePeriod getExpireMembersAfter() {
        return expireMembersAfter;
    }

    public ExternalAdPublication getExternalAdPublication() {
        return externalAdPublication;
    };

    public MemberGroup getGroupAfterExpiration() {
        return groupAfterExpiration;
    }

    public int getMaxAdDescriptionSize() {
        return maxAdDescriptionSize;
    }

    public int getMaxAdImagesPerMember() {
        return maxAdImagesPerMember;
    }

    public TimePeriod getMaxAdPublicationTime() {
        return maxAdPublicationTime;
    }

    public int getMaxAdsPerMember() {
        return maxAdsPerMember;
    }

    public int getMaxImagesPerMember() {
        return maxImagesPerMember;
    }

    public int getMaxPinWrongTries() {
        return maxPinWrongTries;
    }

    public int getMaxPosSchedulingPayments() {
        return maxPosSchedulingPayments;
    }

    public int getMaxSchedulingPayments() {
        return maxSchedulingPayments;
    }

    public TimePeriod getMaxSchedulingPeriod() {
        return maxSchedulingPeriod;
    }

    public int getNumberOfCopies() {
        return numberOfCopies;
    }

    public TimePeriod getPinBlockTimeAfterMaxTries() {
        return pinBlockTimeAfterMaxTries;
    }

    public RangeConstraint getPinLength() {
        return pinLength;
    }

    public int getResultPageSize() {
        return resultPageSize;
    }

    public int getSmsAdditionalCharged() {
        return smsAdditionalCharged;
    }

    public TimePeriod getSmsAdditionalChargedPeriod() {
        return smsAdditionalChargedPeriod;
    }

    public BigDecimal getSmsChargeAmount() {
        return smsChargeAmount;
    }

    public TransferType getSmsChargeTransferType() {
        return smsChargeTransferType;
    }

    public String getSmsContextClassName() {
        return smsContextClassName;
    }

    public int getSmsFree() {
        return smsFree;
    }

    public int getSmsShowFreeThreshold() {
        return smsShowFreeThreshold;
    }

    public boolean isAllowMakePayment() {
        return allowMakePayment;
    }

    public boolean isAllowsMultipleScheduledPayments() {
        return isAllowsScheduledPayments() && maxSchedulingPayments > 1;
    }

    public boolean isAllowsScheduledPayments() {
        return maxSchedulingPayments > 0 && maxSchedulingPeriod != null && maxSchedulingPeriod.isValid();
    }

    public boolean isEnablePermanentAds() {
        return enablePermanentAds;
    }

    public boolean isRepayLoanByGroup() {
        return repayLoanByGroup;
    }

    public boolean isSendPasswordByEmail() {
        return sendPasswordByEmail;
    }

    public boolean isShowPosWebPaymentDescription() {
        return showPosWebPaymentDescription;
    }

    public boolean isViewLoansByGroup() {
        return viewLoansByGroup;
    }

    public void setAllowMakePayment(final boolean allowMakePayment) {
        this.allowMakePayment = allowMakePayment;
    }

    public void setDefaultAdPublicationTime(final TimePeriod defaultAdPublicationTime) {
        this.defaultAdPublicationTime = defaultAdPublicationTime;
    }

    public void setEmailValidation(final Set<EmailValidation> emailValidation) {
        this.emailValidation = emailValidation;
    }

    public void setEnablePermanentAds(final boolean enablePermanentAds) {
        this.enablePermanentAds = enablePermanentAds;
    }

    public void setExpireMembersAfter(final TimePeriod expireMemberAfter) {
        expireMembersAfter = expireMemberAfter;
    }

    public void setExternalAdPublication(final ExternalAdPublication externalAdPublication) {
        this.externalAdPublication = externalAdPublication;
    }

    public void setGroupAfterExpiration(final MemberGroup groupAfterExpiration) {
        this.groupAfterExpiration = groupAfterExpiration;
    }

    public void setMaxAdDescriptionSize(final int maxAdDescriptionSize) {
        this.maxAdDescriptionSize = maxAdDescriptionSize;
    }

    public void setMaxAdImagesPerMember(final int maxAdImagesPerMember) {
        this.maxAdImagesPerMember = maxAdImagesPerMember;
    }

    public void setMaxAdPublicationTime(final TimePeriod maxAdPublicationTime) {
        this.maxAdPublicationTime = maxAdPublicationTime;
    }

    public void setMaxAdsPerMember(final int maxAdsPerMember) {
        this.maxAdsPerMember = maxAdsPerMember;
    }

    public void setMaxImagesPerMember(final int maxImagesPerMember) {
        this.maxImagesPerMember = maxImagesPerMember;
    }

    public void setMaxPinWrongTries(final int maxPinWrongTries) {
        this.maxPinWrongTries = maxPinWrongTries;
    }

    public void setMaxPosSchedulingPayments(final int maxPosSchedulingPayments) {
        this.maxPosSchedulingPayments = maxPosSchedulingPayments;
    }

    public void setMaxSchedulingPayments(final int maxSchedulingPayments) {
        this.maxSchedulingPayments = maxSchedulingPayments;
    }

    public void setMaxSchedulingPeriod(final TimePeriod maxSchedulingPeriod) {
        this.maxSchedulingPeriod = maxSchedulingPeriod;
    }

    public void setNumberOfCopies(final int numberOfCopies) {
        this.numberOfCopies = numberOfCopies;
    }

    public void setPinBlockTimeAfterMaxTries(final TimePeriod pinBlockTimeAfterMaxTries) {
        this.pinBlockTimeAfterMaxTries = pinBlockTimeAfterMaxTries;
    }

    public void setPinLength(final RangeConstraint pinLength) {
        this.pinLength = pinLength;
    }

    public void setRepayLoanByGroup(final boolean repayLoansByGroup) {
        repayLoanByGroup = repayLoansByGroup;
    }

    public void setResultPageSize(final int resultPageSize) {
        this.resultPageSize = resultPageSize;
    }

    public void setSendPasswordByEmail(final boolean sendPasswordByMail) {
        sendPasswordByEmail = sendPasswordByMail;
    }

    public void setShowPosWebPaymentDescription(final boolean showPosWebPaymentDescription) {
        this.showPosWebPaymentDescription = showPosWebPaymentDescription;
    }

    public void setSmsAdditionalCharged(final int smsAdditionalCharged) {
        this.smsAdditionalCharged = smsAdditionalCharged;
    }

    public void setSmsAdditionalChargedPeriod(final TimePeriod smsAdditionalChargedPeriod) {
        this.smsAdditionalChargedPeriod = smsAdditionalChargedPeriod;
    }

    public void setSmsChargeAmount(final BigDecimal smsChargeAmount) {
        this.smsChargeAmount = smsChargeAmount;
    }

    public void setSmsChargeTransferType(final TransferType smsChargeTransferType) {
        this.smsChargeTransferType = smsChargeTransferType;
    }

    public void setSmsContextClassName(final String smsContextClassName) {
        this.smsContextClassName = smsContextClassName;
    }

    public void setSmsFree(final int smsFree) {
        this.smsFree = smsFree;
    }

    public void setSmsShowFreeThreshold(final int smsShowFreeThreshold) {
        this.smsShowFreeThreshold = smsShowFreeThreshold;
    }

    public void setViewLoansByGroup(final boolean viewLoansByGroup) {
        this.viewLoansByGroup = viewLoansByGroup;
    }

}
