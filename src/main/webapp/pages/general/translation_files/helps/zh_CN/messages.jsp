<div style="page-break-after: always;">
<a name="top"></a>
<p class="head_description">联环系统的讯息可以在许多不同场合用于所有类型的用户之间。
有许多灵活的特色可用于会员和组别之间发送讯息。也可以定义不同类别的讯息，使某些组别能够访问某些类别的讯息。<br>
讯息是通过联环系统的内部讯息系统发送的。这意味着当收件人一旦登录，便看到您发送的讯息。联环系统可 <a
	href="${pagePrefix}preferences#notifications"><u>配置</u></a> 为这些讯息也通过电邮发送 - 但是，是否允许这做法是收件人的责任。
如果您要肯定讯息是电邮发送的，您应该直接使用电邮。不过，这可以通过联环系统界面来做到，它在每个会员的个人资料包含 &quot;发送电邮&quot; 按钮。</p>
<i>在哪里可以找到它？</i><br>
<span class="member">您可以通过 &quot;菜单：个人 > 讯息&quot; 来访问讯息。</span>
<span class="admin">您可以通过 &quot;菜单：讯息 > 讯息&quot; 来访问讯息。</span>
<span class="broker">作为经纪发送讯息（给您的所有会员），您可以去
&quot;菜单：经纪 > 发送讯息&quot; 选项。
</span>
另一种方法发送讯息是到会员的 <a href="${pagePrefix}profiles"><u>个人资料</u></a>，
<span class="admin">在 &quot;会员讯息&quot; 节内，
<br><br>
<i>如何使它运作？</i><br>
您将需要设置权限来允许讯息。对于管理员，您应该在 &quot;讯息&quot; 区块下面设置 <a href="${pagePrefix}groups#manage_group_permissions_admin_member"><u>权限</u></a>，
它让您可以设置不同的权限来发送讯息给会员。<br>
有关讯息的 <a href="${pagePrefix}groups#manage_group_permissions_member"><u>会员权限</u></a>
可以在 &quot;讯息&quot; 区块下面找到。<br>
经纪有多一套 <a href="${pagePrefix}groups#manage_group_permissions_broker"><u>权限</u></a>，它是有关发送讯息给他们的经纪会员；
这些讯息可以在 &quot;个人讯息&quot; 区块下面找到。
</span>
<hr>

<A NAME="messages_search"></A>
<h3>讯息列表</h3>
在这页面，您可以选择查看您发送或收到的 <a href="#top"><u>讯息</u></a>。您可以使用下拉框来选择查看 &quot;收件箱&quot;、&quot;寄件箱&quot;
或 &quot;垃圾箱&quot; 的文件夹。<br>
<span class="admin">您也可以在 &quot;类别&quot; 下拉框中选择特定的类别（注：您需要最少有查看类别的权限，以便它们显示在列表中）。<br></span>
您可以点击 &quot;进阶&quot; 按钮来搜索讯息，通过使用关键字，或者是接收或发送讯息的会员。在填写搜索项目后，您必须点击 &quot;提交&quot; 按钮。
<br><br>为了发送新的讯息，您必须点击标记为 &quot;发送新讯息&quot; 的提交按钮。
<hr class="help">

<A NAME="messages_search_result"></A>
<h3>讯息搜索结果</h3>
这页面显示的 <a href="#top"><u>讯息</u></a> 是根据
<a href="#messages_search"><u>以上视窗</u></a> 中所指定的标准。
<p>图标将显示讯息的状态，以下任一：
<ul>
	<li><img border="0" src="${images}/message_unread.gif"
		width="16" height="16">&nbsp;（未读）
	<li><img border="0" src="${images}/message_read.gif"
		width="16" height="16">&nbsp;（已读）
	<li><img border="0" src="${images}/message_replied.gif"
		width="16" height="16">&nbsp;（已回复）
	<li><img border="0" src="${images}/message_removed.gif"
		width="16" height="16">&nbsp;（已删除）
