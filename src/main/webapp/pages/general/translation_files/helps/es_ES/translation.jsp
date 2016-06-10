<div style="page-break-after: always;">
<a name="translation_top"></a>
<span class="admin">
<br><br>
Cyclos soporta diferentes idiomas, y le permite administrar sus correspondientes traducciones.<br>
Todos los textos desplegados a los usuarios finales se encuentran contenidos en un archivo específico para cada idioma. <br>
Cyclos posee un número de idiomas a seleccionar, 
a través del menú de configuración usted puede cambiar el idioma de su instalación con sólo unos pocos clicks.<br>
Si no se encuentra satisfecho con la traducción obtenida, usted mismo puede 
modificar cada uno de los textos en forma individual o términos específicos en Cyclos.<br>
Este módulo también contiene una sección para el mantenimiento de todos los mensajes enviados a través de Cyclos, 
y todas las notificaciones enviadas por correo electrónico. 

<i> ¿Dónde la encuentro? </i> <br>
El módulo de traducción puede ser accedido a través del "Menú: Traducción".<br>
Las siguientes opciones se encuentran disponibles:
<ul>
	<li> <b> <a href="#translation_keys"><u>Aplicación</u></a>: </b>
	Le permite gestionar la traducción de todos textos y
	términos que aparecen en las ventanas de la aplicación.
	<li> <b> <a href="#notifications"><u>Notificaciones</u></a>: </b>
	Le permite gestionar la traducción de todas las notificaciones enviadas por el sistema.
	<li> <b> Correos electrónicos: </b> 
	Le permite gestionar los textos de los mensajes de correo electrónico enviados por el sistema.
	<li> <b> Importar / Exportar: </b> Permite la importación o exportación de archivos de traducción. 
	Esto permite a las comunidades de Cyclos compartir sus traducciones con otros usuarios Cyclos.
</ul>
Nota: Los archivos de texto estáticos, como los contactos y las nuevas páginas, no se gestionan a través del
módulo de traducción, sino a través de la <a href="${pagePrefix}content_management"><u>Gestión de contenido</u></a>.
<br><br>
Nota 2: Si desea cambiar el idioma de Cyclos, este no es el lugar.<br> 
El idioma puede ser cambiado en el 
<a href="${pagePrefix}settings#local"><u>Menú: Configuración> Configuración local</u></a> en el bloque de "Internacionalización".<br>
<hr>

<a name="translation_keys"></a>
<h2> Claves de traducción </h2>
Cualquier texto corto escrito desplegado en la interfaz de Cyclos, se mantiene en un archivo específico por cada idioma. 
La interfaz de Cyclos le permite a usted sustituir todo el archivo de traducción, o editar cada clave en el archivo de forma individual.

Si una <a href="#key"><u>clave</u></a> que es utilizada en una página de aplicación, 
no puede ser encontrada en el <a href="#language_file"><u>archivo de traducción</u></a>, la clave
aparecerá en la página entre signos de interrogación.<br> 
("? translationMessage.search.showOnlyEmpty ???")
<br>
En tal caso, podrá agregar la clave (y su valor) en el archivo de traducción usted mismo, a través del "Menú: Traducción> Aplicación".
<br><br> 
Si usted no está satisfecho con la traducción y como aparece en la ventana del navegador, puede adaptarlo.<br> 
Siga el siguiente procedimiento:
<ol>
	<li> Vaya a la página de búsqueda de términos para la traducción (a través del "Menú: Traducción> Aplicación"), 
	allí puede escribir la expresión que desea editar y adaptar su "valor".<br> 
	El término no es sensible a mayúsculas - minúsculas, y el sistema también buscará coincidencias
	si se introduce sólo una parte del término. <br>
	Haga click en "Búsqueda" para mostrar los resultados obtenidos.
	
	<li> En la página de resultados, utilice el ícono de Modificación 
	<img border="0" src="${images}/edit.gif" width="16" height="16">&nbsp;, para cambiar su valor.
