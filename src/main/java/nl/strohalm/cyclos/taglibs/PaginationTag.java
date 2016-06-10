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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import nl.strohalm.cyclos.utils.MessageHelper;
import nl.strohalm.cyclos.utils.SpringHelper;
import nl.strohalm.cyclos.utils.query.Page;

import org.apache.commons.lang.StringUtils;

/**
 * Custom tag to handle paging
 * @author luis
 */
public class PaginationTag extends TagSupport {

    private static final long serialVersionUID = 6787858661511733003L;
    private String            form;
    private Object            items;
    private String            onClickHandler;

    @Override
    public int doEndTag() throws JspException {
        final JspWriter out = pageContext.getOut();

        try {
            if (!(items instanceof Page<?>)) {
                out.println("<!-- pagination tag: items is not a page -->");
                return EVAL_PAGE;
            }
            out.println("<span style='vertical-align:middle'>");
            final Page<?> page = (Page<?>) items;
            final HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();

            final int currentPage = page.getCurrentPage() + 1;
            final int pageCount = page.getPageCount();

            if (!page.isEmpty()) {
                final MessageHelper messageHelper = SpringHelper.bean(pageContext.getServletContext(), MessageHelper.class);
                if (pageCount == 1) {
                    out.println(messageHelper.message("global.pagination.single.page", page.getTotalCount()));
                } else {
                    out.println(messageHelper.message("global.pagination.various.pages", page.getTotalCount()));
                    if (currentPage > 1) {
                        out.println(pageLink(currentPage - 1, "<img src=\"" + request.getContextPath() + "/pages/images/previous.gif\" style=\"valign:bottom\" border=\"0\" title=\"" + messageHelper.message("global.pagination.tooltip.previous") + "\">"));
                    }
                    out.println(StringUtils.join(resolvePages(currentPage, pageCount), ' '));
                    if (currentPage < pageCount) {
                        out.println(pageLink(currentPage + 1, "<img src=\"" + request.getContextPath() + "/pages/images/next.gif\" style=\"valign:bottom\" border=\"0\" title=\"" + messageHelper.message("global.pagination.tooltip.next") + "\">"));
                    }
                }
            }
            out.println("</span>");
            return EVAL_PAGE;
        } catch (final IOException e) {
            throw new JspException(e);
        } finally {
            release();
        }

    }

    public String getForm() {
        return form;
    }

    public Object getItems() {
        return items;
    }

    public String getOnClickHandler() {
        return onClickHandler;
    }

    @Override
    public void release() {
        super.release();
        items = null;
        form = null;
    }

    public void setForm(final String form) {
        this.form = form;
    }

    public void setItems(final Object items) {
        this.items = items;
    }

    public void setOnClickHandler(final String onClickHandler) {
        this.onClickHandler = onClickHandler;
    }

    /**
     * Builds a page link or just the current page
     */
    private String buildPageLink(final int currentPage, final int page) {
        if (page == currentPage) {
            return "<span class='currentPage'>" + currentPage + "</span>";
        }
        return pageLink(page, String.valueOf(page));
    }

    /**
     * Builds a page link with the given label
     */
    private String pageLink(final int page, final String label) {
        return "<a onClickHandler='" + StringUtils.trimToEmpty(onClickHandler) + "' jumpToPage='" + (page - 1) + "' " + (StringUtils.isNotEmpty(form) ? "form='" + form + "'" : "") + " class='paginationLink'>" + label + "</a>";
    }

    private String[] resolvePages(final int currentPage, final int pageCount) {
        final List<String> pages = new ArrayList<String>();
        if (currentPage >= 3) {
            pages.add(buildPageLink(currentPage, 1));
            if (currentPage >= 4) {
                pages.add("...");
            }
        }
        if (currentPage > 1) {
            pages.add(buildPageLink(currentPage, currentPage - 1));
        }
        pages.add(buildPageLink(currentPage, currentPage));
        if (currentPage < pageCount) {
            pages.add(buildPageLink(currentPage, currentPage + 1));
        }
        if (currentPage <= pageCount - 2) {
            if (currentPage <= pageCount - 1) {
                pages.add("...");
            }
            pages.add(buildPageLink(currentPage, pageCount));
        }
        return pages.toArray(new String[pages.size()]);
    }
}
