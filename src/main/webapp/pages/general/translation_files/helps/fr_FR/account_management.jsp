<div style="page-break-after: always;">
<span class="admin broker">
<br><br>All configurations relating to accounts and
transactions can be done in the accounts management functions.

<i>Where to find it.</i><br>
Account management can be accessed via the &quot;Menu: Accounts > Account
management&quot;.
<hr>

<A NAME="currencies"></A>
<h2>Currencies</h2>
It is possible to create a new currency and associate accounts to those
currencies. There can be a default currency set per group.

<i>Where to find it.</i><br>
Currency can be accessed via &quot;menu: Accounts > Manage currencies&quot;.
<hr>

<A NAME="currency_search"></A>
<h3>Currencies</h3>
The currency list page shows a list with configured currencies in the system. A
currency can be bound to an account type. To add a new currency click &quot;New
currency&quot; at the bottom right. <br>
To delete or edit a currency you will have to click the icons in the list.
<ul>
	<li><img border="0" src="${images}/edit.gif" width="16" height="16">
	Click the icon to modify the currency.
	<li><img border="0" src="${images}/delete.gif" width="16" height="16">
	Click the icon if you want to delete the currency.
</ul>
<hr class="help">

<A NAME="currency_details"></A>
<h3>Modify currency / insert new currency</h3>
In the edit currency page you can define the currency. The following fields are
available:
<ul>
	<li><b>Name:</b> Internal name (does not show up anywhere)
	<li><b>Symbol:</b> Will show up in pages. For example after the currency
	in price field of an advertisement.
	<li><b>Pattern:</b> Here you can set the currency name/symbol and and
	where it shows up. You can place it either in front or after the amount. Some
	currencies (like the dollar) will show the symbol before the amount and others
	after. The currency pattern will show up in lists and messages.<br>
	Symbols are not always supported by browsers, a good example is the Euro
	symbol. In these cases you might need to use the unicode symbol (e.g.
	&amp;euro;)
	<li><b>Description:</b> For internal information (does not show up
	anywhere)
</ul>
<hr class="help">

<a name="accounts"></a>
<h2>Accounts</h2>
Accounts in Cyclos can be either of type &quot;System&quot; or
&quot;Member&quot; Both types are related to a currency and can contain units
that can be transferred to and from other accounts (if transaction types between
these accounts exist). <br>
Contrary to a Member account a System account does not have a associated owner.
Administrators that have the permissions can make payments from these system
accounts to other system or member accounts. <br>
A Member can have zero, one or more related member accounts, and make payments
between his own accounts, to other member accounts or to system accounts.

If a new account with the type &quot;Member&quot; is created it does not have
any related Members yet. You will have to take the following steps in order to
let members actually use the account type:
<ol>
	<li><b>Assign the account to a group:</b> Only after assigning the account
	to a Member group the members of this group will have access to this account.
	This can be done via the <a href="${pagePrefix}groups#manage_groups"><u>
	group settings</u></a>.
	<li><b>Transaction types:</b> Before the Members will be able to make
	payments from this account or receive payments in it you will need to create
	and associate &quot;<a href="#transaction_types"><u>transaction types</u></a>&quot;.
	This can be done with the transaction type window which is on the same page as
	the window for setting the account type properties.
	<li><b>Permissions:</b> Of course, you will also need to set the <a
		href="${pagePrefix}groups#manage_groups"><u>permissions</u></a>.
</ol>
Besides transaction types, there are many things that are directly related to
accounts like like loans, transaction fees, account fees and payment filters.
<br><br>Cyclos ships with a database with a set of <a href="#standard_accounts"><u>
standard accounts</u></a> which will be fit for the majority of users/systems.
<br><br>
<i>Where to find it.</i><br>
Accounts can be managed (created, deleted etc) via the menu: &quot;Menu:
Accounts > Account management&quot;.<br>
System accounts can be viewed at &quot;Menu: Accounts > system accounts&quot;.
<hr>

<a name="standard_accounts"></a>
<h3>Standard Account types</h3>
Although it is possible to create a whole new account structure from scratch we
provided a database with defaults that would be standard for the majority of
complementary currency systems. The database defaults can always be extended
with more accounts and transfer types. We created a member account and various
system accounts. The <a href="#account_fees"><u>account fees</u></a> (automatic,
manual and liquidity tax = &quot;demurrage&quot;) and <a
	href="#transaction_fees"><u> transaction fees</u></a> are disabled by default
