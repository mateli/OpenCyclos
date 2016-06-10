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

import java.io.Serializable;
import java.math.BigDecimal;

import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.sms.MemberSmsStatus;
import nl.strohalm.cyclos.utils.TimePeriod;

/**
 * This sms context implementation is the default used by Cyclos. <br>
 * It gets the related data from the member group (ignoring the specified member)
 * @author ameyer
 */
public class MemberGroupSmsContextImpl implements ISmsContext, Serializable {

    private static final long                      serialVersionUID = 869531744522514275L;

    private static final MemberGroupSmsContextImpl INSTANCE         = new MemberGroupSmsContextImpl();

    public static MemberGroupSmsContextImpl getInstance() {
        return INSTANCE;
    }

    private MemberGroupSmsContextImpl() {
    }

    @Override
    public BigDecimal getAdditionalChargeAmount(final Member member) {
        return member.getMemberGroup().getMemberSettings().getSmsChargeAmount();
    }

    @Override
    public TimePeriod getAdditionalChargedPeriod(final Member member) {
        return member.getMemberGroup().getMemberSettings().getSmsAdditionalChargedPeriod();
    }

    @Override
    public int getAdditionalChargedSms(final Member member) {
        return member.getMemberGroup().getMemberSettings().getSmsAdditionalCharged();
    }

    @Override
    public int getFreeSms(final Member member) {
        return member.getMemberGroup().getMemberSettings().getSmsFree();
    }

    @Override
    public boolean showFreeSms(final MemberSmsStatus status) {
        final Member member = status.getMember();
        final int threshold = member.getMemberGroup().getMemberSettings().getSmsShowFreeThreshold();
        return getFreeSms(member) - status.getFreeSmsSent() <= threshold;
    }
}
