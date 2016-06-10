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
package nl.strohalm.cyclos.webservices.utils;

import java.util.List;

import nl.strohalm.cyclos.entities.infotexts.InfoText;
import nl.strohalm.cyclos.entities.infotexts.InfoTextQuery;
import nl.strohalm.cyclos.utils.Period;
import nl.strohalm.cyclos.utils.conversion.Transformer;
import nl.strohalm.cyclos.webservices.infotexts.InfoTextResultPage;
import nl.strohalm.cyclos.webservices.infotexts.InfoTextSearchParameters;
import nl.strohalm.cyclos.webservices.model.InfoTextVO;

/**
 * Contains utility methods to handle custom fields<br>
 * <b>WARN</b>: Be aware that this helper <b>doesn't</b> access the services through the security layer. They are all local services.
 * @author luis
 */
public class InfoTextHelper {

    private QueryHelper queryHelper;

    public void setQueryHelper(final QueryHelper queryHelper) {
        this.queryHelper = queryHelper;
    }

    public InfoTextQuery toQuery(final InfoTextSearchParameters params) {
        if (params == null) {
            return null;
        }
        final InfoTextQuery query = new InfoTextQuery();
        queryHelper.fill(params, query);
        query.setAlias(params.getAlias());
        query.setKeywords(params.getKeywords());
        return query;
    }

    public InfoTextResultPage toResultPage(final List<InfoText> list) {
        return queryHelper.toResultPage(InfoTextResultPage.class, list, new Transformer<InfoText, InfoTextVO>() {
            @Override
            public InfoTextVO transform(final InfoText infoText) {
                return toVO(infoText);
            }
        });
    }

    public InfoTextVO toVO(final InfoText infoText) {
        final InfoTextVO vo = new InfoTextVO();
        vo.setId(infoText.getId());
        vo.setSubject(infoText.getSubject());
        vo.setBody(infoText.getBody());
        final Period validity = infoText.getValidity();
        vo.setValidFrom(validity == null ? null : validity.getBegin());
        vo.setValidTo(validity == null ? null : validity.getEnd());
        return vo;
    }

}
