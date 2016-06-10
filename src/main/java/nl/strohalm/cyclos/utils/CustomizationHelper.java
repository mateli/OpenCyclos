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

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

import javax.servlet.ServletContext;

import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.customization.binaryfiles.BinaryFile;
import nl.strohalm.cyclos.entities.customization.documents.DynamicDocument;
import nl.strohalm.cyclos.entities.customization.documents.StaticDocument;
import nl.strohalm.cyclos.entities.customization.files.CustomizedFile;
import nl.strohalm.cyclos.entities.customization.files.CustomizedFile.Type;
import nl.strohalm.cyclos.entities.customization.files.CustomizedFileQuery;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.GroupFilter;
import nl.strohalm.cyclos.entities.groups.OperatorGroup;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.services.groups.GroupService;
import nl.strohalm.cyclos.services.settings.SettingsService;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.utils.conversion.LocaleConverter;
import nl.strohalm.cyclos.utils.customizedfile.CustomizedFileHandler;

import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.ServletContextAware;

/**
 * Helper class for customizations
 * @author luis
 */
public class CustomizationHelper implements ServletContextAware {
    public class CustomizationData {
        private CustomizationLevel level;
        private Long               id;

        public CustomizationData(final CustomizationLevel level) {
            this.level = level;
        }

        public CustomizationData(final CustomizationLevel level, final Long id) {
            this(level);
            this.id = id;
        }

        public Long getId() {
            return id;
        }

        public CustomizationLevel getLevel() {
            return level;
        }
    }

    public static enum CustomizationLevel {
        GROUP, GROUP_FILTER, GLOBAL, NONE
    }

    public static List<String>        OPERATOR_SPECIFIC_FILES = Collections.unmodifiableList(Arrays.asList("posweb_header.jsp", "posweb_footer.jsp"));
    public static final String        APPLICATION_PAGES_PATH  = "/pages/";
    public static final String        STATIC_FILES_PATH       = "/pages/general/static_files/";
    public static final String        STYLE_PATH              = "/pages/styles/";
    public static final String        HELP_PATH               = "/pages/general/translation_files/helps/";
    public static final String        DOCUMENT_PATH           = "/pages/documents/";
    private static final List<String> EXCLUDED_DIRS           = Arrays.asList(new String[] { "/general/translation_files/", "/scripts/", "/styles/" });
    private static final List<String> LOGIN_CUSTOMIZED_FILES  = Arrays.asList(new String[] { "login.css", "login.jsp", "top.jsp" });

    private GroupService              groupService;
    private SettingsService           settingsService;
    private CustomizedFileHandler     customizedFileHandler;
    private ServletContext            context;

    /**
     * Returns the real file for the given customized file
     */
    public File customizedFileOf(final CustomizedFile file) {
        Group group = file.getGroup();
        final GroupFilter groupFilter = file.getGroupFilter();
        final Type type = file.getType();
        String customizedPath;
        if (group == null && groupFilter == null) {
            customizedPath = customizedPathFor(type);
        } else if (group != null) {
            group = loadGroup(group.getId());
            final boolean forceSameGroup = (group instanceof OperatorGroup && OPERATOR_SPECIFIC_FILES.contains(file.getName()));
            customizedPath = customizedPathFor(type, group, forceSameGroup);
        } else {
            customizedPath = customizedPathFor(type, groupFilter);
        }
        final File dir = new File(context.getRealPath(customizedPath));
        final String fileName = resolveName(file);
        return new File(dir, fileName);
    }

    /**
     * Returns the real file for the given customized file
     */
    public File customizedFileOf(final CustomizedFile.Type type, final String name) {
        final CustomizedFile file = new CustomizedFile();
        file.setType(type);
        file.setName(name);
        return customizedFileOf(file);
    }

