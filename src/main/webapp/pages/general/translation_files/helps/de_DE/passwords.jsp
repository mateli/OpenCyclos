<div style="page-break-after: always;">
<br><br>
Cyclos arbeitet mit Benutzer-Kennwörtern, kann aber auch für spezielle 
Überweisungs-Kennwörter konfiguriert werden. 

<i>Wo ist es zu finden?</i><br>
Ändern können Sie Ihr Benutzer-Kennwort über &quot;Menu: Persönlich > Kennwort ändern&quot;.<br>
Für das Überweisungs-Kennwort wenden Sie sich bitte an den entsprechenden
<a href="#transaction_password"><u>Hilfe-Abschnitt</u></a>.
<br><br>
<span class="broker admin">
Ein Mitglieds-Kennwort kann über das Profil des Mitglieds geändert werden im Abschnitt 
&quot;Zugang: Benutzerkennwort verwalten&quot;. 
</span>
<hr>

<a NAME="change"></a>
<h3>Benutzer-Kennwort ändern</h3>
Um das Kennwort zu ändern, geben Sie bitte Ihr derzeitiges Kennwort und das neue Kennwort zweimal 
ein, und klicken Sie auf &quot;Weiter&quot;. Möglicherweise wurden Kennwort-Bedingungen, wie z.B. 
ein Verbot für extrem einfache oder offensichtliche Kennwörter aktiviert. In einem solchen Fall 
erscheint eine Fehlermeldung, wenn Sie ein Kennwort eingeben, dass nicht den in den Bestimmungen 
definierten Anforderungen entspricht. In der Nachricht werden Sie über diese Bestimmungen und das 
zulässige Format des Kennworts informiert. 
<br>
Es ist nicht zulässig, dass Sie als neues Kennwort ein bereits von Ihnen verwendetes eingeben. 

<span class="admin">
Ist &quot;Bei der nächsten Anmeldung ändern&quot; ausgefüllt, so wird das Mitglied bei 
der ersten Anmeldung aufgefordert, das Kennwort zu ändern.<br>
Ist die Gruppeneinstellung &quot;Kennwort per E-Mail versenden&quot; aktiviert, dann haben Sie 
zusätzlich die Option, automatisch ein neues Kennwort zu generieren und es an das Mitglied zu  
versenden, indem Sie die Schaltfläche &quot;Kennwort zurücksetzen und per E-Mail senden&quot;   
anklicken. In diesem Fall wird das Kennwort automatisch generiert, so dass Sie nichts ausfüllen müssen. 
</span>
<hr class="help">

<span class="admin">
Je nach System (Zugangs)-Einstellungen (&quot;Menü: Einstellungen > Zugang&quot;) kann das 
Überweisungskennwort als Nummer in ein formelles Feld eingegeben werden, oder mit Hilfe 
einer virtuellen Tastatur. Sie können auch wählen, welche Zeichen Teil des Überweisungskennworts 
sein sollen.  
Das Überweisungskennwort wird auf der Seite &quot;Benutzerverwaltung&quot; 
(&quot;Profile > Zugang > Kennwörter verwalten&quot;) verwaltet. Diese Option ist nur dann verfügbar, 
wenn das Überweisungskennwort für die Gruppe freigegeben wurde (in den Gruppenberechtigungen). 
<hr class="help">
</span>

<a NAME="transaction_password_generation"></a>
<h3>Überweisungskennwort</h3>
Ein Überweisungskennwort ist eine Kennwort, nach dem bei jeder Überweisung gefragt werden kann.
Hier können Sie Ihr persönliches Überweisungskennwort erfragen. Mit &quot;Überweisungskennwort beschaffen&quot;
erscheint Ihr Kennwort. Sorgen Sie dafür, dass Sie dieses Überweisungskennwort nicht vergessen. Sie können dieses Kennwort 
nur einmal abfragen. 
<hr class="help">

<span class="admin">
<a NAME="manage_transaction_password"></a>
<h3>Überweisungskennwort verwalten</h3>
<br>Ist das <a href="#transaction_password"><u>Überweisungskennwort</u></a> für die Gruppe freigegeben, 
kann es vier verschiedene Status haben. 
<ul>
	<li><b>Inaktiv:</b> Das Überweisungskennwort wurde noch nie verwendet/generiert. 
	<li><b>Anstehend:</b> Das Überweisungskennwort steht an, von einem Mitglied/Administrator 
	generiert zu werden. 
	<li><b>Aktiv:</b> Das Überweisungskennwort wurde vom Mitglied abgefragt. 
	<li><b>Gesperrt:</b> Das Überweisungskennwort wurde von einem Administrator gesperrt. 
</ul>
Der jeweilige Status erscheint in diesem Fenster.<br><br>
Ein Administrator (mit der entsprechenden Berechtigung) kann durch Anklicken der Schaltfläche 
&quot;Überweisungskennwort sperren&quot; das Überweisungskennwort zurücksetzen oder sperren. In diesem 
Fall ist das Überweisungskennwort ungültig, und das Mitglied erhält kein neues Kennwort (bis der 
Administrator das Kennwort zurücksetzt)
<br><br>
Sie können das Überweisungskennwort auch zurücksetzen, über die Schaltfläche &quot;Überweisungskennwort 
zurücksetzen&quot;.  In diesem Fall erhält das Mitglied ein neues Überweisungskennwort. 

