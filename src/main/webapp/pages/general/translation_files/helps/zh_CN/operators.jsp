<div style="page-break-after: always;">
<a name="top"></a>
<p class="head_description">操作员职能允许会员定义操作员：在联环系统中可以为会员做一些工作的次级会员。
操作员没有自己的账户，只可以访问其会员的账户，在那里他们可以做一些工作。试想一间小型公司有三名员工，每个员工将成为该公司账户的操作员。<br>
正如会员一样，操作员是以组别组织的。每个会员可定义自己的操作员组别，让会员新增有不同权限的不同级别操作员。
例如：您可以新增一个可以执行付款的超级操作员，和几个只能管理广告的简单操作员。操作员执行的每个付款将有额外的
&quot;执行者&quot; 字段，并且会员有可能用操作员来搜索付款。</p>
<span class="member notOperator"> 
<i>在哪里可以找到它？</i><br>
操作员的职能可以通过主页 &quot;菜单：操作员&quot; 到达。在主菜单节内有几个子菜单，可以让您访问操作员的职能：
<ul>
	<li><b>操作员：</b> 将带您到操作员的搜索视窗。在这里您也可以新增操作员。
	<li><b>在线操作员：</b> 显示现正在线的操作员。
	<li><b>操作员组别：</b> 让您定义不同级别的操作员。
	<li><b>自定字段：</b> 让您新增操作员的特殊字段。
</ul>
</span>
操作员可以通过正常的登录页登录，这将显示 &quot;操作员登录&quot; 的额外链接（在系统设置需要启用操作员模块，以便此链接显示出来）。<br>
操作员也可以给予访问销售点网络模块（销售点网络的资料可以在渠道 <a href="${pagePrefix}payments#accessing_channels"><u>帮助</u></a> 中找到）。
<br><br>

<span class="member">操作员可以通过特别的操作员主要项目单来执行关于会员的行动，这是被称为 &quot;会员操作&quot;，并且只有操作员可看得见。
通过这个，操作员可以访问所有职能，该些职能通常在会员的 &quot;主菜单：个人&quot; 项目内。</span>
<br>
<span class="admin">
<i>如何使它运作？</i><br>
首先必须启用操作员，可以通过在 <a href="${pagePrefix}groups#manage_group_permissions_member"><u>用户和组别 > 组别权限 > 修改权限</u></a>
内的 &quot;操作员&quot; 区块，标记为 &quot;管理操作员&quot; 的复选框做到。<br>
除了设置权限，为了使操作员能够登录，请确定操作员在 <a href="${pagePrefix}settings#access"><u>设置 > 访问设置</u></a> 已启用，可通过 &quot;允许操作员登录&quot; 复选框。
<br><br><b>注1：</b> 如果您自定登录页，请务必保存代码，它是用来使操作员能够登录的。否则，操作员的登录链接可能看不见。
<br><br><b>注2：</b> 操作员模块和操作完全是会员负责的。管理员不可能为会员而管理操作员。管理员唯一可以做的管理工作是从 <a
	href="${pagePrefix}user_management#connected_users_result"><u>在线用户</u></a> 页面断开操作员。
</span>
<hr>

<span class="member notOperator">
<a name="search_operator"></a>
<h3>搜索操作员</h3>
在此页面，您可以搜索（您已注册的）操作员。职能的运作与一般的会员搜索职能相同。在组别的选择框，您可以留下 &quot;所有组别&quot; 筛选器或选择您要搜索的一个或多个组别。<br>
然后点击 &quot;搜索&quot; 以显示您的搜索结果。
<br><br>您也可以新增一个操作员，通过在此视窗下面的下拉框中，选择操作员组别（&quot;新增操作员&quot;）。这下拉框只有在没有搜索结果视窗时才可见。
<hr class='help'>
</span>

<span class="member notOperator"> <a name="search_operator_result"></a>
<h3>搜索操作员结果</h3>
此页面将显示搜索操作员的结果列表。点击操作员的名称或用户名将开启个人资料页。
<hr class="help">
</span>

<span class="member notOperator"> <A NAME="create_operator"></A>
<h3>新增操作员</h3>
在此页面，您可以新增操作员。所有标有红色星号（*）的字段是必须填的。<br>
在填写字段后，您可以直接转到个人资料（&quot;储存并开启操作员的个人资料&quot; 按钮），或者新增一个操作员（&quot;储存并新增操作员&quot; 按钮）。
<hr class='help'>
</span>

