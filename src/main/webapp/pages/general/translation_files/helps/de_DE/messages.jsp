<div style="page-break-after: always;">
<a name="top"></a>
<br><br>Nachrichten in Cyclos können zu vielen unterschiedlichen 
Anlässen und von allen Benutzertypen verwendet werden. Es gibt eine Reihe von flexiblen 
Bestandteilen für das Versenden von Nachrichten zwischen Mitgliedern und Gruppen. Es ist 
außerdem möglich, verschiedene Nachrichtenkategorien zu definieren, so dass bestimmte 
Gruppen Zugang zu bestimmten Nachrichtenkategorien haben.<br>
Nachrichten werden über ein Cyclos-internes System versandt. Dies bedeutet, dass der Empfänger 
die Nachricht sehen kann, sobald er sich anmeldet. Cyclos kann so <a
href="${pagePrefix}preferences#notifications"><u>konfiguriert</u></a> werden, dass diese Nachrichten per 
E-Mail versendet werden, es liegt allerdings am Empfänger, zu entscheiden, ob er dies zulassen 
möchte. Wenn Sie sicher gehen wollen, dass eine Nachricht per E-Mail versandt wird, sollten 
Sie die E-Mail-Funktion verwenden. Dies kann über die Cyclos-Schnittstelle erfolgen, die in 
jedem Mitgliedsprofil eine Schaltfläche &quot;E-Mail senden&quot; enthält.
<i>Wo ist es zu finden?</i><br>
<span class="member">Zugang zu den Nachrichten erhalten Sie über &quot;Menü: Persönlich > Nachrichten&quot;.</span>
<span class="admin">Zugang zu den Nachrichten erhalten Sie über &quot;Menü: Nachrichten > Nachrichten&quot;.</span>
<span class="broker">Um als Broker Nachrichten (an alle Ihre Mitglieder) zu senden, verwenden 
Sie bitte &quot;Menü: Brokering> Nachricht an Mitglieder&quot;.</span>
Ein weiterer Weg, Nachrichten zu versenden, besteht darin, zum <a href="${pagePrefix}profiles">
<u>Profil</u></a> eines Mitglieds zu gehen, und dort  
<span class="admin">im Abschnitt &quot;Mitglied-Informationen&quot;</span> die Schaltfläche 
&quot;Nachricht senden&quot; anzuklicken.

<br><br><i>Wie können Nachrichten aktiviert werden?</i><br>
Um Nachrichten zu versenden, benötigen Sie Berechtigungen. Für Administratoren müssen Sie die
<a href="${pagePrefix}groups#manage_group_permissions_admin_member"><u>Berechtigungen</u></a>
unter dem Abschnitt &quot;Nachrichten&quot; einstellen. Hier haben Sie die Möglichkeit, 
unterschiedliche Berechtigungen für das Versenden von Nachrichten an Mitglieder einzustellen.<br>
Die entsprechenden <a href="${pagePrefix}groups#manage_group_permissions_member">
<u>Berechtigungen für Mitglieder</u></a> im Zusammenhang mit Nachrichten finden Sie unter dem 
Abschnitt mit dem Titel &quot;Nachrichten&quot;.<br>
Broker verfügen über eine Reihe zusätzlicher <a href="${pagePrefix}groups#manage_group_permissions_broker">
<u>Berechtigungen</u></a> in Zusammenhang mit dem Versenden von Nachrichten an die von Ihnen 
betreuten Mitglieder, diese finden Sie unter dem Abschnitt &quot;Persönliche Nachrichten&quot;.
</span>
<hr>

<A NAME="messages_search"></A>
<h3>Nachrichtenliste</h3>
Auf dieser Seite können Sie <a href="#top"><u>Nachrichten</u></a> zur Ansicht auswählen, die 
Sie empfangen oder versendet haben. Mit der Auswahlliste können Sie die Ablagen &quot;Posteingang&quot;, 
&quot;Gesendete Objekte&quot; und &quot;Papierkorb&quot; ansehen.<br>
<span class="admin">In der Auswahlliste &quot;Kategorie&quot; können Sie auch spezifische 
Kategorien auswählen (Anmerkung: Sie benötigen zumindest eine Ansichtsberechtigung für die 
einzelnen Kategorien, damit diese auf der Liste erscheinen).<br></span>
Durch anklicken der Schaltfläche &quot;Erweitert&quot; können Sie nach Nachrichten suchen, die 
bestimmte Schlüsselworte enthalten, oder nach dem versendenden oder empfangenden Mitglied. 
Nach Eingabe der Suchbegriffe klicken Sie bitte auf die Schaltfläche &quot;Weiter&quot;.
<br><br>Um eine neue Nachricht zu versenden, klicken Sie bitte auf die Schaltfläche 
&quot;Eine neue Nachricht senden&quot;.
<hr class="help">

