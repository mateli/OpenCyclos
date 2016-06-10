<div style="page-break-after: always;">
<h2>Betalingen</h2>
Een deelnemer kan betalen aan een andere deelnemer, of aan een systeemrekening 
(vereniging, organisatie etc). Als een deelnemer meer dan een rekeningsoort heeft
met dezelfde valuta kan er ook een betaling naar eigen rekening plaatsvinden.
Een betaling kan worden ingepland om op een later tijdstip te worden uitgevoerd. 
Elke betaling kan worden afgedrukt als bewijsstuk. 

<span class="admin">Onder bepaalde omstandigheden kunnen betalingen worden 
<a href="#charge_back"><u>teruggedraaid</u></a>.</span>

<br><br><i>Waar te vinden?</i><br>
<br><br><span class="member">In de webtoegang kan een betaling vanaf drie plaatsen
worden gestart. 
<ul>
	<li><b>Betaling aan deelnemer:</b> &quot;Menu: Rekening > Deelnemerbetaling&quot; 
	(indien door de administratie aangezet)
	<li><b>Betaling aan het systeem:</b> &quot;Menu: Rekening > Systeembetaling&quot;
	<li><b>Vanuit het profiel van de deelnemer:</b> betalingen aan deelnemers 
	kunnen worden gestart vanaf de <a href="${pagePrefix}profiles"><u>profiel</u></a>
	pagina van de deelnemer.
</ul>
</span> 
<span class="broker"> A broker can start payments for his members via
their <a href="${pagePrefix}profiles"><u> profile</u></a>. This includes
payments to other members, and payments to system accounts.
<br><br>A broker can also <a href="#geautoriseerd"><u>authorize</u></a> payments of
members; this can be reached via the main &quot;Menu: Brokering > Awaiting
authorization&quot; and &quot;Menu: Brokering > Authorizations history&quot;.<br>
</span> <span class="admin"> Payments can be started from various locations. The
availability of the mentioned options depends of course on your organization's
settings and the autorisaties of various groups:
<ul>
	<li><b>Profile:</b> From a member's <a href="${pagePrefix}profiles"><u>profile</u></a>
	you can perform payments to other members, as well as payments to the system.
	<li><b>System to System payments:</b> can be started from the &quot;Menu:
	Accounts > System Payment&quot;. These are transfers from one system account to
	another system account.
	<li><b>System to member Payments:</b> can be started from the &quot;Menu:
	Accounts > Member Payment&quot;. These are payments from a system account to a
	member.
</ul>
Also, various types of special payments exists, and these can mostly be reached
via the main menu:
<ul>
	<li><b><a href="#geautoriseerd"><u>Authorizations</u></a>:</b> can be reached
	via &quot;Menu: Accounts > to Authorize&quot; and &quot;Menu: Accounts >
	Authorizations history&quot;.
	<li><b><a href="#scheduled"><u>Scheduled payments</u></a>:</b> can be
	reached via &quot;Menu: Accounts > Scheduled payments&quot;.
	<li><b>Loan repayments:</b> can be reached via &quot;Menu: Accounts > loan
	payments&quot;; this subject is covered in the <a href="${pagePrefix}loans"><u>loans
	help file</u></a>.
</ul>
<br>
</span> Note that, besides doing payments directly, you can also pay by responding to
an <a href="${pagePrefix}invoices"><u>invoice</u></a>.

