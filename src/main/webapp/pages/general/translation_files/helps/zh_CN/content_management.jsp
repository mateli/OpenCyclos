<div style="page-break-after: always;">
<span class="admin"> <a name="top"></a>
<p class="head_description">联环系统让您透过修改任何联环系统文件来更改用户的界面。此外，它可以将修改的文件包装为 .theme 主题文件，与其他联环系统用户分享。<br>
这意味着，在实践中，您基本上可以控制一切您或您的会员在浏览器看到的页面：内容和外貌可以更改。</p>
<a name="type_list"></a> 您可以自定以下类型的文件：
<ul>
	<li><b><a href="#statics"><u>静态文件</u></a></b>
	<li><b><a href="#helps"><u>帮助文件</u></a></b>
	<li><b><a href="#jsp"><u>应用页面</u></a></b>
	<li><b><a href="#css"><u>CSS样式表文件</u></a></b>
	<li>除了这些，您还可以自定 <a href="#images"><u>图片</u></a>。
	<li><b><a href="#themes"><u>主题</u></a>：</b> 让您可以切换到其他预先规定的铺排，而无需更改各种文件。
</ul>
<b>重要提示：</b> 请注意，修改这类文件可以是一个复杂的工作，您需要有关CSS样式表和HTML文本的知识。
<p>请注意，您不仅可以在系统级别（在此帮助文件论述）设置自定文件，您还可以在 <a href="${pagePrefix}groups#manage_group_customized_files"><u>组别级别</u></a> 设置自定文件，甚至在 <a
	href="${pagePrefix}groups#manage_group_filter_customized_files"><u>组别筛选器级别</u></a>。
<p><i>在哪里可以找到它？</i><br>
在系统级别的内容管理可以通过主菜单 &quot;内容管理&quot; 到达。<br>
要处理组别级别的内容管理，您应该到 <a
	href="${pagePrefix}groups#manage_groups"><u>管理组别</u></a> 视窗，并点击 <img border="0" src="${images}/edit.gif"
	width="16" height="16">&nbsp;组别修改图标。这个题目涵盖在组别的帮助文件。<br>
要处理组别筛选器级别的内容管理，请到 &quot;菜单：用户和组别 > 组别筛选器&quot;，然后点击 <img border="0"
	src="${images}/edit.gif" width="16" height="16">&nbsp;组别筛选器修改图标。同样，这个题目涵盖在组别的帮助文件。
<p><i>如何使它运作？</i><br>
您将需要设置 <a
	href="${pagePrefix}groups#manage_group_permissions_admin_system"><u>管理员权限</u></a> 以显示内容管理的项目单。有几个项目影响在
&quot;菜单：内容管理&quot; 中项目的可见性，可套用下列区块：
<ul>
	<li>&quot;自定图片&quot;
	<li>&quot;自订系统文件&quot;
	<li>&quot;主题&quot;
</ul>

<hr>

<a name="statics"></a>
<h3>静态文件</h3>
这是应用程序的完整页面，可以由您的组织自定。<br>
要修改这些文件，HTML文本的知识是需要的。小心不要改动HTML标签，并且不修改在应用标签内的内容（标签是在 &lt; 和 &gt; 内的一切）。
<p>本职能允许修改以下静态文件（此列表可能已经过时，因为可能已增加更多文件）：
<ul>
	<li><b>contact.jsp:</b> 页面显示在会员章节中的菜单：&quot;菜单：帮助 > 联系&quot;；应提供资料如何联系您的组织。
	<li><b>general_news.jsp：</b> 一般新闻讯息显示在 <a
		href="${pagePrefix}home#home_news"><u>一般新闻（讯息板）</u></a>视窗，在联环系统会员章节的页面。
	<li><b>login.jsp：</b> 登录页的铺排（登录页的文本可以在翻译职能进行修改）。
	<li><b>member_about.jsp：</b> 页面显示在会员和管理员章节中的菜单：&quot;菜单：帮助 > 关于&quot;。
	<li><b>posweb_footer.jsp：</b> 对外付款组件销售网络的页面注脚（会员可自订）。
	<li><b>posweb_header.jsp：</b> 对外付款组件销售网络的页面标题（会员可自订）。
	<li><b>posweb_login.jsp:</b> 对外付款组件销售网络的登录页标题。
	<li><b>top.jsp：</b> 一般的应用程序标题。
	<li><b>webshop_footer.jsp：</b> 对外付款组件网上商店的页面注脚。
	<li><b>webshop_header.jsp：</b> 对外付款组件网上商店的页面标题。
