<div style="page-break-after: always;">
<br><br><br> Ein Zugangsgerät ist ein Zahlungshilfsmittel 
das verwendet werden kann, um Zahlungen von außerhalb Cyclos zu tätigen. Dabei kann es sich um ein 
POS-Gerät (point of sale) in Kombination mit einer Kreditkarte oder einer Kundenkarte handeln.<br>
Ein Mitglied mit einer aktiven Kundenkarte kann Zahlungen an einem WebPOS-Kommunikationsweg, 
einem Hardware–POS (Kartenleser) oder mit auf dem Rechner installierter Software tätigen. 
Üblicherweise handelt es sich bei Kartenzahlungen um Zahlungen vom Kunden an ein Geschäft. Ein Mitglied 
kann zwar mehrere Karten besitzen, aber nur eine aktive Karte.

<span class="broker admin"> Statt einer PIN kann auch das Überweisungs- oder 
das Benutzerkennwort des Benutzers verwendet werden.<br>
Die Karte basiert auf einem Kartentyp einer Art Vorlage für die Karten.
</span><br>

<span class="member"> <i>Wo ist es zu finden?</i><br>
Zugangsgeräte finden Sie über &quot;Menü: Persönlich > POS-Geräte
/ Karten
</span><br>
<span class="broker"> Die Zugangsgeräte der Broker-Mitglieder kann 
unter Brokering-Aktionen im Profil gefunden werden.</span><br>

<span class="admin"> <i>Wo ist es zu finden?</i><br>
Zugangsgeräte finden Sie im Profil des Mitglieds oder unter &quot;Menü: 
Zugangsgeräte.<br><br>

<i>Wie werden Zugangsgeräte aktiviert?</i><br>
Um einen Karte generieren zu können, muss ein <a
	href="${pagePrefix}access_devices#list_card_type"><u>Kartentyp</u></a> existieren, und dieser 
muss mit einer oder mehreren Mitglieds- oder Brokergruppen verbunden sein. Ist 
dies der Fall, können Sie über Mitgliedsaktionen für ein Mitglied eine Karte generieren. Ebenso 
ist es möglich, über die Funktion <a
	href="${pagePrefix}user_management#bulk_actions"><u>Massenaktionen</u>
</a> mehrere Karten für eine Gruppe von Mitgliedern zu generieren.</span>
<br><br>
<hr>
<a name="card_details"></a>
<h3>Kartendetails</h3>
Dieses Fenster zeigt die Details der Karte, und die Aktionen die Sie dazu 
durchführen können. 
<br>
<br>
Kartendetails
<ul>
	<li><b>Kartennummer</b>
	<li><b>Aktivierungsdatum</b>
	<li><b>Erstellungsdatum</b>
	<li><b>Ablaufdatum</b>
	<li><b>Status</b>
</ul>

Kartenaktionen
<ul>
	<li><b>Karte sperren</b> Diese Option erscheint nur wenn es sich um eine 
	&quot;aktive&quot; Karte handelt. Wird diese Aktion durchgeführt, so wird die Karte 
	in den Status &quot;gesperrt&quot; verschoben, und kann nicht mehr benutzt werden. Sie 
	kann allerdings wieder aktiviert werden. 
	<li><b>Karte entsperren</b> Diese Option erscheint nur wenn es sich um eine 
	&quot;gesperrte&quot; Karte handelt. Wird diese Aktion durchgeführt, erhält die Karte 
	wieder &quot;aktiven&quot; Status.
	<li><b>Karte aktivieren</b> Diese Option erscheint nur wenn es sich um eine Karte 
	&quot;in Erwartung&quot; handelt. Wird diese Aktion durchgeführt, erhält die Karte 
	&quot;aktiven&quot; Status.<br>
  Bitte beachten Sie dass existierende aktive Karten des Mitglieds ungültig werden 
  wenn Sie eine neue Karte aktivieren. 
	<li><b>Karte vernichten</b> Wird diese Aktion durchgeführt so erhält die Karte 
	den Status &quot;ungültig&quot;, und kann nie wieder verwendet werden.
	<li><b>Sicherheitscode der Karte ändern</b> Diese Option erscheint nur dann 
	wenn für die Karte ein Sicherheitscode verwendet wird.  
</ul>
<span class="admin">
<b>Anmerkung:</b> 
Mehr Information zum Sicherheitscode für Karten finden Sie im Hilfe-Abschnitt der Seite  
<a href="#edit_card_type"><u>Kartentyp bearbeiten</u></a>.
</span>
<br><br>
<b>Anmerkung:</b>
Die Karten-Aktionen erfordern ein Überweisungskennwort, falls dies aktiviert ist.
Nicht alle Aktionen können auch für Mitglieder aktiviert werden. Es kann daher sein, dass nicht alle Aktionen 
erscheinen. 

