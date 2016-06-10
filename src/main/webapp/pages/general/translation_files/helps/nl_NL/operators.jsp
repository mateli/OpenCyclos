<div style="page-break-after: always;"
<a name="top"></a>
<br><br>The operators function allows a member to define
operators: a sort of sub-level members who are allowed to do some tasks in
cyclos for the member. Operators don't have an own account but only have access
to their member account where they can do some operations. Think of a small
company with three employees, where each employee will become an operator for
the company's account.<br>
Just as members, operators are organized in groups. Each member may define its
own operator groups, allowing a member to create different levels of operators
with different autorisaties. You could for example create one super operator who
is allowed to perform payments, and a few simple operators who can only manage
advertisements. Every payment performed by an operator will have an extra field
&quot;Performed by&quot; and the member has the possibility to search payments 
by operator.
<span class="member notOperator"> 
<i>Where to find it?</i><br>
The operators functions can be reached via
the main &quot;Menu: Operators&quot;. Under this main menu section there are
several sub-menu's giving access to the operator functionality:
<ul>
	<li><b>Operators:</b> will bring you to the search window for operators.
	You can also create a new operator here.
	<li><b>Connected operators:</b> shows which operators are connected.
	<li><b>Operator groups:</b> allows you to define different levels of
	operators.
	<li><b>Custom fields:</b> allows you to create special fields for
	operators.
