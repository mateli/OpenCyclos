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
package nl.strohalm.cyclos.controls.settings;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.entities.settings.MessageSettings;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.PropertyHelper;
import nl.strohalm.cyclos.utils.TextFormat;
import nl.strohalm.cyclos.utils.binding.PropertyException;
import nl.strohalm.cyclos.utils.conversion.HtmlConverter;
import nl.strohalm.cyclos.utils.conversion.StringTrimmerConverter;
import nl.strohalm.cyclos.utils.validation.ValidationException;
import nl.strohalm.cyclos.utils.validation.Validator;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForward;

/**
 * Action used to edit the a message setting
 * @author luis
 */
public class EditMessageSettingAction extends BaseFormAction {
    public static final int MAX_SUBJECT_SIZE = 400;
    public static final int MAX_BODY_SIZE    = 4000;
    public static final int MAX_SMS_SIZE     = 256;

    @Override
    protected ActionForward handleSubmit(final ActionContext context) throws Exception {
        final EditMessageSettingForm form = context.getForm();
        final String setting = form.getSetting();
        StringUtils.trimToNull(form.getValue());
        final MessageSettings messageSettings = settingsService.getMessageSettings();
        if (form.isHasGeneral()) {
            final boolean isHtml = setting.toLowerCase().endsWith("html");
            String value;
            if (isHtml) {
                value = HtmlConverter.instance().valueOf(form.getValue());
            } else {
                value = StringTrimmerConverter.instance().valueOf(form.getValue());
            }
            PropertyHelper.set(messageSettings, setting, value);
        }
        if (form.isHasSubject()) {
            PropertyHelper.set(messageSettings, setting + "Subject", StringTrimmerConverter.instance().valueOf(form.getSubject()));
        }
        if (form.isHasBody()) {
            PropertyHelper.set(messageSettings, setting + "Message", HtmlConverter.instance().valueOf(form.getBody()));
        }
        if (form.isHasSms()) {
            PropertyHelper.set(messageSettings, setting + "Sms", StringTrimmerConverter.instance().valueOf(form.getSms()));
        }
        settingsService.save(messageSettings);

        context.sendMessage("settings.message.modified");
        return ActionHelper.redirectWithParam(context.getRequest(), context.getSuccessForward(), "setting", setting);
    }

    @Override
    protected void prepareForm(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();
        final EditMessageSettingForm form = context.getForm();
        final MessageSettings messageSettings = settingsService.getMessageSettings();
        final String setting = StringUtils.trimToNull(form.getSetting());
        if (setting == null) {
            throw new ValidationException();
        }

        // Try the setting name itself (like global settings)
        try {
            final String value = PropertyHelper.get(messageSettings, setting);
            form.setValue(value);
            form.setHasGeneral(true);
            request.setAttribute("generalIsHtml", setting.toLowerCase().endsWith("html"));
        } catch (final PropertyException e) {
            // Ignore - probably didn't have this setting
        }

        // Try the subject
        try {
            final String property = setting + "Subject";
            final String value = PropertyHelper.get(messageSettings, property);
            form.setSubject(value);
            form.setHasSubject(true);
        } catch (final PropertyException e) {
            // Ignore - probably didn't have this setting
        }

        // Try the body
        try {
            final String property = setting + "Message";
            final String value = PropertyHelper.get(messageSettings, property);
            form.setBody(value);
            form.setHasBody(true);
        } catch (final PropertyException e) {
            // Ignore - probably didn't have this setting
        }

        // Try the sms
        try {
            final String property = setting + "Sms";
            final String value = PropertyHelper.get(messageSettings, property);
            form.setSms(value);
            form.setHasSms(true);
        } catch (final PropertyException e) {
            // Ignore - probably didn't have this setting
        }

        request.setAttribute("setting", setting);
        request.setAttribute("format", TextFormat.RICH);
    }

    @Override
    protected void validateForm(final ActionContext context) {
        final EditMessageSettingForm form = context.getForm();
        final Validator validator = new Validator("settings.message");
        if (form.isHasGeneral()) {
            validator.property("value").required().maxLength(MAX_SUBJECT_SIZE);
        }
        if (form.isHasSubject()) {
            validator.property("subject").required().maxLength(MAX_SUBJECT_SIZE);
        }
        if (form.isHasBody()) {
            validator.property("body").required().maxLength(MAX_BODY_SIZE);
        }
        if (form.isHasSms()) {
            validator.property("sms").required().maxLength(MAX_SMS_SIZE);
        }
        validator.validate(form);
    }

}
