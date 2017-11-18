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
package nl.strohalm.cyclos.dao.infotexts;

import nl.strohalm.cyclos.dao.BaseDAOImpl;
import nl.strohalm.cyclos.entities.infotexts.InfoText;
import nl.strohalm.cyclos.entities.infotexts.InfoTextQuery;
import nl.strohalm.cyclos.entities.utils.Period;
import nl.strohalm.cyclos.utils.jpa.JpaQueryHelper;
import nl.strohalm.cyclos.utils.jpa.JpaQueryHelper.QueryParameter;
import org.apache.commons.lang.StringUtils;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InfoTextDAOImpl extends BaseDAOImpl<InfoText> implements InfoTextDAO {

    public InfoTextDAOImpl() {
        super(InfoText.class);
    }

    @Override
    public List<InfoText> search(final InfoTextQuery query) {
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        final StringBuilder hql = JpaQueryHelper.getInitialQuery(InfoText.class, "info", query.getFetch());

        final String alias = StringUtils.trimToNull(query.getAlias());
        if (alias != null) {
            hql.append(" and :alias member of info.aliases ");
            namedParameters.put("alias", alias);
        }

        if (query.getKeywords() != null) {
            hql.append(" and (info.subject like :keywords or info.body like :keywords) ");
            namedParameters.put("keywords", "%" + query.getKeywords().toUpperCase() + "%");
        }

        if (query.isWithBodyOnly()) {
            hql.append(" and length(info.body) > 0");
        }

        if (query.isOnlyActive()) {
            JpaQueryHelper.addParameterToQuery(hql, namedParameters, "info.enabled", Boolean.TRUE);
            final Period period = Period.day(Calendar.getInstance());
            QueryParameter beginParameter = JpaQueryHelper.getBeginParameter(period);
            if (beginParameter != null) {
                hql.append(" and (info.validity.end is null or info.validity.end " + beginParameter.getOperator() + " :_begin_)");
                namedParameters.put("_begin_", beginParameter.getValue());
            }
            QueryParameter endParameter = JpaQueryHelper.getEndParameter(period);
            if (endParameter != null) {
                hql.append(" and (info.validity.begin is null or info.validity.begin " + endParameter.getOperator() + " :_end_)");
                namedParameters.put("_end_", endParameter.getValue());
            }
        } else {
            JpaQueryHelper.addParameterToQuery(hql, namedParameters, "info.enabled", query.getEnabled());
            JpaQueryHelper.addPeriodParameterToQuery(hql, namedParameters, "info.validity.begin", query.getStartIn());
            JpaQueryHelper.addPeriodParameterToQuery(hql, namedParameters, "info.validity.end", query.getEndIn());
        }

        return list(query, hql.toString(), namedParameters);
    }
}
