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

import java.io.Serializable;

/**
 * Wraps an account and its status
 * 
 * @author jcomas
 */
public class MemberAccountWithStatusVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private MemberAccountVO   account;
    private AccountStatusVO   status;

    public MemberAccountWithStatusVO(final MemberAccountVO account, final AccountStatusVO status) {
        super();
        this.account = account;
        this.status = status;
    }

    public MemberAccountVO getAccount() {
        return account;
    }

    public AccountStatusVO getStatus() {
        return status;
    }

    public void setAccount(final MemberAccountVO account) {
        this.account = account;
    }

    public void setStatus(final AccountStatusVO status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "MemberAccountWithStatusVO [account=" + account + ", status=" + status + "]";
    }

}
