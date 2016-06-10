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
package nl.strohalm.cyclos.webservices.rest;

import java.util.ArrayList;
import java.util.List;

import nl.strohalm.cyclos.entities.customization.fields.MemberCustomField;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.services.customization.MemberCustomFieldService;
import nl.strohalm.cyclos.services.elements.ElementService;
import nl.strohalm.cyclos.services.elements.MemberService;
import nl.strohalm.cyclos.utils.EntityHelper;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.webservices.model.FieldVO;
import nl.strohalm.cyclos.webservices.model.PossibleValueVO;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller which handles /memberFields paths
 * 
 * @author luis
 */
@Controller
public class MemberFieldsRestController extends BaseFieldsRestController<MemberCustomField> {

    private ElementService           elementService;
    private MemberCustomFieldService memberCustomFieldService;
    private MemberService            memberService;

    /**
     * Lists the member custom fields for viewing the given user's profile, by id
     */
    @RequestMapping(value = "memberFields/byMemberId/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public List<FieldVO> listByMemberId(@PathVariable final Long userId) {
        Member member;
        try {
            member = elementService.load(userId);
        } catch (Exception e) {
            throw new EntityNotFoundException(Member.class);
        }
        return list(member);
    }

    /**
     * Lists the member custom fields for viewing the given user's profile, by id
     */
    @RequestMapping(value = "memberFields/byMemberPrincipal/{principal}", method = RequestMethod.GET)
    @ResponseBody
    public List<FieldVO> listByMemberPrincipal(@PathVariable final String principal) {
        Member member;
        try {
            member = memberService.loadByIdOrPrincipal(null, null, principal);
        } catch (Exception e) {
            throw new EntityNotFoundException(Member.class);
        }
        return list(member);
    }

    /**
     * Lists the member custom fields for searching advertisements
     */
    @RequestMapping(value = "memberFields/forAdsSearch", method = RequestMethod.GET)
    @ResponseBody
    public List<FieldVO> listForAdsSearch() {
        List<MemberCustomField> fields = memberCustomFieldService.list();
        fields = customFieldHelper.onlyForAdSearch(fields);
        ArrayList<Long> customFieldIds = new ArrayList<Long>(EntityHelper.toIdsAsList(fields));
        return memberCustomFieldService.getFieldVOs(customFieldIds);
    }

    /**
     * Lists the member custom fields for searching users
     */
    @RequestMapping(value = "memberFields/forSearch", method = RequestMethod.GET)
    @ResponseBody
    public List<FieldVO> listForUserSearch() {
        List<MemberCustomField> fields = memberCustomFieldService.list();
        fields = customFieldHelper.onlyForMemberSearch(fields);
        ArrayList<Long> customFieldIds = new ArrayList<Long>(EntityHelper.toIdsAsList(fields));
        return memberCustomFieldService.getFieldVOs(customFieldIds);
    }

    /**
     * Lists the member custom fields for the authenticated user's group
     */
    @RequestMapping(value = "memberFields/mine", method = RequestMethod.GET)
    @ResponseBody
    public List<FieldVO> listMine() {
        List<MemberCustomField> fields = memberCustomFieldService.list();
        fields = customFieldHelper.onlyOwnedFields(fields, LoggedUser.member().getMemberGroup());
        ArrayList<Long> customFieldIds = new ArrayList<Long>(EntityHelper.toIdsAsList(fields));
        return memberCustomFieldService.getFieldVOs(customFieldIds);
    }

    /**
     * Returns the possible values of a given custom field
     */
    @RequestMapping(value = "memberFields/{id}/possibleValues", method = RequestMethod.GET)
    @ResponseBody
    public List<PossibleValueVO> listPossibleValues(@PathVariable final Long id) {
        return memberCustomFieldService.getPossibleValueVOs(id, null);
    }

    /**
     * Returns the details about a custom field
     */
    @RequestMapping(value = "memberFields/name/{name}/possibleValues", method = RequestMethod.GET)
    @ResponseBody
    public List<PossibleValueVO> listPossibleValuesByInternalName(@PathVariable final String name) {
        MemberCustomField field = load(name);
        return memberCustomFieldService.getPossibleValueVOs(field.getId(), null);
    }

    /**
     * Returns the details about a custom field
     */
    @RequestMapping(value = "memberFields/name/{name}/possibleValues/{parentValueId}", method = RequestMethod.GET)
    @ResponseBody
    public List<PossibleValueVO> listPossibleValuesByInternalNameAndParent(@PathVariable final String name, @PathVariable final Long parentValueId) {
        MemberCustomField field = load(name);
        return memberCustomFieldService.getPossibleValueVOs(field.getId(), parentValueId);
    }

    /**
     * Returns the details about a custom field
     */
    @RequestMapping(value = "memberFields/{fieldId}/possibleValues/{parentValueId}", method = RequestMethod.GET)
    @ResponseBody
    public List<PossibleValueVO> listPossibleValuesByParent(@PathVariable final Long fieldId, @PathVariable final Long parentValueId) {
        return memberCustomFieldService.getPossibleValueVOs(fieldId, parentValueId);
    }

    /**
     * Returns the details about a custom field
     */
    @RequestMapping(value = "memberFields/{id}", method = RequestMethod.GET)
    @ResponseBody
    public FieldVO loadById(@PathVariable final Long id) {
        return memberCustomFieldService.getFieldVO(id);
    }

    /**
     * Returns the details about a custom field
     */
    @RequestMapping(value = "memberFields/name/{name}", method = RequestMethod.GET)
    @ResponseBody
    public FieldVO loadByInternalName(@PathVariable final String name) {
        MemberCustomField memberField = load(name);
        return memberCustomFieldService.getFieldVO(memberField.getId());
    }

    public void setElementService(final ElementService elementService) {
        this.elementService = elementService;
    }

    public void setMemberCustomFieldService(final MemberCustomFieldService memberCustomFieldService) {
        this.memberCustomFieldService = memberCustomFieldService;
    }

    public void setMemberService(final MemberService memberService) {
        this.memberService = memberService;
    }

    private List<FieldVO> list(final Member member) {
        List<MemberCustomField> fields = memberCustomFieldService.list();
        fields = customFieldHelper.onlyVisibleFields(fields, member.getMemberGroup());
        ArrayList<Long> customFieldIds = new ArrayList<Long>(EntityHelper.toIdsAsList(fields));
        return memberCustomFieldService.getFieldVOs(customFieldIds);
    }

    private MemberCustomField load(final String name) {
        try {
            MemberCustomField field = customFieldHelper.findByInternalName(memberCustomFieldService.list(), name);
            if (field == null) {
                throw new Exception();
            }
            return field;
        } catch (Exception e) {
            throw new EntityNotFoundException(MemberCustomField.class);
        }
    }

}
