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
package nl.strohalm.cyclos.services.elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.strohalm.cyclos.dao.members.imports.ImportedMemberDAO;
import nl.strohalm.cyclos.dao.members.imports.ImportedMemberRecordDAO;
import nl.strohalm.cyclos.dao.members.imports.MemberImportDAO;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.access.MemberUser;
import nl.strohalm.cyclos.entities.access.User;
import nl.strohalm.cyclos.entities.accounts.MemberAccountType;
import nl.strohalm.cyclos.entities.accounts.MemberGroupAccountSettings;
import nl.strohalm.cyclos.entities.accounts.SystemAccountOwner;
import nl.strohalm.cyclos.entities.customization.fields.CustomField;
import nl.strohalm.cyclos.entities.customization.fields.CustomFieldValue;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomField;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomFieldValue;
import nl.strohalm.cyclos.entities.customization.fields.MemberRecordCustomField;
import nl.strohalm.cyclos.entities.customization.fields.MemberRecordCustomFieldValue;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Administrator;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.imports.ImportedMember;
import nl.strohalm.cyclos.entities.members.imports.ImportedMemberQuery;
import nl.strohalm.cyclos.entities.members.imports.ImportedMemberRecord;
import nl.strohalm.cyclos.entities.members.imports.ImportedMemberRecordCustomFieldValue;
import nl.strohalm.cyclos.entities.members.imports.MemberImport;
import nl.strohalm.cyclos.entities.members.imports.MemberImportResult;
import nl.strohalm.cyclos.entities.members.records.MemberRecord;
import nl.strohalm.cyclos.entities.members.records.MemberRecordType;
import nl.strohalm.cyclos.entities.members.records.MemberRecordTypeQuery;
import nl.strohalm.cyclos.entities.settings.AccessSettings;
import nl.strohalm.cyclos.entities.settings.AccessSettings.UsernameGeneration;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.services.accounts.AccountServiceLocal;
import nl.strohalm.cyclos.services.accounts.CreditLimitDTO;
import nl.strohalm.cyclos.services.customization.MemberCustomFieldServiceLocal;
import nl.strohalm.cyclos.services.customization.MemberRecordCustomFieldServiceLocal;
import nl.strohalm.cyclos.services.fetch.FetchServiceLocal;
import nl.strohalm.cyclos.services.groups.GroupServiceLocal;
import nl.strohalm.cyclos.services.settings.SettingsServiceLocal;
import nl.strohalm.cyclos.services.transactions.PaymentServiceLocal;
import nl.strohalm.cyclos.services.transactions.TransactionContext;
import nl.strohalm.cyclos.services.transactions.TransferDTO;
import nl.strohalm.cyclos.utils.CacheCleaner;
import nl.strohalm.cyclos.utils.CustomFieldHelper;
import nl.strohalm.cyclos.utils.DateHelper;
import nl.strohalm.cyclos.utils.HashHandler;
import nl.strohalm.cyclos.utils.TimePeriod;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.utils.conversion.CalendarConverter;
import nl.strohalm.cyclos.utils.conversion.CoercionHelper;
import nl.strohalm.cyclos.utils.csv.CSVReader;
import nl.strohalm.cyclos.utils.csv.UnknownColumnException;
import nl.strohalm.cyclos.utils.query.PageHelper;
import nl.strohalm.cyclos.utils.query.QueryParameters.ResultType;
import nl.strohalm.cyclos.utils.validation.EmailValidation;
import nl.strohalm.cyclos.utils.validation.LengthValidation;
import nl.strohalm.cyclos.utils.validation.MaxLengthError;
import nl.strohalm.cyclos.utils.validation.MinLengthError;
import nl.strohalm.cyclos.utils.validation.RegexValidation;
import nl.strohalm.cyclos.utils.validation.RequiredError;
import nl.strohalm.cyclos.utils.validation.UniqueError;
import nl.strohalm.cyclos.utils.validation.ValidationError;
import nl.strohalm.cyclos.utils.validation.ValidationException;
import nl.strohalm.cyclos.utils.validation.Validator;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Handles importing members
 * 
 * @author luis
 */
