/*
   This file is part of Cyclos.

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
package nl.strohalm.cyclos.webservices.rest;

import java.util.ArrayList;
import java.util.List;

import nl.strohalm.cyclos.entities.access.Channel;
import nl.strohalm.cyclos.entities.access.Channel.Credentials;
import nl.strohalm.cyclos.entities.access.PrincipalType;
import nl.strohalm.cyclos.entities.customization.images.Image.Nature;
import nl.strohalm.cyclos.entities.customization.images.SystemImage;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.services.application.ApplicationService;
import nl.strohalm.cyclos.services.customization.ImageService;
import nl.strohalm.cyclos.services.customization.MemberCustomFieldService;
import nl.strohalm.cyclos.services.settings.SettingsService;
import nl.strohalm.cyclos.utils.MessageResolver;
import nl.strohalm.cyclos.webservices.WebServiceContext;
import nl.strohalm.cyclos.webservices.model.FieldVO;
import nl.strohalm.cyclos.webservices.model.ImageVO;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller which handles /general paths. This is the only REST service which runs without authentication.
 * 
 * @author luis
 */
@Controller
public class GeneralRestController extends BaseRestController {
    public static enum CredentialTypeVO {
        LOGIN_PASSWORD,
        TRANSACTION_PASSWORD,
        PIN,
        CARD_SECURITY_CODE
    }

    public static class GeneralData {
        private String           cyclosVersion;
        private String           applicationName;
        private String           welcomeMessage;
        private PrincipalTypeVO  principalType;
        private FieldVO          principalCustomField;
        private CredentialTypeVO credentialType;
        private List<ImageVO>    images;

        public String getApplicationName() {
            return applicationName;
        }

        public CredentialTypeVO getCredentialType() {
            return credentialType;
        }

        public String getCyclosVersion() {
            return cyclosVersion;
        }

        public List<ImageVO> getImages() {
            return images;
        }

        public FieldVO getPrincipalCustomField() {
            return principalCustomField;
        }

        public PrincipalTypeVO getPrincipalType() {
            return principalType;
        }

        public String getWelcomeMessage() {
            return welcomeMessage;
        }

        public void setApplicationName(final String applicationName) {
            this.applicationName = applicationName;
        }

        public void setCredentialType(final CredentialTypeVO credentialType) {
            this.credentialType = credentialType;
        }

        public void setCyclosVersion(final String cyclosVersion) {
            this.cyclosVersion = cyclosVersion;
        }

        public void setImages(final List<ImageVO> images) {
            this.images = images;
        }

        public void setPrincipalCustomField(final FieldVO principalCustomField) {
            this.principalCustomField = principalCustomField;
        }

        public void setPrincipalType(final PrincipalTypeVO principalType) {
            this.principalType = principalType;
        }

        public void setWelcomeMessage(final String welcomeMessage) {
            this.welcomeMessage = welcomeMessage;
        }

        @Override
        public String toString() {
            return "GeneralData [cyclosVersion=" + cyclosVersion + ", welcomeMessage=" + welcomeMessage + ", applicationName=" + applicationName + ", principalType=" + principalType + ", principalCustomField=" + principalCustomField + ", credentialType=" + credentialType + ", images=" + images + "]";
        }
    }

    public static enum PrincipalTypeVO {
        USER,
        EMAIL,
        CARD,
        CUSTOM_FIELD
    }

    private final static String      MOBILE_WELCOME_MESSAGE_KEY = "mobile.welcomeMessage";

    private SettingsService          settingsService;
    private ApplicationService       applicationService;
    private ImageService             imageService;
    private MemberCustomFieldService memberCustomFieldService;
    private MessageResolver          messageResolver;

    /**
     * Returns general metadata about the service
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "general", method = RequestMethod.GET)
    @ResponseBody
    public GeneralData getGeneralData() {
        Channel channel = WebServiceContext.getChannel();

        PrincipalType principalType = channel.getDefaultPrincipalType();

        // If the credentials is default, pass LOGIN_PASSWORD, as we want here the access password
        Credentials credentials = channel.getCredentials();
        if (credentials == Credentials.DEFAULT) {
            credentials = Credentials.LOGIN_PASSWORD;
        }

        LocalSettings settings = settingsService.getLocalSettings();
        GeneralData data = new GeneralData();
        data.setWelcomeMessage(messageResolver.message(MOBILE_WELCOME_MESSAGE_KEY, settings.getApplicationName()));
        data.setApplicationName(settings.getApplicationName());
        data.setCyclosVersion(applicationService.getCyclosVersion());
        data.setPrincipalType(PrincipalTypeVO.valueOf(principalType.getPrincipal().name()));
        if (principalType.getCustomField() != null) {
            data.setPrincipalCustomField(memberCustomFieldService.getFieldVO(principalType.getCustomField().getId()));
        }
        data.setCredentialType(CredentialTypeVO.valueOf(credentials.name()));
        List<ImageVO> imageVos = new ArrayList<ImageVO>();
        List<SystemImage> images = (List<SystemImage>) imageService.listByNature(Nature.SYSTEM);
        for (SystemImage systemImage : images) {
            String simpleName = systemImage.getSimpleName();
            if (simpleName.startsWith("mobileSplash")) {
                ImageVO vo = imageService.getImageVO(systemImage);
                vo.setCaption(simpleName);
                imageVos.add(vo);
            }
        }
        data.setImages(imageVos);

        return data;
    }

    public void setApplicationService(final ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    public void setImageService(final ImageService imageService) {
        this.imageService = imageService;
    }

    public void setMemberCustomFieldService(final MemberCustomFieldService memberCustomFieldService) {
        this.memberCustomFieldService = memberCustomFieldService;
    }

    public void setMessageResolver(final MessageResolver messageResolver) {
        this.messageResolver = messageResolver;
    }

    public void setSettingsService(final SettingsService settingsService) {
        this.settingsService = settingsService;
    }
}
