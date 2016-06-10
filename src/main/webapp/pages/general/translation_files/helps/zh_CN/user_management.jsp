<div style="page-break-after: always;">
<span class="admin">
<p class="head_description">
这些是用户管理功能网页。它们可以用来寻找会员、注册新会员、查看当前登录的用户概况，或看到其他有关会员的状态。</p>

<i>在哪里可以找到它？</i><br> 
下面列出了可用的功能，并在那里可以找到：
<ul>
	<li><b>寻找和新增会员：</b> 可以通过 &quot;菜单：用户和组别 > 管理会员&quot;。这包括寻找经纪。
	<li><b>寻找和新增管理员：</b> 可以通过 &quot;菜单：用户和组别 > 管理管理员&quot;。
	<li><b><a href="#connected"><u>在线用户</u></a>：</b> 可以通过 &quot;菜单：用户和组别 > 在线用户&quot;。
	<li><b><a href="#bulk_actions"><u>整批行动：</u></a></b> 可以通过 &quot;菜单：用户和组别 > 整批行动&quot;。
</ul>
</span>

<span class="broker">
<ul>
	<li><b>新增/注册会员：</b> 只要您有这个权限，可以通过 &quot;菜单：经纪 > 注册会员&quot;。
</ul>
</span>

<span class="member">
<ul>
	<li><b>搜索会员：</b> 可以通过 &quot;菜单：搜索 > 会员&quot;。
</ul>
</span>
<hr>

<span class="admin">
<A NAME="create_user"></A>
<h3>新增用户</h3>
在这里，您可以输入一个新会员的信息。视窗非常简单；系统配置决定显示什么字段。<br>
会员将属于所列出第一个字段的 <a href="${pagePrefix}groups"><u>组别</u></a>。
<br><br>如果您正在新增的会员的组别已存在有权限的经纪组别，您可以（可选择）从经纪组别指定一个经纪给该会员。为此，您应该拣选底部的标示为 &quot;指定经纪&quot; 的复选框。<br>
您可以指定用户的密码，并指定用户可以直接使用密码或在下次登录时改变它。
<br><br>在填写数据后，您可以选择储存会员和输入新的会员（按钮 &quot;储存并新增会员&quot;），或直接开启新会员的 <a href="${pagePrefix}profiles"><u>个人资料</u></a>（按钮 &quot;储存和开启个人资料&quot;）。
<hr class="help">
</span>

<span class="broker">
<a name="create_user_for_broker"></a>
<h3>经纪—新增会员</h3>
在这里，您可以输入一个新会员的信息。视窗非常简单；系统配置决定显示什么字段。<br>
您完成了会员注册后，您将自动成为会员的经纪。<br>
您可以指定用户的密码，并指定用户可以直接使用密码或在下次登录时改变它。
<br><br>在填写数据后，您可以选择储存会员和输入新的会员（按钮 &quot;储存并新增会员&quot;），或直接开启新会员的 <a href="${pagePrefix}profiles"><u>个人资料</u></a>（按钮 &quot;储存和开启个人资料&quot;）。
<br><br>
注册一个新会员后，您可能会收到一个自动 <a href="${pagePrefix}brokering#commission"><u>佣金</u></a>，根据系统的规则。佣金取决于注册会员的贸易额。
<br>
通过 &quot;菜单：经纪 > 会员&quot;，您可以管理员您注册的会员，并跟进他们的行动。
<hr class="help">
</span>


<span class="member">
<A NAME="search_member_by_member"></A>
<h3>搜索会员</h3>
在此网页上您可以搜索会员。搜索会员会搜索所有会员的个人资料字段。您可以使用多个关键字的搜寻。<br>
各种 &quot;操作符&quot; 可用于会员（和广告）搜索。最常用的是：
<ul>
	<li><b>*</b> 星号 &quot;通配符&quot; 可让您搜寻一部分的词语。例如搜索 巴* 将返回所有用户中有相符的个人资料，例如巴芭拉、巴特、巴克街（后者将是一个地址字段）
	<li><b>- not</b> 搜索的关键字之前，直接有一个减号或 &quot;not&quot; 和空间，将返回的结果不包含该关键字。
	<li><b>or</b> 搜索返回的结果会包含在 &quot;or&quot; 的前面或后面的关键字。
	<li><b>and</b> 搜索返回的结果会包含在 &quot;and&quot; 的前面和后面的关键字。
