<div style="page-break-after: always;">
<a name="member_records_top"></a>
<br><br>

Los registros de miembro son unidades de información 
que se asocian/habilitan a determinados miembros en el sistema.<br> 

Existen múltiples y variadas características para su definición y gestión. <br>
Son utilizados con el fin de almacenar información sobre los miembros en uno o varios campos.<br>
<br>
Es decir, un miembro puede tener diferentes valores almacenados en sus "registros de miembro". <br>
Esto distingue a los registros de miembro de los <a href="${pagePrefix}custom_fields"><u>campos personalizados</u></a>;<br>
si bien ambos adjuntan información personalizada para los miembros, en el caso de los campos personalizados
un miembro sólo posee un valor para dicho campo (no varios). <br>
<br>
Los registros de miembros pueden poseer un conjunto de campos, de diferentes tipos. <br>
<br>
Un <b><i>Ejemplo</i></b> sencillo son las "Solicitudes de Soporte" o "Incidencias", 
donde cada registro de miembro podrá poseer variada información: <br>
<ul>
	<li>Fecha y hora de inicio.</li> 
	<li>Estado. </li>
	<li>Solicitud. </li>
	<li>Trabajos realizados.</li> 
	<li>Resolución.</li> 
	<li>Fecha y hora de finalización y/o conformidad, etc.</li> 
</ul>


<span class="admin">
(como se explica más adelante)<br>
<hr>
<br><br>
Este manual posee una pequeña <a href="#guidelines"> <u> guía </u> </a>
que usted puede consultar, y contiene un <a href="#example"> <u> ejemplo </u> </a>
para hacer el uso de los registros de miembros más ilustrativo.
<br><br> <i> ¿Dónde los encuentro? </i> <br>
Los registros de miembros pueden ser accedidos a través del <a href="${pagePrefix}profiles"> <u> perfil </u> </a>
de un miembro> en el bloque "Información miembro". 
Puede configurar los tipos de registro a través del "Menú: Usuarios & Grupos> Tipos de registro del miembro".

<br><br>
<i>¿Cómo hacerlos funcionar?</i> <br>
	Por favor, consulte nuestra <a href="#guidelines"> <u> guía </u> </a> para esta opción.
	</span>
	<span class="broker">
	<br><br> Estos son los registros de información adicional asociada a cada miembro. 
	Usted puede tener la posibilidad de ver, añadir, editar o eliminar estos registros,
	en función de los privilegios establecidos por el administrador.
	<br><br> <i> ¿Dónde los encuentro? </i> <br>
	Los registros de miembro pueden ser accedidos a través del <a href="${pagePrefix}profiles"> <u>
	perfil </u> </a> de un miembro. 
	Los tipos de registros del miembro, serán incluidos en "Acciones del broker para ...".

<hr>
</span>

