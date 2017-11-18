package nl.strohalm.cyclos.entities.customization.documents;

import nl.strohalm.cyclos.entities.converters.StringValuedEnumAttributeConverter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class MemberDocumentVisibilityAttributeConverter extends StringValuedEnumAttributeConverter<MemberDocument.Visibility> implements AttributeConverter<MemberDocument.Visibility, String> {
}
