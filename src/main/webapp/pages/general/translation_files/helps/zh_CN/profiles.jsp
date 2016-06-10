<div style="page-break-after: always;">
<a name="top"></a>
<p class="head_description">
个人资料是特定用户的有关资料。<br>
<span class="admin">
有管理员个人资料和会员个人资料。管理员登录目的只是为了管理，并没有账户，也没有其它会员功能，如：联系人、评语等。因此，管理员的个人资料比会员个人资料更简单。<br>
更重要的是，链接到每个个人资料的是 <a href="#actions"><u>...的行动</u></a> 视窗，它让您从某人的个人资料中启动各种行动。</p>
</span>
&quot;用户名&quot; 是用来登录的名称；这与 &quot;名称&quot; 形成对照，后者是指某人的真正名称。
<p><i>在哪里可以找到它？</i><br>
您可以通过 &quot;菜单：个人 > 个人资料&quot; 访问自己的个人资料。<br>
<span class="member">您有两种方法可以访问另一会员的个人资料：
<ul>
	<li><b>通过搜索：</b> 通过 &quot;菜单：搜索 > 会员&quot; 来执行 <a href="${pagePrefix}user_management#search_member_by_member"><u>搜索会员</u></a>。
	<li><b>通过您的联系人：</b> 通过 &quot;菜单：个人 > 联系人&quot;，使用您的 <a href="${pagePrefix}user_management#contacts"><u>联系人列表</u></a> 以转到会员的个人资料。
</ul>
</span> 
<span class="admin"> 您可以通过 &quot;菜单：用户和组别 > 管理管理员&quot; 来访问另一管理员的个人资料。<br>
您可以通过 &quot;菜单：用户和组别 > 管理会员&quot; 来访问会员的个人资料。
</span> 
<span class="broker"> 作为经纪，您可以通过 &quot;菜单：经纪 > 会员&quot; 来访问自己的会员的个人资料。
</span>
<span class="admin">
<p><i>如何使它运作？</i><br>
任何人都可以随时查看自己的个人资料。对于会员和经纪组别，您需要明确地设置可以查看的其它组别的个人资料（下面第一个项目）。有几个权限是关于个人资料：
<ul>
	<li>为了控制会员可以看到的其它组别，请设置该组别的 <a href="${pagePrefix}groups#manage_group_permissions_member"><u>权限</u></a>。
	这会员组别将可看到在 &quot;会员个人资料&quot; 区块内下拉框中所选的每个组别，也就是说，会员可以查看在这些组别内其他会员的个人资料。
	经纪组别可以设置相同的权限。
	<li>为了让管理员能够更改会员的个人资料，您需要在 &quot;会员&quot; 区块内设置这些 <a 
	href="${pagePrefix}groups#manage_group_permissions_admin_member"><u>权限</u></a>，并拣选 &quot;修改资料&quot; 复选框。
	<li>此外，还有更改管理员的个人资料的 <a href="${pagePrefix}groups#manage_group_permissions_admin_admin"><u>权限</u></a>，
	这可通过在 &quot;管理员&quot; 区块内的 &quot;修改资料&quot; 复选框做到。
	<li>对于经纪，您将需要设置 <a href="${pagePrefix}groups#manage_group_permissions_broker"><u>权限</u></a>，让他们能够更改其会员的个人资料。
	这可以通过在 &quot;经纪&quot; 区块内的 &quot;修改资料&quot; 复选框做到。
</ul>
</span>
<hr>

<span class="admin">
<a NAME="admin_profile"></a>
<h3>管理员个人资料</h3>
在这页面，您可以修改 <a href="#top"><u>管理员个人资料</u></a>—您自己的或另一管理员的个人资料（后者当然是取决于您的权限）。<br>
您应该点击 &quot;更改&quot; 以进行修改，并点击 &quot;提交&quot; 来储存更改。
<br><br>大多数项目都相当简单。如果您正在查看另一管理员的个人资料，页面会显示该管理员所属的组别。
<hr class="help">
</span>

