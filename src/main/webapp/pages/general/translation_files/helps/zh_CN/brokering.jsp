<div style="page-break-after: always;">
<p class="head_description">
&quot;经纪&quot; 类型组别的会员可以注册新会员，并有一定程度的访问和控制这些会员（取决于经纪组别的配置）。
&quot;经纪&quot; 这个词不是很充分，因为这一功能可用于许多不同的目的。<br>
一个通常的经纪职能是，经纪注册会员时，可以接收 <a href="#commission"><u>佣金</u></a>。
佣金可配置为新会员所做的贸易数额方面。这个想法是，可以激励经纪更严肃地与新会员 &quot;熟络&quot;。<br>
一个经纪也可以被允许部分地管理一个组别会员，他们可能感到用电脑做某些联环系统会员任务是太不安全。<br>
<span class="broker admin"> 经纪的功能也可以由贷款代理人在微型融资系统使用。贷款代理人作为经纪，也可以注册新会员和检索会员贷款状态的信息。
社区系统，如本土交易系统（LETS），可以让社区的（账户）管理员使用经纪功能，协助其他没有机会或能力使用互联网/手机/文本的会员。不同类型的经纪组别可以存在于同一系统。</span></p>
经纪可以与每个会员成立佣金的合同。

<p><span class="broker"> <i>在哪里可以找到它？</i><br>
您可以通过 &quot;菜单：经纪&quot;，访问所有经纪职能<br>
&quot;配有经纪&quot; 的会员可在会员的 <a href="${pagePrefix}profiles#member_profile"><u>个人资料</u></a> 下方的 
<a href="${pagePrefix}profiles#actions_for_member_by_broker"><u>经纪行动网页</u></a> 找到经纪活动的信息和有关的职能。</span>
<p><span class="admin"> <i>在哪里可以找到它？</i> <br>
管理员没有经纪的职能，但他们可以一定程度上访问经纪和已被分配经纪的会员的经纪职能。
这是可以在会员或经纪的 <a href="${pagePrefix}profiles#member_profile"><u>个人资料</u></a> 下方的
<a href="${pagePrefix}profiles#actions_for_member_by_admin"><u>行动网页</u></a> 做到。<br>
经纪功能可用于多种用途；因此，搜查和有关经纪职能可能会出现在其他的模块。例如，贷款搜索可以有经纪搜索筛选器；管理员可以批量执行有关经纪的行动。这些职能会在它们的范围内解释。

<p><i>如何使它运作？</i><br>
为了启用经纪您必须设置 <a href="${pagePrefix}groups#manage_group_permissions_broker"><u>经纪权限</u></a> 的 &quot;经纪&quot;
和 <a href="${pagePrefix}groups#edit_broker_group"><u>经纪组别设置</u></a> 的 &quot;经纪&quot;。
后者只有一个项目：如果您想经纪能够注册会员，您必须在这里设置。<br>
经纪佣金可在 <a href="${pagePrefix}brokering#broker_commission_list"><u>经纪佣金</u></a> 的
<a href="${pagePrefix}account_management#transaction_type_details"><u>交易类型</u></a> 级别配置启用。<br>
您还需要设置 <a href="${pagePrefix}groups#manage_group_permissions_broker"><u>经纪权限</u></a> 内的 &quot;经纪&quot;。
要会员可以查看佣金，还必须设置 <a href="${pagePrefix}groups#manage_group_permissions_member"><u>权限</u></a>（&quot;佣金&quot;）。
<p><b>请注意</b>：让经纪或会员查看佣金，但没有在管理员的交易类型配置和启用佣金，是没有意义的。
在这种情况下，经纪和会员将看到空框，因为他们有机会查看经纪佣金和合同，但不能设定它们，因为它们根本不存在。<br>
请不仅新增经纪佣金，而且也把它启用。
</span>
<hr>

<span class="broker admin">
<A NAME="broker_search_member"></A>
<h3>搜索经纪的会员</h3>
此功能提供了经纪的会员列表。
<br>
您可以搜索：
<ul>
	<li><b>登录名/会员：</b> 搜索个别会员
	<li><b>权限组别：</b> 搜索组别
	<li><b>状态：</b>
	<ul>
		<li><b>活跃：</b>这是在系统活跃的会员（在一个 &quot;活跃&quot; 的组别）
		<li><b>会员佣金完成：</b>显示会员的 <a href="#commission"><u>佣金</u></a> 已收到和完成。
		<li><b>等待激活：</b>这是您已注册但尚未活跃的会员（因为他们必须由经纪或管理员激活）。
	</ul>
