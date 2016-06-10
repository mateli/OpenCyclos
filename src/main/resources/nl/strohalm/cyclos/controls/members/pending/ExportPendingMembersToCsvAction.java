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
package nl.strohalm.cyclos.controls.members.pending;

import java.util.List;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseCsvAction;
import nl.strohalm.cyclos.entities.access.User;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomField;
import nl.strohalm.cyclos.entities.members.PendingMember;
import nl.strohalm.cyclos.entities.members.PendingMemberQuery;
import nl.strohalm.cyclos.entities.settings.AccessSettings;
import nl.strohalm.cyclos.entities.settings.AccessSettings.UsernameGeneration;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsEvent;
import nl.strohalm.cyclos.services.customization.MemberCustomFieldService;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.conversion.CustomFieldConverter;
import nl.strohalm.cyclos.utils.csv.CSVWriter;

/**
 * Action used to export a member search result as csv
 * @author luis
 */
public class ExportPendingMembersToCsvAction extends BaseCsvAction {

    private DataBinder<PendingMemberQuery> dataBinder;
    private MemberCustomFieldService       memberCustomFieldService;

    public MemberCustomFieldService getMemberCustomFieldService() {
        return memberCustomFieldService;
    }

    @Override
    public void onLocalSettingsUpdate(final LocalSettingsEvent event) {
        super.onLocalSettingsUpdate(event);
        dataBinder = null;
    }

    @Inject
    public void setMemberCustomFieldService(final MemberCustomFieldService memberCustomFieldService) {
        this.memberCustomFieldService = memberCustomFieldService;
    }

    @Override
    protected List<?> executeQuery(final ActionContext context) {
        final SearchPendingMembersForm form = context.getForm();
        final PendingMemberQuery query = getDataBinder().readFromString(form.getQuery());
        query.fetch(PendingMember.Relationships.CUSTOM_VALUES, PendingMember.Relationships.MEMBER, RelationshipHelper.nested(PendingMember.Relationships.BROKER));
        return elementService.search(query);
    }

    @Override
    protected String fileName(final ActionContext context) {
        final User loggedUser = context.getUser();
        return "pending_members_" + loggedUser.getUsername() + ".csv";
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    protected CSVWriter resolveCSVWriter(final ActionContext context) {
        final LocalSettings settings = settingsService.getLocalSettings();
        final AccessSettings accessSettings = settingsService.getAccessSettings();

        final CSVWriter<PendingMember> csv = CSVWriter.instance(PendingMember.class, settings);
        if (accessSettings.getUsernameGeneration() == UsernameGeneration.NONE) {
            csv.addColumn(context.message("member.username"), "username");
        }
        csv.addColumn(context.message("member.name"), "name");
        csv.addColumn(context.message("member.email"), "email");
        csv.addColumn(context.message("member.creationDate"), "creationDate", settings.getDateConverter());
        csv.addColumn(context.message("member.group"), "memberGroup.name");
        final List<MemberCustomField> customFields = memberCustomFieldService.list();
        for (final MemberCustomField field : customFields) {
            csv.addColumn(field.getName(), "customValues", new CustomFieldConverter(field, elementService, settings));
        }
        if (context.isAdmin()) {
            csv.addColumn(context.message("member.brokerUsername"), "broker.username");
            csv.addColumn(context.message("member.brokerName"), "broker.name");
        }
        return csv;
    }

    private DataBinder<PendingMemberQuery> getDataBinder() {
        if (dataBinder == null) {
            final LocalSettings settings = settingsService.getLocalSettings();
            dataBinder = SearchPendingMembersAction.createDataBinder(settings);
        }
        return dataBinder;
    }
}
