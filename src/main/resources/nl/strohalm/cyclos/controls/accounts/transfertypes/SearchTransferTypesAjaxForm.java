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
package nl.strohalm.cyclos.controls.accounts.transfertypes;

import java.util.ArrayList;
import java.util.List;

import nl.strohalm.cyclos.controls.accounts.transfertypes.SearchTransferTypesAjaxAction.Options;

import org.apache.struts.action.ActionForm;

/**
 * Form for searching transfer types using Ajax
 * @author luis
 */
public class SearchTransferTypesAjaxForm extends ActionForm {

    private static final long serialVersionUID = 5873060040707670959L;

    private String            channel;
    private String            context;
    private String            fromOwnerId;
    private boolean           loanData;
    private boolean           direction;
    private boolean           scheduling;
    private String            currencyId;
    private String            toOwnerId;
    private String[]          fromAccountTypeId;
    private String[]          toAccountTypeId;
    private String[]          fromGroups;
    private String[]          toGroups;
    private String[]          fromOrToGroups;
    private String            fromNature;
    private String            toNature;
    private boolean           useBy;
    private boolean           ignoreGroup;
    private boolean           useFromGroup;
    private boolean           showCurrency;

    public String getChannel() {
        return channel;
    }

    public String getContext() {
        return context;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public String[] getFromAccountTypeId() {
        return fromAccountTypeId;
    }

    public String[] getFromGroups() {
        return fromGroups;
    }

    public String getFromNature() {
        return fromNature;
    }

    public String[] getFromOrToGroups() {
        return fromOrToGroups;
    }

    public String getFromOwnerId() {
        return fromOwnerId;
    }

    public Options[] getOptions() {
        final List<Options> options = new ArrayList<Options>();
        if (loanData) {
            options.add(Options.LOAN_DATA);
        }
        if (direction) {
            options.add(Options.DIRECTION);
        }
        if (scheduling) {
            options.add(Options.SCHEDULING);
        }
        if (showCurrency) {
            options.add(Options.CURRENCY);
        }
        return options.toArray(new Options[options.size()]);
    }

    public String[] getToAccountTypeId() {
        return toAccountTypeId;
    }

    public String[] getToGroups() {
        return toGroups;
    }

    public String getToNature() {
        return toNature;
    }

    public String getToOwnerId() {
        return toOwnerId;
    }

    public boolean isDirection() {
        return direction;
    }

    public boolean isIgnoreGroup() {
        return ignoreGroup;
    }

    public boolean isLoanData() {
        return loanData;
    }

    public boolean isScheduling() {
        return scheduling;
    }

    public boolean isShowCurrency() {
        return showCurrency;
    }

    public boolean isUseBy() {
        return useBy;
    }

    public boolean isUseFromGroup() {
        return useFromGroup;
    }

    public void setChannel(final String channel) {
        this.channel = channel;
    }

    public void setContext(final String context) {
        this.context = context;
    }

    public void setCurrencyId(final String currencyId) {
        this.currencyId = currencyId;
    }

    public void setDirection(final boolean direction) {
        this.direction = direction;
    }

    public void setFromAccountTypeId(final String[] fromAccountTypeId) {
        this.fromAccountTypeId = fromAccountTypeId;
    }

    public void setFromGroups(final String[] fromGroups) {
        this.fromGroups = fromGroups;
    }

    public void setFromNature(final String fromNature) {
        this.fromNature = fromNature;
    }

    public void setFromOrToGroups(final String[] fromOrToGroups) {
        this.fromOrToGroups = fromOrToGroups;
    }

    public void setFromOwnerId(final String fromOwnerId) {
        this.fromOwnerId = fromOwnerId;
    }

    public void setIgnoreGroup(final boolean ignoreGroup) {
        this.ignoreGroup = ignoreGroup;
    }

    public void setLoanData(final boolean loanData) {
        this.loanData = loanData;
    }

    public void setScheduling(final boolean scheduling) {
        this.scheduling = scheduling;
    }

    public void setShowCurrency(final boolean showCurrency) {
        this.showCurrency = showCurrency;
    }

    public void setToAccountTypeId(final String[] toAccountTypeId) {
        this.toAccountTypeId = toAccountTypeId;
    }

    public void setToGroups(final String[] toGroups) {
        this.toGroups = toGroups;
    }

    public void setToNature(final String toNature) {
        this.toNature = toNature;
    }

    public void setToOwnerId(final String toOwnerId) {
        this.toOwnerId = toOwnerId;
    }

    public void setUseBy(final boolean useBy) {
        this.useBy = useBy;
    }

    public void setUseFromGroup(final boolean useFromGroup) {
        this.useFromGroup = useFromGroup;
    }

}
