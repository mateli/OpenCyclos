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
package nl.strohalm.cyclos.entities.accounts.pos;

import java.util.Collection;
import java.util.Map;

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.utils.StringValuedEnum;

/**
 * 
 * @author rodrigo
 */
public class Pos extends Entity {

    public static enum Relationships implements Relationship {
        MEMBER_POS("memberPos");
        private final String name;

        private Relationships(final String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    public enum Status implements StringValuedEnum {
        UNASSIGNED("U"), ASSIGNED("A"), DISCARDED("D");
        private final String value;

        private Status(final String value) {
            this.value = value;
        }

        @Override
        public String getValue() {
            return value;
        }
    }

    private String             posId;
    private String             description;
    private MemberPos          memberPos;
    private Collection<PosLog> posLog;
    private Status             status;

    private static final long  serialVersionUID = -6054597340850484757L;

    public String getDescription() {
        return description;
    }

    public MemberPos getMemberPos() {
        return memberPos;
    }

    public String getPosId() {
        return posId;
    }

    public Collection<PosLog> getPosLog() {
        return posLog;
    }

    public Status getStatus() {
        return status;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setMemberPos(final MemberPos memberPos) {
        this.memberPos = memberPos;
    }

    public void setPosId(final String posId) {
        this.posId = posId;
    }

    public void setPosLog(final Collection<PosLog> posLog) {
        this.posLog = posLog;
    }

    public void setStatus(final Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return getId() + " - " + posId;
    }

    @Override
    protected void appendVariableValues(final Map<String, Object> variables, final LocalSettings localSettings) {
        variables.put("pos_id", getPosId());
    }

}
