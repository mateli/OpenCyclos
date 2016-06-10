<div style="page-break-after: always;">
<span class="admin">
<br><br>
Cyclosは、様々な言語をサポートしており、翻訳を管理できます。エンドユーザーに対して表示されるすべてのテキストは、言語固有のファイル(各言語につき1つ)と、より大きなテキストブロックのための限られた数の静的テキストファイルの中に含まれています。<br> Cyclosには、選択可能な多くの言語が備わっており、設定メニューから少しマウスをクリックするだけで、インストールの言語を変更できます。<br> Cyclosに備わっている翻訳に満足でない場合は、Cyclos内の各々個々のテキストや用語を修正することもできます。このモジュールは、Cyclosシステムから送信されるすべてのメッセージと送信されるすべてのEメール通知を維持するためのセクションも含みます。<i>見つけ方</i><br>
翻訳モジュールには、「メニュー: 翻訳」からアクセスできます。以下のサブメニュー項目が利用可能です:
<ul>
	<li><b><a href="#translation_keys"><u>アプリケーション</u></a>:</b> アプリケーションウィンドウ内に現れるすべての小さなテキスト用語の翻訳を管理できます。
	<li><b><a href="#notifications"><u>通知</u></a>:</b> システムによって送信される通知を管理できます。
	<li><b>Eメール:</b> システムによって送信されるEメールのテキストを管理できます。
	<li><b>インポート/エクスポート:</b> 翻訳ファイルをインポートまたはエクスポートできます。これにより、Cyclosのコミュニティが他のCyclosユーザーと翻訳を共有できます。
</ul>
注1: 連絡先やお知らせページのような静的テキストファイルは、翻訳モジュールから管理されません。それらは<a href="${pagePrefix}content_management"><u>コンテンツ管理</u></a>から管理できます。<br><br> 注2:Cyclosの言語を変更したい場合は、ここではありません。言語は<a href="${pagePrefix}settings#local"><u>メニュー: 設定 &gt; ローカル設定</u></a>内の「国際化」ブロックで変更できます。<br> 
<hr>


<a name="translation_keys"></a>
<h2>翻訳キー</h2>
Cyclosのインタフェース内に現れる任意の短い書かれたテキストは、言語ファイル内に保持されており、各々の言語につき1つ存在します。Cyclosのインタフェースにより、言語ファイル全体を置き換えるか、そのファイル内の各々のキーを個別に編集することができます。アプリケーションページ内に位置する<a href="#key"><u>キー</u></a>が、<a href="#language_file"><u>言語ファイル</u></a>内に見つからなければ、そのページ内に現れるキーは、クエスチョンマークの間に示されます。これはたいていこのように見えます:「???translationMessage.search.showOnlyEmpty???」。 そのような場合、「メニュー: 翻訳 &gt; アプリケーション」から、自分で言語ファイルにキー(とその値)を追加できます。<br><br>ブラウザウィンドウ内に現れる翻訳に満足でない場合は、それを改変できます。以下の手順に従ってください:
<ol>
	<li>(「メニュー: 翻訳 &gt; アプリケーション」から)翻訳用語の検索ページに行き、そこで「値」編集ボックス内に改変したい用語を入力できます。その用語は大文字・小文字の区別がなく、用語の一部分のみを入力するとプログラムが合致するものを探します。結果を表示するには「検索」をクリックしてください。
	<li>結果ページでは、修正アイコン(<img border="0" src="${images}/edit.gif" width="16" height="16">&nbsp;)をクリックして、値を変更してください。
</ol>
<br><br>ファイルからキー/値の組全体を追加または消去することもできます。 しかし、これは少し慎重を要するので、これに関して<a href="#caution"><u>警告の注</u></a>を読みたいかもしれません。
<hr class="help">


<a name="caution"></a>
<h3>言語キーの追加/削除についての警告</h3>
Cyclosのインタフェースにより、<a href="#language_file"><u>言語ファイル</u></a>からの言語<a href="#key"><u>キー</u></a>を修正、追加、または削除できます。それらのキーの修正は安全なアクションですが、キーの追加または削除は少し慎重を要します。それは言語キーについてのシステムがどのように機能するかを、完全に理解してる場合にのみ行うべきです。<br> 削除されたキーは、言語ファイルそれ自体からのみ削除されます。それはアプリケーションページからは削除されません。そのキーがアプリケーションページで未だに使われている場合、次回そのページはただクエスチョンマークに挟まれたキー名のみを表示するでしょう。これはたいていあまり美しくありません(例: &quot;???about.bla.something.title???&quot;)。<br> 逆もまた同様です。つまり、言語ファイルへのキーの追加は、そのキーをどこかで使い始めないと、何の手助けにもなりません。あなたは<a href="${pagePrefix}content_management"><u>アプリケーションページのカスタマイズ</u></a>によってこれを行えます。<br> 非常に希ですが、アップデート後にキーが失われるということも起こり得ます。通常のCyclosのアップデートは、新しい翻訳キーを追加します。 そのような場合は、キーを自分で安全に追加できます。
<hr class="help">


