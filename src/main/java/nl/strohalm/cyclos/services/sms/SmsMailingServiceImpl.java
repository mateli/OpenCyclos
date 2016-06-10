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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nl.strohalm.cyclos.dao.sms.SmsMailingDAO;
import nl.strohalm.cyclos.entities.accounts.Account;
import nl.strohalm.cyclos.entities.accounts.AccountStatus;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.customization.fields.CustomField;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomField;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomFieldValue;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.entities.sms.SmsMailing;
import nl.strohalm.cyclos.entities.sms.SmsMailingQuery;
import nl.strohalm.cyclos.scheduling.polling.SmsMailingSendingPollingTask;
import nl.strohalm.cyclos.services.accounts.AccountDTO;
import nl.strohalm.cyclos.services.accounts.AccountServiceLocal;
import nl.strohalm.cyclos.services.application.ApplicationServiceLocal;
import nl.strohalm.cyclos.services.customization.MemberCustomFieldService;
import nl.strohalm.cyclos.services.customization.MemberCustomFieldServiceLocal;
import nl.strohalm.cyclos.services.elements.MemberServiceLocal;
import nl.strohalm.cyclos.services.fetch.FetchServiceLocal;
import nl.strohalm.cyclos.services.settings.SettingsServiceLocal;
import nl.strohalm.cyclos.utils.CustomFieldHelper;
import nl.strohalm.cyclos.utils.MessageResolver;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.utils.validation.PropertyValidation;
import nl.strohalm.cyclos.utils.validation.ValidationError;
import nl.strohalm.cyclos.utils.validation.ValidationException;
import nl.strohalm.cyclos.utils.validation.Validator;

import org.apache.commons.collections.CollectionUtils;

/**
 * Service implementation for sms mailings
 * 
 * @author luis
 */
public class SmsMailingServiceImpl implements SmsMailingServiceLocal {

    private class VariableEntry {
        String key;
        String value;

        public VariableEntry(final String key, final String value) {
            this.key = key;
            this.value = value;
        }
    }

    private SmsMailingDAO            smsMailingDao;
    private SettingsServiceLocal     settingsService;
    private MemberServiceLocal       memberService;
    private MemberCustomFieldService memberCustomFieldService;
    private ApplicationServiceLocal  applicationService;
    private MessageResolver          messageResolver;
    private FetchServiceLocal        fetchService;
    private AccountServiceLocal      accountService;
    private CustomFieldHelper        customFieldHelper;

