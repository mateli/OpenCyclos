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
package nl.strohalm.cyclos.webservices.payments;

import java.util.List;

import nl.strohalm.cyclos.webservices.model.FieldValueVO;
import nl.strohalm.cyclos.webservices.utils.ObjectHelper;

import org.apache.commons.lang.ArrayUtils;

/**
 * Parameters for making a new payment
 * @author luis
 */
public class PaymentParameters extends AbstractPaymentParameters {
    private static final long  serialVersionUID = 8364312939644892473L;
    private Long               transferTypeId;
    private Boolean            fromSystem       = false;
    private Boolean            toSystem         = false;
    private String             credentials;
    private List<FieldValueVO> customValues;
    /**
     * A list of field's internal names indicating the payer's FieldValueVOs to be returned for a successful payment.<br>
     * NOTE: This list will be taken into account ONLY for an incoming WS request through an unrestricted WS Client.
     * @see {@link nl.strohalm.cyclos.webservices.payments.PaymentResult} (<code>getTransfer().getFromMember().getFields()</code>)
     */
    private List<String>       fromMemberFieldsToReturn;

    /**
     * A list of field's internal names indicating the receiver's FieldValueVOs to be returned for a successful payment.<br>
     * NOTE: This list will be taken into account ONLY for an incoming WS request through an unrestricted WS Client.
     * @see {@link nl.strohalm.cyclos.webservices.payments.PaymentResult} (<code>getTransfer().getMember().getFields()</code>)
     */
    private List<String>       toMemberFieldsToReturn;

    /**
     * If true the member's account status will be set in the payment result.
     */
    private Boolean            returnStatus     = false;

    public String getCredentials() {
        return credentials;
    }

    public List<FieldValueVO> getCustomValues() {
        return customValues;
    }

    public List<String> getFromMemberFieldsToReturn() {
        return fromMemberFieldsToReturn;
    }

    public boolean getFromSystem() {
        return ObjectHelper.valueOf(fromSystem);
    }

    public boolean getReturnStatus() {
        return ObjectHelper.valueOf(returnStatus);
    }

    public List<String> getToMemberFieldsToReturn() {
        return toMemberFieldsToReturn;
    }

    public boolean getToSystem() {
        return ObjectHelper.valueOf(toSystem);
    }

    public Long getTransferTypeId() {
        return transferTypeId;
    }

    public void setCredentials(final String credentials) {
        this.credentials = credentials;
    }

    public void setCustomValues(final List<FieldValueVO> customValues) {
        this.customValues = customValues;
    }

    public void setFromMemberFieldsToReturn(final List<String> fromMemberFieldsToReturn) {
        this.fromMemberFieldsToReturn = fromMemberFieldsToReturn;
    }

    public void setFromSystem(final boolean fromSystem) {
        this.fromSystem = fromSystem;
    }

    public void setReturnStatus(final boolean returnStatus) {
        this.returnStatus = returnStatus;
    }

    public void setToMemberFieldsToReturn(final List<String> toMemberFieldsToReturn) {
        this.toMemberFieldsToReturn = toMemberFieldsToReturn;
    }

    public void setToSystem(final boolean toSystem) {
        this.toSystem = toSystem;
    }

    public void setTransferTypeId(final Long transferTypeId) {
        this.transferTypeId = transferTypeId;
    }

    @Override
    public String toString() {
        return "PaymentParameters [credentials=****" + ", customValues=" + customValues + ", fromMemberFields= " + ArrayUtils.toString(fromMemberFieldsToReturn) + ", toMemberFields= " + ArrayUtils.toString(toMemberFieldsToReturn) + ", fromSystem=" + fromSystem + ", returnStatus=" + returnStatus + ", toSystem=" + toSystem + ", transferTypeId=" + transferTypeId + ", " + super.toString() + "]";
    }
}
