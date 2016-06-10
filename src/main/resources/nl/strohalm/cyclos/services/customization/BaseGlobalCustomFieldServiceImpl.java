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
package nl.strohalm.cyclos.services.customization;

import java.util.List;

import nl.strohalm.cyclos.entities.customization.fields.CustomField;
import nl.strohalm.cyclos.utils.cache.CacheCallback;

/**
 * Base implementation for custom field services whose fields are global, that is, not scoped under another entity.
 * 
 * @author luis
 */
public abstract class BaseGlobalCustomFieldServiceImpl<CF extends CustomField> extends BaseCustomFieldServiceImpl<CF> implements BaseGlobalCustomFieldService<CF> {

    protected final CustomField.Nature nature;

    protected BaseGlobalCustomFieldServiceImpl(final Class<CF> customFieldType, final CustomField.Nature nature) {
        super(customFieldType);
        this.nature = nature;
    }

    @Override
    public List<CF> list() {
        return getCache().get(ALL_KEY, new CacheCallback() {
            @Override
            public Object retrieve() {
                List<CF> fields = list(null);
                return fetchService.fetch(fields, fetch);
            }
        });
    }

    @Override
    @SuppressWarnings("unchecked")
    protected List<CF> list(final CF field) {
        return (List<CF>) customFieldDao.listByNature(nature, CustomField.Relationships.POSSIBLE_VALUES, CustomField.Relationships.CHILDREN);
    }
}
