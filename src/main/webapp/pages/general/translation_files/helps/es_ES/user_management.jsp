<div style="page-break-after: always;">
<a name="user_management_top"></a>
<span class="admin">
<br><br>

Estas páginas contienen las funciones de administración de usuarios.
Se pueden utilizar para la búsqueda de miembros, registro de nuevos miembros, obtener una visión general de los
usuarios actualmente registrados, o ver otra información sobre el estado de los miembros.


<i> ¿Dónde las encuentro? </i> <br>
La siguiente lista muestra las funcionalidades disponibles y dónde se pueden encontrar:
<ul>
<li> <b> Búsqueda y creación de miembros: </b> Se puede realizar a través del 
"Menú: Usuarios & Grupos> Administrar usuarios". Esta opción incluye la búsqueda de brokers.
<li> <b> Búsqueda y creación de administradores: </b> Se puede realizar a través del 
"Menú: Usuarios & Grupos> Gestionar administradores".
<li> <b> <a href="#connected"><u>Usuarios conectados</u></a>: </b> Se puede acceder a través del
"Menú: Usuarios & Grupos> Usuarios conectados".
<li> <b> <a href="#bulk_actions"><u>Acciones en masa</u></a>: </b> Se puede acceder a través del
"Menú: Usuarios & Grupos> Acciones en masa".
</ul>
</span>

<span class="broker">
<ul>
	<li><b>Creación y registro de miembros: </b> 
	Siempre y cuando posea los permisos correspondientes, 
	usted podrá acceder a realizar esta acción a través del "Menú: Brokering> Registrar miembro".
</ul>
</span>

<span class="admin">
<A NAME="create_user"></A>
<h3>Registrar nuevo miembro</h3>
Aquí usted puede ingresar la información correspondiente al nuevo miembro.<br> 
El formulario es sencillo y los campos disponibles dependerán de su configuración. 
<br>
El miembro pertenecerá al <a href="${pagePrefix}groups"><u>grupo</u></a> indicado en el primer campo. 
<br><br> 
Si existe un broker para el grupo, y este posee permisos de intermediación (brokering) con el grupo del miembro que está creando, 
usted podrá asignarle (opcionalmente) al miembro un agente intermediario (broker) en particular. 
Para ello, usted debe marcar la casilla de verificación en la parte inferior, etiquetada "Asignar broker".
<br><br> 
Luego de completar los datos requeridos y deseados, usted puede:
<ul>
	<li>Confirmar el registro del nuevo miembro (botón "Guardar nuevo miembro"), o
	<li>Confirmar el registro y acceder directamente al 
	<a href="${pagePrefix}profiles"><u>perfil</u></a> del nuevo miembro (botón "Guardar y abrir perfil").
</ul>
<hr class="help">
</span>

<span class="broker">
<a name="create_user_for_broker"></a>
<h3> Brokering - Crear nuevo miembro </h3>
Aquí usted puede ingresar la información correspondiente al nuevo miembro.<br> 
El formulario es sencillo y los campos disponibles dependerán de su configuración. 
<br>
El nuevo usuario será asignado automáticamente como uno de sus miembros (usted será su Broker) al finalizar el registro.
<br><br> 
Luego de completar los datos requeridos y deseados, usted puede:
<ul>
	<li>Confirmar el registro del nuevo miembro (botón "Guardar nuevo miembro"), o
	<li>Confirmar el registro y acceder directamente al 
	<a href="${pagePrefix}profiles"><u>perfil</u></a> del nuevo miembro (botón "Guardar y abrir perfil").
</ul>
<br><br>
Por el registro de un nuevo miembro, usted puede recibir una 
<a href="${pagePrefix}brokering#commission"><u>comisión</u></a> de forma automática, en función de la configuarción de su sistema. 
La comisión dependerá del volúmen de transacciones de sus miembros.
<br>
A través del "Menú: Brokering> Miembros", usted puede gestionar sus miembros registrados y su actividad en el sistema.
<hr class="help">
</span>

