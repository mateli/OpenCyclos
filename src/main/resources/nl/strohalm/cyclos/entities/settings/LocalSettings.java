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
package nl.strohalm.cyclos.entities.settings;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Reference.Level;
import nl.strohalm.cyclos.utils.DataObject;
import nl.strohalm.cyclos.utils.Dimensions;
import nl.strohalm.cyclos.utils.FileUnits;
import nl.strohalm.cyclos.utils.IntValuedEnum;
import nl.strohalm.cyclos.utils.MessageProcessingHelper;
import nl.strohalm.cyclos.utils.StringValuedEnum;
import nl.strohalm.cyclos.utils.TextFormat;
import nl.strohalm.cyclos.utils.TimePeriod;
import nl.strohalm.cyclos.utils.conversion.AmountConverter;
import nl.strohalm.cyclos.utils.conversion.CalendarConverter;
import nl.strohalm.cyclos.utils.conversion.CoercionHelper;
import nl.strohalm.cyclos.utils.conversion.NumberConverter;
import nl.strohalm.cyclos.utils.conversion.UnitsConverter;
import nl.strohalm.cyclos.utils.lucene.LuceneUtils;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.lang.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.br.BrazilianAnalyzer;
import org.apache.lucene.analysis.de.GermanAnalyzer;
import org.apache.lucene.analysis.el.GreekAnalyzer;
import org.apache.lucene.analysis.fr.FrenchAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;

/**
 * Groups local settings
 * @author luis
 */
@SuppressWarnings("unchecked")
public class LocalSettings extends DataObject {

    /**
     * Contains possible values for csv record separators
     * @author luis
     */
    public static enum CsvRecordSeparator implements StringValuedEnum {
        LF("\n"), CR_LF("\r\n"), CR("\r");
        private final String value;

        private CsvRecordSeparator(final String value) {
            this.value = value;
        }

        @Override
        public String getValue() {
            return value;
        }
    }

    /**
     * Contains possible values for csv string quotes
     * @author luis
     */
    public static enum CsvStringQuote implements StringValuedEnum {
        DOUBLE_QUOTE("\""), SINGLE_QUOTE("'"), NONE("");
        private final String value;

        private CsvStringQuote(final String value) {
            this.value = value;
        }

        @Override
        public String getValue() {
            return value;
        }
    }

    /**
     * Contains possible values for csv value separators
     * @author luis
     */
    public static enum CsvValueSeparator implements StringValuedEnum {
        COMMA(","), SEMICOLON(";"), TAB("\t");
        private final String value;

        private CsvValueSeparator(final String value) {
            this.value = value;
        }

        @Override
        public String getValue() {
            return value;
        }
    }

    /**
     * Possible patterns for date representation
     * @author luis
     */
    public static enum DatePattern implements StringValuedEnum {
        YYYY_MM_DD_SLASH(DatePatternOrder.YMD, '/'), YYYY_MM_DD_PERIOD(DatePatternOrder.YMD, '.'), YYYY_MM_DD_DASH(DatePatternOrder.YMD, '-'),

        MM_DD_YYYY_SLASH(DatePatternOrder.MDY, '/'), MM_DD_YYYY_PERIOD(DatePatternOrder.MDY, '.'), MM_DD_YYYY_DASH(DatePatternOrder.MDY, '-'),

        DD_MM_YYYY_SLASH(DatePatternOrder.DMY, '/'), DD_MM_YYYY_PERIOD(DatePatternOrder.DMY, '.'), DD_MM_YYYY_DASH(DatePatternOrder.DMY, '-');

        private final DatePatternOrder order;
        private final String           value;
        private final char             separator;
        private final String           calendarFormat;
        private final String           pattern;

        private DatePattern(final DatePatternOrder order, final char separator) {
            this.order = order;
            this.separator = separator;
            value = StringUtils.join(order.getFormatParts(), separator);
            calendarFormat = StringUtils.join(order.getCalendarParts(), separator);
            final StringBuilder sb = new StringBuilder();
            for (final String part : order.getCalendarParts()) {
                if (sb.length() > 0) {
                    sb.append(separator);
                }
                sb.append(StringUtils.repeat("#", part.length()));
            }
            pattern = sb.toString();
        }

