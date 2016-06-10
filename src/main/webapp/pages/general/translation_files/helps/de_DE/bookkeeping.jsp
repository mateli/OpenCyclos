<div style="page-break-after: always;">
<br><br>Die Funktion Buchhaltung kann verwendet werden, um 
&quot;externe Konten&quot; wie Bankkonten  oder Konten von anderen Währungssystemen 
mit Konten in einem Cyclos-System abzubilden (spiegeln). 
<br><br>
Da die Buchhaltungsfunktion es ermöglicht, innerhalb von Cyclos ein vollständiges 
Abbild des externen Kontos (und seiner Überweisungen) zu haben, ist es auch möglich, 
die Cyclos-Überweisungen verwaltungstechnisch mit den importierten Überweisungen 
zu &quot;verbinden&quot;. Dies bedeutet, dass beide Überweisungen aufeinander verweisen 
und einen besonderen Status innehaben. Dieser Status wird als ein bestimmtes Symbol im 
Fenster &quot;Überweisungs-Zusammenfassung&quot; erscheinen. Es ist auch möglich, 
Überweisungslisten nach Status zu durchsuchen und auszudrucken.
<br><br>Eine typische Verwendung des Buchhaltungsmoduls ist die Kontrolle der Salden in Systemen, 
in denen interne (Cyclos-)Einheiten durch konventionelles Geld in einem Bankkonto gedeckt 
werden. In einem solchen Fall stehen die Überweisungen auf dem Bankkonto in direkter 
Beziehung mit spezifischen Überweisungen von Einheiten in Cyclos. Eine Einzahlung 
(eingehende Zahlung) bei der Bank könnte z.B. verbunden sein mit einer System-an-Mitglied-Zahlung, 
möglicherweise einem Ankauf von Cyclos-Einheiten, oder der Rückzahlung eines Darlehens in Cyclos. 
Und andersherum, eine Zahlung vom Bankkonto (Abbuchung) an das Bankkonto eines Mitglieds 
kann in Zusammenhang stehen mit einer Mitglied-an-System-Zahlung (z.B. Umtausch) in Cyclos. 
Die Funktion &quot;Abgleich&quot; hilft dabei, die &quot;Deckung&quot; der 
Cyclos-Einheiten zu kontrollieren. Der &quot;Abgleichs-Saldo&quot; eines Systems, in dem die 
Einheiten zu 100% durch konventionelle Währung gedeckt sind, ist eins zu eins. 
<br><br>Es ist möglich, automatisch spezifische (Cyclos-)Überweisungen zu importierten Überweisungen 
zu generieren. Zum Beispiel kann eine eingehende Zahlung bei einem externen Bankkonto in 
Cyclos eine System-an-Mitglied-Zahlung generieren.<br>
Die Einzelheiten dazu sind im Abschnitt &quot;Wichtige Import-Einstellungen&quot; des Moduls 
&quot;Buchhaltung&quot; erklärt. 
<br><br>Kontrolle der &quot;Deckung&quot; ist nur ein Beispiel für die Verwendung des Buchhaltungs-Moduls.  
Das Modul kann auch für andere Fälle verwendet werden, in denen eine externe Überweisung eine 
lokale Überweisung oder eine Darlehens-Status-Änderung in einem lokalen System generieren muss.
<br><br>
<i>Wo ist es zu finden?</i>
<br>
Das Buchhaltungsmodul finden Sie unter &quot;Menü: Buchhaltung&quot; (damit dieses 
Menü-Element erscheint, müssen die entsprechenden Berechtigungen für die 
Administratorengruppe eingestellt sein). 
<br><br>
<i>Wie wird die Buchhaltung aktiviert?</i><br>
Damit das Modul erscheint, benötigen Sie die richtigen <a
	href="${pagePrefix}groups#manage_group_permissions_admin_system"><u>Administratoren-
Berechtigungen</u></a> im Abschnitt &quot;Externe Konten (Buchhaltung)&quot;.
<br><br>Bevor Sie in der Lage sind, neue externe Überweisungen in Cyclos zu importieren, 
müssen Sie Folgendes tun: 
<ol>
	<li>Überprüfen Sie, ob es die  <a
		href="${pagePrefix}account_management#transaction_types"><u>
	Überweisungstypen</u></a>, die Sie für diese Überweisung brauchen, gibt. 
	<li>Richten Sie ein externes Konto ein, über &quot;Menü: Externe Konten verwalten >
	Neues externes Konto&quot;.
	<li><b>Datei-Zuordnung:</b> Erstellen Sie eine Definition der Import-Datei, im Fenster 
	unter demjenigen, in welchem Sie das externe Konto eingerichtet haben. 
	<li><b>Feldzuordnung:</b> Im nächsten Fenster weisen Sie Cyclos an, wie die 
	Felder dieser Datei zu behandeln sind. 
	<li><b>Zahlungstyp-Zuordnung:</b> Instruieren Sie Cyclos, wie die Werte in den 
	Fenstern, die den Zahlungstyp definieren, den Zahlungstypen zuzuordnen sind. 
	Dies tun Sie im nächsten Fenster. 
