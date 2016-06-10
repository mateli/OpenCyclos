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

import nl.strohalm.cyclos.access.AdminMemberPermission;
import nl.strohalm.cyclos.access.AdminSystemPermission;
import nl.strohalm.cyclos.access.BrokerPermission;
import nl.strohalm.cyclos.access.MemberPermission;
import nl.strohalm.cyclos.access.OperatorPermission;
import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.EntityReference;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.ads.Ad;
import nl.strohalm.cyclos.entities.customization.images.AdImage;
import nl.strohalm.cyclos.entities.customization.images.Image;
import nl.strohalm.cyclos.entities.customization.images.ImageDetailsDTO;
import nl.strohalm.cyclos.entities.customization.images.MemberImage;
import nl.strohalm.cyclos.entities.customization.images.OwneredImage;
import nl.strohalm.cyclos.entities.customization.images.SystemImage;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.BaseServiceSecurity;
import nl.strohalm.cyclos.utils.EntityHelper;
import nl.strohalm.cyclos.utils.ImageHelper.ImageType;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.webservices.model.ImageVO;

/**
 * Security implementation for {@link ImageService}
 * 
 * @author ameyer
 */
public class ImageServiceSecurity extends BaseServiceSecurity implements ImageService {

    private ImageServiceLocal imageService;

    @Override
    public ImageVO getImageVO(final SystemImage systemImage) {
        // Nothing to check
        return imageService.getImageVO(systemImage);
    }

    @Override
    public List<? extends Image> listByNature(final Image.Nature nature) {
        // No check needed... this method is also invoked from rest service to get the splash images for a mobile device.

        return imageService.listByNature(nature);
    }

    @Override
    public List<? extends OwneredImage> listByOwner(final Entity owner) {
        permissionService.checkRelatesTo(getOwnerMember(owner));

        return imageService.listByOwner(owner);
    }

    @Override
    public Image load(final Long id, final Relationship... fetch) {
        // there are no permissions to check (e.g.: the images could be published to a web site)
        return imageService.load(id, fetch);
    }

    @Override
    public Image reload(final Long id, final Relationship... fetch) throws EntityNotFoundException {
        checkIsSystem();
        return imageService.reload(id, fetch);
    }

    @Override
    public int remove(final Long... ids) {
        if (ids == null) {
            return 0;
        }
        for (Long id : ids) {
            checkManagement(imageService.load(id, RelationshipHelper.nested(AdImage.Relationships.AD, Ad.Relationships.OWNER), MemberImage.Relationships.MEMBER));
        }

        return imageService.remove(ids);
    }

    @Override
    public int removeStyleImage(final String imageName) {
        permissionService.permission()
                .admin(AdminSystemPermission.THEMES_SELECT)
                .check();

        return imageService.removeStyleImage(imageName);
    }

    @Override
    public <T extends OwneredImage> T save(final Entity owner, final String caption, final ImageType type, final String name, final InputStream in) {
        checkManagement(owner instanceof Ad ? Image.Nature.AD : Image.Nature.MEMBER, getOwnerMember(owner));
        return imageService.save(owner, caption, type, name, in);
    }

    @Override
    public Image save(final Image.Nature nature, final ImageType type, final String name, final InputStream in) {
        // called from ThemeHandler too
        if (!permissionService.hasPermission(AdminSystemPermission.THEMES_SELECT)) {
            checkManagement(nature, null);
        }
        return imageService.save(nature, type, name, in);
    }

    @Override
    public void saveDetails(final ImageDetailsDTO details) {
        Entity owner = details.getOwner();
        if (owner instanceof EntityReference) {
            // we must create the right entity reference (Ad or Member) because the details.getOwner() is a reference to the abstract Entity type
            owner = EntityHelper.reference(details.getNature() == Image.Nature.AD ? Ad.class : Member.class, details.getOwner().getId());
        }

        checkManagement(details.getNature(), getOwnerMember(owner));

        imageService.saveDetails(details);
    }

    public void setImageServiceLocal(final ImageServiceLocal imageService) {
        this.imageService = imageService;
    }

    private void checkManagement(final Image image) {
        switch (image.getNature()) {
            case AD:
                checkManagement(image.getNature(), ((Ad) ((AdImage) image).getOwner()).getOwner());
                break;
            case MEMBER:
                checkManagement(image.getNature(), ((MemberImage) image).getMember());
                break;
            case SYSTEM:
            case CUSTOM:
            case STYLE:
                checkManagement(image.getNature(), null);
                break;
            default:
                throw new IllegalArgumentException("Unknown image's nature: " + image.getNature());
        }
    }

    private void checkManagement(final Image.Nature nature, final Member member) {
        switch (nature) {
            case AD:
                permissionService.permission(member)
                        .admin(AdminMemberPermission.ADS_MANAGE)
                        .broker(BrokerPermission.ADS_MANAGE)
                        .member(MemberPermission.ADS_PUBLISH)
                        .operator(OperatorPermission.ADS_PUBLISH)
                        .check();
                break;
            case MEMBER:
                permissionService.permission(member)
                        .admin(AdminMemberPermission.MEMBERS_CHANGE_PROFILE)
                        .broker(BrokerPermission.MEMBERS_CHANGE_PROFILE)
                        .member()
                        .check();
                break;
            case SYSTEM:
            case CUSTOM:
            case STYLE:
                permissionService.permission()
                        .admin(AdminSystemPermission.CUSTOM_IMAGES_MANAGE)
                        .check();
                break;
            default:
                throw new IllegalArgumentException("Unknown image's nature: " + nature);
        }
    }

    private Member getOwnerMember(final Entity owner) {
        if (owner instanceof Ad) {
            return fetchService.fetch((Ad) owner, Ad.Relationships.OWNER).getOwner();
        } else if (owner instanceof Member) {
            return (Member) owner;
        } else {
            throw new PermissionDeniedException();
        }
    }
}
