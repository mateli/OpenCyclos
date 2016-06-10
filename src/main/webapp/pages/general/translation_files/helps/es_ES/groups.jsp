<div style="page-break-after: always;"><a name="groups_top"></a>
<br><br>Cyclos permite la categorización de
usuarios en Grupos.<br>
Cada usuario del sistema puede pertenecer a un único grupo. <br>
Existen tres <a href="#group_categories"><u>categorías
principales</u></a> de grupos (miembros, brokers y administradores).<br>
<br>
Los grupos son utilizados para otorgar <a href="#permissions"><u>permisos</u></a>
a usuarios en la aplicación. Un usuario no puede acceder a la
funcionalidad de Cyclos, si su grupo no cuenta con los permisos
adecuados. <br>
Por supuesto que es posible cambiar los permisos del grupo, o mover a
los usuarios de un grupo de permisos a otro.<br>
<br>
Además de los permisos, un grupo puede tener un grupo específico de <a
	href="#edit_member_group"><u>configuraciones</u></a>, que definen el
comportamiento de un grupo, como límites y tipos de accesos permitidos.
<br>
Los grupos de tipo "miembro" tienen más opciones de configuración que
los de tipo "administrador". Por ejemplo, en la configuración de los de
tipo miembro se puede definir qué cuentas tiene el grupo, así como el
diseño y los archivos personalizados de un grupo específico.

<br><br>El hecho de que un Miembro pueda pertenecer solamente a un grupo
no significa que toda la configuración del sistema, sea específica y
dependa exlusivamente de los grupos. El sistema es igualmente muy
flexible.<br>
Existen configuraciones a nivel del sistema, a nivel de los grupos, y a
nivel individual. <br>
Si una misma regla de configuración existe en distintos niveles, la de
menor nivel poseerá siempre mayor prioridad. <br>
Por ejemplo, un límite de crédito de un individuo sobrescribe el límite
de crédito del grupo, y una página de grupo personalizada (como ser las
páginas de contactos) sobrescribirá la página de contactos del sistema.<br>
Muchas configuraciones pueden ser aplicadas a varios grupos. Por
ejemplo: un cargo (tasa) puede ser configurado para ser aplicado a
varios grupos de miembros.

<br><br>Cyclos cuenta con una configuración de grupos por defecto.
Normalmente, estos grupos son suficientes para utilizar el sistema.<br>

Puede utilizar estos grupos por defecto no solamente para brindar
permisos, sino también para administrar sus grupos.<br>
Por ejemplo, si se necesita borrar un miembro del sistema, simplemente
puede moverlo al grupo "miembros eliminados" a través de la función
"cambiar grupo". Esta función graba todos los cambios sobre los grupos
con la fecha, tiempo y el administrador que realizó dicha acción.<br>
Aunque la configuración con los grupos por defecto sirve para la mayoría
de las organizaciones, es posible crear nuevos grupos. Sin embargo, esto
es algo que debería ser realizdo solamente por alguien con experiencia.

<br><br><span class="admin"> <i> ¿Dónde los encuentro? </i><br>
La Administración de Grupos se encuentra bajo el Menú "Principal:
Usuarios & Grupos> Permisos de Grupo".<br>
Filtros de grupo se encuentra en "Menú: Usuarios & Grupos> Filtros de
grupo". 

<i> ¿Cómo hacerlos funcionar?</i><br>
Un nuevo miembro siempre será parte de un grupo. Por lo tanto un grupo
deberá ser seleccionado cuando se crea un nuevo miembro o un nuevo
administrador. Esto se realiza en la sección del Menú principal:
"Usuarios & Grupos> <a
	href="${pagePrefix}user_management#search_member_by_admin"><u>Administrar
Miembros</u></a>" y "Usuarios & Grupos> <a
	href="${pagePrefix}user_management#search_admin"><u>Gestionar
Administradores</u></a>".<br>
</span>
<hr>

<a name="group_categories"></a>
<h2>Principales categorías de grupos</h2>
Existen tres principales categorías de grupos:
<ul>
	<li><a href="#member_groups"><u>Grupos de Miembros:</u></a>
	Miembros comunes que tienen acceso a la sección de miembros de Cyclos.
	<li><a href="#broker_groups"><u>Grupos de Brokers:</u></a> Un tipo
	de "súper-miembros", miembros con algunas funciones administrativas
	sobre otro grupo de miembros.
	<li><a href="#admin_groups"><u>Grupos de Administradores:</u></a>
	Usuarios con funciones administrativas.
</ul>
La separación en tres principales categorías es por razones de
seguridad, para que sea imposible dar a algún miembro <a
	href="#permissions"><u>permisos</u></a> de administrador por accidente.
<br>
Todos los grupos cuentan con permisos por defecto que no se pueden
modificar.
<hr>

<a name="member_groups"></a>
<h2>Grupos de miembros estándar</h2>
Los miembros de este grupo tienen acceso a la sección de miembros de
Cyclos. <br>
El sistema cuenta con los siguientes Grupos de miembros estándar:
<ul>
	<li><a href="#inactive_members"><u>Miembros Pendientes</u></a>:
	Los usuarios recién ingresados a través de la pagina de registro son
	puestos en este grupo. Tendrán que ser "activados" (colocados en un
	grupo de "miembros plenos") por un administrador antes de que puedan
	ingresar al sistema.

	<li><a href="#full_members"><u>Miembros Plenos</u></a>: Miembros
	normales.

	<li><a href="#disabled_members"><u>Miembros Deshabilitados</u></a>:
	Miembros temporalmente inactivos.

	<li><a href="#removed_members"><u>Miembros Eliminados</u></a>:
	Miembros que han dejado de utilizar el sistema definitivamente.
</ul>


Los grupos por defecto no son fijos o "hard coded", pero han sido
creados utilizando los <a href="#permissions"><u>permisos</u></a> de
grupo y las <a href="#edit_member_group"><u>configuraciones</u></a> que
creemos son usualmente utilizadas. Sin embargo es posible modificar
estos grupos y crear nuevos con diferentes configuraciones. <a
	name="pending_members"></a>
<h3>Grupo "Miembros Pendientes"</h3>
Cuando un usuario se registra a través de la página de registro, es
colocado automáticamente en el grupo de "Miembros Pendientes". Los
miembros de este grupo no pueden acceder al sistema ya que NO poseen una
cuenta activa.<br>
Los administradores de cuentas pueden solicitar la lista de usuarios de
este grupo; validar el usuario, y luego colocarlo dentro del grupo de
"usuarios plenos". Normalmente el grupo <a href="#full_members"><u>"Miembros
Plenos"</u></a>. <br>
Tanto el grupo de Miembros Inactivos como el de "Miembros Pendientes"
son unidireccionales, ya que no es posible volver a poner un miembro en
el grupo de Miembros Pendientes. <br>
La única opción es que el nuevo miembro en "Miembros Pendientes" sea
completamente eliminado - en caso de una registración en falso. <br>
Una vez que el miembro fue movido al grupo "Miembros Plenos", no puede
ser eliminado, sino que únicamente puede ser puesto bajo el grupo <a
	href="#removed_members"><u>"Miembros Eliminados"</u></a>. <a
	name="full_members"></a>
<h3>Grupo "Miembros Plenos"</h3>
Este es el grupo normal de los miembros. Un "Miembro Pleno" puede
ingresar al sistema y seleccionar cualquier funcionalidad de los
Miembros. Cuando un miembro es movido del grupo de Pendientes al de
Plenos el mismo es "activado", Esto significa que puede recibir una
cuenta con el crédito por defecto (si está configurado) así como una
contraseña para ingresar al sistema. <br>
En caso de estar habilitado, recibirá un "Correo de Activación" con
información sobre el código de usuario y el estado de su cuenta. <a
	name="disabled_members"></a>
<h3>Grupo "Miembros Deshabilitados"</h3>
Cuando un Administrador coloca a un Miembro en el grupo de "Miembros
Deshabilitados" dicho Miembro no puede acceder más al sistema. La cuenta
está en modo de "Suspensión". Los miembros de este grupo no pagan cargos
(tasas) ni impuestos. <br>
La única función activa que un "Miembro Deshabilitado" tiene es la de
continuar recibiendo pagos (pero no puede acceder al sistema para
verlos). <br>
Los anuncios de los Miembros Deshabilitados no se muestran en la
búsqueda de anuncios realizados por otro Miembro.<br>
Pero el perfil de un Miembro Deshabilitado aparece en la búsqueda de
miembros. Cuando se visualiza el perfil de un Miembro Deshabilitado se
muestra un mensaje detallando que el Miembro esta deshabilitado y no
tiene acceso al sistema (por el momento). <br>
Para ser re-activado, un Miembro de este grupo necesita ser devuelto al
grupo de <a href="#full_members"><u>Miembros Plenos</u></a> por un
Administrador. <br>
Una de las razones más comunes por la cual se pone a un Miembro como
deshabilitado es cuando el Miembro se va al extranjero por un período de
tiempo (como cuatro meses). Asimismo puede ser utilizado para poner a
Miembros sospechosos en este grupo y así evitar que ingresen al sistema
(mientras se los investiga). <br>

<a name="removed_members"></a>
<h3>Grupo "Miembros Eliminados"</h3>
La razón de mover a un Miembro al grupo "Miembros Eliminados" es que el
Miembro abandonó el sistema. Una vez que ingresa al grupo de los
Eliminados no puede volver a ningún otro grupo. La información aún se
mantiene en la Base de Datos y puede ser accedida por los
administradores y sirve solamente como función de respaldo. <br>
Cualquier dato (anuncios, perfil) de los Miembros Eliminados no puede
ser vista por otros miembros. Solo la historia de transacciones mostrará
las transacciones realizadas con este Miembro. En caso de que un Miembro
Pleno seleccione el nombre del Miembro Eliminado de una antigua
transacción del registro de transacciones históricas, recibirá un
mensaje advirtiendo que el Miembro ha sido Eliminado en vez de mostrar
su perfil. Si algún Miembro tiene a un Miembro Eliminado entre su lista
de contactos, recibirá el mismo mensaje de alerta.
<br><br>El grupo de Eliminados actúa con el mero propósito de archivo
histórico. Si luego de algunos años el sistema necesita una limpieza, el
Administrador sabe que la información contenida en el grupo de
Eliminados puede ser borrada (o respaldada) con seguridad.<br>
<br>
<b>Nota:</b> Hay una excepción a esta regla. Un miembro que nunca
perteneció a un grupo que tuvo cuentas puede ser eliminado
permanentemente del sistema. Hay un permiso de administración diferente
para este propósito. <br>
<hr>

<a name="broker_groups"></a>
<h2>Grupos de brokers estándar</h2>
Los Brokers son una suerte de "Súper Miembros" que tienen permitido
realizar ciertas acciones administrativas para un grupo de otros
miembros. Tienen acceso a la sección de miembros de Cyclos.<br>
<br>
Los siguientes grupos de brokers estándar están disponibles en Cyclos:
<ul>
	<li><a href="#full_brokers"><u>Brokers Plenos</u></a>: Lo usual,
	brokers normales (estándar).

	<li><a href="#disabled_brokers"><u>Brokers Deshabilitados</u></a>:
	Brokers temporalmente deshabilitados.

	<li><a href="#removed_brokers"><u>Brokers Eliminados</u></a>:
	Brokers permanentemente eliminados.
</ul>


Tal como los grupos de miembros por defecto, los grupos de brokers por
defecto no son fijos o "hard coded" sino que han sido creados utilizando
un grupo de <a href="#permissions"><u>permisos</u></a> y <a
	href="#edit_broker_group"><u>configuraciones</u></a> que creemos son
las comúnmente utilizadas. <br>
Pero es posible modificar estos grupos y crear nuevos grupos con
diferentes configuraciones. <a name="full_brokers"></a>
<h3>Grupo "Brokers Plenos"</h3>
Un broker es un Miembro con funciones extra. Puede registrar a otros
miembros y dependiendo de la configuración del sistema, puede tener
cierto acceso a los miembros de los cuales él es broker. <br>
Cuando un broker registra un miembro este miembro necesita primero ser
activado por el administrador. Existe la posibilidad de que el broker
ponga al miembro directamente en uno o varios grupos "activos", pero
esto debe configurarse previamente. También es posible que el broker
reciba una <a href="${pagePrefix}brokering#commission"><u>
comisión </u></a> sobre la (transacciones) actividad de sus miembros. Una
comisión es configurada pero no activada por el grupo de Brokers Plenos.

<a name="disabled_brokers"></a>
<h3>Grupo "Brokers Deshabilitados"</h3>
Un broker de este grupo no puede ingresar al sistema como broker ni como
miembro. Su cuenta se mantiene activa, lo que significa que puede
recibir pagos y deberá pagar aportes (si corresponde). <br>
Se puede utilizar este grupo para deshabilitar temporalmente a un
broker, en caso de que se vaya al exterior por algún tiempo, o hay una
investigación en curso por sospecha de abuso o fraude.
<br><br>Véase la <a href="${pagePrefix}brokering"><u>sección de
brokers</u></a> por más información sobre los mismos. <a name="removed_brokers"></a>
<h3>Grupo "Brokers Eliminados"</h3>
Este grupo es similar al de <a href="#removed_members"><u>"Miembros
Eliminados"</u></a>. <br>
Si un broker ha sido puesto en este grupo, la historia de "broker -
miembros" aún será visible para los administradores. <br>
Tenga cuidado ya que el broker no podrá volver a otro grupo; "eliminado"
en este caso realmente significa "eliminado".
<br><br>Véase la <a href="${pagePrefix}brokering"><u>sección de
brokers</u></a> por más información sobre los mismos.
<hr>

<a name="admin_groups"></a>
<h2>Grupos de administradores estándar</h2>
Los usuarios de este grupo pueden realizar tareas administrativas dentro
del sistema. <br>
Tienen acceso a la sección de administración de Cyclos. <span
	class="admin"> Cyclos cuenta con los siguientes grupos de
administradores estándar:
<ul>
	<li><a href="#system_admins"><u>Administradores de Sistema</u></a>:
	Pueden realizar cualquier función de administración.
	<li><a href="#account_admins"><u>Administradores de Cuenta</u></a>:
	Administran la/s cuenta/s de una serie de miembros.
	<li><a href="#disabled_admins"><u>Administradores
	Deshabilitados</u></a>: Administradores temporalmente deshabilitados.
	<li><a href="#removed_admins"><u>Administradores
	Eliminados</u></a>: Administradores definitivamente eliminados.
</ul>
Este grupo cuenta con <a href="#permissions"><u>permisos</u></a> por
defecto, que pueden ser modificados. </span> <span class="admin"> <a
	name="system_admins"></a>
<h3>Grupo "Administradores de Sistema"</h3>
Usuarios de este grupo pueden realizar cualquier función administrativa,
incluyendo la creación de nuevos administradores, configuración de
permisos y del sistema en general. Se recomienda utilizar este grupo
para la configuración del sistema y no para tareas operacionales. </span> <span
	class="admin"> <a name="account_admins"></a>
<h3>Grupo "Administradores de Cuenta"</h3>
Usuarios de este grupo puede realizar tareas de administración sobre sus
miembros asignados (incluyendo anuncios), sin poder realizar tareas de
configuración del sistema. También tienen acceso a otras funciones como
ser el estado del sistema, estadísticas, etc. Es posible crear grupos de
administradores de cuenta para gestionar grupos de miembros específicos
para dividir "horizontalmente&quot la administración de cuentas;. </span> <span
	class="admin"> <a name="disabled_admins"></a>
<h3>Grupo "Administradores Deshabilitados"</h3>
Los administradores de este grupo no pueden hacer nada, ni siquiera
ingresar al sistema. Se utiliza para deshabilitar administradores en
forma temporaria sin tener que borrarlos. </span> <span class="admin"> <a
	name="removed_admins"></a>
<h3>Grupo "Administradores Eliminados"</h3>
Este grupo se utiliza para borrar del sistema a un administrador en
forma definitiva. Hay que tener cuidado ya que tal como con los <a
	href="#removed_members"><u>Miembros Eliminados</u></a>, una vez
eliminado el administrador no hay marcha atrás. La única opción que hay
para volver a utilizar el administrador eliminado, es borrarlo
completamente del sistema y de la base de datos, para luego registrarlo
nuevamente.
<hr class="help">
</span> <span class="admin"> <a name="change_group"></a>
<h3>Cambio de grupo</h3>
Con esta opción se puede cambiar a un miembro (o <a
	href="${pagePrefix}brokering"><u>broker</u></a>) del grupo al cual
pertenece. Simplemente seleccione el nuevo grupo de la lista desplegable
y escriba un comentario sobre el motivo del cambio en el área de
"Descripción". Luego acepte el cambio haciendo click en el botón
"Aceptar".
<br><br>Haga click aquí para una visión general de <a
	href="#member_groups"><u>grupos de miembros</u></a>.
