<div style="page-break-after: always;">
<a name="custom_fields_top"></a>
<br><br> 

Generalmente las organizaciones que trabajan con el sistema, buscan
poseer tipos propios y específicos de información almacenada en la base de datos de Cyclos. 
Por este motivo, es posible gestionar los campos que son mantenidos en la base de datos, y los cuales
son visibles en el sistema. <br>

Un administrador puede añadir nuevos campos, modificar y eliminar los campos existentes.

Esto es posible para: los miembros, administradores
y perfiles de <a href="${pagePrefix}operators"><u> operadores </u></a>;
para anuncios, para <a href="${pagePrefix}loans"><u> préstamos </u></a> y <a
	href="${pagePrefix}loan_groups"><u> grupos de préstamos </u></a>; para <a
	href="${pagePrefix}member_records"><u> registros de miembros </u></a>.<br>

Por ejemplo, si una organización necesita campo extra para el <a href = "${pagePrefix}profiles"> <u> perfil </u></a>, 
indicando el número de zapato (calzado) del miembro, el administrador puede crear un nuevo campo
y definir las diversas propiedades de este nuevo campo, como: nombre, tipo, tamaño,
visibilidad, los permisos, la ubicación, la validación, la conducta y otros ajustes. <br>

Los campos pueden ser asignados a <a href="${pagePrefix}groups"> <u> grupos </u> </a>.
Esto le permite tener diferentes formas y perfiles de registro para los miembros.

Cyclos posee por defecto un conjunto predeterminado de campos personalizados, que son, por supuesto, también
manejables y modificables. 
Por supuesto, no todos los campos en la base de datos son personalizados; debido a que 
algunos campos son tan importantes que no se pueden quitar ni modificar.<br>


<i> ¿Dónde los encuentro? </i> <br>
Los campos personalizados se pueden gestionar a través del Menú principal:"Campos personalizados".

<br><br> <i> ¿Cómo hacerlos funcionar? </i> <br>
Para la gestión de campos personalizados, es necesario que usted posea los
<a href="${pagePrefix}groups#manage_group_permissions_admin_system"> <u> permisos </u> </a> correspondientes;
estos sólo pueden ser asignados a los administradores, y pueden encontrarse en el bloque "campos personalizados".
<hr>

<a name="list_custom_fields"></a>
<h3> Lista de campos personalizados </h3>
Esta ayuda se aplica a los campos personalizados 
para miembros, administradores, <a href="${pagePrefix}operators"><u>operadores</u></a>, para 
<a href="${pagePrefix}advertisements"><u>anuncios</u></a>, <a href="${pagePrefix}loans"><u>préstamos</u></a>,
<a href="${pagePrefix}loan_groups"><u>grupos de préstamos</u></a> y 
<a href="${pagePrefix}member_records"><u>registros de miembros</u></a>.
<br><br> 
La lista muestra todos los campos personalizados definidos existentes.<br>
Los campos de miembro, administrador y operador, se mostrarán en su perfil.<br>
Los campos de los préstamos y de los grupos de préstamo, se mostrarán en sus páginas respectivas, al igual que los campos de anuncios.<br>
La base de datos definida por defecto en Cyclos, posee algunos campos personalizados.<br>
<br>
Las siguientes opciones (acciones) se encuentran disponibles:
<ul>
	<li> El Nombre y la configuración de los campos personalizados pueden ser modificados seleccionado el ícono de Edición 
	<img border="0" src="${images}/edit.gif" width="16" height="16"> correspondiente. <br>
	
	<li>Usted puede borrar un campo personalizado haciendo click en el ícono de Eliminación <img border="0" src="${images}/delete.gif" width="16" height="16"> 
	correspondiente.<br>
	Tenga en cuenta que la eliminación de un campo personalizado sólo será posible si el mismo no se encuentra en uso, 
	o sea, si el campo posee información en la base de datos, será imposible su borrado. 
	En tal caso, nosotros recomendamos ocultar el campo para los grupos deseados. 
	
	<li>Seleccionando el botón Aceptar correspondiente a la opción "Cambiar el orden de los campos", 
	usted puede cambiar el orden de los campos que aparecen en la página.<br>
	
	<li>Seleccionando el botón Aceptar correspondiente a la opción "Nuevo campo personalizado", usted puede insertar un nuevo campo personalizado.<br>
</ul>
En el archivo de ayuda contextual de esta página, usted puede encontrar información detallada sobre la configuración de los campos personalizados.
<hr class="help">

