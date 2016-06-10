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
package nl.strohalm.cyclos.webservices.ads;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import nl.strohalm.cyclos.webservices.model.AdVO;
import nl.strohalm.cyclos.webservices.model.ResultPage;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Page results for ads
 * @author luis
 */
public class AdResultPage extends ResultPage<AdVO> {
    private static final long serialVersionUID = -186613342878700230L;

    public AdResultPage() {
    }

    public AdResultPage(final int currentPage, final int pageSize, final int totalCount, final List<AdVO> elements) {
        super(currentPage, pageSize, totalCount, elements);
    }

    @JsonIgnore
    @XmlElement
    public List<AdVO> getAds() {
        return getElements();
    }

    public void setAds(final List<AdVO> ads) {
        setElements(ads);
    }
}
