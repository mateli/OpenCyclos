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
package nl.strohalm.cyclos.controls.ads;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.access.AdminMemberPermission;
import nl.strohalm.cyclos.access.BrokerPermission;
import nl.strohalm.cyclos.access.MemberPermission;
import nl.strohalm.cyclos.access.OperatorPermission;
import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseAction;
import nl.strohalm.cyclos.entities.ads.Ad;
import nl.strohalm.cyclos.entities.ads.AdQuery;
import nl.strohalm.cyclos.entities.customization.images.AdImage;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.Operator;
import nl.strohalm.cyclos.services.ads.AdService;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.struts.action.ActionForward;

/**
 * Lists the advertisements of a given member
 * @author luis
 */
public class MemberAdsAction extends BaseAction {

    private AdService adService;

    public AdService getAdService() {
        return adService;
    }

    @Inject
    public void setAdService(final AdService adService) {
        this.adService = adService;
    }

    @Override
    protected ActionForward executeAction(final ActionContext context) throws Exception {
        final MemberAdsForm form = context.getForm();
        Member member;
        boolean myAds = false;
        boolean editable = false;
        // Read only means that the broker is viewing member ads as a common member
        final boolean brokerViewingAsMember = form.isReadOnly();

        // if the memberId parameter is zero or is equals to the logged user or is equals to the logged operator's member
        if (form.getMemberId() <= 0 || form.getMemberId() == context.getElement().getId() ||
                (context.isOperator() && form.getMemberId() == ((Operator) context.getElement()).getMember().getId())) {
            if (context.isMember()) {
                member = context.getElement();
                editable = permissionService.hasPermission(MemberPermission.ADS_PUBLISH);
            } else if (context.isOperator()) {
                member = ((Operator) context.getElement()).getMember();
                editable = permissionService.hasPermission(OperatorPermission.ADS_PUBLISH);
            } else {
                throw new ValidationException();
            }
            myAds = true;
        } else {
            final Element element = elementService.load(form.getMemberId(), Element.Relationships.USER);

            if (!(element instanceof Member)) {
                throw new ValidationException();
            }

            member = (Member) element;
            if (context.isMember()) {
                editable = !brokerViewingAsMember && context.isBrokerOf(member) && permissionService.hasPermission(BrokerPermission.ADS_MANAGE);
            } else if (context.isAdmin()) {
                editable = permissionService.hasPermission(AdminMemberPermission.ADS_MANAGE);
            }
        }

        final AdQuery query = new AdQuery();
        query.fetch(RelationshipHelper.nested(Ad.Relationships.OWNER, Element.Relationships.USER), Ad.Relationships.CURRENCY);
        query.setMyAds(myAds);
        query.setOwner(member);

        // Member viewing another member's ads
        if (!context.isAdmin() && !myAds && !context.isBrokerOf(member)) {
            query.setStatus(Ad.Status.ACTIVE);
        }

        if (brokerViewingAsMember) {
            query.setStatus(Ad.Status.ACTIVE);
        }

        final List<Ad> ads = adService.search(query);

        // Check if any ad has images
        boolean hasImages = false;
        for (final Ad ad : ads) {
            final Collection<AdImage> images = ad.getImages();
            if (images != null && !images.isEmpty()) {
                hasImages = true;
                break;
            }
        }

        // Check for maxAds
        member = elementService.load(member.getId(), Element.Relationships.GROUP);
        final int adCount = ads.size();
        final int maxAdsPerMember = member.getMemberGroup().getMemberSettings().getMaxAdsPerMember();

        final HttpServletRequest request = context.getRequest();
        request.setAttribute("member", member);
        request.setAttribute("hasImages", hasImages);
        request.setAttribute("myAds", myAds);
        request.setAttribute("editable", editable);
        request.setAttribute("ads", ads);
        request.setAttribute("readOnly", brokerViewingAsMember);
        request.setAttribute("maxAds", adCount >= maxAdsPerMember);

        return context.getInputForward();
    }

}
