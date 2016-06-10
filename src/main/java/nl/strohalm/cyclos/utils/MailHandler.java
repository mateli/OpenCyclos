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
package nl.strohalm.cyclos.utils;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.Operator;
import nl.strohalm.cyclos.entities.members.PendingEmailChange;
import nl.strohalm.cyclos.entities.members.PendingMember;
import nl.strohalm.cyclos.entities.members.messages.MessageCategory;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.entities.settings.MailSettings;
import nl.strohalm.cyclos.entities.settings.MailTranslation;
import nl.strohalm.cyclos.entities.settings.MessageSettings;
import nl.strohalm.cyclos.exceptions.MailSendingException;
import nl.strohalm.cyclos.services.settings.SettingsServiceLocal;
import nl.strohalm.cyclos.utils.transaction.CurrentTransactionData;
import nl.strohalm.cyclos.utils.transaction.TransactionCommitListener;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

/**
 * Handles mail sending
 * @author luis
 */
public class MailHandler implements InitializingBean, DisposableBean {

    private class MailParameters {
        private String          subject;
        private InternetAddress replyTo;
        private InternetAddress to;
        private String          body;
        private boolean         isHTML;
    }

    private class SenderThreads extends WorkerThreads<MailParameters> {

        public SenderThreads(final String name, final int threadCount) {
            super(name, threadCount);
        }

        @Override
        protected void process(final MailParameters params) {
            doSend(params.subject, params.replyTo, params.to, params.body, params.isHTML, true);
        }
    }

    private SettingsServiceLocal settingsService;
    private LinkGenerator        linkGenerator;
    private MessageResolver      messageResolver = new MessageResolver.NoOpMessageResolver();
    private SenderThreads        senderThreads;
    private int                  maxThreads      = 5;

    @Override
    public void afterPropertiesSet() throws Exception {
        senderThreads = new SenderThreads("Cyclos mail sender", maxThreads);
    }

    @Override
    public void destroy() throws Exception {
        if (senderThreads != null) {
            senderThreads.interrupt();
            senderThreads = null;
        }
    }

    /**
     * Returns the mail address for the given Element
     */
    public InternetAddress getInternetAddress(final Element element) {
        if (element == null) {
            return null;
        }
        return getInternetAddress(element.getName(), element.getEmail());
    }

    /**
     * Returns the mail address for the given PendingMember
     */
    public InternetAddress getInternetAddress(final PendingMember pendingMember) {
        if (pendingMember == null) {
            return null;
        }
        return getInternetAddress(pendingMember.getName(), pendingMember.getEmail());
    }

    /**
     * Returns the mail address for the given Element
     */
    public InternetAddress getInternetAddress(String name, final String email) {
        if (StringUtils.isEmpty(email)) {
            return null;
        }
        final LocalSettings localSettings = settingsService.getLocalSettings();
        if (StringUtils.isEmpty(name)) {
            name = email;
        }
        try {
            return new InternetAddress(email, name, localSettings.getCharset());
        } catch (final UnsupportedEncodingException e) {
            // Shouldn't happen!!!
            return null;
        }
    }

    /**
     * Returns the mail address
     */
    public InternetAddress getMailAddress(final String mail) {
        final LocalSettings localSettings = settingsService.getLocalSettings();
        try {
            return new InternetAddress(mail, mail, localSettings.getCharset());
        } catch (final UnsupportedEncodingException e) {
            // Shouldn't happen!!!
            return null;
        }
    }

    /**
     * Returns the mail address for the given element, but returns null when a member hides his mail
     */
    public InternetAddress getReplyAddress(Element element) {
        // When an operator, check his member
        if (element instanceof Operator) {
            element = ((Operator) element).getMember();
        }
        // Check whether the mail is hidden
        if (element instanceof Member) {
            final Member member = (Member) element;
            if (member.isHideEmail()) {
                return null;
            }
        }
        return getInternetAddress(element);
    }

    /**
     * Returns the mail address for system
     */
    public InternetAddress getSystemAddress() {
        final LocalSettings localSettings = settingsService.getLocalSettings();
        final MailSettings mailSettings = settingsService.getMailSettings();
        try {
            return new InternetAddress(mailSettings.getFromMail(), localSettings.getApplicationName(), localSettings.getCharset());
        } catch (final UnsupportedEncodingException e) {
            // Shouldn't happen!!!
            return null;
        }
    }

