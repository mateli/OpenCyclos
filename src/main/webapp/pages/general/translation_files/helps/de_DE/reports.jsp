<div style="page-break-after: always;">
<br><br>
Berichte ermöglichen Ihnen, eine Übersicht über den Systemstatus zu erhalten. 
Es stehen verschiedene Arten von Berichten zur Verfügung:
<ul>
	<li><b>Persönliche Aktivitätsberichte für ein Mitglied</b>
	<li><b>Tabellen mit individuellen Auswertungen von Mitgliedern</b>
	<li><b>Systemweite Auswertung</b>
</ul>
Die Berichte werten die Anzahl der Überweisungen, den Umsatz, die Anzahl der Inserate 
und die Anzahl der eingestellten Referenzen aus.

Im Gegensatz zu Berichten bietet Cyclos aber auch <a href="${pagePrefix}statistics"><u>
Statistiken</u></a>, also erweiterte Berichte mit statistischer Analyse.
<br><br>
<i>Wo ist es zu finden?</i><br>
Auf Berichte kann auf folgende Weisen zugegriffen werden:
<span class="admin">
Für Berichte gibt es ein Menüeintrag im Hauptmenu: &quot;Menü: Berichte&quot;. Dieses enthält
mehrere Untermenüs.</span>
Individuelle Berichte zu Mitgliedern können über das <a href="${pagePrefix}profiles"><u>
Profil</u></a> des Mitglieds erstellt werden.
<span class="admin">
<br><br><i>Wie werden die Berichte aktiviert?</i><br>
Alle Arten von Berichten bedürfen einer <a href="${pagePrefix}groups#manage_group_permissions_admin_system"><u>
Berechtigung</u></a>. Diese müssen aktiviert werden, bevor der Bericht gezeigt werden kann. Die
Berechtigungen finden Sie in den Gruppen-Berechtigungen im Bereich &quot;Berichte&quot;.
</span><hr>

<a name="member_activities"></a>
<h3>Meine Berichte / Mitglieder-Aktivitäten</h3>
Hier können Sie einen kurzen Bericht mit aktuellen Daten entweder für Sie selbst oder ein anderes Mitglied ansehen.
Ebenso sehen Sie Daten zu <a href="${pagePrefix}references"><u>Referenzen</u></a>,
und zu <a href="${pagePrefix}advertisements"><u>Inseraten</u></a>, wie auch zu 
<a href="${pagePrefix}invoices"><u> Rechnungen</u></a>.<br>
Falls es sich bei den Daten um Ihre eigenen handelt, so können Sie auch die Daten in Zusammenhang mit 
Ihren Konten und Ihren  
<a href="${pagePrefix}invoices"><u>Rechnungen</u></a> zeigen.
<br><br>
Mit entsprechenden Berechtigungen können Informationen anderer Mitglieder eingesehen werden.
Dies ist abhängig von der Systemkonfiguration.

<hr class="help">

<span class="admin">
<a name="current_state"></a>
<h3>Zustandsübersicht</h3>
&quot;Zustandsübersicht&quot; ermöglicht es Ihnen, eine Übersicht über den 
derzeitigen Status des Systems zu erhalten.
<a href="#current_result"><u>Klicken Sie hier</u></a> um eine Übersicht 
zu erhalten.
<br><br>
Verwenden Sie die Kontrollkästchen um auszuwählen, was Sie zeigen möchten, 
und klicken dann auf &quot;Weiter&quot;. <a name="current_result"></a><br>
Verwenden Sie &quot;Aktuelle Zeit&quot; wenn Sie den aktuellen Status  
vom System erhalten möchten und &quot;Früherer Zeitpunkt&quot; um eine 
ältere Zustandsübersicht zu erhalten. 

