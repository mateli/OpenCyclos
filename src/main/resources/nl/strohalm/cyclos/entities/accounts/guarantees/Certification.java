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
package nl.strohalm.cyclos.entities.accounts.guarantees;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Map;

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.utils.Period;
import nl.strohalm.cyclos.utils.StringValuedEnum;

public class Certification extends Entity {

    public static enum Relationships implements Relationship {
        GUARANTEE_TYPE("guaranteeType"), LOGS("logs"), BUYER("buyer"), ISSUER("issuer");
        private final String name;

        private Relationships(final String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }

    }

    public static enum Status implements StringValuedEnum {
        ACTIVE("A"), CANCELLED("C"), SUSPENDED("S"), EXPIRED("E"), SCHEDULED("SC");
        private final String value;

        private Status(final String value) {
            this.value = value;
        }

        @Override
        public String getValue() {
            return value;
        }
    }

    private static final long            serialVersionUID = -4782899135453104654L;

    private BigDecimal                   amount;
    private Period                       validity;
    private Status                       status;
    private GuaranteeType                guaranteeType;
    private Collection<CertificationLog> logs;
    private Member                       buyer;
    private Member                       issuer;

    /**
     * Change the certification's status and adds a new certification log to it
     * @param status the new certification's status
     * @param by the author of the change
     * @return the new CertificationLog added to this Certification
     */
    public CertificationLog changeStatus(final Status status, final Element by) {
        setStatus(status);

        if (logs == null) {
            logs = new ArrayList<CertificationLog>();
        }
        final CertificationLog log = getNewLog(status, by);
        logs.add(log);

        return log;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Member getBuyer() {
        return buyer;
    }

    public GuaranteeType getGuaranteeType() {
        return guaranteeType;
    }

    public Member getIssuer() {
        return issuer;
    }

    public Collection<CertificationLog> getLogs() {
        return logs;
    }

    public CertificationLog getNewLog(final Status status, final Element by) {
        final CertificationLog log = new CertificationLog();
        log.setCertification(this);
        log.setDate(Calendar.getInstance());
        log.setStatus(status);
        log.setBy(by);

        // TODO: should I add the created log to the logs' collection?
        return log;
    }

    public Status getStatus() {
        return status;
    }

    public Period getValidity() {
        return validity;
    }

    public void setAmount(final BigDecimal amount) {
        this.amount = amount;
    }

    public void setBuyer(final Member buyer) {
        this.buyer = buyer;
    }

    public void setGuaranteeType(final GuaranteeType guaranteeType) {
        this.guaranteeType = guaranteeType;
    }

    public void setIssuer(final Member issuer) {
        this.issuer = issuer;
    }

    public void setLogs(final Collection<CertificationLog> logs) {
        this.logs = logs;
    }

    public void setStatus(final Status status) {
        this.status = status;
    }

    public void setValidity(final Period validity) {
        this.validity = validity;
    }

    @Override
    public String toString() {
        return "Cert. (" + getId() + ") - " + status;
    }

    @Override
    protected void appendVariableValues(final Map<String, Object> variables, final LocalSettings localSettings) {
        final String pattern = getGuaranteeType().getCurrency().getPattern();
        variables.put("amount", localSettings.getUnitsConverter(pattern).toString(getAmount()));
        variables.put("buyer_member", getBuyer().getName());
        variables.put("buyer_login", getBuyer().getUsername());
        variables.put("issuer_member", getIssuer().getName());
        variables.put("issuer_login", getIssuer().getUsername());
    }

}
