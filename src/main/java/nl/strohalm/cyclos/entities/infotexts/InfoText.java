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
package nl.strohalm.cyclos.entities.infotexts;

import java.util.Calendar;
import java.util.LinkedHashSet;
import java.util.Set;

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.utils.Period;

import org.apache.commons.lang.StringUtils;

public class InfoText extends Entity {

    private static final long serialVersionUID = 1L;

    private String            subject;
    private String            body;
    private boolean           enabled;
    private Period            validity;
    private Set<String>       aliases;

    public void addAlias(final String alias) {
        if (aliases == null) {
            aliases = new LinkedHashSet<String>();
        }
        aliases.add(alias);
    }

    public Set<String> getAliases() {
        return aliases;
    }

    public String getAliasesString() {
        return StringUtils.join(aliases, ",");
    }

    public String getBody() {
        return body;
    }

    public String getSubject() {
        return subject;
    }

    public Period getValidity() {
        return validity;
    }

    public boolean isActive() {

        if (enabled) {
            // when validity is null it's assumed to be valid for all time.
            if (validity == null) {
                return true;
            } else {
                return validity.includes(Calendar.getInstance());
            }
        } else {
            return false;
        }
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setAliases(final Set<String> aliases) {
        this.aliases = aliases;
    }

    public void setBody(final String body) {
        this.body = body;
    }

    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }

    public void setSubject(final String subject) {
        this.subject = subject;
    }

    public void setValidity(final Period validity) {
        this.validity = validity;
    }

    @Override
    public String toString() {
        return getId() + " - InfoText - " + getAliases();
    }

}
