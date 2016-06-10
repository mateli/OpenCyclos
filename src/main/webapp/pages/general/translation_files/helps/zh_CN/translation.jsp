<div style="page-break-after: always;">
<span class="admin">
<p class="head_description">
联环系统支持不同的语言，并允许您管理翻译。所有显示给最终用户的文字，是载在具体语言的文件中（每个语言一份），再加上数量有限的静态文本文件，装载较大的文本块。<br>
联环系统附有一些语言可供选择；通过设置菜单，您可以更改安装语言，只需点击几下鼠标。<br>
如果您不满意联环系统附有的翻译，您也可以修改联环系统的个别文字或词语。这个模块还包含一节，通过联环系统，维护所有发出电邮用的讯息和所有发送的电邮通知。</p>
<i>在哪里可以找到它？</i><br>
翻译模块可通过 &quot;菜单：翻译&quot;。有以下子项：
<ul>
	<li><b><a href="#translation_keys"><u>应用程序</u></a>：</b>
	可让您管理所有短小文本词语的翻译，正如它们出现在应用程序的视窗。
	<li><b><a href="#notifications"><u>通知</u></a>：</b>
	让您管理系统发送的通知。
	<li><b>电邮：</b> 允许您管理系统发送的电邮文本。
	<li><b>导入/导出：</b> 可以让您导入或导出翻译文件。这使得联环系统社区与其他联环系统的用户分享他们的翻译。
</ul>
注 1：静态文本文件，如联系和新的网页，不通过翻译模块管理；管理它们，可通过 <a
	href="${pagePrefix}content_management"><u>内容管理</u></a>。
<p>
注 2：这里不可以更改联环系统的语言。语言可以改变自 <a
	href="${pagePrefix}settings#local"><u>菜单：设置 > 本地设置</u></a> 的 &quot;国际化&quot; 区块。<br> 
<hr>

<a name="translation_keys"></a>
<p class="sub_description">
<h2>翻译关键词</h2>
任何出现在联环系统接口的短小书面文字是储存在一份语言文件内；每个语言一份。联环系统接口允许您取代整份语言文件，或者修改文件中的个别关键词。
</p>
如果一个应用程序网页内的 <a href="#key"><u>关键词</u></a> 在 <a href="#language_file"><u>语言文件</u></a> 找不到，在页面显示的关键词会出现在问号之间。
这通常看起来就像这样：&quot;???translationMessage.search.showOnlyEmpty???&quot;。在这种情况下，您可以增加关键词（和它的数值）在语言文件中，通过
&quot;菜单：翻译 > 应用程序&quot;。
<p>如果您不满意在浏览器视窗中出现的翻译，您可以这样修改它：
<ol>
	<li>去翻译关键词的搜索网页（通过 &quot;菜单：翻译 > 应用程序&quot;)，而且您可以在 &quot;数值&quot; 编辑框输入要修改的文字。
	这个关键词是不区分大小写，并且如果您只输入部分的关键词，应用程序还将寻找匹配的词语。点击
	&quot;搜索&quot;，以显示结果。
	<li>在结果页面上，使用修改图标（<img border="0" src="${images}/edit.gif" width="16" height="16">）更改该数值。
</ol>
<p>您也可以从文件增加或删除整对关键词/数值。这是有点棘手，您可能需要阅读 <a href="#caution"><u>注意事项</u></a>。
<hr class="help">

<a name="caution"></a>
<h3>增加/删除语言关键词注意事项</h3>
联环系统的接口让您可以从 <a href="#language_file"><u>语言文件</u></a> 修改、增加或删除语言 <a href="#key"><u>关键词</u></a>。
修改这些关键词是一个安全的行动，但增加或删除关键词可有点棘手。如果您完全了解语言关键词在系统如何运作，才应该这样做。<br>
删除一个关键词，仅仅是从语言文件删除，不是从应用程序的网页删除。
当您删除关键词，如果它仍然在应用程序网页使用，下一次该网页将只显示相关的关键词名称标志，通常是不很漂亮的（例如：&quot;???about.bla.something.title???&quot;）。<br>
反之亦然：在语言文件增加了关键词并不帮您什么，如果您不开始使用它。为此，您可以 <a href="${pagePrefix}content_management"><u>自定应用程序网页</u></a>。<br>
也可能发生关键词是在更新后失去了—虽然这是非常罕见的。正常联环系统更新将增加新的翻译关键词。在这种情况下，您可以放心地增加关键词。
<hr class="help">


