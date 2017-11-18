package nl.strohalm.cyclos.entities.settings;

import nl.strohalm.cyclos.entities.converters.StringValuedEnumAttributeConverter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class SettingTypeAttributeConverter extends StringValuedEnumAttributeConverter<Setting.Type>
        /* https://bugs.eclipse.org/bugs/show_bug.cgi?id=415296 */ implements AttributeConverter<Setting.Type, String> {
}
