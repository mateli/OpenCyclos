<div style="page-break-after: always;">
<a name="top"></a>
<p class="head_description">
联环系统可以让您透过 &quot;发票&quot; &quot;发送帐单&quot; 给某人。发票是正式的请求以支付已提供的货物或服务。<br>
在会员和管理之间新增和管理发票，有许多特色。还有，会员可以互相发送发票。<br>
收件人可以拒绝或接受发票。发件人也可以取消发票。另一方将永远收到有关这些行动的通知。但是会员不能拒绝来自系统账户的发票。</p>
<p>
<i>在哪里可以找到它？</i><br> 
您可以使用以下方法访问发票：
<ul>
	<span class="admin">
		<li><b>菜单：账户 > 会员发票：</b> 让您从组织（从系统账户）发送发票给会员。</li>
		<li><b>菜单：账户 > 管理发票：</b> 让您管理系统账户收到和发出的发票。</li>
	</span>
	<span class="admin broker">
		<li><b>会员 <a href="${pagePrefix}profiles"><u>个人资料</u></a> > 账户：查看发票</b> 让您查看特定会员的发票。
		</li>
	</span>
	<span class="member">
		<li><b>菜单：账户> 系统发票：</b> 让您发送发票给组织。
		</li>
		<li><b>菜单：账户 > 会员发票：</b> 让您发送发票给另一会员。</li>
		<li><b>菜单：账户 > 发票：</b> 让您查看和管理所有您收到和发出的发票。
		</li>
		<li><b>会员 <a href="${pagePrefix}profiles"><u>个人资料</u></a> > 发送发票：</b> 让您直接从会员的个人资料发送发票。
		</li>
	</span>
</ul>
<hr>

<span class="member">
<A NAME="send_invoice_member_to_system"></A>
<h3>会员发送发票给系统</h3>
会员发送 <a href="#top"><u>发票</u></a> 给系统（组织）是可能的。组织管理员将收到发票，并将会接受或拒绝它。当管理员接受或拒绝发票，您将在个人新闻讯息部份收到关于这行动的通知。<br>
要发送发票，请填写字段，然后点击 &quot;提交&quot; 按钮。
<hr class="help">
</span>

<A NAME="send_invoice_system_to_member"></A>
<A NAME="send_invoice_member_to_member"></A>
<A NAME="send_invoice_member_to_member_select"></A>
<h3>发送发票给会员</h3>
在这里，您可以发送发票给会员。该会员将接到 &quot;收到发票&quot;，当点击 &quot;接受&quot; 后，便可以支付给您。
如果发票已被接受，它就像任何其它付款，成为正常的付款，并会显示在交易历史中。
<br>
<span class="member"> 另一方面，会员也可以选择拒绝您的发票，这意味着取消付款。发件人将得到该发票已被拒绝的通知。
<br><br>在 &quot;菜单：账户 > 发票&quot;，您可以查看和管理收到和发出的发票。
</span>
<span class="admin">所有系统发出给会员和会员发出给系统的发票概况可以在菜单：&quot;账户 > 管理发票&quot; 节中看到。</span>
<p>您将要输入以下字段：
<ul>
	<li><b>（用户）名：</b> 您可以在字段中填写 &quot;名称&quot; 或 &quot;用户名&quot; 来选择应该收到发票的会员。
	如果您输入名称的一部分，它将会自动完成（此字段将不可见，如果它已经清楚知道您要发送发票的目标会员）。
	<li><b>金额：</b> 如果您的系统有多种货币，在这里，您必须在金额后面的下拉框选择货币。如果只有一种货币，这个下拉框将不会显示。
	<li><b>预定：</b> 如果管理员激活这项目，此选项将会出现。您可利用预定选项，允许收件人以预定付款（分期）支付发票。
	收到发票的人将获得通知关于分期付款的日期和金额；当收件人接受发票，预定付款将显示在他的（发出）预定付款。
	<li><b>付款方式：</b> 在这之后，如果有多于一种合适的付款方式存在，您将要选择付款方式。如果只有一种方式存在，这字段将不会显示。
	<li><b>描述：</b> 当接受了发票后，这描述将在交易概况中显示为交易描述。
</ul>
完成表格后，请点击 &quot;提交&quot; 来发送发票。在提交后，您会被要求进行确认。
<hr class="help">

<span class="admin">
<A NAME="manage_invoices_by_admin"></A>
<h3>系统发票</h3>
<p>这个视窗给您选择得到系统账户发送 <a href="#top"><u>发票</u></a> 给会员账户（发出发票），和会员账户发送发票给系统账户（收到发票）的概况。
<p>您可以筛选以下字段：
<ul>
	<li><b><a href="#status"><u>状态</u></a></b>
	<li><b>类型：</b> &quot;收到&quot; 或 &quot;发出&quot;
	<li><b>一个时段</b>
	<li><b>名称：</b> 用户名或真正的会员名称
	<li><b>描述</b>
	<li><b><a href="${pagePrefix}account_management#transaction_types"><u>付款方式</u></a>：</b> 这只适用于发出发票。收到发票的付款方式是没有定义的。
	当接受发票时（见 <a href="#incoming_invoice_detail_by_admin"><u>发票详情</u></a>），管理员必须选择付款方式。
