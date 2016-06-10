<div style="page-break-after: always;">
<a name="messages_top"></a>
<br><br>

Los Mensajes en Cyclos se pueden utilizar en diferentes ocasiones y por diferentes motivos entre usuarios del sistema.<br>
Existen muchas características disponibles y flexibles para el envío de mensajes. <br>
<br>
Es posible definir diferentes categorías de mensajes, 
a las que podrán tener acceso únicamente los grupos de miembros deseados. <br>
<br>
Los mensajes son enviados a través de un sistema de mensajería interna en Cyclos. <br>
Esto significa que el destinatario visualizará sus mensajes, tan pronto como ingrese nuevamente el sistema. <br>
<br>

Cyclos puede <a href="${pagePrefix}notifications"><u>configurar</u></a> además, que estos mensajes
también sean envíados por mail; sin embargo, esto es responsabilidad del receptor permitirlo o no. <br>
Si quiere estar seguro de que un mensaje será enviado por correo electrónico,
usted puede utilizar la opción de envío de correo electrónico directamente, a través de la interfaz de Cyclos,
que posee un "Enviar correo electrónico" en el perfil de cada uno de los miembros. 

<i>¿Dónde los encuentro?</i><br>
<span class="member"> Puede acceder a los mensajes a través del "Menú: Personal> Mensajes ". </span>
<span class="admin"> Puede acceder a los mensajes a través del "Menú: Mensajes> Mensajes ". </span>
<span class="broker"> Para enviar los mensajes como broker (a todos sus miembros), utilice el
"Menú: Personal> Mensajes", también puede usar el "Menú: Intermediación> Mensaje a los miembros ".
</span>

<br> Otra forma de enviar mensajes es en el <a href="${pagePrefix}profiles"><u>perfil</u></a>
de un miembro y, a continuación, haga click en el botón "Enviar mensaje"
<span class="admin"> en la sección de las acciones posibles a realizar con este miembro</span>.
<span class="admin">
<br><br> <i> ¿Cómo hacerlos funcionar? </i> <br>
Deberá volver a configurar los permisos de los mensajes. Para los administradores, debe configurar los
<a href="${pagePrefix}groups#manage_group_permissions_admin_member"><u> permisos </u></a>
en el bloque "Mensajes", lo que le permite establecer diversos permisos para el envío de
mensajes a los miembros. <br>
Los <a href="${pagePrefix}groups#manage_group_permissions_member"><u> permisos para los miembros </u></a>
relativos a los mensajes, se pueden encontrar en el bloque titulado "Mensajes". <br>
Los brokers poseen un conjunto adicional de <a href="${pagePrefix}groups#manage_group_permissions_broker"><u>
permisos </u></a> en relación con el envío de mensajes a sus miembros, que puede ser
encontrado en el bloque "mensajes personales".
</span>
<hr>

<A NAME="messages_search"></A>
<h3> Mis mensajes </h3>
En esta página usted puede gestionar sus <a href="#messages_top"><u>mensajes</u></a> enviados y recibidos en el sistema.<br>
A través del menú desplegable <b>Bandeja de mensajes</b> seleccione los mensajes que usted desea visualizar: 
<ul>
	<li>Bandeja de entrada 
	<li>Elementos enviados
	<li>Papelera
</ul>

<span class="admin"> 
También puede seleccionar categorías específicas de mensajes, a través del 
menú desplegable <b>Categoría</b>.<br>
Nota: Usted debe poseer permisos sobre las categorías de mensajes, para poder visualizarlas en la lista.
<br>
</span>
<br>
Al hacer click en el botón "Avanzada" (opciones avanzadas), usted podrá buscar mensajes utilizando 
palabras clave u otros filtros, como el miembro remitente o emisor del mensaje. <br>
<br>
Luego de completar los filtros de búsqueda deseados, haga click en el botón "Aceptar".
<br><br>
Para enviar un Nuevo mensaje, debe hacer click en el botón Aceptar correspondiente a la etiqueta "Enviar un nuevo mensaje".
<hr class="help">

<A NAME="messages_search_result"></A>
<h3> Resultado de búsqueda de mensajes </h3>
Esta página muestra los <a href="#messages_top"><u>mensajes</u></a> que cumplen con los criterios de búsqueda 
especificados en la <a href="#messages_search"><u>ventana superior</u></a>.

<br><br> El ícono permite mostrar el estado del mensaje:
<ul>
<li> <img border = "0" src = "${images}/message_unread.gif"
Width = "16" height = "16"> (sin leer)
<li> <img border = "0" src = "${images}/message_read.gif"
Width = "16" height = "16"> (leído)
<li> <img border = "0" src = "${images}/message_replied.gif"
Width = "16" height = "16"> (respondido)
<li> <img border = "0" src = "${images}/message_removed.gif"
Width = "16" height = "16"> (eliminado)
</ul> 