<span class="admin"> <a name="guidelines"></a>
<hr>
<h3> Guía para la definición de registros de miembro </h3>
Con el fin de crear nuevos registros de miembro, usted debe seguir los siguientes pasos:
<ol>
	<li> En primer lugar, piense con claridad sobre el alcance del nuevo registro.<br> 
	¿Qué información le gustaría almacenar?<br> 
	¿No puede ser realizado con un simple <a href="${pagePrefix}custom_fields"><u>campo personalizado</u></a>?

	<li> Si desea crear un nuevo tipo de registro de miembro, es necesario contar con los permisos correspondientes. 
	Dichos permisos pueden ser localizados en el bloque "Tipos de registro del miembro" en la administración de 
	<a href="${pagePrefix}groups#manage_group_permissions_admin_system"><u>permisos</u></a>; deberá comprobar la casilla de verificación "Administrar".

	<li> Luego, puede crear un nuevo tipo de registro de miembro a través del 
	"Menú: Usuarios & Grupos><a href="#member_record_types_list"><u>Tipos de registro del miembro</u></a>, 
	mediante el botón "Insertar nuevo tipo de registro para miembro".

	<li> Una vez que haya creado el nuevo tipo de registro para el miembro, en la 
	<a href="#member_record_type_fields_list"><u>pantalla siguiente</u></a> usted podrá
	añadir campos personalizados al tipo de registro. 
	Se debe añadir al menos un campo a cada uno de los tipos de registro, 
	de lo contrario el tipo de registro no contendrá información, y será totalmente inútil.<br> 
	Para algunos campos personalizados, necesitará crear también posibles valores (véase el <a href="#example"><u>ejemplo</u></a>).

	<li> Una vez que el tipo de registro es creado, deberá definir quién puede: visualizar, crear, modificar y eliminar 
	los nuevos registros en el "Menú: Usuarios & Grupos>Administrar grupos - Grupo - bloque "Registros de miembro".<br> 
	Usted puede establecer estos permisos para grupos de administradores y para grupos de brokers.

	<li> El registro de miembro se mostrará con un botón de acción debajo del <a href="${pagePrefix}profiles"><u>perfil</u></a>,	
	en la sección "Información miembro" de las <a href="${pagePrefix}profiles#actions_for_member_by_admin"><u>Acciones para ...</u></a>.<br>
	Esta opción sólo será visible para los brokers o administradores.<br>
	Aquí, usted podrá añadir todos los elementos de este registro de miembro que desee.

	<li> Si la configuración del registro del miembro posee marcada la opción "Mostrar item del menú", 
	usted podrá buscar los valores del registro de ese miembro a través del "Menú: Usuarios & grupos".

	<li> Todos los registros se pueden buscar a través de las "acciones" ubicadas debajo de la página del perfil de un miembro.<br> 
	También puede buscar en todos los registros de miembro existentes (no sólo los relacionados con un miembro) en el menú (Menú: Usuarios & grupos).
</ol>
<hr class="help">

