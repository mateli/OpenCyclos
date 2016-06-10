 <div style="page-break-after: always;">
<br><br>
The settings in Cyclos define any configuration at
system level.

<i>Where to find it?</i><br>
The translations module can be accessed via the &quot;Menu: Settings&quot;.<br>
The following types of settings exist:
<ul>
	<li><b> <a href="#local"><u> Local settings:</u></a></b> Any
	system local as language, time zone and formats.
	<li><b><a href="#alerts"><u> Alert settings:</u></a></b>
	Thresholds and other settings concerning system alerts.
	<li><b><a href="#access"><u> Access settings:</u></a></b> Settings
	concerning access and security levels.
	<li><b><a href="#mail"><u> Mail settings:</u></a></b> The mail
	server settings
	<li><b><a href="#log"><u> Log settings:</u></a></b> The settings
	of the log files.
	<li><b><a href="#channels"><u> Channels:</u></a></b> Configuration
	the channels (access media).
	<li><b><a href="#web_services_clients"><u> Web service
	clients</u></a></b> Configuration the web services.
	<li><b>System Tasks:</b> There are two types of system tasks. <b><a
	href="#search_indexes"><u> Manage Indexes </u></a></b> and <b> <a
	href="#online_state"><u> System availability </u></a></b>
	<li><b><a href="#import_export"><u> Export & import</u></a></b>
	Import and export settings.
</ul>
<hr>


<A NAME="local"></A>
<h2>Local settings</h2>
These are settings related to the locale and system. In order to make changes
click the &quot;change&quot; button; to save the changes click &quot;submit&quot;.
<b>Cyclos identification</b>
<ul>
	<li><b>Application name:</b>
	This is the title of the application as it appears in the top bar of your
	browser.
	<li><b>Application username:</b>
	This is name that is used for automatically generated messages by the system to
	members. For example: The &quot;cyclos administration&quot; accepted your invoice of..
	<li><b>Identification for external channels:</b>
	This name is used in case third party software is accessing Cyclos and needs to
	identify the instance it is connecting to. Normally this would be the instance
	name.
	<li><b>Identification for external channels</b>
	This is the name that is used by external channels. For example when configuring the 
	SMS module (which is an external module) you need to specify an identifier in the SMS 
	configuration file. That identifier is defined in this setting.
	
	<br>
	<li><b>Application root URL</b>
	This URL is used by Cyclos when e-mails (e.g notifications) are sent to users 
	that contain a link to Cyclos. For example in the case of a registration 
	confirmation. The URL would be normally something like www.yourdomain.com/cyclos, 
	or just www.yourdomain.com. Be aware that this URL can be overwritten on lower 
	level (group filters and groups). This would be necessary in the case you have 
	configured various independent communities/instances within a single Cyclos 
	installation. In that case each community/instance can have it's own access URL.
	<br>
	<li><b>Global container page url</b> This field is needed if you want to access
	Cyclos from a web site (like the demo on the Cyclos project site). The web site
	would have an Iframe or a frame set that includes the Cyclos login page. If you
	do this you might want to customize the &quot;top&quot; (in content management
	- static files - top.jsp) in order not to shop up.<br>
	The url will need to be a full url with http:// or https:// in front. Be aware
	that if you put the url request to the normal page will be directed directly
	to the container page. If the container pages does not work correctly it means
	you cannot access Cyclos with the normal url. If this happens you can always
	access Cyclos with the original page putton do/login after the url. For example
	http://www.yourdomain.org/cyclos/do/login<br>
	Cyclos supports multiple communities in one instance. Each community can be
	accessed via its own (customized) login page or from their own web site. In
	order to enable this you will need to define a container page url per group or
	group filter. In this type of configuration the Global container url will be
	the default (fall back) container for groups that do not have a their own web
	site to access Cyclos.<br>
	More information on this can be found in the <a
	href="${pagePrefix}groups#group_details"><u>group</u></a> and <a
	href="${pagePrefix}groups#group_filter"><u>group filter</u></a> settings.
</ul>

