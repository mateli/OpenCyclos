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

import java.util.List;

import javax.jws.WebService;

import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.ads.Ad;
import nl.strohalm.cyclos.entities.ads.AdCategory;
import nl.strohalm.cyclos.entities.customization.fields.AdCustomField;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomField;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.ads.AdCategoryServiceLocal;
import nl.strohalm.cyclos.services.ads.AdServiceLocal;
import nl.strohalm.cyclos.services.customization.AdCustomFieldServiceLocal;
import nl.strohalm.cyclos.services.customization.MemberCustomFieldServiceLocal;
import nl.strohalm.cyclos.utils.CustomFieldHelper;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.webservices.model.AdCategoryVO;
import nl.strohalm.cyclos.webservices.model.AdVO;
import nl.strohalm.cyclos.webservices.model.DetailedAdCategoryVO;
import nl.strohalm.cyclos.webservices.utils.AdHelper;

/**
 * Web service implementation
 * @author luis
 */
@WebService(name = "ads", serviceName = "ads")
public class AdWebServiceImpl implements AdWebService {

    private static final Relationship[]   FETCH = { RelationshipHelper.nested(Ad.Relationships.OWNER, Element.Relationships.GROUP), Ad.Relationships.CATEGORY, Ad.Relationships.CUSTOM_VALUES, Ad.Relationships.IMAGES };
    private AdServiceLocal                adServiceLocal;
    private AdHelper                      adHelper;
    private CustomFieldHelper             customFieldHelper;
    private AdCategoryServiceLocal        adCategoryServiceLocal;
    private AdCustomFieldServiceLocal     adCustomFieldServiceLocal;
    private MemberCustomFieldServiceLocal memberCustomFieldServiceLocal;

    @Override
    public AdResultPage fullTextSearch(final FullTextAdSearchParameters params) {
        if (params == null) {
            return null;
        }
        final List<Ad> ads = adServiceLocal.fullTextSearch(adHelper.toFullTextQuery(params));
        return adHelper.toResultPage(params, ads);
    }

    @Override
    public AdCategoryVO[] listCategories() {
        final List<AdCategory> list = adCategoryServiceLocal.listLeaf();
        final AdCategoryVO[] vos = new AdCategoryVO[list.size()];
        for (int i = 0; i < vos.length; i++) {
            vos[i] = adHelper.toVO(list.get(i));
        }
        return vos;
    }

    @Override
    public DetailedAdCategoryVO[] listCategoryTree() {
        final List<AdCategory> rootList = adCategoryServiceLocal.listRoot();
        final DetailedAdCategoryVO[] vos = new DetailedAdCategoryVO[rootList.size()];
        for (int i = 0; i < rootList.size(); i++) {
            final AdCategory adCategory = rootList.get(i);
            vos[i] = adHelper.toDetailedVO(adCategory);
        }
        return vos;
    }

    @Override
    public AdVO load(final long id) {
        try {
            final Ad ad = adServiceLocal.load(id, FETCH);
            if (!ad.getStatus().isActive() || !ad.isExternalPublication()) {
                // Make sure only active ads are returned
                throw new EntityNotFoundException();
            }
            List<AdCustomField> adFields = adCustomFieldServiceLocal.list();
            List<MemberCustomField> memberFields = customFieldHelper.onlyForGroup(memberCustomFieldServiceLocal.list(), ad.getOwner().getMemberGroup());
            return adHelper.toVO(ad, adFields, memberFields);
        } catch (final PermissionDeniedException e) {
            return null;
        } catch (final EntityNotFoundException e) {
            return null;
        }
    }

    @Override
    public AdCategoryVO loadCategory(final long id) {
        try {
            return adHelper.toVO(adCategoryServiceLocal.load(id));
        } catch (final EntityNotFoundException e) {
            return null;
        }
    }

    @Override
    public AdResultPage search(final AdSearchParameters params) {
        if (params == null) {
            return null;
        }
        final List<Ad> ads = adServiceLocal.search(adHelper.toQuery(params));
        return adHelper.toResultPage(params, ads);
    }

    public void setAdCategoryServiceLocal(final AdCategoryServiceLocal adCategoryServiceLocal) {
        this.adCategoryServiceLocal = adCategoryServiceLocal;
    }

    public void setAdCustomFieldServiceLocal(final AdCustomFieldServiceLocal adCustomFieldServiceLocal) {
        this.adCustomFieldServiceLocal = adCustomFieldServiceLocal;
    }

    public void setAdHelper(final AdHelper adHelper) {
        this.adHelper = adHelper;
    }

    public void setAdServiceLocal(final AdServiceLocal adService) {
        adServiceLocal = adService;
    }

    public void setCustomFieldHelper(final CustomFieldHelper customFieldHelper) {
        this.customFieldHelper = customFieldHelper;
    }

    public void setMemberCustomFieldServiceLocal(final MemberCustomFieldServiceLocal memberCustomFieldServiceLocal) {
        this.memberCustomFieldServiceLocal = memberCustomFieldServiceLocal;
    }

}
