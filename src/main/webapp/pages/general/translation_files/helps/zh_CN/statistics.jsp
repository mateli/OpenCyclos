<div style="page-break-after: always;">
<p class="head_description">联环系统有一节是数据的统计分析。<br>
统计是有关试图量化您的结果的精确程度。我们通过进行统计测试及尽可能指出数字的精确性来做到。</p>
<i>在哪里可以找到它？</i><br>
您可以通过 &quot;菜单：报表 > 统计分析&quot; 到达这节。
<p><i>如何使它运作？</i><br>
统计是有 <a href="${pagePrefix}groups#manage_group_permissions_admin_system"><u>权限</u></a> 的，它们必须要被启用才可看得见。
权限可以在 &quot;报表&quot;区块内的 &quot;统计&quot; 复选框中找到。<p>
如果您想在统计中使用某些 <a href="${pagePrefix}groups#group_filters"><u>组别筛选器</u></a> 或 <a
href="${pagePrefix}account_management#payment_filters"><u>付款筛选器</u></a>，您将要把它们配置为统计可看见。
此项工作可以在这些筛选器的配置页面（跟随着链接）做到。
<hr>

<a name="choose_category"></a>
<p class="sub_description">
<h3>选择统计数据类别</h3>
这视窗提供选项让您选择统计类别。点击其中一个按钮将带您到相应的表单，在那里，您可以指定将要计算的统计数据详情。因此，这表单只用于指定主要类别。
<br>
您可以在同一时间内只选择一种类别。由于统计计算可以是非常沉重的，允许所有的测试和计算在同一时间进行可能会导致服务器负荷过重。
<br>
您可以选择以下类别：
<ul>
	<li><b>关键发展</b>：决定系统的健康和成长的主要参数（如：会员数目，贸易数量等）。<br>
	<li><b>会员活动</b>：涉及会员个人贸易活动的统计。<br>
	<li><b>财政统计</b>：系统账户的统计：任何系统账户的所有收入和支出。<br>
	<li><b>税及费用</b>：涉及税和费用账户的统计。<br>
</ul>
这软件的未来版本将有更多选项。
<hr class="help">

<a name="forms"></a>
<p class="sub_description">
<h2>统计表单</h2>
当选择了您想要看到的统计数据 <a href="#choose_category"><u>类别</u></a>
后，您将会被带到可以指定所要的统计数据的表单。这些表单总是有一个可以使用复选框来选择项目的特定节（每种类别都不相同），
以及普通节（所有统计数据类别多数一样），让您定义统计参数，如：<a
href="#periods"><u>期段</u></a> 和各种 <a href="#filters"><u>筛选器</u></a>。
</p>
在每个统计表单，您首先使用 <a href="#whattoshow"><u>&quot;显示什么内容？&quot;</u></a> 下拉框来选择想要看到的统计类型。
<br>
您可以使用左边的复选框来选择想要看到的项目。项目名称的解释是足够的，但如果您想核实它们的明确定义，您可能需要查核用语 <a href="#glossary"><u>词汇表</u></a>。
如果您选择一个题目的图表复选框，接着通常将显示图表和表；如果您没有拣选图表复选框，将只显示表。
<br>
当选定了一个或多个复选框后，您必须指定可供下面视窗选择的 <a href="#parameters"><u>共同参数</u></a>。
完成后，您应该核对每个项目，然后点击在此页面底部的 &quot;提交&quot; 按钮来进行所有的计算。
<p><b><u>警告</u>：请注意，大型数据库的统计计算可能需要一段时间。</b><hr>

<a name="key_development"></a>
<p class="sub_description">
<h2>关键发展
</h3>
在这里，您可以指定想要计算的关键发展统计。关键发展统计意指取得系统发展的一般概况。您可能会对比时期和查验趋势，但没有进行统计分析。
</p>
您可以得到下列项目的统计：
<ul>
	<li><b>会员数量</b>
	<li><b><a href="#grossProduct"><u>生产总值</u></a></b>
	<li><b>交易数量</b>
	<li><b>平均交易金额</b>
	<li><b>广告数量</b>
</ul>
<p>
请 <a href="#forms"><u>点击这里</u></a> 以取得表单的描述。
<hr>

<a name="member_activity"></a>
<p class="sub_description">
<h2>会员活动
</h3>
在这里，您可以指定想要计算的会员活动统计。会员活动统计让您洞悉会员的活动。另外，统计可以在不同的会员群组中进行。
</p>
您可以得到下列项目的统计：
<ul>
	<li><b><a href="#grossProduct"><u>生产总值</u></a></b>
	<li><b>交易数量</b>
	<li><b>没有交易的会员％</b>
	<li><b>登录</b> 显示您的会员登录这软件的次数。
</ul>
<p>
请 <a href="#forms"><u>点击这里</u></a> 以取得表单的描述。
<hr>

<a name="finances"></a>
<p class="sub_description">
<h2>财政统计</h2>
在这里，您可以指定想要计算的财政统计。财政统计可以给您有关任何系统账户的所有收到和支出流量的精确概况。
</p>
您可以得到下列项目的统计：
<ul>
	<li><b>概述</b> 显示转入和转出指定系统账户的付款概述。它可以用条形图比较收入和支出。
	<li><b>收入</b> 以圆饼图显示收入在您指定的筛选器的付款中如何划分。
	<li><b>支出</b> 以圆饼图显示支出在您指定的筛选器的付款中如何划分。 
</ul>
<p>
请 <a href="#forms"><u>点击这里</u></a> 以取得表单的描述。
<hr>

