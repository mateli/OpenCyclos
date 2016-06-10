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
package nl.strohalm.cyclos.access;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.utils.StringValuedEnum;

public enum ModuleType implements StringValuedEnum {
    BASIC(BasicPermission.class, "B", "basic", false),
    ADMIN_SYSTEM(AdminSystemPermission.class, "AS", "system", false),
    ADMIN_MEMBER(AdminMemberPermission.class, "AM", "adminMember", true),
    ADMIN_ADMIN(AdminAdminPermission.class, "AA", "adminAdmin", false),
    MEMBER(MemberPermission.class, "M", "member", true),
    BROKER(BrokerPermission.class, "BK", "broker", true),
    OPERATOR(OperatorPermission.class, "O", "operator", true);

    private static Map<Group.Nature, List<ModuleType>> BY_GROUP_NATURE;

    public static List<ModuleType> getModuleTypes(final Group.Nature groupNature) {
        if (BY_GROUP_NATURE == null) {
            final Map<Group.Nature, List<ModuleType>> byNature = new HashMap<Group.Nature, List<ModuleType>>();
            for (final Group.Nature grpNature : Group.Nature.values()) {
                byNature.put(grpNature, new ArrayList<ModuleType>());
            }
            for (final ModuleType type : values()) {
                switch (type) {
                    case BASIC:
                        byNature.get(Group.Nature.ADMIN).add(type);
                        byNature.get(Group.Nature.BROKER).add(type);
                        byNature.get(Group.Nature.MEMBER).add(type);
                        byNature.get(Group.Nature.OPERATOR).add(type);
                        break;
                    case ADMIN_ADMIN:
                    case ADMIN_MEMBER:
                    case ADMIN_SYSTEM:
                        byNature.get(Group.Nature.ADMIN).add(type);
                        break;
                    case BROKER:
                        byNature.get(Group.Nature.BROKER).add(type);
                        break;
                    case MEMBER:
                        byNature.get(Group.Nature.BROKER).add(type);
                        byNature.get(Group.Nature.MEMBER).add(type);
                        break;
                    case OPERATOR:
                        byNature.get(Group.Nature.OPERATOR).add(type);
                        break;
                    default:
                        throw new IllegalArgumentException("Unsupported module type: " + type);
                }
            }
            BY_GROUP_NATURE = Collections.unmodifiableMap(byNature);
        }

        return BY_GROUP_NATURE.get(groupNature);
    }

    private final Class<? extends Permission> permissionClass;
    private final String                      value;
    private final String                      prefix;

    private final boolean                     relatedToMember;

    private ModuleType(final Class<? extends Permission> permissionClass, final String value, final String prefix, final boolean relatedToMember) {
        this.permissionClass = permissionClass;
        this.value = value;
        this.prefix = prefix;
        this.relatedToMember = relatedToMember;
    }

    public List<Module> getModules() {
        final List<Module> modules = new ArrayList<Module>();
        for (final Module module : Module.values()) {
            if (module.getType() == this) {
                modules.add(module);
            }
        }
        return modules;
    }

    public Class<? extends Permission> getPermissionClass() {
        return permissionClass;
    }

    public String getPrefix() {
        return prefix;
    }

    @Override
    public String getValue() {
        return value;
    }

    public boolean isRelatedToMember() {
        return relatedToMember;
    }
}
