<div style="page-break-after: always;">
<a name="guarantees_top"></a>
<br><br>
Das Garantie-Modul bietet einen Mechanismus wo &quot;zukünftige&quot;
Zahlungen für den Kauf von Waren oder Dienstleistungen, welche direkt
beim Anbieter (Verkäufer) als lokale Kaufkraft in einem Netzwerk, welches Cyclos 
als Zahlungssystem verwendet, verfügbar gemacht werden. Dies bedeutet, dass diese
&quot;erwartete&quot; lokale Kaufkraft für zukünftige Zahlungen 
(konventionelles Geld) unterstützt wird. Da der Verkäufer durch die Organisation
in lokaler Währung zahlt (auf die Lieferung der Ware), ist der Käufer
jetzt in Verpflichtung zur Organisation (und nicht an den Verkäufer). Ein
&quot;Aussteller&quot;, für gewöhnlich eine finanzielle Institution, garantiert diese
zukünftigen Zahlungen (vom Käufer an die Organisation). Im Fall, dass
der Käufer nicht zahlt oder nicht zahlen kann, ist der Aussteller verantwortlich, 
dass die Zahlung durchgeführt wird. Natürlich wird der Aussteller dafür
Kosten verrechnen. Diese Kosten sind im Allgemeinen viel geringer als vergleichbare Darlehen
auf dem Finanzmarkt.<br>Aussteller haben verschiedene Werkzeuge (in Cyclos) für
die Ausstellung und Überwachung der Garantien zur Verfügung. Sie können Käufer 
evaluieren (lokale
Regierungen, Unternehmen oder Einzelpersonen) und definierten einen maximalen
&quot;Ausgabe&quot;-Betrag und andere Bedingungen wie beispielsweise Gebühren und
Ablaufdatum. Das Garantiemodul ist sehr flexibel und unterstützt 
verschiedene Benutzer, definiert in den verschiedenen Garantietypen.


<i>Wo ist es zu finden?</i><br>Garantietypen können unter
&quot;Menü: Garantien > Garantietypen&quot; gefunden werden.<br>
<hr>


<a name="guarantee_models"></a>
<h3>Garantie-Module</h3>
Es gibt drei Typen von Garantiemodellen:<br>
<ul>
	<li><b>Garantien mit Zahlungsverpflichtungen:</b> Es ist üblich, dass größere Unternehmen oder lokale 
	Regierungen Waren und Dienstleistungen vom lokalen Anbieter kaufen und zu einem spätern Zeitpunkt (zum Beispiel in 
	einem, drei oder sechs Monaten) zahlen. Ein typisches Beispiel ist ein Supermarkt oder eine Gemeinde die 
	ihre lokalen Anbieter bezahlen. Das Garantiemodul mit Zahlungsverpflichtungen bietet Online-Werkzeuge für 
	die Verwaltung und Überwachung dieser Zahlungen. Der Prozess startet wenn ein Aussteller, üblicherweise ein Finanzinstitut,
	ein Unternehmen (Käufer) evaluiert und eine Zertifikation in Cyclos erstellt 
    wurde.<br>Die Zertifikation 
	definiert mögliche Gebühren und den maximalen Ausgabebetrag. Bis zu diesem Betrag kann ein Käufer 
	Zahlungsverpflichtungen an seine Aussteller &quot;veröffentlichen&quot;, üblicherweise eine Zahlungsverpflichtung je ausstehender Rechnung.  Wenn der
	Anbieter/Verkäufer entscheidet, direkt in lokaler Kaufkraft zu erhalten (anstatt auf die
	Zahlung zu einem spätern Zeitpunkt zu warten) kann er eine Zahlungsverpflichtung akzeptieren. Für den
	Verkäufer bedeutet dies einen signifikanten Unterschied, der er/sie direkten Zugang zum Geld hat (in 
	Form von lokaler Kaufkraft). Für den Käufer ist der einzige Unterschied mit Zahlungsverpflichtungen zu arbeiten,
	anstelle der Bezahlung des Anbieters mit einer Verzögerung bei herkömmlicher Währung, wird 
	nun an die Organisation bezahlt (mit der selben Verzögerung wie bei herkömmlicher Währung).</li><br>
	<li><b>Garantien nur mit Käufer: </b>Dieses Modell wird typischerweise verwendet, wenn Verkäufer seinen Kunden eine
	Finanzierung für einen einzelnen Kauf anbietet. Verkäufer und Käufer sind Teil des Prozesses.</li>
	<br>
	<li><b>Garantien mit Käufer und Verkäufer: </b>Dieses Modell wird typischerweise verwendet wenn Verkäufer seinen Kunden
	eine Finanzierung für einen einzelnen Kauf anbietet. Verkäufer und Käufer sind Teil des Prozesses.</li>