<span class="member">
<A NAME="search_member_by_member"></A>
<h3> Búsqueda de miembros </h3>
<i>(Menú: Buscar> Miembros)</i><br>
<br>
En esta página usted puede buscar miembros existentes en el sistema.<br>
"Búsqueda de miembros" recorre y busca en todos los campos del perfil de los miembros. <br>
Usted puede además incluir/utilizar "Palabras clave" en su búsqueda. <br>
<br>
Diversos "operadores" pueden ser utilizados en la búsqueda de miembros.<br>
Los más comúnmente utilizados son:
<ul>
	<li> <b> * (asterisco) </b> <br>
	El asterisco actúa como "comodín", le permite buscar por partes de palabras (campos).
	Por ejemplo, una búsqueda sobre ban*, devolverá todos los usuarios que posean la 
	combinación "ban" en alguno de sus campos, como por ejemplo: Banco Hipotecario.
	 </li>
	<br>
	<li> <b> - / no </b> <br>
	Una búsqueda con una palabra clave directamente precedida por un signo de menos ("-")
	o precedido por un "no" y un espacio, obtendrá resultados que no contengan dicha palabra clave.</li>
	<br>
	<li> <b> o </b> <br>
	La búsqueda debe devolver resultados con cualquiera de las palabras ingresadas, la anterior al "o", 
	o la palabra clave posterior al "o".</li>
	<br>
	<li> <b> y </b> <br>
	La búsqueda debe devolver los resultados que contegan todas la palabras ingresadas; 
	la palabra clave anterior al "y", y la palabra clave posterior al "y".</li>
</ul>
<hr class="help">
</span>

<span class="member"> 
<A NAME="search_member_result"></A>
<h3> Resultado de búsqueda de miembros </h3>
Esta ventana muestra el resultado de la búsqueda de miembros.<br> 
Seleccionando la opción "Código de miembro" o "Nombre" de un miembro, se accederá a su 
<a href="${pagePrefix}profiles"><u>perfil</u></a>.
<br>
Seleccionando la imagen del miembro, dicha imagen se mostrará en tamaño completo en una ventana emergente.
<hr class="help">
</span>

<span class="admin"> 
<A NAME="search_member_by_admin"></A>
<h3> Búsqueda de miembros </h3>
<i>(Menú: Usuarios & Grupos> Administrar usuarios)</i><br>
<br>
En esta página usted podrá buscar miembros registrados en el sistema, o registrar nuevos miembros.
<br><br> 
Para buscar miembros, usted puede completar diversos campos/filtros e incluir además "Palabras clave" en su búsqueda.<br>
Si hace click en el botón "Búsqueda" sin introducir ningún filtro, se mostrarán todos los miembros existentes en el sistema. <br>
<ul>
	<li> <b> Filtros de grupo: </b> Aquí usted puede especificar un 
	<a href="${pagePrefix}groups#group_filters"><u>filtro de grupo</u></a> (si existen definidos en el sistema).
	
	<li> <b> Grupo: </b> En este punto puede filtrar por
	<a href="${pagePrefix}groups"><u>grupos</u></a> de usuarios.
	
	<li> <b> Código de broker / Broker: </b> Escriba aquí el Código de usuario o el Nombre de un broker; 
	su página de resultados mostrará a los miembros pertenecientes a dicho broker.
	
	<li> <b> Activación (Desde / Hasta): </b> Usted puede buscar por el rango de fechas en el que un miembro ingresó al sistema.<br> 
	Puede utilizar el calendario, haciendo click en el ícono <img border="0" src="${images}/calendar.gif" width="16" height="16">&nbsp;.
	
	<li> <b> [Campos personalizados]: </b> Si el Administrador define nuevos campos personalizados y especifica que sean 
	mostrados en la búsqueda de miembros; aparecerán a continuación los campos/filtros correspondientes. 
	
	<li> <b> Crear un miembro: </b> Para registrar un nuevo miembro en el sistema, usted deberá utilizar esta opción.
Aquí seleccionará el grupo al que desea que el nuevo miembro pertenezca, y será redireccionado
al <a href="#create_user"><u>Formulario de registro</u></a>.
<br>
</ul>
<hr class="help">
	
</span>
 
