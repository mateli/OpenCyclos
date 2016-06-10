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

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.access.AdminMemberPermission;
import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.accounts.guarantees.Certification;
import nl.strohalm.cyclos.entities.accounts.guarantees.Certification.Status;
import nl.strohalm.cyclos.entities.accounts.guarantees.CertificationLog;
import nl.strohalm.cyclos.entities.accounts.guarantees.Guarantee;
import nl.strohalm.cyclos.entities.accounts.guarantees.GuaranteeType;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.services.accounts.guarantees.CertificationService;
import nl.strohalm.cyclos.services.accounts.guarantees.GuaranteeService;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.ActionHelper.ByElementExtractor;
import nl.strohalm.cyclos.utils.Amount;
import nl.strohalm.cyclos.utils.EntityHelper;
import nl.strohalm.cyclos.utils.RequestHelper;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.DataBinderHelper;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.conversion.IdConverter;
import nl.strohalm.cyclos.utils.query.PageParameters;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.struts.action.ActionForward;

public class EditCertificationAction extends BaseFormAction {
    private DataBinder<Certification> dataBinder;
    private CertificationService      certificationService;
    private GuaranteeService          guaranteeService;

    @Inject
    public void setCertificationService(final CertificationService certificationService) {
        this.certificationService = certificationService;
    }

    @Inject
    public void setGuaranteeService(final GuaranteeService guaranteeService) {
        this.guaranteeService = guaranteeService;
    }

    @Override
    protected ActionForward handleSubmit(final ActionContext context) throws Exception {
        final EditCertificationForm form = context.getForm();
        final Certification certification = getDataBinder().readFromString(form.getCertification());
        final boolean isInsert = certification.isTransient();
        initialize(certification, context);
        certificationService.save(certification);
        context.sendMessage(isInsert ? "certification.inserted" : "certification.modified");

        return ActionHelper.redirectWithParam(context.getRequest(), context.getSuccessForward(), "certificationId", certification.getId());
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void prepareForm(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();
        final EditCertificationForm form = context.getForm();
        final long id = form.getCertificationId();
        final boolean isInsert = id <= 0L;
        // TODO: Who has permissions to modify a certification?
        final boolean isEditable = isInsert;
        final boolean isIssuer = (Boolean) context.getSession().getAttribute("isIssuer");

        if (!isInsert) {
            final Certification certification = certificationService.load(id, Certification.Relationships.GUARANTEE_TYPE, Certification.Relationships.BUYER);
            getDataBinder().writeAsString(form.getCertification(), certification);

            final LocalSettings localSettings = settingsService.getLocalSettings();
            final PageParameters guaranteesPageParameters = new PageParameters(localSettings.getMaxPageResults(), form.getGuaranteesPage());
            final List<Guarantee> guarantees = guaranteeService.getGuarantees(certification, guaranteesPageParameters, Collections.EMPTY_LIST);

            final ByElementExtractor extractor = new ByElementExtractor() {
                @Override
                public Element getByElement(final Entity entity) {
                    return ((CertificationLog) entity).getBy();
                }
            };

            final boolean showGuarantees = !context.isAdmin() || permissionService.hasPermission(AdminMemberPermission.GUARANTEES_VIEW_GUARANTEES);
            request.setAttribute("certification", certification);
            request.setAttribute("showGuarantees", showGuarantees);
            request.setAttribute("logsBy", ActionHelper.getByElements(context, certification.getLogs(), extractor));
            request.setAttribute("canLock", certificationService.canChangeStatus(certification, Status.SUSPENDED));
            request.setAttribute("canUnlock", certificationService.canChangeStatus(certification, Status.ACTIVE));
            request.setAttribute("canCancel", certificationService.canChangeStatus(certification, Status.CANCELLED));
            request.setAttribute("canDelete", guarantees.isEmpty() && certificationService.canDelete(certification));
            request.setAttribute("guarantees", guarantees);
            request.setAttribute("usedAmount", certificationService.getUsedAmount(certification, false));

        }

        if (isIssuer) { // only if the logged user is a issuer we must search for guarantee types
            Group group = context.getGroup();
            group = groupService.load(group.getId(), Group.Relationships.GUARANTEE_TYPES);
            final Collection<GuaranteeType> guaranteeTypes = group.getEnabledGuaranteeTypes();
            filterGuaranteeTypes(guaranteeTypes);
            request.setAttribute("guaranteeTypes", guaranteeTypes);
        }

        if (isEditable) {
            request.setAttribute("buyerGroupIds", EntityHelper.toIdsAsString(guaranteeService.getBuyers()));
        } else {
            request.setAttribute("buyerGroupIds", "[]");
        }

        request.setAttribute("isInsert", isInsert);
        request.setAttribute("isEditable", isEditable);

        RequestHelper.storeEnum(request, Amount.Type.class, "amountTypes");
        RequestHelper.storeEnum(request, Certification.Status.class, "status");
        request.setAttribute("fixedFeeType", GuaranteeType.FeeType.FIXED);
    }

    @Override
    protected void validateForm(final ActionContext context) {
        final EditCertificationForm form = context.getForm();
        final Certification certification = getDataBinder().readFromString(form.getCertification());
        initialize(certification, context);
        certificationService.validate(certification);
    }

    /**
     * Filter by guarantee type's model equals to WITH_PAYMENT_OBLIGATION
     * @param guaranteeTypes
     */
    private void filterGuaranteeTypes(final Collection<GuaranteeType> guaranteeTypes) {
        CollectionUtils.filter(guaranteeTypes, new Predicate() {

            @Override
            public boolean evaluate(final Object object) {
                return GuaranteeType.Model.WITH_PAYMENT_OBLIGATION == ((GuaranteeType) object).getModel();
            }
        });
    }

    private DataBinder<Certification> getDataBinder() {
        if (dataBinder == null) {
            final LocalSettings localSettings = settingsService.getLocalSettings();

            final BeanBinder<Certification> binder = BeanBinder.instance(Certification.class);
            binder.registerBinder("id", PropertyBinder.instance(Long.class, "id", IdConverter.instance()));
            binder.registerBinder("guaranteeType", PropertyBinder.instance(GuaranteeType.class, "guaranteeType"));
            binder.registerBinder("amount", PropertyBinder.instance(BigDecimal.class, "amount", localSettings.getNumberConverter()));
            binder.registerBinder("buyer", PropertyBinder.instance(Member.class, "buyer"));
            binder.registerBinder("validity", DataBinderHelper.rawPeriodBinder(localSettings, "validity"));
            binder.registerBinder("status", PropertyBinder.instance(Certification.Status.class, "status"));

            dataBinder = binder;
        }

        return dataBinder;

    }

    private void initialize(final Certification certification, final ActionContext context) {
        // sets the logged user as the issuer
        certification.setIssuer(EntityHelper.reference(Member.class, ((Member) context.getAccountOwner()).getId()));
    }
}