<A NAME="application"></A>
<h3>搜索翻译关键词</h3>
在此视窗中您可以搜索 <a href="#key"><u>翻译关键词</u></a>。<br>
在适当的修改框输入关键词或 <a href="#value"><u>数值</u></a>。请注意，搜索不区分大小写，而且您不必输入完整的词；搜索将包括部分匹配。
一如往常，您可以不输入任何东西，只需点击 &quot;搜索&quot; —这将返回所有可用的关键词/数值的结果。<br>
选择 &quot;只空值&quot; 复选框，将仅显示没有翻译的关键词（可能在更新后发生）。<br>
搜索结果将出现在下方的 <a href="#application_results"><u>搜索结果列表视窗</u></a>。在此视窗中，您有可能改变每个关键词的翻译。
<p>还可以增加新的翻译关键词；如果您想这样做，单击提交标记为 &quot;新增关键词&quot; 的按钮。您可能需要首先阅读关于这个的 <a href="#caution"><u>注意事项</u></a>。
<p>
注：这不是您想更改联环系统的语言的地方。语言可以在 <a href="${pagePrefix}settings#local"><u>菜单：设置 > 本地设置</u></a> 的 &quot;国际化&quot; 区块改变。<br> 
<hr class="help">

<a name="application_results"></a>
<h3>搜索结果（翻译关键词/数值）</h3>
此视窗显示您在 <a href="#application"><u>上面视窗</u></a> 定义搜索的结果。<br>
在此视窗中您可以选择一个关键词，并删除它（通过 <img border="0"
	src="${images}/delete.gif" width="16" height="16">&nbsp;删除图标），或修改它（通过 <img border="0"
	src="${images}/edit.gif" width="16" height="16">&nbsp;编辑图标）。
	如果您想删除多个关键词，您可以选择一个或一个以上的复选框，然后使用 &quot;删除选定&quot; 按钮。<p>
请注意，删除关键词可能会非常棘手，您可能需要首先阅读 <a href="#caution"><u>注意事项</u></a>。
<hr class="help">

<A NAME="edit_key"></A>
<h3>修改翻译关键词</h3>
此视窗中您可以修改翻译 <a href="#key"><u>关键词</u></a> 的 <a href="#value"><u>数值</u></a>。首先点击 &quot;更改&quot;，然后进行更改，然后单击 &quot;提交&quot; 储存更改。<br>
您可以使用多行，但通常这是被忽视，其结果将显示在一个单一行内。
<hr class="help">

<A NAME="insert_key"></A>
<h3>新增翻译关键词</h3>
在这里您可以输入一个新的翻译 <a href="#key"><u>关键词</u></a> 和 <a href="#value"><u>数值</u></a>。
只要输入他们，并点击 &quot;提交&quot;。在增加新的翻译关键词之前，我们建议您阅读
<a href="#caution"><u>注意事项</u></a>  这个问题。
<hr class="help">

<A NAME="import_file"></A>
<h3>导入/导出翻译文件</h3>
在此视窗中您可以 <a href="#import"><u>导入</u></a> 或 <a href="#export"><u>导出</u></a> <a href="#language_file"><u>语言文件</u></a>。请跟除联系以获取更多信息。
<hr class="help">

<a name="import"></a>
<h3>导入语言文件</h3>
在此视窗中的上述矩形是导入一个新的 <a
	href="#language_file"><u>语言文件</u></a>。这将是一个罕见的例子，例如在联环系统增加一个新的语言。正常联环系统的自动更新会增加新的翻译 <a href="#key"><u>关键词</u></a>（如果有）。