</ul>
您也可以在静态文件的文字中新增 <a href="#insert_images"><u>图片</u></a>。
<hr class="help">

<a name="helps"></a>
<h3>帮助文件</h3>
您可能需要更改帮助文件，例如您更改了其所属的 <a
	href="#jsp"><u>应用程序文件</u></a>，或万一您认为原来的文本不够明确。<br>
如果您想修改帮助文件，您需要知道它的名称和文件内要跳到的标签。帮助文件是由每一主要题目组织而成的，大约有30个帮助文件，每一个包含一些帮助视窗的文本，通过标签跳到这些视窗文本。<br>
要找到文件名称和标签名称，您要把滑鼠指针逗留在帮助图标上（每个视窗的右上角）。您的浏览器的状态列会显示位置：&quot;帮助：file_name#tag_name&quot;；在这种情况下，该文件将是
&quot;file_name.jsp&quot;，在帮助文件的章节（标签）将被称为 &quot;tag_name&quot;。在帮助文件中，这个标记名称是置于一个 &lt;a name="tag_name"&gt; ...&lt;/a&gt; 标签内。<br>
请注意，您的浏览器可能隐藏状态列的讯息。如果上述状态没有显示帮助文件的位置，您应该更改浏览器的设定，并确定已设置 &quot;显示状态列讯息&quot;。
<p>您也可以在帮助文件的文本新增 <a href="#insert_images"><u>图片</u></a>，但当心图片的大小，因为帮助视窗只有300x400像素。
<hr class="help">

<a name="jsp"></a>
<h3>应用页面</h3>
在联环系统，应用页面是一个 .jsp 文件，它包含用作在页面放置元件的字符，但不包含文本。在实践中这意味着任何不包含帮助文本的 jsp 文件，这些 jsp 文件定义在什么情况下放置您在浏览器中看见的页面。<br>
整个铺排结构可以修改，但它可能会严重影响内部系统和应用程序的运作。因此，请使用判断力来修改应用程序文件。
建议只有小规模的铺排更改才修改它们－例如，您要更改元件的次序，或者在页面中不想见某些元件。
此外，在您这样做之前，请核实如果循正常的联环系统通过管理章节配置程序，您的目标仍无法实现。
<p>为了避免问题，联环系统将永远保留一份 <a
	href="#edit_customized_file"><u>原本页面</u></a> 的副本，它可以很容易地回复原状。</p>
<p>您也可以在 jsp 文件的内容中新增 <a href="#insert_images"><u>图片</u></a>。
<hr class="help">

<a name="css"></a>
<h3>样式表文件</h3>
样式表文件（CSS文件）定义元件在页面中的样子。您可能想要更改它，例如，您想给某些元件另一外貌，如更突出的按钮、更光亮的项目单等。要修改样式表，样式表文件注释的知识是必需的。

<p>联环系统有以下的样式表文件：
<ul>
	<li><b>style.css</b><br>
	联环系统铺排的主要样式表文件（菜单、视窗和顶部）
=	<li><b>login.css</b><br>
	登录页的样式表
	<li><b>mobile.css</b><br>
	手机页面的样式表
	<li><b>posweb.css</b><br>
	销售网络页面的样式表
	<li><b>ieAdjust.css</b><br>
	来解决与Internet Explorer的兼容性问题的方法
</ul>
<p>为了查看样式表文件的修改效果，您可能要在您的浏览器刷新该页面。这可以通过到网址
&quot;www.yourdomain.org/cyclos/pages/styles/style.css&quot; 完成。<br>
样式表的内容将显示为文本。要确保新页面是活跃的，您可以在您的浏览器刷新页面几次。
<p>如果您想使用新的样式表文件，您只需复制全部内容，并把它放在现有的样式表之上（在样式表修改视窗），并如上所述般刷新页面。
<p>如果您已处理一个不错的样式表文件，欢迎您发送给我们，以便我们能够提供给他人。样式表将刊登在SourceForge和项目地点。
<hr class="help">

<a name="insert_images"></a>
<h3>文本文件的图片</h3>
可以在文本文件中新增图片，如在 <a href="#statics"><u>静态文件</u></a> 和 <a href="#helps"><u>帮助</u></a> 页面。
要做到这一点，图片必须提供给应用程序。您可以核对可用的 <a
	href="#system_images"><u>系统图片</u></a>，或者上载自己的图片 &quot;菜单：内容管理 > 自订图片&quot;。要在页面新增图片，以下标签需要放在静态文件的开端：
