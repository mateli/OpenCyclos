<div style="page-break-after: always;">
<br><br>
Ein Mitglied kann eine Zahlung an ein anderes Mitglied oder an ein Systemkonto (Gemeinschaft, 
Organisation, etc) leisten. Falls zutreffend, kann das Mitglied auch Einheiten zwischen seinen 
eigenen Konten überweisen, falls jedes Mitglied mehr als ein Konto für die gleiche Währung hat. 
Zahlungen können auch für zukünftige Termine geplant werden. Alle Zahlungen haben auch eine 
Drucken-Schaltfläche, um einen Überweisungsbeleg auszudrucken. 

<span class="admin">Unter bestimmten Bedingungen können Zahlungen auch rückgängig gemacht werden 
(<a href="#charge_back"><u>Rückbelastung</u></a>).</span>

<br><br><i>Wo ist es zu finden?</i><br>
<span class="member">Zahlungen im Haupt-Kommunikationsweg können von drei verschiedenen Orten
gestartet werden. Über das Menü: 
<ul>
  <li><b>Über das Menü:</b>
	<li><b>Zahlungen an Mitglieder:</b> &quot;Menü: Konto > Zahlung an Mitglied&quot;
	<li><b>Zahlung an System:</b> &quot;Menü: Konto > Zahlung an System&quot;
	<li><b>Über das Profil:</b> Zahlungen an andere Mitglieder können auch über das  
	<a href="${pagePrefix}profiles"><u>Profil</u></a> des jeweiligen Mitglieds gestartet werden.
</ul>
</span> 
<span class="broker"> Ein Broker kann Zahlungen für seine Mitglieder über deren  
<a href="${pagePrefix}profiles"><u>Profil</u></a> starten. Dies beinhaltet Zahlungen 
an andere Mitglieder und Zahlungen an Systemkonten.  
<br><br>Ein Broker kann auch Zahlungen eines Mitglieds  <a href="#authorized"><u>autorisieren</u></a>. 
Zu dieser Funktion gelangen Sie über das Hauptmenü: &quot;Menü: Brokering > Zu autorisieren&quot; 
und &quot;Menü: Brokering > Autorisierungen&quot;.<br>
</span> <span class="admin"> Zahlungen können von verschiedenen Orten aus gestartet werden. Die 
Verfügbarkeit der erwähnten Optionen hängt natürlich ab von den Einstellungen Ihrer Organisation 
und den Berechtigungen der verschiedenen Gruppen.  
<ul>
	<li><b>Profil:</b> Vom <a href="${pagePrefix}profiles"><u>Profil</u></a>
	eines Mitglieds aus können Sie sowohl Zahlungen an andere Mitglieder tätigen als 
	auch Zahlungen an das System. 
	<li><b>Zahlungen System an System:</b> starten Sie über  &quot;Menü: Konten > Systemzahlung&quot;. 
	Dies sind Überweisungen von einem Systemkonto an ein anderes Systemkonto. 
	<li><b>Zahlungen System an Mitglied:</b> starten Sie über &quot;Menü:	Konten > Zahlung an Mitglied&quot;. 
	Dies sind Überweisungen von einem Systemkonto an ein Mitglied. 
</ul>
Außerdem gibt es verschiedene Typen von besonderen Zahlungen. Zu den meisten davon 
gelangen Sie über das Hauptmenü: 
<ul>
	<li><b><a href="#authorized"><u>Autorisierungen:</u></a></b> finden Sie über 
	&quot;Menü: Konten > Zu autorisieren&quot; und &quot;Menü: Konten >	Autorisierungen&quot;.
	<li><b><a href="#scheduled"><u>Geplante Zahlungen:</u></a></b> finden Sie über 
	&quot;Menü: Konten > Geplante Zahlungen&quot;.
	<li><b>Darlehensrückzahlungen:</b> finden Sie über &quot;Menü: Konten > Darlehenszahlungen&quot;; 
	dieses Thema wird auch in der <a href="${pagePrefix}loans"><u>Hilfedatei für Darlehen</u></a> 
	behandelt.
</ul>
<br>
</span> Bitte beachten Sie, dass Sie neben dem Ausführen direkter Zahlungen auch durch Reagieren 
auf eine <a href="${pagePrefix}invoices"><u>Rechnung</u></a> bezahlen können. 

<span class="admin">
<br><br><i>Wie werden Zahlungen aktiviert?</i><br>
Das Wichtigste ist hier, dass es für jede Zahlung einen Überweisungstyp geben muss. Wenn Sie für eine 
Zahlung keinen entsprechenden Überweisungstyp definiert haben, kann die Zahlung nicht ausgeführt werden. 
Überweisungstypen verwalten Sie durch Verwaltung des Kontos, von dem aus sie getätigt werden. Dafür 
gehen Sie bitte zu &quot;Menü: Konten > Konten verwalten&quot;, und wählen Sie den Kontentyp des 
Auftraggebers. Dort erhalten Sie eine <a href="${pagePrefix}account_management#transaction_type_search"><u>
Übersicht</u></a> aller für dieses Konto verfügbaren Überweisungstypen; 
dort können Sie auch einen neuen Typ hinzufügen (wenn Sie die dazu notwendigen Berechtigungen haben).<br> 
Sobald es einen Überweisungstyp gibt, müssen Sie immer noch die Berechtigungen für deren Verwendung durch 
die verschiedenen Gruppen einstellen.
<ul>
	<li>Administratoren können die <a href="${pagePrefix}groups#manage_group_permissions_admin_system"><u>
	Berechtigungen</u></a> zur Ausführung von Systemzahlungen haben. Der Abschnitt &quot;Systemzahlungen&quot; 
	enthält verschiedene Einstellungen.
	<li>Administratoren können die <a href="${pagePrefix}groups#manage_group_permissions_admin_member"><u>
	Berechtigungen</u></a> zur Ausführung von Mitgliedszahlungen haben. Der Abschnitt &quot;Mitgliedszahlungen&quot; 
	enthält verschiedene Einstellungen.
	<li>Auch für Mitglieder müssen Sie die <a href="${pagePrefix}groups#manage_group_permissions_member"><u>
	Berechtigungen</u></a> zur Ausführung von Zahlungen einstellen; dies tun Sie über verschiedene Einstellungen 
	im Abschnitt &quot;Zahlungen&quot;. Ein solcher Abschnitt ist auch für Broker verfügbar. 
	<li>Broker können die <a href="${pagePrefix}groups#manage_group_permissions_broker"><u>
	Berechtigungen</u></a> zur Ausführung von Zahlungen für Mitglieder haben. Der Abschnitt &quot;Mitgliedszahlungen&quot; 
	enthält diese Einstellungen.
	<li>Für autorisierte und geplante Zahlungen gibt es für jede Hauptgruppe (Mitglieder, Broker, Administratoren) 
	eine Einstellung unter dem Abschnitt &quot;Konto&quot;, der es dieser Gruppe ermöglicht, autorisierte und/oder 
	geplante Zahlungen zu zeigen. 
