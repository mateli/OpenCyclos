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

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;

import nl.strohalm.cyclos.entities.settings.LocalSettings;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Represents a percentage or fixed value
 * @author luis
 */
public class Amount implements Serializable, Cloneable {

    public static enum Type implements StringValuedEnum {
        PERCENTAGE("P"), FIXED("F");
        public static Type getFromValue(final String value) {
            for (final Type type : values()) {
                if (type.getValue().equals(value)) {
                    return type;
                }
            }
            return null;
        }

        private final String value;

        private Type(final String value) {
            this.value = value;
        }

        // TODO JAVADOC
        public BigDecimal apply(final BigDecimal number, final BigDecimal amount) {
            if (number == null || amount == null) {
                return number;
            }
            final MathContext mathContext = new MathContext(LocalSettings.BIG_DECIMAL_DIVISION_PRECISION);
            if (this == FIXED) {
                return amount;
            } else {
                return (number.multiply(amount).divide(new BigDecimal(100), mathContext));
            }
        }

        public String getValue() {
            return value;
        }
    }

    private static final long serialVersionUID = 6344417251664586114L;

    public static Amount fixed(final BigDecimal value) {
        final Amount amount = new Amount();
        amount.setType(Type.FIXED);
        amount.setValue(value);
        return amount;
    }

    public static Amount percentage(final BigDecimal value) {
        final Amount amount = new Amount();
        amount.setType(Type.PERCENTAGE);
        amount.setValue(value);
        return amount;
    }

    private Type       type;
    private BigDecimal value;

    public Amount() {
    }

    public Amount(final BigDecimal value, final Type type) {
        this.value = value;
        this.type = type;
    }

    // TODO JAVADOC
    public BigDecimal add(final BigDecimal number) {
        final BigDecimal applied = apply(number);
        if (applied == null) {
            return null;
        }
        return applied.add(number);
    }

    // TODO JAVADOC
    public BigDecimal apply(final BigDecimal number) {
        if (value == null || type == null || number == null) {
            return number;
        }
        return type.apply(number, value);
    }

    @Override
    public Amount clone() {
        try {
            return (Amount) super.clone();
        } catch (final CloneNotSupportedException e) {
            return null;
        }
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof Amount)) {
            return false;
        }
        final Amount amount = (Amount) obj;
        return new EqualsBuilder().append(value, amount.value).append(type, amount.type).isEquals();
    }

    public Type getType() {
        return type;
    }

    public BigDecimal getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(value).append(type).toHashCode();
    }

    public boolean isPercentage() {
        return type == Type.PERCENTAGE;
    }

    public void setType(final Type type) {
        this.type = type;
    }

    public void setValue(final BigDecimal value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value + (type == Type.PERCENTAGE ? "%" : "");
    }
}