<span class="admin">
<A NAME="admin_search_member_result"></A>
<h3> Resultado de búsqueda de miembros </h3>
Esta ventana muestra el resultado de la búsqueda de miembros.<br> 
Seleccionando el "Código de miembro" o el "Nombre" del miembro, se abrirá su <a href="${pagePrefix}profiles"><u>perfil</u></a>.
Seleccionando el botón Atrás en la página del perfil, volverá al resultado de la búsqueda.
<br><br> 
Usted tiene la opción de imprimir el resultado de la búsqueda, haciendo click en el ícono de Impresión 
<img border="0" src="${images}/print.gif" width="16" height="16">, ubicado en la parte superior derecha de la ventana, al lado del ícono de Ayuda.<br> 
<br><br> 
Otra posibilidad es descargar los resultados de la búsqueda a un archivo, haciendo click en el ícono Guardar 
<img border="0" src="${images}/save.gif" width="16" height="16">.
Los resultados se guardarán en <a href="${pagePrefix}loans#csv"><u>formato CSV</u></a>, y 
contendrán todos los campos existentes en el <a href="${pagePrefix}profiles"><u>perfil</u></a> del miembro.
(dependiendo del usuario, algunos campos pueden no ser visibles en esta ventana de resultados). <br>
<br>
En la <a href="${pagePrefix}settings#local"><u>configuración local</u></a> del sistema, usted puede especificar si desea mostrar 
los nombres de los campos en los cabezales (primera línea) de las columnas.<br>
<br><br> 

Nota: En la función de Reportes, usted será capaz de obtener 
<a href="${pagePrefix}reports#member_lists"><u>listas de miembros</u></a> más específicas y con mayor información.
Por ejemplo, listas de miembros con datos adicionales como los saldos de sus cuentas, número de anuncios, etc.
<hr class="help">
</span>

<span class="broker admin"> 
<a name="search_pending_member"></a>
<h3>Miembros pendientes</h3>
Los miembros "pendientes" son miembros que se han registrado, pero todavía deben confirmar (activar) su registro 
respondiendo un correo electrónico de validación.<br> 
Sólo después de dicha confirmación el miembro podrá conectarse a Cyclos.
<br>
Luego de un tiempo específico, los miembros pendientes se eliminarán automáticamente del sistema (y de la lista).<br>
Las tres opciones de registro (auto-registro en la página de registro público, registro por parte del broker y por la administración) 
pueden solicitar confirmación por correo electrónico.<br>
<br>
En esta página usted puede buscar Miembros pendientes. Puede buscar por Nombre, Grupo y por Fecha de registro.<br>
El filtro por Grupo (grupo de miembros), no significa que los miembros pertenecen a dicho grupo, 
pero indica el grupo al que pertenecerán luego de la validación de registro.<br>
</span>

<span class="admin"> 
Usted también puede buscar por el broker. <br>
Esto significa que la búsqueda sólo mostrará los miembros registrados por el broker seleccionado. <br>
Tenga en cuenta que esto sólo funcionará si los miembros que son registradas por un broker, necesitan confirmar (activar) su registro. <br>
Esta es una <a href="${pagePrefix}groups#group_registration_settings"><u>configuración de grupo</u></a> opcional. <br>
El período máximo de registro es definido en la <a href="${pagePrefix}settings#local"><u>configuración local</u></a> del sistema. <br>
<br>
<hr class="help">
</span>

<span class="broker admin"> <a name="search_pending_member_result"></a>
<h3>Resultado de búsqueda de miembros pendientes</h3>
Esta ventana muestra el resultado de la búsqueda de miembros pendientes.<br>
Usted debe ser consciente de que estos miembros NO son miembros "activos" (no pueden ingresar y no son visibles en el sistema). <br>
<br>
Sólo en casos excepcionales usted deseará eliminar un miembro de la lista.<br>
Si elimina un miembro pendiente, significa que el miembro no será capaz de confirmar (activar) su registro. <br>
Usted puede visualizar y modificar los datos de un miembro pendiente, a través de su perfil, en ese caso, 
puede enviarle nuevamente el correo electrónico de validación.
<hr class="help">
</span>

<span class="broker admin"> <a name="pending_member"></a>
<h3>Detalle del miembro pendiente</h3>
En esta página usted puede visualizar y modificar los datos del perfil del miembro pendiente, 
y si es necesario, enviarle la validación de correo electrónico nuevamente.
<hr class="help">
</span>

