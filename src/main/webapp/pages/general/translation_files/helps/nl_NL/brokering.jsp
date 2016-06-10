<div style="page-break-after: always;"
<br><br>
A member of a &quot;Broker&quot; type of group can register new members and have
some level of access and control over these Members (depending of the
configuration of the Broker group). The name &quot;broker&quot; is not very
adequate because this function can be used for many different purposes.<br>
A common broker function is that a broker can receive <a href="#commission"><u>
commission</u></a> when registering members. The commission can be configured on the
amount of trade done by the new members. The idea is that this is an incentive
for brokers to take the &quot;buddying&quot; of new members serious.<br>
A broker may also be allowed to do part of the personal administration for a
group of members who feel too insecure with computers to do certain cyclos
member tasks themselves. <br>
<span class="broker admin"> The broker function can also be used by loan
agents in micro finance systems. The loan agent as a broker can also register
new members and retrieve information about the loan status of the members.
Community systems like LETS can use the broker function for neighborhood
(account) administrators that can assist other members that do not have access
or the ability to use Internet/mobile/scrip. Different types of broker groups
can exist in the same system. </span>
It is possible for brokers to set up
<a href="#commission_contracts"><u>contracts</u></a>
on the commissions with each individual member.

<br><br><span class="broker"> <i>Where to find it?</i><br>
You can reach all brokering functions via the &quot;Menu: Brokering&quot;.<br>
Brokering information and functions related to one of your &quot;brokered&quot;
members can be found at the <a
	href="${pagePrefix}profiles#actions_for_member_by_broker"><u>broker
actions page</u></a> below the <a href="${pagePrefix}profiles#member_profile"><u>profile</u></a>
of that member. </span>
<br><br><span class="admin"> <i>Where to find it?</i> <br>
Administrators do not have brokering functions but they can have a certain level
of access to the brokering functions of both broker and members that have been
assigned to a broker. This can be done in the <a
	href="${pagePrefix}profiles#actions_for_member_by_admin"><u>actions
page</u></a> below the <a href="${pagePrefix}profiles#member_profile"><u>profile</u></a>
of the member or broker.<br>
As explained the brokering function can be used for many purposes. Therefore
searches and functions related to brokers may appear in other modules. For
example a loans search can have a broker search filter and an admin can perform
bulk actions related to a broker. These functions are explained within the
context of that function.

<br><br><i>How to get it working?</i><br>
In order to enable brokering you will have to set the <a
	href="${pagePrefix}groups#manage_group_autorisaties_broker"><u>broker
autorisaties</u></a> in block &quot;brokering&quot; and <a
	href="${pagePrefix}groups#edit_broker_group"><u>broker group settings</u></a>
in block &quot;brokering&quot;. The latter has only one item; if you want 
brokers to be able to register members, you must define that here.<br>
Broker commissions can be enabled at <a
	href="${pagePrefix}account_management#transaction_type_details"><u>
transaction type</u></a> level in the <a
	href="${pagePrefix}brokering#broker_commission_list"><u>brokering
comissions</u></a> configuration.<br>
You will also need to set the <a
	href="${pagePrefix}groups#manage_group_autorisaties_broker"><u> broker
autorisaties</u></a> for this (under block &quot;brokering&quot;). For a member to view
the commissions, also <a
	href="${pagePrefix}groups#manage_group_autorisaties_member"><u>
autorisaties</u></a> must be set (block &quot;Commissions&quot;).
<br><br><b>Note</b> that it does not make any sense to give brokers or members
access to commissions, without defining and enabling commissions as an admin in
the transaction type configuration, as pointed out above. In such a case,
brokers and members will be confronted with empty boxes because they have access
to brokering commissions and contracts without being able to adapt or define
these, because they simply don't exist.<br>
Be sure not only to create the broker commission fee, but also to have it
enabled.
</span>
<hr>

<span class="broker admin">
<A NAME="broker_search_member"></A>
<h3>Search member of broker</h3>
This function gives a list with the members of the broker.
<br>
You can search on:
<ul>
	<li><b>Login name / Member:</b> Search for individual member
	<li><b>Permissions group:</b> Search by group
	<li><b>Status:</b>
	<ul>
		<li><b>Active: </b>These are the Members who are active in the system (in
		an &quot;active&quot; group)
		<li><b>Commission ended: </b> Shows the members for which the <a href="#commission"><u>
		commission</u></a> has been received and ended.
		<li><b>Awaiting activation: </b>These are the members you have registered but are not
		active yet (because they must be activated by a broker or admin first). 
	</ul>
