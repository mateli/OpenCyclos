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
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import nl.strohalm.cyclos.entities.customization.images.Image;
import nl.strohalm.cyclos.entities.customization.images.OwneredImage;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.services.settings.SettingsService;
import nl.strohalm.cyclos.utils.MessageHelper;
import nl.strohalm.cyclos.utils.SpringHelper;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Tag used to display and remove images
 * @author luis
 */
public class ImagesTag extends TagSupport {
    private static final long            serialVersionUID = -2838185736271052294L;

    private boolean                      editable;
    private List<? extends OwneredImage> images;
    private boolean                      imageOnly;
    private String                       varName;
    private String                       style;

    @Override
    public int doEndTag() throws JspException {
        release();
        return super.doEndTag();
    }

    @Override
    public int doStartTag() throws JspException {

        final HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        final StringBuilder sb = new StringBuilder();
        if (images == null) {
            images = Collections.emptyList();
        }

        final LocalSettings localSettings = getLocalSettings();

        // Get the nature and owner id of the images
        Image.Nature nature = null;
        Long ownerId = null;
        if (!images.isEmpty()) {
            final OwneredImage image = images.iterator().next();
            nature = image.getNature();
            ownerId = image.getOwner().getId();
        }

        final int adjust = imageOnly ? 0 : 8;
        final int width = localSettings.getMaxThumbnailWidth() + adjust;
        final String rnd = System.currentTimeMillis() + "_" + RandomStringUtils.random(4, true, false);
        final String id = "img_" + rnd;
        sb.append("<div id='").append(id).append("' style='width:").append(width).append("px;").append(style == null ? "" : style).append("' class='imageContainerDiv ").append(imageOnly ? "" : "imageContainer").append("'>");
        if (!imageOnly) {
            sb.append("<div class='thumbnailContainer'><table style='width:100%;border:none;padding:0px;margin:0px;'><tr><td style='text-align:center;vertical-align:middle;border:none;padding:0px;margin:0px;'>\n");
        }
        sb.append("<img class='thumbnail' id='thumbnail_").append(rnd).append("' src='").append(request.getContextPath()).append("/systemImage?image=noPicture&thumbnail=true'/>");
        if (!imageOnly) {
            sb.append("</td></tr></table></div>");
            if (images.size() > 1) {
                sb.append("<div class='imageControls' id='imageControls_").append(rnd).append("'><table cellpadding='0' cellspacing='0' border='0' width='100%'><tr>");
                sb.append("<td style='padding:0px' class='imageControlPrevious' id='previous_").append(rnd).append("' onclick='this.container.previousImage()' align='center' width='10'><img style='cursor:pointer;cursor:hand;' src=\"" + request.getContextPath() + "/pages/images/previous.gif\" border=\"0\"></td>");
                sb.append("<td style='padding:0px' class='imageIndex' id='index_").append(rnd).append("' nowrap='nowrap' align='center'>1 / ").append(images.size()).append("</td>");
                sb.append("<td style='padding:0px' class='imageControlNext' id='next_").append(rnd).append("' onclick='this.container.nextImage()' align='center' width=\'10\'><img style='cursor:pointer;cursor:hand;' src=\"" + request.getContextPath() + "/pages/images/next.gif\" border=\"0\"></td>");
                sb.append("</tr></table></div>\n");
            }
            if (editable && !images.isEmpty()) {
                final MessageHelper messageHelper = SpringHelper.bean(pageContext.getServletContext(), MessageHelper.class);
                sb.append("<div class='imageDetails' id='imageDetails_").append(rnd).append("' style='cursor:pointer;cursor:hand' onclick='this.container.details()'><a>");
                sb.append(messageHelper.message("image.details"));
                sb.append("</a></div>\n");
                sb.append("<div class='imageRemove' id='imageRemove_").append(rnd).append("' style='cursor:pointer;cursor:hand' onclick='this.container.removeImage()'><a>");
                sb.append(messageHelper.message("image.remove"));
                sb.append("</a></div>\n");
            }
        }
        sb.append("</div>\n");
        sb.append("<script>\n");
        final String varName = StringUtils.isEmpty(this.varName) ? id : this.varName;
        sb.append(String.format("var %s = new ImageContainer($('%s'), '%s', '%s');\n", varName, id, nature, ownerId));

        // Add the image descriptors
        for (final OwneredImage image : images) {
            final String caption = StringEscapeUtils.escapeHtml(StringUtils.trimToEmpty(image.getCaption()));
            final String url = StringEscapeUtils.escapeHtml(request.getContextPath() + "/thumbnail?id=" + image.getId());
            sb.append(varName).append(String.format(".imageDescriptors.push(new ImageDescriptor('%s', '%s', '%s'));\n", image.getId(), StringEscapeUtils.escapeJavaScript(caption), StringEscapeUtils.escapeJavaScript(url)));
        }

        // Set the references to other elements
        sb.append(varName).append(".appendElement('index', 'index_").append(rnd).append("');\n");
        sb.append(varName).append(".appendElement('thumbnail', 'thumbnail_").append(rnd).append("');\n");
        sb.append(varName).append(".appendElement('controls', 'imageControls_").append(rnd).append("');\n");
        sb.append(varName).append(".appendElement('previous', 'previous_").append(rnd).append("');\n");
        sb.append(varName).append(".appendElement('next', 'next_").append(rnd).append("');\n");
        sb.append(varName).append(".appendElement('imageRemove', 'imageRemove_").append(rnd).append("');\n");
        sb.append(varName).append(".appendElement('imageDetails', 'imageDetails_").append(rnd).append("');\n");

        sb.append(varName).append(".currentImage = 0;\n");
        sb.append(varName).append(".updateImage();\n");

        sb.append("</script>\n");
        try {
            pageContext.getOut().print(sb.toString());
        } catch (final IOException e) {
            throw new JspException(e);
        }
        return EVAL_PAGE;
    }

    public List<? extends OwneredImage> getImages() {
        return images;
    }

    public String getVarName() {
        return varName;
    }

    public boolean isEditable() {
        return editable;
    }

    public boolean isImageOnly() {
        return imageOnly;
    }

    @Override
    public void release() {
        super.release();
        images = null;
        varName = null;
        imageOnly = false;
        editable = false;
        style = null;
    }

    public void setEditable(final boolean editable) {
        this.editable = editable;
    }

    public void setImageOnly(final boolean imageOnly) {
        this.imageOnly = imageOnly;
    }

    public void setImages(final List<? extends OwneredImage> images) {
        this.images = images;
    }

    public void setStyle(final String style) {
        this.style = style;
    }

    public void setVarName(final String varName) {
        this.varName = varName;
    }

    private LocalSettings getLocalSettings() {
        final SettingsService settingsService = SpringHelper.bean(pageContext.getServletContext(), SettingsService.class);
        final LocalSettings settings = settingsService.getLocalSettings();
        return settings;
    }
}