<span class="admin">
<a name="search_admin"></a>
<h3> Buscar administradores </h3>
<i>(Menú: Usuarios & Grupos> Gestionar administradores)</i><br>
En esta página usted puede buscar administradores, y registrar nuevos administradores en el sistema.<br>
<br><br> 
Para buscar, sólo debe especificar una Palabra clave, y/o limitar la
búsqueda a un determinado Grupo de administradores. 
Si hace click en el botón "Búsqueda" sin introducir ningún filtro/campo, 
aparecerá una lista de todos los administradores del sistema.<br>
<br><br> 
Para registrar un nuevo administrador, deberá utilizar el menú desplegable "Crear un miembro", 
ubicado en la parte inferior izquierda del formulario. 
Aquí seleccionará el <a href="${pagePrefix}groups#admin_groups"><u>grupo</u></a> al que desea que el nuevo administrador pertenezca, 
y será redireccionado al <a href="#create_user"><u>Formulario de registro</u></a> para nuevos administradores.
</span>

<span class="admin">
<a name="search_admin_result"></a>
<h3> Resultado de búsqueda de administradores </h3>
Esta ventana presenta los resultados de la búsqueda de administradores.<br> 
Usted puede hacer click en el Código de usuario del administrador o en su Nombre, para obtener más detalles sobre el administrador.
<hr class="help">
</span>

<span class="admin">
<a name="create_admin"></a>
<h3> Registrar nuevo administrador </h3>
Aquí usted puede registrar un nuevo Administrador.<br>
Se <b>recomienda</b> que todas las personas que realizan tareas de administración, posean una cuenta propia de acceso. 
No es aconsejable que diferentes personas compartan una única cuenta de administrador.<br> 
Esto facilita el mantenimiento de los permisos, posibles errores, y baja de cuentas cuando las personas abandonan la organización.
<br><br> 
El formulario es simple y directo. 
Los campos marcados con un asterisco (<font color="red">*</font>) rojo, son obligatorios.
<br><br>
Luego de completar los datos requeridos, usted tiene la opción de: 
<ul>
	<li>Guardar e ingresar un nuevo administrador (botón "Guardar nuevo administrador"),</li> 
	<li>Guardar y abrir directamente el 
	<a href="${pagePrefix}profiles"><u>perfil</u></a> del nuevo administrador (botón "Guardar y abrir perfil").</li>
</ul>
</span>

