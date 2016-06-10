<div style="page-break-after: always;">
<br><br>A member can publish advertisements and search for
them. An advertisement has various fields and it is possible
to upload one or more pictures. The advertisements can be published
automatically on the organization web site (if this option is enabled).

<span class="admin"> <i>Where to find it.</i><br>
Advertisements have a special main menu item (called &quot;advertisements&quot;,
not surprisingly). Here you can perform searches on the advertisements
(&quot;Menu: Advertisements > Products & services&quot;) or manage the <a
	href="${pagePrefix}advertisements#manage_categories"><u> categories</u></a>.
Advertisements of specific users can be accessed via their profiles. </span>


<span class="member">
<i>Where to find it.</i><br> 
You can access advertisements in the following ways:
<ul>
	<li><b>Searching:</b> Advertisements can be searched via the
	&quot;Menu: Search > Products & Services&quot;.
	<li><b>Manage your advertisements:</b> You can manage your own 
	advertisements via the &quot;Menu: Personal > advertisements&quot;.
	<li><b>Ads interests:</b> You can specify your ads interests at
	&quot;Menu: Preferences > Ad Interests&quot;
	<li><b>Advertisements of another member:</b> can be accessed via
	the <a href="${pagePrefix}profiles"><u>profile</u></a> of that member. 	
</ul>
</span>
<hr>


<a NAME="advertisement_search"></a>
<h3>Advertisement search</h3>
The advertisement search window allows you to search advertisements throughout
the system. It has a keyword search and the option to
search either in &quot;offers&quot; (default) or &quot;wants&quot;. As with any
search window in cyclos, any search field left blank will return all possible values
for that search field. This means that if you click the &quot;search&quot;
button without specifying any keyword, all offers (or wants) available will be
returned.<br><br>The advertisement search will search in all advertisement fields (so
in Title, description, owner, category level etc). You can use more than one
keyword in the search.<br>
Various &quot;operators&quot; can be used with the member (and advertisement)
search. The most commonly used are:
<ul>
	<li><b>*</b> The asterisk &quot;wildcard&quot; allows you to search for part of
	words. For example a search on ba* will return all ads that have the letter
	combination ba in one of its fields like for example Baker, bargain, bass
	gitar.
	<li><b>- not</b> A search with a keyword preceded directly by a minus sign
	or preceded by &quot;not&quot; and a space will return results that do not
	contain that keyword.
	<li><b>or</b> The search should return results with either the word
	preceding &quot;or&quot; or the keyword behind it.
	<li><b>and</b> The search should return results with both the word
	preceding &quot;and&quot; and the keyword behind it.
</ul>
If you want more search options, you can click the &quot;Advanced&quot; 
button; then you can narrow down your search by selecting various search
options like price range, category and publishing date. The options are all
pretty straightforward and self-explanatory.
<span class='admin'>
<br>The status drop down allows you to search on the <a href="#ad_status"><u>
ad status</u></a>.
The &quot;group&quot; drop down allows you to search on a
<a href="${pagePrefix}groups#group_filters"><u>group filter</u></a>, 
whereas the &quot;permission groups&quot; drop down allows you to specify
a group directly. Of course, these two items shouldn't contradict each other.
Often, the group filter is used to specify a set of groups residing in the
same geographical community, but this of course depends on you local configuration
and the definition of you group filters.
</span>
<br><br>
In stead of searching for a keyword, you can also browse a advertisement category
via the window below titled &quot;browse by category&quot;. If this window is not visible,
you should click on the link &quot;View categories&quot; and it will be displayed.
In this window, all available categories are listed.
Behind each category name is the number of advertisements in that category;
when you click on the name all offers or all wants in that category will 
be displayed in a list.
<br><br>
Another option is to view the latest ads via the &quot;view latest ads&quot; link.
Click on it and all the ads will be displayed, sorted from new to old. 
It will show either the all offers or all wants depending on the
selection you made.
<hr class="help">