</ol>
	<br><br>
	Usted también puede añadir o eliminar un par (clave/valor) completo del archivo. 
	Esta no es una tarea sencilla, sin embargo, puede consultar las <a href="#caution"><u>notas de precaución</u></a> sobre este tema.
<hr>

<a name="caution"></a>
<h3> Precaución al añadir o quitar claves de traducción </h3>
La interfaz de Cyclos le permite modificar, agregar o quitar <a href="#key"><u>claves</u></a> 
en el <a href="#language_file"><u>archivo de traducción</u></a>.<br>
La modificación de dichas claves es un lugar seguro, pero la inserción o eliminación de claves pueden ser peligrosas. 
Usted sólo debe hacerlo si posee un sólido entendimiento de cómo funciona el sistema de claves. <br>
Una clave que se elimina, sólo se elimina del archivo de traducción en sí, NO es eliminado de las páginas de la aplicación.<br>
Si la clave permanece en uso en una aplicación cuando usted la ha eliminado, 
la próxima vez que esta página se muestre, el nombre de la clave
aparecerá entre signos de interrogación, lo que no suele ser muy adecuado. 
(por ejemplo: "? about.bla.something.title ???"). <br>
Viceversa: La inserción de una clave en el archivo de traducción puede no ser de ayuda para usted si no es utilizada en ningún lugar. 
Usted puede hacer esto en la <a href="${pagePrefix}content_management"><u>gestión de contenido.</u></a>. <br>

También puede suceder que una clave se pierda después de una actualización (aunque esto no es muy frecuente).
Una actualización normal de Cyclos añadirá la nueva traducción de claves. 
<hr class="help">

<A NAME="application"></A>
<h3> Búsqueda de clave de traducción </h3>
En esta ventana usted puede buscar <a href="#key"><u>traducciones de claves</u></a> en el sistema.<br>
Para ello, introduzca la Clave o su <a href="#value"><u>Valor</u></a> deseado.<br>
Tenga en cuenta que la búsqueda no es sensible a mayúsculas y minúsculas, y que no es necesario introducir el término a buscar completo.<br> 

Como siempre, usted puede no escribir nada en los campos y presionar el botón "Búsqueda"; 
esto le devolverá todas los pares de claves/valores disponibles en el cuadro de resultados. <br>
<br>
Seleccionando la opción "Solo claves sin valor", se mostrarán únicamente las claves con valor nulo, o sea, que no posean una traducción 
(puede ocurrir luego de un cambio de idioma). <br>
<br>
El resultado de la búsqueda aparecerá en la <a href="#application_results"><u>ventana de resultados de búsqueda</u></a>, 
ubicada en la parte inferior. En esta ventana usted tiene la posibilidad de modificar la traducción de las claves encontradas. <br>
<br><br> 
También es posible añadir una nueva traducción de clave, si desea hacerlo, 
haga click en el botón Aceptar correspondiente a la etiqueta "Agregar nueva clave". <br>
Es recomendable que usted lea las <a href="#caution"><u>notas de precaución</u></a> primero.
<br><br>
Nota: Si desea cambiar el idioma de Cyclos, este NO es el lugar. El idioma puede ser cambiado en el
<a href="${pagePrefix}settings#local"><u>Menú: Configuración> Configuración local</u></a> en el bloque de "Internacionalización". <br>
<hr class="help">

<a name="application_results"></a>
<h3> Resultado de búsqueda (traducción de clave/valor) </h3>
Esta ventana muestra los resultados de la búsqueda definida en la <a href="#solicitud"><u>ventana superior</u></a>.<br>
<br>
En esta ventana usted puede: 
<ul>
	<li> Seleccionar una clave y eliminarla, a través del ícono de Eliminación 
	<img border="0" src="${images}/delete.gif" width="16" height="16">&nbsp; correspondiente. </li>
	<li> Editar una clave, a través de su ícono de Modificación	
	<img border="0" src="${images}/edit.gif" width="16" height="16">&nbsp; correspondiente.</li> 
	<li> Si desea Eliminar varias claves juntas, puede seleccionar sus respectivas casillas de verificación y, a continuación, 
	utilizar el botón "Eliminar elemento seleccionado". Tenga en cuenta que la eliminación de claves puede ser una tarea complicada, 
	usted puede leer las <a href="#caution"><u>notas de precaución</u></a> sobre este tema.</li>
