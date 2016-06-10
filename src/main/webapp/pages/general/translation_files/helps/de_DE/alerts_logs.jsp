<div style="page-break-after: always;">
<span class="admin"> 
<br><br>Diese Meldungen sind dazu da, den Administrator über verschiedene 
Ereignisse zu informieren, wie z.B. Systemfehler, Vorgänge in Zusammenhang mit 
Mitgliedern (Anzahl der Anmeldeversuche für ein Konto, Erreichen des Kreditlimits etc.).

<i>Wo ist es zu finden?</i><br>
Meldungen und Protokolle sind zugänglich über &quot;Menü: Meldungen&quot;.<br>
<br><br><i>Wie werden die Meldungen aktiviert?</i><br>
<a
	href="${pagePrefix}alerts_logs#system_alerts"><u>Systemmeldungen</u></a> sind stets aktiviert.<br>
	<a
	href="${pagePrefix}alerts_logs#member_alerts"><u>Mitgliedermeldungen</u></a>
	können in <a
	href="${pagePrefix}settings#alerts"><u>Meldungseinstellungen</u></a> 
	konfiguriert werden.
<hr>

<a NAME="system_alerts"></a>
<h3>Systemmeldungen</h3>
Das Fenster Systemmeldungen zeigt eine Liste der Systemmeldungen. Dabei kann es sich um Meldungen 
in Zusammenhang mit der Software des Systems handeln, ebenso um Meldungen, die nicht direkt 
mit den Mitgliedskonten zu tun haben. Die folgenden Meldungen sind verfügbar: 
<ul>
	<li>Anwendung gestartet
	<li>Anwendung beendet
	<li>Kontogebühren gestartet
	<li>Kontogebühr abgebrochen
	<li>Kontogebühr beendet
	<li>Kontogebühr fehlgeschlagen
	<li>Kontogebühr eingezogen
</ul>
Meldungen können entweder mit dem <img border="0" src="${images}/delete.gif"
	width="16" height="16">&nbsp;<i>Symbol Entfernen </i>auf der rechten Seite der Meldungen, 
oder aber durch Auswahl einer oder mehrerer Meldungen und Klicken der Schaltfläche 
&quot;Ausgewählte entfernen&quot; gelöscht werden. Diese Meldungen werden daraufhin von der Liste entfernt.
Es gibt allerdings ein Fenster <a href="#alerts_history"><u>Meldungsverlauf</u></a>, in dem Sie nach alten, 
von der Liste entfernten Meldungen suchen können. Dies erlaubt es, Wiederholungen und Muster der Meldungen zu erkennen.
<hr class='help'>

<a name="member_alerts"></a>
<h3>Mitgliedsmeldungen</h3>
Das Fenster Mitgliedsmeldungen zeigt eine Liste der Meldungen im Zusammenhang mit dem Verhalten der 
Mitglieder. Die Grenzwerte können auf der Seite &quot;Menü: Einstellungen >   
<a href="${pagePrefix}settings#alerts"><u>Meldungseinstellungen</u></a >&quot;
eingestellt werden. Zur Zeit sind die folgenden Meldungen verfügbar:
<ul>
	<li>Mitglieder erhalten eine Anzahl von sehr schlechten <a href="${pagePrefix}references"><u>	Referenzen</u></a>
	<li>Mitglieder geben eine Anzahl von sehr schlechten <a href="${pagePrefix}references"><u>	Referenzen</u></a> 
	<li>Angabe der Zeit, während derer ein Mitglied nicht auf eine eingegangene Rechnung (nur System an Mitglied) reagiert. </u>
	<li>Anzahl der zurückgewiesenen Rechnungen
	<li>Überschreitung der maximalen Anzahl von inkorrekten Benutzernamen (jemand versucht mehrere Male sich mit falschem Benutzernamen anzumelden). 
	<li>Aufgrund von Überschreitung der maximalen Anmeldeversuche vorübergehend gesperrte Benutzer  
	<li>Überschreitung der maximalen Anzahl von inkorrekten Kennwörtern (jemand versucht mehrere Male sich mit richtigem Benutzernamen, aber falschem Kennwort anzumelden). 
	<li>Neue Mitglieder (Selbstregistrierung) auf der Anmeldeseite. 
	<li>Ablauf eines nicht zurück gezahlten Darlehens. 
</ul>
Sie können das <img border="0" src="${images}/delete.gif" width="16"
	height="16">&nbsp;<i>Löschen-Symbol</i> verwenden, um die Meldung von der Liste zu entfernen.
Es gibt allerdings ein Fenster <a href="#alerts_history"><u>Meldungsverlauf</u></a>, in dem Sie nach
alten, von der Liste entfernten Meldungen suchen können. Dies erlaubt es, Wiederholungen und Muster der Meldungen zu erkennen.
<hr class='help'>