but it is easy to change the default values and enable them.
<br><br>The database comes with the following default System Accounts:
<ul>
	<li><b>Loan (debit) account:</b> The Debit or Loan Account is only used
	for loans and the initial credit (which can be a loan or a gift). It is the
	main System Account and it is called loan account for clarity reasons
	(sometimes this kind of account is called the &quot;Float Account&quot;, or the
	&quot;Debit Account&quot;). It is common to leave the debit account as the ONLY
	account that has no negative limit. This account is necessary for the creation
	of Units. When Units are created the Loan Account goes negative and the
	receiver (mostly a Member account) will go positive with the same amount of
	Units.<br>
	The administration of the Loan Account is therefore very critical. Maybe not so
	much for a LETS kind of system, but for Barter (business) software or a system
	backed with money this must be very secure and controlled.<br>
	<br>
	<li><b>Community Account:</b> The Community Account is the account that is
	owned by the community and it can receive the taxes (if configured) and Member
	contribution payments. An administrator can perform a payment from the
	Community Account to a Member account (e.g. for community work done by that
	Member). Just like a Member account the Community Account cannot go below the
	lower credit limit.<br>
	<br>
	<li><b>Voucher Account:</b> The Voucher Account is the account that
	contains the (digital) Units that have been brought in circulation as Vouchers
	(physical Units). When a Member wants to buy Vouchers the member will need to
	pay the Voucher Account. The organization can check the payment and hand over
	the Vouchers. When a Member wants to cash the Vouchers back to Units the member
	will need to hand over the Vouchers to the organization and an Administrator
	will have to perform a payment from the Voucher account to the Member account.
	<br>
	In the case of a system where Units are (partially) backed with conventional
	money the Voucher can be sold for conventional money. In this case it is not
	necessary to be a Member of the system. You just buy Vouchers as a kind of
	&quot;bonus coupon&quot;. In this case an administrator will have to make a
	payment from the Loan Account to the Voucher Account.<br>
	<br>
	<li><b>Organization Account:</b> The Organization account serves as an
	extra account for the Organization. If needed, the name can be changed
	according to its function (e.g. social account or investment fund).
</ul>
<hr class="help">

<A NAME="account_search"></A>
<h3>Account list</h3>
The account type list page shows an overview with a list of the accounts and
their types. (therefore we sometimes refer to this as account type).
<br><br>To create a new member or system account you will have to click the
&quot;submit&quot; button labeled &quot;insert new account type&quot; below the
window.
<ul>
	<li><img border="0" src="${images}/edit.gif" width="16" height="16">
	Click the icon to modify the account.
	<li><img border="0" src="${images}/delete.gif" width="16" height="16">
	Click the icon if you want to delete the account. (Deleting an account can only
	be done if no transactions for this account exist in the system).
</ul>
<hr class="help">

<A NAME="account_details"></A>
<h3>Modify Account type / New Account</h3>
In the account details window you can create or modify a new member account. <br>
If you are creating a new account you can specify if it will be a system or
member account. The following options are available:
<UL>
	<li><b>Name:</b> Name of the account. This will show up in the <a
		href="#account_search"><u>manage accounts list</u></a>, and (if it is a system
	account) in the <a href="${pagePrefix}payments#account_overview"><u>system
	account overview list</u></a>.
	<li><b>Description:</b> Explanation of the account (readable only by the
	administration).
	<li><b>Currency:</b> Here you set the <a href="#currencies"><u>currency</u></a>
	for this account.
	<li><b>Limit type (system account only):</b> An account can be unlimited
	which means that it can go indefinitely negative (this is mostly
	&quot;debit&quot;, &quot;float&quot; or &quot;loan&quot; account)<br>
	If the account is limited you can specify the upper and lower credit limit. The
	limit type can only be specified at account creation (and not changed
	afterwards)
</UL>
Note: Many account settings are group specific (for example the credit limits).
These settings can be modified in the <a
	href="${pagePrefix}groups#manage_groups"><u> user group settings</u></a>.
<hr class="help">

<A NAME="transaction_types"></A>
<h2>Transaction types</h2>
Each payment (also called transaction) has a &quot;transaction type&quot;. The
transaction type defines the origin and the destination account type of the
payment. If an account type has not associated transaction types, no payment can
be done. A transaction type must be associated to the origin account (= the
account of the payer).


<i>Where to find it.</i><br> 
Transaction types can be defined and modified in the account management windows;
to get there, you should follow the &quot;Menu: Accounts > Manage
Accounts&quot;, and click the edit icon to modify an account type. In the next
screen there will be a special window with an overview of the associated
transaction types.
<hr class="help">

<A NAME="transaction_type_search"></A>
<h3>Transaction types list</h3>
The transaction type window displays a list of transaction types related to the
selected account.
<ul>
	<li><img border="0" src="${images}/edit.gif" width="16" height="16">
	Click the icon to modify the transaction type.
	<li><img border="0" src="${images}/delete.gif" width="16" height="16">
	Click the icon if you want to delete the transaction type.
</ul>
Use the &quot;insert new transaction type&quot; button below to insert a new
transaction type.
<br><br>Note1: The permissions of a transaction type are defined per group and
can be set in &quot;Menu: Users & Groups > Permission groups > edit group
permissions.&quot;
<br><br>Note2: If the selected account is a member account, the specific account
settings (credit limit etc) are group defined. These can also be modified in the
section &quot;Users & Groups - permission groups&quot; (edit icon - select
account).
<hr class='help'>

