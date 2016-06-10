<div style="page-break-after: always;">
<a NAME="top"></a>
<br><br>Member records are user-defined units of
information that are attributed to particular members. There are many features
for defining and managing member records.<br>
You should use member records if you want to store information about members on
a one-to-many basis (meaning: one member can have many different values stored
in his &quot;member records&quot;). This distinguishes member records from <a
	href="${pagePrefix}custom_fields"><u>custom fields</u></a>, where you can also
attach custom defined information to a member, but in the case of custom fields
one member can only have one value in his own custom field, and not many.<br>
<br>
You can define many different types and fields in member records. A simple example
is the member &quot;remarks&quot; that previous Cyclos versions had. A remark 
was just a list of text fields that could be added to a member. Now it is possible
to add more field types to a record and there are different ways they can be displayed.
(as explained later) 


<span class="admin"><br><br>This manual has a small <a href="#guidelines"><u>guideline</u></a>
menu which you may want to check, and comes with an <a href="#example"><u>example</u></a>
to make the use of member records more illustrative.
<br><br><i>Where to find it?</i><br>
Member records can be accessed via the <a href="${pagePrefix}profiles"><u>profile</u></a>
of a member > block &quot;Member info&quot;. You can set member record types via 
the &quot;Menu: Users & Groups > Member record types&quot;.
<br><br>
<i>How to get it working?</i><br>
Please check our <a href="#guidelines"><u>guideline</u></a> for this.
</span>
<span class="broker">
<br><br>These member records are additional information associated with each
member. You may have the ability to view, add, edit, or delete these records
depending on the privileges set by the administrator.
<br><br><i>Where to find it?</i><br>
Member records can be accessed via the <a href="${pagePrefix}profiles"><u>
profile</u></a> of a member. The member record type will be listed under &quot;Broker
actions for...&quot;.<br>
There is also a search on member records per record type in the menu
&quot;Brokering&quot;. For example the default database has a record type called
&quot;Remarks&quot; and this will show up in the Brokering menu. In this 
search you can  <a href="#search_member_records"><u>search</u> for records.
</span>
<hr>

<span class="admin"> <a name="guidelines"></a>
<h3>Guidelines for defining member records</h3>
In order to create new member records, the following steps must be performed:
<ol>
	<li>First, think clearly about what you want with your member record. What
	information would you like to store? Couldn't it be done with a simple
	<a href="${pagePrefix}custom_fields"><u>custom field</u></a>?
	<li>If you want to create a new member record type you will need to have
	the permissions. This can be found under the block &quot;Member records types&quot;
	in the administration <a href="${pagePrefix}groups#manage_group_permissions_admin_system"><u>
	permissions</u></a>; you will need to check the &quot;manage&quot; checkbox.
	<li>After that, you can create a new member record type via &quot;Menu:
	Users & Groups > <a href="#member_record_types_list"><u>Member records</u></a>
	&quot; with the &quot;insert new member record type&quot; button.
	<li>Once you have created the new member record type, in the <a
		href="#member_record_type_fields_list"><u>next screen</u></a> you will have
	the possibilities to add fields to the member record type. You MUST add at
	least one field to each member record type otherwise the type cannot hold any
	information, and will be completely useless. For some fields, you will need to
	create possible values too (see the <a href="#example"><u>example</u></a> ).
	<li><b>Set permissions</b> Once the record type is created you need to define
	who can view, modify and delete member records in the &quot;Menu: Users &
	Groups > Permission Groups&quot; section, block &quot;Member records&quot;. You can
	set this for admin groups as well as for broker groups.
	<li>The member record will show up with a button below the member <a
		href="${pagePrefix}profiles"><u> profile</u></a>, in the &quot;Member
	info&quot; section of the <a
		href="${pagePrefix}profiles#actions_for_member_by_admin"><u>Actions
	for ...</u></a> window. This will only be visible for brokers or admins. Here, you can
	add as many items of this member record as you want.
	<li>If the member record configuration has the option &quot;Show menu
	item&quot; checked you can search values of that member record via the main
	&quot;menu: users & groups&quot;.
	<li><b>Searching:</b> All member records can be searched via the
	&quot;actions&quot; page below the profile of a member. You can also search all
	member records (not only related to one member) from the menu
</ol>
<hr class="help">