</ul>

<hr class="help">
</span>

<span class="member"> 
<A NAME="search_member_result"></A>
<h3>搜索结果（搜索会员）</h3>
此视窗显示搜索会员的结果。选择 &quot;登录名&quot; 或 &quot;会员名称&quot;，将开启会员的 <a href="${pagePrefix}profiles"><u>个人资料</u></a>。
选择图片图标，将在弹出视窗开启图像。
<hr class="help">
</span>

<span class="admin"> 
<A NAME="search_member_by_admin"></A>
<h3>搜索会员</h3>
在搜索会员网页（菜单：用户和组别 > 管理会员），您可以搜索会员和新增注册会员。
<br><br>如果您想寻找一个会员，您可以填写各种搜索字段（但没有一个是强制性的）。如果您只点击 &quot;搜索&quot;
按钮，没有输入任何字段，您将会得到所有会员的列表。<br>
<ul>
	<li><b>组别筛选器：</b> 在这里，您可以指定一个 <a href="${pagePrefix}groups#group_filters"><u>组别筛选器</u></a>。
	<li><b><a href="${pagePrefix}groups"><u>组别权限</u></a></b>
	<li><b>经纪登录/经纪：</b> 在此处输入经纪的登录名或真实姓名，结果页会显示属于这个经纪的会员。
	<li><b>激活从/到：</b> 有了这些日期字段，您可以搜索某人成为会员的日期。您可以点击日历图标（<img border="0" src="${images}/calendar.gif" width="16" height="16">），使用日期拣选工具。
</ul>
为了注册一个新会员，您将使用底部左侧的下拉框。在这里，您选择新增会员的组别，您将被带到 <a href="#create_user"><u>注册表</u></a>。
<hr class="help">
</span>
 
<span class="admin">
<A NAME="admin_search_member_result"></A>
<h3>搜索结果（搜索会员）</h3>
此视窗显示搜索会员的结果。选择登录名或会员的名称，将开启会员的 <a href="${pagePrefix}profiles"><u>个人资料</u></a>。选择个人资料页的后退按钮将跳回到搜索结果。
<br><br>您可以选择打印搜索结果：点击在右上角视窗帮助图标旁边的打印图标（<img border="0" src="${images}/print.gif" width="16" height="16">）。
这会将您带到一个友好的打印机视窗，在这里您可以按一下按钮，打印概况。
<br><br>另一种可能性是下载结果文件：点击储存图标（<img border="0" src="${images}/save.gif" width="16" height="16">）。
结果将下载为 CSV 格式，其中将包含所有存在于会员 <a
	href="${pagePrefix}profiles"><u>个人资料</u></a> 的字段（包括许多在此结果视窗不可见的字段）。<br>
在 <a href="${pagePrefix}settings#local"><u>本地设置</u></a>，您可以指定是否在标题行（第一行）显示字段名称。
<br><br>注：在报告功能，您能够检索更具体的 <a href="${pagePrefix}reports#member_lists"><u>会员名单</u></a>，例如会员名单包含更多的数据，如账户余额和广告数目。
<hr class="help">
</span>