</ol>
Erst wenn Sie diese Schritte durchgeführt haben (einmal genügt), sind Sie in der Lage, 
neue externe Überweisungen über eine Überweisungsdatei, die Sie von Ihrem Bankkonto 
erhalten haben, zu importieren.  
<a href="#using"><u>Klicken Sie hier</u></a>
um eine Übersicht darüber zu erhalten, wie Sie die neuen Überweisungen von der Datei 
importieren können. 

<hr>

<A NAME="external_accounts_list"></A>

<h3>Externe Kontenliste</h3>
Dieses Fenster zeigt eine Liste aller externen Konten. 
<ul>
	<li><img border="0" src="${images}/edit.gif" width="16" height="16">
	Dieses Symbol anklicken, um die externen Konten zu ändern. 
	<li><img border="0" src="${images}/delete.gif" width="16" height="16">
	Klicken Sie dieses Symbol, um die externen Konten zu löschen. 
</ul>
Ein neues externes Konto hinzufügen können Sie über die Schaltfläche 
&quot;Neues externes Konto&quot; unten. 

<hr class="help">

<A NAME="new_edit_external_account"></A>

<h3>(Neues) externes Konto bearbeiten</h3>
Auf dieser Seite können Sie ein externes Konto und die dazugehörigen internen 
System- und Mitgliedskonten definieren.<br>
Überweisungen auf externen Konten sind meist verbunden mit Überweisungen in Zusammenhang 
mit der Einrichtung von Einheiten (z.B. Darlehen) und deren Auflösung (z.B. Umtausch). 
Daher ist der Kontentyp eines Systemkontos, das mit einem externen Konto verbunden ist, 
meist &quot;unbegrenzt&quot;. Mehr Information dazu finden Sie in der 
<a href="${pagePrefix}account_management#account_details"><u>
Hilfe</u></a> unter &quot;Konto einrichten&quot;. 
<hr class="help">


<A NAME="edit_file_mapping"></A>

