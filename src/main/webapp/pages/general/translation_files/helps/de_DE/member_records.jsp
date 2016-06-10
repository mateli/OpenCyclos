<div style="page-break-after: always;">
<a NAME="top"></a>
<br><br>Mitgliedseinträge sind benutzerdefinierte Informationseinheiten, 
die einem bestimmten Mitglied zugeschrieben sind. Es gibt eine Vielzahl von Funktionen 
und Möglichkeiten für die Definition und die Verwaltung von Mitgliedseinträgen.<br>
Mitgliedseinträge können verwendet um Informationen zu Mitgliedern auf der 
&quot;One-to-Many&quot;-Basis speichern möchten (das bedeutet: Für jedes Mitglied können 
Sie in seinen &quot;Mitgliedseinträgen&quot;) eine Vielzahl an unterschiedlichen Werten speichern). 
<span class="admin">
Dies unterscheidet Mitgliedsfelder von <a	href="${pagePrefix}custom_fields">
<u>benutzerdefinierten Feldern</u></a>, in denen Sie zwar auch benutzerdefinierte Informationen 
für ein Mitglied festlegen können, allerdings jeweils nur einen Wert pro Mitglied und Feld.<br>
<br>
In den Mitgliedseinträgen können Sie viele unterschiedlichen Typen und Felder definieren. 
Ein einfaches Beispiel dafür ist das Feld &quot;Bemerkungen&quot; der vorherigen Cyclos-Version. 
Eine Bemerkung war ganz einfach eine Lise von Textfeldern, die einem Mitglied angefügt werden konnte. 
Nun ist es allerdings möglich, den Einträgen mehrere Feldtypen hinzuzufügen, und es gibt 
unterschiedliche Möglichkeiten für ihr Erscheinen. (Erklärung weiter unten) 
</span>

<span class="admin"><br><br>Dieses Handbuch enthält einen <a href="#guidelines"><u>Leitfaden</u></a>, 
den Sie vielleicht zuerst anschauen sollten, und <a href="#example"><u>Beispiele</u></a>, 
die Ihnen das Thema Mitgliedseinträge besser verständlich machen.  
<br><br><i>Wo ist es zu finden?</i><br>
Zu den Mitgliedseinträgen gelangen Sie über das <a href="${pagePrefix}profiles"><u>Profil</u></a>
des Mitglieds, Abschnitt &quot;Mitglied-Informationen&quot;. Einstellen können Sie die Mitgliedseinträge 
über &quot;Menü: Benutzer und Gruppen > Mitgliedseinträge&quot;.
<br><br>
<i>Wie werden die Mitgliedseinträge aktiviert?</i><br>
Bitte schauen Sie dazu in unserem  <a href="#guidelines"><u>Leitfaden</u></a> nach.
</span>
<span class="broker">
<br><br>
Bei diesen Mitgliedseinträgen handelt es sich um zusätzliche Information zu den entsprechenden 
Mitgliedern. Je nach den von den Administratoren eingestellten Berechtigungen können Sie Einträge 
zeigen, hinzufügen, bearbeiten oder löschen. 
<br><br>
<i>Wo ist es zu finden?</i><br>
Zu den Mitgliedseinträgen gelangen Sie über das <a href="${pagePrefix}profiles"><u>
Profil</u></a> des Mitglieds. Der Mitgliedseintrag ist unter &quot;Brokeraktionen für...&quot; 
aufgelistet.<br>
Außerdem gibt es im Menü &quot;Brokering&quot; die Möglichkeit, die Mitgliedseinträge nach Eintragstypen 
zu durchsuchen. Zum Beispiel hat die Standarddatenbank einen Mitgliedseintrag &quot;Bemerkungen&quot;, 
der im Brokering-Menü erscheint. Hier können Sie nach <a href="#search_member_records">
<u>Mitgliedseinträgen suchen</u>.
</span>
<hr>

