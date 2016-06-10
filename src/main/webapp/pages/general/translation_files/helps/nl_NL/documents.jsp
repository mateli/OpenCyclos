<div style="page-break-after: always;"
<a name="top"></a>
<br><br>Documents are pages with information which can
show up in the Member section of Cyclos. The member can select a document from a
list. There are two types of documents. &quot;Static&quot; and &quot;Dynamic
documents. Static documents are just files like a pdf file and can be assigned
to an individual member or a member group. Dynamic documents are html documents
that can be assigned to one or more groups. These documents can get a personal
flaw for the member as it can retrieve fields from the profile of the member
viewing it.
<span class="admin">
A typical custom document would be a loan contract or any kind of
request document which the member can use to request something from the
administration.<br>
A Dynamic document can be shown directly when a member opens it; in contrast to that,
it is also possible to first present a form which needs to be filled in by the Member.
When the member submits the form the result document can include the input the
member entered, as well as the member profile fields.

<br><br><i>Where to find it?</i><br>
Documents can be found under the &quot;Menu: Content Management >
Documents&quot;. An example of creating a dynamic document can be found in the
cyclos wiki, under the section &quot;configuration - custom documents&quot;.
<br>
Existing individual member documents can be reached from the <a
	href="${pagePrefix}profiles"><u> profile</u></a> of a member (block
&quot;member info&quot;).

<br><br><i>How to get it working?</i><br>
Before you can create documents, you will need to set the <a href="${pagePrefix}
groups#manage_group_autorisaties_admin_member"><u>autorisaties</u></a> first. This can be done
under the block &quot;Documents&quot;, via several checkboxes.
Once you have these autorisaties, you can create new documents via the &quot;Menu:
Content Management > Documents&quot;.<br>
<br><br>For each created document, the visibility has to be set in the group
<a href="${pagePrefix}groups#manage_group_autorisaties_member"><u>autorisaties</u></a>, block
&quot;documents&quot;. So this means that documents are assigned to certain member groups.
It is possible to set the document only viewable by administrators, only by
administrators and brokers, and by administrators, brokers and the member self
(members can never see each others documents).<br>
<b>Note:</b> There exists no such thing as admin documents in cyclos.
</span>
<span class="member">
<br><br><i>Where to find it?</i><br>
Documents can be viewed at &quot;Menu: Personal >
documents&quot;.
</span>
<hr>

<span class="admin"> <a name="document_list"></a>
<h3>Custom document list</h3>
This pages shows a list with custom <a href="#top"><u>documents</u></a> that
have been defined in the system up to now. Besides the name of the document, the
list shows the following:
<ul>
	<li><b>type:</b> shows the <a href="#top"><u>type</u></a> of
	the document.
	<li><img border="0" src="${images}/edit.gif" width="16" height="16">&nbsp;
	click the edit icon of a document to modify it.
	<li><img border="0" src="${images}/view.gif" width="16" height="16">&nbsp;
	click the view icon to view the result.
	<li><img border="0" src="${images}/delete.gif" width="16" height="16">&nbsp;
	clicking the delete icon will delete the document.
</ul>
To create a new document, you should use either one of the two buttons at the
bottom of the window (&quot;new dynamic document&quot; or &quot;new static
document&quot;)
<hr class="help">
</span>

<span class="admin"> <a name="new_edit_static_document"></a>
<h3>Insert/modify new static document</h3>
This allows you to insert a <a href="#top"><u>new static
document</u></a>. The form is very simple: just enter a name and a description for the
document, and fill in the file name in the &quot;upload file&quot; edit. You may
want to use the &quot;browse&quot; button for this.<br>
When done, click the &quot;submit&quot; button to save the file.
<br><br>The document file can be in any format. In case you chose to change an
existing file, the current file is placed under the link &quot;current
file&quot;; you can click this link to view the present version of the document.
<br><br><b>Beware</b>: Just creating a document doesn't mean that your
members/users can view it. After document creation, you should set the <a
	href="${pagePrefix}groups#manage_group_autorisaties_member"><u>autorisaties
for the members</u></a> to view the documents, by selecting the new document in a drop
down under the document section of the autorisaties.
<hr class="help">
</span>


<span class="admin"> <a name="new_edit_dynamic_document"></a>
<h3>Insert/modify new dynamic document</h3>
This allows you to insert a <a href="#top"><u>new dynamic
document</u></a>. The form has the following elements:
<ul>
	<li><b>Name:</b> The name of the document.
	<li><b>Description:</b> The description of the document (only for
	administration uses)
	<li><b>Form page:</b> It can be that the document first needs some user
	input before printing. On this page you can write an html page with a form to
	request the necessary user input. If you don't need this user input, you may
	leave this blank.
	<li><b>Document page:</b> Here you can write the document page in html
	format. If you defined a form page in the edit box above, then you may include
	the user input from that page. The document page will be opened in a pop-up
	window with a print and close button. You can also insert images. You will need
	to upload them first in the &quot;<a
		href="${pagePrefix}content_management#custom_images"><u> Custom images</u></a>&quot;
	section.
</ul>
<br><br>Note 2: There are examples available of dynamic documents in the
cyclos wiki, under the section &quot;configuration - custom documents&quot;. See 
<a href="http://project.cyclos.org/wiki">project.cyclos.org/wiki</a>.
After document creation, you should set
the <a href="${pagePrefix}groups#manage_group_autorisaties_member"><u>autorisaties
for the members</u></a> to view the documents, by selecting the new document in a drop
down under the document section of the autorisaties.
<hr class="help">
</span>

<a name="member_document"></a>
<h3>Download document</h3>
This window gives a list of documents which the administration has made
available to members. These documents can be downloaded and printed.
<br><br>A document is usually an organizational document. If specified by the
administration a document can show first a form that requires some additional
input by you, to be included in the document. <span class="broker admin">
For admins and brokers, also the type of the document is listed. Static and
dynamic documents can only be viewed from this window (you should go to the
&quot;Menu: content management > documents&quot; to manage them); member
documents however are managed from this window. In such a case, you have the
following options:
<ul>
	<li><img border="0" src="${images}/view.gif" width="16" height="16">&nbsp;
	allows you to view the document
	<li><img border="0" src="${images}/edit.gif" width="16" height="16">&nbsp;
	allows you to modify the document
	<li><img border="0" src="${images}/delete.gif" width="16" height="16">&nbsp;
	allows you to delete the document.
</ul>
</span><hr class="help">

<span class="broker admin"> <a name="edit_member_document"></a>
<h3>Insert / modify member document</h3>
With this window, you can define a new &quot;Static&quot; document for an individual member.
This can be any type of file like a pdf or a picture. If you want to modify you can just overwrite 
the previous document should click the
&quot;change&quot; button first in order to make changes. When done, use the
&quot;submit&quot; button to save your changes.
<ul>
	<li><b>Name:</b> just give a descriptive name
	<li><b>Description:</b> only visible for admins
	<li><b>Visibility:</b> here you can define select to which user types this
	document will be visible. If you select &quot;member&quot;, the member can see
	the document as well. If broker is selected only the broker (and admin with
	autorisaties) can see the document. And, obviously, if
	&quot;administrators&quot; is selected only administrators can see the
	document.<li><b>Current file:</b> is the present document file. You may click the
	link to view it. This will not be visible if you use this window to create a
	new member document.
	<li><b>Upload file:</b> Just enter the filename here with full path. You
	may want to use the &quot;browse&quot; button for this.
</ul>
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

