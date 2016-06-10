<div style="page-break-after: always;"
<a name="top"></a>
<br><br>Loans can provide the basic credit mechanism for
Cyclos. Cyclos supports the essential qualities of a loan including scheduled
payments (installments) and interest, and allows administrators to apply a
variety of fees.

<span class="admin">
<br><br><i>Where to find it / how to get it working.</i><br>
For using loans in cyclos, you should perform the following steps:
<ol>
	<li><b>Make transfer types:</b> Before being able to use loans, the
	appropriate <a href="${pagePrefix}account_management#transaction_types"><u>transaction
	types</u></a> for loans must be created, as each loan must have its own transaction
	type.<br>
	In general, you will have to create two loan transaction types: one providing
	the loan to the member (coming from a system account), and another type which
	the member will use to pay back the loan (so, a member to system account). You
	cannot create the first without specifying the latter, so its best to start
	with the payback transaction type (the one from member to system). For more
	complicated debit systems, you may have to create transaction types for fees or
	interests too.
	<br><br>A new transaction type for <b>paying back the loans</b> is created as
	follows:
	<ul>
		<li>Go to the transaction section, &quot;Menu: Accounts > Account
		management&quot;.
		<li>Choose an account type on which the members will receive the loans.
		Normally, this would be the &quot;member account&quot;. Click the <img
			border="0" src="${images}/edit.gif" width="16" height="16">&nbsp; edit
		icon of this account type.
		<li>In the next window, go to the &quot;<a
			href="${pagePrefix}account_management#transaction_type_search"><u>
		transaction types</u></a>&quot; window, and click the &quot;insert new transaction
		type&quot; button below it. But before you do so, you should check if there is
		already an appropriate transaction type for loan repayments available.
		<li>In the following window, you should choose an account type in the
		&quot;To&quot; drop down box. This would normally be the &quot;debit
		account&quot;, but you'd have to think about which system account types you
		will use for loans.<br>
		Simply fill in the rest of the fields, and if necessary refer to the local
		help system.
	</ul>
	<br>
	When this is done, you can create the new transaction type for <b>granting
	the loan</b>:
	<ul>
		<li>Again, go to the transaction section, &quot;Menu: Accounts > Account
		management&quot;.
		<li>Choose the account type which you specified before in the
		&quot;to&quot; drop down box (see above), which is usually the &quot;debit
		account&quot;; Click the <img border="0" src="${images}/edit.gif" width="16"
			height="16">&nbsp; edit icon of this account type.
		<li>Check if there is an appropriate transaction type for granting loans
		available yet. If not, create one by clicking the &quot;insert new transaction
		type&quot; button.
		<li>In the following window, you should choose an account type in the
		&quot;To&quot; drop down box. This would normally be the &quot;member
		account&quot; you have used before when creating the repayment transaction
		type. By choosing a transfer type, the &quot;is loan&quot; checkbox at the
		bottom of this window will become visible. Check this checkbox, and several
		other fields will become visible. <a href="#make_loan_type"><u>Click
		here</u></a> for more details, or refer to the local help.
	</ul>
	<br>
	<br>
	<li><b>Set appropriate autorisaties:</b> Make sure the appropriate <a
		href="${pagePrefix}groups#manage_groups"><u>autorisaties</u></a> for loans are
	set. Admins and probably brokers must <a
		href="${pagePrefix}groups#manage_group_autorisaties_admin_member"><u>have
	autorisaties</u></a> to grant loans; you may also want to set the <a
		href="${pagePrefix}groups#manage_group_autorisaties_member"><u>member
	autorisaties</u></a> to view and repay loans (and more). Note that the permission to
	repay a loan (by admin or member) must be set explicitly.<br>
	Also, you may want to set autorisaties for <a href="${pagePrefix}loan_groups"><u>
	loan groups</u></a>, if you want to use these.<br>
	<br>
	<li><b>Grant the loan:</b> A loan is granted by going to a member's <a
		href="${pagePrefix}profiles"><u>profile</u></a> and clicking &quot;Grant
	Loan&quot;.<br>
	<br>
	<li><b>Managing loans:</b> Loans can be managed by going to the
	&quot;Menu: Accounts > Manage Loans&quot; section. Payments can be managed via
	the &quot;Menu: Accounts > Loan Payments&quot;.
