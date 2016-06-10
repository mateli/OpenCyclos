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
package nl.strohalm.cyclos.controls.members.brokering;

import java.util.Map;

import nl.strohalm.cyclos.controls.BaseQueryForm;
import nl.strohalm.cyclos.utils.binding.MapBean;

/**
 * Form used to edit a broker commission contract
 * @author Jefferson Magno
 */
public class EditBrokerCommissionContractForm extends BaseQueryForm {

    private static final long serialVersionUID = -7971382544032634526L;
    private long              brokerCommissionId;
    private long              brokerCommissionContractId;
    private long              memberId;

    public EditBrokerCommissionContractForm() {
        setBrokerCommissionContract("amount", new MapBean("type", "value"));
        setBrokerCommissionContract("period", new MapBean("begin", "end"));
    }

    public Map<String, Object> getBrokerCommissionContract() {
        return values;
    }

    public Object getBrokerCommissionContract(final String key) {
        return values.get(key);
    }

    public long getBrokerCommissionContractId() {
        return brokerCommissionContractId;
    }

    public long getBrokerCommissionId() {
        return brokerCommissionId;
    }

    public long getMemberId() {
        return memberId;
    }

    public void setBrokerCommissionContract(final Map<String, Object> map) {
        values = map;
    }

    public void setBrokerCommissionContract(final String key, final Object value) {
        values.put(key, value);
    }

    public void setBrokerCommissionContractId(final long brokerCommissionContractId) {
        this.brokerCommissionContractId = brokerCommissionContractId;
    }

    public void setBrokerCommissionId(final long brokerCommissionId) {
        this.brokerCommissionId = brokerCommissionId;
    }

    public void setMemberId(final long memberId) {
        this.memberId = memberId;
    }

}
