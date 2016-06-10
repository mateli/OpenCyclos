<div style="page-break-after: always;">
<p class="head_description">联环系统把用户分类为组别。系统内的每个用户只能在一个组别。
有三个 <a href="#group_categories"><u>主要类别</u></a> 的组别。<br>
组别是用来授予权限给软件的用户。如果他的组别不允许，用户便无法获得联环系统的功能。
当然，可以更改组别的权限，或移动用户从一个权限组别到另一个。<br>
除了权限外，组别也有特定的组别 <a href="#edit_member_group"><u>设置</u></a>，界定组别的行为，例如限制和允许的访问类型。
会员组别比管理员组别有更多的配置选项。在会员组别设置内，您可以定义例如该组别有什么账户，以及布局和特定组别的内容项目。
<p>一个会员只能在一组别，但这并不意味着所有的系统配置是具体组别的。系统仍是非常灵活。配置和设置存在于系统、组别和个人级别。
如果同样的设置存在于不同的级别，低级别将享有优先权。例如个人的信贷额度将掩盖组别的信贷额度；一个自定的组别网页（例如联系页）将掩盖系统的联系网页。<br>
许多配置可设置在多个组别。例如贡献可以配置在几个会员组别收取。
<p>联环系统附有一套标准的组别。正常情况下，这些组别应满足系统的运行。<br>
您不仅可以使用标准的组别给予的权限，而且也可管理您的组别。
例如，如果要从系统删除一个会员，只要通过 &quot;更改组别&quot; 的功能，将他移到 &quot;删除会员&quot; 组别。
此功能还记录了所有组别更改的日期、时间和完成行动的管理员。<br>
虽然标准组别的配置适合大多数组织，但也可以新增组别。然而，这应该是有经验地运行了系统一段时间之后才做。

<p><span class="admin"><i>在哪里可以找到它？</i><br>
组别管理可在 &quot;菜单：用户和组别 > 权限组别&quot; 主要项下找到。<br>
组别筛选器可在 &quot;菜单：用户和组别 > 组别筛选器&quot; 找到。

<p><i>如何使它运作？</i><br>
新会员将永远是一个组别的一部分。因此，新增一个会员或管理员时，需要选择组别。
这可在 &quot;用户和组别 > <a href="${pagePrefix}user_management#search_member_by_admin"><u>管理会员</u></a>&quot;
和 &quot;用户和组别 > <a href="${pagePrefix}user_management#search_admin"><u>管理管理员</u></a>&quot; 找到。<br>
</span></p>
<hr>

<a name="group_categories"></a>
<p class="sub_description">
<h2>组别的主要类别</h2>
主要有三种类型的组别：
<ul>
	<li><a href="#member_groups"><u>会员组别</u></a> - 普通会员，可以访问联环系统会员节。
	<li><a href="#broker_groups"><u>经纪组别</u></a> - 一种 &quot;超级会员&quot;，允许执行其他会员组别的一些行政职能。
	<li><a href="#admin_groups"><u>管理员组别</u></a> - 提供行政职能的用户。
</ul>
这样分割主要类别是出于安全考虑，因此不会意外地给会员一些管理<a href="#permissions"><u>权限</u></a>。
<br>
所有组别具有默认权限，也可以修改它们。
</p>
<hr class="help">

<a name="member_groups"></a>
<p class="sub_description">
<h2>标准会员组别</h2>
这些组别的会员有机会访问联环系统会员节。<br>
系统具有以下标准会员组别：
<ul>
	<li><a href="#inactive_members"><u>不活跃会员</u></a>：如果这个组别
设置为初始组别，用户便无法登录，需要由管理员 &quot;激活&quot;（放置在一个活跃的会员组别）才可以登录。
	<li><a href="#full_members"><u>正式会员</u></a>：一个正常的会员。
	<li><a href="#disabled_members"><u>禁用会员</u></a>：暂时闲置的会员。
	<li><a href="#removed_members"><u>删除会员</u></a>：已肯定离开系统的会员。
</ul>
</p>
默认的组别也不是固定或 &quot;硬编码&quot; 的，而是建基于我们认为是很常用的使用组别的
<a href="#permissions"><u>权限</u></a> 和 <a href="#edit_member_group"><u>设置</u></a>。
也可以修改或创造新的组别，使用不同的配置。
<hr class="help">

<a name="inactive_members"></a>
<h3>不活跃会员组别</h3>
当用户通过注册注册网页，他将自动在 &quot;不活跃会员&quot; 组别。这一组别的会员无法登录和使用系统，也没有有效的账户。
账户管理员可以要求列表这一组别的用户；在确认此用户后，把他放入了一个 &quot;活跃&quot; 的组别，通常是
<a href="#full_members"><u>&quot;正式会员&quot;</u></a> 组别。
<br>
由于不活跃会员没有相关的账户，意味着可以完全由系统删除会员。一旦会员已在
&quot;活跃&quot; 组别（拥有账户），便不能删除该会员了，
只能放在 <a href="#removed_members"><u>已删除会员</u></a>
组别。更多不活跃/活跃状态组别的讯息可以在此 <a href="#group_details"><u>帮助</u></a> 找到。
<hr class="help"> 

<a name="full_members"></a>
<h3>正式会员组别</h3>
这是会员的正常组别。&quot;正式会员&quot; 可以登录并选择任何会员功能。
当一个会员从之前的待定会员组别转到正式会员组别，他将被 &quot;激活&quot;，这意味着他可以得到一个账户与初始默认信贷（如果已配置），也将收到登录系统的密码。
<br>
如果已配置，他可以收到有登录讯息和账户状态的 &quot;激活邮件&quot;。
<hr class="help">

<a name="disabled_members"></a>
<h3>禁用会员组别</h3>
当管理员把一个会员放在 &quot;禁用会员&quot; 组别，会员便无法登录了。该账户是在 &quot;休眠&quot; 状态。
这一组别的会员不收取税款和支付缴款。唯一的活跃功能，是 &quot;禁用会员&quot; 仍然可以接收付款（但他们无法登录看到它）。
<br>
禁用会员的广告将不会出现在其他会员做的搜索广告。但禁用会员的个人资料将出现在会员搜索。当查看禁用会员的个人资料，将显示会员是禁用的讯息，并（在这个时刻）不能进入该系统。
<br>
为了由本组别重新启动会员，需要由管理员放回 <a href="#full_members"><u>正式会员</u></a> 组别。
<br>
把一个会员放在禁用组别的一个典型原因，是一个会员已移居国外一段有限的时间（如4个月）。它也可以被用来把可疑会员在这一组别（待进一步调查），以避免他们登录。
<hr class="help">

<a name="removed_members"></a>
<h3>删除会员组别</h3>
把会员移到 &quot;删除会员&quot; 组别的原因，是会员已经离开了系统。一旦进入删除组别，会员便不能重新进入任何其他组别。
他的数据仍会在数据库中，供管理员查看，但只能作为备份功能。
<br>
其他会员将不会见到删除会员的任何数据（广告、个人资料）。只有交易记录会显示与此会员在过去的交易。
如果在这种情况下，活跃会员从旧的交易历史记录选择了删除会员的名，他将收到一条讯息 ，显示该会员已被删除，而非显示个人资料。
如果会员仍然有删除会员在他们的联络名单，他们将获得同样的警告讯息 。
<p>
删除组别只是作为存档功能。如果若干年后，该系统需要清理，系统管理员知道，可以将数据从删除组别中安全地删除（备份）。
<br>
<b>注：</b>有一个例外。会员如果从来不属于一个有账户的组别，可以被永久从系统删除。有一个独立的管理员设置允许这样做。
<br>
<hr>

<a name="broker_groups"></a>
<p class="sub_description">
<h2>标准经纪组别</h2>
经纪是一种 &quot;超级会员&quot;，可以允许执行某些其他组别会员的行政工作。他们可以访问联环系统会员节。
<br>
联环系统有下列标准经纪组别：
<ul>
	<li><a href="#full_brokers"><u>正式经纪</u></a>：正常的、标准的经纪类型。
	<li><a href="#disabled_brokers"><u>禁用经纪</u></a>：临时停用的经纪。
	<li><a href="#removed_brokers"><u>删除经纪</u></a>：永久删除的经纪。
</ul>
</p>
与默认会员组别相似，默认的经纪组别也不是固定或 &quot;硬编码&quot; 的，而是建基于我们认为是很常用的使用组别的
<a href="#permissions"><u>权限</u></a> 和 <a href="#edit_broker_group"><u>设置</u></a>。
也可以修改或创造新的组别，使用不同的配置。
<hr class="help">

<a name="full_brokers"></a>
<h3>正式经纪组别</h3>
经纪是一个有额外功能的会员。一个经纪可以注册其他会员，并根据系统配置，可以有一定权限访问以他作为经纪的会员。
当经纪注册会员后，会员首先需要由系统管理员激活。也可能是经纪可以把会员直接放在某个 &quot;活跃&quot; 组别，但这将需要配置。
经纪也可以收取会员在（交易）活动的 <a href="${pagePrefix}brokering#commission"><u>佣金</u></a>。正式经纪组别已配置佣金，但没有启动。
<hr class="help">

<a name="disabled_brokers"></a>
<h3>禁用经纪组别</h3>
这组别的经纪无法登录，也不能作为一个会员。该账户仍然有效，意味着经纪可以收到付款，并收取缴款（如适用）。
<br>
您可以使用此组别临时停用一个经纪，例如，因为经纪在国外几个月，或等待调查被怀疑滥用或舞弊的经纪。
<hr class="help">

<a name="removed_brokers"></a>
<h3>删除经纪组别</h3>
这个组别与 <a href="#removed_members"><u>删除会员</u></a> 组别是大致相同的。
如果一个经纪已在删除经纪组别，&quot;经纪-会员&quot; 的历史将仍然可供管理员查看。
<br>
请注意，该经纪不能放回另一组别； &quot;删除&quot; 的意思是真正的 &quot;删除&quot;。
<p>
更多的经纪讯息可以在此 <a href="${pagePrefix}brokering"><u>经纪节</u></a> 找到。
<hr class="help">

<a name="admin_groups"></a>
<p class="sub_description">
<h2>标准管理员组别</h2>
管理员组别的用户可以执行软件中的管理任务。他们可以访问联环系统的管理部分。
<span class="admin">
联环系统附有下列标准管理员组别：
<ul>
	<li><a href="#system_admins"><u>系统管理员</u></a>：可以使用任何行政职能。
	<li><a href="#account_admins"><u>账户管理员</u></a>：有关会员的管理。
	<li><a href="#disabled_admins"><u>禁用管理员</u></a>：临时禁用的管理员。
	<li><a href="#removed_admins"><u>删除管理员</u></a>：彻底删除的管理员。
</ul>
</p>
这些组别具有默认 <a href="#permissions"><u>权限</u></a>，也可以修改它们。
</span>
<hr class="help">

<span class="admin">
<a name="system_admins"></a>
<h3>系统管理员组别</h3>
这一组别的用户可以使用任何管理职能，包括建立新的管理员、设置权限和更改系统配置。系统管理员最好是仅用于配置，而不是作平常运作。
<hr class="help">
</span>

<span class="admin">
<a name="account_admins"></a>
<h3>账户管理员组别</h3>
这一组别的用户可以使用任何与会员相关的管理和广告管理。账户管理员不能更改任何系统设置/配置。此外，账户管理员可以访问所有 &quot;浏览&quot; 功能，如系统状态，统计数据等。
可以新增账户管理组别管理具体的会员组别，以 &quot;横向&quot; 分割账户管理。
<hr class="help">
</span>

<span class="admin">
<a name="disabled_admins"></a>
<h3>禁用管理员组别</h3>
在此组别的管理员根本不能做任何事情，甚至不能登录。这组别可用于暂时禁用管理员，而不必删除他。
<hr class="help">
</span>

<span class="admin">
<a name="removed_admins"></a>
<h3>删除管理员组别</h3>
这个组别是最终删除系统内的管理员。请注意，与 <a href="#removed_members"><u>删除会员</u></a> 一样，是不能后退的。
一旦删除，管理员就无法移回。唯一的选择，是从系统和数据库完全删除管理员。
<hr class="help">
</span>

<span class="admin">
<a name="change_group"></a>
<h3>更改组别</h3>
在这里，您可以更改属于会员（或<a href="${pagePrefix}brokering"><u>经纪</u></a>）的组别，只要从下拉框中选择新的组别。
您必须在 &quot;描述&quot; 文本区为更改这组别写评论。您可以点击 &quot;提交&quot; 按钮确认更改。
<p>点击这里可得到 <a href="#member_groups"><u>会员组别</u></a> 的概况。
<p>当您提交更改组别后，它被放在历史中，按年月顺序排列，最新的在顶端。上述的评论历史显示每个评论的状态行，它包含提交更改的管理员名称、更改日期和实际执行的组别更改（&quot;从组别 X 到 Y &quot;）。<br>
这样管理员可以快速地知悉会员账户所发生事情的概况，并理解为什么做了更改。评论可以只是更改原因的短句，任何其它关于客户的额外资料，应在 <a
	href="${pagePrefix}profiles#member_info_actions"><u>备注</u></a> 职能中输入。