</ol>
</span>
<span class="member">
<br><br><i>Waar te vinden</i><br>
You can access your loans via the main &quot;Menu:
Account > Loans&quot;. Here you can get an overview, and it is the starting
point for repayments.
</span>
<span class="broker">You can access loans of your members via the <a
	href="${pagePrefix}profiles"><u> profile of a member</u></a>; under actions,
there is a special section for managing loans.</span>
<hr>

<span class="admin"> <a name="make_loan_type"></a>
<h3>Create a loan transaction type</h3>
(<i>Tip: there may be quite some linking to other sections of the help. Use
the backspace button to move back, when your browser doesn't display the back
button)</i>
<br><br>If you checked the &quot;is loan&quot; checkbox it means that the <a
	href="${pagePrefix}account_management#transaction_types"><u>transaction
type</u></a> you are creating is a <a href="#top"><u>loan</u></a> . The loan settings
will appear below as soon as you check this checkbox. <br>
In case of a loan then some other fields will have to be specified too. The most
important of those fields is the first one, the &quot;loan type&quot; field.
First make your choice for this type; the choice determines what other fields
will be visible. <br>
The actual transaction type you are editing or creating just now, is the
transaction type for putting the units on a member's account as a loan. Of
course, a corresponding transaction type for paying back these amounts is
needed; this corresponding transaction type needs to be specified at the field
&quot;repayment transaction type&quot; This will be a member-to-system account
transaction type. If it does not exist yet, you will have to create it first -
this means that you will have to create a repayment type first before you can
continue with creating the present loan transaction type.
<br><br>There are three different <a href="#loan_types"><u>loan types</u></a>
available; the rest of the visible fields will be discussed under each loan
type:
<ul>
	<li><b>Simple loan:</b> must be paid back at (or before) a specific date.
	If your loan is of this type, you must specify the following other fields:
	<ul>
		<li><b>Repayment transaction type: </b> for explanation see a bit above.
		
		<li><b>Days to repay loan:</b> This is the expiration period; after this
		period the loan will show up as &quot;expired&quot; in the loan overview of
		the member and the manage loan function in the administration section. The
		member is supposed to have paid back the loan before it is expired.
	</ul>
	<br>
	<br>
	<li><b>Multiple payments:</b> loans of this type are divided in periodic
	(monthly) repayments, each having its own expiration date. You will have to
	specify the repayment type.
	<li><b>With fees:</b> This is a loan that can have different types of fees
	and periodic repayments. The following fees can be configured:
	<ul>
		<li><b>Monthly interest rate:</b> This is (compound) interest calculated
		per month. The total amount of the loan and other costs (interest, grant fee)
		are spread over a fixed number of periodic equal-sized payments (installment
		loan).
		<li><b>Grant fee: </b> This is the one time fee that needs to be paid for
		the loan. This amount is spread over (included in) all periodic repayments.
		The fee can be a percentage of the total loan amount or a fixed amount.
		<li><b>Expiry fee:</b> This is the fixed amount that has to be paid when
		a repayment has not been done in time (before the expiration date).
		<li><b>Expiry interest: </b> This is the interest charged every day when
		a repayment has not be done in time.
	</ul>
</ul>
<hr class="help">
</span>

<a name="loan_types"></a>
<h3>Loan types</h3>
<br><br>There are three different <a href="#top"><u>loan</u></a> types available:

<ul>
	<li><b>Simple loan:</b> This means that the loan will have to be paid back
	at a specific date. At this date the loan will expire. Within this period the
	member can chose to pay the full amount at once or pay part of the amount in
	several payments. The only thing which counts is that all has to be paid back
	before the expiration date.
	<li><b>Multiple payments:</b><br>
	With this loan type the repayment is divided in periodic (monthly) repayments.
	When granting the loan to the member you can specify the first loan repayment
	date and the number of loan <a href="#component"><u>components</u></a>. Every
	loan installment will have its own (monthly) expiration date. When the
	installment expires it will show up as &quot;expired&quot; in the loan
	installments overview of the member and in the manage loan function in the
	administration section.
	<li><b>With fees:</b> This is a loan that can have different types of fees
	and periodic repayments. Every fee can have it's own repayment type. It is just
	as the previous loan type, but with extra possible fees.
