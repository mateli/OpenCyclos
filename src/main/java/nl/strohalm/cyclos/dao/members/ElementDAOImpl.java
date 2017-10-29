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

import nl.strohalm.cyclos.dao.IndexedDAOImpl;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.access.Channel;
import nl.strohalm.cyclos.entities.accounts.Account;
import nl.strohalm.cyclos.entities.customization.fields.CustomFieldValue;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomField;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.exceptions.QueryParseException;
import nl.strohalm.cyclos.entities.groups.BrokerGroup;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.AdminQuery;
import nl.strohalm.cyclos.entities.members.Administrator;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Element.Nature;
import nl.strohalm.cyclos.entities.members.ElementQuery;
import nl.strohalm.cyclos.entities.members.FullTextAdminQuery;
import nl.strohalm.cyclos.entities.members.FullTextElementQuery;
import nl.strohalm.cyclos.entities.members.FullTextMemberQuery;
import nl.strohalm.cyclos.entities.members.FullTextOperatorQuery;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.MemberQuery;
import nl.strohalm.cyclos.entities.members.Operator;
import nl.strohalm.cyclos.entities.members.OperatorQuery;
import nl.strohalm.cyclos.entities.members.RegistrationAgreement;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.entities.settings.LocalSettings.MemberResultDisplay;
import nl.strohalm.cyclos.entities.settings.LocalSettings.SortOrder;
import nl.strohalm.cyclos.entities.utils.Period;
import nl.strohalm.cyclos.services.elements.BrokerQuery;
import nl.strohalm.cyclos.services.settings.SettingsServiceLocal;
import nl.strohalm.cyclos.utils.EntityHelper;
import nl.strohalm.cyclos.utils.jpa.JpaCustomFieldHandler;
import nl.strohalm.cyclos.utils.jpa.JpaQueryHelper;
import nl.strohalm.cyclos.utils.lucene.Filters;
import nl.strohalm.cyclos.utils.lucene.LuceneUtils;
import nl.strohalm.cyclos.utils.query.PageParameters;
import nl.strohalm.cyclos.utils.query.QueryParameters.ResultType;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Implementation class for element DAO
 * @author rafael
 * @author luis
 */
public class ElementDAOImpl extends IndexedDAOImpl<Element> implements ElementDAO {

    private static final String[]       FIELDS_FULL_TEXT = { "name", "username", "email", "customValues" };
    private JpaCustomFieldHandler jpaCustomFieldHandler;
    private SettingsServiceLocal        settingsService;

    public ElementDAOImpl() {
        super(Element.class);
    }

    @Override
    public void activateMembersOfGroup(final MemberGroup group) {
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        namedParameters.put("date", Calendar.getInstance());
        namedParameters.put("group", group);
        bulkUpdate("update Member set activationDate = :date where group = :group and activationDate is null", namedParameters);
    }

    @Override
    public void createAgreementForAllMembers(final RegistrationAgreement registrationAgreement, final MemberGroup group) {
        final String insert = "insert into registration_agreement_logs (member_id, registration_agreement_id, date) select id, ?, ? from members where group_id = ?";
        runNative(insert, registrationAgreement.getId(), Calendar.getInstance(), group.getId());
    }

