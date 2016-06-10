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

import java.util.Collections;
import java.util.List;
import java.util.Map;

import nl.strohalm.cyclos.dao.BaseDAOImpl;
import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.ads.Ad;
import nl.strohalm.cyclos.entities.customization.images.Image;
import nl.strohalm.cyclos.entities.customization.images.Image.Nature;
import nl.strohalm.cyclos.entities.customization.images.OwneredImage;
import nl.strohalm.cyclos.entities.exceptions.DaoException;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.exceptions.UnexpectedEntityException;
import nl.strohalm.cyclos.entities.members.Member;

/**
 * Implementation class for images
 * @author rafael
 */
public class ImageDAOImpl extends BaseDAOImpl<Image> implements ImageDAO {

    public ImageDAOImpl() {
        super(Image.class);
    }

    public int countAdImages(final Ad ad) {
        return (Integer) uniqueResult("select count(*) from AdImage i where i.ad.id = :id", ad);
    }

    public int countMemberImages(final Member member) {
        return (Integer) uniqueResult("select count(*) from MemberImage i where i.member.id = :id", member);
    }

    public List<? extends Image> listByNature(final Nature nature) throws DaoException {
        checkNature(nature);
        return list("from " + nature.getEntityType().getName() + " i order by i.name", null);
    }

    public List<? extends OwneredImage> listByOwner(final Entity owner) {
        final Nature nature = Nature.getByOwner(owner);
        if (nature == null) {
            throw new UnexpectedEntityException();
        }
        final Map<String, ?> params = Collections.singletonMap("owner", owner);
        return list("from " + nature.getEntityType().getName() + " i where i." + nature.getOwnerProperty() + " = :owner order by i.order", params);
    }

    public Image load(final Nature nature, final String name) {
        checkNature(nature);
        final Map<String, ?> params = Collections.singletonMap("name", name);
        final Image image = uniqueResult("from " + nature.getEntityType().getName() + " i where i.name = :name", params);
        if (image == null) {
            throw new EntityNotFoundException(getEntityType());
        }
        return image;
    }

    private void checkNature(final Nature nature) {
        if (nature == null || nature == Nature.MEMBER || nature == Nature.AD) {
            throw new IllegalArgumentException("Invalid nature: " + nature);
        }
    }
}
