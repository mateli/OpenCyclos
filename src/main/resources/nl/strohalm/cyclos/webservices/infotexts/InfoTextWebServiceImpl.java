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
package nl.strohalm.cyclos.webservices.infotexts;

import java.util.List;

import javax.jws.WebService;

import nl.strohalm.cyclos.entities.infotexts.InfoText;
import nl.strohalm.cyclos.entities.infotexts.InfoTextQuery;
import nl.strohalm.cyclos.services.infotexts.InfoTextServiceLocal;
import nl.strohalm.cyclos.webservices.model.InfoTextVO;
import nl.strohalm.cyclos.webservices.utils.InfoTextHelper;

/**
 * Implementation for info text web service
 * @author luis
 */
@WebService(name = "infoTexts", serviceName = "infoTexts")
public class InfoTextWebServiceImpl implements InfoTextWebService {

    private InfoTextHelper       infoTextHelper;
    private InfoTextServiceLocal infoTextServiceLocal;

    @Override
    public InfoTextVO loadByAlias(final String alias) {
        final InfoText infoText = infoTextServiceLocal.loadByAliasForWebServices(alias);
        return infoTextHelper.toVO(infoText);
    }

    @Override
    public InfoTextVO loadById(final Long id) {
        final InfoText infoText = infoTextServiceLocal.loadForWebServices(id);
        return infoTextHelper.toVO(infoText);
    }

    @Override
    public InfoTextResultPage search(final InfoTextSearchParameters params) {
        InfoTextQuery query = infoTextHelper.toQuery(params);
        if (query == null) {
            query = new InfoTextQuery();
        }
        final List<InfoText> list = infoTextServiceLocal.searchForWebServices(query);
        return infoTextHelper.toResultPage(list);
    }

    public void setInfoTextHelper(final InfoTextHelper infoTextHelper) {
        this.infoTextHelper = infoTextHelper;
    }

    public void setInfoTextServiceLocal(final InfoTextServiceLocal infoTextService) {
        infoTextServiceLocal = infoTextService;
    }

}
