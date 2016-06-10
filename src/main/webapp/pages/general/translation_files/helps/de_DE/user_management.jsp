<div style="page-break-after: always;">
<span class="admin">
<br><br>
Diese Seiten enthalten die Funktionen zur Benutzerverwaltung. Sie können benutzt werden, um nach 
Mitgliedern zu suchen, neue Mitglieder zu registrieren, eine Übersicht über die derzeit angemeldeten 
Benutzer zu erhalten, oder andere Informationen zum Nutzerstatus zu zeigen.

<i>Wo ist es zu finden?</i><br> 
Im Folgenden sehen Sie eine Liste der verfügbaren Funktionalitäten, und wo sie zu finden sind.
<ul>
	<li><b>Suchen und Einrichten von Mitgliedern:</b> kann über das  &quot;Menü: Benutzer und Gruppen > 
	Mitglieder verwalten&quot; erfolgen. Dies beinhaltet auch die Suche nach Brokern. 
	<li><b>Suchen und Einrichten von Administratoren:</b> kann über das  &quot;Menü: Benutzer und Gruppen >
	Administratoren verwalten&quot; erfolgen.
	<li><b><a href="#connected"><u>Angemeldete Benutzer:</u></a></b> finden Sie über das
	&quot;Menü: Benutzer und Gruppen > Angemeldete Benutzer&quot;.
	<li><b><a href="#bulk_actions"><u>Massenaktionen</u></a>:</b> finden Sie über das
	&quot;Menü: Benutzer und Gruppen > Massenaktionen&quot;.
</ul>
</span>


<span class="broker">
<ul>
	<li><b>Einrichten / Registrieren von Mitgliedern:</b> Vorausgesetzt Sie haben die dazu 
	notwendigen Berechtigungen, finden Sie dies über &quot;Menü: Brokering > Mitglieder registrieren&quot;.
</ul>
</span>

<span class="member">
<ul>
	<li><b>Mitglieder suchen:</b> Nach Mitgliedern suchen können Sie über &quot;Menü: Suche >
	Mitglieder&quot;.
</ul>
</span>
<hr>

<span class="admin">
<A NAME="create_user"></A>
<h3>Benutzer einrichten</h3>
Hier können Sie Information über das neue Mitglied eingeben. Das Fenster ist recht einfach 
verständlich; welche Felder in diesem Formular erscheinen hängt von Ihrer Konfiguration
ab.<br>
Das Mitglied ist Teil der im ersten Feld aufgelisteten  <a href="${pagePrefix}groups">
<u> Gruppe</u></a>. 
<br><br>
Gibt es eine Brokergruppe, die die Brokerberechtigungen für die Gruppe hat, deren Mitglied 
Sie gerade einrichten, haben Sie die Option, das Mitglied einem Broker dieser Brokergruppe 
zuzuordnen. Dafür füllen Sie bitte das Kontrollkästchen &quot;Broker zuordnen&quot; unten 
aus.<br>
Sie können für den Benutzer ein Kennwort spezifizieren, und festlegen, ob der Benutzer das 
Kennwort direkt weiter verwenden kann, oder ob er es bei der nächsten Anmeldung ändern muss.  
<br><br>
Nach Eingabe der Daten haben Sie die Option, das Mitglied zu speichern und ein neues Mitglied 
einzurichten (Schaltfläche &quot;Speichern und weiteres Mitglied anlegen&quot;), oder aber direkt 
das <a href="${pagePrefix}profiles"><u>Profil</u></a> des neuen Mitglieds zu öffnen (Schaltfläche 
&quot;Speichern und Profil öffnen&quot;). 

<hr class="help">
</span>

<span class="broker"> <a name="create_user_for_broker"></a>
<h3>Brokering - Neues Mitglied einrichten</h3>
Hier können Sie Information über das neue Mitglied eingeben. Das Fenster ist recht 
einfach verständlich; welche Felder in diesem Formular erscheinen hängt ab von Ihrer 
Konfiguration.<br>
Nachdem Sie die Registrierung fertig gestellt haben, wird das Mitglied automatisch als 
ein von Ihnen gebrokertes Mitglied registriert werden.<br>
Sie können für den Benutzer ein Kennwort spezifizieren, und festlegen, ob der Benutzer 
das Kennwort direkt weiter verwenden kann, oder ob er es bei der nächsten Anmeldung ändern 
muss.  
<br><br>
Nach Eingabe der Daten haben Sie die Option, das Mitglied zu speichern oder ein neues Mitglied 
einzurichten  (Schaltfläche &quot;Speichern und weiteres Mitglied anlegen&quot;), oder aber 
direkt das <a href="${pagePrefix}profiles"><u>Profil</u></a> des neuen Mitglieds zu öffnen 
(Schaltfläche &quot;Speichern und Profil öffnen&quot;). 
<br><br>
Für die Registrierung eines neuen Mitglieds erhalten Sie möglicherweise eine  <a
	href="${pagePrefix}brokering#commission"><u>Kommission</u></a>, je nach den Regeln Ihres 