</ul>
<hr class="help">


<span class="admin">
<a name="guarantee_types_list"></a>
<h3>Liste der Garantietypen</h3>Dieses Fenster zeigt eine Liste aller konfigurierten Garantietypen.<br>
</span>
<hr class="help">


<span class="admin">
<a name="edit_guarantee_type"></a>
<h3>Garantie ändern / neue Garantie</h3> Garantien haben folgende Felder:
<ul>
	<li><b> Name: </b> Der Name der Garantie.<br>
	<li><b> Währung: </b> Währung im Zusammenhang mit der Garantie.
	<li><b> Modell: </b> Dies definiert den Typ der Garantie, wie im Bereich <a
	href="#guarantee_models"><u>Garantiemodelle</u> </a> beschrieben.
	<li><b> Freigegeben: </b> Mit dieser Auswahl kann der Garantietyp freigegeben bzw. gesperrt werden.
	<li><b> Autorisiert durch: </b>Diese Option legt fest, wie zu autorisieren ist. Mögliche Optionen sind 
	Aussteller, Aussteller und Administrator, Keiner (einige Optionen sind nicht für alle Modelle verfügbar)
	<li><b> Max. anstehende Garantieperiode:</b> (nur Modell Zahlungsverpflichtung). Dies ist das Zeitfenster
	in dem ein Aussteller (oder Administrator) die Garantie autorisieren oder zurückweisen kann. <br>Wenn
	dieser Zeitraum vergangen ist, fällt die Garantie automatisch in den Status &quot;ohne Wirkung&quot;<br>
	<li><b> Max. Zeitraum zwischen Zahlungsverpflichtungen:</b> (nur Modell Zahlungsverpflichtung). Dies ist das maximale
	Zeitfenster, um Zahlungsverpflichtungen als &quot;Paket&quot; an den Käufer einzutragen.
	<li><b> Kreditgebühr: </b> Eine Kreditgebühr wird immer zu einem Systemkonto gehen (Überweisungstyp Mitglied-an-System)<br> Die Kreditgebühr (wenn sie auf nur lesen gesetzt ist) kann durch den Administrator geändert werden,
	aber nie durch den Aussteller.
	<li><b> Ausgabegebühr: </b> Die Ausgabegebühr geht immer an den Aussteller (Überweisungstyp Mitglied-an-Mitglied), wie die Kreditgebühr, kann entweder an den Käufer oder den Verkäufer verrechnet werden (je nach Konfiguration
	des Garantietyps). Die Gebühr kann durch den Aussteller geändert werden, wenn diese nicht als
	&quot;nur lesen&quot; definiert ist. Ein Administrator (als Autorisierer) kann die Gebühr immer ändern.
	<li><b> Gebührenberechnung (gemeinsame Regeln). </b> Bei Gebühren im Fall vom 
    Modell &quot;Käufer und Verkäufer&quot; ist es möglich zu definieren wer belastet wird (entweder Käufer oder Verkäufer). Gebühren können als
	fixer Wert, in Prozent des Garantiebetrages und als effektive Jahresgebühr konfiguriert werden. Im letzten
	Fall wird die Gebühr nach folgender Formel berechnet:<br> Jahresgebühr =
	((1+T/100)^(d/365) - 1) * M<br> Wo:
	<ul>
		<li><b>T</b> = Gebühr %</li>
		<li><b>d</b> = Gültigkeit der Garantie in Tagen</li>
		<li><b>M</b> = Betrag</li>
	</ul>
	<li><b>Beschreibung: </b> Eine (optional) erklärende Beschreibung des Garantietyps.
	<li><b>Überweisungstypen: </b> Generierter Überweisungstyp der Garantie definieren. 
	<li><b>Kreditgebühr: </b>Auswahl des Mitglied-an-System Überweisungstyps</li>
	<li><b>Ausgabegebühr: </b>Auswahl des Mitglied-an-Mitglied Überweisungstyps</li>
	<li><b>Darlehen: </b> Auswahl des Darlehens</li>
	<li><b>Weiterleitung: </b> Das ist der (Mitglied-an-Mitglied) Überweisungstyp, welcher vom
	Käufer an den Verkäufer geht.</li>
