<div style="page-break-after: always;">
<span class="admin"> <a name="content_management_top"></a>
<br><br>

Cyclos le permite cambiar su interfaz de usuario, modificando su sistema de archivos.<br> 
Esto significa que en la práctica, usted puede controlar todo, básicamente, sobre la
o las páginas que sus miembros verán en su navegador: tanto el contenido como su aspecto, pueden ser manipulados.

<a name="type_list"> </a> Usted puede personalizar los siguientes tipos de archivos:
<ul>
	<li><b><a href="#statics"><u>Archivos estáticos</u></a> </b>
	<li><b><a href="#helps"><u>Archivos de ayuda</u></a> </b>
	<li><b><a href="#jsp"><u>Páginas de aplicación</u></a></b>
	<li><b><a href="#css"><u>Archivos CSS</u></a></b>
	<li> Además, usted también puede personalizar sus <a href="#images"><u>imágenes</u></a>.
	<li><b><a href="#themes"><u>Temas</u></a>: </b> Le permite cambiar a
	otro diseño predefinido, sin la molestia de cambiar todo tipo de archivos.
</ul>
<b> Importante: </b> Por favor, tenga en cuenta que la edición de este tipo de archivos puede ser un
complicado trabajo. Será necesario e imprescindible el conocimiento de CSS y HTML para hacerlo.
<br><br> Tenga en cuenta que usted no solo puede personalizar los archivos a nivel del sistema 
(por ejemplo este archivo de ayuda), sino que también puede configurar los archivos personalizados de
<a href="${pagePrefix}groups#manage_group_customized_files"><u>grupos,</u></a> e
incluso los <a href="${pagePrefix}groups#manage_group_filter_customized_files"><u>filtros de grupo</u></a>.

<br><br><i> ¿Dónde lo encuentro?</i><br>
La gestión de contenidos de todo el sistema puede ser accedida a través del "Menú: Gestión de Contenido". <br>
Para realizar la gestión de contenidos a nivel de grupo, usted deberá ir a la ventana de
<a href="${pagePrefix}groups#manage_groups"><u>Administrar grupos</u></a> y haga click en el ícono de edición
<img border="0" src="${images}/edit.gif" width="16" height="16"> de un grupo.
Este tema es abordado los archivos de ayuda sobre grupos.
<br>
Para realizar la gestión de contenidos a nivel de filtros de grupo, vaya al "Menú: Usuarios & Grupos> Filtros de grupo",
y haga click en el ícono de edición <img border="0" src="${images}/edit.gif" width="16" height="16"> de un filtro de grupo. 
Una vez más, este tema es tratado en sus respectivos archivos de ayuda.

<br><br><i>¿Cómo hacerlo funcionar? </i> <br>
Deberá definir los <a href="${pagePrefix}groups#manage_group_permissions_admin_system"><u>permisos de administrador</u></a> 
para los elementos del menú de gestión de contenido.
Existen varios elementos que afectan a la visibilidad de los temas en el "Menú: Gestión de Contenido", 
y que se aplican a los siguientes bloques:
<ul>
	<li> "Imágenes personalizadas" </li>
	<li> "Sistema de archivos personalizados" </li> 
	<li> "Temas" </li>
</ul>

