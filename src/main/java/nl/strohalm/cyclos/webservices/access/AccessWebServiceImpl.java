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
package nl.strohalm.cyclos.webservices.access;

import javax.jws.WebService;

import nl.strohalm.cyclos.entities.access.Channel;
import nl.strohalm.cyclos.entities.access.Channel.Credentials;
import nl.strohalm.cyclos.entities.access.PrincipalType;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.access.AccessServiceLocal;
import nl.strohalm.cyclos.services.access.exceptions.BlockedCredentialsException;
import nl.strohalm.cyclos.services.access.exceptions.CredentialsAlreadyUsedException;
import nl.strohalm.cyclos.services.access.exceptions.InvalidCredentialsException;
import nl.strohalm.cyclos.services.elements.ElementServiceLocal;
import nl.strohalm.cyclos.services.settings.SettingsServiceLocal;
import nl.strohalm.cyclos.utils.validation.ValidationError;
import nl.strohalm.cyclos.utils.validation.ValidationException;
import nl.strohalm.cyclos.webservices.PrincipalParameters;
import nl.strohalm.cyclos.webservices.WebServiceContext;
import nl.strohalm.cyclos.webservices.utils.ChannelHelper;
import nl.strohalm.cyclos.webservices.utils.MemberHelper;
import nl.strohalm.cyclos.webservices.utils.WebServiceHelper;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Implementation for AccessWebService
 * 
 * @author luis
 */
@WebService(name = "access", serviceName = "access")
public class AccessWebServiceImpl implements AccessWebService {

    private SettingsServiceLocal settingsServiceLocal;
    private ChannelHelper        channelHelper;
    private ElementServiceLocal  elementServiceLocal;
    private AccessServiceLocal   accessServiceLocal;
    private MemberHelper         memberHelper;
    private WebServiceHelper     webServiceHelper;

    @Override
    public ChangeCredentialsStatus changeCredentials(final ChangeCredentialsParameters params) {

        // Get and validate the parameters
        final String principal = params == null ? null : StringUtils.trimToNull(params.getPrincipal());
        final String oldCredentials = params == null ? null : StringUtils.trimToNull(params.getOldCredentials());
        final String newCredentials = params == null ? null : StringUtils.trimToNull(params.getNewCredentials());
        if (principal == null || oldCredentials == null || newCredentials == null) {
            throw new ValidationException();
        }

        final Channel channel = WebServiceContext.getChannel();
        // Only login password and pin can be changed from here
        final Credentials credentials = channel.getCredentials();
        if (credentials != Credentials.LOGIN_PASSWORD && credentials != Credentials.PIN) {
            throw new PermissionDeniedException();
        }
        final PrincipalType principalType = channelHelper.resolvePrincipalType(params.getPrincipalType());

        // Load the member
        Member member;
        try {
            member = elementServiceLocal.loadByPrincipal(principalType, principal, Element.Relationships.GROUP, Element.Relationships.USER);
        } catch (final Exception e) {
            webServiceHelper.error(e);
            return ChangeCredentialsStatus.MEMBER_NOT_FOUND;
        }

        // Check the current credentials
        try {
            accessServiceLocal.checkCredentials(channel, member.getMemberUser(), oldCredentials, WebServiceContext.getRequest().getRemoteAddr(), WebServiceContext.getMember());
        } catch (final InvalidCredentialsException e) {
            webServiceHelper.error(e);
            return ChangeCredentialsStatus.INVALID_CREDENTIALS;
        } catch (final BlockedCredentialsException e) {
            webServiceHelper.error(e);
            return ChangeCredentialsStatus.BLOCKED_CREDENTIALS;
        }

        // The login password is numeric depending on settings. Others, are always numeric
        boolean numericPassword;
        if (credentials == Credentials.LOGIN_PASSWORD) {
            numericPassword = settingsServiceLocal.getAccessSettings().isNumericPassword();
        } else {
            numericPassword = true;
        }
        if (numericPassword && !StringUtils.isNumeric(newCredentials)) {
            return ChangeCredentialsStatus.INVALID_CHARACTERS;
        }

        // Change the password
        try {
            accessServiceLocal.changeCredentials(member.getMemberUser(), newCredentials);
        } catch (final ValidationException e) {
            if (CollectionUtils.isNotEmpty(e.getGeneralErrors())) {
                // Actually, the only possible general error is that it is the same as another credential.
                // In this case, we return CREDENTIALS_ALREADY_USED
                return ChangeCredentialsStatus.CREDENTIALS_ALREADY_USED;
            }
            // There is a property error. Let's scrap it to determine which is it
            try {
                final ValidationError error = e.getErrorsByProperty().values().iterator().next().iterator().next();
                final String key = error.getKey();
                if (key.endsWith("obvious")) {
                    // The password is too simple
                    return ChangeCredentialsStatus.TOO_SIMPLE;
                } else {
                    // Either must be numeric / must contain letters and numbers / must contain letters, numbers and special
                    throw new Exception();
                }
            } catch (final Exception e1) {
                // If there is some kind of unexpected validation result, just return as invalid
                webServiceHelper.error(e1);
                return ChangeCredentialsStatus.INVALID_CHARACTERS;
            }
        } catch (final CredentialsAlreadyUsedException e) {
            webServiceHelper.error(e);
            return ChangeCredentialsStatus.CREDENTIALS_ALREADY_USED;
        }
        return ChangeCredentialsStatus.SUCCESS;
    }

