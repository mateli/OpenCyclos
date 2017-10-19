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

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.ads.Ad;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.utils.StringValuedEnum;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.sql.Blob;
import java.util.Calendar;

/**
 * Stores an image
 * @author luis
 */
@javax.persistence.Entity
@Table(name = "images")
@DiscriminatorColumn(name = "subclass", length = 3) // index="ix_subclass_name"
public abstract class Image extends Entity {

    public static enum Nature implements StringValuedEnum {
        SYSTEM("sys"), CUSTOM("ctm"), STYLE("sty"), MEMBER("mbr"), AD("ad");

        public static Nature getByOwner(final Entity owner) {
            if (owner instanceof Member) {
                return Image.Nature.MEMBER;
            } else {
                return Image.Nature.AD;
            }
        }

        private final String value;

        private Nature(final String value) {
            this.value = value;
        }

        public Class<? extends Image> getEntityType() {
            switch (this) {
                case AD:
                    return AdImage.class;
                case CUSTOM:
                    return CustomImage.class;
                case SYSTEM:
                    return SystemImage.class;
                case MEMBER:
                    return MemberImage.class;
                case STYLE:
                    return StyleImage.class;
            }
            return null;
        }

        public String getOwnerProperty() {
            switch (this) {
                case AD:
                    return "ad";
                case MEMBER:
                    return "member";
            }
            return null;
        }

        public Class<? extends Entity> getOwnerType() {
            switch (this) {
                case AD:
                    return Ad.class;
                case MEMBER:
                    return Member.class;
            }
            return null;
        }

        public String getValue() {
            return value;
        }
    }

    private static final long serialVersionUID = 3550019581431328393L;

    @Column(name = "content_type", nullable = false, length = 100)
    private String            contentType;

    @Lob
    @Column(name = "image", nullable = false, length = 16000000) // index="ix_subclass_name"
    private Blob              image;

    @Column(name = "image_size", nullable = false)
    private Integer           imageSize;

    @Column(name = "last_modified", nullable = false)
    private Calendar          lastModified;

    @Column(name = "name", nullable = false, length = 100)
    private String            name;

    @Lob
    @Column(name = "thumbnail", length = 16000000)
    private Blob              thumbnail;

    @Column(name = "thumbnail_size", nullable = false)
    private Integer           thumbnailSize;

    public String getContentType() {
        return contentType;
    }

    public Blob getImage() {
        return image;
    }

    public Integer getImageSize() {
        return imageSize;
    }

    public Calendar getLastModified() {
        return lastModified;
    }

    public String getName() {
        return name;
    }

    public abstract Nature getNature();

    /**
     * Returns the name without extension
     */
    public String getSimpleName() {
        final int pos = name == null ? -1 : name.lastIndexOf('.');
        if (pos < 0) {
            return name;
        }
        return name.substring(0, pos);
    }

    public Blob getThumbnail() {
        return thumbnail;
    }

    public Integer getThumbnailSize() {
        return thumbnailSize;
    }

    public void setContentType(final String contentType) {
        this.contentType = contentType;
    }

    public void setImage(final Blob image) {
        this.image = image;
    }

    public void setImageSize(final Integer imageSize) {
        this.imageSize = imageSize;
    }

    public void setLastModified(final Calendar lastModified) {
        this.lastModified = lastModified;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setThumbnail(final Blob thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setThumbnailSize(final Integer thumbnailSize) {
        this.thumbnailSize = thumbnailSize;
    }

    @Override
    public String toString() {
        return getId() + " - " + name;
    }
}
