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
package nl.strohalm.cyclos.utils.validation;

import java.util.regex.Pattern;

/**
 * Validation for e-mails
 * @author luis
 */
public class EmailValidation implements PropertyValidation {

    private static final long            serialVersionUID = 1601992764743773124L;
    private static final Pattern         PATTERN          = Pattern.compile("[\\w\\!\\#\\$\\%\\&\\'\\*\\+\\-\\/\\=\\?\\^\\`\\{\\|\\}\\~][\\w\\!\\#\\$\\%\\&\\'\\*\\+\\-\\/\\=\\?\\^\\`\\{\\|\\}\\~\\.]*@[a-zA-Z0-9\\.-]+\\.[a-zA-Z]{2,4}");
    private static final EmailValidation INSTANCE         = new EmailValidation();

    public static EmailValidation instance() {
        return INSTANCE;
    }

    public ValidationError validate(final Object object, final Object name, final Object value) {
        if (value == null || "".equals(value)) {
            return null;
        }
        final String email = (String) value;
        final boolean valid = PATTERN.matcher(email).matches() && !email.contains("..") && !email.contains(".@");
        if (valid) {
            return null;
        } else {
            return new InvalidError();
        }
    }
}
