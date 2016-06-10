<div style="page-break-after: always;">
<p class="head_description">
会员可以付款给另一会员或系统账户（社区、组织等）。此外，如果适用，如果每个会员有一个以上相同货币的账户，会员可以在自己的账户之间转账单位。付款也可以预定未来日期。所有的款项有一个打印按钮来打印交易收据。
</p>
<span class="admin">在某些条件下，付款也可以撤消（<a href="#charge_back"><u>退单</u></a>）的。</span>

<p><i>在哪里可以找到它？</i><br>
<span class="member">付款在主要网络接入页可以从三个地点开始。从菜单中：这有两个版本：
<ul>
	<li><b>付款给会员：</b> &quot;菜单：账户 > 会员付款&quot;
	<li><b>付款给系统：</b> &quot;菜单：账户 > 系统付款&quot;
	<li><b>从个人资料：</b> 付款给其他会员也可以从该会员的 <a href="${pagePrefix}profiles"><u>个人资料</u></a> 页开始。
</ul>
</span> 
<span class="broker"> 经纪可以通过其会员的 <a href="${pagePrefix}profiles"><u>个人资料</u></a> 开始付款给他们。这包括付款给其他会员和付款给系统账户。
<p>经纪也可以 <a href="#authorized"><u>授权</u></a> 会员的付款，这可以通过主要的 &quot;菜单：经纪> 等待授权&quot; 和 &quot;菜单：经纪 > 授权历史&quot;。<br>
</span><span class="admin"> 付款可从不同的地点开始。可用的选项当然是根据您组织的设置和不同组别的权限：
<ul>
	<li><b>个人资料：</b> 从会员的 <a href="${pagePrefix}profiles"><u>个人资料</u></a>，
	您可以执行付款给其他会员，以及付款给系统。
	<li><b>系统付款给系统：</b> 可以从 &quot;菜单：账户 > 系统付款&quot; 开始。这是从系统转账到另一系统账户。
	<li><b>系统付款给会员：</b> 可以从 &quot;菜单：账户 > 会员付款&quot; 开始。这些是从系统账户付款给会员。
</ul>
还有不同类型的特殊付款，这些多数是可以通过主要菜单到达：
<ul>
	<li><b><a href="#authorized"><u>授权</u></a>：</b> 可以通过 &quot;菜单：账户 > 授权&quot; 和 &quot;菜单：账户 > 授权历史&quot; 进入。
	<li><b><a href="#scheduled"><u>预定付款</u></a>：</b> 可以通过 &quot;菜单：账户 > 预定付款&quot; 进入。
	<li><b>偿还贷款：</b> 可以通过 &quot;菜单：账户 > 偿还贷款&quot; 进入。这题目将涵盖在 <a href="${pagePrefix}loans"><u>贷款帮助</u></a>。
</ul>
<br>
</span> 请注意，除了进行直接付款，您也可以用回复 <a href="${pagePrefix}invoices"><u>发票</u></a> 来付款。

<span class="admin">
<p><i>如何使它运作？</i><br>
最重要的问题是每一笔付款必须有付款方式存在。如果您没有为某些付款定义付款方式，付款是无法进行的。您可以运用管理付款的账户来管理交易类型。
要这样做，您应该到 &quot;菜单：账户 > 管理账户&quot;，并选择付款人的账户类型。
这里，您将有该账户所有可用的交易类型 <a href="${pagePrefix}account_management#transaction_type_search"><u>概况</u></a>，也让您新增类型（如果您有权限）。<br>
当有了付款方式，您仍然需要设置权限，让各个组别可以使用它。
<ul>
	<li>管理员可以有 <a href="${pagePrefix}groups#manage_group_permissions_admin_system"><u>权限</u></a> 来执行系统付款：&quot;系统付款&quot; 区块包含各种设置。
	<li>管理员也可以有 <a href="${pagePrefix}groups#manage_group_permissions_admin_member"><u>权限</u></a> 为会员执行付款：&quot;会员付款&quot; 区块包含各种设置。
	<li>对于会员，您也将需要设置 <a href="${pagePrefix}groups#manage_group_permissions_member"><u>权限</u></a> 来执行付款；这可以通过在
	&quot;付款&quot; 区块内的各种设置做到。这样的区块也可用于经纪。
	<li>经纪可以有 <a href="${pagePrefix}groups#manage_group_permissions_broker"><u>权限</u></a> 为会员执行付款，在 &quot;会员付款&quot; 区块内。
	<li>对于授权和预定付款，是存在于每个主要组别（会员、经纪、管理员）在 &quot;账户&quot; 区块内的设置，让该组别查看授权和/或预定付款。
