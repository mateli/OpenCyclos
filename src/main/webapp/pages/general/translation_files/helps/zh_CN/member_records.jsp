<div style="page-break-after: always;">
<a NAME="top"></a>
<p class="head_description">会员记录是有关个别会员所有的用户自定资料。有许多定义和管理会员记录的特色。<br>
您可以使用会员记录来储存有关会员在一对多基础上的资料（即：一名会员可以有许多不同的数据值储存在他的 &quot;会员记录&quot;）。
<span class="admin">
这是会员记录与 <a href="${pagePrefix}custom_fields"><u>自定字段</u></a> 的分别；
您可以给会员附上自定定义的资料，但在自定字段的情况下，一名会员在自己的自定字段只能有一个数据值，而不是多个。<br>
<br>
您可以在会员记录中定义多种不同的类型和字段。一个简单的例子是联环系统以前的版本有的会员 &quot;备注&quot;。
备注只是一个可加进会员中的文本字段列表。现在可以在纪录中增加更多的字段类型，而且它们可以有不同的方式显示出来（如后的说明。）
</span>
</p>
<span class="admin"><p>这本手册有一个您可能想要查核的小型 <a href="#guidelines"><u>指引</u></a>
菜单，并配备了一个 <a href="#example"><u>例子</u></a> 使会员记录的使用更具解说性。
<p><i>在哪里可以找到它？</i><br>
会员记录可通过会员的 <a href="${pagePrefix}profiles"><u>个人资料</u></a> > &quot;会员资料&quot; 区块进入。
您可以通过 &quot;菜单：用户和组别 > 会员记录类型&quot; 来设置会员记录类型。
<br><br>
<i>如何使它运作？</i><br>
请查看我们有关的 <a href="#guidelines"><u>指引</u></a>。
</span>
<span class="broker">
<br><br>这些会员记录是每个会员相关的附加资料。您可能可以查看、新增、修改或删除这些记录，这取决于管理员所设置的权限。
<br><br><i>在哪里可以找到它？</i><br>
会员记录可通过会员的 <a href="${pagePrefix}profiles"><u>个人资料</u></a> 进入。会员记录类型将在 &quot;经纪行动为...&quot; 下面列出。<br>
在菜单的 &quot;经纪&quot; 中还有搜索每个记录类型的会员记录。例如：默认的数据库有一个称为
&quot;备注&quot; 的记录类型，并将显示在经纪菜单。在这里，您可以 <a href="#search_member_records"><u>搜索</u>记录。
</span>
<hr>

<span class="admin"> <a name="guidelines"></a>
<h3>定义会员记录的指引</h3>
要新增会员记录，必须执行下列步骤：
<ol>
	<li>首先，想清楚您想要的会员记录，您希望储存什么资料？一个简单的
	<a href="${pagePrefix}custom_fields"><u>自定字段</u></a> 能否做到您的要求？
	<li>如果您想新增一个会员记录类型，您需要权限。
	这可以在管理 <a href="${pagePrefix}groups#manage_group_permissions_admin_system"><u>权限</u></a> 中的 &quot;会员记录类型&quot; 区块下面找到。
	您将需要拣选 &quot;管理&quot; 复选框。
	<li>之后，您可以新增一个会员记录类型，通过 &quot;菜单：用户和组别 > <a href="#member_record_types_list"><u>会员记录</u></a>&quot; 节的 &quot;新增会员记录类型&quot; 按钮。
	<li>当您已经新增了会员记录类型，在 <a href="#member_record_type_fields_list"><u>下一视窗中</u></a>，您会有可能增加字段到会员记录类型。
	您必须在每个会员记录类型增加最少一个字段，否则该类型不能包含任何资料，并将完全无用。
	对于某些字段，您也需要新增可能值（见 <a href="#example"><u>范例</u></a>）。
	<li><b>设置权限：</b> 当新增了记录类型，您需要定义谁可以查看、修改和删除会员记录，通过 &quot;菜单：用户和组别 > 权限组别&quot; 节的
	&quot;会员记录&quot;区块。您可以为管理员组别和经纪组别设置权限。
	<li>会员记录按钮将会显示在 <a href="${pagePrefix}profiles#actions_for_member_by_admin"><u>...的行动</u></a> 视窗中 &quot;会员资料&quot; 节内的会员 <a
		href="${pagePrefix}profiles"><u>个人资料</u></a> 下面。这将只有经纪或管理员可看见。在这里，您可以任意增加您想要的会员记录项目。
	<li>如果会员记录配置已拣选 &quot;显示项目单&quot; 的选项，您可以通过主页 &quot;菜单：用户和组别&quot; 来搜索这会员记录的数据值。
	<li><b>搜索：</b> 可以搜索所有会员的记录，通过在会员个人资料下方的
	&quot;行动&quot; 页面。您也可以从菜单中搜索所有会员的记录（不只限于一个会员）。
