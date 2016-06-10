<div style="page-break-after: always;"
<a name="top"></a>
<br><br>
A profile is the information associated with a
particular user.<br>
<span class="admin">
There are administrator profiles and member profiles. An administrator login is
just for administration purposes and has no Account nor other Member functions
like contacts, references etc, hence the profile of an admin is much more simple
than a member profile.<br>
More important, connected to each profile are the <a href="#actions"><u>Actions
for...</u></a> windows, which allow you to initiate various actions from someone's
profile.
</span>
The &quot;login name&quot; is the name with which someone logs in; this in
contrast to &quot;name&quot;, which is someone's real name.
<br><br><i>Where to find it?</i><br>
You can access your own profile via &quot;Menu: Personal > profile&quot;.<br>
<span class="member"> You can access another member's profile in two
ways:
<ul>
	<li><b>via a search:</b> Perform a <a
		href="${pagePrefix}user_management#search_member_by_member"><u> search
	on members</u></a> via &quot;Menu: search > members&quot;.
	<li><b>via your contacts:</b> User your <a href="${pagePrefix}contacts"><u>contact
	list</u></a> to jump to a member's profile, via &quot;Menu: Personal > Contacts&quot;.
</ul>
</span> 
<span class="admin"> You can access another admin's profile via
&quot;menu: Users & Groups > Manage admins&quot;.<br>
You can access a member's profile via &quot;Menu: Users & Groups > Manage
members&quot;. 
</span> 
<span class="broker"> As a broker, you can access your
own members' profiles via the &quot;Menu: brokering > members&quot;. 
</span>
<span class="admin">
<br><br><i>How to get it working?</i><br>
Anyone can always view their own profile. For member and broker groups, you need to
set explicitedly which profiles of other groups may be viewed by them (first item below).
There are several autorisaties connected to profiles:
<ul>
	<li>To control which other groups the member can see, set these
	<a href="${pagePrefix}groups#manage_group_autorisaties_member"><u>autorisaties</u></a>
	for the group. Each group checked in the drop down box at the block 
	&quot;Member profile&quot; will be visible for this member group, meaning that 
	the member can view the profile of other members in these groups. The same permission
	can be set for broker groups.
	<li>For an admin to be able to change a member's profile, you need to set the <a href="${pagePrefix}
	groups#manage_group_autorisaties_admin_member"><u>autorisaties</u></a> for this
	under the block &quot;members&quot;, and check the checkbox &quot;change profile&quot;.
	<li>There is also a <a href="${pagePrefix}groups#manage_group_autorisaties_admin_admin"><u>permission</u></a>
	to change the profile of an admin, under the block &quot;administrators&quot;, the checkbox
	&quot;change profile&quot;.
	<li>For brokers, you will need to set the <a href="${pagePrefix}groups#manage_group_autorisaties_broker"><u>
	autorisaties</u></a> to allow them to change the profile of their members. This can be done under
	the block &quot;brokering&quot;, by checking the &quot;change profile&quot; checkbox.
</ul>
</span>
<hr>