<p>首先，您将需要决定 &quot;做什么&quot;。有四个选项：
<ul>
	<li><b>只导入新的关键词：</b> 只导入新关键词，不会影响现有的关键词。
	<li><b>只导入新的关键词和空键：</b> 和上述一样，但现在它也将导入空键（即：关键词的数值是空的，可能是因为翻译是尚未完全完成）。
	<li><b>导入新的和修改了的关键词：</b> 导入新的和修改了的关键词。这意味着，如果您之前修改了一些关键词的数值，他们将被“默认”值覆盖。不再使用的关键词将被删除。
	<li><b>取代整个文件：</b> 它会简单地覆盖整个文件。所有您之前的修改当然会丢失。
</ul>
之后，您将要 &quot;浏览&quot; 在您的计算机存储的翻译文件，并点击 &quot;提交&quot;。
<p>注：您要导入的文件没有必要包含所有关键词—除非您选择 &quot;取代整个文件&quot;。在所有其他情况下，它可以是任何数量的关键词（但必须是正确的格式）。<br>
当您要取代整个文件，确保您导入整个文件。否则，您可能失去现有的关键词。


<a name="export"></a>
<h3>导出语言文件</h3>
导出目前的 <a href="#language_file"><u>语言定义</u></a> 非常简单：只需使用标记为 &quot;导出属性文件&quot; 的 &quot;提交&quot; 按钮。如果您按一下这个按钮，浏览器将接管，并通常询问您是否要储存文件。<br>
导出语言文件将是罕见的情况；您想这样做，通常是您想把自己做的联环系统翻译提供给其他联环系统社区用户。
如果您做了自己的翻译，我们鼓励您与我们联系，因此我们可以在正式的联环系统增加发布您的翻译。见项目的联系地址（<a href="http://project.cyclos.org"><u>http://project.cyclos.org</u></a>）。
<hr>

<a name="notifications"></a>
<h2>通知</h2>
联环系统可以让您管理各种通知的内容，如下视窗。
<hr class="help">

<A NAME="general_notifications"></A>
<h3>一般通知</h3>
此视窗可以显示一般的 <a href="${pagePrefix}preferences"><u>通知</u></a>。通常这些是加入到发出的电邮的前缀和后缀。
您可以点击编辑图标（<img border="0" src="${images}/edit.gif" width="16" height="16">）以更改内容。
<hr class="help">

<A NAME="member_notifications"></A>
<h3>会员通知</h3>
此视窗会显示在各种场合发送给会员的 <a href="${pagePrefix}preferences"><u>通知</u></a>。
您可以点击编辑图标（<img border="0" src="${images}/edit.gif" width="16" height="16">）以更改内容。
<hr class="help">

<A NAME="admin_notifications"></A>
<h3>管理员通知</h3>
此视窗会显示在各种场合发送给管理员的 <a href="${pagePrefix}preferences"><u>通知</u></a>。
您可以点击编辑图标（<img border="0" src="${images}/edit.gif" width="16" height="16">）以更改内容。
<hr class="help">

<A NAME="edit_notifications"></A>
<h3>编辑通知</h3>
该视窗可让您变更通知的内容。要做到这一点，您（一如既往）首先应该点击 &quot;更改&quot;；完成后，您可以点击 &quot;提交&quot; 储存您的更改。<br>
丰富文本编辑器会出现，让您能够使用一些布局功能。您也可以使用某些字段，这取决于您要修改的通知。
<p>
<br>下列 #variables# 可用于通知文本。
#variable name# 在 #..# 之间放置，将被替换有关数值。<br>
                                              
<h2>普遍通知</h2>

<table rules="all" frame="border" cellspacing="10%" cellpadding="10%">
<tr>
	<td><b>设置名称</b></td>
	<td><b>变量</b></td>
	<td><b>取代</b></td>
</tr>
<tr>
	<td rowspan="2">删除经纪代理的备注</td>
	<td>#member#</td>
	<td>会员姓名</td>
</tr>
<tr>
	<td>#login#</td>
	<td>会员登录名</td>
</tr>
<tr>
	<td>邮件主题前缀，<br>
    纯文本邮件正文后缀，<br>
	HTML邮件正文后缀，<br>
	短讯前缀
	</td>
	<td>#system_name#</td>
	<td>您的系统名称</td>