</ul>
<b>Anmerkung: </b>Es gibt zwei Wege, Garantien zu erstellen:
<ul>
	<li><b>Manuell:</b> durch Aussteller oder Administrator (Modelle &quot;Käufer und Verkäufer&quot; und &quot;nur
	Käufer&quot;)
	<li><b>Automatisch</b> bei Aktivierung der Garantie. (Modell Zahlungsverpflichtung)
</ul> </span>
<hr>


<a name="certifications"></a>
<h2>Zertifizierungen</h2>
<br><br>
Eine Zertifizierung ist ein digitaler Vertrag, erstellt durch einen &quot;Aussteller&quot;. Sie
definiert den maximalen Betrag, welcher ein bestimmter &quot;Käufer&quot; für die Ausgabe von
<a href="#payment_obligations"><u>Zahlungsverpflichtung</u> </a> an den
Verkäufer verwenden kann. Aussteller können nur eine &quot;aktive&quot; Zertifizierung
mit dem selben Käufer und der selben Währung haben.<br>

<i>Wo ist es zu finden?</i><br> Zertifizierungen können unter
&quot;Menü: Garantien > Zertifizierungen&quot; gefunden werden.<br>
<hr class="help">


<a name="new_certification"></a> <a name="edit_certification"></a>
<h3>Neue Zertifizierung / Zertifizierung ändern</h3>
Eine Zertifizierung hat folgende Einträge:
<ul>
	<li><b>Status: </b>(nur Zertifizierung anzeigen). Dieses Feld zeigt
	den <a href="#statusC"><u>Status</u> </a>der Zertifizierung.</li>
	<li><b>Garantietyp: </b>Der Garantietyp im Zusammenhang mit der
	Zertifizierung</li>
	<li><b>Käufer: </b>Der Name und die Anmeldung des Käufers</li>
	<li><b>Gültigkeitsperiode: </b> Der Zeitraum, in dem die Zertifizierung 
	aktiv ist. Wenn das Initalisierungsdatum (von) in der Zukunft liegt, 
	wird die Zertifizierung erst ab diesem Zeitpunkt aktiv.
	Es ist nicht möglich, eine Zertifizierung mit einem frühren Datum als 
	das aktuelle Datum zu erstellen.</li>
	<li><b>Betrag: </b>Dies ist der maximale Betrag, bis zu dem ein Käufer 
	eine Zahlungsverpflichtung ausgeben kann.</li>
	<li><b>Verwendeter Betrag: </b>Der verwendete Betrag ist der Gesamtbetrag
	der als Zahlungsverpflichtung (je Zertifizierung) ausgegeben wurde. Der eingesetzte Betrag ist
	die Summe aller Beträge der Garantie mit dem Status <i>Akzeptiert</i>
	und <i>Wartend</i>. Wenn die Zahlungsverpflichtungen durch den Käufer
	ausgezahlt werden und administrativ der Zertifizierungsbetrag geschlossen wird,
	ist sie wieder erneut verfügbar (vorausgesetzt, dass die Gültigkeitsdauer noch
	aktiv ist).
</ul>
<hr class="help">


<a name="certification_logs"></a>
<h3>Statusprotokoll Zertifizierung</h3>
Dieses Fenster zeigt Informationen über die Änderung des <a
href="#statusC"><u>Status</u> </a>der Zertifizierung.
<br><br>
<hr class="help">


<a name="statusC"></a>
<h3>Zertifizierung-Status</h3>
Zertifizierungen können verschiedene Status in Abhängigkeit vom 
Garantietyp und Betriebsablauf haben. Hier sind alle möglichen Status aufgelistet und
mit einer Beschreibung der möglichen Aktionen welche zu dem Status führen.
<br><br>
<b>Status / Aktionen</b>
<ul>
	<li>Geplant</li>
	<ul>
		<li>Wenn ein Aussteller eine neue Zertifizierung mit einem
		zukünftigen Initialisierungsdatum erstellt (nicht heute).</li>
		<li>Wenn ein Aussteller eine Zertifizierung mit Status 
		&quot;unterbrochen&quot; aktiviert und diese ein zukünftiges Initialiserungsdatum
		hat (nicht heute).</li>
	</ul>
	<li>Aktiv</li>
	<ul>
		<li>Wenn ein Aussteller eine neue Zertifizierung mit einem 
		vergangenen oder dem aktuellen Datum als Initialisierungsdatum erstellt.</li>
		<li>Wenn ein Aussteller eine Zertifizierung mit Status
		&quot;unterbrochen&quot; aktiviert und ein vergangenes oder aktuelles Datum
		als Initalisierungsdatum hat.</li>
		<li>Wenn eine geplante Zertifizierung das aktuelle Datum erreicht.</li>
	</ul>
	<li>Unterbrochen</li>
	<ul>
		<li>Wenn ein Aussteller eine Zertifizierung mit Status aktiv oder 
		geplant, unterbricht.</li>
		<li>Wenn eine geplante Zertifizierung das aktuelle Datum erreicht, 
		aber es bereits eine aktive Zertifizierung mit dem selben Aussteller,
		Währung und Käufer gibt.</li>
	</ul>
	<li>Abgelaufen</li>
	<ul>
		<li>Wenn das Ablaufdatum einer Zertifizierung im Status
		&quot;aktiv&quot; oder &quot;geplant&quot; erreicht ist.</li>
	</ul>
	<li>Annulliert</li>
		<ul> 
		<li>Wenn ein Administrator eine Zertifizierung mit einem der folgenden
		Status annulliert: aktiv, unterbrochen oder geplant.</li>
	</ul>
