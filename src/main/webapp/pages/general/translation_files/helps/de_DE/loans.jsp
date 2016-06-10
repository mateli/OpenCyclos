<div style="page-break-after: always;">
<a name="top"></a>
<br><br>Darlehen können für Cyclos den grundlegenden Kreditmechanismus 
darstellen. Cyclos unterstützt alle erforderlichen Eigenschaften eines Darlehens, einschließlich 
geplanter Zahlungen (Raten) und Zinsen, und ermöglicht den Administratoren, eine Reihe von 
Gebühren anzuwenden.<br>
Darlehen können intern in Cyclos oder auch von extern zurückerstattet werden. 
Im letzeren Fall benötigt das Darlehen ein administratives &quot;erledigt&quot; 
in Cylcos. Ein Darlehen kann aus einer Zahlung oder aus mehrfachen Zahlungen 
bestehen. Wenn ein Darlehen aus mehreren Zahlungen besteht, kann jedes ein 
Verfallsdatum und Status haben. Ein Darlehen kann entweder einem individuellen 
Mitglied oder einer Darlehensgruppe gewährt werden. Eine Darlehensgruppe ist 
eine Gruppe von Mitgliedern, eines dieser Mitglieder kann die Berechtigung 
zugeteilt werden, das Darlehen abzuwickeln. Dies ist eine übliche Funktion 
für Mikrofinanz-Projekte.

<span class="admin">
<br><br><i>Wo ist es zu finden, wie können Darlehen verwendet werden?</i><br>
Um in Cyclos Darlehen verwenden zu können, sind die folgenden Schritte notwendig:
<ol>
	<li><b>Überweisungstypen einrichten:</b> Bevor Sie Darlehen verwenden können, müssen die 
	geeigneten <a href="${pagePrefix}account_management#transaction_types"><u>Überweisungstypen</u></a> 
	für Darlehen eingerichtet werden, da jedes Darlehen einen eigenen Überweisungstyp 
	haben muss.<br>
	Im Allgemeinen brauchen Sie zwei Darlehens-Überweisungstypen: einen Typ, um ein Darlehen an 
	ein Mitglied vergeben zu können (von einem Systemkonto kommend), und einen weiteren Typ, 
	den das Mitglied verwendet um das Darlehen zurück zu zahlen (also Mitglied an Systemkonto). 
	Das erste können Sie nicht einrichten, ohne letzteres zu spezifizieren, es empfiehlt sich 
	also mit dem Rückzahlungs-Überweisungstyp zu beginnen (vom Mitglied an das System). 
	Für kompliziertere Debit-Systeme müssen Sie möglicherweise Überweisungstypen auch für 
	Gebühren und Zinsen einrichten. 
	<br><br>Einen neuen Überweisungstyp für die <b>Rückzahlung von Darlehen</b> richten 
	Sie wie folgt ein:<br><br>
	<ul>
		<li>Gehen Sie zum Abschnitt Überweisungstypen, &quot;Menü: Konten > Konten verwalten&quot;.
		<li>Wählen Sie den Kontentyp, auf dem die Mitglieder die Darlehen erhalten. Dies ist 
		normalerweise das &quot;Mitgliedskonto&quot;. Klicken Sie auf das Bearbeiten-Symbol 
		<img border="0" src="${images}/edit.gif" width="16" height="16">&nbsp; für diesen Kontentyp.
		<li>Im nächsten Fenster, gehen Sie bitte zum Fenster  &quot;<a
			href="${pagePrefix}account_management#transaction_type_search"><u>
		Überweisungstypen</u></a>&quot;, und klicken Sie auf die Schaltfläche &quot;Neuen 
		Überweisungstyp einfügen&quot; unten. Bevor Sie dies tun, sollten Sie allerdings überprüfen, 
		ob es nicht bereits ein entsprechender Überweisungstyp für Darlehensrückzahlungen verfügbar ist. 
		<li>Im folgenden Fenster wählen Sie bitte einen Kontentyp im Feld &quot;An&quot; aus. Normalerweise 
		ist dies das &quot;Ausgangskonto&quot;, Sie sollten allerdings überlegen, welche 
		Systemkontentypen Sie für die Darlehen verwenden wollen.<br>
		Füllen Sie bitte einfach den Rest der Felder aus, und wenden Sie sich, wenn notwendig,
		an die lokale Hilfe. 
	</ul>
	<br><br>
	Wenn Sie damit fertig sind, können Sie einen neuen Überweisungstyp für die <b>Bewilligung 
	des Darlehens</b> einrichten:
	<br><br>
	<ul>
		<li>Gehen Sie wieder zum Abschnitt Überweisungstypen, &quot;Menü: Konten > Konten verwalten&quot;.
		<li>Wählen Sie bitte den Kontentyp, den Sie zuvor  im Feld &quot;An&quot;spezifiziert haben 
		(siehe oben), dies ist normalerweise das &quot;Ausgangskonto&quot;. Klicken Sie auf das 
		Bearbeiten-Symbol <img border="0" src="${images}/edit.gif" width="16"
			height="16">&nbsp; für diesen Kontentyp.
		<li>Überprüfen Sie, ob es für die Bewilligung von Darlehen bereits ein entsprechender
		 Überweisungstyp verfügbar ist. Falls nicht, erstellen Sie ihn durch Anklicken der Schaltfläche 
		 &quot;Neuen Überweisungstyp einfügen&quot;. 
		<li>Im folgenden Fenster wählen Sie bitte einen Kontentyp im Feld &quot;An&quot;.  Dies ist 
		normalerweise das &quot;Mitgliedskonto&quot;, das Sie zuvor bei der Einrichtung des 
		Rückzahlungs-Überweisungstyps verwendet haben. Nach Auswahl des Überweisungstyps erscheint 
		das Kontrollkästchen &quot;Ist Darlehen&quot; unten in diesem Fenster. Wählen Sie dieses Kästchen,
		und es werden weitere Felder erscheinen. Für mehr Details, wenden Sie sich bitte an die 
		lokalen Hilfen, oder <a href="#make_loan_type"><u>klicken Sie hier</u></a>.
	</ul>
	<br>
	<br>
	<li><b>Geeignete Berechtigungen einstellen:</b> Stellen Sie sicher, dass die geeigneten <a
		href="${pagePrefix}groups#manage_groups"><u>Berechtigungen</u></a> für Darlehen eingestellt sind.
		Administratoren und möglicherweise Broker benötigen  <a	
		href="${pagePrefix}groups#manage_group_permissions_admin_member"><u>Berechtigungen</u></a> 
		Darlehen zu bewilligen; möglicherweise möchten Sie auch <a
		href="${pagePrefix}groups#manage_group_permissions_member"><u>Mitgliedsberechtigungen</u></a> 
		zum Zeigen und Zurückzahlen einstellen (und weitere). Bitte beachten Sie, dass die Berechtigung 
		für die Rückzahlung eines Darlehens (durch Administrator oder Mitglied) ausdrücklich eingestellt 
		werden muss.<br>
		Falls Sie  <a href="${pagePrefix}loan_groups"><u>Darlehensgruppen</u></a> verwenden möchten, 
		sollten Sie auch dafür die Berechtigungen einstellen.<br>
	<br>
	<li><b>Das Darlehen bewilligen:</b> Ein Darlehen bewilligen Sie, indem Sie im  <a
		href="${pagePrefix}profiles"><u>Profil</u></a> des Mitglieds &quot;Darlehen bewilligen&quot;
		anklicken.<br>
	<br>
	<li><b>Darlehen verwalten:</b> Verwaltet werden können Darlehen über den Abschnitt 
	&quot;Menü: Konten > Darlehen verwalten&quot;. Zahlungen verwalten Sie über das 
	&quot;Menü: Konten> Darlehenszahlungen&quot;. 
