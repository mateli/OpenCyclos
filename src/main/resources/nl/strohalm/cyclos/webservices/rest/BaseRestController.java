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

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import nl.strohalm.cyclos.utils.Pair;
import nl.strohalm.cyclos.webservices.model.ServerErrorVO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Base class for REST controllers
 * @author luis
 */
public abstract class BaseRestController {

    private static final Log LOG = LogFactory.getLog(BaseRestController.class);

    /**
     * Handles {@link Exception}s
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ServerErrorVO handleUnknownException(final Exception ex, final HttpServletResponse response) throws IOException {
        Pair<ServerErrorVO, Integer> error = RestHelper.resolveError(ex);
        int errorCode = error.getSecond();
        if (errorCode == HttpServletResponse.SC_INTERNAL_SERVER_ERROR) {
            LOG.error("Error on REST call", ex);
        }
        response.setStatus(errorCode);
        return error.getFirst();
    }

}
