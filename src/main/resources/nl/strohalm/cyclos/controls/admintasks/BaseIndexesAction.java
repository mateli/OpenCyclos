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
package nl.strohalm.cyclos.controls.admintasks;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseAction;
import nl.strohalm.cyclos.entities.Indexable;
import nl.strohalm.cyclos.entities.ads.Ad;
import nl.strohalm.cyclos.entities.members.Administrator;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.Operator;
import nl.strohalm.cyclos.entities.members.records.MemberRecord;
import nl.strohalm.cyclos.services.application.ApplicationService;
import nl.strohalm.cyclos.utils.ClassHelper;

import org.apache.struts.action.ActionForward;

/**
 * Base action to perform operations over full-text indexes
 * 
 * @author luis
 */
public abstract class BaseIndexesAction extends BaseAction {

    private static final Map<String, Class<? extends Indexable>> INDEXABLE_TYPES;
    static {
        final Map<String, Class<? extends Indexable>> indexableTypes = new HashMap<String, Class<? extends Indexable>>();
        indexableTypes.put(ClassHelper.getClassName(Member.class), Member.class);
        indexableTypes.put(ClassHelper.getClassName(Administrator.class), Administrator.class);
        indexableTypes.put(ClassHelper.getClassName(Operator.class), Operator.class);
        indexableTypes.put(ClassHelper.getClassName(Ad.class), Ad.class);
        indexableTypes.put(ClassHelper.getClassName(MemberRecord.class), MemberRecord.class);
        INDEXABLE_TYPES = Collections.unmodifiableMap(indexableTypes);
    }

    protected ApplicationService                                 applicationService;

    @Inject
    public final void setApplicationService(final ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @Override
    protected ActionForward executeAction(final ActionContext context) throws Exception {
        final IndexesForm form = context.getForm();
        form.getIndex();
        return null;
    }

    protected Class<? extends Indexable> resolveEntityType(final ActionContext context) {
        final IndexesForm form = context.getForm();
        return INDEXABLE_TYPES.get(form.getIndex());
    }

}
