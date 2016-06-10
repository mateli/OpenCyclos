<div style="page-break-after: always;"
<br><br>An access device is a payment medium
that can be used to make payments outside of Cyclos. This can be a POS
(point of sale) device in combination with a credit or debit card.<br>
A member with an active card can make payments at a the webPOS channel,
a hardware POS (card reader) or POS software installed at a computer.
Card payments are typically consumer-to-business payments. A member can
have more than one cards but only one active card.<br>
<br>
<span class="broker admin"> It is possible to require in stead of
the PIN the transaction or login password of the user.<br>
A card is based on a <a href="#edit_card_type"><u>card type</u></a>
which is a kind of a template for the card.
</span>


<span class="member"> <i>Where to find it?</i><br>
Access devices can be reached via the &quot;Menu: Personal > POS devices
/ Cards<br>
</span>
<span class="broker"> The access devices or &quot;brokered&quot;
users can be found at the brokering actions profile page. </span>

<span class="admin"> <i>Where to find it?</i><br>
Access devices can be reached via member's profile or &quot;Menu: Access
devices.<br>
<br>
<i>How to get it working.</i><br>
In order to generate a &quot;card type&quot; needs to exist and it it
needs to be assigned it to one or more a member or broker groups. After
that you can generate a card for a member from the member actions. It is 
also possible to generate multiple cards for a group of members from the <a
	href="${pagePrefix}user_management#bulk_actions"><u>Bulk
Actions</u></a> function. </span>


<a name="card_details"></a>
<h3>Card details</h3>
This window shows the details of the card and the actions you can
perform on it.
<br>
<br>
Card details
<ul>
	<li><b>Card number</b>
	<li><b>Activation date</b>
	<li><b>Creation date</b>
	<li><b>Expiration date</b>
	<li><b>Status</b>
</ul>

Card Actions
<ul>
	<li><b>Block card</b> This option will only appear if card status
	is &quot;Active&quot;. When this action is performed the card will
	enter in the &quot;block&quot; status what means it cannot be used. It
	can return to active state.
	<li><b>Unblock card</b> This option will only appear if card
	status is &quot;Blocked&quot;. When this action is performed the card
	will return to the &quot;active&quot;
	<li><b>Activate card</b> This option will only appear if the card
	status is &quot;pending&quot;. When this action is performed the card
	will enter in the &quot;active&quot; status.<br>
	Be aware that When the member has already an active card the existing
	active card and you activate a new one the existing active card will be
	canceled.
	<li><b>Cancel card</b> When this action is performed the card will
	enter in the &quot;cancel&quot; status what means it never can be be
	used again.
	<li><b>Change card security code</b> This option will only appear
	if a card security code is used.
</ul>
<span class="admin">
<b>Note:</b> 
More information about the card security code can be found at the help of the 
<a href="#edit_card_type"><u>edit card type</u></a> page.
</span>
<br><br>
<b>Note:</b>
The card actions will ask for transaction password if this is enabled.
Note all actions might be enabled for members. So not all actions might
show up.


<span class="admin">
<a name="card_logs"></a>
<h3>Card log</h3>
This windows will show a list with all card status changes (if any).
The actions that generates a card log are
<ul>
	<li>Block card
	<li>Unblock card  
	<li>Activate card
	<li>Cancel card	
</ul>
<hr class="help">
</span>


<a name="search_cards"></a>
<span class="broker admin">
<h3>Search cards</h3>
At this window you can search for cards with the following criteria:
<ul>
	<li>Card status
	<li>Groups 
	<li>Expiration date
	<li>Member
	<li>Card number
	<li>Card type. The only required field. If there's only one card
	type, this option won't appear.</ul>
<hr class="help">
</span>


<a name="search_card_results"></a>
<span class="member">
<h3>List cards</h3>
This page shows a list with all your cards. You can enter the card by
selecting the magnifying glass icon in order to perform actions on the
card.
</span>
<span class="broker admin">
<h3>Search card results</h3>
This page shows a list with the result of the search. You can enter the
card by selecting the magnifying glass icon in order to perform actions
on the card. </span>
<span class="admin"> You can the list to export to a CSV
file and to print the results selecting the print and csv icons on the
header.
</span>

<span class="admin">
<br><br><b>Create card</b><br>
When the card list is accessed from a member profile action you will
have the option to create a new card but selecting the &quot;Create
card&quot; button. If you select this option a new card will be created
with the pending status. The card will be based on the card type that is
set for the member group. (If the group doens't have a card type
associated, no card will be generated).<br>
If there is already a card in the pending status it will be canceled in
favor of the new card.
</span>
<hr class="help">


<span class="admin">
<a name="list_card_type"></a>
<h3>Card types</h3>
This window shows a list with all card types in the system. A card types
is a template for a card (in the same way a transaction type is a template 
for a transaction). You can edit an existing card type by selecting the 
edit icon and delete a card type by selecting the delete icon.<br>
Note that you cannot delete a card type when card transactions based the 
card type exist. 
<hr class="help">
</span>

