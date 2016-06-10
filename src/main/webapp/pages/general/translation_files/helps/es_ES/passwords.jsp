<div style="page-break-after: always;">
<a name="passwords_top"></a>
<br><br>
Cyclos posee una contraseña de acceso (login) al sistema, y también, puede ser configurado para utilizar una
contraseña especial para realizar transacciones (contraseña de transacción).

<i> ¿Dónde las encuentro? </i><br>
Usted puede cambiar su contraseña de inicio de sesión (acceso) a través del "Menú: Personal> Cambiar contraseña". <br>
La contraseña de transacción deberá ser solicitada (su habilitación) a la Administración, 
y es generada por el usuario; si lo desea puede ver la correspondiente 
<a href="#transaction_password"><u>sección de ayuda</u></a>.
<br><br>
<span class="broker admin">
La contraseña de un miembro se puede cambiar a través del perfil del miembro, 
en la sección "Acceso> Administrar contraseñas".
</span>
<hr>

<a NAME="change"></a>
<span class="member">
<h3> Cambiar mi contraseña de acceso al sistema </h3>
Para cambiar su contraseña de acceso (login) al sistema: 
<ol>
	<li>Ingrese su Contraseña actual.</li>
	<li>Ingrese su Nueva contraseña dos veces.</li> 
	<li>Haga click en el botón "Aceptar" para su confirmación.</li>
</ol>
<br>
Nota: En el caso de que su contraseña haya vencido, sólo deberá ingresar la nueva contraseña y su confirmación.<br>
<br>  
Es posible que las políticas establecidas para el manejo de contraseñas, prohiban la activación y 
utilización de contraseñas sencillas (u obvias), y/o el uso de contraseñas utilizadas en el pasado (repetidas).<br>
En el caso de que su "Nueva contraseña" no cumpla con los requisitos definidos en la política, 
se le presentará un mensaje de error explicativo.<br>
Dicho mensaje le informará acerca de la política, y le permitirá volver a definir la nueva contraseña de acceso.<br>
</span>

<span class="broker admin">
<h3> Cambiar contraseña de acceso al sistema </h3>
Para cambiar la contraseña de acceso (login) al sistema del miembro: 
<ol>
	<li>Ingrese la Nueva contraseña.</li> 
	<li>Confirme la nueva contraseña.</li>
	<li>Haga click en el botón "Aceptar" para efectuar el cambio.</li>
</ol>
<br>
Si la opción "Forzar cambio de contraseña en el próximo acceso" es seleccionada, 
el miembro deberá obligatoriamente cambiar la contraseña en su próximo inicio de sesión (login).
<br>  
Es posible que las políticas establecidas para el manejo de contraseñas, prohiban la activación y 
utilización de contraseñas sencillas (u obvias), y/o el uso de contraseñas utilizadas en el pasado (repetidas).<br>
En el caso de que la "Nueva contraseña" no cumpla con los requisitos definidos en la política, se le presentará un mensaje de error explicativo.<br>
Dicho mensaje le informará acerca de la política, y le permitirá volver a definir la nueva contraseña de acceso.<br>
<br>
Si el permiso de grupo de miembro "Enviar contraseña generada por correo electrónico" es habilitado y usted posee el permiso para regenerar la 
contraseña de acceso a sistema, tendrá la opción extra de regenerar y enviar automáticamente por correo electrónico la contraseña al miembro, 
presionando el botón "Regenerar contraseña y enviar por correo electrónico".<br>
</span>
<hr>

<a name="transaction_password"></a>
<h2> Contraseña de transacción </h2>
La contraseña de transacción es solicitada y exigida por el sistema cada vez que un usuario desea realizar un Pago. <br>
<i>(adicionalmente y dependiendo de su configuración, la contraseña de transacción puede ser utilizada en otras 
actividades críticas del sistema, como por ejemplo: para realizar un cambio de <a href="#pin"><u>PIN</u></a>)</i>

<span class="admin">
El sistema de acceso puede ajustarse en el "Menú: Configuración> Configuración de acceso".
La operación puede ser escribiendo directamente la contraseña en el campo correspondiente
del formulario o a través de un teclado virtual. 
También se puede elegir el formato que tendrá tanto la contraseña de acceso, como la contraseña de transacción.
<br><br> La contraseña de transacción se gestiona en la página de administración de usuarios
("Perfil> Acceso> Administrar contraseñas"). Esta opción sólo está disponible si la
contraseña de transacción ha sido habilitada para el grupo (permisos de grupos).
</span>

<a NAME="transaction_password_generation"></a>
<h3> Contraseña de transacción </h3>
Aquí usted puede generar su <a href="#transaction_password"><u>contraseña de transacción</u></a>.<br>
Luego de hacer click en el botón "Generar contraseña de transacción", será mostrada en pantalla su contraseña de transacción. <br>
<br>
<b>Importante:</b> Asegúrese recordar y guardar su contraseña de transacción en un lugar seguro, 
pues NO será posible visualizarla nuevamente. <br>
En caso de olvido o extravío, usted deberá solicitar al Administrador 
que se le habilite la generación de una nueva contraseña de transacción.

