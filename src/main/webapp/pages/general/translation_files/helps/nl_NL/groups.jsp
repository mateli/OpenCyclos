<div style="page-break-after: always;"
<br><br>Cyclos has a categorization of users in groups.
Each user in the system can only be in one group. There are three <a
	href="#group_categories"><u>main categories</u></a> of groups.<br>
The groups are used to grant <a href="#autorisaties"><u>autorisaties</u></a> to
users in the software. A user cannot access cyclos functionality if his group
doesn't allow permission for it. It is of course possible to change group
autorisaties, or to move users from one permission group to another.<br>
A part from the autorisaties a group can also have specific group <a
	href="#edit_member_group"><u>Settings</u></a> that define behaviour of the
group like limits and the type of access that is allowed. Member groups have more configurations
options than administrator groups. In the member group settings you can define
for example what accounts the group has as well as layout and content items
for the specific group.
<br><br>The fact that a Member can only be in one group does not mean that all
system configuration is group specific. The system is still very flexible.
Configurations and settings exist at system level, group level and individual
level. If the same setting exist at various levels the lower level will always
have priority. For example an individual credit limit will overwrite a group
credit limit. And a customized group page (for example the contact page) will
overwrite the system contact page.<br>
Many configurations can be set for multiple groups. For example a contribution
can be configured to be charged on several member groups.
<br><br>Cyclos ships with a set of standard groups. Normally, these groups should
be fine for running a system.<br>
You can use the standard groups not only for granting autorisaties, but also for
managing your groups. For example, if a member is to be removed from the system,
you can just move him to the &quot;removed members&quot; group via the
&quot;change group&quot; function. This function also records all group changes
with the date, time and administrator who performed the action.<br>
Though the configuration with standard groups fits well for most organizations,
it is also possible to create new groups. However, this is something which
should only be done with experience in running a system for some time.

<br><br><span class="admin"> <i>Waar te vinden</i><br>
Group Management can be found under the Main &quot;Menu: Users & Groups >
Permission Groups&quot;.<br>
Group filters can be found at the &quot;Menu: Users & Groups > group filters&quot;.


<br><br><i>How to get it working.</i><br>
A new member will always be part of a group. Therefore a group needs to be
selected when creating a new member or admin. This is done in the section
&quot;Users &amp; Groups > <a href="${pagePrefix}user_management#search_member_by_admin"><u>Manage
Members</u></a>&quot; and &quot;Users &amp; Groups > <a
	href="${pagePrefix}user_management#search_admin"><u>Manage Admins</u></a>&quot;.<br>
</span>
<hr>

<a name="group_categories"></a>
<h2>Main categories for groups</h2>
There are three main categories of groups:
<ul>
	<li><a href="#member_groups"><u>member groups</u></a> - ordinary members
	having access to the member section of cyclos.
	<li><a href="#broker_groups"><u>broker groups</u></a> - a sort of
	&quot;super-members&quot;, members allowed some administrative functions for a
	set of other members.
	<li><a href="#admin_groups"><u>admin groups</u></a> - users with
	administrative functions.
</ul>
This separation in main categories is for security reasons, so that it is
impossible to give a member some admin
<a href="#autorisaties"><u>autorisaties</u></a>
by accident.
<br>
All of the groups come with default autorisaties, but it is possible to modify
these.

<hr>

<a name="member_groups"></a>
<h2>Standard Member Groups</h2>
Members of these groups have access to the member section of Cyclos. The system
comes with the following standard Member Groups:
<ul>
	<li><a href="#inactive_members"><u>Inactive Members</u></a> : If this group
	is set as initial group users cannot login but need to be &quot;activated&quot;
	(placed in an active member group) by an administrator before being able to log
	in.
	<li><a href="#full_members"><u>Full Members</u></a> :&nbsp; A normal
	member.
	<li><a href="#disabled_members"><u>Disabled Members</u></a> :&nbsp;
	Temporary inactive members.
	<li><a href="#removed_members"><u>Removed Members</u></a> :&nbsp; Members
	who definitely left the system.
</ul>

The default groups are not fixed or &quot;hard coded&quot; but have been created
using the group
<a href="#autorisaties"><u>autorisaties</u></a>
and
<a href="#edit_member_group"><u>Settings</u></a>
that we thought are commonly used. It but it is possible to modify these groups
are create new groups with different configurations.
<hr class="help">

<a name="inactive_members"></a>
<h3>Inactive Members Group</h3>
When a user registers via the registration page he will be put automatically in
the &quot;Inactive Members&quot; group. Members from this group cannot login and
access the system and do not have an active account. Account administrators can
request a list of users from this group; after validation of this user, he can be 
put into the an &quot;active&quot; group. Normally the
<a href="#full_members"><u>Full Member</u></a>
group.
<br>
Because the inactive members has no related account it means that it is possible
to delete members completely from the system. Once a member has been in an
&quot;active&quot; group (with an account) the Member cannot be deleted anymore
and can only be put in the
<a href="#removed_members"><u>Removed Members</u></a>
group. More information about the inactive/active status of groups can be found
at this <a href="#group_details"><u>Help section</u></a>.
<hr class="help">

<a name="full_members"></a>
<h3>Full Members group</h3>
This is the normal group for members. A &quot;Full Member&quot; can login and
select any Member functionality. When a member is moved from the Pending to the
Full Member group he will be &quot;activated&quot;, This means that he and can
receive an account with the initial default credit (if configured) and will also
receive a password for logging in to the system.
<br>
If configured he may be sent an &quot;Activation Mail&quot; with information
about the login and account status.
<hr class="help">

<a name="disabled_members"></a>
<h3>Disabled Members group</h3>
When an Administrator places a Member in the &quot;Disabled Members&quot; group
the Member cannot login anymore. The account is in &quot;Hibernation&quot;
state. Members in this group are not charged for taxes and contribution
payments. The only active function that a &quot;Disabled Member&quot; has is
that they can still receive payments (but they cannot login to see this).
<br>
The Ads of a Disabled Member won't show up in the Ads Search done by other
Members. But the profile of Disabled Members will show up in the Member search.
When viewing the profile of a Disabled Member it will show the message that the
Member is Disabled and has (at this moment) no access to the system.
<br>
In order to be re-activated a Member from this group needs to be placed back in
the
<a href="#full_members"><u>Full Members</u></a>
group by an administrator.
<br>
A typical reason for placing a Member in the Disabled group would be that a
Member has moved abroad for a limited period of time (like four months). It can
also be used to put suspicious Members in this group to avoid that they login
(pending further inquiry).
<hr class="help">

<a name="removed_members"></a>
<h3>Removed Members group</h3>
The reason to move a Member into the &quot;Removed Members&quot; group is that
the Member has left the system. Once in the Removed group one cannot get back
into any other group. The data will still be in the Database and viewable by
administrators but it only serves as a backup function.
<br>
Any data (Ads, profile) from the Removed Member will not be viewable by other
members. Only the transaction history will show the past transactions with this
Member. If in this case an active Member selects the Member name from an old
transaction history record he will receive a message that this member has been
removed instead of showing the profile. If Members still have a Removed Member
in their contact list they will receive the same alert message.
<br><br>
The Removed group acts merely as an archive function. If after some years the
system needs a clean up, the administrator will know that the data from the
Removed group can be deleted (backed-up) safely.
<br>
<b>Note:</b> There is an exception to this rule. A member that never belonged 
to a group that had accounts can be removed permanently from the system. There 
is a separate administration permission for this. 
<br>
<hr>

<a name="broker_groups"></a>
<h2>Standard Broker Groups</h2>
Brokers are a sort of &quot;Super Members&quot; who can be allowed to perform
certain administrative actions for a set of other members. They have access to
the member section of Cyclos.
<br>
The following standard broker groups are available in cyclos:
<ul>
	<li><a href="#full_brokers"><u>Full Brokers</u></a>: the normal, standard
	broker type.
	<li><a href="#disabled_brokers"><u>Disabled Brokers</u></a>: for temporary
	disabling a broker.
	<li><a href="#removed_brokers"><u>Removed Brokers</u></a>: permanently
	removed brokers.
</ul>

Like the default member groups the default broker group are not fixed or
&quot;hard coded&quot; but have been created using the group
<a href="#autorisaties"><u>autorisaties</u></a>
and
<a href="#edit_broker_group"><u>Settings</u></a>
that we thought are commonly used. It but it is possible to modify these groups
are create new groups with different configurations.
<hr class="help">

<a name="full_brokers"></a>
<h3>Full Brokers Group</h3>
A broker is a Member with extra functions. A broker can register other members
and depending on the system configuration, can have certain access to the
members of whom he is the broker. When a broker registers a member this member
first needs to be activated by an administrator. It is also possible that a
broker can place members directly in one or more &quot;active&quot; groups, but
this would need to be configured. It is also possible for a broker to receive
<a href="${pagePrefix}brokering#commission"><u></u></a> on the (transaction) activity of its members. A commission is
configured for the Full broker group but not activated.
<hr class="help">

<a name="disabled_brokers"></a>
<h3>Disabled Brokers Group</h3>
A broker in this group cannot login, also not as a member. The account is still
active which means that the broker can receive payments and will be charged
contributions (if applicable).
<br>
You can use this group for temporary disabling a broker, for example because the
broker is abroad for a few months, or for example pending inquiry if you suspect
abuse or fraud by a broker.
<br><br>
See the
<a href="${pagePrefix}brokering"><u>broker section</u></a>
for more explanation on brokers.
<hr class="help">

<a name="removed_brokers"></a>
<h3>Removed Brokers Group</h3>
This group is much the same as the
<a href="#removed_members"><u>&quot;Removed member&quot;</u></a>
group. If a broker has been put in the removed broker group, the &quot;broker -
members&quot; history will still be visible for administrators.
<br>
Beware that the broker can never be put back into another group;
&quot;removed&quot; really means &quot;removed&quot;.
<br><br>
See the
<a href="${pagePrefix}brokering"><u>broker section</u></a>
for more explanation on brokers.
<hr>

<a name="admin_groups"></a>
<h2>Standard Admin Groups</h2>
Users in admin groups may perform administrative tasks in the software. They
have access to the admin section of Cyclos. 
<span class="admin">
Cyclos ships with the following
standard admin groups:
<ul>
	<li><a href="#system_admins"><u>System Administrators</u></a>: can use any
	administration function.
	<li><a href="#account_admins"><u>Account Administrators</u></a>: for
	member related management.
	<li><a href="#disabled_admins"><u>Disabled Administrators</u></a>:
	temporary removed Administrators.
	<li><a href="#removed_admins"><u>Removed Administrators</u></a>:
	definitively removed administrators.
</ul>

These groups come with default
<a href="#autorisaties"><u>autorisaties</u></a>
, but it is possible to modify these.
</span>
<hr class="help">

<span class="admin">
<a name="system_admins"></a>
<h3>System Administrators Group</h3>
Users from this group can use any administration functions, including creating
new administrators, setting autorisaties and changing the system configuration.
It is good practive to use the system administration group only for configuration 
and not for operational tasks.
<hr class="help">
</span>

<span class="admin">
<a name="account_admins"></a>
<h3>Account Administrators Group</h3>
Users in this group can use any Member related management and Ads management. An
account administrator cannot change any system setting /configuration. Also the
account administrator has access to all the &quot;view&quot; functions like
system status, statistics etc. It is possible to have create account
administration groups to manage specific member groups in order to divide the
account administration &quot;horizontally&quot;.
<hr class="help">
</span>

<span class="admin">
<a name="disabled_admins"></a>
<h3>Disabled Administrators Group</h3>
Administrators in this group can simply not do anything, not even login. This
group can be used to temporarily Disable adminstrators without having to delete
them.
<hr class="help">
</span>

<span class="admin">
<a name="removed_admins"></a>
<h3>Removed Administrators Group</h3>
This group is for definitively removing administrators from the system. Beware
that, just as with
<a href="#removed_members"><u>Removed Members</u></a>
, there is no way back. Once removed, the administrator cannot be moved back.
The only option which is then still available, is to completely remove the admin
from the system and the database.
<hr class="help">
</span>

