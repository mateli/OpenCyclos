<div style="page-break-after: always;">
<br><br>
Reports allow you to get an overview of the state of the system. You can view several kinds of reports:
<ul>
	<li><b>individual activity reports of one member</b>
	<li><b>tables with individual scores for a set of members</b>
	<li><b>system wide scores</b>
</ul>
The reports score the number of transactions, the trade volume, the
number of ads and the number of references set out.

In contrast to reports, Cyclos also offers
<a href="${pagePrefix}statistics"><u> statistics</u></a>, 
which are extended reports with statistical analysis.

<br><br><i>Where to find it?</i><br>
You can reach reports in the following ways: <span class="admin">
There is a main menu item on reports: &quot;Menu: Reports&quot;. It
contains various sub-items. </span> Individual reports of members can be viewed
via a member's <a href="${pagePrefix}profiles"><u> profile</u></a>. <span
	class="admin">
	
<br><br><i>How to get it working?</i><br>
All the report types have a <a
href="${pagePrefix}groups#manage_group_permissions_admin_system"><u>
permission</u></a> which must be enabled before they will be visible. The
permissions can be found in a block entitled &quot;Reports&quot;.
</span>
<hr>


<a name="member_activities"></a>
<h3>My reports / Member Activities</h3>
Here you can view a small report on present data of yourself or another
member. You will see data on
<a href="${pagePrefix}references"><u>references</u></a>, and on
<a href="${pagePrefix}advertisements"><u>advertisements</u></a>
(=&quot;products & services&quot;), as well on
<a href="${pagePrefix}invoices"><u> invoices</u></a>.
<br>
If the data is about yourself, you will also be able to view data on
your account(s) and your <a href="${pagePrefix}invoices"><u>invoices</u></a>.
<br><br>
If permitted  account information can be viewed by other users. This 
depends on the system configuration.  
<hr class="help">


<span class="admin"> <a name="current_state"></a>
<h3>State overview</h3>
&quot;State overview&quot; enables you to view a report of the
present state of the system or any date in the passed.
<a href="#current_result"><u>Click here</u></a> for an overview 
of what will be shown.
<br><br>
Use the check boxes to select what you want to view, and then
click &quot;submit&quot;. <a name="current_result"></a><br>
Use &quot;Current time&quot; if you want to see the state of the 
system at the current time and &quot;history&quot; if you want the
state reports for a date in the history. 

<h3>State report results</h3>
The following are shown in this page:
<ul>
	<li><b>Member <a href="${pagePrefix}groups"><u>Group</u></a>
	Information</b>: This section will show the number of Enabled Members;
	these are all the Members who are able to log-in into this software.
	This in contrary to the rest of the page, which will show all the
	Member groups and their amounts of members belonging to them.
	<br><br>
	<li><b><a href="${pagePrefix}advertisements"><u>Advertisements</u></a>
	Information</b>:
	<ul>
		<li><b>Number of Active Members with Advertisements:</b> all the
		accounts who are able to log-in, and who have placed one or more ads.
    	<li><b>Number of Active Advertisements:</b> all the ads which are
		valid for today.
		<li><b>Number of Expired Advertisements:</b> all the ads which
		are expired.
		<li><b>Number of Scheduled Advertisements:</b> all the ads which
		are saved already, but will become active in the future.
	</ul>
	<br><br>
	<li><b>System accounts</b>: This section will show all system
	accounts and their current balance.
	<br><br>
	<li><b>Member accounts</b>: This section will show all the Member
	account types and the sum of the balances. Normally there is only one
	Member account type in the system. All the members have one account of
	this type.
	<br><br>
	<li><b><a href="${pagePrefix}invoices"><u>Invoices</u></a></b>:
	<ul>
		<li><b>Number of member invoices:</b> Total number of incoming
		and outgoing invoices between members.
		<li><b>Total sum of member invoices:</b> Total sum of incoming
		and outgoing invoices between members.
		<li><b>Number of incoming system invoices:</b> Total number of
		invoices from Member to System accounts.
		<li><b>Sum of incoming system invoices:</b> Total sum of invoices
		from Member to System accounts.
		<li><b>Number of outgoing system invoices:</b> Total number of
		invoices from System to Member accounts.
		<li><b> Sum of outgoing system invoices:</b> Total sum of
		invoices from System to Member accounts.
	</ul>
	<br><br>
	<li><b><a href="${pagePrefix}loans"><u>Loans</u></a></b>:
	<ul>
		<li><b>Number of open loans:</b> All the member loans that are
		not entirely repaid.
		<li><b>Remaining amount of open loans:</b> Sum of all the
		remaining (open) loan payments.
	</ul>
	<br><br>
	<li><b><a href="${pagePrefix}references"><u>References</u></a></b>:
	<br><br>For all different reference categories, the table shows how
	often this particular category has been given.
