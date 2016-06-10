<div style="page-break-after: always;">
<span class="admin"> 
<p class="head_description">警示是通知管理员各种事情，包括系统错误和有关会员的事件（账户登录尝试的数目、已达到信贷额度等）。</p>

<i>在哪里可以找到它？</i><br>
警示与日志可以通过 &quot;菜单：警示&quot; 进入。<br>
<p><i>如何使它运作？</i><br>
<a href="${pagePrefix}alerts_logs#system_alerts"><u>系统警示</u></a> 永远是启用的。<br>
<a href="${pagePrefix}alerts_logs#member_alerts"><u>会员警示</u></a>可以在 <a href="${pagePrefix}settings#alerts"><u>警示设置</u></a> 页面中配置。
<hr>

<a NAME="system_alerts"></a>
<h3>系统警示</h3>
系统警示视窗将显示有系统警示的列表。这些可能是有关正在运行的软件系统的警示，和与会员账户没有直接关系的警示。可用以下警示：
<ul>
	<li>应用程序启动
	<li>应用程序关闭
	<li>账户费用开始执行
	<li>账户费用已取消执行
	<li>账户费用执行完成
	<li>账户费用执行失败
	<li>账户费用已还原
</ul>
您可以删除警示，通过单击在警示右侧的 <img border="0" src="${images}/delete.gif"
	width="16" height="16">&nbsp;删除图标，或选择一个或多个警示，然后单击 &quot;删除所选&quot; 按钮。这将会把警示从列表中删除。但是，有一个 <a
	href="#alerts_history"><u>警示历史</u></a> 视窗，在这里，您可以搜索一个已从列表上删除的旧警示；这种方法有可能检测再次出现的警示和模式。
<hr class='help'>

<a name="member_alerts"></a>
<h3>会员警示</h3>
会员警示视窗会显示与会员行为有关的警示列表。临界点可在 &quot;菜单：设置 > <a href="${pagePrefix}settings#alerts"><u>警示设置</u></a >&quot; 页面中设置。目前，可用以下警示：
<ul>
	<li>人们收到一定数目的非常不好的 <a href="${pagePrefix}references"><u>评语</u></a>
	<li>人们发出一定数目的非常不好的评语
	<li>人们收到发票而没有反应的日数（只是系统给会员发票）</u></a>
	<li>发票被拒绝的数目
	<li>超过尝试的不正确用户名（有人多次尝试以错误的用户名登录）
	<li>用户暂时被封锁，因为超过了尝试限额。
	<li>超过了不正确密码限额（有人多次尝试以正确的用户名登录，但密码是错误的）
	<li>新会员在登录页面（自我注册）。
	<li>尚未偿还的过期贷款。
</ul>
您可以使用 <img border="0" src="${images}/delete.gif" width="16"
	height="16">&nbsp;<b>删除图标</b> 从列表中删除警示。但是，有一个 <a href="#alerts_history"><u>警示历史</u></a> 视窗，在这里您可以搜索已从列表上删除的旧警示。
	这种方法有可能检测再次出现的警示和模式。
<hr class='help'>

<A NAME="alerts_history"></A>
<h3>警示历史</h3>
这视窗可让您搜索所有已被删除的旧警示。如果您想看最近的警示，请前往 &quot;菜单：警示 > 系统警示&quot; 和 &quot;菜单：警示 > 会员警示&quot;。新的警示（尚未从列表上删除的警示）不显示在警示历史。<p>
如果您不填写修改框，您将得到所有账户警示或系统警示的概况。当您选择警示类型为 &quot;会员&quot;，警示会在警示列表中显示会员名称。
还可以搜索某一特定会员的警示；为此，您可以填写登录和会员（自动完成）字段。
<hr class='help'>

<a name="alerts_history_result"></a>
<h3>警示历史搜索结果</h3>
此视窗显示所有的旧警示，这是根据您在上面视窗中所指定的搜索标准。可以有多个页面，请见视窗的下方进入其他页面。<br>
如果您想看最近的警示，请前往 &quot;菜单：警示 > 系统警示&quot; 和 &quot;菜单：警示 > 会员警示&quot;。新的警示（尚未从列表上删除的警示）不显示在警示历史。
<hr>


<A NAME="error_log"></A>
<h3>错误日志</h3>
此页面将显示所有应用程序错误的列表。您可以直接从列表中开启和删除错误。当您删除错误后，它将仍然可以在 <a href="#error_history"><u>错误日志历史</u></a> 页面显示。
<hr class='help'>

<a name="error_history"></a>
<h3>搜索错误日志历史</h3>
这页面让您指定期限，以限制在其下方的 <a href="#error_history_result"><u>搜索结果</u></a>。
定义期限，可通过指定开始日期和结束日期；请按输入指示格式的日期或单击日历图标。
<hr class="help">

<A NAME="error_history_result"></A>
<h3>错误日志历史结果</h3>
这页面将显示在指定期限内所有应用程序错误的列表，该期限置于视窗上方的 <a href="#error_history"><u>搜索错误日志历史</u></a>。
如果没有指定，将显示一个齐全的列表。您可以直接从列表中开启错误。结果可以给标记页数，您可以单击 &quot;跳到页：&quot; 右侧的数字以浏览该页。
当错误已是在 <a href="#error_log"><u>错误日志</u></a> 页面（&quot;菜单：警示 > 错误日志&quot;）被删除后，它们才出现在此视窗。
<hr class='help'>

<a name="error_log_details"></a>
<h3>错误日志详情</h3>
此页面会显示应用程序错误详情的列表。此资料将帮助系统管理员和联环系统开发者查看错误的成因。
<p><b>注：</b> 应用程序错误并不一定是代表出错。由于联环系统配置的灵活性，安装具有抵触职能的配置是有可能的；联环系统捕捉大多数这类错误并有特定的讯息，但无法预测所有可能的配置错误。
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