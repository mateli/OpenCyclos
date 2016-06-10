<div style="page-break-after: always;">
<span class="admin"> <a name="top"></a>
<br><br>
In Cyclos können Sie Benutzeroberfläche ändern, indem Sie Änderungen in den 
Cyclos-Systemdateien vornehmen. Zusätzlich ist es möglich, Änderungen zu Themendateien 
zusammen zu packen, und sie mit anderen Cyclos-Benutzern zu teilen.<br>
Dies bedeutet in der Praxis, dass Sie fast alles in Zusammenhang mit den Seiten, 
die Sie oder Ihre Mitglieder in Ihrem Browser sehen, kontrollieren können: sowohl 
Inhalt als auch Aussehen können modifiziert werden.

<a name="type_list"></a> Die folgenden Dateitypen können Sie anpassen:
<ul>
	<li><b><a href="#statics"><u>Statische Dateien</u></a> </b>
	<li><b><a href="#helps"><u>Hilfedateien</u></a> </b>
	<li><b><a href="#jsp"><u>Anwendungsdateien</u></a></b>
	<li><b><a href="#css"><u>CSS-Dateien</u></a></b>
	<li><b><a href="#images"><u> Bilder</u></a></b> hier
	können Sie Systembilder ändern oder eigene Bilder hochladen und verwenden.
	<li><b><a href="#themes"><u>Themes</u></a></b> ermöglichen Ihnen zu einem anderen 
	vordefinierten Layout zu wechseln, ohne dabei eine Menge Dateien ändern zu müssen. 
	<li><b><a href="${pagePrefix}documents"><u>Dokumente</u></a></b>
</ul>
<b>Wichtig:</b> Bitte beachten Sie, dass die Bearbeitung dieser Art von Dateien recht 
kompliziert sein kann. Um das zu tun, sollten Sie sich mit css und html auskennen. 
<br><br>
Bitte beachten Sie, dass Sie auf Systemebene nicht nur benutzerdefinierte Dateien 
einstellen können (wird in dieser Hilfe-Datei behandelt), sondern auch auf 
<a href="${pagePrefix}groups#manage_group_customized_files"><u>Gruppenebene</u></a> und  
sogar auf <a href="${pagePrefix}groups#manage_group_filter_customized_files"><u>Gruppenfilterebene</u></a>. 
<br><br>
<i>Wo ist es zu finden?</i><br>
Zum Content Management auf Systemebene gelangen Sie über das Hauptmenü-Element 
&quot;Content Management&quot;.<br>
Um Content Management auf der Gruppenebene durchzuführen, gehen Sie bitte zum Fenster 
<a href="${pagePrefix}groups#manage_groups"><u>Gruppen verwalten</u></a> und 
klicken Sie dort auf das Bearbeiten-Symbol <img border="0" src="${images}/edit.gif"
	width="16" height="16">&nbsp; der jeweiligen Gruppe. Dieses Thema ist in der Hilfedatei 
Gruppen behandelt.<br>
Um Content Management auf der Gruppenfilterebene durchzuführen, gehen Sie bitte auf 
&quot;Menü: Benutzer und Gruppen > Gruppenfilter&quot;, und klicken Sie auf das 
Bearbeiten-Symbol <img border="0"	src="${images}/edit.gif" width="16" height="16">&nbsp;
eines Gruppenfilters. Auch dieses Thema ist in der Hilfedatei Gruppen behandelt. 
<br><br>
<i>Wie wird mit dem Content-Management gearbeitet?</i><br>
Damit die Menüelemente des Content Management erscheinen, müssen Sie die 
<a href="${pagePrefix}groups#manage_group_permissions_admin_system"><u>
Administrator-Berechtigungen</u></a> einstellen. Es gibt mehrere Elemente, die die 
Sichtbarkeit der Elemente im &quot;Menü: Content-Management&quot; beeinflussen; 
die folgenden Bereiche gehören dazu: 
<ul>
	<li>&quot;Benutzerdefinierte Bilder&quot;
	<li>&quot;Systemweite benutzerdefinierte Dateien&quot;
	<li>&quot;Themes&quot;
</ul>

<hr>