</ul>
<hr class="help">
</span>

<span class="broker">
<A NAME="broker_search_member_result"></A>
<h3>Broker - search member result</h3>
This window shows the list of members that have been registered with you as a broker.
<br>
In the member list you can select the member or login name to open the members
<a href="${pagePrefix}profiles"><u>profile</u></a> page.
<hr class="help">
</span>

<span class="admin">
<A NAME="admin_brokering_list"></A>
<h3>Member list (of broker)</h3>
This page shows a list of all the members related to a particular broker. You
can select the name to go into the member profile.<br>
Clicking the <img border="0" src="${images}/delete.gif" width="16" height="16">&nbsp;delete
icon will unassign the broker from the member (you will be asked for confirmation).  
<hr class="help">
</span>


<span class="admin">
<A NAME="add_member_to_broker"></A>
<h3>Add member to broker</h3>
In this page you can add a member to the brokering list of the broker. The login
and member name fields will auto-complete when typing. If the member is related
to another broker and there is still a <a href="#commission"><u>commission</u></a>
active you can suspend this by selecting "Suspend commission".<br>
Note that you can reassign a whole set of members to another broker via the 
<a href="${pagePrefix}user_management#bulk_actions"><u>bulk actions</u></a>.
<hr class="help">
</span>

<span class="admin">
<A NAME="change_broker"></A>
<h3>Set broker / Change broker of...</h3>
In this page you can set the broker a member is related to. The field
&quot;Current broker&quot; will show the current broker of the member, if applicable. This
field can be empty &quot;none&quot; because a member is not necessarily related to
a broker. If you want to relate a member to a broker you can type the new broker
in the auto complete login or name fields. When the broker shows up you can
change the (selected) broker by writing a comment and hit the submit button
below.<br>
If you want to stop any <a href="#commission"><u>commission</u></a> 
that is still active for the current broker you can select the field 
&quot;Suspend commission&quot;. If you don't the 
new broker will inherit the commission settings of the old one.
This means that the commission will be charged from the day that the broker 
got the new member until the end date of the commission as configured in the 
commission settings. 
<hr class="help">
</span>

<span class="admin">
<a name="remove_member_to_broker"></a>
<h3>Remove member</h3>
The title of this window may be a bit alarming, but in this window the only thing
that will happen if you click submit, is that the member is no longer registered
with his broker. So the broker is un-assigned from the member.<br>
Before you click the &quot;submit&quot; button you can add a comment
about the reason of removal.<br>
Note that you can reassign a whole set of members to another broker via the 
<a href="${pagePrefix}user_management#bulk_actions"><u>bulk actions</u></a>.
<hr class="help">
</span>


<a name="commission"></a>
<h2>Broker Commissions</h2>
For his work, a broker may receive a commission; this is a small payment connected 
to the activity of the broker's members. When a broker registers a new member, usually
this member becomes one of the broker's members. The broker can get a small payment for each
transaction this new member is involved. The idea behind this is that it will stimulate
brokers to take the buddying of new members seriously.

When a broker is moved to any other group all contracts and running
commissions will be closed.
<hr>

<span class="admin broker">
<A NAME="broker_commission_list"></A>
<h3>Broker commissions list</h3>
This windows shows a list with all configured broker <a href="#commission"><u>
commissions</u></a> (either enabled or disabled)
<ul>
	<li><img border="0" src="${images}/edit.gif" width="16" height="16">
	Click the icon to modify the brokering.
	<li><img border="0" src="${images}/delete.gif" width="16" height="16">
 	Click the icon if you want to delete the brokering.
</ul>
To add a new commission click &quot;Insert new broker commission&quot;.
<hr class="help">
</span>


