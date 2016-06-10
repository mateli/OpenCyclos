<div style="page-break-after: always;">
<a name="settings_top"></a>
<br><br>

En este módulo se detalla la configuración y las definiciones del sistema Cyclos.

<i> ¿Dónde las encuentro? </i> <br>
Al módulo de configuración se puede acceder a través del "Menú: Configuración". <br>
<br>
Existen los siguientes tipos de ajustes:
<ul>
<li><b><a href="#local"><u>Configuración Local:</u></a></b> Todo sobre el sistema local, como el idioma, zona horaria y formatos.
<li><b><a href="#alerts"><u>Configuración de Alertas:</u></a></b> Configuración relativa a las alertas del sistema.
<li><b><a href="#access"><u>Configuración de acceso:</u></a></b> Ajustes en relación al acceso y niveles de seguridad.
<li><b><a href="#mail"><u>Configuración de Correo:</u></a></b> Configuración del servidor de correo.
<li><b><a href="#log"><u>Configuración del Log:</u></a></b> Configuración de los archivos de registro.
<li><b><a href="#channels"><u>Canales:</u></a></b> Configuración de los canales (medios de acceso).
<li><b><a href="#web_services_clients"><u>Clientes de servicios Web:</u></a></b> Configuración de los servicios Web.
<li><b><a href="#import_export"><u>Importar / Exportar:</u></a></b> Importar y exportar configuraciones.
</ul>
<hr>

<A NAME="local"></A>
<h2> Configuración Local </h2>
Estos son los ajustes relacionados con la localización y el sistema.<br>
Con el fin de realizar cambios, haga click en el botón "Modificar".<br>
Para confirmar los cambios, haga click en el botón "Aceptar".<br>
<br>
<b> Identificación de instancia de Cyclos </b>
<ul>
	<li> <b> Nombre de la aplicación: </b> Título de la aplicación, tal y como aparece en la barra superior de su navegador.

	<li> <b> Código de la aplicación: </b> Es el nombre que se utiliza para mensajes generados automáticamente por el sistema para miembros.
	Por ejemplo: La "Administración Cyclos" ha aceptado su factura de ...

	<li> <b> Identificación para canales externos: </b>
	Este es el nombre utilizado para los canales externos.
	Por ejemplo, al configurar el módulo SMS (que es un módulo externo), 
	se debe especificar un identificador en el archivo de configuración de SMS. 
	Este identificador se define en este campo.
	
	<li><b>URL raiz de la aplicación:</b>
	Esta dirección URL es utilizada por Cyclos cuando son enviados correos electrónicos a usuarios (por ejemplo, notificaciones) 
	que contienen un enlace/link a Cyclos.
	Por ejemplo en el caso de una confirmación de registro.<br>
	La URL será normalmente algo así como: www.sudominio.com/cyclos o solo www.sudominio.com.
	Tenga en cuenta que esta URL se puede sobrescribir en un nivel inferior (filtros de grupo y grupos). 
	Esto sería necesario en el caso de tener configuradas varias comunidades/instancias independientes dentro de una única instalación de Cyclos.
	En este caso, cada comunidad/instancia puede tener su propia URL de acceso.

	<li> <b> URL de la página contenedora global: </b>
	Este campo es necesario si usted desea tener acceso a Cyclos desde un sitio Web (como la demo en el sitio del proyecto Cyclos).<br>
	El sitio Web poseerá un Iframe o un frame set incluído en la página de acceso a Cyclos (login page).
	Si esto ocurre, es posible que usted desee personalizar el "cabezal" (Menú: Gestión de contenido> Archivos estáticos - top.jsp).
	<br>
	La URL deberá ser una dirección URL completa con http:// o https:// delante.<br>
	Tenga en cuenta que si usted coloca la solicitud de URL a la página normal, será direccionado directamente a la página contenedora.<br>
	Si las páginas contenedoras no funcionan correctamente, significa que usted no puede acceder a Cyclos con la url normal.<br>
	Si esto ocurre, usted siempre puede acceder a Cyclos agregando a la página original "do/login" después de la url.
	Por ejemplo: http://www.suDominio.org/cyclos/do/login. <br>
	Cyclos soporta múltiples comunidades en una misma instancia. <br>
	Cada comunidad puede acceder a través de su propia y personalizada página de acceso (login page), o desde su propio sitio Web.<br>
	Con el fin de habilitar esto, usted deberá definir una URL de la página de contenedora por cada grupo o filtro de grupo.<br>
	En este tipo de configuración la URL contenedora global, será la contenedora por defecto (retroceder)
	para los grupos que no poseen su propio sitio Web para acceder a Cyclos.<br>
	<br>
	Por más información, consulte la configuración del
	<a href="${pagePrefix}groups#group_details"><u>grupo</u></a> y
	<a href="${pagePrefix}groups#group_filter"><u>filtro de grupo</u></a>.
	<br>
</ul>