</ul>

</span>

<hr>

<a name="payments"></a>
<p class="sub_description"></p>
<h3>付款</h3>
联环系统的付款表格往往有一些共同的要素。在这简短的介绍，我们涵盖可能出现在您的付款视窗的共同要素。
在大多数情况下，您只需要输入金额和描述，然后点击 &quot;提交&quot; 按钮。在其它各种情况下，您也将要输入一些其它字段。
<br>
请注意，选择所有的字段和按正确的选项次序是重要的，即从上至下。例如：在选择货币之前，您必须输入前一字段请求的会员名称，否则，是不会运作的。
<p>
<ul>
	<li><b>用户名：</b> 如果付款给另一会员，并且这没有从上下文中清楚见到，您将要输入您想支付的会员的用户名或者真正名称。字段是自动完成的：多数只要输入首几个字母便足够。
	<li><b>金额：</b> 只需输入金额。
	<li><b>货币：</b> 这字段紧接在金额后面显示。这只有一种以上的货币才可能看得见。这是取决于您组织的本地配置。
	<li><b>交易类型：</b> 可能有一种以上的交易类型。在这种情况下，您将要从下拉框中选择交易类型。
	<li class="admin"><b>过去付款日期：</b> 管理员可以选择执行过去日期的付款。这主要是为了问责的情况而做，并应只在极少数情况下使用。这必须在 <a
		href="${pagePrefix}groups#manage_group_permissions_admin_member"><u>管理员的会员权限</u></a> 中启用。
	<li><b>预定：</b> 如果这种付款方式允许预定付款，您可以选择在未来日期或经常性未来日期的多次付款，来（自动）处理付款。（如需要更详细的资料，请参阅 <a href="#pay_scheduled"><u>预定付款</u></a>）
</ul>
<p>在提交后，您会被请求进行确认。单位是直接从您的账户转账到另一方的账户。此交易将显示在双方的账户历史中，寄件人（付款人）呈红色的 - 符号，收款人呈蓝色的 + 符号。
<hr class="help">

<a name="transaction_confirm"></a>
<h3>交易确认</h3>
在此视窗中，您被请求确认您刚才输入的付款。请彻底地核对所有资料，如果一切正确，请点击 &quot;提交&quot;。
<br>
如果有错误，您可以使用 &quot;返回&quot; 按钮。
<hr class="help">

<A NAME="to_member"></A>
<h3>付款给会员</h3>
这个视窗让您付款给会员。在大多数情况下，您只需要输入描述、金额，以及会员名称（如果这还不知道），并点击 &quot;提交&quot;。
<br>
请 <a href="#payments"><u>点击这里</u></a> 以取得一般的付款视窗描述。
<hr class="help">

<A NAME="to_system"></A>
<h3 class="admin">系统账户之间转账</h3>
<h3 class="member">付款给系统</h3>
<span class="admin"> 在此视窗中，您可以在系统账户之间付款。
<p>
</span>
<span class="member">在此视窗中，您可以付款给其中一个组织账户或 &quot;系统账户&quot;。</span>
请 <a href="#payments"><u>点击这里</u></a> 以取得一般的付款视窗描述。
<hr class="help">