</ul>

</span>

<hr>

<a name="payments"></a>
<br><br>
<h3>Zahlungen tätigen</h3>
Die Cyclos-Formulare für die Ausführung von Zahlungen haben einige Elemente gemeinsam. 
In dieser kurzen Einführung behandeln wir diese gemeinsamen Elemente, die in Ihrem 
Zahlungsfenster erscheinen können. In den meisten Fällen können Sie einfach nur Betrag 
und Beschreibung eingeben und dann auf &quot;Weiter&quot; klicken. In anderen Fällen 
müssen Sie zusätzlich weitere Felder ausfüllen.<br>
Bitte beachten Sie, dass es wichtig ist, alle Felder und Optionen in der richtigen Reihenfolge 
zu wählen, das heißt, von oben nach unten. Es funktioniert  z.B. nicht, die Währung zu wählen 
bevor Sie einen Betrag eingegeben haben.  
<br><br>
<ul>
	<li><b>Benutzername / Name:</b> Handelt es sich um eine Zahlung an ein anderes Mitglied, 
	und ist dies aber nicht direkt aus dem Zusammenhang erklärt, müssen Sie entweder den 
	Benutzernamen oder den Namen des empfangenden Mitglieds eingeben. Das Feld ist selbst-vervollständigend: 
	meist ist Eingabe der ersten Buchstaben ausreichend. 
	<li><b>Betrag:</b> einfach den Betrag eingeben. 
	<li><b>Währung:</b> Dieses Feld erscheint direkt hinter dem Betragsfeld. Es erscheint nur 
	dann, wenn mehr als eine Währung möglich ist. Dies hängt ab von der Basiseinstellung Ihrer 
	Organisation. 
	<li><b>Überweisungstyp:</b> Es kann sein, dass mehrere Überweisungstypen möglich sind. In diesem 
	Fall müssen Sie den Überweisungstyp im Auswahlfeld wählen. 
	<li class="admin"><b>Zahlung rückdatieren:</b> Ein Administrator kann auch &quot;Zahlungen in der 
	Vergangenheit&quot; ausführen. Dies wird meist aus Gründen der Nachvollziehbarkeit getan und sollte 
	nur selten stattfinden. Dafür muss eine Freigabe in den <a 
	href="${pagePrefix}groups#manage_group_permissions_admin_member"><u>Mitgliedsberechtigungen für Administratoren</u></a> 
	geschehen. 
	<li><b>Planung:</b> Sind geplante Zahlungen für diesen Zahlungstyp zugelassen, können Sie wählen, die 
	Zahlung zu einem zukünftigen Termin (automatisch) oder als Mehrfachzahlungen zu wiederkehrenden 
	zukünftigen Terminen bearbeiten zu lassen (für mehr Information wenden Sie sich bitte an 
	<a href="#pay_scheduled"><u>Geplante Zahlungen</u></a>) 
</ul>
<br><br>
Danach werden Sie um eine Bestätigung gebeten. Die Einheiten werden direkt von Ihrem Konto auf das Konto 
der anderen Partei überwiesen. Die Überweisung erscheint im Überweisungsverlauf beider Parteien, in dem des 
Auftraggebers (Zahlers) in Rot, mit einem – Zeichen (Minus), in dem des Empfängers in Blau, mit einem + Zeichen 
(Plus) versehen. 
<hr class="help">

<a name="transaction_confirm"></a>
<h3>Überweisungsbestätigung</h3>
In diesem Fenster werden Sie gebeten, die von Ihnen gerade eingegebene Zahlung zu bestätigen. 
Überprüfen Sie gründlich alle Informationen, und klicken Sie auf &quot;Weiter&quot;, wenn alles 
richtig ist. 
<br>
Wurde ein Fehler gemacht, verwenden Sie bitte die Schaltfläche &quot;Zurück&quot;.
<br><br>
Anmerkung: Wenn eine Fehlermeldung &quot;Überweistungstyp ist erforderlich&quot; erscheint, so ist 
das leider noch ein Fehler im Cyclos-System. Dieser Software-Fehler soll in der Cyclos-Version 4 behoben 
werden.  
<hr class="help">

<A NAME="to_member"></A>
<h3>Zahlung an Mitglied</h3>
In diesem Fenster können Sie eine Zahlung an ein Mitglied ausführen. In den meisten Fällen geben 
Sie einfach nur die Beschreibung, den Betrag und den Mitgliedsnamen ein, falls der noch nicht bekannt 
ist, und klicken dann auf &quot;Weiter&quot;.<br>
<a href="#payments"><u>Hier klicken</u></a> um eine allgemeine Beschreibung der Zahlungsfenster zu sehen. 
<hr class="help">

<A NAME="to_system"></A>
<h3 class="admin">Überweisung zwischen Systemkonten</h3>
<h3 class="member">Zahlung an das System</h3>
<span class="admin"> In diesem Fenster können Sie eine Zahlung zwischen Systemkonten tätigen. 
</span>
<span class="member"> In diesem Fenster können Sie eine Zahlung an eines der Organisationskonten oder 
&quot;Systemkonten&quot; tätigen. <a href="#payments"><u>Hier klicken</u></a>, um eine allgemeine Beschreibung 
der Zahlungsfenster zu sehen.  

<hr class="help">

<A NAME="as_member_to_system"></A>
<span class="broker admin">
<h3>Zahlung als Mitglied an System</h3>
In diesem Fenster können Sie als Mitglied eine Zahlung vom Mitgliedskonto an ein Systemkonto 
tätigen. 
<br><br>
<a href="#payments"><u>Hier klicken</u></a>, um eine allgemeine Beschreibung der Zahlungsfenster 
zu sehen. 
<hr class="help">
</span>

