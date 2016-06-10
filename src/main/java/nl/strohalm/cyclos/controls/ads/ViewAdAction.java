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
package nl.strohalm.cyclos.controls.ads;

import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.utils.validation.ValidationException;

/**
 * Action used to view details of an advertisement
 * @author luis
 */
public class ViewAdAction extends BaseAdAction {

    @Override
    protected void formAction(final ActionContext context) throws Exception {
        // Ensure nothing is done on a submit
        throw new ValidationException();
    }

    @Override
    protected void prepareForm(final ActionContext context) throws Exception {
        super.prepareForm(context);
        context.getRequest().setAttribute("editable", false);
    }

    @Override
    protected void validateForm(final ActionContext context) {
        // Ensure it is never valid
        throw new ValidationException();
    }
}
