/*
    This file is part of Cyclos (www.cyclos.org).
    A project of the Social Trade Organisation (www.socialtrade.org).

    Cyclos is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    Cyclos is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Cyclos; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA

 */
package nl.strohalm.cyclos.entities;

import nl.strohalm.cyclos.entities.ads.Ad;
import nl.strohalm.cyclos.entities.converters.OperationTypeAttributeConverter;
import nl.strohalm.cyclos.entities.members.Administrator;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.Operator;
import nl.strohalm.cyclos.entities.members.records.MemberRecord;
import nl.strohalm.cyclos.utils.FormatObject;
import nl.strohalm.cyclos.utils.StringValuedEnum;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Table;
import java.util.Calendar;

/**
 * Represents an operation which should be applied to the local lucene indexings
 * 
 * @author luis
 */
@Table(name = "index_operations")
@javax.persistence.Entity
public class IndexOperation extends Entity {

    /**
     * The entity over which the operation is applied
     * 
     * @author luis
     */
    public enum EntityType implements StringValuedEnum {
        ADVERTISEMENT(Ad.class, "ADV"),
        MEMBER(Member.class, "MBR"),
        MEMBER_RECORD(MemberRecord.class, "MRC"),
        ADMIN(Administrator.class, "ADM"),
        OPERATOR(Operator.class, "OPR");

        public static EntityType from(final Class<? extends Indexable> entityClass) {
            for (final EntityType entityType : values()) {
                if (entityType.getEntityClass().equals(entityClass)) {
                    return entityType;
                }
            }
            throw new IllegalArgumentException("No EntityType for " + entityClass);
        }

        private final Class<? extends Indexable> entityClass;
        private final String                     value;

        private EntityType(final Class<? extends Indexable> entityClass, final String value) {
            this.entityClass = entityClass;
            this.value = value;
        }

        public Class<? extends Indexable> getEntityClass() {
            return entityClass;
        }

        @Override
        public String getValue() {
            return value;
        }
    }

    /**
     * The type of operation which should be executed
     * 
     * @author luis
     */
    public enum OperationType implements StringValuedEnum {
        REBUILD("REB"), REBUILD_IF_CORRUPT("RIC"), ADD("ADD"), REMOVE("REM");
        private final String value;

        private OperationType(final String value) {
            this.value = value;
        }

        @Override
        public String getValue() {
            return value;
        }
    }

    private static final long serialVersionUID = -7460146693838037965L;

    @Column(nullable = false) // index="ix_indexops_date"
    private Calendar          date;

    @Convert(converter = EntityTypeAttributeConverter.class)
    @Column(name = "entity_type", length = 3, nullable = false)
	private EntityType        entityType;

    @Convert(converter = OperationTypeAttributeConverter.class)
    @Column(name = "operation_type", length = 3, nullable = false)
	private OperationType     operationType;

    @Column(name = "entity_id")
    private Long              entityId;

	public Calendar getDate() {
        return date;
    }

    public Long getEntityId() {
        return entityId;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setDate(final Calendar date) {
        this.date = date;
    }

    public void setEntityId(final Long entityId) {
        this.entityId = entityId;
    }

    public void setEntityType(final EntityType entityType) {
        this.entityType = entityType;
    }

    public void setOperationType(final OperationType operationType) {
        this.operationType = operationType;
    }

    @Override
    public String toString() {
        return FormatObject.formatObject(date) + " " + entityType + " " + operationType + (entityId == null ? "" : " " + entityId);
    }

}
