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

import java.io.PrintWriter;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.customization.images.Image;
import nl.strohalm.cyclos.entities.customization.images.ImageCaptionDTO;
import nl.strohalm.cyclos.entities.customization.images.ImageDetailsDTO;
import nl.strohalm.cyclos.entities.customization.images.OwneredImage;
import nl.strohalm.cyclos.services.ads.AdService;
import nl.strohalm.cyclos.services.customization.ImageService;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.BeanCollectionBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.struts.action.ActionForward;

/**
 * Action used to edit image details
 * @author luis
 */
public class ImageDetailsAction extends BaseFormAction {
    private AdService                               adService;
    private ImageService                            imageService;
    private DataBinder<ImageDetailsDTO>             dataBinder;
    private DataBinder<Collection<ImageCaptionDTO>> detailCollectionBinder;

    public AdService getAdService() {
        return adService;
    }

    public DataBinder<Collection<ImageCaptionDTO>> getDetailCollectionBinder() {
        if (detailCollectionBinder == null) {
            detailCollectionBinder = BeanCollectionBinder.instance(getDetailBinder());
        }
        return detailCollectionBinder;
    }

    public ImageService getImageService() {
        return imageService;
    }

    @Inject
    public void setAdService(final AdService adService) {
        this.adService = adService;
    }

    @Inject
    public void setImageService(final ImageService imageService) {
        this.imageService = imageService;
    }

    @Override
    protected ActionForward handleSubmit(final ActionContext context) throws Exception {
        final ImageDetailsForm form = context.getForm();
        final ImageDetailsDTO details = getDataBinder().readFromString(form.getImages());
        if (details.getNature() == null || details.getOwner() == null) {
            throw new ValidationException();
        }

        boolean success;
        try {
            imageService.saveDetails(details);
            success = true;
        } catch (final Exception e) {
            success = false;
        }

        // Return a JavaScript message to the opener window
        final HttpServletResponse response = context.getResponse();
        response.setContentType("text/html");
        final PrintWriter out = response.getWriter();
        out.print("<html><script>");
        if (success) {
            out.print("window.opener.imageContainer.handleImageDetailsSuccess(");
            out.print(getDetailCollectionBinder().readAsString(details.getDetails()));
            out.print(");\n");
        } else {
            out.print("window.opener.imageContainer.handleImageDetailsError();\n");
            out.print("history.back();");
        }
        out.print("</script></html>");
        return null;
    }

    @Override
    protected void prepareForm(final ActionContext context) throws Exception {
        final ImageDetailsForm form = context.getForm();
        final ImageDetailsDTO details = getDataBinder().readFromString(form.getImages());
        Entity owner;
        try {
            owner = details.getImageOwner();
        } catch (final Exception e) {
            owner = null;
        }
        if (owner == null) {
            throw new ValidationException();
        }
        final List<? extends OwneredImage> images = imageService.listByOwner(owner);
        context.getRequest().setAttribute("images", images);
    }

    private DataBinder<ImageDetailsDTO> getDataBinder() {
        if (dataBinder == null) {
            final BeanBinder<ImageDetailsDTO> binder = BeanBinder.instance(ImageDetailsDTO.class);
            binder.registerBinder("nature", PropertyBinder.instance(Image.Nature.class, "nature"));
            binder.registerBinder("owner", PropertyBinder.instance(Entity.class, "owner"));
            binder.registerBinder("details", BeanCollectionBinder.instance(getDetailBinder(), "details"));

            dataBinder = binder;
        }
        return dataBinder;
    }

    private BeanBinder<ImageCaptionDTO> getDetailBinder() {
        final BeanBinder<ImageCaptionDTO> detailBinder = BeanBinder.instance(ImageCaptionDTO.class);
        detailBinder.registerBinder("id", PropertyBinder.instance(Long.class, "id"));
        detailBinder.registerBinder("caption", PropertyBinder.instance(String.class, "caption"));
        return detailBinder;
    }
}
