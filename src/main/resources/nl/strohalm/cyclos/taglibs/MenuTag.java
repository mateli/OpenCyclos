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

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import nl.strohalm.cyclos.access.Module;
import nl.strohalm.cyclos.access.Permission;
import nl.strohalm.cyclos.services.permissions.PermissionService;
import nl.strohalm.cyclos.utils.MessageHelper;
import nl.strohalm.cyclos.utils.SpringHelper;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Taglib for menu rendering
 * @author luis
 */
public class MenuTag extends TagSupport {

    /**
     * A menu item bean, containing data to render a menu
     * @author luis
     */
    private static class Menu {
        private final String url;
        private final String label;
        private final String confirmationKey;
        private Menu         parent;
        private List<Menu>   children;

        public Menu(final String label, final String url, final String confirmationKey) {
            this.label = label;
            this.url = url;
            this.confirmationKey = confirmationKey;
        }

        public void addChild(final Menu child) {
            if (child != null) {
                child.parent = this;
                if (children == null) {
                    children = new ArrayList<Menu>();
                }
                children.add(child);
            }
        }

        public List<Menu> getChildren() {
            return children;
        }

        public String getConfirmationKey() {
            return confirmationKey;
        }

        public String getLabel() {
            return label;
        }

        public Menu getParent() {
            return parent;
        }

        public String getUrl() {
            return url;
        }

        public boolean hasContent() {
            return url != null || children != null;
        }

        public boolean isNested() {
            return parent != null;
        }
    }

    private static final long serialVersionUID = 1447606721801461983L;

    private String            confirmationKey;
    private String            key;
    private String            url;
    private Module            module;
    private Permission        permission;
    private String            label;
    private PermissionService permissionService;
    private Menu              menu;
    private MessageHelper     messageHelper;

    @Override
    public int doEndTag() throws JspException {
        try {
            // Rendering is done by the topmost tag. If this is nested, do nothing
            // Also, render something only if there is some content
            if (menu == null || menu.isNested() || !menu.hasContent()) {
                return EVAL_PAGE;
            }
            final JspWriter out = pageContext.getOut();

            // Render this parent menu
            final int index = index();
            final String divId = divId(menu, index);
            renderDiv(menu, index, divId);

            // Render each submenu
            final List<Menu> subMenus = menu.getChildren();
            if (CollectionUtils.isNotEmpty(subMenus)) {
                out.print("<ul id='subMenuContainer" + index + "' class='subMenuContainer' style='display:none'>");
                final int subMenuCount = subMenus.size();
                for (int i = 0; i < subMenuCount; i++) {
                    final Menu subMenu = subMenus.get(i);
                    final String subMenuId = divId(subMenu, i);
                    renderDiv(subMenu, i, subMenuId);
                    if (i == 0) {
                        out.println("<script>$('" + subMenuId + "').addClassName('firstSubMenu');</script>");
                    } else if (i == subMenuCount - 1) {
                        out.println("<script>$('" + subMenuId + "').addClassName('lastSubMenu');</script>");
                    }
                }
                out.println("</ul></li>");
            }
            out.println();
            out.println("<script>allMenus.push($('" + divId + "'));</script>");

            return EVAL_PAGE;
        } catch (final IOException e) {
            throw new JspException(e);
        } finally {
            release();
        }
    }

    @Override
    public int doStartTag() throws JspException {

        // If there is a permission check, verify it
        boolean granted = true;
        if (permission != null) {
            granted = permissionService.hasPermission(permission);
        } else if (module != null) {
            granted = permissionService.hasPermission(module);
        }
        // If the permission is not granted, don't eval the tag
        if (!granted) {
            return EVAL_PAGE;
        }

        // Build the menu bean
        if (StringUtils.isEmpty(label) && StringUtils.isNotEmpty(key)) {
            label = messageHelper.message(key);
        }
        menu = new Menu(label, url, confirmationKey);

        // Find the parent tag, if any
        final MenuTag parentTag = (MenuTag) findAncestorWithClass(this, MenuTag.class);
        if (parentTag != null) {
            // Nest the menu beans
            final Menu parentMenu = parentTag.getMenu();
            if (parentMenu != null) {
                parentMenu.addChild(menu);
            } else {
                // The parent tag does not have permission - ignore this menu too
                menu = null;
            }
        }

        return EVAL_BODY_INCLUDE;
    }

    public String getConfirmationKey() {
        return confirmationKey;
    }

    public String getKey() {
        return key;
    }

    public String getLabel() {
        return label;
    }

    public Menu getMenu() {
        return menu;
    }

    public Module getModule() {
        return module;
    }

    public Permission getPermission() {
        return permission;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public void release() {
        label = null;
        key = null;
        url = null;
        module = null;
        permission = null;
        confirmationKey = null;
        menu = null;
        super.release();
    }

    public void setConfirmationKey(final String confirmationKey) {
        this.confirmationKey = confirmationKey;
    }

    public void setKey(final String key) {
        this.key = key;
    }

    public void setLabel(final String displayName) {
        label = displayName;
    }

    public void setModule(final Module module) {
        this.module = module;
    }

    @Override
    public void setPageContext(final PageContext pageContext) {
        super.setPageContext(pageContext);
        permissionService = SpringHelper.bean(pageContext.getServletContext(), PermissionService.class);
        messageHelper = SpringHelper.bean(pageContext.getServletContext(), MessageHelper.class);
    }

    public void setPermission(final Permission permission) {
        this.permission = permission;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    private String divId(final Menu menu, final int index) {
        if (menu.isNested()) {
            return "submenu" + pageContext.getAttribute("menuIndex") + "." + index;
        } else {
            return "menu" + index;
        }
    }

    private String getClassName(final Menu menu) {
        return (menu.isNested() ? "subMenu" : "menu");
    }

    private int index() {
        int index = (Integer) ObjectUtils.defaultIfNull(pageContext.getAttribute("menuIndex"), -1);
        index++;
        pageContext.setAttribute("menuIndex", index);
        return index;
    }

    /**
     * Render the current div
     */
    private void renderDiv(final Menu menu, final int index, final String divId) throws IOException, JspException {
        final JspWriter out = pageContext.getOut();
        final String className = getClassName(menu);
        out.print("<li id='" + divId + "'class='" + className + "'");
        String url = menu.getUrl();
        if (StringUtils.isNotEmpty(url)) {
            url = StringEscapeUtils.escapeHtml(url);
            if (url.contains("?")) {
                url += "&";
            } else {
                url += "?";
            }
            url += "fromMenu=true";
            out.print(" linkURL=\"" + url + "\"");
            final String confirmationKey = menu.getConfirmationKey();
            if (StringUtils.isNotEmpty(confirmationKey)) {
                out.print(" confirmationMessage=\"" + StringEscapeUtils.escapeHtml(messageHelper.message(confirmationKey)) + "\"");
            }
        }
        out.print(">");
        out.print("<span class=\"" + className + "Bullet\"></span>");
        out.print("<span class=\"" + className + "Text\">");
        final String displayName = menu.getLabel();
        out.print(EscapeHTMLTag.escape(displayName, true));
        out.println("</span>");
        if (menu.getParent() == null) {
            out.println("<script>menuCount++;</script>");
        }
    }
}
