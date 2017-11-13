package nl.strohalm.cyclos.entities.ads.imports;

import nl.strohalm.cyclos.entities.converters.StringValuedEnumAttributeConverter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ImportedAdStatusAttributeConverter extends StringValuedEnumAttributeConverter<ImportedAd.Status> implements AttributeConverter<ImportedAd.Status, String> {
}