<span class="broker admin"> <A NAME="as_member_to_member"></A>
<h3>Zahlung Mitglied an Mitglied</h3>
In diesem Fenster können Sie als Mitglied eine Zahlung vom Mitgliedskonto an ein anderes 
Mitgliedskonto tätigen. 
<br><br><a href="#payments"><u>Hier klicken</u></a>, um eine allgemeine Beschreibung der Zahlungsfenster 
zu sehen. 
<hr class="help">
</span>

<A NAME="member_self_payments"></A>
<h3 class='member'>Überweisung zwischen meinen Konten</h3>
<h3 class='admin'>Überweisung zwischen den Konten des Mitglieds (durch Administrator)</h3>
<span class="admin"> Für einen Administrator ist es (mit den entsprechenden Berechtigungen) möglich, 
eine Eigenzahlung so auszuführen, als sei er das Mitglied. </span>
Eine Eigenzahlung ermöglicht es, eine Überweisung von einem Konto auf ein anderes des gleichen Inhabers 
(Mitglieds) zu tätigen. Eine Selbstzahlung funktioniert genau wie eine ganz gewöhnliche andere Zahlung 
auch. 
<br><br>
<a href="#payments"><u>Hier klicken</u></a>, um eine allgemeine Beschreibung der Zahlungsfenster zu sehen. 
<hr>

<a name="scheduled"></a>
<h2>Geplante Zahlungen</h2>
Die Funktion &quot;geplante Zahlungen&quot; ermöglicht einem Mitglied, zukünftige 
geplante Zahlungen (Ratenzahlungen) an andere Konten einzurichten. Dabei kann es sich 
um eine einzelne geplante Zahlung, um Mehrfachzahlungen als &quot;Paket&quot;, oder um 
regelmäßig wiederkehrende (periodische) Zahlungen (z.B. monatl. Zahlungen) handeln. Die 
Zahlungen werden an den angegebenen Daten automatisch getätigt. 
<br>
Geplante Zahlungen können auch mit Rechnungen kombiniert werden. Ein Mitglied, das eine 
Rechnung an ein anderes Mitglied schickt, kann (falls es dazu berechtigt ist) spezifizieren, 
ob die Zahlung zu einem geplanten (zukünftigen) Datum oder zu unterschiedlichen 
Zahlungszeitpunkten stattfindet. Hat der Rechnungsempfänger die Rechnung akzeptiert, 
so erscheinen die geplanten Zahlungen in seiner Liste für &quot;geplante Zahlungen&quot; 
(und werden an den vom  Auftraggeber angegebenen Terminen abgebucht). 

<span class="admin"> Es ist möglich (in den Systemeinstellungen) alle Typen von Zahlungen 
zu planen. Um geplante Zahlungen zu aktivieren, benötigen Sie Folgendes: 
<ol>
	<li><b>Berechtigungen:</b> Zuerst müssen Sie die Berechtigungen für 
		die Mitgliedsgruppe einstellen. Derzeit gibt es drei Berechtigungen für Mitgliedsgruppen, 
		die aktiviert werden können, die Sie aber vielleicht nicht alle aktivieren möchten. Wenn Sie  
		<a href="${pagePrefix}brokering"><u>Broker</u></a> oder Administratoren erlauben möchten, 
		geplante Zahlungen für das Mitglied auszuführen, sollten Sie auch die Berechtigungen für 
		diese beiden Gruppen überprüfen. 
	<li><b>Gruppeneinstellungen:</b> Für Mitgliedsgruppen gibt es spezielle <a
		href="${pagePrefix}groups#edit_member_group"><u>Gruppeneinstellungen</u></a>
	(&quot;Menü: Benutzer und Gruppen > Gruppen&quot; klicken Sie dann auf das Bearbeiten-Symbol   
	<img border="0" src="${images}/edit.gif" width="16" height="16">&nbsp;
	einer Mitgliedsgruppe) für geplante Zahlungen. Dies wird oft übersehen, bitte also nicht vergessen.
	<li><b>Überweisungstyp:</b> Im <a	href="${pagePrefix}account_management#transaction_types"><u>
	Überweisungstyp</u></a> muss geplante Zahlung aktiviert sein. Im <a
		href="${pagePrefix}account_management#transaction_type_details"><u>Fenster Eigenschaften 
	Überweisungstyp</u></a> gibt es ein eigenes Kontrollkästchen &quot;Geplante Zahlungen zulassen&quot;.<br>
	<b>Anmerkung:</b> Für manche Zahlungstypen ist geplante Zahlung nicht verfügbar. Dabei handelt es sich 
	um den Überweisungstyp Mitglied an System und um Überweisungen vom Typ Eigenzahlung. 
</ol>
Nun sollten geplante Zahlungen funktionieren. In jedem <a	href="#payments"><u>Zahlungsfenster</u></a> 
erscheint nun, falls relevant, ein Auswahlfeld &quot;Geplante Zahlung&quot;. 
<br><br>
Geplante Zahlungen finden Sie über das &quot;Menü: Konten > Geplante Zahlungen&quot;. 
</span>
<span class="member"> 
Ihre geplanten Zahlungen finden Sie über das &quot;Menü: Konto > Geplante Zahlungen&quot;.</span>
<hr class="help">

