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

import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.utils.DataObject;

/**
 * Contains parameters used on the log for REST web services
 * 
 * @author luis
 */
public class RestLogDTO extends DataObject {

    private static final long serialVersionUID = -2214586462935167221L;
    private String            remoteAddress;
    private Member            member;
    private String            method;
    private String            uri;
    private String            queryString;
    private String            requestBody;
    private Throwable         error;

    public Throwable getError() {
        return error;
    }

    public Member getMember() {
        return member;
    }

    public String getMethod() {
        return method;
    }

    public String getQueryString() {
        return queryString;
    }

    public String getRemoteAddress() {
        return remoteAddress;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public String getUri() {
        return uri;
    }

    public void setError(final Throwable error) {
        this.error = error;
    }

    public void setMember(final Member member) {
        this.member = member;
    }

    public void setMethod(final String method) {
        this.method = method;
    }

    public void setQueryString(final String queryString) {
        this.queryString = queryString;
    }

    public void setRemoteAddress(final String remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    public void setRequestBody(final String requestBody) {
        this.requestBody = requestBody;
    }

    public void setUri(final String uri) {
        this.uri = uri;
    }

}
