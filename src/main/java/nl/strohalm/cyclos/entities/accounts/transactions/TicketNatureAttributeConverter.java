package nl.strohalm.cyclos.entities.accounts.transactions;

import nl.strohalm.cyclos.entities.converters.StringValuedEnumAttributeConverter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class TicketNatureAttributeConverter extends StringValuedEnumAttributeConverter<Ticket.Nature> implements AttributeConverter<Ticket.Nature, String> {
}
