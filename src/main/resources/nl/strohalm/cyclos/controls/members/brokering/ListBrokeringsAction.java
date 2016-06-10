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
package nl.strohalm.cyclos.controls.members.brokering;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.access.AdminMemberPermission;
import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseQueryAction;
import nl.strohalm.cyclos.entities.groups.BrokerGroup;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.BrokeringQuery;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.brokerings.Brokering;
import nl.strohalm.cyclos.services.elements.BrokeringService;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.DataBinderHelper;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.binding.SimpleCollectionBinder;
import nl.strohalm.cyclos.utils.query.QueryParameters;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.commons.collections.CollectionUtils;

/**
 * List a broker's registered members
 * @author luis
 */
public class ListBrokeringsAction extends BaseQueryAction {

    public static DataBinder<BrokeringQuery> brokeringDataBinder() {
        final BeanBinder<BrokeringQuery> binder = BeanBinder.instance(BrokeringQuery.class);
        binder.registerBinder("status", PropertyBinder.instance(BrokeringQuery.Status.class, "status"));
        binder.registerBinder("username", PropertyBinder.instance(String.class, "username"));
        binder.registerBinder("groups", SimpleCollectionBinder.instance(Group.class, "groups"));
        binder.registerBinder("name", PropertyBinder.instance(String.class, "name"));
        binder.registerBinder("pageParameters", DataBinderHelper.pageBinder());
        return binder;
    }

    private BrokeringService           brokeringService;

    private DataBinder<BrokeringQuery> dataBinder;

    public BrokeringService getBrokeringService() {
        return brokeringService;
    }

    public DataBinder<BrokeringQuery> getDataBinder() {
        if (dataBinder == null) {
            dataBinder = brokeringDataBinder();
        }
        return dataBinder;
    }

    @Inject
    public void setBrokeringService(final BrokeringService brokeringService) {
        this.brokeringService = brokeringService;
    }

    @Override
    protected void executeQuery(final ActionContext context, final QueryParameters queryParameters) {
        final BrokeringQuery query = (BrokeringQuery) queryParameters;
        final List<Brokering> brokerings = brokeringService.search(query);
        context.getRequest().setAttribute("brokerings", brokerings);
    }

    @Override
    protected QueryParameters prepareForm(final ActionContext context) {
        final HttpServletRequest request = context.getRequest();
        final ListBrokeringsForm form = context.getForm();

        boolean myBrokerings = false;
        boolean canChangeBroker = false;

        // Retrieve the broker
        Member broker = null;
        final Element loggedElement = context.getElement();
        if (form.getMemberId() <= 0 || loggedElement.getId().equals(form.getMemberId())) {
            if (context.isMember()) {
                broker = context.getElement();
                myBrokerings = true;
            }
        } else {
            if (context.isAdmin()) {
                final Element element = elementService.load(form.getMemberId(), Element.Relationships.USER, Element.Relationships.GROUP);
                if (element instanceof Member) {
                    broker = (Member) element;
                }
                canChangeBroker = permissionService.hasPermission(AdminMemberPermission.BROKERINGS_CHANGE_BROKER);
            }
        }
        if (broker == null || !broker.getMemberGroup().isBroker()) {
            throw new ValidationException();
        }

        // Get member groups for brokering search
        BrokerGroup brokerGroup = (BrokerGroup) broker.getGroup();
        brokerGroup = groupService.load(brokerGroup.getId(), BrokerGroup.Relationships.POSSIBLE_INITIAL_GROUPS);

        final List<MemberGroup> groups = new ArrayList<MemberGroup>();
        final Collection<MemberGroup> possibleInitialGroups = brokerGroup.getPossibleInitialGroups();
        if (CollectionUtils.isNotEmpty(possibleInitialGroups)) {
            for (final MemberGroup memberGroup : possibleInitialGroups) {
                if (!groups.contains(memberGroup)) {
                    groups.add(memberGroup);
                }
            }
        }
        request.setAttribute("groups", groups);

        // Resolve the query parameters
        final BrokeringQuery query = getDataBinder().readFromString(form.getQuery());
        if (query.getStatus() == null) {
            query.setStatus(BrokeringQuery.Status.ACTIVE);
        }
        query.setBroker(broker);
        query.fetch(RelationshipHelper.nested(Brokering.Relationships.BROKERED, Element.Relationships.USER));

        // Store the request attributes
        request.setAttribute("broker", broker);
        request.setAttribute("myBrokerings", myBrokerings);
        request.setAttribute("canChangeBroker", canChangeBroker);
        request.setAttribute("status", brokeringService.listPossibleStatuses(brokerGroup));
        return query;
    }

    @Override
    protected boolean willExecuteQuery(final ActionContext context, final QueryParameters queryParameters) throws Exception {
        return true;
    }
}
