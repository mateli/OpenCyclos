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
package nl.strohalm.cyclos.entities.settings;

import nl.strohalm.cyclos.utils.DataObject;

/**
 * Groups mail settings
 * @author luis
 */
public class MailTranslation extends DataObject {

    private static final long serialVersionUID                 = 5541315164617978971L;
    private String            invitationSubject                = "Invitation to join #system_name#";
    private String            invitationMessage                = "The member #member# sent you an invitation to explore #system_name#.<br>To access the software, #link#";

    private String            activationSubject                = "Welcome to #system_name#";
    private String            activationMessageWithPassword    = "Hello #member#.<br><br>Your account on #system_name# has been activated.<br><br>You can access the system with the login name #login# and the password #password#. This password is temporary, and should be changed after the first login.<br><br>To access the software, #link#<br><br> Kind Regards,<br> The #system_name# team.";
    private String            activationMessageWithoutPassword = "Hello #member#.<br><br>Your account on #system_name# has been activated.<br><br>You can access the system with the login name #login#.<br><br>To access the software, #link# <br><br> Kind Regards,<br> The #system_name# team.";

    private String            resetPasswordSubject             = "#system_name# password reset";
    private String            resetPasswordMessage             = "Hello #member#.<br><br>Your password has been reset.<br>You can now login as #login# with password #password#.<br>You will have to change your password on your next login.<br>To access the software, #link#";

    private String            mailValidationSubject            = "#system_name# e-mail validation";
    private String            mailValidationMessage            = "Hello #member#, you have been registered on #system_name#, but first, the e-mail address entered must be validated.<br><br>In order to validate the registration, please click the link below:<br><br>#link#<br><br>Thanks for registering on #system_name#. If you haven't registered or wasn't expecting this mail, please, ignore it.";

    private String            mailChangeValidationSubject      = "#system_name# e-mail change validation";
    private String            mailChangeValidationMessage      = "Hello #member#.<br><br>You (or someone in your behalf) have changed your e-mail in #system_name# to #new_email#, and this action must be confirmed.<br><br>In order to validate the e-mail change, please click the link below:<br><br>#link#<br><br>If you haven't performed this action or wasn't expecting this mail, please, ignore it.";

    public String getActivationMessageWithoutPassword() {
        return activationMessageWithoutPassword;
    }

    public String getActivationMessageWithPassword() {
        return activationMessageWithPassword;
    }

    public String getActivationSubject() {
        return activationSubject;
    }

    public String getInvitationMessage() {
        return invitationMessage;
    }

    public String getInvitationSubject() {
        return invitationSubject;
    }

    public String getMailChangeValidationMessage() {
        return mailChangeValidationMessage;
    }

    public String getMailChangeValidationSubject() {
        return mailChangeValidationSubject;
    }

    public String getMailValidationMessage() {
        return mailValidationMessage;
    }

    public String getMailValidationSubject() {
        return mailValidationSubject;
    }

    public String getResetPasswordMessage() {
        return resetPasswordMessage;
    }

    public String getResetPasswordSubject() {
        return resetPasswordSubject;
    }

    public void setActivationMessageWithoutPassword(final String activationMessageWithoutPassword) {
        this.activationMessageWithoutPassword = activationMessageWithoutPassword;
    }

    public void setActivationMessageWithPassword(final String activationMessageWithPassword) {
        this.activationMessageWithPassword = activationMessageWithPassword;
    }

    public void setActivationSubject(final String activationSubject) {
        this.activationSubject = activationSubject;
    }

    public void setInvitationMessage(final String invitationMessage) {
        this.invitationMessage = invitationMessage;
    }

    public void setInvitationSubject(final String invitationSubject) {
        this.invitationSubject = invitationSubject;
    }

    public void setMailChangeValidationMessage(final String mailChangeValidationMessage) {
        this.mailChangeValidationMessage = mailChangeValidationMessage;
    }

    public void setMailChangeValidationSubject(final String mailChangeValidationSubject) {
        this.mailChangeValidationSubject = mailChangeValidationSubject;
    }

    public void setMailValidationMessage(final String mailValidationMessage) {
        this.mailValidationMessage = mailValidationMessage;
    }

    public void setMailValidationSubject(final String mailValidationSubject) {
        this.mailValidationSubject = mailValidationSubject;
    }

    public void setResetPasswordMessage(final String resetPasswordMessage) {
        this.resetPasswordMessage = resetPasswordMessage;
    }

    public void setResetPasswordSubject(final String resetPasswordSubject) {
        this.resetPasswordSubject = resetPasswordSubject;
    }

}
