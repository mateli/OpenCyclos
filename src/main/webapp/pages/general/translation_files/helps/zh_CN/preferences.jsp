<div style="page-break-after: always;">
<a name="top"></a>
<p class="head_description">
当指定的事件发生在联环系统时，可通过电邮或讯息让用户得到警示通知。
</p>

<span class="member">
<i>在哪里可以找到它？</i><br> 
通知可通过 &quot;菜单：偏好 > 通知&quot; 进入。
</span>

<span class="admin"> <i>在哪里可以找到它？</i><br>
通知可通过 &quot;菜单：个人 > 电邮通知&quot; 进入。
<br><br><i>如何使它运作？</i><br>
管理员永远可以选择配置个人的通知。<br>
管理员可以在 <a	href="${pagePrefix}groups#manage_group_permissions_member"><u>会员权限</u></a> 节内（&quot;偏好&quot; 区块中）启用会员（组别）的通知职能。<br>
附加的通知设置可在 <a href="${pagePrefix}groups#edit_member_group"><u>会员组别设置</u></a>（&quot;通知&quot; 区块）中定义。<br>
通知的内容可以通过在 &quot;菜单：翻译 > 通知&quot; 的 <a href="${pagePrefix}translation"><u>翻译</u></a> 区块中更改。<br>
</span>
<hr class="help">

