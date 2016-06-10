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
package nl.strohalm.cyclos.entities.customization.documents;

import nl.strohalm.cyclos.entities.Relationship;

import org.apache.commons.lang.StringUtils;

/**
 * Is a customized document, that will have custom pages for (optionally) displaying a form with parameters and show a document
 * @author luis
 */
public class DynamicDocument extends Document {

    public static enum Relationships implements Relationship {
        FORM_PAGE("formPage"), DOCUMENT_PAGE("documentPage");
        private final String name;

        private Relationships(final String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    private static final long serialVersionUID = -3105332690172908757L;
    private DocumentPage      formPage;
    private DocumentPage      documentPage;

    public DocumentPage getDocumentPage() {
        return documentPage;
    }

    public DocumentPage getFormPage() {
        return formPage;
    }

    @Override
    public Nature getNature() {
        return Nature.DYNAMIC;
    }

    public boolean isHasFormPage() {
        return formPage != null && StringUtils.isNotEmpty(formPage.getContents());
    }

    public void setDocumentPage(final DocumentPage documentPage) {
        this.documentPage = documentPage;
    }

    public void setFormPage(final DocumentPage formPage) {
        this.formPage = formPage;
    }

}
