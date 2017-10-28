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

import nl.strohalm.cyclos.utils.CustomObjectHandler;
import nl.strohalm.cyclos.utils.transactionimport.TransactionFileImport;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;

/**
 * A file mapping that allows an arbitrary class to implement the mapping
 * @author luis
 */
@DiscriminatorValue("cst")
@javax.persistence.Entity
public class CustomFileMapping extends FileMapping {
    private static final long serialVersionUID = 8965734986862937675L;

    @Column(name = "classname")
    private String            className;

    public String getClassName() {
        return className;
    }

    @Override
    public TransactionFileImport getImport(final CustomObjectHandler customObjectHandler) {
        return customObjectHandler.get(className);
    }

    @Override
    public Nature getNature() {
        return Nature.CUSTOM;
    }

    public void setClassName(final String className) {
        this.className = className;
    }
}
