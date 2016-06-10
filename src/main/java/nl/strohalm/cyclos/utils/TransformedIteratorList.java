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
package nl.strohalm.cyclos.utils;

import java.util.Collection;
import java.util.Iterator;

import nl.strohalm.cyclos.utils.conversion.Transformer;

/**
 * An iterator list that transforms elements before returning them
 * @author luis
 * 
 * @param <Input> The input element type
 * @param <Output> The output element type
 */
public class TransformedIteratorList<Input, Output> extends IteratorListImpl<Output> {

    private final Transformer<Input, Output> transformer;

    public TransformedIteratorList(final Transformer<Input, Output> transformer, final Collection<Input> collection) {
        this(transformer, collection.iterator());
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public TransformedIteratorList(final Transformer<Input, Output> transformer, final Iterator<Input> iterator) {
        super((Iterator) iterator);
        this.transformer = transformer;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Output next() {
        final Input input = (Input) super.next();
        return transformer.transform(input);
    }
}
