<div style="page-break-after: always;">
<span class="admin broker">
<br><br>Alle Konfigurationen in Zusammenhang mit Konten und 
Überweisungen können in den Kontoverwaltungsfunktionen getätigt werden.

<i>Wo ist es zu finden?</i><br>
Die Kontenverwaltung ist zugänglich über das &quot;Menü: Konten > Konten verwalten&quot;.
<hr>

<A NAME="currencies"></A>
<h2>Währungen</h2>
Es ist möglich, neue Währungen zu schaffen, und diese mit Konten zu verbinden. 
Pro Gruppe kann eine Währung als Standard eingestellt werden.

<i>Wo ist es zu finden?</i><br>
Der Bereich Währung ist zugänglich über &quot;Menü: Konten > Währungen verwalten&quot;.
<hr class="help">

<A NAME="currency_search"></A>
<h3>Währungen suchen</h3>
Die Seite Währungsliste zeigt eine Liste mit den im System konfigurierten Währungen. Eine 
Währung kann an einen bestimmten Kontentyp gebunden sein. Um eine neue Währung hinzu zufügen, 
klicken Sie auf &quot;Neue Währung&quot; unten rechts.<br>
Um eine Währung zu löschen oder zu bearbeiten, klicken Sie auf die Symbole in der Liste.
<ul>
	<li><img border="0" src="${images}/edit.gif" width="16" height="16">
	Dieses Symbol anklicken, um die Währung zu ändern.
	<li><img border="0" src="${images}/delete.gif" width="16" height="16">
	Dieses Symbol anklicken, um die Währung zu löschen.
</ul>
<hr class="help">

<A NAME="currency_details"></A>
<h3>Währung ändern / neue Währung einfügen</h3>
Auf der Seite &quot;Neue Währung&quot; können Sie die Währung definieren. Die folgenden 
Felder sind verfügbar:
<ul>
	<li><b>Name:</b> Interner Name (erscheint nirgendwo)
	<li><b>Symbol:</b> Wird auf Seiten erscheinen. Zum Beispiel nach der Währung, 
	im Preisfeld eines Inserats
	<li><b>Anzeigemuster:</b> Hier können Sie den Währungsnamen/das Symbol einstellen, und 
	wo dieses erscheinen soll. Symbol oder Name können sowohl vor als auch hinter dem 
	Betrag erscheinen. Bei manchen Währungen (wie z.B. dem Dollar) erscheint das Symbol 
	vor dem Betrag, bei anderen wiederum danach. Dieses Währungssymbol erscheint in Listen 
	und in Nachrichten.<br>
	Symbole werden nicht immer von den Browsern unterstützt, ein gutes Beispiel dafür ist der 
	Euro. In diesem Fall verwenden Sie besser das Unicode-Symbol (z.B. &amp;euro;).
	<li><b>Beschreibung:</b> Nur zur internen Information (erscheint nirgendwo)
</ul>
<hr class="help">
</span>

<hr>
<span class="admin broker"> <a name="accounts"></a>
<h2>Konten</h2>
Cyclos-Konten sind entweder Systemkonten oder Mitgliedskonten. Beide Typen sind 
mit einer Währung verbunden und können Einheiten enthalten, die von und auf andere 
Konten überwiesen werden können (sofern es Überweisungstypen für Überweisungen 
zwischen diesen beiden Konten gibt).<br>
Im Gegensatz zu einem Mitgliedskonto ist ein Systemkonto nicht mit einem Eigentümer 
verbunden. Berechtigte Administratoren können von diesen Systemkonten Zahlungen 
auf andere Systemkonten oder auf Mitgliedskonten tätigen.<br>
Ein Mitglied kann kein, ein oder mehrere verbundene Mitgliedskonten haben und kann 
Zahlungen zwischen diesen eigenen Konten, auf Konten anderer Mitglieder oder auf 
Systemkonten tätigen.

Wird ein neues Konto vom Typ &quot;Mitglied&quot; eingerichtet, so ist es noch keinem 
Mitglied zugeordnet. Folgende Schritte müssen durchgeführt werden, damit der Kontentyp 
für das Mitglied überhaupt verwendbar wird:
<ol>
	<li><b>Zuordnung des Kontos zu einer Gruppe:</b> Erst nach Zuordnung des Kontos zu 
	einer Mitgliedsgruppe erhalten Mitglieder dieser Gruppe Zugang zu diesem Konto. 
	Dies kann über die <a href="${pagePrefix}groups#manage_groups">
	<u>Gruppeneinstellungen</u></a> geschehen.
	<li><b>Überweisungstypen:</b> Bevor ein Mitglied Zahlungen von diesem Konto tätigen 
	oder aber Einzahlungen erhalten kann, müssen Sie die &quot;<a href="#transaction_types">
	<u>Überweisungstypen</u></a>&quot; einrichten und zuordnen.
	Dies kann über das Fenster &quot;Überweisungstypen&quot; geschehen, welches sich auf der 
	gleichen Seite wie das Fenster zur Einstellung der Eigenschaften der Überweisungstypen befindet.
	<li><b>Berechtigungen:</b> Und natürlich müssen Sie auch die <a
		href="${pagePrefix}groups#manage_groups"><u>Berechtigungen</u></a> einstellen.
</ol>
Neben den Überweisungstypen gibt es noch eine Anzahl von anderen Dingen, die im direkten 
Zusammenhang mit den Konten stehen, z.B. Darlehen, Überweisungsgebühren, Kontogebühren und 
Zahlungsfilter.
<br><br>Die Cyclos-Standardkonfiguration bietet eine Reihe von <a href="#standard_accounts"><u>
Standardkonten</u></a>, welche für die Mehrzahl der Benutzer/Systeme verwendbar sind.
<br><br>
<i>Wo ist es zu finden?</i><br>
Die Kontenverwaltung (Einrichtung, Löschen etc.) ist über &quot;Menü:
Konten > Konten verwalten&quot; zugänglich.<br>
Systemkonten über &quot;Menü: Konten > Systemkonten&quot;.
<hr>

<a name="standard_accounts"></a>
<h3>Standard-Kontotypen</h3>
Obwohl es möglich ist, eine von Grund auf neue Kontostruktur zu schaffen, haben wir 
eine Datenbank mit Standardeinstellungen zur Verfügung gestellt, die für die Mehrzahl 
der komplementären Währungssysteme zweckmäßig erscheint. Die Standardeinstellungen der 
Datenbank können allerdings stets um weitere Konto- und Überweisungstypen ergänzt werden. 
Wir haben ein Mitgliedskonto und verschiedene Systemkonten konfiguriert. Die 
<a href="#account_fees"><u>Kontogebühren</u></a> (automatisch, manuell und 
Liquiditätsgebühr = &quot;Demurrage&quot;) und <a	href="#transaction_fees">
<u>Überweisungsgebühren</u></a> sind in der Standardkonfiguration gesperrt; es ist allerdings 
leicht, die Standardwerte zu ändern und sie zu aktivieren.
<br><br>Die Standardkonfiguration bietet die folgenden Standard-Systemkonten:
<ul>
	<li><b>Darlehenskonto (Debitkonto):</b> Das Debit- oder Darlehenskonto wird nur für 
	Darlehen und für das Startguthaben verwendet (dabei kann es sich um ein Darlehen oder 
	um ein Geschenk handeln). Dies ist das Hauptsystemkonto, und wird aus Gründen der 
	Klarheit Darlehenskonto genannt (manchmal wird diese Art Konto auch &quot;Fließkonto&quot; 
	oder &quot;Ausgangskonto&quot; genannt). Häufig ist das Darlehenskonto das EINZIGE Konto, 
	das kein eingeschränktes Kreditlimit hat. Dieses Konto ist notwendig für die Bereitstellung 
	von Einheiten. Werden Einheiten   bereitgestellt, so wird das Darlehenskonto negativ belastet, 
	während der Empfänger (üblicherweise   ein Mitgliedskonto) eine Gutschrift für die gleiche 
	Anzahl Einheiten erhält.<br>
	Die Verwaltung des Darlehenskontos ist daher von größter Wichtigkeit. Dies trifft möglicherweise 
	nicht so sehr auf LETS-Systeme zu, aber für professionelle Tauschsysteme (Barter) oder 
	geldgedeckte Systeme muss dies sehr sicher funktionieren.<br>
	<br>
	<li><b>Gemeinschaftskonto:</b> Das Gemeinschaftskonto gehört der Gemeinschaft und empfängt 
	die Gebühren (falls konfiguriert) und Einzahlungen von Mitgliedsbeiträgen. Ein Administrator 
	kann eine Zahlung von einem Gemeinschaftskonto auf ein Mitgliedskonto tätigen (z.B. für eine 
	Leistung, die das Mitglied für die Gemeinschaft erbracht hat). Wie das Mitgliedskonto auch, 
	kann das Gemeinschaftskonto nicht unter die untere Kreditgrenze fallen.<br>
	<br>
	<li><b>Gutscheinkonto:</b> Das Gutscheinkonto enthält die (Tausch)-Einheiten, die als 
	Gutscheine (materialisierte Einheiten) in Umlauf gebracht wurden. Will ein Mitglied 
	Gutscheine kaufen, so muss es eine Zahlung an das Gutscheinkonto leisten. Die Organisation 
	kann dann den Eingang überprüfen und die Gutscheine übergeben. Will ein Mitglied die 
	Gutscheine wieder in Einheiten umtauschen, so muss das Mitglied die Gutscheine an die 
	Organisation zurückgeben. Ein Administrator übernimmt dann die Überweisung vom 
	Gutscheinkonto auf das Mitgliedskonto.
	<br>
	Im Falle eines Systems mit (partieller) Deckung der Einheiten durch eine konventionelle 
	Währung kann der Gutschein auch gegen Geld verkauft werden. In diesem Fall ist 
	Mitgliedschaft im System nicht notwendig. Die Gutscheine sind dann ganz einfache
	&quot;Bonus-Coupons&quot;. In diesem Fall muss der Administrator eine Zahlung vom 
	Darlehenskonto auf das Gutscheinkonto tätigen.<br>
	<br>
	<li><b>Organisationskonto:</b> Das Organisationskonto ist ein Extra-Konto für die Organisation. 
	Wenn nötig, kann der Name entsprechend der Funktion geändert werden (z.B. Sozialkonto oder 
	Investitionsfond).
</ul>
<hr class="help">

