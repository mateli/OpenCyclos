<div style="page-break-after: always;">
<span class="admin"> <a name="top"></a>
<br><br>Cyclos allows you to change the User
Interface by modifying any cyclos system files. In addition, it is
possible to package up modifications as .theme files, and to share them
with other Cyclos users.<br>
This means that in practice, you can control basically everything about
the pages you or your members will see in their browser: the contents
and the looks can be manipulated.
<a name="type_list"></a> You can customize the following types of files:
<ul>
	<li><b><a href="#statics"><u>Static files</u></a> </b>
	<li><b><a href="#helps"><u>Help files</u></a> </b>
	<li><b><a href="#jsp"><u>Application Pages</u></a></b>
	<li><b><a href="#css"><u>CSS files</u></a></b>
	<li>besides these, you can also customize <a href="#images"><u>images</u></a>.	
	<li><b><a href="#themes"><u>Themes</u></a>:</b> allow you to
	switch to another pre-defined layout without the hassle of changing all
	kinds of files.
</ul>
<b>Important:</b> please note that editing these kind of files can be a
complicated job. You will need knowledge about css and html to do so.
<br><br>Note that you can not only set customized files at the system
level (which is dealt by this help file); you can also set customized
files at <a href="${pagePrefix}groups#manage_group_customized_files"><u>group
level</u></a> and even at <a
	href="${pagePrefix}groups#manage_group_filter_customized_files"><u>group
filter level</u></a>.

<br><br><i>Where to find it?</i><br>
Content Management at system wide level can be reached via the Main
Menu, item &quot;Content Management&quot;.<br>
For doing content management at group level, you should go to the <a
	href="${pagePrefix}groups#manage_groups"><u>manage groups
window</u></a> and click on the <img border="0" src="${images}/edit.gif"
	width="16" height="16">&nbsp; edit icon of a group. This subject
is covered in the groups help file. <br>
For doing content management at group filter level, go to &quot;Menu:
Users & Groups > group filters&quot;, and click the <img border="0"
	src="${images}/edit.gif" width="16" height="16">&nbsp; edit icon
of a group filter. Again, this subject is covered in the groups help
file.
<br><br><i>How to get it working?</i><br>
You will need to set the <a
	href="${pagePrefix}groups#manage_group_permissions_admin_system"><u>
admin permissions</u></a> for the menu items on content management to show up.
There are several items which affect the visibility of items in the
&quot;Menu: Content Management&quot;; the following blocks apply:
<ul>
	<li>&quot;Customized images&quot;
	<li>&quot;System wide customized files&quot;
	<li>&quot;Themes&quot;
</ul>
<hr>


<a name="statics"></a>
<h3>Static Files</h3>
These are complete pages in the application which can be customized to
your organization. <br>
To modify these files knowledge of html is needed. Take care that you
leave html taqs complete and that you don't modify content within
application tags (tags are everything within &lt; and &gt;).
<br><br>The function permits to modify the following static files (this
list may be out of date, as more files may have been added):
<ul>
	<li><b>contact.jsp:</b> Page show in the menu of the member
	section: &quot;Menu: Help > Contact&quot;; should be giving information
	on how to contact your organization.
	<li><b>general_news.jsp:</b> General news message shown in the <a
	href="${pagePrefix}home#home_news"><u>general news (message
	board)</u></a> window at the home page in of the member section of Cyclos.
	<li><b>login.jsp:</b> Layout of the login page (the text of the
	login page can be modified in the translation function).
	<li><b>member_about.jsp:</b> Page shown in the menu of the member
	and admin section: &quot;Menu: Help > About&quot;.
	<li><b>posweb_footer.jsp:</b> Page footer of the external payment
	module posweb. (can be customized by member)
	<li><b>posweb_header.jsp:</b> Page header of the external payment
	module posweb. (can be customized by member)
	<li><b>posweb_login.jsp:</b> Login page header of the external payment
	module posweb.
	<li><b>top.jsp:</b> General heading of the application.
	<li><b>webshop_footer.jsp:</b> Page footer of the external payment
	module webshop.
	<li><b>webshop_header.jsp:</b> Page header of the external payment
	module webshop.
</ul>
You may also insert <a href="#insert_images"><u>images</u></a> in the
text of static files.
<hr class="help">