<span class="admin">
<a NAME="admin_profile"></a>
<h3>Admin profile</h3>
In this page you can modify an <a href="#top"><u>admin profile</u></a>; either
your own or the profile of another admin (the latter of course dependant on your
autorisaties.<br>
You should click &quot;change&quot; to be able to make changes; click
&quot;submit&quot; to save the changes you made.
<br><br>Most items are pretty straightforward. If you're viewing the profile of
another admin the page will show the group the admin belongs to.
<hr class="help">
</span>

<a NAME="member_profile"></a>
<h3>Member profile</h3>
The
<a href="#top"><u>profile</u></a>
page shows information
<span class="admin broker">the member</span>
<span class="member">you</span>
filled in when registering.
<span class="member">In case of your own profile, </span>
You can add and modify information by clicking &quot;Change&quot; at the bottom
of the page. Don't forget to click the &quot;submit&quot; button when you're
done in order to save the changes.
<span class="member">
<br><br>Of course, the profile of other members cannot be modified, and the
member may have chosen to make some of the information invisible to other
members. Therefore, the rest of this help text mostly refers to your own
changeable profile.
</span>
<br><br>Some general information on the form:
<ul>
	<li><b>*:</b> information which is obligatory is marked by a red asterix
	(*) at the right side of the edit box.
	<li><b>hide: </b> Certain fields can be made invisible for other Members by
	selecting the &quot;Hide&quot; box displayed behind the field you want to hide.
</ul>
<br><br>The form is pretty straightforward, and most items are self-explanatory.
Some remarks on the following specific fields is given below (note that the
fields mentioned may not be visible, depending of the configuration your
organization chose):
<ul>
	<span class="admin">
	<li><b>Last login:</b> this field will show &quot;isLogged&quot; in case
	the member is using the cyclos program right now. In this case, you can
	forcibly log out the member via a button in the window below.
	</span>
	<li><b>birthday:</b> you can use the &quot;datepicker&quot; button (<img
		border="0" src="${images}/calendar.gif" width="16" height="16">&nbsp;)
	to get a calendar which may help you filling in the date.
	<li><b>broker:</b> This field is only visible when your organization uses
	the <a href="${pagePrefix}brokering"><u>brokering</u></a> system. The field is
	not changeable, but you can use the &quot;Open profile&quot; link next to it to
	go to the profile of your broker.
	<li><b>Registration date:</b> If the member is in a &quot;pending&quot;
	group (group without related account) it will show the date that the member was
	registered (either by an admin, broker or by the member self at the public
	registration page).<br>
	This date won't be show if the member is part of an active group (but the
	activation date is showed in the member reports page)
	<li><b>add picture:</b> check this checkbox and you will be able to <a
		href="#picture"><u> add a picture</u></a> to your profile.
</ul>
<hr class="help">

<a name="picture"></a>
<h3>Pictures in your profile or advertisement</h3>
Cyclos allows you to upload pictures for your
<a href="#top"><u>profile</u></a> 
or advertisement. Follow these directions if you want other members to be able to see your
picture:
<ol>
	<li>Click on &quot;Change&quot; to be able to make changes to the form.
	<li>Check the &quot;Add Picture&quot; checkbox; extra edits will become
	visible.
	<li>Click on the &quot;Browse&quot; button to choose a photo on your
	computer you want to upload.
	<li>Selecting the &quot;browse&quot; button to browse your computer's file
	system. Choose one and click on &quot;Open&quot;. Note that there is a maximum
	file size (which will be displayed in the window) and that only the formats
	JPG, GIF and PNG are supported.
	<li>After that, the name of the file is placed in the &quot;Picture&quot;
	box.
	<li>When you click &quot;Submit&quot; the file is uploaded.
</ol>
You can upload more than one image. The number of images that can be uploaded is
defined by the administration.
<br>
You can browse through the pictures by selecting the navigation under the images
&quot;< 1/2/3 >&quot;. You can change the display order of the images and select
a text that will be showed under the image by selecting the &quot;Details&quot;
link below the image.
<br>
You can remove an image by viewing the image and selecting &quot;remove&quot;
link below the image. Other members will be able to view your image in the same
way (but they cannot remove an image).
<hr class="help">

<a name="actions"></a>
<h2>Actions for...</h2>
Below each
<a href="#top"><u>profile</u></a>
there is usually a window with buttons for all the actions you can perform
concerning this member. These actions vary from making payments to
<span class="admin broker">or for</span>
the member, to giving references to the member, sending messages to this member,
etc. etc.

