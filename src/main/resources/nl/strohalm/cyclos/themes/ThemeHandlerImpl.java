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
package nl.strohalm.cyclos.themes;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletContext;

import nl.strohalm.cyclos.entities.customization.files.CustomizedFile;
import nl.strohalm.cyclos.entities.customization.files.CustomizedFile.Type;
import nl.strohalm.cyclos.entities.customization.images.Image;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.services.customization.CustomizedFileService;
import nl.strohalm.cyclos.services.customization.ImageService;
import nl.strohalm.cyclos.services.settings.SettingsService;
import nl.strohalm.cyclos.themes.Theme.Style;
import nl.strohalm.cyclos.themes.exceptions.ThemeException;
import nl.strohalm.cyclos.themes.exceptions.ThemeNotFoundException;
import nl.strohalm.cyclos.utils.CSSHelper;
import nl.strohalm.cyclos.utils.CustomizationHelper;
import nl.strohalm.cyclos.utils.ImageHelper.ImageType;
import nl.strohalm.cyclos.utils.WebImageHelper;
import nl.strohalm.cyclos.utils.conversion.CoercionHelper;
import nl.strohalm.cyclos.utils.validation.ValidationException;
import nl.strohalm.cyclos.utils.validation.Validator;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.ServletContextAware;

/**
 * Theme handler implementation
 * @author luis
 */
public class ThemeHandlerImpl extends BaseThemeHandler implements ServletContextAware {

    private static final String         THEME_PROPERTIES_ENTRY = "theme.properties";
    private static final String         THEMES_PATH            = "/WEB-INF/themes/";
    private static final FilenameFilter THEME_FILTER;
    private static final FilenameFilter STYLE_FILTER;
    private static final FileFilter     IMAGE_FILTER;
    static {
        THEME_FILTER = new SuffixFileFilter(".theme");
        STYLE_FILTER = new SuffixFileFilter(".css");
        IMAGE_FILTER = new FileFilter() {
            @Override
            public boolean accept(final File pathname) {
                // Check if is a recognized image
                try {
                    ImageType.getByContent(pathname);
                    return true;
                } catch (final Exception e) {
                    return false;
                }
            }

        };
    }

    /**
     * Return the properties for the given zip file
     */
    private static Properties properties(final ZipFile zipFile) throws IOException {
        final ZipEntry propertiesEntry = zipFile.getEntry(THEME_PROPERTIES_ENTRY);
        if (propertiesEntry == null) {
            throw new FileNotFoundException(THEME_PROPERTIES_ENTRY);
        }
        final Properties properties = new Properties();
        properties.load(zipFile.getInputStream(propertiesEntry));
        return properties;
    }

    private ServletContext        context;
    private ImageService          imageService;
    private CustomizedFileService customizedFileService;
    private CustomizationHelper   customizationHelper;
    private WebImageHelper        webImageHelper;
    private SettingsService       settingsService;

    public void setCustomizationHelper(final CustomizationHelper customizationHelper) {
        this.customizationHelper = customizationHelper;
    }

    public void setCustomizedFileService(final CustomizedFileService customizedFileService) {
        this.customizedFileService = customizedFileService;
    }

    public void setImageService(final ImageService imageService) {
        this.imageService = imageService;
    }

    @Override
    public void setServletContext(final ServletContext servletContext) {
        context = servletContext;
    }

    public void setSettingsService(final SettingsService settingsService) {
        this.settingsService = settingsService;
    }

    public void setWebImageHelper(final WebImageHelper webImageHelper) {
        this.webImageHelper = webImageHelper;
    }

