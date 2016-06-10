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
package nl.strohalm.cyclos.utils.conversion;

import nl.strohalm.cyclos.entities.accounts.AccountOwner;
import nl.strohalm.cyclos.entities.accounts.SystemAccountOwner;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.utils.access.LoggedUser;

/**
 * Converts between an id and an account owner. A valid id means a Member, while an invalid means system
 * @author luis
 */
public class AccountOwnerConverter implements Converter<AccountOwner> {

    private static final long                       serialVersionUID        = -4876497836403187578L;
    private static final AccountOwnerConverter      INSTANCE                = new AccountOwnerConverter(false);
    private static final AccountOwnerConverter      ZERO_IS_SYSTEM_INSTANCE = new AccountOwnerConverter(true);
    private static final ReferenceConverter<Member> REFERENCE_CONVERTER     = ReferenceConverter.instance(Member.class);

    public static AccountOwnerConverter instance() {
        return INSTANCE;
    }

    public static AccountOwnerConverter zeroIsSystemInstance() {
        return ZERO_IS_SYSTEM_INSTANCE;
    }

    private final boolean zeroIsSystem;

    private AccountOwnerConverter(final boolean zeroIsSystem) {
        this.zeroIsSystem = zeroIsSystem;
    }

    public boolean isZeroIsSystem() {
        return zeroIsSystem;
    }

    public String toString(final AccountOwner object) {
        if (object instanceof Member) {
            return REFERENCE_CONVERTER.toString((Member) object);
        }
        return "";
    }

    public AccountOwner valueOf(final String string) {
        final Member member = REFERENCE_CONVERTER.valueOf(string);
        return member == null ? (zeroIsSystem ? SystemAccountOwner.instance() : LoggedUser.accountOwner()) : member;
    }
}