<a name="parameters"></a>
<p class="sub_description">
<h2>统计表单的共同参数和字段</h2>
在您可以查看统计结果之前，必须指定您想要看什么。一般情况下，联环系统需要知道几样东西，然后才可以开始计算和显示统计结果。
<ul>
	<li>&quot;<a href="#whattoshow"><u>显示什么内容？</u></a>&quot;：这下拉框是在表单顶部的第一个项目。在这里，您选择显示统计数据的方法。<br>
	<li>&quot;<a href="#periods"><u>期段</u></a>&quot;：此框显示在复选框的下面。在这里，您指定用来计算结果的期段。
	并非所有数据都是周期的数据；一些数据不能在一个期段中计算，在这种情况下，便使用期段的最后一天。这总是显示在帮助里。<br>
	<li>&quot;<a href="#filters"><u>筛选器</u></a>&quot;：在期段框的下面，有一个筛选器。
	由于联环系统没有任何预先确定的会员或贸易概念，您必须通过筛选器来指定它。<br>
</ul>
</p>
<hr class="help">

<a name="whattoshow"></a>
<h3> &quot;显示什么内容？&quot; 下拉框</h3>
您可以用这下拉框来指定统计数据的显示方法。您可以在下列四种方法中选择：
<ul>
	<li>&quot;一个时期：&quot; 这给予您一个 <a href="#periods"><u>时期</u></a> 的摘要。正常情况下，将不会进行统计分析，也不可能显示任何图表。<br>
	<li>&quot;对比时期：&quot; 这是对比两个时期的结果，可能是以条形图表显示。选择这个，如果您想看某些结果对比较早时期是否有增加或减少。
	如果两个时期之间的差额是 <a href="#p"><u>统计上重要</u></a> 的，便进行计算统计分析。<br>
	<li>&quot;整个时段&quot;：运作一样，但在一个时间范围内。<br>
	<li>&quot;分布&quot;：通常给予 <a href="#histo"><u>直方图</u></a>。显示某些结果在人口的分布情况的图表。<br>
</ul>
并非所有这些项目都可看得见，这是根据您所选的统计类别。
<hr class="help">

<a name="periods"></a>
<h3>期段</h3>
您应该指定统计数据必须计算的期段。<br>
取决于您所选择的项目，您可以指定一个或两个期段，甚至一个范围内的期段。
<p>&quot;主要时期&quot; 是计算统计数据的期段。这可能是相比于 &quot;对比时期&quot;。<br>
对于这两个期段的每个时期，您必须输入一个名称。这名称将用作表中栏的标题和图表中的图例。<br>
如果您选择 &quot;整个时段&quot;，期段框将有点不同。在这种情况下，您应该首先选择您是否想看到结果是按每年、季或月的次序，然后，您可以选择将计算统计数据的时间范围。<br>
<br>
请确定不要选择过于广阔的范围：选择要看到在10年期间每个月的结果将不仅造成图表拥挤，也将给您的服务器带来非常沉重的负荷，而导致长的等待时间。<br>
在这种情况下，弹出视窗会告诉您可以请求 &quot;数据点&quot; 的最大限额，并请您限制您的请求。
&quot;数据点&quot; 是指一个独立的计算。例如：如果您选择5项复选框，而范围是13个月，这将导致有 5 × 13 = 65 数据点。
在这种情况下，图表复选框是不计算在内的，因为它们通常不需要额外的计算。
<hr class="help">

<a name="filters"></a>
<h3>筛选器</h3>
在可以计算会员数目和贸易数量之前，应用程序当然必须知道您认为什么才是 &quot;会员&quot; 和什么才是 &quot;贸易&quot;。
由于联环系统有众多的自定用户组别和交易类型一起运作，应用程序没有一个预先确定的概念关于什么是 &quot;会员&quot;、什么是 &quot;贸易&quot;。
您必须用 &quot;筛选器&quot; 在这视窗中指定。
<p>不是所有的筛选器都可看得见，这是根据您所选的项目。可以看见以下的筛选器：
<ul>
	<li><b>筛选会员：</b> 您可以使用此筛选器来指定您认为是会员的 <a
		href="${pagePrefix}groups#member_groups"><u>会员组别</u></a>。您可以指定一个以上的组别。您 <b>必须</b> 指定至少一个组别。<br>
	在结果页面上，您在这里所选的组别将显示为 &quot;会员&quot;。
	<p>
	<li><b><a href="${pagePrefix}account_management#payment_filters"><u>付款筛选器</u></a>：</b> 您可以使用此筛选器来指定想要包括在结果中的交易类型。
	通常，这就是您认为的 &quot;贸易&quot;。<br>
	您可以指定只有一个付款筛选器，但请注意与 &quot;付款筛选器（多个）<b></b>&quot; 下拉框的差别，这是下文所述，并在那里您可以定义多个筛选器。
	如果没有任何类型适合您的目的，您可以随时特别为这目的新增一个付款筛选器。（见下文）<br>
	<br>
	<b>警告：</b><br>
	付款筛选器下拉框通常只包含有关您已经选定的组别筛选器的付款。有可能发生付款筛选器下拉框是空的：在这种情况下，您可能选择了没有定义付款的组别。
	在这种情况下，您不能继续进行统计计算。这只有两种解决方法：<b>或</b>选择另一组别，<b>或</b>给这组别新增一个付款筛选器。
	<p>要新增统计的付款筛选器：
	<ol>
		<li>转到主菜单的 &quot;账户&quot; > &quot;管理账户&quot; 
		<li>然后点击 &quot;会员账户&quot; 编辑图标
		<li>转到页面最底部显示标记为 &quot;新增付款筛选器&quot; 的按钮。
		<li>在这里，您输入新付款筛选器的规格，（请见此页的帮助）。提供一个有意思的名称，不要忘记拣选
		&quot;在报表显示&quot; 复选框，否则它将不会显示在统计页面（在 &quot;组别可见性&quot;，您不需要作出任何选择）。<br>
		您可以在 &quot;交易类型&quot; 下拉框中选择您想要这筛选器包括的转账。<br>
		<li>付款筛选器应该显示在所选的统计页面的付款筛选器下拉框。
	</ol>
	在统计结果页面上，您所指定的付款方式将显示为 &quot;贸易&quot;。
	<p>
	<li><b>付款筛选器（多个）：</b>（请注意"多个"）。这与上一个项目完全一样，只是在这里您可以选择一个以上的筛选器。
	如果您选择一个以上的付款筛选器，会显示和对比所选的付款筛选器的结果；
	如果您选择只有一个付款筛选器，此付款筛选器会分成所包含的转账类型，并显示每个转账类型的结果。<br>
	<b>警告：</b> 当在这里选择一个以上的付款筛选器，重要的是，所有选定的项目是独特的和没有重叠。
	由于每个付款筛选器可能包含多个转账类型，可能一些付款筛选器会有共同的转账类型。如果是这种情况，就不可能新增例如饼图的图表，当中所有部份的总和必须为100％。
	为了这个原因，应用程序会进行核对是否付款筛选器有重叠。
	<p>
	<li><b>经纪筛选器：</b> 您可以使用此筛选器来指定您认为是 <a href="${pagePrefix}brokering"><u>经纪</u></a> 的用户组别。
	象会员筛选器一样，您可以指定一个以上的组别，并且您 <b>必须</b> 指定至少一个组别。在结果页面上，这些组别将显示为	&quot;经纪&quot;。