<a name="example"></a>
<h3>Example Member Record</h3>
The help descriptions will refer to a specific example of a member record type,
in order to make the concept of member records better understandable. The
example is just a simple example, and without any doubt better configuration of
this member record type would have been possible.
<ol>
	<li><b>Think what you want:</b> In this example, we will create a new
	member record type, which is called "helpdesk calls" to keep track of how often
	members are calling the help desk with what kind of problems.<br>
	<br>
	<li><b>Set the permissions:</b> &quot;menu: users & groups > permission
	groups&quot;, and refer to the helps of these screens, as <a
		href="${pagePrefix}groups#manage_groups"><u>setting permissions</u></a> is
	pretty straightforward.<br>
	<br>
	<li><b>Create new member record type:</b> In &quot;Menu: Users & Groups >
	<a href="#member_record_types_list"><u>Member records</u></a>&quot; you have to
	click the &quot;insert new member record type&quot; button. In the following <a
		href="#edit_member_record_type"><u>window</u></a>, you fill in the following:
	<ul>
		<li><b>name:</b> &quot;helpdesk call&quot;
		<li><b>label:</b> this would become &quot;helpdesk calls&quot;.
		<li><b>groups:</b> choose a member group for which you want to use the
		new member record type. For example &quot;full members&quot;
		<li><b>Search results layout:</b> as we are not using it for any
		numerical analysis, we will just select &quot;tiled&quot;.
		<li><b>show menu item:</b>This means the record type will show up in the 
		menu the main &quot;menu: users & groups&quot;. In that page you can search 
		for record values with various search options.
		<li><b>editable:</b> there is no need to change the items after they have
		been created, so we won't check this one.
	</ul>
	After this, we click &quot;submit&quot; to create the new member record type.
	It will show up in our <a href="#member_record_types_list"><u>Member
	records types</u></a> overview. <br>
	<br>
	<li><b>Create fields in the member record type:</b> You need to create
	fields in the member record, otherwise the new record makes no sense. In the <a
		href="#member_record_types_list"><u>Member records types</u></a> overview you
	should click the <img border="0" src="${images}/edit.gif" width="16"
		height="16">&nbsp; edit icon, bringing you to the <a
		href="#edit_member_record_type"><u>edit member record type</u></a> window. Use
	the &quot;insert new custom field&quot; button for each new field we need to
	create. This will bring us to the <a href="#member_record_type_fields_list"><u>member
	record type fields list</u></a> screen.<br>
	The fields are a bit silly, but it just serves as an example.<br>
	<ul>
		<li><b>date field:</b> by clicking the &quot;insert new custom
		field&quot; button in the &quot;member record type fields list&quot; we arrive
		at the screen to create a <a
			href="${pagePrefix}custom_fields#edit_custom_fields"><u>new custom
		field</u></a>. Here we fill in the following (form fields not listed are not essential
		for the functioning of the new field):
		<ul>
			<li><b>name:</b> &quot;date&quot;
			<li><b>data type:</b> &quot;date&quot;
		</ul>
		Fill anything at the other fields in the form, and click &quot;submit&quot; to
		save.<br>
		<b>NOTE:</b> in fact, creating a date field is not at all necessary, as cyclos
		automatically keeps the creation date of each member record entry, so you can
		perform a search on it.
		<li><b>type field:</b> again click the &quot;insert new custom
		field&quot; button in the &quot;member record type fields_list&quot;. Now fill
		in the following:
		<ul>
			<li><b>name:</b> &quot;type&quot;
			<li><b>data type:</b> &quot;enumerated&quot;
			<li><b>field type:</b> &quot;radio button&quot;
			<li><b>required:</b> should be checked.
		</ul>
		After clicking submit, you will again see the &quot;member record type
		fields_list&quot; window. Now, you still have to define possible values for
		this new field, and you do this by clicking the <img border="0"
			src="${images}/edit.gif" width="16" height="16">&nbsp; edit icon of the
		now listed &quot;type&quot; field.<br>
		This will bring you back to the &quot;edit custom field&quot; form. Here,
		below, click the &quot;new possible value&quot; button to enter the new values
		&quot;complaint on other member&quot;, &quot;doesn't understand cyclos&quot;
		and &quot;wants to tell he's happy&quot;. If finished, these values should
		appear in the <a href="${pagePrefix}custom_fields#possible_values"><u>
		possible values</u></a> list. In the end, click &quot;back&quot; to return to the
		field overview.
		<li><b>Remark field:</b> Finally we will add a remark field in the same
		way:
		<ul>
			<li><b>name:</b> &quot;remark&quot;
			<li><b>date type:</b> &quot;String&quot;
			<li><b>Field type:</b> &quot;rich text editor&quot;
		</ul>
		Now the new record type is finished. It will not become visible in the main
		menu untill you log out and log in again.
	</ul>
	<li><b>add data:</b> Now you can start using the member record. Below the
	profile of a member, in the &quot;Member info&quot; section of the <a
		href="${pagePrefix}profiles#actions_for_member_by_admin"><u>Actions
	for ...</u></a> window, there will be a special button &quot;help desk calls&quot;
	which will bring you to the window to enter data in you new member record.
	<li><b>You can search the data in the member record via &quot;menu:
	Users & groups > help desk calls&quot;:</b>