Systems. Die Kommission ist abhängig vom Umsatz des von Ihnen registrierten 
Mitglieds.<br>
Über &quot;Menü: Brokering > Mitglieder&quot; können Sie die von Ihnen registrierten Mitglieder 
verwalten, und deren Tätigkeiten verfolgen. 
<hr class="help">
</span>


<span class="member">
<A NAME="search_member_by_member"></A>
<h3>Suche nach Mitgliedern</h3>
In diesem Fenster können Sie nach Mitgliedern suchen. Durchsucht werden alle Profilfelder 
der Mitglieder. Sie können bei der Suche mehr als ein Suchwort verwenden.<br>
Verschiedene &quot;Steuerzeichen&quot; können bei der Mitglieder- und Inseratesuche verwendet 
werden. Die am häufigsten Verwendeten sind: 
<ul>
	<li><b>*</b> Der "Platzhalter" für beliebige Zeichenfolgen möglicht Ihnen, 
	nach Teilen von	Wörtern zu suchen. Eine Suche nach ba*, zum Beispiel, zeigt alle Benutzer, 
	deren Profil die Zeichenkombination ba auf einem ihrer Felder beinhalten. Zum Beispiel: Barbara, 
	Batman, Backhausstraße (letzteres wäre ein Adressfeld).
	<li><b>- not</b> Eine Suche, bei der dem Schlüsselwort eine Minuszeichen oder das Wort 
	&quot;not&quot; und eine Leerstelle voraus geht, resultiert in allem, was dieses 
	Schlüsselwort nicht	enthält.
	<li><b>or</b> Die Suche soll  Ergebnisse erbringen, die das dem &quot;or&quot; 
	voranstehende oder nachstehende Schlüsselwort enthalten.
	<li><b>and</b> Die Suche soll  Ergebnisse erbringen, die das dem &quot;and&quot; 
	voranstehende und nachstehende Schlüsselwort enthalten.
</ul>

<hr class="help">
</span>

<span class="member"> 
<A NAME="search_member_result"></A>
<h3>Suchergebnisse (für Mitgliedssuche)</h3>
Dieses Fenster zeigt das Ergebnis der Mitgliedssuche. Wählen des &quot;Benutzernames&quot; 
oder des &quot;Namens&quot; öffnet das <a href="${pagePrefix}profiles"><u>
Profil</u></a> des betreffenden Mitglieds.
Auswahl des Bildsymbols öffnet das Bild oder die Bilder in einem Popup-Fenster. 
<hr class="help">
</span>

<span class="admin"> 
<A NAME="search_member_by_admin"></A>
<h3>Suche nach Mitgliedern</h3>
Auf dieser Seite (Menü: Benutzer & Gruppen > Mitglieder verwalten) können Sie Mitglieder 
suchen und neue Mitglieder registrieren. 
<br><br>
Wenn Sie nach einem Mitglied suchen möchten, können Sie verschiedene Suchfelder ausfüllen 
(keines davon ist Pflicht). Anklicken der Schaltfläche &quot;Suche&quot; ohne etwas in eines der 
Felder einzugeben, zeigt eine Liste aller Mitglieder.
How the members are ordered can be set via &quot;Menu: Settings > Local settings&quot;.<br>
Wie die Mitglieder sortiert werden, kann in &quot;Menü: Einstellungen > Basiseinstellungen&quot; gewählt werden.<br>
<ul>
	<li><b>Suchbegriff:</b> Hier können Sie ein Schlagwort eingeben, wie Benutzername, Name, Ort, usw.
	<li><b>Gruppenfilter:</b> Hier können Sie einen <a href="${pagePrefix}groups#group_filters"><u>
	Gruppenfilter</u></a> spezifizieren. (ist nur sichtbar, wenn Gruppenfilter definiert wurden) 
	<li><b><a href="${pagePrefix}groups"><u>Gruppe:</u></a></b> Hier können Sie nach Gruppen filtern.
	<li><b>Broker-Benutzername / Broker:</b> Geben Sie hier den Benutzernamen oder den Namen des 
	Brokers ein, und Ihre Ergebnisseite zeigt alle Mitglieder, die zu diesem Broker gehören. 
	<li><b>Aktivierung von / bis:</b> Mit diesen Datumsfeldern können Sie ein Mitglied über das 
	Datum suchen, an dem es Mitglied Ihrer Organisation wurde. Klicken Sie auf das Kalender-Symbol 
  <img border="0" src="${images}/calendar.gif" width="16" height="16">&nbsp;, 
	um das Datumsauswahlfeld zu benutzen.
