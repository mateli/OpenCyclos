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
package nl.strohalm.cyclos.services.transfertypes;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedList;

import nl.strohalm.cyclos.dao.FetchDAO;
import nl.strohalm.cyclos.dao.accounts.transactions.AuthorizationLevelDAO;
import nl.strohalm.cyclos.dao.accounts.transactions.TransferTypeDAO;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.transactions.AuthorizationLevel;
import nl.strohalm.cyclos.entities.accounts.transactions.AuthorizationLevel.Authorizer;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.services.fetch.FetchServiceLocal;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.validation.GeneralValidation;
import nl.strohalm.cyclos.utils.validation.ValidationError;
import nl.strohalm.cyclos.utils.validation.ValidationException;
import nl.strohalm.cyclos.utils.validation.Validator;

import org.apache.commons.collections.CollectionUtils;

/**
 * Implementation for AuthorizationLevelService
 * @author luis, Jefferson Magno
 */
public class AuthorizationLevelServiceImpl implements AuthorizationLevelServiceLocal {

    /**
     * Validates the authorization level before saving. If the authorizer of a level is an admin, checks the requirement for admin groups
     */
    public class AdminGroupRequiredValidation implements GeneralValidation {

        private static final long serialVersionUID = -4472441677571047937L;

        @Override
        public ValidationError validate(final Object object) {
            final AuthorizationLevel authorizationLevel = (AuthorizationLevel) object;
            if (authorizationLevel.getAuthorizer() == AuthorizationLevel.Authorizer.ADMIN && CollectionUtils.isEmpty(authorizationLevel.getAdminGroups())) {
                return new ValidationError("authorizationLevel.error.adminGroupRequired");
            }
            return null;
        }

    }

    /**
     * Validates the authorization level before saving. The amount of the current level must be higher than the amount of the lower level and must be
     * lesser than the amount of the higher level
     * @author Jefferson Magno
     */
    public class AmountValidation implements GeneralValidation {

        private static final long serialVersionUID = -6923517450707542011L;

        @Override
        public ValidationError validate(final Object object) {
            final AuthorizationLevel authorizationLevel = (AuthorizationLevel) object;
            final TransferType transferType = authorizationLevel.getTransferType();
            if (!CollectionUtils.isEmpty(transferType.getAuthorizationLevels())) {
                final AuthorizationLevel lowerLevel = getLowerLevel(authorizationLevel);
                final AuthorizationLevel higherLevel = getHigherLevel(authorizationLevel);
                final BigDecimal amount = authorizationLevel.getAmount();
                if (amount != null) {
                    if (lowerLevel != null && amount.compareTo(lowerLevel.getAmount()) < 0) {
                        return new ValidationError("authorizationLevel.error.lowerLevelAmount");
                    }
                    if (higherLevel != null && amount.compareTo(higherLevel.getAmount()) > 0) {
                        return new ValidationError("authorizationLevel.error.higherLevelAmount");
                    }
                }
            }
            return null;
        }
    }

    /**
     * Validates the authorization level before saving. The receiver can be only the authorizer of level 1. The broker can be the authorizer of level
     * 2 if the authorizer of level 1 is the receiver. The administrator can be authorizer of any levels.
     * @author Jefferson Magno
     */
    public class AuthorizerLevelValidation implements GeneralValidation {

        private static final long serialVersionUID = 2297783229528452496L;