</ul>
<hr class="help">

<A NAME="edit_key"></A>
<h3> Modificar traducción de clave </h3>
En esta ventana se puede modificar el valor 
<a href="#value"><u></u></a> de una traducción de <a href="#key"><u>clave</u></a>.<br> 
Primero haga click en "Modificar" y, a continuación, realice los cambios, y haga click en "Aceptar" para confirmarlos.<br>
Puede utilizar varias líneas, pero usualmente esto es ignorado y el resultado es mostrado en una única línea.
<hr class="help">

<A NAME="insert_key"></A>
<h3> Nueva clave de traducción </h3>
Aquí usted puede realizar una nueva traducción de <a href="#key"><u>clave</u></a> y <a href="#valor"><u>valor</u></a>.<br> 
Sólo debe introducir la traducción, y hacer click en el botón "Aceptar".<br> 
Antes de añadir nuevas traducciones de claves, le recomendamos leer las <a href="#caution"><u>notas de precaución</u></a> sobre de este tema.
<hr class="help">

<A NAME="import_file"></A>
<h3> Importar / exportar claves de traducción </h3>
En esta ventana usted puede 
<a href="#import"><u>importar</u></a> o 
<a href="#export"><u>exportar</u></a> 
un <a href="#language_file"><u>archivo de traducción</u></a>. 
Siga los enlaces para mayor información.
<hr class="help">

<a name="import"></a>
<h3> Importar archivo de traducción </h3>
Esta opción le permite realizar la importación de un nuevo <a href="#language_file"><u>archivo de traducción</u></a>.<br> 
Este sería un caso poco común, como por ejemplo, al añadir un nuevo idioma a Cyclos.<br> 
Una actualización normal de Cyclos añadirá automáticamente la nueva traducción de <a href="#key"><u>claves</u></a> (si es que existe).
<br><br> 
En primer lugar, usted deberá decidir sobre "¿Qué hacer?". <br>
Existen cuatro(4) opciones disponibles:
<ul>
	<li> <b> Importar sólo claves nuevas (dejando todas las claves existentes): </b> Esta opción le permite importar sólo las nuevas claves;
	<li> <b> Importar claves nuevas y vacías: </b> Igual a la opción anterior, pero esta también importa las 
	claves vacías (es decir, las claves sin una traducción definida).
	<li> <b> Importar nuevas y modificadas: </b> Esta opción le permite importar las claves nuevas y modificadas,
	o sea las claves a las que se le han modificado algunos de sus principales valores o se
	sobreescribió su valor por defecto. 
	<li> <b> Reemplazar el archivo entero: </b> Esta opción sólo sobreescribe el archivo entero.
	Toda personalización realizada por usted en el pasado, por supuesto, se perderá.
</ul>
Luego, usted deberá "Examinar", localizar el archivo de traducción ("Archivo de propiedades") almacenado localmente en su equipo, 
y hacer click en el botón Aceptar.
<br><br> 
Nota: 
No es necesario que el archivo contenga toda la importación de claves - excepto, por supuesto, si elige "Reemplazar el archivo entero" -. <br>
En todos los demás casos, puede contener cualquier cantidad de claves (en el formato correcto). <br>
Cuando se desea reemplazar el archivo entero, asegúrese de cargar todo el archivo. En caso contrario se arriesga a perder sus claves actuales.