<span class="admin broker">
<A NAME="broker_commission_details"></A>
<h3>Broker commission details</h3>
Just like a transaction fee a broker <a href="#commission"><u>commission</u></a> 
is credited/debited when the &quot;parent&quot; transaction is performed and the criteria of the broker
commission matches.<br>
As always, use the &quot;change&quot; button below in order to be able to make changes;
click &quot;submit&quot; to save your changes.
<br><br>
The commission has the following configurations:
<ul>
	<li><b>Transaction type:</b> This is the transaction type on which the
	broker commission is invoked.
	<li><b>Name:</b>	Name of the broker commission.
	<li><b>Description:</b>	Description of the commission.
	<li><b>Enabled:</b> The commission is active when checked. Be sure to check this, 
	otherwise it will not work.
	<li><b>Will be charged:</b> Here you can define who will be charged. This
	can be the payer, receiver or a system account.
	<li><b>Will receive:</b>
	Here you can define who will receive the fee. This can be the broker of the payer
	or the broker of the receiver.
	<li><b>Allow any account:</b>
	If this is checked there is no limitation of the realm in which the transaction
	fee can be applied. If you charge the fee on another account than the
	account belonging to the invoking transaction type you will need to check this option.
	For example if you want transaction types in another currency for the commission.
	<li><b>Generated transaction type:</b>
	Here you define what transaction type will be generated. It is common to create
	a specific transaction type for this so that you can filter on it (e.g. in
	account history: fees & taxes). The default database comes with a transaction
	fee and transaction type for the transaction fee.
	<li><b>Amount:</b>
	Here you can define the amount of the commission. The broker will receive
	the commission each time his member makes a payment (and if the condition of
	applicability is met).<br>
	In case of broker <a href="#commission_contract"><u>contracts</u></a>,
	the broker commission values can be changed by the 
	broker. In this case, the amount value will be used as the default commission value
	for the contract. The broker can changes
	these values when creating &quot;broker contracts&quot; for each of its
	members. See also next item.
	<li><b>Max amount fixed and %:</b>
	These options are related to broker contracts and will only show up if at the
	&quot;Will be charged&quot; option a member is specified (and not a system
	account). The value will define the maximum amount that a broker can put in a
	broker contract. (a broker needs to have autorisaties to manage broker contracts)
</ul>

<li><b>Conditions of applicability</b><br> 
Here you can define under what
conditions the fee will be applied. Only if the conditions match the fee will be
applied. The conditions can be combined.
<ul>
	<li><b>Amount greater or equals:</b>
	The fee will only be charged if the transaction amount is greater than or equals the
	specified amount.
	<li><b>Amount less or equals</b><br>
	The fee will only be charged if the transaction amount is less than or equals
	the specified amount.
	<li><b>From all groups:</b>
	If this is checked the fee will apply for members of any group paying a payment of 
	the transaction type. If you want to apply the fee only for specific groups, then
	you will need to uncheck this box, and a multi select drop down will appear, 
	allowing you to specify the groups.
	<li><b>To all groups</b><br>
	If this is checked the fee will apply for members of any group receiving a payment 
	of the transaction type. If you want to apply the fee only for specific groups, then
	you will need to uncheck this box, and a multi select drop down will appear, 
	allowing you to specify the groups.
	<li><b>All broker groups</b><br>
	If this is checked the fee will be applied to brokers of any broker group involved. 
	If you want to apply the fee only to brokers of specific broker groups, then
	you will need to uncheck this box, and a multi select drop down will appear, 
	allowing you to specify which broker groups will be involved.
	<li><b>When commission is paid:</b>
	Here you can define when the commission will be charged. This can be:
	<ul>
		<li><b>Always:</b>
		The broker commissions payments will always (infinitely) be paid. (the
		commission can be stopped from the page: member profile - set broker).
		<li><b>Transactions:</b>
		The broker commissions payments will stop after a given number of
		transactions. The amount can be filled in the input field which will appear before
		this drop down if you choose this option.
		<li><b>Days:</b>
		The broker commissions payments will stop after a given number of days. The
		amount can be filled in the input field which will appear before the select box after
		you choose this option.
	</ul>
</ul>
<hr class="help">
</span>