</ul>
Um eine neues Mitglied zu registrieren, müssen Sie das Auswahlfeld unten links im Formular 
verwenden. Wählen Sie hier die Gruppe, in der Sie das Mitglied einrichten möchten, und Sie 
gelangen zum  <a href="#create_user"><u>Registrierungsformular</u></a>. 
<hr class="help">
</span>
 
<span class="admin">
<A NAME="admin_search_member_result"></A>
<h3>Suchergebnisse (für Mitgliedssuche)</h3>
Dieses Fenster zeigt das Ergebnis der Mitgliedssuche. Anklicken des Benutzernamens 
oder des Mitgliedsnamens bringt Sie zum <a href="${pagePrefix}profiles"><u>Profil</u></a> 
dieses Mitglieds. Auswahl der Schaltfläche &quot;Zurück&quot; auf dieser Profilseite 
bringt Sie schnell zurück zum Suchergebnis. 
<br><br>
Sie haben die Option, das Suchresultat auszudrucken, durch Anklicken des Drucken-Symbols  
<img border="0" src="${images}/print.gif" width="16" height="16">&nbsp;
oben rechts im Fenster, neben dem Hilfe-Symbol. Dadurch gelangen Sie zu einem 
druckbaren Formular, wo Sie durch Klicken der Schaltfläche eine Übersicht drucken können.
<br><br>Eine weitere Möglichkeit ist Herunterladen des Ergebnisses in eine Datei, durch Anklicken 
des Speichern-Symbols <img border="0" src="${images}/save.gif" width="16" height="16">&nbsp;.
Das Ergebnis wird im CSV-Format, heruntergeladen, 
das alle Felder des Mitglieds-<a href="${pagePrefix}profiles"><u>Profil</u></a> beinhaltet 
(also auch mehrere Felder, die in diesem Ergebnis-Fenster nicht sichtbar sind).<br>
In den <a href="${pagePrefix}settings#local"><u>Basiseinstellungen</u></a> können Sie 
spezifizieren, ob Sie die Feldnamen in den Überschriften (erste Zeilen) der Rubriken zeigen 
wollen.
<br><br>Anmerkung: In der Berichte-Funktion können Sie spezifischere 
<a href="${pagePrefix}reports#member_lists"><u>Mitgliedslisten</u></a> erstellen.
Über die Berichte-Funktion finden Sie zum Beispiel Mitgliedslisten mit zusätzlichen 
Daten wie Kontosaldo und Anzahl der Inserate. 
<hr class="help">
</span>


<span class="broker admin"> <a name="search_pending_member"></a>
<h3>Interessenten suchen</h3>
Interessenten sind Mitglieder, die zwar registriert sind, ihre Registrierung aber noch 
nicht ihre Antwort auf eine E-Mail-Nachricht bestätigt haben. Erst nach dieser 
Bestätigung kann das Mitglied sich anmelden.<br>
Nach einer bestimmten Zeit wird der Interessent automatisch aus dem System (und der Liste) 
entfernt. Alle drei Registrierungsoptionen (Selbst-Registrierung auf der öffentlichen 
Seite, Registrierung durch Broker und durch Mitglied) können eine E-Mail-Bestätigung 
erfordern.<br> 
Anmerkung: Ein Mitglied kann sich nicht mit einer E-Mail registrieren, die im System bereits 
verwendet ist (entweder von einem anderen Interessenten oder aktiven Mitglieder).
<br><br>
Auf dieser Seite können Sie nach Interessenten suchen. Sie können sowohl über den Namen, 
die Gruppe oder den Registrierungs-Zeitraum suchen.<br>
Die Suche nach einer Gruppe bedeutet nicht, dass die Mitglieder zu dieser Gruppe gehören, 
sondern gibt die Gruppe an, zu der die Mitglieder nach ihrer E-Mail-Validierung gehören 
werden.<br>
</span>
<span class="admin"> Sie können aber auch über Broker suchen. Das bedeutet, dass die Suche
 nur die Mitglieder zeigt, die durch den gewählten Broker registriert wurden. Bitte beachten 
 Sie, dass dies nur dann funktioniert, wenn die durch einen Broker registrierten Mitglieder 
 auch eine Bestätigung schicken müssen. Dies ist eine optionale 
 <a href="${pagePrefix}groups#group_registration_settings"><u>Gruppeneinstellung</u></a>. 
 Der maximale Registrierungs-Zeitraum ist in den Basiseinstellungen definiert.<br>
