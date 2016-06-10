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
package nl.strohalm.cyclos.initializations;

import java.util.ArrayList;
import java.util.List;

import nl.strohalm.cyclos.entities.customization.images.Image;
import nl.strohalm.cyclos.services.customization.ImageService;
import nl.strohalm.cyclos.utils.WebImageHelper;

/**
 * Initializes the images
 * @author luis
 */
public class ImageInitialization implements LocalWebInitialization {

    private ImageService   imageService;
    private WebImageHelper webImageHelper;

    @Override
    public String getName() {
        return "Images";
    }

    @Override
    public void initialize() {
        updateImages();
    }

    public void setImageService(final ImageService imageService) {
        this.imageService = imageService;
    }

    public void setWebImageHelper(final WebImageHelper webImageHelper) {
        this.webImageHelper = webImageHelper;
    }

    /**
     * Updates the local images
     */
    private void updateImages() {
        // Initialize the custom and stylesheet images
        final List<Image> images = new ArrayList<Image>();
        images.addAll(imageService.listByNature(Image.Nature.SYSTEM));
        images.addAll(imageService.listByNature(Image.Nature.CUSTOM));
        images.addAll(imageService.listByNature(Image.Nature.STYLE));
        for (final Image image : images) {
            webImageHelper.update(imageService.reload(image.getId()));
        }
    }

}