<h3>Zustandsbericht: Ergebnisse</h3>
Auf dieser Seite wird folgendes gezeigt:
<ul>
	<li><b>Mitglieder und <a href="${pagePrefix}groups"><u>Gruppen</u></a>
	</b>: Dieser Abschnitt zeigt die Anzahl der aktivierten Mitglieder; also aller Mitglieder, 
	die sich in diese Software anmelden können. Im Gegensatz dazu zeigt der Rest der Seite 
	alle Mitgliedsgruppen und die Anzahl ihrer jeweiligen Mitglieder.
	<br><br>
	<li><b><a href="${pagePrefix}advertisements"><u>Inserate:</u></a></b>
	<ul>
		<li><b>Anzahl der aktiven Mitglieder mit Inseraten:</b> alle Mitglieder, 
		die sich anmelden können, und die ein oder mehrere Inserate geschaltet haben.
		<li><b>Anzahl der aktiven Inserate:</b> alle am heutigen Tag gültige Inserate.
		<li><b>Anzahl der abgelaufenen Inserate:</b> alle abgelaufenen Inserate.
		<li><b>Anzahl der geplanten Inserate:</b> alle bereits gespeicherten Inserate, 
		die in der Zukunft gültig werden.
		<li><b>Anzahl Dauerinserate:</b> alle Inserate die ohne Zeitbegrenzung aktiv sind.
	</ul>
	<br><br>
	<li><b>Systemkonten:</b> Dieser Abschnitt zeigt alle Systemkonten und deren derzeitige Salden.
	<br><br>
	<li><b>Mitgliedskonten:</b> Dieser Abschnitt zeigt alle Mitgliedskonten und die Summe der Salden. 
	Normalerweise gibt es nur einen Mitgliedskontentyp im System. Alle Mitglieder haben dann ein 
	Konto dieses Typs.
	<br><br>
	<li><b><a href="${pagePrefix}invoices"><u>Rechnungen:</u></a></b>
	<ul>
		<li><b>Anzahl der Mitgliedsrechnungen:</b> Anzahl aller eingehenden und ausgehenden 
		Rechnungen zwischen Mitgliedern.
		<li><b>Summe der Mitgliedsrechnungen:</b> Gesamtbetrag der eingehenden und ausgehenden 
		Rechnungen zwischen Mitgliedern.
		<li><b>Anzahl der eingehenden Systemrechnungen:</b> Anzahl aller Rechnungen von Mitglied an Systemkonten.
		<li><b>Summe der eingehenden Systemrechnungen:</b> Gesamtbetrag der Rechnungen von Mitglied an Systemkonten.
		<li><b>Anzahl der ausgehenden Systemrechnungen:</b> Anzahl aller Rechnungen vom System an Mitgliedskonten.
		<li><b>Summe der ausgehenden Systemrechnungen:</b> Gesamtbetrag der Rechnungen vom System an Mitgliedskonten.
	</ul>
	<br><br>
	<li><b><a href="${pagePrefix}loans"><u>Darlehen:</u></a></b>
	<ul>
		<li><b>Anzahl offener Darlehen:</b> Alle Darlehen an Mitglieder, die noch nicht vollständig zurück gezahlt sind.
		<li><b>Verbleibender Betrag offener Darlehen:</b> Summe aller verbleibenden (offenen) Darlehenszahlungen.
	</ul>
	<br><br>
	<li><b><a href="${pagePrefix}references"><u>Referenzen:</u></a></b><br>
	Diese Tabelle zeigt die Anzahl der gegebenen Referenzen, für jede der 
	unterschiedlichen Referenzkategorien.
</ul>
<hr class="help">

<a name="member_lists"></a>
<h2>Mitgliedslisten</h2>
Diese Funktion zeigt Ihnen eine Liste der Mitglieder zusammen mit verschiedenen Daten 
zu jedem dieser Mitglieder. Sie können diese Daten für den gegenwärtigen Zeitpunkt 
anfordern, oder aber einen Zeitpunkt in der Vergangenheit wählen.<br>
Zu folgenden Themen können Sie Daten anfordern:
<ul>
	<li>Mitglieds- und Brokername
	<li>Inserate
	<li>Referenzen
	<li>Kontolimits und Salden
</ul>