<a NAME="member_profile"></a>
<h3>会员个人资料</h3>
<a href="#top"><u>个人资料</u></a>
页面显示在注册时所输入的<span class="admin broker">会员</span><span class="member">您的</span>资料。
<span class="member">如果是自己的个人资料，</span>
您可以点击在页面底部的 &quot;更改&quot; 按钮来新增和修改资料。完成操作后，请不要忘记点击 &quot;提交&quot; 按钮以储存更改。
<span class="member">
<br><br>当然，其他会员的个人资料是不能修改的，而且会员可以选择一些使其他会员看不见的资料。因此，这帮助的其余部份主要是有关您自己可更改的个人资料。
</span>
<br><br>一些表格内的一般资料：
<ul>
	<li><b>*：</b> 强制性的资料在编辑框的右侧有一个红色的星号（*）。
	<li><b>隐藏：</b> 使其他会员看不见某些字段。可以通过选择 &quot;隐藏&quot; 框，它显示在要隐藏的字段后面。
</ul>
表格相当简单，多数项目都是不解自明的。有以下一些特定字段的备注（请注意，可能看不见所提及的字段，这取决于您组织选择的配置）：
<ul>
	<span class="admin">
	<li><b>上次登录：</b> 如果会员现时正在使用联环系统，这字段将显示为 &quot;在线&quot;。在这种情况下，您可以通过视窗下面的按钮来强行登出会员。
	</span>
	<li><b>生日：</b> 您可以使用 &quot;日历图标&quot;（<img
		border="0" src="${images}/calendar.gif" width="16" height="16">）来帮助您填写日期。
	<li><b>经纪：</b> 只有当您的组织使用 <a href="${pagePrefix}brokering"><u>经纪</u></a> 系统时，这字段才可见。
	字段是不可更改的，但您可以使用在字段旁边的 &quot;开启个人资料&quot; 链接转到您经纪的个人资料。
	<li><b>注册日期：</b> 如果会员是在 &quot;待处理&quot;
	组别（没有相关账户的组别），它将会显示会员注册的日期（由管理员、经纪或者会员自己在公众注册页注册）。<br>
	如果会员是在活跃组别，这日期将不会显示（但激活日期会显示在会员报表页面）。
	<li><b>新增图片：</b> 拣选此复选框，您就可以 <a	href="#picture"><u>新增图片</u></a> 到您的个人资料。
</ul>
<hr class="help">

<a name="picture"></a>
<h3>您的个人资料或广告图片</h3>
联环系统让您上载图片到您的 <a href="#top"><u>个人资料</u></a> 或广告。如果您想其他会员能够看到您的图片，请依照下列指示：
<ol>
	<li>点击 &quot;更改&quot; 以便能够修改表格。
	<li>拣选 &quot;新增图片&quot; 复选框；将可见额外的修改。
	<li>点击 &quot;浏览&quot; 按钮来选择在电脑内您要上载的照片。
	<li>选择 &quot;浏览&quot; 按钮来浏览您电脑的文件系统。选择一个并点击 &quot;开启&quot;。
	请注意，是有文件最大长度的（这将显示在视窗），而且这只支持JPG、GIF和PNG的格式。
	<li>之后，该文件的名称被放置在 &quot;图片&quot;	框。
	<li>当您点击 &quot;提交&quot; 便上载文件。
</ol>
您可以上载一个以上的图片。可以上载的图片数目是管理员定义的。
<br>
您可以选择在图片 &quot;< 1/2/3 >&quot; 下的导航来浏览图片。您可以更改图片的显示次序，以及选择将显示在图片下面的文本，这可选择在图片下方的 &quot;详情&quot; 链接。
<br>
您可以删除图片。透过查看图片并在图片下方选择 &quot;删除&quot; 链接。其他会员将能够以同样的方式查看您的图片（但他们不能删除图片）。
<hr class="help">