<a name="pay_scheduled"></a>
<h3>Eine geplante Zahlung tätigen</h3>
In diesem Hilfe-Element geht es um <a href="#scheduled"><u>geplante Zahlungen</u></a>.
Es enthält eine Beschreibung der dazu nötigen speziellen Felder im Fenster Zahlung.
<br><br>Das Auswahlfeld &quot;geplant&quot; enthält drei mögliche Werte: 
<ul>
	<li><b>Nicht geplant (sofort bearbeiten):</b> Wählen Sie dies, wenn Sie keine 
	geplante Zahlung verwenden möchten. 
	<li><b>Für zukünftiges Datum planen:</b> Wenn Sie diese Option wählen, wird die Zahlung 
	 an dem von Ihnen spezifizierten Datum ausgeführt. Das Datum muss im Bearbeitungsfeld 
	 &quot;Geplant für&quot; spezifiziert werden; es sollte erscheinen, wenn Sie diese 
	 Option wählen. Um das Zeitauswahlfeld zu benutzen, klicken Sie bitte auf das 
	 Kalender-Symbol <img border="0" src="${images}/calendar.gif" width="16" height="16">&nbsp;.
	<li><b>Mehrere geplante Zahlungen:</b> Dies ist die komplexeste Form von geplanten Zahlungen.  
	 Durch Auswahl dieser Option können Sie eine einmalige Zahlung in mehrere Teilzahlungen 
	 aufteilen. Für jede dieser Teilzahlungen können Datum und Betrag einzeln eingestellt werden.<br>
   Auf dem Formular finden Sie die folgenden Elemente: 
	<ul>
		<li><b>Anzahl Zahlungen:</b> Die Anzahl der von Ihnen gewünschten Teilzahlungen. Zum 
		Beispiel: 10 Zahlungen, eine in jeder Woche. Der von Ihnen angegebene Betrag wird in 
		gleiche Teile aufgeteilt. 
		<li><b>Erstes Zahlungsdatum:</b> Um das Zeitauswahlfeld zu benutzen, klicken Sie bitte 
		auf das Kalender-Symbol <img border="0" src="${images}/calendar.gif" width="16" height="16">&nbsp;.
		<li><b>Zahlung jede(n):</b> Verwenden Sie dieses Auswahlfeld, um den Zeitraum festzulegen. 
		<li><b>Berechnen:</b> Diese Schaltfläche können Sie verwenden, um zu sehen, wieviel 
		genau an welchem Datum gezahlt wird. Sobald Sie diese Schaltfläche anklicken, 
		erscheint eine Übersicht der Zahlungstermine und der Beträge. Diese Termine und Beträge 
		können Sie ändern, solange Sie darauf achten, dass die Summe immer noch den oben eingegebenen 
		Betrag ergibt.<br>
		Anmerkung: Mit dieser Option wird nichts bearbeitet. Sie dient lediglich der Vorschau auf 
		die Beträge und Termine. 
	</ul>
</ul>
<hr class="help">

<A NAME="scheduled_payments_by_admin"></a>
<A NAME="scheduled_payments_by_member"></a>
<h3>Geplante Zahlungen suchen</h3>
In diesem Fenster können Sie nach <a href="#scheduled"><u>geplanten Zahlungen</u></a> 
suchen. Auf dem Formular finden Sie die folgenden, zu spezifizierenden Elemente: Bitte 
beachten Sie, dass ein freigelassenes Feld alle für dieses Feld möglichen Werte erbringt. 
<ul>
	<li><b>Typ:</b> Hier können Sie &quot;ausgehende&quot; oder &quot;eingehende&quot; Zahlung 
	spezifizieren. Eine &quot;ausgehende&quot; Zahlung ist eine normale Zahlung; &quot;eingehende&quot; 
	Zahlung bedeutet eine <a href="${pagePrefix}invoices"><u>Rechnung</u></a>, auf der der 
	Auftraggeber angegeben hat, dass sie durch geplante Zahlungen bezahlt werden kann. 
	<li><b>Konto:</b> wählen Sie im Auswahlfeld einen Kontotyp. Es erscheint nur dann, wenn mehr 
	als eine Möglichkeit besteht. 
	<li><b>Status:</b> &quot;Offen&quot; bedeutet &quot;noch nicht bezahlt&quot;; der Rest erklärt 
	sich von selbst. 
	<li><b>Benutzername / Name:</b> In diesen beiden Feldern können Sie ein Mitglied spezifizieren, 
	an das die Zahlung getätigt wurde. Die Felder sind auto-vervollständigend. 
	<li><b>Von Datum / Bis Datum:</b> Hier können Sie einen Zeitraum angeben. Um das Zeitauswahlfeld 
	zu benutzen, klicken Sie bitte auf das Kalender-Symbol <img border="0"
		src="${images}/calendar.gif" width="16" height="16">&nbsp;.
</ul>
<hr class="help">

<a name="view_scheduled_payment"></a>
<h3>Details geplante Zahlungen</h3>
Dieses Fenster zeigt die Details der <a href="#scheduled"><u>geplante Zahlungen</u></a>. 
Klicken Sie auf die Namen der entsprechenden Personen, um zu deren Profilen zu gelangen.<br> 
Um die Details auszudrucken, klicken Sie bitte auf das Drucken-Symbol 
<img border="0" src="${images}/print.gif" width="16" height="16">&nbsp;.
<br><br>
Wenn Sie die Berechtigungen haben, sind zwei Schaltflächen im unteren Teil des Fensters 
verfügbar. 
<ul>
	<li><b>Sperren:</b> Hiermit können Sie die Zahlung zeitweise sperren. Sie wird dann nicht mehr 
	getätigt, bis Sie die Sperre wieder aufheben. Eine gesperrte Zahlung kann entsperrt werden. 
	<li><b>Entsperren:</b> Entsperrt die Zahlung, so dass sie wieder an den ursprünglich eingegebenen 
	Terminen stattfindet. Diese Schaltfläche erscheint nicht, wenn der Zahlungstermin bereits vergangen 
	ist. In diesem Fall können Sie immer noch bezahlen, indem Sie zum Fenster  <a
	href="#sheduled_payment_transfers">Geplante Zahlungsüberweisungen</a> unten gehen, und das 
	Anzeigen-Symbol <img border="0" src="${images}/view.gif" width="16"	height="16">&nbsp; 
	der Überweisung anklicken.
	<li><b>Abbrechen:</b> Der Unterschied zur gesperrten Zahlung besteht darin, dass Abbrechen
	einer geplanten Zahlung endgültig ist. Die noch offenen Zahlungen werden dann nicht mehr geleistet, 
	und werden endgültig entfernt, ohne die Möglichkeit, sie wieder zu entsperren. Die bereits geleisteten 
	Zahlungen werden nicht rückgängig gemacht.
</ul>
<hr class="help">

<br><br><A NAME="sheduled_payment_transfers"></A>
<h3>Geplante Zahlungsüberweisungen</h3>
Diese Seite zeigt alle Überweisungen (Teilzahlungen) die Teil eines Pakets aus 
<a href="#pay_scheduled"><u>mehreren geplanten Zahlungen</u></a>. Um Details der Zahlung 
anschauen zu können, klicken Sie auf das Anzeigen-Symbol <img border="0" src="${images}/view.gif" 
width="16" height="16">&nbsp;.
<hr class="help">