The availability of actions may vary, depending on the configuration and rules
in your organization. Also, some of the actions listed in the help windows may
not be available to you when you don't have the right autorisaties.
<br><br>The help windows give a summary of maximal available actions, that is:
all actions which might possibly occur in your window. As said, it is very
likely that you will not see all of those actions, depending on your
configuration and autorisaties. For a more detailed explanation of the actions
you can refer to the help within the specific action window you will reach when
clicking the button. You may also follow the links, which will give you some
more general explanation about the subject. (The items are discussed from left
to right, line by line from top to bottom.)
<br><br>Actions and profiles for operators are discussed in the helpfile of operators. 
<hr>

<a name="actions_for_member_by_operator"></a>
<a name="actions_for_member"></a>
<span class="member">
<h3>Actions for member</h3>
In this window, you can perform various actions regarding this member.
<UL>
	<li><b>Make payment</b>
	<LI><b>View & Give References:</b> See what other people's experiences
	with this member are and provide a <a href="${pagePrefix}references"><u>
	reference</u></a> about this member.
	<li><b>Transaction feedbacks:</b> allow you to give a <a
		href="${pagePrefix}transaction_feedback"><u> qualification</u></a> to a
	transaction.
	<LI><b>Send <a href="${pagePrefix}invoices"><u>invoice</u></a></b>
	<li><b>View <a href="${pagePrefix}advertisements"><u>advertisements</u></a></b>
	<li><b>Send e-mail</b>
	<li><b>send message</b> with the <a href="${pagePrefix}messages"><u>internal
	messaging system</u></a> of cyclos.
	<li><b>View Reports:</b> will bring you to <a
		href="${pagePrefix}reports#member_activities"><u> reports</u></a> with information
	about this member's activity.
	<LI><b>Add to <a href="${pagePrefix}contacts"><u>contact list</u></a></b>
</UL>
<hr class="help">
</span>

<span class="broker"> <a name="actions_for_member_by_broker"></a>
<h3>Broker actions for member</h3>
This window is the starting point for all <a href="${pagePrefix}brokering"><u>broker</u></a>
actions in relation to this member. So, this would be the main page to turn to
for your tasks as a broker.<br>
You may want to read the <a href="#actions"><u>general notes</u></a> on this
window first.
<UL>
	<LI><b>Manage advertisements:</b> Add, delete or edit <a
		href="${pagePrefix}advertisements"><u>advertisements</u></a> for this member.
	<li><b>Account information:</b> brings you to the account history of this
	member, showing the balance, and the transaction history. (view access)
	<li><b>View <a href="${pagePrefix}payments#scheduled"><u>scheduled
	payments</u></a></b>
	<li><b><a href="${pagePrefix}payments#geautoriseerd"><u>
	Authorized payments</u></a></b> This will bring you to the payments which the member should
	authorize as a receiver of the payments. 
	This is applicable for a configuration where a
	receiver of a payment (the member) should authorize the payment before it is
	added to the balance on his account. As a broker, you can act as if you were
	this member and authorize these payments, resulting in adding them to this
	member's balance. This is only available if applicable.
	<li><b>View payment <a href="${pagePrefix}payments#geautoriseerd"><u>
	authorizations</u></a>:</b> Here you go to the overview of payments which you, as a broker,
	must authorize for the member in order to have them paid from his/her account. This is applicable
	for the situations where members cannot perform certain (or all) payments,
	because they must be geautoriseerd by an admin or broker first.
	<li><b>Payment member to member:</b> Perform a payment to another member
	as if you were your member.
	<li><b>Self payment:</b> Perform a transfer between the different accounts
	of this member. This is only possible if this member has more than one account.
	
	<li><b>Payment member to system:</b> Perform a payment to the organization
	/ system as if you were your member.
	<li><b>Manage references:</b> Here you can see how other members value
	this member, and you can see what kind of experiences this member has had with
	other members. The provided and received <a href="${pagePrefix}references"><u>references</u></a>
	are displayed. A broker can change or delete the given references of the member
	(if he has the autorisaties)
	<li><b>View loans:</b> The Overview of the <a href="${pagePrefix}loans"><u>loans</u></a>
	of this member.
	<li><b>View invoices:</b> View all the <a href="${pagePrefix}invoices"><u>invoices</u></a>
	this member has sent and received.
	<li><b>invoice member to member:</b> send an invoice to another member as
	if you were your member.
	<li><b>invoice member to system:</b> send an invoice to the system as if
	you were this member.
	<li><b>Loan Groups:</b> show the <a href="${pagePrefix}loan_groups"><u>loan
	groups</u></a> this member is part of.
	<li><b>Manage passwords:</b> allows you to reset the member's <a
		href="${pagePrefix}passwords"><u> passwords</u></a>.
	<li><b>External access:</b> allows you to manage to via <a
		href="${pagePrefix}settings#channels"><u> channels</u></a> the member can access
	cyclos. It also gives you the possibility to change the member's <a
		href="${pagePrefix}passwords#pin"><u>pin number</u></a> (a numeric
	password for for example web shop access).
	<LI><b>Remarks:</b> Any comment about this member can be put here. These
	remarks are meant for yourself or for other administrators and brokers. None of
	the members are able to see those remarks. If the remark text before the submit
	button is red it means that it contains a remark. If there is no text in the
	remark field the remark text will remain in the default text colour of the
	application.
	<li><b>...:</b> any other <a href="${pagePrefix}member_records"><u>member
	record</u></a> types you defined will be listed here too with a button.
	<LI><b>Member documents:</b> This page gives access to page with member <a
		href="${pagePrefix}documents"><u>documents</u></a> that can be printed.
	<li><b>commission contracts:</b> gives you access to a page where you can
	review or set up <a href="${pagePrefix}brokering#commission_contract"><u>contracts</u></a>
	between you and your members about your brokering services.
	<li><b>SMS logs:</b> gives you access to the <a
		href="${pagePrefix}reports#sms_log"><u> SMS logs</u></a>, where SMS
	messages sent to this member are kept. The system can be configured to send SMS
	messages on several occasions.
