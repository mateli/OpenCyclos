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
package nl.strohalm.cyclos.entities.reports;

/**
 * A convenient way to store the numerical results of the statistics. As the presentation of statistical results may take various forms, such as "3.12
 * +/- 1.76", "12" or "34%". This class can handle all these formats. It does not provide a toString method, as the settings with the application's
 * NumberFormat are not easily accessible from cyclos3. The rendering in jsp's is taken care of in the FormatTag.java class.
 * @author Rinke
 * 
 */
public class StatisticalNumber extends Number implements Comparable<StatisticalNumber> {

    private static final long serialVersionUID = -7277480165783349984L;

    /**
     * A factory method for the creation of a pValue which could not be created due to the fact that n-values were too small.
     * @return a StatisitcalNumber with invalid pvalue.
     */
    public static StatisticalNumber createNullPvalue() {
        final StatisticalNumber sn = new StatisticalNumber(0);
        sn.isNull = true;
        sn.pvalue = true;
        return sn;
    }

    /**
     * a factory method for the creation of numbers representing percentages.
     * @param value a Double representing the percentage. This parameter may be null, meaning that the percentage cannot be calculated (for example in
     * case of growth from 0 to 0).
     * @return a StatisticalNumber which will be interpreted as a percentage, that is: with percentage set to true, and a precision of 0 digits after
     * the decimal point.
     */
    public static StatisticalNumber createPercentage(final Double value) {
        StatisticalNumber statisticalNumber = null;
        if (value == null) {
            statisticalNumber = new StatisticalNumber(0);
            statisticalNumber.isNull = true;
        } else {
            statisticalNumber = new StatisticalNumber(value);
        }
        statisticalNumber.percentage = true;
        return statisticalNumber;
    }

    /**
     * a factory method for the creation of numbers representing percentages. Is an overloaded version of the single parameter version. Calculates the
     * difference from value2 to value1.<br>
     * Example:
     * 
     * <pre>
     * if value2 = 500, and value1 = 750, then the result is a
     * StatisticalNumber representing 50%, because the growth from 500 to 750 is
     * 50%.
     * </pre>
     * 
     * @param value1 - a Number representing value1. Accepts null, but then the result is also null.
     * @param value2 - a Number representing value2. Accepts null, but then te result is also null.
     * @return a StatisticalNumber which will be interpreted as a percentage, that is: with percentage set to true, and a precision of 0 digits after
     * the decimal point.
     */
    public static StatisticalNumber createPercentage(final Number value1, final Number value2) {
        if (value1 instanceof StatisticalNumber) {
            final StatisticalNumber sn1 = (StatisticalNumber) value1;
            if (sn1.getValue() == null || sn1.isNull) {
                return StatisticalNumber.createPercentage(null);
            }
        }
        if (value2 instanceof StatisticalNumber) {
            final StatisticalNumber sn2 = (StatisticalNumber) value2;
            if (sn2.getValue() == null || sn2.isNull) {
                return StatisticalNumber.createPercentage(null);
            }
        }
        if (value1 == null || value2 == null) {
            return StatisticalNumber.createPercentage(null);
        }
        if (value2.floatValue() == 0) {
            return StatisticalNumber.createPercentage(null);
        } else {
            final double percentage = 100 * (value1.floatValue() - value2.floatValue()) / value2.floatValue();
            return StatisticalNumber.createPercentage(percentage);
        }
    }

    /**
     * A factory method for the creation of numbers representing p-values.
     * @param value a double representing the p-value
     * @return a StatisticalNumber which will be interpreted as a p-value, that is: with isPvalue set to true, and a precision of 3 digits after the
     * decimal point.
     */
    public static StatisticalNumber createPvalue(final double value) {
        final StatisticalNumber statisticalNumber = new StatisticalNumber(value);
        statisticalNumber.pvalue = true;
        statisticalNumber.precision = 3;
        return statisticalNumber;
    }