<a name="example"></a>
<h3> Ejemplo de registro de miembro </h3>
Aquí se muestra un ejemplo simple y específico de un tipo de registro de miembo,
con el fin de obtener una mayor comprensión acerca de los registros de miembro. 
<ol>
	<li> <b> Piense lo que desea: </b> En este ejemplo, vamos a crear un nuevo tipo de registro de miembro, 
	denominado "Llamadas de asistencia técnica", para realizar un seguimiento de la frecuencia
	con que los miembros consultan al sector de Soporte técnico (help desk), y con qué tipo de problemas/solicitudes.<br>
	<br>
	<li> <b> Establecer los permisos: </b> A través del "Menú: Usuarios & Grupos> Administrar grupos", 
	y consultando las ayudas respectivas para <a href="${pagePrefix}groups#manage_groups"><u>establecer los permisos</u></a> deseados.<br>
	<br>
	<li> <b> Crear nuevo tipo de registro de miembro: </b> 
	En el "Menú: Usuarios & Grupos> <a href="#member_record_types_list"><u>Tipos de registro del miembro</u></a>", 
	haga click en el botón "Insertar nuevo tipo de registro para miembro".<br> 
	En la siguiente <a href="#edit_member_record_type"><u>ventana</u></a>, complete:
	<ul>
		<li> <b> Nombre: </b> "Llamadas de asistencia técnica".
		<li> <b> Etiqueta (plural): </b> Completamos con "Solicita asistencia".
		<li> <b> Descripción: </b> Ingrese una breve descripción sobre el objetivo del campo.
		<li> <b> Grupos: </b> Elija un grupo de miembros para el que desea utilizar el
		nuevo tipo de registro de miembro. Por ejemplo, "Empresas medianas".
		<li> <b> Resultados de búsqueda: </b> Como no será utilizado para un análisis numérico, seleccione la opción "Plano".
		<li> <b> Mostrar ítem del menú: </b> Esta opción significa que el tipo de registro se mostrará en el menú principal "Menú: Usuarios & Grupos". 
		En esa página, usted puede buscar el registro de valores con diferentes opciones de búsqueda.
		<li> <b> Editable: </b> No hay necesidad de cambiar los elementos después de que hayan sido creados, por lo que no vamos a marcar este campo.
	</ul>
	<br>
	Luego, haga click en el botón "Aceptar" para crear el nuevo tipo de registro de miembro.<br>
	Se mostrará en nuestra ventana de <a href="#member_record_types_list"><u>Tipos de registros de miembro</u></a>.<br>
	<br>
	<li> <b> Crear campos personalizados para el tipo de registro de miembro: </b> Usted deberá crear
	campos en el registro del miembro, de lo contrario el nuevo registro no tendrá sentido. 
	En la <a href="#member_record_types_list"><u>lista de tipos de registro</u></a> 
	usted deberá hacer click en el ícono de Modificación <img border="0" src="${images}/edit.gif" width ="16" height="16">, 
	accediendo a la ventana para <a href="#edit_member_record_type"><u>editar el tipo de registro</u></a> de miembro.<br> 
	Use el botón "Insertar nuevo campo personalizado" para crear cada nuevo campo. 
	Esto lo redireccionará a la pantalla con la <a href="#member_record_type_fields_list"><u>lista de campos del tipo de registro de miembro</u></a>.<br>
	<ul>
		<li><b> Campo Fecha:</b> Haciendo click en el botón "Insertar nuevo campo personalizado" en el tipo de registro de miembro, 
		en la lista de los campos existentes, accederá a la ventana para la creación de un 
		<a href="${pagePrefix}custom_fields#edit_custom_fields"><u>nuevo campo personalizado</u></a>.<br> 
		Complete los siguientes campos (los campos del formulario no explicados no son esenciales para su correcto funcionamiento):
		<ul>
			<li><b>Nombre:</b> "Fecha".
			<li><b>Tipo de dato:</b> Fecha.
		</ul>
		<br>
		Ingrese información en los campos restantes del formulario y haga click en el botón "Aceptar" para guardar el nuevo campo personalizado. <br>
		<b>NOTA:</b> 
		De hecho, la creación de un campo "Fecha" no es en absolutamente necesaria. 
		Cyclos guarda automáticamente la fecha de creación de los registros de entrada de cada miembro, 
		por lo que podrían realizarse búsquedas por dicha fecha.
		
		<li> <b> Tipo de campo: </b> Nuevamente haga click en el botón "Insertar nuevo campo personalizado" 
		en la lista de tipos de registros del miembro.<br>
		Complete los siguientes datos:
			<ul>
				<li> <b> Nombre: </b> "Tipo".
				<li> <b> Tipo de dato: </b> "Lista de opciones".
				<li> <b> Tipo de campo: </b> "Casilla de selección".
				<li> <b> Requerido: </b> Esta opción debe ser seleccionada.
			</ul>
			<br>
			Después de hacer click en el botón Aceptar, usted volverá a visualizar la ventana de la "lista de tipos de registros de miembro".<br>
			Defina los posibles valores para este nuevo campo, haciendo click en el ícono de Modificación 
			<img border="0" src="${images}/edit.gif" width="16" height="16"> de la lista de campos actual. <br>
			Esto lo redireccionará nuevamente a la pantalla de edición de campos personalizados. 
			Aquí, a continuación, haga click en el botón "Nuevo valor posible" para introducir los nuevos valores:
			"Denuncia a otro miembro", "No entiende Cyclos", "Conforme con el sistema", etc. <br>
			<br>
			Estos valores deberán aparecer en la lista de 
			<a href="${pagePrefix}custom_fields#possible_values"><u>posibles valores</u></a>. 
			Al finalizar, haga click en el botón "Atrás" para regresar a la visión global del campo.
			
			<li> <b> Observación de campo: </b> Por último, agregue una observación sobre el campo, de la siguiente manera:
			<ul>
				<li> <b> Nombre: </b> "Observación".
				<li> <b> Tipo de dato: </b> "Cadena de caracteres".
				<li> <b> Tipo de campo: </b> "Editor de texto enriquecido".
			</ul>
			<br>
			La creación del nuevo tipo de registro ha finalizado. 
			No serán visibles en los Menú principales, hasta que usted salga de su cuenta e ingrese nuevamente.
	</ul>
			<li> <b> Agregar datos: </b> Ahora puede comenzar a utilizar el nuevo registro para miembros. <br>
			Por debajo del perfil de un miembro, en la sección de "Información miembro", en la ventana de 
			<a href="${pagePrefix}profiles#actions_for_member_by_admin"><u>Acciones para ...</u></a>, hallará un botón especial etiquetado como	
			"Llamadas de asistencia técnica", que le llevará a la correspondiente ventana, para introducir los datos en el registro de nuevos miembros.
			
			<li> <b> Búsqueda:</b> Usted puede buscar los datos en el registro, a través de los miembros, en el 
			"Menú: Usuarios & Grupos> Llamadas de asistencia técnica".