        public String getCalendarFormat() {
            return calendarFormat;
        }

        public DatePatternOrder getOrder() {
            return order;
        }

        public String getPattern() {
            return pattern;
        }

        public char getSeparator() {
            return separator;
        }

        @Override
        public String getValue() {
            return value;
        }
    }

    public static enum DatePatternOrder {
        YMD, MDY, DMY;

        public String[] getCalendarParts() {
            switch (this) {
                case DMY:
                    return new String[] { "%d", "%m", "%Y" };
                case MDY:
                    return new String[] { "%m", "%d", "%Y" };
                case YMD:
                    return new String[] { "%Y", "%m", "%d" };
            }
            return null;
        }

        public String[] getFormatParts() {
            switch (this) {
                case DMY:
                    return new String[] { "dd", "MM", "yyyy" };
                case MDY:
                    return new String[] { "MM", "dd", "yyyy" };
                case YMD:
                    return new String[] { "yyyy", "MM", "dd" };
            }
            return null;
        }
    }

    public static enum DecimalInputMethod {
        RTL, LTR
    }

    /**
     * Contains possible number formats
     * @author luis
     */
    public static enum Language implements StringValuedEnum {
        ENGLISH("en_US", "en"),

        SPANISH("es_ES", "es"),

        // SPANISH_TIMEBANKS("es_ES_TB", "es"),
        CZECH("cs_CZ", "cs"),

        GERMAN("de_DE", "de", new GermanAnalyzer(LuceneUtils.LUCENE_VERSION)),

        GREEK("el_GR", "el", new GreekAnalyzer(LuceneUtils.LUCENE_VERSION)),

        DUTCH("nl_NL", "nl"),

        PORTUGUESE_BRAZIL("pt_BR", "pt-br", new BrazilianAnalyzer(LuceneUtils.LUCENE_VERSION)),

        CHINESE_SIMPLIFIED("zh_CN", "zh-cn"),

        FRENCH("fr_FR", "fr", new FrenchAnalyzer(LuceneUtils.LUCENE_VERSION)),

        JAPANESE("ja_JP", "jp"),

        ITALIAN("it_IT", "it"),

        RUSSIAN("ru_RU", "ru");

        private final Locale   locale;
        private final String   value;
        private final String   alternate;
        private final Analyzer analyzer;

        private Language(final String value, final String alternate) {
            this(value, alternate, new StandardAnalyzer(LuceneUtils.LUCENE_VERSION));
        }

        private Language(final String value, final String alternate, final Analyzer analyzer) {
            this.value = value;
            locale = CoercionHelper.coerce(Locale.class, value);
            this.alternate = alternate;
            this.analyzer = analyzer;
        }

        public String getAlternate() {
            return alternate;
        }

        public Analyzer getAnalyzer() {
            return analyzer;
        }

        public Locale getLocale() {
            return locale;
        }

        @Override
        public String getValue() {
            return value;
        }
    }

    /**
     * Determines which field will be displayed on a given result list
     * @author luis
     */
    public static enum MemberResultDisplay {
        USERNAME, NAME;

        public String getProperty() {
            return name().toLowerCase();
        }
    }

    /**
     * Contains possible number formats
     * @author luis
     */
    public static enum NumberLocale {
        PERIOD_AS_DECIMAL(Locale.US), COMMA_AS_DECIMAL(Locale.GERMANY);
        private final Locale locale;

        private NumberLocale(final Locale locale) {
            this.locale = locale;
        }

        public Locale getLocale() {
            return locale;
        }
    }

    /**
     * Indicates the number precision for the local currency
     * @author luis
     */
    public static enum Precision implements IntValuedEnum {
        ZERO(0), ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6);
        private final int value;

        private Precision(final int value) {
            this.value = value;
        }

        public MathContext getMathContext() {
            return new MathContext(value, RoundingMode.HALF_UP);
        }