</ol>
</span>
<span class="member">
<br><br><i>Wo ist es zu finden?</i><br>
Zugang zu Ihren Darlehen erhalten Sie über das Hauptmenü  &quot;Menü:
Konten > Darlehen&quot;. Hier finden Sie eine Übersicht, und den Ausgangspunkt 
für Rückzahlungen.
</span>
<span class="broker">Zugang zu den Darlehen Ihrer Mitglieder erhalten Sie über das <a
	href="${pagePrefix}profiles"><u> Profil eines Mitglieds</u></a>, unter Aktionen 
	finden Sie einen speziellen Abschnitt für die Verwaltung von Darlehen.</span>
<hr>

<span class="admin"> <a name="make_loan_type"></a>
<h3>Erstellen eines Überweisungstyps für Darlehen</h3>
(<i>Tipp: möglicherweise gibt es eine Vielzahl von Links zu anderen Abschnitten der Hilfen. 
Verwenden Sie die Zurück-Schaltfläche dieser Seite um zurück zu gehen, wenn Ihr Browser die 
Zurück-Schaltfläche nicht zeigt.</i>)
<br><br>Wenn Sie das Kontrollkästchen &quot;Ist Darlehen&quot;gewählt haben, dann ist der von 
Ihnen einzurichtende <a	href="${pagePrefix}account_management#transaction_types"><u>Überweisungstyp</u></a> 
ein <a href="#top"><u>Darlehen</u></a>. Die Darlehenseinstellungen werden dann unten 
erscheinen, sobald Sie das Kontrollkästchen wählen.<br>
Handelt es sich um ein Darlehen, müssen auch noch einige andere Felder spezifiziert werden. 
Das wichtigste dieser Felder ist das erste, das Feld &quot;Darlehenstyp&quot;. Treffen 
Sie hier zuerst eine Entscheidung; diese Entscheidung bestimmt, welche anderen Felder 
erscheinen werden.<br>
Der eigentliche Überweisungstyp, den Sie gerade bearbeiten oder einrichten, ist der Überweisungstyp 
dafür, Einheiten als Darlehen auf das Konto eines Mitglieds zu überweisen. Natürlich bedarf es 
auch eines entsprechenden Überweisungstyps für die Rückzahlung dieser Beträge. Dieser entsprechende 
Überweisungstyp muss im Feld &quot;Überweisungstyp für Rückzahlungen&quot; spezifiziert werden. 
Hier handelt es sich um einen Überweisungstyp Mitglied-an-System, falls dieser noch nicht existiert, 
müssen Sie ihn zuerst einrichten – dies bedeutet, dass Sie zuerst einen Rückzahlungstyp 
einrichten, bevor Sie mit der Einrichtung des aktuellen Darlehens-Überweisungstyps 
fortfahren können. 
<br><br>
Verfügbar sind drei verschiedene <a href="#loan_types"><u>Darlehenstypen</u></a>;
die übrigen sichtbaren Felder werden unter jedem Darlehenstyp behandelt: 
<ul>
	<li><b>Einfache Zahlung:</b> muss zu (oder bis zu) einem bestimmten Datum zurück gezahlt 
	werden. Handelt es sich bei Ihrem Darlehen um ein solches, müssen Sie die folgenden weiteren 
	Felder spezifizieren: 
	<ul>
		<li><b>Überweisungstyp für Rückzahlungen:</b> Erklärung siehe etwas weiter oben. 
		<li><b>Standard Rückzahlungsfrist in Tagen:</b> Dies ist die Ablauffrist; nach diesem Zeitraum 
		erscheint dieses Darlehen als &quot;abgelaufen&quot; in der Darlehensübersicht des 
		Mitglieds und in der Funktion &quot;Darlehen verwalten&quot; des Verwaltungsabschnitts. 
		Das Mitglied sollte das Darlehen vor Ablauf zurück gezahlt haben. 
	</ul>
	<br>
	<br>
	<li><b>Mehrfachzahlungen:</b> Darlehen dieses Typs sind in regelmäßige (monatliche) 
	Rückzahlungen eingeteilt, jede mit eigenem Ablaufdatum. Der Rückzahlungstyp muss von 
	Ihnen spezifiziert werden. 
	<li><b>Mit Gebühren:</b> Dieses Darlehen kann mit unterschiedlichen Arten von Gebühren 
	und periodischen Rückzahlungen verbunden sein. Die folgenden Gebühren können konfiguriert 
	werden: 
	<ul>
		<li><b>Monatlicher Zinssatz:</b> Dies ist ein (Zinses-) Zins, und wird per Monat berechnet. 
		Der Gesamtbetrag des Darlehens und andere Kosten (Zinsen, Bewilligungsgebühr) werden über 
		eine festgelegte Anzahl von Monaten in gleiche Zahlungen geteilt (Ratenzahlungen). 
		<li><b>Darlehen bewilligen:</b> Dies ist eine einmalige Gebühr, die für das Darlehen erhoben 
		wird. Dieser Betrag wird über alle periodischen Rückzahlungen verteilt (ist in ihnen eingeschlossen). 
		Bei der Gebühr kann es sich um einen Prozentsatz des gesamten Darlehens handeln, oder 
		aber um einen Fixbetrag. 
		<li><b>Ablaufgebühr:</b> Dies ist der fixe Betrag, der fällig wird wenn eine Rückzahlung nicht 
		rechtzeitig bedient wird (vor dem Ablaufdatum). 
		<li><b>Ablaufzinsen:</b> Dies sind die Zinsen, die für jeden Tag erhoben werden, den die 
		Rückzahlung überfällig ist. 
	</ul>
