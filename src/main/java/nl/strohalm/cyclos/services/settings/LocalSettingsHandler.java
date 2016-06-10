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
package nl.strohalm.cyclos.services.settings;

import java.util.LinkedHashMap;
import java.util.Map;

import nl.strohalm.cyclos.entities.access.Channel;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferListener;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.entities.settings.LocalSettings.CsvRecordSeparator;
import nl.strohalm.cyclos.entities.settings.LocalSettings.CsvStringQuote;
import nl.strohalm.cyclos.entities.settings.LocalSettings.CsvValueSeparator;
import nl.strohalm.cyclos.entities.settings.LocalSettings.DatePattern;
import nl.strohalm.cyclos.entities.settings.LocalSettings.DecimalInputMethod;
import nl.strohalm.cyclos.entities.settings.LocalSettings.Language;
import nl.strohalm.cyclos.entities.settings.LocalSettings.MemberResultDisplay;
import nl.strohalm.cyclos.entities.settings.LocalSettings.NumberLocale;
import nl.strohalm.cyclos.entities.settings.LocalSettings.Precision;
import nl.strohalm.cyclos.entities.settings.LocalSettings.SortOrder;
import nl.strohalm.cyclos.entities.settings.LocalSettings.TimePattern;
import nl.strohalm.cyclos.entities.settings.LocalSettings.TransactionNumber;
import nl.strohalm.cyclos.entities.settings.Setting;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsChangeListener;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsEvent;
import nl.strohalm.cyclos.services.access.ChannelServiceLocal;
import nl.strohalm.cyclos.services.customization.MemberCustomFieldServiceLocal;
import nl.strohalm.cyclos.utils.FileUnits;
import nl.strohalm.cyclos.utils.TextFormat;
import nl.strohalm.cyclos.utils.TimePeriod.Field;
import nl.strohalm.cyclos.utils.conversion.CoercionConverter;
import nl.strohalm.cyclos.utils.conversion.Converter;
import nl.strohalm.cyclos.utils.conversion.IdConverter;
import nl.strohalm.cyclos.utils.conversion.TimeZoneConverter;
import nl.strohalm.cyclos.utils.validation.CompareToValidation;
import nl.strohalm.cyclos.utils.validation.InvalidError;
import nl.strohalm.cyclos.utils.validation.PropertyValidation;
import nl.strohalm.cyclos.utils.validation.RequiredError;
import nl.strohalm.cyclos.utils.validation.RequiredValidation;
import nl.strohalm.cyclos.utils.validation.ValidationError;
import nl.strohalm.cyclos.utils.validation.Validator;

import org.apache.commons.lang.StringUtils;

/**
 * Local settings handler
 * @author luis
 */
public class LocalSettingsHandler extends BaseSettingsHandler<LocalSettings, LocalSettingsChangeListener> {
    private ChannelServiceLocal           channelService;
    private MemberCustomFieldServiceLocal memberCustomFieldService;

    protected LocalSettingsHandler() {
        super(Setting.Type.LOCAL, LocalSettings.class);
    }

    public void setChannelServiceLocal(final ChannelServiceLocal channelService) {
        this.channelService = channelService;
    }

    public void setMemberCustomFieldServiceLocal(final MemberCustomFieldServiceLocal memberCustomFieldService) {
        this.memberCustomFieldService = memberCustomFieldService;
    }