<hr class="help">
</span>

<span class="broker admin"> <a name="search_pending_member_result"></a>
<h3>Interessentenliste</h3>
Dieses Fenster zeigt das Ergebnis der Mitgliedssuche. Denken Sie bitte daran, dass 
diese Mitglieder noch keine &quot;aktiven&quot; Mitglieder sind (sie können sich noch 
nicht anmelden und sind im System noch nicht sichtbar). Nur in ganz seltenen Fällen 
kann es vorkommen, dass Sie ein Mitglied von dieser Liste entfernen möchten. Einen 
Interessenten zu löschen bedeutet, dass das Mitglied seine Registrierung nicht 
bestätigen kann.<br>
Wenn Sie das Mitglied bearbeiten, können Sie die Profildaten zeigen und ändern, und 
falls notwendig, die Validierung noch einmal senden. 

<hr class="help">
</span>

<span class="broker admin"> <a name="pending_member"></a>
<h3>Interessenten-Details</h3>
Auf dieser Seite können Sie die Profildaten zeigen und ändern, und falls notwendig, 
die E-Mail-Validierung noch einmal senden. 

<hr class="help">
</span>

<span class="admin">
<a name="search_admin"></a>
<h3>Suche nach Administratoren</h3>
Auf der Seite (Menu: Benutzer und Gruppen > Administratoren verwalten)  können Sie 
nach Administratoren suchen und neue Administratoren registrieren. 
<br><br>
Das Formular ist recht einfach: Sie können einfach einen Suchbegriff 
eingeben, und/oder die Suche auf eine spezifische Administratoren-Gruppe beschränken.  
Anklicken der Schaltfläche &quot;Suche&quot; ohne etwas in eines der Felder einzugeben, 
zeigt eine Liste aller Administratoren.<br>
<br>
Um einen neuen Administrator zu registrieren, verwenden Sie bitte das Auswahlfeld unten 
im Formular. Hier wählen Sie die <a href="${pagePrefix}groups#admin_groups"><u>Gruppe</u></a> 
in der Sie den Administrator einrichten wollen, und gelangen dadurch zum 
<a href="#create_user"><u>Registrierungsformular</u></a> für neue Administratoren.
</span>

<span class="admin">
<a name="search_admin_result"></a>
<h3>Suchergebnisse (für Administratorensuche)</h3>
Dieses Fenster zeigt die Ergebnisse Ihre Suche nach Administratoren. Um weitere 
Details über diesen Administrator zu erfahren, klicken Sie auf seinen Benutzernamen, 
oder auf seinen Namen. 

<hr class="help">
</span>

<span class="admin">
<a name="create_admin"></a>
<h3>Neuen Administrator registrieren</h3>
Hier können Sie einen neuen Administrator registrieren. Wir empfehlen 
<b>ganz entschieden</b> alle Personen, die als Administratoren fungieren, mit 
einem eigenen Konto und Anmeldenamen auszustatten, so dass nicht mehrere Personen 
ein einzelnes Administratoren-Konto teilen. Dies macht es sehr viel leichter, 
Berechtigungen zu vergeben, mögliche Fehler zurück zu verfolgen, oder Konten zu 
schließen, wenn Personen die Organisation verlassen. 
<br>
Das Formular ist recht einfach, und erklärt sich von selbst. Jedes mit einem roten 
Sternchen (*) versehene Feld ist ein Pflichtfeld. 
<br><br>
Nach Eingabe der Daten haben Sie die Option, den Administrator zu speichern, und einen  
weitern Administrator einzufügen (Schaltfläche &quot;Speichern und weiteren Administrator 
anlegen&quot;), oder aber direkt das <a href="${pagePrefix}profiles"><u>Profil</u></a> 
des neuen Administrators zu öffnen (Schaltfläche &quot;Speichern und Profil öffnen&quot;). 

<hr class="help">
</span>

<span class="admin">
<hr>
<a name="connected"></a>
<h2>Angemeldete Benutzer</h2>
Auf dieser Seite (Menü: Benutzer und Gruppen > Angemeldete Benutzer) können Sie eine 
Übersicht erlangen, welche Benutzer (Mitglieder, Administratoren, Broker) derzeit 
angemeldet sind, und das System nutzen. 

</span>

