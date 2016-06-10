<div style="page-break-after: always;">
<a name="top"></a>
<br><br>Die Funktion Operatoren ermöglicht einem Mitglied, 
Operatoren zu definieren: einer Art Unter-Mitglieder, die die Erlaubnis haben, 
in Cyclos einige Aufgaben für andere Mitglieder zu übernehmen. Operatoren haben 
kein eigenes Konto, sondern haben lediglich Zugang zum Konto ihres Mitglieds, wo 
sie einige Tätigkeiten ausführen können. Stellen Sie sich eine Firma mit drei 
Angestellten vor, in der jeder Angestellte zum Operator für das Firmenkonto 
wird.<br>
Genau wie Mitglieder auch, sind Operatoren in Gruppen organisiert. Jedes Mitglied 
kann seine eigenen Operatorengruppen definieren; dies ermöglicht es jedem Mitglied, 
verschiedene Operator-Ebenen mit unterschiedlichen Berechtigungen zu erstellen. Sie 
könnten z.B. einen Super-Operator einrichten, der Zahlungen tätigen darf, und einige 
einfache Operatoren, die lediglich Inserate verwalten dürfen. Jede durch einen Operator 
getätigte Zahlung weist ein Extrafeld &quot;Ausgeführt von&quot; auf, und das Mitglied 
kann Zahlungen nach Operatoren durchsuchen.

<span class="member notOperator"> 
<i>Wo ist es zu finden?</i><br>
Die Funktion Operatoren finden Sie über das Hauptmenü &quot;Menü: Operatoren&quot;. Unter 
diesem Abschnitt des Hauptmenüs gibt es mehrere Untermenüs, die Zugang zur 
Operatoren-Funktionalität bieten. 
<ul>
	<li><b>Operatoren:</b> führt Sie zu einem Suchfenster nach Operatoren. Hier können Sie 
	auch neue Operatoren erstellen. 
	<li><b>Angemeldete Operatoren:</b> zeigt, welche Operatoren angemeldet sind. 
	<li><b>Operatorengruppen:</b> ermöglicht Ihnen, verschiedene Ebenen von Operatoren 
	zu definieren. 
	<li><b>Felder anpassen:</b> ermöglicht Ihnen, besondere benutzerdefinierte Felder für 
	Operatoren zu erstellen. 
