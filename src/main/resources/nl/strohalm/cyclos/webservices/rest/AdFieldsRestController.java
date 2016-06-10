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

import nl.strohalm.cyclos.entities.customization.fields.AdCustomField;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.services.customization.AdCustomFieldService;
import nl.strohalm.cyclos.utils.EntityHelper;
import nl.strohalm.cyclos.webservices.model.FieldVO;
import nl.strohalm.cyclos.webservices.model.PossibleValueVO;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller which handles /adFields paths
 * 
 * @author luis
 */
@Controller
public class AdFieldsRestController extends BaseFieldsRestController<AdCustomField> {

    private AdCustomFieldService adCustomFieldService;

    /**
     * Lists all ad custom fields
     */
    @RequestMapping(value = "adFields", method = RequestMethod.GET)
    @ResponseBody
    public List<FieldVO> list() {
        List<AdCustomField> fields = adCustomFieldService.list();
        ArrayList<Long> customFieldIds = new ArrayList<Long>(EntityHelper.toIdsAsList(fields));
        return adCustomFieldService.getFieldVOs(customFieldIds);
    }

    /**
     * Lists the custom fields which can be used as search filters
     */
    @RequestMapping(value = "adFields/forSearch", method = RequestMethod.GET)
    @ResponseBody
    public List<FieldVO> listForList() {
        List<AdCustomField> fields = adCustomFieldService.list();
        fields = customFieldHelper.onlyForAdsSearch(fields);
        ArrayList<Long> customFieldIds = new ArrayList<Long>(EntityHelper.toIdsAsList(fields));
        return adCustomFieldService.getFieldVOs(customFieldIds);
    }

    /**
     * Returns the possible values of a given custom field
     */
    @RequestMapping(value = "adFields/{id}/possibleValues", method = RequestMethod.GET)
    @ResponseBody
    public List<PossibleValueVO> listPossibleValues(@PathVariable final Long id) {
        return adCustomFieldService.getPossibleValueVOs(id, null);
    }

    /**
     * Returns the possible values of a given custom field by internal name
     */
    @RequestMapping(value = "adFields/name/{name}/possibleValues", method = RequestMethod.GET)
    @ResponseBody
    public List<PossibleValueVO> listPossibleValuesByInternalName(@PathVariable final String name) {
        AdCustomField field = load(name);
        return adCustomFieldService.getPossibleValueVOs(field.getId(), null);
    }

    /**
     * Returns the possible values of a given custom field and parent value id
     */
    @RequestMapping(value = "adFields/name/{name}/possibleValues/{parentValueId}", method = RequestMethod.GET)
    @ResponseBody
    public List<PossibleValueVO> listPossibleValuesByInternalNameAndParent(@PathVariable final String name, @PathVariable final Long parentValueId) {
        AdCustomField field = load(name);
        return adCustomFieldService.getPossibleValueVOs(field.getId(), parentValueId);
    }

    /**
     * Returns the possible values of a given custom field by internal name and parent value id
     */
    @RequestMapping(value = "adFields/{fieldId}/possibleValues/{parentValueId}", method = RequestMethod.GET)
    @ResponseBody
    public List<PossibleValueVO> listPossibleValuesByParent(@PathVariable final Long fieldId, @PathVariable final Long parentValueId) {
        return adCustomFieldService.getPossibleValueVOs(fieldId, parentValueId);
    }

    /**
     * Returns the details about a custom field
     */
    @RequestMapping(value = "adFields/{id}", method = RequestMethod.GET)
    @ResponseBody
    public FieldVO loadById(@PathVariable final Long id) {
        return adCustomFieldService.getFieldVO(id);
    }

    /**
     * Returns the details about a custom field
     */
    @RequestMapping(value = "adFields/name/{name}", method = RequestMethod.GET)
    @ResponseBody
    public FieldVO loadByInternalName(@PathVariable final String name) {
        AdCustomField ad = load(name);
        return adCustomFieldService.getFieldVO(ad.getId());
    }

    public void setAdCustomFieldService(final AdCustomFieldService adCustomFieldService) {
        this.adCustomFieldService = adCustomFieldService;
    }

    private AdCustomField load(final String name) {
        try {
            AdCustomField field = customFieldHelper.findByInternalName(adCustomFieldService.list(), name);
            if (field == null) {
                throw new Exception();
            }
            return field;
        } catch (Exception e) {
            throw new EntityNotFoundException(AdCustomField.class);
        }
    }

}