<A NAME="alerts_history"></A>
<h3>Meldungsverlauf</h3>
Dieses Fenster ermöglicht Ihnen die Suche nach alten, gelöschten Meldungen. Falls 
Sie sich kürzlich erfolgte Meldungen anschauen möchten, gehen Sie bitte zu &quot;Menü: 
Meldungen > Systemmeldungen&quot; und &quot;Menü: Meldungen > Mitgliedsmeldungen&quot;. 
Neue Meldungen (solche, die noch nicht von der Liste gelöscht wurden) erscheinen nicht 
im Meldungsverlauf.<br><br>
Wenn Sie die Eingabefelder nicht ausfüllen, erhalten Sie eine Übersicht aller Mitgliedsmeldungen 
oder Systemmeldungen. Wenn Sie den Meldungstyp &quot;Mitglied&quot; auswählen, so erscheinen 
die Benutzernamen der Mitglieder in der Meldungsliste. Außerdem ist es möglich, nach Meldungen für ein 
bestimmtes Mitglied zu suchen. Dies können Sie tun, indem Sie die Felder Benutzername oder Name 
ausfüllen (Auto-Vervollständigung). 
<hr class='help'>

<a name="alerts_history_result"></a>
<h3>Meldungsverlauf Suchergebnisse</h3>
Dieses Fenster zeigt alle alten Meldungen, entsprechend Ihrer in obigem Fenster festgelegten Suchkriterien. 
Es kann sein, dass mehr als eine Seite verfügbar ist. Unterhalb des Fensters haben Sie die Möglichkeit, 
weitere Seiten anzuschauen.<br>
Falls Sie sich kürzlich erfolgte Meldungen anschauen möchten, gehen Sie bitte 
zu &quot;Menu: Meldungen > Systemmeldungen&quot; und &quot;Menü: Meldungen > Mitgliedsmeldungen&quot;. 
Neue Meldungen (solche, die noch nicht von der Liste gelöscht wurden) erscheinen nicht im Meldungsverlauf.
<hr>
<A NAME="error_log"></A>
<h3>Fehlerprotokoll</h3>
Diese Seite zeigt eine Liste aller Anwendungsfehler. Diese Fehler können Sie hier öffnen und direkt von 
der Liste löschen. Falls Sie einen Fehler löschen, ist dieser immer noch auf der Seite 
<a href="#error_history"><u>Fehlerverlauf</u></a> verfügbar.
<hr class='help'>

<a name="error_history"></a>
<h3>Fehlerverlauf</h3>
Auf dieser Seite können Sie einen Zeitrahmen festlegen, um die <a	href="#error_history_result">
<u>Suchergebnisse</u></a> zu begrenzen. Definieren Sie den Zeitraum, indem Sie das Anfangsdatum und 
das Enddatum eingeben, entweder indem Sie die Daten mit dem angegebenen Format in das Eingabefeld 
eingeben, oder aber über Anklicken des Kalendersymbols. 
<hr class="help">

<A NAME="error_history_result"></A>
<h3>Fehlerverlauf Suchergebnisse</h3>
Diese Seite zeigt eine Liste aller Anwendungsfehler innerhalb der im oberen Fenster 
<a href="#error_history"><u>Fehlerprotokollverlauf</u></a> definierten Zeitspanne. Ist keine 
Zeitspanne definiert, so wird die vollständige Liste gezeigt. Einen Fehler können Sie direkt in 
der Liste öffnen. Die Resultate sind paginiert, Sie können daher die Seite durchsuchen, indem Sie 
die Zahlen rechts von &quot;Weiter zu Seite:&quot; anklicken. Fehler erscheinen nur dann in 
diesem Fenster, wenn sie von der Seite <a href="#error_log"><u>Fehlerprotokoll</u></a> gelöscht 
wurden (&quot;Menü: Meldungen > Fehlerprotokoll&quot;).
<hr class='help'>


<a name="error_log_details"></a>
<h3>Fehlerdetails</h3>
Diese Seite zeigt eine Liste mit Details zu den Anwendungsfehlern. 
Diese Informationen helfen dem Administrator und den Entwicklern von Cyclos dabei, 
die Ursache des Fehlers zu erkennen. 
<br><br><b>Anmerkung:</b> Ein Anwendungsfehler ist nicht notwendigerweise ein Softwarefehler. 
Die Flexibilität der Cyclos-Konfigurationen resultiert manchmal in der Einstellung von 
Konfigurationen mit widersprüchlichen Funktionen. Die meisten Fehler dieser Art werden 
in Cyclos durch spezifische Fehlermeldungen abgefangen, allerdings ist es unmöglich alle 
denkbaren Konfigurationsfehler vorauszusehen. 
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