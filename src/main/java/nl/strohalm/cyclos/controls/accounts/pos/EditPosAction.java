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
package nl.strohalm.cyclos.controls.accounts.pos;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.access.AdminMemberPermission;
import nl.strohalm.cyclos.access.BrokerPermission;
import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.entities.accounts.pos.MemberPos;
import nl.strohalm.cyclos.entities.accounts.pos.MemberPos.Status;
import nl.strohalm.cyclos.entities.accounts.pos.Pos;
import nl.strohalm.cyclos.entities.accounts.pos.Pos.Relationships;
import nl.strohalm.cyclos.entities.groups.AdminGroup;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.access.exceptions.BlockedCredentialsException;
import nl.strohalm.cyclos.services.accounts.pos.MemberPosService;
import nl.strohalm.cyclos.services.accounts.pos.PosService;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;

import org.apache.struts.action.ActionForward;

/**
 * @author rodrigo
 */
public class EditPosAction extends BaseFormAction {

    private class PosEditionPermissions {
        private boolean canAssign;
        private boolean canBlock;
        private boolean canChangeParameters;
        private boolean canChangePin;
        private boolean canDiscard;
        private boolean canUnassign;
        private boolean canUnblock;
        private boolean canUnblockPin;
        private boolean editable;
        private boolean isDiscarded;

        public boolean isCanAssign() {
            return canAssign;
        }

        public boolean isCanBlock() {
            return canBlock;
        }

        public boolean isCanChangeParameters() {
            return canChangeParameters;
        }

        public boolean isCanChangePin() {
            return canChangePin;
        }

        public boolean isCanDiscard() {
            return canDiscard;
        }

        public boolean isCanUnassign() {
            return canUnassign;
        }

        public boolean isCanUnblock() {
            return canUnblock;
        }

        public boolean isCanUnblockPin() {
            return canUnblockPin;
        }

        public boolean isDiscarded() {
            return isDiscarded;
        }

        public boolean isEditable() {
            return editable;
        }

        public void setCanAssign(final boolean canAssign) {
            this.canAssign = canAssign;
        }

        public void setCanBlock(final boolean canBlock) {
            this.canBlock = canBlock;
        }

        public void setCanChangeParameters(final boolean canChangeParameters) {
            this.canChangeParameters = canChangeParameters;
        }

        public void setCanChangePin(final boolean canChangePin) {
            this.canChangePin = canChangePin;
        }

        public void setCanDiscard(final boolean canDiscard) {
            this.canDiscard = canDiscard;
        }

        public void setCanUnassign(final boolean canUnassign) {
            this.canUnassign = canUnassign;
        }

        public void setCanUnblock(final boolean canUnblock) {
            this.canUnblock = canUnblock;
        }

        public void setCanUnblockPin(final boolean canUnblockPin) {
            this.canUnblockPin = canUnblockPin;
        }

        public void setDiscarded(final boolean isDiscarded) {
            this.isDiscarded = isDiscarded;
        }

        public void setEditable(final boolean editable) {
            this.editable = editable;
        }
    }

    private DataBinder<Pos>  writeDataBinder;
    private PosService       posService;
    private MemberPosService memberPosService;