</ol>
<hr class="help">
</span>


<span class="admin">
<a NAME="member_records"></a>
<h3> Editar registro de miembro </h3>
En esta pantalla muestra los datos de los <a href="#member_records_top"> <u> registros de miembro </u> </a> 
en la <a href="#edit_member_record_type"> <u> lista </u> </a> de los tipos de registros existentes para miembros.<br>

Se muestra el usuario que ha creado el registro, cuando se creó, y el
contenido de los campos personalizados que se definen en el 
"Menú: Usuarios & Grupos> <a href="#member_record_types_list"> <u> Lista de tipos de registro para miembro </u> </a>".

Si el campo de registro se definió como "editable",
entonces, la opción de modificar el registro también estará disponible. <br>
Si posee los permisos, también podrá crear un nuevo registro, haciendo click 
en el botón "Aceptar" por debajo de la página, de la etiqueta "Crear nueva ..."
<hr class="help">
</span>

<span class="broker">
<a NAME="member_records"></a>
<h3> Editar registro de miembro </h3>
En esta pantalla se muestran los datos de los <a href="#member_records_top"> <u> registros de miembros </u> </a>.
Algunos tipos de registros de miembros son editables. 
Los registros de miembros pueden ser modificados
seleccionando el botón "Modificar", editando los campos, y luego haciendo click en "Aceptar". <br>
Si posee los permisos, usted también podrá crear un nuevo registro, haciendo 
click en el botón "Aceptar" por debajo de la página, de la etiqueta "Crear nueva ..."
<hr class="help">
</span>

<span class="admin"> <a NAME="member_record_types_list"></a>
<h3> Tipos de registro del miembro </h3>
Esta ventana muestra la lista de tipos de <a href="#member_records_top"><u>registros de miembro</u></a> del sistema.
<ul>
	<li> Para Modificar las propiedades del tipo de registro, haga click en el ícono de Modificación 
	<img border="0" src="${images}/edit.gif" width="16" height="16"> correspondiente.
	
	<li> Para Eliminar el tipo de registro, haga click en el ícono de Eliminación 
	<img border="0" src="${images}/delete.gif" width="16" height="16"> correspondiente.<br> 
	Tenga en cuenta que sólo es posible eliminar tipos de registro que no hayan sido utilizados aún, 
	tan pronto como un miembro posea la información correspondiente a este tipo de registro, no podrá ser eliminado.
	
	<li> Para Crear un nuevo tipo de registro de miembro, haga click en el botón "Aceptar" 
	correspondiente a la opción "Insertar nuevo tipo de registro para miembro".<br>
	Para la creación de un nuevo tipo de registro de miembro, puede consultar esta pequeña <a href="#guidelines"><u>guía</u></a> en línea.
</ul>
<hr class="help">
</span>