    /**
     * Returns the root path for the customized file type
     */
    public String customizedPathFor(final CustomizedFile.Type type) {
        String path;
        switch (type) {
            case STATIC_FILE:
                path = STATIC_FILES_PATH + "customized/";
                break;
            case HELP:
                path = HELP_PATH;
                break;
            case STYLE:
                path = STYLE_PATH;
                break;
            case APPLICATION_PAGE:
                path = APPLICATION_PAGES_PATH;
                break;
            default:
                throw new IllegalArgumentException("Unknown file type: " + type);
        }
        return path;
    }

    /**
     * Returns the root path for the customized file type
     */
    public String customizedPathFor(final CustomizedFile.Type type, final Group group, final boolean forceSameGroup) {
        // Style sheets use the same path as the original file
        if (group != null && type != CustomizedFile.Type.STYLE) {
            final String pathPart = pathPart(group, forceSameGroup);
            return customizedPathFor(type) + pathPart + "/";
        }
        return customizedPathFor(type);
    }

    /**
     * Returns the root path for the customized file type
     */
    public String customizedPathFor(final CustomizedFile.Type type, final GroupFilter groupFilter) {
        // Style sheets use the same path as the original file
        if (groupFilter != null && type != CustomizedFile.Type.STYLE) {
            final String pathPart = pathPart(groupFilter);
            return customizedPathFor(type) + pathPart + "/";
        }
        return customizedPathFor(type);
    }

    /**
     * Deletes the given file on disk
     */
    public void deleteFile(final java.io.File file) {
        customizedFileHandler.delete(getRelativePath(file));
    }

    /**
     * Returns the real directory for documents
     */
    public File documentDir() {
        return new File(context.getRealPath(DOCUMENT_PATH));
    }

    /**
     * Returns the real document file for a given document
     */
    public File documentFile(final DynamicDocument document) {
        return new File(documentDir(), "document_" + document.getId() + ".jsp");
    }

    /**
     * Finds the customization level and the related id for the given parameters, trying files on the following order:
     * <ol>
     * <li>Customized for the given group (if any)</li>
     * <li>Customized for the group filters of the given group (if the group is the same as the logged member's group)</li>
     * <li>Customized for the given group filter (if any)</li>
     * <li>Customized globally</li>
     * <li>The original file (with no customizations)</li>
     * </ol>
     */
    public CustomizationData findCustomizationOf(final Type type, Group group, final GroupFilter groupFilter, final String name) {
        final CustomizedFile file = new CustomizedFile();
        file.setType(type);
        file.setName(name);

        if (group == null && LoggedUser.hasUser()) {
            group = LoggedUser.group();
        }

        // Try customized for group
        if (group != null) {
            group = loadGroup(group.getId());
            file.setGroup(group);
            // Try directly for group
            String customizedPath = customizedPathFor(type, group, true);
            String dir = context.getRealPath(customizedPath);
            String fileName = resolveName(file);
            File physicalFile = new File(dir, fileName);
            if (physicalFile.exists()) {
                if (type == Type.STYLE && group instanceof OperatorGroup) {
                    // fileName already contains correct memberGroup style sheet, but we need to get
                    // the memberGroup instance now for new CustomizationData() to work correctly
                    group = ((OperatorGroup) group).getMember().getGroup();
                }
                return new CustomizationData(CustomizationLevel.GROUP, group.getId());
            }

            // For operator group, try by member group
            try {
                if (group instanceof OperatorGroup) {
                    customizedPath = customizedPathFor(type, group, false);
                    dir = context.getRealPath(customizedPath);
                    fileName = resolveName(file);
                    physicalFile = new File(dir, fileName);
                    if (physicalFile.exists()) {
                        // Check if the file found uses the operator or member group
                        final String groupIdPart = "group_" + group.getId();
                        if (!fileName.contains(groupIdPart) && !dir.contains(groupIdPart)) {
                            // It is the member group
                            group = ((OperatorGroup) group).getMember().getGroup();
                        }
                        return new CustomizationData(CustomizationLevel.GROUP, group.getId());
                    }
                }
            } catch (final EntityNotFoundException e) {
                // Ignore
            }

            // Try by group filters of the given group (when is the same group as the logged user or no user logged in)
            if (!LoggedUser.hasUser() || LoggedUser.group().equals(group)) {
                file.setGroup(null);
                try {
                    Group groupForFilters = group;
                    if (group instanceof OperatorGroup) {
                        groupForFilters = ((OperatorGroup) group).getMember().getGroup();
                    }
                    final Collection<GroupFilter> groupFilters = loadGroup(groupForFilters.getId(), Group.Relationships.GROUP_FILTERS).getGroupFilters();
                    for (final GroupFilter current : groupFilters) {
                        file.setGroupFilter(current);
                        customizedPath = customizedPathFor(type, current);
                        dir = context.getRealPath(customizedPath);
                        fileName = resolveName(file);
                        physicalFile = new File(dir, fileName);
                        if (physicalFile.exists()) {
                            return new CustomizationData(CustomizationLevel.GROUP_FILTER, current.getId());
                        }
                    }
                } catch (final EntityNotFoundException e) {
                    // Ignore
                }
            }
        }
        // Try directly for group filter
        if (groupFilter != null) {
            file.setGroupFilter(groupFilter);
            final File groupFilterFile = customizedFileOf(file);
            if (groupFilterFile.exists()) {
                return new CustomizationData(CustomizationLevel.GROUP_FILTER, groupFilter.getId());
            }
        }

        // Try customized globally
        final String globallyCustomizedPath = customizedPathFor(type) + name;
        final File physicalFile = new File(context.getRealPath(globallyCustomizedPath));
        if (physicalFile.exists()) {
            return new CustomizationData(CustomizationLevel.GLOBAL);
        }

        // Return the original
        return new CustomizationData(CustomizationLevel.NONE);
    }

