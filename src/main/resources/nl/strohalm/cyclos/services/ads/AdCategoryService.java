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

import java.util.List;

import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.ads.AdCategory;
import nl.strohalm.cyclos.entities.ads.AdCategoryQuery;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.services.Service;
import nl.strohalm.cyclos.utils.validation.ValidationException;
import nl.strohalm.cyclos.webservices.model.AdCategoryHierarchicalVO;

/**
 * Service interface for advertisement categories On Cyclos2, there were two different entities: Heading and Category. Now, categories can have a
 * parent, and that would allow a tree of categories. But, at the time, only two levels are allowed, what mimics the Cyclos 2 behaviour using a single
 * entity. Advertisements are allowed only on leaf categories. The interface is used for crud (create, remove, update, delete) operations and other ad
 * related operations like list and activate categories.
 * @author luis
 * @author rafael
 */
public interface AdCategoryService extends Service {

    /**
     * Exports all enabled categories into a xml string. For format, see {@link #importFromXml(String)}
     */
    String exportToXml();

    /**
     * Converts a category into a hierarchical representation
     */
    AdCategoryHierarchicalVO getHierarchicalVO(AdCategory category);

    /**
     * Import categories from the given xml string, creating a new category hierarchy. The expected format is:
     * 
     * <pre>
     * &lt;ad-categories&gt;
     *     &lt;ad-category name=&quot;...&quot;&gt;
     *         &lt;ad-category name=&quot;...&quot; /&gt;
     *         &lt;ad-category name=&quot;...&quot; /&gt;
     *     &lt;/ad-category&gt;
     *     &lt;ad-category name=&quot;...&quot;&gt;
     *         &lt;ad-category name=&quot;...&quot;&gt;
     *            &lt;ad-category name=&quot;...&quot; /&gt;
     *            &lt;ad-category name=&quot;...&quot; /&gt;
     *         &lt;/ad-category&gt;
     *         &lt;ad-category name=&quot;...&quot; /&gt;
     *         &lt;ad-category name=&quot;...&quot; /&gt;
     *         &lt;ad-category name=&quot;...&quot; /&gt;
     *     &lt;/ad-category&gt;
     * &lt;/ad-categories&gt;
     * </pre>
     */
    void importFromXml(String xml);

    /**
     * Lists the active leaf categories
     */
    List<AdCategory> listLeaf();

    /**
     * Lists the active root categories, with all child categories fetched
     */
    List<AdCategory> listRoot();

    /**
     * Loads the category, fetching the specified relationships
     */
    AdCategory load(Long id, Relationship... fetch) throws EntityNotFoundException;

    /**
     * Removes the specified categories
     */
    int remove(Long... ids);

    /**
     * Saves the specified category, returning it with the generated id if it doesn't exist yet. A category's parent cannot be modified
     */
    AdCategory save(AdCategory category);

    /**
     * Searches for categories as parameters
     */
    List<AdCategory> search(AdCategoryQuery query);

    /**
     * Set the ad category order
     */
    void setOrder(Long[] fieldIds);

    /**
     * Validates the specified category
     */
    void validate(AdCategory category) throws ValidationException;

}