</ul>
<hr class="help">
</span>

<a name="loan_types"></a>
<h3>Darlehenstypen</h3>
<br><br>Drei verschiedene <a href="#top"><u>Darlehenstypen</u></a> sind verfügbar:

<ul>
	<li><b>Einfaches Darlehen:</b> Dies bedeutet, dass das Darlehen zu einem bestimmten 
	Datum zurück gezahlt werden muss. Nach diesem Datum gilt das Darlehen als abgelaufen. 
	Innerhalb dieses Zeitraums kann das Mitglied wählen, ob es den vollen Betrag auf einmal, 
	oder mehrere Teilbeträge zahlt. Das einzig Wichtige ist, dass vor Ende der Ablauffrist 
	alles zurück gezahlt ist. 
	<li><b>Mehrfachzahlungen:</b><br>
	Darlehen dieses Typs sind in regelmäßige (monatliche) Rückzahlungen eingeteilt. Bei der 
	Bewilligung des Darlehens an das Mitglied können Sie das Datum der ersten Rückzahlung 
	spezifizieren, und die Anzahl der <a href="#component"><u>Darlehenskomponenten</u></a>. 
	Jede Darlehensrate hat ihr eigenes (monatliches) Ablaufdatum. Dies ist die Ablauffrist; 
	nach diesem Zeitraum erscheint dieses Darlehen als &quot;abgelaufen&quot; in der Darlehensübersicht 
	des Mitglieds und in der Funktion „Darlehen verwalten“ des Verwaltungsabschnitts. 
	<li><b>Mit Gebühren:</b> Dieses Darlehen kann mit unterschiedlichen Arten von Gebühren und 
	periodischen Rückzahlungen verbunden sein. Jede Gebühr kann einen eigenen Rückzahlungstyp 
	haben. Alles ist wie bei den vorher behandelten Darlehenstypen, allerdings sind einige 
	zusätzliche Gebühren möglich. 
</ul>
<hr class="help">

