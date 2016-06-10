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
package nl.strohalm.cyclos.controls.accounts.external.filemappings;

import java.util.Collection;
import java.util.EnumMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.access.AdminSystemPermission;
import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.entities.accounts.external.ExternalAccount;
import nl.strohalm.cyclos.entities.accounts.external.filemapping.CSVFileMapping;
import nl.strohalm.cyclos.entities.accounts.external.filemapping.CustomFileMapping;
import nl.strohalm.cyclos.entities.accounts.external.filemapping.FieldMapping;
import nl.strohalm.cyclos.entities.accounts.external.filemapping.FileMapping;
import nl.strohalm.cyclos.entities.accounts.external.filemapping.FileMappingWithFields;
import nl.strohalm.cyclos.services.accounts.external.ExternalAccountService;
import nl.strohalm.cyclos.services.accounts.external.filemapping.FileMappingService;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.RequestHelper;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.conversion.IdConverter;
import nl.strohalm.cyclos.utils.conversion.ReferenceConverter;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.struts.action.ActionForward;

/**
 * Action used to edit a file mapping
 * @author jefferson
 */
public class EditFileMappingAction extends BaseFormAction {

    private ExternalAccountService                                     externalAccountService;
    private FileMappingService                                         fileMappingService;
    private Map<FileMapping.Nature, DataBinder<? extends FileMapping>> dataBinders;

    public DataBinder<? extends FileMapping> getDataBinder(final FileMapping.Nature nature) {
        if (dataBinders == null) {
            dataBinders = new EnumMap<FileMapping.Nature, DataBinder<? extends FileMapping>>(FileMapping.Nature.class);

            final BeanBinder<CSVFileMapping> csvFileMappingBinder = BeanBinder.instance(CSVFileMapping.class);
            initBasicFileMapping(csvFileMappingBinder);
            initFileMappingWithFields(csvFileMappingBinder);
            csvFileMappingBinder.registerBinder("stringQuote", PropertyBinder.instance(Character.class, "stringQuote"));
            csvFileMappingBinder.registerBinder("columnSeparator", PropertyBinder.instance(Character.class, "columnSeparator"));
            csvFileMappingBinder.registerBinder("headerLines", PropertyBinder.instance(Integer.class, "headerLines"));
            dataBinders.put(FileMapping.Nature.CSV, csvFileMappingBinder);

            final BeanBinder<CustomFileMapping> customFileMappingBinder = BeanBinder.instance(CustomFileMapping.class);
            initBasicFileMapping(customFileMappingBinder);
            customFileMappingBinder.registerBinder("className", PropertyBinder.instance(String.class, "className"));
            dataBinders.put(FileMapping.Nature.CUSTOM, customFileMappingBinder);
        }
        return dataBinders.get(nature);
    }

    @Inject
    public void setExternalAccountService(final ExternalAccountService externalAccountService) {
        this.externalAccountService = externalAccountService;
    }

    @Inject
    public void setFileMappingService(final FileMappingService fileMappingService) {
        this.fileMappingService = fileMappingService;
    }

    @Override
    protected ActionForward handleSubmit(final ActionContext context) throws Exception {
        final FileMapping fileMapping = resolveFileMapping(context);
        final boolean isInsert = fileMapping.isTransient();
        fileMappingService.save(fileMapping);
        context.sendMessage(isInsert ? "fileMapping.inserted" : "fileMapping.modified");
        return ActionHelper.redirectWithParam(context.getRequest(), context.getSuccessForward(), "externalAccountId", fileMapping.getAccount().getId());
    }

