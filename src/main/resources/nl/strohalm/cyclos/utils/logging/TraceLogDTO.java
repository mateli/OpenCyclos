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
package nl.strohalm.cyclos.utils.logging;

import java.util.Map;

import nl.strohalm.cyclos.entities.access.User;
import nl.strohalm.cyclos.utils.DataObject;

/**
 * Contains parameters used on the trace log
 * 
 * @author luis
 */
public class TraceLogDTO extends DataObject {

    private static final long     serialVersionUID = 469154058161370035L;
    private String                remoteAddress;
    private User                  user;
    private String                sessionId;
    private String                requestMethod;
    private String                path;
    private Map<String, String[]> parameters;
    private Throwable             error;
    private boolean               hasDatabaseWrites;

    public Throwable getError() {
        return error;
    }

    public Map<String, String[]> getParameters() {
        return parameters;
    }

    public String getPath() {
        return path;
    }

    public String getRemoteAddress() {
        return remoteAddress;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public String getSessionId() {
        return sessionId;
    }

    public User getUser() {
        return user;
    }

    public boolean isHasDatabaseWrites() {
        return hasDatabaseWrites;
    }

    public void setError(final Throwable error) {
        this.error = error;
    }

    public void setHasDatabaseWrites(final boolean hasDatabaseWrites) {
        this.hasDatabaseWrites = hasDatabaseWrites;
    }

    public void setParameters(final Map<String, String[]> parameters) {
        this.parameters = parameters;
    }

    public void setPath(final String path) {
        this.path = path;
    }

    public void setRemoteAddress(final String remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    public void setRequestMethod(final String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public void setSessionId(final String sessionId) {
        this.sessionId = sessionId;
    }

    public void setUser(final User user) {
        this.user = user;
    }

}
