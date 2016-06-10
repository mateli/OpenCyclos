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
package nl.strohalm.cyclos.controls.settings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.entities.settings.LocalSettings.DatePattern;
import nl.strohalm.cyclos.entities.settings.LocalSettings.Language;
import nl.strohalm.cyclos.entities.settings.LocalSettings.TransactionNumber;
import nl.strohalm.cyclos.services.access.ChannelService;
import nl.strohalm.cyclos.services.customization.MessageImportType;
import nl.strohalm.cyclos.services.customization.TranslationMessageService;
import nl.strohalm.cyclos.services.settings.SettingsService;
import nl.strohalm.cyclos.utils.FileUnits;
import nl.strohalm.cyclos.utils.RequestHelper;
import nl.strohalm.cyclos.utils.TextFormat;
import nl.strohalm.cyclos.utils.TimePeriod;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.DataBinderHelper;
import nl.strohalm.cyclos.utils.binding.MapBean;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.conversion.TimeZoneConverter;

import org.apache.commons.lang.StringUtils;

/**
 * Action used to edit local settings
 * @author luis
 */
public class EditLocalSettingsAction extends BaseFormAction {

    private ChannelService            channelService;
    private TranslationMessageService translationMessageService;
    private DataBinder<LocalSettings> dataBinder;

