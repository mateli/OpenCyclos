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
package nl.strohalm.cyclos.controls.accounts.pos;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseQueryAction;
import nl.strohalm.cyclos.entities.accounts.pos.Pos;
import nl.strohalm.cyclos.entities.accounts.pos.PosQuery;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.services.accounts.pos.PosService;
import nl.strohalm.cyclos.utils.RequestHelper;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.DataBinderHelper;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.binding.SimpleCollectionBinder;
import nl.strohalm.cyclos.utils.query.QueryParameters;

/**
 * 
 * @author rodrigo
 */
public class SearchPosAction extends BaseQueryAction {

    private DataBinder<PosQuery> dataBinder;

    private PosService           posService;

    // Used to get data and save to database
    public DataBinder<PosQuery> getDataBinder() {
        if (dataBinder == null) {

            final BeanBinder<PosQuery> binder = BeanBinder.instance(PosQuery.class);
            binder.registerBinder("posId", PropertyBinder.instance(String.class, "posId"));
            binder.registerBinder("statuses", SimpleCollectionBinder.instance(PosQuery.QueryStatus.class, "statuses"));
            binder.registerBinder("member", PropertyBinder.instance(Member.class, "member"));
            binder.registerBinder("pageParameters", DataBinderHelper.pageBinder());

            dataBinder = binder;
        }
        return dataBinder;
    }

    @Inject
    public void setPosService(final PosService posService) {
        this.posService = posService;
    }

    @Override
    protected void executeQuery(final ActionContext context, final QueryParameters queryParameters) {
        final PosQuery query = (PosQuery) queryParameters;
        final List<Pos> pos = posService.search(query);
        context.getRequest().setAttribute("pos", pos);

    }

    @Override
    protected QueryParameters prepareForm(final ActionContext context) {

        final SearchPosForm form = context.getForm();
        final HttpServletRequest request = context.getRequest();
        final PosQuery query = getDataBinder().readFromString(form.getQuery());

        // Members
        if (query.getMember() != null) {
            final Member member = elementService.load(query.getMember().getId(), Element.Relationships.USER);
            query.setMember(member);
        }
        if (context.isBroker()) {
            query.setBroker((Member) context.getElement());
        }
        RequestHelper.storeEnum(request, PosQuery.QueryStatus.class, "statuses");

        return query;
    }

    @Override
    protected boolean willExecuteQuery(final ActionContext context, final QueryParameters queryParameters) throws Exception {
        return true;
    }
}