<b>Internationalization</b>
<ul>
	<li><b>Language: </b>
	You can choose from several languages. At the moment seven languages are
	available.
	<li><b>Number format: </b>
	Choose a format to present the comma-dot separation of numbers. At the moment
	we support the American and European number formatting.
	<li><b>Number precision : </b>
	This setting presents the number of digits behind the comma (or decimal point).
	If it is set to zero the application will only work with full numbers. For most
	systems this setting would be set to 2.
	<li><b>High precision : </b>
	This setting presents the number of digits behind the comma (or decimal point)
	for account fees only. Normally this number would be the same as the number
	precision. But in some cases it can be necessary to have a higher precision for
	the accuracy of account fees calculations.
	<li><b>Decimal numbers input method: </b> With this setting you can define if
	the decimal separator (e.g. comma) is filled in automatically, what means the
	user will just types numbers (first option &quot;right to left&quot;) The
	second option (left to right) means that the user will have to type the decimal
	separator.
	<li><b>Time zone: </b> Here you can define the time zone. This only needs
	to be set if the server is located in a different time zone as the users that
	are using the instance.
	<li><b>Date format: </b>
	Choose a format to print dates on the screen.
	<li><b>Time format: </b>
	Choose a time format. The time is used to display the full date/time for
	transactions, alerts, invoices and remarks.
</ul>

<b>Limits </b>
<ul>
	<li><b>Max. iterator results: </b>
	The maximum results (lines) in any print or &quot;download&quot; as csv option.
	This option is set to avoid heavy load on the server.
	<li><b>Max. page results: </b>
	The maximum number of items placed on one search results page.
	<li><b>Max. Ajax results: </b>
	The maximum number of items placed in the automatic completion fields like
	&quot;jump to member&quot; and direct payments and invoices.
	<li><b>Max. upload size: </b>
	This is the maximum upload size for profile and advert images.
	<li><b>Max. image size: </b>
	This is maximum size (width and height) of images (e.g. advertisements and
	profiles). If the uploaded sizes are larger Cyclos will downsize the image
	automatically.
	<li><b>Max. thumbnail size: </b>
	This is maximum size (width and height) of a thumbnail (clickable picture for
	ads and members). The thumbnail will be shown with the same dimensions as the
	original image. Therefore the size can be less if the dimensions are
	&quot;portrait&quot;.
	<li><b>Brokering expiration: </b>
	If this period is set a registered member of a broker will disappear from their
	list. If your system works with broker
	<a href="${pagePrefix}brokering#commission"><u>commissions</u></a>
	you will have to make sure
	that you do not have the commission period set as less than the broker expiry
	period.
	<li><b>Delete messages on trash after: </b>
	This is the maximum period member messages will in the trash bin. After this
	period the messages will be removed.
	<li><b>Maximum e-mail confirmation time for member registration: </b>
	If a member registers externally and e-mail confirmation is enabled (in group
	settings &quot;Validate mail address on public registration &quot;) the member
	will have to confirm within this period.
</ul>

<b>Data display </b>
<ul>
	<li><b>Obligatory e-mail for members: </b> If this option is selected the
	e-mail field will be obligatory (on registration by admin or public
	registration by member)
	<li><b>Member display name: </b> In some cases either the name of the member
	or the login name is displayed. This is for example with the loans and advertisement
 	result lists. With setting you can define whether the login or user name will be
 	displayed in such cases.
	<li><b>Sort order for member search results:</b> Here you can define how the member
	search results are ordered (&quot;Menu: Users &amp; Groups - Manage members&quot;)).
	The default is chronological ordering (on creation date of the member).
	If you choose alphabetical ordering, then the previous element in this form
	(&quot;Member display name&quot;) determines if the members are sorted
	on their real names or on their login names.
	<li><b>Advertisement description format: </b> Here you can chose the
	format of the description (text area). It can be either a normal text box or an
	(wysiwyg - what you see is what you get) editor.
	<li><b>Message format: </b> Here you can define the editor type to
	use for the normal messages between members and administrators and members
	<li><b>Show counters in advertisement categories: </b> If this setting is
	selected the counters (numbers) next to the advertisement categories links (in
	the search advertisements pages when browsing the categories) will be
	displayed. The reason why one might use this if you have one instance contains
	with various groups/communities that function as isolated groups and therefore
	cannot see each other (and neither each other advertisements).<br>
	As the counters are global they will always represent the number of all the
	advertisements in the system (for the specific category of course). This would
	be confusing because the counters would show higher numbers that the amount of
	advertisements in the community.<br>
	So, in case of multi-instance setup it is better to disable the counters.
