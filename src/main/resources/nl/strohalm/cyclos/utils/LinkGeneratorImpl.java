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
package nl.strohalm.cyclos.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.concurrent.Callable;

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.accounts.guarantees.Certification;
import nl.strohalm.cyclos.entities.accounts.guarantees.Guarantee;
import nl.strohalm.cyclos.entities.accounts.guarantees.PaymentObligation;
import nl.strohalm.cyclos.entities.accounts.loans.Loan;
import nl.strohalm.cyclos.entities.accounts.transactions.Invoice;
import nl.strohalm.cyclos.entities.accounts.transactions.ScheduledPayment;
import nl.strohalm.cyclos.entities.accounts.transactions.Transfer;
import nl.strohalm.cyclos.entities.ads.Ad;
import nl.strohalm.cyclos.entities.alerts.ErrorLogEntry;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.GroupFilter;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.groups.SystemGroup;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.GeneralReference;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.Operator;
import nl.strohalm.cyclos.entities.members.PendingEmailChange;
import nl.strohalm.cyclos.entities.members.TransactionFeedback;
import nl.strohalm.cyclos.entities.members.TransactionFeedbackRequest;
import nl.strohalm.cyclos.entities.members.brokerings.BrokerCommissionContract;
import nl.strohalm.cyclos.services.groups.GroupService;
import nl.strohalm.cyclos.services.settings.SettingsService;
import nl.strohalm.cyclos.utils.access.LoggedUser;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Implementation for link generator
 * @author jefferson
 */
public class LinkGeneratorImpl implements LinkGenerator {

    private static final Log LOG = LogFactory.getLog(LinkGeneratorImpl.class);
    private SettingsService  settingsService;
    private MessageHelper    messageHelper;
    private GroupService     groupService;

    @Override
    public String generateForApplicationRoot(final Element element) {
        return buildTagFor(getRootUrl(element));
    }

    @Override
    public String generateLinkFor(final Element element, final Entity entity) throws EntityNotFoundException {
        String relativePath = "";
        if (entity instanceof Ad) {
            relativePath = "viewAd?id=";
        } else if (entity instanceof Member) {
            relativePath = "profile?memberId=";
        } else if (entity instanceof Invoice) {
            relativePath = "invoiceDetails?invoiceId=";
        } else if (entity instanceof Transfer) {
            relativePath = "viewTransaction?transferId=";
        } else if (entity instanceof GeneralReference) {
            relativePath = "generalReferenceDetails?referenceId=";
        } else if (entity instanceof TransactionFeedback) {
            relativePath = "transactionFeedbackDetails?referenceId=";
        } else if (entity instanceof TransactionFeedbackRequest) {
            final TransactionFeedbackRequest transactionFeedbackRequest = (TransactionFeedbackRequest) entity;
            String paramName;
            if (transactionFeedbackRequest.getPayment() instanceof ScheduledPayment) {
                paramName = "scheduledPaymentId";
            } else {
                paramName = "transferId";
            }
            relativePath = "transactionFeedbackDetails?" + paramName + "=";
        } else if (entity instanceof Loan) {
            relativePath = "loanDetails?loanId=";
        } else if (entity instanceof ErrorLogEntry) {
            relativePath = "viewErrorLogEntry?entryId=";
        } else if (entity instanceof BrokerCommissionContract) {
            relativePath = "editBrokerCommissionContract?brokerCommissionContractId=";
        } else if (entity instanceof Certification) {
            relativePath = "editCertification?certificationId=";
        } else if (entity instanceof Guarantee) {
            relativePath = "guaranteeDetails?guaranteeId=";
        } else if (entity instanceof PaymentObligation) {
            relativePath = "editPaymentObligation?paymentObligationId=";
        } else {
            throw new EntityNotFoundException(entity.getClass());
        }

        final String path = "do/" + element.getNature().name().toLowerCase() + "/" + relativePath + entity.getId();
        final String baseUrl = getBaseUrl(element);
        final String pathPart = "/do/redirectFromMessage?userId=" + element.getId() + "&path=" + StringHelper.encodeUrl("/" + path);
        return buildTagFor(resolveUrl(baseUrl, pathPart));
    }

