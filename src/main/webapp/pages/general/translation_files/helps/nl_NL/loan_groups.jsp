<div style="page-break-after: always;"
<a name="top"></a>
<br><br>Loan Groups allow members to be collectively
responsible for a loan.<br>
Giving a loan to a loan group means that the responsible member for the loan
group will receive the loan. All members can view the loan and also repay the
loan.
<i>Waar te vinden</i>
<br>
Loan groups can be accessed via
<span class="admin"> &quot;Menu: Users & Groups: Loan groups&quot;. <br>
To view this item the administrator needs to have the correct <a
	href="${pagePrefix}groups#manage_group_autorisaties_admin_member"><u> autorisaties</u></a>
(block loan groups) <br><br>

<i>How to get it working.</i><br>
Once you created a loan group you can add members to it as described in below in
<a href="#create_loan_group"><u>create loan group</u></a> &quot;<br>
In order to give a loan to a loan group a loan must exists and the admin must
have the <a href="${pagePrefix}groups#manage_group_autorisaties_admin_member"><u>
permission</u></a> to give the loan (in autorisaties block &quot;loans&quot;)<br>
There are additional <a href="${pagePrefix}groups#edit_member_group"><u>
loan groups settings</u></a> that can be defined per member group.<br>
You can either give a loan via &quot;Menu: Users & Groups: Loan groups&quot; >
edit loan group > Give loan or via the <a
	href="${pagePrefix}profiles#actions_for_member_by_admin"><u> </u></a> (block Loans > Grant
loan) </span>


<span class="member">&quot;Menu: Account > Loan groups&quot;.</span>
<hr>

<span class="admin">
<a NAME="search_loans_group"></a>
<h3>Search loan groups</h3>
This is the search page for <a href="#top"><u>loan groups</u></a>.<br>
You can search by loan group name,
description or member that belongs to the group (by login name of the member
or real name of the member).
<br><br>After you filled in the necessary fields, you should click &quot;search&quot;
and the groups will show up in the <a href="#search_loans_group_result"><u>
search result window</u></a> below.
<br><br>
A new loan group can be created by clicking the &quot;<a href="#create_loan_group"><u>
Create loan group</u></a> &quot;
button.
<hr class="help">
</span>

<span class="admin">
<a name="create_loan_group"></a>
<h3>New loan group</h3>
In this window you can create a new loan group. Just enter a name for the new
group, and a description, and click &quot;submit&quot;.
<br><br>The newly created group will show up in your next screen, which is the
<a href="#search_loans_group_result"><u>loan search result</u></a> window. At 
first creation, the group is still empty. You can add members to it by modifying
the loan group (click the <img border="0" src="${images}/edit.gif" width="16" height="16">&nbsp;
icon) in the loan search result window). 
<hr class="help">
</span>

<span class="admin">
<a name="search_loans_group_result"></a>
<h3>Loan group search results</h3>
Here are the results of the <a href="#search_loans_group"><u>loan group search</u></a>.
The <a href="#top"><u>loan group</u></a> name and description
are displayed. In addition, you can use the
<img border="0" src="${images}/edit.gif" width="16" height="16">
&nbsp; edit icon; which will bring you to a window where you can 
edit the properties of the loan group, add members, or
manage loans of the group.
<br><br>You can use the
<img border="0" src="${images}/delete.gif" width="16" height="16">
&nbsp; Delete icon to remove a group. This is only possible if the group
does not have any open loans assigned.
<hr class="help">
</span>

<span class="admin">
<a name="loan_group_members_by_admin"></a>
<h3>Members in this loan group</h3>
The is window displays the members in a <a href="#top"><u>loan group</u></a>.
The name and login name are displayed.<br>
To remove a member from the group, click the delete <img border="0"
	src="${images}/delete.gif" width="16" height="16"> &nbsp; icon.<br>
To add a member to the group, enter the login name or name (the name is
autocompleted) in the edit boxes and click the &quot;Add&quot; button.
<hr class="help">
</span>

<span class="admin">
<a name="loan_group_detail_by_admin"></a>
<h3>Modify loan group</h3>
This window gives access to information about the <a href="#top"><u>loan group</u></a>.
You can perform the following actions:
<ul>
	<li><b>Change:</b>To change the name or description, click this button. After having
	changed these, click &quot;submit&quot; to save the changes.
	<li><b>View loans:</b> click this button to get an overview of the existing 
	<a href="${pagePrefix}loans"><u>
	loans</u></a> for this group.
	<li><b>Give loan:</b> click this button to give a loan to the group. The responsible
	member for the group will receive the loan. If the autorisaties have been set, all
	members of the loan group can view the loan, and repay it. 
</ul>
<br><br>
<font color="#FF0000">Note:</font> When accessing the loan group via the
member management function the admin will only have &quot;view&quot;
autorisaties. The actions mentioned here can only be done directly
from the loan group management page (&quot;Menu: Users & Groups > loan groups&quot;).
<hr class="help">
</span>

<a name="search_loans"></a>
<h3>Loans of Group ...</h3>
Here you can get an overview of the loans of the <a href="#top"><u>
loan group</u></a>. The form is very simple: you can only select
one of the two radio buttons to view either &quot;open&quot; or
&quot;closed&quot; loans. 
<hr class="help">

<span class="member">
<a NAME="member_loan_groups_by_member"></a>
<h3>My loan groups</h3>
Here you can see the <a href="#top"><u>loan groups</u></a> that you belong to. To view more information
about a loan group, click the
<img border="0" src="${images}/view.gif" width="16" height="16">
&nbsp; View icon.
<hr class="help">
</span>

<span class="admin">
<a NAME="member_loan_groups_by_admin"></a>
<h3>Loan groups of member</h3>
Here you can see the <a href="#top"><u>loan groups</u></a> that the member belongs to. 
To view more information about a loan group, click the
<img border="0" src="${images}/view.gif" width="16" height="16">
&nbsp; View icon.<br>
To abolish a loan group, click on the delete 
<img border="0" src="${images}/delete.gif" width="16" height="16">&nbsp;icon. This
is only possible if there are no open loans for the loan group.<br><br>
Note that you cannot view loans here. For that, you should go to
the member profile > view loans, or to &quot;Menu: accounts > manage loans&quot;.
<hr class="help">
</span>

<span class="member">
<a name="loan_group_detail_by_member"></a>
<h3>Loan group details</h3>
Here the loan group name and description are displayed.<br>
The members in this group are displayed in the <a href="#loan_group_members_by_member"><u>
window below</u></a>. 
<hr class="help">
</span>

<span class="admin">
<a name="add_member_loan_groups"></a>
<h3>Add member to loan group</h3>
With this window you can add a member to a <a href="#top"><u>loan group</u></a>.
A member can belong to more than one loan group. The form is very simple:
Just select a loan group, and click &quot;submit&quot;. 
<hr class="help">
</span>

<span class="member">
<a NAME="loan_group_members_by_member"></a>
<h3>Members in this loan group</h3>
Here the real name and login name of the members of this loan group are
displayed. You can click them to go to their <a href="${pagePrefix}profiles"><u>
profile</u></a>.
You cannot view your loans (or the loans to the loan group) here. Therefore, 
you should go to &quot;Menu: account > loans&quot;; this will also display the
loans to the loan group you are a member of.
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