<a name="statics"></a>
<h3>Statische Dateien</h3>
Dies sind komplette Seiten der Anwendung, die für Ihre Organisation angepasst werden 
können.<br>
Um diese Dateien verändern zu können, sollten Sie sich mit HTML auskennen. Achten Sie 
bitte darauf, HTML-Tags vollständig zu lassen, und keinen Inhalt innerhalb von Anwendungs-Tags 
zu ändern (Tags sind alles innerhalb von &lt; und &gt;). 
<br><br>Die Funktion ermöglicht die Änderung der folgenden statischen Dateien (möglicherweise ist 
diese Liste veraltet, und es sind noch weitere Dateien hinzu gekommen): 
<ul>
	<li><b>contact.jsp:</b> Seite erscheint im Menu des Mitgliedsbereichs: 
	&quot;Menü: Hilfe > Kontakt&quot;; sollte Information dazu bereit halten, wie man mit Ihrer 
	Organisation in Kontakt tritt. 
	<li><b>general_news.jsp:</b> Allgemeine Nachrichten, zu sehen im Fenster 
	<a href="${pagePrefix}home#home_news"><u>Neuigkeiten (Schwarzes Brett)</u></a> auf der 
	Startseite des Cyclos-Mitgliedsbereichs. 
	<li><b>login.jsp:</b> Layout der Anmeldeseite (der Text der Anmeldeseite kann über die 
	Übersetzungsfunktion geändert werden). 
	<li><b>member_about.jsp:</b> Diese Seite erscheint im Menü der Mitglieds- und Administratorenbereiche  
	&quot;Menü: Hilfe > Über Cyclos&quot;. 
	<li><b>posweb_footer.jsp:</b> Fußzeile der Seite zum externen Zahlungsmodul POS-Web. (kann 
	vom Mitglied angepasst werden). 
	<li><b>posweb_header.jsp:</b> Kopfzeile der Seite zum externen Zahlungsmodul POS-Web. (kann 
	vom Mitglied angepasst werden). 
	<li><b>posweb_login.jsp:</b> Kopfzeile der Anmeldeseite zum externen Zahlungsmodul POS-Web. 
	<li><b>top.jsp:</b> Allgemeine Überschrift der Anwendung. 
	<li><b>webshop_footer.jsp:</b> Fußzeile der Seite zum externen Zahlungsmodul Internetshop.
	<li><b>webshop_header.jsp:</b> Kopfzeile der Seite zum externen Zahlungsmodul Internetshop. 
</ul>
In den Text von statischen Dateien können Sie auch <a href="#insert_images"><u>Bilder</u></a> 
einfügen.
<hr class="help">

<a name="helps"></a>
<h3>Hilfedateien</h3>
Es kann sein, dass Sie Hilfedateien verändern müssen, da Sie Änderungen in den dazugehörigen  
<a href="#jsp"><u>Anwendungsdateien</u></a> vorgenommen haben, oder einfach auch nur weil Sie 
den ursprünglichen Text nicht verständlich genug finden.<br>
Wenn Sie eine Hilfedatei verändern möchten, müssen Sie sowohl deren Namen wissen, als auch 
die Textmarke innerhalb der Datei den Sie bearbeiten möchten. Hilfedateien sind nach einem Hauptthema 
organisiert, insgesamt gibt es davon etwa 30. Jede von ihnen enthält den Text mehrerer Hilfefenster; 
Springen zu diesen Fenstern geschieht über Textmarker.<br>
Um den Dateinamen und den Textmarker herauszufinden, bewegen Sie mit dem Mauszeiger über dem Hilfesymbol 
(oben rechts im Fenster). Die Statuszeile Ihres Browsers zeigt Ihnen den Ort. 
&quot;Hilfe: dateiname#textmarke&quot; in diesem Fall wäre die Datei &quot;dateiname.jsp&quot;, und der 
Abschnitt der Hilfedatei würde &quot;textmarke&quot; genannt. Innerhalb der Hilfedatei befindet 
sich die Textmarke in einem &lt;a name="textmarke"&gt;...&lt;/a&gt; Tag.<br>
Bitte beachten Sie, dass Ihr Browser Statuszeilen-Nachrichten verbergen kann; Sie sollten Ihre 
Browser-Einstellungen ändern und sicherstellen, dass &quot;Statuszeilen-Nachrichten anzeigen&quot; 
freigegeben ist, falls nicht angezeigt wird, wo die Hilfedateien zu finden sind.  
<br><br>
Sie können auch Bilder in den Text der Hilfedateien einfügen, achten Sie allerdings auf die Größe 
der Bilder, da das Hilfefenster nur 300 x 400 Pixel hat. 

