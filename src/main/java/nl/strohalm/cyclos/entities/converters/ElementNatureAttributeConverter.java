package nl.strohalm.cyclos.entities.converters;

import nl.strohalm.cyclos.entities.members.Element;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ElementNatureAttributeConverter extends StringValuedEnumAttributeConverter<Element.Nature> implements AttributeConverter<Element.Nature, String> {
}