<b> Internacionalización </b>
<ul>
	<li> <b> Lenguaje: </b>
	Usted puede elegir entre los idiomas disponibles.

	<li> <b> Formato de número: </b>
	Seleccione un formato de presentación de los números en Cyclos (separador de miles y símbolo decimal).
	En la actualidad es soportado el formato de números americanos y europeos.

	<li> <b> Precisión de número: </b>
	Este parámetro representa el número de dígitos posibles luego de la coma (o punto decimal).
	Si se ingresa cero(0), la aplicación sólo funcionará con números enteros.
	Para la mayoría de los sistemas el valor establecido en dos(2).

	<li> <b> Alta precisión: </b>
	Este parámetro representa el número de dígitos luego de la coma (o punto decimal) sólo para los cargos (tasas). <br>
	Normalmente, este número sería el mismo que la "precisión de número", sin embargo, en algunos casos puede ser necesario
	disponer de una mayor precisión para la exactitud en los cálculos de los cargos de cuentas.

	<li> <b> Método de digitación de números decimales: </b>
	Con este ajuste usted puede definir si el separador decimal (por ejemplo: coma) se completa de forma
	automática, lo que significa que el usuario deberá sólo digitar los números
	(primera opción "derecha hacia izquierda"). <br>
	La segunda opción ("izquierda hacia derecha") significa que el usuario deberá digitar el separador decimal.

	<li> <b> Huso horario: </b>
	Aquí puede definir la zona horaria correspondiente.
	Esto sólo se debe establecer si el servidor está situado en una zona horaria diferente a la
	de los usuarios que están utilizando esta instancia.

	<li> <b> Formato de la fecha: </b>
	Seleccione un formato para las fechas de impresión en la pantalla.

	<li> <b> Formato de la hora: </b>
	Seleccione un formato para la hora. Esta es usada para mostrar la fecha/hora completa en las
	Transacciones, alertas, facturas y en los comentarios.
</ul>

<b> Límites </b>
<ul>
	<li> <b> Resultados de iterador máximo: </b>
	Número máximo de resultados (líneas) en impresión o "descarga" como archivo .csv.<br>
	Esta opción es configurada para evitar cargas pesadas en el servidor.

	<li> <b> Resultados de página máximo: </b>
	Número máximo de puntos (items) incluídos en una página de resultados de búsqueda.

	<li> <b> Resultados máximo Ajax: </b>
	Número máximo de opciones desplegadas en los campos autocompletables (como por ejemplo el nombre de miembro).

	<li> <b> Tamaño máximo de archivo de subida: </b>
	Tamaño máximo de archivo de subida (carga) para imágenes de perfil y de anuncios.

	<li> <b> Tamaño máximo de imágenes: </b>
	Tamaño máximo (ancho y alto) de las imágenes (por ejemplo: perfiles y anuncios).
	Si los tamaños son más grandes, Cyclos los reducirá automáticamente.

	<li> <b> Tamaño máximo de vista previa: </b>
	Tamaño máximo (ancho y alto) de la vista previa/miniatura de una imagen
	(al hacer click sobre la imagen de un anuncio o de un miembro).
	La vista en miniatura se mostrará con las mismas dimensiones que la imagen original.
	Por lo tanto, el tamaño puede ser menor si las dimensiones son de "retrato".

	<li> <b> Vencimiento de brokering: </b>
	Si este período se establece, un miembro registrado para un broker desaparece de su lista.
	Si su sistema funciona con
	<a href="${pagePrefix}brokering#commission"><u>comisiones de broker</u></a>,
	usted deberá asegurarse que la comisión no posea un plazo menor al período de expiración del broker.

	<li> <b> Eliminar mensajes de la papelera luego de: </b>
	Período máximo de los mensajes en la papelera de reciclaje.
	Pasada esta fecha, los mensajes serán removidos.

	<li> <b> Tiempo máximo para confirmación de correo electrónico para registro de miembro: </b>
	Si un miembro se registra externamente y la confirmación vía correo electrónico se encuentra habilitada
	(en la: Configuración del grupo> "Validación de correo electrónico"), el miembro
	deberá confirmar antes del vencimiento de este plazo.
</ul>

<b> Visualización de datos: </b>
<ul>
	<li> <b> Correo electrónico obligatorio para miembros: </b>
	Si esta opción es seleccionada, la dirección de correo electrónico será obligatoria
	(en el registro por la administración, en el registro público o por el miembro).</li>

	<li><b> Correo electrónico debe ser único:</b>
	Si esta opción es seleccionada, la dirección de correo electrónico deberá ser única.</li>

	<li> <b> Lista de resultado de miembro muestra: </b>
	Con esta opción, usted puede definir si los anuncios y la lista de los resultados de búsquedas,
	mostrarán el Código o el Nombre del miembro.</li>

	<li> <b> Formato de descripción de anuncio: </b>
	Aquí usted puede seleccionar el formato de la descripción (área de texto) de los anuncios.
	Puede ser normal (solo texto) o un editor del cuadro de texto (solo texto enriquecido).
	Existe también la posibilidad de habilitar ambas opciones, estableciendo una de ellas como predeterminada.</li>

	<li> <b> Formato de mensaje simple: </b>
	Aquí usted puede definir el tipo de editor para la utilización de mensajes normales entre miembros, o entre administradores y miembros.</li>

	<li> <b> Formato de mensaje de admin a grupos: </b>
	Aquí usted puede definir el tipo de editor para los mensajes a grupos (sólo administradores).</li>

	<li> <b> Formato de mensaje de broker a miembros registrados: </b>
	Aquí usted puede definir el tipo de editor para los mensajes de un broker a sus miembros.</li>

	<li> <b> Mostrar contadores en las categorías de anuncios:</b>
	Si esta opción es seleccionada, se mostrarán contadores (números) en forma de enlace junto a las categorías de anuncios
	(en las páginas de búsqueda de anuncios, en su navegación por categorías).
	<br>
	La razón por la que podría utilizar esta opción, es si usted posee una instancia con varios grupos/comunidades que funcionan de forma
	aislada, y por lo tanto, no pueden visualizarse unos a otros (ni los anuncios de los otros grupos).<br>
	Como los contadores son globales, representan el número de anuncios totales en el sistema (para la categoría específica, por supuesto).
	Esto podría ser confuso, debido a que los contadores mostrarían números superiores a la cantidad real de anuncios para el grupo/comunidad. <br>
	Por lo tanto, en el caso de existir varias instancias, se recomienda desactivar los contadores.</li>