</UL>
<hr class="help">
</span>

<span class="admin"> <a name="actions_for_member_by_admin"></a>
<h3>Actions for member (by admin)</h3>
This window is the starting point for all administration actions in regards to
members. So, this would be the main page to turn to for your tasks as an <a
	href="${pagePrefix}groups#account_admins"><u> account administrator</u></a>.<br>
You may want to read the <a href="#actions"><u>general notes</u></a> on this
window first.<br>

Admin actions are organized in sections. Each section is placed in a different
help section, in order to keep the length of blocks of text manageable and
readable.
<br><br>The following sections exist. Click on the links to move to the
description of buttons in each section.
<ul>
	<li><b><a href="#access_actions"><u>Access section</u></a>:</b> all about
	controling the member's access to the system and the software.
	<li><b><a href="#ads_actions"><u>Advertisements section</u></a>:</b> about
	managing the member's advertisements.
	<li><b><a href="#accounts_actions"><u>Accounts section</u></a>:</b> all
	about the accounts of the member, including payments and invoices.
	<li><b><a href="#member_info_actions"><u>Member info section</u></a>:</b>
	information about the member, his/her activity, etc.
	<li><b><a href="#brokering_actions"><u>Brokering section</u></a>:</b>
	actions relating to the <a href="${pagePrefix}brokering"><u>brokering</u></a>
	system of cyclos.
	<li><b><a href="#loans_actions"><u>Loans section</u></a>:</b> actions
	related to <a href="${pagePrefix}loans"><u></u></a> of this member.
</ul>
<hr class="help">
</span>

