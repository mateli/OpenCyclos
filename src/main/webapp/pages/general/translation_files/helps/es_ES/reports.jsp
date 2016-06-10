<div style="page-break-after: always;">
<a name="reports_top"></a>
<br><br>

Los reportes le permiten obtener una visión general del estado del sistema. <br>
Usted puede obtener varios tipos de reportes:
<ul>
<li> <b> Reportes de actividad de cada uno de los miembros </b>
<li> <b> Tablas con resultados de un conjunto de miembros </b>
<li> <b> Todos los números/resultados del sistema </b>
</ul>
Dentro de los números/resultados del sistema se incluyen entre otros, el número de transacciones, el volúmen de transacciones, el número
de anuncios y el número de referencias establecidas.

Cyclos también ofrece <a href="${pagePrefix}statistics"><u>informes estadísticos</u></a>.

<br><br>
<i> ¿Dónde los encuentro? </i> <br>
Se puede acceder a los reportes de la siguiente manera:
<ul>
	<li class="admin"> A través del menú principal: "Menú: Reportes".
		Esta opción contiene diversos subtemas:
		<ul>
			<li>Visión global</li>
			<li>Listas de Miembros</li>
			<li>Reportes de miembros</li>
			<li>Análisis estadísticos</li>
		</ul></li>
	<span class="admin">
	<br>
	</span>
	<li class="member"> A sus propios reportes, a través del "Menú: Personal> Reportes"</li>
	<li class="broker"> A sus propios reportes, a través del "Menú: Personal> Reportes"</li>
	<br>
	<li> A los reportes individuales de un miembro en particular, a través de su 
	<a href="${pagePrefix}profiles"><u>Perfil</u></a> ("Ver reportes").</li>
</ul>

<span class="admin">
<br><br> <i> ¿Cómo hacerlos funcionar? </i> <br>
Todos los tipos de reportes tienen sus 
<a href="${pagePrefix}groups#manage_group_permissions_admin_system"><u>permisos</u></a>, 
que deben estar habilitados antes de ser este visible. 
Los permisos se encuentran en el bloque titulado "Reportes".
</span><hr>
<a name="member_activities"></a>
<h3> Mis reportes / Actividades de miembro </h3>
Aquí usted puede ver un pequeño informe sobre los datos actuales de sus cuentas, o de las de otro miembro.<br>
<br>
Puede visualizar también los datos sobre las: 
<ul>
	<li><a href="${pagePrefix}references"><u>Referencias</u></a>
	<li><a href="${pagePrefix}advertisements"><u>Anuncios</u></a> de productos y servicios
	<li><a href="${pagePrefix}invoices"><u>Facturas</u></a> emitidas y recibidas
</ul>
<hr class="help">

<span class="admin">
<a name="current_state"></a>
<h3> Reportes del estado actual </h3>
Esta ventana le permite visualizar un informe sobre el estado actual del sistema.<br>
<a href="#current_result"><u>Haga click aquí</u></a> para obtener una visión general sobre sus posibles resultados.<br>
<br><br> 
Utilice las casillas de verificación disponibles para seleccionar las estadísticas que usted desea visualizar 
y, a continuación, haga click en el botón "Aceptar".
<hr class="help">