<hr class="help">

<a name="jsp"></a>
<h3>Anwendungsdateien</h3>
Eine Anwendungsdatei ist eine .jsp Datei in Cyclos, und enthält Code um Elemente auf den Seiten zu 
platzieren, aber keinen Text. In der Praxis bedeutet dies alle jsp-Dateien die keinen Hilfetext 
enthalten. Diese .jsp-Dateien definieren, was wohin auf die Seite kommt, die Sie in Ihrem Browser 
sehen.<br>
Die gesamte Struktur des Layouts kann verändert werden, dies kann allerdings größeren Einfluss auf 
Systeminterna und das Funktion der Anwendung haben. Seien Sie also bitte bei der Änderung von 
Anwendungsdateien sehr zurückhaltend. Es empfiehlt sich, Änderungen nur für kleinere 
Layoutverbesserungen vorzunehmen, z.B. die Anordnung von Elementen, oder wenn Sie möchten, dass 
ein Element auf der Seite nicht erscheint. Bevor Sie allerdings zur Tat schreiten, versichern Sie 
sich bitte, ob Ihr Vorhaben nicht auch über die normale Cyclos-Konfiguration über den 
Administrations-Bereich des Programms erreicht werden kann. 
<br><br>
Um Probleme zu vermeiden, bewahrt Cyclos stets eine Kopie der  <a
	href="#edit_customized_file"><u> Originaldatei</u></a> auf, die Sie leicht wiederherstellen können.
<br><br>
In den Text von jsp- Dateien können Sie auch  <a href="#insert_images"><u>Bilder</u></a> einfügen.

<hr class="help">

<a name="css"></a>
<h3>CSS-Dateien</h3>
CSS-Dateien (cascading style sheets) definieren das Aussehen der Elemente auf einer Seite. 
Falls Sie also bestimmten Elementen ein anderes Aussehen geben möchten, z.B. hervorgehobene 
Schaltflächen, auffallendere Menüelemente, etc. können Sie hier Veränderungen vornehmen. 
Um die Stylesheets zu ändern, sollten Sie sich mit CSS-Datei-Annotation auskennen. 
<br><br>
Cyclos arbeitet mit folgenden Stylesheet-Dateien:
<ul>
	<li><b>style.css</b><br>
	Das Haupt-Stylesheet für das Cyclos-Layout (Menü, Fenster und Überschriftbereich) 
	<li><b>login.css</b><br>
	Das Stylesheet für die Anmeldeseite. 
	<li><b>mobile.css</b><br>
	Das Stylesheet für die Mobiltelefonseite. 
	<li><b>posweb.css</b><br>
	Das Stylesheet für die POS-Web-Seite 
	<li><b>ieAdjust.css</b><br>
	Wird für die Lösung von Kompatibilitätsproblemen mit Internetexplorer verwendet.
</ul>
<br><br>
Um die Wirkung der Veränderungen in den CSS-Dateien zu sehen, müssen Sie möglicherweise die Seite 
in Ihrem Browser aktualisieren.  Dies tun Sie, indem Sie zur URL
&quot;www.yourdomain.org/cyclos/pages/styles/style.css&quot; gehen.<br>
Um sicher zu stellen, dass die neue Seite aktiv ist, können Sie in Ihrem Browser die 
Seite mehrere Male aktualisieren. 
<br><br>
Wenn Sie eine neue CSS-Datei verwenden möchten, können Sie einfach den gesamten Inhalt 
kopieren, und den Inhalt in das bestehende Stylesheets einfügen (im Fenster Style-Sheet 
bearbeiten), und die Seite wie oben beschrieben aktualisieren. 
<br><br>
Wenn Sie eine gefällige CSS-Datei eingerichtet haben, sind Sie herzlich eingeladen, 
uns diese zu schicken. Wir machen Sie dann für andere verfügbar. Ihr Stylesheet würde dann über 
Sourceforge und der Cyclos-Project-Site veröffentlicht. 
<hr class="help">

