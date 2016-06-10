<div style="page-break-after: always;">
<br><br>The bookkeeping function can be used to link
(mirror) &quot;external accounts&quot; like bank accounts or accounts from other
currency systems with accounts in a Cyclos system.
<br><br>Because the bookkeeping function allows to have a complete mirror of the
external account (and its transactions) locally in Cyclos it is possible to
administratively &quot;link&quot; Cyclos transactions with the imported
transactions. This means both transactions will have a reference to each other
and receive an extra status. This status shows up as a specific icon in the
transaction summary windows. It is also possible to search and print results of
transaction lists per status.
<br><br>A typical use of the bookkeeping module is to control the balances of
systems where the internal (cyclos) Units are backed with conventional money at
a bank account. In such a case the transactions in the bank account have a
direct relation with specific Unit transactions in Cyclos. For example, a
deposit (incoming payment) at the Bank could be related to a system-to-member
payment that can be the &quot;buying&quot; of Cyclos Units or the repayment of a
loan in Cyclos. And, the other way around, a payment from the bank account
(debit) to a member bank account can be related to a member-to-system (e.g.
conversion) payment in Cyclos. The concilation function will help to control the
&quot;backing&quot; of the Cyclos Units. For example, &quot;the conciliation
balance&quot; of a system where the internal Units are 100% backed by
conventional currency will be one to one.
<br><br>It is possible to generate automatically specific types of (Cyclos)
transactions on imported transactions. For example a incoming payment at an
external bank account can generate a Cyclos system-to-member payment.<br>
This is explained in detail in the import configuration section of the
bookkeeping module.
<br><br>The controlling of the &quot;backing&quot; is just an example of a use of
the bookkeeping module. The module can be implemented for other cases where an
external transactions need to generate local transactions or loan status changes
in a local system.
<br><br>
<i>Where to find it.</i>
<br>
The bookkeeping module can be found at the &quot;Menu: Bookkeeping&quot; (for
this menu item to show up permissions need to be set for the administration
group)

<br><br><i>How to get it working.</i><br>
For the module to be visible you will need the correct <a
	href="${pagePrefix}groups#manage_group_permissions_admin_system"><u>admin
permissions</u></a> in block &quot;External Accounts (bookkeeping)&quot;.
<br><br>Before being able to import new external transactions in cyclos, you must
perform the following steps:
<ol>
	<li>Check that the <a
		href="${pagePrefix}account_management#transaction_types"><u>
	transactions types</u></a> that you will need for these transactions exist
	<li>Create an external account via &quot;Menu: Manage External Accounts >
	New external account&quot;.
	<li><b>file mapping:</b> Create a definition of the import file via the
	window below the one in which you created the external account.
	<li><b>field mapping:</b> Tell cyclos how it should treat all the fields
	in this file in the next window.
	<li><b>payment type mapping:</b> tell cyclos how values in the field
	defining payment types should map to payment types. This is done in the next
	window.
</ol>
Only after you performed these steps (only once is enough) you will be able to
import new external transactions via a transaction file you received from your
bank account.
<a href="#using"><u>Click here</u></a>
to get an overview on how to import the new transactions from the file.
<hr>


<A NAME="external_accounts_list"></A>
<h3>List external accounts</h3>
This windows shows a list with all external accounts.
<ul>
	<li><img border="0" src="${images}/edit.gif" width="16" height="16">
	Click the icon to modify the external accounts.
	<li><img border="0" src="${images}/delete.gif" width="16" height="16">
	Click the icon if you want to delete the external accounts.
</ul>
You can add a new external account via the &quot;new external account&quot;
button below.
<hr class="help">


<A NAME="new_edit_external_account"></A>
<h3>New / edit external account</h3>
In this page you can define an external account and the internal system and
member accounts that will be related to it.<br>
Transactions in external accounts will be mostly related to transactions that
are involved in Unit creation (e.g. loan) and destruction (e.g conversion).
Therefore the system account related to an external account would normally 
be of the type &quot;unlimited&quot;. More information on this can be found at the 
<a href="${pagePrefix}account_management#account_details"><u>
help file</u></a> of the &quot;account creation&quot;. 
<hr class="help">