</ul>

<b> Exportación CSV: </b>
<ul>
	<li> <b> Mostrar encabezado: </b>
	Si esta opción es seleccionada, en la primer fila del <a href="${pagePrefix}loans#csv"><u>archivo CSV</u></a>
	se mostrará el nombre de cada campo, en la primer línea de cada columna
	(por ejemplo, código de usuario, nombre, dirección, etc.).

	<li> <b> Comilla de cadena de caracteres: </b>
	Cuando esta opción es seleccionada, las "cadenas de caracteres"
	como campos de descripción y título, se colocarán entre comillas. <br>

	Nota: Tenga cuidado de que si define esta opción en "Ninguno", los campos cadena de caracteres
	que contienen el valor separador (coma, punto y coma), se romperán y se colocarán en la siguiente columna.
	Por lo tanto, es recomendable utilizar una comilla.

	<li> <b> Separador de valores: </b>
	Este es el caracter de separación para el archivo CSV.
	Es posible que deba especificar este separador al importar el archivo en una hoja de cálculo o en un programa procesador de texto.

	<li> <b> Corte de fila: </b>
	Este es el caracter de "fin de línea" utilizado.
	El valor por defecto Unix, es el estándar más utilizado.
	El tipo "DOS" puede ser utilizado para sistemas Windows.
</ul>

<b>SMS:</b>
<ul>
	<li><b>Habilitado:</b>
	Si esta opción es seleccionada, la opción SMS es habilitada, el sistema permitirá pagos, solicitudes de pago
	y consultar la información de la cuenta a través de mensajes SMS.
	Una vez que la opción de SMS es "habilitada", se mostrará en la configuración de grupo
	y en los ajustes de las notificaciones del canal. </li>

	<li><b>Canal para operaciones:</b>
	Aquí usted deberá seleccionar el canal SMS correspondiente (definido en el Menú: Configuración> Canales).</li>

	<li><b>Url para notificaciones:</b>
	El módulo de SMS en Cyclos, requiere un controlador SMS externo.
	La comunicación entre Cyclos y el controlador SMS pasará a través de la Web, por lo tanto usted deberá proporcionar una dirección URL Web. </li>

	<li><b>Campo de perfil que representa el teléfono móvil:</b>
	Aquí usted deberá indicar el campo del perfil de los miembros que identifica el teléfono celular.
</ul>

<b> Número de transacción: </b>
<ul>
	<li> <b> Habilitar: </b>
	Si esta opción es seleccionada (activada), cada transacción en el sistema generará un número único de transacción (identificador).
	El formato de dicho identificador puede ser definido en los siguientes campos:<br>
	<ul>
		<li> <b> Prefijo: </b>
		Prefijo del identificador (números o letras).

		<li> <b> Largo de identificador: </b>
		Longitud del identificador.

		<li> <b> Sufijo: </b>
		Sufijo del identificador (números o letras).</li>
	</ul>
	<br>
	<i>Por ejemplo</i>: El número de transacción de la primera operación en el sistema, con el
	prefijo de configuración = abc, longitud = 5, Sufijo xyz, sería --> "abc00001xyz"
</ul>

<b> Devolución de pagos: </b>
<br><br>
Devolución de pago significa que el pago es deshecho: desaparecerá del sistema como si nunca hubiera existido.
En el caso de que el pago haya generado otras operaciones (por ejemplo: cargos, tasas, préstamos, etc.), todas serán eliminadas del sistema.
<ul>
	<li><b>Período máximo para devolución:</b>
	Aquí usted puede definir el tiempo máximo que un administrador posee para realizar la devolución de un pago.</li>

	<li><b>Descripción de pago de devolución:</b>
	Aquí usted puede establecer la descripción correspondiente a la devolución de pago.</li>
</ul>

