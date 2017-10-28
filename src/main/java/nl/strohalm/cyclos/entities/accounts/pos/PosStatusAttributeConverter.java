package nl.strohalm.cyclos.entities.accounts.pos;

import nl.strohalm.cyclos.entities.converters.StringValuedEnumAttributeConverter;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class PosStatusAttributeConverter extends StringValuedEnumAttributeConverter<Pos.Status> {
}
