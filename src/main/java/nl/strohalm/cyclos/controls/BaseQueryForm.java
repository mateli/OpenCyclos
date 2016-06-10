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
package nl.strohalm.cyclos.controls;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.utils.binding.MapBean;

import org.apache.struts.action.ActionMapping;

/**
 * Base form for paged queries
 * @author luis
 */
public abstract class BaseQueryForm extends BaseBindingForm {
    private static final long serialVersionUID = 5422648802599139222L;

    public BaseQueryForm() {
        reset(null, null);
    }

    public Map<String, Object> getQuery() {
        return values;
    }

    public Object getQuery(final String key) {
        return values.get(key);
    }

    @Override
    public void reset(final ActionMapping mapping, final HttpServletRequest request) {
        super.reset(mapping, request);
        setQuery("pageParameters", new MapBean("currentPage"));
    }

    public void setQuery(final Map<String, Object> query) {
        values = query;
    }

    public void setQuery(final String key, final Object value) {
        values.put(key, value);
    }
}
