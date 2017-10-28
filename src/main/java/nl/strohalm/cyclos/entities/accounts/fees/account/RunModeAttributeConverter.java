package nl.strohalm.cyclos.entities.accounts.fees.account;

import nl.strohalm.cyclos.entities.accounts.fees.account.AccountFee.ChargeMode;
import nl.strohalm.cyclos.entities.converters.StringValuedEnumAttributeConverter;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class RunModeAttributeConverter extends StringValuedEnumAttributeConverter<AccountFee.RunMode> {
}
