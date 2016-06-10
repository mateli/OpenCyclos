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
package nl.strohalm.cyclos.utils.access;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import nl.strohalm.cyclos.entities.access.User;
import nl.strohalm.cyclos.entities.accounts.AccountOwner;
import nl.strohalm.cyclos.entities.accounts.pos.Pos;
import nl.strohalm.cyclos.entities.groups.AdminGroup;
import nl.strohalm.cyclos.entities.groups.BrokerGroup;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.groups.OperatorGroup;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.Operator;
import nl.strohalm.cyclos.entities.services.ServiceClient;
import nl.strohalm.cyclos.services.access.exceptions.NotConnectedException;

import org.apache.commons.collections.MapUtils;

/**
 * Class that provides static access to the logged user, as well as custom attributes, using a ThreadLocal
 * @author luis
 */
public class LoggedUser {

    public static enum AccessType {
        SYSTEM, USER, WEB_SERVICE
    }

    private static final String                           ACCESS_TYPE_KEY    = "cyclos.loggedUser.accessType";
    private static final String                           ELEMENT_KEY        = "cyclos.loggedUser.element";
    private static final String                           GROUP_KEY          = "cyclos.loggedUser.group";
    private static final String                           USER_KEY           = "cyclos.loggedUser.user";
    private static final String                           REMOTE_ADDRESS_KEY = "cyclos.loggedUser.remoteAddress";
    private static final String                           SERVICE_CLIENT_KEY = "cyclos.loggedUser.serviceClient";
    private static final String                           POS_KEY            = "cyclos.loggedUser.pos";
    private static final ThreadLocal<Map<String, Object>> ATTRIBUTES         = new ThreadLocal<Map<String, Object>>();

    /**
     * Return the current account owner - system when an admin is logged, or the member
     */
    public static AccountOwner accountOwner() {
        final Element loggedElement = element();
        return loggedElement.getAccountOwner();
    }

    /**
     * Release resources. Should be called before the current thread dies
     */
    public static void cleanup() {
        final Map<String, Object> map = ATTRIBUTES.get();
        if (map != null) {
            map.clear();
        }
        ATTRIBUTES.remove();
    }

    /**
     * Returns the logged element
     */
    @SuppressWarnings("unchecked")
    public static <E extends Element> E element() {
        final Element element = getAttribute(ELEMENT_KEY);
        if (element == null) {
            throw new NotConnectedException();
        }
        return (E) element;
    }

    /**
     * Returns the access type for the current execution
     */
    public static AccessType getAccessType() {
        return getAttribute(ACCESS_TYPE_KEY);
    }

    /**
     * Returns the attribute for the given key
     */
    @SuppressWarnings("unchecked")
    public static <T> T getAttribute(final String key) {
        final Map<String, Object> map = ATTRIBUTES.get();
        if (map != null) {
            return (T) map.get(key);
        }
        return null;
    }

    /**
     * Returns all attributes. The returned map is just a defensive copy - changing it won't change the internal attributes
     */
    public static Map<String, Object> getAttributes() {
        final Map<String, Object> attributes = ATTRIBUTES.get();
        if (attributes == null) {
            return null;
        }
        // Return a defensive copy
        return new HashMap<String, Object>(attributes);
    }

    /**
     * Returns the logged user's group
     */
    @SuppressWarnings("unchecked")
    public static <G extends Group> G group() {
        final Group group = getAttribute(GROUP_KEY);
        if (group == null) {
            throw new NotConnectedException();
        }
        return (G) group;
    }

    /**
     * Returns whether there is an user in the current transaction
     */
    public static boolean hasUser() {
        return getAttribute(ELEMENT_KEY) != null;
    }

    /**
     * Initializes. Should be called before any other methods on the current thread
     */
    public static void init(final Map<String, Object> attributes) {
        if (MapUtils.isNotEmpty(attributes)) {
            ATTRIBUTES.set(attributes);
        }
    }

    /**
     * Initializes as a {@link Pos} web service
     */
    public static void init(final Pos pos, final String remoteAddress, final Map<String, Object> attributes) {
        final Member member = pos.getMemberPos().getMember();
        final Map<String, Object> map = initMap(AccessType.WEB_SERVICE, member, remoteAddress, attributes);
        map.put(POS_KEY, pos);
    }

    /**
     * Initializes as a {@link ServiceClient} web service
     */
    public static void init(final ServiceClient serviceClient, final String remoteAddress, final Map<String, Object> attributes) {
        final Member member = serviceClient.getMember();
        final Map<String, Object> map = initMap(AccessType.WEB_SERVICE, member, remoteAddress, attributes);
        map.put(SERVICE_CLIENT_KEY, serviceClient);
    }

    /**
     * Initializes. Should be called before any other methods on the current thread
     */
    public static void init(final User user) {
        init(user, null, null);
    }

    /**
     * Initializes. Should be called before any other methods on the current thread
     */
    public static void init(final User user, final String remoteAddress) {
        init(user, remoteAddress, null);
    }

