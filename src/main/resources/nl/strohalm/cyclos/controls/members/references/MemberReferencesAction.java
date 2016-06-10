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
package nl.strohalm.cyclos.controls.members.references;

import java.util.Calendar;
import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseQueryAction;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.accounts.transactions.Payment;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.Reference;
import nl.strohalm.cyclos.entities.members.Reference.Level;
import nl.strohalm.cyclos.entities.members.Reference.Nature;
import nl.strohalm.cyclos.entities.members.ReferenceQuery;
import nl.strohalm.cyclos.entities.members.TransactionFeedback;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.services.elements.ReferenceService;
import nl.strohalm.cyclos.utils.Period;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.RequestHelper;
import nl.strohalm.cyclos.utils.TimePeriod;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.DataBinderHelper;
import nl.strohalm.cyclos.utils.conversion.CoercionHelper;
import nl.strohalm.cyclos.utils.query.QueryParameters;
import nl.strohalm.cyclos.utils.validation.ValidationException;

/**
 * Action used to view a member's references and edit the reference to that member
 * @author luis
 */
public class MemberReferencesAction extends BaseQueryAction {

    /**
     * Represents the direction we're searching
     * @author luis
     */
    public static enum Direction {
        RECEIVED, GIVEN
    }

    private DataBinder<ReferenceQuery> dataBinder;
    private ReferenceService           referenceService;

    @Inject
    public void setReferenceService(final ReferenceService referenceService) {
        this.referenceService = referenceService;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void executeQuery(final ActionContext context, final QueryParameters queryParameters) {
        final HttpServletRequest request = context.getRequest();
        final MemberReferencesForm form = context.getForm();
        final Direction direction = CoercionHelper.coerce(Direction.class, form.getDirection());
        final Member member = (Member) request.getAttribute("member");
        final ReferenceQuery query = (ReferenceQuery) queryParameters;
        if (member != null) {
            form.setMemberId(member.getId());
        }

        // Retrieve both summaries for all time and last 30 days
        final Map<Level, Integer> allTime = referenceService.countReferencesByLevel(query.getNature(), member, direction == Direction.RECEIVED);
        request.setAttribute("summaryAllTime", allTime);
        final Period period30 = new TimePeriod(30, TimePeriod.Field.DAYS).periodEndingAt(Calendar.getInstance());
        final Map<Level, Integer> last30Days = referenceService.countReferencesHistoryByLevel(query.getNature(), member, period30, direction == Direction.RECEIVED);
        request.setAttribute("summaryLast30Days", last30Days);

        // Calculate the score
        int totalAllTime = 0;
        int total30Days = 0;
        int scoreAllTime = 0;
        int score30Days = 0;
        final Collection<Level> levels = (Collection<Level>) request.getAttribute("levels");
        int nonNeutralCountAllTime = 0;
        int positiveCountAllTime = 0;
        int nonNeutralCount30Days = 0;
        int positiveCount30Days = 0;
        for (final Level level : levels) {
            final int value = level.getValue();
            final int allTimeCount = CoercionHelper.coerce(Integer.TYPE, allTime.get(level));
            final int last30DaysCount = CoercionHelper.coerce(Integer.TYPE, last30Days.get(level));

            // Calculate the total
            totalAllTime += allTimeCount;
            total30Days += last30DaysCount;

            // Calculate the score (sum of count * value)
            scoreAllTime += allTimeCount * value;
            score30Days += last30DaysCount * value;

            // Calculate the data for positive percentage
            if (value != 0) {
                nonNeutralCountAllTime += allTimeCount;
                nonNeutralCount30Days += last30DaysCount;
                if (value > 0) {
                    positiveCountAllTime += allTimeCount;
                    positiveCount30Days += last30DaysCount;
                }
            }
        }

        // Calculate the positive percentage
        final int percentAllTime = nonNeutralCountAllTime == 0 ? 0 : Math.round((float) positiveCountAllTime / nonNeutralCountAllTime * 100F);
        final int percentLast30Days = nonNeutralCount30Days == 0 ? 0 : Math.round((float) positiveCount30Days / nonNeutralCount30Days * 100F);

        // Store calculated data on request
        request.setAttribute("totalAllTime", totalAllTime);
        request.setAttribute("total30Days", total30Days);
        request.setAttribute("scoreAllTime", scoreAllTime);
        request.setAttribute("score30Days", score30Days);
        request.setAttribute("percentAllTime", percentAllTime);
        request.setAttribute("percent30Days", percentLast30Days);

        // Get the references list
        request.setAttribute("references", referenceService.search(query));
    }

    @Override
    protected QueryParameters prepareForm(final ActionContext context) {
        final MemberReferencesForm form = context.getForm();
        final HttpServletRequest request = context.getRequest();
        final ReferenceQuery query = getDataBinder().readFromString(form.getQuery());
        query.setNature(CoercionHelper.coerce(Nature.class, form.getNature()));

        if (query.getNature() == null) {
            query.setNature(Nature.GENERAL);
        }

        // Find out the member
        Member member;
        try {
            member = (Member) (form.getMemberId() <= 0L ? context.getAccountOwner() : elementService.load(form.getMemberId(), Element.Relationships.GROUP));
        } catch (final Exception e) {
            throw new ValidationException();
        }

        final boolean myReferences = member.equals(context.getAccountOwner());

        // Retrieve the direction we're looking at
        Direction direction = CoercionHelper.coerce(Direction.class, form.getDirection());
        if (direction == null) {
            direction = Direction.RECEIVED;
            form.setDirection(direction.name());
        }
        final boolean isGiven = direction == Direction.GIVEN;
        if (isGiven) {
            query.setFrom(member);
        } else {
            query.setTo(member);
        }
        final boolean isGeneral = query.getNature() == Reference.Nature.GENERAL;

        if (!isGeneral) {
            query.fetch(RelationshipHelper.nested(TransactionFeedback.Relationships.TRANSFER, Payment.Relationships.TYPE, TransferType.Relationships.FROM, AccountType.Relationships.CURRENCY));
        }

        // When it's a member (or operator) viewing of other member's received general references, he can set his own too
        final boolean canSetReference = isGeneral && referenceService.canGiveGeneralReference(member);

        // Check whether the logged user can manage references on the list
        final boolean canManage = isGeneral && (myReferences && isGiven || !myReferences) && referenceService.canManageGeneralReference(member);

        // Bind the form and store the request attributes
        final LocalSettings localSettings = settingsService.getLocalSettings();
        getDataBinder().writeAsString(form.getQuery(), query);
        request.setAttribute("member", member);
        request.setAttribute("canManage", canManage);
        request.setAttribute("myReferences", myReferences);
        request.setAttribute("isGiven", isGiven);
        request.setAttribute("isGeneral", isGeneral);
        request.setAttribute("levels", localSettings.getReferenceLevelList());
        request.setAttribute("canSetReference", canSetReference);
        RequestHelper.storeEnum(request, Direction.class, "directions");

        if (!isGeneral) {
            final boolean showAmount = context.isAdmin() || context.getAccountOwner().equals(member);
            request.setAttribute("showAmount", showAmount);
        }
        return query;
    }

    @Override
    protected boolean willExecuteQuery(final ActionContext context, final QueryParameters queryParameters) throws Exception {
        return true;
    }

    private DataBinder<ReferenceQuery> getDataBinder() {
        if (dataBinder == null) {
            final BeanBinder<ReferenceQuery> binder = BeanBinder.instance(ReferenceQuery.class);
            binder.registerBinder("pageParameters", DataBinderHelper.pageBinder());
            dataBinder = binder;
        }
        return dataBinder;
    }
}