    // Used to get data and save to database
    public DataBinder<Pos> getWriteDataBinder() {
        if (writeDataBinder == null) {
            final LocalSettings localSettings = settingsService.getLocalSettings();
            final BeanBinder<MemberPos> memberPos = BeanBinder.instance(MemberPos.class, "memberPos");
            memberPos.registerBinder("id", PropertyBinder.instance(Long.class, "id"));
            memberPos.registerBinder("posName", PropertyBinder.instance(String.class, "posName"));
            memberPos.registerBinder("member", PropertyBinder.instance(Member.class, "member"));
            memberPos.registerBinder("status", PropertyBinder.instance(MemberPos.Status.class, "status"));
            memberPos.registerBinder("allowMakePayment", PropertyBinder.instance(Boolean.TYPE, "allowMakePayment"));
            memberPos.registerBinder("resultPageSize", PropertyBinder.instance(Integer.class, "resultPageSize"));
            memberPos.registerBinder("numberOfCopies", PropertyBinder.instance(Integer.class, "numberOfCopies"));
            memberPos.registerBinder("maxSchedulingPayments", PropertyBinder.instance(Integer.class, "maxSchedulingPayments"));
            memberPos.registerBinder("date", PropertyBinder.instance(Calendar.class, "date", localSettings.getDateConverter()));

            final BeanBinder<Pos> binder = BeanBinder.instance(Pos.class);
            binder.registerBinder("posId", PropertyBinder.instance(String.class, "posId"));
            binder.registerBinder("id", PropertyBinder.instance(Long.class, "id"));
            binder.registerBinder("description", PropertyBinder.instance(String.class, "description"));
            binder.registerBinder("status", PropertyBinder.instance(Pos.Status.class, "status"));
            binder.registerBinder("memberPos", memberPos);

            writeDataBinder = binder;
        }
        return writeDataBinder;
    }

    @Inject
    public void setMemberPosService(final MemberPosService memberPosService) {
        this.memberPosService = memberPosService;
    }

    @Inject
    public void setPosService(final PosService posService) {
        this.posService = posService;
    }

    @Override
    protected ActionForward handleSubmit(final ActionContext context) throws Exception {

        final EditPosForm form = context.getForm();
        final HttpServletRequest request = context.getRequest();
        final Pos pos = getWriteDataBinder().readFromString(form.getPos());

        try {
            final String operation = form.getOperation();
            if (operation.equals("block")) {
                final MemberPos persistedMemberPos = memberPosService.load(pos.getMemberPos().getId());
                memberPosService.blockMemberPos(persistedMemberPos);
                context.sendMessage("pos.blocked");
            } else if (operation.equals("unblock")) {
                final MemberPos persistedMemberPos = memberPosService.load(pos.getMemberPos().getId());
                memberPosService.unblockMemberPos(persistedMemberPos);
                context.sendMessage("pos.unblocked");
            } else if (operation.equals("unblockPin")) {
                final MemberPos persistedMemberPos = memberPosService.load(pos.getMemberPos().getId());
                memberPosService.unblockPosPin(persistedMemberPos);
                context.sendMessage("pos.pinUnblocked");
            } else if (operation.equals("assign")) {
                final Member member = elementService.load(Long.parseLong(form.getAssignTo()));
                posService.assignPos(member, pos.getId());
                context.sendMessage("pos.assigned", member.getUsername());
            } else if (operation.equals("changePin")) {
                final MemberPos persistedMemberPos = memberPosService.load(pos.getMemberPos().getId());
                memberPosService.changePin(persistedMemberPos, form.getPin());
                context.sendMessage("pos.pinChanged");
            } else if (operation.equals("discard")) {
                posService.discardPos(pos.getId());
                context.sendMessage("pos.discarded");
            } else if (operation.equals("unassign")) {
                posService.unassignPos(pos.getId());
                context.sendMessage("pos.unassigned");
            } else if (operation.equals("updatePos")) {
                final Pos persistedPos = posService.load(pos.getId());
                boolean persistMemberPos = false;
                MemberPos memberPos = null;
                if (pos.getMemberPos() != null && pos.getMemberPos().getId() != null) {
                    // set values that hasn't changed to avoid ValidationError on Service
                    final MemberPos persistedMemberPos = memberPosService.load(pos.getMemberPos().getId());
                    memberPos = pos.getMemberPos();
                    memberPos.setDate(persistedMemberPos.getDate());
                    memberPos.setMember(persistedMemberPos.getMember());
                    memberPos.setStatus(persistedMemberPos.getStatus());
                    memberPos.setPosPin(persistedMemberPos.getPosPin());
                    persistMemberPos = true;
                } else {
                    pos.setMemberPos(null);
                }

                // ensure this attributes doesn't change
                pos.setPosId(persistedPos.getPosId());
                pos.setStatus(persistedPos.getStatus());

                posService.save(pos);

                if (persistMemberPos) {
                    memberPos.setPos(pos);
                    memberPosService.save(memberPos);
                }
                context.sendMessage("pos.updated");
            } else if (operation.equals("newPos")) {
                pos.setMemberPos(null);
                posService.save(pos);
                context.sendMessage("pos.created");
            }

            final Map<String, Object> params = new HashMap<String, Object>();
            params.put("id", pos.getId());
            if (form.getMemberId() > 0) {
                params.put("memberId", form.getMemberId());
            }
            return ActionHelper.redirectWithParams(context.getRequest(), context.getSuccessForward(), params);
        } catch (final BlockedCredentialsException e) {
            request.getSession().invalidate();
            return context.sendError("card.updateCard.error.userBlocked");
        }
    }

