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

import java.util.List;

import javax.xml.bind.annotation.XmlType;

import nl.strohalm.cyclos.webservices.utils.ObjectHelper;

/**
 * Contains detailed information about an account type
 * 
 * @author luis
 */
@XmlType(name = "detailedAccount")
public class DetailedAccountTypeVO extends AccountTypeVO {

    private static final long    serialVersionUID = -8280130412566489510L;
    private Boolean              isDefault        = false;

    private List<TransferTypeVO> transferTypes;

    public boolean getDefault() {
        return ObjectHelper.valueOf(isDefault);
    }

    public List<TransferTypeVO> getTransferTypes() {
        return transferTypes;
    }

    public void setDefault(final boolean isDefault) {
        this.isDefault = isDefault;
    }

    public void setTransferTypes(final List<TransferTypeVO> transferTypes) {
        this.transferTypes = transferTypes;
    }

    @Override
    public String toString() {
        final StringBuilder buffer = new StringBuilder();

        buffer.append("DetailedAccountTypeVO[isDefault=" + isDefault);

        for (final TransferTypeVO vo : transferTypes) {
            buffer.append(", vo=" + vo.toString());
        }

        buffer.append("]");

        return buffer.toString();
    }

}
