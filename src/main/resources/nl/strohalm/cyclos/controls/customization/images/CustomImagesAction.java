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

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.entities.customization.images.CustomImage;
import nl.strohalm.cyclos.entities.customization.images.Image;
import nl.strohalm.cyclos.services.customization.ImageService;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.ImageHelper.ImageType;
import nl.strohalm.cyclos.utils.WebImageHelper;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.struts.action.ActionForward;
import org.apache.struts.upload.FormFile;

/**
 * Action used to list the customized images
 * @author luis
 */
public class CustomImagesAction extends BaseFormAction {

    private ImageService   imageService;
    private WebImageHelper webImageHelper;

    public ImageService getImageService() {
        return imageService;
    }

    @Inject
    public void setImageService(final ImageService imageService) {
        this.imageService = imageService;
    }

    @Inject
    public void setWebImageHelper(final WebImageHelper webImageHelper) {
        this.webImageHelper = webImageHelper;
    }

    @Override
    protected ActionForward handleSubmit(final ActionContext context) throws Exception {
        final CustomImagesForm form = context.getForm();
        Image.Nature nature = null;
        FormFile upload = null;
        try {
            try {
                nature = Image.Nature.valueOf(form.getNature());
            } catch (final Exception e) {
                throw new ValidationException();
            }
            if (nature != Image.Nature.CUSTOM && nature != Image.Nature.STYLE) {
                throw new ValidationException();
            }

            upload = form.getUpload();
            final ImageType type = ImageType.getByContentType(upload.getContentType());
            final Image image = imageService.save(nature, type, upload.getFileName(), upload.getInputStream());

            // Update the physical file
            webImageHelper.update(image);

            context.sendMessage("customImage.uploaded");
        } finally {
            try {
                upload.destroy();
            } catch (final Exception e) {
                // Ignore
            }
        }
        return ActionHelper.redirectWithParam(context.getRequest(), context.getSuccessForward(), "nature", nature);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void prepareForm(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();
        final CustomImagesForm form = context.getForm();
        Image.Nature nature = null;
        try {
            nature = Image.Nature.valueOf(form.getNature());
        } catch (final Exception e) {
            throw new ValidationException();
        }
        final List<CustomImage> images = (List<CustomImage>) imageService.listByNature(nature);
        request.setAttribute("images", images);
        request.setAttribute("nature", nature.name());
    }
}
