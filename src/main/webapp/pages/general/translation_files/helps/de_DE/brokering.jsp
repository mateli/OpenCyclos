<div style="page-break-after: always;">
<br><br>
Ein Mitglied einer Gruppe vom Typ &quot;Broker&quot; kann neue Mitglieder 
registrieren und hat bis zu einer bestimmten Ebene Zugang und Kontrolle über 
diese Mitglieder (abhängig von der Konfiguration der Broker-Gruppe). Die 
Bezeichnung &quot;Broker&quot; ist eigentlich hier nicht ganz zutreffend, da diese 
Funktion für viele unterschiedliche Zwecke verwendet werden kann.<br>
Eine übliche Brokerfunktion ist es, dass der Broker eine <a href="#commission"><u>
Kommission</u></a> erhalten kann, wenn er Mitglieder registriert. Die Kommission 
kann so konfiguriert sein, dass sie vom Umsatz der neuen Mitglieder abhängt. 
Die Idee dahinter ist, dass der Broker es mit der Betreuung der 
neuen Mitglieder ernst nimmt.<br>
Es kann einem Broker auch ermöglicht werden, einen Teil der persönlichen Verwaltung 
einer neuen Mitgliedsgruppe zu übernehmen, falls diese zum Beispiel zu unsicher 
im Umgang mit Computern ist, um bestimmte Cyclos-Mitgliedsvorgänge selbst zu tätigen.<br>
<span class="broker admin"> Die Brokerfunktion kann in Mikro-Finanzsystemen auch 
von Darlehensvermittler verwendet werden. Der Darlehensvermittler kann als Broker 
ebenfalls neue Mitglieder registrieren und Informationen über den Darlehensstatus 
der Mitglieder einholen. Gemeinschaftssysteme wie LETS können die Brokerfunktion 
für Verwaltungszwecke verwenden, um anderen Mitgliedern zu helfen, 
die keinen Zugang oder nicht die Fähigkeit haben, mit dem Internet/Handy umzugehen. 
Innerhalb eines Systems kann es unterschiedliche Typen von Brokergruppen geben. </span>
Brokern ist es möglich, mit den einzelnen Mitgliedern Verträge über die Kommission abzuschließen.

<br><br><span class="broker"> <i>Wo ist es zu finden?</i><br>
Alle Brokering-Funktionen sind zugänglich über das &quot;Menü: Brokering&quot;.<br>
Brokering-Informationen und Funktionen in Zusammenhang mit den von Ihnen &quot;vermittelten&quot; 
Mitgliedern finden Sie auf der Seite <a	href="${pagePrefix}profiles#actions_for_member_by_broker">
<u>Broker-Aktionen</u></a> unter dem <a href="${pagePrefix}profiles#member_profile"><u>Profil</u></a>
des jeweiligen Mitglieds.</span>
<br><br><span class="admin"> <i>Wo ist es zu finden?</i> <br>
Administratoren haben keine Broker-Funktionen, können aber bis zu einem bestimmten 
Grad Zugang zu den Brokering-Funktionen sowohl für Broker als auch für die Mitglieder 
haben, die einem Broker zugeordnet sind. Dies kann auf der Seite 
<a	href="${pagePrefix}profiles#actions_for_member_by_admin"><u>Aktionen</u></a> unter dem 
<a href="${pagePrefix}profiles#member_profile"><u>Profil</u></a> des Mitglieds 
oder des Brokers getätigt werden.<br>
Wie bereits erklärt, kann die Brokering-Funktion für viele Zwecke verwendet werden. 
Daher können Suchkriterien und Funktionen in Verbindung mit Brokern in anderen Modulen auftauchen. 
Eine Darlehenssuche kann zum Beispiel einen Broker-Suchfilter haben, und ein 
Administrator kann Massenaktionen in Zusammenhang mit dem Broker tätigen. 
Diese Funktionen sind dann im Zusammenhang mit der entsprechenden Funktion erklärt.