<span class="admin">
<br><br><i>How to get it working?</i><br>
The most important issue is that for each payment there must exist a payment type. If you 
did not define a payment type for a certain payment, then the payment cannot take place.
You can manage transaction types by managing the account from which they are paid. To do so, 
you should go to the &quot;menu: Accounts > Manage accounts&quot;, and choose the account type
of the payer. There you will have an <a href="${pagePrefix}account_management#transaction_type_search"><u>
overview</u></a> of all available transaction types for this account, allowing you also to add a
new type (if you have the autorisaties).<br>
As soon as there is a payment type, you still need to set the autorisaties for using it for
various groups. 
<ul>
	<li>Admins can have <a href="${pagePrefix}groups#manage_group_autorisaties_admin_system"><u>
	autorisaties</u></a> to perform system payments: block &quot;system payments&quot; contains
	various settings.
	<li>Admins can also have <a href="${pagePrefix}groups#manage_group_autorisaties_admin_member"><u>
	autorisaties</u></a> to perform payments for members: block &quot;member payments&quot; contains
	various settings.
	<li>For members, you will also need to set the <a href="${pagePrefix}groups#manage_group_autorisaties_member"><u>
	autorisaties</u></a> for performing payments; this is done with various settings in the block
	&quot;payments&quot;. Such a block is also available for brokers.
	<li>Brokers can have <a href="${pagePrefix}groups#manage_group_autorisaties_broker"><u>
	autorisaties</u></a> to perform payments for members, under the block &quot;member payments&quot;.
	<li>For geautoriseerd and scheduled payments, there exists for each main group (members, brokers,
	admins) a setting under the block &quot;Account&quot; which allows this group to view
	geautoriseerd and/or scheduled payments.
</ul>

</span>

<hr>

<a name="payments"></a>
<br><br>
<h3>Making payments</h3>
The forms for making payments in cyclos often have some common elements. In this
short introduction, we cover the common elements which may appear in your
payment window. In most cases, you can just fill in the amount and the
description, and then click &quot;Submit&quot;. In various other cases you will
have to fill in some other fields too.
<br>
Note that it is important to choose all the fields and options in the correct
order, that is, from above to below. For example, choosing a currency before you
have entered the previous fields asking for the member name, will not work.
<br><br>
<ul>
	<li><b>(login) name:</b> If the payment is to another member, and this is
	not already clear from the context, you'll have to fill in either the login
	name or the real name of the member you want to pay. The field works by
	auto-completion: just typing in the first few letters is most of the time
	enough.
	<li><b>Amount:</b> just enter the amount.
	<li><b>Currency:</b> This field appears just after the amount field. It is
	only visible if more than one currency is possible. This depends on the local
	configuration of your organization.
	<li><b>Transaction type:</b> It can be that more than one transaction type
	is possible. In that case you will have to choose the transaction type from the
	drop down box.
	<li class="admin"><b>Pay in past:</b> An administrator can chose to
	perform the payment in the past. This is mostly done for accountability issues
	and should only be used in rare cases. This must be enabled in the <a
		href="${pagePrefix}groups#manage_group_autorisaties_admin_member"><u>
	member autorisaties for administrator</u></a>.
	<li><b>Scheduling:</b> If scheduled payments are allowed for this type of
	payment you can choose to have the payment processed (automatically) at a
	future date or as multiple payments at recurrent future dates (for more
	information see <a href="#pay_scheduled"><u>Scheduled payments</u></a>).
</ul>
<br><br>After submitting you will be asked for a confirmation. The Units are
directly transferred from your account to the account of the other party. The
transaction will show up in the account history of both, for the sender (payer)
in red with a - sign and for the receiver in blue with a + sign.
<hr class="help">

<a name="transaction_confirm"></a>
<h3>Transaction confirmation</h3>
In this window you are asked to confirm the payment you just entered. Check all
the information thoroughly, and click &quot;submit&quot; if everything is
correct.
<br>
If a mistake is made, you can use the &quot;back&quot; button.
<hr class="help">

<A NAME="to_member"></A>
<h3>Payment to member</h3>
This window allows you to make a payment to a member. In most cases, you
can just fill in the description, the amount, and the member name if this is not
yet known, and click &quot;Submit&quot;.
<br>
<a href="#payments"><u>Click here</u></a>
for a general description of the payment windows.
<hr class="help">

<A NAME="to_system"></A>
<h3 class="admin">Transfer between System accounts</h3>
<h3 class="member">Payment to System</h3>
<span class="admin"> In this window you can make a payment between system
accounts.
<br><br>
</span>
<span class="member"> In this window you can make a payment to one of the
organization accounts or &quot;system accounts&quot;.</span>
<a href="#payments"><u>Click here</u></a>
for a general description of the payment windows.
<hr class="help">