<a name="ads_results"></a>
<h3>Advertisements overview</h3>
Various windows will show you an overview of advertisements. Most
of these windows have the following elements, allowing you to do
some actions on the advertisements in the list. (Not all of these
may be available to you, depending on your permissions and where you
came from):
<ul>
	<li><b>picture:</b> You can click on the picture to have it shown in 
	its original size in a special popup window.
	<li><b>title:</b> You can click on the title of the ad, which will bring
	you to a window with details of the advertisement.
	<li><b>member name:</b> clicking on the member name will bring you to the 
	member's <a href="${pagePrefix}profiles"><u>profile</u></a>.
	<li><b>status:</b> this column shows the <a href="#ad_status"><u>status</u></a> of the
	advertisement. 
	<li><img border="0" src="${images}/edit.gif" width="16"
		height="16"> Click on the icon to modify the advertisement.
	<li><img border="0" src="${images}/delete.gif" width="16"
		height="16"> Click on the icon to delete this advertisement.
</ul>
If applicable, and if you have the permissions, a button labeled 
&quot;Insert new advertisement&quot; will be visible below. You can use
this to enter a new advertisement.
<hr class="help">


<a name="ad_status"></a>
<h3>Advertisement status</h3>
An advertisement has a status, which can have the following values:
<ul>
	<li><b>Scheduled:</b>
	The begin date is set in the future. The advertisement will not (yet) show
	up when somebody performs a search on advertisements.
	<li><b>Active: </b>
	The current date is between the begin date and the end date. Only
	active advertisements will show up in the search result of other
	Members.
	<li><b>Expired:</b>
	The current date is past the end date. The advertisement will not (anymore)
	show up when somebody does a search on advertisements.
	<li><b>permanent:</b> This type of advertisement does not have a begin
	and end date, and will always be visible. It is specifically meant for services
	and for goods which cannot get &quot;sold out&quot;.
</ul>
<hr class="help">


<a name="search_ads_result"></a>
<h3>Search Results (of advertisements)</h3>
This window will show the search results according to the search criteria you
specified in the window above. <br><br>
The result list will show the advertisements sorted by relevance. To get a detailed
explanation of the elements in this overview, <a href="#ads_results"><u>
click here</u></a>. <br><br>
In the right upper corner of the window, next to the help icon, there is
a print icon <img border="0" src="${images}/print.gif" width="16" height="16">&nbsp;
which will allow you to print a list of the advertisements.
<span class="admin">
Next to the print functions an administrator can
also download the advertisements in a <a href="${pagePrefix}loans#csv"><u>
csv file</u></a> via the <img border="0" src="${images}/save.gif" width="16" height="16">&nbsp;
save icon.
</span>
<hr class="help">


<span class="member">
<a NAME="member_my_ads"></a>
<h3>My advertisements</h3>
This page shows all the advertisements (Products & Services) you placed.
To get a detailed
explanation of the elements in this overview, <a href="#ads_results"><u>
click here</u></a>. 
<hr class="help">
</span>


<a name="ads_of_member_description"></a>
<h3>Advertisement details</h3>
When viewing the ads of another member you will see all fields that
contain information. Click on the small picture 
to view the image(s) in real size.
<br>
If you want more information about the member, or if you want to make a
payment you can go directly to the member's <a href="${pagePrefix}profiles"><u>
profile</u></a> by clicking the &quot;Owner&quot; link.
<hr class="help">


<a name="ad_insert"></a>
<a NAME="ad_modify"></a>
<h3>Modify advertisement / New Advertisement</h3>
With this form you can modify an existing advertisement, or
insert a new one. If you are modifying an existing advertisement, you
should click the &quot;Change&quot; button first, otherwise the fields
cannot be modified. When done, click the &quot;submit&quot;
button to save your advertisement.
<br><br>Most fields are self explanatory. We will shortly comment on some fields:
<ul>
	<li><b>Title:</b> The &quot;"title"&quot; can be used for <b>short</b>
	description. It will show up in the search result window.
	<li><b>description:</b> You can use the &quot;Description&quot; field for the
	details. For the description you may use &quot;rich text&quot; which means
	you have some extra options to get a nice layout for the advertisement.
	<li><b>not-expirable:</b> For long term offers you can check this check box.
	If that case the advertisement will not expire. See next item. In order
	to keep the advertisements in your organization up to date (and not full of
	items which are not available anymore), we advise to use this check box only
	with services, and not with goods of which you have only one item.    
	<li><b>Publication date / expiry date:</b> The &quot;Publication Date&quot; is
	the date that the advertisement will be published for the first time, 
	which means that it will show up in the search for products & services. 
	An advertisement has a maximum life time by default (set by the 
	administration). When the &quot;expiration date&quot; is reached the 
	ad will not show up anymore in the searches (but it will show up in your list).
	<li><b>external publication:</b> If you check &quot;external publication&quot;
	you allow the ad to be published automatically in the community web site.
	<li><b>pictures:</b> You can upload one or more <a href="${pagePrefix}profiles#picture"><u>
	pictures</u></a> related to the advertisement. 