<b>Tareas programadas:</b>
<br><br>
Esta opción se utiliza normalmente para la ejecución de tareas habituales.
Con esta opción usted puede definir cuando las tareas programadas serán ejecutadas.<br>
Normalmente son utilizadas en el caso de que exista más de una instancia de Cyclos ejecutándose en el mismo sistema.
Cyclos posee tareas programadas que se ejecutan diariamente y de forma horaria.
<ul>
	<li> <b> Hora de las tareas programadas: </b> Aquí usted puede ajustar la hora (0-24) para las tareas que se ejecutan diariamente.
	Un ejemplo de una tarea diaria es la marca de verificación de vencimiento de Anuncios.

	<li> <b> Minuto de las tareas programadas: </b> Aquí usted puede ajustar los minutos (0-60) para
	las tareas que se ejecutan diariamente y cada hora.

	<li> <b> Reconstruir índices de búsqueda cada vez: </b> Con esta opción usted puede definir
	el tiempo y la frecuencia en los que Cyclos reconstruirá los índices.
	En Cyclos v3.5, los miembros y anuncios están indexados, lo que ayuda a realizar las búsquedas de forma más rápida.
	La indexación permite múltiples búsquedas por palabras clave, de forma más rápida, y sobre todos los campos
	(campos del perfil de usuario o anuncio). <br>
	Los índices pueden quedar corruptos en el tiempo, por lo que es recomendable volver a indexarlos frecuentemente.
	Sugerimos que se reconstruyan los índices de forma semanal, y a una hora tranquila (en la noche o en la mañana).<br>
	Dependiendo de la cantidad de anuncios y perfiles de miembros, esta acción puede tardar un tiempo considerable.
	El proceso se ejecuta en un "hilo", y no afecta el funcionamiento de Cyclos. <br>
	La re-indexación también se pueder realizar eliminando los índices manualmente en el servidor
	(sólo eliminar los índices del directorio WEB-INF) y reiniciar el servidor o instancia.
</ul>
<hr>

<A NAME="alerts"></A>
<h2> Configuración de alertas </h2>
En la configuración de alertas, usted puede definir los límites y parámetros de alertas correspondientes a la conducta de los miembros.
Para realizar cambios, haga click en el botón "Modificar";<br>
Para confirmar los cambios, haga click en el botón "Aceptar".<br>
<br>
Los siguientes parámetros de alertas pueden ser establecidos:
<ul>
	<li> <b> Nuevos miembros pendientes de ser activados: </b>
	Si esta opción es seleccionada, una alerta de cuenta se generará cuando un nuevo usuario se registre (en la página de acceso).

	<li> <b> Muy malas referencias dadas: </b>
	Cuando alguien ha dado más de un número "x" de referencias "malas", se generará una alerta.

	<li> <b> Muy malas referencias recibidas: </b>
	Cuando alguien recibe más de un número "x" de referencias "malas", se generará una alerta.

	<li> <b> Vencimiento de la factura: </b>
	Cuando alguien recibe una factura, pero no reacciona frente a ella (ni la rechaza ni la acepta),
	luego de un período de tiempo "x", se generará una alerta.

	<li> <b> Facturas rechazadas: </b>
	Cuando alguien rechaza más de una cantidad "x" de facturas, se generará una alerta.

	<li> <b> Intentos de acceso incorrectos: </b>
	Luego de una cantidad "x" de intentos fallidos de ingreso al sistema, se generará una alerta de sistema.
</ul>
<hr>

<A NAME="access"></A>
<h2> Configuración de acceso </h2>
Aquí usted puede definir los parámetros/ajustes relativos al acceso a Cyclos.<br>
Para realizar cambios, haga click en el botón "Modificar"; <br>
para confirmar los cambios, haga click en el botón "Aceptar".<br>
<br>
Las siguientes opciones se encuentran disponibles:
<ul>
	<li> <b> Teclado virtual para contraseña de acceso: </b>
	Cuando esta opción es seleccionada, a los usuarios (tanto administradores como miembros)
	se les presentará un teclado virtual en la página de acceso (login) al sistema.<br>
	La contraseña deberá ser escrita con el teclado virtual.<br>
	El teclado virtual evita la intervención de programas (software) malintencionados, que buscan capturar las contraseñas ingresadas.

	<li> <b> Teclado virtual para la contraseña de transacción: </b>
	Cuando esta opción es seleccionada, a los usuarios se les presentará un teclado virtual para el ingreso de la contraseña de transacción.<br>
	La contraseña de transacción deberá ser escrita con el teclado virtual.

	<li> <b> Contraseña de acceso numérica: </b>
	Si esta opción es seleccionada, los miembros sólo podrán poseer contraseñas de acceso (login) numéricas.<br>
	Esta opción podría ser necesaria si usted desea que la contraseña de acceso (Web)
	sea utilizada a la misma vez como PIN para otros canales (como por ejemplo: operaciones SMS o POSweb).<br>
	Esta configuración no es aplicable para Administradores.


	<li> <b> Permitir múltiples conexiones del mismo usuario: </b> Cuando se selecciona esta opción los usuarios pueden acceder más de una vez, y desde diferentes navegadores, al mismo tiempo.

	<li> <b> Permitir conexión de operador: </b>
	Si usted trabaja con <a href="${pagePrefix}operators"><u>operadores</u></a>,
	esta casilla de verificación debe ser comprobada con el fin de permitir el acceso de los mismos (operadores) al sistema.

	<li> <b> Tiempo máximo de inactividad de sesión de administradores: </b>
	Es el tiempo que un administrador puede permanecer inactivo en su sesión en Cyclos (la sesión se cerrará pasado este tiempo).

	<li> <b> Tiempo de inactividad de sesión del miembro: </b>
	Es el tiempo que un miembro puede permanecer inactivo en su sesión en Cyclos (la sesión se cerrará pasado este tiempo).

	<li> <b> Tiempo de inactividad de sesión de PosWeb: </b>
	Es el tiempo que un miembro u operador puede permanecer inactivo en su sesión PosWeb (la sesión se cerrará pasado este tiempo).

	<li> <b> Lista de acceso administradores: </b>
	Aquí usted puede colocar la dirección IP o los nombres de las máquinas de los usuarios que pueden acceder a la sección de administración.<br>
	Por favor, ponga cada nombre de host o IP en una nueva línea (Enter).<br>
	Si usted ingresa el texto "#Any host", cualquier host accederá a la página de administración
	(de todas formas el código de usuario y contraseña serán requeridos).
	<br>

	<li> <b> Generación de código de usuario: </b>
	<ul>
		<li> <b> Manual por miembro: </b>
		Para redes de comunidad, es común que los usuarios puedan elegir su propio código de usuario o "nick".<br>
		En este caso, la opción "manual por miembro" debe ser seleccionada. <br>
		Si esta opción es seleccionada, debajo usted podrá especificar
		la longitud mínima y máxima del código de usuario <b>("Largo de código de usuario en conexión manual")</b>.
		<br><br>
		<b> Expresión común para nombre de conexión: </b> En conjunto con el largo del código se puede definir
		una expresión regular para forzar el formato del código de usuario. <br>

		Cualquier caracter (puede o no coincidir con los finalizadores de línea) \d <br>
		Un dígito: [0-9] \D A <br>
		No dígito: [^0-9] \s <br>
		Un blanco: [\t\n \x0B\f\r] \ S A <br>
		No blanco: [^\s] \w <br>
		Un carácter de palabra: [a-zA-z_0-9] \W A <br>
		Carácter no-palabra: [^\w] <br>
		<br>
		http://java.sun.com/j2se/1.5.0/docs/api/java/util/regex/Pattern.htm <br>
		<br>
		Es posible especificar la longitud mínima y máxima del código de usuario,
		que puede ser introducido por un miembro o por un administrador.

		<li> <b> Número generado al azar (código de usuario): </b>
		Para redes de empresas, es común la utilización de códigos de usuario generados de forma automática y al azar.<br>
		Si esta opción es seleccionada, el formulario de registro de miembros no tendrá un campo para el ingreso correspondiente del código de usuario.
		Cuando el formulario de registro se envíe, un código de usuario (aleatorio) será creado automáticamente.
		Debajo de esta opción <b>("Largo de código de usuario generado")</b>, usted puede especificar la longitud del código a generar.</li>
	</ul>
	<br>
	<li><b> Caracteres posibles en la contraseña de transacción: </b>
	Aquí usted puede definir los caracteres que se utilizarán (en orden aleatorio) para la generación de la contraseña.
	(para la configuración de la contraseña de transacción, debe acceder a la
	<a href="${pagePrefix}groups#edit_member_group"><u>configuración de grupo</u></a>).

	<li><b> Contraseña para solicitar un pago externo: </b>
	Aquí usted puede definir la contraseña que se utilizará para el pago de servicios externos.