<a name="actions"></a>
<h2>...的行动</h2>
在每个 <a href="#top"><u>个人资料</u></a> 下面，通常都出现一个视窗，具有您可以执行一切有关这会员行动的按钮。
这些行动会包括：给<span class="admin broker">或代表</span>会员付款、发出评语给会员、发送讯息给这个会员等等。
<br>
可用的行动可能会有所不同，这取决于您组织的配置和规则。此外，当您没有有关的权限时，您可能无法使用一些显示在帮助视窗中的行动。
<br><br>帮助视窗给予最多可用行动的摘要，即：可能出现在您视窗的所有行动。您很可能将不会看到所有这些行动，这是取决于您的配置和使用权限。
如需要行动的更详细解释，您可以参阅帮助；当您点击帮助内的按钮，便可到达特定的行动视窗。
您也可以跟随链接，它将给您多一些有关这题目的一般解释。（讨论项目是从左至右，一行行从上到下。）
<br><br>在操作员的帮助会讨论操作员的行动和个人资料。
<hr class="help">

<a name="actions_for_member_by_operator"></a>
<a name="actions_for_member"></a>
<span class="member">
<h3>会员的行动</h3>
在此视窗中，您可以执行有关这会员的各种行动。
<UL>
	<li><b>付款</b>
	<LI><b>查看及提供评语：</b> 看看其他人与这会员的经历和发出对这会员的 <a href="${pagePrefix}references"><u>评语</u></a>。
	<li><b>交易反馈意见：</b> 让您给予交易的 <a href="${pagePrefix}transaction_feedback"><u>意见</u></a>。
	<LI><b>发送</b> <a href="${pagePrefix}invoices"><u>发票</u></a>
	<li><b>查看</b> <a href="${pagePrefix}advertisements"><u>广告</u></a>
	<li><b>发送电邮</b>
	<li><b>发送讯息：</b> 用联环系统的 <a href="${pagePrefix}messages"><u>内部讯息系统</u></a>。
	<li><b>查看报表：</b> 将您带到包含有关会员活动资料的 <a	href="${pagePrefix}reports#member_activities"><u>报表</u></a>。
	<LI><b>新增</b> <a href="${pagePrefix}user_management#contacts"><u>联系人</u></a>
</UL>
<hr class="help">
</span>

