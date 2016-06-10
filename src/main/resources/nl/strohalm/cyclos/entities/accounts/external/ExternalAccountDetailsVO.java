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

import java.math.BigDecimal;

import nl.strohalm.cyclos.utils.DataObject;

/**
 * Value Object to External Account Overview
 * @author Lucas Geiss
 */
public class ExternalAccountDetailsVO extends DataObject {

    private static final long serialVersionUID = 6919503796662516788L;

    private Long              id;
    private String            name;
    private BigDecimal        balance;

    public ExternalAccountDetailsVO(final Long id, final String name, final BigDecimal balance) {

        this.id = id;
        this.name = name;
        if (balance == null) {
            this.balance = BigDecimal.ZERO;
        } else {
            this.balance = balance;
        }
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setBalance(final BigDecimal balance) {
        this.balance = balance;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setName(final String name) {
        this.name = name;
    }

}