<p><i>&lt;%@taglib uri=&quot;http://jakarta.apache.org/struts/tags/struts-html&quot; prefix=&quot;html&quot; %&gt;</i>
<p>图片的位置将是这样的：
<p><i>&lt;img src=&quot;&lt;html:rewrite page=&quot;/pages/images/some_image.jpg&quot;/&gt;&quot;&gt;</i>
<p>该页面只需要'html:rewrite'标签作为图片的位置。您可以使用正常的HTML参数，如：border、align、width和height。
<hr class="help">

<a name="customized_files"></a>
<h3>自定文件</h3>
<b>注：</b> 此帮助文件只显示在这种形式工作的一般资料。
您可能想核对 <a href="#type_list"><u>这个列表</u></a>，并为特定的资料而沿着您的文件类型链接，并按照您想要自订的文件类型的提示。
<p>这个视窗显示已 <a href="#top"><u>自定</u></a> 的文件列表。您有以下选项：
<ul>
	<li><b>自订新文件：</b> 要自定一个新的文件，请点击标记为 &quot;自订新文件&quot; 的提交按钮。
	<li><img border="0" src="${images}/preview.gif" width="16"
		height="16">&nbsp;让您预览查看结果。
	<li><img border="0" src="${images}/edit.gif" width="16"
		height="16">&nbsp;让您可以修改自定文件。
	<li><img border="0" src="${images}/delete.gif" width="16"
		height="16">&nbsp;点击删除图标，自定文件会从列表中消失。这意味着，文件的最新修改将继续可见，但在第一次更新联环系统时，默认页面将取代它。
</ul>
	注1：当一个文件名显示为红色，它意味着有一个冲突。如何处理冲突，可以在 <a
	href="#edit_customized_file"><u>修改自定文件</u></a> 帮助页面找到解释。<br>
	注2：当您第一次使用此职能，在您的列表中可能没有自定文件，所以没有可见的图标。这取决于您想要自订的文件类型。
<hr class="help">

<a name="edit_customized_file"></a>
<h3>修改自定文件</h3>
<b>注：</b> 此帮助文件只显示在这种形式工作的一般资料。
您可能想核对 <a href="#type_list"><u>这个列表</u></a>，并为特定的资料而沿着您的文件类型链接，并按照您想要 <a href="#top"><u>自订</u></a> 的文件类型的提示。
<p>在此视窗您可以重新修改一个您之前已自定的文件。像往常一样，点击 &quot;更改&quot; 按钮以开始修改字段，并点击 &quot;提交&quot; 来储存更改。
<ul>
	<li><b>文件名：</b> 显示文件名称。这是无法更改的。
	<li><b>内容：</b> 这里显示文件的现时内容。您可以新增/修改文件的内容。
	您可以使用 HTML 和 XML 标签，这给您大量的可能性，例如：&quot;一般新闻&quot; 页面有工具列，当中有快速链接到联环系统的其它章节。
	此外，可以增加 JavaScript 来定义行为。然而，这是很复杂的程序编制，您应参考 <a
		href="http://project.cyclos.org/wiki/index.php?title=Programming_guide#JSP"><u>联环系统程序编制指南</u></a>。
	<li><b>原本内容：</b> 显示此文件在自定之前的原本内容。如果您从 <a href="#customized_files"><u>列表</u></a> 中删除自定文件，原本内容会再次被套用。
	当然，假使您刚才定义的内容似乎不运作，您也可以复制/贴上 &quot;原本内容&quot; 到 &quot;内容&quot; 框。
	<li><b>新内容：</b> 如果您有更新/升级环联环系统，并有一个新版本的文件，联环系统不会用新内容取代该文件，但会生成一个系统警示。新内容可在这地区显示。
	<li><b>解决冲突：</b> 当有新版本的自定文件，环联系统会生成一个系统警示，并把新版本放在 &quot;新内容&quot; 地区。
	此外，在自定文件列表中，&quot;冲突&quot; 的自定文件名称将显示为红色。<br>
	一旦冲突已经解决和一切都正常运作，您可以选择 &quot;解决冲突&quot; 选项，并储存该文件。
	经过这样做后，文件名称在文件列表中不会出现红色，并且新内容的文件版本将被移到原本内容地区。	
</ul>
<hr class="help">