<h3>Datei-Zuordnungen bearbeiten</h3>
Um die Überweisungsinformationen eines externes Kontos importieren zu können, müssen 
Sie die Zuordnung zwischen den Feldern des externen Kontos und den Cyclos-Feldern definieren. 
Nach Einrichtung des ersten externen Kontos werden Sie gebeten, eine neue Datei-Zuordnung 
einzurichten. Die folgenden Zuordnungen sind verfügbar: 
<ul>
	<li><b>Typ: </b>CSV oder benutzerdefiniert (Java-Klasse)<br>
	Normalerweise enthalten Überweisungsdateien Reintext (unformatiert), in dem Werte durch 
	ein Trennzeichen getrennt werden. In diesem Fall können Sie die 
	<a href="${pagePrefix}loans#csv"><u>CSV</u></a>-Option verwenden.<br>
  Hat die Überweisungsdatei ein komplexeres Format, z.B. xml, ist es möglich, eine 
  benutzerdefinierte  Klasse zu programmieren, die mit diesem Format umgehen kann. 
  Obwohl wir in dieser Dokumentation  nicht bis ins letzte Detail gehen wollen, 
  müssen Sie Folgendes tun: 
	<ol>
		<li>Richten Sie eine Java-Klasse ein, die das Cyclos-Interface TransactionFileImport 
		(im utils-package) implementiert. 
		<li>Tragen Sie die Klasse in den classpath ein und kopieren sie in das Server Verzeichnis 
		WEB-INF/classes, oder in ein Bibliotheken-Verzeichnis (lib), wenn sie in einer
		jar-Datei verpackt ist.   
	</ol>
	<li><b>Spalten-Trennzeichen: </b><br>
	Das in der Datei verwendete Spalten-Trennzeichen, meist ein Komma &quot;,&quot;.
	<li><b>Kopfzeilen: </b><br>
	Die Anzahl der Kopfzeilen (die nicht die tatsächlichen Werte enthalten). 
	Diese Zeilen werden ignoriert. 
	<li><b>Zahlenformat: </b><br>
	Hier gibt es zwei mögliche Optionen: 
	<ul>
		<li><b>Feste Position</b> In manchen Fällen enthält das Format für den Betrag in den 
		Überweisungsdateien kein Trennzeichen, das Trennzeichen hat aber eine feste Position, 
		und wird von rechts aus kalkuliert. Zum Beispiel ergibt der Betrag 50000 mit 2 Dezimalstellen 
		500 (oder 500,00 mit Komma).<br>
		Wenn Sie diese Option wählen, heißt das Feld rechts daneben &quot;Kommastellen&quot;. 
		Dort geben Sie normalerweise &quot;2&quot; ein. 
		<li><b>Mit Trennzeichen:</b> Üblicherweise wird ein Trennzeichen verwendet, dies können 
		Sie im Feld &quot;Dezimal-Trennzeichen&quot; definieren (rechts). Meist handelt es sich um 
		einen Punkt &quot;.&quot; oder ein Komma. 
	</ul>
	<li><b>Negativ-Zeichen: </b><br>
	In manchen Fällen ist das Format des Betragsfeldes in Überweisungsdateien niemals negativ, 
	für negative Beträge gibt es allerdings ein besonderes Zeichen in einer separaten Spalte. 
	Dies kann z.B. ein &quot;-&quot; sein, oder ein D (Debit) bzw. S (Soll). Der Wert der Betragsspalte  
	|-500,00| kann das Gleiche sein wie in den Spalten  |D|500,00| (mit | als Spalten-Trennzeichen). 
	In diesem Fall geben Sie dieses Zeichen in dieses Feld ein.<br>
	Normalerweise ist allerdings ein negativer Betrag von einem Minuszeichen im gleichen Feld begleitet, 
	und die Negativ-Spalte wird nicht benötigt. 
	<li><b>Texterkennungszeichen: </b><br>
	Überweisungsdateien und CSV-Dateien enthalten häufig Texterkennungszeichen (um Trennungen von Zeilen und 
	Spalten zu vermeiden). Hier können Sie das Texterkennungszeichen definieren (meist "). 
	<li><b>Datumsformat: </b><br>
	Hier können Sie das Datumsformat definieren. Sie können y für das Jahr, M für Monat (muss 
	ein Großbuchstabe sein) und d für Tag verwenden. Zwischen den Werten können Sie irgendein 
	Trennzeichen verwenden, wie z.B. dd/MM/yy oder yyyy-MM-dd etc. 
</ul>
Klicken Sie auf &quot;Weiter&quot;, um die Datei-Zuordnungen zu speichern. Klicken von &quot;Reset&quot; 
entfernt alle Datei-Zuordnungen; Sie werden dann gebeten, eine Neue einzurichten. Die Zahlungstypen 
(Fenster unten) werden durch das Zurücksetzen der Datei-Zuordnung nicht entfernt. 

<hr class="help">

<A NAME="file_mapping_fields_list"></A>

<h3>Datei-Feldzuordnung</h3>
Ist das Datei-Zuordnungsformat erst einmal definiert (Fenster oben), können Sie die 
Felder der Überweisungsdatei den Cyclos-Feldern zuordnen. Alle Zeilen der Liste ergeben 
eine Zeile in der Überweisungsdatei, und jedes Feld ist eine Spalte. 
<br><br>
Wenn Sie dieses Fenster zum ersten Mal anschauen, sind in der Liste keine Felder zu sehen. 
Um eine neue Feld-Zuordnung einzufügen, klicken Sie bitte auf &quot;Neues Zuordnungsfeld 
einfügen&quot;. Dies müssen Sie für alle Felder wiederholen (die in der Datei für die  
Überweisungen vorhanden sind).<br>
Das erste Feld (mit Ordnungszahl 1) ist dann die  erste (linke) Spalte in der Überweisungsdatei. 
Stellen Sie bitte sicher, dass die Feld-Anordnung mit der Spalten-Anordnung übereinstimmt. 
<br><br>
Sind erst einmal Elemente auf der Liste, können Sie folgende Dinge tun: 
<ul>
	<li>Sie können die Feld-Anordnung verändern durch Anklicken der Schaltfläche &quot;Weiter&quot; neben 
	&quot;Anordnung der Feldzuordnung ändern&quot;.
	<li><img border="0" src="${images}/edit.gif" width="16" height="16">&nbsp;
	Anklicken dieses Symbols ermöglicht Ihnen, die Eingabe für dieses Feld zu ändern. 
	<li><img border="0" src="${images}/delete.gif" width="16" height="16">&nbsp;Anklicken 
	dieses Symbols löscht das Feld aus der Liste. 
</ul>
<hr class="help">


<A NAME="edit_field_mapping"></A>

<h3>(Neue) Feldzuordnung bearbeiten</h3>
Hier können Sie die Zuordnung pro Feld definieren. Sie können einen Namen eingeben, 
der dann als Textbezeichnung in der Liste erscheint. Die Bezeichnung hat keine weitere 
Funktion. Im Auswahlfeld können Sie die folgenden Optionen wählen: 
<ul>
	<li><b>Ignorieren: </b>
	Falls die Datei zusätzliche Spalten enthält, die nicht zu Cyclos-Feldern zugeordnet 
	werden müssen, müssen Sie trotzdem eine Zuordnung dafür einrichten. Stellen Sie hier 
	einfach die Option &quot;Ignorieren&quot; ein. 
	<li><b>Mitglieds-Id: </b>
	Diese Option kann verwendet werden, um die in Cyclos verwendeten internen Identifikationsnummern 
	zuzuordnen. Es ist unwahrscheinlich, dass eine Überweisungsdatei die Cyclos-internen 
	(Mitglieds-)Identifikationsnummern enthält; wir haben allerdings die Option eingebaut, um ein vollständiges 
	Angebot an Optionen zu schaffen. 
	<li><b>Mitglieds-Benutzername: </b>
	Spezifiziert die Überweisungsdatei einen (Cyclos-)Benutzernamen, können Sie die Spalte mit 
	dieser Option definieren. 
	<li><b>Benutzerdefiniertes Mitgliedsfeld: </b>
	Spezifiziert die Überweisungsdatei keinen Benutzernamen, ist es möglich, ein benutzerdefiniertes 
	(Profil-)Feld zuzuordnen, um einen Benutzer zu identifizieren. Dabei muss es sich um ein einmaliges 
	benutzerdefiniertes Feld handeln. Zum Beispiel eine Steuernummer. 
	<li><b>Zahlungstyp: </b>
	Wenn Sie Überweisungen importieren und Zahlungen von der Überweisungsdatei generieren 
	möchten, 	müssen Sie einen Zahlungstyp definieren. Meist entsprechen die Überweisungen 
	in der Überweisungsdatei mehr als einem Überweisungstyp. Zum Beispiel Einlage/Einzahlung, 
	Darlehensrückzahlung, etc. Der Typ der Zahlung wird in einer separaten Spalte durch einen 
	Code spezifiziert. Mit dieser Option definieren Sie, welche Spalte den Zahlungstyp darstellt. 
	Die möglichen Werte der verschiedenen Zahlungstypen können in der Funktion Zahlungstyp unter 
	diesem Fenster definiert werden. 
	<li><b>Zahlungsdatum: </b>
	Mit dieser Option können Sie die Spalte spezifizieren, die das Zahlungsdatum der Überweisung 
	enthält.  
	<li><b>Zahlungsbetrag: </b> 
	Mit dieser Option können Sie die Spalte spezifizieren, die den Zahlungsbetrag der Überweisung 
	enthält.  
	<li><b>Indikator Negativbetrag: </b> Es kommt vor, dass der Zahlungsbetrag selbst nicht anzeigt, 
	ob es sich um einen negativen oder um einen positiven Betrag handelt, sondern dass dies in einer 
	separaten Spalte spezifiziert ist. Mit diesem Feld können Sie definieren, ob das Feld Zahlungsbetrag 
	negativ ist, oder nicht. Dabei kann es sich um ein Zeichen (z.B. &quot;-&quot;) oder um ein Wort 
	(z.B. &quot;D&quot; oder &quot;Debit&quot; bzw. &quot;S&quot; oder &quot;Soll&quot;) handeln. 
</ul>
Zum Speichern klicken Sie bitte auf &quot;Weiter&quot;.
<br><br>
<b>Anmerkung:</b> Sie können jedes Feld nur einmal verwenden. Dies bedeutet, dass wenn Sie einen 
Feldtyp hinzugefügt haben (z.B. Zahlungstermin), können Sie diesen nicht noch einmal hinzufügen 
(es bietet sich gar nicht erst als Option an). Einzige Ausnahme ist das Feld &quot;Ignorieren&quot;, 
da es eine Reihe von Spalten geben kann, die Sie ignorieren (nicht importieren) möchten.<br>
Da Mitglieds-Id, Mitglieds-Benutzername und benutzerdefinierte Mitgliedsfelder alle der Definition 
des Mitglieds dienen, können Sie nur einen dieser drei Feldtypen in Ihrer Dateizuordnung verwenden. 

<hr class="help">

<a name="set_field_mappings_order"></a>
<h3>Anordnung der Feldzuordnung einstellen</h3>
In diesem Fenster können Sie die Anordnung der Feldzuordnungen ändern. Die Zuordnungen 
für die von Ihnen definierten Felder müssen genau der Anordnung der Felder in der 
Überweisungsdatei entsprechen, die Sie importieren möchten. 
<br><br>
Die Fenster funktionieren sehr einfach. Klicken Sie einfach auf den Feldnamen, und ziehen 
Sie ihn mit der Maus dahin, wo Sie ihn haben möchten. Wenn Sie fertig sind, klicken Sie 
auf &quot;Weiter&quot;, um das Ergebnis zu speichern 

<hr class="help">

<A NAME="external_transfer_type_list"></A>
<h3>Zahlungstyp (action mapping)</h3>
Dieses Fenster zeigt eine Liste der möglichen Zahlungstypen, die die Überweisungsdatei 
enthalten kann. Um Zahlungstypen hinzuzufügen, muss eine  
&quot;Zahlungstyp&quot;-Feldzuordnung existieren. In diesem Fenster informieren Sie Cyclos, welche 
Code-Werte in diesem Feld realen Zahlungstypen in Cyclos zugeordnet sind. Bitte beachten 
Sie, dass Sie jeden in diesem Fenster möglichen Wert zuordnen müssen; nicht zugeordnete 
Codes werden als &quot;Keine&quot; zugeordnet. 
<ul>
	<li><img border="0" src="${images}/edit.gif" width="16" height="16">
	Klicken Sie dieses Symbol, um die Zuordnung eines Zahlungstyps zu ändern. 
	<li><img border="0" src="${images}/delete.gif" width="16" height="16">
	Klicken Sie dieses Symbol, um die Zuordnung eines Zahlungstyps zu löschen. 
</ul>
Um eine Zuordnung eines Zahlungstyps hinzuzufügen, klicken Sie auf die Schaltfläche 
&quot;Weiter&quot; neben der Bezeichnung &quot;Neuen Zahlungstyp einfügen&quot;. 
<hr class="help">

<A NAME="edit_external_transfer_type"></A>
<h3>Überweisungstypen einfügen/ändern</h3>
Auf dieser Seite können Sie definieren, wie Cyclos einen Code im Fenster 
&quot;Zahlungstyp&quot; der Überweisungsdatei Ihrer Bank interpretieren soll. 
Hier definieren Sie, welchem Überweisungstyp eine spezifischer Code zugeordnet 
sein soll. Diesen Vorgang sollten Sie für jeden in diesem Feld möglichen Code 
wiederholen.<br>
Name und Beschreibung dienen ausschließlich internen Zwecken und haben keine 
weitere Funktion. Der Code ist einer der möglichen Werte des Feldes mit der 
<a href="#edit_field_mapping"> <u>Zahlungstyp Feldzuordnung</u></a>. 
Ein Beispiel finden Sie in der unten folgenden Liste. 
<br>
Die folgenden Aktionen sind möglich: 
<ul>
	<li><b>Keine: </b>
	Hier teilen Sie einfach mit, dass ein bestimmter Code keine Aktion auslösen soll. 
	Die Überweisung erscheint in Ihrer externen Kontoübersicht, so dass der Saldo 
	korrekt ist, generiert aber keine Zahlung in Cyclos. 
	<li><b>Zahlung an Mitglied generieren: </b>
	Hier ist ein Beispiel hilfreich. Angenommen: Eine Zahlungstyp-Spalte in der 
	Überweisungsdatei hat verschiedene mögliche Werte (Codes), und einer davon ist 
	&quot;DEP1&quot;, was bedeutet, dass es sich bei der Überweisung um eine Einzahlung 
	auf ein Bankkonto handelt. Wenn Sie möchten, dass dieser besondere Zahlungstyp automatisch 
	eine Zahlung von einem Systemkonto an das Mitglied generiert, sollte diese Option 
	gewählt werden. 
	<li><b>Zahlung an System generieren: </b>
	Dies wäre nur selten der Fall, steht aber zur Verfügung, um eine vollständige Bandbreite 
	der Optionen anzubieten. Dies würde bedeuten, dass eine auf ein externes Konto eingehende 
	Zahlung in Cyclos eine Mitglied-an-System-Zahlung generiert.<br>
	Als Beispiel wäre denkbar, dass Sie die Buchhaltung in der nationalen Währung in Cyclos 
	importieren möchten, um zu überprüfen, ob die Mitglieder pünktlich ihre Mitgliedsbeiträge 
	in nationaler Währung zahlen. In diesem Fall müssten Sie Konten für die Organisation in 
	nationaler Währung einrichten (um das Bankkonto des Systems wiederzugeben), zuzüglich 
	zusätzlicher Konten für Mitglieder, in nationaler Währung. Dies würde Ihnen gestatten, 
	Bankkonto-Überweisungen dieser eingerichteten Konten wiederzugeben, und zu überprüfen, 
	ob die Mitglieder ihre Mitgliedsbeiträge gezahlt haben (außerdem können Sie Statistiken 
	dazu herstellen). 
	<li><b>Darlehen erledigen: </b>
	Einige System beinhalten Cyclos-Darlehen, die extern zurück gezahlt werden können (z:B. in 
	konventionellem Geld). Wurde ein Darlehen extern zurück gezahlt, so möchten Sie nicht, dass 
	der Status in Cyclos sich zu &quot;zurückgezahlt&quot; ändert, da dieser Status für diejenigen 
	Rückzahlungen reserviert ist, die in der internen Cyclos-Währung vorgenommen wurden. Im 
	genannten Fall wäre der neue Status &quot;Erledigt&quot;. Wird ein Darlehen zurück gezahlt, 
	so kann der Darlehensstatus sich automatisch je nach Zahlungstyp ändern. 
	<li><b>Abgleichs-Zahlung: </b>
	Der Status &quot;Abgleich&quot; ist für eine interne Cyclos-Zahlung vorgesehen, für die es 
	eine externe, von der Verwaltung bestätigte Gegenzahlung gibt. Er wird verwendet, um den 
	Saldo einer Reihe von Cyclos-Konten zu kontrollieren (System und Mitglied) und den Saldo 
	auf einem externen Konto. 
</ul>
<hr class="help">

<a name="using"></a>
<h2>Verwendung des externen Kontos</h2>
Sie können externe Überweisungen in das externe Konto importieren und sie danach 
verarbeiten. Sie können das externe Konto auch nach Überweisungen durchsuchen. 

<i>Wo ist es zu finden?</i><br>
Die Übersicht &quot;Externes Konto&quot; finden Sie über &quot;Menü: Buchhaltung > Externe Konten&quot;. 
<br><br>
<i>Wie werden externe Konten aktiviert?</i><br>
Haben Sie erst einmal die Zuordnung für eine externe Datei konfiguriert (siehe weiter 
oben in der Datei), können Sie damit beginnen, Überweisungen von einer Datei Ihrer Bank 
zu importieren. Damit dies funktioniert, sollten Sie die folgenden Aktionen 
ausführen: 
<ol>
	<li>Gehen Sie bitte zu  &quot;Menü: Buchhaltung > Externe Konten&quot;, und klicken Sie 
	auf das Importieren-Symbol <img border="0" src="${images}/import.gif" width="16" height="16">&nbsp;,
	um zum Import-Modul zu gelangen.
	<li>Datei importieren. Falls dies zu Fehlern führt, korrigieren Sie diese bitte,
	 bis Ihr Import erfolgreich ist. 
	<li>Gehen Sie zur Übersicht der importierten Überweisungen. Dies können Sie tun über 
	das Fenster &quot;Suchergebnisse&quot; (über dem Fenster mit der Schaltfläche &quot;
	Importieren&quot;), 	und klicken Sie auf das Anzeigen-Symbol <img border="0" src="${images}/preview.gif" 
	width="16" height="16">	&nbsp;.
	<li>Unvollständige Überweisungen entfernen oder wiederherstellen; Überweisungen &quot;Anstehende&quot; 
	verifizieren, und in den &quot;Überprüft&quot;-Status verschieben. All dies können Sie über das 
	Anzeigen-Symbol <img border="0" src="${images}/preview.gif" width="16" height="16">&nbsp;
	jeder Überweisung tun.
	<li>Verarbeiten Sie die überprüften Überweisungen mittels der Schaltfläche &quot;Ausführen&quot; 
	über dieser Überweisungsübersicht. 
</ol>
<hr>

<A NAME="external_accounts_overview"></A>
<h3>Übersicht externe Konten</h3>
Diese Seite listet alle externen, für das System konfigurierten Konten. Die 
Namensspalte zeigt den Namen des externen Kontos, und der Kontosaldo die Summe 
aller importierten Überweisungen. 
<ul>
	<li><img border="0" src="${images}/import.gif" width="16" height="16">
	Klicken Sie auf das Pfeil-Symbol um die Import-Funktion für das Konto einzugeben. 
	Dies ermöglicht Ihnen, externe Überweisungen aus einer Datei zu importieren. Ebenso 
	können Sie eine Übersicht aller vergangenen Importe anfordern. 
	<li><img border="0" src="${images}/preview.gif" width="16" height="16">
	Klicken Sie auf das Anzeigen-Symbol, um die bereits importierten Überweisungen zu 
	zeigen und zu verarbeiten. 
</ul>
<hr class="help">

<A NAME="external_transfer_import_new"></A>
<h3>Neue externe Überweisungen importieren</h3>
In diesem Fenster können Sie neue externe Überweisungen importieren. Wählen Sie 
einfach die Datei und klicken Sie auf &quot;Weiter&quot;. Falls die Datei aufgrund 
von Syntax-Fehlern nicht gelesen werden konnte, erscheint ein Fehlerbericht mit 
Angabe der Zeile und Spalte (Feld), in der der Fehler verursacht wurde. 

<hr class="help">

<A NAME="external_transfer_import_list"></A>
<h3>Externe Überweisung - Importlisten</h3>
In diesem Fenster können Sie Datei-Importe nach Zeitraum suchen. Um das Datum einzugeben, 
können Sie das Kalender-Symbol (<img border="0" src="${images}/calendar.gif" width="16" height="16">) 
benutzen.
<hr class="help">

<A NAME="external_transfer_import_result"></A>
<h3>Externe Überweisung - Importergebnis</h3>
Dieses Fenster zeigt eine Liste aller importierten Überweisungsdateien. 
<ul>
	<li><img border="0" src="${images}/preview.gif" width="16" height="16">
	Klicken Sie auf das Anzeigen-Symbol, um die importierten Überweisungen zu zeigen und zu 
	verarbeiten.<br>
	<li><img border="0" src="${images}/delete.gif" width="16" height="16">
	Klicken Sie auf das Löschen-Symbol, um die importierten Überweisungen wieder zu 
	entfernen.<br>
	Anmerkung: Sie können keine Importe löschen, die Überweisungen mit dem Status &quot;Geprüft&quot;
	oder &quot;Ausgeführt&quot; enthalten. 
</ul>
<hr class="help">

<A NAME="external_account_history"></A>
<h3>Externes Konto - Verlauf</h3>
In diesem Fenster können Sie nach importierten Überweisungen suchen. Die Funktion 
sucht in allen importierten Dateien. Eine Suche ohne Auswahl von Optionen wird für 
das betreffende externe Konto alle importierten Überweisungen zeigen. Die folgenden 
Suchoptionen sind verfügbar: 
<ul>
	<li><b>Typ: </b>
	Mit dieser Option können Sie nach Zahlungstyp suchen (in der Import-Konfiguration definiert). 
	<li><b>Status: </b>
	<ul>
		Eine importierte Überweisung kann die folgenden Stati haben: 
		<li><b>Anstehend: </b>
		Die Überweisung wurde importiert, aber noch nicht verifiziert. Der &quot;Importierte Saldo&quot; 
		(auf der Hauptseite der Buchhaltungsfunktion) wurde davon noch nicht beeinflusst, und 
		sie hat noch keine Aktionen generiert.<br>
		Anmerkung: Eine importierte Überweisung mit Status &quot;Anstehend&quot; kann auch 
		noch gelöscht werden. 
		<li><b>Geprüft: </b>
		Hier handelt es sich um bereits verifizierte Überweisungen mit dem 
		<a href="#external_transfers_history_result"><u>Status</u></a> &quot;Geprüft&quot;. 
		<li><b>Ausgeführt: </b>
		Dies sind Überweisungen, die nach ihrer Verschiebung in den Status &quot;Geprüft&quot; 
		bereits verarbeitet wurden. 
	</ul>
	<li><b>Benutzername / Name: </b>Suche nach Überweisungen durch spezifisches Mitglied. 
	<li><b>Betrag von/bis: </b> Suche über Betragspanne. 
	<li><b>Von Datum / Bis Datum:</b> Suche nach Zeitraum. 
</ul>
Unter dem Fenster finden Sie verschiedene Aktionen (mit drei Schaltflächen); diese sind, 
von links nach rechts: 
<ul>
	<li><b>Zur Importliste:</b> Hierüber gelangen Sie zur Übersicht der Import-Dateien, 
	wo sie auch neue Überweisungsdateien importieren können. 
	<li><b>Zahlungen ausführen: </b>
	Dies öffnet ein Fenster mit der Option, eine oder mehrere der Überweisungen zu verarbeiten.<br>
	Verarbeitung kann bestehen aus: 
	<ul>
		<li>abgleichen einer ausgehenden Zahlung (vom externen Konto). 
		<li>eine interne Cyclos-Zahlung in Verbindung mit einer eingehenden Zahlung 
		auf das Bankkonto zu generieren. 
		<li>eine Cyclos-Darlehenszahlung in Zusammenhang mit einer eingehenden Zahlung auf 
		das Bankkonto als erledigt abzuschließen. 
	</ul>
	Die Schaltfläche &quot;Ausführen&quot; gilt für alle Überweisungen in der Liste Suchergebnisse 
	(ganz gleich, ob sie gewählt wurden oder nicht). Bitte beachten Sie, dass eine Überweisung 
	nur dann verarbeitet werden kann, wenn ihr Status &quot;Geprüft&quot; ist. 
	<li><b>Neue Zahlung: </b>
	Wurde eine Überweisung nicht korrekt importiert, so kann sie auch manuell eingefügt werden.  
</ul>
Mehr Information zu diesen Funktionen finden Sie in der Hilfe des Fensters selbst.
<hr class="help">

<a name="status"></a>
<h3>Überweisungsstatus</h3>
Jede importierte Überweisung hat einen Status. Der Status kann die folgenden Werte haben: 
<ul>
	<li><b>Anstehend</b> <img border="0" src="${images}/pending.gif" width="16" height="16">:
	Die Überweisung wurde importiert, hat jedoch keinerlei Wirkung. Sie ist &quot;anstehend&quot; 
	und wartet auf weitere Aktionen.<br>
	Wurde der Überweisungstyp als &quot;Ist abgleichbar&quot; markiert, erscheint dieser Status 
	auch in der &quot;Übersicht Systemkonto&quot;.  Auf diese Art können Sie den Conciliation-Status 
	direkt auf der Übersichtsseite zurück verfolgen.<br>
	Nur Überweisungen mit dem Status &quot;Anstehend&quot; können gelöscht werden. 
	<li><b>Geprüft</b> <img border="0" src="${images}/checked.gif" width="16" height="16">:
	Die Überweisung wurde verifiziert und &quot;überprüft&quot;. Das bedeutet, dass Sie sich nun im 
	Saldo des externen Kontos niederschlägt.<br>
	Eine Überweisung mit dem Status &quot;Geprüft&quot; kann zurück in &quot;Anstehend&quot; verschoben 
	werden. 
	<li><b>Unvollständig</b> <img border="0" src="${images}/incomplete.gif" width="16" height="16">:
	Die Überweisung wurde importiert, eines oder mehrere Felder wurden jedoch nicht korrekt zugeordnet. 
	Dies kann z.B. geschehen, wenn eines der Mitglieder in der importierten Überweisung in Cyclos nicht 
	existiert.<br>
	<li><b>Abgeglichen</b> <img border="0" src="${images}/conciliated.gif" width="16" height="16">:
	Die Überweisung wurde verarbeitet. Dies bedeutet, dass sie Teil des Saldos des externen Kontos ist, 
	und in Cyclos eine Aktion verursacht (z.B. eine interne Cyclos-Zahlung oder Erledigung (discard) eines 
	Darlehens.<br>
	Wurde der Überweisungstyp als &quot;Ist abgleichbar&quot; markiert, erscheint dieser Status auch in 
	der &quot;Übersicht Systemkonto&quot;. Auf diese Art können Sie den Conciliation-Status direkt auf der 
	Übersichtsseite zurück verfolgen.<br>
	Ausgeführte Überweisungen können weder mit einem anderen Status versehen noch gelöscht werden. 
</ul>
<hr class="help">


<A NAME="external_transfers_history_result"></A>
<h3>Externe Überweisungen</h3>
Dieses Fenster zeigt das Ergebnis des Suchfensters oben. Als Standardeinstellung zeigt es alle 
importierten Überweisungen. Die Typen-Spalte zeigt vorn ein Status-Symbol. Um eine Übersicht der 
möglichen Werte im Status-Feld zu erhalten, <a href="#status"><u>klicken Sie bitte hier</u></a>. 
Die Betrags- und Datumsspalten erklären sich von selbst. 
<br><br>
Die folgenden Aktionen sind für jede Überweisung möglich. 
<ul>
	<li><img border="0" src="${images}/preview.gif" width="16" height="16">
	Zugang zu abgeglichenen und verarbeiteten Zahlungen erhalten Sie durch Auswahl des 
	Vorschau-Symbols. Diese Zahlungen können nicht verändert werden. Es ist allerdings 
	möglich, eine überprüfte Überweisung zurück in den Status &quot;Anstehend&quot; zu 
	verschieben. 
	<li><img border="0" src="${images}/edit.gif" width="16" height="16">
	Ändern Sie die Überweisungsdaten einer Überweisung mit Status &quot;Anstehend&quot; 
	durch Anklicken des Bearbeiten-Symbols.<br>
	<li><img border="0" src="${images}/delete.gif" width="16" height="16">
	Klicken des Löschen-Symbols löscht die Überweisung.<br>
</ul>
<hr class="help">


<A NAME="external_transfers_history_summary"></A>
<h3>Externe Überweisungen - Zusammenfassung</h3>
Dieses Fenster zeigt eine Übersicht und Berichte aller aufgelisteten Überweisungen. 
<br><br>Anmerkung: Nur diejenigen Überweisungen, die das Ergebnis der Suche im Fenster 
oben sind, werden gezählt. Eine Suche ohne Auswahl von Optionen wird für das betreffende 
externe Konto alle importierten Überweisungen zeigen.

<hr class="help">

<A NAME="new_external_transfer"></A>
<h3>Neue externe Überweisung</h3>
Es ist möglich, eine Überweisung manuell hinzuzufügen. Dies sollte normalerweise 
nicht notwendig sein, da der Überweisungsimport entsprechend konfiguriert werden 
kann, alle Überweisungen korrekt zu importieren. In den (seltenen) Fällen, in denen 
es trotzdem notwendig sein kann, können Sie es hier im Fenster tun.<br>  
Die Eingabefelder erklären sich von selbst.

<hr class="help">

<A NAME="edit_external_transfer"></A>
<h3>Externe Überweisung anzeigen und ändern</h3>
Auf dieser Seite können Sie die Details der importierten Überweisungen zeigen. Hat 
die Überweisung den Status &quot;Anstehend&quot;, können Sie ihre Eigenschaften ändern.<br>
Die Eingabefelder erklären sich von selbst. 

<hr class="help">

<A NAME="external_transfer_process"></A>
<h3>Externe Überweisung - Verarbeitung</h3>
Auf dieser Seite können Sie Zahlungen verarbeiten. Bitte beachten Sie, dass eine Zahlung 
den Status <a href="#status"><u>&quot;Geprüft&quot;</u></a> haben muss, andernfalls 
erscheint sie nicht in der Liste der Überweisungen, die bereit zur Bearbeitung sind. 
<br><br>
Das Fenster zeigt eine Übersicht der verarbeitbaren Zahlungen. In jedem Element zeigt 
die obere Zeile die aus der Überweisungsdatei gelesene Originalzeile, wohingegen die 
untere Zeile die Überweisung nach Anklicken von &quot;Weiter&quot; und der Verarbeitung 
zeigt. Wählen Sie die Überweisungen, die Sie verarbeiten möchten, durch Markieren der 
Kontrollkästchen in der ersten Spalte. Dies ermöglicht Ihnen, das Datum oder den Betrag 
zu ändern, falls Ihnen diese nicht richtig erscheinen.<br>
Anklicken von &quot;Weiter&quot; verarbeitet die gewählten Überweisungen.
<br><br>
Es gibt drei Typen von Verarbeitung.
<ul>
	<li><b>Ausgleichs-Zahlung: </b>
	Diese Option ist üblich für Systeme, in denen interne (Cyclos-)Einheiten extern gedeckt werden 
	(z.B. durch konventionelles Geld auf einem Bankkonto). Conciliating einer Reihe von Überweisungen 
	(externe Überweisung und Cyclos-Überweisung) bedeutet, dass beide Überweisungen verbunden sind, 
	da sie verwalterisch zusammen gehören. Dies erscheint im <img border="0" src="${images}/conciliated.gif"
		width="16" height="16"> vor der Zahlung, in der Kontoübersicht der externen Kontofunktion und 
	der Funktion &quot;Übersicht Systemkonto&quot;. In den Suchfenstern dieser Funktionen gibt es die 
	Option, über den Abgleichs-Status zu suchen. Um Überweisungen mit dem Überweisungstyp in Cyclos 
	Conciliate zu können, der muss die Option &quot;Ist abgleichbar&quot; in der Konfiguration des 
	Überweisungstyps markiert sein.<br>
	<li><b>Zahlung generieren: </b><br>
	Eine eingehende (externe) Zahlung (nur positiver Betrag) kann so konfiguriert werden, dass sie 
	eine Cyclos-Zahlung generiert (System an Mitglied oder anders herum). Um eine Zahlung zu verarbeiten, 
	muss sie gewählt werden. Haben Sie die Überweisung gewählt, ist es möglich, Betrag und Datum zu ändern. 
	Dies wird allerdings nur selten benötigt.<br>
	Eine generierte Zahlung hat automatisch den Status &quot;Abgeglichen&quot;. 
	<li><b>Darlehensrückzahlung erledigen: </b><br>
	Ein Darlehen in Cyclos erhält den administrativen Status &quot;Bezahlt&quot;, wenn es zurück gezahlt 
	wurde. In Systemen, in denen Cyclos-Darlehen auch extern zurück gezahlt werden können, erhalten 
	diese Darlehen einen Status, der anzeigt, dass Sie nicht mehr offen sind.  Dieser Status wird 
	&quot;Erledigt&quot; genannt. Eine extern eingehende Zahlung kann eine Zahlung &quot;Darlehen 
	erledigen&quot; generieren.<br>
	In der Konfiguration für externe Konten gibt es dafür einen spezifischen Typ. Wird eine solche externe 
	Überweisung verarbeitet, sucht Cyclos nach offenen Darlehenszahlungen mit gleichem Betrag und Datum 
	und zeigt Übereinstimmungen. Gibt es mehrere übereinstimmende Darlehenszahlungen, werden alle angezeigt, 
	und der Administrator wählt das Richtige aus. Nach einer generierten Zahlung &quot;Darlehen erledigen&quot; 
	ist der Status &quot;Abgeglichen&quot; in der Übersicht &quot;Externe Konten&quot;. 
</ul>
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