</ul>
<hr>

<A NAME="mail"></A>
<h2> Configuración de correo </h2>
En esta página usted puede configurar las preferencias de correo electónico del sistema:
<ul>
	<li><b>Dirección desde:</b> Aquí usted puede definir su "dirección de envío".</li>
	<li><b>Parámetros del servidor SMTP:</b> Aquí puede establecer los parámetros del servidor SMTP.</li>
	<ul>
	<li><b>Nombre del Host</b>.</li>
	<li><b>Puerto</b>.</li>
	<li><b>Código de usuario</b>.</li>
	<li><b>Contraseña</b>.</li>
	<li><b>Utilizar TLS:</b> Si su servidor de correo requiere TLS (Transport Layer Security: protocolo de seguridad de la capa de transporte),
	usted debe chequear esta opción.</li>
	</ul>
</ul>
<br>
Si desea realizar cambios, haga click en el botón "Modificar";<br>
Para confirmar los cambios, haga click en el botón "Aceptar".
<hr>

<A NAME="log"> </A>
<h2> Configuración del log </h2>
La configuración del log define dónde y cómo se escriben los archivos de registro (logs) en el sistema.<br>
Los archivos de registro de Cyclos no son escritos en la base de datos, pero si los archivos de registro en formato texto son almacenados en el servidor.
Por lo tanto es importante que estos parámetros sean manejados por (o en colaboración con) el administrador del servidor. <br>
<br>
Para realizar cambios, haga click en el botón "Modificar";<br>
Para confirmar los cambios haga click en el botón "Aceptar".
<br><br>
Las siguientes opciones son posibles:
<ul>
	<li> <b> Nivel de log de acción: </b>
	Este registro contendrá la información sobre cualquier acción realizada en Cyclos, con la
	información completa (trazabilidad) correspondiente a cada acción, como: la fecha, miembro, etc. <br>
	Las opciones son:
	<ul>
		<li> <b> Apagado: </b>
		No se utiliza el registro de acción.
		<li> <b> Sólo errores: </b>
		Sólo se registran acciones con errores.
		<li> <b> Trazado simple (sin parámetros): </b>
		Registro de todas las acciones, exitosas y con errores.
		<li> <b> Rastreo detallado (parámetros detallados): </b>
		Igual que la opción anterior, pero también se mostrarán los valores transmitidos (parámetros) y devueltos/retornados.
	</ul>
	<br>

	<li> <b> Ruta del archivo log de acciones: </b>
	Aquí usted puede especificar la ruta y el nombre del archivo de registro.
	Si más de una instancia está instalada en el servidor, es una buena práctica definir un directorio de registro para cada una de ellas.
	<ul>
		<li> "/" es el separador de ruta.
		<li> "% T" es el directorio de sistema temporal.
		<li> "% h" es el valor de la propiedad del sistema "user.home".
		<li> "% g" es el número generado para distinguir los registros rotativos
		<li> "% u" único número para la resolución de conflictos.
	</ul>
	<br>

	<li> <b> Generar log solamente si algún dato fue modificado: </b>
	Si esta opción es seleccionada, sólo las acciones que se traducen en cambios de base de datos se registrarán.
	Esto significa que todas las acciones de escritura/almacenamiento de datos serán registradas.
	Sin embargo las acciones tales como búsquedas o impresión no serán logueadas.
	Normalmente, es suficiente loguear los inicios de sesión en la base de datos.
	<br>
	<br>

		<li> <b> Nivel de log de servicios web: </b>
	<br>
	<br>

	<li> <b> Ruta de archivo de log de servicios web: </b>

	<br>
	<br>

	<li><b>Nivel de log de transacciones: </b>
	Este registro contendrá sólo las transacciones, con toda la información relacionada de
	miembro/sistema a miembro/sistema, cantidad, fecha, etc. <br>
	Las siguientes opciones estarán disponibles:
	<ul>
		<li> <b> Apagado: </b> El registro de transacciones no es utilizado.
		<li> <b> Normal: </b>
		Si esta opción es seleccionada, el registro contendrá todas las transacciones con su
		fecha, desde miembro/sistema para miembro/sistema, y su cantidad correspondiente.
		<li> <b> Detallado: </b>
		Esta opción ofrece la misma información que la "normal", adicionando la descripción y el
		tipo de transacción correspondiente.
	</ul>
	<br>

	<li> <b> Ruta de archivo de log de transacciones: </b>
		Aquí usted puede especificar la ruta y el nombre del archivo de registro, de la misma manera
		que el archivo correspondiente al "log de acciones" (véase la explicación más arriba).
		<br><br>
	<li> <b> Nivel del registro de los cargos de cuenta: </b>
		Este registro brinda información sobre los cargos de cuentas (tasas). <br>
		La sección de administración de Cyclos también posee con un
		<a href="${pagePrefix}account_management#account_fee_history"><u>historial de cargos de cuenta</u></a>.
		<br>
		La razón por la que los cargos de cuenta pueden ser incluídos en los registros, es poder tener un
		completo seguimiento de cualquier suceso dentro de Cyclos en los archivos de registro (externo a la base de datos).
		Además, el seguimiento de los cargos de cuenta es más amplio que el historial de cargos de cuenta en la sección de Administración. <br>
		Las siguientes opciones son posibles:
		<ul>
			<li> <b> Apagado: </b> No se lleva a cabo el registro.
			<li> <b> Solo errores: </b> Sólo se mostrarán los errores ocurridos.
			<li> <b> Cambio de estado y errores: </b> Estado de alertas y transacciones exitosas son registradas.
			<li> <b> Detalles: </b> Registra todas las transacciones.
		</ul>
		<br>

	<li> <b> Ruta del archivo de registro de los cargos de cuenta: </b>
		Aquí usted puede especificar la ruta y el nombre del archivo de registro, de la misma manera
		que el archivo correspondiente al "log de acciones" (véase la explicación más arriba).
		<br><br>
	<li> <b> Nivel de log de las tareas programadas: </b>
		El registro de las tareas programadas contiene el registro de los cargos de cuentas.<br>
		Información sobre los cargos de cuentas programados, también se pueden encontrar en la función de
		<a href="${pagePrefix}account_management#account_fee_overview"><u>cargos de cuenta</u></a>. <br>
		La razón por la que ellas también están incluídas en los registros, es para poder
		completar los registros de cualquier acción en Cyclos, independientemente de la base de datos.<br>
		Las siguientes opciones son posibles:
		<ul>
			<li> <b> Apagado: </b> No se lleva a cabo ningún registro.
			<li> <b> Solo errores: </b> Solo mostrará los errores ocurridos.
			<li> <b> Ejecución resumida: </b> Sólo la información de la tarea que se ha ejecutado con un "timestamp" (marca de tiempo).
			<li> <b> Ejecución detallada: </b> Información detallada sobre la tarea.
		</ul>
		<br>

		<li> <b> Ruta del archivo de log de las tareas programadas: </b>
		Aquí usted puede especificar la ruta y el nombre del archivo de registro, de la misma manera que
		el archivo correspondiente al "log de acciones" (véase la explicación más arriba).

		<li> <b> Cantidad máxima de archivos por log: </b>
		Aquí usted puede especificar la cantidad máxima de archivos por registro.<br>
		Cuando el máximo de tamaño de los ficheros de registro y de archivos es alcanzado,
		el registro más antiguo será eliminado y uno nuevo creado.
		Es una buena práctica para asegurarse, realizar una copia de seguridad de los ficheros antes de que su eliminación.

		<li> <b> Tamaño máximo del archivo: </b>
		Cuando el tamaño máximo del archivo es alcanzado, un nuevo archivo de registro se creará.<br>
		Por supuesto, cuanto más grande sea el registro realizado, más grande serán los archivo resultantes,
		y por lo tanto, mayor el tamaño máximo de archivo que usted deberá establecer.
