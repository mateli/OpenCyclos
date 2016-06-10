<div style="page-break-after: always;">
<span class="admin">
<br><br>
These pages are the user management functions.
They can be used to search for members, register new members, get an overview of the
users presently logged in, or see other
information about member status.

<i>Where to find it.</i><br> 
The following lists the available functionality and where it can be found:
<ul>
	<li><b>Searching and creating members:</b> can be done via the &quot;Menu: Users & groups > 
	Manage members&quot;. This includes searching brokers.
	<li><b>Searching and creating admins:</b> can be done via the &quot;Menu: Users & groups >
	Manage admins&quot;.
	<li><b><a href="#connected"><u>Connected Users</u></a>:</b> can be found via the
	&quot;Menu: Users & Groups > connected users&quot;.
	<li><b><a href="#bulk_actions"><u>Bulk Actions</u></a>:</b> can be found via the
	&quot;Menu: Users & Groups > Bulk Actions&quot;.
</ul>
</span>


<span class="broker">
<ul>
	<li><b>Creating/registering members:</b> Provided you have the permissions for
	this, this can be found via &quot;Menu: Brokering > register member&quot;.
</ul>
</span>

<span class="member">
<ul>
	<li><b>Searching members:</b> You can search members via the &quot;Menu: Search >
	Members&quot;.
</ul>
</span>
<hr>

<span class="admin">
<A NAME="create_user"></A>
<h3>Create user</h3>
Here you can enter information about a new member. The window is pretty
straightforward; which fields are shown in this form is dependant of your
configuration. <br>
The member will be part of the <a href="${pagePrefix}groups"><u> group</u></a>that
is listed at the first field. 
<br><br>If there exists a broker group that has broker permissions to the group
of the member that you are creating you can assign (optionally) the member to a
broker from that broker group. For this, you should check the checkbox at the
bottom, labeled &quot;assign broker&quot;.<br>
You can specify a password for the user and specify if the user can use the
password directly or has to change it on the next login.
<br><br>After filling in the data you have the option to save the member and
enter a new member (button &quot;Save and insert new member&quot;) or to open
directly the <a href="${pagePrefix}profiles"><u>profile</u></a> of the new
member (button &quot;Save and open profile&quot;).
<hr class="help">
</span>

<span class="broker"> <a name="create_user_for_broker"></a>
<h3>Brokering - create new member</h3>
Here you can enter information about a new member. The window is pretty
straightforward; which fields are shown in this form is depending of the system
configuration. <br>
The member will automatically assigned as one of your brokered members, after
you completed the registration.<br>
You can specify a password for the user and specify if the user can use the
password directly or has to change it on the next login.
<br><br>After filling in the data you have the option to save the member and
enter a new member (button &quot;Save and insert new member&quot;) or to open
directly the <a href="${pagePrefix}profiles"><u>profile</u></a> of the new
member (button &quot;Save and open profile&quot;).
<br><br>For registering a new member, you might receive an automatic <a
	href="${pagePrefix}brokering#commission"><u>commission</u></a>, depending on
the rules in your system. The commission depends on the trade volume of the
member you registered. <br>
Via &quot;Menu: Brokering > members&quot; you can manage the members you
registered, and follow how they are doing.
<hr class="help">
</span>


<span class="member">
<A NAME="search_member_by_member"></A>
<h3>Search members</h3>
In this page you can search for members.
The member search will search in all member profile fields. You can use more
than one keyword in the search.<br>
Various &quot;operators&quot; can be used with the member (and advertisement)
search. The most commonly used are:
<ul>
	<li><b>*</b> The asterisk &quot;wildcard&quot; allows you to search for
	part of words. For example a search on ba* will return all users that have the
	letter combination Jo in there profile. For example Barbara, Bart, Baker street
	(where the latter would be an address field)
	<li><b>- not</b> A search with a keyword preceded directly by a minus sign
	or preceded by &quot;not&quot; and a space will return results that do not
	contain that keyword.
	<li><b>or</b> The search should return results with either the word
	preceding &quot;or&quot; or the keyword behind it.
	<li><b>and</b> The search should return results with both the word
	preceding &quot;and&quot; and the keyword behind it.
