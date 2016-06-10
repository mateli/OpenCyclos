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
package nl.strohalm.cyclos.controls.access.channels;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseAction;
import nl.strohalm.cyclos.entities.access.Channel;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.EntityHelper;
import nl.strohalm.cyclos.utils.conversion.CoercionHelper;

import org.apache.struts.action.ActionForward;

/**
 * Action used to prepare the external access screen
 * @author Jefferson Magno
 */
public class SelectChannelsAction extends BaseAction {

    @Override
    protected ActionForward executeAction(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();
        final SelectChannelsForm form = context.getForm();
        final long memberId = form.getMemberId();
        Member member;
        if (memberId <= 0L) {
            member = context.getElement();
        } else {
            member = EntityHelper.reference(Member.class, memberId);
        }
        final String[] channelsStr = form.getChannels();
        final List<Channel> channels = (List<Channel>) CoercionHelper.coerceCollection(Channel.class, channelsStr);
        accessService.changeChannelsAccess(member, channels, true);
        context.sendMessage("selectChannels.selected");
        return ActionHelper.redirectWithParam(request, context.getSuccessForward(), "memberId", memberId);
    }
}