<span class="admin">
<A NAME="connected_users"></A>
<h3>Angemeldete Benutzer</h3>
Auf dieser Seite können Sie spezifizieren, welche <a href="${pagePrefix}groups#group_categories">
<u>Typen</u></a> von angemeldeten Benutzern Sie im Fenster unten anschauen möchten.
Das Auswahlfeld &quot;Anzeigen&quot; ermöglicht Ihnen die Auswahl zwischen &quot;Administratoren&quot;, 
&quot;Brokern&quot;, &quot;Mitgliedern&quot; und 
&quot;<a href="${pagePrefix}operators"><u>Operatoren</u></a>&quot;.<br>
Klicken Sie auf &quot;Weiter&quot; um die Ergebnisse zu zeigen. .
<hr class="help">
</span>
 
<A NAME="connected_users_result"></A>
<span class="admin">
<h3>Suchergebnisse angemeldete Benutzer</h3>
Diese Funktion zeigt eine Liste der angemeldeten Benutzer, je nach der von Ihnen 
im Fenster oben getroffenen Auswahl.<br>
In der Liste können Sie das Mitglied anklicken, um das Profil zu öffnen. Administratoren 
mit entsprechender Berechtigung können ein Mitglied durch Anklicken des 
Löschen-Symbols (<img border="0" src="${images}/delete.gif" width="16" height="16">&nbsp;) 
in der letzten Spalte abmelden. 
</span>
<span class="member">
<h3>Suchergebnisse angemeldete Operatoren</h3>
Diese Funktion zeigt eine Liste der derzeit angemeldeten  <a href="${pagePrefix}operators"><u>
Operatoren</u></a>.<br>
In der Liste können Sie den Operator anklicken, um das Profil zu öffnen. Wenn Sie die 
entsprechenden Berechtigungen haben,  können Sie einen Operator durch Anklicken des 
Löschen-Symbols (<img border="0" src="${images}/delete.gif" width="16" height="16">&nbsp;)
in der letzten Spalte abmelden.</span>

<span class="admin">
<hr>
<a name="bulk_actions"></a>
<h2>Massenaktionen</h2>
Die Funktion Massenaktionen (Menü: Benutzer und Gruppen > Massenaktionen) ermöglicht es dem 
Administrator, Aktionen für gesamte Benutzer-Gruppen durchzuführen. 


</span>

<span class="admin">
<A NAME="bulk_actions_filter"></A>
<h3>Massenaktions-Filter</h3>
In diesem Fenster kann ein Administrator die Benutzergruppen spezifizieren, für welche die 
<a href="#bulk_actions"><u>Massenaktion</u></a> ausgeführt wird. Die Felder sind mit einer 
logischen UND-Suche kombiniert, achten Sie also bitte darauf, nicht zuviel zu spezifizieren, 
da Sie sonst womöglich ein &quot;leeres&quot; Ergebnis erhalten. 
<ul>
	<li><b>Gruppenfilter:</b> Hier können Sie einen <a href="${pagePrefix}groups#group_filters"><u>
	Gruppenfilter</u></a> spezifizieren.
	<li><b>Gruppe:</b> Hier können Sie die <a href="${pagePrefix}groups"><u>
	Gruppe</u></a> spezifizieren. Achten Sie bitte darauf, dass dies dem oben spezifizierten 
	Gruppenfilter nicht widerspricht. Normalerweise spezifizieren Sie entweder einen Gruppenfilter 
	oder eine Gruppe, aber nicht beides.
	<li><b>Broker-Benutzername / Broker:</b> falls Sie möchten, dass diese Aktion alle Mitglieder 
	eines Brokers betrifft, spezifizieren Sie den Broker hier entweder durch seinen Namen oder 
	seinen Benutzernamen. 
	<li><b>...:</b> Der Rest des Formulars listet einige für Ihre Mitgliedsprofile  
	<a href="${pagePrefix}custom_fields"><u>benutzerdefinierten Felder</u></a> auf. 
</ul>
Haben Sie erst einmal die Kriterien für die Massenaktion spezifiziert, können Sie durch Anklicken 
der Schaltfläche &quot;Vorschau&quot; unten rechts im Formular zeigen, welche Mitglieder betroffen 
sind. Dies ergibt eine Liste der beinhalteten Mitglieder. 

<hr class="help">
</span>