<span class="broker"> <a name="actions_for_member_by_broker"></a>
<h3>经纪代表会员的行动</h3>
这个视窗是所有与会员有关的 <a href="${pagePrefix}brokering"><u>经纪</u></a> 行动的出发点。因此，这将是您作为经纪要转到的工作主页。<br>
您可能首先要阅读这视窗的 <a href="#actions"><u>一般注释</u></a>。
<UL>
	<LI><b>管理广告：</b> 新增、删除或修改这会员的 <a href="${pagePrefix}advertisements"><u>广告</u></a>。
	<li><b>账户资料：</b> 带您到这会员的账户历史，显示余额和交易历史。（查看访问）
	<li><b>查看 <a href="${pagePrefix}payments#scheduled"><u>预定付款</u></a></b>
	<li><b><a href="${pagePrefix}payments#authorized"><u>授权付款</u></a>：</b> 这将带您到会员作为付款的收款人而应授权的付款。
	这是适用于配置为在付款新增到收款人的账户余额之前，款项的收款人（会员）应授权付款。
	作为经纪，假如适用的话，您可以充当是这会员，并授权这些付款，导致把它们新增到该会员的余额。
	<li><b>查看付款 <a href="${pagePrefix}payments#authorized"><u>授权</u></a>：</b> 在这里，您去到付款概况，您作为经纪必须代表会员授权，以便从他的账户支付该笔付款。
	这适用的情况是当会员无法执行某些（或全部）付款，因为它们首先必须要取得管理员或经纪的授权。
	<li><b>会员付款给会员：</b> 执行付款给另一会员，犹如您是该会员。
	<li><b>自我付款：</b> 执行这会员不同账户之间的转账。这只适用于拥有一个以上账户的会员。	
	<li><b>会员付款给系统：</b> 执行付款给组织/系统，犹如您是该会员。
	<li><b>管理评语：</b> 在这里，您可以看到其他会员对这会员的评价，您还可看到这会员与其他会员的经历。
	显示了发出和收到的 <a href="${pagePrefix}references"><u>评语</u></a>。经纪可以更改或删除会员发出的评语（如果他有权限）。
	<li><b>查看贷款：</b> 会员的 <a href="${pagePrefix}loans"><u>贷款</u></a> 概况。
	<li><b>查看发票：</b> 查看这会员发送和收到的所有 <a href="${pagePrefix}invoices"><u>发票</u></a>。
	<li><b>会员发票给会员：</b> 发送发票给另一会员，犹如您是该会员。
	<li><b>会员发票给系统：</b> 发送发票给系统，犹如您是该会员。
	<li><b>贷款小组：</b> 显示该会员所属的 <a href="${pagePrefix}loan_groups"><u>贷款小组</u></a>。
	<li><b>管理密码：</b> 让您重置会员的 <a	href="${pagePrefix}passwords"><u>密码</u></a>。
	<li><b>外部访问：</b> 让您管理会员访问联环系统可用的 <a href="${pagePrefix}settings#channels"><u>渠道</u></a>。
	它也让您可以更改会员的 <a href="${pagePrefix}passwords#pin"><u>个人密码</u></a>（一个数字密码用于，例如：访问网上商店）。
	<LI><b>备注：</b> 有关此会员的任何评论可以放在这里。这些备注都是预定给您自己或其他管理员和经纪的。没有会员可以看到这些备注。
	如果在提交按钮前面的备注文本是红色，这意味着它包含了备注。如果备注字段没有文字，备注文本会保留应用程序默认的文字颜色。
	<li><b>...：</b> 在这里将列出您定义的任何其它 <a href="${pagePrefix}member_records"><u>会员记录</u></a> 类型，并有一个按钮。
	<LI><b>会员文件：</b> 此页提供访问可打印的会员 <a href="${pagePrefix}documents"><u>文件</u></a> 页面。
	<li><b>佣金合同：</b> 让您访问您可以审核或建立的 <a
	href="${pagePrefix}brokering#commission_contract"><u>合同</u></a> 页面，这是您和您的会员之间有关您的经纪服务的合同。
	<li><b>短讯日志：</b> 让您访问 <a href="${pagePrefix}reports#sms_log"><u>短讯日志</u></a>，它是存放发送给会员的短讯的地方。
	系统可以配置为在多个场合下发送短讯。
</UL>
<hr class="help">
</span>

<span class="admin"> <a name="actions_for_member_by_admin"></a>
<h3>会员的行动（通过管理员）</h3>
这个视窗是所有与会员有关的管理员行动的出发点。因此，这将是您作为 <a href="${pagePrefix}groups#account_admins"><u>账户管理员</u></a> 要转到的工作主页。<br>
您可能首先要阅读这视窗的 <a href="#actions"><u>一般注释</u></a>。<br>

管理员行动是以节组成的。每一节放置在不同的帮助节，是为了保持文字区块长度的管理和易读性。
<br><br>有以下节存在。点击链接转移到每节的描述按钮。
<ul>
	<li><b><a href="#access_actions"><u>访问节</u></a>：</b> 所有关于控制会员访问系统和软件。
	<li><b><a href="#ads_actions"><u>广告节</u></a>：</b> 关于管理会员的广告。
	<li><b><a href="#accounts_actions"><u>账户节</u></a>：</b> 所有关于会员的账户，包括付款及发票。
	<li><b><a href="#member_info_actions"><u>会员资料节</u></a>：</b> 关于会员的资料、他的活动，等等
	<li><b><a href="#brokering_actions"><u>经纪节</u></a>：</b> 关于联环系统内 <a href="${pagePrefix}brokering"><u>经纪</u></a> 系统的行动。
	<li><b><a href="#loans_actions"><u>贷款节</u></a>：</b> 关于该会员的 <a href="${pagePrefix}loans"><u>贷款</u></a> 行动。
</ul>
<hr class="help">
</span>

