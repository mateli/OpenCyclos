package nl.strohalm.cyclos.entities.accounts.guarantees;

import nl.strohalm.cyclos.entities.converters.StringValuedEnumAttributeConverter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class GuaranteeStatusAttributeConverter extends StringValuedEnumAttributeConverter<Guarantee.Status>
    /* https://bugs.eclipse.org/bugs/show_bug.cgi?id=415296 */ implements AttributeConverter<Guarantee.Status, String> {
}
