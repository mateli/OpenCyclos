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
package nl.strohalm.cyclos.services.infotexts;

import java.util.Collections;
import java.util.List;

import nl.strohalm.cyclos.access.AdminSystemPermission;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.infotexts.InfoText;
import nl.strohalm.cyclos.entities.infotexts.InfoTextQuery;
import nl.strohalm.cyclos.services.BaseServiceSecurity;

public class InfoTextServiceSecurity extends BaseServiceSecurity implements InfoTextService {

    private InfoTextServiceLocal infoTextService;

    @Override
    public InfoText load(final long id, final Relationship... fetch) throws EntityNotFoundException {
        permissionService.permission().admin(AdminSystemPermission.INFO_TEXTS_VIEW).check();
        return infoTextService.load(id, fetch);
    }

    @Override
    public int remove(final Long... ids) {
        permissionService.permission().admin(AdminSystemPermission.INFO_TEXTS_MANAGE).check();
        return infoTextService.remove(ids);
    }

    @Override
    public InfoText save(final InfoText infoText) {
        permissionService.permission().admin(AdminSystemPermission.INFO_TEXTS_MANAGE).check();
        return infoTextService.save(infoText);
    }

    @Override
    public List<InfoText> search(final InfoTextQuery query) {
        if (!permissionService.permission().admin(AdminSystemPermission.INFO_TEXTS_VIEW).hasPermission()) {
            return Collections.emptyList();
        }
        return infoTextService.search(query);
    }

    public void setInfoTextServiceLocal(final InfoTextServiceLocal infoTextService) {
        this.infoTextService = infoTextService;
    }

    @Override
    public void validate(final InfoText infoText) {
        // No permissions must be checked to validate.
        infoTextService.validate(infoText);
    }
}
