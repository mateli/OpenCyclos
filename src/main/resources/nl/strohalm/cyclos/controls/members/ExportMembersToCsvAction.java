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
package nl.strohalm.cyclos.controls.members;

import java.util.Collection;
import java.util.List;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.elements.ExportElementsToCsvAction;
import nl.strohalm.cyclos.entities.access.User;
import nl.strohalm.cyclos.entities.accounts.loans.LoanGroup;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomField;
import nl.strohalm.cyclos.entities.members.FullTextElementQuery;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.services.customization.MemberCustomFieldService;
import nl.strohalm.cyclos.utils.conversion.Converter;
import nl.strohalm.cyclos.utils.conversion.CustomFieldConverter;
import nl.strohalm.cyclos.utils.csv.CSVWriter;

/**
 * Action used to export a member search result as csv
 * @author luis
 */
public class ExportMembersToCsvAction extends ExportElementsToCsvAction {

    static class LoanGroupsConverter implements Converter<Collection<LoanGroup>> {

        private static final long serialVersionUID = 5416771410900971276L;

        @Override
        public String toString(final Collection<LoanGroup> loanGroups) {
            final StringBuilder builder = new StringBuilder();
            int loanGroupsCount = 0;
            for (final LoanGroup group : loanGroups) {
                if (loanGroupsCount != 0) {
                    builder.append(", ");
                }
                builder.append(group.getName());
                loanGroupsCount++;
            }
            return builder.toString();
        }

        @Override
        public Collection<LoanGroup> valueOf(final String string) {
            return null;
        }
    }

    private MemberCustomFieldService memberCustomFieldService;

    public MemberCustomFieldService getMemberCustomFieldService() {
        return memberCustomFieldService;
    }

    @Inject
    public void setMemberCustomFieldService(final MemberCustomFieldService memberCustomFieldService) {
        this.memberCustomFieldService = memberCustomFieldService;
    }

    @Override
    protected String fileName(final ActionContext context) {
        final User loggedUser = context.getUser();
        return "members_" + loggedUser.getUsername() + ".csv";
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    protected CSVWriter resolveCSVWriter(final ActionContext context) {
        final LocalSettings settings = settingsService.getLocalSettings();
        final CSVWriter<Member> csv = CSVWriter.instance(Member.class, settings);
        csv.addColumn(context.message("member.id"), "id");
        csv.addColumn(context.message("member.username"), "user.username");
        csv.addColumn(context.message("member.name"), "name");
        csv.addColumn(context.message("member.email"), "email");
        csv.addColumn(context.message("member.creationDate"), "creationDate", settings.getDateConverter());
        csv.addColumn(context.message("member.activationDate"), "activationDate", settings.getDateConverter());
        csv.addColumn(context.message("member.lastLogin"), "user.lastLogin", settings.getDateTimeConverter());
        csv.addColumn(context.message("member.loanGroups"), "loanGroups", new LoanGroupsConverter());
        csv.addColumn(context.message("member.group"), "group.name");
        final List<MemberCustomField> customFields = memberCustomFieldService.list();
        for (final MemberCustomField field : customFields) {
            csv.addColumn(field.getName(), "customValues", new CustomFieldConverter(field, elementService, settings));
        }
        csv.addColumn(context.message("member.brokerUsername"), "broker.username");
        csv.addColumn(context.message("member.brokerName"), "broker.name");
        return csv;
    }

    @Override
    protected FullTextElementQuery resolveQuery(final ActionContext context) {
        final FullTextElementQuery query = super.resolveQuery(context);
        query.fetch(Member.Relationships.CUSTOM_VALUES, Member.Relationships.LOAN_GROUPS, Member.Relationships.BROKER);
        return query;
    }
}