</ul>
<hr class="help">
</span>

<span class="broker">
<A NAME="broker_search_member_result"></A>
<h3>经纪-搜索会员结果</h3>
此视窗显示由您作为经纪注册的会员名单。
<br>
会员名单中您可以选择会员或用户名称，以开启会员的
<a href="${pagePrefix}profiles"><u>个人资料</u></a> 页。
<hr class="help">
</span>

<span class="admin">
<A NAME="admin_brokering_list"></A>
<h3>（经纪的）会员名单</h3>
这页列出有关某一特定经纪的所有会员。您可以选择名称进入会员的个人资料。<br>
点击 <img border="0" src="${images}/delete.gif" width="16" height="16">&nbsp;删除图标，将取消会员的指定经纪（会要求您进行确认）。
<hr class="help">
</span>


<span class="admin">
<A NAME="add_member_to_broker"></A>
<h3>新增经纪会员</h3>
在此页您可以在经纪的经纪名单添加一个会员。键入时，登录及会员名称字段将自动完成。
如果会员与另一经纪还有联系和有活跃的 <a href="#commission"><u>佣金</u></a>，您可以选择 "暂停佣金"。<br>
请注意，您可以通过 <a href="${pagePrefix}user_management#bulk_actions"><u>整批行动</u></a>，将整批会员重新转到另一个经纪。
<hr class="help">
</span>

<span class="admin">
<A NAME="change_broker"></A>
<h3>设定/更改经纪...</h3>
在此页您可以设定与会员有关的经纪。
&quot;当前经纪&quot; 将显示会员当前的经纪，如果适用。这个字段可以是空白的 &quot;无&quot;，因为会员不一定有有关的经纪。
如果您想设定会员的经纪，您可以在自动完成的登录或名称字段输入新的经纪。当经纪显示后，您可以更改经纪的（拣选）书面意见，然后按提交按钮。<br>
如果您想停止当前的经纪任何仍然活跃的 <a href="#commission"><u>佣金</u></a>，您可以拣选 &quot;暂停佣金&quot;字段。
否则新经纪将继承旧经纪的佣金设置。这就意味着，在配置佣金的设置中，从该经纪得到新的会员之日起，将收取直到结束日期的佣金。
<hr class="help">
</span>

<span class="admin">
<a name="remove_member_to_broker"></a>
<h3>删除会员</h3>
此视窗的标题可能有点令人震惊，但唯一会发生的事情，如果您单击提交的话，是会员已不再与他的经纪有联系。因此，会员是删除了指定的经纪。<br>
在单击 &quot;提交&quot; 按钮之前，您可以加入删除原因。<br>
请注意，您可以通过 <a href="${pagePrefix}user_management#bulk_actions"><u>整批行动</u></a>，将整批会员重新转到另一个经纪。
<hr class="help">
</span>
<hr class="help">

<a name="commission"></a>
<p class="sub_description">
<h2>经纪佣金</h2>
经纪可能会收到工作上的佣金；这是经纪一个连接到其会员的活动的细小付款。当经纪注册一个新会员，该会员通常成为经纪的会员。
该经纪可以获得这新会员参与的每笔交易的一个细小付款。背后的想法是，它会刺激经纪认真地与新会员熟络。
</p>
当经纪转移到任何其他组别后，所有合同和执行佣金将会关闭。
<hr>

<span class="admin broker">
<A NAME="broker_commission_list"></A>
<h3>经纪佣金名单</h3>
此视窗列出所有配置的经纪 <a href="#commission"><u>佣金</u></a>（无论是启用或禁用）。
<ul>
	<li><img border="0" src="${images}/edit.gif" width="16" height="16">
	单击该图标以修改经纪。
	<li><img border="0" src="${images}/delete.gif" width="16" height="16">
 	单击该图标以删除经纪。
</ul>
单击 &quot;新增经纪佣金&quot; 以增加一个新的佣金。
<hr class="help">
</span>


