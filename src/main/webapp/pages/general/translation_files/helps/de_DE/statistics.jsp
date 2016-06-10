<div style="page-break-after: always;">
<br><br>Cyclos enthält einen Bereich für die statistische Analyse 
Ihrer Daten. <br>
Bei den Statistiken geht es darum, zu quantifizieren, wie genau Ihre Ergebnisse sind.  
Wir tun dies, indem wir regelmäßig statistische Tests durchführen, und indem wir - wo 
immer möglich - die Genauigkeit der Zahlen angeben.
<i>Wo ist es zu finden?</i><br>
Sie finden diesen Abschnitt über  &quot;Menü: Berichte > Statistische Analyse&quot;.
<br><br><i>Wie können Statistiken aktiviert werden?</i>
Statistiken benötigen eine <a href="${pagePrefix}groups#manage_group_permissions_admin_system"><u>
Berechtigung</u></a>, um angezeigt werden zu können. Die Berechtigung finden Sie im Abschnitt 
&quot;Berichte&quot;, Kontrollkästchen &quot;Statistiken&quot;.<br><br>
Wenn Sie in Ihren Statistiken bestimmte  <a href="${pagePrefix}groups#group_filters">
<u>Gruppenfilter</u></a> oder <a href="${pagePrefix}account_management#payment_filters"><u>
Zahlungsfilter</u></a> verwenden möchten, müssen Sie diese als in den Statistiken sichtbar 
markieren. Dies kann auf den Konfigurationsseiten für diese Filter getan werden 
(den Verweisen folgen). 
<hr>

<a name="choose_category"></a>
<h3>Kategorie der Statistik wählen</h3>
Dieses Fenster ermöglicht Ihnen die Auswahl einer statistischen Kategorie.  Klicken einer 
der Schaltflächen führt Sie zum entsprechenden Formular, wo Sie die Details der zu 
berechnenden Statistik spezifizieren können. Dieses Formular dient also nur der 
Angabe der wichtigsten Kategorien.
<br>
Sie können jeweils nur eine einzige Kategorie wählen. Es kann für den Server eine zu hohe 
Belastung darstellen, alle Tests und Berechnungen auf einmal laufen zu lassen. 
<br>
Sie können unter den folgenden Kategorien wählen:
<ul>
	<li><b>Schlüsselkriterien:</b> Die wichtigsten Parameter für Stabilität und Entwicklung 
	des Systems (wie Anzahl der Mitglieder, Umsatz, etc.) betreffen.<br>
	<li><b>Aktivität der Mitglieder:</b> Statistiken in Zusammenhang mit den Tauschaktivitäten 
	der einzelnen Mitglieder.<br>
	<li><b>Finanzen:</b> Statistiken zu den Systemkonten: alle Einnahmen (Zuflüsse) und 
	Ausgaben (Abflüsse) für jedes	Systemkonto.<br>
</ul>
&nbsp; Weitere Optionen werden in zukünftigen Versionen dieser Software folgen. 
<hr class="help">

<a name="forms"></a>
<h2>Statistische Formulare</h2>
Nachdem Sie entschieden haben, welche statistische <a href="#choose_category">
<u>Kategorie</u></a> sie zeigen möchten, werden Sie zu dem Formular geführt, in dem Sie 
spezifizieren können, welche Statistiken Sie zu dieser Kategorie sehen möchten.
Diese Formulare enthalten immer einen spezifischen Abschnitt (welcher für jede Kategorie 
anders ist), in dem Sie mit Hilfe von Kontrollkästchen die einzelnen Rubriken wählen können, 
und einen allgemeinen Abschnitt (überwiegend identisch für alle statistischen Kategorien), 
in dem Sie die Parameter für die Statistiken festlegen, wie z.B. 
<a href="#periods"><u>Zeitraum</u></a> und verschiedene <a href="#filters"><u>Filter</u></a>. 


In jedem statistischen Formular, wählen Sie zuerst den Typ der Statistik, die Sie sehen 
möchten über die Auswahlliste <a href="#whattoshow"><u>&quot;Was anzeigen?&quot;</u></a>
<br>
Unter Zuhilfenahme der Kontrollkästchen auf der linken Seite können Sie die gewünschten 
Rubriken wählen. Die Namen der Rubriken sind beschreibend genug, wenn Sie allerdings deren 
genaue Definition überprüfen möchten, können Sie im <a href="#glossary"><u>Glossar</u></a> 
nachschauen.Wenn Sie ein Diagramm-Kontrollkästchen wählen, so erscheinen normalerweise 
sowohl Tabellen als auch Grafiken. Wenn Sie das Kontrollkästchen Diagramm nicht wählen, 
erscheint lediglich die Tabelle. 
<br>
Nachdem Sie ein oder mehrere Kontrollkästchen gewählt haben, müssen Sie im Fenster unten die 
<a href="#parameters"><u>gemeinsamen Parameter</u></a> für Ihre Auswahl spezifizieren. 
Wenn Sie dies getan und alles ausgefüllt haben, klicken Sie bitte auf die Schaltfläche 
&quot;Weiter&quot; am unteren Ende der Seite, um alles berechnen zu lassen.
<br><br><b><u>Warnung:</u> Denken Sie bitte daran, dass die Berechnung statistischer Daten für 
umfangreiche Datenbanken einige Zeit in Anspruch nehmen können.</b><hr>

<a name="key_development"></a>
<h2>Schlüsselkriterien:</h2>
Hier können Sie spezifizieren, welche Schlüsselkriterien Sie berechnen lassen wollen. 
Statistiken der Schlüsselkriterien sind dazu gedacht, eine allgemeine Übersicht der 
Entwicklungen innerhalb Ihres Systems zu erstellen. Dies ermöglicht Ihnen, Zeiträume 
zu vergleichen und Trends zu überprüfen, es wird allerdings keine statistische Analyse 
durchgeführt. 

Statistiken können Sie zu folgenden Informationen erstellen:
<ul>
	<li><b>Anzahl der Mitglieder</b>
	<li><b><a href="#grossProduct"><u>Bruttoeinnahmen</u></a></b>
	<li><b>Anzahl der Überweisungen</b>
	<li><b>Durchschnittlicher Überweisungsbetrag</b>
	<li><b>Anzahl der Inserate</b>
</ul>
<br><br>
Um eine Beschreibung des Formulars zu erhalten, <a href="#forms"><u>klicken Sie bitte hier</u></a>.
<hr>

<a name="member_activity"></a>
<h2>Aktivität der Mitglieder</h2>
Hier können Sie spezifizieren, welche Statistiken Sie zu den Mitgliedsaktivitäten berechnen 
lassen wollen. Die Statistiken zu den Mitgliedsaktivitäten ermöglichen Ihnen Einblick in die 
Aktivitäten der Mitglieder. Statistiken können auch für unterschiedliche Zusammensetzungen 
von Mitgliedern durchgeführt werden. 

Statistiken können Sie zu den folgenden Informationen erstellen:
<ul>
	<li><b><a href="#grossProduct"><u>Bruttoeinnahmen</u></a></b>
	<li><b>Anzahl der Überweisungen</b>
	<li><b>Prozentsatz der nicht tauschenden Mitglieder</b>
	<li><b>Anmeldungen</b> zeigt, wie häufig sich die Mitglieder im Programm anmelden.
</ul>
<br><br>
Um eine Beschreibung des Formulars zu erhalten, <a href="#forms"><u>klicken Sie bitte hier</u></a>.
<hr>

<a name="finances"></a>
<h2>Finanzen</h2>
Hier können Sie spezifizieren, welche Finanzstatistiken Sie berechnen lassen wollen. 
Finanzstatistiken geben Ihnen einen genauen Überblick über alle eingehenden und ausgehenden 
Flüsse zu/von jedem Systemkonto. 

Statistiken können Sie zu den folgenden Informationen erstellen:
<ul>
	<li><b>Übersicht</b> zeigt eine Übersicht der eingehenden und ausgehenden Zahlungen an 
	ein oder von einem bestimmten Systemkonto. Es ist möglich, Einnahmen und Ausgaben in einem
	Balkendiagramm zu vergleichen.
	<li><b>Einnahmen</b> zeigt in Form eines Tortendiagramms die Aufteilung der Einnahmen für die
	von Ihnen definierten Zahlungsfilter.
	<li><b>Ausgaben</b> zeigt in Form eines Tortendiagramms die Aufteilung der Ausgaben für die
	von Ihnen definierten Zahlungsfilter. 
</ul>
<br><br>
Um eine Beschreibung des Formulars zu erhalten, <a href="#forms"><u>klicken Sie bitte hier</u></a>.
<hr>