    @Override
    protected void prepareForm(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();
        final EditFileMappingForm form = context.getForm();
        final long externalAccountId = form.getExternalAccountId();
        if (externalAccountId <= 0) {
            throw new ValidationException();
        }
        final ExternalAccount externalAccount = externalAccountService.load(externalAccountId, RelationshipHelper.nested(ExternalAccount.Relationships.FILE_MAPPING, FileMappingWithFields.Relationships.FIELDS));

        final FileMapping fileMapping = externalAccount.getFileMapping();
        final boolean isInsert = (fileMapping == null);
        if (!isInsert) {
            final DataBinder<? extends FileMapping> dataBinder = getDataBinder(fileMapping.getNature());
            dataBinder.writeAsString(form.getFileMapping(), fileMapping);
            request.setAttribute("fileMapping", fileMapping);
            if (fileMapping instanceof FileMappingWithFields) {
                final FileMappingWithFields fmWithFields = (FileMappingWithFields) fileMapping;
                final Collection<FieldMapping> fieldMappings = fmWithFields.getFields();
                request.setAttribute("fieldMappings", fieldMappings);
            }
        } else {
            // Set default values on the form
            form.setFileMapping("columnSeparator", CSVFileMapping.DEFAULT_COLUMN_SEPARATOR);
            form.setFileMapping("stringQuote", CSVFileMapping.DEFAULT_STRING_QUOTE);
            form.setFileMapping("headerLines", CSVFileMapping.DEFAULT_HEADER_LINES);
            form.setFileMapping("dateFormat", FileMappingWithFields.DEFAULT_DATE_FORMAT);
            form.setFileMapping("numberFormat", FileMappingWithFields.DEFAULT_NUMBER_FORMAT);
            form.setFileMapping("decimalPlaces", FileMappingWithFields.DEFAULT_DECIMAL_PLACES);
            form.setFileMapping("decimalSeparator", FileMappingWithFields.DEFAULT_DECIMAL_SEPARATOR);
            form.setFileMapping("negativeAmountValue", FileMappingWithFields.DEFAULT_NEGATIVE_AMOUNT_VALUE);
        }
        final boolean editable = permissionService.hasPermission(AdminSystemPermission.EXTERNAL_ACCOUNTS_MANAGE);
        form.setFileMapping("account", externalAccount.getId());
        request.setAttribute("externalAccountId", externalAccount.getId());
        request.setAttribute("editable", editable);
        request.setAttribute("isInsert", isInsert);
        RequestHelper.storeEnum(request, FileMapping.Nature.class, "natures");
        RequestHelper.storeEnum(request, FileMappingWithFields.NumberFormat.class, "numberFormats");
    }

    @Override
    protected void validateForm(final ActionContext context) {
        final FileMapping fileMapping = resolveFileMapping(context);
        fileMappingService.validate(fileMapping);
    }

    private void initBasicFileMapping(final BeanBinder<? extends FileMapping> fileMappingBinder) {
        fileMappingBinder.registerBinder("id", PropertyBinder.instance(Long.class, "id", IdConverter.instance()));
        fileMappingBinder.registerBinder("account", PropertyBinder.instance(ExternalAccount.class, "account", ReferenceConverter.instance(ExternalAccount.class)));
    }

    private void initFileMappingWithFields(final BeanBinder<? extends FileMappingWithFields> fileMappingWithFieldsBinder) {
        fileMappingWithFieldsBinder.registerBinder("numberFormat", PropertyBinder.instance(FileMappingWithFields.NumberFormat.class, "numberFormat"));
        fileMappingWithFieldsBinder.registerBinder("decimalPlaces", PropertyBinder.instance(Integer.class, "decimalPlaces"));
        fileMappingWithFieldsBinder.registerBinder("decimalSeparator", PropertyBinder.instance(Character.class, "decimalSeparator"));
        fileMappingWithFieldsBinder.registerBinder("negativeAmountValue", PropertyBinder.instance(String.class, "negativeAmountValue"));
        fileMappingWithFieldsBinder.registerBinder("dateFormat", PropertyBinder.instance(String.class, "dateFormat"));
    }

    private FileMapping resolveFileMapping(final ActionContext context) {
        final EditFileMappingForm form = context.getForm();
        FileMapping.Nature nature = null;
        final String fileMappingNature = (String) form.getFileMapping("nature");
        if ("CSV".equals(fileMappingNature)) {
            nature = FileMapping.Nature.CSV;
        } else {
            nature = FileMapping.Nature.CUSTOM;
        }
        return getDataBinder(nature).readFromString(form.getFileMapping());
    }

}
