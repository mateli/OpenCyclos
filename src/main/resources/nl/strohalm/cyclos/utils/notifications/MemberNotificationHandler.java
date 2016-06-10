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
package nl.strohalm.cyclos.utils.notifications;

import java.util.Set;

import nl.strohalm.cyclos.entities.access.Channel.Credentials;
import nl.strohalm.cyclos.entities.access.User;
import nl.strohalm.cyclos.entities.accounts.MemberAccountType;
import nl.strohalm.cyclos.entities.accounts.guarantees.Certification;
import nl.strohalm.cyclos.entities.accounts.guarantees.Guarantee;
import nl.strohalm.cyclos.entities.accounts.guarantees.PaymentObligation;
import nl.strohalm.cyclos.entities.accounts.loans.Loan;
import nl.strohalm.cyclos.entities.accounts.loans.LoanPayment;
import nl.strohalm.cyclos.entities.accounts.pos.MemberPos;
import nl.strohalm.cyclos.entities.accounts.transactions.Invoice;
import nl.strohalm.cyclos.entities.accounts.transactions.Payment;
import nl.strohalm.cyclos.entities.accounts.transactions.PaymentRequestTicket;
import nl.strohalm.cyclos.entities.accounts.transactions.Transfer;
import nl.strohalm.cyclos.entities.ads.Ad;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.Reference;
import nl.strohalm.cyclos.entities.members.TransactionFeedback;
import nl.strohalm.cyclos.entities.members.brokerings.BrokerCommissionContract;
import nl.strohalm.cyclos.entities.members.brokerings.Brokering;
import nl.strohalm.cyclos.services.elements.ChangeBrokerDTO;
import nl.strohalm.cyclos.services.transactions.DoPaymentDTO;
import nl.strohalm.cyclos.services.transactions.TransferDTO;

/**
 * Handles notifications for members
 * 
 * @author luis
 */
public interface MemberNotificationHandler {

    void acceptedInvoiceNotification(Invoice invoice);

    void automaticPaymentReceivedNotification(Transfer transfer, TransferDTO dto);

    void blockedCredentialsNotification(User user, Credentials credentialsType);

    void cancelledInvoiceNotification(Invoice invoice);

    void certificationCanceledNotification(Long certificationId);

    void certificationIssuedNotification(Certification certification);

    void certificationStatusChangedNotification(Certification certification);

    void commissionContractAcceptedNotification(BrokerCommissionContract brokerCommissionContract);

    void commissionContractCancelledNotification(BrokerCommissionContract brokerCommissionContract);

    void commissionContractDeniedNotification(BrokerCommissionContract brokerCommissionContract);

    void deniedInvoiceNotification(Invoice invoice);

    void expiredAdNotification(Ad ad);

    void expiredBrokeringNotification(Brokering brokering);

    void expiredInvoiceNotification(Invoice invoice);

    void expiredLoanNotification(LoanPayment payment);

    void externalChannelPaymentConfirmed(PaymentRequestTicket ticket);

    void externalChannelPaymentPerformed(DoPaymentDTO dto, Payment payment);

    void externalChannelPaymentRequestExpired(PaymentRequestTicket ticket);

    void grantedLoanNotification(Loan loan);

    void guaranteeAcceptedNotification(Guarantee guarantee);

    void guaranteeCancelledNotification(Guarantee guarantee);

    void guaranteeDeniedNotification(Guarantee guarantee);

    void guaranteePendingIssuerNotification(Guarantee guarantee);

    void guaranteeStatusChangedNotification(Guarantee guarantee, Guarantee.Status prevStatus);

    void newCommissionContractNotification(BrokerCommissionContract brokerCommissionContract);

    void paymentAuthorizedOrDeniedNotification(Transfer transfer, boolean notifyTransactionFeedbackRequest);

    void paymentCancelledNotification(Transfer transfer);

    void paymentObligationPublishedNotification(PaymentObligation paymentObligation);

    void paymentObligationRejectedNotification(PaymentObligation paymentObligation);

    void paymentReceivedNotification(Payment payment);

    void posPinBlockedNotification(MemberPos memberPos);

    void receivedInvoiceNotification(Invoice invoice);

    void receivedReferenceNotification(Reference reference);

    void removedBrokeringNotification(ChangeBrokerDTO dto);

    void removedFromBrokerGroupNotification(Member member);

    void scheduledPaymentProcessingNotification(Transfer transfer, boolean notifyPayer, boolean notifyReceiver);

    /**
     * @param member the member who incoming and outgoing scheduled payments were cancelled.
     * @param notifyMember true if we must notify the member itself too.
     * @param membersToNotify set containing the members to be notified by the cancellation.
     * @param removedAccounts set with the removed accounts
     */
    void scheduledPaymentsCancelledNotification(final Member member, boolean notifyMember, Set<Member> membersToNotify, Set<MemberAccountType> removedAccounts);

    void transactionFeedBackAdminCommentsNotification(TransactionFeedback transactionFeedback);

    void transactionFeedBackReceivedNotification(TransactionFeedback transactionFeedback);

    void transactionFeedBackReplyNotification(TransactionFeedback transactionFeedback);

}