    @Override
    protected void doExport(final Theme theme, final OutputStream out) {
        validateForExport(theme);
        final ZipOutputStream zipOut = new ZipOutputStream(out);

        final LocalSettings settings = settingsService.getLocalSettings();
        final String charset = settings.getCharset();

        try {
            // Retrieve which files will be exported
            final List<String> exportedFiles = new ArrayList<String>();
            final List<String> exportedImages = new ArrayList<String>();
            final Collection<Style> styles = theme.getStyles();
            if (styles != null) {
                for (final Style style : styles) {
                    exportedFiles.addAll(style.getFiles());
                }
            }

            // Store the properties file
            final Properties properties = asProperties(theme);
            zipOut.putNextEntry(new ZipEntry(THEME_PROPERTIES_ENTRY));
            properties.store(zipOut, "");
            zipOut.closeEntry();

            // Store each css file
            final List<File> styleFiles = customizationHelper.listByType(CustomizedFile.Type.STYLE);
            for (File file : styleFiles) {
                // We must use the customized one, not the original
                final String name = file.getName();
                if (!exportedFiles.contains(name)) {
                    // When not exporting the given file, continue
                    continue;
                }
                // Read the file contents
                file = customizationHelper.findFileOf(CustomizedFile.Type.STYLE, null, name);
                final String contents = FileUtils.readFileToString(file, charset);

                // Resolve the referenced images by reading all url(name) values from the css
                exportedImages.addAll(CSSHelper.resolveURLs(contents));

                // Write the contents to the zip file
                zipOut.putNextEntry(new ZipEntry("styles/" + name));
                IOUtils.copy(new StringReader(contents), zipOut, charset);
                zipOut.closeEntry();
            }

            // Store each image
            final File dir = webImageHelper.imagePath(Image.Nature.STYLE);
            final File[] imageFiles = dir.listFiles(IMAGE_FILTER);
            for (final File file : imageFiles) {
                final String name = file.getName();
                // Export referenced images only
                if (!exportedImages.contains(name)) {
                    continue;
                }
                zipOut.putNextEntry(new ZipEntry("images/" + name));
                IOUtils.copy(new FileInputStream(file), zipOut);
                zipOut.closeEntry();
            }

        } catch (final Exception e) {
            throw new ThemeException(e);
        } finally {
            // Close the stream
            IOUtils.closeQuietly(zipOut);
        }
    }

    @Override
    protected void doImportNew(final String fileName, final InputStream in) {
        final File file = realFile(fileName);
        try {
            final byte[] data = IOUtils.toByteArray(in);
            customizationHelper.updateFile(file, System.currentTimeMillis(), data);
        } catch (final Exception e) {
            throw new ThemeException();
        }
    }

    @Override
    protected List<Theme> doList() {
        final String path = context.getRealPath(THEMES_PATH);
        final File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        final File[] files = dir.listFiles(THEME_FILTER);
        final List<Theme> themes = new ArrayList<Theme>(files.length);
        for (final File file : files) {
            try {
                themes.add(read(file));
            } catch (final ThemeException e) {
                // Skip this theme
            }
        }
        Collections.sort(themes);
        return themes;
    }

    @Override
    protected void doRemove(final String fileName) {
        final File file = realFile(fileName);
        if (!file.exists()) {
            throw new ThemeNotFoundException(fileName);
        }
        customizationHelper.deleteFile(file);
    }