<span class="admin">
<a name="card_logs"></a>
<h3>Karten-Protokoll</h3>
Dieses Fenster zeigt eine Liste aller Statusänderungen, die Karten betreffend (falls zutreffend). 
Aktionen, die in diesem Protokoll erscheinen, sind: 
<ul>
	<li>Karte sperren
	<li>Karte entsperren 
	<li>Karte aktivieren
	<li>Karte vernichten	
</ul>
<hr class="help">
</span>


<a name="search_cards"></a>
<span class="broker admin">
<h3>Karten suchen</h3>
In diesem Fenster können Sie mit Hilfe der folgenden Kriterien nach Karten suchen:
<ul>
	<li>Kartenstatus
	<li>Gruppen
	<li>Ablaufdatum
	<li>Mitglied
	<li>Kartennummer
	<li>Kartentyp. Das einzige erforderliche Feld. Gibt es lediglich einen Kartentyp, 
	so erscheint diese Option nicht.
</ul>
<hr class="help">
</span>


<a name="search_card_results"></a>
<span class="member">
<h3>Karten auflisten</h3>
Diese Seite zeigt eine Liste all Ihrer Karten. Um Aktionen an einer Karte 
durchzuführen, wählen Sie bitte das Symbols Lupe und geben Sie dann die Karte 
ein. 
</span>
<span class="broker admin">
<h3>Ergebnisse der Kartensuche</h3>
Diese Seite zeigt eine Liste aller Suchresultate. Um Aktionen an einer 
Karte durchzuführen, wählen Sie bitte das Symbols Lupe und geben Sie dann die 
Karte ein.</span> 
<span class="admin"> Sie können diese Liste in eine CSV-Datei exportieren 
und durch Auswahl der Drucken-Symbol und CSV in der Kopfleiste die Ergebnisse 
ausdrucken. 
</span>

<span class="admin">
<br><br><b>Karte erstellen</b><br>
Geschieht der Zugang zur Kartenliste über eine Mitgliedsprofil-Aktion, so haben 
Sie die Option durch Auswahl der Schaltfläche &quot;Karte herstellen&quot; eine neue Karte 
herzustellen. Wenn Sie diese Option wählen, erstellen Sie eine neue Karte mit 
dem Status &quot;in Erwartung&quot;. Die Karte basiert dann auf dem für diese 
Mitgliedsgruppe eingestellten Kartentyp. (hat die Gruppe keinen ihr zugeordneten 
Kartentyp, kann keine Karte erstellt werden).<br>
Gibt es bereits eine Karte mit dem Status &quot;in Erwartung&quot;, so wird diese 
zugunsten der neuen Karte für ungültig erklärt. 
</span>
<hr class="help">


<span class="admin">
<a name="list_card_type"></a>
<h3>Kartentypen</h3>
Dieses Fenster zeigt eine Liste aller Kartentypen im System. Ein Kartentyp ist 
eine Vorlage für eine Karte (so wie ein Überweisungstyp eine Vorlage für eine 
Überweisung ist). Sie können einen existierenden Kartentyp bearbeiten, indem Sie das 
Bearbeiten-Symbol wählen, und durch anklicken des Löschen-Symbols einen Kartentyp löschen.<br>
Bitte beachten Sie, dass sie einen Kartentyp nicht löschen können, wenn auf dem Kartentyp basierende 
Überweisungstypen existieren. 
<hr class="help">
</span>