<hr>
<a name="statics"></a>
<h3> Archivos estáticos </h3>
Se trata de páginas completas en la aplicación que pueden ser personalizadas para su organización.
<br> Para modificar estos archivos usted necesita conocimientos del lenguaje html. 
<br><br> La función permite modificar los siguientes archivos: 
<ul>
	<li> <b> top.jsp:</b> Cabezal general de la aplicación.
	
	<li> <b> login.jsp: </b> El mensaje aparece en la página de acceso (login) de Cyclos, por encima de la ventana de conexión.
	
	<li> <b> general_news.jsp: </b> El mensaje se muestra en la ventana de <a
	href = "${pagePrefix}home#home_news"> <u> noticias generales (tablón de mensajes) </u> </a>, 
	en la página principal, en la sesión del miembro en Cyclos.
	
	<li> <b> member_about.jsp: </b> Se muestra en el menú de los miembros y de los administradores, en el: "Menú: Ayuda> Acerca".
	
	<li> <b> contact.jsp: </b> Se muestra en el menú de los miembros, en el: "Menú: Ayuda> Contacto"; 
	debe brindar información sobre cómo ponerse en contacto con su organización.
	
	<li><b>posweb_login.jsp:</b> Cabezal de la página de acceso al módulo de pagos (externos) Posweb.
	
	<li> <b> posweb_header.jsp:</b> Cabezal de la página de los pagos externos del módulo Posweb.
	(puede ser personalizado por el miembro)
	
	<li> <b> posweb_footer.jsp:</b> Pie de página de los pagos externos del módulo Posweb.
	(puede ser personalizado por el miembro)
	
	<li> <b> webshop_header.jsp:</b> Cabezal de la página de los pagos externos del módulo Webshop.
	<li> <b> webshop_footer.jsp:</b> Pie de página de los pagos externos del módulo Webshop.
	
	<li> <b> statistics_manual.jsp:</b> Página del manual del módulo de estadísticas.
</ul>

También usted puede insertar <a href="#insert_images"><u>imágenes</u></a> en los archivos estáticos.

<hr class="help">
<a name="helps"></a>
<h3>Archivos de ayuda</h3>
Si usted lo desea, puede cambiar los archivos de ayuda del sistema. 
Usted puede cambiar los archivos de ayuda en el caso de que quiera modificar el <a href="#jsp">archivo de aplicación<u></u></a> 
al que pertenece, o porque piensa que el texto original no es suficientemente claro. <br>

Si desea modificar un archivo de ayuda, usted necesita saber su nombre y dentro de qué etiqueta se ubica. 
Los archivos de ayuda están organizados por tema principal y existen cerca de 30 archivos. 
Cada uno de ellos contiene el texto correspondiente a varias ventanas de ayuda, y etiquetas que le permiente saltar a dichas ventanas. <br>
Para averiguar el nombre del archivo, usted debe que posicionarse con el puntero del ratón (mouse)
sobre el ícono de ayuda (en la parte superior derecha de cada ventana). 
La barra de estado de su navegador le mostrará la ubicación correspondiente: <br>
"Ayuda: nombre_de_fichero tag_name#"; <br>
en este caso, el archivo sería "file_name.jsp" y la sección (etiqueta) en el
archivo de ayuda se denomina "tag_name". 
<br>
Tenga en cuenta que su navegador puede ocultar la barra de estado de mensajes, 
debe cambiar la configuración de su navegador, y asegúrese de que la opción "mostrar la barra de estado de mensajes" sea
establecida, en caso de que no sea mostrada la ubicación del archivo de ayuda.
<br><br> Usted también puede insertar imágenes <u><a href="#insert_images"></u></a> 
en el texto de los archivos de ayuda, pero tenga cuidado con el tamaño de la imagen en la ventana de ayuda, 
sólo se permitirán imágenes de 300 por 400 píxeles.
<hr class="help">

<a name="jsp"></a>
<h3> Páginas de aplicación </h3>
Una página de aplicación es un archivo jsp que contiene código de Cyclos para colocar elementos
en las páginas en Cyclos, pero que no contienen texto. 
En la práctica, esto significa que se trata de cualquier archivo jsp que no contiene un texto de ayuda. 
Estos archivos jsp definen que sucede en la página que usted ve en su navegador. <br>

El diseño de toda la estructura se puede modificar, pero puede afectar seriamente a sectores internos del sistema 
y al funcionamiento de la aplicación. 
Por lo tanto, por favor, utilice discrecionalidad en la modificación de archivos de la aplicación. 

Se recomienda modificar sólo para los cambios menores de diseño - por ejemplo, si desea cambiar el orden de
elementos, o quiere que un elemento no sea visible en la página -. 
Asimismo, por favor verifique si su objetivo no puede lograrse con la configuración estándar de Cyclos,
a través de la sección de administración del sistema.

<br><br> Para evitar o resolver ocasionales problemas, Cyclos siempre conservará una copia de la 
<a href="#edit_customized_file"><u>página original</u></a>, para que puede ser revertido fácilmente.
<br><br>
También usted puede insertar <a href="#insert_images"><u>imágenes</u></a> en el contenido de un archivo jsp.
<hr class="help">

