<div style="page-break-after: always;">
<a name="top"></a>
<p class="head_description">贷款小组允许会员集体负责贷款。<br>
提供贷款给贷款小组意味着贷款小组的负责会员将收到贷款。所有会员都可以查看和偿还贷款。</p>
<i>在哪里可以找到它？</i>
<br>
贷款小组可通过
<span class="admin">&quot;菜单：用户和组别：贷款小组&quot; 进入。<br>
要查看此项目，管理员需要有正确的 <a href="${pagePrefix}groups#manage_group_permissions_admin_member"><u>权限</u></a>（贷款小组区块）。<br><br>

<i>如何使它运作？</i><br>
一旦您新增了贷款小组，您可以增加会员到该小组，如在下面 <a href="#create_loan_group"><u>新增贷款小组</u></a> &quot; 中的描述。<br>
为了提供贷款给贷款小组，贷款必须存在和管理员必须有提供贷款的 <a href="${pagePrefix}groups#manage_group_permissions_admin_member"><u>权限</u></a>（在权限区块的 &quot;贷款&quot;）。<br>
附加的 <a href="${pagePrefix}groups#edit_member_group"><u>贷款小组设置</u></a>，可以为每个会员小组定义。<br>
要提供贷款，您可以通过 &quot;菜单：用户和组别：贷款小组&quot; > 修改贷款小组 > 提供贷款，
或通过 <a href="${pagePrefix}profiles#actions_for_member_by_admin"><u>（贷款区块 > 发放贷款）</u></a>。</span>


<span class="member">&quot;菜单：账户 > 贷款小组&quot;。</span>
<hr>

<span class="admin">
<a NAME="search_loans_group"></a>
<h3>搜索贷款小组</h3>
这是 <a href="#top"><u>贷款小组</u></a> 的搜索页面。<br>
您可以利用贷款小组名称、描述或属于该小组的会员（按会员的用户名或会员的真正名称）来搜索。
<br><br>当您填写需要的字段后，您应该点击 &quot;搜索&quot;，小组将会显示在下面的 <a href="#search_loans_group_result"><u>搜索结果视窗</u></a>。
<br><br>
点击 &quot;<a href="#create_loan_group"><u>新增贷款小组</u></a>&quot; 按钮可以创建新的贷款小组。
<hr class="help">
</span>

<span class="admin">
<a name="create_loan_group"></a>
<h3>新增贷款小组</h3>
在此视窗中，您可以新增一个贷款小组。只要输入新小组的名称和描述，并点击 &quot;提交&quot;。
<br><br>新增的小组将显示在您的下一个视窗，即是 <a href="#search_loans_group_result"><u>搜索贷款结果</u></a> 视窗。
在首次新增时，该小组仍然是空的。您可以通过修改贷款小组来加入该小组的会员（在搜索贷款结果视窗，单击 <img border="0" src="${images}/edit.gif" width="16" height="16">&nbsp;图标）。
<hr class="help">
</span>

<span class="admin">
<a name="search_loans_group_result"></a>
<h3>搜索贷款小组结果</h3>
这里是 <a href="#search_loans_group"><u>搜索贷款小组</u></a> 的结果，显示该 <a href="#top"><u>贷款小组</u></a> 的名称和描述。此外，您可以使用
<img border="0" src="${images}/edit.gif" width="16" height="16">&nbsp;编辑图标来进入一个视窗；在这里，您可以修改贷款小组属性、加入会员或管理小组的贷款。
<br><br>您可以使用
<img border="0" src="${images}/delete.gif" width="16" height="16">
&nbsp;删除图标来删除小组。这只限于该小组并没有分配到任何开启的贷款。
<hr class="help">
</span>

