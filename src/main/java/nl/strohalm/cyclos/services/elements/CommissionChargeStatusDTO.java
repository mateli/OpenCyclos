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
package nl.strohalm.cyclos.services.elements;

import nl.strohalm.cyclos.entities.accounts.fees.transaction.BrokerCommission;
import nl.strohalm.cyclos.entities.members.brokerings.BrokerCommissionContract;
import nl.strohalm.cyclos.entities.members.brokerings.BrokeringCommissionStatus;
import nl.strohalm.cyclos.utils.DataObject;

public class CommissionChargeStatusDTO extends DataObject {

    public static enum ChargeStatus {
        DEFAULT_COMMISSION, CONTRACT, NONE;
    }

    private static final long         serialVersionUID = 4615638915792905441L;

    private ChargeStatus              chargeStatus;
    private BrokerCommission          brokerCommission;
    private BrokeringCommissionStatus brokeringCommissionStatus;
    private BrokerCommissionContract  brokerCommissionContract;

    public BrokerCommission getBrokerCommission() {
        return brokerCommission;
    }

    public BrokerCommissionContract getBrokerCommissionContract() {
        return brokerCommissionContract;
    }

    public BrokeringCommissionStatus getBrokeringCommissionStatus() {
        return brokeringCommissionStatus;
    }

    public ChargeStatus getChargeStatus() {
        return chargeStatus;
    }

    public void setBrokerCommission(final BrokerCommission brokerCommission) {
        this.brokerCommission = brokerCommission;
    }

    public void setBrokerCommissionContract(final BrokerCommissionContract brokerCommissionContract) {
        this.brokerCommissionContract = brokerCommissionContract;
    }

    public void setBrokeringCommissionStatus(final BrokeringCommissionStatus brokeringCommissionStatus) {
        this.brokeringCommissionStatus = brokeringCommissionStatus;
    }

    public void setChargeStatus(final ChargeStatus chargeStatus) {
        this.chargeStatus = chargeStatus;
    }

}