public class MemberImportServiceImpl implements MemberImportServiceLocal {

    private FetchServiceLocal                   fetchService;
    private ElementServiceLocal                 elementService;
    private MemberRecordServiceLocal            memberRecordService;
    private MemberCustomFieldServiceLocal       memberCustomFieldService;
    private MemberRecordCustomFieldServiceLocal memberRecordCustomFieldService;
    private AccountServiceLocal                 accountService;
    private PaymentServiceLocal                 paymentService;
    private SettingsServiceLocal                settingsService;
    private GroupServiceLocal                   groupService;
    private MemberRecordTypeServiceLocal        memberRecordTypeService;
    private HashHandler                         hashHandler;
    private MemberImportDAO                     memberImportDao;
    private ImportedMemberDAO                   importedMemberDao;
    private ImportedMemberRecordDAO             importedMemberRecordDao;
    private CustomFieldHelper                   customFieldHelper;

    @Override
    public MemberImportResult getSummary(final MemberImport memberImport) {
        final MemberImportResult result = new MemberImportResult();

        final ImportedMemberQuery query = new ImportedMemberQuery();
        query.setPageForCount();
        query.setMemberImport(memberImport);

        // Get the total number of members
        query.setStatus(ImportedMemberQuery.Status.ALL);
        result.setTotal(PageHelper.getTotalCount(importedMemberDao.search(query)));

        // Get the number of members with error
        query.setStatus(ImportedMemberQuery.Status.ERROR);
        result.setErrors(PageHelper.getTotalCount(importedMemberDao.search(query)));

        // Get the total of transactions
        result.setCredits(importedMemberDao.getTransactions(memberImport, true));
        result.setDebits(importedMemberDao.getTransactions(memberImport, false));

        return result;
    }

    @Override
    public MemberImport importMembers(MemberImport memberImport, final InputStream data) {
        final LocalSettings localSettings = settingsService.getLocalSettings();
        final AccessSettings accessSettings = settingsService.getAccessSettings();

        // Validate and save the import
        getValidator().validate(memberImport);
        final MemberGroup group = fetchService.fetch(memberImport.getGroup());
        memberImport.setGroup(group);
        memberImport.setBy(LoggedUser.<Administrator> element());
        memberImport.setDate(Calendar.getInstance());
        memberImport = memberImportDao.insert(memberImport);

        // Get the account settings
        MemberGroupAccountSettings accountSettings = null;
        final MemberAccountType accountType = memberImport.getAccountType();
        if (accountType != null) {
            accountSettings = groupService.loadAccountSettings(group.getId(), accountType.getId());
        }

        // Find out the custom fields;
        List<MemberCustomField> customFields = memberCustomFieldService.list();
        customFields = customFieldHelper.onlyForGroup(customFields, group);
        final Map<String, CustomField> customFieldMap = new HashMap<String, CustomField>(customFields.size());
        for (final MemberCustomField customField : customFields) {
            customFieldMap.put(customField.getInternalName().toLowerCase(), fetchService.fetch(customField, CustomField.Relationships.POSSIBLE_VALUES));
        }

        // Find the record types
        final MemberRecordTypeQuery mrtQuery = new MemberRecordTypeQuery();
        mrtQuery.fetch(MemberRecordType.Relationships.FIELDS);
        Collection<Group> mrtGroups = new ArrayList<Group>();
        mrtGroups.add(group);
        mrtQuery.setGroups(mrtGroups);
        final List<MemberRecordType> recordTypes = memberRecordTypeService.search(mrtQuery);
        final Map<String, MemberRecordType> recordTypeMap = new HashMap<String, MemberRecordType>(recordTypes.size());
        final Map<MemberRecordType, Map<String, CustomField>> recordTypeFieldsMap = new HashMap<MemberRecordType, Map<String, CustomField>>(recordTypes.size());
        for (final MemberRecordType recordType : recordTypes) {
            final String lowercaseName = recordType.getName().toLowerCase();
            recordTypeMap.put(lowercaseName, recordType);
            // Get the custom fields for this record type
            final Map<String, CustomField> fields = new HashMap<String, CustomField>();
            for (final MemberRecordCustomField customField : recordType.getFields()) {
                fields.put(customField.getInternalName().toLowerCase(), fetchService.fetch(customField, CustomField.Relationships.POSSIBLE_VALUES));
            }
            recordTypeFieldsMap.put(recordType, fields);
        }

        // We need to read the first line in order to discover which columns exist
        final char stringQuote = CoercionHelper.coerce(Character.TYPE, localSettings.getCsvStringQuote().getValue());
        final char valueSeparator = CoercionHelper.coerce(Character.TYPE, localSettings.getCsvValueSeparator().getValue());
        BufferedReader in = null;
        List<String> headers;
        try {
            in = new BufferedReader(new InputStreamReader(data, localSettings.getCharset()));
            headers = CSVReader.readLine(in, stringQuote, valueSeparator);
        } catch (final Exception e) {
            throw new ValidationException("memberImport.invalidFormat");
        }

        final Set<String> usedUsernames = new HashSet<String>();
        // Import each member
        try {
            final CacheCleaner cacheCleaner = new CacheCleaner(fetchService);
            int lineNumber = 2; // The first line is the header
            List<String> values;
            while ((values = CSVReader.readLine(in, stringQuote, valueSeparator)) != null) {
                if (values.isEmpty()) {
                    continue;
                }
                importMember(memberImport, accountSettings, lineNumber, customFieldMap, recordTypeMap, recordTypeFieldsMap, localSettings, accessSettings, headers, values, usedUsernames);
                lineNumber++;
                cacheCleaner.clearCache();
            }
        } catch (final IOException e) {
            throw new ValidationException("memberImport.errorReading");
        } finally {
            IOUtils.closeQuietly(in);
        }
        return memberImport;
    }

