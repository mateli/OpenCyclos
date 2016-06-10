<div style="page-break-after: always;">
<br><br>Cyclos has a section for statistical analysis of
your data. <br>
Statistics is all about trying to quantify how exact your results are. We do
this by performing statistic tests, and by indicating where ever possible the
exactness of the numbers.
<i>Where to find it?</i><br>
You can reach this section via &quot;Menu: Reports > Statistical
Analysis&quot;.
<br><br><i>How to get it working?</i>
Statistics have a <a href="${pagePrefix}groups#manage_group_permissions_admin_system"><u>
permission</u></a> which must be enabled before they will be visible. The
permissions can be found in a block entitled &quot;Reports&quot;, checkbox
&quot;statistics&quot;.<br><br>
If you
want to use certain <a href="${pagePrefix}groups#group_filters"><u>group filters
</u></a> or <a href="${pagePrefix}account_management#payment_filters"><u>
payment filters</u></a> in your statistics, 
you will have to mark them as visible for statistics. This can be done in the
configuration pages for these filters (follow the links). 
<hr>

<a name="choose_category"></a>
<h3>Choose statistics category</h3>
This window gives you the option to choose a statistics category. Clicking one
of the buttons will lead you to the corresponding form, where you can specify
the details of the statistics to be calculated. So, this present form is only
for specifying the main categories.
<br>
You can choose only one category at the time. As the statistical calculations
can be very heavy, allowing all tests and calculations to be performed in one go
could cause a too heavy load on the server.
<br>
You can choose from the following categories:
<ul>
	<li><b>Key Developments</b>: the main parameters determining the health
	and growth of the system (like number of members, trade volume, etc.).<br>
	<li><b>Activity of Members</b>: statistics involving the individual trade
	activity of members.<br>
	<li><b>Finance Statistics</b>: statistics on the system
	accounts: all income and outflows for any system account. <br>
</ul>
&nbsp; More options are to follow in future versions of this software.
<hr class="help">

<a name="forms"></a>
<h2>Statistics forms</h2>
After having made your choice on what
<a href="#choose_category"><u>category</u></a>
of statistics you want to see, you'll be taken to the form where you can specify
what statistics you want to see. These forms always have a specific section
(which is different for each category) where you can choose the items with
checkboxes, and a common section (which is mostly equal for all statistics
categories) where you define parameters for the statistics, such as the
<a href="#periods"><u>period</u></a>
and various
<a href="#filters"><u>filters</u></a>
.

In each statistics form, you first choose the type of statistics you want to see
with the
<a href="#whattoshow"><u>&quot;what to show&quot;</u></a>
drop down box.
<br>
With the checkboxes on the left, you may choose what items you want to see. The
item names are explanative enough, but if you want to verify their precise
definition, you may want to check the
<a href="#glossary"><u>glossary</u></a>
of terms. If you select a graph checkbox of a subject, then usually both tables
and graphs will be shown; if you do not select the graph checkbox, only a table
will be shown.
<br>
After having selected one or more checkboxes, you must specify
<a href="#parameters"><u>common parameters</u></a>
for your selection in the windows below. When you are done and you have checked
everything, click the &quot;submit&quot; button at the bottom of this page to
have everything calculated.
<br><br><b><u>Warning:</u> Please be aware that statistical calculations on large
databases calculations can take some time</b>.<hr>

<a name="key_development"></a>
<h2>Key development
</h3>
Here you can specify which Key Developments Statistics you want to be
calculated. Key Development statistics are meant for getting a general overview
of the developments in your system. You may compare periods and check for
trends, but no statistical analysis is being performed.

For a description of the form, <a href="#forms"><u>click here</u></a>.
<hr>

<a name="member_activity"></a>
<h2>Activity of members
</h3>
Here you can specify which Member Activity Statistics you want to be calculated.
Member Activity Statistics will give you insight in the activity of members.
Also statistical can be performed on different sets of members.

For a description of the form, <a href="#forms"><u>click here</u></a>.
<hr>

<a name="finances"></a>
<h2>Finances</h2>
Here you can specify which Finance Statistics you want to be calculated. Finance
statistics can give you a precise overview of all the incoming and outgoing
flows to/from any system account.

For a description of the form, <a href="#forms"><u>click here</u></a>.
<hr>

<a name="taxes_fees"></a>
<h2>Taxes & Fees</h2>
Here you can specify which statistics on taxes and fees you want
to be calculated. These statistics give you the results for any incoming tax or
fee. (probably still under construction)

For a description of the form, <a href="#forms"><u>click here</u></a>.
<hr>

<a name="parameters"></a>
<h2>Common Parameters and fields for Statistics Forms</h2>
Before you can view the statistic results, you must specify what you want to
see. In general, Cyclos needs to know a few things before it can start
calculating and showing statistic results.
<ul>
	<li>&quot;<a href="#whattoshow"><u>what to show</u></a>&quot;: this drop
	down is on top of the form, the very first item. Here you select the method for
	showing the statistics.<br>
	<li>&quot;<a href="#periods"><u>periods</u></a>&quot;: This box is shown
	below the checkboxes. Here you specify the period over which the result is to
	be calculated. Not all data are periodical data; some data cannot be calculated
	over a period; in such cases, the last day of the period is used. This is
	always indicated in the help file.<br>
	<li>&quot;<a href="#filters"><u>filters</u></a>&quot;: Below the period
	box, there is a filter box. As cyclos does not have any pre-defined notion of
	what members are, nor what trade is, you must specify this via filters. <br>