<a name="edit_new_customized_file"></a>
<h3>自定（新）文件</h3>
在这个视窗您可以选择一个文件以开始 <a href="#top"><u>自定</u></a>。在下拉框的 &quot;选择文件&quot;
中选择您要自定的文件。您可能在此下拉框中直接看到列出的文件，或您可能会看到放置这些文件的目录。如果是目录和子目录，您可以通过目录树的 &quot;选择文件&quot; 的下拉框来选择浏览。
目录将在选择框上面的 &quot;路径&quot; 字段中列出。您可以拣选旁边的 &quot;上移&quot; 链接来移动到较高层的目录。当您到达包含文件的目录，您可以选择该文件，其内容将显示在下面的文本区。<br>
然后，您点击 &quot;更改&quot; 按钮，便可以修改该文件。点击 &quot;提交&quot; 按钮来储存更改。
<p>当您储存一个被更改的自定文件，原本内容将被储存，并显示在自定内容下面。当安装升级版本，联环系统将保留自定文件，但它会核对原本内容和在升级的新文件内容是否有差别。
如果有的话，它将会把新的内容放在原本内容的下面。选择 &quot;解决冲突&quot; 会把新的内容移动到原本内容地区。
<p>当您停止自定一个文件（从列表中删除它），原本内容将被使用。
<hr>

<a name="images"></a>
<p class="sub_description">
<h2>自定图片</h2>
在联环系统中您还可以自定图片。有一整套系统图片，但您也可以上载自己的图片，并在任何自定文件中开始使用它们。
</p>
您可以通过 &quot;菜单：内容管理&quot; 上载您的图片，在那里您可以选择更改 <a href="#system_images"><u>系统图片</u></a>，上载自己的
<a href="#custom_images"><u>自定图片</u></a>，或更改 <a href="#style_images"><u>样式表图片</u></a>。
<hr class="help">

<A NAME="system_images"></A>
<h3>系统图片</h3>
<p>此视窗会列表显示联环系统中现有的系统 <a href="#images"><u>图片</u></a>。如果您点击列表中的图片缩略图，它会在一个弹出视窗显示实际大小的图片。
您可以在下面的 <a href="#images_upload"><u>更新视窗</u></a> 取代系统图片。</p>
<hr class="help">

<A NAME="custom_images"></A>
<h3>自定图片</h3>
<p>此视窗会列表显示联环系统中自定 <a href="#images"><u>图片</u></a>。如果您点击列表中的图片缩略图，它会在一个弹出视窗显示实际大小的图片。
自定图片可用于 <a href="#statics"><u>静态文件</u></a>、<a href="#helps"><u>帮助文件</u></a>，甚至是讯息。
<p>您可以删除图片，请选择 <img border="0" src="${images}/delete.gif" width="16" height="16">&nbsp;删除图标。<br>
您可以在下面的 <a href="#images_upload"><u>更新视窗</u></a> 来新增自定图片。</p>
<hr class="help">

<A NAME="style_images"></A>
<h3>样式表图片</h3>
<p>样式表图片是可用于联环系统视窗铺排的 <a href="#images"><u>图片</u></a>，如：标题、项目单、按钮和背景。您可以在 <a href="#css"><u>样式表文件</u></a> 中使用这些图片。
<p>您可以删除图片，请选择 <img border="0" src="${images}/delete.gif" width="16" height="16">&nbsp;删除图标。<br>
如果您点击列表中的图片缩略图，它会在一个弹出视窗显示实际大小的图片。
您可以在下面的 <a href="#images_upload"><u>更新视窗</u></a> 新增自定图片。</p>
<hr class="help">

<A NAME="images_upload"></A>
<h3>图片上载</h3>
<p>在 <a href="#system_images"><u>系统图片</u></a> 的情况下，您应首先选择哪些图片要取代，通过上述列表的 &quot;上传新图片&quot; 下拉框选择名称。
请注意，当进行上载 <a href="#custom_images"><u>自定图片</u></a> 或 <a href="#style_images"><u>样式表图片</u></a>时，此下拉框将不可见。<br>
然后，点击 &quot;浏览&quot; 按钮，找到您要上载的本地电脑或网络的图片。请确定它有jpg、jpeg、gif 或 png 的延伸。之后，请点击 &quot;提交&quot;，新的图片将出现在上面的视窗列表。</p>
<hr>

<a name="themes"></a>
<p class="sub_description">
<h2>主题</h2>
主题，有时称为 &quot;外壳&quot;，是定义联环系统的铺排（在登录状态顶部列、左面菜单和职能视窗）。主题的职能只是一个快速的方法在铺排之间进行切换，而不需要修改一般的样式表文件和样式表图片。
</p>
主题职可以通过 &quot;菜单：内容管理 > 主题&quot; 找到。