<a name="helps"></a>
<h3>Help files</h3>
You may want to change the help files in case you changed the <a
	href="#jsp"><u> application file</u></a> to which it belongs, or just
in case you think the original text is not clear enough.<br>
If you want to modify a help file you need to know its name and the tag
inside the file to jump to. Help files are organized per main subject;
there are about 30 help files, each of them containing the text of
several help windows, jumping to these window texts via tags.<br>
To find out the filename and tagname you have to hover with the mouse
pointer over the help icon (at the top right of each window). The status
bar of your browser will show the location: &quot;Help:
file_name#tag_name&quot;; in this case the file would be
&quot;file_name.jsp&quot; and the section (tag) in the help file would
be called &quot;tag_name&quot;. Inside the help file this tag name is
placed inside an &lt;a name="tag_name"&gt;...&lt;/a&gt; tag.<br>
Note that your browser may hide status bar messages; you should change
your browser's settings and make sure that &quot;show status bar
messages&quot; is set, in case the above doesn't show the help file
location.
<br><br>You may also insert <a href="#insert_images"><u>images</u></a> in
the text of help files, but take care with the image size as the help
window is only 300 by 400 pixels.
<hr class="help">


<a name="jsp"></a>
<h3>Application Pages</h3>
An application page is a .jsp file in Cyclos containing code to place
elements on the pages, but not containing text. In practice this means
that it is any jsp file which doesn't contain a help text. These .jsp
files define what goes where in the page which you see in you browser. <br>
The entire layout structure can be modified but it can seriously affect
the system internals and the functioning of the application. Therefore,
please use discretion in modifying the Application files. It is
recommended to modify them only for minor layout changes - for example
if you want to change the order of elements, or want an element not to
be visible in the page. Also, before you do so, please verify if your
goal cannot be achieved by normal cyclos configuration via the admin
section of the program.
<br><br>To avoid problems Cyclos will always keep a copy of the <a
	href="#edit_customized_file"><u> original page</u></a> which can be
reverted easily.
<br><br>You may also insert <a href="#insert_images"><u>images</u></a> in
the content of a jsp file.
<hr class="help">


<a name="css"></a>
<h3>CSS files</h3>
CSS files (cascading style sheet) define how the elements in a page look
like. You may want to change this if you want to give certain elements
another look, for example stronger buttons, brighter menu items, etc. To
modify the style sheet, knowledge of css file annotation is needed.
<br><br>Cyclos the following style sheet files:
<ul>
	<li><b>style.css</b><br>
	The main style sheet file for the Cyclos layout (menu, windows and top)
	<li><b>login.css</b><br>
	The style sheet for the login page
	<li><b>mobile.css</b><br>
	The style sheet for the mobile page
	<li><b>posweb.css</b><br>
	The style sheet for the posweb page
	<li><b>ieAdjust.css</b><br>
	The used to solve compatibility issues with Internet Explorer
</ul>
<br><br>In order to view the effects of the css file modification you
might have to refresh the page in your browser. This can be done by
going to the url
&quot;www.yourdomain.org/cyclos/pages/styles/style.css&quot;<br>
The content of the css will show up as text. To make sure that the new
page will be active you can refresh the page a couple of times in your
browser.
<br><br>If you want to use a new css file you can just copy the whole
content and put it over the existing style sheet (in the style sheet
edit window) and refresh the page as described above.
<br><br>If you have managed to make a nice css file you are welcome to
send it to us so that we can make it available to others. The style
sheet will be published at sourceforge and the project site.
<hr class="help">


<a name="insert_images"></a>
<h3>Images in text files</h3>
It is possible to insert images in text files like <a href="#statics"><u>
static files</u></a> and <a href="#helps"><u>help</u></a> pages. To do this the
images must be available to the application. You can check which <a
	href="#system_images"><u>system images</u></a> are available, or you
can upload an image yourself &quot;Menu: Content Management > Custom
Images&quot;. To insert a image in a page the following tag needs to be
placed at the beginning of the static file:
<br><br><i>&lt;%@ taglib
uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html"
%&gt;</i>
<br><br>And the image location will be like this:
<br><br><i>&lt;img src="&lt;html:rewrite
page="/pages/images/some_image.jpg"/&gt;"&gt;</i>
<br><br>The page only needs the 'html:rewrite' tag for the image
location. You can use the normal html arguments like; border, align,
width and height.
<hr class="help">


