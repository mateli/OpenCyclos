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

import java.util.Collection;

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.MemberAccountType;
import nl.strohalm.cyclos.entities.accounts.SystemAccountType;
import nl.strohalm.cyclos.entities.accounts.external.filemapping.FileMapping;

/**
 * Represents an external account for payments that backs internal Cyclos payments
 * @author luis
 */
public class ExternalAccount extends Entity {

    public static enum Relationships implements Relationship {
        MEMBER_ACCOUNT_TYPE("memberAccountType"), SYSTEM_ACCOUNT_TYPE("systemAccountType"), FILE_MAPPING("fileMapping"), TRANSFERS("transfers"), TYPES("types"), IMPORTS("imports");
        private final String name;

        private Relationships(final String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    private static final long                  serialVersionUID = -3694123388080600042L;
    private String                             name;
    private String                             description;
    private MemberAccountType                  memberAccountType;
    private SystemAccountType                  systemAccountType;
    private FileMapping                        fileMapping;
    private Collection<ExternalTransfer>       transfers;
    private Collection<ExternalTransferType>   types;
    private Collection<ExternalTransferImport> imports;

    public String getDescription() {
        return description;
    }

    public FileMapping getFileMapping() {
        return fileMapping;
    }

    public Collection<ExternalTransferImport> getImports() {
        return imports;
    }

    public MemberAccountType getMemberAccountType() {
        return memberAccountType;
    }

    public String getName() {
        return name;
    }

    public SystemAccountType getSystemAccountType() {
        return systemAccountType;
    }

    public Collection<ExternalTransfer> getTransfers() {
        return transfers;
    }

    public Collection<ExternalTransferType> getTypes() {
        return types;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setFileMapping(final FileMapping fileMapping) {
        this.fileMapping = fileMapping;
    }

    public void setImports(final Collection<ExternalTransferImport> imports) {
        this.imports = imports;
    }

    public void setMemberAccountType(final MemberAccountType memberAccountType) {
        this.memberAccountType = memberAccountType;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setSystemAccountType(final SystemAccountType systemAccountType) {
        this.systemAccountType = systemAccountType;
    }

    public void setTransfers(final Collection<ExternalTransfer> transfers) {
        this.transfers = transfers;
    }

    public void setTypes(final Collection<ExternalTransferType> types) {
        this.types = types;
    }

    @Override
    public String toString() {
        return getId() + " - " + getName();
    }
}
