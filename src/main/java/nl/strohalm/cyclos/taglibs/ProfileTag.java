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

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.entities.settings.LocalSettings.MemberResultDisplay;
import nl.strohalm.cyclos.services.elements.ElementService;
import nl.strohalm.cyclos.services.permissions.PermissionService;
import nl.strohalm.cyclos.services.settings.SettingsService;
import nl.strohalm.cyclos.utils.ElementVO;
import nl.strohalm.cyclos.utils.SpringHelper;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.cxf.common.util.StringUtils;

/**
 * Profile tag. When the logged user has permission to view the related element, it shows the profile link. If not, it shows the name or user name
 * based on the local settings.
 * @author jcomas
 */
public class ProfileTag extends TagSupport {
    private static final long serialVersionUID = 1L;
    private Long              elementId        = null;
    private String            text             = null;
    private Integer           fieldLength      = TruncateTag.DEFAULT_LENGTH;
    private String            pattern          = null;
    private String            styleClass       = null;
    private boolean           onlyShowLabel    = false;

    private ElementService    elementService;

    private PermissionService permissionService;

    private SettingsService   settingsService;

    @Override
    public int doEndTag() throws JspException {
        try {
            final JspWriter out = pageContext.getOut();
            try {
                out.write(generateProfileField());
            } catch (final IOException e) {
                throw new JspException(e);
            }
        } finally {
            release();
        }
        return EVAL_PAGE;
    }

    public Long getElementId() {
        return elementId;
    }

    public Integer getFieldLength() {
        return fieldLength;
    }

    public String getPattern() {
        return pattern;
    }

    public String getStyleClass() {
        return styleClass;
    }

    public String getText() {
        return text;
    }

    public boolean isOnlyShowLabel() {
        return onlyShowLabel;
    }

    @Override
    public void release() {
        super.release();
        elementId = null;
        text = null;
        pattern = null;
        fieldLength = TruncateTag.DEFAULT_LENGTH;
        styleClass = null;
        onlyShowLabel = false;
    }

    public void setElementId(final Long elementId) {
        this.elementId = elementId;
    }

    public void setFieldLength(final Integer fieldLength) {
        this.fieldLength = fieldLength;
    }

    public void setOnlyShowLabel(final boolean onlyShowLabel) {
        this.onlyShowLabel = onlyShowLabel;
    }

    @Override
    public void setPageContext(final PageContext pageContext) {
        super.setPageContext(pageContext);
        elementService = SpringHelper.bean(pageContext.getServletContext(), ElementService.class);
        permissionService = SpringHelper.bean(pageContext.getServletContext(), PermissionService.class);
        settingsService = SpringHelper.bean(pageContext.getServletContext(), SettingsService.class);
    }

    public void setPattern(final String pattern) {
        this.pattern = pattern;
    }

    public void setStyleClass(final String styleClass) {
        this.styleClass = styleClass;
    }

    public void setText(final String text) {
        this.text = text;
    }

    private String generateProfileField() {
        if (elementId <= 0) {
            return "";
        }
        final ElementVO element = elementService.getElementVO(elementId);
        boolean canGoToProfile = false;
        final Object foundGroup = CollectionUtils.find(permissionService.getAllVisibleGroups(), new Predicate() {
            @Override
            public boolean evaluate(final Object group) {
                return ((Group) group).getId().equals(element.getGroupId());
            }
        });

        if (foundGroup != null) {
            canGoToProfile = true;
        }

        if (StringUtils.isEmpty(text)) {
            // The text will be the name or the user name based on the local settings.
            final LocalSettings localSettings = settingsService.getLocalSettings();
            final MemberResultDisplay memberResultDisplay = localSettings.getMemberResultDisplay();
            if (pattern != null) {
                text = pattern.replaceAll("username", element.getUsername()).replaceAll("name", element.getName());
            } else {
                if (memberResultDisplay == MemberResultDisplay.NAME) {
                    text = element.getName();
                } else if (memberResultDisplay == MemberResultDisplay.USERNAME) {
                    text = element.getUsername();
                }
            }
        }

        // truncate
        text = TruncateTag.truncate(text, fieldLength);

        String profile;

        if (canGoToProfile && !onlyShowLabel) {

            profile = "<a class=\"$linkClass\" $attribute=\"$id\">$text</a>";

            // $attribute, $class, $id and $text
            String linkClass = "";
            String attribute = "";
            switch (element.getNature()) {
                case ADMIN:
                    linkClass = "adminProfileLink";
                    attribute = "adminId";
                    break;
                case MEMBER:
                    linkClass = "profileLink";
                    attribute = "memberId";
                    break;
                case OPERATOR:
                    linkClass = "operatorProfileLink";
                    attribute = "operatorId";
                    break;
                default:
                    throw new IllegalArgumentException("Unexpected element identifier " + elementId);
            }

            if (!StringUtils.isEmpty(styleClass)) {
                linkClass += " " + styleClass;
            }

            profile = profile.replace("$linkClass", linkClass);
            profile = profile.replace("$attribute", attribute);
            profile = profile.replace("$id", elementId.toString());
            profile = profile.replace("$text", text);

        } else {
            profile = text;
        }
        return profile;
    }

}
