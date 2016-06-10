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

import java.util.Calendar;

import nl.strohalm.cyclos.utils.DateHelper;
import nl.strohalm.cyclos.utils.Period;

public class PeriodValidation implements PropertyValidation {
    /**
     * The possible required validations: only begin is required, only end or both.
     * @author ameyer
     * 
     */
    public static enum ValidationType {
        BEGIN_REQUIRED, END_REQUIRED, BOTH_REQUIRED, BOTH_REQUIRED_AND_NOT_EXPIRED, VALIDATE_RANGE;
    }

    private static final long serialVersionUID = -1732442287413297951L;

    private ValidationType    validationType;

    public PeriodValidation(final ValidationType validation) {
        validationType = validation;
    }

    public ValidationError validate(final Object object, final Object property, final Object value) {
        final Period period = (Period) value;
        if (period == null) {
            return new RequiredError();
        } else {
            if (isBeginRequired(validationType) && period.getBegin() == null) {
                return new ValidationError("errors.periodBeginRequired", property);
            } else if (isEndRequired(validationType) && period.getEnd() == null) {
                return new ValidationError("errors.periodEndRequired", property);
            } else if (ValidationType.BOTH_REQUIRED == validationType || ValidationType.BOTH_REQUIRED_AND_NOT_EXPIRED == validationType) {
                if (period.getBegin().after(period.getEnd())) {
                    return new ValidationError("errors.periodInvalidBounds", property);
                } else if (ValidationType.BOTH_REQUIRED_AND_NOT_EXPIRED == validationType && hasExpired(period)) {
                    return new ValidationError("errors.periodExpired", property);
                } else {
                    return null;
                }
            } else if (validationType == ValidationType.VALIDATE_RANGE) {
                if (period.getBegin() != null && period.getEnd() != null && period.getBegin().after(period.getEnd())) {
                    return new ValidationError("errors.periodInvalidBounds", property);
                }
                if (period.getEnd() != null && hasExpired(period)) {
                    return new ValidationError("errors.periodExpired", property);
                }
                return null;
            } else {
                return null;
            }
        }
    }

    private boolean hasExpired(final Period period) {
        final Calendar currentDate = DateHelper.truncate(Calendar.getInstance());
        return DateHelper.truncate(period.getEnd()).before(currentDate);
    }

    private boolean isBeginRequired(final ValidationType type) {
        return ValidationType.BEGIN_REQUIRED == type || ValidationType.BOTH_REQUIRED == type || ValidationType.BOTH_REQUIRED_AND_NOT_EXPIRED == type;
    }

    private boolean isEndRequired(final ValidationType type) {
        return ValidationType.END_REQUIRED == type || ValidationType.BOTH_REQUIRED == type || ValidationType.BOTH_REQUIRED_AND_NOT_EXPIRED == type;
    }
}