    /**
     * Process the body of a message that's being sent by mail, appending the from member, the category and a suffix
     */
    public String processBody(final Element owner, final String body, final Member member, final MessageCategory category, final boolean isHtml) {
        final LocalSettings localSettings = settingsService.getLocalSettings();
        final String lineSep = isHtml ? "<br>" : "\n";
        final StringBuilder sb = new StringBuilder();
        // When there is a member, it's from member (obviously). When there's a category, from the administration.
        // When neither, it's automatically generated from system
        boolean appendExtraLine = false;
        if (member != null || category != null) {
            sb.append(processLabel("message.from", isHtml));
            if (member == null) {
                sb.append(localSettings.getApplicationUsername());
            } else {
                sb.append(member.getName()).append(" (").append(member.getUsername()).append(")");
            }
            sb.append(lineSep);
            appendExtraLine = true;
        }
        // Append the category, if any
        if (category != null) {
            sb.append(processLabel("message.category", isHtml));
            sb.append(category.getName());
            sb.append(lineSep);
            appendExtraLine = true;
        }
        if (appendExtraLine) {
            sb.append(lineSep);
        }
        sb.append(body);
        sb.append(lineSep);
        sb.append(lineSep);
        final MessageSettings messageSettings = settingsService.getMessageSettings();

        // Append and process the suffix
        String suffix;
        if (isHtml) {
            suffix = messageSettings.getMessageMailSuffixHtml();
        } else {
            suffix = messageSettings.getMessageMailSuffixPlain();
        }
        final Map<String, String> variables = new HashMap<String, String>();
        variables.put("link", getRootUrl(owner, isHtml, true));
        variables.put("system_name", localSettings.getApplicationName());
        sb.append(MessageProcessingHelper.processVariables(suffix, variables));

        return sb.toString();
    }

    /**
     * Process the body of a message that's being sent by mail, appending the prefix
     */
    public String processSubject(final Element owner, final String subject) {
        final LocalSettings localSettings = settingsService.getLocalSettings();
        final MessageSettings messageSettings = settingsService.getMessageSettings();

        String prefix = StringUtils.trimToEmpty(messageSettings.getMessageMailSubjectPrefix());
        final Map<String, String> variables = new HashMap<String, String>();
        variables.put("link", getRootUrl(owner, false, true));
        variables.put("system_name", localSettings.getApplicationName());
        prefix = MessageProcessingHelper.processVariables(prefix, variables);

        return StringUtils.trimToEmpty(prefix + " " + subject);
    }

    /**
     * Sends an arbitrary mail message
     */
    public void send(final String subject, final InternetAddress replyTo, final InternetAddress to, final String body, final boolean isHTML) {
        doSend(subject, replyTo, to, body, isHTML, false);
    }

    public void sendActivation(final boolean threaded, final Member member, final String password) {
        final LocalSettings localSettings = settingsService.getLocalSettings();
        final Map<String, Object> variableValues = member.getVariableValues(localSettings);
        variableValues.put("password", password);
        variableValues.put("system_name", localSettings.getApplicationName());
        variableValues.put("link", getRootUrl(member, true, false));
        variableValues.put("url", getRootUrl(member, true, true));

        final MailTranslation mailTranslation = settingsService.getMailTranslation();
        final String subject = mailTranslation.getActivationSubject();
        String body;
        if (password == null) {
            body = mailTranslation.getActivationMessageWithoutPassword();
        } else {
            body = mailTranslation.getActivationMessageWithPassword();
        }
        sendInternal(threaded, getInternetAddress(member), variableValues, subject, body);
    }

    /**
     * Sends an email only after the current transaction is committed
     */
    public void sendAfterTransactionCommit(final String subject, final InternetAddress replyTo, final InternetAddress to, final String body, final boolean isHTML) {
        if (senderThreads == null) {
            return;
        }
        CurrentTransactionData.addTransactionCommitListener(new TransactionCommitListener() {
            @Override
            public void onTransactionCommit() {
                final MailParameters params = new MailParameters();
                params.subject = subject;
                params.replyTo = replyTo;
                params.to = to;
                params.body = body;
                params.isHTML = isHTML;
                senderThreads.enqueue(params);
            }
        });
    }

    public void sendEmailChange(final PendingEmailChange pendingEmailChange) {
        final LocalSettings localSettings = settingsService.getLocalSettings();
        final Map<String, Object> variableValues = pendingEmailChange.getVariableValues(localSettings);
        variableValues.put("system_name", localSettings.getApplicationName());
        variableValues.put("link", linkGenerator.generateLinkForMailChangeValidation(pendingEmailChange));
        variableValues.put("url", linkGenerator.getMailChangeValidationUrl(pendingEmailChange));

        final MailTranslation mailTranslation = settingsService.getMailTranslation();
        final String subject = mailTranslation.getMailChangeValidationSubject();
        final String body = mailTranslation.getMailChangeValidationMessage();

        Member member = pendingEmailChange.getMember();
        InternetAddress internetAddress = getInternetAddress(member.getName(), pendingEmailChange.getNewEmail());
        sendInternal(false, internetAddress, variableValues, subject, body);
    }

