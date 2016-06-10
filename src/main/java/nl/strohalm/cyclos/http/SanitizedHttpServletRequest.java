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
package nl.strohalm.cyclos.http;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import nl.strohalm.cyclos.utils.StringHelper;

public class SanitizedHttpServletRequest extends HttpServletRequestWrapper {

    public SanitizedHttpServletRequest(final HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getParameter(final String name) {
        return StringHelper.removeMarkupTags(super.getParameter(name));
    }

    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Map getParameterMap() {
        final Map map = super.getParameterMap();
        final Map<String, String[]> sanitizedMap = new HashMap<String, String[]>();
        final Iterator<Map.Entry<String, String[]>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            final Map.Entry<String, String[]> entry = it.next();
            sanitizedMap.put(entry.getKey(), sanitize(entry.getValue()));
        }
        return Collections.unmodifiableMap(map);
    }

    @Override
    public String[] getParameterValues(final String name) {
        return sanitize(super.getParameterValues(name));
    }

    private String[] sanitize(final String[] values) {
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                values[i] = StringHelper.removeMarkupTags(values[i]);
            }
        }

        return values;
    }
}