    public DataBinder<LocalSettings> getDataBinder() {
        if (dataBinder == null) {

            final BeanBinder<TransactionNumber> transactionNumberBinder = BeanBinder.instance(TransactionNumber.class, "transactionNumber");
            transactionNumberBinder.registerBinder("prefix", PropertyBinder.instance(String.class, "prefix"));
            transactionNumberBinder.registerBinder("padLength", PropertyBinder.instance(Integer.TYPE, "padLength"));
            transactionNumberBinder.registerBinder("suffix", PropertyBinder.instance(String.class, "suffix"));
            transactionNumberBinder.registerBinder("enabled", PropertyBinder.instance(Boolean.TYPE, "enabled"));

            final BeanBinder<LocalSettings> binder = BeanBinder.instance(LocalSettings.class);
            binder.registerBinder("applicationName", PropertyBinder.instance(String.class, "applicationName"));
            binder.registerBinder("applicationUsername", PropertyBinder.instance(String.class, "applicationUsername"));
            binder.registerBinder("rootUrl", PropertyBinder.instance(String.class, "rootUrl"));

            binder.registerBinder("language", PropertyBinder.instance(Language.class, "language"));
            binder.registerBinder("numberLocale", PropertyBinder.instance(LocalSettings.NumberLocale.class, "numberLocale"));
            binder.registerBinder("precision", PropertyBinder.instance(LocalSettings.Precision.class, "precision"));
            binder.registerBinder("highPrecision", PropertyBinder.instance(LocalSettings.Precision.class, "highPrecision"));
            binder.registerBinder("decimalInputMethod", PropertyBinder.instance(LocalSettings.DecimalInputMethod.class, "decimalInputMethod"));
            binder.registerBinder("datePattern", PropertyBinder.instance(LocalSettings.DatePattern.class, "datePattern"));
            binder.registerBinder("timePattern", PropertyBinder.instance(LocalSettings.TimePattern.class, "timePattern"));
            binder.registerBinder("timeZone", PropertyBinder.instance(TimeZone.class, "timeZone", TimeZoneConverter.instance()));
            binder.registerBinder("containerUrl", PropertyBinder.instance(String.class, "containerUrl"));

            binder.registerBinder("maxIteratorResults", PropertyBinder.instance(Integer.TYPE, "maxIteratorResults"));
            binder.registerBinder("maxPageResults", PropertyBinder.instance(Integer.TYPE, "maxPageResults"));
            binder.registerBinder("maxAjaxResults", PropertyBinder.instance(Integer.TYPE, "maxAjaxResults"));
            binder.registerBinder("maxUploadSize", PropertyBinder.instance(Integer.TYPE, "maxUploadSize"));
            binder.registerBinder("maxUploadUnits", PropertyBinder.instance(FileUnits.class, "maxUploadUnits"));
            binder.registerBinder("maxImageWidth", PropertyBinder.instance(Integer.TYPE, "maxImageWidth"));
            binder.registerBinder("maxImageHeight", PropertyBinder.instance(Integer.TYPE, "maxImageHeight"));
            binder.registerBinder("maxThumbnailWidth", PropertyBinder.instance(Integer.TYPE, "maxThumbnailWidth"));
            binder.registerBinder("maxThumbnailHeight", PropertyBinder.instance(Integer.TYPE, "maxThumbnailHeight"));

            binder.registerBinder("csvUseHeader", PropertyBinder.instance(Boolean.TYPE, "csvUseHeader"));
            binder.registerBinder("csvRecordSeparator", PropertyBinder.instance(LocalSettings.CsvRecordSeparator.class, "csvRecordSeparator"));
            binder.registerBinder("csvValueSeparator", PropertyBinder.instance(LocalSettings.CsvValueSeparator.class, "csvValueSeparator"));
            binder.registerBinder("csvStringQuote", PropertyBinder.instance(LocalSettings.CsvStringQuote.class, "csvStringQuote"));

            binder.registerBinder("cyclosId", PropertyBinder.instance(String.class, "cyclosId"));
            binder.registerBinder("smsEnabled", PropertyBinder.instance(Boolean.TYPE, "smsEnabled"));
            binder.registerBinder("sendSmsWebServiceUrl", PropertyBinder.instance(String.class, "sendSmsWebServiceUrl"));
            binder.registerBinder("smsCustomFieldId", PropertyBinder.instance(Long.TYPE, "smsCustomFieldId"));
            binder.registerBinder("smsChannelName", PropertyBinder.instance(String.class, "smsChannelName"));

            binder.registerBinder("emailRequired", PropertyBinder.instance(Boolean.TYPE, "emailRequired"));
            binder.registerBinder("emailUnique", PropertyBinder.instance(Boolean.TYPE, "emailUnique"));
            binder.registerBinder("transactionNumber", transactionNumberBinder);

            binder.registerBinder("brokeringExpirationPeriod", DataBinderHelper.timePeriodBinder("brokeringExpirationPeriod"));
            binder.registerBinder("deleteMessagesOnTrashAfter", DataBinderHelper.timePeriodBinder("deleteMessagesOnTrashAfter"));
            binder.registerBinder("deletePendingRegistrationsAfter", DataBinderHelper.timePeriodBinder("deletePendingRegistrationsAfter"));
            binder.registerBinder("memberSortOrder", PropertyBinder.instance(LocalSettings.SortOrder.class, "memberSortOrder"));
            binder.registerBinder("memberResultDisplay", PropertyBinder.instance(LocalSettings.MemberResultDisplay.class, "memberResultDisplay"));
            binder.registerBinder("adDescriptionFormat", PropertyBinder.instance(TextFormat.class, "adDescriptionFormat"));
            binder.registerBinder("messageFormat", PropertyBinder.instance(TextFormat.class, "messageFormat"));

            binder.registerBinder("schedulingHour", PropertyBinder.instance(Integer.TYPE, "schedulingHour"));
            binder.registerBinder("schedulingMinute", PropertyBinder.instance(Integer.TYPE, "schedulingMinute"));
            binder.registerBinder("transferListenerClass", PropertyBinder.instance(String.class, "transferListenerClass"));

            binder.registerBinder("maxChargebackTime", DataBinderHelper.timePeriodBinder("maxChargebackTime"));
            binder.registerBinder("chargebackDescription", PropertyBinder.instance(String.class, "chargebackDescription"));
            binder.registerBinder("showCountersInAdCategories", PropertyBinder.instance(Boolean.TYPE, "showCountersInAdCategories"));

            dataBinder = binder;
        }
        return dataBinder;
    }

    public TranslationMessageService getMessageService() {
        return translationMessageService;
    }

    public SettingsService getSettingsService() {
        return settingsService;
    }

    @Inject
    public void setChannelService(final ChannelService channelService) {
        this.channelService = channelService;
    }

    @Inject
    public void setTranslationMessageService(final TranslationMessageService translationMessageService) {
        this.translationMessageService = translationMessageService;
    }

    @Override
    protected void formAction(final ActionContext context) throws Exception {
        final LocalSettings oldSettings = settingsService.getLocalSettings();

        LocalSettings settings = resolveLocalSettings(context);
        settings = settingsService.save(settings);

        // There are some steps when the language is changed...
        if (oldSettings.getLanguage() != settings.getLanguage()) {
            // Replace message bundle file
            final Properties properties = translationMessageService.readFile(settings.getLocale());
            translationMessageService.importFromProperties(properties, MessageImportType.REPLACE);

            // Replace the translation settings (mail and messages)
            settingsService.reloadTranslation();
        }

        context.sendMessage("settings.local.modified");
    }

