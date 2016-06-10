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
package nl.strohalm.cyclos.entities.accounts.transactions;

import java.math.BigDecimal;
import java.util.Collection;

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.groups.AdminGroup;
import nl.strohalm.cyclos.utils.StringValuedEnum;

/**
 * An authorization level for an authorized transfer type
 * @author luis
 */
public class AuthorizationLevel extends Entity {

    public static enum Authorizer implements StringValuedEnum {
        RECEIVER("R"), PAYER("P"), BROKER("B"), ADMIN("A");

        private final String value;

        private Authorizer(final String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public static enum Relationships implements Relationship {
        TRANSFER_TYPE("transferType"), ADMIN_GROUPS("adminGroups");
        private final String name;

        private Relationships(final String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public static final int        MAX_LEVELS       = 5;

    private static final long      serialVersionUID = 467554265841133365L;
    private BigDecimal             amount;
    private Integer                level;
    private Authorizer             authorizer;
    private TransferType           transferType;
    private Collection<AdminGroup> adminGroups;

    public Collection<AdminGroup> getAdminGroups() {
        return adminGroups;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Authorizer getAuthorizer() {
        return authorizer;
    }

    public Integer getLevel() {
        return level;
    }

    public TransferType getTransferType() {
        return transferType;
    }

    public void setAdminGroups(final Collection<AdminGroup> adminGroups) {
        this.adminGroups = adminGroups;
    }

    public void setAmount(final BigDecimal amount) {
        this.amount = amount;
    }

    public void setAuthorizer(final Authorizer authorizer) {
        this.authorizer = authorizer;
    }

    public void setLevel(final Integer level) {
        this.level = level;
    }

    public void setTransferType(final TransferType transferType) {
        this.transferType = transferType;
    }

    @Override
    public String toString() {
        return getId() + " - Level: " + getLevel();
    }

}
