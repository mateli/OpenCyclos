<div style="page-break-after: always;">
<a name="operators_top"></a>
<br><br>
Esta función le permite a los miembros definir Operadores del sistema.<br>
Los Operadores del sistema son una especie de sub-nivel de los miembros, a los que se les permite 
realizar en Cyclos algunas tareas en nombre del miembro al que pertenecen.<br>
<br>
Los operadores no poseen una cuenta propia (si poseen un código de usuario, una contraseña de acceso y una contraseña de transacción), 
y sólo tienen acceso a Cyclos para realizar algunas operaciones. 
El concepto de operador es incorporado justamente con el fin de restringir acciones y operaciones de usuarios.
<br>
Piense en una pequeña empresa con tres empleados, donde cada empleado se convierta en un operador de la cuenta de la empresa, 
o en un supermercado, donde cada cajero sería un operador. <br>
<br>
Así como los miembros, los operadores se organizan en grupos. <br>
<br>
Cada miembro puede definir su propio grupo de operadores, lo que le permite a un miembro 
crear diferentes niveles de operadores (grupos) con diferentes permisos. <br>
Usted puede, por ejemplo, crear un super-operador, al que se le permite realizar los pagos, 
y unos pocos operadores que sólo puedan gestionar publicidad (anuncios). <br>
<br>
Cada uno de los pagos realizados por el operador tendrá un campo adicional "realizado por:"
, y el miembro tendrá la posibilidad de realizar búsquedas de pagos por el operador.

<span class="member notOperator"> 
<i> ¿Dónde los encuentro? </i> <br>
	Las funciones de los operadores pueden ser accedidas a través del menú principal, en el "Menú: Operadores". <br>
	Esta sección del menú principal, contiene varios sub-menús que brindan acceso a la funcionalidad del operador:
	<ul>
	<li> <b> Operadores: </b> Permite la búsqueda de operadores. Usted también puede crear un nuevo operador aquí.
	<li> <b> Operadores conectados: </b> Muestra los operadores que están conectados.
	<li> <b> Grupos de operadores: </b> Le permite definir distintos niveles de Operadores.
	<li> <b> Campos personalizados: </b> Le permite crear campos especiales (personalizados) para los Operadores.
</ul>
</span>

Los operadores pueden ingresar a Cyclos a través de una página de acceso (login) especial, alcanzada a través de la
página de acceso normal, presionando el enlace "Iniciar sesión como operador" 
(el módulo de operadores debe ser activado en la configuración del sistema). <br>
<br>
<span class="member"> Los operadores pueden realizar sus acciones relacionadas con el miembro
mediante una opción especial del menú principal, "Operaciones de miembro", sólo visible para los operadores.
A través de esta opción, el operador puede acceder a todas las funciones que normalmente corresponden al
"Menú principal: Personal" para los miembros. </span>

<br><br>
<span class="admin">
<i> ¿Cómo hacerlos funcionar? </i> <br>
Los operadores deben ser habilitados a través de los 
<a	href="${pagePrefix}groups#manage_group_permissions_member"><u>permisos</u></a>
de grupo, en el bloque "Operadores", marcando la casilla "Administrar operadores". <br>

Además de establecer los permisos correspondientes, para que los operadores puedan acceder a Cyclos,
debe estar habilitada en la <a href="${pagePrefix}settings#access"><u> Configuración de acceso </u></a>,
la opción, a través de la casilla de verificación "Permitir conexión de operador".
<br><br>

<br><br> 
<br>
Nota: </b> Si usted personaliza la página de acceso, asegúrese de mantener el código 
utilizado para permitir el acceso de los operadores. De lo contrario, 
el vínculo para el acceso del operador podría no ser visible.

<br><br> Nota 2: </b> El módulo Operadores y sus operaciones, son exclusiva responsabilidad del miembro. 
Un administrador no puede gestionar/administrar los operadores de un miembro. 
La administración lo único que puede hacer es desconectar operadores de los 
<a href="${pagePrefix}user_management#connected_users_result"><u> usuarios conectados</u></a> al sistema.
</span>

