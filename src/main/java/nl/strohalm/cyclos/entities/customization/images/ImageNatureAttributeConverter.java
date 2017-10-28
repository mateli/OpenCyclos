package nl.strohalm.cyclos.entities.customization.images;

import nl.strohalm.cyclos.entities.converters.StringValuedEnumAttributeConverter;
import nl.strohalm.cyclos.entities.customization.files.CustomizedFile;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class ImageNatureAttributeConverter extends StringValuedEnumAttributeConverter<Image.Nature> {
}
