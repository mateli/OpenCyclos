<div style="page-break-after: always;">
<a name="top"></a>
<br><br>
Las Notificaciones permiten a los usuarios estar informados vía correo electrónico, mensajería interna y/o SMS, 
cuando se producen determinados eventos específicos en el Sistema.<br>
<br>

<span class="member">
<i>¿Dónde las encuentro?</i><br> 
A las Notificaciones se puede acceder a través del "Menú: Preferencias> Notificaciones".
</span>

<span class="admin"> 
<i>¿Dónde las encuentro?</i><br>
A las Notificaciones se puede acceder a través del "Menú: Personal> Notificaciones por correo electrónico".
<br><br>

<i> ¿Cómo hacerlas funcionar? </i><br>
Los administradores tendrán siempre la opción de configurar las notificaciones personales. <br>
Un administrador puede habilitar la función de notificación para los miembros (grupos) en los
<a href="${pagePrefix}groups#manage_group_permissions_member"><u>permisos de miembros</u></a> (en el bloque "Preferencias").<br>
Ajustes adicionales de notificación pueden ser definidos en los 
<a href="${pagePrefix}groups#edit_member_group"><u>ajustes de grupo</u></a> (en el bloque "Notificaciones").<br>
El texto de las notificaciones se puede cambiar en la 
<a href="${pagePrefix}translation"><u>traducción</u></a> a través del "Menú: Traducción> Notificaciones".<br>
</span>
<hr class="help">

<span class="admin">
<A NAME="email_notifications"></A>
<h3>Notificaciones por correo electrónico</h3>
Seleccione los tipos de <a href="#top"><u>notificaciones</u></a> que usted desea recibir, 
seleccionando las opciones deseadas (en cada menú desplegable), 
o marcando las casillas de verificación correspondientes.<br>
<br>
<b>NOTA:</b> Usted puede NO visualizar todas la opciones descriptas a continuación, dependiendo de la configuración y/o permisos establecidos en el sistema.
<br><br>

Cuando finalice, haga click en el botón "Aceptar" para confirmar los cambios.<br>
<ul>
	<li><b> Nuevos miembros registrados: </b> Si un nuevo miembro ingresa a un "grupo inicial", 
	usted puede ser notificado. Pueden ser seleccionados uno o más grupos (iniciales).<br>
	Si el correo electrónico de validación es obligatorio (configuración del grupo),
	usted recibirá la notificación cuando el miembro haya confirmado su registro.

	<li><b> Pagos: </b> Para cada uno de los 
	<a href="${pagePrefix}account_management#transaction_types"><u>tipos de transacción</u></a> disponibles,
	se puede establecer un proceso de notificación. Esto significa que se notifique cuando se lleve a cabo una transferencia (pago) de este tipo.
	
	<li><b> Nuevo pago esperando por autorización: </b> Para cada uno de los tipos de transacción que 
	necesitan ser autorizados por la Administración, usted puede establecer un proceso de notificación.<br> 
	Esto significa que sea notificado cuando exista una nueva transferencia (pago) de este tipo pendiente de autorización.
	
	<li><b> Garantías: </b> Seleccione los tipos de garantías por los cuales quiere ser notificado 
	al momento de crear una nueva <a href="${pagePrefix}guarantees#guarantees_top"><u>garantía</u></a>.
	
	<li><b> Mensajes: </b> Para cada una de las 
	<a href="${pagePrefix}messages#categories"><u>categorías de mensajes</u></a> puede configurarse su correspondiente notificación.
	
	<li><b> Alertas del sistema: </b> Para cada una de las 
	<a href="${pagePrefix}alerts_logs#system_alerts"><u>alertas del sistema</u></a> 
	se puede establecer su correspondiente notificación.
	
	<li><b> Alertas de miembro: </b> Para cada una de las 
	<a href="${pagePrefix}alerts_logs#member_alerts"><u>alertas de miembro</u></a> 
	se puede establecer su correspondiente notificación.
	
	<li><b> Errores de aplicación: </b> Seleccione esta casilla de verificación para que se le informe acerca de los
	<a href="${pagePrefix}alerts_logs#error_log"><u>errores de aplicación</u></a> ocurridos por correo electrónico.
	
	<li><b> Facturas del sistema: </b> Seleccione esta casilla para ser notificado vía correo electrónico
	de la recepción de <a href="${pagePrefix}invoices#notifications_top"><u>Facturas del sistema</u></a>.
