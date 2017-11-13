package nl.strohalm.cyclos.entities.accounts.fees.account;

import nl.strohalm.cyclos.entities.converters.StringValuedEnumAttributeConverter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class RunModeAttributeConverter extends StringValuedEnumAttributeConverter<AccountFee.RunMode>
/* https://bugs.eclipse.org/bugs/show_bug.cgi?id=415296 */ implements AttributeConverter<AccountFee.RunMode, String> {
}
