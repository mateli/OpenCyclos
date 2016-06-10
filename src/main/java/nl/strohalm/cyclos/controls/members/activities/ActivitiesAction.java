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
package nl.strohalm.cyclos.controls.members.activities;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseAction;
import nl.strohalm.cyclos.entities.ads.Ad;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.Reference;
import nl.strohalm.cyclos.services.elements.ActivitiesVO;
import nl.strohalm.cyclos.services.elements.MemberService;
import nl.strohalm.cyclos.utils.RequestHelper;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.struts.action.ActionForward;

/**
 * Action to retrieve a member's activities
 * @author luis
 * @author Jefferson Magno
 */
public class ActivitiesAction extends BaseAction {

    private MemberService memberService;

    public MemberService getMemberService() {
        return memberService;
    }

    @Inject
    public void setMemberService(final MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    protected ActionForward executeAction(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();
        final ActivitiesForm form = context.getForm();

        boolean myActivities = false;

        Member member;
        if (form.getMemberId() <= 0 || context.getElement().getId().equals(form.getMemberId())) {
            member = context.getMember();
            myActivities = true;
        } else {
            final Element element = elementService.load(form.getMemberId(), Element.Relationships.USER, Element.Relationships.GROUP);
            if (!(element instanceof Member)) {
                throw new ValidationException();
            }
            member = (Member) element;
        }

        // Get the activities
        final ActivitiesVO activities = memberService.getActivities(member);

        request.setAttribute("member", member);
        request.setAttribute("activities", activities);
        request.setAttribute("myActivities", myActivities);

        RequestHelper.storeEnum(request, Reference.Level.class, "referenceLevels");
        RequestHelper.storeEnumMap(request, Ad.Status.class, "adStatus");

        return context.getInputForward();
    }

}