</ul> 
If you are changing an existing advertisement, the form will also show a
button labeled &quot;Insert new advertisment&quot; below; you can use this to create
a new advertisement.
<br><br><b>Note: </b>There is a maximum number of advertisements you can
publish. This is set by the administration. If you want to place more
advertisements, you need to first delete some older ads.
<hr class="help">


<a NAME="ads_of_member"></a>
<a name="admin_ads_of_member"></a>
<h3>Advertisement list of member</h3>
This window shows all the ads of a specific member. 
To get a detailed
explanation of the elements in this overview, <a href="#ads_results"><u>
click here</u></a>.<hr>


<a name="categories"></a>
<h2>Advertisement Categories</h2>
Advertisements are organized in categories. Advertisement Categories can be up
to three levels deep: you can have main categories and sub-categories.

<span class="admin">
<i>Where to find it.</i><br>
Admins can manage advertisements via the main &quot;Menu: Advertisements&quot;.
</span>
<hr class="help">


<span class="admin">
<br><br><a NAME="manage_categories"></a>
<h3>Advertisement categories</h3>
This page allows you to manage a advertisement categorie level If you are within
a category below the &quot;Main category&quot; a navigation path like &quot;Main
category > second category level > ... &quot; will show up. The page will list
all the categories above the level indicated in this navigation path. <br>
You can select the links in the navigation path to go back to a higher level of 
categories. The categories in the list show their name, status and the number of sub
categories it has. The items are shown:
<ul>
	<li><b>Title:</b> the name of the category
	<li><b>Status:</b> active (used) or inactive. Inactive categories and
	their contained sub categories and advertisements are (temporary) not used and
	thus not visible to users; the category can be reactivated however.
	<li><b>Sub category:</b> If the category level has sub categories it will
	show the number of sub categories the category level contains.
	<li><img border="0" src="${images}/edit.gif" width="16" height="16">&nbsp;
	Clicking this item will bring you inside the category where you can modify it
	and add sub categories in this category. allows you to change the name of the
	category, or to activate/inactivate it.
	<li><img border="0" src="${images}/delete.gif" width="16" height="16">
	Click the icon if you want to delete the category. This is only possible if the
	category (or its sub categories) doesn't contain any ads.
	<li><b>Alter order:</b> To alter the order of a category select the
	&quot;submit&quot; button labeled &quot;Alter order&quot;, below the overview.
	<li><b>Insert:</b> To insert a new category in this level click the
	&quot;Insert new category&quot; button below the overview.
</ul>
<hr class="help">
</span>


<span class="admin">
<br><br><a NAME="edit_category"></a>
<h3>Edit category / new category</h3>
This is the page for editing or adding a category. It shows the parent category
and the name field (which you can change).
<br>
You can add one or more items in the category level. If you put more then one you 
will have to put each one in a new line.<br>
The status which can be active or inactive. When an category is not marked as active
it won't show up anywhere (not in the searches or combo boxes). The reason for
this is that you cannot delete categories if they (or their sub categories) contain
advertisements. The active/inactive checkbox allows you to have an opportunity to
make the category invisible, even when it contains active ads. You should, however,
be careful with this, because de-activating a category can effect a lot of users who
might start asking questions about this.<br><br>
Once the category level is created you can add items to it by selecting &quot;Insert new
category&quot; button below the list.
<hr class="help">
</span>


