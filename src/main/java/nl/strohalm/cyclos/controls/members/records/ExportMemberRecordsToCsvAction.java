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
package nl.strohalm.cyclos.controls.members.records;

import java.util.List;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseCsvAction;
import nl.strohalm.cyclos.entities.access.User;
import nl.strohalm.cyclos.entities.customization.fields.MemberRecordCustomField;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.records.FullTextMemberRecordQuery;
import nl.strohalm.cyclos.entities.members.records.MemberRecord;
import nl.strohalm.cyclos.entities.members.records.MemberRecordType;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.services.customization.MemberRecordCustomFieldService;
import nl.strohalm.cyclos.services.elements.MemberRecordService;
import nl.strohalm.cyclos.services.elements.MemberRecordTypeService;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.conversion.CustomFieldConverter;
import nl.strohalm.cyclos.utils.conversion.FormatOnlyConverter;
import nl.strohalm.cyclos.utils.csv.CSVWriter;
import nl.strohalm.cyclos.utils.query.QueryParameters.ResultType;
import nl.strohalm.cyclos.utils.validation.ValidationException;

/**
 * Action used to export a member search result as csv
 * @author luis
 */
public class ExportMemberRecordsToCsvAction extends BaseCsvAction {

    /**
     * As {@link MemberRecord}s can actually be used for admins, we can't use the 'broker' property directly, as for admins that would throw an error
     * @author luis
     */
    private static class BrokerConverter extends FormatOnlyConverter<Element> {
        private static final long serialVersionUID = 1L;
        private final boolean     username;

        public BrokerConverter(final boolean username) {
            this.username = username;
        }

        @Override
        public String toString(final Element element) {
            if (element instanceof Member) {
                final Member broker = ((Member) element).getBroker();
                if (broker != null) {
                    return username ? broker.getUsername() : broker.getName();
                }
            }
            return null;
        }

    }

    private MemberRecordTypeService               memberRecordTypeService;
    private MemberRecordService                   memberRecordService;
    private MemberRecordCustomFieldService        memberRecordCustomFieldService;
    private DataBinder<FullTextMemberRecordQuery> dataBinder;

    @Inject
    public void setMemberRecordCustomFieldService(final MemberRecordCustomFieldService memberRecordCustomFieldService) {
        this.memberRecordCustomFieldService = memberRecordCustomFieldService;
    }

    @Inject
    public void setMemberRecordService(final MemberRecordService memberRecordService) {
        this.memberRecordService = memberRecordService;
    }

    @Inject
    public void setMemberRecordTypeService(final MemberRecordTypeService memberRecordTypeService) {
        this.memberRecordTypeService = memberRecordTypeService;
    }

    @Override
    protected List<?> executeQuery(final ActionContext context) {
        final SearchMemberRecordsForm form = context.getForm();
        final FullTextMemberRecordQuery query = getDataBinder().readFromString(form.getQuery());
        query.fetch(RelationshipHelper.nested(MemberRecord.Relationships.BY, Element.Relationships.USER));
        query.fetch(MemberRecord.Relationships.CUSTOM_VALUES);
        query.fetch(RelationshipHelper.nested(MemberRecord.Relationships.ELEMENT, Element.Relationships.USER));
        query.fetch(RelationshipHelper.nested(MemberRecord.Relationships.ELEMENT, Member.Relationships.BROKER, Element.Relationships.USER));
        query.fetch(RelationshipHelper.nested(MemberRecord.Relationships.MODIFIED_BY, Element.Relationships.USER));
        query.setType(resolveType(context));
        query.setResultType(ResultType.ITERATOR);
        query.fetch(MemberRecord.Relationships.ELEMENT, MemberRecord.Relationships.CUSTOM_VALUES);
        final boolean global = form.isGlobal();
        // Fetch the element
        Element element = null;
        if (!global) {
            final long elementId = form.getElementId();
            if (elementId <= 0L) {
                throw new ValidationException();
            }
            element = elementService.load(elementId, Element.Relationships.GROUP);
        }

        if (global) {
            if (form.getQueryElementId() > 0L) {
                final Element queryElement = elementService.load(form.getQueryElementId());
                query.setElement(queryElement);
            }
        } else {
            query.setElement(element);
        }
        return memberRecordService.fullTextSearch(query);
    }

    @Override
    protected String fileName(final ActionContext context) {
        final User loggedUser = context.getUser();
        final MemberRecordType type = resolveType(context);
        return type.getLabel() + "_" + loggedUser.getUsername() + ".csv";
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    protected CSVWriter resolveCSVWriter(final ActionContext context) {
        final MemberRecordType type = resolveType(context);

        final LocalSettings settings = settingsService.getLocalSettings();
        final CSVWriter<MemberRecord> csv = CSVWriter.instance(MemberRecord.class, settings);
        csv.addColumn(context.message("memberRecord.date"), "date", settings.getDateTimeConverter());
        csv.addColumn(context.message("memberRecord.by"), "by.name");
        if (type.isEditable()) {
            csv.addColumn(context.message("memberRecord.lastModified"), "lastModified", settings.getDateTimeConverter());
            csv.addColumn(context.message("memberRecord.modifiedBy"), "modifiedBy.name");
        }
        csv.addColumn(context.message("member.username"), "element.username");
        csv.addColumn(context.message("member.name"), "element.name");
        csv.addColumn(context.message("member.brokerUsername"), "element", new BrokerConverter(true));
        csv.addColumn(context.message("member.brokerName"), "element", new BrokerConverter(false));
        final List<MemberRecordCustomField> customFields = memberRecordCustomFieldService.list(type);
        for (final MemberRecordCustomField field : customFields) {
            csv.addColumn(field.getName(), "customValues", new CustomFieldConverter(field, elementService, settings));
        }
        return csv;
    }

    private DataBinder<FullTextMemberRecordQuery> getDataBinder() {
        if (dataBinder == null) {
            dataBinder = SearchMemberRecordsAction.memberRecordQueryDataBinder(settingsService.getLocalSettings());
        }
        return dataBinder;
    }

    private MemberRecordType resolveType(final ActionContext context) {
        final SearchMemberRecordsForm form = context.getForm();
        final long typeId = form.getTypeId();
        if (typeId <= 0L) {
            throw new ValidationException();
        }
        return memberRecordTypeService.load(typeId);
    }
}