</tr>
</table>
<br>

<h2>会员通知</h2>
<table rules="all" frame="border" cellspacing="10%" cellpadding="10%">
<tr>
	<td><b>设置名称</b></td>
	<td><b>变量</b></td>
	<td><b>取代</b></td>
</tr>
<tr>
	<td rowspan="2">经纪代理到期，<br>
	手动删除经纪代理</td>
	<td>#member#</td>
	<td>经纪会员姓名</td>
</tr>
<tr>
	<td>#login#</td>
	<td>经纪会员登录名</td>
</tr>
<tr>
	<td rowspan="5">新的佣金合同，<br>
	取消的佣金合同</td>
	<td>#broker#</td>
	<td>经纪名称</td>
</tr>
<tr>
	<td>#start_date#</td>
	<td>新合同开始日期</td>
</tr>
<tr>
	<td>#end_date#</td>
	<td>新合同结束日期</td>
</tr>
<tr>
	<td>#amount#</td>
	<td>合同金额</td>
</tr>
<tr>
	<td>#link#</td>
	<td>有关详细信息的链接</td>
</tr>
<tr>
	<td rowspan="5">被接受的佣金合同，<br>
	被拒绝的佣金合同</td>
	<td>#member#</td>
	<td>会员名称</td>
</tr>
<tr>
	<td>#start_date#</td>
	<td>新合同开始日期</td>
</tr>
<tr>
	<td>#end_date#</td>
	<td>新合同结束日期</td>
</tr>
<tr>
	<td>#amount#</td>
	<td>合同金额</td>
</tr>
<tr>
	<td>#link#</td>
	<td>有关详细信息的链接</td>
</tr>
<tr>
	<td>最低单位</td>
	<td>#creditLimit#</td>
	<td>会员的信用额度</td>
</tr>
<tr>
	<td rowspan="2">广告届满<br>
	有兴趣的广告</td>
	<td>#title#</td>
	<td>广告标题</td>
</tr>
<tr>
	<td>#link#</td>
	<td>有关详细信息的链接</td>
</tr>
<tr>
	<td rowspan="3">收到发票</td>
	<td>#member#</td>
	<td>发送会员姓名</td>
</tr>
<tr>
	<td>#login#</td>
	<td>发送会员登录名</td>
</tr>
<tr>
	<td>#link#</td>
	<td>有关详细信息的链接</td>
</tr>
<tr>
	<td rowspan="6">被接受的发票，<br>
	被取消的发票，<br>
	被拒绝的发票</td>
	<td>#member#</td>
	<td>会员名称</td>
</tr>
<tr>
	<td>#login#</td>
	<td>会员登录名</td>
</tr>
<tr>
	<td>#date#</td>
	<td>发票已发送日期</td>
</tr>
<tr>
	<td>#amount#</td>
	<td>发票金额</td>
</tr>
<tr>
	<td>#description#</td>
	<td>发票描述</td>
</tr>
<tr>
	<td>#link#</td>
	<td>有关详细信息的链接</td>
</tr>
<tr>
	<td rowspan="4">收到的发票过期，<br>
	发送的发票过期</td>
	<td>#member#</td>
	<td>会员名称</td>
</tr>
<tr>
	<td>#login#</td>
	<td>会员登录名</td>
</tr>
<tr>
	<td>#amount#</td>
	<td>发票金额</td>
</tr>
<tr>
	<td>#link#</td>
	<td>有关详细信息的链接</td>
</tr>
<tr>
	<td rowspan="3">贷款到期</td>
	<td>#grantDate#</td>
	<td>授予贷款的日期</td>
</tr>
<tr>
	<td>#amount#</td>
	<td>贷款金额</td>
</tr>
<tr>
	<td>#link#</td>
	<td>有关详细信息的链接</td>
</tr>
<tr>
	<td rowspan="2">批准贷款</td>
	<td>#amount#</td>
	<td>贷款金额</td>
</tr>
<tr>
	<td>#link#</td>
	<td>有关详细信息的链接</td>
</tr>
<tr>
	<td rowspan="6">收到付款</td>
	<td>#amount#</td>
	<td>金额</td>