<span class="broker admin"> <a name="search_pending_member"></a>
<h3>搜索待审批会员</h3>
待审批会员，是已注册但尚未答复证实注册电邮的会员。只有在确认后，会员才可以登录。<br>
在等待某一特定时间后，待审批会员将被自动从系统（和名单）删除。
所有三个注册办法（在公众注册页面自我注册、由经纪注册和会员注册）都可以要求电邮确认。<br>
注：会员不能注册已存在于系统中的电邮（无论是其他待审批或活跃的会员）。
<br><br>
在此网页中，您可以搜索待审批会员。您可以按姓名、组别和注册日期范围搜索。<br>
该组别的搜索并不意味着会员已属于该组别，但显示在电邮验证后该会员将参加的组别。<br>
</span>
<span class="admin"> 您也可以用经纪搜索。这意味着搜索只会显示在选定经纪注册的会员。请注意，这只适合由经纪注册的会员是需要确认的。
这是可选的 <a href="${pagePrefix}groups#group_registration_settings"><u>组别设置</u></a>。最大注册时间是在本地设置内。<br>
<hr class="help">
</span>

<span class="broker admin"> <a name="search_pending_member_result"></a>
<h3>待审批会员列表</h3>
此视窗显示搜索会员的结果。必须认识到，这些会员不是 &quot;活跃&quot; 的会员（他们不能登录和不可见到系统）。只有在极少数情况下，您将要删除会员。
删除待审批会员意味着会员将无法确认他的注册。<br>
如果要修改，您可以查看和修改会员的个人资料数据，并在必要时再发送验证电邮。
<hr class="help">
</span>

<span class="broker admin"> <a name="pending_member"></a>
<h3>待审批会员详细资料</h3>
在这个网页上，您可以查看和修改会员的个人资料数据，并在必要时再发送验证电邮。
<hr class="help">
</span>

<span class="admin">
<a name="search_admin"></a>
<h3>搜索管理员</h3>
在搜寻管理员网页（菜单：用户和组别 > 管理管理员），您可以寻找管理员和注册新的管理员。
<br><br>形式非常简单：您可以指定一个关键字，和/或搜索某个管理员组别。如果您只点击 &quot;搜索&quot; 按钮，没有输入任何字段，您会得到一份所有管理员的名单。<br>
<br><br>为了注册一个新的管理员，您必须使用表格底部的下拉框。在这里，您选择新的管理员的 <a
	href="${pagePrefix}groups#admin_groups"><u>组别</u></a>，您将被带到新管理员的 <a href="#create_user"><u>注册表</u></a>。
</span>

<span class="admin">
<a name="search_admin_result"></a>
<h3>搜索结果（管理员搜索）</h3>
此框显示搜索管理员的结果。您可以按一下管理员的登录名或真实姓名，获得有关此管理员的资料。
<hr class="help">
</span>

<span class="admin">
<a name="create_admin"></a>
<h3>注册新的管理员</h3>
在这里，您可以注册一个新的管理员。我们<b>强烈</b>建议为所有管理员提供他们自己的账户和登录名，因此不同的人不会共享一个管理员账户。这会更易于维护权限，跟踪可能出现的错误，并在有人离开组织时关闭有关账户。
<br>表格不言自明。任何有红星（*）的字段是强制性的。
<br><br>在输入数据后，您可以选择储存管理员和输入一个新的管理员（按钮 &quot;储存并新增管理员&quot;）或直接开启新管理员的 <a href="${pagePrefix}profiles"><u>个人资料</u></a>（按钮 &quot;储存并开启个人资料&quot;）。
<hr class="help">
</span>

<span class="admin">
<hr>
<a name="connected"></a>
<p class="sub_description">
<h2>在线用户</h2>
在线用户页面（菜单：用户和组别 > 在线用户）可以显示目前登录并使用系统的用户（会员，管理员，经纪）概况。
</p>
</span>

<span class="admin">
<A NAME="connected_users"></A>
<h3>在线用户</h3>
在这个视窗，您可以指定在下面的视窗可以看到哪些 <a href="${pagePrefix}groups#group_categories"><u>类型</u></a> 的在线用户。
&quot;显示&quot; 下拉框让您可以选择 &quot;管理员&quot;、&quot;经纪&quot;、&quot;会员&quot; 和 &quot;<a href="${pagePrefix}operators"><u>操作员</u></a>&quot;。<br>
点击 &quot;提交&quot; 以显示结果。
<hr class="help">
</span>
 