</ul>
<br>
虽然联环系统程序通常只显示相关的筛选，可能发生某些筛选组合是没有意义的。例如：如果您所选的组别筛选器和付款筛选器并不配合对方（例如，组别：&quot;会员&quot;
与付款：&quot;系统付给社区&quot;），然后当然可预期有奇怪的结果。
<p>并非所有的筛选器都用于所有类型的统计数据。通常，在结果页面上，每个表或图表将显示计算该结果所 <a href="#filtersUsed"><u>使用</u></a> 的筛选器。
<hr>


<a name="results"></a>
<p class="sub_description">
<h2>统计结果</h2>
在联环系统中有一些常规用来显示统计结果。此概况解释关于这些常规：
<ul>
	<li><a href="#tables"><u>表</u></a> 是显示统计的默认表单。请点击链接转到表的一般描述。
	<li><a href="#graphs"><u>图表</u></a> 是只有当您选择了图表复选框才显示。请点击链接转到图表的一般描述。
	<li><a href="#tests"><u>统计测试</u></a> 在任何合理和可能的地方会进行测试。
	<li><a href="#calculation"><u>一些普通常规</u></a> 可以在这里找到用于统计计算的常规。
	<li><a href="#numbers"><u>数字的显示和精确性</u></a>：一般情况下，结果3与结果3.00的意思是不同的。请点击链接转到使用于联环系统中数字的精确性和显示的常规描述。
	<li>当 <a href="#nodata"><u>太少数据</u></a> 可用时，联环系统如何运作。
</ul>
</p>
<hr class="help">

<a name="tables"></a>
<h3>统计表</h3>
在联环系统统计中，显示数据的默认方式是表。多数的表（不是全部）采用下列表格：
<ul>
	<li><b>第一栏：</b> 您在前一页定义的主要 <a href="#periods"><u>时期</u></a>。
	<li><b>第二栏：</b> 此 <a href="#periods"><u>时期</u></a> 是用来对比您在前一页定义了的时期，如有的话。
	<li><b>第三栏：</b> 从第二时期到主要时期的相对增长（以百分比的格式）。
	<li><b>第四栏：</b> 统计测试的 <a href="#p"><u>p值</u></a>，是用来测试数字的差异程度。本栏并不总是显示的。
</ul>
上述计算通常是为 &quot;对比时期&quot; 的；如果您选择另一方法，该表可能看起来不同。<br>
如果您没有指定图表，在表的下面会出现一个简短 <a href="#filtersUsed"><u>表</u></a>，它显示了您为这结果所指定的 <a href="#filters"><u>筛选器</u></a>。
否则，这资料显示在 <a href="#graphs"><u>图表</u></a> 下面。
<hr class="help">

<a name="graphs"></a>
<h3>统计图表</h3>
图表通常显示与上述表中相同的数据。但是，一些栏的间接数据（由其它栏衍生而来的数据）可能不会显示在表中，例如：两个<a
href="#periods"><u>时期</u></a> 或 <a href="#p"><u>p值</u></a> 之间的增长百分比。
对于数据本身的描述，请点击相应表的帮助图标（图表视窗上方的视窗）。本节只解释有关使用于图表的一般常规。
<p>如果您将鼠标停留在图表上面的条形，相应数据的数值会显示在您的滑鼠指针。<br>
图表下方出现一个简短 <a href="#filtersUsed"><u>表</u></a>，它显示您为这结果所指定的 <a href="#filters"><u>筛选器</u></a>。
<hr class="help">


