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
package nl.strohalm.cyclos.services.elements;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import nl.strohalm.cyclos.dao.members.MemberRecordDAO;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.customization.fields.MemberRecordCustomFieldValue;
import nl.strohalm.cyclos.entities.groups.AdminGroup;
import nl.strohalm.cyclos.entities.groups.BrokerGroup;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.records.FullTextMemberRecordQuery;
import nl.strohalm.cyclos.entities.members.records.MemberRecord;
import nl.strohalm.cyclos.entities.members.records.MemberRecordQuery;
import nl.strohalm.cyclos.entities.members.records.MemberRecordType;
import nl.strohalm.cyclos.entities.members.records.MemberRecordTypeQuery;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.customization.MemberRecordCustomFieldServiceLocal;
import nl.strohalm.cyclos.services.settings.SettingsServiceLocal;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.utils.query.PageHelper;
import nl.strohalm.cyclos.utils.validation.ValidationException;
import nl.strohalm.cyclos.utils.validation.Validator;

import org.apache.commons.lang.StringUtils;

/**
 * Implementation class for member records service
 * @author Jefferson Magno
 */
public class MemberRecordServiceImpl implements MemberRecordServiceLocal {

    private MemberRecordCustomFieldServiceLocal memberRecordCustomFieldService;
    private MemberRecordTypeServiceLocal        memberRecordTypeService;
    private MemberRecordDAO                     memberRecordDao;
    private SettingsServiceLocal                settingsService;

    @Override
    public Map<MemberRecordType, Integer> countByType(final Element element) {
        // Find the types related to the given element and that the logged user has access
        final MemberRecordTypeQuery typeQuery = new MemberRecordTypeQuery();
        Collection<Group> groups = new ArrayList<Group>();
        groups.add(element.getGroup());
        typeQuery.setGroups(groups);
        if (LoggedUser.hasUser()) {
            final Group loggedGroup = LoggedUser.group();
            if (loggedGroup instanceof AdminGroup) {
                typeQuery.setViewableByAdminGroup((AdminGroup) loggedGroup);
            } else if (loggedGroup instanceof BrokerGroup) {
                typeQuery.setViewableByBrokerGroup((BrokerGroup) loggedGroup);
            } else {
                throw new PermissionDeniedException();
            }
        }
        // For each type, count the records for the given element
        final List<MemberRecordType> types = memberRecordTypeService.search(typeQuery);
        final Map<MemberRecordType, Integer> map = new LinkedHashMap<MemberRecordType, Integer>();
        for (final MemberRecordType type : types) {
            final MemberRecordQuery query = new MemberRecordQuery();
            query.setElement(element);
            query.setType(type);
            query.setPageForCount();
            final int count = PageHelper.getTotalCount(search(query));
            map.put(type, count);
        }
        return map;
    }

    @Override
    public List<MemberRecord> fullTextSearch(final FullTextMemberRecordQuery query) {
        if (query.getElement() != null && StringUtils.isEmpty(query.getKeywords())) {
            return search(query.toMemberRecordQuery());
        }
        query.setAnalyzer(settingsService.getLocalSettings().getLanguage().getAnalyzer());
        return memberRecordDao.fullTextSearch(query);
    }

    @Override
    public MemberRecord insert(final MemberRecord memberRecord) throws PermissionDeniedException {
        return doSave(memberRecord);
    }

    @Override
    public MemberRecord load(final Long id, final Relationship... fetch) {
        return memberRecordDao.load(id, fetch);
    }

    @Override
    public int remove(final Long... ids) throws PermissionDeniedException {
        final int count = memberRecordDao.delete(ids);
        memberRecordDao.removeFromIndex(MemberRecord.class, ids);
        return count;
    }

    @Override
    public List<MemberRecord> search(final MemberRecordQuery query) {
        return memberRecordDao.search(query);
    }

    public void setMemberRecordCustomFieldServiceLocal(final MemberRecordCustomFieldServiceLocal memberRecordCustomFieldService) {
        this.memberRecordCustomFieldService = memberRecordCustomFieldService;
    }

    public void setMemberRecordDao(final MemberRecordDAO memberRecordDao) {
        this.memberRecordDao = memberRecordDao;
    }

    public void setMemberRecordTypeServiceLocal(final MemberRecordTypeServiceLocal memberRecordTypeService) {
        this.memberRecordTypeService = memberRecordTypeService;
    }

    public void setSettingsServiceLocal(final SettingsServiceLocal settingsService) {
        this.settingsService = settingsService;
    }

    @Override
    public MemberRecord update(final MemberRecord memberRecord) throws PermissionDeniedException {
        return doSave(memberRecord);
    }

    @Override
    public void validate(final MemberRecord memberRecord) throws ValidationException {
        getValidator().validate(memberRecord);
    }

    private MemberRecord doSave(MemberRecord memberRecord) {
        final Element by = LoggedUser.element();
        final Calendar now = Calendar.getInstance();
        final Collection<MemberRecordCustomFieldValue> customValues = memberRecord.getCustomValues();
        memberRecord.setCustomValues(null);
        if (memberRecord.isTransient()) {
            memberRecord.setDate(now);
            memberRecord.setBy(by);
            memberRecord = memberRecordDao.insert(memberRecord);
        } else {
            // Preserve the original author and date
            final MemberRecord current = load(memberRecord.getId());
            memberRecord.setBy(current.getBy());
            memberRecord.setDate(current.getDate());

            memberRecord.setModifiedBy(by);
            memberRecord.setLastModified(now);
            memberRecord = memberRecordDao.update(memberRecord);
        }
        memberRecord.setCustomValues(customValues);
        memberRecordCustomFieldService.saveValues(memberRecord);

        memberRecordDao.addToIndex(memberRecord);

        return memberRecord;
    }

    private Validator getValidator() {
        final Validator validator = new Validator("memberRecord");
        validator.property("type").required();
        validator.property("element").key("memberRecord.user").required();
        validator.property("title").required();
        return validator;
    }

}