<p>一些更改组别的备注：
<ul>
	<li>当会员在 <a href="#inactive_members"><u>不活跃会员</u></a> 组别，您可以选择从系统中完全删除该会员。这可能有用于重复或虚假的注册。
	一旦会员的账户已被激活，它便不能被删除，但您可以把它放在 &quot;被删除会员&quot; 组别。
	<li>当您把一个会员从 <a href="#full_brokers"><u>经纪</u></a>
	移到正常会员（非经纪）组别，属于该经纪的所有会员将没有经纪。（如果您把经纪移动到另一经纪组别，例如：<a href="#disabled_brokers"><u>被禁经纪</u></a>，情况并非如此）。
	如果您不想会员没有任何经纪，最好首先更改所有有关会员的经纪，然后将其原来的经纪移到非经纪组别。这可以通过 <a href="${pagePrefix}user_management#bulk_actions"><u>整批行动</u></a> 职能完成。
</ul>
<hr class="help">
</span>

<span class="admin">
<a name="change_group_admin"></a>
<h3>更改管理员组别</h3>
在这里您可以更改管理员的 <a href="#admin_groups"><u>管理员组别</u></a>。只要从下拉框中选择新的组别。您可以在
&quot;描述&quot; 文本区为更改这组别写评论（例如：为什么）。在您完成输入文本后，不要忘记点击标记为 &quot;更改组别&quot; 的提交按钮。
<p>当您提交更改后，它被放在历史中，按年月顺序排列，最新的在顶端。它包含提交更改的管理员名称、更改日期和实际执行的组别更改（&quot;从组别 X 到 Y&quot;）。
<p>这样管理员可以快速地知悉管理员账户所发生事情的概况，并理解为什么做了更改。评论可以只是更改原因的短句，任何其它关于管理员的额外资料，应在备注职能中输入。
<p>在此视窗也可以从系统中完全地删除管理员，如果这是需要的。但是，建议的方法是把管理员放在 &quot;被禁管理员组别&quot;。
<hr class="help">
</span>

<span class="admin">
<a name="group_management"></a>
<p class="sub_description">
<h2>组别管理</h2>
您可以在联环系统执行各种组别管理的行动。您可以更改组别属性，修改权限，和可以删除或新增权限组别。
</p>
组别管理可以通过 &quot;菜单：用户和组别 > 权限组别&quot; 进入。
<hr class="help">
</span>

<<span class="admin">
<a name="search_groups"></a>
<h3>搜索组别</h3>
您可以通过在 &quot;类型&quot; 下拉框中的其中一个
<a href="#group_categories"><u>类别</u></a> 来搜索；如果系统具有 <a href="#group_filters"><u>组别筛选器</u></a>，它的搜索选项也将会出现。
<hr class="help">
</span>

<span class="admin">
<a name="manage_groups"></a>
<h3>管理权限组别</h3>
有这个视窗，您可以管理各种权限组别。这个视窗提供了可用组别的概况和新增组别的可能性。
<br><br>您可以给每个列出的组别点击以下图标：
<ul>
	<li><img border="0" src="${images}/edit.gif" width="16" height="16">&nbsp;选择修改图标，将开启一个可以设置组别属性的页面。
	<li><img border="0" src="${images}/permissions.gif" width="16" height="16">&nbsp;选择权限图标，将开启一个可以设置权限组别的页面。
	<li><img border="0" src="${images}/permissions_gray.gif" width="16" height="16">&nbsp;如果权限组别图标为灰色，这意味着，该组别没有可以设置的权限，因为它是一个
	&quot;被删除&quot; 组别；放在这组别的会员将被删除，但它们的一些数据（如：交易）将留在系统。
	如需要更多资料，请见 &quot;新&quot; 组别的 <a href="#insert_group"><u>帮助</u></a>。
	<li><img border="0" src="${images}/delete.gif" width="16" height="16">&nbsp;选择删除图标，可以让您删除组别。只有该组别内没有用户才能被删除。
</ul>
您可以点击 &quot;新增组别&quot; 按钮来新增一个组别。我们强烈建议只当您有运作默认组别的经验后，才这样做。
<br>
点击这里可得到
<a href="#group_categories"><u>组别类别</u></a> 的概况。
<hr class="help">
</span>

<span class="admin">
<a name="edit_admin_group"></a>
<h3>修改管理员组别</h3>
在这里您可以修改
<a href="#admin_groups"><u>管理员组别</u></a>
的属性。点击 &quot;更改&quot; 按钮后，您可以更改名称、描述和 &quot;访问设置&quot;。状态只能在新增组别中定义。
<p>请注意，您不能在这里设置权限；组别的属性和设置与权限是不一样的。您可以通过单击 <a href="#manage_groups"><u>组别概况</u></a> 的 <img border="0" src="${images}/permissions.gif" width="16"
	height="16"> 权限图标来修改组别权限，但您也可以使用在视窗下方标记为 &quot;组别权限&quot; 的捷径按钮。<br>
<p>以下的组别设置可用：
<ul>
	<li><b>密码长度：</b> 最小和最大的密码长度。<br>
	<li><b>密码政策：</b> 您有三个选项，这些选项的意思是十分明显的（生日、号码次序等）。<br>
	如果选择了密码策略，用户是不能选择一个在过去曾经使用的密码。
	<li><b>最多密码尝试：</b> 当用户达到最大数量的尝试，用户将无法登录，直到失活时间已经过去（见以下的设置）。<br>
	<li><b>最多密码尝试后之失活时间：</b> 这是当用户已经达到了最多密码尝试后，将无法登录的时间。<br>
	<li><b>登录密码期限：</b> 您可以使用此设置来定义登录密码的有效期限。当期限已到，会员将被迫输入新的密码。如果您在这里输入 &quot;0&quot;，密码将永不过期。<br>
	<li><b>交易密码：</b> 在这里您可以设置使用特殊的密码进行交易。您有下列选项：
	<ul>
		<li><b>&quot;不用&quot;</b>：不使用交易密码。会员可以执行任何交易（当然，如果他们有权限），而不需要首先输入一个交易密码。
		<li><b>&quot;自动&quot;</b>：如果选择此 &quot;自动&quot; 选项，系统会在新增会员账户时（或现在，如果是现有的会员）生成一个交易密码。会员将会在其个人讯息箱收到密码（只有一次）。
		<li><b>&quot;手动&quot;</b>：如果选择这项，交易密码只能从会员个人资料页面的 <a href="${pagePrefix}profiles#access_actions"><u>&quot;管理交易密码&quot;</u></a> 行动中手动地生成。
		更多有关交易密码的资料可在该页面找到。
	</ul>
	<li><b>交易密码长度：</b> 设置交易密码的长度。此密码永远有一个固定长度（当然，如果不使用交易密码，此设置没有任何影响）。
	<li><b>最多交易密码尝试：</b> 达到了这失败尝试的数目后，交易密码会被封锁。
	管理员可以从 <a href="${pagePrefix}profiles#access_actions"><u>&quot;管理交易密码&quot;</u></a> 重设密码（当然，如果不使用交易密码，此设置没有任何影响）。</li>
</ul>
完成修改后，请不要忘记点击 &quot;提交&quot; 按钮。
<hr class="help">
</span>

<span class="admin">
<a name="edit_member_group"></a>
<h3>修改会员组别</h3>
您可以在这里修改
<a href="#member_groups"><u>会员组别</u></a>
的属性。点击 &quot;更改&quot; 按钮后，您可以更改名称、描述和几个类别的设置。
<p>请注意，您不能在这里设置权限，组别的属性和设置与权限是不一样的。您可以通过单击 <a href="#manage_groups"><u>组别概况</u></a> 的 <img border="0" src="${images}/permissions.gif" width="16"
	height="16"> 权限图标来修改组别权限，但您也可以使用在视窗下方标记为 &quot;组别权限&quot; 的捷径按钮。<br>
<p>会员组别设置是按类别次序。以下是可用的类别，您可以单击链接，以得到有关在这些类别中的字段详情：
<ul>
	<li><b><a href="#group_details"><u>组别详细资料</u></a></b> 提供主要摘要。
	<li><b><a href="#group_registration_settings"><u>注册设置</u></a></b> 是定义有关注册会员的组别行为设置，它也包含一些杂项设置。
	<li><b><a href="#group_access_settings"><u>访问设置</u></a></b> 是定义组别访问的设置。
	<li><b><a href="#group_notification_settings"><u>通知设置</u></a></b> 是所有关于通知这些组别的电邮。
	<li><b><a href="#group_ad_settings"><u>广告设置</u></a></b> 是定义有关广告的组别行为设置。
	<li><b><a href="#group_scheduled_payment_settings"><u>预定付款设置</u></a></b> 是关于这组别的
	<a href="${pagePrefix}payments#scheduled"><u>预定付款</u></a> 设置。
	<li><b><a href="#group_loans_settings"><u>贷款小组设置</u></a></b> 是定义有关贷款的组别行为设置。
</ul>
<hr class="help">
</span>

<span class="admin"> <a name="group_details"></a>
<h3>组别详细资料</h3>
在这里，您可以设置一些一般的组别设置。下面的设置有：
<ul>
	<li><b>类型</b> 组别类型（会员、经纪、管理员）。这是在
	 <a href="#insert_group"><u>新增组别</u></a> 时定义和不能更改的。
	<li><b>删除</b> 这字段显示该组别是否 &quot;已删除&quot;。这也是在 <a href="#insert_group"><u>新增组别</u></a> 时定义和不能更改的。
	<li><b>名称</b> 组别的名称。也是在新增组别时定义，但可以更改。
	<li><b>登录网页名称</b> 只有您自定这组别的登录页（在下面自订文件视窗），此选项才将会显示出来。
	您可以访问自定（组别）登录页，通过把登录网页名称放在 &quot;全系统&quot; 的登录页和?字符的后面。登录网页名称不能有空格。一个例子是：<br>
	http://www.yourdomain.org/cyclos?yourgrouploginpagename<br>
	请注意，也可以为每个 <a href="${pagePrefix}groups#group_filter"><u>组别筛选器</u></a> 指定一个登录网页名称。
	<li><b>集装箱网址</b> 如果您想从一个网站内访问联环系统，您可以使用此设置。
	设置的运作与全系统的集装箱网址一样（见 <a href="${pagePrefix}settings#local"><u>设置-本地设置</u></a>），但只是为这一组别。
	在这字段，您将需要放置在开启联环系统的 iframe 或 frame-set 的网页。例如：
	http://www.yourgroupdomain.org/cycloswrapper.php<br>
	请注意，也可以给每个 <a	href="${pagePrefix}groups#group_filter"><u>组别筛选器</u></a> 指定一个登录集装箱网址。
	<li><b>描述</b> 在这里您可以放置组别的描述。字段只能充当组别设置中的额外资料，并不会用于联环系统的其它任何地方。
	<li><b>激活组别</b> 此选项只有在没有账户的组别才会显示。如果在该组别的会员不应被其他会员看得见，您应该让这个选择为空白。<br>
	在某些情况下，即使当会员没有账户，您也想把他们显示给其他用户，这样的话，您应该拣选这个选项。
	例如：只需要做行政工作的经纪（因而不能自己进行贸易），或只给示范用户登录来简介系统提供的内容，而无法进行交易。<br>
	更改此设置将适用于组别的所有现有会员和新会员。
	<p>
</ul>
<hr class="help">
</span>

<span class="admin">
<a name="group_registration_settings"></a>
<h3>组别注册设置</h3>
这些设置定义有关会员注册的组别行为。它是 &quot;<a href="#edit_member_group"><u>修改会员组别</u></a>&quot; 表格的一部分。以下设置可供使用：
<ul>
	<li><b>初始组别</b>：如果您想组别是初始组别，您可以拣选此复选框。该组别的用户是通过自己在公众注册页面注册后，被放置在这里的。在联环系统可以有一个或多个初始组别。<br>
	您也可以指定一个不同的名称来显示组别，只有一个以上的初始组别时这才显示出来，然后注册用户要从下拉框选择您在这字段定义了的组别名称。<br>
	<li><b>电邮验证</b>：当选择此选项，电邮验证是必要的。在提交选项后，用户将收到讯息，指出他将收到一封需要答复的确认电邮，以便处理注册。<br>
	在确认注册之前和最大确认期段尚未过期，会员是在待处理状态。最大确认期段可以在本地设置 <a
		href="${pagePrefix}settings#local"><u>帮助</u></a> > 中的 &quot;限制&quot; 节定义。等待激活会员可以在 <a
		href="${pagePrefix}user_management#search_pending_member"><u>等待激活会员</u></a> 页面查看。<br>
	验证具有以下选项：
	<ul>
		<li><b>不验证：</b> 不验证电邮。会员将直接是初始组别的一部分，该组别是为公众注册而设置的。