<a name="filtersUsed"></a>
<h3>使用的筛选器</h3>
在图表或表下面，新增有一个显示这结果所指定的 <a href="#filters"><u>筛选器</u></a> 的表。
要执行这些计算，联环系统需要知道在图表内应该包括的会员种类 — 这是 <a href="${pagePrefix}groups#group_filters"><u>组别</u></a> 筛选器。
它也应该知道您认为什么才是 &quot;贸易&quot; — 这是由 &quot;付款筛选器&quot; 指定的。还有其它种类的筛选器可能已被使用。<br>
如果筛选器没有用于计算，&quot;不用&quot; 便被打印出来。如果使用了筛选器，但您没有指定任何特定的组别或付款，便包括所有会员或所有的付款。只有相关的筛选器才列印在表中。
<hr class="help">

<a name="calculation"></a>
<h3>如何计算？</h3>
结果一般是计算在指定的 <i><a href="#periods"><u>时期</u></a> 内</i>。这意味着，例如：包括在此期间内曽经是会员的任何会员。
在罕见的情况下，结果只是关于期段最后一天的情况；这总是在特定的帮助中说明。请注意，没有为 &quot;部分会员&quot; 修正。
例如：在期段内任何会员的生产总值都计算在内，没有对该会员在此期间内也许只有一天是会员这事实作出任何修正。<br>
多数时间，在结果中使用的是 <a href="#median"><u>中位数</u></a>，而不是平均数。
<hr class="help">

<a name="numbers"></a>
<h3>数字的精确性和显示：</h3>
数字通常是显示在一范围内的，如果可能的话。这范围是95％- <a href="#range"><u>置信区间</u></a> 环绕其平均数或 <a
	href="#median"><u>中位数</u></a>。在表中，这些数字可能以三种不同的形式显示：
<ul>
	<li><b>12.0</b> 意指置信区间不能新增，因为有太少数据，或是因为数字并非基于一组数字。
	<li><b>12.0&nbsp;&#177;&nbsp;3.4</b> 意指置信区间是对称的。12.0 - 3.4 是置信区间的下限；而 12.0 + 3.4 是上限。
	<li><b>12.0（9.7 - 19.2）</b> 意指置信区间是不对称的，其 <a href="#distribution"><u>分布是不对称</u></a> 的。括号内的数字是置信区间的下限和上限。
	<li><b>-</b>&nbsp;意指无法计算数目，可能是由于没有足够的数据进行可靠的计算。
</ul>
在图表中，置信区间是用环绕着数据点的错误列显示出来。
<hr class="help">

<a name="nodata"></a>
<h3>太少数据</h3>
统计分析是建基于捆绑在一起的一批观察的原则，和在图表中用一个数字或点来表示。但是，倘若这批观察非常小，将会怎么样呢？<br>
理论上，统计测试可以以3个或更多观察的数据点执行。但是，这仍然使数据点非常不可靠：观察数目（n）越高，平均数、中位数或范围的可靠性越好。<br>
联环系统中，我们不显示有少于15个观察的统计结果，因为我们认为低于这一点的统计分析是不够可靠的。
<hr>

<p>
<h2>关键发展结果</h2>
</p>
<p></p>

<a name="reportsStatsKeydevelopmentsNumberOfMembers"></a>
<h3>会员数目表</h3>
有关联环系统统计的一般常规，请参阅 <a href="#results"><u>一般结果</u> 节</a>。
<p>在表中显示有以下的列：
<ul>
	<li><b>会员</b>：会员数目是根据您在前一页设置的会员 <a href="#filters"><u>筛选器</u></a>。
	它把您指定的 <a href="#periods"><u>期段</u></a> 中曽经是会员的任何会员计算在内，即使他在此期间已从会籍中删除，或在这指定期间成为新会员。
	<li><b>新会员</b>：在指定期段内转移到指定的会员组别的任何会员。
	<li><b>移离会员</b>：在一段时期内从指定的会员组别转移到不在组别筛选器中的组别的任何会员。
</ul>
<hr class="help">

<a name="reportsStatsKeydevelopmentsGrossProduct"></a>
<h3>生产总值表</h3>
有关联环系统统计的一般常规，请参阅 <a href="#results"><u>一般结果</u> 节</a>。
<p>此单行显示了您指定 <a href="#filters"><u>筛选</u></a> 的 <a href="#grossProduct"><u>生产总值</u></a>。
<hr class="help">

<a name="reportsStatsKeydevelopmentsNumberOfTransactions"></a>
<h3>交易数量表</h3>
有关联环系统统计的一般常规，请参阅 <a href="#results"><u>一般结果</u> 节</a>。
<p>此单行根据您指定的 <a href="#filters"><u>筛选器</u></a> 来显示 <a href="#numberOfTransactions"><u>交易数量</u></a>。
<hr class="help">

<a name="reportsStatsKeydevelopmentsAverageAmountPerTransaction"></a>
<h3>交易金额中位数表</h3>
有关联环系统统计的一般常规，请参阅 <a href="#results"><u>一般结果</u> 节</a>。
<p>此单行根据您指定的 <a href="#filters"><u>筛选器</u></a> 来显示每笔交易金额的 <a href="#median"><u>中位数</u></a>。
<hr class="help">

<a name="reportsStatsKeydevelopmentsHighestAmountPerTransaction"></a>
<h3>交易最高金额表</h3>
有关联环系统统计的一般常规，请参阅 <a href="#results"><u>一般结果</u> 节</a>。
<p>此单行根据您指定的 <a href="#filters"><u>筛选器</u></a> 来显示每笔交易的最高金额。
<hr class="help">

