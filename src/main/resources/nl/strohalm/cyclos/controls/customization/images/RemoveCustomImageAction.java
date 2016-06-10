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

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseAction;
import nl.strohalm.cyclos.entities.customization.images.Image;
import nl.strohalm.cyclos.entities.customization.images.Image.Nature;
import nl.strohalm.cyclos.services.customization.ImageService;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.StringHelper;
import nl.strohalm.cyclos.utils.WebImageHelper;

import org.apache.struts.action.ActionForward;

/**
 * Action used to remove a custom image
 * @author luis
 */
public class RemoveCustomImageAction extends BaseAction {

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
    protected ActionForward executeAction(final ActionContext context) throws Exception {
        final RemoveCustomImageForm form = context.getForm();
        final long id = form.getImageId();
        final Image image = imageService.load(id);
        imageService.remove(image.getId());
        context.sendMessage("customImage.removed");

        // Remove the local file if a custom or style image
        if (image.getNature() != Nature.SYSTEM) {
            webImageHelper.remove(image);
        }

        final HttpServletRequest request = context.getRequest();
        return ActionHelper.redirectWithParam(request, context.getSuccessForward(), "nature", StringHelper.removeMarkupTags(request.getParameter("nature")));
    }
}