Sobre los mensajes encontrados, usted puede realizar las siguientes acciones:
<ul>
	<li> <b>Abrir</b> un mensaje, seleccionando su Asunto (título).</li>
	<br>
	<li> <b>Aplicar acción</b> a varios mensajes a la vez. Para ello:
		<ul>
			<li> Seleccione los mensajes a los que le aplicará la acción, marcando sus respectivas casillas de verificación; 
			<li> Aplique la medida (acción) deseada, seleccionándola en el menú desplegable "Aplicar acción a los mensajes seleccionados ..."</li>
		</ul>
	<br>
	<li> <b>Eliminar</b> un único mensaje, directamente seleccionando su respectivo ícono de eliminación 
	<img border="0" src="${images}/delete.gif" width="16" height="16">&nbsp;.</li>
</ul>
<hr class='help'>

<A NAME="messages_send"></A>
<h3> Enviar mensaje </h3>
<span class="admin">
Aquí usted puede enviar un <a href="#messages_top"><u>mensaje</u></a> a un miembro específico, 
o a grupos de miembros (a través del menú desplegable <b>Enviar a</b>).<br> 
<br>
Si usted envía el mensaje a través de la función "Menú: Mensajes> Mensajes", deberá especificar el 
<b>Código de miembro</b> y/o <b>Nombre del miembro</b> correspondiente (campos auto-completables).
Si existen definidas, deberá también especificar una <b>Categoría</b> para el mensaje.<br>
<br><br> 
Para desea enviar un mensaje a un grupo, la forma es diferente. 
Primero usted deberá seleccionar uno o más grupos del menú desplegable <b>Grupo(s)</b>. 
Todos los miembros de los grupos seleccionados recibirán el mensaje.<br>
<br> 
Un mensaje a un grupo, podrá ser en formato simple o en texto enriquecido. 
Este último le permite utilizar características especiales de diseño, como tipos de letra, imágenes, etc.
Para ello deberá elegir la opción "Texto enriquecido" a través de su correspondiente botón de radio; 
el editor de texto enriquecido se desplegará, permitiendo el uso de diferentes botones para el diseño (probar y jugar) del mensaje.<br>
También puede utilizar HTML plano, haciendo click en el botón "Fuente" del editor de texto. <br>
<br>
Si desea incluir una imagen, deberá subirla en la sección "Menú: Gestión de contenido> 
<a href="${pagePrefix}content_management#custom_images"><u>Imágenes adaptadas</u></a>).<br> 
Como administrador, NO es posible enviar un mensaje a otro administrador.
</span>
<span class="member">
	Aquí usted puede enviar un <a href="#messages_top"><u>mensaje</u></a> a otro Miembro o a la Administración.
</span>
<span class="broker">
Aquí usted puede enviar un mensaje a:
<ul>
	<li> Un miembro;
	<li> Todos sus miembros registrados;
	<li> A la administración;
</ul>
</span>
<span class="member">
La forma es sencilla.<br> 
<br>
Si usted desea enviarle un mensaje a un <i>"Miembro":</i> 
<ul>
	<li>Especifique el miembro destinatario, ya sea seleccionando su <b>Código de miembro</b> o su <b>Nombre de miembro</b>
	(si completa uno de los dos campos, el restante se completará automáticamente).<br></li>
	<li>Luego ingrese el <b>Asunto</b> y el <b>Cuerpo</b> del mensaje.<br></li>
</ul>
Si usted desea enviarle un mensaje a la <i>"Administración":</i> 
<ul>
	<li>Especifique una <b>Categoría</b> para su mensaje.
</ul>
<br>
</span>

<span class="broker">
	Si usted elige "mis miembros registrados" 
	tendrá la opción de escribirle el mensaje plano o en formato de "texto enriquecido".
	Esta última opción le permitirá usar características especiales de diseño,
	como tipos de letra, imágenes, etc. 
	Para ello deberá elegir la opción "texto enriquecido" en su correspondiente botón de radio; 
	el editor de texto enriquecido se hará visible, permitiendo
	el uso de diferentes botones para el diseño (probar y jugar).
	También puede usar HTML plano, haciendo click en el botón "Fuente" del editor de texto. <br>
</span>
	Luego de haber finalizado el mensaje, haga click en el botón "Aceptar" para enviarlo.<br>
	El mensaje aparecerá en su bandeja de "Elementos enviados".
<hr class='help'>

<span class="broker">
<a name="messages_send_brokered_members"></a>
<h3> Enviar mensaje </h3>
	Esta ventana le permite enviar un mensaje a todos sus miembros. La forma muy sencilla.<br>
	Usted puede escribir el mensaje plano o en formato de "texto enriquecido".
	Esta última opción le permitirá usar características especiales de diseño, como tipos de letra, imágenes, etc. 
	Para ello deberá seleccionar la opción "texto enriquecido" en su correspondiente botón de radio; 
	el editor de texto enriquecido se hará visible, permitiendo la utilización de diferentes botones para el diseño (probar y jugar).
	También puede usar HTML plano, haciendo click en el botón "Fuente" del editor de texto. <br>
	<br>
	Luego de haber finalizado el mensaje, haga click en el botón "Aceptar" para su envío.<br>
	El mensaje aparecerá en su bandeja de "Elementos enviados".
