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
package nl.strohalm.cyclos.controls.customization.images;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.entities.customization.images.Image;
import nl.strohalm.cyclos.entities.customization.images.SystemImage;
import nl.strohalm.cyclos.services.customization.ImageService;
import nl.strohalm.cyclos.utils.ImageHelper.ImageType;
import nl.strohalm.cyclos.utils.WebImageHelper;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.upload.FormFile;

/**
 * Action for displaying and updating system images
 * @author luis
 */
public class SystemImagesAction extends BaseFormAction {

    /**
     * Holds data for displaying a system image, together with it's message label
     * @author luis
     */
    public static class SystemImageVO implements Comparable<SystemImageVO> {
        private final String      label;
        private final SystemImage image;

        public SystemImageVO(final String label, final SystemImage image) {
            this.label = label;
            this.image = image;
        }

        @Override
        public int compareTo(final SystemImageVO other) {
            if (other == null || other.label == null) {
                return 1;
            }
            return label.compareTo(other.label);
        }

        public SystemImage getImage() {
            return image;
        }

        public String getLabel() {
            return label;
        }
    }

    private ImageService   imageService;
    private WebImageHelper webImageHelper;

    @Inject
    public void setImageService(final ImageService imageService) {
        this.imageService = imageService;
    }

    @Inject
    public void setWebImageHelper(final WebImageHelper webImageHelper) {
        this.webImageHelper = webImageHelper;
    }

    @Override
    protected void formAction(final ActionContext context) throws Exception {
        final SystemImagesForm form = context.getForm();
        FormFile upload = null;
        try {
            upload = form.getUpload();
            final ImageType type = ImageType.getByContentType(upload.getContentType());
            final String name = StringUtils.trimToEmpty(form.getName());
            final Image image = imageService.save(Image.Nature.SYSTEM, type, name, upload.getInputStream());

            // Update context elements / cache
            webImageHelper.update(image);

            context.sendMessage("customImage.uploaded");
        } finally {
            try {
                upload.destroy();
            } catch (final Exception e) {
                // Ignore
            }
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void prepareForm(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();
        final SortedSet<SystemImageVO> images = new TreeSet<SystemImageVO>();
        final List<SystemImage> systemImages = (List<SystemImage>) imageService.listByNature(Image.Nature.SYSTEM);
        for (final SystemImage image : systemImages) {
            final String key = "customImage.system." + image.getSimpleName();
            images.add(new SystemImageVO(context.message(key), image));
        }
        request.setAttribute("images", images);
    }
}
