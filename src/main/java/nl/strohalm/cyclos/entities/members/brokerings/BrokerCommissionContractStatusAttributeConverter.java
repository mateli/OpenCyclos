package nl.strohalm.cyclos.entities.members.brokerings;

import nl.strohalm.cyclos.entities.converters.StringValuedEnumAttributeConverter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class BrokerCommissionContractStatusAttributeConverter extends StringValuedEnumAttributeConverter<BrokerCommissionContract.Status> implements AttributeConverter<BrokerCommissionContract.Status, String> {
}
