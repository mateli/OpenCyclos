package nl.strohalm.cyclos.entities.accounts.transactions;

import nl.strohalm.cyclos.entities.converters.StringValuedEnumAttributeConverter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class TransferAuthorizationActionAttributeConverter extends StringValuedEnumAttributeConverter<TransferAuthorization.Action> implements AttributeConverter<TransferAuthorization.Action, String> {
}