<A NAME="account_search"></A>
<h3>Kontenliste</h3>
Die Seite Kontenliste zeigt eine Übersicht mit einer Liste aller Konten und ihrer Typen an (daher 
bezeichnen wir dies manchmal als Kontentyp).
<br><br>Um ein neues Mitglieds- oder Systemkonto einzurichten, klicken Sie bitte auf die
&quot;Weiter&quot;-Schaltfläche mit der Bezeichnung &quot;Neues Konto einfügen&quot; 
unter dem Fenster.
<ul>
	<li><img border="0" src="${images}/edit.gif" width="16" height="16">
	Dieses Symbol anklicken, um das Konto zu ändern.
	<li><img border="0" src="${images}/delete.gif" width="16" height="16">
	Dieses Symbol anklicken, um das Konto zu löschen. (Ein Konto kann nur dann gelöscht 
	werden, wenn es im System keine Überweisungen für dieses Konto gibt).
</ul>
<hr class="help">

<A NAME="account_details"></A>
<h3>Kontotyp ändern / Neues Konto</h3>
Im Fenster &quot;Kontodetails&quot; können Sie ein neues Mitgliedskonto einrichten oder ändern. <br>
Falls Sie ein neues Konto einrichten, können Sie spezifizieren, ob es sich dabei um ein 
Systemkonto oder um ein Mitgliedskonto handelt. Die folgenden Optionen sind verfügbar:
<UL>
	<li><b>Name:</b> Name des Kontos. Dies wird in der <a
		href="#account_search"><u>Kontoliste</u></a> gezeigt werden und (falls es sich um ein 
		Systemkonto handelt) in der <a href="${pagePrefix}payments#account_overview">
		<u>Systemkontoliste</u></a>.
	<li><b>Beschreibung:</b> Erklärung des Kontos (nur für den Administrator lesbar).
	<li><b>Währung:</b> Hier stellen Sie die <a href="#currencies"><u>Währung</u></a>
	für das Konto ein.
	<li><b>Limit-Typ (nur für Systemkonten):</b> Ein Konto kann unlimitiert sein, was bedeutet, 
	dass es unbegrenzt negativ belastet werden kann (dies ist meist ein
	&quot;Debit-&quot;, &quot;Fließ-&quot; oder &quot;Darlehenskonto&quot;).<br>
	Ist das Konto limitiert, so können Sie das obere und das untere Kreditlimit festlegen. 
	Der Limit-Typ kann nur zum Zeitpunkt des Kontoeinrichtens spezifiziert werden 
	(und es ist keine nachträgliche Änderung möglich).
</UL>
Anmerkung: Viele Kontoeinstellungen sind gruppenspezifisch (z.B. die Kreditlimits). 
Diese Einstellungen können in den <a	href="${pagePrefix}groups#manage_groups">
<u>Gruppeneinstellungen</u></a> geändert werden.
<hr>

<A NAME="transaction_types"></A>
<h2>Überweisungstypen</h2>
Jede Zahlung (auch Überweisung genannt) entspricht einem &quot;Überweisungstyp&quot;. 
Der Überweisungstyp definiert den Ursprungs- und den Ziel- Kontotyp der Zahlung. Falls 
einem Kontentyp keine Überweisungstypen zugeordnet sind, kann keine Zahlung erfolgen. 
Dem Ursprungskonto (= dem Konto des Auftraggebers) muss ein Überweisungstyp zugeordnet sein.


<i>Wo ist es zu finden?</i><br> 
Überweisungstypen können im Fenster Kontoverwaltung definiert und verändert werden; 
um dorthin zu gelangen, folgen Sie bitte dem &quot;Menü: Konten > Konten verwalten&quot;, 
und klicken Sie auf das Bearbeiten-Symbol, um einen Kontentyp zu ändern. In der nächsten 
Maske sehen Sie ein spezielles Fenster mit einer Übersicht der zugeordneten 
Überweisungstypen.
<hr class="help">

<A NAME="transaction_type_search"></A>
<h3>Liste Überweisungstypen</h3>
Das Fenster Überweisungstypen zeigt eine Liste der Überweisungstypen in Verbindung mit 
dem gewählten Konto.
<ul>
	<li><img border="0" src="${images}/edit.gif" width="16" height="16">
	Dieses Symbol anklicken, um den Überweisungstyp zu ändern.
	<li><img border="0" src="${images}/delete.gif" width="16" height="16">
	Dieses Symbol anklicken, um den Überweisungstyp zu löschen.
</ul>
Verwenden Sie die Schaltfläche &quot;Neuen Überweisungstyp einfügen&quot; unten, um 
einen neuen Überweisungstyp einzufügen.
<br><br>Anmerkung 1: Die Berechtigungen für einen Überweisungstyp sind pro Gruppe definiert, 
und können im &quot;Menü: Benutzer und Gruppen > Gruppen > Symbol: Berechtigungen bearbeiten&quot;
eingestellt werden.
<br><br>Anmerkung 2: Ist das gewählte Konto ein Mitgliedskonto, so sind die spezifischen 
Kontoeinstellungen (Kreditlimit etc.) für die gesamte Gruppe definiert. Diese können 
ebenfalls im Abschnitt &quot;Benutzer und Gruppen > Gruppen&quot; geändert werden 
(Bearbeiten-Symbol wählen).
<hr class='help'>

