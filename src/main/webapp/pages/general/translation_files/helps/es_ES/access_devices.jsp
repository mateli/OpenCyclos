<div style="page-break-after: always;">
<a name="access_devices_top"></a>
<br><br>

Un dispositivo de acceso es un medio de pago que puede ser utilizado para realizar pagos por fuera (externamente) de Cyclos. <br>
Puede ser un dispositivo POS (punto de venta) en combinación con una tarjeta de crédito o débito.<br>
Un miembro con una tarjeta activa puede realizar pagos en: un canal webPOS, un hardware POS (lector de tarjetas) 
o un software POS instalado en un ordenador.<br> 
<br>
Un miembro puede tener más de una tarjeta en el sistema, pero sólo una activa.<br>
<br>

<span class="broker admin">
Una tarjeta es basada en un <a href="#list_card_type"><u>tipo de tarjeta</u></a>, que es un modelo (template) de tarjeta.
</span>

<span class="member"> <i>¿Dónde los encuentro?</i><br>
Los dispositivos de acceso se encuentran a través del “Menú: Personal> POS / Tarjetas”.<br>
</span>
<span class="broker"> 
Un broker puede encontrar los dispositivos de acceso de sus miembros a través de las acciones de Brokering en el perfil del miembro. 
</span>

<span class="admin"> <i>¿Dónde los encuentro?</i><br>
Los dispositivos de acceso se encuentran a través del perfil del miembro ó a través del “Menú: Dispositivos de acceso”.<br>
<br>
<i>¿Cómo funcionan?</i><br>
En primer lugar se debe crear el <a href="#edit_card_type"><u>tipo de tarjeta</u></a>, 
para posteriormente ser asignado a uno o más grupos de miembros, o de brokers.<br>
Luego usted puede generar la tarjeta para el miembro a través de las acciones de miembro ubicadas en su perfil.<br>
También es posible generar múltiples tarjetas para un grupo de miembros, a través desde la función de 
<a href="${pagePrefix}user_management#bulk_actions"><u>acciones en masa</u></a>. 
</span>

<a name="card_details"></a>
<h3>Gestión de tarjeta</h3>
Esta ventana muestra el detalle de la tarjeta seleccionada y las posibles acciones a realizar sobre la misma.<br>
<br>
Tarjeta:
<ul>
	<li><b>Código de miembro</b>
	<li><b>Número</b> 
	<li><b>Fecha de activación</b> 
	<li><b>Fecha de creación</b> 
	<li><b>Fecha de vencimiento</b> 
	<li><b>Estado</b>
	<li><b>Código de seguridad</b>
</ul>
<br>
Acciones:
<ul>
	<li><b>Bloquear</b> Esta opción sólo se verá si el estado de la tarjeta es &quot;Activa&quot;. 
	Cuando se lleva acabo esta acción, la tarjeta cambiará al estado "Bloqueada", lo cual 
	significa que no podrá ser utilizada. Se puede volver al estado "Activa" (Desbloquear). 
	<li><b>Desbloquear</b> Esta opción sólo se verá si el estado de la tarjeta es "Bloqueada". 
	Cuando se lleva acabo esta acción, la tarjeta cambiará al estado "Activa". 
	<li><b>Activar</b> Esta opción sólo se verá si el estado de la tarjeta es "Pendiente". 
	Cuando se lleva acabo esta acción, la tarjeta cambiará al estado "Activa".
	<li><b>Cancelar</b> Cuando la tarjeta se encuentra en estado "Cancelada" significa que no podrá
	volver a utilizarse, ni cambiar de estado. 
	<li><b>Cambiar código de seguridad</b> Esta opción sólo se verá si el estado de 
	la tarjeta es "Activa" ó "Bloqueada". Cuando se lleva acabo esta acción, se podrá 
	cambiar el código de seguridad definido.
</ul>
<span class="admin">
<b>Nota:</b> 
Por más información acerca de cómo cambiar el código de seguridad de la tarjeta, 
consulte la ayuda correspondiente para <a href="#edit_card_type"><u>modificar tipo de tarjeta</u></a>.
</span>
<br><br>
<b>Nota:</b>
Para las operaciones que se realizan con la tarjeta se solicitará la <i>Contraseña de transacción</i>, si se encuentra habilitada.<br>
<br>
Se debe tener en cuenta que todas las acciones deben ser habilitadas para los miembros. 
Por lo tanto, pueden no estar disponibles todas las acciones, dependiendo de los permisos establecidos en el sistema. 

<span class="admin">
<a name="card_logs"></a>
<h3>Registro de tarjeta</h3>
Esta ventana muestra una lista con todos los cambios de estado de una tarjeta (existente).<br>
Las estados posibles en el registro son:
<ul>
	<li>Activa
	<li>Bloqueada
	<li>Pendiente
	<li>Cancelada	
