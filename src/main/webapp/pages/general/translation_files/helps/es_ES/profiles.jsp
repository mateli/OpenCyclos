<div style="page-break-after: always;">
<a name="profiles_top"></a>
<br><br>

El Perfil es la información personal asociada a un miembro. <br>
<span class="admin">
Existen perfiles de administrador y perfiles de usuario (miembro). <br>
El acceso de un administrador es sólo para fines administrativos y no posee ninguna cuenta, ni otras funciones de miembro:
como contactos, referencias, etc.; Por lo tanto, el perfil de un administrador es mucho más sencillo que el perfil de un miembro. <br>
<br>
Muy importante es que, conectado al Perfil de cada miembro, podrá acceder a la ventana de <br>
<a href="#actions"><u> Acciones para ...</u></a>, lo que le permite realizar diversas acciones con el miembro deseado.
</span>
El "Código de miembro" es la denominación o código identificativo con el que el miembro se conecta a Cyclos.<br>
El "Nombre" es el verdadero nombre del miembro.

<br><br> <i> ¿Dónde los encuentro? </i> <br>
Usted puede acceder a su perfil a través del "Menú: Personal> Perfil". <br>
<br>
<span class="member"> Se puede acceder al perfil de otro miembro de dos(2) maneras:
<ul>
	<li> <b> A través de una búsqueda: </b> Realizar una 
	<a href="${pagePrefix}user_management#search_member_by_member"><u>búsqueda de miembros</u></a> a través del <br>
	"Menú: Buscar> Miembros".</li>
	<br>
	<li> <b> A través de sus contactos: </b> Utilizar su <a href="${pagePrefix}user_management#contacts"><u>lista de contactos</u></a> 
	para acceder al perfil de un miembro, a través del "Menú: Personal> Contactos".</li>
</ul>
</span> 
<span class="admin"> Puede acceder al perfil de otro administrador a través del
"Menú: Usuarios & Grupos> Gestionar administradores". <br>
Puede acceder al perfil de un miembro a través del 
"Menú: Usuarios & Grupos> Administrar usuarios".
</span>
<span class="broker"> Como un broker, usted puede acceder al perfil de sus
propios miembros a través del "Menú: Brokering> Miembros".
</span>

<span class="admin">
<br><br> <i> ¿Cómo hacerlo funcionar? </i> <br>
Cualquier miembro puede ver su propio perfil. 
Para los miembros y grupos de brokers, es necesario establecer qué perfiles de los 
otros grupos pueden ser visitados.

Existen varios permisos relacionados a los perfiles:
<ul>
	<li> Para el control de otros grupos que el miembro puede ver, establecer estos
	<a href="${pagePrefix}groups#manage_group_permissions_member"><u>permisos para el grupo</u></a>. 
	
	<li> Para un administrador poder cambiar el perfil de un miembro, es necesario configurar los 
	<a href="${pagePrefix}groups#manage_group_permissions_admin_member "><u>permisos (de miembros)</u></a> 
	correspondientes en el bloque de "miembros", y marcar la casilla "cambio de perfil".
	
	<li> También existen 
	<a href="${pagePrefix}groups#manage_group_permissions_admin_admin"><u>permisos (de administrador)</u></a> 
	para cambiar el perfil de un administrador, en el bloque de "administradores", en la casilla de verificación "cambio de perfil".
	
	<li> Para brokers, deberá configurar los 
	<a href="${pagePrefix}groups#manage_group_permissions_broker"><u>permisos (de broker)</u></a>, 
	para que puedan cambiar el perfil de sus miembros. <br>
	Esto puede hacerse en el bloque de "intermediación", a través de la casilla de verificación "cambiar el perfil de".
</ul>
</span>
<hr>

<span class="admin">
<a NAME="admin_profile"></a>
<h3> Perfil de Administrador </h3>
En esta página usted puede modificar el <a href="#profiles_top"><u>perfil</u></a> de un Administrador, 
ya sea su cuenta o el perfil de otro usuario administrador (por supuesto, dependiendo de sus permisos).<br>
<br>
Usted debe hacer click en el botón "Modificar" para realizar cambios; 
haga click en "Aceptar" para confirmar las modificaciones efectuadas.
<br><br> 
La mayoría de los campos son auto-explicativos. <br>
Si usted está visualizando el perfil de otro administrador, 
en la página se mostrará el grupo de administración al que pertenece el usuario.
<hr class="help">
</span>

