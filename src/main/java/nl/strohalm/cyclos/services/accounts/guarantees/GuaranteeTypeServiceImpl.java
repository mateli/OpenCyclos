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
package nl.strohalm.cyclos.services.accounts.guarantees;

import java.math.BigDecimal;
import java.util.List;

import nl.strohalm.cyclos.dao.accounts.guarantees.GuaranteeTypeDAO;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.guarantees.GuaranteeType;
import nl.strohalm.cyclos.entities.accounts.guarantees.GuaranteeTypeQuery;
import nl.strohalm.cyclos.utils.validation.GeneralValidation;
import nl.strohalm.cyclos.utils.validation.ValidationError;
import nl.strohalm.cyclos.utils.validation.ValidationException;
import nl.strohalm.cyclos.utils.validation.Validator;

/**
 * Implementation class for the guarantee types service interface
 * @author Jefferson Magno
 */
public class GuaranteeTypeServiceImpl implements GuaranteeTypeServiceLocal {

    private class AuthorizerByValidation implements GeneralValidation {

        private static final long serialVersionUID = -1616070380031832842L;

        public ValidationError validate(final Object object) {
            final GuaranteeType guaranteeType = (GuaranteeType) object;
            if (guaranteeType.getModel() == null || guaranteeType.getAuthorizedBy() == null) {
                return null;
            }

            final GuaranteeType.AuthorizedBy authorizedBy = guaranteeType.getAuthorizedBy();
            if (guaranteeType.getModel() == GuaranteeType.Model.WITH_PAYMENT_OBLIGATION && authorizedBy != GuaranteeType.AuthorizedBy.ISSUER && authorizedBy != GuaranteeType.AuthorizedBy.BOTH) {
                return new ValidationError("guaranteeType.error.invalidAuthorizedBy");
            } else {
                return null;
            }
        }
    }

    // Used by the transfer type validation
    private enum TransferTypes {
        CREDIT, ISSUE, FORWARD
    }

    private class TransferTypeValidation implements GeneralValidation {
        private static final long serialVersionUID = -1616070380031832842L;

        private TransferTypes     trasferType;

        public TransferTypeValidation(final TransferTypes tt) {
            trasferType = tt;
        }

        public ValidationError validate(final Object object) {
            final GuaranteeType guaranteeType = (GuaranteeType) object;
            switch (trasferType) {
                case CREDIT:
                    final boolean hasCreditFee = guaranteeType.getCreditFee().getFee() != null && BigDecimal.ZERO.compareTo(guaranteeType.getCreditFee().getFee()) == -1;
                    if (guaranteeType.getCreditFeeTransferType() == null && (!guaranteeType.getCreditFee().isReadonly() || hasCreditFee)) {
                        return new ValidationError("guaranteeType.error.creditFeeTransferType");
                    }
                    break;
                case FORWARD:
                    if (guaranteeType.getForwardTransferType() == null && guaranteeType.getModel() != GuaranteeType.Model.WITH_BUYER_ONLY) {
                        return new ValidationError("guaranteeType.error.forwardTransferType");
                    }
                    break;
                case ISSUE:
                    final boolean hasIssueFee = guaranteeType.getIssueFee().getFee() != null && BigDecimal.ZERO.compareTo(guaranteeType.getIssueFee().getFee()) == -1;
                    if (guaranteeType.getIssueFeeTransferType() == null && (!guaranteeType.getIssueFee().isReadonly() || hasIssueFee)) {
                        return new ValidationError("guaranteeType.error.issueFeeTransferType");
                    }
            }
            return null;
        }
    };

    private GuaranteeTypeDAO guaranteeTypeDao;

    public boolean areEnabledGuaranteeTypes() {
        final GuaranteeTypeQuery queryParameters = new GuaranteeTypeQuery();
        queryParameters.setEnabled(true);
        return !search(queryParameters).isEmpty();
    }

    public GuaranteeType load(final Long id, final Relationship... fetch) {
        return guaranteeTypeDao.load(id, fetch);
    }

    public int remove(final Long... ids) {
        return guaranteeTypeDao.delete(ids);
    }

    public GuaranteeType save(final GuaranteeType guaranteeType) {
        validate(guaranteeType);
        verify(guaranteeType);
        if (guaranteeType.isTransient()) {
            return guaranteeTypeDao.insert(guaranteeType);
        } else {
            return guaranteeTypeDao.update(guaranteeType);
        }
    }

    public List<GuaranteeType> search(final GuaranteeTypeQuery query) {
        final List<GuaranteeType> guaranteeTypes = guaranteeTypeDao.search(query);

        return guaranteeTypes;
    }

    public void setGuaranteeTypeDao(final GuaranteeTypeDAO guaranteeTypeDao) {
        this.guaranteeTypeDao = guaranteeTypeDao;
    }

    public void validate(final GuaranteeType guaranteeType) throws ValidationException {
        getValidator().validate(guaranteeType);
    }

    private Validator getValidator() {
        final Validator validator = new Validator("guaranteeType");
        validator.property("name").required();
        validator.property("model").required();
        validator.property("currency").required();
        validator.property("authorizedBy").required();
        validator.property("loanTransferType").key("guaranteeType.error.loanTransferType").required();
        validator.general(new AuthorizerByValidation());
        validator.general(new TransferTypeValidation(TransferTypes.CREDIT));
        validator.general(new TransferTypeValidation(TransferTypes.ISSUE));
        validator.general(new TransferTypeValidation(TransferTypes.FORWARD));
        return validator;
    }

    /**
     * Checks if a guarantee fee is not generated from the specified guarantee type fee (i.e. it is zero and read-only)
     * @param feeVO guarantee type fee
     * @return true only if this guarantee type fee not generate a guarantee fee (is empty)
     */
    private boolean isEmptyGuaranteeTypeFee(final GuaranteeTypeFeeVO feeVO) {
        return BigDecimal.ZERO.compareTo(feeVO.getFee()) == 0 && feeVO.isReadonly();
    }

    private void verify(final GuaranteeType guaranteeType) {
        guaranteeType.setCreditFee(verifyFee(guaranteeType.getCreditFee()));
        guaranteeType.setIssueFee(verifyFee(guaranteeType.getIssueFee()));

        // ensure the fee payer (if the model is different from Buyer & seller the payer always is the seller)
        if (guaranteeType.getModel() != GuaranteeType.Model.WITH_BUYER_AND_SELLER) {
            guaranteeType.setCreditFeePayer(GuaranteeType.FeePayer.SELLER);
            guaranteeType.setIssueFeePayer(GuaranteeType.FeePayer.SELLER);
        } else if (guaranteeType.getModel() == GuaranteeType.Model.WITH_BUYER_ONLY) {
            guaranteeType.setForwardTransferType(null);
        }

        if (isEmptyGuaranteeTypeFee(guaranteeType.getCreditFee())) {
            guaranteeType.setCreditFeeTransferType(null);
        }

        if (isEmptyGuaranteeTypeFee(guaranteeType.getIssueFee())) {
            guaranteeType.setIssueFeeTransferType(null);
        }
    }

    private GuaranteeTypeFeeVO verifyFee(GuaranteeTypeFeeVO fee) {
        if (fee == null) {
            fee = new GuaranteeTypeFeeVO();
        }
        if (fee.getFee() == null) {
            fee.setFee(BigDecimal.ZERO);
        }
        return fee;
    }
}
