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
package nl.strohalm.cyclos.controls.members.imports;

import org.apache.struts.action.ActionForm;

/**
 * Form used to view the import summary and to confirm the import
 * 
 * @author luis
 */
public class ImportedMembersSummaryForm extends ActionForm {
    private static final long serialVersionUID = -6665329044416564608L;
    private long              importId;
    private boolean           sendActivationMail;

    public long getImportId() {
        return importId;
    }

    public boolean isSendActivationMail() {
        return sendActivationMail;
    }

    public void setImportId(final long importId) {
        this.importId = importId;
    }

    public void setSendActivationMail(final boolean sendActivationMail) {
        this.sendActivationMail = sendActivationMail;
    }
}
