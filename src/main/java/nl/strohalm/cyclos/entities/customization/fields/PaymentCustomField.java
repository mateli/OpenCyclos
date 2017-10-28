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
package nl.strohalm.cyclos.entities.customization.fields;

import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.utils.StringValuedEnum;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.Collection;

/**
 * A custom field for payments
 * @author luis
 */
@DiscriminatorValue("pmt")
@javax.persistence.Entity
public class PaymentCustomField extends CustomField {

    public static enum Access implements StringValuedEnum {
        NONE("N"), FROM_ACCOUNT("F"), TO_ACCOUNT("T"), BOTH_ACCOUNTS("B"), DESTINATION_MEMBER("D");

        private final String value;

        private Access(final String value) {
            this.value = value;
        }

        @Override
        public String getValue() {
            return value;
        }
    }

    public static enum Relationships implements Relationship {
        TRANSFER_TYPE("transferType"), LINKED_TRANSFER_TYPES("linkedTransferTypes");
        private final String name;

        private Relationships(final String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    private static final long        serialVersionUID = 958467435523383262L;

    @Column(name = "payment_enabled")
    private boolean                  enabled          = true;

    @ManyToOne
    @JoinColumn(name = "transfer_type_id")
	private TransferType             transferType;

    @Column(name = "payment_search_access", length = 1)
	private Access                   searchAccess     = Access.NONE;

    @Column(name = "payment_list_access", length = 1)
	private Access                   listAccess       = Access.NONE;

    @ManyToMany(mappedBy = "customFields")
	private Collection<TransferType> linkedTransferTypes;

	public Collection<TransferType> getLinkedTransferTypes() {
        return linkedTransferTypes;
    }

    public Access getListAccess() {
        return listAccess;
    }

    public Access getSearchAccess() {
        return searchAccess;
    }

    public TransferType getTransferType() {
        return transferType;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }

    public void setLinkedTransferTypes(final Collection<TransferType> linkedTransferTypes) {
        this.linkedTransferTypes = linkedTransferTypes;
    }

    public void setListAccess(final Access listAccess) {
        this.listAccess = listAccess;
    }

    public void setSearchAccess(final Access searchAccess) {
        this.searchAccess = searchAccess;
    }

    public void setTransferType(final TransferType transferType) {
        this.transferType = transferType;
    }
}
