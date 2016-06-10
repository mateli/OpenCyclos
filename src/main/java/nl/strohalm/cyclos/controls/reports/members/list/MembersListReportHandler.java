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
package nl.strohalm.cyclos.controls.reports.members.list;

import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.reports.members.list.MembersListReportDTO.PeriodType;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.MemberQuery;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.services.accounts.AccountTypeService;
import nl.strohalm.cyclos.services.accounts.MemberAccountTypeQuery;
import nl.strohalm.cyclos.services.elements.ElementService;
import nl.strohalm.cyclos.utils.SpringHelper;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.binding.SimpleCollectionBinder;
import nl.strohalm.cyclos.utils.query.PageParameters;
import nl.strohalm.cyclos.utils.query.QueryParameters.ResultType;

public class MembersListReportHandler {

    private AccountTypeService               accountTypeService;
    private ElementService                   elementService;
    private BeanBinder<MembersListReportDTO> binder;
    private LocalSettings                    settings = null;

    public MembersListReportHandler(final LocalSettings settings) {
        this.settings = settings;
    }

    @SuppressWarnings("unchecked")
    public Collection<AccountType> getAccountTypes(final List<MemberGroup> groups) {
        final MemberAccountTypeQuery atQuery = new MemberAccountTypeQuery();
        atQuery.setRelatedToGroups(groups);
        final Collection<AccountType> accountTypes = (Collection<AccountType>) accountTypeService.search(atQuery);
        return accountTypes;
    }

    public BeanBinder<MembersListReportDTO> getDataBinder() {
        if (binder == null || settings == null) {
            BeanBinder<MembersListReportDTO> temp;
            temp = BeanBinder.instance(MembersListReportDTO.class);
            temp.registerBinder("broker", PropertyBinder.instance(Member.class, "brokerId"));
            temp.registerBinder("memberName", PropertyBinder.instance(Boolean.TYPE, "memberName"));
            temp.registerBinder("brokerUsername", PropertyBinder.instance(Boolean.TYPE, "brokerUsername"));
            temp.registerBinder("brokerName", PropertyBinder.instance(Boolean.TYPE, "brokerName"));
            temp.registerBinder("groups", SimpleCollectionBinder.instance(MemberGroup.class, "groups"));
            temp.registerBinder("activeAds", PropertyBinder.instance(Boolean.TYPE, "activeAds"));
            temp.registerBinder("expiredAds", PropertyBinder.instance(Boolean.TYPE, "expiredAds"));
            temp.registerBinder("permanentAds", PropertyBinder.instance(Boolean.TYPE, "permanentAds"));
            temp.registerBinder("scheduledAds", PropertyBinder.instance(Boolean.TYPE, "scheduledAds"));
            temp.registerBinder("givenReferences", PropertyBinder.instance(Boolean.TYPE, "givenReferences"));
            temp.registerBinder("receivedReferences", PropertyBinder.instance(Boolean.TYPE, "receivedReferences"));
            temp.registerBinder("accountsBalances", PropertyBinder.instance(Boolean.TYPE, "accountsBalances"));
            temp.registerBinder("accountsCredits", PropertyBinder.instance(Boolean.TYPE, "accountsCredits"));
            temp.registerBinder("accountsUpperCredits", PropertyBinder.instance(Boolean.TYPE, "accountsUpperCredits"));
            if (settings != null) {
                temp.registerBinder("period", PropertyBinder.instance(Calendar.class, "period", settings.getDateTimeConverter()));
            }
            temp.registerBinder("periodType", PropertyBinder.instance(PeriodType.class, "periodType"));
            binder = temp;
        }

        return binder;
    }

    @SuppressWarnings("unchecked")
    public MembersListReportVOIterator handleReport(final ActionContext context) {
        final MembersListReportForm form = context.getForm();
        final MembersListReportDTO dto = getDataBinder().readFromString(form.getMembersListReport());
        final List<MemberGroup> groups = (List<MemberGroup>) dto.getGroups();
        final Member broker = dto.getBroker();
        final MemberQuery query = new MemberQuery();
        query.fetch(Element.Relationships.USER);
        query.setGroups(groups);
        query.setBroker(broker);
        query.setResultType(ResultType.ITERATOR);
        query.setPageParameters(new PageParameters(-1, 0));

        final List<Member> members = (List<Member>) elementService.searchAtDate(query, dto.getPeriod());

        final Collection<AccountType> accountTypes = getAccountTypes(groups);
        final MembersListReportVOIterator voIterator = new MembersListReportVOIterator(dto, accountTypes, members);
        SpringHelper.injectBeans(context.getServletContext(), voIterator);

        return voIterator;
    }

    @Inject
    public void setAccountTypeService(final AccountTypeService accountTypeService) {
        this.accountTypeService = accountTypeService;
    }

    @Inject
    public void setElementService(final ElementService elementService) {
        this.elementService = elementService;
    }
}