    @Override
    protected void prepareForm(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();
        final EditPosForm form = context.getForm();
        final long id = form.getId();
        Pos pos;
        PosEditionPermissions permissions;
        boolean hasMemberPos = false;

        permissions = checkPermissionsInformation(context);

        final boolean isInsert = id <= 0;
        if (isInsert) {
            pos = new Pos();
            pos.setStatus(Pos.Status.UNASSIGNED);
        } else {
            pos = posService.load(id, Relationships.MEMBER_POS);
            hasMemberPos = pos.getMemberPos() != null;
            if (hasMemberPos) {
                request.setAttribute("memberLogin", pos.getMemberPos().getMember().getUsername());
                request.setAttribute("userName", pos.getMemberPos().getMember().getName());
            }
        }

        getWriteDataBinder().writeAsString(form.getPos(), pos);

        // Attributes
        request.setAttribute("pos", pos);
        request.setAttribute("isInsert", isInsert);
        request.setAttribute("hasMemberPos", hasMemberPos);
        request.setAttribute("isRegularUser", !context.isAdmin() && !context.isBroker());

        // Permissions
        request.setAttribute("canAssign", permissions.isCanAssign());
        request.setAttribute("canBlock", permissions.isCanBlock());
        request.setAttribute("canChangeParameters", permissions.isCanChangeParameters());
        request.setAttribute("canChangePin", permissions.isCanChangePin());
        request.setAttribute("canDiscard", permissions.isCanDiscard());
        request.setAttribute("canUnassign", permissions.isCanUnassign());
        request.setAttribute("canUnblock", permissions.isCanUnblock());
        request.setAttribute("canUnblockPin", permissions.isCanUnblockPin());
        request.setAttribute("editable", permissions.isEditable());
        request.setAttribute("isDiscarded", permissions.isDiscarded());
        request.setAttribute("memberId", form.getMemberId());
    }

    @Override
    protected void validateForm(final ActionContext context) {
        final EditPosForm form = context.getForm();
        final Pos pos = getWriteDataBinder().readFromString(form.getPos());
        posService.validate(pos);
    }