        @Override
        public ValidationError validate(final Object object) {
            final AuthorizationLevel authorizationLevel = (AuthorizationLevel) object;
            if (authorizationLevel.isTransient()) {
                Integer level = authorizationLevel.getLevel();
                if (level == null) {
                    level = getNewLevel(authorizationLevel);
                }
                final Authorizer authorizer = authorizationLevel.getAuthorizer();
                if (authorizer == AuthorizationLevel.Authorizer.RECEIVER && level > 1) {
                    return new ValidationError("authorizationLevel.error.receiverAuthorizerLevel");
                } else if (authorizer == AuthorizationLevel.Authorizer.BROKER) {
                    if (level == 2) {
                        final AuthorizationLevel lowerLevel = getLowerLevel(authorizationLevel);
                        if (lowerLevel.getAuthorizer() != AuthorizationLevel.Authorizer.RECEIVER) {
                            return new ValidationError("authorizationLevel.error.brokerLowerAuthorizerLevel");
                        }
                    }
                    if (level > 2) {
                        return new ValidationError("authorizationLevel.error.brokerAuthorizerLevel");
                    }
                }
            }
            return null;
        }
    }

    /**
     * Validates the authorization level before saving. It's not allowed to insert more authorization levels than the number specified in the constant
     * MAX_LEVELS of the class AuthorizationLevel
     * @author Jefferson Magno
     */
    public class MaxLevelValidation implements GeneralValidation {

        private static final long serialVersionUID = -8200684320071292074L;

        @Override
        public ValidationError validate(final Object object) {
            final AuthorizationLevel authorizationLevel = (AuthorizationLevel) object;
            Integer level = authorizationLevel.getLevel();
            if (level == null) {
                level = getNewLevel(authorizationLevel);
            }
            if (level > AuthorizationLevel.MAX_LEVELS) {
                return new ValidationError("authorizationLevel.error.maxLevel", AuthorizationLevel.MAX_LEVELS);
            }
            return null;
        }

    }

    private AuthorizationLevelDAO authorizationLevelDao;
    private FetchDAO              fetchDao;
    private FetchServiceLocal     fetchService;
    private TransferTypeDAO       transferTypeDao;

    public Validator getValidator() {
        final Validator validator = new Validator("authorizationLevel");
        validator.property("transferType").required();
        validator.property("amount").required();
        validator.property("authorizer").required();
        validator.general(new MaxLevelValidation());
        validator.general(new AmountValidation());
        validator.general(new AuthorizerLevelValidation());
        validator.general(new AdminGroupRequiredValidation());
        return validator;
    }

    public AuthorizationLevel load(final Long id, final Relationship... fetch) {
        return authorizationLevelDao.load(id, fetch);
    }

    @Override
    public int remove(final Long... ids) {
        // It's assumed that all authorization levels that are being removed belongs to the same transfer type
        Long transferTypeId = null;
        for (final Long id : ids) {
            final AuthorizationLevel authorizationLevel = load(id, AuthorizationLevel.Relationships.TRANSFER_TYPE);
            transferTypeId = authorizationLevel.getTransferType().getId();
            break;
        }
        final int removedAuthorizationLevels = authorizationLevelDao.delete(ids);
        updateRemainingLevels(transferTypeId);
        return removedAuthorizationLevels;
    }

    @Override
    public AuthorizationLevel save(final AuthorizationLevel authorizationLevel) {
        validate(authorizationLevel);
        if (authorizationLevel.isTransient()) {
            final Integer newLevel = getNewLevel(authorizationLevel);
            authorizationLevel.setLevel(newLevel);
            return authorizationLevelDao.insert(authorizationLevel);
        }
        return authorizationLevelDao.update(authorizationLevel);
    }

    public void setAuthorizationLevelDao(final AuthorizationLevelDAO authorizationLevelDao) {
        this.authorizationLevelDao = authorizationLevelDao;
    }

    public void setFetchDao(final FetchDAO fetchDao) {
        this.fetchDao = fetchDao;
    }

    public void setFetchServiceLocal(final FetchServiceLocal fetchService) {
        this.fetchService = fetchService;
    }

    public void setTransferTypeDao(final TransferTypeDAO transferTypeDao) {
        this.transferTypeDao = transferTypeDao;
    }

