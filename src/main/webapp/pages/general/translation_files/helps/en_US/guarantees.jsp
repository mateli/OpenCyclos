<div style="page-break-after: always;">
<a name="guarantees_top"></a>
<br><br>
The guarantee module offers a mechanism where &quot;future&quot;
payments for the purchasing of goods or services can be made available
directly to the providers (sellers) as local purchasing power in a
network that uses Cyclos as payment system. This means that this
&quot;anticipated&quot; local purchasing power is backed by future
(conventional money) payments. Because the seller is paid in local
currency by the organization (upon the delivery of the goods), the
buyer now is in debt to the organization (and not to the seller). An
&quot;Issuer&quot;, usually a financial institution, guarantees these
future payments (from the buyer to the organization). In case the
buyer does not, or cannot pay the Issuer will be responsible that the
payment will be done. Naturally, the issuer will charge costs for
this. But these costs are generally much lower than similar loans in
the financial market. <br>
<br> Issuers have various tools at their disposal (in Cyclos) to
emit and monitor the guarantees. They can evaluate buyers (local
governments, companies or individuals) and define a maximum
&quot;spending&quot; amount and other conditions such as fees and an
expiry date. The guarantee module is very flexible and supports
various uses, defined in the different guarantee types.


<i>Where to find it?</i><br> Guarantee types can be found at:
&quot;Menú: Guarantees> Guarantee types&quot;.<br>
<hr>


<a name="guarantee_models"></a>
<h3>Guarantees Modules</h3>
There are three types of guarantee models:<br>
<ul>
	<li><b>Guarantees with payment obligations:</b> It is very common for larger companies or local
	governments to buy goods and services from local providers and pay at a later moment, for example in
	one, three or six months. A typical example is a supermarket or a municipality paying their local
	providers. The guarantee module with payment obligations offer online tools that can administer and
	monitor these payments. The process starts when an Issuer, normally a financial institution,
	evaluates a company (buyer) and creates a certification in Cyclos.<br> The certification
	defines possible fees and maximum spending amount. Up to this amount a buyer can &quot;publish&quot;
	payment obligations to its providers, usually a payment obligation per outstanding invoice. If the
	provider/seller decides that it want to receive directly in local purchasing power (in stead of
	waiting for the money payments at a later date) it can accept the payment obligations. For the
	seller it means a significant difference because he/she have directly access to the money (in the
	form of local purchasing power). For the buyer the only difference in working with payment
	obligations is that in stead of paying the provider at a delay with conventional currency, it will
	now pay the organization (with the same delay with conventional currency).</li><br>
	<li><b>Guarantees with buyer only:</b> This model is typically used by sellers to offer a
	financing to its customers for a single purchase. Both seller and buyer are part of the process..</li>
	<br>
	<li><b>Guarantees with buyer and seller: </b> This model is typically used by sellers to offer
	a financing to its customers for a single purchase. Both seller and buyer are part of the process.</li>
</ul>
<hr class="help">


<span class="admin">
<a name="guarantee_types_list"></a>
<h3>Guarantee type list</h3> This window lists shows all the configured guarantee types. <br>
</span>
<hr class="help">


