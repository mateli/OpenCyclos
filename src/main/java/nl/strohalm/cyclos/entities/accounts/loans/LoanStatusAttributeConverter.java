package nl.strohalm.cyclos.entities.accounts.loans;

import nl.strohalm.cyclos.entities.converters.StringValuedEnumAttributeConverter;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class LoanStatusAttributeConverter extends StringValuedEnumAttributeConverter<Loan.Status> {
}
