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
package nl.strohalm.cyclos.controls.accounts.guarantees.certifications;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.access.AdminMemberPermission;
import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseQueryAction;
import nl.strohalm.cyclos.entities.accounts.guarantees.Certification;
import nl.strohalm.cyclos.entities.accounts.guarantees.CertificationQuery;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.accounts.guarantees.CertificationDTO;
import nl.strohalm.cyclos.services.accounts.guarantees.CertificationService;
import nl.strohalm.cyclos.services.accounts.guarantees.GuaranteeService;
import nl.strohalm.cyclos.utils.EntityHelper;
import nl.strohalm.cyclos.utils.RequestHelper;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.DataBinderHelper;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.binding.SimpleCollectionBinder;
import nl.strohalm.cyclos.utils.query.QueryParameters;

public class SearchCertificationsAction extends BaseQueryAction {

    private DataBinder<CertificationQuery> dataBinder;
    private CertificationService           certificationService;
    private GuaranteeService               guaranteeService;

    @Inject
    public void setCertificationService(final CertificationService certificationService) {
        this.certificationService = certificationService;
    }

    @Inject
    public void setGuaranteeService(final GuaranteeService guaranteeService) {
        this.guaranteeService = guaranteeService;
    }

    @Override
    protected void executeQuery(final ActionContext context, final QueryParameters queryParameters) {
        final HttpServletRequest request = context.getRequest();
        final CertificationQuery query = (CertificationQuery) queryParameters;
        query.fetch(Certification.Relationships.GUARANTEE_TYPE);
        final List<CertificationDTO> listCertificationDTOs = certificationService.searchWithUsedAmount(query);
        request.setAttribute("listCertificationDTOs", listCertificationDTOs);

        context.getSession().setAttribute("executeCertificationsQuery", true);
    }

    @Override
    protected QueryParameters prepareForm(final ActionContext context) {
        final HttpServletRequest request = context.getRequest();
        final SearchCertificationsForm form = context.getForm();
        final CertificationQuery query = getDataBinder().readFromString(form.getQuery());
        final boolean hasViewPermission = permissionService.hasPermission(AdminMemberPermission.GUARANTEES_VIEW_CERTIFICATIONS);
        final boolean isIssuer = (Boolean) context.getSession().getAttribute("isIssuer");
        final boolean isBuyer = (Boolean) context.getSession().getAttribute("isBuyer");
        final boolean showBuyer = isIssuer || hasViewPermission;
        final boolean showIssuer = context.isAdmin() || isBuyer;

        // TODO: Who has permissions to modify a certification?
        request.setAttribute("isEditable", false);
        request.setAttribute("hasViewPermission", hasViewPermission);
        request.setAttribute("showBuyer", showBuyer);
        request.setAttribute("showIssuer", showIssuer);
        request.setAttribute("buyerGroupIds", showBuyer ? EntityHelper.toIdsAsString(guaranteeService.getBuyers()) : "[]");
        request.setAttribute("issuerGroupIds", showIssuer ? EntityHelper.toIdsAsString(guaranteeService.getIssuers()) : "[]");

        RequestHelper.storeEnum(request, Certification.Status.class, "status");

        // Sets the logged user (a buyer) as the buyer
        if (!hasViewPermission && !isIssuer) {
            if (!context.isMember()) { // a not allowed user (e.g. an admin) trying to search by certifications
                throw new PermissionDeniedException();
            } else {
                query.setBuyer(EntityHelper.reference(Member.class, ((Member) context.getAccountOwner()).getId()));
            }
        } else if (query.getBuyer() != null) {
            query.setBuyer((Member) elementService.load(query.getBuyer().getId()));
        }

        // Sets the logged user as the issuer
        if (isIssuer) {
            query.setIssuer(EntityHelper.reference(Member.class, ((Member) context.getAccountOwner()).getId()));
        } else {
            query.setIssuer(query.getIssuer() == null ? null : (Member) elementService.load(query.getIssuer().getId()));
        }

        // Reset the flag
        if (RequestHelper.isFromMenu(request)) {
            context.getSession().setAttribute("executeCertificationsQuery", false);
        }

        return query;
    }

    @Override
    protected boolean willExecuteQuery(final ActionContext context, final QueryParameters queryParameters) throws Exception {
        final boolean b = super.willExecuteQuery(context, queryParameters);
        final Object executeQuery = context.getSession().getAttribute("executeCertificationsQuery");
        return b || executeQuery != null && (Boolean) executeQuery;
    }

    private DataBinder<CertificationQuery> getDataBinder() {
        if (dataBinder == null) {
            final LocalSettings localSettings = settingsService.getLocalSettings();

            final BeanBinder<CertificationQuery> binder = BeanBinder.instance(CertificationQuery.class);
            binder.registerBinder("startIn", DataBinderHelper.periodBinder(localSettings, "starts"));
            binder.registerBinder("endIn", DataBinderHelper.periodBinder(localSettings, "expires"));
            binder.registerBinder("buyer", PropertyBinder.instance(Member.class, "buyer"));
            binder.registerBinder("issuer", PropertyBinder.instance(Member.class, "issuer"));
            binder.registerBinder("statusList", SimpleCollectionBinder.instance(Certification.Status.class, "statusList"));
            binder.registerBinder("pageParameters", DataBinderHelper.pageBinder());

            dataBinder = binder;
        }

        return dataBinder;
    }

}