</tr>
<tr>
	<td>#member#</td>
	<td>有关会员名称</td>
</tr>
<tr>
	<td>#login#</td>
	<td>会员登录名</td>
</tr>
<tr>
	<td>#transaction_number#</td>
	<td>交易号码（如果启用）</td>
</tr>
<tr>
	<td>#balance#</td>
	<td>新的余额</td>
</tr>
<tr>
	<td>#link#</td>
	<td>有关详细信息的链接</td>
</tr>
<tr>
	<td rowspan="4">等待授权接收的付款，<br>
	新的等待授权接收的付款，<br>
	新的等待授权支付的付款，<br>
	新的等待经纪授权的付款</td>
	<td>#amount#</td>
	<td>金额</td>
</tr>
<tr>
	<td>#member#</td>
	<td>有关会员名称</td>
</tr>
<tr>
	<td>#login#</td>
	<td>会员登录名</td>
</tr>
<tr>
	<td>#link#</td>
	<td>有关详细信息的链接</td>
</tr>
<tr>
	<td rowspan="3">等待授权付款，<br>
	等待拒绝付款，<br>
	等待取消付款</td>
	<td>#amount#</td>
	<td>金额</td>
</tr>
<tr>
	<td>#date#</td>
	<td>提交付款日期</td>
</tr>
<tr>
	<td>#link#</td>
	<td>有关详细信息的链接</td>
</tr>
<tr>
	<td rowspan="4">预定付款成功处理，<br>
	预定付款失败（付款人），<br>
	预定付款失败（收款人）</td>
	<td>#amount#</td>
	<td>金额</td>
</tr>
<tr>
	<td>#member#</td>
	<td>有关会员名称</td>
</tr>
<tr>
	<td>#login#</td>
	<td>有关会员登录名</td>
</tr>
<tr>
	<td>#link#</td>
	<td>有关详细信息的链接</td>
</tr>
<tr>
	<td rowspan="5">已执行的外部渠道的付款</td>
	<td>#amount#</td>
	<td>金额</td>
</tr>
<tr>
	<td>#member#</td>
	<td>有关会员名称</td>
</tr>
<tr>
	<td>#login#</td>
	<td>有关会员登录名</td>
</tr>
<tr>
	<td>#channel#</td>
	<td>​​渠道（网络、销售终端、短讯等）</td>
</tr>
<tr>
	<td>#link#</td>
	<td>有关详细信息的链接</td>
</tr>
<tr>
	<td rowspan="5">外部渠道的付款请求的付款人过期</td>
	<td>#amount#</td>
	<td>金额</td>
</tr>
<tr>
	<td>#toMemberName#</td>
	<td>要求付款的会员名称</td>
</tr>
<tr>
	<td>#toMemberUsername#</td>
	<td>要求的会员登录名</td>
</tr>
<tr>
	<td>#channel#</td>
	<td>渠道（网络、销售终端、短讯等）</td>
</tr>
<tr>
	<td>#link#</td>
	<td>有关详细信息的链接</td>
</tr>
<tr>
	<td rowspan="5">外部渠道的付款请求的收款人过期</td>
	<td>#amount#</td>
	<td>金额</td>
</tr>
<tr>
	<td>#fromMemberName#</td>
	<td>付款人会员名称</td>
</tr>
<tr>
	<td>#fromMemberUsername#</td>
	<td>付款人会员登录名</td>
</tr>
<tr>
	<td>#channel#</td>
	<td>渠道（网络、销售终端、短讯等）</td>
</tr>
<tr>
	<td>#link#</td>
	<td>有关详细信息的链接</td>
</tr>
<tr>
	<td rowspan="3">收到评语</td>
	<td>#member#</td>
	<td>有关会员名称</td>
</tr>
<tr>
	<td>#login#</td>
	<td>有关会员登录名</td>
</tr>
<tr>
	<td>#link#</td>
	<td>有关详细信息的链接</td>
</tr>
<tr>
	<td rowspan="5">收到的交易反馈意见，<br>
	交易反馈回答， <br>
	交易反馈管理员意见</td>
	<td>#member#</td>
	<td>有关会员名称</td>
