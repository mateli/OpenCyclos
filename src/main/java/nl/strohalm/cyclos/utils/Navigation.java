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

import java.io.Serializable;
import java.util.LinkedList;

import javax.servlet.http.HttpSession;

import nl.strohalm.cyclos.controls.ActionContext;

import org.apache.struts.action.ActionMapping;

/**
 * Handles page navigation
 * @author luis
 */
public class Navigation implements Serializable {

    public static final String NAVIGATION_KEY   = "navigation";
    private static final long  serialVersionUID = 7518139480217210726L;

    /**
     * Returns the {@link Navigation} instance for the given session
     */
    public static Navigation get(final HttpSession session) {
        if (session == null) {
            return null;
        }
        Navigation navigation = (Navigation) session.getAttribute(NAVIGATION_KEY);
        if (navigation == null) {
            navigation = new Navigation();
            session.setAttribute(NAVIGATION_KEY, navigation);
        }
        return navigation;
    }

    private final LinkedList<ActionMapping> paths = new LinkedList<ActionMapping>();
    private ActionMapping                   lastAction;

    private Navigation() {
    }

    /**
     * Removes the current path and the last path, returning the last
     */
    public String back() {
        // Remove the current path
        if (!paths.isEmpty()) {
            final ActionMapping last = paths.removeFirst();
            if (lastAction != null && lastAction.getPath().equals(last.getPath())) {
                setLastAction(null);
                return back();
            } else {
                return last.getPath();
            }
        }
        return null;
    }

    /**
     * Clears the path
     */
    public void clear() {
        paths.clear();
        lastAction = null;
    }

    /**
     * Check if the given path is contained on the navigation
     */
    public boolean contains(final String path) {
        return indexOf(path) >= 0;
    }

    /**
     * Returns the last path
     */
    public String getLast() {
        return paths.isEmpty() ? null : paths.getFirst().getPath();
    }

    public ActionMapping getLastAction() {
        return lastAction;
    }

    /**
     * Return the previous path
     */
    public String getPrevious() {
        return paths.size() < 2 ? null : paths.get(1).getPath();
    }

    /**
     * Find a path in the navigation
     */
    public int indexOf(final String path) {
        int index = -1;
        for (final ActionMapping current : paths) {
            index++;
            if (current.getPath().contains(path)) {
                return index;
            }
        }
        return -1;
    }

    /**
     * Removes the current path, returning it
     */
    public String removeCurrent() {
        // Remove the current path
        if (!paths.isEmpty()) {
            return paths.removeFirst().getPath();
        }
        return null;
    }

    public void setLastAction(final ActionMapping lastAction) {
        this.lastAction = lastAction;
    }

    public int size() {
        return paths.size();
    }

    /**
     * Store the current mapping
     */
    public void store(final ActionContext context) {
        final ActionMapping actionMapping = context.getActionMapping();

        // Check if we must clear the path
        if (RequestHelper.isFromMenu(context.getRequest())) {
            clear();
        }

        final String path = actionMapping.getPath();
        final ActionMapping last = paths.isEmpty() ? null : paths.getFirst();
        if (last == null) {
            paths.addFirst(actionMapping);
        } else if (!path.equals(last.getPath())) {
            // If the path is in the navigation, remove it
            final int position = indexOf(path) + 1;
            for (int i = 0; i < position; i++) {
                paths.removeFirst();
            }
            paths.addFirst(actionMapping);
        }
    }
}