<span class="admin broker"> <a NAME="loan"></a>
<h3>Darlehen bewilligen</h3>
Mit dieser Funktion können Sie einem Mitglied <a href="#top"><u>Darlehen</u></a> erteilen. 
Um Darlehen erteilen zu können, müssen einige Bedingungen erfüllt sein; um zu sehen, 
welche, <a href="#top"><u>klicken Sie bitte hier</u></a>.
<br><br>Um ein Darlehen zu bewilligen, müssen die folgenden Felder ausgefüllt werden: 
<ul>
	<li><b>Darlehensgruppe:</b> Diese Option erscheint, wenn das Mitglied für eine oder für
	 mehrere <a href="${pagePrefix}loan_groups"><u>Darlehensgruppen</u></a> das verantwortliche 
	 Mitglied ist. Falls Sie hier keine Darlehensgruppe beteiligen, sondern das Darlehen an das
   Mitglied persönlich geben möchten, wählen Sie bitte die Option &quot;Persönlich&quot;. 
	 <br>
	<li><b>Identfikation:</b> Dieser Name dient der Identifikation dieses Darlehens. 
	 Hier haben Sie freie Wahl.<br>
   Anmerkung: Das Identifikations-Feld ist ein <a href="${pagePrefix}custom_fields"><u>
   benutzerdefiniertes Darlehensfeld</u></a> der Basiseinstellung. Sie können es entfernen oder 
   aber andere benutzerdefinierte Darlehensfelder mit anderen Regeln einrichten.<br>
	<li><b>Darlehenstyp:</b> Dies ist das wichtigste Feld des Formulars. Hier wählen Sie, welchem 
	Überweisungstyp das Darlehen zugehört. Jeder dieser Überweisungstypen beinhaltet einen der drei 
	möglichen  <a href="#loan_types"><u>Darlehenstypen</u></a>. Abhängig von dem entsprechenden 
	Darlehenstyp erscheinen dann die übrigen Felder des Formulars. Die <b>Darlehenstyp-spezifischen Felder</b> 
	behandeln wir weiter unten.<br>
	<li><b>Beschreibung:</b> geben Sie eine Beschreibung des Darlehens ein.<br>
	<li><b>Betrag:</b> Dies ist der Gesamtbetrag, der auf das Konto des Mitglieds überwiesen wird. 
	Dies ist der ursprüngliche Schuldbetrag, oder Darlehensbetrag.<br>
	<li><b>Bewilligung rückdatieren:</b> Klicken Sie dieses Kontrollkästchen, wenn das Datum für 
	das Darlehen NICHT heute, sondern statt dessen ein bereits vergangenes Datum sein soll. 
	Wenn Sie dieses Kästchen wählen, werden Sie in einem dann erscheinenden Feld gebeten,  
	das Datum anzugeben.<br>
	<li><i>Darlehenstyp-spezifische Felder:</i> Die übrigen Felder des Formulars hängen davon ab, 
	was Sie im Auswahlfeld &quot;Darlehenstyp&quot; gewählt haben. 
	<ul>
		<li><b>Einfaches Darlehen:</b> Entspricht der von Ihnen gewählte Überweisungstyp einem 
		&quot;Einfachen Darlehen&quot;, so erscheinen die folgenden Felder: 
		<ul>
			<li><b>Rückzahlungstermin:</b> Das Datum, zu dem das Darlehen zurück gezahlt sein muss. 
			An diesem Datum wird eine Meldung generiert, und der Darlehensstatus geht in 
			&quot;Abgelaufen&quot; über (im Abschnitt Darlehensverwaltung). 
		</ul>
		<br>
		<li><b>Darlehen mit Mehrfachzahlungen:</b> Entspricht der von Ihnen gewählte Überweisungstyp 
		einem &quot;Darlehen mit Mehrfachzahlungen&quot;, so erscheinen die folgenden 
		Felder: 
		<ul>
			<li><b>Erstes Ablaufdatum:</b> Die Rückzahlung des Darlehens ist in mehrere 
			&quot;<a href="#component"><u>Darlehenskomponenten</u></a>&quot; unterteilt. 
			Geben Sie hier bitte das Datum ein, an dem die erste Komponente zurück gezahlt werden 
			muss (<b>nicht</b> der Gesamtbetrag des Darlehens). An diesem Datum wird eine Meldung 
			generiert und der Status der Darlehenskomponente geht in &quot;Abgelaufen&quot; über. 
			<li><b>Anzahl der Rückzahlung:</b> Die Anzahl der monatlichen Rückzahlungen 
			(Darlehenskomponenten). 
			<li><b>Berechnen:</b> Die Schaltfläche &quot;Berechnen&quot; zeigt die verschiedenen 
			Darlehensrückzahlungen und deren Ablauftermine. Diese Termine und Werte können verändert 
			werden. Wenn Sie diese Werte ändern, achten Sie bitte darauf, dass die Summe der Komponenten 
			immer noch den gesamten Darlehensbetrag ergibt. 
		</ul>
		<br>
		<li><b>Darlehen mit Gebühren:</b> Entspricht der von Ihnen gewählte Überweisungstyp einem 
		&quot;Darlehen mit gebühren&quot;, so erscheinen die folgenden Felder: 
		<ul>
			<li><b>Alle Einstellungen für Zinsen und Gebühren:</b> Diese Felder über dem Feld 
			&quot;Beschreibung&quot; zeigen an, welche Gebühren entstehen. Dies nur zu Ihrer Information,
			 Änderungen können Sie hier keine vornehmen. Für mehr Information, <a	href="#make_loan_type"> 
			 <u>klicken Sie bitte hier</u></a>.
			<li><b>Erstes Ablaufdatum:</b> Die Rückzahlung des Darlehens ist in mehrere 
			&quot;<a href="#component"><u>Darlehenskomponenten</u></a>&quot;.unterteilt. Geben Sie hier 
			bitte das Datum ein, an dem die erste Komponente zurück gezahlt werden muss (<b>nicht</b> der 
			Gesamtbetrag des Darlehens). An diesem Datum wird eine Meldung generiert, und der Status der 
			Darlehenskomponente geht in &quot;Abgelaufen&quot; über.
			<li><b>Anzahl der Rückzahlungen:</b> Die Anzahl der monatlichen Rückzahlungen (Darlehenskomponenten). 
			<li><b>Anzeigen:</b> Die Schaltfläche &quot;Anzeigen&quot; zeigt die verschiedenen 
			Darlehenskomponenten und deren Ablauftermine. Diese Termine und Werte können nicht direkt 
			geändert werden, sondern nur durch Änderung des Gesamtbetrags (des Darlehens) oder der Anzahl 
			der Rückzahlungen. Die gezeigten Beträge beinhalten verschiedene Gebühren. 
		</ul>
		<br>
	</ul>
