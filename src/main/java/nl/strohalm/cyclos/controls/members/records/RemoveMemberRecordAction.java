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

import java.util.HashMap;
import java.util.Map;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseAction;
import nl.strohalm.cyclos.entities.members.records.MemberRecord;
import nl.strohalm.cyclos.entities.members.records.MemberRecordType;
import nl.strohalm.cyclos.entities.members.records.MemberRecordType.Layout;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.elements.MemberRecordService;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.struts.action.ActionForward;

/**
 * Action used to remove a member record
 * @author Jefferson Magno
 */
public class RemoveMemberRecordAction extends BaseAction {

    private MemberRecordService memberRecordService;

    @Inject
    public void setMemberRecordService(final MemberRecordService memberRecordService) {
        this.memberRecordService = memberRecordService;
    }

    @Override
    protected ActionForward executeAction(final ActionContext context) throws Exception {
        final RemoveMemberRecordForm form = context.getForm();
        final long id = form.getMemberRecordId();
        if (id <= 0) {
            throw new ValidationException();
        }
        final MemberRecord record = memberRecordService.load(id, MemberRecord.Relationships.ELEMENT, MemberRecord.Relationships.TYPE);
        final MemberRecordType type = record.getType();
        final String typeName = type.getName();

        try {
            memberRecordService.remove(id);
            context.sendMessage("memberRecord.removed", typeName);
        } catch (final PermissionDeniedException e) {
            throw e;
        } catch (final Exception e) {
            return context.sendError("memberRecord.error.removing", typeName);
        }

        boolean isGlobal = false;
        final SearchMemberRecordsForm searchForm = (SearchMemberRecordsForm) context.getSession().getAttribute("searchMemberRecordsForm");
        if (searchForm != null && searchForm.isGlobal()) {
            isGlobal = true;
        }

        final Map<String, Object> params = new HashMap<String, Object>();
        final boolean isFlat = record.getType().getLayout() == Layout.FLAT;
        ActionForward forward;
        if (isGlobal) {
            forward = context.getSuccessForward();
        } else {
            if (isFlat) {
                forward = context.findForward("successFlat");
            } else {
                forward = context.getSuccessForward();
            }
            params.put("elementId", record.getElement().getId());
            params.put("typeId", type.getId());
        }

        return ActionHelper.redirectWithParams(context.getRequest(), forward, params);
    }
}