<a name="customized_files"></a>
<h3>Customized files</h3>
<b>Note:</b> This help file shows only general information on the
working of this form. You may want to check <a href="#type_list"><u>this
list</u></a> and follow the link of your file type for specific information and
tips on the type of file you want to customize.
<br><br>This window shows a list with files that have been <a href="#top"><u>customized</u></a>.
You have the following options:
<ul>
	<li><b>Customize new file:</b> customize a new file by clicking
	the submit button labeled &quot;customize new file&quot;.
	<li><img border="0" src="${images}/preview.gif" width="16"
		height="16">&nbsp; View lets you preview the result.
	<li><img border="0" src="${images}/edit.gif" width="16"
		height="16">&nbsp; allows you to edit a customized file.
	<li><img border="0" src="${images}/delete.gif" width="16"
		height="16">&nbsp;By clicking the delete icon, the customized
	file will disappear from the list. This means that the latest
	modification of the file will remain visible but at the first update of
	Cyclos it will be replaced by the default page.
</ul>
Note1: When a file name is displayed with a red color it means there is a
conflict. An explanation how to deal with conflicts can be found at the <a
	href="#edit_customized_file"><u>Edit customized file</u></a> help page.<br>
Note2: When you use this functionality for the first time, there might be no
customized files in your list, so no icons will be visible. This depends on the
type of files you want to customize.
<hr class="help">


<a name="edit_customized_file"></a>
<h3>Edit customized file</h3>
<b>Note:</b> This help section shows only general information on the
working of this form. You may want to check <a href="#type_list"><u>this
list</u></a> and follow the link of your file type for specific information and
tips on the type of file you want to <a href="#top"><u>customize</u></a>.
<br><br>In this window you can re-modify a file which you customized
before. As usual, click the &quot;change&quot; button to start modifying
the fields, and click &quot;submit&quot; to save your changes.
<ul>
	<li><b>File Name:</b> shows the file name. This cannot be modified.
	<li><b>Contents:</b> This area show the current content of the file. You
	can create/modify the contents of the file. You can use html and xml tags,
	which gives you a lot of possibilities, such as for example a &quot;general
	news&quot; page with a tool bar with quick links to other sections in cyclos.
	Also, javascript may be added to define behavior. However, this is
	sophisticated programming, for which you should refer to the <a
		href="http://project.cyclos.org/wiki/index.php?title=Programming_guide#JSP"><u>cyclos
	programming guide</u></a>.
	<li><b>Original contents:</b> shows you the original contents of this file
	as it was before it was customized. If you delete the customized file from the
	<a href="#customized_files"><u>list</u></a>. the orginal content will be
	applied again. Of course, you can just copy/paste the contents of the
	&quot;original contents&quot; into the &quot;contents&quot; box, in case the
	content you just defined doesn't seem to work.
	<li><b>New contents:</b> If you have updated/upgraded Cyclos and there is 
	a new version of the file, Cyclos will not replace the file with the
	new content but generate a (system) alert. The new content will be available in this area.
	<li><b>Resolve conflicts:</b> When there is a new version of a file that
	has been customized, Cyclos will generated a system alert and put the new
	version in the &quot;New contents&quot; area. Also, the name of the
	&quot;conflicting&quot; customized file will appear with a red color in the
	customized file list.<br>
	Once the conflict has been resolved and everything is working correctly you 
	can select the &quot;resolve conflict&quot; option and save the file. After
	doing this the file name will show not show up anymore in red at the file list and 
	the new content of the file version will be moved to the original content area. 	
</ul>
<hr class="help">