</ul>
<br><br>Anmerkung: Falls notwendig, ist es möglich  <a href="${pagePrefix}custom_fields"><u>zusätzliche 
Darlehensfelder</u></a> einzurichten.  Zum Beispiel ein Feld für die Nummer des Darlehensabkommens. 
<hr class="help">
</span>

<span class="admin broker"> <a name="loan_confirm"></a>
<h3>Darlehen bestätigen</h3>
Diese Maske dient einfach der Bestätigung der Darlehensinformationen, bevor das Darlehen 
ausgezahlt wird. Überprüfen Sie die Information, und klicken Sie auf &quot;Weiter&quot;, 
um das Darlehen auszuzahlen. 
<hr class="help">
</span>

<span class="admin"> <a NAME="search_loans_by_admin"></a>
<h3>Darlehen suchen</h3>
Diese Funktion verschafft Ihnen einen Überblick über alle Mitglieds-<a href="#top"><u>Darlehen</u></a>.
Für die Suche haben Sie hier eine Reihe von Optionen. Und wie immer bedeutet ein freigelassenes Feld dass 
Sie nach allen möglichen Ergebnissen für dieses Feld suchen. 
<ul>
	<li><b>Filter:</b> Die ersten beiden Filteroptionen stellen eine Kombination des Darlehensstatus 
	dar, bei dem &quot;Alle offenen&quot; alle noch nicht vollständig zurück gezahlten oder erledigten 
	bedeutet, und &quot;Alle geschlossenen&quot; alle vollständig zurückgezahlten oder erledigten.<br>
	Die übrigen Filteroptionen sind die verschiedenen Darlehens-<a href="#status"><u>Status</u></a>.
	<li><b>Benutzerdefiniertes Feld:</b> Gibt es für den Darlehenstyp ein benutzerdefiniertes Feld, und 
	ist dieses Feld für die Darlehenssuche konfiguriert, so erscheint dieses Feld nach der Filteroption. 
	<li><b>Darlehenstyp:</b> Gibt es mehr als einen Darlehenstyp, so können Sie diese im Auswahlfeld wählen. 
	Hier bezieht sich der Darlehenstyp auf den <a	href="${pagePrefix}account_management#transaction_types">
	<u>Überweisungstyp</u></a>, zu welchem das Darlehen gehört. 
	<li><b>Benutzername / Name des Mitglieds:</b> Mit dieser Option können Sie nach Darlehen eines bestimmten 
	Mitglieds suchen. Das Eingabefeld vervollständigt sich selbst bei der Eingabe. </li>
	<li><b>Benutzername /Name des Brokers (Darlehensagent):</b>  Diese Option ermöglicht Ihnen die Suche 
	nach allen mit einem bestimmten <a href="${pagePrefix}brokering"><u>Broker</u></a> (Agent)verbundenen 
	Mitgliedsdarlehen. Das Eingabefeld vervollständigt sich selbst bei der Eingabe. 
	<li><b>Überweisungsnummer:</b> Ist diese Option für das System aktiviert, können Sie hier nach 
	der Überweisungsnummer suchen. 
	<li><b>Darlehensguppe:</b> Mit dieser Option können Sie nach den einer bestimmten 
	<a href="${pagePrefix}loan_groups"><u>Darlehensgruppe</u></a> gegebenen Darlehen suchen. Diese 
	Option ist nur dann sichtbar, wenn es im System Darlehensgruppen gibt. 
	<li><b>Zeitraum Darlehensbewilligung:</b> Diese Option ermöglicht die Suche nach Darlehen, die 
	innerhalb eines bestimmten Zeitraums bewilligt wurden. 
	<li><b>Zeitraum Darlehensablauf:</b> Diese Option ermöglicht die Suche nach Darlehen, die innerhalb 
	eines bestimmten Zeitraums ablaufen werden. 
	<li><b>Rückzahlungszeitraum:</b> Diese Option ermöglicht Ihnen die Suche nach Darlehenskomponenten, 
	die innerhalb des spezifizierten Zeitraums zurück gezahlt wurden. 
