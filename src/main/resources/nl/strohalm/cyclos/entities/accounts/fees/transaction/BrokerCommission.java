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
package nl.strohalm.cyclos.entities.accounts.fees.transaction;

import java.util.Collection;

import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.groups.BrokerGroup;
import nl.strohalm.cyclos.utils.StringValuedEnum;

/**
 * A fee that represents a broker commission
 * @author luis
 */
public class BrokerCommission extends TransactionFee {

    public static enum Relationships implements Relationship {
        BROKER_GROUPS("brokerGroups");
        private final String name;

        private Relationships(final String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public static enum When implements StringValuedEnum {
        ALWAYS("A"), COUNT("C"), DAYS("D");
        private final String value;

        private When(final String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public static enum WhichBroker implements StringValuedEnum {
        SOURCE("S"), DESTINATION("D");
        private final String value;

        private WhichBroker(final String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    private static final long       serialVersionUID = 3069444935878230607L;

    private Integer                 count;
    private When                    when;
    private WhichBroker             whichBroker;
    private boolean                 allBrokerGroups  = true;
    private Collection<BrokerGroup> brokerGroups;

    public Collection<BrokerGroup> getBrokerGroups() {
        return brokerGroups;
    }

    public Integer getCount() {
        return count;
    }

    @Override
    public Nature getNature() {
        return Nature.BROKER;
    }

    public When getWhen() {
        return when;
    }

    public WhichBroker getWhichBroker() {
        return whichBroker;
    }

    public boolean isAllBrokerGroups() {
        return allBrokerGroups;
    }

    public void setAllBrokerGroups(final boolean allBrokerGroups) {
        this.allBrokerGroups = allBrokerGroups;
    }

    public void setBrokerGroups(final Collection<BrokerGroup> brokerGroups) {
        this.brokerGroups = brokerGroups;
    }

    public void setCount(final Integer count) {
        this.count = count;
    }

    public void setWhen(final When when) {
        this.when = when;
    }

    public void setWhichBroker(final WhichBroker whichBroker) {
        this.whichBroker = whichBroker;
    }

}
