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
package nl.strohalm.cyclos.controls.members;

import java.util.Map;

import nl.strohalm.cyclos.controls.elements.CreateElementForm;

/**
 * Form used to create a member
 * @author luis
 */
public class CreateMemberForm extends CreateElementForm {
    private static final long serialVersionUID = -8054408188398196588L;
    private String            captcha;

    public String getCaptcha() {
        return captcha;
    }

    public Map<String, Object> getMember() {
        return values;
    }

    public Object getMember(final String key) {
        return values.get(key);
    }

    public void setCaptcha(final String captcha) {
        this.captcha = captcha;
    }

    public void setMember(final Map<String, Object> member) {
        values = member;
    }

    public void setMember(final String key, final Object value) {
        values.put(key, value);
    }
}
