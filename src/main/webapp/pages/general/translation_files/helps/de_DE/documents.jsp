<div style="page-break-after: always;">
<a name="top"></a>
<br><br>Dokumente sind Seiten mit Informationen, die im Mitgliedsbereich 
von Cyclos angezeigt werden können.
Das Mitglied kann ein Dokument von einer Liste wählen.<br><br> 
Es gibt zwei Arten von Dokumenten. &quot;Statische&quot; und &quot;Dynamische&quot;
Dokumente.<br>
<ul>
<li><b>Statische Dokumente</b> sind ganz einfach Dateien, wie zum Beispiel PDF-Dateien, und können 
einem einzelnen Mitglied oder einer Mitgliedergruppe zugeordnet sein.</li>
<li><b>Dynamische Dokumente</b> sind Html-Dokumente, die einer oder mehreren Gruppen zugeordnet sein können. 
Diese Dokumente können Formularseiten für eine Benutzereingabe enthalten, ebenso können
Profilfelder der Mitglieder angezeigt werden.
</li>
</ul>
<span class="admin">
Ein typisches benutzerdefiniertes Dokument wäre z.B. ein Darlehensabkommen oder irgendeine Anfrage, 
die das Mitglied dazu verwenden kann, etwas bei der Administration anzufordern.<br>
Ein dynamisches Dokument erscheint direkt, wenn ein Mitglied es öffnet; als Alternative ist 
es auch möglich, zuerst ein Formular zu zeigen, das vom Mitglied ausgefüllt werden muss. 
Wenn das Mitglied das Formular einreicht, so kann das resultierende Dokument sowohl die 
Eingabe des Mitglieds als auch die Profilfelder des Mitglieds enthalten.

<br><br><i>Wo ist es zu finden?</i><br>
Dokumente finden Sie unter dem &quot;Menü: Content Management >
Dokumente&quot;. Ein Beispiel für die Erstellung eines dynamischen Dokuments finden 
Sie im Cyclos Wiki, im Abschnitt&nbsp;&quot;Configuration & customation - Dynamic documents&quot; 
(Konfiguration & Anpassungen - Dynamische Dokumente).
<br>
Bereits existierende individuelle Mitgliedsdokumente sind zugänglich über das <a
href="${pagePrefix}profiles"><u>Profil</u></a> eines Mitglieds (Abschnitt
&quot;Mitglied-Informationen&quot;).

<br><br><i>Wie kann die Verwaltung der Dokumente aktiviert werden?</i><br>
Bevor Sie daran gehen können, Dokumente zu erstellen, müssen Sie zuerst die entsprechenden 
<a href="${pagePrefix}groups#manage_group_permissions_admin_member"><u>Berechtigungen</u></a> einstellen. 
Dies können Sie unter dem Abschnitt &quot;Dokumente&quot;, mittels mehrerer Kontrollkästchen tun.
Sobald Sie diese Berechtigungen eingerichtet haben, können Sie ein neues Dokument erstellen, 
über das &quot;Menü: Content Management > Dokumente&quot;.
<br><br>
<b>Anmerkung 1:</b>Für jedes eingerichtete Dokument muss die Sichtbarkeit in den
<a href="${pagePrefix}groups#manage_group_permissions_member"><u>Berechtigungen</u></a> der 
Gruppe, Abschnitt &quot;Dokumente&quot; eingestellt werden. Dies bedeutet, dass Dokumente bestimmten 
Mitgliedergruppen zugeordnet werden. Es ist möglich, ein Dokument so einzustellen, dass es nur 
für die Administratoren sichtbar ist, oder nur für die Administratoren und Broker, oder dass 
es für Administratoren, Broker und das Mitglied selbst sichtbar ist (Mitglieder können 
niemals die Dokumente anderer sehen).<br><br>
<b>Anmerkung 2:</b> In Cyclos gibt es nicht so etwas wie Administrator-Dokumente.
</span>
<span class="member">
<br><br><i>Wo ist es zu finden?</i><br>
Dokumente können über &quot;Menü: Persönlich > Dokumente&quot; gezeigt werden.
</span>
<hr>

