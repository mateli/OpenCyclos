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
package nl.strohalm.cyclos.entities.accounts.external.filemapping;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Calendar;
import java.util.Collection;

import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.utils.StringValuedEnum;
import nl.strohalm.cyclos.utils.conversion.BooleanConverter;
import nl.strohalm.cyclos.utils.conversion.CalendarConverter;
import nl.strohalm.cyclos.utils.conversion.Converter;
import nl.strohalm.cyclos.utils.conversion.FixedLengthNumberConverter;
import nl.strohalm.cyclos.utils.conversion.NumberConverter;

import org.apache.commons.lang.StringUtils;

/**
 * A file mapping that has mapped fields
 * @author luis
 */
public abstract class FileMappingWithFields extends FileMapping {

    /**
     * A format for parsing numbers
     * @author luis
     */
    public enum NumberFormat implements StringValuedEnum {
        FIXED_POSITION("F"), WITH_SEPARATOR("S");

        private final String value;

        private NumberFormat(final String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public static enum Relationships implements Relationship {
        FIELDS("fields");
        private final String name;

        private Relationships(final String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public static final NumberFormat DEFAULT_NUMBER_FORMAT         = NumberFormat.FIXED_POSITION;
    public static final Character    DEFAULT_NEGATIVE_AMOUNT_VALUE = new Character('-');
    public static final Integer      DEFAULT_DECIMAL_PLACES        = new Integer(2);
    public static final Character    DEFAULT_DECIMAL_SEPARATOR     = new Character('.');
    public static final String       DEFAULT_DATE_FORMAT           = "yyyy-MM-dd";

    private static final long        serialVersionUID              = -6761459914402653154L;
    private NumberFormat             numberFormat;
    private String                   negativeAmountValue;
    private Integer                  decimalPlaces;
    private Character                decimalSeparator;
    private String                   dateFormat;
    private Collection<FieldMapping> fields;

    /**
     * Returns a converter for date
     */
    public Converter<Calendar> getDateConverter() {
        final String format = dateFormat.toLowerCase().replace('m', 'M');
        return new CalendarConverter(format);
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public Integer getDecimalPlaces() {
        return decimalPlaces;
    }

    public Character getDecimalSeparator() {
        return decimalSeparator;
    }

    public Collection<FieldMapping> getFields() {
        return fields;
    }

    /**
     * Returns a converter for negateAmount
     */
    public Converter<Boolean> getNegateAmountConverter() {
        return new BooleanConverter(String.valueOf(negativeAmountValue));
    }

    public String getNegativeAmountValue() {
        return negativeAmountValue;
    }

    /**
     * Returns a converter for amount
     */
    public Converter<BigDecimal> getNumberConverter() {
        if (numberFormat == NumberFormat.FIXED_POSITION) {
            return new FixedLengthNumberConverter<BigDecimal>(BigDecimal.class, decimalPlaces);
        } else {
            final DecimalFormatSymbols symbols = new DecimalFormatSymbols();
            symbols.setDecimalSeparator(decimalSeparator);
            symbols.setGroupingSeparator('!');

            final DecimalFormat format = new DecimalFormat("0." + StringUtils.repeat("0", decimalPlaces), symbols);
            format.setGroupingUsed(false);
            return new NumberConverter<BigDecimal>(BigDecimal.class, format);
        }
    }

    public NumberFormat getNumberFormat() {
        return numberFormat;
    }

    public void setDateFormat(final String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public void setDecimalPlaces(final Integer decimalPlaces) {
        this.decimalPlaces = decimalPlaces;
    }

    public void setDecimalSeparator(final Character decimalSeparator) {
        this.decimalSeparator = decimalSeparator;
    }

    public void setFields(final Collection<FieldMapping> fields) {
        this.fields = fields;
    }

    public void setNegativeAmountValue(final String negativeAmountValue) {
        this.negativeAmountValue = negativeAmountValue;
    }

    public void setNumberFormat(final NumberFormat numberFormat) {
        this.numberFormat = numberFormat;
    }

}