<span class="admin">
<a name="edit_card_type"></a>
<h3>Kartentyp bearbeiten</h3>
Auf dieser Seite können Sie einen 
<a
href="${pagePrefix}access_devices#list_card_type"><u>Kartentyp</u></a> erstellen oder bearbeiten. Ein Kartentyp kann nur 
dann bearbeitet werden, wenn es keine mit diesem Kartentyp generierten Karten gibt. 
Die folgenden Felder sind verfügbar: 
<ul>
	<li><b>Name:</b> Der Name des Kartentyps. Der Name wird nur für die 
	Suchen verwendet. 
	<li><b>Zahlenformat:</b> Stellt dar, wie die Kartennummer formatiert 
	werden soll.<br>
	Mögliche Zeichen sind: 
	<ul>
		<li>&quot;#&quot; Eine Nummer
		<li>&quot;-&quot; Ein Trennzeichen
		<li>&quot;/&quot; Ein Trennzeichen
		<li>&quot;\&quot; Ein Trennzeichen
		<li>&quot;.&quot; Ein Trennzeichen
	</ul>
	Beispiel für ein Zahlenformat: &quot;#### #### #### ####&quot; oder
	&quot;####&quot; &quot;##/##&quot;<br>
	Es ist möglich eine (fixe) Zahl einzugeben, wie &quot;123&quot;.	
	<li><b>Ablaufdatum:</b> An diesem Datum geht die Karte in den	Status 
	abgelaufen über (und kann danach weder verwendet noch wieder aktiviert werden). 
	<li><b>Tag des Ablaufdatums ignorieren:</b> Wird diese Option gewählt, 
	so wird die Karte am letzten Tag des betreffenden Monats ungültig. 
	<li><b>Sicherheitscode:</b> Bei dem Kartensicherheitscode handelt es sich um 
	ein für die Karte definiertes Kennwort. Das funktioniert ähnlich wie 
	eine PIN, allerdings mit dem Unterschied, dass PINs in Cyclos nicht per 
	Karte definiert werden, sondern für den Benutzer (d.h. der Benutzer 
	kann die PIN für mehr als einen Zugangsweg verwenden).<br>
  Mögliche Werte sind: 
	<ul>
		<li><b>Nicht verwendet:</b> Die Karte verwendet keinen Sicherheitscode. 
		<li><b>Manuell:</b> Das Mitglied und Broker/Administratoren mit entsprechender Berechtigung 
		können den Sicherheitscode ändern. 
		<li><b>Automatisch:</b> Das System generiert den Sicherheitscode.
	</ul>
	<b>Maximale Anzahl fehlgeschlagener Versuche mit Sicherheitscode:</b> Nach dieser Anzahl 
	erfolgloser Versuche wird die Karte gesperrt.<br>
	<b>Sperrfrist nach fehlgeschlagenen Versuchen:</b> Zeitraum, für den die Karte nach den erfolglosen 
	Versuchen gesperrt wird.<br>
	<b>Länge des Sicherheitscodes:</b> Mindest-  und Maximallänge des 
	Sicherheitscodes.<br>
</ul>
<hr class="help">
</span>


<a name="POS"></a>
<h3>POS (Point of Sale)</h3>
Bei einem POS-Gerät handelt es sich entweder um Hardware (Kartenleser) oder um auf einem Rechner installierte 
Software (oder jedes andere Gerät mit einem Internetbrowser). Ein POS befindet sich normalerweise direkt im 
Geschäft. Ein Mitglied kann mehrere POS-Geräte haben. Typischerweise identifiziert 
der POS den Benutzer (Zahler) wenn er oder sie eine Karte durch den Leser schiebt. 
Dies ist allerdings optional (der POS kann auch für manuelle Eingabe konfiguriert werden). Der benutzer muss die Karte 
durch Eingabe einer PIN bestätigen.  
<br>
<span class="admin"> Um POS-Zahlungen zu zu lassen, muss der POS-Kommunikationsweg 
aktiviert sein. Mehr Information zu POS finden Sie im Hilfe-Abschnitt von  
at the <a href="${pagePrefix}settings#channels"><u>Einstellungen -
Kommunikationswege</u></a>.</span>


<a name="edit_pos"></a>
<h3>Erstellen / Bearbeiten von POS</h3>
In diesem Formular können Sie die Konfigurationen für das POS-Gerät einstellen und 
Aktionen im Zusammenhang mit POS durchführen. Jeder POS hat die folgenden Details: 
<ul>
	<li><b>Identifikationsnummer:</b> Diese Nummer kann zur Verwaltung der POS-Geräte 
	verwendet werden. Dabei handelt es sich meist um die Seriennummern der POS-Geräte. 
	Ist diese Identifikationsnummer einmal definiert, kann sie nicht mehr verändert werden. 
	<span class="admin"> 
	Die Identifikationsnummer dient dazu, den POS zu identifizieren, wenn er 
	mit Cyclos kommuniziert (Authentifizierung geschieht über die PIN).  
	</span>
	<li><b>Beschreibung des POS:</b> Beschreibung (optional).
	<li><b>Benutzername:</b> Der Benutzername des Mitglieds, dem der POS zugeordnet wurde.
	<li><b>Vollständiger Name:</b> Der Name Mitglieds, dem der POS zugeordnet wurde.
	<li><b>POS-Name:</b> Dies ist ein Name, der für den POS festgelegt werden kann. 
	Im Gegensatz zur Identifikationsnummer kann dieser Name verändert werden (z.B. Laden 1, MobilePOS1 
	etc.). 
	<li><b>Zugeordnet am:</b> Datum, an dem der POS diesem Mitglied zugeordnet wurde.
	<li><b>Status:</b> Status des POS, entweder 'nicht zugeordnet', 'zugeordnet', 'aktiv'. 
	<li><b>Zahlungen ermöglichen:</b> Ein POS wird normalerweise verwendet, 
	um Zahlungen von Kunden zu erhalten. Falls Sie es auch ermöglichen möchten, dass der Besitzer des POS Zahlungen 
	an andere Mitglieder tätigen kann, wählen Sie bitte diese Option. 
	<li><b>Größe der Ergebnisseite:</b> Dies zeigt die maximale Ergebnisse 
	für die Kontenübersicht. Die Standardeinstellung von 5 bedeutet, dass 
	der Kontoverlauf den Saldo und die letzten 5 Überweisungen 
	zeigt. 
	<li><b>Anzahl der Belege:</b> Die Anzahl der zu druckenden Belege. Üblicherweise 
	werden für jeden Vorgang zwei Belege gedruckt (einer für das Geschäft und 
	einer für den Kunden).  
	<li><b>Maximale Anzahl geplanter Zahlungen:</b> Die maximal zulässige Anzahl 
	geplanter Zahlungen. 