</ul>
<hr class="help">
</span>

<hr class="help">
<a name="search_cards"></a>
<span class="broker admin">
<h3>Búsqueda de tarjetas</h3>
En esta ventana usted puede buscar las tarjetas existentes en el sistema, utilizando los siguientes criterios:
<ul>
	<li><b>Vencimiento (Desde/Hasta)</b>: Rango de vencimientos de las tarjetas a buscar.
	<li><b>Código de miembro</b>: Código del miembro titular.
	<li><b>Nombre del miembro</b>: Nombre del miembro titular.
	<li><b>Grupo</b>: Grupo/s de miembros para los cuales realizar la búsqueda de tarjetas.
	<li><b>Número</b>: Número de la tarjeta.
	<li><b>Estado</b>: Estado de la tarjeta.<br>
		Los estados posibles a buscar son:
			<ul>
				<li>Pendiente</li>
				<li>Activa</li>
				<li>Bloqueada</li>
				<li>Cancelada</li>
				<li>Vencida</li>	
			</ul>
	<br>
	<li><b>Tipo de tarjeta</b>: Única selección obligatoria. Si existe un solo tipo de tarjeta definido, está opción no se mostrará.</ul>
<hr class="help">
</span>

<a name="search_card_results"></a>
<span class="member">
<h3> Tarjetas </h3>
Esta página muestra una lista con todas sus tarjetas en el sistema.<br>
<br>
Inicialmente son desplegados los siguientes datos:
<ul>
<li><b>Número</b></li>
<li><b>Fecha de vencimiento</b></li>
<li><b>Estado</b></li>
</ul>
Si usted desea realizar acciones (cambios) sobre una tarjeta o simplemente visualizar su información completa, 
haga click en el ícono <img border="0" src="${images}/view.gif" width="16" height="16"> (lupa) correspondiente.
</span>

<span class="broker admin">
<h3>Resultado de búsqueda</h3>
Esta página muestra una lista con el resultado de la búsqueda de tarjetas en el sistema.<br> 
Usted puede realizar acciones (cambios) sobre una tarjeta o simplemente visualizar su información completa, 
haciendo click en el ícono <img border="0" src="${images}/view.gif" width="16" height="16"> (lupa) correspondiente.  
</span><br>
<span class="admin"> 
<br>
También es posible exportar la lista a un archivo con formato .CSV ó imprimir los resultados.
</span>

<span class="admin">
<h3>Crear tarjetas</h3>
Accediendo a la lista de tarjetas del miembro en su perfil (Gestión de tarjetas), es posible 
crear una nueva tarjeta, presionando el botón "Ingresar nueva tarjeta".<br> 
Dicha tarjeta se creará en estado pendiente.<br>
La nueva tarjeta estará basada en el <a href="#list_card_type"><u>tipo de tarjeta</u></a> asignado para el grupo del miembro
(si el grupo no posee un tipo de tarjeta asociado, no se generará ninguna tarjeta).<br> 
En el caso de ya existir una tarjeta en estado "Pendiente", la misma será "Cancelada" previamente a la creación de la nueva tarjeta.
</span>
<hr class="help">

<span class="admin">
<a name="list_card_type"></a>
<h3>Tipos de tarjeta</h3>
Un Tipo de tarjeta es un modelo/template específico de tarjeta 
(de la misma forma que un tipo de transacción es un modelo de transacción).<br>
<br>
Esta ventana muestra una lista con todos los tipos de tarjetas existentes en el sistema.<br> 
Usted puede:
<ul>
	<li><b>Modificar</b> un tipo de tarjeta, seleccionando el ícono de Modificación	
	<img border="0" src="${images}/edit.gif" width="16" height="16"> correspondiente.<br>
	
	<li><b>Eliminar</b> un tipo de tarjeta, seleccionando el ícono de Eliminación
	<img border="0" src="${images}/delete.gif" width="16" height="16"> correspondiente.<br>
	
	<li><b>Crear</b> un nuevo tipo de tarjeta, presionando el botón Aceptar correspondiente 
	a la acción "Ingresar nuevo tipo de tarjeta".
	</li>  
</ul>
<br>
Nota: Debe tener en cuenta que NO se podrá eliminar un tipo de tarjeta, si existen transacciones realizadas
con tarjetas creadas para dicho Tipo de tarjeta.
<hr class="help">
</span>