<a name="reportsStatsKeydevelopmentsNumberOfAds"></a>
<h3>广告数目表</h3>
有关联环系统统计的一般常规，请参阅 <a href="#results"><u>一般结果</u> 节</a>。
<p>显示有以下的列：
<ul>
	<li><b>活跃的广告</b>：这是没有过期，也没预定，但在 <a href="#periods"><u>期段</u></a> 结束时活跃的广告数目。
	<li><b>预定的广告</b>：这是在期段结束时未来（预定）的广告数目。
	<li><b>过期广告</b>：这是在期段结束时不再有效（过期）的广告数目。
	<li><b>新增广告</b>：这是在整个期间内新增的广告数量。
</ul>
<p>请注意，首三个项目是 <i>在期段结束时</i>，而最后一个项目是 <i>在整个期间内</i>。
<hr class="help">

<a name="reportsStatsKeydevelopmentsThroughTimeMonths"></a>
<a name="reportsStatsKeydevelopmentsThroughTimeQuarters"></a>
<a name="reportsStatsKeydevelopmentsThroughTimeYears"></a>
<h3>&quot;整个时段&quot; 表</h3>
有关联环系统统计的一般常规，请参阅 <a href="#results"><u>一般结果</u> 节</a>。
<p>如果您选择 &quot;整个时段&quot;，将显示在您所选的时间范围内选定项目的历史概况。<br>
项目是与在 &quot;对比时期&quot; 下的一样。<br>
概述：
<ul>
	<li><b>会员数目</b>：在每个 <a href="#periods"><u>时期</u></a> 内的会员数目。
	<li><b>生产总值</b>：在每个时期内。
	<li><b>交易数目</b>：在每个时期内。
	<li><b>交易金额</b>：在每个时期内的平均交易金额。
	<li><b>广告数目</b>：在每个时期最后一天的活跃广告。
</ul>
时期是指月、季或年。当然，所有都是根据您指定的 <a href="#filters"><u>筛选器</u></a>。
<hr>

<p>
<h2>会员活动结果</h2>
</p>
<p></p>

<a name="reportsStatsActivitySinglePeriodGrossProduct"></a> <a
	name="reportsStatsActivityComparePeriodsGrossProduct"></a>
<h3>会员生产总值表</h3>
有关联环系统统计的一般常规，请参阅 <a href="#results"><u>一般结果</u> 节</a>。
<p>此表在两行显示了每名会员的 <a href="#grossProduct"><u>生产总值</u></a> <a href="#median"><u>中位数</u></a>：
<ul>
	<li>&quot;有收入会员&quot;：只包括在请求 <a href="#periods"><u>时期</u></a> 内曽有任何收到交易的会员。
	<li><i>所有会员</i>：包括所有会员。由于 <a href="#median"><u>中位数</u></a> 的性质，如果有一半以上的会员没有收入，这个值将会是零（0）。
</ul>
当然，所有都是根据您指定的 <a href="#filters"><u>筛选器</u></a>。
<p>对于 &quot;对比时期&quot;，这些栏显示两个请求时期的结果，然后有两个栏显示结果所根据的会员数目，接着是这两个时期之间的实际增长，和测试这两个结果是否不同的
<a href="#p"><u>p值</u></a>。<br>
对于 &quot;一个时期&quot;，只显示结果和会员数目。<br>
<hr class="help">

<a name="reportsStatsActivitySinglePeriodNumberTransactions"></a> <a
	name="reportsStatsActivityComparePeriodsNumberTransactions"></a>
<h3>会员交易次数表</h3>
有关联环系统统计的一般常规，请参阅 <a href="#results"><u>一般结果</u> 节</a>。
<p>此表在两行显示了每名会员的 <a
	href="#numberOfTransactions"><u>交易次数</u></a> <a href="#median"><u>中位数</u></a>：<br>
<ul>
	<li>&quot;有交易会员&quot;：只包括在请求 <a href="#periods"><u>时期</u></a> 内曽有交易的会员 — 收到或发出的转账都计算为交易。
	<li>&quot;所有会员&quot;：包括所有会员。由于 <a href="#median"><u>中位数</u></a> 的性质，如果有一半以上的会员没有交易，这个值将会是零（0）。
</ul>
当然，所有都是根据您指定的 <a href="#filters"><u>筛选器</u></a>。
<p>对于 &quot;对比时期&quot;，这些栏显示两个请求时期的结果，然后有两个栏显示结果所根据的会员数目，接着是这两个时期之间的实际增长，和测试这两个结果是否不同的
<a href="#p"><u>p值</u></a>。<br>
对于 &quot;一个时期&quot;，只显示结果和会员数目。<br>
<hr class="help">

<a name="reportsStatsActivitySinglePeriodPercentageNoTrade"></a> <a
	name="reportsStatsActivityComparePeriodsPercentageNoTrade"></a>
<h3>无交易会员百分比表</h3>
有关联环系统统计的一般常规，请参阅 <a href="#results"><u>一般结果</u> 节</a>。
<p>只显示一行，指出在您请求的 <a href="#periods"><u>期段</u></a> 内完全没有交易的会员的百分比（意指没有任何支付或收到金额）。一如往常，&quot;会员&quot; 和
&quot;交易&quot; 的定义是根据您所选的 <a href="#filters"><u>筛选器</u></a>。
<p>对于 &quot;对比时期&quot;，这些栏显示两个请求时期的结果、这两个时期之间的实际增长，和测试这两个结果是否不同的 <a href="#p"><u>p值</u></a>。
<hr class="help">