</ul>
</span>
Operators can login via a the normal login page, which will show a extra link
&quot;Login as operator&quot; (the operators module needs to be enabled in the
system settings in order for this link to show up).<br>
Operators can be also been given access to the POSweb module (information about 
POSweb can be found at the Channels <a
	href="${pagePrefix}payments#accesing_channels"><u>Help page</u></a>.


<br>
<span class="member"> Operators can perform their actions regarding the
member from a special operator main menu item, which is only visible for
operators, and which is called &quot;member operations&quot;. Via this, the
operator can access all functionality which would normally fall under the
&quot;Main menu: personal&quot; item for the member. </span>
<br><br>
<span class="admin">
<i>How to get it working?</i><br>
Operators must be enabled first via the <a
	href="${pagePrefix}groups#manage_group_autorisaties_member"><u>autorisaties</u></a>
of a group, block &quot;Operators&quot;, the checkbox entitled &quot;manage operators&quot;.<br>
Besides setting the autorisaties, for operators to be able to login make sure
that operators are enabled in the <a href="${pagePrefix}settings#access"><u>
acces settings</u></a> via the &quot;allow operator
login&quot; checkbox.
<br><br><b>Note:</b> If you customize the login page, be sure to keep the code
which is used to enable operators to login. Otherwise the operator login link
might not be visible. 
<br><br><b>Note 2:</b>
The operator module and the
operations are entirely the responsibility of the member. An admin cannot
manage operators for a member. The only administration an admin can do is
disconnect operators from the <a
	href="${pagePrefix}user_management#connected_users_result"><u>Connected
users</u></a> page.
</span>
<hr>

<span class="member notOperator">
<a name="search_operator"></a>
<h3>Operators search</h3>
In this page you can search for operators (that you have registered). The
function works the same a the common member search function. In the group select
box you can leave the &quot;All groups&quot; filter or select one or more groups
you want to search in. <br>
Click &quot;search&quot; to display the results of your search.
<br><br>You can also create a new operator. This is done by selecting an operator
group in the drop down box below this window (&quot;create a new
operator&quot;). This drop down box is only visible if there is no search result
window.
<hr class='help'>
</span>

<span class="member notOperator"> <a name="search_operator_result"></a>
<h3>Search results operators</h3>
This page will show the result list for the operator search. Clicking the name
or login name of the operator will open the profile page.
<hr class="help">
</span>

<span class="member notOperator"> <A NAME="create_operator"></A>
<h3>Create operator</h3>
In this page you can create a new operator. All the fields marked with a red
asterisk (*) are obligatory. <br>
After filling in the fields you can either go directly to the profile (button
&quot;Save and open operator profile&quot;) or add a new operator (button
&quot;Save and insert a new operator&quot;).
<hr class='help'>
</span>

<a name="operator_profile"></a>
<span class="member">
<h3>Operator profile</h3>
This window shows the profile of the operator. Most fields cannot be changed,
though some can. You should click the &quot;change&quot; button in order to make
changes; when done, click &quot;submit&quot; to save the changes. </span>
<span class="member notOperator">
<br><br>If this very operator is logged in at the moment you check this screen,
this will be notified. The field &quot;last login&quot; will show (in red) the
words &quot;is logged&quot;.
<hr class='help'>
</span>

<span class="member notOperator">
<A NAME="actions_for_operator_by_member"></A>
<h3>Actions for operator</h3>
Here you can perform several actions on this operator. This help gives a summary
of all the actions. For a more detailed explanation of the actions you can refer
to the help within the specific actions.
<br><br>The following actions are available:
<UL>
	<LI><b>Change Permission group:</b> change the operator group this
	operator belongs to.
	<LI><b>Send e-mail:</b> Send an e-mail to the operator. This will open
	your local e-mail program.
	<LI><b>Manage login password:</b> Change the password for the operator.
	<li><b>Allow user to login now:</b>This is only visible in case the
	operator tried to login with a wrong password for several times, and got his
	account temporary blocked for that. Normally, there is a maximum number of
	tries allowed; if you try to login more often with a wrong password, your
	account will be blocked temporary for an amount of time which is set by the
	administrators. If you're sure this operator is who he says he is, you can
	immediately allow him to login again by clicking this button. In that case, the
	operator doesn't have to wait until the normal waiting time for this has
	passed.
	<li><b>Disconnect logged user:</b> This will only be visible when the
	operator is logged at this very moment. This is also indicated by the field
	&quot;last login&quot; in the profile window above; this will show the words
	&quot;Is logged&quot;. In this case, you can immediately force the operator out
	of the program by clicking this button. You may want to do this when for
	example there is an investigation pending on abuse, or when the operator cannot
	login because the system thinks he is already logged in.
</UL>
<hr class='help'>
</span>

<span class="member notOperator">
<A NAME="manage_operator_groups"></A>
<h3>Manage operator group</h3>
This page shows a list with the
<a href="#top"><u>operator</u></a>
groups. You can perform the following actions here:
<ul>
	<li><img border="0" src="${images}/edit.gif" width="16" height="16">&nbsp;
	the edit icon will take you to a page with settings for this group.
	<li><img border="0" src="${images}/autorisaties.gif" width="16" height="16">&nbsp;
	the autorisaties icon will take you to the page where you can set the
	autorisaties for this group. This icon will be disabled (greyed out, <img
		border="0" src="${images}/autorisaties_gray.gif" width="16" height="16">&nbsp;)
	when the group has a &quot;Removed&quot; status.
	<li><img border="0" src="${images}/delete.gif" width="16" height="16">&nbsp;
	clicking the delete icon will remove the group. You can only delete groups if
	there are no members (operators) in it.
	<li><b>adding:</b> To add a new operator group you will have to click the
	submit button labeled &quot;Insert new group&quot;.
</ul>
<hr class='help'>
</span>

<span class="member notOperator">
<A NAME="manage_group_autorisaties_basic"></A>
<h3>Basic group autorisaties</h3>
In this window you can set the basic autorisaties. The basic autorisaties do not
affect other functions, e.g. if an operator cannot login, he still might be able
to receive payments. The following autorisaties can be set:
<ul>
	<li><b>login: </b><br>
	If this is not checked, operators of this group cannot login.
	<li><b>invite message: </b><br>
	Ff checked, members of this group see a window box in there main page (after
	logging in), with which they can invite a friend to try out your organization.
</ul>
<hr class='help'>
</span>

<span class="member notOperator"> <A
	NAME="manage_operator_group_autorisaties"></A>
<h3>Manage operator group autorisaties</h3>
In this window you can set the <a href="${pagePrefix}groups#autorisaties"><u>
autorisaties</u></a> for an <a href="#top"><u>operator</u></a> group. These autorisaties
are derived from you own group's autorisaties: an operator can never do more than
you yourself are allowed to; an operator can only do less or equal. <br>
For this reason, most likely you will not see all the options listed in this
help overview. Use the links to get more information about the item.
<br><br>The operators that belong to this group may receive these autorisaties
(depending on the system settings and your own autorisaties):
<br><br>
<b>Member Account</b>
<ul>
	<li><b>View geautoriseerd payments</b>
	<li><b>View scheduled payments</b>
	<li><b>View account information:</b>
	Use the drop down box to select for which account the operator can see the
	information (payments, balance, etc).
</ul>
<b>Ads</b>
<ul>
	<li><b>Publish</b> If &quot;Publish&quot; is selected a operator can
	publish advertisements and the menu item &quot;Personal - Advertisements&quot;
	will show up in the operator menu.
</ul>
<b>Contact list</b>
<ul>
	<li><b>Manage:</b> allows the operator to manage the <a
		href="${pagePrefix}user_management#contacts"><u> contact list</u></a>, that is, to add, edit
	or delete members from the list.
	<li><b>View:</b>allows the operator to only view the contact list (and use
	it), but there is no permission to change it.
</ul>
<b>Guarantees</b><br>
This is part of the Guarantees system of cyclos, where each account balance in cyclos is guaranteed by a backed
sum of money. You may choose the following autorisaties:
<ul>
	<li><b>Sell with payment obligations:</b> 
</ul>

<b>Invoices</b> <br>
In this section you can define if a operator can send <a
	href="${pagePrefix}invoices"><u>invoices</u></a> to other members, either from a
user profile or directly form the &quot;Account&quot; menu. When the
&quot;System invoices&quot; is selected a operator can send invoices to system
accounts from the &quot;Account menu&quot;.
<ul>
	<li><b>Member invoice option from menu:</b> Will show the option invoice to member from the menu
	<li><b>Send to member:</b> Allows to send invoices to other members.
	<li><b>Send to system:</b>Allows to send invoices to a system account.
	<li><b>View:</b> View invoices.
</ul>

<li><b>Loans:</b> In this section you can define the autorisaties for <a
	href="${pagePrefix}loans"><u>loans</u></a> for operators.
<ul>
	<li><b>View:</b> If the &quot;View&quot; option is selected the operators
	of the group can view its loans. If view is not selected the menu items do not
	show up.
	<li><b>Repay:</b> select this to allow the operator to perform loan
	repayments.
</ul>

<li><b>Messages:</b> In this section you can define to what extend the
operator may use the <a href="${pagePrefix}messages"><u>messages</u></a> system
of Cyclos.
<ul>
	<li><b>View:</b> The operator can view the message system.
	<li><b>Send to member:</b> The operator is allowed to send messages to other members.
	<li><b>Send to administration:</b> The operator is allowed to send messages to the administration. 
	<li><b>Manage:</b> The operator can move and delete messages.
</ul>


<b>Payments:</b> Here you can specify what type of payment is allowed for this operator group.
Mostly you will select only one or a few types.
<ul>
	<li><b>Self payment:</b> If this is selected the operator can make payment
	between your own accounts. In the drop down you can specify the possible
	transaction types. This option will only make sense if you have more than one
	operator account.
	<li><b>Member payments:</b> If selected the operator can pay another member.
	<li><b>Member payment option from menu:</b> If this option is checked the
	operators can perform payments to other members directly from the
	&quot;Account&quot; menu.
	<li><b>System payments:</b> If selected the operator can pay to a system
	account. If this option is not selected the menu item &quot;System
	payment&quot; will not show up.
	<li><b>Make POSweb payment:</b> allows an operator to pay at POSweb (Point
	of Sale) page.
	<li><b>Receive POSweb payment:</b> Choose this option in case you want to
	allow operators to receive payments via WEBpos. This would typically be a
	situation in a shop. The shop person at the counter would login as operator
	(normally via http://..cyclos/posweb) to the POSweb interface; in the following
	window the client can enter his PIN to make a payment to the shop. <br>
	Checking this checkbox allows this procedure. (Normally, you would then disable
	all other autorisaties for the operator.)
	<li><b>Authorize or deny:</b> allows the operator to authorize or deny a
	payment if you are the receiver.
	<li><b>Cancel payment authorization:</b> When geautoriseerd payments are
	used, this will allow operators to cancel their geautoriseerd payments once they
	have been created, but not yet been geautoriseerd.
	<li><b>Cancel scheduled payment:</b> When scheduled payments are used,
	this will allow operators to cancel their scheduled payments before the planned
	date has started.
	<li><b>Block scheduled payment:</b> allows the operator to block his
	scheduled payment temporary.
	<li><b>Request payments from other channels:</b> when this is checked, the
	operator can send payment requests (external invoices) over other channels; you can choose
	these channels from the drop down box.
</ul>

<b>References</b> <br>
This allows the operator to view or manage <a
	href="${pagePrefix}references"><u> references</u></a>.
<ul>
	<li><b>View:</b> view the references
	<li><b>manage my references:</b> allows the operator to use the reference
	system, including the permission to give references to other members.
	<li><b>Manage my transaction feedbacks:</b> allows the operator to manage
	your <a href="${pagePrefix}transaction_feedback#feedbacks_summary"><u> transaction
	feedbacks</u></a>, including the permission to give feedback on transactions.
</ul>

<b>Reports</b><br>
If &quot;View my reports&quot; is selected the operator can view
your own <a href="${pagePrefix}reports#member_activities"><u> reports
pages</u></a>.
</ul>
<hr class='help'>
</span>

<span class="member notOperator"> <a name="edit_operator_group"></a>
<h3>Modify operator group</h3>
The <a href="#top"><u>operator</u></a> group configuration is devided in two
parts:
<ul>
	<li><b>Group Details:</b> Here you can only change the name and
	description of the operator group.
	<li><b>Max amount per day per payment type:</b> With these settings you
	can define the maximum amount per day per payment type. All available payment
	types are listed here; for each type, you can specify if there is a limit in
	the amount an operator can pay as this payment type.
</ul>
You can go directly to the permission of this group by clicking &quot;Group
autorisaties&quot;.
<hr class='help'>
</span>

<span class="member notOperator">
<A NAME="insert_operator_group"></A>
<h3>Insert new operator group</h3>
This window allows you to create a new
<a href="#top"><u>operator</u></a>
group.
<br>
You have the following options
<ul>
	<li><b>Removed:</b> If you create a removed group it means that the
	Operators that are put in this group have really left the system. Once in the
	Removed group one cannot get back in any other group. The data will still be in
	the Database and viewable by you but it serves as a backup function only.
	<li><b>Name:</b> Name of the group.
	<li><b>Description:</b> Description of the group.
	<li><b>copy settings from:</b> This will only be visible if there is
	already another operator group defined. You can specify another operator group
	here; then the settings for the newly created group will be copied from the
	group you specified.
</ul>
After having created the new group, you should set the group properties in the
next screen, and you should also set the group's autorisaties.
<hr class='help'>
</span>

<span class="member notOperator">
<a name="manage_group_customized_files"></a>
<h3>Customized POSweb</h3>
You can define the header and footer for the POSweb.
This window shows a list with customizations for this group.
You have the following options:
<ul>
	<li><b>Modify</b> an existing customized file via the <img border="0"
		src="${images}/edit.gif" width="16" height="16">&nbsp;edit icon.
	<li><b>View</b> how the result will look for a member of the group via the
	<img border="0" src="${images}/view.gif" width="16" height="16">&nbsp;view
	icon.
	<li><b>Delete</b> a customized file definition via the <img border="0"
		src="${images}/delete.gif" width="16" height="16">&nbsp;delete icon.
	<li><b>Insert</b> a new customized file via the &quot;customize new
	file&quot; button. 
</ul>
<hr class="help">
</span>


<span class="member notOperator"> <a name="customize_group_file"></a>
<h3>Modify POSweb header & footer</h3>
In this page you can customize the header and footer of the POSweb page. This
the page that can be accessed by operators to make and receive payments. (the
POSweb URL is mostly something like www.domain.com/cyclos/posweb)<br>
After the operator logged in the header and footer will show up above and below
the payment window.
<hr class="help">
</span>


<span class="member notOperator">
<A NAME="change_group_operator"></A>
<h3>Change operator group</h3>
In this window you can place an
<a href="#top"><u>operator</u></a>
in another group. This means that the operator will receive the autorisaties of
the other group. After having filled in the form, click the &quot;Change
group&quot; button to save and commit your changes.
<br>
<br><br>Clicking the &quot;Remove permanently&quot; option will remove the
operator. This is only possible if the operator did not perform any transactions
yet.<br>
Otherwise, you will have to move it to a &quot;Removed&quot; group; this means
that the operator cannot perform any more actions (not even login), but his
actions in the passed can still be viewed by you.
<hr class='help'>
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