<A NAME="scheduled_payments_result"></A>
<h3>Suchergebnisse (geplante Zahlungen)</h3>
Dieses Fenster zeigt eine Liste der <a href="#scheduled"><u>geplanten Zahlungen</u></a>, 
in Übereinstimmung mit den von Ihnen im Fenster oben spezifizierten Kriterien. 
<br><br>
Folgendes wird angezeigt:
<ul>
	<li><b>Datum:</b> Datum der geplanten Zahlung
	<li><b>Benutzername:</b> Anklicken dieses Benutzernamens bringt Sie zum Profil des Mitglieds. 
	<li><b>Betrag</b>
	<li><b>Pakete:</b> Die erste Zahl gibt an, wie viele Teilzahlungen dieser geplanten Zahlungen 
	bereits geleistet wurden; die zweite Zahl gibt die Gesamtanzahl der zu leistenden Teilzahlungen 
	innerhalb dieser geplanten Zahlungen an. Ist die Zahlung nicht in mehrere Teilzahlungen unterteilt, 
	sondern besteht aus einer einmaligen Zahlung, dann ist diese zweite Zahl eine &quot;1&quot;.
	<li><b>Status:</b> dieser ist entweder &quot;geplant&quot;, &quot;gesperrt&quot;,
	&quot;Erwartet Autorisierung&quot; (siehe <a href="#authorized"><u>autorisierte Zahlungen</u></a>)
	<li><img border="0" src="${images}/view.gif" width="16" height="16">&nbsp;Klicken Sie auf dieses 
	Symbol um die Details dieser Überweisung zu zeigen. Hier haben Sie die Option die Details
	auszudrucken, und die Zahlung zu sperren, zu entsperren oder zu zahlen (vorausgesetzt, 
	Sie haben die dazu notwendigen Berechtigungen). 
</ul>
<hr>

<a name="authorized"></a>
<h2>Autorisierte Zahlungen</h2>
Cyclos kann so konfiguriert sein, dass alle Zahlungen vor der tatsächlichen Überweisung 
des Betrags auf das Empfängerkonto einer Autorisierung bedürfen. Die Autorisierung kann 
durch einen Administrator, einen Broker oder durch das empfangende Mitglied durchgeführt 
werden, je nach Konfiguration. Es kann mehr als eine Konfigurationsebene geben, was 
bedeutet, dass mehr als eine der oben genannten Personen autorisieren muss. Für jede 
Autorisierungsebene können unterschiedliche Kriterien konfiguriert werden. 
<br>
Solange die Zahlung nicht autorisiert ist, verbleibt sie im Status &quot;Erwarte 
Autorisierung&quot;. Der Autorisierende (Broker oder Administrator) wird benachrichtigt 
und kann dann die Zahlung autorisieren (aktivieren). Sowohl Mitglied als auch Autorisierender 
(Broker oder Administrator) haben Zugang zu einer Liste anstehender Zahlungen, die noch 
der Autorisierung bedürfen. Sowohl Auftraggeber als auch Empfänger werden nach Durchführung 
der Zahlung(Autorisierung) benachrichtigt. 

<span class="admin"> Autorisierte Zahlungen werden über  <a
	href="${pagePrefix}account_management#transaction_types"><u>
Überweisungstyp</u></a>; verwaltet; es ist eine Reihe von unterschiedlichen Einstellungen 
möglich.
<br><br>Autorisierte Zahlungen können wie folgt aktiviert werde: 
<ol>
	<li><b>Berechtigungen:</b> Stellen Sie bitte zuallererst sicher, dass all relevanten <a
		href="${pagePrefix}groups#permissions"><u>Berechtigungen</u></a> eingestellt sind. 
		Möglicherweise möchten Sie die Berechtigungen für Administratoren, Broker und für 
		Mitglieder einstellen. Für jede dieser Benutzergruppen gibt es verschiedene 
		Berechtigungen zur Autorisierung; Sie können auch die Suchfunktion Ihres Browsers 
		verwenden (normalerweise ctrl-f) um sie auf der Seite zu finden.
	<li><b>Autorisierung für den Überweisungstyp einstellen:</b> Für den <a
		href="${pagePrefix}account_management#transaction_types"><u>Überweisungstyp</u></a>, 
		müssen Sie die Autorisierung freigeben. Dies tun Sie über das Feld &quot;Erfordert 
		Autorisierung&quot; im Fenster <a	href="${pagePrefix}account_management#transaction_type_details">
		<u>Details Überweisungstyp</u></a>.<br>
		<b>Anmerkung:</b> Solange es noch Zahlungen gibt,	die auf Autorisierung warten, 
		solange kann die Autorisierungsoption auch nicht deaktiviert werden.
	<li><b>Autorisierungsebene(n) einstellen:</b> Danach müssen Sie Autorisierungsebenen 
	  einstellen. Dies tun Sie ein Fenster weiter unten, im <a
		href="${pagePrefix}account_management#authorized_payment_levels"><u>
	  Autorisierungsebenen Zahlungen</u></a>. Falls Sie Hilfe benötigen, wenden Sie sich 
	  bitte an die Hilfedetails des jeweiligen Formulars.
</ol>
</span>
<span class="member">
Autorisierung für Mitglieder bedeutet ganz einfach, dass der Empfänger eine Zahlung von 
anderen akzeptieren muss, bevor die Überweisung stattfindet. Dadurch hat der Empfänger 
die Möglichkeit, die Zahlung nicht anzunehmen (d.h. das Produkt ist nicht mehr verfügbar). 
Sowohl Empfänger als auch Auftraggeber erhalten darüber eine Nachricht. Diese Konfiguration 
ähnelt der Verwendung von Rechnungen, und kommt nur recht selten vor. Es empfiehlt sich, 
Empfänger-Autorisierungen und Rechnungen nicht im gleichen System zu verwenden. 
</span>
Autorisierte Zahlungen finden Sie:
<ul>
	<li><b>Menü: Konten > Zu autorisieren</b> gibt Ihnen eine Übersicht der Zahlungen die 
	noch von Ihnen zu autorisieren sind. Dieses Menü ist nur dann verfügbar, wenn Sie 
	berechtigt sind eingehende Zahlungen zu autorisieren.
	<li><b>Menü: Konten > Autorisierungen</b> ermöglicht Ihnen die Suche nach Autorisierungen, 
	vergangen oder gegenwärtig, autorisiert, verweigert oder annulliert. Alle in der Vergangenheit 
	von Ihnen ausgeführten Autorisierungs-Aktionen können über dieses Fenster gefunden werden.<br>
	Bitte beachten Sie, dass diese Option für die Suche nach Autorisierungs-Aktionen gilt. 
	Überweisungen in Erwartung der Autorisierung werden darüber nicht gefunden (da sie 
	betreffend noch nicht durchgeführt wurde). 
	<li class="broker"><b> Menü: Brokering > Zu autorisieren</b> gibt Ihnen eine Übersicht 
	der Zahlungen die noch von Ihnen als Broker zu autorisieren sind (im Gegensatz zu 
	&quot;Menü: Konto > Zu autorisieren&quot;, wo Sie Ihre persönlichen autorisierten 
	Zahlungen finden). 
	<li class="broker"><b> Menü: Brokering > Autorisierungen</b> Genau wie bei dem Element 
	&quot;Menü: Konto > Autorisierungen&quot;, allerdings nicht zu den Autorisierungen in 
	Zusammenhang mit Ihren Brokering-Aktivitäten. 