</ul>
Klicken Sie auf &quot;Weiter&quot;, um die Abfrage zu starten.
<hr class="help">
</span>

<a NAME="search_loans_result"></a>
<h3>Ergebnisse der Darlehenssuche</h3>
Dieses Fenster zeigt das Ergebnis der Abfrage <a href="#top"><u>Darlehenssuche</u></a>. Das 
Fenster zeigt eine Liste mit den folgenden Informationen (möglicherweise sind nicht alle 
Spalten sichtbar, abhängig von Ihrem Weg und einigen Einstellungen): 
<ul>
	<li><b>Mitglied:</b> Mitglied, das das Darlehen erhalten hat. Klicken Sie auf den Namen um 
	zum <a href="${pagePrefix}profiles"><u>Profil</u></a> zu gelangen. 
	<li><b>Beschreibung:</b> Beschreibung des Darlehens.
	<li><b>Betrag:</b> Der gesamte Darlehensbetrag. 
	<li><b>Verbleibender Betrag:</b> Der gesamte Betrag des Darlehens, der noch zur 
	Bezahlung aussteht. 
	<li><b>Zahlungen:</b> Die Anzahl der  <a href="#component"><u>Darlehenskomponenten</u></a>.
	Die erste Zahl gibt die Anzahl der bereits getätigten Rückzahlungen an. Die zweite Zahl (nach 
	dem Schrägstrich) ist die Gesamtzahl der Darlehenskomponenten. Dies erscheint nicht, wenn 
	die Liste nur einfache Darlehen ohne Ratenzahlung enthält. 
	<li><b>Bearbeitungssymbol: </b><img border="0" src="${images}/edit.gif" width="16"
		height="16">&nbsp; verwenden Sie dies, um Darlehensdetails zu zeigen, 
		zusammen mit zusätzlicher Information. 
</ul>
Oben rechts im Fenster sind weitere Symbole sichtbar. Das Speichern-Symbol  
<img border="0" src="${images}/save.gif" width="16" height="16">
&nbsp; exportiert die Liste in eine <a href="#csv"><u>CSV-Datei</u></a>. Das Drucken-Symbol 
<img border="0" src="${images}/print.gif" width="16" height="16">
&nbsp;öffnet eine druckbare Seite mit den Details aller aufgelisteten Darlehen. 
<hr class="help">

<a NAME="search_loans_member_by_admin"></a>
<a NAME="search_loans_by_member"></a>
<a NAME="search_loans_member_by_broker"></a>
<span class="admin broker">
<h3>Suche nach Darlehen eines Mitglieds</h3>
</span>
<span class="member">
<h3>Meine Darlehen suchen</h3>
</span>
Diese Funktion verschafft Ihnen einen Überblick über alle <a href="#top"><u>Darlehen</u></a>
<span class="admin broker">des Mitglieds</span>. Treffen Sie Ihre Auswahl über die 
Optionsschaltfläche &quot;<a href="#open"><u>Offen</u></a>&quot; oder &quot;<a href="#closed"><u>Geschlossen</u></a>&quot;. Das 
<a href="#search_loans_result"><u>Fenster Suchergebnisse</u></a>
unten zeigt die Ergebnisse.
<hr class="help">

<span class="admin"> <a NAME="search_loan_payments"></a>
<h3>Darlehenszahlungen suchen</h3>
Diese Seite ermöglicht Ihnen die Suche nach Informationen zu Zahlungen der  
<a href="#top"><u>Darlehen</u></a> – einschließlich der Information zu noch nicht 
ausgezahlten Darlehen. Die folgenden Felder sind verfügbar, und wie immer ergibt die 
unspezifizierte Suche alle für dieses Feld möglichen Werte:
<ul>
	<li><b>Status:</b> Hier können Sie Anfragen zum <a href="#status"><u>Status</u></a> 
	des Darlehens.
	<li><b>Benutzerdefiniertes Feld:</b> Gibt es für den Darlehenstyp ein benutzerdefiniertes 
	Feld, und ist dieses Feld für die Darlehenssuche konfiguriert, so erscheint dieses Feld 
	nach der Filteroption. 
	<li><b>Überweisungstyp:</b> Dies ist der <a	
	href="${pagePrefix}account_management#transaction_types"><u>Überweisungstyp</u></a> des Darlehens. 
	(Erscheint nur dann, wenn mehr als ein Darlehens-Überweisungstyp existiert). 
	<li><b>Benutzername/Name:</b> Dies sind der Benutzername und der Name des Mitglieds. 
	<li><b>Broker Benutzername/Name:</b> sind der Benutzername und der Name des <a
		href="${pagePrefix}brokering"><u>Brokers</u></a> des Darlehensnehmers. 
	<li><b>Zeitraum Ablaufdatum:</b> Diese Option ermöglicht Ihnen die Suche nach
	Darlehenszahlungen die innerhalb eines bestimmten Zeitraumes ablaufen.
	<li><b>Zeitraum Zahlungsdatum:</b> Diese Option ermöglicht Ihnen die Suche nach
	Darlehenszahlungen die innerhalb eines bestimmten Zeitraumes getätigt wurden.
	
</ul>
<hr class="help">
</span>