<A NAME="messages_search_result"></A>
<h3>Nachrichten Suchergebnis</h3>
Diese Seite zeigt die <a href="#top"><u>Nachrichten</u></a> auf Basis der im
<a href="#messages_search"><u>Fenster oben</u></a> spezifizierten Kriterien.
<br><br>Das Symbol zeigt den Status der Nachricht an, entweder:
<ul>
	<li><img border="0" src="${images}/message_unread.gif"
		width="16" height="16">&nbsp;(ungelesen)
	<li><img border="0" src="${images}/message_read.gif"
		width="16" height="16">&nbsp;(gelesen)
	<li><img border="0" src="${images}/message_replied.gif"
		width="16" height="16">&nbsp;(beantwortet)
	<li><img border="0" src="${images}/message_removed.gif"
		width="16" height="16">&nbsp;(entfernt)
</ul>
Die folgenden Aktionen können Sie ausführen:
<ul>
	<li>Durch Auswahl des Titels können Sie eine Nachricht öffnen.
	<li>Mehrere Nachrichten können Sie über deren Kontrollkästchen auswählen, um danach 
	die gewählte Aktion durch Auswahl im Listenfeld unter dem Nachrichtenfenster anzuwenden.
	<li>Einzelne Nachrichten können Sie entfernen, indem Sie direkt das Löschen-Symbol 
	<img border="0" src="${images}/delete.gif" width="16" height="16">&nbsp; anklicken.
</ul>
<hr class='help'>

<A NAME="messages_send"></A>
<h3>Nachrichten senden</h3>
<span class="admin"> 
	Hier können Sie eine <a href="#top"><u>Nachricht</u></a> an ein Mitglied versenden. 
	Wenn Sie die Nachricht über die Nachrichtenfunktion versenden (&quot;Menü: Nachrichten > Nachrichten&quot;),
	müssen Sie das Mitglied im Textfeld Benutzername und/oder Name spezifizieren 
	(vervollständigt sich automatisch). Für die Nachricht müssen Sie eine Kategorie auswählen.<br>
	<br><br>Wenn Sie eine Nachricht an eine Gruppe versenden, verändert sich das Formular, 
	und es erscheinen	andere Bearbeitungsfelder. Zuerst müssen Sie eine oder mehrere Gruppen 
	aus dem Mehrfachauswahlfeld	&quot;Gruppen&quot; wählen. Alle Mitglieder der von Ihnen 
	gewählten Gruppen erhalten dann die Nachricht.<br>
	Wenn Sie etwas an eine Gruppe versenden, haben Sie die Option, die Nachricht als Reintext oder 
	formatierten Text zu verfassen. Letzteres ermöglicht Ihnen die Verwendung besonderer Hilfsmittel 
	für Layout, wie z.B. Schriftarten, Illustrationen etc. Wählen Sie dafür bitte die Option 
	&quot;Rich-Text&quot; in den Optionsschaltflächen. Daraufhin erscheint der Rich-Texteditor, 
	zusammen mit einer Reihe unterschiedlicher Schaltflächen für Layout (probieren Sie sie 
	einfach aus und spielen Sie damit etwas herum). Sie können aber auch ganz einfach HTML 
	verwenden, indem Sie die Schaltfläche &quot;Quellcode&quot; des Rich-Texteditors anklicken.<br>
	Möchten Sie ein Bild einfügen, müssen Sie dieses zuvor hochladen, und zwar über den Abschnitt 
	&quot; Menü: Content Management > <a href="${pagePrefix}content_management#custom_images">
	<u>Benutzerdefinierte Bilder</u></a > &quot;.<br>
	Als Administrator können Sie keine Nachrichten an einen anderen Administrator versenden.
</span>
<span class="member">
	Hier können Sie eine <a href="#top"><u>Nachricht</u></a> entweder an ein Mitglied oder die 
	Administration versenden:
</span>
<span class="broker">
	Hier können Sie eine Nachricht versenden an:
	<ul>
		<li>ein bestimmtes Mitglied
		<li>alle Ihre registrierten Mitglieder
		<li>oder an die Administration.
	</ul> 
</span>
<span class="member">
	Das Formular ist recht einfach. Wenn Sie &quot;Mitglied&quot; wählen, müssen Sie entweder 
	den Namen oder den Benutzernamen des Mitglieds eingeben. Wenn Sie den Benutzernamen
  verwenden, erscheint automatisch der Name.<br>
	Wenn Sie &quot;Administration&quot; wählen, müssen Sie zusätzlich eine Kategorie für Ihre 
	Nachricht spezifizieren.<br>