</ul>
<hr class="help">

<a name="transfers_awaiting_authorization_by_member"></a>
<a name="transfers_awaiting_authorization_by_admin"></a>
<h3>Zu autorisierende Überweisungen</h3>
Verwenden Sie dieses Fenster für eine Übersicht der Überweisungen, die von Ihnen 
<a href="#authorized"><u>autorisiert</u></a> werden müssen. Und wie immer bedeutet 
ein freigelassenes Feld dass alle in diesem Fenster möglichen Werte in den Suchergebnissen 
erscheinen. Anklicken des Schaltfläche &quot;Suche&quot; ohne Spezifizierung eines der Felder 
ergibt also alle Zahlungen in Erwartung der Autorisierung. 
<br><br>Die folgenden Suchkriterien können Sie festlegen: 
<ul>
	<li><b>Benutzername / Name:</b> Diese Felder sind selbstvervollständigend; 
	Eingabe der ersten Buchstaben ist meist ausreichend. 
	<li><b>Von Datum / Bis Datum:</b> Hier können Sie einen Zeitraum bestimmen. 
	Um das Zeitauswahlfeld zu benutzen, klicken Sie bitte auf das Kalender-Symbol  
	<img border="0"	src="${images}/calendar.gif" width="16" height="16">&nbsp;.
	<li><b>Überweisungstyp</u></b> Suche nach Überweisungstyp.
	<li span class="admin"><b>Nur wenn Broker nicht autorisieren können:</b> Ausfüllen 
	dieses Kontrollkästchens zählt zu den Ergebnissen nur solche, in denen Sie als 
	Administrator der einzige Autorisierungsberechtigte sind. 
</ul>
Die Ergebnisse erscheinen im darunter befindlichen Fenster. 
<hr class="help">

<a name="transfers_awaiting_authorization_result"></a>
<h3>Suchergebnisse (Überweisungen erwaten Autorisierung)</h3>
In dieser Übersicht sehen Sie die Überweisung, die noch Ihrer 
<a href="#authorized"><u>Autorisierung</u></a> bedürfen. Die negativen Beträge 
sind ausgehende Zahlungen, die noch der Autorisierung bedürfen; die positiven 
Beträge sind eingehende Zahlungen in Erwartung der Autorisierung. 
<br><br>
Klicken Sie auf das Symbol Anzeigen-Symbol <img border="0" src="${images}/view.gif" width="16"
	height="16">&nbsp;um das Detailfenster zu öffnen, in dem Sie eine Zahlung 
	autorisieren oder verweigern können. 
<hr class="help">

<a name="transfer_authorizations_by_admin"></a>
<a name="transfer_authorizations_by_member"></a>
<h3>Autorisierte Überweisungsaktionen</h3>
Über dieses Fenster können Sie nach Aktionen für <a href="#authorized">
<u>Autorisierung</u></a> suchen. Das Formular ist recht einfach. Und 
wie immer bedeutet ein freigelassenes Feld, dass alle in diesem Fenster 
möglichen Werte in den Suchergebnissen erscheinen.<br>
Die folgenden Suchoptionen sind verfügbar:
<ul>
	<li><b>Überweisungstyp:</b> Suche nach Überweisungstyp. 
	<li><b>Suche nach Aktion:</b>
	<ul>
		<li><b>Autorisiert:</b> Autorisierte Zahlungen. 
		<li><b>Abgelehnt:</b> Verweigerte Zahlungen.
		<li><b>Annuliert:</b> Annullierte Zahlungen (von anderen). 
	</ul>
	<li><b>Suche nach Mitglied:</b> Suche nach einzelnem Mitglied. 
	<li><b>Suche nach Zeitraum:</b> Suche über Datumsbereich. 
</ul>
Sind Sie mit der Auswahl fertig, können Sie die Schaltfläche &quot;Suche&quot; unten 
auf der Seite anklicken. Die Ergebnisse erscheinen daraufhin im Fenster unten. 
<hr class="help">

<a name="transfers_authorizations_result"></a>
<h3>Suchergebnisse Autorisierungsverlauf</h3>
Dieses Fenster zeigt die Suchergebnisse, in Übereinstimmung mit den von Ihnen im 
Fenster oben spezifizierten Kriterien. Verwenden Sie das Anzeigen-Symbol  
<img border="0" src="${images}/view.gif" width="16" height="16">
&nbsp; um Details zu dem Element zu erhalten.
<hr class="help">

<a name="transaction_authorizations_detail"></a>
<h3>Autorisierungsaktionen</h3>
Dieses Fenster zeigt alle Aktionen für die <a href="#authorized"><u>Autorisierung</u></a> 
die bisher im Zusammenhang mit der obigen Überweisung geschehen sind. Dabei kann es 
sich um Autorisierungen, aber auch um Verweigerungen oder Annullierungen handeln. 
Gezeigt werden das Datum und der Ausführende der Aktion. 
<hr>

<h2>Diverse Zahlungsfenster</h2>
Unten sehen Sie einige allgemeine und diverse Hilfefenster in Zusammenhang mit Zahlungen. 