</ul>

<hr>

<a name="whattoshow"></a>
<h3>&quot;What to show&quot; drop down</h3>
With this drop down, you specify the method on how to show the statistics. You
may choose between the following four methods:
<ul>
	<li>&quot;Single Period:&quot; This gives you a summary of one single <a
		href="#periods"><u>period</u></a>. Normally, no statistical analysis will be
	performed, nor is it possible to show any graph. <br>
	<li>&quot;Compare Periods:&quot; This compares the results for two
	periods, which may be presentable in a bar graph. Choose this if you want to
	see if a certain result has increased or decreased compared to an earlier
	period. A statistical analysis is performed to calculate if the difference
	between the two periods is <a href="#p"><u>statistically significant</u></a>.<br>
	<li>&quot;Through time&quot;: does the same, but over a time range.<br>
	<li>&quot;Distribution&quot;: gives usually a <a href="#histo"><u>histogram</u></a>,
	a graph showing how a certain result is distributed over the population. <br>
</ul>
Not all of these items may be visible, depending on the category of statistics
you choose.
<hr class="help">

<a name="periods"></a>
<h3>Periods</h3>
You should specify the period over which the statistics must be calculated. <br>
Depending on which item you selected, you can specify one or two periods, or
even a range of periods.
<br><br>The &quot;Main Period&quot; is the period over which the statistics are
calculated. This may be compared to the &quot;Period to compare with&quot;. <br>
For each of those two period you must enter a name. This name will be used in
the table column headers and in the graph legends.<br>
If you selected &quot;through time&quot;, the period box will look a bit
different. In this case, you should first select if you want to seen the results
ordered per year, quarter or month, after which you may select a time range over
which the statistics will be calculated. <br>
<br>
Be sure not to to select a range which is too broad: choosing to see the results
of every month over a period of 10 years would not only result in overcrowded
graphs, but will also put a very heavy load on your server and will result in
long waiting times.<br>
In such a case, a popup window will tell you how many &quot;data points&quot;
you may request at most, and ask you to limit your request. A &quot;data
points&quot; refers to one separate calculation. Example: if you selected 5 item
checkboxes, and a range of 13 months, this will result in 5 x 13 = 65
datapoints. Graph checkboxes do not count in this case, as they usually don't
demand extra calculations.
<hr class="help">

<a name="filters"></a>
<h3>Filters</h3>
Before being able to calculate how many members there are, and what the trade
volume is, the program must of course know what you consider to be a
&quot;member&quot; and what you consider to be &quot;trade&quot;. As Cyclos
works with a multitude of custom user groups and transaction types, the
application does not have a predefined notion of what a &quot;member&quot; is,
nor what &quot;trade&quot; is. You must specify that in this window, using
&quot;filters&quot;.
<br><br>Depending on which items you chose, not all filter edits may be visible.
The following filters might be visible:
<ul>
	<li><b>Member filter: </b> With this filter, you specify which <a
		href="${pagePrefix}groups#member_groups"><u>member group</u></a>(s) you
	consider to be members. You may specify more than one group. You <b>must</b>
	specify at least one group. <br>
	In the result pages, the groups you selected here will be indicated as
	&quot;members&quot;.
	<br><br>
	<li><b><a href="${pagePrefix}account_management#payment_filters"><u>payment
	filter</u></a>: </b> With this filter, you specify which transaction types you want to have
	included in the results. Usually, this is what you consider to be
	&quot;trade&quot;.<br>
	You may specify only one payment filter, but note the difference with the
	&quot;payment filter<b>s</b>&quot; drop down, which is described below, and in
	which you may define more than one filter. If there is no type available which
	fits your purpose, you can always create a new payment filter specifically for
	this purpose. (See below)<br>
	<br>
	<b>warning:</b><br>
	Often, the payment filter drop down contains only payments which are relevant
	to the group filter you already selected. It can happen that the payment filter
	drop down is empty: in that case, you probably selected a group which has no
	payments defined. In this case, you can not continue with the statistics. This
	can only be solved in two ways: OR select another group, OR create a new
	paymentfilter for this group. <!-- Put this below in the section on payment filters, in another file -->
	<br><br>To create a new payment filter for statistics:
	<ol>
		<li>go to &quot;Accounts&quot; > &quot;Manage Accounts&quot; in the main
		menu
		<li>then click on the edit icon of &quot;Member account&quot;
		<li>go to button at the very bottom of page which shows up, titled
		&quot;insert new payment filter&quot;.
		<li>Here you fill in the specifications of the new payment filter (see
		also the help of this page). Provide a logical name, don't forget to check the
		&quot;show in reports&quot;-checkbox, otherwise it won't show up in the
		statistics pages (you need not to select anything at &quot;group
		visibility&quot;). <br>
		You can select the transfers you want to include in this filter at the
		&quot;transaction types&quot; drop-down.<br>
		<li>The payment filter should show up in the payment filter drop down on
		the select pages for the statistics.
	</ol>
	In the result pages of the statistics, the payment type you specified will be
	indicated as &quot;trade&quot;.
	<br><br>
	<li><b>payment filterS:</b> (note the s at the end). This is exactly the
	same as the previous item, only here you can select more than one filter. If
	you select more than one payment filter, then the results for the selected
	payment filters are shown and compared; if you select only one payment filter,
	then this filter is split up into its containing transfer types, and the
	results for each of the transfer types is shown.<br>
	<b>Warning:</b> when selecting more than one paymentfilter here, it is
	important that all selected items are distinct and do not overlap. As each
	paymentfilter may contain several transfer types, it is possible that some
	paymentfilters have a transfer type in common. If this is the case, it would be
	impossible to create graphs like pie charts, where all sections need to add up
	to 100%. For this reason, the program performs a check if payment filters are
	not overlapping.
	<br><br>
	<li><b>broker filter: </b> With this filter, you specify which user
	group(s) you consider to be <a href="${pagePrefix}brokering"><u>brokers</u></a>s.
	As with the member filter, you may specify more that one group, and you <b>must</b>
	specify at least one. In the result pages, these groups will be indicated as
	&quot;brokers&quot;.
