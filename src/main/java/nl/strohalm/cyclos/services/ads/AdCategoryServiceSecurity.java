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
package nl.strohalm.cyclos.services.ads;

import java.util.Iterator;
import java.util.List;

import nl.strohalm.cyclos.access.AdminMemberPermission;
import nl.strohalm.cyclos.access.AdminSystemPermission;
import nl.strohalm.cyclos.access.BrokerPermission;
import nl.strohalm.cyclos.access.MemberPermission;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.ads.AdCategory;
import nl.strohalm.cyclos.entities.ads.AdCategoryQuery;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.BaseServiceSecurity;
import nl.strohalm.cyclos.utils.validation.ValidationException;
import nl.strohalm.cyclos.webservices.model.AdCategoryHierarchicalVO;

/**
 * Security implementation for {@link AdCategoryService}
 * 
 * @author Rinke
 */
public class AdCategoryServiceSecurity extends BaseServiceSecurity implements AdCategoryService {

    private AdCategoryServiceLocal adCategoryService;

    @Override
    public String exportToXml() {
        permissionService.permission().admin(AdminSystemPermission.AD_CATEGORIES_FILE).check();
        return adCategoryService.exportToXml();
    }

    @Override
    public AdCategoryHierarchicalVO getHierarchicalVO(final AdCategory category) {
        checkViewPermissions();
        AdCategoryHierarchicalVO hierarchicalVO = adCategoryService.getHierarchicalVO(category);
        filterViewables(hierarchicalVO);
        return hierarchicalVO;
    }

    @Override
    public void importFromXml(final String xml) {
        permissionService.permission().admin(AdminSystemPermission.AD_CATEGORIES_FILE).check();
        adCategoryService.importFromXml(xml);
    }

    @Override
    public List<AdCategory> listLeaf() {
        checkViewPermissions();
        return filterViewables(adCategoryService.listLeaf());
    }

    @Override
    public List<AdCategory> listRoot() {
        checkViewPermissions();
        return filterViewables(adCategoryService.listRoot());
    }

    @Override
    public AdCategory load(final Long id, final Relationship... fetch) throws EntityNotFoundException {
        checkViewPermissions();
        AdCategory result = adCategoryService.load(id, fetch);
        if (!isViewable(result)) {
            throw new PermissionDeniedException();
        }
        return result;
    }

    @Override
    public int remove(final Long... ids) {
        permissionService.permission().admin(AdminSystemPermission.AD_CATEGORIES_MANAGE).check();
        return adCategoryService.remove(ids);
    }

    @Override
    public AdCategory save(final AdCategory category) {
        checkManagePermissions();
        return adCategoryService.save(category);
    }

    @Override
    public List<AdCategory> search(final AdCategoryQuery query) {
        permissionService.permission().admin(AdminSystemPermission.AD_CATEGORIES_VIEW).check();
        return adCategoryService.search(query);
    }

    public void setAdCategoryServiceLocal(final AdCategoryServiceLocal adCategoryService) {
        this.adCategoryService = adCategoryService;
    }

    @Override
    public void setOrder(final Long[] fieldIds) {
        checkManagePermissions();
        adCategoryService.setOrder(fieldIds);
    }

    @Override
    public void validate(final AdCategory category) throws ValidationException {
        // no permissions on validation
        adCategoryService.validate(category);
    }

    /**
     * Checks if the user has permissions to manage ad categories
     */
    private void checkManagePermissions() {
        permissionService.permission().admin(AdminSystemPermission.AD_CATEGORIES_MANAGE).check();
    }

    /**
     * Checks if the user has permissions to view ad categories.
     */
    private void checkViewPermissions() {
        permissionService.permission()
                .admin(AdminSystemPermission.AD_CATEGORIES_VIEW, AdminMemberPermission.ADS_VIEW)
                .broker(BrokerPermission.ADS_VIEW)
                // I suppose I can assume that members having MemberPermission.PREFERENCES_MANAGE_AD_INTERESTS also have ADS_VIEW permission
                .member(MemberPermission.ADS_VIEW)
                .operator(MemberPermission.ADS_VIEW)
                .check();
    }

    private void filterViewables(final AdCategoryHierarchicalVO hierarchicalVO) {
        if (hierarchicalVO != null) {
            if (hierarchicalVO.getChildren() != null) {
                for (Iterator<AdCategoryHierarchicalVO> iterator = hierarchicalVO.getChildren().iterator(); iterator.hasNext();) {
                    AdCategoryHierarchicalVO cat = iterator.next();
                    if (!isViewable(cat)) {
                        iterator.remove();
                    } else {
                        filterViewables(cat);
                    }
                }
            }
        }
    }

    /**
     * filters out the AdCategories which are not viewable according to the isViewable method
     */
    private List<AdCategory> filterViewables(final List<AdCategory> list) {
        for (Iterator<AdCategory> iterator = list.iterator(); iterator.hasNext();) {
            AdCategory cat = iterator.next();
            if (!isViewable(cat)) {
                iterator.remove();
            }
        }
        return list;
    }

    /**
     * checks whether the AdCategory is visible for the logged user.
     * @param adCat
     * @return true if user has AdminSystemPermission.AD_CATEGORIES_VIEW permission; in all other cases true if the AdCategory is enabled.
     */
    private boolean isViewable(final AdCategory adCat) {
        if (permissionService.hasPermission(AdminSystemPermission.AD_CATEGORIES_VIEW)) {
            return true;
        }
        // Otherwise, cat must not be disabled
        return adCat.isEnabled();
    }

    private boolean isViewable(final AdCategoryHierarchicalVO cat) {
        if (permissionService.hasPermission(AdminSystemPermission.AD_CATEGORIES_VIEW)) {
            return true;
        }
        // Otherwise, cat must not be disabled
        AdCategory adCat = load(cat.getId());
        return adCat.isEnabled();
    }

}