<a name="parameters"></a>
<h2>Gemeinsame Parameter und Felder für statistische Formulare</h2>
Bevor Sie die statistischen Ergebnisse zeigen können, müssen Sie spezifizieren, was genau Sie 
lesen möchten. Im Allgemeinen muss Cyclos ein paar Dinge wissen, um statistische Ergebnisse 
kalkulieren und zeigen zu können. 
<ul>
	<li>&quot;<a href="#whattoshow"><u>Was anzeigen?</u></a>&quot;: Dieses Listenfeld befindet sich 
	im Formular ganz oben, als erste Rubrik. Wählen Sie hier die Methode zum Zeigen der Statistik.<br>
	<li>&quot;<a href="#periods"><u>Zeiträume</u></a>&quot;: Diese Rubrik ist unterhalb der 
	Kontrollkästchen zu sehen. Bestimmen Sie hier den Zeitraum, für den das Ergebnis berechnet werden 
	soll. Nicht alle Daten sind periodische Daten; einige Daten können nicht für einen Zeitraum  
	berechnet werden. In solchen Fällen wird der letzte Tag des Zeitraums verwendet. Dies ist in 
	der Hilfedatei stets angegeben.<br>
	<li>&quot;<a href="#filters"><u>Filter</u></a>&quot;: Unterhalb der Rubrik Zeitraum finden Sie eine 
	Filterauswahl. Da es in Cyclos keine vorformulierte Definition dessen gibt, was ein Mitglied, 
	oder was Handel, ist, müssen Sie dies über diese Filter eingrenzen.<br>
</ul>

<hr class="help">

<a name="whattoshow"></a>
<h3>Listenfeld &quot;Was anzeigen?&quot;</h3>
Mit diesem Listenfeld bestimmen Sie die Methode zum Zeigen der Statistiken. Sie können zwischen den 
folgenden vier Methoden wählen: 
<ul>
	<li>&quot;Ein Periode anzeigen:&quot; Dies ergibt die Zusammenfassung eines einzigen 
	<a href="#periods"><u>Zeitraums</u></a>. Normalerweise wird dann keine statistische Analyse 
	durchgeführt, und es kann auch keine Grafik gezeigt werden.<br>
	<li>&quot;Vergleich zweier Zeiträume:&quot; Dies vergleicht die Ergebnisse zweier Zeiträume, was 
	als Balkendiagramm gezeigt werden kann. Wählen Sie dies bitte, wenn Sie sehen möchten, ob sich ein 
	bestimmtes Resultat im Vergleich mit einem früheren oder späteren Zeitraum verringert oder erhöht hat. 
	Eine statistische Analyse wird durchgeführt, um zu berechnen, ob die Differenz zwischen den beiden 
	Zeiträumen <a href="#p"><u>statistisch signifikant</u></a> ist.<br>
	<li>&quot;Über einen Zeitraum&quot;: tut das Gleiche, aber über eine gewisse Zeitspanne hinweg.<br>
	<li>&quot;Verteilung&quot;: ergibt normalerweise <a href="#histo"><u>Balkendiagramm</u></a>,
	also eine Grafik, die anzeigt, wie ein bestimmtes Ergebnis über einen Bestand verteilt ist.<br>
</ul>
Es kann sein, dass nicht alle dieser Ruriken gezeigt werden. Das hängt von der von Ihnen 
gewählten Statistik ab. 
<hr class="help">

<a name="periods"></a>
<h3>Zeiträume</h3>
Spezifizieren Sie bitte den Zeitraum, für den die Statistiken berechnet werden sollen. 
Abhängig davon, welche Rubrik Sie gewählt haben, können Sie nun einen oder zwei Zeiträume 
bestimmen, oder eine Zeitspanne.
<br><br>The &quot;Hauptzeitraum&quot; ist der Zeitraum, für den die Statistiken berechnet werden. 
Die wird verglichen mit dem &quot;Vergleichszeitraum&quot;. <br>
Geben Sie bitte für jeden dieser beiden Zeiträume einen Namen ein. Dieser Name wird in der 
Kopfzeile der Tabelle und in der Legende der Grafik verwendet.<br>
Falls Sie &quot;Über einen Zeitraum&quot; gewählt haben, sieht das Zeitraum-Kästchen etwas anders aus. 
In diesem Fall sollten Sie zuerst wählen, ob Sie die angeforderten Ergebnisse für das Jahr, 
das Quartal oder den Monat gezeigt  bekommen möchten. Danach wählen Sie bitte den Zeitraum, 
für die die Statistiken berechnet werden sollen.<br>
<br>
Wichtig ist, dass Sie keinen zu langen Zeitraum wählen: Wenn Sie entscheiden, dass Sie die 
Ergebnisse jeden Monats für einen Zeitraum von 10 Jahren anzeigen möchten, dann ergibt das 
nicht nur völlig unübersichtliche Grafiken, sondern stellt auch eine schwere Belastung für Ihren 
Server dar, und verursacht lange Wartezeiten.<br>
In einem solchen Fall erscheint ein Pop-up-Fenster und teilt Ihnen mit, wie viele  
&quot;Datenpunkte&quot; Sie höchstens anfordern können, und bittet Sie darum, Ihre Anforderung 
einzugrenzen. Ein &quot;Datenpunkt&quot; stellt jeweils eine gesonderte Berechnung dar. Zum 
Beispiel: wenn Sie 5 Elemente-Kästchen gewählt haben, und einen Zeitraum von 13 Monaten, so ergibt 
dies 5 x 13 = 65 Datenpunkte. Kontrollkästchen für Grafiken werden in diesem Fall nicht mitgerechnet, 
da sie normalerweise keine zusätzlichen Berechnungen erfordern. 
<hr class="help">