</ol>
<hr class="help">

<a name="example"></a>
<h3>会员记录范例</h3>
帮助说明将参照一个特定的会员记录类型范例，以便对会员记录的概念有更好的理解。该范例只是一个简单的例子，无疑，这会员记录类型的配置会有更好的可能。
<ol>
	<li><b>思考您想得到什么：</b> 在这例子中，我们将新增一个会员记录类型，它被称为“求助记录”以记录会员致电服务台的时间次数和问题的种类。<br>
	<br>
	<li><b>设置权限：</b> <a href="${pagePrefix}groups#manage_groups"><u>设置权限</u></a> 非常简单，可以通过 &quot;菜单：用户和组别 > 权限组别&quot;，并参照这些视窗的帮助来完成。<br>
	<br>
	<li><b>新增会员记录类型：</b> 在 &quot;菜单：用户和组别 > <a href="#member_record_types_list"><u>会员记录</u></a>&quot;，您必须点击 &quot;新增会员记录类型&quot; 按钮。并在下面的 <a
		href="#edit_member_record_type"><u>视窗</u></a>，填写以下内容：
	<ul>
		<li><b>名称：</b> &quot;求助记录&quot;
		<li><b>标签：</b> 这将成为 &quot;求助记录&quot;。
		<li><b>组别：</b> 选择您要使用的新会员记录类型所属的会员组别，例如：&quot;正式会员&quot;。
		<li><b>搜索结果铺排：</b> 由于我们不把它用作任何数值分析，我们将只选择 &quot;平铺&quot;。
		<li><b>显示项目单：</b>这意指记录类型将会显示在菜单主页 &quot;菜单：用户和组别&quot;。在这页面，您可以使用各种搜索选项来搜索数据值记录。
		<li><b>可修改：</b> 在项目新增后，是没有需要更改它们的，所以我们不会拣选这个。
	</ul>
	之后，我们点击 &quot;提交&quot; 以新增会员记录类型。它将会显示在我们的 <a href="#member_record_types_list"><u>会员记录类型</u></a> 概况。<br>
	<br>
	<li><b>新增会员记录类型字段：</b> 您必须在会员记录中新增字段，否则，新纪录没有意义。
	在 <a href="#member_record_types_list"><u>会员记录类型</u></a> 概况，您应该点击 <img border="0" src="${images}/edit.gif" width="16"
	height="16">&nbsp;编辑图标，带您到 <a href="#edit_member_record_type"><u>修改会员记录类型</u></a> 视窗。
	为我们需要新增的每个字段，点击 &quot;新增自定字段&quot; 按钮。这将把我们带到 <a href="#member_record_type_fields_list"><u>会员记录类型字段列表</u></a> 的视窗。<br>
	虽然这些字段有点无聊，但它只是作为一个范例。<br>
	<ul>
		<li><b>日期字段：</b> 点击在 &quot;会员记录类型字段列表&quot; 中的 &quot;新增自定字段&quot; 按钮后，把我们带到新增 <a
			href="${pagePrefix}custom_fields#edit_custom_fields"><u>自定字段</u></a> 的视窗。
			在这里，我们填写以下内容（对于新字段的职能运作，没有列出的表格字段是不必要的）：
		<ul>
			<li><b>名称：</b> &quot;日期&quot;
			<li><b>数据类型：</b> &quot;日期&quot;
		</ul>
		填写表格的其它字段，并点击 &quot;提交&quot; 来储存。<br>
		<b>注意：</b> 事实上，新增日期字段不是必要的，因为联环系统会自动保存每个会员记录条目的新增日期，所以您可以进行搜索。
		<li><b>类型字段：</b> 再次点击在 &quot;会员记录类型字段列表&quot; 中的 &quot;新增自定字段&quot; 按钮。现在填写以下内容：
		<ul>
			<li><b>名称：</b> &quot;类型&quot;
			<li><b>数据类型：</b> &quot;列举值&quot;
			<li><b>字段类型：</b> &quot;单选按钮&quot;
			<li><b>必须填：</b> 应拣选。
		</ul>
		点击提交后，您将会再次看到 &quot;会员记录类型字段列表&quot; 视窗。现在，您仍然要为这新字段定义可能值，在现时列出的 &quot;类型&quot; 字段，点击 <img
		border="0" src="${images}/edit.gif" width="16" height="16">&nbsp;编辑图标。<br>
		这将带您返回到 &quot;修改自定字段&quot; 的表格。在这下面，请点击 &quot;新的可能值&quot; 按钮来输入新的值
		&quot;投诉其他会员&quot;，&quot;不明白联环系统&quot;
		和 &quot;要说出他快乐&quot;。当完成后，这些值应该会出现在 <a href="${pagePrefix}custom_fields#possible_values"><u>可能值</u></a> 列表。
		最后，点击 &quot;返回&quot; 以返回到字段概况。
		<li><b>备注字段：</b> 最后我们将以相同的方式来新增备注字段：
		<ul>
			<li><b>名称：</b> &quot;备注&quot;
			<li><b>数据类型：</b> &quot;文字串&quot;
			<li><b>字段类型：</b> &quot;丰富文本编辑器&quot;
		</ul>
		现在，新增记录类型已完成。它将不会在主菜单中看得见，直到您登出并再次登入。
	</ul>
	<li><b>新增数据：</b> 现在您可以开始使用会员记录。在 <a
		href="${pagePrefix}profiles#actions_for_member_by_admin"><u>...的行动</u></a>
		视窗中，&quot;会员资料&quot; 节内的会员个人资料下面，将有一个特殊的 &quot;求助记录&quot; 按钮，
		它将带您到输入新会员记录数据的视窗。
	<li><b>您可以搜索会员记录的数据</b>，通过 &quot;菜单：用户和组别 > 求助记录&quot;。
