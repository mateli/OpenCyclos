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
package nl.strohalm.cyclos.controls.image;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseAjaxAction;
import nl.strohalm.cyclos.entities.ads.Ad;
import nl.strohalm.cyclos.entities.customization.images.AdImage;
import nl.strohalm.cyclos.entities.customization.images.Image;
import nl.strohalm.cyclos.entities.customization.images.MemberImage;
import nl.strohalm.cyclos.services.customization.ImageService;
import nl.strohalm.cyclos.servlets.ImageByIdServlet;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.customizedfile.CustomizedFileHandler;
import nl.strohalm.cyclos.utils.validation.ValidationException;

/**
 * Action to remove an image
 * @author luis
 */
public class RemoveImageAction extends BaseAjaxAction {

    private ImageService          imageService;
    private CustomizedFileHandler customizedFileHandler;

    @Inject
    public void setCustomizedFileHandler(final CustomizedFileHandler customizedFileHandler) {
        this.customizedFileHandler = customizedFileHandler;
    }

    @Inject
    public void setImageService(final ImageService imageService) {
        this.imageService = imageService;
    }

    @Override
    protected ContentType contentType() {
        return ContentType.TEXT;
    }

    @Override
    protected void renderContent(final ActionContext context) throws Exception {
        final RemoveImageForm form = context.getForm();
        final Long id = form.getId();
        if (id <= 0) {
            throw new ValidationException();
        }
        final Image image = imageService.load(id, MemberImage.Relationships.MEMBER, RelationshipHelper.nested(AdImage.Relationships.AD, Ad.Relationships.OWNER));

        // Call the correct method
        switch (image.getNature()) {
            case AD:
                imageService.remove(id);
                break;
            case MEMBER:
                imageService.remove(id);
                break;
            default:
                throw new ValidationException();
        }

        // Remove from cache (will do nothing if cache is not used)
        customizedFileHandler.delete(ImageByIdServlet.IMAGES_CACHE_PATH + "/" + id);
        customizedFileHandler.delete(ImageByIdServlet.THUMBNAILS_CACHE_PATH + "/" + id);
    }

}
