package nl.strohalm.cyclos.entities.accounts.transactions;

import nl.strohalm.cyclos.entities.converters.StringValuedEnumAttributeConverter;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class PaymentStatusAttributeConverter extends StringValuedEnumAttributeConverter<Payment.Status> {
}