<A NAME="as_member_to_system"></A>
<span class="broker admin">
<h3>Payment as Member to System</h3>
In this window you can make a payment as a member from the member account to a
system account.
<br><br><a href="#payments"><u>Click here</u></a> for a general description of
the payment windows.
<hr class="help">
</span>

<span class="broker admin"> <A NAME="as_member_to_member"></A>
<h3>Payment as Member to Member</h3>
In this window you can make a payment as a member from the member account to
another member account.
<br><br><a href="#payments"><u>Click here</u></a> for a general description of
the payment windows.
<hr class="help">
</span>

<A NAME="member_self_payments"></A>
<h3 class='member'>Transfer between my accounts</h3>
<h3 class='admin'>Transfer between accounts of member (by admin)</h3>
<span class="admin"> It is possible for an administrator (with
autorisaties) to perform a self payment as if the admin were the member.</span>
<br>
A self payment allows to make a transfer from one account to another account of
the same owner (member). A self payment works the same as a common payment to
another member.
<br>
<a href="#payments"><u>Click here</u></a>
for a general description of the payment windows.
<hr>

<a name="scheduled"></a>
<h2>Scheduled payments</h2>
The scheduled payments function allows a member to create future scheduled
payments (installments) to other accounts. This can be a single scheduled
payment which is done only once, multiple payments as a &quot;pack&quot;, or
recurrent (periodic) payments (e.g monthly). The payments will be done
automatically at the specified dates.
<br>
Scheduled payments can also be combined with invoices. A member that sends an
invoice to another member will be able (if she/he has the autorisaties) to
specify if the invoice needs to be paid directly or can be paid at a scheduled
(future) date or multiple payment dates. Once the receiving member accepts the
invoice the scheduled payments will appear in his scheduled payment list (and
charged at the dates specified by the member that has send the invoice).

<span class="admin"> It is possible (in the system configuration) to
allow any type of payment to be scheduled. For enabling scheduled payments, you
need to do the following:
<ol>
	<li><b>Permissions:</b> First, you need to set all the <a
		href="${pagePrefix}groups#manage_group_autorisaties_member"><u></u></a> for the
	member group. At the moment of writing, there are three autorisaties for member
	groups which can be enabled, but you may not want to enable them all. If you
	want to allow <a href="${pagePrefix}brokering"><u>brokers</u></a> or admins to
	do scheduled payments for the member, you should check the autorisaties for
	those groups too.
	<li><b>Group Settings:</b> For member groups, there is a <a
		href="${pagePrefix}groups#edit_member_group"><u>special group setting</u></a>
	(&quot;Menu: Users & Groups > Permission groups&quot; and click on the edit
	icon <img border="0" src="${images}/edit.gif" width="16" height="16">&nbsp;
	of a member group) for scheduled payments. This one is often overlooked, so
	don't forget to set these.
	<li><b>Transaction Type:</b> In the <a
		href="${pagePrefix}account_management#transaction_types"><u>
	transaction type</u></a> the scheduling must be enabled. In the <a
		href="${pagePrefix}account_management#transaction_type_details"><u>transaction
	type properties window</u></a> is a special checkbox &quot;allow scheduled
	payments&quot;.<br>
	<b>Note:</b> For some payment types, scheduling is not available. These are
	member to system transaction types, and self payment transaction types.
</ol>
This should set scheduled payments to work. In this case, each <a
	href="#payments"><u> payment window</u></a> will show a &quot;Scheduling&quot;
drop down when this is relevant.
<br><br>Scheduled payments can be searched via the &quot;Menu: Accounts >
Scheduled payments&quot;.
</span>
<span class="member"> Your Scheduled payments can be searched via the
&quot;Menu: Account > Scheduled payments&quot;. </span>
<hr class="help">

