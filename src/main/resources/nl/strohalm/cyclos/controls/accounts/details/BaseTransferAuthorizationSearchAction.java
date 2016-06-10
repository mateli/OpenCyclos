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
package nl.strohalm.cyclos.controls.accounts.details;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.BaseQueryAction;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.accounts.transactions.AuthorizationLevel.Authorizer;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferTypeQuery;
import nl.strohalm.cyclos.entities.groups.AdminGroup;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Administrator;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.Operator;
import nl.strohalm.cyclos.services.transactions.TransferAuthorizationService;
import nl.strohalm.cyclos.services.transfertypes.TransferTypeService;
import nl.strohalm.cyclos.utils.RelationshipHelper;

/**
 * Base action for searching transfer authorizations
 * @author luis
 */
public abstract class BaseTransferAuthorizationSearchAction extends BaseQueryAction {

    protected TransferAuthorizationService transferAuthorizationService;
    protected TransferTypeService          transferTypeService;

    public BaseTransferAuthorizationSearchAction() {
        super();
    }

    @Inject
    public void setTransferAuthorizationService(final TransferAuthorizationService transferAuthorizationService) {
        this.transferAuthorizationService = transferAuthorizationService;
    }

    @Inject
    public void setTransferTypeService(final TransferTypeService transferTypeService) {
        this.transferTypeService = transferTypeService;
    }

    protected List<TransferType> resolveTransferTypes(Element element) {
        element = elementService.load(element.getId(), Element.Relationships.GROUP);
        final TransferTypeQuery ttQuery = new TransferTypeQuery();
        ttQuery.setAuthorizable(true);
        Collection<Authorizer> authorizers;
        AdminGroup authorizerGroup = null;
        if (element instanceof Administrator) {
            authorizers = EnumSet.of(Authorizer.BROKER, Authorizer.ADMIN);
            authorizerGroup = (AdminGroup) element.getGroup();
        } else {
            Member member = null;
            if (element instanceof Operator) {
                final Operator operator = (Operator) element;
                member = elementService.load(operator.getMember().getId(), RelationshipHelper.nested(Element.Relationships.GROUP, MemberGroup.Relationships.ACCOUNT_SETTINGS));
            } else {
                member = elementService.load(((Member) element).getId(), RelationshipHelper.nested(Element.Relationships.GROUP, MemberGroup.Relationships.ACCOUNT_SETTINGS));
            }
            ttQuery.setFromOrToAccountTypes(new ArrayList<AccountType>(member.getMemberGroup().getAccountTypes()));

            authorizers = EnumSet.of(Authorizer.RECEIVER);
            if (member.getMemberGroup().isBroker()) {
                authorizers.add(Authorizer.BROKER);
            }
        }
        ttQuery.setAuthorizers(authorizers);
        ttQuery.setAuthorizerGroup(authorizerGroup);
        ttQuery.fetch(TransferType.Relationships.AUTHORIZATION_LEVELS);
        final List<TransferType> transferTypes = transferTypeService.search(ttQuery);
        return transferTypes;
    }

}