<span class="admin">
<a name="loan_group_members_by_admin"></a>
<h3>贷款小组会员</h3>
这视窗显示在 <a href="#top"><u>贷款小组</u></a> 中的会员，并显示会员的名称和用户名。<br>
要删除小组会员，请单击删除 <img border="0" src="${images}/delete.gif" width="16" height="16">&nbsp;图标。<br>
要新增小组会员，请在编辑框输入用户名或名称（名称是自动完成的），并点击 &quot;新增&quot; 按钮。
<hr class="help">
</span>

<span class="admin">
<a name="loan_group_detail_by_admin"></a>
<h3>修改贷款小组</h3>
这个视窗提供有关 <a href="#top"><u>贷款小组</u></a> 的资料。您可以执行以下行动：
<ul>
	<li><b>更改：</b>点击这个按钮来更改名称或描述。完成更改后，请点击 &quot;提交&quot; 来储存更改。
	<li><b>查看贷款：</b> 点击此按钮以得到这小组现有的 <a href="${pagePrefix}loans"><u>贷款</u></a> 概况。
	<li><b>提供贷款：</b> 点击此按钮给小组提供贷款，该小组的负责会员将收到贷款。如果已设置权限，小组的所有会员都可以查看和偿还贷款。
</ul>
<br><br>
<font color="#FF0000">注意：</font> 当通过会员管理职能访问贷款小组，管理员将只有 &quot;查看&quot; 权限。
这里所说的行动，只能直接从贷款小组管理页面（&quot;菜单：用户和组别 > 贷款小组&quot;）完成。
<hr class="help">
</span>

<a name="search_loans"></a>
<h3>小组...的贷款</h3>
在这里，您可以得到 <a href="#top"><u>贷款小组</u></a> 的贷款概况。表格非常简单：您只能选择其中一个单选按钮，以查看 &quot;开启&quot; 或 &quot;关闭&quot; 的贷款。
<hr class="help">

<span class="member">
<a NAME="member_loan_groups_by_member"></a>
<h3>我的贷款小组</h3>
在这里，可以看到您所属的 <a href="#top"><u>贷款小组</u></a>。要查看更多有关贷款小组的资料，请点击 <img border="0" src="${images}/view.gif" width="16" height="16">&nbsp;查看图标。
<hr class="help">
</span>

<span class="admin">
<a NAME="member_loan_groups_by_admin"></a>
<h3>会员的贷款小组</h3>
在这里，您可以看到会员所属的 <a href="#top"><u>贷款小组</u></a>。要查看更多关贷款小组的资料，请点击
<img border="0" src="${images}/view.gif" width="16" height="16">&nbsp;查看图标。<br>
要取消贷款小组，请点击<img border="0" src="${images}/delete.gif" width="16" height="16">&nbsp;删除图标。这只限于该贷款小组没有任何开启的贷款。<p>
请注意，您无法在这里查看贷款。您应该到 会员个人资料 > 查看贷款，或进入 &quot;菜单：账户 > 管理贷款&quot; 查看。
<hr class="help">
</span>

<span class="member">
<a name="loan_group_detail_by_member"></a>
<h3>贷款小组详情</h3>
这里显示贷款小组的名称和描述。<br>
这小组的会员都显示在 <a href="#loan_group_members_by_member"><u>下面的视窗</u></a>。
<hr class="help">
</span>

<span class="admin">
<a name="add_member_loan_groups"></a>
<h3>新增贷款小组会员</h3>
您可以使用这个视窗来增加 <a href="#top"><u>贷款小组</u></a> 的会员。一名会员可以属于多个贷款小组。表格非常简单：只需选择贷款小组，并点击 &quot;提交&quot; 按钮。
<hr class="help">
</span>

<span class="member">
<a NAME="loan_group_members_by_member"></a>
<h3>贷款小组会员</h3>
这里显示这贷款小组会员的真正名称和用户名。您可以点击它们以进入他们的 <a href="${pagePrefix}profiles"><u>个人资料</u></a>。
您无法在这里查看自己的贷款（或给予贷款小组的贷款）。因此，您应该访问 &quot;菜单：账户 > 贷款&quot;，这也将显示您作为会员所属的贷款小组的贷款。
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