<br><br><i>Wie werden die Broker-Funktionen aktiviert?</i><br>
Um &quot;Brokering&quot; zu aktivieren, müssen Sie die 
<a href="${pagePrefix}groups#manage_group_permissions_broker"><u>Broker-Berechtigungen</u></a> 
im Abschnitt &quot;Brokering&quot; und <a	href="${pagePrefix}groups#edit_broker_group">
<u>Broker-Gruppeneinstellungen</u></a> im Abschnitt &quot;Einstellungen Brokering&quot; einstellen. 
Die letztgenannte hat lediglich eine Rubrik. Wenn Sie möchten, dass Broker in der Lage sind, 
Mitglieder zu registrieren, müssen Sie das hier definieren.<br>
Broker-Kommissionen können auf der <a	href="${pagePrefix}account_management#transaction_type_details">
<u>Überweisungstyp</u></a>-Ebene in der Konfiguration 
<a href="${pagePrefix}brokering#broker_commission_list"><u>Brokerkommissionen</u></a> 
aktiviert werden.<br>
Und natürlich müssen Sie auch die entsprechenden <a
href="${pagePrefix}groups#manage_group_permissions_broker"><u>Broker-Berechtigungen</u></a> 
einstellen (im Abschnitt &quot;Brokering&quot;). Damit ein Mitglied die Kommissionen zeigen kann, müssen 
dazu die <a	href="${pagePrefix}groups#manage_group_permissions_member"><u>
Berechtigungen</u></a> eingestellt sein (Abschnitt &quot;Kommissionen&quot;).
<br><br><b>Anmerkung:</b> Es macht keinen Sinn, Brokern oder Mitgliedern Zugang zu 
Kommissionen zu geben, ohne zuvor als Administrator in der Konfiguration der 
Überweisungstypen Kommissionen definiert und aktiviert zu haben, wie oben erwähnt. 
In solch einem Fall wären Broker und Mitglieder mit leeren Kästchen konfrontiert, 
und hätten Zugang zu Brokering-Kommissionen und Verträgen, ohne diese allerdings 
anpassen oder definieren zu können, da sie ganz einfach nicht existieren.<br>
Stellen Sie sicher, dass Sie die Broker-Kommissionsgebühr nicht nur einrichten, 
sondern auch aktivieren.
</span>
<hr>

<span class="broker admin">
<A NAME="broker_search_member"></A>
<h3>Mitglieder eines Brokers suchen</h3>
Diese Funktion erbringt eine Liste der zu einem Broker gehörenden Mitglieder.<br>
Suchen können Sie nach:
<ul>
	<li><b>Benutzername / Name:</b> Suche nach einzelnem Mitglied
	<li><b>Gruppe:</b> Suche nach Gruppe
	<li><b>Status:</b>
	<ul>
		<li><b>Aktiv: </b>Dies sind die Mitglieder, die im System aktiv sind (in 
		einer &quot;aktiven&quot; Gruppe)
		<li><b>Kommission beendet: </b> Zeigt die Mitglieder, die <a href="#commission"><u>
		Kommission</u></a> erhalten haben  und diese beendet wurde.
		<li><b>Erwarten Aktivierung: </b>Dies sind die von Ihnen registrierten Mitglieder, 
		die aber noch nicht aktiv sind (da sie zuvor von einem Broker oder Administrator 
		aktiviert werden müssen). 
	</ul>
</ul>
<hr class="help">
</span>

<span class="broker">
<A NAME="broker_search_member_result"></A>
<h3>Broker – Mitgliedersuche Ergebnisse</h3>
Dieses Fenster zeigt eine Liste der Mitglieder, die von Ihnen als Broker registriert wurden.<br>
In der Mitgliedsliste können Sie das Mitglied oder den Benutzernamen wählen, um das
<a href="${pagePrefix}profiles"><u>Profil</u></a> des Mitglieds zu öffnen.
<hr class="help">
</span>

<span class="admin">
<A NAME="admin_brokering_list"></A>
<h3>Mitgliederliste (des Brokers)</h3>
Diese Seite zeigt eine Liste aller mit einem bestimmten Broker verbundenen Mitglieder. 
Wählen Sie bitte den Namen, um in das Mitgliedsprofil zu gelangen.<br>
Klicken des Löschen-Symbols <img border="0" src="${images}/delete.gif" width="16" height="16">&nbsp;, 
um die Verbindung zwischen Broker und Mitglied aufzuheben (Sie werden um Bestätigung gebeten).  
<hr class="help">
</span>