</ul>

<b>CSV export </b>
<ul>
	<li><b>Show header: </b>
	If this option is selected the first row of the CSV file will show the name of the
	field at the first line of each column (for example, member name, adress, etc)
	<li><b>String quote: </b>
	When this option is selected the &quot;string&quot; fields like description and
	title will be placed between quotes.<br>
	Note: take care that if you leave this option &quot;none&quot; the string
	fields that contain the value separator (comma, tab or semicolon) will break
	and be placed in the next (wrong) column. Therefore it is highly advisable to
	use a string quote.
	<li><b>Value separator: </b>
	This is the separator character for the CSV file. You might need to specify
	this separator when importing the file into a spreadsheet or text processor
	program.
	<li><b>Row break: </b>
	This is the &quot;end of line&quot; character that is used. The default unix
	row break is the standard most used. The &quot;DOS&quot; type can be used for
	windows systems.
</ul>

<b>SMS </b>
<ul>
	<li><b>Enable: </b> If you enable SMS Cyclos will allow payments, payment
	requests, info texts and consulting account information via SMS. The SMS
	module in Cyclos requires an external SMS controller.
	<li><b>Operations channel: </b> Here you select the channel that will be
	used for the module. If there is no SMS channel you have to create it first
	(in &quot;Menu: Settings - Channels&quot;).<br>
	<li><b>Send SMS Web Service URL: </b> The communication between Cyclos and
	the SMS controller will happen via web services. Therefore you will need
	to provide a web service URL.
	<li><b>Custom field representing the mobile phone: </b>You will have to
	define the profile field that will be used by the SMS module. (only custom
	fields that are unique and of the type String will show up in the select
	list)<br>
</ul>
Once that SMS option is active it will show up in the group settings (notifications
section) and as a channel in member notification settings.<br>
Detailed Information about the SMS configuration of Cyclos can be found
at the the <a
href="http://project.cyclos.org/wiki/index.php?title=Cyclos_controller_%28SMS%29#Cyclos_setup"
target="_blank"><u>Wiki</u> </a>.

<br><br><b>Transaction number </b>
<ul>
	<li><b>Transaction number: </b> If this option is enabled every
	transaction in the system will generate an unique transaction number
	(identifier). The format of this identifier can be defined in the
	following fields:
	<ul>
		<li><b>Prefix: </b> Prefix of the identifier (numbers or letters)
		<li><b>Identifier length: </b> The length of identifier
		(sequential)
		<li><b>Suffix: </b> Suffix of the identifier (numbers or letters)

	</ul>
	For example: the transaction number of the first transaction in the
	system with configuration Prefix=abc, length=5, Suffix xyz will be
	&quot;abc00001xyz&quot;
</ul>


<b>Charge back: </b> In this setting you can define the maximum time an
admin can charge back a payment. Charge back means a payment in the
opposite direction will be done. In case the payment generated other
transactions (e.g. fees and loans) all transactions will generate
opposite transactions. The date maximum time you can charge back a
payment can be also be set. The description of the generated opposite
transaction can contain the variables #date and #description Where the
description is the description of the original transaction.
<br><br>Take much care if you charge back a transaction that has related
loans. This might result in errors if the loan has been repaid.

