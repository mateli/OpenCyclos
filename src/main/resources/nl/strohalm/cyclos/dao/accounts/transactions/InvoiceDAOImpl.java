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
package nl.strohalm.cyclos.dao.accounts.transactions;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.strohalm.cyclos.dao.BaseDAOImpl;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.AccountOwner;
import nl.strohalm.cyclos.entities.accounts.Currency;
import nl.strohalm.cyclos.entities.accounts.SystemAccountOwner;
import nl.strohalm.cyclos.entities.accounts.transactions.Invoice;
import nl.strohalm.cyclos.entities.accounts.transactions.InvoiceQuery;
import nl.strohalm.cyclos.entities.accounts.transactions.InvoiceQuery.Direction;
import nl.strohalm.cyclos.entities.accounts.transactions.InvoiceSummaryDTO;
import nl.strohalm.cyclos.entities.accounts.transactions.Payment;
import nl.strohalm.cyclos.entities.accounts.transactions.PaymentFilter;
import nl.strohalm.cyclos.entities.accounts.transactions.Transfer;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.reports.InvoiceSummaryType;
import nl.strohalm.cyclos.services.transactions.TransactionSummaryVO;
import nl.strohalm.cyclos.utils.Period;
import nl.strohalm.cyclos.utils.hibernate.HibernateHelper;

import org.apache.commons.collections.CollectionUtils;

/**
 * Implementation class for invoice DAO
 * @author rafael
 */
public class InvoiceDAOImpl extends BaseDAOImpl<Invoice> implements InvoiceDAO {

    public InvoiceDAOImpl() {
        super(Invoice.class);
    }

    public TransactionSummaryVO getSummary(final InvoiceSummaryDTO dto) {
        final Direction direction = dto.getDirection();
        final AccountOwner owner = dto.getOwner();
        final AccountOwner relatedOwner = dto.getRelatedOwner();
        PaymentFilter filter = dto.getFilter();
        final Period period = dto.getPeriod();
        final Invoice.Status status = dto.getStatus();

        final HashMap<String, Object> namedParameters = new HashMap<String, Object>();

        final StringBuilder hql = new StringBuilder("select new " + TransactionSummaryVO.class.getName() + "(count(*), sum(e.amount)) from " + getEntityType().getName() + " e ");

        hql.append(" where e." + (direction == Direction.INCOMING ? "to" : "from") + "Member" + (owner instanceof SystemAccountOwner ? " is null " : " = :owner"));
        namedParameters.put("owner", owner);

        if (dto.getCurrency() != null) {
            hql.append(" and (exists (select tt.id from TransferType tt where tt = e.transferType and tt.from.currency = :currency) or exists (select at.id from AccountType at where at = e.destinationAccountType and at.currency = :currency))");
            namedParameters.put("currency", dto.getCurrency());
        }

        if (dto.isFromMemberToMember()) {
            hql.append(" and e." + (direction == Direction.INCOMING ? "from" : "to") + "Member is not null");
        } else if (relatedOwner != null) {
            hql.append(" and e." + (direction == Direction.INCOMING ? "from" : "to") + "Member" + (relatedOwner instanceof SystemAccountOwner ? " is null " : " = :relatedOwner"));
            namedParameters.put("relatedOwner", relatedOwner);
        }

        if (CollectionUtils.isNotEmpty(dto.getTypes())) {
            hql.append(" and ( ");
            hql.append("    e.destinationAccountType in (:types) or ");
            hql.append("    exists(select i.id from Invoice i where i.transferType.from in (:types) and i = e) or ");
            hql.append("    exists(select i.id from Invoice i where i.transferType.to in (:types) and i = e) ");
            hql.append(" ) ");
            namedParameters.put("types", dto.getTypes());
        }

        if (status != null) {
            hql.append(" and e.status = :status ");
            namedParameters.put("status", status);
        }

        HibernateHelper.addPeriodParameterToQuery(hql, namedParameters, "e.date", period);

        if (filter != null) {
            filter = getFetchDao().fetch(filter, PaymentFilter.Relationships.TRANSFER_TYPES);
            HibernateHelper.addInParameterToQuery(hql, namedParameters, "e.transferType", filter.getTransferTypes());
        }
        return uniqueResult(hql.toString(), namedParameters);
    }

