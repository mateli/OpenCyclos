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

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;

import nl.strohalm.cyclos.dao.customizations.ImageDAO;
import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.ads.Ad;
import nl.strohalm.cyclos.entities.customization.files.CustomizedFile;
import nl.strohalm.cyclos.entities.customization.files.CustomizedFileQuery;
import nl.strohalm.cyclos.entities.customization.images.AdImage;
import nl.strohalm.cyclos.entities.customization.images.CustomImage;
import nl.strohalm.cyclos.entities.customization.images.Image;
import nl.strohalm.cyclos.entities.customization.images.Image.Nature;
import nl.strohalm.cyclos.entities.customization.images.ImageCaptionDTO;
import nl.strohalm.cyclos.entities.customization.images.ImageDetailsDTO;
import nl.strohalm.cyclos.entities.customization.images.MemberImage;
import nl.strohalm.cyclos.entities.customization.images.OwneredImage;
import nl.strohalm.cyclos.entities.customization.images.StyleImage;
import nl.strohalm.cyclos.entities.customization.images.SystemImage;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.InitializingService;
import nl.strohalm.cyclos.services.customization.exceptions.ImageException;
import nl.strohalm.cyclos.services.fetch.FetchServiceLocal;
import nl.strohalm.cyclos.services.settings.SettingsServiceLocal;
import nl.strohalm.cyclos.utils.Dimensions;
import nl.strohalm.cyclos.utils.ImageHelper.ImageType;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.query.PageHelper;
import nl.strohalm.cyclos.webservices.model.ImageVO;
import nl.strohalm.cyclos.webservices.utils.ImageHelper;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.web.context.ServletContextAware;

/**
 * Image service implementation
 * @author luis
 */
public class ImageServiceImpl implements ImageServiceLocal, InitializingService, ServletContextAware {
    private static final String        SYSTEM_IMAGES_PATH        = "/WEB-INF/images/system";
    private static final String        STYLE_IMAGES_PATH         = "/WEB-INF/images/style";
    private static final int           MAX_IMAGE_SIZE_MULTIPLIER = 5;

    private FetchServiceLocal          fetchService;
    private ImageDAO                   imageDao;
    private SettingsServiceLocal       settingsService;
    private ServletContext             servletContext;
    private CustomizedFileServiceLocal customizedFileService;
    private ImageHelper                imageHelper;

    private static final Dimensions    SYSTEM_IMAGE_DIMENSIONS   = new Dimensions(3000, 3000);

    @Override
    public ImageVO getImageVO(final SystemImage systemImage) {
        return imageHelper.toVO(systemImage);
    }

    @Override
    public void initializeService() {
        if (servletContext == null) {
            return;
        }
        final File systemImagesDir = new File(servletContext.getRealPath(SYSTEM_IMAGES_PATH));

        // Import new system images
        importNewImages(systemImagesDir.listFiles(), Nature.SYSTEM);

        // Only when there are no customized CSS files, import the new style images
        CustomizedFileQuery query = new CustomizedFileQuery();
        query.setType(CustomizedFile.Type.STYLE);
        query.setPageForCount();
        boolean hasCssFiles = PageHelper.hasResults(customizedFileService.search(query));
        if (!hasCssFiles) {
            final File styleImagesDir = new File(servletContext.getRealPath(STYLE_IMAGES_PATH));
            importNewImages(styleImagesDir.listFiles(), Nature.STYLE);
        }
    }

    @Override
    public List<? extends Image> listByNature(final Nature nature) {
        return imageDao.listByNature(nature);
    }

    @Override
    public List<? extends OwneredImage> listByOwner(final Entity owner) {
        return imageDao.listByOwner(owner);
    }

    @Override
    public Image load(final Long id, final Relationship... fetch) {
        return imageDao.load(id, fetch);
    }

    /**
     * Returns the new system image files
     */
    public File[] newSystemImages() {
        final File dir = new File(servletContext.getRealPath(SYSTEM_IMAGES_PATH));
        return dir.listFiles();
    }

    @Override
    public Image reload(final Long id, final Relationship... fetch) throws EntityNotFoundException {
        return imageDao.reload(id, fetch);
    }

    @Override
    public int remove(final Long... ids) {
        return imageDao.delete(ids);
    }