</ol>
<hr class="help">
</span>


<span class="admin">
<a NAME="member_records"></a>
<h3>Edit member record</h3>
This screen shows the <a href="#top"><u>member record</u></a> data of a
<a href="#edit_member_record_type"><u>list</u></a>
type member record.<br>
It displays the user who created the record, when it was created, and the
contents of the custom fields that were defined in the &quot;( Menu: User &
Groups > <a href="#member_record_types_list"><u>Member record types</u></a>  )&quot;.
If the record field was defined as &quot;Editable&quot;, 
then the option to modify the record data is also available.<br>
If you have the permissions, you may also create a new record by
clicking the &quot;submit&quot; button below the page labeled &quot;create new...&quot;
<hr class="help">
</span>

<span class="broker">
<a NAME="member_records"></a>
<h3>Edit member record</h3>
This screen shows the <a href="#top"><u>member record</u></a> data.
Some member record types are editable. Editable member records can be modified
by selecting the &quot;Change&quot; button, editing the
fields, and clicking &quot;Submit&quot;.<br>
If you have the permissions, you may also create a new record by
clicking the &quot;submit&quot; button below the page labeled &quot;create new...&quot;
<hr class="help">
</span>

<span class="admin"> <a NAME="member_record_types_list"></a>
<h3>List member record types</h3>
In this screen the available <a href="#top"><u>member record</u></a> types are
displayed.
<ul>
	<li>By clicking on the <img border="0" src="${images}/edit.gif" width="16"
		height="16">&nbsp; Edit Icon, the member record type properties can be
	modified.
	<li>By clicking on the <img border="0" src="${images}/delete.gif"
		width="16" height="16">&nbsp; Delete Icon, the Member record type is
	removed. Note that it is only possible to remove member record types which
	haven't been used yet; as soon as one member has information in this member
	record type, then the member record type cannot be deleted anymore.
	<li>To create a new member record type, click the &quot;Submit&quot;
	button next to &quot;Insert new member record type&quot;. If you want to create
	a new member record type, you may want to refer to this small <a
		href="#guidelines"><u> guideline</u></a>.
</ul>
<hr class="help">
</span>

<span class="admin broker"> <a NAME="remarks"></a>
<h3>Tiled member records</h3>
The information you can enter in this screen is a tiled &quot;<a href="#top"><u>
member record</u></a>&quot;. Most of the time this is defined for the possibility of
adding information to a member profile. The fields are defined by the administration.
To add a record fill in the fields (the fields with a red asterisk are
obligatory) and click &quot;Submit&quot;. The existing records for the member
are listed in the lower area.
<hr class="help">
</span>

<span class="admin broker"> <a NAME="search_member_records"></a>
<h3>Search member records</h3>
Here you can search through the member records by filling in the fields and
clicking &quot;Search&quot;.
<ul>
	<li><b>Keywords:</b> allows you to search in any field of the <a
		href="#top"><u>member record</u></a> type.
	<li><b>Login name:</b> and..
	<li><b>Member:</b> allow you to search records by the user they are
	attributed to.
	<li><b>Creation date:</b> can be used to search on records by the date
	they were created. Each member record type comes automatically with a creation
	date field; you don't have to create this field yourself.