</ul>
<br>
Although the cyclos program usually shows only relevant filters, it may happen
that certain combinations of filters do not make sense. For example, if you
selected a group filter and payment filter which do not match with each other
(for example, group: &quot;members&quot; and payments: &quot;system to
community&quot;) then of course strange results may be expected.
<br><br>Not all filters are used for all types of statistics. Usually, in the
result page, each table or graph will show which filters were exactly <a
	href="#filtersUsed"><u>used</u></a> for the calculation of its results.
<hr class="help">


<a name="results"></a>
<h2>Statistical Results</h2>
There are some conventions used in presenting the statistical results in cyclos.
This overview explains about those conventions:
<ul>
	<li><a href="#tables"><u>tables</u></a> is the default form of presenting
	the statistics. Click on the link to go to the general description of the
	tables.
	<li><a href="#graphs"><u>graphs</u></a> are only shown if you selected a
	graph checkbox. Click on the link to go to the general description of the
	graphs.
	<li><a href="#tests"><u>statistical tests</u></a> are performed wherever
	reasonable and possible.
	<li><a href="#calculation"><u>sine general conventions</u></a> on the
	calculation of the statistics can be found here.
	<li><a href="#numbers"><u>presentation and exactness of numbers</u></a>:
	generally, a result of 3 means something different than a result of 3.00. Click
	on the link to go to a description of conventions used in cyclos on exactness
	and presentation of numbers.
	<li>How cyclos behaves when there is <a href="#nodata"><u>too few
	data</u></a> available.
</ul>

<hr>

<a name="tables"></a>
<h3>Tables in Statistics</h3>
The default way of presenting data in Cyclos statistics, is in a table. Most
tables (not all) take the following form:
<ul>
	<li><b>first column:</b> the main <a href="#periods"><u>period</u></a> as
	you defined it on the previous page.
	<li><b>second column:</b> the <a href="#periods"><u>period</u></a> to
	compare it with, if you defined this on the previous page.
	<li><b>third column:</b> the relative growth (in %) from the second period
	to the main period.
	<li><b>fourth column:</b> the <a href="#p"><u>p-value</u></a> of a
	statistical test, testing how different the numbers are. This column is not
	always shown.
</ul>
The above counts usually for &quot;compare periods&quot;; if you chose another
method, the tables may look different. <br>
If you didn't specify a graph, then below the table a short <a
	href="#filtersUsed"><u>table</u></a> is shown, displaying the <a href="#filters"><u>filters</u></a>
you specified for this result. Otherwise, this information is shown below the <a
	href="#graphs"><u>graph</u></a>.
<hr class="help">

<a name="graphs"></a>
<h3>Graphs in statistics</h3>
Usually, a graph shows the same data which is in the table right above it.
However, some columns of secundairy data (data which is derived from other
columns) may not be shown in the table, for example growth percentage between
two <a href="#periods"><u>period</u></a>s or a <a href="#p"><u>p-value</u></a>.
For a description of the data itself, please click on the help icon of the
corresponding table (the window above the graph window). This section only
explains about general conventions used in graphs.
<br><br>If you hoover your mouse above a bar in the graph, the numeric value of
the corresponding data is shown at your mouse pointer. <br>
Below the graph a short <a href="#filtersUsed"><u>table</u></a> is shown,
displaying the <a href="#filters"><u>filters</u></a> you specified for this
result.
<hr class="help">


<a name="filtersUsed"></a>
<h3>Filters used</h3>
Below a graph or a table, there is a table added showing the <a href="#filters"><u>filters</u></a>
you specified for this result. In order to perform these calculations, cyclos
needs to know what kind of members should be included in the graph - this is the
<a href="${pagePrefix}groups#group_filters"><u>group</u></a> filter. It also
should know what you consider to be &quot;trade&quot; - this is specified by the
&quot;payment filter&quot;. Also other kinds of filters may have been used. <br>
If a filter was not used for the calculation, then &quot;not used&quot; is printed. If the
filter was used, but you didn't specify any particular groups or payments, then
all members or all payments are included. Only relevant filtes are printed in
this table.
<hr class="help">