<a name="css"></a>
<h3> Archivos CSS </h3>
Los archivos CSS (hojas de estilo en cascada) definen los elementos que constituyen el aspecto de una página.
Es posible que usted desee cambiar estos archivos para darle otra imagen a ciertos elementos,
por ejemplo, colores, botones, elementos de menú, etc. 
Para modificar las hojas de estlo, es necesario el conocimiento de los archivos css.

<br><br> Cyclos brinda los siguientes archivos de hoja de estilo (css):
<ul>
	<li> <b> Style.css </b> <br>
	El principal archivo de hoja de estilo para la presentación de Cyclos (menú, ventanas, etc.);
	<li> <b> Login.css </b> <br>
	La hoja de estilo para la página de acceso;
	<li> <b> Mobile.css </b> <br>
	La hoja de estilo para la página de móviles;
	<li> <b> Posweb.css </b> <br>
	La hoja de estilo para la página posweb;
	<li> <b> IeAdjust.css </b> <br>
	La hoja de estilo utilizada para resolver los problemas de compatibilidad con Internet Explorer;
</ul>

<br><br> Para ver los efectos de la modificación de un archivo css, usted deberá 
actualizar (refrescar) la página en su navegador. 
Esto puede realizarse yendo a la url "www.sudominio.org / cyclos / pages / estilos / style.css" <br>

El contenido de la CSS se mostrará como texto. Para asegurarse de que la nueva página
está activa, actualice la página un par de veces en su navegador.

<br><br> Si desea utilizar un nuevo archivo css, puede copiar todo el contenido a
lo largo de la hoja de estilos ya existente (en la ventana de edición de hojas de estilo) y
actualizar la página, tal como se describe más arriba.

<br><br> Si ha conseguido crear un buen archivo css, lo invitamos a que nos envíe
el archivo, para que nosotros podamos ponerlo a disposición de los demás usuarios. 
La hoja de estilo será publicado en SourceForge y en el sitio del proyecto.
<hr class="help">

<a name="insert_images"></a>
<h3> Imágenes en archivos de texto </h3>
Es posible insertar imágenes en archivos de texto como 
<a href="#statics"><u>archivos estáticos</u></a> y archivos de <a href="#helps"><u>ayuda</u></a>. 

Para ello las imágenes deberán estar a disposición para la aplicación. 
Usted puede comprobar que las <a href="#system_images"><u>imágenes del sistema</u></a> 
están disponibles, o se puede cargar una imagen propia en el "Menú: Gestión de Contenido> Imágenes adaptadas". 

Para insertar una imagen en una página, la etiqueta siguiente debe colocarse al inicio del archivo estático:
<br><br><i>&lt;%@ taglib
uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %&gt;</i>


<br><br> Y la ubicación de la imagen será como esta:
<br><br><i>&lt;img src="&lt;html:rewrite
page="/pages/images/some_image.jpg"/&gt;"&gt;</i>

<br><br> La página sólo necesita la etiqueta 'html:rewrite" de ubicación de la imagen. 
Usted puede usar los argumentos html normales como; border, align, width and height.
<hr class="help">

<a name="customized_files"></a>
<h3>Archivos personalizados </h3>
Esta sección de ayuda muestra sólo información general sobre el funcionamiento del formulario y sus posibles acciones.<br>
<br>
Es posible que usted desee chequear la <a href="#type_list"><u>lista de archivos</u></a> aquí desplegada, y siguiendo el enlace (link), 
pueda obtener información específica y sugerencias sobre el tipo de archivo que desea <a href="#content_management_top"><u>personalizar</u></a>.
<br><br> 
Esta ventana muestra una lista con los archivos que han sido <a href="#content_management_top"><u>personalizados</u></a>.<br>
<br>
Las siguientes opciones (acciones) se encuentran disponibles:
<ul>
	<li> <b>Personalizar</b> un nuevo archivo, haciendo click en el botón "Personalizar nuevo archivo", ubicado en la parte inferior derecha de la ventana.	
	
	<li> Obtener una <b>Vista previa</b> del archivo personalizado, haciendo click en el ícono de Visualización 
	<img border="0" src="${images}/preview.gif" width="16" height="16"> correspondiente.
	
	<li> <b>Modificar</b> un archivo personalizado, haciendo click en el ícono de Modificación 
	<img border="0" src="${images}/edit.gif" width="16" height="16"> correspondiente.
	
	<li> <b>Eliminar</b> un archivo personalizado, haciendo click en el ícono de Eliminación 
	<img border="0" src="${images}/delete.gif" width="16" height="16"> correspondiente.<br> 
	Esto significa que la última modificación realizada sobre el archivo seguirá siendo visible, 
	pero en la próxima actualización de Cyclos será sustituída por su definición predeterminada.
