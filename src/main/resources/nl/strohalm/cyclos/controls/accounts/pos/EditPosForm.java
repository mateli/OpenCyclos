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
package nl.strohalm.cyclos.controls.accounts.pos;

import java.util.Map;

import nl.strohalm.cyclos.controls.BaseBindingForm;
import nl.strohalm.cyclos.utils.binding.MapBean;

/**
 * 
 * @author rodrigo
 */
public class EditPosForm extends BaseBindingForm {

    private static final long serialVersionUID = -2550794532422649762L;
    // POS Pk
    private long              id;
    // POS identifier set by the admin/broker
    private String            posId;
    private long              memberId;
    private String            operation;
    private String            assignTo;
    private String            pin;

    public EditPosForm() {
        final MapBean memberPos = new MapBean("id", "allowMakePayment", "date", "maxSchedulingPayments", "numberOfCopies", "resultPageSize", "member", "status", "posName", "posPin");
        setPos("memberPos", memberPos);
    }

    public String getAssignTo() {
        return assignTo;
    }

    public long getId() {
        return id;
    }

    public long getMemberId() {
        return memberId;
    }

    public String getOperation() {
        return operation;
    }

    public String getPin() {
        return pin;
    }

    public Map<String, Object> getPos() {
        return values;
    }

    public Object getPos(final String key) {
        return values.get(key);
    }

    public String getPosId() {
        return posId;
    }

    public void setAssignTo(final String assignTo) {
        this.assignTo = assignTo;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public void setMemberId(final long memberId) {
        this.memberId = memberId;
    }

    public void setOperation(final String operation) {
        this.operation = operation;
    }

    public void setPin(final String pin) {
        this.pin = pin;
    }

    public void setPos(final Map<String, Object> map) {
        values = map;
    }

    public void setPos(final String key, final Object value) {
        values.put(key, value);
    }

    public void setPosId(final String posId) {
        this.posId = posId;
    }
}