</ul>
<hr class="help">

<span class="admin broker"> <a NAME="loan"></a>
<h3>Grant loan</h3>
With this function you can give <a href="#top"><u>loans</u></a> to a Member. To
be able to give a loan certain conditions must be met first, <a href="#top"><u>click
here</u></a> to see which.
<br><br>The following fields need to be filled in to grant the loan:
<ul>
	<li><b>Loan group:</b> This option will display if the member is the
	responsible member or part of one of more <a href="${pagePrefix}loan_groups"><u>loan
	groups</u></a>. If you don't want to get any loan group involved, but in stead want to
	pass the loan to the member in personal, select the &quot;Personal&quot;
	option. <br>
	<li><b>Identifier:</b> This is the name by which the loan will be
	identified. You may choose anything you like.<br>
	Note: The identifier field is a <a href="${pagePrefix}custom_fields"><u>custom
	loan field</u></a> provided with the default database. You can remove it or create
	other loan custom fields with differerent rules. <br>
	<li><b>Loan type:</b> This is the most important field in the form. Here
	you select which transaction type the loan belongs to. Each of these
	transaction types implies one of the three possible <a href="#loan_types"><u>loan
	types</u></a>. Depending on the implied loan type, the rest of the form will show its
	fields. We will cover the <b>loan type specific fields</b> below.<br>
	<li><b>Description:</b> enter a description for the loan.<br>
	<li><b>Amount:</b> This is the total amount which the member gets on his
	account. It is the initial debt amount, or &quot;Principal&quot; of the loan.<br>
	<li><b>Grant in past:</b> Check this checkbox if the date for this loan
	should <b>NOT</b> be today, but a date in the past in stead. If you check this
	box, you will be asked to specify that date in an extra field which will show
	up.<br>
	<li><i>loan type specific fields:</i> The rest of the fields in the form
	is dependant on what you chose at the &quot;loan type&quot; drop down.
	<ul>
		<li><b>Simple Loan:</b> If the loan transaction type you chose implies a
		&quot;Simple Loan&quot; type, the following fields are visible:
		<ul>
			<li><b>Repayment Date:</b> The date that the loan will need to be repaid
			(&quot;remittance&quot;). At this date an alert will be generated and the
			loan status will change to &quot;expired&quot; (in the loan administration
			section).
		</ul>
		<br>
		<br>
		<li><b>Multiple Payments Loan:</b> If the loan transaction type you chose
		implies a &quot;Multiple repayments loan&quot; type, the following fields are
		visible:
		<ul>
			<li><b>First expiry date:</b> The loan repayment is split up in several
			&quot;loan <a href="#component"><u>components</u></a>&quot;. Here you enter
			the date that the first loan component will need to be repaid (so <b>not</b>
			the entire loan). On this date an alert will be generated and the loan
			component status will change to &quot;expired&quot;.
			<li><b>Number of repayments:</b> The number of monthly repayments (loan
			components).
			<li><b>Calculate:</b> The calculate button will show the different loan
			repayments and their expiration date. Those dates and values can be changed.
			If you change the values make sure that that the total amount of the
			components will be the same as the total loan amount.
		</ul>
		<br>
		<li><b>Loan with Fees:</b> If the loan transaction type you chose implies
		a &quot;Loan with Fees&quot; type, the following fields are visible:
		<ul>
			<li><b>All interest and fees settings:</b> These fields above the
			&quot;description&quot; field indicate the fees which are due. They are just
			for information and cannot be changed. For more information <a
				href="#make_loan_type"> <u>click here</u></a>.
			<li><b>First expiry date:</b> The loan repayment is split up in several
			&quot;loan <a href="#component"><u>components</u></a>&quot;. Here you enter
			the date that the first loan component will need to be repaid (so <b>not</b>
			the entire loan). On this date an alert will be generated and the loan
			component status will change to &quot;expired&quot;.
			<li><b>Number of repayments:</b> The number of monthly repayments
			(components).
			<li><b>Show:</b> This button will show the different loan components and
			their expiration date. Those dates and values can't be changed directly, you
			can only change them by modifying the total amount or the number of
			repayments. The amounts shown have the various fees included.
		</ul>
		<br>
	</ul>
