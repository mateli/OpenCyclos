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
package nl.strohalm.cyclos.controls.members.bulk;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.controls.members.SearchMembersAction;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomFieldValue;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.GroupQuery;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.FullTextMemberQuery;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsChangeListener;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsEvent;
import nl.strohalm.cyclos.services.elements.BulkMemberActionResultVO;
import nl.strohalm.cyclos.services.groups.GroupService;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.MapBean;
import nl.strohalm.cyclos.utils.conversion.CoercionHelper;
import nl.strohalm.cyclos.utils.validation.RequiredError;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Action to apply the bulk change group for members
 * @author luis
 */
public class MemberBulkChangeGroupAction extends BaseFormAction implements LocalSettingsChangeListener {

    static void prepare(final ActionContext context, final GroupService groupService) {
        final HttpServletRequest request = context.getRequest();
        final GroupQuery query = new GroupQuery();
        query.setNatures(Group.Nature.MEMBER, Group.Nature.BROKER);
        query.setStatus(Group.Status.NORMAL);
        request.setAttribute("possibleNewGroups", groupService.search(query));
    }

    private DataBinder<FullTextMemberQuery> dataBinder;

    private ReadWriteLock                   lock = new ReentrantReadWriteLock(true);

    public DataBinder<FullTextMemberQuery> getDataBinder() {
        try {
            lock.readLock().lock();

            if (dataBinder == null) {
                final LocalSettings localSettings = settingsService.getLocalSettings();
                dataBinder = SearchMembersAction.memberQueryDataBinder(localSettings);
            }
            return dataBinder;
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public void onLocalSettingsUpdate(final LocalSettingsEvent event) {
        try {
            lock.writeLock().lock();
            dataBinder = null;
        } finally {
            lock.writeLock().unlock();
        }

    }

    @Override
    protected void formAction(final ActionContext context) throws Exception {
        final MemberBulkActionsForm form = context.getForm();

        // Read the user input
        final MapBean bean = form.getChangeGroup();
        final FullTextMemberQuery query = getDataBinder().readFromString(form.getQuery());
        final MemberGroup newGroup = groupService.load(CoercionHelper.coerce(Long.class, bean.get("newGroup")));
        final String comments = StringUtils.trimToNull((String) bean.get("comments"));

        final BulkMemberActionResultVO results = elementService.bulkChangeMemberGroup(query, newGroup, comments);
        context.sendMessage("member.bulkActions.groupChanged", results.getChanged(), results.getUnchanged(), newGroup.getName());

        // Clear the change group parameters
        form.getChangeGroup().clear();
    }

    @Override
    protected void prepareForm(final ActionContext context) throws Exception {
        prepare(context, groupService);
    }

    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
    protected void validateForm(final ActionContext context) {
        final MemberBulkActionsForm form = context.getForm();

        final FullTextMemberQuery query = getDataBinder().readFromString(form.getQuery());

        final Collection<MemberCustomFieldValue> customValues = (Collection<MemberCustomFieldValue>) query.getCustomValues();
        for (final Iterator it = customValues.iterator(); it.hasNext();) {
            final MemberCustomFieldValue fieldValue = (MemberCustomFieldValue) it.next();
            if (StringUtils.isEmpty(fieldValue.getValue())) {
                it.remove();
            }
        }
        if (CollectionUtils.isEmpty(query.getGroupFilters()) && CollectionUtils.isEmpty(query.getGroups()) && query.getBroker() == null && CollectionUtils.isEmpty(customValues)) {
            throw new ValidationException("member.bulkActions.error.emptyQuery");
        }

        final MapBean bean = form.getChangeGroup();
        final MemberGroup newGroup = CoercionHelper.coerce(MemberGroup.class, bean.get("newGroup"));
        final String comments = StringUtils.trimToNull((String) bean.get("comments"));
        if (newGroup == null || newGroup.isTransient()) {
            throw new ValidationException("newGroup", "changeGroup.new", new RequiredError());
        }
        if (StringUtils.isEmpty(comments)) {
            throw new ValidationException("comments", "remark.comments", new RequiredError());
        }
    }

}
