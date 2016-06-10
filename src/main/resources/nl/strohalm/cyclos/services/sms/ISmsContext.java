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
package nl.strohalm.cyclos.services.sms;

import java.math.BigDecimal;

import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.sms.MemberSmsStatus;
import nl.strohalm.cyclos.utils.TimePeriod;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * This interface is used to calculate the sms related data according to a member. The bean will be autowired by name by Spring, so any service
 * available on cyclos can be injected via setters. Also, if the bean needs a callback for initialization, should implement {@link InitializingBean},
 * and for finalization, {@link DisposableBean}
 * @author ameyer
 */
public interface ISmsContext {

    /**
     * @return The amount to be charged for the additional sms package
     */
    BigDecimal getAdditionalChargeAmount(Member member);

    /**
     * @return The validity period for the additional charged sms package
     */
    TimePeriod getAdditionalChargedPeriod(Member member);

    /**
     * @return The additional charged sms count (package's size)
     */
    int getAdditionalChargedSms(Member member);

    /**
     * @return The free sms count
     */
    int getFreeSms(Member member);

    /**
     * @return Whether the number of free sms messages should be shown to the member
     */
    boolean showFreeSms(MemberSmsStatus status);
}