<a name="calculation"></a>
<h3>How is it calculated?</h3>
Generally, results are calculated <i>over the <a href="#periods"><u>period</u></a></i>
specified. This means, that for example any member who was a member at some time
in the period is included. In rare occasions the result only is about the state
on the final day of the period; this is always stated in the specific help. Note
that there is no correction for &quot;partial members&quot;. For example: the gross
product of any member inside the period is counted without any correction for
the fact that this member maybe only a member for just 1 day in the period.<br>
Most of the time, the <a href="#median"><u>median</u></a> is used in results,
and not the average.
<hr class="help">

<a name="numbers"></a>
<h3>Presentation and exactness of numbers</h3>
Numbers are generally presented with a range around it, if possible. This range
is a 95%-<a href="#range"><u>confidence interval</u></a> around the mean or <a
	href="#median"><u>median</u></a>. In tables, these numbers may be presented in three
different forms:
<ul>
	<li><b>12.0</b> meaning that a confidence interval could not be created,
	because there was too few data, or because the number was not based on a set of
	numbers.
	<li><b>12.0&nbsp;&#177;&nbsp;3.4</b> meaning that the confidence interval
	is symmetric. 12.0 - 3.4 is the lower limit of the confidence interval; 12.0 +
	3.4 is the upper limit.
	<li><b>12.0 (9.7 - 19.2)</b> meaning that the confidence interval is
	assymmetric, and the <a href="#distribution"><u>distribution is skewed</u></a>.
	The numbers between brackets are the lower and upper limits of the confidence
	interval.
	<li><b>-</b>&nbsp; meaning that the number could not be calculated,
	probably due to the fact that there is not enough data available to make a
	reliable calculation possible.
</ul>
In graphs, the confidence intervals are indicated by errorbars around the data
points.
<hr class="help">

<a name="nodata"></a>
<h3>Too few data</h3>
Statistical analysis is based on the principle that a set of observations is
bundled and representated by one number or point in a graph. But what if this
set of observations is very small? <br>
Theoretically, a statistical test can be performed with 3 or more observations
in a data point. However, this still makes the data point very unreliable: the
higher the number of observations is (n), the better the reliability of the
mean, median or range. <br>
In Cyclos, we do not show statistical results with less than 15 observations, as
we think statistical analysis is not reliable enough beyond that point.
<hr class="help">

<h2>Key Development Results</h2>

<br><br>

<a name="reportsStatsKeydevelopmentsNumberOfMembers"></a>
<h3>Table for Number of Members</h3>
Please see the <a href="#results"><u>general section on results</u></a> for
general conventions in cyclos statistics.
<br><br>The following rows are shown in this table:
<ul>
	<li><b>Members</b>: the number of members according to the member <a
		href="#filters"><u>filter</u></a> you set in the previous page. It counts any
	member who has been a member during the <a href="#periods"><u>period</u></a>
	you specified, even if it was removed from membership during that period, or
	became a new member during that specified period.
	<li><b>New members</b>: any member who was moved into the specified member
	groups during the specified period.
	<li><b>Disappeared members</b>: any member who was - during the period -
	moved from the specified member groups to a group not in the group filter.
</ul>
<hr class="help">

<a name="reportsStatsKeydevelopmentsGrossProduct"></a>
<h3>Table for Gross Product</h3>
Please see the <a href="#results"><u>general section on results</u></a> for
general conventions in cyclos statistics.
<br><br>The single row shows the <a href="#grossProduct"><u>Gross Product</u></a>
for the <a href="#filters"><u>filters</u></a> you specified.
<hr class="help">

<a name="reportsStatsKeydevelopmentsNumberOfTransactions"></a>
<h3>Table for Number of Transactions</h3>
Please see the <a href="#results"><u>general section on results</u></a> for
general conventions in cyclos statistics.
<br><br>The single row shows the <a href="#numberOfTransactions"><u>number
of transactions</u></a> according to the <a href="#filters"><u>filters</u></a> you
specified.
<hr class="help">

<a name="reportsStatsKeydevelopmentsAverageAmountPerTransaction"></a>
<h3>Table for Median Transaction Amount</h3>
Please see the <a href="#results"><u>general section on results</u></a> for
general conventions in cyclos statistics.
<br><br>The single rows shows the <a href="#median"><u>median</u></a> amount per
transaction according to the <a href="#filters"><u>filters</u></a> you
specified.
<hr class="help">

<a name="reportsStatsKeydevelopmentsHighestAmountPerTransaction"></a>
<h3>Table for Highest transaction amount</h3>
Please see the <a href="#results"><u>general section on results</u></a> for
general conventions in cyclos statistics.
<br><br>The single rows shows the highest amount per transaction according to the
<a href="#filters"><u>filters</u></a> you specified.
<hr class="help">

<a name="reportsStatsKeydevelopmentsNumberOfAds"></a>
<h3>Table for Number of Ads</h3>
Please see the <a href="#results"><u>general section on results</u></a> for
general conventions in cyclos statistics.
<br><br>The following rows are shown:
<ul>
	<li><b>Active Ads</b>: the number of Ads which are not expired, nor
	scheduled, but active at the end of the <a href="#periods"><u>period</u></a>.
	<li><b>Scheduled Ads</b>: the number of future Ads (scheduled) at the end
	of the period.
	<li><b>Expired Ads</b>: the number of Ads which which are not valid
	anymore (expired) at the end of the period.
	<li><b>Created Ads</b>: the number of Ads which were newly created over
	the period.
