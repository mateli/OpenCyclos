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
package nl.strohalm.cyclos.initializations;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;

import nl.strohalm.cyclos.entities.alerts.SystemAlert;
import nl.strohalm.cyclos.entities.customization.files.CustomizedFile;
import nl.strohalm.cyclos.entities.customization.files.CustomizedFileQuery;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.services.customization.CustomizedFileServiceLocal;
import nl.strohalm.cyclos.services.settings.SettingsServiceLocal;
import nl.strohalm.cyclos.utils.CustomizationHelper;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.ServletContextAware;

/**
 * Initializes the customized files
 * @author luis
 */
public class CustomizedFileInitialization implements LocalInitialization, ServletContextAware {

    private static final Log           LOG = LogFactory.getLog(CustomizedFileInitialization.class);
    private CustomizedFileServiceLocal customizedFileService;
    private CustomizationHelper        customizationHelper;
    private ServletContext             servletContext;
    private SettingsServiceLocal       settingsServiceLocal;

    @Override
    public String getName() {
        return "Customized files";
    }

    @Override
    public void initialize() {
        // First, clear the customized css files to ensure proper migration from previous versions when css files were not customized
        final File customizedStylesDir = new File(servletContext.getRealPath(customizationHelper.customizedPathFor(CustomizedFile.Type.STYLE)));
        for (final File css : customizedStylesDir.listFiles((FilenameFilter) new SuffixFileFilter(".css"))) {
            css.delete();
        }

        final LocalSettings localSettings = settingsServiceLocal.getLocalSettings();
        final CustomizedFileQuery query = new CustomizedFileQuery();
        query.fetch(CustomizedFile.Relationships.GROUP);
        query.setAll(true);
        final List<CustomizedFile> files = customizedFileService.search(query);
        for (final CustomizedFile customizedFile : files) {
            final CustomizedFile.Type type = customizedFile.getType();
            final String name = customizedFile.getName();
            final File physicalFile = customizationHelper.customizedFileOf(customizedFile);
            final File originalFile = customizationHelper.originalFileOf(type, name);

            if (!originalFile.exists()) {
                // Probably an old file which is no longer relevant. Remove the customized file from DB
                customizedFileService.stopCustomizing(customizedFile);
                continue;
            }

            try {
                // No conflicts are checked for style sheet files
                if (type != CustomizedFile.Type.STYLE) {
                    final boolean wasConflict = customizedFile.isConflict();

                    // Check if the file contents has changed since the customization
                    String originalFileContents = null;
                    if (originalFile.exists()) {
                        originalFileContents = FileUtils.readFileToString(originalFile);
                        if (originalFileContents.length() == 0) {
                            originalFileContents = null;
                        }
                    }
                    // Check if the file is now on conflict (or the new contents has changed)
                    boolean contentsChanged;
                    boolean newContentsChanged;
                    if (type == CustomizedFile.Type.APPLICATION_PAGE) {
                        contentsChanged = !StringUtils.trimToEmpty(originalFileContents).equals(StringUtils.trimToEmpty(customizedFile.getOriginalContents())) && !StringUtils.trimToEmpty(originalFileContents).equals(StringUtils.trimToEmpty(customizedFile.getContents()));
                        newContentsChanged = contentsChanged && !StringUtils.trimToEmpty(originalFileContents).equals(StringUtils.trimToEmpty(customizedFile.getNewContents()));
                    } else {
                        contentsChanged = !StringUtils.trimToEmpty(originalFileContents).equals(StringUtils.trimToEmpty(customizedFile.getOriginalContents()));
                        newContentsChanged = !StringUtils.trimToEmpty(originalFileContents).equals(StringUtils.trimToEmpty(customizedFile.getNewContents()));
                    }

                    if (!wasConflict && contentsChanged) {
                        // Save the new contents, marking the file as conflicts
                        customizedFile.setNewContents(originalFileContents);
                        customizedFileService.save(customizedFile);

                        // Generate an alert if the file is customized for the whole system
                        if (customizedFile.getGroup() == null && customizedFile.getGroupFilter() == null) {
                            SystemAlert.Alerts alertType = null;
                            switch (type) {
                                case APPLICATION_PAGE:
                                    alertType = SystemAlert.Alerts.NEW_VERSION_OF_APPLICATION_PAGE;
                                    break;
                                case HELP:
                                    alertType = SystemAlert.Alerts.NEW_VERSION_OF_HELP_FILE;
                                    break;
                                case STATIC_FILE:
                                    alertType = SystemAlert.Alerts.NEW_VERSION_OF_STATIC_FILE;
                                    break;
                            }
                            customizedFileService.notifyNewVersion(alertType, customizedFile);
                        }
                    } else if (wasConflict && newContentsChanged) {
                        // The file has changed again. Update the new contents
                        customizedFile.setNewContents(originalFileContents);
                        customizedFileService.save(customizedFile);
                    }
                }

                // Check if we must update an style file
                final long lastModified = customizedFile.getLastModified() == null ? System.currentTimeMillis() : customizedFile.getLastModified().getTimeInMillis();
                if (!physicalFile.exists() || physicalFile.lastModified() != lastModified) {
                    physicalFile.getParentFile().mkdirs();
                    FileUtils.writeStringToFile(physicalFile, customizedFile.getContents(), localSettings.getCharset());
                    physicalFile.setLastModified(lastModified);
                }
            } catch (final IOException e) {
                LOG.warn("Error handling customized file: " + physicalFile.getAbsolutePath(), e);
            }
        }
        // We must copy all non-customized style sheets to the customized dir, so there will be no problems for locating images
        String virtualPath = customizationHelper.originalPathFor(CustomizedFile.Type.STYLE);
        final File originalDir = new File(servletContext.getRealPath(virtualPath));
        if (originalDir == null || originalDir.listFiles() == null) {
            // this happens in case something is wrong with the directory, and servletContext.getRealPath returns null
            LOG.error("Error copying any style sheet file due to path problems. Images may not be locatable now. Unresolvable path: " + virtualPath);
            return;
        }
        for (final File original : originalDir.listFiles()) {
            final File customized = new File(customizedStylesDir, original.getName());
            if (!customized.exists()) {
                try {
                    FileUtils.copyFile(original, customized);
                } catch (final IOException e) {
                    LOG.warn("Error copying style sheet file: " + customized.getAbsolutePath(), e);
                }
            }
        }
    }

    public void setCustomizationHelper(final CustomizationHelper customizationHelper) {
        this.customizationHelper = customizationHelper;
    }

    public void setCustomizedFileServiceLocal(final CustomizedFileServiceLocal customizedFileService) {
        this.customizedFileService = customizedFileService;
    }

    @Override
    public void setServletContext(final ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public void setSettingsServiceLocal(final SettingsServiceLocal settingsServiceLocal) {
        this.settingsServiceLocal = settingsServiceLocal;
    }
}
