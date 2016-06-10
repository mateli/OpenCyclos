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
package nl.strohalm.cyclos.services.transfertypes;

import java.util.Collection;
import java.util.List;

import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.accounts.Currency;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.members.Element;

/**
 * Local interface. It must be used only from other services.
 */
public interface TransferTypeServiceLocal extends TransferTypeService {

    /**
     * returns the allowed transfer types for the given user
     */
    Collection<TransferType> getAllowedTTs(Element element);

    /**
     * gets a list with all possible conversion TT's available for any member account and any currency. A TT is considered a conversion if it goes
     * from member to an unlimited system account.
     */
    List<TransferType> getConversionTTs();

    /**
     * gets a list with all possible converstion TT's for the specified accountType
     */
    List<TransferType> getConversionTTs(AccountType fromAccountType);

    /**
     * gets a list with all possible conversion TT's available for any member account of the specified currency. A TT is considered a conversion if it
     * goes from member to an unlimited system account.
     */
    List<TransferType> getConversionTTs(Currency currency);

}