<a name="insert_images"></a>
<h3>Bilder in Textdateien</h3>
Es ist möglich, Bilder in Textdateien wie <a href="#statics"><u>statische Dateien</u></a> 
und <a href="#helps"><u>Hilfedateien</u></a> einzufügen. Um dies zu tun, müssen die Bilder 
für die Anwendung verfügbar sein. Sie können nachschauen, welche 
<a href="#system_images"><u>Systembilder</u></a> verfügbar sind, oder Sie können selbst ein 
Bild hochladen, über  &quot;Menü: Content Management > Benutzerdefinierte Bilder&quot;. 
Um ein Bild in eine Seite einzufügen, muss der folgende Tag am Anfang der statischen Datei 
platziert werden: 
<br><br>
<i>&lt;%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html"%&gt;</i>
<br><br>
Das Bild-Verzeichnis ist dann wie folgt:
<br><br><i>&lt;img src="&lt;html:rewrite page="/pages/images/some_image.jpg"/&gt;"&gt;</i>
<br><br>
Die Seite benötigt lediglich den Tag 'html:rewrite' für das Bild-Verzeichnis. Sie können alle 
normalen HTML-Argumente verwenden wie &quot;border, align, width and height&quot;.  

<hr class="help">

<a name="customized_files"></a>
<h3>Benutzerdefinierte Dateien</h3>
<b>Anmerkung:</b> Diese Hilfedatei zeigt nur ganz allgemeine Information zur Funktion 
dieses Formulars. Am Besten überprüfen Sie <a href="#type_list"><u>diese Liste</u></a>, 
und folgen dem Verweis Ihres Dateityps um mehr Information und Tipps darüber zu erhalten, 
welchen Dateityp Sie anpassen möchten.
<br><br>
Dieses Fenster zeigt eine Liste der <a href="#top"><u>benutzerdefinierten Dateien</u></a> 
die bereichts angepasst sind. Sie haben die folgenden Optionen: 
<ul>
	<li><b>Neue Datei anpassen:</b> Eine neue Datei anpassen durch anklicken der Schaltfläche 
	&quot;Weiter&quot;.
	<li><img border="0" src="${images}/preview.gif" width="16"
		height="16">&nbsp;Zeigt Ihnen eine Vorschau des Ergebnisses. 
	<li><img border="0" src="${images}/edit.gif" width="16"
		height="16">&nbsp;Ermöglicht Ihnen eine benutzerdefierte Datei zu bearbeiten. 
	<li><img border="0" src="${images}/delete.gif" width="16"
		height="16">&nbsp;Durch Anklicken des Löschen-Symbols entfernen Sie die benutzerdefinierte 
		Datei von der Liste. Dies bedeutet, dass die letzte Änderung der Datei sichtbar bleibt, 
		bei der nächsten Cyclos-Aktualisierung aber durch die als Standard eingestellte Seite 
		ersetzt wird.
</ul>
Anmerkung 1: Wird ein Dateiname in Rot gezeigt, weist das auf einen bestehenden Konflikt hin. 
Eine Erklärung zum Umgang mit Konflikten finden Sie auf der Hilfeseite  
<a href="#edit_customized_file"><u>Benutzerdefinierte Datei bearbeiten</u></a>.<br>
Anmerkung 2: Wenn Sie diese Funktionalität zum ersten mal verwenden, kann es sein, dass es in 
Ihrer Liste noch keine benutzerdefinierten Dateien gibt; es sind dann noch keine Symbole sichtbar. 
Dies hängt von dem Dateityp ab, den Sie anpassen möchten. 

<hr class="help">

