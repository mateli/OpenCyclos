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
package nl.strohalm.cyclos.controls.members.bulk;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.controls.members.SearchMembersAction;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomFieldValue;
import nl.strohalm.cyclos.entities.members.FullTextMemberQuery;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsChangeListener;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsEvent;
import nl.strohalm.cyclos.services.accounts.cards.CardService;
import nl.strohalm.cyclos.services.elements.BulkMemberActionResultVO;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.MapBean;
import nl.strohalm.cyclos.utils.conversion.CoercionHelper;
import nl.strohalm.cyclos.utils.validation.RequiredError;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author rodrigo
 */
public class MemberBulkGenerateCardAction extends BaseFormAction implements LocalSettingsChangeListener {

    private DataBinder<FullTextMemberQuery> dataBinder;
    private CardService                     cardService;
    private ReadWriteLock                   lock = new ReentrantReadWriteLock(true);

    public CardService getCardService() {
        return cardService;
    }

    public DataBinder<FullTextMemberQuery> getDataBinder() {
        try {
            lock.readLock().lock();
            if (dataBinder == null) {
                final LocalSettings localSettings = settingsService.getLocalSettings();
                dataBinder = SearchMembersAction.memberQueryDataBinder(localSettings);
            }
            return dataBinder;
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * @see nl.strohalm.cyclos.entities.settings.events.LocalSettingsChangeListener#onLocalSettingsUpdate(nl.strohalm.cyclos.entities.settings.events.LocalSettingsEvent)
     */
    @Override
    public void onLocalSettingsUpdate(final LocalSettingsEvent event) {
        try {
            lock.writeLock().lock();
            dataBinder = null;
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Inject
    public void setCardService(final CardService cardService) {
        this.cardService = cardService;
    }

    @Override
    protected void formAction(final ActionContext context) throws Exception {
        final MemberBulkActionsForm form = context.getForm();
        final MapBean bean = form.getGenerateCard();
        final FullTextMemberQuery query = getDataBinder().readFromString(form.getQuery());
        final boolean generateForPending = CoercionHelper.coerce(Boolean.TYPE, bean.get("generateForPending"));
        final boolean generateForActive = CoercionHelper.coerce(Boolean.TYPE, bean.get("generateForActive"));

        final BulkMemberActionResultVO results = cardService.bulkGenerateNewCard(query, generateForPending, generateForActive);
        context.sendMessage("member.bulkActions.cardGenerated", results.getChanged(), results.getUnchanged());

        // Clear the generate card parameters
        form.getGenerateCard().clear();

    }

    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
    protected void validateForm(final ActionContext context) {
        final MemberBulkActionsForm form = context.getForm();
        final MapBean bean = form.getGenerateCard();
        final FullTextMemberQuery query = getDataBinder().readFromString(form.getQuery());
        final String comments = StringUtils.trimToNull((String) bean.get("comments"));

        final Collection<MemberCustomFieldValue> customValues = (Collection<MemberCustomFieldValue>) query.getCustomValues();
        for (final Iterator it = customValues.iterator(); it.hasNext();) {
            final MemberCustomFieldValue fieldValue = (MemberCustomFieldValue) it.next();
            if (StringUtils.isEmpty(fieldValue.getValue())) {
                it.remove();
            }
        }
        if (CollectionUtils.isEmpty(query.getGroupFilters()) && CollectionUtils.isEmpty(query.getGroups()) && query.getBroker() == null && CollectionUtils.isEmpty(customValues)) {
            throw new ValidationException("member.bulkActions.error.emptyQuery");
        }
        if (StringUtils.isEmpty(comments)) {
            throw new ValidationException("comments", "remark.comments", new RequiredError());
        }
    }
}