</ul>
<hr>

<A NAME="channels"></A>
<h2> Canales </h2>
A Cyclos se puede acceder a través de diferentes medios de comunicación o "canales"
(por ejemplo, Internet, telefonía móvil, etc.)
<br><br>
Con el fin de facilitar la tarea de añadir nuevos canales en el futuro, todos los canales son enumerados dinámicamente.
Para un Cyclos "normal", no es necesario modificar la configuración de los canales. <br>
Los actuales canales disponibles son:
<ul>
	<li> <b> Acceso principal Web: </b>
	Este es el acceso principal a través de un navegador Web.
	Normalmente con una URL como: www.suDominio.org / cyclos

	<li> <b> Pagos Posweb: </b>
	El acceso POSWeb (punto de venta), como un consumidor en una tienda.<br>
	Utilizado por <a href="${pagePrefix}operators"><u>operadores</u></a> o directamente por los miembros.
	Puede ser accedido ingresando /posweb o /operador, después de la URL correspondiente.<br>
	Por ejemplo: www.suDominio.org / cyclos / Operador.

	<li> <b> Acceso WAP 1: </b> (Wireless Application protocol) Es normalmente utilizado por los teléfonos móviles
	más antiguos que no soportan WAP 2.
	El módulo se puede acceder ingresando /WAP después del dominio.

	<li> <b> Acceso WAP 2: </b>
	WAP 2 proporciona el acceso a la Web a través de teléfonos móviles.
	El módulo puede ser accedido ingresando /dominio después del dominio Cyclos.

	<li> <b> Pagos Webshop: </b>
	El canal de pagos Webshop (pagos online) permite realizar pagos desde software de comercio electrónico.
