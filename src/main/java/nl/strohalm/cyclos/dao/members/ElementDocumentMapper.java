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
package nl.strohalm.cyclos.dao.members;

import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.utils.CustomFieldsContainer;
import nl.strohalm.cyclos.utils.lucene.AbstractDocumentMapper;
import nl.strohalm.cyclos.utils.lucene.DocumentBuilder;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.document.Document;

/**
 * Maps {@link Element}s to lucene {@link Document}s
 * 
 * @author luis
 */
public abstract class ElementDocumentMapper<E extends Element> extends AbstractDocumentMapper<E> {

    /**
     * Determines whether the e-mail will be indexed
     */
    protected boolean indexEmail(final E element) {
        return StringUtils.isNotEmpty(element.getEmail());
    };

    @Override
    protected void process(final DocumentBuilder document, E element) {
        element = fetchDao.fetch(element, Element.Relationships.USER, Element.Relationships.GROUP);
        document.add("name", element.getName());
        document.add("username", element.getUsername());
        document.add("group", element.getGroup());
        document.add("creationDate", element.getCreationDate());
        document.add("active", element.isActive());
        // because the analyzer messes up the sort for the names, we need a special one for sorting, called without analyzer (the false argument)
        document.add("nameForSort", element.getName(), false);
        document.add("usernameForSort", element.getUsername(), false);
        if (indexEmail(element)) {
            document.add("email", element.getEmail());
        }
        CustomFieldsContainer<?, ?> fieldsContainer = (CustomFieldsContainer<?, ?>) element;
        document.add("customValues", fieldsContainer.getCustomValues());
    }

}
