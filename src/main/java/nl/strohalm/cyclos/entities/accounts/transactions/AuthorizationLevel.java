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

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.groups.AdminGroup;
import nl.strohalm.cyclos.utils.StringValuedEnum;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Collection;

/**
 * An authorization level for an authorized transfer type
 * @author luis
 */
@Cacheable
@Table(name = "authorization_levels")
@javax.persistence.Entity
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

    @Column(name = "amount", nullable = false, precision = 15, scale = 6)
    private BigDecimal             amount;

    @Column(name = "level", nullable = false)
    private Integer                level;

    @Column(name = "authorizer", nullable = false, updatable = false, length = 1)
	private Authorizer             authorizer;

    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
	private TransferType           transferType;

    @ManyToMany
    @JoinTable(name = "admin_group_authorization_level",
            joinColumns = @JoinColumn(name = "authorization_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"))
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
