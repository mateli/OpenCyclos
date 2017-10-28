package nl.strohalm.cyclos.entities.accounts.external;

import nl.strohalm.cyclos.entities.converters.StringValuedEnumAttributeConverter;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class ExternalTransferTypeActionAttributeConverter extends StringValuedEnumAttributeConverter<ExternalTransferType.Action> {
}
