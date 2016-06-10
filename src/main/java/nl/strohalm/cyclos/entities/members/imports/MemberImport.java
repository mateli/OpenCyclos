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
package nl.strohalm.cyclos.entities.members.imports;

import java.util.Calendar;
import java.util.Collection;

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.MemberAccountType;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Administrator;
import nl.strohalm.cyclos.utils.FormatObject;

/**
 * Contains data about a whole member import
 * 
 * @author luis
 */
public class MemberImport extends Entity {

    public static enum Relationships implements Relationship {
        BY("by"), GROUP("group"), ACCOUNT_TYPE("accountType"), INITIAL_DEBIT_TRANSFER_TYPE("initialDebitTransferType"), INITIAL_CREDIT_TRANSFER_TYPE("initialCreditTransferType");

        private final String name;

        private Relationships(final String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    private static final long          serialVersionUID = -284696929784000327L;
    private Administrator              by;
    private Calendar                   date;
    private MemberGroup                group;
    private MemberAccountType          accountType;
    private TransferType               initialDebitTransferType;
    private TransferType               initialCreditTransferType;
    private Collection<ImportedMember> members;

    public MemberAccountType getAccountType() {
        return accountType;
    }

    public Administrator getBy() {
        return by;
    }

    public Calendar getDate() {
        return date;
    }

    public MemberGroup getGroup() {
        return group;
    }

    public TransferType getInitialCreditTransferType() {
        return initialCreditTransferType;
    }

    public TransferType getInitialDebitTransferType() {
        return initialDebitTransferType;
    }

    public Collection<ImportedMember> getMembers() {
        return members;
    }

    public void setAccountType(final MemberAccountType accountType) {
        this.accountType = accountType;
    }

    public void setBy(final Administrator by) {
        this.by = by;
    }

    public void setDate(final Calendar date) {
        this.date = date;
    }

    public void setGroup(final MemberGroup group) {
        this.group = group;
    }

    public void setInitialCreditTransferType(final TransferType initialCreditType) {
        initialCreditTransferType = initialCreditType;
    }

    public void setInitialDebitTransferType(final TransferType initialDebitType) {
        initialDebitTransferType = initialDebitType;
    }

    public void setMembers(final Collection<ImportedMember> members) {
        this.members = members;
    }

    @Override
    public String toString() {
        return getId() + " at " + FormatObject.formatObject(date);
    }

}
