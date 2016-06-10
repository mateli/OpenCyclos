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
package nl.strohalm.cyclos.controls.members;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseAjaxAction;
import nl.strohalm.cyclos.entities.access.PrincipalType;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.services.access.ChannelService;
import nl.strohalm.cyclos.utils.binding.DataBinderHelper;

/**
 * Action used to load a member, either by username or card number
 * @author luis
 */
public class LoadMemberAjaxAction extends BaseAjaxAction {

    private ChannelService channelService;

    @Inject
    public void setChannelService(final ChannelService channelService) {
        this.channelService = channelService;
    }

    @Override
    protected ContentType contentType() {
        return ContentType.JSON;
    }

    @Override
    protected void renderContent(final ActionContext context) throws Exception {
        final LoadMemberAjaxForm form = context.getForm();
        final PrincipalType principalType = channelService.resolvePrincipalType(form.getChannel(), form.getPrincipalType());
        final String principal = form.getPrincipal();
        Member member;
        try {
            member = elementService.loadByPrincipal(principalType, principal, Element.Relationships.USER);
        } catch (final EntityNotFoundException e) {
            member = null;
        }
        final String json = DataBinderHelper.simpleElementBinder().readAsString(member);
        responseHelper.writeJSON(context.getResponse(), json);
    }
}
