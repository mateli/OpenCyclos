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
package nl.strohalm.cyclos.dao;

import java.util.Arrays;
import java.util.List;

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.Indexable;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.customization.fields.CustomField;
import nl.strohalm.cyclos.entities.customization.fields.CustomFieldValue;
import nl.strohalm.cyclos.utils.EntityHelper;
import nl.strohalm.cyclos.utils.StringHelper;
import nl.strohalm.cyclos.utils.lucene.Filters;
import nl.strohalm.cyclos.utils.lucene.IndexHandler;
import nl.strohalm.cyclos.utils.lucene.LuceneQueryHandler;
import nl.strohalm.cyclos.utils.query.PageParameters;
import nl.strohalm.cyclos.utils.query.QueryParameters;
import nl.strohalm.cyclos.utils.query.QueryParameters.ResultType;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;

/**
 * Base implementation for indexed DAOs
 * @author luis
 */
public abstract class IndexedDAOImpl<E extends Entity & Indexable> extends BaseDAOImpl<E> implements IndexedDAO<E> {

    protected IndexHandler       indexHandler;
    protected LuceneQueryHandler luceneQueryHandler;

    public IndexedDAOImpl(final Class<E> entityClass) {
        super(entityClass);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void addToIndex(final E entity) {
        final Class<? extends Indexable> type = (Class<? extends Indexable>) EntityHelper.getRealClass(entity);
        indexHandler.index(type, entity.getId());
    }

    @Override
    public void removeFromIndex(final Class<? extends E> entityType, final Long... ids) {
        if (ids != null && ids.length > 0) {
            indexHandler.remove(entityType, Arrays.asList(ids));
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void removeFromIndex(final E entity) {
        final Class<? extends Indexable> type = (Class<? extends Indexable>) EntityHelper.getRealClass(entity);
        indexHandler.remove(type, entity.getId());
    }

    public void setIndexHandler(final IndexHandler indexHandler) {
        this.indexHandler = indexHandler;
    }

    public void setLuceneQueryHandler(final LuceneQueryHandler luceneQueryHandler) {
        this.luceneQueryHandler = luceneQueryHandler;
    }

    protected void addCustomField(final Filters filters, final Analyzer analyzer, final CustomFieldValue fieldValue) {
        addCustomField(filters, analyzer, fieldValue, "customValues.%s");
    }

    protected void addCustomField(final Filters filters, final Analyzer analyzer, final CustomFieldValue fieldValue, final String fieldNamePattern) {
        CustomField field = fieldValue.getField();
        if (field == null) {
            return;
        }
        field = getFetchDao().fetch(field);
        String value = fieldValue.getValue();
        if (field.getType() == CustomField.Type.ENUMERATED && fieldValue.getPossibleValue() != null) {
            value = fieldValue.getPossibleValue().getId().toString();
        } else if (field.getType() == CustomField.Type.MEMBER && fieldValue.getMemberValue() != null) {
            value = fieldValue.getMemberValue().getId().toString();
        }
        if (StringUtils.isNotEmpty(field.getPattern())) {
            // For masked fields, ensure to remove the mask
            value = StringHelper.removeMask(field.getPattern(), value, false);
        }
        if (StringUtils.isNotEmpty(value)) {
            String fieldName = String.format(fieldNamePattern, field.getId());
            switch (field.getType()) {
                case ENUMERATED:
                    // For enumerated fields, the value might actually be split by commas
                    String[] values = StringUtils.split(value, ',');
                    filters.addTerms(fieldName, (Object[]) values);
                    break;
                case STRING:
                case URL:
                    // Analyzed string fields
                    filters.addFieldQuery(analyzer, fieldName, value);
                    break;
                default:
                    // Other types are not analyzed
                    filters.addTerms(fieldName, value);
                    break;
            }
        }
    }

    /**
     * Executes a full-text query, applying the desired result type and page parameters
     */
    protected <T extends E> List<T> list(final Class<T> entityType, final QueryParameters queryParameters, final Query query, final Filters filters, final Sort sort) {
        final ResultType resultType = queryParameters == null || queryParameters.getResultType() == null ? ResultType.LIST : queryParameters.getResultType();
        final PageParameters pageParameters = queryParameters == null ? null : queryParameters.getPageParameters();
        final Relationship[] fetch = queryParameters == null || queryParameters.getFetch() == null ? null : queryParameters.getFetch().toArray(new Relationship[queryParameters.getFetch().size()]);
        return luceneQueryHandler.executeQuery(entityType, query, filters, sort, resultType, pageParameters, fetch);
    }
}