<a name="current_result"></a>
<h3> Reportes del estado actual: Resultados </h3>
A continuación en esta página se muestra:
<ul>
	<li> <b> Información de <a href="${pagePrefix}groups"><u>grupos</u></a></b>: 
	Esta sección muestra el número de miembros habilitados; estos son todos los miembros en condiciones de acceder (loguearse) al sistema.
	Se muestra cada uno de los grupos de miembros existentes en el sistema y su respectiva cantidad de miembros pertenecientes.
	<br><br>
	<li> <b> Información de <a href="${pagePrefix}advertisements"><u>anuncios</u></a></b>:
	<ul>
		<li> <b> Número de miembros activos con anuncios: </b> 
		Miembros habilitados para acceder al sistema y que posean uno o varios anuncios publicados.
		<li> <b> Número de anuncios activos: </b> 
		Todos los anuncios válidos/activos al día de hoy.
		<li> <b> Número de anuncios vencidos: </b> 
		Todos los anuncios vencidos, es decir, que su fecha de publicación ha expirado.
		<li> <b> Número de anuncios programados: </b> 
		Todos los anuncios que han sido programados, pero serán activados en una fecha futura.
		<li> <b> Número de anuncios permanentes: </b> 
		Todos los anuncios permanentes, es decir, que no posean un período de publicación.
	</ul>
	<br><br>
	<li> <b> Cuentas del sistema </b>: 
	En esta sección se muestran todas las cuentas del sistema y su correspondiente saldo actual.
	<br><br>
	<li> <b> Cuentas de miembros </b>: 
	En esta sección se muestran todos los tipos de cuentas de miembro y saldos actuales (total del tipo de cuenta) correspondientes.<br> 
	Normalmente sólo existe un tipo de cuenta de miembro en el sistema, y todos los miembros poseen una cuenta de este tipo.
	<br><br>
	<li><b><a href="${pagePrefix}invoices"><u>Facturas</u></a></b>:
	<ul>	
		<li><b>Número de facturas de miembro:</b> Número total de facturas (de entrada y salida) entre miembros.
		<li><b>Sumatoria de facturas de miembro:</b> Importe total de las facturas (de entrada y salida) entre miembros.
		<li><b>Número de facturas entrantes del sistema:</b> Número total de facturas del sistema, recibidas de cuentas de miembros.
		<li><b>Sumatoria de facturas entrantes del sistema:</b> Importe total de las facturas del sistema, recibidas de cuentas de miembros.
		<li><b>Número de facturas salientes del sistema:</b> Número total de facturas del sistema, emitidas a cuentas de miembros.
		<li><b>Sumatoria de facturas salientes del sistema:</b> Importe total de las facturas del sistema, emitidas a cuentas de miembros.
	</ul>
	<br><br>
	<li><b><a href="${pagePrefix}loans"><u>Préstamos</u></a></b>:
	<ul>
		<li><b>Número de préstamos abiertos:</b> Todos los préstamos de miembros que no han sido totalmente reembolsados (pagos).
		<li><b>Cantidad de préstamos que quedan abiertos:</b> Suma de todos pagos restantes de préstamos (abiertos).
	</ul>
	<br><br>
	<li><b><a href="${pagePrefix}references"><u>Referencias</u></a></b>: Para las diferentes categorías de referencias existentes, 
	el cuadro muestra la cantidad recibida de cada una de ellas.
</ul>

<hr>
<a name="member_lists"></a>
<h2> Listas de miembros </h2>
Esta función le permite visualizar un listado de los miembros existentes en el sistema, 
junto con diversos datos (columnados) sobre cada uno de ellos.<br>
Usted puede solicitar listar datos actuales, o elegir un punto (fecha pasada) en el historial. <br>
<br>
Usted puede solicitar datos sobre los siguientes temas:
<ul>
	<li> Broker
	<li> Miembros
	<li> Anuncios
	<li> Referencias
	<li> Cuentas
</ul>