<A NAME="as_member_to_system"></A>
<span class="broker admin">
<h3>付款给系统</h3>
在此视窗中，您可以代表会员从会员账户付款给系统账户。
<br><br>
请 <a href="#payments"><u>点击这里</u></a> 以取得一般的付款视窗描述。
<hr class="help">
</span>

<span class="broker admin"> <A NAME="as_member_to_member"></A>
<h3>代表会员付款给会员</h3>
在此视窗中，您可以代表会员从会员账户付款给另一会员的账户。
<br><br>
请 <a href="#payments"><u>点击这里</u></a> 以取得一般的付款视窗描述。
<hr class="help">
</span>

<A NAME="member_self_payments"></A>
<h3 class='member'>我的账户之间转账</h3>
<h3 class='admin'>会员账户之间的转账（由管理员）</h3>
<span class="admin">（有权限的）管理员执行自我付款是可能的，犹如管理员是会员。</span>
<br><br>
自我付款允许从一个账户转账到相同拥有者（会员）的另一账户。自我付款与一般的付款给其他会员的运作一样。
<br>
请 <a href="#payments"><u>点击这里</u></a> 以取得一般的付款视窗描述。
<hr>

<a name="scheduled"></a>
<p class="sub_description">
<h2>预定付款</h2>
预定付款职能允许会员新增未来的预定付款（分期）给其它账户。这可能是只做一次的单次预定付款，好象 &quot;一批&quot; 的多次付款，或经常性（定期）的付款（如：每月）。付款将会在指定的日期自动完成。
<br>
预定付款也可以与发票结合。发送发票给另一会员的会员将可以（如果他有权限）指定该发票是否需要直接付款，或可以在预定（未来）日期或多次付款日期支付。
一旦接收的会员接受了发票，预定付款将会出现在他的预定付款列表（并在发送该发票的会员所指定的日期收取）。
</p>
<span class="admin">（在系统配置）允许预定任何方式的付款是可能的。要预定付款，您需要做到以下几点：
<ol>
	<li><b>权限：</b> 首先，您需要设置所有会员组别的权限。
	在写作此刻，有三种可启用的会员组别权限，但是您可能不想把它们全部都启用。
		如果您要允许 <a href="${pagePrefix}brokering"><u>经纪</u></a> 或管理员代表会员执行预定付款，您也应该为这些组别拣选权限。
	<li><b>组别设置：</b> 对于会员组别，有一种 <a href="${pagePrefix}groups#edit_member_group"><u>特殊组别设置</u></a> 
	（&quot;菜单：用户和组别 > 权限组别&quot;，并点击会员组别的编辑图标 <img border="0" src="${images}/edit.gif" width="16" height="16">）的预定付款。这个常常被忽略，因此，请不要忘记设置这些。
	<li><b>交易类型：</b> 在 <a href="${pagePrefix}account_management#transaction_types"><u>交易类型</u></a> 必须启用预定。
	在 <a href="${pagePrefix}account_management#transaction_type_details"><u>交易类型属性视窗</u></a> 中，有一个特别的 &quot;允许预定付款&quot;复选框。<br>
	<b>注：</b> 某些付款方式是没有预定的。这些是会员付款给系统的交易类型，以及自我付款的交易类型。
</ol>
这应设置运作预定付款。在这种情况下，当与此有关时，每个 <a href="#payments"><u>付款视窗</u></a> 会显示 &quot;预定&quot; 下拉框。
<br><br>预定付款可以通过 &quot;菜单：账户 > 预定付款&quot; 来搜索。
</span>
<span class="member">您的预定付款可以通过 &quot;菜单：账户 > 预定付款&quot; 来搜索。</span>
<hr class="help">