<span class="admin broker">
<A NAME="broker_commission_details"></A>
<h3>经纪佣金细节</h3>
就像交易费用，当 &quot;原本&quot; 的交易进行，而经纪佣金也正确配置时，才会贷记/借记经纪的 <a href="#commission"><u>佣金</u></a>。<br>
一如往常，用 &quot;修改&quot; 按钮，以便能够进行更改；点击 &quot;提交&quot; 储存更改。
<p>
佣金有以下配置：
<ul>
	<li><b>交易类型：</b> 这是启用经纪佣金的交易类型。
	<li><b>名称：</b> 经纪佣金的名称。
	<li><b>描述：</b> 佣金的描述。
	<li><b>启用：</b> 当拣选时，佣金是活跃。请务必检查这一点，否则它将无法工作。
	<li><b>支付者：</b> 在这里您可以设定谁将会支付佣金。这可能是付款人、收款人或系统账户。
	<li><b>接收者：</b> 在这里您可以设定谁将会接收佣金。这可能是付款人的经纪或收款人的经纪。
	<li><b>允许任何账户：</b>
	如果拣选它，便不会限制交易费用的范围。如果您收取的费用来自与交易类型不同的其他账户，您需要拣选此选项。例如，您想佣金交易类型是用其他货币。
	<li><b>产生的交易类型：</b>
	在这里，您设定会产生的交易类型。通常会建立一个特别的交易类型，让您可以进行筛选（例如在账户历史，用费用及税金）。默认数据库具有交易费用和它的交易类型。
	<li><b>金额：</b>
	在这里，您可以设定佣金数额。每次会员作出付款（如果条件适用），经纪将获得佣金。<br>
	如果是经纪合同，经纪佣金价值可以由经纪改变。在这种情况下，价值数额将用作佣金合同的默认值。
	经纪可以在新增每个会员的 &quot;经纪合同&quot; 时，更改这些值。见下一个项目。
	<li><b>最高金额固定和％：</b>
	这些选项与经纪合同有关，只在 &quot;支付者&quot; 选项指定是会员（而不是系统账户）时，才会显示。
	它的价值将确定经纪可以在经纪合同设定的最大数额（经纪需要有权限管理经纪合同）。
</ul>

<li><b适用条件</b><br> 
在这里您可以设定费用适用的条件。只会收取符合适用条件的费用。条件可以组合在一起。
<ul>
	<li><b>金额大于或等于：</b>
	只当交易金额大于或等于指定的金额时，才会收取费用。
	<li><b>数额较小或等于：</b>
	只当交易金额小于或等于指定的金额时，才会收取费用。
	<li><b>所有支付组别：</b>
	如果拣选它，费用将适用于付出了交易类型的付款的任何一组别的会员。如果您想费用只针对特定的组别，则需要取消此框，然后会出现多选择下拉框，让您指定各个组别。
	<li><b>所有接收组别：</b>
	如果拣选它，费用将适用于接受了交易类型的付款的任何一组别的会员。如果您想费用只针对特定的组别，则需要取消此框，然后会出现多选择下拉框，让您指定各个组别。
	<li><b>所有经纪组别：</b>
	如果拣选它，费用将适用于任何有关的经纪组别的经纪。如果您想费用只针对特定的经纪组别，则需要取消此框，然后会出现多选择下拉框，让您指定各个组别。
	<li><b>佣金何时支付：</b>
	您可以在这里设定佣金何时支付。这可以是：
	<ul>
		<li><b>永远：</b>
		经纪佣金将永远（无限）地支付。（从页面 会员的个人资料-设定经纪，可以停止佣金）。
		<li><b>交易数目：</b>
		当超过一定的交易数目后，经纪佣金会停止支付。如果选择此选项，将在这个下拉框前面出现输入框，让您填入数目。
		<li><b>天数：</b>
		当超过一定的天数后，经纪佣金会停止支付。如果选择此选项，将在这个下拉框前面出现输入框，让您填入天数。
	</ul>
</ul>
<hr class="help">
</span>