<A NAME="connected_users_result"></A>
<span class="admin">
<h3>在线用户搜索结果</h3>
此功能会显示在线用户的名单，根据您在上述视窗的选择。<br>
在列表中您可以点击会员，开启他的个人资料。有权限的管理员可以点击最后一栏的删除图标（<img border="0"
	src="${images}/delete.gif" width="16" height="16">）以断开会员。
</span>
<span class="member">
<h3>在线操作员搜索结果</h3>
此功能会显示在线 <a href="${pagePrefix}operators"><u>操作员</u></a> 的名单。<br>
在列表中您可以点击操作员，开启他的个人资料。如果您有权限，可以点击最后一栏的删除图标（<img border="0" src="${images}/delete.gif" width="16" height="16">）以断开操作员。</span>
<hr class="help">

<span class="admin">
<hr>
<a name="bulk_actions"></a>
<p class="sub_description">
<h2>整批行动</h2>
整批行动功能（菜单：用户和组别 > 整批行动）容许管理员执行操作整个组别的用户。
</p>
</span>

<span class="admin">
<A NAME="bulk_actions_filter"></A>
<h3>整批行动筛选器</h3>
此视窗容许管理员指定 <a href="#bulk_actions"><u>整批行动</u></a> 的用户群。字段相结合为一个 &quot;逻辑和&quot; 的搜索，所以您不应指定太多，避免空的结果。
<ul>
	<li><b>组别：</b> 在这里您指定 <a href="${pagePrefix}groups#group_filters"><u>组别筛选器</u></a>。
	<li><b>权限组别：</b> 在这里您指定 <a href="${pagePrefix}groups"><u>组别</u></a>。留意它不会与上述指定组别筛选器冲突。通常您会指定组别或组别筛选器，但不会两者同时使用。
	<li><b>经纪登录名/经纪：</b> 如果您想将要采取的行动对是一个经纪的所有会员，可在这里指定经纪的真实姓名或登录名。
	<li><b>...：</b> 本表格的其余部分列出一些 <a href="${pagePrefix}custom_fields"><u>自定字段</u></a> 定义的会员资料。
</ul>
一旦您指定您的整批行动的标准，您可以查看它包括的会员，通过点击右下角的 &quot;预览&quot; 按钮。这将提交一份包括在内的会员名单。
<hr class="help">
</span>

<span class="admin"> <A NAME="bulk_action"></A>
<h3>行动</h3>
在这里可指定要进行的 <a href="#bulk_actions"><u>整批行动</u></a>。您有以下可能性：
<ul>
	<li><b>改变组别：</b> 这将改变选定组别的权限组别。<br>
	一旦您选择它，您将被要求输入新的 <a href="${pagePrefix}groups"><u>权限组别</u></a>，并输入评论。在点击 &quot;提交&quot; 后，所有有关会员将迁往新的组别。
	<li><b>改变经纪：</b> 这将改变选择组别的 <a href="${pagePrefix}brokering"><u>经纪</u></a>。<br>
	一旦您选择它，您将被要求输入新经纪的登入名称或真实姓名（如果您输入一个，另一个将自动完成）。<br>
	拣选 &quot;暂停佣金&quot; 复选框，将导致丢弃所有正在运行的和开启的 <a href="${pagePrefix}brokering#commission"><u>佣金支付</u></a>。
	您可能希望使用此复选框，如果您认为新的经纪没有权利得到之前经纪应得的佣金支付。<br>
	您也应该输入一个评论。点击 &quot;提交&quot; 按钮后，所有参与的会员将分配新经纪。
	<li><b>生成卡：</b> 此选项将生成会员卡。为了生成会员卡，一个 <a
		href="${pagePrefix}access_devices#edit_card_type"><u>卡类型</u></a>
	必须存在，会员必须属于一个组别，而组别在
	<a href="${pagePrefix}groups#edit_member_group"><u>组别设置</u></a>
	视窗的“访问设置”已选择这个卡类型。<br>
	您可以通过标记一个或两个选择框，为已拥有卡的会员“再创建”卡类型。
	您可以选择为已有卡在等待和/或活跃状态的会员再创建卡。
	在这两种情况下，现有的卡将被取消。
