<div style="page-break-after: always;">
<a name="top"></a>
<br><br>Notifications allow users to be alerted
by email or message when specified events in cyclos take place.

<span class="member"> <i>Where to find it?</i><br>
Notifications can be accessed via the &quot;Menu: Preferences >
Notifications&quot;. </span>

<span class="admin"> <i>Where to find it?</i>
<br> Notifications can be accessed via the &quot;Menu:
Personal > e-mail notifications&quot;. 
<br><br>

<i>How to get it working?</i><br> Administrators will always have
the option to configure personal notifications.<br> An admin can
enable the notification function for member (groups) in the <a
href="${pagePrefix}groups#manage_group_permissions_member"><u>member
permissions</u> </a> section (in block &quot;Preferences&quot;).<br> Additional
notification settings can be defined in the <a
href="${pagePrefix}groups#edit_member_group"><u>member group settings</u>
</a> (in block &quot;Notifications&quot;).<br> The content of the
notifications can be change in the <a href="${pagePrefix}translation"><u>translation</u>
</a> module via the &quot;Menu: Translation > notifications&quot;.<br>
</span>
<hr class="help">


<span class="admin"><A NAME="email_notifications"></A>
<h3>E-mail notifications</h3> Select the types of <a href="#top"><u>notifications</u>
</a> you want to receive by selecting the check boxes in the drop downs.
When finished, click &quot;Submit&quot; to save the changes.
<ul>
	<li><b>New registered members:</b> If a member is placed in a
	new group you can be notified. One or more groups can be selected.
	<br> If e-mail confirmation is required in (<a
	href="${pagePrefix}groups#group_registration_settings"><u>member
	group settings</u></a>) you will receive the notification when 
	the member has confirmed the 	registration.
	<li><b>Payments:</b> For each of the available <a
	href="${pagePrefix}account_management#transaction_types"><u>payment types</u>
	</a> you can set a notification, meaning you are notified when a transfer
	with this type takes place.
	<li><b>New payment awaiting authorization:</b> For each of the available 
	<a href="${pagePrefix}account_management#transaction_types"><u>
	authorizable payment types</u>
	</a> you can set a notification, meaning you are notified when a pending
	by authorization transfer with this type takes place.
	<li><b>Guarantees:</b> Select the guarantee types you want to be
	notified when a new <a href="${pagePrefix}guarantees"><u>guarantee</u>
	</a> of those types is created.
	<li><b>Messages:</b> for each <a
	href="${pagePrefix}messages#categories"><u> message category</u>
	</a> you can set the notification.
	<li><b>System Alerts:</b> For each of the <a
	href="${pagePrefix}alerts_logs#system_alerts"><u> System alerts</u>
	</a> you can set notification.
	<li><b>Member Alerts:</b> For each of the <a
	href="${pagePrefix}alerts_logs#member_alerts"><u> Member alerts</u>
	</a> you can set notification.
	<li><b>Application Errors:</b> Select this checkbox to be
	informed of <a href="${pagePrefix}alerts_logs#error_log"><u>
	Application Errors</u> </a> by email.
	<li><b>System Invoices:</b> Select this checkbox to be notified
	by e-mail of the receiving of <a href="${pagePrefix}invoices#top"><u>
	System Invoices</u></a>.
</ul>
</span>