<A NAME="select_theme"></A>
<h3>选择主题</h3>
<p>要选择另一 <a href="#themes"><u>主题</u></a>，您必须从 &quot;主题&quot; 下拉框中选择一个，然后点击标记为 &quot;套用&quot; 的提交按钮。
您必须刷新浏览器以显示主题。在某些情况下您需要清除浏览器的缓存。<br>
请注意，如果您之前没有使用此职能，便没有主题可以用，导致一个空的下拉框。在这种情况下，您必须首先 <a href="#import_theme"><u>导入</u></a> 一个新的主题。
<p>当您点击标记为 &quot;删除&quot; 的提交按钮，主题将被删除，并且自定铺排的修改将被删除。
所以，如果您自定您自己的 <a href="#css"><u>一般样式表</u></a> 或 <a href="#style_images"><u>样式表图片</u></a>，您可能想首先
<a href="#export_theme"><u>导出</u></a> 它作为主题，以便稍后容易再次导入。
主题职能只影响样式表，它并不影响 <a href="#statics"><u>静态文件</u></a> 和 <a href="#helps"><u>帮助文件</u></a>。
<hr class="help">

<A NAME="import_theme"></A>
<h3>导入新的主题</h3>
<p>用这个职能，您可以导入<a href="#themes"><u>主题</u></a>。联环系统主题有一个 .theme 的延伸。
只要使用 &quot;浏览&quot; 按钮，浏览到文件的位置，并点击 &quot;提交&quot;。</p>
<hr class="help">

<A NAME="export_theme"></A>
<h3>导出现时主题设置</h3>
<p>如果您做了自己的 <a href="#themes"><u>主题</u></a>（通过修改 <a href="#css"><u>样式表文件</u></a> 和/或 <a
	href="#style_images"><u>样式表图片</u></a>），您可以导出该主题作为一个 .theme 的文件。<br>
这职能让您导出现时活跃的主题。只需填写字段，并点击下面的 &quot;提交&quot; 按钮。<br>
您也可以通过选择以下的复选框，导出一个子集的主题文件。有三个选项：
<ul>
	<li><b>系统</b>：这些是核心的 <a href="#jsp"><u>.jsp</u></a>
	和 <a href="#css"><u>.css</u></a> 文件</li> 
	<li><b>登录页：</b>这是初始的登录页</li>
	<li><b>手机：</b>这些是手机访问的页面</li>
</ul>
<p><i>如果您发展您自己的主题，请考虑提交给联环系统的发展团队。</i> 我们可以提供给联环系统其它用户。
<hr>


<A NAME="infotexts"></A>
<h2>信息文本</h2> 
信息文本存储在联环系统，可以通过联网服务进行检索。
一个典型的应用是短讯模块。用户可以用短讯发送关键词，例如“推广”，便会收到相应的文本。
信息文本可以在联环系统注册和管理。
<br><br>
信息文本可以有标题和正文。如果它们被用在网站，便更常见。
信息文本所有属性的细节，可见于 <a 
href="${pagePrefix}content_management#infotexts_edit"><u>编辑信息文本</u></a>。
<br><br>
有关如何通过联网服务检索信息文本，可见于 <a
href="http://project.cyclos.org/wiki/index.php?title=Web_services#Info_texts"
target="_blank"><u>联环系统Wiki</u> </a>.
<hr class="help"> 


<A NAME="infotext_search"></A>
<h2>搜索信息文本</h2>
在此页面您可以搜索 <a
href="${pagePrefix}content_management#infotexts"><u>信息文本</u>
</a>，并选择“新增按钮”创建新的信息文本。
<hr class="help">


<A NAME="infotexts_result"></A>
<h2>信息文本的结果</h2>
此页显示搜索结果。
您可以选择“编辑”图标 <img border="0" src="${images}/edit.gif"
width="16" height="16"> 修改信息文本，或删除图标 <img border="0" src="${images}/delete.gif" width="16" height="16">
删除相应的信息文本。
<hr class="help">


<A NAME="infotexts_edit"></A>
<h2>新增/编辑信息文本</h2>
在本页面，您可以定义信息文本的规则和内容。
下面的选项是可用的。
<ul>
	<li><b>关键词：</b>这关键词将用于检索相应的文本字段（标题和正文）。
	容许不止一个键。在这情况下，附加键将作为别名。
	<li><b>标题：</b>这是可以检索的内容文本。
在短文的情况下（如：短讯）这是唯一使用的文本。
	<li><b>正文：</b>对于能够使用标题和正文的渠道，
（例如网站）正文将在这里定义。
	<li><b>有效期：</b>这个设置定义的信息文字是活跃（可以检索）的期段。
	<li><b>启用：</b>在这里您可以快速启用和禁用信息文本。
	在禁用时（未选中），无法检索信息文本。
</ul>


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

</span>