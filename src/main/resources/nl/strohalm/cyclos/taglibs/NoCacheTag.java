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
package nl.strohalm.cyclos.taglibs;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import nl.strohalm.cyclos.utils.ResponseHelper;
import nl.strohalm.cyclos.utils.SpringHelper;

/**
 * Set the response headers, so the browser will not cache the request
 * @author luis
 */
public class NoCacheTag extends TagSupport {

    private static final long serialVersionUID = -3606077575698930609L;

    @Override
    public int doStartTag() throws JspException {

        final HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();
        final HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        final ResponseHelper responseHelper = SpringHelper.bean(request.getSession().getServletContext(), ResponseHelper.class);
        responseHelper.setNoCache(response);

        return EVAL_PAGE;
    }
}