    @Override
    public List<? extends Element> fullTextSearch(final FullTextElementQuery elementQuery) {
        final String keywords = StringUtils.trimToNull(elementQuery.getKeywords());
        final Nature nature = elementQuery.getNature();
        // We cannot search on a null nature
        if (nature == null) {
            return Collections.emptyList();
        }
        // When searching by keywords, use the full-text query
        Analyzer analyzer = elementQuery.getAnalyzer();
        Query query;
        Sort sort = null;
        if (keywords == null) {
            query = new MatchAllDocsQuery();
            sort = new Sort(new SortField("creationDate", SortField.Type.STRING, true));
        } else {
            try {
                query = keywords == null ? new MatchAllDocsQuery() : getQueryParser(analyzer).parse(keywords);
            } catch (final ParseException e) {
                throw new QueryParseException(e);
            }
        }
        final Filters filters = new Filters();
        filters.addTerms("active", elementQuery.getEnabled());
        filters.addTerms("group", elementQuery.getGroups());
        final Collection<? extends CustomFieldValue> customValues = elementQuery.getCustomValues();
        if (CollectionUtils.isNotEmpty(customValues)) {
            for (final CustomFieldValue fieldValue : customValues) {
                addCustomField(filters, analyzer, fieldValue);
            }
        }
        if (CollectionUtils.isNotEmpty(elementQuery.getExcludeElements())) {
            Collection<Long> excludeIds = EntityHelper.toIdsAsList(elementQuery.getExcludeElements());
            filters.add(Filters.andNot(Filters.terms("id", excludeIds)));
        }

        if (elementQuery instanceof FullTextMemberQuery) {
            final FullTextMemberQuery memberQuery = (FullTextMemberQuery) elementQuery;
            filters.addPeriod("activationDate", memberQuery.getActivationPeriod());
            filters.addTerms("broker", memberQuery.getBroker());
            if (memberQuery.isWithImagesOnly()) {
                filters.addTerms("hasImages", true);
            }
            sort = decideSorting(memberQuery);

        } else if (elementQuery instanceof FullTextOperatorQuery) {
            final FullTextOperatorQuery operatorQuery = (FullTextOperatorQuery) elementQuery;
            final Member member = operatorQuery.getMember();
            if (member == null) {
                // Cannot search operators without a member
                return Collections.emptyList();
            }
            filters.addTerms("member", member);
        } else if (elementQuery instanceof FullTextAdminQuery) {
            sort = decideSorting(elementQuery);
        }
        return list(nature.getElementClass(), elementQuery, query, filters, sort);
    }

    @Override
    public Map<Long, Integer> getCountPerGroup(final Collection<MemberGroup> groups) {
        Map<String, ?> params = Collections.singletonMap("groups", groups);
        StringBuilder hql = new StringBuilder();
        hql.append(" select g.id, count(m.id) ");
        hql.append(" from Member m join m.group g ");
        hql.append(" where g in (:groups) ");
        hql.append(" group by g.id ");
        return this.<Long, Integer> map(hql.toString(), params);
    }