（或者，如果是管理员/经纪注册，将是管理员或经纪所选择的组别的一部分）。
		<li><b>只是验证公众注册：</b>只验证在公众注册页面注册的会员。
		<li><b>验证公众和经纪注册：</b>验证在公众注册页面注册的会员及由经纪注册的会员。
		<li><b>验证所有注册（管理员/经纪/公众）：</b>（不解自明）
	</ul>
	<li><b>注册协议：</b> 在这里，您可以选择将显示在注册页面的注册协议。您可以在 <a href="#list_registration_agreements"><u>注册协议</u></a> 页面新增注册协议。<br>
	如果注册协议是以组别定义的，所有该组别的用户需要接受有关的协议，才能够登录到联环系统。如果用户是透过管理员或经纪注册，协议页面将会在他们第一次登录时显示出来。<br>
	如果用户在系统已经注册，并移到一个定义了注册协议的组别，用户在下次登录时将看见该协议（并将需要接受该协议，才能够登录）。<br>
	当更改到一个新增的注册协议，&quot;强逼下次登录时接受&quot; 的额外选项将会出现。
	当拣选此选项时，组别的所有新用户和现有的用户将在下次登录时看见该注册协议；如果不拣选此选项，只有新的注册用户将要接受该协议。<br>
	<li><b>通过电邮发送密码：</b> 当拣选这个选项，用户在注册后将会通过电邮收到密码。如果不选择此选项，用户（和经纪/管理员，这取决于权限）可以在注册表格中定义密码。
	<br>
	讯息可以在 <a href="${pagePrefix}settings#mail"><u>电邮设置</u></a> 定义。如果您使用此选项，请确保电邮字段是必需的。这可在 <a href="${pagePrefix}settings#local"><u>本地设置</u></a> 定义。<br>
	<li><b>会员个人资料图片最多数目：</b> 会员放在他的 <a href="${pagePrefix}profiles"><u>个人资料</u></a> 中的最多图片数目。<br>
	<li><b>会员有效期限</b>：如果您设置此字段的值不是 &quot;0&quot;，在此组别的会员可能会设置为自动到期。自从会员进入组别，过了这段时间后，会员将被自动放置在另一组别（见下项）。<br>
	<li><b>过期后的组别</b>：如果在上一个项目，您为一组别的会员设置了有效期限，在这里您必须输入到期后该会员被移到的组别。
</ul>
<hr class="help">
</span>

<span class="admin"> <a name="list_registration_agreements"></a>
<h3>注册协议</h3>
此视窗显示注册协议的列表。更多有关注册协议的资料可在修改协议的 <a
	href="#registration_agreement"><u>帮助</u></a> 找到。<br>
您可以新增一个协议或修改现有的协议。<br>
如果没有组别拥有这协议，并且没有会员已接受了该协议，协议才能被删除。<br>
<hr class="help">
</span>

<span class="admin"> <a name="registration_agreement"></a>
<h3>新增/修改注册协议</h3>
注册协议是一个可以显示在注册页面的文本。
想要注册的用户必须选择一个复选框，表明他同意该协议，才能够提交。<br>
协议可以连接到一个或多个组别，这可以在 <a href="#group_registration_settings"><u>组别注册设置</u></a> 中配置。
<br>
当您更改了协议，并希望现有的用户（已经接受了旧协议）也再次接受，您必须新增一个协议，
并在组别设置更改它。（您也必须选择 &quot;强逼下次登录时接受&quot; 的选项）
<hr class="help">
</span>

<span class="admin">
<a name="group_access_settings"></a>
<h3>组别访问设置</h3>
这是定义组别的访问行为的设置。这些设置是 &quot;<a href="#edit_member_group"><u>修改会员组别</u></a>&quot; 表格的一部分。以下设置可供使用：
<ul>
	<li><b>可用的渠道：</b> 渠道是访问联环系统的方法。在这里，您可以选择这组别访问联环系统的方法。也可见本帮助的 <a href="${pagePrefix}settings#channels"><u>渠道</u></a> 节。
	可以选择一个或多个以下选项：
	<ul>
		<li><b>主要网络接入：</b> 通过浏览器的正常网页访问联环系统。
		<li><b>销售点网络付款：</b> 销售点访问是给允许通过联环系统付款的商店。如果启用这个选项，这意味着付款人（消费者）组别需要这种访问设置。
		<li><b>短讯：</b> 该模块在发展中。
		<li><b>WAP1 和 WAP2 接入：</b> 通过手机访问联环系统。
		<li><b>网上商店付款：</b> 通过在网上商店的 &quot;联环系统支付&quot; 按钮。
	</ul>
	<li><b>预设渠道：</b> 这组别的每个会员可以设置自己个人偏好的渠道：用户自己可以通过他的个人页面关掉在以前的项目（&quot;可用的渠道&quot;）中选定的所有项目。例如：如果您选择在
	&quot;预设会员访问&quot; 下拉框的 &quot;WAP 1&quot;，这意味着这个组别的所有会员都选择 &quot;WAP 1&quot; 渠道，并在这些会员的个人设置页面预设为选定的渠道。
	然而，在这之后，每个会员可决定把这个设定再次关掉。<br>
	<li><b>个人密码长度：</b> 对于一些付款渠道，如：短讯和网上商店，可以设置使用个人密码（只限数字）。在这选项，您可以定义最大和最小的密码长度。<br>
	请注意，为了显示这选项，使用个人密码的渠道必须在 &quot;可用的渠道&quot; 中选择。当新增第一个具有个人密码的可用渠道，您必须首先储存组别设置，密码长度的选项才会显示出来。<br>
	<li><b>密码长度：</b> 设置用户登录需要的最小和最大的密码长度。<br>
	<li><b>密码政策：</b> 您有三个选项，这些选项的意思是十分明显的。<br>
	<li><b>最多密码尝试：</b> 当用户达到最大数量的尝试，用户将无法登录，直到失活时间已经过去（见以下的设置）。<br>
	<li><b>最多密码尝试后之失活时间：</b> 这是当用户已经达到了最多密码尝试后，将无法登录的时间。<br>
	<li><b>登录密码期限：</b> 您可以使用此设置来定义登录密码的有效期限。当期限已到，会员将被迫输入新的密码。如果您在这里输入 &quot;0&quot;，密码将永不过期。<br>
	<li><b>交易密码：</b> 在这里您可以设置使用一种特殊的密码进行交易。您有下列选项：
	<ul>
		<li><b>&quot;不用&quot;</b>：不使用交易密码。会员可以执行任何交易（当然，如果他们有权限），而不需要首先输入一个交易密码。
		<li><b>&quot;自动&quot;</b>：如果选择此 &quot;自动&quot; 选项，系统会在新增会员账户时（或现在，如果是现有的会员）生成一个交易密码。会员将会在其个人讯息箱收到密码（只有一次）。
		<li><b>&quot;手动&quot;</b>：如果选择这项，交易密码只能从会员个人资料页面的 <a
			href="${pagePrefix}profiles#access_actions"><u>&quot;管理交易密码&quot;</u></a> 行动中手动地生成。更多有关交易密码的资料可在该页面找到。
	</ul>
	<li><b>交易密码长度：</b> 设置交易密码的长度。此密码永远有一个固定长度。<br>
	<li><b>最多交易密码尝试：</b> 达到了这失败尝试的数目后，交易密码会被封锁。管理员可以从 <a href="${pagePrefix}profiles#access_actions"><u>&quot;管理交易密码&quot;</u></a> 的行动中重设密码。
</ul>
<hr class="help">
</span>

<span class="admin"> <a name="group_notification_settings"></a>
<h3>通知设置</h3>
这些设置是有关组别会员可以启用的个人通知。
<ul>
	<li><b>默认用电邮发送讯息：</b> 这是新增会员（这组别）时标记为默认的通知讯息。<br>
	如果您在这下拉框中选择一个项目，这意味着，它将是预设在这组别的每个会员的个人偏好页面的选择。但是，会员自己可以选择不拣选而否决此通知。
	<li><b>允许短讯讯息：</b> 这是将用于短讯通知的讯息。<br>
	注意：此选项只适用于活跃的短讯渠道（设置-渠道）。
	<li><b>默认用短讯发送讯息：</b> 这是新增会员（这组别）时标记为默认的通知讯息。<br>
	如果您在这下拉框中选择一个项目，这意味着，它将是预设在这组别的每个会员的个人偏好页面的选择。但是，会员自己可以选择不拣选而否决此通知。<br>
	<li><b>默认允许短讯收费：</b>同上	
	<li><b>默认接受免费邮件：</b>同上
	<li><b>默认接受付费邮件：</b>同上
	<li><b>短讯收费转账类型：</b> 这是用于发出短讯时收费的交易类型（从系统发送给会员，如：通知）。
	<li><b>短讯收费金额：</b> 这是每个发出短讯将要收取的金额。
</ul>
大部份列出的项目是很明显和不解自明的。这帮助只选出可能需要进一步澄清的项目。<br>
通知帮助将提供更多有关特定选项的资料。
<hr class="help">
</span>

<span class="admin">
<a name="group_ad_settings"></a>
<h3>广告设置</h3>
这些设置定义有关广告的组别行为。它是 &quot;<a href="#edit_member_group"><u>修改会员组别</u></a>&quot; 表格的一部分。以下设置可供使用：
<ul>
	<li><b>每名会员最大的广告数量：</b> 不解自明。<br>
	<li><b>启用永久广告：</b> 当拣选，允许这会员组别可以有 <a href="${pagePrefix}advertisements#ad_modify"><u>永远 &quot;永不过期&quot; 的广告</u></a>。<br>
	<li><b>默认的广告发布时间：</b> 新增广告的默认期段。会员可以随时更改每个广告的激活期段。<br>
	<li><b>广告最长发布时间：</b> 会员可以给广告的最大刊登期限。<br>
	<li><b>广告外部刊登</b>：这允许外部刊登广告，意指这组别的广告可以自动在组织内的网站刊登，从而非会员可以查看。所有选项都是不解自明的。&quot;允许选择&quot;
	是指会员自己可以选择他的广告可否用于外部刊登。<br>
	<li><b>广告图片最多数目：</b> 在一个广告中会员可以放置图片的最多数目。<br>
	<li><b>广告描述最大长度</b>：描述广告中字节/字符的最大长度。
</ul>
<hr class="help">
</span>

<a name="group_scheduled_payment_settings"></a>
<h3>预定付款组别设置</h3>
这些是有关 <a href="${pagePrefix}payments#scheduled"><u>预定付款</u></a> 的设置。这些设置是 &quot;<a href="#edit_member_group"><u>修改会员组别</u></a>&quot; 表格的一部分。<p>
所有这些设置可参考 <a href="${pagePrefix}payments#pay_scheduled"><u>多次预定付款</u></a>。以下设置可供使用：
<ul>
	<li><b>预定付款最多数目：</b> 付款的最多期数（或分期付款），其中付款是可以划分和预定的。例如：10次分期付款，每两个星期一次。
	<li><b>预定时期最大值：</b> 在今天和上一次付款之间的期段最大值。
</ul>
<hr class="help">


<span class="admin">
<a name="group_loans_settings"></a>
<h3>贷款小组设置</h3>
这些是有关 <a href="${pagePrefix}loans"><u>贷款</u></a> 的设置。这些设置是 &quot;<a href="#edit_member_group"><u>修改会员组别</u></a>&quot; 表格的一部分。以下设置可供使用：
<ul>
	<li><b>查看给予贷款小组的贷款</b>：会员可以查看发放给他的 <a
		href="${pagePrefix}loan_groups"><u>贷款小组</u></a> 的 <a
		href="${pagePrefix}loans"><u>贷款</u></a>。
	<li><b>允许贷款小组任何会员还款：</b> 如果拣选此选项，贷款小组的任何会员都可以偿还贷款。如果不拣选该选项，只有标记为负责人的会员才能够偿还贷款。
</ul>
<hr class="help">
</span>