<A NAME="edit_file_mapping"></A>
<h3>Edit file mappings</h3>
In order to import the transaction information for an external account you will
need to define the mapping between the fields of the external account and the
fields in Cyclos. The first time after creating an external account you will be
asked to create a new file mapping. The following mappings are available.
<ul>
	<li><b>Type: </b>CSV or custom (class file)<br>
	Normally transaction files contain plain text where the values are separated by
	a separator character. In this case you can use the he <a
		href="${pagePrefix}loans#csv"> <u>CSV</u></a> option.<br>
	In the case the transaction file has a more complex format, for example xml, it
	is possible to program a customized class that can handle the format. This
	documentation is not intended to go in the technical details but you will need
	to do the following:
	<ol>
		<li>create a Java class that implements the Cyclos interface
		TransactionFileImport (in the utils package)
		<li>put it in the classpath and in the server location WEB-INF/classes or
		in a shared lib directory if it is packaged in a jar.
	</ol>
	<li><b>Column separator: </b><br>
	The separation character used in the file, mostly a comma &quot;,&quot;.
	<li><b>Header lines: </b><br>
	The number of header lines (that do not contain the actual values). These lines
	will be ignored.
	<li><b>Number format: </b><br>
	This has two possible options:
	<ul>
		<li><b>fixed position:</b> In some cases the format of the amount in
		transaction files has no separator but the separator is fixed and calculated
		from the right. For example an amount of 50000 with Decimal places 2 will
		result in 500 (or 500,00 with comma).<br>
		If you choose this option, the field to the right of it will be called
		&quot;Decimal places&quot;, and here you'd usually enter the &quot;2&quot;.
		<li><b>with separator:</b> Usually a separator is used; you can define
		this in the field &quot;Decimal separator&quot; (at the right). This is mostly
		a dot &quot;.&quot; or a comma.
	</ul>
	<li><b>Negative character: </b><br>
	In some cases the format of the amount field in transaction files is never
	negative but for negative numbers there is a special character in a separate
	column. This can be for example a &quot;-&quot; or a D (debit). For example the
	amount column field |-500,00| can be the same as the colums |D|500,00| (where
	the | is the column separator). In such a case you enter this character in this
	field.<br>
	Normally the amount has the negative character in the same field and the
	negative column is not needed.
	<li><b>String quote: </b><br>
	Transaction files and CSV files often contain strings for text (in order to
	prevent line and column breaking). Here you can define the String quotes
	(mostly ").
	<li><b>Date format: </b><br>
	Here you can define the date format. You can used y for year, M for month (must
	be capital) and d for day. You can use any separator between the values like
	dd/mm/yy or yyyy-MM-dd etc.
</ul>
Click &quot;Sumbit&quot; to submit the file mappings. Clicking the
&quot;Reset&quot; button will remove all file mappings and you will be prompted
to create a new one. The payment types (window below) won't be removed when
resetting the file mapping.
<hr class="help">


<A NAME="file_mapping_fields_list"></A>
<h3>File mapping fields</h3>
Once the file mapping format is defined (window above) you can map the fields of
the transaction file to Cyclos fields. All the rows in the list will represent
one row in the transaction file and every field is one column.
<br><br>When you see this window for the first time there will be no fields in
the list. In order to insert a new field mapping you click on &quot;insert new
field mapping&quot;. You will have to repeat this for all the fields in the file
with transactions.<br>
The first field (with Order number 1) will be the first (left) column in the
transaction file. Make sure the order of the fields correspond with the column
order.
<br><br>Once there are items in the list you can do several things.
<ul>
	<li>You can change the field order by clicking the &quot;Change field
	mappings order&quot; button.
	<li><img border="0" src="${images}/edit.gif" width="16" height="16">&nbsp;
	clicking this icon lets you change the entry for this field.
	<li><img border="0" src="${images}/delete.gif" width="16" height="16">&nbsp;clicking
	this icon will delete the field from the list.
</ul>
<hr class="help">


<A NAME="edit_field_mapping"></A>
<h3>New/Edit Field Mapping</h3>
Here you can define the mapping per field. You can enter a name, which is just a
text label that will be showed in the list. It does not have any function. In
the Field drop down combo you can select the following options.
<ul>
	<li><b>Ignored: </b>
	If the file contains extra columns that do not need to be mapped to cyclos
	fields you will still have to create a mapping for it, but you set the
	&quot;Ignore&quot; option.
	<li><b>MemberId: </b>
	This option can be used to map to the interal Id number used in Cyclos. It is
	unlikely that a transaction file will contain the Cyclos internal (member) ID
	but we put the option to offer a complete set of options.
	<li><b>Membername: </b>
	If the transaction file specifies a (Cyclos) username you can define the column
	with this option.
	<li><b>Member custom field: </b>
	If the transaction file does not specify a username it is possible to map a
	custom (profile) field in order to identify the a user. This must be a unique
	custom field. For example a fiscal number.
	<li><b>Payment type: </b>
	If you want to import transactions and generate payments from the transaction
	file you must define a payment type. Mostly transactions in the transaction
	file will be of more than one payment type. For example deposit, loan repayment
	etc. The type of payment is specified with a code in a separate column. With
	this option you define what column represents the payment type. The possible
	values of the different payment types can be defined in the payment types
	function below this window.
	<li><b>Payment date: </b>
	With this option you can specify the column that contains the payment date of
	the transaction.
	<li><b>Payment amount: </b> With this option you can specify the column
	that contains the payment amount of the transaction.
	<li><b>Negative amount indicator: </b> It can happens that the Payment
	amount does not indicate if it is a negative or positive amount but that this
	is specified in a separate column. With this field you can define if the
	Payment amount field is negative or not. It can be a sign (e.g. &quot;-&quot;)
	or a word (e.g. &quot;D&quot; or &quot;debit&quot;)
</ul>
Click &quot;Submit&quot; to save.<br><br>
<b>Note:</b> You can only use a field only once. That
means that when you added a field type (e.g. payment date) you cannot add it
again (it won't show up as an option). An exception is the &quot;ignored&quot; field
because there can be various columns you might want to ignore (not import).<br>
Because the MemberID, Membername and Member custom fields are all used to define the 
member you can only use one of those three field types in your file mapping.
<hr class="help">


<a name="set_field_mappings_order"></a>
<h3>Set field mappings order</h3>
In this window you can change the order of the field mappings. The mappings for
fields you defined need exactly to be the same order as the order of fields in
the transactions file you want to import.
<br><br>The window works in a very simple way. You can just click on a field
name, and drag it with your mouse to the place where you want to have it. When
done, you can click the &quot;submit&quot; button to save the results.
<hr class="help">


<A NAME="external_transfer_type_list"></A>
<h3>Payment type (action mapping)</h3>
This window shows a list with possible types of payments that the transaction
file can contain. In order to add payment types there must exist a field mapping
with the &quot;Payment type&quot; field. With this window, you tell cyclos which 
code values in this field map to real payment types in cyclos. Remember that you
must map every possible value which may exist in this field; codes which
don't map to any sensible transaction type are mapped as &quot;none&quot;.
<ul>
	<li><img border="0" src="${images}/edit.gif" width="16" height="16">
	Click the icon to modify a payment type mapping.
	<li><img border="0" src="${images}/delete.gif" width="16" height="16">
	Click the icon if you want to delete a payment type mapping.
</ul>
To add a payment type mapping click the &quot;submit&quot; button labeled 
&quot;Insert new payment type&quot;.
<hr class="help">


<A NAME="edit_external_transfer_type"></A>
<h3>Insert / Modify transfer types</h3>
In this page you can define how cyclos should interpret a code in the 
payment type field of the transactions file from your bank. Here you define
to which transaction type a specific code should map. You should repeat this
procedure for every possible code in this field. 
<br>
The name and description are for internal use and do not have a function. The
code is one of the possible values of the field containing the 
<a href="#edit_field_mapping"> <u>payment type field mapping</u></a>
. See the list below for an example.
<br>
The following actions are possible.
<ul>
	<li><b>None: </b>
	Here you just say that a specific code should not perform any action. The transaction
	will appear at your external account overview, so that the balance is correct, but
	it will not generate any payment in cyclos.
	<li><b>Generate payment to member: </b>
	Here an example can help. If a payment type column in the in the transaction
	file would have various possible values (codes) and one of them was
	&quot;DEP1&quot; which indicates the transaction is a deposit at the bank
	account. If you want this particular payment type to generate automatically a
	payment from a system account to the member this option should be selected.
	<li><b>Generate payment to system: </b>
	This would be a rare case but is available to offer any possible option. It
	means that an incoming transaction in a external account would generate a
	payment from member to system in Cyclos.<br>
	An example could be that you would want to import the bookkeeping in the
	national currency into cyclos, in order to check if members pay their
	membership fee in national currency in time. In this case you would need to
	create accounts for the organization in national currency (to reflect the bank
	account of the system), plus additional accounts for members in the national
	currency. This would allow you to reflect bank account transactions by these
	created accounts, and check if members paid their membership fees (besides
	that, you could also perform statistics on it).
	<li><b>Discard loan: </b>
	Some systems have <a href="${pagePrefix}loans"><u>loans</u></a> in Cyclos that
	can be paid externally (e.g. with conventional money). When the loan is repaid
	externally you don't want the status in Cyclos to change to repaid, because that 
	status is reserved for repayment with internal cyclos currency. In such a case, the
	new status would be &quot;Discarded&quot;. 
	When a loan repayment is made the loan status can be changed automatically
	according to these payment types.
	<li><b>Conciliate: </b>
	The conciliate status is a status given to an internal Cyclos payment
	specifying that it has a external &quot;contra &quot; payment and has been
	administratively confirmed. It is used to control the balance of a set of
	Cyclos accounts (system and a member) and the balance with an external account.
</ul>
<hr class="help">


<a name="using"></a>
<h2>Using the external account</h2>
You can import external transactions into the external account, and afterwards
process them. You can also search the external account for transactions.

<i>Where to find it?</i>
<br>
The external account overview can be found via the &quot;menu: Bookkeeping >
account overview&quot;.
<br><br><i>How to get it working?</i><br>
Once you have configured the mapping for an external file (see top of file), you
can start to import transactions from a file from your bank. You should perform
the following actions to get this working:
<ol>
	<li>Go to the &quot;Menu: Bookkeeping > Account Overview&quot;, and click
	the <img border="0" src="${images}/import.gif" width="16" height="16">&nbsp;import
	icon to go to the import module.
	<li>import the file. If this results in errors, correct them, until your
	import is successful.
	<li>Go to the overview of the imported transactions. This can be done via
	the Search results window (above the window with the import button), and
	clicking the <img border="0" src="${images}/preview.gif" width="16" height="16">&nbsp;
	view icon.
	<li>remove or restore incomplete transactions; verify pending transactions
	and turn them into the &quot;checked&quot; status. All of this can be done via
	the <img border="0" src="${images}/preview.gif" width="16" height="16">&nbsp;view
	icon of each transaction.
	<li>process the checked transactions via the &quot;process&quot; button
	above this transaction overview.
</ol>
<hr>


<A NAME="external_accounts_overview"></A>
<h3>External accounts overview</h3>
This page lists all the external account configured for the system. The name
column will show the name of the external account and the Account balance the
sum of all imported transactions.
<ul>
	<li><img border="0" src="${images}/import.gif" width="16" height="16">
	Click the arrow icon to enter the import function for the account, allowing you
	to import the external transactions from a file. You can also get an
	overview of all past imports.
	<li><img border="0" src="${images}/preview.gif" width="16" height="16">
	Click the magnifying glass icon to view and process the transactions that have
	already been imported.
</ul>
<hr class="help">


<A NAME="external_transfer_import_new"></A>
<h3>External transfer import new</h3>
In this window you can import new external transactions. Just choose the file and
click &quot;submit&quot;. If the file could not be read due to syntax errors, 
there will appear an error report specifying the line and field causing the error. 
<hr class="help">


<A NAME="external_transfer_import_list"></A>
<h3>External transfer import list</h3>
In this window you can search for file imports by period. You may use the date
pickers (
<img border="0" src="${images}/calendar.gif" width="16" height="16">
) for entering the dates.
<hr class="help">


<A NAME="external_transfer_import_result"></A>
<h3>External transfer import result</h3>
This window shows a list with all imported transaction files.
<ul>
	<li><img border="0" src="${images}/preview.gif" width="16" height="16">
	Click the magnifying glass icon to view and process the imported transactions.<br>
	<li><img border="0" src="${images}/delete.gif" width="16" height="16">
	Click the delete icon to remove the imported transactions again. <br>
	Note: You cannot delete imports that have transactions with the status
	&quot;checked&quot; or &quot;processed&quot;.
</ul>
<hr class="help">


<A NAME="external_account_history"></A>
<h3>External account history</h3>
In this window you can search for imported transactions. The function searches
in all imported files. 
A search with no options selected will show all imported
transacations for the given external account.
The following search options are available:
<ul>
	<li><b>Type: </b>
	With this option you can search per payment type (defined in the import
	configuration). 
	<li><b>Status: </b>
	<ul>
		An imported transaction can have one of the following status.
		<li><b>Pending: </b>
		The transaction has been imported but not verified yet. It did not effect the
		&quot;Imported balance&quot; yet (at the main page of the bookkeeping
		function) and did generated any actions.<br>
		Note: It is also possible to delete an imported transaction that has the
		pending status.
		<li><b>Checked: </b>
		This are transactions that have been verified and put in the
		&quot;checked&quot; <a href="#external_transfers_history_result"><u>status</u></a>. 
		<li><b>Processed: </b>
		This are transactions that have been processed after they had been put in the
		checked status.
	</ul>
	<li><b>Login name / Member: </b>
	Search for transactions by specific member.
	<li><b>From Amount / To Amount: </b>
	Search by amount range
	<li><b>From date / To Date:</b>
	Search by period.
</ul>
Under the window there are actions (showing three buttons); These are, from left to right:
<ul>
	<li><b>To import List:</b> This will take you to the overview with import files, 
	where you can also import a new transaction file.
	<li><b>Process payments: </b>
	This will open a process window where you will have the option to process one
	or more of the transactions. <br>
	The process can either be
	<ul>
		<li>to conciliate an outgoing payment (form the external
	account)
		<li>to generate an internal Cyclos payment related to an incoming payment at
	the bank account
		<li>to discard a (Cyclos) loan payment related to an incoming
	payment at the external account.
	</ul>
	The process button applies to all transactions in the list search result
	(indifferent if they are selected or not). Note that a transaction can only be processed
	if its status is &quot;checked&quot;.
	<li><b>New Payment: </b>
	It is possible to insert a transaction manually in case it was not imported
	correctly.
</ul>
More information on these functions can be found at the function windows self.
<hr class="help">


<a name="status"></a>
<h3>Transaction status</h3>
Each imported transaction has a status. The status can have the following values:
<ul>
	<li><b>Pending</b> <img border="0" src="${images}/pending.gif" width="16" height="16">:
	The transaction has been imported but has not any effect. It is pending for
	further actions.<br>
	This status will also show up in the system account overview if the transaction
	type is checked as &quot;Is conciliable&quot;. This way you can track the
	conciliation status directly from the account over view page.<br>
	Only transactions with the pending status can be deleted.
	<li><b>Checked</b> <img border="0" src="${images}/checked.gif" width="16" height="16">:
	The transaction has been verified and &quot;checked&quot;. This means that it
	will enter in the external account balance.<br>
	It is possible to put a transaction with the status &quot;checked&quot; back to
	&quot;pending&quot;.
	<li><b>Incomplete</b> <img border="0" src="${images}/incomplete.gif" width="16" height="16">:
	The transaction has been imported but one or more fields could not be mapped
	correctly. For example if a member in the imported transaction does not
	exist in Cyclos.<br>
	<li><b>Conciliated</b> <img border="0" src="${images}/conciliated.gif" width="16" height="16">:
	The transaction has been processed. This means that it is part of the external
	account balance and it causes an action in Cyclos (e.g. an internal Cyclos
	payment or loan discard).<br>
	This status will also show up in the system account overview if the transaction
	type is checked as &quot;Is conciliable&quot;. This way you can track the
	conciliation status directly from the account over view page.<br>
	Processed transactions cannot be given another status nor they can be
	deleted.
</ul>
<hr class="help">


<A NAME="external_transfers_history_result"></A>
<h3>External transfers</h3>
This window shows the result of the search window above. By default it shows all
imported transactions. The Type column will show a status icon in front. 
<a href="#status"><u>Click here</u></a> for an overview of possible values for the status.  
The amount and date columns are self explanatory.
<br><br>
The following actions are possible on each transaction.
<ul>
	<li><img border="0" src="${images}/preview.gif" width="16" height="16">
	You can enter concicliated and processed payments selecting the preview icon.
	Those payments cannot be changed. It is possible however to set a
	checked transaction back to a pending one.
	<li><img border="0" src="${images}/edit.gif" width="16" height="16">
	You can modify the transaction data from a transaction in the pending status by
	selecting the edit icon.<br>
	<li><img border="0" src="${images}/delete.gif" width="16" height="16">
	Click the delete icon to delete the transaction.<br>
</ul>
<hr class="help">


<A NAME="external_transfers_history_summary"></A>
<h3>External transfers history summary</h3>
This window shows an overview and reports of the total of listed transactions.
<br><br>Note: Only the transactions that are the result of the search of the top
window will be counted. A search with no options selected will show all imported
transacations for the given external account.
<hr class="help">


<A NAME="new_external_transfer"></A>
<h3>New external transfer</h3>
It is possible to add a transaction manually. Normally this should not be
necessary as the transaction import can be configured to import all transactions
correctly. But in the (rare) case that it is needed at can be done in this
window.
<br>
The input fields are self explanatory.
<hr class="help">


<A NAME="edit_external_transfer"></A>
<h3>View & Modify external transfer</h3>
In this page you can view the details of the imported transfer. If the transfer
is in the pending <a href="#status"><u>status</u></a> you can modify its properties.<br>
The input fields are self explanatory.
<hr class="help">


<A NAME="external_transfer_process"></A>
<h3>External transfer process</h3>
In this page you can process payments. Note that a payment has to be in
<a href="#status"><u>&quot;checked&quot; status</u></a> or else it won't show up 
in the list of transactions which are ready for processing.
<br><br>The window gives an overview of the processable payments. In each item, the above
row shows the original line as it is read from the transaction file, and the below row shows
the transaction as it will be processed after you clicked submit. Select the transactions
you want to process by checking the checkbox in the first column. This will also allow
you to modify the date or amount if these appear to be incorrect.
<br>
Clicking &quot;submit&quot; will process the transactions selected. 
<br><br>
There are three types of processing.
<ul>
	<li><b>Conciliate : </b>
	This option is common for systems where the internal (Cyclos) units are backed
	externally (e.g. conventional money at a bank account). Conciliating a set of
	transactions (external transaction and Cyclos transaction) means that both
	transactions will be linked because they are administratively related. This
	will show up with the <img border="0" src="${images}/conciliated.gif"
		width="16" height="16"> in front of the payment in the account overview
	of the external account function and the system account overview function. In
	the search windows of these functions there will be an option to search by the
	conciliations status. A In order to be able to conciliate transactions with the
	transaction type in Cyclos that can be concilated must have the option &quot;Is
	conciliable&quot; checked in the transaction type configuration. <br>
	<li><b>Generate payment : </b><br>
	An incoming (external) payment (positive amount only) can be configured to
	generate a Cyclos payment (system to member or the other way around). To
	process a payment it will need to be selected. Once the transaction is selected
	it is possible to modify the amount and the date but this will only be needed
	in rare cases.<br>
	A generated payment will have automatically the conciliate status.
	<li><b>Discard loan repayment : </b><br>
	A loan in Cyclos will enter in a administrative &quot;paid&quot; status when it
	is repaid. In systems where Cyclos loans can be repaid externally it will be
	possible to give those loans a status indicating that they are repaid. This
	status is called &quot;Discarded&quot;. It is possible to have an external
	incoming payment generating a Discard loan payment.<br>
	There a specific type for in the external account configuration. When such a
	external transaction is processed Cyclos will search for open loan payments
	with the same amount and date and will present the closest match. If there are
	more loan payments that match the will be all showed and the admin can chose
	the correct one. After A generated loan discard payment conciliate status in
	the external account overview.
</ul>
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
