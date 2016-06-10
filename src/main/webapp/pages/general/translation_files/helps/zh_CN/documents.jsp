<div style="page-break-after: always;">
<a name="top"></a>
<p class="head_description">文件是可以显示在联环系统的会员章节中具有资料的页面。会员可以从列表中选择文件。
<br><br>
有两种文件类型：&quot;静态&quot; 和 &quot;动态&quot; 文件。<br>
<ul>
	<li><b>静态</b>文件只是档案，就像一个 pdf 档案，可以被分配给个别的会员或会员组别。</li>
	<li><b>动态</b>文件是 HTML 文件，可以被分配到一个或多个组别；这些文件对于会员可能做到个人化，因为它可以从查看它的会员的个人资料取出字段。</p></li>
</ul>
<span class="admin">
一个典型的自定文件，是一个贷款合同，或任何种类的请求文件，会员可使用这些文件来要求管理员做某些东西。<br>
当会员开启动态文件，它可以直接显示出来。与此对比，也可以首先显示会员需要填写的表格；当会员提交表格后，结果文件可以包括会员的输入，以及会员的个人资料字段。

<br><br><i>在哪里可以找到它？</i><br>
文件可以通过 &quot;菜单：内容管理 > 文件&quot; 进入。新增动态文件的例子可以在联环系统的 wiki 找到，它在 &quot;配置-自定文件&quot; 下面。
<br>
现有的个人会员文件可从会员（&quot;会员资料&quot; 区块）的 <a href="${pagePrefix}profiles"><u>个人资料</u></a> 达到。

<br><br><i>如何使它运作？</i><br>
在您新增文件之前，您首先将需要设置 <a href="${pagePrefix}groups#manage_group_permissions_admin_member"><u>权限</u></a>。
这是可以通过几个在 &quot;文件&quot; 区块下面的复选框做到。一旦有了这些权限，您可以通过 &quot;菜单：内容管理 > 文件&quot; 来新增文件。<br>
<br>
<b>注1：</b>对于每个已新增文件，其可见性要在 &quot;文件&quot; 区块的组别 <a href="${pagePrefix}groups#manage_group_permissions_member"><u>权限</u></a> 中设置。
因此，这意味着文件是被指定到某些会员组别。可以设置文件只供管理员查看，只有管理员和经纪可查看，和只有管理员、经纪和会员自己（会员可永远看不到对方的文件）可查看。<br><br>
<b>注2：</b> 联环系统不存在管理员文件这样的东西。
</span>
<span class="member">
<i>在哪里可以找到它？</i><br>
文件可以在 &quot;菜单：个人 > 文件&quot; 查看。
</span>
<hr>

<span class="admin"> <a name="document_list"></a>
<h3>自定文件名单</h3>
此页面显示了自定 <a href="#top"><u>文件</u></a> 的名单，该些文件到现在为止在系统已定义。除了文件名称外，名单还显示如下：
<ul>
	<li><b>类型：</b> 显示文件的 <a href="#top"><u>类型</u></a>。
	<li><img border="0" src="${images}/edit.gif" width="16" height="16">&nbsp;
	单击修改图标以修改文件。
	<li><img border="0" src="${images}/view.gif" width="16" height="16">&nbsp;
	单击查看图标以查看结果。
	<li><img border="0" src="${images}/delete.gif" width="16" height="16">&nbsp;
	单击删除图标以删除文件。
</ul>
要增加一个新的文件，您应该使用在视窗底部的两个按钮（&quot;新的动态文件&quot; 或 &quot;新的静态文件&quot;）之其中一个。
<hr class="help">
</span>

<span class="admin"> <a name="new_edit_static_document"></a>
<h3>新增/修改新的静态文件</h3>
这让您增加一个 <a href="#top"><u>新的静态文件</u></a>。表格非常简单：只要输入名称和文件的描述，并在修改 &quot;上载档案&quot; 中填写档案名称。您可能想使用 &quot;浏览&quot; 按钮来处理。<br>
完成后，点击 &quot;提交&quot; 按钮以储存该文件。
<br><br>文件档案可以是任何格式。如果您选择更改现有的档案，现时的档案被放在 &quot;当前文件&quot; 的链接，您可以点击此链接查看当前版本的文件。
<br><br><b>请注意</b>：只是新增文件并不意味着您的会员/用户可以查看它。文件新增后，您应该设置 <a
	href="${pagePrefix}groups#manage_group_permissions_member"><u>会员权限</u></a> 以查看文件，这是通过在权限文件章节下的下拉框来选择新的文件。
