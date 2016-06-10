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
package nl.strohalm.cyclos.controls.accounts.details;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.entities.accounts.AccountStatus;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferQuery;
import nl.strohalm.cyclos.entities.customization.fields.CustomField;
import nl.strohalm.cyclos.entities.customization.fields.CustomFieldPossibleValue;
import nl.strohalm.cyclos.entities.customization.fields.CustomFieldValue;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.settings.LocalSettings.MemberResultDisplay;
import nl.strohalm.cyclos.services.accounts.AccountDateDTO;
import nl.strohalm.cyclos.services.accounts.GetTransactionsDTO;
import nl.strohalm.cyclos.utils.DateHelper;
import nl.strohalm.cyclos.utils.EntityHelper;
import nl.strohalm.cyclos.utils.Period;
import nl.strohalm.cyclos.utils.conversion.IdConverter;
import nl.strohalm.cyclos.utils.query.QueryParameters;

import org.apache.commons.lang.StringUtils;

/**
 * Action used to print transactions
 * @author luis
 */
public class PrintAccountHistoryAction extends AccountHistoryAction {

    @Override
    protected Integer pageSize(final ActionContext context) {
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected QueryParameters prepareForm(final ActionContext context) {
        final TransferQuery query = (TransferQuery) super.prepareForm(context);
        final HttpServletRequest request = context.getRequest();

        // Fetch the data to show on the screen
        if (query.getPaymentFilter() != null) {
            query.setPaymentFilter(paymentFilterService.load(query.getPaymentFilter().getId()));
        }
        if (query.getMember() != null) {
            query.setMember((Member) elementService.load(query.getMember().getId()));
        }
        if (query.getBy() != null) {
            query.setBy(elementService.load(query.getBy().getId()));
        }

        // Get the period begin date
        final Period period = query.getPeriod();
        final GetTransactionsDTO dto = new GetTransactionsDTO(query.getOwner(), query.getType(), period);
        dto.setRelatedToMember(query.getMember());
        dto.setPaymentFilter(query.getPaymentFilter());
        dto.setBy(query.getBy());

        // Get additional data for the printing
        final AccountStatus status = (AccountStatus) request.getAttribute("status");
        if (period != null && period.getBegin() != null) {
            final BigDecimal initialBalance = accountService.getBalance(new AccountDateDTO(status.getAccount(), period.getBegin()));
            request.setAttribute("initialBalance", initialBalance);
        }
        if (period != null && period.getEnd() != null) {
            final Calendar end = DateHelper.getDayEnd(period.getEnd());
            final BigDecimal finalBalance = accountService.getBalance(new AccountDateDTO(status.getAccount(), end));
            request.setAttribute("finalBalance", finalBalance);
        }

        // Get the custom fields search map
        final Map<String, String> customValueFilters = new LinkedHashMap<String, String>();
        final Collection<nl.strohalm.cyclos.utils.CustomFieldHelper.Entry> entries = (Collection<nl.strohalm.cyclos.utils.CustomFieldHelper.Entry>) request.getAttribute("customFieldsForSearch");
        for (final nl.strohalm.cyclos.utils.CustomFieldHelper.Entry entry : entries) {
            final CustomField field = entry.getField();
            final CustomFieldValue fieldValue = entry.getValue();
            if (fieldValue == null) {
                continue;
            }
            String value = null;
            if (field.getType() == CustomField.Type.ENUMERATED) {
                // The filter may be done using a list of comma-separated identifiers. Get the value for them
                final String[] parts = StringUtils.split(fieldValue.getValue(), ",");
                if (parts == null) {
                    continue;
                }
                final Collection<CustomFieldPossibleValue> possibleValues = paymentCustomFieldService.loadPossibleValues(Arrays.asList(EntityHelper.toIds(parts)));
                boolean first = true;
                final StringBuilder sb = new StringBuilder();
                for (final CustomFieldPossibleValue possibleValue : possibleValues) {
                    if (first) {
                        first = false;
                    } else {
                        sb.append(", ");
                    }
                    sb.append(possibleValue.getValue());
                }
                value = sb.toString();
            } else if (field.getType() == CustomField.Type.MEMBER) {
                Long memberId = null;
                if (fieldValue.getMemberValue() != null) {
                    memberId = fieldValue.getMemberValue().getId();
                } else {
                    memberId = IdConverter.instance().valueOf(fieldValue.getValue());
                }
                if (memberId != null) {
                    final Element element = elementService.load(memberId);
                    if (element instanceof Member) {
                        final MemberResultDisplay memberResultDisplay = settingsService.getLocalSettings().getMemberResultDisplay();
                        if (memberResultDisplay == MemberResultDisplay.NAME) {
                            value = element.getName();
                        } else {
                            value = element.getUsername();
                        }
                    }
                }

            } else {
                value = fieldValue.getValue();
            }
            customValueFilters.put(field.getName(), value);
        }
        request.setAttribute("customValueFilters", customValueFilters);

        return query;
    }
}