<span class="admin">
<a name="edit_guarantee_type"></a>
<h3>Modify / New guarantee</h3> Guarantees have the following fields:
<ul>
	<li><b> Name: </b> Name of the guarantee.<br>
	<li><b> Currency: </b> Currency related to the guarantee.
	<li><b> Model: </b> This defines the type of guarantee as explained in the <a
	href="#guarantee_models"><u>Guarantees models</u> </a> section.
	<li><b> Enabled: </b> This check box allows to enable and disable the guarantee type.
	<li><b> Authorized by: </b> This option defines how has to authorize. Possible options are
	Issuer, Issuer and Admin, None (some options are not available for all models)
	<li><b> Max pending guarantee period:</b> (payment obligation model only). This is the time
	window in which a issuer (or the administrator) can authorize are reject a guarantee. <br> When
	this period is passed the guarantee will pass automatically to the status &quot;without action&quot;<br>
	<li><b> Max period between obligations:</b> (payment obligation model only). This is maximum
	time window that payment obligations can have in order to submit them as &quot;pack&quot; to the
	buyer.
	<li><b> Credit fee: </b> A credit fee will always go to a system account (member-to-system
	transaction type)<br> The fees of a credit fee can be altered by the admin if set as read only,
	but never by the issuer.
	<li><b> Emission fee: </b> The emission fee will always go to the issuer (member-to-member
	transaction type), like the credit fee either to the buyer or the seller can be charged (depending
	on the guarantee type configuration). The fees of an issue fee can be altered by the issuer when it
	is not defined as &quot;read only&quot;. An admin (as authorizer) can always modify a credit fee.
	<li><b> Fees calculations (common rules). </b> Fees case of the &quot;buyer and seller&quot;
	model it is possible to define who is charged (either the buyer or the seller). Fees can be
	configured as a fixed value, a percentage of the guarantee amount, and a annual percentage. In the
	later case the fee will be calculated as with the following formula:<br> Annual fee =
	((1+T/100)^(d/365) - 1) * M<br> Where:
	<ul>
		<li><b>T</b> = Fee %</li>
		<li><b>d</b> = Guarantee validity period in days</li>
		<li><b>M</b> = Amount</li>
	</ul>
	<li><b> Description: </b> An (optional) explanatory description of the guarantee type.
	<li><b> Transaction types: </b> Here are defined the transaction types for the generated
	transactions of the guarantee.
	<li><b>Credit fee: </b>Chose from member-to-system transaction types</li>
	<li><b>Emission fee:</b> Chose from member-to-member transaction type. types</li>
	<li><b>Loan: </b> Chose the loan</li>
	<li><b>Forward: </b> This is the (member-to-member) transaction type that will go from the
	buyer to the seller.</li>
</ul>
<b>Note: </b>There are two ways that guarantees can be created:
<ul>
	<li><b>Manually:</b> by Issuer or Administrator (&quot;buyer and seller&quot; and &quot;buyer
	only&quot; models)
	<li><b>Automatically</b> upon activation of guarantee. (payment obligation model)
</ul> </span>
<hr>


<a name="certifications"></a>
<h2>Certifications</h2>
<br><br>
A certificate is digital contract created by an &quot;issuer&quot;. It
defines a max amount that a specific &quot;buyer&quot; can use to emit
<a href="#payment_obligations"><u>Payment obligations</u> </a> to
sellers. Issuer can have only one &quot;active&quot; certification
with the same buyer and the same currency.<br>

<i>Where to find it?</i><br> Certifications can be found at:
&quot;Menú: Guarantees > Certifications&quot;.<br>
<hr class="help">


<a name="new_certification"></a> <a name="edit_certification"></a>
<h3>New / Modify certification</h3>
A certification has the following entries:
<ul>
	<li><b>Status: </b>(view certification only). This fields lists
	the <a href="#statusC"><u>Status</u> </a>of the certification.</li>
	<li><b>Guarantee type: </b>The guarantee type related to the
	certification</li>
	<li><b>Buyer: </b>The name and login of the Buyer</li>
	<li><b>Validity period: </b> The period in which the
	certification will be active. If the initialization (From) date is in
	the future the certification will only be active beginning that date
	It is not possible to create a certification with a beginning date
	earlier than the current date</li>
	<li><b>Amount:</b> This is the maximum amount up to which a buyer
	can emit payment obligations.</li>
	<li><b>Used amount:</b> The used amount is the total amount
	emitted as payment obligations (per certification). The use amount is
	the sum of all the amounts of the guarantees with the status <i>Accepted</i>
	and <i>Pending</i>. When the payment obligations are being paid off
	by the buyer and administratively closed the certification amount
	will be available again (provided that the validation period is still
	active).
</ul>
<hr class="help">


<a name="certification_logs"></a>
<h3>Certifcation status logs</h3>
This window gives information about the changes of the <a
href="#statusC"><u>Status</u> </a>of the certifications.
<br><br>
<hr class="help">