    @Override
    public CredentialsStatus checkCredentials(final CheckCredentialsParameters params) {
        if (WebServiceContext.getMember() != null) {
            // Only allow this method on unrestricted clients
            throw new PermissionDeniedException();
        }
        try {
            final Channel channel = WebServiceContext.getChannel();
            final PrincipalType principalType = channelHelper.resolvePrincipalType(params.getPrincipalType());
            final Member member = memberHelper.loadByPrincipal(principalType, params.getPrincipal());
            String credentials = params.getCredentials();
            if (channel.getCredentials() == Credentials.TRANSACTION_PASSWORD) {
                credentials = credentials.toUpperCase();
            }
            final String remoteAddr = WebServiceContext.getRequest().getRemoteAddr();
            accessServiceLocal.checkCredentials(channel, member.getMemberUser(), credentials, remoteAddr, null);
            return CredentialsStatus.VALID;
        } catch (final BlockedCredentialsException e) {
            webServiceHelper.error(e);
            return CredentialsStatus.BLOCKED;
        } catch (final Exception e) {
            webServiceHelper.error(e);
            return CredentialsStatus.INVALID;
        }
    }

    @Override
    public boolean isChannelEnabledForMember(final PrincipalParameters params) {
        Member member = WebServiceContext.getMember();
        if (member != null) {
            throw new PermissionDeniedException();
        }
        final PrincipalType principalType = channelHelper.resolvePrincipalType(params.getPrincipalType());
        member = elementServiceLocal.loadByPrincipal(principalType, params.getPrincipal());
        return memberHelper.isChannelEnabledForMember(member);
    }

    public void setAccessServiceLocal(final AccessServiceLocal accessService) {
        accessServiceLocal = accessService;
    }

    public void setChannelHelper(final ChannelHelper channelHelper) {
        this.channelHelper = channelHelper;
    }

    public void setElementServiceLocal(final ElementServiceLocal elementService) {
        elementServiceLocal = elementService;
    }

    public void setMemberHelper(final MemberHelper memberHelper) {
        this.memberHelper = memberHelper;
    }

    public void setSettingsServiceLocal(final SettingsServiceLocal settingsService) {
        settingsServiceLocal = settingsService;
    }

    public void setWebServiceHelper(final WebServiceHelper webServiceHelper) {
        this.webServiceHelper = webServiceHelper;
    }

}
