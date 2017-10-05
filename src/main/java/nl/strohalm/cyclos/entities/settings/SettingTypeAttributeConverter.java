package nl.strohalm.cyclos.entities.settings;

import nl.strohalm.cyclos.entities.converters.StringValuedEnumAttributeConverter;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class SettingTypeAttributeConverter extends StringValuedEnumAttributeConverter<Setting.Type> {
}
