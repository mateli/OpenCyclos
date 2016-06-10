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

/**
 * Validator that delegates validation to an external validator
 * @author luis
 */
public class DelegatingValidator extends Validator {

    /**
     * Returns a delegating validator
     * @author luis
     */
    public static interface DelegateSource {
        Validator getValidator();
    }

    private static final long    serialVersionUID = 8455541438826620344L;

    private final DelegateSource source;

    /**
     * Delegates to a fixed source
     */
    public DelegatingValidator(final DelegateSource source) {
        this.source = source;
    }

    /**
     * Delegates to a fixed source
     */
    public DelegatingValidator(final Validator delegate) {
        source = new DelegateSource() {
            public Validator getValidator() {
                return delegate;
            }
        };
    }

    @Override
    public void validate(final Object object) throws ValidationException {
        source.getValidator().validate(object);
    }

    @Override
    protected void appendValidationErrors(final ValidationException vex, final Object object) {
        source.getValidator().appendValidationErrors(vex, object);
    }
}