        @Override
        public int getValue() {
            return value;
        }
    }

    /**
     * Defines the sort order for member lists.
     * @author rinke
     */
    public static enum SortOrder {
        CHRONOLOGICAL, ALPHABETICAL;

    }

    /**
     * Possible patterns for time representation
     * @author luis
     */
    public static enum TimePattern implements StringValuedEnum {
        HH24_MM_SS("HH:mm:ss", "HH(24):MM:SS", "%H:%M:%S"), HH24_MM("HH:mm", "HH(24):MM", "%H:%M"), HH12_MM_SS("hh:mm:ss a", "HH(12):MM:SS AM/PM", "%I:%M:%S %p"), HH12_MM("hh:mm a", "HH(12):MM AM/PM", "%I:%M %p");
        private final String value;
        private final String description;
        private final String calendarFormat;

        private TimePattern(final String value, final String description, final String calendarFormat) {
            this.value = value;
            this.description = description;
            this.calendarFormat = calendarFormat;
        }

        public String getCalendarFormat() {
            return calendarFormat;
        }

        public String getDescription() {
            return description;
        }

        @Override
        public String getValue() {
            return value;
        }
    }

    /**
     * Contains parameters for generating a transaction number. The number is composed by 3 parts: a prefix, the padded identifier and a suffix. Both
     * prefix and suffix may contain a date pattern between #, so, for example, if the prefix TN#yy-MM# is processed on january, 2006, the result
     * would be TN06-01. To generate a # character, it must be escaped with \\#
     */
    public static class TransactionNumber implements Serializable {
        private static final long serialVersionUID = 7841112633641561669L;
        private String            prefix;
        private int               padLength;
        private String            suffix;
        private boolean           enabled;

        public TransactionNumber() {
        }

        public TransactionNumber(final String prefix, final int padLength, final String suffix) {
            this.prefix = prefix;
            this.padLength = padLength;
            this.suffix = suffix;
        }

        /**
         * Generates the number for a given transaction id and date
         */
        public String generate(final Long id, final Calendar date) {
            if (padLength <= 0) {
                throw new IllegalStateException("Cannot generate a transaction number with padLength " + padLength);
            }
            if (id == null || id <= 0L) {
                throw new IllegalArgumentException("Invalid id: " + id);
            }
            return MessageProcessingHelper.processDate(prefix, date) + StringUtils.leftPad(String.valueOf(id), padLength, '0') + MessageProcessingHelper.processDate(suffix, date);
        }

        public int getPadLength() {
            return padLength;
        }

        public String getPrefix() {
            return prefix;
        }

        public String getSuffix() {
            return suffix;
        }

        public boolean isEnabled() {
            return enabled;
        }

        public boolean isValid() {
            return padLength > 0;
        }

        public void setEnabled(final boolean enabled) {
            this.enabled = enabled;
        }

        public void setPadLength(final int padLength) {
            this.padLength = padLength;
        }

        public void setPrefix(final String prefix) {
            this.prefix = prefix;
        }

        public void setSuffix(final String suffix) {
            this.suffix = suffix;
        }
    }

    public static final int                               MAX_PRECISION                   = 10;
    public static final int                               BIG_DECIMAL_DIVISION_PRECISION  = 6;

    private static final long                             serialVersionUID                = -2880060408820762157L;
    private String                                        applicationName                 = "Cyclos";
    private String                                        cyclosId                        = null;
    private String                                        applicationUsername             = "The Administration";
    private Language                                      language                        = Language.ENGLISH;
    private String                                        rootUrl                         = "http://localhost:8080/cyclos";

    private String                                        charset                         = "UTF-8";
    private NumberLocale                                  numberLocale                    = NumberLocale.COMMA_AS_DECIMAL;
    private Precision                                     precision                       = Precision.TWO;
    private Precision                                     highPrecision                   = Precision.FOUR;
    private DecimalInputMethod                            decimalInputMethod              = DecimalInputMethod.RTL;
    private DatePattern                                   datePattern                     = DatePattern.DD_MM_YYYY_SLASH;
    private TimePattern                                   timePattern                     = TimePattern.HH24_MM_SS;
    // Initially empty, meaning no time zone computation
    private TimeZone                                      timeZone;
    private String                                        containerUrl;

    private int                                           maxIteratorResults              = 1000;
    private int                                           maxPageResults                  = 15;
    private int                                           maxAjaxResults                  = 8;
    private int                                           maxUploadSize                   = 5;
    private FileUnits                                     maxUploadUnits                  = FileUnits.MEGA_BYTES;
    private int                                           maxImageWidth                   = 800;
    private int                                           maxImageHeight                  = 600;
    private int                                           maxThumbnailWidth               = 100;
    private int                                           maxThumbnailHeight              = 100;
    private int                                           schedulingHour                  = 0;
    private int                                           schedulingMinute                = 0;
    private String                                        transferListenerClass;

    private boolean                                       csvUseHeader                    = true;
    private CsvRecordSeparator                            csvRecordSeparator              = CsvRecordSeparator.LF;

    private CsvValueSeparator                             csvValueSeparator               = CsvValueSeparator.COMMA;
    private CsvStringQuote                                csvStringQuote                  = CsvStringQuote.DOUBLE_QUOTE;
    private TransactionNumber                             transactionNumber               = null;
    private String                                        sendSmsWebServiceUrl            = null;
    private String                                        smsChannelName                  = null;

    private Long                                          smsCustomFieldId                = null;
    private boolean                                       smsEnabled                      = false;

    private boolean                                       emailRequired                   = true;

    private boolean                                       emailUnique                     = true;

    private int                                           referenceLevels                 = 5;
    private TimePeriod                                    brokeringExpirationPeriod       = new TimePeriod(0, TimePeriod.Field.YEARS);
    private TimePeriod                                    deleteMessagesOnTrashAfter      = new TimePeriod(30, TimePeriod.Field.DAYS);

    private TimePeriod                                    deletePendingRegistrationsAfter = new TimePeriod(7, TimePeriod.Field.DAYS);
    private SortOrder                                     memberSortOrder                 = SortOrder.CHRONOLOGICAL;
    private MemberResultDisplay                           memberResultDisplay             = MemberResultDisplay.USERNAME;
    private TextFormat                                    adDescriptionFormat             = TextFormat.RICH;
    private TextFormat                                    messageFormat                   = TextFormat.RICH;
    private TimePeriod                                    maxChargebackTime               = new TimePeriod(1, TimePeriod.Field.MONTHS);
    private String                                        chargebackDescription           = "Chargeback for payment at #date#\n#description#";
    private boolean                                       showCountersInAdCategories      = true;
    private transient Locale                              locale;
    private transient DecimalFormat                       decimalFormat;

    private transient NumberConverter<Long>               longConverter;
    private transient DecimalFormat                       highPrecisionDecimalFormat;

    private transient DecimalFormatSymbols                decimalSymbols;
    private transient NumberConverter<BigDecimal>         numberConverter;
    private transient NumberConverter<BigDecimal>         highPrecisionConverter;
    private final transient NumberConverter<BigDecimal>[] specificPrecisionConverters     = new NumberConverter[MAX_PRECISION + 1];
    private transient AmountConverter                     amountConverter;
    private transient CalendarConverter                   rawDateConverter;
    private transient CalendarConverter                   dateConverter;
    private transient CalendarConverter                   dateConverterForGraphs;
    private transient CalendarConverter                   dateTimeConverter;
    private transient CalendarConverter                   timeConverter;
    private transient Integer                             maxUploadBytes;
    private transient MathContext                         mathContext;
    private transient Comparator<Element>                 memberComparator;
    private transient Dimensions                          maxImageDimensions;
    private transient Dimensions                          maxThumbnailDimensions;
    private transient List<Level>                         referenceLevelList;

    public TextFormat getAdDescriptionFormat() {
        return adDescriptionFormat;
    }

    public AmountConverter getAmountConverter() {
        if (amountConverter == null) {
            amountConverter = new AmountConverter(getDecimalFormat());
        }
        return amountConverter;
    }

    public AmountConverter getAmountConverter(final String unitsPattern) {
        return new AmountConverter(getDecimalFormat(), unitsPattern);
    }

    public String getApplicationName() {
        return applicationName;
    }

    public String getApplicationUsername() {
        return applicationUsername;
    }

    public TimePeriod getBrokeringExpirationPeriod() {
        return brokeringExpirationPeriod;
    }

    public String getChargebackDescription() {
        return chargebackDescription;
    }

    public String getCharset() {
        return charset;
    }

    public String getContainerUrl() {
        return containerUrl;
    }

    public CsvRecordSeparator getCsvRecordSeparator() {
        return csvRecordSeparator;
    }

    public CsvStringQuote getCsvStringQuote() {
        return csvStringQuote;
    }

    public CsvValueSeparator getCsvValueSeparator() {
        return csvValueSeparator;
    }

    public String getCyclosId() {
        return cyclosId;
    }

    public CalendarConverter getDateConverter() {
        if (dateConverter == null) {
            dateConverter = new CalendarConverter(datePattern.value, timeZone);
        }
        return dateConverter;
    }

    /**
     * gets a CalendarConverter which is derived from the normal CalendarConverter, but without the year. To be used for cewolf graphs as Strings
     * along the x-axis, where normal date labels with a year would be to long and make the x-axis too packed with labels.
     * @return a CalendarConverter without the year.
     */
    public CalendarConverter getDateConverterForGraphs() {
        if (dateConverterForGraphs == null) {
            // strip the year from the existing normal dateConverter
            final String[] formatParts = datePattern.order.getFormatParts();
            final ArrayList<String> formatPartsForGraph = new ArrayList<String>(formatParts.length - 1);
            for (final String formatPart : formatParts) {
                if (formatPart.toLowerCase().indexOf('y') == -1) {
                    formatPartsForGraph.add(formatPart);
                }
            }
            String[] formatPartsForGraphArray = new String[formatPartsForGraph.size()];
            formatPartsForGraphArray = formatPartsForGraph.toArray(formatPartsForGraphArray);
            // use array for creating new pattern value without years
            final String patternValue = StringUtils.join(formatPartsForGraphArray, datePattern.separator);
            dateConverterForGraphs = new CalendarConverter(patternValue, timeZone);
        }
        return dateConverterForGraphs;
    }

    public DatePattern getDatePattern() {
        return datePattern;
    }

    public CalendarConverter getDateTimeConverter() {
        if (dateTimeConverter == null) {
            dateTimeConverter = new CalendarConverter(datePattern.value + " " + timePattern.value, timeZone);
        }
        return dateTimeConverter;
    }

    public DecimalInputMethod getDecimalInputMethod() {
        return decimalInputMethod;
    }

    public DecimalFormatSymbols getDecimalSymbols() {
        if (decimalSymbols == null) {
            decimalSymbols = new DecimalFormatSymbols(numberLocale.getLocale());
        }
        return decimalSymbols;
    }

    public TimePeriod getDeleteMessagesOnTrashAfter() {
        return deleteMessagesOnTrashAfter;
    }

    public TimePeriod getDeletePendingRegistrationsAfter() {
        return deletePendingRegistrationsAfter;
    }

    public Precision getHighPrecision() {
        return highPrecision;
    }

    public NumberConverter<BigDecimal> getHighPrecisionConverter() {
        if (highPrecisionConverter == null) {
            highPrecisionConverter = new NumberConverter<BigDecimal>(BigDecimal.class, getHighPrecisionDecimalFormat());
        }
        return highPrecisionConverter;
    }

    public DecimalFormat getHighPrecisionDecimalFormat() {
        if (highPrecisionDecimalFormat == null) {
            highPrecisionDecimalFormat = getDecimalFormat(highPrecision.value);
        }
        return highPrecisionDecimalFormat;
    }

    public Language getLanguage() {
        return language;
    }

    public Locale getLocale() {
        if (locale == null) {
            locale = language.getLocale();
        }
        return locale;
    }

    public NumberConverter<Long> getLongConverter() {
        if (longConverter == null) {
            longConverter = new NumberConverter<Long>(Long.class, getDecimalFormat(0));
        }
        return longConverter;
    }

    public MathContext getMathContext() {
        if (mathContext == null) {
            mathContext = new MathContext(BIG_DECIMAL_DIVISION_PRECISION);
        }
        return mathContext;
    }

    public int getMaxAjaxResults() {
        return maxAjaxResults;
    }

    public TimePeriod getMaxChargebackTime() {
        return maxChargebackTime;
    }

    public Dimensions getMaxImageDimensions() {
        if (maxImageDimensions == null) {
            maxImageDimensions = new Dimensions(maxImageWidth, maxImageHeight);
        }
        return maxImageDimensions;
    }

    public int getMaxImageHeight() {
        return maxImageHeight;
    }

    public int getMaxImageWidth() {
        return maxImageWidth;
    }

    public int getMaxIteratorResults() {
        return maxIteratorResults;
    }

    public int getMaxPageResults() {
        return maxPageResults;
    }

    public Dimensions getMaxThumbnailDimensions() {
        if (maxThumbnailDimensions == null) {
            maxThumbnailDimensions = new Dimensions(maxThumbnailWidth, maxThumbnailHeight);
        }
        return maxThumbnailDimensions;
    }

    public int getMaxThumbnailHeight() {
        return maxThumbnailHeight;
    }

    public int getMaxThumbnailWidth() {
        return maxThumbnailWidth;
    }

    public int getMaxUploadBytes() {
        if (maxUploadBytes == null) {
            maxUploadBytes = maxUploadUnits.calculate(maxUploadSize);
        }
        return maxUploadBytes;
    }

    public int getMaxUploadSize() {
        return maxUploadSize;
    }

    public FileUnits getMaxUploadUnits() {
        return maxUploadUnits;
    }

    public Comparator<Element> getMemberComparator() {
        if (memberComparator == null) {
            memberComparator = new BeanComparator(memberResultDisplay.getProperty());
        }
        return memberComparator;
    }

    public MemberResultDisplay getMemberResultDisplay() {
        return memberResultDisplay;
    }

    public SortOrder getMemberSortOrder() {
        return memberSortOrder;
    }

    public TextFormat getMessageFormat() {
        return messageFormat;
    }

    public NumberConverter<BigDecimal> getNumberConverter() {
        if (numberConverter == null) {
            numberConverter = new NumberConverter<BigDecimal>(BigDecimal.class, getDecimalFormat());
        }
        return numberConverter;
    }

    public NumberConverter<BigDecimal> getNumberConverterForPrecision(int precision) {
        precision = Math.max(0, Math.min(precision, MAX_PRECISION));
        if (specificPrecisionConverters[precision] == null) {
            specificPrecisionConverters[precision] = new NumberConverter<BigDecimal>(BigDecimal.class, getDecimalFormat(precision));
        }
        return specificPrecisionConverters[precision];
    }

    public NumberLocale getNumberLocale() {
        return numberLocale;
    }

    public Precision getPrecision() {
        return precision;
    }

    public CalendarConverter getRawDateConverter() {
        if (rawDateConverter == null) {
            rawDateConverter = new CalendarConverter(datePattern.value);
        }
        return rawDateConverter;
    }

    public List<Level> getReferenceLevelList() {
        if (referenceLevelList == null) {
            Level[] levels;
            if (referenceLevels == 3) {
                levels = new Level[] { Level.GOOD, Level.NEUTRAL, Level.BAD };
            } else {
                levels = Level.values();
            }
            referenceLevelList = Collections.unmodifiableList(Arrays.asList(levels));
        }
        return referenceLevelList;
    }

    public int getReferenceLevels() {
        return referenceLevels;
    }

    public String getRootUrl() {
        return rootUrl;
    }

    public int getSchedulingHour() {
        return schedulingHour;
    }

    public int getSchedulingMinute() {
        return schedulingMinute;
    }

    public String getSendSmsWebServiceUrl() {
        return sendSmsWebServiceUrl;
    }

    public String getSmsChannelName() {
        return smsChannelName;
    }

    public Long getSmsCustomFieldId() {
        return smsCustomFieldId;
    }

    public CalendarConverter getTimeConverter() {
        if (timeConverter == null) {
            timeConverter = new CalendarConverter(timePattern.value, timeZone);
        }
        return timeConverter;
    }

    public TimePattern getTimePattern() {
        return timePattern;
    }

    public TimeZone getTimeZone() {
        return timeZone;
    }

    public TransactionNumber getTransactionNumber() {
        return transactionNumber;
    }

    public String getTransferListenerClass() {
        return transferListenerClass;
    }

    public UnitsConverter getUnitsConverter(final String pattern) {
        return new UnitsConverter(pattern, getNumberConverter().getNumberFormat());
    }

    public boolean isCsvUseHeader() {
        return csvUseHeader;
    }

    public boolean isEmailRequired() {
        return emailRequired;
    }

    public boolean isEmailUnique() {
        return emailUnique;
    }

    public boolean isShowCountersInAdCategories() {
        return showCountersInAdCategories;
    }

    /**
     * Returns whether the SMS commands are enabled
     */
    public boolean isSmsCommandEnabled() {
        return StringUtils.isNotEmpty(smsChannelName);
    }

    public boolean isSmsEnabled() {
        return smsEnabled;
    }

    public BigDecimal round(final BigDecimal number) {
        if (number == null) {
            return null;
        } else {
            return number.setScale(getPrecision().getValue(), RoundingMode.HALF_UP);
        }
    }

    public BigDecimal roundHighPrecision(final BigDecimal number) {
        if (number == null) {
            return null;
        } else {
            return number.setScale(getHighPrecision().getValue(), RoundingMode.HALF_UP);
        }
    }

    public void setAdDescriptionFormat(final TextFormat adDescriptionFormat) {
        this.adDescriptionFormat = adDescriptionFormat;
    }

    public void setApplicationName(final String applicationName) {
        this.applicationName = applicationName;
    }

    public void setApplicationUsername(final String applicationUsername) {
        this.applicationUsername = applicationUsername;
    }

    public void setBrokeringExpirationPeriod(final TimePeriod brokeringExpirationPeriod) {
        this.brokeringExpirationPeriod = brokeringExpirationPeriod;
    }

    public void setChargebackDescription(final String chargebackDescription) {
        this.chargebackDescription = chargebackDescription;
    }

    public void setContainerUrl(final String containerUrl) {
        this.containerUrl = containerUrl;
    }

    public void setCsvRecordSeparator(final CsvRecordSeparator csvRecordSeparator) {
        this.csvRecordSeparator = csvRecordSeparator;
    }

    public void setCsvStringQuote(final CsvStringQuote csvStringQuote) {
        this.csvStringQuote = csvStringQuote;
    }

    public void setCsvUseHeader(final boolean csvUseHeader) {
        this.csvUseHeader = csvUseHeader;
    }

    public void setCsvValueSeparator(final CsvValueSeparator csvValueSeparator) {
        this.csvValueSeparator = csvValueSeparator;
    }

    public void setCyclosId(final String cyclosId) {
        this.cyclosId = cyclosId;
    }

    public void setDatePattern(final DatePattern datePattern) {
        this.datePattern = datePattern;
        invalidateDateParams();
    }

    public void setDecimalInputMethod(final DecimalInputMethod decimalInputMethod) {
        this.decimalInputMethod = decimalInputMethod;
    }

    public void setDeleteMessagesOnTrashAfter(final TimePeriod deleteMessagesOnTrashAfter) {
        this.deleteMessagesOnTrashAfter = deleteMessagesOnTrashAfter;
    }

    public void setDeletePendingRegistrationsAfter(final TimePeriod deletePendingRegistrationsAfter) {
        this.deletePendingRegistrationsAfter = deletePendingRegistrationsAfter;
    }

    public void setEmailRequired(final boolean mailRequired) {
        emailRequired = mailRequired;
    }

    public void setEmailUnique(final boolean emailUnique) {
        this.emailUnique = emailUnique;
    }

    public void setHighPrecision(final Precision highPrecision) {
        this.highPrecision = highPrecision;
    }

    public void setLanguage(final Language language) {
        this.language = language == null ? Language.ENGLISH : language;
        invalidateLanguageParams();
    }

    public void setLocale(final Locale locale) {
        this.locale = locale;
    }

    public void setMaxAjaxResults(final int maxAjaxResults) {
        this.maxAjaxResults = maxAjaxResults;
    }

    public void setMaxChargebackTime(final TimePeriod maxChargebackTime) {
        this.maxChargebackTime = maxChargebackTime;
    }

    public void setMaxImageHeight(final int maxImageHeight) {
        this.maxImageHeight = maxImageHeight;
    }

    public void setMaxImageWidth(final int maxImageWidth) {
        this.maxImageWidth = maxImageWidth;
    }

    public void setMaxIteratorResults(final int maxIteratorPageResults) {
        maxIteratorResults = maxIteratorPageResults;
    }

    public void setMaxPageResults(final int pageSize) {
        maxPageResults = pageSize;
    }

    public void setMaxThumbnailHeight(final int maxThumbnailHeight) {
        this.maxThumbnailHeight = maxThumbnailHeight;
    }

    public void setMaxThumbnailWidth(final int maxThumbnailWidth) {
        this.maxThumbnailWidth = maxThumbnailWidth;
    }

    public void setMaxUploadSize(final int maxUploadSize) {
        this.maxUploadSize = maxUploadSize;
        maxUploadBytes = null;
    }

    public void setMaxUploadUnits(final FileUnits maxUploadUnits) {
        this.maxUploadUnits = maxUploadUnits;
        maxUploadBytes = null;
    }

    public void setMemberResultDisplay(final MemberResultDisplay adResultDisplay) {
        memberResultDisplay = adResultDisplay;
        memberComparator = null;
    }

    public void setMemberSortOrder(final SortOrder memberSortOrder) {
        this.memberSortOrder = memberSortOrder;
    }

    public void setMessageFormat(final TextFormat messageFormat) {
        this.messageFormat = messageFormat;
    }

    public void setNumberLocale(final NumberLocale numberLocale) {
        this.numberLocale = numberLocale;
        invalidateNumberParams();
    }

    public void setPrecision(final Precision precision) {
        this.precision = precision;
        invalidateNumberParams();
    }

    public void setReferenceLevels(final int referenceLevels) {
        this.referenceLevels = referenceLevels;
        referenceLevelList = null;
    }

    public void setRootUrl(final String rootUrl) {
        this.rootUrl = rootUrl;
    }

    public void setSchedulingHour(final int schedulingHour) {
        this.schedulingHour = schedulingHour;
    }

    public void setSchedulingMinute(final int schedulingMinute) {
        this.schedulingMinute = schedulingMinute;
    }

    public void setSendSmsWebServiceUrl(final String sendSmsWebServiceUrl) {
        this.sendSmsWebServiceUrl = sendSmsWebServiceUrl;
    }

    public void setShowCountersInAdCategories(final boolean showCountersInAdCategories) {
        this.showCountersInAdCategories = showCountersInAdCategories;
    }

    public void setSmsChannelName(final String smsChannelName) {
        this.smsChannelName = smsChannelName;
    }

    public void setSmsCustomFieldId(final Long smsCustomFieldId) {
        this.smsCustomFieldId = smsCustomFieldId;
    }

    public void setSmsEnabled(final boolean smsEnabled) {
        this.smsEnabled = smsEnabled;
    }

    public void setTimePattern(final TimePattern timePattern) {
        this.timePattern = timePattern;
        invalidateDateParams();
    }

    public void setTimeZone(final TimeZone timeZone) {
        this.timeZone = timeZone;
        invalidateDateParams();
    }

    public void setTransactionNumber(final TransactionNumber transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public void setTransferListenerClass(final String transferListenerClass) {
        this.transferListenerClass = transferListenerClass;
    }

    public BigDecimal truncate(final BigDecimal number) {
        if (number == null) {
            return null;
        } else {
            return number.setScale(getPrecision().getValue(), RoundingMode.FLOOR);
        }
    }

    private DecimalFormat getDecimalFormat() {
        if (decimalFormat == null) {
            decimalFormat = getDecimalFormat(precision.value);
        }
        return decimalFormat;
    }

    private DecimalFormat getDecimalFormat(final int precision) {
        String pattern = "#,##0";
        if (precision > 0) {
            pattern += "." + StringUtils.repeat("0", precision);
        }
        return new DecimalFormat(pattern, getDecimalSymbols());
    }

    /**
     * Invalidates calculated objects using date paramters
     */
    private void invalidateDateParams() {
        rawDateConverter = null;
        dateConverter = null;
        dateTimeConverter = null;
        dateConverterForGraphs = null;
        timeConverter = null;
    }

    /**
     * Invalidates calculated objects using language parameters
     */
    private void invalidateLanguageParams() {
        locale = null;
    }

    /**
     * Invalidates calculated objects using number parameters
     */
    private void invalidateNumberParams() {
        longConverter = null;
        decimalFormat = null;
        amountConverter = null;
        highPrecisionConverter = null;
        numberConverter = null;
        decimalSymbols = null;
        mathContext = null;
        for (int i = 0; i < specificPrecisionConverters.length; i++) {
            specificPrecisionConverters[i] = null;
        }
    }

}