<a name="pay_scheduled"></a>
<h3>预定付款</h3>
这帮助项目是有关 <a href="#scheduled"><u>预定付款</u></a> 的，并描述了它在付款视窗内的特殊字段。
<p>在 &quot;预定&quot; 下拉框有三个可能值：
<ul>
	<li><b>不预定（立即支付）：</b> 请选择此项如果您不想使用预定付款。
	<li><b>预定未来日期：</b> 如果选择此选项，付款将在您指定的日期执行。您必须在 &quot;预定&quot; 修改图标内指定日期；如果拣选此选项，它应出现。您可能想通过 <img border="0"
		src="${images}/calendar.gif" width="16" height="16">&nbsp;日历图标来选择日期。
	<li><b>多次预定付款：</b> 这是最复杂的预定付款方式。您可以选择此选项来把一个单次付款分裂成多次付款。每个多次付款的日期和金额是可以个别设定的。<br>
	表格内有以下要素：
	<ul>
		<li><b>付款次数：</b> 您想要的分期付款次数。例如：10次付款，每星期一次。您在以上所指定的金额会分为相等的部分。
		<li><b>第一次付款日期：</b> 您可能要通过 <img border="0" src="${images}/calendar.gif" width="16" height="16">&nbsp;日历图来选择日期。
		<li><b>付款每：</b> 请使用这两个下拉框来选择期段。
		<li><b>计算：</b> 您可能想使用此按钮来查看在这些日期将要支付的正确金额。当您点击此按钮，便显示了付款日期和金额的概况。
		您可以更改这些日期和金额，只要它们的总金额相等于您在以上输入的金额。<br>
		注意：此选项没有处理任何事情。它只是用来预览金额和日期。
	</ul>
</ul>
<hr class="help">

<A NAME="scheduled_payments_by_admin"></a>
<A NAME="scheduled_payments_by_member"></a>
<h3>搜索预定付款</h3>
在这里，您可以搜索 <a href="#scheduled"><u>预定付款</u></a>。可以指定下列在表格内的要素。请注意，让字段留空的结果是得到这字段的所有值。
<ul>
	<li><b>搜索类型：</b> 在这里，您可以指定 &quot;发出&quot; 付款或 &quot;收到&quot; 付款。发出付款只是一个正常的付款；而 &quot;收到&quot; 是指寄件人指定可以用预定付款支付的 <a href="${pagePrefix}invoices"><u>发票</u></a>。
	<li><b>账户：</b> 在下拉框中选择账户类型。这只有一个以上是可用的才可看得见。
	<li><b>状态：</b> &quot;开启&quot; 是指 &quot;尚未支付&quot;，其余的是不解自明。
	<li><b>用户名/会员：</b> 在这两个字段，您可以指定完成付款的会员。该字段是自动完成的。
	<li><b>开始日期/结束日期：</b> 在这里，您可以指定一个时段。您可能想通过 <img border="0" src="${images}/calendar.gif" width="16" height="16">&nbsp;日历图标来选择日期。
</ul>
<hr class="help">

<a name="view_scheduled_payment"></a>
<h3>预定付款细节</h3>
这视窗会显示 <a href="#scheduled"><u>预定付款</u></a> 的详情。可以点击您要的的个人名称去他们的个人资料。
<br>
您可以点击 <img border="0" src="${images}/print.gif" width="16" height="16"> 打印图标来打印细节。
<br><br>如果您有权限，在视窗底部可能有两个按钮：
<ul>
	<li><b>封锁：</b> 这让您暂时封锁付款。付款将不会执行，直到您取消或解除封锁。封锁的付款是可以解除的。
	<li><b>解除封锁：</b> 解除封锁付款，因此，这将在原本的预定日期执行付款。如果付款日期已经过去，此按钮将不可见。在这种情况下，您仍然可以转到视窗下面的 <a
		href="#sheduled_payment_transfers">预定付款转账</a> 进行付款，然后点击转账的 <img border="0" src="${images}/view.gif" width="16"
		height="16">&nbsp;查看图标。
	<li><b>取消：</b> 与封锁按钮的差异是取消了的预定付款是最终的。开启的预定付款将不会支付，并将肯定被删除，而没有不取消的选项。已经支付的预定付款将无法环原。
</ul>
<hr class="help">

