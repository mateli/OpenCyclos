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
package nl.strohalm.cyclos.taglibs;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import nl.strohalm.cyclos.entities.reports.StatisticalNumber;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.services.settings.SettingsService;
import nl.strohalm.cyclos.utils.Amount;
import nl.strohalm.cyclos.utils.SpringHelper;
import nl.strohalm.cyclos.utils.conversion.CardNumberConverter;
import nl.strohalm.cyclos.utils.conversion.CoercionHelper;
import nl.strohalm.cyclos.utils.conversion.Converter;
import nl.strohalm.cyclos.utils.conversion.NumberConverter;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Renders a formatted data
 * @author luis
 * @author rinke (the StatisticalNumber part)
 */
public class FormatTag extends TagSupport {

    private static final long serialVersionUID = 5262848042770106863L;

    private Amount            amount;
    private Calendar          rawDate;
    private Calendar          date;
    private Calendar          dateTime;
    private Calendar          time;
    private Number            number;
    private Long              bytes;
    private Object            defaultValue;
    private Integer           precision;
    private boolean           forceSignal;
    private String            unitsPattern;
    private String            cardNumberPattern;

    @Override
    public int doEndTag() throws JspException {
        String out = "";
        final LocalSettings localSettings = getLocalSettings();
        if (number != null) {
            if (number instanceof Double || number instanceof BigDecimal || number instanceof Float) {
                final BigDecimal theNumber = CoercionHelper.coerce(BigDecimal.class, number);
                NumberConverter<BigDecimal> numberConverter;
                if (StringUtils.isNotEmpty(unitsPattern)) {
                    numberConverter = localSettings.getUnitsConverter(unitsPattern);
                } else if (precision == null) {
                    numberConverter = localSettings.getNumberConverter();
                } else {
                    numberConverter = localSettings.getNumberConverterForPrecision(precision);
                }
                out = numberConverter.toString(theNumber);
                if (forceSignal && theNumber.compareTo(BigDecimal.ZERO) > 0) {
                    out = "+" + out;
                }
            } else if (number instanceof StatisticalNumber) {
                out = convertStatisticalNumber((StatisticalNumber) number);
            } else if (number instanceof BigInteger && cardNumberPattern != null) {
                out = new CardNumberConverter(cardNumberPattern).toString((BigInteger) number);
            } else {
                final NumberConverter<Long> longConverter = localSettings.getLongConverter();
                final Long theNumber = CoercionHelper.coerce(Long.class, number);
                out = longConverter.toString(theNumber);
            }
        } else if (amount != null) {
            Converter<Amount> converter;
            if (StringUtils.isEmpty(unitsPattern)) {
                converter = localSettings.getAmountConverter();
            } else {
                converter = localSettings.getAmountConverter(unitsPattern);
            }
            out = converter.toString(amount);
        } else if (rawDate != null) {
            out = localSettings.getRawDateConverter().toString(rawDate);
        } else if (date != null) {
            out = localSettings.getDateConverter().toString(date);
        } else if (dateTime != null) {
            out = localSettings.getDateTimeConverter().toString(dateTime);
        } else if (time != null) {
            out = localSettings.getTimeConverter().toString(time);
        } else if (bytes != null) {
            out = FileUtils.byteCountToDisplaySize(bytes);
        } else {
            out = defaultValue == null ? null : defaultValue.toString();
        }
        try {
            if (StringUtils.isNotEmpty(out)) {
                pageContext.getOut().print(out);
            }
        } catch (final IOException e) {
            throw new JspException(e);
        } finally {
            release();
        }
        return EVAL_PAGE;
    }

    public Amount getAmount() {
        return amount;
    }

    public Long getBytes() {
        return bytes;
    }

    public String getCardNumberPattern() {
        return cardNumberPattern;
    }

    public Calendar getDate() {
        return date;
    }

    public Calendar getDateTime() {
        return dateTime;
    }

    public Object getDefault() {
        return defaultValue;
    }

    public Object getNumber() {
        return number;
    }

    public Integer getPrecision() {
        return precision;
    }

    public Calendar getTime() {
        return time;
    }

    public String getUnitsPattern() {
        return unitsPattern;
    }

    public boolean isForceSignal() {
        return forceSignal;
    }

    @Override
    public void release() {
        super.release();
        number = null;
        amount = null;
        rawDate = null;
        date = null;
        dateTime = null;
        time = null;
        bytes = null;
        defaultValue = null;
        precision = null;
        forceSignal = false;
        cardNumberPattern = null;
    }

    public void setAmount(final Amount amount) {
        this.amount = amount;
    }

    public void setBytes(final Long bytes) {
        this.bytes = bytes;
    }

    public void setCardNumberPattern(final String cardNumberPattern) {
        this.cardNumberPattern = cardNumberPattern;
    }

    public void setDate(final Calendar date) {
        this.date = date;
    }

    public void setDateTime(final Calendar dateTime) {
        this.dateTime = dateTime;
    }

    public void setDefault(final Object defaultValue) {
        this.defaultValue = defaultValue;
    }

    public void setForceSignal(final boolean forceSignal) {
        this.forceSignal = forceSignal;
    }

    public void setNumber(final Object number) {
        this.number = CoercionHelper.coerce(Number.class, number);
    }

    public void setPrecision(final Integer precision) {
        this.precision = precision;
    }

    public void setRawDate(final Calendar rawDate) {
        this.rawDate = rawDate;
    }

    public void setTime(final Calendar time) {
        this.time = time;
    }

    public void setUnitsPattern(final String unitsPattern) {
        this.unitsPattern = unitsPattern;
    }

    private String convertStatisticalNumber(final StatisticalNumber number) {
        final Integer lPrecision = (precision == null) ? new Integer(number.getPrecision()) : precision;
        final NumberConverter<BigDecimal> numberConverter = getLocalSettings().getNumberConverterForPrecision(lPrecision);
        if (number.isNull()) {
            return "-";
        }
        String data = numberConverter.toString(new BigDecimal(number.floatValue()));
        if (number.isPercentage()) {
            data = data + "%";
        } else if (number.isPvalue() && number.floatValue() < 0.05) {
            data = "<b>" + data + "</b>";
        } else if (number.hasErrorBar()) {
            if (number.hasSymmetricalErrorBar()) {
                final String error = numberConverter.toString(new BigDecimal(number.getError().floatValue()));
                data = data + "&nbsp;&#177;&nbsp;" + error;
            } else {
                final String lower = numberConverter.toString(new BigDecimal(number.getLowerBound().floatValue()));
                final String upper = numberConverter.toString(new BigDecimal(number.getUpperBound().floatValue()));
                data = data + "&nbsp;&nbsp;(" + lower + " - " + upper + ")";
            }
        }
        return data;
    }

    private LocalSettings getLocalSettings() {
        final SettingsService settingsService = SpringHelper.bean(pageContext.getServletContext(), SettingsService.class);
        final LocalSettings settings = settingsService.getLocalSettings();
        return settings;
    }

}