<span class="admin"> <a name="guidelines"></a>
<h3>Leitfaden für die Definition von Mitgliedseinträgen</h3>
Um neue Mitgliedseinträge zu erstellen, müssen Sie die folgenden Schritte durchführen: 
<ol>
	<li>Denken Sie als erstes darüber nach, was Sie von Ihren Mitgliedseinträgen wollen. 
	Welche Art von Informationen soll gespeichert werden? Könnte dies nicht auch über ein 
	einfaches	<a href="${pagePrefix}custom_fields"><u>benutzerdefiniertes Feld</u></a> geschehen?
	<li>Um einen neuen Typ von Mitgliedseintrag einzurichten, benötigen Sie dazu die Berechtigungen. 
	Diese finden Sie im Abschitt &quot;Mitgliedseinträge&quot; in den Administrations-
  <a href="${pagePrefix}groups#manage_group_permissions_admin_system"><u>
	Berechtigungen</u></a>; aktivieren Sie bitte das Kontrollkästchen &quot;Verwalten&quot;.
	<li>Danach können Sie einen neuen Typ Mitgliedseintrag erstellen, über 
	&quot;Menü: Benutzer und Gruppens > <a href="#member_record_types_list"><u>Mitgliedseinträge</u></a>
	&quot; mit Hilfe der Schaltfläche &quot;Neuen Mitgliedseintrag einfügen&quot;.
	<li>Haben Sie den neuen Typ Mitgliedseintrag eingerichtet, so haben Sie in der 
	<a href="#member_record_type_fields_list"><u>nächste Maske</u></a> die Möglichkeit, diesem Typ 
	neue Felder hinzuzufügen. Sie MÜSSEN jedem Mitgliedseintrag unbedingt mindestens ein Feld zuweisen, 
	da der Typ sonst keine Information enthalten kann, und völlig unbrauchbar wäre. 
	Für manche Felder müssen Sie auch mögliche Werte einrichten (siehe <a href="#example"><u>Beispiel</u></a> ).
	<li><b>Berechtigungen einstellen</b> Ist der Mitgliedseintrag eingerichtet, müssen Sie definieren, 
	wer Mitgliedseinträge zeigen, ändern oder löschen darf, über &quot;Menü: Benutzer und Gruppen > 
	Gruppenberechtigungen&quot;, Abschnitt &quot;Migliedseinträge&quot;. Dies können Sie sowohl für 
	Administratorengruppen als auch für Brokergruppen einstellen. 
	<li>Der Mitgliedseintrag erscheint mit einer Schaltfläche unter dem Mitglieds-
	<a href="${pagePrefix}profiles"><u>Profil</u></a>, im Abschnitt &quot;Mitglieds-Informationen&quot; 
	des Fensters <a	href="${pagePrefix}profiles#actions_for_member_by_admin"><u>Aktionen für...</u></a>. 
	Dies ist nur für Broker oder Administratoren sichtbar. Hier können Sie den Mitgliedseinträgen so viele 
	Elemente hinzufügen, wie Sie möchten.
	<li>Ist in der Konfiguration der Mitgliedseinträge die Option &quot;Im Menü anzeigen&quot; 
	ausgefüllt, können Sie diesen Mitgliedseintrags über das Hauptmenü 
	&quot;Menü: Benutzer und Gruppen&quot; anzeigen.
	<li><b>Suchen:</b> Alle Mitgliedseinträge können über die Seite &quot;Aktionen&quot; unter dem Profil 
	des Mitglieds. Sie können alle Mitgliedseinträge (nicht nur die zu einem Mitglied gehörenden) 
	über das Menü durchsuchen. 
</ol>
<hr class="help">