    @Override
    public MemberImport load(final Long id, final Relationship... fetch) throws EntityNotFoundException {
        return memberImportDao.load(id, fetch);
    }

    @Override
    public void processImport(MemberImport memberImport, final boolean sendActivationMail) {
        memberImport = fetchService.fetch(memberImport, MemberImport.Relationships.GROUP, MemberImport.Relationships.ACCOUNT_TYPE, MemberImport.Relationships.INITIAL_CREDIT_TRANSFER_TYPE, MemberImport.Relationships.INITIAL_DEBIT_TRANSFER_TYPE);

        // Iterate through each member
        final ImportedMemberQuery memberQuery = new ImportedMemberQuery();
        memberQuery.setResultType(ResultType.ITERATOR);
        memberQuery.setMemberImport(memberImport);
        memberQuery.setStatus(ImportedMemberQuery.Status.SUCCESS);
        int count = 0;
        for (final ImportedMember importedMember : importedMemberDao.search(memberQuery)) {
            processMember(memberImport, importedMember, sendActivationMail);
            if (count % 20 == 0) {
                // Every few records, clear the cache to avoid too many objects in memory
                fetchService.clearCache();
            }
            count++;
        }
        // Delete the import after processing it
        memberImportDao.delete(memberImport.getId());
    }

    @Override
    public void purgeOld(Calendar time) {
        // Only purge after 1 day of idleness
        time = new TimePeriod(1, TimePeriod.Field.DAYS).remove(time);
        for (final MemberImport memberImport : memberImportDao.listBefore(time)) {
            memberImportDao.delete(memberImport.getId());
        }
    }

    @Override
    public List<ImportedMember> searchImportedMembers(final ImportedMemberQuery params) {
        return importedMemberDao.search(params);
    }

    public void setAccountServiceLocal(final AccountServiceLocal accountService) {
        this.accountService = accountService;
    }

    public void setCustomFieldHelper(final CustomFieldHelper customFieldHelper) {
        this.customFieldHelper = customFieldHelper;
    }

    public void setElementServiceLocal(final ElementServiceLocal elementService) {
        this.elementService = elementService;
    }

    public void setFetchServiceLocal(final FetchServiceLocal fetchService) {
        this.fetchService = fetchService;
    }

    public void setGroupServiceLocal(final GroupServiceLocal groupService) {
        this.groupService = groupService;
    }