</ul>
<br><br>Note: It is possible to create <a href="${pagePrefix}custom_fields"><u>extra
loan fields</u></a> if this is necessary. For example a loan contract number.
<hr class="help">
</span>

<span class="admin broker"> <a name="loan_confirm"></a>
<h3>Loan confirm</h3>
This screen simply verifies the loan information before the loan is issued.
Check the information, and click &quot;Submit&quot; to issue the loan.
<hr class="help">
</span>

<span class="admin"> <a NAME="search_loans_by_admin"></a>
<h3>Search loans</h3>
With this function you can get an overview of all the member <a href="#top"><u>loans</u></a>.
There are various search options. As always, leaving a field blank means that
you search for all possibilities for that field.
<ul>
	<li><b>Filter:</b> The first two filter option are a combination of loan
	status where &quot;Any open&quot; are all loans that have not been entirely
	repaid or discarded and &quot;Any closed&quot; all loans that have been
	entirely repaid or discarded.<br>
	The rest of the filter options are the different loans <a href="#status"><u>Status</u></a>
	<li><b>Custom field:</b> If a custom payment field exists is defined for
	the loan type and is configured to be included in the loans search it will show
	up after the filter option.
	<li><b>Loan type:</b> If more than one loan type exists you can select
	them in the drop down box. Here, loan type refers to the <a
		href="${pagePrefix}account_management#transaction_types"><u>transaction
	type</u></a> to which the loan belongs.
	<li><b>Member login / name:</b> With this option you can search for a
	specific member's loans. The input field will be auto completed when typing.</li>
	<li><b>Broker (loan agent) login / name:</b> This option allows you to
	view all the member loans related to a specific <a
		href="${pagePrefix}brokering"><u> broker</u></a> (agent). The input field will
	be auto completed when typing.
	<li><b>Transaction number:</b> If transaction numbers are enabled on the
	system you can search by transaction number.
	<li><b>Loan group:</b> With this option you can search for the loans given
	to a specific <a href="${pagePrefix}loan_groups"><u>loan group</u></a>. This
	option is only visible if loan groups exists in the system.
	<li><b>Grant date period:</b> This option allows to search for loans that
	were granted within a specific period.
	<li><b>Expiry date period:</b> This option allows to search for loans that
	will expire within a specific period.
	<li><b>Payment date period:</b> This option allows you to search for loans
	components that have been paid in the specified period.
	<li><b>Pending authorization:</b> This option allows you to search for
	loans components that are pending for authorization. (the option only shows up
	if the admin has autorisaties to view geautoriseerd payments)
	<li><b>Authorization denied:</b> This option allows you to search for
	loans components that have been denied in the authorization process. (the
	option only shows up if the admin has autorisaties to view geautoriseerd payments)
	
</ul>
Click &quot;Submit&quot; to issue the query.
<hr class="help">
</span>

<a NAME="search_loans_result"></a>
<h3>Search loans result</h3>
This window shows the result of the
<a href="#top"><u>loan</u></a>
search query. The window will show a list with the following information (not
all columns may be visible, depending on where you came from and some settings):
<ul>
	<li><b>Member:</b> Member that received the loan. Click on the name to go
	to the <a href="${pagePrefix}profiles"><u>profile</u></a>.
	<li><b>Description:</b> Description of the loan.
	<li><b>Amount:</b> The total amount of the loan.
	<li><b>Remaining amount:</b> The total amount of the loan which the member
	still has to pay.
	<li><b>Payments:</b> The number of loan <a href="#component"><u>components</u></a>.
	The first number is the amount of repayments that have been made already. The
	second number (after the forward slash) is the total number of loan components.
	This will not be visible if the list only contains simple loans without
	installments.
	<li><b>edit icon (<img border="0" src="${images}/edit.gif" width="16"
		height="16">&nbsp;): </b> use this to view the loan details, together with
	some additional information.