</ul>
<hr class="help">

<A NAME="import_members"></A>
<h3>导入会员</h3>
这个功能可以导入会员（的个人资料），可选地也可以存入或从会员账户撤回初始信用额度。
初始信用额度可以选择账户类型，然后选择一个正余额和负余额的付款方式。
如果在CSV文件中的金额是正的，在“正结余的付款类型”选择的付款类型（交易类型）将用于从系统转账到会员账户。
当它是负数，在“负结余的付款类型”选择的付款类型（交易类型）将用于从会员账户转账到系统账户。
<br><br>
CSV文件是用于导入会员：
<ul>
    <li>文件的第一行需要包含所有要导入的字段名称。请参阅下面列出所有可能的导入字段。
    这些栏名不分大小写，可以任何顺序。
    <li>其他行包含所有用户的信息，每一行代表一个用户。
    <li>所有行都需要有相同的字段数量（相同的逗号数量），个别字段可以留空。
</ul>
请确保导入的字段中的所有值符合在联环系统的验证标准。
例如自定字段可能需要是唯一的，或者需要有由系统管理员设置一定数量的字符。
数字和日期应根据本地设置的定义格式化。
<br><br>
可以导入以下字段：
<ul>
	<li><b>name</b>（必填）：会员姓名。
	<li><b>username</b>（必填）：这是登录名，必须是唯一的，否则会给予错误。
	如果登录名是自动生成的（账户编码），没有必要这栏，联环系统将生成登录名。
	<li><b>creationdate</b>（可选）：如果拣选它，个人会员的报告的日期（注册日期）将会用此日期。
	如果没有日期，将会用现在的（导入）日期。
	<li><b>password</b>（可选）：纯文字会员密码。会员在第一次登入时必须改变它的。
	<li><b>email</b>（根据联环系统设置决定是必填或可选的）：它必须是一个良好的电邮地址。
	<li><b>balance</b>（可选）：最初的账户余额。只在已拣选账户类型时才用。
	如果您选择账户类型，您可以指定付款类型（负余额是会员付给系统，正余额是系统付给会员）。<br>
	<i>如果您使用此选项，您必须确保将被扣除的账户有足够的信贷。</i></li>
	<li><b>creditlimit</b>（可选）：账户的（负）信贷额度上限。如果是空，将采取账户组别设置的默认值。
	<li><b>uppercreditlimit</b>（可选）：账户的信贷额度上限。如果是空，将采取账户组别设置的默认值。
	<li><b>自定字段的内部名称</b>（可选）：有关选定组别的自定字段的内部名称。将会执行必需的验证。
	如果字段是一个名单（列举值），只有在会员已有现存的数值时，才能成功导入。
	例如，如果您有一个自定字段“地区”，有三种可能的地区“中区”、“南区” 和“北区”，其他地区将不得导入。
	空白地区的会员将被导入（除非设置为“必填”）。
	<li><b>会员记录类型.自定字段的内部名称</b>（可选）：会员记录的数值。
	默认数据库的一个例子是“Remark.comments”；意见是备注记录类型的一个字段，这个字段必须是（在字段配置指定的）内部名称。<br>
	注意，如果您要导入一个记录类型字段，所有定义给这个记录类型的字段，必需在CSV文件（作为一个栏）列明。
</ul>
下面是CSV文件的一个例子（这里只导入必填的字段和密码）​​：<br>
<i>
name,username,email,password <br>
Paul McCartney,Paul,Paul@McCartney.com,1234 <br>
John Lennon,John,John@Lennon.com,1234 <br>
</i><br>
下面是CSV文件的另一个例子（这里很多字段都是导入的，包括自定字段 area 和记录类型 remark 的自定字段 comments）：<br>
<i>
name,username,email,password,creationdate,balance,creditlimit,uppercreditlimit,area,remark.comments <br>
Paul McCartney,Paul,Paul@McCartney.com,1234,25/08/2000,500,200,,Example area,calls a lot <br>
John Lennon,John,John@Lennon.com,1234,01/01/2001,1000,,,Example area,good artist <br>
</i>
<hr class="help">