</ul>
Cuando esta función es utilizada por primera vez, es posible que no aparezcan archivos personalizados en la lista, 
por lo que tampoco serán visibles los íconos correspondientes. Esto depende del tipo de archivos que usted desee personalizar en el sistema.
<hr class="help">

<a name="edit_customized_file"></a>
<h3> Modificar el archivo personalizado </h3>
Esta sección de ayuda muestra sólo información general sobre el funcionamiento del formulario y sus posibles acciones.<br>
<br>
Es posible que usted desee chequear la <a href="#type_list"><u>lista de archivos</u></a> y 
siguiendo el enlace (link), pueda obtener información específica y sugerencias sobre el tipo
de archivo que desea <a href="#content_management_top"><u>personalizar</u></a>.
<br><br> 
En esta ventana usted puede volver a modificar un archivo personalizado:<br> 
<ul>
	<li>Haga click en el botón "Modificar" para iniciar la modificación.</li>
	<li>Haga click en el botón "Aceptar" para confirmar los cambios realizados.</li>
</ul>
Los siguientes campos se encuentran disponibles:
<ul>
	<li> <b> Nombre de archivo: </b> Muestra el nombre del archivo. Este campo no puede ser modificado.
	
	<li> <b> Contenido: </b> Aquí usted puede crear/modificar el contenido del archivo.
	Puede utilizar etiquetas HTML y XML, que le brindarán muchas posibilidades, tales como
	por ejemplo, "información general" de la página, con una barra de herramientas con acceso rápidos a otras secciones de Cyclos. <br>
	Además, puede añadir código javascript para definir su comportamiento.
	Sin embargo, dicha programación es sofisticada, para la cual usted debe hacer referencia a la 
	<a href="http://project.cyclos.org/wiki/index.php?title=Programming_guide"><u>Guía de programación Cyclos</u></a>.
	
	<li><b>Nuevo contenido:</b>
	Si usted ha actualizado/mejorado Cyclos y existe una nueva versión del archivo,
	Cyclos no reemplazará el archivo con el nuevo contenido, pero generará una alerta (de sistema).<br>
	El nuevo contenido estará disponible en esta área.
	
	<li> <b> Contenido original: </b> Muestra el contenido original del archivo, como era antes de su personalización.<br>
	Si usted elimina el archivo personalizado de la <a href="#customized_files"><u>lista</u></a>, el contenido original será aplicado nuevamente.<br>
	Por supuesto, usted puede copiar y pegar el contenido del "Contenido original" en el campo "Contenido", 
	en el caso de que el nuevo contenido definido no funcione correctamente.
	
	<li><b>Resolver conflicto:</b> 
	Cuando existe una nueva versión de un archivo que ha sido personalizado,
	Cyclos generará una alerta de sistema y colocará la nueva versión en el área de "Nuevo contenido".
	Además, el nombre del archivo personalizado "en conflicto" aparecerá en color rojo en la lista de archivos personalizados.<br>
	Una vez que el conflicto ha sido resuelto y todo funciona correctamente, 
	usted puede seleccionar la opción "Resolver conflicto" y guardar el archivo.<br>
	Luego de realizada esta acción, el nombre del archivo no se mostrará más en color rojo en la lista de archivos, 
	y el nuevo contenido de la versión del archivo será movida al área del "Contenido original".
</ul>
<hr class="help">

