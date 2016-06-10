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
package nl.strohalm.cyclos.webservices.model;

import java.util.Calendar;
import java.util.List;

import javax.xml.bind.annotation.XmlType;

import nl.strohalm.cyclos.webservices.utils.ObjectHelper;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Represents a scheduled payment from the point of view of an account
 * 
 * @author luis
 */
@XmlType(name = "transfer")
public class ScheduledPaymentVO extends BasePaymentVO {

    private static final long                   serialVersionUID = 66232228828594425L;
    private List<ScheduledPaymentInstallmentVO> installments;
    private Boolean                             open             = false;

    @JsonIgnore
    @Override
    public String getFormattedProcessDate() {
        return super.getFormattedProcessDate();
    }

    public List<ScheduledPaymentInstallmentVO> getInstallments() {
        return installments;
    }

    public boolean getOpen() {
        return ObjectHelper.valueOf(open);
    }

    @JsonIgnore
    @Override
    public Calendar getProcessDate() {
        return super.getProcessDate();
    }

    @JsonIgnore
    @Override
    public PaymentStatusVO getStatus() {
        return super.getStatus();
    }

    public void setInstallments(final List<ScheduledPaymentInstallmentVO> installments) {
        this.installments = installments;
    }

    public void setOpen(final boolean open) {
        this.open = open;
    }

    @Override
    public String toString() {
        if (fromMember != null || fromSystemAccountName != null) {
            return "ScheduledPaymentVO [amount=" + amount + ", fields=" + fields + ", formattedAmount=" + formattedAmount + ", formattedDate=" + formattedDate + ", formattedProcessDate=" + formattedProcessDate + ", fromMember=" + fromMember + ", toMember=" + member + ", fromSystemAccountName=" + fromSystemAccountName + ", toSystemAccountName=" + systemAccountName + ", transferType=" + transferType + ", installments=" + installments + "]";
        } else {
            return "ScheduledPaymentVO [amount=" + amount + ", fields=" + fields + ", formattedAmount=" + formattedAmount + ", formattedDate=" + formattedDate + ", formattedProcessDate=" + formattedProcessDate + ", member=" + member + ", systemAccountName=" + systemAccountName + ", transferType=" + transferType + ", installments=" + installments + "]";
        }
    }
}