</ul>
In addition to these fields are any custom fields which you defined for your
member record type (in &quot;Menu: Users & Groups > <a
	href="#member_record_types_list"><u> Member record types</u></a> &quot;.
<hr class="help">
</span>

<span class="admin broker">
<a NAME="search_records_of_member"></a>
<h3>Search member records of one member</h3>
Here you can search through the records of this specific member by filling in the fields and
clicking &quot;Search&quot;.
<ul>
	<li><b>Keywords:</b> can be used to search on any field in the record.
	<li><b>Creation date:</b> is stored for every entry in the records; this
	field is automatically created for each member record type.
	<li>In addition to these fields are the
custom fields associated with the particular member record types that are
defined by the administrator.
	<li>You can enter new data in the member records of this member, by clicking
	the &quot;submit&quot; button. This button is labeled &quot;create new &quot;, followed
	by the name of the member record type.
</ul>
<hr class="help">
</span>

<span class="broker admin"> <a NAME="member_records_search_results"></a>
<h3>Member records search results</h3>
Here are the results of the member record search. The User name and the
specified member record fields are displayed in the result.
<ul>
	<li>To view the entire record click the <img border="0"
		src="${images}/view.gif" width="16" height="16"> &nbsp; View icon.
	<li>To modify it click the <img border="0" src="${images}/edit.gif"
		width="16" height="16"> Edit icon.
	<li>To remove it click the <img border="0" src="${images}/delete.gif"
		width="16" height="16"> Delete icon. &nbsp; &nbsp;
</ul>
<hr class="help">
</span>

<span class="admin"> <a NAME="edit_member_record_type"></a>
<h3>Modify or create member record type</h3>
Here you can modify the structure of the <a href="#top"><u>Member Record</u></a>.
To modify the record, select &quot;Change&quot;, edit the fields and click
&quot;Submit&quot;. If you are creating a new type, you don't need to click
&quot;change&quot; first. For creating a new type, you may want to check these <a
	href="#guidelines"><u>guidelines</u></a> and our <a href="#example"><u>example</u></a>
<ul>
	<li><b>Name:</b> is self-explanitory.
	<li><b>Label:</b> is used by the User Interface and should most of the
	time be the name of the member record in plural form.
	<li><b>Description:</b> is a text description of the purpose and meaning
	of the record. You can fill in here anything you like.
	<li><b>Groups:</b> this drop down allows you to select which user groups
	can have this member record type attributed to them. If a record is attributed
	to a user group, then the new member record type will show up with a button in
	the &quot;Member info&quot; section of the <a
		href="${pagePrefix}profiles#actions_for_member_by_admin"><u>Actions
	for ...</u></a> window below the member's profile (only visible for administrators).
	<li><b>Search results layout:</b> this allows you to choose how the record
	data appears in search results. The options are:
	<ul>
		<li><b>tiled:</b> each item is listed below the previous item, separated
		by a line. This is most appropriate for remarks and alike.
		<li><b>list:</b> items are displayed in table format, with columns and
		rows. Each record is a row in the table.
	</ul>
	<li><b>Show menu item:</b> if checked, there will be a menu item with the
	name of the type in the admin interface, under the &quot;Users & Groups&quot;
	section. You can use this menu item to <a href="#search_member_records"><u>search</u></a>
	the member record type for specific values.
	<li><b>Editable:</b> if checked, the record data can be modified after
	being created (by admins or brokers). If it is not checked, it cannot be
	changed anymore after being created. Usually, remark types are not editable:
	once a remark has been created it cannot be changed anymore.
</ul>
<br><br>In addition to modifying the properties of the record, you should also
create and modify the &quot;Custom Fields&quot; in the <a
	href="#member_record_type_fields_list"><u>window below</u></a>, otherwise the
member record you created will be empty and meaningless.
<hr class="help">

<a NAME="member_record_type_fields_list"></a>
<h3>Member record type fields list</h3>
Here the fields of the Member Record are listed. The fields are where the data
of the record are stored and indexed. In order for a Member Record to be useful,
at least one custom field must be present.
<ul>
	<li>To create a new custom field click &quot;Submit&quot; next to
	&quot;Insert new custom field&quot;.
	<li>To change the order that the fields appear click the button next to
	&quot;Change field order&quot;. This button is only visible when applicable.
	<li>To edit a field click the <img border="0" src="${images}/edit.gif"
		width="16" height="16"> &nbsp; Edit icon.
	<li>To delete a field click the <img border="0" src="${images}/delete.gif"
		width="16" height="16"> &nbsp; Delete icon.
</ul>
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