<A NAME="transaction_type_details"></A>
<h3>Überweisungstyp ändern/einfügen</h3>
Dieses Fenster ermöglicht ie Eigenschaften eines bestimmten Überweisungstyps 
einzustellen. Die Überweisungstypen haben folgenden Eingabefelder (abhängig von Typ 
und Konfiguration sind davon möglicherweise nicht alle sichtbar):
<ul>
	<li><b>Name:</b> Name des Überweisungstyps.
	<br>
	<li><b>Beschreibung:</b> Interne Beschreibung des Überweisungstyps. Diese Beschreibung 
	kann in den Überweisungsdetails erscheinen, die der Kunde auf seinem Bildschirm sieht.<br>
	Anmerkung: Im Falle von Darlehensrückzahlungen und periodischen Gebühren können Sie 
	bestimmte Codes verwenden, um spezifische Daten in der Beschreibung einzufügen. 
	Sie können &quot;Platzhalter&quot; verwenden – in der endgültigen Überweisungsbeschreibung 
	werden diese dann durch ihre entsprechenden Werte ersetzt. Für eine Übersicht, bitte 
  <a href="#placeholders"><u>hier klicken</u></a>.
  <br>
	<li><b>Bestätigungsnachricht:</b> Diese Nachricht erscheint unterhalb der Überweisungsinformation 
	im Pop-up-Fenster &quot;Bestätigung&quot;. So ist es möglich, Nachrichten für 
	bestimmte Zahlungstypen einzustellen.
	<br>
	<li><b>Von:</b> Kontotyp des Auftraggebers.
	<br>
	<li><b>An:</b> Kontotyp des Empfängers.
	<br>
	<li><b>An festgelegtes Mitglied:</b> Wenn Sie möchten, dass das Ziel eines bestimmten 
	Überweisungstyps stets das gleiche Mitglied ist, wählen Sie hier dieses Mitglied. 
	Bitte beachten Sie, dass dies nur sehr selten der Fall ist. Dies wird meist dann 
	verwendet, wenn Sie Regeln und/oder benutzerdefinierte Überweisungsfelder mit einem bestimmten 
	Mitglied verbinden wollen, statt mit einer Mitgliedsgruppe.<br>
	Haben Sie mehr als einen Zahlungstyp (Mitglied-an-Mitglied), so können Sie Zahlungen 
	verbergen, indem Sie die Prioritätsoption verwenden (Erklärung weiter unten). 
	Im Falle eines als Ziel festgelegten Mitglieds möchten Sie möglicherweise die normale 
	Zahlung Mitglied-an-Mitglied ausblenden, indem Sie die Prioritätseinstellung frei lassen.
	<br>
	<li><b>Verfügbarkeit:</b> Nur für Zahlungen zwischen Mitgliedskonten. Wenn Ihre Mitglieder 
	nur ein Konto für diese Währung haben, wird diese Option nicht sichtbar sein. In 
	diesem Fall wird das &quot;freigegebene&quot; Kontrollkästchen (nächstes Element) gezeigt. 
	Die Verfügbarkeit definiert, von wo aus die Zahlung geleistet werden kann.
	<ul>
		<li><b>Gesperrt:</b> Die Zahlung ist inaktiv und wird nirgendwo erscheinen.
		
		<li><b>Zahlung an andere:</b> Dies ist der am weitesten verbreitete Zahlungstyp. 
		Wird diese Option gewählt, so kann ein Mitglied diesen Überweisungstyp verwenden, 
		um Zahlungen an ein anderes Mitglied oder an ein Systemkonto zu tätigen.
		<li><b>Eigenzahlung:</b> Existiert mehr als ein Mitgliedskontentyp (zum Beispiel 
		ein Girokonto und ein Sparkonto) so können Sie einen Überweisungstyp einrichten, 
		der es einem Mitglied ermöglicht, Zahlungen zwischen diesen Konten zu tätigen. 
		Im Falle der Eigenzahlung möchten Sie wahrscheinlich keine Zahlungen vom Girokonto 
		eines Mitglieds auf das Sparkonto eines anderen Mitglieds zulassen. Sie können dies 
		durch Abwählen des Kontrollkästchens &quot;Direktzahlungen&quot; erreichen.
	</ul>
	Anmerkung!: Überweisungstypen, welche automatisch generiert werden können, wie 
	Kontogebühren und Überweisungsgebühren, werden immer erhoben, auch wenn in dieser 
	Übersicht keine Option angewählt wurde und keine Berechtigungen für die 
	Mitgliedsgruppe eingestellt wurden. Das Gleiche gilt für das nächste Element, 
	das Kontrollkästchen &quot;Freigegeben&quot;.
	<br>
	<li><b>Freigegeben:</b> Mit diesem Überweisungstyp können Zahlungen geleistet werden; 
	der Zahlungstyp ist im Zahlungsfenster und in der Überweisungsübersicht sichtbar. 
	Bitte beachten Sie, dass dieses Element und das vorhergehende einander ausschließen: 
	wenn &quot;Verfügbarkeit&quot; erscheint, wird &quot;Freigegeben&quot; nicht erscheinen, 
	und umgekehrt.
	<br>
	<li><b>Kommunikationswege:</b> Dieses Element ermöglicht die 
	<a href="${pagePrefix}settings#channels"><u>Kommunikationswege</u></a> zu definieren, 
	die für diesen Typ verwendet werden können. Der als Standard eingestellte Kommunikationsweg 
	ist der &quot;Internetzugang&quot;, allerdings gibt es auch andere Möglichkeiten 
	(wie z.B. Mobiltelefon WAP 2).
	<br>
	<li><b>Priorität:</b> Wird die Prioritäts-Option gewählt, dann hat dieser Überweisungstyp 
	Vorrang vor anderen Überweisungen. Das bedeutet, dass wenn eine Zahlung mehr als einen 
	Überweisungstyp aufweisen kann, nur die Überweisungstypen mit Prioritäts-Einstellung 
	gezeigt werden. Ist keine der Zahlungen als prioritär markiert, so erscheinen sie 
	alle.<br>
	Die Prioritätseinstellung wird nur in relativ komplexen Systemen verwendet, in denen 
	unterschiedliche Gruppen nur unter bestimmten Bedingungen mit anderen Gruppen handeln 
	können.<br>
	Mit den Gruppen- und Überweisungstypen ist es möglich, ein System einzurichten, in dem 
	unterschiedliche Gemeinschaften miteinander und untereinander handeln können, und 
	zwar innerhalb des gleichen Systems.<br>
	Diese Einstellung wird ebenso in Kombination mit 	der weiter oben beschriebenen 
	Option &quot;An festgelegtes Mitglied&quot; verwendet.
	<br>
	<li><b>Max. Betrag pro Tag:</b> Dies ist der Maximalbetrag, der pro Mitglied und Tag mit 
	diesem Überweisungstyp gezahlt werden kann. Dies kann zum Beispiel verwendet werden, 
	um per Mobiltelefon getätigte Zahlungen zu begrenzen.<br>
	<br>
	
	<li><b>Mindestbetrag:</b> Dies ist der Mindestbetrag je Überweisung 
	mit diesem Überweisungstyp. Als Beispiel: eine Spendenzahlung die einen
	Mindestbetrag erfordert.<br>
	<br>	
	<li><b>Invoke Java class:</b> If specific behavior or functionality is 
	needed that cannot be done by configuration you could create your own
	Java class. The class will be invoked every time a payment with this transaction
	type is processed.<br><br>
	<b>Note1:</b>  
	The class will only be invoked on &quot;processed&quot; payments. That means
	it will not be invoked when payments are scheduled for a future date or pending 
	for authorization. (But once scheduled or authorized payments are finally processed 
	the class will be invoked).
	<br><br>
	<b>Note2:</b>
	Make sure that the class is available on the server class path, for example WEB-INF/lib.
	<br><br>
	<b>Note3:</b> It is also possible to invoke a class on all payment types. This can 
	be configured in <a
		href="${pagePrefix}settings#local"><u>local settings</u></a> - Extra - Java class 
	for processed payments.
	<br>
	<br>	
	<li><b>Erfordert Autorisierung:</b> Wird dies gewählt, erscheint ein zusätzliches 
	Autorisierungsfenster unter diesem Fenster, sobald Sie hier zum ersten 
	Mal gespeichert haben. Mehr Information zu <a href="${pagePrefix}payments#authorized">
	<u>autorisierte Zahlungen</u></a> finden Sie im Hilfe-Abschnitt 
	<a href="#authorized_payment_levels"><u>dieses Fensters</u></a>.<br>
	Wenn Sie die Autorisierungsoption abwählen, werden für alle neuen Zahlungen die 
	Autorisierungsstufen deaktiviert.
	<br>
	<li><b>Haupt- und Untertransaktionen anzeigen:</b> Einige Systeme machen von
	automatisch generierten Überweisungsgebühren starken Gebrauch. Dies kann beim
	Endbenutzer zu Verwirrung bei der Anzeige der Transaktionen im Überweisungsverlauf führen. 
	Mit dieser Einstellung können Sie definieren, wer die Anzeige der Gebühren im 
	Überweisungsverlauf sieht. 
	<li><b>Erlaube geplante Zahlungen (Kurse):</b> Wird diese Option gewählt, kann der 
	Überweisungstyp <a href="${pagePrefix}payments#scheduled"><u>geplant</u></a> werden. 
	Dies bedeutet allerdings nicht, dass jedes Mitglied und jeder Administrator mit diesem 
	Überweisungstyp automatisch auch die Planungs-Option nutzen kann. Sie benötigen dazu 
	immer noch die Berechtigung pro Gruppe (geplante Zahlungen zeigen und ermöglichen). 
	Für mehr Information dazu wenden Sie sich bitte an das Benutzerhandbuch 
	Zahlungen – geplante Zahlungen.
	<ul>
		<li><b>Rücklage für Gesamtbetrag geplanter Zahlungen:</b> Wird diese 
		Option gewählt, kann das Mitglied Zahlungen nur dann planen wenn der 
		gesamte Betrag für alle Teilzahlungen auf dem Konto verfügbar ist. Der 
		gesamte Betrag wird daraufhin reserviert und kann nicht ausgegeben werden.<br>
    Diese Option wird für die üblichen Zahlungen zwischen den Mitgliedern eines Tauschsystems 
    wahrscheinlich nicht verwendet werden. Die Einstellung wäre eher üblich für geschäftliche 
    Zahlungen per POS (point of sale). 
		<li><b>Stornierung geplanter Zahlungen durch den Zahlenden erlauben:</b> Wird 
		diese Option gewählt, so kann der Zahler geplante Zahlungen stornieren. 
		Bitte beachten Sie, dass zusätzlich zu dieser Einstellung die Gruppe des Zahlers 
		auch mit der Berechtigung zur Stornierung geplanter Zahlungen ausgestattet sein muss. 
		<li><b>Sperren geplanter Zahlungen durch den Zahlenden erlauben:</b> Wird 
		diese Option gewählt, so kann der Zahler geplante Zahlungen sperren. 
		Bitte beachten Sie, dass zusätzlich zu dieser Einstellung die Gruppe des Zahlers 
		auch mit der Berechtigung zum Sperren geplanter Zahlungen ausgestattet sein muss. 
		<li><b>Geplante Zahlungen beim Empfänger anzeigen:</b> Geplante Zahlungen 
		werden vom Zahler veranlasst. Für den Empfänger erschient jede Teilzahlung als isolierte, 
		einzelne Zahlung. Dem Empfänger ist nicht ersichtlich, dass es sich um 
		Teilzahlungen für einen größeren Betrag handelt.<br> 
    Wenn Sie möchten, dass der Empfänger dir zukünftigen (eingehenden) geplanten Zahlungen 
    sehen kann, wählen Sie diese Option.<br> 
    Diese Einstellung ist gebräuchlicher für Zahlungen vom Konsumenten an ein Geschäft, 
    als am POS (point of sale).<br>
		Bitte beachten Sie dass geplante Zahlungen, die auf akzeptierte 
		Rechnungen hin erfolgen, dem Empfänger (Rechnungssender) stets angezeigt werden. 
	</ul>
	<br>
	<li><b>Ist abgleichbar:</b> Wird diese Option gewählt, so wird dieser Überweisungstyp Teil 
	der Conciliation-Funktion sein. Für mehr Information wenden Sie sich bitte an die 
	<a href="${pagePrefix}bookkeeping"><u>Hilfe-Themen</u></a> der Funktion 
	Buchhaltung	/ Abgleich.
	<br>
	<li><b>Ist ein Darlehen:</b> (nur verfügbar für Zahlungen System-an-Mitglied). Markieren 
	Sie dieses Kästchen, um anzugeben, ob es sich bei Überweisung um ein Darlehen handelt. 
	Die Darlehens-Einstellungen erscheinen darunter, sobald Sie das Kästchen ausgefüllt haben. 
	Für weitere Information <a	href="${pagePrefix}loans#make_loan_type"><u>klicken Sie bitte hier</u></a>. 
	<br>	
	<li><b>Feedback zum Geschäftsvorgang anfordern:</b> Diese Option ist nur für 
	Mitglied-an-Mitglied-Zahlungen. Wird diese Option gewählt, so können Mitglieder 
	Feedbacks zu durchgeführten Geschäftsvorgängen dieses Typs einstellen. Für 
	weitere Informationen zum Thema Feedbacks wenden Sie sich bitte an das Hilfethema 
	<a href="${pagePrefix}transaction_feedback">Feedbacks zu Geschäftsvorgängen</a>.<br>
	Die folgenden Elemente sind nur dann sichtbar, wenn das Kontrollkästchen 
	&quot;Feedback zum Geschäftsvorgang anfordern&quot; gewählt wurde:
	<ul>
		<li><b>Maximaler Zeitraum für das Feedback:</b> Wurde eine Zahlung getätigt, so hat der 
		Auftraggeber (= Käufer) einen begrenzten Zeitraum zur Abgabe des Feedbacks zur Verfügung. 
		Dieser Zeitraum kann hier eingestellt werden.
		<li><b>Maximaler Zeitraum zum Beantworten eines Feedbacks:</b> Der Empfänger (=Verkäufer) 
		kann ein Feedback beantworten. Der maximale Zeitraum hierfür kann hier 
		eingestellt werden.
		<li><b>Level des Feedbacks nach Ablauf:</b> Sobald ein Feedback-Zeitraum überschritten 
		wurde, wird ein Verzugs-Feedback erzeugt. Sie können hier den Standardwert einstellen 
		(typischerweise wäre dies &quot;Neutral&quot;).
		<li><b>Kommentar des Feedbacks nach Ablauf:</b> Sobald ein Kommentar-Zeitraum überschritten 
		wurde, wird eine Verzugs-Kommentar erzeugt. Sie können den Standardkommentar hier 
		einstellen (typischerweise wäre dies &quot;Dieser Überweisung wurden keine Kommentare 
		zugeordnet.&quot;).
	</ul>
</ul>
<hr class="help">

<a name="placeholders"></a>
<h3>Platzhalter für Beschreibungsfelder</h3>
Im Falle von Darlehensrückzahlungen und periodischen Gebühren können Sie 
bestimmte Variablen verwenden, um spezifische Daten in der Beschreibung eines 
<a href="#transaction_types"><u>Überweisungstyps</u></a> einzufügen. Sie können 
&quot;Platzhalter&quot; verwenden - in der endgültigen Überweisungsbeschreibung werden 
diese dann durch ihre entsprechenden Werte ersetzt.<br>
<br><br><b>Darlehensrückzahlung:</b>
<ul>
	<li><b>#loanAmount#:</b> der ursprüngliche Betrag des Darlehens
	<li><b>#loanTotalAmount#:</b> Darlehensbetrag plus Kosten (Zinsen, Darlehens-Bewilligungsgebühr)
	<li><b>#parcelAmount#:</b> Betrag der Darlehensraten, der Betrag für eine bestimmte 
	Darlehensperiode. Ein Darlehen ist unterteilt in eine festgelegte Anzahl von 
	Rückzahlungsraten.
	<li><b>#parcelNumber#:</b> Anzahl der Darlehensraten. Ein Darlehen ist in eine 
	festgelegte Anzahl von Rückzahlungen unterteilt, den &quot;Kurse&quot;. 
	Jede dieser Kurse wird mit einer eigenen Nummer bezeichnet.