<span class="admin">
<a name="group_brokering_settings"></a>
<h3>经纪组别的经纪设置</h3>
这些是有关 <a href="${pagePrefix}brokering"><u>经纪</u></a> 职能的设置。这些设置是 &quot;<a href="#edit_member_group"><u>修改会员组别</u></a> &quot; 表格的一部分。以下设置可供使用：
<ul>
	<li><b>可能的初始组别：</b> 在这里，您可以选择经纪注册的会员的初始组别，这可以根据使用的经纪而不同。这可能是一个等待激活的不活跃的组别，需要管理员或活跃组别激活。<br>
	如果您选择一个或多个组别，将显示一份具有组别的列表以供经纪选择。<br>
	请注意，如果您在这里不选择一个或多个组别，经纪将无法注册任何会员。
</ul>
<hr class="help">
</span>

<span class="admin">
<a name="edit_broker_group"></a>
<h3>修改经纪组别</h3>
在这里，您可以修改
<a href="#broker_groups"><u>经纪组别</u></a>
的属性。在点击 &quot;更改&quot; 按钮后，您可以更改名称、描述和几个类别的 &quot;设置&quot;。
<br>
<p>请注意，您不能在这里设置权限，组别的属性和设置与权限是不一样的。您可以通过单击 <a href="#manage_groups"><u>组别概况</u></a> 的 <img border="0" src="${images}/permissions.gif" width="16"
	height="16"> 权限图标来修改组别权限，但您也可以使用在视窗下方标记为 &quot;组别权限&quot; 的捷径按钮。<br>
<p>经纪组别设置是按类别次序。以下是可用的类别，您可以单击链接，以得到有关在这些类别中字段的详情：
<ul>
	<li><b><a href="#group_details"><u>组别详细资料</u></a></b> 提供主要摘要。在这里，您可以更改显示的组别 &quot;名称&quot; 和
	&quot;描述&quot;（当然，在您点击此框以下的 &quot;更改&quot; 按钮之后）。
	<li><b><a href="#group_registration_settings"><u>注册设置</u></a></b> 是定义有关注册会员的组别行为设置，它也包含一些杂项设置。
	<li><b><a href="#group_access_settings"><u>访问设置</u></a></b> 是定义组别访问的设置。
	<li><b><a href="#group_notification_settings"><u>通知设置</u></a></b> 是所有关于通知这些组别的电邮。
	<li><b><a href="#group_brokering_settings"><u>经纪设置</u></a></b> 是关于处理其他会员的经纪职能设置。
	<li><b><a href="#group_ad_settings"><u>广告设置</u></a></b> 是定义有关广告的组别行为设置。
	<li><b><a href="#group_loans_settings"><u>贷款小组设置</u></a></b> 是定义有关贷款的组别行为设置。
</ul>
<hr class="help">
</span>

<span class="admin">
<a name="insert_group"></a>
<h3>新增组别</h3>
这个视窗让您可以新增一个组别。
<br>
您有下列选项：
<ul>
	<li><b>类型：</b> 组别的 <a href="#group_categories"><u>类型</u></a>。这可以是 &quot;会员&quot;、&quot;经纪&quot; 或 &quot;管理员&quot;。
	<li><b>删除：</b> 当一组别被标记为 &quot;删除&quot;，这意味着在这组别的会员真的已离开了系统。一旦放置在 &quot;删除&quot; 组别，便不能回到任何其它组别。
	该数据仍会在数据库中及管理员可查看，但它只作为一种备份职能。
	<li><b>名称：</b> 组别的名称，它会显示给用户。
	<li><b>描述：</b> 组别的描述。
	<li><b>复制设置：</b> 您可以通过这个下拉框，复制所有设置从现有的组别到新的组别。设置和权限都将被复制。
</ul>
新增资料后，您当然应该点击 &quot;提交&quot; 按钮来储存更改。
<br>
<b>重要提示：</b>
在新增会员组别后，您应该通过组别列表页面设置权限和属性。
<p>注意：在新增组别后，更改组别的类型和状态是不可能的。
<hr class="help">
</span>

<span class="admin">
<a name="manage_group_accounts"></a>
<h3>管理组别账户</h3>
<a href="#member_groups"><u>会员组别</u></a>（和 <a href="#broker_groups"><u>经纪组别</u></a>）可以有不同的联系 <a href="${pagePrefix}account_management#accounts"><u>账户</u></a>。
下面的列表显示与组别联系的会员账户类型。列表中的账户会显示在该组别会员的个人账户概况。不同的组别可以共同使用一种账户类型，也就是说，不同的组别可以有同样的联系账户类型。
在这种情况下，您仍然可以为每个组别定义不同的账户设置和权限。
<br><br>您可以修改账户设置，请单击 <img border="0"src="${images}/edit.gif" width="16" height="16">&nbsp;修改图标。<br>
<b>注意</b>：要使这账户能够付款和收款，只是新增或修改是不够的；您还需要设置组别的 <a href="#permissions"><u>权限</u></a>，否则账户无法使用。
<br><br>您也可以拆离这组别的账户类型，请单击 <img border="0" src="${images}/delete.gif" width="16" height="16">&nbsp;删除图标。
如果您从组别 &quot;拆离&quot; 账户，这意味着用户仍将可见该账户，并且他们可以看到账户的交易，但该账户将在一个 &quot;不活跃&quot; 状态，它不能用于支付。
<p>您也可以联系新增账户类型到这组别，通过点击此框以下的 &quot;联系新账户&quot; 提交按钮。这将带您到 <a href="#insert_group_account"><u>&quot;新增组别账户&quot;</u></a> 视窗。
<hr class="help">
</span>

<span class="admin">
<a name="edit_group_account"></a>
<a name="insert_group_account"></a>
<h3>修改账户设置/加入组别账户</h3>
在此页面，您可以为选定组别的账户类型定义账户设置。这可以是现有的联系账户类型，或首次要联系此组别的账户类型。
要修改现有的账户，您应该点击 &quot;更改&quot; 以进行修改；当联系新的账户类型，您可以直接输入字段。完成后，点击 &quot;提交&quot; 来储存更改。
<br>
可以定义以下的设置：
<ul>
	<li><b>账户：</b> 要联系的账户类型。一旦新增了账户类型，便不能更改，因此它只适用于新账户类型。您只能联系已新增的账户类型；如果您还没有新增适当的账户，您应该首先这样做，才可以进行联系。
	<li><b>是默认：</b> 因为新增一个以上的会员账户是可能的，系统需要知道其中将被默认的账户。
	这是出于两个原因，第一，系统可以设置发送电邮通知会员关于他们的默认账户的结余。第二，通过手机完成的付款将使用默认账户。
	<li><b>要求交易密码：</b> 如果拣选这个选项，会员将被要求填写交易密码。<br>
	注意：要启用了组别的交易密码（在组别设置），此选项才能启动。否则，它将不可见。
	<li><b>信贷额度下限：</b> 账户的默认信贷额度。金额通常是零或负数。更改它可能会影响现有的账户，这取决于 &quot;更新现有会员信贷额度&quot; 复选框，它置在此页面的下方（见下文）。
	除了整个组别有效的默认信贷额度外，也可以设置个别会员的 <a href="${pagePrefix}account_management#credit_limit"><u>信贷额度</u></a>。个人设置将掩盖组别的信贷额度。
	<li><b>信贷额度上限：</b> 如果定义信贷额度上限，这意味着该账户达到这额度后将不会接收付款。付款人将收到付款失败的讯息。
	<li><b>更新现有会员信贷额度：</b> 如果您更改了现有联系账户类型的信贷额度，您可以在这个选择框定义是否更改现有会员（个人或组别）的信贷额度。
	如果您没有拣选这个选择框，新的信贷额度将只适用于该组别的新会员。如果您首次联系这个账户类型，此框是不可见的。
	<li><b>初始信贷：</b> 这是新会员将自动收到的数额，它可能是零或正数。
	<li><b>初始信贷交易类型：</b> 如果在这上面的修改框中的初始信贷设置不是 0，您必须在这个下拉框中指定这信贷的
	<a href="${pagePrefix}account_management#transaction_types"><u>转账类型</u></a>。由于转账类型有一个来源和目标的账户，它允许您指定取得初始信贷金额的账户。
	默认的数据库系统附有从借记账户到会员账户的 &quot;初始信贷&quot; 转账类型，当然您可以自由地使用其它转账类型。
	<li><b>低单位警示：</b> 如果会员账户达到这数量，将发送个人警示讯息（见下文）。您可以在这里只输入一个正数。它与信贷额度是相关的。
	例如：如果信贷额度是 -200，低单位设置为 10，当低单位的余额达到 -190 单位时，会员将收到警示。如果信贷额度是零，低单位设置为 10，当余额达到 10（正数）单位时，会员将收到低单位警示。
	<li><b>低单位讯息：</b> 当达到低单位数量时，会员收到的（个人）讯息。
</ul>
<hr class="help">
</span>

<span class="admin">
<a name="manage_group_customized_files"></a>
<h3>管理自订文件</h3>
联环系统是可高度定义的系统。您不仅可以定义显示自己的样式和文本（通过
<a href="${pagePrefix}content_management"><u>&quot;内容管理&quot;</u></a>），您也可以在组别层次中指定。
这就意味着，每一个组别可以有它自己特定的样式和文本在软件中显示，这些称为 &quot;自订文件&quot;。
<p>这视窗提供这个组别自订文件的概况。如果还没有定义自订文件，讯息会显示没有任何可用的自订文件。您有下列选项：
<ul>
	<li><b>新增</b> 一个自订文件，请通过 &quot;自订新文件&quot; 按钮。组别自订文件将掩盖系统自订文件（可以通过 <a href="${pagePrefix}content_management"><u>&quot;内容管理&quot;</u></a> 设置）。
	<li><b>修改</b> 现有的自订文件，请通过 <img border="0" src="${images}/edit.gif" width="16" height="16">&nbsp;修改图标。
	<li><b>查看</b> 组别会员将可看到的结果，请通过 <img border="0" src="${images}/view.gif" width="16" height="16">&nbsp;查看图标。
	<li><b>删除</b> 自订文件的定义，请通过 <img border="0"
		src="${images}/delete.gif" width="16" height="16">&nbsp;删除图标。当它被删除后，将使用系统的默认自订文件，如果有的话。
</ul>
<hr class="help">
</span>

<span class="admin">
<a name="customize_group_file"></a>
<h3>为...修改自订文件 / 为...自订新文件</h3>
在此页面，您可以自订静态文件或样式表文件。它的运作与
<a href="${pagePrefix}content_management"><u>内容管理</u></a> 中的相同，但是这次您只是为这一组别而指定它。
<br>
有关如何输入特定指示，请见 <a href="${pagePrefix}content_management#edit_customized_file"><u>这里</u></a>。
<br><br>
<b>注意：</b>当您要在静态文件中包含图片，将需要 <a href="${pagePrefix}content_management#custom_images"><u>上载</u></a> 图片。
</span>

<hr>
<a name="permissions"></a>
<h2>权限</h2>
<p class="sub_description">
在联环系统，权限是按照组别而组织的。您可以管理每一组别访问联环系统软件职能的权限。通常情况下，会员组别将看不见他们无法访问的联环系统部分。
<br>
<span class="admin">
联环系统是非常灵活的。例如：它可以让您新增不同的管理员组别，而每个管理员组别有权限来管理特定的会员组别。较复杂的系统是有可能新增额外的
&quot;垂直&quot; 管理，定义特定的任务范围，如：账户管理员、系统管理员、簿记管理员、代用券/凭证管理员等。
</p>
您可以修改组别权限，通过进入 &quot;菜单：用户和组别 > 权限组别&quot;，然后单击修改权限图标（<img border="0" src="${images}/permissions.gif" width="16" height="16">)。
</span>
<hr class="help">

<a name="manage_group_permissions_basic"></a>
<h3>组别的基本权限</h3>
在此视窗中您可以设置基本权限。基本权限是所有 <a href="#group_categories"><u>组别类型</u></a>（会员、管理员、经纪）相同的权限。
<br>
这些基本权限并不影响其它职能，例如：如果一个会员无法登录，但他仍可以收到付款。
<br>
可以设置下列权限：
<ul>
	<li><b>登录</b>：如果没有拣选此项，该组别的会员无法登录。
	<li><b>邀请讯息</b>：如果拣选此项，该组别的会员在（登录后）主要页面看到一个视窗，让他们能够邀请朋友来 <a href="${pagePrefix}home#home_invite"><u>试用您的软件</u></a>。
</ul>
<hr class="help">