</ul>
<br><br>Note that the first three items are <i>at the end of the period</i>,
while the last item is <i>over the complete period</i>.
<hr class="help">

<a name="reportsStatsKeydevelopmentsThroughTimeMonths"></a><br>
<a name="reportsStatsKeydevelopmentsThroughTimeQuarters"></a><br>
<a name="reportsStatsKeydevelopmentsThroughTimeYears"></a>
<h3>Table for &quot;Through Time&quot;</h3>
Please see the <a href="#results"><u>general section on results</u></a> for
general conventions in cyclos statistics.
<br><br>If you selected &quot;through time&quot; a historical overview of the
selected items will be shown over the time range you selected. <br>
The items are the same as the ones under &quot;compare periods&quot;. <br>
To summarize:
<ul>
	<li><b>number of members</b>: the number of members over each <a
		href="#periods"><u>period</u></a>.
	<li><b>gross product</b>: over each period.
	<li><b>number of transactions</b>: over each period.
	<li><b>transaction amount</b>: average transaction amount over each
	period.
	<li><b>number of ads</b>: number of active ads on the last day of each
	period.
</ul>
Where the period is months, quarters or years. And of course, all according to
the <a href="#filters"><u>filter</u></a>s you specified.
<hr class="help">

<h2>Member Activity Results</h2>

<br><br>

<a name="reportsStatsActivitySinglePeriodGrossProduct"></a> <a
	name="reportsStatsActivityComparePeriodsGrossProduct"></a>
<h3>Table for Gross Product per member</h3>
Please see the <a href="#results"><u>general section on results</u></a> for
general conventions in cyclos statistics.
<br><br>This table shows the <a href="#median"><u>median</u></a> <a href="#grossProduct"><u>gross
product</u></a> per member in two rows:
<ul>
	<li>&quot;per earning member&quot;: only members who had any incoming
	transactions inside the requested <a href="#periods"><u>period</u></a> are
	included.
	<li><i>over all members</i>: all members are included. Due to the nature
	of the <a href="#median"><u>median</u></a> this value will be zero (0) if more
	than half of the members does not have income.
</ul>
Of course, all according to the <a href="#filters"><u>filters</u></a> you
specified.
<br><br>For &quot;compare periods&quot;, the columns show the results for the two
requested periods, then two columns with the number of members on which the
results are based, followed by the growth realized between those two periods,
and the <a href="#p"><u>p-value</u></a> for the test that the two results are
not different. <br>
For &quot;one period&quot;, only the result and the number of members are shown.<br>
<hr class="help">

<a name="reportsStatsActivitySinglePeriodNumberTransactions"></a> <a
	name="reportsStatsActivityComparePeriodsNumberTransactions"></a>
<h3>Table for Number of Transactions per member</h3>
Please see the <a href="#results"><u>general section on results</u></a> for
general conventions in cyclos statistics.
<br><br>This table shows the <a href="#median"><u>median</u></a> <a
	href="#numberOfTransactions"><u>number of transactions</u></a> per member in two rows:<br>
<ul>
	<li>&quot;per trading member&quot;: only members who have been trading
	inside the requested <a href="#periods"><u>period</u></a> are included - both
	incoming or outgoing transfers are counted as trade.
	<li>&quot;over all members&quot;: all members are included. Due to the
	nature of the <a href="#median"><u>median</u></a>, this value will be zero (0)
	if more than half of the members does not trade.
</ul>
Of course, all according to the <a href="#filters"><u>filters</u></a> you
specified.
<br><br>For &quot;compare periods&quot;, the columns show the results for the two
requested periods, then two columns with the number of members on which the
results are based, followed by the growth realized between those two periods,
and the <a href="#p"><u>p-value</u></a> for the test that the two results are
not different. <br>
For &quot;one period&quot;, only the result and the number of members are shown.<br>
<hr class="help">

<a name="reportsStatsActivitySinglePeriodPercentageNoTrade"></a> <a
	name="reportsStatsActivityComparePeriodsPercentageNoTrade"></a>
<h3>Table for Percentage of Members not trading</h3>
Please see the <a href="#results"><u>general section on results</u></a> for
general conventions in cyclos statistics.
<br><br>Only one row is shown, indicating the percentage of members who did not
trade at all during the <a href="#periods"><u>period</u></a> you requested
(meaning, did not pay or receive any sums). As always, &quot;members&quot; and
&quot;trade&quot; defined according to the <a href="#filters"><u>filters</u></a>
you selected.
<br><br>For &quot;compare periods&quot;, the columns show the results for the two
requested periods, the growth realized between those two periods, and the <a
	href="#p"><u>p-value</u></a> for the test that the two results are not different.
<hr class="help">

<a name="reportsStatsActivitySinglePeriodLoginTimes"></a> <a
	name="reportsStatsActivityComparePeriodsLoginTimes"></a>
<h3>Table for login times per member, one period.</h3>
Please see the <a href="#results"><u>general section on results</u></a> for
general conventions in cyclos statistics.
<br><br>Only one row is shown, indicating the number of logins over the <a
	href="#periods"><u>period</u></a> per member. &quot;Members&quot; of course