<a name="reportsStatsActivitySinglePeriodLoginTimes"></a> <a
	name="reportsStatsActivityComparePeriodsLoginTimes"></a>
<h3>会员登录次数（一个时期）表</h3>
有关联环系统统计的一般常规，请参阅 <a href="#results"><u>一般结果</u> 节</a>。
<p>只显示一行，指出在 <a href="#periods"><u>期段</u></a> 内每名会员的登录次数。
&quot;会员&quot; 当然是根据您指定的组别 <a href="#filters"><u>筛选器</u></a> — 如果会员在指定时期内的任何时间曽经是会员也计算在内。
<p>对于&quot;对比时期&quot;，栏位显示两个请求时期的结果，这两个时期之间的实际增长，和测试这两个结果是否不同的 <a href="#p">p值</a>。
<hr class="help">

<a name="reportsStatsActivityThroughTimeGrossProduct"></a>
<h3>会员生产总值（整个时段）表</h3>
有关联环系统统计的一般常规，请参阅 <a href="#results"><u>一般结果</u> 节</a>。
<p>此表给予在您指定的时间范围内每点的每名会员的 <a
	href="#grossProduct"><u>生产总值</u></a> <a href="#median"><u>中位数</u></a> 的概况。这是为两种会员类型做的：
<ul>
	<li>&quot;有收入&quot;：这是在指定的月、季或年内有收入的会员的生产总值。
	<li>&quot;所有&quot;：这包括在指定的月、季或年内所有的会员。
</ul>
为了这两个组别，生产总值所建基的会员数目是显示在最后的两个栏。<br>
请注意，当然，图表或表中每点的时间跨度越短，其结果将会是越低。
<hr class="help">

<a name="reportsStatsActivityThroughTimeNumberTransactions"></a>
<h3>会员交易次数（整个时段）表</h3>
有关联环系统统计的一般常规，请参阅 <a href="#results"><u>一般结果</u> 节</a>。
<p>此表显示了在指定的时间范围内每名会员的 <a href="#numberOfTransactions"><u>交易次数</u></a> <a href="#median"><u>中位数</u></a>，收到和发出的交易也计算在内。
<br>
这是为两种会员类型做的：
<ul>
	<li>&quot;有交易&quot;：这提供在指定的月、季或年内有进行交易的会员的交易次数（交易意指：收到或支付的任何总额）。
	<li>&quot;所有&quot;：这包括在指定的月、季或年内所有的会员。
</ul>
为了这两个组别，生产总值所建基的会员数目是显示在最后的两个栏。<br>
请注意，当然，图表或表中每点的时间跨度越短，其结果将会是越低。
<hr class="help">

<a name="reportsStatsActivityThroughTimePercentageNoTrade"></a>
<h3>无交易会员百分比（整个时段）表</h3>
有关联环系统统计的一般常规，请参阅 <a href="#results"><u>一般结果</u> 节</a>。
<p>此表提供了在您指定的 <a href="#periods"><u>时段</u></a> 范围内没有交易的会员的百分比概况。
<p>会员被认为是 &quot;无交易&quot; 如果他在指定的月、季或年没有进行任何交易（没有支付，也没有收到，并根据您指定的付款<a href="#filters"><u>筛选器</u></a>）。
当然，每个图表点的时间跨度越短，无交易的百分比将会越高。在提供每月结果的图表中，无交易的百分比将自然地远高于在每年结果的图表。<br>
表中的最后一栏给予每个结果值所建基的会员数目。
<hr class="help">

<a name="reportsStatsActivityThroughTimeLoginTimes"></a>
<h3>会员登录次数（整个时段）表</h3>
有关联环系统统计的一般常规，请参阅 <a href="#results"><u>一般结果</u> 节</a>。
<p>此表提供了在指定的时间范围内会员登录系统的次数 <a href="#median"><u>中位数 </u></a> 的历史概况。
不仅给于每月、季或年的登录次数，在最后一栏也显示这计算所依据的会员数目。<br>
请注意，当然，每个图表点的时间跨度越短，每名会员的登录次数将会越低。
<hr class="help">

<a name="reportsStatsActivityHistogramGrossProduct"></a>
<h3>会员生产总值直方图</h3>
有关联环系统统计的一般常规，请参阅 <a href="#results"><u>一般结果</u> 节</a>。
<p>此图表根据您定义的 <a href="#filters"><u>筛选器</u></a>，提供了在主要时期内每名会员的 <a
	href="#grossProduct"><u>生产总值</u></a> <a href="#histo"><u>直方图</u></a>。
<hr class="help">

<a name="reportsStatsActivityHistogramNumberTransactions"></a>
<h3>会员交易次数直方图</h3>
有关联环系统统计的一般常规，请参阅 <a href="#results"><u>一般结果</u> 节</a>。
<p>此图表根据您定义的 <a href="#filters"><u>筛选器</u></a>，提供了在主要 <a href="#periods"><u>时期</u></a> 内每名会员的 <a
	href="#numberOfTransactions"><u>交易次数</u></a> <a href="#histo"><u>直方图</u></a>。
<hr class="help">

<a name="reportsStatsActivityHistogramLogins"></a>
<h3>会员登录次数直方图</h3>
有关联环系统统计的一般常规，请参阅 <a href="#results"><u>一般结果</u> 节</a>。
<p>此图表根据您定义的组别 <a href="#filters"><u>筛选器</u></a>，提供了在主要 <a href="#periods"><u>时期</u></a> 内每名会员的登录次数 </a> <a href="#histo"><u>直方图</u></a>。
<hr class="help">

