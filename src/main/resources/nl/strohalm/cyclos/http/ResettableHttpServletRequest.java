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

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;

/**
 * An {@link HttpServletRequest} which wraps another request, keeping track of changes. Any changes can be applied or reset.
 * @author luis
 */
public class ResettableHttpServletRequest extends HttpServletRequestWrapper implements AttributeHolder, Resettable {

    private ResettableAttributeHolder attributes;
    private ResettableHttpSession     session;

    public ResettableHttpServletRequest(final HttpServletRequest request) {
        super(request);
        attributes = new ResettableAttributeHolder(AttributeHolder.Factory.request(request));
    }

    @Override
    public void applyState() {
        attributes.applyState();
        if (session != null) {
            session.applyState();
        }
    }

    @Override
    public Object getAttribute(final String name) {
        return attributes.getAttribute(name);
    }

    @Override
    public Enumeration<String> getAttributeNames() {
        return attributes.getAttributeNames();
    }

    @Override
    public HttpSession getSession() {
        if (session != null) {
            return session;
        }
        final HttpSession original = super.getSession();
        session = new ResettableHttpSession(this, original);
        return session;
    }

    @Override
    public HttpSession getSession(final boolean create) {
        if (session != null) {
            return session;
        }
        final HttpSession original = super.getSession(create);
        if (original != null) {
            session = new ResettableHttpSession(this, original);
        }
        return session;
    }

    public void invalidateSession() {
        session = null;
    }

    @Override
    public void removeAttribute(final String name) {
        attributes.removeAttribute(name);
    }

    @Override
    public void resetState() {
        attributes.resetState();
        if (session != null) {
            session.resetState();
        }
    }

    @Override
    public void setAttribute(final String name, final Object value) {
        attributes.setAttribute(name, value);
    }

}