according to the group <a href="#filters"><u>filter</u></a> you specified - a
member is counted if member on any moment inside the specified period.
<br><br>For &quot;compare periods&quot;, the columns show the results for the two
requested periods, the growth realized between those two periods, and the <a
	href="#p">p-value</a> for the test that the two results are not different.
<hr class="help">

<a name="reportsStatsActivityThroughTimeGrossProduct"></a>
<h3>Table for Gross Product per member through time</h3>
Please see the <a href="#results"><u>general section on results</u></a> for
general conventions in cyclos statistics.
<br><br>This table gives an overview of the <a href="#median"><u>medain</u></a> <a
	href="#grossProduct"><u>gross product</u></a> per member for each point in the time
range you specified. It does this for two types of members:
<ul>
	<li>&quot;with income&quot;: this is the gross product for members who did
	have income inside the specified month, quarter or year.
	<li>&quot;all&quot;: this includes all members available in the specified
	month, quarter or year.
</ul>
For both groups, the number of members on which the gross product is based, is
given in the last two columns.<br>
Note that naturally, the shorter the time span for each point in the graph or
table, the lower the results will be.
<hr class="help">

<a name="reportsStatsActivityThroughTimeNumberTransactions"></a>
<h3>Table for Number of Transactions per member through time</h3>
Please see the <a href="#results"><u>general section on results</u></a> for
general conventions in cyclos statistics.
<br><br>This table shows the <a href="#median"><u>median</u></a> <a
	href="#numberOfTransactions"><u>number of transactions</u></a> per member over the
specified time range, where both incoming and outgoing transactions are counted.
<br>
It does this for two types of members:
<ul>
	<li>&quot;traders&quot;: this gives the number of transactions for members
	who did trade inside the specified month, quarter or year (trade means:
	received or paid any sums).
	<li>&quot;all&quot;: this includes all members available in the specified
	month, quarter or year.
</ul>
For both groups, the number of members on which the gross product is based, is
given in the last two columns.<br>
Note that naturally, the shorter the time span for each point in the graph or
table, the lower the results will be.
<hr class="help">

<a name="reportsStatsActivityThroughTimePercentageNoTrade"></a>
<h3>Table for Percentage of members not trading, through time</h3>
Please see the <a href="#results"><u>general section on results</u></a> for
general conventions in cyclos statistics.
<br><br>This table gives an overview of the percentage of members not trading
during a <a href="#periods"><u>period</u></a>, over the time range you
specified.
<br><br>A member is considered &quot;not trading&quot; if he didn't perform any transaction
(nor paying nor receiving, and according to the payment <a href="#filters"><u>filter</u></a>
you specified) in the specified month, quarter or year. Of course, the shorter
the time span is for each graph point, the higher the percentage of non-traders
will be. In a graph with the results given per month, the percentage non-traders
will naturally be much higher than in a graph with the results given per year. <br>
The last column in the table gives the number of members on which each result
value is based.
<hr class="help">

<a name="reportsStatsActivityThroughTimeLoginTimes"></a>
<h3>Table for logins per member through time</h3>
Please see the <a href="#results"><u>general section on results</u></a> for
general conventions in cyclos statistics.
<br><br>This table gives a historical overview of the <a href="#median"><u>median</u></a>
number of logins a member did into the system, over the specified time range.
Not only the number of logins in each month, quarter or year is given, but in
the last column also the number of members on which this calculation is based is
given.<br>
Note that naturally, the shorter the time span for each graph point, the lower
the number of logins per member will be.
<hr class="help">

<a name="reportsStatsActivityHistogramGrossProduct"></a>
<h3>Histogram for Gross Product per member</h3>
Please see the <a href="#results"><u>general section on results</u></a> for
general conventions in cyclos statistics.
<br><br>This graph gives a <a href="#histo"><u>histogram</u></a> of the <a
	href="#grossProduct"><u>gross product</u></a> per member over the main period,
according to the <a href="#filters"><u>filter</u></a>s you defined.
<hr class="help">

<a name="reportsStatsActivityHistogramNumberTransactions"></a>
<h3>Histogram for Number of Transactions per member</h3>
Please see the <a href="#results"><u>general section on results</u></a> for
general conventions in cyclos statistics.
<br><br>This graph gives a <a href="#histo"><u>histogram</u></a> of the <a
	href="#numberOfTransactions"><u>number of transactions</u></a> per member for the main
<a href="#periods"><u>period</u></a>, according to the <a href="#filters"><u>filter</u></a>s
you defined.
<hr class="help">

<a name="reportsStatsActivityHistogramLogins"></a>
<h3>Histogram for number of logins per member</h3>
Please see the <a href="#results"><u>general section on results</u></a> for
general conventions in cyclos statistics.
<br><br>This graph gives a <a href="#histo"><u>histogram</u></a> of the number of
logins per member for the main <a href="#periods"><u>period</u></a>, according
to the group <a href="#filters"><u>filter</u></a> you defined.
<hr class="help">

<a name="reportsStatsActivityToptenGrossProduct"></a>
<h3>Top ten most active members by Gross Product</h3>
Please see the <a href="#results"><u>general section on results</u></a> for
general conventions in cyclos statistics.
<br><br>This table gives the ten members with the highest <a href="#grossProduct"><u>gross
product</u></a> during the specified main <a href="#periods"><u>period</u></a>.
<hr class="help">