    @Override
    public void validate(final AuthorizationLevel authorizationLevel) throws ValidationException {
        if (authorizationLevel.isPersistent()) {
            // Authorizer is not an updatable field, so retrieve the saved value from the database
            final AuthorizationLevel savedAuthorizationLevel = authorizationLevelDao.load(authorizationLevel.getId());
            authorizationLevel.setAuthorizer(savedAuthorizationLevel.getAuthorizer());
        }
        final TransferType transferType = fetchDao.fetch(authorizationLevel.getTransferType(), TransferType.Relationships.AUTHORIZATION_LEVELS);
        final Collection<AuthorizationLevel> authorizationLevels = fetchService.fetch(transferType.getAuthorizationLevels(), RelationshipHelper.nested(AuthorizationLevel.Relationships.TRANSFER_TYPE, AuthorizationLevel.Relationships.ADMIN_GROUPS));
        transferType.setAuthorizationLevels(authorizationLevels);
        authorizationLevel.setTransferType(transferType);
        getValidator().validate(authorizationLevel);
    }

    /*
     * Returns the higher authorization level of the transfer type or null if there isn't a higher level
     */
    private AuthorizationLevel getHigherLevel(final AuthorizationLevel authorizationLevel) {
        final TransferType transferType = authorizationLevel.getTransferType();
        final LinkedList<AuthorizationLevel> authorizationLevels = new LinkedList<AuthorizationLevel>(transferType.getAuthorizationLevels());
        if (CollectionUtils.isEmpty(authorizationLevels)) {
            return null;
        }
        final Integer currentLevel = authorizationLevel.getLevel();
        if (currentLevel == null) {
            // We are inserting a new authorization level, so there isn't a higher level
            return null;
        } else {
            // We are updating a saved authorization level
            if (currentLevel == authorizationLevels.size() || authorizationLevels.size() == 1) {
                // This level is already the highest or the only one
                return null;
            }
            // List indexes start with 0
            return authorizationLevels.get(currentLevel);
        }
    }

    /*
     * Returns the lower authorization level of the transfer type or null if there isn't a lower level
     */
    private AuthorizationLevel getLowerLevel(final AuthorizationLevel authorizationLevel) {
        final TransferType transferType = authorizationLevel.getTransferType();
        final LinkedList<AuthorizationLevel> authorizationLevels = new LinkedList<AuthorizationLevel>(transferType.getAuthorizationLevels());
        if (CollectionUtils.isEmpty(authorizationLevels)) {
            return null;
        }
        final Integer currentLevel = authorizationLevel.getLevel();
        if (currentLevel == null) {
            // We are inserting a new authorization level, the lower level is the highest level saved
            return authorizationLevels.getLast();
        } else {
            // We are updating a saved authorization level
            if (currentLevel == 1 || authorizationLevels.size() == 1) {
                return null;
            }
            // List indexes start with 0
            return authorizationLevels.get(currentLevel - 2);
        }
    }

    /*
     * Calculates the level of a new authorization level based on the highest saved authorization level
     */
    private Integer getNewLevel(final AuthorizationLevel authorizationLevel) {
        final TransferType transferType = authorizationLevel.getTransferType();
        final LinkedList<AuthorizationLevel> authorizationLevels = new LinkedList<AuthorizationLevel>(transferType.getAuthorizationLevels());
        if (CollectionUtils.isEmpty(authorizationLevels)) {
            return 1;
        }
        return authorizationLevels.size() + 1;
    }

    /*
     * When authorization levels are removed, this method updates the level of the remaining authorization levels
     */
    private void updateRemainingLevels(final Long transferTypeId) {
        final TransferType transferType = transferTypeDao.load(transferTypeId, TransferType.Relationships.AUTHORIZATION_LEVELS);
        final Collection<AuthorizationLevel> authorizationLevels = transferType.getAuthorizationLevels();
        if (!CollectionUtils.isEmpty(authorizationLevels)) {
            int currentLevel = 1;
            for (final AuthorizationLevel authorizationLevel : authorizationLevels) {
                authorizationLevel.setLevel(currentLevel);
                authorizationLevelDao.update(authorizationLevel);
                currentLevel++;
            }
        }
    }

}
