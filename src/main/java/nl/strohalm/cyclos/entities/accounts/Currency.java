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
package nl.strohalm.cyclos.entities.accounts;

import java.math.BigDecimal;

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.Relationship;

/**
 * An account type currency
 * 
 * @author luis
 * @author rinke (rate stuff)
 */
public class Currency extends Entity {

    public static enum Relationships implements Relationship {
        A_RATE_PARAMETERS("aRateParameters"), D_RATE_PARAMETERS("dRateParameters"), I_RATE_PARAMETERS("iRateParameters");

        private final String name;

        private Relationships(final String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    private static final long serialVersionUID = 5910755754107368364L;

    private String            name;
    private String            description;
    private String            symbol;
    private String            pattern          = "#amount#";
    private DRateParameters   dRateParameters;
    private ARateParameters   aRateParameters;
    private IRateParameters   iRateParameters;

    public ARateParameters getaRateParameters() {
        return aRateParameters;
    }

    public String getDescription() {
        return description;
    }

    public DRateParameters getdRateParameters() {
        return dRateParameters;
    }

    public IRateParameters getiRateParameters() {
        return iRateParameters;
    }

    public BigDecimal getMinimalD() {
        if (isEnableDRate()) {
            return dRateParameters.getMinimalD();
        }
        return null;
    }

    @Override
    public String getName() {
        return name;
    }

    public String getPattern() {
        return pattern;
    }

    public String getSymbol() {
        return symbol;
    }

    public boolean isEnableARate() {
        return aRateParameters != null;
    }

    public boolean isEnableDRate() {
        return dRateParameters != null;
    }

    public boolean isEnableIRate() {
        return iRateParameters != null;
    }

    public void setaRateParameters(final ARateParameters rateParameters) {
        aRateParameters = rateParameters;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setdRateParameters(final DRateParameters dRateParameters) {
        this.dRateParameters = dRateParameters;
    }

    public void setiRateParameters(final IRateParameters iRateParameters) {
        this.iRateParameters = iRateParameters;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setPattern(final String pattern) {
        this.pattern = pattern;
    }

    public void setSymbol(final String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return getId() + " - " + symbol;
    }

}
