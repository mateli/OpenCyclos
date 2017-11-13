package nl.strohalm.cyclos.entities.accounts;

import nl.strohalm.cyclos.entities.converters.StringValuedEnumAttributeConverter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class AccountTypeNatureAttributeConverter extends StringValuedEnumAttributeConverter<AccountType.Nature>
    /* https://bugs.eclipse.org/bugs/show_bug.cgi?id=415296 */ implements AttributeConverter<AccountType.Nature, String> {
}