<a name="order_custom_fields"></a>
<h3> Cambiar el orden de los campos personalizados </h3>
Esta ayuda se aplica a los campos personalizados para miembros, administradores, <a href="${pagePrefix}operators"><u>operadores</u></a>, 
<a href="${pagePrefix}advertisements"><u>anuncios</u></a>,
<a href="${pagePrefix}loans"><u>préstamos</u></a>, <a href="${pagePrefix}loan_groups"><u>grupos de préstamos</u></a>, 
y para <a href="${pagePrefix}member_records"><u>registro de miembros</u></a>.
<br><br>
Aquí usted puede definir el orden en que los campos personalizados se mostrarán en la página.<br> 
Para ello, coloque el puntero del ratón (mouse) sobre el nombre del campo, haga click y manteniéndolo presionado, 
"arrastre" el nombre a la nueva posición deseada. <br>
Al terminar, presione el botón "Aceptar".
<hr class="help">

<a name="edit_custom_fields"></a>
<h3> Modificar / Nuevo campo personalizado </h3>
En esta ventana usted puede configurar las propiedades del campo personalizado. <br>
Asegúrese de hacerlo de forma correcta, debido a que algunas opciones sólo pueden ser modificadas en su creación. 
Luego del campo estar en uso, será imposible modificarlo. <br>
	<br><br> Están disponibles las siguientes opciones:
	<ul>
	<li> <b> Nombre: </b> Este es el nombre o la "etiqueta" del campo que será visible en Cyclos.
	<li> <b> Nombre interno: </b> Este es el nombre interno del campo. Sólo se utiliza con fines de desarrollo.
	
	<li> <b> Tipo de dato: </b> Con el tipo de dato se puede especificar el tipo de campo a definir. 
	Existen seis tipos posibles de campo:
	<ul>
		<li> <b> Cadena de caracteres: </b> La cadena puede ser texto con cualquier caracter. 
		Si desea especificar un campo con un "patrón de ingreso" obligatorio, como por ejemplo el correo electrónico, 
		teléfono o código postal, usted puede crear una máscara en el campo siguiente (patrón de ingreso). <br>
		Esta máscara forzará al usuario a ingresar la información del campo en el formato correcto.<br>
		Usted puede encontrar documentación detallada sobre la máscara de entrada en el sitio del proyecto:<br>
		<a href="http://javascriptools.sourceforge.net/docs/manual/InputMask_mask.html" target="_blank">JavaScript tools</a>.
		
		<li><b>Lista de opciones:</b> 
		El tipo "Lista de opciones" significa, que usted tendrá una lista de valores posibles, 
		como por ejemplo la Zona ("Norte", "Sur", "Este", "Oeste"). <br>
		
		Una lista de opciones puede ser presentada como cuadro de selección (desplegable) 
		o como un botón de opción/selección. <br>
		
		Cuando se selecciona el tipo de dato Lista de opciones, es desplegado un nuevo campo adicional, 
		denominado "Todas las etiquetas seleccionadas". <br>
		
		El contenido de este campo se mostrará en el menú desplegable como opción por defecto.<br>
		En el ejemplo de las "zonas", podría mostrarse en este campo "Todas las zonas".
		
		<li> <b> Número entero: </b> Este tipo significa que el campo podrá contener un
		número, sin coma o punto decimal (según el idioma).
		
		<li> <b> Número decimal: </b> Esto tipo significa que el campo podrá contener un 
		número, con coma o punto decimal. <br>
		La precisión y el formato se definirá en la 
		<a href="${pagePrefix}settings#local"><u>"Configuración> Configuación local> Internacionalización> Formato de número"</u></a>.
		
		<li> <b> Fecha: </b> Este campo sólo podrá contener una fecha. 
		El formato de la fecha podrá ser definido en el 
		<a href="${pagePrefix}settings#local"><u>"Configuración> Configuración local> Internacionalización> Formato de fecha"</u></a>.
		
		<li> <b> Verdadero/Falso: </b> Es un campo que podrá tomar dos posibles valores: Verdadero o Falso.
	
		<li> <b> Dirección Web: </b> Esta opción significa que el contenido del campo deberá ser una URL (dirección Web) válida. 
		Al visualizar el campo URL, se mostrará como un enlace (link) que abrirá la URL en una nueva pestaña/ventana.
		
		<li> <b> Miembro:< /b> Este campo se puede utilizar para almacenar una relación de miembro a miembro.
		El campo permite seleccionar un miembro con una búsqueda rápida (nombre y código de usuario).
		Es una manera conveniente de conectar miembros con otros miembros o entidades
		que pueden tener campos personalizados como pagos. 
		
		Puede ser especialmente conveniente si usted no desea utilizar brokering, 
		o si los miembros ya poseen un broker.
		
		Un ejemplo típico es para añadir un 'contacto' en el perfil del usuario.
		Otro uso podría ser con el fin de añadir un campo de miembro en un tipo de transacción, 
		para los casos donde el usuario pagador o destinatario no se conoce por utilizarse 
		un tipo de transacción a/desde una cuenta de sistema.
	</ul>
	<br>
	
	<li> <b> Patrón de ingreso: </b> Aquí usted puede un introducir una "máscara" (patrón de ingreso) para el campo.<br>
	El patrón de ingreso fuerza al usuario a introducir la información contenida en el formato deseado/correcto.
	
	<li> <b> Campo padre: </b> Aquí usted puede definir si las posibles opciones para este campo 
	son dependientes del valor de otro campo (padre). 
	Para una explicación más detallada, haga click <a href="#parent_field"><u>aquí</u></a>.
	
	<li><b>Todas las etiquetas seleccionadas</b>. 
	
	<li> <b> Tipo de campo: </b> Hay diferentes tipos de campo de acuerdo con los tipos de datos contenidos.<br>
	Existen los siguientes tipos:
	<ul>
		<li> Cuadro de texto (1 línea).
		<li> Área de texto (5 líneas).
		<li> Editor de texto enriquecido.
	</ul>
	<br>
	<li> <b> Tamaño del campo: </b> El tamaño del campo podrá ser "extra chico", "chico", "medio", "grande" y "completo". 
	Un tamaño absoluto puede ser definido en el archivo de hoja de estilo. 
	El tamaño "completo", implica el tamaño completo de la ventana. 
	La opción "por defecto" puede variar dependiendo de cada tipo de campo, pero a menudo los
	campos utilizar aproximadamente el 80% del espacio disponible. 
	Por ejemplo: el campo "nombre" en esta pantalla posee el tamaño predeterminado.
	
	<li> <b>Habilitado</b>(solamente para campos personalizados de tipos de transacción/pago): 
	Esta opción le permite a usted habilitar o deshabilitar la utilización del campo personalizado.<br>
	Una vez que los campos personalizados son utilizados (poseen datos)
	no podrán ser eliminados del sistema, debido a que sus datos deben permanecer accesibles por razones de seguridad.<br>
	Si no se desea utilizar más un campo personalizado, usted puede simplemente desasignarlo de los grupos relacionados.
	Lo cual no es posible con los campos personalizados de pago (tipos de transacción), ya que no se encuentran directamente relacionados con los grupos.<br> 
	Esta opción le permite a usted habilitar o deshabilitar la utilización del campo personalizado.<br> 
	Por lo tanto, los campos personalizados de pago se puede desactivar (deshabilitar) utilizando esta casilla de verificación.<br> 
	Tenga en cuenta que los campos seguirán siendo visibles como filtros de búsqueda (las opciones a continuación). Usted podrá desactivar esta opción manualmente.
	
	<li> <b> Visible para (sólo anuncios):</b> 
	Aquí usted puede definir qué usuarios pueden visualizar el campo personalizado de anuncios.<br>
	Pueden ser:<br>
	<ul>
		<li>Sólo administradores;
		<li>Brokers y administradores;
		<li>Todos los usuarios: El propietario y todos los demás miembros, brokers y administradores.
	</ul>
	<br>
	<li><b> Mostrar en la búsqueda:</b> 
	Si esta opción es seleccionada,	el campo se mostrará como un filtro en el resumen de cuenta.<br>
	En caso de que el tipo de pago sea un préstamo, se mostrarán en la búsqueda de préstamo para administradores.
	
	<li><b> Mostrar en la lista:</b> 
	Si esta opción es seleccionada, el campo personalizado se mostrará como una columna en la lista de resultados de búsqueda.<br>
	En caso de que el tipo de pago sea un préstamo, se mostrará en la lista de resultados de búsqueda de préstamos, para los administradores.
	<br>
	El campo siempre será incluído en la exportación como archivo .csv y en la impresión.<br>
	Aunque esta opción no esté activada.
	
	<li><b> Mostrar perfil por: </b> Aquí usted puede definir qué grupos de usuarios podrán visualizar el campo.<br> 
	Los permisos de visualización funcionan de forma jerárquica.<br>
	Si un "Miembro" puede visualizar un campo, un administrador y sus brokers también podrán visualizarlo.<br>
	Si se selecciona el grupo "Broker", un administrador podrá visualizar el campo también.<br>
	Si se selecciona el grupo "Administrador", sólo los administradores podrán visualizar el campo.
	
	<li><b> Modificable por :</b> Aquí usted puede definir qué grupos de usuarios podrán modificar el campo. 
	
	<li> <b> Mostrar en búsqueda de miembros para: </b> Aquí usted puede definir los grupos de usuarios 
	a los que les aparecerá este campo personalizado en la página de búsqueda de miembros.
	(los permisos de visualización trabajan con estructura jerárquica)
	
	<li> <b> Mostrar en búsqueda de anuncios para: </b> Aquí usted puede definir los grupos de usuarios 
	a los que les aparecerá este campo personalizado en la página de búsqueda de anuncios.
	(los permisos de visualización trabajan con estructura jerárquica)
	
	
	<!-- VER CON ANDRES, COMO FUNCIONAN LOS PARAMETROS DE BUSQUEDA -->
	
	<li><b>Incluir en la búsqueda por palabras-clave:</b> 
	Con esta opción usted puede habilitar el campo personalizado para búsquedas por palabras clave, de miembros y anuncios.<br>
	<ul>
		<li><b>No incluir:</b> No se habilita el campo personalizado para búsquedas por palabras clave.</li>
		<li><b>Solamente miembros:</b> Sólo incluído para búsquedas de miembros.</li>
		<li><b>Miembros y anuncios:</b> Incluído para búsquedas tanto de miembros como de anuncios.<br>
		Si esta opción es seleccionada, se podrán realizar búsquedas de anuncios utilizando campos del miembro (perfil).<br>
		Tenga en cuenta que esto sólo es útil para búsquedas combinadas.
		Si se realiza una búsqueda de anuncios, en la que coincide con un campo del perfil del miembro, 
		todos los anuncios para dicho miembro serán desplegados.</li>
	</ul>
	<br>
	<li> <b> Mostrar en búsqueda de préstamos para: </b> Aquí usted puede definir los grupos de usuarios
	a los que les aparecerá este campo personalizado en la página de búsqueda de préstamos. 
	(los permisos de visualización trabajan con estructura jerárquica)
	
	<li> <b> Miembro puede ocultarse: </b> Aquí usted puede definir si el miembro poseerá la opción de ocultar el campo.
	
	<li> <b> Mostrar en reporte del miembro: </b> Aquí usted puede definir si el campo se podrá visualizar 
	en la página de búsqueda de miembros. 
	
	<li> <b> Acceso de broker (sólo para el registro de nuevo campo personalizado para tipo de registro de miembro): </b> 
	Aquí usted puede indicar el tipo de acceso que poseerá el broker sobre este campo: 
	<ul>
		<li>Ninguno</li>
		<li>Sólo lectura</li>
		<li>Editable</li>
	</ul>
	<br>
	<li> <b> Validación: </b> Usted puede especificar las siguientes validaciones:
	<ul>
		<li> <b> Requerido: </b> Si se selecciona esta opción, el campo será obligatorio y
		se mostrará con un asterisco rojo al lado. El usuario obligatoriamente deberá introducir un valor.
		<li> <b> Único: </b> Si se selecciona esta opción, el valor del campo sólo podrá existir una vez (ser único) en el sistema. 
		Esta opción se puede seleccionar si es necesario asegurar la unicidad de algún dato, como ser:
		pasaporte o número de registro fiscal, etc.
		<li> <b> Largo Mínimo y Máximo: </b> Si el campo es una cadena de caracteres, se puede definir
		un lagro mínimo y un largo máxima para la misma. 
		El usuario sólo podrá ingresar valores para este campo dentro de este rango.
	</ul>
	<br>
	<li> <b> Descripción: </b> Aquí usted puede ingresar una descripción para el campo. 
	La administración puede explicar el uso de este campo. 
	La descripción sólo se mostrará en la edición/modificación del campo.
	
	<li> <b> Habilitar campo para grupos: </b> Aquí usted puede seleccionar los grupos que
	serán propietarios del campo.