<span class="admin">
<a name="change_group"></a>
<h3>Change Group</h3>
Here you can change the group a member (or
<a href="${pagePrefix}brokering"><u>broker</u></a>
) belongs to. Just select the new group from the drop-down box. You must write a
comment on this change of group in the &quot;Description&quot; text area. You
can submit your change by clicking the &quot;submit&quot; button.
<br><br>Click here for an overview of <a href="#member_groups"><u>member
groups</u></a>.
<br><br>After you have submitted the change of group, it is placed in the history
box, in chronological order, the newest on top. The comment history shows above
every comment a status line with the name of the administrator who submitted the
change, the date and the actual group change which was performed (&quot;from
group x to y&quot;). <br>
This is the way administrators can have a quick overview of what happened with a
Member account and read why changes had been done. The comment line can just be
a short sentence with the reason for the change. Any other additional
information about a customer should be entered in the <a
	href="${pagePrefix}profiles#member_info_actions"><u>remarks</u></a> function.
<br><br>Some remarks on changing groups:
<ul>
	<li>When a member is in the <a href="#inactive_members"><u>Inactive Members</u></a> 
	group you have the option to delete the member completely from
	the system. This can be useful for duplicate or bogus registrations. Once a
	member account has been activated it cannot be deleted anymore but you can put
	it in the &quot;Removed Members&quot; group.
	<li>When you move a member from a <a href="#full_brokers"><u>broker</u></a>
	to a normal member (non-broker) group, then all the members which fall under
	the broker will have no broker anymore. (This is not the case if you move the
	broker to another broker group, for example the <a href="#disabled_brokers"><u>Disabled
	Brokers</u></a>). If you don't want the members to be left without any broker, it may
	be smart to first change all the concerning members their broker, and only
	after that move their original broker to a non-broker group. This may be done
	via the <a href="${pagePrefix}user_management#bulk_actions"><u>bulk
	actions</u></a> function.
</ul>
<hr class="help">
</span>

<span class="admin">
<a name="change_group_admin"></a>
<h3>Change admin group</h3>
Here you can change the
<a href="#admin_groups"><u>admin group</u></a>
an Admin belongs to. Just select the new group from the drop-down box. You can
write a comment on this change of groups (for example: why) in the
&quot;description&quot; text area. Don't forget to click the submit button
labeled &quot;Change Group&quot; after you have finished entering your text.
<br><br>After you have submitted the change, it is placed in the history box, in
chronological order, the newest on top. The comment history shows above every
comment a status line with the name of the administrator who submitted the
change, the date and the actual group change which was performed (&quot;from
group x to y&quot;).
<br><br>This way the administrators can have a quick overview of what happened
with an Admin account and read why changes had been done. The comment can just
be a short sentence with the reason for change. Any other additional information
about an administrator should be entered in the Remarks function.
<br><br>In this window there is also the possibility to remove an administrator
completely from the system if this is needed. However, the preferred way would
be to place the admin in the &quot;Disabled Administrators Group&quot;.
<hr class="help">
</span>

<span class="admin">
<a name="group_management"></a>
<h2>Group Management</h2>
You can perform various group management actions in cyclos. You can change group
properties, modify autorisaties, and you can delete or add permission groups.

Group management can be reached via &quot;Menu: Users & Groups > Permission groups&quot;.
<hr>
</span>

<span class="admin">
<a name="search_groups"></a>
<h3>Search groups</h3>
You can search by one of the
<a href="#group_categories"><u>categories</u></a> in the &quot;type&quot; drop
down and if the system has <a href="#group_filters"><u>group filters</u></a> a
search option for these filters will also appear.
<hr class="help">
</span>

<span class="admin">
<a name="manage_groups"></a>
<h3>Manage Permission Groups</h3>
With this window you can manage the various permission groups. This window gives
an overview of available groups and the possibility to create new groups.
<br><br>You can click the following icons for each of the listed groups:
<ul>
	<li><img border="0" src="${images}/edit.gif" width="16" height="16">&nbsp;Selecting
	the edit icon will open a page where you can set the group properties.
	<li><img border="0" src="${images}/autorisaties.gif" width="16" height="16">&nbsp;Selecting
	the autorisaties icon will open a page where you can set the autorisaties for the
	group.
	<li><img border="0" src="${images}/autorisaties_gray.gif" width="16" height="16">&nbsp;If
	the permission group icon is grey it means that no autorisaties can be set for
	the group because it is a &quot;removed&quot; group. Members that will be
	placed in this group will be removed but some of their data (e.g. transactions)
	will remain in the system. For more info on this see the <a
		href="#insert_group"><u>help file</u></a> of &quot;new&quot; group.
	<li><img border="0" src="${images}/delete.gif" width="16" height="16">&nbsp;selecting
	the remove icon will let you remove the group. Groups can only be removed if no
	more users are in it.
</ul>
You can click the &quot;Insert New Group&quot; button to insert a new group. We
recommend strongly to only do this when you have had some experience working
with the default groups.
<br>
Click here for an overview of
<a href="#group_categories"><u>group categories</u></a>
.
<hr class="help">
</span>

<span class="admin">
<a name="edit_admin_group"></a>
<h3>Modify Admin Group</h3>
You can edit/modify an
<a href="#admin_groups"><u>admin group</u></a>
's properties here. After clicking the &quot;change&quot; button, you can change
the name, the description and the &quot;access settings&quot;. The status can
only be defined on group creation.
<br><br>Note that you cannot set autorisaties here; group properties and settings
are not the same as autorisaties. You can modify the group autorisaties by
clicking the <img border="0" src="${images}/autorisaties.gif" width="16"
	height="16"> autorisaties icon from the <a href="#manage_groups"><u>group
overview</u></a>, but you may also use the shortcut button below the window labeled
&quot;Group Permissions&quot;.<br>
<br><br>The following group settings are availible:
<ul>
	<li><b>Password length:</b> Minimum and maximum password length.<br>
	<li><b>Password Policy:</b> You can choose from three options; the meaning
	of those options are quite obvious (birthdate, sequence of numbers etc).<br>
	If a password policy is chosen the users cannot chose a new password that 
	have been used in the past.
	<li><b>Maximum password tries:</b> When the user reaches the amount of
	maximimum attempts the user cannot login anymore until the deactivation time
	has passed (see next setting).<br>
	<li><b>Deactivation time after max password tries:</b> This is the time a
	user will not be able to login for after he has reached the maximum password
	attempts.<br>
	<li><b>Login password expires after:</b> With this setting you can define
	a period that the login password will be valid. When the period expires the
	member will be forced to put in a new password. If you enter &quot;0&quot;
	here, the password will never expire.<br>
	<li><b>Transaction password:</b> Here you can set the use of a special
	password for transactions. You have the following options:
	<ul>
		<li><b>&quot;Not Used&quot;</b>: the transaction password is not used,
		and members can perform any transaction (if they have the autorisaties, of
		course) without first entering a transaction password.
		<li><b>&quot;Automatic&quot;</b>: If this option is selected to
		&quot;Automatic&quot; the system will generate a transaction password on
		account creation of a new member (or now, for existing members). The member
		will receive the password (only once) in his personal message inbox.
		<li><b>&quot;Manual&quot;</b>: If this is selected the transaction
		password can only be generated manually from the
		<a href="${pagePrefix}profiles#access_actions"><u>&quot;Manage
		transaction password&quot;</u></a> action in the profile page of the member. More
		information about the transaction password can be found at that page.
	</ul>
	<li><b>Transaction password length:</b> sets the length of the transaction
	password. This password always has a fixed length. (Of course, this setting has
	no effect if the transaction password is not used).
	<li><b>Max transaction password tries:</b> After this number of failed
	transactions the password will be blocked. An administrator can reset the
	password from the <a href="${pagePrefix}profiles#access_actions"><u>&quot;Manage
	transaction password&quot;</u></a> action. (Of course, this setting has no effect if
	the transaction password is not used).</li>
</ul>
Don't forget to click the &quot;submit&quot; button after you've made your
adaptions.
<hr class="help">
</span>

<span class="admin">
<a name="edit_member_group"></a>
<h3>Modify Member Group</h3>
You can edit/modify a
<a href="#member_groups"><u>member group</u></a>
's properties here. After clicking the &quot;change&quot; button, you can change
the name, the description and several categories of settings.
<br><br>Note that you cannot set autorisaties here; group properties and settings
are not the same as autorisaties. You can modify the group autorisaties by
clicking the <img border="0" src="${images}/autorisaties.gif" width="16"
	height="16"> autorisaties icon from the <a href="#manage_groups"><u>group
overview</u></a>, but you may also use the shortcut button below the window labeled
&quot;Group Permissions&quot;.<br>
<br><br>Member group settings are ordered by category. The following categories
are availible; you may click the links to get details about the fields in these
categories:
<ul>
	<li><b><a href="#group_details"><u>Group details</u></a></b> gives the main summary. 
	<li><b><a href="#group_registration_settings"><u>Registration
	settings</u></a></b> are settings defining the group behavior connected to registration of
	the member. It also contains some miscellaneous settings.
	<li><b><a href="#group_access_settings"><u>Access settings</u></a></b> are
	settings defining the group's access.
	<li><b><a href="#group_notification_settings"><u>Notification
	settings</u></a></b> is all about email notification to this group.
	<li><b><a href="#group_ad_settings"><u>Advertisement settings</u></a></b>
	are settings defining the group behavior regarding advertisements.
	<li><b><a href="#group_scheduled_payment_settings"><u>Scheduled
	payment settings</u></a></b> are settings about
	<a href="${pagePrefix}payments#scheduled"><u>scheduled payments</u></a>
	for this group.
	<li><b><a href="#group_loans_settings"><u>Loan group settings</u></a></b> are
	settings defining the group behavior connected to loans.
</ul>
<hr class="help">
</span>