Si usted desea más de una serie de datos en el tiempo, debe acceder al: <br>
"Menú: Reportes> <a href="#member_reports"><u>Reportes de miembros"</u></a>, o al
"Menú: Reportes> <a href="${pagePrefix}statistics">Análisis estadísticos"<u></u></a>.
<br><br> 
Si selecciona varias casillas de verificación en su búsqueda, el sistema puede tomar unos segundos en realizar el cálculo. 
Por favor tenga paciencia. 
<br><br>
En la parte inferior de la página, usted puede <a href="#results"><u>imprimir o descargar</u></a> los resultados obtenidos.
<br><br>
Se encuentran disponibles las siguientes opciones:
<ul>
	<li> <b> Tiempo: </b> 
	Primero se debe seleccionar un punto en el tiempo para el listado. <br>
	Existen dos(2) opciones:
	<ul>
		<li> <b> Hora actual: </b> Por supuesto, se listarán datos actuales.
		<li> <b> Historial: </b> Ofrece un listado histórico. 
		Usted debe especificar la fecha deseada. Si usted elige esta opción puede utilizar el
		selector de fechas, haciendo click en el icono del calendario 
		<img border="0" src="${images}/calendar.gif" width="16" height="16">&nbsp;.
	</ul> <br>
	
	<li> <b> Broker: </b> 
	En esta sección, usted puede especificar el Código o Nombre del <a href="${pagePrefix}brokering"><u>broker</u></a>. 
	El listado se limitará a los miembros correspondientes a dicho broker. <br>
	
	<li> <b> Miembros: </b> En esta sección puede especificar:
	<ul>
		<li> <b> Nombre de miembro: </b> Seleccione esta opción si desea que el nombre del miembro
		se imprima en cada fila del listado. De lo contrario, los datos permanecerán de forma anónima.
		
		<li> <b> Código de broker: </b> Seleccione esta opción si desea que el código de usuario del broker
		se imprima con cada elemento del listado.
		
		<li> <b> Nombre de broker: </b> Seleccione esta opción si desea que el nombre (nombre real) del broker se imprima
		con cada elemento del listado.
		
		<li> <b> Grupos: </b> Utilice este menú desplegable para seleccionar los grupos que desea en el listado. 
		Si selecciona, por ejemplo, "Pequeñas empresas", todos los miembros de este grupo serán incluídos 
		(por supuesto, dependiendo de lo definido en la sección del "Broker").
		</ul>
		<br>
	<li> <b> Anuncios: </b> En esta sección se especifican los datos que se desea filtrar de los 
	<a href="${pagePrefix}advertisements"><u>anuncios</u></a> y sus correspondientes 
	<a href="${pagePrefix}advertisements#ad_status"><u>estados</u></a>:
	<ul>
		<li> <b> Anuncios activos </b>
		<li> <b> Anuncios vencidos </b>
		<li> <b> Anuncios permanentes </b>
		<li> <b> Anuncios programados </b>
	</ul>
	<br>
		
	<li> <b> Referencias: </b> En esta sección se puede especificar si desea visualizar información sobre 
	<a href="${pagePrefix}references"><u>referencias</u></a> "dadas" o "recibidas".
	<br>
	
	<li> <b> Cuentas: </b> En esta sección se puede especificar qué información de las cuentas desea visualizar:
	<ul>
		<li> <b> Créditos minimo de cuenta </b>
		<li> <b> Crédito máximo de cuentas </b>
		<li> <b> Saldo de cuentas </b>
	</ul>
</ul>
<hr>

<a name="member_reports"></a>
<h2> Reportes de miembros </h2>
Esta función le permite imprimir un informe con información correspondiente a las transacciones de los miembros.

	<i>Importante:</i> Tenga en cuenta que esta función puede ser bastante "pesada", 
	debido a que permite listar <b>todas</b> las transacciones de <b>todos</b> los miembros.
	Puede resultar pesada la carga en el servidor, y puede causar que el servidor se bloquee si se solicita una gran cantidad de datos.<br>
	<br><br> 
	Usted debe recorrer y completar el formulario en el orden determinado, de arriba hacia abajo. 
	<br><br>
	En la parte inferior de la página, puede <a href="#results"><u>imprimir o descargar</u></a> los resultados obtenidos.
	<br><br>
	Pueden ser especificados los siguientes parámetros:
	<ul>
		<li> <b> Nombre de miembro:</b> El código de miembro se muestra siempre. 
		Si desea que el nombre del miembro se presente, deberá seleccionarlo aquí.
		
		<li> <b> Código y Nombre de <a href="${pagePrefix}brokering"><u>broker</u></a>:</b> 
		Estos datos no serán mostrados a menos que se seleccionen sus casillas de verificación correspondientes.
		
		<li> <b> Grupos de miembros: </b> Aquí puede especificar que 
		<a href="${pagePrefix}groups"><u>grupo(s) de miembros</u></a> desea visualizar.
		
		<li> <b> Tipo de cuenta: </b> Por lo general, un grupo de miembros posee un
		<a href="${pagePrefix}account_management#accounts"><u>tipo de cuenta</u></a>.
		Sin embargo, es posible que los miembros tengan varios tipos de cuenta.<br> 
		Especifique el el tipo de cuenta que desea visualizar aquí.
		
		<li><b> Filtros de pago:</b> 
		Le permite especificar qué tipo de transacciones desea sean listadas.<br> 
		Los <a href="${pagePrefix}account_management#transaction_types"><u>filtros de pago</u></a> pueden ser especificados en la sección de 
		<a href="${pagePrefix}account_management#account_search"><u>"Administrar cuentas"</u></a>.<br> 
		Para que un filtro de pago pueda ser mostrado en la función de reportes, debe tener seleccionada la opción "Mostrar en reportes".
		
		<li> <b> Fecha (Desde / Hasta):</b>: Permite especificar el intervalo de fechas deseado.
		
		<li><b> Transacciones de débito / crédito: </b> 
		Luego de elegir un filtro de pago, esta opción se hará visible.
		Usted <b>debe</b> seleccionar al menos una de estas casillas de verificación.<br>
		Una transacción de débito es un pago.
		
		<li><b>Incluir miembros sin transacciones:</b> 
		Si esta opción es seleccionada, los miembros sin transacciones serán incluídos en el listado.
		
		<li><b>Nivel de detalle:</b> 
		Permite especificar el nivel de detalle de la información que usted desea visualizar:
		<ul>
			<li> <b> Resúmen: </b> Lista sólo la suma total de todas las transacciones en el período; en una línea por cada miembro.
			<li> <b> Transacciones: </b> 
			Lista todas las transacciones en el período, de cada miembro con el tipo de transacción y el número de transacción (si es utilizado). 
		</ul>
	</ul>

