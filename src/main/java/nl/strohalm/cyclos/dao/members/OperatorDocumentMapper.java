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

import nl.strohalm.cyclos.entities.members.Operator;
import nl.strohalm.cyclos.utils.lucene.DocumentBuilder;

import org.apache.lucene.document.Document;

/**
 * Maps {@link Operator}s to lucene {@link Document}s
 * 
 * @author luis
 */
public class OperatorDocumentMapper extends ElementDocumentMapper<Operator> {

    @Override
    protected void process(final DocumentBuilder document, final Operator operator) {
        super.process(document, operator);
        document.add("member", operator.getMember());
    }

}
