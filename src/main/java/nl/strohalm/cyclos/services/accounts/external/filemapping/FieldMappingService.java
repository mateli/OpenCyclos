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
package nl.strohalm.cyclos.services.accounts.external.filemapping;

import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.external.filemapping.FieldMapping;
import nl.strohalm.cyclos.services.Service;

/**
 * Service interface for field mappings
 * @author Jefferson Magno
 */
public interface FieldMappingService extends Service {

    /**
     * Loads a field mapping by id
     */
    FieldMapping load(Long id, Relationship... fetch);

    /**
     * Removes the specified field mapping
     */
    int remove(Long... ids);

    /**
     * Saves the field mapping
     */
    FieldMapping save(FieldMapping fieldMapping);

    /**
     * Set the field mappings order
     */
    void setOrder(Long[] fieldMappingsIds);

    /**
     * Validate the specified field mapping
     */
    void validate(FieldMapping fieldMapping);

}
