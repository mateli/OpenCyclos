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

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseAction;
import nl.strohalm.cyclos.entities.settings.AccessSettings;

import org.apache.struts.action.ActionForward;

/**
 * Action used to request the logged element's transaction password
 * @author luis
 */
public class TransactionPasswordAction extends BaseAction {

    /**
     * Returns the number of buttons per row to be used on transaction password
     */
    public static int buttonsPerRow(final AccessSettings accessSettings) {
        final String chars = accessSettings.getTransactionPasswordChars();
        final int length = chars.length();
        int buttonsPerRow = length / 2;
        for (int i = 7; i >= 2; i--) {
            if (length % i == 0) {
                buttonsPerRow = i;
                break;
            }
        }
        return buttonsPerRow;
    }

    @Override
    protected ActionForward executeAction(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();

        // Check if the transaction password should be requested
        if (!context.isTransactionPasswordEnabled()) {
            return null;
        }

        // Check the transaction password status
        if (!Boolean.valueOf(request.getParameter("noCheck"))) {
            context.validateTransactionPassword();
        }

        // Determine the number of buttons per row if using virtual keyboard
        final AccessSettings accessSettings = settingsService.getAccessSettings();
        if (accessSettings.isVirtualKeyboardTransactionPassword()) {
            request.setAttribute("buttonsPerRow", buttonsPerRow(accessSettings));
        }

        return context.getInputForward();
    }
}