</span>
<span class="broker">
	Wenn Sie &quot;Meine registrierten Mitglieder“&quot; gewählt haben, so haben Sie die Option, 
	die Nachricht entweder als Reintext oder im &quot;Rich-Text-Format&quot; zu verfassen. 
	Letzteres ermöglicht Ihnen die Verwendung besonderer Hilfsmittel für Layout, wie z.B. 
	Schriftarten, Illustrationen etc. Wählen Sie dafür bitte die Option &quot;Rich-Text&quot;. 
	Daraufhin erscheint der Rich-Texteditor, zusammen mit einer Reihe unterschiedlicher 
	Schaltflächen für Layout (probieren Sie sie einfach aus und spielen Sie damit etwas herum). 
	Sie können aber auch ganz einfach HTML verwenden, indem Sie die Schaltfläche 
	&quot;Quellcode&quot; des Rich-Texteditors anklicken.<br>
</span>
Nach Fertigstellung Ihrer Nachricht klicken Sie bitte auf &quot;Weiter&quot;, um sie zu versenden. 
Die Nachricht erscheint dann in Ihrer Ablage für &quot;Gesendete Objekte&quot;.
<hr class='help'>

<span class="broker">
<a name="messages_send_brokered_members"></a>
<h3>Nachrichten senden</h3>
	Hier können Sie eine Nachricht an alle Ihre registrierten Mitglieder versenden. Das Formular 
	ist recht einfach.
	<br><br>Sie haben die Option, die Nachricht entweder als Reintext oder im &quot;Rich-Text-Format&quot; 
	zu verfassen. Letzteres ermöglicht Ihnen die Verwendung besonderer Hilfsmittel für 
	Layout, wie z.B. Schriftarten, Illustrationen etc. Wählen Sie dafür bitte die Option 
	&quot;Rich-Text&quot;. Daraufhin erscheint der Rich-Texteditor, zusammen mit einer Reihe 
	unterschiedlicher Schaltflächen für Layout (probieren Sie sie einfach aus und spielen Sie 
	damit etwas herum). Sie können aber auch ganz einfach HTML verwenden, indem Sie die Schaltfläche 
	&quot;Quellcode&quot; des Rich-Texteditors anklicken.<br>
Nach Fertigstellung Ihrer Nachricht klicken Sie bitte auf &quot;Weiter&quot;, um sie zu versenden. 
Die Nachricht erscheint dann in Ihrer Ablage für &quot;Gesendete Objekte&quot;.
<hr class="help">
</span>

<A NAME="messages_view"></A>
<h3>Nachrichten anzeigen</h3>
Dies ist die <a href="#top"><u>Nachricht</u></a>. Sie haben die Option, die Nachricht in den
&quot;Papierkorb&quot; zu verschieben, indem Sie die Option &quot;In den Papierkorb verschieben&quot; 
wählen. Nachrichten im Papierkorb können aber trotzdem wieder gelesen werden, indem Sie ganz 
einfach den &quot;Papierkorb&quot; in Ihrer <a href="#messages_search"><u>Nachrichtenübersicht</u></a> 
öffnen.<br>
Die Nachricht beantworten können Sie, indem Sie die Schaltfläche &quot;Antworten&quot; anklicken 
(haben Sie die Nachricht selbst versandt, gibt es keine Schaltfläche &quot;Antworten&quot;).
<hr>

<a name="categories"></a>
<h2>Nachrichtenkategorien</h2>
Die Kategorien der <a href="#top"><u>Nachrichten</u></a> ermöglichen Ihnen, 
an die Administration versandte Nachrichten besser zu verwalten. Kategorien existieren 
nur für Nachrichten zwischen Mitgliedern und der Administration. Nachrichten von Mitglied 
an Mitglied verwenden keine Kategorien.

<hr class='help'>

<span class="admin">
<A NAME="message_categories"></A>
<h3>Nachrichtenkategorien</h3>
Diese Seite zeigt eine Liste der <a href="#categories"><u></u>Nachrichtenkategorien</a>.
<br><br>
<ul>
	<li><img border="0" src="${images}/edit.gif" width="16" height="16">&nbsp;
	ermöglicht Ihnen, die Kategorie zu ändern.
	<li><img border="0" src="${images}/delete.gif" width="16" height="16">&nbsp;
	ermöglicht Ihnen, die Kategorie zu löschen. Dies ist nur dann möglich, wenn sie noch 
	nicht verwendet wurde.
	<li>Benutzen Sie die Schaltfläche &quot;Eine neue Nachrichtenkategorie einfügen&quot;, 
	um eine neue Kategorie einzurichten.
</ul>
</span>

