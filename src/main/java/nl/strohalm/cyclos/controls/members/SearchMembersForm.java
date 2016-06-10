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
package nl.strohalm.cyclos.controls.members;

import java.util.Collections;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.controls.elements.SearchElementsForm;
import nl.strohalm.cyclos.utils.Navigation;

import org.apache.struts.action.ActionMapping;

/**
 * Form used to search members
 * @author luis
 */
public class SearchMembersForm extends SearchElementsForm {
    private static final long serialVersionUID = 3548242105275629628L;

    @Override
    public void reset(final ActionMapping mapping, final HttpServletRequest request) {
        super.reset(mapping, request);

        // Clear the groups on each request only if not in a nested path
        if (request != null) {
            final Navigation navigation = Navigation.get(request.getSession());
            if (navigation == null || navigation.size() == 1) {
                setQuery("groups", Collections.emptyList());
                setQuery("groupFilters", Collections.emptyList());
            }
        }
    }

}
