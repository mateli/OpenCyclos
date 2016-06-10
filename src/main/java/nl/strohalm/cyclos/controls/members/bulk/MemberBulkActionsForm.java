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
package nl.strohalm.cyclos.controls.members.bulk;

import java.util.Collections;
import java.util.Set;

import nl.strohalm.cyclos.controls.members.SearchMembersForm;
import nl.strohalm.cyclos.utils.binding.MapBean;

/**
 * Form used to apply bulk actions on members that match the given filter
 * @author luis
 */
public class MemberBulkActionsForm extends SearchMembersForm {

    private static final long serialVersionUID = 3943139281907906116L;
    private MapBean           changeGroup      = new MapBean("newGroup", "comments");
    private MapBean           changeBroker     = new MapBean("newBroker", "comments", "suspendCommission");
    private MapBean           generateCard     = new MapBean("newCard", "comments", "generateForPending", "generateForActive");
    private MapBean           changeChannels   = new MapBean(true, "enableIds", "disableIds");

    public MemberBulkActionsForm() {
        changeChannels.setType("enableIds", Set.class);
        changeChannels.setType("disableIds", Set.class);

        changeChannels.set("enableIds", Collections.emptySet());
        changeChannels.set("disableIds", Collections.emptySet());
    }

    public MapBean getChangeBroker() {
        return changeBroker;
    }

    public MapBean getChangeChannels() {
        return changeChannels;
    }

    public MapBean getChangeGroup() {
        return changeGroup;
    }

    public MapBean getGenerateCard() {
        return generateCard;
    }

    public void setChangeBroker(final MapBean changeBroker) {
        this.changeBroker = changeBroker;
    }

    public void setChangeChannels(final MapBean changeChannels) {
        this.changeChannels = changeChannels;
    }

    public void setChangeGroup(final MapBean changeGroup) {
        this.changeGroup = changeGroup;
    }

    public void setGenerateCard(final MapBean generateCard) {
        this.generateCard = generateCard;
    }
}
