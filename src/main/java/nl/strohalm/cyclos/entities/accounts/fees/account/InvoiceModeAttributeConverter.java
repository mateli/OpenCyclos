package nl.strohalm.cyclos.entities.accounts.fees.account;

import nl.strohalm.cyclos.entities.converters.StringValuedEnumAttributeConverter;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class InvoiceModeAttributeConverter extends StringValuedEnumAttributeConverter<AccountFee.InvoiceMode> {
}
