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
package nl.strohalm.cyclos.entities.members.imports;

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomField;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomFieldValue;
import nl.strohalm.cyclos.utils.CustomFieldsContainer;
import nl.strohalm.cyclos.utils.StringValuedEnum;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;

/**
 * Contains temporary data for an imported member
 * 
 * @author luis
 */
@Table(name = "imported_members")
@javax.persistence.Entity
public class ImportedMember extends Entity implements CustomFieldsContainer<MemberCustomField, MemberCustomFieldValue> {

    public static enum Relationships implements Relationship {
        IMPORT("import"), CUSTOM_VALUES("customValues"), RECORDS("records");

        private final String name;

        private Relationships(final String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    public static enum Status implements StringValuedEnum {
        SUCCESS, MISSING_NAME, MISSING_USERNAME, INVALID_USERNAME, USERNAME_ALREADY_IN_USE, MISSING_EMAIL, INVALID_EMAIL, INVALID_CREATION_DATE, MISSING_CUSTOM_FIELD, INVALID_CUSTOM_FIELD, INVALID_BALANCE, BALANCE_LOWER_THAN_CREDIT_LIMIT, BALANCE_UPPER_THAN_CREDIT_LIMIT, INVALID_CREDIT_LIMIT, INVALID_UPPER_CREDIT_LIMIT, INVALID_RECORD_TYPE, INVALID_RECORD_TYPE_FIELD, MISSING_RECORD_FIELD, INVALID_RECORD_FIELD, UNKNOWN_ERROR, INVALID_CUSTOM_FIELD_VALUE_UNIQUE, INVALID_CUSTOM_FIELD_VALUE_MAX_LENGTH, INVALID_CUSTOM_FIELD_VALUE_MIN_LENGTH;

        @Override
        public String getValue() {
            return name();
        }
    }

    private static final long                  serialVersionUID = -4080042034080488479L;

    @ManyToOne
    @JoinColumn(name = "import_id", nullable = false)
	private MemberImport                       _import;

    @Column(name = "status", nullable = false, length = 50)
	private Status                             status;

    @Column(name = "error_argument1", length = 200)
    private String                             errorArgument1;

    @Column(name = "error_argument2", length = 200)
    private String                             errorArgument2;

    @Column(name = "name", length = 100)
    private String                             name;

    @Column(name = "salt", length = 32)
    private String                             salt;

    @Column(name = "username", length = 64)
    private String                             username;

    @Column(name = "password", length = 64)
    private String                             password;

    @Column(name = "email", length = 100)
    private String                             email;

    @Column(name = "line_number")
    private Integer                            lineNumber;

    @Column(name = "creation_date")
    private Calendar                           creationDate;

    @Column(name = "credit_limit", precision = 15, scale = 6)
    private BigDecimal                         creditLimit;

    @Column(name = "upper_credit_limit", precision = 15, scale = 6)
    private BigDecimal                         upperCreditLimit;

    @Column(name = "initial_balance", precision = 15, scale = 6)
    private BigDecimal                         initialBalance;

    @OneToMany(mappedBy = "importedMember", cascade = CascadeType.REMOVE)
	private Collection<MemberCustomFieldValue> customValues;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
	private Collection<ImportedMemberRecord>   records;

	public Calendar getCreationDate() {
        return creationDate;
    }

    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    @Override
    public Class<MemberCustomField> getCustomFieldClass() {
        return MemberCustomField.class;
    }

    @Override
    public Class<MemberCustomFieldValue> getCustomFieldValueClass() {
        return MemberCustomFieldValue.class;
    }

    @Override
    public Collection<MemberCustomFieldValue> getCustomValues() {
        return customValues;
    }

    public String getEmail() {
        return email;
    }

    public String getErrorArgument1() {
        return errorArgument1;
    }

    public String getErrorArgument2() {
        return errorArgument2;
    }

    public MemberImport getImport() {
        return _import;
    }

    public BigDecimal getInitialBalance() {
        return initialBalance;
    }

    public Integer getLineNumber() {
        return lineNumber;
    }

    @Override
    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public Collection<ImportedMemberRecord> getRecords() {
        return records;
    }

    public String getSalt() {
        return salt;
    }

    public Status getStatus() {
        return status;
    }

    public BigDecimal getUpperCreditLimit() {
        return upperCreditLimit;
    }

    public String getUsername() {
        return username;
    }

    public void setCreationDate(final Calendar creationDate) {
        this.creationDate = creationDate;
    }

    public void setCreditLimit(final BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
    }

    @Override
    public void setCustomValues(final Collection<MemberCustomFieldValue> customValues) {
        this.customValues = customValues;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public void setErrorArgument1(final String errorArgument) {
        errorArgument1 = errorArgument;
    }

    public void setErrorArgument2(final String errorArgument2) {
        this.errorArgument2 = errorArgument2;
    }

    public void setImport(final MemberImport _import) {
        this._import = _import;
    }

    public void setInitialBalance(final BigDecimal initialBalance) {
        this.initialBalance = initialBalance;
    }

    public void setLineNumber(final Integer lineNumber) {
        this.lineNumber = lineNumber;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public void setRecords(final Collection<ImportedMemberRecord> records) {
        this.records = records;
    }

    public void setSalt(final String salt) {
        this.salt = salt;
    }

    public void setStatus(final Status status) {
        this.status = status;
        if (status != null && status != Status.SUCCESS) {
            creditLimit = BigDecimal.ZERO;
            initialBalance = null;
            upperCreditLimit = null;
        }
    }

    public void setUpperCreditLimit(final BigDecimal upperCreditLimit) {
        this.upperCreditLimit = upperCreditLimit;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return getId() + " - " + getName() + " (" + getUsername() + ")";
    }

}