    public File findFileOf(final CustomizedFile.Type type, final Group group, final GroupFilter groupFilter, final String name) {
        final String path = findPathOf(type, group, groupFilter, name);
        return new File(context.getRealPath(path));
    }

    public File findFileOf(final CustomizedFile.Type type, final Group group, final String name) {
        return findFileOf(type, group, null, name);
    }

    public String findPathOf(final CustomizedFile.Type type, final Group group, final String name) {
        return findPathOf(type, group, null, name);
    }

    /**
     * Returns the path of a customized file.
     */
    public String findPathOf(final Type type, final Group group, final GroupFilter groupFilter, final String name) {
        final CustomizationData customization = findCustomizationOf(type, group, groupFilter, name);
        return pathOf(type, name, customization);
    }

    /**
     * Returns the real form file for a given document
     */
    public File formFile(final DynamicDocument document) {
        return new File(documentDir(), "form_" + document.getId() + ".jsp");
    }

    public List<File> getDirectoryContents(final String path) {
        final String rootPath = APPLICATION_PAGES_PATH + ("/".equals(path) ? "" : path);
        final File rootDir = new File(context.getRealPath(rootPath));
        final List<File> allDirectories = Arrays.asList(rootDir.listFiles((FileFilter) DirectoryFileFilter.DIRECTORY));
        final List<File> filteredDirectories = new ArrayList<File>();
        for (final File currentFile : allDirectories) {
            final String fullPath = path + currentFile.getName() + "/";
            if (!EXCLUDED_DIRS.contains(fullPath)) {
                filteredDirectories.add(currentFile);
            }
        }
        final List<File> files = Arrays.asList(rootDir.listFiles(CustomizedFile.Type.APPLICATION_PAGE.getFilter()));
        Collections.sort(filteredDirectories);
        Collections.sort(files);
        final List<File> filesAndDirs = new ArrayList<File>();
        filesAndDirs.addAll(filteredDirectories);
        filesAndDirs.addAll(files);
        return filesAndDirs;
    }

