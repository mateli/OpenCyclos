package nl.strohalm.cyclos.entities.accounts.guarantees;

import nl.strohalm.cyclos.entities.converters.StringValuedEnumAttributeConverter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class AuthorizedByAttributeConverter extends StringValuedEnumAttributeConverter<GuaranteeType.AuthorizedBy>
    /* https://bugs.eclipse.org/bugs/show_bug.cgi?id=415296 */ implements AttributeConverter<GuaranteeType.AuthorizedBy, String> {
}