</ul>
</span>

<span class="member">
<A NAME="notification_preferences"></A>
<h3>Preferencias de notificación</h3>
En esta página usted puede definir las Notificaciones que desea recibir en el sistema. <br>
Se puede optar por recibirlas vía: 
<ul>
	<li><b>Correo electrónico</b>: A través del e-mail registrado en su perfil.</li>
	<li><b>Mensaje Cyclos</b>: A través del sistema de mensajería interna de Cyclos.</li> 
	<li><b>Mensaje SMS</b>: A través de mensajes SMS (si está habilitado por la administración).</li>
</ul>
Los mensajes internos de la administración NO pueden ser desactivados.<br>
<br>
Como siempre, usted primero debe hacer click en el botón "Modificar", con el fin de realizar las modificaciones deseadas.<br> 
Cuando finalice, haga click en "Aceptar" para confirmar los cambios efectuados.<br>
<br> 
Las siguientes notificaciones están disponibles:

<ul>
	<li> <b> Mensajes de miembros:</b> Se trata de mensajes enviados a través de Cyclos, ya sea de miembros o de administradores. 
	Esta opción posibilita una forma de recibir mensajes de correo electrónico (con la opción de correo electrónico), 
	sin la necesidad de publicar su dirección de correo electrónico en Cyclos.
	
	<li> <b> Mensajes personales de administración</b>.
	
	<li> <b> Mensajes de administración:</b> Estos son mensajes personales o de distribución masiva, enviados por la administración.
	
	<li> <b> Alertas de acceso:</b> Usted recibirá la notificación de que realizó varios intentos para acceder a su cuenta con una contraseña equivocada.
	
	<li> <b> Eventos generales de cuenta: </b> Estos son los eventos generales relacionados con una cuenta.
	
	<li> <b> Eventos de brokering: </b> Notifica sobre cualquier evento de brokering.
		
		Estos son: <br>
		<ul>
			<span class="member">
			
				<li> Nuevo <a href="${pagePrefix}brokering#commission_contract"><u>contrato de comisión</u></a>.
				<li> Contrato de comisión cancelado.<br>
			</span>
				
			<span class="broker">
				
				<li> Vencimiento de brokering.
				<li> Brokering eliminada / broker cambiado.
				<li> Broker de grupo.
				<li> Pago broker en espera, requiere autorización.
				<li> <a href="${pagePrefix}brokering#commission_contract"><u>Contrato de comisión</u></a> aceptado.
				<li> Contrato de comisión negado.
			</span>
		</ul><br>
	
	<li> <b> Eventos de pagos: </b> Eventos relacionados con los pagos en el sistema. 
	Pagos recibidos o eventos relacionados con la autorización y programación de pagos.
	
	<li> <b> Pagos realizados por canales externos: </b> Cuando se efectúa un pago a través de un canal externo (por ejemplo, por SMS).
	
	<li> <b> Eventos de préstamo: </b> Estos son los eventos relacionados a los 
	<a href="${pagePrefix}loans"><u>préstamos</u></a>. Son mensajes acerca de los nuevos préstamos y
		los respectivos vencimientos de sus pagos. 
	
	<li> <b> Alerta de vencimiento de anuncio: </b> Notifica la expiración de un anuncio </u></a>.
	
	<li> <b> Notificaciones de intereses de anuncio: </b> Si está activado, recibirá
		la notificación de que un nuevo anuncio coincide con sus 
		<a href="${pagePrefix}ads_interest"><u>intereses de anuncios</u></a>.
	
	<li> <b> Eventos de facturas: </u> </b> </a> Todo sobre las facturas (recibidas, aceptadas, canceladas).
	
	<li> <b> Referencia recibida:</b> Cuando una referencia es recibida o modificada.
	
	<li> <b> Certificaciones:</b> Cuando una certificación es otorgada por un emisor de garantías.
	
	<li> <b> Garantías: </b> </u></a> Notifica sobre el sistema de garantías en Cyclos.
	
	<li> <b> Obligaciones de pago: </b> </u></a> Notifica sobre el sistema de obligaciones de pago en Cyclos.
	
	<li> <b> Calificación de transacciones: </b> </a> Notificaciones sobre la calidad de las calificaciones recibidas en una transacción específica.
