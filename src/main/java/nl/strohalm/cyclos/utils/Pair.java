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

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * A pair of two values
 * @author luis
 * 
 * @param <T1> The first value type
 * @param <T2> The second value type
 */
public class Pair<T1, T2> implements Cloneable, Serializable {
    private static final long serialVersionUID = 1934715538721883614L;

    /**
     * Factory class for a pair. Useful to reduce generics declaration, as generic types are inferred
     */
    public static <T1, T2> Pair<T1, T2> of(final T1 first, final T2 second) {
        return new Pair<T1, T2>(first, second);
    }

    private T1 first;
    private T2 second;

    public Pair() {
    }

    public Pair(final T1 first, final T2 second) {
        this.first = first;
        this.second = second;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Pair<T1, T2> clone() {
        try {
            return (Pair<T1, T2>) super.clone();
        } catch (final CloneNotSupportedException e) {
            return null;
        }
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof Pair<?, ?>)) {
            return false;
        }
        final Pair<?, ?> pair = (Pair<?, ?>) obj;
        return new EqualsBuilder().append(first, pair.first).append(second, pair.second).isEquals();
    }

    public T1 getFirst() {
        return first;
    }

    public T2 getSecond() {
        return second;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(first).append(second).toHashCode();
    }

    public void setFirst(final T1 first) {
        this.first = first;
    }

    public void setSecond(final T2 second) {
        this.second = second;
    }
}