<br>
<span class="admin">
<a NAME="manage_transaction_password"></a>
<h3> Administrar contraseña de transacción </h3>
Cuando la <a href="#transaction_password"><u>contraseña de transacción</u></a> está habilitada, 
puede poseer cuatro estados diferentes:
<ul>
	<li> <b> No generada: </b> La contraseña nunca ha sido utilizada/generada.
	<li> <b> Pendiente: </b> La contraseña está pendiente de ser generada por el miembro.
	<li> <b> Activa: </b> La contraseña de transacción ha sido generada por el miembro.
	<li> <b> Bloqueada: </b> La contraseña de transacción ha sido bloqueada por un administrador.
</ul>
Un administrador (con los permisos adecuados) puede: 
<ul>
	<li><b>Habilitar generar contraseña de transacción</b> (botón Aceptar):
	La contraseña de transacción quedará en el estado de "Pendiente" y el miembro podrá generarla (activarla) en su próximo acceso al sistema. </li>
	<li><b>Bloquear contraseña de transacción</b> (botón Aceptar): 
	La contraseña de transacción es bloqueada, el miembro no podrá realizar transacciones que la requieran y no podrá generar una nueva 
	(hasta que un administrador la restablezca/habilite).</li>
</ul>
</span>
<hr>

<a name="pin"></a>
<h2> PIN </h2>
Un PIN es una contraseña utilizada para la realización de algunos tipos de pagos externos, como: 
POS (Punto de Venta), pagos a través de SMS, Paypal u otros. Es una clave que contiene sólo números.<br><br>


<span class="admin">
Para habilitar la contraseña PIN, debe hacer lo siguiente:
<ul>
	<li> <b> Canal: </b> El PIN debe estar habilitado en el <a href="${pagePrefix}settings#channels_detail"><u>
	canal </u></a> ("Menú: Configuración> Canales". Para Modificar uno, haga click en el ícono correspondiente).
	<li> <b> <a href="${pagePrefix}groups#edit_member_group"><u>
	Grupo de opciones </u></a>: </b> En la sección "Configuración de acceso" el tamaño de la clave debe ser establecida.
	<li> <b> <a href="${pagePrefix}account_management#transaction_types"><u>
	Tipo de transacción </u></a>: </b> En el tipo de operación adecuado, el canal debe estar activado.
</ul>
</span>
Al PIN y al <a href="${pagePrefix}settings#channels"><u>canal</u></a> de acceso se puede acceder a través del
<span class="admin">
<a href="${pagePrefix}profiles"><u> perfil </u></a>> del miembro, en su Acceso externo.
</span>
<span class="member">
"Menú: Personal> Acceso externo".
</span>

<br><br>
<a NAME="change_pin"></a>
<h3> Cambiar mi PIN </h3>
El <a href="#pin"><u>PIN</u></a> es utilizado para la realización de algunos tipos de pagos externos como: 
POS (Punto de Venta), pagos a través de SMS, Paypal u otros. El PIN es numérico (no se permiten letras).<br> 
<br>
Para cambiar su PIN, usted debe ingresar primero su contraseña de acceso (inicio de sesión) 
o su <a href="#transaction_password"><u>contraseña de transacción</u></a>, dependiendo de la configuración establecida para su organización.<br>
 El PIN se debe ingresar dos(2) veces y ser confirmado con el botón de "Aceptar".
<hr class="help">


<a NAME="select_channels"></a>
<h2> Canales </h2>
<span class="admin"> En esta ventana, se muestran los posibles 
<a href="${pagePrefix}settings#channels"><u> canales </u></a>. </span>

<span class="member"> Un canal es una vía, un medio de comunicación para acceder a
Cyclos, por ejemplo en la Web a través de un navegador (browser) o un teléfono móvil. </span>
<br>
Pueden no estar disponibles todos los canales existentes, dependerá de la configuración de su organización.<br>

<h3> Cambiar mis accesos a canales </h3>
Usted puede seleccionar los canales que desea utilizar chequeando su respectiva casilla de verificación.
<ul>
	<li> <b> Pagos POSweb: </b> Pagos en un punto de venta (pagos del consumidor en una tienda/negocio).
	<li> <b> Acceso WAP 1: </b> Permite acceder a los teléfonos móviles más antiguos,
	WAP 1 (Wireless Application Protocol 1). 
	<li> <b> Acceso WAP 2: </b> Permite acceder a los teléfonos móviles más modernos,
	WAP 2 (Wireless Application Protocol 2). 
	<li> <b> SMS: </b> Permite operar a través de mensajes SMS. 
	<li> <b> Pagos Webshop: </b> Permite pagos en sitios del exterior (comercio electrónico). </u><br>
</li>
</ul>
No olvide hacer click en el botón "Aceptar" luego de haber elegido sus opciones, de lo contrario los cambios no serán guardados.
<br>
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