Bitte beachten Sie, dass es sich bei allen diesen Daten um eine &quot;punktuelle Erfassung&quot; 
handelt, also um Daten zu einem spezifischen Zeitpunkt. Das hat zur Folge, dass Information 
zur Geschäftstätigkeit hier nicht möglich sind (punktuelle Information über eine Tätigkeit ist sinnlos). 
Wenn Sie Daten für einen bestimmten Zeitraum möchten, gehen Sie bitte auf
&quot;Menü: <a href="#member_reports"><u>Mitgliedsberichte</u></a>&quot; oder auf 
&quot;Menü: <a href="${pagePrefix}statistics"><u>Statistische Analyse</u></a>&quot;.
<br><br>Falls Sie eine größere Anzahl Kontrollkästchen anklicken, dauert es möglicherweise 
einige Sekunden, bis die Berechnungen fertig sind. Haben Sie also bitte etwas Geduld.<br><br>
Am unteren Ende der Seite können Sie den Befehl zum <a href="#results"><u>Bericht drucken</u></a>
oder <a href="#results"><u>Bericht herunterladen</u></a> der Ergebnisse geben.
<br><br>
Die folgenden Optionen sind verfügbar:
<ul>
	<li><b>Zeit:</b> wählen Sie bitte zuerst den Zeitpunkt, für den Sie die Liste anfordern. 
	Hier gibt es zwei Optionen:
	<ul>
		<li><b>Aktuelle Zeit:</b> ergibt eine Liste mit aktuellen Daten.
		<li><b>Früherer Zeitpunkt:</b> ergibt eine Liste mit Daten zu einem früheren Zeitpunkt. 
		Wenn Sie diese Option wählen, legen Sie bitte ein Datum fest. Sie können entweder den Zeitpunkt
		direkt in das Eingabefeld eingeben oder aber Sie klicken dafür auf das Kalendersymbol 
		<img border="0" src="${images}/calendar.gif" width="16" height="16">&nbsp;.
	</ul> <br><br>
	<li><b>Broker:</b> In diesem Abschnitt können Sie den Namen oder den Benutzernamen 
	eines <a href="${pagePrefix}brokering"><u>Brokers</u></a> spezifizieren. Dies begrenzt 
	Ihre Datenliste, auf die zu diesem Broker gehörenden Mitglieder.<br><br>
	<li><b>Mitglieder:</b> In diesem Abschnitt können Sie Folgendes spezifizieren:
	<ul>
		<li><b>Mitgliedsname:</b> dieses Kästchen anklicken, wenn Sie möchten, dass der Name des 
		Mitglieds in der Liste steht. Wenn Sie dieses Kästchen nicht anklicken, 
		werden die Daten des Mitglieds anonym ausgedruckt.
		<li><b>Broker-Benutzername:</b> Wenn Sie dieses Kästchen anklicken, steht der Benutzername 
		des Brokers in der Liste. 
		<li><b>Brokername:</b> Wenn Sie dieses Kästchen anklicken, steht der Name 
		des Brokers	in der Liste.
		<li><b>Gruppen:</b> Benutzen Sie das Mehrfachauswahlfeld um die Gruppen zu wählen, für die 
		Sie diese Liste erstellen wollen. Wenn Sie zum Beispiel &quot;Vollwertige Mitglieder&quot; wählen, 
		so werden alle Mitglieder dieser Gruppe berücksichtigt (abhängig davon natürlich, 
		was Sie im Abschnitt &quot;Broker&quot; eingegeben haben). 
	</ul><br><br>
	<li><b>Inserate:</b> In diesem Abschnitt spezifizieren Sie, welche Daten Sie zum Thema
	<a href="${pagePrefix}advertisements"><u>Inserate</u></a> einbeziehen möchten. Für jedes 
	Kontrollkästchen gilt: wenn ausgewählt, wird die Anzahl der Inserate mit diesem 
	spezifischen <a href="${pagePrefix}advertisements#ad_status"><u>Status</u></a> gedruckt.
	<ul>
		<li><b>Aktive Inserate</b>
		<li><b>Abgelaufene Inserate</b>
		<li><b>Dauerinserate</b>
		<li><b>Geplante Inserate</b>
	</ul><br><br>
	<li><b>Referenzen:</b> In diesem Abschnitt können Sie spezifizieren, ob Sie Informationen zu 
	&quot;erteilten&quot; und &quot;erhaltenen&quot; <a href="${pagePrefix}references"><u>Referenzen</u></a> 
	zeigen wollen.
	<br><br>
	<li><b>Konten:</b> In diesem Abschnitt können Sie spezifizieren, welche Kontoinformationen Sie zeigen möchten:
	<ul>
		<li><b>Unteres Kreditlimit</b>
		<li><b>Oberes Kreditlimit</b>
		<li><b>Kontosaldo</b>
	</ul>
