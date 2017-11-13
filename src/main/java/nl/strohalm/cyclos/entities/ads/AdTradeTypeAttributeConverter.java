package nl.strohalm.cyclos.entities.ads;

import nl.strohalm.cyclos.entities.converters.StringValuedEnumAttributeConverter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class AdTradeTypeAttributeConverter extends StringValuedEnumAttributeConverter<Ad.TradeType> implements AttributeConverter<Ad.TradeType, String> {
}
