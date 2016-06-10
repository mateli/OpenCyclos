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
package nl.strohalm.cyclos.taglibs;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import nl.strohalm.cyclos.access.Permission;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.services.permissions.PermissionService;
import nl.strohalm.cyclos.services.settings.SettingsService;
import nl.strohalm.cyclos.utils.IntValuedEnum;
import nl.strohalm.cyclos.utils.SpringHelper;
import nl.strohalm.cyclos.utils.StringValuedEnum;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.utils.conversion.CoercionHelper;

import org.springframework.context.ApplicationContext;

/**
 * Contains taglib functions implementation
 * @author luis
 */
public class Functions {

    /**
     * Checks if a collection, array or map contains a value (or map key)
     */
    public static boolean contains(final Object collection, final Object value) {
        if (collection == null) {
            return false;
        }
        if (collection instanceof Collection<?>) {
            return ((Collection<?>) collection).contains(value);
        } else if (collection instanceof Map<?, ?>) {
            return ((Map<?, ?>) collection).containsKey(value);
        } else if (collection.getClass().isArray()) {
            return Arrays.asList((Object[]) collection).contains(value);
        }
        throw new IllegalArgumentException("Can't determine if " + collection + " contains " + value);
    }

    /**
     * Checks if the object arguments are equals
     */
    public static boolean equals(final Object object1, final Object object2) {
        if (object1 == null || object2 == null) {
            return false;
        } else {
            return object1.equals(object2);
        }
    }

    /**
     * Checks if the logged user has the permission for the given module / operation
     */
    public static boolean granted(final Permission permission) {
        try {
            final ApplicationContext context = LoggedUser.getAttribute("applicationContext");
            final PermissionService permissionService = SpringHelper.bean(context, PermissionService.class);
            return permissionService.hasPermission(permission);
        } catch (final Exception e) {
            return false;
        }
    }

    /**
     * Returns an enum name
     */
    public static String name(final Object object) {
        if (object == null) {
            return null;
        } else if (object instanceof Enum<?>) {
            return ((Enum<?>) object).name();
        } else {
            return object.toString();
        }
    }

    /**
     * Rounds the given number
     */
    public static BigDecimal round(final Object object) {
        try {
            final Number number = CoercionHelper.coerce(BigDecimal.class, object);
            final ApplicationContext context = LoggedUser.getAttribute("applicationContext");
            final SettingsService settingsService = SpringHelper.bean(context, SettingsService.class);
            final LocalSettings localSettings = settingsService.getLocalSettings();
            return localSettings.round(CoercionHelper.coerce(BigDecimal.class, number));
        } catch (final Exception e) {
            return null;
        }
    }

    /**
     * Returns an enum value
     */
    public static Object value(final Object object) {
        if (object == null) {
            return null;
        } else if (object instanceof StringValuedEnum) {
            return ((StringValuedEnum) object).getValue();
        } else if (object instanceof IntValuedEnum) {
            return ((IntValuedEnum) object).getValue();
        } else if (object instanceof Enum<?>) {
            return ((Enum<?>) object).name();
        } else {
            return object.toString();
        }
    }
}
