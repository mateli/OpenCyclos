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
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.utils.FormatObject;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.util.Calendar;
import java.util.Collection;

/**
 * Contains a set of imported transfers from a file
 * @author luis
 */
@Table(name = "external_transfer_imports")
@javax.persistence.Entity
public class ExternalTransferImport extends Entity {

    public static enum Relationships implements Relationship {
        ACCOUNT("account"), BY("by"), TRANSFERS("transfers");
        private final String name;

        private Relationships(final String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    private static final long            serialVersionUID = -8862140825847721639L;

    @Column(nullable = false)
    private Calendar                     date;

    @ManyToOne
    @JoinColumn(name = "by_id", nullable = false)
	private Element                      by;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
	private ExternalAccount              account;

    @OneToMany(mappedBy = "transferImport", cascade = CascadeType.REMOVE)
    @OrderBy("date desc")
	private Collection<ExternalTransfer> transfers;

	public ExternalAccount getAccount() {
        return account;
    }

    public Element getBy() {
        return by;
    }

    public Calendar getDate() {
        return date;
    }

    public Collection<ExternalTransfer> getTransfers() {
        return transfers;
    }

    public void setAccount(final ExternalAccount account) {
        this.account = account;
    }

    public void setBy(final Element by) {
        this.by = by;
    }

    public void setDate(final Calendar date) {
        this.date = date;
    }

    public void setTransfers(final Collection<ExternalTransfer> transfers) {
        this.transfers = transfers;
    }

    @Override
    public String toString() {
        return getId() + ", account: " + account + ", date: " + FormatObject.formatObject(date);
    }

}