    @Override
    public String generateLinkForMailChangeValidation(final PendingEmailChange change) {
        return buildTagFor(getMailChangeValidationUrl(change));
    }

    @Override
    public String generateLinkForMailValidation(final MemberGroup group, final String key) {
        return buildTagFor(getMailValidationUrl(group, key));
    }

    @Override
    public String getMailChangeValidationUrl(final PendingEmailChange change) {
        final String baseUrl = getBaseUrl(change.getMember());
        final String pathPart = "/do/redirectFromMessage?userId=" + change.getMember().getId() + "&path=/do/member/validateEmailChange?key=" + change.getValidationKey();
        return resolveUrl(baseUrl, pathPart);
    }

    @Override
    public String getMailValidationUrl(final MemberGroup group, final String key) {
        final String baseUrl = getBaseUrl(group);
        final String pathPart = "/do/validateRegistration?key=" + key;
        return resolveUrl(baseUrl, pathPart);
    }

    @Override
    public String getRootUrl(final Element element) {
        return getBaseUrl(element) + "/";
    }

    public void setGroupService(final GroupService groupService) {
        this.groupService = groupService;
    }

    public void setMessageHelper(final MessageHelper messageHelper) {
        this.messageHelper = messageHelper;
    }

    public void setSettingsService(final SettingsService settingsService) {
        this.settingsService = settingsService;
    }

    /**
     * Build the anchor tag for the given url, using a message key 'message.link.label' to retrieve the label
     */
    private String buildTagFor(final String url) {
        final String label = messageHelper.message("message.link.label");
        return buildTagFor(url, label);
    }

    /**
     * Build the anchor tag for the given url, with a given label key
     */
    private String buildTagFor(final String url, final String label) {
        return "<a class=\"default\" href=\"" + url + "\">" + label + "</a>";
    }

    private String getBaseUrl(final Element element) {
        SystemGroup group;
        if (element instanceof Operator) {
            group = (SystemGroup) loadGroup(((Operator) element).getMember().getGroup().getId());
        } else {
            group = (SystemGroup) loadGroup(element.getGroup().getId());
        }
        return getBaseUrl(group);
    }

    private String getBaseUrl(SystemGroup group) {
        // As there is no FetchServiceLocal here, load the group again with the correct relationships
        group = (SystemGroup) loadGroup(group.getId());

        // Attempt by group
        String url = group.getRootUrl();

        if (StringUtils.isEmpty(url)) {
            // Not found on group. Attempt on group filters
            final Collection<GroupFilter> groupFilters = group.getGroupFilters();
            for (final GroupFilter groupFilter : groupFilters) {
                if (StringUtils.isNotEmpty(groupFilter.getRootUrl())) {
                    url = groupFilter.getRootUrl();
                    break;
                }
            }
        }

        if (StringUtils.isEmpty(url)) {
            // Get the system default
            url = settingsService.getLocalSettings().getRootUrl();
        }

        if (StringUtils.isEmpty(url)) {
            // None found?!? Should be required on settings!!!
            LOG.error("No root url was found when generating a link");
            return "";
        }

        // Make sure the trailing / is removed
        return StringUtils.removeEnd(url, "/");
    }

    private Group loadGroup(final Long id) {
        return LoggedUser.runAsSystem(new Callable<Group>() {
            @Override
            public Group call() throws Exception {
                return groupService.load(id, Group.Relationships.GROUP_FILTERS);
            }
        });
    }

    private String resolveUrl(final String baseUrl, String pathPart) {
        if (baseUrl.contains("?")) {
            // As the base url already contains a '?', it is assumed that the rest is a parameter to it, like:
            // http://baseUrl?param=<the rest of the path should be escaped>
            try {
                pathPart = URLEncoder.encode(pathPart, "UTF-8");
            } catch (final UnsupportedEncodingException e) {
                throw new IllegalStateException(e);
            }
        }
        return baseUrl + pathPart;
    }
}