</ul>

<h3>Zertifizierungs-Benachrichtigungen</h3>
Die folgenden Benachrichtigungen im Zusammenhang mit Zertifizierungen können generiert werden:<br>
<ul>
	<li>Wird an den Käufer gesendet, wenn eine Zertifizierung einen der 
	folgenden Status erreicht: Aktiv, unterbrochen, abgelaufen oder annulliert</li>
	<li>Wird an den Aussteller gesendet, wenn eine Zertifizierung in den Status
	abgelaufen geht.</li>
</ul>
<hr class="help">


<a name="certifications_search"></a>
<h3>Zertifikationen suchen</h3>
Diese Liste zeigt alle konfigurierten <a href="#certifications"><u>Zertifizierungen</u></a>, 
folgenden Suchoptionen stehen zur Verfügung:<br>
<ul>
	<li><b>Status</b> Der <a href="#statusC"><u>Status</u>
	</a> der Zertifizierung:</li>
	<ul>
		<li>Aktiv</li>
		<li>Annulliert</li>
		<li>Unterbrochen</li>
		<li>Abgelaufen</li>
		<li>Geplant</li>
		<br>
	</ul>
		<li><b>Aussteller: </b>Der Name und die Anmeldung des Ausstellers</li>
		<li><b>Käufer: </b>Der Name und die Anmeldung des Käufers</li>
		<li><b>Aktivierungsdatum: </b>Suche nach Aktivierungsdatum innerhalb einer Frist</li>
		<li><b>Ablaufdatum: </b>Suche nach Ablaufdatum innerhalb einer Frist</li>
	</ul>
<hr class="help">


<a name="certifications_search_results"></a>
<h3>Suchergebnisse Zertifikationen</h3>
Diese Seite zeigt eine Liste mit Suchergebnissen von Zertifizierungen.<br>Die Zertifizierungsdetails
können Sie durch Anwahl des Anzeigen-Symbols
<img border="0" src="${images}/view.gif" width="16" height="16"> in der Liste der Zertifizierungen ansehen.
<br>
<hr>


<a name="guarantees"></a>
<h2>Garantien</h2>
<br><br>
<i>Wo ist es zu finden?</i><br> Garantien können unter 
&quot;Menü: Garantien > Garantien&quot; gefunden werden.<br>
<hr class="help">


<a name="guarantee_register"></a>
<h3>(Neue) Garantie registrieren</h3>
Es gibt zwei Wege Garantien zu erstellen:<br>
<ul>
	<li>Manuell durch Aussteller oder Administrator (Modelle &quot;Käufer und
	Verkäufer&quot; und &quot;nur Käufer&quot;)
	<li>Automatisch nach der Aktivierung der Garantie. (Modell Zahlungsverpflichtung)
</ul>
Eine Garantie kann folgende Felder haben:
<ul>
	<li><b>Käufer: </b>Der Name und die Anmeldung des Käufers</li>
	<li><b>Aussteller: </b>Der Name und die Anmeldung des Ausstellers</li>
	<li><b>Verkäufer: </b>Der Name und die Anmeldung des Verkäufers</li>
	<li><b>Kennung:</b> Ein eindeutiger Code welcher die Garantie identifiziert (optional)</li>
	<li><b>Gültigkeit: </b> Der Zeitraum in dem die Garantie aktiv wird</li>
	<li><b> Kreditgebühr: </b>Der Betrag der Kreditgebühr<span class="admin"> (siehe <a
	href="#edit_guarantee_type"><u>Garantietyp</u></a>) </span><br>
	Dieses Feld ist abhängig von der Konfiguration des Garantietyps editierbar.
	<li><b>Ausgabegebühr: </b> Der Betrag der Ausgabegebühr <span class="admin">(siehe <a
	href="#edit_guarantee_type"><u> Garantietyp</u></a>)</span><br>
	Dieses Feld ist abhängig von der Konfiguration des Garantietyps editierbar.</li>
	<br>
