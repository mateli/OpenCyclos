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
package nl.strohalm.cyclos.controls.access.transactionpassword;

import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseAjaxAction;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.utils.JSONBuilder;

/**
 * Class used to generate the member's transaction password
 * @author luis
 */
public class GenerateTransactionPasswordAction extends BaseAjaxAction {

    @Override
    protected ContentType contentType() {
        return ContentType.JSON;
    }

    @Override
    protected void renderContent(final ActionContext context) throws Exception {
        String transactionPassword = null;
        String errorKey = null;
        try {
            transactionPassword = accessService.generateTransactionPassword();
        } catch (final PermissionDeniedException e) {
            errorKey = "transactionPassword.error.permissionDenied";
        } catch (final Exception e) {
            errorKey = "transactionPassword.error.generating";
        }

        final JSONBuilder json = new JSONBuilder();
        json.set("status", transactionPassword != null);
        if (transactionPassword != null) {
            json.set("transactionPassword", transactionPassword);
        } else {
            json.set("errorMessage", context.message(errorKey));
        }
        responseHelper.writeJSON(context.getResponse(), json);
    }
}