<br><br><b>Scheduled tasks: </b> This setting is normally used for
performance issues. With this setting you can define when the tasks are
run. This mostly used for performance reason in case more than one
Cyclos instances run on the same system. Cyclos has scheduled tasks that
run daily and hourly.
<ul>
	<li><b>Scheduled tasks hour: </b> Here you can set the hour (0-24)
	for the tasks that run daily. An example of a daily task is the check
	for expired advertisements.
	<li><b>Scheduled tasks minute: </b> Here you can set the minute
	(0-60) for the tasks that run daily and hourly (the daily tasks will
	add the minutes to the configured hour above) An example of a hourly
	task is the check account fees.
	<li><b>Rebuild search indexes every: </b> With this setting you
	can define the time and frequency the Cyclos indexes will be rebuild.
	In Cyclos 3.5 the members and advertisements are indexed, which helps
	to make the searches faster. The indexing also allows multiple keyword
	searches and all fields (member profile or advertisement fields) will
	be searched.<br>
	As indexes can become corrupt over time it is a good idea to re-index
	frequently. We suggest to have the indexes rebuild weekly at a quiet
	hour (at night or in the morning). Depending on the amount of
	advertisements and member profiles this can take up some time. The
	process runs in a different thread and does not effect the Cyclos
	functioning.<br>
	The re-indexing can also be done be removing the indexes manually at
	the server (just remove the directory indexes in the WEB-INF directory)
	and restarting the server or instance.
	<br><br>
</ul>
<br><br><b>Extra: Java class for processed payments</b>
    If specific behavior or functionality is needed for payments (that cannot
    be done by configuration) you could create your own	Java class that will
    be invoked every time a payment is processed. This setting applies for
    all transaction types. It is also possible to define a class for a specific
    transaction type in the (<a
		href="${pagePrefix}account_management#transaction_type_details"><u>transaction
    type details</u></a>) - &quot;Run Java class&quot;.
    <br><br>
<b>Note1:</b>
	The class will only be invoked on &quot;processed&quot; payments. That means
	it will not be invoked when payments are scheduled for a future date or pending
	for authorization. (But once scheduled or authorized payments are finally processed
	the class will be invoked).
	<br><br>
	<b>Note2:</b>
	Make sure that the class is available on the server class path, for example WEB-INF/lib.
	<br><br>
<hr>


<A NAME="alerts"></A>
<h2>Alerts settings</h2>
In the Alert settings you can define the limits and thresholds for
alerts that are related to member behavior. In order to make changes
click the &quot;change&quot; button; to save the changes click
&quot;submit&quot;. <br>
At the moment the following alert thresholds can be set:
<ul>
	<li><b>New pending activation members: </b> If this option is
	selected an account alert is generated when a new user registers (at
	the login page)
	<li><b> Given very bad references: </b> when someone has given
	more than &quot;x&quot; references &quot;Very Bad&quot; an alert is
	generated.
	<li><b>Received very bad references: </b> when someone receives
	more than x references &quot;Very Bad&quot; an alert is generated.
	<li><b>Invoice expiration: </b> When someone receives an invoice,
	but does not react at all (neither rejecting it, nor accepting it) then
	after period &quot;x&quot; an alert is generated.
	<li><b>Denied invoices: </b> When someone rejects more than
	&quot;x&quot; invoices, an alert is generated.
	<li><b>Incorrect login attempts: </b> After &quot;x&quot; failed
	login attempts a system alert is generated.
</ul>
<hr>


