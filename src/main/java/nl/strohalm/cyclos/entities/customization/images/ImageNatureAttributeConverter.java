package nl.strohalm.cyclos.entities.customization.images;

import nl.strohalm.cyclos.entities.converters.StringValuedEnumAttributeConverter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ImageNatureAttributeConverter extends StringValuedEnumAttributeConverter<Image.Nature> implements AttributeConverter<Image.Nature, String> {
}