<a name="pay_scheduled"></a>
<h3>Make a scheduled payment</h3>
This help item is about
<a href="#scheduled"><u>Scheduled payments</u></a>
and describes the special fields for this inside the
<a href="#payments"><u>payment</u></a>
window.
<br><br>The &quot;scheduling&quot; drop down has three possible values:
<ul>
	<li><b>Not scheduled (process immediately):</b> choose this if you don't
	want to use scheduled payments.
	<li><b>Schedule for future date:</b> if you choose this option, the
	payment will be performed at the date you specify. The date must be specified
	in the &quot;scheduled for&quot; edit which should appear if you select this
	option. You may want to choose the date picker via the calendar <img border="0"
		src="${images}/calendar.gif" width="16" height="16">&nbsp; icon.
	<li><b>Multiple scheduled payments:</b> This is the most sophisticated
	form of scheduled payments. You can split up a single payment into multiple
	sub-payments by choosing this option. Of each sub-payment, the date and amount
	can be set individually.<br>
	The following elements are available in the form:
	<ul>
		<li><b>Payment count:</b> The number of sub-payments you want to make.
		For example: 10 payments, one every week. The amount you specified above is
		divided into equal parts.
		<li><b>First payment date:</b> You may want to use the date picker via
		the <img border="0" src="${images}/calendar.gif" width="16" height="16">&nbsp;
		calendar icon.
		<li><b>Payment every:</b> Use these two drop downs to choose the period.
		
		<li><b>Calculate:</b> You may want to use this button, in order to view
		how much will be paid exactly on which dates. As soon as you click this
		button, an overview of payment dates and amounts is shown. You can change
		these dates and amounts, as long as you take care that they all sum up to the
		amount you filled in above.<br>
		Note: this option does not process anything. It is merely use to preview the
		amounts and dates.
	</ul>
</ul>
<hr class="help">

<A NAME="scheduled_payments_by_admin"></a>
<A NAME="scheduled_payments_by_member"></a>
<h3>Search scheduled payments</h3>
Here you can search for
<a href="#scheduled"><u>scheduled payments</u></a>
. The following elements in the form can be specified. Please note that leaving
a field blank results in returning all values for that field.
<ul>
	<li><b>Search Type:</b> Here you can specify &quot;outgoing&quot; payments
	or &quot;incoming&quot; payments. An outgoing payment is just a normal payment;
	&quot;incoming&quot; means an <a href="${pagePrefix}invoices"><u>invoice</u></a>
	of which the sender specified that it can be paid with scheduled payments.
	<li><b>Account:</b> select an account type in the drop down. This is only
	visible if more than one possibility is available.
	<li><b>Status:</b> &quot;Open&quot; means &quot;not yet paid&quot;; the
	rest is self explanatory.
	<li><b>login name / member:</b> In these two fields you can specify a
	member to which the payment was done. The fields are auto-completed.
	<li><b>From date / To date:</b> you may specify a time period here. You
	may want to use the date pickers via the calendar <img border="0"
		src="${images}/calendar.gif" width="16" height="16">&nbsp; icon.
</ul>
<hr class="help">

<a name="view_scheduled_payment"></a>
<h3>Scheduled payment details</h3>
This window will show the details of the
<a href="#scheduled"><u>Scheduled payment</u></a>
. You can click on the names of the persons to go to their profiles.
<br>
You can click the print
<img border="0" src="${images}/print.gif" width="16" height="16">
&nbsp; icon to print the details.
<br><br>If you have the autorisaties, two buttons may be available at the bottom
of the window:
<ul>
	<li><b>Block:</b> This allows you to temporary block the payment. It will
	not be performed until you cancel or unblock. A block payment can be unblocked.
	
	<li><b>Unblock:</b> Deblocks the payment, so that it will be performed on
	the originally scheduled date. If the payment date has passed, this button will
	not be visible. In that case, you can still pay by going to the <a
		href="#sheduled_payment_transfers">scheduled payment transfers</a> window
	below, and click the view <img border="0" src="${images}/view.gif" width="16"
		height="16">&nbsp; icon of the transfer.
	<li><b>Cancel:</b> The difference with the block button is that canceling
	a scheduled payment is definitive. The scheduled payments that are open will
	not be payed, and will be removed definitely, without having the option to
	un-cancel it. The scheduled payments that have been paid will not be undone.
</ul>
<hr class="help">