<A NAME="transaction_type_details"></A>
<h3>Modify/Insert Transaction type</h3>
With this window, you can set the properties of a specific transaction type. The
transaction types have the following fields (not all may be visible, depending
on the type and configuration):
<ul>
	<li><b>Name:</b> Name of the transaction type.<br>
	<br>
	<li><b>Description:</b> Internal description of the transaction type. This
	description may appear in the transaction details.<br>
	Note: In case of loan repayments and periodic fees, you may use certain codes
	to included specific data in the description. You can use
	&quot;placeholders&quot; - they will be replaced by their corresponding values
	in the eventual transaction description. <a href="#placeholders"><u>
	Click here</u></a> for an overview.<br>
	<br>
	<li><b>Confirmation message:</b> This message will show up below the
	transaction information in the confirmation pop-up window. This way it is
	possible to set messages for specific payment types.<br>
	<br>
	<li><b>From:</b> the account type of the payer.<br>
	<br>
	<li><b>To:</b> the account type of the receiver.<br>
	<br>
	<li><b>Fixed memberTo:</b> If you want that the destination of this transaction type is 
	always the same member you select a member here. Note that this are rare cases. This 
	is mostly used if you want rules and/or custom payment fields bound to a specific 
	member and not a member group. <br>
	If you have more than one (member to member) payment type you can hide payments using 
	the priority option (explained below). In the cased of a fixed destination member you 
	might want to hide the normal member-to-member payment by setting leaving the priority 
	setting blank.
	<br>
	<br>
	<li><b>Availability:</b> (Only for payments between member accounts - this
	option will not be visible if your members only have one account for this
	currency. In such a case, the &quot;enabled&quot; checkbox (next item) will be
	shown,) The availability defines where the payment can be made from.
	<ul>
		<li><b>Disabled:</b> The payment is inactive and won't show up anywhere.
		
		<li><b>Payment to others:</b> This is the most common payment type. If
		this option is selected, a member can use this transaction type to perform
		payments to another member or to a system account.
		<li><b>Self payment:</b> If more than one member account type exists (for
		example checking account and savings account) you can create a transaction
		type to allow a member to make payments between these accounts. In case of a
		self payment you probably do not want to allow payments from one member's
		checking account to another member's savings account. This can be achieved by
		un-selecting the direct payment checkbox.
	</ul>
	Note!: Transaction types that can be automatically generated like account fees
	and transaction fees will always be charged, even if no option is checked in
	this overview and no permissions have been set for the member group. Same
	counts for the next item, the &quot;enabled&quot; checkbox. <br>
	<br>
	<li><b>Enabled:</b> Payments can be made with this transaction type and
	the payment type is visible in payment windows and transaction summary. Note
	that this item is mutually exclusive with the previous: if
	&quot;availability&quot; is shown, &quot;enabled&quot; will not be shown and
	vice versa.<br>
	<br>
	<li><b>channels:</b> This item allows you to define the <a
		href="${pagePrefix}settings#channels"><u>channels</u></a> over which this type
	can be used. The default channels is &quot;main web access&quot;, but others
	(like mobile phone) are available to.<br>
	<br>
	<li><b>Priority:</b> If the priority option is selected the transaction
	type will have priority over other transactions. This means that when a payment
	could show more than one possible transaction type only the transaction types
	with the priority settings will be shown. But if none of the payments is set as
	priority they will all show up.<br>
	The priority setting will only be used with relatively complex systems where
	various groups are allowed to trade only under some circumstances with other
	groups.<br>
	With the group and transaction types it is possible to set up a system where
	various communities can trade independently but also inter-trade, all within
	the same system.<br>
	The setting is also used in combination with the &quot;fixed member&quot; option
	explained above. 
	<br>
	<li><b>Max amount per day:</b> This is the maximum which can be paid per
	member per day with this transaction type. For example this can be used to
	limit the payments done with mobile phones.<br>
	<br>
	<li><b>Is loan:</b> (only available for system to member payments) Check
	this checkbox to indicate if the transaction type is a loan. The loan settings
	will appear below as soon as you check this box; for more information <a
		href="${pagePrefix}loans#make_loan_type"><u>click here</u></a>. <br>
	<br>
	<li><b>Requires authorization:</b> If selected an extra authorization
	window will appear below this window as soon as you save it for the first time.
	More information about <a href="${pagePrefix}payments#authorized"><u>authorized
	payments</u></a> can be found in the help of <a href="#authorized_payment_levels"><u>that
	window</u></a> .<br>
	If you un-select the authorizations option all authorizations levels will be
	canceled for new payments. <br>
	<br>
	<li><b>Allows scheduled payments (installments):</b> If this option is
	selected it means that this type of transaction can be <a
		href="${pagePrefix}payments#scheduled"><u>scheduled</u></a>. This does not
	mean that any member or admin with this transaction type will be automatically
	able to use the schedule option. You still need to set the permissions per
	group (view and allow scheduled payments). For more information please refer to
	the manual - Payments - Scheduled payments. <br>
	<br>
	<li><b>Is conciliable:</b> If this option is checked this transaction type
	will be part of the conciliation funcion. For more information please refere to
	the <a href="${pagePrefix}bookkeeping"><u>Help file</u></a> of the bookkeeping
	/ conciliation function. . <br>
	<br>
	<li><b>Require feedback on transactions:</b> This option is only available
	for member to member payments. If this option is checked members can set
	feedback on performed transactions of this type. For more information on feed
	back please refer to the feed back <a href="${pagePrefix}transaction_feedback">help
	page </a><br>
	The following elements are only visible if the &quot;require feedback&quot;
	checkbox is checked:
	<ul>
		<li><b>Max feedback period:</b> When a payment is done the payer (=buyer)
		has a maximum time in which the feedback can be set. This period can be
		specified with this setting.
		<li><b>Max feedback reply comments period:</b> The receiver of a feedback
		(=seller) can reply to a feedback. This maximum period can be specified with
		this setting.
		<li><b>Feedback level on expiration:</b> Once a feedback has been expired
		a default feedback will be created. You can specify the default level in this
		setting (this would be typically something like &quot;Neutral&quot;).
		<li><b>Feedback comments on expiration:</b> Once a feedback has been
		expired a default feedback will be created. You can specify the default
		comment in this setting (this would be typically something like &quot;No
		feedback added&quot;).
	</ul>