<span class="admin">
<A NAME="add_member_to_broker"></A>
<h3>Mitglied einem Broker hinzufügen</h3>
Auf dieser Seite können Sie ein Mitglied der Brokering-Liste eines Brokers hinzufügen. 
Die Textfelder Benutzername und Name vervollständigen sich automatisch bei der Eingabe. 
Falls das Mitglied mit einem anderen Broker verbunden ist, und eine aktive 
<a href="#commission"><u>Kommission</u></a> existiert, können Sie diese aufheben, 
indem Sie &quot;Kommission aufheben&quot; wählen.<br>
Bitte beachten Sie, dass Sie über <a href="${pagePrefix}user_management#bulk_actions">
<u>Massenaktionen</u></a> einen kompletten Satz Mitglieder einem anderen Broker 
zuordnen können.

<hr class="help">
</span>

<span class="admin">
<A NAME="change_broker"></A>
<h3>Broker festlegen / Broker ändern von...</h3>
Auf dieser Seite können Sie festlegen, mit welchem Broker ein Mitglied verbunden ist. 
Das Feld &quot;Akuteller Broker&quot; zeigt den derzeitigen Broker eines Mitglieds, 
falls vorhanden. Dieses Feld kann &quot;leer&quot; sein, da ein Mitglied nicht immer 
mit einem Broker verbunden sein muss. Falls Sie ein Mitglied mit einem Broker 
verbinden wollen, geben Sie den neuen Broker in die sich automatisch vervollständigenden 
Benutzer- oder Namensfelder ein. Sobald der Broker erscheint, können Sie den (gewählten) 
Broker ändern, indem Sie einen Kommentar schreiben und die Schaltfläche 
&quot;Weiter&quot; darunter anklicken.<br>
Falls Sie eine noch für den derzeitigen Broker 
aktive <a href="#commission"><u>Kommission</u></a> beenden möchten, wählen Sie bitte 
das Feld &quot;Kommission aufheben&quot;. Wenn Sie dies nicht tun, so erbt der neue 
Broker die Kommissionseinstellungen des vorherigen. Das bedeutet, dass die Kommission 
von dem Tag an erhoben wird, an dem der Broker das neue Mitglied zugewiesen bekommen 
hat, bis zum in den Kommissionseinstellungen konfigurierten Enddatum der Kommission.
<hr class="help">
</span>

<span class="admin">
<a name="remove_member_to_broker"></a>
<h3>Mitglied entfernen</h3>
Der Titel dieses Fensters ist möglicherweise etwas beunruhigend, allerdings ist alles 
was passiert, wenn Sie &quot;Weiter&quot; klicken, dass das Mitglied nicht mehr bei 
diesem Broker registriert sein wird. Der Broker ist also dem Mitglied nicht weiter zugeordnet.<br>
Bevor Sie nun die Schaltfläche &quot;Weiter&quot; anklicken, können Sie einen Kommentar 
mit der Begründung für die Entfernung hinzufügen.<br>
Bitte beachten Sie, dass Sie über <a href="${pagePrefix}user_management#bulk_actions">
<u>Massenaktionen</u></a> einen kompletten Satz Mitglieder einem anderen Broker zuordnen können.

<hr class="help">
</span>
<hr class="help">

<a name="commission"></a>
<h2>Broker-Kommissionen</h2>
Für seine Arbeit kann der Broker eine Kommission erhalten; dies ist eine kleine 
Entschädigung und richtet sich in der Höhe nach den Aktivitäten seiner Mitglieder. 
Registriert ein Broker ein neues Mitglied, so wird dieses Mitglied für gewöhnlich 
ein dem Broker zugeordnetes Mitglied. Der Broker kann eine kleine Zahlung für 
jede Überweisung erhalten, an der das neue Mitglied beteiligt ist. Die Idee dahinter 
ist, Broker zu ermuntern, die Betreuung der neuen Mitglieder ernst zu nehmen.

Wird ein Broker zu irgendeiner anderen Gruppe verschoben, so werden alle Kommissionsverträge
und laufenden Kommissionen beendet.
<hr class="help">