<span class="member notOperator">
<hr>
<a name="search_operator"></a>
<h3> Búsqueda de operadores </h3>
En esta página usted puede buscar a sus operadores (que haya registrado) existentes en el sistema. <br>
<br>
Puede utilizar "Palabras clave" y funciona de la misma manera que la búsqueda de miembros. <br>

En el campo de selección de Grupo, usted puede dejar "Todos los grupos", o seleccionar uno o más grupos de operadores a buscar.<br>
<br>
Haga click en el botón "Búsqueda" para mostrar los resultados obtenidos.
<br><br> 
Usted también puede crear un nuevo Operador, seleccionando el grupo al que pertenecerá en la lista desplegable "Crear nuevo operador",
ubicado en la parte inferior derecha de la ventana.<br>
Esta lista desplegable sólo estará visible si NO existe ningún resultado en la ventana de búsqueda.
<hr class='help'>
</span>

<span class="member notOperator"> <a name="search_operator_result"></a>
<h3> Resultados de la búsqueda de operadores </h3>
Esta página mostrará la lista de resultados de la búsqueda de operadores. 
Al hacer click en el nombre o en el código de usuario del operador, se abrirá la página de su perfil.
<hr class="help">
</span>

<span class="member notOperator"> <A NAME="create_operator"></A>
<h3> Crear un nuevo operador </h3>
En esta página usted puede crear un nuevo Operador en el sistema.<br> 
Todos los campos marcados con un asterisco (<font color="red">*</font>) de color rojo son obligatorios. <br>
<br>
Luego de completar los campos requeridos, usted puede:
<ul>
	<li> Grabar e ir directamente al perfil del nuevo operador (botón <b>"Guardar y abrir perfil de operador"</b>); 
	<li> Simplemente añadir el nuevo operador (botón <b>"Guarde e inserte un Nuevo operador"</b>).
</ul>
<hr class='help'>
</span>

<a name="operator_profile"></a>
<span class="member">
<h3> Perfil de operador </h3>
Esta ventana muestra el perfil del Operador.<br> 
La mayoría de los campos NO podrán ser modificados. <br>
Usted debe hacer click en el botón "Modificar", con el fin de realizar cambios;<br>
Cuando finalice, haga click en "Aceptar" para su confirmación. 
</span>

<span class="member notOperator">
<br><br> 
Si este Operador ingresa al sistema en el momento en que usted visualiza esta pantalla,
en el campo "Última conexión" se mostrarán (en rojo) las palabras "Está conectado".
<hr class='help'>
</span>

<span class="member notOperator">
<A NAME="actions_for_operator_by_member"></A>
<h3> Acciones para operador </h3>
Aquí usted puede realizar varias acciones para este Operador. <br>
Esta ayuda le ofrece un resumen de las posibles acciones. <br>
<br>
Si usted desea una explicación más detallada de cada una de éstas acciones, 
consulte su respectiva ayuda específica.

<br><br> 
Las siguientes acciones están disponibles:
<ul>
	<li> <b> Cambiar grupo de permisos: </b> Le permite cambiar el grupo al que pertenece el operador.
	
	<li> <b> Enviar mensaje de correo electrónico: </b> Le permite enviar un mail al operador.<br> 
	Esta acción abrirá el programa gerenciador de su correo electrónico por defecto.
	
	<li> <b> Administrar contraseñas: </b> Le permite cambiar las contraseñas del operador.
	
	<li> <b> Permitir al usuario iniciar sesión ahora: </b> Esta opción es visible únicamente en el caso 
	de que el operador intentó ingresar al sistema con contraseña inválida en varias ocasiones, 
	y su cuenta fue bloqueada temporalmente. <br>
	Normalmente, existe un número máximo de intentos permitidos; y el tiempo de bloqueo de una cuenta es definido 
	por los administradores.<br>
	Si está seguro de que este operador es quien dice ser, y lo desea, puede
	inmediatamente permitirle conectarse a Cyclos, haciendo click en este botón. 
	
	<li> <b> Desconectar usuario conectado: </b> Esta opción sólo es visible cuando el operador está 
	dentro (logueado) de Cyclos en ese momento.<br> 
	Esto también es indicado por el campo "último inicio de sesión" en la parte superior del perfil, 
	donde mostrará	"Está conectado". 
	En este caso, usted puede sacar (desloguear) al operador inmediatamente del programa, haciendo click en este botón. 
	
