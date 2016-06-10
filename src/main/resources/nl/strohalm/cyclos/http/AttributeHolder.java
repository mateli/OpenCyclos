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

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Denotes servlet components which hold attributes
 * @author luis
 */
public interface AttributeHolder {

    public static class Factory {

        /**
         * Returns a view for the given {@link ServletContext} as an {@link AttributeHolder}
         */
        public static AttributeHolder context(final ServletContext servletContext) {
            return new AttributeHolder() {

                @Override
                public Object getAttribute(final String name) {
                    return servletContext.getAttribute(name);
                }

                @Override
                @SuppressWarnings("unchecked")
                public Enumeration<String> getAttributeNames() {
                    return servletContext.getAttributeNames();
                }

                @Override
                public void removeAttribute(final String name) {
                    servletContext.removeAttribute(name);
                }

                @Override
                public void setAttribute(final String name, final Object value) {
                    servletContext.setAttribute(name, value);
                }
            };
        }

        /**
         * Returns a view for the given {@link HttpServletRequest} as an {@link AttributeHolder}
         */
        public static AttributeHolder request(final HttpServletRequest request) {
            return new AttributeHolder() {

                @Override
                public Object getAttribute(final String name) {
                    return request.getAttribute(name);
                }

                @Override
                @SuppressWarnings("unchecked")
                public Enumeration<String> getAttributeNames() {
                    return request.getAttributeNames();
                }

                @Override
                public void removeAttribute(final String name) {
                    request.removeAttribute(name);
                }

                @Override
                public void setAttribute(final String name, final Object value) {
                    request.setAttribute(name, value);
                }
            };
        }

        /**
         * Returns a view for the given {@link HttpSession} as an {@link AttributeHolder}
         */
        public static AttributeHolder session(final HttpSession session) {
            return new AttributeHolder() {

                @Override
                public Object getAttribute(final String name) {
                    return session.getAttribute(name);
                }

                @Override
                @SuppressWarnings("unchecked")
                public Enumeration<String> getAttributeNames() {
                    return session.getAttributeNames();
                }

                @Override
                public void removeAttribute(final String name) {
                    session.removeAttribute(name);
                }

                @Override
                public void setAttribute(final String name, final Object value) {
                    session.setAttribute(name, value);
                }
            };
        }
    }

    Object getAttribute(String name);

    Enumeration<String> getAttributeNames();

    void removeAttribute(String name);

    void setAttribute(String name, Object value);

}