<a name="example"></a>
<h3>Beispiel Mitgliedseintrag</h3>
Die Hilfe-Beschreibungen beziehen sich auf einen  bestimmten Typ Mitgliedseintrag als Beispiel, 
um das Konzept der Mitgliedseinträge besser verständlich zu machen. Das Beispiel ist ein einfaches, 
und ohne Zweifel wäre eine bessere Konfiguration dieses Mitgliedseintrages möglich gewesen. 
<ol>
	<li><b>Erste Überlegungen:</b> In diesem Beispiel richten wir einen neuen Typ Mitgliedseintrag 
	ein, den wir &quot;Informationsdienst&quot; nennen, um festzustellen wie häufig und mit welchen 
	Fragen Mitglieder sich an den Informationsdienst wenden.<br>
	<br>
	<li><b>Berechtigungen einstellen:</b> &quot;Menü: Benutzer und Gruppen > Gruppenberechtigungen&quot;, 
	und wenden Sie sich an die Hilfen in diesen Masken, da das <a	href="${pagePrefix}groups#manage_groups">
	<u>Einstellen von Berechtigungen</u></a> recht einfach ist.<br>
	<br>
	<li><b>Neuen Mitgliedseintrag einrichten:</b> Im &quot;Menü: Benutzer und Gruppen >
	<a href="#member_record_types_list"><u>Mitgliedseinträge</u></a>&quot; klicken Sie bitte 
	auf die Schaltfläche  &quot;Neuen Mitgliedseintrag einfügen&quot;. Im folgenden  <a
		href="#edit_member_record_type"><u>Fenster</u></a>, geben Sie Folgendes ein: 
	<ul>
		<li><b>Name:</b> &quot;Informationsdienst&quot;
		<li><b>Titel:</b> dies wäre dann &quot;Informationsdienste&quot;.
		<li><b>Gruppen:</b> Wählen Sie eine Mitgliedsgruppe, für die Sie den neuen Typ 
		Mitgliedseintrag verwenden möchten. Zum Beispiel &quot;Vollwertige Mitglieder&quot;
		<li><b>Layout Suchergebnisse:</b> Wenn wir diese nicht für irgendeine numerische Analyse 
		verwenden wollen, wählen wir hier einfach &quot;Flach&quot;.
		<li><b>Im Menü anzeigen:</b> Dies bedeutet, der Eintragstyp erscheint im Haupt-
		&quot;Menü: Benutzer und Gruppen&quot;. Auf dieser Seite können Sie mit unterschiedlichen 
		Suchoptionen nach Eintragswerten suchen. 
		<li><b>Bearbeitbar:</b> Es gibt keinen Grund dafür, die Elemente nach ihrer Einrichtung 
		verändern zu wollen; wir füllen hier also nicht aus. 
	</ul>
	Danach klicken wir auf &quot;Weiter&quot; um den neuen Typ Mitgliedseintrag einzurichten. 
	Er erscheint jetzt in unserer Übersicht <a href="#member_record_types_list">
	<u>Mitgliedseinträge</u></a>.<br>
	<br>
	<li><b>Felder im Mitgliedseintrag einrichten:</b> Nun müssen Sie im neuen Mitgliedseintrag 
	 auch Felder einrichten, sonst macht der neue Eintrag keinen Sinn. In der Übersicht <a
	 href="#member_record_types_list"><u>Mitgliedseinträge</u></a> das 
	 Bearbeiten-Symbol <img border="0" src="${images}/edit.gif" width="16"
	 height="16">&nbsp; anklicken, dies bringt Sie zum Fenster  <a
	 href="#edit_member_record_type"><u>Mitgliedseintrag bearbeiten</u></a>. Verwenden Sie die 
	 Schaltfläche &quot;Neues benutzerdefiniertes Feld einfügen&quot;, für jedes neu einzurichtende 
	 Feld. Dies bringt uns zur Maske <a href="#member_record_type_fields_list"><u>Liste der 
	 benutzerdefinierten Felder für Mitgliedseinträge</u></a>.<br>
	 Die Felder sind hier nicht unbedingt sinnvoll, dienen aber auch nur als Beispiel.<br>
	<ul>
		<li><b>Datenfeld:</b> durch anklicken der Schaltfläche <a	href="${pagePrefix}custom_fields#edit_custom_fields">
		<u>Neues Benutzerdefiniertes Feld einfügen</u></a> gelangen wir zur Eingabemaske. 
		Hier finden wir Folgendes (nicht aufgeführte Formularfelder sind für die Funktion des 
		neuen Feldes nicht notwendig): 
		<ul>
			<li><b>Name:</b> &quot;Datum&quot;
			<li><b>Datentyp:</b> &quot;Datum&quot;
		</ul>
		Geben Sie irgend etwas in die anderen Felder des Formulars ein, und klicken Sie auf 
		&quot;Weiter&quot; um zu speichern.<br>
		<b>ANMERKUNG:</b> tatsächlich ist das Datumsfeld völlig überflüssig, da Cyclos automatisch das 
		Erstellungsdatum jedes Mitgliedseintrags speichert, so dass Sie darüber auch eine Suche 
		laufen lassen können. 
		<li><b>Feldtyp:</b>durch Anklicken der Schaltfläche &quot;Neues benutzerdefiniertes Feld einfügen&quot; 
		in der Liste der &quot;benutzerdefinierte Felder&quot; der Mitgliedseinträge&quot;. 
		Geben Sie nun Folgendes ein:  
		<ul>
			<li><b>Name:</b> &quot;Type&quot;
			<li><b>Datentyp:</b> &quot;Liste&quot;
			<li><b>Feldtyp:</b> &quot;Optionsschaltfläche&quot;
			<li><b>Erforderlich:</b> sollte ausgefüllt werden .
		</ul>
		Nach Anklicken von &quot;Weiter&quot;, gelangen Sie wieder auf die Seite mit der Liste der 
		&quot;benutzerdefinierten Felder&quot; für Mitgliedseinträge. Nun müssen Sie noch für dieses 
		Feld die möglichen Werte eingeben. Dies tun Sie durch Anklicken des Bearbeiten-Symbols  
		<img border="0" src="${images}/edit.gif" width="16" height="16">&nbsp; beim nun gelisteten 
		Feld.<br>
		Dies bringt Sie zurück zum Formular &quot;benutzerdefiniertes Feld bearbeiten&quot;. Etwas 
		weiter unter klicken Sie bitte auf die Schaltfläche &quot;Neuer möglicher Wert&quot; um die 
		neuen Werte &quot;Beschwerde über andere Mitglieder&quot;, &quot;Probleme mit Cyclos&quot;
		und &quot;Anregungen&quot; einzugeben. Wenn Sie dies getan haben, sollten diese 
		neuen Werte in der Liste <a href="${pagePrefix}custom_fields#possible_values"><u>
		Werteliste</u></a> erscheinen. Zum Schluss klicken Sie noch auf &quot;Zurück&quot; um zur 
		Feldübersicht zurück zu kehren. 
		<li><b>Bemerkungsfeld:</b> Letztendlich fügen wir ein Bemerkungsfeld auf genau die gleiche 
		Weise hinzu: 
		<ul>
			<li><b>Name:</b> &quot;Bemerkung&quot;
			<li><b>Datentyp:</b> &quot;Zeichenkette&quot;
			<li><b>Feldtyp:</b> &quot;Rich-Texteditor&quot;
		</ul>
		Der neue Eintragstyp ist nun fertiggestellt. Im Hauptmenü erscheint er allerdings erst dann wenn 
		Sie sich ab- und dann wieder anmelden. 
	</ul>
	<li><b>Daten hinzufügen:</b> Nun können Sie anfangen, die Mitgliedseinträge zu verwenden. 
	Unter dem Profil eines Mitglieds, im Abschnitt &quot;Mitglieds-Informationen&quot; des Fensters <a
	href="${pagePrefix}profiles#actions_for_member_by_admin"><u>Aktionen für...</u></a>, 
	finden Sie eine spezielle Schaltfläche &quot;Informationsdienste&quot;, über die Sie zu dem 
	Fenster gelangen, in das Sie die Daten in Ihren neuen Mitgliedseintrag eingeben können. 
	<li><b>Sie können die Mitgliedseinträge auch, über das &quot;Menü: 
	Benutzer und Gruppen > Informationsdienste&quot; finden.</b>