<a name="accessing_channels"></a>
<h3>Zahlung über Kommunikationswege durchführen</h3>
Je nach Konfiguration kann ein Mitglied Zahlungen über eine Reihe von Kommunikationswegen
ausführen. 
<ul>
	<li>Der gebräuchlichste ist der Internetzugang (www.domain.com/cyclos)
	<li>Ein weiterer nützlicher Zahlungsweg ist eine einfache Seite auf der sich 
	Mitglieder nur anmelden, um schnelle Zahlungen durchzuführen (www.domain.com/posweb/pay). 
	<li>Mitglieder/Geschäfte, die Zahlungen von Kunden am POS (point of sale) entgegen nehmen 
	möchten, können die POS-Web-Seite verwenden. Bitte beachten Sie, dass Clients eine persönliche 
	PIN generieren müssen, um die Zahlung zu validieren. 
	<li>Für Mitglieder/Geschäfte, die auf der gleichen Seite sowohl zahlen als auch Zahlungen 
	entgegen nehmen möchten, können den Kommunikationsweg (www.domain.com/posweb) verwenden.<br>
  Diese Zugangsseite wird üblicherweise von regionalen &quot;Cache-Points&quot; verwendet, wo 
  Kunden Gutscheine (Scrip) oder tatsächliches Geld abholen können. 
	<li>Für Mitglieder/Geschäfte mit &quot;Paydesk&quot; Operatoren, die Zahlungen ausführen 
	oder Zahlungen von Kunden entgegen nehmen können, kann die Seite Operatoren POS-Web 
	(www.domain.com/posweb/operator) verwendet werden.
	<li>Mobile Zahlungen können über die  URLs www.domain.com/cyclos/mobile (WAP 2) und 
	www.domain.com/cyclos/wap (WAP 1) getätigt werden. 
</ul>
Die Verfügbarkeit der Zahlungskanäle hängt ab von der Konfiguration des Systems. Im allgemeinen 
verlangt die Durchführung von Zahlungen vom Kunden die Eingabe einer PIN.  
Die Ausführung einer Zahlung über die POS-Web-Kommunikationswege funktioniert genau so wie der über den 
Internetzugang, verlangt also ein Kennwort oder ein Überweisungskennwort, je nach Konfiguration. 

<hr class="help">

<a name="request"></a>
<h3>Anforderung der Zahlung über einen anderen Kommunikationsweg</h3>
In diesem Fenster können Sie über ein anderes Medium Kommunikationsweg um Zahlung ersuchen. 
Das funktioniert so ähnlich wie eine Rechnung, insofern als die Zahlung ausgelöst wird, 
sobald sie akzeptiert wurde. Der Unterschied besteht darin, dass diese Zahlungsaufforderung 
eine PIN und eine Bestätigung mit Aufforderungs-Identnummer benötigt (in der SMS enthalten). 
Derzeit sind nur SMS-Zahlungsaufforderungen verfügbar. Die Administration muss den SMS-Kanal 
freigeben bevor diese Funktion genutzt werden kann. 
<br><br>
Das funktioniert so: nach Eingabe eines Mitglieds in das (sich selbst vervollständigende) 
Feld Benutzername/Name, Eingabe eines Betrags und der Beschreibung, und Anklicken von 
&quot;Weiter&quot; ergeht eine Zahlungsaufforderung über den SMS-Kanal. Der Empfänger erhält 
unverzüglich eine SMS, die er durch Beantworten und Eingabe einer PIN bestätigen kann. 
Nach Abschicken einer Zahlungsaufforderung ändert sich der Status (siehe Hilfe unten). 
Bestätigt das Mitglied die Zahlung durch Senden einer SMS, ändert sich der Status zu 
&quot;Gezahlt&quot; (und das Produkt kann überreicht oder verschickt werden). 

<hr class="help">

<a name="search_requests"></a>
<h3>Nach Zahlungsaufforderungen suchen </h3>
In diesem Fenster können Sie nach Zahlungsaufforderungen suchen. Als Standardeinstellung 
werden hier alle anstehenden und erfolgreichen (bestätigte & gezahlte) Anforderungen gezeigt. 
Sie können aber auch Filter mit anderen Status, oder Suche nach Mitgliedern wählen. 
<br><br>
Das Ergebnisfenster zeigt die Zahlungsaufforderungen je nach Suchfiltern. Die Seite wird 
automatisch alle 5 Sekunden aktualisiert (zeigt also automatisch alle Statusveränderungen). 
<hr class="help">

<a name="account_overview"></a>
<h3>Kontoübersicht</h3>
Dieses Fenster zeigt eine Liste all Ihrer zugänglichen Konten. 
<ul>
	<li><img border="0" src="${images}/view.gif" width="16" height="16">&nbsp;
	Dieses Symbol anklicken, um auf das Konto zuzugreifen. 
</ul>
<hr class="help">

<a name="transaction_history"></a>
<h3>Überweisungszusammenfassung</h3>
Dieses Fenster bietet verschiedene Optionen für die Suche nach Überweisungen. Sie 
können unter den folgenden Optionen wählen: 
<UL>
	<LI><b>Status:</b> Dies ist der Status von Zahlungen, die der Autorisierung bedürfen. 
	Dies erscheint nur, wenn Autorisierung für Mitgliedszahlungen oder Darlehen aktiviert ist. 
	Es zeigt den Status: 
	<ul>
		<LI><b>Erwartet Autorisierung:</b> Die Zahlung oder das Darlehen muss vor Bearbeitung 
		der Zahlung autorisiert werden. 
		<LI><b>Bearbeitet:</b> Die Zahlung oder das Darlehen wurde autorisiert und bearbeitet. 
		<LI><b>Abgelehnt:</b> Die Zahlung oder das Darlehen wurde abgelehnt
	</ul>
	<LI><b>Zahlungstyp:</b> In diesem Auswahllistenfeld können Sie nach spezifischen Zahlungstypen 
	filtern. 
	<LI><b>Benutzername / Name:</b> Zeigt nur Überweisungen für eine bestimmte Person, indem Sie 
	den Benutzernamen oder Mitgliedsnamen der Person eingeben. Während der Eingabe vervollständigt 
	sich das Feld selbst. 
	<LI><b>Zeitraum:</b> Zeigt nur Überweisungen innerhalb eines bestimmten Zeitraums; diesen 
	bestimmen Sie über Eingabe des Anfangs- und Enddatums. 
	<LI><b>Beschreibung:</b> Suchen Sie nach einer bestimmten Beschreibung, indem Sie einen Begriff 
	(Schlüsselwort) in das <i>Beschreibungsfeld</i>> eingeben. Eingabe von &quot;Rad&quot; zeigt z.B. 
	alle Überweisungen mit dem Wort &quot;Rad&quot; in der Beschreibung oder im Titel. 
	<LI><b>Überweisungsnummer:</b> Ist diese Option für das System aktiviert, können Sie hier nach 
	der Überweisungsnummer suchen. 
</UL>
<hr class="help">


<a name="transaction_history_result"></a>
<h3>Ergebnis Überweisungszusammenfassung</h3>
Das Fenster zeigt das Ergebnis der Überweisungen.<br>
Anklicken des Drucken-Symbols <img border="0" src="${images}/print.gif" 
 width="16" height="16">&nbsp; “ (in der oberen Leiste neben dem Hilfe-Symbol) 