<a name="reportsStatsActivityToptenNumberTransactions"></a>
<h3>Top ten most active members by Number of Transactions</h3>
Please see the <a href="#results"><u>general section on results</u></a> for
general conventions in cyclos statistics.
<br><br>This table gives the ten members with the highest <a
	href="#numberOfTransactions"><u>number of transactions</u></a> during the specified
main <a href="#periods"><u>period</u></a>.
<hr class="help">

<a name="reportsStatsActivityToptenLogin"></a>
<h3>Top ten most active members by Number of Logins</h3>
Please see the <a href="#results"><u>general section on results</u></a> for
general conventions in cyclos statistics.
<br><br>This table gives the ten members with the highest number of logins during
the specified main <a href="#periods"><u>period</u></a>.
<hr class="help">

<h2>Member Activity Results</h2>

<br><br>

<a name="reportsStatsFinancesSinglePeriodOverview"></a>
<h3>Overview Finances One Period</h3>
Please see the <a href="#results"><u>general section on results</u></a> for
general conventions in cyclos statistics.
<br><br>This table gives an overview of incoming and outgoing payments for the
specified system account. The final column gives the difference between income
and expenditure.
<br><br>The payment <a href="#filters"><u>filters</u></a> you specified are shown
in the table; paymentFilters you did not specify but which were relevant for
this account are collected and summarized as &quot;other&quot;. Note that this
&quot;other&quot; category is not shown in the graph, because if this is a large number,
it then will overrule the rest of the picture.
<br><br>If you selected only one payment filter, then this filter is split up
into its containing transfer types, and the transfer types are shown in the
table. Otherwise, the payment filters are shown.<br>
If a graph is also shown, then below the graph the selected filters are shown;
otherwise these are shown below the table.
<hr class="help">

<a name="reportsStatsFinancesSinglePeriodIncome"></a> <a
	name="reportsStatsFinancesSinglePeriodExpenditure"></a>
<h3>Income or Expenditure over one period</h3>
Please see the <a href="#results"><u>general section on results</u></a> for
general conventions in cyclos statistics.
<br><br>This table or graph gives an overview of the income (in case you selected
income) or expenditure (in case you selected expenditure) for a system account.<br>
The table gives the two specified <a href="#periods"><u>period</u></a>s in the
columns, where the third column shows the growth from period II to period I. <br>
The rows of the table give the payment <a href="#filters"><u>filter</u></a>s you
specified.
<br><br>In order to keep an overview, we advise not to select too many
paymentfilters when generating this table/graph.<br>
<hr class="help">

<a name="reportsStatsFinancesThroughTimeIncome"></a> <a
	name="reportsStatsFinancesThroughTimeExpenditure"></a>
<h3>Income or Expenditure through time</h3>
Please see the <a href="#results"><u>general section on results</u></a> for
general conventions in cyclos statistics.
<br><br>This table gives an overview of income (or expenditure) categories
through time. You may want to view these if you are interested how certain
expenditures develop through time. <br>
Each requested paymentfilter has its column in the table and its series in the
graph. We advise you to not request too many series (paymentfilters) in this
graph, as graphs will become easily overpopulated.
<hr class="help">


<br><br><a name="glossary"></a>
<h2>Glossary of terms</h2>
<br><br>


<a name="range"></a><b>Confidence interval</b>
<br><br>A confidence interval is an indication of the precision of the data. It
means that there is a chance of 95% that the found number lies within these
ranges. Often, standard deviation or standard error of mean are used to indicate
the precision of the data. We don't use these because we believe a 95% cofidence
interval is much more intuitive, and has a meaning in the real world (in
contrast to standard deviation, which is rather abstract).
<br><br>Confidence intervals are usually calculated on the assumption of some <a
	href="#distribution"><u>distribution</u></a> of the underlying data; in our
case, as in most of the times we may not assume normal distribution, we use a
binominal distribution of rank sums around the median to calculate the
confidence interval. This means that the absolute values are not used directly,
but ranked from low to high, and these rank numbers are used to calculate the
confidence intervals. This may sometimes lead to asymmetric confidence
intervals.
<hr class='help'>

<a name="distribution"></a> <b>Distribution and skewness</b>
<br><br>In statistics, a result is based on a set of observations, from which you
calculated a mean or <a href="#median"><u>median</u></a>. All these single
observations together usually follow a certain pattern, which is called a
distribution of the numbers. The most natural and often found distribution is
the normal distribution, where the center value is the most abundant, and
outliers become less abundant the farther their distance is from the center
value. A normal distribution is perfectly symmetric. <br>
Most statistical methods are based on the assumption of normal distribution.
However, dayly life is often not so perfect; experience learns that data from
most cyclos databases are not at all distributed normally. Cyclos data often
appears to be skewed, with assymmetric distributions. To give an example: this
might mean that members having a higher than median gross product, often are
farther away from the median than members having a lower than median gross
product.<br>
To show the distribution of something, we use &quot;<a href="#histo"><u>histograms</u></a>&quot;.<br>
<hr class='help'>

<a name="grossProduct"></a> <b>Gross Product</b>
<br><br>This is the sum of all <b>incoming</b> transaction amounts over a certain
<a href="#periods"><u>period</u></a>. So all the units earned over a period.
<hr class='help'>

