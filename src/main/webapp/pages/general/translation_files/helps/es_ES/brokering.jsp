<div style="page-break-after: always;">
<a name="brokering_top"></a>
<br><br>
Un Broker (agente de comercialización o corredor) es un tipo de miembro del sistema, el cual puede registrar nuevos miembros 
y realizar distintas acciones en su nombre.<br> 
Poseen cierto nivel de acceso y control sobre estos miembros (dependiendo de la configuración del grupo Broker). <br>
Un broker puede ser autorizado a realizar tareas de administración en el sistema; esto está pensado para
miembros con problemas de acceso, o que se sientan inseguros con los ordenadores (computadores) 
y con la realización de determinadas tareas en Cyclos por sí solos.<br>
<br>
Un broker puede recibir una <a href="#commission"><u>comisión</u></a> por el registro de nuevos miembros y/o 
por el volúmen de transacciones realizadas por sus miembros. 
La idea es que éste sea un incentivo para que los brokers acerquen/atraigan nuevos miembros al sistema.<br>

<span class="broker admin"> 
El broker también puede ser utilizado como un 
agente de intermediación de préstamos en los sistemas de financiación de las microempresas.<br> 
El agente intermediario (broker) también puede registrar
nuevos miembros y recuperar información acerca del estado de sus préstamos.<br>
Los sistemas comunitarios como LETS, pueden utilizar la función del broker como 
administradores de cuentas, que pueden ayudar a otros miembros que no poseen acceso
o la capacidad de uso de Internet/Móvil.<br> 
Diferentes tipos de grupos de brokers pueden existir en el mismo sistema. 
</span> 
 
Es posible para los brokers establecer un <a href="#commission_contract"><u>contrato de comisión</u></a>
con cada miembro en forma individual.
<br><br>
<span class="broker"> <i> ¿Dónde los encuentro? </i> <br> <%--OK--%>
Puede acceder a todas las funciones de brokering a través del "Menú: Brokering". <br>
Para la intermediación (brokering) de información y funciones relacionadas con un miembro en particular, 
puede acceder directamente en las 
<a href="${pagePrefix}profiles#actions_for_member_by_broker"><u>acciones del broker para miembro</u></a>, ubicadas debajo del 
<a href="${pagePrefix}profiles#member_profile"><u>perfil</u></a> de dicho miembro. 
</span>
<br><br>
<span class="admin"> <i> ¿Dónde los encuentro? </i> <br> <%--OK--%>
Los administradores no poseen funciones de brokering, sino que pueden tener un cierto nivel
de acceso a las funciones de intermediación del agente (broker) y a los miembros que se han
asignado a un broker. 
Esto puede hacerse en las 
<a href="${pagePrefix}profiles#actions_for_member_by_admin"><u>acciones</u></a>, ubicadas debajo del 
<a href="${pagePrefix}profiles#member_profile"><u>perfil</u></a> del miembro o broker. <br>
Como se explica, la función de brokering puede ser utilizada para muchos propósitos. 
Por tanto, las búsquedas y funciones relacionadas con los brokers pueden aparecer en otros módulos. 
<br><br> 
<i> ¿Cómo hacerlos funcionar? </i> <br> <%--OK--%>
A fin de activar la intermediación tendrá que establecer los 
<a href="${pagePrefix}groups#manage_group_permissions_broker"><u>permisos del broker</u></a> 
en el bloque "Brokering" y las 
<a href="${pagePrefix}groups#edit_broker_group"><u>opciones de grupo de brokers</u></a> 
en el bloque "Brokering". <br>

Las comisiones del Broker se pueden activar en el 
<a href="${pagePrefix}account_management#transaction_type_details"><u>tipo de transacción</u></a> 
en la configuración de las 
<a href="${pagePrefix}brokering#broker_commission_list"><u>comisiones por brokering</u></a>. 
<br>
También deberá definir los permisos del broker para este (en virtud del bloque de "Brokering"). <br>
Para que un miembro vea las comisiones, también deberá establecer sus 
<a href="${pagePrefix}groups#manage_group_permissions_member"><u>permisos</u></a> (bloque "Comisiones").
<br><br>
Note que no tiene sentido dar a los brokers o a los miembros el acceso a las comisiones, permitiendo
la definición de las comisiones como administrador en la configuración de los tipos de transacciones.
En tal caso, los brokers y los miembros se enfrentarán con campos vacías, tendrán
acceso a los contratos de brokering y a las comisiones, pero sin poder cambiar su definición o adaptación.<br>
Asegúrese de que no sólo pueda crear la comisión del broker, sino también activarla.
</span>