<a name="edit_new_customized_file"></a>
<h3> Modificar el archivo </h3>
En esta ventana usted puede elegir un archivo para su <a href="#content_management_top"><u>personalización</u></a>.
<br>
Elija el archivo que desea personalizar en el menú desplegable "Seleccionar archivo".<br>
Al seleccionar el archivo, su contenido se mostrará en el área de texto ubicada a continuación ("Contenido").<br>

<ul>
	<li>Usted podrá <b>Modificar</b> el archivo luego de hacer click en el botón "Modificar".</li>
	<br>
	<li>Usted podrá <b>Guardar</b> los cambios efectuados, haciendo click en el botón "Aceptar".</li>
</ul> 
Cuando usted guarda un archivo personalizado modificado, su "Contenido original" es guardado y mostrado debajo de su personalización. 
Cuando se instala una actualización en Cyclos, se mantendrá el archivo personalizado,
pero se comprobará si existen diferencias entre el "Contenido original" y el "Nuevo contenido" en la actualización.<br>
Cuando usted finalice la personalización del archivo, el contenido original será utilizado.

<hr>
<a name="images"></a>
<h2> Imágenes personalizadas </h2>
También usted puede personalizar las imágenes de Cyclos.<br>
Existe un conjunto de imágenes del sistema, pero usted también puede subir sus propias imágenes 
y utilizarlas en cualquier archivo personalizado.

Puede subir sus imágenes a través del "Menú: Gestión de contenido", 
donde puede optar por cambiar las <a href="#system_images"><u>imágenes del sistema</u></a>,
cargar sus propias <a href="#custom_images"><u>imágenes personalizadas</u></a> 
o cambiar las <a href="#style_images"><u>imágenes de las hojas de estilo</u></a>.
<hr>

<A NAME="system_images"></A>
<h3> Imágenes del sistema </h3>
<br><br> 
Esta ventana muestra una lista con el sistema actual de <a href="#images"><u>imágenes</u></a> en Cyclos.<br>
Si usted hace click sobre una imagen en miniatura (vista previa) de la lista, la misma se mostrará en su tamaño verdadero en una ventana emergente.<br>
<br>
Es posible sustituir una imagen del sistema en la siguiente <a href="#images_upload"><u>ventana</u></a> ("Actualizar imagen del sistema").
<hr class="help">

<A NAME="custom_images"></A>
<h3> Imágenes personalizadas </h3>
<br><br> 
Esta ventana muestra una lista con las <a href="#images"><u>imágenes</u></a> personalizadas en Cyclos.<br> 
Si usted hace click sobre una imagen en miniatura (vista previa) de la lista, la misma se mostrará en su tamaño verdadero en una ventana emergente. <br> 
Las imágenes personalizadas pueden ser utilizadas en los <a href="#statics"><u>archivos estáticos</u></a>, 
<a href="#helps"><u>archivos de ayuda</u></a>, e incluso en mensajes.
<br><br> 
Usted puede Eliminar una imagen seleccionando el botón de Eliminación 
<img border="0" src="${images}/delete.gif" width="16" height="16">&nbsp; correspondiente.<br>
<br>
Usted puede agregar una imagen personalizada en la siguiente <a href="#images_upload"><u>ventana</u></a> ("Nueva imagen").
<hr class="help">

<A NAME="style_images"></A>
<h3> Imágenes de hojas de estilo </h3>
<br><br> 
Las imágenes de hojas de estilo, son <a href="#images"><u>imágenes</u></a> que pueden ser
utilizadas en el diseño de la ventana Cyclos, como títulos, elementos de menú, botones y fondos.<br> 
Puede utilizar estas imágenes en un <a href="#css"><u>archivo CSS</u></a>.
<br><br> 
Usted puede Eliminar una imagen seleccionando el botón de Eliminación 
<img border ="0" src="${images}/delete.gif" width="16" height="16"> correspondiente.<br>
<br>
Si usted hace click sobre una imagen en miniatura (vista previa) de la lista, la misma se mostrará en su tamaño verdadero en una ventana emergente.<br>
Puede agregar una imagen personalizada en la siguiente <a href="#images_upload"><u>ventana</u></a> ("Nueva imagen de hojas de estilo").
<hr class="help">