<A NAME="access"></A>
<h2>Access settings</h2>
Here you can define the settings that have to do with accessing Cyclos.
In order to make changes click the &quot;change&quot; button; to save
the changes click &quot;submit&quot;.
<br> The following options are available:
<ul>
	<li><b>Virtual keyboard for login password: </b> When this option
	is checked the users (both admin and member) will be presented a
	virtual keyboard at the login page. The password needs to be typed
	using the virtual keyboard. A virtual keyboard prevents malicious key
	logging software capturing the password.
	<li><b>Virtual keyboard for transaction password: </b> When this
	option is checked the users (both admin and member) will be presented
	a virtual keyboard when a transaction password is required. The
	location of the keys of the transaction password are displayed in
	random order. The possible characters of the transaction password can
	be defined in the option below &quot;Possible characters in
	transaction password&quot;.
	<li><b>Numeric password: </b> If this option is selected, members
	can only have numeric passwords. This option might be needed if the
	members also make payments with cards and a PIN number. This settings
	does not apply to administrators.
	<li><b>Allow multiple logins for the same user : </b>When this
	option is selected users can login more than once, and from different
	browsers, at the same time.
	<li><b>Allow operator login:</b> If you have <a
	href="${pagePrefix}operators"><u> operators</u>
	</a> enabled, this checkbox must be checked in order to allow operators to
	login.
	<li><b>Admin. session timeout: </b> The time an administrator
	will be logged out after being inactive.
	<li><b>Member session timeout: </b> The time a member will be
	logged out after being inactive.
	<li><b>POSweb session timeout: </b> The time a member or operator
	will be logged out the POSweb page after being inactive.
	<li><b>Admin. access whitelist: </b> Here you can put the ip
	addresses or hostnames of the users that can access the
	administration section. Please put every hostname or ip on a new line
	(return) If you leave the &quot;#Any host&quot; entry empty, any host
	will be allowed to access the administration login page (but user
	name and password is still needed)
	<li><b>Login name generation: </b>
	<ul>
		<li><b>Manual by member: </b> For community networks it is
		common for users to choose their own login name or
		&quot;nickname&quot;. In this case the option &quot;Manual by
		Member&quot; needs to be selected.<br> If this option is
		selected you can specify the minimal and maximal login length and a
		regular expression to force the format of the login name.<br>
		Any character (may or may not match line terminators) \d A digit:
		[0-9] \D A non-digit: [^0-9] \s A whitespace character: [
		\t\n\x0B\f\r] \S A non-whitespace character: [^\s] \w A word
		character: [a-zA-Z_0-9] \W A non-word character: [^\w]<br>
		http://java.sun.com/j2se/1.5.0/docs/api/java/util/regex/Pattern.htm<br>
		It is possible to specify the minimum and maximum login name length
		that can be entered by a member or administrator.
		<li><b>Generated random number (login number): </b> For
		business networks it is common to use generated login names or
		&quot;account numbers&quot;. When this module is activated the
		register forms do not have an login name input field. When the form
		is submitted a login name (random number) will automatically be
		created. Below this option you can specify the length of the
		generated login code.
	</ul>
	<li><b>Possible characters in transaction password: </b> Here you
	can define the characters that will be used (in random order) for the
	transaction password generation. (for the transaction password
	settings refer to the <a href="${pagePrefix}groups#edit_member_group"><u>group
	settings</u></a>).
	</ul>
<hr>


<A NAME="mail"></A>
<h2>Mail settings</h2>
In this page you can set the mail preferences. You can define the
&quot;From address&quot; and the SMTP server parameters. If your mail
server requires TLS you will have to check the TLS option. <br>
In order to make changes click the &quot;change&quot; button; to save
the changes click &quot;submit&quot;.
<hr>