</ul>
<hr class="help">


<a name="member_lists"></a>
<h2>Member lists</h2>
This function lets you view a list with members containing various data
on each member. You may request the data about the present state, or you
may choose a point in history.<br>
You may request data on the following subjects:
<ul>
	<li>member and broker name
	<li>ads
	<li>references
	<li>account limits and balances.
</ul>

Note that this is all &quot;point data&quot;, which means the data is
about a specific point in time. Consequence of this is that information
on trade activity cannot be given (as the trade on a point in time is
senseless). If you want data over a range in time, you should go to
&quot;Menu: <a href="#member_reports"><u>member reports</u></a>&quot; or
to &quot;Menu: <a href="${pagePrefix}statistics"><u>statistics</u></a>&quot;.
<br><br>If you check a lot of check boxes, it could take a few seconds to
make the calculations. So please have patience.
<br><br>At the bottom of the page, you can <a href="#results"><u>print
or download</u></a> the results.
<br><br>The following options are available:
<ul>
	<li><b>Time:</b> first you should select a point in time on which
	you would have the list. There are two options:
	<ul>
		<li><b>current time:</b> give of course a list with present data.
		<li><b>history:</b> gives a list on a date in the past. You
		should specify the date if you choose this option. You may use the
		date picker by clicking the calendar icon ( <img border="0"
		src="${images}/calendar.gif" width="16" height="16">&nbsp;).
	</ul>
	<br><br>
	<li><b>Broker:</b> In this section, you can specify the name or
	login name of a <a href="${pagePrefix}brokering"><u>broker</u></a>.
	Then, your list with data will be limited to only the members of this
	broker.
	<br><br>
	<li><b>Members:</b> In this section you can specify the following:
	<ul>
		<li><b>Member name:</b> check this if you want the name of the
		member to be printed in each row of the list. If you don't check this,
		the member data will be printed anonymously.
		<li><b>broker login:</b> check this and the broker login name
		will be printed with each item in the list.
		<li><b>broker name:</b> check this and the broker's name (real
		name) will be printed with each item in the list.
		<li><b>Permission groups:</b> Use this multi drop down to select
		the groups of which you want this list. If you select for example
		&quot;full members&quot;, all members in this group will be included
		(of course, depending on what you entered in the &quot;broker&quot;
		section).
	</ul>
	<br><br>
	<li><b>Ads:</b> In this section you specify which data you want to
	be included about <a href="${pagePrefix}advertisements"><u>advertisements</u></a>.
	For each checkbox, the number of ads with that specific <a
	href="${pagePrefix}advertisements#ad_status"><u> status</u></a> are
	printed.
	<ul>
		<li><b>active ads</b>
		<li><b>expired ads</b>
		<li><b>permanent ads</b>
		<li><b>scheduled ads</b>
	</ul>
	<br>
	<br>
	<li><b>References:</b> In this section you can specify if you want
	to view information on &quot;given&quot; and &quot;received&quot; <a
		href="${pagePrefix}references"><u>references</u></a>. <br>
	<br>
	<li><b>Accounts:</b> In this section you can specify which account
	information to print:
	<ul>
		<li><b>lower credit limit</b>
		<li><b>upper credit limit</b>
		<li><b>account balance</b>
	</ul>
</ul>
<hr>


<a name="member_reports"></a>
<h2>Member Reports</h2>
This function allows you to print a report with transaction information related to members.