<span class="admin broker">
<A NAME="broker_commission_list"></A>
<h3>Liste Broker-Kommissionen</h3>
Dieses Fenster zeigt eine Liste aller konfigurierten Broker-<a href="#commission"><u>Kommissionen</u></a> 
(sowohl aktivierte als auch nicht aktivierte).
<ul>
	<li><img border="0" src="${images}/edit.gif" width="16" height="16">
	Dieses Symbol anklicken, um Brokering zu ändern.
	<li><img border="0" src="${images}/delete.gif" width="16" height="16">
 	Dieses Symbol anklicken, um Brokering zu löschen.
</ul>
Um eine neue Kommission hinzu zu fügen, klicken Sie bitte auf &quot;Neue Brokerkommission einfügen&quot;.
<hr class="help">
</span>


<span class="admin broker">
<A NAME="broker_commission_details"></A>
<h3>Details Broker-Kommissionen</h3>
Genau wie eine Überweisungsgebühr wird die Broker-<a href="#commission"><u>Kommission</u></a> 
überwiesen, wenn die &quot;Haupt&quot;-Überweisung durchgeführt und die Kriterien 
der Broker-Kommission erfüllt sind.<br>
Wie sonst auch, klicken Sie bitte zuerst auf die Schaltfläche &quot;Bearbeiten&quot;, um 
Änderungen vorzunehmen. Wenn Sie fertig sind, klicken Sie bitte auf &quot;Weiter&quot;, 
um die Änderungen zu speichern.
<br><br>
Die Broker-Kommission hat die folgenden Konfigurationen:
<ul>
	<li><b>Überweisungstyp:</b> Dies ist der Überweisungstyp, auf den die Kommission angewandt wird.
	<li><b>Name:</b> Name der Kommission.
	<li><b>Beschreibung:</b>	Beschreibung der Kommission.
	<li><b>Freigegeben:</b> Ist dieses Kästchen ausgefüllt, ist die Kommission aktiv. 
	Bitte überprüfen sie diese Einstellung, sonst funktioniert die Kommission nicht.
	<li><b>Wird abgebucht von:</b> Hier können Sie definieren, wem der Betrag abgebucht wird. 
	Dies kann der Auftraggeber, der Empfänger	oder das System sein.
	<li><b>Wird erhalten von:</b>
	Hier können Sie definieren, wer den Betrag erhält. Dies kann der Broker des Auftraggebers 
	oder der Broker des Empfängers sein.
	<li><b>Jedes Konto zulassen:</b>
	Ist dieses Kästchen ausgefüllt, gibt es keine Begrenzung für die Anwendung der 
	Überweisungsgebühr. Wenn Sie ein anderes Konto als dasjenige, das zum auslösenden 
	Überweisungstyp gehört, mit der Gebühr belasten, müssen Sie diese Option anklicken. 
	Dies kann z.B. der Fall sein, wenn Sie für die Kommission eine andere Währung wählen 
	als für den Überweisungstyp vorgegeben.
	<li><b>Generierter Überweisungstyp:</b>
	Hier definieren Sie, welcher Überweisungstyp generiert wird. Üblicherweise wird ein 
	bestimmter Überweisungstyp dafür eingerichtet, so dass Sie einen Filter daraufhin 
	setzen können (z.B. im Kontoverlauf: Abgaben und Gebühren). Die Standardkonfiguration 
	beinhaltet eine Überweisungsgebühr und einen Überweisungstyp für diese Überweisung.
	<li><b>Betrag:</b>
	Hier können Sie den Kommissionsbetrag definieren. Der Broker erhält diese Kommission, 
	wann immer &quot;sein&quot; Mitglied eine Zahlung tätigt (und die Kriterien der 
	Anwendbarkeit erfüllt sind).<br>
	Bei bestehenden Brokerverträgen können die 
	Werte der Broker-Kommissionen vom Broker geändert werden. In diesem Fall wird 
	der Beitrag als Standard-Kommissionswert für das Abkommen verwendet. Bei der 
	Einrichtung des &quot;Brokerabkommens&quot; kann der Broker diese Werte für jedes 
	seiner Mitglieder verändern. Lesen Sie dazu auch bitte den nächsten Abschnitt.

	<li><b>Festgelegter Maximalbetrag und Prozentangabe (%):</b>
	Diese Optionen hängen mit den Brokerverträgen zusammen, und werden nur dann erscheinen, 
	wenn in der Option &quot;Wird abgebucht&quot; ein Mitglied spezifiziert ist (wenn 
	es sich also nicht um ein Systemkonto handelt). Dieser Wert definiert den Maximalbetrag, 
	den ein Broker in den Brokerverträgen einsetzen kann. (Um Brokerverträge zu verwalten, 
	benötigt ein Broker die Berechtigungen dazu)