<span class="member"> <A
NAME="notification_preferences"></A>
<h3>Notification preferences</h3> In this page you can define the
notifications you want to receive and you can choose to receive them
by messages internal cyclos message, e-mail or SMS (if enabled by the
administration). However, internal messages from the administration
cannot be disabled.<br> As always, you should first click the
&quot;Change&quot; button in order to make modifications; when done,
click &quot;submit&quot; to save the changes.
<br><br>
The following notifications are available (note that not all
<ul>
	<li><b>Messages from members</b> These are messages send via
	Cyclos, either by members or administrators. This option enables a
	way to receive e-mails (by checking the e-mail option) without
	publishing you e-mail address in Cyclos.
	<li><b>Personal messages from administration</b>
	<li><b>Mailings from administration:</b> These are personal or
	bulk messages sent from the administration.
	<li><b>Access alerts:</b> You will receive notification for
	various attempts to login to your account with a wrong password.
	<li><b>General account events:</b> This are events related to
	the account such as low credit alert.
	<li><b>Brokering events:</b> Notifications about any brokering
	event. <span class="member">These are:</span>
	<ul>
	<span class="member">
		<li>New <a href="${pagePrefix}brokering#commission_contract"><u>commission
		contract</u> </a> inserted
		<li>Commission contract canceled.
		</span>
		<span class="broker">
		<li>Brokering expired
		<li>Brokering removed / broker changed
		<li>Removed from broker group
		<li>Pending payment requires broker authorization
		<li><a href="${pagePrefix}brokering#commission_contract"><u>Commission
		contract</u> </a> accepted
		<li>Commission contract denied
		</span>
	</ul>
	<li><b>Payment events:</b> Events related to payments. This will
	be received payment or events concerning authorization and scheduled payments.
	<li><b>Payment made via external channel:</b> When a payment is
	made externally (e.g. by SMS)
	<li><b>Loan events:</b> This are events related to <a
	href="${pagePrefix}loans"><u>loans</u> </a>. Messages about new
	loans and the expiration of loan payments. This option is only
	showed if the member has loans.
	<li><b>Ad expiration alert:</b> When an advertisement expires</u></a>.
	<li><b>Ad interests notifications:</b> If enabled you will
	receive notifications when a new advertisement matches an <a
	href="${pagePrefix}ads_interest"><u> ad interest</u> </a>.
	<li><b>Invoice events</u> </b></a> Any event about invoices (received,
	accepted, canceled)
	<li><b>Received reference</b> When reference is received or modified.
	<li><b>Transaction feedbacks</b></a> Notifications about quality
	reference over one specific transaction.
	<li><b>Guarantees</b></u></a> deal with the guarantee system in cyclos.
	<li><b>Payment obligations</b></u></a> deal with the guarantee system in
	cyclos.
</ul> </span>


<span class="member admin">
<hr class="help">
<A NAME="receipt_printers"></A>
<h3>Receipt printers</h3>
In some situations Cyclos user want to be able to print receipts after
payments are made. For example by businesses that use Cyclos for
receiving client payments. Commonly Web applications cannot print to
local receipt (ticket) printers. However, this has been made possible
in Cyclos. Transaction receipts can be printed from the transaction
details page and from the POSweb page (directly after a payment was
made). When a payment is part of a bundle of scheduled payments all
scheduled payments will be printed in the receipt. The POSweb page has
also the option to print a list of daily transactions.
<br><br>
Enabling receipt printers involves two main tasks. First the
member that want to use receipt printers will need to make the printers 
available (by adding them to the 
<a href="${pagePrefix}preferences#receipt_printer_search"><u>receipt
printer list</u> </a>. This is normally a one time job and done by
a member that has the technical know-how. When this is done the
printers can be enabled for specific computers by the users (members or
operators) that want to use them.<br>
</span>
	
	
<span class="member"><A NAME="receipt_printer_search"></A>
<hr class="help">
<h3>Receipt printer list</h3> This window shows a list of all your
configured <a href="${pagePrefix}preferences#receipt_printers"><u>receipt
printers</u>)</a>. You can delete and edit the printers by selecting the
corresponding icons. As printers are defined in the local computer
configuration they need to be activated per computer. In the option
&quot;Print on this computer using&quot; you can select a printer if
you want to use the computer you are working on to print transaction
receipts with a local printer. <br> <br> Any printer that
was added to the list (by going &quot;Insert new&quot; option) will be
also available for your operators who can use the printers either at
the main web access channel (preferences - Receipt printers ) or the
POSWeb channel (Print settings at the left top). </span>
<hr class="help">


<span class="member"> <A NAME="receipt_printer_details"></A>
<h3>Receipt printer details</h3> The begin and end of document
commands depend on the specific printer brand / model. They are
useful, for example, to cut the paper or use the buzzer after
printing. To send specific ASCII characters you can use the #NUMBER
varialbe, for example, for ASCII 100, use #100. As an example, for
Epson printers to cut the paper the ASCI characters #27#105 are used.
For more details on specific printer configurations look at the 
<a href="http://project.cyclos.org/wiki/index.php?title=Receipt_printers" target="_blank">Wiki</a>.
<ul>
	<li><b>Display name: </b> The name in Cyclos
	<li><b>Local printer name: </b> The local printer name must be
	the exact name of a printer configured on the operating system.
	<li><b>Begin of document command: </b> Here you can define a local
	printer command such as new line, font size etc. These commands are
	specific for the printer model.<br> Any text (ASCI characters) you
	put in this field will show up at the beginning of the receipt. This
	way you can put a extra &quot;header&quot; text in the print
	receipt. In case if you put a text make sure you put a new line
	command after the text \n<br> (The print receipt will also
	include a system defined header)
    <li><b>End of document command: </b> In this field you can put any
	local printer command. Normally this field is used to define where
	and how to cut the paper after printing. 
	<li><b>Additional message on payment receipts: </b> Here you can put
	any text. It will show up as an additional &quot;footer&quot; (e.g.
	Thanks for buying at..
</ul>
When you want to use a local receipt printer it need to be installed
for your operating system. Here are examples how to do this in Ubuntu
and Windows <br> <br> <b>Ubuntu</b>
<ul>
	<li>Java needs to be installed on the computer
	<li>First install a local driver for the printer (refer to
	printer manufacturer site)
	<li>In Ubuntu, go to: System - Administration - Printing
	<li>Select: &quot;Add&quot; the printer should show up, select
	it and click &quot;forward&quot;
	<li>Now it will search for Drivers, select &quot;Generic&quot;
	from the list (first option)
	<li>Than Select in &quot;Models&quot; the &quot;Raw queue&quot;	option
	<li>Give a short name for the printer, e.g. Epson
	<li>Now click &quot;Apply&quot;, the printer should appear in the
	printer list
	<li>Now open a command prompt and run the following commands:<br>
	cupsctl FileDevice=Yes<br>
	padmin -p Epson -E -v file:/dev/usb/lp0<br>
	(make sure that the name of the printer is the one you have	added)
</ul>
<b>Windows</b>
<ul>
	<li>Java needs to be installed on the computer
	<li>Install a local driver for the printer (refer to manufacturer site)	
	<li>In Control Panel - Printers, add local printer.
	<li>Select in &quot;port&quot; the newly added printer
	<li>Select for manufacturer &quot;Generic&quot; and model &quot;Text
	only&quot;
	<li>Give a short name for the printer, e.g. Epson
	<li>The printer should appear in the printer list
</ul> </span>

</div>
<%--  page-break end --%>

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