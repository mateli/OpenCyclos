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
package nl.strohalm.cyclos.dao.customizations;

import java.util.List;

import nl.strohalm.cyclos.dao.BaseDAO;
import nl.strohalm.cyclos.dao.DeletableDAO;
import nl.strohalm.cyclos.dao.InsertableDAO;
import nl.strohalm.cyclos.dao.UpdatableDAO;
import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.ads.Ad;
import nl.strohalm.cyclos.entities.customization.images.Image;
import nl.strohalm.cyclos.entities.customization.images.OwneredImage;
import nl.strohalm.cyclos.entities.exceptions.DaoException;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.members.Member;

/**
 * Interface for image DAO
 * @author rafael
 */
public interface ImageDAO extends BaseDAO<Image>, InsertableDAO<Image>, UpdatableDAO<Image>, DeletableDAO<Image> {

    /**
     * Returns the number of images in the given advertisement
     */
    int countAdImages(Ad ad);

    /**
     * Returns the number of images in the given member
     */
    int countMemberImages(Member member);

    /**
     * Lists images by <code>nature</code> and <code>name</code>, ordering by name. This method can only be used for the given natures: SYSTEM, CUSTOM
     * or STYLE. If other nature is passed in, an IllegalArgumentException will be thrown. If any exception is thrown by the underlying
     * implementation, it should be wrapped by a DaoException.
     * 
     * <p>
     * Note that any java.hql.Blob attribute should be loaded transparently. Implementors are free to provide such behavior in the prefered way.
     * Image's should be ready to use transparently, even if lazy loading of Blob's is used.
     * 
     * @param nature nature of the image
     * @throws DaoException
     */
    List<? extends Image> listByNature(Image.Nature nature) throws DaoException;

    /**
     * List images by <code>owner</code>, ordering by the image order
     */
    List<? extends OwneredImage> listByOwner(Entity owner);

    /**
     * Loads for a specific image based on its <code>nature</code> and <code>name</code>. This method can only be used for the given natures: SYSTEM,
     * CUSTOM or STYLE. If other nature is passed in, an IllegalArgumentException will be thrown. If no such Image can be found, throws an
     * EntityNotFoundException. If any exception is thrown by the underlying implementation, it should be wrapped by a DaoException.
     * 
     * <p>
     * Note that any java.hql.Blob attribute should be loaded transparently. Implementors are free to provide such behavior in the prefered way.
     * Image's should be ready to use transparently, even if lazy loading of Blob's is used.
     * 
     * @param nature nature of the image
     * @param name the name is not case sensitive
     * @throws EntityNotFoundException The specified image does not exists
     * @throws DaoException
     */
    Image load(Image.Nature nature, String name) throws EntityNotFoundException, DaoException;

}
