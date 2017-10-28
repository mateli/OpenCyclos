package nl.strohalm.cyclos.entities.members;

import nl.strohalm.cyclos.entities.converters.StringValuedEnumAttributeConverter;
import nl.strohalm.cyclos.entities.members.messages.Message;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class ReferenceNatureAttributeConverter extends StringValuedEnumAttributeConverter<Reference.Nature> {
}
