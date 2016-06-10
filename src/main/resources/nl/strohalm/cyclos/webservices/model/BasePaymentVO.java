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

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Base class for payments
 * 
 * @author luis
 */
public abstract class BasePaymentVO extends BasePaymentDataVO {
    private static final long     serialVersionUID = 3440176277262171427L;
    protected TransferTypeVO      transferType;
    protected String              description;
    // this field is set only for a non-restricted member
    protected MemberVO            fromMember;
    // this field is the related (other side) member for a restricted member
    protected MemberVO            member;
    // this field is set only for a non-restricted member
    protected String              fromSystemAccountName;
    // this field is the related (other side) system account for a restricted member
    protected String              systemAccountName;

    @XmlTransient
    protected Map<String, String> fieldsMap;
    protected List<FieldValueVO>  fields;

    public String getDescription() {
        return description;
    }

    @JsonProperty("customValues")
    public List<FieldValueVO> getFields() {
        return fields;
    }

    @JsonIgnore
    public Map<String, String> getFieldsMap() {
        if (fieldsMap == null) {
            if (fields != null) {
                fieldsMap = new HashMap<String, String>();
                for (final FieldValueVO vo : fields) {
                    fieldsMap.put(vo.getInternalName(), vo.getValue());
                }
            } else {
                fieldsMap = Collections.emptyMap();
            }
        }
        return fieldsMap;
    }

    @JsonIgnore
    public MemberVO getFromMember() {
        return fromMember;
    }

    @JsonIgnore
    public String getFromSystemAccountName() {
        return fromSystemAccountName;
    }

    public MemberVO getMember() {
        return member;
    }

    public String getSystemAccountName() {
        return systemAccountName;
    }

    public TransferTypeVO getTransferType() {
        return transferType;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setFields(final List<FieldValueVO> fields) {
        this.fields = fields;
    }

    public void setFromMember(final MemberVO fromMember) {
        this.fromMember = fromMember;
    }

    public void setFromSystemAccountName(final String fromSystemAccountName) {
        this.fromSystemAccountName = fromSystemAccountName;
    }

    public void setMember(final MemberVO member) {
        this.member = member;
    }

    public void setSystemAccountName(final String systemAccountName) {
        this.systemAccountName = systemAccountName;
    }

    public void setTransferType(final TransferTypeVO transferType) {
        this.transferType = transferType;
    }

}