<span class="broker admin">
<A NAME="broker_search_member"></A> <%--OK--%>
<hr>
<h3> Miembros registrados (del broker) </h3>
Esta función muestra una lista con los miembros del broker. <br>
Usted puede buscar por:
	<ul>
		<li> <b> Código de miembro / Nombre: </b> Búsqueda de miembro de forma individual.
		<li> <b> Grupo: </b> Búsqueda por grupo miembros.
		<li> <b> Estado: </b>
		<ul>
			<li> <b> Activo: </b> Miembros que están activos en el sistema (en un grupo "activo")
			<li> <b> Comisión finalizada: </b> Miembros para los que la <a href="#commission"><u>comisión</u></a> ha sido recibida y finalizada.
			<li> <b> Esperando activación: </b> Miembros que se han registrado, pero no están
			activos aún (deben ser activados por un broker o un administrador).
		</ul>
	</ul>
<hr class="help">
</span>

<span class="broker">
<A NAME="broker_search_member_result"></A><%--OK--%>
<h3> Resultado de búsqueda de miembros (del broker) </h3>
Esta ventana muestra la lista de los miembros que se han registrado con usted como su Broker.
<br>
En dicha lista usted puede seleccionar el Código o Nombre de un miembro y acceder a su 
<a href="${pagePrefix}profiles"><u>perfil</u></a>.
</span>

<span class="admin">
<A NAME="admin_brokering_list"></A> <%--OK--%>
<h3> Lista de miembros (del broker) </h3>
Esta página muestra una lista de todos los miembros relacionados con un broker. 
Usted puede seleccionar el nombre del miembro para acceder a su perfil. <br>
Al hacer click en el ícono Borrar <img border="0" src="${images}/delete.gif" width="16" height="16">, 
eliminará la asignación del broker con el miembro (se le pedirá confirmación).
<hr class="help">
</span>


<span class="admin">
<A NAME="add_member_to_broker"></A><%--OK--%>
<h3> Añadir miembro a broker </h3>
En esta página usted puede añadir un usuario (miembro) a la lista de intermediación del broker. 
Deberá completar la información requerida, como código de usuario y campos auto-completables al escribir. 
Si el miembro está relacionado a otro broker y no existe todavía una comisión <a href="#commission"><u></u></a>
activa, puede ser suspendida seleccionando "Suspender la comisión". <br>
Tenga en cuenta que puede reasignar todo un conjunto de miembros a otro agente a través de las
<a href="${pagePrefix}user_management#bulk_actions"><u> acciones en masa</u></a>.
<hr class="help">
</span>

<span class="admin">
<A NAME="change_broker"></A><%--OK--%>
<h3> Asignar broker </h3>
En esta página se puede asignar un miembro a un broker. 
El campo "actual broker" mostrará el actual broker del miembro, si corresponde. 
Este campo puede estar vacío (ninguno), porque un miembro no necesariamente debe estar relacionado con
un broker. 
Si desea asignar un miembro a un broker, puede escribir el nuevo código de usuario o el nombre 
completo del broker (campos auto-completables). 
Cuando el agente se muestre (puede ser cambiado), usted deberá escribir un comentario 
y pulsar el botón Aceptar a continuación. <br>

Si desea detener cualquier <a href="#commission"><u> comisión </u></a>
que aún esté activa para el actual broker, puede seleccionar el campo "Comisión suspendida". 
De lo contrario, el nuevo broker heredará la configuración de la comisión antigua.
Esto significa que la comisión se cobrará desde el día en que el miembro es asignado
al nuevo broker, hasta la fecha final de la comisión, configurada en las propiedades de la comisión.
<hr class="help">
</span>