<span class="admin"> <a name="access_actions"></a>
<h3>Member actions: access</h3>
(This overview is part of the &quot;<a href="#actions_for_member_by_admin"><u>actions
for member</u></a>&quot; window.)
<ul>
	<li><b>Allow user to login now:</b> This is only visible if the member's
	access is blocked, for example because of subsequent tries to login with a
	wrong password. Such an action would block the member's login for a certain
	period of time, according to the <a href="${pagePrefix}settings"><u>settings</u></a>.
	If such a member should call the administration in frustration, you can
	immediately deblock the member's login via this button. After clicking it, the
	member should be able to retry the login. <br>
	<b>Note:</b> of course, this doesn't reset the member's login <a
		href="${pagePrefix}passwords"><u>password</u></a>. If the member doesn't
	remember his password, you may reset it via the button &quot;manage
	passwords&quot; in the same section of this window.
	<li><b>Disconnect logged user:</b> If the member is logged in at this very
	moment, you can forcibly log him out by clicking this button. This button is <b>only</b>
	visible if the member is logged in at present. <br>
	A reason for logging out a member would be if a Member closed the browser
	without logging out correctly. If the Member wants to login within a certain
	time (timeout) a message will inform the Member that the member is still
	logged. When the Member contacts the administration he/she can be logged out
	with this function. Another option however is, just to wait until the time out
	period is finished; the default time out value is 10 minutes.<br>
	Another reason for logging out a Member would be in case of an emergency like
	suspected abuse or hacking of an account. It is recommended in such cases, to
	(temporary) disable this member after having him/her forcibly logged out (you
	can do this via the &quot;Change Permission Group&quot; button in the same
	section of this page).
	<li><b>Manage passwords:</b> allows you to reset the member's <a
		href="${pagePrefix}passwords"><u> passwords</u></a>.
	<li><b>External Access:</b> allows you to manage to via <a
		href="${pagePrefix}settings#channels"><u> channels</u></a> the member can access
	cyclos. It also gives you the possibility to change the member's <a
		href="${pagePrefix}passwords#pin"><u>pin number</u></a> (a numeric
	password for for example web shop access).
	<li><b>Change permission group:</b> allows you to change the <a
		href="${pagePrefix}groups"><u> group</u></a> to which a member belongs. Every
	member belongs to a group. This page will allow to change the group of the
	member and to send an activation e-mail. The basic way to control access to the
	cyclos software is via groups. If you should want to remove a member from the
	system/organization, you should use this button, and move the member to the
	&quot;removed members&quot; group.
	<li><b>SMS logs:</b> gives you access to the <a
		href="${pagePrefix}reports#sms_log"><u> SMS logs</u></a>, where SMS
	messages sent to this member are kept. The system can be configured to send SMS
	messages on several occasions.
</ul>
<hr class="help">
</span>

<span class="admin"> <a name="ads_actions"></a>
<h3>Member actions: advertisements</h3>
(This overview is part of the &quot;<a href="#actions_for_member_by_admin"><u>actions
for member</u></a>&quot; window.)
<ul>
	<LI><b>Manage advertisements:</b> Add, delete or edit <a
		href="${pagePrefix}advertisements"><u>advertisements</u></a> for this member.
	
</ul>
<hr class="help">
</span>

