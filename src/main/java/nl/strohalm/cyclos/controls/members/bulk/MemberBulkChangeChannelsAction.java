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
import java.util.HashSet;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.controls.members.SearchMembersAction;
import nl.strohalm.cyclos.entities.access.Channel;
import nl.strohalm.cyclos.entities.members.FullTextMemberQuery;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsChangeListener;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsEvent;
import nl.strohalm.cyclos.services.access.ChannelService;
import nl.strohalm.cyclos.services.elements.BulkMemberActionResultVO;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.SimpleCollectionBinder;

/**
 * Action to apply the bulk change group for members
 * @author luis
 */
public class MemberBulkChangeChannelsAction extends BaseFormAction implements LocalSettingsChangeListener {

    public static class ChangeChannelsBean {
        Collection<Channel> enableChannels;
        Collection<Channel> disableChannels;

        public Collection<Channel> getDisableChannels() {
            return disableChannels;
        }

        public Collection<Channel> getEnableChannels() {
            return enableChannels;
        }

        public void setDisableChannels(final Collection<Channel> disabledChannels) {
            disableChannels = disabledChannels;
        }

        public void setEnableChannels(final Collection<Channel> enabledChannels) {
            enableChannels = enabledChannels;
        }
    }

    static void prepare(final ActionContext context, final ChannelService channelService) {
        final HttpServletRequest request = context.getRequest();
        final Collection<Channel> channels = channelService.list();
        // The "web" channel can not be customized by the user, so it should not be sent to the JSP page
        final Channel webChannel = channelService.loadByInternalName(Channel.WEB);
        channels.remove(webChannel);
        request.setAttribute("channels", channels);
    }

    private DataBinder<FullTextMemberQuery> dataBinder;
    private BeanBinder<ChangeChannelsBean>  beanBinder;
    private ReadWriteLock                   lock = new ReentrantReadWriteLock(true);
    protected ChannelService                channelService;

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
    public final void setChannelService(final ChannelService channelService) {
        this.channelService = channelService;
    }

    @Override
    protected void formAction(final ActionContext context) throws Exception {
        final MemberBulkActionsForm form = context.getForm();

        // Read the user input
        final ChangeChannelsBean changeChanelsBean = getBeanBinder().readFromString(form.getChangeChannels());
        final FullTextMemberQuery query = getDataBinder().readFromString(form.getQuery());
        final BulkMemberActionResultVO result = elementService.bulkChangeMemberChannels(query, changeChanelsBean.enableChannels, changeChanelsBean.disableChannels);
        if (result.getChanged() > 0 && result.getUnchanged() > 0) {
            context.sendMessage("member.bulkActions.channelsChanged", result.getChanged(), result.getUnchanged());
        } else if (result.getChanged() > 0) {
            context.sendMessage("member.bulkActions.channelsChangedForAll", result.getChanged());
        } else {
            context.sendMessage("member.bulkActions.channelsNotChanged", result.getUnchanged());
        }

        // Clear the change channels parameters
        form.getChangeChannels().clear();
    }

    @Override
    protected void prepareForm(final ActionContext context) throws Exception {
        prepare(context, channelService);
    }

    @Override
    protected void validateForm(final ActionContext context) {
        final MemberBulkActionsForm form = context.getForm();
        final ChangeChannelsBean changeChanelsBean = getBeanBinder().readFromString(form.getChangeChannels());
        final FullTextMemberQuery query = getDataBinder().readFromString(form.getQuery());
        elementService.validateBulkChangeChannels(query, changeChanelsBean.enableChannels, changeChanelsBean.disableChannels);
    }

    private BeanBinder<ChangeChannelsBean> getBeanBinder() {
        if (beanBinder != null) {
            return beanBinder;
        }

        final BeanBinder<ChangeChannelsBean> beanBinder = BeanBinder.instance(ChangeChannelsBean.class);
        beanBinder.registerBinder("enableChannels", SimpleCollectionBinder.instance(Channel.class, HashSet.class, "enableIds"));
        beanBinder.registerBinder("disableChannels", SimpleCollectionBinder.instance(Channel.class, HashSet.class, "disableIds"));

        return beanBinder;
    }

}
