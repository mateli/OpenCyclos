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
package nl.strohalm.cyclos.webservices.utils;

import java.util.Collection;
import java.util.List;

import nl.strohalm.cyclos.entities.access.MemberUser;
import nl.strohalm.cyclos.entities.access.User;
import nl.strohalm.cyclos.entities.accounts.Currency;
import nl.strohalm.cyclos.entities.accounts.transactions.Payment;
import nl.strohalm.cyclos.entities.accounts.transactions.PaymentRequestTicket;
import nl.strohalm.cyclos.entities.accounts.transactions.Ticket;
import nl.strohalm.cyclos.entities.accounts.transactions.Transfer;
import nl.strohalm.cyclos.entities.accounts.transactions.WebShopTicket;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomField;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.services.customization.MemberCustomFieldServiceLocal;
import nl.strohalm.cyclos.services.elements.ElementServiceLocal;
import nl.strohalm.cyclos.services.settings.SettingsServiceLocal;
import nl.strohalm.cyclos.utils.CustomFieldHelper;
import nl.strohalm.cyclos.webservices.WebServiceContext;
import nl.strohalm.cyclos.webservices.model.PaymentRequestTicketVO;
import nl.strohalm.cyclos.webservices.model.TicketVO;
import nl.strohalm.cyclos.webservices.model.WebShopTicketVO;
import nl.strohalm.cyclos.webservices.webshop.GenerateWebShopTicketParams;

/**
 * Utility class for tickets<br>
 * <b>WARN</b>: Be aware that this helper <b>doesn't</b> access the services through the security layer. They are all local services.
 * @author luis
 */
public class TicketHelper {

    private MemberCustomFieldServiceLocal memberCustomFieldServiceLocal;
    private SettingsServiceLocal          settingsServiceLocal;
    private ElementServiceLocal           elementServiceLocal;
    private CurrencyHelper                currencyHelper;
    private MemberHelper                  memberHelper;
    private CustomFieldHelper             customFieldHelper;

    public void setCurrencyHelper(final CurrencyHelper currencyHelper) {
        this.currencyHelper = currencyHelper;
    }

    public void setCustomFieldHelper(final CustomFieldHelper customFieldHelper) {
        this.customFieldHelper = customFieldHelper;
    }

    public void setElementServiceLocal(final ElementServiceLocal elementService) {
        elementServiceLocal = elementService;
    }

    public void setMemberCustomFieldServiceLocal(final MemberCustomFieldServiceLocal memberCustomFieldService) {
        memberCustomFieldServiceLocal = memberCustomFieldService;
    }

    public void setMemberHelper(final MemberHelper memberHelper) {
        this.memberHelper = memberHelper;
    }

    public void setSettingsServiceLocal(final SettingsServiceLocal settingsService) {
        settingsServiceLocal = settingsService;
    }

    /**
     * Convert a params object into a ticket
     */
    public WebShopTicket toTicket(final GenerateWebShopTicketParams params) {
        if (params == null) {
            return null;
        }
        final WebShopTicket ticket = new WebShopTicket();
        ticket.setAmount(params.getAmount());
        ticket.setCurrency(currencyHelper.resolve(params.getCurrency()));
        ticket.setClientAddress(params.getClientAddress());
        ticket.setDescription(params.getDescription());
        ticket.setReturnUrl(params.getReturnUrl());

        // Check the member restriction
        final Member restricted = WebServiceContext.getMember();
        if (restricted != null) {
            ticket.setTo(restricted);
        } else {
            try {
                final User user = elementServiceLocal.loadUser(params.getToUsername(), User.Relationships.ELEMENT);
                if (user instanceof MemberUser) {
                    ticket.setTo(((MemberUser) user).getMember());
                } else {
                    throw new IllegalArgumentException("Invalid username: " + params.getToUsername() + ". It isn't an instance of MemberUser");
                }
            } catch (final Exception e) {
                throw new IllegalArgumentException("Invalid member: " + params.getToUsername());
            }
        }
        return ticket;
    }