<A NAME="images_upload"></A>
<h3> Cargar imagen </h3>
<br><br> 
En el caso de una <a href="#system_images"><u>imagen del sistema</u></a>, usted debe primero elegir la imagen que desea reemplazar, 
seleccionándola por su nombre en la lista desplegable "Cargar nueva imagen".<br>
Tenga en cuenta que este menú desplegable no estará disponible cuando se encuentre cargando una <a href="#custom_images"><u>imagen personalizada</u></a>.
o una <a href="#style_images"><u>imagen de hojas de estilo</u></a>. <br>
<br>
A continuación, haga click en el botón "Examinar" y localice la imagen que desea cargar en su computadora local o de red.<br> 
Asegúrese de que posea extensión jpg, jpeg, gif o png.<br> 
<br>
Para finalizar, haga click en el botón "Aceptar".<br> 
La nueva imagen aparecerá en la lista ubicada en la parte superior de la ventana.
<!--<hr class="help">-->
<hr>

<a name="themes"></a>
<h2> Temas </h2>
Un tema, a veces llamado "skin", define el diseño de Cyclos (barra superior de estado en inicio de sesión, menú y funciones de las ventanas).<br>
Los temas se constituyen en una forma rápida de cambiar los diseños, sin la necesidad de realizar una edición general de los archivos de hojas de estilo, 
y de las imágenes de hojas de estilo.
<br><br>
Los temas puedem ser accedidos a través del "Menú: Gestión de Contenido> Temas".
<hr>

<a name="select_theme"></a>
<h3> Selección de tema </h3>
<br><br> 
Para seleccionar un <a href="#themes"><u>tema</u></a> usted debe seleccionarlo de la lista desplegable "Tema" 
y a continuación, hacer click en el botón Aceptar.<br> 
Usted deberá actualizar/recargar su navegador para que el tema sea presentado. 
En algunos casos es necesario borrar la caché del navegador. <br>
<br>
Tenga en cuenta que si no ha utilizado antes esta funcionalidad, los temas pueden no encontrarse disponibles, resultando en la lista desplegable vacía. 
En tal caso, deberá comenzar por <a href="#import_theme"><u>importar</u></a> un nuevo tema.
<br><br> 
Al hacer click en el botón "Eliminar", el tema se eliminará y la modificación de diseño personalizado será borrada.<br> 
Así que si usted ha realizado su propia adaptación de la <a href="#css"><u>hoja de estilo general</u></a> 
o de <a href="#style_images"><u>imágenes de hojas de estilo</u></a>, es posible que desee 
<a href="#export_theme"><u>exportarlos</u></a> como un tema, con el fin de poder volver a importarlos en el futuro.<br> 
La función de los temas sólo afecta a las hojas de estilo, no afecta a los 
<a href="#statics"><u>archivos estáticos</u></a> ni a los <a href="#helps"><u>archivos de ayuda</u></a>.
<hr class="help">

<A NAME="import_theme"></A>
<h3> Importar nuevo tema </h3>
<br><br> 
Esta función permite importar un nuevo <a href="#themes"><u>tema</u></a>. <br> 
El archivo correspondiente a un tema en Cyclos posee la extensión ".theme".<br> 
Usted sólo debe utilizar el botón "Examinar" para ubicar el archivo deseado.<br> 
Luego haga click en el botón "Aceptar" para su importación.

<hr class="help">
<A NAME="export_theme"></A>
<h3> Exportar configuración actual como tema </h3>
<br><br> 
Si usted ha realizado su propio <a href="#themes"><u>tema</u></a> (mediante la modificación de un 
<a href="#css"><u>archivo de hoja de estilo (CSS)</u></a> y/o de <a href="#style_images"><u>imágenes de hoja de estilo</u></a>), 
puede exportar el tema como un archivo (extensión ".theme"). <br>
Esta función sólo exportará el tema actualmente activo.<br> 
Simplemente complete los campos requeridos y haga click en el botón "Aceptar". <br>
<br>
Usted también puede exportar un subconjunto de los archivos de un tema, 
marcando las casillas de verificación correspondientes, ubicadas en la parte inferior de la ventana ("Estilos a exportar"). <br> 
Tres opciones estarán disponibles:
<ul>
	<li> <b> Sistema: </b> Estos son los principales archivos <a href="#jsp"><u>.jsp</u></a> y <a href="#css"><u>.css</u></a>. </li>
	<li> <b> Página de acceso: </b> Esta es la página de acceso principal. </li>
	<li> <b> Dispositivo móvil: </b> Son las páginas de acceso a través de teléfono móvil. </li>