<span class="admin broker">
<A NAME="commission_settings"></A>
<h3>Commission settings</h3>
In this window you can check the default <a href="#commission"><u>commission</u></a>
settings. The settings will be applied for all new registered members, unless
they are overruled by the changes the broker has made to his settings, or by 
individual <a href="#commission_contract"><u>contracts</u></a> between
brokers and members.
<br>If the administration did not define any commission settings, this window will be empty. 
If you have enabled commission management for members and brokers, you should
also define a default commission. See top of this document.
</span>
<span class="broker">
In this window you can define the default <a href="#commission"><u>commission</u></a>
settings. These settings will be applied to all YOUR new registered member. As long as you haven't
set the default commission in this window, the administration can change the default
commission settings. As soon as you have set your commission settings in this screen, 
they will overrule the settings of the administration.
<br>Note that any individual <a href="#commission_contract"><u>contracts</u></a> 
you set up with your members, will again overrule the settings you change here. 
<br><br>
If the administration did not define any commission settings, this window will be empty. 
In that case, you should notify them about this.
<br><br>
You can change your settings by clicking the &quot;change&quot; button below; use the
&quot;submit&quot; button to save your changes. This will only be visible in case you have
autorisaties to change the default settings.
<br>

The status cannot be changed. 
</span>
Status can have the following values: <br>
<ul>
	<li><b>Active:</b>
	This means that all configured broker commissions will be charged if the
	conditions apply.
	<li><b>Inactive:</b>
	This status means that no commissions can be charged. If this is the case, it means
	that this is configured system wide, and that it is set by an administrator.
	<li><b>Suspended:</b>
	The broker commissions are temporary suspended. 
</ul>
<span class="admin"> An administrator has the option to suspend (stop temporary) 
all active commissions. Even when broker <a href="#commission_contract"><u>contracts</u></a>
are enabled a broker can add new commissions 
contracts but they will enter directly in the suspended status.
</span>
<hr class="help">

<a name="commission_contract"></a>
<h2>Commission Contract</h2>
A commission contract is an arrangement between a broker and a member. Normally, 
the broker receives either a percentage of each payment of the member or a fixed amount
with each payment. This <a href="#commission"><u>commission</u></a> can be paid by the 
paying member, the receiving member, 
or can be paid by the organization (from a system account).<br>
Depending on the configuration, brokers are free to set up a different individual contract with 
each of the members assigned to them. The member has to agree upon this contract
before it will be applied - the member can either accept or deny a newly proposed
broker contract. 

The member can view the commission details and both member and broker will receive 
a notifications when the status of the commission has been changed.
<br><br>
<span class="broker">
Depending on the system settings a broker can define the commission per member. 
There can only be one active commission per period (it is possible to have more
commissions if the commission period do not overlap).<br> 
<br>Note: When a broker is moved to any other group all contracts and the
commissions will be closed.
<br><br>
<i>Where to find it?</i><br>
Commission contracts can be found in the &quot;Menu: Brokering > commission contracts&quot;.
You can add a new commission contract for a member via the member's profile, in the
&quot;brokering actions&quot; window, button &quot;Commission contracts&quot;. At the bottom of
the page you will arrive there will be a button to create a new contract.
</span>
<span class="member"><%-- dit is ook zichtbaar voor brokers. Als broker zie ik nu 2x where to find --%>
<i>Where to find it?</i><br>
You can find the brokering contracts in the &quot;Menu: Personal > commission charge status&quot;. 
This is only visible if you have the persmissions for it.
</span>
<hr>

<span class="admin broker">
<A NAME="commission_contracts_search_filters"></A>
<h3>Broker Commission contracts search</h3>
In this page you can search for existing <a href="#commission_contract"><u>
commission contracts</u></a>.<br>
Most search options are self explanatory. The status is explained
<a href="#commission_contract_status"><u>here</u></a>. 
<hr class="help">
</span>

<span class="admin broker">
<A NAME="commission_contracts_search_results"></A>
<h3>Commission contracts search result</h3>
This window will show a list with all the <a href="#commission_contract"><u>
commission contracts</u></a> and their <a href="#commission_contract_status"><u>status</u></a>.
<ul>
	<li><img border="0" src="${images}/edit.gif" width="16" height="16">
	Select the magnifying glass icon the enter the details.
	<li><img border="0" src="${images}/delete.gif" width="16" height="16">
	Click the delete icon if you want to delete the contract. This is only
	visible if you have the autorisaties, and if the contract is not in 
	active status.
