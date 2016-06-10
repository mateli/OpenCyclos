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
package nl.strohalm.cyclos.controls.accounts.cards;

import java.util.Collection;

import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.entities.accounts.cards.Card;
import nl.strohalm.cyclos.entities.accounts.cards.CardQuery;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.utils.EntityHelper;
import nl.strohalm.cyclos.utils.query.QueryParameters;

/**
 * Class used to print cards on search cards page
 * 
 * @author rodrigo
 */
public class PrintCardsAction extends SearchCardsAction {

    @Override
    protected Integer pageSize(final ActionContext context) {
        return null;
    }

    @Override
    protected QueryParameters prepareForm(final ActionContext context) {
        final CardQuery query = (CardQuery) super.prepareForm(context);
        query.fetch(Card.Relationships.CARD_TYPE);
        final Collection<MemberGroup> groups = groupService.load(EntityHelper.toIdsAsList(query.getGroups()));
        query.setGroups(groups);

        return query;
    }

    @Override
    protected boolean shouldLimit() {
        return false;
    }

}