</ul>
Nuevos canales sólo pueden ser añadidos por medio de programación.<br>
Una vez realizada esta acción, se pueden añadir a la lista de canales en esta página y la relación con los grupos
a través de la <a href="${pagePrefix}groups#edit_member_group"><u>configuración de grupos</u></a> y
<a href="${pagePrefix}account_management#transaction_types"><u>tipos de transacciones</u></a>.
<hr>

<A NAME="channels_detail"></A>
<h3> Detalles de canal (Nuevo o Modificar) </h3>
Es poco probable que usted deba configurar canales en el sistema.<br>
Para WebPOS, POS y canales personalizados, usted puede definir la forma de identificación de los usuarios.<br>
Para cualquier otro canal, usted puede definir las credenciales de confirmación
(esta opción no se encuentra disponible para el acceso principal Web, porque en ese caso es definido en el grupo).
<hr>

<A NAME="web_services_clients"> </A>
<h2> Clientes de web services </h2>
Aquí usted puede definir qué programas externos pueden acceder a Cyclos vía servicios Web (webservices)
y qué servicios pueden ser accedidos.
<br>
Usted puede hacer click en el ícono de Edición <img border="0" src="${images}/edit.gif" width="16" height="16">,
para modificar un cliente de web services. <br>
<br>
Si desea agregar un nuevo cliente de web services, haga click en el botón "Aceptar" correspondiente a la etiqueta "Nuevo cliente de web services".
<hr>