</ul>
<hr class='help'>
</span>

<span class="member notOperator">
<A NAME="manage_operator_groups"></A>
<h3> Grupos - Administrar grupos de operadores </h3>
Esta página muestra una lista con los <a href="#operators_top"><u>grupos de operadores</u></a> existentes.<br>
<br>
Usted puede realizar las siguientes acciones:
<ul>
	<li> <b>Modificar</b> <img border="0" src="${images}/edit.gif" width="16" height="16">:
	Este ícono lo llevará a una página para realizar ajustes para este grupo.
	<li> <b>Permisos</b> <img border="0" src="${images}/permissions.gif" width="16" height="16">:
	Este ícono lo llevará a la página donde se pueden configurar los
	permisos para este grupo. 
	Este ícono será desactivado (en gris) <img border="0" src="${images}/permissions_gray.gif" width="16" height="16">
	cuando el grupo tenga el estado de "eliminado".
	<li> <b>Eliminar</b> <img border="0" src="${images}/delete.gif" width="16" height="16">:
	Haga click en este ícono para eliminar el grupo. 
	Sólo podrá eliminar un grupo, si el mismo no contiene miembros (operadores).
	<li> <b>Agregar:</b> Para añadir un nuevo grupo de operadores, 
	usted deberá hacer click en el botón "Agregar un nuevo grupo".
</ul>
<hr class='help'>
</span>


<span class="member notOperator">
<A NAME="manage_group_permissions_basic"></A>
<h3> Permisos básicos para el grupo </h3>
En esta ventana usted puede configurar los permisos básicos de un grupo de operadores.<br> 
Los permisos básicos no afectan a otras funciones, por ejemplo, si el operador no puede conectarse (loguearse) al sistema, 
todavía podría recibir pagos.<br> 
<br>
Los siguientes permisos pueden establecerse:
<ul>
	<li> <b> Conectarse: </b> <br>
	Si esta opción NO es marcada, los operadores de este grupo no podrán ingresar a Cyclos.
	<li> <b> Mensaje de invitación: </b> <br>
	Si esta opción es marcada, los miembros de este grupo podrán ver una ventana en la página principal de Cyclos 
	(usuario logueado), con la que podrán invitar a un amigo a probar/conocer el sistema.
</ul>
<hr class='help'>
</span>

<span class="member notOperator"> <A NAME="manage_operator_group_permissions"></A>
<h3> Permisos de operador para el grupo </h3>
En esta ventana usted puede configurar los <a href="${pagePrefix}groups#permissions"><u>
permisos</u></a> para un <a href="#operators_top"><u> grupo de operadores</u></a>. 

