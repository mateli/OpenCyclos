package nl.strohalm.cyclos.entities.accounts.external;

import nl.strohalm.cyclos.entities.converters.StringValuedEnumAttributeConverter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ExternalTransferTypeActionAttributeConverter extends StringValuedEnumAttributeConverter<ExternalTransferType.Action>
    /* https://bugs.eclipse.org/bugs/show_bug.cgi?id=415296 */ implements AttributeConverter<ExternalTransferType.Action, String> {
}