</ul>
<hr class="help">

<a name="placeholders"></a>
<h3>Placeholders for description fields</h3>
In case of loan repayments and periodic fees, you may use certain codes to
included specific data in the description of a <a href="#transaction_types"><u>
transaction type</u></a> . You can use &quot;placeholders&quot; - they will be replaced
by their corresponding values in the eventual transaction description. <b>Loan
repayment:</b>
<ul>
	<li><b>#loanAmount#:</b> The original loan amount
	<li><b>#loanTotalAmount#:</b> Loan amount plus costs (interest, loan grant
	fee)
	<li><b>#parcelAmount#:</b> Loan component amount, the amount for a
	specific loan period. A loan is divided into a fixed number of repayment
	components.
	<li><b>#parcelNumber#:</b> Loan component number. A loan is divided into a
	fixed number of repayments, the &quot;components&quot;. Each of these
	components can have its own number.
</ul>
For more information on loans, see the <a href="${pagePrefix}loans"><u>loans
help</u></a>.
<br><br><b><a href="#account_fees"><u>Periodic Fees</u></a> (= Contribution
and Liquidity tax):</b>
<ul>
	<li><b>#begin_date#:</b> Start date (only in case of <a
		href="#account_fees"><u> liquidity tax</u></a>)
	<li><b>#end_date#:</b> End date (only in case of liquidity tax)
	<li><b>#tax#:</b> = Amount
	<li><b>#freebase#:</b> Freebase, the basic amount over which no tax is due
	(only in case of liquidity tax)
	<li><b>#volume#:</b> = Total trade volume (only in case of liquidity tax)
	
	<li><b>#result#:</b> = Result
</ul>
<hr class="help">


<br><br><A NAME="payment_fields_list"></A>
<h3>Custom payment fields</h3>
It is possible to add custom fields to a transaction (payment) type just as you can 
set a custom field to member profiles or advertisements. The payment field will only
be visible for the current transaction type (the one you are editing at the moment).<br>
You can create to type of custom payment fields:
<ul>
	<li><b>Insert new custom field:</b> With this option you can set a custom field
	to a payment type in the same way you can do this to a member profile (for
	example)
	<li><b>Link existing custom field:</b> With this option you can link to an existing
	payment field. More information on this can be found at the link custom payment field 
	details. 
	</ul>
<hr class="help">


<br><br><A NAME="payment_fields_link"></A>

<h3>Link custom payment field</h3>
It is common in more complex system that transactions are &quot;forwarded&quot;.
This is normally done by using a transaction fee that charges 100% and has a
third party destination account. In this case you possibly would want to use the
same custom field for both payments. You can do this to create the custom
payment fields in the origin transaction type and in the second (generated) transaction type create links
to the custom payment field to them (by using the option &quot;Link existing
custom field&quot;)
</ul>
<hr class="help">


<br><br><A NAME="authorized_payment_levels"></A>
<h3>Authorization levels</h3>
This function will allow to define levels of authorization for a payment type
which needs <a href="${pagePrefix}payments#authorized"><u> authorization</u></a>.
<br><br>There are various possible levels of authorization, and there can be more
than one authorization level per payment type, meaning that various persons will
need to authorize one payment with (possibly) different criteria. Both member
and authorizer (broker or admin) will have access to a list with pending
payments that need authorization.
<br><br>This window will show all the authorization levels for the transaction
type. If none is available you will have to click the &quot;New authorized
payment level&quot;, because you will have to define at least one level of
authorization for each authorized payment type. When you add a new level it will
show up in the list.<br>
If levels already have been defined, you have the following options:
<ul>
	<li><img border="0" src="${images}/edit.gif" width="16" height="16">
	Click the icon to modify the authorized payment level.
	<li><img border="0" src="${images}/delete.gif" width="16" height="16">
	Click the icon if you want to delete the authorized payment level.
</ul>
<br><br>If you un select the authorization option in the transaction type it
means that all authorizations for the transaction type are disabled. The
authorization levels will disappear but remain in the history. If the
authorization checkbox is checked again in the transaction type configuration
the authorization levels will appear again.
<br><br>If an authorization payment generates other payments (e.g. a loan with
loan repayments, taxes and fees etc) the whole group of payments can be
authorized as a whole (and the total amount blocked/reserved until
authorization).
<hr class="help">