<a name="statusC"></a>
<h3>Certification status</h3>
Certifications can have various different status depending on the
Guarantee type and the operational flow. Here below are listed all
possible status and every status will describe the possible actions
that will lead to the status.
<br><br>
<b>Status / Actions</b>
<ul>
	<li>Scheduled</li>
	<ul>
		<li>When an issuer creates a new certification with the
		initialization date is at a future date (not today).</li>
		<li>When an issuer activates a certification that was in
		suspended status and has an initialization date is at a future date
		(not today).</li>
	</ul>
	<li>Active</li>
	<ul>
		<li>When an issuer creates a new certification with the
		initialization date in the passed or the current date.</li>
		<li>When an issuer activates a certification that was in
		suspended status and has an initialization date in the passed or the
		current date.</li>
		<li>When a scheduled certificate reaches the current date.</li>
	</ul>
	<li>Suspended</li>
	<ul>
		<li>When an issuer suspends a certification that is in the active,
		or scheduled status.</li>
		<li>When a scheduled certification reaches the current date but
		there is already a active certification with the same issuer,
		currency and buyer.</li>
	</ul>
	<li>Expired</li>
	<ul>
		<li>When a the final date of a certification in active
		status or scheduled status is reached.</li>
	</ul>
	<li>Canceled</li>
		<ul> 
		<li>When an administrator cancels a certification with one
		of the following status: active, suspended or scheduled.</li>
	</ul>
</ul>

<h3>Certification notifications</h3>
The following notifications related to certifications can be generated:<br>
<ul>
	<li>Sent to Buyer when a certification enters one of the
	following status: Active, Suspended, Expired or canceled</li>
	<li>Sent to the Issuer when a certification enters in the status
	expired.</li>
</ul>
<hr class="help">


<a name="certifications_search"></a>
<h3>Search certifications</h3>
This lists shows all the configured <a href="#certifications"><u>Certifications</u>
</a> can be search with the following search options<br>
<ul>
	<li><b>Status</b> one of the <a href="#statusC"><u>status</u>
	</a> of the certification:</li>
	<ul>
		<li>Active</li>
		<li>Canceled</li>
		<li>Suspended</li>
		<li>Expired</li>
		<li>Scheduled</li>
		<br>
	</ul>
		<li><b>Issuer: </b>The name and login of the Issuer</li>
		<li><b>Buyer: </b>The name and login of the Buyer</li>
		<li><b>Activation date</b>Search by activation date within a
		period</li>
		<li><b>Expiration date:</b>Search by Expiration date within a period</li>
	</ul>
<hr class="help">


<a name="certifications_search_results"></a>
<h3>Certification search result</h3>
This page will show a list with the search results for certifications.<br> In order to view the
certification details you can select the magnifying glass icon
<img border="0" src="${images}/view.gif" width="16" height="16"> of the certification in the list
<br>
<hr>


<a name="guarantees"></a>
<h2>Guarantees</h2>
<br><br>
<i>Where to find it?</i><br> Guarantees can be find at Menu:
Guarantees > Guarantees.<br>
<hr class="help">


<a name="guarantee_register"></a>
<h3>Register (new) guarantee</h3>
There are two ways that guarantees can be created:<br>
<ul>
	<li>Manually by Issuer or Administrator (&quot;buyer and
	seller&quot; and &quot;buyer only&quot; models)
	<li>Automatically upon activation of guarantee. (payment
	obligation model)
