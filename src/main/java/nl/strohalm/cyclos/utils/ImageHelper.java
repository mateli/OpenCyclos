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
package nl.strohalm.cyclos.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import nl.strohalm.cyclos.exceptions.ApplicationException;

import com.mullassery.imaging.Imaging;
import com.mullassery.imaging.ImagingFactory;
import com.mullassery.imaging.util.Util;

/**
 * Contains helper methods for image manipulation
 * @author luis
 */
public final class ImageHelper {

    /**
     * Contains the known image types
     * @author luis
     */
    public static enum ImageType {
        GIF(GIF_SIGNATURE, "image/gif"), ICO(null, "image/x-ico", "image/x-icon", "image/ico"), JPEG(JPEG_SIGNATURE, "image/jpeg", "image/pjpeg"), PNG(PNG_SIGNATURE, "image/png", "image/x-png");

        /**
         * Try to identify a file type given the first content bytes
         */
        public static ImageType getByContent(final byte[] firstBytes) {
            for (final ImageType type : values()) {
                final byte[] signature = type.getSignature();
                if (signature == null || firstBytes.length < signature.length) {
                    continue;
                }
                // The first bytes are the type signature
                boolean starts = true;
                for (int i = 0; i < signature.length; i++) {
                    if (signature[i] != firstBytes[i]) {
                        starts = false;
                        break;
                    }
                }
                if (starts) {
                    return type;
                }
            }
            throw new UnknownImageTypeException();
        }

        /**
         * Try to identify a file type by reading it's content
         */
        public static ImageType getByContent(final File file) throws IOException {
            final FileInputStream in = new FileInputStream(file);
            try {
                final byte[] buffer = new byte[10];
                in.read(buffer);
                return getByContent(buffer);
            } finally {
                in.close();
            }
        }

        /**
         * Try to identify a file type given a MIME content type
         */
        public static ImageType getByContentType(final String contentType) {
            for (final ImageType type : values()) {
                final List<String> types = Arrays.asList(type.contentTypes);
                if (types.contains(contentType)) {
                    return type;
                }
            }
            throw new UnknownImageTypeException();
        }

        /**
         * Try to identify a file type given a file name, using the extension
         */
        public static ImageType getByFileName(final String fileName) {
            String extension;
            final int sep = fileName.lastIndexOf('.');
            if (sep <= 0) {
                extension = "";
            } else {
                extension = fileName.substring(sep + 1);
            }
            extension = extension.toLowerCase();
            if (extension.equals("jpg") || extension.equals("jpeg")) {
                return JPEG;
            } else if (extension.equals("gif")) {
                return GIF;
            } else if (extension.equals("png")) {
                return PNG;
            } else if (extension.equals("ico")) {
                return ICO;
            } else if (extension.equals("png")) {
                return PNG;
            }
            throw new UnknownImageTypeException();
        }

        private final byte[]   signature;
        private final String[] contentTypes;

        private ImageType(final byte[] signature, final String... contentTypes) {
            this.signature = signature;
            this.contentTypes = contentTypes;
        }

        public String getContentType() {
            return contentTypes[0];
        }

        public String[] getContentTypes() {
            return contentTypes;
        }

        public byte[] getSignature() {
            return signature;
        }

        /**
         * Returns whether this image type can be resized by the application
         */
        public boolean isResizeSupported() {
            return this != ICO;
        }
    }

    /**
     * Exception thrown when trying to lookup an unknown image type
     * @author luis
     */
    public static class UnknownImageTypeException extends ApplicationException {
        private static final long serialVersionUID = 8673642585560912420L;
    }

    private static final byte[] GIF_SIGNATURE  = { (byte) 0x47, (byte) 0x49, (byte) 0x46 };
    private static final byte[] JPEG_SIGNATURE = { (byte) 0xFF, (byte) 0xD8, (byte) 0xFF };
    private static final byte[] PNG_SIGNATURE  = { (byte) 0x89, (byte) 0x50, (byte) 0x4e, (byte) 0x47, (byte) 0x0d, (byte) 0x0a, (byte) 0x1a, (byte) 0x0a };
    private static Imaging      imaging;

    /**
     * @see #resizeGivenMaxDimensions(BufferedImage, String, Dimensions)
     */
    public static File generateThumbnail(final BufferedImage image, final String contentType, final int maxWidth, final int maxHeight) throws IOException {
        return resizeGivenMaxDimensions(image, contentType, new Dimensions(maxWidth, maxHeight));
    }

    /**
     * Loads an image from an stream
     */
    public static BufferedImage load(final InputStream in) {
        return imaging().read(in);
    }

    /**
     * Resizes the given image to a maximum according to the given dimensions, returning the file where it is stored
     */
    public static File resizeGivenMaxDimensions(final BufferedImage image, String contentType, final Dimensions maxDimensions) throws IOException, FileNotFoundException {
        final Dimensions originalDimensions = new Dimensions(image.getWidth(), image.getHeight());
        if ((ImageHelper.ImageType.getByContentType(contentType) == ImageHelper.ImageType.GIF)) {
            contentType = "image/png";
        }

        // Resize the image and write it to disk
        final Dimensions newDimensions = originalDimensions.resizeKeepingRatio(maxDimensions);
        final BufferedImage thumbnail = imaging().resize(image, newDimensions.getWidth(), newDimensions.getHeight());
        final File thumbFile = File.createTempFile("cyclos", "image");
        Util.saveImage(imaging, thumbnail, ImageType.getByContentType(contentType).name(), thumbFile);
        return thumbFile;
    }

    /**
     * Returns the imaging instance
     */
    private synchronized static Imaging imaging() {
        if (imaging == null) {
            imaging = ImagingFactory.createImagingInstance();
        }
        return imaging;
    }
}
