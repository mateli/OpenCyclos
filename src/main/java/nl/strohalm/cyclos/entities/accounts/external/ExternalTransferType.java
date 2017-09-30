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
package nl.strohalm.cyclos.entities.accounts.external;

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.utils.StringValuedEnum;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.util.Collection;

/**
 * Represents a type for an external transfer
 * @author luis
 */
@Cacheable
@Table(name = "external_transfer_types")
@javax.persistence.Entity
public class ExternalTransferType extends Entity {

    /**
     * An action to execute with this transfer type
     * @author luis
     */
    public static enum Action implements StringValuedEnum {
        IGNORE("I"), GENERATE_SYSTEM_PAYMENT("P"), GENERATE_MEMBER_PAYMENT("M"), DISCARD_LOAN("L"), CONCILIATE_PAYMENT("C");

        private final String value;

        private Action(final String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public static enum Relationships implements Relationship {
        ACCOUNT("account"), TRANSFER_TYPE("transferType"), TRANSFERS("transfers");
        private final String name;

        private Relationships(final String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    private static final long            serialVersionUID = 4958639810768991285L;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false) // unique-key="uk_account_code"
	private ExternalAccount              account;

    @Column(length = 50, nullable = false)
    private String                       name;

    @Column(columnDefinition = "text", length = Integer.MAX_VALUE)
    private String                       description;

    @Column(length = 20, nullable = false) // unique-key="uk_account_code"
    private String                       code;

    @Column(length = 1, nullable = false)
	private Action                       action;

    @ManyToOne
    @JoinColumn(name = "transfer_type_id")
	private TransferType                 transferType;

    @OneToMany(mappedBy = "type", cascade = CascadeType.REMOVE)
    @OrderBy("date desc")
	private Collection<ExternalTransfer> transfers;

	public ExternalAccount getAccount() {
        return account;
    }

    public Action getAction() {
        return action;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public Collection<ExternalTransfer> getTransfers() {
        return transfers;
    }

    public TransferType getTransferType() {
        return transferType;
    }

    public void setAccount(final ExternalAccount externalAccount) {
        account = externalAccount;
    }

    public void setAction(final Action action) {
        this.action = action;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setTransfers(final Collection<ExternalTransfer> transfers) {
        this.transfers = transfers;
    }

    public void setTransferType(final TransferType transferType) {
        this.transferType = transferType;
    }

    @Override
    public String toString() {
        return getId() + " - " + getName();
    }

}