</ul>
At the top right of the window there are a few other icons available. The
<img border="0" src="${images}/save.gif" width="16" height="16">
&nbsp; icon will export the list to a
<a href="#csv"><u>csv</u></a>
-file. The
<img border="0" src="${images}/print.gif" width="16" height="16">
&nbsp;print icon will open a print page with the details of all the listed
loans.
<hr class="help">

<a NAME="search_loans_member_by_admin"></a>
<a NAME="search_loans_by_member"></a>
<a NAME="search_loans_member_by_broker"></a>
<span class="admin broker">
<h3>Search Loans of member</h3>
</span>
<span class="member">
<h3>Search my loans</h3>
</span>
With this function you can get an overview of the
<a href="#top"><u>loans</u></a>
<span class="admin broker">of the member</span>
. Just make your choice with the &quot;
<a href="#open"><u>open</u></a>
&quot; or &quot;
<a href="#closed"><u>closed</u></a>
&quot; radio button. The
<a href="#search_loans_result"><u> search result window</u></a>
below will show the results.
<hr class="help">

<span class="admin"> <a NAME="search_loan_payments"></a>
<h3>Search loan payments</h3>
This page allows you to search for <a href="#top"><u>loan</u></a> payment
information - even for loan payment information which hasn't been paid yet. The
following fields are available; as always, not specifying anything will result
in all possible values for that field:
<ul>
	<li><b>Status:</b> Here you can search on the possible loan <a
		href="#status"><u>status</u></a>
	<li><b>Custom field:</b> If a custom payment field exists is defined for
	the loan type and is configured to be included in the loans search it will show
	up after the filter option.
	<li><b>Transaction Type:</b> This is the loan <a
		href="${pagePrefix}account_management#transaction_types"><u>
	transaction type</u></a> of the loan. (only showed if more than one 
	loan transaction type exist).
	<li><b>Member Login/Member:</b> This is the login name and real name of
	the borrower
	<li><b>Broker Login/Name:</b> This is the login/name of the borrowers <a
		href="${pagePrefix}brokering"><u>broker</u></a>.
	<li><b>Period from/to:</b> The date refers to the payment date, in case
	the loan was repaid. If the loan wasn't repaid, the date refers to the
	expiration date of the loan or loan <a href="#component"><u>component</u></a>.

	
</ul>
<hr class="help">
</span>

<a NAME="loan_detail"></a>
<h3>Loan detail</h3>
This page shows details about the
<a href="#top"><u>loan</u></a>
. Depending on the type of loan the page will show several loan values.
<br><br>The print icon (<img border="0" src="${images}/print.gif" width="16"
	height="16">&nbsp;) will open a printable page with the details of the
loan and all the loan <a href="#component"><u>components</u></a>. <span
	class="admin"> In some special states of the loan (when it has status
&quot;expired&quot; or &quot;in process&quot;) you can change the <a
	href="#status"><u>status</u></a> by clicking the button below marked &quot;Mark
this loan as...&quot;. </span>
<hr class="help">

<a NAME="loan_parcels_detail"></a>
<h3>Payments</h3>
This page shows the details about the
<a href="#top"><u>loan</u></a>
<a href="#component"><u> components</u></a>
. All components of the loan are listed in this overview. The table is pretty
straightforward.
<a href="#status"><u>status</u></a>
can be one of several values.
<hr class="help">

<span class="admin"> <a NAME="loan_to_members"></a>
<h3>Loan to members</h3>
This page will show a list of the members that belong to the <a
	href="${pagePrefix}loan_groups"><u>loan group</u></a> of the selected <a
	href="#top"><u>loan</u></a>. The name of the &quot;responsible&quot; member