<hr>
<a name="results"></a>
<h3>Resultados de reportes</h3>
Usted puede elegir las acciones a realizar con estos reportes 
(botones ubicados en la parte inferior derecha de las páginas):
<ul>
	<li> <b> Imprimir reporte: </b> Se imprime el informe en pantalla.
	Esta pantalla también incluye un botón de impresión, para enviar el informe directamente a su impresora.
	
	<li> <b> Descargar reporte: </b> Usted tendrá como resultado la descarga de los resultados en un archivo en formato	
	<a href="${pagePrefix}loans#csv"><u>.CSV</u></a>.
</ul>
</span>

<hr>
<!-- Nuevo FER -->
<a name="sms_broadcast"></a>
<h3> Mensajes SMS de difusión </h3>
<span class="admin">
Los mensajes SMS de difusión son aquellos que un Administrador puede enviar en forma masiva a un conjunto de usuarios (grupos seleccionados) 
o a un usuario específico en forma individual, con el fin de difundir una “noticia”.<br> 
<br>
Los destinatarios pueden ser usuarios (o grupos de) 
<a href="${pagePrefix}groups#member_groups"><u>miembros</u></a> o 
<a href="${pagePrefix}groups#broker_groups"><u>brokers</u></a>.<br>
<br>
Un SMS de difusión puede ser enviado a: 
<ul>
	<li>Un usuario</li>
	<li>Un grupo</li>
	<li>Varios grupos</li>
	<li>Todos los grupos</li>
</ul>
El contenido de los SMS de difusión es definido por el Administrador al momento de realizar el envío.
</span>

<span class="broker">
Los mensajes SMS de difusión son aquellos que un Broker puede enviar en forma masiva a todos sus miembros 
o a uno de ellos en forma individual, con el fin de difundir una “noticia”.<br> 
<br>
Un SMS de difusión puede ser enviado a: 
<ul>
	<li>Un miembro</li>
	<li>Todos sus miembros</li>
</ul>
El contenido de los SMS de difusión es definido por el Broker al momento de realizar el envío.
</span>

<hr>
<a name="sms_mailings"></a>
<h3> SMS de difusión </h3>
Esta página permite gestionar sus <a href="#sms_broadcast"><u>mensajes SMS de difusión</u></a> en el sistema.<br>
Aquí usted puede:
<ul>
	<li><b>Buscar mensajes SMS de difusión:</b> Seleccionando el rango de fechas deseado y presionando el botón "Búsqueda".<br>
	Los resultados correspondientes serán mostrados en la ventana inferior.</li>
	<br><br>
	<li><b>Enviar un nuevo SMS de difusión:</b> Presionando el botón "Enviar nuevo".</li>
</ul>

<a name="sms_mailings_results"></a>
<h3>Resultado de búsqueda de SMS de difusión</h3>
<span class="admin">
Esta página muestra los mensajes SMS de difusión existentes en el sistema para el rango de fechas solicitado en la ventana superior.<br>
Para cada mensaje SMS de difusión se despliega la siguiente información:
<ul>
	<li><b>Fecha:</b> Fecha y hora del mensaje SMS de difusión.</li>
	<li><b>Enviado por:</b> Usuario emisor del mensaje.</li>
	<li><b>Tipo:</b> Indica si el mensaje fué Con cargo o Sin cargo.</li> 
	<li><b>Texto:</b> Texto de mensaje.</li>
	<li><b>Destinatarios:</b> Indica el usuario o los grupos de miembros receptores del SMS de difusión.</li>
	<li><b>Mensajes enviados:</b> Cantidad de mensajes SMS de difusión enviados (receptores).</li>