<span class="admin">
<a name="remove_member_to_broker"></a><%--OK--%>
<h3> Eliminar miembro </h3>
El título de la ventana puede resultar alarmante, pero en ella lo único 
que ocurrirá si usted hace click en el botón Aceptar, es que el miembro ya no estará asignado a su broker.<br>
<br>
Antes de hacer click en el botón "Aceptar", puede agregar un comentario sobre la razón de la modificación (desasignación). <br>
Tenga en cuenta que usted puede asignar todo un conjunto de miembros a otro broker, a través de las 
<a href="${pagePrefix}user_management#bulk_actions"><u>acciones en masa</u></a> del sistema.
<hr class="help">
</span>

<a name="brokering_details"></a><%-- deze window heb ik nog steeds niet kunnen vinden --%>
<%-- <h3> Título de ventana </h3>  Explicación  --%>
<hr class="help">

<a name="commission"></a><%--OK--%>
<h2> Comisión de broker </h2>
Por su labor, un broker puede recibir una comisión, lo que significa un pequeño pago relacionado a la actividad de sus miembros en el sistema.<br>
Cuando un agente registra un nuevo miembro, por lo general éste se convierte en miembro del broker. <br>

El broker puede obtener una comisión por cada operación de este nuevo miembro. <br>
El objetivo es estimular a los intermediarios (brokers) a que atraigan y capten nuevos miembros al sistema.<br>
Cuando un broker se traslada a otro grupo, todas las comisiones de los contratos en ejecución 
se cerrarán.

</div>
<div style="page-break-after: always;">

<span class="admin broker">
<A NAME="broker_commission_list"></A>
<h3> Lista de comisiones de broker </h3>
Esta ventana muestra una lista con todas las 
<a href="#commission"><u>comisiones</u></a> configuradas para los brokers (activas o inactivas).
<ul>
	<li> Haga click en el ícono de Modificación <img border="0" src="${images}/edit.gif" width="16" height="16"> 
	correspondiente, para modificar el brokering.
	<li> Haga click en el ícono de Eliminación <img border="0" src="${images}/delete.gif" width="16" height="16"> 
	correspondiente, para eliminar el brokering.
</ul>
Para Crear una nueva comisión, haga click en el botón "Agregar una nueva comisión de broker".
<hr class="help">
</span>

<span class="admin broker">
<A NAME="broker_commission_details"></A><%--OK--%>
<h3> Detalles de comisión de broker </h3>
Al igual que en una transacción de pago, la <a href="#commission"><u> comisión</u></a>
de un broker se acredita/debita cuando el miembro realiza un transacción y en base 
a los criterios de comisión establecidos. <br>
Como siempre, use botón "Modificar" ubicado debajo para poder realizar cambios;
haga click en "Aceptar" para confirmar los cambios.
<br><br>
La comisión tiene las siguiente configuración:
<ul>
	<li> <b> Tipo de transacción: </b> Este es el tipo de transacción a la que corresponde la comisión estipulada.
	<li> <b> Nombre: </b> Nombre de la comisión al broker.
	<li> <b> Descripción: </b> Descripción de la comisión.
	<li> <b> Activado: </b> La comisión está activa. Asegúrese de verificar este campo, de lo contrario no funcionará.
	<li> <b> Se cargará: </b> Aquí puede definir que se cargará. 
	Esto puede hacerlo el ordenante, el receptor o una cuenta del sistema.
	<li> <b> Recibirán: </b> Aquí puede definir quien recibirá el pago. 
	Esto puede hacerlo el agente del ordenante o el broker del receptor.
	<li> <b> Permitir cualquier cuenta: </b>
	Si esto se comprueba, no habrá limitaciones en las cuentas a las que la transacción
	será aplicada. Si carga la comisión en una cuenta diferente a la
	cuenta a la que pertenece el tipo de transacción, es necesario comprobar esta opción.
	Por ejemplo, si desea tipos de transacciones en otras monedas para la comisión.
	<li><b> Creación de tipo de transacción: </b>
	Aquí puede definir que tipo de transacción se generará. 
	Es común crear una operación específica para este tipo, de manera de poder filtrar por el mismo 
	La base de datos por defecto viene con una operación de pago y un tipo de transacción para la transacción de pago.
	<li> <b> Monto: </b>
	Aquí puede definir el importe de la comisión. El broker recibirá
	la comisión cada vez que un miembro realiza un pago (y si la condición de
	aplicabilidad de la comisión se cumple). <br>
	En caso de los <a href="#commission_contract"><u> contratos</u></a>
	del broker, los valores de comisión pueden ser modificados por el broker.
	En este caso, el importe se utilizará como valor por defecto para la comisión del contrato. 
	El broker puede cambiar estos valores en la creación del "contrato de broker" para cada uno de sus
	miembros. Véase también el siguiente punto.
	<li> <b> Máxima cantidad fija y %: </b>
	Estas opciones están relacionadas con el contrato del broker y sólo se muestran si en la
	opción "Se cargará" se especifica un miembro (no una cuenta del sistema). 
	Este valor definirá la cantidad máxima que puede estipular un broker en un su
	contrato. (un broker debe tener los permisos para la gestión de contratos)