    @Override
    protected void prepareForm(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();
        final EditLocalSettingsForm form = context.getForm();
        final LocalSettings settings = settingsService.getLocalSettings();

        form.setSetting("enableSms", StringUtils.isNotEmpty(settings.getSendSmsWebServiceUrl()) || StringUtils.isNotEmpty(settings.getSmsChannelName()));
        final TransactionNumber transactionNumber = settings.getTransactionNumber();
        form.setSetting("enableTransactionNumber", transactionNumber != null && transactionNumber.getPadLength() > 0);

        getDataBinder().writeAsString(form.getSetting(), settings);

        RequestHelper.storeEnum(request, LocalSettings.Language.class, "languages");
        RequestHelper.storeEnum(request, FileUnits.class, "uploadUnits");
        RequestHelper.storeEnum(request, LocalSettings.NumberLocale.class, "numberLocales");
        RequestHelper.storeEnum(request, LocalSettings.Precision.class, "precisions");
        RequestHelper.storeEnum(request, LocalSettings.DecimalInputMethod.class, "decimalInputMethods");
        final Map<DatePattern, String> datePatterns = new LinkedHashMap<DatePattern, String>();
        for (final DatePattern datePattern : DatePattern.values()) {
            datePatterns.put(datePattern, messageHelper.getDatePatternDescription(datePattern).toUpperCase());
        }
        request.setAttribute("datePatterns", datePatterns);
        RequestHelper.storeEnum(request, LocalSettings.TimePattern.class, "timePatterns");
        RequestHelper.storeEnum(request, LocalSettings.CsvRecordSeparator.class, "csvRecordSeparators");
        RequestHelper.storeEnum(request, LocalSettings.CsvValueSeparator.class, "csvValueSeparators");
        RequestHelper.storeEnum(request, LocalSettings.CsvStringQuote.class, "csvStringQuotes");
        RequestHelper.storeEnum(request, LocalSettings.MemberResultDisplay.class, "memberResultDisplays");
        RequestHelper.storeEnum(request, LocalSettings.SortOrder.class, "memberSortOrders");
        RequestHelper.storeEnum(request, TextFormat.class, "textFormats");
        request.setAttribute("brokeringExpirationUnits", Arrays.asList(TimePeriod.Field.DAYS, TimePeriod.Field.MONTHS, TimePeriod.Field.YEARS));
        request.setAttribute("deleteMessagesExpirationUnits", Arrays.asList(TimePeriod.Field.DAYS, TimePeriod.Field.MONTHS, TimePeriod.Field.YEARS));
        request.setAttribute("maxChargebackTimeUnits", Arrays.asList(TimePeriod.Field.DAYS, TimePeriod.Field.WEEKS, TimePeriod.Field.MONTHS));
        request.setAttribute("indexRebuildingTimeUnits", Arrays.asList(TimePeriod.Field.DAYS, TimePeriod.Field.WEEKS, TimePeriod.Field.MONTHS));
        request.setAttribute("smsChannels", channelService.listNonBuiltin());
        request.setAttribute("smsCustomFields", channelService.possibleCustomFieldsAsPrincipal());

        // Transform the time zones in an
        final List<String> timeZones = new ArrayList<String>();
        for (final String id : TimeZone.getAvailableIDs()) {
            if (!id.contains("/") || id.contains("Etc")) {
                continue;
            }
            timeZones.add(id);
        }
        Collections.sort(timeZones);
        timeZones.add(0, "GMT");
        request.setAttribute("timeZones", timeZones);
    }

    @Override
    protected void validateForm(final ActionContext context) {
        final LocalSettings settings = resolveLocalSettings(context);
        settingsService.validate(settings);
    }

    /**
     * Resolve the submitted local settings instance
     */
    private LocalSettings resolveLocalSettings(final ActionContext context) {
        final EditLocalSettingsForm form = context.getForm();
        final LocalSettings settings = getDataBinder().readFromString(form.getSetting());
        // If transaction number is not enabled, clear the setting
        final MapBean tn = (MapBean) form.getSetting("transactionNumber");
        if (!Boolean.parseBoolean((String) tn.get("enabled"))) {
            settings.setTransactionNumber(null);
        }
        return settings;
    }
}