</ul> 
</span>

<span class="broker">
Esta página muestra los mensajes SMS de difusión existentes para el rango de fechas solicitado en la ventana superior.<br>
Para cada mensaje SMS de difusión se despliega la siguiente información:
<ul>
	<li><b>Fecha:</b> Fecha y hora del mensaje SMS de difusión.</li>
	<li><b>Tipo:</b> Indica si el mensaje fué Con cargo o Sin cargo.</li> 
	<li><b>Texto:</b> Texto de mensaje.</li>
	<li><b>Destinatarios:</b> Indica el código del miembro receptor (SMS de difusión individual), 
	o "Grupal broker" en el caso de un mensaje SMS de difusión enviado a todos sus miembros.  
	</li>
	<li><b>Mensajes enviados:</b> Cantidad de mensajes SMS de difusión enviados (receptores).</li>
</ul> 
</span>

<hr>
<a name="send_sms_mailing"></a>
<h3>Enviar nuevo mensaje SMS de difusión</h3>
Aquí usted puede enviar un nuevo <a href="#sms_broadcast"><u>mensaje SMS de difusión</u></a>.<br>
Pasos a seguir:<br>
<span class="admin">
<ol>
	<li>Seleccione mediante el botón de radio <b>Tipo de envío</b>, si el nuevo mensaje SMS de difusión es: <i>Grupal</i> o <i>Individual</i>.</li><br>
		<ul>
			<li>Si es <u>Grupal</u>: Marque en la lista desplegable <b>Grupos</b>, los grupos de usuarios destinatarios del mensaje.</li>
			<br><br>
			<li>Si es <u>Individual</u>: Indique el usuario destinatario, ingresando su <b>Código de miembro</b> o <b>Nombre</b> (campos auto-completables).</li>
		</ul>
		<br>
	<li>Seleccione mediante el botón de radio <b>Tipo</b> si el mensaje es <i>Con cargo</i> o <i>Sin cargo</i>.</li>
	<li>Ingrese el <b>Texto</b> del mensajes SMS de difusión.</li>
</ol>
</span>

<span class="broker">
<ol>
	<li>Seleccione mediante el botón de radio <b>Tipo de envío</b>, si el nuevo mensaje SMS de difusión es: <i>Grupal</i> o <i>Individual</i>.</li><br><br>
		<ul>
			<li>Si es <u>Grupal</u>: Seleccione mediante el botón de radio <b>Tipo</b> si el mensaje es <i>Con cargo</i> o <i>Sin cargo</i>. <br>
			El mensaje SMS será enviado a todos sus miembros en el sistema.</li>
			<br><br>
			<li>Si es <u>Individual</u>: Indique el miembro destinatario, ingresando su <b>Código de miembro</b> o <b>Nombre</b> (campos auto-completables).<br>
			Seleccione también mediante el botón de radio <b>Tipo</b> si el mensaje es <i>Con cargo</i> o <i>Sin cargo</i>.</li>
		</ul>
		<br>
	<li>Ingrese el <b>Texto</b> del mensajes SMS de difusión.</li>
</ol>

</span>
Para enviar el mensaje SMS de difusión presione el botón "Aceptar", ubicado en la parte inferior derecha de la ventana.
<!-- FIN NUEVO FER -->
<hr class="help">

<a name="sms_log_report"></a>
<h3> Mensajes SMS enviados </h3>
<span class="admin">
Esta ventana permite listar la cantidad de SMS enviados por los miembros (o cobrados a ellos) en el sistema.<br>
<br>
Pueden ser utilizados los siguientes filtros:
<ul>
	<li><b>Fecha(Desde/Hasta)</b>: Rango de fechas de los mensajes SMS a buscar.</li>
	<li><b>Grupo:</b> Aquí puede seleccionar el o los grupos de miembros a listar.</li>
	<li><b>Código / Nombre de miembro:</b> Permite seleccionar un miembro en particular para el reporte, indicando su código o nombre.</li>
	<li><b>Estado:</b> Indica el estado de los mensajes SMS a buscar.<br> 
	Las opciones son:
			<ul>
				<li><i>Enviado y descontado de la cuenta.</i></li>
				<li><i>Enviado y descontado a otro miembro.</i></li>
				<li><i>Enviado y descontado del saldo de SMS gratis.</i></li>
				<li><i>Enviado.</i></li>
				<li><i>No enviado debido a error.</i></li>
			</ul>