</ul>

<h3> Mensajes SMS </h3>
En este sector usted puede habilitar (o deshabilitar) la operación en el sistema por el canal SMS y 
seleccionar sus preferencias.<br>
<br>
Opciones disponibles:
<ul>
	<li>Habilitar operaciones por canal SMS.</li>
	<li>Autorizar cobro de paquete de mensajes adicionales cuando no se disponga de saldo.</li>
	<li>Autorizar cobro de mensajea cuando no se disponga de saldo.</li>
	<li>Habilitar envío de mensajes de difusión sin cargo desde la administración.</li>
	<li>Habilitar envío de mensajes de difusión con cargo desde la administración.</li>
</ul>

Adicionalmente, se despliega información correspondiente a su "estado" para el canal SMS en el sistema, dónde se indica:
<ul type="circle">
	<li>Cantidad de mensajes SMS sin cargo utilizados y disponibles.</li>
	<li>Cantidad de mensajes SMS adicionales utilizados y disponibles.</li>
	<li>Costo, tamaño y período de validez del paquete de mensajes SMS adicionales.</li>	
</ul>
<br>
El botón "Desactivar SMS", ubicado en la parte inferior izquierda de la ventana, le permite de forma rápida y sencilla, 
desactivar en su totalidad la operación SMS (y sus notificaciones) en el sistema, con un solo click.
<br>
<br>
<hr class="help">
</span>

<span class="member admin">
	<A NAME="receipt_printers"></A>
	<h3>Impresoras de Recibos</h3>
	En algunas ocasiones el usuario de Cyclos, luego de efectuar pagos, desea ser capaz de imprimir los recibos correspondientes.
	Por ejemplo, las empresas que utilizan Cyclos para la recepción de pagos de sus clientes.<br>
	Comúnmente las aplicaciones Web no permiten imprimir recibos en impresoras locales; Sin embargo, esto es posible en Cyclos.<br>
	Los recibos de las transacciones se pueden imprimir desde la página de "Detalles de transacción" y desde la página de POSWeb 
	(inmediatamente después de realizado un pago).
	<br>
	Cuando un pago es parte de un grupo (cuotas) de pagos programados, todos los pagos programados serán impresos en el recibo.
	La página de POSWeb también posee la opción de imprimir una lista de transacciones diarias.<br>
	<br>
	Habilitar impresoras de recibos implica la realización de dos tareas principales.<br>
	En primer lugar, las impresoras deben ser colocadas como disponibles (agregándolas en la 
	<a href="${pagePrefix}preferences#receipt_printer_search"><u>Lista de impresoras de recibos</u></a>). 
	<br>
	Este es normalmente un trabajo que lleva tiempo y debe ser realizado por un usuario con conocimientos técnicos.<br> 
	Cuando esta tarea es realizada, las impresoras pueden ser habilitadas para equipos específicos 
	de los usuarios (miembros u operadores) que deseen utilizarlas.
	<br>