</ul>
您可以执行以下行动：
<ul>
	<li>您可以选择标题来开启讯息。
	<li>您可以使用它们的复选框来选择多种讯息，还有在讯息视窗下面的下拉框选择一项行动，并应用在这些讯息。
	<li>您可以直接选择删除图标（<img border="0" src="${images}/delete.gif" width="16" height="16">）来删除单一讯息。
</ul>
<hr class='help'>

<A NAME="messages_send"></A>
<h3>发送讯息</h3>
<span class="admin"> 
	在这里，您可以发送 <a href="#top"><u>讯息</u></a> 给会员。
	如果您从讯息职能内（&quot;菜单：讯息 > 讯息&quot;）发送讯息，您必须指定登录会员和/或会员字段（有自动完成功能）。您将要指定一个讯息的类别。<br>
	<p>如果您发送讯息给组别，形式将会更改，并将有其它的编辑可使用。首先，您将必须从
	&quot;组别&quot; 多种下拉框中选择一个或多个组别；所选组别的所有会员将收到讯息。<br>
	当发送给组别，您将可选择以纯文字或丰富文本发送。后者允许您使用特殊的特色铺排，如字体、图片等；
	您必须在单选按钮中拣选 &quot;丰富文本&quot; 选项，然后将可看见丰富文本编辑器，让您可以使用各种铺排按钮（只须试验它们，玩一会便知道）。
	您也可以使用纯HTML格式，请点击丰富文本编辑器的 &quot;来源&quot; 按钮。<br>
	如果您想包含图片，您首先必须在 &quot;菜单：内容管理 > <a href="${pagePrefix}content_management#custom_images"><u>自订图片</u></a>&quot; 节中把它上载。<br>
	作为管理员，您不能发送讯息给另一管理员。
</span>
<span class="member">
	在这里，您可以发送 <a href="#top"><u>讯息</u></a> 给会员或者管理员。
</span>
<span class="broker">
	在这里，您可以发送讯息给：
	<ul>
		<li>特定会员
		<li>您的所有注册会员
		<li>或管理员。
	</ul> 
</span>
<span class="member">
	形式非常简单。如果您选择 &quot;会员&quot;，您必须用名称或者用户名来指定该会员。如果您填写用户名，真正的名称是自动提供的。<br>
	如果拣选 &quot;管理员&quot;，您也应该指定讯息的类别。<br>
</span>
<span class="broker">
	如果拣选 &quot;我的注册会员&quot;，您可以选择以 &quot;纯文字&quot; 或 &quot;丰富文本&quot; 格式来撰写讯息。后者允许您使用特殊的特色铺排，如字体、图片等；
	您必须在单选按钮中拣选 &quot;丰富文本&quot; 选项，然后将可看见丰富文本编辑器，让您可以使用各种铺排按钮（只须试验它们，玩一会便知道）。
	您也可以使用纯HTML格式，请点击丰富文本编辑器的 &quot;来源&quot; 按钮。<br>
</span>
完成后，请点击 &quot;提交&quot; 来发送讯息。该讯息将会出现在您的 &quot;寄件箱&quot;。
<hr class='help'>

<span class="broker">
<a name="messages_send_brokered_members"></a>
<h3>发送讯息</h3>
	在这里，您可以发送讯息给您所有的注册会员。格式非常简单。
	<p>您可以选择用 &quot;纯文字&quot; 或 &quot;丰富文本&quot; 格式来撰写讯息。后者允许您给使用特殊的特色铺排，如字体、图片等；
	您必须在单选按钮中拣选 &quot;丰富文本&quot; 选项，然后将可看见丰富文本编辑器，让您可以使用各种铺排按钮（只须试验它们，玩一会便知道）。
	您也可以使用纯HTML格式，请点击丰富文本编辑器的 &quot;来源&quot; 按钮。<br>
完成后，请点击 &quot;提交&quot; 来发送讯息。该讯息将会出现在您的 &quot;寄件箱&quot;。
<hr class="help">
</span>

