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
package nl.strohalm.cyclos.webservices.ads;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import nl.strohalm.cyclos.entities.services.ServiceOperation;
import nl.strohalm.cyclos.webservices.Permission;
import nl.strohalm.cyclos.webservices.model.AdCategoryVO;
import nl.strohalm.cyclos.webservices.model.AdVO;
import nl.strohalm.cyclos.webservices.model.DetailedAdCategoryVO;

/**
 * Web service interface for ads
 * @author luis
 */
@WebService
public interface AdWebService {

    /**
     * Searches for adversisements using a full text search, according to the search parameters
     */
    @Permission(ServiceOperation.ADVERTISEMENTS)
    @WebMethod
    public AdResultPage fullTextSearch(@WebParam(name = "params") FullTextAdSearchParameters params);

    /**
     * Lists all categories
     */
    @Permission(ServiceOperation.ADVERTISEMENTS)
    @WebMethod
    public AdCategoryVO[] listCategories();

    /**
     * Lists the detailed category tree
     */
    @Permission(ServiceOperation.ADVERTISEMENTS)
    @WebMethod
    public DetailedAdCategoryVO[] listCategoryTree();

    /**
     * Loads a single advertisement using it's identifier
     */
    @Permission(ServiceOperation.ADVERTISEMENTS)
    @WebMethod
    public AdVO load(@WebParam(name = "id") long id);

    /**
     * Loads a category by id
     */
    @Permission(ServiceOperation.ADVERTISEMENTS)
    @WebMethod
    public AdCategoryVO loadCategory(@WebParam(name = "id") long id);

    /**
     * Searches for adversisements, according to the search parameters
     */
    @Permission(ServiceOperation.ADVERTISEMENTS)
    @WebMethod
    public AdResultPage search(@WebParam(name = "params") AdSearchParameters params);
}