<hr class="help">
</span>

<A NAME="messages_view"></A>
<h3> Detalles de mensaje </h3>
Esta página muestra el <a href="#messages_top"><u>mensaje</u></a> al cual accedió.<br> 
<br>
Usted tiene la opción de eliminar el mensaje y mandarlo a la
"papelera", haciendo click en el botón "Mover a la papelera". <br>
Los mensajes eliminados pueden ser siempre releídos, simplemente accediendo a la "papelera",
con su <a href="#messages_search"><u>búsqueda de mensajes</u></a>. <br>
<br>
Puede responder el mensaje haciendo click en el botón "Responder"
(si el mensaje ha sido enviado por usted, no habrá un botón de respuesta).

<hr>

<a name="categories"></a>
<h2> Categorías de mensajes</h2>
Las categorías de <a href="#messages_top"><u>mensajes</u></a> le permiten una mejor gestión de los mensajes a la administración.
Sólo existen categorías para los mensajes entre los miembros y la administración. <br>
De miembro a miembro no se utilizan las categorías de mensajes.


<span class="admin">
<A NAME="message_categories"></A>
<h3>Categorías de mensajes</h3>
Esta página lista las <a href="#categories"><u>categorías</u></a> de <a href="#messages_top"><u>mensajes</u></a> 
existentes en el sistema.
<br><br>
<ul>
	<li> <img border="0" src="${images}/edit.gif" width="16" height="16">
	Le permite Modificar la categoría.
	<li> <img border="0" src="${images}/delete.gif" width="16" height="16"> 
	Le permite Eliminar la categoría. Esto sólo es posible cuando no ha sido utilizada aún.
	<li> Utilice el botón "Agregar nueva categoría de mensaje" para Crear una nueva categoría.
</ul>
<hr class='help'>
</span>

<span class="admin">
<a name="edit_message_category"></a>
<h3> Modificar categoría de mensaje </h3>
Esta ventana le permite cambiar el nombre de una 
<a href="#categories"><u>categoría</u></a> de <a href="#messages_top"><u>mensajes</u></a>.
<br>
Por favor, elija un nombre descriptivo, ya que será lo que los miembros
visualizarán en su <a href="#messages_send"><u>ventana de envío de mensaje</u></a>. <br>
<br>
Como siempre, deberá hacer click en el botón "Modificar" con el fin de realizar cambios;
cuando haya finalizado, haga click en el botón "Aceptar" para su confirmación.
<hr class="help">
</span>

<span class="admin">
<a name="new_message_category"> </a>
<h3> Agregar nueva categoría de mensaje </h3>
Esta ventana le permite crear una nueva 
<a href="#categories"><u>categoría</u></a> de <a href="#messages_top"><u>mensajes</u></a>.<br>
Sólo deberá introducir un nombre descriptivo, y hacer click en el botón "Aceptar" para su confirmación. <br>
Los miembros visualizarán este nombre en su <a href="#messages_send"><u>ventana de envió de mensaje</u></a>.
<br>
<hr class="help">
</span>


<span class="broker admin">
<a name="sms_mailings"></a>
<h3>SMS de difusión</h3>
En esta ventana usted puede buscar mensajes SMS de difusión en el sistema.<br>
Pueden ser SMS de difusión enviados a usuarios individuales o a grupos de usuarios. <br>
<hr class="help">
</span>

<span class="broker admin">
<a name="sms_mailings_results"></a>
<h3>Resultado de búsqueda de SMS de difusión</h3>
Esta página muestra el resultado de la búsqueda de mensajes SMS de difusión.<br>
La columna "Destinatarios" mostrará el miembro o los grupos de usuarios destinatarios del SMS de difusión.<br>
Las otras columnas se explican por sí mismas.
<hr class="help">
</span>

<span class="broker admin">
<a name="send_sms_mailing"></a>
<h3>Enviar nuevo mensaje SMS de difusión</h3>
En esta ventana usted puede enviar un nuevo SMS de difusión. 
Puede enviar un mensaje SMS de difusión a
</span>
<span class="broker">sus usuarios registrados. </span>
<span class="admin">uno o más grupos. </span>
<span class="broker admin"> 
Usted puede definir si el SMS de difusión es "con costo" o "sin costo".<br>
Un SMS de difusión con costo significa que se le cobrará al usuario en unidades locales, 
o si el usuario posee un crédito en mensajes SMS (gratis o paquetes adicionales), este será utilizado primero.<br>
Un SMS de difusión sin costo significa que no se le cobrará al usuario.<br>
Por lo general, los envíos de SMS de difusión con fines publicitarios serán sin costo, y otros tipos de SMS de difusión de la Organización podrán ser con costo.<br>
<br> 
El usuario puede definir en sus <a href="${pagePrefix}preferences#notifications_preferences"><u>preferencias de notificación</u></a> 
si él/ella desea recibir SMS de difusión con costo y/o sin costo.<br>
Los mensajes SMS de difusión individuales para miembros, siempre serán sin costo (para el usuario).
</span>


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