<span class="admin broker"> <a NAME="remarks"></a>
<h3> Registro de miembro plano (mosaico) </h3>
La información que usted podrá ingresar en esta pantalla es un 
"<a href="#member_records_top"> <u> registro de miembro </u> </a> plano". 
Normalmente, son definidos para brindar la posibilidad de agregar información al perfil de un usuario. 
Los campos están definidos por la administración.
Para añadir un registro, complete los campos requeridos (los campos con un asterisco rojo son obligatorios) y haga click en "Aceptar". 
Los registros existentes de los miembros son enumerados en la zona inferior.
<hr class="help">
</span>

<span class="admin"> 
<a NAME="search_member_records"></a>
<h3> Buscar registros de miembros </h3>
Aquí usted puede buscar registros de miembro, completando los filtros deseados 
y haciendo click en el botón "Búsqueda".
<ul>
	<li><b>Palabras clave:</b> Le permite buscar palabras contenidas en cualquier campo de los 
	<a href="#member_records_top"><u>registros de miembro</u></a> existentes.
	
	<li><b>Código / Nombre de miembro:</b> Permite buscar registros por el miembro al que son asignados.
	
	<li><b>Código / Nombre de broker:</b> Permite buscar registros de miembros asignados a un broker específico. 
	
	<li><b>[campos personalizados]:</b> Permite filtrar registros utilizando campos personalizados existentes. 
	
	<li><b>Fecha de registración:</b> Se puede utilizar para buscar registros por su fecha de creación.
	Cada tipo de registro de miembro posee automáticamente un campo fecha de creación, usted no tiene que crear este campo.