</ul>
Für mehr Information zu den Darlehen, wenden Sie sich bitte an das <a href="${pagePrefix}loans">
<u>Hilfethema Darlehen</u></a>.<br>
<br><br><b><a href="#account_fees"><u>Periodische Gebühren</u></a></b>
<ul>
	<li><b>#begin_date#:</b> Anfangsdatum (nur im Fall der <a
		href="#account_fees"><u> Liquiditätsgebühr</u></a>)
	<li><b>#end_date#:</b> Enddatum (nur im Fall der Liquiditätsgebühr)
	<li><b>#tax#:</b> Betrag
	<li><b>#freebase#:</b> Der Basisbetrag (Freibetrag), für den keine Gebühr erhoben wird 
	(nur zutreffend für Liquiditätsgebühr)
	<li><b>#volume#:</b> Gesamtumsatz (nur zutreffend für Liquiditätsgebühr)
	<li><b>#result#:</b> Ergebnis
</ul>
<br><br><b><a href="#transaction_fees"><u>Überweisungsgebühren</u></a></b>.
Bitte beachten Sie, dass diese in der Beschreibung des Überweisungstyps der 
Gebühr eingetragen werden sollten, nicht in der Beschreibung der Gebühr selbst. 
<ul>
	<li><b>#fee#</b>: der Wert der Gebühr, also dem Prozentsatz 
	(inklusive des %-Zeichens) falls es sich um eine als Prozentsatz berechnete Gebühr 
	handelt, oder der Betrag, im Falle einer Festbetragsgebühr.  
	<li><b>#fee_amount#</b>: wie oben (#fee#).
	<li><b>#member#</b>: das zahlende Mitglied.
	<li><b>#transfer#</b>: Der Gesamtbetrag der ursprünglichen Überweisung, 
	die diese Gebühr auslöst. 
	<li><b>#original_amount#</b>: wie oben (#transfer#).
	<li><b>#amount#</b>: Eine eingegrenzte Darstellung des als Gebühr zu zahlenden Betrags.
	Im Falle fixer Gebühren ist dies das Gleiche wie #fee#; aber 
	im Falle von Prozentsatz-Gebühren ist #fee# multipliziert mit dem Überweisungsbetrag. 
</ul>
<hr class="help">


<br><br><A NAME="payment_fields_list"></A>
<h3>Benutzerdefinierte Überweisungsfelder</h3>
Sie können einem Überweisungstyp (Zahlung) benutzerdefinierte Felder hinzufügen,
genauso wie Sie auch benutzerdefinierte Felder in den Mitgliedsprofilen oder 
den Inseraten festlegen können. Das Überweisungsfeld wird nur für den aktuellen Überweisungstyp 
zu sehen sein (jenen, den Sie gerade bearbeiten).<br>
Sie können zwei verschiedene Arten benutzerdefinierter Überweisungsfelder einrichten:
<ul>
	<li><b>Neues benutzerdefiniertes Feld einfügen:</b> Mit dieser Option können Sie ein 
	benutzerdefiniertes Feld für einen Zahlungstyp genauso einstellen, wie Sie das z.B. 
	beim Mitgliedsprofil tun.
	<li><b>Verweis auf bestehendes benutzerdefiniertes Feld:</b> Mit dieser Option können 
	Sie auf ein existierendes Überweisungsfeld verweisen. Mehr Information dazu finden Sie über 
	den Verweis &quot;Details benutzerdefinierte Überweisungsfelder&quot;. 
	</ul>
<hr class="help">


<br><br><A NAME="payment_fields_link"></A>
<h3>Verweis auf benutzerdefinierte Überweisungsfelder</h3>
In komplexeren Systemen ist es üblich, dass Überweisungen &quot;weitergeleitet&quot; werden.
Dies ist normalerweise so gelöst, dass eine Überweisungsgebühr in Höhe von 100% der 
Überweisung erhoben wird, die als Ziel das Konto einer Drittpartei hat. In diesem Fall 
könnten Sie vielleicht das gleiche benutzerdefinierte Feld für beide Zahlungen verwenden. 
Dies können Sie tun, indem Sie das benutzerdefinierte Überweisungsfeld im ursprünglichen 
Überweisungstyp einrichten, und im zweiten (generierten) Überweisungstyp einen Verweise 
auf das benutzerdefinierte Überweisungsfeld einrichten (unter Verwendung der Option 
&quot;Verweis auf bestehendes benutzerdefiniertes Überweisungsfeld&quot;). 
</ul>
<hr>


<br><br><A NAME="authorized_payment_levels"></A>
<h3>Autorisierungsebenen</h3>
Mit dieser Funktion können Sie Autorisierungsebenen für einen Zahlungstyp konfigurieren, der
eine <a href="${pagePrefix}payments#authorized"><u>Autorisierung</u></a> bedarf.
<br><br>Verschiedene Ebenen der Autorisierung sind möglich, und es gibt möglicherweise 
mehrere Autorisierungsebenen für einen Zahlungstyp. Dies bedeutet, dass verschiedene 
Personen einen einzigen Zahlungstyp mit (möglicherweise) unterschiedlichen Kriterien 
autorisieren. Sowohl Mitglied als auch Autorisierender (Broker oder Administrator) 
haben Zugang zu einer Liste anstehender Zahlungen, die noch der Autorisierung bedürfen.
<br><br>Dieses Fenster zeigt alle Autorisierungsebenen für den Überweisungstyp. Ist keine 
verfügbar, klicken Sie bitte auf &quot;Neue Autorisierungsebene&quot;, da Sie mindestens 
eine Autorisierungsebene für jeden zu autorisierenden Zahlungstyp definieren müssen. 
Wenn Sie eine neue Ebene hinzufügen, so erscheint diese auf der Liste.<br>
Wenn eine Ebene definiert wurde, gibt es folgende Optionen:
<ul>
	<li><img border="0" src="${images}/edit.gif" width="16" height="16">
	Klicken Sie dieses Symbol, um die Ebene autorisierte Zahlungen zu verändern.
	<li><img border="0" src="${images}/delete.gif" width="16" height="16">
	Klicken Sie dieses Symbol, um die Ebene autorisierte Zahlungen zu löschen.
</ul>
<br><br>Wenn Sie die Autorisierungsoption im Überweisungstyp deaktivieren, sperren Sie damit 
alle Autorisierungen für den Überweisungstyp. Die Autorisierungsebenen erscheinen 
dann zwar nicht mehr, bleiben aber im Verlauf. Wird das Kontrollkästchen 
&quot;Autorisierungen&quot; in der Konfiguration des Überweisungstyps wieder angewählt, 
sind die Autorisierungsebenen wieder zu sehen.
<br><br>Wenn eine autorisierte Zahlung andere Zahlungen generiert (z.B. ein Darlehen mit 
Darlehensrückzahlungen, Gebühren, etc.), kann die gesamte Gruppe der Zahlungen auf 
einmal autorisiert werden (und der gesamte Betrag bleibt bis zur Autorisierung 
blockiert/reserviert).
<hr class="help">