<span class="admin">
<a name="manage_group_permissions_admin_system"></a>
<h3>系统管理权限</h3>
在此视窗中，您可以设置管理员组别的系统职能权限。因此，这权限视窗不包含任何有关会员组别的权限。
为了进行更改，您需要向下滚动至底部，然后选择 &quot;更改&quot; 按钮。点击在页面底部的 &quot;提交&quot; 后，更改便会被储存。
<p>权限的结构是非常简单的。多数职能有两种权限，&quot;查看&quot; 和 &quot;管理&quot;。<br>
如果不选择查看，该项目将不会显示在组别管理员的菜单上。<br>
&quot;管理&quot; 选项提供了新增、修改和删除的权限。<br>
<br>
有下列权限（您可能需要使用链接以取得更多资料）：
<ul>
	<li><b>账户费用：</b> 查看权限允许管理员到 <a href="${pagePrefix}account_management#account_fee_overview"><u>账户费用概况</u></a> 页面。<br>
	 &quot;收费&quot; 权限允许在 <a href="${pagePrefix}account_management#account_fee_history"><u>账户费用历史</u></a> 页面中收取 &quot;手动&quot; 或失败的账户费用。<br>
	<li> <b>账户：</b> 这节可让您设置以下权限：
	<ul>
		<li><b><a href="${pagePrefix}account_management#account_search"><u>管理账户</u></a></b> 权限来新增和修改账户结构。
		<li><b>查看账户管理：</b> 与前一项相同，只是管理员不能进行更改。
		<li><b>查看系统账户资料：</b> 在这里，您可以设置管理员可以查看的系统账户摘要。
		<li><b>查看 <a href="${pagePrefix}payments#authorized"><u>授权付款</u></a></b> 查看已授权付款项目名单。
		<li><b>查看 <a href="${pagePrefix}payments#scheduled"><u>预定付款</u></a></b> 查看预定付款项目名单。
	</ul>
	<li><b>广告类别：</b> 设置 <a href="${pagePrefix}advertisements#categories"><u>广告类别</u></a> 的权限。<br>
	<li><b>管理组别：</b>
	<ul>
		<li>如果拣选 <b>查看</b>，管理员可以在他的 <a href="#manage_groups"><u>组别权限视窗</u></a> 中看到不同的管理组别。
		<li><b>管理自订文件：</b> 允许管理员处理其他管理员的 <a href="#manage_group_customized_files"><u>自订文件</u></a>。
	</ul>
		<li><b>管理任务：</b>允许管理员看到系统状态：
	<ul>
		<li><b>设置系统的可用性：</b>选中时，管理员可以控制
		<a href="${pagePrefix}settings#online_state"><u>系统可用性</u></a>
		<li><b>管理索引：</b>选中时，管理员可以 <a
			href="${pagePrefix}settings#search_indexes"><u>管理索引</u></a>
	</ul>
	<li><b><a href="${pagePrefix}access_devices#list_card_type"><u>卡类型</u></a></b> 
	允许管理员管理和查看卡类型（见链接） 
	<li><b><a href="${pagePrefix}alerts_logs#system_alerts"><u>警示</u></a>：</b> 在特殊情况下系统发出的警告。<br>
	<li><b><a href="${pagePrefix}settings#channels"><u>渠道</u></a>：</b> 定义用户访问联环系统的方法（例如：网络、手机等）。<br>
	<li><b><a href="${pagePrefix}account_management#currencies"><u>货币</u></a>：</b> 允许管理员查看和/或管理在联环系统的不同货币。<br>
	<li><b><a href="${pagePrefix}custom_fields"><u>自定字段</u></a>：</b> 允许管理员管理自定的字段。<br>
	<li><b><a href="${pagePrefix}content_management#custom_images"><u>自定图片</u></a>：</b> 允许管理员管理自定的图片。这会影响下列 &quot;内容管理&quot; 主要菜单中的项目名单的可见性：
	<ul>
		<li>系统图片
		<li>自订图片
		<li>样式表图片
	</ul>
	<li><b><a href="${pagePrefix}alerts_logs#error_log"><u>错误日志</u></a>：</b> 允许管理员查看和管理错误日志。<br>
	<li><b><a href="${pagePrefix}bookkeeping"><u>外部账户（簿记）</u></a>：</b> 管理权限允许管理员配置模块，如：新增和修改外部账户，还有定义字段和交易类型。<br>
	查看权限允许查看外部账户的配置。<br>
	详情权限允许查看付款，但不允许对它们进行任何行动。<br>
	其它权限（处理、核对和管理）是对外付款业务上的权限。<br>
	<li><b><a href="#group_filters"><u>组别筛选器</u></a>：</b> 允许您捆绑一套组别，并给予这套组别一个特定的名称。
	通过这种方式，您可以新增一种 &quot;超级组别&quot;，它可以使用于联环系统内的多个职能。<br>
	当然您可以选择 &quot;管理&quot; 和 &quot;查看&quot; 来设置权限，但您也可以选择 &quot;管理自订文件&quot;，这将允许管理员在组别筛选器（一套组别）中设置自订文件。<br>
	<li><b>担保类型：</b> 在担保系统中，您可以定义不同的 <a href="${pagePrefix}guarantees"><u>担保类型</u></a>。<br>
	<li><b><a href="${pagePrefix}content_management#infotexts"><u>信息文本</u></b></a>。
	允许管理员查看和管理信息文本（见链接）<br>
	<li><b><a href="${pagePrefix}loan_groups"><u>贷款小组</u></a>：</b> 查看及新增贷款组别。贷款小组是一群可以以小额信贷方式收取和偿还贷款的组别会员。<br>
	<li><b><a href="${pagePrefix}member_records"><u>会员记录类型</u></a>：</b> 会员记录让您定义将从会员搜集到的资料。此权限允许管理员新增/定义这样的会员记录类型。<br>
	<li><b>讯息类别：</b> 允许管理员查看和/或管理不同类别的内部 <a href="${pagePrefix}messages"><u>讯息</u></a> 系统。<br>
	<li><b>组别权限：</b> 允许 <a href="#manage_groups"><u>管理</u></a> 不同
	<a href="#group_categories"><u>组别类别</u></a>。<br>
	<li><b><a href="${pagePrefix}groups#list_registration_agreements"><u>注册协议</u></a></b>
	允许管理员查看和管理注册协议。<br>
	<li><b>报表：</b> 每个项目对应一个主要项目单：
	<ul>
		<li><b><a href="${pagePrefix}reports#current_state"><u>状态报表</u></a></b>
	<li><b><a href="${pagePrefix}reports#member_lists"><u>会员列表</u></a></b>
		<li><b>已发送短讯：</b> 在不同的场合，系统可能发送几个短讯，这取决于配置。此权限赋予管理员访问发送短讯概况的报告。
		<li><b><a href="${pagePrefix}statistics"><u>统计</u></a></b> 可以访问统计模块。
	</ul>	
	<br>
	<li><b>设置：</b> 这将让管理员访问设置菜单。
	<ul>
		<li><b><a href="${pagePrefix}settings#local"><u>管理本地设置</u></a></b>
		<li><b><a href="${pagePrefix}settings#alerts"><u>管理警示设置</u></a></b>
		<li><b><a href="${pagePrefix}settings#access"><u>管理访问设置</u></a></b>
		<li><b><a href="${pagePrefix}settings#mail"><u>管理电邮设置</u></a></b>
		<li><b><a href="${pagePrefix}settings#log"><u>管理日志设置</u></a></b>
		<li><b>查看设置</u></a></b> （只能查看设置）
		<li><b><a href="${pagePrefix}settings#import_export"><u>导出/导入设置</u></a></b>
	</ul>
	<li><b>系统付款：</b> 在这里，您可以授权付款从 <a
		href="${pagePrefix}account_management#standard_accounts"><u>系统账户</u></a>
	<ul>
		<li><b>系统付款：</b> 管理员可以执行系统付款属于所选的 <a
			href="${pagePrefix}account_management#transaction_types"><u>转账类型</u></a>.
		<li><b>授权：</b> <a
			href="${pagePrefix}payments#authorized"><u>授权 </u></a>系统付款的权限。
		<li><b>取消：</b> 取消 <a
			href="${pagePrefix}payments#scheduled"><u>预定付款</u></a>的权限
		<li><b>退单付款：</b> 管理员有此权限可以“退单”付款，
既是相反付款相同的金额。如果付款产生其他交易（如费用和贷款），所有交易将退回。
		<br>
		您可以在
		<a href="${pagePrefix}settings#local"><u>本地设置/u></a>
		定义一个交易类型的最大可以退单时间
		<li><b>取消预定付款：</b> （不言自明）
		<li><b>封锁预定付款：</b> （不言自明）
	</ul>
	<li><b>系统状态：</b> 允许管理员看到系统状态：
	<ul>
		<li><b>查看系统状态：</b> 当拣选，管理员将在他的主页看到 <a
			href="${pagePrefix}home#home_status"><u>查看系统状态</u></a> 视窗。
		<li><b>查看在线管理员：</b> 选择管理员可以在 <a
			href="${pagePrefix}user_management#connected_users"><u>在线用户</u></a> 的视窗查看的 <a href="#admin_groups"><u>管理组别</u></a>。
		<li><b>查看在线经纪：</b> 运作相同。
		管理员将只能查看他拥有查看权限的 <a href="#broker_groups"><u>经纪组别</u></a> 的在线经纪。
		<li><b>查看在线会员：</b> 运作相同。有一个所有 <a href="#member_groups"><u>会员组别</u></a> 的复选框。管理员将只能查看他拥有查看权限的在线会员组别。
		<li><b>查看在线操作员：</b> 运作相同。它会显示操作员及其所属的会员。
	</ul>
	<li><b>系统付款：</b> 在这里，您可以为从 <a href="${pagePrefix}account_management#standard_accounts"><u>系统账户</u></a> 付款分配权限。
	<ul>
		<li><b>系统付款：</b> 管理员可以执行属于选定的 <a href="${pagePrefix}account_management#transaction_types"><u>转账类型</u></a> 的系统付款。
		<li><b>授权：</b> <a href="${pagePrefix}payments#authorized"><u>授权</u></a> 系统付款的权限。
		<li><b>取消：</b> 取消 <a href="${pagePrefix}payments#scheduled"><u>预定付款</u></a> 的权限。
		<li><b>退单：</b> 拥有这权限的管理员可以 &quot;退单&quot;，这意味着一个相同金额的相反付款将会完成。假使该付款生成其它交易（如：费用和贷款），所有的交易将被撤销。<br>
		您可以在 <a href="${pagePrefix}settings#local"><u>本地设置</u></a> 定义交易类型的最大退单时限。
	</ul>
	<li><b><a href="${pagePrefix}content_management"><u>系统层面的自定文件</u></a>：</b>
	允许管理员设置系统层面的自定文件（而不是每个组的自定文件）。
	这会影响“内容管理”主要菜单下的以下菜单项：
	<ul>
		<li>静态文件
		<li>帮助文件
		<li>CSS文件
		<li>应用页面
	</ul>
	<li><b>主题：</b> 管理 <a href="${pagePrefix}content_management"><u>内容管理</u></a>
	菜单下的 <a href="${pagePrefix}content_management#themes"><u>主题</u></a>。<br>
	<li><b>翻译：</b> 允许访问 <a href="${pagePrefix}translation"><u>翻译</u></a>
	主菜单，当中可以查看/管理用您的语言做的翻译。<br>
	<li><b><a href="${pagePrefix}settings#web_services_clients"><u>联网服务客户端</u></a>：</b>
	定义外部软件连接联环系统联网服务的访问级别。<br>
</ul>
<hr class="help">
</span>

