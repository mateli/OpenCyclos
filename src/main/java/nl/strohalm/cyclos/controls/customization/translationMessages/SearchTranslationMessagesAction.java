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
package nl.strohalm.cyclos.controls.customization.translationMessages;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseQueryAction;
import nl.strohalm.cyclos.entities.customization.translationMessages.TranslationMessage;
import nl.strohalm.cyclos.entities.customization.translationMessages.TranslationMessageQuery;
import nl.strohalm.cyclos.services.customization.TranslationMessageService;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.DataBinderHelper;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.query.QueryParameters;

/**
 * Action used to search translation messages
 * @author luis
 */
public class SearchTranslationMessagesAction extends BaseQueryAction {

    private TranslationMessageService           translationMessageService;
    private DataBinder<TranslationMessageQuery> dataBinder;

    public DataBinder<TranslationMessageQuery> getDataBinder() {
        if (dataBinder == null) {
            final BeanBinder<TranslationMessageQuery> binder = BeanBinder.instance(TranslationMessageQuery.class);
            binder.registerBinder("key", PropertyBinder.instance(String.class, "key"));
            binder.registerBinder("value", PropertyBinder.instance(String.class, "value"));
            binder.registerBinder("showOnlyEmpty", PropertyBinder.instance(Boolean.TYPE, "showOnlyEmpty"));
            binder.registerBinder("pageParameters", DataBinderHelper.pageBinder());
            dataBinder = binder;
        }
        return dataBinder;
    }

    @Inject
    public void setTranslationMessageService(final TranslationMessageService translationMessageService) {
        this.translationMessageService = translationMessageService;
    }

    @Override
    protected void executeQuery(final ActionContext context, final QueryParameters queryParameters) {
        final HttpServletRequest request = context.getRequest();
        final TranslationMessageQuery query = (TranslationMessageQuery) queryParameters;
        final List<TranslationMessage> translationMessages = translationMessageService.search(query);
        request.setAttribute("messages", translationMessages);
    }

    @Override
    protected QueryParameters prepareForm(final ActionContext context) {
        final SearchTranslationMessagesForm form = context.getForm();
        return getDataBinder().readFromString(form.getQuery());
    }

}