</ul>
Eine Garantie wechselt mit dem Tag der Gültigkeit in den Status akzeptiert. Wenn eine 
Garantie in den Status akzeptiert wechselt, wird ein Darlehen generiert und mögliche Gebühren werden verrechnet.
<br> Nur Garantien, die durch einen Administrator manuell erstellt wurden, können gelöscht werden. 
Vorausgesetzt, dass diese im Status &quot;Wartender Administrator&quot; ist und der 
einzige vergangene Status der erreicht wurde &quot;Wartender Aussteller&quot; ist.<br><br>
<hr class="help">


<a name="guarantees_search"></a>
<h3>Garantien suchen</h3>
Auf dieser Seite können Sie nach Garantien suchen. Die folgenden Suchoptionen sind verfügbar:
<ul>
	<li><b> Status: </b> Der <a href="#statusG"><u>Status</u> </a> der
	Garantie.</li>
	<li><b>Käufer: </b>Der Name und die Anmeldung des Käufers</li>
	<li><b>Aussteller: </b>Der Name und die Anmeldung des Ausstellers</li>
	<li><b>Verkäufer: </b>Der Name und die Anmeldung des Verkäufers</li>
	<li><b> Gültigkeit: </b> Der Zeitraum in dem die Garantie aktiv wird</li>
	<li><b>Ablaufdatum: </b>Suche nach Ablaufdatum innerhalb einer Frist</li>
	<li><b>Betrag: </b>Der Betrag der Garantie.</li>
</ul>
<hr class="help">



<a name="guarantees_search_results"></a>
<h3>Suchergebnisse der Garantien</h3>
Diese Seite zeigt die Suchergebnisse der Garantien.
<br> Um eine Garantie anzuzeigen wählen Sie bitte das entsprechende Ansicht-Sybmol
<img border="0" src="${images}/view.gif" width="16" height="16"> an.
<br><br>
<hr class="help">


<a name="guarantee_details"></a>
<h3>Garantie-Details</h3>
Diese Seiten zeigen folgende Details der Garantie, wie im Hilfebereich
<a href="#guarantee_register"><u>Garantie registrieren</u> </a> beschrieben.
Zusätzliche Felder sind:
<ul>
	<li><b>Status: </b>Der aktuelle <a href="#statusG"><u>Status</u>
	</a> der Garantie</li>
	<li><b>Registrierungsdatum: </b>Das Datum, an dem die Garantie registriert wurde.</li>
	<li><b>Generiertes Darlehen: </b>Im Falle der Garantie generiertes
	Darlehen mit einem Link, um das Darlehen anzuzeigen</li>
</ul>
<br><br>
<hr class="help">


<a name="guarantee_payment_obligations"></a>
<h3>Zahlungsverpflichtung der Garantie</h3>
Dieses Fenster zeigt eine Liste mit
<a href="#payment_obligations"><u>Zahlungsverpflichtungen</u></a> 
im Zusammenhang mit der Garantie. 
<br><br>
<hr class="help">


<a name="guarantee_logs"></a>
<h3>Garantie-Status geändert (Protokoll)</h3>
Dieses Fenster zeigt eine Liste mit dem <a href="#statusG"><u>Status</u></a>.<br><br>
<hr class="help">


<h3>Garantie-Status</h3>
<a name="statusG"></a> Garantien können verschiedene Status in Abhängigkeit vom Garantietyp
und dem Betriebsablauf haben. Hier sind alle möglichen Status aufgelistet und
mit einer Beschreibung der möglichen Aktionen welche zu dem Status führen.
<br><br>