Estos permisos son derivados originalmente de su propio grupo de permisos.<br> 
Un operador hará lo que usted le permita, y nunca podrá hacer más de lo que usted puede hacer en el sistema; 
podrá hacer lo mismo o menos. <br>
Por esta razón, es probable que usted no pueda ver todas las opciones enumeradas en esta ayuda. <br>
Utilice los vínculos correspondientes para obtener mayor información sobre el tema.
<br><br> 
Los operadores que pertenecen a este grupo, pueden recibir estos permisos
(dependiendo de la configuración del sistema y de sus propios permisos):
<br><br>

	<b> Cuenta de miembro </b>
	<ul>
		<li> <b> Ver información de cuentas: </b>
		Utilice el cuadro desplegable para seleccionar las cuentas que el operador 
		podrá consultar su información (pagos, saldo, etc.).
		<li> <b> Ver pagos autorizados </b>
		<li> <b> Ver pagos agendados </b>
		
	</ul>

	<b> Anuncios </b>
	<ul>
		<li> <b> Publicar: </b> 
		Si esta opción es marcada, el operador podrá publicar anuncios y 
		se mostrará la opción "Menú: Personal> Anuncios" en su menú principal.
	</ul>

	<b> Lista de contacto </b>
	<ul>
		<li> <b> Administrar : </b> 
		Esta opción permite al operador gestionar la 
		<a href="${pagePrefix}user_management#contacts"><u>lista de contactos</u></a>, 
		es decir, añadir, editar o eliminar miembros de la lista.
		<li> <b> Ver: </b> Permite al operador sólo ver la lista de contactos, no permisos para modificarla.
	</ul>

	<b> Garantías </b> <br>
	Esta opción corresponde a las garantías en Cyclos, donde cada cuenta de balance 
	está garantizado por dinero en efectivo. 
	Usted puede elegir los siguientes permisos:
	<ul>
	<li><b> Comprar con obligaciones de pago: </b>Permite la creación y publicación de obligaciones de pago.</li>
	<li><b> Vender con obligaciones de pago: </b>Permite aceptar obligaciones de pago.</li>
	<li><b> Tipos de garantías permitidos (para Certificaciones): </b>
	Lista con los tipos de garantías que pueden ser utilizados al momento de crear una certificación.</li>
	<li><b> Emitir certificaciones: </b>Permite crear nuevas certificaciones.</li>
	</ul>
	
	<b> Facturas </b> <br>
	En esta sección se puede definir si un operador podrá enviar <a href="${pagePrefix}invoices"><u>facturas</u></a>
	a los demás miembros, ya sea desde un perfil de usuario, o directamente desde la opción "Cuenta" del menú principal. 
	Cuando se seleccionan las "Facturas al sistema", un operador podrá enviarle facturas al sistema desde la opción 
	correspondiente en la sección "Cuenta" del menú principal.
	<ul>
	<li> <b> Ver: </b> Ver las facturas.
	
	<li> <b> Enviar a miembro: </b> Permite enviar facturas a los demás miembros.
	
	<li> <b> Administrar: </b> Esta opción permite al operador gestionar las <a href="${pagePrefix}invoices"><u>facturas</u></a>.
	
	<li> <b> Mostrar opción de menú factura a miembro: </b> Mostrará la opción de facturación a los demás miembros 
	"Factura a miembro", en el menú principal del sistema.
	
	<li> <b> Enviar a sistema: </b> Permite enviar facturas a una cuenta del sistema.
	</ul>

	<b> Préstamos: </b> <br>
	En esta sección se pueden definir los permisos de los 
	<a href="${pagePrefix}loans"><u>préstamos</u></a> para los operadores.
	<ul>
		<li> <b> Ver: </b> Si esta opción es seleccionada, los operadores del grupo 
		podrán ver sus préstamos. Si no se ha seleccionado, la opción no estará visible en el menú.
		<li> <b> Pagar: </b> Seleccione esta opción para permitir al operador realizar pagos de préstamos.
	</ul>

	<b> Mensajes: </b> <br>
	En esta sección usted puede definir en qué medida el
	operador podrá utilizar los <a href="${pagePrefix}messages"><u>mensajes</u></a> en Cyclos.
	<ul>
	<li> <b> Ver: </b> El operador podrá ver los mensajes del sistema.
	<li> <b> Enviar al miembro: </b> El operador está autorizado a enviar mensajes a otros miembros.
	<li> <b> Enviar a la administración: </b> El operador está autorizado a enviar mensajes a la administración.
	<li> <b> Administrar: </b> El operador podrá mover y eliminar mensajes.
	</ul>

	<b> Pagos: </b> <br>
	En este punto usted puede definir los permisos sobre los pagos para los operadores. 
	<ul>
		<li> <b> Pagos a miembro: </b> Elija esta opción en caso de que quiera que los operadores puedan
		efectuar pagos a otros miembros.
		
		<li> <b> Mostrar opción de menú de pago a miembro: </b> Si esta opción es seleccionada,
		los operadores podrán realizar los pagos a otros miembros, a través de la opción "Cuenta" del menú principal.
		
		<li> <b> Pagos a sistema: </b> Elija esta opción en caso de que quiera que los operadores puedan
		efectuar pagos al sistema. Si no se selecciona esta opción, la opción "Pago a sistema" no se mostrará en el menú.
	
		<li> <b> Realizar pago POSweb: </b> Esta opción habilita a un operador a realizar pagos externos POSweb.
		
		<li> <b> Recibir pago POSweb: </b> 
		Elija esta opción en caso de que quiera que los operadores puedan recibir pagos 
		a través de los Puntos de Venta (POSweb).
	
		<li> <b> Cancelar autorizaciones de pago: </b> Esta opción permitirá a los operadores, 
		cancelar pagos una vez que estos han sido creados, pero aún no han sido autorizados.
		
		<li> <b> Cancelar pago agendado: </b> Esta opción permitirá a los operadores,
		cancelar pagos que han sido programados.

		<li> <b> Bloquear pago agendado: </b> Esta opción permitirá a los operadores,
		bloquear pagos agendados temporalmente.

		<li> <b> Pago propio </b>: Si es seleccionado, el operador podrá efectuar pagos
		entre sus propias cuentas. Esta opción sólo tendrá sentido si posee más de una cuenta de operador.
		
		<li> <b> Autorizar o denegar: </b> Permite al operador autorizar o denegar un pago si es el receptor.
		
		<li> <b> Solicitar pagos por otros canales: </b> Permite enviar solicitudes de pago (facturas externas) a otros canales, 
		usted podrá elegir estos canales en el correspondiente cuadro desplegable. 
	</ul>

	<b> Referencias </b> <br>
	Esta opción permite al operador ver o gestionar las 
	<a href="${pagePrefix}references"><u>referencias</u></a>.
	<ul>
		<li> <b> Ver: </b> Ver las referencias.
		<li> <b> Administrar referencias: </b> Permite al operador gestionar las referencias, 
		incluído el permiso para dar referencias sobre otros miembros.
		
		<li> <b> Administrar calificaciones de transacción: </b> Permite al operador gestionar
		las <a href="${pagePrefix}transaction_feedback#feedbacks_summary"><u>calificaciones</u></a> recibidas, 
		incluídos los permisos para calificar transacciones.
	</ul>

	<b> Informes </b> <br>
	<ul>	
		<li> <b> Ver mis informes: </b> Si esta opción es seleccionada, el operador podrá ver
		sus propios <u><a href="${pagePrefix}reports#member_activities">informes</u></a>.
	</ul>
	<hr class='help'>
	</span>