<a NAME="member_profile"></a>
<h3> Perfil de Miembro / Mi perfil </h3>
El <a href="#profiles_top"><u>perfil</u></a> muestra y permite gestionar los datos personales del miembro.
<br><br>
<span class="member"> En el caso de su propio perfil, </span>
Usted puede añadir y modificar la información haciendo click en el botón "Modificar", 
ubicado en la parte inferior derecha de la ventana. <br> 
No olvide hacer click en el botón "Aceptar" para confirmar los cambios.

<span class="member">
<br><br> Por supuesto, el perfil de otros miembros no puede ser modificado, y cada
miembro podrá optar por hacer parte de su información invisible (ocultar) para otros miembros. 
Por lo tanto, el resto de este texto de ayuda es referido principalmente a los cambios en su propio perfil.
</span>

<br><br>Información general del formulario:
<ul>
	<li> <b><font color="red"> * </font>:</b> La información que es obligatoria está marcada con un asterisco rojo
	(<font color="red">*</font>) en el lado derecho del cuadro de edición.
	<li> <b> Ocultar: </b> Usted puede marcar como privado un campo, haciendo que no sea visible por otros miembros.
	Para ello marque la opción "Ocultar" que aparece a continuación del campo correspondiente.
</ul>
<br><br> El formulario es sencillo, y la mayoría de los campos se explican por sí mismos.<br>
Algunos comentarios sobre los siguientes campos específicos, se indican a continuación
(tenga en cuenta que los campos descriptos pueden no ser visibles para usted, dependiendo de la configuración de su organización):

<ul>
	<span class="admin">
	<li> <b> Último acceso: </b> Este campo mostrará "Está conectado" en caso
	de que el miembro esté utilizando el programa Cyclos en el momento. 
	En este caso, puede forzar la desconexión de este miembro mediante 
	el botón en la ventana de abajo "desconectar usuario conectado".
	</span>
	
	<li> <b> Cumpleaños: </b> Usted puede utilizar el ícono	calendario <img border="0" src="${images}/calendar.gif" width="16" height="16"> 
	que le ayudará a ingresar la fecha de su cumpleaños.
	<br>
	<li> <b> Broker: </b> Este campo sólo es visible cuando la organización utiliza el sistema de
	<a href="${pagePrefix}brokering"><u>brokers</u></a>.<br> 
	Este campo no es modificable, pero puede utilizar la opción "Abrir perfil" que aparece junto a él, 
	para acceder al perfil de su broker.
	<br>
	<li> <b> Fecha de registro: </b> Si el miembro se encuentra en el grupo "Miembros pendientes"
	(grupo sin una cuenta asignada), este campo mostrará la fecha en la que el miembro fué registrado
	(ya sea por un administrador, broker, o por cuenta propia del miembro en la página de registro público).
	Esta fecha NO se mostrará si el miembro es parte de un grupo activo 
	(pero se mostrará la fecha de activación en la página de reportes del miembro).
	<br>
	<li> <b> Agregar imagen: </b> Marque esta casilla y podrá acceder a
	<a href="#picture"><u>agregar una imagen</u></a> a su perfil.
</ul>
<hr class="help">


<a name="picture"></a>
<h3> Fotos en su perfil o anuncio </h3>
Cyclos le permite cargar fotos en su <a href="#profiles_top"><u>perfil</u></a> o anuncio. <br>
Siga las siguientes instrucciones si desea que otros miembros puedan ver su foto:
<ol>
	<li> Haga click en el botón "Modificar" para realizar cambios en el formulario;
	<li> Marque la casilla "Añadir Imagen"; 
	<li> Haga click en el botón "Examinar" para buscar y elegir una foto en su equipo que desee cargar;
	<li> Elija la foto y haga click en "Abrir". Tenga en cuenta que existe un tamaño máximo
	para el archivo (que se mostrará en la ventana) y que sólo los formatos JPG, GIF y PNG son aceptados;
	<li> Luego, ingrese el nombre de la foto en el "Título de Imagen";
	<li> Al hacer click en "Aceptar" la foto se cargará.