</ol>
<hr class="help">
</span>


<span class="admin">
<a NAME="member_records"></a>
<h3>Mitgliedseintrag bearbeiten</h3>
Diese Maske zeigt die Daten vom <a href="#top"><u>Mitgliedseintrages</u></a> vom Typ
<a href="#edit_member_record_type"><u>Liste</u></a>.<br>
Sie zeigt den Benutzer, der den Eintrag erstellt hat, wann er erstellt wurde, und 
den Inhalt der benutzerdefinierten Felder, die im  &quot;(Menü: Benutzer und
Gruppen > <a href="#member_record_types_list"><u>Mitgliedseinträge</u></a>)&quot; definiert 
wurden.
Wurde das Eintragsfeld als  &quot;Bearbeitbar&quot; definiert, so besteht ebenfalls die 
Option die Eintragsdaten zu ändern.<br>
Wenn Sie die Berechtigungen haben, können Sie auch einen neuen Eintrag erstellen, durch 
Anklicken der Schaltfläche &quot;Weiter&quot; unter der Seite mit der Bezeichnung  
&quot;Neues ... einrichten&quot;
<hr class="help">
</span>

<span class="broker">
<a NAME="member_records"></a>
<h3>Mitgliedseintrag bearbeiten</h3>
Diese Maske zeigt die Daten vom <a href="#top"><u>Mitgliedseintrag</u></a>.
Einige Typen Mitgliedseinträge sind bearbeitbar. Bearbeitbare Mitgliedseinträge können 
durch Wählen der Schaltfläche &quot;Bearbeiten&quot;, Bearbeiten der Felder und Anklicken von 
&quot;Weiter&quot; verändert werden.<br>
Wenn Sie die Berechtigungen haben, können Sie auch einen neuen Eintrag erstellen, durch 
Anklicken der Schaltfläche &quot;Weiter&quot; unter der Seite mit der Bezeichnung 
&quot;Neues ... einrichten&quot;. 
<hr class="help">
</span>