</ul>
<hr class="help">
</span>

<a name="commission_contract_status"></a>
<h3>Commission contract status</h3>
A <a href="#commission_contract"><u>commission contract</u></a> can have the following status:
<ul>
	<li><b>Pending: </b>
	The contract will be in the pending status until the member did confirm and
	accepts the commission contract.
	<li><b>Accepted: </b>
	This status means that the commission has been accepted by the member but the
	begin date of the contract has not been reached yet.
	<li><b>Active: </b>
	Once the broker commission has been accepted and the begin date has been reached
	the commission contract will go in the active status, meaning that the
	commission will be charged according the settings of the contract.
	<li><b>Denied: </b>
	If a contract is denied by the member it will go in the denied status.
	<li><b>Expired: </b>
	If a contract is not accepted before the begin date it will go into the expired status.
	<li><b>Canceled: </b>
	A broker can cancel a commission contract what means that future payments won't
	generate commissions anymore.
	<li><b>Closed: </b>
	The commission contract has been ended because the end date has been passed and 
	commissions have been charged.
</ul>
<hr class="help">

<A NAME="commission_charge_status"></A>
<h3>Commission status</h3>
This window shows a quick result with information about the current
<a href="#commission"><u>commission</u></a>.
This can be a default commission or an individual broker <a href="#commission_contract"><u>
commission contract</u></a>.
The information is self-explanatory. A contract can be in one of various options for 
<a href="#commission_contract_status"><u>status</u></a>.
<hr class="help">

<A NAME="commission_contracts_list"></A>
<h3>Commission contracts list</h3>
This window will show a list with all the <a href="#commission_contract"><u>
commission contracts</u></a> for this member, 
and their <a href="#commission_contract_status"><u>status</u></a> .
<span class="admin broker">
<ul>
	<li><img border="0" src="${images}/view.gif" width="16" height="16">&nbsp;
	Select the magnifying glass icon the enter the details. 
	<li><img border="0" src="${images}/delete.gif" width="16" height="16">&nbsp;And the delete icon to 
	delete a contract. This is only visible if you have the autorisaties, and if the
	contract is not in active status.
	<li>you can <b>add</b> a new commission contract by making a type choice in the
	drop down entitled &quot;New Contract&quot;, at the bottom right of this window.
	This drop down will be empty if the administration did not define any commission settings.
	In this case, you should notify them about this.
</ul>
</span>
<span class="member">
You can click the <img border="0" src="${images}/view.gif" width="16" height="16">&nbsp;
magnifying glass icon to see the details. This will take you to a window where you can 
accept or deny the proposed contract, in case the status is &quot;pending&quot;.
</span> 
<hr class="help">

<A NAME="commission_contract_edit"></A>
<h3>Modify/View broker Commission contract</h3>
The window shows the details of the <a href="#commission_contract"><u>
commission contract</u></a> you selected. 
<a href="#commission_contract_status"><u>Click here</u></a> for an overview of the 
possible values of the status field. <br>
Once active, a commission contract cannot be deleted. However, you can
<a href="#commission_contract_status"><u>cancel</u></a>
the contract by clicking the &quot;cancel&quot; button.
<br><br>
<span class="member">If the status is &quot;pending&quot;, it means this is a new
contract proposed by your broker to you. In this case, you can accept or deny
the proposed arrangement, by clicking either of the two buttons at the bottom.
A proposed broker contract will not be applied unless you accepted it.</span>
<hr class="help">

<A NAME="commission_contract_insert"></A>
<h3>Insert Broker Commission contract</h3>
The window allows you to create a new <a href="#commission_contract"><u>
commission contract</u></a> for the member selected.<br>
The status is always &quot;pending&quot;, since the contract can only go into
another status as soon as the member accepted it or denied it - which is impossible
at the moment, because you're just creating it.<br><br>
You must set the &quot;start date&quot;, &quot;end date&quot; and amount for the
new contract. For the dates you may use the date picker via the 
<img border="0" src="${images}/calendar.gif" width="16" height="16">&nbsp;icon.<br><br> 
Once active, a commission contract cannot be deleted. However, you can
<a href="#commission_contract_status"><u>cancel</u></a>  
the contract by clicking the &quot;cancel&quot; button.  
<hr class="help">

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