</ul>

<b>Voraussetzungen der Anwendbarkeit</b><br> 
Hier können Sie definieren, unter welchen Voraussetzungen die Gebühr angewendet wird. 
Die Gebühr wird nur dann angewendet, wenn diese Kriterien erfüllt sind. Die 
Bedingungen sind miteinander kombinierbar.
<ul>
	<li><b>Betrag größer oder gleich</b>
	Die Gebühr wird nur dann erhoben, wenn der Überweisungsbetrag höher als oder gleich 
	hoch wie der spezifizierte Betrag ist.
	<li><b>Betrag kleiner oder gleich</b><br>
	Die Gebühr wird nur dann erhoben, wenn der Überweisungsbetrag niedriger als oder gleich 
	hoch wie der spezifizierte Betrag ist.
	<li><b>Von allen Gruppen</b>
	Ist dies ausgewählt, wird die Gebühr von Mitgliedern jeder Gruppe erhoben, 
	die eine Zahlung des Überweisungstyps tätigen. Wenn Sie die Gebühr nur für bestimmte 
	Gruppen anwenden möchten, dann entfernen Sie bitte dieses Häkchen, und wählen Sie	aus 
	dem	daraufhin erscheinenden Listenfeld, welche Gruppen Sie wählen möchten.
	<li><b>An alle Gruppen</b><br>
	Ist dies ausgewählt, wird die Gebühr von Mitgliedern jeder Gruppe erhoben, 
	die eine Zahlung des Überweisungstyps empfangen. Wenn Sie die Gebühr nur für bestimmte 
	Gruppen anwenden möchten, dann entfernen Sie bitte dieses Häkchen, und wählen	Sie aus 
	dem daraufhin erscheinenden Listenfeld, welche Gruppen Sie wählen möchten.
	<li><b>Alle Brokergruppen</b><br>
	Ist dieses Kästchen ausgewählt, wird die Gebühr auf alle Broker jeder beteiligten Brokergruppe 
	angewandt. Wenn Sie die Gebühr nur für bestimmte Broker aus bestimmten Brokergruppen anwenden 
	möchten, dann entfernen Sie bitte dieses Häkchen, und wählen Sie aus 
	dem daraufhin erscheinenden Listenfeld, welche Brokergruppen Sie beteiligen möchten.
	<li><b>Kommission wird gezahlt:</b>
	Hier können Sie definieren, wann die Kommission abgebucht wird. Dies kann sein:
	<ul>
		<li><b>Immer:</b>
		Die Broker-Kommissionen werden immer gezahlt (unendlich). (Stoppen können Sie die 
		Kommission über die Seite &quot;Mitgliedsprofil > Broker festlegen&quot;).
		<li><b>Überweisungen:</b>
		Die Zahlung der Broker-Kommissionen wird nach einer bestimmten Anzahl von Überweisungen 
		eingestellt. Wenn Sie diese Option wählen, dann geben Sie bitte die Anzahl in das vor 
		der Auswahlbox erscheinende Eingabefeld ein.
		<li><b>Tage:</b>
		Die Zahlung der Broker Kommissionen wird nach einer bestimmten Anzahl von Tagen 
		eingestellt. Nachdem Sie diese Option gewählt haben, geben Sie bitte die Anzahl von 
		Tagen in das vor der Auswahlbox erscheinende Eingabefeld ein.
	</ul>
</ul>
<hr class="help">
</span>