</ul>

<li> <b> Condiciones de aplicabilidad </b><br>
	Aquí puede definir en qué condiciones la tarifa (comisión) se aplicará. 
	Sólo si las condiciones coinciden, la comisión será aplicada. Las condiciones se pueden combinar.
	<ul>
	<li> <b> Monto mayor o igual a: </b><br>
	Sólo se cobrará si el monto de la operación es mayor o igual al
	monto determinado.
	<li> <b> Monto menor o igual a: </b><br>
	Sólo se cobrará si el monto de la operación es inferior o igual al
	monto determinado.
	<li> <b> De todos los grupos: </b><br>
	Si está marcada, la comisión se aplicará a los miembros de cualquier grupo que haya realizado un pago
	del tipo de transacción.
	Si desea aplicar la tasa sólo para grupos específicos, entonces
	tendrá que desactivar esta casilla, entonces aparecerá un menú desplegable donde deberá seleccionar 
	los grupos específicos deseados.
	
	<li> <b> Para todos los grupos </b> <br>
	Si está marcada, la comisión se aplicará a los miembros de cualquier grupo que haya recibido un pago
	del tipo de transacción. 
	Si desea aplicar la tasa sólo para grupos específicos, entonces
	tendrá que desactivar esta casilla, entonces aparecerá un menú desplegable donde deberá seleccionar 
	los grupos específicos deseados.
	<li> <b> Todos los grupos de brokers </b> <br>
	Si está marcada, la comisión se aplicará a los brokers de cualquier grupo en cuestión.
	Si desea aplicar la comisión únicamente para los brokers de grupos específicos,
	tendrá que desactivar esta casilla, entonces aparecerá un menú desplegable donde deberá seleccionar 
	los grupos de brokers específicos deseados.
	<li> <b> Cuando se paga comisión: </b>
	
	Aquí puede definir el momento la comisión se cobrará. Esto puede ser:
	<ul>
		<li> <b> Siempre: </b>
		Las comisiones del broker sobre los pagos, se cobrarán siempre. 
		(la comisión puede ser detenida de la página: Perfil de usuario - Configuración del broker).
		<li> <b> Operaciones: </b>
		Las comisiones del broker sobre los pagos se detendrá después de un número determinado de
		transacciones. Este número se puede establecer en el campo que se mostrará junto al 
		menú desplegable, si elige esta opción.
		<li> <b> Días: </b>
		Las comisiones del broker sobre los pagos se detendrá después de un número determinado de días. 
		El número correspondiente se puede establecer en el campo que aparecerá antes de la caja de selección, 
		si elige esta opción.
	</ul>
</ul>
<hr class="help">
</span>

<span class="admin broker">
<A NAME="commission_settings"></A>
<h3> Configuración de comisiones </h3>
Esta ventana permite comprobar la configuración de las <a href="#commission"><u>comisiones</u></a> por defecto en el sistema.<br>
Las comisiones configuradas se aplicarán a todos los nuevos miembros registrados, sin embargo,
serán sobreescritas/reemplazadas por los cambios que el broker realice (si posee permisos) en su configuración, o por los 
<a href="#commission_contract"><u>contratos individuales de comisión</u></a> definidos entre brokers y miembros.<br>
<br> 
Si la administración no define la configuración de ningún tipo de comisión por defecto, esta ventana permanecerá vacía.<br> 
Si usted ha habilitado la gestión de comisiones para miembros y brokers, deberá también definir una comisión por defecto.<br>
Las comisiones por defecto son definidas en los tipos de transacción (pagos) correspondientes, sobre los cuales se aplicará la comisión del broker 
(Menú: Cuentas> Administrar cuentas).
 </span>