<p><A NAME="sheduled_payment_transfers"></A>
<h3>预定付款转账</h3>
这页显示 <a href="#pay_scheduled"><u>多次预定付款</u></a> 的所有转账部分（分期付款）。您可以点击
<img border="0" src="${images}/view.gif" width="16" height="16">&nbsp;查看图标转到付款的细节。
<hr class="help">


<A NAME="scheduled_payments_result"></A>
<h3>搜索结果（预定付款）</h3>
这结果视窗显示的 <a href="#scheduled"><u>预定付款</u></a> 列表，是按照您在以上所指定的搜索准则。
<p>有以下显示：
<ul>
	<li><b>日期：</b> 预定的日期。
	<li><b>用户名：</b> 您可以点击这里到会员的个人资料。
	<li><b>金额</b> 
	<li><b>分期：</b> 第一个数字显示这预定付款已经支付了多少次部分付款；第二个数字显示这预定付款的部分付款总次数。
	如果付款没有分成多个部分付款，而只是一次性的单次付款，那么第二个数字将是 &quot;1&quot;。
	<li><b>状态：</b> 可以是 &quot;预定&quot;、&quot;封锁&quot; 或 &quot;等待授权&quot;（请见 <a href="#authorized"><u>授权付款</u></a>）。
	<li><img border="0" src="${images}/view.gif" width="16" height="16">&nbsp;使用这个图标以查看转账详情。
	在那里，您将可以选择打印细节、封锁、解除封锁或支付款项（只要您有正确的权限）。
</ul>
<hr>

<a name="authorized"></a>
<p class="sub_description">
<h2>授权付款</h2>
联环系统可以配置为在金额真正转账到收款人的账户之前，这付款首先需要授权。管理员、经纪或接收的会员可以执行授权，这是取决于系统内的配置。
可以有一个以上的授权级别，也就是说，将需要一个以上的授权者；每个授权级别可以配置不同的准则。
<br>
只要款项尚未授权支付，它将仍然停留在 &quot;等待授权&quot; 的状态。授权者将得到通知，并可以授权（激活）支付这笔款项。
会员和授权者将获得一份需要授权的等待付款列表。当完成付款（授权）后，付款人和收款人都将会收到通知。
</p>
<span class="admin">授权付款是以每个 <a href="${pagePrefix}account_management#transaction_types"><u>交易类型</u></a> 来管理的，有各种的设置。
<br><br>授权付款可以启用如下：
<ol>
	<li><b>权限：</b> 首先，您应该当心所有相关的 <a
		href="${pagePrefix}groups#permissions"><u>权限</u></a> 设置。您可能想要给管理员、经纪和会员设置权限。
		每个用户组别有各种授权权限；您可能想在页面使用浏览器的搜索职能（通常是按Ctrl-f）来寻找它们。
	<li><b>设置交易类型授权：</b> 您需要启用 <a
		href="${pagePrefix}account_management#transaction_types"><u>交易类型</u></a> 的授权。这是通过在 <a
		href="${pagePrefix}account_management#transaction_type_details"><u>交易类型细节视窗</u></a> 内的 &quot;需要授权&quot; 字段中完成。<br>
		<b>注：</b> 只要还有交易等待授权，此授权选项是不能取消的。
	<li><b>设置授权级别：</b> 之后，您需要设置授权级别。这是在 <a
		href="${pagePrefix}account_management#authorized_payment_levels"><u>授权付款级别视窗</u></a> 中做到。如需要详细资料，请参照帮助。