<span class="admin"> <a NAME="member_record_types_list"></a>
<h3>Liste der Mitgliedseintäge</h3>
In dieser Maske sind die verfügbaren Typen <a href="#top"><u>Mitgliedseinträge</u></a> aufgeführt. 
<ul>
	<li>Durch Anklicken des Bearbeiten-Symbols <img border="0" src="${images}/edit.gif" width="16"
		height="16">&nbsp; können die Eigenschaften des Typs Mitgliedseintrag geändert werden. 
	<li>Durch Anklicken des Löschen-Symbols  <img border="0" src="${images}/delete.gif"
		width="16" height="16">&nbsp; wird der Typ Mitgliedseintrag entfernt. Bitte beachten Sie, 
		dass das Entfernen eines Typs Mitgliedseintrags nur dann möglich ist, wenn es nicht bereits 
		in Verwendung ist; sobald irgendein Mitglied in diesem Typ Mitgliedseintrag Information hat, 
		ist es unmöglich den Typ Mitgliedseintrag zu löschen.
	<li>Danach klicken Sie auf die Schaltfläche &quot;Weiter&quot; neben  &quot;Neues Mitgliedseintrag erstellen&quot;. 
	Falls Sie einen neuen Typ Mitgliedseintrag einrichten möchten, halten Sie sich am besten an 
	diesen <a href="#guidelines"><u>Leitfaden</u></a>.
</ul>
<hr class="help">
</span>

<span class="admin broker"> <a NAME="remarks"></a>
<h3>Einfache Mitgliedseinträge</h3>
Die Information die Sie in dieser Maske eingeben können, ist ein einfacher 
&quot;<a href="#top"><u>Mitgliedseintrag</u></a>&quot;. Dies wird meist definiert, um zu ermöglichen, 
dass einem Mitgliedsprofil Informationen hinzugefügt werden können. Die Felder werden von der Administration 
definiert. Um einen Eintrag hinzuzufügen, füllen Sie bitte die Felder aus (Felder mit einem roten 
Stern (*) sind Pflichtfelder) und klicken Sie auf &quot;Weiter&quot;. Die bereits bestehenden Einträge 
für das Mitglied sind im unteren Bereich aufgelistet. 
<hr class="help">
</span>

