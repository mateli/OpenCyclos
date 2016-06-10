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
package nl.strohalm.cyclos.webservices.model;

import java.util.List;

import javax.xml.bind.annotation.XmlType;

import nl.strohalm.cyclos.webservices.utils.ObjectHelper;

/**
 * Detailed data for ad category for web services
 * @author luis
 */
@XmlType(name = "detailedAdCategory")
public class DetailedAdCategoryVO extends AdCategoryVO {
    private static final long          serialVersionUID = -477774908557924729L;
    private String                     fullName;
    private List<DetailedAdCategoryVO> children;
    private Integer                    level            = 0;
    private Integer                    countOffer       = 0;
    private Integer                    countSearch      = 0;

    public List<DetailedAdCategoryVO> getChildren() {
        return children;
    }

    public int getCountOffer() {
        return ObjectHelper.valueOf(countOffer);
    }

    public int getCountSearch() {
        return ObjectHelper.valueOf(countSearch);
    }

    public String getFullName() {
        return fullName;
    }

    public int getLevel() {
        return ObjectHelper.valueOf(level);
    }

    public void setChildren(final List<DetailedAdCategoryVO> children) {
        this.children = children;
    }

    public void setCountOffer(final int countOffer) {
        this.countOffer = countOffer;
    }

    public void setCountSearch(final int countSearch) {
        this.countSearch = countSearch;
    }

    public void setFullName(final String fullName) {
        this.fullName = fullName;
    }

    public void setLevel(final int level) {
        this.level = level;
    }

    @Override
    public String toString() {
        final StringBuilder buffer = new StringBuilder();
        buffer.append("DetailedAdCategoryVO(fullName=" + fullName + ", level=" + level + ", countOffer=" + countOffer + ", countSearch=" + countSearch);
        if (children == null) {
            buffer.append("childrens=0)");
        } else {
            buffer.append("childrens=" + children.size() + ")");
        }
        return buffer.toString();
    }
}