</ul>

<hr class="help">
</span>

<span class="member"> 
<A NAME="search_member_result"></A>
<h3>Search Results (for member search)</h3>
This window shows the result of a Member search. Selecting the &quot;Login
Name&quot; or &quot;Member Name&quot; will open the <a href="${pagePrefix}profiles"><u>
profile</u></a> of that Member.
Selecting the image icon will open the image(s) in a pop-up window.
<hr class="help">
</span>

<span class="admin"> 
<A NAME="search_member_by_admin"></A>
<h3>Search Members</h3>
In the search member page (Menu: Users & groups > Manage members)
you can search for members and register new members.
<br><br>If you want to search for a member you can fill in various search fields
(but none of them are obligatory). If you just click the &quot;Search&quot;
button without entering anything in the fields you will get a list of all the
members.<br>
<ul>
	<li><b>group filters:</b> Here you can specify a <a href="${pagePrefix}groups#group_filters"><u>
	group filter</u></a >.
	<li><b><a href="${pagePrefix}groups"><u>Permission group</u></a></b>
	<li><b>broker login / broker:</b> Enter here the login name or the real name of a broker, and
	your result page will show the members belonging to this broker.
	<li><b>Activation from / to:</b> With these date fields
you can search for the date on which
somebody became a member of your organization. You may use the datepickers by
clicking the calendar icon (<img border="0" src="${images}/calendar.gif" width="16" height="16">&nbsp;).
</ul>
<br><br>
In order to
register a new member you will have to use the drop down at the bottom of the form, on the left. 
Here you select the group in which you want the member to be created, and you will be taken
to the <a href="#create_user"><u>registration form</u></a>. 
<hr class="help">
</span>
 
<span class="admin">
<A NAME="admin_search_member_result"></A>
<h3>Search results (for member search)</h3>
This window shows the result of a Member search. Selecting the Login name or
Member name will open the <a href="${pagePrefix}profiles"><u>profile</u></a> of
that Member. Selecting the back button in that profile page will jump back to
the search result.
<br><br>You have the option to print the search result by clicking the print icon
(<img border="0" src="${images}/print.gif" width="16" height="16">&nbsp;)
at the top right in the window, next to the help icon. This will take you to a
printer friendly window, where you can click a button to print the overview.
<br><br>Another possibility is to download the results in a file by clicking the
save icon (<img border="0" src="${images}/save.gif" width="16" height="16">&nbsp;).
The results will be downloaded in <a href="${pagePrefix}loans#csv"><u>CSV format</u></a>, which 
will contain all the fields which exists in the Members <a
	href="${pagePrefix}profiles"><u>profile</u></a> (so including many fields not
visible in this result window). <br>
In <a href="${pagePrefix}settings#local"><u>local settings</u></a> you
can specify if you want to show the field names in the headings (first line) of
the columns.
<br><br>Note: In the reports function you will be able to retrieve more specific
<a href="${pagePrefix}reports#member_lists"><u>Member lists</u></a>.
For example member lists with additional data like the account
balance and number of advertisements, can be found in the reports function.
<hr class="help">
</span>


<span class="broker admin"> <a name="search_pending_member"></a>
<h3>Search pending members</h3>
Pending members are members that have been registered but did confirm there
registration yet by responding to an e-mail message. Only after confirmation the
member can login.<br>
After a specific time the pending will be removed automatically from the system
(and the list). All three registration options (self registration at public
registration page, registration by broker and by member) can demand e-mail
confirmation. <br>
At this page you can search for pending members. You can search by name, group
and registration date range.<br>
The group search does not mean that the members belong to the group but it
indicates the group the members will be part of after e-mail validation.<br>
</span>
<span class="admin"> You can also search by broker. This means that the search 
will only show the members registered by the selected broker. Note that this only
will work if members that are registered by a broker will need to confirm. This
is an optional <a href="${pagePrefix}groups#group_registration_settings"><u>Group
setting</u></a>. The maximum registration period is defined in the local settings. <br>
<br>
<hr class="help">
</span>

