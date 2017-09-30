package nl.strohalm.cyclos.entities.converters;

import nl.strohalm.cyclos.entities.members.Element;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class ElementNatureAttributeConverter extends StringValuedEnumAttributeConverter<Element.Nature> {
}