<span class="member notOperator"> <a name="edit_operator_group"></a>
<h3> Modificar grupo de operador </h3>
La configuración de los <a href="#operators_top"><u>grupos de operadores</u></a> se divide en dos partes:
<ul>
	<li> <b> Detalles de grupo: </b> Aquí sólo se puede cambiar el nombre y la descripción del grupo.</li>
	<br>
	<li> <b> Monto máximo por día por tipo de pago: </b> Aquí se pueden definir los importes máximos 
	por día y por tipo de pago.<br> 
	Todos los tipos de pago (transacción) disponibles, son enumerados aquí; 
	para cada tipo de pago, usted puede especificar si existe un límite en
	la cantidad (monto) que un operador puede pagar.<br>
</ul>
Usted debe hacer click en el botón "Modificar", con el fin de realizar cambios; 
cuando finalice, haga click en el botón "Aceptar" para su confirmación.<br> 
<br>
Puede acceder directamente a los permisos de este grupo, haciendo click en el botón "Permisos de grupo".
<hr class='help'>
</span>

<span class="member notOperator">
<A NAME="insert_operator_group"></A>
<h3> Agregar nuevo grupo de operadores </h3>
Esta ventana le permite crear un nuevo grupo de <a href="#operators_top"><u>operadores</u></a>.
<br>
Usted posee las siguientes opciones:
<ul>
	<li> <b> Eliminado: </b> Si usted crea un grupo de operadores "eliminado", los operadores 
	incluídos en él realmente habrán abandonado el sistema. <br>
	Una vez en el "grupo de eliminados", no podrán volver a pertenecer a otro grupo. <br>
	Estos datos permanecerán en la base de datos y usted los podrá ver, pero sólo funcionará como copia de seguridad.
	
	<li> <b> Nombre: </b> Nombre del nuevo grupo.
	<li> <b> Descripción: </b> Descripción del grupo.
	<li> <b> Copiar configuración de: </b> Esta opción solo será visible si existe otro grupo de operadores definido. <br>
	Usted puede especificar un grupo de operadores aquí, luego de realizados los ajustes al nuevo grupo, se copiarán 
	las definiciones desde el grupo especificado.
