<div style="page-break-after: always;">
<span class="admin"> 
<br><br>Alerts are for notifying administrators of
various events including system errors, and events concerning members (number of
login attempts with an account, reaching credit limit, etc).

<i>Where to find it.</i><br>
Alerts and logs can be reached via the &quot;Menu: Alerts&quot;.<br>
<br><br><i>How to get it working.</i><br>
<a
	href="${pagePrefix}alerts_logs#system_alerts"><u>System alerts</u></a> are always enabled.<br>
	<a
	href="${pagePrefix}alerts_logs#member_alerts"><u>Member alerts</u></a>
	can be configured in the <a
	href="${pagePrefix}settings#alerts"><u>alerts settings page.</u></a>
<hr>


<a NAME="system_alerts"></a>
<h3>System alerts</h3>
The system alerts window will show a list with system alerts. These can be
alerts related to the system the software is running on and alerts that are not
directly related to member accounts. The following alerts are available:
<ul>
	<li>Application started
	<li>Application shutdown
	<li>Account fee started
	<li>Account fee canceled
	<li>Account fee finished
	<li>Account fee failed
	<li>Account fee recovered
</ul>
You can delete alerts either with the <img border="0" src="${images}/delete.gif"
	width="16" height="16">&nbsp;Delete icon at the right of the alert or
selecting one or more alerts and click the &quot;Remove Selected&quot; button.
This will remove the alerts from the list. However, there is an <a
	href="#alerts_history"><u>Alerts History</u></a> window, where you can do a
search on old Alerts deleted from the list. This way it is possible to detect
recurring alerts and patterns.
<hr class='help'>


<a name="member_alerts"></a>
<h3>Member alerts</h3>
The member alerts window will show a list of alerts that are related to member
behavior. The thresholds can be set in the &quot;Menu: Settings > 
<a href="${pagePrefix}settings#alerts"><u>Alert Settings</u></a >&quot;
page. At the moment the following alerts are available:
<ul>
	<li>People receiving a number of very bad <a href="${pagePrefix}references"><u>
	references</u></a>
	<li>People giving a number of very bad references
	<li>Number of days that someone has not reacted to an incoming invoice
	(system to member only). invoice</u></a>
	<li>Number of rejected invoices
	<li>Maximum of incorrect login names exceeded (someone tried several times
	to login with a wrong login name)
	<li>Temporary blocked users because of exceeding the max amount of tries.
	<li>Maximum of incorrect passwords exceeded (someone tried several times
	to login with a correct login name, but a wrong password).
	<li>New members (self registration) at the login page.
	<li>Expiry of loan that has not been repaid.
</ul>
You can use the <img border="0" src="${images}/delete.gif" width="16"
	height="16">&nbsp;<b>Delete icon</b> to remove the Alert from the list.
However, there is an <a href="#alerts_history"><u>Alerts History</u></a> window,
where you can do a search on old Alerts deleted from the list. This way it is
possible to detect recurring alerts and patterns.
<hr class='help'>


<A NAME="alerts_history"></A>
<h3>Alerts history</h3>
This window allows you to search all the old alerts which have been deleted. If
you want to see the recent alerts, please go to &quot;Menu: Alerts > System
Alerts&quot; and &quot;Menu: Alerts > Member Alerts&quot;. The new alerts
(alerts which have not been deleted from the list) do not show up in the alerts
history.<br><br>
If you do not fill in the edit boxes, you will get an overview of all account
alerts or system alerts. When you select the alert type as &quot;Member&quot;
the alerts will show the Member name in the alert list. It is also possible to
search for alerts of a specific member. You can do this by filling in the login
and member (auto completion) fields.
<hr class='help'>


<a name="alerts_history_result"></a>
<h3>Search Results alerts history</h3>
This window shows all the old alerts as you specified it in the search criteria of
the window above. More than one page might be available; see below the window to
enter other pages. <br>
If you want to see the recent alerts, please go to &quot;Menu: Alerts > System
Alerts&quot; and &quot;Menu: Alerts > Member Alerts&quot;. The new alerts
(alerts which have not been deleted from the list) do not show up in the alerts
history.
<hr>




<A NAME="error_log"></A>
<h3>Error log</h3>
This page will show a list with all the application errors. You can open and
delete an error directly from the list. When you delete an error it will still
be available in the <a href="#error_history"><u>error log history</u></a> page.
<hr class='help'>


<a name="error_history"></a>
<h3>Search error log history</h3>
This page lets you specify a time period in order to limit the <a
	href="#error_history_result"><u>Search results</u></a> below it. Define the
time period by specifying the begin date and end date by either typing in the
date in the indicated format or clicking on the calendar icon.
<hr class="help">


<A NAME="error_history_result"></A>
<h3>Error log history result</h3>
This page will show a list with all the application errors in the time period
specified in the <a href="#error_history"><u>Search error log history</u></a>
window above it. If none is specified a complete list will be shown. You can
open an error directly from the list. The results are paginated and you can
browse the page by clicking the numbers to the right of &quot;Jump to
page:&quot;.
Errors only appear in this window if they have been deleted from the 
<a href="#error_log"><u>error log</u></a> page (&quot;Menu : Alerts > error log&quot;).
<hr class='help'>


<a name="error_log_details"></a>
<h3>Error log details</h3>
This page will show a list with the details of the application errors. This
information will help the system administrator and Cyclos developers to see what
caused the error.
<br><br><b>Note:</b> An application error is not necessarily a bug. Because of
the flexibility of the Cyclos configuration it is possible to setup a
configuration with conflicting functions. Most of these kind of errors are
caught in Cyclos with a specific error messages but it is not possible to
predict all possible configurations errors.
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