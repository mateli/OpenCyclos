package nl.strohalm.cyclos.entities.accounts.fees.account;

import nl.strohalm.cyclos.entities.accounts.fees.account.AccountFee.ChargeMode;
import nl.strohalm.cyclos.entities.converters.StringValuedEnumAttributeConverter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ChargeModeAttributeConverter extends StringValuedEnumAttributeConverter<ChargeMode>
    /* https://bugs.eclipse.org/bugs/show_bug.cgi?id=415296 */ implements AttributeConverter<ChargeMode, String> {
}