</span>
	
	<span class="member"><A NAME="receipt_printer_search"></A>
		<h3>Impresoras de recibos</h3> 
		Esta ventana muestra una lista de todas sus 
		<a href="${pagePrefix}preferences#receipt_printers"><u>impresoras de recibos</u></a> configuradas.<br>
		<br>
		Usted puede Eliminar y Modificar las impresoras seleccionando los íconos correspondientes. <br>
		Como las impresoras son definidas en la configuración local del equipo, necesitan ser activadas por equipo (computador). <br>
		<br>
		En la opción "Imprimir en este equipo utilizando" usted puede seleccionar una impresora si desea utilizar 
		el equipo en el que está trabajando en la impresión de recibos de transacciones con una impresora local. <br>
		<br>
		Cualquier impresora añadida a la lista (a través de la opción "Insertar nueva impresora") 
		también se encontrará disponible para los operadores, quienes pueden utilizar las impresoras tanto 
		en el canal Web (Preferencias> Impresoras de recibos), como en el canal POSWeb (configuración de impresión en la parte superior izquierda). 
		</span>

	
	<hr class="help">
	<span class="member"> 
	<A NAME="receipt_printer_details"></A>
		<h3>Nueva / Modificar impresora de recibos</h3>
		Los comandos de inicio y fin del documento dependen de la marca/modelo específico de la impresora.
		Estos son útiles, por ejemplo, para cortar el papel o utilizar el timbre después de la impresión.<br>
		Para enviar caracteres específicos ASCII, usted puede utilizar el #NÚMERO variable, por ejemplo, ASCII 100, utilice #100.<br>
		A modo de ejemplo, para las impresoras Epson, para cortar el papel se utilizar el ASCII #27#105. 
		Por más detalles sobre la configuración específica de la impresora consulte la 
		<a href="http://project.cyclos.org/wiki/index.php?title=Receipt_printers" target="_blank">Wiki</a>.
		
		<ul>
			<li><b>Nombre: </b> El nombre de la impresora mostrado en Cyclos.
			<li><b>Nombre de la impresora local: </b>
			El nombre de la impresora local debe ser el nombre exacto de una impresora configurada en el sistema operativo. 
			<li><b>Comando de inicio del documento:</b> 
			Aquí usted puede definir un comando de la impresora local, como nueva línea, tamaño de fuente, etc.
			Estos comandos son específicos para el modelo de la impresora.<br>
			Cualquier texto (caracteres ASCII) que usted ingrese en este campo se mostrará al inicio del recibo.
			De esta manera usted puede poner un "cabezal" de texto extra en la impresión del recibo.
			Si ingresa un texto, asegúrese de poner un comando de nueva línea al final del texto (\n)<br>
			(La impresión de recibos también incluye un encabezado definido por el sistema)
			
			<li><b>Comando de fin del documento:</b> 
			En este campo usted puede ingresar cualquier comando de la impresora local.
			Normalmente este campo es utilizado para definir dónde y cómo se corta el papel después de la impresión.
			<li><b>Mensaje adicional en recibos: </b> 
			Aquí usted puede ingresar cualquier texto.
			Este se mostrará como un "pie de página" adicional (por ejemplo: Gracias por comprar en ...)
					
		</ul> 
		Cuando usted desea utilizar una impresora local de recibos, es necesario instalarla para su sistema operativo.
		Estos son algunos ejemplos de cómo hacer esto en Ubuntu y Windows.<br> 
		<br> 
		<b>Ubuntu</b>
		<ul>
			<li>Java debe estar instalado en el equipo (computador).
			<li>Instale el driver/controlador para la impresora (consulte el sitio Web del fabricante de la impresora).
			<li>En Ubuntu, vaya a: Sistema - Administración - Impresión.
			<li>Seleccione: "Agregar" la impresora, que debe mostrarse; seleccione y haga click "Enviar".
			<li>Ahora se buscarán los controladores, seleccione "Genérico" de la lista (primer opción).
			<li>Seleccione en "Modelos" la opción "Cola de Raw".
			<li>Establezca un nombre corto para la impersora, por ejemplo: Epson.
			<li>Ahora haga click en "Aceptar", la impresora debe aparecer en la lista de impresoras.
			<li>Ahora abra un símbolo del sistema (ventana de DOS) y ejecute los siguientes comandos:<br>
				cupsctl FileDevice=Yes<br>
			    padmin -p Epson -E -v file:/dev/usb/lp0<br>
			    (asegúrese de que el nombre de la impresora es correcto y corresponde a la añadida)
		</ul> 
		
		<b>Windows</b>
		<ul>
			<li>Java debe estar instalado en el equipo (computador).
			<li>Instale el driver/controlador para la impresora (consulte el sitio Web del fabricante de la impresora).
			<li>En el Panel de Control - Impresoras, Agregar una impresora local.
			<li>Seleccione en "Puerto" la impresora recién añadida.
			<li>Seleccione fabricante "Genérico" y modelo "sólo texto".
			<li>Establezca un nombre corto para la impresora, por ejemplo: Epson.
			<li>La impresora debe aparecer en la lista de impresoras.
		</ul> 
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