</ul>
Luego de haber creado el nuevo grupo de operadores, usted debe configurar las propiedades del grupo en la
siguiente pantalla, y también debe establecer los permisos correspondientes.
<hr class='help'>
</span>

<span class="member notOperator">
<a name="manage_group_customized_files"></a>
<h3> Posweb Personalizada </h3>
Usted puede definir el encabezado y el pie de página para la Posweb.<br>
Esta ventana muestra una lista con las personalizaciones para este grupo.
Usted tiene las siguientes opciones:
<ul>
	<li> <b> Modificar</b> un archivo personalizado, a través del ícono de edición 
	<img border="0" src="${images}/edit.gif" width ="16" height="16">.
	
	<li> <b> Ver</b> cómo se verá el resultado de un miembro del grupo, a través del ícono de visualización
	<img border="0" src="${images}/view.gif" width="16" height="16">.
	
	<li> <b> Borrar</b> un archivo personalizado a través del ícono de eliminación 
	<img border="0" src ="${images}/delete.gif" width ="16" height ="16">.
	
	<li> <b> Agregar</b> un nuevo archivo personalizado a través del botón 
	"Agregar nuevo archivo personalizado".
</ul>
<hr class="help">
</span>

<span class="member notOperator"> <a name="customize_group_file"></a>
<h3> Modificar encabezado y pie de página Posweb </h3>
En esta página usted podrá personalizar el encabezado y el pie de la página Posweb. <br>
A esta página se puede acceder por parte de los operadores para realizar y recibir pagos. <br>
(la URL del Posweb es algo así como:  www.domain.com / cyclos / posweb) <br>
Luego de que el operador registra el encabezado y el pie de página, se mostrarán 
por encima y por debajo de la ventana de pago.
<hr class="help">
</span>

<span class="member notOperator">
<A NAME="change_group_operator"></A>
<h3> Cambiar grupo de operador </h3>
En esta ventana usted puede asignar un <a href="#operators_top"><u>operador</u></a> a otro grupo. 
Esto significa que el operador adquirirá los permisos del otro grupo. 

Luego de haber completado el formulario, haga click en el botón "Modificar" 
para confirmar el cambio.
<br>
<br><br> Haga click en el botón "Eliminar permanentemente", para eliminar el operador. 
Esto sólo será posible si el operador no ha realizado ninguna transacción aún. <br>
En caso contrario, tendrá que asignarlo a un "grupo eliminado", lo que significa
que el operador no podrá realizar más acciones (ni siquiera de acceso), pero sus
acciones en el pasado (historia) podrán ser accedidas por usted.
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