<a name="operator_profile"></a>
<span class="member">
<h3>操作员的个人资料</h3>
此视窗显示操作员的个人资料。大多数的字段是不能更改的，虽然有些可以。您应该点击 &quot;更改&quot; 按钮来进行修改；完成后，点击 &quot;提交&quot; 来储存更改。</span>
<span class="member notOperator">
<br><br>当您拣选此视窗时，如果该操作员也在线，他将得到通知。&quot;上次登录&quot; 字段将显示（红色）&quot;在线&quot; 的字句。
<hr class='help'>
</span>

<span class="member notOperator">
<A NAME="actions_for_operator_by_member"></A>
<h3>操作员行动</h3>
在这里，您可以执行操作员的几个行动。这帮助提供所有行动的摘要。如需要有关行动的更详细解释，请参阅帮助内的特定行动。
<br><br>有以下行动：
<UL>
	<LI><b>更改组别权限：</b> 更改这操作员所属的操作员组别。
	<LI><b>发送电邮l：</b> 发送电邮给操作员。这将开启您的本地电邮程式。
	<LI><b>管理登录密码：</b> 更改操作员的密码。
	<li><b>允许使用者现在登入：</b>这只有在操作员试图使用错误的密码登录好几次的情况下才看得见，他的账户也因而暂时被封锁。
	通常情况下，是允许有最大的尝试限额的，如果您经常使用错误的密码尝试登录，您的账户将被暂时封锁一段时间，该时间是管理员设置的。
	如果您肯定这操作员是他所说那位，您可以立即点击这个按钮让他再次登录。在这种情况下，操作员不必等待正常的封锁时间过去。
	<li><b>断开在线用户：</b> 这将只有当操作员在这时刻在线才可以看到。这也将在上面的个人资料视窗内的 &quot;上次登录&quot; 字段中显示为 &quot;在线&quot;。
	在这种情况下，您可以立即点击这个按钮来强逼操作员登出程式。您可能要这样做，例如：当有一个等待的滥用调查，或当操作员无法登录，因为系统认为他已登录。
</UL>
<hr class='help'>
</span>

<span class="member notOperator">
<A NAME="manage_operator_groups"></A>
<h3>管理操作员组别</h3>
这页面显示 <a href="#top"><u>操作员</u></a> 组别的列表。您可以在这里执行以下行动：
<ul>
	<li><img border="0" src="${images}/edit.gif" width="16" height="16">&nbsp;编辑图标将带您到设置这组别的页面。
	<li><img border="0" src="${images}/permissions.gif" width="16" height="16">&nbsp;权限图标将带您到可以设置这组别权限的页面。
	当该组别是 &quot;删除&quot; 状态，此图标将会被禁用（呈灰色，<img border="0" src="${images}/permissions_gray.gif" width="16" height="16">）。
	<li><img border="0" src="${images}/delete.gif" width="16" height="16">&nbsp;点击删除图标会删除组别。您只能删除没有会员（操作员）的组别。
	<li><b>新增：</b> 要新增一个操作员组别，您必须点击标记为 &quot;新增组别&quot; 的提交按钮。
</ul>
<hr class='help'>
</span>

<span class="member notOperator">
<A NAME="manage_group_permissions_basic"></A>
<h3>基本的组别权限</h3>
在这视窗中，您可以设置基本的权限。基本权限不影响其它职能，例如：如果操作员无法登录，但他仍可以收到付款。可以设置以下权限：
<ul>
	<li><b>登录：</b><br>
	如果这不被拣选，这组别的操作员无法登录。
	<li><b>邀请讯息：</b><br>
	如果被拣选，该组别的会员在其主页中会看到一个视窗（登录后），使他们能够邀请朋友来尝试您的组织。
</ul>
<hr class='help'>
</span>

<span class="member notOperator"> <A
	NAME="manage_operator_group_permissions"></A>
<h3>管理操作员组别的权限</h3>
在此视窗中，您可以设置 <a href="#top"><u>操作员</u></a> 组别的 <a href="${pagePrefix}groups#permissions"><u>权限</u></a>。
这些权限是从您自己组别的权限衍生而来的：操作员不能做超过您所允许的；操作员只能做少过或等于它。<br>
由于这个原因，很可能您不会看到所有的选项列出在此帮助概况中。请使用链接以取得更多有关该项目的资料。
<br><br>属于该组别的操作员可能会收到这些权限（根据系统设置和您自己的权限）：
<br><br>
<b>会员账户</b>

<ul>
	<li><b>查看已授权付款</b>
	<li><b>查看预定付款</b>
	<li><b>查看账户资料：</b> 使用下拉框来选择操作员可以看到资料（付款、余额等）的账户。