<A NAME="imported_members_summary"></A>
<h3>导入会员摘要</h3>
这页概述了导入的会员。在这个阶段尚未有任何处理。您可以点击（数目）链接，显示成功或不成功导入的会员状态（或点击 &quot;会员总数&quot; 后面的数目，显示两者）。<br>
如果您点击 &quot;导入&quot;，将导入成功的会员。但先查看导入会员的状态，仍是好的。
<br>
如果拣选​​“发送激活电邮”选项而目标组别是一个积极的组别，会员将收到激活邮件。
<hr class="help">


<A NAME="imported_member_details"></A>
<h3>导入会员搜索<</h3>
在此视窗中您可以搜索在导入名单中的具体会员。您可以通过行数或会员名搜索。会员名搜索选项将搜索登录名和会员名称。<br>
<hr class="help">


<A NAME="imported_member_details_result"></A>
<h3>导入会员搜索结果</h3>
该视窗将显示导入的结果。在错误导入的情况下，它会通知错误类型（例如缺漏字段、名称已在使用中）；在成功导入的情况下，它会显示信贷额度（下限）和账户余额。<br>
要处理会员，您可以点击 &quot;返回&quot; 和 &quot;导入&quot; 按钮。<br>
如果您想发送激活电邮，您要选择
“发送激活电子邮件”选项（请确保在
<a href="${pagePrefix}groups#group_settings"><u>组别设置</u></a>
拣选这个选项）
</span>

<span class="member">
<hr>
<p><A NAME="contacts"></A>
<h3>联系人</h3>
<p class="head_description">这些画面让您管理您的联系人。</p>

在联系人名单（菜单：个人 > 联系人）您可以用鼠标选择下列各种行动：
<ul>
	<li><b>登录名—会员名称：</b> 开启会员的个人资料页。
	<li><b>电邮：</b> 发送电邮给此会员。
	<li><b><img border="0" src="${images}/edit.gif" width="16" height="16">&nbsp;</b>
	查看/新增/编辑会员详细资料。
	<li><b><img border="0" src="${images}/edit_gray.gif" width="16"
		height="16"></b> 如果图标没有颜色，这意味着字段不包含任何信息。
	<li><b><img border="0" src="${images}/delete.gif" width="16"
		height="16"></b> 从您的联系人名单删除该会员。
</ul>
开始时，您在这个名单上没有任何联系人。您可以有两种方式新增联系人：
<ul>
	<li>使用 &quot;<a href="#add_contact"><u>新增联系人</u></a> &quot;
	视窗如下。
	<li>首先执行 <a href="${pagePrefix}user_management#search_member_by_member"><u>搜索会员</u></a>（&quot;菜单：搜索&quot;）。
	在搜索结果名单中，您可以点击会员的名字，输入会员的 <a href="${pagePrefix}profiles"><u>个人资料</u></a>。在个人资料，您应该点击 &quot;新增为联系人&quot;，以增加会员到您的联系人名单中。
</ul>
<hr class="help">

<p><A NAME="add_contact"></A>
<h3>新增联系人</h3>
在这里您可以新增联系人到您的联系人名单。为此，您可以通过键入登录名或会员名称字段（自动完成），然后点击 &quot;提交&quot;。
<hr class="help">

<p><A NAME="contact_note"></A>
<h3>联系人注释</h3>
在这个网页上，您可以新增用户的其他信息。此信息只有您能看见；如果您从您的联系人名单删除会员，它也将被删除。
<hr class="help">

<a name="contact_us"></a>
<h3>联系我们</h3>
如果您有任何问题关于本套软件，此网页包含它的联系地址。
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