    /**
     * Returns the relative path of the given file according to the servlet context
     */
    public String getRelativePath(final java.io.File file) {
        final String absolutePath = file.getAbsolutePath();
        final String root = context.getRealPath("/");
        if (absolutePath.startsWith(root)) {
            return absolutePath.substring(root.length());
        }
        return absolutePath;
    }

    /**
     * Checks whether any of the the given files is related to the login page
     */
    public boolean isAnyFileRelatedToLoginPage(final Collection<CustomizedFile> files) {
        for (final CustomizedFile file : files) {
            if (isRelatedToLoginPage(file)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks whether the given file is related to the login page
     */
    public boolean isRelatedToLoginPage(final CustomizedFile file) {
        return LOGIN_CUSTOMIZED_FILES.contains(file.getName());
    }

    /**
     * Lists files for the customized file type
     */
    public List<File> listByType(final CustomizedFile.Type type) {
        final File dir = new File(context.getRealPath(originalPathFor(type)));
        return new ArrayList<File>(Arrays.asList(dir.listFiles(type.getFilter())));
    }

    /**
     * Returns a collection of file names of files that are not already customized
     */
    public List<String> onlyNotAlreadyCustomized(final CustomizedFile.Type type, final List<CustomizedFile> customizedFiles) {
        final List<String> notYetCustomized = new ArrayList<String>();
        final List<File> files = listByType(type);
        final CustomizedFileQuery query = new CustomizedFileQuery();
        query.setType(type);
        for (final File file : files) {
            boolean alreadyCustomized = false;
            for (final CustomizedFile customizedFile : customizedFiles) {
                if (customizedFile.getName().equals(file.getName())) {
                    alreadyCustomized = true;
                    break;
                }
            }
            if (!alreadyCustomized) {
                notYetCustomized.add(file.getName());
            }
        }
        Collections.sort(notYetCustomized);
        return notYetCustomized;
    }

    /**
     * Returns the real file for the given original file
     */
    public File originalFileOf(final CustomizedFile.Type type, final String name) {
        final File dir = new File(context.getRealPath(originalPathFor(type)));
        return new File(dir, name);
    }

    /**
     * Returns the root path for the customized file type
     */
    public String originalPathFor(final CustomizedFile.Type type) {
        String path;
        switch (type) {
            case STATIC_FILE:
                path = STATIC_FILES_PATH;
                break;
            case HELP:
                final String locale = LocaleConverter.instance().toString(settingsService.getLocalSettings().getLocale());
                path = HELP_PATH + locale + "/";
                break;
            case STYLE:
                path = STYLE_PATH + "original/";
                break;
            case APPLICATION_PAGE:
                path = APPLICATION_PAGES_PATH;
                break;
            default:
                throw new IllegalArgumentException("Unknown file type: " + type);
        }
        return path;
    }

    /**
     * Returns the path for the given customization
     */
    public String pathOf(final Type type, final String name, final CustomizationData customization) {
        final CustomizedFile file = new CustomizedFile();
        file.setType(type);
        file.setName(name);

        String path;
        switch (customization.getLevel()) {
            case NONE:
                path = originalPathFor(type);
                break;
            case GLOBAL:
                path = customizedPathFor(type);
                break;
            case GROUP:
                final Group group = EntityHelper.reference(Group.class, customization.getId());
                file.setGroup(group);
                path = customizedPathFor(type, group, true);
                break;
            case GROUP_FILTER:
                final GroupFilter groupFilter = EntityHelper.reference(GroupFilter.class, customization.getId());
                file.setGroupFilter(groupFilter);
                path = customizedPathFor(type, groupFilter);
                break;
            default:
                return null;
        }
        return path + resolveName(file);
    }

    public void setCustomizedFileHandler(final CustomizedFileHandler customizedFileHandler) {
        this.customizedFileHandler = customizedFileHandler;
    }

    public void setGroupService(final GroupService groupService) {
        this.groupService = groupService;
    }

    @Override
    public void setServletContext(final ServletContext servletContext) {
        context = servletContext;
    }

    public void setSettingsService(final SettingsService settingsService) {
        this.settingsService = settingsService;
    }

    /**
     * Returns the real file for a given static document
     */
    public File staticFile(final StaticDocument document) {
        return new File(documentDir(), document.getId().toString());
    }

    /**
     * Update a binary file
     */
    public void updateBinaryFile(final java.io.File file, final BinaryFile binaryFile) {
        byte[] contents;
        try {
            contents = binaryFile.getContents().getBytes(1, binaryFile.getSize());
        } catch (final Exception e) {
            throw new IllegalStateException(e);
        }
        final Long lastModified = binaryFile.getLastModified() == null ? System.currentTimeMillis() : binaryFile.getLastModified().getTimeInMillis();
        customizedFileHandler.write(getRelativePath(file), lastModified, contents);
    }

    /**
     * Update a customized file with binary contents
     */
    public void updateFile(final File file, final long lastModified, final byte[] contents) {
        customizedFileHandler.write(getRelativePath(file), lastModified, contents);
    }

    /**
     * Update a customized file with text contents
     */
    public void updateFile(final java.io.File file, final long lastModified, String contentsStr) {
        byte[] contents;
        if (contentsStr == null) {
            contentsStr = "";
        }
        try {
            contents = contentsStr.getBytes("UTF-8");
        } catch (final Exception e) {
            throw new IllegalStateException(e);
        }
        updateFile(file, lastModified, contents);
    }

    /**
     * Update a customized file
     */
    public void updateFile(final java.io.File file, final nl.strohalm.cyclos.entities.customization.files.File customizedFile) {
        final Long lastModified = customizedFile.getLastModified() == null ? System.currentTimeMillis() : customizedFile.getLastModified().getTimeInMillis();
        updateFile(file, lastModified, customizedFile.getContents());
    }

    private Group loadGroup(final Long id, final Relationship... relationships) {
        return LoggedUser.runAsSystem(new Callable<Group>() {
            @Override
            public Group call() throws Exception {
                return groupService.load(id, relationships);
            }
        });
    }

    /**
     * Returns the additional path part for the given customized group
     */
    private String pathPart(final Group group, final boolean forceSameGroup) {
        if (group == null) {
            return null;
        }
        Long groupId;
        if (!forceSameGroup && (group instanceof OperatorGroup)) {
            final OperatorGroup og = (OperatorGroup) loadGroup(group.getId(), RelationshipHelper.nested(OperatorGroup.Relationships.MEMBER, Element.Relationships.GROUP));
            groupId = og.getMember().getGroup().getId();
        } else {
            groupId = group.getId();
        }
        return "group_" + groupId.toString();
    }

    /**
     * Returns the additional path part for the given group filter
     */
    private String pathPart(final GroupFilter groupFilter) {
        if (groupFilter == null) {
            return null;
        }
        return "group_filter_" + groupFilter.getId();
    }

    /**
     * Returns the name of the style sheet for a given group
     */
    private String resolveName(final CustomizedFile file) {
        final Type type = file.getType();
        String name = file.getName();
        final Group group = file.getGroup();
        final GroupFilter groupFilter = file.getGroupFilter();
        // Only style sheets may change the name when customized for groups or group filters. Other types change the path when customized
        if (type != CustomizedFile.Type.STYLE || (group == null && groupFilter == null)) {
            return name;
        }
        String filename;
        String extension;
        final int pos = name.lastIndexOf('.');
        if (pos < 0) {
            filename = name;
            extension = "";
        } else {
            filename = name.substring(0, pos);
            extension = name.substring(pos + 1);
        }
        // Find the path part to append on the name
        String pathPart;
        if (group != null) {
            pathPart = pathPart(group, false);
        } else {
            pathPart = pathPart(groupFilter);
        }
        name = filename + "_" + pathPart + (StringUtils.isEmpty(extension) ? "" : "." + extension);
        return name;
    }

}
