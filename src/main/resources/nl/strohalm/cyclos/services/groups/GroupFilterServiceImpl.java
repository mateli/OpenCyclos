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
package nl.strohalm.cyclos.services.groups;

import java.util.Collection;
import java.util.List;

import nl.strohalm.cyclos.dao.groups.GroupFilterDAO;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.groups.GroupFilter;
import nl.strohalm.cyclos.entities.groups.GroupFilterQuery;
import nl.strohalm.cyclos.utils.validation.Validator;

/**
 * Implementation for groups filters.
 * @author jeferson
 */

public class GroupFilterServiceImpl implements GroupFilterServiceLocal {

    private GroupFilterDAO groupFilterDao;

    @Override
    public GroupFilter findByLoginPageName(final String loginPageName) {
        return groupFilterDao.findByLoginPageName(loginPageName);
    }

    @Override
    public Collection<GroupFilter> load(final Collection<Long> ids, final Relationship... fetch) {
        return groupFilterDao.load(ids, fetch);
    }

    @Override
    public GroupFilter load(final Long id, final Relationship... fetch) {
        return groupFilterDao.load(id, fetch);
    }

    @Override
    public int remove(final Long... ids) {
        return groupFilterDao.delete(ids);
    }

    @Override
    public GroupFilter save(final GroupFilter groupFilter) {
        validate(groupFilter);
        if (groupFilter.isTransient()) {
            return groupFilterDao.insert(groupFilter);
        } else {
            return groupFilterDao.update(groupFilter);
        }
    }

    @Override
    public List<GroupFilter> search(final GroupFilterQuery query) {
        return groupFilterDao.search(query);
    }

    public void setGroupFilterDao(final GroupFilterDAO groupFilterDao) {
        this.groupFilterDao = groupFilterDao;
    }

    @Override
    public void validate(final GroupFilter groupFilter) {
        getValidator().validate(groupFilter);
    }

    private Validator getValidator() {
        final Validator validator = new Validator("groupFilter");
        validator.property("name").required().maxLength(100);
        validator.property("loginPageName").maxLength(20);
        validator.property("containerUrl").maxLength(100).url();
        validator.property("description").maxLength(1000);
        return validator;
    }

}