<a name="edit_new_customized_file"></a>
<h3>Customize (new) file</h3>
In this window you can choose a file to start <a href="#top"><u>customizing</u></a>.
Choose the file you want to customize in the &quot;select file&quot;
drop down. You may see files listed directly in this drop down, or you
may see the directories in which these files may be placed. In case of
directories and subdirectories, you can browse through the directory
tree with the &quot;Select file&quot; drop down select. The directories
will be listed in the &quot;Path&quot; field above the select box. You
can move to a higher directory by selecting the &quot;Up&quot; link next
to the select. When you reached a directory that contains files you can
select the file and its contents will show up in the text area below. <br>
Then, you may edit the file after having clicked the &quot;change&quot;
button. Save your changes by clicking the &quot;submit&quot; button.
<br><br>When you save a changed customized file the original contents
will be saved and showed below the customization. When an upgrade is
installed Cyclos will keep the customized file but it will check if
there are differences between the original content and the content of
the new file in the upgrade. If so it will place the new content below
the original content. Selecting &quot;Resolve&quot; will move the new
content to the original content area.
<br><br>When you stop customizing a file (by removing it from the list)
the original content will be used.<hr>


<a name="images"></a>
<h2>Customizing Images</h2>
You can also customize images in Cyclos. There is a set of system
images, but you can also upload your own images and start using them in
any customized file.

You can upload your images via the &quot;Menu: Content Management&quot;,
where you can choose to change <a href="#system_images"><u>system
images</u></a>, upload your own <a href="#custom_images"><u>custom
images</u></a>, or change <a href="#style_images"><u>style sheet images</u></a>.
<hr class="help">


<A NAME="system_images"></A>
<h3>System images</h3>
<br><br>This window will show a list with the current system <a
	href="#images"><u>images</u></a> in Cyclos. If you click an image
thumbnail in the list it will show the real sized image in a pop-up
window. You can replace a system image in the <a href="#images_upload"><u>
update window</u></a> below.
<hr class="help">


<A NAME="custom_images"></A>
<h3>Custom images</h3>
<br><br>This window will show a list with the custom <a href="#images"><u>images</u></a>
in Cyclos. If you click an image thumbnail in the list it will show the
real sized image in a pop-up window. The custom images can be used in
the <a href="#statics"><u>static files</u></a>, <a href="#helps"><u>help
files</u></a> , and even messages.
<br><br>You can delete an image selecting the <img border="0"
	src="${images}/delete.gif" width="16" height="16">&nbsp;delete icon.<br>
You can add a custom image in the <a href="#images_upload"><u>
update window</u></a> below.
<hr class="help">


<A NAME="style_images"></A>
<h3>Style sheet images</h3>
<br><br>Style sheet images are <a href="#images"><u>images</u></a> that
can be used in the layout of Cyclos like window headings, menu items,
buttons and backgrounds. You would use these images in a <a href="#css"><u>css
file</u></a>.
<br><br>You can delete an image selecting the <img border="0"
	src="${images}/delete.gif" width="16" height="16">&nbsp;delete icon.<br>
If you click an image thumbnail in the list it will show the real sized
image in a pop-up window. You can add a custom image in the <a
	href="#images_upload"><u> update window</u></a> below.
<hr class="help">


<A NAME="images_upload"></A>
<h3>Images upload</h3>
<br><br>In case of a <a href="#system_images"><u>system image</u></a>,
you should first select which image you want to replace by choosing the
name from the above list in the &quot;upload new&quot; drop down box.
Note that this drop down will not be visible when uploading a <a
	href="#custom_images"><u>custom image</u></a> or <a
	href="#style_images"><u>style sheet image</u></a>.<br>
Then, click the &quot;browse&quot; button and find the image you want to
upload on your local computer or network. Make sure it has the extension
jpg, jpeg, gif or png. After this click &quot;submit&quot;. The new
image will appear in the list window above.
<hr>


<a name="themes"></a>
<h2>Themes</h2>
A theme, sometimes called a &quot;skin&quot;, defines the a layout of
Cyclos (in login status topbar, left menu and function windows). The
themes function is just a quick way to switch between layouts without
the need of editing the general style sheet file and style sheet images.

The theme function can be found via the &quot;Menu: Content Management >
themes&quot;.