</ul>
<hr>

<a name="member_reports"></a>
<h2>Mitgliedsberichte</h2>
Diese Funktion ermöglicht Ihnen, einen Bericht mit Informationen zu Überweisungen 
des Mitglieds zu drucken. 

<br><br>ANMERKUNG: Bitte füllen Sie das Formular in der vorgegebenen Reihenfolge aus, 
von oben nach unten. Der Zahlungsfilter kann z.B. leer sein, wenn Sie nicht 
zuvor einen Kontotyp gewählt haben. <br><br>
Am unteren Ende der Seite können Sie den Befehl zum <a href="#results"><u>Bericht drucken</u></a>
oder <a href="#results"><u>Bericht herunterladen</u></a> der Ergebnisse geben.
<br><br>
Auf dem Formular können Sie die folgenden Elemente spezifizieren:
<ul>
	<li><b>Mitgliedsname:</b> der Benutzername wird immer gezeigt. Wenn Sie möchten, 
	dass der Mitgliedsname gezeigt wird, so müssen Sie dies hier wählen.
	<li><b><a href="${pagePrefix}brokering"><u>Broker</u></a>-Benutzername und Name:</b> Diese 
	Information wird nur gezeigt wenn Sie diese Kontrollkästchen wählen.
	<li><b>Mitgliedergruppen:</b> hier können Sie spezifizieren, welche
	<a href="${pagePrefix}groups"><u>Mitgliedergruppen</u></a> Sie zeigen möchten.
	<li><b>Kontotyp:</b> üblicherweise hat eine Gruppe von Mitgliedern einen
	<a href="${pagePrefix}account_management#accounts"><u>Kontotyp</u></a>.
  Es ist allerdings möglich, dass Mitglieder mehrere Kontotypen haben. Spezifizieren 
  Sie hier bitte, welchen Typ Sie zeigen möchten.
	<li><b>Datumsfelder &quot;Von&quot; und &quot;Bis&quot;:</b> um den Zeitraum einzugrenzen.
	<li><b>Was anzeigen?:</b> dies ist möglicherweise das wichtigste Feld. Hier geben Sie 
	an, ob Sie Überweisungen oder Rechnungen zeigen wollen.<br>
	Wenn Sie Überweisungen gewählt haben:
	<ul>
		<li><b>Zahlungsfilter</b> ermöglicht Ihnen zu spezifizieren, welche Art von 
		Überweisungen Sie aufgelistet haben möchten. Die
		<a href="${pagePrefix}account_management#transaction_types"><u>Zahlungsfilter</u></a>
		können im Abschnitt <a href="${pagePrefix}account_management#account_search"><u>&quot;Konten 
		verwalten&quot;</u></a> spezifiziert werden. Damit der Zahlungsfilter in der Berichtsfunktion 
		sichtbar sein kann, muss für ihn die Option &quot;In den Berichten anzeigen&quot; gewählt werden.
		<li><b>Eingehende/Ausgehende Überweisungen:</b>
		nachdem Sie einen Zahlungsfilter gewählt haben, wird dies zu sehen sein. Hier müssen Sie 
		<b>unbedingt</b> mindestens eines der Kontrollkästchen wählen. Eine Ausgehende Überweisung 
		ist eine Zahlung.
		<li><b>Mitglieder ohne Überweisungen berücksichtigen:</b> wird dieses Kästchen gewählt, 
		so werden auch Mitglieder ohne Überweisungen berücksichtigt.
		<li><b>Detailgenauigkeit</b> ermöglicht Ihnen zu spezifizieren, 
		wie viele Details Sie sehen möchten.
		<ul>
			<li><b>Zusammenfassung</b> zeigt Ihnen lediglich die Gesamtsumme aller Überweisungen innerhalb des Zeitraums, 
			als eine Zeile pro Mitglied.
			<li><b>Überweisungen</b> listet alle Überweisungen innerhalb des 
			Zeitraums, für jedes Mitglied mit Überweisungstyp und Überweisungsnummer (wenn verwendet).
		</ul>
	</ul>
