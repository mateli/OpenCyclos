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
package nl.strohalm.cyclos.controls.mobile.exceptions;

import nl.strohalm.cyclos.exceptions.ApplicationException;

/**
 * Represents a mobile operation exception, containing a title key and an error key
 * @author luis
 */
public class MobileException extends ApplicationException {

    private static final Object[] EMPTY_ARGS       = new Object[0];
    private static final long     serialVersionUID = 4189126083959548226L;

    private final Object[]        args;
    private final String          messageKey;
    private final String          titleKey;

    public MobileException() {
        this(true, null, null, (Object[]) null);
    }

    public MobileException(final boolean withTitle, final String titleKey, final String messageKey, final Object... args) {
        this.titleKey = titleKey;
        this.messageKey = messageKey;
        this.args = args == null ? EMPTY_ARGS : args;
    }

    public MobileException(final String messageKey) {
        this(true, null, messageKey, (Object[]) null);
    }

    public MobileException(final String messageKey, final Object... args) {
        this(true, null, messageKey, args);
    }

    public Object[] getArgs() {
        return args;
    }

    public String getMessageKey() {
        return messageKey;
    }

    public String getTitleKey() {
        return titleKey;
    }
}