<A NAME="select_theme"></A>
<h3>Theme selection</h3>
<br><br>To select another <a href="#themes"><u>theme</u></a> you have to
choose one from the &quot;theme&quot; drop down box, and then click the
submit button labeled &quot;apply&quot;. You will have to refresh your
browser for the theme to show up. In some case you need to clear the
browser cache.<br>
Note that if you didn't use this functionality before, no themes might
be available, resulting in an empty drop down box. In such a case, you
will have to <a href="#import_theme"><u>import</u></a> a new theme first.
<br><br>When you click the submit button labeled &quot;Remove&quot; the
theme will be removed and custom layout modification will be deleted. So
if you made your own customization of the <a href="#css"><u>general
style sheet</u></a> or <a href="#style_images"><u>style sheet images</u></a> you
might want to <a href="#export_theme"><u>export</u></a> it first as a
theme, so it is easy to re-import it again later. The theme function
only affects style sheets; it doesn't affect the <a href="#statics"><u>static
files</u></a> and <a href="#helps"><u>help files</u></a>.
<hr class="help">


<A NAME="import_theme"></A>
<h3>Import new theme</h3>
<br><br>With this function you can import a <a href="#themes"><u>theme</u></a>.
A Cyclos theme has a .theme extension. Just use the &quot;browse&quot;
button to browse to the location of the file and click &quot;Submit&quot;.
<hr class="help">


<A NAME="export_theme"></A>
<h3>Export current settings as theme</h3>
<br><br>If you made your own <a href="#themes"><u>theme</u></a> (by
modifying the <a href="#css"><u>style sheet file</u></a> and/or the <a
	href="#style_images"><u>style sheet images</u></a>) you can export the
theme as a .theme file.<br>
This function will alow you to export the current active theme. Just
fill in the fields and click the &quot;submit&quot; button below. <br>
You can also export a subset of the theme files, by checking the
checkboxes below. Three options are available:
<ul>
	<li><b>System</b> These are the core <a href="#jsp"><u>.jsp</u>
	</a> and <a href="#css"><u>.css</u>
	</a> files</li>
	<li><b>Login Page</b> This is the initial login page</li>
	<li><b>Mobile</b> These are the pages for access by mobile phone</li>
</ul>
<br><br>
<i>If you do develop your own theme, please consider submitting them
to the Cyclos development team.</i> We can then make it available to other
users of Cyclos.
<hr>


<A NAME="infotexts"></A>
<h2>Info texts</h2> Info texts are texts stored in Cyclos that and can
be retreived via webservices. A typical use is the SMS module. Users can
send a keyword by SMS, for example &quot;promocion&quot; and receive a
corresponding text. The info texts can be registered and managed in
Cyclos. <br> <br> Info texts can have a title and message body. This is
more common if they are used to be included in websites. Details of all
the info text properties can be found at the <a
href="${pagePrefix}content_management#infotexts_edit"><u>edit
info text</u> </a> page.<br> <br> Information about how to retreive info
texts via web services can be found at the <a
href="http://project.cyclos.org/wiki/index.php?title=Web_services#Info_texts"
target="_blank"><u>Wiki</u> </a>.
<hr class="help"> 


<A NAME="infotext_search"></A>
<h2>Search info texts</h2> At this page you can search for <a
href="${pagePrefix}content_management#infotexts"><u>info texts</u>
</a> and create a new one by selecting the &quot;create new
button&quot;.
<hr class="help">


<A NAME="infotexts_result"></A>
<h2>Info texts result</h2> This page shows the result from the search.
You can select the edit icon <img border="0" src="${images}/edit.gif"
width="16" height="16"> to modify modify the info text and the delete
icon <img border="0" src="${images}/delete.gif" width="16" height="16">
to delete the corresponding info text.
<hr class="help">


<A NAME="infotexts_edit"></A>
<h2>New / edit info text</h2>
A this page you can define the rules and the content of the info text.
The following options are available.
<ul>
	<li><b>Key: </b>This is the key that will be used to retreive the
	corresponding text fields (title and body). More than one keys are
	permitted. In that case the addtional keys will works as aliases.
	<li><b>Title: </b>This is the content text that can be retreived.
	In case of short texts (e.g. SMS) this is the only text used.
	<li><b>Body text: </b>For functions where both title and body text
	are used (e.g. websites) the body text will be defined here.
	<li><b>Validity</b> This settings defines the period when the info
	text is active (and can be retreived)
	<li><b>Enabled</b> Here you can quickly enable and disable the info
     text. When disabled (not checked) the info text cannot be retreived.
</ul>


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

</span>
