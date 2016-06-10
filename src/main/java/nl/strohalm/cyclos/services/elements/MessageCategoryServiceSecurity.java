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
package nl.strohalm.cyclos.services.elements;

import java.util.Iterator;
import java.util.List;

import nl.strohalm.cyclos.access.AdminSystemPermission;
import nl.strohalm.cyclos.entities.members.messages.MessageCategory;
import nl.strohalm.cyclos.entities.members.messages.MessageCategoryQuery;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.BaseServiceSecurity;
import nl.strohalm.cyclos.utils.validation.ValidationException;

/**
 * Security implementation for {@link MessageCategoryService}
 * 
 * @author Rinke
 */
public class MessageCategoryServiceSecurity extends BaseServiceSecurity implements MessageCategoryService {

    private MessageCategoryServiceLocal messageCategoryService;

    @Override
    public MessageCategory load(final Long id) {
        MessageCategory messageCategory = messageCategoryService.load(id);
        if (!permissionService.hasPermission(AdminSystemPermission.MESSAGE_CATEGORIES_VIEW) && !messageCategoryService.canView(messageCategory)) {
            throw new PermissionDeniedException();
        }
        return messageCategory;
    }

    @Override
    public int remove(final Long... ids) {
        checkManages();
        return messageCategoryService.remove(ids);
    }

    @Override
    public MessageCategory save(final MessageCategory messageCategory) {
        checkManages();
        return messageCategoryService.save(messageCategory);
    }

    @Override
    public List<MessageCategory> search(final MessageCategoryQuery query) {
        // Filter out the non-visible categories. This can be done because the result type is never PAGE.
        List<MessageCategory> result = filterVisible(messageCategoryService.search(query));
        return result;
    }

    public void setMessageCategoryServiceLocal(final MessageCategoryServiceLocal messageCategoryService) {
        this.messageCategoryService = messageCategoryService;
    }

    @Override
    public void validate(final MessageCategory messageCategory) throws ValidationException {
        // no permissions needed for validation
        messageCategoryService.validate(messageCategory);
    }

    private void checkManages() {
        permissionService.permission()
                .admin(AdminSystemPermission.MESSAGE_CATEGORIES_MANAGE)
                .check();
    }

    /**
     * filters the list of MessageCats, so that only cats which are visible to the LoggedUser's group remain. If logged as admin with
     * AdminSystemPermission.MESSAGE_CATEGORIES_VIEW, no filtering is done regardless of SystemGroup.messageCategories.
     */
    private List<MessageCategory> filterVisible(final List<MessageCategory> listAll) {
        // for sysAdmins who can view/manage all cats, don't filter
        if (!permissionService.hasPermission(AdminSystemPermission.MESSAGE_CATEGORIES_VIEW)) {
            for (Iterator<MessageCategory> iterator = listAll.iterator(); iterator.hasNext();) {
                MessageCategory cat = iterator.next();
                if (!messageCategoryService.canView(cat)) {
                    iterator.remove();
                }
            }
        }
        return listAll;
    }

}