</ol>
Usted puede cargar más de una imagen. 
El número de imágenes que se puede cargar es definido por la administración.
<br>
Usted puede navegar a través de las imágenes <font color="blue">"<1/2/3>"</font>.<br> 
Puede cambiar su orden de visualización y seleccionar un texto que se mostrará por debajo de cada imagen, seleccionando el vínculo "Detalles".
<br>
Puede eliminar una imagen, seleccionando el vínculo "Eliminar" ubicado debajo de la imagen. <br>
Otros miembros podrán ver su imagen de la misma forma (no podrán eliminarla).

<hr>
<a name="actions"></a>
<h2> Acciones para ...</h2>
Debajo del <a href="#profiles_top"><u>perfil</u></a> de cada miembro, se encuentra una ventana
con el acceso a las acciones que usted puede realizar en relación con este miembro.<br> 
Estas acciones van desde efectuar pagos, <span class="admin broker"></span>
dar referencias, envío de mensajes, etc.

La disponibilidad de las acciones puede variar, dependiendo de la configuración, las normas de 
su organización. Además, algunas de las acciones enumeradas en las ventanas de ayuda,
no estarán disponibles para usted si no cuenta con los permisos correspondientes.
<br>
Las ventanas de ayuda ofrecen un resumen de las acciones disponibles, que son:
todas las acciones que podrían aparecer en su ventana.
Como ya se ha dicho, es probable que usted no acceda a todas ellas, dependiendo de la
configuración del sistema y de sus permisos. <br>
Para una explicación más detallada, debe consultar la ayuda específica sobre cada acción, 
dentro de su ventana correspondiente y luego de hacer click en el botón (acción) deseado. 
<br>
Usted también puede seguir los vínculos, que le brindarán una explicación complementaria sobre el tema. 
(los temas se analizan de izquierda a derecha, línea a línea de arriba a abajo).<br>
<br><br> 
Las acciones y perfiles de los operadores se examinan en las ayudas de los operadores.
<hr>
<a name="actions_for_member_by_operator"></a>
<a name="actions_for_member"></a>
<span class="member">
<h3> Acciones para miembro </h3>
En esta ventana, usted puede realizar varias acciones en relación con este miembro:
<ul>
	<li> <b>Realizar pago:</b> Le permite realizar un pago al miembro seleccionado;
	
	<li> <b>Ver y Dar Referencias:</b> Le permite visualizar las experiencias/referencias de otros miembros
	con el miembro seleccionado. Usted también puede definir una 
	<a href="${pagePrefix}references"><u>referencia</u></a> sobre este miembro;
	
	<li> <b>Calificaciones de transacciones:</b> Le permite a usted dar una 
	<a href="${pagePrefix}transaction_feedback"><u>calificación</u></a> a una transacción;
	
	<li> <b> Enviar factura:</b> Le permite enviar una <a href="${pagePrefix}invoices"><u>factura</u></a></b> a este miembro;
	
	<li> <b> Ver anuncios:</b> Le permite visualizar los 
	<a href="${pagePrefix}advertisements"><u>anuncios</u></a></b> del miembro seleccionado;
	
	<li> <b> Enviar mensaje de correo electrónico:</b> Esta opción acciona el gerenciador de correo electrónico por defecto 
	de su PC, y le permite enviarle un mail al miembro seleccionado;
	
	<li> <b> Enviar mensaje:</b> Le permite enviar un mensaje al miembro, mediante el sistema de 
	<a href="${pagePrefix}messages"><u>mensajería interna</u></a> de Cyclos;
	
	<li> <b> Ver reportes: </b> Lo llevará a los <a href="${pagePrefix}reports#member_activities"><u>reportes</u></a>
	con la información acerca de las actividades de este miembro;
	
	<li><b> Agregar a lista de contacto:</b> Permite agregar el miembro seleccionado a su 
	<a href="${pagePrefix}user_management#contacts"><u>lista de contactos</u></a></b>.
</ul>
<hr class="help">
</span>