<span class="admin"> <a name="accounts_actions"></a>
<h3>Member actions: Accounts</h3>
(This overview is part of the &quot;<a href="#actions_for_member_by_admin"><u>actions
for member</u></a>&quot; window.)
<ul>
	<li><b>Account information:</b> brings you to the account history of this
	member, showing the balance, and the transaction history. (view access)
	<li><b>View <a href="${pagePrefix}payments#scheduled"><u>scheduled
	payments</u></a></b>
	<li><b><a href="${pagePrefix}payments#geautoriseerd"><u>
	Authorized payments</u></a></b> This will bring you to the payments which the member should
	authorize as a receiver of the payments. This is applicable for a configuration where a
	receiver of a payment (the member) should authorize the payment before it is
	added to the balance on his account. As an admin, you can act as if you were
	this member and authorize these payments, resulting in adding them to this
	member's balance. This is only available if applicable.<br>
	This corresponds with the payment autorisaties: 
	&quot;<a href="${pagePrefix}groups#manage_group_autorisaties_member"><u>
	authorize when payment receiver</u></a >&quot;.
	<li><b>View payment <a href="${pagePrefix}payments#transfer_authorizations_by_admin"><u>
	authorizations</u></a>:</b> Here you go to the overview of payments which you, as an admin,
	must <a href="${pagePrefix}payments#geautoriseerd"><u> authorize for
	the member</u></a> in order to have them paid from his/her account. This is applicable
	for the situations where members cannot perform certain (or all) payments,
	because they must be geautoriseerd by an admin or broker first.<br>
	This corresponds to the autorisaties: &quot;<a href="${pagePrefix}groups#manage_group_autorisaties_member"><u>
	accounts > view geautoriseerd payments</u></a >&quot;.
	<li><b>Payment system to member:</b> pay the member from a system account.
	<li><b>Payment member to member:</b> Perform a payment to another member
	as if you were your member.
	<li><b>Payment member to system:</b> Perform a payment to the organization
	/ system as if you were your member.
	<li><b>Self payment:</b> Perform a transfer between the different accounts
	of this member. This is only possible if this member has more than one account.
	
	<li><b>View invoices:</b> View all the <a href="${pagePrefix}invoices"><u>invoices</u></a>
	this member has sent and received.
	<li><b>Invoice system to member:</b> send this member an invoice from a
	system account. This means that the organization is sending the member a bill,
	and the member will have to pay this.
	<li><b>invoice member to member:</b> send an invoice to another member as
	if you were your member.
	<li><b>invoice member to system:</b> send an invoice to the system as if
	you were this member.
	<li><b>credit limit:</b> Here you can set the individual
	credit limit for this member. Note that this is the credit limit ONLY for this
	particular member, and not the group credit
	limit which is set on a group level.
</ul>
<hr class="help">
</span>

<span class="admin"> <a name="member_info_actions"></a>
<h3>Member actions: member info</h3>
(This overview is part of the &quot;<a href="#actions_for_member_by_admin"><u>actions
for member</u></a>&quot; window.)
<ul>
	<li><b>View Reports:</b> will bring you to <a
		href="${pagePrefix}reports#member_activities"><u> reports</u></a> with information
	about this member's activity.
	<LI><b>Remarks:</b> Any comment about this member can be put here. These
	remarks are meant for yourself or for other administrators and brokers. None of
	the members are able to see those remarks. If the remark text before the submit
	button is red it means that it contains a remark. If there is no text in the
	remark field the remark text will remain in the default text colour of the
	application.
	<li><b>...:</b> any other <a href="${pagePrefix}member_records"><u>member
	record</u></a> types you defined will be listed here too with a button.
	<li><b>Manage references:</b> Here you can see how other members value
	this member, and you can see what kind of experiences this member has had with
	other members. The provided and received <a href="${pagePrefix}references"><u>references</u></a>
	are displayed. A broker can change or delete the given references of the member
	(if he has the autorisaties)
	<li><b>Transaction feedbacks:</b> allow you to give a <a
		href="${pagePrefix}transaction_feedback"><u> qualification</u></a> to a
	transaction.
	<li><b>send message</b> with the <a href="${pagePrefix}messages"><u>internal
	messaging system</u></a> of cyclos.
	<li><b>Send e-mail</b>
	<LI><b>Member documents:</b> This page gives access to page with member <a
		href="${pagePrefix}documents"><u>documents</u></a> that can be printed.
	<li><b>commission contracts:</b> gives you access to a page where you can
	review or set up <a href="${pagePrefix}brokering#commission_contract"><u>contracts</u></a>
	between you and your members about your brokering services.
</ul>
<hr class="help">
</span>