<A NAME="log"></A>
<h2>Log settings</h2>
The log settings define where and how the logs are written. The Cyclos
log files are not written to the database but to text log files on the
server. Therefore it is important that this setting is managed by (or in
collaboration with) the server administrator.<br>
In order to make changes click the &quot;change&quot; button; to save
the changes click &quot;submit&quot;.
<br><br>The following configurations are possible:
<ul>
	<li><b>Action log level: </b> This log will contain information
	about any cyclos actions with the complete information (trace) about
	the action like date, member, etc.<br>
	The following options are possible.
	<ul>
		<li><b>Off: </b> Action log is not used.
		<li><b>Errors only: </b> Action log will only contain errors.
		<li><b>Simple tracing: </b> Action log will log any succes and
		error action. It will show the consequetive method calls.
		<li><b>Detailed tracing:</b> Same as the previous, but will also
		show the values which are passed (parameters) and returned.
	</ul>
	<li><b>Action logfile path / name: </b> Here you can specify the
	path and the filename. If more than one instance is installed on the
	server it is good practice have a separate log directory for each
	instance.
	<ul>
		<li>"/" the local pathname separator
		<li>"%t" the system temporary directory
		<li>"%h" the value of the "user.home" system property
		<li>"%g" the generation number to distinguish rotated logs
		<li>"%u" a unique number to resolve conflicts
	</ul>
	<li><b>Generate log only if data was changed: </b> If this is checked
	only actions that result in database changes will be logged. This basically
	means that all actions that write/store something will be logged, but actions
	such as searches or printing won't be logged. Normally it is sufficient
	to log only database writes.
	<li><b>Transactions log level: </b> This log will contain only
	transactions with all related info like from member/system to
	member/system, amount, date etc.<br>
	The following options are possible.
	<ul>
		<li><b>Off: </b> Transaction log is not used.
		<li><b>Normal: </b> When this options is selected the log will
		contain all transactions with the date, from Member/System to
		Member/System and the amount.
		<li><b>Detailed: </b> This option gives the same information as
		the Normal plus the description and transaction type of the
		transactions.
	</ul>
	<li><b>Transactions logfile path / name</b> <br>
	Here you can specify the path and the filename in the same way as the
	Action logfile path and name (see explanation above)
	<li><b>Account fee log level: </b> This log gives information
	about the account fees (contributions). The cyclos administration
	section also comes with a <a
		href="${pagePrefix}account_management#account_fee_history"><u>
	history of account fees</u></a>. The reason that the account fees can been
	included in the logs was to be able to have a complete logging of any
	thing that happens within Cyclos in the log files (outsite of the
	database). Further the account fee logging is more extended than the
	account fee history in the administration section.<br>
	The following options are possible.
	<ul>
		<li><b>Off:</b> no logging takes place
		<li><b>Errors only:</b> will show only problems
		<li><b>Status changes and errors:</b> alerts status and successes
		are logged
		<li><b>Detailed:</b> logs all transactions.
	</ul>
	<li><b>Account fee logfile path / name</b> <br>
	Here you can specify the path and the filename in the same way as the
	Action logfile path and name (see explanation above)
	<li><b>Scheduled task log level</b> <br>
	The scheduled tasks log contains the account fee log. Information about
	scheduled account fees can also be found in the <a
		href="${pagePrefix}account_management#account_fee_overview"><u>account
	fees management</u></a> fucntion. The reason that they are also included in the
	logs is to have complete logs of any action in Cyclos seperatly from
	the database. The following options are possible.
	<ul>
		<li><b>Off:</b> no logging takes place.
		<li><b>Errors only:</b> Will only show problems
		<li><b>Summerized execution:</b> Just information that the task
		has run with a timestamp.
		<li><b>Detailed exection:</b> Detailed information about the
		task. (be aware that this can become quite extensive, an account fee
		that charges many members will log all separate transactions)
	</ul>
	<li><b>Scheduled task logfile path / name</b> <br>
	Here you can specify the path and the filename in the same way as the
	Action logfile path and name (see explanation above)
	<li><b>Max. files per log: </b> Here you can specify the maximum
	amount of log files. When the maximum of logfiles and files sizes are
	reached the oldest logfile will be deleted and a new one will be
	created. It is good practice to make sure that the logfiles are backed
	up before they are deleted.
	<li><b>Max file size: </b> When the max file size is reached a new
	log file will be created. Of course, the more intensive logging you do,
	the higher the maximum file size may have to be set.
</ul>
<hr>


<A NAME="channels"></A>
<h2>Channels</h2>
Cyclos can be handle requests via different media or
&quot;channels&quot;. These can be internal (Cyclos) channels like Web,
POSweb, mobile phone, and external (payment) channels used for access
from third party software like e-commerce sites, ATM machines and POS
devices.
In order to make it easier to add new channels in the future all
channels are listed dynamically. The existing channels are internal and
for normal Cyclos configuration it is not necessary to change them. <br>
The current (internal) channels are available:
<ul>
	<li><b>Main web access: </b> This is the main access via a web
	browser. Typically with a URL like www.yourdomain.org/cyclos
	<li><b>POS: </b> This channel is used for hardware and software
	POS (point of sale) devices. The channel uses the POS identifier to
	identify the POS. More information about the POS can be found at the <a
		href="${pagePrefix}access_devices#POS"><u>Access devices</u></a> help
	file.
	<li><b>POSweb payments: </b> The POSweb (point of sale) access, as
	a consumer at a shop. Used by <a href="${pagePrefix}operators"><u>
	operators</u></a> or directly by members. It can be accessed by putting /posweb
	or /operator after the instance URL. For example
	www.yourdomain.org/cyclos/operator.<br>
	<li><b>WAP 1 access: </b> (wireless application protocol) is
	normally used by older mobile fones that do not support wap 2. The
	module can be accessed by putting /wap after the domain.
	<li><b>WAP 2 access: </b> WAP 2 provides web access via mobile
	fones. The module can be accessed by putting /mobile after cyclos
	domain.
	<li><b>Webshop payments: </b> The webshop payment channel allows
	payments done from e-commerce software.
