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
package nl.strohalm.cyclos.webservices.sms;

import java.io.Serializable;

import nl.strohalm.cyclos.webservices.utils.ObjectHelper;

import org.apache.commons.lang.ArrayUtils;

/**
 * Parameters used to send an SMS message
 * @author luis
 */
public class SendSmsParameters implements Serializable {

    private static final long serialVersionUID = -211018539906643986L;

    private String            smsTypeCode;
    private String[]          smsTypeArgs;
    private String            targetPrincipal;
    private String            targetPrincipalType;
    private String            toChargePrincipal;
    private String            toChargePrincipalType;
    private String            text;
    private Boolean           infoText         = false;
    private String            traceData;

    public boolean getInfoText() {
        return ObjectHelper.valueOf(infoText);
    }

    /**
     * @return the arguments used to translate the sms type description. Only the first five elements in the array will be taken into account.
     */
    public String[] getSmsTypeArgs() {
        return smsTypeArgs;
    }

    /**
     * Each sms type is identified by a code and has two I18N keys of the form:<br>
     * <ul>
     * <li>sms.type.code</li>
     * <li>sms.type.code.description: used in conjunction with {@link #getSmsTypeArgs()}</li>
     * </ul>
     * @return the code used to identify the sms type.
     * @see #getSmsTypeArgs()
     */
    public String getSmsTypeCode() {
        return smsTypeCode;
    }

    public String getTargetPrincipal() {
        return targetPrincipal;
    }

    public String getTargetPrincipalType() {
        return targetPrincipalType;
    }

    public String getText() {
        return text;
    }

    public String getToChargePrincipal() {
        return toChargePrincipal;
    }

    public String getToChargePrincipalType() {
        return toChargePrincipalType;
    }

    /**
     * Optional. If the WS client set a trace data it will be copied to the outgoing sms sent by Cyclos.<br>
     * It depends on the client side then there is no guarantee of uniqueness between different clients.
     */
    public String getTraceData() {
        return traceData;
    }

    public void setInfoText(final boolean infoText) {
        this.infoText = infoText;
    }

    public void setSmsTypeArgs(final String[] smsTypeArgs) {
        this.smsTypeArgs = smsTypeArgs;
    }

    public void setSmsTypeCode(final String smsType) {
        smsTypeCode = smsType;
    }

    public void setTargetPrincipal(final String targetPrincipal) {
        this.targetPrincipal = targetPrincipal;
    }

    public void setTargetPrincipalType(final String targetPrincipalType) {
        this.targetPrincipalType = targetPrincipalType;
    }

    public void setText(final String text) {
        this.text = text;
    }

    public void setToChargePrincipal(final String toChargePrincipal) {
        this.toChargePrincipal = toChargePrincipal;
    }

    public void setToChargePrincipalType(final String toChargePrincipalType) {
        this.toChargePrincipalType = toChargePrincipalType;
    }

    public void setTraceData(final String traceData) {
        this.traceData = traceData;
    }

    @Override
    public String toString() {
        return "SendSmsParameters [traceData=" + traceData + ", smsTypeCode=" + smsTypeCode + ", smsTypeArgs=" + ArrayUtils.toString(smsTypeArgs) + ", targetPrincipal=" + targetPrincipal + ", targetPrincipalType=" + targetPrincipalType + ", text=" + text + ", toChargePrincipal=" + toChargePrincipal + ", toChargePrincipalType=" + toChargePrincipalType + "]";
    }
}
