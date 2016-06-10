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
/**
 *
 */
package nl.strohalm.cyclos.controls.members.brokering;

import java.util.Iterator;
import java.util.List;

import nl.strohalm.cyclos.access.AdminMemberPermission;
import nl.strohalm.cyclos.access.BrokerPermission;
import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseCsvAction;
import nl.strohalm.cyclos.entities.access.User;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomField;
import nl.strohalm.cyclos.entities.members.BrokeringQuery;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.brokerings.Brokering;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.services.customization.MemberCustomFieldService;
import nl.strohalm.cyclos.services.elements.BrokeringService;
import nl.strohalm.cyclos.services.transactions.LoanService;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.conversion.CustomFieldConverter;
import nl.strohalm.cyclos.utils.csv.CSVWriter;
import nl.strohalm.cyclos.utils.query.QueryParameters.ResultType;

/**
 * Exports the Brokered members list of a broker to CSV. Uses the ListBrokeringsForm for this.
 * 
 * @author rinke
 */
public class ExportBrokeringsToCsvAction extends BaseCsvAction {

    private BrokeringService           brokeringService;
    private MemberCustomFieldService   memberCustomFieldService;
    private LoanService                loanService;
    private DataBinder<BrokeringQuery> dataBinder;

    public MemberCustomFieldService getMemberCustomFieldService() {
        return memberCustomFieldService;
    }

    @Inject
    public void setBrokeringService(final BrokeringService brokeringService) {
        this.brokeringService = brokeringService;
    }

    @Inject
    public void setLoanService(final LoanService loanService) {
        this.loanService = loanService;
    }

    @Inject
    public void setMemberCustomFieldService(final MemberCustomFieldService memberCustomFieldService) {
        this.memberCustomFieldService = memberCustomFieldService;
    }

    @SuppressWarnings("rawtypes")
    @Override
    protected Iterable<?> executeQuery(final ActionContext context) {
        final ListBrokeringsForm form = context.getForm();
        final BrokeringQuery query = getDataBinder().readFromString(form.getQuery());
        // Retrieve the broker
        if (query.getBroker() == null) {
            Member broker = null;
            final Element loggedElement = context.getElement();
            if (form.getMemberId() <= 0 || loggedElement.getId().equals(form.getMemberId())) {
                if (context.isMember()) {
                    broker = context.getElement();
                }
            } else {
                if (context.isAdmin()) {
                    final Element element = elementService.load(form.getMemberId(), Element.Relationships.USER, Element.Relationships.GROUP);
                    if (element instanceof Member) {
                        broker = (Member) element;
                    }
                }
            }
            query.setBroker(broker);
        }
        query.setResultType(ResultType.ITERATOR);
        // Check if we will display loan data
        final boolean showLoanData;
        if (query.getStatus() == BrokeringQuery.Status.PENDING) {
            showLoanData = false;
        } else {
            // It is a nonsense to show loans of pending members
            if (context.isAdmin()) {
                showLoanData = permissionService.hasPermission(AdminMemberPermission.BROKERINGS_VIEW_LOANS);
            } else {
                showLoanData = permissionService.hasPermission(BrokerPermission.LOANS_VIEW);
            }
        }
        query.fetch(RelationshipHelper.nested(Brokering.Relationships.BROKERED, Element.Relationships.GROUP),
                RelationshipHelper.nested(Brokering.Relationships.BROKERED, Member.Relationships.CUSTOM_VALUES));
        final List<Brokering> brokerings = brokeringService.search(query);
        final Iterable result = new Iterable() {
            @Override
            public Iterator iterator() {
                return new BrokeredIterator(brokerings.iterator(), loanService, showLoanData);
            }
        };
        return result;
    }

    @Override
    protected String fileName(final ActionContext context) {
        final User loggedUser = context.getUser();
        return "brokerings_" + loggedUser.getUsername() + ".csv";
    }

    @Override
    protected CSVWriter<?> resolveCSVWriter(final ActionContext context) {
        final LocalSettings settings = settingsService.getLocalSettings();
        final CSVWriter<Brokering> csv = CSVWriter.instance(Brokering.class, settings);
        final String memberPrefix = "brokering.brokered.";
        csv.addColumn(context.message("member.id"), memberPrefix + "id");
        csv.addColumn(context.message("member.username"), memberPrefix + "username");
        csv.addColumn(context.message("member.name"), memberPrefix + "name");
        csv.addColumn(context.message("member.email"), memberPrefix + "email");
        csv.addColumn(context.message("member.lastLogin"), memberPrefix + "user.lastLogin", settings.getDateTimeConverter());
        final List<MemberCustomField> customFields = memberCustomFieldService.list();
        for (final MemberCustomField field : customFields) {
            csv.addColumn(field.getName(), memberPrefix + "customValues", new CustomFieldConverter(field, elementService, settings));
        }
        csv.addColumn(context.message("brokering.loans.count"), "loans.count");
        csv.addColumn(context.message("brokering.loans.amount"), "loans.amount");
        return csv;
    }

    private DataBinder<BrokeringQuery> getDataBinder() {
        if (dataBinder == null) {
            dataBinder = ListBrokeringsAction.brokeringDataBinder();
        }
        return dataBinder;
    }

}