<hr class="help">
</span>

<a name="pin"></a>
<h3>PIN</h3>
Eine PIN ist ein Kennwort, das für manche Typen von externen Zahlungen verwendet wird, wie POS (Point of Sale), 
PayPal oder Zahlungen per SMS. Eine PIN enthält nur Zahlen.<br><br>
<span class="admin">
Um ein PIN-Kennwort zu aktivieren, müssen Sie folgendes tun: 
<ul>
	<li><b>Kommunikationsweg:</b> Die PIN muss im <a href="${pagePrefix}settings#channels_detail"><u>
	Kommunikationsweg</u></a> aktiviert werden (&quot;Menü: Einstellungen > Kommunikationswege&quot;, 
	und Anklicken des Bearbeitungssymbols um eine PIN zu ändern).
	<li><b><a href="${pagePrefix}groups#edit_member_group"><u>
	Gruppeneinstellungen:</u></a></b> Die PIN-Länge muss unter &quot;Zugangseinstellungen&quot; eingestellt werden.
	<li><b> <a href="${pagePrefix}account_management#transaction_types"><u>
	Überweisungstyp:</u></a></b> im entsprechenden Überweisungstyp muss der Kommunikationsweg freigegeben werden.
</ul>
</span>
Zu PIN und <a href="${pagePrefix}settings#channels"><u>Kommunikationsweg</u></a> gelangen Sie über das 
<span class="admin">das Mitglieds-<a href="${pagePrefix}profiles"><u>Profil</u></a> > Externer Zugang.</span>
<span class="member">&quot;Menü: Persönlich > Externer Zugang&quot;.</span>
<hr class="help">

<a NAME="change_pin"></a>
<h3>PIN ändern/freigeben</h3>
Ein <a href="#pin"><u>PIN</u></a> wird für manche Typen von externen Zahlungen verwendet, wie POS (Point of Sale), 
PayPal oder Zahlungen per SMS.<br>
Eine PIN kann nur aus Zahlen bestehen (Buchstaben sind nicht zulässig). Um Ihre PIN zu ändern, müssen Sie 
zuerst Ihr Kennwort eingeben. Falls das <a href="#transaction_password"><u>Überweisungskennwort</u></a>
benutzt wird, müssen Sie dieses zuerst eingeben (statt eines Benutzerkennworts). Die PIN muss zwei 
mal eingegeben, und über die Schaltfläche &quot;Weiter&quot; bestätigt werden.<br><br>
Wird die PIN falsch eingegeben, kann Sie nach einer bestimmten Anzahl von Fehlversuchen gesperrt werden 
(Standardeinstellung ist dreimal). Sie können dann entweder warten, bis die Blockade wieder aufgehoben wurde, 
oder die PIN manuell entsperren, indem Sie die Schaltfläche &quot;Entsperren&quot; unter dem Fenster 
&quot;PIN ändern&quot; wählen. 

<hr class="help">

<a NAME="select_channels"></a>
<h3>Kommunikationswege</h3>
Hier sehen Sie die möglichen
<span class="admin"> <a href="${pagePrefix}settings#channels"><u>Kommunikationswege</u></a>.</span>
<span class="member">Kommunikationswege. Ein Kommunikationsweg ist das Medium, über das Sie auf Cyclos 
zugreifen, z.B. ein Web-Browser, oder ein Mobiltelefon.</span>
<br>
Es kann sein, dass nicht alle (Wege) verfügbar sind, je nach Konfiguration in Ihrer Organisation. 
Wählen Sie die von Ihnen gewünschten Kommunikationswege, indem Sie in die entsprechenden Kästchen 
ein Häkchen setzen.
<ul>
	<li><b>POS-Web Zahlungen:</b> Point of Sale Zahlungen (Zahlungen des Konsumenten direkt am Ort des Verkaufs). 
	<li><b>WAP 1 Zugang:</b> Mobiltelefonzugang für ältere Modelle, die WAP 1 unterstützen (Wireless 
	Application Protocol 1). Mobiltelefonzugang unterstützen Saldenübersicht, Kontoinformation und Zahlungen.
	<li><b>WAP 2 Zugang:</b> Mobiltelefonzugang für neuere Mobiltelefone, die WAP 2 unterstützen (Wireless 
	Application Protocol 2). Mobiltelefonzugang unterstützen Saldenübersicht, Kontoinformation und Zahlungen. 
	<li><b>Internetshop-Zugang:</b> Ermöglicht Zahlungen an externen (E-Handel) Stellen.
</u> 
Denken Sie bitte daran, auf  &quot;Weiter&quot; zu klicken, nachdem Sie Ihre Auswahl getroffen haben, 
andernfalls werden die Änderungen nicht gespeichert. 
</li></ul>
<b>Anmerkung: </b> Der SMS-Kommunikationskanal kann auf der Benachrichtigunsseite aktiviert werden. Der Link
im Fenster (unter Zugang Kommunikationskanäle) führt Sie direkt auf diese Seite.
<hr class="help">

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