<A NAME="edit_authorization_level"></A>
<h3>Autorisierungsebene bearbeiten</h3>
In diesem Fenster können Sie die <a href="#authorized_payment_levels"><u>
Autorisierungsebene</u></a> definieren. Sie können maximal fünf Autorisierungsebenen 
definieren. Jede Ebene kann einen &quot;Autorisierungstyp&quot; haben. Welche Art der 
Autorisierung verfügbar ist, hängt von der Autorisierungsebene ab.
<ul>
	<li><b>Empfänger:</b> Ist der Autorisierungstyp &quot;Empfänger&quot;, 
	so bedeutet dies, dass das (Ziel-)Mitglied, das die Zahlung erhält, die Zahlung zuerst 
	autorisieren muss. Autorisiert der Empfänger die Zahlung (durch Anklicken der 
	Schaltfläche &quot;Akzeptieren&quot; auf der Liste der anstehenden noch zu autorisierenden 
	Zahlungen, so wird der Betrag überwiesen, und der Status verändert sich von 
	&quot;Anstehend&quot; zu &quot;Autorisiert&quot;.<br>
	Dieser Autorisierungstyp ist recht selten. Wenn im System <a href="${pagePrefix}invoices"><b>
	Rechnungen</b></a> verwendet werden, empfehlen wir, diese Option nicht zu nutzen. 
	Rechnungen bieten eine ähnliche Funktion.
	<li><b>Auftraggeber:</b> Diese Option besteht nur dann, wenn auf der ersten Ebene die 
	Option Autorisierungstyp &quot;Empfänger&quot; besteht.<br>
	Sie bietet eine zusätzliche (optionale) Autorisierungsebene, nach dem Autorisierungstyp 
	&quot;Empfänger&quot;. Ist dieser Typ konfiguriert, so bleibt eine Zahlung im Status 
	&quot;anstehend&quot;, nachdem sie vom Empfänger autorisiert wurde. Nun muss der 
	Auftraggeber (der Urheber) die Zahlung akzeptieren. Ist dies getan, wird die Zahlung 
	überwiesen.
	<li><b>Broker/Administrator:</b> Diese Autorisierungsebene wird entweder als erste 
	Ebene oder als zweite Ebene nach einer Ebene mit dem Autorisierungstyp &quot;Empfänger&quot; 
	erstellt. Dies bedeutet, dass der <a href="${pagePrefix}brokering"><u>Broker</u></a> 
	des die Zahlung tätigenden Mitglieds die Zahlung autorisieren muss – und optional 
	auch ein Administrator.<br>
	Neben dem Broker können Sie die Administratorengruppe wählen, die die Zahlung autorisieren 
	darf. Hier gibt es keine Priorität einer Autorisierung über die andere: sowohl Broker 
	als auch Administrator können autorisieren.
	<li><b>Administrator:</b> Dieser Autorisierungstyp ist als erste Ebene verfügbar, 
	als zweite Ebene nach Broker/Administrator, und als dritte Ebene nach Auftraggeber 
	oder jedem anderen auf Administratoren beschränkten Typ.<br>
	Dies bedeutet, dass Sie unterschiedliche Ebenen mit dem Administratoren-Typ und 
	verschiedenen Beträgen und Gruppen miteinander kombinieren können. Wählen Sie bitte 
	die Administratorengruppe, die die Zahlung autorisieren kann.
</ul>
Außerdem spezifizieren Sie bitte den Betrag für jeden Autorisierungstyp und jede Ebene. 
Wenn Sie möchten, dass alle Zahlungen autorisiert werden, tragen Sie bitte eine Null 
in das Betragsfeld ein. Falls Sie z.B. die Zahl 1.000 eintragen, so müssen Zahlungen 
eines Mitglieds, die innerhalb von 24 Stunden den Gesamtbetrag von 1.000 überschreiten, 
autorisiert werden.<br>
Der Betrag einer Autorisierungsebene kann entweder gleich hoch sein wie der auf der 
Ursprungsebene, oder aber höher.
<hr>


<A NAME="payment_filters"></A>
<h2>Zahlungsfilter</h2>
Sie können Überweisungstypen auch mit &quot;Zahlungsfilter&quot; zu Gruppen zusammenfassen. 
Diese Filter ermöglichen es, bestimmte verwandte Überweisungstypen zu gruppieren, zum 
Beispiel für das Fenster &quot;Übersicht Überweisungen&quot; oder für Statistiken. 
Zum Beispiel: verschiedene Arten von Beiträgen und andere spezifische Gemeinschaftszahlungen 
können in einem Filter namens „Gemeinschaftszahlungen“ zusammengefasst werden. 
Die Zahlungsfilter können ebenfalls verwendet werden, um benutzerdefinierte Berichte 
zu erstellen. Für den Administrator sind die Zahlungsfilter ein gutes Mittel, um 
Zahlungen zurückzuverfolgen, oder aber spezifische Berichte einzuholen.

<i>Wo ist es zu finden?</i><br>
Zahlungsfilter sind stets einem Kontotyp zugeordnet, sie sind also zugänglich über 
&quot;Menü: Konten > Konten verwalten&quot;. Wählen Sie einen Kontotyp und klicken Sie 
auf das Bearbeiten-Symbol <img border="0" src="${images}/edit.gif" width="16" height="16">&nbsp;. 
Neben anderen Fenstern gibt es auch ein spezielles Fenster mit den dazugehörigen 
Zahlungsfiltern.
<hr class="help">

<A NAME="payment_filter_search"></A>
<h3>Liste Zahlungsfilter</h3>
Dieses Fenster listet die zu dem Konto gehörigen <a href="#payment_filters">
<u>Zahlungsfilter</u></a>.
<ul>
	<li><img border="0" src="${images}/edit.gif" width="16" height="16">
	Dieses Symbol anklicken, um den Zahlungsfilter zu ändern.
	<li><img border="0" src="${images}/delete.gif" width="16" height="16">
	Klicken Sie dieses Symbol, um den Zahlungsfilter zu löschen.
</ul>
Um einen neuen Zahlungsfilter einzurichten, klicken Sie bitte auf die 
&quot;Weiter&quot;-Schaltfläche mit der Bezeichnung &quot;Neuen Zahlungsfilter einfügen&quot; 
unter dem Fenster.
<hr class="help">

<A NAME="payment_filter_details"></A>
<h3>Zahlungsfilter ändern/einfügen</h3>
Hier können Sie einen bereits bestehenden <a href="#payment_filters"><u>Zahlungsfilter</u></a> bearbeiten. 
Die folgenden Felder können definiert werden:
<ul>
	<li><b>Name:</b> Der Name des Filters.
	<li><b>Beschreibung:</b> Die Beschreibung des Filters.
	<li><b>In Kontoverlauf anzeigen:</b> Ist dieses Kästchen ausgefüllt, so wird der Filter 
	im Kontoverlauf (des gewählten Kontos) angezeigt.
	<li><b>In den Berichten anzeigen:</b> Wenn Sie das Kontrollkästchen 
	&quot;In den Berichten anzeigen&quot;	anwählen, so schließt die Funktion 
	<a href="${pagePrefix}reports"><u>Berichte</u></a> zu diesem Filter ein. 
	Zu sehen sein werden dann die Beitragssummen aus allen Überweisungen in Verbindung mit 
	diesem Zahlungsfilter. 	Dies ist auch verfügbar für das <a href="${pagePrefix}statistics">
	<u>Modul Statistiken</u></a>.<br>
	Sie können Zahlungsfilter auch ausschließlich für die Berichtsfunktion einrichten. Dafür 
	müssen Sie das Kontrollkästchen &quot;In Kontoverlauf anzeigen&quot; deaktivieren.
	<li><b>Überweisungstypen:</b> Hier müssen Sie die <a href="#transaction_types">
	<u>Überweisungstypen</u></a> festlegen, die im Zahlungsfilter beinhaltet sein sollen.
	<li><b>Sichtbarkeit Gruppen:</b> Hier können Sie bestimmen, welche Gruppen die 
	Zahlungsfilter sehen können. Auf diese Art ist es möglich, für unterschiedliche 
	Gruppen unterschiedliche Filter einzurichten. Eine Brokergruppe kann z.B. einen 
	&quot;Gezahlte Kommissionen&quot;-Filter haben. Administratoren benötigen normalerweise 
	spezifischere Zahlungsfilter, während Mitgliedsgruppen eher grundlegende Zahlungsfilter 
	haben, wie z.B. Überweisungen zu Tauschgeschäften, Gebühren.
</ul>
<hr>

<A NAME="transaction_fees"></A>
<h2>Überweisungsgebühren</h2>
Im Gegensatz zu einer Kontogebühr kann eine Überweisungsgebühr nicht manuell 
erhoben oder geplant werden, sondern wird automatisch berechnet, wenn eine weitere Überweisung 
geschieht. Die Überweisungsgebühr wird daher `innerhalb´ einer Überweisung 
konfiguriert (siehe 'Location' unten).<br>
Gebühren können prozentual oder in absoluter Höhe erhoben werden und es kann definiert 
werden, von wem sie erhoben werden.<br>
<br>
Ein typisches Beispiel für eine Gebühr ist die &quot;gewöhnliche&quot; Überweisungsgebühr 
einer Transaktion.
Jedes Mal, wenn eine Überweisung stattfindet, wird die Gebühr erhoben (falls konfiguriert). 
<%-- *Übersetzen --%>
The transaction detail of a fee does
will show the original (invoking) <br>
<%-- Übersetzen* --%>
Eine Überweisung kann mit mehr als einer Gebühr verbunden sein. Für eine Überweisungsgebühr 
gibt es eine reihe von Konfigurationsoptionen. Kontogebühren können mehrere 
Zahlungsrichtungen haben (Mitglied an Mitglied, Mitglied an System, System an Mitglied, 
etc), und es gibt verschiedene Wege zu definieren, wem die Gebühr berechnet wird 
und wer sie erhält. Aufgrund der unterschiedlichen Arten, Gebühren zu verwenden, muss es sich dabei 
nicht immer um eine &quot;Gebühr&quot; handeln. Eine Gebühr kann z.B. dazu verwendet werden, 
Zahlungen an andere Konten &quot;weiter zu leiten&quot; oder zu &quot;verteilen&quot; 
(unter Verwendung der Prozentsatz-Option). 

<i>Wo ist es zu finden?</i><br>
Eine Überweisungsgebühr ist immer einem bestimmten <a	href="#transaction_types">
<u>Überweisungstyp</u></a> &quot;zugeordnet&quot;, daher ist die Konfiguration der 
Überweisungsgebühr innerhalb der Überweisungstyp-Konfiguration zu finden. Gehen Sie 
bitte zu &quot;Menü: Konten > Konten verwalten&quot;, wählen Sie ein Konto (über das 
Bearbeiten-Symbol), gehen Sie auf das Fenster Überweisungstyp und wählen Sie dort einen 
Überweisungstyp (über das Bearbeiten-Symbol). Hier finden Sie ein besonderes Fenster mit 
zugeordneten Überweisungsgebühren.
<hr class="help">

<A NAME="transaction_fee_search"></A>
<h3>Überweisungsgebühren</h3>
Dieses Fenster zeigt eine Liste der den <a href="#transaction_types"><u>Überweisungstypen</u>
</a> zugeordneten <a href="#transaction_fees"><u>Überweisungsgebühren</u></a>.
<ul>
	<li><img border="0" src="${images}/edit.gif" width="16" height="16">
	Dieses Symbol anklicken, um die Überweisungsgebühr zu verändern.
	<li><img border="0" src="${images}/delete.gif" width="16" height="16">
	Dieses Symbol anklicken, um die Überweisungsgebühr zu löschen.
</ul>
Um eine neue Überweisungsgebühr einzurichten, klicken Sie bitte unter dem Fenster 
bei der Bezeichnung &quot;Neue Überweisungsgebühr einfügen&quot; auf die Schaltfläche 
&quot;Weiter&quot;.
<hr class="help">


<A NAME="transaction_fee_details"></A>
<h3>Überweisungsgebühr ändern/einfügen</h3>
In diesem Fenster können Sie bereits existierende <a href="#transaction_fees"><u>
Überweisungsgebühren</u></a> ändern, oder aber eine neue definieren.<br>
Eine Überweisungsgebühr wird immer dann ausgelöst, wenn eine bestimmte Überweisung 
stattfindet. Allerdings sind die Gebühren selbst auch Überweisungen, und benötigen 
daher auch einen eigenen Überweisungstyp. Bevor Sie eine neue Gebühr konfigurieren, 
müssen Sie zuerst einen neuen <a href="#transaction_types"><u>Überweisungstyp</u></a> 
für diese Gebühr einrichten.
<br><br><b>Felder:</b>
<ul>
	<li><b>Überweisungstyp:</b> Dies ist der Überweisungstyp, auf den die Überweisungsgebühr 
	erhoben wird. Für eine Gebühr auf Überweisungen wählen Sie typischerweise etwas wie 
	&quot;Überweisungsgebühr&quot;.<br>
	<li><b>Name:</b> Der Name der Gebühr.<br>
	<li><b>Beschreibung:</b> Die Beschreibung der Gebühr. 
	Bitte beachten Sie, dass es sich bei dieser 
	Beschreibung nicht um die Beschreibung handelt, die das Mitglied in 
	seinen Überweisungsdetails sehen kann. Dafür lesen Sie bitte im 
	Beschreibungsfeld des zu dieser Gebühr 	gehörenden Überweisungstyps.
    <br>
	<li><b>Freigegeben:</b> Ist dies ausgefüllt, ist die Gebühr aktiviert. Wenn nicht, wird 
	die Gebühr nicht belastet und das System wird sie ignorieren.
	<br>
	<li><b>Wird abgebucht von:</b> Hier können Sie definieren, wessen Konto belastet wird. 
	Es gibt verschiedene Möglichkeiten. Vorsicht, wenn ein Broker so konfiguriert ist,
	dass er dem Mitglied als Broker die Gebühr abbucht. 
	<br> 
	<li><b>Wird erhalten von:</b> Hier können Sie definieren, wer die Gebühr erhalten wird. 
	Es gibt verschiedene Möglichkeiten. Vorsicht, wenn der Broker so konfiguriert ist,
	dass der Broker die Gebühr des Mitglieds empfängt.
	<br>
	<li><b>Erlaube jedes Konto:</b> Normalerweise werden im nächsten Bearbeitungsfeld 
	(&quot;Generierter Überweisungstyp&quot;) nur relevante Überweisungstypen in der gleichen 
	Währung gezeigt. Wenn Sie dieses Kontrollkästchen wählen, dann gibt es keinerlei Begrenzung 
	auf die Währung, in denen die Überweisungsgebühr angewendet werden kann: dann werden alle 
	Überweisungstypen gezeigt, auch in anderen Währungen.
	<br>
	<li><b>Generierter Überweisungstyp:</b> Hier können Sie definieren, welcher Überweisungstyp 
	generiert wird, also der Überweisungstyp der Gebühr selbst. Es ist üblich, dafür einen 
	eigenen Überweisungstyp einzurichten, so dass sie auch „gefiltert“ werden kann (z.B. 
	für den Kontoverlauf: Gebühren). Die als Standard eingestellte Datenbank ist mit einer 
	Überweisungsgebühr und einem Überweisungstyp für diese Überweisungsgebühr ausgestattet. 
	Eine Gebühr auf Überweisungen würde typischerweise &quot;Überweisungsgebühr&quot; genannt.<br>
	ANMERKUNG: Es ist unbedingt notwendig, dass die Einrichtung des Überweisungstyps VOR 
	Einrichtung der Gebühr selbst geschieht (lesen Sie bitte dazu die Anmerkung oberhalb).
	<br>
	<li><b>Berechnungstyp:</b> Dies definiert, wie die Gebühr 
	berechnet wird. Die folgenden Typen sind möglich: 
	<ul>
		<li><b>Fester Wert:</b> Die Gebühr beläuft sich stets auf den gleichen Wert. Diesen 
		Wert können Sie im nächsten Bearbeitungsfeld &quot;Wert berechnen&quot; eingeben. 
		
		<li><b>Prozentsatz:</b> Die Gebühr errechnet sich aus einem Prozentsatz der 
		sie auslösenden Überweisung. Diesen Prozentsatz können Sie im nächsten 
		Bearbeitungsfeld &quot;Wert berechnen&quot; eingeben.  
	</ul>
	<br>
	<li><b>Wert berechnen:</b> Hier geben Sie bitte den Gebührenbetrag 
	ein (falls Sie im vorherigen Bearbeitungsfeld &quot;festen Betrag&quot; gewählt haben), 
	oder geben Sie einen Prozentsatz ein (falls Sie im vorherigen Bearbeitungsfeld &quot;Prozentsatz&quot; 
	gewählt haben).	
	<li><b>Abzug:</b> Dieses Feld ist nur sichbar bei 
	&quot;Berechnungstyp&quot; &quot;fester Wert&quot; oder &quot;Prozentsatz&quot;.<br>
  Mit dieser Einstellung können Sie bestimmen, ob die Überweisungsgebühr als eine 
  &quot;zusätzliche Zahlung&quot; erhoben wird, oder ob sie vom Gesamtbetrag der 
  ursprünglichen Zahlung &quot;abgezogen&quot; wird.<br>

	Dies lässt sich mit einem Beispiel erklären: Wird eine Zahlung von 100 getätigt, und die 
	Einstellung besagt, dass eine Gebühr von 3 davon abgezogen werden soll, so werden die 
	folgenden Zahlungen generiert:
	<ul>
		<li>Haupt-Überweisung (auslösende Überweisung): 97
		<li>Gebühr: 3
	</ul>
	Der obige Fall ist allerdings selten. Normalerweise werden Gebühren nicht vom Gesamtbetrag 
	abgezogen. Beispiel einer nicht-abziehbaren Gebühr:
	<ul>
		<li>Haupt-Überweisung (auslösende Überweisung): 100
		<li>Gebühr: 3
	</ul>
	<br>
	<li><b>Voraussetzungen der Anwendbarkeit:</b> Hier können Sie definieren, unter welchen 
	Bedingungen die Gebühr anzuwenden ist. Die Gebühr wird nur bei übereinstimmenden Bedingungen 
	angewandt. Diese Bedingungen können kombiniert werden.
	<ul>
		<li><b>Betrag größer oder gleich:</b> Die Gebühr wird nur dann erhoben, wenn der 
		Überweisungsbetrag der auslösenden Überweisung größer als oder gleich ist wie der 
		eingetragene Betrag.
		<li><b>Betrag kleiner oder gleich:</b> Die Gebühr wird nur dann erhoben, wenn der 
		Überweisungsbetrag der auslösenden Überweisung kleiner als oder gleich ist wie der 
		eingetragene Betrag.
		<li><b>Von allen Gruppen:</b> Werden alle Gruppen gewählt, wird die Gebühr für 
		alle Mitglieder jeder Gruppe angewendet, die als Auftraggeber für die auslösende 
		Überweisung angesehen werden können. Möchten Sie die Gebühr nur auf bestimmte Gruppen 
		anwenden, deaktivieren Sie zunächst das Kontrollkästchen &quot;Von allen Gruppen&quot; 
		und wählen Sie dann die Gruppen in dem nun sichtbaren Mehrfachauswahlfeld aus.
		<li><b>An alle Gruppen:</b> Genau wie im vorhergehenden Punkt, betrifft allerdings die 
		Empfänger des auslösenden Überweisungstyps.
	</ul>
</ul>
<hr class="help">
<A NAME="account_fees"></A>
<h2>Kontogebühren</h2>
&quot;Kontogebühren&quot;, häufig auch &quot;Beiträge&quot; genannt, sind periodisch 
geplant oder werden manuell von einem Administrator ausgeführt. Kontogebühren sind 
verbunden mit einem Konto, und können für eine oder für mehrere Mitgliedergruppen 
aktiviert werden. Wird eine Kontogebühr ausgeführt, so werden alle in der Konfiguration 
der Kontogebühren gewählten Mitgliedergruppen belastet – aber obwohl das Wort &quot;Gebühr&quot; 
nahe legt, dass die Mitglieder sie bezahlen, kann eine Kontogebühr auch so konfiguriert werden, 
dass das Systemkonto sie bezahlt, und die Mitglieder sie erhalten. Eine typische Kontogebühr 
ist eine monatliche Zahlung von Mitgliedern an das Systemkonto (es kann aber auch genau 
anders herum sein). Ein weiteres Beispiel ist die &quot;Liquiditätsgebühr&quot;, auch 
&quot;Demurrage&quot; oder &quot;Liegegebühr&quot; genannt, bei der Benutzer für die Dauer ihres 
Guthabens zahlen, sozusagen &quot;Negativzinsen&quot;. Die Idee dahinter ist, Mitglieder dazu 
zu ermuntern, ihr Guthaben zu verwenden, statt es auf dem Konto liegen zu lassen.
<br><br>
<i>Wo ist es zu finden?</i><br>
Alle Kontogebühren sind dem Kontotyp zugeordnet, ihre Konfiguration wird also über die 
Kontenverwaltung durchgeführt. &quot;Menü: Konten > Konten verwalten&quot;. Wählen 
Sie einen Kontotyp und klicken Sie auf das Bearbeiten-Symbol <img border="0" src="${images}/edit.gif"
	width="16" height="16">.
<br><br>Verwaltung und Übersicht der Kontogebühren finden Sie auf der Seite Kontenübersicht 
&quot;Menü: Konten > Kontogebühren&quot;. Auf dieser Seite erhalten Sie eine Übersicht aller 
Kontogebühren und deren Status; dort können Sie auch manuell eine Kontogebühr ausführen.
<hr class="help">

<A NAME="account_fee_overview"></A>
<h3>Kontogebühren</h3>
Die Seite Kontogebühren zeigt alle <a href="#account_fees"><u>
Kontogebühren</u></a>, die für irgendeinen Kontotyp aktiviert sind.
<br><br>Das Fenster zeigt eine Liste der aktiven (freigegebenen) Kontogebühren, deren letztes 
und nächstes Durchführungsdatum sowie deren Status (&quot;Läuft&quot;,
&quot;Beendet&quot;).
<br><br>Kontogebühren können entweder automatisch (planmäßig) oder manuell ausgeführt werden. 
Manuell auszuführende Kontogebühren werden nur dann ausgeführt, wenn der Link 
&quot;Jetzt ausführen&quot; angeklickt wird. 

Nachdem die Kontogebühr erfolgreich ausgeführt wurde, wird das letzte
Ausführungsdatum eingetragen, ebenfalls wird das Ausführungsdatum im 
<a href="#account_fee_history"><u>Kontogebühr-Verlauf</u></a> eingetragen (unteres Fenster).
<hr class="help">

<a name="account_fee_history"></a>
<h3>Kontogebühren-Verlauf</h3>
Diese Seite zeigt alle in der Vergangenheit abgebuchten Kontogebühren. 
Mit der Anwahl des Lupensymbols öffnen sich die Details über die Kontogebühren.
<br><br>
Die &quot;Statusspalte&quot; zeigt entweder den Statustext in blau an, 
dies bedeutet, dass die Kontogebühr erfolgreich war. Wird der Statustext
rot dargestellt, bedeutet dies, dass es Probleme während der Ausführung der Kontogebühr
gegeben hat. Probleme können auf der Seite Kontogebührdetails eingesehen und behoben werden. 
<hr class="help">


<a name="account_fee_log_details"></a>
<h3>Kontogebühr-Details</h3>
Dieses Fenster zeigt die Details der Kontogebühr. Die meisten Details sind
selbserklärend. Wenn die Kontogebühr vom Typ &quot;geplant&quot; ist, wird
ein Feld für den &quot;Zeitraum&quot; mit Anfangsdatum und Enddatum des Zeitraumes
für den Abbuchungszeitraum angezeigt (entweder monatlich, wöchentlich, täglich).
Falls die Gebühr täglich geplant ist, wird nur ein Datum angezeigt.<br><br>
Das Feld &quot;Gesamter eingezogender Betrag&quot; zeigt den gesamten Betrag
der Zahlungen und die akzeptieren Rechnungen (diese generieren eine Zahlung).
Aus diesem Grund kan sich der &quot;Gesamter eingezogender Betrag&quot; im Laufe der
Zeit erhöhen, wenn Mitglieder Rechnungen akzeptieren. Dies ist natürlich nur der Fall, wenn
die Option &quot;Rechnung senden&quot; in der 
<a href="#account_fee_details"><u>Kontogebühr-Konfiguration</u></a> freigebeben ist.
<br><br>
Der unterschiedliche Status (ausgeführ, übersprungen, usw.) wird ausführlich in der Hilfe
von <a href="#account_fee_log_member_search"><u>Kontogebühr (Mitgliedssuche) 
</u></a> beschrieben (siehe unterhalb). Wenn das Feld &quot;Fehler&quot; unterschiedliche
Anzahlen anzeigt, bedeutet dies, dass während der Ausführung der Gebühr eines oder mehrere Mitglieder nicht
abgebucht wurde. Wenn dies der Fall ist, wird eine Schaltfläche mit dem Text
&quot;Erneut abbuchen (für fehlgeschlagene Mitglieder) &quot;. Wenn Sie diese Schaltfläche anklicken, werden bei allen Mitglieder,
bei denen die Abbuchung fehlgeschlagen ist, sollte die Kontogebühr erneut abgebucht werden. Die
Anzahl der Fehler sollte nach der Abbuchung 0 sein und wenn sie zurück auf die Seite <a href="#account_fee_history">
<u>Kontogebühren-Verlauf</u></a> wechseln, sollte der Status &quot;Beendet&quot;
zu blau wechseln (und nicht weiterhin rot).  


<a name="account_fee_log_member_search"></a>
<h3>Mitgliedersuche (Kontogebühren)</h3>
Es ist möglich nach Status, Gruppen und individuelle Mitglieder für eine Kontogebeühr
zu filtern. Die folgende Status sind:
<ul>
	<li><b>Fehler</b> Deser Filter sucht alle Mitglieder bei denen aufgrund eines Fehlers die Kontogebühr
	nicht abgebucht wurde. Dies sollte nie passieren. Es wäre möglich, dass ein Problem
	bei Cyclos besteht, dies müsste an das Cyclosteam berichtet werden.
	Wir beheben den Fehler, so dass die Kontogebühren abgeschlossen werden können. Insbesondere
	Kontogebühren sollten sich in keinem inkonsistenten Zustand befinden.
	<li><b>Ausgeführt</b> Der Status ausgeführt gilt für alle durchgeführten Mitglieder. Grundsätzlich
	beinhaltet der Status ausgeführt auch alle anderen (weiter unten erläutert) außer dem Status 
	&quot;Fehler&quot;.
	<li><b>Übersprungen</b> Mitglieder mit diesem Status wurden ausgeführt aber die Gebühr wurde
	nicht abgebucht, das der Betrag unter dem Minimum (0,01) lag. 
	<li><b>Zahlung</b> Dieser Filter zeigt alle Mitglieder, bei denen die Gebühr direkt abgebucht wurde. 
	<li><b>Rechnung</b> Dieser Filter zeigt alle Mitglieder, die eine Rechnung erhalten 
	haben. Dies ist daher die Summe aller &quot;Offenen&quot; und &quot;Geschlossenen&quot;
	Rechnungen.
	<li><b>Offene Rechnung</b> Dieser Filter zeigt alle Mitglieder die eine Rechnung erhalten
	und diese noch nicht akzeptiert (bezahlt) haben. 
	<li><b>Akzeptierte Rechnung</b>
	Dieser Filter zeigt alle Mitglieder die eine Rechnung erhalten und diese akzeptiert (bezahlt) haben.
</ul>


<a name="account_fee_log_member_list"></a>
<h3>Kontogebührverlauf - Mitgliederliste</h3>
Dieses Fenster zeigt die Ergebnisliste (Mitglied) des oberen Fensters. Wird ein Mitglied ausgewählt,
gelangt man in das Profil des Mitglieds. Die Statusspalte zeigt den Status
der Kontogebühr. Ist der Status Zahlung oder Rechnung können Sie diese direkt öffnen.

<A NAME="account_fee_list"> </A>
<h3>Kontogebühren</h3>
Dieses Fenster zeigt eine Liste aller konfigurierten <a href="#account_fees"><u>
Kontogebühren</u></a> (aktiviert und nicht aktiviert) für diesen Kontotyp.
<ul>
	<li><img border="0" src="${images}/edit.gif" width="16" height="16">
	Dieses Symbol anklicken, um die Kontogebühr zu ändern.
	<li><img border="0" src="${images}/delete.gif" width="16" height="16">
	Dieses Symbol anklicken, um die Kontogebühr zu löschen.
</ul>
Um eine neue Kontogebühr hinzuzufügen, klicken Sie bitte auf &quot;Neue Kontogebühr 
einfügen&quot; unter der Liste.

<hr class="help">

<A NAME="account_fee_details"></A>
<h3>Kontogebühr ändern/einfügen</h3>
In diesem Fenster können Sie eine (neue oder bereits existierende) <a href="#account_fees">
<u>Kontogebühr</u></a> konfigurieren. Bitte beachten Sie, dass die Gebühr selbst eine 
Überweisung darstellt, sie erfordert also einen <a href="#transaction_types">
<u>Überweisungstyp</u></a>. Diesen Überweisungstyp müssen Sie eingerichtet haben, bevor 
Sie an die Einrichtung und Konfiguration einer neuen Kontogebühr gehen können.
<br><br>Die folgenden Konfigurationsoptionen sind verfügbar:</b>
<ul>
	<li><b>Name:</b> Der Name der Kontogebühr.<br>
	<li><b>Beschreibung:</b> Die Beschreibung der Kontogebühr.<br>
	<li><b>Freigegeben:</b> Nur wenn dieses Kästchen ausgefüllt ist, wird die Kontogebühr 
	abgebucht. Ob die Gebühr geplant oder manuell durchgeführt wird, spielt in diesem Fall 
	keine Rolle: ohne dass dieses Kästchen ausgefüllt ist, ist die Ausführung nicht möglich.<br>
	<li><b>Buchungsart:</b> Fünf verschiedene Buchungsarten sind möglich:
	<ul>
		<li><b>Festbetrag:</b> Beim Festbetrag handelt es sich um einen Beitrag (vom Mitgliedskonto 
		an das Systemkonto) oder um eine einmalige oder regelmäßige Zahlung von einem 
		Systemkonto an eine Gruppe von Mitgliedern.
		<li><b>Umsatzbezogener Prozentwert bei positiven Salden:</b> Ein positiver Umsatzprozentsatz wird 
		genauso wie Zinsen errechnet. Wird die Zahlungsrichtung (siehe unten) als 
		&quot;System an Mitglied&quot; definiert, handelt es sich eigentlich um Zinsen. 
		Ist die Zahlungsrichtung &quot;Mitglied an System&quot;, ist diese Gebühr eine 
		Art negativer Zins, eine Liquiditätsgebühr (Liegegebühr, Demurrage).<br>
        Die Demurrage wird auf der Grundlage des Zeitraumes vom Feld &quot;Periodesich&quot; berechnet.
        Am Ende eines jeden Tages wird innerhalb dieser Periode der Kontostand genommen und mit dieser
        Information der &quot;Durchschnitt&quot; des Kontossaldos berechnet.
        Zum Beispiel, wenn ein Mitglied 100 Einheiten auf seinem/ihrem Konto während einer Periode
        von 1 Monat, der durchschnittliche Kontosaldo wird genau 100 sein. Eine Demurrage von
        1% berechnet über diesen Saldo würde als Ergebnis 1 Einheit ergeben. 
 		<li><b>Umsatzbezogener Prozentwert bei negativen Salden:</b> Der negative Umsatzprozentsatz funktioniert 
		genau so, nur für Minussalden.
		<li><b>Saldobezogener Prozentwert bei positiven Salden:</b> Der Saldoprozentsatz bezieht sich nicht auf 
		den Umsatz (Zeit+Guthaben), sondern nur auf das Guthaben. Wird die Gebühr ausgeführt 
		(entweder manuell oder geplant), bezieht sich die Gebühr lediglich auf den Saldo 
		zum Zeitpunkt der Abbuchung. Wenn also im oben angeführten Beispiel das Mitglied 
		direkt vor Berechnung der Gebühr sein Guthaben auf null reduziert, so wird ihm 
		nichts berechnet.
		<li><b>Saldobezogener Prozentwert bei negativen Salden:</b> Der negative Saldoprozentsatz funktioniert genau so, 
		nur mit Minussalden.
	</ul>
	
		<br>
<b>Beispiel Kontogebühr:</b><br>
Eine Überweisung für jeden Teilbetrag bilden, wie folgt: <br>
Darlehensbetrag = 1000, Bewilligungsgebühr = 30, monatliche Zinsen = 20, in 10 Zahlungen = 10 x 105 <br><br>
Jede Zahlung sollte erzeugen:<br	>
<ul>
	<li>Eine Basisrückzahlung von 100 (ursprünglicher Rückzahlungsbetrag, ohne Gebühren und Zinsen) 
	<li>Die Bewilligungsgebühr-Rückzahlung von 3 
	<li>Die monatliche Zinsrückzahlung von 2 
	<li>Falls abgelaufen, ie: Ablaufgebühr = 4, 5 Tage abgelaufen mit 1%/Tag 
	<li>Die Ablaufgebühr-Rückzahlung von 4 
	<li>Die täglichen Ablauf-Zinsrückzahlung von 5 (1% of 100 * 5 Tage) 
</u>	

<br><br>
<b>Formel:</b><br>
Variable<br>
RA = erhaltener Darlehensbetrag <br>
IN = Ratenzahl <br>
GF = Bewilligungsgebühr <br>
MI = monatliche Zinsen <br>
EF = Ablaufgebühr <br>
EI = Ablaufzinsen <br>
IA = Ratenbetrag <br>
D = Verzug in Tagen <br>
DA = Gesamtbetrag Darlehen<br><br>

Wenn bewilligt: <br>
DA = RA + GF + MI/100 ^ (Gesamtzahl Tage / 30) * RA <br>
IA = DA / IN 
<br><br>
Die Formel lautet: 
IA = (RA + GF) * (((1+MI/100) ^ (IN + D/30)) * MI) / (((1+MI/100) ^ IN) - 1) 
DA = IA * IN 
Beachten Sie, dass die letze Ratenzahlung DA - (IA * (IN-1)) ist, somit haben wir keine Rundungsprobleme.
<br><br>		


	
	Bitte denken Sie daran, dass die Buchungsart für bereits bestehende Gebühren nicht 
	verändert werden kann; er kann lediglich für neue Kontogebühren eingestellt werden.<br>
	<br>
	<li><b>Toleranz:</b> Dieses Element ist nur dann sichtbar, wenn unter 
	&quot;Buchungsart&quot; der Wert &quot;Positiver Umsatzprozentsatz&quot; gewählt
	wurde. Bei Toleranz handelt es sich um die Einstellung, die verwendet werden kann, um 
	zu verhindern, dass Mitglieder, die eine Systemzahlung erhalten haben (z.B. ein Darlehen) 
	und es innerhalb kurzer Zeit wieder ausgeben (entweder indem sie es umwandeln 
	oder indem sie etwas erwerben), für diesen Betrag und Zeitraum eine Liquiditätsbühr zahlen müssen. 
	Falls zum Beispiel ein Mitglied 100 Einheiten erhält und diese innerhalb von 24 Stunden 
	ausgibt, wird, wenn der Toleranzzeitraum auf 24 Stunden eingestellt ist, von diesem 
	Mitglied für diese Zeit und diesen Betrag keine Gebühr erhoben. Wenn das Mitglied 
	allerdings die 100 Einheiten innerhalb von zwei Tagen ausgibt, wird ihm der gesamte 
	Zeitraum von 2 Tagen berechnet (und nicht nur einem).<br>
	<br>
	<li><b>Zahlungsrichtung:</b> Dies definiert die Richtung der Kontogebühr, also entweder 
	vom Mitglied an das System oder anders herum.<br>
	<br>
	<li><b>Generierter Überweisungstyp:</b> Die Kontogebühr bedarf eines Überweisungstyps. 
	Die Überweisung entspricht dem gewählten Überweisungstyp.<br>
	<br>
	<li><b>Betrag:</b> Wenn Sie bei Buchungsart &quot;Festbetrag&quot; wählen, wird dieser 
	Betrag erhoben. Andernfalls ist es ein Prozentsatz des Umsatzes oder des Guthabens.<br>
	<br>
	<li><b>Freibetrag:</b> Mit dem Freibetrag ist es möglich, eine Gebühr nur ab einem 
	bestimmten (durchschnittlichen) Betrag geltend zu machen.<br>
	Bei der Buchungsart für Umsatzprozentsatz berechnet sich die Gebühr nur auf den Betrag über 
	dem Freibetrag. 
	Im oben angeführten Beispiel heißt das:
	<ul>
		<li><b>Buchungsart:</b> Positiver Umsatzprozentsatz
		<li><b>Betrag:</b> 1%
		<li><b>Freibetrag:</b> 40
		<li><b>Umsatz auf Mitgliedskonto:</b> 100 Einheiten für den gesamten Monat
	</ul>
	Ohne Freibetrag (d.h. ein Freibetrag von 0) betrüge die berechnete Gebühr 1 Einheit. 
	Da aber ein Freibetrag von 40 eingestellt ist, werden die ersten 40 Einheiten nicht 
	berechnet. Die Gebühr beträgt in diesem Fall 0,60 Einheiten.<br>
	Bei einer Kontogebühr mit Festbetrag wird das Mitglied nicht belastet, wenn
	der Saldo kleiner oder gleich als der Freibetrag ist.<br>
	<br>
	<li><b>Rechnung senden:</b> Dieses Element ist nur im Fall einer Zahlungsrichtung 
	&quot;Mitglied an System&quot; sichtbar.<br>
	Dies definiert, was geschieht, wenn ein Mitglied, dem eine Gebühr berechnet wurde, 
	nicht genug Guthaben hat, um die Gebühr zu zahlen.<br>
	Die folgenden Optionen sind verfügbar:
	<ul>
		<li><b>Nur wenn das Mitglied nicht genügend Guthaben hat:</b> in diesem Fall 
		erhalten nur Mitglieder mit nicht ausreichenden Guthaben eine Rechnung; 
		der Rest wird abgebucht.
		<li><b>Niemals (Kreditlimit kann überschritten werden):</b> in diesem Fall wird 
		die Gebühr von allen Mitgliedern abgebucht, und alle Mitglieder zahlen, auch wenn 
		dadurch ihr Konto unter das Kreditlimit rutscht.
		<li><b>Immer (Mitglied nicht automatisch belasten):</b> in diesem Fall wird mit der 
		Gebühr niemand belastet, sondern nur Rechnungen versandt, ganz gleich ob die 
		Mitglieder dadurch ihr Kreditlimit erreichen oder nicht.
	</ul>
	<br>
	<b>Anmerkung:</b> Sie werden einen Überweisungstyp für die Zahlung Mitglied an System
	entsprechend der Rechnung anlegen müssen. Meistens hat diese Art von Überweisung
	die Bezeichnung &quot;Beitrag&quot;. Wenn Sie diese Überweisungsart für Mitglieder
	und Administratoren als mögliche (manuelle) Überweisung nicht anzeigen wollen,
	können Sie die Option &quot;Freigegeben&quot; im Überweisungstyp deaktivieren.
	Die Mitglieder können die Rechnung immer noch aktzeptieren, jedoch Mitglieder und 
	Administratoren haben nicht die Berechtigung mit diesem Überweisungstyp manuell
	eine Zahlung durchzuführen.  
	<br>
	<br>
	<li><b>Ausführungsmodus:</b> Hier können Sie definieren, ob die Kontogebühr geplant 
	oder manuell ausgeführt wird. Bitte denken Sie daran, dass dies für bereits bestehende 
	Gebühren nicht verändert werden kann; es kann lediglich für neue Gebühren eingestellt 
	werden.<br>
	Wird die Gebühr manuell abgebucht, muss ein Administrator die Abbuchung der Gebühr im 
	Fenster <a href="#account_fee_overview"><u>Übersicht Kontogebühren</u></a> initiieren. 
	Ist die Buchungsart &quot;Geplant&quot;, wird die Gebühr automatisch zum konfigurierten 
	Termin abgebucht. In diesem Fall stehen folgende zusätzliche Optionen zur Verfügung:
	<ul>
		<li><b>Periodisch:</b> Dies ist der periodische Intervall, in dem die Kontogebühr 
		erhoben wird. Zum Beispiel jeden Monat oder jedes Jahr. Ein Beispiel: Wird dies auf 
		&quot;3 Monate&quot; eingestellt, so wird die Kontogebühr alle 3 Monate ausgeführt. 
		Ist die Einstellung &quot;0&quot;, läuft die Kontogebühr nur einmal.
		<li><b>Tag:</b> Der Tag des Monats oder der Woche, an dem die Kontogebühr ausgeführt 
		wird. Dies erscheint natürlich nicht, wenn Sie eine tägliche Ausführung wählen.
		<li><b>Stunde:</b> Die Stunde (1-24), zu der die Kontogebühr ausgeführt wird.
	</ul>
	<br>
	<li><b>Freigeben ab:</b> Dieses Feld steht nur zur Verfügung, wenn die Option
	&quot;Freigegeben&quot; aktiviert ist. Wenn Sie eine periodisch geplante Kontogebühr erst
	ab einem bestimmten Zeitpunkt ausführen wollen, können Sie hier einen Zeitpunkt
	angeben.<br>
	Zum Beispiel: Sie erstellen eine geplante Kontogebühr periodisch jeden Monat und jeweils
	am 1. des Monats. Sie möchten diese Kontogebühr aber erst am 1. Januar 2011 starten 
	(angenommen, wir leben derzeit im Juni 2010). Sie müssen dann das Datum bei 
	&quot;Freigeben ab&quot; zwischen dem 1. Dezember 2010 und dem 1. Januar 2011 setzen.<br>
	Anmerkung: Das &quot;Freigeben ab&quot;-Datum kann nur einmal eingestellt werden. Die
	Option wird nicht mehr gezeigt, sobald sie aktiv wurde. Natürlich können Sie die Kontogebühr
	jederzeit mit der Option &quot;Freigegeben&quot; deaktivieren oder aktivieren.<br>
	<br>
	<br>
	<li><b>Gruppen</b>: Hier können Sie wählen, welche Gruppe(n) die Kontogebühr entrichten 
	oder empfangen sollen.
</ul>
<hr class="help">

<A NAME="credit_limit"></A>
<h3>Persönliches Kreditlimit:</h3>
<br><br><i>Wo ist es zu finden?</i><br>
Das persönliche Kreditlimit ist zugänglich über <a href="${pagePrefix}profiles#accounts_actions">
<u>Aktionen für Mitglieder</u></a> (Fenster unterhalb des Mitgliedsprofils).
<br><br>Mit dieser Funktion können individuelle Kreditlimits für Mitglieder eingestellt werden. 
Wird ein neues Mitgliedskonto eingerichtet, so erhält das Mitglied zunächst das als 
Standard eingestellte, in den <a href="${pagePrefix}groups#manage_groups">
<u>Gruppeneinstellungen</u></a> konfigurierte Kreditlimit.<br>
Mit Hilfe dieses Fensters können Sie diese Gruppen-Kreditlimits außer Kraft setzen. Hier 
können Sie – für alle Konten – ein oberes und ein unteres Kreditlimit für dieses einzelne 
Mitglied einstellen. Erreicht ein Mitglied das untere Kreditlimit, so kann es keine 
Zahlungen mehr tätigen.
<br><br>Das obere Kreditlimit ist eine nur selten benutzte Funktion. Erreicht ein Mitglied das 
obere Kreditlimit, so kann es keine Zahlungen empfangen. Der Auftraggeber erhält dann eine 
Nachricht, dass der Empfänger nicht in der Lage ist, Zahlungen zu empfangen. Eine Ausnahme 
ist der geplante Beitrag, bei dem Mitglieder Einheiten empfangen. Diese Zahlungen werden 
immer durchgeführt.
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