    @Override
    public Map<Long, Integer> getCountPerGroup(final Collection<MemberGroup> groups, final Calendar timePoint) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("groups", groups);
        params.put("timePoint", timePoint);
        StringBuilder hql = new StringBuilder();
        hql.append(" select g.id, count(m.id) ");
        hql.append(" from GroupHistoryLog l join l.element m join l.group g ");
        hql.append(" where g in (:groups) ");
        hql.append("  and l.period.begin <= :timePoint ");
        hql.append("  and (l.period.end is null or l.period.end > :timePoint)");
        hql.append(" group by g.id");
        return this.<Long, Integer> map(hql.toString(), params);
    }

    @Override
    public Calendar getFirstMemberActivationDate() {
        final String hql = "select min(activationDate) from Member";
        return uniqueResult(hql, new HashMap<String, Object>());
    }

    public JpaCustomFieldHandler getJpaCustomFieldHandler() {
        return jpaCustomFieldHandler;
    }

    @Override
    public List<Number[]> getNewMembersCountThroughTheTime(final Collection<? extends Group> groups, final Period period) {
        final StringBuilder hql = new StringBuilder("select month(m.creationDate), year(m.creationDate), count(m.id) ");
        hql.append(" from Member m ");
        hql.append(" where 1=1 ");
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        if (groups != null && !groups.isEmpty()) {
            JpaQueryHelper.addInParameterToQuery(hql, namedParameters, "m.group", groups);
        }
        if (period != null) {
            JpaQueryHelper.addPeriodParameterToQuery(hql, namedParameters, "m.creationDate", period);
        }
        hql.append(" group by month(m.creationDate), year(m.creationDate) ");
        hql.append(" order by year(m.creationDate), month(m.creationDate) ");
        final List<Object[]> results = list(hql.toString(), namedParameters);
        final Iterator<Object[]> i = results.iterator();
        final List<Number[]> numberList = new ArrayList<Number[]>();
        while (i.hasNext()) {
            final Object[] row = i.next();
            final Number[] intRow = new Integer[3];
            intRow[0] = (Integer) row[0];
            intRow[1] = (Integer) row[1];
            intRow[2] = (Integer) row[2];
            numberList.add(intRow);
        }
        return numberList;
    }

    /**
     * gets the number of members which were in the specified group at any moment during the specified period. Used by Activity Stats: gross Product,
     * number of transactions and % not trading for compare periods, Histogram, Single Period, and by Key Dev Stats number of members
     * 
     * @param groups the set of groups in which the members must be counted.
     * @param period the period in which they should be part of any of the groups
     * @return an int indicating the number of members during that period in that set of groups
     * 
     */
    @Override
    public int getNumberOfMembersInGroupsInPeriod(final Collection<? extends Group> groups, final Period period) {
        final StringBuilder hql = new StringBuilder(" select count(m.id) from Member m where 1=1 ");

        final Map<String, Object> namedParameters = new HashMap<String, Object>();

        if (CollectionUtils.isEmpty(groups) && period != null && period.getEnd() != null) {
            namedParameters.put("endDate", period.getEnd());
            hql.append(" and m.creationDate <= :endDate ");

        } else if (!CollectionUtils.isEmpty(groups) && period != null && period.getEnd() != null && period.getBegin() != null) {
            namedParameters.put("beginDate", period.getBegin());
            namedParameters.put("endDate", period.getEnd());
            namedParameters.put("groups", groups);

            // First condition: it has been in one of the selected groups
            hql.append(" and ( (m.group in (:groups) and m.creationDate < :endDate and not exists ");
            hql.append(" (select gr1.id from GroupRemark gr1 where gr1.subject = m)) or ");

            // Second: Changed the member's group inside the period.
            hql.append(" (m.creationDate < :endDate and exists (select gr.id from GroupRemark gr where gr.subject=m and (gr.oldGroup in ");
            hql.append(" (:groups) or gr.newGroup in (:groups)) and gr.date > :beginDate and gr.date <= :endDate)) or ");

            // Third condition: the group remark right before the period put the member in one
            // of the selected groups
            hql.append(" exists (select gr2.id from GroupRemark gr2 where gr2.subject=m and ");
            hql.append(" gr2.newGroup in (:groups) and gr2.date=(select max(gr3.date) from GroupRemark ");
            hql.append(" gr3 where gr3.subject=m and gr3.date < :beginDate)) or ");

            // Fourth condition: the group remark right after the begin period: the member was created
            // then the group was changed in the period, we must use oldGroup.
            hql.append(" (m.creationDate <= :endDate and exists (select gr2.id from GroupRemark gr2 where ");
            hql.append(" gr2.subject=m and gr2.oldGroup in (:groups) and gr2.date = (select min(gr3.date) ");
            hql.append(" from GroupRemark gr3 where gr3.subject=m and gr3.date > :endDate))) ");
            hql.append(" ) ");
        } else if (!CollectionUtils.isEmpty(groups)) {
            hql.append(" and m.group in (:groups) ");
            namedParameters.put("groups", groups);
        }
        final Number count = uniqueResult(hql.toString(), namedParameters);
        return count.intValue();
    }

    @Override
    public List<Number[]> getRemovedMembersCountThroughTheTime(final Collection<? extends Group> groups, final Period period) {
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        final StringBuilder hql = new StringBuilder("select month(gr.date), year(gr.date), count(gr.id) ");
        hql.append(" from GroupRemark gr ");
        hql.append(" where 1=1 ");

        hql.append(" and exists ( ");
        hql.append("    select gr.id ");
        hql.append("    from GroupRemark gr ");
        hql.append("    where ");
        hql.append("        gr.subject = e ");
        hql.append("        and gr.newGroup.status = :removed ");
        namedParameters.put("removed", Group.Status.REMOVED);

        // Deactivation period
        if (period != null) {
            JpaQueryHelper.addPeriodParameterToQuery(hql, namedParameters, "gr.date", period);
        }
        if (groups != null && !groups.isEmpty()) {
            hql.append("        and gr.oldGroup in (:groups) ");
            namedParameters.put("groups", groups);
        } else {
            return new ArrayList<Number[]>();
        }
        hql.append(" 	)");

        hql.append(" and gr.newGroup.status = :removed ");
        hql.append(" group by month(gr.date), year(gr.date) ");
        hql.append(" order by year(gr.date), month(gr.date) ");
        namedParameters.put("removed", Group.Status.REMOVED);

        // Map<Integer, Integer> removedMembersCountThroughTheYears = new HashMap<Integer, Integer>();
        final List<Object[]> results = list(hql.toString(), namedParameters);
        final Iterator<Object[]> i = results.iterator();
        final List<Number[]> numberList = new ArrayList<Number[]>();
        while (i.hasNext()) {
            final Object[] row = i.next();
            final Number[] intRow = new Integer[3];
            intRow[0] = (Integer) row[0];
            intRow[1] = (Integer) row[1];
            intRow[2] = (Integer) row[2];
            numberList.add(intRow);
        }
        return numberList;
    }

    @Override
    public boolean hasValueForField(final Member member, final MemberCustomField field) {
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        namedParameters.put("member", member);
        namedParameters.put("field", field);
        final StringBuilder hql = new StringBuilder();
        hql.append(" select 1");
        hql.append(" from MemberCustomFieldValue fv");
        hql.append(" where fv.member = :member");
        hql.append("   and fv.field = :field");
        hql.append("   and (fv.possibleValue is not null or (fv.stringValue is not null and length(fv.stringValue) > 0))");
        final List<?> list = list(ResultType.LIST, hql.toString(), namedParameters, PageParameters.max(1));
        return !list.isEmpty();
    }

    @Override
    public Iterator<Member> iterateMembers(final boolean ordered, final MemberGroup... groups) {
        if (groups == null || groups.length == 0) {
            return Collections.<Member> emptyList().iterator();
        }
        final Map<String, List<MemberGroup>> parameters = Collections.singletonMap("groups", Arrays.asList(groups));
        return iterate("from Member m left join fetch m.user where m.group in (:groups) " + (ordered ? "order by m.name, m.user.username" : ""), parameters);
    }

    @Override
    public List<Member> listMembersRegisteredBeforeOnGroup(final Calendar date, final MemberGroup group) {
        final StringBuilder hql = new StringBuilder();
        hql.append(" select m");
        hql.append(" from GroupHistoryLog log, Member m left join fetch m.user ");
        hql.append(" where log.element = m ");
        hql.append("   and log.element.group = :group ");
        hql.append("   and log.group = :group ");
        hql.append("   and log.period.end is null ");
        hql.append("   and log.period.begin < :date");
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        namedParameters.put("date", date);
        namedParameters.put("group", group);
        return list(ResultType.ITERATOR, hql.toString(), namedParameters, null, Element.Relationships.USER, Element.Relationships.GROUP);
    }

    @Override
    public Member loadByCustomField(final MemberCustomField customField, final String value, final Relationship[] fetch) {
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        final StringBuilder hql = new StringBuilder();
        hql.append(" select m");
        hql.append(" from MemberCustomFieldValue fv inner join fv.member m inner join m.user u inner join fetch m.group g");
        hql.append(" where g.status <> :removed");
        hql.append(" and fv.field = :field");
        hql.append(" and fv.stringValue = :value");
        namedParameters.put("removed", Group.Status.REMOVED);
        namedParameters.put("field", customField);
        namedParameters.put("value", value);
        final Member member = uniqueResult(hql.toString(), namedParameters);
        if (member == null) {
            throw new EntityNotFoundException(Member.class, null, String.format("Custom field used to load: <%1$s, %2$s>", customField.getInternalName(), value));
        }
        return member;
    }

    @Override
    public Element loadByEmail(final String email, final Relationship... fetch) throws EntityNotFoundException {
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        final StringBuilder hql = JpaQueryHelper.getInitialQuery(Member.class, "m", Arrays.asList(fetch));
        hql.append(" and m.group.status <> :removed");
        hql.append(" and m.email = :email");
        namedParameters.put("removed", Group.Status.REMOVED);
        namedParameters.put("email", email);
        final Element element = uniqueResult(hql.toString(), namedParameters);
        if (element == null) {
            throw new EntityNotFoundException(Element.class);
        }
        return element;
    }

    @Override
    public void removeChannelsFromMembers(final MemberGroup group, final Collection<Channel> channels) {
        if (CollectionUtils.isNotEmpty(channels)) {
            final Map<String, Object> parameters = new HashMap<String, Object>();
            final Set<Long> channelIds = new HashSet<Long>();
            CollectionUtils.addAll(channelIds, EntityHelper.toIds(channels));
            parameters.put("channelIds", channelIds);
            parameters.put("groupId", group.getId());
            final String statement = " delete from members_channels " +
                    " where channel_id in (:channelIds) " +
                    " and member_id in (select id from members where group_id = :groupId) ";
            runNative(statement, parameters);
        }

    }

    @Override
    public List<Element> search(final ElementQuery query) {
        Class<? extends Element> entityType;
        if (query instanceof AdminQuery) {
            entityType = Administrator.class;
        } else if (query instanceof MemberQuery) {
            entityType = Member.class;
        } else if (query instanceof OperatorQuery) {
            entityType = Operator.class;
        } else {
            throw new IllegalArgumentException("Invalid query parameters: " + query);
        }

        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        final Set<Relationship> fetch = query.getFetch();
        final StringBuilder hql = new StringBuilder();
        if (query instanceof MemberQuery && ((MemberQuery) query).isHasAds()) {
            hql.append(" select distinct e");
            hql.append(" from ").append(entityType.getName()).append(" e inner join e.ads ad ");
        } else {
            hql.append(" select e");
            hql.append(" from ").append(entityType.getName()).append(" e ");
        }
        jpaCustomFieldHandler.appendJoins(hql, "e.customValues", query.getCustomValues());
        JpaQueryHelper.appendJoinFetch(hql, entityType, "e", fetch);
        hql.append(" where 1=1 ");
        if (query instanceof BrokerQuery) {
            hql.append(" and exists (select 1 from " + BrokerGroup.class.getName() + " bg where bg = e.group) ");
        }
        if (query.getExcludeElements() != null && !query.getExcludeElements().isEmpty()) {
            hql.append(" and e not in (:excludeElements) ");
            namedParameters.put("excludeElements", query.getExcludeElements());
        }
        if (query.isExcludeRemoved()) {
            hql.append(" and e.group.status <> :removedStatus");
            namedParameters.put("removedStatus", Group.Status.REMOVED);
        }
        JpaQueryHelper.addRightLikeParameterToQuery(hql, namedParameters, "e.user.username", query.getUsername());
        JpaQueryHelper.addLikeParameterToQuery(hql, namedParameters, "e.name", query.getName());
        JpaQueryHelper.addRightLikeParameterToQuery(hql, namedParameters, "e.email", query.getEmail());
        // Group filters are handled at service level
        if (query.getGroups() != null && !query.getGroups().isEmpty()) {
            JpaQueryHelper.addInParameterToQuery(hql, namedParameters, "e.group", query.getGroups());
        }
        JpaQueryHelper.addPeriodParameterToQuery(hql, namedParameters, "e.creationDate", query.getCreationPeriod());

        final Boolean enabled = query.getEnabled();
        // Specific tests for admins and members
        if (query instanceof AdminQuery) {
            if (enabled != null) {
                // If searching for admins, enabled means normal groups, while disabled means removed admins
                final Group.Status groupStatus = enabled ? Group.Status.NORMAL : Group.Status.REMOVED;
                JpaQueryHelper.addParameterToQuery(hql, namedParameters, "e.group.status", groupStatus);
            }
        } else if (query instanceof MemberQuery) {
            final MemberQuery memberQuery = (MemberQuery) query;
            JpaQueryHelper.addPeriodParameterToQuery(hql, namedParameters, "e.activationDate", memberQuery.getActivationPeriod());
            if (enabled != null) {
                // For members enabled means the activationData must be not null / null for Enable / Disabled
                hql.append(" and e.activationDate is " + (enabled ? "not" : "") + " null ");
                if (enabled) {
                    // Enabled also has a normal group
                    JpaQueryHelper.addParameterToQuery(hql, namedParameters, "e.group.status", Group.Status.NORMAL);
                }
            }
            // With images only
            if (memberQuery.isWithImagesOnly()) {
                hql.append(" and exists (select mi.id from MemberImage mi where mi.member=e)");
            }
            // Deactivation period
            final Period deactivationPeriod = memberQuery.getDeactivationPeriod();
            if (deactivationPeriod != null) {
                hql.append(" and exists ( ");
                hql.append("    select gr.id ");
                hql.append("    from GroupRemark gr ");
                hql.append("    where ");
                hql.append("        gr.subject = e ");
                hql.append("        and gr.newGroup.status = :removed ");
                JpaQueryHelper.addPeriodParameterToQuery(hql, namedParameters, "gr.date", deactivationPeriod);
                hql.append(" )");
                namedParameters.put("removed", Group.Status.REMOVED);
            }
            // Has ads
            final boolean hasAds = memberQuery.isHasAds();
            if (hasAds) {
                hql.append(" and ad.permanent=true or ( ");
                hql.append("     ad.publicationPeriod.begin <= current_date() and ");
                hql.append("     ad.publicationPeriod.end >= current_date() ");
                hql.append(" ) ");
            }
            // Broker
            if (memberQuery.getBroker() != null) {
                JpaQueryHelper.addParameterToQuery(hql, namedParameters, "e.broker", memberQuery.getBroker());
            }
            // Group filters
            if (CollectionUtils.isNotEmpty(memberQuery.getGroupFilters())) {
                hql.append(" and exists (select gf.id from GroupFilter gf where gf in (:groupFilters) and e.group in elements(gf.groups))");
                namedParameters.put("groupFilters", memberQuery.getGroupFilters());
            }
        } else if (query instanceof OperatorQuery) {
            final OperatorQuery operatorQuery = (OperatorQuery) query;
            hql.append(" and exists (");
            hql.append("     select o.id from Operator o where o = e and o.member = :member");
            hql.append(" )");
            namedParameters.put("member", operatorQuery.getMember());
        }

        if (query.getViewableGroup() != null) {
            hql.append(" and :mg in elements(e.group.canViewProfileOfGroups)");
            namedParameters.put("mg", query.getViewableGroup());
        }
        jpaCustomFieldHandler.appendConditions(hql, namedParameters, query.getCustomValues());
        if (query.isRandomOrder()) {
            JpaQueryHelper.appendOrder(hql, "rand()");
        } else if (query.getOrder() != null) {
            switch (query.getOrder()) {
                case USERNAME:
                    JpaQueryHelper.appendOrder(hql, "e.user.username");
                    break;
                case NAME:
                    JpaQueryHelper.appendOrder(hql, "e.name", "e.id");
                    break;
            }
        }
        return list(query, hql.toString(), namedParameters);
    }

    public Iterator<Member> searchActiveMembers(final Collection<Group> toSearch) {
        javax.persistence.Query query = entityManager.createQuery(" from " + Member.class.getName() + " m  where m.group in (?)  and exists (select 1 from " + Account.class.getName() + " a where a.member = m) ", Member.class);
        query.setParameter(1, toSearch);
        return query.getResultList().iterator();
    }

    @Override
    public List<Element> searchAtDate(final MemberQuery query, final Calendar date) {
        final StringBuilder hql = JpaQueryHelper.getInitialQuery(Member.class, "m", query.getFetch());
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        if (query.getBroker() != null) {
            hql.append(" and m.broker = :broker ");
            namedParameters.put("broker", query.getBroker());
        }
        if (date == null) {
            hql.append(" and m.group in (:groups) ");
            namedParameters.put("groups", query.getGroups());
        } else {
            if (!CollectionUtils.isEmpty(query.getGroups())) {
                hql.append(" and ( m.creationDate <= :date ");
                hql.append(" and (m.group in (:groups) and not exists ");
                hql.append(" (select gr1.id from GroupRemark gr1 where gr1.subject=m)) ");

                hql.append(" or exists (select gr2.id from GroupRemark gr2 where gr2.subject=m and ");
                hql.append(" gr2.newGroup in (:groups) and gr2.date= ");
                hql.append(" (select max(gr3.date) from GroupRemark gr3 ");
                hql.append(" where gr3.subject=m and gr3.date <= :date)) ");

                hql.append(" or (m.creationDate <= :date and exists (select gr2.id from ");
                hql.append(" GroupRemark gr2 where gr2.subject=m and gr2.oldGroup in (:groups) ");
                hql.append(" and gr2.date = (select min(gr3.date) from GroupRemark gr3 ");
                hql.append("  where gr3.subject=m and gr3.date > :date)) ))");
                namedParameters.put("groups", query.getGroups());
            } else {
                hql.append(" and m.creationDate <= :date ");
            }
            namedParameters.put("date", date);
        }

        return list(ResultType.ITERATOR, hql.toString(), namedParameters, query.getPageParameters());
    }

    // Used by Stats Key Dev > number new members
    @Override
    public List<Element> searchHistoryNew(final ElementQuery query) {
        Class<? extends Element> entityType;
        if (query instanceof AdminQuery) {
            entityType = Administrator.class;
        } else {
            entityType = Member.class;
        }
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        final Set<Relationship> fetch = query.getFetch();
        final StringBuilder hql = new StringBuilder();
        hql.append(" select e");
        hql.append(" from ").append(entityType.getName()).append(" e ");
        jpaCustomFieldHandler.appendJoins(hql, "e.customValues", query.getCustomValues());
        JpaQueryHelper.appendJoinFetch(hql, entityType, "e", fetch);
        hql.append(" where 1=1");
        JpaQueryHelper.addRightLikeParameterToQuery(hql, namedParameters, "e.user.username", query.getUsername());
        JpaQueryHelper.addLikeParameterToQuery(hql, namedParameters, "e.name", query.getName());

        final Collection<? extends Group> groups = query.getGroups();

        final Boolean enabled = query.getEnabled();
        // Specific tests for admins and members
        if (query instanceof AdminQuery) {

            JpaQueryHelper.addPeriodParameterToQuery(hql, namedParameters, "e.creationDate", query.getCreationPeriod());

            if (groups != null && !groups.isEmpty()) {
                JpaQueryHelper.addInParameterToQuery(hql, namedParameters, "e.group", groups);
            }

            if (enabled != null) {
                // If searching for admins, enabled means normal groups, while disabled means removed admins
                final Group.Status groupStatus = enabled ? Group.Status.NORMAL : Group.Status.REMOVED;
                JpaQueryHelper.addParameterToQuery(hql, namedParameters, "e.group.status", groupStatus);
            }
        } else {
            if (groups != null && !groups.isEmpty()) {
                hql.append(" and ( ( 1 = 1");

                final Period creationPeriod = query.getCreationPeriod();
                if (creationPeriod != null) {
                    JpaQueryHelper.addPeriodParameterToQuery(hql, namedParameters, "e.creationDate", creationPeriod);
                }
                hql.append(" and ((not exists ");
                hql.append(" (select gr.id from GroupRemark gr where gr.subject = e) ");
                hql.append(" and e.group in (:groups) )");

                namedParameters.put("groups", groups);

                hql.append("  or exists ( ");
                hql.append("    select gr.id ");
                hql.append("    from GroupRemark gr ");
                hql.append("    where ");
                hql.append("        gr.subject = e ");

                if (groups != null && !groups.isEmpty()) {
                    hql.append("        and gr.oldGroup in (:groups) ) ");
                }
                hql.append(")) or ");

                hql.append(" exists ( ");
                hql.append("    select gr.id ");
                hql.append("    from GroupRemark gr ");
                hql.append("    where ");
                hql.append("        gr.subject = e ");

                // using 'creation period'
                if (creationPeriod != null) {
                    JpaQueryHelper.addPeriodParameterToQuery(hql, namedParameters, "gr.date", creationPeriod);
                }
                if (groups != null && !groups.isEmpty()) {
                    // hql.append(" and (gr.newGroup in (:groups) ");
                    hql.append("        and gr.newGroup in (:groups) and gr.oldGroup not in (:groups) ");
                    namedParameters.put("groups", groups);
                }
                hql.append("    )");

                hql.append(" ) ");
            } else {
                final Period creationPeriod = query.getCreationPeriod();
                if (creationPeriod != null) {
                    JpaQueryHelper.addPeriodParameterToQuery(hql, namedParameters, "e.creationDate", creationPeriod);
                }
            }
        }
        // Custom Values
        jpaCustomFieldHandler.appendConditions(hql, namedParameters, query.getCustomValues());

        JpaQueryHelper.appendOrder(hql, "e.user.username");
        return list(query, hql.toString(), namedParameters);
    }

    // Used by Stats Key Developments > number of (disappeared) members
    @Override
    public List<Element> searchHistoryRemoved(final ElementQuery query) {
        Class<? extends Element> entityType;
        if (query instanceof AdminQuery) {
            entityType = Administrator.class;
        } else {
            entityType = Member.class;
        }
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        final Set<Relationship> fetch = query.getFetch();
        final StringBuilder hql = new StringBuilder();
        hql.append(" select e");
        hql.append(" from ").append(entityType.getName()).append(" e ");
        jpaCustomFieldHandler.appendJoins(hql, "e.customValues", query.getCustomValues());
        JpaQueryHelper.appendJoinFetch(hql, entityType, "e", fetch);
        hql.append(" where 1=1");
        if (query instanceof BrokerQuery) {
            hql.append(" and exists (select 1 from " + BrokerGroup.class.getName() + " bg where bg = e.group) ");
        }
        JpaQueryHelper.addRightLikeParameterToQuery(hql, namedParameters, "e.user.username", query.getUsername());
        JpaQueryHelper.addLikeParameterToQuery(hql, namedParameters, "e.name", query.getName());
        final MemberQuery memberQuery = (MemberQuery) query;

        final Boolean enabled = query.getEnabled();
        // Specific tests for admins and members
        if (query instanceof AdminQuery) {
            if (enabled != null) {
                // If searching for admins, enabled means normal groups, while disabled means removed admins
                final Group.Status groupStatus = enabled ? Group.Status.NORMAL : Group.Status.REMOVED;
                JpaQueryHelper.addParameterToQuery(hql, namedParameters, "e.group.status", groupStatus);
            }
        } else {

            hql.append(" and exists ( ");
            hql.append("    select gr.id ");
            hql.append("    from GroupRemark gr ");
            hql.append("    where ");
            hql.append("        gr.subject = e ");

            // Deactivation period
            final Period deactivationPeriod = memberQuery.getDeactivationPeriod();
            if (deactivationPeriod != null) {
                JpaQueryHelper.addPeriodParameterToQuery(hql, namedParameters, "gr.date", deactivationPeriod);
            }
            // if at least one group was chosen.
            // if more than one group is selected, it should not count moving
            // the members from and to those groups, because they are the 'same group' in this case.
            final Collection<? extends Group> groups = query.getGroups();
            if (groups != null && !groups.isEmpty()) {
                hql.append("     and gr.oldGroup in (:groups) and gr.newGroup not in (:groups) ");
                namedParameters.put("groups", groups);
            }
            // no group was chosen, no disappears members is returned;
            else {
                return new ArrayList<Element>();
            }
            hql.append(" 	)");
        }
        // Custom Values
        jpaCustomFieldHandler.appendConditions(hql, namedParameters, query.getCustomValues());

        JpaQueryHelper.appendOrder(hql, "e.user.username");
        return list(query, hql.toString(), namedParameters);
    }

    public void setJpaCustomFieldHandler(final JpaCustomFieldHandler jpaCustomFieldHandler) {
        this.jpaCustomFieldHandler = jpaCustomFieldHandler;
    }

    public void setSettingsServiceLocal(final SettingsServiceLocal settingsService) {
        this.settingsService = settingsService;
    }

    private Sort decideSorting(final FullTextElementQuery elementQuery) {
        Sort sort;
        // sorting
        LocalSettings localSettings = settingsService.getLocalSettings();
        SortOrder memberSortOrder = localSettings.getMemberSortOrder();
        if (elementQuery instanceof FullTextMemberQuery) {
            FullTextMemberQuery memberQuery = (FullTextMemberQuery) elementQuery;
            if (memberQuery.getMemberSortOrder() != null) {
                memberSortOrder = memberQuery.getMemberSortOrder();
            }
        }
        if (memberSortOrder == SortOrder.CHRONOLOGICAL) {
            sort = new Sort(new SortField("creationDate", SortField.Type.STRING, true));
        } else {
            if (elementQuery.getNameDisplay() == MemberResultDisplay.NAME) {
                sort = new Sort(new SortField("nameForSort", SortField.Type.STRING));
            } else {
                sort = new Sort(new SortField("usernameForSort", SortField.Type.STRING));
            }
        }
        return sort;
    }

    private MultiFieldQueryParser getQueryParser(final Analyzer analyzer) {
        final Map<String, Float> boosts = new HashMap<String, Float>();
        boosts.put("name", 2.0F);
        boosts.put("username", 1.5F);
        return new MultiFieldQueryParser(LuceneUtils.LUCENE_VERSION, FIELDS_FULL_TEXT, analyzer, boosts);
    }
}