<hr class="help">
<a name="export"></a>
<h3> Exportar archivo de traducción </h3>
Esta opción le permite exportar el presente <a href="#language_file"><u>archivo de traducción</u></a>. <br>
Usted sólo debe utilizar el botón Aceptar correspondiente a la etiqueta "Exportar como archivo de propiedades". 
Si hace click en dicho botón, el navegador se hará cargo y, generalmente, le preguntará si desea guardar el archivo (o abrirlo). <br>
Exportar el archivo de traducción no es común, ya que se utilizaría para poner su propia traducción de Cyclos 
a disposición de otros usuarios de la comunidad.<br>
Si usted ha efectuado su propia traducción, le recomendamos ponerse en contacto con nosotros y hacer su traducción disponible, 
con el posible fin de añadir su traducción a la distribución oficial de Cyclos.
Visite el sitio del proyecto a través de la dirección: <br>
<a href="http://project.cyclos.org"><u>http://project.cyclos.org</u></a>

<hr>
<a name="notifications"></a>
<h2> Notificaciones </h2>
Cyclos le permite administrar el contenido de las siguientes <a href="${pagePrefix}notifications"><u>notificaciones</u></a>:
<hr>

<A NAME="general_notifications"></A>
<h3> Notificaciones generales </h3>
Esta ventana muestra las <a href="${pagePrefix}notifications"><u>notificaciones</u></a> generales del sistema.<br> 
Normalmente estos son los prefijos y sufijos que se añadirán a los correos salientes.<br>
Usted puede cambiar el contenido haciendo click en el ícono de Edición 
<img border="0" src="${images}/edit.gif" width="16" height="16">&nbsp; correspondiente.
<hr class="help">

<A NAME="member_notifications"></A>
<h3> Notificaciones de miembro </h3>
Esta ventana muestra las <a href="${pagePrefix}notifications"><u>notificaciones</u></a> 
enviadas a los miembros por diversos motivos o eventos del sistema.<br>
Usted puede cambiar los contenidos haciendo click en el ícono de Edición 
<img border="0" src="${images}/edit.gif" width="16" height="16">&nbsp; correspondiente.
<hr class="help">

<A NAME="admin_notifications"></A>
<h3> Notificaciones de administradores </h3>
Esta ventana muestra las <a href="${pagePrefix}notifications"><u>notificaciones</u></a> 
enviadas a los administradores por diversos motivos o eventos del sistema.<br>
Usted puede cambiar los contenidos haciendo click en el ícono de Edición
<img border="0" src="${images}/edit.gif" width="16" height="16">&nbsp; correspondiente.
<hr class="help">

<A NAME="edit_notifications"></A>
<h3> Editar notificación </h3>
Esta ventana le permite modificar el contenido de la notificación.<br> 
Para ello, usted debe hacer click en el botón "Modificar" en primer lugar; <br>
Cuando haya finalizado, puede confirmar los cambios haciendo click en el botón "Aceptar". <br>
Un completo editor de texto aparecerá y le permitirá utilizar características de diseño.<br>
Usted también puede utilizar algunos campos, dependiendo de la notificación a modificar.
<%-- Hoe kan ik weten welke?? --%>
<hr class="help">

<A NAME="mail_translation"></A>
<h3> Traducción de correo electrónico </h3>
Esta ventana le permite cambiar el contenido de los mensajes de correo electrónico enviados por diversos motivos o eventos en el sistema.<br> 
Para ello, en primer lugar debe hacer click en el botón "Modificar"; 
Al finalizar, usted puede confirmar los cambios haciendo click en el botón "Aceptar".<br>
<br>
Los siguientes correos electrónicos pueden ser modificados en la actualidad:
<ul>
	<li> <b> Mensaje de invitación: </b> Enviar un correo electrónico cuando se utiliza la opción de
	<a href="${pagePrefix}home#home_invite"><u>invitar a un amigo</u></a> a través del "Menú: Inicio> Invitar".
	
	<li> <b> Mensaje de activación: </b> Enviar un correo electrónico a un miembro cuando se activa.
	Esto ocurre luego de la inscripción, cuando un administrador activa al miembro, moviéndolo de un grupo de 
	"<a href="${pagePrefix}groups#pending_members"><u>miembros pendientes de activación</u></a>" 
	a un grupo de "miembros plenos".
	
	<li> <b> Validación del correo electrónico del registro público: </b> 
	Enviar un correo electrónico a un potencial miembro, cuando el mismo intenta registrarse por primera vez. 
	Para que este correo sea enviado, Cyclos debe poseer sus 
	<a href="${pagePrefix}notifications"><u>notificaciones</u></a> configuradas para ello.
	
	<li> <b> Reseteo de contraseña: </b> Enviar un correo electrónico cuando alguien desea restablecer su contraseña.
	
