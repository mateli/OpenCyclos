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
package nl.strohalm.cyclos.setup;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

/**
 * Keeps track of version history
 * @author luis
 */
public class VersionHistory implements Serializable {
    private static final String VERSION_SEPARATOR = "---------------------------------------------------------------------------";
    private static final long   serialVersionUID  = -7557182450900181998L;
    private List<Version>       history;
    private Version             current;

    public synchronized void addVersion(final Version version) {
        if (version == null) {
            throw new NullPointerException();
        }
        if (history == null) {
            history = new ArrayList<Version>();
        } else if (history.contains(version)) {
            throw new IllegalArgumentException(String.format("Version %s already on history", version.getLabel()));
        }
        history.add(version);
    }

    public Version find(final String version) {
        final int index = indexOf(version);
        return index < 0 ? null : history.get(index);
    }

    public Version getCurrent() {
        return current;
    }

    public List<Version> getHistory() {
        return history;
    }

    public int indexOf(final String version) {
        for (int i = 0; i < history.size(); i++) {
            final Version current = history.get(i);
            if (current.sameAs(version)) {
                return i;
            }
        }
        return -1;
    }

    public void onFinish() {
        if (history != null) {
            Collections.reverse(history);
            current = history.get(history.size() - 1);
        }
    }

    public void setHistory(final List<Version> history) {
        this.history.clear();
        if (history != null) {
            for (final Version version : history) {
                addVersion(version);
            }
        }
    }

    @Override
    public String toString() {
        if (history == null || history.isEmpty()) {
            return "Empty history";
        }
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw);
        pw.println("Cyclos 3 changelog");
        pw.println("==================");
        pw.println();
        boolean firstTime = true;
        final ListIterator<Version> listIterator = history.listIterator(history.size());
        while (listIterator.hasPrevious()) {
            final Version version = listIterator.previous();
            if (firstTime) {
                firstTime = false;
            } else {
                pw.println(VERSION_SEPARATOR);
                pw.println();
            }
            pw.print(version);
        }
        return sw.toString();
    }

    public List<Version> upgrade(final String fromVersion) {
        if (current != null && current.sameAs(fromVersion)) {
            return Collections.emptyList();
        }
        final int index = indexOf(fromVersion);
        if (index < 0) {
            return null;
        }
        return new ArrayList<Version>(history.subList(index + 1, history.size()));
    }
}
