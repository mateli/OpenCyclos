<div style="page-break-after: always;">
<p class="head_description">
联环系统有登录密码，并且可以配置使用特殊交易密码。
</p>
<i>在哪里可以找到它？</i><br>
您可以通过 &quot;菜单：个人 > 更改密码&quot; 来更改您的登录密码。<br>
对于交易密码，请参阅有关的 <a href="#transaction_password"><u>帮助</u></a> 节。
<br><br>
<span class="broker admin">
会员密码可以通过 &quot;访问 > 管理密码&quot; 节内的会员个人资料来更改。
</span>
<hr>

<a NAME="change"></a>
<h3>更改登录密码</h3>
要更改密码，输入您现时的密码和两次新密码，然后点击 &quot;提交&quot;。密码政策有可能已被激活，如：禁止容易或明显的密码。
在这种情况下，当您选择的密码不符合政策規定的要求时，将会出现错误讯息。讯息会通知您关于政策和允许的密码格式。
<br>
您不允许选择在过去曾经使用的密码作为新密码。

<span class="admin">如果拣选了 &quot;强逼下次登录时更改&quot;，会员将在首次登录时被要求更改密码。<br>
如果已启用了组别设置的 &quot;通过电邮发送密码&quot;，您将有额外的选择来自动生成一个新密码，并发送给会员；只要点击 &quot;重置密码和发送电邮&quot; 的提交按钮便可。
在这种情况下，密码将被自动生成，而您不需要输入任何东西。</span>
<hr class="help">

<span class="admin">
根据系统（访问）设置（&quot;菜单：设置 > 访问设置&quot;），可以在正常的表格字段中输入数字的交易密码，或使用虚拟键盘。您也可以选择将用作为交易密码一部分的字符。
可以通过用户管理页面（&quot;个人资料 > 访问 > 管理密码&quot;）来管理交易密码。该选项只适用于已启用交易密码的组别（在组别权限）。
<hr class="help">
</span>

<a NAME="transaction_password_generation"></a>
<a name="transaction_password"></a>
<h3>交易密码</h3>
交易密码是一个密码，可以要求用户每次想要进行付款时提供。
在这里您可以检索您的个人交易密码。在点击 &quot;领取交易密码&quot; 之后，您的密码会显示出来。
请确定要记住您的交易密码。您将只能领取密码一次。
<hr class="help">

<span class="admin">
<a NAME="manage_transaction_password"></a>
<h3>管理交易密码</h3>
<br>当启用了组别的 <a href="#transaction_password"><u>交易密码</u></a>，可以有四种不同状态：
<ul>
	<li><b>没有生成：</b> 交易密码从未被使用/生成。
	<li><b>待处理：</b> 交易密码等待被会员/管理员生成。
	<li><b>活跃：</b> 交易密码已被会员领取。
	<li><b>被封锁：</b> 交易密码已被管理员封锁。
</ul>
这状态显示在此视窗。<p>
管理员（有适当的权限）可以点击 &quot;封锁交易密码&quot; 按钮来重置或封锁交易密码。在这种情况下，交易密码是无效的，会员将不会收到一个新密码（直到管理员重置它）。<p>
您也可以通过 &quot;重置交易密码&quot; 按钮来重置交易密码。在这种情况下，会员将收到新的交易密码。
<hr class="help">
</span>

<a name="pin"></a>
<h3>个人密码</h3>
个人密码是用于一些对外付款方式的密码，如：销售点、PayPal 或通过短讯的付款。个人密码只包含数字。<p>
<span class="admin">
要启用个人密码，必须完成以下行动：
<ul>
	<li><b>渠道：</b> 个人密码必须在 <a href="${pagePrefix}settings#channels_detail"><u>渠道</u></a> 内启用（&quot;菜单：设置 > 渠道&quot;，并点击编辑图标来修改）。
	<li><b><a href="${pagePrefix}groups#edit_member_group"><u>组别设置</u></a>：</b> 在 &quot;访问设置&quot; 下面，必须设置密码长度。
	<li><b><a href="${pagePrefix}account_management#transaction_types"><u>交易类型</u></a>：</b> 在适当的交易类型，必须启用渠道。
</ul>
</span>
个人密码和访问 <a href="${pagePrefix}settings#channels"><u>渠道</u></a> 可以通过
<span class="admin">会员 <a 
href="${pagePrefix}profiles"><u>个人资料</u></a> > 外部访问到达。
</span>
<span class="member">&quot;菜单：个人 > 外部访问&quot; 到达。
</span>
<hr class="help">

<a NAME="change_pin"></a>
<h3>更改/解除封锁个人密码</h3>
<a href="#pin"><u>个人密码</u></a> 是使用于一些对外付款的方式，如：销售点、PayPal 或短讯付款。<br>
个人密码只可以是数字（不允许字母）。若要更改密码，首先必须输入您的登录密码。
如果使用了 <a href="#transaction_password"><u>交易密码</u></a>，您要首先输入该密码（代替登录密码）。
个人密码必须输入两次，并用 &quot;提交&quot; 按钮来确认。<p>
如果错误输入个人密码，当超过了尝试限额后（默认是 3），密码可以被封锁。
您可以等待直到封锁时间已过，或者以手动 &quot;解除封锁&quot; 个人密码（可选择在更改密码视窗下面的解除封锁按钮）。
<hr class="help">

<a NAME="select_channels"></a>
<h3>渠道</h3>
在这里显示可能有的<span class="admin"> <a href="${pagePrefix}settings#channels"><u>渠道</u></a>。</span>
<span class="member">渠道；渠道是一个媒介体，通过它可以访问联环系统，例如：可以通过浏览器网络或手机访问。</span>
<br>
不是所有渠道都可用的，是根据您组织中的配置。您可以在复选框内选择您要使用的渠道。
<ul>
	<li><b>销售点网络付款：</b> 销售点付款（象消费者在商店付款）。
	<li><b>WAP 1 接入：</b> 手机访问支持较旧模式的WAP 1（无线应用协议1）。手机付款支持销售的概况，请浏览付款历史和付款。
	<li><b>WAP 2 接入：</b> 手机访问支持较现代的WAP 2（无线应用协议2）。手机付款支持销售的概况，请浏览付款历史和付款。
	<li><b>网上商店付款：</b> 允许在外部（电子商务）地点付款。当您选择后，请不要忘记点击 &quot;提交&quot;，否则更改将不会被储存。
</li></ul>
<b>注意：</b>短讯渠道可以在通知页面激活。
视窗的链接（在访问渠道之下）将带领您直接到该页面。
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