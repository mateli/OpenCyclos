<div style="page-break-after: always;">
<a name="documents_top"></a>
<br><br>
Esta opción permite visualizar, enviar o imprimir documentación relacionada a cada miembro. <br>
Los documentos son páginas con información que pueden aparecer en la sección del miembro en Cyclos.<br>
El miembro puede seleccionar un documento de una lista.<br>
<br>
Existen dos tipos de documentos: "Estáticos" y "Dinámicos":
<br>
<ul>
	<li><b>Estáticos</b>: consisten en archivos de imágenes o comprobantes (por ejemplo archivos .pdf), 
	la información contenida en ellos es estática.<br>
	Pueden asignarse a un miembro o a un grupo de miembros.</li>
	<br>
	<li><b>Dinámicos</b>: consisten en formularios diseñados con lenguaje HTML, cuya información 
	será enviada a otros participantes o a la Administración de la Red. <br>
	Pueden asignarse a uno o más grupos de miembros.<br>
	Estos documentos pueden tener un formato personal por defecto, ya que puede recuperar datos del perfil del miembro
	que lo lee.</li>
	
</ul>

<span class="admin">
Un típico ejemplo de documento sería un contrato de préstamo o de cualquier tipo de
solicitud, que el miembro puede utilizar con el fin de solicitarle algo a la administración.<br>

<br><br><i>¿Dónde los encuentro?</i><br>
Los documentos pueden ser encontrados en el "Menú: Gestión de Contenidos>
Documentos". Un ejemplo de la creación de un documento dinámico se puede encontrar en el
Cyclos wiki, en la sección "Configuración - Documentos personalizados".
<br>
Cada uno de los documentos existentes para cada miembro pueden ser accedidos a partir del <a
href = "${pagePrefix}profiles"><u> perfil </u></a> del miembro correspondiente (bloque de
"Información del miembro").

<br><br> <i> ¿Cómo hacerlos funcionar? </i> <br>
Antes de que pueda crear documentos, se necesitarán los <a href = "${pagePrefix}groups#manage_group_permissions_admin_member "><u> permisos </u></a> correspondientes. 
Esto se puede hacer en el bloque de "Documentos", completando varias casillas.
Una vez que usted posee estos permisos, puede crear nuevos documentos a través del 
"Menú: Gestión de Contenidos> Documentos". <br>

<br><br> Para cada documento creado, se debe establecer la visibilidad en el bloque de
<a href="${pagePrefix}groups#manage_group_permissions_member"><u> permisos </u></a> de documentos 
por grupo. Por lo tanto, esto significa que los documentos pueden ser asignados a determinados grupos.
Es posible configurar un documento como sólo visible por los administradores, sólo por
administradores y brokers; y por los administradores, brokers y miembros.<br>

</span>
<span class="member">
<br><br> <i> ¿Dónde los encuentro? </i> <br>
Los documentos pueden ser accedidos en "Menú: Personal> Documentos".
</span>
<hr>

<span class="admin"> <a name="document_list"></a>
<h3> Lista de documentos personalizados </h3>
Esta página muestra una lista con los <a href="#documents_top"><u>documentos</u></a> que se han definido en el sistema.<br> 
Además del Nombre del documento, se muestra la siguiente información (y posibles acciones):
<ul>
	<li> <b> Tipo: </b> Muestra el <a href="#documents_top"><u>tipo</u></a> del documento.
	<li> Para Modificar un documento, haga click en el ícono de Modificación 
	<img border="0" src="${images}/edit.gif" width="16" height="16"> correspondiente.
	<li> Para Ver un documento, haga click en el ícono de Visualización 
	<img border="0" src="${images}/view.gif" width="16" height="16"> correspondiente.
	<li> Para Eliminar un documento, haga click en el ícono de Eliminación 
	<img border="0" src="${images}/delete.gif" width="16" height="16"> correspondiente.
</ul>
Para Crear un nuevo documento, debe utilizar uno de los dos botones ubicados en la
parte inferior de la ventana ("Nuevo documento dinámico" o "Nuevo documento estático")
<hr class="help">
</span>

<span class="admin"> <a name="new_edit_static_document"></a>
<h3> Nuevo / Modificar documento estático </h3>
Esta opción le permite crear un nuevo <a href="#documents_top"><u>Documento estático</u></a>. <br>
Simplemente escriba un Nombre, una Descripción para el documento y luego ingrese el nombre del archivo correspondiente en el campo "Reemplazar archivo". 
Puede si lo desea utilizar el botón "Examinar" para su localización.<br>
<br>
Para finalizar, haga click en el botón "Aceptar" para guardar el archivo.
<br><br> 
El archivo (documento) puede ser en cualquier formato. <br>
En el caso de que haya optado por un cambio en un archivo existente, 
el archivo actual puede ser accedido a través del enlace "Archivo actual"; 
usted puede hacer click sobre dicho enlace para visualizar la versión actual del documento.
<br><br> 
Cuidado: Sólo la creación de un documento NO significa que sus miembros o los usuarios pueden visualizarlo. 
Luego de su creación, deberá establecer los <a href="${pagePrefix}groups#manage_group_permissions_member"><u>permisos</u></a> correspondientes, 
para que los miembros puedan visualizarlos.
<hr class="help">
</span>