<span class="admin"> <a name="brokering_actions"></a>
<h3>Member actions: Brokering</h3>
(This overview is part of the &quot;<a href="#actions_for_member_by_admin"><u>actions
for member</u></a>&quot; window.)
<ul>
	<li><b>Set broker:</b> At this page you can change the <a
		href="${pagePrefix}brokering"><u> broker</u></a> the member is
	belonging to.
	<li><b>Member list (as broker):</b> This will only be available if the
	member you are viewing is a <a href="${pagePrefix}brokering"><u>broker</u></a>.
	In that case, it will show the members belonging to that broker, and gives you
	the opportunity to add a new member for this broker.
	<li><b>Commissions settings:</b> This will only be available if the member
	you are viewing is in fact a broker. Here you can view the 
	<a href="${pagePrefix}brokering#commission_contract"><u>commission contracts</u></a>
	this broker is in to, and you can search specific commission contracts between
	the broker and his members.
</ul>
<hr class="help">
</span>

<span class="admin"> <a name="loans_actions"></a>
<h3>Member actions: Loans</h3>
(This overview is part of the &quot;<a href="#actions_for_member_by_admin"><u>actions
for member</u></a>&quot; window.)
<ul>
	<li><b>View Loans:</b> takes you to an overview of the <a
		href="${pagePrefix}loans"><u> loans</u></a> of this member.
	<li><b>Grant loan:</b> Here you give a loan to the member.
	<li><b>Loan groups:</b> Management of the <a
		href="${pagePrefix}loan_groups"><u> loan groups</u></a> the member belongs to.
	
</ul>
<hr class="help">
</span>

<span class="admin"> <a name="actions_for_admin"></a>
<h3>Actions for admin</h3>
Here you can perform several actions in relation to this administrator. You may
want to read the <a href="#actions"><u>general notes</u></a> on this window
first.
<ul>
	<li><b>Send e-mail</b>
	<li><b>Remarks:</b> Any remark about this admin can be put here. The remark is
	a member record that comes with the default database. Any other <a
		href="${pagePrefix}member_records"><u>member record</u></a> types you defined
	for admins will be listed here too with a button. As you can assign member
	records to specific permission <a
		href="${pagePrefix}groups"><u>groups</u></a>, it is possible that there are
	member records defined for admin groups.
	<li><b>Change permission group:</b> allows you to change the groups to
	which an admin belongs. Every admin belongs to a group. This page will allow to
	change the group of the admin. The basic way to control access to the cyclos
	software is via groups. If you should want to remove an admin from the
	system/organization, you should use this button, and move the admin to the
	&quot;removed admins&quot; group.
	<li><b>Allow user to login now:</b> This is only visible if the admin's
	access is blocked, for example because of subsequent tries to login with a
	wrong password. Such an action would block the admin's login for a certain
	period of time, according to the <a href="${pagePrefix}settings"><u>settings</u></a>.
	If such a admin should call the administration in frustration, you can
	immediately deblock the admin's login via this button. After clicking it, the
	admin should be able to retry the login. <br>
	<b>Note:</b> of course, this doesn't reset the admin's login <a
		href="${pagePrefix}passwords"><u>password</u></a>. If the admin doesn't
	readmin his password, you may reset it via the button &quot;manage
	passwords&quot; in the same section of this window.
	<li><b>Disconnect logged user:</b> If the admin is logged in at this very
	moment, you can forcibly log him out by clicking this button. This button is <b>only</b>
	visible if the admin is logged in at present. <br>
	A reason for logging out a admin would be if a admin closed the browser without
	logging out correctly. If the admin wants to login within a certain time
	(timeout) a message will inform the admin that the admin is still logged. When
	the admin contacts the administration he/she can be logged out with this
	function. Another option however is, just to wait until the time out period is
	finished; the default time out value is 10 minutes.<br>
	Another reason for logging out a admin would be in case of an emergency like
	suspected abuse or hacking of an account. It is recommended in such cases, to
	(temporary) disable this admin after having him/her forcibly logged out (you
	can do this via the &quot;Change Permission Group&quot; button on this page).
	<li><b>Manage passwords:</b> here you can reset the admin's passwords.
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