package nl.strohalm.cyclos.entities.groups;

import nl.strohalm.cyclos.entities.converters.StringValuedEnumAttributeConverter;
import nl.strohalm.cyclos.entities.customization.files.CustomizedFile;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class GroupStatusAttributeConverter extends StringValuedEnumAttributeConverter<Group.Status> {
}
