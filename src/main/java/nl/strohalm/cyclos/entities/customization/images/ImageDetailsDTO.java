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
package nl.strohalm.cyclos.entities.customization.images;

import java.util.List;

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.utils.DataObject;
import nl.strohalm.cyclos.utils.EntityHelper;

/**
 * Contains data about image details
 * @author luis
 */
public class ImageDetailsDTO extends DataObject {
    private static final long     serialVersionUID = 2439841592862873950L;
    private Image.Nature          nature;
    private Entity                owner;
    private List<ImageCaptionDTO> details;

    public List<ImageCaptionDTO> getDetails() {
        return details;
    }

    public Entity getImageOwner() {
        return EntityHelper.reference(nature.getOwnerType(), owner.getId());
    }

    public Image.Nature getNature() {
        return nature;
    }

    public Entity getOwner() {
        return owner;
    }

    public void setDetails(final List<ImageCaptionDTO> details) {
        this.details = details;
    }

    public void setNature(final Image.Nature nature) {
        this.nature = nature;
    }

    public void setOwner(final Entity owner) {
        this.owner = owner;
    }
}