<a name="histo"></a> <b>Histogram</b>
<br><br>A Histogram is a graph showing how observations are spread over a
population. Horizontally (on the x-axis) in the graph is the parameter in which
you are interested (for example: gross product of each member). This is divided
into logical groups, for example: gross products ranging from 0 to 100 units per
month, 100 to 200 units per month, etc etc. Vertically, on the y-axis, there is
the number of observations counted in each x-axis group. So in our example: 5
members in the 0 to 100 units group, 20 members in the 100 to 200 units per
month group, etc etc. <br>
The graph resulting from this shows the <a href="#distribution"><u><u>distribution</u></u></a>
of personal gross product over members.<br>
Note that in our histograms, observations which fall exactly on the border
between two bars, are counted as belonging to the bar with the highest category
value.
<br><br><b>Note:</b> The program automatically calculates the optimal division of
the x-axis in nice &quot;classes&quot;. In case of very strange distributions (for
example, the vast majority of members doing nothing at all) of underlying data,
the division of classes along the x-axis may not seem optimal, visually.
<hr class='help'>

<a name="median"></a> <b>Median</b>
<br><br>Normally, the mean or average would be used to indicate the value of a
number based on a set of observations. However, the mean is very sensitive to
extreme outliers: if one member has an activity which is 20 times higher than
the rest of the members, this one member influences the mean very heavenly, even
though this member is not at all representative. A sollution to overcome this is
to use the median in stead of the mean. The median is the center of the <a
	href="#distribution"><u>distribution</u></a>: 50% of all observations is less
than the median, and 50% of all observations is greater than the median. The
median is not sensitive to outliers. <br>
Because cyclos copes with data which often has extreme outliers or skewed
distribution, throughout the statistics of cyclos the median is used - unless
stated differently. The use of the median in such cases is general standard in
the world of statistics.
<br><br>In case of calculating a median from a set of integers (= whole numbers),
we use balancing or fairness correction. The median value belonging to set
{0,1,2,2,3,3,3,3,4} and to {2,3,3,3,3,4,5,6,7} are both 3, though in the first
list, the first 3 is taken, and in the second list, the last 3. As this seems
not &quot;fair&quot;, all elements with value 3 are spread equaly over a range from 2.5 to
3.5, and then the value is retrieved from this range via interpolation. This of
course makes no sense with broken number lists, so this approach is only used in
case of integers.
<br><br>Using the median in stead of the average has a few <b>consequences</b>:
<ul>
	<li>Round numbers may be the result, especially in case of a median based
	on a few observations. <br>
	<li>As ranges are calculated on rank numbers, given ranges around medians
	often may be asymmetrical. This feels natural, though, considering the fact
	that the underlying distribution is not symmetrical either.<br>
	<li>In case of underlying distributions with lots of zero's (more than
	50%), the median will of course be 0 too. <br>
</ul>
<hr class='help'>

<a name="membersNotTrading"></a> <b>Members not trading</b>
<br><br>These are members having 0 transactions. So no transactions coming in as
well as no transactions going out.
<hr class='help'>

<a name="N"></a> <b>n</b>
<br><br>the number of items in a set of numbers.
<hr class='help'>

<a name="numberOfTransactions"></a> <b>Number of transactions</b>
<br><br>In contrast to the <a href="#grossProduct"><u>gross product</u></a>, for
the sum of someones transactions, both incoming and outgoing transactions of a
member are counted.
<hr class='help'>

<a name="p"></a> <b>P-value</b>
<br><br>When comparing two or more <a href="#periods"><u>period</u></a>s, a
statistical test is performed whenever possible. The goal of any of these tests
is, to calculate how different two values are.<br>
The outcome of the test is represented by one number: the &quot;p-value&quot;.
This value indicates the chance that two means (or medians) are from an equal
population. In plain language: It is the change the numbers are really equal.
The smaller the p-value, the more different the two numbers are. <br>
By convention, we say that numbers are really different if p is less than 5%.
This is called &quot;statisitcally significant difference&quot;. In plain
language: &quot;if p < 0.05, we say that the numbers are really different. If p
is bigger, then we cannot be sure if the numbers are different or not, but to be
on the sure side, we assume they are not any different.&quot; <br>
Any p-value less than 5% is printed <b>bold</b>. <br>
<hr class='help'>

<a name="tests"></a><b>Statistical Tests</b>
<br><br>A statistical test is usually performed if you want to know if two (or
more) results are really different or not - for example, you would want to know
if the activity of members in this year has increased, compared to the activity
of last year. Is a difference of 5% really different? And a difference of 10%?
20%? Where do we start calling a difference a real difference, and where do we
consider it being just coincidence? <br>
The statistical test can tell you if differences are <b>real</b> differences, or
if the noticed &quot;difference&quot; fall just within the normal ranges of variation and
coincidence, and thus shouldn't be considered as really different. This depends
on the underlying <a href="#distribution"><u>distribution</u></a>, on the sample
size, and on the variation in the population.<br>
The type of test is usually mentioned in the help file. Commonly used tests in
Cyclos are the Wilcoxon RankSumTest, and the binominal test for two sample
proportions. These tests do not assume any underlying distribution, as, in most
cases, we may not assume normal distributions. <br>
The result of a statistical test is expressed as a <a href="#p"><u>p-value</u></a>.<br>
<hr class="help">

</div> <%--  page-break end --%>