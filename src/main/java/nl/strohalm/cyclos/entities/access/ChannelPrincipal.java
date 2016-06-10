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
package nl.strohalm.cyclos.entities.access;

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.access.Channel.Principal;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomField;

/**
 * Contains the relationship between a channel and the allowed principals
 * 
 * @author luis
 */
public class ChannelPrincipal extends Entity {

    public static enum Relationships implements Relationship {
        CHANNEL("channel"), CUSTOM_FIELD("customField");
        private final String name;

        private Relationships(final String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    private static final long serialVersionUID = -7488098967445663138L;
    private Channel           channel;
    private Principal         principal;
    private MemberCustomField customField;
    private boolean           isDefault;

    public PrincipalType asPrincipalType() {
        if (customField != null) {
            return new PrincipalType(customField);
        } else {
            return new PrincipalType(principal);
        }
    }

    public Channel getChannel() {
        return channel;
    }

    public MemberCustomField getCustomField() {
        return customField;
    }

    public Principal getPrincipal() {
        return principal;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setChannel(final Channel channel) {
        this.channel = channel;
    }

    public void setCustomField(final MemberCustomField customField) {
        this.customField = customField;
    }

    public void setDefault(final boolean isDefault) {
        this.isDefault = isDefault;
    }

    public void setPrincipal(final Principal principal) {
        this.principal = principal;
    }

    @Override
    public String toString() {
        return getId() + " - " + channel + " - " + principal;
    }

}