</ul>
A guarantee can have the following fields:
<ul>
	<li><b>Buyer: </b>The name and login of the Buyer</li>
	<li><b>Issuer: </b>The name and login of the Issuer</li>
	<li><b>Seller: </b>The name and login of the Seller</li>
	<li><b> Identifier:</b> A unique code that identifies the
	guarantee (optional)</li>
	<li><b> Validity: </b> The period in which the guarantee will be
	active</li>
	<li><b> Credit fee: </b> The value of the credit fee <span class="admin">(see <a
	href="#edit_guarantee_type"><u> Guarantee type</u> </a> </span><br>
	This field is editable depending on the guarantee type configuration.
	<li><b>Emission fee: </b> The value of the credit fee <span class="admin">(see <a
	href="#edit_guarantee_type"><u> Guarantee type</u> </a></span> <br>
	This field is editable depending on the guarantee type configuration.</li>
	<br>
</ul>
A guarantee will enter the accepted status at the beginning date of the validity period. When a
guarantee gets the accepted status a loan will be generated and possible fees will be charged.
<br> Only guarantees created manually by an administrator can be deleted. Provided that the are in
pending administrator status and the only status thrue which the have passed is pending Issuer.<br> <br>
<hr class="help">


<a name="guarantees_search"></a>
<h3>Guarantee search</h3>
At this page you can search for guarantees. The following search options are available:
<ul>
	<li><b> Status: </b> The <a href="#statusG"><u>status</u> </a> of
	the guaranteer.</li>
	<li><b>Buyer: </b>The name and login of the Buyer</li>
	<li><b>Issuer: </b>The name and login of the Issuer</li>
	<li><b>Seller: </b>The name and login of the Seller</li>
	<li><b> Validity: </b> The period in which the guarantee will be
	active</li>
	<li><b>Expiration date:</b>Search by Expiration date within a period</li>
	<li><b>Amount: </b> The amount of the guarantee.</li>
</ul>
<hr class="help">


<a name="guarantees_search_results"></a>
<h3>Guarantee search result</h3>
This page shows the search result of the guarantees
<br> In oder to view a guarantee select the corresponding icon
<img border="0" src="${images}/view.gif" width="16" height="16">.
<br><br>
<hr class="help">


<a name="guarantee_details"></a>
<h3>Guarantee details</h3>
This pages shows the following the details of the guarantee as described in
<a href="#guarantee_register"><u>register guarantee</u> </a> help section.
Additional fields are:
<ul>
	<li><b>Status: </b>The current <a href="#statusG"><u>status</u>
	</a> of the guarantee</li>
	<li><b>Registration date: </b>The date the guarantee was registered.</li>
	<li><b>Generated loan: </b>In the case the guarantee generated
	a loan a link will be displayed to the loan</li>
</ul>
<br><br>
<hr class="help">


<a name="guarantee_payment_obligations"></a>
<h3>Payment obligations of guarantee</h3>
This window will show a list with the
<a href="#payment_obligations"><u>payment obligations</u>
</a> related to the guarantee. 
<br><br>
<hr class="help">


<a name="guarantee_logs"></a>
<h3>Guarantee status changes (log)</h3>
This window will show a list with the <a href="#statusG"><u>status</u></a>
<br><br>
<hr class="help">


<h3>Guarantee status</h3>
<a name="statusG"></a> Guarantees can have various different status depending on the Guarantee type
and the operational flow. Here below are listed all possible status and every status will describe
the possible actions that will lead to the status.
<br><br>

<b>Status / Actions</b>
<ul>
	<li>Pending issuer</li>
	<ul>
		<li>When an administrator registers a new guarantee that needs
		to be authorized by an issuer, or issuer and administrator.</li>
		<li>When a seller accepts a payment obligation or a
		&quot;pack&quot; of payment obligations.</li>
	</ul>
	<li>Pending administrator</li>
	<ul>
		<li>When an administrator registers a new guarantee that needs
		to be authorized by an administrator (only).</li>
		<li>When an issuer authorizes a guarantee that is in
		&quot;pending issuer&quot; status and is configured to be authorized
		also by an administrator.</li>
	</ul>
	<li>Accepted</li>
		<ul>
		<li>When an administrator registers a new guarantee that does
		not require authorization.</li>
		<li>When an administrator accepts a guarantee that is in status
		&quot;pending administrator&quot;.</li>
		<li>When an issuer accepts a guarantee that is in &quot;pending
		issuer&quot; status.</li>
	</ul>

	<li>Denied</li>
	<ul>
		<li>When an issuer denies a guarantee that is in &quot;pending
		issuer&quot; status.</li>
		<li>When an administrator denies a guarantee that is in status
		&quot;pending administrator&quot;.</li>
	</ul>
	<li>Canceled</li>
	<ul>
		<li>When an administrator cancels a guarantee that has the
		status pending issuer or pending administrator.</li>
		<li>When an administrator cancels a guarantee that has the
		status accepted but the loan has not been generated yet because it
		requires another (loan) authorization.</li>
	</ul>
	<li>No action</li>
	<ul>
		<li>When the max time of a guarantee in status pending issuer
		has expired (issuer did not perform any action).</li>
		<li>When the max time of a guarantee in status pending
		administrator has expired (administrator did not perform any
		action).</li>
	</ul>
</ul>

<h3>Notifications</h3>
The guarantee status changes can generate the following notifications:
<ul>
	<li>Sent to the issuer when a new guarantee is created and gets
	the status &quot;pending issuer&quot;.</li>
	<li>Sent to issuer when an existing guarantee has been canceled
	or the maximum period of the guarantee status expired.</li>
	<li>Sent to the buyer when a guarantee status changes to the
	status accepted, denied or canceled.</li>
	<li>Sent to the seller when a guarantee status changes to the
	status accepted, denied or canceled.</li>
	<li>Sent to the administrator when a new guarantee gets the
	status &quot;pending administrator&quot;.</li>
</ul>
<br><br>
<hr class="help">


<a name="guarantee_authorization"></a>
<h3>Guarantee authorization</h3>
Guarantees can required authorization in various steps of the operational flow and by different
roles. The authorization configuration is defined in the
<a href="#edit_guarantee_type"><u>guarantee type</u>
</a> by the option &quot;Authorized by &quot;
<hr>


<a name="payment_obligations"></a>
<h2>Payment obligations</h2>
<br><br>A payment obligation is a digital (cyclos) document, just as a loan
or an invoice. As the word implies, a payment obligation is an obligation for a &quot;buyer&quot; to
pay a &quot;seller&quot;. A payment obligation is created by the buyer, usually for each outstanding
invoice of a provider (seller). Payment obligations have an &quot;amount&quot; and &quot;expiration
date&quot; and can be bundled together in a &quot;pack&quot; in order to make it more convenient to
process them. When the seller accepts the payment obligation(s) he/she will receive internal units
for the amount of the payment obligation(s). Once payment obligations are accepted by the seller and
validated by the Issuer (and optionally by the Cyclos administrator as well) they will generate a
loan and a Guarantee.

<i>Where to find it?</i><br> Payment obligations can be found at &quot;Menu: Guarantees > Payment
obligations&quot;
<br><br>
<hr class="help">


<a name="payment_obligations_search"></a>
<h3>Search payment obligations</h3> At this page you can search for payment obligation. 
<br>Possible search options are: 
<ul>
	<li><b>Status: </b> is the <a href="#statusOP"><u>status</u>
	</a> of the payment obligations.</li>
	<li><b>Currency: </b>(only displayed if multiple currencies exist).</li>
	<li><b>Buyer / Seller: </b>Depending on role of the logged user</li>
	<li><b>Expiration date: </b>Published date, after this date the
	payment obligation will not be visible to the seller anymore. The
	publication date most be before the expiration date.</li>
	<li><b>Amount: </b>Amount range of the payment obligation </li>
</ul>
<br><br>
<hr class="help">


<a name="payment_obligations_search_results"></a>
<h3>Search result payment obligation</h3>
This window will show the results of the search. In order to access a payment obligation you can
select the icon
<img border="0" src="${images}/view.gif" width="16" height="16">.
<br><br>
<hr class="help">


<a name="edit_payment_obligation"></a>
<h3>Create & edit payment obligation</h3>
Buyers with a valid <a href="#certifications"><u>certification</u></a>
can emit payment obligations to their providers (sellers). <br>
The buyer can create payment obligations at the menu option
&quot;Guarantees > Payment obligations&quot; <br> The necessary
fields are: <br>
<ul>
	<li><b>Seller: </b>The name and login of the Seller</li>
	<li><b>Published until: </b>Published date, after this date the
	payment obligation will not be visible to the seller anymore. The
	publication date most be before the expiration date.</li>
	<li><b>Expiration date: </b>End date until which the seller can
	accept the payment obligation</li>
	<li><b>Amount: </b>Amount of the payment obligation (normally
	amount of specific invoice)</li>
	<li><b>Description: </b> Optional description</li>
</ul>
<br> 
Once a payment obligation has been created the following options
are available:
<ul>
	<li><b>Delete: </b>This will delete the payment obligation</li>
	<li><b>Publish: </b>This will put the payment obligation in the
	published state. What means it will be visible for the seller.</li>
	<li><b>Change: </b>With this option the payment obligation can be
	changed.</li>
	<li><b>Un-publish: </b> (only visible when in published status).
	Selecting this option the payment obligation will regain the
	&quot;Registered&quot; status. What means it can be altered, and
	published again.</li>
</ul>
<br><br>
<hr class="help">


<a name="accept_payment_obligation"></a>
<h3>Actions by seller</h3>
When a payment obligations (or a pack of payment obligations) are in the &quot;published&quot;
status the seller can either accept or reject the payment obligation(s) by selecting the options in
the payment obligation window.<br> <br> In order to make it more convenient it is possible for the
seller to submit multiple payment obligations as a &quot;pack&quot;. This can be done by marking the
select boxes in front of each payment obligation in the payment obligation search result list.
<br> Note: These select options only up when the search result is limited by buyer and currency (if
more than one currency is available).
<br> On rejecting the payment obligation(s) will be simply canceled and the buyer will be informed
with a notification.
<br> When a seller accepts a payment obligation the following will happen.
<ul>
	<li>A guarantee will be generated with the amount of the payment
	obligation(s).</li>
	<li>The issuer will have to authorize the guarantee</li>
	<li>Optionally an administrator will have to authorize the
	guarantee as well</li>
	<li>Once authorized by all levels the guarantee will enter in the
	&quot;accepted&quot; status, a loan will be generated with the buyer
	as destination account</li>
	<li>The amount of the loan will be forwarded to the seller
	account. This means that the loan will be open for the buyer but the
	seller has been debited with the guarantee amount</li>
	<li>Possible credit and emission fees are charged (depending on
	the configuration in the <a href="#edit_guarantee_type"><u>guarantee
	type</u> </a>
</ul>
All status changes concerning payment obligations are logged and
notifications are send to both the buyer and the seller.
<br><br>
<hr class="help">


<a name="payment_obligation_logs"></a>
<h3>Payment obligation status changes</h3>
This window gives information about the changes of the
<a href="#statusOP"><u>status</u> </a>
<hr class="help">


<h3>Payment obligation status</h3>
<a name="statusOP"></a> Payment obligation can have various different status depending on the
Guarantee type and the operational flow. Here below are listed all possible status and every status
will describe the possible actions that will lead to the status.
<br><br>

<b>Status / Actions</b>
<ul>
	<li>Registered</li>
	<ul>
		<li>When a buyer creates a new payment obligation.</li>
		<li>When a buyer unpblishes a payment obligation.</li>
		<li>When a max publication date of a payment obligation in the
		published status is reached.</li>
	</ul>
	<li>Published</li>
	<ul>
		<li>When a buyer publishes a payment obligation that is in the
		registered status.</li>
	</ul>
		<li>Accepted</li>
	<ul>
		<li>When a seller accepts a payment obligation that is in the
		published status.</li>
	</ul>
		<li>Denied</li>
	<ul>
		<li>When a seller denies a payment obligation that is in the
		published status.</li>
		</ul>
	<li>Expired</li>
	<ul>
	<li>When the expiration date of a payment obligation in published
	status is reached.</li>
	</ul>
</ul>
<h3>Notifications</h3>
The payment obligation status changes can generate the following notifications:<br>
<ul>
	<li>To the seller when a payment obligation gets the published status.</li>
	<li>To the buyer when a buyer when a payment obligation gets the denied status.</li>
</ul>

</div>

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