<a name="filters"></a>
<h3>Filter</h3>
Bevor Sie nun berechnen können, wie viele Mitglieder es gibt, oder wie hoch das Handelsvolumen 
ist, muss das Programm natürlich wissen, was Sie als &quot;Mitglied&quot; oder als &quot;Handel&quot; 
betrachten. Da Cyclos mit einer Vielzahl von  benutzerdefinierten Benutzergruppen und 
Überweisungstypen arbeitet, enthält die Anwendung keine vorgefassten Definitionen davon, 
was ein &quot;Mitglied&quot; oder was &quot;Handel&quot; ist. Dies müssen Sie in diesem 
Fenster - unter Verwendung von &quot;Filter&quot; - spezifizieren.
<br><br>Je nachdem welche Elemente Sie wählen, kann es sein, dass nicht alle Filter-Bearbeitungsfelder 
sichtbar sind. Die folgenden Filter können erscheinen: 
<ul>
	<li><b>Mitgliedsfilter:</b> Mit diesem Filter spezifizieren Sie, welche  <a
	href="${pagePrefix}groups#member_groups"><u>Mitgliedsgruppe</u></a>(n) Sie als Mitglieder betrachten. 
	Sie können mehr als eine Gruppe spezifizieren. Sie <b>müssen</b> allerdings mindestens
	eine Gruppe spezifizieren.<br>
	Auf den Ergebnisseiten werden die hier von Ihnen gewählten Gruppen als &quot;Mitglieder&quot; 
	ausgewiesen. 
	<br><br>
	<li><b><a href="${pagePrefix}account_management#payment_filters"><u>Zahlungsfilter:</u></a></b> 
	Mit diesem Filter spezifizieren Sie, welche Überweisungstypen die Ergebnisse einschließen 
	sollen. Üblicherweise handelt es sich hier um das, was Sie unter &quot;Handel&quot; 
	verstehen.<br>
	Sie können hier nur einen einzigen Zahlungsfilter spezifizieren, beachten Sie aber bitte den 
	Unterschied zum unten beschriebenen Listenfeld &quot;Mehrere Zahlungsfilter&quot;, in welchem 
	Sie mehr als einen Filter definieren können. Falls es keinen für Ihre Zwecke passenden Typ gibt, 
	können Sie immer einen neuen Zahlungsfilter für Ihre Anforderungen einrichten. (Siehe unten)<br>
	<br>
	<b>Warnung:</b><br>
	Häufig enthält das Listenfeld &quot;Zahlungsfilter&quot; nur solche Zahlungen, welche für den 
	bereits von Ihnen gewählten Gruppenfilter relevant sind. Möglicherweise ist das Listenfeld 
	&quot;Zahlungsfilter&quot; auch leer: in diesem Fall haben Sie wahrscheinlich eine Gruppe gewählt, 
	für die keine Zahlungen definiert wurden. In diesem Fall können Sie mit der Auswertung  nicht 
	fortfahren. Dies können Sie auf zweierlei Art lösen: ENTWEDER wählen Sie eine andere Gruppe, 
	ODER Sie richten einen neuen Zahlungsfilter für diese Gruppe ein.  
	<br><br>Einrichten eines neuen Zahlungsfilters für Statistiken: 
	<ol>
		<li>gehen Sie zu &quot;Konten > Konten verwalten&quot; im Hauptmenü
		<li>klicken Sie dort auf das Bearbeitungssymbol von &quot;Mitgliedskonto&quot;
		<li>wählen Sie die mit &quot;Neuen Zahlungsfilter einfügen&quot; benannte Schaltfläche 
		ganz unten auf der erscheinenden Seite.
		<li>4.	Geben Sie hier die Spezifikationen des neuen Zahlungsfilters ein (lesen Sie dazu auch 
		die Hilfe auf dieser Seite). Geben Sie einen logischen Namen ein, und denken Sie 
		daran, das Kontrollkästchen  &quot;In Berichten anzeigen&quot; anzuklicken, 
		sonst werden die Ergebnisse nicht auf den Statistikseiten angezeigt (unter 
		&quot;Gruppensichtbarkeit&quot; müssen Sie allerdings nichts auswählen.<br>
		Die Überweisungen, die Sie in diesem Filter berücksichtigen möchten, wählen Sie bitte im 
		Listenfeld &quot;Überweisungstypen&quot;.
		<li>der Zahlungsfilter sollte im Listenfeld &quot;Zahlungsfilter&quot; auf der Auswahlseite 
		für die Statistiken erscheinen.
	</ol>
	Auf der Ergebnisseite der Statistiken wird der von Ihnen gewählte Zahlungstyp als 
	&quot;Handel&quot; ausgewiesen.
	<br><br>
	<li><b>Mehrere Zahlungsfilter:</b>Hier gibt es viele Überschneidungen mit dem vorherigen 
	Absatz, der Unterschied ist, dass Sie hier mehr als einen Filter wählen können. Wenn Sie 
	mehr als einen Zahlungsfilter wählen, werden die Ergebnisse für die ausgesuchten Zahlungsfilter 
	gezeigt und verglichen. Haben Sie lediglich einen Filter gewählt, so wird dieser in die darin 
	enthaltenen Überweisungstypen unterteilt, und die Ergebnisse werden für jeden Überweisungstyp 
	gezeigt.<br>
	<b>Warnung:</b> Wenn Sie mehr als einen Zahlungsfilter wählen, ist es wichtig, dass alle 
	gewählten Rubriken sich klar unterscheiden und sich nicht überschneiden. Da jeder Zahlungsfilter 
	mehrere Überweisungstypen enthalten kann, ist es möglich, dass einige Zahlungsfilter einen 
	Überweisungstyp gemeinsam haben. In diesem Fall ist es z.B. unmöglich Tortendiagramme zu 
	erstellen, in denen alle Anteile zusammen 100% ergeben müssen. Aus diesem Grund überprüft 
	das Programm, ob es innerhalb der Überweisungsfelder Überschneidungen gibt. 
	<br><br>
	<li><b>Brokerfilter:</b> Mit diesem Filter spezifizieren Sie, welche Benutzergruppe(n) Sie als 
	<a href="${pagePrefix}brokering"><u>Broker</u></a> betrachten. Genau wie beim Mitgliedsfilter 
	auch, können Sie hier mehr als eine Gruppe spezifizieren, <b>müssen</b> aber mindestens eine bestimmen. 
	Auf der Ergebnisseiten werden diese Gruppen als &quot;Broker&quot; ausgewiesen. 
</ul>
<br>
Obwohl das Cyclos-Programm normalerweise nur relevante Filter zeigt, kann es passieren, 
dass bestimmte Filterkombinationen keinen Sinn machen. Wenn Sie z.B. einen Gruppenfilter 
und einen Zahlungsfilter gewählt haben, die nicht zueinander passen (z.B. Gruppe: 
&quot;Mitglieder&quot; und Zahlungen : &quot;System an Gemeinschaft&quot;), dann ist natürlich 
kein brauchbares Ergebnis zu erwarten.
<br><br>Nicht alle Filter werden für alle Arten von Statistiken verwendet. Die Ergebnisseite zeigt 
normalerweise für jede Tabelle oder Grafik an, welche Filter für exakt diese Berechnung der 
Ergebnisse <a	href="#filtersUsed"><u>verwendet</u></a> wurden.

<hr>

<a name="results"></a>
<h2>Statistische  Ergebnisse</h2>
In Cyclos gibt es ein paar Regeln für die Präsentation der statistischen Ergebnisse. 
Hier sehen Sie eine Übersicht dieser Regeln: 
<ul>
	<li><a href="#tables"><u>Tabellen</u></a> sind die als Standard eingestellten 
	Darstellungsform für Statistiken. Klicken Sie auf den Link, um zu einer allgemeinen 
	Beschreibung der Tabellen zu gelangen. 
	<li><a href="#graphs"><u>Grafiken</u></a> werden nur gezeigt, wenn Sie das Kontrollkästchen 
	für Grafiken gewählt haben. Klicken Sie auf den Link, um zu einer allgemeinen Beschreibung 
	der Grafiken zu gelangen. 
	<li><a href="#tests"><u>Statistische Tests</u></a> werden durchgeführt, wo immer 
	dies vernünftig und möglich ist. 
	<li><a href="#calculation"><u>Generelle Regeln zur Berechnung</u></a> der 
	Statistiken finden Sie hier. 
	<li><a href="#numbers"><u>Präsentation und Genauigkeit der Zahlen</u></a>
	Im Allgemeinen bedeutet ein Ergebnis von 3 etwas anderes als ein Ergebnis von 3,00. 
	Klicken Sie auf den Verweis, um zu einer Beschreibung der Cyclos- Regeln zur Genauigkeit 
	und Darstellung der Zahlen zu gelangen. 
	<li>Wie sich Cyclos verhält, wenn  <a href="#nodata"><u>zu wenige Daten</u></a> verfügbar sind.
</ul>

<hr class="help">

<a name="tables"></a>
<h3>Statistische Tabellen </h3>
Die in Cyclos als Standard eingestellte Art der Darstellung von Statistiken ist die Tabelle. 
Die meisten Tabellen (aber nicht alle) haben folgende Form: 
<ul>
	<li><b>Erste Spalte:</b> Der Haupt-<a href="#periods"><u>Zeitraum</u></a>, 
	wie von Ihnen auf der vorhergehenden Seite definiert. 
	<li><b>Zweite Spalte:</b> Der Vergleichs-<a href="#periods"><u>Zeitraum</u></a>, falls von 
	Ihnen auf der vorhergehenden Seite definiert. 
	<li><b>Dritte Spalte:</b> das relative Wachstum (in %) zwischen dem zweiten Zeitraum
	und dem Hauptzeitraum.
	<li><b>Vierte Spalte:</b> Der <a href="#p"><u>p-Wert</u></a> eines statistischen Tests, 
	prüft wie unterschiedlich die Zahlen sind. Diese Spalte wird nicht immer gezeigt. 
</ul>
Obiges gilt normalerweise für &quot;Vergleichzeiträume&quot;; wenn Sie eine andere Methode 
gewählt haben, sehen die Tabellen möglicherweise anders aus.<br>
Wenn Sie nicht Diagramm gewählt haben, so erscheint unter der Tabelle eine 
kleine <a	href="#filtersUsed"><u>Tabelle</u></a> mit den von Ihnen für dieses Ergebnis 
spezifizierten Filtern. Andernfalls erscheint diese Information unterhalb der 
<a href="#graphs"><u>Grafik</u></a>.
<hr class="help">

<a name="graphs"></a>
<h3>Grafiken in Statistiken</h3>
Normalerweise zeigt die Grafik die gleichen Daten wie die Tabelle unmittelbar darüber.
Allerdings: manche Spalten mit Sekundärdaten (Daten, die sich aus anderen 
Spalten generieren) werden in der Tabelle nicht angezeigt, z.B. das Wachstumsvolumen 
zwischen zwei <a href="#periods"><u>Zeiträumen</u></a> oder der <a href="#p"><u>p-Wert</u></a>. 
Für eine Beschreibung der Daten selbst, klicken Sie bitte auf das Hilfesymbol der 
entsprechenden Tabelle (das Fenster über dem Grafik-Fenster). Dieser Abschnitt enthält 
nur Erklärungen zu ganz allgemeinen in Grafiken verwendeten Regeln. 
<br><br>Wenn Sie mit Ihrer Maus über eine Markierung in der Grafik fahren, wird Ihnen der 
entsprechende Wert des Datenpunktes angezeigt. Unterhalb der Grafik erscheint eine 
kleine <a href="#filtersUsed"><u>Tabelle</u></a> mit den von Ihnen für dieses Ergebnis 
spezifizierten <a href="#filters"><u>Filtern</u></a>. 

<hr class="help">


<a name="filtersUsed"></a>
<h3>Verwendete Filter</h3>
Unterhalb der Grafik oder der Tabelle erscheint eine kleine Tabelle mit den von Ihnen 
für dieses Ergebnis spezifizierten <a href="#filters"><u>Filtern</u></a>. 
Um diese Berechnungen durchführen zu können, muss Cyclos wissen, welche Art Mitglieder 
die Grafik enthalten soll – dies ist der <a href="${pagePrefix}groups#group_filters"><u>Gruppenfilter</u></a>. 
Das System sollte ebenfalls wissen, was Sie unter &quot;Handel&quot; verstehen – dies 
spezifiziert der &quot;Zahlungsfilter&quot;. Es können auch andere Arten von Filtern 
zur Verwendung kommen.<br>
Wird ein Filter für die Berechnungen nicht verwendet, steht auf dem Ausdruck 
&quot;nicht verwendet&quot; ausgedruckt. Wenn ein Filter verwendet wurde, Sie aber keine 
bestimmte Gruppe oder Zahlungen spezifiziert haben, dann werden alle Mitglieder oder 
Gruppen eingeschlossen. In dieser Tabelle werden nur die relevanten Filter gedruckt. 

<hr class="help">

<a name="calculation"></a>
<h3>Wie wird berechnet?</h3>
Im allgemeinen werden Ergebnisse über den angegebenen <i><a href="#periods"><u>Zeitraum</u></a></i> 
errechnet. Dies bedeutet, dass zum Beispiel auch alle Mitglieder, die zu irgendeinem Zeitpunkt 
innerhalb dieses Zeitraums Mitglied waren, berücksichtigt werden. In seltenen Fällen bezieht 
sich das Ergebnis auch nur auf den Stand am letzten Tag dieses Zeitraums; dies ist jeweils in 
der diesbezüglichen Hilfe angegeben. Bitte beachten Sie, dass es keinerlei Anpassungsmöglichkeit 
für &quot;Teilzeit-Mitglieder&quot; gibt. Zum Beispiel: die  Umsatz eines jeden Mitglieds 
innerhalb dieses Zeitraums werden gezählt, ohne Anpassung oder Korrektur dafür, dass das Mitglied 
möglicherweise nur während eines Tages dieses Zeitraums Mitglied war.<br>
Meist wird als Ergebnis der <a href="#median"><u>Mittelwert</u></a> verwendet, und nicht der Durchschnitt. 

<hr class="help">

<a name="numbers"></a>
<h3>Darstellung und Genauigkeit der Zahlen</h3>
Generell werden die Zahlen, wann immer möglich,innerhalb eines Bereichs angegeben. Dieser 
Bereich stellt einen 95% <a href="#range"><u>Vertrauensbereich</u></a>Vertrauensbereich um den 
<a href="#median"><u>Mittelwert</u></a> oder den Medianwert herum dar. In Tabellen können diese 
Zahlen auf drei verschiedene Arten angezeigt werden: 
<ul>
	<li><b>12.0</b> bedeutet, dass kein Vertrauensbereich hergestellt werden konnte, da entweder zu 
	wenige Daten verfügbar waren, oder aber weil die Zahl nicht auf einer Zahlenreihe basiert. 
	<li><b>12.0&nbsp;&#177;&nbsp;3.4</b> bedeutet einen symmetrischen Vertrauensbereich. 12.0 - 3.4 ist 
	die untere Grenze des Vertrauensbereichs; 12.0 + 3.4 die obere. 
	<li><b>12.0 (9.7 - 19.2)</b> bedeutet einen asymmetrischen Vertrauensbereich, und eine 
	<a href="#distribution"><u>ungleiche Verteilung</u></a>. Die Zahlen in den Klammern stellen die 
	untere und die obere Begrenzung des 	Vertrauensbereichs dar.
	<li><b>-</b>&nbsp; bedeutet, dass die Zahl nicht berechnet werden konnte, wahrscheinlich weil für 
	die verlässliche Kalkulation nicht genügend Daten zur Verfügung standen. 
</ul>
In Grafiken werden die Vertrauensbereiche durch Fehlerbalken um die Datenpunkte herum dargestellt.  
<hr class="help">

<a name="nodata"></a>
<h3>Zu wenige Daten</h3>
Statistische Analyse beruht auf dem Prinzip, eine Reihe von Beobachtungen zu bündeln, und durch 
eine Zahl oder einen Punkt in einer Grafik darzustellen. Was geschieht allerdings, wenn zu wenige 
Beobachtungen vorliegen? <br>
Theoretisch kann mit 3 oder mehr Beobachtungen in einem Datenpunkt ein statistischer Test durchgeführt 
werden. Allerdings ist dieser Datenpunkt recht unzuverlässig: je höher die Anzahl der Beobachtungen (n), 
desto zuverlässiger sind die Angaben zu Mittelwert, Median oder Bereich.<br>  
In Cyclos stellen wir keine statistischen Ergebnisse dar, die auf weniger als 15 Beobachtungen beruhen, 
da solche Angaben unserer Ansicht nach nicht verlässlich sind. 

<hr>

<h2>Ergebnisse der Schlüsselkriterien</h2>

<br><br>

<a name="reportsStatsKeydevelopmentsNumberOfMembers"></a>
<h3>Tabelle für die Anzahl der Mitglieder</h3>
Bitte lesen Sie den <a href="#results"><u>allgemeinen Abschnitt zu Ergebnissen</u></a>, für die 
generellen Regeln in Cyclos-Statistiken. 

<br><br>Die Tabelle enthält folgende Zeilen:
<ul>
	<li><b>Mitglieder:</b> die Anzahl der Mitglieder, in Übereinstimmung mit dem auf der vorherigen Seite 
	eingestellten <a href="#filters"><u>Filter</u></a>.  Dies berücksichtigt alle Mitglieder, die innerhalb 
	des von Ihnen spezifizierten <a href="#periods"><u>Zeitraums</u></a> Mitglied waren, auch wenn es während 
	dieses Zeitraums von der Mitgliedsliste entfernt wurde, oder aber während dieses Zeitraums erst Mitglied 
	wurde.
	<li><b>Neue Mitglieder:</b> jedes Mitglied, das innerhalb des angegebenen  Zeitraums in die spezifizierte 
	Mitgliedsgruppe verschoben wurde. 
	<li><b>Verschwundene Mitglieder:</b> jedes Mitglied, das innerhalb des angegebenen Zeitraums aus den 
	spezifizierten Mitgliedsgruppen verschoben wurde, und zwar in eine in diesem Filter nicht berücksichtigte 
	Gruppe.
</ul>
<hr class="help">

<a name="reportsStatsKeydevelopmentsGrossProduct"></a>
<h3>Tabelle für Umsatz</h3>
Bitte lesen Sie den <a href="#results"><u>allgemeinen Abschnitt zu Ergebnissen</u></a>, für die 
generellen Regeln in Cyclos-Statistiken.
<br><br>Die einzelne Zeile zeigt die <a href="#grossProduct"><u>Umsatz</u></a> für die
von Ihnen spezifitieren <a href="#filters"><u>Filter</u></a>.
<hr class="help">

<a name="reportsStatsKeydevelopmentsNumberOfTransactions"></a>
<h3>Tabelle für die Anzahl der Überweisungen</h3>
Bitte lesen Sie den <a href="#results"><u>allgemeinen Abschnitt zu Ergebnissen</u></a>, für die 
generellen Regeln in Cyclos-Statistiken.
<br><br>Die einzelne Zeile zeigt die <a href="#numberOfTransactions"><u>Anzahl der Überweisungen</u></a> 
in Übereinstimmung mit den von Ihnen spezifizierten <a href="#filters"><u>Filtern</u></a>.
<hr class="help">

<a name="reportsStatsKeydevelopmentsAverageAmountPerTransaction"></a>
<h3>Tabelle für den mittleren Überweisungsbetrag</h3>
Bitte lesen Sie den <a href="#results"><u>allgemeinen Abschnitt zu Ergebnissen</u></a>, für die 
generellen Regeln in Cyclos-Statistiken.
<br><br>Die einzelne Zeile zeigt den  <a href="#median"><u>Mittelwert</u></a> des Betrages pro Überweisung, 
in Übereinstimmung mit den von Ihnen spezifizierten <a href="#filters"><u>Filtern</u></a>.
<hr class="help">

<a name="reportsStatsKeydevelopmentsHighestAmountPerTransaction"></a>
<h3>Tabelle für den höchsten Überweisungsbetrag</h3>
Bitte lesen Sie den <a href="#results"><u>allgemeinen Abschnitt zu Ergebnissen</u></a>, für die 
generellen Regeln in Cyclos-Statistiken.
<br><br>Die einzelne Zeile zeigt den Höchstbetrag pro Überweisung, in Übereinstimmung mit den von 
Ihnen spezifizierten <a href="#filters"><u>Filtern</u></a>.
<hr class="help">

<a name="reportsStatsKeydevelopmentsNumberOfAds"></a>
<h3>Tabelle für die Anzahl der Inserate</h3>
Bitte lesen Sie den <a href="#results"><u>allgemeinen Abschnitt zu Ergebnissen</u></a>, für die 
generellen Regeln in Cyclos-Statistiken.
<br><br>Die Tabelle enthält folgende Zeilen:  
<ul>
	<li><b>Aktive Inserate:</b> die Anzahl der Inserate, die weder abgelaufen noch geplant sind, 
	sondern die am Ende des <a href="#periods"><u>Zeitraums</u></a> aktiv sind.
	<li><b>Geplante Inserate:</b> die Anzahl der zukünftigen (geplanten) Inserate am Ende 
	des Zeitraums. 
	<li><b>Abgelaufene Inserate:</b> die Anzahl der Inserate, die am Ende des Zeitraums 
	abgelaufen sind. 
	<li><b>Neue Inserate:</b> die Anzahl der Inserate, die während des Zeitraums neu 
	erstellt wurden. 
</ul>
<br><br>Bitte beachten Sie, dass sich die ersten drei Angaben auf das Ende des Zeitraums beziehen, 
während die letzte Angabe für den gesamten Zeitraum gilt.</i>.
<hr class="help">

<a name="reportsStatsKeydevelopmentsThroughTimeMonths"></a>
<a name="reportsStatsKeydevelopmentsThroughTimeQuarters"></a>
<a name="reportsStatsKeydevelopmentsThroughTimeYears"></a>
<h3>Tabelle für eine &quot;Über einen Zeitraum&quot;</h3>
Bitte lesen Sie den <a href="#results"><u>allgemeinen Abschnitt zu Ergebnissen</u></a>, für die 
generellen Regeln in Cyclos-Statistiken.
<br><br>Wenn Sie  &quot;Über einen Zeitraum&quot; wählen, erscheint eine historische Übersicht der gewählten 
Rubriken, für den von Ihnen gewählten Zeitabschnitt.<br> 
Die Rubriken sind die gleichen wie unter &quot;Vergleichszeiträume&quot;.<br>
Um es kurz zusammen zu fassen: 
<ul>
	<li><b>Anzahl der Mitglieder:</b> die Anzahl der Mitglieder während jedes  <a
		href="#periods"><u>Zeitraums</u></a>.
	<li><b>Umsatz:</b> während jedes Zeitraums. 
	<li><b>Anzahl der Überweisungen:</b> während jedes Zeitraums. 
	<li><b>Überweisungsbetrag:</b> durchschnittlicher Überweisungsbetrag während eines Zeitraums. 
	<li><b>Anzahl der Inserate:</b> Anzahl der aktiven Inserate am letzten Tag eines Zeitraums.
</ul>
Bei den Zeiträumen kann es sich um Monate, Quartale oder um Jahre handeln. Alles natürlich je nach 
den von Ihnen spezifizierten <a href="#filters"><u>Filtern</u></a>.
<hr>

<h2>Ergebnisse der Mitgliedsaktivitäten</h2>

<br><br>

<a name="reportsStatsActivitySinglePeriodGrossProduct"></a> <a
	name="reportsStatsActivityComparePeriodsGrossProduct"></a>
<h3>Tabelle für Umsatz pro Mitglied</h3>
Bitte lesen Sie den <a href="#results"><u>allgemeinen Abschnitt zu Ergebnissen</u></a>, für die 
generellen Regeln in Cyclos-Statistiken.
<br><br>Diese Tabelle zeigt den <a href="#median"><u>Mittelwert</u></a> <a href="#grossProduct"> der 
<u>Umsatz</u></a> je Mitglied, in zwei Zeilen:
<ul>
	<li><i>&quot;pro verdienendem Mitglied&quot;:</i> hier werden nur diejenigen Mitglieder berücksichtigt, die 
	während des abgefragten <a href="#periods"><u>Zeitraums</u></a> Zahlungseingänge zu verzeichnen 
	hatten.
	<li><i>für alle Mitglieder:</i> hier sind alle Mitglieder berücksichtigt. Aufgrund der Eigenschaften 
	des <a href="#median"><u>Mittelwert</u></a> ist dieser Wert Null (0), wenn mehr als die Hälfte 
	der Mitglieder keine Eingänge hatte. 
</ul>
Alles natürlich je nach den von Ihnen spezifizierten  <a href="#filters"><u>Filtern</u></a> .
<br><br>Für &quot;Vergleichszeiträume&quot;, zeigen die Spalten die Ergebnisse beider angeforderter
Zeiträume, danach zwei Spalten mit der Anzahl der Mitglieder, auf denen das Ergebnis basiert, 
gefolgt von dem zwischen den beiden Zeiträumen realisierten Wachstum, und dem 
<a href="#p"><u>p-Wert</u></a> für den Test zum Nachweis, dass sich die beiden Ergebnisse nicht 
unterscheiden.<br> 
Für &quot;einen Zeitraum&quot; wird nur das Ergebnis und die Anzahl der Mitglieder gezeigt.<br>

<hr class="help">

<a name="reportsStatsActivitySinglePeriodNumberTransactions"></a> <a
	name="reportsStatsActivityComparePeriodsNumberTransactions"></a>
<h3>Tabelle für die Anzahl der Überweisungen pro Mitglied</h3>
Bitte lesen Sie den <a href="#results"><u>allgemeinen Abschnitt zu Ergebnissen</u></a>, für die 
generellen Regeln in Cyclos-Statistiken.
<br><br>Diese Tabelle zeigt die  <a href="#median"><u>mittlere</u></a> <a
	href="#numberOfTransactions"><u>Anzahl der Überweisungen</u></a> pro Mitglied, in zwei Zeilen:<br>
<ul>
	<li>&quot;pro handelndem Mitglied&quot;: hier sind nur diejenigen Mitglieder berücksichtigt, 
	die während des angeforderten <a href="#periods"><u>Zeitraums</u></a> Handel getrieben haben; 
	hier werden sowohl ausgehende als auch eingehende Zahlungen als Handel gezählt. 
	<li><i>für alle Mitglieder:</i> hier sind alle Mitglieder berücksichtigt. Aufgrund der Eigenschaften 
	des <a href="#median"><u>Mittelwerts</u></a> ist dieser Wert Null (0), wenn mehr als die Hälfte der 
	Mitglieder keinen Handel getrieben hat.
</ul>
Alles natürlich je nach den von Ihnen spezifizierten  <a href="#filters"><u>Filtern</u></a>.
<br><br>Für &quot;Vergleichszeiträume&quot;, zeigen die Spalten die Ergebnisse beider angeforderter Zeiträume, 
danach zwei Spalten mit der Anzahl der Mitglieder, auf denen das Ergebnis basiert, gefolgt von dem zwischen 
den beiden Zeiträumen realisierten Wachstum, und dem <a href="#p"><u>p-Wert</u></a> für den 
Test zum Nachweis, dass sich die beiden Ergebnisse nicht unterscheiden.<br> 
Für &quot;einen Zeitraum&quot; wird nur das Ergebnis und die Anzahl der Mitglieder gezeigt.<br>
<hr class="help">

<a name="reportsStatsActivitySinglePeriodPercentageNoTrade"></a>
<a name="reportsStatsActivityComparePeriodsPercentageNoTrade"></a>
<h3>Tabelle für den Prozentsatz der Mitglieder, die keinen Handel treiben</h3>
Bitte lesen Sie den <a href="#results"><u>allgemeinen Abschnitt zu Ergebnissen</u></a>, für die 
generellen Regeln in Cyclos-Statistiken.
<br><br>Hier erscheint lediglich eine Spalte, die anzeigt, welcher Prozentsatz der Mitglieder während 
des von Ihnen angeforderten <a href="#periods"><u>Zeitraums</u></a> gar keinen Handel getrieben hat 
(d.h., die keinerlei Eingänge oder Ausgänge zu verzeichnen hatten). Wie immer richten sich 
&quot;Mitglieder&quot; und &quot;Handel&quot; nach den von Ihnen gewählten <a href="#filters"><u>Filtlern</u></a>. 

<br><br>Für &quot;Vergleichszeiträume&quot;, zeigen die Spalten die Ergebnisse beider angeforderter Zeiträume, 
danach zwei Spalten mit der Anzahl der Mitglieder, auf denen das Ergebnis basiert, gefolgt von dem zwischen 
den beiden Zeiträumen realisierten Wachstum, und dem <a	href="#p"><u>p-Wert</u></a> für den Test zum Nachweis, 
dass sich die beiden Ergebnisse nicht unterscheiden.
<hr class="help">

<a name="reportsStatsActivitySinglePeriodLoginTimes"></a>
<a name="reportsStatsActivityComparePeriodsLoginTimes"></a>
<h3>Tabelle für die Anmeldezeiten pro Mitglied, für einen Zeitraum</h3>
Bitte lesen Sie den <a href="#results"><u>allgemeinen Abschnitt zu Ergebnissen</u></a>, für die 
generellen Regeln in Cyclos-Statistiken.
<br><br>Hier erscheint lediglich eine Zeile, die die Anzahl der Anmeldungen pro Mitglied während des 
<a href="#periods"><u>Zeitraums</u></a> angibt. Wie immer richten sich &quot;Mitglieder&quot; nach den 
von Ihnen gewählten Gruppen-<a href="#filters"><u>Filter</u></a> – gezählt wird jedes Mitglied, 
das zu irgendeinem Zeitpunkt innerhalb des spezifizierten Zeitraums Mitglied war. 
<br><br>Für &quot;Vergleichszeiträume&quot;, zeigen die Spalten die Ergebnisse beider angeforderter Zeiträume, 
danach zwei Spalten mit der Anzahl der Mitglieder, auf denen das Ergebnis basiert, gefolgt von dem zwischen 
den beiden Zeiträumen realisierten Wachstum, und dem <a	href="#p">p-Wert</a> für den Test zum Nachweis, 
dass sich die beiden Ergebnisse nicht unterscheiden.  
<hr class="help">

<a name="reportsStatsActivityThroughTimeGrossProduct"></a>
<h3>Tabelle für Umsatz pro Mitglied, als Verlauf</h3>
Bitte lesen Sie den <a href="#results"><u>allgemeinen Abschnitt zu Ergebnissen</u></a>, für die 
generellen Regeln in Cyclos-Statistiken.
<br><br>Diese Tabelle zeigt eine Übersicht der  <a href="#median"><u>mittleren</u></a> <a
	href="#grossProduct"><u>Umsatz</u></a> pro Mitglied, für jeden Punkt innerhalb des von 
	Ihnen spezifizierten Zeitabschnitts. Dies geschieht für zwei Arten von Mitgliedern: 
<ul>
	<li>&quot;Mit Einkommen&quot;: dies sind die Umsatz für Mitglieder, die innerhalb des 
	spezifizierten Monats, Quartals oder Jahres ein Einkommen zu verzeichnen hatten. 
	<li>&quot;Alle&quot;: dies berücksichtigt alle innerhalb des spezifizierten Monats, Quartals oder 
	Jahres verfügbaren Mitglieder.
</ul>
Für beide Gruppen ist die Anzahl der Mitglieder, auf denen die  Umsatz basieren, in den 
letzten beiden Spalten angegeben..<br>
Bitte beachten Sie, dass die Ergebnisse für gewöhnlich um so niedriger sind, je kürzer die Zeiträume
für jeden Punkt innerhalb der Grafik oder der Tabelle sind.
<hr class="help">

<a name="reportsStatsActivityThroughTimeNumberTransactions"></a>
<h3>Verlaufstabelle für die Anzahl der Überweisungen, pro Mitglied</h3>
Bitte lesen Sie den <a href="#results"><u>allgemeinen Abschnitt zu Ergebnissen</u></a>, für die 
generellen Regeln in Cyclos-Statistiken.
<br><br>Diese Tabelle zeigt die  <a href="#median"><u>mittleren</u></a> <a href="#numberOfTransactions"><u>
Anzahl der Überweisungen</u></a> pro Mitglied,  während des spezifizierten Zeitabschnitts; hier werden 
sowohl ausgehende als auch eingehende Überweisungen berücksichtigt.<br>
Dies geschieht für zwei Arten von Mitgliedern: 
<ul>
	<li>&quot;Tauschende Mitglieder&quot;: dies ist die Anzahl der Überweisungen für Mitglieder, die 
	innerhalb des spezifizierten Monats, Quartals oder Jahres Handel getrieben haben. (Tauschhandel 
	bedeutet: haben jede Art von Betrag erhalten oder gezahlt). 
	<li>&quot;Alle&quot;: die berücksichtigt alle innerhalb des spezifizierten Monats, Quartals oder 
	Jahres verfügbaren Mitglieder. 
</ul>
Für beide Gruppen ist die Anzahl der Mitglieder, auf denen die Umsatz basieren, in den 
letzten beiden Spalten angegeben.<br>
Bitte beachten Sie, dass die Ergebnisse für gewöhnlich um so niedriger sind, je kürzer die Zeiträume
für jeden Punkt innerhalb der Grafik oder der Tabelle sind. 
<hr class="help">

<a name="reportsStatsActivityThroughTimePercentageNoTrade"></a>
<h3>Tabelle der nicht tauschenden Mitglieder, für einen Zeitraum</h3>
Bitte lesen Sie den <a href="#results"><u>allgemeinen Abschnitt zu Ergebnissen</u></a>, für die 
generellen Regeln in Cyclos-Statistiken.
<br><br>Diese Tabelle zeigt eine Übersicht des Mitgliedsprozentsatzes, die innerhalb eines 
<a href="#periods"><u>Zeitraums</u></a>, keinen Handel betrieben haben, während des von Ihnen 
spezifizierten Zeitabschnitts. 
<br><br>Ein Mitglied wird als &quot;nicht Handel treibend&quot; betrachtet, wenn es in dem von 
Ihnen spezifizierten Monat, Quartal oder Jahr keinerlei Überweisung getätigt hat 
(weder als Auftraggeber noch als Empfänger, und in Übereinstimmung mit dem von Ihnen festgelegten 
Zahlungs-<a href="#filters"><u>Filter</u></a>). Natürlich ist der Prozentsatz der Nicht-Handel-Treibenden 
um so höher, je kürzer die Zeiträume für jeden Datenpunkt ist. In einer monatlichen Ergebnis-Grafik 
ist der Prozentsatz Nicht-Handel-Treibender  natürlich sehr viel höher als in einer Grafik der 
jährlichen Ergebnisse.<br>
Die letzte Spalte der Tabelle zeigt die Anzahl der Mitglieder, auf der die Ergebnis-Werte beruhen. 

<hr class="help">

<a name="reportsStatsActivityThroughTimeLoginTimes"></a>
<h3>Tabelle für Anmeldungen pro Mitglied, als Verlauf</h3>
Bitte lesen Sie den <a href="#results"><u>allgemeinen Abschnitt zu Ergebnissen</u></a>, für die 
generellen Regeln in Cyclos-Statistiken.
<br><br>Diese Tabelle verschafft Ihnen einen Überblick über den Verlauf der  <a href="#median">
<u>mittleren</u></a> Anzahl der Anmeldungen eines Mitglieds im System, über einen spezifizierten 
Zeitraum. Nicht nur die Anzahl der Anmeldungen eines jeden Monats, Quartals oder Jahres erscheint, 
sondern die letzte Spalte enthält auch die Anzahl der Mitglieder, auf der diese Berechnung beruht.<br>
Bitte beachten Sie, dass die Anzahl der Anmeldungen pro Mitglied um so niedriger sind, je kürzer die 
Zeiträume für jeden Datenpunkt sind. 
<hr class="help">

<a name="reportsStatsActivityHistogramGrossProduct"></a>
<h3>Diagramm für Umsatz pro Mitglied</h3>
Bitte lesen Sie den <a href="#results"><u>allgemeinen Abschnitt zu Ergebnissen</u></a>, für die 
generellen Regeln in Cyclos-Statistiken.
<br><br>Diese Grafik zeigt ein  <a href="#histo"><u>Balkendiagramm</u></a> der <a
	href="#grossProduct"><u>Umsatz</u></a> über den Hauptzeitraum, in 
	Übereinstimmung mit den von Ihnen definierten <a href="#filters"><u>Filtern</u></a>.
<hr class="help">

<a name="reportsStatsActivityHistogramNumberTransactions"></a>
<h3>Diagramm für die Anzahl der Überweisungen pro Mitglied</h3>
Bitte lesen Sie den <a href="#results"><u>allgemeinen Abschnitt zu Ergebnissen</u></a>, für die 
generellen Regeln in Cyclos-Statistiken.
<br><br>Diese Grafik zeigt ein  <a href="#histo"><u>Balkendiagramm</u></a> der 
<a href="#numberOfTransactions"><u>Anzahl der Überweisungen</u></a> pro Mitglied über den 
Haupt-<a href="#periods"><u>Zeitraum</u></a>, in Übereinstimmung mit den von Ihnen definierten
<a href="#filters"><u>Filtern</u></a>.
<hr class="help">

<a name="reportsStatsActivityHistogramLogins"></a>
<h3>Diagramm für die Anzahl der Anmeldungen pro Mitglied</h3>
Bitte lesen Sie den <a href="#results"><u>allgemeinen Abschnitt zu Ergebnissen</u></a>, für die 
generellen Regeln in Cyclos-Statistiken.
<br><br>Diese Grafik zeigt ein  <a href="#histo"><u>Balkendiagramm</u></a> der Anzahl der Anmeldungen pro 
Mitglied über den Haupt-<a href="#periods"><u>Zeitraum</u></a>, in Übereinstimmung mit den von Ihnen 
definierten Gruppen-<a href="#filters"><u>Filtern</u></a>.
<hr class="help">

<a name="reportsStatsActivityToptenGrossProduct"></a>
<h3>Die 10 aktivsten Mitglieder nach Umsatz</h3>
Bitte lesen Sie den <a href="#results"><u>allgemeinen Abschnitt zu Ergebnissen</u></a>, für die 
generellen Regeln in Cyclos-Statistiken..
<br><br>Diese Tabelle zeigt Ihnen die 10 Mitglieder mit dem höchsten  <a href="#grossProduct">
<u>Umsatz</u></a> während des spezifizierten Haupt-<a href="#periods"><u></u></a>.
<hr class="help">

<a name="reportsStatsActivityToptenNumberTransactions"></a>
<h3>Die 10 aktivsten Mitglieder nach Anzahl der Überweisungen</h3>
Bitte lesen Sie den <a href="#results"><u>allgemeinen Abschnitt zu Ergebnissen</u></a>, für die 
generellen Regeln in Cyclos-Statistiken.
<br><br>Diese Tabelle zeigt Ihnen die 10 Mitglieder mit der höchsten  <a	href="#numberOfTransactions">
<u>Anzahl an Überweisungen</u></a> innerhalb des spezifizierten Haupt-<a href="#periods"><u>Zeitraums</u></a>.
<hr class="help">

<a name="reportsStatsActivityToptenLogin"></a>
<h3>Die 10 aktivsten Mitglieder nach Anzahl der Anmeldungen</h3>
Bitte lesen Sie den <a href="#results"><u>allgemeinen Abschnitt zu Ergebnissen</u></a>, für die 
generellen Regeln in Cyclos-Statistiken.
<br><br>Diese Tabelle zeigt Ihnen die 10 Mitglieder mit der höchsten Anzahl an Anmeldungen innerhalb 
des spezifizierten  Haupt-<a href="#periods"><u>Zeitraums</u></a>.
<hr class="help">
<br><br>

<a name="reportsStatsFinancesSinglePeriodOverview"></a>
<h3>Übersicht Finanzen während eines Zeitraums</h3>
Bitte lesen Sie den <a href="#results"><u>allgemeinen Abschnitt zu Ergebnissen</u></a>, für die 
generellen Regeln in Cyclos-Statistiken.
<br><br>Diese Tabelle zeigt eine Übersicht der eingehenden und ausgehenden Zahlungen für das spezifizierte 
Systemkonto. Die letzte Spalte zeigt die Differenz zwischen Einnahmen und Ausgaben. 
<br><br>Die von Ihnen spezifizierten Zahlungs-<a href="#filters"><u>Filter</u></a> erscheinen in 
der Tabelle; von Ihnen nicht spezifizierte, für dieses Konto aber relevante Filter, werden gesammelt 
als &quot;Andere&quot; zusammengefasst. Bitte beachten Sie, dass diese &quot;Andere&quot; Kategorie 
in der Grafik nicht dargestellt wird, da es sich hier um eine sehr hohe Zahl handelt, die die Grafik 
verzerren würde, bzw. die anderen Datenpunkte aussagelos machen würde. 

<br><br>Wenn Sie nur einen Zahlungsfilter gewählt haben, so wird dieser Zahlungsfilter in die darin 
enthaltenen Überweisungstypen unterteilt, und die Überweisungstypen erscheinen in der Tabelle. 
Andernfalls erscheinen die Zahlungsfilter.<br>
Wird auch eine Grafik gezeigt, so erscheinen unter der Grafik die gewählten Filter; andernfalls 
erscheinen sie unter der Tabelle.
<hr class="help">

<a name="reportsStatsFinancesSinglePeriodIncome"></a>
<a name="reportsStatsFinancesSinglePeriodExpenditure"></a>
<h3>Einkünfte oder Ausgaben über einen Zeitraum</h3>
Bitte lesen Sie den <a href="#results"><u>allgemeinen Abschnitt zu Ergebnissen</u></a>, für die 
generellen Regeln in Cyclos-Statistiken.
<br><br>Diese Tabelle oder Grafik zeigt eine Übersicht über die Einkünfte (wenn Sie Einkünfte gewählt haben) 
oder die Ausgaben (wenn Sie Ausgaben gewählt haben) eines Systemkontos.<br>
Die Tabelle zeigt in den Spalten zwei spezifizierte <a href="#periods"><u>Zeiträume</u></a>, wobei die 
dritte Spalte das Wachstum zwischen Zeitraum I und Zeitraum II angibt.<br>
Die Zeilen der Tabelle zeigen die von Ihnen spezifizierten Zahlungs-<a href="#filters"><u>Filter</u></a>. 

<br><br>Um die Übersicht zu behalten, raten wir dazu, bei der Gestaltung dieser Tabelle/Grafik nicht zu 
viele Zahlungsfilter zu wählen.<br>
<hr class="help">

<a name="reportsStatsFinancesThroughTimeIncome"></a>
<a name="reportsStatsFinancesThroughTimeExpenditure"></a>
<h3>Verlauf Einnahmen oder Ausgaben</h3>
Bitte lesen Sie den <a href="#results"><u>allgemeinen Abschnitt zu Ergebnissen</u></a>, für die 
generellen Regeln in Cyclos-Statistiken.
<br><br>Diese Tabelle zeigt eine Übersicht des Verlaufs, in den Kategorien von Einkünften oder 
Ausgaben Diese können interessant sein, um die Entwicklungen bestimmter Ausgaben über eine 
längere Zeit hinweg zu betrachten.<br>
Jeder angeforderte Zahlungsfilter hat innerhalb der Tabelle eine Spalte, und eine Datenreihe in 
der Grafik. Wir raten Ihnen, in dieser Grafik nicht zu viele Datenreihen (Zahlungsfilter) 
anzufordern, da die Grafik sonst sehr unübersichtlich wird. 

<hr>


<br><br><a name="glossary"></a>
<h2>Glossar der verwendeten Begriffe</h2>
<br><br>


<a name="range"></a><b>Vertrauensbereich</b>
<br><br>Ein Vertrauensbereich ist ein Anzeichen für die Genauigkeit der Daten. Er bedeutet, 
dass die ermittelten Zahlen mit einer Wahrscheinlichkeit von 95% innerhalb dieser Bereiche 
liegen. Häufig werden &quot;Standard Abweichung&quot; oder &quot;Standardabweichung vom 
Mittelwert&quot; benutzt, um die Genauigkeit der Daten anzugeben. Wir verwenden diese nicht, 
da wir glauben, dass ein Konfidenzintervall/Vertrauensbereich von 95% sehr viel intuitiver 
ist, und in der realen Welt tatsächlich eine Bedeutung hat (wohingegen die Standardabweichung 
doch eher abstrakt ist). 
<br><br>Die Berechnung von Vertrauensbereichen basiert normalerweise auf der Annahme einer 
irgendwie gearteten <a	href="#distribution"><u>Verteilung</u></a> der zugrunde liegenden 
Daten; in unserem Fall, da wir keine normale Verteilung annehmen  können, verwenden wir eine 
binominale Verteilung von Rangsummen um den Median herum, um den Vertrauensbereich zu berechnen. 
Dies bedeutet, dass die absoluten Werte nicht direkt verwendet werden, sondern von niedrig 
nach hoch eingestuft werden. Diese Rangsummen werden verwendet, um Vertrauensbereiche zu 
berechnen. Dies kann zu asymmetrischen Vertrauensbereichen führen. 
<hr class='help'>

<a name="distribution"></a> <b>Verteilung und Schiefe </b>
<br><br>In Statistiken basiert ein Resultat auf einer Reihe von Beobachtungen, die der 
Berechnung des Mittelwerts oder <a href="#median"><u>Mittel</u></a> zugrunde liegen. 
All diese einzelnen Beobachtungen zusammen folgen üblicherweise einem Muster, welches 
Zahlenverteilung genannt wird. Die natürlichste und häufigste Verteilung ist die 
&quot;Normal-Verteilung&quot;, in der der zentrale Wert am häufigsten vorkommt, während die 
Ausreißer um so seltener werden, je weiter sie vom Zentralwert abweichen. Eine normale 
Verteilung ist völlig symmetrisch.<br>
Die meisten statistischen Methoden basieren auf der Annahmen einer normalen Verteilung. 
Allerdings ist das tägliche Leben oft nicht so perfekt, sondern die Erfahrung zeigt, 
dass die Daten in den Cyclos-Datenbanken ganz und gar nicht gleichmäßig verteilt sind. 
Cyclos-Daten erscheinen häufig schief, mit asymmetrischer Verteilung. Um ein Beispiel anzugeben: 
es kann sein, dass Mitglieder, die  höhere als normale Umsatz haben, häufig weiter 
entfernt vom Mittelwert liegen, als Mitglieder mit Umsätzen, die niedriger als der 
Mittelwert sind.<br>
Um die Verteilung von etwas darzustellen, verwenden wir &quot;<a href="#histo"><u>Diagramme</u></a>&quot;.<br>
<hr class='help'>

<a name="grossProduct"></a> <b>Umsatz</b>
<br><br>Dies ist die Summe aller <b>eingehenden</b> Überweisungen über einen gewissen 
<a href="#periods"><u>Zeitraum</u></a>. Also alle während eines Zeitraums eingenommenen Einheiten.
<hr class='help'>

<a name="histo"></a> <b>Diagramm</b>
<br><br>Ein Balkendiagramm ist eine Grafik, die darstellt, wie sich eine bestimmte Beobachtung 
über einen Bestand verteilt. Die horizontale X-Achse der Grafik ist der Parameter, der Sie 
interessiert (z.B. Bruttoeinnahme jedes Mitglieds). Dies ist in logische Gruppen unterteilt, 
zum Beispiel: Umsatz von 1 - 100 Einheiten pro Monat, 100 – 200 Einheiten pro Monate usw. 
Vertikal, auf der Y-Achse, sehen Sie die Anzahl der in jeder X-Achsen-Gruppe gezählten Beobachtungen. 
Zurück zu unserem Beispiel: 5 Mitglieder in der Gruppe &quot;0-100 Einheiten pro Monat&quot;, 
20 Mitglieder in der Gruppe &quot;100-200 Einheiten pro Monate&quot; usw.<br>
Die daraus resultierende Grafik zeigt die  <a href="#distribution"><u><u>Verteilung</u></u></a>
der Umsatz über die Anzahl der Mitglieder hinweg.<br>
Bitte beachten Sie, dass in unseren Diagrammen diejenigen Beobachtungen, die direkt auf der Grenzlinie 
zwischen 2 Balken liegen, der jeweils höheren Kategorie zugeschlagen werden. 

<br><br><b>Anmerkung:</b> : Das Programm berechnet automatisch eine optimale Unterteilung der X-Achse 
in vernünftige &quot;Klassen&quot;. Im Falle sehr auffälliger Verteilungen (z.B. wenn sich bei 
der großen Mehrheit der Mitglieder rein gar nichts tut) der zugrunde liegenden Daten, kann es sein, 
dass die Aufteilung in Klassen entlang der X-Achse visuell nicht optimal erscheint. 

<hr class='help'>

<a name="median"></a> <b>Mittelwert</b>
<br><br>Normalerweise wird der Mittelwert oder der Durchschnittswert verwendet, um einen auf einer Reihe 
von Beobachtungen basierenden Wert anzugeben. Der Mittelwert ist allerdings sehr empfindlich für 
extreme Ausreißer: wenn ein Mitglied um das 20fache aktiver ist als der Rest der Mitglieder, dann 
beeinflusst dieses eine Mitglied den Mittelwert über Gebühr, und das, obwohl es nicht im Mindesten 
repräsentativ ist. Eine Lösung hierfür ist, den Median statt des Mittelwerts zu verwenden. Der Medianwert 
ist das Zentrum der <a href="#distribution"><u>Verteilung</u></a>: 50% aller Beobachtungen sind geringer 
als der Medianwert, und 50% der Beobachtungen liegen darüber. Der Median ist Ausreißern gegenüber 
unempfindlich.<br>
Da es sich in Cyclos oft um Daten mit extremen Ausreißern, oder mit schiefer Verteilung handelt, 
wird für Cyclos-Statistiken ausschließlich der Medianwert verwendet – es sei denn, etwas Anderes 
ist ausdrücklich angegeben. In solchen Fällen (wie der Statistik in Cyclos) ist die Verwendung des 
Mittelwerts im statistischen Bereich Standard. 
<br><br>Wird der Mittelwert aus einer Reihe von ganzen Zahlen errechnet, verwenden wir eine ausgleichende 
oder Fairness-Korrektur. Die Mittelwerte der Reihe {0,1,2,2,3,3,3,3,4} und der Reihe {2,3,3,3,3,4,5,6,7} 
sind in beiden Fällen 3, obwohl in der ersten Reihe die erste 3, ( gestrichen wird), und in der 
zweiten Reihe die letzte 3 gestrichen wird. Da dies nicht &quot;fair&quot; erscheint, werden alle 
Elemente mit dem Wert 3 gleichmäßig über ein Spanne von 2,5 bis 3,5 verteilt, und der Wert aus dieser 
Spanne über Interpolation ermittelt. Dies macht natürlich keinen Sinn mit Reihen von gebrochenen Zahlen, 
die oben beschriebene Vorgehensweise findet also nur bei ganzen Zahlen Anwendung. 

<br><br>Die Verwendung des Mittelwertes statt des Durchschnitts hat einige <b>Konsequenzen</b>:
<ul>
	<li>Oft kommen bei den Berechnungen gerundete Beträge heraus, vor allem dann, wenn der Mittelwert 
	auf wenigen Beobachtungen basiert.<br>
	<li>Da Bereiche auf Grundlage von Rangnummern berechnet werden, sind bestimmte Spannen um den 
  Mittelwert herum häufig asymmetrisch. Zieht man allerdings in Betracht, dass ja die zugrunde liegende 
  Verteilung auch nicht symmetrisch ist, erscheint das ganz natürlich.<br>
	<li>Enthält die zugrunde liegende Verteilung eine größere Anzahl von Nullen (0) (mehr als 50%), so 
	ist der Mittelwert natürlich ebenfalls 0. <br>
</ul>
<hr class='help'>

<a name="membersNotTrading"></a> <b>Nicht Handel treibende Mitglieder </b>
<br><br>Dies sind Mitglieder mit 0 Überweisungen. Es gibt also weder eingehende noch 
ausgehende Überweisungen. 
<hr class='help'>

<a name="N"></a> <b>n</b>
<br><br>die Anzahl der Elemente in einer Zahlenreihe (n = Grundgesamtheit).
<hr class='help'>

<a name="numberOfTransactions"></a> <b>Anzahl der Überweisungen </b>
<br><br>Im Gegensatz zu den <a href="#grossProduct"><u>Umsatz</u></a>, werden für die 
Summe der Überweisungen eines Mitglieds sowohl eingehende als auch ausgehende Überweisungen 
eingerechnet. 
<hr class='help'>

<a name="p"></a> <b>p-Wert</b>
<br><br>Beim Vergleich zweier oder mehrerer  <a href="#periods"><u>Zeiträume</u></a>, wird 
wann immer möglich eine statistische Tests durchgeführt. Ziel dieser Tests ist es, 
zu errechnen um wie viel zwei Werte von einander abweichen.<br>
Das Ergebnis der Tests wird als eine Zahl dargestellt: the &quot;p-Wert&quot;.
Dieser Wert zeigt die Möglichkeit an, dass zwei Mittelwerte (oder Mittel) auf einer 
gleichen Bestand beruhen. Einfach ausgedrückt: Die Differenz der Zahlen ist gleich. 
Je kleiner der p-Wert, desto stärker unterscheiden sich die Zahlen.<br>
Üblicherweise sagt man, dass sich Zahlen wirklich unterscheiden, wenn p kleiner als 5% ist. 
Dies nennt man eine &quot;statistisch bedeutsame Differenz&quot;. Einfach ausgedrückt: 
&quot;ist p< 0,05 sagen wir, dass Zahlen sich wirklich unterscheiden. Ist p größer, dann 
können wir nicht wirklich sicher sein, ob die Zahlen unterschiedlich sind oder nicht; 
um auf der sicheren Seite zu sein, gehen wir daher davon aus dass sie nicht unterschiedlich sind.&quot;<br> 
Jeder p-Wert der kleiner ist als 5% wird <b>fett</b> gedruckt.<br>
<hr class='help'>

<a name="tests"></a><b>Statistische Tests</b>
<br><br>Ein statistischer Test wird normalerweise durchgeführt, wenn Sie wissen wollen ,ob 
sich zwei (oder mehr) Ergebnisse wirklich unterscheiden – wenn Sie zum Beispiel wissen möchten, 
ob sich die Aktivitäten der Mitglieder in diesem Jahr im Vergleich zum letzten Jahr erhöht haben.  
Ist ein Unterschied von 5% wirklich ein Unterschied? Und ein  Unterschied von 10%?  20%? 
Ab wann nennen wir einen Unterschied einen wirklichen Unterschied, und wo betrachten wir ihn 
einfach als einen Zufall?<br>
Der statistische Test kann Ihnen sagen, ob es sich um eine wirkliche Differenz handelt, oder 
ob der wahrgenommene &quot;Unterschied&quot; noch innerhalb der normalen Bandbreite der Variationen 
und Koinzidenzen liegt, und daher nicht als wirklich different angesehen werden sollte.  Dies hängt 
ab von der zugrunde liegenden <a href="#distribution"><u>Verteilung</u></a>, dem Probenumfang, und der 
Variationen innerhalb der Bestand.<br>
Der Testtyp ist normalerweise in der Hilfedatei erwähnt. Häufig verwendete Tests sind der Wilcoxon 
Rangsummentest, und der Binominaltest für zwei Probengrößen. Diese Tests gehen nicht von einer 
zugrunde liegenden Verteilung aus, da wir in den meisten Fällen nicht von normalen Verteilungen 
ausgehen können.<br>
Das Ergebnis eines statistischen Tests wird als <a href="#p"><u>p-Wert</u></a> ausgedrückt.<br>

</div> <%--  page-break end --%>