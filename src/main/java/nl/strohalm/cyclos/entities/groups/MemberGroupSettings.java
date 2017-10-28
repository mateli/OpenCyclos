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

import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.utils.RangeConstraint;
import nl.strohalm.cyclos.entities.utils.TimePeriod;
import nl.strohalm.cyclos.utils.DataObject;
import nl.strohalm.cyclos.utils.StringValuedEnum;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.util.Set;

/**
 * Settings of a member group
 * @author luis
 */
@Embeddable
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
    @ElementCollection
    @CollectionTable(name = "member_groups_email_validation",
            joinColumns = @JoinColumn(name = "group_id"))
    @Column(name = "type", length = 1, nullable = false)
    private Set<EmailValidation>  emailValidation;

    @AttributeOverrides({
            @AttributeOverride(name = "min", column=@Column(name="min_pin_length")),
            @AttributeOverride(name = "max", column=@Column(name="max_pin_length"))
    })
    @Embedded
    private RangeConstraint       pinLength                    = new RangeConstraint(4, 4);

    @Column(name = "member_max_pin_tries")
    private int                   maxPinWrongTries             = 3;

    @AttributeOverrides({
            @AttributeOverride(name = "number", column=@Column(name="member_pin_block_number")),
            @AttributeOverride(name = "field", column=@Column(name="member_pin_block_field"))
    })
    @Embedded
    private TimePeriod            pinBlockTimeAfterMaxTries    = new TimePeriod(1, TimePeriod.Field.DAYS);

    // Notifications
    @ManyToOne
    @JoinColumn(name = "sms_charge_transfer_type_id")
    private TransferType          smsChargeTransferType;

    // this is the amount to be charged for the a smsAdditionalCharged
    @Column(name = "sms_charge_amount", precision = 15, scale = 6)
    private BigDecimal            smsChargeAmount;

    @Column(name = "sms_free_count", nullable = false)
    private int                   smsFree                      = 0;

    @Column(name = "sms_additional_charged_count", nullable = false)
    private int                   smsAdditionalCharged         = 1;

    @Column(name = "sms_show_free_threshold")
    private int                   smsShowFreeThreshold         = 50;

    @AttributeOverrides({
            @AttributeOverride(name = "number", column=@Column(name="sms_additional_charged_period_number")),
            @AttributeOverride(name = "field", column=@Column(name="sms_additional_charged_period_field"))
    })
    @Embedded
    private TimePeriod            smsAdditionalChargedPeriod   = new TimePeriod(1, TimePeriod.Field.MONTHS);

    @Column(name = "sms_context_class_name")
    private String                smsContextClassName;

    // Registration
    @Column(name = "member_send_password_by_email", nullable = false)
    private boolean               sendPasswordByEmail          = true;

    @AttributeOverrides({
            @AttributeOverride(name = "number", column=@Column(name="member_expire_number")),
            @AttributeOverride(name = "field", column=@Column(name="member_expire_field"))
    })
    @Embedded
    private TimePeriod            expireMembersAfter           = null;

    @ManyToOne
    @JoinColumn(name = "member_expire_group_id")
    private MemberGroup           groupAfterExpiration         = null;

    @Column(name = "member_max_images_per_member")
    private int                   maxImagesPerMember           = 3;

    // Advertisements
    @Column(name = "member_max_ads_per_member")
    private int                   maxAdsPerMember              = 10;

    @Column(name = "member_enable_permanent_ads", nullable = false)
    private boolean               enablePermanentAds           = true;

    @AttributeOverrides({
            @AttributeOverride(name = "number", column=@Column(name="member_default_ad_publication_number")),
            @AttributeOverride(name = "field", column=@Column(name="member_default_ad_publication_field"))
    })
    @Embedded
    private TimePeriod            defaultAdPublicationTime     = new TimePeriod(1, TimePeriod.Field.MONTHS);

    @AttributeOverrides({
            @AttributeOverride(name = "number", column=@Column(name="member_max_ad_publication_number")),
            @AttributeOverride(name = "field", column=@Column(name="member_max_ad_publication_field"))
    })
    @Embedded
    private TimePeriod            maxAdPublicationTime         = new TimePeriod(3, TimePeriod.Field.MONTHS);

    @Column(name = "member_external_ad_publication", length = 1)
    private ExternalAdPublication externalAdPublication        = ExternalAdPublication.ENABLED;

    @Column(name = "member_max_ad_images_per_member")
    private int                   maxAdImagesPerMember         = 3;

    @Column(name = "member_max_ad_description_size")
    private int                   maxAdDescriptionSize         = 2048;

    // Scheduled payments
    @Column(name = "member_max_scheduling_payments", nullable = false)
    private int                   maxSchedulingPayments        = 36;

    @AttributeOverrides({
            @AttributeOverride(name = "number", column=@Column(name="member_max_scheduling_period_number")),
            @AttributeOverride(name = "field", column=@Column(name="member_max_scheduling_period_field"))
    })
    @Embedded
    private TimePeriod            maxSchedulingPeriod          = new TimePeriod(3, TimePeriod.Field.YEARS);

    // Loans
    @Column(name = "member_view_loans_by_group", nullable = false)
    private boolean               viewLoansByGroup             = true;

    @Column(name = "member_repay_loan_by_group", nullable = false)
    private boolean               repayLoanByGroup             = true;

    // Pos
    @Column(name = "member_allow_make_payment", nullable = false)
    private boolean               allowMakePayment             = false;

    @Column(name = "member_max_pos_scheduling_payments", nullable = false)
    private int                   maxPosSchedulingPayments     = 6;

    @Column(name = "member_number_of_copies", nullable = false)
    private int                   numberOfCopies               = 2;

    @Column(name = "member_result_page_size", nullable = false)
    private int                   resultPageSize               = 5;

    @Column(name = "member_show_posweb_pmt_dsc", nullable = false)
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