    @Override
    protected Map<String, Converter<?>> createConverters() {
        final Map<String, Converter<?>> localConverters = new LinkedHashMap<String, Converter<?>>();
        localConverters.put("applicationName", CoercionConverter.instance(String.class));
        localConverters.put("applicationUsername", CoercionConverter.instance(String.class));
        localConverters.put("rootUrl", CoercionConverter.instance(String.class));
        localConverters.put("language", CoercionConverter.instance(Language.class));
        localConverters.put("numberLocale", CoercionConverter.instance(NumberLocale.class));
        localConverters.put("precision", CoercionConverter.instance(Precision.class));
        localConverters.put("highPrecision", CoercionConverter.instance(Precision.class));
        localConverters.put("decimalInputMethod", CoercionConverter.instance(DecimalInputMethod.class));
        localConverters.put("datePattern", CoercionConverter.instance(DatePattern.class));
        localConverters.put("timePattern", CoercionConverter.instance(TimePattern.class));
        localConverters.put("timeZone", TimeZoneConverter.instance());
        localConverters.put("containerUrl", CoercionConverter.instance(String.class));
        localConverters.put("maxIteratorResults", CoercionConverter.instance(Integer.TYPE));
        localConverters.put("maxPageResults", CoercionConverter.instance(Integer.TYPE));
        localConverters.put("maxAjaxResults", CoercionConverter.instance(Integer.TYPE));
        localConverters.put("maxUploadSize", CoercionConverter.instance(Integer.TYPE));
        localConverters.put("maxUploadUnits", CoercionConverter.instance(FileUnits.class));
        localConverters.put("maxImageWidth", CoercionConverter.instance(Integer.TYPE));
        localConverters.put("maxImageHeight", CoercionConverter.instance(Integer.TYPE));
        localConverters.put("maxThumbnailWidth", CoercionConverter.instance(Integer.TYPE));
        localConverters.put("maxThumbnailHeight", CoercionConverter.instance(Integer.TYPE));
        localConverters.put("referenceLevels", CoercionConverter.instance(Integer.TYPE));
        localConverters.put("smsEnabled", CoercionConverter.instance(Boolean.TYPE));
        localConverters.put("csvUseHeader", CoercionConverter.instance(Boolean.TYPE));
        localConverters.put("csvRecordSeparator", CoercionConverter.instance(CsvRecordSeparator.class));
        localConverters.put("csvValueSeparator", CoercionConverter.instance(CsvValueSeparator.class));
        localConverters.put("csvStringQuote", CoercionConverter.instance(CsvStringQuote.class));
        localConverters.put("cyclosId", CoercionConverter.instance(String.class));
        localConverters.put("sendSmsWebServiceUrl", CoercionConverter.instance(String.class));
        localConverters.put("smsChannelName", CoercionConverter.instance(String.class));
        localConverters.put("smsCustomFieldId", IdConverter.instance());
        localConverters.put("transactionNumber.prefix", CoercionConverter.instance(String.class));
        localConverters.put("transactionNumber.padLength", CoercionConverter.instance(Integer.TYPE));
        localConverters.put("transactionNumber.suffix", CoercionConverter.instance(String.class));
        localConverters.put("emailRequired", CoercionConverter.instance(Boolean.TYPE));
        localConverters.put("emailUnique", CoercionConverter.instance(Boolean.TYPE));
        localConverters.put("brokeringExpirationPeriod.number", CoercionConverter.instance(Integer.class));
        localConverters.put("brokeringExpirationPeriod.field", CoercionConverter.instance(Field.class));
        localConverters.put("deleteMessagesOnTrashAfter.number", CoercionConverter.instance(Integer.class));
        localConverters.put("deleteMessagesOnTrashAfter.field", CoercionConverter.instance(Field.class));
        localConverters.put("deletePendingRegistrationsAfter.number", CoercionConverter.instance(Integer.class));
        localConverters.put("deletePendingRegistrationsAfter.field", CoercionConverter.instance(Field.class));
        localConverters.put("memberSortOrder", CoercionConverter.instance(SortOrder.class));
        localConverters.put("memberResultDisplay", CoercionConverter.instance(MemberResultDisplay.class));
        localConverters.put("adDescriptionFormat", CoercionConverter.instance(TextFormat.class));
        localConverters.put("messageFormat", CoercionConverter.instance(TextFormat.class));
        localConverters.put("schedulingHour", CoercionConverter.instance(Integer.TYPE));
        localConverters.put("schedulingMinute", CoercionConverter.instance(Integer.TYPE));
        localConverters.put("transferListenerClass", CoercionConverter.instance(String.class));
        localConverters.put("maxChargebackTime.number", CoercionConverter.instance(Integer.class));
        localConverters.put("maxChargebackTime.field", CoercionConverter.instance(Field.class));
        localConverters.put("chargebackDescription", CoercionConverter.instance(String.class));
        localConverters.put("showCountersInAdCategories", CoercionConverter.instance(Boolean.TYPE));
        return localConverters;
    }

