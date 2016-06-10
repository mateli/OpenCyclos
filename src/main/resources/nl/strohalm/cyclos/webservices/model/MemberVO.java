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
package nl.strohalm.cyclos.webservices.model;

import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Member data for web services
 * @author luis
 */
@XmlType(name = "member")
public class MemberVO extends EntityWithCustomFieldsVO {
    private static final long serialVersionUID = 366127525637277472L;

    private String            name;
    private String            username;
    private String            email;
    private Long              groupId;
    private List<ImageVO>     images;

    public String getEmail() {
        return email;
    }

    @JsonIgnore
    public Long getGroupId() {
        return groupId;
    }

    public List<ImageVO> getImages() {
        return images;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public void setGroupId(final Long groupId) {
        this.groupId = groupId;
    }

    public void setImages(final List<ImageVO> images) {
        this.images = images;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        final StringBuilder buffer = new StringBuilder();
        buffer.append(" (username=" + username + ", name=" + name + ", email=" + email);

        final Map<String, String> fields = getFieldsMap();
        if (fields != null && !fields.isEmpty()) {
            buffer.append(", fields=" + fields);
        }

        if (images != null) {
            buffer.append(", #images=" + images.size());
        } else {
            buffer.append(", #images=0");
        }

        buffer.append(")");

        return buffer.toString();
    }

}
