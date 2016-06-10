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
package nl.strohalm.cyclos.controls.access.transactionpassword;

import org.apache.struts.action.ActionForm;

/**
 * Form used to manage a member's transaction password
 * @author luis
 */
public class ManageTransactionPasswordForm extends ActionForm {
    private static final long serialVersionUID = -6266070082189825902L;
    private long              userId;
    private boolean           block;
    private boolean           embed;

    public long getUserId() {
        return userId;
    }

    public boolean isBlock() {
        return block;
    }

    public boolean isEmbed() {
        return embed;
    }

    public void setBlock(final boolean block) {
        this.block = block;
    }

    public void setEmbed(final boolean standalone) {
        embed = standalone;
    }

    public void setUserId(final long userId) {
        this.userId = userId;
    }
}