<span class="admin"> <a name="access_actions"></a>
<h3>会员行动：访问</h3>
（这概况是 &quot;<a href="#actions_for_member_by_admin"><u>会员的行动</u></a>&quot; 视窗的一部分。）
<ul>
	<li><b>允许使用者现在登入：</b> 这只有当会员的访问被封锁才可看得见。例如，因为多次尝试使用错误密码登录。
	这样的行动会在某一段时间内封锁会员登录，根据 <a href="${pagePrefix}settings"><u>设置</u></a>。
	如果该会员受挫折而联络管理员，您可以通过这个按钮立即解除封锁会员登录。点击后，该会员应能够重新尝试登录。<br>
	<b>注：</b> 当然，这并不是重置会员的登录 <a href="${pagePrefix}passwords"><u>密码</u></a>。
	如果会员忘记了他的密码，您可以通过在这视窗同一节内的 &quot;管理密码&quot; 按钮来重置密码。
	<li><b>断开在线用户：</b> 如果恰恰在这个时刻会员是在线，您可以通过点击这按钮来强行把他登出。<b>只有当</b> 会员现正在线，此按钮才可看见。<br>
	登出会员的原因可能是，如果会员没有正确地登出就关闭了浏览器，当该会员想要在一定时间（超时）内登录，将通知会员他仍然在线。
	当会员联络管理员，他可以用这个职能登出。另一种选择是，只要等待直到超时期限结束；默认的超时值是10分钟。<br>
	另一个登出会员的原因是，在紧急情况下，如：怀疑滥用或黑客攻击账户。
	建议在这些情况下，当他被强行登出之后，（暂时）禁用此会员（您可以通过在此页面同一节内的 &quot;更改组别权限&quot; 按钮做到）。
	<li><b>管理密码：</b> 让您重置会员的 <a href="${pagePrefix}passwords"><u>密码</u></a>。
	<li><b>外部访问：</b> 让您管理会员访问联环系统可以通过的 <a href="${pagePrefix}settings#channels"><u>渠道</u></a>。它也让您可以更改会员的 <a
		href="${pagePrefix}passwords#pin"><u>个人密码</u></a>（一个数字密码用于，例如：访问网上商店）。
	<li><b>更改权限组别：</b> 让您更改会员所属的 <a	href="${pagePrefix}groups"><u>组别</u></a>。每个会员属于一个组别。
	这页面将让您更改会员的组别，并发送激活电邮。控制访问联环系统软件的基本方法，是通过组别。
	如果您要从系统/组织删除会员，您应使用此按钮，并把该会员转移到 &quot;被删除会员&quot; 组别。
	<li><b>短讯日志：</b> 让您访问 <a
		href="${pagePrefix}reports#sms_log"><u>短讯日志</u></a>，它是存放发送给会员的短讯的地方。系统可以配置为在多个场合下发送短讯。
	<li><b>管理卡：</b> 让您 <a href="${pagePrefix}access_devices#search_card_results"><u>管理卡</u></a>，它会显示每个会员的卡。
</ul>
<hr class="help">
</span>

<span class="admin"> <a name="ads_actions"></a>
<h3>会员行动：广告</h3>
（这概况是 &quot;<a href="#actions_for_member_by_admin"><u>会员的行动</u></a>&quot; 视窗的一部分。）
<ul>
	<LI><b>管理广告：</b> 新增、删除或修改这会员的 <a href="${pagePrefix}advertisements"><u>广告</u></a>。
	
</ul>
<hr class="help">
</span>

