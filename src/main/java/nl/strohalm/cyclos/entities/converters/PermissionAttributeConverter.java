package nl.strohalm.cyclos.entities.converters;

import nl.strohalm.cyclos.access.Permission;
import nl.strohalm.cyclos.utils.access.PermissionHelper;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class PermissionAttributeConverter implements AttributeConverter<Permission, String> {

    @Override
    public String convertToDatabaseColumn(Permission entity) {
        if (entity == null) {
            return null;
        }
        return entity.getQualifiedName();
    }

    @Override
    public Permission convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }
        return PermissionHelper.getPermission(code);
    }

}
