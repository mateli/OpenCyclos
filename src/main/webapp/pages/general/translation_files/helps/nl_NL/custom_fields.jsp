<div style="page-break-after: always;"
<br><br>Often, organizations running cyclos want their
own specific type of information stored in the database of cyclos. Therefore, it
is possible to manage the fields which are maintained in the database, and which
are visible in the application.<br>
An administrator can add new fields, modify existing fields, and delete fields.
This is possible for member, admin
and <a href="${pagePrefix}operators"><u>operator</u></a> profiles,
for advertisements, for <a
	href="${pagePrefix}loans"><u>loans</u></a> and <a
	href="${pagePrefix}loan_groups"><u>loan groups</u></a>, and for <a
	href="${pagePrefix}member_records"><u>member records</u></a>.<br>
For example, if an organization needs an extra <a
	href="${pagePrefix}profiles"><u>profile</u></a> field
indicating the shoe size of the member, the administrator can create a new field
and define various properties of the new field, like name, type, size,
visibility, autorisaties, location, validation and other behavior and settings.<br>
The fields can be assigned to <a href="${pagePrefix}groups"><u>groups</u></a>.
This allows you to have different register forms and profiles for consumers and
businesses, just to give an example. In the above example, it would allow the
organization to create different policies for members with different shoe sizes.<br>
Cyclos comes with a set of default custom fields, which are of course also
manageable. Of course, not all fields in the database are custom fields; quite
some fields are so important that they cannot be removed, nor modified.<br>

<i>Where to find it?</i><br>
Custom Fields can be managed via the Main Menu, item &quot;Custom Fields&quot;.
<br><br><i>How to get it working?</i><br>
For managing custom fields, you need to have the 
<a href="${pagePrefix}groups#manage_group_autorisaties_admin_system"><u>autorisaties</u></a>; 
these can only be assigned to admins, and can be found under the block &quot;custom fields&quot;.
<hr>

<a name="list_custom_fields"></a>
<h3>List custom fields</h3>
This help applies to the custom fields for members,
admins,
<a href="${pagePrefix}operators"><u>operators</u></a>
,
<a href="${pagePrefix}advertisements"><u>ads</u></a>
,
<a href="${pagePrefix}loans"><u>loans</u></a>
and
<a href="${pagePrefix}loan_groups"><u>loan groups</u></a>
, and for
<a href="${pagePrefix}member_records"><u>member records</u></a>
.
<br><br>The list show all the custom fields which are defined for this subject.<br>
Member, admin and operator fields will show up in their profile. The default
database that comes with cyclos already has some custom member fields defined.<br>
Loan and loan group fields will show up in the loan and loan group pages, and ad
fields will show up in the ads page.
<br><br>The name and configuration of the fields can be modified by selecting the
edit icon. In the help file of that page you can find detailed information about
the custom field configuration.<br>
You can delete a field by clicking the red delete icon. Note that deleting a
custom field is only possible when it is not used already; as soon as any field
in the database has information, it will be impossible to delete the field. In
such a case you can hide the field by de selecting all groups.
<br><br>Selecting the &quot;Change field order&quot; Submit button gives you the
option to change the order that the fields appear in the page. <br>
Selecting the &quot;New Custom Field&quot; Submit button gives you the option to
insert a new profile field.<br>
<hr class="help">

<a name="order_custom_fields"></a>
<h3>Set custom fields order</h3>
This help applies to the custom fields for members,
admins,
<a href="${pagePrefix}operators"><u>operators</u></a>
,
<a href="${pagePrefix}advertisements"><u>ads</u></a>
,
<a href="${pagePrefix}loans"><u>loans</u></a>
and
<a href="${pagePrefix}loan_groups"><u>loan groups</u></a>
, and for
<a href="${pagePrefix}member_records"><u>member records</u></a>
.
<br><br>Here you can define the order that the custom fields will show up in the
page. To do this, put your mouse pointer over the field name, left click the
mouse and keep it held down and &quot;drag&quot; the name to the new position. <br>
After this select &quot;Submit&quot;.
<hr class="help">

