<div style="page-break-after: always;">
<br><br>Häufig möchten die Organisationen, die mit Cyclos arbeiten, 
ihre eigenen, spezifischen Informationen in der Cyclos-Datenbank speichern. Es ist daher 
möglich, die Felder der Datenbank, die in der Anwendung erscheinen,  zu bearbeiten.<br>
Ein Administrator kann neue Felder hinzufügen, und existierende Felder ändern oder löschen. 
Dies ist möglich für Mitglieder, Administratoren und <a href="${pagePrefix}operators">
<u>Operatoren</u></a>, für  Profile,  Inserate, <a href="${pagePrefix}loans"><u>Darlehen</u></a> 
und <a href="${pagePrefix}loan_groups"><u>Darlehensgruppen</u></a>,  
<a href="${pagePrefix}member_records"><u>Mitgliedseinträge</u></a> und für Zahlungen.<br>
Wenn eine Organisation zum Beispiel ein zusätzliches Feld im <a	href="${pagePrefix}profiles">
<u>Profil</u></a> für die Schuhgröße eines Mitglieds benötigt, kann der Administrator 
ein neues Feld einrichten und dafür verschiedene Eigenschaften definieren, wie z.B. 
Feldnamen, Typ, Größe, Sichtbarkeit, Berechtigungen, Ort, Bewertung und weitere Merkmale 
oder Einstellungen.
Die Felder können  <a href="${pagePrefix}groups"><u>Gruppen</u></a> zugeordnet werden.
Dies ermöglicht Ihnen, unterschiedliche Registrierungsformulare und Profile für Konsumenten 
und Geschäfte  einzurichten, um nur ein Beispiel zu nennen. Im oben genannten Beispiel 
bestünde die Möglichkeit, unterschiedliche Regelungen für Mitglieder mit unterschiedlichen 
Schuhgrößen zu schaffen.
Cyclos ist mit einer Reihe von Standard-benutzerdefinierten Feldern ausgestattet, die aber 
natürlich alle bearbeitbar sind. Natürlich sind nicht alle Felder der Datenbank benutzerdefinierte 
Felder; einige der Felder sind so wichtig, dass sie weder entfernt noch bearbeitet werden können.<p>

<i>Wo ist es zu finden?</i><br>
Benutzerdefinierte Felder können über das Hauptmenü-Element &quot;Felder anpassen&quot; 
verwaltet werden. 
Benutzerdefinierte Felder für Zahlungen werden im Überweisungstyp verwaltet, sie werden im Hauptmenü 
nicht aufgelistet. Sie können auf die Überweisungstypen über das 
Hauptmenü &quot;Konten>Konten verwalten&quot; > &quot;Kontotyp bearbeiten&quot; zugreifen.

<br><br><i>Wie werden benutzerdefinierte Felder aktiviert?</i><br>
Um benutzerdefinierte Felder zu verwalten, benötigen Sie eine  
<a href="${pagePrefix}groups#manage_group_permissions_admin_system"><u>Berechtigung</u></a>;
diese können nur an Administratoren vergeben werden. Sie finden sie unter dem Abschnitt
&quot;Benutzerdefinierte Felder&quot;.  
<hr>

<a name="list_custom_fields"></a>
<h3>Liste der benutzerdefiniertern Felder</h3>
Diese Hilfe bezieht sich auf die benutzerdefinierten Felder für Mitglieder, Administratoren, 
<a href="${pagePrefix}operators"><u>Operatoren</u></a>, 
<a href="${pagePrefix}advertisements"><u>Inserate</u></a>, 
<a href="${pagePrefix}loans"><u>Darlehen</u></a> und
<a href="${pagePrefix}loan_groups"><u>Darlehensgruppen</u></a>, und auf
<a href="${pagePrefix}member_records"><u>Mitgliedseinträge</u></a>.
<br><br>
Die Liste zeigt alle benutzerdefinierten Felder, die für das jeweilige Thema definiert wurden.<br>
Mitglieds-, Administratoren- und Operatorenfelder erscheinen in deren Profilen. In der 
Cyclos-Basiseinstellung sind bereits einige benutzerdefinierten Felder definiert.
Darlehens- und Gruppendarlehensfelder erscheinen auf den Seiten für Darlehen und Darlehensgruppen; 
Inseratfelder erscheinen auf der Inserateseite. 
<br><br>
Der Name und die Konfiguration der Felder kann über Auswahl es Bearbeiten-Symbols verändert werden. 
In der Hilfe-Datei auf der Seite finden Sie detaillierte Information zur Konfiguration der 
benutzerdefinierten Felder.
Löschen können Sie ein Feld durch Anklicken des roten Löschen-Symbols. Bitte beachten Sie dass 
Löschen eines benutzerdefinierten Feldes nur dann möglich ist, wenn es nicht bereits in Verwendung 
ist; sobald irgendein Feld der Datenbank Information enthält, ist es unmöglich das Feld zu löschen. 
In diesem Fall können Sie das Feld allerdings verbergen, indem Sie keine Gruppe freigeben. 
<br><br>
Über die Schaltfläche &quot;Feldanordnung ändern&quot; können Sie die Anordnung der Felder bestimmen.
Auswahl der Schaltfläche &quot;Neues benutzerdefiniertes Feld&quot; ermöglicht Ihnen das Einfügen 
eines neuen Profilfeldes.<br>
<hr class="help">

