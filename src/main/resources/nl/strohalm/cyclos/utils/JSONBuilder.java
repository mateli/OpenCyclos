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

import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;

import org.json.simple.JSONObject;

/**
 * Utility class used to build JSON strings
 * 
 * @author luis
 */
public class JSONBuilder implements Serializable {

    private static final long serialVersionUID = -6731630811505060856L;
    private JSONObject        json             = new JSONObject();

    /**
     * Sets a JSON object property
     */
    @SuppressWarnings("unchecked")
    public JSONBuilder set(final String property, final Object value) {
        json.put(property, value);
        return this;
    }

    /**
     * Returns the JSON string
     */
    @Override
    public String toString() {
        return json.toJSONString();
    }

    /**
     * Writes the JSON string directly into the given writer
     */
    public void write(final Writer writer) throws IOException {
        json.writeJSONString(writer);
    }

}
