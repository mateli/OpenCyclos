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
package nl.strohalm.cyclos.entities.ads;

import java.util.Arrays;

import nl.strohalm.cyclos.entities.ads.Ad.TradeType;
import nl.strohalm.cyclos.utils.DataObject;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Query parameters for counting ads in categories
 * 
 * @author luis
 */
public class AdCategoryWithCounterQuery extends DataObject {
    private static final long serialVersionUID = 9210994368890022363L;
    private TradeType         tradeType;
    private boolean           externalPublication;
    private Long[]            groupIds;

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AdCategoryWithCounterQuery)) {
            return false;
        }
        final AdCategoryWithCounterQuery o = (AdCategoryWithCounterQuery) obj;
        return new EqualsBuilder()
                .append(tradeType, o.tradeType)
                .append(externalPublication, o.externalPublication)
                .append(groupIds, o.groupIds)
                .isEquals();
    }

    public Long[] getGroupIds() {
        return groupIds;
    }

    public TradeType getTradeType() {
        return tradeType;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(tradeType)
                .append(externalPublication)
                .append(groupIds)
                .toHashCode();
    }

    public boolean isExternalPublication() {
        return externalPublication;
    }

    public void setExternalPublication(final boolean externalPublication) {
        this.externalPublication = externalPublication;
    }

    public void setGroupIds(final Long[] groupIds) {
        this.groupIds = groupIds;
        if (groupIds != null) {
            Arrays.sort(groupIds);
        }
    }

    public void setTradeType(final TradeType tradeType) {
        this.tradeType = tradeType;
    }

}