<span class="admin">
<br><br><a NAME="ad_category_order"></a>
<h3>Set Ad category order</h3>
Here you can define the order in which the categories will show up in the page.
To do this, put your mouse pointer over the field name, left click the mouse and
keep it held down and &quot;drag&quot; the name to the new position.
Selecting &quot;Sort by use alphabetical order&quot; will sort the categories by 
alphabetical order.<br>
After this select &quot;Submit&quot; to save your changes. 
<hr class="help">
</span>
<hr>


<span class="admin">
<a name="import_export"></a>
<h2>Import / Export categories</h2>
Cyclos allows to import and export advertisement categories, to make sharing
of advertisement categories between organizations and cyclos instances possible.

<i>Where to find it.</i><br> 
You can access this item main &quot;Menu: Advertisements > import/export categories&quot;.
</span>
<hr class="help">


<span class="admin">
<br><br><a NAME="import_ad_categories"></a>
<h3>Import categories</h3>
When you choose &quot;import&quot;, you can specify a category file (with xml
extension) via the &quot;browse&quot; button.
<hr class="help">
</span>


<span class="admin">
<br><br><a NAME="export_ad_categories"></a>
<h3>Export categories</h3>
To export a file, select &quot;Submit&quot; and the browser will
take over and ask you where to save the file.
</span>
<hr class="help">


<span class="admin">
<br><br><a NAME="import_ads"></a>
<h3>Import advertisements</h3>
With this function it is possible to import advertisements. The advertisement fields
are mapped to the column names in the import file. The column names are case
insensitive and need to be at the first line (header). The columns can be in any
order (there is no column one or two, as long as the names are correct it will
work). If a field is optional you can either omit the whole column of leave the
column value empty. <br>
If more than one currencies exist in the system you can chose the currency of the 
advertisements to import (only the ads with a value in the &quot;prive&quot; fields
will have a currency). 
The advertisement import does not support the import of images.<br>
The following fields (columns) are supported.
<ul>
	<li>title (required)<br>
	The advertisement title.
	<li>owner (required)<br>
	The login name of the member (owner) of the advertisement.
	<li>description (required)<br>
	The full advertisement description. May be in either plain text or html format,
	depending on the html column value.
	<li>html (optional)<br>
	Determines the description format. True indicates rich text description. False
	values (the default) indicates plain text description.
	<li>category (required)<br>
	The advertisement category name. Nested categories must be separated by colons
	(:) and optional spaces. E.g. &quot;Products: Computers: Laptops&quot;<br>
	Note that the last category or the &quot;leafe&quot; is not followed by a colon.
	<li>publicationstart (optional)<br>
	Determines when the advertisement will start to be published. When not set, the
	current date is used.
	<li>publicationend (optional)<br>
	Determines the end of the publication period. When not set, indicates a
	permanent advertisement, but a group setting may forbit permanent
	advertisements. If this is the case, this column is required.
	<li>tradetype (optional)<br>
	Determines if the member is offering or searching for products. Possible values
	are (case insensitive): offer or search (where search is a want). Defaults to
	offer.
	<li>external (optional)<br>
	Determines whether the advertisement is supposed to be shown on web services
	search. A group setting may force this value to true or false. Defaults to
	true.
	<li>price (optional)<br>
	The advertisement price.
	<li>custom field internal name (optional)<br>
	The internal name of a custom field. The required validation is honored.
</ul>
Make sure ads group permissions possible



<br><br><a NAME="imported_ad_details"></a>
<h3>Search imported advertisements</h3>
In this window you can search in the imported list for specific advertisements
by line number. If you won't specify a line number all advertisements of the
import will be listed in the window below.



<br><br><a NAME="imported_ad_details_result"></a>
<h3>Search result imported ads</h3>
This window shows the result of the search. It lists the ads with their details and status.



<br><br><a NAME="imported_ads_summary"></a>
<h3>Imported advertisements summary</h3>
This window will show the result of the import. In case of import errors it will 
inform the error type (e.g. field missing) and in case of successful import it 
will show the member, category and the price of the ads.<br>
To process the import you can hit back and the &quot;Import&quot; button.

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
</div>