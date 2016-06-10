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
package nl.strohalm.cyclos.controls.groups.groupFilters.customizedFiles;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseAjaxAction;
import nl.strohalm.cyclos.entities.customization.files.CustomizedFile;
import nl.strohalm.cyclos.entities.customization.files.CustomizedFile.Type;
import nl.strohalm.cyclos.entities.customization.files.CustomizedFileQuery;
import nl.strohalm.cyclos.entities.groups.GroupFilter;
import nl.strohalm.cyclos.services.customization.CustomizedFileService;
import nl.strohalm.cyclos.utils.CustomizationHelper;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.binding.SimpleCollectionBinder;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.commons.collections.CollectionUtils;

/**
 * Action used to retrieve the available files to be customized for a group filter
 * @author luis
 */
public class GetAvailableFilesForGroupFilterAjaxAction extends BaseAjaxAction {

    public static final Map<CustomizedFile.Type, Collection<String>> POSSIBLE_CUSTOMIZED_FILES;
    static {
        final Set<String> empty = Collections.emptySet();
        final Map<CustomizedFile.Type, Collection<String>> possible = new EnumMap<CustomizedFile.Type, Collection<String>>(CustomizedFile.Type.class);
        possible.put(CustomizedFile.Type.STATIC_FILE, Arrays.asList("login.jsp", "contact.jsp", "general_news.jsp", "member_about.jsp", "top.jsp"));
        possible.put(CustomizedFile.Type.STYLE, Arrays.asList("login.css", "style.css", "mobile.css"));
        possible.put(CustomizedFile.Type.HELP, empty);
        possible.put(CustomizedFile.Type.APPLICATION_PAGE, empty);
        POSSIBLE_CUSTOMIZED_FILES = Collections.unmodifiableMap(possible);
    }

    private DataBinder<CustomizedFileQuery>                          dataBinder;
    private CustomizedFileService                                    customizedFileService;
    private SimpleCollectionBinder<String>                           collectionBinder;
    private CustomizationHelper                                      customizationHelper;

    public SimpleCollectionBinder<String> getCollectionBinder() {
        if (collectionBinder == null) {
            collectionBinder = SimpleCollectionBinder.instance(String.class);
        }
        return collectionBinder;
    }

    public CustomizedFileService getCustomizedFileService() {
        return customizedFileService;
    }

    public DataBinder<CustomizedFileQuery> getDataBinder() {
        if (dataBinder == null) {
            final BeanBinder<CustomizedFileQuery> binder = BeanBinder.instance(CustomizedFileQuery.class);
            binder.registerBinder("type", PropertyBinder.instance(CustomizedFile.Type.class, "type"));
            binder.registerBinder("groupFilter", PropertyBinder.instance(GroupFilter.class, "groupFilterId"));
            dataBinder = binder;
        }
        return dataBinder;
    }

    @Inject
    public void setCustomizationHelper(final CustomizationHelper customizationHelper) {
        this.customizationHelper = customizationHelper;
    }

    @Inject
    public void setCustomizedFileService(final CustomizedFileService customizedFileService) {
        this.customizedFileService = customizedFileService;
    }

    @Override
    protected ContentType contentType() {
        return ContentType.JSON;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void renderContent(final ActionContext context) throws Exception {
        final GetAvailableFilesForGroupFilterAjaxForm form = context.getForm();
        final CustomizedFileQuery query = getDataBinder().readFromString(form);
        final Type type = query.getType();
        if (type == null || query.getGroupFilter() == null) {
            throw new ValidationException();
        }
        final List<CustomizedFile> customizedFiles = customizedFileService.search(query);
        final List<String> notAlreadyCustomized = customizationHelper.onlyNotAlreadyCustomized(type, customizedFiles);

        // Ensure only possible per type
        final Collection<String> possible = CollectionUtils.intersection(notAlreadyCustomized, POSSIBLE_CUSTOMIZED_FILES.get(type));
        responseHelper.writeJSON(context.getResponse(), getCollectionBinder().readAsString(possible));
    }
}