<br><br>NOTE that you have to go through the form in the given order,
from top to bottom. The payment filter box may for example be empty if
you do not first select an account type.
<br><br>At the bottom of the page, you can <a href="#results"><u>print
or download</u></a> the results.
<br><br>On the form, you may specify the following elements:
<ul>
	<li><b>member name</b>: The login name is always shown. If you
	want the member name to show up you will have to selected it here.
	<li><b><a href="${pagePrefix}brokering"><u>broker</u></a>
	login and name</b>: this information won't show up unless you check these
	checkboxes.
	<li><b>Member groups</b>: here you can specify which <a
	href="${pagePrefix}groups"><u>member group(s)</u></a> you want to view.
	<li><b>Account type</b>: usually, a member group has one <a
	href="${pagePrefix}account_management#accounts"><u>type of
	account</u></a>. However, it is possible that members have multiple account
	types. Specify the type you want to see here.
	<li><b>from and to fields</b>: to specify the date range.
	<li><b>what to show?</b>: this is probably the most important
	field. Here you specify if you want to view transactions or invoices.<br>
	If you selected transactions:
	<ul>
		<li><b>Payment filters</b> allows you to specify which kind of
		transactions you want to have listed. The <a
		href="${pagePrefix}account_management#transaction_types"><u>payment
		filters</u></a> can be specified in the <a
		href="${pagePrefix}account_management#account_search"><u>&quot;Manage
		account&quot;</u></a> section. For the payment filter to show up in the
		reports function they must be marked with the option &quot;Show in
		reports&quot;.
		<li><b>debit / credit transactions</b>: after you chose a payment
		filter, this will become visible. You <b>must</b> choose at least one
		of these checkboxes. A debit transaction is a payment.
		<li><b>include members without transactions:</b> if checked,
		includes members who did not trade.
		<li><b>details level</b> allows you to specify how much details
		you want to see.
		<ul>
			<li><b>summary</b> gives you only the total sum of all
			transactions in the period, resulting in one line per member.
			<li><b>Transactions</b> lists all transactions in the period, of
			each member with the transaction type and transaction number (if
			used).
		</ul></ul>
		</ul>
	</ul>
</ul>
<hr class="help">


<a name="results"></a>
<h3>Results of reports</h3>
You can choose to types of actions with these reports (buttons at the
bottom of the pages, right):
<ul>
	<li><b>print report:</b> prints the report on the screen, in a
	printer friendly layout. This screen will also include a print button
	to send the report to your printer.
	<li><b>download report:</b> will result in the downloading of the
	results in csv format.
</ul>
<hr class="help">
</span>


<a name="sms_log"></a>
<h3>SMS messages sent</h3>
The system may sent several SMS messages on various occasions (for
example payments via SMS), depending on the configuration.
<span class="admin"> See the <a
	href="${pagePrefix}settings#local"><u> local settings help</u></a> for
more information on this configuration.</span>
<br><br>This window allows you to get an overview of SMS messages sent.
The form is very straightforward, and most elements do not need further
explanation. The &quot;status&quot; drop down allows you to specify if
sending of the SMS was succesful or if it failed. <br>
After clicking &quot;search&quot; the results will be displayed in a
window below.


<span class="admin"> 
<a name="sms_log_search_results"></a>
<h3>SMS messages sent result</h3>
This window will show a list with the results from the above search.
<hr class="help">
</span>


<span class="admin"> 
<a name="sms_log_report"></a>
<h3>SMS log report</h3>
In this window you can search for outgoing SMS messages. The search filters
are self explanatory. There are two types of outgoing SMS messags. Messages 
that are related to SMS operations like a payment confirmation, and 
personal SMS notifications (e.g notification on advertisement match). <br><br>
Outgoing messages are being charged to the organisation
(by the SMS gateway or operator) and it is therefore imporant to keep track
of the messages that are being sent.<br>
The configuration of the SMS charging is defined in the <a
href="${pagePrefix}groups#group_notification_settings"><u>Notification settings</u></a> 
(Group settings).
<br><br>
Note: Members can view their outgoing SMS messages in &quot;Personal - SMS history&quot;
<hr class="help">
</span>


<span class="admin"> 
<a name="sms_log_report_search_results"></a>
<h3>SMS log search result</h3>
This window will show a list with the results from the above search. It is possible
to print and export the list by selecting the corresponding icons.
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