</ul>

<hr class="help">
<a name="parent_field"></a>
<h3> Campo padre </h3>
Cuando un campo es padre de otro, significa que las posibles opciones para elegir
para este campo, dependerán del valor del campo padre.
<br>
Por ejemplo, usted puede tener un campo personalizado "provincia", y otro campo personalizado "ciudad". 
Si el usuario selecciona una "provincia", el campo "ciudad" contendrá una lista con todas las ciudades 
pertenecientes a la provincia seleccionada. 
En este caso, se marca a la "provincia" como el <i> campo padre </i> para el campo "ciudad".
<br>
Note que el "campo padre" no es siempre visible. Sólo es visible para los tipos enumerados.

<! - Comprobar esto: es waar dit? ->
Para cada uno de los campos padres, se puede definir un conjunto diferente de
valores para los "campos hijos", a través de la ventana de
<a href="#possible_values"><u>posibles valores</u></a>
(disponible después de hacer click en el botón "Aceptar").
<hr class="help">


<a name="possible_values"></a>
<h3> Lista de valores </h3>
Esta ventana muestra una lista de los posibles valores para el campo personalizado.
<br><br> 
Usted puede eliminar un valor, seleccionando su correspondiente ícono de Eliminación 
<img border="0" src="${images}/delete.gif" width="16" height="16">. <br>