</ul>
填写表格后，请点击 &quot;搜索&quot;，结果将显示在 <a href="#invoices_result_by_admin"><u>视窗下面</u></a>。
<p>注：当搜索具有 &quot;开启&quot; 状态以外的发票时，将显示已经被取消、拒绝或接受的发票历史。
<hr class="help">
</span>

<span class="member">
<A NAME="manage_invoices_by_member"></A>
<h3>我的发票</h3>
在此视窗中，您可以使用 &quot;类型&quot; 选项来查看 &quot;收到&quot; 或 &quot;发出&quot; <a href="#top"><u>发票</u></a> 的列表。
结果将显示在 <a href="#invoices_result_by_member"><u>视窗下面</u></a>。
<p>在进阶选项，您利用表格中的一些或所有字段来优化搜索。多数字段是相当简单直接的。&quot;用户名&quot; 是会员用于登录的名称，&quot;会员&quot; 是会员的真正名称。<br>
<a href="#status"><u>状态</u></a> 可以有多种不同的值，请点击连结以知道更多有关的资料。
<hr class="help">
</span>

<span class="admin broker">
<A NAME="manage_member_invoices_by_admin"></A>
<A NAME="manage_member_invoices_by_broker"></A>
<h3>会员发票</h3>
<p>这个视窗给您提供选项以获得会员收到或发出的 <a href="#top"><u>发票</u></a> 概况。
<p>您可以筛选以下字段：
<ul>
	<li><b><a href="#status"><u>状态</u></a></b>
	<li><b>类型：</b> &quot;收到&quot; 或 &quot;发出&quot;
	<li><b>一个时段</b>
	<li><b>名称：</b> 用户名或真正的会员名称
	<li><b>描述</b>
	<li><b>付款方式</u></a></b>
</ul>
填写表格后，请点击 &quot;搜索&quot;，结果将显示在管理发票页面。
<p>注：当搜索具有“开启”状态以外的发票时，将显示已经被取消、拒绝或接受的发票历史。
<hr class="help">
</span>

<a name="status"></a>
<h3>发票状态</h3>
有以下的 <a href="#top"><u>发票</u></a> &quot;状态&quot;：
<ul>
	<li><b>开启：</b> 已发送发票，但收件人尚未支付或拒绝接收。
	<li><b>接受：</b> 收件人已支付的发票。
	<li><b>拒绝：</b> 收件人拒绝接收的发票。
	<li><b>取消：</b> 您自己取消的发票。
	<li><b>过期：</b> 发票收件人没有给予任何反应（没有支付或拒绝），并且到期日已过。
</ul>
<hr class="help">

<A NAME="accept_invoice"></A>
<h3>接受发票</h3>
这是在点击 &quot;接受&quot; <a href="#top"><u>发票</u></a> 按钮后出现的确认视窗。<br>
在此之后，便没有返回的办法：当您点击 &quot;提交&quot; 后，金额将从账户中提取，并将支付该帐单。
<hr class="help">

<A NAME="invoices_result_by_admin"></A>
<A NAME="invoices_result_by_member"></A>
<h3>搜索结果</h3>
这个页面显示 <a href="#top"><u>发票</u></a> 搜索结果列表。点击查看图标（<img border="0" src="${images}/view.gif" width="16" height="16">）将会开启发票的详情。
<br>
如果发票是 &quot;开启&quot; 的，您可以按照发票的类型（接受、拒绝或取消）来执行行动。
<hr class="help">

<A NAME="invoice_details"></A>
<A NAME="outgoing_invoice_detail_by_admin"></A>
<A NAME="incoming_invoice_detail_by_admin"></A>
<A NAME="outgoing_invoice_detail_by_member"></A>
<A NAME="incoming_invoice_detail_by_member"></A>
<h3>发票详情</h3>
此视窗显示发票的详情。根据权限和发票类型，您可以采取下列一项行动。
<ul>
	<li><b>接受：</b> <span class="member">如果您是</span><span class="admin broker">如果这个会员是</span>此发票的收件人，您可以接受它。
	如果您这样做，将从<span class="admin broker">会员</span><span class="member">您</span>的账户中提取金额，并转账到发票发件人的账户。
	在正常的语言：您已支付帐单。如果您点击这个按钮，确认视窗会随之而来，要求您确认。
                        </li>
	<li><b>拒绝：</b> <span class="member">如果您是</span><span class="admin broker">如果这个会员是</span>此发票的收件人，您也可以拒绝接收此发票。
	这意味着您拒绝支付这金额。付款将不会发生，而另一方将收到有关的通知。<br>
	拒绝从系统/组织发出的发票是不可能的。
	<li><b>取消：</b> 如果<span class="admin broker">这个会员</span><span class="member">您</span>是此发票的发件人，您可以在收件人接受该发票之前的任何时间取消它。
	如果您取消了发票，另一方将在其个人讯息视窗中收到该发票已被取消的通知。
</ul>
对于收到的发票，如果支付这笔款项有多个可能的付款方式，您可能首先要指定付款方式。在这种情况下，您应该在可见的 &quot;付款方式&quot; 下拉框作出选择。在许多情况下，这个下拉框不会出现。
<hr class="help">

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