<span class="admin broker"> <a NAME="search_member_records"></a>
<h3>Mitgliedseinträge suchen</h3>
Hier können Sie Mitgliedseinträge durchsuchen, indem Sie die Felder ausfüllen und 
auf &quot;Suchen&quot; klicken.
<ul>
	<li><b>Schlüsselwörter:</b> ermöglicht Ihnen die Suche in allen Feldern des Typs <a
		href="#top"><u>Mitgliedseintrag</u></a>.
	<li><b>Benutzername / Mitgliesname:</b> ermöglichen Ihnen die Suche in Einträgen des Mitglieds, 
	dem sie zugeordnet sind. 
	<li><b>Broker Benutzername / Brokername:</b> ermöglichen Ihnen die Suche in Einträgen des Mitglieds
	welche einem spezifischen Broker zugeordnet sind.	
	<li><b>Registrierungsdatum:</b> kann verwendet werden, um Mitgliedseinträge nach Erstellungsdatum 
	zu durchsuchen. Jeder Typ Mitgliedseintrag ist automatisch mit einem Feld &quot;Erstellungsdatum&quot; 
	ausgestattet, so dass Sie dieses Feld nicht extra einrichten müssen.
</ul>
Zusätzlich zu diesen Eingabefeldern gibt es benutzerdefinierte Felder, die Sie für Ihren Typ 
Mitgliedseintrag definiert haben in &quot;Menü: Benutzer und Gruppen > <a
	href="#member_record_types_list"><u>Mitgliedseinträge</u></a> &quot;.
<hr class="help">
</span>

<span class="admin broker">
<a NAME="search_records_of_member"></a>
<h3>Mitgliedseinträge eines Mitglieds durchsuchen</h3>
Hier können Sie Mitgliedseinträge dieses bestimmten Mitglieds durchsuchen, indem Sie die 
Felder ausfüllen, und auf &quot;Suchen&quot; klicken.
<ul>
	<li><b>Schlüsselwörter:</b> können verwendet werden, um jedes Feld der Mitgliedseinträge 
	nach ihnen zu durchsuchen. 
	<li><b>Erstellungsdatum:</b> wird für jede Eingabe in den Mitgliedseinträgen gespeichert; 
	dieses Feld wird automatisch für jeden Typ Mitgliedseintrag eingerichtet. 
	<li>Zusätzlich zu diesen Eingabefeldern gibt es benutzerdefinierte Felder, die zu dem 
	Typ Mitgliedseintrag gehören, und die vom Administrator definiert werden. 
	<li>Durch Anklicken der Schaltfläche &quot;Weiter&quot; können Sie den Mitgliedseinträgen 
	dieses Mitglieds neue Daten hinzufügen. Diese Schaltfläche ist mit &quot;Neue einrichten&quot; 
	bezeichnet, gefolgt von dem Namen des Mitgliedseintrags. 
</ul>
<hr class="help">
</span>

<span class="broker admin"> <a NAME="member_records_search_results"></a>
<h3>Suchergebnisse Mitgliedseinträge</h3>
Hier sind die Resultate der Durchsuchungen der Mitgliedseinträge. Der Benutzername 
und der spezifizierte Mitgliedsname erscheinen im Ergebnis. 
<ul>
	<li>Um den gesamten Mitgliedseintrag zu zeigen, klicken Sie auf das Anzeigen-Symbol  
	<img border="0"	src="${images}/view.gif" width="16" height="16">.
	<li>Um Änderungen durchzuführen, klicken Sie auf das Bearbeiten-Symbol 
	<img border="0" src="${images}/edit.gif" width="16" height="16">.
	<li>Zum Entfernen, klicken Sie auf das Löschen-Symbol  <img border="0" src="${images}/delete.gif"
		width="16" height="16">.
</ul>
<hr class="help">
</span>

