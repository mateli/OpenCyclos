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
package nl.strohalm.cyclos.utils.hibernate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;

/**
 * Hibernate user type to persist periods of dates using time
 * @author luis
 */
public class PeriodType extends BasePeriodType {

    private static final long   serialVersionUID = -9164399167265352576L;
    private static final Log    LOG              = LogFactory.getLog(PeriodType.class);
    private static final Type[] TYPES            = { StandardBasicTypes.CALENDAR, StandardBasicTypes.CALENDAR };

    @Override
    public Type[] getPropertyTypes() {
        return TYPES;
    }

    @Override
    protected Log getLog() {
        return LOG;
    }

}