</tr>
<tr>
	<td>#login#</td>
	<td>有关会员登录名</td>
</tr>
<tr>
	<td>#amount#</td>
	<td>原付款金额</td>
</tr>
<tr>
	<td>#date#</td>
	<td>原付款日期</td>
</tr>
<tr>
	<td>#link#</td>
	<td>有关详细信息的链接</td>
</tr>
<tr>
	<td rowspan="3">收到账户费用</td>
	<td>#account_fee#</td>
	<td>账户费用名称</td>
</tr>
<tr>
	<td>#amount#</td>
	<td>原付款金额</td>
</tr>
<tr>
	<td>#link#</td>
	<td>有关详细信息的链接</td>
</tr>
<tr>
	<td rowspan="4">发出新认证</td>
	<td>#amount#</td>
	<td>认证金额</td>
</tr>
<tr>
	<td>#issuerName#</td>
	<td>发出认证用户姓名</td>
</tr>
<tr>
	<td>#issuerUsername#</td>
	<td>发出认证用户登录名</td>
</tr>
<tr>
	<td>#link#</td>
	<td>有关详细信息的链接</td>
</tr>
<tr>
	<td rowspan="5">认证状态改变，<br>
	担保状态改变（仅买方）</td>
	<td>#amount#</td>
	<td>认证/担保金额</td>
</tr>
<tr>
	<td>#issuerName#</td>
	<td>发出者姓名</td>
</tr>
<tr>
	<td>#issuerUsername#</td>
	<td>发出者登录名</td>
</tr>
<tr>
	<td>#status#</td>
	<td>新的状态</td>
</tr>
<tr>
	<td>#link#</td>
	<td>有关详细信息的链接</td>
</tr>
<tr>
	<td rowspan="4">认证过期，<br>
	担保过期，<br>
	担保等待授权（只买方），<br>
	新发布的付款义务</td>
	<td>#amount#</td>
	<td>金额</td>
</tr>
<tr>
	<td>#buyerName#</td>
	<td>买方姓名</td>
</tr>
<tr>
	<td>#buyerUsername#</td>
	<td>买方登录名</td>
</tr>
<tr>
	<td>#link#</td>
	<td>有关详细信息的链接</td>
</tr>
<tr>
	<td rowspan="9">担保状态改变</td>
	<td>#amount#</td>
	<td>认证/担保金额</td>
</tr>
<tr>
	<td>#status#</td>
	<td>新的状态</td>
</tr>
<tr>
	<td>#issuerName#</td>
	<td>发出者姓名</td>
</tr>
<tr>
	<td>#issuerUsername#</td>
	<td>发出者登录名</td>
</tr>
<tr>
	<td>#buyerName#</td>
	<td>买方姓名</td>
</tr>
<tr>
	<td>#buyerUsername#</td>
	<td>买方登录名</td>
</tr>
<tr>
	<td>#sellerName#</td>
	<td>卖方姓名</td>
</tr>
<tr>
	<td>#sellerUsername#</td>
	<td>卖方登录名</td>
</tr>
<tr>
	<td>#link#</td>
	<td>有关详细信息的链接</td>
</tr>
<tr>
	<td rowspan="6">担保等待授权</td>
	<td>#amount#</td>
	<td>担保金额</td>
</tr>
<tr>
	<td>#buyerName#</td>
	<td>买方姓名</td>
</tr>
<tr>
	<td>#buyerUsername#</td>
	<td>买方登录名</td>
</tr>
<tr>
	<td>#sellerName#</td>
	<td>卖方姓名</td>
</tr>
<tr>
	<td>#sellerUsername#</td>
	<td>卖方登录名</td>
</tr>
<tr>
	<td>#link#</td>
	<td>有关详细信息的链接</td>
</tr>
<tr>
	<td rowspan="4">被拒绝的付款义务</td>
	<td>#amount#</td>
	<td>金额</td>
</tr>
<tr>
	<td>#sellerName#</td>
	<td>卖方姓名</td>
</tr>
<tr>
	<td>#sellerUsername#</td>
	<td>卖方登录名</td>
