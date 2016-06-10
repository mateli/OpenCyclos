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

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.entities.ads.Ad;
import nl.strohalm.cyclos.entities.ads.AdQuery;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.services.ads.AdService;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.utils.conversion.Transformer;
import nl.strohalm.cyclos.utils.query.PageParameters;
import nl.strohalm.cyclos.utils.query.QueryParameters.ResultType;
import nl.strohalm.cyclos.webservices.ads.AbstractAdSearchParameters.AdVOTradeType;
import nl.strohalm.cyclos.webservices.ads.AdResultPage;
import nl.strohalm.cyclos.webservices.ads.FullTextAdSearchParameters;
import nl.strohalm.cyclos.webservices.model.AdVO;
import nl.strohalm.cyclos.webservices.utils.QueryHelper;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller which handles /ads paths
 * 
 * @author luis
 */
@Controller
public class AdsRestController extends BaseRestController {
    private static final String        MEMBER_CUSTOM_VALUE = "memberCustomValue.";
    private static final String        AD_CUSTOM_VALUE     = "adCustomValue.";

    private QueryHelper                queryHelper;
    private AdService                  adService;
    private MemberFieldsRestController memberFieldsRestController;
    private AdFieldsRestController     adFieldsRestController;

    /**
     * Loads an advertisement by id
     */
    @RequestMapping(value = "ads/{id}", method = RequestMethod.GET)
    @ResponseBody
    public AdVO loadById(@PathVariable final Long id) {
        Ad ad;
        try {
            ad = adService.load(id);
        } catch (Exception e) {
            throw new EntityNotFoundException(Ad.class);
        }
        return adService.getAdVO(AdVO.class, ad, true, true, false);
    }

    /**
     * Searches for advertisements
     */
    @RequestMapping(value = "ads", method = RequestMethod.GET)
    @ResponseBody
    public AdResultPage search(FullTextAdSearchParameters params, @RequestParam(required = false) final String memberPrincipal, @RequestParam(defaultValue = "false") final boolean searching, final HttpServletRequest request) {
        if (params == null) {
            params = new FullTextAdSearchParameters();
        }
        // We use the searching flag instead of enum
        params.setTradeType(searching ? AdVOTradeType.SEARCH : AdVOTradeType.OFFER);

        // It shouldn't be possible to filter by groups
        params.setMemberGroupIds(null);

        params.setMemberFields(memberFieldsRestController.requestParametersToFieldValues(request, MEMBER_CUSTOM_VALUE));
        params.setAdFields(adFieldsRestController.requestParametersToFieldValues(request, AD_CUSTOM_VALUE));

        return adService.getAdResultPage(params, memberPrincipal);
    }

    /**
     * Returns authenticated user's ads
     */
    @RequestMapping(value = "ads/mine", method = RequestMethod.GET)
    @ResponseBody
    public AdResultPage searchMine(@RequestParam(defaultValue = "10") final int pageSize, @RequestParam(defaultValue = "0") final int currentPage) {
        AdQuery query = new AdQuery();
        query.setResultType(ResultType.PAGE);
        query.setPageParameters(new PageParameters(pageSize, currentPage));
        query.setOwner(LoggedUser.member());
        List<Ad> ads = adService.search(query);
        return queryHelper.toResultPage(AdResultPage.class, ads, new Transformer<Ad, AdVO>() {
            @Override
            public AdVO transform(final Ad ad) {
                return adService.getMyVO(ad);
            }
        });
    }

    public void setAdFieldsRestController(final AdFieldsRestController adFieldsRestController) {
        this.adFieldsRestController = adFieldsRestController;
    }

    public void setAdService(final AdService adService) {
        this.adService = adService;
    }

    public void setMemberFieldsRestController(final MemberFieldsRestController memberFieldsRestController) {
        this.memberFieldsRestController = memberFieldsRestController;
    }

    public void setQueryHelper(final QueryHelper queryHelper) {
        this.queryHelper = queryHelper;
    }
}