<span class="admin broker">
<A NAME="commission_settings"></A>
<h3>Kommission Einstellungen:</h3>
In diesem Fenster können Sie die Standard-<a href="#commission"><u>Kommissions</u></a>-Einstellungen 
überprüfen. Diese Einstellungen werden angewandt auf alle neu registrierten 
Mitglieder, außer sie werden durch die vom Broker in seinen Einstellungen getätigten 
Änderungen, oder durch individuelle Verträge zwischen Brokern und Mitgliedern, aufgehoben.
<br>Wenn die Administration keine Kommissionseinstellungen definiert hat, ist dieses 
Fenster leer. Wenn Sie die Kommissionsverwaltung für Mitglieder und Broker aktiviert 
haben, sollten Sie auch eine Standardkommission definieren. Dazu lesen Sie bitte den 
oberen Teil dieses Textes.
</span>
<span class="broker">
In diesem Fenster können Sie die Standard-<a href="#commission"><u>Kommissions</u>
</a>-Einstellungen festlegen. Diese Einstellungen finden dann auf alle IHRE neuen Mitglieder Anwendung. 
Solange Sie die Standardkommission nicht in diesem Fenster definiert haben, kann die 
Administration die Standard-Kommissionseinstellung ändern. Sobald Sie in dieser Maske 
Ihre Kommissionseinstellung getätigt haben, hebt diese die Administrationseinstellung auf.
<br>Bitte beachten Sie, dass von Ihnen mit Ihren Mitgliedern abgeschlossene individuelle 
Verträge die von Ihnen hier getätigten Einstellungsänderungen aufheben.
<br>
Wenn die Administration keine Kommissionseinstellungen definiert hat, ist dieses Fenster leer. 
In diesem Fall sollten Sie sie darüber informieren.
<br>
Die Einstellungen können Sie ändern, indem Sie die Schaltfläche &quot;Bearbeiten&quot; 
anklicken; benutzen Sie die Schaltfläche &quot;Weiter&quot;, um Ihre Änderungen zu speichern. 
Dies ist nur dann sichtbar, wenn Sie die Berechtigungen zur Änderung der Standardeinstellungen 
haben.<br>
Der Status kann nicht verändert werden. 
</span>
Status kann die folgenden Werte haben: <br>
<ul>
	<li><b>Aktiv:</b>	Dies bedeutet, dass alle konfigurierten Broker-Kommissionen erhoben werden, 
	wenn die Kriterien erfüllt sind.
	<li><b>Inaktiv:</b>
	Dieser Status bewirkt, dass keine Kommissionen erhoben werden können. Ist dies 
	der Fall, so gilt die Konfiguration für das gesamte System und wurde von einem Administrator 
	eingestellt.
	<li><b>Unterbrochen:</b>
	Die Broker-Kommissionen sind vorübergehend aufgehoben. 
</ul>
<span class="admin"> Ein Administrator hat die Möglichkeit, alle aktiven Kommissionen 
zeitweise aufzuheben. Auch wenn die Broker-<a href="#commission_contract"><u>Verträge</u></a>
gesperrt sind, kann ein Broker neue Kommissionsverträge hinzufügen; sie gehen allerdings 
direkt in den Status &quot;Unterbrochen&quot; über.
</span>
<hr class="help">

<a name="commission_contract"></a>
<h2>Kommissionsabkommen</h2>
Ein Kommissionsabkommen ist eine Vereinbarung zwischen einem Broker und einem Mitglied. 
Normalerweise erhält der Broker entweder einen Prozentsatz jeder Zahlung des Mitglieds 
oder aber einen fixen Betrag für jede Zahlung. Diese <a href="#commission"><u>Kommission</u></a> 
kann entweder vom zahlenden Mitglied, dem empfangenden Mitglied oder aber durch die 
Organisation entrichtet werden (vom Systemkonto).<br>
Je nach Konfiguration steht es Brokern frei, mit allen ihnen zugeordneten Mitgliedern 
individuelle Verträge abzuschließen. Das Mitglied muss diesem Vertrag zustimmen, bevor er 
Anwendung finden kann – das Mitglied kann also dem vorgeschlagenen Brokerabkommen entweder 
zustimmen oder ablehnen. 