    @Override
    protected Validator createValidator() {
        final Validator localValidator = new Validator("settings.local");
        localValidator.property("applicationName").required().length(1, 100);
        localValidator.property("applicationUsername").required().length(1, 100);
        localValidator.property("rootUrl").required().url();
        localValidator.property("language").required();
        localValidator.property("numberLocale").required();
        localValidator.property("precision").required().between(0, 6);
        localValidator.property("highPrecision").required().between(0, 6);
        localValidator.property("decimalInputMethod").required();
        localValidator.property("datePattern").required();
        localValidator.property("timePattern").required();
        localValidator.property("containerUrl").url();
        localValidator.property("maxIteratorResults").required().positive();
        localValidator.property("maxPageResults").required().positiveNonZero();
        localValidator.property("maxAjaxResults").required().positiveNonZero();
        localValidator.property("maxUploadSize").required().positiveNonZero();
        localValidator.property("maxUploadUnits").required();
        localValidator.property("maxImageWidth").required().between(10, 10000);
        localValidator.property("maxImageHeight").required().between(10, 10000);
        localValidator.property("maxThumbnailWidth").required().between(10, 200);
        localValidator.property("maxThumbnailHeight").required().between(10, 200);
        localValidator.property("referenceLevels").required().anyOf(3, 5);
        localValidator.property("csvRecordSeparator").required();
        localValidator.property("csvValueSeparator").required();
        localValidator.property("csvStringQuote").required();
        localValidator.property("cyclosId").length(0, 100);
        localValidator.property("transactionNumber.prefix").length(0, 50);
        localValidator.property("transactionNumber.padLength").add(new PropertyValidation() {

            private static final long serialVersionUID = -6756132904303474845L;

            @Override
            public ValidationError validate(final Object object, final Object property, final Object value) {
                LocalSettings settings = (LocalSettings) object;
                if (settings.getTransactionNumber() != null && settings.getTransactionNumber().isEnabled()) {
                    ValidationError required = RequiredValidation.instance().validate(object, property, value);
                    if (required != null) {
                        return required;
                    }
                    ValidationError greaterThan = CompareToValidation.greaterEquals(1).validate(object, property, value);
                    if (greaterThan != null) {
                        return greaterThan;
                    }
                    ValidationError lessThan = CompareToValidation.lessEquals(20).validate(object, property, value);
                    if (lessThan != null) {
                        return lessThan;
                    }

                }
                return null;
            }
        });
        localValidator.property("transactionNumber.suffix").length(0, 50);
        localValidator.property("brokeringExpirationPeriod.number").between(0, 999);
        localValidator.property("brokeringExpirationPeriod.field").required();
        localValidator.property("deleteMessagesOnTrashAfter.number").between(0, 999);
        localValidator.property("deleteMessagesOnTrashAfter.field").required();
        localValidator.property("deletePendingRegistrationsAfter.number").between(0, 999);
        localValidator.property("deletePendingRegistrationsAfter.field").required();
        localValidator.property("memberSortOrder").required();
        localValidator.property("memberResultDisplay").required();
        localValidator.property("adDescriptionFormat").required();
        localValidator.property("messageFormat").required();
        localValidator.property("schedulingHour").required().between(0, 23);
        localValidator.property("schedulingMinute").required().between(0, 59);
        localValidator.property("transferListenerClass").instanceOf(TransferListener.class);
        localValidator.property("maxChargebackTime.number").between(0, 999);
        localValidator.property("maxChargebackTime.field").required();
        localValidator.property("chargebackDescription").required();

        localValidator.property("sendSmsWebServiceUrl").key("settings.local.sms.sendSmsWebServiceUrl").length(0, 256).add(new PropertyValidation() {
            private static final long serialVersionUID = 0L;

            @Override
            public ValidationError validate(final Object object, final Object name, final Object value) {
                final LocalSettings settings = (LocalSettings) object;

                if (StringUtils.isEmpty((String) value) && settings.isSmsEnabled()) {
                    return new RequiredError();
                }
                else {
                    return null;
                }
            }
        });

        localValidator.property("smsChannelName").key("settings.local.sms.channel").add(new PropertyValidation() {
            private static final long serialVersionUID = 0L;

            @Override
            public ValidationError validate(final Object object, final Object name, final Object value) {
                final LocalSettings settings = (LocalSettings) object;

                if (!settings.isSmsEnabled()) {
                    return null;
                }

                if (StringUtils.isEmpty((String) value) && settings.isSmsEnabled()) {
                    return new RequiredError();
                }

                boolean channelFound = false;
                for (final Channel channel : channelService.listNonBuiltin()) {
                    if (channel.getInternalName().equals(value)) {
                        channelFound = true;
                        break;
                    }
                }
                return channelFound ? null : new InvalidError();
            }
        });

        localValidator.property("smsCustomFieldId").key("settings.local.sms.customField").add(new PropertyValidation() {
            private static final long serialVersionUID = 0L;

            @Override
            public ValidationError validate(final Object object, final Object property, final Object value) {
                final LocalSettings settings = (LocalSettings) object;
                final Long id = (Long) value;

                if (!settings.isSmsEnabled()) {
                    return null;
                }

                if (id == null || id.intValue() < 0 && settings.isSmsEnabled()) {
                    return new RequiredError();
                }
                try {
                    // Load and ensure it's a valid member custom field
                    memberCustomFieldService.load(id);
                    return null;
                } catch (final EntityNotFoundException e) {
                    return new InvalidError();
                }
            }
        });

        return localValidator;
    }

    @Override
    protected void notifyListener(final LocalSettingsChangeListener listener, final LocalSettings settings) {
        listener.onLocalSettingsUpdate(new LocalSettingsEvent(settings));
    }

    @Override
    protected LocalSettings read() {
        final LocalSettings localSettings = super.read();

        // Validate the transaction number
        TransactionNumber transactionNumber = localSettings.getTransactionNumber();
        if (transactionNumber != null) {
            if (transactionNumber.getPadLength() <= 0) {
                localSettings.setTransactionNumber(null);
            } else {
                transactionNumber.setEnabled(true);
            }
        }

        return localSettings;
    }

}
