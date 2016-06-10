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
package nl.strohalm.cyclos.controls.accounts.external.transferimports;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

/**
 * Form used to import a new external transfer file
 * @author luis
 */
public class NewTransferImportForm extends ActionForm {
    private static final long serialVersionUID = -4881566000575163990L;
    private long              externalAccountId;
    private FormFile          file;

    public long getExternalAccountId() {
        return externalAccountId;
    }

    public FormFile getFile() {
        return file;
    }

    public void setExternalAccountId(final long externalAccountId) {
        this.externalAccountId = externalAccountId;
    }

    public void setFile(final FormFile file) {
        this.file = file;
    }
}
