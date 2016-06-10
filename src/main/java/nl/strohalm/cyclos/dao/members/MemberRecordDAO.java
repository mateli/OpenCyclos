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
package nl.strohalm.cyclos.dao.members;

import java.util.List;

import nl.strohalm.cyclos.dao.BaseDAO;
import nl.strohalm.cyclos.dao.DeletableDAO;
import nl.strohalm.cyclos.dao.IndexedDAO;
import nl.strohalm.cyclos.dao.InsertableDAO;
import nl.strohalm.cyclos.dao.UpdatableDAO;
import nl.strohalm.cyclos.entities.exceptions.DaoException;
import nl.strohalm.cyclos.entities.members.records.FullTextMemberRecordQuery;
import nl.strohalm.cyclos.entities.members.records.MemberRecord;
import nl.strohalm.cyclos.entities.members.records.MemberRecordQuery;

/**
 * Interface for member record dao
 * @author Jefferson Magno
 */
public interface MemberRecordDAO extends BaseDAO<MemberRecord>, InsertableDAO<MemberRecord>, DeletableDAO<MemberRecord>, UpdatableDAO<MemberRecord>, IndexedDAO<MemberRecord> {

    /**
     * Searches for member records using a full-text search. If no entity can be found, returns an empty list. If any exception is thrown by the
     * underlying implementation, it should be wrapped by a DaoException.
     */
    public List<MemberRecord> fullTextSearch(FullTextMemberRecordQuery query);

    /**
     * Searches for member records. If no entity can be found, returns an empty list. If any exception is thrown by the underlying implementation, it
     * should be wrapped by a DaoException.
     */
    public List<MemberRecord> search(MemberRecordQuery queryParameters) throws DaoException;

}