<span class="admin">
<a NAME="search_loan_payments_result"></a>
<h3>Suchergebnisse Darlehenszahlungen</h3>
Diese Seite zeigt die Ergebnisse der Suchanfrage für Darlehenszahlungen.
<ul>
	<li><b>Mitglied:</b> Mitglied, das das Darlehen erhalten hat. Klicken Sie den
	Namen an um zum <a href="${pagePrefix}profiles"><u>Profil</u> zu gelangen</a>.
	<li><b>Datum:</b> Das Ablaufdatum des Darlehens.
	<li><b>Betrag:</b> Der Gesamtbetrag des Darlehens.
	<li><b>Status:</b> Der  <a
		href="#status"><u>Status</u></a> der Darlehenszahlung.
	<li><b>Zurückbezahlt:</b> Der Betrag der intern zurück gezahlten Darlehenszahlungen.
	<li><b>Erledigt:</b> Der Betrag der erledigten Darlehenszahlungen.
	<li><b>Symbol anzeigen <img border="0" src="${images}/view.gif"
		width="16" height="16">&nbsp;: </b> verwenden Sie dies, um die Details
	der Darlehenszahlungen zusammen mit zusätzlicher Information zu zeigen.
</ul>
Oben rechts auf der Seite finden Sie zusätliche Symbole. Das 
<img border="0" src="${images}/save.gif" width="16" height="16">
&nbsp; Speichern-Symbol exportiert die Liste in eine 
<a href="#csv"><u>CSV-Datei</u></a>. 
Das
<img border="0" src="${images}/print.gif" width="16" height="16">
&nbsp;Drucken-Symbol öffnet eine druckbare Seite mit den Details aller aufgelisteten 
Darlehen.
<hr class="help">
</span>

