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
package nl.strohalm.cyclos.services.accounts.cards;

import java.util.ArrayList;
import java.util.List;

import nl.strohalm.cyclos.access.AdminSystemPermission;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.cards.CardType;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.BaseServiceSecurity;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.access.LoggedUser;

/**
 * Security implementation for {@link CardTypeService}
 * 
 * @author Rinke
 */
public class CardTypeServiceSecurity extends BaseServiceSecurity implements CardTypeService {

    private CardTypeServiceLocal cardTypeService;

    @Override
    public boolean hasCards(final long cardTypeId) {
        // called by EditCardTypeAction, so only for admins
        checkAdminView();
        return cardTypeService.hasCards(cardTypeId);
    }

    @Override
    public List<CardType> listAll() {
        List<CardType> result = cardTypeService.listAll();
        return filterVisible(result);
    }

    @Override
    public CardType load(final Long id, final Relationship... fetch) {
        CardType cardType = cardTypeService.load(id, fetch);
        if (!permissionService.hasPermission(AdminSystemPermission.CARD_TYPES_VIEW)) {
            // Members associated with that card type could still see
            Member member = LoggedUser.member();
            if (member != null) {
                member = (Member) fetchService.fetch(LoggedUser.element(),
                        RelationshipHelper.nested(Element.Relationships.GROUP, MemberGroup.Relationships.CARD_TYPE));
                CardType associatedType = ((MemberGroup) member.getGroup()).getCardType();
                if (associatedType != null) {
                    return fetchService.fetch(associatedType, fetch);
                }
            }
            throw new PermissionDeniedException();
        }
        return cardType;
    }

    @Override
    public int remove(final Long... ids) {
        checkAdminManage();
        return cardTypeService.remove(ids);
    }

    @Override
    public CardType save(final CardType cardType) {
        checkAdminManage();
        return cardTypeService.save(cardType);
    }

    public void setCardTypeServiceLocal(final CardTypeServiceLocal cardTypeService) {
        this.cardTypeService = cardTypeService;
    }

    @Override
    public void validate(final CardType cardType) {
        // no permissions on validation
        cardTypeService.validate(cardType);
    }

    private void checkAdminManage() {
        permissionService.permission().admin(AdminSystemPermission.CARD_TYPES_MANAGE).check();
    }

    private void checkAdminView() {
        permissionService.permission().admin(AdminSystemPermission.CARD_TYPES_VIEW).check();
    }

    /**
     * filters the input list, so that only visible cardTypes are returnd. For admins with the correct permissions, these are all types in the input
     * list. For members there can only be one cardType.
     * @param unfilteredList
     * @return
     */
    private List<CardType> filterVisible(final List<CardType> unfilteredList) {
        // An admin with view permission can see all types
        if (permissionService.hasPermission(AdminSystemPermission.CARD_TYPES_VIEW)) {
            return unfilteredList;
        }
        List<CardType> result = new ArrayList<CardType>(1);
        // in case of a member, there's only one cardType possible, which we will add to an empty list
        if (LoggedUser.isMember()) {
            Member member = (Member) fetchService.fetch(LoggedUser.element(),
                    RelationshipHelper.nested(Element.Relationships.GROUP, MemberGroup.Relationships.CARD_TYPE));
            final CardType cardType = ((MemberGroup) member.getGroup()).getCardType();
            if (unfilteredList.contains(cardType)) {
                result.add(cardType);
            }
        }
        return result;
    }
}
