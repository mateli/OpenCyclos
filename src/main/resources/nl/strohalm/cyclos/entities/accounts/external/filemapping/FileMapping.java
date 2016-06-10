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
package nl.strohalm.cyclos.entities.accounts.external.filemapping;

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.external.ExternalAccount;
import nl.strohalm.cyclos.utils.CustomObjectHandler;
import nl.strohalm.cyclos.utils.transactionimport.TransactionFileImport;

/**
 * Maps a file format to import transfers
 * @author luis
 */
public abstract class FileMapping extends Entity {

    /**
     * The nature of the file mapping
     * @author jefferson
     */
    public static enum Nature {
        CSV, CUSTOM;
    }

    public static enum Relationships implements Relationship {
        EXTERNAL_ACCOUNT("externalAccount");
        private final String name;

        private Relationships(final String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    private static final long serialVersionUID = 2240788293712329995L;

    private ExternalAccount   account;

    public ExternalAccount getAccount() {
        return account;
    }

    /**
     * Resolves a transaction file import to parse data
     */
    public abstract TransactionFileImport getImport(CustomObjectHandler customObjectHandler);

    /**
     * Returns the nature of the FileMapping
     */
    public abstract Nature getNature();

    public void setAccount(final ExternalAccount account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return getId() + " - " + getAccount();
    }

}
