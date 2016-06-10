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
package nl.strohalm.cyclos.utils;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

/**
 * Implementation for message resolver
 * @author luis
 */
public class MessageResolverImpl implements MessageResolver {

    private MessageHelper messageHelper;

    /**
     * 
     * @see nl.strohalm.cyclos.utils.MessageResolver#addMessageResourcesLoadedListener(nl.strohalm.cyclos.utils.MessageResourcesLoadedListener)
     */
    @Override
    public void addMessageResourcesLoadedListener(final MessageResourcesLoadedListener listener) {
        messageHelper.addMessageResourcesLoadedListener(listener);
    }

    @Override
    public String message(final String key, final List<?> args) {
        return message(key, CollectionUtils.isEmpty(args) ? (Object[]) null : args.toArray());
    }

    @Override
    public String message(final String key, final Object... args) {
        return messageHelper.message(key, args);
    }

    public void setMessageHelper(final MessageHelper messageHelper) {
        this.messageHelper = messageHelper;
    }

}