<a name="reportsStatsActivityToptenGrossProduct"></a>
<h3>十大生产总值最活跃会员</h3>
有关联环系统统计的一般常规，请参阅 <a href="#results"><u>一般结果</u> 节</a>。
<p>此表提供了在指定的主要 <a href="#periods"><u>时期</u></a> 内有最高 <a href="#grossProduct"><u>生产总值</u></a> 的前十名会员。
<hr class="help">

<a name="reportsStatsActivityToptenNumberTransactions"></a>
<h3>十大交易次数最活跃会员</h3>
有关联环系统统计的一般常规，请参阅 <a href="#results"><u>一般结果</u> 节</a>。
<p>此表提供了在指定的主要 <a href="#periods"><u>时期</u></a> 内有最多 <a href="#numberOfTransactions"><u>交易次数</u></a> 的前十名会员。
<hr class="help">

<a name="reportsStatsActivityToptenLogin"></a>
<h3>十大登录次数最活跃会员</h3>
有关联环系统统计的一般常规，请参阅 <a href="#results"><u>一般结果</u> 节</a>。
<p>此表提供了在指定的主要 <a href="#periods"><u>时期</u></a> 内有最多登录次数的前十名会员。
<hr class="help">

<p>

<a name="reportsStatsFinancesSinglePeriodOverview"></a>
<h3>一个时期的财政概况</h3>
有关联环系统统计的一般常规，请参阅 <a href="#results"><u>一般结果</u> 节</a>。
<p>此表提供指定系统账户的收到和发出付款的概况。最后的一栏提供收入和支出之间的差额。
<p>在表中显示了您所指定的付款 <a href="#filters"><u>筛选器</u></a>；您没有指定但与此账户有关的付款筛选器会被收集和归纳为 &quot;其它&quot;。
请注意，这 &quot;其它&quot; 类别不会显示在图表中。因为如果这是一个大数目，将会影响其余的数据。
<p>如果您选择只有一个付款筛选器，此筛选器会分成其包含的转账类型，并且显示该些转账类型在图表中。否则会显示各付款筛选器。<br>
如果图表也显示出来，那幺，选定的筛选器会显示在图表的下面；否则会显示在表的下面。
<hr class="help">

<a name="reportsStatsFinancesSinglePeriodIncome"></a> <a
	name="reportsStatsFinancesSinglePeriodExpenditure"></a>
<h3>一个时期的收入或支出</h3>
有关联环系统统计的一般常规，请参阅 <a href="#results"><u>一般结果</u> 节</a>。
<p>此表或图表提供了系统账户的收入（如果您选择了收入）或支出（如果您选择了支出）的概况。<br>
此表在栏中提供两个指定的 <a href="#periods"><u>时期</u></a>，当中第三栏显示从第二期段到第一期段的增长。<br>
表的行提供了您指定的付款 <a href="#filters"><u>筛选器</u></a>。
<p>要保持概况，当生成此表/图表时，我们建议不要选择太多的付款筛选器。<br>
<hr class="help">

<a name="reportsStatsFinancesThroughTimeIncome"></a> <a
	name="reportsStatsFinancesThroughTimeExpenditure"></a>
<h3>整个时段的收入或支出</h3>
有关联环系统统计的一般常规，请参阅 <a href="#results"><u>一般结果</u> 节</a>。
<p>此表提供了整个时段收入（或支出）类别的概况。如果您有兴趣某些支出在整个时段如何发展，您可能想要查看此表。<br>
每个请求的付款筛选器在表中有自己的行及在图表中有自己的系列。我们建议您在图表中不要请求太多系列（付款筛选器），因为图表将容易成为太拥挤。
<hr>


<p><a name="glossary"></a>
<h2>术语词汇表</h2>
<p></p>


<a name="range"></a><b>置信区间</b>
<p>置信区间是表示数据的精确性。这意味着，有95％的机会该找到的数字位在这些范围内。标准偏差或平均标准误差经常是用来表明数据的精确性。
我们不使用这些，因为我们相信95％的置信区间更直觉，并在现实世界中具有意义（与相当抽象的标准偏差形成对照）。
<p>置信区间通常假设一些基本数据 <a href="#distribution"><u>分布</u></a> 而计算；
就我们的情况而言，由于在大多数时候，我们未必能假设正态分布，所以我们使用环绕着中位数的级别总和的二项式分布来计算置信区间。
这意味着，不直接使用绝对值，而是从低到高排列，而这些排列数字是用来计算置信区间。这有时会导致不对称的置信区间。
<hr class='help'>

<a name="distribution"></a> <b>分布和偏斜</b>
<p>在统计方面，结果是建基于一批观察来计算平均数或 <a href="#median"><u>中位数</u></a>。所有这些单一的观察一起通常遵循一定的模式，这是所谓的数量分布。
最自然的和经常发现的分布是正态分布，其中心数值是最多出现的，而从中心数值距离越远，异常数据的出现程度变得越少。正态分布是完全对称的。<br>
大多数的统计方法是基于正态分布的假设。然而，日常生活往往不是完美的；从经验得知，来自大部份联环系统数据库的数据根本不是正常地分布的。
联环系统数据常常出现偏斜，有不对称分布。举一个例子：这可能意味着，会员有一个高于中位数的生产总值，往往比低于中位数生产总值的会员是距离中位数更远。<br>
我们使用 &quot;<a href="#histo"><u>直方图</u></a>&quot; 来显示数值的分布。<br>
<hr class='help'>

