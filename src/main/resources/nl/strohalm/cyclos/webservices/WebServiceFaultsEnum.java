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
package nl.strohalm.cyclos.webservices;

/**
 * All faults throws by the web services operations
 * @author ameyer
 */
public enum WebServiceFaultsEnum implements WebServiceFault {

    APPLICATION_OFFLINE("application-offline"),

    SECURE_ACCESS_REQUIRED("secure-access-required"),

    UNAUTHORIZED_ACCESS("unauthorized-access"),

    INVALID_PARAMETERS("invalid-parameter"),

    QUERY_PARSE_ERROR("query-parse-error"),

    UNEXPECTED_ERROR("unexpected-error"),

    INVALID_CREDENTIALS("invalid-credentials"),

    BLOCKED_CREDENTIALS("blocked-credentials"),

    INVALID_CHANNEL("invalid-channel"),

    MEMBER_NOT_FOUND("member-not-found"),

    CURRENTLY_UNAVAILABLE("currently-unavailable"),

    INACTIVE_POS("inactive-pos");

    protected String code;

    private WebServiceFaultsEnum(final String code) {
        this.code = code;
    }

    @Override
    public String code() {
        return code;
    }
}