<span class="admin">
<hr>
<a name="connected"></a>
<h2> Usuarios conectados </h2>
<i>(Menú: Usuarios & Grupos> Usuarios conectados</i><br>
Esta página le permite obtener una visión general de los usuarios (miembros, operadores, administradores y brokers) 
registrados que se encuentren utilizando el sistema en el momento.

<hr>
</span>

<span class="admin">
<A NAME="connected_users"></A>
<h3> Usuarios conectados </h3>
Esta ventana le permite especificar que <a href="${pagePrefix}groups#group_categories"><u>tipo</u></a> 
de usuarios conectados usted desea buscar y visualizar (en la ventana inferior).<br> 
<br>
El menú desplegable "Mostrar", le permite seleccionar entre los usuarios del tipo: 
<ul>
	<li>Administrador</li> 
	<li>Miembro</li>
	<li>Broker</li>
	<li><a href="${pagePrefix}operators"><u>Operador</u></a></li>
</ul>
Haga click en el botón "Aceptar" para mostrar los resultados de su búsqueda.
<hr class="help">
</span>
 
<A NAME="connected_users_result"></A>
<span class="admin">
<h3> Resultado de búsqueda de usuarios conectados </h3>
Esta ventana muestra una lista con los usuarios conectados al sistema, de acuerdo a la selección realizada en la ventana superior. <br>
En la lista usted podrá hacer click sobre el miembro (Código o Nombre) para abrir su perfil. <br>
<br>
Los administradores que posean permisos, podrán desconectar a un miembro, haciendo click en su icono de Eliminación
<img border="0" src="${images}/delete.gif" width="16" height="16">&nbsp; correspondiente, ubicado en la última columna. 
</span>

<span class="member">
<h3> Resultado de búsqueda de operadores conectados </h3>
Esta función muestra una lista con los <a href="${pagePrefix}operators"><u>operadores</u></a> conectados en el sistema.<br>
Usted puede hacer click sobre el Operador (Código o Nombre) para acceder a su perfil.<br> 
Si posee los permisos necesarios, usted puede desconectar a un operador haciendo click en su ícono de Eliminación 
<img border="0" src="${images}/delete.gif" width="16" height="16"> correspondiente.
</span>

<span class="admin">
<hr>
<a name="bulk_actions"></a>
<h2> Acciones en masa </h2>
<i>(Menú: Usuarios & Grupos> Acciones en masa)</i><br>
Las acciones en masa permiten al Administrador ejecutar acciones sobre grupos de usuarios.

<hr>
</span>

<span class="admin">
<A NAME="bulk_actions_filter"></A>
<h3> Filtro de miembros para acciones en masa </h3>
Esta ventana le permite al Administrador especificar los usuarios a los cuáles se le aplicarán las
<a href="#bulk_actions"><u>acciones en masa</u></a>. <br>
<br>
Los campos son combinables y poseen una lógica de búsqueda, por lo que deberá tener cuidado de no excederse en su 
especificación, ya que podría obtener un resultado vacío.
<ul>
    <li> <b> Filtro de grupo: </b> Aquí puede especificar el <a href="${pagePrefix}groups#group_filters"><u>filtro de grupo</u></a> deseado.
	<li> <b> Grupo: </b> Aquí usted puede especificar los <a href="${pagePrefix}groups"><u>Grupos</u></a> de usuarios deseados. 
	Verifique que éste no se se encuentre en conflicto con el filtro de grupo seleccionado. 
	Usualmente se especifica el grupo ó el filtro de grupo, pero no ambos.  
	<li> <b> Código de broker / Nombre de broker: </b> Si desea que las acciones se apliquen a todos los miembros
	pertenecientes a un determinado broker, especifique el broker aquí, ya sea por su código o por su nombre.
	<li> <b> ... :</b> El resto del formulario es formado por <a href="${pagePrefix}custom_fields"><u>campos personalizados</u></a>, 
	que son definidos en los perfiles de los miembros.
</ul>
Una vez especificados sus criterios para las acciones en masa, usted puede visualizar la lista de los miembros incluídos
haciendo click en el botón "Vista preliminar", ubicado en la parte inferior derecha de la ventana. 
</span>

<span class="admin"> <A NAME="bulk_action"></A>
<hr>
<h3> Acción </h3>
En esta ventana se especifica la <a href="#bulk_actions"><u>acción en masa</u></a> a ejecutar.<br>
Las siguientes opciones están disponibles:
<ul>
	<li> <b> Alterar grupo: </b> Esta opción cambiará de grupo a los miembros seleccionados. <br>
	Una vez elegida esta opción, se le solicitará el ingreso del Nuevo 
	<a href="${pagePrefix}groups"><u>grupo</u></a> y sus Comentarios.<br> 
	Luego de hacer click en el botón "Aceptar", todos los miembros implicados serán trasladados al nuevo grupo.<br>
	<br>
	<li> <b> Modificar broker: </b> Esta opción cambiará el <a href = "${pagePrefix}brokering"><u>broker</u></a> 
	de los miembros seleccionados.<br>
	Una vez elegida esta opción, se le solicitará el ingreso del Nuevo broker.<br>
	
	Si usted marca la casilla de verificación "Comisión suspendida", todos los pagos de 
	<a href="${pagePrefix}brokering#commission"><u>comisión</u></a> en ejecución o abiertos, serán descartados. <br>
	Usted puede utilizar esta opción, si piensa que el nuevo broker no tiene derecho a recibir los pagos de comisiones 
	generados por las acciones del broker anterior.<br>
	Usted también debe ingresar sus Comentarios. <br>
	Luego de hacer click en el botón "Aceptar", todos los miembros implicados tendrán el nuevo broker asignado.<br>
	<br>
	<li><b>Generar tarjeta:</b> 
	Esta opción generará la tarjeta para los miembros seleccionados.<br>
	Para que las tarjetas sean generadas, debe existir un <a href="${pagePrefix}access_devices#edit_card_type"><u>tipo de tarjeta</u></a> 
	y los miembros seleccionados deben pertenecer a un grupo de usuarios que tenga habilitado el "Tipo de tarjeta" 
	en el bloque "Configuración de acceso" en la 
	<a href="${pagePrefix}groups#edit_member_group"><u>configuración del grupo</u></a>.
	<br>
	Es posible regenerar tipos de tarjetas para miembros que ya poseen una tarjeta, 
	seleccionando una o ambas casillas de selección.<br>
	Puede optar por volver a crear tarjetas para los miembros que tienen tarjetas 
	en el estado pendiente y/o estado activo. En ambos casos la tarjeta existente será cancelada.
	<li><b>Activar/Desactivar canales:</b> Con esta opción se puede activar o desactivar 
	los canales para un grupo seleccionado.<br>
</ul>

<A NAME="import_members"></A>
<hr>
<h3> Importar miembros </h3>
Con esta función es posible importar miembros al sistema.<br>
Opcionalmente, se puede establecer un crédito inicial para los miembros y/o generar un pago inicial, de o para, una cuenta del sistema. <br>
<br>
Los números y las fechas deben presentar un formato acorde a la configuración local establecida. <br>
Los campos se identifican por el nombre de la columna. <br>
Las columnas pueden estar ubicadas en cualquier orden (no hay columna uno o dos, siempre y cuando los nombres sean correctos).<br>
Si un campo es opcional, usted puede omitir toda la columna o establecer la columna con valor vacío. <br>
<br>
Las siguientes columnas o campos son compatibles:
<ul>
	<li><b>name</b> (requerido):<br> Nombre completo del miembro.
	<li><b>username</b> (requerido):<br>
	Este es el código de miembro, es único y dará un error si un usuario con este código ya existe en el sistema. 
	En el caso de que el código de miembro sea generado automáticamente, la columna no es necesaria/requerida 
	y Cyclos generará los códigos de inicio de sesión (acceso).
	<li><b>password</b> (opcional):<br>
	La contraseña de acceso del miembro. Deberá cambiarla en su primer acceso al sistema.
	<li><b>email</b> (obligatorio u opcional dependiendo de la configuración de Cyclos): <br>
	Debe poseer el formato de una dirección de correo electrónico.
	<li><b>balance</b> (opcional): <br>
	Es el saldo de la cuenta inicial. Sólo se utiliza cuando es seleccionado un tipo de cuenta. 
	En el caso de que usted seleccione un tipo de cuenta, puede especificar los tipos de transacciones 
	(de miembro a sistema para saldos negativos, y de sistema a miembros para saldos positivos). <br>
	Si utiliza esta opción, usted debe estar seguro de que existe suficiente crédito en las cuentas que serán debitadas.
	<li><b>creditlimit</b> (opcional): <br>
	Límite de crédito (negativo) de las cuentas. Si está vacío, tomará por defecto los valores de la configuracion del grupo.
	<li><b>uppercreditlimit</b> (opcional): <br>
	Límite de crédito superior de la cuentas. Si está vacío, tomará por defecto los valores predeterminados en la configuración del grupo.
	<li><b>[nombre interno de campo personalizado]</b> (opcional): <br>
	El nombre interno de un campo personalizado está relacionado con el grupo seleccionado. 
	En el caso de que un campo sea una lista (enumerada), la importación de los miembros sólo tendrá éxito si cuenta con un valor existente. 
	Por ejemplo, si existe un campo personalizado "zona", con tres posibles áreas: "centro", "sur" y "norte", con otras áreas no se importará.
	Los miembros con áreas vacías serán importados (salvo que el campo esté definido como "necesario").
	<li><b>[tipo de registro de miembro]</b> (opcional): <br>
	Son los valores de los registros para miembros. Un ejemplo para la base de datos predeterminada es el "Remark.comments"(comentarios de observación). 
	Donde los comentarios son un registro del tipo de observación.
	Este campo debe ser el nombre interno (especificado en la configuración del campo). <br>
	Tenga cuidado de que si usted desea importar un tipo de registro, todos los campos 
	definidos para este tipo de registro deben ser especificados en el archivo CSV (como una columna).
</ul>

<hr>
<A NAME="imported_members_summary"></A>
<h3> Resúmen de la importación de miembros </h3>
Esta página ofrece una visión general sobre la importación de miembros.<br> 
En esta etapa NO existe aún ningún miembro procesado.<br> 
<br>
Para visualizar el estado de los miembros a importar, puede seleccionar el enlace (número) correspondiente a: 
<ul>
	<li>Número total de miembros.</li>
	<li>Miembros correctos.</li>
	<li>Miembros con error.</li>
</ul>
Si presiona el botón "Importar", los miembros serán importados.<br>
Es una buena práctica visualizar posteriormente el estado de los miembros importados.

<hr>
<A NAME="imported_member_details"></A>
<h3> Búsqueda de miembros importados </h3>
En esta ventana usted puede buscar en la lista de miembros importados.<br> 
Puede buscar ya sea por número de línea o por el nombre de miembro. 
La opción búsqueda por el nombre del miembro, puede ser realizada por los campos código de miembro o nombre de miembro. <br>

<hr>
<A NAME="imported_member_details_result"></A>
<h3> Resultado de búsqueda de miembros importados </h3>
Esta ventana muestra el resultado de las importaciones de miembros. <br>
En el caso de errores en la importación, se informará el tipo de error.<br> 
En el caso de éxito en la importación, se mostrará el límite de crédito y el saldo de la cuenta.<br>
<br>
Para procesar miembros nuevamente, usted puede seleccionarlos y hacer click en el botón "Importar".
</span>

<hr>
<span class="member">
<br><br><A NAME="contacts"></A>
<h2>Contactos</h2>
<br><br>
Estas ventanas le permiten a usted manejar sus Contactos.<br>
Un contacto, es un miembro con el cuál usted opera e interactúa con frecuencia.<br>
Mediante sus Contactos, usted podrá acceder de forma directa y rápida a los mismos en el sistema. 
<h3> Mis contactos </h3>
A través de su lista de contactos (Menú: Personal> Contactos), usted puede realizar las 
siguientes acciones:
<ul>
	<li> <b> Código de miembro / Nombre de miembro: </b> Permite acceder al perfil del miembro.
	<li> <b> Correo electrónico: </b> Permite enviar un correo electrónico al miembro.
	<li> <b> <img border="0" src="${images}/edit.gif" width="16" height="16"> </b>
	Ver / Agregar / Editar información adicional acerca del miembro.
	<b> <li> <img border="0" src="${images}/edit_gray.gif" width="16" height="16"> </b> 
	Si no visualiza el ícono de color, significa que el campo de información se encuentra vacío.
	<b> <li> <img border="0" src="${images}/delete.gif" width="16" height="16"> </b> 
	Eliminar el miembro de su lista de contactos.
</ul>

Al comienzo, usted no poseerá contactos en su lista.<br>
Usted puede añadir contactos de dos (2) maneras:
<ul>
	<li> Usando la ventana ubicada en la parte inferior para 
	"<a href="#add_contact"><u>Agregar nuevo contacto</u></a>".</li>
	<br>
	<li> En primer lugar, realizar una <u><a href="${pagePrefix}user_management#search_member_by_member">búsqueda de miembros</u></a>
	("Menú: Buscar> Miembros"). <br>
	En la lista con los resultados de su búsqueda, usted puede acceder al <a href="${pagePrefix}profiles"><u>perfil</u></a> 
	de un miembro haciendo click en su Nombre (o en su Código de miembro). <br>
	En el perfil, deberá hacer click el botón Aceptar correspondiente a la acción "Agregar a lista de contacto"; 
	de este modo agregará el miembro a su Lista de contactos.</li>
</ul>
<hr class="help">

<br><br><A NAME="add_contact"></A>
<h3> Agregar nuevo contacto </h3>
Aquí usted puede añadir un nuevo contacto a su lista de contactos. <br>
<br>
Puede hacerlo ingresando el "Código de miembro" o "Nombre de miembro" (auto-completables) deseado, 
y luego presionando el botón "Aceptar".
<hr class="help">

<br><br><A NAME="contact_note"></A>
<h3> Nota de contacto </h3>
En esta página usted puede insertar información adicional acerca de un miembro. 
Esta información sólo es visible para usted y será eliminada si se elimina el miembro
de su lista de contactos.
<hr class="help">

<a name="contact_us"></a>
<h3> Contacto </h3>
Esta página contiene las vías de contacto (telefónos, correo electrónico, etc.) con la Administración 
por si usted tiene dudas o consultas sobre el software.<br>
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
