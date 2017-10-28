package nl.strohalm.cyclos.entities.accounts.guarantees;

import nl.strohalm.cyclos.entities.converters.StringValuedEnumAttributeConverter;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class PaymentObligationStatusAttributeConverter extends StringValuedEnumAttributeConverter<PaymentObligation.Status> {
}
