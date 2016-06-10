<div style="page-break-after: always;">
<a name="top"></a>
<br><br>Messages in cyclos can be used on many different occasions
between users of all types. There are many flexible features
available for sending messages between members and between groups. It is also possible
to define different categories of messages, where certain groups can be given access
to certain categories of messages.<br>
Messages are send via an internal messaging system in cyclos. This means that
the recipient sees the message you sent as soon as he logs in. Cyclos can be <a
	href="${pagePrefix}preferences#notifications"><u>configured</u></a> that these messages
are also sent via e-mail - however, this is the responsibility of the 
receiver to allow this or not. If you want to be sure that a message is send by e-mail, 
you should use e-mail directly. This can however be done via the cyclos-interface,
which contains a &quot;send e-mail&quot; button in each member profile.

<i>Where to find it?</i><br>
<span class="member">You can access messages via the &quot;Menu: Personal
> messages&quot; </span>
<span class="admin">You can access messages via the &quot;Menu: Messages
> messages&quot;. </span>
<span class="broker">To send messages as a broker (to all your members), you can go to 
the &quot;Menu: Brokering > message to members&quot; option.
</span>
Another way to send messages is to go to the
<a href="${pagePrefix}profiles"><u>profile</u></a>
of a member, and then click the &quot;send message&quot; button
<span class="admin">in the &quot;Member info&quot; section.

<br><br><i>How to get it working?</i><br>
You will need to set the permissions to allow messages. For admins, you should set
<a href="${pagePrefix}groups#manage_group_permissions_admin_member"><u>permissions</u></a>
under the block &quot;Messages&quot;; this allows you to set various permissions for sending
messages to members.<br>

The <a href="${pagePrefix}groups#manage_group_permissions_member"><u>permissions for members</u></a>
concerning messages can be found under the block entitled &quot;Messages&quot;.<br>
Brokers have an additional set of <a href="${pagePrefix}groups#manage_group_permissions_broker"><u>
permissions</u></a> concerning sending messages to their brokered members, which can be 
found under the block &quot;personal messages&quot;.
</span>
<hr>


<A NAME="messages_search"></A>
<h3>Messages list</h3>
In this page you can select to view the <a href="#top"><u>messages</u></a> that you sent or received.
With the drop down select you can view the the &quot;Inbox&quot;, &quot;Sent Items&quot;
or &quot;Trash&quot; folder.<br>
<span class="admin">You can also select specific categories in the
&quot;Category&quot; drop-down (Note: you need to have at least view permissions
for the categories in order for them to show up in the list).<br></span>
By clicking the &quot;Advanced&quot; button you can search for messages with
keywords, or the member that the message came from or was sent to. After filling
in the search items you have to click the &quot;Submit&quot; button.
<br><br>
In order to send a new message you have to click the submit button labeled &quot;Send a new
message&quot;.
<hr class="help">


<A NAME="messages_search_result"></A>
<h3>Messages search result</h3>
This page shows the <a href="#top"><u>messages</u></a> based on the criteria specified in the
<a href="#messages_search"><u>window above</u></a>.
<br><br>The icon will show the status of the message, either:
<ul>
	<li><img border="0" src="${images}/message_unread.gif"
		width="16" height="16">&nbsp;(unread)
	<li><img border="0" src="${images}/message_read.gif"
		width="16" height="16">&nbsp;(read)
	<li><img border="0" src="${images}/message_replied.gif"
		width="16" height="16">&nbsp;(replied)
	<li><img border="0" src="${images}/message_removed.gif"
		width="16" height="16">&nbsp;(removed)
</ul>
You can perform the following actions:
<ul>
	<li>You can open a message by selecting the title.
	<li>You can select multiple messages with their checkboxes, and apply an
	action on those messages by selecting the action in the drop down select below
	the messages window.
	<li>You can remove single message by selecting directly the delete icon (
	<img border="0" src="${images}/delete.gif" width="16" height="16">&nbsp;).
</ul>
<hr class='help'>


<A NAME="messages_send"></A>
<h3>Send messages</h3>
<span class="admin"> 
	Here you can send a <a href="#top"><u>message</u></a> to a member. If you send the
	message from within the message function (&quot;Menu: Messages > Messages&quot;)
	you have to specify member in the login and/or member fields (with auto
	completion). You will have to specify a category for the message.<br>
	<br><br>If you send a message to a group, the form will change, and their will be 
	other edits to use. You will first have to select one or more groups from the
	&quot;groups&quot; multi-drop down; all of the members in the groups you selected
	will receive the message.
	<br>When sending to a group you will have the option to send in plain format
	or in Rich Text. The latter allows you to use special features for layout, such
	as fonts, pictures, etc. For this you have to choose the &quot;Rich text&quot;
	option in the radio buttons; Then the Rich Text editor will become visible, allowing
	you to use various buttons for layout (just try them out and play a bit around with it).
	You can also use plain html, by clicking the &quot;source&quot; button of the rich text
	editor.<br>
	If you want to include an image you will have to upload it
	first in the &quot;( Menu: Content management > <a href="${pagePrefix}content_management#custom_images"><u>
	Custom images</u></a > )&quot; section.<br>
	As admin, you cannot send a message to another administrator.
</span>
<span class="member">
	Here you can send a <a href="#top"><u>message</u></a> to either a member or the administration.
</span>
<span class="broker">
	Here you can send a message to:
	<ul>
		<li>a specific member
		<li>all your registered members
		<li>or the administration.
	</ul> 
</span>
<span class="member">
	The form is pretty straightforward. If you choose &quot;member&quot; you have
	to specify the member either by name or by login name; if you fill in the
	login name the real name is automatically provided.<br>
	If &quot;Administration&quot; is chosen, you should also specify a category for your
	message.<br>