    public TransactionSummaryVO getSummaryByType(final Currency currency, final InvoiceSummaryType invoiceSummaryType, final Collection<MemberGroup> memberGroups) {
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        String fromMember = " is not null ";
        String toMember = " is not null ";

        switch (invoiceSummaryType) {
            case SYSTEM_INCOMING:
                toMember = " is null ";
                break;
            case SYSTEM_OUTGOING:
                fromMember = " is null ";
                break;
        }

        final StringBuilder hql = new StringBuilder("select new " + TransactionSummaryVO.class.getName() + "(count(*), sum(i.amount)) from Invoice i ");
        hql.append(" where i.fromMember " + fromMember + " and i.toMember " + toMember);
        hql.append(" and (exists (select tt.id from TransferType tt where tt = i.transferType and tt.from.currency = :currency) or exists (select at.id from AccountType at where at = i.destinationAccountType and at.currency = :currency))");
        namedParameters.put("currency", currency);
        if (memberGroups != null && !memberGroups.isEmpty()) {
            switch (invoiceSummaryType) {
                case SYSTEM_INCOMING:
                    hql.append(" and i.fromMember.group in (:memberGroups) ");
                    break;
                case SYSTEM_OUTGOING:
                    hql.append(" and i.toMember.group in (:memberGroups) ");
                    break;
                case MEMBER:
                    hql.append(" and (i.fromMember.group in (:memberGroups) or i.toMember.group in (:memberGroups)) ");
                    break;
            }
            namedParameters.put("memberGroups", memberGroups);
        }
        namedParameters.put("status", Invoice.Status.OPEN);
        hql.append(" and i.status = :status");

        return uniqueResult(hql.toString(), namedParameters);
    }

    public Invoice loadByPayment(final Payment payment, final Relationship... fetch) throws EntityNotFoundException {
        String property;
        if (payment instanceof Transfer) {
            property = "transfer";
        } else {
            property = "scheduledPayment";
        }
        final Map<String, ?> params = Collections.singletonMap("payment", payment);
        final Invoice invoice = uniqueResult("from Invoice i where i." + property + " = :payment", params);
        if (invoice == null) {
            throw new EntityNotFoundException(getEntityType());
        }
        return getFetchDao().fetch(invoice, fetch);
    }

    public List<Invoice> search(final InvoiceQuery query) {
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        final Set<Relationship> fetch = query.getFetch();
        final StringBuilder hql = new StringBuilder("select i from " + getEntityType().getName() + " i");
        hql.append(" left join i.fromMember fm");
        hql.append(" left join fm.group fmg");
        hql.append(" left join i.toMember tm");
        hql.append(" left join tm.group tmg");
        HibernateHelper.appendJoinFetch(hql, getEntityType(), "i", fetch);
        hql.append(" where 1 = 1");
        HibernateHelper.addLikeParameterToQuery(hql, namedParameters, "i.description", query.getDescription());
        HibernateHelper.addParameterToQuery(hql, namedParameters, "i.status", query.getStatus());
        HibernateHelper.addParameterToQuery(hql, namedParameters, "i.transferType", query.getTransferType());
        HibernateHelper.addParameterToQuery(hql, namedParameters, "i.transfer.transactionNumber", query.getTransactionNumber());
        HibernateHelper.addPeriodParameterToQuery(hql, namedParameters, "i.date", query.getPeriod());

        // With payments scheduled for inside the period
        if (query.getPaymentPeriod() != null) {
            hql.append(" and exists (select ip.id from InvoicePayment ip where ip.invoice = i");
            HibernateHelper.addPeriodParameterToQuery(hql, namedParameters, "ip.date", query.getPaymentPeriod());
            hql.append(" ) ");
        }

        final boolean isIncoming = query.getDirection() == Direction.INCOMING;
        final String owner = (isIncoming) ? "to" : "from";
        final String related = (isIncoming) ? "from" : "to";
        if (query.getOwner() != null) {
            if (query.getOwner() instanceof SystemAccountOwner) {
                hql.append(" and i." + owner + "Member is null ");
            } else {
                HibernateHelper.addParameterToQuery(hql, namedParameters, "i." + owner + "Member", query.getOwner());
            }
        }
        if (query.getRelatedOwner() != null) {
            if (query.getRelatedOwner() instanceof SystemAccountOwner) {
                hql.append(" and i." + related + "Member is null ");
            } else {
                HibernateHelper.addParameterToQuery(hql, namedParameters, "i." + related + "Member", query.getRelatedOwner());
            }
        }
        if (query.getGroups() != null && !query.getGroups().isEmpty()) {
            hql.append(" and (fmg in (:groups) or tmg in (:groups)) ");
            namedParameters.put("groups", query.getGroups());
        }

        // Operated by
        if (query.getBy() != null) {
            hql.append(" and (i.performedBy = :by or i.sentBy = :by)");
            namedParameters.put("by", query.getBy());
        }

        HibernateHelper.appendOrder(hql, "i.date desc");
        return list(query, hql.toString(), namedParameters);
    }

}
