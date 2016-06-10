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

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.access.AdminSystemPermission;
import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.entities.accounts.cards.CardType;
import nl.strohalm.cyclos.services.accounts.cards.CardTypeService;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.RequestHelper;
import nl.strohalm.cyclos.utils.TimePeriod;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.DataBinderHelper;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.conversion.IdConverter;

import org.apache.struts.action.ActionForward;

/**
 * 
 * @author rodrigo
 */
public class EditCardTypeAction extends BaseFormAction {

    private CardTypeService      cardTypeService;
    private DataBinder<CardType> writeDataBinder;

    public CardTypeService getCardTypeService() {
        return cardTypeService;
    }

    // Used to get data and save to database
    public DataBinder<CardType> getWriteDataBinder() {
        if (writeDataBinder == null) {

            final BeanBinder<CardType> binder = BeanBinder.instance(CardType.class);
            binder.registerBinder("id", PropertyBinder.instance(Long.class, "id", IdConverter.instance()));
            binder.registerBinder("name", PropertyBinder.instance(String.class, "name"));
            binder.registerBinder("cardFormatNumber", PropertyBinder.instance(String.class, "cardFormatNumber"));
            binder.registerBinder("defaultExpiration", DataBinderHelper.timePeriodBinder("defaultExpiration"));
            binder.registerBinder("securityCodeBlockTime", DataBinderHelper.timePeriodBinder("securityCodeBlockTime"));
            binder.registerBinder("cardSecurityCode", PropertyBinder.instance(CardType.CardSecurityCode.class, "cardSecurityCode"));
            binder.registerBinder("showCardSecurityCode", PropertyBinder.instance(Boolean.TYPE, "showCardSecurityCode"));
            binder.registerBinder("ignoreDayInExpirationDate", PropertyBinder.instance(Boolean.TYPE, "ignoreDayInExpirationDate"));
            binder.registerBinder("cardSecurityCodeLength", DataBinderHelper.rangeConstraintBinder("cardSecurityCodeLength"));
            binder.registerBinder("maxSecurityCodeTries", PropertyBinder.instance(Integer.class, "maxSecurityCodeTries"));
            writeDataBinder = binder;
        }
        return writeDataBinder;
    }

    @Inject
    public void setCardTypeService(final CardTypeService cardTypeService) {
        this.cardTypeService = cardTypeService;
    }

    @Override
    protected ActionForward handleSubmit(final ActionContext context) throws Exception {
        final EditCardTypeForm form = context.getForm();
        CardType cardType = getWriteDataBinder().readFromString(form.getCardType());
        final boolean isInsert = cardType.isTransient();
        cardType = cardTypeService.save(cardType);
        if (isInsert) {
            context.sendMessage("cardType.inserted");
        } else {
            context.sendMessage("cardType.modified");
        }
        return ActionHelper.redirectWithParam(context.getRequest(), context.getSuccessForward(), "cardTypeId", cardType.getId());
    }

    @Override
    protected void prepareForm(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();
        final EditCardTypeForm form = context.getForm();
        final long cardTypeId = form.getCardTypeId();
        boolean editable;
        boolean hasCardGenerated = false;

        editable = permissionService.hasPermission(AdminSystemPermission.CARD_TYPES_MANAGE);
        CardType cardType;

        final boolean isInsert = cardTypeId <= 0L;
        if (isInsert) {
            cardType = new CardType();
            editable = true;
        } else {
            cardType = cardTypeService.load(cardTypeId);
            hasCardGenerated = cardTypeService.hasCards(cardTypeId);
        }

        getWriteDataBinder().writeAsString(form.getCardType(), cardType);

        request.setAttribute("cardType", cardType);
        request.setAttribute("isInsert", isInsert);
        request.setAttribute("editable", editable);
        request.setAttribute("hasCardGenerated", hasCardGenerated);
        request.setAttribute("defaultExpirations", Arrays.asList(TimePeriod.Field.MONTHS, TimePeriod.Field.YEARS));
        request.setAttribute("securityCodeBlockTime", Arrays.asList(TimePeriod.Field.MINUTES, TimePeriod.Field.HOURS, TimePeriod.Field.DAYS));
        RequestHelper.storeEnum(request, CardType.CardSecurityCode.class, "cardSecurityCodes");
    }

    @Override
    protected void validateForm(final ActionContext context) {
        final EditCardTypeForm form = context.getForm();
        final CardType cardType = getWriteDataBinder().readFromString(form.getCardType());
        cardTypeService.validate(cardType);
    }
}
