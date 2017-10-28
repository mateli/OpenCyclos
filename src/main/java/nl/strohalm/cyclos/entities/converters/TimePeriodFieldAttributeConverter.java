package nl.strohalm.cyclos.entities.converters;

import nl.strohalm.cyclos.entities.utils.TimePeriod;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class TimePeriodFieldAttributeConverter extends IntValuedEnumAttributeConverter<TimePeriod.Field> {
}