<span class="admin"> <a name="group_details"></a>
<h3>Group Details</h3>
Here you can set some general group settings. The following settings are available: 
<ul>
	<li><b>Type</b> The type of group (member, broker, admin). This is defined
	at <a href="#insert_group"><u>group insertion</u></a> and cannot be changed.
	<li><b>Removed</b> This field shows if the group is a &quot;removed
	&quot;. This is also defined at <a href="#insert_group"><u>group
	insertion</u></a> and cannot be changed.
	<li><b>Name</b> The name of the group. Also defined at group insertion but
	can be changed.
	<li><b>Login page name</b> This option will only show up if you customized
	the login page for this group (in customized files window below). The
	customized (group) login page can be accessed by putting the login page name
	after the &quot;global&quot; login page with an interrogation character. The
	login page name cannot have spaces. An example would be:<br>
	http://www.yourdomain.org/cyclos?yourgrouploginpagename.<br>
	Note that it is also possible to specify a login page name per <a
		href="${pagePrefix}groups#group_filter"><u>group filter</u></a>
	<li><b>Container page url</b> This setting is used if you want to access
	Cyclos from within a website. The settings works the same as the global
	container page (see <a href="${pagePrefix}settings#local"><u>Settings -
	Local settings</u></a> but just for this group. In this field you would need to put the
	page that opens the iframe or frame-set that includes Cyclos. for example:
	http://www.yourgroupdomain.org/cycloswrapper.php<br>
	Note that it is also possible to specify a login container page per <a
		href="${pagePrefix}groups#group_filter"><u>group filter</u></a>.
	<li><b>Description</b> Here you can put the description of the group. The field 
	just serves for extra information in the group settings and is not used anywhere else in Cyclos.
	<li><b>Activate group</b> This option will only show up for groups that do not 
	have an account. In case members of this group should not be visible to other 
	members you should leave this select empty.<br>
	In some cases you would like members to show up for other users even when they
	do not have an account, and that case you should mark this option. For example
	brokers that need to do merely administrative tasks (and hence cannot trade
	themselves) or demo users that only login to have a peek of what's in the
	offering in the system, and will not be able to trade.<br>
	When chaning this setting it will be applied on all existing members and new
	members of the group.
	<br><br>
</ul>
<hr class="help">
</span>

<span class="admin"> <a name="group_registration_settings"></a>
<h3>Registration settings for group</h3>
These are settings defining the group behavior connected to registration of the
member. The settings are part of the &quot; <a href="#edit_member_group"><u>modify
member group</u></a> &quot; form. The following are available:
<ul>
	<li><b>Initial group</b>: If you want the group to be the initial group
	where users are placed right after having registered themselves via the public
	registration page, you can select this check box. There can be one or more
	initial groups in Cyclos.<br>
	You can also specify a different name for the group to show up. This is only
	shown when there are more than one initial groups. The registering user then
	has to choose from a drop down selector showing the group names as you define
	it in this field.<br>
	<br>
	<li><b>E-mail validation</b>: When this option is selected e-mail
	validation is required. After submitting the user will receive a message that
	he/she will receive a confirmation e-mail that will need to be replied in order
	for the registration to be processed.<br>
	Until registration has been confirmed and the maximum confirmation period has
	not been expired the members are in a pending status. The maxim confirmation
	period can be defined at the &quot;limits&quot; section of the local settings <a
		href="${pagePrefix}settings#local"><u>help file</u></a> The pending members
	can be view at the <a
		href="${pagePrefix}user_management#search_pending_member"><u>Pending
	members</u></a> page.<br>
	The following validation options are available:
	<ul>
		<li><b>Do not validate:</b> The e-mail is not validated. The member will
		be directly part of the initial group that is set for the public registration.
		(or, in case of a registration by admin/broker part of the group selected by
		the admin or broker).
		<li><b>Validate on public registration only:</b> The registration is only
		validated when members register at the public registration page.
		<li><b>Validate on public and broker registrations:</b> The registration
		is validated when members register at the public registration page but also
		when brokers register a member.
		<li><b>Validate on all registrations (admin / broker / public):</b> (Self
		explanatory)
	</ul>
	<br>
	<li><b>Registration Agreement</b> Here you can chose the registration
	agreement that will be shown at the registration page. You can add new
	registration agreements at the <a href="#list_registration_agreements"><u>Registration
	agreement list</u></a> page.<br>
	If a registration agreement is defined for a group all users of that group need
	to accept the agreement to be able to login to Cyclos. If the users are
	registered by an admin or broker the agreement page will show up the first time
	they login.<br>
	If users that are already registered in the system are moved to a group that
	has a registration agreement defined the user will be presented the agreement
	upon the next login (and will need to accept the agreement to be able to
	login).<br>
	When changing to a newly created registration agreement the additional option
	&quot;Force accept on next login&quot; will appear. When this option is
	selected all new users but also the existing users of the group will be
	presented the registration agreement. If not selected only newly registered
	users will have to accept the agreement. <br>
	<br><li><b>Send password by e-mail:</b> When this option is checked the user 
	will receive the password by e-mail after registration. If this option is 
	not checked the user (and broker / admin depending on the autorisaties) can 
	define the password at the registration form. 
	<br>
	<br>
	The message can be defined in <a href="${pagePrefix}settings#mail"><u>mail
	settings</u></a>. If you use this option make sure that the e-mail field is obligatory.
	This can be set in <a href="${pagePrefix}settings#local"><u>local
	settings</u></a>.<br>
	<br>
	<li><b>Max profile images per member:</b> The maximum number of images the
	member may place in his <a href="${pagePrefix}profiles"><u>profile</u></a>.<br>
	<br>
	<li><b>Expire members after</b>: membership on this group may be set to
	automatically expiration if you set this field to another value than
	&quot;0&quot;. After this time has passed since the member entered the group,
	the member will be automatically placed in another group (see next item).<br>
	<br>
	<li><b>Group after expiration</b>: if you set a expiration time for
	membership of a group at the previous item, here you must enter the group to
	which the member is moved after expiration.
</ul>
<hr class="help">
</span>

<span class="admin"> <a name="list_registration_agreements"></a>
<h3>Registration agreements</h3>
This window shows a list with registration agreements. More information about
registration agreements can be found at the modify agreement <a
	href="#group_registration_agreement"><u> help page</u></a>. <br>
You can insert a new agreement or modify an existing one. <br>
Agreements can only be deleted if there is no group that has the agreement and
when no members have accepted the agreement. <br>
<br>
<hr class="help">
</span>

<span class="admin"> <a name="registration_agreement"></a>
<h3>New / Modify registration agreement</h3>
A registration agreement is a text that can shown up at the registration page
and users who want to register MUST select a checkbox stating that they agree
with this agreement in order to be able to submit.<br>
Agreements can be bound to one or more groups. This can be configured in the <a
	href="#group_registration_settings"><u> Group registration settings</u></a> 
	<br>
When you make changes to an agreement and want also the existing users (that
already accept the agreement) to accept again you have to create a new agreement
and change it in the group settings. (you will also have to select the option
&quot;Force accept on next login&quot;)
<hr class="help">
</span>


<span class="admin"> <a name="group_access_settings"></a>
<h3>Access Settings of group</h3>
These are settings defining the group's access behavior. The settings are part
of the &quot; <a href="#edit_member_group"><u>modify member group</u></a> &quot;
form. The following are available:
<ul>
	<li><b>Accessible Channels:</b> Channels are the means of access to
	cyclos. Here you can select by which means of access cyclos can be accessed by
	this group. See also <a href="${pagePrefix}settings#channels"><u>the
	channels section</u></a> of this help. One or more of the following options can be
	selected.
	<ul>
		<li><b>Main web access:</b> Accessing cyclos via the normal web page in a
		browser.
		<li><b>Posweb payments:</b> <b>P</b>oint <b>O</b>f <b>S</b>ale access for
		shops allowing payments via cyclos. If this option is enabled it means that
		the payer (consumer) group needs this access settting.
		<li><b>SMS:</b> This module is under developement.
		<li><b>WAP 1 and WAP2 access:</b> access to cyclos via mobile phones.
		<li><b>Webshop payments:</b> access via a &quot;pay by cyclos&quot;
		button in a web shop.
	</ul>
	<br>
	<br>
	<li><b>Default channels:</b> Each member of this group can set his
	personal preferences for channels: all items which are selected by the previous
	item (&quot;accessible channels&quot;) can be turned of by the user himself via
	his personal pages. If you would check for example the &quot;WAP 1&quot; in the
	&quot;default member access&quot; drop down, it means that for all members in
	this group, the &quot;WAP 1&quot; channel is selected and checked by default in
	the personal settings pages of these members. However, after that, each member
	may decide to turn this setting off again. <br>
	<br>
	<li><b>PIN length:</b> For some payment channels like SMS and Webshop the
	use of a PIN (number only) can be set. In this option you can define the max
	and min lenght of the PIN. s<br>
	Note that in order for this option to show up a channel that uses pin must be
	selected in &quot;accessible channels&quot;. When adding the first accessible
	channel with PIN you will have to save the group settings first before the PIN
	length option will show up. <br>
	<br>
	<li><b>Password length:</b> sets the minimum and maximum length of the
	password which the user needs for logging in.<br>
	<br>
	<li><b>Password Policy:</b> You can choose from three options; the meaning
	of those options are quite obvious.<br>
	<br>
	<li><b>Maximum password tries:</b> When the user reaches the amount of
	maximimum attempts the user cannot login anymore until the deactivation time
	has passed (see next setting).<br>
	<br>
	<li><b>Deactivation time after max password tries:</b> This is the time a
	user will not be able to login for after he has reached the maximum password
	attempts.<br>
	<br>
	<li><b>Login password expires after:</b> With this setting you can define
	a period that the login password will be valid. When the period expires the
	member will be forced to put in a new password. If you enter &quot;0&quot;
	here, the password will never expire.<br>
	<br>
	<li><b>Transaction password:</b> Here you can set the use of a special
	password for transactions. You have the following options:
	<ul>
		<li><b>&quot;Not Used&quot;</b>: the transaction password is not used,
		and members can perform any transaction (if they have the autorisaties, of
		course) without first entering a transaction password.
		<li><b>&quot;Automatic&quot;</b>: If this option is selected to
		&quot;Automatic&quot; the system will generate a transaction password on
		account creation of a new member (or now, for existing members). The member
		will receive the password (only once) in his personal message inbox.
		<li><b>&quot;Manual&quot;</b>: If this is selected the transaction
		password can only be generated manually from the <a
			href="${pagePrefix}profiles#access_actions"><u>&quot;Manage
		transaction password&quot;</u></a> action in the profile page of the member. More
		information about the transaction password can be found at that page.
	</ul>
	<br>
	<br>
	<li><b>Transaction password length:</b> sets the length of the transaction
	password. This password always has a fixed length.<br>
	<br>
	<li><b>Max transaction password tries:</b> After this number of failed
	transactions the password will be blocked. An administrator can reset the
	password from the <a href="${pagePrefix}profiles#access_actions"><u>&quot;Manage
	transaction password&quot;</u></a> action.
</ul>
<hr class="help">
</span>

<span class="admin"> <a name="group_notification_settings"></a>
<h3>Notification settings</h3>
These are settings about personal notifications that members of a group can
enable.
<ul>
	<li><b>Default messages sent by e-mail:</b> These are the notification
	messages marked by default upon member creation (for this group)<br>
	If you check an item in this drop down, it means that it will be checked by
	default in the personal preference page of each member of this group. However,
	the member himself may choose to uncheck and overrule this notification.
	<li><b>SMS allowed messages:</b> These are the messages that will be
	available for SMS notification. <br>
	Note: This option is only available if an SMS channel is active (Local settings
	- Channels)
	<li><b>Default messages sent by SMS:</b> These are the notification
	messages marked by default upon member creation (for this group)
	If you check an item in this drop down, it means that it will be checked by
	default in the personal preference page of each member of this group. However,
	the member himself may choose to uncheck and overrule this notification. <br>
	<li><b>SMS charge transfer type:</b> This is the transaction type that
	will be used to charge for outgoing SMS (from system to member, e.g.
	notifications)
	<li><b>SMS charge amount:</b> This is the amount that will be charged for
	each outgoing SMS
</ul>
Most list items are pretty obvious and self explanatory. This help only picks
out items which might need further clarification. <br>
The <a href="${pagePrefix}notifications#notification_preferences"><u>Notifications
help</u></a> will give more information about the specific options.
<hr class="help">
</span>

<span class="admin">
<a name="group_ad_settings"></a>
<h3>Advertisement settings</h3>
These are settings defining the group behavior regarding advertisements. The
settings are part of the &quot;
<a href="#edit_member_group"><u>modify member group</u></a>
&quot; form. The following are available:
<ul>
	<li><b>Max ads per member:</b> Self explanatory.<br>
	<br>
	<li><b>Enable permanent advertisements</b>: when checked, this allows <a
		href="${pagePrefix}advertisements#ad_modify"><u>permanent &quot;not expirable&quot;
	advertisements</u></a> for this member group.<br>
	<br>
	<li><b>Default ad publication time:</b> The default period for a new
	advertisement. The member can always change the period of activation for each
	ad.<br>
	<br>
	<li><b>Max ad publication time:</b> The maximum publication period a
	member can give to an advertisement.<br>
	<br>
	<li><b>Advertisement external publication</b>: This allows external
	publications of advertisements, meaning that the ads of this group can be
	published automatically on the organization's website, thus being viewable by
	non-members. All options are pretty self explanatory. &quot;Allow Choice&quot;
	means that the member himself may choose if his advertisements can be used for
	external publication.<br>
	<br>
	<li><b>Max images per ad:</b> The maximum number of images a member may
	place at one ad.<br>
	<br>
	<li><b>Max ad description size</b>: the maximum size of the ad description
	in bytes/characters.
</ul>
<hr class="help">
</span>

<a name="group_scheduled_payment_settings"></a>
<h3>Scheduled Payment settings of group</h3>
These are settings connected to <a href="${pagePrefix}payments#scheduled"><u>
scheduled payments</u></a>. The settings are part of the &quot;
<a href="#edit_member_group"><u>modify member group</u></a>
&quot; form.<br><br>
All of these settings refer to the <a href="${pagePrefix}payments#pay_scheduled"><u>
multiple scheduled payments</u></a>. 
The following are available:
<ul>
	<li><b>Max. Scheduling payments:</b> The maximum number of terms (or partial payments) 
	in which a payment can be divided and scheduled. For example: 10 partial payments, 
	one every two weeks.
	<li><b>Max. scheduling period:</b> The maximum total period between today and the last payment.
</ul>
<hr class="help">


<span class="admin">
<a name="group_loans_settings"></a>
<h3>Loans settings of group</h3>
These are settings connected to <a href="${pagePrefix}loans"><u>loans</u></a>.
The settings are part of the &quot;
<a href="#edit_member_group"><u>modify member group</u></a>
&quot; form. The following are available:
<ul>
	<li><b>View loans given to loan group</b>: The member may view <a
		href="${pagePrefix}loans"><u>loans</u></a> given to his <a
		href="${pagePrefix}loan_groups"><u>loan group</u></a>.
	<li><b>Loan repayment permitted by any loan group member:</b> If this
	option is selected any member of the loan group can repay a loan. If the option
	is not selected only the member that is marked as repsonsible person can repay
	the loan.
</ul>
<hr class="help">
</span>

<span class="admin">
<a name="group_brokering_settings"></a>
<h3>Brokering settings of broker group</h3>
These are settings connected to the
<a href="${pagePrefix}brokering"><u>brokering</u></a>
functions of a broker. The settings are part of the &quot;
<a href="#edit_member_group"><u>modify member group</u></a> &quot; form. The
following are available:
<ul>
	<li><b>Possible initial groups:</b> Here you can choose the initial group
	of the members that are registered by a broker. This can be different according
	to the use of brokers. It could be an inactive group like pending activation
	that need activation by an admin or an active group.<br>
	If you select one or more groups the broker will be presented a list with the
	groups where the broker can select from.<br>
	Note that brokers will NOT be able to register any members, if you do not
	select one or more groups here.
</ul>
<hr class="help">
</span>

<span class="admin">
<a name="edit_broker_group"></a>
<h3>Modify Broker Group</h3>
You can edit a
<a href="#broker_groups"><u>broker group</u></a>
's properties here. After clicking the &quot;change&quot; button, you can change
the name, the description and several categories of &quot;settings&quot;.
<br>
<br><br>Note that you cannot set autorisaties here; group properties and settings
are not the same as autorisaties. You can modify the group autorisaties by
clicking the <img border="0" src="${images}/autorisaties.gif" width="16"
	height="16"> autorisaties icon from the <a href="#manage_groups"><u>group
overview</u></a>, but you may also use the shortcut button below the window labeled
&quot;Group Permissions&quot;.<br>
<br><br>Broker group settings are ordered by category. The following categories
are availible; you may click the links to get details about the fields in these
categories:
<ul>
	<li><b><a href="#group_details"><u>Group details</u></a></b> gives the
	main summary. Here you can change the displayed &quot;name&quot; and the
	&quot;description&quot; of the group (of course, after you clicked the
	&quot;change&quot; button below the box.
	<li><b><a href="#group_registration_settings"><u>Registration
	settings</u></a></b> are settings defining the group behavior connected to registration of
	the member. It also contains some miscellaneous settings.
	<li><b><a href="#group_access_settings"><u>Access settings</u></a></b> are
	settings defining the group's access.
	<li><b><a href="#group_notification_settings"><u>Notification
	settings</u></a></b> is all about email notification to this group.
	<li><b><a href="#group_brokering_settings"><u>Brokering
	settings</u></a></b> are settings concerning the brokering of other members.
	<li><b><a href="#group_ad_settings"><u>Advertisement settings</u></a></b>
	are settings defining the group behavior regarding advertisements.
	<li><b><a href="#group_loans_settings"><u>Loans settings</u></a></b> are
	settings defining the group behavior connected to loans.
</ul>
<hr class="help">
</span>

<span class="admin">
<a name="insert_group"></a>
<h3>Insert New Group</h3>
This window allows you to create a new group.
<br>
You have the following options
<ul>
	<li><b>Type:</b> The <a href="#group_categories"><u>type</u></a> of group.
	This can be &quot;member&quot;, &quot;broker&quot; or &quot;admin&quot;.
	<li><b>Removed:</b> When a group is marked as &quot;removed&quot; it means
	that the Members in this group have really left the system. Once in a
	&quot;removed&quot; group one cannot get back in any other group. The data will
	still be in the Database and viewable by administrators but it serves as a
	backup function only.
	<li><b>Name:</b> Name of the group as it will be displayed to users.
	<li><b>Description:</b> Description of the group.
	<li><b>Copy settings from:</b> You can copy all the settings for this new
	group from an existing group via this drop down. Both settings and autorisaties
	are copied.
</ul>
After having added the information, you should of course click the
&quot;Sumbit&quot; button to save the changes.
<br>
<b>Important:</b>
After creating the new member group, you should set the autorisaties and
properties via group list page.
<br><br>Note: After creating a group it is not possible to change the type and
the status of the group.
<hr class="help">
</span>

<span class="admin">
<a name="manage_group_accounts"></a>
<h3>Manage Group Accounts</h3>
<a href="#member_groups"><u>Member groups</u></a>
(and
<a href="#broker_groups"><u>broker groups</u></a>
) can have various associated
<a href="${pagePrefix}account_management#accounts"><u>accounts</u></a>
. The list below shows the member account types associated with the group. The
accounts in the list will show up in the personal accounts overview for the
members of this group. An account type may be shared by different groups,
meaning that different groups can have the same associated account type. In such
a case, you can still define different account settings and autorisaties for each
group.
<br><br>You can modify account settings by clicking the <img border="0"
	src="${images}/edit.gif" width="16" height="16">&nbsp; edit icon.<br>
<b>NOTE</b>: Beware that to be able to make and receive payments for this
account just creating or modifying it is not enough. You will also need to set
the <a href="#autorisaties"><u>autorisaties</u></a> for the group, otherwise the
account cannot be used.
<br><br>You can also detach an account type for this group by clicking the <img
	border="0" src="${images}/delete.gif" width="16" height="16">&nbsp;
delete icon. If you &quot;detache&quot; an account from a group it means that 
the account will remain visible for the users and they can see the transactions 
of the account but the account will be in an &quot;inactive&quot; status. It 
cannot be used for payments anymore. 
<br><br>You may also associate a new account type to this group, by clicking the
&quot;Associate New Account&quot; submit button below this box. This will bring
you to the <a href="#insert_group_account"><u>&quot;insert group
account&quot;</u></a> window.
<hr class="help">
</span>

<span class="admin">
<a name="edit_group_account"></a>
<a name="insert_group_account"></a>
<h3>Modify account settings / Add an account to group</h3>
In this page you can define the account settings for an account type of the
selected group. This is possible for an existing associated account type, or for
an account type you want to associate with this group for the first time.
<br>
When modifying an existing account, you should click &quot;change&quot; in order
to be able to make modifications; when associating a new account type you can
directly enter the fields. When done, click &quot;submit&quot; to save the
changes.
<br>
The following settings can be defined:
<ul>
	<li><b>Account:</b> The account type you want to associate. It cannot be
	changed once the account type is created, so it is only available for new
	account types. You can only associate an account type which has already been created; if
	you haven't created the appropriate account, you should do this first
	before you can associate it.
	<li><b>Is default:</b> Because it is possible to create more than one
	Member account the system will need to know which account will be the default
	one. This is for two reasons. Firstly the system can be set to send mails
	informing the member on the balance of their default account. Secondly,
	payments done by the mobile phone will use the default account.
	<li><b>Require transaction password:</b> If this option is checked the
	member will be asked to fill in the transaction password. <br>
	Note: This option can only be activated if the transaction password is enabled
	for the group (in group settings). Otherwise, it will not be visible.
	<li><b>Lower credit limit:</b> The default credit limit for the account.
	The amount will be typically have a zero or a negative amount. Changing this
	may affect existing accounts, depending on the &quot;update credit limit for
	existing members&quot; checkbox, a bit lower on this page (see below). Besides
	the default credit limit which is valid for the complete group, it is also
	possible to set a <a href="${pagePrefix}account_management#credit_limit"><u>
	credit limit</u></a> for individual members. The individual setting 
	will overwrite the group credit limit.
	<li><b>Upper credit limit:</b> If you define a upper credit limit it means
	that the account will not receive payments when it reached this limit. The
	payer will receive a message informing that the payment has failed.
	<li><b>Update credit limit for existing members:</b> If you changed the
	credit limit(s) of an existing account type association, you can define with
	this select box if the credit limits for the existing members (either
	individual or group credit limits) will be changed. If you do not select this
	select box the new credit limit will only be applied to new members of this
	group. This box is not visible if you associate this account type for the first
	type.
	<li><b>Initial credit:</b> This is the amount that the new members will
	receive automatically. This can be zero or a positive amount.
	<li><b>Initial credit transaction type:</b> If an initial credit other
	than 0 is set in the edit box just above this one, you must specify the kind of
	<a href="${pagePrefix}account_management#transaction_types"><u>transfer type</u></a> for this
	credit at this drop down box. As the transfer type has an origin and
	destination account, it allows you to specify from which account the initial
	credit amount is taken. The default system database comes with a transfer type
	&quot;Initial credit&quot; from the Debit account to the Member account, but of
	course you are free to use another transfer type.
	<li><b>Low units alert:</b> If a member account reaches this amount a
	personal alert message (see below) will be sent. You can enter only a positive
	amount here. The amount you enter is relative to the credit limit. This means
	for example that if the credit limit is -200 and the Low units setting is 10,
	the member will receive the low unit alert when the balance reaches -190 Units.
	If the credit limit is zero and the Low units setting is 10 the member will
	receive the low unit alert when the balance reaches 10 (positive) Units.
	<li><b>Low units message:</b> The (personal) message a member receives
	when the low Units amount is reached.
</ul>
<hr class="help">
</span>

<span class="admin">
<a name="manage_group_customized_files"></a>
<h3>Manage Customized Files</h3>
Cyclos is highly customizable. You can not only define your own styles and texts
to be shown (via
<a href="${pagePrefix}content_management"><u>&quot;Content
Management&quot;</u></a>
, but you can also specify this at group level. This means that each group can
have it's own specific style and its own specific texts to be shown in the
software. These are called &quot;Customized Files&quot;.
<br><br>This window gives an overview of customized files for this group. If no
customized files are defined yet, then a message is shown that none are
available. You have the following options:
<ul>
	<li><b>Insert</b> a new customized file via the &quot;customize new
	file&quot; button. A customized group file will override the system customized
	file (which you can set via <a href="${pagePrefix}content_management"><u>&quot;Content
	Management&quot;</u></a> ).
	<li><b>Modify</b> an existing customized file via the <img border="0"
		src="${images}/edit.gif" width="16" height="16">&nbsp;edit icon.
	<li><b>View</b> how the result will look for a member of the group via the
	<img border="0" src="${images}/view.gif" width="16" height="16">&nbsp;view
	icon.
	<li><b>Delete</b> a customized file definition via the <img border="0"
		src="${images}/delete.gif" width="16" height="16">&nbsp;delete icon.
	When it is deleted the default customized file for the system will be used, if
	available.
</ul>
<hr class="help">
</span>

<span class="admin">
<a name="customize_group_file"></a>
<h3>Modify customized file for ... / Customize new file for...</h3>
In this page you can customize a static file or css file. It works the same as
in
<a href="${pagePrefix}content_management"><u>Content Management</u></a>
, only this time you specify it only for this group.
<br>
For specific instructions on what to enter, see
<a href="${pagePrefix}content_management#edit_customized_file"><u>here</u></a>
.
<br><br>Note: When you want to include pictures in the static files they will
need to be <a href="${pagePrefix}content_management#custom_images"><u>uploaded</u></a>.
<hr class="help">
</span>

<h2>Permissions</h2>

<br><br>
<hr>

<a name="autorisaties"></a>
<br><br>
In cyclos, autorisaties are organized per group. For each group you can manage
the permission to access functions in the Cyclos software. Normally, sections of
cyclos to which a member group doesn't have access will be invisible for these
member groups.
<br>
<span class="admin">
The system is very flexible. For example, it allows you to create different
administrator groups, where each administrator group has autorisaties to manage a
specific member group. For complexer systems it is possible to create extra
&quot;vertical&quot; administration to define specific task realms like account
administrator, system administrator, bookkeeping administrator, administrators
of vouchers/scrip, etc..

You can modify group autorisaties via &quot;Menu: Groups > Permission
Groups&quot;, and then click the autorisaties icon (
<img border="0" src="${images}/autorisaties.gif" width="16" height="16">
&nbsp;).
</span>
<hr class="help">

<a name="manage_group_autorisaties_basic"></a>
<h3>Basic Permissions of Group</h3>
In this window you can set the basic autorisaties. Basic autorisaties are
autorisaties that are the same for all
<a href="#group_categories"><u>group types</u></a>
(member, admin, broker).
<br>
These basic autorisaties do not affect other functions, e.g. if a member cannot
login, he still might be able to receive payments.
<br>
The following autorisaties can be set:
<ul>
	<li><b>login</b>: if this is not checked, members of this group cannot
	login.
	<li><b>invite message</b>: if checked, members of this group see a window
	box in there main page (after logging in), with which they can invite a friend
	to <a href="${pagePrefix}home#home_invite"><u>try out your organization</u></a>.
	
	<li><b>Show quick access</b>: This option (only available for member and
	broker groups) will show a window at the home page with quick access icons to
	most used functions.
</ul>
<hr class="help">

<span class="admin">
<a name="manage_group_autorisaties_admin_system"></a>
<h3>System administration autorisaties</h3>
In this window you can set the autorisaties for the system functions of an
administrator group. So this autorisaties window does not contain any autorisaties
related to member groups. In order to make changes you have to scroll down to
the bottom and select the &quot;change&quot; button, which will allow you to
make changes. Changes are saved after clicking &quot;submit&quot;, also
completely at the bottom of the page.
<br><br>The autorisaties structure is quite straightforward. Most functions have
two autorisaties,&quot;View&quot; and &quot;Manage&quot;. <br>
If View is not selected, the item won't show up in the menu for administrators
of the group.<br>
The &quot;Manage&quot; option gives the permission to create, edit and delete. <br>
<br>
The following autorisaties are available (you may want to use the links for more
information):
<ul>
	<li><b>Account Fees:</b> The view autorisaties allows the admin to go to the <a
		href="${pagePrefix}account_management#account_fee_overview"><u> Account Fee
	overview</u></a> page.<br>
	The &quot;Charge&quot; autorisaties allows to charge a &quot;manual&quot; or failed
	account fee in the  <a
		href="${pagePrefix}account_management#account_fee_history"><u> Account Fee
	history</u></a> page.<br>
	<br>
	<li>The <b>Accounts:</b> section allows you to set the following
	autorisaties:
	<ul>
		<li><b><a href="${pagePrefix}account_management#account_search"><u>Manage
		Accounts</u></a></b>
		Permissions Create and modify the account structure.
		<li><b>View Account Management:</b> the same as previous, only the admin
		cannot make changes.
		<li><b>View System Accounts Information:</b> Here you can set which system
		accounts summaries are viewable by the admin.
		<li><b>View <a href="${pagePrefix}payments#geautoriseerd"><u>geautoriseerd
		payments</u></a></b>
		View geautoriseerd payments menu item.
		<li><b>View <a href="${pagePrefix}payments#scheduled"><u>scheduled
		payments</u></a></b>
		View scheduled payments menu item.
	</ul>
	<br>
	<br>
	<li><b>Ad Categories:</b> sets autorisaties for <a
		href="${pagePrefix}advertisements#categories"><u> ad categories</u></a>.<br>
	<br>
	<li><b>Admin groups:</b>
	<ul>
		<li>If <b>View</b> is selected, the admin can see the different admin
		groups in his <a href="#manage_groups"><u>group autorisaties window</u></a>.
		<li><b>Manage Customized Files:</b> allows the admin to manage <a
			href="#manage_group_customized_files"><u>customized files</u></a> for other
		admins.
	</ul>
	<br>
	<br>
	<li><b><a href="${pagePrefix}alerts_logs#system_alerts"><u>Alerts</u></a></b>
	are warnings from the system on special occasions.<br>
	<br>
	<li><b><a href="${pagePrefix}settings#channels"><u>Channels</u></a></b> define by
	which means the user accesses cyclos (for example: web, mobile phone, etc). <br>
	<br>
	<li><b><a href="${pagePrefix}account_management#currencies"><u>Currencies</u></a>:</b>
	Allows an admin to view and/or manage different currencies </a>in cyclos.<br>
	<br>
	<li><b><a href="${pagePrefix}custom_fields"><u>Customd fields</u></a></b> Allows
	an admin to manage the custom fields. <br>
	<br>
	<li><b><a href="${pagePrefix}content_management#custom_images"><u>Customized
	images</u></a></b> Allows an admin to manage the custom images. This affects the
	visibility of the following menu items under the &quot;Content Management&quot; main menu:
	<ul>
		<li>system images
		<li>custom images
		<li>style sheet images
	</ul>
	<br>
	<br>
		<li><b><a href="${pagePrefix}content_management"><u>
	Customized files</u></a> :</b> Allows the admin to set customized files on a system wide
	level (as oposed to customized file per group). 
	This affects the following menu items under the &quot;Content Management&quot; main menu:
	<ul>
		<li>static files
		<li>help files
		<li>CSS files
		<li>Application pages
	</ul>
	<br><br>
	
	<li><b><a href="${pagePrefix}alerts_logs#error_log"><u>Error
	log</u></a></b> View and Manage the error logs.<br>
	<br>
	<li><b><a href="${pagePrefix}bookkeeping"><u>External accounts
	(bookkeeping)</u></a>:</b> The manage permission allows an admin to configure
	the module like creating and modifying external accounts and defining fields
	and transaction types<br>
	The view permission allow to view the external account configuration.<br>
	The details permission allows to view the payments but does not allow to
	perform any action on them. <br>
	The other autorisaties (process, check and manage) are operational autorisaties
	for external payments;. <br><br>
	
	<li><b><a href="#group_filters"><u>Group Filters</u></a></b> Allow you to
	bundle a set of groups and give that set a specific name. In this way you can
	create a sort of &quot;super groups&quot;, which you can use for several
	functions in cyclos.<br>
	Of course you can choose &quot;manage&quot; and &quot;view&quot; to set the
	autorisaties, but you can also choose &quot;manage customized files&quot;. This
	would allow an administrator to set customized files on a group filter, so on a
	set of groups at once. <br>
	<br>
	
	<li><b><a href="${pagePrefix}groups"><u>Permission Groups:</u></a></b>
	allows to <a href="#manage_groups"><u>manage</u></a> different <a
		href="#group_categories"><u> group categories</u></a>.<br>
	<br>
	
	<li><b>Guarantee types: </b> In the guarantee system, you can define
	different <a href="${pagePrefix}guarantees"><u>types of
	guarantees</u></a>. <br>
	<br>
	
	<li><b><a href="${pagePrefix}loan_groups"><u>Loan Groups</u></a></b> -
	View and create loangroups. Loangroups are groups members that can receive and
	repay loans in a micro-credit way. <br>
	<br>
	<li><b><a href="${pagePrefix}member_records"><u>Member record
	types:</u></a></b> Member records allow you to define information which will be gathered on
	the members. This autorisaties allows the admin to create/define such a member
	record type.<br>
	<br>
	<li><b>Message Categories:</b> allows the admin to view and/or manage the
	different categories for the internal <a href="${pagePrefix}messages"><u>messaging</u></a>
	system.<br>
	<br>
	<li><b>System Payments:</b> Here you can assign autorisaties for payments
	from <a href="${pagePrefix}account_management#standard_accounts"><u>system
	accounts</u></a>
	<ul>
		<li><b>System Payment:</b> The admin can perform system payments
		belonging to the selected <a href="${pagePrefix}account_management#transaction_types"><u>transfer
		types</u></a>.
		<li><b>Authorize:</b> The permission to <a
			href="${pagePrefix}payments#geautoriseerd"><u>authorize </u></a>system
		payments.
		<li><b>Cancel:</b> The permission to cancel a <a
			href="${pagePrefix}payments#scheduled"><u> scheduled payments</u></a>
		<li><b>Chargeback payment:</b> An administrator with this autorisaties can
		&quot;Charge back&quot; a payment what means that an oposite payment will be
		done with the same amount. In case the payment generated other transactions
		(e.g. fees and loans) all transactions will be charged back.<br>
		You can define the maximum time a transaction type can be charged back in <a
			href="${pagePrefix}settings#local"><u>Local settings</u></a>
	</ul>
	<br> 
	<br>
	<li><b>Reports:</b> Each item corresponds with a main menu item:
	<ul>
		<li><b><a href="${pagePrefix}reports#current_state"><u>Current
		state</u></a></b>
		<li><b><a href="${pagePrefix}reports#member_lists"><u>Member
		lists</u></a></b>
		<li><b>Sent SMS messages:</b> The system may sent several SMS messages on
		various occasions, depending on the configuration. This permission gives the
		admin access to the reports giving an overview of SMS messages sent.
		<li><b><a href="${pagePrefix}statistics"><u>Statistics</u></a></b> gives
		access to the statistics module.
	</ul>	
	<br>
	<br>
	<li><b><a href="${pagePrefix}settings#web_services_clients"><u>Web
	Services clients</u></a></b> Define access levels for external software to
	connect to the Cyclos webservices. <br>
	<br>
	<li><b>Settings:</b> This will give the admin access to the settings menu.
	<ul>
		<li><b><a href="${pagePrefix}settings#local"><u>
		Manage local settings</u></a></b>
		<li><b><a href="${pagePrefix}settings#alerts"><u>Manage
		alert settings</u></a></b>
		<li><b><a href="${pagePrefix}settings#access"><u>Manage
		access settings</u></a></b>
		<li><b><a href="${pagePrefix}settings#mail"><u>
		Manage mail settings</u></a></b>
		<li><b><a href="${pagePrefix}settings#log"><u>
		Manage log settings</u></a></b>
		<li><b><a href="${pagePrefix}settings#channels"><u>
		Manage channels</u></a></b>
		<li><b><a href="${pagePrefix}settings#web_services_clients"><u>
		Manage webservices clients</u></a></b>
		<li><b><a href="${pagePrefix}settings#import_export"><u>
		Export/import settings</u></a></b>
	</ul>
	<br>
	<br>
	<li><b>System status:</b> allows the admin to see the system status:
	<ul>
		<li><b>View system status:</b> when selected, the admin will see the <a
			href="${pagePrefix}home#home_status"><u>view system status</u></a> window in
		his home page.
		<li><b>View connected admins:</b> select here the <a href="#admin_groups"><u>
		admin groups</u></a> the admin can view in the <a
			href="${pagePrefix}user_management#connected_users"><u> Connected
		users</u></a> window.
		<li><b>View connected members:</b> Works the same. There is one checkbox
		for all <a href="#member_groups"><u>member groups</u></a>. The admin will only
		view connected members of group he/she has view autorisaties for.
		<li><b>View connected brokers:</b> Works the same. The admin will only
		view connected brokers of <a href="#broker_groups"><u>broker groups</u></a>
		he/she has view autorisaties for.
		<li><b>View connected operators:</b> Works the same. It will show the operator
		and the member it belongs to.
	</ul>
	<br>
	<li><b>Themes:</b> To manage <a
		href="${pagePrefix}content_management#themes"><u> themes</u></a> under
	the <a href="${pagePrefix}content_management"><u>content management</u></a>
	menu.<br>
	<br>
	<li><b>Translation:</b> allows the access to the <a
		href="${pagePrefix}translation"><u> Translation</u></a> main menu, where
	translations in your language can be viewed/managed.
</ul>
<hr class="help">
</span>

<span class="admin">
<a name="manage_group_autorisaties_admin_member"></a>
<h3>Member administration autorisaties</h3>
In this window you can set the autorisaties for the member functions of an
administrator group. These autorisaties would typically apply to an &quot;Account
administrator&quot; group. The autorisaties structure is straightforward. Most
functions have &quot;View&quot; and &quot;Manage&quot; permissons and sometimes
extra specific autorisaties.
<br>
If View is not selected the function will not show up in the menu or as a member
action button (if the administator would type in the location directly in the
browser the under laying permission structure would present a &quot;no
permission&quot; page).
<br>
The &quot;Manage&quot; option gives permission to create, edit and delete.
<br><br>In the first select box (&quot;Manages groups:&quot;) you can select one
or more member groups. This means that an admin of this group can only manage
members from those groups and only see information concerning those groups. This
means that any other information related to members like loans, reports, alerts,
logged users etc will only show results that are related to the groups that are
selected.<br>
This option allows you to let specific member groups be administrated by
specific account admins.
<br><br>The following autorisaties can be set - you may want to use the links for
more information:
<ul>
	<li><b>Access:</b> autorisaties to control the access by the members of the
	member group. It contains the following sub-items:
	<ul>
		<li><b>Change login password:</b> allows the admin to change the
		member's login password from the member's profile (in member action
		&quot;manage passwords&quot;) .<br>
		Note that if this permission is not checked an admin can still define
		a temporary password upon member registration (the member will be
		forced to change the password at first login). But this is only in
		case the <a href="#edit_member_group"><u>group setting</u></a>
		&quot;Send password by e-mail&quot; is not checked.<br>
		If the group setting &quot;Send password by e-mail&quot; is checked
		(in the member group setting) the admin cannot define the password at
		the registration. In this case the member will receive automatically a temporary
		password by mail that need to be changed at first login).<br>
		If both the group setting &quot;Send password by e-mail&quot; and
		&quot;Change login password&quot; are checked the admin can chose at
		the registration to define a definitive (not temporary) password or to
		force the user changing the password on first login.
		<li><b>Reset login password:</b> allows the admin to reset the members
		password, which (depending on the configuration) generally means that it will
		be automatically regenerated and send by mail.
		<li><b>Manage <a href="${pagePrefix}passwords#transaction_password"><u>transaction
		password</u></a>:</b> allows to manage the special password which can be set for
		transactions.
		<li><b>Disconnect logged member:</b> allows the admin to immediately
		disconnect a member who is using the system now.
		
		<li><b>Disconnect logged operator:</b> allows the admin to immediately
		disconnect an <a href="${pagePrefix}operators"><u>operator</u></a> who is
		using the system now.
		
		<li><b>Reactivate disabled members (because of failed login
		attempts):</b> If a member forgot his password and tries several times to log in
		with a wrong password, he will be temporary disabled. If this permission is
		set, an administrator can allow such a member to immediately login again. In
		such a case, a button appears in the member profile &quot;allow member to
		login now&quot;.
		<li><b>Change pin:</b> Allows the admin to change the <a
			href="${pagePrefix}passwords#pin"><u>pin number</u></a>, which is
		a numeric password for access to certain <a href="${pagePrefix}settings#channels"><u>channels</u></a>,
		such as webshops.
		<li><b>Change channels access:</b> change the access methods to channels,
		like the web, via mobile phones, etc.
		<li><b>Unblock PIN:</b>Unblock the PIN when the member exceeded the max attempts.
	</ul>
	<br>
	<br>
	<li><b>Accounts:</b> autorisaties concerning the autorisaties for the admin
	group to manage or view the member accounts. It contains the following
	sub-items.
	<ul>
		<li><b>View information:</b> Allows the admin to view the account
		information (balance, transaction overview, etc).
		<li><b>View geautoriseerd payments:</b> Allows the admin to view <a
			href="${pagePrefix}payments#geautoriseerd"><u>View geautoriseerd payments</u></a>.
		
		<li><b>View scheduled payments:</b> Allows the admin to view <a
			href="${pagePrefix}payments#scheduled"><u>scheduled payments</u></a>.
		<li><b>Set credit limit:</b> Allows the admin to set the individual credit limits;
		this will overrule the credit limits set for the group.
	</ul>
	<br>
	<br>
	
	<li><b>Products and Services:</b> These autorisaties allow the admin of
	this admin group to view and/or manage the advertisements of a member.<br>
	There is also a permission that allows to <a
		href="${pagePrefix}advertisements#import_ads"><u>import advertisements</u></a>.
	<br>
	<br>
	<li><b>Brokerings:</b> Permissions concerning the brokering of members by
	<a href="${pagePrefix}brokering"> <u>brokers</u></a>.
	<ul>
		<li><b>Change Broker:</b> allows the admin to change the broker of the
		member.
		<li><b>View Member list (as Broker):</b> allows the admin to view the
		members of a broker.
		<li><b>View Loan details in members list (print):</b> allows to view the
		loans in the list of member which are managed or arranged via the broker.
		<li><b>Manage </b><a href="${pagePrefix}brokering#commission">
		<u>commissions</u></a>: manage the commissions a broker may get.
	</ul>
	<br>
	<br>
	<li><b>Bulk Actions:</b> With <a href="${pagePrefix}user_management#bulk_actions"><u>
	bulk actions</u></a> you can perform a specific
	action on a set of members.
	<ul>
		<li><b>Change group:</b> Change the group of a set of members.
		<li><b>Change broker:</b> Change the broker of a set of members. You may
		want to do this when one broker quits his brokering job, and his members need
		another broker.
	</ul>
	<br>
	<br>
	<li><b>Documents:</b> allows the admin to manage and view member <a
		href="${pagePrefix}documents"> <u>documents</u></a>. The following sub-items
	apply:
	<ul>
		<li><b>View Documents:</b> Here you can select the documents the admin
		can view. If no documents are available the dropdown will be empty.
		<li><b>View <a href="${pagePrefix}documents"><u>dynamic</u></a>
		documents</b>
		<li><b>View <a href="${pagePrefix}documents"><u>static</u></a>
		documents</b>
		<li><b>View <a href="${pagePrefix}documents#member_document"><u>member</u></a>
		documents</b>
	</ul>
	<br>
	<br>
	<li><b>Member Groups:</b>
	<ul>
		<li><b>View:</b>if this is set, the <a href="#manage_groups"><u>overview</u></a>
		of member groups can be viewed by the admin.
		<li><b>Manage account settings:</b> allows the admin to <a
			href="#manage_group_accounts"><u>manage the group accounts settings</u></a>. If this
		is NOT set, the admin can view the settings (depending on the previous
		permission item), but not change them.
		<li><b>Manage customized files:</b> allows the admin to manage <a
			href="${pagePrefix}content_management#customized_files"><u>customized files</u></a> for this
		group.
	</ul>
	<br>
	<br>
	<li><b>Guarantees:</b> Various autorisaties concerning the <a
		href="${pagePrefix}guarantees"><u> guarantees</u></a> system. Contains the
	following sub-items:
	<ul>
		<%-- TO DO --%>
		<li><b>View payment obligations:</b>
		<li><b>View certifications:</b>
		<li><b>View guarantees:</b>
		<li><b>Process guarantee loans:</b>
		<li><b>Register guarantees:</b>
		<li><b>Cancel payment obligations as member:</b>
		<li><b>Cancel certifications as member:</b>
		<li><b>Cancel guarantees as member:</b>
		<li><b>Accept guarantees:</b>
	</ul>
	<br>
	<br>
	<li><b>Member Invoices:</b> Various settings to allow access to member <a
		href="${pagePrefix}invoices"> <u>invoices</u></a> by the admin. All of these
	items are self-explanative enough, so we won't provide here a subitem
	description. <br>
	<br>
	<li><b><a href="${pagePrefix}loan_groups"><u>Loan Group
	Membership</u></a></b> Here you can define if an admin can add and remove members
	from a loan group or just view the loan groups.
		<br>
	<br>
	<li><b><a href="${pagePrefix}loans"><u>Loans</u></a>:</b> Various settings
	to allow access to member loans. The following subitems exist:
	<ul>
		<li><b>View member loans</b> View normal loans of members.
		<li><b>View geautoriseerd loans:</b> For some loans special authorization is needed.
		<li><b>Grant loan:</b> With the drop down you can specify different types
		of loans which the admin is allowed to grant to users.
		<li><b>Grant loan in past date:</b> for special occasions where the loan date cannot be the current date.
		<li><b><a href="${pagePrefix}loans#discard"><u>Discard
		loan</u></a>: </b> allows to &quot;cancel&quot; a loan by putting its remaining sum to
		zero.
		<li><b>Repay loan:</b> Allows the admin to repay a loan for the member
		<li><b>Repay loan in past date:</b> The same as the previous item, but
		now the admin is allowed to set the date of the repayment to some date in the
		past.
		<li><b>Manage expired loan status:</b> Allows you to give an expired loan
		an <a href="${pagePrefix}loans#status"><u>extra status/marking</u></a>.
	</ul>
	<br>
	<br>
	<li><b>Messages:</b> allows access by the admin to the <a
		href="${pagePrefix}messages"> <u>messages</u></a> system of cyclos.
	<ul>
		<li><b>View:</b> has a drop down where you can choose which types of
		message categories the admin is allowed to view. You can create new types of
		messages via <a href="${pagePrefix}messages#categories"><u>messages-categories</u></a>.
		<li><b>Send to member:</b> allows the admin to send a message to an
		individual member.
		<li><b>Send to group:</b> allows the admin to send a message to everyone
		in a member group.
		<li><b>Manage:</b> allows for management of messages. This allows for
		example to search old messages, and to create message categories.
	</ul>
	<br>
	<br>
	<li><b>Member Payments:</b> this is a set of autorisaties concerning the <a
		href="${pagePrefix}payments"><u>payments</u></a>. Subitems are:
	<ul>
		<li><b>System to member payments:</b> select here the system payments which
		the admin can use to pay the member from a system account.
		<li><b>Show member payment in menu:</b> allows the admin to perform a
		system to member payment from the main menu.
		<li><b>Member payment in past date:</b> allows the admin to perform the
		system to member payment, but &quot;pre-dated&quot;, meaning that a past date
		can be set as the payment date.
		<li><b>Member to member payment:</b> select here the payment types the
		admin can perform as if he were the member, paying another member.
		<li><b>Member self payment:</b> these are payments from one account of a
		member to another account of the same member. The admin can perform these
		payments as if he were the member.
		<li><b>Member to system payment:</b> the admin can make a payment to a
		system account, from the member account, as if he were the member. Select here
		the payment types for which you want to allow this.
		<li><b><a href="${pagePrefix}payments#geautoriseerd"><u>Authorize
		payments</u></a></b> are payments for which special authorization is needed; here you set
		that the admin can authorize a payment as if he were the member.
		<li><b>Cancel geautoriseerd payment as member</b>
		<li><b>Cancel <a href="${pagePrefix}payments#scheduled"><u>scheduled
		payment</u></a> as member</b>
		<li><b>Block scheduled payment as member:</b> allows you to block a
		scheduled payment. The difference between block and cancel is that a 
		blocked scheduled payment can be de-blocked. Canceling is definitive.
		<li><b><a href="${pagePrefix}payments#charge_back"><u>Chargeback
		payment</u></a>:</b> allows the admin to undo a payment for the member.
	</ul>
	<br>
	<br>
	<li><b><a href="${pagePrefix}member_records"><u>Member records</u></a>:</b>
	allows the admin to manage member records - these allow you to define
	information which will be gathered on the members. Subitems are pretty
	self-explanatory.<br>
	<br>
	<li><b><a href="${pagePrefix}references"><u>References</u></a>:</b> allows
	the admin to manage or view references - the system in which members can assign
	good or bad qualifications to each other.<br>
	<br>
	<li><b><a href="${pagePrefix}reports#member_activities"><u>Activities
	report</u></a>:</b> Are reports about the activity of the specific member, which are
	accessible from the action buttons below the member's profile. Sub-items are
	self-explanatory enough.<br>
	<br>
	
	<li><b><a href="${pagePrefix}reports#sms_log"><u>SMS logs</u></a>:</b>
	gives access to the logs of SMS messages sent for this member. The system can
	be configured to send SMS messages on several occasions.<br>
	<br>
	
	<li><b><a href="${pagePrefix}transaction_feedback"><u>Transaction
	Feedbacks </u></a></b>: These are feedbacks by other members on specific transactions.
	This autorisaties allow to view or view & modify the transaction feedbacks by an admin.<br>
	Transaction feedbacks are enabled in the <a
		href="${pagePrefix}account_management#transaction_type_details"><u>transaction
	type</u></a> configuration. <br>
	<br>
	<li><b>Members:</b> some miscellaneous autorisaties on what the admin can
	do with members. Sub-items:
	<ul>
		<li><b>View:</b> if this is not selected, the admin cannot view members
		<li><b>Register:</b> if this is selected, the admin can register a new
		member from the users search page.
		<li><b>Change profile:</b> if this is selected, the admin can make
		changes in the <a href="${pagePrefix}profiles"><u>profile</u></a> fields of a
		member.
		<li><b>Permanently remove:</b> The admin may remove members permanently
		from the database. This only can be done if the member never belonged to 
		a group that had accounts (after being actived a member can always be placed in the <a
			href="#removed_members"><u>removed members</u></a> group).			
		<li><b>Change group:</b> allows the admin to change the group of a
		member.
		<li><b>Import:</b> Allows the admin to import member lists into Cyclos (normally 
		migration from other system). <br>More information is available at the 
		<a
		href="${pagePrefix}user_management#import_members"><u>Help file</u></a>.
		<li><b>Manage pending members:</b> The admin can view members that have registred but
		did not validate there registration yet (per mail)
		<br>More information is available at the 
		<a
		href="${pagePrefix}user_management#search_pending_member"><u>Help file</u></a>.
	</ul>
</ul>
<hr class="help">
</span>

<span class="admin">
<a name="manage_group_autorisaties_admin_admin"></a>
<h3>Admin administration autorisaties</h3>
In this window you can set the autorisaties of the admininstrator functions of an
administrator group. That means that you can define the level of access that
adminstrators have in the system: members of the specified admin group will
receive the autorisaties you checked. It is common pratice to have a limited (if
not one) number of administrators with this kind of permission, because these
are top level autorisaties.
<br>
The autorisaties structure is straight forward. Most functions have
&quot;View&quot; and &quot;Manage&quot; permissons and sometimes extra specific
autorisaties.
<br>
If View is not selected the function will not show up in the menu and neither as
member action button (if the administator would type in the location directly in
the browser the underlaying permission structure would present a &quot;no
permission&quot; page).
<br>
The &quot;Manage&quot; option gives the permission to create, edit and delete.
<br><br>If the function has specific autorisaties (apart from manage and view) the
permission will have a name that indicates the type of permission (e.g.
disconnect logged-in member). <br>
<br>
The following are available; we only mention items which may need explanation:
<ul>
	<li><b>Access:</b>
	<ul>
		<li><b>Disconnect:</b> allows to disconnect another admin from the system
		at the very moment of clicking the disconnect button.
		<li><b>Reactivate disabled admins (because of failed login attempts):</b>
		If an admin is disabled because he doesn't remember his password, you can
		allow login for him again.
	</ul>
	<br>
	<br>
	<li><b>Administrator records:</b> are like <a
		href="${pagePrefix}member_records"><u>member records</u></a>, but allows you
	to keep information for administrators. Sub_items are straightforward. <br>
	<br>
	<li><b>Administrators:</b> these are autorisaties concerning other admins,
	like registring them and placing them in another group. 
	There are less admin than member functions. Administrators do not have accounts
	but can only have a certain level of access to system accounts.<br>
	The items are self explanatory enough.
</ul>
<hr class="help">
</span>

<span class="admin">
<a name="manage_group_autorisaties_member"></a>
<h3>Member autorisaties for member group</h3>
The members that belong to this group will receive these autorisaties.
<ul>
	<li><b>Access:</b>Unblock the PIN when the member exceeded the max attempts.<br><br>
	<li><b>Account:</b> A member can always access his own account, so there
	is no permission on viewing/managing one's own account. In this section you can
	only set if it is possible that the member can view:
	<ul>
		<li><b><a href="${pagePrefix}payments#geautoriseerd"><u>geautoriseerd
		payments</u></a></b>
		<li><b><a href="${pagePrefix}payments#scheduled"><u>scheduled
		payments</u></a></b>
	</ul>
	<br>
	<br>
	<li><b>Ads:</b><br>
	<ul>
		<li><b>Publish:</b> If &quot;Publish&quot; is selected a member can
		publish advertisements and the menu item &quot;Personal - Advertisements&quot;
		will show up in the member menu.<br>
		<br>
		<li><b>View:</b> If no groups are selected in the &quot;View&quot; drop
		down, Advertisment functions will not be availible for this member group.
		Neither will it appear in the Search menu or <a
			href="${pagePrefix}profiles#actions_for_member"><u> &quot;Actions of
		member&quot;</u></a> page below the profile.<br>
		If one or more groups are selected the <a
			href="${pagePrefix}advertisements#advertisement_search"><u> &quot;Search
		Products & Services&quot;</u></a> function will only show results of members of the
		selected groups.<br>
		Normally you would select all groups. If you have groups that need to operate
		totally separate but within the same system you could limit the visibility by
		selecting only specific groups. An example would be if there is a business
		group and you don't want to show up consumer advertisements for this group.
	</ul>
	<br>
	<br>
	<li><b>Commissions</b>: If &quot;view&quot; is checked, it allows the
	member to view the <a href="${pagePrefix}brokering#commission"><u>
	commissions</u></a> for his own broker. <br>
	<br>
	<li><b>Documents:</b> With this option you can determine the <a
		href="${pagePrefix}documents"><u>documents</u></a> that will show up in the
	member menu &quot;Personal - documents&quot;. If no document is selected the
	menu item will not show up (for this member group)<br>
	<br>
	<li><b>Guarantees:</b> This is part of the <a
		href="${pagePrefix}guarantees"><u></u></a> system of cyclos, where each
	account balance in cyclos is guaranteed by a backed sum of money. You may
	choose the following autorisaties:
	<ul>
		<li><b>Buy with payment obligations from groups:</b> here you can select from
		which group the member is allowed to buy with <a
			href="${pagePrefix}guarantees"><u>payment obligations</u></a>.
		<li><b>Issue certifications to:</b> <%-- ?? LATER DOOR HUGO ?? --%>
		<li><b>Create guarantees (as issuer):</b> <%-- ?? LATER DOOR HUGO ?? --%>
		<li><b>Sell with payment obligations to groups:</b> <%-- ?? LATER DOOR HUGO ?? --%>
	</ul>
	<br>
	<br>
	<li><b>Invoices:</b> In this section you can define if a member can send <a
		href="${pagePrefix}invoices"><u>invoices</u></a> to other members, either from
	a user profile or directly form the &quot;Account&quot; menu. When the
	&quot;System invoices&quot; is selected a member can send invoices to system
	accounts from the &quot;Account menu&quot;.<br>
	<br>
	<li><b>Loans:</b> In this section you can define the autorisaties for <a
		href="${pagePrefix}loans"><u>loans</u></a> for members.
	<ul>
		<li><b>View:</b> If the &quot;View&quot; option is selected the members
		of the group can view its loans. If view is not selected the menu items do not
		show up.
		<li><b>Repay:</b> select this to allow the member to perform loan
		repayments.
	</ul>
	<br>
	<br>
	<li><b>Messages:</b> In this section you can define to what extend the
	member may use the <a href="${pagePrefix}messages"><u>messages</u></a> system
	of Cyclos. For more than one categorie to show up in the &quot; Send to
	Administration&quot; drop down, you will need to <a
		href="${pagePrefix}messages#categories"><u>define</u></a> those categories
	first.<br>
	Note: For a message to show up for an administrator the administration group
	needs to have the autorisaties as well.
	<br>
	<br>
	<li><b>Operators:</b> Here you can define if members can make use of the <a
		href="${pagePrefix}operators"><u>operators</u></a> system of cyclos, which
	allows you to define a sort of sub-members for one account. There is just one
	checkbox to turn on or off.<br>
	<br>
	<li><b>Payments:</b> Here you can specify what <a
		href="${pagePrefix}account_management#transaction_type_details"><u> type of payment</u></a> is
	allowed for this member group. Mostly you will select only one or a few types.
	The availability of types of course depends on how many payment types you defined.
	<ul>
		<li><b>Self payment:</b> If this is selected the member can make payment
		between his own accounts. In the drop down you can specify the possible
		transaction types. This option will only make sense if you have more than one
		member account for this group.
		<li><b>Member payments:</b> Here you can select which payment types the
		member may use when paying another member.
		<li><b>Member payment option from menu:</b> If this option is checked the
		members can perform payments to other members directly from the
		&quot;Account&quot; menu.

		<li><b>System payments:</b> Here you can specify which payment types the
		member can choose to make payments to system accounts. If none are selected
		the menu item &quot;System payment&quot; will not show up.
		
		<li><b>Generate external payment tickets:</b><br>
		This is about the ticketing system used by member shops. It is mostly used
		by webshops that want to use Cyclos as an external payment system. If this
		option is selected the member (shop) is allowed to generate tickets. The
		ticket system is transparent for the users. It provides a structure where web
		shops can authenticate and validate consumers and payment data but without
		having access to the login details of the consumer. The technical details 
		can be found at the Cyclos wiki page (webservices - tickets)
		
		
		<li><b>Authorize when payment receiver:</b> allows the member to <a
			href="${pagePrefix}payments#geautoriseerd"><u>Authorize</u></a> a payment if he
		is the receiver.
		<br>
		Note: This option allows a a confirmation by the receiver. It works similar in the
		way invoices work and therefore should not be combined with invoices.
		
		<li><b>Cancel payment pending authorization:</b> When <a
			href="${pagePrefix}payments#geautoriseerd"><u> geautoriseerd payments</u></a>
		are used, this will allow members to cancel their geautoriseerd payments once
		they have been created, but not yet been geautoriseerd.

		<li><b>Cancel scheduled payment:</b> When <a
			href="${pagePrefix}payments#scheduled"><u> scheduled payments</u></a> are
		used, this will allow members to cancel their scheduled payments before the
		planned date has started.

		<li><b>Block scheduled payment:</b> allows the member to block his
		scheduled payment temporary.

		<li><b>Request payments from other channels:</b> when this is checked,
		the member can send &quot;<a href="${pagePrefix}payments#request"><u>
		payment requests</u></a>&quot; (invoices) over other <a href="${pagePrefix}settings#channels"><u>
		channels</u></a>; you can choose these channels from the drop down box. At the moment,
		only SMS is available for this, but in future other channels might be added.
	</ul>
	<br>
	<li><b>Preferences:</b> This allows the members of the group to access
	their preferences main menu item. There are only two items in this menu, and
	hence in these autorisaties:
	<ul>
		<li><b>Notifications</b> allows the member to manage their e-mail <a
			href="${pagePrefix}notifications"><u> notifications</u></a>.
		<li><b></b> allows the member to manage their <a
			href="${pagePrefix}ads_interest"><u>advertisement interests</u></a>.
	</ul>
	<br>
	<li><b>Member profile:</b> Here you can specify the members that will show
	up in the <a href="${pagePrefix}user_management#search_member_by_member"><u>
	&quot;Search members&quot;</u></a> of this member group. Normally all groups are
	selected (except removed or disabled groups). You only would want to allow
	specific groups to be viewed in case you want to have groups working
	independently in the system. For example a consumers and business group that 
	cannot see each other. If you
	use a specific group to view you will need to set the same autorisaties for the
	view autorisaties in Ads.<br>
	<br>
	<li><b>References:</b> This allows the member to view <a
		href="${pagePrefix}references"><u> references</u></a>, or to give them to
	other members. If you do not want to use the reference function at all (for one
	or more groups) you leave the &quot;View&quot; option empty. In this case the
	reference menu and other reference buttons will not show up. <br>
	<br>
	<li><b>Member reports:</b> If &quot;View&quot; is selected the member
	group can view the <a href="${pagePrefix}reports#member_reports"><u>
	reports pages</u></a> of other members. If you select an account type at &quot;Show
	account information&quot; the member can also view account information
	(balances) of other members in these reports. <br>
	<br>
	<li><b><a href="${pagePrefix}reports#sms_log"><u>SMS
	logs</u></a>:</b> gives the member access to the logs of SMS messages sent for him. The
	system can be configured to send SMS messages on several occasions.<br>
	<br>
</ul>
<hr class="help">
</span>

<span class="admin">
<a name="manage_group_autorisaties_broker"></a>
<h3>Broker autorisaties</h3>
These are typically the autorisaties about the
<a href="${pagePrefix}brokering"> <u>brokering</u></a>
functions a broker may perform. This means you can define the type of functions
brokers of this group can do in relation to their members. The autorisaties
structure is straight forward. Most functions have &quot;View&quot; and
&quot;Manage&quot; permissons and sometimes extra specific autorisaties.
<br>
If View is not selected the function will not show up in the menu of the broker,
and neither as member action button (if the broker would type in the location
directly in the browser the underlaying permission structure would present a
&quot;no permission&quot; page).
<br>
The &quot;Manage&quot; option gives the permission to create, edit and delete.
<br>
<br>
You can allow that the broker has access to the following functions for his
brokered members:
<ul>
	<li><b>Account:</b>
	<ul>
		<li><b>View account information:</b> the broker can view the account
		information of his brokered members.
		<li><b>View <a href="${pagePrefix}payments#geautoriseerd"><u>geautoriseerd
		payments</u></a></b> of his brokered members is allowed if this is selected.
		<li><b>View <a href="${pagePrefix}payments#scheduled"><u>scheduled
		payments</u></a></b>
	</ul>
	<br>
	<br>
	<li><b>Products and Services:</b> allows a broker's viewing or managing
	permission to the advertisements of the brokered member.<br>
	<br>
	
	<li><b><a href="${pagePrefix}documents">Documents:</a></b>
	<ul>	
	<li><b>View:</b> Here you can choose which system wide documents the
	broker can view.
	<li><b>View member individual documents:</b> Allows the broker to view the <u>
	<a href="${pagePrefix}documents#member_document"> individual documents</a></u>
	of the member.
	<li><b>Manage member individual documents:</b> same as previous, but
	management rights.
	</ul>
	
	<br>
	
	<li><b>Invoices:</b> Significance of items here is obvious and
	self-explanatory. <br>
	<br>
	<li><b><a href="${pagePrefix}loan_groups"><u>Loan groups</u></a></b> <br>
	View the loan group menu item.
	<br>
	<br>
	<li><b><a href="${pagePrefix}loans"><u>Loans</u></a></b><br>
	View the loan menu item.
	<br>
	<br>
	<li><b>Access:</b> These allow the broker to control access for the
	member. It contains the following sub-items:
	<ul>
		<li><b>Change login password:</b> allows the broker to change the
		member's login password from the member's profile (in member action
		&quot;manage passwords&quot;) .<br>
		Note that if this permission is not checked a broker can still define
		a temporary password upon member registration (the member will be
		forced to change the password at first login). But this is only in
		case the <a href="#edit_member_group"><u>group setting</u></a>
		&quot;Send password by e-mail&quot; is not checked.<br>
		If the group setting &quot;Send password by e-mail&quot; is checked
		(in the member group setting) the broker cannot define the password at
		the registration. In this case the member will receive automatically a temporary
		password by mail that need to be changed at first login).<br>
		If both the group setting &quot;Send password by e-mail&quot; and
		&quot;Change login password&quot; are checked the broker can chose at
		the registration to define a definitive (not temporary) password or to
		force the user changing the password on first login.
		<li><b>Reset login password:</b> allows the broker to reset the members login
		password, which (depending on the configuration) generally means that it will
		be automatically regenerated and send by mail.
		<li><b>Manage <a href="${pagePrefix}passwords#transaction_password"><u>transaction
		password</u></a>:</b> allows to manage the special password which can be set for
		transactions.
		<li><b>Change pin:</b> Allows the broker to change the <a
			href="${pagePrefix}passwords#pin"><u>pin number</u></a>, which is
		a numeric password for access to certain <a href="${pagePrefix}settings#channels">
		<u>channels</u></a>, such as webshops.
		<li><b>Change channels access:</b> change the access methods to <a
			href="${pagePrefix}settings#channels"><u>channels</u></a>, like the web, via mobile
		phones, etc.
		<li><b>Unblock PIN:</b>Unblock the PIN when the member exceeded the max attempts.<br><br>
	</ul>
	<br>
	<br>
	<li><b>Member Payments:</b> controls to which payment items for the member
	the broker has access to.<br>
	Subitems are:
	<ul>
		<li><b>Payment as member to member:</b> select here the payment types the
		broker can perform as if he were the member, paying another member.
		<li><b>payment as member to system:</b> the broker can make a payment to
		a system account, from the member account, as if he were the member. Select
		here the payment types for which you want to allow this.
		<li><b>Authorize:</b> The broker can authorize payments of the member for
		the <a href="${pagePrefix}payments#geautoriseerd"><u>geautoriseerd
		payments</u></a> system.
		<li><b>Cancel geautoriseerd payment as member</b>
		<li><b>Cancel <a href="${pagePrefix}payments#scheduled"><u>scheduled
		payment</u></a> as member</b>.
		<li><b>Block scheduled payment as member:</b> Temporary block a scheduled payment. 
		The difference between block and cancel is that a 
		blocked scheduled payment can be de-blocked. Canceling is definitive.
		<li><b>Member self payment:</b> these are payments from one account of a
		member to another account of the same member. The broker can perform these
		payments as if he were the member.
	</ul>
	<br>
	<br>
	<li><b><a href="${pagePrefix}member_records"><u>Member records</u></a>:</b>
	allows the admin to manage member records - these allow you to define
	information which will be gathered on the members. Subitems are pretty
	self-explanatory.<br>
	<br>
	<li><b><a href="${pagePrefix}reports#sms_log"><u>SMS
	logs</u></a>:</b> gives access to the logs of SMS messages sent for the brokered members.
	The system can be configured to send SMS messages on several occasions.<br>
	<br>
	<li><b>Brokering:</b>
	<ul>
		<li><b>Register:</b> allows the broker to register a new member.
		<li><b>Change profile:</b> allows the broker to change the <a
			href="${pagePrefix}profiles"><u> profiles</u></a> of their brokered members.
		<li><b>Manage default commissions:</b> This allows the broker to set the default values of the
		<a href="${pagePrefix}brokering#commission"><u>commissions</u></a> himself.
		<li><b>Manage contracts:</b> If this is selected, the broker can manage
		the <a href="${pagePrefix}brokering#commission_contract"><u>contracts</u></a> on
		commissions with the members. This can be done via a button in below the
		<a href="${pagePrefix}profiles"><u>profile</u></a>.
		<li><b>Manage pending members:</b> The broker can view members that have registered but
		did not validate there registration yet (per mail)
		<br>More information is available at the 
		<a
		href="${pagePrefix}user_management#search_pending_member"><u>Help file</u></a>.
			 	
	</ul>
	<br>
	<br>
	<li><b>Personal messages:</b> There is one item here, allowing the broker
	to send personal <a href="${pagePrefix}messages"><u>messages</u></a> to all of
	his brokered members.<br>
	<br>
	<li><b>References:</b> allows the broker to manage (give, delete, modify)
	<a href="${pagePrefix}references"><u>references</u></a> for the member. <br>
	<br>
	<li><b>Reports:</b>
	<ul>
		<li><b>View:</b> if this is selected the broker can view the <a
			href="${pagePrefix}reports#member_reports"><u>reports pages</u></a> of his
		brokered members.
		<li><b>Show Account information:</b> if you select an account type here,
		the broker can also view account information (balances) of his members in
		these reports.
	</ul>
	<br>
	<br>
</ul>
<hr class="help">
</span>

<a name="group_filters"></a>
<h2>Group Filters</h2>
Group filters are a sort of &quot;super groups&quot;: a set of groups bundled
together, and given a name, on which you can perform certain actions. So in
short, a group filter is a &quot;group of groups&quot;.
<br>
Group Filters come in handy for several tasks in cyclos. You can perform
statistic calculations on group filters, and members and admins can perform
searches on a group filter.

<i>Where to find it?</i><br>
Group filters can be found at the &quot;Menu: Users & Groups > group filters&quot;.
<hr>

<span class="admin">
<a name="group_filter"></a>
<h3>Modify / create group filter</h3>
In this window the
<a href="#group_filters"><u>group filter</u></a>
can be defined or modified. If you modify an existing group, you should first
click the button labeled &quot;change&quot; before you are able to change the
fields in the form.
<br>
Don't forget to click &quot;submit&quot; to commit and save the changes.
<ul>
	<li><b>Name:</b> choose a name you want for the new group filter.
	<li><b>Login page name</b> This option will only show up if you customized
	the login page for this group filter (in customized files window below). The
	customized (group) login page can be accessed by putting the login page name
	after the &quot;global&quot; login page with an interrogation character. The
	login page name cannot have spaces. An example would be:<br>
	http://www.yourdomain.org/cyclos?yourgrouploginpagename.<br>
	Note that it is also possible to specify a login page name per <a
		href="${pagePrefix}groups#group_details"><u>group</u></a>.
	<li><b>Container page url</b> This setting is used if you want to access
	Cyclos from within a website. The settings works the same as the global
	container page (see <a href="${pagePrefix}settings#local"><u>Settings -
	Local settings</u></a> but just for this group filter. In this field you would need to
	put the page that opens the iframe or frame-set that includes Cyclos. for
	example: http://www.yourgroupdomain.org/cycloswrapper.php<br>
	Note that it is also possible to specify a login container page per <a
		href="${pagePrefix}groups#group_details"><u>group</u></a>.
	<li><b>Description:</b> a description for administrators to make clear the
	user of the group filter.
	<li><b>show in profile:</b> when this is selected, the group filter will
	show up at the group field in the <a href="${pagePrefix}profiles"><u>profile</u></a>
	of the member.
	<li><b>Groups:</b> is the most important element in this form. Here you
	select the groups you want to be in the group filter.
	<li><b>Viewable by:</b> allows you to choose which groups will be able to
	see your group filter. In that case, the group filter will show up in the
	member and advertisement search in the member section. NOTE that if you
	selected the &quot;show in profile&quot; checkbox, it will be always visible in
	the profile, independant of the groups you select at &quot;viewable by&quot;.
	So this setting only affects the search function.
</ul>
<hr class="help">
</span>

<span class="admin">
<a name="manage_group_filters"></a>
<h3>Manage group filters</h3>
This window shows a list with
<a href="#group_filters"><u>group filters</u></a>
and gives the option to manage them.
<ul>
	<li><b>Modify</b> (or view) an existing group filter via the <img
		border="0" src="${images}/edit.gif" width="16" height="16">&nbsp;edit
	icon.
	<li><b>Delete</b> a group filter via the <img border="0"
		src="${images}/delete.gif" width="16" height="16">&nbsp;delete icon.
	<li><b>Create:</b> a new group filter by clicking the submit button
	labelled &quot;insert new group filter&quot;.
</ul>
<hr class="help">
</span>

<span class="admin">
<a name="manage_group_filter_customized_files"></a>
<h3>Customized files (for group filter)</h3>
With this function you can set a
<a href="${pagePrefix}content_management"><u> customized file</u></a>
for a
<a href="#group_filters"><u>group filter</u></a>
. This means that each group filter can have its own customization like layout
(colors, styles), logo, and pages like news, contact, manuals etc. If no
specific group or group filter customization is made the main layout and pages
will appear.
<br>
Group customization overrules group filter customization, so if you defined
both, first the system will check if there is a file customized for the group,
and show that. If that's not the case, it will check if the file is customized
for the group filter. If that's not the case either, it will show the system
wide customized version of the file, if available.
<br>
Elements in the form:
<ul>
	<li><b>View</b> how the result will look via the <img border="0"
		src="${images}/view.gif" width="16" height="16">&nbsp;view icon.
	<li><b>Modify</b> an existing customized file via the <img border="0"
		src="${images}/edit.gif" width="16" height="16">&nbsp;edit icon.
	<li><b>Delete</b> a customized file definition via the <img border="0"
		src="${images}/delete.gif" width="16" height="16">&nbsp;delete icon.
	<li><b>Create:</b> a new customized file via the button labeled
	&quot;create new file&quot;.
</ul>
<hr class="help">
</span>

</div> <%--  page-break end --%>

<div class='help'>
<br><br>
<br><br>
<br><br>
<br><br>
<br><br>
<br><br>
<br><br>
<br><br>
<br><br>
<br><br>
<br><br>
<br><br>
<br><br>
<br><br>
<br><br>
<br><br>
<br><br>
<br><br>
<br><br>
<br><br>
<br><br>
<br><br>
</div>

