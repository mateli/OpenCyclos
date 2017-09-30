package nl.strohalm.cyclos.entities.accounts.cards;

import nl.strohalm.cyclos.entities.converters.StringValuedEnumAttributeConverter;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class CardSecurityCodeAttributeConverter extends StringValuedEnumAttributeConverter<CardType.CardSecurityCode> {
}