</tr>
<tr>
	<td>#link#</td>
	<td>有关详细信息的链接</td>
</tr>
</table>
<h2>管理员通知</h2>
<table rules="all" frame="border" cellspacing="10%" cellpadding="10%">
<tr>
	<td><b>设置名称</b></td>
	<td><b>变量</b></td>
	<td><b>取代</b></td>
</tr>
<tr>
	<td rowspan="6">担保等待授权（仅买方）</td>
	<td>#amount#</td>
	<td>金额</td>
</tr>
<tr>
	<td>#issuerName#</td>
	<td>发出者姓名</td>
</tr>
<tr>
	<td>#issuerUsername#</td>
	<td>发出者登录名</td>
</tr>
<tr>
	<td>#buyerName#</td>
	<td>买方姓名</td>
</tr>
<tr>
	<td>#buyerUsername#</td>
	<td>买方登录名</td>
</tr>
<tr>
	<td>#link#</td>
	<td>有关详细信息的链接</td>
</tr>
<tr>
	<td rowspan="8">担保等待授权</td>
	<td>#amount#</td>
	<td>金额</td>
</tr>
<tr>
	<td>#issuerName#</td>
	<td>发出者姓名</td>
</tr>
<tr>
	<td>#issuerUsername#</td>
	<td>发出者登录名</td>
</tr>
<tr>
	<td>#buyerName#</td>
	<td>买方姓名</td>
</tr>
<tr>
	<td>#buyerUsername#</td>
	<td>买方登录名</td>
</tr>
<tr>
	<td>#sellerName#</td>
	<td>卖方姓名</td>
</tr>
<tr>
	<td>#sellerUsername#</td>
	<td>卖方登录名</td>
</tr>
<tr>
	<td>#link#</td>
	<td>有关详细信息的链接</td>
</tr>
<tr>
	<td rowspan="2">应用程序错误</td>
	<td>#path#</td>
	<td>发生错误时的路径</td>
</tr>
<tr>
	<td>#link#</td>
	<td>有关详细信息的链接</td>
</tr>
<tr>
	<td rowspan="6">系统给会员的付款</td>
	<td>#from_account#</td>
	<td>来源账户名称</td>
</tr>
<tr>
	<td>#payment_type#</td>
	<td>付款类型</td>
</tr>
<tr>
	<td>#amount#</td>
	<td>金额</td>
</tr>
<tr>
	<td>#member#</td>
	<td>会员名称 paid</td>
</tr>
<tr>
	<td>#login#</td>
	<td>会员登录名 paid</td>
</tr>
<tr>
	<td>#link#</td>
	<td>有关详细信息的链接</td>
</tr>
<tr>
	<td rowspan="6">会员给系统的付款</td>
	<td>#to_account#</td>
	<td>目标账户名称</td>
</tr>
<tr>
	<td>#payment_type#</td>
	<td>付款类型</td>
</tr>
<tr>
	<td>#amount#</td>
	<td>金额</td>
</tr>
<tr>
	<td>#member#</td>
	<td>收款会员名称</td>
</tr>
<tr>
	<td>#login#</td>
	<td>收款会员登录名</td>
</tr>
<tr>
	<td>#link#</td>
	<td>有关详细信息的链接</td>
</tr>
<tr>
	<td rowspan="5">系统给系统的付款</td>
	<td>#from_account#</td>
	<td>来源账户名称</td>
</tr>
<tr>
	<td>#to_account#</td>
	<td>目标账户名称</td>
</tr>
<tr>
	<td>#payment_type#</td>
	<td>付款类型</td>
</tr>
<tr>
	<td>#amount#</td>
	<td>金额</td>
</tr>
<tr>
	<td>#link#</td>
	<td>有关详细信息的链接</td>
</tr>
<tr>
	<td rowspan="4">新的等待管理员授权的付款，<br>
	系统发票</td>
	<td>#amount#</td>
	<td>金额</td>
</tr>
<tr>
	<td>#member#</td>
	<td>会员名称</td>
</tr>
<tr>
	<td>#login#</td>
	<td>会员登录名</td>
