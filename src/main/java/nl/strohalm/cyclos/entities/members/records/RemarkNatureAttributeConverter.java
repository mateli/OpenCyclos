package nl.strohalm.cyclos.entities.members.records;

import nl.strohalm.cyclos.entities.converters.StringValuedEnumAttributeConverter;
import nl.strohalm.cyclos.entities.members.messages.Message;
import nl.strohalm.cyclos.entities.members.remarks.Remark;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class RemarkNatureAttributeConverter extends StringValuedEnumAttributeConverter<Remark.Nature> {
}