<a NAME="loan_detail"></a>
<h3>Darlehensdetail</h3>
Diese Seite zeigt Details in Zusammenhang mit dem <a href="#top"><u>Darlehen</u></a>. 
Je nach Darlehenstyp zeigt die Seite verschiedene Darlehenswerte. 	
<br><br>Das Drucken-Symbol <img border="0" src="${images}/print.gif" width="16"
	height="16">&nbsp;öffnet eine druckbare Seite mit Einzelheiten zum Darlehen und allen 
	<a href="#component"><u>Darlehenskomponenten</u></a>.  
	
	
 <span
	class="admin"> In bestimmten Phasen des Darlehens (mit Status &quot;abgelaufen&quot; 
	oder &quot;in Bearbeitung&quot; können Sie den <a href="#status"><u>Status</u></a> 
	über die Schaltfläche unten &quot;Dieses Darlehen markieren als...&quot; verändern.</span>
<hr class="help">

<a NAME="loan_parcels_detail"></a>
<h3>Details Darlehenrückzahlung</h3>
Diese Seite zeigt  Details in Zusammenhang mit den <a href="#component"><u>Darlehenskomponenten</u></a>. 
Alle Komponenten des Darlehens sind in dieser Übersicht aufgelistet. Die Tabelle ist 
einfach verständlich. Der <a href="#status"><u>Status</u></a> kann einer von mehreren 
Werten sein. 
<hr class="help">

<span class="admin"> <a NAME="loan_to_members"></a>
<h3>Darlehen an Mitglieder</h3>
Diese Seite zeigt eine Liste der Mitglieder die zur  <a
	href="${pagePrefix}loan_groups"><u>Darlehensgruppe</u></a> des gewählten  <a
	href="#top"><u>Darlehens</u></a> gehören. Der Name des  &quot;verantwortlichen&quot; Mitglieds 
	(des Mitglieds, das das Darlehen erhalten hat) erscheint in rot. Anklicken der Namen bringt  
	Sie zu den  <a href="${pagePrefix}profiles"><u>Profilen</u></a> der Mitglieder. 
<hr class="help">
</span>

<a NAME="loan_repayment_by_admin"></a>
<a NAME="loan_repayment_by_member"></a>
<h3>Darlehensrückzahlung</h3>
Diese Seite enthält Information zu den <a href="#top"><u>Darlehens</u></a>-Komponenten, 
und der Möglichkeit, der <span class="admin">Erledigung</span>
der <a href="#component"><u>Darlehenskomponenten</u></a>.
<br>
Sie können den Betrag anpassen, die Voreingabe ist allerdings der von Ihnen noch geschuldete Betrag. 
<span class="admin"> Wenn Sie das Kästchen  &quot;Zahlung rückdatieren&quot; wählen, wird die 
Darlehensrückzahlung auf ein vergangenes Datum gebucht. In einem besonderen Bearbeitungsfeld 
werden Sie gebeten, dieses Datum zu spezifizieren. </span>
<br><br>Ist das Darlehen eines mit  <a href="#loan_types"><u>mehrfachen Rückzahlungen</u></a> 
Rückzahlungen (dies beinhaltet auch Darlehen mit Gebühren), so sind einige weitere Felder verfügbar.  
Dieser Felder sind für einfache Darlehen nicht verfügbar. Die &quot;Zahlungsnummer&quot; bezieht 
sich auf die Darlehenskomponente in der obigen Übersicht, normalerweise zahlen Sie die nächste 
Zahlung in der Reihe (die niedrigste verfügbare und noch nicht getätigte Zahlungsnummer), 
allerdings können Sie auch eine andere Komponente wählen.
<br><br>
<span class="admin"> Verwenden Sie eine der Schaltflächen um die Rückzahlung zu tätigen oder das 
Darlehen als &quot;erledigt&quot; zu markieren.</span> <span class="member">
Klicken Sie auf die Schaltfläche &quot;Zurückzahlen&quot; um das Darlehen (einen Teil davon) 
zurück zu zahlen.</span>
<hr class="help">

<a name="glossary"></a>
<h2>Glossar der verwendeten Begriffes</h2>

<a name="component"></a>
<b>Rate</b>
<br>Eine von mehreren aufeinander folgenden Zahlungen zur Tilgung einer Schuld. Ist die Rückzahlung 
eines Darlehens in mehrere Teile unterteilt, so heißt jeder dieser Teile &quot;Rate&quot;. 
<br><br>

<a name="csv"></a>
<b>CSV-Datei</b>
<br>
CSV &quot;Comma separated values&quot;, dies ist das Format der Dateien, die von den 
verschiedenen Suchergebnis-Fenstern in Cyclos herunter geladen werden können. Wie der 
Name bereits besagt, sind in diesem Format die Werte der Felder durch Kommas getrennt 
(obwohl auch jedes andere Zeichen als Trennsymbol verwendet werden kann).<br>
Dieses Format kann normalerweise über ein Programm zur Tabellenkalkulation wie 
&quot;Open Office Calc&quot; oder &quot;Microsoft Excel&quot; geöffnet werden. Sie können 
die CSV-Datei allerdings auch über einen Texteditor in Kombination mit Makros bearbeiten. 
Programme wie Word oder WordPerfect haben ausgezeichnete Makro-Einrichtungenn um Eingabedateien 
automatisch zu übersichtlichen Dokumenten zu verarbeiten. 
<br><br>

<a name="status"></a>
<b>Darlehensstatus</b><br>   
Der Darlehensstatus kann sich sowohl auf Darlehen als auch auf <a href="#component">
<u>Darlehenskomponenten</u></a> beziehen. Dabei kann es sich um folgende Angaben 
handeln: 
<ul>
	<li><b>Offen:</b> Ein &quot;offenes&quot; Darlehen ist eines, das noch nicht zurück 
	gezahlt wurde, und das Ablaufdatum noch nicht erreicht hat. Seitens des Mitglieds gibt 
	es weiterhin eine Zahlungsverpflichtung. 
	<li><b>Abgelaufen:</b> Das Ablaufdatum des Darlehens ist überschritten, es wurde allerdings 
	noch nicht vollständig zurück gezahlt. 
	<li><b>Abgeschlossen / zurückgezahlt:</b> Das Darlehen wurde zurück gezahlt
	und gilt verwaltungstechnisch als abgeschlossen. Das Mitglied hat keine 
	Zahlungsverpflichtung mehr. 
  <li><b>Erledigt</b> Eine Darlehenskomponente gilt normalerweise als &quot;Erledigt&quot; wenn 
	das Darlehen auf anderem Wege zurück gezahlt wurde, z.B. durch Waren oder konventionelle
  Währung. Eine erledigte Darlehenskomponente gilt als abgeschlossen.<br>
	<li><b>In Bearbeitung:</b> Erreicht ein Darlehen das Ablaufdatum, kann ein Administrator 
	den Status zu &quot;In Bearbeitung&quot; verändern. Dies geschieht meist um das Darlehen neu 
	zu verhandeln. Danach kann der Administrator das Darlehen entweder in &quot;Einziebar&quot; 
	oder &quot;Nicht rückzahlbar&quot; verschieben (siehe nächster Punkt). Dieser Staus ist nur für 
	ein abgelaufenes Darlehen möglich.  Er bedeutet, dass das Darlehen zwar abgelaufen ist, 
	die Parteien aber weiterhin darüber verhandeln, was damit zu tun ist.<br>
	<li><b>Eingezogen:</b> Dies ist der Status nach &quot;In Bearbeitung&quot;. 
	Dies bedeutet, dass das Darlehen zurückbezahlt wurde. 
	<li><b>Nicht rückzahlbar:</b> Zu diesem Status gelangt ein Darlehen nur über den 
	&quot;In Bearbeitung&quot;-Status.  Genau genommen bedeutet er, dass das Darlehen noch immer 
	fällig ist, dass aber alle Parteien davon ausgehen, dass es nicht vom Mitglied zu zahlen ist, 
	und auch keine Zahlungen mehr zu erwarten sind. Das Darlehen ist sozusagen &quot;eingefroren&quot;. 
	<li><b>Anstehende Autorisierung:</b> Die Zahlung des Darlehens bedarf noch der Autorisierung. Ist die 
	Zahlung des Darlehens erst einmal autorisiert, geschieht die Überweisung automatisch.
	(Diese Option wird in den Suchfiltern nur angezeigt, wenn der Administrator die Berechtigung hat,
	autorisierte Zahlungen anzusehen) 
	<li><b>Autorisierung abgelehnt:</b> Die Zahlung des Darlehens wurde abgelehnt. Das bedeutet, 
	dieses Darlehen wurde von der Verwaltung storniert.
	(Diese Option wird in den Suchfiltern nur angezeigt, wenn der Administrator die Berechtigung hat,
	autorisierte Zahlungen anzusehen) 
</ul>

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