</ul>
<hr class="help">


<a name="results"></a>
<h3>Ergebnisse der Berichte</h3>
Sie haben die Wahl zwischen zwei weiteren Aktionen, die Ergebnisse betreffend 
(Schaltflächen auf der Seite unten rechts):
<ul>
	<li><b>Bericht drucken:</b> druckt den Bericht, in einem druckbaren Layout. 
	Diese Anzeige enthält ebenfalls eine Schaltfläche 	&quot;Drucken&quot; um den Bericht 
	an Ihren Drucker zu senden. 
	<li><b>Bericht herunterladen:</b> lädt den Bericht als Liste im 
	CSV-Format herunter.
</ul>
<hr class="help">
</span>


<a name="sms_log"></a>
<h3>SMS Nachrichtenprotokolle</h3>
Das System kann zu unterschiedlichen Gelegenheiten SMS-Nachrichten versenden (zum Beispiel 
Zahlungen via SMS), je nach Konfiguration.
<span class="admin"> 
Für mehr Information zu dieser Konfiguration schauen Sie bitte unter 
<a href="${pagePrefix}settings#local"><u>Basiseinstellungen</u></a> nach.</span>
<br><br>Dieses Fenster ermöglicht Ihnen einen Überblick darüber, welche SMS-Nachrichten 
versandt wurden. Das Formular ist unkompliziert und die meisten Elemente bedürfen 
keiner weiteren Erklärung. Das Listenfeld &quot;Status&quot;ermöglicht Ihnen zu 
erkennen, ob das Versenden der SMS erfolgreich war oder fehlgeschlagen ist.
<br>
Nach Anklicken von &quot;Suche&quot; werden die Ergebnisse in einem darunter befindlichen 
Fenster gezeigt.


<span class="admin"> 
<a name="sms_log_search_results"></a>
<h3>Suchergebnisse gesendete SMS-Nachrichten</h3>
Dieses Fenster zeigt das Ergebis der oberen Suche an.
<hr class="help">
</span>


<span class="admin"> 
<a name="sms_log_report"></a>
<h3>SMS Protokollbericht</h3>
In diesem Fenster können Sie nach ausgehenden SMS-Nachrichten suchen. Die Suchfilter
sind selbsterklärend. Es gibt zwei Arten ausgehender SMS-Nachrichten. Nachrichten
die im Zusammenhang mit einer Zahlungsbestätigung 
und persönliche SMS-Benachrichtigungen (z.B. Benachrichtigungen über neue Inserate).
<br><br>
Ausgehende Nachrichten werden den Organisationen berechnet
(durch das SMS-Gateway oder Betreiber) und es ist daher wichtig den Überblick
über die gesendeten SMS-Nachrichten zu behalten.<br>
Die Konfiguration über die Belastung der SMS ist in <a
href="${pagePrefix}groups#group_notification_settings"><u>Benachrichtigungseinstellungen</u></a> 
(Gruppeneinstellungen) definiert.
<br><br>
Anmerkung: Mitglieder können ihre eigenen ausgehenden SMS-Nachrichten in &quot;Persönlich - SMS-Protokolle&quot; ansehen.
<hr class="help">
</span>


<span class="admin"> 
<a name="sms_log_report_search_results"></a>
<h3>Suchergebnisse SMS-Protokolle</h3>
Dieses Fenster zeigt das Ergebis der oberen Suche an. Sie können die Ergebnisse über die
entsprechenden Symbole ausdrucken und exportieren.
<hr class="help">
</span>