<span class="admin broker">
<A NAME="commission_settings"></A>
<h3>佣金设置</h3>
在此视窗中您可以检查默认的 <a href="#commission"><u>佣金</u></a> 设置。
这些设置将应用于所有新注册会员，除非它们被经纪的设置所推翻，或由经纪和会员之间的个别合同推翻。
<br>如果管理员没有确定任何佣金的设置，这个视窗将是空的。如果您已经启用会员和经纪的佣金管理，您也应该设定默认佣金。见本文件的顶端。
</span>
<span class="broker">
在这个视窗您可以设定默认 <a href="#commission"><u>佣金</u></a> 的设置。这些设置将应用到您所有新的注册会员。只要您在此视窗中没有设置默认佣金，管理员可以变更默认佣金设置。
当您在这个视窗上设置您的佣金设置，它们将推翻管理员的设置。
<br>请注意，任何您设置给您的会员的个别合同，将再次推翻这里改变的设置。
<br>
如果管理员没有设定任何佣金设置，这个视窗将是空的。在这种情况下，您应该通知管理员。
<br>
您可以通过点击 &quot;更改&quot; 按钮，更改您的设置；使用
&quot;提交&quot; 按钮储存更改。这只可以您有权限更改默认设置时，才会显示。
<br>

状态是不能改变的。
</span>
状态可以有以下值：<br>
<ul>
	<li><b>活跃：</b>
	这意味着，如果条件适用，将收取所有配置好的经纪佣金。
	<li><b>不活跃：</b>
	此状态意味着不可以收取佣金。如果是这种情况，意味着这是由管理员作的全系统的配置。
	<li><b>暂停：</b>
	经纪佣金是暂时中止。
</ul>
<span class="admin"> 管理员可以选择暂停（暂时停止）所有活跃的佣金。
甚至当经纪已启用 <a href="#commission_contract"><u>合同</u></a>，而可以增加新的佣金合同，但它们将直接进入暂停状态。
</span>
<hr class="help">

<a name="commission_contract"></a>
<p class="sub_description">
<h2>佣金合同</h2>
佣金合同是经纪和会员之间的一个安排。通常情况下，经纪收取会员每次付款的一个百分比或固定数额。
这个 <a href="#commission"><u>佣金</u></a> 可以由付款会员、收款会员或组织（从系统账户）支付。<br>
根据不同的配置，经纪可以自由地与他的每个会员建立了不同的个人合同。会员需要确定合同，它才可以使用；会员可以选择接受或者拒绝新提议的经纪合约。
</p>
会员可以查看佣金详细情况；佣金状态改变时，会员和经纪都将收到通知。
<p>
<span class="broker">
根据系统设置，经纪可以为每个会员设定佣金。每期只能有一个活跃的佣金（如果佣金期间不重叠，有可能有更多的佣金）。<br> 
<br>注意：当经纪转移到任何其他组别时，所有合同和佣金将会关闭。
<br><br>
<i>在哪里可以找到它？</i><br>
佣金合同可以在 &quot;菜单：经纪 > 佣金合同&quot; 找到。您可以通过会员的个人资料，为会员添加一个新的佣金合同：在
&quot;经纪活动&quot; 视窗，&quot;佣金合同&quot; 按钮。在页面的底部，有新增合同的按钮。
</span>
<span class="member">
<i>在哪里可以找到它？</i><br>
您可以在 &quot;菜单：个人 > 佣金征收状态&quot; 找到经纪合同。
这只在您有权限时才看得见。
</span>
<hr class="help">

<span class="admin broker">
<A NAME="commission_contracts_search_filters"></A>
<h3>搜索经纪佣金合同</h3>
在此网页上您可以搜索现有的 <a href="#commission_contract"><u>佣金合同</u></a>。<br>
大多数搜索选项是不言自明的。状态的解释在 <a href="#commission_contract_status"><u>这里</u></a>。
<hr class="help">
</span>

<span class="admin broker">
<A NAME="commission_contracts_search_results"></A>
<h3>佣金合同搜索结果</h3>
此名单视窗会显示所有 <a href="#commission_contract"><u>佣金合同</u></a> 和它们的 <a href="#commission_contract_status"><u>状态</u></a>。
<ul>
	<li><img border="0" src="${images}/edit.gif" width="16" height="16">
	点击放大镜图标以显示细节。
	<li><img border="0" src="${images}/delete.gif" width="16" height="16">
	点击删除图标以删除该合同。这只是当您有权限看得见，而该合同是不活跃的状态。
</ul>
<hr class="help">
</span>