<a name="edit_customized_file"></a>
<h3>Benutzerdefinierte Dateien bearbeiten</h3>
<b>Anmerkung:</b> Dieser Hilfeabschnitt zeigt nur ganz allgemeine Information zur Funktion 
dieses Formulars. Am Besten überprüfen Sie <a href="#type_list"><u>diese Liste</u></a>, 
und folgen dem Link Ihres Dateityps um mehr Information und Tipps darüber zu erhalten, welche 
<a href="#top"><u>benutzerdefinierte Datei</u></a> Sie anpassen möchten. 
<br><br>
In diesem Fenster können Sie eine Datei bearbeiten, die Sie zuvor zuvor angepasst haben. 
Klicken Sie wie üblich auf die Schaltfläche &quot;Bearbeiten&quot; und beginnen Sie mit der Änderung. 
Klicken Sie dann auf &quot;Weiter&quot; um Ihre Änderungen zu speichern. 
<ul>
	<li><b>Dateiname:</b> zeigt den Namen der Datei. Dieser kann nicht geändert werden. 
	<li><b>Inhalte:</b> Dieser Bereich zeigt den aktuellen Inhalt der Datei. Der Inhalt der Datei 
	kann verändert werden. Sie können html und xml Tags verwenden, was Ihnen eine Reihe von Möglichkeiten 
	eröffnet, wie z.B. die Seite &quot;Neuigkeiten (Schwarzes Brett)&quot; mit einer Symbolleiste mit 
	Schnellverweisen zu anderen Bereichen von Cyclos. Ebenso können Sie Javascript hinzufügen um das 
	Verhalten zu definieren. Dies ist allerdings Programmieren auf höherer Stufe; wenden Sie sich also 
	bitte zuerst an  <a	href="http://project.cyclos.org/wiki/index.php?title=Programming_guide#JSP"><u>
	Cyclos Programm-Anleitung</u></a>.
	<li><b>Originale Inhalte:</b> zeigt Ihnen die Originalcontents/inhalte dieser Datei, vor 
	Benutzerdefinition. Wenn Sie die benutzerdefinierte Datei von dieser
	<a href="#customized_files"><u>Liste</u></a> löschen wird der Originalinhalt wieder angewandt. 
	Natürlich können Sie auch einfach die Inhalte der &quot;Original Inhalte&quot; in den Textbereich 
	&quot;Inhalte&quot; kopieren und einfügen, falls der von Ihnen gerade definierte Inhalt scheinbar 
	nicht funktioniert.  
	<li><b>Neue Inhalte:</b> Wenn Sie gerade eine Cyclos-Aktualisierung vorgenommen haben und es eine 
	neue Version dieser Datei gibt, wird Cyclos die Datei mit dem neuen Inhalt nicht ersetzen, sondern 
	eine Systemmeldung generieren. Der neue Inhalt ist dann in diesem Bereich verfügbar. 
	<li><b>Konflikt beseitigen:</b> Gibt es eine neue Version einer benutzerdefinierten Datei, generiert 
	Cyclos eine Systemmeldung und verschiebt die neue Datei in den Bereich  &quot;Neue Inhalte&quot;. 
	Der Name der &quot;konfliktauslösenden&quot; benutzerdefinierten Datei erscheint in der Liste der 
	benutzerdefinierten Dateien in Rot.<br>
	Ist der Konflikt gelöst, und funktioniert alles einwandfrei, können Sie die Option 
	&quot;Konflikt beseitigen&quot; wählen und die Datei speichern. Wenn Sie dies getan haben, erscheint 
	der Name der Datei in der Dateiliste nicht mehr in Rot, und der neue Inhalt der Dateiversion wird in 
	den Bereich des Original-Inhalts verschoben.
</ul>
<hr class="help">

<a name="edit_new_customized_file"></a>
<h3>(Neue)benutzerdefinierte Datei anpassen</h3>
In diesem Fenster wählen Sie, welche Datei Sie <a href="#top"><u>anpassen</u></a> möchten.
Wählen Sie die gewünschte Datei im Listenauswahl &quot;Datei auswählen&quot;. Entweder sehen Sie 
die Dateien direkt in der Liste, oder Sie sehen die Verzeichnisse, in denen Sie die Dateien finden. 
Im Fall von Verzeichnissen und Unterverzeichnissen können Sie mit dem Listenauswahlfeld 
&quot;Datei auswählen&quot; die Verzeichnisse auswählen. Die Verzeichnisse sind im 
Textfeld &quot;Pfad&quot; über der Auswahl aufgelistet. Zu einem höheren Verzeichnis gelangen 
Sie über Auswahl des &quot;Auf&quot;-Links neben der Auswahl. Haben Sie das Verzeichnis mit 
den Dateien erreicht, erscheint nach Auswahl der Datei deren Inhalt im Textbereich 
darunter.<br>
Nachdem Sie die Schaltfläche &quot;Bearbeiten&quot; geklickt haben, können Sie die Datei bearbeiten. 
Speichern Sie diese Änderungen durch Anklicken der Schaltfläche &quot;Weiter&quot;. 
<br><br>
Wenn Sie eine veränderte, benutzerdefinierte Datei speichern, wird der Originalinhalt gespeichert 
und erscheint unter der Bedutzerdefinition. Wird eine Cyclos-Aktualisierung installiert, bewahrt 
Cyclos die benutzerdefinierte Datei, überprüft aber, ob es Unterschiede zwischen dem Originalinhalt 
und dem Inhalt der neuen Datei des Upgrades gibt. In diesem Fall verschiebt Cyclos den neuen 
Inhalt unter den Originalinhalt. Wählen von &quot;Konflikt beseitigen&quot; verschiebt den neuen 
Inhalt in den Bereich Originalinhalt. 
<br><br>
Wenn Sie die Benutzerdefinition einer Datei beenden (durch Entfernung von der Liste) wird der 
Originalinhalt verwendet. 
<hr>

