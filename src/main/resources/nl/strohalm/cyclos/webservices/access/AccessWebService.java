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
package nl.strohalm.cyclos.webservices.access;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import nl.strohalm.cyclos.entities.services.ServiceOperation;
import nl.strohalm.cyclos.webservices.Permission;
import nl.strohalm.cyclos.webservices.PrincipalParameters;

/**
 * Web service interface for access
 * @author luis
 */
@WebService
public interface AccessWebService {

    /**
     * Changes the credentials associated with the service's channel
     */
    @Permission(ServiceOperation.MANAGE_MEMBERS)
    @WebMethod
    public ChangeCredentialsStatus changeCredentials(@WebParam(name = "params") ChangeCredentialsParameters params);

    /**
     * Checks whether the given credentials are valid for the channel related to the web service client
     */
    @Permission(ServiceOperation.ACCESS)
    @WebMethod
    public CredentialsStatus checkCredentials(@WebParam(name = "params") CheckCredentialsParameters params);

    /**
     * Check whether the given member has access to the current service's channel
     */
    @Permission(ServiceOperation.ACCESS)
    @WebMethod
    public boolean isChannelEnabledForMember(@WebParam(name = "params") PrincipalParameters params);

}
