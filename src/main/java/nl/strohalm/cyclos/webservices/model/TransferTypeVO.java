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
package nl.strohalm.cyclos.webservices.model;

import javax.xml.bind.annotation.XmlType;

/**
 * Represents a transfer type
 * @author luis
 */
@XmlType(name = "transferType")
public class TransferTypeVO extends EntityVO {
    private static final long serialVersionUID = 7373055911656014287L;
    private String            name;
    private AccountTypeVO     from;
    private AccountTypeVO     to;

    public AccountTypeVO getFrom() {
        return from;
    }

    public String getName() {
        return name;
    }

    public AccountTypeVO getTo() {
        return to;
    }

    public void setFrom(final AccountTypeVO from) {
        this.from = from;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setTo(final AccountTypeVO to) {
        this.to = to;
    }

    @Override
    public String toString() {
        return "TransferTypeVO [from=" + from + ", name=" + name + ", to=" + to + "]";
    }

}