<a name="edit_custom_fields"></a>
<h3>New/Edit custom fields</h3>
In this window you can set the properties of the custom field. Make sure you do
this correctly because some options you can only change on creation - after the
field is in use it may be impossible to make changes.<br>
Be aware that not all options are available for all types of custom fields. Currently 
the member (profile) fields have the most possible options.
<br><br>The following options exist:
<ul>
	<li><b>Name:</b> This is the name or the &quot;label&quot; of the field
	that will be visible in Cyclos.
	<li><b>Internal name:</b> This is the internal name of the field. It is
	only used for development purposes.
	<li><b>Data type:</b> With the data type you can specify the type of
	field. There are six field types.
	<ul>
		<li><b>String:</b> The string can be text with any character. If you want to
		specify an obligatory field &quot;input pattern&quot; like e-mail or postal
		code number you can create such a mask in the field below. The mask will force
		the user to enter information in the correct format. After submitting there
		will be an extra check to ensure the input is correct.<br>
		Documentation about the input mask can be found at the site of the project 
		<a
			href="http://javascriptools.sourceforge.net/docs/manual/InputMask_mask.html" target="_blank"> JavaScript
		tools </a>.
		<li><b>Enumerated:</b> The enumerated type means you have a list of
		values, like Area (&quot;north&quot;, &quot;east&quot;, &quot;south&quot;,
		&quot;west&quot;). An enumerated list can be presented as drop down box or
		radio select button. When enumerated is selected you will be presented with an
		extra input field called &quot;All selected label&quot;. This label will show
		up in the drop down by default. In the example of Areas the label will show
		&quot;All areas&quot;.
		<li><b>Integer number:</b> This type means that the field can only be a
		number, without a decimal comma or point (depending on your language).
		<li><b>Decimal number:</b> This means that the field is a number with a
		decimal comma or point. The precision and format is defined in the <a
			href="${pagePrefix}settings#local"><u>&quot;local settings > number
		format&quot;</u></a>.
		<li><b>Date:</b> This field can only contain a date. The date format can
		be defined in <a href="${pagePrefix}settings#local"><u>&quot;local
		settings > Internationalization > date format&quot;</u></a>.
		<li><b>Boolean:</b> The boolean is just a checkbox with two possible
		values: &quot;selected&quot; and &quot;not selected&quot; (or &quot;true&quot;
		and &quot;false&quot;).</ul>
	<li><b>Parent field:</b> Here you can define if the possible options to
	choose from for this field are dependant of the value of another field. For
	further explanation click <a href="#parent_field"><u>here</u></a>.
	<li><b>Field type:</b> There are different field types according to the
	Data types. The following types exist:
	<ul>
		<li>Text box (one line or text area (5 lines) for String (=text).
		<li>Select box or radio button for Enumerated (=drop down select)
		<li>Text box only for integer, decimal and dates.
	</ul>
	<li><b>Field size:</b> The field sizes can be &quot;tiny&quot;,
	&quot;small&quot;, &quot;medium&quot;and &quot;large&quot;. Their exact,
	absolute size can be defined in the style sheet file. You can also specify the
	&quot;full&quot; size which is the full size of the window. The option
	&quot;default&quot; can be different for each field type, but often means about
	80% of the available space. For example: the &quot;name&quot; field on this
	screen has the default size.
		<li><b>Visible for (ads only) :</b> Here you can define who can view the
	ad custom field. This can be:<br>
	All users (the owner as well as all other members, brokers and
	admininistrators. All brokers and administrators or administrators only.
	<li><b>Show in search (payment field only):</b> If this is checked the
	custom payment field will show up as a filter in the account summary. In case
	the payment type is a loan it will show up in the loan search for
	administrators.
	<li><b>Show in result list (payment field only):</b> If this is checked
	the custom payment field will show up as a column in the search result list. In
	case the payment type is a loan it will show up in the loan search result list
	for administrators.<br>
	Note the field will always be included in the export as csv and print. Even if
	this option is not checked.
	<li><b>Show in profile for:</b> Here you can define the who can view the
	field. The following options are available:
	<ul>
		<li><b>None:</b> The field is not visible for any user in the system.
		<li><b>Administrator:</b> Only the administrator can view the field.
		<li><b>Broker:</b> Only the administrator and the member's broker can
		view the field.
		<li><b>Member registration:</b> The member can only view the field when
		registering at the public registration page (and not at his profile). The
		broker and administrator can view the field.
		<li><b>Member, not at registration:</b> Member, broker and administrator
		can view the field but the field is not visible at the registration page.
		<li><b>Member:</b> Member, broker and administrator can view the field.
		<li><b>Other members:</b> Member, broker and administrator and other
		members in the system that have autorisaties to view the member group can view
		the field.
	</ul><li><b>Editable by:</b> Here you can define who can modify the field (the
	autorisaties are working with the same hierarchical structure as the &quot;show
	in profile for&quot; options<li><b>Member search for:</b> Here you can define for who the field will
	show up in the member search page. (the autorisaties work with the same
	hierarchical structure as the view autorisaties)
	<li><b>Advertisement search for:</b> Here you can define who the field
	will show up for, in the advertisement search page. (the autorisaties work with
	the same hierarchical structure as the view autorisaties)
	<li><b>Include in keywords search:</b> With this option you can make the field 
	available for the keywords field search option in Member and advertisements searches. <br>
	The member keyword search has the option to include only in the member search (Members only)
	or for both member and advertisement searches. If the latter option is selected
	members can search advertisements using member (profile) fields. Note that 
	this is only useful for combined searches. A advertisement search with a match 
	of a single member profile field will show all advertisements of this member. 	
	<li><b>Loan search for:</b> Here you can define who the field will show up
	for, in the loan search page. (the autorisaties work with the same hierarchical
	structure as the view autorisaties)
	<li><b>Member can hide:</b> Here you can define who will have the option
	to hide a field (the autorisaties work with the same hierarchical structure as
	the view autorisaties)
	<li><b>Show in member print:</b> Here you can define who the field will
	show up for, in the member search page. (the autorisaties work with the the same
	hierarchical structure as the view autorisaties)
	<li><b>Validation:</b> You can specify the following validations:
	<ul>
		<li><b>Required:</b> If this is selected the field will be obligatory and
		show a red asterisk next to the field. The user MUST enter a value.
		<li><b>Unique:</b> If unique is selected the field value can only exist
		once in the system. This can be selected if you need to assure that fields are
		unique like passport or fiscal registration numbers.
		<li><b>Min and max length:</b> If the field is a string you can define
		the min and max length. The user will only be able to input to the length of
		the field within this range.
		<li><b>Validator class:</b> If you need more complex validation that
		cannot be handled by a regular expression (in the input pattern field above)
		you can write your own validation class. <br>A typical situation would be if you
		want to perform a validation based on the calculation of the input, or a
		remote validation.<br>
		Information on how to implement a validator class can be found at the
		<a
			href="http://project.cyclos.org/wiki/index.php?title=Setup_%26_configuration#Custom_fields"
			target="_blank"><u>Wiki</u></a>.</ul>
	<li><b>Description:</b> Here you can put a description of the field. The
	administration can explain the use of the field. The description will only show
	up in the edit field.
	<li><b>Enable field for groups:</b> Here you can select the groups that
	will own the field.
</ul>
<hr class="help">

<a name="parent_field"></a>
<h3>Parent Field</h3>
When a field has a parent field, it means that the possible options to choose
from for this field are dependant of the value of another field.
<br>
For example, you may have a &quot;Province&quot; custom field, and a
&quot;city&quot; custom field. If the user selects &quot;Southarea&quot; as a
province, then the &quot;city&quot; field will list all the cities in the
province of &quot;Southarea&quot;. In this case, you would mark the
&quot;Province&quot; field as the
<i>parent field</i>
for the &quot;city&quot; field.
<br>
In the &quot;parent field&quot; field you can make your choice from other
existing fields to assign them as the parent field for the present field. Note
that the &quot;parent field&quot; is not always visible. It is only visible for
enumerated types.
<!--  check this: is dit waar?? -->
For each of the values of the parent field you can define a different set of
values for the child field, via the
<a href="#possible_values"><u>possible values</u></a>
window (available after clicking &quot;submit&quot;).
<hr class="help">


<a name="possible_values"></a>
<h3>Values for custom field</h3>
This window shows a list of the possible values for the field.
<br><br>You can delete a value by selecting the delete icon. This only works when
the specific value is NOT in use. I it is possible to &quot;empty&quot; values 
by assigning all used values to another one of the list. (this is explained 
further in edit value)
<br><br>
If your field has a <a href="#parent_field"><u>parent field</u></a>, you should
first select the appropriate value of the parent field for which you want to
define the new values, before clicking &quot;new value&quot;. You can use the
selector between the &quot;back&quot; button and the &quot;new value&quot;
button for this.
<hr class="help">

<a name="edit_possible_value"></a>
<h3>Insert new field value / modify field value</h3>
<br><br>The following options are available:

<ul>
	<li><b>Parent field name:</b> In case your field has a <a
		href="#parent_field"><u>parent field</u></a> , the value of the parent field
	where your new values will be assigned to is shown in this label.<br>
	(If you want to define new values for other values of the parent field, you
	should go back to the <a
		href="#possible_values"><u>previous screen</u></a>.
	<li><b>Value:</b>Here you can specify the name of a value. Write the value
	and select &quot;Submit&quot;. The value will show up in the value list in
	alphabetical order.
	<li><b>Default:</b> If selected this value will be pre-selected when the form
	is shown. There can only be one default value per custom field.
	<li><b>Enabled:</b> If this is checked the value will show up as possible select
	option. If enabled is not selected the value will show up but only of there is
	data in the value. This way you can decide not using a value that has been used
	in the past but prevent that old (not used) values are lost.
	<li><b>Replace occurrences by: (edit mode only)</b> When editing a value
	you can move the values of all fields that contain data for this value type to
	another one. This way it is possible to remove a value in the value list page
	(it only allows removing if the value is not used). If you want to prevent
	existing values you can also opt to disable the value as explained above.<br>
	(Removing values can be done in the list page at the <a
		href="#possible_values"><u>previous screen</u></a>)
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
<br><br>
</div>