    /**
     * Initializes. Should be called before any other methods on the current thread
     */
    public static void init(final User user, final String remoteAddress, final Map<String, Object> attributes) {
        initMap(AccessType.USER, user.getElement(), remoteAddress, attributes);
    }

    /**
     * Returns if the logged user is an administrator
     */
    public static boolean isAdministrator() {
        return getAttribute(GROUP_KEY) instanceof AdminGroup;
    }

    /**
     * Returns if the logged user is an broker. Note that a broker is also a member
     */
    public static boolean isBroker() {
        return getAttribute(GROUP_KEY) instanceof BrokerGroup;
    }

    /**
     * Returns if the logged user is a member. Note that this is also true if the member is a broker
     */
    public static boolean isMember() {
        return getAttribute(GROUP_KEY) instanceof MemberGroup;
    }

    /**
     * Return if the logged user is an operator
     */
    public static boolean isOperator() {
        return getAttribute(GROUP_KEY) instanceof OperatorGroup;
    }

    /**
     * Returns whether the current context is of a system task
     */
    public static boolean isSystem() {
        return getAccessType() == AccessType.SYSTEM;
    }

    /**
     * Returns whether the current context is either a system or an unrestricted service client (member is null)
     */
    public static boolean isSystemOrUnrestrictedClient() {
        return isSystem() || isUnrestrictedClient();
    }

    /**
     * An unrestricted client is a (WS) client working as any member in the system.<br>
     * But it only can do member's related tasks
     * @return
     */
    public static boolean isUnrestrictedClient() {
        return isWebService() && ATTRIBUTES.get().get(SERVICE_CLIENT_KEY) != null && !hasUser();
    }

    /**
     * Returns whether the current context is of a WS task
     */
    public static boolean isWebService() {
        return getAccessType() == AccessType.WEB_SERVICE;
    }

    /**
     * Returns the member if the current user is either a member (returning himself) or an operator (returning his member). Otherwise, returns null.
     */
    public static Member member() {
        final Object element = getAttribute(ELEMENT_KEY);
        if (element instanceof Member) {
            return (Member) element;
        } else if (element instanceof Operator) {
            return ((Operator) element).getMember();
        }
        return null;
    }

    /**
     * Returns the POS if the current transaction was started by a POS access, null otherwise
     */
    public static Pos pos() {
        return getAttribute(POS_KEY);
    }

    /**
     * Returns the remote address of the logged user
     */
    public static String remoteAddress() {
        return getAttribute(REMOTE_ADDRESS_KEY);
    }

    public static <T> T runAs(final User user, final Callable<T> callable) {
        return runAs(user, null, callable);
    }

    public static <T> T runAs(final User user, final String remoteAddress, final Callable<T> callable) {
        final Map<String, Object> previousAttributes = ATTRIBUTES.get();
        try {
            init(user, remoteAddress);
            return callable.call();
        } catch (final RuntimeException e) {
            throw e;
        } catch (final Exception e) {
            throw new RuntimeException(e);
        } finally {
            ATTRIBUTES.set(previousAttributes);
        }
    }

    public static <T> T runAsSystem(final Callable<T> callable) {
        Map<String, Object> previousAttributes = null;
        final boolean alreadyRunninAsSystem = isSystem();
        if (!alreadyRunninAsSystem) {
            previousAttributes = ATTRIBUTES.get();
        }
        try {
            if (!alreadyRunninAsSystem) {
                initAsSystem();
            }
            return callable.call();
        } catch (final RuntimeException e) {
            throw e;
        } catch (final Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (!alreadyRunninAsSystem) {
                ATTRIBUTES.set(previousAttributes);
            }
        }
    }

    /**
     * Returns the service client if the current transaction was started by a web service access, null otherwise
     */
    public static ServiceClient serviceClient() {
        return getAttribute(SERVICE_CLIENT_KEY);
    }

    /**
     * Sets an attribute for the given key
     */
    public static void setAttribute(final String key, final Object value) {
        final Map<String, Object> map = ATTRIBUTES.get();
        if (map != null) {
            map.put(key, value);
        }
    }

    /**
     * Returns the logged user
     */
    @SuppressWarnings("unchecked")
    public static <U extends User> U user() {
        final User user = getAttribute(USER_KEY);
        if (user == null) {
            throw new NotConnectedException();
        }
        return (U) user;
    }

    /**
     * Sets the current access as system
     */
    private static void initAsSystem() {
        initMap(AccessType.SYSTEM, null, null, null);
    }

    private static Map<String, Object> initMap(final AccessType accessType, final Element element, final String remoteAddress, final Map<String, Object> attributes) {
        final Map<String, Object> map = new HashMap<String, Object>();
        map.put(ACCESS_TYPE_KEY, accessType);
        if (element != null) {
            map.put(ELEMENT_KEY, element);
            map.put(USER_KEY, element.getUser());
            map.put(GROUP_KEY, element.getGroup());
        }
        map.put(REMOTE_ADDRESS_KEY, remoteAddress);
        if (attributes != null) {
            map.putAll(attributes);
        }
        ATTRIBUTES.set(map);
        return map;
    }
}