    public PaymentRequestTicketVO toVO(final PaymentRequestTicket ticket, final Collection<MemberCustomField> requiredCustomFields) {
        if (ticket == null) {
            return null;
        }
        final PaymentRequestTicketVO vo = new PaymentRequestTicketVO();
        fill(ticket, vo, true, requiredCustomFields);
        vo.setFromChannel(ticket.getFromChannel().getInternalName());
        vo.setToChannel(ticket.getToChannel().getInternalName());
        vo.setTraceData(ticket.getTraceData());
        return vo;
    }

    public WebShopTicketVO toVO(final WebShopTicket ticket, final Collection<MemberCustomField> requiredCustomFields) {
        if (ticket == null) {
            return null;
        }
        final WebShopTicketVO vo = new WebShopTicketVO();
        fill(ticket, vo, false, requiredCustomFields);
        vo.setMemberAddress(ticket.getMemberAddress());
        vo.setClientAddress(ticket.getClientAddress());
        vo.setReturnUrl(ticket.getReturnUrl());
        return vo;
    }

    private void fill(final Ticket ticket, final TicketVO vo, final boolean onlyBasicCustomFields, final Collection<MemberCustomField> requiredCustomFields) {
        final Transfer transfer = ticket.getTransfer();
        Currency currency = ticket.getCurrency();
        if (currency == null && ticket.getTransferType() != null) {
            currency = ticket.getTransferType().getFrom().getCurrency();
        }
        final LocalSettings localSettings = settingsServiceLocal.getLocalSettings();
        vo.setId(ticket.getId());
        vo.setTicket(ticket.getTicket());
        vo.setAwaitingAuthorization(transfer != null && transfer.getStatus() == Payment.Status.PENDING);
        boolean isAwaitingAuthorization = vo.getAwaitingAuthorization();
        vo.setOk(!isAwaitingAuthorization && ticket.getStatus() == Ticket.Status.OK);
        vo.setCancelled(ticket.getStatus() == Ticket.Status.CANCELLED);
        vo.setExpired(ticket.getStatus() == Ticket.Status.EXPIRED);
        vo.setPending(ticket.getStatus() == Ticket.Status.PENDING);

        final List<MemberCustomField> customFields = memberCustomFieldServiceLocal.list();
        if (ticket.getFrom() != null) {
            final Member from = elementServiceLocal.load(ticket.getFrom().getId(), Element.Relationships.GROUP);
            List<MemberCustomField> fields = customFieldHelper.onlyForGroup(customFields, from.getMemberGroup());
            if (onlyBasicCustomFields) {
                fields = customFieldHelper.onlyBasic(fields);
            }
            vo.setFromMember(memberHelper.toVO(from, fields, requiredCustomFields, false));
        }
        if (ticket.getTo() != null) {
            final Member to = (Member) elementServiceLocal.load(ticket.getTo().getId(), Element.Relationships.GROUP);
            List<MemberCustomField> fields = customFieldHelper.onlyForGroup(customFields, to.getMemberGroup());
            if (onlyBasicCustomFields) {
                fields = customFieldHelper.onlyBasic(fields);
            }
            vo.setToMember(memberHelper.toVO(to, fields, requiredCustomFields, false));
        }
        vo.setAmount(ticket.getAmount());
        if (currency == null) {
            vo.setFormattedAmount(localSettings.getNumberConverter().toString(ticket.getAmount()));
        } else {
            vo.setFormattedAmount(localSettings.getUnitsConverter(currency.getPattern()).toString(ticket.getAmount()));
        }
        vo.setDescription(ticket.getDescription());
        vo.setCreationDate(ticket.getCreationDate());
        vo.setFormattedCreationDate(localSettings.getDateTimeConverter().toString(ticket.getCreationDate()));
    }
}