<span class="admin">
<a name="manage_group_permissions_admin_member"></a>
<h3>会员管理权限</h3>
在此视窗中，您可以为管理员组别的会员职能设置权限。这些权限通常适用于 &quot;账户管理员&quot; 组别。权限的结构很简单，多数职能具有 &quot;查看&quot; 和 &quot;管理&quot; 权限，有时还有额外的特定权限。
<br>
如果没有拣选查看，职能将不会显示在菜单中或作为会员行动的按钮（如果管理员在浏览器的位置上直接输入，权限结构将显示一个 &quot;设有权限&quot; 的页面）。
<br>
&quot;管理&quot; 选项提供了新增、修改和删除的权限。
<p>在第一个选择框（&quot;管理组别：&quot;）中，您可以选择一个或多个会员组别。这意味着这些组别的管理员只能管理该组别的会员，和只能看到有关该些组别的资料。
这意味着，其它会员相关的资料，如：贷款、报表、警示、登录用户等只会显示有关选定组别的结果。<br>
此选项让您允许特定的账户管理员管理特定的会员组别。
<p>可以设置下列权限（您可能需要使用链接以取得更多资料）：
<ul>
	<li><b>访问：</b> 控制会员组别的会员的访问权限。它包含以下分项：
	<ul>
		<li><b>更改登录密码：</b> 允许管理员从会员的个人资料更改会员的登录密码（在会员 &quot;管理密码&quot; 行动）。<br>
		请注意，如果不拣选此权限，管理员仍可以在会员注册时定义一个暂时密码（将强逼会员在首次登录时更改密码）。不过，这只适用于不拣选 <a
			href="#edit_member_group"><u>组别设置</u></a> 的 &quot;通过电邮发送密码&quot;。<br>
		如果拣选组别设置的 &quot;通过电邮发送密码&quot;（在该会员的组别设定），管理员不能定义密码；用户将通过电邮收到一个暂时密码，该密码需要在首次登录时更改。<br>
		如果组别设置的 &quot;通过电邮发送密码&quot; 和 &quot;更改登录密码&quot; 一起被拣选，管理员可以在注册时选择定义一个确定（不是暂时）的密码或强逼用户在首次登录时更改密码。
		<li><b>重置登录密码：</b> 允许管理员重置会员的密码，这通常意味着它会自动再生并通过电邮发送密码（取决于配置）。
		<li><b>管理 <a href="${pagePrefix}passwords#transaction_password"><u>交易密码</u></a>：</b> 允许管理为交易而设置的特别密码。
		<li><b>断开登录会员：</b> 允许管理员立即断开此刻使用该系统的会员。
		<li><b>断开登录操作员：</b> 允许管理员立即断开此刻使用该系统的 <a href="${pagePrefix}operators"><u>操作员</u></a>。		
		<li><b>重新启动会员（因为失败的登录尝试被禁用）：</b> 如果会员忘记了密码，并多次尝试使用不正确的密码登录，他将被暂时禁用。
		如果设置了此权限，管理员可以允许该会员立即再次登入。在这种情况下，在会员个人资料中会显示一个 &quot;允许会员现在登入&quot; 的按钮。
		<li><b>更改个人密码：</b> 允许管理员更改 <a
			href="${pagePrefix}passwords#pin"><u>个人密码</u></a>，这是一个数字密码以访问某些 <a href="${pagePrefix}settings#channels"><u>渠道</u></a>，如：网上商店。
		<li><b>解除封锁个人密码：</b> 当会员超过允许的尝试后，解除封锁了的个人密码。
		<li><b>更改访问渠道：</b> 更改访问渠道的方法，如：联网、通过手机等。
	</ul>
	<li><b>账户：</b> 关于管理员组别管理或查看会员账户的权限。它包含以下分项：
	<ul>
		<li><b>查看资料：</b> 允许管理员查看账户资料（余额、交易概况等）。
		<li><b>查看已授权付款：</b> 允许管理员查看 <a href="${pagePrefix}payments#authorized"><u>授权付款</u></a>。		
		<li><b>查看预定付款：</b> 允许管理员查看 <a href="${pagePrefix}payments#scheduled"><u>预定付款</u></a>。
		<li><b>设置信贷额度：</b> 允许管理员设置个人的信贷额度，这将掩盖为组别设置的信贷额度。
	</ul>
	<li><b><a href="${pagePrefix}reports#member_activities"><u>活动报表</u></a>:</b>
	有关特定会员的活动报表，从会员的个人资料下面的动作按钮访问。各分项是不言自明的。<br>
	<li><b>经纪：</b> 有关 <a href="${pagePrefix}brokering"><u>经纪</u></a> 管理经纪会员的权限。
	<ul>
		<li><b>更改经纪：</b> 允许管理员更改会员的经纪。
		<li><b>查看（经纪的）会员列表：</b> 允许管理员查看经纪的会员。
		<li><b>查看会员列表的贷款细节（打印）：</b> 允许查看在会员列表中通过经纪管理或安排的贷款。
		<li><b>管理 <a href="${pagePrefix}brokering#commission"><u>佣金</u></a>：</b> 管理经纪可能会获得的佣金。
	</ul>
	<li><b>整批行动：</b> 可以使用 <a href="${pagePrefix}user_management#bulk_actions"><u>整批行动</u></a> 对一群会员执行特定的行动。
	<ul>
		<li><b>更改组别：</b> 更改一群会员的组别。
		<li><b>更改经纪：</b> 更改一群会员的经纪。您可能想要这样做，当经纪离开他的经纪工作，而他的会员需要另一个经纪。
		<li><b>生成卡：</b> 这可以产生用户组的卡（根据相应的 <a
		href="${pagePrefix}access_devices#list_card_type"><u>卡类型</u></a>。					
	</ul>
	<li><b>卡：</b> 允许管理员执行操作会员的 <a
		href="${pagePrefix}access_devices#list_card_type"><u>卡</u></a> 。<br>
	<li><b>文件：</b> 允许管理员管理和查看会员的 <a
		href="${pagePrefix}documents"><u>文件</u></a>。可使用下列分项：
	<ul>
		<li><b>查看文件：</b> 在这里，您可以选择管理员可以查看的文件。如果没有文件可用，下拉框将是空白的。
		<li><b>管理 <a href="${pagePrefix}documents"><u>动态</u></a> 文件</b>
		<li><b>管理 <a href="${pagePrefix}documents"><u>静态</u></a> 文件</b>
		<li><b>管理 <a href="${pagePrefix}documents#member_document"><u>会员</u></a> 文件</b>
	</ul>
	<li><b>担保：</b> 允许管理员执行与 <a
		href="${pagePrefix}guarantees#guarantees_search"><u>担保</u></a>相关的操作。<br>
	<li><b><a href="${pagePrefix}loan_groups"><u>贷款小组会籍</u></a></b> 在这里，您可以定义管理员是否可以在贷款组别中新增和删除会员，或只限查看该贷款小组。<br>
	<li><b><a href="${pagePrefix}loans"><u>贷款</u></a>：</b> 允许访问会员贷款的各种设置。有以下分项：
	<ul>
		<li><b>查看会员贷款：</b> 查看正常的会员贷款。
		<li><b>查看已授权贷款：</b> 对于有些贷款，特别授权是必要的。
		<li><b>发放贷款：</b> 您可以使用下拉框来指定允许管理员发放给用户的不同类型的贷款。
		<li><b>在过去的日期发放贷款：</b> 在特殊情况下，贷款日期不是现时的日期。
		<li><b><a href="${pagePrefix}loans"><u>舍弃贷款</u></a>：</b> 允许通过把贷款的总余额变为零来 &quot;取消&quot; 贷款。
		<li><b>偿还贷款：</b> 允许管理员代表会员偿还贷款。
		<li><b>在过去的日期偿还贷款：</b> 与前一项目相同，但允许管理员设置还款日期为过去的日期。
		<li><b>管理过期贷款状态：</b> 让您给予过期贷款 <a href="${pagePrefix}loans#status"><u>额外状态/标记</u></a>。
	</ul>
	<li><b>会员组别：</b>
	<ul>
		<li><b>查看：</b> 如果已设置这项，管理员可以查看会员组别的 <a href="#manage_groups"><u>概况</u></a>。
		<li><b>管理账户设置：</b> 允许管理员 <a
			href="#manage_group_accounts"><u>管理组别账户设置</u></a>。如果没有设置这项，管理员可以查看设置（取决于先前的权限项目），但不可以更改它们。
		<li><b>管理自订文件：</b> 允许管理员管理这组别的 <a href="${pagePrefix}content_management#customized_files"><u>自订文件</u></a>。
	</ul>
	<li><b>会员发票：</b> 允许管理员访问会员 <a
		href="${pagePrefix}invoices"><u>发票</u></a> 的各种设置。所有这些项目都是不解自明的，所以我们将不会在这里提供分项说明。<br>
	<li><b>会员付款：</b> 这是有关 <a
		href="${pagePrefix}payments"><u>付款</u></a> 的一组权限。有以下分项：
	<ul>
		<li><b>系统付款给会员：</b> 在这里选择管理员可以从系统账户付款给会员的系统付款。
		<li><b>在过去的日期付款给会员：</b> 允许管理员执行系统付款给会员，但 &quot;预先日期&quot; 意指可以设置过去的日期为付款日期。
		<li><b>代表会员付款给会员：</b> 在这里选择的付款类型，管理员可以执行付款给另一名会员，犹如他是会员。
		<li><b>代表会员自我付款：</b> 允许管理员从会员的一个账户付款给相同会员的另一个账户，犹如他是会员。
		<li><b>代表会员付款给系统：</b> 在这里选择的付款类型，允许管理员从会员账户付款给系统账户，犹如他是会员。请选择允许的付款类别。
		<li><b><a href="${pagePrefix}payments#authorized"><u>授权付款</u></a></b> 是需要特别授权的付款。在这里，您设置管理员可以授权付款，犹如他是会员。
		<li><b>代表会员取消已授权付款</b>
		<li><b>代表会员取消 <a href="${pagePrefix}payments#scheduled"><u>预定付款</u></a></b><br>
		为了使此设置有效，交易类型的配置也必须允许取消预定付款。
		<li><b>代表会员封锁预定付款：</b> 允许您封锁预定付款。封锁和取消的差别是，已封锁的预定付款是可以解除封锁的，而取消是不可挽回的。<br>
		为了使此设置有效，交易类型的配置也必须允许封锁预定付款。
		<li><b><a href="${pagePrefix}payments#charge_back"><u>退单</u></a>：</b> 允许管理员为会员撤消付款。
	</ul>
	<li><b>讯息：</b> 允许管理员访问联环系统的 <a href="${pagePrefix}messages"><u>讯息</u></a> 系统。
	<ul>
		<li><b>查看：</b> 您可以在下拉框选择允许管理员查看讯息的类型。您可以通过 <a href="${pagePrefix}messages#categories"><u>讯息类别</u></a> 来新增讯息类型。
		<li><b>发送给会员：</b> 允许管理员发送讯息给个别会员。
		<li><b>发送给组别：</b> 允许管理员发送讯息给组别的每个会员。
		<li><b>管理：</b> 允许管理讯息。例如：允许搜索旧的讯息，和新增讯息类别。
	</ul>
	<li><b><a href="${pagePrefix}member_records"><u>会员记录</u></a>：</b>
	允许管理员管理会员记录－这让您定义将搜集到的会员资料。分项目是不解自明的。<br>
	<li><b>会员：</b> 管理员可以为会员做的一些杂项的权限。分项目：
	<ul>
		<li><b>注册：</b> 如果选择这项，管理员可以从用户的搜索页面注册新会员。
		<li><b>管理待审批会员：</b> 管理员可以查看已注册但没有（从邮件）验证登记的会员。<br>
		更多信息可见于 <a
			href="${pagePrefix}user_management#search_pending_member"><u>帮助文件</u></a>。
		<li><b>修改资料：</b> 如果选择这项，管理员可以在会员的 <a href="${pagePrefix}profiles"><u>个人资料</u></a> 字段进行更改。
		<li><b>修改登录名：</b>（不解自明）
		<li><b>修改会员名称：</b>（不解自明）
		<li><b>永久删除：</b> 管理员可以从数据库中永久删除会员。这只限于该会员从来不属于一个曾经拥有账户的组别（激活后，会员永远可以放在 <a
			href="#removed_members"><u>被删除会员</u></a> 组别）。			
		<li><b>更改组别：</b> 允许管理员更改会员的组别。
		<li><b>导入：</b> 允许管理员导入会员列表到联环系统（通常从其它系统迁移）。<br>更多资料可在 <a href="${pagePrefix}user_management#import_members"><u>帮助</u></a> 中找到。
	</ul>
	<li><b>讯息：</b> 允许管理员访问联环系统的 <a href="${pagePrefix}messages"><u>讯息</u></a> 系统。
	<ul>
		<li><b>查看：</b> 您可以在下拉框选择允许管理员查看讯息的类型。您可以通过 <a href="${pagePrefix}messages#categories"><u>讯息类别</u></a> 来新增讯息类型。
		<li><b>发送给会员：</b> 允许管理员发送讯息给个别会员。
		<li><b>发送给组别：</b> 允许管理员发送讯息给组别的每个会员。
		<li><b>管理：</b> 允许管理讯息。例如：允许搜索旧的讯息，和新增讯息类别。
	</ul>
	<li><b>销售终端：</b> 允许管理员管理会员的 <a href="${pagePrefix}access_devices#search_pos"><u>销售终端设备</u></a>。
	<br>
	<li><b>偏好设定：</b> 允许管理员管理用户的 <a href="${pagePrefix}preferences"><u>偏好设定</u></a>.
	<br>	
	<li><b>产品和服务：</b> 这些权限允许管理此组别的管理员查看和/或管理会员的广告。<br>
	还有允许 <a href="${pagePrefix}advertisements#import_ads"><u>导入广告</u></a> 的权限。<br>
	<li><b><a href="${pagePrefix}references"><u>评语</u></a>：</b> 允许管理员管理或查看评语－系统的会员可以把好或不好的评语分配给对方。<br>
	<li><b><a href="${pagePrefix}reports#sms_log"><u>短讯日志</u></a>：</b>
	提供访问为这会员发送的短讯日志。系统可以配置在多个情况下发送短讯。<br>
	<br>
	<li><b><a href="${pagePrefix}reports#sms_mailings"><u>短讯邮件</u></a>：</b> 
	给一名或一组会员发送短讯（邮件）的权限。<br>
	<li><b><a href="${pagePrefix}transaction_feedback"><u>交易反馈意见</u></a>：</b> 这是其他会员对特定交易的反馈意见。此权限允许管理员查看或/和修改交易反馈意见。<br>
	交易反馈意见是在 <a href="${pagePrefix}account_management#transaction_type_details"><u>交易类型</u></a> 配置中启用的。<br>