</ul> 
<hr class="help">

<a name="sms_log_report_search_results"></a>
<h3> Resultado de búsqueda de mensajes SMS enviados </h3>
Esta ventana muestra el listado resultante de la búsqueda de mensajes SMS enviados por miembro, 
de acuerdo a los filtros definidos en la ventana superior.<br>
Se despliega la siguiente información: 
<ul>
	<li><b>Código de miembro</b>.</li>
	<li><b>Nombre</b> del miembro.</li>
	<li><b>SMS enviados:</b> Cantidad de mensaje SMS enviados por el miembro (o cobrados a él).</li>
</ul>
<hr class="help">
</span> 

<a name="sms_log"></a>
<h3> Búsqueda de mensajes SMS </h3>
<span class="admin">
Esta ventana permite buscar todos los SMS enviados o cobrados al miembro en el sistema.<br>
<br>
El registro (log) incluye:
<ul>
	<li>Los mensajes SMS enviados por el miembro.</li>
	<li>Los mensajes SMS cobrados al miembro.</li>
</ul> 
</span> 

<span class="member">
Esta ventana le permite buscar sus mensajes SMS en el sistema.<br>
El registro incluye tanto sus mensajes SMS enviados, como los mensajes SMS cobrados a usted.<br>
<br>
</span>
La búsqueda puede ser filtrada por: 
<ul>
	<li><b>Fecha(Desde/Hasta)</b>: Rango de fechas de los mensajes SMS a buscar.</li>
	<br>
	<li><b>Estado:</b> Indica el estado de los mensajes SMS a buscar.<br> 
	Las opciones son:
			<ul>
				<li><i>Enviado y descontado de la cuenta.</i></li>
				<li><i>Enviado y descontado a otro miembro.</i></li>
				<li><i>Enviado y descontado del saldo de SMS gratis.</i></li>
				<li><i>Enviado.</i></li>
				<li><i>No enviado debido a error.</i></li>
			</ul>
	</li> 
	<br>
	<li><b>Tipo:</b> Indica el tipo de los mensajes SMS a buscar.<br>
	Los tipos existentes son:
			<ul>
			<li><b><i>Difusión sin cargo:</i></b> Mensaje SMS de difusión enviado por la Administración, cuyo costo es cubierto por la Administración.</li> 
			<li><b><i>Difusión con cargo:</i></b> Mensaje SMS de difusión enviado por la Administración, cuyo costo es cubierto por el Miembro.</li>
			<li><b><i>Notificación:</i></b> Mensaje SMS enviado por el sistema, correspondiente a la Notificación de un evento del sistema ocurrido. 
			(Ej. pago recibido, referencia recibida, alertas de acceso). </li>
			<li><b><i>Canal SMS:</i></b> Mensaje SMS enviado por el sistema, correspondiente a una Operación SMS (Motivo).<br>
			<br>
			Motivos disponibles:
			<ul>
			<li>Solicitud de pago</li>
			<li>Pago directo</li>
			<li>Detalle de cuenta</li>
			<li>Ayuda</li>
			<li>General</li>
			<li>Error en [Operación]</li>
			</ul>
			</li>
			</ul>
	</li>
<br>
</ul>
Luego de hacer click en el botón "Búsqueda", los resultados se mostrarán en la parte inferior de la ventana.
<hr class="help">

<a name="sms_log_search_results"></a>
<h3> Resultado de búsqueda de mensajes SMS </h3>
Esta ventana muestra el resultado de la búsqueda de mensajes SMS.<br>
Para cada mensaje SMS se despliega la siguiente información: 
<ul>
	<li><b>Fecha y hora</b> del mensaje SMS.</li>
	<li><b>Tipo</b> del mensaje SMS.</li>
	<li><b>Estado</b> del mensaje SMS.</li>
</ul>
<hr>