<br><br>Luego de haber aceptado el cambio de grupo, el mismo es ingresado
en la casilla de historial en orden cronológico, con el último cambio
encabezando la lista. El historial de comentarios muestra para cada
comentario una línea de estado con el nombre del administrador que
ingresó el cambio , la fecha y el cambio de grupo realizado ("del grupo
x al y"). <br>
Este es la forma en que los administradores pueden ver rápidamente que
paso con una cuenta de Miembro y leer los motivos del cambio. La línea
de comentario es una oración corta con el motivo del cambio. Cualquier
otra información adicional sobre un cliente debe ser ingresada en la
función de <a href="${pagePrefix}profiles#member_info_actions"><u>observaciones</u></a>.
<br><br>algunas observaciones sobre el cambio de grupo:
<ul>
	<li>Cuando un Miembro pertenece al grupo <a
		href="#inactive_members"><u>"Miembros Pendientes"</u></a> usted cuenta
	con la opción de borrar al Miembro completamente del sistema. Esto
	puede ser útil en caso de registraciones en falso. Una vez que la
	cuenta de Miembro es activada no puede ser borrada, pero puede ser
	puesta en el grupo "Miembros Eliminados".
	<li>Cuando se mueve un Miembro del grupo de <a
		href="#full_brokers"><u>brokers</u></a> al de miembros normales (no
	brokers), todos los miembros que eran gestionados por dicho broker ya
	no lo son, y no tienen broker alguno (No es el caso cuando se mueve al
	broker de un grupo de brokers a otro, por ejemplo el de <a
		href="#disabled_brokers"><u>Brokers Deshabilitados</u></a>). Si no
	desea que los miembros queden sin un broker asignado, sería prudente
	cambiar primero el broker de todos los miembros involucrados, y luego
	si cambiar el broker a un grupo de no-brokers. Esto puede realizarse a
	través de la función <a
		href="${pagePrefix}user_management#bulk_actions"><u>acciones
	en masa</u></a>.
</ul>
<hr class="help">
</span> <span class="admin"> <a name="change_group_admin"></a>
<h3>Cambio de grupo de administradores</h3>
Aquí puede cambiar el <a href="#admin_groups"><u>grupo de
administradores</u></a> al cual un Administrador pertenece. Simplemente
seleccione el nuevo grupo de la lista desplegable. Usted puede escribir
un comentario sobre el cambio (por ejemplo: el motivo) en el área de
"descripción". No se olvide de hacer click en el botón de "Aceptar"
correspondiente al "Cambio de Grupo" luego de ingresar el texto.
<br><br>Luego de aceptar el cambio, el mismo es ingresado en la casilla
del historial, en orden cronológico, con el último cambio encabezando la
lista. El historial de comentarios muestra para cada comentario una
línea de estado con el nombre del administrador que ingresó el cambio ,
la fecha y el cambio de grupo realizado ("del grupo x al y").
<br><br>De esta forma los administradores pueden ver que paso con la
cuenta de un Administrador rápidamente y el motivo de los cambios
realizados. El comentario es una oración de largo acotado que contiene
los motivos del cambio. Cualquier otra información adicional sobre el
Administrador debe ser ingresada con la función de Observaciones.
<br><br>En esta ventana existe también la posibilidad de eliminar un
Administrador completamente del sistema si es necesario. Sin embargo, la
mejor forma de hacer esto es poner al Administrador en el "Grupo de
Administradores Deshabilitados".
<hr class="help">
</span> <span class="admin"> <a name="group_management"></a>
<h2>Administración de grupos</h2>
Es posible realizar varias acciones de administración de grupos en
cyclos. Se pueden modificar, agregar y borrar las propiedades y los
permisos de los grupos.
La administración de grupos es accedida mediante "Menú: Usuarios &
Grupos> Grupos de permisos".
<hr>
</span> <span class="admin"> <a name="search_groups"></a>
<h3>Buscar grupos</h3>
Usted puede buscar grupos de usuarios seleccionando una de las <a
	href="#group_categories"><u>categorías de grupos</u></a> en la lista
desplegable "Tipo", así como a través de los <a href="#group_filters"><u>Filtros
de grupo</u></a>, si existen definidos en el sistema.
<hr class="help">
</span> <span class="admin"> <a name="manage_groups"></a>
<h3>Grupos - Administrar grupos de usuarios</h3>
Esta ventana permite Administrar los grupos de usuarios y sus permisos,
ofreciendo un visión general de los grupos disponibles y la posibilidad
de crear nuevos grupos de usuarios.
<br><br>Usted puede hacer click sobre los siguientes íconos para cada uno
de los grupos existentes:
<ul>
	<li><img border="0" src="${images}/edit.gif" width="16"
		height="16">&nbsp; Seleccionar el ícono de Modificación abrirá
	una página donde puede modificar las propiedades del grupo.

	<li><img border="0" src="${images}/permissions.gif" width="16"
		height="16">&nbsp; Seleccionar el ícono de Permisos abrirá una
	página donde puede modificar los permisos del grupo.

	<li><img border="0" src="${images}/permissions_gray.gif"
		width="16" height="16">&nbsp; Si el ícono de permisos se
	encuentra en color gris, significa que los permisos NO pueden ser
	modificados, debido a que el grupo es del tipo "eliminado".<br>
	Los miembros colocados en este grupo, serán eliminados pero parte de su
	información permanecerá en el sistema.<br>
	Por más información consulte el <a href="#insert_group"><u>archivo
	de ayuda</u></a> sobre insertar "nuevo" grupo.

	<li><img border="0" src="${images}/delete.gif" width="16"
		height="16">&nbsp; Seleccionar el ícono de Eliminación le
	permitirá eliminar el grupo. Solamente se pueden eliminar los grupos
	que no posean miembros.
</ul>
Puede hacer click en el botón "Agregar un nuevo grupo" para crear un
nuevo grupo.<br>
Nota: Se recomienda realizar esta acción solamente cuando posea
experiencia trabajando con los grupos por defecto.<br>
<br>
Haga <a href="#group_categories"><u>click aquí</u></a> si desea obtener
una visión general sobre las categorías de grupo.
<hr class="help">


</span> <span class="admin"> 
<a name="edit_admin_group"></a>
<h3>Modificar grupo administrador</h3>
Aquí usted puede modificar las propiedades de un <a href="#admin_groups"><u>grupo Administrador</u></a>.
<br>
Luego de hacer click en el botón "Modificar", usted puede modificar los 
<a href="#group_details"><u>Detalles de grupo</u></a> que son los mismos que para los grupos de miembro y broker.<br>
<br><br>
Advierta que aquí no es posible configurar sus permisos; los
detalles y configuración de un grupo no son lo mismo que sus permisos.<br>
<br>
Usted puede modificar los permisos del grupo haciendo click en el ícono
de permisos <img border="0" src="${images}/permissions.gif" width="16" height="16"> desde la 
<a href="#manage_groups"><u>administración de grupos</u></a>, o utilizando el botón de atajo "Permisos de Grupo" 
ubicado en la parte inferior izquierda de esta ventana.<br>
<br>
The following access settings are available
Las siguientes configuraciones de acceso están disponibles:
<ul>
	<li><b>Largo de contraseña:</b> Largo mínimo y máximo de la
	contraseña de acceso.<br>
	<li><b>Política de contraseñas:</b> Existen tres(3) opciones
	disponibles y auto-explicativas:
	<ul>
		<li>No reforzar la contraseña.</li>
		<li>Prohibir contraseñas sencillas.</li>
		<li>Prohibir contraseñas sencillas obligando letras y números.</li>
	</ul>
	<br>
	<li><b>Máximo de intentos por contraseña errónea:</b> 
	Cuando el usuario alcanza la cantidad máxima de intentos fallidos definida, pierde la
	posibilidad de volver a ingresar al sistema, hasta que se alcance el
	tiempo de re-activación (ver siguiente parámetro).<br>
	
	<li><b>Tiempo de re-activación por contraseña errónea:</b> Este es
	el tiempo durante el cual el usuario no podrá ingresar al sistema,
	luego de alcanzar la cantidad máxima de intentos de contraseña errónea
	(parámetro anterior).<br>

	<li><b>Validez de la contraseña de acceso:</b> Mediante esta
	configuración se puede definir el período de validez de la contraseña
	de acceso. Cuando expira dicho período, el miembro se ve obligado a
	ingresar una nueva contraseña. Si se ingresa "0" aquí, la contraseña no
	caducará nunca.<br>

	<li><b>Contraseña de transacción:</b> Aquí se puede configurar la
	utilización de una contraseña especial para las transacciones,
	denominada <a href="${pagePrefix}passwords#transaction_password"><u>contraseña
	de transacción</u></a>. <br>
	Se puede seleccionar una de las siguientes opciones:
	<ul>
		<li><b>No usado</b>: La contraseña de transacción no es
		utilizada, y los miembros pueden realizar cualquier transacción (si es
		que poseen permisos por supuesto) sin tener que ingresar una
		contraseña de transacción.
		<li><b>Automático</b>: Si esta opción es seleccionada, el sistema
		generará una contraseña de transacción al crearse la cuenta de un
		nuevo Miembro (o al modificarse el parámetro, para miembros
		existentes). El Miembro recibirá su contraseña de transacción (por
		única vez) en su bandeja de mensajes personal.
		<li><b>Manual</b>: Si esta opción es seleccionada, la contraseña
		de transacción puede ser generada únicamente de forma manual a través
		de la opción "Habilitar generar la contraseña de transacción", ubicada
		en la acción <a href="${pagePrefix}profiles#access_actions"><u>Administrar
		contraseñas</u></a> en la página de perfil del Miembro.<br>
		Más información sobre la contraseña de transacción puede ser
		encontrada en dicha página.</li>
		<br>
	</ul>
	<li><b>Largo de la contraseña de transacción:</b> Define el largo
	de la contraseña de transacción. Esta contraseña siempre posee un largo
	fijo (obviamente, esta configuración no tendrá efecto si la contraseña
	de transacción no es utilizada).

	<li><b>Máximo de intentos por contraseña de transacción
	errónea:</b> Luego de alcanzar la cantidad de intentos aquí definida sin
	ingresar la contraseña de transacción correctamente, la misma será
	bloqueada.<br>
	Un Administrador puede reestablecer la contraseña mediante la acción
	"Habilitar generar la contraseña de transacción", ubicada en la acción
	<a href="${pagePrefix}profiles#access_actions"><u>Administrar
	contraseñas</u></a> en la página de perfil del Miembro (esta configuración no
	tiene efecto si la contraseña de transacción no es utilizada).</li>
</ul>
No olvide hacer click en el botón "Aceptar" para confirmar las
modificaciones realizadas.
<hr class="help">
</span> <span class="admin"> <a name="edit_member_group"></a>
<h3>Modificar grupo miembro</h3>
Aquí usted puede modificar las propiedades de un <a
	href="#member_groups"><u>grupo Miembro</u></a>.<br>
Luego de hacer click en el botón "Modificar", puede cambiar el Nombre,
la Descripción y varias categorías de configuraciones del grupo.
<br><br>Advierta que aquí no es posible configurar permisos; propiedades
y configuración de un grupo no son lo mismo que sus permisos.<br>
<br>
Puede modificar los permisos del grupo, haciendo click en el ícono de
permisos <img border="0" src="${images}/permissions.gif" width="16"
	height="16"> desde la <a href="#manage_groups"><u>administración
de grupos</u></a>, o utilizando el botón de atajo "Permisos de Grupo" ubicado en
la parte inferior izquierda de esta ventana.
<br><br>Las configuraciones de grupo de Miembro son ordenadas por
categoría. <br>
Las siguientes categorías están disponibles; usted puede hacer click en
los enlaces correspondientes para obtener más detalles sobre los campos
existentes en cada categoría:
<ul>
	<li><b><a href="#group_details"><u>Detalles de grupo:</u></a></b>
	Proporciona el resumen (detalles) del grupo.

	<li><b><a href="#group_registration_settings"><u>Configuración
	de registro:</u></a></b> Son las configuraciones que definen el comportamiento del
	grupo en relación al registro de sus miembros en el sistema. Contiene
	también otras configuraciones.

	<li><b><a href="#group_access_settings"><u>Configuración
	de acceso:</u></a></b> Son las configuraciones referentes a los accesos del grupo
	en el sistema.

	<li><b><a href="#group_notification_settings"><u>Configuración
	de notificaciones:</u></a></b> Todas las configuraciones relacionadas con las
	notificaciones asociadas al grupo.

	<li><b><a href="#group_ad_settings"><u>Configuración
	de anuncios:</u></a></b> Son las configuraciones que definen el comportamiento del
	grupo en relación a los anuncios en el sistema.

	<li><b><a href="#group_scheduled_payment_settings"><u>Configuración de pagos:</u></a></b> 
	Son las configuraciones referentes a los <a href="${pagePrefix}payments#scheduled"><u>pagos</u></a> del grupo.

	<li><b><a href="#group_loans_settings"><u>Configuración de préstamos:</u></a></b> 
	Son las configuraciones que definen el comportamiento del grupo en relación a los préstamos.
</ul>
<hr class="help">
</span> 

<span class="admin"> 
<a name="group_details"></a>
<h3>Detalles de grupo</h3>
Aquí usted puede establecer algunas de las configuraciones generales del grupo 
(dentro de la ventana <a href="#edit_member_group"><u>"Modificar grupo miembro"</u></a>).
<br>
Las siguientes opciones están disponibles:
<ul>
	<li><b>Tipo:</b> Define el tipo de grupo (miembro, broker, admin). 
	Es definido al <a href="#insert_group"><u>definir el grupo</u></a> y no puede ser modificado posteriormente.
	
	<li><b>Eliminado:</b> Este campo indica si el grupo es "eliminado". 
	Esto también se define al <a href="#insert_group"><u>definir el grupo</u></a> y no puede ser modificado.
	
	<li><b>Nombre:</b> Es el nombre del grupo. También se define al crear el grupo, pero puede ser modificado.
	
	<li><b>URL raiz de la aplicación:</b> 
	Usted deberá especificar aquí una dirección URL en el caso que desee que los usuarios de este grupo posean su propia página de acceso (login) a Cyclos.<br> 
	La URL se utiliza cuando se generan los enlaces/links que se incluyen en los correos electrónicos. 
	Por ejemplo, el enlace/link en una notificación de confirmación de registro vía e-mail. 
	Si se define una URL a nivel de grupo, 
	sobrescribirá la URL raíz de la aplicación definido en el filtro de grupo (si se especifica) y en la configuración local.
	
	<li><b>Nombre de página de acceso:</b> 
	Esta opción sólo se mostrará si usted ha personalizado archivos relacionados con la página de acceso/login (top.jsp, login.jsp, login.css) para este grupo 
	(en los "Archivos personalidos" ubicados en la parte inferior de la ventana).<br>
	<br>
	A la página de acceso personalizada (para el grupo) se puede acceder ingresando el nombre de la página de acceso/login "global" y luego, a continuación, 
	un signo de interrogación y el "Nombre de página de acceso" definido.
	El nombre de la página de acceso no puede poseer espacios.<br>
	Un ejemplo sería: http://www.suDominio.org/cyclos?<b>Nombre de página de acceso</b><br>
	<br>
	Tenga en cuenta que también es posible especificar una página de acceso (login) por 
	<a href="${pagePrefix}groups#group_filter"><u>filtro de grupo</u></a>.
	
	<li><b>URL de la página contenedora:</b> Al igual que el "Nombre de página de acceso" (arriba), 
	este campo sólo aparece cuando se ha personalizado la página de inicio o acceso (login) para este grupo.<br> 
	La URL de la página contenedora se utiliza si usted desea acceder a Cyclos desde una página Web.<br> 
	Esta configuración funciona igual que la "URL de la página contenedora global" 
	(ver <a href="${pagePrefix}settings#local"><u>Configuración - Configuración local</u></a>, pero sólo para este grupo.
	Si se define a nivel de grupo, se sobrescribirá la URL definida en la configuración local.
	Este parámetro se utiliza si usted desea acceder a Cyclos desde dentro de un sitio/portal Web.<br>
	La configuración funciona de igual forma que la "URL de la página contenedora global" 
	(ver <a href="${pagePrefix}settings#local"><u>Menú: Configuración> Configuración local</u></a>, pero sólo para este grupo.<br> 
	
	En este campo usted debe ingresar la página que abre el iframe o frame-set que incluye Cyclos. 
	Por ejemplo: <br> http://www.suDominio.org/cycloswrapper.php 
	<br>
	Tenga en cuenta que también es posible especificar una página contenedora 
	por <a href="${pagePrefix}groups#group_filter"><u>filtro de grupo</u></a>.
	
	<li><b>Descripción:</b> 
	Aquí usted puede ingresar la descripción del grupo.<br>
	Este campo sólo sirve para información adicional en la configuración del grupo, 
	y no se utiliza en ningún otro lugar en Cyclos.
	
	<li><b>Activar grupo:</b> 
	Esta opción sólo se mostrará para los grupos que no posean cuentas en el sistema.<br> 
	Si el grupo no debe ser visible para otros miembros, usted no debe seleccionar esta opción (casilla).<br>
	Si quiere que los miembros del grupo se encuentren visibles para otros usuarios, incluso cuando no posean cuentas, debe seleccionar esta opción.<br> 
	Ejemplos pueden ser los brokers, que necesitan ralizar exclusivametne tareas administrativas 
	o usuarios de demostración, que únicamente ingresan al sistema para visualizar ofertas y no transaccionan. <br>
	Al cambiar este parámetro, dicha modificación es aplicada a todos los miembros existentes y a los nuevos miembros del grupo.
	<br><br>
</ul>
<hr class="help">
</span> 

<span class="admin"> 
<a name="group_registration_settings"></a>
<h3>Configuración de registro para el grupo</h3>
Estas son las configuraciones que definen el comportamiento del grupo en
relación con el registro de sus Miembros. <br>
Esta configuración es parte del formulario <a href="#edit_member_group"><u>modificar
grupo miembro</u></a>.<br>
<br>
Las opciones disponibles son las siguientes:
<ul>
	<li><b>Grupo inicial:</b> Seleccione esta opción si usted desea que el grupo sea un "grupo inicial", 
	en el cual serán ingresados los miembros luego de haberse registrado en la página de registro público.<br>
	Puede existir uno o más grupos iniciales en el sistema (por ejemplo: "empresas pendientes", "personas pendientes", etc.).<br>
	<br>
	Al seleccionar esta opción, se mostrará el campo <b><i>"Mostrar como"</i></b>, donde
	usted puede especificar un nombre diferente para cada grupo inicial existente.<br>
	Esta opción sólo aparece cuando existe más de un grupo inicial. <br>
	<br>
	El usuario que se registre a través de la página de registro público
	deberá seleccionar de una lista desplegable el grupo inicial al que
	pertenecerá. Dicha lista desplegable, mostrará los nombres de los
	grupos (iniciales) definidos por usted en este campo.<br>
	<br>
	
	<li><b>Validación de correo electrónico:</b> Aquí usted puede 
	definir para que grupos y para	que tipos de usuarios 
	(miembro, broker, administrador o web service)
	el correo electrónico será validado.<br>
	La validación es requerida en el momento del registro o al 
	modificar el correo electrónico en el perfil.<br>
	<br>
	
	Hasta que el registro sea confirmado (activado) y el período máximo de
	confirmación no haya expirado, los miembros permanecerán en un estado
	"pendiente". <br>
	El período máximo de confirmación puede ser definido en la
	sección "Límites" de la <a href="${pagePrefix}settings#local"><u>configuración
	local</u></a> del sistema, a través del campo "Tiempo máximo para confirmación
	de correo electrónico para registro de miembros".<br>
	<br>
	
	Los miembros que se encuentran en estado pendiente, pueden ser
	visualizados en la página de <a
		href="${pagePrefix}user_management#search_pending_member"><u>miembros
	pendientes</u></a>.<br>
	<br>
	<br>

	<li><b>Términos del acuerdo:</b> Aquí usted puede elegir los
	términos de acuerdo que se mostrarán en la página de registro.<br>
	Puede agregar nuevos términos de acuerdo para el registro en la página
	de <a href="#list_registration_agreements"><u>términos de
	acuerdo</u></a>.<br>
	Si se definen términos de acuerdo para un grupo, todos los usuarios de
	ese grupo deberán aceptarlos para poder acceder a Cyclos. <br>
	Si los usuarios son registrados por un broker o por un administrador,
	la página para la aceptación de los términos de acuerdo será mostrada
	en el primero ingreso (login) del miembro al sistema.<br>
	<br>
	Si los usuarios ya registrados en el sistema son movidos a un grupo que
	tiene definido términos de acuerdo para su registro; en su próximo
	ingreso al sistema (login) se les presentará la página para la
	aceptación de los términos de acuerdo (deberá aceptarlos para poder
	iniciar sesión). <br>
	<br>
	Al establecer nuevos términos de acuerdo para un grupo, aparecerá la
	opción adicional "Forzar la aceptación en el próximo login". <br>
	Cuando esta opción sea seleccionada, a los nuevos usuarios y también a
	los miembros existentes del grupo, en su próximo ingreso al sistema
	(login) se les presentará la página para la aceptación de los términos
	de acuerdo.<br>
	Si esta opción no es seleccionada, sólo se les presentará a los nuevos
	usuarios registrados, quienes deberán aceptar los términos.<br>
	<br>

	<li><b>Enviar contraseña generada por correo electrónico:</b>
	Cuando esta opción es seleccionada, el miembro recibirá su contraseña
	de acceso al sistema vía correo electrónico, luego de su registro.<br>
	Si esta ocpión no es seleccionada, el usuario (y broker/administrador
	dependiendo de los permisos) puede definir su contraseña de acceso en
	el formulario de registro.<br>
	<br>
	El mensaje puede ser definido en la <a
		href="${pagePrefix}settings#mail"><u>configuración de correo</u></a>
	del sistema.<br>
	Si usted desea utilizar esta opción, asegúrese que el campo de correo electrónico sea obligatorio.<br>
	Esto puede configurarse en la <a href="${pagePrefix}settings#local"><u>configuración
	local</u></a> del sistema.<br>
	<br>

	<li><b>Número máximo de imágenes por miembro:</b> Es la cantidad
	máxima de imágenes que un Miembro puede añadir en su <a
		href="${pagePrefix}profiles"><u>perfil</u></a>.<br>
	<br>

	<li><b>Expirar miembros después de:</b> La membresía de este grupo
	puede ser configurada para que expire automáticamente, si se ingresa un
	valor en este campo distinto de "0". <br>
	Luego de transcurrido el tiempo ingresado en este campo desde que el
	miembro ingresó al grupo, el miembro será automáticamente movido a otro
	grupo (ver próxima opción).<br>
	<br>

	<li><b>Grupo después de la expiración:</b> Luego de transcurrido
	el tiempo de expiración de membresía de un grupo (parámetro anterior),
	sus miembros serán movidos al grupo ingresado en este campo.
</ul>
<hr class="help">
</span> <span class="admin"> <a name="list_registration_agreements"></a>
<h3>Términos de acuerdo</h3>
Esta ventana muestra una lista con los Términos de acuerdo de registro
existentes en el sistema.<br>
<ul>
	<li>Para Crear nuevos términos de acuerdo, haga click en el botón
	Aceptar correspondiente a la etiqueta "Crear términos de acuerdo",
	ubicado en la parte inferior derecha de la ventana.
	<li>Para Modificar (o visualizar) términos de acuerdo, haga click
	en el ícono <img border="0" src="${images}/edit.gif" width="16"
		height="16">&nbsp; correspondiente.
	<li>Para Eliminar términos de acuerdo, haga click en el ícono <img
		border="0" src="${images}/delete.gif" width="16" height="16">&nbsp;
	correspondiente.
</ul>
Los términos de acuerdo sólo podrán ser eliminados si no existe ningún
grupo de usuarios que los utilice, y no existan miembros que los hayan
aceptado.<br>
<br>
Puede encontrar más información acerca de Términos de acuerdo de
registro, en la correspondiente <a href="#group_registration_agreement"><u>página
de ayuda</u></a>. <br>
<hr class="help">
</span> <span class="admin"> <a name="registration_agreement"></a>
<h3>Crear / Modificar términos de acuerdo</h3>
Los Términos de acuerdo son el texto que se muestra en la página de
registro; en ellos son establecidas las condiciones de uso del sistema.<br>
Los usuarios que deseen registrarse, <b>deberán</b> aceptar dichos
términos, seleccionando la casilla de verificación correspondiente.<br>
<br>
Los términos de acuerdo pueden estar asociados a uno o más grupos de
usuarios. <br>
Esto puede ser definido en la <a href="#group_registration_settings"><u>configuración
de registro para el grupo</u></a>.<br>
<br>
Cuando usted realice modificaciones en los Términos de acuerdo, y desee
que los usuarios existentes (que ya aceptaron otros términos) acepten
los nuevos términos de acuerdo, deberá crear los nuevos términos y
cambiarlos (establecerlos) en la configuración de los grupos
correspondientes (seleccionando también la opción "Forzar la aceptación
en el próximo login").
<hr class="help">
</span> <span class="admin"> <a name="group_access_settings"></a>
<h3>Configuración de acceso del grupo</h3>
Estas configuraciones definen para el grupo, el comportamiento
relacionado con los accesos al sistema.<br>
Son parte del formulario <a href="#edit_member_group"><u>modificar
grupo miembro</u></a>.<br>
Las siguientes opciones están disponibles:
<ul>
	<li><b>Acceso a los canales:</b> Con <a
		href="${pagePrefix}settings#channels"><u>canales</u></a> se refiere a
	la forma de acceso a Cyclos. Aquí se pueden definir las diferentes
	formas en que este grupo accederá al sistema.<br>
	Si lo desea, puede obtener ayuda sobre la <a
		href="${pagePrefix}settings#channels"><u>configuración de
	canales</u></a>.<br>
	<br>
	Una o más de las siguientes opciones (canales) pueden ser
	seleccionadas:
	<ul>
		<li><b>Acceso Web principal (Internet):</b> Acceder a Cyclos a
		través de la página Web principal.</li>
		<li><b>Pagos Posweb:</b> Acceso <b>P</b>oint <b>O</b>f <b>S</b>ale
		(Punto de Venta) para comercios que aceptan pagos vía Cyclos. Si esta
		opción se encuentra habilitada significa que el grupo pagador
		(consumidor) necesitará tener configurado este acceso.</li>
		<li><b>SMS:</b> Acceso a Cyclos vía mensajes SMS.</li>
		<li><b>Acceso WAP 1 / WAP2:</b> Acceso a Cyclos vía telefonía
		celular, mediante navegación WAP.</li>
		<li><b>Pagos Webshop:</b> Acceso a través de un botón "Pagar con
		Cyclos" en una tienda virtual.</li>
	</ul>
	<br>
	<li><b>Acceso por defecto de miembros:</b> En esta opción usted
	puede seleccionar los canales activados por defecto, para los miembros
	pertencientes al grupo.<br>
	Sin embargo, cada miembro puede configurar sus preferencias personales
	respecto a los canales a utilizar; todas las opciones seleccionadas en
	el punto anterior ("canales accesibles") pueden ser activadas o
	desactivados por el propio usuario en su configuración personal.<br>
	<br>
	Ejemplo: Usted puede seleccionar la opción "WAP 2" de la lista
	desplegable "Acceso por defecto de miembros", lo que significa que
	todos los miembros de este grupo tendrán seleccionado por defecto el
	canal de acceso "WAP 2" en su configuración personal. Sin embargo,
	luego de esto, cada miembro puede decidir si desactivar o no esta
	opción. <br>

	<li><b>Tipo de tarjeta:</b> 
	Indica el tipo de tarjeta habilitado para el grupo.</li>
	
	<li><b>Largo de PIN (Mín./Máx.):</b> 
	Largo mínimo y máximo del PIN.</li>
	
	<li><b>Máximo de intentos por PIN erróneo:</b>  
	Cuando el miembro alcanza la cantidad de intentos aquí definida, su PIN será bloqueado 
	y no podrá operar con él, hasta que sea superado el tiempo de reactivación (ver siguiente parámetro) establecido.</li>
	
	<li><b>Tiempo de desactivación por PIN erróneo:</b>
	Este es el tiempo durante el cual el usuario no podrá operar con su PIN,
	luego de alcanzar la cantidad máxima de intentos de PIN erróneo (parámetro anterior).</li> 
	
	<li><b>Largo de contraseña de acceso (Mín./Máx.):</b> 
	Largo mínimo y máximo de la contraseña de acceso al sistema.<br>

	<li><b>Política de contraseñas:</b> El sistema ofrece cuatro
	políticas para el manejo de contraseñas de acceso:
	<ul>
		<li><b>Permitir contraseñas sencillas:</b> No se realizarán
		controles sobre las contraseñas definidas.</li>

		<li><b>Prohibir contraseñas sencillas:</b> No podrán ser
		definidas contraseñas:
		<ul>
			<li>Utilizadas en el pasado (repetidas).</li>
			<li>Que contengan el código de usuario de acceso al sistema.</li>
			<li>Que contengan el código de usuario del correo electrónico.</li>
			<li>Que posean únicamente caracteres o números consecutivos.</li>
			<li>Que posean datos contenidos en los campos personalizados del
			miembro del tipo cadena de caracteres (ejemplo: Dirección).</li>
			<li>Que posean datos coincidentes con campos personalizados del
			miembro del tipo fecha o númerico (ejemplo: Fecha de nacimiento o
			teléfono).</li>
		</ul>
		<br>
		<li><b>Forzar letras y números:</b> Adicionalmente a los
		controles realizados en la política anterior, se forzará el ingreso de
		letras y números en las contraseñas definidas.</li>

		<li><b>Forzar letras, números y caracteres especiales:</b>
		Adicionalmente al ingreso de letras y números, se exigirá el ingreso
		de caracteres especiales (como por ejemplo: ;, ", %) en las
		contraseñas definidas.</li>
	</ul>
	<br>
	<li><b>Máximo de intentos por contraseña errónea:</b> 
	Cuando el miembro alcanza la cantidad de intentos aquí definida, 
	no podrá ingresar (loguearse) al sistema, hasta que sea superado el tiempo de reactivación (ver siguiente parámetro) establecido.<br>
	
	<li><b>Tiempo de desactivación por contraseña errónea:</b> 
	Este es el tiempo durante el cual el usuario no podrá ingresar al sistema,
	luego de alcanzar la cantidad máxima de intentos de contraseña errónea (parámetro anterior).<br>

	<li><b>Validez de la contraseña de acceso:</b> Mediante esta
	configuración se puede definir el período de validez de la contraseña
	de acceso. Cuando expira dicho período, el miembro se ve obligado a
	ingresar una nueva contraseña. Si se ingresa "0" aquí, la contraseña no
	caducará nunca.<br>

	<li><b>Contraseña de transacción:</b> Aquí se puede configurar la
	utilización de una contraseña especial para las transacciones,
	denominada <a href="${pagePrefix}passwords#transaction_password"><u>contraseña
	de transacción</u></a>.<br>
	Se puede seleccionar una de las siguientes opciones:
	<ul>
		<li><b>No usado</b>: La contraseña de transacción no es
		utilizada, y los miembros pueden realizar cualquier transacción (si es
		que poseen permisos por supuesto) sin tener que ingresar una
		contraseña de transacción.

		<li><b>Automático</b>: Si esta opción es seleccionada, el sistema
		generará una contraseña de transacción al crearse la cuenta de un
		nuevo Miembro (o al modificarse el parámetro, para miembros
		existentes). El Miembro recibirá su contraseña de transacción (por
		única vez) en su bandeja de mensajes personal.

		<li><b>Manual</b>: Si esta opción es seleccionada, la contraseña
		de transacción puede ser generada únicamente de forma manual a través
		de la opción "Habilitar generar la contraseña de transacción", ubicada
		en la acción <a href="${pagePrefix}profiles#access_actions"><u>Administrar
		contraseñas</u></a> en la página de perfil del Miembro.<br>
		Más información sobre la contraseña de transacción puede ser
		encontrada en dicha página.</li>
	</ul>
	<br>
	<li><b>Largo de la contraseña de transacción:</b> Define el largo
	de la contraseña de transacción. Esta contraseña siempre posee un largo
	fijo (obviamente, esta configuración no tendrá efecto si la contraseña
	de transacción no es utilizada).

	<li><b>Máximo de intentos por contraseña de transacción
	errónea:</b> Luego de alcanzar la cantidad de intentos aquí definida sin
	ingresar la contraseña de transacción correctamente, la misma será
	bloqueada.<br>
	Un Administrador puede reestablecer la contraseña mediante la acción
	"Habilitar generar la contraseña de transacción", ubicada en la acción
	<a href="${pagePrefix}profiles#access_actions"><u>Administrar
	contraseñas</u></a> en la página de perfil del Miembro (esta configuración no
	tiene efecto si la contraseña de transacción no es utilizada).</li>
</ul>
<hr class="help">
</span> <span class="admin"> <a name="group_notification_settings"></a>
<h3>Configuración de notificaciones</h3>
Aquí son definidas las configuraciones referentes a las notificaciones
del grupo en el sistema, y los parámetros principales del canal SMS.<br>
<ul>
	<li><b>Notificaciones por defecto enviadas por correo
	electrónico:</b> Aquí usted puede definir las notificaciones vía correo
	electrónico por defecto que los miembros del grupo recibirán al
	producirse determinados eventos del sistema.<br>
	Dichas notificaciones son establecidas por defecto para el grupo; pero
	cada miembro en su configuración personal (Menú: Preferencias>
	Nofificaciones) podrá deshabilitarlas/habilitarlas.</li>
	<li><b>Notificaciones permitidas a ser enviadas por SMS:</b>
	Habilita las notificaciones mediante mensajes SMS de determinados
	eventos del sistema.<br>
	Nota: Esta opción estará disponible solamente si el canal SMS se
	encuentra activado (Menú: Configuración> Canales).</li>
	<li><b>Notificaciones por defecto enviadas por SMS:</b> Aquí usted
	puede definir las notificaciones vía SMS por defecto que los miembros
	del grupo recibirán al producirse determinados eventos del sistema.<br>
	Dichas notificaciones son establecidas por defecto para el grupo; pero
	cada miembro en su configuración personal (Menú: Preferencias>
	Nofificaciones) podrá deshabilitarlas/habilitarlas.</li>
	<li><b>Tipo de transferencia usado para cobrar los SMS:</b> Indica
	el Tipo de transacción con el cual se realizará el cobro de los cargos
	correspondientes a los mensajes SMS consumidos por los miembros del
	grupo.</li>
	<li><b>Usar una clase para personalizar el contexto de SMS:</b> Si
	esta opción es seleccionada, podrá definirse una clase de Java
	específica (programación especial) para personalizar el contexto y
	modelo de costos SMS. Las siguientes opciones desaparecerán y sólo será
	desplegada la nueva opción "Nombre de clase para personalizar el
	contexto de SMS", dónde se deberá ingresar el nombre de la clase
	correspondiente.</li>
	<li><b>Cantidad de SMS sin cargo por mes:</b> Indica la cantidad
	de SMS sin costo con los que contarán los miembros del grupo. Dichos
	cantidad de mensajes SMS, no es acumulable y renovable al inicio de
	cada mes.</li>

	<li><b>Mostrar SMS sin cargo solo si menos de:</b> En las
	notificaciones (Menú: Preferencias> Notificaciones) de los miembros del
	grupo, se mostrará la cantidad de mensajes SMS sin cargo disponibles,
	sólo si dicha cantidad es menor a la aquí indicada.</li>

	<li><b>Tamaño del paquete de SMS adicionales:</b> Indica el tamaño
	(cantidad) del paquete de mensajes SMS adicionales. Este paquete será
	aplicado una vez que el miembro no posea saldo disponible de SMS sin
	cargo y haya configurado en sus preferencias personales la aceptación
	del cobro de paquetes de SMS adicionales.</li>
	<li><b>Costo del paquete adicional:</b> Es el costo del paquete de
	mensajes SMS adicionales.</li>
	<li><b>Período de validez del paquete adicional:</b> Es el período
	de validez de los mensajes que componen el paquete de SMS adicionales.
	</li>
</ul>
La ayuda sobre <a
	href="${pagePrefix}notifications#notification_preferences"><u>preferencias
de notificaciones</u></a> le brindará información más específica sobre las
opciones descriptas.
<hr class="help">
</span> <span class="admin"> <a name="group_ad_settings"></a>
<h3>Configuración de anuncios</h3>
Esta configuración define el comportamiento de grupo con respecto a los
anuncios en el sistema.<br>
La configuración es parte del formulario de "<a
	href="#edit_member_group"><u>modificar miembro de grupo</u></a>". Las
siguientes opciones están disponibles:
<ul>
	<li><b>Número máximo de anuncios por miembro:</b> Cantidad máxima
	de anuncios que un miembro puede publicar.</li>
	<li><b>Habilitar anuncios permanentes:</b> Si esta opción es
	seleccionada, podrán definirse <a
		href="${pagePrefix}advertisements#ad_modify"><u>anuncios</u></a>
	permanentes (que no caducan) para el grupo.</li>
	<li><b>Tiempo de publicación por defecto:</b> Es el período por
	defecto de publicación de los nuevos anuncios. Cada miembro puede
	posteriormente modificar este período para cada anuncio.
	<li><b>Tiempo máximo de publicación:</b> Es el período máximo de
	publicación que un miembro le puede establecer a un anuncio.</li>
	<li><b>Publicación de anuncio externo</b>: Esta opción habilita la
	publicación externa de anuncios; significa que los anuncios de este
	grupo pueden ser publicados automáticamente en el sitio Web de la
	organización y por lo tanto visible para no miembros.<br>
	Las opciones son auto explicativas y obvias. "Permitir opción"
	significa que el miembro puede elegir si su anuncio podrá ser utilizado
	para su publicación externa.</li>
	<li><b>Número máximo de imágenes por anuncio:</b> Cantidad máxima
	de imágenes que el miembro puede agregar por anuncio.</li>
	<li><b>Tamaño máximo de descripción del anuncio</b>: Es el tamaño
	máximo en bytes/caracteres que puede poseer la descripción del anuncio.</li>
</ul>
<hr class="help">

</span> 
<span class="admin"> 
<a name="group_scheduled_payment_settings"></a>
<h3>Configuración de pagos para el grupo</h3>
Son las configuraciones del grupo relacionadas a los <a
	href="${pagePrefix}payments#scheduled"><u>pagos agendados</u></a>.<br>
Esta configuración es parte del formulario "<a href="#edit_member_group"><u>modificar
grupo miembro</u></a>".
<br><br>Todas estas configuraciones refieren a los <a
	href="${pagePrefix}payments#pay_scheduled"><u>pagos agendados</u></a>
en el sistema. Las siguientes opciones están disponibles:
<ul>
	<li><b>Cantidad máxima de pagos agendados:</b> Es la cantidad
	máxima de cuotas en las cuales un pago puede ser dividido y agendado.
	Por ejemplo: Un máximo de 12 cuotas.</li>
	
	<li><b>Período máximo para pagos agendados:</b> Es el período de
	tiempo máximo entre la fecha actual y el último pago (cuota).</li>
	
	<li><b>Mostrar campo de descripción en el pago POSWeb:</b>
	Si esta opción es seleccionada, se mostrará un campo de descripción del pago en la página de POSWeb	
	para los usuarios que se encuentren conectados en la página de POSWeb.</li>
	
	<li><b>Ocultar moneda (solo se muestra para tipos de transacción):</b> 
	En los casos que se utilizan varias monedas para mas de un tipo de transacción
	puede ser confuso para el usuario si tiene que elegir primero la moneda
	y después el tipo de transacción. Si se utiliza esta configuración no
	se visualizarán las opciones de moneda. Solo se podrá seleccionar el tipo de transacción
	de una lista desplegable. Es recomendable por tanto, que el nombre de un tipo de transacción 
	deje en claro el objetivo y la moneda que utiliza.</li>
	
</ul>
<hr class="help">

<a name="group_loans_settings"></a>
<h3>Configuración de préstamos para el grupo</h3>
Son las configuraciones del grupo relacionadas con los <a
	href="${pagePrefix}loans"><u>préstamos</u></a> en el sistema.<br>
Esta configuración es parte del formulario "<a href="#edit_member_group"><u>modificar
miembro de grupo</u></a>".<br>
Las siguientes opciones están disponibles:
<ul>
	<li><b>Ver préstamos dados al grupo:</b> Si esta opción es
	seleccionada, le permite al miembro visualizar los <a
		href="${pagePrefix}loans"><u>préstamos</u></a> dados a su <a
		href="${pagePrefix}loan_groups"><u>grupo de préstamo</u></a>.
	<li><b>Se permite el pago del préstamo por cualquier miembro
	del grupo:</b> Si esta opción es seleccionada, cualquier miembro del grupo
	de préstamo puede pagar (reembolsar) un préstamo.<br>
	Si la opción no es seleccionada, solamente el miembro responsable del
	préstamo puede pagar (reembolsar) el préstamo.
</ul>
<hr class="help">
</span> <span class="admin"> <a name="group_brokering_settings"></a>
<h3>Configuración para el grupo de broker</h3>
Son configuraciones relacionadas con la función de <a
	href="${pagePrefix}brokering"><u>brokering</u></a> del grupo . Esta
configuración es parte del formulario <a href="#edit_member_group"><u>modificar
grupo miembro</u></a>.<br>
Las siguientes opciones están disponibles:
<ul>
	<li><b>Grupos iniciales posibles:</b> Aquí usted puede seleccionar
	el o los posibles grupos iniciales de los miembros que son registrados
	por un broker.<br>
	Esto puede variar de acuerdo a la utilización de los brokers en su
	organización. Puede tratarse de un grupo inactivo pendiente de
	activación por parte de un Administrador, o un grupo activo.<br>
	Si selecciona uno o más grupos, al momento de registrar un miembro, se
	le presentará al broker una lista con grupos disponibles para su
	selección.<br>
	Tenga en cuenta que los brokers NO podrán registrar miembros, si no
	existe seleccionado uno o más grupos aquí.
</ul>
<hr class="help">
</span> <span class="admin"> <a name="edit_broker_group"></a>
<h3>Modificar grupo broker</h3>
Aquí usted puede modificar las propiedades de un <a
	href="#broker_groups"><u>grupo Broker</u></a>.<br>
Luego de hacer click en el botón "Modificar", puede cambiar el Nombre,
la Descripción y varias categorías de configuraciones del grupo.
<br><br>Advierta que aquí no es posible configurar permisos; propiedades
y configuración de un grupo no son lo mismo que sus permisos.<br>
<br>
Puede modificar los permisos del grupo, haciendo click en el ícono de
permisos <img border="0" src="${images}/permissions.gif" width="16"
	height="16"> desde la <a href="#manage_groups"><u>administración
de grupos</u></a>, o utilizando el botón de atajo "Permisos de Grupo" ubicado en
la parte inferior izquierda de esta ventana.
<br><br>Las configuraciones del grupo Broker son ordenadas por categoría.<br>
Las siguientes categorías están disponibles; usted puede hacer click en
los enlaces correspondientes para obtener más detalles sobre los campos
existentes en cada categoría:
<ul>
	<li><b><a href="#group_details"><u>Detalles de grupo:</u></a></b>
	Proporciona el resumen (detalles) del grupo.
	<li><b><a href="#group_registration_settings"><u>Configuración
	de registro:</u></a></b> Son las configuraciones que definen el comportamiento del
	grupo en relación al registro de sus miembros en el sistema. Contiene
	también otras configuraciones.
	<li><b><a href="#group_access_settings"><u>Configuración
	de acceso:</u></a></b> Son las configuraciones referentes a los accesos del grupo
	en el sistema.
	<li><b><a href="#group_notification_settings"><u>Configuración
	de notificaciones:</u></a></b> Todas las configuraciones relacionadas con las
	notificaciones asociadas al grupo.
	<li><b><a href="#group_brokering_settings"><u>Configuración
	de brokers:</u></a></b> Son las configuraciones relacionadas al brokering de otros
	miembros.
	<li><b><a href="#group_ad_settings"><u>Configuración
	de anuncios:</u></a></b> Son las configuraciones que definen el comportamiento del
	grupo en relación a los anuncios en el sistema.
	<li><b><a href="#group_scheduled_payment_settings"><u>Configuración
	pagos agendados:</u></a></b> Son las configuraciones referentes a los <a
		href="${pagePrefix}payments#scheduled"><u>pagos agendados</u></a> del
	grupo.
	<li><b><a href="#group_loans_settings"><u>Configuración
	de préstamos:</u></a></b> Son las configuraciones que definen el comportamiento del
	grupo en relación a los préstamos.
</ul>
<hr class="help">
</span> <span class="admin"> <a name="insert_group"></a>
<h3>Agregar nuevo grupo</h3>
Esta ventana le permite crear nuevos grupos de usuarios.<br>
Las siguientes opciones están disponibles:
<ul>
	<li><b>Tipo:</b> Indica el tipo o <a href="#group_categories"><u>categoría</u></a>
	del grupo a crear. Puede ser del tipo "Miembro", "Broker" o
	"Administrador".
	<li><b>Eliminado:</b> Cuando el grupo es marcado como "eliminado",
	significa que los miembros de este grupo han abandonado el sistema. Una
	vez que un miembro fue movido a un grupo "eliminado", no podrá ser
	movido a otro grupo.<br>
	La información seguirá disponible en la base de datos y será visible
	para los administradores, simple y únicamente como respaldo.
	<li><b>Nombre:</b> Nombre del grupo, el cual será mostrado a los
	usuarios.
	<li><b>Descripción:</b> Descripción del grupo.
	<li><b>Copiar configuración de:</b> Usted puede copiar todas las
	configuraciones para este nuevo grupo de uno ya existente, mediante
	esta lista desplegable. La configuración y permisos del grupo
	seleccionado serán copiados.
</ul>
Luego de haber ingresado la información requerida, haga click en el
botón "Aceptar" para guardar los cambios.<br>
<br>
Importante: Luego de crear un nuevo grupo de usuarios, usted debe
configurar sus permisos y propiedades a través de la página de <a
	href="#manage_groups"><u>administración de grupos</u></a>.
<br><br>Nota: Luego de crear un grupo no es posible cambiar el tipo ni el
estado del grupo.
<hr class="help">
</span> <span class="admin"> <a name="manage_group_accounts"></a>
<h3>Cuentas del grupo (Administrar)</h3>
Los <a href="#member_groups"><u>grupos de miembro</u></a> (y los <a
	href="#broker_groups"><u>grupos de broker</u></a>) pueden poseer varias
<a href="${pagePrefix}account_management#accounts"><u>cuentas</u></a>
asociadas. <br>
<br>
La lista que se muestra a continuación contiene los tipos de cuenta de
miembro, asociadas al grupo.<br>
Las cuentas en la lista serán las desplegadas en la vista general de las
cuentas de los miembros (Menú de miembro: Cuentas> Información de
cuentas) pertenecientes a este grupo.<br>
Un tipo de cuenta puede ser compartida por diferentes grupos, lo cual
significa que diferentes grupos de usuarios pueden tener asociada el
mismo tipo de cuenta. En estos casos, sin embargo, es posible definir
diferentes configuraciones de cuentas y permisos para cada grupo.
<br><br>
<ul>
	<li>Usted puede <b>Modificar</b> la configuración de las cuentas,
	haciendo click en el ícono de Modificación <img border="0"
		src="${images}/edit.gif" width="16" height="16">
	correspondiente.<br>
	<br>
	Nota: Con solo crear o modificar un cuenta no es suficiente para
	realizar o recibir pagos. Deberán también configurarse los <a
		href="#permissions"><u>permisos</u></a> del grupo, o la cuenta no
	podrá ser utilizada.</li>
	<br>
	<li>Es posible <b>Eliminar</b> un tipo de cuenta del grupo,
	haciendo click en el ícono de Eliminación <img border="0"
		src="${images}/delete.gif" width="16" height="16">
	correspondiente.<br>

	Si una cuenta es "eliminada" del grupo, significa que la cuenta seguirá
	siendo visible por los usuario (así como las transacciones de la
	misma), pero estará en estado "inactiva". No podrá ser utilizada para
	realizar pagos.</li>
	<br>
	<li>Usted puede <b>Asociar</b> un nuevo tipo de cuenta al grupo,
	haciendo click en el botón "Asociar nueva cuenta" ubicado en la parte
	inferior derecha de la ventana. Se desplegará la ventana de <a
		href="#insert_group_account"><u>Agregar una cuenta al grupo</u></a>.</li>
</ul>

<hr class="help">
</span> <span class="admin"> <a name="edit_group_account"></a> <a
	name="insert_group_account"></a>
<h3>Modificar la configuración de la cuenta / Agregar una cuenta al
grupo</h3>
En esta página usted puede definir la configuración de la cuenta para el
tipo de cuenta del grupo de usuarios seleccionado.<br>
Es posible realizar esta acción sobre tipos de cuenta ya asociadas al
grupo, o para un tipo de cuenta que se desea asociar al grupo por
primera vez.<br>
<ul>
	<li>Para <b>Modificar</b> una cuenta existente, haga click en el
	botón "Modificar";</li>
	<br>
	<li>Para <b>Agregar</b> un nuevo tipo de cuenta, ingrese los
	campos requeridos directamente.</li>
</ul>
Al finalizar, haga click en el botón "Aceptar" para guardar los cambios.<br>
<br>
Las siguientes opciones pueden ser definidas:
<ul>
	<li><b>Cuenta:</b> Indica el Tipo de cuenta que desea asociar. No
	puede ser modificada una vez que fue creada, por lo cual es una opción
	únicamente disponible para nuevos tipos de cuenta.<br>
	Sólo puede ser asociado un tipo de cuenta creado previamente. De lo
	contrario, deberá primero crear el tipo de cuenta apropiado, para luego
	asociarlo al grupo de miembros.
	<li><b>Por defecto:</b> Como es posible asociar más de una cuenta
	de miembro, el sistema necesita saber que cuenta será utilizada por
	defecto. Esto es por dos motivos. Primero, el sistema puede ser
	configurado para el envío de correos electrónicos informando el saldo
	de cuenta por defecto. Segundo, los pagos realizados mediante teléfono
	celular podrían utilizar la cuenta establecida por defecto.

	<li><b>Exigir contraseña de transacción:</b> Si esta opción es
	seleccionada, se le exigirá al miembro ingresar su contraseña de
	transacción para realizar transacciones. <br>
	Nota: Esta opción se encontrará activa solamente si la utilización de
	la contraseña de transacción es habilitada para el grupo (configuración
	del grupo), de lo contrario este parámetro no será visible.

	<li><b>Ocultar si no hay límite de crédito:</b> Para algunos
	productos financieros usted podría no querer mostrar una cuenta de
	miembro, si NO existe definido un límite de crédito individual.<br>
	Por ejemplo, cuando una cuenta adicional de miembro se utiliza para
	pagos con tarjeta de crédito. Normalmente este tipo de cuenta posee un
	límite de crédito negativo, que el miembro puede utilizar para los
	pagos con tarjeta; pero podrían no todos los miembros del grupo tener
	definido de forma individual un límite de crédito negativo, y usted no
	querer mostrar la cuenta para estos miembros.<br>
	<br>
	Por supuesto esto puede ser resuelto mediante la creación de un grupo
	separado para "Miembros de tarjeta de crédito" y asignar la cuenta al
	nuevo grupo, pero esto implicará una mayor complejidad en sus
	definiciones. Con el fin de evitar crear grupos diferentes, es posible
	que una cuenta sólo se muestre si los miembros poseen un límite de
	crédito definido. <br>
	Por supuesto esto es aplicable a límites de crédito individuales. Si
	todo el grupo posee un límite mínimo de crédito, la cuenta deberá
	mostrarse para todos y establecer esta opción no sería necesario.<br>
	<br>
	Nota: La regla general en Cyclos es que cualquier cuenta con
	transacciones se mostrará y anulará esta opción. Esto significa que en
	el caso de que esta opción se establezca para una cuenta y un miembro
	que no tiene definido un límite de crédito, pero posee transacciones en
	esta cuenta, la cuenta se mostrará de todas formas.

	<li><b>Límite mínimo de crédito:</b> Indica el límite mínimo de
	crédito por defecto de la cuenta. El monto puede ser menor o igual a
	cero. Modificar el valor de este campo afectará las cuentas existentes,
	dependiendo de la casilla de verificación "Actualizar límite de crédito
	para los miembros existentes". <br>
	Aparte del límite mínimo de crédito por defecto válido para todo el
	grupo, también es posible configurar un <a
		href="${pagePrefix}account_management#credit_limit"><u>límite
	de crédito</u></a> para miembros individuales. La configuración individual
	sobrescribe el límite de crédito establecido del grupo.

	<li><b>Límite máximo de crédito:</b> Definir el límite máximo de
	crédito significa que la cuenta no podrá recibir más pagos cuando se
	haya alcanzado dicho límite. El usuario pagador recibirá un mensaje
	informándole que no se ha podido realizar el pago.

	<li><b>Actualizar límite de crédito para los miembros
	existentes:</b> Si se actualizó (modificó) los límites de crédito de un
	tipo de cuenta asociada, usted puede definir seleccionando esta opción
	si los límites de crédito de los miembros existentes (límites de
	crédito individuales y grupales) deberán ser actualizados. Si no
	selecciona esta opción, los nuevos límites de crédito serán aplicados
	sólo a los nuevos miembros del grupo.

	<li><b>Crédito inicial:</b> Monto que los nuevos miembros del
	grupo recibirán inicialmente y de forma automática para esta cuenta.
	Puede ser un valor igual o mayor a cero.

	<li><b>Tipo de transacción crédito inicial:</b> Si se ingresa un
	"crédito inicial" (parámetro anterior) distinto de 0, usted deberá
	especificar el <a
		href="${pagePrefix}account_management#transaction_types"><u>tipo
	de transacción</u></a> correspondiente para este crédito, seleccionándolo de la
	lista desplegable. <br>
	Como el tipo de transacción posee una cuenta origen y una cuenta
	destino, esto le permite especificar de que cuenta inicial es tomado el
	monto del crédito. La base de datos por defecto posee un tipo de
	transacción "Crédito inicial", definido de la cuenta de Débito del
	sistema a una cuenta de Miembro, pero por supuesto es posible
	configurar la utilización de otro tipo de transacción.

	<li><b>Alerta de mínimo de unidades:</b> Si una cuenta de miembro
	alcanza el monto definido en este punto, un mensaje de alerta personal
	(siguiente parámetro) será enviado al miembro. <br>
	Aquí usted puede ingresar solamente un monto positivo, el cual es
	relativo al límite de crédito. Esto significa que: si el límite de
	crédito es definido en -200 y este campo (mínimo de unidades) es 10, el
	miembro recibirá una alerta cuando el saldo sea de -190.</b> Si el límite
	de crédito es 0 y el mínimo de unidades es 10, el miembro recibirá una
	alerta cuando el saldo sea de 10.

	<li><b>Mensaje de mínimo de unidades:</b> Este es el mensaje
	(personal) que recibirá el miembro cuando el "mínimo de unidades"
	(parámetro anterior) es alcanzado.
</ul>
<hr class="help">
</span> <span class="admin"> <a name="manage_group_customized_files"></a>
<h3>Archivos personalizados (Administrar)</h3>
Cyclos es altamente personalizable.<br>
No solo le permite a usted definir para el sistema sus propios estilos y
textos a ser visualizados mediante la <a
	href="${pagePrefix}content_management"><u>"Gestión de
contenido"</u></a>, sino que además puede realizar personalizaciones a nivel de
grupo de usuarios. <br>
Esto significa que cada grupo puede poseer su propio estilo y sus
propios textos dentro de la aplicación.<br>
Estos son los llamados "Archivos personalizados".
<br><br>Esta ventana ofrece una vista general de los archivos
personalizados del grupo.<br>
Si no existe ningún archivo personalizado para el grupo, un mensaje es
mostrado, indicando que el grupo no posee archivos personalizados.<br>
<br>
Las siguientes opciones están disponibles:
<ul>
	<li><b>Insertar</b> un nuevo archivo personalizado, a través del
	botón "Modificar nuevo archivo personalizado". Un archivo personalizado
	de grupo sobrescribe a uno del sistema (que puede configurarse en la <a
		href="${pagePrefix}content_management"><u>"Gestión de
	contenido"</u></a>).</li>

	<li><b>Modificar</b> un archivo personalizado existente, mediante
	el ícono de Modificación <img border="0" src="${images}/edit.gif"
		width="16" height="16">&nbsp; correspondiente.</li>

	<li><b>Visualizar</b> el resultado para un miembro del grupo,
	obteniendo una vista previa del archivo personalizado, mediante el
	ícono de Visualización <img border="0" src="${images}/view.gif"
		width="16" height="16">&nbsp; correspondiente.</li>

	<li><b>Eliminar</b> un archivo personalizado, mediante el ícono de
	Eliminación <img border="0" src="${images}/delete.gif" width="16"
		height="16">&nbsp; correspondiente.<br>
	Cuando un archivo personalizado de grupo es eliminado, vuelve a ser
	utilizado el archivo personalizado del sistema por defecto (si se
	encuentra disponible).</li>
</ul>
<hr class="help">
</span> <span class="admin"> <a name="customize_group_file"></a>
<h3>Modificar el archivo personalizado para ... / Personalizar
nuevo archivo para ...</h3>
En esta página usted puede personalizar un archivo Estático o Estilo (de
tipo CSS).<br>
Trabaja de la misma forma que en la <a
	href="${pagePrefix}content_management"><u>Gestión de Contenido</u></a>,
pero aquí se especifica solamente para este grupo.<br>
<br>
Para obtener instrucciones más específicas, haga click <a
	href="${pagePrefix}content_management#edit_customized_file"><u>aquí</u></a>
<br><br>Nota: Si desea incluir fotos en el archivo estático, las mismas
deberán ser <a href="${pagePrefix}content_management#custom_images"><u>cargadas</u></a>.

</span>

<hr>

<a name="permissions"></a>
<h2>Permisos</h2>
En Cyclos, los permisos son organizados por grupo.<br>
Por cada grupo usted puede administrar los permisos para acceder a las
distintas funcionalidades de la aplicación. Normalmente, las secciones
de Cyclos a las que un grupo de miembros no puede acceder, serán
invisibles para todos los miembros de ese grupo. <br>

<span class="admin"> El sistema es muy flexible, permitiendo por
ejemplo, crear diferentes grupos de administradores, donde cada grupo
(de administradores) tiene permisos para administrar grupos de miembros
específicos.<br>

Para sistemas más complejos es posible crear una administración extra
"vertical", para definir dominios de tareas específicas: como
administrador de cuenta, de sistema, de contabilidad, de vales, etc. 
Se pueden modificar los permisos del grupo en el "Menú: Grupos> Permisos
de Grupos", y luego hacer click en el ícono de permisos <img border="0"
	src="${images}/permissions.gif" width="16" height="16">&nbsp;. </span>

<hr>
<a name="manage_group_permissions_basic"></a>
<h3>Permisos básicos</h3>
En esta ventana se pueden configurar los permisos básicos del grupo. <br>
Estos permisos son iguales para todas las <a href="#group_categories"><u>categorías
de grupos</u></a> (Miembro, Administrador, Broker) del sistema. <br>
Estos permisos básicos no afectan otras funciones. Por ejemplo, si un
miembro no puede acceder al sistema, de todos modos podrá aún seguir
recibiendo pagos.<br>
<br>
Los siguientes permisos pueden ser configurados:
<ul>
	<li><b>Conectarse</b>: Si esta opción es seleccionada, los
	miembros de este grupo podrán acceder al sistema.

	<li><b>Invitar miembro</b>: Si esta opción es seleccionada, los
	miembros de este grupo podrán visualizar una ventana en su página
	principal (luego de ingresar al sistema), en donde podrán invitar a
	amigos a <a href="${pagePrefix}home#home_invite"><u>conocer su
	organización</u></a>.

	<!--
	<li><b>Mostrar acceso rápido</b>: Si esta opción (sólo disponible
	para grupos de miembros) es seleccionada, se mostrará en la página
	principal una ventana con íconos de acceso directo a las funciones más
	utilizadas del sistema.
	-->
	
</ul>
<hr class="help">

<span class="admin"> <a
	name="manage_group_permissions_admin_system"></a>
<h3>Permisos de administración de sistema</h3>
En esta ventana se pueden configurar los permisos correspondientes a las
funciones del sistema para un grupo Administrador.<br>
Esta ventana no contiene permisos relacionados a grupos de Miembro.<br>
Para realizar cambios, primero debe desplazarse a la parte inferior de
la ventana y hacer click en el botón "Modificar".<br>
Para guardar los cambios efectuados, haga click en el botón "Aceptar",
ubicado también al pie de la ventana.
<br><br>La mayoría de las funciones poseen los permisos de <b>Ver</b> y <b>Administrar</b>:
<ul>
	<a name="view_permissions_admin"></a>
	<li><b>Ver:</b> Si esta casilla de verificación es seleccionada,
	la opción será mostrada en el menú para los administradores del grupo.</li>

	<a name="manage_permissions_admin"></a>
	<li><b>Administrar:</b> Si esta casilla es seleccionada, se le
	otorgarán al administrador los permisos para <i>Crear, Modificar y
	Eliminar</i>.</li>
</ul>

Los siguientes permisos están disponibles (puede utilizar los enlaces
para obtener más información):

<ul>
	<li><b>Administrar grupos:</b> Permite <a href="#manage_groups"><u>administrar</u></a>
	las diferentes <a href="#group_categories"><u>categorías de
	grupo</u></a>.
	<ul>
		<li><b>Administrar grupo miembro:</b> Habilita la gestión para la
		categoría de grupo miembro.</li>
		<li><b>Administrar grupo broker:</b> Habilita la gestión para la
		categoría de grupo broker.</li>
		<li><b>Administrar grupo administrador:</b> Habilita la gestión
		para la categoría de grupo administrador.</li>
	</ul>
	<br>
	
	<li><b>Alertas:</b> Las <a
		href="${pagePrefix}alerts_logs#system_alerts"><u>alertas</u></a> son
	advertencias del sistema producidas en ocasiones (eventos) especiales.<br>
	Los permisos disponibles son:
	<ul>
		<li><b>Administrar alertas de miembro:</b> Permite al
		administrador gestionar las alertas de miembro.</li>
		<li><b>Administrar alertas de sistema:</b> Permite al
		administrador gestionar las alertas de sistema.</li>
		<li><b>Ver alertas de miembro:</b> Permite al administrador
		visualizar las alertas de miembro.</li>
		<li><b>Ver alertas de sistema:</b> Permite al administrador
		visualizar las alertas de sistema.</li>
	</ul>
	<br>
	
	<li><b>Archivos personalizados a nivel de sistema:</b> Permite al
	administrador configurar <a href="${pagePrefix}content_management"><u>archivos
	personalizados a nivel de sistema</u></a> (diferentes a los archivos
	personalizados por grupo).
	<ul>
		<li><b><a href="#manage_permissions_admin"><u>Administrar</u></a></b></li>
		<li><b><a href="#view_permissions_admin"><u>Ver</u></a></b></li>
	</ul>
	<br>
	Son afectadas las siguientes opciones dentro del "Menú: Gestión de
	Contenido":<br>
	<ul>
		<li>Archivos estáticos</li>
		<li>Archivos de ayuda</li>
		<li>Archivos CSS</li>
		<li>Páginas de aplicación</li>
	</ul>
	<br>
	
	<li><b>Campos personalizados:</b> Permite al administrador
	gestionar los <a href="${pagePrefix}custom_fields"><u>campos
	personalizados</u></a>.
	<ul>
		<li><b><a href="#manage_permissions_admin"><u>Administrar</u></a></b></li>
		<li><b><a href="#view_permissions_admin"><u>Ver</u></a></b></li>
	</ul>
	<br>
	
	<li><b>Canales:</b> Permite al administrador ver y/o gestionar los
	<a href="${pagePrefix}settings#channels"><u>canales</u></a> por los que
	el usuario accede a Cyclos (Por ejemplo: Web, SMS, etc.).
	<ul>
		<li><b><a href="#manage_permissions_admin"><u>Administrar</u></a></b></li>
		<li><b><a href="#view_permissions_admin"><u>Ver</u></a></b></li>
	</ul>
	<br>
	
	<li><b>Cargos:</b> Esta sección define los permisos
	correspondientes a los <a
		href="${pagePrefix}account_management#account_fee_overview"><u>cargos
	de cuenta</u></a> en el sistema.
	<ul>
		<li><b>Ver:</b> Permite al administrador visualizar los cargos de
		cuenta existentes.</li>
		<li><b>Cargo:</b> Permite al administrador ejecutar cargos de
		cuenta, manuales o automáticos que hayan fallado, a través de la
		página del <a
			href="${pagePrefix}account_management#account_fee_history"><u>historial
		de cargos de cuenta</u></a>.</li>
	</ul>
	<br>
	
	<li><b>Categorías de anuncios:</b> Esta sección establece los
	permisos sobre las <a href="${pagePrefix}advertisements#categories"><u>categorías
	de anuncios</u></a>.
	<ul>
		<li><b><a href="#manage_permissions_admin"><u>Administrar</u></a></b></li>
		<li><b><a href="#view_permissions_admin"><u>Ver</u></a></b></li>

		<li><b>Exportar/importar archivo:</b> Permite al administrador <a
			href="${pagePrefix}advertisements#import_export"><u>exportar
		o importar</u></a> categorías de anuncios a través de un archivo en formato
		XML.</li>
	</ul>
	<br>
	
	<li><b>Categorías de mensaje:</b> Permite al administrador ver y/o
	gestionar las diferentes categorías de <a href="${pagePrefix}messages"><u>mensajes</u></a>
	en Cyclos.
	<ul>
		<li><b><a href="#manage_permissions_admin"><u>Administrar</u></a></b></li>
		<li><b><a href="#view_permissions_admin"><u>Ver</u></a></b></li>
	</ul>
	<br>
	
	<li><b>Clientes de web services:</b> Permite ver y/o gestionar los
	niveles de acceso para que aplicaciones externas se conecten a los <a
		href="${pagePrefix}settings#web_services_clients"><u>web
	services</u></a> de Cyclos.
	<ul>
		<li><b><a href="#manage_permissions_admin"><u>Administrar</u></a></b></li>
		<li><b><a href="#view_permissions_admin"><u>Ver</u></a></b></li>
	</ul>
	<br>

	<li><b>Configuración:</b> Permite al administrador el acceso al
	menú de configuración.<br>
	<ul>
		<li><b><a href="${pagePrefix}settings#local"><u>Configuración local</u></a></b>
		<li><b><a href="${pagePrefix}settings#alerts"><u>Configuración de alertas</u></a></b>
		<li><b><a href="${pagePrefix}settings#access"><u>Configuración de acceso</u></a></b>
		<li><b><a href="${pagePrefix}settings#mail"><u>Configuración de mensajería</u></a></b>
		<li><b><a href="${pagePrefix}settings#log"><u>Configuración de archivo de registro</u></a></b>
		<li><b><a href="${pagePrefix}settings#settings"><u>Ver configuración</u></a>:</b> 
		Si esta opción no es seleccionada, el administrador no podrá visualizar el menú de configuración (Menú: Configuración).
		<li><b><a href="${pagePrefix}settings#import_export"><u>Exportar / importar a archivo</u></a></b>
		</li>
	</ul>
	<br>
	
	<li><b>Cuentas:</b> Esta sección permite configurar los permisos
	sobre las <a href="${pagePrefix}account_management#accounts"><u>cuentas</u></a>
	en el sistema.
	<ul>
		<li><b>Administrar cuentas:</b> Permisos para crear y modificar
		la <a href="${pagePrefix}account_management#account_search"><u>estructura
		de cuentas</u></a> del sistema.
		<li><b>Ver administración de cuentas:</b> El administrador puede
		visualizar la estructura de cuentas, pero no puede realizar cambios.
		<li><b>Ver Información de las cuentas del sistema:</b> Aquí puede
		seleccionar las cuentas del sistema que serán visibles para el
		administrador.
		<li><b>Ver pagos autorizados:</b> Ver menú de <a
			href="${pagePrefix}payments#authorized"><u>pagos autorizados</u></a>.
		<li><b>Ver pagos agendados:</b> Ver menú de <a
			href="${pagePrefix}payments#scheduled"><u>pagos agendados</u></a>.
	</ul>
	<br>
	
	<li><b>Cuentas externas:</b> Esta sección define los permisos
	correspondientes a las <a
		href="${pagePrefix}bookkeeping#bookkeeping_top"><u>cuentas
	externas</u></a> en el sistema.
	<ul>
		<li><b>Administrar:</b> Permite al administrador configurar el
		módulo, creando y modificando cuentas externas, definiendo campos y
		tipos de transacción.</li>
		<li><b>Ver:</b> Permite visualizar la configuración de las
		cuentas externas.</li>
		<li><b>Detalles:</b> Permite visualizar los pagos, pero no
		realizar ninguna acción sobre ellos.</li>
		<li><b>Procesar pago:</b> Permite procesar pagos externos.</li>
		<li><b>Verificar pago:</b> Permite verificar pagos externos.</li>
		<li><b>Administrar pago:</b> Permite administrar pagos externos.</li>
	</ul>
	<br>
	
	<li><b>Estado de Sistema:</b> Permite al administrador ver el
	estado del sistema.<br>
	<ul>
		<li><b>Ver estado de sistema:</b> Si esta opción es seleccionada,
		el administrador visualizará la ventana de <a
			href="${pagePrefix}home#home_status"><u>Estado del programa</u></a>,
		en su página inicial.
		<li><b>Ver administradores conectados:</b> Seleccione aquí los <a
			href="#admin_groups"><u>grupos de administradores</u></a>, para los
		cuales se podrá visualizar los administradores conectados, dentro de
		la ventana de <a href="${pagePrefix}user_management#connected_users"><u>usuarios
		Conectados</u></a>.
		<li><b>Ver brokers conectados:</b> Funciona de la misma forma. El
		administrador podrá visualizar sólo los brokers conectados de los <a
			href="#broker_groups"><u>grupos de brokers</u></a> sobre los cuales
		posea permisos.
		<li><b>Ver miembros conectados:</b> Funciona de la misma forma,
		pero es una única casilla de verificación para todos los <a
			href="#member_groups"><u>grupos de miembros</u></a>.<br>
		El administrador podrá visualizar solamente los miembros conectados de
		los grupos sobre los que posea permisos.
		<li><b>Ver operadores conectados:</b> Funciona de la misma forma.
		Muestra el operador y el miembro al cual pertenece.</li>
	</ul>
	<br>
	
	<li><b>Filtros de grupo:</b> Permite al administrador ver y/o
	gestionar los <a href="#group_filters"><u>filtros de grupo</u></a> en
	el sistema.
	<ul>
		<li><b><a href="#manage_permissions_admin"><u>Administrar</u></a></b></li>
		<li><b><a href="#view_permissions_admin"><u>Ver</u></a></b></li>
		<li><b>Administrar archivos personalizados:</b> Permite
		configurar archivos personalizados para un filtro de grupo, o sea,
		para un conjunto de grupos al mismo tiempo.</li>
	</ul>
	<br>

	<li><b>Grupos de administradores:</b> Esta sección otorga permisos
	sobre los otros grupos de administradores en el sistema.
	<ul>
		<li><b>Ver:</b> Si esta opción es seleccionada, el administrador
		puede visualizar diferentes grupos de administradores en su ventana de
		<a href="#manage_groups"><u>Administración de grupos</u></a>.

		<li><b>Administrar archivos personalizados:</b> Permite al
		administrador la gestión de <a href="#manage_group_customized_files"><u>archivos
		personalizados</u></a> para otros administradores.
	</ul>
	<br>

	<li><b>Grupos de préstamos:</b> Permite ver y/o gestionar <a
		href="${pagePrefix}loan_groups"><u>grupos de préstamos</u></a>.
	<ul>
		<li><b><a href="#manage_permissions_admin"><u>Administrar</u></a></b></li>
		<li><b><a href="#view_permissions_admin"><u>Ver</u></a></b></li>
	</ul>
	<br>

	<li><b>Imágenes personalizadas:</b> Permite al administrador
	gestionar las <a href="${pagePrefix}content_management#custom_images"><u>Imágenes
	personalizadas</u></a>.
	<ul>
		<li><b><a href="#manage_permissions_admin"><u>Administrar</u></a></b></li>
		<li><b><a href="#view_permissions_admin"><u>Ver</u></a></b></li>
	</ul>
	<br>
	Puede ser afectada la visibilidad de las siguientes opciones dentro del
	"Menú: Gestión de contenido":<br>
	<ul>
		<li>Imágenes del sistema</li>
		<li>Imágenes adaptadas</li>
		<li>Imágenes de plantilla</li>
	</ul>
	<br>
	
	<li><b>Log de errores:</b> Permite al administrador ver y/o
	gestionar el <a href="${pagePrefix}alerts_logs#error_log"><u>Log
	de errores</u></a> del sistema.
	<ul>
		<li><b><a href="#manage_permissions_admin"><u>Administrar</u></a></b></li>
		<li><b><a href="#view_permissions_admin"><u>Ver</u></a></b></li>
	</ul>
	<br>
	
	<li><b>Mensajes informativos:</b>
	Permite al administardor ver y/o administrar los 
	<a href="${pagePrefix}content_management#infotexts"><u>mensajes informativos (InfoTexts)</u></a> en el sistema.
	<ul>
		<li><b><a href="#manage_permissions_admin"><u>Administrar</u></a></b></li>
		<li><b><a href="#view_permissions_admin"><u>Ver</u></a></b></li>
	</ul>
	<br>
	
	<li><b>Monedas:</b> Permite al administrador ver y/o gestionar diferentes 
	<a href="${pagePrefix}account_management#currencies"><u>monedas</u></a> en Cyclos.
	<ul>
		<li><b><a href="#manage_permissions_admin"><u>Administrar</u></a></b></li>
		<li><b><a href="#view_permissions_admin"><u>Ver</u></a></b></li>
	</ul>
	<br>

	<li><b>Pagos de sistema:</b> Aquí se pueden asignar los permisos
	para los pagos desde <a
		href="${pagePrefix}account_management#standard_accounts"><u>cuentas
	de sistema</u></a>.
	<ul>
		<li><b>Pago al sistema:</b> El administrador puede realizar pagos
		de sistema utilizando los <a
			href="${pagePrefix}account_management#transaction_types"><u>tipos
		de transacción</u></a> seleccionados en la lista desplegable.
		<li><b>Autorizar:</b> Permiso para <a href="${pagePrefix}payments#authorized"><u>autorizar</u></a> pagos de sistema.
		<li><b>Cancelar:</b> Permiso para cancelar <a href="${pagePrefix}payments#scheduled"><u>pagos agendados</u></a>.
		<li><b>Devolver pago:</b> Con este permiso, el administrador
		puede efectuar la "Devolución" de un pago, lo que significa que una
		transacción opuesta al pago, será realizada sobre la misma cuenta.<br>
		En el caso de que el pago haya generado alguna otra transacción (por
		ejemplo: tasas de un préstamo) todas ellas serán anuladas y devueltas.<br>
		Puede definirse una tiempo máximo en el que una transacción puede ser
		anulada en la <a href="${pagePrefix}settings#local"><u>configuración
		local</u></a>.
		<li><b>Cancelar pago agendado:</b> Permiso para cancelar pagos agendados.
		<li><b>Bloquear pago agendado:</b> Permiso para bloquear pagos agendados.
	</ul>
	<br>

	<li><b>Reportes:</b> Esta sección establece los permisos sobre los
	<a href="${pagePrefix}reports#reports"><u>reportes</u></a> en el
	sistema.<br>
	Cada punto se corresponde con una opción del menú principal:<br>
	<ul>
		<li><b>Estado actual:</b> Permite visualizar un informe del <a
			href="${pagePrefix}reports#current_state"><u>estado actual</u></a>
		del sistema.
		<li><b>Listas de miembros:</b> Permite visualizar informes sobre
		<a href="${pagePrefix}reports#member_lists"><u>listas de
		miembros</u></a> e información asociada.
		<li><b>Búsqueda de mensajes SMS:</b> El sistema puede enviar
		mensajes SMS en diversas ocasiones y por diferentes motivos,
		dependiendo de su configuración. Este permiso le otorga al
		administrador acceso a los reportes que le brindan una visión general
		de los mensajes SMS enviados.
		<li><b>Estadística:</b> Permite visualizar los <a
			href="${pagePrefix}statistics"><u>informes estadísticos</u></a> del
		sistema.
		<li><b>Simulaciones:</b> Permite al administrador visualizar la
		configuración correspondiente a la <a
			href="${pagePrefix}reports#simulations"><u>simulación</u></a> de <a
			href="${pagePrefix}account_management#rates"><u>tasas</u></a>. Se
		aconseja establecer este permiso para los administradores si usted
		posee tasas habilitadas para las monedas.

		<li><b>Simulación de configuración de ratio-D</b>
		<li><b>Simulación de configuración de ratio-A</b>
		</li>
	</ul>
	<br>

	<li><b>Tareas administrativas:</b> Permite al administrador
	realizar tareas administrativas en el sistema.
	<ul>
		<li><b>Establecer disponibilidad del sistema:</b> Cuando esta
		opción es seleccionada, el administrador puede controlar la <a
			href="${pagePrefix}settings#system_availability"><u>disponibilidad
		del sistema</u></a>.</li>
		
		<li><b>Gestión de índices de búsqueda:</b> Cuando esta opción es
		seleccionada, el administrador puede gestionar los <a
			href="${pagePrefix}settings#search_indexes"><u>índices de
		búsqueda</u></a> del sistema.</li>
	</ul>
	<br>

	<li><b>Temas:</b> Permite administrar los <a
		href="${pagePrefix}content_management#themes"><u>Temas</u></a>,
	ubicados en el menú <a href="${pagePrefix}content_management"><u>Gestión
	de contenido</u></a>.<br>
	Los permisos disponibles son:
	<ul>
		<li><b>Seleccionar:</b> Permite seleccionar/establecer un tema en el sistema.
		<li><b>Eliminar:</b> Permite eliminar temas del sistema.
		<li><b>Importar:</b> Permite importar temas en Cyclos.
		<li><b>Exportar:</b> Permite exportar temas a un archivo.
	</ul>
	<br>

	<li><b>Tipos de Garantía:</b> Permite al administrador ver y/o
	gestionar los <a href="${pagePrefix}guarantees"><u>tipos de
	garantías</u></a> en el sistema (módulo de garantías).
	<ul>
		<li><b><a href="#manage_permissions_admin"><u>Administrar</u></a></b></li>
		<li><b><a href="#view_permissions_admin"><u>Ver</u></a></b></li>
	</ul>
	<br>
	
	<li><b>Tipos de registro de miembro:</b> Permite ver y/o gestionar
	<a href="${pagePrefix}member_records"><u>tipos de registro de
	miembro</u></a> en el sistema.
	<ul>
		<li><b><a href="#manage_permissions_admin"><u>Administrar</u></a></b></li>
		<li><b><a href="#view_permissions_admin"><u>Ver</u></a></b></li>
	</ul>
	<br>

	<li><b>Tipos de tarjeta:</b> Esta sección define los permisos
	correspondientes a los <a
		href="${pagePrefix}access_devices#list_card_type"><u>tipos de
	tarjeta</u></a> en el sistema.
	<ul>
		<li><b>Gestión de tipos de tarjetas:</b> Permite al administrador
		gestionar los tipos de tarjetas.</li>
		
		<li><b>Ver tipos de tarjeta:</b> Permite al administrador
		visualizar los tipos de tarjetas existentes.</li>
	</ul>
	<br>

	<li><b>Traducción:</b> Permite acceder al menú de <a
		href="${pagePrefix}translation"><u>Traducción</u></a> (menú
	principal), donde se pueden visualizar y/o administrar las traducciones
	correspondientes a su lenguaje.<br>
	Los permisos disponibles son:
	<ul>
		<li><b><a href="#manage_permissions_admin"><u>Administrar</u></a></b>.
		<li><b><a href="#view_permissions_admin"><u>Ver</u></a></b>.
		<li><b>Importar / exportar a un archivo:</b> Permite importar o exportar las traducciones del sistema a un archivo.
		<li><b>Traducción de correos electrónicos:</b> Permite realizar la traducción de los 
		textos correspondientes a los correos electrónicos enviados por el sistema.
		<li><b>Notificación:</b> Permite realizar la traducción de los textos correspondientes a las notificaciones del sistema.
		</li>
	</ul>
	<br>

	<li><b>Términos de acuerdo:</b> Le permite ver y/o gestionar los <a
		href="#list_registration_agreements"><u>términos de acuerdo</u></a>
	definidos en el sistema.
	<ul>
		<li><b><a href="#view_permissions_admin"><u>Ver</u></a></b></li>
		<li><b><a href="#manage_permissions_admin"><u>Administrar</u></a></b></li>
	</ul>
	<br>
</ul>
<hr class="help">
</span> <span class="admin"> <a
	name="manage_group_permissions_admin_member"></a>
<h3>Permisos de administración de miembro</h3>
En esta ventana usted puede configurar los permisos de un grupo
Administrador, para las funciones de Miembros.<br>
Estos permisos se aplican típicamente al grupo "Administrador de
cuentas".<br>
<br>
La mayoría de las funciones poseen los permisos de <b>Ver</b> y <b>Administrar</b>;
en algunos casos otros permisos específicos.<br>
<ul>
	<li><b>Ver:</b> Si esta casilla de verificación es seleccionada,
	la función será desplegada en el menú para los administradores y como
	un botón de acción en el perfil del miembro.<br>
	Nota: Si el administrador ingresara la dirección (URL) directamente en
	el navegador, la estructura de permisos desplegaría una página de
	"acceso denegado".</li>

	<li><b>Administrar:</b> Si esta casilla es seleccionada, se le
	otorgarán al administrador los permisos para <i>Crear, Modificar y
	Eliminar</i>.</li>
</ul>
<br>
En el primer menú desplegable <b>Administrar grupos</b>, usted puede
seleccionar uno o más grupos de miembros.<br>
Esto significa que el administrador de este grupo, podrá visualizar y
gestionar únicamente miembros pertenecientes a estos grupos.<br>
Por lo tanto, cualquier otra información relacionada con los miembros,
como ser: préstamos, reportes, alertas, usuarios, etc., mostrarán
información de los grupos aquí seleccionados.<br>
En síntesis, esta opción le permite especificar los grupos de miembros a
ser gestionados por un administrador de cuenta específico.
<br><br>Los siguientes permisos pueden ser configurados (utilice los
enlaces correspondientes para obtener más información):
<ul>
	<li><b>Acceso:</b> Permisos para controlar el acceso al sistema de
	los miembros.<br>
	Contiene las siguientes opciones:
	<ul>
		<li><b>Cambiar contraseña de acceso:</b> Permite al
		administrador cambiar la contraseña de acceso de los miembros, a
		través del perfil del miembro (botón "Administrar contraseñas").<br>
		Tenga en cuenta que si este permiso no es seleccionado, el
		administrador de todos modos puede definir una contraseña de acceso
		temporal al registrar un miembro en el sistema (el miembro
		obligatoriamente deberá cambiar su contraseña en su primer inicio de
		sesión/login). Esto sólo sucederá en el caso de que el parámetro de
		grupo "Enviar contraseña generada por correo electrónico" no esté
		seleccionado (Configuración de registro del grupo).<br>
		Si el parámetro "Enviar contraseña generada por correo electrónico" se
		encuentra seleccionado, el administrador no podrá definir la
		contraseña de acceso en el registro del miembro. En este caso, el
		miembro recibirá automáticamente una contraseña temporal por correo
		electrónico, la cual deberá cambiar en su primer inicio de
		sesión/login).<br>
		Si ambos parámetros ("Enviar contraseña generada por correo
		electrónico" y "Cambiar contraseña de acceso al sistema") son
		seleccionados, el administrador podrá elegir en el momento del
		registro del miembro, si desea establecerle una contraseña de acceso
		definitiva (no temporal) o forzar al miembro a cambiar la contraseña
		en su primer inicio de sesión/login en el sistema.

		<li><b>Regenerar contraseña de acceso al sistema:</b> Permite al
		administrador restaurar la contraseña de acceso de los miembros, que
		(dependiendo de la configuración) generalmente significa que será
		regenerada automáticamente y enviada por correo electrónico.

		<li><b>Administrar contraseña de transacción:</b> Permite al
		administrar gestionar la <a
			href="${pagePrefix}passwords#transaction_password"><u>contraseña
		de transacción</u></a> que puede ser configurada para la ejecución de
		transacciones.

		<li><b>Desconectar miembro:</b> Permite al administrador
		desconectar en forma instantánea a un miembro que se encuentra
		utilizando el sistema.

		<li><b>Desconectar operador:</b> Permite al administrador
		desconectar en forma instantánea a un <a href="${pagePrefix}operators"><u>operador</u></a>
		que se encuentra utilizando el sistema.

		<li><b>Reactivar miembros deshabilitados (por intentos fallidos de ingreso de contraseña):</b> 
		Si un miembro olvida su contraseña de acceso e intenta acceder varias veces al sistema con la contraseña
		errónea, será inhabilitado de forma temporal. Si esta opción es
		seleccionada, el administrador podrá permitir el acceso de este
		miembro en forma inmediata. En este caso, el botón "Permitir al
		usuario conectarse ahora" aparecerá en el perfil del miembro.

		<li><b>Cambiar PIN:</b> Permite al administrador cambiar el <a
			href="${pagePrefix}passwords#pin"><u>PIN</u></a> del miembro. El PIN
		es una contraseña numérica utilizada para acceder mediante ciertos <a
			href="${pagePrefix}settings#channels"><u>canales</u></a> al sistema.

		<li><b>Desbloquear PIN:</b> Permite al administrador desbloquear
		el PIN, cuando un miembro excede el número máximo de intentos
		fallidos.
		
		<li><b>Modificar el acceso a canales:</b> Permite al
		administrador cambiar los canales de acceso de los miembros, como por
		ejemplo: Web, SMS, etc.
	</ul>
	<br>
	
	<li><b>Acciones en masa:</b> Mediante las <a
		href="${pagePrefix}user_management#bulk_actions"><u>acciones
	en masa</u></a> usted puede realizar acciones específicas para un conjunto de
	miembros.
	<ul>
		<li><b>Modificar grupo:</b> Cambiar el grupo de un conjunto de
		miembros.
		<li><b>Modificar broker:</b> Cambiar el broker de un grupo de
		miembros. Puede querer hacer esto cuando un broker renuncia a su
		trabajo, y sus miembros necesitan la asignación de otro broker.
		<li><b>Generar tarjeta:</b> Generar tarjetas para un conjunto de
		miembros.
		<li><b>Acceso a los canales de Cambio:</b> Cambie los canales de un grupo.</li>
	</ul>
	<br>
	
	<li><b>Afiliación de grupo préstamo:</b> Estos permisos permiten
	al grupo administrador visualizar y/o gestionar <a
		href="${pagePrefix}loan_groups"><u>grupos de prestámo</u></a> en el
	sistema.<br>
	<ul>
		<li><b><a href="#manage_permissions_admin"><u>Administrar</u></a></b></li>
		<li><b><a href="#view_permissions_admin"><u>Ver</u></a></b></li>
	</ul>
	<br>
	
	<li><b>Brokering:</b> Permisos relacionados con el <a
		href="${pagePrefix}brokering"><u>brokering</u></a> de miembros.
	<ul>
		<li><b>Cambiar broker:</b> Permite al administrador cambiar el
		broker de un miembro.
		<li><b>Ver lista de miembros (como broker):</b> Permite al
		administrador visualizar los miembros de un broker.
		<li><b>Ver datos de préstamos en impresión de lista de
		miembros:</b> Permite visualizar los préstamos en la lista de miembros que
		es gestionada por el broker.
		<li><b>Administrar comisiones:</b> Permite administrar las <a
			href="${pagePrefix}brokering#commission"><u>comisiones</u></a>
		obtenidas por el broker.
	</ul>
	<br>
	
	<li><b>Calificaciones de transacciones:</b> Permiten al
	administrador gestionar o visualizar los comentarios (<a
		href="${pagePrefix}transaction_feedback"><u>calificaciones</u></a>)
	sobre las transacciones.<br>
	Las calificaciones de transacción se habilitan en la configuración de
	los <a href="${pagePrefix}account_management#transaction_type_details"><u>tipos
	de transacción</u></a>.<br>
	<ul>
		<li><b><a href="#view_permissions_admin"><u>Ver</u></a></b></li>
		<li><b><a href="#manage_permissions_admin"><u>Administrar</u></a></b></li>
	</ul>
	<br>
	
	<li><b>Cuentas:</b> Permisos correspondientes a la gestión y
	visualización de las cuentas de miembro.<br>
	Contiene las siguientes opciones:
	<ul>
		<li><b>Ver información:</b> Le permite al administrador ver
		información de la cuenta (balance, visión general de transacciones,
		etc).
		<li><b>Buscar por estados de pagos autorizados:</b> Le permite al administrador
		visualizar los <a href="${pagePrefix}payments#authorized"><u>pagos
		autorizados</u></a>.
		<li><b>Ver pagos agendados:</b> Le permite al administrador
		visualizar los <a href="${pagePrefix}payments#scheduled"><u>pagos
		agendados</u></a>.
		<li><b>Simular conversión:</b> Permite seleccionar los tipos de
		transacción (pagos) para los cuales se podrá "simular conversiones" de
		moneda electrónica.
		<li><b>Configurar límite de crédito:</b> Le permite al
		administrador configurar los límites de crédito individualmente; esto
		sobrescribe los límites establecidos para el grupo.
	</ul>
	<br>

	<li><b>Documentos:</b> Le permite al administrador gestionar y
	visualizar los <a href="${pagePrefix}documents"> <u>documentos</u></a>
	del miembro. <br>
	Las siguientes opciones están disponibles:
	<ul>
		<li><b>Ver Documentos:</b> Aquí se pueden seleccionar los
		documentos que podrá visualizar el administrador. Si no existe ninguno
		disponible, la lista desplegable estará vacía.

		<li><b>Administrar documentos <a
			href="${pagePrefix}documents"><u>dinámicos</u></a></b>.
		<li><b>Administrar documentos <a
			href="${pagePrefix}documents"><u>estáticos</u></a></b>.
		<li><b>Administrar documentos de los <a
			href="${pagePrefix}documents#member_document"><u>miembros</u></a></b>.
	</ul>
	<br>

	<li><b>Facturas de miembro:</b> Esta configuración permite al
	administrador gestionar las <a href="${pagePrefix}invoices"><u>facturas</u></a>
	de los miembros. Los permisos disponibles son:<br>
	<ul>
		<li><b>Enviar factura desde perfil:</b> 
		Si esta opción es seleccionada, estará accesible en el perfil de los miembros, 
		el botón de acción "Factura del sistema al miembro" (bloque Cuentas).</li>
		
		<li><b>Ver facturas de miembro:</b> Si esta opción es
		seleccionada, el administrador podrá visualizar las facturas del
		miembro. Para ello, estará accesible en el perfil de los miembros, el
		botón de acción "Ver facturas" (bloque Cuentas).</li>
		
		<li><b>Aceptar factura:</b> Permite aceptar una factura.</li>
		
		<li><b>Cancelar factura:</b> Permite cancelar una factura.</li>
		
		<li><b>Rechazar factura:</b> Permite rechazar una factura.</li>
		
		<li><b>Enviar como miembro a miembro:</b> Si esta opción es
		seleccionada, estará accesible en el perfil de los miembros, el botón
		de acción "Factura de miembro a miembro" (bloque Cuentas).</li>
		
		<li><b>Enviar como miembro a sistema:</b> Si esta opción es
		seleccionada, estará accesible en el perfil de los miembros, el botón
		de acción "Factura de miembro a sistema" (bloque Cuentas).</li>
		
		<li><b>Aceptar factura de miembro como miembro:</b> Permite
		aceptar una factura de otro miembro como si fuese el miembro.</li>
		
		<li><b>Aceptar factura de sistema como miembro:</b> Permite
		aceptar una factura del sistema como si fuese el miembro.</li>
		
		<li><b>Rechazar factura como miembro:</b> Permite rechazar una
		factura como si fuese el miembro (receptor de la factura).</li>
		
		<li><b>Cancelar factura como miembro:</b> Permite cancelar una
		factura como si fuese el miembro (emisor de la factura).</li>
		
		<!-- 
		<li><b>Enviar factura desde el menú:</b> Si esta opción es
		seleccionada, se desplegará la opción correspondiente en el menú del
		administrador (Menú: Cuentas> Factura a miembro).</li>
		 -->
	</ul>
	<br>

	<li><b>Garantías:</b> Permisos relacionados con las <a
		href="${pagePrefix}guarantees"><u>garantías</u></a> en el sistema.<br>
	Contiene las siguientes opciones:
	<ul>
		<%-- TO DO --%>
		<li><b>Ver obligaciones de pago</b>.
		<li><b>Ver certificaciones</b>.
		<li><b>Ver garantías</b>.
		<li><b>Registrar garantías</b>.
		<li><b>Cancelar certificaciones</b>.
		<li><b>Rechazar/Cancelar garantías</b>.
		<li><b>Aceptar garantías</b>.
		</li>
	</ul>
	<br>

	<li><b>Grupos de miembro:</b> Esta sección define los permisos
	correspondientes a la configuración de los grupos de miembros.
	<ul>
		<li><b>Ver:</b>Si esta opción es seleccionada, la <a
			href="#manage_groups"><u>vista general</u></a> de los grupos de
		miembro, puede ser visualizada por el administrador.
		<li><b>Administrar configuración de la cuenta:</b> Permite al
		administrador la <a href="#manage_group_accounts"><u>gestión
		de las cuentas de grupo</u></a>. Si esta opción NO es seleccionada, el
		administrador podrá visualizar la configuración (dependiendo del
		permiso anterior), pero no podrá realizar modificaciones.
		<li><b>Administrar archivos personalizados:</b> Permite al
		administrador la gestión de los <a
			href="${pagePrefix}content_management#customized_files"><u>archivos
		personalizados</u></a> para el grupo.
	</ul>
	<br>
	
	<li><b>Mensajes:</b> Permite el acceso del administrador al
	sistema de <a href="${pagePrefix}messages"><u>mensajes</u></a>
	(mensajería interna) de Cyclos.
	<ul>
		<li><b>Ver:</b> Contiene una lista desplegable, donde se puede
		seleccionar los tipos (categorías) de mensajes que podrá visualizar el
		administrador.<br>
		Usted puede nuevos tipos de mensajes a través las <a
			href="${pagePrefix}messages#categories"><u>categorías de
		mensajes</u></a>.
		<li><b>Enviar al miembro:</b> Permite que el administrador envíe
		mensajes a un miembro de forma individual.
		<li><b>Enviar al grupo:</b> Permite que el administrador envíe
		mensajes a todos los miembros de un grupo.
		<li><b>Administrar:</b> Permite la administración de los
		mensajes. Permite por ejemplo: buscar mensajes viejos y crear
		categorías de mensajes.
	</ul>
	<br>
	
	<li><b>Miembros:</b> Permisos sobre las acciones que el
	administrador puede realizar con los miembros en el sistema.<br>
	Las opciones son:
	<ul>
		<!--  
		<li><b>Ver:</b> Si esta opción no es seleccionada, el
		administrador no podrá visualizar miembros.
		-->
		
		<li><b>Registrar:</b> Si esta opción es seleccionada, el
		administrador puede registrar nuevos miembros en la página de búsqueda
		de usuarios.
		
		<li><b>Gestionar miembros pendientes:</b> Permite gestionar los
		miembros pertenecientes a grupos "pendientes".</li>
		
		<li><b>Cambiar perfil del miembro:</b> Si esta opción es seleccionada, el
		administrador puede realizar modificaciones en los campos del <a
			href="${pagePrefix}profiles"><u>perfil</u></a> de un miembro.

		<li><b>Cambiar nombre del miembro:</b> Permite al administrador
		modificar el nombre del miembro, en el perfil del miembro.</li>
		
		<li><b>Cambiar código de usuario del miembro:</b> Permite al administrador
		modificar el código de usuario del miembro, en el perfil del miembro.</li>
		
		<li><b>Cambiar e-mail del miembro:</b> Permite al administrador
		modificar el e-mail del miembro, en el perfil del miembro.</li>

		<li><b>Eliminar permanentemente:</b> El administrador puede
		eliminar miembros de forma permanentemente de la base de datos. Esto
		puede ser realizado solamente si el miembro nunca perteneció a un
		grupo con cuentas asignadas (luego de ser activado, un miembro puede
		ser movido al grupo <a href="#removed_members"><u>miembros
		eliminados</u></a>).

		<li><b>Cambiar grupo:</b> Permite al administrador cambiar a un miembro de grupo.

		<li><b>Importar</b>: Permite importar miembros al sistema (desde
		un archivo .CSV).</li>
	</ul>
	<br>
	
	<li><b>POS:</b> Permisos correspondientes a la gestión de los
	dispositivos <a href="${pagePrefix}access_devices#POS"><u>POS</u></a>
	en el sistema.<br>
	Las siguientes acciones sobre los dispositivos POS (auto-explicativas)
	están disponibles:
	<ul>
		<li><b>Ver</b>.</li>
		<li><b>Administrar</b>.</li>
		<li><b>Asignar</b>.</li>
		<li><b>Bloquear</b>.</li>
		<li><b>Descartar</b>.</li>
		<li><b>Desbloquear PIN</b>.</li>
		<li><b>Modificar PIN</b>.</li>
		<li><b>Cambiar parámetros</b>.</li>
	</ul>
	<br>
	
	<li><b>Pagos de Miembro:</b> Conjunto de permisos referentes a los
	<a href="${pagePrefix}payments"><u>pagos</u></a> de los miembros. <br>
	Las opciones son:
	<ul>
		<li><b>Pago de sistema a miembro:</b> Seleccione aquí los tipos
		de transacción (pagos) que el administrador puede utilizar para pagar
		a miembros desde una cuenta del sistema.
		<!--  
		<li><b>Mostrar opción de menú de pago a miembro:</b> Permite al
		administrador realizar pagos de sistema a miembro desde el menú
		principal.
		-->
		<li><b>Pago de miembro en una fecha anterior:</b> Permite al
		administrador realizar pagos de sistema a miembro, con una fecha de
		pago pasada (en vez de la fecha actual).
		<li><b>Pago de miembro a miembro:</b> Seleccione aquí los tipos
		de transacción (pagos) que el administrador puede utilizar para pagar
		a otros miembros, como si fuese el miembro.
		<li><b>Pago propio:</b> Seleccione aquí los tipos de transacción
		(pagos) para realizar pagos de una cuenta a otra del mismo miembro. El
		administrador puede realizar estos pagos como si fuese el miembro.
		<li><b>Pago de miembro a sistema:</b> El administrador puede
		realizar un pago a una cuenta de sistema, desde una cuenta de miembro,
		como si fuese el miembro. Seleccione aquí los tipos de transacción
		(pagos) para los que desee autorizar esta acción.
		<li><b>Autorizar pagos:</b> Si esta opción es seleccionada, el
		administrador podrá <a href="${pagePrefix}payments#authorized"><u>autorizar
		pagos</u></a> como si fuese el miembro.
		<li><b>Cancelar pago autorizado como miembro:</b> Permite al
		administrador cancelar un pago autorizado, como si fuese el miembro.
		<li><b>Cancelar pago agendado como miembro:</b> Permite al
		administrador cancelar un <a href="${pagePrefix}payments#scheduled"><u>pago
		agendado</u></a>, como si fuese el miembro.
		<li><b>Bloquear pago agendado como miembro:</b> Permite bloquear
		un pago agendado como si fuese un miembro. La diferencia entre
		bloquear y cancelar es que el bloqueado puede desbloquearse, mientras
		que la cancelación es definitiva.
		<li><b>Devolver pago:</b> Seleccione aquí los tipos de
		transacción que el administrador puede utilizar para <a
			href="${pagePrefix}payments#charge_back"><u>devolver un pago</u></a>
		para el miembro.
	</ul>
	<br>
	
	<li><b>Preferencias:</b> Esta sección establece los permisos sobre
	las preferencias del sistema.
	<ul>
		<li><b>Administrar notificaciones:</b> Permite al administrador
		gestionar las <a
			href="${pagePrefix}notifications#notification_preferences"><u>preferencias
		de notificación</u></a> de los miembros.</li>
	</ul>
	<br>
	
	<li><b>Productos y servicios:</b> Estos permisos permiten al grupo
	administrador visualizar y/o gestionar los anuncios de los miembros.<br>
	<ul>
		<li><b><a href="#view_permissions_admin"><u>Ver</u></a></b></li>
		<li><b><a href="#manage_permissions_admin"><u>Administrar</u></a></b></li>
		<li><b>Importar:</b> Permite la <a
			href="${pagePrefix}advertisements#import_ads"><u>importación
		de anuncios</u></a> al sistema.
	</ul>
	<br>
	
	<li><b>Préstamos:</b> Configuración que permite al administrador
	acceder a los <a href="${pagePrefix}loans"><u>préstamos</u></a> del
	miembro.<br>
	Contiene las siguientes opciones:
	<ul>
		<li><b>Ver préstamos de miembro</b> Permite visualizar los
		préstamos de miembros normales.
		<li><b>Ver préstamos pendientes de autorización ó rechazados:</b>
		Para ciertos préstamos especiales, su autorización es requerida.
		<li><b>Otorgar préstamo:</b> En esta lista desplegable, puede
		especificar los tipos de préstamos (tipos de transacción) que el
		administrador podrá otorgar a los miembros.
		<li><b>Otorgar préstamo en una fecha anterior:</b> Utilizado en
		ciertas ocasiones donde la fecha del préstamo no puede ser la actual.
		<li><b>Permitir pago externo:</b> Permite <a
			href="${pagePrefix}loans#discard"><u>"cancelar"</u></a> un préstamo,
		colocando su total restante en cero (0).
		<li><b>Permitir pago:</b> Le permite al administrador pagar un
		préstamo del miembro.
		<li><b>Permitir pago en una fecha anterior:</b> Al igual que la
		opción anterior, pero el administrador puede también ingresar la fecha
		del pago (menor a la actual).
		<li><b>Administrar el estado de préstamos vencidos:</b> Permite
		dar a un préstamo vencido un <a href="${pagePrefix}loans#status"><u>estado/marca
		extra</u></a>.
	</ul>
	<br>

	<li><b>Referencias:</b> Permite al administrador gestionar o
	visualizar las <a href="${pagePrefix}references"><u>Referencias</u></a>.
	<ul>
		<li><b><a href="#view_permissions_admin"><u>Ver</u></a></b></li>
		<li><b><a href="#manage_permissions_admin"><u>Administrar</u></a></b></li>
	</ul>
	<br>
	
	<li><b>Registros de SMS (logs):</b> Brinda acceso a la <a
		href="${pagePrefix}reports#sms_log"><u>bitácora de mensajes
	SMS</u></a> enviados por el miembro, o cobrados al miembro. El sistema puede
	ser configurado para enviar mensajes SMS en diversas ocasiones.<br>
	<ul>
		<li><b><a href="#view_permissions_admin"><u>Ver</u></a></b></li>
	</ul>
	<br>
	
	<li><b>Registros de miembro:</b> Permite al administrador
	gestionar los <a href="${pagePrefix}member_records"><u>registros
	de miembro</u></a>. Las acciones a realizar sobre los registros de miembro
	seleccionados son:
	<ul>
		<li><b>Ver</b>.</li>
		<li><b>Crear</b>.</li>
		<li><b>Modificar</b>.</li>
		<li><b>Eliminar</b>.</li>
	</ul>
	<br>
	
	<li><b>Reporte de actividades:</b> Son <a
		href="${pagePrefix}reports#member_activities"><u>reportes</u></a>
	sobre la actividad de un miembro específico, accesibles a través de los
	botones de acción ubicados en la parte inferior del perfil del miembro.<br>
	<ul>
		<li><b><a href="#view_permissions_admin"><u>Ver</u></a></b></li>
		<li><b>Mostrar información de cuentas</b>.</li>
	</ul>
	<br>
	
	<li><b>SMS de difusión:</b> Permite al administrador
	enviar y/o visualizar <a href="${pagePrefix}reports#sms_broadcast"><u>mensajes
	SMS de difusión</u></a>.<br>
	Los permisos disponibles son:
	<ul>
		<li><b>Ver</b>.</li>
		<li><b>Enviar mensajes sin cargos</b>.</li>
		<li><b>Enviar mensajes con cargos</b>.</li>
	</ul>
	<br>
	
	<li><b>Tarjetas:</b> Permisos correspondientes a la gestión de <a
		href="${pagePrefix}access_devices#access_devices_top"><u>tarjetas</u></a>
	en el sistema.<br>
	Las siguientes acciones sobre las tarjetas (auto-explicativas) están
	disponibles:
	<ul>
		<li><b>Ver</b>.
		<li><b>Generar</b>.
		<li><b>Cancelar</b>.
		<li><b>Bloquear</b>.
		<li><b>Desbloquear</b>.
		<li><b>Cambiar código</b>.
		<li><b>Desbloquear código de seguridad</b>.</li>
	</ul>
	<br>
</ul>

<hr class="help">
</span> <span class="admin"> <a
	name="manage_group_permissions_admin_admin"></a>
<h3>Permisos de administración de administrador</h3>
En esta ventana usted puede configurar los permisos de un grupo
Administrador, para las funciones de otros administradores.<br>
Esto significa que se puede definir el nivel de acceso que los
administradores tendrán en el sistema.<br>
Es una práctica habitual tener una limitada (si no existe un único
administrador) la cantidad de administradores con este tipo de permisos,
ya que son los permisos de más alto nivel en el sistema.<br>
<br>
La mayoría de las funciones poseen los permisos de <b>Ver</b> y <b>Administrar</b>;
en algunos casos otros permisos específicos extra. <br>
<ul>
	<li><b>Ver:</b> Si esta casilla de verificación NO es
	seleccionada, dicha función no será desplegada en el menú, ni como un
	botón de acción del miembro. Nota: Si el administrador ingresara la
	dirección (URL) directamente en el navegador, la estructura de permisos
	desplegaría una página de "acceso denegado".</li>

	<li><b>Administrar:</b> Si esta casilla es seleccionada, se le
	otorgarán al administrador los permisos para <i>Crear, Modificar y
	Eliminar</i>.</li>
</ul>
Las siguientes opciones están disponibles:
<ul>
	<li><b>Acceso:</b>
	<ul>
		<li><b>Cambiar contraseña:</b> Permite al administrador cambiar
		la contraseña de acceso de otros usuarios administradores.
		<li><b>Administrar contraseña de transacción:</b> Permite
		gestionar las contraseñas de transacción de otros usuarios
		administradores.
		<li><b>Desconectar:</b> Permite desconectar a otros
		administradores en cualquier momento, haciendo click en el botón
		Desconectar.
		<li><b>Reactivar administradores deshabilitados (por intentos
		fallidos de ingreso de contraseña):</b> Si un administrador es
		inhabilitado por no recordar su contraseña, usted puede permitir
		volver a intentar su acceso.
	</ul>
	<br>

	<li><b>Administradores:</b> Permisos relacionados a otros
	administradores en el sistema.<br>
	Existen menos funciones para administradores que para miembros.<br>
	Los administradores no poseen cuentas; pero si un nivel determinado de
	acceso a las cuentas del sistema.<br>
	Las opciones son auto-explicativas.
	<ul>
		<li><b>Ver</b>.</li>
		<li><b>Registrar</b>.</li>
		<li><b>Cambiar perfil</b>.</li>
		<li><b>Cambiar grupo</b>.</li>
		<li><b>Eliminar</b>.</li>
	</ul>
	<br>
	
	<li><b>Registros de administradores:</b> Son como <a
		href="${pagePrefix}member_records"><u>registros de miembro</u></a>,
	pero almacenan información de los administradores.<br>
	Las opciones son auto-explicativas.
	<ul>
		<li><b>Ver</b>.</li>
		<li><b>Crear</b>.</li>
		<li><b>Modificar</b>.</li>
		<li><b>Eliminar</b>.</li>
	</ul>
</ul>

<hr class="help">
</span> <span class="admin"> <a name="manage_group_permissions_member"></a>
<h3>Permisos de miembro</h3>
Los miembros pertenecientes al grupo recibirán los siguientes permisos.
<ul>
    <li><b>Cambie los canales de acceso:</b> Cuando esta opción es seleccionada, 
	el miembro puede activar / desactivar sus canales(ej. SMS, POS).<br>
	<li><b>Acceso:</b> 
	Esta sección define los permisos de acceso al sistema para el grupo.
	<ul>
		<li><b>Desbloquear PIN:</b> Permite desbloquear el PIN, cuando el
		miembro haya superado el número máximo de intentos fallidos en su
		ingreso.<br>
	</ul>
	<br>
	
	<li><b>Anuncios:</b> 
	Esta sección establece los permisos del grupo sobre los anuncios en el sistema.
	<ul>
		<li><b>Ver:</b> Si uno o más grupos son seleccionados en esta
		lista desplegable, la función <a
			href="${pagePrefix}advertisements#advertisement_search"><u>Buscar
		anuncios</u></a> (Menú: Buscar> Productos y servicios) mostrará solamente
		resultados de los miembros integrantes de los grupos seleccionados.<br>
		Normalmente se seleccionan todos los grupos.<br>
		Si usted posee grupos que necesitan operar de forma separada dentro
		del mismo sistema, usted puede limitar la visibilidad de los anuncios
		seleccionando solamente ciertos grupos.<br>

		Si no son seleccionados grupos, las funciones de Anuncios no estarán
		disponibles para los miembros de este grupo.<br>
		Tampoco se desplegarán en el menú de Búsqueda o en la página de <a
			href="${pagePrefix}profiles#actions_for_member"><u>acciones
		del miembro</u></a> ubicada bajo el perfil.<br>
		</li>

		<li><b>Publicar:</b> Si esta opción es seleccionada, el miembro
		podrá publicar anuncios en el sistema, y se desplegará la opción
		Anuncios en su Menú personal (Menú: Personal> Anuncios).</li>
	</ul>
	<br>
	
	<li><b>Cuenta:</b> 
	Los miembros siempre tienen acceso a sus cuentas, 
	por lo que no existen permisos para Ver/Administrar sus propias cuentas.<br>
	En esta sección únicamente es posible configurar si los miembros
	pueden:
	<ul>
		<li><b> Ver pagos autorizados:</b> Permite visualizar sus <a
			href="${pagePrefix}payments#authorized"><u>pagos autorizados</u></a>
		en el sistema.
		<li><b> Ver pagos agendados:</b> Permite visualizar sus <a
			href="${pagePrefix}payments#scheduled"><u>pagos agendados</u></a>.
		<li><b> Simular conversión:</b> Permite realizar una simulación
		de conversión de moneda electrónica, para los tipos de transacción
		seleccionados en la lista desplegable.</li>
	</ul>
	<br>
	
	<li><b>Documentos:</b> 
	Mediante esta opción se puede determinar
	que <a href="${pagePrefix}documents"><u>documentos</u></a> se mostrarán
	en el menú personal del miembro (Menú: Personal> Documentos).<br>
	Si no existe ningún documento seleccionado, la opción no se mostrará en
	el menú (para el grupo).<br>
	<br>
	
	<li><b>Facturas:</b> 
	Esta sección define los permisos sobre las 
	<a href="${pagePrefix}invoices"><u>facturas</u></a> en el sistema.<br>
	Los permisos disponibles son:
	<ul>
		<li><b>Ver:</b> Si esta opción es seleccionada, la opción
		"Facturas" se desplegará en el menú de cuentas del miembro (Menú:
		Cuentas> Facturas), y podrá visualizar sus facturas en el sistema.</li>
		
		<li><b>Enviar a miembro:</b> Si esta opción es seleccionada, el
		miembro podrá enviarle facturas a otros miembros, tanto desde el
		perfil del miembro como directamente desde el menú de cuentas (Menú:
		Cuentas> Factura a miembro).</li>
		
		<li><b>Facturas a sistema:</b> Si esta opción es seleccionada, el
		miembro podrá enviarle facturas a cuentas del sistema, desde el menú
		cuentas (Menú: Cuentas> Factura al sistema).</li>
		
		<!--  
		<li><b>Mostrar opción de menú factura a miembro:</b> Si esta
		opción es seleccionada, la opción "Factura a miembro" se desplegará en
		el menú de cuentas del miembro (Menú: Cuentas> Factura a miembro).</li>
		-->
	</ul>
	<br>
	
	<!--  
	<li><b>Comisiones:</b> 
	Esta sección define los permisos
	correspondientes a las <a href="${pagePrefix}brokering#commission"><u>comisiones</u></a>
	de broker para el grupo.
	<ul>
		<li><b>Ver:</b> Si esta opción es seleccionada, el miembro podrá
		visualizar las comisiones de su broker.</li>
	</ul>
	<br>
	-->
	
	<li><b>Garantías:</b> 
	Permisos correspondientes al módulo de 
	<a href="${pagePrefix}guarantees"><u>garantías</u></a> de Cyclos, donde
	cada saldo de cuenta se encuentra garantizado con un respaldo en dinero físico.<br>
	Usted puede establecer los siguientes permisos:
	<ul>
		
		<li><b>Crear garantías (como emisor):</b> 
		Permite seleccionar los tipos de garantías para los cuales los miembros del grupo podrán ser emisores.<br>
		Este permiso también es tenido en cuenta para la emisión de certificaciones, 
		en el caso de tipos de garantías con obligaciones de pago. 
		
		<li><b>Emitir certificaciones:</b> 
		Aquí puede seleccionar los grupos de usuarios a los cuales es posible emitirles certificaciones.
						
		<li><b>Comprar con obligaciones de pago:</b> 
		Aquí usted puede seleccionar los grupos de miembros a los cuales es posible publicarles (comprarles con) 
		<a href="${pagePrefix}guarantees"><u>obligaciones de pago</u></a>.
			
		<li><b>Vender con obligaciones de pago:</b> 
		Si esta opción es seleccionada, los miembros del grupo podrán aceptar (vender con) obligaciones de pago. 
	</ul>
	<br>

	<li><b>Mensajes:</b> 
	Aquí usted puede establecer los permisos de los miembros para la utilización del sistema de 
	<a href="${pagePrefix}messages"><u>mensajes</u></a> (mensajería interna)
	de Cyclos.<br>
	Los permisos disponibles son:
	<ul>
		<li><b>Ver:</b> Si esta opción es seleccionada, la opción
		"Mensajes" se desplegará en el menú personal del miembro (Menú:
		Personal> Mensajes), y podrá visualizar sus mensajes en el sistema.</li>
		<li><b>Enviar al miembro:</b> Si esta opción es seleccionada, el
		miembro podrá enviarle mensajes a otros miembros, tanto desde el
		perfil del miembro como directamente desde el menú personal (Menú:
		Personal> Mensajes).</li>
		<li><b>Enviar a la administración:</b> Aquí usted puede definir
		los tipos (categorías) de mensajes que el miembro podrá enviarle a la
		administración. <br>
		Las <a href="${pagePrefix}messages#categories"><u>categorías
		de mensajes</u></a> a seleccionar en la lista desplegable, deben ser definidas
		previamente.</li>
		<li><b>Administrar:</b> Si esta opción es seleccionada, el
		miembro podrá gestionar sus mensajes en el sistema.</li>
	</ul>
	<br>
	Nota: Para que un mensaje sea visualizado por un Administrador, el
	grupo de administradores debe poseer también configurados los permisos
	correspondientes. <br>
	<br>

	<li><b>Operadores:</b> Esta sección define los permisos
	correspondientes a la gestión de usuarios <a
		href="${pagePrefix}operators"><u>Operadores</u></a> para el grupo de
	miembros.
	<ul>
		<li><b>Administrar operadores:</b> Aquí usted puede definir
		(habilitar/deshabilitar) si los miembros pueden utilizar Operadores en
		Cyclos.</li>
	</ul>
	<br>

	<li><b>Pagos:</b> Aquí se especifican los permisos referentes a
	los pagos del grupo de miembros en el sistema, y los <a
		href="${pagePrefix}account_management#transaction_type_details"><u>tipos
	de transacción</u></a> habilitados. <br>
	Los permisos disponibles son:
	<ul>		
		<li><b>Pago propio:</b> Si esta opción es seleccionada, el
		miembro puede realizar pagos entre sus propias cuentas.<br>
		En la lista desplegable, usted puede seleccionar los posibles tipos de
		transacción a utilizar.<br>
		Esta opción tiene sentido solamente si se tiene más de una cuenta de
		miembro para este grupo.

		<li><b>Pagos a miembro:</b> Aquí usted puede seleccionar que
		tipos de transacción puede utilizar el miembro para realizarle pagos a
		otros miembros.
		
		<li><b>Pagos a sistema:</b> Aquí usted puede seleccionar que
		tipos de transacción puede utilizar el miembro para realizarle pagos a
		cuentas del sistema.<br>
		Si no es seleccionado ningún tipo de transacción, la opción "Pago al
		sistema" no será desplegada en el menú del miembro.
		
		<li><b>Generar tickets de pago externo:</b><br>
		Definido mayormente para tiendas virtuales (webshops) que desean
		utilizar Cyclos como un sistema de pago externo.<br>
		Si esta opción es seleccionada, el miembro (comercio) es habilitado
		para emitir comprobantes. El sistema de comprobantes es transparente
		para los usuarios y provee de una estructura, donde las tiendas
		virtuales pueden autenticarse y validar consumidores e información de
		pagos sin tener acceso a los detalles de registro del consumidor.<br>
		
		<li><b>Autorizar pagos:</b> Permite al miembro <a
			href="${pagePrefix}payments#authorized"><u>autorizar</u></a> pagos.

		<li><b>Cancelar pago pendiente de autorización:</b> Si se
		utilizan <a href="${pagePrefix}payments#authorized"><u>pagos
		autorizados</u></a>, esta opción permitirá que los miembros cancelen sus pagos
		a autorizar, una vez creados pero aún no autorizados.

		<li><b>Cancelar pago agendado:</b> Si se utilizan <a
			href="${pagePrefix}payments#scheduled"><u>pagos agendados</u></a>,
		esta opción permitirá que los miembros cancelan dichos pagos antes de
		la fecha establecida.

		<li><b>Bloquear pagos agendados:</b> Le permite al miembro
		bloquear temporalmente un pago agendado.
		
		<!-- 					
		<li><b>Mostrar opción de menú pago:</b> Si esta opción es
		seleccionada, el miembro puede realizar pagos directamente desde su
		menú de cuentas (Menú: Cuentas).
		 -->

		<li><b>Solicitar pagos por otros canales:</b> En esta lista
		desplegable usted puede seleccionar otros <a
			href="${pagePrefix}settings#channels"><u>canales</u></a> mediante los
		cuales el miembro puede enviar "<a
			href="${pagePrefix}payments#request"><u>solicitudes de pago</u></a>"
		(facturas).<br>
		Por el momento, solo el canal SMS se encuentra habilitado para esto,
		pero en el futuro otros canales serán incorporados.

		<li><b>Devolver pago recibido:</b> Aquí usted puede seleccionar
		que tipos de transacción puede utilizar el miembro para <a
			href="${pagePrefix}payments#charge_back"><u>devolver un pago</u></a>
		recibido.<br>
		<br>

		Más información técnica puede encontrarse en la página wiki de Cyclos
		(Comprobantes - Webservices).
	</ul>
	<br>
	
	<li><b>Perfil de Miembro:</b> 
	Esta sección establece los permisos sobre los perfiles de otros miembros (grupos) en el sistema.
	<ul>
		<li><b>Ver:</b> Aquí usted puede especificar los grupos de
		miembros, para los cuales este grupo podrá realizar <a
			href="${pagePrefix}user_management#search_member_by_member"><u>búsquedas
		de miembros</u></a> (Menú: Buscar> Miembros). <br>
		Normalmente son seleccionados todos los grupos (menos los eliminados o
		deshabilitados). Sin embargo, usted podría desear que grupos de
		miembros trabajen de forma independiente en el sistema, limitando la
		visualización entre grupos.
		
		<li><b>Cambiar el código de usuario propio:</b> Esta opción le permite a los miembros 
		de este grupo cambiar su nombre de usuario en su perfil.
		
		<li><b>Cambiar el nombre propio:</b> Esta opción le permite a los miembros 
		de este grupo cambiar su nombre en su perfil.
		
		<li><b>Cambiar el e-mail propio:</b> Esta opción le permite a los miembros 
		de este grupo cambiar su mail en su perfil.</li>
	</ul>
	<br>
	
	<li><b>Preferencias:</b> 
	Aquí usted puede definir para los
	miembros del grupo, el acceso a sus preferencias en el sistema.<br>
	Los permisos disponibles son:
	<ul>
		<li><b>Administrar notificaciones:</b> Permite al miembro
		administrar sus <a href="${pagePrefix}notifications"><u>notificaciones</u></a>
		(correo electrónico, mensajería interna, SMS, etc.).

		<li><b>Administrar anuncios de interés:</b> Permite al miembro
		administrar sus <a href="${pagePrefix}ads_interest"><u>anuncios de interés</u></a>.
		
		<li><b>Administrar impresoras de recibos: </b> Permite al miembro
		ver y administrar sus <a href="${pagePrefix}preferences#receipt_printers"><u>impresoras de recibos</u></a>.
	</ul>
	<br>

	<li><b>Préstamos:</b> 
	Esta sección establece los permisos sobre
	los <a href="${pagePrefix}loans"><u>préstamos</u></a>.
	<ul>
		<li><b>Ver:</b> Si esta opción es seleccionada, los miembros del
		grupo podrán visualizar sus préstamos. De lo contrario, dicha opción
		no será desplegada en el menú (Menú: Cuentas> Préstamos).
		<li><b>Pagar:</b> Si selecciona esta opción, el miembro podrá
		realizar pagos de préstamos.
	</ul>
	<br>

	<li><b>Referencias:</b> 
	Esta sección define los permisos
	correspondientes a las <a href="${pagePrefix}references"><u>referencias</u></a>
	para el grupo.
	<ul>
		<li><b>Ver:</b> Permite al miembro visualizar sus referencias.</li>
		<li><b>Dar:</b> Permite al miembro dar referencias de otros
		miembros en el sistema.</li>
	</ul>
	<br>
	Si no desea utilizar esta función (para uno o varios grupos), no
	seleccione la opción "Ver". En este caso el menú de referencias (Menú:
	Personal> Referencias) y sus botones de acción correspondientes, no
	serán desplegados.<br>
	<br>

	<li><b>Registros de SMS:</b> 
	Esta sección define los permisos
	sobre el <a href="${pagePrefix}reports#sms_log"><u>registro de
	mensajes SMS</u></a> (log) para el grupo.
	<ul>
		<li><b>Ver:</b> Si esta opción es seleccionada, el miembro podrá
		acceder al registro de mensajes SMS enviados por él o cobrados a él.
		El sistema puede ser configurado para el envío de mensajes SMS en
		distintas cirunstancias (operaciones, notificaciones, alertas, etc.).</li>
	</ul>
	<br>
	
	<li><b>Reportes de Miembro:</b> 
	Esta sección establece los
	permisos sobre los <a href="${pagePrefix}reports#member_reports"><u>reportes</u></a>
	del grupo.
	<ul>
		<li><b>Ver:</b> Si esta opción es seleccionada, el miembro podrá
		visualizar los reportes de otros miembros.</li>
		<li><b>Mostrar información de cuentas:</b> Si selecciona al menos
		un tipo de cuenta en esta lista desplegable, el miembro podrá
		visualizar también la información (saldos) de cuentas de otros
		miembros.<br>
	</ul>
	<br>

	<li><b>Tarjetas:</b> Esta sección define los permisos sobre las <a
		href="${pagePrefix}access_devices#access_devices_top"><u>tarjetas</u></a>
	en el sistema.<br>
	Las siguientes acciones sobre las tarjetas (auto-explicativas) están
	disponibles:
	<ul>
		<li><b>Vista:</b> Si esta opción es seleccionada, la opción
		"Tarjetas" se desplegará en el menú personal del miembro (Menú:
		Personal> Tarjetas), y podrá visualizar sus tarjetas en el sistema.</li>
		
		<li><b>Bloquear:</b> Si esta opción es seleccionada, el miembro
		podrá "bloquear" sus tarjetas.</li>
		
		<li><b>Desbloquear:</b> Si esta opción es seleccionada, el
		miembro podrá "desbloquear" sus tarjetas.</li>
				
		<li><b>Cambiar código:</b> Si esta opción es seleccionada, el
		miembro podrá cambiar el código de seguridad de sus tarjetas.</li>
				
	</ul>
	<br>
</ul>

<hr class="help">
</span> <span class="admin"> <a name="manage_group_permissions_broker"></a>
<h3>Permisos de broker</h3>
En esta sección se establecen los permisos correspondientes a las
funciones de <a href="${pagePrefix}brokering"><u>brokering</u></a> del
sistema, para un grupo Broker.<br>
Esto significa que usted puede definir las acciones que los brokers de
este grupo podrán realizar en nombre de sus miembros.<br>
<br>
La mayoría de las funciones poseen los permisos de <b>Ver</b> y <b>Administrar</b>;
en algunos casos otros permisos específicos.
<ul>
	<a name="view_permissions_broker"></a>
	<li><b>Ver:</b> Si esta casilla de verificación es seleccionada,
	la opción será mostrada en el menú del broker y como un botón de acción
	en el perfil del miembro.<br>
	Nota: Si el broker ingresara la dirección (URL) directamente en el
	navegador, la estructura de permisos desplegaría una página de "acceso
	denegado".</li>

	<a name="manage_permissions_broker"></a>
	<li><b>Administrar:</b> Si esta casilla es seleccionada, se le
	otorgarán al administrador los permisos para <i>Crear, Modificar y
	Eliminar</i>.</li>
</ul>
Puede permitir que el broker acceda a las siguientes funciones de sus
miembros:
<ul>
	<li><b>Acceso:</b> Permite al broker controlar el acceso al
	sistema de sus miembros.<br>
	Contiene las siguientes opciones:
	<ul>
		<li><b>Cambiar contraseña:</b> Le permite al broker cambiar la
		contraseña de acceso de sus miembros, a través de sus perfiles.
		<li><b>Regenerar contraseña:</b> Le permite al broker restaurar
		la contraseña de acceso al sistema de sus miembros, que (dependiendo
		de la configuración) generalmente será generada automáticamente y
		enviada por correo electrónico al miembro.
		<li><b>Administrar contraseña de transacción:</b> Permite al
		broker la administración de la <a
			href="${pagePrefix}passwords#transaction_password"><u>contraseña
		de transacción</u></a> para sus miembros.
		<li><b>Cambiar PIN:</b> Permite al broker cambiar el <a
			href="${pagePrefix}passwords#pin"><u>PIN</u></a> de sus miembros, que
		es una contraseña numérica utilizada para ciertos <a
			href="${pagePrefix}settings#channels"><u>canales</u></a> de acceso al
		sistema.
		<li><b>Desbloquear PIN:</b> Permite al broker desbloquear el PIN
		de sus miembros, por haber excedido la cantidad máxima de intentos
		fallidos en su ingreso.</li>
		<li><b>Alterar acceso a canales:</b> Permite al broker cambiar
		los <a href="${pagePrefix}settings#channels"><u>canales</u></a> de
		acceso al sistema, Web, teléfono celular, etc.
	</ul>
	<br>

	<li><b>Brokering:</b>
	<ul>
		<li><b>Registrar:</b> Permite al broker registrar nuevos miembros
		en el sistema.
		<li><b>Gestionar miembros pendientes:</b> El broker puede
		visualizar miembros registrados, pero no validados aún (por correo
		electrónico). Más información se encuentra disponible en el <a
			href="${pagePrefix}user_management#search_pending_member"><u>archivo
		de ayuda</u></a> correspondiente.</li>
		<li><b>Cambiar perfil:</b> Permite al broker modificar el <a
			href="${pagePrefix}profiles"><u>perfil</u></a> de sus miembros.
		<li><b>Cambiar el nombre de cliente:</b> Permite al broker
		modificar el nombre de sus miembros.</li>
		<li><b>Cambiar el código de cliente:</b> Permite al broker
		modificar el código de sus miembros.</li>
		<li><b>Cambiar el e-mail:</b> Permite al broker
		modificar el mail de sus miembros.</li>	
		<li><b>Administrar comisiones por defecto:</b> Permite al broker
		configurar el valor por defecto de las <a
			href="${pagePrefix}brokering#commission"><u>comisiones</u></a> de sus
		miembros.
		<li><b>Administrar contratos de comisión:</b> Si esta opción es
		seleccionada, el broker puede administrar los <a
			href="${pagePrefix}brokering#commission_contract"><u>contratos
		de comisión</u></a> de sus miembros.
	</ul>
	<br>
	
	<li><b>Cuenta:</b> Permisos del broker correspondientes a la
	gestión y visualización de las cuentas de sus miembros.
	<ul>
		<li><b>Ver información de cuentas:</b> Permite al broker
		visualizar la información de las cuentas de sus miembros.
		<li><b>Ver pagos autorizados:</b> El broker puede visualizar los
		<a href="${pagePrefix}payments#authorized"><u>pagos
		autorizados</u></a> de sus miembros.
		<li><b>Ver pagos agendados:</b> El broker puede visualizar los <a
			href="${pagePrefix}payments#scheduled"><u>pagos agendados</u></a> de
		sus miembros.
		<li><b>Simular conversión para miembro:</b> Permite al broker
		realizar una simulación de conversión de moneda electrónica sobre las
		cuentas de sus miembros, para los tipos de transacción seleccionados
		en la lista desplegable.</li>
	</ul>
	<br>

	<li><b>Documentos:</b> Esta sección define los permisos sobre los
	<a href="${pagePrefix}documents"><u>documentos</u></a> para el broker.<br>
	<ul>
		<li><b>Ver:</b> Aquí puede seleccionar los documentos que podrá
		visualizar el broker en el sistema.
		<li><b>Ver documentos individuales del miembro:</b> Permite al
		broker visualizar los <u><a
			href="${pagePrefix}documents#member_document">documentos
		individuales</a></u> de sus miembros.
		<li><b>Administrar documentos individuales del miembro:</b> Igual
		que el permiso anterior, pero con derechos de administración.
	</ul>
	<br>

	<li><b>Facturas:</b> Esta configuración permite al broker
	gestionar las <a href="${pagePrefix}invoices"><u>facturas</u></a> de
	sus miembros.<br>
	<ul>
		<li><b>Ver:</b> Si esta opción es seleccionada, el broker podrá
		visualizar las facturas de sus miembros.</li>
		<li><b>Enviar como miembro a miembro:</b> Si esta opción es
		seleccionada, estará accesible en el perfil de sus miembros, el botón
		de acción "Factura de miembro a miembro" (bloque Acciones de broker).</li>
		<li><b>Enviar como miembro a sistema:</b> Si esta opción es
		seleccionada, estará accesible en el perfil de sus miembros, el botón
		de acción "Factura de miembro a sistema" (bloque Acciones de broker).</li>
		<li><b>Aceptar la factura del miembro como miembro:</b> Permite
		al broker aceptar una factura de otro miembro como si fuese el
		miembro.</li>
		<li><b>Aceptar la factura del sistema como miembro:</b> Permite
		al broker aceptar una factura del sistema como si fuese el miembro.</li>
		<li><b>Rechazar como miembro:</b> Permite al broker rechazar una
		factura como si fuese el miembro (receptor de la factura).</li>
		<li><b>Cancelar como miembro:</b> Permite al broker cancelar una
		factura como si fuese el miembro (emisor de la factura).</li>
	</ul>
	<br>

	<li><b>Grupos de préstamos:</b> Esta sección define los permisos
	del broker correspondientes a los <a href="${pagePrefix}loan_groups"><u>grupos
	de préstamo</u></a>.
	<ul>
		<li><b><a href="#view_permissions_broker"><u>Ver</u></a></b></li>
	</ul>
	<br>
	
	<li><b>Mensajes personales:</b> Permisos correspondientes a los <a
		href="${pagePrefix}messages"><u>mensajes</u></a> del broker a sus
	miembros.
	<ul>
		<li><b>Enviar mensaje a miembros registrados:</b> Permite al
		broker enviar mensajes personales a todos sus miembros.</li>
	</ul>
	<br>

	<li><b>POS:</b> Permisos del broker correspondientes a la gestión
	de los dispositivos <a href="${pagePrefix}access_devices#POS"><u>POS</u></a>
	de sus miembros.<br>
	Las siguientes acciones sobre los dispositivos POS (auto-explicativas)
	están disponibles:
	<ul>
		<li><b>Vista</b>.</li>
		<li><b>Gestión</b>.</li>
		<li><b>Asignar</b>.</li>
		<li><b>Bloquear</b>.</li>
		<li><b>Descartar</b>.</li>
		<li><b>Desbloquear PIN</b>.</li>
		<li><b>Cambiar PIN</b>.</li>
		<li><b>Cambiar parámetros</b>.</li>
	</ul>
	<br>
	
	<li><b>Pagos de miembro:</b> Permite al broker controlar las
	opciones de pago de sus miembros.<br>
	Las permisos disponibles son:
	<ul>
		<li><b>Pago de miembro a miembro:</b> Seleccione aquí los tipos
		de transacción que el broker puede utilizar para realizar pagos de
		miembro a miembro, con si fuese un miembro.
		<li><b>Pago de miembro propio:</b> Seleccione aquí los tipos de
		transacción que el broker puede utilizar para realizar pagos entre
		cuentas del mismo miembro, como si fuese el miembro.
		<li><b>Pago de miembro a sistema:</b> Seleccione aquí los tipos
		de transacción que el broker puede utilizar para realizar pagos de
		miembro a cuentas del sistema, como si fuese el miembro.
		<li><b>Autorizar:</b> El broker puede <a
			href="${pagePrefix}payments#authorized"><u>autorizar pagos</u></a>
		del miembro, como si fuese el miembro.
		<li><b>Cancelar pago autorizado como miembro:</b> El broker puede
		cancelar pagos autorizados del miembro, como si fuese el miembro.
		<li><b>Cancelar pago agendado como miembro:</b> El broker puede
		cancelar <a href="${pagePrefix}payments#scheduled"><u>pagos
		agendados</u></a> del miembro, como si fuese el miembro.
		<li><b>Bloquear pago agendado como miembro:</b> El broker puede
		bloquear temporalmente un pago agendado del miembro, como si fuese el
		miembro.<br>
		La diferencia entre bloquear y cancelar es que el pago bloqueado se
		puede desbloquear, mientras que la cancelación es definitiva.</li>
	</ul>
	<br>
	
	<li><b>Preferencias:</b> Esta sección define los permisos del
	broker sobre las preferencias de sus miembros.
	<ul>
		<li><b>Administrar notificaciones:</b> Permite al broker
		administrar las <a
			href="${pagePrefix}notifications#notification_preferences"><u>preferencias
		de notificación</u></a> de sus miembros.</li>
	</ul>
	<br>
	
	<li><b>Productos y Servicios:</b> Permiten al broker visualizar
	y/o gestionar los anuncios de sus miembros.<br>
	<ul>
		<li><b><a href="#view_permissions_broker"><u>Ver</u></a></b></li>
		<li><b><a href="#manage_permissions_broker"><u>Administrar</u></a></b></li>
	</ul>
	<br>
	
	<li><b>Préstamos:</b> Esta sección define los permisos del broker
	sobre los <a href="${pagePrefix}loans"><u>préstamos</u></a> en el
	sistema.
	<ul>
		<li><b><a href="#view_permissions_broker"><u>Ver</u></a></b></li>
	</ul>
	<br>
	
	<li><b>Referencias:</b> Permite al broker gestionar (dar,
	modificar y eliminar) las <a href="${pagePrefix}references"><u>referencias</u></a>
	de sus miembros.
	<ul>
		<li><b><a href="#manage_permissions_broker"><u>Administrar</u></a></b></li>
	</ul>
	<br>
	
	<li><b>Registros de SMS:</b> Brinda al broker acceso a la <a
		href="${pagePrefix}reports#sms_log"><u>bitácora de mensajes
	SMS</u></a> de sus miembros.
	<ul>
		<li><b><a href="#view_permissions_broker"><u>Ver</u></a></b></li>
	</ul>
	<br>
	
	<li><b>Registros de miembros:</b> Permite al broker gestionar los
	<a href="${pagePrefix}member_records"><u>registros de miembro</u></a>
	de sus miembros.<br>
	Las acciones disponibles son:
	<ul>
		<li><b>Ver</b>.</li>
		<li><b>Crear</b>.</li>
		<li><b>Modificar</b>.</li>
		<li><b>Eliminar</b>.</li>
	</ul>
	<br>
	
	<li><b>Reportes:</b> Esta sección define los permisos del broker
	correspondientes a los <a href="${pagePrefix}reports#member_reports"><u>reportes</u></a>
	de sus miembros.
	<ul>
		<li><b>Ver:</b> Si esta opción es seleccionada, el broker puede
		visualizar los reportes de sus miembros.</li>
		<li><b>Mostrar información de cuentas:</b> El broker podrá
		visualizar también información de las cuentas (de sus miembros)
		seleccionadas en este menú desplegable.</li>
	</ul>
	<br>

	<li><b>SMS de difusión:</b> Esta sección define los permisos del
	broker sobre los <a href="${pagePrefix}reports#sms_broadcast"><u>mensajes
	SMS de difusión</u></a> a sus miembros.
	<ul>
		<li><b>Enviar mensajes de difusión sin cargos:</b> Permite al
		broker enviar mensajes SMS de difusión a sus miembros sin costo.</li>
		<li><b>Enviar mensajes de difusión con cargos:</b> Permite al
		broker enviar mensajes SMS de difusión a sus miembros con costo.</li>
	</ul>
	
	<li><b>Tarjetas:</b> Permisos del broker correspondientes a la
	gestión de <a href="${pagePrefix}access_devices#access_devices_top"><u>tarjetas</u></a>
	de sus miembros.<br>
	Las siguientes acciones sobre las tarjetas (auto-explicativas) están
	disponibles:
	<ul>
		<li><b>Ver</b>.
		<li><b>Generar</b>.
		<li><b>Cancelar</b>.
		<li><b>Bloquear</b>.
		<li><b>Desbloquear</b>.
		<li><b>Cambiar código</b>.
		<li><b>Desbloquear código de seguridad</b>.</li>
	</ul>
	<br>
</ul>
</span>

<hr>
<a name="group_filters"></a>
<h2>Filtros de grupo</h2>
Los filtros de grupo son una especie de "súper grupos": una serie de
grupos juntos, con un nombre determinado, sobre los que puede realizar
ciertas acciones.<br>
En resumen, un filtro de grupo es un "grupo de grupos".<br>
<br>
Los Filtros de Grupo son muy útiles en varias funciones de Cyclos. <br>
Se pueden realizar cálculos estadísticos y búsquedas sobre Filtros de
grupo.
<i>¿Dónde los encuentro?</i><br>
Filtros de Grupo se encuentra en "Menú: Usuarios & Grupos> Filtros de
grupo".
<hr>

<span class="admin"> 
<a name="group_filter"></a>
<h3>Modificar / Agregar filtro de grupo</h3>
En esta ventana usted puede modificar o crear un nuevo <a
	href="#group_filters"><u>filtro de grupo</u></a>.<br>
<br>
Para modificar un filtro de grupo existente, primero debe hacer click en
el botón "Modificar".<br>
Para confirmar los cambios efectuados, haga click en el botón "Aceptar".<br>
<br>
Los campos disponibles son:
<ul>
	<li><b>Nombre:</b> Nombre del filtro de grupo.
	
	<li><b>URL raiz de la aplicación:</b> Aquí deberá especificar una dirección URL en el caso de que desee que los usuarios 
	de este grupo de filtro posean su propia página de acceso (login) a Cyclos.<br>
	Esta URL se utiliza cuando se generan los enlaces/link que se incluyen en correos electrónicos.
	Por ejemplo, el enlace/link en la confirmación de registro vía e-mail.<br>
	Si usted define una dirección URL en el filtro de grupo, sobrescribirá la URL raíz de la aplicación definida en la configuración local.
	
	<li><b>Nombre en la página de acceso:</b> Esta opción sólo aparecerá si usted ha personalizado la página de inicio de sesión/acceso (login.jsp), 
	para este filtro de grupo (en la ventana de archivos personalizados).<br>
	A la página de inicio de sesión/acceso (de grupo) personalizada se puede acceder colocando el nombre de la página de inicio de sesión, después 
	de la página de inicio de sesión "global", con un signo de interrogación entre medio. 
	El nombre de la página de inicio de sesión no puede tener espacios.<br>
	Un ejemplo podría ser:<br>
	http://www.sudominio.org/cyclos?nombredesupaginadeiniciodegrupo.<br>
	<br>
	Tenga en cuenta que también es posible especificar un nombre de página de inicio de sesión por 
	<a href="${pagePrefix}groups#group_details"><u>grupo</u></a>.
		
		
	<li><b>URL de la página contenedora:</b> Este campo se utiliza si usted desea acceder a Cyclos desde dentro de un sitio o página Web.<br>
	Esta configuración funciona de la misma forma que la URL de la página contenedora global.
	(ver <a href="${pagePrefix}settings#local"><u>Configuración - Configuración local</u></a>), pero sólo para este filtro de grupo. <br>
	
	En este campo deberá poner la página que abre el iframe o frame-set que incluye Cyclos. 
	Por ejemplo: http://www.sudominiodegrupo.org/cycloswrapper.php<br>
	<br>
	Tenga en cuenta que también es posible especificar una URL de página contenedora por 
	<a href="${pagePrefix}groups#group_details"><u>grupo</u></a>.
	
	<li><b>Descripción:</b> Descripción utilizada para clarificar el uso del filtro de grupo.
	
	<li><b>Mostrar en perfil:</b> Si esta opción es seleccionada, el
	filtro de grupo será mostrado en el <a href="${pagePrefix}profiles"><u>perfil</u></a> del miembro.
	
	<li><b>Grupos:</b> Es la opción más importante del formulario. 
	Aquí usted debe seleccionar los grupos integrantes del filtro.
	
	<li><b>Visible por:</b> Permite seleccionar los grupos que podrán
	visualizar el filtro de grupo en las búsquedas de miembros y anuncios del sistema.<br>
	Note que si la casilla de verificación "Mostrar en perfil" es
	seleccionada, el filtro de grupo siempre será visible en el perfil,
	independientemente de los grupos seleccionados en "Mostrado por". 
	Por lo tanto esta configuración afecta únicamente la función de búsqueda.
</ul>
<hr class="help">
</span> <span class="admin"> <a name="manage_group_filters"></a>
<h3>Administrar filtros de grupo</h3>
Esta página le permite administrar los <a href="#group_filters"><u>filtros
de grupo</u></a> en el sistema.
<ul>
	<li><b>Modificar</b> (o visualizar) un filtro de grupo existente
	mediante el ícono de Modificación <img border="0"
		src="${images}/edit.gif" width="16" height="16">&nbsp;
	correspondiente.

	<li><b>Eliminar</b> un filtro de grupo mediante el ícono de
	Eliminación <img border="0" src="${images}/delete.gif" width="16"
		height="16">&nbsp; correspondiente.

	<li><b>Crear</b> un nuevo filtro de grupo haciendo click en el
	botón Aceptar correspondiente a la etiqueta "Agregar nuevo filtro de
	grupo".
</ul>
<hr class="help">
</span> <span class="admin"> <a
	name="manage_group_filter_customized_files"></a>
<h3>Archivos personalizados (para filtro de grupo)</h3>
Con esta función se puede configurar <a
	href="${pagePrefix}content_management"><u>archivos
personalizados</u></a> para un <a href="#group_filters"><u>filtro de
grupo</u></a>. Esto significa que cada grupo puede poseer su propia
personalización, como ser el diseño (colores, estilos), logo, y páginas
como noticias, contactos, manuales etc. <br>
Si no se crea ninguna personalización para un grupo o filtro de grupo
específico, se mostrarán las páginas y diseño por defecto. <br>
La personalización de grupo sobrescribe a la de filtro de grupo, por lo
que si son definidas ambas, se mostrará primero la configuración del
grupo, y si no tiene, la del filtro de grupo. Si el filtro tampoco posee
archivos personalizados, mostrará los del sistema por defecto <br>
Las opciones del formulario son:
<ul>
	<li><b>Visualizar</b> como se verá el resultado, mediante el ícono
	<img border="0" src="${images}/view.gif" width="16" height="16">&nbsp;
	correspondiente.
	<li><b>Modificar</b> un archivo personalizado mediante el ícono <img
		border="0" src="${images}/edit.gif" width="16" height="16">&nbsp;
	correspondiente.
	<li><b>Eliminar</b> un archivo personalizado mediante el ícono <img
		border="0" src="${images}/delete.gif" width="16" height="16">&nbsp;
	correspondiente.
	<li><b>Crear</b> un archivo personalizado mediante el botón "Crear
	nuevo archivo".
</ul>
<hr class="help">
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

