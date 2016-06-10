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
package nl.strohalm.cyclos.dao.ads.imports;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import nl.strohalm.cyclos.dao.BaseDAOImpl;
import nl.strohalm.cyclos.entities.ads.AdCategory;
import nl.strohalm.cyclos.entities.ads.imports.AdImport;
import nl.strohalm.cyclos.entities.ads.imports.ImportedAdCategory;
import nl.strohalm.cyclos.utils.RelationshipHelper;

public class ImportedAdCategoryDAOImpl extends BaseDAOImpl<ImportedAdCategory> implements ImportedAdCategoryDAO {

    public ImportedAdCategoryDAOImpl() {
        super(ImportedAdCategory.class);
    }

    public List<ImportedAdCategory> getLeafCategories(final AdImport adImport) {
        final String hql = "select c from " + getEntityType().getName() + " c where c.adImport.id = :id and not exists (select c1.id from " + getEntityType().getName() + " c1 where c1.importedParent = c)";
        final List<ImportedAdCategory> list = list(hql, adImport);
        final List<ImportedAdCategory> categories = new ArrayList<ImportedAdCategory>(list.size());
        for (final ImportedAdCategory category : list) {
            categories.add(fetchParent(category));
        }
        Collections.sort(categories, new Comparator<ImportedAdCategory>() {
            public int compare(final ImportedAdCategory cat1, final ImportedAdCategory cat2) {
                return cat1.getFullName().compareTo(cat2.getFullName());
            }
        });
        return categories;
    }

    /**
     * Recursively fetch the parent categories
     */
    private ImportedAdCategory fetchParent(ImportedAdCategory category) {
        if (category.getExistingParent() != null) {
            category = getFetchDao().fetch(category, ImportedAdCategory.Relationships.EXISTING_PARENT);
            category.setExistingParent(getFetchDao().fetch(category.getExistingParent(), RelationshipHelper.nested(AdCategory.MAX_LEVEL, AdCategory.Relationships.PARENT)));
        } else if (category.getImportedParent() != null) {
            category = getFetchDao().fetch(category, ImportedAdCategory.Relationships.IMPORTED_PARENT);
            category.setImportedParent(fetchParent(category.getImportedParent()));
        }
        return category;
    }

}