Das Mitglied kann die Kommissionsdetails anschauen, und beide, das Mitglied und der 
Broker, erhalten eine Benachrichtigung, wenn sich der Status der Kommission geändert hat.
<br><br>
<span class="broker">
Je nach Systemeinstellungen kann der Broker die Kommission je Mitglied verändern. 
Pro Zeitbereich kann es nur eine aktive Kommission geben (solange die Zeitbereiche sich 
nicht überschneiden, kann es mehr als eine Kommission geben).<br> 
<br>Anmerkung: Wird ein Broker zu irgendeiner anderen Gruppe verschoben, so werden alle 
Verträge und laufenden Kommissionen beendet.
<br><br>
<i>Wo ist es zu finden (für Broker)?</i><br>
Kommissionsabkommen finden Sie über das &quot;Menü: Brokering > Kommissionsabkommen&quot;.
Sie können über das Mitgliedsprofil dem Mitglied einen neues Kommissionsabkommen im Fenster
&quot;Brokering-Aktionen&quot;, über die Schaltfläche &quot;Kommissionsabkommen&quot;. 
Am unteren Ende der Seite finden Sie die Schaltfläche, um einen neuen Vertrag zu erstellen.
</span>
<span class="member">
<i>Wo ist es zu finden (für Mitglieder)?</i><br>
Die Brokerabverträge finden Sie im &quot;Menu: Persönlich > Brokerkommission&quot;. 
Dies ist nur dann sichtbar, wenn Sie die Berechtigung dazu haben.
</span>
<hr class="help">

<span class="admin broker">
<A NAME="commission_contracts_search_filters"></A>
<h3>Suche Broker-Kommissionsverträge</h3>
In diesem Fenster können Sie nach bestehenden <a href="#commission_contract"><u>
Kommissionsverträgen</u></a> suchen.<br>
Die meisten Suchoptionen erklären sich selbst. Eine Erklärung des Status finden Sie
<a href="#commission_contract_status"><u>hier</u></a>. 
<hr class="help">
</span>

<span class="admin broker">
<A NAME="commission_contracts_search_results"></A>
<h3>Suche Kommissionsverträge (Ergebnisse)</h3>
Dieses Fenster zeigt eine Liste aller <a href="#commission_contract"><u>
Kommissionsverträge</u></a> und deren <a href="#commission_contract_status"><u>Status</u></a>.
<ul>
	<li><img border="0" src="${images}/edit.gif" width="16" height="16">
	Wählen Sie das Bearbeiten-Symbol, um die Details anzuschauen.
	<li><img border="0" src="${images}/delete.gif" width="16" height="16">
	Klicken Sie das Löschen-Symbol, um das Abkommen zu löschen. Dies ist nur dann 
	sichtbar, wenn Sie die Berechtigungen haben, und der Vertrag keinen aktiven Status hat.
</ul>
<hr class="help">
</span>

<a name="commission_contract_status"></a>
<h3>Status Kommissionsabkommen</h3>
Ein <a href="#commission_contract"><u>Kommissionsabkommen</u></a> kann die folgenden Status haben:
<ul>
	<li><b>Anstehend: </b>
	der Vertrag hat den Status &quot;Anstehend&quot; inne, bis das Mitglied ihn bestätigt 
	und ihm zugestimmt hat.
	<li><b>Akzeptiert: </b>
	Dieser Status bedeutet dass die Kommission vom Mitglied akzeptiert wurde, das Startdatum 
	des Vertrags allerdings noch nicht eingetreten ist.
	<li><b>Aktiv: </b>
	Sobald eine Broker-Kommission akzeptiert wurde und das Startdatum erreicht ist, geht 
	die Kommission in den aktiven Status über, was bedeutet, dass die Kommission je nach 
	den Einstellungen des Vertrages erhoben wird.
	<li><b>Abgelehnt: </b>
	Wird der Vertrag vom Mitglied abgelehnt, so hat er den Status &quot;Abgelehnt&quot;.
	<li><b>Abgelaufen: </b>
	Wird ein Vertrag nicht vor dem Startdatum akzeptiert, geht er in den Status &quot;Abgelaufen&quot;.
	<li><b>Storniert: </b>
	Ein Broker kann einen Vertrag stornieren, was bedeutet, dass bei zukünftigen Zahlungen 
	keine Kommissionen mehr generiert werden.
	<li><b>Geschlossen: </b>
	Die Laufzeit des Kommissionsabkommens ist beendet und die Kommissionen wurden erhoben.
</ul>
<hr class="help">

<A NAME="commission_charge_status"></A>
<h3>Kommissions-Status</h3>
Dieses Fenster zeigt eine kurze Übersicht der derzeitigen
<a href="#commission"><u>Kommissionen</u></a>.
Hierbei kann es sich um eine Standardkommission oder um einen individuellen 
Broker-<a href="#commission_contract"><u>Kommissionsabkommen</u></a> handeln.
Die Information erklärt sich von selbst. Der Vertrag kann eine der 
<a href="#commission_contract_status"><u>Status</u></a>-Optionen innehaben..
<hr class="help">