<A NAME="messages_view"></A>
<h3>查看讯息</h3>
这是 <a href="#top"><u>讯息</u></a>。您可以通过点击标记为 &quot;转移至垃圾箱&quot; 的提交按钮，
来选择删除讯息并把它转移至 &quot;垃圾箱&quot;。
垃圾箱内的讯息可以再次读取，只需要在您的 <a href="#messages_search"><u>讯息概况</u></a> 中开启 &quot;垃圾箱&quot;。<br> 
您可以通过点击 &quot;回复&quot; 按钮来回复讯息（如果该讯息是由您发送的，就不会有回复按钮）。
<hr>

<a name="categories"></a>
<p class="sub_description">
<h2>讯息类别</h2>
<a href="#top"><u>讯息</u></a> 类别让您可以更好地管理发送给管理员的讯息。类别只存在于会员和管理员之间的讯息。会员之间的讯息不使用类别。
</p>
<hr class="help">

<span class="admin">
<A NAME="message_categories"></A>
<h3>讯息类别</h3>
此页面将列出可用的 <a href="#top"><u>讯息</u></a> <a href="#categories"><u>类别</u></a>。
<p>
<ul>
	<li><img border="0" src="${images}/edit.gif" width="16" height="16">&nbsp;可让您修改类别。
	<li><img border="0" src="${images}/delete.gif" width="16" height="16">&nbsp;可让您删除类别。这只限于它没有曾经被使用。
	<li>使用标记为 &quot;新增讯息类别&quot; 的提交按钮来新增类别。
</ul>
</span>

<span class="admin">
<a name="edit_message_category"></a>
<h3>修改讯息类别</h3>
这视窗可让您更改 <a href="#top"><u>讯息</u></a> 的 <a href="#categories"><u>类别</u></a> 名称。
请选择一些有意义的描述，这将是会员在其 <a href="#messages_send"><u>发送讯息</u></a> 视窗中看到的。<br>
与往常一样，您应该点击 &quot;更改&quot; 来进行修改；完成后，点击 &quot;提交&quot; 按钮来储存更改。
<hr class="help">
</span>

<span class="admin">
<a name="new_message_category"></a>
<h3>新增讯息类别</h3>
这视窗可让您为 <a href="#top"><u>讯息</u></a> 新增一个 <a href="#categories"><u>类别</u></a>。只要输入名称的描述，并点击 &quot;提交&quot; 按钮来储存更改。<br>
会员将在其 <a href="#messages_send"><u>发送讯息</u></a> 视窗中看到此名称。
<hr class="help">
</span>

<span class="broker admin">
<a name="sms_mailings"></a>
<h3>短讯邮件</h3>
在此视窗中，您可以搜索短讯邮件。这些邮件是发送到组的个人用户。
可以搜索组或个人短讯邮件的历史。
<hr class="help">
</span>


<span class="broker admin">
<a name="sms_mailings_results"></a>
<h3>短讯邮件搜索结果</h3>
这页显示短讯邮件搜索的结果。收件人列将显示收邮件的会员或组。其他不解自明。
<hr class="help">
</span>


<span class="broker admin">
<a name="send_sms_mailing"></a>
<h3>发送短讯邮件</h3>
在此视窗中，您可以发送短讯邮件给
</span>
<span class="broker">您的注册用户。</span>
<span class="admin">一个或多个组。</span>
<span class="broker admin"> 
您可以定义邮件是“收费”或“免费”。一个收费邮件意味着
将从用户以本地单位收取，或者如果用户有短讯信贷，将首先使用该信贷。
免费邮件意味着将不会收取用户短讯费用。通常商业邮件将是免费的，其他的组织性邮件可以是收费的<br>
用户可以在
<a href="${pagePrefix}preferences#notifications_preferences"><u>n通知偏好</u></a>
定义他/她会否接收收费和/或免费邮件。
给会员的个别讯息 都是免费的（对用户来说）。
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