</ul>
<br> 
<i> Si usted desarrolla sus propios temas, por favor considere la presentación de los mismos al equipo de desarrollo Cyclos,</i> 
para que podamos ponerlos a disposición de otros usuarios de la Comunidad.
<br>

<hr class="help">
<A NAME="infotexts"></A>
<h2>Mensajes informativos</h2> 
Los mensajes informativos (Infotexts) son textos almacenados en Cyclos, bajo uno o varios alias, que pueden ser recuperados a través de Web services.
Un uso típico de los mismos es en el módulo SMS.<br>
Los usuarios pueden enviar una palabra clave por SMS, por ejemplo, "promoción" y recibir su texto correspondiente.<br>
Los mensajes informativos pueden ser registrados y gestionados en Cyclos.<br>
Los mensajes informativos pueden poseer un título y un cuerpo de mensaje.
Esto es utilizado de forma más común si se utilizan para su inclusión en sitios Web.<br>
<br>
Los detalles de las características y opciones de los mensajes informativos pueden ser encontradas en la página de 
<a href="${pagePrefix}content_management#infotexts_edit"><u>modificación de mensajes informativos (Infotext)</u></a>.<br> 
<br> 
Información sobre cómo recuperar mensajes informativos a través de Web services puede ser encontrada en la 
<a href="http://project.cyclos.org/wiki/index.php?title=Web_services#Info_texts" target="_blank"><u>Wiki</u></a> de Cyclos.
<hr class="help"> 

<A NAME="infotext_search"></A>
<h2>Buscar mensajes informativos</h2> 
En esta página usted puede buscar 
<a href="${pagePrefix}content_management#infotexts"><u>mensajes informativos</u></a> 
existentes en el sistema y/o crear uno nuevo, presionando el botón "Crear nuevo".
<hr class="help">

<A NAME="infotexts_result"></A>
<h2>Resultado de búsqueda</h2> 
Esta página muestra el resultado de la búsqueda realizada.<br>
<ul>
	<li>Usted puede modificar un mensaje informativo, seleccionando el ícono <img border="0" src="${images}/edit.gif" width="16" height="16"> correspondiente. <br>
	<li>Usted puede eliminar un mensaje informativo, seleccionando el ícono <img border="0" src="${images}/delete.gif" width="16" height="16"> correspondiente.<br>
</ul>
<br>
<hr class="help">

<A NAME="infotexts_edit"></A>
<h2>Nuevo / Modificar mensaje informativo</h2>
En esta página usted puede definir las reglas y el contenido del mensaje informativo.
Las siguientes opciones se encuentran disponibles:
<ul>
	<li><b>Alias: </b>
	Esta es la clave que se utilizará para recuperar el texto correspondiente (texto y descripción). 
	Se puede definir más de una clave. En este caso las claves adicionales trabajan como alias.
	
	<li><b>Texto: </b>
	Este es el contenido del mensaje informativo a recuperar. 
	En caso de textos cortos (ejemplo: SMS), este es el único texto utilizado.
	
	<li><b>Descripción: </b>
	Para las funciones en las que se utiliza un texto y una descripción (ejemplo: sitios Web), 
	el cuerpo del texto/descripción se define aquí.
	
	<li><b>Validez (Desde/Hasta): </b> 
	Aquí usted puede definir el período de validez del mensaje informativo, durante el cual se encontrará activo (y podrá ser recuperado).
	
	<li><b>Activo: </b> 
	Aquí usted puede rápidamente habilitar o deshabilitar (activar/desactivar) el mensaje informativo.<br>
	Cuando desactiva (deshabilita esta opción), el mensaje informativo no podrá ser recuperado.
</ul>
<!-- <hr class="help"> -->

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

</span>