<span class="broker admin"> <a name="search_pending_member_result"></a>
<h3>Pending members list</h3>
This window shows the result of a Member search. Be aware that these members are
not &quot;active&quot; members (they cannot login and are not visible in the
system). Only in rare cases you would want to delete a member from the list.
Deleting a pending member would mean the member won't be able to confirm
his/here registration.<br>
If you edit the member you can view and modify the profile data and if necessary
send the validation again.
<hr class="help">
</span>

<span class="broker admin"> <a name="pending_member"></a>
<h3>Pending member details</h3>
At this page you can view and modify the profile data and if necessary send the
validation e-mail again.
<hr class="help">
</span>

<span class="admin">
<a name="search_admin"></a>
<h3>Search Administrators</h3>
In the search admin page (Menu: Users & groups > Manage admins) you can search
for administrators and register new administrators.
<br><br>The form is very simple: you can only specify a keyword, and/or limit the
search to a specific admin group. If you just click the &quot;Search&quot;
button without entering anything in the fields you will get a list of all the
administrators.<br>
<br><br>In order to register a new administrator you will have to use the drop
down at the bottom of the form. Here you select the <a
	href="${pagePrefix}groups#admin_groups"><u>group</u></a> in which you want the
admin to be created, and you will be taken to the <a href="#create_user"><u>registration
form</u></a> for new administrators.
</span>

<span class="admin">
<a name="search_admin_result"></a>
<h3>Search Results (admin search)</h3>
This box gives the results of your search on administrators. You can
click on an admin's login name, or on their real name, to get details
about this administrator.
<hr class="help">
</span>

<span class="admin">
<a name="create_admin"></a>
<h3>Register new administrator</h3>
Here you can register a new administrator.
We
<b>strongly</b>
recommend to have all people working as an administrator supplied with
their own account and login, so that no different people are sharing
one single admin account. This makes it easier to maintain
permissions, track possible errors, and close accounts when people leave
the organization. 
<br><br>The form is pretty simple
and straightforward. Any field with a red asterix (*) is obligatory.
<br><br><br><br>After filling in the data you have the option to save the admin and
enter a new admin (button &quot;Save and insert new administrator&quot;) or to open
directly the <a href="${pagePrefix}profiles"><u>profile</u></a> of the new
administrator (button &quot;Save and open profile&quot;).
<hr class="help">
</span>

<span class="admin">
<a name="connected"></a>
<h2>Connected Users</h2>
At the connect user page (Menu: Users & groups > Connected users) you can get an
overview which users (members, admins, brokers) are presently logged in and
using the system.

<hr>
</span>

<span class="admin">
<A NAME="connected_users"></A>
<h3>Connected users</h3>
This window allows you to specify which <a href="${pagePrefix}groups#group_categories"><u>
types</u></a> of connected users you want to see
in the window below. The &quot;show&quot; drop down allows you to choose
between &quot;admins&quot;, &quot;brokers&quot;, &quot;members&quot; and &quot;
<a href="${pagePrefix}operators"><u>operators</u></a>&quot;.<br>
Click &quot;submit&quot; to show the results.
<hr class="help">
</span>
 
<A NAME="connected_users_result"></A>
<span class="admin">
<h3>Search Results Connected users </h3>
This function will show a list with the connect users, according to you
selection in the above window.<br>
In the list you can click on the member to open the profile. Administrators with
permissions can disconnect a member by clicking the delete icon (<img border="0"
	src="${images}/delete.gif" width="16" height="16">&nbsp;) in the last column. 
</span>
<span class="member">
<h3>Search Results Connected operators</h3>
This function will show a list with the connected <a href="${pagePrefix}operators"><u>
operators</u></a>.<br>
In the list you can click on the operator to open the profile. If you have the
permissions, you can disconnect an operator by clicking the
delete icon (<img border="0" src="${images}/delete.gif" width="16" height="16">&nbsp;)
in the last column. </span>

