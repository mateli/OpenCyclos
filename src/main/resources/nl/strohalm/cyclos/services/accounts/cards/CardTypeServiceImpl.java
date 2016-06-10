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
package nl.strohalm.cyclos.services.accounts.cards;

import java.util.List;

import nl.strohalm.cyclos.dao.accounts.cards.CardTypeDAO;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.cards.CardType;
import nl.strohalm.cyclos.entities.accounts.cards.CardType.CardSecurityCode;
import nl.strohalm.cyclos.utils.MessageResolver;
import nl.strohalm.cyclos.utils.RangeConstraint;
import nl.strohalm.cyclos.utils.TimePeriod;
import nl.strohalm.cyclos.utils.validation.AnyOfValidation;
import nl.strohalm.cyclos.utils.validation.CardFormatValidation;
import nl.strohalm.cyclos.utils.validation.CompareToValidation;
import nl.strohalm.cyclos.utils.validation.InvalidError;
import nl.strohalm.cyclos.utils.validation.PositiveValidation;
import nl.strohalm.cyclos.utils.validation.PropertyValidation;
import nl.strohalm.cyclos.utils.validation.RequiredValidation;
import nl.strohalm.cyclos.utils.validation.ValidationError;
import nl.strohalm.cyclos.utils.validation.Validator;
import nl.strohalm.cyclos.utils.validation.Validator.Property;

/**
 * 
 * @author rodrigo
 */
public class CardTypeServiceImpl implements CardTypeServiceLocal {

    private final class PasswordTrialsValidation implements PropertyValidation {
        private static final long serialVersionUID = -5445950747956604765L;

        public ValidationError validate(final Object object, final Object property, final Object value) {
            final CardType cardType = (CardType) object;

            if (((Integer) value == 0) && (cardType.getMaxSecurityCodeTries() > 0)) {
                return new InvalidError();
            }

            return null;
        }
    }

    private CardTypeDAO     cardTypeDao;

    private MessageResolver messageResolver;

    public CardTypeDAO getCardTypeDao() {
        return cardTypeDao;
    }

    public Validator getValidator(final CardType type) {

        final Validator validator = new Validator("cardType");
        validator.property("name").required().maxLength(100);

        // Validates Card Format Number
        final Property cardFormat = validator.property("cardFormatNumber");
        cardFormat.required().maxLength(50);
        cardFormat.add(CardFormatValidation.instance());

        validator.property("defaultExpiration.number").key("cardType.defaultExpiration").add(RequiredValidation.instance()).add(PositiveValidation.instance()).add(CompareToValidation.lessEquals(28));
        validator.property("defaultExpiration.field").key("cardType.defaultExpiration").add(new AnyOfValidation(TimePeriod.Field.MONTHS, TimePeriod.Field.YEARS));

        validator.property("securityCodeBlockTime.number").key("cardType.securityCodeBlockTime").between(0, 999);
        validator.property("securityCodeBlockTime.field").key("cardType.securityCodeBlockTime").required();
        validator.property("securityCodeBlockTime.number").key("cardType.securityCodeBlockTime").add(new PasswordTrialsValidation());

        if (type.isTransient()) {
            validator.property("cardSecurityCode").required();
        }
        validator.property("cardSecurityCodeLength.min").required().between(1, 32).positiveNonZero();
        // Validates if Security code length max size is greater or equals security code min size
        final Property max = validator.property("cardSecurityCodeLength.max");
        max.required().between(1, 32).positiveNonZero();
        final RangeConstraint securityCodeLength = type.getCardSecurityCodeLength();
        if (securityCodeLength != null) {
            final Integer minValue = type.getCardSecurityCodeLength().getMin();
            if (minValue != null) {
                max.comparable(minValue, ">=", new ValidationError("errors.greaterThan", messageResolver.message("cardType.cardSecurityCodeLength.min")));
            }
        }

        return validator;
    }

    public boolean hasCards(final long cardTypeId) {
        return cardTypeDao.searchWithCardType(cardTypeId);
    }

    public List<CardType> listAll() {
        return cardTypeDao.listAll();
    }

    public CardType load(final Long id, final Relationship... fetch) {
        return cardTypeDao.load(id, fetch);
    }

    public int remove(final Long... ids) {
        return cardTypeDao.delete(ids);

    }

    public CardType save(CardType cardType) {
        validate(cardType);
        if (cardType.isTransient()) {
            if (cardType.getCardSecurityCode() == CardSecurityCode.AUTOMATIC) {
                cardType.setShowCardSecurityCode(true);
            }
            cardType = cardTypeDao.insert(cardType);
        } else {
            final CardType oldCardType = load(cardType.getId());
            cardType.setCardSecurityCode(oldCardType.getCardSecurityCode());
            cardType.setShowCardSecurityCode(oldCardType.isShowCardSecurityCode());
            cardType = cardTypeDao.update(cardType, true);
        }
        return cardType;
    }

    public void setCardTypeDao(final CardTypeDAO cardTypeDao) {
        this.cardTypeDao = cardTypeDao;
    }

    public void setMessageResolver(final MessageResolver messageResolver) {
        this.messageResolver = messageResolver;
    }

    public void validate(final CardType cardType) {
        getValidator(cardType).validate(cardType);
    }
}
