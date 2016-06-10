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
package nl.strohalm.cyclos.utils.statistics;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import nl.strohalm.cyclos.utils.Pair;

/**
 * Class provides additional usefull list methods. It works like the standard general Math class: it has only static methods, and is final.
 * @author Rinke
 */
public final class ListOperations {

    /**
     * Make a List<Number> of a double[]
     * @param data a double[] to be converted to a List
     * @return an ArrayList<Number>
     */
    public static List<Number> arrayToList(final double[] data) {
        final List<Number> list = new ArrayList<Number>();
        for (final double element : data) {
            list.add(new Double(element));
        }
        return list;
    }

    /**
     * converts an input list of type List<Number> to List<Double>
     * 
     * @param list
     */
    public static List<Double> convertToDoubleList(final List<Number> list) {
        final List<Double> result = new ArrayList<Double>();
        final ListIterator<Number> it = list.listIterator();
        while (it.hasNext()) {
            result.add(it.next().doubleValue());
        }
        return result;
    }

    /**
     * gets the "element" belonging to an index from a List with Numbers, where the index can even be a broken (non-int) number, and where balanced
     * correction is applied
     */
    public static double getElementFromIndex(final List<Number> list, final double index) {
        final int lowerIndex = (int) Math.floor(index);
        int upperIndex = (int) Math.ceil(index);
        if (upperIndex > list.size() - 1) {
            upperIndex = list.size() - 1;
        }
        double endValue;
        if (lowerIndex == upperIndex) {
            endValue = ListOperations.getBalancedValue(list, lowerIndex);
        } else {
            final double lowerValue = ListOperations.getBalancedValue(list, lowerIndex);
            final double upperValue = ListOperations.getBalancedValue(list, upperIndex);
            endValue = lowerValue + ((upperValue - lowerValue) * (index - lowerIndex));
        }
        return endValue;
    }

    /**
     * gives the element with the highest value in the list.
     * @return null if the list was null or empty.
     */
    public static Number getMax(final List<Number> list) {
        if (list == null) {
            return null;
        }
        if (list.size() == 0) {
            return null;
        }
        final Iterator<Number> it = list.iterator();
        double max = it.next().doubleValue();
        while (it.hasNext()) {
            final double next = it.next().doubleValue();
            if (max < next) {
                max = next;
            }
        }
        return max;
    }

    /**
     * takes the second elements of a <code>Collection</code> of <code>Pair</code>s, and returns them in a List
     * 
     * @param <S> the type of the frist element of the <code>Pair</code>s in the input collection
     * @param <T> the type of the second element of the <code>Pair</code>s in the input collection
     * @param collection the input <code>Collection</code> with <code>Pair</code>s
     * @return a List with only the second elements of the <code>Pair</code>s.
     */
    public static <S, T> List<T> getSecondFromPairCollection(final Collection<Pair<S, T>> collection) {
        final List<T> seconds = new ArrayList<T>();
        for (final Pair<S, T> pair : collection) {
            final T t = pair.getSecond();
            seconds.add(t);
        }
        return seconds;
    }

    /**
     * takes the second elements of a <code>Collection</code> of <code>Pair</code>s, and returns them in a List. Same as
     * <code>getSecondFromPairCollection</code>, but returns a List<Number>, where <code>getSecondFromPairList</code> returns the type of second
     * element of the Pair
     * 
     * @param <S> the type of the first element of the <code>Pair</code>s in the input collection
     * @param <T> the type of the second element of the <code>Pair</code>s in the input collection, must extend <code>Number</code>.
     * @param collection the input <code>Collection</code> with <code>Pair</code>s
     * @return a List<Number> with only the second elements of the <code>Pair</code>s.
     */
    public static <S, T extends Number> List<Number> getSecondNumberFromPairCollection(final Collection<Pair<S, T>> collection) {
        final List<Number> seconds = new ArrayList<Number>();
        for (final Pair<S, T> pair : collection) {
            final T t = pair.getSecond();
            seconds.add(t);
        }
        return seconds;
    }

    /**
     * Make an array with doubles from a List<Number>
     * @param list a List with Numbers
     * @return an array with doubles
     */
    public static double[] listToArray(final List<Number> list) {
        final double[] array = new double[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i).doubleValue();
        }
        return array;
    }

    /**
     * Make an array with ints from a List<Number>
     * 
     * @param list a list with Numbers
     * @return an array with ints.
     */
    public static int[] listToIntArray(final List<Number> list) {
        final int[] array = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i).intValue();
        }
        return array;
    }

    /**
     * switches x and y of a matrix (= a 2 dimensional array), so that matrix[2][3] will become matrix[3][2]. Returns null when the size of first
     * dimension is 0.
     */
    @SuppressWarnings("unchecked")
    public static <T> T[][] transposeMatrix(final T[][] matrix) {
        if (matrix == null) {
            return null;
        }
        if (matrix.length == 0) {
            return null;
        }
        final int rows = matrix.length;
        final int cols = matrix[0].length;
        final T[][] result = (T[][]) Array.newInstance(matrix.getClass().getComponentType(), cols);
        for (int j = 0; j < cols; j++) {
            final T[] secondLevelArray = (T[]) Array.newInstance(matrix[0].getClass().getComponentType(), rows);
            result[j] = secondLevelArray;
        }
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[j][i] = matrix[i][j];
            }
        }
        return result;
    }

    /**
     * Calculates the balanced value belonging to an index. This means that case of "ties" for the retrieved value, result is calculated as a
     * proportional value regarding its position, giving a more accurate estimation. <br>
     * The value belonging to index 4 of {0,1,2,2,3,3,3,3,4} and of {2,3,3,3,3,4,5,6,7} are both 3, though in the first list, the first 3 is taken,
     * and in the second list, the last 3. As this is not "fair", all elements with value 3 are spread equaly over a range from 2.5 to 3.5, and then
     * the value is retrieved from this range via interpolation. In the first list, this gives a value of 2.625. The second list gives a value of
     * 3.375. The formula for this is: Vb = Vn - 0.5 + ([0.5 + i - i1 ]/m), where:
     * <ul>
     * <li>Vb = "balanced value", the outcome (so in our examples: 2.625 or 3.375)
     * <li>Vn = "nominal value", the value belonging to index 4 in the list (so in our examples: 3)
     * <li>n = the length of the list (in our examples: 9)
     * <li>i = the requested index number
     * <li>i1 = the index number of the first item in the ordered list having the same value as the nominal value Vn (in our examples: 4 and 1, as the
     * first 3 in the ranges are on the 4th and the 1st index).
     * <li>m = the number of items in the list having the same value as the nominal value Vn (in our examples: 4, as there are 4 items with value 3).
     * </ul>
     * <b>NOTE</b>: this balanced value approach works only with integer lists, as one must have an idea of "units" over which to spread the value. In
     * case of a list with doubles, should a 50, 50, 50, be spread over 45 - 55, over 49.5 - 50.5, or over 49.95 - 50.05?? With non integers, such an
     * approach is meaningless, and just the nominal values are returned.
     */
    private static double getBalancedValue(final List<Number> list, final int index) {
        final Number nominalValue = list.get(index);
        double result = nominalValue.doubleValue();
        if (nominalValue.getClass() == Integer.class) {
            final int firstItemWithValue = list.indexOf(nominalValue);
            final int numberOfItemsWithValue = list.lastIndexOf(nominalValue) + 1 - firstItemWithValue;
            if (numberOfItemsWithValue > 1) {
                final double correction = (0.5 + index - firstItemWithValue) / numberOfItemsWithValue;
                result = result - 0.5 + correction;
            }
        }
        return result;
    }

}
