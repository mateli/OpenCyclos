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

import org.apache.struts.action.ActionForm;

/**
 * Form used to edit a message setting
 * @author luis
 */
public class EditMessageSettingForm extends ActionForm {

    private static final long serialVersionUID = -9189257236726821589L;

    private String            setting;
    private String            value;
    private String            subject;
    private String            body;
    private String            sms;
    private boolean           hasGeneral;
    private boolean           hasSubject;
    private boolean           hasBody;
    private boolean           hasSms;

    public String getBody() {
        return body;
    }

    public String getSetting() {
        return setting;
    }

    public String getSms() {
        return sms;
    }

    public String getSubject() {
        return subject;
    }

    public String getValue() {
        return value;
    }

    public boolean isHasBody() {
        return hasBody;
    }

    public boolean isHasGeneral() {
        return hasGeneral;
    }

    public boolean isHasSms() {
        return hasSms;
    }

    public boolean isHasSubject() {
        return hasSubject;
    }

    public void setBody(final String body) {
        this.body = body;
    }

    public void setHasBody(final boolean hasBody) {
        this.hasBody = hasBody;
    }

    public void setHasGeneral(final boolean hasGeneral) {
        this.hasGeneral = hasGeneral;
    }

    public void setHasSms(final boolean hasSms) {
        this.hasSms = hasSms;
    }

    public void setHasSubject(final boolean hasSubject) {
        this.hasSubject = hasSubject;
    }

    public void setSetting(final String setting) {
        this.setting = setting;
    }

    public void setSms(final String sms) {
        this.sms = sms;
    }

    public void setSubject(final String subject) {
        this.subject = subject;
    }

    public void setValue(final String value) {
        this.value = value;
    }

}