<span class="admin">
<a name="bulk_actions"></a>
<h2>Bulk actions</h2>
The bulk actions function (Menu: Users & groups > Bulk actions) allow an
administrator to perform actions on entire groupings of users.

<hr>
</span>

<span class="admin">
<A NAME="bulk_actions_filter"></A>
<h3>Bulk actions filter</h3>
This window allows the administrator to specify the group of users on which the
<a href="#bulk_actions"><u>bulk action</u></a> is performed. The fields are combined
to a logical AND-search, so take care that you don't specify too much, because that
might end you up with an empty result.
<ul>
	<li><b>Group:</b> here you specify the <a href="${pagePrefix}groups#group_filters"><u>
	group filter</u></a>.
	<li><b>Permission Group:</b> Here you specify the <a href="${pagePrefix}groups"><u>
	group</u></a>. Take care that this doesn't conflict with the group filter specified above. 
	Usually you'd specify group filter or group, but not both.
	<li><b>broker login / broker:</b> if you want the action to be taken on all the members
	of one broker, specify the broker here, either by real name or login name.
	<li><b>...:</b> the rest of the form lists some <a href="${pagePrefix}custom_fields"><u>
	custom fields</u></a> defined for your member profiles. 
</ul>
Once you've specified your criteria for the bulk action, you can view which members are included
by clicking the &quot;preview&quot; button at the bottom right of the form. This
will result in the presentation of a list of members included.
<hr class="help">
</span>

<span class="admin"> <A NAME="bulk_action"></A>
<h3>Action</h3>
Here the <a href="#bulk_actions"><u>bulk action</u></a> to be performed is
specified. You have the following possibilities:
<ul>
	<li><b>Change group:</b> This will change the permission group of the
	selected grouping.<br>
	Once you selected this, You will be asked to enter the new <a
		href="${pagePrefix}groups"><u>permission group</u></a>, and a comment. After
	clicking &quot;submit&quot;, all the members involved will be moved to the new
	group.
	<li><b>Change broker:</b> This will change the <a
		href="${pagePrefix}brokering"><u>broker</u></a> of the selected grouping. <br>
	Once you selected this, you will be asked to give the new broker's login name
	or real name (if you enter one, the other will be auto completed).<br>
	Checking the &quot;suspend commission&quot; checkbox will result in discarding
	all running and open <a href="${pagePrefix}brokering#commission"><u>commission
	payments</u></a>. You may want to use this checkbox if you think the new assigned
	broker had no rights to receive commission payments evolving from the actions
	of the former broker. <br>
	You should also enter a comment. After having clicked the &quot;submit&quot;
	button, all the involved members will have the broker assigned.
</ul>
<hr class="help">

