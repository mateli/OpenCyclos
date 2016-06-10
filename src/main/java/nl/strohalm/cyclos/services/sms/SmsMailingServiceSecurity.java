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
package nl.strohalm.cyclos.services.sms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import nl.strohalm.cyclos.access.AdminMemberPermission;
import nl.strohalm.cyclos.access.BrokerPermission;
import nl.strohalm.cyclos.entities.groups.AdminGroup;
import nl.strohalm.cyclos.entities.groups.GroupQuery;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.sms.SmsMailing;
import nl.strohalm.cyclos.entities.sms.SmsMailingQuery;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.BaseServiceSecurity;
import nl.strohalm.cyclos.services.groups.GroupServiceLocal;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.utils.access.PermissionHelper;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.commons.collections.CollectionUtils;

/**
 * Security implementation for {@link SmsMailingService}
 * 
 * @author jcomas
 */
public class SmsMailingServiceSecurity extends BaseServiceSecurity implements SmsMailingService {
    private SmsMailingServiceLocal smsMailingService;
    private GroupServiceLocal      groupService;

    @Override
    public Map<String, String> getSmsTextVariables(List<MemberGroup> groups) {
        groups = checkGroups(groups);
        return smsMailingService.getSmsTextVariables(groups);
    }

    @Override
    public Map<String, String> getSmsTextVariables(final Member member) {
        permissionService.checkManages(member);
        return smsMailingService.getSmsTextVariables(member);
    }

    @Override
    public List<SmsMailing> search(final SmsMailingQuery query) {
        if (!permissionService.hasPermission(AdminMemberPermission.SMS_MAILINGS_VIEW, BrokerPermission.SMS_MAILINGS_FREE_SMS_MAILINGS, BrokerPermission.SMS_MAILINGS_PAID_SMS_MAILINGS)) {
            throw new PermissionDeniedException();
        }
        applySearchRestrictions(query);
        return smsMailingService.search(query);
    }

    @Override
    public SmsMailing send(final SmsMailing smsMailing) {
        if (smsMailing.isSingleMember()) {
            permissionService.permission(smsMailing.getMember())
                    .admin(AdminMemberPermission.SMS_MAILINGS_FREE_SMS_MAILINGS)
                    .broker(BrokerPermission.SMS_MAILINGS_FREE_SMS_MAILINGS)
                    .check();
            smsMailing.setGroups(Collections.<MemberGroup> emptyList());
        } else {
            if (smsMailing.isFree()) {

                permissionService.permission()
                        .admin(AdminMemberPermission.SMS_MAILINGS_FREE_SMS_MAILINGS)
                        .broker(BrokerPermission.SMS_MAILINGS_FREE_SMS_MAILINGS)
                        .check();
            } else {
                permissionService.permission()
                        .admin(AdminMemberPermission.SMS_MAILINGS_PAID_SMS_MAILINGS)
                        .broker(BrokerPermission.SMS_MAILINGS_PAID_SMS_MAILINGS)
                        .check();
            }
            smsMailing.setGroups(checkGroups(new ArrayList<MemberGroup>(smsMailing.getGroups())));
        }

        return smsMailingService.send(smsMailing);
    }

    public void setGroupServiceLocal(final GroupServiceLocal groupService) {
        this.groupService = groupService;
    }

    public void setSmsMailingServiceLocal(final SmsMailingServiceLocal smsMailingService) {
        this.smsMailingService = smsMailingService;
    }

    @Override
    public void validate(final SmsMailing smsMailing, final boolean isMemberRequired) throws ValidationException {
        // Nothing to check
        if (!isMemberRequired) {
            smsMailing.setGroups(checkGroups(new ArrayList<MemberGroup>(smsMailing.getGroups())));
        }
        smsMailingService.validate(smsMailing, isMemberRequired);
    }

    private void applySearchRestrictions(final SmsMailingQuery query) {
        // the search is allowed only for admins and brokers
        if (LoggedUser.isBroker()) {
            // Ensure that brokers only see mailings sent by himself
            query.setBroker((Member) LoggedUser.element());
            Member member = fetchService.fetch(query.getMember(), Member.Relationships.BROKER);
            if (member != null && !LoggedUser.element().equals(member.getBroker())) {
                throw new PermissionDeniedException();
            }
        } else {
            // Ensure admins will only see groups he can manage
            final AdminGroup adminGroup = fetchService.fetch((AdminGroup) LoggedUser.group(), AdminGroup.Relationships.MANAGES_GROUPS);
            final Collection<MemberGroup> groups = query.getGroups();
            if (CollectionUtils.isEmpty(groups)) {
                query.setGroups(adminGroup.getManagesGroups());
            } else {
                groups.retainAll(adminGroup.getManagesGroups());
            }
        }
    }

    @SuppressWarnings("unchecked")
    private List<MemberGroup> checkGroups(List<MemberGroup> groups) {
        final GroupQuery query = new GroupQuery();
        if (LoggedUser.isAdministrator()) {
            query.setManagedBy((AdminGroup) LoggedUser.group());
        } else {
            query.setBroker(LoggedUser.member());
        }
        query.setOnlyActive(true);
        List<MemberGroup> allowedGroups = (List<MemberGroup>) groupService.search(query);
        groups = new ArrayList<MemberGroup>(PermissionHelper.checkSelection(allowedGroups, groups));
        return groups;
    }
}