    private PosEditionPermissions checkPermissionsInformation(final ActionContext context) {
        final PosEditionPermissions permissions = new PosEditionPermissions();
        final EditPosForm form = context.getForm();
        Pos pos;
        boolean hasMemberPos = false;
        final long id = form.getId();
        final boolean isInsert = id <= 0;

        permissions.setEditable(permissionService.hasPermission(AdminMemberPermission.POS_MANAGE) || permissionService.hasPermission(BrokerPermission.POS_MANAGE));
        if (isInsert) {
            if (!context.isBroker() && !context.isAdmin()) {
                throw new PermissionDeniedException();
            }
        } else {
            pos = posService.load(id, Relationships.MEMBER_POS);
            hasMemberPos = pos.getMemberPos() != null;
            Member member = null;
            if (hasMemberPos) {
                member = pos.getMemberPos().getMember();
                if (member.equals(context.getElement())) {
                    permissions.setEditable(true);
                }
                if ((!context.isBroker() && !context.isAdmin() && !member.equals(context.getElement())) || (context.isBroker() && !context.isBrokerOf(member) && !member.equals(context.getElement()))) {
                    throw new PermissionDeniedException();
                }
                if (context.isAdmin()) {
                    final AdminGroup group = groupService.load(context.getGroup().getId(), AdminGroup.Relationships.MANAGES_GROUPS);
                    if (!group.getManagesGroups().contains(pos.getMemberPos().getMember().getGroup())) {
                        throw new PermissionDeniedException();
                    }
                }
            } else {
                if (!context.isBroker() && !context.isAdmin()) {
                    throw new PermissionDeniedException();
                }
            }

            if (context.isAdmin() || (context.isBroker() && !hasMemberPos) || (context.isBroker() && hasMemberPos && !member.equals(context.getElement()))) {
                final boolean isAdmin = context.isAdmin();
                switch (pos.getStatus()) {
                    case UNASSIGNED:
                        permissions.setCanAssign(permissionService.hasPermission(isAdmin ? AdminMemberPermission.POS_ASSIGN : BrokerPermission.POS_ASSIGN));
                        break;
                    case ASSIGNED:
                        permissions.setCanUnassign(permissionService.hasPermission(isAdmin ? AdminMemberPermission.POS_ASSIGN : BrokerPermission.POS_ASSIGN));
                        permissions.setCanChangePin(permissionService.hasPermission(isAdmin ? AdminMemberPermission.POS_CHANGE_PIN : BrokerPermission.POS_CHANGE_PIN));
                        if (hasMemberPos) {
                            final Status status = pos.getMemberPos().getStatus();
                            if (status == MemberPos.Status.ACTIVE) {
                                permissions.setCanBlock(permissionService.hasPermission(isAdmin ? AdminMemberPermission.POS_BLOCK : BrokerPermission.POS_BLOCK));
                            }
                            if (status == MemberPos.Status.BLOCKED) {
                                permissions.setCanUnblock(permissionService.hasPermission(isAdmin ? AdminMemberPermission.POS_BLOCK : BrokerPermission.POS_BLOCK));
                            }
                            if (status == MemberPos.Status.PIN_BLOCKED) {
                                permissions.setCanUnblockPin(permissionService.hasPermission(isAdmin ? AdminMemberPermission.POS_UNBLOCK_PIN : BrokerPermission.POS_UNBLOCK_PIN));
                            }
                        }
                        break;
                    case DISCARDED:
                        permissions.setDiscarded(true);
                        break;
                }
                if (pos.getStatus() != Pos.Status.DISCARDED) {
                    permissions.setCanDiscard(permissionService.hasPermission(isAdmin ? AdminMemberPermission.POS_DISCARD : BrokerPermission.POS_DISCARD));
                }
                permissions.setCanChangeParameters(permissionService.hasPermission(isAdmin ? AdminMemberPermission.POS_CHANGE_PARAMETERS : BrokerPermission.POS_CHANGE_PARAMETERS));
            } else if (context.isMember() && hasMemberPos && context.getElement().equals(member)) {
                permissions.setCanChangeParameters(true);

                final Status status = pos.getMemberPos().getStatus();

                if (status == MemberPos.Status.ACTIVE) {
                    permissions.setCanBlock(true);
                } else if (status == MemberPos.Status.BLOCKED) {
                    permissions.setCanUnblock(true);
                } else if (status == MemberPos.Status.PIN_BLOCKED) {
                    permissions.setCanUnblockPin(true);
                }
                if (pos.getStatus().equals(Pos.Status.ASSIGNED)) {
                    permissions.setCanChangePin(true);
                }
            }
        }

        return permissions;
    }
}
