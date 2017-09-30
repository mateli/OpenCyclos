package nl.strohalm.cyclos.entities.converters;

import nl.strohalm.cyclos.utils.IntValuedEnum;

import javax.persistence.AttributeConverter;
import java.lang.reflect.ParameterizedType;
import java.util.EnumSet;

public class IntValuedEnumAttributeConverter<E extends Enum<E> & IntValuedEnum>
        implements AttributeConverter<E, Integer> {

    @SuppressWarnings("unchecked")
    private final Class<E> enumClass = (Class<E>) ((ParameterizedType) this.getClass().getGenericSuperclass())
            .getActualTypeArguments()[0];

    @Override
    public Integer convertToDatabaseColumn(E entity) {
        if (entity == null) {
            return null;
        }
        return entity.getValue();
    }

    @Override
    public E convertToEntityAttribute(Integer code) {
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