<span class="admin"> <a name="accounts_actions"></a>
<h3>会员行动：账户</h3>
（这概况是 &quot;<a href="#actions_for_member_by_admin"><u>会员的行动</u></a>&quot; 视窗的一部分。）
<ul>
	<li><b>账户资料：</b> 带您到这会员的账户历史，显示余额和交易历史。（查看访问）
	<li><b>查看 <a href="${pagePrefix}payments#scheduled"><u>预定付款</u></a></b>
	<li><b><a href="${pagePrefix}payments#authorized"><u>授权付款</u></a>：</b> 这将带您到会员作为付款的收款人而应授权的付款。
	这是适用于配置为在付款新增到收款人的账户余额之前，款项的收款人（会员）应授权付款。
	作为管理员，假如适用的话，您可以充当这会员，并授权这些付款，导致把它们新增到该会员的余额。<br>
	这相当于付款权限：&quot;<a href="${pagePrefix}groups#manage_group_permissions_member"><u>授权接收时付款</u></a >&quot;。
	<li><b>查看付款 <a href="${pagePrefix}payments#transfer_authorizations_by_admin"><u>授权</u></a>：</b> 在这里，您去到付款概况，您作为管理员必须 <a 
	href="${pagePrefix}payments#authorized"><u>代表会员授权</u></a>，以便从他的账户支付该笔付款。
	这适用的情况是当会员无法执行某些（或全部）的付款，因为它们首先必须要取得管理员或经纪的授权。<br>
	这相当于权限：&quot;<a href="${pagePrefix}groups#manage_group_permissions_member"><u>账户 > 查看已授权付款</u></a >&quot;。	
	<li><b>系统付款给会员：</b> 从系统账户支付给会员。
	<li><b>会员付款给会员：</b> 执行付款给另一会员，犹如您是该会员。
	<li><b>会员付款给系统：</b> 执行付款给组织/系统，犹如您是该会员。
	<li><b>自我付款：</b> 执行这会员不同账户之间的转账。这只适用于拥有一个以上账户的会员。	
	<li><b>查看发票：</b> 查看这会员发送和收到的所有 <a href="${pagePrefix}invoices"><u>发票</u></a>。
	<li><b>系统发票给会员：</b> 从系统账户发送发票给此会员。这意味着，组织发送帐单给会员，而会员必须支付。
	<li><b>会员发票给会员：</b> 发送发票给另一会员，犹如您是该会员。
	<li><b>会员发票给系统：</b> 发送发票给系统，犹如您是该会员。
	<li><b>信贷额度：</b> 在这里，您可以设置这会员的个人信贷额度。请注意，这信贷额度<b>只</b>适用于这个特定的会员，而不是在组别层面的组别信贷额度。
</ul>
<hr class="help">
</span>

<span class="admin"> <a name="member_info_actions"></a>
<h3>会员活动：会员资料</h3>
（这概况是 &quot;<a href="#actions_for_member_by_admin"><u>会员的行动</u></a>&quot; 视窗的一部分。）
<ul>
	<li><b>查看报表：</b> 将带您到有这会员活动资料的 <a href="${pagePrefix}reports#member_activities"><u>报表</u></a>。
	<LI><b>备注：</b> 有关此会员的任何评论可以放在这里。这些备注都是预定给您自己或其他管理员和经纪的。没有会员可以看到这些备注。
	如果在提交按钮前面的备注文本是红色，这意味着它包含了备注。如果备注字段没有文字，备注文本会保留应用程序默认的文字颜色。
	<li><b>...：</b> 在这里将列出您定义的任何其它 <a href="${pagePrefix}member_records"><u>会员记录</u></a> 类型，并有一个按钮。
	<li><b>管理评语：</b> 在这里，您可以看到其他会员对这会员的评价，您还可看到这会员与其他会员的经历。
	显示了发出和收到的 <a href="${pagePrefix}references"><u>评语</u></a>。经纪可以更改或删除会员发出的评语（如果他有权限）。
	<li><b>交易反馈意见：</b> 让您给予交易 <a href="${pagePrefix}transaction_feedback"><u>意见</u></a>。
	<li><b>发送讯息</b> 用联环系统的 <a href="${pagePrefix}messages"><u>内部讯息系统</u></a>。
	<li><b>发送电邮</b>
	<LI><b>会员文件：</b> 此页提供访问可打印的会员 <a href="${pagePrefix}documents"><u>文件</u></a> 页面。
	<li><b>佣金合同：</b> 让您访问您可以审核或建立 <a
	href="${pagePrefix}brokering#commission_contract"><u>合同</u></a> 页面，这是您和您的会员之间有关您的经纪服务的合同。
</ul>
<hr class="help">
</span>