</ul>
</span>
Operatoren können sich über die normale Anmeldeseite anmelden, die einen besonderen Link 
&quot;Anmelden als Operator&quot; enthält (das Operatoren-Modul muss in den Systemeinstellungen 
aktiviert sein, damit dieser Link zu sehen ist).<br>
Operatoren kann außerdem Zugang zum POS-Web-Modul erteilt werden (Information zu POS-Web finden Sie in 
der <a href="${pagePrefix}payments#accesing_channels"><u>Hilfe für Kommunikationswege</u></a>.
<br><br>
<span class="member"> Operatoren können ihre Aktionen in Zusammenhang mit dem Mitglied von einem
besonderen Hauptmenü-Eintrag (&quot;Operatoren-Hauptmenü&quot;) aus durchführen, das nur für Operatoren sichtbar ist. 
Über dieses Menü haben Operatoren Zugang zu allen Funktionalitäten, die normalerweise für das Mitglied 
unter &quot;Menü: Persönlich&quot; zu finden sind.</span>
<br>
<span class="admin">
<i>Wie werden Operatoren aktiviert?</i><br>
Operatoren müssen zunächst über die  <a
	href="${pagePrefix}groups#manage_group_permissions_member"><u>Berechtigungen</u></a>
einer Gruppe aktiviert werden, im Abschnitt &quot;Operatoren&quot;, über das Kontrollkästchen  
&quot;Operatoren verwalten&quot;.<br>
Zusätzlich zur Einstellung der Berechtigungen, benötigen Operatoren, um sich anmelden zu können, 
eine Freigabe in den  <a href="${pagePrefix}settings#access"><u>
Zugangseinstellungen</u></a>, über das Kontrollkästchen  &quot;Operatorenanmeldung ermöglichen&quot;.
<br><br><b>Anmerkung:</b> Wenn Sie die Anmeldeseite anpassen, denken Sie bitte daran, den Code, den Sie für die 
Aktivierung der Operatoren verwenden, zu sichern. Andernfalls kann es sein, dass der Anmeldelink für die 
Operatoren nicht erscheint. 
<br><br><b>Anmerkung 2:</b>
Das Operatorenmodul und die Operationen liegen vollständig in der Verantwortung des Mitglieds. 
Ein Administrator kann keine Operatoren für ein Mitglied verwalten. Die einzige verwaltende Tätigkeit, 
die ein Administrator hier durchführen kann, ist es, die Operatoren von der Seite 
<a href="${pagePrefix}user_management#connected_users_result"><u>Angemeldete Benutzer</u></a> abzumelden.
</span>
<hr>

<span class="member notOperator">
<a name="search_operator"></a>
<h3>Operatorensuche</h3>
Auf dieser Seite können Sie nach Operatoren suchen (die sie selbst registriert haben). Die Funktion 
arbeitet genau wie die normale Mitgliedssuchfunktion. In der Gruppenauswahl können Sie entweder den 
Filter &quot;Alle Gruppen&quot; bestehen lassen, oder aber eine oder mehrere Gruppen für Ihre Suche 
auswählen.<br>
Klicken Sie auf &quot;Suchen&quot;, um die Ergebnisse Ihrer Suche zu zeigen. 
<br><br>
Hier können Sie auch einen neuen Operatore erstellen. Dies tun Sie, indem Sie über das Auswahlfeld 
unter diesem Fenster eine Operatorengruppe wählen (&quot;Neuen Operator erstellen&quot;). Dieses 
Auswahlfeld ist nur dann sichtbar, wenn es kein Fenster für Suchergebnisse gibt. 
<hr class='help'>
</span>

<span class="member notOperator"> <a name="search_operator_result"></a>
<h3>Ergebnisse Operatorensuche</h3>
Diese Seite zeigt eine Ergebnisliste der Operatorensuche. Anklicken des Namens oder des Benutzernamens 
des Operators öffnet die Profilseite. 
<hr class="help">
</span>

<span class="member notOperator"> <A NAME="create_operator"></A>
<h3>Operator erstellen</h3>
Auf dieser Seite können Sie einen neuen Operator erstellen. Alle mit einem roten Stern (*) markierten 
Felder sind Pflichtfelder.<br>
Nach Ausfüllen der Felder können Sie entweder direkt zum Profil gehen (Schaltfläche 
&quot;Speichern und Operatorenprofil öffnen&quot;), oder ein neues Operatorenprofil hinzufügen (Schaltfläche 
&quot;Speichern und neuen Operator hinzufügen&quot;). 

<hr class='help'>
</span>

<a name="operator_profile"></a>
<span class="member">
<h3>Operatorenprofil</h3>
Dieses Fenster zeigt das Profil des Operatoren. Bis auf wenige Ausnahmen können die Fenster nicht 
verändert werden. Klicken Sie bitte auf &quot;Bearbeiten&quot;, um Veränderungen vorzunehmen, sind Sie damit 
fertig, klicken Sie auf &quot;Weiter&quot;, um diese Änderungen zu speichern.</span>
<span class="member notOperator">
<br><br>Ist dieser Operator gerade angemeldet, wenn Sie seine Maske ansehen, so erscheint darüber eine 
Benachrichtigung. Auf dem Feld &quot;Letzte Anmeldung&quot; erscheint (in Rot) &quot;Ist angemeldet&quot;. 

<hr class='help'>
</span>

<span class="member notOperator">
<A NAME="actions_for_operator_by_member"></A>
<h3>Aktionen für Operatoren</h3>
Hier können Sie zu diesem Operatoren verschiedene Aktionen durchführen. Diese Hilfe zeigt eine 
Zusammenfassung aller Aktionen. Für eine detaillierte Erklärung der Aktionen, gehen Sie bitte 
zur Hilfe innerhalb des spezifischen Aktionsfensters. 
<br><br>Die folgenden Aktionen sind verfügbar:
<UL>
	<LI><b>Gruppe ändern:</b> Ändern der Operatorengruppe, der dieser Operator angehört. 
	<LI><b>E-Mail senden:</b> Versenden einer E-Mail an den Operator. Dies öffnet Ihr lokales E-Mail-Programm. 
	<LI><b>Kennwort verwalten:</b> Änderung des Kennworts für den Operator.
	<li><b>Benutzer darf sich jetzt anmelden:</b> Dies erscheint nur dann, wenn der Operator bereits mehrfach 
	versucht hat, sich mit dem falschen Kennwort anzumelden, und sein Konto daher zeitweilig gesperrt wurde. 
	Normalerweise ist eine maximale Anzahl von Versuchen erlaubt; falls Sie sich mit dem falschen Kennwort 
	häufiger anzumelden versuchen, wird Ihr Konto zeitweilig gesperrt. Die Dauer der Sperrung wird von den 
	Administratoren eingestellt. Wenn Sie sicher sind, dass dieser Operator der ist, für den er sich ausgibt, 
	können Sie ihm durch Anklicken dieser Schaltfläche die sofortige Anmeldung ermöglichen. In diesem Fall 
	muss der Operator mit der Neuanmeldung nicht warten, bis die Sperrzeit vorüber ist. 
	<li><b>Benutzer abmelden:</b> Dies erscheint nur dann, wenn der Operator genau zu diesem Zeitpunkt 
	angemeldet ist. Diese Angabe finden Sie auch im Feld &quot;Letzte Anmeldung&quot; im Profilfenster oben. 
	Darin erscheint dann  &quot;Ist angemeldet&quot;. In diesem Fall können Sie den Operator zwingen, 
	das Programm zu verlassen, indem Sie diese Schaltfläche anklicken. Der Grund für diese Vorgehensweise 
	kann zum Beispiel eine anstehende Untersuchung eines Missbrauchsvorwurfs sein, oder wenn der  
	Operator sich nicht anmelden kann, weil das System ihn für bereits angemeldet hält.
</UL>
<hr class='help'>
</span>

<span class="member notOperator">
<A NAME="manage_operator_groups"></A>
<h3>Operatorengruppe verwalten</h3>
Diese Seite zeigt eine Liste aller <a href="#top"><u>Operatoren</u></a>-Gruppen. 
Hier können Sie die folgenden Aktionen ausführen:
<ul>
	<li><img border="0" src="${images}/edit.gif" width="16" height="16">&nbsp;
	Das Bearbeitungs-Symbol führt Sie zu einer Seite mit den Einstellungen für diese Gruppe.
	<li><img border="0" src="${images}/permissions.gif" width="16" height="16">&nbsp;
	Das Berechtigungs-Symbol führt Sie zu einer Seite, auf der Sie die Berechtigungen für 
	diese Gruppe einstellen können. Hat die Gruppe den Status &quot;Entfernt&quot;, ist dieses Symbol 
	gesperrt (ausgegraut, <img border="0" src="${images}/permissions_gray.gif" width="16" height="16">&nbsp;).
	<li><img border="0" src="${images}/delete.gif" width="16" height="16">&nbsp;
	Anklicken des Löschen-Symbols entfernt die Gruppe. Sie können Gruppen nur dann entfernen, wenn sich 
	darin keine Mitglieder (Operatoren) befinden. 
	<li><b>Hinzufügen:</b> Um eine neue Operatorengruppe hinzuzufügen, klicken Sie bitte auf die 
	&quot;Neue Gruppe einfügen&quot; und dann auf &quot;Weiter&quot;. 
</ul>
<hr class='help'>
</span>

<span class="member notOperator">
<A NAME="manage_group_permissions_basic"></A>
<h3>Basis-Gruppenberechtigungen</h3>
In diesem Fenster können Sie die Basisberechtigungen einstellen. Die Basiseinstellungen haben 
keine Auswirkung auf andere Funktionen, wenn z.B. ein Operator sich nicht anmelden kann, kann 
er doch möglicherweise weiterhin Zahlungen erhalten. Die folgenden Berechtigungen können 
eingestellt werden: 
<ul>
	<li><b>Anmelden:</b><br>
	Ist hier kein Häkchen gesetzt, können sich Operatoren dieser Gruppe nicht anmelden. 
	<li><b>Mitglied einladen:</b><br>
	Ist hier ein Häkchen gesetzt, so sehen Mitglieder dieser Gruppe eine Fensterbox auf ihrer 
	Hauptseite (nach der Anmeldung), mit der sie einen Freund dazu einladen können, Ihre 
	Organisation kennen zu lernen.
</ul>
<hr class='help'>
</span>

<span class="member notOperator"> <A
	NAME="manage_operator_group_permissions"></A>
<h3>Berechtigungen Operatorengruppe verwalten</h3>
In diesem Fenster können Sie die  <a href="${pagePrefix}groups#permissions"><u>
Berechtigungen</u></a> für eine <a href="#top"><u>Operatoren</u></a>-Gruppe einstellen. 
Diese Berechtigungen leiten sich von Ihren eigenen Gruppenberechtigungen ab: ein Operator 
kann niemals mehr tun als Sie selbst dürfen, sondern lediglich gleich viel oder weniger. 
Aus diesem Grund sehen Sie wahrscheinlich nicht alle Optionen in dieser Hilfe-Übersicht 
aufgelistet. Verwenden Sie die Links, um mehr Informationen zu diesem Punkt zu 
erhalten.<br><br> 
Die zu dieser Gruppe gehörenden Operatoren können diese Berechtigungen erhalten (je nach 
Systemeinstellungen und Ihren eigenen Berechtigungen). 
<br><br>
<b>Mitgliedskonto</b>
<ul>
	<li><b>Autorisierte Zahlungen anzeigen</b>
	<li><b>Geplante Zahlungen anzeigen</b>
	<li><b>Kontoinformationen anzeigen:</b>
	Verwenden Sie das Auswahlfeld, um zu wählen, für welches Konto der Operator Informationen 
	zeigen kann (Zahlungen, Saldo usw.). 
</ul>
<b>Inserate</b>
<ul>
	<li><b>Veröffentlichen:</b> Wenn Sie &quot;Veröffentlichen&quot; wählen, kann ein Operator 
	Inserate veröffentlichen, und das Menü-Element &quot;Persönlich > Inserate&quot; erscheint 
	im Operatoren-Menü. 
</ul>
<b>Kontaktliste</b>
<ul>
	<li><b>Verwalten:</b> ermöglicht dem Operator, die <a	href="${pagePrefix}user_management#contacts">
	<u>Kontaktliste</u></a> zu verwalten, ihr also Mitglieder 
	hinzuzufügen, sie zu bearbeiten oder Mitglieder von der Liste zu löschen. 
	<li><b>Ansicht:</b> ermöglicht dem Operator die Kontaktliste nur anzusehen (und zu verwenden), 
	er hat allerdings keine Berechtigung zur Bearbeitung. 
</ul>
<b>Sicherheiten</b><br><br>
Dies ist Teil des Sicherheiten-Systems von Cyclos, in dem jedes Saldo durch einen Deckungsbetrag
garantiert ist. Sie können die folgenden Berechtigungen wählen: 
<ul>
	<li><b>Kaufen mit Zahlungsverpflichtung</b></li>
	<li><b>Verkaufen mit Zahlungsverpflichtung</b>
	<li><b>Zugelassene Sicherheitstypen (für Zertifizierungen)</b></li>
	<li><b>Zertifizierungen ausstellen</b></li>
	
</ul>
<b>Rechnungen</b> <br><br>
In diesem Abschnitt können Sie definieren, ob ein Operator  <a
href="${pagePrefix}invoices"><u>Rechnungen</u></a> an andere Mitglieder schicken kann, 
entweder von einem Benutzerprofil aus, oder direkt über das Kontomenü. Wird 
&quot;Systemrechnungen&quot; gewählt, so kann ein Operator vom &quot;Kontomenü&quot; 
aus Rechnungen an Systemkonten schicken. 
<ul>
	<li><b>Mitgliedsrechnung vom Menü:</b> Zeigt die Option &quot;Rechnung an Mitglied&quot; im Menü. 
	<li><b>An Mitglied senden:</b> Ermöglicht es, Rechnungen an andere Mitglieder zu versenden. 
	<li><b>An System senden:</b> Ermöglicht es, Rechnungen an ein Systemkonto zu versenden. 
	<li><b>Ansicht:</b> Rechnungen anzeigen.
</ul>

<li><b>Darlehen:</b> In diesem Abschnitt können Sie die Berechtigungen von
<a href="${pagePrefix}loans"><u>Darlehen</u></a> für Operatoren einstellen.
<ul>
	<li><b>Ansicht:</b> Wird die Option &quot;Ansicht&quot; gewählt, können die Operatoren 
	der Gruppe deren Darlehen ansehen. Wird &quot;Anzeigen&quot; nicht gewählt, erscheinen 
	die Menüelemente nicht. 
	<li><b>Zurückzahlen:</b> Wählen dieser Option ermöglicht dem Operator, Darlehensrückzahlungen zu tätigen. 
</ul>

<li><b>Nachrichten:</b> In diesem Abschnitt können Sie definieren, in welchem Umfang der Operator 
das Cyclos-<a href="${pagePrefix}messages"><u>Nachrichten</u></a>-System benutzen kann.
<ul>
	<li><b>Anzeigen:</b> Der Operator kann das Nachrichtensystem zeigen. 
	<li><b>An Mitglied senden:</b> Der Operator darf Nachrichten an andere Mitglieder versenden. 
	<li><b>An die Administration senden:</b> Der Operator darf Nachrichten an die Administration versenden.  
	<li><b>Verwalten:</b> Der Operator kann Nachrichten entfernen und löschen. 
</ul>


<b>Zahlungen:</b> Hier können Sie spezifizieren, welcher Zahlungstyp für diese Operatorengruppe zulässig ist. 
Meist werden Sie hier nur einen oder wenige Typen auswählen. 
<ul>
	<li><b>Eigenzahlung:</b> Wird dies gewählt, kann der Operator Zahlungen zwischen Ihren 
	eigenen Konten tätigen. Im Auswahlfeld können Sie die möglichen Überweisungstypen spezifizieren. 
	Diese Option macht nur dann Sinn, wenn Sie mehr als ein Operatorenkonto haben. 
	<li><b>Mitgliedszahlungen:</b> Wird dies gewählt, kann der Operator Zahlungen an ein anderes Mitglied tätigen. 
	<li><b>Mitgliedszahlung vom Menü:</b> Wird diese Option gewählt, können die Operatoren Zahlungen an andere 
	Mitglieder direkt über das Menü vornehmen. 
	<li><b>Systemzahlungen:</b> Wird dies gewählt, kann der Operator Einzahlungen auf ein Systemkonto 
	machen. Wird diese 	Option nicht gewählt, erscheint das Menü-Element 
	&quot;Systemzahlung&quot; nicht. 
	<li><b>POS-Web-Zahlung tätigen:</b> ermöglicht dem Operator POS-Web Zahlungen durchzuführen.
	<li><b>POS-Web-Zahlung empfangen:</b> Wählen Sie diese Option, wenn Sie den Operatoren 
	ermöglichen wollen, Zahlungen über POS-Web zu empfangen. Dabei handelt es sich normalerweise 
	um eine Situation im Verkaufsladen.  Der Verkäufer an der Verkaufstheke meldet sich in diesem 
	Fall als Operator (normalerweise über via http://..cyclos/posweb) auf dem POS-Web Interface an. 
	Im folgenden Fenster kann der Kunde seine PIN eingeben, um eine Zahlung an das Geschäft zu veranlassen.<br> 
  Anklicken dieses Kästchens ermöglicht dieses Verfahren. (Normalerweise würden Sie dann alle anderen 
  Berechtigungen für den Operator sperren.) 
	<li><b>Autorisieren oder ablehnen:</b> ermöglicht dem Operator eine Zahlung, deren Empfänger Sie sind, 
	zu autorisieren oder abzulehnen. 
	<li><b>Zahlungsautorisierung annullieren:</b> Werden geplante Zahlungen verwendet, ermöglicht dies den Operatoren, 
	ihre geplanten Zahlungen vor deren Beginndatum zu annullieren. 
	<li><b>Geplante Zahlung annullieren:</b> Werden geplante Zahlungen verwendet, ermöglicht dies den Operatoren, 
	ihre geplanten Zahlungen vor deren Beginndatum zu annullieren 
	<li><b>Geplante Zahlung sperren:</b> gestattet dem Operator, seine geplante Zahlungen zeitweilig zu sperren. 
	<li><b>Zahlungen von anderen Kommunikationswegen anfordern:</b> ist dieses Kästchen ausgefüllt, kann 
	der Operator Zahlungsaufforderungen (externe Rechnungen) über andere Kommunikationswege versenden; die Auswahl 
	dieser Kommunikationswege treffen Sie bitte über das Auswahlfeld. 
</ul>

<b>Referenzen</b> <br><br>
Dies gestattet dem Operator  <a	href="${pagePrefix}references"><u> Referenzen</u></a> zu verwalten.
<ul>
	<li><b>Ansicht:</b> Referenzen anzeigen.
	<li><b>Meine Referenzen verwalten:</b> gestattet dem Operator, das Referenzsystem zu verwenden, 
	inklusive der Berechtigung, anderen Mitgliedern Referenzen zu erteilen.  
	<li><b>Meine Feedbacks zu Geschäftsvorgängen verwalten: :</b> gestattet dem Operator, 
	Ihre <a href="${pagePrefix}transaction_feedback#feedbacks_summary">
	<u>Feedbacks zu Geschäftsvorgängen</u></a> zu verwalten, inklusive der Berechtigung, 
	Feedbacks zu Geschäftsvorgängen zu erteilen. 
</ul>

<b>Berichte</b><br><br>
Wird &quot;Meine Berichte anzeigen&quot; gewählt, kann der Operator Ihre  
<a href="${pagePrefix}reports#member_activities"><u> Berichte</u></a> anzeigen.
</ul>
<hr class='help'>
</span>

<span class="member notOperator"> <a name="edit_operator_group"></a>
<h3>Operatorengruppen ändern</h3>
Die Konfiguration der  <a href="#top"><u>Operator</u></a>-Gruppe ist in zwei Teile unterteilt:
<ul>
	<li><b>Gruppendetails:</b> Hier können Sie nur den Namen und die Beschreibung der 
	Operatorengruppe ändern. 
	<li><b>Maximaler Betrag pro Tag je Zahlungstyp:</b> Mit dieser Einstellung können Sie den 
	Maximalbetrag pro Tag je Zahlungstyp einstellen. Alle verfügbaren Zahlungstypen sind hier 
	gelistet. Für jeden Typ können Sie spezifizieren, ob es eine Begrenzung dafür gibt, wieviel 
	ein Operator über diesen Zahlungstyp zahlen kann. 
</ul>
Zu den Berechtigungen für diese Gruppe gelangen Sie direkt über Anklicken von 
&quot;Gruppenberechtigungen&quot;. 
<hr class='help'>
</span>

<span class="member notOperator">
<A NAME="insert_operator_group"></A>
<h3>Neue Operatorengruppe einfügen</h3>
Dieses Fenster ermöglicht Ihnen, eine neue <a href="#top"><u>Operator</u></a>-Gruppe einzurichten.
<br>
Sie haben die folgenden Optionen: 
<ul>
	<li><b>Entfernt:</b> Solch eine Gruppe richten Sie für Operatoren ein, die das System 
	bereits verlassen haben. Ist jemand erst einmal in dieser Gruppe, kann er niemals in 
	irgendeine Gruppe zurückkehren. Die Daten befinden sich zwar noch in der Datenbank, und
  können angeschaut werden, haben aber lediglich Backup-Funktion. 
	<li><b>Name:</b> Name der Gruppe.
	<li><b>Beschreibung:</b> Beschreibung der Gruppe.
	<li><b>Einstellungen kopieren von:</b> Dies ist nur dann sichtbar, wenn bereits eine 
	andere Operatorengruppe definiert wurde. Hier können Sie eine andere Gruppe spezifizieren; 
	die Einstellungen für die neue Gruppe werden von der von Ihnen spezifizierten Gruppe übernommen. 
</ul>
Nachdem Sie die neue Gruppe eingerichtet haben, sollten Sie in der nächsten Maske die 
Gruppeneigenschaften einstellen, ebenso die Gruppenberechtigungen. 
<hr class='help'>
</span>

<span class="member notOperator">
<a name="manage_group_customized_files"></a>
<h3>Benutzerdefiniertes POS-Web</h3>
Hier können Sie Kopf- und Fußzeile für das POS-Web definieren. Das Fenster zeigt eine Liste 
Anpassungsmöglichkeiten für diese Gruppe. Sie haben die folgenden Optionen: 
<ul>
	<li><b>Ändern</b> einer bestehenden benutzerdefinierten Datei über das Bearbeiten-Symbol 
	<img border="0"	src="${images}/edit.gif" width="16" height="16">&nbsp; bearbeiten.
	<li><b>Anzeigen</b> Anzeigen wie das Ergebnis für ein Mitglied der Gruppe aussieht, über das Anzeigen-Symbol
	<img border="0" src="${images}/view.gif" width="16" height="16">&nbsp;
	<li><b>Löschen</b> einer benutzerdefinierten Datei über das Löschen-Symbol <img border="0"
		src="${images}/delete.gif" width="16" height="16">&nbsp;.
	<li><b>Einfügen</b> einer neuen benutzerdefinierten Datei über die Schaltfläche 
	&quot;Neue Datei anpassen&quot;.
</ul>
<hr class="help">
</span>


<span class="member notOperator"> <a name="customize_group_file"></a>
<h3>Ändern der Kopf- und Fußzeile für das POS-Web</h3>
Hier können Sie Kopf- und Fußzeile für die POS-Web-Seite  anpassenen. Dies ist die Seite,
die Operatoren zugänglich ist, um Zahlungen zu tätigen und zu empfangen. (Die POS-Web–URL 
ist meist etwas wie www.domain.com/cyclos/posweb).<br>
Nachdem der Operator sich angemeldet hat, erscheinen Kopfzeile und Fußzeile über und 
unter dem Zahlungsfenster. 
<hr class="help">
</span>


<span class="member notOperator">
<A NAME="change_group_operator"></A>
<h3>Operatorengruppe ändern</h3>
In diesem Fenster können Sie einen <a href="#top"><u>Operator</u></a>
in eine andere Gruppe verschieben. Dies bedeutet, dass der Operator die Berechtigungen einer 
anderen Gruppe erhält. Nach Ausfüllen des Formulars klicken Sie bitte auf die Schaltfläche  
&quot;Gruppe ändern&quot;, um Ihre Änderungen zu speichern. 
<br>
<br><br>
Anklicken der Option &quot;Definitiv entfernen&quot; entfernt den Operator. Dies ist nur dann 
möglich, wenn der Operator noch keine Überweisungen getätigt hat. Andernfalls müssen Sie ihn 
in eine Gruppe die als &quot;Entfernt&quot; definiert wurde, verschieben, was bedeutet, dass der 
Operator keinerlei Aktionen mehr durchführen kann (auch nicht sich anmelden); seine zurückliegenden 
Aktionen bleiben aber weiterhin für Sie sichtbar. 
<hr class='help'>
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