<span class="admin"> <a name="document_list"></a>
<h3>Liste benutzerdefinierter Dokumente</h3>
Diese Seite zeigt eine Liste aller verfügbaren benutzerdefinierten 
<a href="#top"><u>Dokumente</u></a>. 
Neben dem Namen des Dokuments zeigt die Liste folgendes:
<ul>
	<li><b>Typ:</b> zeigt den <a href="#top"><u>Dokument-Typ</u></a>.
	<li><img border="0" src="${images}/edit.gif" width="16" height="16">&nbsp;
	Klicken Sie das Bearbeiten-Symbol, um das Dokument zu ändern.
	<li><img border="0" src="${images}/view.gif" width="16" height="16">&nbsp;
	Klicken Sie auf das Anzeigen-Symbol, um das Resultat zu zeigen.
	<li><img border="0" src="${images}/delete.gif" width="16" height="16">&nbsp;
	Klicken des Löschen-Symbols löscht das Dokument.
</ul>
Um ein neues Dokument zu erstellen, müssen Sie eine der beiden Schaltflächen im 
unteren Teil des Fensters (&quot;Neues dynamisches Dokument&quot; oder 
&quot;Neues statisches Dokument&quot;) verwenden.
<hr class="help">
</span>

<span class="admin"> <a name="new_edit_static_document"></a>
<h3>Einfügen / ändern eines neuen statischen Dokuments</h3>
Dies ermöglicht Ihnen das Einfügen eines <a href="#top"><u>neues statisches
Dokuments</u></a>. Das Formular ist recht einfach: tragen Sie bitte einfach 
einen Namen und eine Beschreibung des Dokuments ein, die Datei kann mit der Schaltfläche
&quot;Durchsuchen...&quot; von Ihrem lokalen Computer hochgeladen werden.<br>
Klicken Sie auf &quot;Weiter&quot;, um die Datei zu speichern.
<br><br>Für die Dokumentendatei ist jedes Format geeignet. Wenn Sie eine bereits existierende 
Datei ändern wollen, finden Sie die derzeit aktuelle Datei unter dem Link &quot;
Aktuelle Datei&quot; klicken Sie darauf, um die gegenwärtige Version des Dokuments zu zeigen.
<br><br><b>Vorsicht</b>: Erstellen eines Dokuments bedeutet noch nicht, dass Ihre 
Mitglieder/Benutzer es auch ansehen können. Nach dem Erstellen des Dokuments sollten Sie auch 
die <a	href="${pagePrefix}groups#manage_group_permissions_member"><u>Mitgliedsberechtigungen</u></a> 
zum Lesen der Dokumente einstellen, indem Sie das neue Dokument in einem Auswahlfeld unter 
im Abschnitt Dokumente der Gruppen-Berechtigungen auswählen.
<hr class="help">
</span>


<span class="admin"> <a name="new_edit_dynamic_document"></a>
<h3>Einfügen / ändern eines neuen dynamischen Dokuments</h3>
Dies ermöglicht Ihnen das Einfügen eines <a href="#top"><u>neuen dynamischen Dokuments</u></a>. 
Auf dem Formular finden Sie die folgenden Elemente:
<ul>
	<li><b>Name:</b> Der Name des Dokuments.
	<li><b>Beschreibung:</b> Die Beschreibung des Dokuments (nur für Administrationszwecke)
	<li><b>Formularseite:</b> Es kann sein, dass das Dokument zunächst Eingaben seitens des 
	Benutzers benötigt, damit es gedruckt werden kann. Auf dieser Seite können Sie eine 
	Html-Seite mit Formular erstellen, um die notwendigen Benutzer-Eingaben abzufragen. 
	Wenn Sie diese Benutzer-Eingaben nicht benötigen, können Sie diese auch frei lassen.
	<li><b>Dokumentseite:</b> Hier können Sie eine Dokumentseite im Html-Format schreiben. 
	Haben Sie im Bearbeitungsfeld oben eine Formularseite definiert, so können Sie die 
	Benutzereingaben dieser Seite hier einschließen. Die Dokumentseite öffnet sich in einem 
	Pop-Up-Fenster mit einer Schaltfläche für &quot;Drucken&quot; und &quot;Schließen&quot;. 
	Sie können außerdem auch Bilder einfügen. Diese müssen Sie zuerst im Bereich 
	&quot;<a href="${pagePrefix}content_management#custom_images"><u>Benutzerdefinierte Bilder</u></a>&quot; 
	hochladen.