<A NAME="web_services_clients_detail"> </A>
<h3> Insertar / Modificar clientes de web services </h3>
Aquí usted puede insertar un nuevo cliente de web services, o modificar uno existente.<br>
<br>
Para editar un cliente de web services existente, primero deberá hacer click en el botón "Modificar". <br>
Para finalizar, haga click en el botón "Aceptar" para confirmar los cambios realizados. <br>
Los campos disponibles son:
<ul>
	<li> <b> Nombre: </b> Aquí usted puede especificar el nombre del cliente de web service. Este campo es sólo utilizado internamente.

	<li> <b> Dirección de internet: </b>
	Aquí usted puede especificar la dirección IP o el nombre de dominio (sin http(s)://), 
	a la que se le permite el acceso a los servicios Web.<br>
	(Por ejemplo: para un nombre de host como 'http://www.mydomain.com', la dirección ingresada debe ser 'www.mydomain.com').<br>
	Considere que si usted busca conectarse con el servidor a través de
	un proveedor de servicios de Internet (ISP), la misma dirección IP
	es probablemente utilizada para otros sitios Web (utilizando hostheaders).<br>
	Esto significa que todos estos sitios tendrán acceso a la instancia Web.<br>
	Muchas veces la dirección IP que resuelve un nombre de dominio de sitio Web
	es diferente a la dirección IP utilizada por el sitio Web para conectarse (a Cyclos).
	En este caso usted deberá consultar con su proveedor qué IP (rango) es utilizada para realizar
	conexiones externas. También es posible especificar un rango de direcciones IP (ej.: 77.88.45.0-255).<br>
	Tenga en cuenta que especificando un rango podría significar un agujero de seguridad.<br>
	En caso de accesos menos críticos como visualizar anuncios, esto podría no ser un problema, pero
	para acciones más serias como realizar pagos o visualizar datos de miembros, es preferible
	sólo acceder a través de una única dirección IP.
	
	<li> <b> Canal: </b>
	Aquí usted puede seleccionar el canal utilizado por el cliente de web service.

	<li> <b> Restringido al miembro / Nombre: </b>
	Aquí puede definir si el servicio Web (web service) se limita a un determinado miembro.<br>
	Puede indicar su Código de miembro o su Nombre. <br>
	El tipo de acceso dependerá de los permisos (ver más abajo).

	<li> <b> Usuario HTTP: </b>
	En esta opción (opcional), usted puede especificar un código de usuario.
	Cada solicitud HTTP presentada por el cliente web, deberá ser autenticada con este código de usuario y contraseña.

	<li> <b> Contraseña HTTP: </b>
	En esta opción usted puede especificar una contraseña para el usuario configurado anteriormente.

	<li> <b> Permisos: </b>
	<ul>
		<li> <b> Búsqueda de anuncios: </b>
		Permite la búsqueda de anuncios (normalmente dentro de un sitio web).
		Si el web service está restringido por miembro, el servicio sólo podrá recuperar los anuncios que el miembro (grupo) pueda visualizar.

		<li> <b> Búsqueda de miembros: </b>
		Permite la búsqueda de miembros (normalmente dentro de un sitio web).
		Si el web service está restringido por miembro, el servicio sólo podrá recuperar listas de miembros y los perfiles,
		que el miembro (grupo) pueda visualizar.

		<li> <b> Pagos para tienda virtual: </b>
		Permite recibir pagos de usuarios Cyclos a través de la tienda Web (webshop).
		Los pagos para tienda online (webshop) autentican al miembro a través del sistema de entrada.<br>
		Esto se explica en el sitio wiki de Cyclos> Web services
		(<a href="http://project.cyclos.org/wiki/index.php?title=Web_services"><u>http://project.cyclos.org/wiki/index.php?title=Web_services</u></a>).

		<li> <b> Realizar pagos: </b>
		Esta opción permite realizar pagos Cyclos.
		Si el servicio Web (web service) está restringido por miembro,
		este miembro será el destino (receptor) y el pago exigirá el
		<a href="${pagePrefix}passwords#pin"><u>PIN</u></a> (credencial) del miembro emisor/pagador.

		<li> <b> Acceder a detalles de cuentas: </b>
		Permite la búsqueda de todos los detalles de las cuentas y transacciones.<br>
		Si el servicio Web (web service) está restringido por miembro, el servicio sólo podrá recuperar las transacciones del mismo.

		<li> <b> Enviar mensajes SMS: </b> Cuando se selecciona esta opción un servicio web puede instruir a Cyclos para enviar un mensaje SMS.

		<li> <b> Obtener mensajes informativos: </b> Esta opción permite el acceso a los <a href="${pagePrefix}content_management#infotexts"> <u> textos informativos </u> </a>
	</ul>
</ul>
<hr>

<!-- TRADUCIR -->
<A NAME="search_indexes"></A>
<h2>Administrar índices</h2>
Los índices son utilizados para facilitar la búsqueda rápida de usuarios, anuncios y registros de miembros.<br>
Los índices permiten la búsqueda por una palabra clave o por combinación de palabras clave. <br>
Los índices no se almacenan en la base de datos, pero si en archivos separados en el servidor. <br>
Si no se encuentran índices en la puesta en marcha (startup) de Cyclos, el proceso de inicialización los creará. <br>
Con el paso del tiempo (y mayor cantidad de datos) es posible que usted desee optimizar el índice. <br>
La opción de "reconstruir", volverá a crear el índice.
Esta es una opción a la cual se debe recurrir sólo en caso de problemas.
Usted puede reconstruir y optimizar índices, según el tipo de índice. <br>
Si es necesario, usted puede reconstruir todos los índices de una sola vez, seleccionando la opción "Reconstruir todo".
<hr>

<A NAME="online_state"></A>
<h2>Disponibilidad del sistema</h2>
En algunos casos es posible que usted desee evitar que los usuarios inicien sesión (accedan al sistema),
con el fin de realizar algún mantenimiento o configuración dentro de la sección de administración.<br>
Con el fin de evitar tener que cerrar Cyclos y mostrar una página de error,
usted puede desconectar a todos los usuarios conectados y no permitirles ingresar temporalmente al sistema a través de esta opción
(botón Aceptar correspondiente a la etiqueta "Establecer el sistema fuera de servicio").<br>
<br>
Sólo los administradores con permisos podrán conectarse (loguearse), configurar y establecer el sistema en línea nuevamente.
<br>
(Los permisos se pueden encontrar en: Menú: Usuarios & Grupos> Permisos de grupo> Tareas administrativas> Establecer disponibilidad del sistema)
<hr>

<A NAME="import_export"></A>
<h2> Importar / Exportar </h2>
Con esta función usted puede exportar e importar las configuraciones (ajustes) establecidas,
con el objetivo de compartirlas entre diferentes instancias de Cyclos. <br>
<br>
Todos las configuraciones pueden ser exportadas e importadas, excepto la configuración de canales y web services
(ya que estos suelen ser únicos para cada instancia). <br>
<br>
La <i>Exportación</i> genera un archivo (legible) settings.xml. <br>
La <i>Importación</i> aplica la configuración del archivo importado.<br>
<ul>
<li><b>Tipos:</b>
Permite seleccionar los tipos de configuraciones a importar/exportar.
	<ul>
		<li>Configuración de acceso</li>
		<li>Configuración de alertas</li>
		<li>Configuración local</li>
		<li>Configuración de logs</li>
		<li>Configuración de correo electrónico</li>
	</ul>
	<br>
<li><b>Acción:</b>
Permite elegir la acción a realizar.
	<ul>
		<li>Exportar</li>
		<li>Importar</li>
	</ul>
	<br>
</li>
<li><b>Archivo:</b>
Sólo en el caso de la importación, usted deberá seleccionar el archivo a importar, a través del botón "Examinar".</li>
</ul>

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