<a name="images"></a>
<h2>Benutzerdefinierte Bilder</h2>
In Cyclos  können Sie auch Bilder individualisieren. Es gibt eine Reihe von Systembildern, 
Sie können aber auch Ihre eigenen Bilder hochladen und sie in jeder benutzerdefinierten 
Datei verwenden. 

Ihre Bilder hochladen können Sie über  &quot;Menü: Content Management&quot;,
wo Sie  <a href="#system_images"><u>Systembilder</u></a> verändern, eigene <a href="#custom_images">
<u>benutzerdefinierte Bilder</u></a> hochladen, oder <a href="#style_images"><u>Stylesheet-Bilder</u></a> 
ändern können.
<hr class="help">

<A NAME="system_images"></A>
<h3>Systembilder</h3>
<br><br>
Dieses Fenster zeigt eine Liste der derzeitigen System-<a
	href="#images"><u>Bilder</u></a> in Cyclos. Wenn Sie eine Bildvorschau auf 
der Liste anklicken, erscheint das Bild in Originalgröße in einem Popup-Fenster. 
Ein Systembild ersetzen können Sie unter  <a href="#images_upload"><u>
Systembild aktualisieren</u></a> unten.
<hr class="help">

<A NAME="custom_images"></A>
<h3>Benutzerdefinierte Bilder</h3>
<br><br>
Dieses Fenster zeigt eine Liste der derzeitigen benutzerdefinierten  
<a href="#images"><u>Bilder</u></a> in Cyclos. Wenn Sie eine Bildvorschau auf der 
Liste anklicken, erscheint das Bild in Originalgröße in einem Popup-Fenster. Die 
benutzerdefinierten Bilder können in <a href="#statics"><u>statischen Dateien</u></a>, 
<a href="#helps"><u>Hilfedateien</u></a>, und sogar in Nachrichten verwendet werden. 
<br><br>
Ein Bild entfernen können Sie durch Auswahl des Löschen-Symbols <img border="0"
	src="${images}/delete.gif" width="16" height="16">&nbsp;.<br>
Um ein benutzerdefiniertes Bild hinzufügen können Sie unter  <a href="#images_upload"><u>
Neues benutzerdefiniertes Bild</u></a> unten.
<hr class="help">

<A NAME="style_images"></A>
<h3>CSS-Bilder</h3>
<br><br>
CSS-Bilder sind <a href="#images"><u>Bilder</u></a> , die im Layout von Cyclos 
verwendet werden können, wie z.B. für Überschriften, Menüelemente, Schaltflächen und 
Hintergründe. Diese Bilder verwenden Sie in einer <a href="#css"><u>CSS-Datei</u></a>.
<br><br>
Ein Bild entfernen können Sie durch Auswahl des Löschen-Symbols <img border="0"
	src="${images}/delete.gif" width="16" height="16">&nbsp;.<br>
Wenn Sie eine Bildvorschau auf der Liste anklicken, erscheint das Bild in Originalgröße 
in einem Popup-Fenster. Um ein benutzerdefiniertes Bild hinzufügen können Sie unter <a
	href="#images_upload"><u>Neues CSS-Bild</u></a> unten.
<hr class="help">