<a name="commission_contract_status"></a>
<h3>佣金合同状态</h3>
<a href="#commission_contract"><u>佣金合同</u></a> 可以有以下状态：
<ul>
	<li><b>待处理：</b>
	该合同将在待处理状态，直到会员确认并接受该佣金合同。
	<li><b>接受：</b>
	此状态是指会员已接受了该佣金，但还没有达到该合同的开始日期。
	<li><b>活跃：</b>
	一旦经纪佣金已被接受并已达到合同的开始日期，佣金将在活跃状态；这意味着，该佣金将根据合同的设置收取。
	<li><b>拒绝：</b>
	如果合同被会员拒绝，它将在否认状态。
	<li><b>过期：</b>
	如果合同在开始日期之前不被接受，它将进入过期状态。
	<li><b>取消：</b>
	经纪可以取消佣金合同，意味着今后的付款将不会再产生佣金。
	<li><b>完结：</b>
	佣金合同已经完结，因为结束日期已经过去了，佣金也已被收取。
</ul>
<hr class="help">

<A NAME="commission_charge_status"></A>
<h3>佣金状态</h3>
此视窗显示了当前 <a href="#commission"><u>佣金</u></a> 信息的快速结果。
这可能是一个默认的佣金或个别经纪 <a href="#commission_contract"><u>佣金合同</u></a>。信息不言自明。
合同可在其中一种 <a href="#commission_contract_status"><u>状态</u></a>。
<hr class="help">

<A NAME="commission_contracts_list"></A>
<h3>佣金合同名单</h3>
此视窗会显示本会员所有 <a href="#commission_contract"><u>佣金合同</u></a> 的名单和它们的 <a href="#commission_contract_status"><u>状态</u></a>。
<span class="admin broker">
<ul>
	<li><img border="0" src="${images}/view.gif" width="16" height="16">&nbsp;
	点击放大镜图标以显示细节。
	<li><img border="0" src="${images}/delete.gif" width="16" height="16">&nbsp;
	点击删除图标以删除该合同。这只是当您有权限看得见，而该合同是不活跃的状态。
	<li>您可以 <b>添加</b> 一个新的佣金，在这个视窗右下角，题为 &quot;新的合同&quot; 的下拉框选择一个合同类型。
	如果管理员没有设定任何佣金设置，此下拉框将是空的。在这种情况，您应该通知管理员。
</ul>
</span>
<span class="member">
您可以点击 <img border="0" src="${images}/view.gif" width="16" height="16">&nbsp;放大镜图标，即可看到详细内容。
这会带您到一个视窗；如果合同的状态为 &quot;待定&quot;，您可以接受或拒绝这个建议的合同。
</span> 
<hr class="help">

<A NAME="commission_contract_edit"></A>
<h3>修改/查看经纪佣金合同</h3>
这视窗显示您选择的 <a href="#commission_contract"><u>佣金合同</u></a> 细节。
<a href="#commission_contract_status"><u>点击这里</u></a> 以显示可能的状态值。<br>
一旦状态是活跃，便不能删除佣金合同。不过，您可以 <a href="#commission_contract_status"><u>取消</u></a>
合同，通过点击 &quot;取消&quot; 按钮。
<br><br>
<span class="member">如果状态是 &quot;待处理&quot;，意味着这是您的经纪提给您的一个新合同。在这种情况下，您可以接受或拒绝建议的安排，通过点击这两个在底部的按钮。
除非您接受了它，否则建议的经纪合同将不适用。</span>
<hr class="help">

<A NAME="commission_contract_insert"></A>
<h3>新增经纪佣金合同</h3>
这视窗让您为选定的会员新增一个 <a href="#commission_contract"><u>佣金合同</u></a>。<br>
它的状态一定是 &quot;待处理&quot;，因为合同只能在会员接受或拒绝它后，才进入另一个状态；在刚新增它的时候，这是不可能的。<p>
您必须设置新合同的 &quot;起始日期&quot;、&quot;结束日期&quot; 和数额。
您可以通过日期选择器 <img border="0" src="${images}/calendar.gif" width="16" height="16">&nbsp;图标选择日期。<p> 
一旦状态是活跃，便不能删除佣金合同。不过，您可以 <a href="#commission_contract_status"><u>取消</u></a>
合同，通过点击 &quot;取消&quot; 按钮。

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