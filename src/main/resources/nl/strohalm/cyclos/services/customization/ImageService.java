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
package nl.strohalm.cyclos.services.customization;

import java.io.InputStream;
import java.util.List;

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.customization.images.Image;
import nl.strohalm.cyclos.entities.customization.images.ImageDetailsDTO;
import nl.strohalm.cyclos.entities.customization.images.OwneredImage;
import nl.strohalm.cyclos.entities.customization.images.SystemImage;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.services.Service;
import nl.strohalm.cyclos.utils.ImageHelper.ImageType;
import nl.strohalm.cyclos.webservices.model.ImageVO;

/**
 * Service interface for customized images (system, advertisement, profile)
 * @author luis
 */
public interface ImageService extends Service {

    /**
     * Returns an ImageVO for the given image.
     */
    ImageVO getImageVO(SystemImage systemImage);

    /**
     * Lists the images of the given nature
     */
    List<? extends Image> listByNature(Image.Nature nature);

    /**
     * Lists the images belonging to the specified owner
     */
    List<? extends OwneredImage> listByOwner(Entity owner);

    /**
     * Loads the image by id with the specified fetch
     */
    Image load(Long id, Relationship... fetch);

    /**
     * Reloads the image from the database
     */
    Image reload(Long id, Relationship... fetch) throws EntityNotFoundException;

    /**
     * It removes: the specified AD or MEMBER images that belongs to a managed member<br>
     * or the images with nature: SYSTEM, CUSTOM or STYLE.
     */
    int remove(Long... ids);

    /**
     * Removed the specified style image by name (if the image doesn't exist it returns silently)
     * @param imageName
     */
    int removeStyleImage(String imageName);

    /**
     * Saves the specified ownered image
     * @param owner Member or Ad owner
     */
    <T extends OwneredImage> T save(final Entity owner, final String caption, final ImageType type, final String name, final InputStream in);

    /**
     * Saves an image.
     * @param nature it could be SYSTEM, CUSTOM or STYLE
     */
    Image save(Image.Nature nature, ImageType type, String name, InputStream in);

    /**
     * Saves the image details: order and captions
     */
    void saveDetails(ImageDetailsDTO details);
}