<br><br><A NAME="sheduled_payment_transfers"></A><!-- Link is correct, but with Typo -->
<h3>Scheduled payment transfers</h3>
This page shows all the transfers (sub-payments) that are part of a
<a href="#pay_scheduled"><u>multiple scheduled payment</u></a>
. You can click on the view
<img border="0" src="${images}/view.gif" width="16" height="16">
&nbsp; icon to go to the details of a payment.
<hr class="help">


<A NAME="scheduled_payments_result"></A>
<h3>Search results (scheduled payments)</h3>
This result window shows a list with the
<a href="#scheduled"><u>scheduled payments</u></a>
according to the search criteria you specified above.
<br><br>The following is shown:
<ul>
	<li><b>Date:</b> the date of scheduling
	<li><b>Login name:</b> you may click on this to go to the member's
	profile,
	<li><b>Amount</b>
	<li><b>Parcels:</b> the first number shows how many partial payments of
	this scheduled payment have been paid already; the second number shows the
	total number of partial payments in this scheduled payment. If the payment
	isn't cut up in more partial payments but is just a single one time payment,
	then this second number will be &quot;1&quot;.
	<li><b>Status:</b> can be &quot;scheduled&quot;, &quot;blocked&quot;,
	&quot;awaiting authorization&quot; (see <a href="#geautoriseerd"><u>geautoriseerd
	payments</u></a>)
	<li><img border="0" src="${images}/view.gif" width="16" height="16">&nbsp;use
	this icon to view the details of this transfer. There you will have the option
	to print the details, and to block, deblock or pay the payment (providing you
	have the correct autorisaties for it).
</ul>
<hr>

<a name="authorized"></a>
<h2>Geautoriseerde betalingen</h2>
Het kan worden ingesteld in de systeemconfiguratie dat betalingen moeten worden
geautoriseerd voordat deze worden uitgevoerd. De autorisatie kan worden verricht 
door een administrator, werver, of de ontvangende deelnemer, afhankelijk van hoe 
het ingesteld is in de configuratie. Er kan meer dan een autorisatieniveau zijn. 
Dat wil zeggen dat meer dan een persoon autorisatie moet verlenen aan de transactie,
met verschillende mogelijke criteria.
<br>
Zolang een transactie niet is geautoriseerd blijft deze in de statuse &quot;Wachtend
op autorisatie&quot;. De autorisator ontvangt een notificatie over de wachtende
transactie. Zowel de deelnemer als de autorisator hebben toegang tot een overzicht
van transacties die wachten op autorisatie. Zowel de betaler als de begunstigde ontvangen 
een notificatie als de transactie is geautoriseerd.

<span class="admin"> Authorized payments are managed per <a
	href="${pagePrefix}account_management#transaction_types"><u>
transaction type</u></a>; there are various settings available.
<br><br>Authorized payments can be enabled as follows:
<ol>
	<li><b>autorisaties:</b> First you should take care that all relevant <a
		href="${pagePrefix}groups#autorisaties"><u>autorisaties</u></a> are set. You may
	want to set the autorisaties for admins, brokers and members. For each of those
	user groups there are various autorisaties on authorization; you may want to use
	your browser's search function (usually ctrl-f) to find them on the page.
	<li><b>set authorization on transaction type:</b> On the <a
		href="${pagePrefix}account_management#transaction_types"><u>transaction
	type</u></a>, you need to enable authorization. This is done via the &quot;requires
	authorization&quot; field in the <a
		href="${pagePrefix}account_management#transaction_type_details"><u>transaction
	type details window</u></a>. <b>Note:</b> this authorization option cannot be unset as
	long as there are still transactions waiting to be geautoriseerd.
	<li><b>set authorization level(s):</b> After that, you need to set
	authorization levels. This is done one window below, in the <a
		href="${pagePrefix}account_management#geautoriseerd_payment_levels"><u>
	geautoriseerd payment levels window</u></a>. Please refer to the local help for details.