    @Override
    public int removeStyleImage(final String imageName) {
        try {
            Image img = imageDao.load(Image.Nature.STYLE, imageName);
            return imageDao.delete(img.getId());
        } catch (final EntityNotFoundException e) {
            // returns silently
            return 0;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends OwneredImage> T save(final Entity owner, final String caption, final ImageType type, final String name, final InputStream in) {
        if (owner instanceof Ad) {
            return (T) doSaveAdImage((Ad) owner, caption, type, name, in);
        } else if (owner instanceof Member) {
            return (T) doSaveMemberImage((Member) owner, caption, type, name, in);
        } else {
            throw new IllegalArgumentException("Unsupported owner image: " + owner);
        }
    }

    @Override
    public Image save(final Nature nature, final ImageType type, final String name, final InputStream in) {
        Image image = null;
        switch (nature) {
            case SYSTEM:
                try {
                    image = load(Image.Nature.SYSTEM, name);
                } catch (final EntityNotFoundException e) {
                    image = new SystemImage();
                }
                break;
            case STYLE:
                try {
                    image = load(Image.Nature.STYLE, name);
                } catch (final EntityNotFoundException e) {
                    image = new StyleImage();
                }
                break;
            case CUSTOM:
                try {
                    image = load(Image.Nature.CUSTOM, name);
                } catch (final EntityNotFoundException e) {
                    image = new CustomImage();
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid nature: " + nature);
        }

        image = save(image, in, type.getContentType(), name);
        // We need this reload in order to be able to immediately read the blobs
        fetchService.reload(image);
        return image;
    }

    @Override
    public void saveDetails(final ImageDetailsDTO details) {
        final Entity owner = details.getImageOwner();
        int order = 0;
        for (final ImageCaptionDTO dto : details.getDetails()) {
            final OwneredImage image = imageDao.load(dto.getId());
            if (!image.getOwner().equals(owner)) {
                throw new PermissionDeniedException();
            }
            image.setOrder(order++);
            image.setCaption(dto.getCaption());
            imageDao.update(image);
        }
    }

    public void setCustomizedFileServiceLocal(final CustomizedFileServiceLocal customizedFileService) {
        this.customizedFileService = customizedFileService;
    }

    public void setFetchServiceLocal(final FetchServiceLocal fetchService) {
        this.fetchService = fetchService;
    }

    public void setImageDao(final ImageDAO imageDao) {
        this.imageDao = imageDao;
    }

    public void setImageHelper(final ImageHelper imageHelper) {
        this.imageHelper = imageHelper;
    }

    @Override
    public void setServletContext(final ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public void setSettingsServiceLocal(final SettingsServiceLocal settingsService) {
        this.settingsService = settingsService;
    }

    private AdImage doSaveAdImage(Ad ad, final String caption, final ImageType type, final String name, final InputStream in) {
        ad = fetchService.fetch(ad, RelationshipHelper.nested(Ad.Relationships.OWNER, Element.Relationships.GROUP));
        final int maxImages = ad.getOwner().getMemberGroup().getMemberSettings().getMaxAdImagesPerMember();
        final int count = imageDao.countAdImages(ad);
        if (count >= maxImages) {
            throw new PermissionDeniedException();
        }
        final AdImage image = new AdImage();
        image.setAd(ad);
        image.setCaption(caption);
        image.setOrder(count + 1);
        return save(image, in, type.getContentType(), name);
    }

    private MemberImage doSaveMemberImage(Member member, final String caption, final ImageType type, final String name, final InputStream in) {
        member = fetchService.fetch(member, Element.Relationships.GROUP);
        final int maxImages = member.getMemberGroup().getMemberSettings().getMaxImagesPerMember();
        final int count = imageDao.countMemberImages(member);
        if (count >= maxImages) {
            throw new PermissionDeniedException();
        }
        final MemberImage image = new MemberImage();
        image.setMember(member);
        image.setCaption(caption);
        image.setOrder(count + 1);
        return save(image, in, type.getContentType(), name);
    }

    /**
     * Generates a thumbnail of the original image
     * @return The generated temporary files
     */
    private Set<File> generateBlobs(final Image image, final InputStream in) throws IOException {
        final LocalSettings localSettings = settingsService.getLocalSettings();

        // Generate a temporary file
        final File originalFile = File.createTempFile("cyclos", "image");
        // Store the stream to the file
        IOUtils.copy(in, new FileOutputStream(originalFile));

        final ImageType type = ImageType.getByContentType(image.getContentType());
        File imageFile = null;
        File thumbnailFile = null;

        // When the runtime cannot handle the given image type, we'd better not try!!!
        if (type.isResizeSupported()) {
            // Get the limits
            final Dimensions maxImageDimensions = image instanceof SystemImage ? SYSTEM_IMAGE_DIMENSIONS : localSettings.getMaxImageDimensions();
            final Dimensions maxThumbnailDimensions = localSettings.getMaxThumbnailDimensions();

            // Read the image properties
            final BufferedImage bufImage = ImageIO.read(originalFile);
            final Dimensions originalDimensions = new Dimensions(bufImage.getWidth(), bufImage.getHeight());
            if (originalDimensions.isGreaterThan(new Dimensions(maxImageDimensions.getWidth() * MAX_IMAGE_SIZE_MULTIPLIER, maxImageDimensions.getHeight() * MAX_IMAGE_SIZE_MULTIPLIER))) {
                throw new ImageException(ImageException.INVALID_DIMENSION);
            }

            // Image (except style sheet) needs resizing
            if (image.getNature() != Image.Nature.STYLE) {
                if (originalDimensions.isGreaterThan(maxImageDimensions)) {
                    imageFile = nl.strohalm.cyclos.utils.ImageHelper.resizeGivenMaxDimensions(bufImage, type.getContentType(), maxImageDimensions);
                }
            }
            // Thumbnail needs resizing
            if (originalDimensions.isGreaterThan(maxThumbnailDimensions)) {
                thumbnailFile = nl.strohalm.cyclos.utils.ImageHelper.resizeGivenMaxDimensions(bufImage, type.getContentType(), maxThumbnailDimensions);
            }
        }

        // When no resize was done, use the same original file
        if (imageFile == null) {
            imageFile = originalFile;
        }
        if (thumbnailFile == null) {
            thumbnailFile = originalFile;
        }

        // Set the blobs
        final int imageSize = (int) imageFile.length();
        image.setImage(imageDao.createBlob(new FileInputStream(imageFile), imageSize));
        image.setImageSize(imageSize);
        final int thumbnailSize = (int) thumbnailFile.length();
        image.setThumbnail(imageDao.createBlob(new FileInputStream(thumbnailFile), thumbnailSize));
        image.setThumbnailSize(thumbnailSize);

        // Return the open files
        return new HashSet<File>(Arrays.asList(originalFile, imageFile, thumbnailFile));
    }

    /**
     * Imports new images into the database
     */
    private void importNewImages(final File[] newImages, final Nature nature) {
        for (final File file : newImages) {
            final String fileName = file.getName();
            ImageType type;
            try {
                type = ImageType.getByFileName(fileName);
            } catch (final Exception e) {
                // Ignore...probably not an image file
                continue;
            }

            try {
                // Check if the image is in the database
                load(nature, fileName);
            } catch (final EntityNotFoundException e) {
                // Not on DB - update it
                try {
                    save(nature, type, fileName, new FileInputStream(file));
                } catch (final FileNotFoundException e1) {
                    throw new IllegalStateException("File not found?!? " + e1);
                }
            }
        }
    }

    private Image load(final Nature nature, final String name) {
        // Nonsense for images with owner
        if (nature.getOwnerProperty() != null) {
            throw new EntityNotFoundException(Image.class);
        }
        return imageDao.load(nature, name);
    }

    private <I extends Image> I save(I image, final InputStream in, final String contentType, final String name) {
        image.setContentType(contentType);
        image.setName(name);
        image.setLastModified(Calendar.getInstance());
        Set<File> openFiles = null;
        try {
            try {
                openFiles = generateBlobs(image, in);
            } catch (final ImageException e) {
                throw e;
            } catch (final Throwable e) {
                throw new ImageException(e);
            }
            if (image.isTransient()) {
                image = imageDao.insert(image, true);
            } else {
                image = imageDao.update(image, true);
            }
            return image;
        } finally {
            if (CollectionUtils.isNotEmpty(openFiles)) {
                // Ensure open files will be removed after the transaction commits
                for (final File file : openFiles) {
                    file.delete();
                }
            }
        }
    }
}