</ul>
Adicionalmente a estos campos, serán desplegados los campos personalizados definidos por usted para sus tipos de registro de miembro 
(en el "Menú: Usuarios & Grupos> <a href="#member_record_types_list"><u>Tipos de registro del miembro</u></a>").
<hr class="help">
</span>

<span class="admin broker">
<a NAME="search_records_of_member"></a>
<h3> Búsqueda de registros para un miembro </h3>
Aquí usted podrá buscar los registros existentes para un miembro, completando los campos deseados y
haciendo click en el botón "Búsqueda".
<ul>
	<li> <b> Palabras clave: </b> Se pueden utilizar para buscar en cualquier campo del registro.
	<li> <b> Fecha de creación: </b> Se puede utilizar para buscar registros, la fecha en la que fueron creados. 
	Cada tipo de registro de miembro posee su fecha de creación (asignada automáticamente).
	<li> Además de estos campos, están los campos personalizados asociados a los tipos de registros de 
	miembro, que son definidos por el administrador.
	<li> Usted puede introducir nuevos valores en los registros de este miembro, haciendo click en
	el botón "Aceptar" correspondiente. Este botón posee la etiqueta "Crear nuevo", seguida
	por el nombre del tipo de registro de miembro.
</ul>

<hr class="help">
</span>

<span class="broker admin"> <a NAME="member_records_search_results"></a>
<h3> Resultados de la búsqueda de registros de miembros </h3>
Estos son los resultados de la búsqueda de los registros de miembros. 
El código de usuario y el tipo de registro de miembro especificado, serán mostrados en el resultado.
<ul>
	<li> Para ver el registro completo, haga click en el ícono de visualización <img border="0"
		src="${images}/view.gif" width="16" height="16"> &nbsp; correspondiente.
	<li> Para modificarlo, haga click en el ícono de modificación <img border="0" src="${images}/edit.gif"
		width="16" height="16">.
	<li> Para eliminarlo, haga click en el ícono de eliminación <img border="0" src="${images}/delete.gif"
		width="16" height="16">. &nbsp; &nbsp;
</ul>
<hr class="help">
</span>

<span class="admin"> <a NAME="edit_member_record_type"></a>
<h3> Modificar / Nuevo tipo de registro de miembro </h3>
Aquí usted puede modificar o definir un nuevo <a href="#member_records_top"><u>registro de miembro</u></a>.<br>
<br>
Para Modificar el registro de miembro, seleccione el botón "Modificar", edite los campos deseados 
y haga click en el botón "Aceptar" para confirmar los cambios. <br>
<br>
Para Crear un nuevo tipo de registro, puede consultar esta <a href="#guidelines"><u>guía</u></a> en línea y/o un <a href="#example"><u>ejemplo</u></a>.
	<ul>
		<li><b>Nombre:</b> Es el nombre del registro de miembro.  
		<li><b>Etiqueta (plural):</b> 
		Es utilizada por la interfaz de usuario, y en la mayoría de los casos, es el nombre del registro en plural del formulario.
		
		<li><b>Descripción:</b> Breve descripción de la finalidad y el sentido del registro.
		
		<li><b>Grupos:</b> 
		Este menú desplegable le permite seleccionar los grupos de miembros que podrán acceder a este tipo de registro.<br> 
		Si un registro se asigna a un grupo de usuarios, a continuación, el nuevo tipo de registro de miembro se mostrará con un botón 
		en la sección "Información miembro" de las <a href = "${pagePrefix}profiles#actions_for_member_by_admin"><u>Acciones para ...</u></a>, 
		ubicadas debajo de la ventana del perfil del miembro (sólo visible para los administradores).
		
		<li><b>Resultados de búsqueda:</b> 
		Le permite elegir la forma en la que serán mostrados los resultados de la búsqueda.<br>
		Las opciones son:
		<ul>
			<li><b>Plano:</b> Cada tema se enumerará a continuación del punto anterior, separado por una línea. 
			Esta es la opción más apropiada para los comentarios o tipos de registros similares.
			<li><b>Lista:</b> Se muestran los temas en formato de tabla, con columnas y filas. Cada registro es una fila en la tabla.
		</ul>
		<br>
		<li><b>Mostrar ítem del menú:</b> Si esta opción es seleccionada, habrá un elemento del menú con el
		mismo nombre del tipo de registro, en la interfaz del administrador, en la sección "Menú: Usuarios & Grupos".
		Usted puede utilizar esta opción del menú para realizar una <a href="#search_member_records"><u>búsqueda</u></a> del tipo de registro, 
		para los valores especificados.
		
		<li><b>Editable:</b> Si esta opción es seleccionada, el registro de miembro podrá ser modificado 
		luego de su creación (por administradores o brokers). 
		Normalmente, los tipos de observación no son editables: una vez que una observación se ha creado, no podrá ser modificada.
	</ul>
<br><br>
Además de modificar las propiedades del registro, también deberá crear y modificar los "campos personalizados" en la 
<a href="#member_record_type_fields_list"><u>ventana inferior</u></a>, de lo contrario, el registro de miembro será creado vacío 
y no tendrá sentido su existencia.
<hr class="help">

<a NAME="member_record_type_fields_list"></a>
<h3> Campos personalizados (de tipos de registros de miembro) </h3>
Aquí son listados los campos personalizados correspondientes a los registros de miembros existentes.<br> 

Los campos personalizados se encuentran donde los datos del registro son almacenados e indexados.<br>
Para que un registro de miembro sea útil, debe existir al menos un campo personalizado.
<ul>
	<li>Para Crear un nuevo campo personalizado, haga click en el botón "Aceptar" 
	correspondiente a la etiqueta "Insertar nuevo campo personalizado".
	
	<li>Para Cambiar el orden en que se despliegan los campos personalizados, haga click en el botón "Aceptar" 
	correspondiente a la etiqueta "Cambiar el orden del campo personalizado".
	
	<li>Para Modificar un campo personalizado, haga click en el ícono de Modificación 
	<img border="0" src="${images}/edit.gif" width="16" height="16"> &nbsp; correspondiente.
	
	<li>Para Eliminar un campo personalizado, haga click en el ícono de Eliminación 
	<img border="0" src="${images}/delete.gif" width="16" height="16"> &nbsp; correspondiente.
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