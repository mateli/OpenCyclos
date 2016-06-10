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

import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.entities.accounts.cards.Card;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsEvent;
import nl.strohalm.cyclos.services.accounts.cards.CardService;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.DataBinderHelper;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.binding.SimpleCollectionBinder;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.struts.action.ActionForward;

/**
 * 
 * @author rodrigo
 */

public class CreateCardAction extends BaseFormAction {

    protected CardService    cardService;

    private DataBinder<Card> dataBinder;

    public CardService getCardService() {
        return cardService;
    }

    public DataBinder<Card> getDataBinder() {
        if (dataBinder == null) {
            final LocalSettings localSettings = settingsService.getLocalSettings();
            final BeanBinder<Card> binder = BeanBinder.instance(Card.class);
            binder.registerBinder("groups", SimpleCollectionBinder.instance(Group.class, "groups"));
            binder.registerBinder("status", SimpleCollectionBinder.instance(Card.Status.class, "status"));
            binder.registerBinder("expiration", DataBinderHelper.periodBinder(localSettings, "expiration"));
            binder.registerBinder("member", PropertyBinder.instance(Member.class, "member"));
            binder.registerBinder("number", PropertyBinder.instance(Card.class, "number"));

            dataBinder = binder;
        }
        return dataBinder;
    }

    public void onLocalSettingsUpdate(final LocalSettingsEvent event) {
        dataBinder = null;
    }

    @Inject
    public void setCardService(final CardService cardService) {
        this.cardService = cardService;
    }

    @Override
    protected ActionForward handleSubmit(final ActionContext context) throws Exception {

        final CardForm form = context.getForm();
        final long memberId = form.getMemberId();

        // save the card object

        Member member = null;
        try {
            if (memberId > 0L) {
                member = elementService.load(memberId, Element.Relationships.USER, RelationshipHelper.nested(Element.Relationships.GROUP));
                if (member == null) {
                    throw new Exception();
                }
            }
            final Card card = cardService.generateNewCard(member);

            ActionForward forward;
            forward = context.findForward("view");
            final Map<String, Object> params = new TreeMap<String, Object>();
            params.put("cardId", card.getId());
            params.put("listOnly", form.getListOnly());
            context.sendMessage("card.created");
            return ActionHelper.redirectWithParams(context.getRequest(), forward, params);
        } catch (final Exception e) {
            throw new ValidationException();
        }

    }

    @Override
    protected void prepareForm(final ActionContext context) throws Exception {

        final HttpServletRequest request = context.getRequest();
        final CardForm form = context.getForm();
        final long memberId = form.getMemberId();
        Member member = null;
        try {
            if (memberId > 0L) {
                member = elementService.load(memberId, Element.Relationships.USER, RelationshipHelper.nested(Element.Relationships.GROUP));
                if (member == null) {
                    throw new Exception();
                }
            }
            request.setAttribute("member", member);
        } catch (final Exception e) {
            throw new ValidationException();
        }

    }
}
