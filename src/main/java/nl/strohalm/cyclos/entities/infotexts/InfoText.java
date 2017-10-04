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

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.utils.Period;
import org.apache.commons.lang.StringUtils;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import java.util.Calendar;
import java.util.LinkedHashSet;
import java.util.Set;

@Table(name = "info_texts")
@javax.persistence.Entity
public class InfoText extends Entity {

    private static final long serialVersionUID = 1L;

    @Column(name = "subject", nullable = false, length = 160)
    private String            subject;

    @Column(name = "body", columnDefinition = "text")
    private String            body;

    @Column(name = "enabled", nullable = false)
    private boolean           enabled;

    @AttributeOverrides({
            @AttributeOverride(name = "begin", column=@Column(name="begin_date")),
            @AttributeOverride(name = "end", column=@Column(name="end_date"))
    })
    @Embedded
	private Period            validity;

    @ElementCollection
    @CollectionTable(name = "info_text_aliases",
            joinColumns = @JoinColumn(name = "info_text_id"))
    @Column(name = "alias")
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