</ol>
</span>
<span class="member">
会员授权简单指收款人在交易进行之前将要接受来自对方的付款。收款人有可能拒绝该笔付款（例如：没有产品）。
收款人和付款人都将收到通知。此配置与发票的使用相似，并且相当罕见。在同一系统中，最好不要同时使用收款人授权和发票。
</span>
在软件内，您可以在下列的位置找到授权付款：
<ul>
	<li><b>菜单：账户 > 等待授权</b> 提供需要您授权付款的概况。只有当您有权限来授权收到的付款时，这项目单才可用。
	<li><b>菜单：账户 > 授权</b> 让您可以搜索任何过去或现在，已授权、拒绝或取消的授权。
	所有您过去完成的授权行动可以通过这个视窗找到。<br>
	请注意：此选项是搜索授权行动。因此，不能找到等待转账的授权（因对它们没有采取任何行动）。
	<li class="broker"><b>菜单：经纪 > 等待授权</b>将提供作为经纪的您需要授权您的会员付款概况。（对比菜单：账户 > 已授权，在这里您将找到您的个人授权付款）。                     
	<li class="broker"><b>菜单：经纪 > 授权</b> 与 &quot;菜单：账户 > 授权&quot; 一样，但不是有关您的经纪活动的授权。
</ul>
<hr class="help">

<a name="transfers_awaiting_authorization_by_member"></a>
<a name="transfers_awaiting_authorization_by_admin"></a>
<h3>转账授权</h3>
使用此视窗以取得您必须 <a href="#authorized"><u>授权</u></a> 的转账概况。与往常一样，让字段留空白意味着这搜索结果包括所有的可能值。所以点击 &quot;搜索&quot;
按钮而没有指定任何字段，将显示所有等待授权的付款。
<p>您可以指定以下搜索准则：
<ul>
	<li><b>用户名/会员名称：</b> 这些字段是自动完成的，所以只需输入首几个字母便足够了。
	<li><b>开始日期/结束日期：</b> 在这里，您可以指定一个时段，您可能想通过 <img border="0"	src="${images}/calendar.gif" width="16" height="16">&nbsp;日历图标来选择日期。
	<li><b>交易类型：</u></b> 用交易类型来搜索。
	<li span class="admin"><b>只当经纪不能授权时：</b> 拣选此复选框的结果只包括作为管理员的您是唯一能够授权的项目。
</ul>
结果将显示在下面的视窗。
<hr class="help">

<a name="transfers_awaiting_authorization_result"></a>
<h3>搜索结果（转账等待授权）</h3>
在这概况，您会看到目前仍在等待您 <a href="#authorized"><u>授权</u></a> 的交易。负数是需要授权的发出付款，而正数是等待授权的收到付款。
<p>点击 <img border="0" src="${images}/view.gif" width="16" height="16">&nbsp;查看图标以开启细节视窗，在这里您可以授权或拒绝付款。
<hr class="help">

<a name="transfer_authorizations_by_admin"></a>
<a name="transfer_authorizations_by_member"></a>
<h3>授权交易行动</h3>
您可以使用这个视窗来搜索 <a href="#authorized"><u>授权</u></a> 行动。表格很简单，如果您让字段留空，这意味着，结果会包含字段的所有可能值。
<br>
有以下搜索选项：
<ul>
	<li><b>交易类型：</b> 用交易类型来搜索。
	<li><b>搜索行动：</b>
	<ul>
		<li><b>授权：</b> 已批准的付款。
		<li><b>拒绝：</b> 已拒绝的付款。
		<li><b>取消：</b>（其它人）已取消的付款。
	</ul>
	<li><b>搜索会员：</b> 搜索个别会员。
	<li><b>搜索时段：</b> 搜索日期范围。
</ul>
完成后，您可以点击在页面底部的 &quot;搜索&quot; 按钮。结果将显示在下面的视窗。
<hr class="help">

<a name="transfers_authorizations_result"></a>
<h3>授权历史搜索结果</h3>
显示在以上视窗中所指定的搜索准则的结果。请使用 <img border="0" src="${images}/view.gif" width="16" height="16">&nbsp;查看图标以取得项目的详情。
<hr class="help">

<a name="transaction_authorizations_detail"></a>
<h3>授权行动</h3>
此视窗显示在上述交易中已执行的所有 <a href="#authorized"><u>授权</u></a> 行动。这些可以是授权，但也可以是拒绝，或取消。它显示了行动的日期和执行者。
<hr>

