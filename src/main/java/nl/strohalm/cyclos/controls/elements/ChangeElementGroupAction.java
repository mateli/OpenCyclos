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
package nl.strohalm.cyclos.controls.elements;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.remarks.GroupRemark;
import nl.strohalm.cyclos.services.elements.RemarkService;
import nl.strohalm.cyclos.utils.EntityHelper;
import nl.strohalm.cyclos.utils.validation.RequiredError;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.commons.lang.StringUtils;

/**
 * Action used to change an element group
 * @author luis
 */
public class ChangeElementGroupAction extends BaseFormAction {

    private RemarkService remarkService;

    public RemarkService getRemarkService() {
        return remarkService;
    }

    @Inject
    public void setRemarkService(final RemarkService remarkService) {
        this.remarkService = remarkService;
    }

    @Override
    protected void formAction(final ActionContext context) throws Exception {
        final ChangeElementGroupForm form = context.getForm();
        final String comments = form.getComments();
        final Element element = elementService.load(form.getElementId());
        final Group newGroup = EntityHelper.reference(Group.class, form.getNewGroupId());
        elementService.changeGroup(element, newGroup, comments);
    }

    @Override
    protected void prepareForm(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();
        final ChangeElementGroupForm form = context.getForm();
        Element element = null;
        try {
            element = elementService.load(form.getElementId(), Element.Relationships.GROUP);
            final Element loggedElement = context.getElement();
            if (loggedElement.equals(element)) {
                throw new Exception();
            }
        } catch (final Exception e) {
            element = null;
        }
        if (element == null) {
            throw new ValidationException();
        }

        // Retrieve the possible new groups
        final List<? extends Group> possible = elementService.getPossibleNewGroups(element);
        final Group currentGroup = element.getGroup();
        form.setNewGroupId(currentGroup.getId());
        request.setAttribute("permanentlyRemoved", currentGroup.getStatus() == Group.Status.REMOVED);

        // Retrieve the history
        final List<GroupRemark> history = remarkService.listGroupChangeHistory(element);

        request.setAttribute("element", element);
        request.setAttribute("possibleGroups", possible);
        request.setAttribute("history", history);
    }

    @Override
    protected void validateForm(final ActionContext context) {
        final ChangeElementGroupForm form = context.getForm();
        final ValidationException val = new ValidationException();
        val.setPropertyKey("elementId", "member.member");
        val.setPropertyKey("newGroupId", "changeGroup.new");
        val.setPropertyKey("comments", "remark.comments");
        if (form.getElementId() <= 0) {
            val.addPropertyError("elementId", new RequiredError());
        }
        if (form.getNewGroupId() <= 0) {
            val.addPropertyError("newGroupId", new RequiredError());
        }
        if (StringUtils.isEmpty(form.getComments())) {
            val.addPropertyError("comments", new RequiredError());
        }
        val.throwIfHasErrors();
    }

}