<A NAME="images_upload"></A>
<h3>Bilder hochladen</h3>
<br><br>
Im Fall eines <a href="#system_images"><u>Systembilds</u></a>, wählen Sie bitte zuerst 
das Bild, das Sie ersetzen möchten, indem Sie den Namen von der obigen Liste im Auswahlfeld 
&quot;Neues Bild hochladen&quot; auswählen. Bitte beachten Sie, dass dieses Auswahlfeld nicht 
erscheint, wenn Sie ein <a href="#custom_images"><u>benutzerdefiniertes Bild</u></a> 
oder <a href="#style_images"><u>CSS-Bild</u></a> hochladen.<br>
Danach klicken Sie bitte auf die Schaltfläche &quot;Durchsuchen...&quot; und wählen Sie das 
Bild, das Sie hochladen möchten, in Ihrem (lokalen) Rechner oder Netzwerk. Stellen Sie bitte 
sicher, dass es die Endung jpg, jpeg, gif oder png hat. Danach klicken Sie bitte auf 
&quot;Weiter&quot;. Das neue Bild erscheint im Listenfenster oben.

<hr>

<a name="themes"></a>
<h2>Themes</h2>
Ein Theme, manchmal auch Design oder Layout genannt, ist ein Paket von Bildern und Einstellungen, 
die das Aussehen und Verhalten der grafischen Benutzeroberfläche (GUI) von Computerprogrammen 
wie Cyclos festlegen. Die Themes-Funktion bietet eine schnelle Methode um Layouts zu wechseln, 
ohne das allgemeine CSS-Datei und die CSS-Bilder bearbeiten zu müssen. 

Die Theme-Funktion finden Sie über  &quot;Menü: Content Management > Themes&quot;.
<hr>


<A NAME="select_theme"></A>
<h3>Theme Auswahl</h3>
<br><br>
Um ein anderes  <a href="#themes"><u>Theme</u></a> zu wählen, entscheiden Sie 
sich für eines in der Auswahlbox &quot;Theme&quot;, und klicken Sie dann auf die 
Schaltfläche &quot;Anwenden&quot;. Damit das Theme erscheint, müssen Sie Ihren Browser 
aktualisieren. In manchen Fällen müssen Sie den Browser-Cache leeren.<br>
Bitte beachten Sie, wenn Sie diese Funktionalität noch nie verwendet haben, 
es sein kann, dass keine Themes verfügbar sind und das Auswahlfeld leer ist. In einem 
solchen Fall müssen Sie zuerst ein neues Theme <a href="#import_theme">
<u>importieren</u></a>.
<br><br>
Wenn Sie die Schaltfläche &quot;Entfernen&quot; anklicken, wird das Theme entfernt, 
und die benutzerdefinierte Layout-Änderung gelöscht. Wenn Sie also Ihre benutzerdefinierte 
Anpassung des <a href="#css"><u>allgemeinen Stylesheet</u></a> oder der 
<a href="#style_images"><u>CSS-Bilder</u></a> durchgeführt haben, sollten Sie dies 
zuerst als Theme <a href="#export_theme"><u>exportieren</u></a>, so dass Sie es 
später leichter wieder importieren können. Die Theme-Funktion betrifft lediglich Stylesheets; 
sie hat keinen Einfluss auf die <a href="#statics"><u>statischen Dateien</u></a> und 
<a href="#helps"><u>Hilfedateien</u></a>. 

<hr class="help">

<A NAME="import_theme"></A>
<h3>Neues Theme importieren</h3>
<br><br>
Mit dieser Funktion können Sie ein <a href="#themes"><u>Theme</u></a> importieren. Ein 
Cyclos-Theme hat die Endung .theme. Verwenden Sie einfach die Schaltfläche 
&quot;Durchsuchen&quot; um die Datei zu öffnen, und klicken Sie dann auf &quot;Weiter&quot;.

<hr class="help">