    public void setHashHandler(final HashHandler hashHandler) {
        this.hashHandler = hashHandler;
    }

    public void setImportedMemberDao(final ImportedMemberDAO importedMemberDao) {
        this.importedMemberDao = importedMemberDao;
    }

    public void setImportedMemberRecordDao(final ImportedMemberRecordDAO importedMemberRecordDao) {
        this.importedMemberRecordDao = importedMemberRecordDao;
    }

    public void setMemberCustomFieldServiceLocal(final MemberCustomFieldServiceLocal memberCustomFieldService) {
        this.memberCustomFieldService = memberCustomFieldService;
    }

    public void setMemberImportDao(final MemberImportDAO memberImportDao) {
        this.memberImportDao = memberImportDao;
    }

    public void setMemberRecordCustomFieldServiceLocal(final MemberRecordCustomFieldServiceLocal memberRecordCustomFieldService) {
        this.memberRecordCustomFieldService = memberRecordCustomFieldService;
    }

    public void setMemberRecordServiceLocal(final MemberRecordServiceLocal memberRecordService) {
        this.memberRecordService = memberRecordService;
    }

    public void setMemberRecordTypeServiceLocal(final MemberRecordTypeServiceLocal memberRecordTypeService) {
        this.memberRecordTypeService = memberRecordTypeService;
    }

    public void setPaymentServiceLocal(final PaymentServiceLocal paymentService) {
        this.paymentService = paymentService;
    }

    public void setSettingsServiceLocal(final SettingsServiceLocal settingsService) {
        this.settingsService = settingsService;
    }

    @Override
    public void validate(final MemberImport memberImport) throws ValidationException {
        getValidator().validate(memberImport);
    }

    private Validator getValidator() {
        final Validator validator = new Validator("memberImport");
        validator.property("group").required();
        return validator;
    }