<b>Status / Aktionen</b>
<ul>
	<li>Wartender Aussteller</li>
	<ul>
		<li>Wenn ein Administrator eine neue Garantie, die vom 
		Aussteller autorisiert werden muss, registriert.</li>
		<li>Wenn ein Verkäufer eine Zahlungsverpflichtung oder ein
		&quot;Paket&quot; von Zahlungsverpflichtungen akzeptiert.</li>
	</ul>
	<li>Wartender Administrator</li>
	<ul>
		<li>Wenn ein Administrator eine neue Garantie, die (nur) vom
		Administrator autorisiert werden muss, registriert.</li>
		<li>Wenn ein Aussteller eine Garantie autorisiert, die im Status
		&quot;Wartender Aussteller&quot; ist und so konfiguriert ist, dass
		sie vom Administrator autorisiert werden muss.</li>
	</ul>
	<li>Akzeptiert</li>
		<ul>
		<li>Wenn ein Administrator eine neue Garantie, die keine
		Autorisierung erfordert, registriert.</li>
		<li>Wenn ein Administrator eine Garantie akzeptiert, die im Status
		&quot;Wartender Administrator&quot; ist.</li>
		<li>Wenn ein Aussteller eine Garantie akzeptiert, die im Status
		&quot;Wartender Aussteller&quot; ist.</li>
	</ul>

	<li>Verweigert</li>
	<ul>
		<li>Wenn ein Aussteller eine Garantie, die im Status
		&quot;Wartender Aussteller&quot; ist, verweigert.</li>
		<li>Wenn ein Administrator eine Garantie, die im Status
		&quot;Wartender Administrator&quot; ist, verweigert.</li>
	</ul>
	<li>Annulliert</li>
	<ul>
		<li>Wenn ein Administrator eine Garantie annulliert, die den Status
		&quot;Wartender Aussteller&quot; oder &quot;Wartender Administrator&quot; hat.</li>
		<li>Wenn ein Administrator eine Garantie annulliert, die den Status
		&quot;akzeptiert&quot; hat, aber das Darlehen noch nicht generiert wurde, da
		dies eine andere Autorisierung erfordert.</li>
	</ul>
	<li>Keine Aktion</li>
	<ul>
		<li>Wenn die maximale Zeit einer Garantie im Status &quot;Wartender Aussteller&quot;
		abgelaufen ist (Aussteller hat keine Aktion durchgeführt).</li>
		<li>Wenn die maximale Zeit einer Garantie im Status &quot;Wartender Administrator&quot;
		abgelaufen ist (Administrator hat keine Aktion durchgeführt).</li>
	</ul>
</ul>

<h3>Benachrichtigungen</h3>
Die Änderung des Garantiestatus kann folgende Benachrichtigungen generieren:
<ul>
	<li>An den Aussteller gesendet, wenn eine neue Garantie erstellt wurde und
	den Status &quot;Wartender Aussteller&quot; erhält.</li>
	<li>An den Aussteller gesendet, wenn eine vorhandene Garantie annulliert wurde
	oder die maximale Dauer des Garantiestatus abgelaufen ist.</li>
	<li>An den Käufer gesendet, wenn ein Garantiestatus in den Status
	akzeptiert, verweigert oder annulliert geändert wurde.</li>
	<li>An den Verkäufer gesendet, wenn ein Garantiestatus in den Status
	akzeptiert, verweigert oder annulliert geändert wurde.</li>
	<li>An den Administrator gesendet, wenn eine neue Garantie den 
	Status &quot;Wartender Administrator&quot; erhält.</li>
</ul>
<br><br>
<hr class="help">


<a name="guarantee_authorization"></a>
<h3>Garantieautorisierung</h3>
Garantien können Autorisierung in verschiedenen Schritten des operativen Ablaufs und 
durch unterschiedliche Rollen erfordern. Die Autorisierungskonfiguration ist im
<a href="#edit_guarantee_type"><u>Garantietyp</u></a> 
mit die Option &quot;Autorisiert durch &quot; definiert.
<hr>


<a name="payment_obligations"></a>
<h2>Zahlungsverpflichtungen</h2>
<br><br> Eine Zahlungsverpflichtung ist ein digitales (Cyclos) Dokument, nur als Darlehen
oder Rechnung. Wie das Wort schon sagt, ist eine Zahlungsverpflichtung eine Verpflichtung für einen &quot;Käufer&quot;
an einen &quot;Verkäufer&quot; zu zahlen. Eine Zahlungsverpflichtung wird durch den Käufer erstellt, in der Regel für
jede anstehende Rechnung eines Anbieters (Verkäufer). Zahlungsverpflichtungen haben einen &quot;Betrag&quot; und ein 
&quot;Ablaufdatum&quot; und können zusammen zu einem &quot;Paket&quot; gebündelt werden, um die Verarbeitung
zu vereinfachen. Wenn der Verkäufer die Zahlungsverpflichtung(en) akzeptiert 
erhält er/sie interne Einheiten
für die Höhe der Zahlungsverpflichtung(en). Sobald Zahlungsverpflichtungen durch den Verkäufer akzeptiert und
durch den Aussteller (und optional auch durch den Cyclos-Administrator) geprüft sind, wird ein Darlehen und
eine Garantie generiert.

