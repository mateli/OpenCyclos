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
package nl.strohalm.cyclos.webservices.members;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import nl.strohalm.cyclos.entities.services.ServiceOperation;
import nl.strohalm.cyclos.webservices.Permission;
import nl.strohalm.cyclos.webservices.PrincipalParameters;
import nl.strohalm.cyclos.webservices.model.GroupVO;
import nl.strohalm.cyclos.webservices.model.MemberVO;

/**
 * Web service interface for members
 * @author luis
 */
@WebService
public interface MemberWebService {

    /**
     * Searches for members using a full-text search, according to the search parameters
     */
    @Permission(ServiceOperation.MEMBERS)
    @WebMethod
    MemberResultPage fullTextSearch(@WebParam(name = "params") FullTextMemberSearchParameters params);

    /**
     * Returns a list of the managed groups by the caller channel
     */
    @Permission(ServiceOperation.MANAGE_MEMBERS)
    @WebMethod
    List<GroupVO> listManagedGroups();

    /**
     * Loads a member using it's identifier
     */
    @Permission(ServiceOperation.MEMBERS)
    @WebMethod
    MemberVO load(@WebParam(name = "id") long id);

    /**
     * Loads a member using the given principal
     */
    @Permission(ServiceOperation.MEMBERS)
    @WebMethod
    MemberVO loadByPrincipal(@WebParam(name = "params") PrincipalParameters params);

    /**
     * Loads a member using it's username
     */
    @Permission(ServiceOperation.MEMBERS)
    @WebMethod
    MemberVO loadByUsername(@WebParam(name = "username") String username);

    /**
     * Register a new member. The groupId is optional, and only needed when there are multiple groups this channel manages
     */
    @Permission(ServiceOperation.MANAGE_MEMBERS)
    @WebMethod
    MemberRegistrationResult registerMember(@WebParam(name = "params") RegisterMemberParameters params);

    /**
     * Searches for members, according to the search parameters
     */
    @Permission(ServiceOperation.MEMBERS)
    @WebMethod
    MemberResultPage search(@WebParam(name = "params") MemberSearchParameters params);

    /**
     * Updates a member profile. Empty values for name or e-mail means those fields won't be updated. Custom fields which are passed will be updated.
     * Those not included on the array won't. As key to find the member, it's possible to either pass the id or a principal (like username), together
     * with the principal type (optional, assuming the channel's default) when there are more than one options
     */
    @Permission(ServiceOperation.MANAGE_MEMBERS)
    @WebMethod
    void updateMember(@WebParam(name = "params") UpdateMemberParameters params);

}