</span>
<span class="broker">
	If &quot;My registered members&quot; is chosen you
	have the option to write the message in &quot;Plain&quot; or in &quot;Rich Text&quot; format.
	The latter allows you to use special features for layout, such
	as fonts, pictures, etc. For this you have to choose the &quot;Rich text&quot;
	option in the radio buttons; Then the Rich Text editor will become visible, allowing
	you to use various buttons for layout (just try them out and play a bit around with it).
	You can also use plain html, by clicking the &quot;source&quot; button of the rich text
	editor.<br>
</span>
After having finished the message, click &quot;submit&quot; and it will be send.
The message will appear in your &quot;Sent items&quot; box.
<hr class='help'>


<span class="broker">
<a name="messages_send_brokered_members"></a>
<h3>Send messages</h3>
	Here you can send a message to all your registered members. The form is pretty straightforward.
	<br><br>You
	have the option to write the message in &quot;Plain&quot; or in &quot;Rich Text&quot; format.
	The latter allows you to use special features for layout, such
	as fonts, pictures, etc. For this you have to choose the &quot;Rich text&quot;
	option in the radio buttons; Then the Rich Text editor will become visible, allowing
	you to use various buttons for layout (just try them out and play a bit around with it).
	You can also use plain html, by clicking the &quot;source&quot; button of the rich text
	editor.<br>
After having finished the message, click &quot;submit&quot; and it will be send.
The message will appear in your &quot;Sent items&quot; box.
<hr class="help">
</span>


<A NAME="messages_view"></A>
<h3>View Messages</h3>
This is the <a href="#top"><u>message</u></a>. You have the option to remove the message to the
&quot;Trash&quot; box by clicking the submit button labeled &quot;Move to trash&quot;. Messages
in this trash box can always be reread by just opening the &quot;trash&quot; box
in your <a href="#messages_search"><u>messages overview</u></a>.<br> 
You can reply to the message by clicking the &quot;Reply
button&quot; (if the message was sent by you there will not be a reply button).
<hr>


<a name="categories"></a>
<h2>Message categories</h2>
<a href="#top"><u>Message</u></a> categories allow you to better manage messages send to the administration.
Categories only exist for message between members and the
administration. Member to member messages do not use the categories.

<hr class='help'>


<span class="admin">
<A NAME="message_categories"></A>
<h3>Message categories</h3>
This page will list the <a href="#categories"><u></u></a> for <a href="#top"><u>messages</u></a>.
<br><br>
<ul>
	<li><img border="0" src="${images}/edit.gif" width="16" height="16">&nbsp;
	allows you to modify the category.
	<li><img border="0" src="${images}/delete.gif" width="16" height="16">&nbsp;lets
	you delete the category. This is only possible when it hasn't been used yet.
	<li>Use the submit button labeled &quot;insert new message category&quot;
	to create a new category.
</ul>

</span>


<span class="admin">
<a name="edit_message_category"></a>
<h3>Modify Message Category</h3>
This window allows you to change the name of the <a href="#categories"><u>
category</u></a> for <a href="#top"><u>messages</u></a>. 
Please choose something descriptive, as this is what members
will see in their <a href="#messages_send"><u>send message window</u></a> .<br>
As always, you should click &quot;change&quot; in order to make your changes;
when finished, click the &quot;submit&quot; button to save your changes.
<hr class="help">
</span>


<span class="admin">
<a name="new_message_category"></a>
<h3>Insert New Message Category</h3>
This window allows you to create a new <a href="#categories"><u>
category</u></a> for <a href="#top"><u>messages</u></a>.
Just enter some descriptive name, and
click the &quot;submit&quot; button to save your changes.<br>
Members will see this name in their <a href="#messages_send"><u>
send message window</u></a> . 
<hr class="help">
</span>


<span class="broker admin">
<a name="sms_mailings"></a>
<h3>SMS mailings</h3>
In this window you can search for SMS mailings. These are mailings send to groups are individual users.
It is possible to search on the history of group or individual SMS mailings. 
<hr class="help">
</span>


<span class="broker admin">
<a name="sms_mailings_results"></a>
<h3>SMS mailings results</h3>
This pages shows the result of SMS mailing search. The recipient column will either show the member or
group the the mailing was sent to. The other columns are self explanatory.  
<hr class="help">
</span>


<span class="broker admin">
<a name="send_sms_mailing"></a>
<h3>Send SMS mailing</h3>
In this window you can send an SMS mailing. When you send a mailing to  
</span>
<span class="broker">your registered users. </span>
<span class="admin">one or more groups. </span>
<span class="broker admin"> 
You can define if the mailing is &quot;paid&quot; or &quot;free&quot;. A paid mailing means that
the user will be charged in local units, or if the user has SMS credit this credit will be used
first. A free mailing means that the user will not be charged for the mailing. Usually commercial
mailings will be free and other organisational mailings can be paid.<br> A user can define in the
<a href="${pagePrefix}preferences#notifications_preferences"><u>notification preferences</u></a>
if he/she will want to receive paid and/or free mailing.<br>
Individual messages to members are always free of charge (for the user).<br><br>
It is possible include variables in the messages by using the drop down &quot;Variables&quot;.
The variables will only appear after selecting groups, or in case of individual mailing a member 
needs to be selected. This is because different members (groups) can have different profile fields. 
The possible variables can be any profile field and specific information related to the member such as 
&quot;account balance&quot; and &quot;credit limit&quot;.  
When selecting the &quot;Add&quot; button the variable will be inserted in the message text field below.
An example of a mailing would be:<br> &quot;Hello #name#,<br>Your account balance is #balance#&quot;.
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