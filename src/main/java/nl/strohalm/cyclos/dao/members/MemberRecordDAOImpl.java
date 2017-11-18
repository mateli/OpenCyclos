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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.strohalm.cyclos.dao.IndexedDAOImpl;
import nl.strohalm.cyclos.entities.customization.fields.MemberRecordCustomFieldValue;
import nl.strohalm.cyclos.entities.exceptions.DaoException;
import nl.strohalm.cyclos.entities.exceptions.QueryParseException;
import nl.strohalm.cyclos.entities.members.records.FullTextMemberRecordQuery;
import nl.strohalm.cyclos.entities.members.records.MemberRecord;
import nl.strohalm.cyclos.entities.members.records.MemberRecordQuery;
import nl.strohalm.cyclos.utils.jpa.JpaCustomFieldHandler;
import nl.strohalm.cyclos.utils.jpa.JpaQueryHelper;
import nl.strohalm.cyclos.utils.lucene.Filters;
import nl.strohalm.cyclos.utils.lucene.LuceneUtils;

import org.apache.commons.collections.CollectionUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;

/**
 * Implementation for member record DAO
 * @author Jefferson Magno
 * @author luis
 */
public class MemberRecordDAOImpl extends IndexedDAOImpl<MemberRecord> implements MemberRecordDAO {

    private static final String[]       FIELDS_FULL_TEXT = { "customValues", "element.name", "element.username", "element.email", "by.name", "by.username" };
    private JpaCustomFieldHandler jpaCustomFieldHandler;

    public MemberRecordDAOImpl() {
        super(MemberRecord.class);
    }

    @Override
    public List<MemberRecord> fullTextSearch(final FullTextMemberRecordQuery recordQuery) {
        final String keywords = recordQuery.getKeywords();
        Analyzer analyzer = recordQuery.getAnalyzer();
        Query query;
        Sort sort = null;
        if (keywords == null) {
            query = new MatchAllDocsQuery();
            sort = new Sort(new SortField("date", SortField.Type.STRING, true));
        } else {
            try {
                query = getQueryParser(analyzer).parse(keywords);
            } catch (final ParseException e) {
                throw new QueryParseException(e);
            }
        }
        final Filters filters = new Filters();
        filters.addTerms("type", recordQuery.getType());
        filters.addTerms("element", recordQuery.getElement());
        filters.addTerms("element.group", recordQuery.getGroups());
        filters.addTerms("element.broker", recordQuery.getBroker());
        filters.addTerms("by", recordQuery.getBy());
        filters.addPeriod("date", recordQuery.getPeriod());

        // Custom fields
        if (CollectionUtils.isNotEmpty(recordQuery.getCustomValues())) {
            for (final MemberRecordCustomFieldValue fieldValue : recordQuery.getCustomValues()) {
                addCustomField(filters, analyzer, fieldValue);
            }
        }
        return list(MemberRecord.class, recordQuery, query, filters, sort);
    }

    @Override
    public List<MemberRecord> search(final MemberRecordQuery query) throws DaoException {
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        final StringBuilder hql = new StringBuilder();
        hql.append(" select mr");
        hql.append(" from ").append(getEntityType().getName()).append(" mr ");
        jpaCustomFieldHandler.appendJoins(hql, "mr.customValues", query.getCustomValues());
        JpaQueryHelper.appendJoinFetch(hql, getEntityType(), "mr", query.getFetch());
        hql.append(" where 1=1");
        JpaQueryHelper.addParameterToQuery(hql, namedParameters, "mr.type", query.getType());
        JpaQueryHelper.addParameterToQuery(hql, namedParameters, "mr.element", query.getElement());
        JpaQueryHelper.addInParameterToQuery(hql, namedParameters, "mr.element.group", query.getGroups());
        JpaQueryHelper.addParameterToQuery(hql, namedParameters, "mr.element.broker", query.getBroker());
        JpaQueryHelper.addParameterToQuery(hql, namedParameters, "mr.by", query.getBy());
        JpaQueryHelper.addPeriodParameterToQuery(hql, namedParameters, "mr.date", query.getPeriod());
        jpaCustomFieldHandler.appendConditions(hql, namedParameters, query.getCustomValues());
        JpaQueryHelper.appendOrder(hql, "mr.date desc", "mr.element.name");
        return list(query, hql.toString(), namedParameters);
    }

    public void setJpaCustomFieldHandler(final JpaCustomFieldHandler jpaCustomFieldHandler) {
        this.jpaCustomFieldHandler = jpaCustomFieldHandler;
    }

    private MultiFieldQueryParser getQueryParser(final Analyzer analyzer) {
        final Map<String, Float> boosts = new HashMap<String, Float>();
        boosts.put("customValues", 2F);
        boosts.put("by.name", 0.5F);
        boosts.put("by.username", 0.5F);
        return new MultiFieldQueryParser(LuceneUtils.LUCENE_VERSION, FIELDS_FULL_TEXT, analyzer, boosts);
    }

}
