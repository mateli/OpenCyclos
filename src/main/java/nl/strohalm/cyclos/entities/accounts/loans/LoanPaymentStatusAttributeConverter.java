package nl.strohalm.cyclos.entities.accounts.loans;

import nl.strohalm.cyclos.entities.converters.StringValuedEnumAttributeConverter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class LoanPaymentStatusAttributeConverter extends StringValuedEnumAttributeConverter<LoanPayment.Status> implements AttributeConverter<LoanPayment.Status, String> {
}
