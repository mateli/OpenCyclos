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
package nl.strohalm.cyclos.utils.conversion;

import nl.strohalm.cyclos.utils.MessageResolver;

import org.apache.commons.lang.StringUtils;

/**
 * Looks up messages for converting
 * @author luis
 */
public class MessageConverter extends FormatOnlyConverter<Object> {

    private static final long     serialVersionUID = 8851692447710810803L;
    private final MessageResolver messageResolver;
    private final String          prefix;

    public MessageConverter(final MessageResolver messageResolver) {
        this(messageResolver, "");
    }

    public MessageConverter(final MessageResolver messageResolver, final String prefix) {
        this.messageResolver = messageResolver;
        this.prefix = prefix;
    }

    @Override
    public String toString(final Object object) {
        if (object == null) {
            return null;
        }
        final String key = object.toString();
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        return messageResolver.message(prefix + key);
    }
}