</ol>
</span>
<span class="member">
<br><br>Authorization for members simply means that the receiver will have to
accept a payment from others before the transaction is made. In this the
receiver had the possibility to deny the payment (e.g. the product is not
available). Both receiver and payer will receive a notification. This
configuration is similar to the use of invoices and quite rare. It is better not
to use receiver authorizations and invoices in the same system.
</span>
<br><br>You can find geautoriseerd payments at the following locations in the
software:
<ul>
	<li><b>Menu: Accounts > To authorize</b> will give an overview of payments
	which need to be geautoriseerd by you. This menu is only available if you have
	autorisaties to authorize incoming payments.
	<li><b>Menu: Accounts > Authorizations</b> allows you to search for any
	authorizations, past or present, geautoriseerd, denied or canceled. All past
	authorization actions done by you can be found via this window.Here you can
	search for authorization actions done systemwide, so not only payments
	geautoriseerd by an admin.<br>
	Note that this option is for searching on authorization actions. So transfers
	waiting on authorization are not found (as no action has been taken on them
	yet).
	<li class="broker"><b> Menu: Brokering > To authorize</b> This is an
	overview of payments of your members you need to authorize as broker (in
	contrast to Menu: Account > To Authorize where you will find your personal
	geautoriseerd payments).
	<li class="broker"><b> Menu: Brokering > Authorizations</b> The same as
	the Menu: Account > Authorizations item, but not on authorizations regarding
	your brokering activities.
</ul>
<hr class="help">

<a name="transfers_awaiting_authorization_by_member"></a>
<a name="transfers_awaiting_authorization_by_admin"></a>
<h3>Transfers to authorize</h3>
Use this window to get an overview of the transfers which have to be
<a href="#geautoriseerd"><u>geautoriseerd</u></a>
by you. As always, leaving a field empty means that all possible values for that
field are included in the search result. So hitting the &quot;search&quot;
button without specifying any of the fields will result in all payments awaiting
authorization.
<br><br>You can specify the following search criteria:
<ul>
	<li><b>login / member name:</b> these fields are auto-completed, so just
	entering the first letters would be enough
	<li><b>from date / to date:</b> you can specify a period here, and you may
	want to use the date pickers via the calendar <img border="0"
		src="${images}/calendar.gif" width="16" height="16">&nbsp; icon.
	<li><b>Transaction type</u></b> Search by transaction type.
	<li span class="admin"><b>Only if broker's can't authorize:</b> Checking
	this checkbox includes only the results where you as admin are the only one
	able to authorize.
</ul>
The results will be displayed in the window below.
<hr class="help">

<a name="transfers_awaiting_authorization_result"></a>
<h3>Search Results (transfers awaiting authorization)</h3>
In this overview you will see the transactions which are still awaiting your
<a href="#geautoriseerd"><u>authorization</u></a>
. The negative amounts are outgoing payments that need authorizations and the
positive payments are incoming payments that are waiting for authorization.
<br><br>Click the view <img border="0" src="${images}/view.gif" width="16"
	height="16">&nbsp;icon to open the details window, where you can
authorize or deny the payment.
<hr class="help">

<a name="transfer_authorizations_by_admin"></a>
<a name="transfer_authorizations_by_member"></a>
<h3>Authorized transaction actions</h3>
With this window you can search on
<a href="#geautoriseerd"><u>authorization</u></a>
actions. The form is quite straightforward. If you leave a field blank, it means
that any possible value for the field is returned in the result.
<br>
The following search options are available:
<ul>
	<li><b>Transaction type:</b> Search by transaction type.
	<li><b>Search by action:</b>
	<ul>
		<li><b>geautoriseerd:</b> Approve payments.
		<li><b>denied:</b> Denied payments.
		<li><b>canceled:</b> Canceled payments (by others).
	</ul>
	<li><b>Search by Member:</b> Search for individual member.
	<li><b>Search by period:</b> Search by data range.
</ul>
When done, you can click the &quot;search&quot; button at the bottom of the
page. The results will show up in the window below.
<hr class="help">

<a name="transfers_authorizations_result"></a>
<h3>Search results authorizations history</h3>
Shows the search results for the criteria you specified in the window above. Use
the
<img border="0" src="${images}/view.gif" width="16" height="16">
&nbsp; view icon to get details on the item.
<hr class="help">