    // //////////////// other methods //////////////////////////
    /**
     * allows for scaling of any Number, which is (for example) division by 1000.
     * @param oldNumber a Number to be scaled. Can take any Number subclass.
     * @param scaleFactor a double indicating the number by which the values should be divided, for example 1000
     * @return a new scaled Number. If the oldNumber param was...
     * <ul>
     * <li><b>null</b> then a null is returned.
     * <li><b>a StatisticalNumber: </b>
     * <ul>
     * <li>if indicating a pvalue, then the oldNumber is returned.
     * <li>if indicating a percentage, then the oldNumber is returned.
     * <li>if indicating another StatisticalNumber, then value and error are scaled, but precision is the same.
     * </ul>
     * <li><b>any other Number</b> then a scaled Double is returned.
     * </ul>
     */
    public static Number scale(final Number oldNumber, final double scaleFactor) {
        if (oldNumber == null) {
            return null;
        }
        Number newNumber;
        if (oldNumber instanceof StatisticalNumber) {
            final StatisticalNumber statisticalOldNumber = (StatisticalNumber) oldNumber;
            if (statisticalOldNumber.isNull) {
                return new StatisticalNumber();
            }
            if (statisticalOldNumber.pvalue | statisticalOldNumber.percentage) {
                return oldNumber;
            }
            final double newValue = statisticalOldNumber.doubleValue() / scaleFactor;
            if (statisticalOldNumber.error == null && statisticalOldNumber.lowerBound != null) {
                final Double newLower = statisticalOldNumber.lowerBound / scaleFactor;
                final Double newUpper = statisticalOldNumber.upperBound / scaleFactor;
                newNumber = new StatisticalNumber(newValue, newLower, newUpper, statisticalOldNumber.precision);
            } else {
                final Double newError = (statisticalOldNumber.error == null) ? null : statisticalOldNumber.error / scaleFactor;
                newNumber = new StatisticalNumber(newValue, newError, statisticalOldNumber.precision);
            }
        } else {
            newNumber = oldNumber.doubleValue() / scaleFactor;
        }
        return newNumber;
    }

    /**
     * a Double storing the value of the number.
     */
    private final Double value;
    /**
     * a double storing the error.
     */
    private Double       error;
    /**
     * a byte indicating the precision, that is: the number of digits after the decimal point or comma.
     */
    private byte         precision;

    // ///////////// CONSTRUCTORS and semi-constructors /////////////////////////////

    /**
     * the lower limit of the confidence interval. So value - error = lowerbound
     */
    private Double       lowerBound;

    /**
     * the upper limit of the confidence interval. So value + error = upperbound
     */
    private Double       upperBound;

    /**
     * true if the number represents a p-value.
     */
    private boolean      pvalue;

    /**
     * true if the number represents a percentage.
     */
    private boolean      percentage;

    /**
     * true if a percentage or p-value connot be calculated
     */
    private boolean      isNull;

    /**
     * null constructor, creates a null value
     */
    public StatisticalNumber() {
        value = null;
        isNull = true;
    }

    /**
     * Most simple constructor, creating numbers presented as integers. To be used for the most simple form, a single int like "12".
     * 
     * @param value a double which is the value to be assigned to the object
     */
    public StatisticalNumber(final double value) {
        this.value = new Double(value);
    }

    /**
     * Constructor for simple decimal numbers like "6.234".
     * @param value a double being the value of the object.
     * @param precision the number of digits after the decimal point.
     */
    public StatisticalNumber(final double value, final byte precision) {
        this.value = new Double(value);
        this.precision = precision;
    }

    /**
     * Constructor for a StatisticalNumber with a value and an error indication. This constructor must be used for numbers taking the form "12.12 �
     * 2.76"
     * @param value a double indicating the value
     * @param error a Double indicating the error, so the number after the � sign. This is the only param which can take the value null.
     * @param precision a byte indicating the number of digits after the decimal point
     */
    public StatisticalNumber(final double value, final Double error, final byte precision) {
        this.value = new Double(value);
        this.error = error;
        this.precision = precision;
    }

    /**
     * Constructor for a StatisticalNumber with a value and error indication, specifically designed for <b>a-symmetrical</b> error bars. In this
     * case, the error field will be left empty, and only the lowerBound and upperBound fields will be set. The number will be printed as "12.12 (10.0 -
     * 17.2)". For this type of StatisticalNumber, hasErrorBars() returns true, but hasSymmetricalErrorBars returns false
     * 
     * @param value a double indicating the value
     * @param lowerBound a Double indicating the lower value of the error range
     * @param upperBound a Double indicating the upper value of the error range
     * @param precision a byte indicating the number of digits after the decimal point.
     */
    public StatisticalNumber(final double value, final Double lowerBound, final Double upperBound, final byte precision) {
        if (lowerBound == null || upperBound == null) {
            throw new IllegalArgumentException("lower and upper Bounds may not be null with this constructor");
        }
        this.value = new Double(value);
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.precision = precision;
    }