    @Override
    protected void doSelect(final String fileName) {
        ZipFile zipFile = null;
        final LocalSettings settings = settingsService.getLocalSettings();
        final String charset = settings.getCharset();
        try {
            final File file = realFile(fileName);
            if (!file.exists()) {
                throw new ThemeNotFoundException(fileName);
            }
            zipFile = new ZipFile(file);

            // Ensure the properties entry exists
            properties(zipFile);

            // Find all currently used images by style
            final Map<String, Collection<String>> imagesByFile = new HashMap<String, Collection<String>>();
            final File imageDir = webImageHelper.imagePath(Image.Nature.STYLE);
            final File[] cssFiles = imageDir.listFiles(STYLE_FILTER);
            for (final File css : cssFiles) {
                final String contents = FileUtils.readFileToString(css, charset);
                final List<String> urls = CSSHelper.resolveURLs(contents);
                imagesByFile.put(css.getName(), urls);
            }

            // Read the files
            final Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                final ZipEntry entry = entries.nextElement();
                // We will not handle directories
                if (entry.isDirectory()) {
                    continue;
                }
                final String name = entry.getName();
                final String entryFileName = new File(name).getName();
                if (name.startsWith("images/")) {
                    final ImageType type = ImageType.getByFileName(entryFileName);
                    // Save the image
                    final Image image = imageService.save(Image.Nature.STYLE, type, entryFileName, zipFile.getInputStream(entry));
                    // Update the physical image
                    webImageHelper.update(image);
                } else if (name.startsWith("styles/")) {
                    // Save the style sheet
                    CustomizedFile customizedFile = new CustomizedFile();
                    customizedFile.setName(entryFileName);
                    customizedFile.setType(CustomizedFile.Type.STYLE);
                    final String contents = IOUtils.toString(zipFile.getInputStream(entry), charset);
                    customizedFile.setContents(contents);
                    final File originalFile = customizationHelper.originalFileOf(Type.STYLE, entryFileName);
                    if (originalFile.exists()) {
                        customizedFile.setOriginalContents(FileUtils.readFileToString(originalFile, charset));
                    }
                    customizedFile = customizedFileService.saveForTheme(customizedFile);

                    // Update the physical file
                    final File physicalFile = customizationHelper.customizedFileOf(CustomizedFile.Type.STYLE, entryFileName);
                    customizationHelper.updateFile(physicalFile, customizedFile);

                    // Remove images that are no longer used
                    final List<String> newImages = CSSHelper.resolveURLs(contents);
                    final Collection<String> oldImages = imagesByFile.get(entryFileName);
                    if (CollectionUtils.isNotEmpty(oldImages)) {
                        for (final String imageName : oldImages) {
                            if (!newImages.contains(imageName)) {
                                // No longer used. Remove it
                                imageService.removeStyleImage(imageName);
                                // Remove the physical file
                                final File imageFile = new File(imageDir, imageName);
                                customizationHelper.deleteFile(imageFile);
                            }
                        }
                    }
                }
            }
        } catch (final ThemeException e) {
            throw e;
        } catch (final Exception e) {
            throw new ThemeException(e);
        } finally {
            try {
                zipFile.close();
            } catch (final Exception e) {
                // Ignore
            }
        }
    }

    @Override
    protected void doValidateForExport(final Theme theme) throws ValidationException {
        getExportValidator().validate(theme);
    }

    /**
     * Returns the theme as a properties object
     */
    private Properties asProperties(final Theme theme) {
        final Properties properties = new Properties();
        properties.setProperty("title", StringUtils.trimToEmpty(theme.getTitle()));
        properties.setProperty("author", StringUtils.trimToEmpty(theme.getAuthor()));
        properties.setProperty("version", StringUtils.trimToEmpty(theme.getVersion()));
        properties.setProperty("description", StringUtils.trimToEmpty(theme.getDescription()));
        final Collection<String> strings = CoercionHelper.coerceCollection(String.class, theme.getStyles());
        properties.setProperty("styles", StringUtils.join(strings.iterator(), ','));
        return properties;
    }

    /**
     * Reads properties as a Theme object
     */
    private Theme fromProperties(final Properties properties) {
        final Theme theme = new Theme();
        theme.setTitle(StringUtils.trimToNull(properties.getProperty("title")));
        theme.setAuthor(StringUtils.trimToNull(properties.getProperty("author")));
        theme.setVersion(StringUtils.trimToNull(properties.getProperty("version")));
        theme.setDescription(StringUtils.trimToNull(properties.getProperty("description")));
        final String styles = StringUtils.trimToNull(properties.getProperty("styles"));
        if (styles == null) {
            // None found - Assume all styles
            theme.setStyles(EnumSet.allOf(Style.class));
        } else {
            final String[] array = StringUtils.split(styles, ',');
            theme.setStyles(CoercionHelper.coerceCollection(Theme.Style.class, array));
        }
        return theme;
    }

    private Validator getExportValidator() {
        final Validator exportValidator = new Validator("theme");
        exportValidator.property("title").required();
        exportValidator.property("filename").required();
        exportValidator.property("styles").key("theme.stylesToExport").required();
        return exportValidator;
    }

    /**
     * Reads a theme from file
     */
    private Theme read(final File file) throws ThemeException {
        ZipFile zipFile = null;
        try {
            if (!file.exists()) {
                throw new ThemeNotFoundException(file.getName());
            }
            zipFile = new ZipFile(file);
            final Properties properties = properties(zipFile);
            final Theme theme = fromProperties(properties);
            theme.setFilename(file.getName());
            return theme;
        } catch (final Exception e) {
            throw new ThemeException(e);
        } finally {
            try {
                zipFile.close();
            } catch (final Exception e) {
                // Ignore
            }
        }
    }

    /**
     * Returns the real file for the theme
     */
    private File realFile(final String fileName) {
        final String path = context.getRealPath(THEMES_PATH);
        return new File(path, fileName);
    }
}