<a name="transaction_authorizations_detail"></a>
<h3>Authorizations actions</h3>
This window shows all the
<a href="#geautoriseerd"><u>authorization</u></a>
actions which yet have been performed on the above transaction. These can be
authorizations, but also denials, or cancelings. It shows the date of the
action, and who did the action.
<hr class="help">

<h2>Miscellaneous payment windows</h2>
Below are given some general and miscellaneous help windows concerning payments.
<hr>

<a name="accessing_channels"></a>
<h3>Accessing payment channels</h3>
Depending on the configuration a member can make payments via various payment
channels.
<ul>
	<li>The most common is the main web access page (www.domain.com/cyclos)
	<li>Another useful payment channel is a simple page where members can just
	login and perform a quick payment (www.domain.com/posweb/pay).
	<li>Members / businesses that want to receive payments from
	customers/clients in a POS (point of sale) kind of way can use the POSweb page
	(www.domain.com/posweb/receive). Note that the clients need to generate a
	personal PIN to validate the payment.
	<li>For members /businesses that want to be able to pay and receive at the
	same page can use the access channel (www.domain.com/posweb).<br>
	This is a access page is commonly used by local &quot;cache points&quot; where
	clients can retrieve or redeem vouchers (scrip) or physical money.
	<li>For members /businesses that have &quot;paydesk&quot; operators that
	can make payments and accept payments from customers/clients the operator
	POSweb page can be used (www.domain.com/posweb/operator).<br>
	<li>Mobile payments can be done from the URL's
	www.domain.com/cyclos/mobile (wap2) and www.domain.com/cyclos/wap (wap1).
</ul>
The availability of the payment channels depends on the configuration of the
system. In general receiving payments will demand the client to enter a PIN
number. Making payments from the POSweb channels works in the same way as the
main web access, requiring the login password or transaction transaction password
depending of the configuration.

<hr class="help">

<a name="request"></a>
<h3>Request for payment via other channel</h3>
In this window you can request a payment via another payment medium (<a href="#accessing_channels"><u>
channel</u></a>).
It works much like an invoice in the way that the payment will be activated as
soon as it is accepted. The difference is that this payment request needs a PIN
and a confirmation with the payment request ID (provided in the SMS) Currently
only SMS payment requests are available. The administration will need to enable
the SMS channel before this function can be used.
<br><br>It works like this: when putting a member in the (autocomplete) login
name / name field, writing an amount and description and hitting
&quot;submit&quot; a payment request will be send via the SMS channel. This
means that the member will receive instantly an SMS that can be be confirmed by
replying it and providing a PIN. After a payment request have been send the
status will change (see help below). When the member confirms the payment by
replying with an SMS the status will change to &quot;Paid&quot; (and the product
can be handed over or send)
<hr class="help">

<a name="search_requests"></a>
<h3>Search Payment requests</h3>
In this window you can search for payment request. By default it will show all
pending and sucesful (confirmed & paid) requests. You can also chose filter on
other status and search by member as well.
<br><br>The result window will show the payment request according to the search
filters. The page is automatically refreshed every 5 seconds (and will a status
change automatically)
<hr class="help">

<a name="account_overview"></a>
<h3>Account overview</h3>
This window shows a list with all your accessible accounts.
<ul>
	<li><img border="0" src="${images}/view.gif" width="16" height="16">&nbsp;
	Click the icon if you want to enter the account;
</ul>
<hr class="help">

<a name="transaction_history"></a>
<h3>Transaction summary</h3>
This window has various options to search for transactions. You can select from
the following options:
<UL>
	<LI><b>Status:</b> This is the status of payments that needs
	authorization. It will only show up when authorization is enabled for member
	payments or loans. It will show the status:
	<ul>
		<LI><b>Awaiting authorization:</b> The payment or loan needs to been
		geautoriseerd before the transfer will be processed.
		<LI><b>Processed:</b> The payment or loan has been geautoriseerd and
		processed.
		<LI><b>Denied:</b> The payment or loan has been denied.
	</ul>
	<LI><b>Payment type:</b> With this drop down box you can filter on a
	specific payment type.
	<LI><b>Login name / member:</b> Displays only transactions for a certain
	person, by filling in the login name or member of that person. The field will
	be auto completed while typing.
	<LI><b>Date range:</b> Displays only transactions within the date range,
	by filling in a start and/or end date.
	<LI><b>Description:</b> Search for a certain description, by filling in a
	term (key word) in the <I>description</I> box. Filling in &quot;bike&quot;
	shows all transactions with the word &quot;bike&quot; in the description or
	title.
	<LI><b>Transaction number:</b> If transaction numbers are enabled in the
	system you can use this field to search by transaction number.