<span class="admin"> <A NAME="bulk_action"></A>
<h3>Aktion</h3>
Hier spezifizieren Sie die durchzuführende  <a href="#bulk_actions"><u>Massenaktion</u></a>. 
Sie haben die folgenden Möglichkeiten: 
<ul>
	<li><b>Gruppe ändern:</b> Dies ändert die Gruppe für die Auswahl.<br>
	Sobald Sie dies ausgewählt haben, werden Sie gebeten, die neue  <a
		href="${pagePrefix}groups"><u>Gruppe</u></a>, und einen Kommentar einzugeben. 
	  Klicken von &quot;Weiter&quot; verschiebt nun alle betroffenen Mitglieder in die neue 
	  Gruppe. 
	<li><b>Broker ändern:</b> Dies ändert den <a href="${pagePrefix}brokering"><u>Broker</u></a> 
		für die Auswahl.<br>
		Sobald Sie dies ausgewählt haben, werden Sie gebeten, den Benutzernamen oder den Namen 
		des Brokers einzugeben (wenn Sie den einen eingeben, vervollständigt sich der andere von 
		selbst).<br>
		Ausfüllen des Kontrollkästchens &quot;Kommission aufheben&quot; verwirft alle laufenden 
		und offenen <a href="${pagePrefix}brokering#commission"><u>Kommissionszahlungen</u></a>. 
		Dieses Kontrollkästchen möchten Sie möglicherweise verwenden, wenn Sie der Meinung sind dass 
		der neu zugeordnete Broker kein Anrecht hatte, Kommissionen auf die Tätigkeit des vorherigen 
		Brokers zu erhalten.<br>
		Hier sollten Sie auch einen Kommentar abgeben. Klicken von &quot;Weiter&quot; ordnet nun alle 
		betroffenen Mitglieder diesem Broker zu.
		<li><b>Karte generieren:</b> Diese Option generiert eine Karte für das 
		Mitglied. Damit Karten eingerichtet werden können, muss ein <a
		href="${pagePrefix}access_devices#edit_card_type"><u>Kartentyp</u></a>
		existieren und die Mitglieder müssen zu einer Gruppe gehören, für die
		unter &quot;Zugangseinstellungen&quot; im Formular <a href="${pagePrefix}groups#edit_member_group">
		<u>Gruppeneinstellungen</u></a> ein Kartentyp gewählt wurde.<br>
		Ex ist möglich, für Mitglieder, die bereits eine Karte haben, einen Kartentyp
		&quot;nachzumachen&quot;. Dies können Sie tun, indem Sie eine oder beide Kontrollkästchen
		markieren. Sie können wählen, ob Sie Karten für Mitglieder mit einem Erwartungstatus oder im aktiven 
		Status nachmachen. In beiden Fällen wird die bereits existierende Karte 
		ungültig.
		<li><b>Kommunikationswege freigeben/sperren:</b> Mit dieser Option können Sie für die ausgewählte Gruppe
		Kommunikationswege freigeben oder sperren.<br>	
		</ul>
<hr class="help">

<A NAME="import_members"></A>
<h3>Mitglieder importieren</h3>
Mit dieser Funktion ist es möglich Mitglieder zu importieren (deren Profil-Informationen). 
Optional kann auch ein Startguthaben (Saldo) von oder zum Mitgliederkonto generiert 
werden. Das Überweisung des Startguthabens kann durch Auswahl eines Kontotyps und einem 
Überweisungstyp für positive und negative Salden festgelegt werden. Ist der Betrag 
in der CSV-Datei positiv, muss der Überweisungstyp unter 
'Überweisungstyp für positiven Saldo' gewählt werden, die Überweisung erfolgt dann vom
Systemkonto an das Mitgliedskonto. Wenn der Betrag negativ ist, muss der Überweisungstyp
unter 'Überweisungstyp für negativen Saldo' gewählt werden, die Überweisung erfolgt dann vom
Mitgliedskonto an das Systemkonto.<br><br> 

Für den Import der Mitglieder wird eine CSV-Datei benötigt:
<ul>
    <li>Die erste Zeile der Datei enthält alle Namen der Felder, die für den
    Import benötigt werden. Die Liste unterhalb zeigt alle möglichen Felder für den Import.
    Bei den Spaltennamen muss auf Gross-und Kleinschreibung geachtet werden, 
    sie können in beliebiger Reihenfolge angegeben werden.
    <li>Die weiteren Zeilen enthalten die Benutzerinformationen, jede Zeile
    repräsentiert einen Benutzer.
    <li>Alle Zeilen benötigen die selbe Anzahl von Feldern (die selbe Anzahl von 
    Trennzeichen), optionale Felder können auch leer gelassen werden.
</ul>

Stellen Sie sicher, dass die Werte der Felder mit den Gültigkeitskriterien von Cyclos
übereinstimmen. Zum Beispiel müssen benutzerdefinierte Felder eventuell eindeutig sein oder
müssen eine bestimmte Anzahl Zeichen habe, die vom Systemadminstrator vorgebeben wurden. 
Zahlen und Datum müssen nach den Definitionen in Cyclos formatiert werden.<br><br>