<i>Wo ist es zu finden?</i><br> Zahlungsverpflichtungen können unter &quot;Menü: Garantien > Zahlungsverpflichtungen&quot; 
gefunden werden.<br><br>
<hr class="help">


<a name="payment_obligations_search"></a>
<h3>Zahlungsverpflichtungen suchen</h3> Auf dieser Seite können Sie nach Zahlungsverpflichtungen suchen. 
<br>Mögliche Suchoptionen sind: 
<ul>
	<li><b>Status: </b> ist der <a href="#statusOP"><u>Status</u>
	</a> der Zahlungsverpflichtungen.</li>
	<li><b>Währung: </b>(wird nur angezeigt, wenn mehrere Währungen vorhanden sind).</li>
	<li><b>Käufer / Verkäufer: </b>Je nach Rolle des angemeldeten Benutzers</li>
	<li><b>Ablaufdatum: </b>nach diesem Datum ist die Zahlungsverpflichtung
	für den Verkäufer nicht mehr sichtbar. Die
	Veröffentlichung ist meistens vor dem Auflaufdatum.</li>
	<li><b>Betrag: </b>Betragsbereich der Zahlungsverpflichtung </li>
</ul>
<br><br>
<hr class="help">


<a name="payment_obligations_search_results"></a>
<h3>Suchergebnisse für Zahlungsverpflichtungen</h3>
Dieses Fenster zeigt die Ergebnisse der Suche an. Für Zugriff auf eine Zahlungsverpflichtung können Sie
das Anzeige-Symbol
<img border="0" src="${images}/view.gif" width="16" height="16"> verwenden.
<br><br>
<hr class="help">


<a name="edit_payment_obligation"></a>
<h3>Zahlungsverpflichtung erstellen und bearbeiten</h3>
Käufer mit einer gültigen <a href="#certifications"><u>Zertifizierung</u></a>
können Zahlungsverpflichtungen gegenüber ihren Anbieter (Verkäufer) ausgeben. <br>
Der Käufer kann Zahlungsverpflichtungen mit der Menüoption 
&quot;Garantien > Zahlungsverpflichtungen&quot; erstellen.<br> Die notwendigen
Felder sind: <br>
<ul>
	<li><b>Verkäufer: </b>Der Name und die Anmeldung des Verkäufers</li>
	<li><b>Veröffentlicht bis: </b>Veröffentlichungsdatum, nach diesem Datum ist die
	Zahlungsverpflichtung für die Verkäufer nicht mehr sichtbar. Veröffentlichung ist 
	meistens vor dem Auflaufdatum.</li>
	<li><b>Ablaufdatum: </b>Datum, bis zu dem der Verkäufer die 
	Zahlungsverpflichtung akzeptieren kann</li>
	<li><b>Betrag: </b>Betrag der Zahlungsverpflichtung (normalerweise
	Betrag der spezifischen Rechnung)</li>
	<li><b>Beschreibung: </b> Optionale Beschreibung</li>
</ul>
<br> 
Sobald eine Zahlungsverpflichtung erstellt wurde sind folgende Optionen
verfügbar:
<ul>
	<li><b>Löschen: </b>Damit wir die Zahlungsverpflichtung gelöscht.</li>
	<li><b>Veröffentlichen: </b>Dies setzt die Zahlungsverpflichtung in den
	Status veröffentlicht. Dies bedeutet, sie ist für den Verkäufer sichtbar.</li>
	<li><b>Ändern: </b>Mit dieser Option kann die Zahlungsverpflichtung geändert werden.</li>
	<li><b>Veröffentlichung aufheben: </b> (nur sichtbar wenn Status veröffentlicht ist).
	Mit dieser Option wird die Zahlungsverpflichtung wieder in den Status
	&quot;Registriert&quot; gesetzt. Was bedeutet, sie kann geändert und erneut veröffentlicht werden.</li>
</ul>
<br><br>
<hr class="help">