<span class="admin"> <a name="brokering_actions"></a>
<h3>会员行动：经纪</h3>
（这概况是 &quot;<a href="#actions_for_member_by_admin"><u>会员的行动</u></a>&quot; 视窗的一部分。）
<ul>
	<li><b>设置经纪：</b> 在这页面，您可以更改会员所属的 <a	href="${pagePrefix}brokering"><u>经纪</u></a>。
	<li><b>会员列表（作为经纪）：</b> 这将只适用于如果您正在查看的会员是 <a href="${pagePrefix}brokering"><u>经纪</u></a>。
	在这种情况下，它将显示属于该经纪的会员，并让您有机会为这经纪新增会员。
	<li><b>佣金设置：</b> 这将只适用于如果您正在查看的会员实际上是一个经纪。
	在这里，您可以查看这经纪的 <a href="${pagePrefix}brokering#commission_contract"><u>佣金合同</u></a>，以及搜索经纪和他的会员之间的特定佣金合同。
</ul>
<hr class="help">
</span>

<span class="admin"> <a name="loans_actions"></a>
<h3>会员行动：贷款</h3>
（这概况是 &quot;<a href="#actions_for_member_by_admin"><u>会员的行动</u></a>&quot; 视窗的一部分。）
<ul>
	<li><b>查看贷款：</b> 带您到这会员的 <a	href="${pagePrefix}loans"><u>贷款</u></a> 概况。
	<li><b>发放贷款：</b> 在这里，您提供贷款给会员。
	<li><b>贷款小组：</b> 管理会员所属的 <a	href="${pagePrefix}loan_groups"><u>贷款小组</u></a>。
	
</ul>
<hr class="help">
</span>

<span class="admin"> <a name="actions_for_admin"></a>
<h3>管理员的行动</h3>
在这里，您可以执行几个有关管理员的行动。您可能首先要阅读这视窗的 <a href="#actions"><u>一般注释</u></a>。
<ul>
	<li><b>发送电邮</b>
	<li><b>备注：</b> 有关这管理员的任何评论可以放在这里。备注是默认数据库附带的会员记录。在这里也将列出您为管理员定义的任何其它 <a
		href="${pagePrefix}member_records"><u>会员记录</u></a> 类型，并有一个按钮。正如您可以把会员记录分配给特定的权限 <a
		href="${pagePrefix}groups"><u>组别</u></a>，为管理员组别定义会员记录是有可能的。
	<li><b>更改权限组别：</b> 让您更改管理员所属的组别。每个管理员属于一个组别。
	这页面将允许更改管理员的组别。控制访问联环系统软件的基本方法，是通过组别。
	如果您要从系统/组织删除管理员，您应该使用这个按钮，并把该管理员转移到 &quot;被删除管理员&quot; 组别。
	<li><b>允许使用者现在登入：</b> 这只有当管理员的访问被封锁才可看得见。例如，因为多次尝试使用错误密码登录。
	这样的行动会在某一段时间内封锁管理员登录，根据 <a href="${pagePrefix}settings"><u>设置</u></a>。
	如果该管理员受挫折而联络管理员，您可以通过这个按钮立即解除封锁管理员登录。点击后，该管理员应能够重新尝试登录。<br>
	<b>注：</b> 当然，这并不是重置管理员的登录 <a href="${pagePrefix}passwords"><u>密码</u></a>。
	如果管理员忘记了他的密码，您可以通过在这视窗同一节内的 &quot;管理密码&quot; 按钮来重置密码。
	<li><b>断开在线用户：</b> 如果恰恰在这个时刻管理员是在线，您可以通过点击这按钮来强行把他登出。<b>只有当</b> 管理员现正在线，此按钮才可看见。<br>
	登出管理员的原因可能是，如果管理员没有正确地登出就关闭了浏览器，当该管理员想要在一定时间（超时）内登录，将通知管理员他仍然在线。
	当管理员联络管理员，他可以用这个职能登出。另一种选择是，只要等待直到超时期限结束；默认的超时值是10分钟。<br>
	另一个登出管理员的原因是，在紧急情况下，如：怀疑滥用或黑客攻击账户。
	建议在这些情况下，当他被强行登出之后，（暂时）禁用此管理员（您可以通过在此页面同一节内的 &quot;更改组别权限&quot; 按钮做到）。
	<li><b>管理密码：</b> 在这里，您可以重置管理员的密码。
</ul>
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