</ul>	
</ul>
<hr class="help">
</span>

<span class="admin">
<a name="manage_group_permissions_admin_admin"></a>
<h3>管理员管理权限</h3>
在此视窗中，您可以为管理员组别的管理员职能设置权限。这意味着，您可以定义管理员在系统中的访问级别：指定的管理员组别会员将收到您所选的权限。
通常的做法是有限数目（如果不是1个）的管理员具有这种权限，因为这些是最高级别的权限。
<br>
权限的结构很简单。多数职能有 &quot;查看&quot; 和 &quot;管理&quot; 权限，有时还有额外的特定权限。
<br>
如果没有拣选查看，职能将不会显示在菜单中或作为会员行动的按钮（如果管理员在浏览器的位置上直接输入，权限结构将显示一个 &quot;没有权限&quot; 的页面）。
<br>
&quot;管理&quot; 选项提供了新增、修改和删除的权限。
<p>如果职能具有特定的权限（管理和查看之外），该权限将有一个名称以表明权限的类型（例如：断开登录会员）。<br>
<br>
以下是可用的权限，我们只提及可能需要解释的项目：
<ul>
	<li><b>访问：</b>
	<ul>
		<li><b>更改登录密码：</b>（不解自明）
		<li><b>更改交易密码：</b>（不解自明）
		<li><b>断开：</b> 在点击断开按钮那个时刻，允许从系统断开另一个管理员。
		<li><b>重新启动管理员（因为失败的登录尝试被禁用）：</b>	如果管理员因为忘记了密码而被禁用，您可以允许他再次登录。
	</ul>
	<li><b>管理员记录：</b> 如 <a href="${pagePrefix}member_records"><u>会员记录</u></a>，但允许您储存管理员的资料。分项目是简明的。<br>
	<li><b>管理员：</b> 这是有关其他管理员的权限，如：注册他们和把他们放在另一组别。管理职能比会员职能少。管理员没有账户，但只能有一定的级别访问系统账户。<br>
	这些项目是不解自明的。
</ul>
<hr class="help">
</span>

<span class="admin">
<a name="manage_group_permissions_member"></a>
<h3>会员组别的会员权限</h3>
属于这个组别的会员将获得以下权限：
<ul>
	<li><b>访问：</b> 当会员超过允许的尝试后，解除封锁了的个人密码。<br><br>
	<li><b>账户</b>：会员可以随时访问自己的账户，所以没有查看/管理自己账户的权限。在本节中，您只能设置会员是否可以查看：
	<ul>
		<li><b><a href="${pagePrefix}payments#authorized"><u>授权付款</u></a></b>
		<li><b><a href="${pagePrefix}payments#scheduled"><u>预定付款</u></a></b>
	</ul>
	<li><b>广告（产品和服务）：</b><br>
	<ul>
		<li><b>查看：</b> 如果在 &quot;查看&quot; 下拉框中没有选择组别，广告职能将不适用于这个会员组别，并且不会出现在搜索菜单上或在个人资料下方的 <a
			href="${pagePrefix}profiles#actions_for_member"><u>&quot;会员行动&quot;</u></a> 页面。<br>
		如果选择一个或多个组别，<a href="${pagePrefix}advertisements#advertisement_search"><u>&quot;搜索产品及服务&quot;</u></a> 职能将只显示选定组别会员的结果。<br>
		通常您会选择所有组别。如果您有组别需要在同一个系统内完全独立运作，您可以通过选择特定的组别来限制可见性。例如：有一个商业组别，而您不想显示这组别的消费广告。
		<li><b>发布：</b> 如果拣选 &quot;发布&quot;，会员可以刊登广告，项目单 &quot;个人－广告&quot; 将会显示在会员菜单。<br>
	</ul>
	<li><b>卡：</b> 在这里，您可以定义会员自己处理 <a
	href="${pagePrefix}access_devices#search_cards"><u>卡</u></a> 的权限。<br>
	<li><b>文件：</b> 您可以使用此选项决定将显示在会员菜单 &quot;个人－文件&quot; 的 <a href="${pagePrefix}documents"><u>文件</u></a>。如果没有拣选文件，将不会显示（这个会员组别的）项目单。<br>
	<li><b>担保：</b> 这是 <a href="${pagePrefix}guarantees"><u></u></a> 联环系统的一部份，一笔支持款项担保联环系统的每个账户余额。您可以选择下列权限：
	<ul>
		<li><b>管理担保（作为发行人）：</b> 可以创建和编辑担保。
		<li><b>发行认证：</b> 可以发出认证给选择的团体。
		<li><b>以付款义务购买的组别：</b> 在这里，您可以选择会员可以以 <a
			href="${pagePrefix}guarantees"><u>付款义务</u></a> 购买的组别。
		<li><b>以付款义务出售的组别：</b> 可以接受付款义务的组别
	</ul>
	<li><b>发票：</b> 在这节中，您可以定义会员是否可以发送 <a href="${pagePrefix}invoices"><u>发票</u></a> ，
	无论是从用户的个人资料页面，或直接从 &quot;账户&quot; 菜单发送。
		<ul>
			<li><b>查看：</b> 当选择时，会员可以查看发票的菜单项。
			如果未使用发票，此选项通常是不选择的。
			<li><b>发送给会员：</b> 选择它会允许会员发送发票给其他会员。
			<li><b>发送给系统：</b> 选择它会允许会员发送发票给系统账户。
		</ul>
	<li><b>贷款：</b> 在这节中，您可以定义会员 <a href="${pagePrefix}loans"><u>贷款</u></a> 的权限。
	<ul>
		<li><b>查看：</b> 如果拣选 &quot;查看&quot; 选项，组别的会员可以查看其贷款。如果没有拣选查看，项目单不会显示。
		<li><b>偿还：</b> 选择此项允许会员执行偿还贷款。
	</ul>
	<li><b>会员资料：</b> 
	<ul>
		<li><b>查看：</b> 在这里，您可以指定将显示这个会员组在“搜索会员”的会员。
		通常会拣选所有的组别（删除或禁用的组别除外）。
		如果您想有组别在系统内独立工作，您才希望允许查看特定的组别。
		例如消费者和商业团体不能看到对方。如果您使用特定的组别查看，
		您将需要在广告的权限中设置相同的权限。
		<li><b>更改自己的登录名：</b> 当选择时，本组会员可以在个人资料改变自己的登录名。
		<li><b>更改自己的用户名：</b>  当选择时，本组会员可以在个人资料改变自己的用户名。
	</ul>
	<li><b>会员报告：</b> 如果选择“查看”，本组会员可以查看其他会员的
	<a href="${pagePrefix}reports#member_reports"><u>报告页</u></a>。
	如果您在“显示账户信息”选择一个账户类型，会员也可以在这些报告中查看其他会员的信息（账户余额）。
	<li><b>讯息：</b> 在这节中，您可以定义会员可使用联环系统的
	<a href="${pagePrefix}messages"><u>讯息</u></a> 系统的程度。
	<ul>
		<li><b>查看：</b>允许查看讯息
		<li><b>发送给会员：</b>允许发送讯息给其他会员。
		<li><b>发送给管理员：</b>允许发送讯息给管理员。
		<li><b>管理：</b>有了这个权限，会员可以执行查看讯息 以外的其他行动，
		如，删除、标记为未读等。
	</ul>
	注意：要给管理员显示讯息，管理员组别也需要有权限。
	管理员可以定义 <a href="${pagePrefix}messages#categories"><u>讯息 类别</u></a>。 
		这些将显示在会员给管理员的信息，作为强制性的选项。<br>
	<li><b>操作员：</b> 在这里，您可以定义会员是否可以使用联环系统的 <a
		href="${pagePrefix}operators"><u>操作员</u></a> 系统，它让您定义类似一个账户的分会员。这只有一个复选框来开启或关闭。<br>
	<li><b>付款：</b> 在这里，您可以指定允许这会员组别的 <a
		href="${pagePrefix}account_management#transaction_type_details"><u>付款方式</u></a>。
		您多数只会选择一种或几种方式。可用的方式取决于您定义了多少种付款方式。<br>
	<ul>
		<li><b>自我付款：</b> 如果选择这个选项，会员可以在自己的账户之间进行付款。在下拉框，您可以指定可能的交易类型。此选项只适用于拥有一个以上会员账户的组别。
		<li><b>付款给会员：</b> 在这里，您可以选择会员付款给另一会员时可以使用的付款方式。
		<li><b>付款给系统：</b> 在这里，您可以指定会员可以选择付款给系统账户的付款方式。如果无选择，项目单 &quot;付款给系统&quot; 不会显示。
		<li><b>生成对外付款票据：</b><br> 这是有关会员商店所使用的票据系统。主要使用者是网上商店，它们想利用联环系统作为一种对外付款的系统。
		如果拣选此选项，会员（商店）允许生成票据。票据系统对于用户是透明的。它提供了一个结构，当中网络商店可以监定和验证消费者和付款数据，但没有访问消费者的登录详情。
		有关技术细节，可以在联环系统的wiki网页（联网服务-票据）找到。
		<li><b>接受付款者授权：</b> 允许会员 <a	href="${pagePrefix}payments#authorized"><u>授权</u></a> 付款，如果他是收款人。
		这可以作为接受付款者，并可选接受付款者授权后由付款人授权。<br>
		授权的配置，可见于 <a
			href="${pagePrefix}account_management#edit_authorization_level"><u>交易类型</u></a>。<br>
		注意：此选项的运作方式与发票类似，因此，不应该和发票联合起来。		
		<li><b>取消等待批准的付款：</b> 当使用 <a href="${pagePrefix}payments#authorized"><u>授权付款</u></a>，这将允许会员取消他们新增了的、但尚未授权的授权付款。
		<li><b>取消预定付款：</b> 当使用 <a href="${pagePrefix}payments#scheduled"><u>预定付款</u></a>，这将允许会员在计划的日期开始之前取消他们的预定付款。
		<li><b>封锁预定付款：</b> 允许会员暂时封锁他们的预定付款。
		<li><b>请求从其它渠道付款：</b> 当拣选这个选项，会员可以从其它的 <a href="${pagePrefix}settings#channels"><u>渠道</u></a> 发送 &quot;<a
		href="${pagePrefix}payments#request"><u>付款请求</u></a>&quot;（发票）。您可以从下拉框中选择这些渠道。目前，只有短讯可用，但在未来可能增加其它渠道。
		<li><b>收到付款后退单：</b> 有了这个权限，用户可退单（取消）付款。见 <a
			href="${pagePrefix}settings#local_chargeback"><u>本地设置</u></a> > 退单。 
	</ul>
	<li><b>偏好：</b> 这允许该组别的会员访问他们在主要项目单的偏好。在这菜单只有两个项目，因此有这些权限：
	<ul>
		<li><b>管理通知</b> 允许会员管理他们的电邮 <a href="${pagePrefix}preferences"><u>通知</u></a>。
		<li><b>管理广告兴趣<</b> 允许会员管理他们的 <a href="${pagePrefix}ads_interest"><u>广告兴趣</u></a>。
		<li><b>管理收据打印机设置：</b> 允许会员查看和管理
		<a href="${pagePrefix}preferences#receipt_printers"><u>收据打印机</u></a>.
	</ul>
	<li><b>评语：</b> 这允许会员查看 <a href="${pagePrefix}references"><u>评语</u></a>，或把它们提供给其他会员。
	如果您根本不想使用评语职能（一个或多个组别），您可让 &quot;查看&quot; 选项空白。在这种情况下，评语菜单和其它评语按钮将不会显示。<br>
	<li><b><a href="${pagePrefix}reports#sms_log"><u>短讯日志</u></a>：</b> 给予会员访问发送给他的短讯日志。系统可以配置在多个情况下发送短讯。<br>
</ul>
<hr class="help">
</span>

