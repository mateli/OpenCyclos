package nl.strohalm.cyclos.entities.alerts;

import nl.strohalm.cyclos.entities.converters.StringValuedEnumAttributeConverter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class SystemAlertsAttributeConverter extends StringValuedEnumAttributeConverter<SystemAlert.Alerts> implements AttributeConverter<SystemAlert.Alerts, String> {
}