</ul>

Die folgenden Aktionen konnen für POS-Geräte durchgeführt werden: 
(Möglicherweise sind nicht alle Aktionen verfügbar. Die Aktionen hängen von den Berechtigungen ab.) 
<ul>
	<li><b>Zuordnen:</b> Der POS ist derzeit nicht zugeordnet und kann durch 
	Wahl dieser Option zugeordnet werden. 
	<li><b>Zuordnung aufheben:</b> Der POS ist derzeit zugeordnet und kann durch 
	Wahl dieser Option aus dieser Zuordnung gelöst werden. 
	<li><b>Entsperren:</b> Der POS ist derzeit gesperrt und kann durch 
	Wahl dieser Option entsperrt werden. 
	<li><b>Sperren:</b> Der POS ist derzeit nicht aktiv und kann durch Wahl dieser Option gesperrt werden. 
	<li><b>PIN ändern:</b> POS-PIN ändern. Die PIN dient der Erkennung des 
	POS und aller vom Besitzer dieser POS durchgeführten Aktionen wie Salden und 
	Kontoverlauf abrufen, Zahlungen tätigen. Das Empfangen von Zahlungen durch Kartenkunden benötigt keine 
	POS-PIN (da in diesem Fall der Kunde seine PIN verwendet). 
	Wird nur bei entsprechender Berechtigung angezeigt. 
	<span class="admin">
	<li><b>Vernichten:</b> Wird ein POS vernichtet, kann er in keiner Art mehr 
	verwendet werden. Es kann also auch kein neuer POS mit der gleichen 
	POS-ID erzeugt werden. Üblicherweise wird ein POS verworfen, wenn das 
	physische Gerät nicht mehr verwendet werden kann ( weil es schadhaft oder verloren gegangen ist).
	</span>
</ul>
<hr class="help">
</span>

<span class="admin">
<a name="pos_logs"></a>
<h3>POS Protokolle</h3>
Diese zeigen Statusänderungen des POS (falls zutreffend). Aktionen, die in diesem Protokoll 
erscheinen, sind: 
<ul>
	<li>POS zuordnen
	<li>POS Zuordnung aufheben
	<li>POS sperren
	<li>POS freigeben
	<li>POS vernichten
</ul>
<hr class="help">
</span>

<span class="broker admin">
<a name="search_pos"></a>
<h3>POS suchen</h3>
Suchen nach POS, nach bestimmten Kriterien. Zur Suche haben Sie folgende Optionen: 
<ul>
	<li>POS Status
	<li>Identifikationsnummer
	<li>Mitglied
</ul>
<hr class="help">
</span>

<span class="broker admin"> <a name="search_pos_results"></a>
<h3>Ergebnisse der POS-Suche</h3>
Diese Seite zeigt eine Liste aller Suchresultate. 
<ul>
	<li><img border="0" src="${images}/edit.gif" width="16"
		height="16"> Dieses Symbol anklicken um den POS zu bearbeiten.
	<li><img border="0" src="${images}/delete.gif" width="16"
		height="16"> Durch Anklicken dieses Symbols können Sie den POS löschen.<br>
  Anmerkung: Sie können einen POS nur dann löschen, wenn er bisher noch keinem 
  Mitglied zugeordnet wurde. 
</ul>
<hr class="help">
</span>

<span class="member"> 
<a name="member_pos"></a>
<h3>POS-Liste</h3>
Dieses Formular zeigt eine Liste der POS, die Ihnen zugeordnet wurden.
Klicken Sie auf das &quot;View&quot; <img border="0" src="${images}/view.gif" width="16"
		height="16"> Anzeigen-Symbol um den POS zu ändern.
<hr class="help">
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
</div>
