package nl.strohalm.cyclos.entities.accounts.loans;

import nl.strohalm.cyclos.entities.converters.StringValuedEnumAttributeConverter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class TypeAttributeConverter extends StringValuedEnumAttributeConverter<Loan.Type> implements AttributeConverter<Loan.Type, String> {
}