<a name="order_custom_fields"></a>
<h3>Anordnung der Benutzerdefinierte Felder</h3>
Diese Hilfe bezieht sich auf die benutzerdefinierten Felder für Mitglieder, Administratoren,
<a href="${pagePrefix}operators"><u>Operatoren</u></a>, 
<a href="${pagePrefix}advertisements"><u>Inserate</u></a>, 
<a href="${pagePrefix}loans"><u>Darlehen</u></a> und 
<a href="${pagePrefix}loan_groups"><u>Darlehensgruppen</u></a>, und auf 
<a href="${pagePrefix}member_records"><u>Mitgliedseinträge</u></a>.
<br><br>
Hier können Sie die Anordnung einstellen, in der die benutzerdefinierten Felder auf der Seite 
erscheinen. Um dies zu tun, gehen Sie bitte mit Ihrem Mauszeiger auf die Feldbezeichnung, 
linksklicken Sie die Maus, und halten Sie den Finger darauf, während Sie den Namen in 
die neue Position &quot;ziehen&quot;.<br>
Danach klicken Sie bitte auf &quot;Weiter&quot;.
<hr class="help">

<a name="edit_custom_fields"></a>
<h3>Benutzerdefinierte Felder bearbeiten/einfügen</h3>
In diesem Fenster stellen Sie die Eigenschaften des benutzerdefinierten Feldes ein. Bitte tun 
Sie das mit aller Sorgfalt, da einige Eigenschaften nur bei der Einrichtung wählbar sind – 
ist das Feld erst in Verwendung, kann es sein, dass keine Veränderung mehr möglich ist.<br>
Bitte beachten Sie, dass nicht alle Optionen für alle Typen von benutzerdefinierten Feldern möglich sind. 
Derzeit haben die Mitgliedsfelder (Profilfelder) die meisten möglichen Optionen. 
<br><br>Sie haben die folgenden Optionen: 
<ul>
	<li><b>Name:</b> Dies ist der Name, des in Cyclos erscheinenden Feldes. 
	<li><b>Interner Name:</b> Dies ist der interne Name des Feldes. Er wird nur 
	zu Entwicklungszwecken verwendet. 
	<li><b>Datentyp:</b> Mit Hilfe des Datentyps spezifizieren Sie den Feldtyp. 
	Es gibt sechs Feldtypen. 
	<ul>
		<li><b>Zeichenkette:</b> Die Zeichenkette kann ein beliebiger Text sein. Wenn Sie für ein 
		Pflichtfeld ein &quot;Eingabemuster&quot; (z.B. für E-Mail-Adresse, oder Postleitzahl) 
		spezifizieren möchten, können Sie eine solche Maske im Feld darunter einrichten. 
		Die Maske zwingt den Benutzer dazu, Eingaben im korrekten Format zu tätigen.
		Nach Anklicken von &quot;Weiter&quot; werden alle Eingaben noch einmal überprüft.<br>
		Dokumentationen zur Eingabemaske finden Sie unter 
		<a href="http://javascriptools.sourceforge.net/docs/manual/InputMask_mask.html" target="_blank"> 
		JavaScript tools</a>.
		<li><b>Liste:</b> Eine Liste von Werten, z.B. zum Ort (&quot;Norden&quot;, &quot;Osten&quot;, 
		&quot;Süden&quot; und &quot;Westen&quot;). Eine solche Aufzählung kann entweder als 
		Auswahlbox oder als Optionsschaltfläche erscheinen. Wenn Sie &quot;Auswahlbox&quot; wählen, 
		erscheint ein zusätzlicher Eintrag mit der Bezeichnung &quot;Titel alles ausgewählt&quot;. 
		Dieses Label erscheint dann als Standard in der Auswahlbox. Im Zusammenhang mit dem Ortsbeispiel 
		oben hieße das: &quot;Alle Richtungen&quot;. 
		<li><b>Zahl:</b> Dieser Typ bedeutet, dass das Feld nur aus einer ganzen Zahl bestehen kann, 
		ohne Dezimalkomma oder Punkt (je nach Sprache). 
		<li><b>Dezimalzahl:</b> Dieser Typ bedeutet, dass das Feld aus einer  Zahl mit Dezimalkomma 
		oder Punkt (je nach Sprache) bestehen kann. Genauigkeit und Format sind in den 
		<a href="${pagePrefix}settings#local"><u>&quot;Basiseinstellungen > Zahlenformat&quot;</u></a>  
		definiert.
		<li><b>Datum:</b> Dieses Feld kann nur ein Datum enthalten. Das Datumsformat können Sie unter 
		<a href="${pagePrefix}settings#local"><u>&quot;Basiseinstellungen > Internationalisierung > Datumsformat&quot;</u></a> 
		wählen.
		<li><b>Boolesch (ja/nein):</b> Ein boolesches Feld ist ein Kontrollkästchen mit nur zwei Werten: 
		&quot;ausgewählt&quot; und &quot;nicht ausgewählt&quot; (oder &quot;ja&quot;
		und &quot;nein&quot;).
		
		
		<li><b>URL:</b> In dieses Feld können Sie eine gültige URL eingeben. 
		Da URL-Feld wird als Link dargestellt, die URL-Seite wird 
		in einem neuen Tab/Fenster geöffnet.
		<li><b>Mitglied:</b> Dieses Feld kann dafür verwendet werden um ein Verhältnis
		Mitglied zu Mitglied zu speichern.
		Dieses Feld ermöglicht es ein Mitlied mit einer Schnellsuche (Name und Benutzername)
		zu selektieren. Dies ist ein angenehmer Weg, um Mitglieder für andere Mitglieder oder
		Eingaben, welche benutzerdefinierte Felder wie Zahlungen verwenden, zu 
		verknüpfen. Dies ist besonders praktisch, wenn sie die Brokerfunktion nicht 
		verwenden wollen, oder wenn ein Mitlgied bereits einem Broker zugeordnet ist.
		Ein typischen Beispiel wäre einen 'Kontakt' ins Mitgliederprofil einzufügen. 
        Eine andere Anwendung wäre, ein Mitgliedsfeld im Überweisungstyp hinzuzufügen,
        in Fällen bei denen der Auftraggeber oder Empfänger nicht bekannt ist, da
        es sich um eine Systemüberweisung handelt.
	</ul>
	<li><b>Hauptfeld:</b> Hier können Sie definieren, ob die für dieses Feld möglichen, wählbaren   
	 Optionen vom Wert eines anderen Feldes abhängen. Für mehr Information, klicken Sie 
	 <a href="#parent_field"><u>hier</u></a>.
	<li><b>Feldtyp:</b> In Übereinstimmung mit den Datentypen gibt es unterschiedliche Feldtypen. 
	Die folgenden Feldtypen gibt es: 
	<ul>
		<li>Rich-Texteditor oder Textbereich (eine Zeile oder ein Textfeld (5 Zeilen) für eine Zeichenkette (Text).<br>
		<b>Anmerkung:</b> Rich-Texteditorfelder können vom Mitglied nicht ausgeblendet werden.
		Siehe auch die Bemerkung unter &quot;Mitglied kann Feld verbergen&quot; auf dieser Seite.  
		<li>Auswahlbox oder Optionsschaltfläche für Listen
		<li>Textbereich für Ganzzahlen, Dezimalzahlen und Text. 
	</ul>
	<li><b>Feldgröße:</b> Als Feldgrößen stehen &quot;Sehr klein&quot;,	&quot;klein&quot;, 
	&quot;Mittel&quot; und &quot;Groß&quot; zur Vefügung. Die genaue, absolute Größe definieren Sie in 
	der Style-Sheet Datei. Sie können aber auch	&quot;Voll&quot; spezifizieren. 
	Die Option &quot;Standard&quot; kann für jeden Feldtyp unterschiedlich sein, bedeutet 
	allerdings meist 80% der verfügbaren Fläche. Zum Beispiel: das Feld &quot;Name&quot; auf dieser 
	Maske hat die als Standard eingestellte Größe. 

	<li><b>Freigegeben (nur benutzerdefinierte Überweisungsfelder) :</b> Sobald benutzerdefinierte
	Felder verwendet werden (Benutzer haben Daten in den Feldern) können sie nicht mehr gelöscht
	werden, da die Daten für im System zugänglich sein müssen. Ist ein benutzerdefiniertes Feld
	nicht erforderlich, können sie es einfach aus der Gruppenzuordnung entfernen. 
	Die ist jedoch für benutzerdefinierte Überweisungsfelder nicht möglich, da sie nicht direkt
	einer Gruppe zugeordnet sind. Deshalb können Sie es mit diesem Kontrollkästchen 
	sperren. Beachten Sie, das gesperrte Felder weiterhin im Suchfilter sichtbar sind.
	Diese müssen manuell gesperrt werden (siehe unten).

	<li><b>Sichtbar für (nur für Inseratefelder):</b> Hier können Sie festlegen, wer dieses benutzerdefinierte 
	Feld sehen darf. Folgende Möglichkeiten bestehen:<br>
	Alle Mitglieder (das Mitglied selbst und alle anderen Mitglieder, Broker und Administratoren.
	Alle Broker und Administratoren oder nur die Administratoren.

	<li><b>In der Suche anzeigen (nur für Überweisungsfelder):</b> Wird dies gewählt, erscheint 
	das benutzerdefinierte Überweisungsfeld als Filter in der Kontoübersicht. Beim  Zahlungstyp 
	&quot;Darlehen&quot; erscheint es in der Darlehenssuche nach Administrator. 
	<li><b>In der Ergebnisliste anzeigen (nur für Überweisungsfelder):</b> Wird dies gewählt, erscheint 
	das benutzerdefinierte Überweisungsfeld als Spalte in der Liste der Suchergebnisse. Beim Zahlungstyp 
	&quot;Darlehen&quot; erscheint es in der Ergebnisliste &quot;Darlehenssuche nach Administrator&quot;.<br>
	Bitte beachten Sie, dass das Feld immer in den Export als CSV und im Druck eingeschlossen ist. 
	Dies auch, wenn die Option nicht gewählt wurde. 
	
	<li><b>In Profil anzeigen für:</b> Hier können Sie festlegen, wer dieses benutzerdefinierte 
		Feld sehen darf. Folgende Möglichkeiten bestehen:<br>
	<ul>
		<li><b>Keine:</b> Dieses Feld ist für keinen Benutzer im System sichtbar.
		<li><b>Administrator:</b> Nur für Administratoren sichtbar.
		<li><b>Broker:</b> Nur ein Administator und der Broker des Mitglieds kann dieses Feld sehen.
		<li><b>Registrierung:</b> Das Mitglied kann dieses Feld nur bei der öffentlichen
		Registrierung sehen (aber nicht in seinem Profil). Broker und Administratoren können 
		dieses Feld sehen.
		<li><b>Mitglied, nicht bei der Registrierung:</b> Mitglied, Broker und Administrator
		können dieses Feld sehen, das Feld ist bei der Registrierung aber nicht sichtbar.
		<li><b>Mitglied:</b> Das Mitglied, Broker und Administratoren können dieses Feld sehen.
		<li><b>Alle Mitglieder:</b> Das Mitglied selbst, Broker und Administratoren und alle andern
		Mitglieder im System mit der entsprechenden Berechtigung für diese Gruppe, können dieses Feld sehen.
	</ul>
	<li><b>Bearbeitbar von:</b> Hier können Sie festlegen, wer dieses benutzerdefinierte 
		bearbeiten darf (die Hierarchie funktioniert genau wie bei &quot;In Profil anzeigen für&quot;).
	<li><b>In Mitgliedersuche für:</b> Hier können Sie definieren, für wen das Feld auf der Mitgliedssuchseite 
	erscheint (die Hierarchie funktioniert genau wie bei den Anzeigeberechtigungen). 
	<li><b>In Inseratensuche für:</b> Hier können Sie definieren, für wen das Feld auf der Inseratesuchseite 
	erscheint (die Hierarchie funktioniert genau wie bei den Anzeigeberechtigungen). 
	<li><b>In Suchbegriff enthalten:</b> Mit dieser Option machen Sie das Feld verfügbar für die Suchoption 
	nach Schlüsselworten in Mitglieds- und Inseratesuchen.<br> 
	Für die Mitgliedssuche nach Schlüsselwörtern haben Sie die Option, diese entweder nur in der Mitgliedssuche 
	als auch unter Mitgliedssuche und Inseratesuche einzuschließen. Wird letzteres gewählt, können 
	Mitglieder über die Mitglieds(profil)felder nach Inseraten suchen. Bitte beachten Sie, dass die nur 
	für kombinierte Suchen Sinn macht. Eine Inseratesuche mit einem einzigen Mitglied als Treffer wird alle 
	Inserate dieses Mitglieds anzeigen. 
	<li><b>In Darlehensuche für:</b> Hier können Sie definieren, für wen das Feld bei der  
	Darlehenssuche erscheint (die Hierarchie funktioniert genau wie bei den Anzeigeberechtigungen). 
	<li><b>Mitglied kann Feld verbergen:</b> Hier können Sie definieren, wer ein Feld verbergen kann 
	(die Hierarchie funktioniert genau wie bei den Anzeigeberechtigungen).<br> 
	<b>Anmerkung:</b> Wenn ein Mitglied ein Feld verbirgt bedeutet dies, dass weder andere Mitglieder
	noch Administratoren den Wert des Feldes in den Suchbegriffen finden. Natürlich haben
	Administratoren die Möglichkeit das Feld als separate Suchfunktion mit der 
	Option &quot;In Mitgliedersuche für - Administrator&quot; einzurichten.	 
	<br><b>Anmerkung 2:</b> Bitte beachten Sie, dass im Falle von bestehenden Feldern
	alle bereits vorhandenen Informationen für andere Mitglieder für dieses Feld sichtbar werden.
	Die Mitglieder sind eventuell nicht damit einverstanden, dass Informationen, von denen sie dachten,
	dass sie vorborgen sind, plötzlich für Andere sichtbar werden.
	<li><b>Im Mitgliederausdruck anzeigen:</b> Hier können Sie definieren, ob dieses Feld im Mitgliedsausdruck  
	erscheint. 
	<li><b>Bewertung:</b> Die folgenden Bewertungen können Sie spezifizieren: 
	<ul>
		<li><b>Erforderlich:</b> Falls gewählt ist dieses Feld ein Pflichtfeld, und es erscheint rechts 
		neben dem Feld ein roters Sternchen (*). Hier MUSS der Benutzer einen Wert eingeben. 
		<li><b>Eindeutig:</b> Wird dies gewählt, kann der Wert des Feldes im System nur ein einziges 
		mal vorkommen. Dies können Sie wählen, um zum Beispiel bei einer Passnummer oder einer Steuernummer 
		sicher zu stellen, dass sie nur ein Mal vorkommt. 
		<li><b>Minimale und maximale Länge:</b> Ist dieses Feld eine Zeichenkette, können Sie hier die minimale oder 
		die maximale Länge der Zeichenkette festlegen. Der Benutzer muss dann bei seiner Eingabe innerhalb dieser beiden 
		Werte bleiben. 
		<li><b>Feldvalidierung:</b> Falls Sie eine komplexere Bewertung benötigen, die nicht durch 
		einen gewöhnlichen Begriff gehandhabt werden kann, können Sie Ihre eigene Feldvalidierung schreiben.<br> 
		Eine typische Situation hierfür wäre, wenn Sie eine auf einer Berechnung der Eingabe beruhende Bewertung, 
		oder eine entfernte Validierung durchführen möchten.<br>
		Informationen dazu, wie Sie die Feldvalidierung einsetzen, finden Sie im 
		<a href="http://project.cyclos.org/wiki/index.php?title=Setup_%26_configuration#Custom_fields"
			target="_blank"><u>Wiki</u></a>.
	</ul>
	<li><b>Beschreibung:</b> Hier können Sie eine Beschreibung des Feldes eingeben. Die Administration 
	kann die Verwendung des Feldes erklären. Die Beschreibung erscheint nur im Bearbeitungsfeld. 
	<li><b>Feld für Gruppen freigeben:</b> Hier können Sie eingeben, welche Gruppen das Feld sehen können.
</ul>
<hr class="help">

<a name="parent_field"></a>
<h3>Hauptfeld</h3>
Hat ein Feld ein Hauptfeld („Parent field“), so bedeutet dies dass die für dieses Feld möglichen, 
wählbaren Optionen vom Wert eines anderen Feldes abhängen.<br>
Sie könnten z.B. ein benutzerdefiniertes Feld &quot;Provinz&quot; und ein benutzerdefiniertes Feld 
&quot;Stadt&quot; haben. Wählt der Benutzer &quot;Südliche Region&quot; als Provinz, dann listet das 
Stadtfeld alle Städte der Provinz &quot;Südliche Region&quot;. In diesem Fall würden Sie das Feld
&quot;Provinz&quot; als das <i>Hauptfeld</i> des Feldes &quot;Stadt&quot; markieren.<br>
Im Hauptfeld können Sie dann entscheiden, ob es für dieses Feld wiederum andere Hauptfelder geben soll. 
Bitte beachten Sie, dass das Hauptfeld nicht immer erscheint. Dies ist nur beim Datentyp Liste sichtbar. 
<br>
Für jeden der Werte des Hauptfeldes können Sie eine unterschiedliche Reihe von Werten für das Unterfeld
definieren, und zwar über das Fenster <a href="#possible_values"><u>Mögliche Werte</u></a> (erscheint 
nach Anklicken von &quot;Weiter&quot;). 

<hr class="help">

<a name="possible_values"></a>
<h3>Werte für benutzerdefiniertes Feld</h3>
Dieses Fenster zeigt eine Liste der für das Feld möglichen Werte.<br>
Löschen können Sie einen Wert durch Anklicken des Löschen-Symbols. Dies funktioniert allerdings nur dann, 
wenn der entsprechende Wert NICHT in Gebrauch ist. Es ist möglich, Werte zu &quot;leeren&quot;, indem 
Sie alle verwendeten Werte einem anderen auf der Liste zuordnen (dies ist in &quot;Wert bearbeiten&quot; 
erklärt). 
<br><br>
Hat Ihr Feld ein  <a href="#parent_field"><u>Hauptfeld</u></a>, sollten Sie zuerst den entsprechenden Wert 
für das Hauptfeld, für das Sie neue Werte definieren möchten, wählen, bevor Sie &quot;Neue Werte&quot; 
anklicken. Dafür können Sie die Auswahlschaltfläche zwischen  &quot;Zurück&quot; und &quot;Neuer Wert&quot; 
verwenden. 

<hr class="help">

<a name="edit_possible_value"></a>
<h3>Neuen Wert einfügen/bearbeiten</h3>
<br><br>Die folgenden Optionen sind verfügbar: 

<ul>
	<li><b>Name des Hauptfeldes:</b> Hat Ihr Feld ein  <a	href="#parent_field"><u>Hauptfeld</u></a>, 
	  so erscheint in diesem Label der Wert	des Hauptfeldes, dem Ihre neuen Werte zugeordnet werden.<br>
	  (Falls Sie neue Werte für andere Werte des Hauptfeldes definieren möchten, gehen Sie bitte 
	  zurück zu <a href="#possible_values"><u>Mögliche Werte</u></a>.
	<li><b>Wert:</b> hier spezifizieren Sie den Namen eines Wertes. Geben Sie den Wert ein, und wählen 
	  Sie dann &quot;Weiter&quot;. Der Wert erscheint dann in alphabetischer Reihenfolge in der Werteliste. 
	<li><b>Standard:</b> Wird dies gewählt, so erscheint dieser Wert als Vorauswahl im gezeigten 
	  Formular. Pro benutzerdefiniertem Feld kann es nur einen als Standard eingestellten Wert geben. 
	<li><b>Freigegeben:</b> Wird dies gewählt, so erscheint der Wert als mögliche Auswahloption. 
	  Wird Freigegeben nicht gewählt, so erscheint der Wert nur dann, wenn sich darin Daten befinden. 
	  So können sich so entscheiden, einen in der Vergangenheit verwendeten Wert zwar nicht mehr zu verwenden, 
	  die darin enthaltenen Daten aber zu bewahren. 
	<li><b>Ersetze das Vorkommen von: (Nur im Bearbeitungsmodus)</b> Wenn Sie ein Feld bearbeiten,
	  können Sie die Werte aller Felder, die Daten dieses Wertetyps enthalten, einem anderen zuordnen. 
	  Auf diese Weise ist es möglich, einen Wert aus einer Wertelisten-Seite zu entfernen (das geht nur, 
	  wenn der Wert nicht verwendet wird). Falls Sie existierende Werte nicht mehr verwenden möchten, können Sie 
	  auch wie weiter oben erklärt den Wert deaktivieren.<br>
    (Werte entfernen können Sie auf der Seite <a href="#possible_values"><u>Mögliche Werte</u></a>). 
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
<br><br>
</div>