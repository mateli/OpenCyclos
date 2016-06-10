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
package nl.strohalm.cyclos.controls.members.sms;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseAjaxAction;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.services.sms.SmsMailingService;

import org.apache.commons.lang.ArrayUtils;
import org.json.simple.JSONObject;

/**
 * Searches SMS mailing variables and returns a list as an JSON
 * @author jcomas
 */
public class SearchSmsMailingVariablesAjaxAction extends BaseAjaxAction {

    protected SmsMailingService smsMailingService;

    @Inject
    public final void setSmsMailingService(final SmsMailingService smsMailingService) {
        this.smsMailingService = smsMailingService;
    }

    @Override
    protected ContentType contentType() {
        return ContentType.JSON;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void renderContent(final ActionContext context) throws Exception {

        final List<MemberGroup> groups = resolveGroups(context);
        Map<String, String> variables = null;
        final Member m = resolveMember(context);
        final boolean isToMember = m != null;

        if (groups != null || context.isBroker() && !isToMember) {
            variables = smsMailingService.getSmsTextVariables(groups);
        } else {
            if (m != null) {
                variables = smsMailingService.getSmsTextVariables(m);
            }
        }

        // We need to pass an ordered structure. JSONObject doesn't preserve the map order, so we pass a list of entries were each entry is a list of
        // [key, value].

        final JSONObject json = new JSONObject();
        final List<List<String>> entries = new ArrayList<List<String>>();

        for (final Map.Entry<String, String> entry : variables.entrySet()) {
            final ArrayList<String> e = new ArrayList<String>();
            e.add(entry.getKey());
            e.add(entry.getValue());
            entries.add(e);
        }
        json.put("entries", entries);
        responseHelper.writeJSON(context.getResponse(), json.toJSONString());
    }

    /**
     * Resolves the possible groups for the logged user to see
     */
    private List<MemberGroup> resolveGroups(final ActionContext context) {
        // Ensure that only normal groups (not removed) are used
        List<MemberGroup> groups = null;
        final SearchSmsMailingVariablesAjaxForm form = context.getForm();
        if (ArrayUtils.isNotEmpty(form.getGroupIds())) {
            groups = new ArrayList<MemberGroup>();
            for (final Long id : form.getGroupIds()) {
                if (id > 0) {
                    groups.add((MemberGroup) groupService.load(id));
                }
            }
        }
        return groups;
    }

    private Member resolveMember(final ActionContext context) {
        final SearchSmsMailingVariablesAjaxForm form = context.getForm();
        Member member = null;
        final long memberId = form.getMemberId();
        if (memberId > 0) {
            try {
                member = elementService.load(memberId);
            } catch (final Exception e) {
                member = null;
            }
        }
        return member;
    }
}