Folgende Felder können importiert werden:
<ul>
	<li><b>name</b> (erforderlich): Der Mitgliedsname.
	<li><b>username</b> (erforderlich): Dies ist der Benutzername, er muss eindeutig sein, es kommt zu einem Fehler, wenn der Benutzername
	bereits im System vorhanden ist. Wenn der Benutzername automatisch generiert wird (Kontonummer) 
	wird diese Spalte nicht benötigt, Cyclos generiert dann diese Benutzernamen.
	<li><b>creationdate</b> (optional):	Das Erstellungsdatum des Mitglieds. Dieses erscheint im persönlichen Mitgliedsbericht (Mitglied seit). 
	Wenn kein Datum angegeben wird, wird das aktuelle Datum des Imports eingetragen. 
	<li><b>password</b> (optional): Das Kennwort. Die Mitglieder werden aufgefordert das Kennwort bei der ersten Anmeldung zu ändern.
	<li>email (erforderlich oder optional je nach Cyclos Einstellungen).<br>Es ist ein gültiges
	Format für eine E-Mail Adresse erforderlich.
	<li><b>balance</b> (optional): Der Anfangssaldo des Kontos. Wird nur verwendet, wenn ein Kontotyp ausgewählt ist. Falls Sie einen
	Kontotyp auswählen, können Sie die Überweisungstypen &quot;Mitglied an System&quot; für negative 
	Salden und &quot;System an Mitglied&quot; für positive Salden angeben.<br>
	Wenn Sie diese Option verwenden, müssen Sie sicherstellen, dass das Konto ausreichend Kreditlimit
	hat um es zu belasten.
	<li><b>creditlimit</b> (optional): Das Kreditlimit der Konten (negativ). Bei leerem Eintrag wird 
	das Standardlimit des Kontos der Gruppeneinstellungen verwendet.
	<li><b>uppercreditlimit</b> (optional):	Das obere Kreditlimit der Konten. Bei leerem Eintrag wird 
	das Standardlimit des Kontos der Gruppeneinstellungen verwendet.
	<li><b>benutzerdefiniertes Feld interner Name</b> (optional):	Der interne Name des benutzerdefinierten 
	Feldes bezogen auf die ausgewählte Gruppe. Die erforderliche Gültigkeit wird geprüft. 
	Ist das Feld eine Liste (aufzählend) ist der Import nur erfolgreich
	wenn ein gültiger Wert vorhanden ist. Als Beispiel, bei einem benutzerdefinierten Feld 
	&quot;area&quot; mit drei möglichen Werten &quot;Zentrum&quot;
	&quot;Süd&quot; und &quot;Nord&quot; sind nur diese Werte gültig, andere Werte werden nicht importiert.
	Mitglieder mit leeren Werten werden importiert (nur wenn das Feld &quot;erforderlich&quot; nicht markiert ist).
	<li><b>Mitgliedseintrag.benutzerdefiniertes Feld interner Name</b> (optional): Werte für Mitgliedseinträge. 
	Als Beispiel für das Standard-Projekt (Deutsch) ist &quot;Bemerkung.Kommentare&quot;. 
	Kommentare ist ein Feld vom Eintrag Bemerkung. Diese Angabe ist der interne Name des 
	Feldes(spezifiziert	in der Konfiguration des benutzerdefinierten Feldes).<br>
	Zu beachten ist, wenn Sie Mitgliedseinträge	importieren wollen, dass alle Felder die 
	in diesem Mitgliedseintrag	definiert sind eine eigene Spalte	in der CSV-Datei benötigen.
	</ul>
Ein Beispiel für eine CSV-Datei (hier nur mit den erforderlichen Feldern und Kennwort):<br>
<i>
name,username,email,password <br>
Paul McCartney,Paul,Paul@McCartney.com,1234 <br>
John Lennon,John,John@Lennon.com,1234 <br>
</i><br>
Ein anderes Beispiel einer CSV-Datei (hier mehrere Felder die importiert werden, ein benutzerdefiniertes
Feld 'area' und ein benuderdefinieter Kommentar eines Mitliedseintrages von Typ Bemerkung):<br>
<i>
name,username,email,password,creationdate,balance,creditlimit,uppercreditlimit,area,remark.comments <br>
Paul McCartney,Paul,Paul@McCartney.com,1234,25/08/2000,500,200,,Example area,calls a lot <br>
John Lennon,John,John@Lennon.com,1234,01/01/2001,1000,,,Example area,good artist <br>
</i>	
<hr class="help">