<span class="admin">
<a name="edit_card_type"></a>
<h3>Modificar / Nuevo tipo de tarjeta</h3>
En esta página usted puede crear o modificar un tipo de tarjeta.<br> 
<br>
Los campos disponibles son:
<ul>
	<li><b>Nombre</b>: Es el nombre del tipo de tarjeta. Es utilizado únicamente en búsquedas.
	<li><b>Formato de número</b>: Representa el formato del número de la tarjeta.<br>
	Los posibles caractéres a utilizar son:
	<ul>
		<li>&quot;#&quot; Un número
		<li>&quot;-&quot; Un separador
		<li>&quot;/&quot; Un separador
		<li>&quot;\&quot; Un separador
		<li>&quot;.&quot; Un separador
	</ul>
	Ejemplos para el formato del número: &quot;#### #### #### ####&quot; ó
	&quot;####&quot; &quot;##/##&quot;
	<br><br>
	<li><b>Vencimiento</b>: Fecha de expiración, en la cuál la tarjeta quedará sin efecto y no podrá ser utilizada o activada.
	<li><b>Ignorar día en fecha de vencimiento</b>: Si esta opción es seleccionada, la tarjeta vencerá/expirará el último día del mes.
	<li><b>Código de seguridad</b>: El código de seguridad de la tarjeta es una contraseña definida para la tarjeta.<br>
	Funciona de manera similar al PIN, con la diferencia que el PIN en Cyclos no es definido por tarjeta, sino por usuario 
	(el usuario puede utilizar el PIN para varios canales).<br>
	
	Los posibles valores son:
	<ul>
		<li><b>No usado</b>: La tarjeta no utilizará el código de seguridad.
		<li><b>Manual</b>: El miembro y el broker/administrador (con permisos) podrán cambiar el código de seguridad.
		<li><b>Automático</b>: El código de seguridad será generado por el sistema.
	</ul>
	<br>
	<li><b>Número máximo de intentos fallidos del código de seguridad.</b>: Alcanzada esta cantidad de intentos fallidos, la tarjeta será bloqueada. 
	<li><b>Tiempo de bloqueo del código de seguridad</b>: Tiempo durante el cual la tarjeta permanecerá bloqueada, 
	al alcanzar el número máximo de intentos fallidos en el código de seguridad.
	<li><b>Largo del código de seguridad</b>: Es el largo mínimo y máximo del código de seguridad.<br>
</ul>
<br>
Al finalizar, haga click en el botón Aceptar para confirmar sus cambios.
<hr class="help">
</span>


<a name="POS"></a>
<h3>POS (Punto de venta)</h3>
Un dispositivo POS puede ser tanto un hardware (lector de tarjetas), o un software instalado en un ordenador (u otro dispositivo).
Un POS normalmente se encuentra en un local comercial.<br>
Un miembro puede poseer más de un dispositivo POS.<br> 
Normalmente, un POS identifica al usuario (pagador) al pasar su tarjeta por el lector 
(opcionalmente, el POS podría permitir también el ingreso/entrada de forma manual, dependiendo de su configuración).<br> 
El usuario luego deberá validar el pago con tarjeta ingresando su PIN.<br>

<span class="admin">
Para habilitar los pagos mediante POS, el canal POS debe encontrarse habilitado.<br> 
Por mayor información acerca de los POS consulte la ayuda correspondiente a 
<a href="${pagePrefix}settings#channels"><u>configuración de canales</u></a>.
</span>

<hr>
<a name="edit_pos"></a>
<h3>Insertar / Modificar POS</h3>
En esta ventana usted puede establecer la configuración y las acciones relacionadas al dipositivo POS. <br>
<br>
Cada <b>POS</b> posee los siguientes datos: 
<ul>
	<li><b>Identificador de POS</b>: Identifica al dispositivo POS en el sistema, normalmente utilizado el número de serie del POS. 
	Este identificador no puede ser cambiado una vez definido.
	<li><b>Descripción</b>: Descripción del POS (opcional).
	<li><b>Código de miembro</b>: Es el código del miembro asignado al POS.	
	<li><b>Nombre</b>: Es el nombre del miembro asignado al POS. 
	<li><b>Nombre del POS</b>: Es el nombre del dispositivo POS en el sistema. A diferencia del campo "Identificador de POS", puede ser modificado.
	<li><b>Asignado el</b>: Fecha en la que el POS fue asignado a un miembro.
	<li><b>Estado</b>: Indica el estado en el que se encuentra el POS.<br> 
	Puede ser: 
	<ul>
		<li>Sin asignar</li>
		<li>Pendiente</li>
		<li>Activo</li>
		<li>Bloqueado</li>
		<li>PIN bloqueado</li>
		<li>Descartado</li>
	</ul>
	<br>
	<li><b>Permitir hacer pagos</b>: Un POS normalmente es utilizado para recibir pagos de clientes. Si usted desea 
	permitir efectuar pagos por el propietario del POS a otros miembros, debe seleccionar (marcar) esta opción.
	<li><b>Tamaño de página de resultados</b>: 
	Indica el número de resultados a mostrarse por página en el resumen de cuenta. 
	El valor por defecto es "5", lo que significa que se mostrará el saldo del balance y las últimas cinco transacciones. 
	<li><b>Número de copias</b>: Indica la cantidad de recibos de transacción que se desea imprimir.<br>
	Normalmente son "2" los recibos a imprimir, uno para el Comercio (archivo) y otro para el Cliente.	
	<li><b>Máximos pagos agendados</b>: Es la cantidad máxima de pagos programados/agendados permitidos.