<span class="admin"> 
<a name="new_edit_dynamic_document"></a>
<h3> Nuevo / Modificar documento dinámico </h3>
Esta opción le permite crear un nuevo <a href="#documents_top"><u>Documento dinámico</u></a>. <br>
El formulario posee los siguientes elementos:
<ul>
	<li> <b>Nombre:</b> Es el nombre del documento.
	<li> <b>Descripción:</b> La descripción del documento (utilizado sólo por la Administración).
	
	<li> <b>Página de formulario:</b> 
	Puede suceder que el documento primero necesite que el usuario realice alguna entrada/ingreso (de datos) antes de su impresión.<br> 
	En esta página usted puede escribir una página HTML, con un formulario con la información que necesita solicitar al usuario.<br> 
	Si no necesita dicha entrada/ingreso del usuario, puede dejarlo en blanco.
	
	<li> <b>Página del documento:</b> 
	Aquí usted puede escribir la página del documento en formato HTML.<br> 
	Si se define una "página de formulario" en el cuadro de edición anterior, entonces usted puede incluir la entrada/ingreso del usuario de esa página.<br> 
	La página del documento se abrirá en una ventana emergente (pop-up), con un botón de Impresión y de Cerrar. <br>
	Usted también puede insertar imágenes, las cuales deberá cargar por primera vez en la sección de 
	<a href="${pagePrefix}content_management#custom_images"><u>imágenes personalizadas</u></a>.
</ul>
<br><br> 
Nota: Existen ejemplos de documentos dinámicos disponibles en el Cyclos wiki, en la sección "Configuración - Documentos personalizados".<br> 
Ver <a href="http://project.cyclos.org/wiki">project.cyclos.org / wiki</a>.<br>
Después de la creación de documentos, usted deberá establecer los 
<a href="${pagePrefix}groups#manage_group_permissions_member"><u>permisos para los miembros</u></a>.
<hr class="help">
</span>

<a name="member_document"></a>
<h3> Mis documentos </h3>
Esta ventana muestra una lista de los <a href="#documents_top"><u>Documentos</u></a> que la administración 
ha creado a disposición de los miembros. <br>
Estos documentos pueden ser descargados e impresos.
<br><br> 
Los documentos suelen contener información de interés para la Organización.<br> 
La administración puede presentar primero un formulario específico, donde usted 
deberá ingresar información requerida, para ser incluída en el documento a generar. 

<span class="broker admin">
Para los administradores y los brokers, también se muestra una lista con los tipos de documentos. 
Estáticos y documentos dinámicos, sólo se pueden ver desde esta ventana (hay que ir al
"Menú: Gestión de contenidos> Documentos" para la gestión de los mismos); 
Los miembros sin embargo, los documentos los gestionarán desde esta ventana. 
En tal caso, usted tiene disponibles las siguientes opciones:
<ul>
	<li><img border="0" src="${images}/view.gif" width="16" height="16">&nbsp;
	Le permite ver el documento
	<li> <img border="0" src="${images}/edit.gif" width="16" height="16">
	Le permite modificar el documento
	<li> <img border="0" src="${images}/delete.gif" width="16" height="16">
	Le permite borrar el documento.
</ul>
</span><hr class="help">

<span class="broker admin"> <a name="edit_member_document"></a>
<h3> Insertar / Modificar documento de miembro </h3>
En esta ventana, puede definir un nuevo documento "Estático" para un miembro individual.
Esto puede ser cualquier tipo de archivo como un pdf o una imagen. 
Si desea modificarlo, se puede sobrescribir el documento anterior haciendo click en el
botón "Cambiar". Cuando haya terminado, utilice el botón "Aceptar" para confirmar los cambios.
<ul>
	<li> <b> Nombre: </b> Nombre descriptivo del documento.
	<li> <b> Descripción: </b> Sólo visible para los administradores.
	
	<li> <b> Visibilidad: </b> Aquí se puede definir para qué usuarios este tipo de documento será visible.

		<ul>
			<li>Si selecciona "miembro", los administradores, los brokers (corredores) y el miembro podrán ver el documento.</li>
			
			<li>Si selecciona "broker", los administradores (con permisos) y el broker podrán ver el documento.</li>
			
			<li>En caso de seleccionar "administradores", sólo los administradores podrán ver el documento.</li>
		</ul><br>
	<li> <b> Archivo actual: </b> Es el presente archivo del documento. Usted puede hacer click en el
		enlace para verlo. Esto no será visible si se utiliza esta ventana para crear un
		Nuevo documento de miembro.
	<li> <b> Cargar el archivo: </b> Sólo tiene que introducir el nombre del archivo aquí con la ruta completa. 
		Si lo desea, puede utilizar el botón "Examinar".
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

