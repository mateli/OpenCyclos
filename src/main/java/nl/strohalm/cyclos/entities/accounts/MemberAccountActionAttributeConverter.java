package nl.strohalm.cyclos.entities.accounts;

import nl.strohalm.cyclos.entities.converters.StringValuedEnumAttributeConverter;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class MemberAccountActionAttributeConverter extends StringValuedEnumAttributeConverter<MemberAccount.Action> {
}
