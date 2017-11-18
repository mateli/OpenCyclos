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

import nl.strohalm.cyclos.entities.utils.RangeConstraint;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import java.io.Serializable;

/**
 * Contains data for a custom field validation
 * @author luis
 */
@Embeddable
public class Validation implements Serializable, Cloneable {

    private static final long serialVersionUID = -5712666847395055720L;

    @Column(name="val_required", nullable = false)
    private boolean           required;

    @Column(name="val_unique", nullable = false)
    private boolean           unique;

    @AttributeOverrides({
            @AttributeOverride(name = "min", column=@Column(name="val_min_length", columnDefinition = "smallint")),
            @AttributeOverride(name = "max", column=@Column(name="val_max_length", columnDefinition = "smallint"))
    })
    @Embedded
	private RangeConstraint   lengthConstraint;

    @Column(name="val_class", length = 256)
    private String            validatorClass;

    public Validation() {
        this(false, false, null);
    }

    public Validation(final boolean required) {
        this(required, false, null);
    }

    public Validation(final boolean required, final boolean unique) {
        this(required, unique, null);
    }

    public Validation(final boolean required, final boolean unique, final RangeConstraint lengthConstraint) {
        this.required = required;
        this.unique = unique;
        this.lengthConstraint = lengthConstraint;
    }

    @Override
    public Validation clone() {
        try {
            return (Validation) super.clone();
        } catch (final CloneNotSupportedException e) {
            return null;
        }
    }

    public RangeConstraint getLengthConstraint() {
        return lengthConstraint;
    }

    public String getValidatorClass() {
        return validatorClass;
    }

    public boolean isRequired() {
        return required;
    }

    public boolean isUnique() {
        return unique;
    }

    public void setLengthConstraint(final RangeConstraint lengthConstraint) {
        this.lengthConstraint = lengthConstraint;
    }

    public void setRequired(final boolean required) {
        this.required = required;
    }

    public void setUnique(final boolean unique) {
        this.unique = unique;
    }

    public void setValidatorClass(final String validatorClass) {
        this.validatorClass = validatorClass;
    }

}
