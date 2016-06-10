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
package nl.strohalm.cyclos.controls.customization.documents;

import java.util.Map;

import nl.strohalm.cyclos.controls.BaseBindingForm;

import org.apache.struts.upload.FormFile;

/**
 * Form used to edit a customized static document
 * @author Jefferson Magno
 */
public class EditStaticDocumentForm extends BaseBindingForm {

    private static final long serialVersionUID = 1136540824344920940L;
    private long              documentId;
    private FormFile          upload;

    public EditStaticDocumentForm() {
    }

    public Map<String, Object> getDocument() {
        return values;
    }

    public Object getDocument(final String key) {
        return values.get(key);
    }

    public long getDocumentId() {
        return documentId;
    }

    public FormFile getUpload() {
        return upload;
    }

    public void setDocument(final Map<String, Object> doc) {
        values = doc;
    }

    public void setDocument(final String key, final Object value) {
        values.put(key, value);
    }

    public void setDocumentId(final long documentId) {
        this.documentId = documentId;
    }

    public void setUpload(final FormFile upload) {
        this.upload = upload;
    }

}
