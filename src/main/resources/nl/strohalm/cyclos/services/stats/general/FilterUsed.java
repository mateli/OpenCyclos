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
package nl.strohalm.cyclos.services.stats.general;

import java.util.ArrayList;
import java.util.List;

/**
 * A value object capturing the use of filters. For each used filter, define an object of this class. To add a new filter type to be shown is very
 * simple:
 * <ol>
 * <li>add a type to the FilterType enum
 * <li>give the key for its column header right there when defining it
 * <li>Define a setFilter method version in StatisticalResultDTO that allows you to call and pass the filter from the Service.
 * </ol>
 * 
 * @author Rinke
 * 
 */
public class FilterUsed {

    /**
     * the type of the filter. See categories for explanation.
     * 
     */
    public enum FilterType {
        /** system account filter */
        SYSTEM_ACCOUNT("reports.stats.systemAccountFilter"),
        /** multiple group filter */
        GROUP("reports.stats.general.members"),
        /** payment filter selector with one possible selection result */
        PAYMENT("reports.stats.general.payments"),
        /** payment filter multi drop down */
        PAYMENTS(""),
        /** one period */
        PERIOD("reports.stats.general.period");

        /**
         * the description serves as the header key for the table.
         */
        String headerKey;

        private FilterType(final String key) {
            headerKey = key;
        }

        private String getHeaderKey() {
            return headerKey;
        }
    }

    /**
     * this determines if keys are used, or if the names are used. Keys must be looked up in the resource bundle, names not.
     */
    public enum FilterUse {
        /**
         * the filter is not relevant at all for this result, and it was not shown in the form either. This is the default value
         */
        DONT_SHOW,
        /**
         * the filter was not used for generating this result, but it was present on the form
         */
        NOT_USED,
        /**
         * the filter was used for generating this result, but the user did not select anything in it.
         */
        NO_SELECT,
        /**
         * the filter was used for generating this result, and the user did select an item. It was a filter where only one item could be selected.
         */
        ITEM,
        /**
         * the filter was used for generating this result, and the user did select something. It was a filter where more than one item could be
         * selected.
         */
        COLLECTION;
    }

    /**
     * pseudo-constructor for constructing an item in the "Filters used" box which declares the filter is not used. "Not used" in this context means,
     * that the filter was displayed on the form, but is not used for generating this dataset. If the filter was not used but is not relevant at all,
     * and wasn't even on the form, then don't use this construct and just do nothing.
     * 
     * @param filterType the <code>FilterType</code> which is not used.
     * @return a <code>FilterUsed</code> object for displaying "not used" in this box.
     */
    public static FilterUsed noFilterUsed(final FilterType filterType) {
        final FilterUsed filterUsed = new FilterUsed(filterType, FilterUse.NOT_USED);
        filterUsed.values.add("reports.stats.general.notUsed");
        return filterUsed;
    }

    /**
     * pseudo-constructor for the "nothing selected" filter selections, where one string is shown to represent the filter value.
     * 
     * @param filterType the <code>FilterType</code> of this filter.
     * @param nothingSelectedKey a <code>String</code> which describes the status when nothing is selected. Usually this will be something like "All
     * Payments" or "All Members". Remember that this must be the key for this String, as the strings itself reside in the language resource bundle.
     * @return a <code>FilterUsed</code> object for displaying messages like "All Payments" in the "Filters used" box.
     */
    public static FilterUsed nothingSelected(final FilterType filterType, final String nothingSelectedKey) {
        final FilterUsed filterUsed = new FilterUsed(filterType, FilterUse.NO_SELECT);
        filterUsed.values.add(nothingSelectedKey);
        return filterUsed;
    }

    private final FilterType   filterType;

    private final FilterUse    filterUse;

    private final List<String> values = new ArrayList<String>(1);

    /**
     * constructor for the default use of this class.
     * @param filterType
     * @param filterUse
     */
    private FilterUsed(final FilterType filterType, final FilterUse filterUse) {
        this.filterType = filterType;
        this.filterUse = filterUse;
    }

    /**
     * constructor to add a multi drop down selector filter to the "Used filters" overview below each graph or table.
     * 
     * @param filterType the <code>FilterType</code> of this Filter.
     * @param names a <code>List</code> of <code>String</code>s containing the names of the elements of the filter. These will ususally be the
     * paymentFilter names or group names selected in the filter. Note that these names are NOT considered to be keys for the language resource
     * bundle; they are printed "as is".
     */
    public FilterUsed(final FilterType filterType, final List<String> names) {
        this(filterType, FilterUse.COLLECTION);
        values.addAll(names);
    }

    /**
     * constructor for the normal use of filters, where only one item can be selected. The name of this item is shown in the "filters used" box below
     * each graph or table.
     * 
     * @param filterType the <code>FilterType</code> of this filter.
     * @param selectedName a <code>String</code> which is the name of the selected item. This is placed "as is" and not looked up in the language
     * resource bundle.
     */
    public FilterUsed(final FilterType filterType, final String selectedName) {
        this(filterType, FilterUse.ITEM);
        values.add(selectedName);
    }

    /**
     * ads blank rows to a list. Called from the <code>DataProducer</code>, in order to make sure all columns are of equal length.
     * 
     * @param rowsToAdd the number of blank rows to add to the value list.
     */
    public void addBlankRows(final int rowsToAdd) {
        for (int i = 0; i < rowsToAdd; i++) {
            values.add("&nbsp;");
        }
    }

    /**
     * replaces a resource bundle key in the values List to its message value. To be called from the <code>StatisticalDataProducer</code>.
     * @param key the key as it appears in the language resource bundle
     * @param message its corresponding value from that bundle.
     */
    public void changeKeyToValue(final String key, final String message) {
        final int index = values.indexOf(key);
        values.set(index, message);
    }

    public FilterUse getFilterUse() {
        return filterUse;
    }

    public String getHeaderKey() {
        return filterType.getHeaderKey();
    }

    public List<String> getValues() {
        return values;
    }

}
