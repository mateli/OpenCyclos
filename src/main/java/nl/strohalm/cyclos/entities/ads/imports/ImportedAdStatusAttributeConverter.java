package nl.strohalm.cyclos.entities.ads.imports;

import nl.strohalm.cyclos.entities.accounts.MemberAccount;
import nl.strohalm.cyclos.entities.converters.StringValuedEnumAttributeConverter;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class ImportedAdStatusAttributeConverter extends StringValuedEnumAttributeConverter<ImportedAd.Status> {
}