<span class="admin"><A NAME="email_notifications"></A>
<h3>电邮通知</h3>
通过下拉的复选框来选择您希望收到的 <a href="#top"><u>通知</u></a> 类型。完成后，请点击 &quot;提交&quot; 来储存更改。
<ul>
	<li><b>新增注册会员：</b> 如果会员放置在新组别，您可以得到通知。可以拣选一个或多个组别。<br>
	如果电邮确认是必须的 (在 <a href="${pagePrefix}groups#group_registration_settings"><u>会员组别设置</u></a>），当会员已确认注册时，您将收到通知。
	<li><b>付款：</b> 您可以为每个可用的 <a href="${pagePrefix}account_management#transaction_types"><u>付款方式</u></a> 设置通知。
	这就意味着当这种类型的转账发生时，您会收到通知。
	<li><b>新的付款等待授权：</b> 对于每一个可用的
	<a href="${pagePrefix}account_management#transaction_types"><u>可授权的付款类型</u></a>，
	您可以设置一个通知，意思是在发生这种类型的转账等待授权时通知您。
	<li><b>担保：</b> 选择当一个新的
	<a href="${pagePrefix}guarantees"><u>担保</u></a>
	创建时，您想得到通知的担保类型。
	<li><b>讯息：</b> 您可以为每个 <a href="${pagePrefix}messages#categories"><u>讯息类别</u></a> 设置通知。
	<li><b>系统警示：</b> 您可以为每个 <a href="${pagePrefix}alerts_logs#system_alerts"><u>系统警示</u></a> 设置通知。
	<li><b>会员警示：</b> 您可以为每个 <a href="${pagePrefix}alerts_logs#member_alerts"><u>会员警示</u></a> 设置通知。
	<li><b>应用程序错误：</b> 拣选此复选框将会通过电邮得到 <a
		href="${pagePrefix}alerts_logs#error_log"><u>应用程序错误</u></a> 的通知。
	<li><b>系统发票：</b> 拣选此复选框将会通过电邮收到接收 <a href="${pagePrefix}invoices#top"><u>系统发票</u></a> 的通知。
</ul>
<hr class="help">
</span>

<span class="member">
<A NAME="notification_preferences"></A>
<h3>通知偏好</h3>
在此页面，您可以定义您希望收到的通知，您还可以选择通过联环系统的内部讯息、电邮或短讯（如果管理员启用了）来接收它们。但是，管理员发送的内部讯息是不能禁用的。<br>
与往常一样，您应该首先点击 &quot;更改&quot; 按钮，以进行修改；完成后，请点击 &quot;提交&quot; 来储存更改。
<br><br>有以下通知可用（请注意，并非所有通知会出现）：
<ul>
	<li><b>会员讯息：</b>
	这些讯息是通过联环系统发送的，无论是会员或者是管理员。此选项启用了（通过拣选电邮选项）接收电邮的方法，而不需要在联环系统中刊登您的电邮地址。
	<li><b>管理员致个人讯息</b>
	<li><b>管理员讯息：</b> 这是管理员发送的个人或大量讯息。
	<li><b>访问警示：</b> 您将收到各种使用错误密码尝试登录到您的账户的通知。
	<li><b>一般账户活动：</b> 这是有关账户的活动，如：低信贷警示。
	<li><b>经纪活动：</b> 通知有关经纪的任何活动。<span class="member">它们是：</span> 
	<ul>
		<span class="member">
		<li>新增 <a href="${pagePrefix}brokering#commission_contract"><u>佣金合同</u></a >
		<li>取消佣金合同。
		</span>
		<span class="broker">
		<li>经纪已过期 
		<li>经纪已删除/经纪已更改 
		<li>已从经纪组别删除 
		<li>等待经纪授权付款
		<li>接受 <a href="${pagePrefix}brokering#commission_contract"><u>佣金合同</u></a>  
		<li>拒绝佣金合同 
		</span>
	</ul>
	<li><b>付款活动：</b> 有关付款的活动。这将是关于收到的付款或授权和预定付款的活动。
	<li><b>通过外部渠道付款：</b> 当付款是外部进行的（如：短讯）。
	<li><b>贷款活动：</b> 这是有关 <a href="${pagePrefix}loans"><u>贷款</u></a> 的活动：有关新贷款和支付贷款到期的讯息。只有当会员有贷款，这选项才会显示。
	<li><b>广告到期警示：</b> 当广告到期。
	<li><b>广告兴趣通知：</b> 如果启用了，当新的广告符合 <a	href="${pagePrefix}ads_interest"><u>广告兴趣</u></a> 时，您将收到通知。
	<li><b>发票活动：</u></b></a> 任何有关发票的活动（如：收到、接受、取消）
	<li><b>收到评语：</b> 当收到或修改评语。
	<li><b>交易反馈意见：</b></a> 通知有关在特定交易上的质量评语。
	<li><b>担保：</b></u></a> 处理联环系统中的担保系统。
	<li><b>付款义务：</b></u></a> 处理联环系统中的付款义务系统。
</ul>
</span>

<span class="member admin">
<hr class="help">
<A NAME="receipt_printers"></A>
<h3>收据打印机</h3>
在某些情况下，联环系统用户希望能够在付款后打印收据。
例如，供企业使用联环系统收取客户付款。常见的网络应用程序不能打印到
当地的收据（票）打印机。然而，在联环系统这已成为可能。
从交易详细信息页面和网络销售终端页面（在直接付款后），可以打印交易收据。
当付款是一个包中的所有预定付款的一部分，所有预定付款将被印在收据。
网络销售终端页面还可以选择打印每日交易的列表。
<br><br>
启用收据打印机，涉及两个主要任务。
首先，会员要使用收据打印机，将需要使打印机列为可用
（通过将它们添加到
<a href="${pagePrefix}preferences#receipt_printer_search"><u>收据打印机列表</u></a>）。
这通常是一次过的工作，并通过有技术能力的会员。
这样做后，要使​​用它们的用户（会员或特定的运营商）可以启用打印机。<br>
</span>
	
	
<span class="member"><A NAME="receipt_printer_search"></A>
<hr class="help">
<h3>收据打印机列表</h3>
此视窗显示您所有配置的
<a href="${pagePrefix}preferences#receipt_printers"><u>收据打印机</u></a> 清单。
您可以选择相应的图标删除和编辑所选的打印机。
打印机在本地计算机定义配置后，它们需要在每台计算机上激活。
在选项“使用这台电脑打印”，您可以在您使用的计算机选择一台打印机，在本地打印机打印交易收据。
<br><br>
任何打印机被添加到列表后（“新增”选项），将可以给操作员使用，
无论是用于主要的网络接入渠道（偏好 - 收据打印机）或网络销售终端渠道（在左上角的打印设置）。
</span>
<hr class="help">


<span class="member"> <A NAME="receipt_printer_details"></A>
<h3>收据打印机的详细信息</h3> 
开始和结束的文件命令取决于特定的打印机品牌/型号。
他们可用于，例如，切纸或印刷后使用蜂鸣器。
要发送特定的ASCII字符，您可以使用#号，例如，ASCII 100，使用#100。
作为一个例子，使用爱普生打印机切纸的ASCII字符是#27#105。
要看特定打印机配置的更多细节，请到
<a href="http://project.cyclos.org/wiki/index.php?title=Receipt_printers" target="_blank">Wiki</a>。
<ul>
	<li><b>显示名称：</b> 在联环系统的名字
	<li><b>本地打印机名称：</b> 本地打印机名称必须是操作系统上配置的这台打印机的确切名称。
	<li><b>开始文件命令：</b> 在这里您可以定义一个本地打印机的命令，如新行、字体大小等。
	这些命令是给特定的打印机型号。<br>
	任何设在这一字段的文本（ASCI字符）将显示在收据的开始。
	这样您可以把一个额外的“页首”文本设在打印收据。如果您设置文字，请确保您在文本命令后加入一个新行 \n<br>
	（打印收据也包括系统定义的“页首”文本）
	<li><b>结束文件命令：</b> 在这一字段您可以加入任何本地打印机命令。
	正常情况下，这一字段是用来定义印刷后如何切纸。
	<li><b>付款收据上的附加信息：</b> 在这里，您可以加入任何文本。
	它会显示一个额外的“页脚”（如感谢购买..）
</ul>
当您想使用一个本地的收据打印机，它需要安装在您的操作系统内。
下面是在Ubuntu和视窗系统的例子。
<br><br>
<b>Ubuntu</b>
<ul>
	<li>需要在计算机上安装 Java
	<li>首先安装本地打印机的驱动程序（参见打印机制造商的网站）
	<li>在 Ubuntu，走到：System - Administration - Printing
	<li>选择：“Add”，打印机应该显示，选择它并点击 “Forward”
	<li>现在它会搜索驱动程序，从列表中选择“Generic”（第一个选项）
	<li>在“Models”选择“Raw queue”选项
	<li>给打印机一个简短名称，例如 Epson
	<li>现在点击“Apply”，这打印机应该出现在打​​印机列表
	<li>现在打开一个命令提示视窗并运行以下命令：<br>
	cupsctl FileDevice=Yes<br>
	padmin -p Epson -E -v file:/dev/usb/lp0<br>
	（确保打印机的名称是您添加了那一个）
</ul>
<b>视窗系统</b>
<ul>
	<li>需要在计算机上安装 Java
	<li>安装本地打印机的驱动程序（参见打印机制造商的网站）	
	<li>在控制面板 - 打印机，添加本地打印机。
	<li>在“端口”选择新添加的打印机
	<li>选择制造商为“一般”和模型为“只有文本”
	<li>给打印机一个简短名称，例如 爱普生
	<li>这打印机应该出现在打​​印机列表
</ul> </span>

</div>
<%--  page-break end --%>

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