<br><br>

<span class="broker">
En esta ventana se puede definir la configuración por defecto de las <a href="#commission"><u>comisiones</u></a>.<br>
Esta configuración se aplicará a todos SUS nuevos miembros registrados.<br> 
Siempre y cuando no haya establecido la comisión por defecto en esta ventana, la administración puede cambiar la configuración por defecto 
de la comisión. Tan pronto como usted haya establecido la configuración de sus comisiones en esta pantalla, 
se sobrescribirá/anulará la configuración de la administración.
<br> 
Tenga en cuenta que cualquier <a href="#commission_contract"><u>contrato</u></a> individual
con sus miembros, tendrá prioridad sobre la configuración establecida aquí.
<br><br>
Si la administración no define la configuración de ningún tipo de comisión, esta ventana estará vacía.
En ese caso, usted deberá notificar acerca de ello.
<br><br>
Usted puede cambiar su configuración haciendo click en el botón "Modificar".
Luego, presione el botón "Aceptar" para confirmar los cambios. 
Esto sólo será visible en el caso de poseer los permisos para cambiar la configuración predeterminada.
<br>
El estado no puede ser cambiado.<br>
El estado puede poseer los siguientes valores: <br>
<ul>
	<li> <b> Activa: </b>
	Esto significa que todas las comisiones configuradas para el broker serán cargadas si las
	condiciones se cumplen.
	<li> <b> Inactiva: </b>
	Esto significa que no se podrán cargar comisiones.<br> 
	Si este es el caso, significa que está configurada para todo el sistema, 
	y que fué establecida por un administrador.
	<li> <b> Suspendida: </b>
	El broker tiene temporalmente las comisiones suspendidas.
</ul>
</span>

<span class="admin"> 
<br><br>
Un administrador tiene la opción de Suspender (parar temporalmente) todas las comisiones activas. 
Incluso cuando el broker pueda añadir nuevos <a href="#commission_contract"><u>contratos de comisión</u></a>, 
estos entrarán directamente en el estado de suspendido.
</span>
<hr class="help">

<a name="commission_contract"></a>
<h2> Contrato de comisión </h2>
Un contrato de comisión es un acuerdo entre un broker y un miembro.<br>
Normalmente, el broker recibe un porcentaje o una cantidad fija por cada pago del miembro en el sistema.<br> 
Esta <a href="#commission"><u>comisión</u></a> puede ser paga por el miembro pagador, 
por el miembro receptor, o bien por la organización (desde una cuenta del sistema).<br>
<br>
Dependiendo de la configuración, los brokers son libres de crear un contrato individual con
cada uno de sus miembros.<br> 
El miembro tiene que acordar este contrato antes de que se apliquen comisiones.<br>
El miembro puede aceptar o rechazar una nueva propuesta de contrato con el broker.<br>
El miembro puede ver los detalles de la comisión, y los dos, miembro y broker, recibirán
una notificación de que el contrato de comisión ha cambiado.
<br><br>
<span class="broker">
Dependiendo de la configuración del sistema, el broker podrá definir una comisión por cada miembro.
Sólo puede existir una comisión por cada período activo (es posible tener más
comisiones si el período de comisión no se solapa). <br>
<br> Nota: Cuando un broker se traslada a cualquier otro grupo, todos los contratos y las
comisiones se cerrarán.
<br><br>

<i> Dónde los encuentro? </i> <br>
Los contratos de comisión se pueden encontrar en el "Menú: Brokering> Contratos de comisión".
Puede añadir un nuevo contrato de comisión de un miembro a través del Perfil del miembro, en la ventana de
"acciones de broker con miembro", presionando el botón "Contratos de comisión". 
En la parte inferior de la página habrá un botón para la creación de un nuevo contrato.

</span>
<span class="member"><%-- dit is ook zichtbaar voor brokers. Als broker zie ik nu 2x where to find --%>
<i> ¿Dónde los encuentro? </i> <br>
Usted puede encontrar los contratos de comisión en el "Menú: Personal> Comisiones de broker".<br>
Esta opción estará disponible sólo si usted posee los permisos correspondientes.
</span>
<hr>