<span class="broker"> <a name="actions_for_member_by_broker"></a>
<h3> Acciones de broker para miembro </h3>
Esta ventana es el punto de partida para todas las acciones del 
<a href="${pagePrefix}brokering"><u>broker</u></a>
en relación con este miembro. 
Por lo tanto, esta sería la página principal para sus tareas como broker.<br>
Si lo desea, puede leer el <a href="#actions"><u>las notas generales</u></a> sobre esta
ventana en primer lugar.
<ul>
	<li> <b> Administrar anuncios: </b> Añadir, eliminar o editar 
	<a href="${pagePrefix}advertisements"><u>anuncios</u></a> para este miembro;
	
	<li> <b> Información de cuentas: </b> Muestra la historia de las cuentas de este
	miembro, balances, y el historial de transacciones;
	
	<li> <b> Ver <a href="${pagePrefix}payments#scheduled"><u>pagos agendados</u></a></b>;
	
	<li> <b> Ver <a href="${pagePrefix}payments#authorized"><u>autorizaciones de pagos:</u></a></b> 
	Esto le llevará a los pagos que el miembro debe autorizar como receptor de los pagos.<br>
	Esto es aplicable para una configuración en la que el receptor de un pago (miembro) debe autorizar el pago 
	antes de que se le acredite en el saldo de su cuenta. 
	Como broker, puede actuar como si fuera el miembro y autorizar estos pagos.
	Esta opción sólo está disponible si procede.
	
	<li> <b> Pago de miembro a miembro: </b> Realizar un pago a otro miembro;
	
	<li> <b> Pago de miembro al sistema: </b> Realizar un pago a la administración/Sistema;
	
	<li> <b> Auto-pago: </b> Realizar una transferencia entre las diferentes cuentas
	de este miembro. Esto sólo es posible si este miembro posee más de una cuenta;
	
	<li> <b> Administrar referencias: </b> Aquí puedes ver cómo otros miembros valúan a
	este miembro, y puede ver qué tipo de experiencias este usuario ha tenido con
	otros miembros. 
	Se muestran las <a href="${pagePrefix}references"><u>referencias</u></a> recibidas. 
	El broker puede cambiar o borrar las referencias dadas a los miembros (si tiene los permisos);
	
	<li> <b> Ver préstamos: </b> La información general de los <a href="${pagePrefix}loans"><u>préstamos</u></a>
	de este miembro;
	<li> <b> Ver facturas: </b> Muestra todas las <a href="${pagePrefix}invoices"><u>facturas</u></a>
	que este usuario ha enviado y recibido;
	<li> <b> Factura de miembro a miembro: </b> Enviar una factura a otro miembro;
	<li> <b> Factura de miembro a sistema: </b> Enviar una factura al sistema;
	
	<li> <b> Grupos de préstamo: </b> <a href="${pagePrefix}loan_groups"> Muestra los <u>grupos de préstamo</u></a> de los que es parte el miembro.
	<li> <b> Administrar contraseñas: </b> Le permite restablecer las 
	<a href="${pagePrefix}passwords"><u>contraseñas</u></a> del miembro.
	<li> <b> Acceso externo: </b> Le permite gestionar que a través de 
	<a href="${pagePrefix}settings#channels"><u>canales</u></a>, los miembros pueden acceder a
	Cyclos. También le da la posibilidad de cambiar los 
	<a href="${pagePrefix}passwords#pin"><u>números de PIN</u></a>
	(por ejemplo, la contraseña para acceder a la tienda virtual Web).
	<li> <b> Comentarios: </b> Cualquier comentario acerca de este miembro se puede ingresar aquí. 
	Estos comentarios son para usted o para otros administradores y brokers. 
	Ninguno de los miembros son capaces de ver estos comentarios. 
	<li> <b> ...:</b> <a href="${pagePrefix}member_records"> Cualquier otro <u>registro de miembro</u></a> que se haya definido, 
	será listado aquí también con un botón.
	<li> <b> Documentos de miembro: </b> Esta página da acceso a la página de 
	<a href="${pagePrefix}documents"><u>documentos</u></a> del miembro, que se pueden imprimir.
	<li> <b> Contratos de comisión: </b> Le da acceso a una página donde se puede
	revisar o crear <a href="${pagePrefix}brokering#commission_contract"><u>contratos de comisión</u></a>
	entre usted y sus miembros, acerca de sus servicios como broker.
	<li> <b> Registros SMS: </b> Le da acceso a los 
	<a href="${pagePrefix}reports#sms_log"><u>registros SMS</u></a> del miembro, donde se
	mantienen los mensajes SMS enviados. 
	El sistema puede ser configurado para enviar Mensajes SMS en varias ocasiones.