Esto sólo funciona cuando el campo/valor específico NO se encuentra en uso.<br>
Esto es posible para valores "vacíos"; existiendo la posibilidad de asignar 
todos las ocurrencias existentes para este valor, a otro valor de la lista. 
(esto se explicará mejor en cómo "Modificar valor de campo")
<br><br> 
Usted puede modificar un valor seleccionando su correspondiente ícono de Edición 
<img border="0" src="${images}/edit.gif" width="16" height="16">. <br>
<br><br> 
Usted puede crer un nuevo valor haciendo click en el botón "Nuevo valor posible".<br>
<br>
Si su campo posee un <a href="#parent_field"><u>campo padre</u></a>, usted debe 
primero seleccionar el campo padre para el cual desea definir nuevos valores, 
haciendo click en el botón "Nuevo valor posible".<br> 
Usted puede utizar el menú desplegable ubicado entre el botón "Atrás" y el botón "Nuevo valor posible" 
para realizar esta acción.
<hr class="help">

<!-- VIEJO 
<a name="edit_possible_value"></a>
<h3> Insertar nuevo valor de campo y/o modificar valor de campo </h3>
Aquí usted puede especificar el nombre de un nuevo valor. 
Escriba el valor y seleccione "Aceptar".
<br>
El valor será mostrado en la lista de valores, en orden alfabético.
<br>
<br>
En el caso de que su campo posea un <a href="#parent_field"><u>campo padre</u></a>, 
usted deberá asignar los nuevos valores a ser visualizados del campo padre.
<br>
<br><br>
Si desea definir nuevos valores para otros valores del campo padre,
haga click en el botón "Aceptar", que lo llevará nuevamente a la
<a href="#possible_values"><u>pantalla anterior</u></a>
, y allí usted podrá seleccionar nuevos valores para los campos padres, 
y repita el procedimiento.