<span class="admin broker">
<A NAME="commission_contracts_search_filters"></A><%--OK--%>
<h3> Contratos de comisión del broker </h3>
En esta página usted puede buscar los <a href="#commission_contract"><u>contratos de comisión</u></a> existentes.<br>
Las opciones de búsqueda se explican por sí mismas. El Estado de un contrato de comisión se explica <a href="#commission_contract_status"><u>aquí</u></a>.
<hr class="help">
</span>

<span class="admin broker">
<A NAME="commission_contracts_search_results"></A><%--OK--%>
	<h3> Resultado de búsqueda de contratos de comisión </h3>
	Esta ventana muestra una lista con sus 
	<a href="#commission_contract"><u>contratos de comisión</u></a> 
	y su <a href="#commission_contract_status"><u>estado</u></a> correspondiente.
	<ul>
	<li> Seleccione el ícono de Visualización <img border="0" src="${images}/view.gif" width="16" height="16"> para acceder a su información.</li>
	<br>
	<li> Seleccione el ícono de Eliminación <img border="0" src="${images}/delete.gif" width="16" height="16"> si desea eliminar el contrato.<br> 
	Esta opción sólo estará disponible si usted posse los permisos necesarios 
	y el contrato se encuentra en estado "Pendiente" o "Activo".</li>
	<br>
	<li> Seleccione el ícono de Modificación <img border="0" src="${images}/edit.gif" width="16" height="16"> si desea modificar el contrato de comisión.<br> 
	Esta opción sólo estará disponible si usted posee los permisos correspondientes 
	y el contrato se encuentra en estado "Pendiente".</li>
</ul>
<hr class="help">
</span>

<a name="commission_contract_status"></a><%--OK--%>
<h3> Estado del contrato de comisión </h3>
	Un <a href="#commission_contract">contrato de comisión</u></a> puede poseer los siguientes estados:
<ul>
	<li> <b> Pendiente: </b> El contrato de comisión estará "pendiente" hasta que el miembro lo acepte o rechace.
	<li> <b> Aceptado: </b> Cuando el contrato de comisión ha sido aceptado por el miembro, pero su 
	fecha de inicio no ha sido alcanzada aún.
	<li> <b> Activo: </b> Cuando un contrato de comisión ha sido aceptado y su fecha de inicio se ha alcanzado; 
	las comisiones se cobrarán según los términos establecidos en el contrato.
	<li> <b> Rechazado: </b> Cuando un contrato de comisión es rechazado por el miembro.
	<li> <b> Vencido: </b> Cuando un contrato no es aceptado antes de su fecha de inicio.
	<li> <b> Cancelado: </b> Un broker puede cancelar un contrato de comisión, lo que significa que los pagos futuros no generarán comisiones.
	<li> <b> Cerrado: </b> El contrato de comisión ha finalizado, debido a que 
	su fecha de finalización ha sido alcanzada y las comisiones han sido cargadas.
</ul>
<hr class="help">

<A NAME="commission_charge_status"></A><%--OK--%>
<h3> Comisiones de broker </h3>
Esta ventana muestra la información sobre sus <a href="#commission"><u>comisiones</u></a> actuales en el sistema.<br>

Pueden ser comisiones por defecto o individuales, acordadas con su 
<span class="member">broker</span>
<span class="broker">(miembro, en el caso de un broker)</span> 
en su correspondiente <a href="#commission_contract"><u>contrato de comisión</u></a>.<br>
<br>
La información mostrada en cada comisión se explica por sí misma (<i>Tipo, Monto y Validez</i>).<br> 
<br>
Un contrato de comisión se encontrará en uno de los 
<a href="#commission_contract_status"><u>estados</u></a> posibles.

<%-- 
<h3> Estado de la comisión </h3>
Esta ventana muestra la información acerca de la <a href="#commission"><u>comisión</u></a> actual.<br>
Esta puede ser una comisión por defecto o una comisión individual, acordada en su 
<a href="#commission_contract"><u>contrato de comisión</u></a>.<br>
La información mostrada se explica por sí misma. 
Un contrato se econtrará en uno de los <a href="#commission_contract_status"><u>estados</u></a> posibles.
--%>
<hr class="help">