<A NAME="imported_members_summary"></A>
<h3>Zusammenfassung Mitglieder importieren</h3>
Diese Seite zeigt eine Übersicht (Vorschau) der Mitglieder. 
Zu diesem Zeitpunkt wurde noch nicht importiert. Sie können den Link auswählen (Nummer) um den Status
der &quot;Gültigen Datensätze&quot;, der &quot;Ungültigen Datensätze&quot; oder 
&quot;Alle Datensätze&quot; anzusehen.<br>
Wenn Sie &quot;Importieren&quot; auswählen werden die gültigen Datensätze ( Mitglieder) importiert.
Wir empfehlen den Status der Datensätze vor dem Import anzusehen.
<br>
Falls die Option &quot;Sende Aktivierungs-E-Mail&quot; ausgewählt ist und die
Gruppe eine aktive Gruppe ist, erhalten die importierten Mitglieder ein
Aktivierungs-E-Mail.
  
<hr class="help">


<A NAME="imported_member_details"></A>
<h3>Suche Datensätze (Mitglieder)</h3>
In diesem Fenster können Sie in der Liste für spezifizierten Mitglieder suchen. 
Sie können entweder nach der Zeilennummer oder nach dem Mitglied suchen. Die Suche nach Mitglied 
sucht in den Feldern Benutzername und Mitgliedsname.<br>
<hr class="help">


<A NAME="imported_member_details_result"></A>
<h3>Suchergebnisse</h3>
Dieses Fenster zeigt das Suchergebnis. Bei Fehlern wird man durch einen Fehlertyp 
informiert (z.B. Feld nicht vorhanden, Benutzername bereits vorhanden). Bei erfolgreichen
Importdaten wird das Kreditlimit und der Saldo angezeigt.<br>
Um die Mitglieder zu importieren können sie mit &quot;Zurück&quot; diese Seite verlassen
und dann mit &quot;Importieren&quot; den Import durchführen.
</span>

<span class="member">
<hr>
<br><br><A NAME="contacts"></A>
<h3>Kontakte</h3>
<br><br>Über diese Seite können Sie Ihre Kontakte verwalten.

In der Kontaktliste (Menü: Persönlich > Kontakte) können Sie verschiedene Aktionen durchführen, 
indem Sie mit der Maus einen der folgenden Punkte aus der Liste anklicken: 

<ul>
	<li><b>Benutzername - Name:</b> Das Profil dieses Mitglieds öffnen.
	<li><b>E-Mail:</b> Ein E-Mail an dieses Mitglied senden.
	<li><b><img border="0" src="${images}/edit.gif" width="16" height="16">&nbsp;</b>
	Ansehen/Hinzufügen/Bearbeiten von zusätzlichen Informationen zu diesem Mitglied.
	<li><b><img border="0" src="${images}/edit_gray.gif" width="16"
		height="16">&nbsp;</b> Wenn dieses Symbol farblos erscheint, so bedeutet das, dass 
		das Informationsfeld keine Information enthält.
	<li><b><img border="0" src="${images}/delete.gif" width="16"
		height="16">&nbsp;</b> Das Mitglied von der Kontaktliste löschen.
</ul>
Am Anfang sind noch keine Kontakte in dieser Liste. Sie können Kontakte auf zwei verschiedene Arten
hinzufügen:
<ul>
	<li>Durch verwenden von &quot;<a href="#add_contact"><u>Neuen Kontakt hinzufügen</u></a>&quot;
	im unteren Fenster.
	<li>Über <a href="${pagePrefix}#user_management#search_member_by_member"><u>Suche eines Mitglieds</u>
	</a> (&quot;Menü: Suche&quot;). In der Liste mit den Suchergebnissen können Sie
	das <a href="${pagePrefix}profiles"><u>Profil</u></a> eines Mitglieds öffnen, indem Sie den Namen des
	Mitglieds anklicken. Im Profil selbst kann dann durch anklicken von &quot;Zur Kontaktliste hinzufügen&quot; 
	das Mitglied zur Kontaktliste hinzugefügt werden.
</ul>
<hr class="help">

<A NAME="add_contact"></A>
<h3>Kontakt hinzufügen</h3>
Hier können Sie einen neuen Kontakt zu Ihrer Kontaktliste hinzufügen. Geben Sie bitte den 
Benutzername oder Namen in die Felder (mit Autovervollständigung) ein, mit &quot;Weiter&quot; bestätigen.
<hr class="help">

<br><br><A NAME="contact_note"></A>
<h3>Kontaktnotiz</h3>
Auf dieser Seite können Sie zusätzliche Informationen über einen Benutzer eingeben. Diese Information 
ist nur für Sie selbst sichtbar und wird gelöscht, wenn Sie das Mitglied von der Kontaktliste entfernen.

<hr class="help">

<a name="contact_us"></a>
<h3>Kontakt</h3>
Dies ist eine Seite mit Informationen, wie die Gemeinschaft oder Organisation 
für Verwaltungszwecke und Unterstützung kontaktiert werden kann.
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