<span class="admin">
<a name="edit_message_category"></a>
<h3>Nachrichtenkategorien ändern</h3>
Dieses Fenster ermöglicht Ihnen, den Namen einer <a href="#categories"><u>
Nachrichenkategorie</u></a> zu ändern. 
Wählen Sie bitte einen beschreibenden Namen, da dieser im Fenster <a href="#messages_send">
<u>Nachricht senden</u></a> anderer Mitglieder erscheint.<br>
Klicken Sie wie üblich auf &quot;Bearbeiten&quot;, um Ihre Änderungen zu tätigen. Wenn Sie 
fertig sind, klicken Sie auf &quot;Weiter&quot;, um die Veränderungen zu speichern.
<hr class="help">
</span>

<span class="admin">
<a name="new_message_category"></a>
<h3>Neue Nachrichtenkategorie einfügen</h3>
Dieses Fenster ermöglicht Ihnen, eine neue <a href="#categories"><u>Nachrichtenkategorie</u></a> 
einzufügen. Geben Sie einfach einen beschreibenden 
Namen ein, und klicken Sie auf die Schaltfläche &quot;Weiter&quot;, um Ihre Änderungen 
zu speichern.<br>
Mitglieder sehen diesen Namen in ihrem Fenster <a href="#messages_send"><u>Nachrichten senden</u></a>. 
<hr class="help">
</span>

<span class="broker admin">
<a name="sms_mailings"></a>
<h3>SMS-Mailings</h3>
In diesem Fenster können Sie nach SMS-Mailings suchen. Dies sind Mailings an Gruppe oder einzelne Benutzer.
Es ist möglich nach dem Verlauf von SMS-Mailings nach Gruppen oder einzelnen Benutzern zu suchen. 
<hr class="help">
</span>

<span class="broker admin">
<a name="sms_mailings_results"></a>
<h3>Suchergebnisse SMS-Mailings</h3>
Diese Seite zeigt die Ergebnisse der suche. Die Empfängerspalte zeigt entweder das Mitglied oder die 
Gruppe an die das Mailing gesendet wurde. Die restlichen Spalten sind selbst erklärend.
<hr class="help">
</span>


<span class="broker admin">
<a name="send_sms_mailing"></a>
<h3>SMS-Mailing senden</h3>
In diesem Fenster können Sie eine SMS-Mailing senden. Mailings können Sie senden an
</span>
<span class="broker">ihre registrierten Mitgieder. </span>
<span class="admin">eine oder mehrere Gruppen. </span>
<span class="broker admin"> 
Sie können festlegen, ob das Mailing &quot;kostenpflichtig&quot; oder &quot;frei&quot; ist. Eine kostenpflichtige Mailing bedeutet, dass
dem Benutzer die lokalen Gebühren verrechnet werden oder falls der Benutzer eine bestimmtes SMS-Guthaben (Anzahl SMS) hat, 
wird zuerst dieses Guthaben verwendet. Eine freie Mailing bedeutet, dass der Benutzer für das Mailing nicht belastet wird.
In der Regel sind kommerzielle Mailings frei, andere organisatorische Mailings sind kostenpflichtig.<br>
Ein Benutzer kann in den <a href="${pagePrefix}preferences#notifications_preferences"><u>Benachrichtigungen</u></a>
definieren, ob er/sie kostenpflichtige und/oder freie Mailings erhalten will.<br>
Individuelle Nachrichten an Mitglieder sind immer kostenlos (für den Benutzer).<br><br>
Es ist möglich über die Auswahlliste &quot;Variablen&quot; in die Nachrichten einzufügen.
Die Variablen erscheinen erst nach Auswahl von Gruppen oder individuellen Mailings (ein Mitglied
muss in diesem Fall ausgewählt werden). Das liegt daran, dass verschiedene Mitglieder (Gruppen) unterschiedliche
Profilfelder haben können. Die möglichen Variablen können in jedem Profilfeld spezifische Informationen
im Zusammenhang mit dem Mitglied wie &quot;Saldo&quot; und &quot;Kreditlimit&quot; enthalten.
Bei der Auwahl von &quot;Hinzufügen&quot; wird die Variable in den Nachrichtentext eingefügt.
Ein Beispiel einer Mailing wäre:<br> &quot;Hallo #name#,<br>Ihr Kontostand ist #balance#&quot;. 
</span>

</div> <%--  page-break end --%>
<div class='help'>
<br><br>
<br><br>
<br><br>
<br><br>
<br><br>
<br><br>
<br><br>
<br><br>
<br><br>
<br><br>
<br><br>
<br><br>
<br><br>
<br><br>
<br><br>
<br><br>
<br><br>
<br><br>
<br><br>
<br><br>
<br><br>
<br><br>
</div>