</ul>

<b>广告</b>
<ul>
	<li><b>发布：</b> 如果选择了 &quot;发布&quot;，操作员可以刊登广告，而且在操作员的项目单中会显示 &quot;个人 - 广告&quot;。
</ul>

<b>联系人列表</b>
<ul>
	<li><b>管理：</b> 允许操作员管理 <a href="${pagePrefix}user_management#contacts"><u>联系人列表</u></a>，也就是在列表中新增、修改或删除会员。
	<li><b>查看：</b> 允许操作员只能查看联系人名单（并使用它），但没有权限更改它。
</ul>

<b>担保</b><br><br>
这是联环系统担保系统的一部分，每个联环系统的账户余额是受支持款项所担保的。您可以选择下列权限：
<ul>
	<li><b>购买时带有付款义务</b></li>
	<li><b>出售时带有付款义务</b></li>
	<li><b>允许担保类型（认证）</b></li>
	<li><b>发行认证</b></li>
	
</ul>

<b>发票</b><br><br>
在本节中，您可以定义操作员是否可以发送 <a
	href="${pagePrefix}invoices"><u>发票</u></a> 给其它会员，无论从用户的个人资料，或者直接从 &quot;账户&quot; 项目单。
	当选择了 &quot;系统发票&quot;，操作员可以从 &quot;账户项目单&quot; 发送发票给系统账户。
<ul>
	<li><b>项目单的会员发票选项：</b> 显示在项目单发票给会员的选项
	<li><b>发送给会员：</b> 允许发送发票给其它会员。
	<li><b>发送给系统：</b>允许发送发票给系统账户。
	<li><b>查看：</b> 查看发票。
</ul>

<li><b>贷款：</b> 在本节中，您可以定义操作员的 <a href="${pagePrefix}loans"><u>贷款</u></a> 权限。
<ul>
	<li><b>查看：</b> 如果拣选 &quot;查看&quot; 选项，组别的操作员可以查看其贷款。如果没有拣选查看，项目单不会显示。
	<li><b>偿还：</b> 拣选这项让操作员执行偿还贷款。
</ul>

<li><b>讯息：</b> 在本节中，您可以定义操作员可使用联环系统的 <a href="${pagePrefix}messages"><u>讯息</u></a> 系统的程度。
<ul>
	<li><b>查看：</b> 操作员可以查看讯息系统。
	<li><b>发送给会员：</b> 操作员可以发送讯息给其他会员。
	<li><b>发送给管理员：</b> 操作员可以发送讯息给管理员。
	<li><b>管理：</b> 操作员可以转移和删除讯息。
</ul>


<b>付款：</b> 在这里，您可以指定允许操作员组的付款方式。多数您只会选择一种或几种方式。
<ul>
	<li><b>自我付款：</b> 如果选择了它，操作员可以在自己的账户之间进行付款。在下拉框中您可以指定可能的交易类型。只当您有一个以上的操作员账户时，此选项才会有意义。
	<li><b>会员付款：</b> 如果选择了它，操作员可以支付另一会员。
	<li><b>项目单有会员付款选项：</b> 如果拣选这个选项，操作员可以从 &quot;账户&quot; 项目单直接执行付款给其它会员。
	<li><b>付款给系统：</b> 如果选择了它，操作员可以付款给系统账户。如果没有拣选此选项，项目单 &quot;系统付款&quot; 不会显示。
	<li><b>作出销售点网络付款：</b> 允许操作员在销售点网络页付款。
	<li><b>接收销售点网络付款：</b> 选择此选项，如果您想允许操作员通过销售点网络接收款项。这通常是商店的情况。
	在商店柜台的人将作为操作员登录（正常通过 http://..cyclos/posweb）到销售点网络接口；在下面的视窗，客户可以输入他的密码进行付款给商店。<br>
	拣选此复选框可以允许进行这个程序。（通常，您会禁用操作员其它所有的权限。）
	<li><b>授权或拒绝：</b> 如果您是收款人，允许操作员授权或拒绝付款。
	<li><b>取消付款授权：</b> 当使用授权付款时，在授权付款已新增，但尚未批准的情况下，这将允许操作员取消他们的授权付款。
	<li><b>取消预定付款：</b> 当使用预定付款时，这将允许操作员在计划付款日期开始前取消他们的预定付款。
	<li><b>封锁预定付款：</b> 允许操作员暂时封锁他的预定付款。
	<li><b>请求从其他渠道付款：</b> 如果选择了它，操作员可以从其它渠道发送付款请求（外部发票），您可以从下拉框中选择这些渠道。
