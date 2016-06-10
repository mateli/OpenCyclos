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
package nl.strohalm.cyclos.webservices.model;

import java.util.Calendar;

import javax.xml.bind.annotation.XmlType;

/**
 * Image data for web services
 * @author luis
 */
@XmlType(name = "image")
public class ImageVO extends EntityVO {
    private static final long serialVersionUID = 4397659519407226091L;
    private String            caption;
    private String            thumbnailUrl;
    private String            fullUrl;
    private Calendar          lastModified;

    public String getCaption() {
        return caption;
    }

    public String getFullUrl() {
        return fullUrl;
    }

    public Calendar getLastModified() {
        return lastModified;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setCaption(final String caption) {
        this.caption = caption;
    }

    public void setFullUrl(final String fullUrl) {
        this.fullUrl = fullUrl;
    }

    public void setLastModified(final Calendar lastModified) {
        this.lastModified = lastModified;
    }

    public void setThumbnailUrl(final String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    @Override
    public String toString() {
        return "ImageVO [caption=" + caption + ", thumbnailUrl=" + thumbnailUrl + ", fullUrl=" + fullUrl + ", lastModified=" + lastModified + "]";
    }
}
