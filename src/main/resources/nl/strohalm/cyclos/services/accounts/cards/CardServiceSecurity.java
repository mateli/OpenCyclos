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

import java.util.Collection;
import java.util.List;

import nl.strohalm.cyclos.access.AdminMemberPermission;
import nl.strohalm.cyclos.access.BrokerPermission;
import nl.strohalm.cyclos.access.MemberPermission;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.cards.Card;
import nl.strohalm.cyclos.entities.accounts.cards.CardQuery;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.members.FullTextMemberQuery;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.services.BaseServiceSecurity;
import nl.strohalm.cyclos.services.elements.BulkMemberActionResultVO;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.utils.access.PermissionHelper;

/**
 * Security implementation for {@link CardService}
 * 
 * @author Rinke
 */
public class CardServiceSecurity extends BaseServiceSecurity implements CardService {

    private CardServiceLocal cardService;

    @Override
    public Card activateCard(Card card, final String cardCode) {
        card = fetchService.fetch(card, Card.Relationships.OWNER);
        permissionService.permission(card.getOwner())
                .admin(AdminMemberPermission.CARDS_UNBLOCK)
                .broker(BrokerPermission.CARDS_UNBLOCK)
                .member(MemberPermission.CARDS_UNBLOCK)
                .check();
        return cardService.activateCard(card, cardCode);
    }

    @Override
    public Card blockCard(Card card) {
        card = fetchService.fetch(card, Card.Relationships.OWNER);
        permissionService.permission(card.getOwner())
                .admin(AdminMemberPermission.CARDS_BLOCK)
                .broker(BrokerPermission.CARDS_BLOCK)
                .member(MemberPermission.CARDS_BLOCK)
                .check();
        return cardService.blockCard(card);
    }

    @Override
    @SuppressWarnings("unchecked")
    public BulkMemberActionResultVO bulkGenerateNewCard(final FullTextMemberQuery query, final boolean generateForPending, final boolean generateForActive) {
        if (query.getBroker() != null) {
            permissionService.checkRelatesTo(query.getBroker());
        }
        Collection<Group> queryGroups = (Collection<Group>) query.getGroups();
        query.setGroups(PermissionHelper.checkSelection(permissionService.getAllVisibleGroups(), queryGroups));
        permissionService.permission().admin(AdminMemberPermission.BULK_ACTIONS_GENERATE_CARD).check();
        return cardService.bulkGenerateNewCard(query, generateForPending, generateForActive);
    }

    @Override
    public Card cancelCard(Card card) {
        card = fetchService.fetch(card, Card.Relationships.OWNER);
        permissionService.permission(card.getOwner())
                .admin(AdminMemberPermission.CARDS_CANCEL)
                .broker(BrokerPermission.CARDS_CANCEL)
                .check();
        return cardService.cancelCard(card);
    }

    @Override
    public Card changeCardCode(Card card, final String code) {
        card = fetchService.fetch(card, Card.Relationships.OWNER);
        permissionService.permission(card.getOwner())
                .admin(AdminMemberPermission.CARDS_CHANGE_CARD_SECURITY_CODE)
                .broker(BrokerPermission.CARDS_CHANGE_CARD_SECURITY_CODE)
                .member(MemberPermission.CARDS_CHANGE_CARD_SECURITY_CODE)
                .check();
        return cardService.changeCardCode(card, code);
    }

    @Override
    public Card generateNewCard(final Member member) {
        permissionService.permission(member)
                .admin(AdminMemberPermission.CARDS_GENERATE)
                .broker(BrokerPermission.CARDS_GENERATE)
                .check();
        return cardService.generateNewCard(member);
    }

    @Override
    public Card getActiveCard(final Member member) {
        checkView(member);
        return cardService.getActiveCard(member);
    }

    @Override
    public Card load(final long cardId, final Relationship... fetch) {
        Relationship[] newFetch = addToFetch(fetch, Card.Relationships.OWNER);
        Card card = cardService.load(cardId, newFetch);
        checkView(card.getOwner());
        return card;
    }

    @Override
    public List<Card> search(final CardQuery query) {
        Member member = query.getMember();
        if (member == null) {
            // No specific member - We can handle admins or brokers
            permissionService.permission()
                    .admin(AdminMemberPermission.CARDS_VIEW)
                    .broker(BrokerPermission.CARDS_VIEW)
                    .check();
            if (LoggedUser.isBroker()) {
                query.setBroker(LoggedUser.member());
            } else if (LoggedUser.isAdministrator()) {
                query.setGroups(permissionService.getVisibleMemberGroups());
            }
        } else {
            checkView(member);
        }
        return cardService.search(query);
    }

    public void setCardServiceLocal(final CardServiceLocal cardService) {
        this.cardService = cardService;
    }

    @Override
    public Card unblockCard(Card card) {
        card = fetchService.fetch(card, Card.Relationships.OWNER);
        permissionService.permission(card.getOwner())
                .admin(AdminMemberPermission.CARDS_UNBLOCK)
                .broker(BrokerPermission.CARDS_UNBLOCK)
                .member(MemberPermission.CARDS_UNBLOCK)
                .check();
        return cardService.unblockCard(card);
    }

    @Override
    public void unblockSecurityCode(Card card) {
        card = fetchService.fetch(card, Card.Relationships.OWNER);
        permissionService.permission(card.getOwner())
                .admin(AdminMemberPermission.CARDS_UNBLOCK_SECURITY_CODE)
                .broker(BrokerPermission.CARDS_UNBLOCK_SECURITY_CODE)
                .check();
        cardService.unblockSecurityCode(card);
    }

    private void checkView(final Member member) {
        permissionService.permission(member)
                .admin(AdminMemberPermission.CARDS_VIEW)
                .broker(BrokerPermission.CARDS_VIEW)
                .member(MemberPermission.CARDS_VIEW)
                .check();
    }

}