</ol>
<hr class="help">
</span>


<span class="admin">
<a NAME="member_records"></a>
<h3>修改会员记录</h3>
此视窗显示会员记录类型 <a href="#edit_member_record_type"><u>列表</u></a> 的 <a href="#top"><u>会员记录</u></a> 数据。<br>
它显示新增纪录的用户、新增的时间，和在 &quot;（菜单：用户和组别 > <a href="#member_record_types_list"><u>会员记录类型</u></a>）&quot; 中所定义的自定字段内容。
如果记录字段定义为 &quot;可修改&quot;，也会提供修改记录数据的选项。<br>
如果您有权限，您也可以新增记录，在标记为 &quot;新增...&quot; 页面的下方，点击 &quot;提交&quot; 按钮。
<hr class="help">
</span>

<span class="broker">
<a NAME="member_records"></a>
<h3>修改会员记录</h3>
此视窗显示 <a href="#top"><u>会员记录</u></a> 数据。一些会员记录类型是可修改的。
可修改的会员记录可以通过选择 &quot;更改&quot; 按钮，然后修改字段，并点击 &quot;提交&quot; 按钮来进行修改。<br>
如果您有权限，您也可以新增记录，在标记为 &quot;新增...&quot; 页面的下方，点击 &quot;提交&quot; 按钮。
<hr class="help">
</span>

<span class="admin"> <a NAME="member_record_types_list"></a>
<h3>会员记录类型列表</h3>
此视窗显示可用的 <a href="#top"><u>会员记录</u></a> 类型。
<ul>
	<li>点击 <img border="0" src="${images}/edit.gif" width="16"
		height="16">&nbsp;编辑图标，可以修改会员记录类型属性。
	<li>点击 <img border="0" src="${images}/delete.gif"
		width="16" height="16">&nbsp;删除图标，可以删除会员记录类型。
		请注意，这只可以删除直到现在尚未曾使用的会员记录类型；一旦会员在这会员记录类型有资料，便不能删除这个会员记录类型。
	<li>点击在 &quot;新增会员记录类型&quot; 旁边的 &quot;提交&quot; 按钮，可以新增会员记录类型。
	如果您想新增会员记录类型，您可能要参照这小型 <a href="#guidelines"><u>指引</u></a>。
</ul>
<hr class="help">
</span>

<span class="admin broker"> <a NAME="remarks"></a>
<h3>平铺会员记录</h3>
您在这视窗上可以输入的资料是平铺的 &quot;<a href="#top"><u>会员记录</u></a>&quot;。
在大多数情况下，这是为了可能在会员个人资料中新增资料而定义的。字段是管理员定义的。
要新增记录，请填写字段（有红色星号的字段是必须填的），然后点击 &quot;提交&quot; 按钮。会员的现有记录显示在较低的位置。
<hr class="help">
</span>

<span class="admin broker"> <a NAME="search_member_records"></a>
<h3>搜索会员记录</h3>
在这里，您可以通过会员记录来进行搜索，首先填写字段，然后点击 &quot;搜索&quot;。
<ul>
	<li><b>关键词：</b> 让您搜索 <a href="#top"><u>会员记录</u></a> 类型的任何字段。
	<li><b>用户名：</b> 和..
	<li><b>会员：</b> 让您使用它们所属的用户来搜索记录。
	<li><b>新增日期：</b> 让您使用它们的新增日期来搜索记录。每个会员记录类型自动附有新增日期字段，您自己不用新增这字段。
