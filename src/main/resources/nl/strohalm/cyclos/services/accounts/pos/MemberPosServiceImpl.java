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
package nl.strohalm.cyclos.services.accounts.pos;

import nl.strohalm.cyclos.dao.access.WrongCredentialAttemptsDAO;
import nl.strohalm.cyclos.dao.accounts.pos.MemberPosDAO;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.access.Channel.Credentials;
import nl.strohalm.cyclos.entities.accounts.pos.MemberPos;
import nl.strohalm.cyclos.entities.groups.MemberGroupSettings;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.services.access.AccessServiceLocal;
import nl.strohalm.cyclos.services.access.exceptions.BlockedCredentialsException;
import nl.strohalm.cyclos.services.access.exceptions.InvalidCredentialsException;
import nl.strohalm.cyclos.services.fetch.FetchServiceLocal;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.notifications.MemberNotificationHandler;

import com.mysql.jdbc.StringUtils;

/**
 * 
 * @author rodrigo
 */
public class MemberPosServiceImpl implements MemberPosServiceLocal {

    private FetchServiceLocal          fetchService;
    private MemberPosDAO               memberPosDao;
    private PosServiceLocal            posService;
    private WrongCredentialAttemptsDAO wrongCredentialAttemptsDao;
    private MemberNotificationHandler  memberNotificationHandler;
    private AccessServiceLocal         accessService;

    @Override
    public MemberPos blockMemberPos(final MemberPos memberPos) {
        memberPos.setStatus(MemberPos.Status.BLOCKED);
        memberPosDao.update(memberPos);

        posService.generateLog(memberPos.getPos());

        return memberPos;
    }

    @Override
    public MemberPos changePin(final MemberPos memberPos, final String pin) {
        boolean generateLog = false;
        if (memberPos.getPosPin() == null || StringUtils.isEmptyOrWhitespaceOnly(memberPos.getPosPin())) {
            memberPos.setStatus(MemberPos.Status.ACTIVE);
            generateLog = true;
        }
        memberPos.setPosPin(pin);
        memberPosDao.update(memberPos);
        if (generateLog) {
            posService.generateLog(memberPos.getPos());
        }
        return memberPos;
    }

    @Override
    public void checkPin(MemberPos memberPos, final String pin) throws InvalidCredentialsException, BlockedCredentialsException {
        memberPos = fetchService.fetch(memberPos, MemberPos.Relationships.POS);

        if (memberPos.getStatus() == MemberPos.Status.PIN_BLOCKED) {
            memberPos = fetchService.fetch(memberPos, RelationshipHelper.nested(MemberPos.Relationships.MEMBER, Element.Relationships.USER));
            throw new BlockedCredentialsException(Credentials.PIN, memberPos.getMember().getUser());
        }

        final String posPin = memberPos.getPosPin();
        if (posPin == null || !posPin.equalsIgnoreCase((pin))) {
            final Member member = fetchService.fetch(memberPos.getMember(), Element.Relationships.GROUP, Element.Relationships.USER);
            final MemberGroupSettings memberSettings = member.getMemberGroup().getMemberSettings();
            final int maxTries = memberSettings.getMaxPinWrongTries();
            wrongCredentialAttemptsDao.record(memberPos);
            int wrongAttempts = wrongCredentialAttemptsDao.count(accessService.wrongAttemptsLimit(), memberPos);
            if (wrongAttempts == maxTries) {
                // Block the POS.
                memberPos.setStatus(MemberPos.Status.PIN_BLOCKED);
                memberPosDao.update(memberPos);
                posService.generateLog(memberPos.getPos());
                // Notify the member
                memberNotificationHandler.posPinBlockedNotification(memberPos);
                throw new BlockedCredentialsException(Credentials.PIN, member.getUser());
            }
            throw new InvalidCredentialsException(Credentials.PIN, member.getUser());
        }

        // Everything ok - remove any previous wrong attempts
        wrongCredentialAttemptsDao.clear(memberPos);
    }

    public MemberPosDAO getMemberPosDao() {
        return memberPosDao;
    }

    @Override
    public MemberPos load(final Long id, final Relationship... fetch) {
        return memberPosDao.load(id, fetch);
    }

    @Override
    public void save(final MemberPos memberPos) {
        memberPosDao.update(memberPos, true);

    }

    public void setAccessServiceLocal(final AccessServiceLocal accessService) {
        this.accessService = accessService;
    }

    public void setFetchServiceLocal(final FetchServiceLocal fetchService) {
        this.fetchService = fetchService;
    }

    public void setMemberNotificationHandler(final MemberNotificationHandler memberNotificationHandler) {
        this.memberNotificationHandler = memberNotificationHandler;
    }

    public void setMemberPosDao(final MemberPosDAO memberPosDao) {
        this.memberPosDao = memberPosDao;
    }

    public void setPosServiceLocal(final PosServiceLocal posService) {
        this.posService = posService;
    }

    public void setWrongCredentialAttemptsDao(final WrongCredentialAttemptsDAO wrongCredentialAttemptsDao) {
        this.wrongCredentialAttemptsDao = wrongCredentialAttemptsDao;
    }

    @Override
    public MemberPos unblockMemberPos(final MemberPos memberPos) {
        memberPos.setStatus(MemberPos.Status.ACTIVE);
        memberPosDao.update(memberPos);

        posService.generateLog(memberPos.getPos());
        return memberPos;
    }

    @Override
    public MemberPos unblockPosPin(final MemberPos memberPos) {
        // Remove wrong attemts traces
        wrongCredentialAttemptsDao.clear(memberPos);

        // Unblock the POS
        unblockMemberPos(memberPos);

        return memberPos;
    }
}