</UL>
<hr class="help">


<a name="transaction_history_result"></a>
<h3>Transaction summary results</h3>
This window shows the result of the payment summary result.<br>
If you click the print icon <img border="0" src="${images}/print.gif"
	width="16" height="16">&nbsp; (located in the top bar next to the help
icon), it will present a printable version of your search result with all
transactions and a summary report.
<br>If you click the print save <img border="0" src="${images}/save.gif"
	width="16" height="16">&nbsp; (located in the top bar next to the print
icon), you can download the result as a CSV file.
<br><br>The first (top) section of the window shows the following informaiton:
<ul>
	<li><b>Account balance:</b> The account balance.
	<li class="member"><b>Lower credit limit:</b> The lower credit limit
	This can be either zero or negative. (if zero the limit is now showed)
	<li class="member"><b>Upper credit limit:</b> The upper credit limit
	This can be either zero or positive. (if zero the limit is now showed)
	<li class="member"><b>Available balance:</b> The available balance that
	can be spend.
	<li class="member"><b>Reserved amount:</b> This is the amount that is
	temporary reserved and cannot be used for payments. This can be because there
	are pending transactions for authorizations or a reserve for the coming
	periodic interest or demurrage charges.
</ul>
Below this section a list is shown with all payments (made and received). Received (incoming)
payments show with a + sign in front; Outgoing payments show up with the - sign
in front. The list shows the payment date, member (receiver or payer) and the
description of the payment. The member/login name is a link to the member
profile. <br>
When clicking the view icon <img border="0" src="${images}/view.gif" width="16" height="16">
&nbsp; of a payment a window will open with the description for that payment.
<hr class="help">

<a name="transaction_detail"></a>
<h3>Transactiedetails</h3>
Dit venster toont details van de gekozen transactie. De transactiedetails kunnen 
worden afgedrukt. Doe dit alleen als het nodig is, denk aan de bomen.
Als een &quot;bovenliggende&quot; of
&quot;onderliggende&quot; betaling bestaat (bijvoorbeeld een transactieheffing) 
wordt deze getoond in het onderste deel van het venster.
<br><br><span class="broker admin"> Als de transactie moet worden geautoriseerd,
wordt deze mogelijkheid getoond. Hierbij moet een commentaar worden ingevuld.
Als het aankruisvakje &quot;Tonen aan deelnemer&quot; is aangevinkt, is het
commentaar zichtbaar voor de deelnemer. Zo niet, dan is het commentaar alleen
zichtbaar voor de administratie. 
</span>
<span class="admin">
Onder bepaalde condities is het mogelijk een transactie terug te boeken via <a href="#charge_back"><u>
Terugboeken</u></a>. Is dit het geval, dan ziet u hiertoe een knop in dit venster. 
</span>
<hr class="help">

<span class="admin">
<a name="charge_back"></a>
<h3>Betalingen terugboeken</h3>
Als administrator met de juiste autorisaties kunt u een betaling
&quot;terugboeken&quot;. Hierdoor wordt een tegengestelde betaling uitgevoerd met 
hetzelfde bedrag. Als er andere transacties zijn gegenereerd (heffingen of commissies) 
worden die ook teruggeboekt. Als een terugboeking mogelijk is, verschijnt een knop 
met deze titel in het venster <a href="#transaction_detail"><u>
transactiedetails</u></a>. Deze knop is voor een bepaalde tijd beschikbaar nadat de 
transactie heeft plaatsgevonden. Deze tijd kan worden ingesteld in 
&quot;Menu: Instellingen > 
<a href="${pagePrefix}settings#local"><u>Lokale instellingen > Terugboekingen</u></a>&quot;.
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