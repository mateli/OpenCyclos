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
package nl.strohalm.cyclos.entities.members.printsettings;

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.members.Member;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Stores settings for a local receipt printer
 * 
 * @author luis
 */
@Table(name = "print_settings")
@javax.persistence.Entity
public class ReceiptPrinterSettings extends Entity {

    public static enum Relationships implements Relationship {
        MEMBER("member");
        private final String name;

        private Relationships(final String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    private static final long serialVersionUID = 5677759942410107438L;

    @ManyToOne
    @JoinColumn(name = "member_id")
	private Member            member;

    @Column(name = "name", nullable = false, length = 100)
    private String            name;

    @Column(name = "printer_name", nullable = false, length = 100)
    private String            printerName;

    @Column(name = "begin_doc_cmd", length = 100)
    private String            beginOfDocCommand;

    @Column(name = "end_doc_cmd", length = 100)
    private String            endOfDocCommand;

    @Column(name = "payment_message", length = 500)
    private String            paymentAdditionalMessage;

	public String getBeginOfDocCommand() {
        return beginOfDocCommand;
    }

    public String getEndOfDocCommand() {
        return endOfDocCommand;
    }

    public Member getMember() {
        return member;
    }

    @Override
    public String getName() {
        return name;
    }

    public String getPaymentAdditionalMessage() {
        return paymentAdditionalMessage;
    }

    public String getPrinterName() {
        return printerName;
    }

    public void setBeginOfDocCommand(final String beginOfDocCommand) {
        this.beginOfDocCommand = beginOfDocCommand;
    }

    public void setEndOfDocCommand(final String endOfDocCommand) {
        this.endOfDocCommand = endOfDocCommand;
    }

    public void setMember(final Member member) {
        this.member = member;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setPaymentAdditionalMessage(final String paymentAdditionalMessage) {
        this.paymentAdditionalMessage = paymentAdditionalMessage;
    }

    public void setPrinterName(final String printerName) {
        this.printerName = printerName;
    }

    @Override
    public String toString() {
        return getId() + " " + name + ", printer: " + printerName + ", member: " + member;
    }

}
