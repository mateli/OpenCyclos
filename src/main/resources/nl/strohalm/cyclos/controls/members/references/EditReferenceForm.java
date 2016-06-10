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
package nl.strohalm.cyclos.controls.members.references;

import java.util.Map;

import nl.strohalm.cyclos.controls.BaseBindingForm;

/**
 * Form used to edit a reference
 * @author luis
 */
public class EditReferenceForm extends BaseBindingForm {
    private static final long serialVersionUID = 4672427146129336419L;
    private long              referenceId;
    private long              memberId;
    private long              transferId;
    private long              scheduledPaymentId;

    public long getMemberId() {
        return memberId;
    }

    public Map<String, Object> getReference() {
        return values;
    }

    public Object getReference(final String key) {
        return values.get(key);
    }

    public long getReferenceId() {
        return referenceId;
    }

    public long getScheduledPaymentId() {
        return scheduledPaymentId;
    }

    public long getTransferId() {
        return transferId;
    }

    public void setMemberId(final long memberId) {
        this.memberId = memberId;
    }

    public void setReference(final Map<String, Object> map) {
        values = map;
    }

    public void setReference(final String key, final Object value) {
        values.put(key, value);
    }

    public void setReferenceId(final long id) {
        referenceId = id;
    }

    public void setScheduledPaymentId(final long scheduledPaymentId) {
        this.scheduledPaymentId = scheduledPaymentId;
    }

    public void setTransferId(final long transferId) {
        this.transferId = transferId;
    }
}
