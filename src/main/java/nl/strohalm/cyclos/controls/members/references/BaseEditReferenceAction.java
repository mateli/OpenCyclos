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
package nl.strohalm.cyclos.controls.members.references;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.Reference;
import nl.strohalm.cyclos.services.elements.ReferenceService;
import nl.strohalm.cyclos.services.transactions.PaymentService;
import nl.strohalm.cyclos.services.transactions.ScheduledPaymentService;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.conversion.IdConverter;
import nl.strohalm.cyclos.utils.conversion.ReferenceConverter;

/**
 * Base action used to view / edit a reference details
 * @author luis
 */
public abstract class BaseEditReferenceAction<T extends Reference> extends BaseFormAction {

    protected ReferenceService        referenceService;
    protected PaymentService          paymentService;
    protected ScheduledPaymentService scheduledPaymentService;

    @Inject
    public void setPaymentService(final PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Inject
    public void setReferenceService(final ReferenceService referenceService) {
        this.referenceService = referenceService;
    }

    @Inject
    public void setScheduledPaymentService(final ScheduledPaymentService scheduledPaymentService) {
        this.scheduledPaymentService = scheduledPaymentService;
    }

    protected void initBinder(final BeanBinder<T> binder) {
        binder.registerBinder("id", PropertyBinder.instance(Long.class, "id", IdConverter.instance()));
        binder.registerBinder("from", PropertyBinder.instance(Member.class, "from", ReferenceConverter.instance(Member.class)));
        binder.registerBinder("to", PropertyBinder.instance(Member.class, "to", ReferenceConverter.instance(Member.class)));
        binder.registerBinder("level", PropertyBinder.instance(Reference.Level.class, "level"));
        binder.registerBinder("comments", PropertyBinder.instance(String.class, "comments"));
    }

    protected abstract T resolveReference(final ActionContext context);

    @Override
    protected void validateForm(final ActionContext context) {
        final Reference reference = resolveReference(context);
        referenceService.validate(reference);
    }
}
