<div style="page-break-after: always;">
<a name="top"></a>
<br><br>
Cyclos allows you to &quot;send the bill&quot;
to someone by means of an &quot;invoice&quot;. An invoice is the official
request to pay for delivered goods or services.<br>
There are many features for initiating and managing invoices
between members and administration. Also, members can send invoices to
each other.<br>
Invoices can be rejected or accepted by the receiver. The sender can
also cancel an invoice. The other party will always receive a 
notification regarding those actions. Members however can never reject
invoices coming from system accounts.
<br><br>
<i>Where to find it.</i><br> 
You can access invoices in the following ways:
<ul>
	<span class="admin">
		<li><b>Menu: accounts > member invoice:</b> allows you to send an invoice
		from the organization (from a system account) to a member.</li>
		<li><b>Menu: accounts > manage invoices:</b> allows you to manage incoming
		and outgoing invoices for system accounts.</li>
	</span>
	<span class="admin broker">
		<li><b>Member <a href="${pagePrefix}profiles"><u>profile</u></a> >
		Accounts: view invoices</b> allows you to view the invoices of a specific member.
		</li>
	</span>
	<span class="member">
		<li><b>Menu: Account > System invoice: </b> allows you to send invoices to
		the organization.
		</li>
		<li><b>Menu: Account > Member invoice:</b> allows you to send invoices to
		another member.</li>
		<li><b>Menu: Account > invoices:</b> lets you view and manage all your
		incoming and outgoing invoices.
		</li>
		<li><b>Member <a href="${pagePrefix}profiles"><u>profile</u></a> >
		send invoice:</b> lets you send an invoice directly from a member's profile.
		</li>
	</span>
</ul>
<hr>

<span class="member">
<A NAME="send_invoice_member_to_system"></A>
<h3>Send invoice member to system</h3>
It is possible for a member to send an <a href="#top"><u>invoice</u></a> to the system (organization). The
organization administrator will receive the invoice and will either accept or
reject it. When an administrator accepts or rejects an invoice you will receive
a notification in your personal news message section informing you about the
action.<br>
To send the invoice, fill in the fields and click the &quot;submit&quot; button.
<hr class="help">
</span>

<A NAME="send_invoice_system_to_member"></A>
<A NAME="send_invoice_member_to_member"></A>
<A NAME="send_invoice_member_to_member_select"></A>
<h3>Send invoice to member</h3>
Here you can send an invoice to a member. The member will receive an
&quot;Incoming Invoice&quot;, and can then pay you by clicking
&quot;Accept&quot;. If the invoice has been accepted it becomes just a normal
payment like any other payments, and it will show up as such in the transaction
history.
<br>
<span class="member"> On the other hand, the member can also choose to
reject your invoice, which means the payment is canceled. The sender, will get a
notification that the Invoice has been rejected.
<br><br>In the &quot;Menu Account > Invoices&quot; you can view and manage
Incoming and Outgoing Invoices.
</span>
<span class="admin">An overview of all the system-to-member and
member-to-system invoices can be seen in the Menu: &quot;Accounts > Manage
Invoices&quot; section </span>
<br><br>You will have to enter the following fields:
<ul>
	<li><b>(Login) name:</b> You can select the member that should receive the
	invoice by either filling in the &quot;name&quot; or &quot;login name&quot;
	field. If you write a part of the name it will be auto-completed. (This field
	will not be visible if it is already clear to which member you send the
	invoice.)
	<li><b>Amount:</b> if multiple Currencies are available in your system,
	you'd have to select the currency here in the drop down after the amount. If
	only one currency is available, this drop down will not be shown.
	<li><b>Scheduling:</b> This option will appear if it is activated by the
	administration. With the scheduling option you allow that the receiver can pay
	the invoice with scheduled payments (installments). The receiver of the invoice
	will be informed about the installments dates and amounts and when the receiver
	accepts the invoice the scheduled payments will show up in his/her (outgoing)
	scheduled payments.
	<li><b>Payment type:</b> After this you will have to choose the payment
	type. This is only if more than one appropriate payment type exists. If only
	one type exists, this field will not be shown.
	<li><b>Description:</b> when the invoice is accepted, this description
	will show up as the transaction description in the transaction overview.
</ul>
After completing the form, click &quot;submit&quot; to send the invoice. After
submitting you will be asked for a confirmation.
<hr class="help">

<span class="admin">
<A NAME="manage_invoices_by_admin"></A>
<h3>System invoices</h3>
<br><br>This window gives you the option to get an overview of the <a href="#top"><u>invoices
</u></a> sent from System accounts to Member accounts (outgoing invoices) and invoices from Member
accounts to System accounts (incoming invoices).
<br><br>You can filter on the following fields:
<ul>
	<li><b><a href="#status"><u>status</u></a></b>
	<li><b>type:</b> &quot;incoming&quot; or &quot;outgoing&quot;
	<li><b>a time period</b>
	<li><b>name:</b> login name or real member name
	<li><b>description</b>
	<li><b><a href="${pagePrefix}account_management#transaction_types"><u>payment
	type</u></a>:</b> this only works for outgoing invoices. For incoming invoices the payment
	type is not defined. An administrator will have to choose the payment type when
	accepting the invoice (see <a href="#incoming_invoice_detail_by_admin"><u>invoice details</u></a>).