    private void importMember(final MemberImport memberImport, final MemberGroupAccountSettings accountSettings, final int lineNumber, final Map<String, CustomField> customFieldMap, final Map<String, MemberRecordType> recordTypeMap, final Map<MemberRecordType, Map<String, CustomField>> recordTypeFieldsMap, final LocalSettings localSettings, final AccessSettings accessSettings, final List<String> headers, final List<String> values, final Set<String> importedUsernames) {
        final Map<MemberRecordType, ImportedMemberRecord> records = new HashMap<MemberRecordType, ImportedMemberRecord>();

        final Map<String, String> customFieldValues = new HashMap<String, String>();
        final Map<MemberRecordType, Map<String, String>> recordFieldValues = new HashMap<MemberRecordType, Map<String, String>>();

        // Insert the member
        ImportedMember member = new ImportedMember();
        member.setSalt(hashHandler.newSalt());
        member.setLineNumber(lineNumber);
        member.setImport(memberImport);
        member.setStatus(ImportedMember.Status.SUCCESS);
        member = importedMemberDao.insert(member);

        final Calendar today = DateHelper.truncate(Calendar.getInstance());
        try {
            member.setCustomValues(new ArrayList<MemberCustomFieldValue>());
            final CalendarConverter dateConverter = localSettings.getRawDateConverter();

            // Process each field. Field names are lowercased to ignore case
            for (int i = 0; i < headers.size() && i < values.size(); i++) {
                final String field = StringUtils.trimToEmpty(headers.get(i)).toLowerCase();
                final String value = StringUtils.trimToNull(values.get(i));
                if ("name".equals(field)) {
                    member.setName(value);
                } else if ("username".equals(field)) {
                    member.setUsername(value);
                } else if ("password".equals(field)) {
                    member.setPassword(hashHandler.hash(member.getSalt(), value));
                } else if ("email".equals(field)) {
                    member.setEmail(value);
                } else if ("creationdate".equals(field)) {
                    try {
                        final Calendar creationDate = dateConverter.valueOf(value);
                        if (creationDate != null) {
                            if (creationDate.after(today) || creationDate.get(Calendar.YEAR) < 1950) {
                                throw new Exception();
                            }
                            member.setCreationDate(creationDate);
                        }
                    } catch (final Exception e) {
                        member.setStatus(ImportedMember.Status.INVALID_CREATION_DATE);
                        member.setErrorArgument1(value);
                        break;
                    }
                } else if ("balance".equals(field)) {
                    try {
                        member.setInitialBalance(localSettings.getNumberConverter().valueOf(value));
                    } catch (final Exception e) {
                        member.setStatus(ImportedMember.Status.INVALID_BALANCE);
                        member.setErrorArgument1(value);
                        break;
                    }
                } else if ("creditlimit".equals(field)) {
                    try {
                        BigDecimal limit = localSettings.getNumberConverter().valueOf(value);
                        // Ensure the limit is positive
                        if (limit != null) {
                            limit = limit.abs();
                        }
                        member.setCreditLimit(limit);
                    } catch (final Exception e) {
                        member.setStatus(ImportedMember.Status.INVALID_CREDIT_LIMIT);
                        member.setErrorArgument1(value);
                        break;
                    }
                } else if ("uppercreditlimit".equals(field)) {
                    try {
                        member.setUpperCreditLimit(localSettings.getNumberConverter().valueOf(value));
                    } catch (final Exception e) {
                        member.setStatus(ImportedMember.Status.INVALID_UPPER_CREDIT_LIMIT);
                        member.setErrorArgument1(value);
                        break;
                    }
                } else if (customFieldMap.containsKey(field)) {
                    // Create a custom field value
                    CustomField customField = customFieldMap.get(field);
                    final MemberCustomFieldValue fieldValue = new MemberCustomFieldValue();
                    fieldValue.setField(customField);
                    fieldValue.setValue(preprocessCustomFieldValue(customField, value));
                    member.getCustomValues().add(fieldValue);
                    customFieldValues.put(field, value);
                } else if (field.contains(".")) {
                    // A record type value
                    final String[] parts = field.split("\\.");
                    // Find the record type
                    final String recordTypeName = parts[0];
                    final MemberRecordType recordType = recordTypeMap.get(recordTypeName);
                    if (recordType == null) {
                        member.setStatus(ImportedMember.Status.INVALID_RECORD_TYPE);
                        member.setErrorArgument1(recordTypeName);
                        break;
                    }
                    // Find the custom field
                    final String recordTypeField = parts[1];
                    final Map<String, CustomField> fieldsMap = recordTypeFieldsMap.get(recordType);
                    final CustomField customField = fieldsMap.get(recordTypeField);
                    if (customField == null) {
                        member.setStatus(ImportedMember.Status.INVALID_RECORD_TYPE_FIELD);
                        member.setErrorArgument1(recordTypeName);
                        member.setErrorArgument2(recordTypeField);
                        break;
                    }
                    // Find the imported member record
                    ImportedMemberRecord record = records.get(recordType);
                    if (record == null) {
                        // None yet - create a new one
                        record = new ImportedMemberRecord();
                        record.setMember(member);
                        record.setType(recordType);
                        record = importedMemberRecordDao.insert(record);
                        record.setCustomValues(new ArrayList<ImportedMemberRecordCustomFieldValue>());
                        records.put(recordType, record);
                    }
                    // Set the custom field
                    final ImportedMemberRecordCustomFieldValue fieldValue = new ImportedMemberRecordCustomFieldValue();
                    fieldValue.setField(customField);
                    fieldValue.setValue(preprocessCustomFieldValue(customField, value));
                    record.getCustomValues().add(fieldValue);

                    // Store the field value in a map
                    Map<String, String> fieldValues = recordFieldValues.get(recordType);
                    if (fieldValues == null) {
                        fieldValues = new HashMap<String, String>();
                        recordFieldValues.put(recordType, fieldValues);
                    }
                    fieldValues.put(recordTypeField, value);
                } else {
                    throw new UnknownColumnException(field);
                }
            }

            // When there was an error, stop processing
            if (member.getStatus() != ImportedMember.Status.SUCCESS) {
                return;
            }

            // Validate some data
            if (member.getName() == null) {
                // Name is always required
                member.setStatus(ImportedMember.Status.MISSING_NAME);
                return;
            }
            final String username = member.getUsername();
            if (accessSettings.getUsernameGeneration() == UsernameGeneration.NONE && username == null) {
                // Username is required when it's not generated
                member.setStatus(ImportedMember.Status.MISSING_USERNAME);
                return;
            }
            // Validate the username
            if (username != null) {
                // Check the username format
                ValidationError error = new RegexValidation(accessSettings.getUsernameRegex()).validate(null, null, username);
                if (error == null) {
                    // Check the username length
                    error = new LengthValidation(accessSettings.getUsernameLength()).validate(null, null, username);
                }
                if (error != null) {
                    member.setStatus(ImportedMember.Status.INVALID_USERNAME);
                    member.setErrorArgument1(username);
                    return;
                }
                // Check if username is duplicated in this import
                if (!importedUsernames.add(username)) {
                    member.setStatus(ImportedMember.Status.USERNAME_ALREADY_IN_USE);
                    member.setErrorArgument1(username);
                    return;
                }
                // Check if username is already used by another member in cyclos
                try {
                    elementService.loadUser(username);
                    // If an user could be loaded, it means that the username is already in use
                    member.setStatus(ImportedMember.Status.USERNAME_ALREADY_IN_USE);
                    member.setErrorArgument1(username);
                    return;
                } catch (final EntityNotFoundException e) {
                    // Ok - not used yet
                }
            }
            if (member.getEmail() == null && localSettings.isEmailRequired()) {
                // Mail is required
                member.setStatus(ImportedMember.Status.MISSING_EMAIL);
                return;
            }
            if (EmailValidation.instance().validate(null, null, member.getEmail()) != null) {
                // Mail format is invalid
                member.setStatus(ImportedMember.Status.INVALID_EMAIL);
                member.setErrorArgument1(member.getEmail());
                return;
            }

            if (memberImport.getAccountType() == null) {
                // Nothing related to accounts will be imported
                member.setInitialBalance(null);
                member.setCreditLimit(null);
                member.setUpperCreditLimit(null);
            } else {
                if (member.getCreditLimit() == null) {
                    // Get the default group credit limit
                    member.setCreditLimit(accountSettings.getDefaultCreditLimit());
                }
                if (member.getUpperCreditLimit() == null) {
                    // Get the default group upper credit limit
                    member.setUpperCreditLimit(accountSettings.getDefaultUpperCreditLimit());
                }
                final BigDecimal balance = member.getInitialBalance();

                if (balance != null) {
                    double balanceValue = balance.doubleValue();
                    if (balanceValue > 0 && memberImport.getInitialCreditTransferType() == null) {
                        // There was an initial credit, but no TT for it: ignore
                        member.setInitialBalance(null);
                        balanceValue = 0;
                    } else if (balanceValue < 0 && memberImport.getInitialDebitTransferType() == null) {
                        // There was an initial debit, but no TT for it: ignore
                        member.setInitialBalance(null);
                        balanceValue = 0;
                    }

                    final BigDecimal creditLimit = member.getCreditLimit();
                    if (creditLimit != null && balanceValue < 0 && balance.compareTo(creditLimit.negate()) < 0) {
                        // When the initial balance is negative, ensure the credit limit accommodates it
                        member.setStatus(ImportedMember.Status.BALANCE_LOWER_THAN_CREDIT_LIMIT);
                        return;
                    }

                    final BigDecimal upperCreditLimit = member.getUpperCreditLimit();
                    if (upperCreditLimit != null && balanceValue > 0 && balance.compareTo(upperCreditLimit) > 0) {
                        // When the initial balance is positive, ensure the credit limit accommodates it
                        member.setStatus(ImportedMember.Status.BALANCE_UPPER_THAN_CREDIT_LIMIT);
                        return;
                    }

                }
            }
            // Save the custom field values
            try {
                memberCustomFieldService.saveValues(member);
            } catch (final Exception e) {
                member.setStatus(ImportedMember.Status.INVALID_CUSTOM_FIELD);
                if (e instanceof ValidationException) {
                    final ValidationException vex = (ValidationException) e;
                    final Map<String, Collection<ValidationError>> errorsByProperty = vex.getErrorsByProperty();
                    if (MapUtils.isNotEmpty(errorsByProperty)) {
                        final String fieldName = errorsByProperty.keySet().iterator().next();
                        final String displayName = vex.getDisplayNameByProperty().get(fieldName);
                        member.setErrorArgument1(StringUtils.isEmpty(displayName) ? fieldName : displayName);
                        final String fieldValue = StringUtils.trimToNull(customFieldValues.get(fieldName.toLowerCase()));
                        if (CollectionUtils.isNotEmpty(errorsByProperty.get(fieldName))) {
                            ValidationError ve = errorsByProperty.get(fieldName).iterator().next();
                            if (ve instanceof UniqueError) {
                                member.setStatus(ImportedMember.Status.INVALID_CUSTOM_FIELD_VALUE_UNIQUE);
                                member.setErrorArgument2(ve.getArguments().iterator().next().toString());
                            } else if (ve instanceof RequiredError) {
                                member.setStatus(ImportedMember.Status.MISSING_CUSTOM_FIELD);
                            } else if (ve instanceof MaxLengthError) {
                                member.setStatus(ImportedMember.Status.INVALID_CUSTOM_FIELD_VALUE_MAX_LENGTH);
                                member.setErrorArgument2(ve.getArguments().iterator().next().toString());
                            } else if (ve instanceof MinLengthError) {
                                member.setStatus(ImportedMember.Status.INVALID_CUSTOM_FIELD_VALUE_MIN_LENGTH);
                                member.setErrorArgument2(ve.getArguments().iterator().next().toString());
                            }
                        }
                        if (StringUtils.isEmpty(member.getErrorArgument2()) && fieldValue != null) {
                            member.setErrorArgument2(fieldValue);
                        }
                    }
                }
                return;
            }

            // Save each record field values
            for (final ImportedMemberRecord record : records.values()) {
                final MemberRecordType recordType = record.getType();
                final Map<String, String> fieldValues = recordFieldValues.get(recordType);
                // Check if the record is not empty
                boolean empty = true;
                for (final String value : fieldValues.values()) {
                    if (StringUtils.isNotEmpty(value)) {
                        empty = false;
                        break;
                    }
                }
                if (empty) {
                    // There are no fields for this record: remove the record itself
                    importedMemberRecordDao.delete(record.getId());
                    continue;
                }
                try {
                    memberRecordCustomFieldService.saveValues(record);
                } catch (final Exception e) {
                    member.setStatus(ImportedMember.Status.INVALID_RECORD_FIELD);
                    if (e instanceof ValidationException) {
                        final ValidationException vex = (ValidationException) e;
                        final Map<String, Collection<ValidationError>> errorsByProperty = vex.getErrorsByProperty();
                        if (MapUtils.isNotEmpty(errorsByProperty)) {
                            final String fieldName = errorsByProperty.keySet().iterator().next();
                            member.setErrorArgument1(recordType.getName() + "." + fieldName);
                            final String fieldValue = StringUtils.trimToNull(fieldValues.get(fieldName));
                            if (fieldValue == null) {
                                // When validation failed and the field is null, it's actually missing
                                member.setStatus(ImportedMember.Status.MISSING_RECORD_FIELD);
                            } else {
                                member.setErrorArgument2(fieldValue);
                            }
                        }
                    }
                    return;
                }
            }

        } catch (final UnknownColumnException e) {
            throw e;
        } catch (final Exception e) {
            member.setStatus(ImportedMember.Status.UNKNOWN_ERROR);
            member.setErrorArgument1(e.toString());
        } finally {
            importedMemberDao.update(member);
        }
    }

