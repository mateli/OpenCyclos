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
package nl.strohalm.cyclos.webservices.fields;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import nl.strohalm.cyclos.entities.services.ServiceOperation;
import nl.strohalm.cyclos.webservices.Permission;
import nl.strohalm.cyclos.webservices.model.FieldVO;
import nl.strohalm.cyclos.webservices.model.PossibleValueVO;

/**
 * Web service interface for custom fields
 * @author luis
 */
@WebService
public interface FieldWebService {

    /**
     * Returns an array of ad fields that can be used on ad search
     */
    @Permission(ServiceOperation.ADVERTISEMENTS)
    @WebMethod
    public List<FieldVO> adFieldsForAdSearch();

    /**
     * Returns an array of all advertisement fields
     */
    @Permission(ServiceOperation.ADVERTISEMENTS)
    @WebMethod
    public List<FieldVO> allAdFields();

    /**
     * Returns an array of all member fields
     */
    @Permission({ ServiceOperation.ADVERTISEMENTS, ServiceOperation.MEMBERS })
    @WebMethod
    public List<FieldVO> allMemberFields();

    /**
     * Returns an array of member fields that can be used on ad search
     */
    @Permission(ServiceOperation.ADVERTISEMENTS)
    @WebMethod
    public List<FieldVO> memberFieldsForAdSearch();

    /**
     * Returns an array of member fields for the given group. The group is optional, assuming the first managed group
     */
    @Permission(ServiceOperation.MANAGE_MEMBERS)
    @WebMethod
    public List<FieldVO> memberFields(Long groupId);

    /**
     * Returns an array of member fields that can be used on member search
     */
    @Permission(ServiceOperation.MEMBERS)
    @WebMethod
    public List<FieldVO> memberFieldsForMemberSearch();

    /**
     * Returns an array of payment fields for the given payment type
     */
    @Permission({ ServiceOperation.ACCOUNT_DETAILS, ServiceOperation.DO_PAYMENT, ServiceOperation.RECEIVE_PAYMENT })
    @WebMethod
    public List<FieldVO> paymentFields(@WebParam(name = "transferTypeId") Long transferTypeId);

    /**
     * Returns an array of possible values for a given ad field, or null if the field is not enumerated
     */
    @Permission(ServiceOperation.ADVERTISEMENTS)
    @WebMethod
    public List<PossibleValueVO> possibleValuesForAdField(@WebParam(name = "name") String name);

    /**
     * Returns an array of possible values for a given member field, given a parent value, or null if the field is not enumerated
     */
    @Permission(ServiceOperation.ADVERTISEMENTS)
    @WebMethod
    public List<PossibleValueVO> possibleValuesForAdFieldGivenParent(@WebParam(name = "name") String name, @WebParam(name = "parent") Long parentValueId);

    /**
     * Returns an array of possible values for a given member field, or null if the field is not enumerated
     */
    @Permission({ ServiceOperation.ADVERTISEMENTS, ServiceOperation.MEMBERS })
    @WebMethod
    public List<PossibleValueVO> possibleValuesForMemberField(@WebParam(name = "name") String name);

    /**
     * Returns an array of possible values for a given member field, given a parent value, or null if the field is not enumerated
     */
    @Permission({ ServiceOperation.ADVERTISEMENTS, ServiceOperation.MEMBERS })
    @WebMethod
    public List<PossibleValueVO> possibleValuesForMemberFieldGivenParent(@WebParam(name = "name") String name, @WebParam(name = "parent") Long parentValueId);

    /**
     * Returns an array of possible values for a given payment field, or null if the field is not enumerated
     */
    @Permission({ ServiceOperation.ACCOUNT_DETAILS, ServiceOperation.DO_PAYMENT, ServiceOperation.RECEIVE_PAYMENT })
    @WebMethod
    public List<PossibleValueVO> possibleValuesForPaymentFields(@WebParam(name = "transferTypeId") Long transferTypeId, @WebParam(name = "name") String name);

}