(member that received the loan) will be displayed in red. Clicking on the names
will bring you to the <a href="${pagePrefix}profiles"><u> profiles</u></a> of
the members.
<hr class="help">
</span>

<a NAME="loan_repayment_by_admin"></a>
<a NAME="loan_repayment_by_member"></a>
<h3>Loan repayment</h3>
This page will show information about the
<a href="#top"><u>loan</u></a>
component and the possibility to repay
<span class="admin">or <a href="#discard"><u>discard</u></a></span>
the loan
<a href="#component"><u>component</u></a>
.
<br>
You can adapt the amount, but it is prefilled with the amount which you are
still due.
<span class="admin"> If you check the &quot;pay in past&quot; checkbox,
the loan repayment will be booked in a past date; you will be asked to specify
this date in an extra edit.</span>
<br><br>If the loan is a loan with <a href="#loan_types"><u>multiple
repayments</u></a> (this would also include a loan with fees), then some extra fields
are available. These fields are not available for simple loans. The
&quot;payment number&quot; refers to the loan component in the overview above;
usually you would pay the next payment in line (the lowest payment number
available and not yet paid back), but you may choose to pay another component.
<br><br><span class="admin">Use either one of the buttons to repay or
discard the loan.</span> <span class="member">click the &quot;repay&quot; button
to repay (part of) the loan.</span>
<hr class="help">

<br><br><a name="glossary"></a>
<h2>Glossary of terms</h2>
<br><br>

<a name="component"></a>
<b>component</b>
of a loan
<br><br>One of a number of successive payments in settlement of a debt. When a
loan repayment is divided into several parts, each of these parts is called an
installment.
<hr class='help'>

<a name="csv"></a>
<br>
<b>CSV</b>
files
<br><br>CSV means &quot;comma separated values&quot;; it is the format for the
files with data which can be downloaded from various search result windows in
cyclos. In this format, the values of fields are, as the title suggests,
separated by comma's (though any other character can be used as separator). <br>
This format can usually be opened by a spreadsheet program like Open Office Calc
or Microsoft Excel. You could also process the CSV file with a text editor in
combination with macro's. Programs such as Word or WordPerfect have excellent
macro facilities to automatically process input files to nicely edited
presentable documents.
<hr class='help'>

<a name="status"></a>
<b>Loan status</b><br>   
The loan status can apply for loans or loan <a href="#component"><u>components</u></a>.
It can be one of the following:
<ul>
	<li><b>Open:</b> The loan is open what means it has not been repaid but
	did not reach the expire date. There are still payment obligations for the
	member.
	<li><b>Expired:</b> The loan repayment date has been passed but it hasn't
	been paid back
	<li><b>Closed / repaid:</b> The loan has been repaid or discarded and is
	administratively closed. The member doesn't have payment obligations anymore.
	<li><b>Discarded</b> A loan component is normally discarded if the loan
	has been paid by other means, for example goods or conventional money. A
	discarded loan component can be considered as closed.<br>
	<li><b>In process:</b> When a loan reaches the expire date an
	administrator can change the status to &quot;in process&quot;. Mostly because
	of the re-negotiating of the loan. After this status an administrator can
	either put the loan in the &quot;recoverd&quot; or &quot;unrecoverable&quot;
	status (see next) This status can only be reached from an expired loan. It
	means that the loan is expired, but that the parties are negotiating on what to
	do with it. <br>
	<li><b>Recovered:</b> This is an &quot;end&quot;status after a &quot;in
	process&quot; status. It means that the loan has been recovered.
	<li><b>Unrecoverable:</b> This status can only be reached from the
	&quot;in process&quot; status. Strictly seen, it means that this loan is still
	due, but all parties consider it as not payable by the member, and don't expect
	any payment anymore. The loan is in sort of a &quot;frozen&quot; state.
	<li><b>Pending authorization:</b> The payment of the loan needs to
	geautoriseerd. Once the loan payment is autorized the transfer will be done
	automatically.
	<li><b>Autorization denied:</b> The payment of the loan has been denied.
	This means the loan is administratively canceled.
</ul>

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