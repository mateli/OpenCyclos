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
package nl.strohalm.cyclos.entities.customization.fields;

import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.PendingMember;
import nl.strohalm.cyclos.entities.members.imports.ImportedMember;

/**
 * Custom field value of a given member
 * @author luis
 */
public class MemberCustomFieldValue extends CustomFieldValue {

    public static enum Relationships implements Relationship {
        MEMBER("member"), PENDING_MEMBER("pendingMember"), IMPORTED_MEMBER("importedMember");
        private final String name;

        private Relationships(final String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    private static final long serialVersionUID = -2784135085280294105L;
    private Member            member;
    private PendingMember     pendingMember;
    private ImportedMember    importedMember;
    private boolean           hidden;

    public ImportedMember getImportedMember() {
        return importedMember;
    }

    public Member getMember() {
        return member;
    }

    @Override
    public Object getOwner() {
        if (importedMember != null) {
            return importedMember;
        } else if (pendingMember != null) {
            return pendingMember;
        } else {
            return member;
        }
    }

    public PendingMember getPendingMember() {
        return pendingMember;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(final boolean hidden) {
        this.hidden = hidden;
    }

    public void setImportedMember(final ImportedMember importedMember) {
        this.importedMember = importedMember;
    }

    public void setMember(final Member member) {
        this.member = member;
    }

    @Override
    public void setOwner(final Object owner) {
        // we must clear the other possible owners to ensure the instance will have only one
        if (owner instanceof PendingMember) {
            setPendingMember((PendingMember) owner);
            setImportedMember(null);
            setMember(null);
        } else if (owner instanceof ImportedMember) {
            setImportedMember((ImportedMember) owner);
            setPendingMember(null);
            setMember(null);
        } else if (owner instanceof Member) {
            setMember((Member) owner);
            setImportedMember(null);
            setPendingMember(null);
        } else {
            throw new IllegalArgumentException(String.format("Invalid owner (%1$s) for custom field value: %2$s", owner, getClass().getSimpleName()));
        }
    }

    public void setPendingMember(final PendingMember pendingMember) {
        this.pendingMember = pendingMember;
    }
}
