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

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import nl.strohalm.cyclos.access.AdminAdminPermission;
import nl.strohalm.cyclos.access.AdminMemberPermission;
import nl.strohalm.cyclos.access.AdminSystemPermission;
import nl.strohalm.cyclos.access.BasicPermission;
import nl.strohalm.cyclos.access.BrokerPermission;
import nl.strohalm.cyclos.access.MemberPermission;
import nl.strohalm.cyclos.access.Module;
import nl.strohalm.cyclos.access.OperatorPermission;

import org.springframework.web.context.ServletContextAware;

/**
 * Helper class for servlet context
 * @author luis
 */
public class ServletContextHelper implements ServletContextAware {
    private ServletContext servletContext;

    @Override
    public void setServletContext(final ServletContext servletContext) {
        this.servletContext = servletContext;

        // stores the Module enumeration type as a map (it can be accessed using Module.MODULE_ENUM_NAME)
        storeEnumMap(Module.class, Module.class.getSimpleName());

        // stores all the enumeration permissions
        storeEnumMap(BasicPermission.class, BasicPermission.class.getSimpleName());
        storeEnumMap(AdminSystemPermission.class, AdminSystemPermission.class.getSimpleName());
        storeEnumMap(AdminAdminPermission.class, AdminAdminPermission.class.getSimpleName());
        storeEnumMap(AdminMemberPermission.class, AdminMemberPermission.class.getSimpleName());
        storeEnumMap(MemberPermission.class, MemberPermission.class.getSimpleName());
        storeEnumMap(BrokerPermission.class, BrokerPermission.class.getSimpleName());
        storeEnumMap(OperatorPermission.class, OperatorPermission.class.getSimpleName());

    }

    public <E extends Enum<E>> void storeEnumMap(final Class<E> enumType, final String name) {
        final E[] values = EnumHelper.values(enumType);
        final Map<String, E> map = new LinkedHashMap<String, E>() {
            static final long serialVersionUID = 1L;

            @Override
            public E get(final Object key) {
                if (!containsKey(key)) {
                    throw new IllegalArgumentException("Key not found on map for name: '" + name + "' and key: '" + key + "'");
                }
                return super.get(key);
            }
        };

        for (final E e : values) {
            map.put(e.name(), e);
        }

        servletContext.setAttribute(name, map);
    }
}