</ul>
Internal channels can only be added programmatically. When adding an
external channel and you want to allow payments via that channel you
will have to add the channel to a <a
href="${pagePrefix}account_management#transaction_types"><u>transaction
type</u></a> (the one that will use the channel). It is good practice to have
just one transaction type per channel. <br>
The member groups that will use the channel will also need to have the
channel enabled (in <a href="${pagePrefix}groups#edit_member_group"><u>groups
settings</u></a> ) and the group will also need permissions to perfrom the
specific transaction type.
<hr class="help">

<A NAME="channels_detail"></A>
<h3>Channel detail (new or modify)</h3>
It is unlikely that you will need to add new channels. For the existing
channels you can define the way users are identified and the way they
need to authenticate.<br>

<ul>
	<li><b>Display name: </b> The name of the Channel that will be
	displayed in the list
	<li><b>Internal name: </b> This name is only for internal use.
	<li><b>User identification: </b> Here you can chose how the user
	must indentify her/him self.
	<li><b>Default user identification : </b> When more than one
	identification options are selected you can select here the default
	identification that will be displayed (e.g. in the login form)
	<li><b>Credentials : </b> Here you chose the credential that is
	used the validate the authentication of the user
	<li><b>Supports payment request : </b> This options needs to be
	selected when you want the channel to support a payment request.
	This is an build option in the SMS module. But might be useful
	for other services as well (e.g. IVR)
	<li><b>Web service url : </b>
	In case the payment request option is check you need to define
	a response url.
</ul>
<hr>


<A NAME="web_services_clients"></A>
<h2>Web services clients</h2>
In the webservices clients you can define what external software can
access Cyclos via webservices and you can define what service can be
accessed.
<br><br>You can edit a webservice selecting the edit icon and delete on
with clicking the delete icon. <br>
If you want to add a new web service client click the &quot;submit&quot;
button labeled &quot;New web services client &quot;.
<hr class="help">