</ul>
En todas estas definiciones, usted puede utilizar los campos correspondientes para visualizar los textos ingresados.

<%-- hoe?? welke?? --%>
<hr class="help">

<a name="imexport_notifications_mails"></a>
<h3> Importar / exportar traducciones de notificaciones y de correos electrónicos </h3>
Con esta ventana, usted puede importar o exportar las traducciones de los textos correspondientes a los correos electrónicos 
y a las notificaciones del sistema.<br> 
<br>
El archivo se encuentra en formato XML y puede ser leído por cualquier otra implementación o versión de Cyclos. <br>
Si usted lo desea, puede hacer esto para el intercambio de traducciones entre implementaciones Cyclos, o por razones de seguridad. <br>
<br>
Pasos a seguir:<br>
<b>1.</b> Seleccione los <b>Tipos</b> de traducciones (Notificaciones y/o Correo electrónico) que desea Importar/Exportar (<b>Acción</b>).<br>
<br>
<b>2a.</b> Cuando elija la opción de "Importar", deberá especificar el Archivo a importar a través del botón "Examinar".<br> 
<br>
<b>2b.</b> Cuando elija la opción de "Exportar", el navegador se hará cargo, y le preguntará dónde desea guardar el archivo a generar/exportar.<br>
<br>
<b>3.</b> Al finalizar, presione el botón Aceptar para realizar la acción deseada (importación/exportación).
<hr>

<br><br><a name="glossary"></a>
<h2> Glosario de términos </h2>
<br><br> 

<a name="language_file"></a><b>Archivo de traducción</b><br>
Cyclos posee un archivo para cada idioma. <br>
Este archivo contiene todas las "cadenas" de texto escritas en la interfaz de Cyclos
(excepto los grandes bloques de texto, que se encuentran en archivos completos independientes). <br>
Cada archivo de traducción (idioma) siempre posee un nombre de acuerdo a un patrón específico:
"ApplicationResources_xx_XX.properties", donde xx se sustituye por el código del idioma 
y el XX se sustituye por el código de país. <br>
<br>
Ejemplo: "ApplicationResources_en_US.properties" es el archivo de Estados Unidos - Inglés. <br>
Cada archivo de traducción contiene archivos <a href="#key"><u>claves</u></a> y 
<a href="#value"><u>valores</u></a>; estos están separados por el signo de "=", sin espacios.
<hr class='help'>
<br><br>
<a name="key"></a><b>Traducción de clave</b><br>
La traducción de claves se encuentra en las páginas de la aplicación, cuando estas
páginas se muestran en su navegador, las claves son puestas identificadas en el 
<a href="#language_file"><u>archivo de traducción</u></a>, y se sustituyen por los correspondientes 
<a href="#value"><u>valores</u></a> que se encuentran en este archivo.
<hr class='help'>

<br><br>
<a name="value"></a><b>Traducción de Valores</b><br>
La traducción de valores, son las palabras y términos en su propio idioma que usted
verá en las páginas de Cyclos. 
Las páginas originales no contienen estos valores. En su lugar, la traducción de <a href="#key"><u>claves</u></a> 
colocada en las páginas de la aplicación, cuando estas páginas se muestran en el navegador,
las claves son identificadas en el <a href="#language_file"><u>archivo de traducción</u></a> 
y se sustituyen por los valores correspondientes que se encuentran en este archivo. <br>
<br>
Todos los valores de la traducción (traducción de aplicación, notificaciones y mensajes de correo electrónico) pueden
contener "variables". 
Las variables siempre están rodeados por dos signos numeral (#), como: #miembro#, #título#, y #importe#.<br> 
La variable será sustituída por el valor correcto cuando se visualice en Cyclos. 
<hr class='help'>
</span>
<br><br>

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