<A NAME="export_theme"></A>
<h3>Aktuelle Einstellungen als Theme exportieren</h3>
<br><br>
Falls Sie Ihr eigenes <a href="#themes"><u>Theme</u></a> erstellt haben (durch Änderung 
der <a href="#css"><u>CSS-Datei</u></a> und/oder der <a	href="#style_images"><u>
CSS-Bilder</u></a>) können Sie das Theme als Theme Datei exportieren.<br>
Diese Funktion ermöglicht Ihnen das aktuell aktive Theme zu exportieren. Füllen Sie 
bitte die Felder aus, und klicken Sie auf die Schaltfläche &quot;Weiter&quot; 
darunter.<br> 
Sie können auch einen ausgewählten Style der Theme Dateien exportieren, indem Sie die 
Kontrollkästchen darunter anklicken. Die folgenden drei Optionen sind verfügbar: 
<ul>
	<li><b>System:</b> Das sind die Core-Dateien <a href="#jsp"><u>.jsp</u></a>
	und <a href="#css"><u>.css</u></a>.</li>
	<li><b>Anmeldeseite:</b> Dies ist die  Anmelde-Startseite. </li>
	<li><b>Mobile:</b> Dies sind Seiten für den Zugriff über Mobiltelefon. </li>
</ul>
<br><br><i>Wenn Sie Ihr eigenes Theme entwickeln, denken Sie bitte daran, es auch an das 
Cyclos-Entwicklerteam weiter zu leiten.</i> Wir können es dann anderen Cyclos-Benutzern 
verfügbar machen.
<hr>


<A NAME="infotexts"></A>
<h2>Infotexte</h2> Infotexte sind Texte die in Cyclos gespeichert werden und
via Web-Service abgerufen werden können. Eine typische Anwendung ist das SMS-Modul. Benutzer können
per SMS ein Schlüsselwort senden, zum Beispiel &quot;Aktion&quot; und empfangen
einen entsprechenden Text. Die Infotexte können in Cyclos registriert und verwaltet
werden.<br><br>Infotexte können einen Titel und Nachrichtentext enthalten. Dies wird
häufig verwendet, falls die Texte auf einer Internetseite angezeigt werden. Details aller
Infotext-Einstellungen können sie auf <a
href="${pagePrefix}content_management#infotexts_edit"><u>Infotext bearbeiten
</u> </a> finden.<br> <br> Informationen, wie die Infotext via Web-Service
abgerufen werden können finden Sie auf <a
href="http://project.cyclos.org/wiki/index.php?title=Web_services#Info_texts"
target="_blank"><u>Wiki</u> </a>.
<hr class="help"> 


<A NAME="infotext_search"></A>
<h2>Infotexte suchen</h2> Auf dieser Seite können Sie nach <a
href="${pagePrefix}content_management#infotexts"><u>Infotexten</u>
</a> suchen und durch die Anwahl der Schaltfläche &quot;Neuer Infotext&quot; neue Texte erstellen.
<hr class="help">


<A NAME="infotexts_result"></A>
<h2>Suchergergebnisse Infotext</h2> Diese Seite zeigt das Ergebnis der Suche.
Mit dem Bearbeiten-Symbol <img border="0" src="${images}/edit.gif"
width="16" height="16"> können Sie einen Infotext bearbeiten und mit dem Löschen-Symbol
 <img border="0" src="${images}/delete.gif" width="16" height="16">
den entsprechenen Infotext löschen.
<hr class="help">


<A NAME="infotexts_edit"></A>
<h2>Neuer Infotext / Infotext bearbeiten</h2>
Auf dieser Seite können sie Regeln und den Inhalt des Infotextes definieren.
Die folgenden Optionen sind verfügbar.
<ul>
	<li><b>Schlüsselwort: </b>Dies ist das Schlüsselwort, das verwendet wird um den 
	entsprechenen Text (Titel und Text) abzurufen. Es sind mehr als ein Schlüsselwort möglich.
	In diesem Fall dienen diese zusätzliche Schlüsselwörter als Alias.
	<li><b>Titel: </b>Dies ist der Text der  abgerufen werden kann. Im Fall von Kurztexten (z.B. SMS) wird nur dieser benötigt.
	<li><b>Text: </b>Für bestimme Funktionen, welche Titel und Text verwendet 
	werden (z.B. Internetseiten) wird hier der Text definiert.
	<li><b>Gültigkeit</b> Diese Einstellung definiert die Periode wann der Infotext 
	aktiv ist und abgerufen werden kann.
	<li><b>Freigegeben</b> Hier können Sie den Infotext freigeben oder sperren.
     Falls gesperrt (nicht angewählt), kann der Infotext nicht abgerufen werden.
</ul>


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

</span>