<h2>杂项付款视窗</h2>
以下提供一些有关付款的一般和杂项帮助视窗。

<a name="accessing_channels"></a>
<h3>访问付款渠道</h3>
根据配置，会员可以通过各种付款渠道支付款项。
<ul>
	<li>最常见的是主要网络接入页（www.domain.com/cyclos）。
	<li>另一个有用的付款渠道是一个简单的页面，当中会员只需要登录和执行快速付款（www.domain.com/posweb/pay）。
	<li>希望以销售点方式收到顾客/客户付款的会员/商业，可以使用销售点网络页（www.domain.com/posweb/receive）。请注意，客户需要生成一个个人密码来验证付款。
	<li>对于希望能够在同一页面支付和接收款项的会员/商业，可以使用访问渠道（www.domain.com/posweb）。<br>
	这是本地 &quot;缓存点&quot; 常用的一个访问页；在这里，客户可以取得或赎回代用券（凭证）或钱。
	<li>对于有 &quot;付款台&quot; 操作员的会员/商业，可以使用操作员销售点网络页（www.domain.com/posweb/operator）来支付和接收顾客/客户的付款。<br>
	<li>手机付款可以通过 www.domain.com/cyclos/mobile (wap2) 和 www.domain.com/cyclos/wap (wap1) 的网址做到.
</ul>
可用的付款渠道是取决于系统的配置。一般收到付款时将请求客户输入个人密码。从销售点网络渠道付款的运作与主要网络接入一样，需要登录密码或交易密码，这取决于配置。</p>
<hr class="help">

<a name="request"></a>
<h3>通过其它渠道请求付款</h3>
在此视窗中，您可以请求通过另一付款媒介（<a href="#accessing_channels"><u>渠道</u></a>）来付款。它的运作方式与发票相似，当付款被接受后将立即被激活。
所不同的是，此付款请求需要个人密码和有付款请求编号的确认（在短讯内提供）。目前只可使用短讯来请求付款。管理员需要启用短讯渠道，这个职能才可以使用。
<p>它的运作是：当在用户名/名称字段中输入会员（自动完成），接着输入金额和描述，并按 &quot;提交&quot; 后，付款请求将通过短讯渠道发送。
这意味着，会员将即时收到短讯，可以回复和提供一个个人密码以确认。当付款请求发送后，状态将会更改（请见以下帮助）。
当会员以短讯回复确认付款，状态将更改为 &quot;已付款&quot;（并可以移交或发送产品）。
<hr class="help">

<a name="search_requests"></a>
<h3>搜索付款请求</h3>
在此视窗中，您可以搜索付款请求。默认情况下它会显示所有待处理和成功（确认及已付款）的请求。您还可以在筛选器选择其它状态和用会员来搜索。
<p>结果视窗会根据搜索筛选器显示付款请求。该页面将会每5秒自动刷新（状态也将自动更改）。
<hr class="help">

<a name="account_overview"></a>
<h3>账户概况</h3>
此视窗显示了所有您可用的账户列表。
<ul>
	<li><img border="0" src="${images}/view.gif" width="16" height="16">&nbsp;单击该图标以进入账户。
</ul>
<hr class="help">

<a name="transaction_history"></a>
<h3>交易摘要</h3>
这视窗有各种选项来搜索交易。您可以选择以下选项：
<UL>
	<LI><b>状态：</b> 这是需要授权的付款状态。它只在启用了会员付款或贷款的授权时才会显示。将显示的状态有：
	<ul>
		<LI><b>等待授权：</b> 在处理转账之前，付款或贷款需要获得授权。
		<LI><b>已处理：</b> 付款或贷款已授权和处理。
		<LI><b>已被拒：</b> 付款或贷款已被拒绝。
	</ul>
	<LI><b>付款方式：</b> 您可以使用此下拉框来筛选特定的付款方式。
	<LI><b>用户名/会员名称：</b> 只显示某人的交易，通过输入此人的用户名或会员名称。当输入时，字段会自动完成。
	<LI><b>日期范围：</b> 只显示日期范围内的交易，通过输入开始和/或结束日期。
	<LI><b>描述：</b> 搜索某些描述，通过在 <I>描述</I> 框中输入词语（关键词）。如：输入 &quot;自行车&quot;
	会显示在描述或标题中有 &quot;自行车&quot; 的所有交易。
	<LI><b>交易编号：</b> 如果交易编号在系统中已启用，可以使用这交易编号字段来搜索。
