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

import javax.xml.bind.annotation.XmlElement;

import nl.strohalm.cyclos.webservices.model.InfoTextVO;
import nl.strohalm.cyclos.webservices.model.ResultPage;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Page results for info texts
 * @author luis
 */
public class InfoTextResultPage extends ResultPage<InfoTextVO> {
    private static final long serialVersionUID = 2676760669726197474L;

    public InfoTextResultPage() {
    }

    public InfoTextResultPage(final int currentPage, final int pageSize, final int totalCount, final List<InfoTextVO> elements) {
        super(currentPage, pageSize, totalCount, elements);
    }

    @JsonIgnore
    @XmlElement
    public List<InfoTextVO> getInfoTexts() {
        return getElements();
    }

    public void setInfoTexts(final List<InfoTextVO> infoTexts) {
        setElements(infoTexts);
    }

}