</ul>
除了这些字段外，还有您在会员记录类型中定义的任何自定字段（在 &quot;菜单：用户和组别 > <a href="#member_record_types_list"><u>会员记录类型</u></a>&quot;）。
<hr class="help">
</span>

<span class="admin broker">
<a NAME="search_records_of_member"></a>
<h3>搜索一名会员的会员记录</h3>
在这里，您可以通过这特定的会员记录来进行搜索，首先填写字段，然后点击 &quot;搜索&quot;。
<ul>
	<li><b>关键词：</b> 可用于搜索记录中的任何字段。
	<li><b>新增日期：</b> 是为记录中的每个条目而储存的；是给每个会员记录类型自动新增的字段。
	<li>除了这些字段外，还有管理员定义的与特定会员记录类型有关的自定字段。
	<li>您可以点击 &quot;提交&quot; 按钮，然后在这会员的会员记录中输入新的数据。此按钮标记为 &quot;新增&quot;，并加入会员记录类型名称。
</ul>
<hr class="help">
</span>

<span class="broker admin"> <a NAME="member_records_search_results"></a>
<h3>会员记录搜索结果</h3>
这里是会员记录搜索的结果。用户名和特定的会员记录字段会显示在结果中。
<ul>
	<li>要查看整个记录，点击 <img border="0" src="${images}/view.gif" width="16" height="16">&nbsp;查看图标。
	<li>要修改它，点击 <img border="0" src="${images}/edit.gif" width="16" height="16">&nbsp;编辑图标。
	<li>要删除它，点击 <img border="0" src="${images}/delete.gif" width="16" height="16">&nbsp;删除图标。
</ul>
<hr class="help">
</span>

<span class="admin"> <a NAME="edit_member_record_type"></a>
<h3>修改或新增会员记录类型</h3>
在这里，您可以修改 <a href="#top"><u>会员记录</u></a> 的结构。要修改记录，首先选择 &quot;更改&quot;，然后修改字段，最后点击
&quot;提交&quot;。如果您要新增一个类型，您不需要首先点击
&quot;更改&quot;。要新增类型，您可能要查核这些 <a
	href="#guidelines"><u>指引</u></a> 和我们的 <a href="#example"><u>范例</u></a>：
<ul>
	<li><b>姓名：</b> 是不言自明的。
	<li><b>标签：</b> 是用户界面所使用的，并且大部分时间应是复数形式的会员记录名称。
	<li><b>描述：</b> 是说明记录的目的和意思的文本。您可以在这里随意填写。
	<li><b>组别：</b> 这个下拉框让您选择可以有这会员记录类型的用户组别。如果纪录是属于用户组别的，在会员个人资料下方的 <a
		href="${pagePrefix}profiles#actions_for_member_by_admin"><u>...的行动</u></a> 视窗中，在
		&quot;会员资料&quot; 节内，将会显示一个新会员记录类型的按钮（只有管理员可看见）。
	<li><b>搜索结果铺排：</b> 这让您选择记录数据出现在搜索结果的式样。选项包括：
	<ul>
		<li><b>平铺：</b> 每个项目列出在上一个项目的下面，用行来分隔。这是最适合备注等等。
		<li><b>列表：</b> 项目以表格形式显示，有栏和列。在表格中每个记录是一列。
	</ul>
	<li><b>显示项目单：</b> 如果拣选，在 &quot;用户和组别&quot; 节内的管理界面，将出现有类型名称的项目单。
	您可以使用这项目单来 <a href="#search_member_records"><u>搜索</u></a> 特定数据值的会员记录类型。
	<li><b>可修改：</b> 如果拣选，在（管理员或经纪）新增记录数据后，可以修改它们。如果没有拣选，它们在新增后便不能更改。
	通常情况下，备注类型是不可修改的：当备注已新增，便不能更改它。
</ul>
<p>除了修改记录的属性外，您也应该在 <a href="#member_record_type_fields_list"><u>下面的视窗</u></a> 新增和修改 &quot;自定字段&quot;，
否则您新增的会员记录将是空白的，且毫无意义。
<hr class="help">

<a NAME="member_record_type_fields_list"></a>
<h3>会员记录类型字段列表</h3>
在这里列出会员记录的字段。这些字段是记录数据被储存和编入索引的地方。为了使会员记录有用，必须存在最少一个自定字段。
<ul>
	<li>要新增自定字段，点击 &quot;新增自定字段&quot; 旁边的 &quot;提交&quot;。
	<li>要更改字段显示的次序，点击 &quot;更改字段次序&quot; 旁边的按钮。此按钮只有在可用时才看得见。
	<li>要修改字段，点击 <img border="0" src="${images}/edit.gif" width="16" height="16">&nbsp;编辑图标。
	<li>要删除字段，点击 <img border="0" src="${images}/delete.gif" width="16" height="16">&nbsp;删除图标。
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