<A NAME="commission_contracts_list"></A>
<h3>Liste Kommissionsverträge</h3>
Dieses Fenster zeigt eine Liste aller <a href="#commission_contract"><u>
Kommissionsverträge</u></a> für dieses Mitglied und deren 
<a href="#commission_contract_status"><u>Status</u></a>.
<span class="admin broker">
<ul>
	<li><img border="0" src="${images}/view.gif" width="16" height="16">&nbsp;
	Wählen Sie das Anzeigen-Symbol, um die Details anzuschauen. 
	<li><img border="0" src="${images}/delete.gif" width="16" height="16">&nbsp;
	Klicken Sie das Löschen-Symbol, um das Abkommen zu löschen. Dies ist nur dann 
	sichtbar, wenn Sie die Berechtigungen haben, und der Vertrag keinen aktiven Status hat.
	<li>ein neues Kommissionsabkommen können Sie <b>hinzufügen</b> indem Sie in der Liste 
	&quot;Neuer Vertrag&quot; unten rechts in diesem Fenster eine Typenwahl treffen.
	Wenn die Administration keine Kommissionseinstellungen definiert hat, ist diese 
	Liste leer. In diesem Fall sollten Sie sie darüber informieren.
</ul>
</span>
<span class="member">
Wählen Sie das <img border="0" src="${images}/view.gif" width="16" height="16">&nbsp;
Anzeigen-Symbol um die Details anzuschauen. Dies bringt Sie zu einem Fenster in dem 
Sie das vorgeschlagene Vertrag akzeptieren oder ablehnen können, falls der Status 
&quot;anstehend&quot; ist.
</span> 
<hr class="help">

<A NAME="commission_contract_edit"></A>
<h3>Broker-Kommissionsabkommen ändern/anzeigen</h3>
Dieses Fenster zeigt die Details des von Ihnen gewählten <a href="#commission_contract"><u>
Kommissionsabkommenes</u></a>.
Um eine Übersicht der möglichen Werte im Status-Feld zu erhalten, 
<a href="#commission_contract_status"><u>Klicken Sie bitte hier</u></a>.<br>
Einmal aktiviert kann ein Kommissionsabkommen nicht mehr gelöscht werden. Allerdings 
können Sie durch anklicken der Schaltfläche &quot;Stornieren&quot; das Kommissionsabkommen
<a href="#commission_contract_status"><u>stornieren</u></a>.
<br><br>
<span class="member">Ist der Status &quot;anstehend&quot;, so handelt es sich um einen 
Ihnen von Ihrem Broker neu vorgeschlagenen Vertrag. In diesem Fall können Sie die 
vorgeschlagene Vereinbarung. Ein vorgeschlagenes Brokerabkommen wird nicht angewandt 
bis Sie es akzeptiert haben.</span>
<hr class="help">

<A NAME="commission_contract_insert"></A>
<h3>Broker-Kommissionsabkommen einfügen</h3>
Das Fenster ermöglicht Ihnen, für das von Ihnen gewählte Mitglied einen neuen  
<a href="#commission_contract"><u>Kommssionsabkommen</u></a> auf zu setzen.<br>
Der Status ist stets &quot;anstehend&quot;. Der Vertrag ändert seinen Status nur
wenn ihn das Mitglied aktzeptiert oder ablehnt - was jedoch nicht möglich ist während Sie
das Abkommen erstellen.<br><br>
Sie müssen sowohl das &quot;Startdatum&quot;, &quot;Ablaufdatum&quot; als auch den Betrag
für den neuen Vertrag einsetzen. Für die Auswahl der Daten können Sie das Kalendersymbol 
<img border="0" src="${images}/calendar.gif" width="16" height="16">&nbsp;verwenden.<br><br> 
Ist er erst einmal aktiviert, kann der Kommissionsabkommen nicht gelöscht werden. 
Durch anklicken der Schaltfläche <a href="#commission_contract_status"><u>Stornieren</u></a>  
können Sie das Abkommen allerdings aufheben.  


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

