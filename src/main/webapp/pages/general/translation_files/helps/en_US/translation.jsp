<div style="page-break-after: always;">
<span class="admin">
<br><br>
Cyclos supports different languages,
and allows you to manage translations. All text displayed to the end users are
contained in a language specific file (one for each language), plus a limited
number of static text files for the larger text blocks.<br>
Cyclos ships with a number of languages to choose from; via the settings menu
you can change the language of your installation with just a few mouse clicks. <br>
If you're not satisfied with the translation cyclos ships with, you can also
modify each individual text or term in cyclos. This module also contains a
section to maintain all the messages sent via the cyclos system, and all the
e-mail notifications sent.

<i>Where to find it?</i><br>
The translations module can be accessed via the &quot;Menu: Translation&quot;.
The following submenu items are available:
<ul>
	<li><b><a href="#translation_keys"><u>Application</u></a>:</b>
	allows you to manage translation of all small text
	terms as they appear in the application windows.
	<li><b><a href="#notifications"><u>Notifications</u></a>:</b>
	allows you to manage notifications sent by the system.
	<li><b>e-mails:</b> allows you to manage the text of e-mails sent by the 	system.
	<li><b>import/export:</b> allows you to import or export translation
	files. This allows communities of cyclos to share their translations with other
	cyclos users.
</ul>
Note1: Static text files like the contact and new pages are not managed via the
translation module; these can be managed via <a
	href="${pagePrefix}content_management"><u>content management</u></a>.
<br><br>
Note2: if you want to change the language of Cyclos this is not the 
place. The language can be changed in  <a
	href="${pagePrefix}settings#local"><u>Menu: Settings > local
settings</u></a> in the block &quot;internationalization&quot;.<br> 
<hr>


<a name="translation_keys"></a>
<h2>Translation keys</h2>
Any short written text which appears in the cyclos interface is kept in a
language file; there exists one for each language. The cyclos interface allows
you to replace the whole language file, or to edit each key in the file
individually.

