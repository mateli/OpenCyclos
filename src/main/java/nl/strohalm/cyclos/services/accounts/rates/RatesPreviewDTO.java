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
package nl.strohalm.cyclos.services.accounts.rates;

/**
 * DTO for passing the rate related params to TransactionFeeService.preview
 * 
 * @author rinke
 */
public class RatesPreviewDTO extends RatesResultDTO {

    private static final long serialVersionUID = -1114737592969047033L;
    private boolean           graph;

    public RatesPreviewDTO() {
        super();
    }

    public RatesPreviewDTO(final RatesResultDTO resultDTO) {
        super();
        setaRate(resultDTO.getaRate());
        setDate(resultDTO.getDate());
        setdRate(resultDTO.getdRate());
        setEmissionDate(resultDTO.getEmissionDate());
        setExpirationDate(resultDTO.getExpirationDate());
        setRateBalanceCorrection(resultDTO.getRateBalanceCorrection());
        setVirtualRatedBalance(resultDTO.getVirtualRatedBalance());
    }

    public boolean isGraph() {
        return graph;
    }

    public void setGraph(final boolean graph) {
        this.graph = graph;
    }

}
