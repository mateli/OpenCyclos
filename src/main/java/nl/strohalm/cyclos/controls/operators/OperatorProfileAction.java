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
package nl.strohalm.cyclos.controls.operators;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.elements.ProfileAction;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.access.OperatorUser;
import nl.strohalm.cyclos.entities.access.User;
import nl.strohalm.cyclos.entities.customization.fields.CustomFieldValue;
import nl.strohalm.cyclos.entities.customization.fields.OperatorCustomField;
import nl.strohalm.cyclos.entities.customization.fields.OperatorCustomField.Visibility;
import nl.strohalm.cyclos.entities.customization.fields.OperatorCustomFieldValue;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.OperatorGroup;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Operator;
import nl.strohalm.cyclos.services.access.exceptions.NotConnectedException;
import nl.strohalm.cyclos.services.customization.OperatorCustomFieldService;
import nl.strohalm.cyclos.services.elements.WhenSaving;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.CustomFieldHelper;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.BeanCollectionBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.conversion.HtmlConverter;
import nl.strohalm.cyclos.utils.conversion.ReferenceConverter;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.struts.action.ActionForward;

/**
 * Profile action for operators
 * @author luis
 */
public class OperatorProfileAction extends ProfileAction<Operator> {

    private static final Relationship[] FETCH = { RelationshipHelper.nested(User.Relationships.ELEMENT, Element.Relationships.GROUP), RelationshipHelper.nested(User.Relationships.ELEMENT, Element.Relationships.USER), RelationshipHelper.nested(User.Relationships.ELEMENT, Operator.Relationships.CUSTOM_VALUES), RelationshipHelper.nested(User.Relationships.ELEMENT, Operator.Relationships.MEMBER) };

    private OperatorCustomFieldService  operatorCustomFieldService;

    private CustomFieldHelper           customFieldHelper;

    @Inject
    public void setCustomFieldHelper(final CustomFieldHelper customFieldHelper) {
        this.customFieldHelper = customFieldHelper;
    }

    @Inject
    public void setOperatorCustomFieldService(final OperatorCustomFieldService operatorCustomFieldService) {
        this.operatorCustomFieldService = operatorCustomFieldService;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected <CFV extends CustomFieldValue> Class<CFV> getCustomFieldValueClass() {
        return (Class<CFV>) OperatorCustomFieldValue.class;
    }

    @Override
    protected Class<Operator> getElementClass() {
        return Operator.class;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected <G extends Group> Class<G> getGroupClass() {
        return (Class<G>) OperatorGroup.class;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected <U extends User> Class<U> getUserClass() {
        return (Class<U>) OperatorUser.class;
    }

    @Override
    protected ActionForward handleDisplay(final ActionContext context) throws Exception {
        final OperatorProfileForm form = context.getForm();
        final HttpServletRequest request = context.getRequest();

        boolean myProfile = false;

        // Load the user
        final Long operatorId = form.getOperatorId();
        OperatorUser operatorUser = null;
        Operator operator = null;
        final Element loggedElement = context.getElement();

        if (context.isMember()) {
            // Member viewing an operator's profile
            if (operatorId <= 0) {
                throw new ValidationException();
            }
            final User loaded = elementService.loadUser(operatorId, FETCH);
            if (!(loaded instanceof OperatorUser)) {
                throw new ValidationException();
            }

            operatorUser = (OperatorUser) loaded;
            operator = operatorUser.getOperator();
            if (!operator.getMember().equals(loggedElement)) {
                throw new ValidationException();
            }
            try {
                request.setAttribute("isLoggedIn", accessService.isLoggedIn(operatorUser));
            } catch (final NotConnectedException e) {
                // OK - the user is not online
            }

            request.setAttribute("disabledLogin", accessService.isLoginBlocked(operatorUser));

        } else if (operatorId <= 0L || operatorId.equals(context.getElement().getId())) { // context.isOperator()
            // The logged user (operator) is viewing it's own profile
            operatorUser = elementService.loadUser(context.getUser().getId(), FETCH);
            operator = operatorUser.getOperator();
            myProfile = true;
        } else {
            throw new ValidationException();
        }

        // Write the operator to the form
        getReadDataBinder(context).writeAsString(form.getOperator(), operator);

        // Retrieve the custom fields
        final List<OperatorCustomField> customFields = operatorCustomFieldService.list(operator.getMember());

        // This map will store, for each field, if it is editable or not
        final Map<OperatorCustomField, Boolean> editableFields = new HashMap<OperatorCustomField, Boolean>();
        for (final Iterator<OperatorCustomField> it = customFields.iterator(); it.hasNext();) {
            final OperatorCustomField field = it.next();
            final Visibility visibility = field.getVisibility();
            // Check if the field is visible
            if (myProfile && visibility == Visibility.NOT_VISIBLE) {
                it.remove();
            } else {
                // Check if the field is editable
                editableFields.put(field, (!myProfile || visibility == Visibility.EDITABLE));
            }
        }

        // Store the request attributes
        request.setAttribute("operator", operator);
        request.setAttribute("removed", operator.getGroup().getStatus() == Group.Status.REMOVED);
        request.setAttribute("customFields", customFieldHelper.buildEntries(customFields, operator.getCustomValues()));
        request.setAttribute("myProfile", myProfile);
        request.setAttribute("editableFields", editableFields);

        return context.getInputForward();
    }

    @Override
    protected ActionForward handleSubmit(final ActionContext context) throws Exception {
        final OperatorProfileForm form = context.getForm();
        Operator operator = getWriteDataBinder(context).readFromString(form.getOperator());
        operator = elementService.changeProfile(operator);
        context.sendMessage("profile.modified");
        return ActionHelper.redirectWithParam(context.getRequest(), super.handleSubmit(context), "operatorId", operator.getId());
    }

    @Override
    protected DataBinder<Operator> initDataBinderForRead(final ActionContext context) {
        final BeanBinder<Operator> dataBinder = (BeanBinder<Operator>) super.initDataBinderForRead(context);
        return dataBinder;
    }

    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
    protected DataBinder<Operator> initDataBinderForWrite(final ActionContext context) {
        final BeanBinder<Operator> dataBinder = (BeanBinder<Operator>) super.initDataBinderForWrite(context);

        final BeanBinder<OperatorCustomFieldValue> customValueBinder = BeanBinder.instance(OperatorCustomFieldValue.class);
        customValueBinder.registerBinder("field", PropertyBinder.instance(OperatorCustomField.class, "field", ReferenceConverter.instance(OperatorCustomField.class)));
        customValueBinder.registerBinder("value", PropertyBinder.instance(String.class, "value", HtmlConverter.instance()));

        final BeanCollectionBinder collectionBinder = (BeanCollectionBinder) dataBinder.getMappings().get("customValues");
        collectionBinder.setElementBinder(customValueBinder);

        return dataBinder;
    }

    @Override
    protected void validateForm(final ActionContext context) {
        final OperatorProfileForm form = context.getForm();
        final Operator operator = getWriteDataBinder(context).readFromString(form.getOperator());
        elementService.validate(operator, WhenSaving.PROFILE, false);
    }
}
