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
package nl.strohalm.cyclos.webservices.rest;

import javax.servlet.http.HttpServletResponse;

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.access.MemberUser;
import nl.strohalm.cyclos.entities.access.User;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.utils.Pair;
import nl.strohalm.cyclos.utils.StringHelper;
import nl.strohalm.cyclos.utils.validation.ValidationException;
import nl.strohalm.cyclos.webservices.model.ServerErrorVO;

import org.apache.commons.lang.StringUtils;

/**
 * Contains helper methods for REST services
 * @author luis
 */
public class RestHelper {

    public static final String NOT_FOUND_ERROR         = "NOT_FOUND";
    public static final String INVALID_ARGUMENT_ERROR  = "INVALID_ARGUMENT";
    public static final String PERMISSION_DENIED_ERROR = "PERMISSION_DENIED";
    public static final String INTERNAL_SERVER_ERROR   = "INTERNAL_SERVER_ERROR";

    /**
     * Returns the {@link ServerErrorVO} bean and the status code representing the given exception
     */
    public static Pair<ServerErrorVO, Integer> resolveError(final Throwable t) {
        if (t instanceof EntityNotFoundException) {
            return Pair.of(handleEntityNotFound((EntityNotFoundException) t), HttpServletResponse.SC_NOT_FOUND);
        } else if (t instanceof IllegalArgumentException) {
            return Pair.of(handleIllegalArgumentException((IllegalArgumentException) t), HttpServletResponse.SC_BAD_REQUEST);
        } else if (t instanceof ValidationException) {
            return Pair.of(handleValidationException((ValidationException) t), HttpServletResponse.SC_BAD_REQUEST);
        } else if (t instanceof PermissionDeniedException) {
            return Pair.of(handlePermissionDenied(), HttpServletResponse.SC_FORBIDDEN);
        } else {
            return Pair.of(handleUnknownException(), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private static ServerErrorVO handleEntityNotFound(final EntityNotFoundException ex) {
        final Class<? extends Entity> entityType = ex.getEntityType();
        String entityName;
        if (entityType == null) {
            entityName = "Entity";
        } else if (entityType.equals(Member.class) || entityType.equals(Element.class) || entityType.equals(User.class) || entityType.equals(MemberUser.class)) {
            entityName = "Member";
        } else {
            entityName = entityType.getSimpleName();
        }
        return new ServerErrorVO(NOT_FOUND_ERROR, StringHelper.upcase(entityName));
    }

    private static ServerErrorVO handleIllegalArgumentException(final IllegalArgumentException ex) {
        return new ServerErrorVO(INVALID_ARGUMENT_ERROR, StringUtils.trimToNull(ex.getMessage()));
    }

    private static ServerErrorVO handlePermissionDenied() {
        return new ServerErrorVO(PERMISSION_DENIED_ERROR, null);
    }

    private static ServerErrorVO handleUnknownException() {
        return new ServerErrorVO(INTERNAL_SERVER_ERROR, null);
    }

    private static ServerErrorVO handleValidationException(final ValidationException ex) {
        return new ServerErrorVO(INVALID_ARGUMENT_ERROR, null);
    }
}