    public void sendEmailValidation(final PendingMember pendingMember) {
        final LocalSettings localSettings = settingsService.getLocalSettings();
        final Map<String, Object> variableValues = pendingMember.getVariableValues(localSettings);
        variableValues.put("system_name", localSettings.getApplicationName());
        final String key = pendingMember.getValidationKey();
        variableValues.put("link", linkGenerator.generateLinkForMailValidation(pendingMember.getMemberGroup(), key));
        variableValues.put("url", linkGenerator.getMailValidationUrl(pendingMember.getMemberGroup(), key));

        final MailTranslation mailTranslation = settingsService.getMailTranslation();
        final String subject = mailTranslation.getMailValidationSubject();
        final String body = mailTranslation.getMailValidationMessage();

        sendInternal(false, getInternetAddress(pendingMember), variableValues, subject, body);
    }

    public void sendInvitation(final Element fromElement, final String toMail) {
        final LocalSettings localSettings = settingsService.getLocalSettings();
        final Map<String, Object> variableValues = fromElement.getVariableValues(localSettings);
        variableValues.put("system_name", localSettings.getApplicationName());
        variableValues.put("link", getRootUrl(fromElement, true, true));

        final MailTranslation mailTranslation = settingsService.getMailTranslation();
        final String subject = mailTranslation.getInvitationSubject();
        final String body = mailTranslation.getInvitationMessage();

        sendInternal(false, getMailAddress(toMail), variableValues, subject, body);
    }

    public void sendResetPassword(final Member member, final String password) {
        final LocalSettings localSettings = settingsService.getLocalSettings();
        final Map<String, Object> variableValues = member.getVariableValues(localSettings);
        variableValues.put("password", password);
        variableValues.put("system_name", localSettings.getApplicationName());
        variableValues.put("link", getRootUrl(member, true, true));

        final MailTranslation mailTranslation = settingsService.getMailTranslation();
        final String subject = mailTranslation.getResetPasswordSubject();
        final String body = mailTranslation.getResetPasswordMessage();

        sendInternal(false, getInternetAddress(member), variableValues, subject, body);
    }

    public void setLinkGenerator(final LinkGenerator linkGenerator) {
        this.linkGenerator = linkGenerator;
    }

    public void setMaxThreads(final int maxThreads) {
        this.maxThreads = maxThreads;
    }

    public void setMessageResolver(final MessageResolver messageResolver) {
        this.messageResolver = messageResolver;
    }

    public void setSettingsServiceLocal(final SettingsServiceLocal settingsService) {
        this.settingsService = settingsService;
    }

    private void doSend(final String subject, final InternetAddress replyTo, final InternetAddress to, final String body, final boolean isHTML, final boolean throwException) {
        if (to == null || StringUtils.isEmpty(to.getAddress())) {
            return;
        }
        final LocalSettings localSettings = settingsService.getLocalSettings();
        final MailSettings mailSettings = settingsService.getMailSettings();
        final JavaMailSender mailSender = mailSettings.getMailSender();
        final MimeMessage message = mailSender.createMimeMessage();
        final MimeMessageHelper helper = new MimeMessageHelper(message, localSettings.getCharset());

        try {
            helper.setFrom(getSystemAddress());
            if (replyTo != null) {
                helper.setReplyTo(replyTo);
            }
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, isHTML);
            mailSender.send(message);
        } catch (final MessagingException e) {
            if (throwException) {
                throw new MailSendingException(subject, e);
            }
            // Store the current Exception
            CurrentTransactionData.setMailError(new MailSendingException(subject, e));
        } catch (final MailException e) {
            if (throwException) {
                throw new MailSendingException(subject, e);
            }
            CurrentTransactionData.setMailError(new MailSendingException(subject, e));
        }
    }

    private String getRootUrl(final Element element, final boolean isHtml, final boolean useUrlAsLink) {
        if (linkGenerator == null) {
            return "";
        }
        final String rootUrl = linkGenerator.getRootUrl(element);
        if (isHtml) {
            if (useUrlAsLink) {
                return "<a href='" + rootUrl + "'>" + rootUrl + "</a>";
            } else {
                return linkGenerator.generateForApplicationRoot(element);
            }
        } else {
            return rootUrl;
        }
    }

    private String processLabel(final String key, final boolean isHtml) {
        String prefix = "";
        String suffix = "";
        if (isHtml) {
            prefix = "<b>";
            suffix = "</b>";
        }
        return prefix + messageResolver.message(key) + ":" + suffix + " ";
    }

    private void sendInternal(final boolean threaded, final InternetAddress to, final Map<String, Object> variables, final String subject, final String body) {
        final String processedSubject = MessageProcessingHelper.processVariables(subject, variables);
        final String processedBody = MessageProcessingHelper.processVariables(body, variables);
        if (threaded) {
            sendAfterTransactionCommit(processedSubject, null, to, processedBody, true);
        } else {
            send(processedSubject, null, to, processedBody, true);
        }
    }

}
