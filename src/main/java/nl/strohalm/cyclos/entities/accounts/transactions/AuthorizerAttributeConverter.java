package nl.strohalm.cyclos.entities.accounts.transactions;

import nl.strohalm.cyclos.entities.accounts.loans.Loan;
import nl.strohalm.cyclos.entities.converters.StringValuedEnumAttributeConverter;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class AuthorizerAttributeConverter extends StringValuedEnumAttributeConverter<AuthorizationLevel.Authorizer> {
}
