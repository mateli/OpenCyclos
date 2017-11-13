package nl.strohalm.cyclos.entities.accounts.fees.account;

import nl.strohalm.cyclos.entities.converters.StringValuedEnumAttributeConverter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class PaymentDirectionAttributeConverter extends StringValuedEnumAttributeConverter<AccountFee.PaymentDirection>
    /* https://bugs.eclipse.org/bugs/show_bug.cgi?id=415296 */ implements AttributeConverter<AccountFee.PaymentDirection, String> {
}
