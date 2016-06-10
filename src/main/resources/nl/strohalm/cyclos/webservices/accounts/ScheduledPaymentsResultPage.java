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
package nl.strohalm.cyclos.webservices.accounts;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import nl.strohalm.cyclos.webservices.model.ResultPage;
import nl.strohalm.cyclos.webservices.model.ScheduledPaymentVO;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Page results for account history transfers
 * @author luis
 */
public class ScheduledPaymentsResultPage extends ResultPage<ScheduledPaymentVO> {
    private static final long serialVersionUID = -186613342878700230L;

    public ScheduledPaymentsResultPage() {
    }

    public ScheduledPaymentsResultPage(final int currentPage, final int pageSize, final int totalCount, final List<ScheduledPaymentVO> elements) {
        super(currentPage, pageSize, totalCount, elements);
    }

    @JsonIgnore
    @XmlElement
    public List<ScheduledPaymentVO> getScheduledPayments() {
        return getElements();
    }

    public void setScheduledPayments(final List<ScheduledPaymentVO> scheduledPayments) {
        setElements(scheduledPayments);
    }

}
