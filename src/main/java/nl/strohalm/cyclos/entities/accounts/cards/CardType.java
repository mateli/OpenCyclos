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
package nl.strohalm.cyclos.entities.accounts.cards;

import java.math.BigInteger;

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.utils.RangeConstraint;
import nl.strohalm.cyclos.utils.StringValuedEnum;
import nl.strohalm.cyclos.utils.TimePeriod;
import nl.strohalm.cyclos.utils.TimePeriod.Field;
import nl.strohalm.cyclos.utils.conversion.CardNumberConverter;
import nl.strohalm.cyclos.utils.conversion.Converter;

/**
 * Represents a card type
 * @author jefferson
 * @author rodrigo
 */
public class CardType extends Entity {

    public static enum CardSecurityCode implements StringValuedEnum {
        NOT_USED("N"), MANUAL("M"), AUTOMATIC("A");
        private final String value;

        private CardSecurityCode(final String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    private static final long               serialVersionUID          = 4768078304847226115L;

    private String                          name;
    private String                          cardFormatNumber          = "#### #### #### ####";
    private TimePeriod                      defaultExpiration         = new TimePeriod(1, Field.YEARS);
    private CardSecurityCode                cardSecurityCode          = CardSecurityCode.NOT_USED;
    private boolean                         showCardSecurityCode      = false;
    private boolean                         ignoreDayInExpirationDate = true;                           ;
    private RangeConstraint                 cardSecurityCodeLength    = new RangeConstraint(4, 4);
    private int                             maxSecurityCodeTries      = 3;
    private TimePeriod                      securityCodeBlockTime     = new TimePeriod(1, Field.DAYS);
    private transient Converter<BigInteger> converter;

    public String getCardFormatNumber() {
        return cardFormatNumber;
    }

    public Converter<BigInteger> getCardNumberConverter() {
        if (converter == null) {
            converter = new CardNumberConverter(cardFormatNumber);
        }
        return converter;
    }

    public CardSecurityCode getCardSecurityCode() {
        return cardSecurityCode;
    }

    public RangeConstraint getCardSecurityCodeLength() {
        return cardSecurityCodeLength;
    }

    public TimePeriod getDefaultExpiration() {
        return defaultExpiration;
    }

    public int getMaxSecurityCodeTries() {
        return maxSecurityCodeTries;
    }

    public String getName() {
        return name;
    }

    public TimePeriod getSecurityCodeBlockTime() {
        return securityCodeBlockTime;
    }

    public boolean isIgnoreDayInExpirationDate() {
        return ignoreDayInExpirationDate;
    }

    public boolean isShowCardSecurityCode() {
        return showCardSecurityCode;
    }

    public void setCardFormatNumber(final String cardFormatNumber) {
        this.cardFormatNumber = cardFormatNumber;
        converter = null;
    }

    public void setCardSecurityCode(final CardSecurityCode cardSecurityCode) {
        this.cardSecurityCode = cardSecurityCode;
    }

    public void setCardSecurityCodeLength(final RangeConstraint cardSecurityCodeLength) {
        this.cardSecurityCodeLength = cardSecurityCodeLength;
    }

    public void setDefaultExpiration(final TimePeriod defaultExpiration) {
        this.defaultExpiration = defaultExpiration;
    }

    public void setIgnoreDayInExpirationDate(final boolean ignoreDayInExpirationDate) {
        this.ignoreDayInExpirationDate = ignoreDayInExpirationDate;
    }

    public void setMaxSecurityCodeTries(final int maxSecurityCodeTries) {
        this.maxSecurityCodeTries = maxSecurityCodeTries;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setSecurityCodeBlockTime(final TimePeriod securityCodeBlockTime) {
        this.securityCodeBlockTime = securityCodeBlockTime;
    }

    public void setShowCardSecurityCode(final boolean showCardSecurityCode) {
        this.showCardSecurityCode = showCardSecurityCode;
    }

    @Override
    public String toString() {
        return getId() + " - " + name;
    }

}