<A NAME="edit_authorization_level"></A>
<h3>Edit Authorization level</h3>
In this window you can define the <a href="#authorized_payment_levels"><u>
level of authorization</u></a>. You can define a maximum of five authorization levels.
Every level can have a authorization &quot;type&quot;. It depends on the
authorization level what kind of authorizations are available.
<ul>
	<li><b>Receiver:</b> When the authorization type is &quot;Receiver&quot;
	it means the (destination) member receiving the payment will have to authorize
	the payment first. When the receiver authorizes the payment (by clicking
	&quot;accept&quot; from the pending authorized payments list) the amount will
	be transferred and the status will go from &quot;pending &quot; to
	&quot;authorized&quot;.<br>
	This authorization type is fairly rare. We suggest not to use this option if <a
		href="${pagePrefix}invoices"><b>invoices</b></a> are used in the system.
	Invoices offer a similar function.
	<li><b>Payer:</b>This option is only available if there is a first level
	option with the &quot;Receiver&quot; authorization type.<br>
	It offers an extra (optional) level of authorization after the
	&quot;Receiver&quot; authorization type. If this type is configured a payment
	will remain in the pending status after a Receiver authorized. Now the the
	payer (originator) will have to accept the payment. When done the amount will
	be transferred.
	<li><b>Broker/Admin:</b> This authorization level is set as either a first
	level or a second level after a level with the Receiver type. It means the <a
		href="${pagePrefix}brokering"><u>broker</u></a> of the member that made the
	payment will need to authorize the payment and optionally also an
	administrator.<br>
	You can select the administration groups that can authorize the payments
	besides the broker. There is no priority of who can authorize. Either broker or
	admin can authorize.
	<li><b>Admin:</b> This authorization type is available as first level,
	second level after Broker/Administrator, third level after Payer and any other
	Administrator only type.<br>
	This means you can chain various levels with the administrator authorization
	level type and different amounts and groups. You will need to select the
	administration group that can authorize the payment.
</ul>
You will need to specify an amount for each authorization type and level. If you
want the payment always to be authorized you can put a zero in the amount field.
If you put an amount, say 1000, it means that if a member pays more than 1000
during 24 hours, the exceeding payment will need to be authorized. <br>
The amount of an authorization level can either have the same amount as the its
parent level or higher.
<hr class="help">


<A NAME="payment_filters"></A>
<h2>Payment filters</h2>
It is possible to group transfer types into &quot;Payment filters&quot;. These
filters allow handy grouping together of certain related transaction types, for
example for the transaction summary window or for statistics. For example:
different kinds of contributions and other specific community payments can be
grouped into one filter with the name &quot;community payments&quot;. The
payment filters can also be used to create customized reports. For the
administrator, the payment filters offer a good tool to keep track of payments
and retrieve specific reports.

<i>Where to find it.</i><br>
Payment filters are always related to an account type, so they must be accessed
via &quot;Menu: Accounts > Account Management&quot; > select an account type and
click the edit <img border="0" src="${images}/edit.gif" width="16" height="16">&nbsp;
icon. Besides other windows, there will be a special window with associated
payment filters.
<hr>

<A NAME="payment_filter_search"></A>
<h3>Payment filter list</h3>
This window lists the <a href="#payment_filters"><u>payment filters</u></a>
associated with the account.
<ul>
	<li><img border="0" src="${images}/edit.gif" width="16" height="16">
	Click the icon to modify the payment filter.
	<li><img border="0" src="${images}/delete.gif" width="16" height="16">
	Click the icon if you want to delete the payment filter.
</ul>
If you want to create a new filter you have to click the &quot;submit&quot;
button below the window labeled &quot;Insert new payment filter&quot;.
<hr class="help">

<A NAME="payment_filter_details"></A>
<h3>Modify / insert payment filter</h3>
Here you can modify an existing, or insert a new <a href="#payment_filters"><u>payment
filter</u></a>. The following fields can be set:
<ul>
	<li><b>Filter name</b>: The name of the filter.
	<li><b>Description</b>: The description of the filter.
	<li><b>Show in account history</b>: If this box is checked the filter will
	be shown in the account history (of the selected account).
	<li><b>Show in reports</b>: If you check the &quot;Show in reports&quot;
	checkbox the <a href="${pagePrefix}reports"><u> report function</u></a> will
	include reports about this filter. It will show the sum of the amounts of all
	the transactions that are related to the payment filter. It will also be
	available for the <a href="${pagePrefix}statistics"><u>statistics
	module</u></a>.<br>
	You can create payment filters just for the report function. To do this you
	will have to unselect the &quot;Show in account history&quot; checkbox.
	<li><b>Transaction types</b>: Here you must choose the <a
		href="#transaction_types"><u>transaction types</u></a> that will be included
	in the filter.
	<li><b>Group visibility</b>: Here you can select the groups that will be
	able to view the payment filters. This way it is possible to create different
	filters for different groups. For example the broker group could have a
	&quot;Commission payments&quot; filter. Administrators will usually need more
	specific payments filters and member groups will have basic payment filters
	like for example: trade transactions - taxes & fees.
</ul>
<hr class="help">

<A NAME="transaction_fees"></A>
<h2>Transaction fees</h2>
A transaction type can contain one or more &quot;Transaction fees&quot;. Every
time a user makes a payment with the transacation type and a fee is configured
the fee can be charged (depending on the criteria in the fee configuration) A
common transaction fee would be a transaction tax that is charged every time a
member makes a payment with the transfer type &quot;trade&quot;. There are
various ways to calculate the fee (fixed, percentage etc) and define who is
charged.<br>
<br>
A typical example of a fee is the &quot;ordinary&quot; transaction fee on trade.
Every time a &quot;trade&quot; transaction takes place the fee will be applied
(if configured). As this fee is a payment in itself, it has its own transaction
type, and thus it will be charged separately and it will show up as a different
payment type in the transaction history. The transaction detail of a fee does
will show the original (invoking) payment of the fee.

<i>Where to find it.</i><br>
A transaction fee will always &quot;belong&quot; to a <a
	href="#transaction_types"><u> transaction type</u></a>; therefore the
