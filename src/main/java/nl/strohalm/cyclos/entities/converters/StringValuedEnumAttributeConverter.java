package nl.strohalm.cyclos.entities.converters;

import nl.strohalm.cyclos.utils.StringValuedEnum;

import javax.persistence.AttributeConverter;
import java.lang.reflect.ParameterizedType;
import java.util.EnumSet;

public class StringValuedEnumAttributeConverter <E extends Enum<E> & StringValuedEnum>
        implements AttributeConverter<E, String> {

    @SuppressWarnings("unchecked")
    private final Class<E> enumClass = (Class<E>) ((ParameterizedType) this.getClass().getGenericSuperclass())
            .getActualTypeArguments()[0];

    @Override
    public String convertToDatabaseColumn(E entity) {
        if (entity == null) {
            return null;
        }
        return entity.getValue();
    }

    @Override
    public E convertToEntityAttribute(String code) {
        for (E entity : EnumSet.allOf(this.enumClass)) {
            if (entity.getValue() == null && code == null) {
                return entity;
            }
            if (entity.getValue() != null && entity.getValue().equals(code)) {
                return entity;
            }
        }
        if (code == null) {
            return null;
        }
        throw new IllegalArgumentException(
                "Code inconnu pour l'enum " + this.enumClass.getName() + " : " + code);
    }

}