</ul>
<br><br>Anmerkung: Beispiele für dynamische Dokumente finden Sie im Cyclos Wiki, 
im Abschnitt &quot;Configuration & customation - Dynamic documents&quot; 
(Konfiguration & Anpassungen - Dynamische Dokumente), unter 
<a href="http://project.cyclos.org/wiki">project.cyclos.org/wiki</a>.
Nach dem Erstellen des Dokuments sollten Sie auch die <a href="${pagePrefix}groups#manage_group_permissions_member">
<u>Mitgliedsberechtigungen</u></a> zum Lesen der Dokumente einstellen, indem Sie das neue 
Dokument in einem Auswahlfeld unter dem Abschnitt Dokumente der Gruppen-Berechtigungen auswählen.
<hr class="help">
</span>

<a name="member_document"></a>
<h3>Dokument herunterladen</h3>
Dieses Fenster zeigt eine Liste der Dokumente, die den Mitgliedern von der Administration 
zur Verfügung gestellt wurden. Diese Dokumente können herunter geladen und gedruckt werden.
<br><br>Bei den Dokumenten handelt es sich üblicherweise um organisatorische Dokumente. 
Falls von der Administration so spezifiziert, kann das Dokument als erstes ein 
Formular zeigen, das von Ihnen verlangt, zusätzliche Information einzufügen.<br> 
<span class="broker admin">
Für Administratoren und Broker wird ebenfalls der Typ des Dokuments aufgelistet. 
Statische und dynamische Dokumente können über dieses Fenster nur angezeigt 
werden (gehen Sie bitte über das &quot;Menü: Content Management > Dokumente&quot; 
um sie zu verwalten); Mitgliedsdokumente werden hingegen über dieses Fenster verwaltet. 
In einem solchen Fall haben Sie die folgenden Optionen:
<ul>
	<li><img border="0" src="${images}/view.gif" width="16" height="16">&nbsp;
	Ermöglicht Ihnen, das Dokument zu zeigen.
	<li><img border="0" src="${images}/edit.gif" width="16" height="16">&nbsp;
	Ermöglicht Ihnen, das Dokument zu ändern.
	<li><img border="0" src="${images}/delete.gif" width="16" height="16">&nbsp;
	Ermöglicht Ihnen, das Dokument zu löschen.
</ul>
</span><hr class="help">

<span class="broker admin"> <a name="edit_member_document"></a>
<h3>Einfügen / ändern Mitgliedsdokument</h3>
Über dieses Fenster können Sie ein neues &quot;statisches&quot; Dokument für ein 
einzelnes Mitglied definieren. Dabei kann es sich um einen beliebigen Dateityp 
handeln, wie z.B. PDF oder um ein Bild. Wenn Sie eine Änderung vornehmen möchten, 
können Sie das bisherige Dokument einfach überschreiben; dazu müssen Sie zuerst die 
Schaltfläche &quot;Bearbeiten&quot; anklicken, nun können Sie eine neue Datei hochladen.
Wenn Sie fertig sind, klicken Sie auf die &quot;Weiter&quot;-Schaltfläche, um Ihre 
Änderungen zu speichern.
<ul>
	<li><b>Name:</b> einfach einen beschreibenden Namen eingeben.
	<li><b>Beschreibung:</b> nur für Administratoren sichtbar.
	<li><b>Sichtbarkeit:</b> hier können Sie wählen, für welche Benutzertypen dieses 
	Dokument sichtbar sein wird. Wenn Sie hier &quot;Mitglied&quot; wählen, wird das Dokument 
	auch für das Mitglied sichtbar sein. Wählen Sie &quot;Broker&quot; (und berechtigter 
	Administrator) kann nur der Broker das Dokument sehen. Und, ganz folgerichtig, 
	wenn Sie &quot;Administratoren&quot; wählen, kann das Dokument nur von Administratoren 
	gelesen werden.
	<li><b>Aktuelle Datei:</b> ist die derzeit aktuelle Datei. Den Link können Sie anklicken, 
	um sie anzuzeigen. Hier wird nichts sichtbar sein, wenn Sie das Fenster dazu 
	verwenden, ein neues Mitgliedsdokument zu erstellen.
	<li><b>Datei hochladen:</b> Hier einfach den vollständigen Pfadnamen der Datei eingeben. 
	Hierzu können Sie die &quot;Durchsuchen...&quot; Schaltfläche benutzen.
</ul>
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