<A NAME="import_members"></A>
<h3>Import members</h3>
With this function it is possible to import members (profiles) and optionally
set an initial credit per member and generate an initial payment from or to a
system account. Numbers and dates should be formatted according to the local
settings definitions. <br>
The fields are identified by the column name. The column names are case
insensitive and need to be at the first line. The columns can be in any order
(there is no column one or two, as long as the names are correct it will work).
If a field is optional you can either omit the whole column of leave the column
value empty. <br>
The following columns/fields are supported.
<ul>
	<li>name (required)<br>
	The member full name.
	<li>username (required)<br>
	This is login name and is unique what means an it will give an error if a user
	with this name already exists in the system. In case login name is an
	automatically generated (account) number the column is not needed and Cyclos
	will generate the login names.
	<li>creationdate (optional)<br>
	If specified the date in the personal member reports (member since) will be set
	with this date. If no date is set in the column the current (import) date will
	be set. 
	<li>password (optional)<br>
	The plain member password. Members will have to change it on first login.
	<li>email (required or optional according to the Cyclos settings). It must
	be a well formed e-mail address.
	<li>balance (optional)<br>
	The initial account balance. Only used when an account type is selected. In
	case you select an account type you can specify the payment types (member to
	system for negative balance and system to member for positive balances.<br>
	If you use this option you must be sure that there is enough credit in the
	accounts that will be debited.
	<li>creditlimit (optional)<br>
	The account's (negative) credit limit. If empty it will take the defaults from
	the group account setting.
	<li>uppercreditlimit (optional)<br>
	The account's upper credit limit. If empty it will take the defaults from the
	group account setting
	<li>custom field internal name (optional)<br>
	The internal name of a custom field related to the selected group. The required
	validation is honored. In case a field is a list (enumerated) the import of the
	member will only succeed if it has a existing value. For example, if you have a
	custom field &quot;area&quot; with three possible areas &quot;center&quot;
	&quot;south&quot; and &quot;north&quot; with other areas will not be imported.
	Members with empty areas will be imported (but not if the field is set as
	&quot;required&quot;).
	<li>member record type.custom field internal name (optional)<br>
	Values for member records. An example for the default database is
	&quot;Remark.comments&quot;. Where comments is a field of the Remark record
	type. This field must be the internal name (specified in the field
	configuration).<br>
	Take care that if you want to import a record type field all fields that are
	defined for this record type need to be specified in the csv file (as a column).
	</ul>
<hr class="help">


<A NAME="imported_members_summary"></A>
<h3>Imported members summary</h3>
This pages gives an overview of the imported members. At this stage nothing is
processed yet. You can select the link (number) to see the status of successful
or unsuccessful imported members (or view both by selecting the number after
&quot;Total members&quot;).<br>
If you select &quot;Import&quot; the successful members will be imported.
Still it is good practice to view the status of the imported members.  
<hr class="help">


<A NAME="imported_member_details"></A>
<h3>Imported members search</h3>
In this window you can search in the imported list for specific members. You can
search either by line number or member name. The member name search option will
search in the loginname and membername fields.<br>
<hr class="help">


<A NAME="imported_member_details_result"></A>
<h3>Imported members search result</h3>
This window will show the result of the imports. In case of import errors it will 
inform the error type (e.g. field missing, name already in use) and in case of 
successful imports it will show the (lower) credit limit and account balance.<br>
To process the members you can hit back and the &quot;Import&quot; button.
</span>

<span class="member">
<hr>
<br><br><A NAME="contacts"></A>
<h3>Contacts</h3>
<br><br>These screens let you manage your contacts.

In the contact list (Menu: Personal > Contacts) you can do various actions by
selecting with your mouse one of the following from the list:
<ul>
	<li><b>Login name - Member name:</b> Open the members profile page.
	<li><b>e-mail:</b> send an e-mail to this member.
	<li><b><img border="0" src="${images}/edit.gif" width="16" height="16">&nbsp;</b>
	View/Add/edit additional information about this member.
	<li><b><img border="0" src="${images}/edit_gray.gif" width="16"
		height="16">&nbsp;</b> If the icon has no color it means that the
	information field does not contain any information.
	<li><b><img border="0" src="${images}/delete.gif" width="16"
		height="16">&nbsp;</b> Delete the member from your contact list.
</ul>
In the beginning, you don't have any contacts in this list. You can add contacts
in two ways:
<ul>
	<li>Use the &quot;<a href="#add_contact"><u>add new contact</u></a> &quot;
	window below.
	<li>by first performing a <a href="${pagePrefix}#search_member_by_member"><u>search
	on members</u></a> (&quot;Menu: Search&quot;). In the list with search results, you can
	enter the <a href="${pagePrefix}profiles"><u>profile</u></a> of a member by
	clicking on the member's name. In the profile, you should click &quot;add to
	contacts&quot; in order to add the member to your contact list.
</ul>
<hr class="help">

<br><br><A NAME="add_contact"></A>
<h3>Add contact</h3>
Here you can add a new contact to your contact list. You can do this by typing
the login or name in the (auto complete) fields and click &quot;Submit&quot;.
<hr class="help">

<br><br><A NAME="contact_note"></A>
<h3>Contact note</h3>
In this page you can insert additional information about a user. This
information is only visible to you and will be deleted if you remove the member
from your contact list.
<hr class="help">

<a name="contact_us"></a>
<h3>Contact us</h3>
This page contains the contact address if you have questions about the software.
<hr class="help">
</span>

</div> <%--  page-break end --%>

<br><br>