<hr class="help">
-->

<a name="edit_possible_value"></a>
<h3> Modificar / Agregar valor de campo personalizado </h3>
<br><br>Están disponibles las siguientes opciones:
<ul>
	<li> <b> [Nombre del campo padre]:</b> En el caso de que su campo posea un 
	<a href="#parent_field"><u>campo padre</u></a>, este campo será asignado y etiquetado 
	con el valor correspondiente al campo padre seleccionado.<br>
	(Si desea definir nuevos valores para otro valor del campo padre, usted debe volver a la 
	<a href="#possible_values"><u>pantalla anterior</u></a>)
	
	<li> <b> Valor:</b> En este punto usted puede especificar el nombre de un valor. <br>
	Ingrese el nombre del valor y haga click en el botón "Aceptar". <br>
	Usted puede ingresar/agregar varios valores juntos, ingresando uno por linea.<br> 
	El valor se mostrará en la lista de valores en orden alfabético.
	
	<li> <b> Habilitado:</b> Si esta opción es seleccionada, el valor se mostrará como una posible opción. <br>
	Si esta opción no es seleccionada, el valor se mostrará pero sólo si hay datos para el valor. <br>
	De esta manera usted puede decidir no utilizar un valor que se ha utilizado
	en el pasado, pero evitar que los antiguos (no utilizados) valores se pierdan.
	
	<li> <b> Reemplazar ocurrencias por: (sólo en modo de edición)</b> Cuando se edita un valor, usted 
	puede mover los valores de todos los campos que contienen datos de este tipo de valor a otro.<br> 
	
	De esta manera, es posible eliminar un valor de la lista de valores de la página
	(sólo se permite la eliminación si el valor no es utilizado). <br>
	
	Si usted desea evitar la utilización de valores existentes, puede optar por deshabilitar el valor como se explicó anteriormente. <br>
	(La eliminación de valores puede ser realizada en la lista existente en la página 
	<a href="#possible_values"><u>pantalla anterior</u></a>)
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
<br><br>
</div>
