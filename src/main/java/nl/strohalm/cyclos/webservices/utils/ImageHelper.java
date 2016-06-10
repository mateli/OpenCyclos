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
package nl.strohalm.cyclos.webservices.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.entities.customization.images.Image;
import nl.strohalm.cyclos.entities.customization.images.OwneredImage;
import nl.strohalm.cyclos.entities.customization.images.SystemImage;
import nl.strohalm.cyclos.webservices.WebServiceContext;
import nl.strohalm.cyclos.webservices.model.ImageVO;

/**
 * Contains utility methods to handle custom fields
 * @author luis
 */
public class ImageHelper {

    public ImageVO toVO(final Image image, final String basePath) {
        ImageVO vo = new ImageVO();
        Long id = image.getId();
        vo.setId(id);
        vo.setLastModified(image.getLastModified());
        if (basePath != null) {
            vo.setThumbnailUrl(basePath + "/thumbnail?id=" + id);
            vo.setFullUrl(basePath + "/image?id=" + id);
        }
        return vo;
    }

    /**
     * Converts an image to vo
     */
    public ImageVO toVO(final OwneredImage image) {
        return toVO(image, null);
    }

    /**
     * Converts an image to vo, with path data
     */
    public ImageVO toVO(final OwneredImage image, final String basePath) {
        if (image == null) {
            return null;
        }

        final ImageVO vo = toVO((Image) image, basePath);
        vo.setCaption(image.getCaption());
        return vo;
    }

    public ImageVO toVO(final SystemImage image) {
        if (image == null) {
            return null;
        }
        String basePath = getBasePath();
        return toVO(image, basePath);
    }

    /**
     * Converts a collection of images into a collection of ImageVO
     */
    public List<ImageVO> toVOs(final Collection<? extends OwneredImage> images) {

        String basePath = getBasePath();
        final List<ImageVO> vos = new ArrayList<ImageVO>();
        for (final OwneredImage image : images) {
            vos.add(toVO(image, basePath));
        }
        return vos;
    }

    private String getBasePath() {
        final HttpServletRequest request = WebServiceContext.getRequest();
        String basePath = null;
        if (request != null) {
            basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        }
        return basePath;
    }

}
