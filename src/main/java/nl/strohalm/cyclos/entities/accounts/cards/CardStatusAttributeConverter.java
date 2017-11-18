package nl.strohalm.cyclos.entities.accounts.cards;

import nl.strohalm.cyclos.entities.converters.StringValuedEnumAttributeConverter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class CardStatusAttributeConverter extends StringValuedEnumAttributeConverter<Card.Status>
    /* https://bugs.eclipse.org/bugs/show_bug.cgi?id=415296 */ implements AttributeConverter<Card.Status, String> {
}