</UL>
<hr class="help">


<a name="transaction_history_result"></a>
<h3>交易摘要结果</h3>
此视窗显示搜索查询交易摘要的结果。<br>
如果您点击打印图标 <img border="0" src="${images}/print.gif"
	width="16" height="16">&nbsp;（位在頂部帮助图标的旁边），将会出现您的搜索结果的打印版，
	当中包含${localSettings.maxIteratorResults}个交易和一份摘要报表。
<span class="admin">在本地设置%gt;限制，您可以改变它显示的交易数量。</span>
<br>如果您单击储存图标 <img border="0" src="${images}/save.gif"
	width="16" height="16">&nbsp;（位在頂部打印图标的旁边），您可以把结果下载为CSV文件。
<p>在视窗的第一（頂端）部分显示下列资料：
<ul>
	<li><b>账户余额：</b> 账户的余额。
	<li class="member"><b>信贷额度下限：</b> 信贷额度的下限。这可以是零或负数（如果是零则不显示）。
	<li class="member"><b>信贷额度上限：</b> 信贷额度的上限。这可以是零或正数（如果是零则不显示）。
	<li class="member"><b>可用余额：</b> 可花費的可用余额。
	<li class="member"><b>保留金额：</b> 这是暂时的保留金额，不能用作付款。这可能是因为有等待授权的交易或留作用於即将来到的定期利息或滞期费。
</ul>
本节下面显示所有付款（支付和收到）的列表。收到（接收）的付款在前面显示有 + 的符号，发出的付款在前面显示有 - 的符号。
该列表显示付款日期、会员（收款人或付款人）和付款的描述。会员名称/用户名会链接到会员的个人资料。<br>
当点击付款的查看图示 <img border="0" src="${images}/view.gif" width="16" height="16">，将开启一个有付款描述的视窗。
<hr class="help">

<a name="transaction_detail"></a>
<h3>交易细节</h3>
此视窗提供有关选择付款的详情。您可以选择打印图标来打印交易细节。如果有 &quot;母&quot; 或
&quot;子&quot; 付款存在（例如：收取交易费用），它将显示在这视窗下面。
<br><br><span class="broker admin"> 如果交易需要得到授权，您将可以选择接受或拒绝该交易。您也将需要输入评论。
如果拣选 &quot;让会员查看&quot; 框，会员将可看见评论。如果不选择此选项，将只有您和管理员可看见评论。
</span>
<span class="admin">
在一定条件下，可以通过 <a href="#charge_back"><u>退单</u></a> 来撤消交易。在这种情况下，在此视窗可看见特别的退单按钮。
</span>
<hr class="help">

<span class="admin">
<a name="charge_back"></a>
<h3>退单</h3>
管理员（有正确的权限）可以 &quot;退单&quot;，这意味着一个相同金额的相反付款将会完成。在付款生成其它交易（如费用和贷款）的情况下，所有交易将被撤销。
如果退单是可能的話，一个特别的退单按钮将会在交易细节视窗中见到。
但是，只有交易已经发生了不太久，这个按钮才能看得见。
您可以在 &quot;菜单：设置 > <a href="${pagePrefix}settings#local"><u>本地设置 > 退单</u></a>&quot; 定义交易类型的最大退单时限。
<hr class="help">
</span>

</div> <%--  page-break end --%>


<div class='help'>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
</div>