<span class="admin"> <a NAME="edit_member_record_type"></a>
<h3>Mitgliedseintrag ändern oder einrichten</h3>
Hier können Sie die Struktur der <a href="#top"><u>Mitgliedseinträge</u></a> ändern.
Um den Eintrag zu ändern, wählen Sie &quot;Bearbeiten&quot;, bearbeiten Sie die Felder, 
und klicken Sie auf &quot;Weiter&quot;. Wenn Sie einen neuen Typ einrichten, müssen 
Sie nicht zuvor auf &quot;Bearbeiten&quot; klicken. Um einen neuen Typ einzurichten, 
lesen Sie bitte zuerst diesen <a	href="#guidelines"><u>Leitfaden</u></a> und 
unser <a href="#example"><u>Beispiel</u></a>.
<ul>
	<li><b>Name:</b> erklärt sich von selbst.
	<li><b>Titel:</b> wird auf der Benutzeroberfläche für die Anzeige verwendet, und 
	ist üblicherweise der Name des Mitgliedseintrags im Plural. 
	<li><b>Beschreibung:</b> ist eine Textbeschreibung des Zwecks und der Bedeutung des 
	Eintrags. Geben Sie ein, was immer Sie möchten.
	<li><b>Gruppen:</b> Dieses Auswahlfeld ermöglicht Ihnen zu wählen, bei welche Benutzergruppen
	der Mitgliedseintrag erscheint mit einer Schaltfläche unter dem Mitgliedsprofil, im Abschnitt 
	&quot;Mitglieds-Informationen&quot; im Bereich <a	href="${pagePrefix}profiles#actions_for_member_by_admin">
	<u>Aktionen für ...</u></a> unterhalb vom Mitgliedsprofil (nur für Administratoren sichtbar).
	<li><b>Layout Suchergebnisse:</b> Hier können Sie wählen, wie die Eintragsdaten in den Suchergebnissen 
	erscheinen sollen. Die folgenden Optionen gibt es: 
	<ul>
		<li><b>Einfach:</b> jedes Element wird unter dem vorangehenden aufgeführt, jeweils 
		durch Linien getrennt. Dies ist für Bemerkungen und Ähnliches das am besten passende Format.
		<li><b>Liste:</b> die Elemente sind in Tabellenform dargestellt, mit Spalten und Zeilen. 
		Jeder Eintrag ist eine Zeile der Tabelle. 
	</ul>
	<li><b>Im Menü anzeigen:</b> wird dies gewählt, gibt es, wenn man als Administrator 
	angemeldet ist,	ein Menüelement mit dem Namen des	Mitliedseintrages, unter dem Abschnitt 
	&quot;Benutzer und Gruppen&quot;.	Dieses Menüelement können Sie verwenden, um den Mitgliedseintrag 
	nach bestimmten Werten zu	<a href="#search_member_records"><u>durchsuchen</u></a>.
	<li><b>Bearbeitbar:</b> falls gewählt, können die Eintragsdateien nach Erstellung verändert werden 
	(durch Administratoren oder Broker). Wird es nicht gewählt, können nach Erstellung keine Veränderungen 
	mehr vorgenommen werden. Normalerweise sind die Eintragstypen nicht bearbeitbar: Ist ein Eintrag erst 
	einmal erstellt, kann er nicht mehr verändert werden. 
</ul>
<br>
Zusätzlich zur Bearbeitung der Eintragseigenschaften, sollten Sie auch die &quot;benutzerdefinierten 
Felder&quot; im <a href="#member_record_type_fields_list"><u>Fenster unten</u></a> erstellen und ändern, 
andernfalls ist der von Ihnen erstellte Mitgliedseintrag leer und bedeutungslos. 
<hr class="help">

<a NAME="member_record_type_fields_list"></a>
<h3>Liste der benutzerdefinierten Felder für Mitgliedseinträge</h3>
Hier sind die Felder des Mitgliedseintrags aufgelistet. In den Feldern sind die Daten des Eintrags 
gespeichert und indexiert. Damit ein Mitgliedseintrag sinnvoll ist, muss es mindestens ein 
benutzerdefiniertes Feld geben. 
<ul>
	<li>Um ein neues benutzerdefiniertes Feld einzurichten, klicken Sie bitte auf &quot;Weiter&quot;, 
	neben &quot;Neues benutzerdefiniertes Feld einfügen&quot;. 
	<li>Um die Anordnung der Felder auf der Seite zu ändern, klicken Sie auf die Schaltfläche
	&quot;Feldanordnung ändern&quot;. Diese Schaltfläche erscheint nur dann, wenn Sie auch anwendbar ist.
	<li>Um ein Feld zu verändern, klicken Sie auf das Bearbeiten-Symbol <img border="0" src="${images}/edit.gif"
		width="16" height="16">.
	<li>Um ein Feld zu löschen, klicken Sie auf das Löschen-Symbol <img border="0" src="${images}/delete.gif"
		width="16" height="16">.
</ul>
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