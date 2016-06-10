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

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.records.MemberRecord;
import nl.strohalm.cyclos.entities.members.records.MemberRecordQuery;
import nl.strohalm.cyclos.entities.members.records.MemberRecordType;

/**
 * Action used to edit and list member records on a single page
 * @author luis
 */
public class FlatMemberRecordsAction extends EditMemberRecordAction {

    @Override
    protected void prepareForm(final ActionContext context) throws Exception {
        super.prepareForm(context);
        final HttpServletRequest request = context.getRequest();
        final MemberRecordType type = (MemberRecordType) request.getAttribute("type");
        final Element element = (Element) request.getAttribute("element");
        final MemberRecordQuery query = new MemberRecordQuery();
        query.setType(type);
        query.setElement(element);
        final List<MemberRecord> memberRecords = getMemberRecordService().search(query);
        request.setAttribute("memberRecords", memberRecords);
        request.setAttribute("removed", element.getGroup().getStatus() == Group.Status.REMOVED);
    }
}
