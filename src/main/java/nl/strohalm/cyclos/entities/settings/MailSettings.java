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

import java.util.Properties;

import nl.strohalm.cyclos.utils.DataObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * Groups mail settings
 * @author luis
 */
public class MailSettings extends DataObject {

    private static final long        serialVersionUID = 5208856489563893834L;

    private String                   fromMail         = "noreply@cyclos.org";
    private String                   smtpServer       = "localhost";
    private int                      smtpPort         = 25;
    private String                   smtpUsername;
    private String                   smtpPassword;
    private boolean                  smtpUseTLS       = false;
    private transient JavaMailSender mailSender;

    public String getFromMail() {
        return fromMail;
    }

    public JavaMailSender getMailSender() {
        if (mailSender == null) {
            final JavaMailSenderImpl impl = new JavaMailSenderImpl();
            impl.setHost(smtpServer);
            impl.setPort(smtpPort);
            final Properties properties = new Properties();
            if (StringUtils.isNotEmpty(smtpUsername)) {
                // Use authentication
                properties.setProperty("mail.smtp.auth", "true");
                impl.setUsername(smtpUsername);
                impl.setPassword(smtpPassword);
            }
            if (smtpUseTLS) {
                properties.setProperty("mail.smtp.starttls.enable", "true");
            }
            impl.setJavaMailProperties(properties);
            mailSender = impl;
        }
        return mailSender;
    }

    public String getSmtpPassword() {
        return smtpPassword;
    }

    public int getSmtpPort() {
        return smtpPort;
    }

    public String getSmtpServer() {
        return smtpServer;
    }

    public String getSmtpUsername() {
        return smtpUsername;
    }

    public boolean isSmtpAuthenticate() {
        return StringUtils.isNotEmpty(smtpUsername);
    }

    public boolean isSmtpUseTLS() {
        return smtpUseTLS;
    }

    public void setFromMail(final String fromMail) {
        this.fromMail = fromMail;
        mailSender = null;
    }

    public void setSmtpPassword(final String smtpPassword) {
        this.smtpPassword = smtpPassword;
        mailSender = null;
    }

    public void setSmtpPort(final int smtpPort) {
        this.smtpPort = smtpPort;
        mailSender = null;
    }

    public void setSmtpServer(final String smtpServer) {
        this.smtpServer = smtpServer;
        mailSender = null;
    }

    public void setSmtpUsername(final String smtpUsername) {
        this.smtpUsername = smtpUsername;
        mailSender = null;
    }

    public void setSmtpUseTLS(final boolean smtpUseTLS) {
        this.smtpUseTLS = smtpUseTLS;
        mailSender = null;
    }
}