</tr>
<tr>
	<td>#link#</td>
	<td>有关详细信息的链接</td>
</tr>
<tr>
	<td rowspan="3">新会员注册</td>
	<td>#member#</td>
	<td>新会员姓名</td>
</tr>
<tr>
	<td>#group#</td>
	<td>新会员组别名称</td>
</tr>
<tr>
	<td>#link#</td>
	<td>有关详细信息的链接</td>
</tr>
<tr>
	<td rowspan="2">会员警示</td>
	<td>#member#</td>
	<td>会员名称</td>
</tr>
<tr>
	<td>#login#</td>
	<td>会员登录名</td>
</tr>
</table>
<hr>

<A NAME="mail_translation"></A>
<h3>电邮翻译</h3>
该视窗可让您变更在某些场合发送的电邮内容。要做到这一点，您（一如既往）首先应该点击 &quot;更改&quot;；完成后，您可以点击 &quot;提交&quot; 储存您的更改。<br>
目前，下面的电邮可以改变：
<ul>
	<li><b>邀请电邮：</b> 通过 &quot;菜单：主页 > 邀请&quot; 使用选项 <a href="${pagePrefix}home#home_invite"><u>邀请朋友</u></a> 时发送的电邮。
	<li><b>激活电邮：</b> 当会员被激活时发送给他的电邮。
	这种情况在注册后，当管理员激活账户，将会员从 &quot;<a href="${pagePrefix}groups#pending_members"><u>待审批会员</u></a>&quot; 组别转移到另一组别（通常为正式会员）时发送。
	<li><b>公众注册电邮验证：</b> 当可能的会员尝试首次注册时发送给他的电邮。
	要发送此电邮，联环系统必须为这个 <a href="${pagePrefix}groups#group_registration_settings"><u>配置</u></a>。
	<li><b>重设密码电邮：</b> 密码被重置时发送的电邮。
</ul>
<hr class="help">

<a name="imexport_notifications_mails"></a>
<h3>导入/导出通知和电邮翻译</h3>
有了这个视窗，您可以导入或导出电邮和通知的翻译文本文件。该文件是XML格式的，并且可以由联环系统任何目前（或未来）的版本读取。<br>
您这样做，可能是希望与其他联环系统之间共享翻译文本，或者出于安全原因。<br>
方法相当简单。当您选择 &quot;导入&quot;，您应该通过 &quot;浏览&quot; 按钮指定文件。如果导出文件，浏览器将接管，并要求您在哪里储存下载。
<hr>

<p><a name="glossary"></a>
<h2>术语表</h2>
<p></p>

<a name="language_file"></a> <b>语言文件</b>
<p>在联环系统中，每种语言有语言文件。该文件包含所有在联环系统接口出现的书面文本 &quot;字符串&quot;（除非是大块的文本，会放在完整的文件内）。<br>
语言文件是根据特定的模式命名：
&quot;ApplicationResources_xx_XX.properties&quot;，其中 xx 是语言代码，XX 是国家代码。例如：
&quot;ApplicationResources_en_US.properties&quot; 的文件是美国英语。<br>
语言文件包含关键词和数值，由 &quot;=&quot; 分开，中间没有任何空间。
<hr class='help'>

<a name="key"></a> <b>翻译关键词</b>
<p>翻译关键词被放置在应用网页中；当这些网页显示在您的浏览器时，关键词是从语言文件中找出，并代之以相应的数值。
<hr class='help'>

<a name="value"></a> <b>翻译数值</b>
<p>翻译数值和词语是在您的浏览器的联环系统网页内看到您自己的语言翻译。原来的网页不包含这些数值。代替的是被放置在应用网页中的翻译关键词；
这些网页显示在您的浏览器时，关键词是从语言文件中找出，并代之以相应的数值。<br>
所有翻译数值（应用翻译、通知和电邮）可以包含 &quot;变量&quot;。
变量总是由两个 # 包围，例如 #member#、#title# 和 #amount#。变量将在联环系统改为正确的数值来显示。
变量名称是不解自明的，所有可能的变量都会使用默认的翻译数值。
<hr class='help'>

</span>

</div> <%--  page-break end --%>