<A NAME="application"></A>
<h3>翻訳キーを検索する</h3>
 このウィンドウでは、<a href="#key"><u>翻訳キー</u></a>を検索できます。<br> 適切な編集ボックス内に、そのキーまたは<a href="#value"><u>値</u></a>を入力してください。その検索は大文字・小文字の区別がなく、用語全体を入力する必要がないことに留意してください。その検索は、部分的に合致するものも含みます。いつものように、何も入力せずにただ「検索」を押すと、結果ボックス内にすべての利用可能なキー/値のペアを返します。<br> 「空の値のみ」チェックボックスを選択すると、翻訳を持たないキーのみが表示されます(それはアップデート後に生じたかもしれません)。<br> 検索結果は、下の<a href="#application_results"><u>検索結果リストウィンドウ</u></a>内に現れます。そのウィンドウでは、キーごとに翻訳を変更可能です。<br><br>新しい翻訳キーを追加することも可能です。そうしたければ、「新しいキーを挿入する」というラベルの付いた送信ボタンをクリックしてください。まず、これに関する<a href="#caution"><u>警告の注</u></a>を読みたいかもしれません。<br><br> 注:Cyclosの言語を変更したい場合は、ここではありません。言語は<a href="${pagePrefix}settings#local"><u>メニュー: 設定 &gt; ローカル設定</u></a>内の「国際化」ブロックで変更できます。<br> 
<hr class="help">


<a name="application_results"></a>
<h3>検索結果(翻訳キー/値の)</h3>
このウィンドウは、<a href="#application"><u>上のウィンドウ</u></a>内で定めたように、検索結果を表示します。<br> このウィンドウでは、キーを選択したり、それを(<img border="0" src="${images}/delete.gif" width="16" height="16">&nbsp; 消去アイコンによって)消去したり、それを(<img border="0" src="${images}/edit.gif" width="16" height="16">&nbsp;編集アイコンによって)編集したりすることができます。複数のキーを消去したければ、チェックボックスで1つ以上を選択できます。それから「選択された項目を削除する」ボタンを使ってください。<br><br> キーの消去は慎重を要するということに注意してください。まず、これについての<a href="#caution"><u>警告の注</u></a>を読みたいかもしれません。 
<hr class="help">


<A NAME="edit_key"></A>
<h3>翻訳キーを修正する</h3>
このウィンドウでは、翻訳<a href="#key"><u>キー</u></a>の<a href="#value"><u>値</u></a>を修正できます。まず「変更」をクリックして変更をし、それから「送信」をクリックして変更内容を保存してください。<br> 複数行を使えますが、たいていそれは無視され、結果は単一の行内に表示されます。
<hr class="help">


<A NAME="insert_key"></A>
<h3>新しい翻訳キー</h3>
ここでは、新しい翻訳<a href="#key"><u>キー</u></a>と<a href="#value"><u>値</u></a>を入力できます。それらを編集ボックス内に入力し、「送信」をクリックしてください。新しい翻訳キーを追加する前に、これについての<a href="#caution"><u>警告の注</u></a>を読むことを勧めます。
<hr class="help">


<A NAME="import_file"></A>
<h3>翻訳ファイルをインポート/エクスポートする</h3>
このウィンドウでは、<a href="#language_file"><u>言語ファイル</u></a>を<a href="#import"><u>インポート</u></a>または<a href="#export"><u>エクスポート</u></a>できます。さらなる情報については、リンクを辿ってください。
<hr class="help">


<a name="import"></a>
<h3>言語ファイルをインポートする</h3>
このウィンドウ内の上の長方形は、新しい<a href="#language_file"><u>言語ファイル</u></a>をインポートするためのものです。これは、たとえばCyclosに新しい言語を追加する時など、稀な場合です。通常のCyclosのアップデートは、新しい翻訳<a href="#key"><u>キー</u></a>を(もしあれば)自動的に追加します。<br><br>まず、「何をするか」を決める必要があります。 4つのオプションがあります:
<ul>
	<li><b>新しいキーのみをインポートする:</b> これは新しいキーをインポートして既存のキーをそのまま残します。
	<li><b>新しいキーと空のキーのみをインポートする:</b> 前の項目と同じですが、今度は空のキー(つまり、おそらく翻訳が完全には終わっていないために値が空であるキー)もインポートします。
	<li><b>新しいキーと修正されたキーをインポートする:</b> これは新しいキーと修正されたキーをインポートします。それは、一部のキーの値を自分で修正した場合、それらは「標準」の値で上書きされるということを意味します。もはや使われていないキーは削除されます。
	<li><b>ファイル全体を置き換える:</b> これはファイル全体を上書きします。
	過去に行われたすべてのカスタマイゼーションは、もちろん失われます。
