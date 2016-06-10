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

import nl.strohalm.cyclos.webservices.model.SearchParameters;
import nl.strohalm.cyclos.webservices.utils.ObjectHelper;

/**
 * Parameters for info text searches via web services
 * @author luis
 */
public class InfoTextSearchParameters extends SearchParameters {

    private static final long serialVersionUID = -9009496012711936976L;
    private String            keywords;
    private String            alias;
    private Boolean           withBodyOnly     = false;

    public String getAlias() {
        return alias;
    }

    public String getKeywords() {
        return keywords;
    }

    public boolean getWithBodyOnly() {
        return ObjectHelper.valueOf(withBodyOnly);
    }

    public void setAlias(final String alias) {
        this.alias = alias;
    }

    public void setKeywords(final String keywords) {
        this.keywords = keywords;
    }

    public void setWithBodyOnly(final boolean withBodyOnly) {
        this.withBodyOnly = withBodyOnly;
    }

    @Override
    public String toString() {
        return "InfoTextSearchParameters [keywords=" + keywords + ", alias=" + alias + ", withBodyOnly=" + withBodyOnly + "]";
    }
}
