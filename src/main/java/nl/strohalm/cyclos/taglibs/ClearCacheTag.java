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

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import nl.strohalm.cyclos.services.fetch.FetchService;
import nl.strohalm.cyclos.utils.CacheCleaner;
import nl.strohalm.cyclos.utils.SpringHelper;

/**
 * A tag that invokes {@link CacheCleaner#clearCache()}
 * @author luis
 */
public class ClearCacheTag extends TagSupport {

    private static final long serialVersionUID = 2929354688050086186L;

    @Override
    public int doEndTag() throws JspException {
        final CacheCleaner cacheCleaner = resolveCacheCleaner();
        cacheCleaner.clearCache();
        return EVAL_PAGE;
    }

    private CacheCleaner resolveCacheCleaner() {
        CacheCleaner cacheCleaner = (CacheCleaner) pageContext.getAttribute("cacheCleaner", PageContext.REQUEST_SCOPE);
        if (cacheCleaner == null) {
            final FetchService fetchService = SpringHelper.bean(pageContext.getServletContext(), FetchService.class);
            cacheCleaner = new CacheCleaner(fetchService);
            pageContext.setAttribute("cacheCleaner", cacheCleaner, PageContext.REQUEST_SCOPE);
        }
        return cacheCleaner;
    }

}