<hr class="help">
</span>


<span class="admin"> <a name="new_edit_dynamic_document"></a>
<h3>新增/修改新的动态文件</h3>
这让您增加一个 <a href="#top"><u>新的动态文件</u></a>。表格有以下部分：
<ul>
	<li><b>名称：</b> 文件的名称。
	<li><b>描述：</b> 文件的描述（只限管理员使用）。
	<li><b>表格页面：</b> 可以在打印文件以前，首先需要用户的一些输入。在此页面上您可以用表格来写HTML页面，以请求需要的用户输入。如果您不需要这些用户的输入，可以把它留空白。
	<li><b>文件页面：</b> 在这里，您可以用HTML格式来写文件的页面。如果您在以上的修改框中定义了表格页面，那么您可以从该页面包括用户的输入。文件页面将开启在弹出的视窗，并有打印和关闭按钮。您也可以新增图片，首先将需要在 &quot;<a
		href="${pagePrefix}content_management#custom_images"><u>自订图片</u></a>&quot; 章节中上载它们。
</ul>
<b>注：</b>在联环系统的 wiki 有提供动态文件的例子，它在 &quot;设置-自定文件&quot; 下面。见 
<a href="http://project.cyclos.org/wiki">project.cyclos.org/wiki</a>。
新增文件后，您应该设置 <a href="${pagePrefix}groups#manage_group_permissions_member"><u>会员权限</u></a> 以查看文件，这是通过在权限文件章节下的下拉框来选择新的文件。
<hr class="help">
</span>

<a name="member_document"></a>
<h3>下载文件</h3>
此视窗显示一个管理员提供给会员的文件名单，这些文件可以下载和打印的。
<p>文件通常是组织的文件。如果管理员指定，文件首先可以显示一个需要您一些额外输入的表格，它们将会被包括在文件中。
<span class="broker admin">
对于管理员和经纪，文件的类型也会列出。静态和动态文件只能从这个视窗看见（您应该到
&quot;菜单：内容管理 > 文件&quot; 来管理它们）；但是会员文件是在这个视窗管理的。在这种情况下，您有下列选项：
<ul>
	<li><img border="0" src="${images}/view.gif" width="16" height="16">&nbsp;让您查看文件
	<li><img border="0" src="${images}/edit.gif" width="16" height="16">&nbsp;让您修改文件
	<li><img border="0" src="${images}/delete.gif" width="16" height="16">&nbsp;让您删除文件
</ul>
</span><hr class="help">

<span class="broker admin"> <a name="edit_member_document"></a>
<h3>新增/修改会员文件</h3>
用这个视窗，您可以为个别会员定义一个新的 &quot;静态&quot; 文件。这可以是任何的档案类型，如 PDF 或图片。如果要修改，您只需写在以前文件上面，首先点击
&quot;更改&quot; 按钮以进行更改；完成后，使用
&quot;提交&quot; 按钮来储存更改。
<ul>
	<li><b>名称：</b> 只要给一个描述的名称
	<li><b>描述：</b> 只有管理员看到
	<li><b>可见性：</b> 在这里，您可以选择哪些用户类型可看到这文件。如果您选择 &quot;会员&quot;，会员可以看见该文件。
	如果选择经纪，只有经纪（和有权限的管理员）可以看到该文件。明显地，如果选择
	&quot;管理员&quot;，只有管理员可以看见该文件。
	<li><b>当前文件：</b> 是目前的文件档案。您可以点击链接查看。如果您使用这个视窗以新增一个会员文件，这将不可见。
	<li><b>上传文件：</b> 只要在这里输入文件名并有完整路径。您可能想使用 &quot;浏览&quot; 按钮来处理。
</ul>
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