<a name="accept_payment_obligation"></a>
<h3>Aktionen durch den Verkäufer</h3>
Wenn Zahlungsverpflichtungen (oder Pakete von Zahlungsverpflichtungen) den Status &quot;veröffentlicht&quot; 
haben, kann der Verkäufer durch Auswahl der Optionen im Fenster für Zahlungsverpflichtungen, die Zahlungsverpflichtung(en) entweder 
akzeptieren oder zurückweisen.<br><br>Um es einfacher zu gestalten ist es für den Verkäufer möglich
mehrere Zahlungsverpflichtungen als &quot;Paket&quot; einzureichen. Dies macht man mit der Auswahlbox vor 
jeder Zahlungsverpflichtung in der Liste für Suchergebnisse für Zahlungsverpflichtungen.
<br> Anmerkung: Diese Auswahloption erscheint nur, wenn das Suchergebnis durch den Käufer und Währung begrenzt ist (wenn
mehr als eine Währung verfügbar ist).
<br>Beim Zurückweisen der Zahlungsverpflichtung(en) wird einfach abgebrochen und der Käufer wird mit
einer Benachrichtigung informiert.  
<br>Wenn ein Verkäufer eine Zahlungsverpflichtung akzeptiert, geschieht folgendes.
<ul>
	<li>Eine Garantie mit dem Betrag der Zahlungsverpflichtung(en) 
	wird erzeugt.</li>
	<li>Der Aussteller muss die Garantie autorisieren</li>
	<li>Optional muss ein Administrator die Garantie ebenfalls autorisieren</li>
	<li>Einmal autorisiert, werden alle Levels der Garantie in den
	Status &quot;akzeptiert&quot; wechseln, ein Darlehen mit dem Käufer als 
	Zielkonto wird generiert.</li>
	<li>Der Betrag des Darlehns wird an das Verkäuferkonto weitergeleitet.
	Dies bedeutet, dass das Darlehen für den Käufer geöffnet wird, der
	Verkäufer mit dem Betrag der Garantie belastet.</li>
	<li>Möglicher Kredit und Ausgabegebühr werden berechnet (abhängig von 
	der Konfiguration des <a href="#edit_guarantee_type"><u>Garantietyps</u> 
	</a>
</ul>
Alle Status über die Zahlungsverpflichtungen werden protokolliert und
Benachrichtigungen an den Käufer und Verkäufer gesendet.
<br><br>
<hr class="help">


<a name="payment_obligation_logs"></a>
<h3>Status der Zahlungsverpflichtung geändert</h3>
Dieses Fenster gibt Informationen über die Änderungen vom
<a href="#statusOP"><u>Status</u> </a>
<hr class="help">


<h3>Status der Zahlungsverpflichtung</h3>
<a name="statusOP"></a> Zahlungsverpflichtungen können verschiedene Status je Art des
Garantietyps und Betriebsablauf haben. Hier sind alle möglichen Status aufgelistet und
mit einer Beschreibung der möglichen Aktionen welche zu dem Status führen.
<br><br>

<b>Status / Aktionen</b>
<ul>
	<li>Registriert</li>
	<ul>
		<li>Wenn ein Käufer eine neue Zahlungsverpflichtung generiert.</li>
		<li>Wenn ein Käufer eine Zahlungsverpflichtung nicht veröffentlicht.</li>
		<li>Wenn das max. Veröffentlichungsdatum der Zahlungsverpflichtung den Status Veröffentlichung 
		erreicht hat.</li>
	</ul>
	<li>Veröffentlicht</li>
	<ul>
		<li>Wenn ein Käufer eine Zahlungsverpflichtung mit dem Status 
        &quot;registriert&quot; veröffentlicht.</li>
	</ul>
		<li>Akzeptiert</li>
	<ul>
		<li>Wenn ein Verkäufer eine Zahlungsverpflichtung mit dem Status &quot;veröffentlicht&quot;
		akzeptiert.</li>
	</ul>
		<li>Verweigert</li>
	<ul>
		<li>Wenn ein Verkäufer eine Zahlungsverpflichtung mit dem Status &quot;veröffentlicht&quot;
		verweigert.</li>
		</ul>
	<li>Abgelaufen</li>
	<ul>
	<li>Wenn das Ablaufdatum der Zahlungsverpflichtung den Status 
    &quot;veröffentlicht&quot; erreicht.</li>
	</ul>
</ul>
<h3>Benachrichtigungen</h3>
Die Statusänderung der Zahlungsverpflichtung kann folgende Benachrichtigungen aktivieren:<br>
<ul>
	<li>An den Verkäufer, wenn eine Zahlungsverpflichtung den Status &quot;veröffentlicht&quot; erhält.</li>
	<li>An den Käufer, wenn eine Zahlungsverpflichtung den Status &quot;verweigert&quot; erhält.</li>
</ul>

</div>

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