<span class="admin">
<a name="edit_card_type"></a>
<h3>Edit card type</h3>
At this page you can create or edit a card type. A card type can only be 
edited when there are no cards generated with that card type.
The following fields exist.
<ul>
	<li><b>Name</b>: The card type name. The name is only used for the
	searches.
	<li><b>Format number</b>: Represent how the card number will be
	formatted.<br>
	Possible characters used are
	<ul>
		<li>&quot;#&quot; A number
		<li>&quot;-&quot; A separator
		<li>&quot;/&quot; A separator
		<li>&quot;\&quot; A separator
		<li>&quot;.&quot; A separator
	</ul>
	Examples for a format number: &quot;#### #### #### ####&quot; or
	&quot;####&quot; &quot;##/##&quot;
	<li><b>Expiration date</b>: At this date the card will enter in
	the expire status (and cannot be used anymore nor activated).
	<li><b>Ignore day in expiration date</b>: If it's checked the card
	will expire in the last day of the month
	<li><b>Security code</b>: The card security code is a password
	defined for the card. It works similar as a PIN with the difference
	that the PIN in Cyclos is not defined per card but per user (the user
	can use a PIN for more than one channels).<br>
	Possible values are:
	<ul>
		<li><b>Note used</b>: The card will not use security code
		<li><b>Manual</b>: The member and broker/admin (with autorisaties)
		can change the security code.
		<li><b>Automatic</b>: The system will generate the security code.
	</ul>
	<b>Max card security code error tries</b>: Card will be blocked after
	this number of unsuccessful attempts.<br>
	<b>Card security code block time</b>: The time the card will be blocked
	after the max number of unsuccessful attempts.<br>
	<b>Security code length</b> : The minimum and maximum length of the
	security code length.<br>
</ul>
<hr class="help">
</span>


<a name="POS"></a>
<h3>POS (Point of Sale)</h3>
A POS device can be either hardware (card reader) or software installed
at a computer (or any device with a . A POS normally is located at a
local business. A member can have more than one POS devices. Typically a
POS will identify the user (payer) when he/she passes a card through the
reader but this is optional (the POS can also allow manually input if it
is configured this way. The user will have to validate the card payment
by typing a PIN.
<br>
<span class="admin"> In order to enable POS payments the POS
channel needs to be enabled. More information about the POS can be found
at the <a href="${pagePrefix}settings#channels"><u>Settings -
channels</u></a> help file. </span>


<a name="edit_pos"></a>
<h3>Create / Edit POS</h3>
In this window you can set the configuration for the POS device and perform 
actions related to the POS. Each POS has the following details: 
<ul>
	<li><b>Identifier</b>: This number can be used to manage the POS
	devices. This is mostly the serial number of the POS device.
	An identifier cannot be changed once it has been defined.
	<span class="admin"> 
	The identifier is used to identify the POS when it communicates  
	with Cyclos (authentication is done by the PIN).
	</span>
	<li><b>Description of POS</b>: Description (optional).
	<li><b>Member login</b>: The login name of the member the POS is assigned to.
	<li><b>Full name</b>: The name of the member the POS is assigned to.
	<li><b>POS name</b>: This is a name that can be given to the POS.
	Contrary to the identifier it can be changed (e.g. Shop1, MobilePOS1
	etc.)
	<li><b>Assigned on</b>: Date when POS has been assigned to the member.
	<li><b>Status</b>: The Status of the POS can be Unassigned, Assigned, Active
	<li><b>Allow make payment</b>: A POS normally is used to receive
	payments from customers. If you want to allow payments made from 
	the owner of the POS to other members than this option needs to be checked.
	<li><b>Result page size</b>: This will show the maximum page
	results for the account summary page. The default is 5 what means it
	the account history will show the amount of the balance and the last five
	transactions.
	<li><b>Number of receipts</b>: The number of transaction receipts
	to be printed. It is common to print two receipts (one for the shop and
	one for the consumer)
	<li><b>Max scheduling payments</b>: The maximum of scheduled
	payments allowed.
</ul>

The following actions can be done for POS devices.
(Not all actions might be available. The actions depend on autorisaties)
<ul>
	<li><b>Assign</b>: POS is currently not assigned and can be assigned by 
	selecting this option. 
	<li><b>Unassign</b>: POS is currently assigned and can be un-assigned by 
	selecting this option.
	<li><b>Unblock</b>: POS is currently blocked assigned and can be unblocked by 
	selecting this option.
	<li><b>Block</b>POS is currently not active and can be blocked by selecting this option.
	<li><b>Change PIN</b>: Change PIN of the POS. The PIN is used to initialize the 
	POS and any action performed by the POS owner like retrieving account balance 
	and history and making payment. Receive payments from card clients does not
	require the POS PIN (because it is the client that provides the PIN)
	Will be show only if has permission is assigned. 
	<span class="admin">
	<li><b>Discard</b>: When a POS is discarded it cannot be used
	anymore in anyway. This means that it is not possible to create a new
	POS with the same POS ID. Normally you will discard a POS when the
	physical POS won't be used anymore (because it is broken or lost etc)
	</span>
</ul>
<hr class="help">
</span>

<span class="admin">
<a name="pos_logs"></a>
<h3>POS logs</h3>
If has any, show the pos status changes. The actions that generates a
pos log are
<ul>
	<li>Assign POS
	<li>Unassign POS
	<li>Block POS
	<li>Unblock POS
	<li>Discard POS
</ul>
<hr class="help">
</span>

<span class="broker admin">
<a name="search_pos"></a>
<h3>Search pos</h3>
Search for POS based on given criteria. You can search with the options.
<ul>
	<li>POS status
	<li>Identifier
	<li>Member
</ul>
<hr class="help">
</span>

<span class="broker admin"> <a name="search_pos_results"></a>
<h3>Search POS results</h3>
Shows list with the search result.
<ul>
	<li><img border="0" src="${images}/edit.gif" width="16"
		height="16"> Click the icon to modify the POS.
	<li><img border="0" src="${images}/delete.gif" width="16"
		height="16"> Click the icon if you want to delete the POS.<br>
	Note: You can only delete a POS if the POS never has been assigned to a
	member.
</ul>
<hr class="help">
</span>

<span class="member"> 
<a name="member_pos"></a>
<h3>POS list</h3>
This window shows a list with POS that have been assigned to you.
You can click on the &quot;View&quot; <img border="0" src="${images}/view.gif" width="16"
		height="16"> icon to modify the POS.
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
</div>
