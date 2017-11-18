package nl.strohalm.cyclos.entities.groups;

import nl.strohalm.cyclos.entities.converters.StringValuedEnumAttributeConverter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class GroupStatusAttributeConverter extends StringValuedEnumAttributeConverter<Group.Status> implements AttributeConverter<Group.Status, String> {
}