<%-- BLOQUE NUEVO FER --%>
<A NAME="name"></A>
<h2>Simulaciones</h2>
La sección "Simulaciones" ubicada en el "Menú: Reportes", en realidad no proporciona reportes/informes, 
en el sentido de que no presentan información extraída de la base de datos del sistema.<br> 
Esta sección le permite a usted realizar simulaciones de determinadas configuraciones en Cyclos, 
en particular sobre el sistema de ratios-D y ratios-A.

Por mayor información usted puede consultar 
<a href="${pagePrefix}account_management#rates"><u>ratios</u></a>, o
<a href="${pagePrefix}account_management#a-rate"><u>ratio-A</u></a> y 
<a href="${pagePrefix}account_management#d-rate"><u>ratio-D</u></a>
<hr>

<A NAME="choose_simulation"></A>
<h3>Elegir Simulación</h3>
Esta ventana le permite seleccionar una simulación.<br>
Las siguientes opciones se encuentran disponibles:
<ul>
	<li><b>Configuración ratio-D</b>: 
	Le permite visualizar la configuración de curva <a href="${pagePrefix}account_management#d-rate"><u>ratio-D</u></a>.
	Esto muestra la relación entre la tasa de conversión y el ratio-D (o fecha).
	
	<li><b>Configuración ratio-A</b>: 
	Le permite visualizar la configuración de curva <a href="${pagePrefix}account_management#a-rate"><u>ratio-A</u></a>.
	Esto muestra la relación entre la tasa de conversión y el ratio-A.
</ul>
<hr class="help">

<span class="admin"> 
<a name="dRate_config_simulation"></a>
<h3>Configuración de simulación Ratio-D</h3>
Esta ventana le permite visualizar la curva resultante de la configuración de los parámetros de 
<a href="${pagePrefix}account_management#d-rate"><u>Ratio-D</u></a>.<br>
Estos parámetros son definidos en la <a href="${pagePrefix}account_management#currency_details"><u>moneda</u></a>.<br>
La curva muestra la relación entre el Ratio-D (fecha) y el porcentaje de tasa resultante del Ratio-D.

<ul>
	<li><b>Parámetros:</b> 
	Estos campos son los parámetros que deben ser establecidos para configurar el Ratio-D.
	Si una configuración actual está disponible, los campos serán precargados con estos valores.
	Sin embargo, usted puede cambiarlos. <br>
	Si en su sistema existen varias monedas con el Ratio-D habilitado,
	primero deberá especificar la moneda deseada en el menú desplegable "Moneda". 
	Si una sola moneda posee el Ratio-D habilitado, este campo no será visible.<br> 
	Para el resto de los parámetros, consulte <a href="${pagePrefix}account_management#currency_details"><u>modificar moneda</u></a>, 
	para obtener una explicación al respecto. <br>
	<br>

	<li><b>Rango:</b><br>
	Le permite a usted configurar los límites de la gráfica a generarse.
	<ul>
		<li><b>Inicio para D =:</b> 
		El punto más a la izquierda en el eje X de la gráfica.<br>
		El valor más alto para D se muestra bien a la izquierda, 
		como D disminuye con el tiempo, y el eje X representa el tiempo.
		
		<li><b>Fin en D =:</b> 
		El punto más a la derecha en el eje X de la gráfica.<br>
		El valor más bajo para D se muestra bien a la derecha, 
		a medida que disminuye D con el tiempo.
	</ul>
</ul>
Luego de hacer click en el botón "Aceptar", la gráfica se mostrará en la 
<a href="#reportsSimulationsDRateConfigGraph"><u>ventana de resultados</u></a>.
<hr class="help">

<A NAME="reportsSimulationsDRateConfigGraph"></A>
<h3>Configuración de curva Ratio-D</h3>
Este gráfico muestra la curva para una configuración de Ratio-D. <br>
El eje X muestra la disminución del <a href="${pagePrefix}account_management#d-rate"><u>Ratio-D</u></a>.
A medida que el Ratio-D disminuye con el tiempo, también se muestra aumento en la fecha. <br>
El eje Y muestra el porcentaje de la tasa resultante de este Ratio-D.
<br><br>
Usted puede posicionar el mouse (ratón) encima de un punto en el gráfico 
para visualizar el valor del dato correspondiente.
<hr class="help">