<A NAME="commission_contracts_list"></A><%--OK--%>
<h3> Contratos de comisión </h3>
Esta ventana muestra una lista con todos sus 
<a href="#commission_contract"><u>contratos de comisión</u></a>,
y su respectivos <a href="#commission_contract_status"><u>estados</u></a>.
<br>

<span class="admin broker">
<ul>
	<li> Seleccione el ícono de Visualización <img border="0" src="${images}/view.gif" width="16" height="16"> 
	correspondiente, para acceder a sus detalles.</li>
	<br>
	<li> Seleccione el ícono de Eliminación <img border="0" src="${images}/delete.gif" width="16" height="16"> 
	correspondiente, para eliminar un contrato de comisión.<br>
	Esta opción sólo estará disponible si usted posee los permisos correspondientes 
	y el contrato se encuentra en estado "Pendiente" o "Activo".</li>
	<br>
	<li> Seleccione el ícono de Modificación <img border="0" src="${images}/edit.gif" width="16" height="16"> 
	correspondiente, para modificar un contrato de comisión.<br> 
	Esta opción sólo estará disponible si usted posee los permisos correspondientes 
	y el contrato se encuentra en estado "Pendiente".</li>
	<br>
	<li> Usted puede crear un nuevo contrato de comisión a través del menú
	desplegable "Nuevo Contrato", ubicado en la parte inferior derecha de esta ventana.<br>
	Este menú se encontrará vacío si la Administración NO ha definido ningún tipo de comisión por defecto.</li>
</ul>
</span>

<span class="member notBroker">
<br>
Usted puede acceder a sus detalles, seleccionando el ícono de Visualización 
<img border="0" src="${images}/view.gif" width="16" height="16"> correspondiente.<br>
<br>
NOTA: Si un contrato de comisión se encuentra en estado "Pendiente", 
usted podrá aceptarlo o rechazarlo accediendo a sus detalles.
</span> 
<hr class="help">


<A NAME="commission_contract_edit"></A><%--OK--%>
<h3> Ver / Modificar contrato de comisión de broker </h3>
La ventana muestra los detalles del 
<a href="#commission_contract"><u>contrato de comisión</u></a> seleccionado.<br>
<br>
<a href="#commission_contract_status"><u>Haga click aquí</u></a> 
para obtener mayor información sobre los posibles Estados de un contrato de comisión.<br>
<br>
Una vez "Activo", un contrato de comisión NO puede ser eliminado.
Sin embargo, se puede <a href="#commission_contract_status"><u>cancelar</u></a>
el contrato, haciendo click en el botón "Cancelar".
<br><br>

<span class="member">
Si su estado es "Pendiente", significa que se trata de un nuevo contrato de comisión 
propuesto a usted por su broker.
Puede Aceptar o Rechazar el contrato propuesto, haciendo click en los botones correspondientes, 
ubicados en la parte inferior de la ventana.<br>
Un contrato de comisión NO se aplicará a menos que usted lo acepte.</span>
<hr class="help">

<A NAME="commission_contract_insert"></A><%--OK--%>
<h3> Insertar contrato de comisión de broker </h3>
Esta ventana le permite crear un Nuevo <a href="#commission_contract"><u>contrato de comisión</u></a> 
para el miembro seleccionado.<br>
<br>
El contrato siempre se iniciará en el estado "Pendiente", ya que sólo puede adoptar
otro estado al ser Aceptado (o Rechazado) por el miembro.<br> 
<br>
Usted debe establecer su: <i>Fecha de inicio</i>, <i>Fecha de finalización</i> 
y el <i>Monto</i> (porcentaje o valor fijo) del nuevo contrato de comisión.<br> 
<br>
Para elegir las	 fechas, puede utilizar el selector de fechas a través su ícono 
<img border="0" src="${images}/calendar.gif" width="16" height="16"> correspondiente. 

<%-- 
<br><br>
Una vez "Activo", un contrato de comisión NO puede ser eliminado. 
Sin embargo, se puede <a href="#commission_contract_status"><u>cancelar</u></a>
el contrato, haciendo click en el botón "Cancelar".

--%>

<hr class="help">
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

