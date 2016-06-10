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
package nl.strohalm.cyclos.controls.customization.files;

import org.apache.struts.action.ActionForm;

/**
 * Form used to retrieve a file contents
 * @author luis
 */
public class GetFileContentsAjaxForm extends ActionForm {
    private static final long serialVersionUID = 7176466222532057695L;
    private String            fileName;
    private String            type;
    private long              groupId;
    private long              groupFilterId;

    public String getFileName() {
        return fileName;
    }

    public long getGroupFilterId() {
        return groupFilterId;
    }

    public long getGroupId() {
        return groupId;
    }

    public String getType() {
        return type;
    }

    public void setFileName(final String fileName) {
        this.fileName = fileName;
    }

    public void setGroupFilterId(final long groupFilterId) {
        this.groupFilterId = groupFilterId;
    }

    public void setGroupId(final long groupId) {
        this.groupId = groupId;
    }

    public void setType(final String type) {
        this.type = type;
    }

}
