package nl.strohalm.cyclos.entities.accounts.transactions;

import nl.strohalm.cyclos.entities.converters.StringValuedEnumAttributeConverter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class AuthorizerAttributeConverter extends StringValuedEnumAttributeConverter<AuthorizationLevel.Authorizer> implements AttributeConverter<AuthorizationLevel.Authorizer, String> {
}