configuration of a transaction fee is located within the transacation type
configuration: go to &quot;Menu: Accounts > account management&quot;, select an
account (via the edit icon), go to the transaction type window, select a
transaction type (via the edit icon). Here you will find a special window with
associated transaction fees.
<hr>

<A NAME="transaction_fee_search"></A>
<h3>Transaction fees</h3>
This window shows a list of associated <a href="#transaction_fees"><u>
transaction fees</u></a> for the <a href="#transaction_types"><u>transaction
type</u></a>.
<ul>
	<li><img border="0" src="${images}/edit.gif" width="16" height="16">
	Click the icon to modify the transaction fee.
	<li><img border="0" src="${images}/delete.gif" width="16" height="16">
	Click the icon if you want to delete the transcation fee.
</ul>
If you want to create a new transaction fees you have to click the
&quot;submit&quot; button labeled &quot;Insert new transaction fees&quot;
located below the window.
<hr class="help">


<A NAME="transaction_fee_details"></A>
<h3>Modify / insert Transaction fee</h3>
This window is used for modifying existing <a href="#transaction_fees"><u>
transaction fees</u></a>, or to define a new one.<br>
A transaction fee will be generated a when a specific transaction occurs.
However, fees are in themselves also transactions and so, they will also need
transaction type of their own. Before configuring a new fee you will have to
create first a new <a href="#transaction_types"><u>transaction type</u></a> for
the fee.
<br><br><b>Fields:</b>
<ul>
	<li><b>Transaction type</b>: This is the transaction type on which the
	transaction fee is invoked. For a fee on trade transfers you'd typically choose
	something like &quot;trade&quot;.<br>
	<br>
	<li><b>Name</b>: Name of the Fee.<br>
	<br>
	<li><b>Description</b>: Description of the Fee.<br>
	<br>
	<li><b>Enabled</b>: The fee is will active when checked. If not, the fee
	is not charged, and the system will act as if it doesn't exist.<br>
	<br>
	<li><b>Will be charged</b>: Here you can define who will be charged. This
	can be a system account the source (payer), destination (receiver) or a fixed
	member.<br>
	<br>
	<li><b>Will receive</b>: Here you can define who will receive the fee.
	This can be a system account the source (payer), destination (receiver) or a
	fixed member.<br>
	<br>
	<li><b>Allow any account</b>: Normally, in the next edit (&quot;generated
	transaction type&quot;) only relevant transaction types in the same currency
	are shown. If you select this checkbox, then there is no limitation of the
	currency in which the transaction fee can be applied: all transaction types are
	shown, even in another currency. <br>
	<br>
	<li><b>Generated transaction type</b>: Here you define what transaction
	type will be generated - so, the transaction type of the fee itself. It is
	common to create a specific transaction type for this so that you can filter on
	it (e.g. in account history: fees & taxes). The default database comes with a
	transaction fee and transaction type for the transaction fee. A fee on trade
	transfers would typically be called something like &quot;transaction tax&quot;.<br>
	NOTE that it is absolutely necessary that you created this transaction type
	first, before being able to create the fee (see note above this list).<br>
	<br>
	<li><b>Amount</b>: Here you can fill in the amount of the fee. If you want
	the amount fee to be a percentage of the original (invoking) amount you will
	have to chose the percentage sign % in the select box. Selecting
	&quot;fixed&quot; will charge a fixed amount indifferently of amount of the
	original transaction.<br>
	<br>
	<li><b>Deduction</b>: With this setting you can define if the transaction
	fee will be calculated as an &quot;extra&quot; payment upon the orginal payment
	or if it will be &quot;deducted&quot; from the total amount of organinal
	payment.<br>
	This can be explained with an example. If a payment of 100 is done and there is
	a fee of 3 that is set to be decucted from the total amount the following
	payments will be generated.:
	<ul>
		<li>Main (invoking) transaction: 97
		<li>Fee: 3
	</ul>
	The above case is quite rare. Usually fees are not deducted from the total
	amount. Example with the same amounts with a non deducted fee:
	<ul>
		<li>Main (invoking) transaction 100
		<li>Fee: 3
	</ul>
	<br>
	<br>
	<li><b>Conditions of applicability</b>: Here you can define under what
	conditions the fee will be applied. The fee will only be applied if the
	conditions match. The conditions can be combined.
	<ul>
		<li><b>Amount is greater or equals</b>: The fee will only be charged if
		the transaction amount of the invoking transaction is greater than or equal to
		the specified amount.
		<li><b>Amount less or equals</b>: The fee will only be charged if the
		transaction amount of the invoking transaction is lesser than or equal to the
		specified amount.
		<li><b>From groups</b>: If all groups are selected the fee will apply for
		members of any group who are considered as payers of the invoking transaction
		type. If you want to apply the fee only for specifics groups you will need to
		uncheck the &quot;From all groups&quot; checkbox, and select the groups in the
		multi select combo which will then appear.
		<li><b>To groups</b>: Equal to the previous item, but for members who are
		the receivers of the invoking transaction type.
	</ul>
</ul>
<hr class="help">

