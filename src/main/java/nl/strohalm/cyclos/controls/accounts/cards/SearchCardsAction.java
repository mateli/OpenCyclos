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

import java.math.BigInteger;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseQueryAction;
import nl.strohalm.cyclos.entities.accounts.cards.Card;
import nl.strohalm.cyclos.entities.accounts.cards.CardQuery;
import nl.strohalm.cyclos.entities.accounts.cards.CardType;
import nl.strohalm.cyclos.entities.groups.AdminGroup;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.GroupFilterQuery;
import nl.strohalm.cyclos.entities.groups.GroupQuery;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsChangeListener;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsEvent;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.accounts.cards.CardService;
import nl.strohalm.cyclos.services.accounts.cards.CardTypeService;
import nl.strohalm.cyclos.services.elements.ElementService;
import nl.strohalm.cyclos.services.groups.GroupService;
import nl.strohalm.cyclos.utils.RequestHelper;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.DataBinderHelper;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.binding.SimpleCollectionBinder;
import nl.strohalm.cyclos.utils.query.QueryParameters;

/**
 * @author rodrigo
 */
public abstract class SearchCardsAction extends BaseQueryAction implements LocalSettingsChangeListener {

    @SuppressWarnings("unchecked")
    public static boolean prepareQuery(final ActionContext context, final CardQuery query, final ElementService elementService, final GroupService groupService) {
        boolean listOnly = true;
        final HttpServletRequest request = context.getRequest();
        final SearchCardsForm form = context.getForm();
        final long memberId = form.getMemberId();

        if (RequestHelper.isGet(request)) {
            query.setStatus(Collections.singleton(Card.Status.ACTIVE));
        }
        final GroupFilterQuery groupFilterQuery = new GroupFilterQuery();
        final GroupQuery groupQuery = new GroupQuery();

        if (RequestHelper.isFromMenu(request)) {
            request.setAttribute("isFromMenu", true);
        } else {
            request.setAttribute("isFromMenu", false);
        }

        if (context.isAdmin()) {
            // Store the member groups for admins
            final AdminGroup adminGroup = context.getGroup();
            groupFilterQuery.setAdminGroup(adminGroup);
            groupQuery.setNatures(Group.Nature.MEMBER, Group.Nature.BROKER);
            groupQuery.setManagedBy(adminGroup);
            final List<MemberGroup> groups = (List<MemberGroup>) groupService.search(groupQuery);
            if (groups.isEmpty()) {
                throw new PermissionDeniedException();
            }
            request.setAttribute("groups", groups);
            listOnly = false;

            if (query.getGroups() != null) {
                for (final Iterator<MemberGroup> iterator = query.getGroups().iterator(); iterator.hasNext();) {
                    // final MemberGroup memberGroup = iterator.next();
                    if (!groups.contains(iterator.next())) {
                        iterator.remove();
                    }
                }
            }
            if (query.getGroups().size() == 0) {
                query.setGroups(groups);
            }

        }

        Member member = null;

        if (memberId > 0L) {
            if (RequestHelper.isFromMenu(request)) {
                request.setAttribute("isFromMenu", true);
            }

            member = elementService.load(memberId);
        }

        // Members
        if (query.getMember() != null) {
            final Member member2 = elementService.load(query.getMember().getId());
            query.setMember(member2);
        }

        if (context.isBroker()) {
            listOnly = false;
            request.setAttribute("isBroker", true);
            if (memberId > 0L) {
                if (!member.equals(context.getElement())) {
                    query.setBroker((Member) context.getElement());
                }
            } else {
                query.setBroker((Member) context.getElement());
            }
        }

        if (!context.isMember() || (memberId == 0L)) {
            if (context.isMember() && !context.isBroker() && memberId == 0) {
                throw new PermissionDeniedException();
            }
        }

        // If it comes from profileOfMemberByXXX.jsp page or isMember and come from Cards menu, shouldn't show filters panel. Must show only a list
        // with user's card

        if (memberId > 0L) {
            if ((!context.isBroker() && !context.isAdmin() && !member.equals(context.getElement())) || (context.isBroker() && !context.isBrokerOf(member) && !member.equals(context.getElement()))) {
                throw new PermissionDeniedException();
            }
            query.setMember(member);
            listOnly = true;
            request.setAttribute("member", member);
            request.setAttribute("cardOwner", member.getId());
        } else {
            request.setAttribute("cardOwner", 0);
        }

        if (listOnly) {
            query.setStatus(null);
        }

        return listOnly;
    }

    private DataBinder<CardQuery> dataBinder;

    protected CardService         cardService;
    protected CardTypeService     cardTypeService;

    private ReadWriteLock         lock = new ReentrantReadWriteLock(true);

    public CardService getCardService() {
        return cardService;
    }

    public CardTypeService getCardTypeService() {
        return cardTypeService;
    }

    public DataBinder<CardQuery> getDataBinder() {
        try {
            lock.readLock().lock();
            if (dataBinder == null) {
                final LocalSettings localSettings = settingsService.getLocalSettings();
                final BeanBinder<CardQuery> binder = BeanBinder.instance(CardQuery.class);
                binder.registerBinder("groups", SimpleCollectionBinder.instance(Group.class, "groups"));
                binder.registerBinder("status", SimpleCollectionBinder.instance(Card.Status.class, "status"));
                binder.registerBinder("expiration", DataBinderHelper.rawPeriodBinder(localSettings, "expiration"));
                binder.registerBinder("member", PropertyBinder.instance(Member.class, "member"));
                binder.registerBinder("number", PropertyBinder.instance(BigInteger.class, "number"));
                binder.registerBinder("cardType", PropertyBinder.instance(CardType.class, "cardType"));
                binder.registerBinder("pageParameters", DataBinderHelper.pageBinder());

                dataBinder = binder;
            }
            return dataBinder;
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public void onLocalSettingsUpdate(final LocalSettingsEvent event) {
        try {
            lock.writeLock().lock();
            super.onLocalSettingsUpdate(event);
            dataBinder = null;
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Inject
    public void setCardService(final CardService cardService) {
        this.cardService = cardService;
    }

    @Inject
    public void setCardTypeService(final CardTypeService cardTypeService) {
        this.cardTypeService = cardTypeService;
    }

    @Override
    protected void executeQuery(final ActionContext context, final QueryParameters queryParameters) {
        final CardQuery query = (CardQuery) queryParameters;
        final List<Card> cards = cardService.search(query);
        context.getRequest().setAttribute("cards", cards);
    }

    @Override
    protected QueryParameters prepareForm(final ActionContext context) {
        final SearchCardsForm form = context.getForm();
        final CardQuery query = getDataBinder().readFromString(form.getQuery());

        final boolean listOnly = prepareQuery(context, query, elementService, groupService);
        final HttpServletRequest request = context.getRequest();

        // Card status
        RequestHelper.storeEnum(request, Card.Status.class, "status");

        // CardTypes
        final List<CardType> cardTypes = cardTypeService.listAll();
        request.setAttribute("cardTypes", cardTypes);

        request.setAttribute("listOnly", listOnly);
        request.setAttribute("isAdmin", context.isAdmin());
        return query;
    }

    @Override
    protected boolean willExecuteQuery(final ActionContext context, final QueryParameters queryParameters) throws Exception {
        final CardQuery query = (CardQuery) queryParameters;
        return ((query.getMember() != null) && (query.getMember().getId() > 0)) || RequestHelper.isFromMenu(context.getRequest()) || (RequestHelper.isPost(context.getRequest()));
    }

}
