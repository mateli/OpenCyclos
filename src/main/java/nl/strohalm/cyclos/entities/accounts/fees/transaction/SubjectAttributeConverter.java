package nl.strohalm.cyclos.entities.accounts.fees.transaction;

import nl.strohalm.cyclos.entities.converters.StringValuedEnumAttributeConverter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class SubjectAttributeConverter extends StringValuedEnumAttributeConverter<TransactionFee.Subject>
    /* https://bugs.eclipse.org/bugs/show_bug.cgi?id=415296 */ implements AttributeConverter<TransactionFee.Subject, String> {
}