<A NAME="aRate_config_simulation"></A>
<h3>Configuración de simulación Ratio-A</h3>
Esta ventana le permite visualizar la curva resultante de una configuración
de <a href="${pagePrefix}account_management#a-rate"><u>Ratio-A</u></a>.<br> 
Estos parámetros son establecidos en la <a href="${pagePrefix}account_management#transaction_fee_details"> <u>tasa de transacción</u></a>.<br>
La curva muestra la relación entre el Ratio-A (fecha) y el porcentaje de la tasa resultante de este Ratio-A. 
Es posible visualizar curvas para la configuración de tasas con "Ratio-A" y para tasas con "Ratio-A y Ratio-D mixto".
<ul>

	<li><b>Tipo de transacción a utilizar para los valores por defecto:</b>
	El formulario intenta obtener las tasas de transacción con Ratio-A existentes en su sistema,
	para cargar sus parámetros en los campos del formulario, y para que usted pueda visualizar la configuración 
	de las curvas para las tasas de transacción existentes.
	Si existe más de una tasa de transacción con Ratio-A habilitado en más de un tipo de transacción, 
	el formulario le pedirá que especifique el tipo de transacción de utilizar.
	Este parámetro no es visible si sólo existe un tipo de transacción con tasa(s) con "Ratio-A" habilitado.
	
	<li><b>Tasa de transacción a utilizar para los valores por defecto:</b> 
	Análogo al punto anterior: si más de una tasa de transacción con Ratio-A existe	en este tipo de transferencia, 
	el formulario le pedirá que especifique la tasa deseada.
	De lo contrario, este parámetro no será visible. <br>
	
	<li><b>Parámetros:</b> Estos campos son los parámetros
	que se deben establecer para configurar el Ratio-A. 
	Si una configuración actual se encuentra disponible, los parámetros son precargadas con estos valores. 
	Usted puede sin embargo, realizar cambios. <br>

	Para la edición de parámetros, consulte 
	<a href="${pagePrefix}account_management#transaction_fee_details"><u>modificar moneda</u></a> 
	para obtener una explicación al respecto. 
	Tenga en cuenta que para los tipos de cargos (tasas), usted puede, por supuesto, 
	sólo elegir entre los tipos de cargos que utilizan el Ratio-A. <br>
	<br>
	 
	<li><b>Rango:</b>
	Esto le permite establecer los límites del gráfico a generarse.
	<ul>
		<li><b>Inicio para A =:</b> 
		El punto más a la izquierda en el eje A de la gráfica.<br>
		En el caso de tipos de cargo de "Ratio A/D mixto",
		esto se expresa en el porcentaje del período total de la garantía. 
		Por ejemplo: si el período total de la garantía es de 80 días, una A de 20 que representan 
		el punto en el eje A del 25% (20 es el 25% de 80). <br>
		Si tipo de cargo es "Ratio-A", este campo solo se expresará en días, la unidad normal para un Ratio-A.
		
		<li><b>Fin en A =:</b> 
		El punto más a la derecha en el eje X de la gráfica. Análogo al elemento anterior.
	</ul>
</ul>
Luego de hacer click en el botón "Aceptar", la gráfica se mostrará en la 
<a href="#reportsSimulationsARateConfigGraph"><u>ventana de resultados</u></a>.
<hr class="help">


<A NAME="reportsSimulationsARateConfigGraph"></A><br> 
<A NAME="reportsSimulationsARateConfigCombinedGraph"></A>
<h3>Configuración de curva Ratio-A</h3>
Este gráfico muestra la curva para la configuración ratio-A de una tasa de transacción.<br>
<br>
El eje X muestra el <a href="${pagePrefix}account_management#a-rate"><u>ratio-A</u></a>.<br>
En el caso de un "Ratio-A y Ratio-D mixto", el eje X se expresa como
un porcentaje del período total de la garantía. 
Por ejemplo: si el período total de la garantía es de 80 días, una A de 20 representará el punto sobre el eje X de 25% (20 es el 25% de 80).<br>
<br>
El eje Y muestra el porcentaje de la tasa resultante de este Ratio-A.<br>
Usted puede posicionar el ratón (Mouse) encima de un punto en el gráfico, para visualizar sus valores correspondientes.
<hr class="help">
</span>
<%-- FIN FER BLOQUE NUEVO --%>

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