<A NAME="web_services_clients_detail"></A>
<h3>Insert/modify Web services client</h3>
Here you can insert a new web services client, or edit an existing. When
done click the &quot;submit&quot; button to save your changes. If you
are modifying an existing, you should first click the &quot;change&quot;
button to make changes.
<ul>
	<li><b>Name: </b> Here you can specify the name. This is only for
	internal use.
	<li><b>Internet address: </b> Here you can specify the IP address
	or domain name (which will resolved to an IP address) that is allowed
	to access the web service. <br>
	Take care that if you want to connect to server via an ISP the same IP
	is probably used for other websites (using hostheaders). This means
	that all these sites would have access to the webinstance. Many times
	the IP to wich resolves an website domain name is another IP the
	website will connect through. In this case you would to contact your
	provider what IP (range) is used to make external connections.<br>
	It is also possible to specify an IP range (e.g. 77.88.45.0-255). Note
	that specifying a range might be a security hole. In case of less
	critical access like viewing advertisements this would not be an issue
	but for more serious actions like payments or viewing member data it is
	preferable only allowing a single IP address. If you specify a hostname
	url the address must be informed without the http prefix.(e.g. for a
	hostname like 'http://www.mydomain.com' the informed address should be
	'www.mydomain.com'). <br>
	<li><b>Channel : </b> Here you can select a channel. This can be a
	channel that you have added or the &quot;build in&quot; channel for
	&quot;Webshop payments&quot;. (The webshop payment channel allows
	payments from e-commerce sites, more information can be found on the
	Cyclos wiki, section: webservices - webshop).
	<li><b>Restricted to member : </b> Here you can define if the web
	service is restricted to a specific member. The type of access will
	depend on the permissions (see below)
	<li><b>HTTP username / password: </b> In this (optional) you can
	specify a username and password. Every http(s) request made by the
	webservice client will need to be authenticated with this user name and
	password.<br>
	If this option is used it is good practice to enable https so that the
	user/password will travel encrypted.
	<li><b>Credentials required: </b> This option is only available if
	a Channel is selected and there is no restriction per member. This
	option allows external software to perform payments but the credentials
	would still be needed. This would typically mean that the member that
	peforms the payment will have to type the credential and the software
	accessing the webservice will capture this and pass it along with the
	payment data to the webservice.
	<li><b>Permissions: </b>
	<ul>
		<li><b>Perform payments: </b> This option is only available if a
		Channel is selected. The service allows payments via (external)
		payment channels. You can select one or more payment types. If the web
		service is restricted by member this member can only perform payments
		(and not receive) via the channel.
		<li><b>Receive payments: </b> This option is also only available
		if a Channel is selected and a (restricted) member is selected. The
		service allows payments via (external) payment channels. You can
		select one or more payment types. The selected restricted by member
		can only receive payments via the selected channel. The payment will
		require the payer <a href="${pagePrefix}passwords#pin"><u>PIN</u></a>.
		<li><b>Chargeback payments : </b> This option is only available
		if a Channel is selected. The service allows <a href="#local"><u>charge
		back payments</u></a> via (external) payment channels. You can select one or
		more payment types.
		<li><b>Search advertisements: </b> Allow to search for
		advertisements (normally within a web site). If the web service is
		restricted by member the service can only retrieve advertisements the
		member (group) can view.<br>
		<li><b>Search members: </b> Allow to search for Members (normally
		within a website). If the webservice is restricted by member the
		service can only retreive member lists and profile fields the member
		(group) can view. <br>
		<li><b>Manage members: </b> Allow to create and modify Members
		for the selected groups. Note that if you have selected search members
		the Web Service client can still view all members in the system<br>
		<li><b>Ignore e-mail and custom field validations: </b> This
		option is only available if one or groups are selected in Manage
		members. When selected the validations on the member profile will be
		ignored.
		<li><b>Webshop payments: </b> Allows to receive payments by
		Cyclos users via the webshop. The webshop payment channel has just one
		permission that can be cheched (webshop payments). The authentication
		of the member is handled via the ticket system. This is explained at
		the Cyclos wiki site > web services ( <a
		href="http://project.cyclos.org/wiki/index.php?title=Web_services"><u>
		http://project.cyclos.org/wiki/index.php?title=Web_services</u></a>).
		<li><b>Access account details: </b> All the search of account
		details and transactions. If the webservice is restricted by member
		the service can only retrieve transactions of the member.
		<li><b>Access informations: </b> This options allows a webservice
		to access information about channels and credentials. It can be used
		for example to know what channels a members has enabled
		<li><b>Send SMS messages: </b> When this option is selected a
		webservice can instruct Cyclos to send an SMS message.
		<li><b>Get info texts: </b> This option gives access to retreive
		the <a href="${pagePrefix}content_management#infotexts"><u>info
		texts</u></a>
	</ul>
</ul>
<hr>


<h2>System tasks</h2>

<A NAME="search_indexes"></A>
<h2>Manage indexes</h2>
Indexes are used to facilitate fast searches on users, advertisements
and member records. The indexes allow searching by a keyword or a
combination of keywords. The indexes are not stored in the database but
in separate files at the server. If no indexes are found during the
startup of Cyclos the initialization process will created them.<br>
The rebuild option will recreate the index. This is just is just a
fall back option and only should be used in case of problems. You can
rebuild indexes per index type. If needed you can rebuild
all indexes at once by selecting the &quot;Rebuild al&quot; option.
<hr class="help">


<A NAME="online_state"></A>
<h2>System availability</h2>
In some case you might want to avoid users to login in order to do some
maintenance or configuration within the administration section. In order
to avoid having to shut down Cyclos and showing an error page you can
disallow users to login and disconnect all logged users with this
option. Only administrators with permissions to set the system online
again will be able to login and set the system online again.
<br>
(The permissions can be found at: admin group permissions >
Adminstrative tasks > Set system availability)
<hr>


<A NAME="import_export"></A>
<h2>Import & Export settings</h2>
With this function you can export and import settings, in order to share
them between cyclos instances. All settings can be exported and imported
except for the settings for channels and web services (because these are
usually unique for each instance). The export import function is quite
straightforward. The export generates a (readable) settings.xml file
with the settings. The import applies the settings of the file that is
imported.


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