</ul>
<hr class="help">
</span>
<span class="admin"> <a name="actions_for_member_by_admin"></a>
<h2> Acciones para miembro (del administrador) </h2>
Esta ventana es el punto de partida para todas las acciones de la administración relacionadas con los miembros. <br>
Por lo tanto, esta sería la página principal para las tareas y 
<a href="${pagePrefix}groups#account_admins"><u>administración de la cuenta</u></a>. <br>
Si lo desea, puede leer las <a href="#actions"><u>notas generales</u></a> sobre esta ventana. <br>
<br>
Las acciones del administrador están organizadas en secciones. 
Cada sección contiene su ayuda correspondiente, a fin de mantener el tamaño de los bloques de ayuda, manejable y
legible.
<br><br> Existen las siguientes secciones: <br> 
(haga click en el enlace para visualizar la descripción de los botones en cada sección)
<ul>
	<li> <b> <a href="#access_actions"><u>Sección de acceso:</u></a></b> 
	Todo sobre el control de acceso de los miembros al sistema y al software;</li>
	
	<li> <b> <a href="#ads_actions"><u>Sección de anuncios:</u></a></b> Todo sobre
	la gestión de la publicidad de los miembros;</li>
	
	<li> <b> <a href="#accounts_actions"><u>Sección cuentas:</u></a></b> Todo
	sobre las cuentas de los miembros, pagos y facturas;</li>
	
	<li> <b> <a href="#member_info_actions"><u>Sección de información de miembro:</u></a></b> 
	Toda la información sobre el miembro, su actividad, etc.;</li>
	
	<li> <b> <a href="#brokering_actions"><u>Sección de brokers:</u></a></b> Todo sobre
	las acciones relativas al sistema de  <a href="${pagePrefix}brokering"><u>brokers</u></a>
	en Cyclos;</li>
	
	<li> <b> <a href="#loans_actions"><u>Sección de préstamos:</u></a></b> Todas las acciones
	relacionados con los préstamos de este miembro.</li>
</ul>
<hr class="help">
</span>
<br>