</ul>

<h3>Acciones</h3>
Las siguientes acciones pueden ser ejecutadas para un dispositivo POS:<br>
(pueden no estar disponibles todas las acciones, dependiendo de los permisos establecidos en el sistema)
<ul>
	<li><b>Asignar</b>: POS creado pero no asignado a un miembro. Puede ser asignado seleccionando esta opción.
	
	<li><b>Quitar asignación</b>: POS asignado a un miembro. Puede ser desasignado seleccionando esta opción.
	
	<li><b>Cambiar PIN</b>: Cambiar PIN del POS.
	El PIN es utilizado para inicializar el POS y cualquier acción realizada por el POS, 
	como recuperar el saldo (balance) de cuenta, su histórico y realizar pagos.
	Recibir pagos de clientes no requiere el PIN del POS (es el cliente quien proporciona el PIN).
	
	<li><b>Bloquear</b>: POS no activo. Puede ser bloqueado seleccionando esta opción.
	
	<li><b>Desbloquear</b>: POS bloqueado. Puede ser desbloqueado seleccionando esta opción.
	<span class="admin">
	
	<li><b>Descartar</b>: 
	Cuando un POS es descartado, no podrá volver a ser utilizado.<br>
	No es posible crear un nuevo POS con el mismo "Identificador de POS".
	Normalmente se descarta un POS cuando físicamente el POS no se utilizará más (rotura, pérdida, etc.).
	</span>
</ul>
<hr class="help">
</span>

<span class="admin">
<a name="pos_logs"></a>
<h3>Registro de POS</h3>
En esta ventana muestra un listado con los cambios de estado del POS seleccionado.<br>
Las acciones que generan un registro para el POS son:
<ul>
	<li>Asignar POS 
	<li>Desasignar POS
	<li>Bloquear POS
	<li>Desbloquear POS
	<li>Descartar POS
</ul>
<hr class="help">
</span>

<span class="broker admin">
<a name="search_pos"></a>
<h3>Buscar POS</h3>
Esta página le permite buscar los dispositivos POS existentes en el sistema.<br>
Usted puede utilizar los siguientes filtros:
<ul>
	<li><b>Estado:</b> Indica el estado en el que se encuentra el POS.<br>
		Los estados posibles son:
		<ul>
			<li> Sin asignar</li>
			<li> Pendiente</li>
			<li> Activo</li>
			<li> Bloqueado</li>
			<li> Pin bloqueado</li>
			<li> Descartado</li>
		</ul>
		<br>
	<li><b>Identificador de POS:</b> Identifica al dispositivo POS en el sistema, normalmente utilizado el número de serie del POS.
	<li><b>Código de miembro:</b> Código del miembro asignado al POS.
	<li><b>Nombre de miembro:</b> Nombre del miembro asignado al POS.
</ul>
<br>
Para crear un nuevo POS, haga click en el botón "Ingresar nuevo POS", ubicado en la parte inferior derecha de la ventana.
<br>
<hr class="help">
</span>

<span class="broker admin"> <a name="search_pos_results"></a>
<h3>Resultado de búsqueda</h3>
Muestra una lista con los resultados de la búsqueda:
<ul>
	<li> Haga click en el ícono <img border="0" src="${images}/edit.gif" width="16" height="16"> correspondiente para modificar el POS.
	<li> Haga click en el ícono <img border="0" src="${images}/delete.gif" width="16" height="16"> correspondiente para eliminar el POS.<br>
</ul>	
Nota: Sólo se podrá eliminar un POS si nunca estuvo asignado a un miembro. 

<hr class="help">
</span>

<span class="member"> 
<a name="member_pos"></a>
<h3>POS de miembro</h3>
Esta ventana muestra todos los POS asignados a usted en el sistema.<br>
<br>
Los datos desplegados son:
<ul>
	<li><b>Identificador de POS</b>: Identifica al dispositivo POS en el sistema.
	<li><b>Nombre del POS</b>: Nombre del dispositivo POS.
	<li><b>Estado</b>: Indica el estado en el que se encuentra el POS. 
	<li><b>Asignado el</b>: Fecha y hora en la que el POS fue asignado.
</ul>
<br>
Si desea Visualizar o Modificar un POS, haga click en el ícono 
<img border="0" src="${images}/view.gif" width="16" height="16"> correspondiente.

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
</div>