<A NAME="account_fees"></A>
<h2>Account fees</h2>
&quot;Account fees&quot;, often referred to as &quot;contributions&quot; are
periodically scheduled or manually run by an administrator. Account fees are
related to an account and can be activated for one or more Member groups. When
an account fee is run, all members groups that have been selected in the account
fee configuration will be charged - however, though the word "fee" suggests that
members are paying, an account fee can also be configured that a system account
is the paying party, and that members receive the fee. A typical account fee is
a monthly contribution payment from members to a system account (but it can be
the other way around as well). Another example is &quot;demurrage&quot; or
&quot;liquidity tax&quot;, where users pay over their positive balance through
time, as a sort of &quot;negative interest&quot;. The idea behind this is that
people are then stimulated to use their balance, in stead of keeping it on their
account.

<i>Where to find it.</i><br>
As account fees are associated to the account type, there configuration is done
via account management: &quot;Menu: accounts > account management&quot;, choose
an account type and click the edit <img border="0" src="${images}/edit.gif"
	width="16" height="16">&nbsp;icon.
<br><br>The administration and overview of the account fees is done from the
account overview page (&quot;Menu: accounts > account fees&quot;). In that page
you get an overview of all account fees and their status; you can also manually
charge an account fee.
<hr>

<A NAME="account_fee_overview"></A>
<h3>Account fees</h3>
The account fees overview page show all <a href="#account_fees"><u>
account fees</u></a> that are enabled for any account type.
<br><br>The window will show a list with the active (enabled) account fees , the
last and next execution date and their status (&quot;failed&quot;,
&quot;succesful&quot;).
<br><br>Account fees can be either automatic (scheduled) or manual. Manual
account fees run only when the link &quot;Run now&quot; is clicked. While the
account fee run is in progress it is possible to cancel the account fee by
clicking the &quot;cancel&quot; link. <br>
A canceled fee or a fee that failed because of technical problems will always
perform a &quot;roll back&quot;. This means that you will never have half
finished account fees. After the account fee has run successfully the last
execution date will be set and an entry will show up in the <a
	href="#account_fee_history"><u>account fee history list</u></a> (window below).

<br><br>If an account fee has failed an alert will show up in the alert log and
the status will show that the fee was failed. A failed fee can be resumed by
clicking the &quot;recharge&quot; link in the account fee history list.
<hr class="help">

<a name="account_fee_history"></a>
<h3>Account fee history</h3>
This page show all the charges of <a href="#account_fees"><u>account
fees</u></a> as done in history. If an account fee charging has failed the status will
show that the fee was failed. A failed fee can be resumed by clicking the
&quot;recharge&quot; link.
<hr class="help">

<A NAME="account_fee_list"> </A>
<h3>Account fees</h3>
This windows shows a list with all configured <a href="#account_fees"><u>
account fees</u></a> (either enabled or disabled) for this account type.
<ul>
	<li><img border="0" src="${images}/edit.gif" width="16" height="16">
	Click the icon to modify the account fee.
	<li><img border="0" src="${images}/delete.gif" width="16" height="16">
	Click the icon if you want to delete the account fee.
</ul>
To add a new account fee click &quot;Insert new account fee&quot; below the
list.
<hr class="help">