If a <a href="#key"><u>key</u></a> which is placed in an application page cannot
be found in the <a href="#language_file"><u>language file</u></a>, the key as it
appears in the page is shown between question marks. This will usually look like
this: &quot;???translationMessage.search.showOnlyEmpty???&quot;. In such a case,
you can add the key (and its value) to the language file yourself, via
&quot;Menu: translation > application&quot;.
<br><br>If you're not content with a translation as it appears in the browser
window, you can adapt it. Follow this procedure:
<ol>
	<li>go to the search page for translation terms (via the &quot;Menu:
	Translation > Applicaton&quot;, and there you may type the term which you want
	to adapt in the &quot;value&quot; edit. The term is not case sensitive, and the
	program will also look for matches if you enter only part of the term. Click
	&quot;Search&quot; to show the results.
	<li>In the result page, use the modify icon (<img border="0"
		src="${images}/edit.gif" width="16" height="16">&nbsp;) to change the value.
</ol>
<br><br>You can also add or delete complete key/value pairs from the file. This
is a bit tricky though, you may want to read the <a href="#caution"><u>caution
notes</u></a> on this.
<hr class="help">


<a name="caution"></a>
<h3>Caution with adding/removing language keys</h3>
The cyclos interface allows you to modify, add or remove language <a href="#key"><u>
keys</u></a> from the <a href="#language_file"><u>language file</u></a>. Modifying those
keys is a safe action, but adding or removing keys can be a bit tricky. You
should only do so if you thoroughly understand how the system with language
keys works.<br>
A key which is removed, is only removed from the language file itself. It is NOT
removed from the application pages. If the key is still in use at an application
page when you removed it, next time this page will just show the key name
between question marks, which is usually not very beautiful (example:
&quot;???about.bla.something.title???&quot;). <br>
Vice versa: adding a key to the language file doesn't help you anything if you
don't start using the key somewhere. You can do this by <a
href="${pagePrefix}content_management"><u>customizing application pages</u></a>.<br>
It also can happen that a key is lost after an update - though this is very
rare. A normal Cyclos update will add the new translation keys. In such a case,
you can safely add the key yourself.
<hr class="help">


<A NAME="application"></A>
<h3>Search Translation Key</h3>
 In this window you can search for <a href="#key"><u>translation keys</u></a>.<br>
Enter the key or <a href="#value"><u>value</u></a> in the appropriate
edit. Note that the search is NOT case sensitive, and that you need not enter
the complete term; the search will also included partly matches. As always, you
may not enter anything and just hit &quot;Search&quot; - this will return all
available key/value pairs in the result box.<br>
Selecting the &quot;only empty values&quot; checkbox will show only the keys
that do not have a translation (which may have happened after an update).<br>
The search result will appear in the <a href="#application_results"><u>
search results list window</u></a> below. In that window you have the possibility to
change the translation per key.
<br><br>It is also possible to add a new translation key; if you want to do so,
click the submit button labeled &quot;insert new key&quot;. You may
want to read the <a href="#caution"><u>caution notes</u></a> on this first.
<br><br>
Note: if you want to change the language of Cyclos this is not the 
place. The language can be changed in  <a
	href="${pagePrefix}settings#local"><u>Menu: Settings > local
settings</u></a> in the block &quot;internationalization&quot;.<br> 
<hr class="help">


<a name="application_results"></a>
<h3>Search results (for translation key/value)</h3>
This window shows the search results as you difined it in the <a
	href="#application"><u> window above</u></a>.<br>
In this window you can select a key and delete it (via the <img border="0"
	src="${images}/delete.gif" width="16" height="16">&nbsp; delete icon), or
edit it (via the <img border="0" src="${images}/edit.gif" width="16" height="16">&nbsp;
edit icon). If you want to delete multiple keys you can select one or more in
the check boxes, and then use the &quot;remove selected&quot;. button. <br><br>
Note that deleting keys can be tricky, you may want to read the <a href="#caution"><u>
caution notes</u></a> about this first. 
<hr class="help">


<A NAME="edit_key"></A>
<h3>Modify translation key</h3>
In this window you can modify the <a href="#value"><u>value</u></a> of a
translation <a href="#key"><u>key</u></a>. First click &quot;change&quot;, then
make your changes, and then click &quot;submit&quot; to save your changes.<br>
You can use multiple lines, but usually this is ignored, and the result will
show in just one single line.
<hr class="help">


<A NAME="insert_key"></A>
<h3>New translation key</h3>
Here you can enter a new translation <a href="#key"><u>key</u></a> and <a
	href="#value"><u>value</u></a>. Just enter them in the edits, and click
&quot;submit&quot;. Before adding new translation keys, we advise to read the
<a href="#caution"><u>caution notes</u></a>  about this.
<hr class="help">


<A NAME="import_file"></A>
<h3>Import/Export translation file</h3>
In this window you can <a href="#import"><u>import</u></a> or <a href="#export"><u>
export</u></a> a <a href="#language_file"><u> language file</u></a>. Follow the links
for more information.
<hr class="help">


<a name="import"></a>
<h3>Import language file</h3>
The above rectangle in this window is for importing a new <a
	href="#language_file"><u> language file</u></a> . This would be a rare case,
like for example when adding a new language to Cyclos. A normal Cyclos update
will add automatically the new translation <a href="#key"><u>keys</u></a> (if
there are any).
<br><br>First, you will need to decide on &quot;What to do&quot;. There are four
options:
<ul>
	<li><b>Import new keys only:</b> This will just import new keys and leave
	the existing keys as they are.
	<li><b>Import new and empty keys only:</b> The same as previous, but now
	it will also import empty keys (that is: keys where the value is empty, 
	probably because the translation is not yet completely finished).
	<li><b>Import new and modified keys</b> This imports new and modified
	keys. That means if you have modified some key values yourself, they will be
	overwritten by the "default" value. Keys that are not used anymore will be
	removed.
	<li><b>Replace entire file</b> This will just overwrite the whole file.
	All customization done by you in the past will of course be lost.
</ul>
After this, you will have to &quot;browse&quot; to the translation file that is
locally stored on your computer and click &quot;submit&quot;.
<br><br>Note: It is not necessary that the file to import contains all keys -
except of course if you chose &quot;replace entire file&quot;. In all other
cases, it can be any amount of keys (but it must be in the correct format).<br>
When you want to replace the entire file make sure that you do upload the entire
file. Otherwise you risk losing your existing keys.


<a name="export"></a>
<h3>Export language file</h3>
The part of the form for exporting the present <a href="#language_file"><u>
language definition</u></a> is very simple: just use the &quot;submit&quot; button
labeled &quot;export as properties file&quot;. If you click this button, the
browser will take over, and usually ask you if you want to save the file.<br>
Exporting the language file would be a rare case; you would want to do this when
you want to make your own cyclos translation available to other cyclos community
users. If you made your own translation, you are encouraged to contact us 
and make your translation available, so we can add your translation to the
official cyclos distribution. See the project site for the contact address
(<a href="http://project.cyclos.org"><u>http://project.cyclos.org</u></a>).
<hr>


<a name="notifications"></a>
<h2>Notifications</h2>
Cyclos allows you to manage the contents of various notifications
with the following windows. 
<hr class="help">


<A NAME="general_notifications"></A>
<h3>General notifications</h3>
This window shows you general <a href="${pagePrefix}preferences"><u>
notifications</u></a>. Usually these are prefixes and suffixes
which are added to outgoing mails. 
You can change the content by clicking the edit icon (
<img border="0" src="${images}/edit.gif" width="16" height="16">&nbsp;).
<hr class="help">


<A NAME="member_notifications"></A>
<h3>Member notifications</h3>
This window shows you <a href="${pagePrefix}preferences"><u>
notifications</u></a> which are sent to the member on various occasions. 
You can change the content by clicking the edit icon (
<img border="0" src="${images}/edit.gif" width="16" height="16">&nbsp;).
<hr class="help">


<A NAME="admin_notifications"></A>
<h3>Admin notifications</h3>
This window shows you <a href="${pagePrefix}preferences"><u>
notifications</u></a> which are sent to admins on various occasions. 
You can change the content by clicking the edit icon (
<img border="0" src="${images}/edit.gif" width="16" height="16">&nbsp;).
<hr class="help">


<A NAME="edit_notifications"></A>
<h3>Edit notification</h3>
This window allows you to change the notification content. To do this, you
should click &quot;change&quot; first (as always); when done, you can save
your changes by clicking &quot;submit&quot;.<br>
A rich text editor will appear, allowing you to use some layout features.
You may also use some fields, depending on the notification you modify.
<br><br>
<br>The following #variables# are available in the notifications text. The
#variable name# which is placed between #..# will be replaced with the
relevant value.<br>

<h2>General Notifications</h2>

<table rules="all" frame="border" celspacing="10%" celpadding="10%">
<tr>
	<td><b>setting name</b></td>
	<td><b>variable</b></td>
	<td><b>replaced by</b></td>
</tr>
<tr>
	<td rowspan="2">Broker removed remark</td>
	<td>#member#</td>
	<td>member real name</td>
</tr>
<tr>
	<td>#login#</td>
	<td>member login name</td>
</tr>
<tr>
	<td>Mail subject prefix, <br>
    Plain text mail body suffix,<br>
	HTML mail body suffix,<br>
	SMS message prefix
	</td>
	<td>#system_name#</td>
	<td>name of your system</td>
</tr>
</table>
<br>

<h2>Member notifications</h2>
<table rules="all" frame="border" celspacing="10%" celpadding="10%">
<tr>
	<td><b>setting name</b></td>
	<td><b>variable</b></td>
	<td><b>replaced by</b></td>
</tr>
<tr>
	<td rowspan="2">Brokering expiration,<br>
	Brokering manually removed</td>
	<td>#member#</td>
	<td>the real name of the brokered member</td>
</tr>
<tr>
	<td>#login#</td>
	<td>the login name of the brokered member</td>
</tr>
<tr>
	<td rowspan="5">New commission contract,<br>
	Commission contract canceled</td>
	<td>#broker#</td>
	<td>the broker name</td>
</tr>
<tr>
	<td>#start_date#</td>
	<td>start date of the new contract</td>
</tr>
<tr>
	<td>#end_date#</td>
	<td>end date of the new contract</td>
</tr>
<tr>
	<td>#amount#</td>
	<td>the contract amount</td>
</tr>
<tr>
	<td>#link#</td>
	<td>a link which can be clicked for more details</td>
</tr>
<tr>
	<td rowspan="5">Commission contract accepted,<br>
	Commission contract denied</td>
	<td>#member#</td>
	<td>the member name</td>
</tr>
<tr>
	<td>#start_date#</td>
	<td>start date of the new contract</td>
</tr>
<tr>
	<td>#end_date#</td>
	<td>end date of the new contract</td>
</tr>
<tr>
	<td>#amount#</td>
	<td>the contract amount</td>
</tr>
<tr>
	<td>#link#</td>
	<td>a link which can be clicked for more details</td>
</tr>
<tr>
	<td>Low units</td>
	<td>#creditLimit#</td>
	<td>the member's credit limit</td>
</tr>
<tr>
	<td rowspan="2">Ad expiry<br>
	Ad interest</td>
	<td>#title#</td>
	<td>the title of the ad</td>
</tr>
<tr>
	<td>#link#</td>
	<td>a link which can be clicked for more details</td>
</tr>
<tr>
	<td rowspan="3">Invoice received</td>
	<td>#member#</td>
	<td>real name of the sending member</td>
</tr>
<tr>
	<td>#login#</td>
	<td>login name of sending member</td>
</tr>
<tr>
	<td>#link#</td>
	<td>a link which can be clicked for more details</td>
</tr>
<tr>
	<td rowspan="6">Invoice accepted,<br>
	Invoice canceled,<br>
	Invoice denied</td>
	<td>#member#</td>
	<td>real name of member</td>
</tr>
<tr>
	<td>#login#</td>
	<td>login name of member</td>
</tr>
<tr>
	<td>#date#</td>
	<td>date the invoice has been sent</td>
</tr>
<tr>
	<td>#amount#</td>
	<td>amount of invoice</td>
</tr>
<tr>
	<td>#description#</td>
	<td>invoice description</td>
</tr>
<tr>
	<td>#link#</td>
	<td>a link which can be clicked for more details</td>
</tr>
<tr>
	<td rowspan="4">Received invoice expired,<br>
	Sent invoice expired</td>
	<td>#member#</td>
	<td>real name of member</td>
</tr>
<tr>
	<td>#login#</td>
	<td>login name of member</td>
</tr>
<tr>
	<td>#amount#</td>
	<td>amount of invoice</td>
</tr>
<tr>
	<td>#link#</td>
	<td>a link which can be clicked for more details</td>
</tr>
<tr>
	<td rowspan="3">Loan expiry</td>
	<td>#grantDate#</td>
	<td>the Date the loan was granted</td>
</tr>
<tr>
	<td>#amount#</td>
	<td>amount of loan</td>
</tr>
<tr>
	<td>#link#</td>
	<td>a link which can be clicked for more details</td>
</tr>
<tr>
	<td rowspan="2">Loan grant</td>
	<td>#amount#</td>
	<td>amount of loan</td>
</tr>
<tr>
	<td>#link#</td>
	<td>a link which can be clicked for more details</td>
</tr>
<tr>
	<td rowspan="6">Payment receiving</td>
	<td>#amount#</td>
	<td>amount</td>
</tr>
<tr>
	<td>#member#</td>
	<td>real name of member involved</td>
</tr>
<tr>
	<td>#login#</td>
	<td>login name of member</td>
</tr>
<tr>
	<td>#transaction_number#</td>
	<td>the number of the transaction (if enabled)</td>
</tr>
<tr>
	<td>#balance#</td>
	<td>new balance</td>
</tr>
<tr>
	<td>#link#</td>
	<td>a link which can be clicked for more details</td>
</tr>
<tr>
	<td rowspan="4">Payment that awaits authorization receiving,<br>
	New payment for receiver to authorize,<br>
	New payment for payer to authorize,<br>
	New payment for broker to authorize</td>
	<td>#amount#</td>
	<td>amount</td>
</tr>
<tr>
	<td>#member#</td>
	<td>real name of member involved</td>
</tr>
<tr>
	<td>#login#</td>
	<td>login name of member</td>
</tr>
<tr>
	<td>#link#</td>
	<td>a link which can be clicked for more details</td>
</tr>
<tr>
	<td rowspan="3">Pending payment authorization,<br>
	Pending payment denial,<br>
	Pending payment canceling</td>
	<td>#amount#</td>
	<td>amount</td>
</tr>
<tr>
	<td>#date#</td>
	<td>date of submitting payment</td>
</tr>
<tr>
	<td>#link#</td>
	<td>a link which can be clicked for more details</td>
</tr>
<tr>
	<td rowspan="4">Scheduled payment succesfully processed,<br>
	Scheduled payment failed (to payer),<br>
	Scheduled payment from invoice failed (to payee)</td>
	<td>#amount#</td>
	<td>amount</td>
</tr>
<tr>
	<td>#member#</td>
	<td>name of member involved</td>
</tr>
<tr>
	<td>#login#</td>
	<td>login name of member involved</td>
</tr>
<tr>
	<td>#link#</td>
	<td>a link which can be clicked for more details</td>
</tr>
<tr>
	<td rowspan="5">External channel payment performed</td>
	<td>#amount#</td>
	<td>amount</td>
</tr>
<tr>
	<td>#member#</td>
	<td>name of member involved</td>
</tr>
<tr>
	<td>#login#</td>
	<td>login name of member involved</td>
</tr>
<tr>
	<td>#channel#</td>
	<td>the channel (web, POS, SMS, etc)</td>
</tr>
<tr>
	<td>#link#</td>
	<td>a link which can be clicked for more details</td>
</tr>
<tr>
	<td rowspan="5">External channel payment request expired payer</td>
	<td>#amount#</td>
	<td>amount</td>
</tr>
<tr>
	<td>#toMemberName#</td>
	<td>name of member requesting the payment</td>
</tr>
<tr>
	<td>#toMemberUsername#</td>
	<td>login name of member requesting</td>
</tr>
<tr>
	<td>#channel#</td>
	<td>the channel (web, POS, SMS, etc)</td>
</tr>
<tr>
	<td>#link#</td>
	<td>a link which can be clicked for more details</td>
</tr>
<tr>
	<td rowspan="5">External channel payment request expired receiver</td>
	<td>#amount#</td>
	<td>amount</td>
</tr>
<tr>
	<td>#fromMemberName#</td>
	<td>name of member who should pay</td>
</tr>
<tr>
	<td>#fromMemberUsername#</td>
	<td>login name of member who should pay</td>
</tr>
<tr>
	<td>#channel#</td>
	<td>the channel (web, POS, SMS, etc)</td>
</tr>
<tr>
	<td>#link#</td>
	<td>a link which can be clicked for more details</td>
</tr>
<tr>
	<td rowspan="3">Reference receiving</td>
	<td>#member#</td>
	<td>name of member involved</td>
</tr>
<tr>
	<td>#login#</td>
	<td>login name of member involved</td>
</tr>
<tr>
	<td>#link#</td>
	<td>a link which can be clicked for more details</td>
</tr>
<tr>
	<td rowspan="5">Transaction feedback received,<br>
	Transaction feedback replied,<br>
	Transaction feedback administration comments</td>
	<td>#member#</td>
	<td>name of member involved</td>
</tr>
<tr>
	<td>#login#</td>
	<td>login name of member involved</td>
</tr>
<tr>
	<td>#amount#</td>
	<td>amount of original payment</td>
</tr>
<tr>
	<td>#date#</td>
	<td>date of original payment</td>
</tr>
<tr>
	<td>#link#</td>
	<td>a link which can be clicked for more details</td>
</tr>
<tr>
	<td rowspan="3">Account fee receiving</td>
	<td>#account_fee#</td>
	<td>the name of the account fee</td>
</tr>
<tr>
	<td>#amount#</td>
	<td>amount of original payment</td>
</tr>
<tr>
	<td>#link#</td>
	<td>a link which can be clicked for more details</td>
</tr>
<tr>
	<td rowspan="4">New certification issued</td>
	<td>#amount#</td>
	<td>amount of certification</td>
</tr>
<tr>
	<td>#issuerName#</td>
	<td>real name of the user issueing this</td>
</tr>
<tr>
	<td>#issuerUsername#</td>
	<td>login name of the user issueing this</td>
</tr>
<tr>
	<td>#link#</td>
	<td>a link which can be clicked for more details</td>
</tr>
<tr>
	<td rowspan="5">Certification status changed,<br>
	Guarantee status changed (only buyer)</td>
	<td>#amount#</td>
	<td>amount of certification/guarantee</td>
</tr>
<tr>
	<td>#issuerName#</td>
	<td>real name of the user issueing this</td>
</tr>
<tr>
	<td>#issuerUsername#</td>
	<td>login name of the user issueing this</td>
</tr>
<tr>
	<td>#status#</td>
	<td>the new status</td>
</tr>
<tr>
	<td>#link#</td>
	<td>a link which can be clicked for more details</td>
</tr>
<tr>
	<td rowspan="4">Expired certification,<br>
	Expired guarantee,<br>
	Guarantee awaiting authorization (only buyer),<br>
	New payment obligation published</td>
	<td>#amount#</td>
	<td>amount</td>
</tr>
<tr>
	<td>#buyerName#</td>
	<td>real name of the user buying</td>
</tr>
<tr>
	<td>#buyerUsername#</td>
	<td>login name of the user buying</td>
</tr>
<tr>
	<td>#link#</td>
	<td>a link which can be clicked for more details</td>
</tr>
<tr>
	<td rowspan="9">Guarantee status changed</td>
	<td>#amount#</td>
	<td>amount of certification/guarantee</td>
</tr>
<tr>
	<td>#status#</td>
	<td>the new status</td>
</tr>
<tr>
	<td>#issuerName#</td>
	<td>real name of the user issueing this</td>
</tr>
<tr>
	<td>#issuerUsername#</td>
	<td>login name of the user issueing this</td>
</tr>
<tr>
	<td>#buyerName#</td>
	<td>real name of the user buying</td>
</tr>
<tr>
	<td>#buyerUsername#</td>
	<td>login name of the user buying</td>
</tr>
<tr>
	<td>#sellerName#</td>
	<td>real name of the user selling</td>
</tr>
<tr>
	<td>#sellerUsername#</td>
	<td>login name of the user selling</td>
</tr>
<tr>
	<td>#link#</td>
	<td>a link which can be clicked for more details</td>
</tr>
<tr>
	<td rowspan="6">Guarantee awaiting authorization</td>
	<td>#amount#</td>
	<td>amount of guarantee</td>
</tr>
<tr>
	<td>#buyerName#</td>
	<td>real name of the user buying</td>
</tr>
<tr>
	<td>#buyerUsername#</td>
	<td>login name of the user buying</td>
</tr>
<tr>
	<td>#sellerName#</td>
	<td>real name of the user selling</td>
</tr>
<tr>
	<td>#sellerUsername#</td>
	<td>login name of the user selling</td>
</tr>
<tr>
	<td>#link#</td>
	<td>a link which can be clicked for more details</td>
</tr>
<tr>
	<td rowspan="4">Payment obligation rejected</td>
	<td>#amount#</td>
	<td>amount</td>
</tr>
<tr>
	<td>#sellerName#</td>
	<td>real name of the user selling</td>
</tr>
<tr>
	<td>#sellerUsername#</td>
	<td>login name of the user selling</td>
</tr>
<tr>
	<td>#link#</td>
	<td>a link which can be clicked for more details</td>
</tr>
</table>
<br>Admin Notifications</br>
<table rules="all" frame="border" celspacing="10%" celpadding="10%">
<tr>
	<td><b>setting name</b></td>
	<td><b>variable</b></td>
	<td><b>replaced by</b></td>
</tr>
<tr>
	<td rowspan="6">Guarantee awaiting for authorization (only buyer)</td>
	<td>#amount#</td>
	<td>amount</td>
</tr>
<tr>
	<td>#issuerName#</td>
	<td>real name of the user issueing this</td>
</tr>
<tr>
	<td>#issuerUsername#</td>
	<td>login name of the user issueing this</td>
</tr>
<tr>
	<td>#buyerName#</td>
	<td>real name of the user buying</td>
</tr>
<tr>
	<td>#buyerUsername#</td>
	<td>login name of the user buying</td>
</tr>
<tr>
	<td>#link#</td>
	<td>a link which can be clicked for more details</td>
</tr>
<tr>
	<td rowspan="8">Guarantee awaiting for authorization</td>
	<td>#amount#</td>
	<td>amount</td>
</tr>
<tr>
	<td>#issuerName#</td>
	<td>real name of the user issueing this</td>
</tr>
<tr>
	<td>#issuerUsername#</td>
	<td>login name of the user issueing this</td>
</tr>
<tr>
	<td>#buyerName#</td>
	<td>real name of the user buying</td>
</tr>
<tr>
	<td>#buyerUsername#</td>
	<td>login name of the user buying</td>
</tr>
<tr>
	<td>#sellerName#</td>
	<td>real name of the user selling</td>
</tr>
<tr>
	<td>#sellerUsername#</td>
	<td>login name of the user selling</td>
</tr>
<tr>
	<td>#link#</td>
	<td>a link which can be clicked for more details</td>
</tr>
<tr>
	<td rowspan="2">Application errors</td>
	<td>#path#</td>
	<td>the path on which the error occurred</td>
</tr>
<tr>
	<td>#link#</td>
	<td>a link which can be clicked for more details</td>
</tr>
<tr>
	<td rowspan="6">Payments from system to members</td>
	<td>#from_account#</td>
	<td>the name of the from-account holder</td>
</tr>
<tr>
	<td>#payment_type#</td>
	<td>the payment type</td>
</tr>
<tr>
	<td>#amount#</td>
	<td>amount</td>
</tr>
<tr>
	<td>#member#</td>
	<td>real name of member paid</td>
</tr>
<tr>
	<td>#login#</td>
	<td>login name of the member paid</td>
</tr>
<tr>
	<td>#link#</td>
	<td>a link which can be clicked for more details</td>
</tr>
<tr>
	<td rowspan="6">Payments from member to system</td>
	<td>#to_account#</td>
	<td>the name of the to-account holder</td>
</tr>
<tr>
	<td>#payment_type#</td>
	<td>the payment type</td>
</tr>
<tr>
	<td>#amount#</td>
	<td>amount</td>
</tr>
<tr>
	<td>#member#</td>
	<td>real name of member paid</td>
</tr>
<tr>
	<td>#login#</td>
	<td>login name of the member paid</td>
</tr>
<tr>
	<td>#link#</td>
	<td>a link which can be clicked for more details</td>
</tr>
<tr>
	<td rowspan="5">Payments from system to system</td>
	<td>#from_account#</td>
	<td>the name of the from-account </td>
</tr>
<tr>
	<td>#to_account#</td>
	<td>the name of the to-account</td>
</tr>
<tr>
	<td>#payment_type#</td>
	<td>the payment type</td>
</tr>
<tr>
	<td>#amount#</td>
	<td>amount</td>
</tr>
<tr>
	<td>#link#</td>
	<td>a link which can be clicked for more details</td>
</tr>
<tr>
	<td rowspan="4">New payment for admin to authorize,<br>
	System invoice</td>
	<td>#amount#</td>
	<td>amount</td>
</tr>
<tr>
	<td>#member#</td>
	<td>real name of member</td>
</tr>
<tr>
	<td>#login#</td>
	<td>login name of the member</td>
</tr>
<tr>
	<td>#link#</td>
	<td>a link which can be clicked for more details</td>
</tr>
<tr>
	<td rowspan="3">New member registration</td>
	<td>#member#</td>
	<td>name of the new member</td>
</tr>
<tr>
	<td>#group#</td>
	<td>name of group of the new member</td>
</tr>
<tr>
	<td>#link#</td>
	<td>a link which can be clicked for more details</td>
</tr>
<tr>
	<td rowspan="2">Member alerts</td>
	<td>#member#</td>
	<td>real name of the member</td>
</tr>
<tr>
	<td>#login#</td>
	<td>login name of the member</td>
</tr>
</table>
<hr>


<A NAME="mail_translation"></A>
<h3>Mail translation</h3>
This window allows you to change the content of mail messages sent on certain
occasions. To do this, you
should click &quot;change&quot; first (as always); when done, you can save
your changes by clicking &quot;submit&quot;.<br>
The following mails can be changed, at present:
<ul>
	<li><b>Invitation mail:</b> the mail sent when using the option to
	<a href="${pagePrefix}home#home_invite"><u>invite a friend</u></a> via
	&quot;Menu: home > invite&quot;.
	<li><b>Activation mail:</b> the mail sent to a member when he is activated.
	This happens after registration, when an admin activates the account by
	moving the member from the &quot;<a href="${pagePrefix}groups#inactive_members"><u>
	pending members</u></a>&quot; group to another group (usually full members).
	<li><b>Public registration e-mail validation:</b> the mail sent to a potential
	member, when he tries to register for the first time. For this mail to be sent,
	cyclos must be <a href="${pagePrefix}groups#group_registration_settings"><u>
	configured for this</u></a>.
	<li><b>reset password mail:</b> the mail sent when someone wants to 
	reset his password.
</ul>
<hr class="help">


<a name="imexport_notifications_mails"></a>
<h3>Import / export notifications and e-mails translations</h3>
With this window, you can import or export the mails and notifications
translated texts to/from a file. The file is in xml format, 
and can be read by any other cyclos instance of this (or future) versions
of cyclos.<br>
You may want to do this for sharing
translations between instances of cyclos, or for security reasons.<br>
The form is rather straightforward. When you choose &quot;import&quot;,
you should specify the file via the &quot;browse&quot; button. In case
of exporting a file, the browser will take over and ask you where to
save the download.
<hr>

<br><br><a name="glossary"></a>
<h2>Glossary of terms</h2>
<br><br>

<a name="language_file"></a> <b>Language file</b>
<br><br>In cyclos, for each language there is a language file. This file contains
all the written text &quot;strings&quot; which appear in the cyclos interface
(except for large blocks of text which reside in complete files).<br>
A language file is always named according to a specific pattern:
&quot;ApplicationResources_xx_XX.properties&quot;, where the xx is replaced by
the language code and the XX is replaced by the country code. Example:
&quot;ApplicationResources_en_US.properties&quot; is the file for US-english.<br>
A language files contains keys and values these are separated by the &quot;=&quot; 
sign, without any spaces.
<hr class='help'>

<a name="key"></a> <b>Translation Key</b>
<br><br>The translation keys are placed in the application pages; when these
pages are displayed in your browser, the keys are looked up in the language 
file, and replaced by the corresponding values found in this file.
<hr class='help'>

<a name="value"></a> <b>Translation Value</b>
<br><br>Translation values are the words and terms in your own language which you
will see on the cyclos pages in your browser. The original pages do not contain
these values. In stead of this, translation keys are
placed in the application pages; when these pages are displayed in your browser,
the keys are looked up in the language file, and replaced by the corresponding 
values found in this file.<br>
All translation values (application translation, notifications and e-mails) can
contain &quot;variables&quot;. Variables are are always surrounded by two #
signs like #member#, #title# and #amount#. The variable and will be replaced
with the correct value when viewed in Cyclos. The variable names are self
explanatory and all possible variables are all used in the default translation
values.
<hr class='help'>

</span>

</div> <%--  page-break end --%>