</ul>
After you filled in the form, click &quot;search&quot; and the results will show
up in the <a href="#invoices_result_by_admin"><u>window below</u></a>.
<br><br>Note: Searching for invoices other than with a status &quot;open&quot;
will show historic invoices that have been already been canceled, rejected or
accepted.
<hr class="help">
</span>

<span class="member">
<A NAME="manage_invoices_by_member"></A>
<h3>My Invoices</h3>
With the &quot;type&quot; option in this window you can view a list with either
&quot;incoming&quot; or &quot;outgoing&quot; <a href="#top"><u>invoices</u></a>.
The results will show up in the <a href="#invoices_result_by_member"><u>
window below</u></a>.
<br><br>In the advanced option you refine your search using some or all of the
fields in the form. Most fields are straightforward enough. &quot;Login
name&quot; is the name with which the member logs in, and &quot;member&quot; is
the real name of the member.<br>
The <a href="#status"><u>status</u></a> can have several different values; click
the link to learn more about this.
<hr class="help">
</span>

<span class="admin broker">
<A NAME="manage_member_invoices_by_admin"></A>
<A NAME="manage_member_invoices_by_broker"></A>
<h3>Invoices of member</h3>
<br><br>This window gives you the option to get an overview of the member's <a href="#top"><u>invoices
</u></a>, either incoming or outgoing.
<br><br>You can filter on the following fields:
<ul>
	<li><b><a href="#status"><u>status</u></a></b>
	<li><b>type:</b> &quot;incoming&quot; or &quot;outgoing&quot;
	<li><b>a time period</b>
	<li><b>name:</b> login name or real member name
	<li><b>description</b>
	<li><b>payment type</u></a></b>
</ul>
After you filled in the form, click &quot;search&quot; and the results will show
up in the manage invoices page.
<br><br>Note: Searching for invoices other than with a status &quot;open&quot;
will show historic invoices that have been already been canceled, rejected or
accepted.
<hr class="help">
</span>

<a name="status"></a>
<h3>Invoice status</h3>
The &quot;status&quot; of an <a href="#top"><u>invoice</u></a> can be one of the following:
<ul>
	<li><b>open:</b> invoices send but not yet paid or rejected by the receiver.
	<li><b>accepted:</b> invoices paid by the receiver.
	<li><b>denied:</b> invoices refused (rejected) by the receiver.
	<li><b>canceled:</b> invoices which you canceled yourself.
	<li><b>expired:</b> invoices to which the receiver hasn't given
	any reaction (nor paid, nor rejected) and which have passed the
	expiration date.
</ul>
<hr class="help">

<A NAME="accept_invoice"></A>
<h3>Accept invoice</h3>
This is a confirmation screen after you clicked the &quot;accept&quot; button
of an <a href="#top"><u>invoice</u></a>. <br>
After this, there is no way back: when you click &quot;submit&quot; the amount
will be taken from the account, and the bill will be paid.   
<hr class="help">

<A NAME="invoices_result_by_admin"></A>
<A NAME="invoices_result_by_member"></A>
<h3>Search results</h3>
This page shows the <a href="#top"><u>invoices</u></a> search result list. Clicking
the view icon (<img border="0" src="${images}/view.gif" width="16" height="16">) will
open the details of the invoice.
<br>
If the invoice is &quot;open&quot; you can
perform an action according to the type of invoice (accept, reject or cancel)
<hr class="help">

<A NAME="invoice_details"></A>
<A NAME="outgoing_invoice_detail_by_admin"></A>
<A NAME="incoming_invoice_detail_by_admin"></A>
<A NAME="outgoing_invoice_detail_by_member"></A>
<A NAME="incoming_invoice_detail_by_member"></A>
<h3>Invoice details</h3>
This window shows the details of the invoice. Depending on the permissions and
of the type of the invoice, you can take one of the following actions.
<ul>
	<li><b>Accept:</b> <span class="member">If you are</span> <span
		class="admin broker">If this member is</span> the receiver of this invoice,
	you can accept it. If you do so, the amount will be taken of <span
		class="admin broker">the member's</span><span class="member">your</span>
	account, and transfered to the account of the sender of the invoice. In normal
	language: you have paid the bill. If you click this button, a confirmation
	screen will follow, asking you to confirm.
	<li><b>Deny:</b> <span class="member">If you are</span> <span
		class="admin broker">If this member is</span> the receiver of this invoice,
	you can also deny (reject) this invoice. This means that you refuse to pay this
	amount. The payment will not take place, and the other party will receive
	notification of this.<br>
	It is impossible to deny an invoice coming from the system/organization.
	<li><b>Cancel:</b> If <span class="admin broker">this member is</span> <span
		class="member">you are</span> the sender of this invoice you can cancel it at
	any time before the receiver has accepted it. If you cancel an invoice, the
	other party will receive a notification in their personal message window that
	the invoice has been canceled.
</ul>
For incoming invoices, you might have to specify the payment type first, if
there is more than one possible payment type for this payment. In such a case, a
&quot;payment type&quot; drop down is visible, in which you should make a
selection. In many cases, this drop down will not be visible.
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