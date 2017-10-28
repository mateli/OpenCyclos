package nl.strohalm.cyclos.entities.members.brokerings;

import nl.strohalm.cyclos.entities.converters.StringValuedEnumAttributeConverter;
import nl.strohalm.cyclos.entities.groups.MemberGroupSettings;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class BrokerCommissionContractStatusAttributeConverter extends StringValuedEnumAttributeConverter<BrokerCommissionContract.Status> {
}
