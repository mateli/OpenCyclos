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
package nl.strohalm.cyclos.controls.payments;

import org.apache.struts.action.ActionForm;

/**
 * Form used to show the details of a successful payment
 * @author luis
 */
public class SuccessfulPaymentForm extends ActionForm {
    private static final long serialVersionUID = -4695928614018865648L;
    private long              transferId;
    private int               count;
    private String            selectMember;
    private String            from;

    public int getCount() {
        return count;
    }

    public String getFrom() {
        return from;
    }

    public String getSelectMember() {
        return selectMember;
    }

    public long getTransferId() {
        return transferId;
    }

    public void setCount(final int count) {
        this.count = count;
    }

    public void setFrom(final String from) {
        this.from = from;
    }

    public void setSelectMember(final String selectMember) {
        this.selectMember = selectMember;
    }

    public void setTransferId(final long transferId) {
        this.transferId = transferId;
    }
}