    @Override
    public Map<String, String> getSmsTextVariables(final List<MemberGroup> groups) {
        return getSmsTextVariables(groups, null, true);

    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public Map<String, String> getSmsTextVariables(final List<MemberGroup> groups, final Member member, final boolean onlyVariableNames) {
        final LocalSettings localSettings = settingsService.getLocalSettings();
        Map<String, String> variables = new LinkedHashMap<String, String>();

        // system variables
        if (onlyVariableNames) {
            ArrayList<VariableEntry> vars = new ArrayList<VariableEntry>();
            vars.add(new VariableEntry("system_name", messageResolver.message("smsMailing.systemName")));
            sortByValueAndAddToMap(vars, variables);
        } else {
            variables.put("system_name", localSettings.getApplicationName());
        }

        // Add the element variables
        if (onlyVariableNames) {
            ArrayList<VariableEntry> vars = new ArrayList<VariableEntry>();
            vars.add(new VariableEntry("login", messageResolver.message("login.memberUsername")));
            vars.add(new VariableEntry("name", messageResolver.message("member.memberName")));
            vars.add(new VariableEntry("email", messageResolver.message("member.email")));
            sortByValueAndAddToMap(vars, variables);
        } else {
            variables.putAll((Map) member.getVariableValues(localSettings));
        }

        // Add the account variables
        if (onlyVariableNames) {
            ArrayList<VariableEntry> vars = new ArrayList<VariableEntry>();
            vars.add(new VariableEntry("balance", messageResolver.message("account.balance")));
            vars.add(new VariableEntry("available_balance", messageResolver.message("account.availableBalance")));
            vars.add(new VariableEntry("reserved_amount", messageResolver.message("account.reservedAmount")));
            vars.add(new VariableEntry("credit_limit", messageResolver.message("account.creditLimit")));
            vars.add(new VariableEntry("upper_credit_limit", messageResolver.message("account.upperCreditLimit")));
            sortByValueAndAddToMap(vars, variables);
        } else {
            final List<Account> allAccounts = (List<Account>) accountService.getAccounts(member, Account.Relationships.TYPE);
            final Account defaultAccount = accountService.getDefaultAccountFromList(member, allAccounts);
            AccountType defaultAccountType = defaultAccount == null ? null : defaultAccount.getType();

            final AccountStatus status = accountService.getCurrentStatus(new AccountDTO(member, defaultAccountType));
            variables.putAll((Map) status.getVariableValues(localSettings));
        }

        // Add the custom field variables
        if (onlyVariableNames) {
            final List<MemberCustomField> allFields = memberCustomFieldService.list();
            List<MemberCustomField> customFields = customFieldHelper.onlyInAllGroups(allFields, groups);
            ArrayList<VariableEntry> vars = new ArrayList<VariableEntry>();
            for (MemberCustomField mcf : customFields) {
                vars.add(new VariableEntry(mcf.getInternalName(), mcf.getName()));
            }
            sortByValueAndAddToMap(vars, variables);
        } else {
            Collection<MemberCustomFieldValue> values = member.getCustomValues();
            for (MemberCustomFieldValue fv : values) {
                CustomField cf = fv.getField();
                String fName = cf.getInternalName();
                String fValue = fv.getValue();
                variables.put(fName, fValue);
            }
        }

        return variables;
    }

    @Override
    public Map<String, String> getSmsTextVariables(Member member) {
        member = fetchService.fetch(member, Element.Relationships.GROUP);
        return getSmsTextVariables(Collections.singletonList((MemberGroup) member.getGroup()));
    }

    @Override
    public Member nextMemberToSend(final SmsMailing mailing) {
        return smsMailingDao.nextMemberToSend(mailing);
    }

    @Override
    public SmsMailing nextToSend() {
        SmsMailingQuery query = new SmsMailingQuery();
        query.setUniqueResult();
        query.setFinished(false);
        List<SmsMailing> list = search(query);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public void removeMemberFromPending(final SmsMailing smsMailing, final Member member) {
        smsMailingDao.removeMemberFromPending(smsMailing, member);
    }

    @Override
    public List<SmsMailing> search(final SmsMailingQuery query) {
        return smsMailingDao.search(query);
    }

    @Override
    public SmsMailing send(final SmsMailing smsMailing) {
        return doSend(smsMailing);
    }

    public void setAccountServiceLocal(final AccountServiceLocal accountService) {
        this.accountService = accountService;
    }

    public void setApplicationServiceLocal(final ApplicationServiceLocal applicationService) {
        this.applicationService = applicationService;
    }

    public void setCustomFieldHelper(final CustomFieldHelper customFieldHelper) {
        this.customFieldHelper = customFieldHelper;
    }

    public void setFetchServiceLocal(final FetchServiceLocal fetchService) {
        this.fetchService = fetchService;
    }

    public void setMemberCustomFieldServiceLocal(final MemberCustomFieldServiceLocal memberCustomFieldService) {
        this.memberCustomFieldService = memberCustomFieldService;
    }

    public void setMemberServiceLocal(final MemberServiceLocal memberService) {
        this.memberService = memberService;
    }

    public void setMessageResolver(final MessageResolver messageResolver) {
        this.messageResolver = messageResolver;
    }

    public void setSettingsServiceLocal(final SettingsServiceLocal settingsService) {
        this.settingsService = settingsService;
    }

    public void setSmsMailingDao(final SmsMailingDAO smsMailingDao) {
        this.smsMailingDao = smsMailingDao;
    }

    @Override
    public void validate(final SmsMailing smsMailing, final boolean isMemberRequired) throws ValidationException {
        if (isMemberRequired && CollectionUtils.isNotEmpty(smsMailing.getGroups())) {
            throw new ValidationException();
        }
        getValidator(isMemberRequired).validate(smsMailing);

        validateVariables(smsMailing, isMemberRequired);

    }

    private SmsMailing doSend(SmsMailing smsMailing) {
        validate(smsMailing, smsMailing.isSingleMember());
        smsMailing.setBy(LoggedUser.element());
        smsMailing.setDate(Calendar.getInstance());
        smsMailing.setSentSms(0);

        smsMailing = smsMailingDao.insert(smsMailing);

        // Send each SMS
        if (smsMailing.isSingleMember() || !CollectionUtils.isEmpty(smsMailing.getGroups())) {
            final MemberCustomField smsCustomField = settingsService.getSmsCustomField();
            if (smsCustomField == null) {
                throw new IllegalStateException("No custom field was set as SMS field under local settings");
            }
            smsMailingDao.assignUsersToSend(smsMailing, smsCustomField);
        }

        applicationService.awakePollingTaskOnTransactionCommit(SmsMailingSendingPollingTask.class);

        return smsMailing;
    }

    private Validator getValidator(final boolean isMemberRequired) {
        final Validator validator = new Validator("smsMailing");
        validator.property("text").required().maxLength(160);
        if (isMemberRequired) {
            validator.property("member").required().add(new PropertyValidation() {
                private static final long serialVersionUID = -20792899778722444L;

                @Override
                public ValidationError validate(final Object object, final Object property, final Object value) {
                    // Ensure the member has a mobile phone set
                    final Member member = (Member) value;
                    if (member == null) {
                        return null;
                    }

                    final MemberCustomField smsCustomField = settingsService.getSmsCustomField();
                    if (memberService.hasValueForField(member, smsCustomField)) {
                        return null;
                    }

                    return new ValidationError("smsMailing.error.noMobilePhone");
                }
            });
        }

        return validator;
    }

    private void sortByValueAndAddToMap(final ArrayList<VariableEntry> vars, final Map<String, String> variables) {
        Collections.sort(vars, new Comparator<VariableEntry>() {
            @Override
            public int compare(final VariableEntry o1, final VariableEntry o2) {
                return o1.value.compareTo(o2.value);
            }
        });
        for (VariableEntry variableEntry : vars) {
            variables.put(variableEntry.key, variableEntry.value);
        }
    }

    @SuppressWarnings("unchecked")
    private void validateVariables(final SmsMailing smsMailing, final boolean isMemberRequired) {
        // Validate variables
        String text = smsMailing.getText();
        // The only variables that can appear are the allowed:
        Map<String, String> variables = null;
        if (isMemberRequired) {
            variables = getSmsTextVariables(smsMailing.getMember());
        } else {
            variables = getSmsTextVariables(new ArrayList<MemberGroup>(smsMailing.getGroups()));
        }

        Set<String> parsedVariables = new HashSet<String>();
        Pattern pattern = Pattern.compile("#[a-zA-Z_][a-zA-Z\\d_]*#");
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            parsedVariables.add(matcher.group().replaceAll("#", ""));
        }

        Collection<String> unexpectedVariables = CollectionUtils.subtract(parsedVariables, variables.keySet());
        if (CollectionUtils.isNotEmpty(unexpectedVariables)) {
            throw new ValidationException("smsMailing.error.variableNotFound", unexpectedVariables);
        }
    }
}
