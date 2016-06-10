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
package nl.strohalm.cyclos.webservices.payments;

/**
 * Parameters used to request a payment
 * @author luis
 */
public class RequestPaymentParameters extends AbstractPaymentParameters {
    private static final long serialVersionUID = 6384869375987949302L;
    private String            destinationChannel;

    public String getDestinationChannel() {
        return destinationChannel;
    }

    public void setDestinationChannel(final String destinationChannel) {
        this.destinationChannel = destinationChannel;
    }

    @Override
    public String toString() {
        return "RequestPaymentParameters [destinationChannel=" + destinationChannel + ", " + super.toString() + "]";
    }
}