<A NAME="account_fee_details"></A>
<h3>Modify / Insert Account fee</h3>
In this window you can configure an <a href="#account_fees"><u>account
fee</u></a> (new or existing). Note that a fee in itself is a transaction; so it will
need a <a href="#transaction_types"><u> transaction type</u></a>. You will need
to have that transaction type created before it is possible to create and
configure the account fee.
<br><br>The following configuration options are available</b>
<ul>
	<li><b>Account fee name</b>: The name of the account fee.<br>
	<br>
	<li><b>Description</b>: The description of the account fee.<br>
	<br>
	<li><b>Enabled</b>: The account fee will only be charged if this box is
	selected. If the fee is scheduled or manual doesn't matter in this case:
	charging will not be possible if this box is not checked.<br>
	<br>
	<li><b>First charged period starts at:</b> choose here when the first
	charge period needs to start. You may want to choose the date picker by
	clicking the calendar <img border="0" src="${images}/calendar.gif" width="16"
		height="16">&nbsp; icon. <br>
	When done, you may want to check the next execution at &quot;Menu: Accounts >
	account fees&quot;. <br>
	<br>
	<li><b>Charge mode</b>: There are five possible charge modes:
	<ul>
		<li><b>Fixed amount</b>: The fixed amount can be a contribution (from
		member to system account) or a one time or scheduled payment from a system
		account to a group of members.
		<li><b>Postive volume percentage</b>: A postive volume percentage is
		calculated in the same way as interest is calculated. Actually when defining
		the payment direction (see below) as &quot;system to member account&quot; it
		will be interest. If the direction is &quot;member to system account&quot; the
		fee will be converted interest (demurrage).<br>
		The amount due is based on a period and amount. An interest/demurrage of 1%
		charged over a member balance that has 100 Units during the entire month will
		be 1 unit.
		<li><b>Negative volume percentage</b>: The negative volume percentage
		works in the same way but only on negative balances.
		<li><b>Postive balance percentage</b>: The balance percentage fee does
		not calculate the volume (time + balance) but the balance only. When the fee
		is run (either manually or scheduled) the fee will consider only the balance
		at the moment of charging. This would mean that if in the above example the
		member lowered his balance to zero just before the fee run he would not be
		charged anything. The balance percentage could cause unnatural behavior in a
		system but that is a methodological issue.
		<li><b>Negative balance percentage</b>: The negative balance percentage
		works in the same way but only on negative balances
	</ul>
	Note that the charge mode cannot be changed on existing fees; it can only be
	set for new account fees.<br>
	<br>
	<li><b>Tolerance</b>: This item is only visible when at &quot;charge
	mode&quot; the value &quot;positive volume &quot; was selected. The tolerance
	is a setting that can be used to avoid that members that received a system
	payment (e.g. loan) and spend it within a short period (either converting or
	buying something) will be charged demurrage over this amount and period. For
	example if a member receives 100 units and spend them within 24 hours the
	member will not be charged for this time/amount if the tolerance period would
	be set to 24 hours. If, in the same case, the member would spend the 100 units
	in two days he would be charged for the whole period of two days (and not one).<br>
	<br>
	<li><b>Payment direction</b>: This defines the direction of the account
	fee, either from member to system or the other way around.<br>
	<br>
	<li><b>Generated transaction type</b>: The account fee will need a
	transaction type. The transaction will be of the choosen transaction type.<br>
	<br>
	<li><b>Amount</b>: In case you choose &quot;fixed amount&quot; at the
	&quot;charge mode&quot;, this will be the amount which will be charged.
	Otherwise, it will be the percentage charged of the volume or balance.<br>
	<br>
	<li><b>Free base</b>: With a freebase it is possible to apply a fee only 
	above a certain amount.<br>
	In the case of a volume percentage account fee mode it would work as the follows
	(using the before mentioned example)
	<ul>
		<li><b>charge mode:</b> &quot;positive volume percentage&quot;
		<li><b>amount:</b> 1%
		<li><b>free base:</b> 40
		<li><b>volume on member's account:</b> 100 Units during the entire month
	</ul>
	In this case without a free base (i.e. a free base of 0) the fee charged would
	be 1 unit; because of the free base, the first 40 will not be charged, so the
	fee charged will be 0.60 units.<br>
	In the case of a fixed account fee the account fee will simply not be charged
	for members that have balances equal or below the free base amount. 
	<br>
	<br>
	<li><b>Send invoice</b>: This item is only visible in case of &quot;member
	to system&quot; payment direction.<br>
	This determines what will happen if a member that has been charged does not
	have enough credits to pay the account fee. The following options are
	available:
	<ul>
		<li><b>Only when member does not have enough credits:</b> in this case
		only members who don't have enough will receive an invoice; the rest is
		charged.
		<li><b>Never (may member accounts go negative):</b> in this case all
		members are charged and all do pay, even if this makes their account go below
		their credit limit.
		<li><b>Always (do not automatically charge member):</b> in this case the
		member the account fee will not charge anybody but only send invoices, no
		matter if the members are reaching their credit limit or not.
	</ul>
	<br>
	<br>
	<li><b>Run mode</b>: Here you can define if the account fee will be
	scheduled or charged manually. Note that this cannot be changed once the fee
	has been created; it can only be set for new fees.<br>
	If charged manually, an administrator needs to initiate the charging of the
	fees from the <a href="#account_fee_overview"><u>account fees overview
	window</u></a>. If the run mode is &quot;Scheduled&quot; it will be charged
	automatically at the configured time. In that case you will have the following
	extra options:
	<ul>
		<li><b>Periodic</b>: This the periodic interval the account fee will run.
		For example every month or every year.<br>
		For example: if this is set to &quot;3&quot; &quot;months&quot; the account
		fee will run every three months. If it is set to &quot;0&quot; the account fee
		will only run once.
		<li><b>Day</b>: The day of the month or week when the account fee will be
		run. This is of course not visible if you chose a period on a daily base.
		<li><b>Hour</b>: The hour (1-24) that the account fee will be run.
	</ul>
	<br>
	<li><b>Enabled since</b>: This field will only show up for fees that have
	the option &quot;Enabled&quot; selected. If you want to configure a scheduled
	tax but do not want it to be charged at the first coming scheduled date you
	can define here a date in the future. As of that date the fee will be charged
	according to the scheduling.<br>
	For example. You added a scheduled fee running at day 1 every month but you only
	want to start this fee at the first of January 2011 (let say we are living now
	in June 2010). You will have to set the &quot;Enabled since&quot; date somewhere
	between the first of December 2010 and the first of January 2011.<br>
	Note: The &quot;Enabled since&quot; date can only be set once. The option won't
	show up once it has been used. Of course you can always disable/enable a fee
	manually with the &quot;Enabled&quot; option above.<br>
	<br>
	<br>
	<li><b>Groups</b>: Here you can select the group(s) that will be charged
	or receive the account fee.
</ul>
<hr class="help">

<A NAME="credit_limit"></A>
<h3>Personal credit limit</h3>
<br><br>
<i>Where to find it.</i><br>
The personal credit limit can be access from the <a
	href="${pagePrefix}profiles#accounts_actions"><u> account actions
for member</u></a> (window below the profile of the member).
<br><br>
With this function, individual credit limits can be set for the Member. At the time a new member
account is created, the member will get the default credit limits that are
configured in the <a href="${pagePrefix}groups#manage_groups"><u> group
settings</u></a>. <br>
With this window you can overrule these group credit limits. You can set here
the upper and lower credit limit for this individual member, for all of the
accounts. When the member reaches the lower credit limit he/she will not be able
to make payments.
<br><br>The upper credit is a rarely used function. When the member reaches the
upper credit limit he will not be able to receive payments. The payer will
receive a message that the receiver is not allowed to receive payments. An
exception is a scheduled contribution where the member receives units. These
payments will always occur.
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