<a name="grossProduct"></a> <b>生产总值</b>
<p>这是在一定 <a href="#periods"><u>时期</u></a> 内所有 <b>收到</b> 的交易金额的总和；所以是在一个时期内赚得的所有单位。
<hr class='help'>

<a name="histo"></a> <b>直方图</b>
<p>直方图是个显示观察数据如何分布的图表。在图表中的水平轴（X轴）是您感兴趣的参数（例如：每名会员的生产总值）。
这分成逻辑组别，例如：生产总值范围从每月0到100单位、每月100至200单位，等等。垂直，在Y轴上，是在每个X轴组别计算的观察数目。
因此，在我们的例子：每月有5名会员在0至100单位组别、有20名会员在100至200单位组别，等等。<br>
这造成的结果图表显示个人生产总值的会员数目 <a href="#distribution"><u><u>分布</u></u></a>。<br>
请注意，在我们的直方图，完全准确地落在两个条形之间边界上的观察，都算作属于有较高值类别的条形。
<p><b>注：</b> 程序自动计算出X轴最理想的精密 &quot;级别&quot; 分布。
如果是分布非常奇怪的基本数据（例如：绝大多数会员都完全无所作为），沿着X轴的级别分布视觉上似乎可能不理想。
<hr class='help'>

<a name="median"></a> <b>中位数</b>
<p>正常情况下，平均数被用来说明建基于一批观察上的数据值。
但是，平均数对极端的异常值非常敏感：如果有一个会员的活动比其余会员高出20倍以上，这一会员重大影响该平均数，即使这个会员不是有代表性。
一个解决辨法是使用中位数代替平均数。中位数是 <a href="#distribution"><u>分布</u></a> 的中心：所有观察的50％是比中位数少，所有观察的50％是比中位数大。
中位数对异常值不敏感。<br>
由于联环系统经常要应付极端的异常值或倾斜分布的数据，整个联环系统的统计是使用中位数 — 除非规定不同。在这种情况下使用中位数是世界统计的一般标准。
<p>如果从一套整数（=完整数字）计算中位数，我们使用平衡或公平更正。
属于集（0,1,2,2,3,3,3,3,4）和（2,3,3,3,3,4,5,6,7）的中位数值都是3，虽然在第一列表中，采用第一个3，而在第二个列表中，用最后的3。
由于这似乎是不 &quot;公平&quot;，所有具有3值的要素是从 2.5 至 3.5 的范围内相等地分布，然后通过插值从这范围取回该值。
对于断裂的列表当然是没有意义的，因此这种方法只用于整数。
<p>用中位数代替平均数有几个 <b>后果</b>：
<ul>
	<li>结果可能是近似整数，特别是在中位数建基于几个观察的情况下。<br>
	<li>由于范围是用排名数字来计算的，因此环绕着中位数的范围往往可能是不对称的。这感觉却是自然的，考虑到基本的分布也是不对称的。<br>
	<li>如果基本的分布有很多是零（超过50％），中位数当然也将会是0。<br>
</ul>
<hr class='help'>

<a name="membersNotTrading"></a> <b>无交易会员</b>
<p>这是有0交易的会员。所以，没有收到交易，也没有发出交易。
<hr class='help'>

<a name="N"></a> <b>n</b>
<p>一组数字的项目数量。
<hr class='help'>

<a name="numberOfTransactions"></a> <b>交易次数</b>
<p>与 <a href="#grossProduct"><u>生产总值</u></a> 形成对照，是对于某人的交易总和，把会员的收到和发出交易计算在内。
<hr class='help'>

<b>p值</b>
<p>当对比两个或以上的 <a href="#periods"><u>时期</u></a>，可能的话，会进行统计测试。所有这些测试的目的是计算出两个值有多少不同。<br>
测试的结果是用一个数字来表示：&quot;p值&quot;。此值指出两个平均数（或中位数）是来自相等人口的机会。用普通语言：是数字真正相等的机会。p值越小，两个数字的差异越大。<br>
依照惯例，如果 p值 是少于 5％，我们说数字是真正的不同。这就是所谓的 &quot;统计上有重要差异&quot;。
用普通语言：&quot;如果 p值 < 0.05，我们说，数字是真正的不同。若 p值 是更大的，那么我们便不能肯定数字是否不同，但是保守地说，我们假设他们没有任何不同。&quot; <br>
任何小于 5％ 的 p值 是以 <b>粗体</b> 表示的。<br>
<hr class='help'>

<a name="tests"></a><b>统计测试</b>
<p>如果您想知道两个（或多个）结果是否真的不同，通常会进行统计测试 — 例如：您想知道对比去年同期的活动，今年的会员活动是否有所增加。5％的差别是真正的差异吗？
10％、20％的差异？ 我们在哪里开始声称一个差异为真正的差异，以及我们在哪里认为这只是巧合？ <br>
统计测试可以告诉您，差异是否 <b>真正</b> 的差异，或注意到的 &quot;差异&quot; 是否只是落在正常的变化和巧合范围内，因此不应被视为真正的差异。
这取决于基本的 <a href="#distribution"><u>分布</u></a>、样本的大小和人口的变化。<br>
测试类型通常在帮助中提及。联环系统常用的测试是Wilcoxon RankSumTest，和两个样本比例的二项式测试。
这些测试没有假设任何基本的分布，因为在大多数情况下，我们可能无法假设正态分布。<br>
统计测试的结果是以 <a href="#p"><u>p值</u></a> 来表示的。<br>

</div> <%--  page-break end --%>