    private String preprocessCustomFieldValue(final CustomField field, String value) {
        if (field.getType() == CustomField.Type.MEMBER) {
            // Attempt to load by username
            try {
                User user = elementService.loadUser(value);
                if (user instanceof MemberUser) {
                    value = user.getId().toString();
                }
            } catch (Exception e) {
                // Ok, leave as is - will fail later
            }
        }
        return value;
    }

    private void processMember(final MemberImport memberImport, ImportedMember importedMember, final boolean sendActivationMail) {
        importedMember = fetchService.fetch(importedMember, ImportedMember.Relationships.CUSTOM_VALUES, ImportedMember.Relationships.RECORDS);

        // Fill the member
        Member member = new Member();
        final MemberUser user = new MemberUser();
        member.setUser(user);
        member.setCreationDate(importedMember.getCreationDate());
        member.setGroup(memberImport.getGroup());
        member.setName(importedMember.getName());
        user.setSalt(importedMember.getSalt());
        user.setUsername(importedMember.getUsername());
        user.setPassword(importedMember.getPassword());
        member.setEmail(importedMember.getEmail());

        // Set the custom values
        fetchService.fetch(importedMember.getCustomValues(), CustomFieldValue.Relationships.FIELD, CustomFieldValue.Relationships.POSSIBLE_VALUE);
        customFieldHelper.cloneFieldValues(importedMember, member);

        // Insert the member
        member = elementService.insertMember(member, !sendActivationMail, false);

        // If the member is active and there was an imported creation date, set the activation date = imported creation date
        if (member.getActivationDate() != null && importedMember.getCreationDate() != null) {
            member.setActivationDate(importedMember.getCreationDate());
        }

        // Insert the records
        final Collection<ImportedMemberRecord> records = importedMember.getRecords();
        if (records != null) {
            for (ImportedMemberRecord importedRecord : records) {
                importedRecord = fetchService.fetch(importedRecord, ImportedMemberRecord.Relationships.CUSTOM_VALUES);
                final MemberRecord record = new MemberRecord();
                record.setElement(member);
                record.setType(importedRecord.getType());
                record.setCustomValues(new ArrayList<MemberRecordCustomFieldValue>());
                for (final ImportedMemberRecordCustomFieldValue importedValue : importedRecord.getCustomValues()) {
                    final CustomField field = importedValue.getField();
                    final MemberRecordCustomFieldValue recordValue = new MemberRecordCustomFieldValue();
                    recordValue.setField(field);
                    if (field.getType() == CustomField.Type.ENUMERATED) {
                        recordValue.setPossibleValue(importedValue.getPossibleValue());
                    } else if (field.getType() == CustomField.Type.MEMBER) {
                        recordValue.setMemberValue(importedValue.getMemberValue());
                    }
                    record.getCustomValues().add(recordValue);
                }
                memberRecordService.insert(record);
            }
        }

        // Handle the account
        final MemberAccountType accountType = memberImport.getAccountType();
        if (accountType != null) {
            // Set the credit limit
            final CreditLimitDTO limit = new CreditLimitDTO();
            limit.setLimitPerType(Collections.singletonMap(accountType, importedMember.getCreditLimit()));
            limit.setUpperLimitPerType(Collections.singletonMap(accountType, importedMember.getUpperCreditLimit()));
            accountService.setCreditLimit(member, limit);

            // Create the initial balance transaction
            final BigDecimal initialBalance = importedMember.getInitialBalance();
            if (initialBalance != null) {
                final double balance = initialBalance.doubleValue();
                TransferDTO transfer = null;
                if (balance < 0 && memberImport.getInitialDebitTransferType() != null) {
                    // Is a negative balance: use the debit TT
                    transfer = new TransferDTO();
                    transfer.setFromOwner(member);
                    transfer.setToOwner(SystemAccountOwner.instance());
                    transfer.setTransferType(memberImport.getInitialDebitTransferType());
                } else if (balance > 0 && memberImport.getInitialCreditTransferType() != null) {
                    // Is a positive balance: use the credit TT
                    transfer = new TransferDTO();
                    transfer.setFromOwner(SystemAccountOwner.instance());
                    transfer.setToOwner(member);
                    transfer.setTransferType(memberImport.getInitialCreditTransferType());
                }
                if (transfer != null) {
                    // If there was a transfer set, it will be used
                    transfer.setAutomatic(true);
                    transfer.setContext(TransactionContext.AUTOMATIC);
                    transfer.setAmount(initialBalance.abs());
                    transfer.setDescription(transfer.getTransferType().getDescription());
                    paymentService.insertWithoutNotification(transfer);
                }
            }
        }
    }
}