<span class="admin"> <a name="access_actions"></a>
<h3> Acciones para miembro - Sección de acceso </h3>
(Esta reseña es parte de la ventana de "<a href="#actions_for_member_by_admin"><u>acciones de miembro</u></a>".)
<ul>
	<li> <b> Permitir al usuario iniciar sesión ahora: </b> Esto sólo será visible si el acceso de miembro
	está bloqueado, por ejemplo, debido a sucesivos intentos de inicio de sesión con un
	contraseña equivocada. Esta acción bloqueará el acceso del miembro por un determinado
	período de tiempo, de acuerdo con la <a href="${pagePrefix}settings"><u>configuración</u></a> establecida.
	Dicho miembro deberá contactar a la administración. Usted podrá
	inmediatamente desbloquear al miembro para su ingreso a través de este botón. 
	Después de hacer click en él, el miembro volverá a intentar la conexión. <br>
	<b> Nota: </b> Por supuesto, esta acción no reinicia la 
	<a href="${pagePrefix}passwords"><u>contraseña</u></a> de ingreso del miembro. 
	Si el miembro no recuerda su contraseña, puede usted restablecerla a través del botón "Administrar
	contraseñas" en la misma sección de esta ventana.
	<li> <b> Desconectar usuario conectado: </b> Si el usuario está conectado actualmente (en ese momento), 
	usted puede forzar su desconexión haciendo click en este botón. 
	Este botón <b> sólo </b> es visible si el miembro está conectado en la actualidad. <br>
	Una de las razones de la desconexión de un miembro sería si un miembro ha cerrado el navegador
	sin haberse desconectado del sistema de forma correcta. 
	Otra razón para la desconexión de un miembro, sería en el caso de una emergencia,
	sospecha de abuso o piratería informática de una cuenta. 
	
	<li> <b> Administrar contraseñas: </b> Le permite restablecer las 
	<a href="${pagePrefix}passwords"><u>contraseñas</u></a> del miembro.
	<li> <b> Acceso externo: </b> Le permite gestionar a través de los 
	<a href="${pagePrefix}settings#channels"><u>canales</u></a> el acceso de los miembros a Cyclos. 
	También le da la posibilidad de cambiar los 
	<a href="${pagePrefix}passwords#pin"><u>números de PIN</u></a> 
	(por ejemplo, el número contraseña para acceder a la tienda virtual Web).
	<li> <b> Cambiar permisos de grupo: </b> Le permite cambiar el 
	<a href="${pagePrefix}groups"><u>grupo</u></a> al que un miembro pertenece. 
	Cada miembro pertenece a un grupo. Esta página le permitirá cambiar el grupo al que
	pertenece el miembro y  enviar un e-mail de activación. 
	La forma básica para el control del acceso a Cyclos es a través de grupos. 
	Si usted desea eliminar un miembro del sistema, deberá usar este botón y mover el miembro al grupo de 
	"miembros eliminados".
	<li> <b> Registros SMS: </b> Le da acceso a los 
	<a href="${pagePrefix}reports#sms_log"><u>registros SMS</u></a>, donde los
	mensajes SMS enviados a este miembro se mantienen. 
	El sistema puede ser configurado para enviar mensajes SMS en varias ocasiones.
</ul>
<hr class="help">
</span>

<span class="admin"> <a name="ads_actions"></a>
<h3> Acciones para miembro - Sección de anuncios </h3>
(Esta reseña es parte de la ventana de 
"<a href="#actions_for_member_by_admin"><u>acciones de miembro</u></a>".)
<ul>
<li> <b> Administrar anuncios: </b> Añadir, eliminar o editar 
<a href="${pagePrefix}advertisements"><u>anuncios</u></a> para este miembro.
	
</ul>
<hr class="help">
</span>

<span class="admin"> <a name="accounts_actions"></a>
<h3> Acciones para miembro - Sección cuentas </h3>
(Esta reseña es parte de la ventana de 
"<a href="#actions_for_member_by_admin"><u>acciones de miembro</u></a>".)
<ul>
	<li> <b> Información de la cuenta: </b> Le muestra la historia de las cuentas del 
	miembro, con indicación de balances y el historial de transacciones.
	
	<li> <b> Ver <a href="${pagePrefix}payments#scheduled"><u>pagos agendados</u></a> </b>
	
	<li> <b> <a href="${pagePrefix}payments#authorized"><u>Pagos a autorizar:</u></a> </b> Esto le llevará a los pagos que el miembro debe
	autorizar como receptor de los pagos.
	Esto es aplicable para una configuración en la que el receptor de un pago (miembro) debe autorizar el pago 
	antes de que se le acredite en el saldo de su cuenta. 
	Como broker, puede actuar como si fuera el miembro y autorizar estos pagos.
	Esta opción sólo está disponible si procede.
	
	Esto se corresponde con los permisos de pago:
	"<a href="${pagePrefix}groups#manage_group_permissions_member"><u>Autorizar el pago al receptor</u></a> ".
	
	<li> <b> <a href="${pagePrefix}payments#transfer_authorizations_by_admin"><u>Aguardando autorización</u></a> </b>: 
	Aquí usted tiene una visión general de los pagos que usted, como administrador, deberá 
	<a href="${pagePrefix}payments#authorized"><u>autorizar para el miembro</u></a>, 
	con el fin de que sean retribuídos con cargo a su cuenta. 
	Esto es aplicable para las situaciones en las que los miembros no puedan realizar determinados (o todos los) pagos,
	porque debe ser previamente autorizado por un administrador o un broker. <br>
	Esto corresponde a las "<a href="${pagePrefix}groups#manage_group_permissions_member"><u>Permisos de cuentas> Autorizaciones de pagos</u></a>";

	<li> <b> Pago de sistema a miembro: </b> Pagar al miembro desde una cuenta de sistema.
	
	<li> <b> Pago de miembro a miembro: </b> Realizar un pago a otro miembro como si fuera el miembro.
	
	<li> <b> Pago de miembro al sistema: </b> Realizar un pago a la organización/sistema, como si fuera el miembro.
	
	<li> <b> Auto-pago: </b> Realizar una transferencia entre las diferentes cuentas
	de este miembro. Esto sólo es posible si este miembro posee más de una cuenta.

	<li> <b> Ver facturas: </b> Muestra todas las <a href="${pagePrefix}invoices"><u>facturas</u></a>
	que este usuario ha enviado y recibido.
	
	<li> <b> Factura del sistema a miembro: </b> Enviar a este miembro una factura desde una cuenta del sistema, 
	por la cual el miembro deberá pagar.
	
	<li> <b> Factura de miembro a miembro: </b> Enviar una factura a otro miembro.
	
	<li> <b> Factura de miembro a sistema: </b> Enviar una factura al sistema.
	
	<li> <b> Límite de crédito: </b> 
	Aquí usted puede configurar el límite de crédito para este miembro. 
	Tenga en cuenta que este es el límite de crédito sólo para este miembro en particular, y no para el grupo.
	El límite de crédito grupal se establece en la configuración a nivel de grupo.
</ul>
<hr class="help">
</span>
	
<span class="admin"> <a name="member_info_actions"></a>
<h3> Acciones para miembro - Sección de información de miembro</h3>
(Esta reseña es parte de la ventana de "<a href="#actions_for_member_by_admin"><u>acciones de miembro</u></a>".)
<ul>
	<li> <b> Ver reporte: </b> Esta opción le llevará a los 
	<a href="${pagePrefix}reports#member_activities"><u>reportes</u></a> con la información
	acerca de la actividad de este miembro.
	
	<li> <b> Comentarios: </b> Cualquier comentario acerca de este miembro se puede ingresar aquí. 
	Estos comentarios son para usted o para otros administradores y brokers. 
	Ninguno de los miembros son capaces de ver estos comentarios. 
	
	<li> <b> Registro de miembro:</b> 
	<a href="${pagePrefix}member_records"> Cualquier otro <u>registro de miembro</u></a> 
	que se haya definido, será listado aquí también con un botón.
	
	<li> <b> Administrar referencias: </b> Aquí puedes ver cómo otros miembros evalúan a
	un miembro, y se puede ver qué tipo de experiencia ha tenido este usuario con
	otros miembros. 
	Se muestran las <a href="${pagePrefix}references"><u>referencias</u></a> recibidas. 
	Un broker puede cambiar o borrar las referencias dadas a los miembros (si tiene los permisos).
	
	<li> <b> Calificaciones de transacciones: </b> Le permite a usted dar una 
	<a href="${pagePrefix}transaction_feedback"><u>calificación</u></a> 
	a una transacción.
	
	<li> <b> Enviar mensaje de correo electrónico </b>.
	
	<li> <b> Enviar un mensaje </b> con el 
	<a href="${pagePrefix}messages"><u>sistema de mensajería interna</u></a> de Cyclos.
	
	<li> <b> Documentos de miembro: </b> Esta página da acceso a la página de 
	<a href="${pagePrefix}documents"><u>documentos</u></a> del miembro, que se pueden imprimir.
	
	<li> <b> Contratos de comisión: </b> Le da acceso a una página donde se puede
	revisar o crear <a href="${pagePrefix}brokering#commission_contract"><u>contratos de comisión</u></a>
	entre usted y sus miembros, acerca de sus servicios como broker.
</ul>
<hr class="help">
</span>

<span class="admin"> <a name="brokering_actions"></a>
<h3> Acciones para miembro - Sección de brokers </h3>
(Esta reseña es parte de la ventana de "<a href="#actions_for_member_by_admin"><u>acciones de miembro</u></a>".)
<ul>
	<li> <b> Establecer broker: </b> En esta página usted puede cambiar el 
	<a href="${pagePrefix}brokering"><u>broker</u></a> al que pertenece el miembro.
	
	<li> <b> Lista de miembros (de broker): </b> Esta opción sólo estará disponible si el
	miembro que está visualizando es un <a href="${pagePrefix}brokering"><u>broker</u></a>.
	En ese caso, se mostrará a los miembros que pertenecen a ese broker, y tendrá
	la oportunidad de agregar un nuevo miembro para este broker.
	
	<li> <b> Ajuste de comisiones: </b> Esta opción sólo estará disponible si el miembro
	que está visualizando es, de hecho, un broker. 
	Aquí podrá ver los <a href="${pagePrefix}brokering#commission_contract"><u>contratos de comisión</u></a>, 
	y buscar entre los contratos de comisión específicos del broker y sus miembros.
</ul>
<hr class="help">
</span>

<span class="admin"> <a name="loans_actions"></a>
<h3> Acciones para miembro - Sección de préstamos</h3>
(Esta reseña es parte de la ventana de "<a href="#actions_for_member_by_admin"><u>acciones de miembro</u></a>".)
<ul>
	<li> <b> Ver préstamos: </b> Le muestra un panorama general de los 
	<a href="${pagePrefix}loans"><u>préstamos</u></a> de este miembro.
	
	<li> <b> Concesión de préstamos: </b> Aquí puede otorgar un préstamo al miembro.
	
	<li> <b> Grupos de préstamos: </b> Gestión de los 
	<a href="${pagePrefix}loan_groups"><u>grupos de préstamos</u></a> a los que pertenece el miembro.
</ul>
<hr class="help">
</span>

<span class="admin"> <a name="actions_for_admin"></a>
<h2> Acciones de administrador </h2>
Aquí usted puede realizar varias acciones en relación con este administrador. 
Usted puede leer las <a href="#actions"><u>notas generales</u></a> sobre esta ventana primero.
<ul>
	<li> <b> Enviar correo electrónico </b>.
	
	<li> <b> Comentarios: </b> Cualquier comentario acerca de este administrador se puede ingresar aquí. 
	Los comentarios (observaciones) son un registro de miembro incluído en la base de datos predeterminada.<br>
	
	Cualquier otro tipo de <a href="${pagePrefix}member_records"><u>registro de miembro</u></a> definido para
	los administradores, será incluido también en este caso con un botón. <br>
	
	Como se pueden asignar permisos a registros de miembros para <a href="${pagePrefix}groups"><u>grupos</u></a> específicos, 
	es posible que se definan registros de miembros para grupos de administradores.
		
	<li> <b> Cambiar permisos de grupo: </b> Le permite cambiar los grupos a los que un
	administrador pertenece. Cada administrador pertenece a un grupo. 
	Esta página permitirá cambiar el grupo de la administración. 
	La forma básica para controlar el acceso a Cyclos es a través de grupos. 
	Si usted desea eliminar un administrador del sistema, deberá usar este botón y mover el administrador al
	grupo de "administradores eliminados".
	
	<li> <b> Permitir al usuario iniciar sesión ahora: </b> Esto sólo será visible si el acceso del administrador
	está bloqueado, por ejemplo, debido a sucesivos intentos de inicio de sesión con un
	contraseña equivocada. Esta acción bloqueará el acceso del administrador por un determinado
	período de tiempo, de acuerdo con la <a href="${pagePrefix}settings"><u>configuración</u></a> establecida.
	Dicho administrador deberá contactar a la administración. Usted podrá
	inmediatamente desbloquear al administrador para su ingreso a través de este botón. 
	Después de hacer click en él, el administrador volverá a intentar la conexión. <br>
	<b> Nota: </b> Por supuesto, esta acción no reinicia la 
	<a href="${pagePrefix}passwords"><u>contraseña</u></a> de ingreso del administrador. 
	Si el administrador no recuerda su contraseña, puede usted restablecerla a través del botón "Administrar
	contraseñas" en la misma sección de esta ventana.
	
	<li> <b> Desconectar usuario conectado: </b> Si el administrador está conectado actualmente (en ese momento), 
	usted puede forzar su desconexión haciendo click en este botón. 
	Este botón <b> sólo </b> es visible si el administrador está conectado en la actualidad. <br>ei
	Una de las razones de la desconexión de un administrador sería si ha cerrado el navegador
	sin haberse desconectado del sistema de forma correcta. 
	Otra razón para la desconexión de un administrador, sería en el caso de una emergencia,
	sospecha de abuso o piratería informática de una cuenta. 	
	
	<li> <b> Administrar contraseñas: </b> Le permite restablecer las contraseñas del administrador.
</ul>
<hr class="help">
<br><br>
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