</ul>
この後、あなたのコンピュータ上にローカルに保存されている翻訳ファイルへと「ブラウズ」して、「送信」をクリックしなければなりません。<br><br>注: インポートするファイルがすべてのキーを含む必要はありません。もちろん、あなたが「ファイル全体を置き換える」を選ぶ場合は別です。その他のすべての場合では、どのくらいの量のキーでも構いません(しかし、それは正しいフォーマットでなければなりません)。<br> ファイル全体を置き換えたければ、確実にファイル全体をアップロードするようにしてください。さもないと、既存のキーを失う危険があります。


<a name="export"></a>
<h3>言語ファイルをエクスポートする</h3>
現在の<a href="#language_file"><u>言語定義</u></a>をエクスポートするためのフォームの部分は、非常に単純です。「プロパティーファイルとしてエクスポートする」というラベルの「送信」ボタンを使ってください。このボタンをクリックすると、ブラウザが引き継ぎ、通常、そのファイルを保存したいかどうか尋ねます。<br> 言語ファイルのエクスポートは稀な場合です。他のCyclosコミュニティのユーザーがあなたのCyclosの翻訳を利用できるようにしたい場合、これを行いたいでしょう。独自の翻訳を作成したら、是非、私たちに連絡して、あなたの翻訳を利用可能にしてください。すると、私たちは、公式のCyclosディストリビューションにあなたの翻訳を加えることができます。連絡先アドレスについては、プロジェクトのサイトを見てください(<a href="http://project.cyclos.org"><u>http://project.cyclos.org</u></a>)。
<hr>


<a name="notifications"></a>
<h2>通知</h2>
Cyclosでは、以下のウィンドウで様々な通知の内容を管理できます。 
<hr class="help">


<A NAME="general_notifications"></A>
<h3>一般的な通知</h3>
このウィンドウは、一般的な<a href="${pagePrefix}notifications"><u>通知</u></a>を表示します。通常、これらは出て行くメールに追加される接頭辞と接尾辞です。編集アイコン(<img border="0" src="${images}/edit.gif" width="16" height="16">&nbsp;)をクリックすることにより、その内容を変更できます。
<hr class="help">


<A NAME="member_notifications"></A>
<h3>メンバー通知</h3>
このウィンドウは、様々な場合にメンバーに送信される<a href="${pagePrefix}notifications"><u>通知</u></a>を示します。編集アイコン(<img border="0" src="${images}/edit.gif" width="16" height="16">&nbsp;)をクリックすることにより、その内容を変更できます。
<hr class="help">


<A NAME="admin_notifications"></A>
<h3>管理者通知</h3>
このウィンドウは、様々な場合に管理者に送信される<a href="${pagePrefix}notifications"><u>通知</u></a>を示します。編集アイコン(<img border="0" src="${images}/edit.gif" width="16" height="16">&nbsp;)をクリックすることにより、その内容を変更できます。
<hr class="help">


<A NAME="edit_notifications"></A>
<h3>通知を編集する</h3>
このウィンドウにより、通知の内容を変更できます。 これを行うには、まず(いつものように)「変更」ボタンをクリックしてください。完了したら、「送信」をクリックすることにより変更点を保存できます。<br> リッチテキストエディタが現れ、それにより、いくつかのレイアウト機能を使えます。修正する通知に応じて、いくつかのフィールドを使えます。
<br><br> <br>通知テキスト内では、以下の#変数#が利用可能です。#..#の間に置かれる#変数名#は、関連する値で置き換えられます。<br>

<h2>一般的な通知</h2>

<table rules="all" frame="border" celspacing="10%" celpadding="10%">
<tr>
	<td><b>設定名</b></td>
	<td><b>変数</b></td>
	<td><b>置き換え後の文字</b></td>
</tr>
<tr>
	<td rowspan="2">仲介人削除済み所見</td>
	<td>#member#</td>
	<td>メンバー実名</td>
</tr>
<tr>
	<td>#login#</td>
	<td>メンバーログイン名</td>
</tr>
<tr>
	<td>メール件名接頭辞, <br> プレーンテキストメール本文接尾辞,<br> HTMLメール本文接尾辞,<br> SMSメッセージ接頭辞
	</td>
	<td>#system_name#</td>
	<td>あなたのシステムの名称</td>
</tr>
</table>
<br>

<h2>メンバー通知</h2>
<table rules="all" frame="border" celspacing="10%" celpadding="10%">
<tr>
	<td><b>設定名</b></td>
	<td><b>変数</b></td>
	<td><b>置き換え後の文字</b></td>
</tr>
<tr>
	<td rowspan="2">仲介期限切れ,<br> 手動で削除された仲介</td>
	<td>#member#</td>
	<td>仲介されるメンバーの実名</td>
</tr>
<tr>
	<td>#login#</td>
	<td>仲介されるメンバーのログイン名</td>
</tr>
<tr>
	<td rowspan="5">新しい委託手数料契約,<br> キャンセルされた委託手数料契約</td>
	<td>#broker#</td>
	<td>仲介人名</td>
</tr>
<tr>
	<td>#start_date#</td>
	<td>新しい契約の開始日</td>
</tr>
<tr>
	<td>#end_date#</td>
	<td>新しい契約の終了日</td>
</tr>
<tr>
	<td>#amount#</td>
	<td>契約額</td>
</tr>
<tr>
	<td>#link#</td>
	<td>さらなる詳細のためにクリックできるリンク</td>
</tr>
<tr>
	<td rowspan="5">承諾された委託手数料契約,<br> 拒否された委託手数料契約</td>
	<td>#member#</td>
	<td>メンバー氏名</td>
</tr>
<tr>
	<td>#start_date#</td>
	<td>新しい契約の開始日</td>
</tr>
<tr>
	<td>#end_date#</td>
	<td>新しい契約の終了日</td>
</tr>
<tr>
	<td>#amount#</td>
	<td>契約額</td>
</tr>
<tr>
	<td>#link#</td>
	<td>さらなる詳細のためにクリックできるリンク</td>
</tr>
<tr>
	<td>低ユニット</td>
	<td>#creditLimit#</td>
	<td>メンバーのクレジット制限</td>
</tr>
<tr>
	<td rowspan="2">広告の有効期限<br> 広告関心</td>
	<td>#title#</td>
	<td>広告の題名</td>
</tr>
<tr>
	<td>#link#</td>
	<td>さらなる詳細のためにクリックできるリンク</td>
</tr>
<tr>
	<td rowspan="3">受け取った請求書</td>
	<td>#member#</td>
	<td>送信メンバーの実名</td>
</tr>
<tr>
	<td>#login#</td>
	<td>送信メンバーのログイン名</td>
</tr>
<tr>
	<td>#link#</td>
	<td>さらなる詳細のためにクリックできるリンク</td>
</tr>
<tr>
	<td rowspan="6">承諾された請求書,<br> キャンセルされた請求書,<br> 拒否された請求書</td>
	<td>#member#</td>
	<td>メンバーの実名</td>
</tr>
<tr>
	<td>#login#</td>
	<td>メンバーのログイン名</td>
</tr>
<tr>
	<td>#date#</td>
	<td>請求書の送信日</td>
</tr>
<tr>
	<td>#amount#</td>
	<td>請求書の額</td>
</tr>
<tr>
	<td>#description#</td>
	<td>請求書説明</td>
</tr>
<tr>
	<td>#link#</td>
	<td>さらなる詳細のためにクリックできるリンク</td>
</tr>
<tr>
	<td rowspan="4">受信した請求書の期限切れ,<br> 送信した請求書の期限切れ</td>
	<td>#member#</td>
	<td>メンバーの実名</td>
</tr>
<tr>
	<td>#login#</td>
	<td>メンバーのログイン名</td>
</tr>
<tr>
	<td>#amount#</td>
	<td>請求書の額</td>
</tr>
<tr>
	<td>#link#</td>
	<td>さらなる詳細のためにクリックできるリンク</td>
</tr>
<tr>
	<td rowspan="3">ローン満期</td>
	<td>#grantDate#</td>
	<td>ローンが認可された日付</td>
</tr>
<tr>
	<td>#amount#</td>
	<td>ローンの額</td>
</tr>
<tr>
	<td>#link#</td>
	<td>さらなる詳細のためにクリックできるリンク</td>
</tr>
<tr>
	<td rowspan="2">ローン認可</td>
	<td>#amount#</td>
	<td>ローンの額</td>
</tr>
<tr>
	<td>#link#</td>
	<td>さらなる詳細のためにクリックできるリンク</td>
</tr>
<tr>
	<td rowspan="6">支払いの受け取り</td>
	<td>#amount#</td>
	<td>額</td>
</tr>
<tr>
	<td>#member#</td>
	<td>関係するメンバーの実名</td>
</tr>
<tr>
	<td>#login#</td>
	<td>メンバーのログイン名</td>
</tr>
<tr>
	<td>#transaction_number#</td>
	<td>取引の数(有効な場合)</td>
</tr>
<tr>
	<td>#balance#</td>
	<td>新しい残高</td>
</tr>
<tr>
	<td>#link#</td>
	<td>さらなる詳細のためにクリックできるリンク</td>
</tr>
<tr>
	<td rowspan="4">認可待ちの支払いの受け取り,<br> 受取人が認可すべき新しい支払い,<br> 支払人が認可すべき新しい支払い,<br> 仲介人が認可すべき新しい支払い</td>
	<td>#amount#</td>
	<td>額</td>
</tr>
<tr>
	<td>#member#</td>
	<td>関係するメンバーの実名</td>
</tr>
<tr>
	<td>#login#</td>
	<td>メンバーのログイン名</td>
</tr>
<tr>
	<td>#link#</td>
	<td>さらなる詳細のためにクリックできるリンク</td>
</tr>
<tr>
	<td rowspan="3">保留中の支払認可,<br> 保留中の支払い拒否,<br> 保留中の支払いキャンセル</td>
	<td>#amount#</td>
	<td>額</td>
</tr>
<tr>
	<td>#date#</td>
	<td>支払い送信の日付</td>
</tr>
<tr>
	<td>#link#</td>
	<td>さらなる詳細のためにクリックできるリンク</td>
</tr>
<tr>
	<td rowspan="4">スケジュールされた支払いの処理成功,<br> スケジュールされた支払いの失敗(支払人へ),<br> 請求書からのスケジュールされた支払いの失敗(支払相手へ)</td>
	<td>#amount#</td>
	<td>額</td>
</tr>
<tr>
	<td>#member#</td>
	<td>関係するメンバーの氏名</td>
</tr>
<tr>
	<td>#login#</td>
	<td>関係するメンバーのログイン名</td>
</tr>
<tr>
	<td>#link#</td>
	<td>さらなる詳細のためにクリックできるリンク</td>
</tr>
<tr>
	<td rowspan="5">外部チャネル支払いの実行</td>
	<td>#amount#</td>
	<td>額</td>
</tr>
<tr>
	<td>#member#</td>
	<td>関係するメンバーの氏名</td>
</tr>
<tr>
	<td>#login#</td>
	<td>関係するメンバーのログイン名</td>
</tr>
<tr>
	<td>#channel#</td>
	<td>チャネル(web, POS, SMSなど)</td>
</tr>
<tr>
	<td>#link#</td>
	<td>さらなる詳細のためにクリックできるリンク</td>
</tr>
<tr>
	<td rowspan="5">外部チャネル支払いリクエストが期限切れした支払人</td>
	<td>#amount#</td>
	<td>額</td>
</tr>
<tr>
	<td>#toMemberName#</td>
	<td>支払いをリクエストしているメンバーの氏名</td>
</tr>
<tr>
	<td>#toMemberUsername#</td>
	<td>リクエストしているメンバーのログイン名</td>
</tr>
<tr>
	<td>#channel#</td>
	<td>チャネル(web, POS, SMSなど)</td>
</tr>
<tr>
	<td>#link#</td>
	<td>さらなる詳細のためにクリックできるリンク</td>
</tr>
<tr>
	<td rowspan="5">外部チャネル支払いリクエストが期限切れした受取人</td>
	<td>#amount#</td>
	<td>額</td>
</tr>
<tr>
	<td>#fromMemberName#</td>
	<td>支払うべきメンバーの氏名</td>
</tr>
<tr>
	<td>#fromMemberUsername#</td>
	<td>支払うべきメンバーのログイン名</td>
</tr>
<tr>
	<td>#channel#</td>
	<td>チャネル(web, POS, SMSなど)</td>
</tr>
<tr>
	<td>#link#</td>
	<td>さらなる詳細のためにクリックできるリンク</td>
</tr>
<tr>
	<td rowspan="3">評価の受け取り</td>
	<td>#member#</td>
	<td>関係するメンバーの氏名</td>
</tr>
<tr>
	<td>#login#</td>
	<td>関係するメンバーのログイン名</td>
</tr>
<tr>
	<td>#link#</td>
	<td>さらなる詳細のためにクリックできるリンク</td>
</tr>
<tr>
	<td rowspan="5">受け取った取引フィードバック,<br> 返信した取引フィードバック,<br> 取引フィードバック管理コメント</td>
	<td>#member#</td>
	<td>関係するメンバーの氏名</td>
</tr>
<tr>
	<td>#login#</td>
	<td>関係するメンバーのログイン名</td>
</tr>
<tr>
	<td>#amount#</td>
	<td>元の支払いの額</td>
</tr>
<tr>
	<td>#date#</td>
	<td>元の支払いの日付</td>
</tr>
<tr>
	<td>#link#</td>
	<td>さらなる詳細のためにクリックできるリンク</td>
</tr>
<tr>
	<td rowspan="3">口座手数料の受け取り</td>
	<td>#account_fee#</td>
	<td>口座手数料の名称</td>
</tr>
<tr>
	<td>#amount#</td>
	<td>元の支払いの額</td>
</tr>
<tr>
	<td>#link#</td>
	<td>さらなる詳細のためにクリックできるリンク</td>
</tr>
<tr>
	<td rowspan="4">発行された新しい証明書</td>
	<td>#amount#</td>
	<td>証明書の額</td>
</tr>
<tr>
	<td>#issuerName#</td>
	<td>real name of the user issueing this</td>
</tr>
<tr>
	<td>#issuerUsername#</td>
	<td>login name of the user issueing this</td>
</tr>
<tr>
	<td>#link#</td>
	<td>さらなる詳細のためにクリックできるリンク</td>
</tr>
<tr>
	<td rowspan="5">変更された証明書状態,<br> 変更された保証書状態(購入者のみ)</td>
	<td>#amount#</td>
	<td>証明書/保証書の額</td>
</tr>
<tr>
	<td>#issuerName#</td>
	<td>これを発行しているユーザーの実名</td>
</tr>
<tr>
	<td>#issuerUsername#</td>
	<td>これを発行しているユーザーのログイン名</td>
</tr>
<tr>
	<td>#status#</td>
	<td>新しい状態</td>
</tr>
<tr>
	<td>#link#</td>
	<td>さらなる詳細のためにクリックできるリンク</td>
</tr>
<tr>
	<td rowspan="4">期限切れの証明書,<br> 期限切れの保証書,<br> 認可待ちの保証書(購入者のみ),<br> 発行された新しい支払債務</td>
	<td>#amount#</td>
	<td>額</td>
</tr>
<tr>
	<td>#buyerName#</td>
	<td>購入しているユーザーの実名</td>
</tr>
<tr>
	<td>#buyerUsername#</td>
	<td>購入しているユーザーのログイン名</td>
</tr>
<tr>
	<td>#link#</td>
	<td>さらなる詳細のためにクリックできるリンク</td>
</tr>
<tr>
	<td rowspan="9">保証書状態の変更</td>
	<td>#amount#</td>
	<td>証明書/保証書の額</td>
</tr>
<tr>
	<td>#status#</td>
	<td>新しい状態</td>
</tr>
<tr>
	<td>#issuerName#</td>
	<td>これを発行しているユーザーの実名</td>
</tr>
<tr>
	<td>#issuerUsername#</td>
	<td>これを発行しているユーザーのログイン名</td>
</tr>
<tr>
	<td>#buyerName#</td>
	<td>購入しているユーザーの実名</td>
</tr>
<tr>
	<td>#buyerUsername#</td>
	<td>購入しているユーザーのログイン名</td>
</tr>
<tr>
	<td>#sellerName#</td>
	<td>販売しているユーザーの実名</td>
</tr>
<tr>
	<td>#sellerUsername#</td>
	<td>販売しているユーザーのログイン名</td>
</tr>
<tr>
	<td>#link#</td>
	<td>さらなる詳細のためにクリックできるリンク</td>
</tr>
<tr>
	<td rowspan="6">認可待ちの保証書</td>
	<td>#amount#</td>
	<td>保証書の額</td>
</tr>
<tr>
	<td>#buyerName#</td>
	<td>購入しているユーザーの実名</td>
</tr>
<tr>
	<td>#buyerUsername#</td>
	<td>購入しているユーザーのログイン名</td>
</tr>
<tr>
	<td>#sellerName#</td>
	<td>販売しているユーザーの実名</td>
</tr>
<tr>
	<td>#sellerUsername#</td>
	<td>販売しているユーザーのログイン名</td>
</tr>
<tr>
	<td>#link#</td>
	<td>さらなる詳細のためにクリックできるリンク</td>
</tr>
<tr>
	<td rowspan="4">却下された支払債務</td>
	<td>#amount#</td>
	<td>額</td>
</tr>
<tr>
	<td>#sellerName#</td>
	<td>販売しているユーザーの実名</td>
</tr>
<tr>
	<td>#sellerUsername#</td>
	<td>販売しているユーザーのログイン名</td>
</tr>
<tr>
	<td>#link#</td>
	<td>さらなる詳細のためにクリックできるリンク</td>
</tr>
</table>
<br>管理者通知</br>
<table rules="all" frame="border" celspacing="10%" celpadding="10%">
<tr>
	<td><b>設定名</b></td>
	<td><b>変数</b></td>
	<td><b>置き換え後の文字</b></td>
</tr>
<tr>
	<td rowspan="6">認可待ちの保証書(購入者のみ)</td>
	<td>#amount#</td>
	<td>額</td>
</tr>
<tr>
	<td>#issuerName#</td>
	<td>これを発行しているユーザーの実名</td>
</tr>
<tr>
	<td>#issuerUsername#</td>
	<td>これを発行しているユーザーのログイン名</td>
</tr>
<tr>
	<td>#buyerName#</td>
	<td>購入しているユーザーの実名</td>
</tr>
<tr>
	<td>#buyerUsername#</td>
	<td>購入しているユーザーのログイン名</td>
</tr>
<tr>
	<td>#link#</td>
	<td>さらなる詳細のためにクリックできるリンク</td>
</tr>
<tr>
	<td rowspan="8">認可待ちの保証書</td>
	<td>#amount#</td>
	<td>額</td>
</tr>
<tr>
	<td>#issuerName#</td>
	<td>これを発行しているユーザーの実名</td>
</tr>
<tr>
	<td>#issuerUsername#</td>
	<td>これを発行しているユーザーのログイン名</td>
</tr>
<tr>
	<td>#buyerName#</td>
	<td>購入しているユーザーの実名</td>
</tr>
<tr>
	<td>#buyerUsername#</td>
	<td>購入しているユーザーのログイン名</td>
</tr>
<tr>
	<td>#sellerName#</td>
	<td>販売しているユーザーの実名</td>
</tr>
<tr>
	<td>#sellerUsername#</td>
	<td>販売しているユーザーのログイン名</td>
</tr>
<tr>
	<td>#link#</td>
	<td>さらなる詳細のためにクリックできるリンク</td>
</tr>
<tr>
	<td rowspan="2">アプリケーションエラー</td>
	<td>#path#</td>
	<td>エラーが生じたパス</td>
</tr>
<tr>
	<td>#link#</td>
	<td>さらなる詳細のためにクリックできるリンク</td>
</tr>
<tr>
	<td rowspan="6">システムからメンバーへの支払い</td>
	<td>#from_account#</td>
	<td>支払元口座保有者の名称</td>
</tr>
<tr>
	<td>#payment_type#</td>
	<td>支払いタイプ</td>
</tr>
<tr>
	<td>#amount#</td>
	<td>額</td>
</tr>
<tr>
	<td>#member#</td>
	<td>支払われたメンバーの実名</td>
</tr>
<tr>
	<td>#login#</td>
	<td>支払われたメンバーのログイン名</td>
</tr>
<tr>
	<td>#link#</td>
	<td>さらなる詳細のためにクリックできるリンク</td>
</tr>
<tr>
	<td rowspan="6">メンバーからシステムへの支払い</td>
	<td>#to_account#</td>
	<td>支払先口座保有者の氏名</td>
</tr>
<tr>
	<td>#payment_type#</td>
	<td>支払いタイプ</td>
</tr>
<tr>
	<td>#amount#</td>
	<td>額</td>
</tr>
<tr>
	<td>#member#</td>
	<td>支払われたメンバーの実名</td>
</tr>
<tr>
	<td>#login#</td>
	<td>支払われたメンバーのログイン名</td>
</tr>
<tr>
	<td>#link#</td>
	<td>さらなる詳細のためにクリックできるリンク</td>
</tr>
<tr>
	<td rowspan="5">システムからシステムへの支払い</td>
	<td>#from_account#</td>
	<td>支払元口座の名称 </td>
</tr>
<tr>
	<td>#to_account#</td>
	<td>支払先口座の名称</td>
</tr>
<tr>
	<td>#payment_type#</td>
	<td>支払いタイプ</td>
</tr>
<tr>
	<td>#amount#</td>
	<td>額</td>
</tr>
<tr>
	<td>#link#</td>
	<td>さらなる詳細のためにクリックできるリンク</td>
</tr>
<tr>
	<td rowspan="4">管理者が認可すべき新しい支払い,<br> システム請求書</td>
	<td>#amount#</td>
	<td>額</td>
</tr>
<tr>
	<td>#member#</td>
	<td>メンバーの実名</td>
</tr>
<tr>
	<td>#login#</td>
	<td>メンバーのログイン名</td>
</tr>
<tr>
	<td>#link#</td>
	<td>さらなる詳細のためにクリックできるリンク</td>
</tr>
<tr>
	<td rowspan="3">新しいメンバーの登録</td>
	<td>#member#</td>
	<td>新しいメンバーの氏名</td>
</tr>
<tr>
	<td>#group#</td>
	<td>新しいメンバーのグループ名</td>
</tr>
<tr>
	<td>#link#</td>
	<td>さらなる詳細のためにクリックできるリンク</td>
</tr>
<tr>
	<td rowspan="2">メンバーアラート</td>
	<td>#member#</td>
	<td>メンバーの実名</td>
</tr>
<tr>
	<td>#login#</td>
	<td>メンバーのログイン名</td>
</tr>
</table>
<hr>


<A NAME="mail_translation"></A>
<h3>メール翻訳</h3>
このウィンドウにより、特定の場合に送信されるメールメッセージの内容を変更できます。これを行うには、まず(いつものように)「変更」をクリックしてください。完了したら、「送信」をクリックすることにより、変更点を保存できます。<br> 現時点では、以下のメールを変更できます:
<ul>
	<li><b>招待メール:</b> 「メニュー: ホーム &gt; 招待」から、<a href="${pagePrefix}home#home_invite"><u>友人を招待する</u></a>オプションを使う時に送信されるメール。
	<li><b>アクティベーションメール:</b> メンバーがアクティベートされた時に送信されるメール。
	これは、登録後、そのメンバーを「<a href="${pagePrefix}groups#inactive_members"><u>保留中のメンバー</u></a>」グループから、別のグループ(通常、完全なメンバー)へと移動することにより、管理者がそのアカウントをアクティベートする時に起こります。
	<li><b>一般登録確認Eメール:</b> 潜在的なメンバーが初めて登録しようとする時に送信されるメール。このメールを送信するには、Cyclosを<a href="${pagePrefix}notifications"><u>このために設定</u></a>しなければなりません。
	<li><b>リセットパスワードメール:</b> 誰かがパスワードをリセットしたい時に送信されるメール。
</ul>
<hr class="help">


<a name="imexport_notifications_mails"></a>
<h3>通知とEメールの翻訳をインポート/エクスポートする</h3>
このウィンドウでは、メールと通知の翻訳済みテキストをファイルからインポート/ファイルにエクスポートできます。ファイルはXMLフォーマットで、この(あるいは将来の)バージョンのCyclosの他のどのインスタンスでも読み込むことができます。<br> Cyclosのインスタンス間で翻訳を共有するため、あるいは、セキュリティ上の理由のために、これを行いたいかもしれません。<br> そのフォームは、非常に分かり易いものです。「インポート」を選ぶ場合、「ブラウズ」ボタンによってファイルを指定してください。ファイルをエクスポートする場合、ブラウザが引き継ぎ、そのダウンロードをどこに保存するかをあなたに尋ねます。
<hr>

<br><br><a name="glossary"></a>
<h2>用語集</h2>
<br><br>

<a name="language_file"></a> <b>言語ファイル</b> <br><br>Cyclos内では、各々の言語について、1つの言語ファイルがあります。このファイルは、Cyclosのインタフェース内に現れる、すべての書かれたテキスト「文字列」を含みます(完全なファイルに備わる大きなブロックのテキストを除く)。<br> 言語ファイルは、常に特定のパターンに従って名付けられています:「ApplicationResources_xx_XX.properties」で、xxは言語コード、XXは国コードによって置き換えられます。例:
「ApplicationResources_en_US.properties」は、US-英語のためのファイルです。<br> 言語ファイルは、<a href="#key"><u>キー</u></a>と<a href="#value"><u>値</u></a>を含みます。 これらは「;」記号によって、スペースなしで区切られています。
<hr class='help'>

<a name="key"></a> <b>翻訳キー</b> <br><br>翻訳キーは、アプリケーションページ内に位置します。それらのページがあなたのブラウザ内で表示される際に、キーは<a href="#language_file"><u>言語ファイル</u></a>内で探し出され、そのファイル内で見つかった<a href="#value"><u>値</u></a>によって置き換えられます。
<hr class='help'>

<a name="value"></a> <b>翻訳値</b> <br><br>翻訳値は、あなたがブラウザ内のCyclosのページ上で見ることになる、あなた自身の言語の単語や用語です。元のページは、これらの値を含みません。その代わりに、翻訳<a href="#key"><u>キー</u></a>はアプリケーションページ内に置かれます。それらのページがあなたのブラウザ内に表示される際に、 キーは<a href="#language_file"><u>言語ファイル</u></a>内で探し出され、そのファイル内で見つかった対応する値によって置き換えられます。<br> すべての翻訳値(アプリケーション翻訳、通知、Eメール)には、「変数」を含められます。変数は、#member#, #title#, #amount#のように、常に2つの#記号によって囲まれています。変数は、Cyclos内で表示される際に、正しい値で置き換えられます。変数名は一目瞭然で、すべての可能な値が標準の翻訳値の中ですべて使われています。
<hr class='help'>

</span>

</div> <%--  page-break end --%>