eine druckerfreundliche Version Ihrer Suchergebnisse, mit ${localSettings.maxIteratorResults} Überweisungen und 
einem zusammenfassenden Bericht. 
<span class="admin">Sie können die Anzahl der Überweisungen die angezeigt werden in den Basiseinstellungen&gt;Limits ändern
</span> 
<br>Anklicken des Speichern-Symbols <img border="0" src="${images}/save.gif"
	width="16" height="16">&nbsp; (in der oberen Leiste neben dem Drucken-Symbol) 
ermöglicht Ihnen, das Ergebnis als CSV-Datei herunterzuladen. 
<br><br>Der erste (obere) Abschnitt des Fensters zeigt die folgenden Informationen: 
<ul>
	<li><b>Kontosaldo:</b> Das Kontosaldo.
	<li><b>D-Rate:</b> Gibt an, nach wie vielen Tagen die Sicherheit
	für diese Einheiten erlischt. Dies ist nur dann sichtbar, wenn in Ihrem System
	<a href="${pagePrefix}account_management#d-rate"><u>D-Rates</u></a> freigegeben sind.
	Die rate wird zu jeder Überweisung angegeben. Niedrige D-Rates verursachen beim Konvertieren
	der Einheiten in nationale Währungen, niedrigere Kosten.
	<br>Bitte beachten Sie, dass diese rate sich mit der Zeit
	verringert, außer Sie haben auf Ihrem Konto keinerlei eingehende Einheiten mehr
	zu verzeichnen. Null-Einheiten haben keinerlei rate, in diesem Fall entspräche also der 
	gezeigt Wert dem der gerade eingerichteten Einheiten. 
	<li><b>A-rate:</b> Gibt das Alter der Einheiten seit ihrer Einrichtung. Dies 
	ist nur dann sichbar, wenn in Ihrem System <a href="${pagePrefix}account_management#a-rate">
	<u>A-rates</u></a> freigegeben sind. Diese rate kann auch verwendet werden um eine zusätzliche
	Gebühr für frühzeitige Konversionen zu berechnen.
	<br>Bitte beachten Sie, dass diese rate sich mit der
	Zeit erhöht, außer Sie haben auf Ihrem Konto keinerlei eingehende Einheiten mehr zu
	verzeichnen.  
	<li class="member"><b>Unteres Kreditlimitt:</b> Das untere Kreditlimit.
	Dieses kann entweder Null oder negativ sein. (falls Null, wird das Limit nun angezeigt)
	<li class="member"><b>Oberes Kreditlimit:</b> Das obere Kreditlimit oder Kontorahmen.
	Dieses kann entweder Null oder positiv sein. (falss Null, wird das Limit nun angezeigt)
	<li class="member"><b>Verfügbares Guthaben:</b> das verfügbare Guthaben, das ausgegeben werden kann. 
	<li class="member"><b>Reservierer Betrag:</b> Dies ist der Betrag, der zeitweilig reserviert, und 
	nicht für Zahlungen verwendet werden kann. Dies kann geschehen, weil es Zahlungen in 
	Erwartung der Autorisierung gibt, oder um einen Betrag für anstehende periodische Zinsen 
	oder Lieggebühren zu reservieren.
</ul>
Unterhalb dieses Abschnitts ist eine Liste aller Zahlungen (geleistet und empfangen). Empfangene 
(eingehende) Zahlungen erscheinen mit einem + Pluszeichen davor; ausgehende Zahlungen erscheinen 
mit – Minuszeichen davor. Die Liste zeigt das Zahlungsdatum, das Mitglied (Empfänger oder Auftraggeber), 
und eine Beschreibung der Zahlung. Der Mitglieds/Benutzername ist gleichzeitig Link zum 
Mitgliedsprofil.<br> 
Anklicken des Anzeigen-Symbols <img border="0" src="${images}/view.gif" width="16" height="16">
&nbsp; eines Zahlungsfensters öffnet die Beschreibung dieser Zahlung. 
<hr class="help">

<a name="transaction_detail"></a>
<h3>Überweisungsdetails</h3>
Dieses Fenster zeigt Details zur gewählten Zahlung. Durch Auswählen des Drucken-Symbols können Sie die 
Überweisungsdetails ausdrucken. Falls eine &quot;Haupt- oder Unterzahlung&quot; existiert (d.h. die 
Überweisungsgebühr wurde erhoben), erscheint sie unter diesem Fenster. 
<br><br>
<span class="broker admin"> Falls die Überweisung einer Autorisierung bedarf, haben Sie die 
Option die Überweisung zu akzeptieren oder abzulehnen. Ebenso werden Sie gebeten, einen Kommentar 
abzugeben. Wenn Sie das Kontrollkästchen &quot;Dem Mitglied zeigen&quot; wählen, ist der Kommentar 
auch für das Mitglied sichtbar. Wird diese Option nicht gewählt, ist der Kommentar nur für Sie und 
die Administration sichtbar. 
</span>
<span class="admin">
Unter bestimmten Umständen ist es möglich, eine Überweisung über <a href="#charge_back"><u>
Rückbelastung</u></a> rückgängig zu machen. 
In diesem Fall erscheint eine besondere Schaltfläche für Rückbelastung in diesem Fenster. 
</span>
<hr class="help">

<span class="admin">
<a name="charge_back"></a>
<h3>Rückbelastung</h3>
Ein Administrator (mit den entsprechenden Berechtigungen) kann eine Zahlung &quot;rückbelasten&quot;, was 
bedeutet, das in umgekehrter Richtung eine Zahlung mit gleichem Betrag getätigt wird. Hat die Zahlung andere 
Überweisungen generiert (wie z.B. Gebühren und Darlehen), so verursachen alle Überweisungen umgekehrte 
Überweisungen. Falls Rückbelastung möglich ist, erscheint eine besondere Schaltfläche in den 
Überweisungsdetails. Allerdings ist diese Schaltfläche nur sichtbar 
für Überweisungen die noch nicht allzu lange her sind. Den maximalen Zeitraum den eine Überweisung 
rückbelastet werden kann können Sie definieren, über &quot;Menü: Einstellungen > <a
	href="${pagePrefix}settings#local"><u>Basiseinstellungen > Rückbelastung</u></a>&quot;.  


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
<br><br>
<br><br>
<br><br>
<br><br>
<br><br>
</div>