</ul>

<b>评语</b><br><br>
这允许操作员查看或管理 <a href="${pagePrefix}references"><u>评语</u></a>。
<ul>
	<li><b>查看：</b> 查看评语
	<li><b>管理我的评语：</b> 可允许操作员使用评语系统，包括发出评语给其他会员的权限。
	<li><b>管理我的交易反馈意见：</b> 允许操作员管理您的 <a href="${pagePrefix}transaction_feedback#feedbacks_summary"><u>交易反馈意见</u></a>，
	包括发出交易反馈意见的权限。
</ul>

<b>报表</b><br><br>
如果拣选 &quot;查看我的报表&quot;，操作员可以查看您自己的 <a href="${pagePrefix}reports#member_activities"><u>报表页</u></a>。
</ul>
<hr class='help'>
</span>

<span class="member notOperator"> <a name="edit_operator_group"></a>
<h3>修改操作员组别</h3>
<a href="#top"><u>操作员</u></a> 组别的配置分为两部分：
<ul>
	<li><b>组别详细资料：</b> 在这里，您只可以更改操作员组别的名称和描述。
	<li><b>每个付款方式每天最高限额：</b> 您可以使用这些设置来定义每个付款方式每天的最高限额。
	这里列出所有可用的付款方式；对于每种方式，您可以指定操作员可以支付此付款方式的最高限额，如果有的话。
</ul>
您可以点击 &quot;组别权限&quot; 直接进入该组别的权限。
<hr class='help'>
</span>

<span class="member notOperator">
<A NAME="insert_operator_group"></A>
<h3>新增操作员组别</h3>
这视窗让您可以新增一个 <a href="#top"><u>操作员</u></a> 组别。
<br>
您有以下选项：
<ul>
	<li><b>删除：</b> 如果您新增一个删除组别，这意味着放在这组别的操作员已经真正离开了系统。一旦被放在删除组别便不能回到其它组别。
	该数据仍会在数据库中和您可以查看，但它只作为一种备份职能。
	<li><b>名称：</b> 组别的名称。
	<li><b>描述：</b> 组别的描述。
	<li><b>复制设置：</b> 如果已经定义了另一操作员组别，这才会看得见。在这里，您可以指定另一操作员组别，然后将从您所指定的组别复制设置到新增的组别。
</ul>
新增新组别后，您应该在下一视窗中设置组别属性，您也应该设置组别权限。
<hr class='help'>
</span>

<span class="member notOperator">
<a name="manage_group_customized_files"></a>
<h3>自定销售点网络</h3>
您可以定义销售点网络的页眉和页脚。此视窗显示这个组别所自定的列表。您有以下选项：
<ul>
	<li><b>修改</b> 现有的自定文件，通过 <img border="0" src="${images}/edit.gif" width="16" height="16">&nbsp;编辑图标。
	<li><b>查看</b> 组别搜索结果的会员会看到的样式，通过 <img border="0" src="${images}/view.gif" width="16" height="16">&nbsp;查看图标。
	<li><b>删除</b> 自定文件的定义，通过 <img border="0" src="${images}/delete.gif" width="16" height="16">&nbsp;删除图标。
	<li><b>新增</b> 自定文件，通过 &quot;自订新文件&quot; 按钮。
</ul>
<hr class="help">
</span>


<span class="member notOperator"> <a name="customize_group_file"></a>
<h3>修改销售点网络的页眉和页脚</h3>
在此页面，您可以自定销售点网络页面的页眉和页脚。操作员可以访问这页面来付款和收款。
（销售点网络网址通常像 www.domain.com/cyclos/posweb）<br>
在操作员登录后，页眉和页脚将会显示在付款视窗的上方和下方。
<hr class="help">
</span>


<span class="member notOperator">
<A NAME="change_group_operator"></A>
<h3>更改操作员组别</h3>
在此视窗中，您可以把 <a href="#top"><u>操作员</u></a> 放在另一组别。
这意味着操作员将收到另一组别的权限。填写表格后，点击 &quot;更改组别&quot; 按钮来储存和提交更改。
<br>
<br><br>点击 &quot;永久删除&quot; 选项，将删除操作员。这只限于从来没有执行任何交易的操作员。<br>
否则，您将要把他转移到 &quot;删除&quot; 组别；这意味着该操作员不能执行任何行动（甚至不能登录），但您仍然可以查看他过去的行动。
<hr class='help'>
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