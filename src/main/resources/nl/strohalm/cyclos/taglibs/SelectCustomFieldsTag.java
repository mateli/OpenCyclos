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

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import nl.strohalm.cyclos.entities.customization.fields.AdminCustomField;
import nl.strohalm.cyclos.entities.customization.fields.CustomField;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomField;
import nl.strohalm.cyclos.entities.groups.AdminGroup;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.utils.CustomFieldHelper;
import nl.strohalm.cyclos.utils.SpringHelper;

import org.apache.commons.lang.StringUtils;

/**
 * Custom tag used to choose custom fields according to a given criteria
 * @author luis
 */
public class SelectCustomFieldsTag extends TagSupport {
    private static final long           serialVersionUID = -5940994485068120343L;
    private List<? extends CustomField> allFields;
    private String                      var;
    private int                         scope;
    private Group                       group;

    private CustomFieldHelper           customFieldHelper;

    public SelectCustomFieldsTag() {
        release();
    }

    @SuppressWarnings("unchecked")
    @Override
    public int doEndTag() throws JspException {
        List<? extends CustomField> result = null;
        if (group instanceof MemberGroup) {
            result = customFieldHelper.onlyForGroup((List<MemberCustomField>) allFields, (MemberGroup) group);
        } else if (group instanceof AdminGroup) {
            result = customFieldHelper.onlyForGroup((List<AdminCustomField>) allFields, (AdminGroup) group);
        }
        pageContext.setAttribute(var, result, scope);
        return EVAL_PAGE;
    }

    @Override
    public void release() {
        super.release();
        allFields = null;
        var = null;
        group = null;
        scope = PageContext.PAGE_SCOPE;
    }

    public void setAllFields(final List<? extends CustomField> allFields) {
        this.allFields = allFields;
    }

    public void setGroup(final Group group) {
        this.group = group;
    }

    @Override
    public void setPageContext(final PageContext pageContext) {
        super.setPageContext(pageContext);
        customFieldHelper = SpringHelper.bean(pageContext.getServletContext(), CustomFieldHelper.class);
    }

    public void setScope(String scope) {
        scope = StringUtils.trimToNull(scope);
        if ("application".equalsIgnoreCase(scope)) {
            this.scope = PageContext.APPLICATION_SCOPE;
        } else if ("session".equalsIgnoreCase(scope)) {
            this.scope = PageContext.SESSION_SCOPE;
        } else if ("request".equalsIgnoreCase(scope)) {
            this.scope = PageContext.REQUEST_SCOPE;
        } else {
            this.scope = PageContext.PAGE_SCOPE;
        }
    }

    public void setVar(final String var) {
        this.var = var;
    }
}
