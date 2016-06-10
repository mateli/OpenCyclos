/*
   This file is part of Cyclos.

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

import nl.strohalm.cyclos.entities.ads.Ad;
import nl.strohalm.cyclos.entities.ads.AdCategory;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.services.ads.AdCategoryService;
import nl.strohalm.cyclos.webservices.model.AdCategoryHierarchicalVO;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller which handles /adCategories paths
 * 
 * @author luis
 */
@Controller
public class AdCategoriesRestController extends BaseRestController {

    private AdCategoryService adCategoryService;

    /**
     * Lists the category hierarchy
     */
    @RequestMapping(value = "adCategories", method = RequestMethod.GET)
    @ResponseBody
    public List<AdCategoryHierarchicalVO> listChildren() {
        List<AdCategoryHierarchicalVO> vos = new ArrayList<AdCategoryHierarchicalVO>();
        for (AdCategory category : adCategoryService.listRoot()) {
            AdCategoryHierarchicalVO vo = adCategoryService.getHierarchicalVO(category);
            if (vo != null) {
                vos.add(vo);
            }
        }
        return vos;
    }

    /**
     * Loads an advertisement category by id
     */
    @RequestMapping(value = "adCategories/{id}", method = RequestMethod.GET)
    @ResponseBody
    public AdCategoryHierarchicalVO loadById(@PathVariable final Long id) {
        AdCategory category;
        try {
            category = adCategoryService.load(id);
        } catch (Exception e) {
            throw new EntityNotFoundException(Ad.class);
        }
        return adCategoryService.getHierarchicalVO(category);
    }

    public void setAdCategoryService(final AdCategoryService adCategoryService) {
        this.adCategoryService = adCategoryService;
    }

}