    /**
     * special null allowing constructor
     * 
     * @param nullAllowedValue as a Double
     */
    public StatisticalNumber(final Double nullAllowedValue) {
        value = nullAllowedValue;
        isNull = value == null;
    }

    /**
     * special null allowing constructor
     * 
     * @param nullAllowedValue as an Integer
     */
    public StatisticalNumber(final Integer nullAllowedValue) {
        if (nullAllowedValue == null) {
            value = null;
            isNull = true;
        } else {
            value = new Double(nullAllowedValue.intValue());
        }
    }

    /**
     * implementation of the standard compareTo method of the Comparable interface.
     * 
     * @param o the object to compare to
     * @return -1, 0 or 1 if this object is smaller, equal to, or greater than the param.
     */
    public int compareTo(final StatisticalNumber o) {
        if (this == o) {
            return 0;
        }
        if ((o == null)) {
            return -1;
        }
        return (int) (doubleValue() - o.doubleValue());
    }

    // ////////////// XValues /////////////////////////////
    /**
     * gets the doubleValue of value
     */
    @Override
    public double doubleValue() {
        return value.doubleValue();
    }

    /**
     * gets the floatValue of value
     */
    @Override
    public float floatValue() {
        return value.floatValue();
    }

    /**
     * gets the error
     */
    public Double getError() {
        return error;
    }

    /**
     * gets the Lowest value of the error bar. Returns null if no error is defined.
     * @return lower limit of the error bar
     */
    public Double getLowerBound() {
        if (lowerBound == null) {
            return (error == null) ? null : (value - error);
        }
        return lowerBound;
    }

    /**
     * gets the precision
     */
    public byte getPrecision() {
        return precision;
    }

    // ////////////////// getters / setters /////////////////////

    /**
     * gets the highest value of the error bar. Returns null if no error is defined
     * @return upper limit of the error bar.
     */
    public Double getUpperBound() {
        if (upperBound == null) {
            return (error == null) ? null : (value + error);
        }
        return upperBound;
    }

    /**
     * gets the value
     */
    public Double getValue() {
        return value;
    }

    /**
     * tests if the statisticalNumber has data. By definition it does not have data when:
     * <ul>
     * <li> it is marked as <code>null</code>
     * <li> its value is <code>null</code>
     * <li> its value is 0
     * </ul>
     * 
     * @return <code>false</code> if it does not have data according to the above definition, <code>true</code> otherwise.
     */
    public boolean hasEnoughData() {
        if (!isNull() && getValue() != null && getValue().doubleValue() != 0) {
            return true;
        }
        return false;
    }

    /**
     * @return true if a confidence interval is defined for this number.
     */
    public boolean hasErrorBar() {
        return (getLowerBound() != null);
    }

    /**
     * @return true if a confidence interval is defined, and if this confidence interval is symmetrical. If no errorBar is defined, it returns false.
     */
    public boolean hasSymmetricalErrorBar() {
        if (hasErrorBar()) {
            return (getError() != null);
        }
        return false;
    }

    /**
     * gets the intValue of value
     */
    @Override
    public int intValue() {
        return value.intValue();
    }

    /**
     * checks if the value cannot be calculated (used in case a percentage cannot be calculated, because the nominator is 0).
     * @return true if the value cannot be calculated
     */
    public boolean isNull() {
        return isNull;
    }

    /**
     * checks if the object represents a percentage
     * @return a boolean indicating if this represents a percentage
     */
    public boolean isPercentage() {
        return percentage;
    }

    /**
     * checks if the object represents a p-value
     * @return a boolean indicating if this represents a p-value
     */
    public boolean isPvalue() {
        return pvalue;
    }

    /**
     * gets the longValue of value
     */
    @Override
    public long longValue() {
        return value.longValue();
    }

    /**
     * sets the precision
     * @param precision
     */
    public void setPrecision(final byte precision) {
        this.precision = precision;
    }
}