<span class="admin">
<a name="manage_group_permissions_broker"></a>
<h3>经纪权限</h3>
这是关于经纪可以执行的 <a href="${pagePrefix}brokering"><u>经纪</u></a>
职能的典型权限。这意味着您可以定义这组别经纪可以处理有关其会员事项的职能类型。权限的结构很简单。多数职能有 &quot;查看&quot; 和
&quot;管理&quot; 权限，有时还有额外的特定权限。
<br>
如果没有拣选查看，职能将不会显示在经纪的菜单中或作为会员行动的按钮（如果经纪在浏览器的位置上直接输入，权限结构将显示一个 &quot;没有权限&quot; 的页面）。
<br>
&quot;管理&quot; 选项提供了新增、修改和删除的权限。
<br>
<br>
您可以允许经纪可以为其经纪会员访问下列职能：
<ul>
	<li><b>访问：</b> 这允许经纪控制会员的访问。它包含以下分项：
	<ul>
		<li><b>更改登录密码：</b> 允许经纪从会员个人资料更改会员的登录密码（在会员 &quot;管理密码&quot; 行动）。<br>
		请注意，如果不拣选此权限，经纪仍然可以在会员注册时定义一个暂时密码（将强逼会员在首次登录时更改密码）。不过，这只是在不拣选 <a
			href="#edit_member_group"><u>组别设置</u></a> 的 &quot;通过电邮发送密码&quot; 情况下。<br>
		如果拣选组别设置的 &quot;通过电邮发送密码&quot;（在该会员的组别设定），经纪不能定义密码；会员将通过电邮收到一个暂时密码，该密码需要在首次登录时更改。<br>
		如果组别设置的 &quot;通过电邮发送密码&quot; 和 &quot;更改登录密码&quot; 一起被拣选，经纪可以在注册时选择定义一个确定（不是暂时）的密码或强逼用户在首次登录时更改密码。
		<li><b>重置登录密码：</b> 允许经纪重置会员的登录密码，这通常意味着它会自动再生并通过电邮发送（取决于配置）。
		<li><b>管理 <a href="${pagePrefix}passwords#transaction_password"><u>交易密码</u></a>：</b> 允许管理为交易而设置的特别密码。
		<li><b>更改个人密码：</b> 允许经纪更改 <a
			href="${pagePrefix}passwords#pin"><u>个人密码</u></a>，这是一个数字密码以访问某些 <a href="${pagePrefix}settings#channels"><u>渠道</u></a>，如：网上商店。
		<li><b>解除封锁个人密码：</b> 当会员超过允许的尝试后，解除封锁了的个人密码。<br>
		<li><b>更改访问渠道：</b> 更改访问 <a href="${pagePrefix}settings#channels"><u>渠道</u></a> 的方法，如：互联网、通过手机等。
	</ul>
	<li><b>账户：</b>
	<ul>
		<li><b>查看账户资料：</b> 经纪可以查看其经纪会员的账户资料。
		<li><b>查看 <a href="${pagePrefix}payments#authorized"><u>已授权付款</u></a></b> 如果拣选这个选项，允许查看关于他的经纪会员的已授权付款。
		<li><b>查看 <a href="${pagePrefix}payments#scheduled"><u>预定付款</u></a></b>
	</ul>
	<li><b>经纪：</b>
	<ul>
		<li><b>注册：</b> 允许经纪注册一个新的会员。
		<li><b>管理待激活会员：</b> 经纪可以查看已注册但没有（从邮件）验证登记的会员。<br>
		更多信息可见于 <a
			href="${pagePrefix}user_management#search_pending_member"><u>帮助文件</u></a>。
		<li><b>修改资料：</b> 允许经纪修改代理的会员的 <a
			href="${pagePrefix}profiles"><u>个人资料</u></a>。
		<li><b>更改全名：</b> 允许经纪修改代理的会员的全名。
		<li><b>更改登录名：</b> 允许经纪修改代理的会员的登录名。
		<li><b>管理默认佣金：</b> 这允许经纪自己修改默认的 <a
			href="${pagePrefix}brokering#commission"><u>佣金</u></a> 设置。
		<li><b>管理合同：</b> 如果选择此选项，经纪可以管理会员的有佣金的 <a
		href="${pagePrefix}brokering#commission_contract"><u>合同</u></a>。
		这可以通过在
		<a href="${pagePrefix}profiles"><u>个人资料</u></a> 下面的按钮。
	</ul>
	<li><b><a href="${pagePrefix}access_devices#search_cards">卡：</a></b> 
	给经纪权限对其会员的卡执行行动。<br>
	<li><b><a href="${pagePrefix}documents">文件：</a></b>
	<ul>	
		<li><b>查看：</b> 在这里，您可以选择经纪可以查看的系统文件。
		<li><b>查看会员个人文件：</b> 允许经纪查看会员的 <u><a href="${pagePrefix}documents#member_document">个人文件</a></u>。
		<li><b>管理会员个人文件：</b> 与前一项相同，但指的是管理权。
	</ul>
	<li><b>发票：</b> 在这里的项目的重要性是很明显的，而且是不解自明的。<br>
	<li><b><a href="${pagePrefix}loan_groups"><u>贷款小组</u></a></b><br>
	查看贷款小组项目单。<br>
	<li><b><a href="${pagePrefix}loans"><u>贷款</u></a></b><br>
	查看贷款项目单。<br>
	<li><b>会员付款：</b> 控制经纪可以访问的会员付款项目。<br>
	分项目是：
	<ul>
		<li><b>代表会员付款给会员：</b> 在这里选择付款类型，允许经纪执行付款给另一名会员，犹如他是会员。
		<li><b>代表会员自我付款：</b> 允许经纪从会员的一个账户付款给同一个会员的另一个账户，犹如他是会员。
		<li><b>代表会员付款给系统：</b> 在这里选择付款类型，允许经纪从会员账户付款给系统账户，犹如他是会员。
		<li><b>授权：</b> 经纪可以授权会员支付 <a href="${pagePrefix}payments#authorized"><u>授权付款</u></a> 系统。
		<li><b>代表会员取消已授权付款</b>
		<li><b>代表会员取消 <a href="${pagePrefix}payments#scheduled"><u>预定付款</u></a></b>。<br>
		为了使此设置有效，交易类型的配置也必须允许取消预定付款。
		<li><b>代表会员封锁预定付款：</b> 暂时封锁预定付款。封锁和取消的差别是，已封锁的预定付款是可以解除封锁的，而取消是不可挽回的。<br>
		为了使此设置有效，交易类型的配置也必须允许封锁预定付款。
	</ul>
	<li><b><a href="${pagePrefix}member_records"><u>会员记录</u></a>：</b>
	允许管理员管理会员记录－这让您定义将搜集到的会员资料。分项目是不解自明的。<br>
	<li><b><a href="${pagePrefix}access_devices#pos">销售终端（设备）：</a></b>
	给经纪权限以管理其会员的销售终端设备。
	<li><b>个人讯息：</b> 在这里有一个项目，允许经纪发送个人 <a href="${pagePrefix}messages"><u>讯息</u></a> 给其所有的经纪会员。<br>
	<br>
	<li><b>偏好设定：</b> 允许经纪设置所有其代理的会员的个人
	<a href="${pagePrefix}preferences"><u>偏好</u></a>。
	<br>
	<li><b>产品和服务（广告）：</b> 允许经纪查看或管理其代理的会员的广告。
	<li><b>评语：</b> 允许经纪管理（发出、删除、修改）会员的 <a href="${pagePrefix}references"><u>评语</u></a>。<br>
	<br>
	<li><b><a href="${pagePrefix}reports#member_activities">报表：</a></b>
	允许经纪查看其会员的报告，以及可选地查看其账户信息。
	<li><b><a href="${pagePrefix}reports#sms_log"><u>短讯日志</u></a>：</b>
	允许经纪访问为其会员发送的短讯日志。系统可以配置在多个情况下发送短讯。<br>
	<li><b><a href="${pagePrefix}reports#sms_mailings"><u>短讯邮件：</u></a></b>
	允许经纪发送短讯邮件给其会员。
</ul>
<hr class="help">
</span>

<a name="group_filters"></a>
<p class="sub_description">
<h2>组别筛选器</h2>
组别筛选器是某种 &quot;超级组别&quot;：一群组别捆绑在一起，并给予一个名称，便可以执行某些行动。因此，简言之，组别筛选器是一个 &quot;组别的组别&quot;。
<br>
组别筛选器在联环系统中的数个任务是很有用的。您可以在组别筛选器执行统计计算，而会员和管理员可以用组别筛选器进行搜索。
</p>
<i>在哪里可以找到它？</i><br>
组别筛选器可通过 &quot;菜单：用户和组别 > 组别筛选器&quot; 找到。
<hr>

<span class="admin">
<a name="group_filter"></a>
<h3>修改/新增组别筛选器</h3>
在此视窗可以定义或修改 <a href="#group_filters"><u>组别筛选器</u></a>。如果您修改现有的组别，在您可以更改表格中的字段之前，您应首先点击标记为 &quot;更改&quot; 的按钮。
<br>
完成后，不要忘记点击 &quot;提交&quot; 和储存更改。
<ul>
	<li><b>名称：</b> >选择您想要的名称给新组别筛选器。
	<li><b>登录网页名称：</b> 只有您自定这组别的登录页（在自订文件视窗下面），此选项才将会显示出来。您可以访问自定（组别）登录页，通过把登录网页名称放在 &quot;全系统&quot; 的登录页和?字符的后面。
	登录网页名称不能有空格。一个例子是：<br>
	http://www.yourdomain.org/cyclos?yourgrouploginpagename<br>
	请注意，也可以为每个 <a	href="${pagePrefix}groups#group_details"><u>组别</u></a> 指定一个登录网页名称。
	<li><b>集装箱网址</b> 如果您想从一个网站内访问联环系统，请使用此设置。
	设置的运作与全系统集装箱网址一样（见 <a href="${pagePrefix}settings#local"><u>设置-本地设置</u></a>），但只是为这一这组别筛选器。
	在这字段，您将需要放置在开启联环系统的 iframe 或 frame-set 的网页。例如：
	http://www.yourgroupdomain.org/cycloswrapper.php<br>
	请注意，也可以给每个 <a	href="${pagePrefix}groups#group_details"><u>组别</u></a> 指定一个登录集装箱网址。
	<li><b>描述：</b> 给管理员的描述，使他清楚组别筛选器的使用。
	<li><b>在个人资料显示：</b> 当选择这项时，组别筛选器将显示在会员 <a href="${pagePrefix}profiles"><u>个人资料</u></a> 的组别字段中。
	<li><b>组别：</b> 是这表格最重要的部分。在这里，选择您想在组别筛选器中的组别。
	<li><b>谁可查看：</b> 让您选择将能够看到组别筛选器的组别。在这种情况下，组别筛选器将显示在会员节的会员和广告搜索。
	请注意，如果您选择了 &quot;在个人资料显示&quot; 的复选框，将永远在个人资料中可以看见，与您在 &quot;谁可查看&quot; 中所选择的组别无关。因此，此设置只影响搜索职能。
</ul>
<hr class="help">
</span>

<span class="admin">
<a name="manage_group_filters"></a>
<h3>管理组别筛选器</h3>
这个视窗显示 <a href="#group_filters"><u>组别筛选器</u></a> 列表，并提供管理它们的选项。
<ul>
	<li><b>修改</b>（或查看）现有的组别筛选器，通过 <img border="0" src="${images}/edit.gif" width="16" height="16">&nbsp;修改图标。
	<li><b>删除</b> 组别筛选器，通过 <img border="0" src="${images}/delete.gif" width="16" height="16">&nbsp;删除图标。
	<li><b>新增</b> 一个组别筛选器，点击标记为 &quot;新增组别筛选器&quot; 的提交按钮。
</ul>
<hr class="help">
</span>

<span class="admin">
<a name="manage_group_filter_customized_files"></a>
<h3>（为组别筛选器）自订文件</h3>
您可以使用这个职能为 <a href="#group_filters"><u>组别筛选器</u></a> 设置 <a href="${pagePrefix}content_management"><u>自订文件</u></a>。
这意味着每个组别筛选器可以有自己的自订，如：铺排（颜色、样式）、标识图标和页面，如：新闻、联系、手册等。如果没有自订的特定组别或组别筛选器，主要的铺排和页面将会出现。
<br>
自订组别会掩盖自订组别筛选器，因此，如果您定义这两个，系统首先会核对这个组别是否有自订文件，并显示它。否则，它会核对组别筛选器是否有自订文件，并显示它。
如果情况也不是这样，它将会显示全系统的自订文件，如果有的话。
<br>
在表格中的成分：
<ul>
	<li><b>查看</b> 取得的结果摸様，通过 <img border="0" src="${images}/view.gif" width="16" height="16">&nbsp;查看图标。
	<li><b>修改</b> 现有的自订文件，通过 <img border="0" src="${images}/edit.gif" width="16" height="16">&nbsp;修改图标。
	<li><b>删除</b> 自订文件的定义，通过 <img border="0" src="${images}/delete.gif" width="16" height="16">&nbsp;删除图标。
	<li><b>新增</b> 一个自订文件，通过标记为 &quot;自订新文件&quot; 的按钮。
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