<div style="page-break-after: always;">
<a name="payments_top"></a>
<br><br>

Un miembro puede realizar un pago a otro miembro o a una cuenta del sistema (comunidad, organización, etc.). <br>
También puede realizar transferencias entre sus propias cuentas, si posee más de una cuenta con la misma moneda.<br>

Los pagos también pueden ser programados (agendados) para fechas futuras.<br> 
Todos los pagos cuentan con un botón de impresión, para imprimir su recibo correspondiente.


<span class="admin">Los pagos también se pueden 
<a href="#charge_back"><u>deshacer</u></a>, bajo ciertas condiciones. </span>

<br><br> <i> ¿Dónde los encuentro? </i> <br>
<br><br><span class="member"> Los pagos pueden ser iniciados de tres (3) diferentes formas:<br>
<br>
- Desde el menú principal:
	<ul>
		<li><b> Pagos a los miembros: </b> "Menú: Cuenta> Pago a miembro" (1);
		<br>
		<li><b> Pagos al sistema: </b> "Menú: Cuenta> Pago al sistema" (2);</li>
	</ul>	
- Y desde el perfil de un miembro:
Los pagos a otros miembros también pueden ser iniciados 
desde el <a href="${pagePrefix}profiles"><u>Perfil</u></a> del miembro receptor, 
a través de la acción (botón) <b>Realizar pago</b> (3).</li>
<br>
<br>
</span> 

<span class="broker"> Un broker puede iniciar los pagos de sus miembros a través de su 
<a href="${pagePrefix}profiles"><u>perfil</u></a>. 
Esto incluye los pagos a otros miembros, y los pagos a cuentas del sistema.
<br><br> Un broker también puede autorizar 
<u><a href="#authorized"></u></a> los pagos de
miembros, accediendo a través del "Menú: Brokering> Pagos a autorizar "y" Menú: Brokering> Historial de autorizaciones". <br>

</span> <span class="admin"> Los Pagos pueden ser iniciados desde diferentes lugares. 
La disponibilidad de las opciones mencionadas, por supuesto, dependerá de la configuración de su organización 
y de los permisos establecidos para los diferentes grupos:

<ul>
	<li> <b> Perfil: </b> Desde el <a href="${pagePrefix}profiles"><u>perfil</u></a> de los miembros,
	usted puede realizar pagos a otros miembros, así como los pagos al sistema.
	<li> <b> Pagos al sistema: </b> Se puede iniciar desde el "Menú: Cuentas> Pago al sistema". 
	Se trata de transferencias desde una cuenta de sistema a otra cuenta del sistema.
	<li> <b> Pagos a miembros: </b> Se puede iniciar desde el "Menú: Cuentas> Pago a miembro". 
	Se trata de pagos desde una cuenta de sistema a una cuenta de miembro.
</ul>

Además, existen diversos tipos de pagos especiales, y la mayoría de estos pueden 
ser accedidos a través del menú principal:
<ul>
	<li> <b> <a href="#authorized"><u>Autorizaciones</u></a>: </b> Se puede acceder
	a través del "Menú: Cuentas> Pagos a autorizar" y 
	"Menú: Cuentas> Búsqueda de autorizaciones".
	
	<li> <b> <a href="#scheduled"><u>Pagos agendados</u></a>: </b> Se puede acceder
	a través del "Menú: Cuentas> Pagos agendados".
	
	<li> <b> Pagos de préstamo: </b> Se puede acceder a través del "Menú: Cuentas> Pago de préstamo; 
	este tema es tratado en el <a href="${pagePrefix}loans"><u>archivo de ayuda sobre préstamos</u></a>.
</ul>
</span> 
Tenga en cuenta que, además de realizar los pagos directamente, también puede pagar 
respondiendo (aceptando) a una <a href="${pagePrefix}invoices"><u>Factura</u></a>.

<span class="admin">
<br><br><i> ¿Cómo hacerlos funcionar?</i><br>
Lo más importante es que, por cada pago debe existir un tipo de pago. 
Si no es definido un tipo de pago, el pago no podrá tener lugar.

Usted puede administrar los tipos de transacción, a través de gestión de las cuentas desde las que se paga. 
Para ello, debe ir al "Menú: Cuentas> Administrar cuentas", y seleccionar el tipo de cuenta emisora del pago.
Allí tendrá una 
<a href="${pagePrefix}account_management#transaction_type_search"><u>visión general</u></a> 
de todos los tipos de transacciones existentes para esta cuenta, 
lo que le permitirá también añadir un nuevo tipo (si posee los permisos). <br>
Tan pronto como exista un tipo de pago, será necesario establecer los permisos para su uso en los diversos grupos.
<ul>
	<li> Los administradores pueden poseer 
	<a href="${pagePrefix}groups#manage_group_permissions_admin_system"><u>permisos</u></a> 
	para realizar los pagos al sistema: el bloque "Pagos a sistema" contiene varios ajustes.
	
	<li> Los administradores también pueden poseer 
	<a href="${pagePrefix}groups#manage_group_permissions_admin_member"><u>permisos</u></a> 
	para realizar los pagos a miembros: el bloque "Pagos a miembro" contiene varios ajustes.
	
	<li> Para los miembros, también deberá definir los 
	<a href="${pagePrefix}groups#manage_group_permissions_member"><u>permisos</u></a> 
	para realizar pagos, esto se hace a través de varios ajustes en el bloque "Pagos". 
	Este bloque también está disponible para los brokers.
	
	<li> Los brokers pueden poseer <a href="${pagePrefix}groups#manage_group_permissions_broker"><u>permisos</u></a> 
	para realizar los pagos de los miembros, bloque "Pagos a miembros".
	
	<li> Para pagos autorizados y agendados, existe para cada grupo (miembros, brokers,
	administradores) un ajuste en el bloque "Cuenta" que permite a estos, ver los pagos autorizados y/o programados.
</ul>

</span>
<hr>
<a name="payments"></a>
<br><br>
<h3> Realizar pagos </h3>
Las formas existentes de efectuar pagos en el sistema por lo general poseen algunos elementos comunes. <br>
En esta breve introducción, se muestran los elementos comunes que pueden aparecer en su ventana de pago. <br>
<br>
En la mayoría de los casos, usted debe completar la <i>Cantidad (monto), Descripción,</i> y hacer click en el botón "Aceptar".<br>
Sin embargo, existen casos en los que deberá completar adicionalmente otros campos.<br>
Tenga en cuenta que es importante seleccionar y completar, todos los campos y las opciones en el correcto orden, 
es decir, de arriba hacia abajo:
<br><br>
<ul>
	<li> <b> Código de miembro / Nombre de miembro: </b> Si el pago es a otro miembro, y éste no se deduce del contexto, 
	usted deberá ingresar, ya sea el Código o el Nombre (campos auto-completables) del miembro al que le desea efectuar el pago. 
	
	<li> <b> Cantidad: </b> Introduzca el monto justo del pago.
	
	<li> <b> Moneda: </b> Este campo aparece a continuación del campo correspondiente al monto (cantidad). <br>
	Sólo es visible si existe en el sistema más de una moneda.
	Esto dependerá de la configuración local de su organización.
	
	<li> <b> Tipo de transacción: </b> Puede ser que se encuentren disponibles más de un tipo de transacción.
	En ese caso, usted deberá elegir el tipo de transacción a utilizar, de la correspondiente lista desplegable.
	
	<li class="admin"> 
	<b> Pago en el pasado: </b> Un Administrador puede elegir realizar el pago en el pasado. 
	Esto se realiza mayoritariamente para ajustes o rendición de cuentas, y sólo debe utilizarse en casos excepcionales. 
	Esta opción debe estar habilitada en los 
	<a href="${pagePrefix}groups#manage_group_permissions_admin_member"><u>permisos de administración de miembros</u></a>.
	
	<li> <b> Agendado: </b> Si son permitidos los pagos agendados para este tipo de transacción, 
	puede hacer que el pago sea agendado/programado (automáticamente) para una
	fecha futura, o en varios pagos periódicos (cuotas) en fechas futuras. <br>
	Por más información, consulte la ayuda sobre <a href="#pay_scheduled"><u>pagos agendados</u></a>.
</ul>
<br><br>
Luego de Aceptar el pago, se le solicitará una confirmación.<br> 
El monto será transferido directamente desde su cuenta a la cuenta del receptor.<br> 
La transacción aparecerá en la historia de la cuenta de ambas partes; para el emisor (pagador)
en rojo con signo negativo (-), y para el receptor en color azul con signo positivo (+).
<hr class="help">

<a name="transaction_confirm"></a>
<h3> Confirmación de transacción </h3>
En esta ventana se le solicitará que confirme el pago que usted acaba de ingresar.<br>
Verifique la información ingresada, y si todo está correcto, haga click en el botón "Aceptar" para confirmar el pago.<br>
<br>
Si cometió un error, puede retornar a la ventana anterior, haciendo click en el botón "Atrás".
<hr class="help">


<A NAME="to_member"></A>
<h3> Pago a miembro </h3>
Esta ventana le permite realizar un pago a un miembro. 
En la mayoría de los casos, usted deberá:
<ol>
	<li> Indicar el Miembro (Código o Nombre) al que desea realizarle el pago.
	<li> Indicar el importe (Cantidad) y moneda del pago. 
	<li> Completar la Descripción.
	<li> Hacer click en el botón "Aceptar".
</ol>
<a href="#payments"><u>Haga click aquí</u></a> si desea visualizar una descripción general de las ventanas de pago.
<hr class="help">

<A NAME="to_system"></A>
<h3 class="admin"> Transferencia entre cuentas del sistema </h3>
<h3 class="member"> Pago al sistema </h3>
<span class="admin"> En esta ventana se pueden realizar pagos entre cuentas del sistema.
<br><br>
</span>

<span class="member"> En esta ventana usted puede efectuar un pago a una de las cuentas del sistema. 
<br>
</span>
<a href="#payments"><u>Haga click aquí</u></a> para obtener una descripción general de las ventanas de pago.
<hr class="help">

<A NAME="as_member_to_system"></A>
<span class="broker admin">
<h3> Pago de miembro al sistema </h3>
En esta ventana usted puede efectuar un pago desde la cuenta de un miembro (en nombre y como si fuera el miembro) a una cuenta del sistema.
<br><br> <a href="#payments"><u>Haga click aquí</u></a> para obtener una descripción general de las ventanas de pago.
<hr class="help">
</span>

<span class="broker admin"> <A NAME="as_member_to_member"></A>
<h3> Pago de miembro a miembro </h3>
En esta ventana usted puede efectuar un pago desde la cuenta de un miembro (en nombre y como si fuera el miembro) a la cuenta de otro miembro.
<br><br> <a href="#payments"><u>Haga click aquí</u></a> para obtener una descripción general de las ventanas de pago.
<hr class="help">
</span>

<A NAME="member_self_payments"></A>
<h3 class='member'> Transferencias entre mis cuentas </h3>
<h3 class='admin'> Transferencias entre cuentas de miembro (por administrador)</h3>
<span class="admin"> Es posible que un Administrador (con los permisos correspondientes)
pueda efecutar un auto-pago, como si fuera un miembro. </span>

Un auto-pago permite realizar una transferencia de una cuenta a otra cuenta del mismo propietario (miembro).<br> 
Un auto-pago funciona de la misma forma que un pago a otro miembro.
<br><br>
<a href="#payments"><u>Haga click aquí</u></a> para obtener una descripción general de las ventanas de pago.
<br><br>
<hr>
<a name="scheduled"></a>
<h2> Pagos agendados </h2>
La función de pagos agendados le permite al miembro efectuar pagos programados (agendados) en el futuro a otras cuentas.<br>
Estos pueden ser constituídos por un único pago, o por varios pagos (cuotas) periódicos (por ejemplo, mensualmente). <br>
Los pagos se efectuarán automáticamente en la fecha especificada.<br>
<br>
Los pagos agendados también pueden combinarse con las facturas. <br>
Un miembro que envía un factura a otro, puede (si ambos poseen los permisos) especificar si 
la factura será paga de forma directa o programada (pagos agendados). <br>
Una vez que el miembro acepta recibir la factura, los pagos correspondientes (cuotas) aparecerán en su lista de pagos agendados. 


<span class="admin"> 
Es posible (en la configuración del sistema) permitir que cualquier tipo de pago sea agendado. <br>
Para permitir los pagos agendados, usted deberá hacer lo siguiente:
<ol>
	<li> <b> Permisos: </b> 
	En primer lugar, es necesario configurar los <a href="${pagePrefix}groups#manage_group_permissions_member"><u>permisos</u></a> 
	para los grupos de miembros correspondientes. 
	Si desea permitir a los <a href="${pagePrefix}brokering"><u>brokers</u></a> y/o administradores
	realizar pagos agendados en nombre de los miembros, deberá revisar los permisos de estos grupos también.
	
	<li> <b> Configuración de grupo: </b> Existe una 
	<a href="${pagePrefix}groups#edit_member_group"><u>configuración especial de grupo</u></a>
	("Menú: Usuarios & Grupos> Administrar grupos" y haga click en el ícono de Edición 
	<img border="0" src="${images}/edit.gif" width="16" height="16"> del grupo de miembro deseado) para los pagos agendados. 
	
	<li> <b> Tipo de transacción: </b> En el 
	<a href="${pagePrefix}account_management#transaction_types"><u>tipo de transacción</u></a>, 
	la programación de pagos debe estar activada. En la ventana de 
	<a href="${pagePrefix}account_management#transaction_type_details"><u>propiedades del tipo de transacción</u></a> 
	se encuentra la opción "Permitir pagos agendados". <br>
	
	<b> Nota: </b> Para algunos tipos de transacción, la programación de pagos no estará disponible. <br>
	Esto puede suceder con tipos de transacciones de miembro a sistema, y de auto-pago.
</ol>

Siguiendo estas definiciones los pagos agendados deberían estar listos para trabajar.<br>
En este caso, en cada una de las <a href="#payments"><u>ventanas de pago</u></a> 
se mostrará un campo desplegable para la correspondiente "programación de pagos", cuando sea pertinente.
<br><br>
Los pagos agendados pueden ser buscados a través del "Menú: Cuentas> Pagos agendados".
</span>

<span class="member"> 
Sus pagos agendados se pueden buscar a través del "Menú: Cuenta> Pagos agendados". </span>
<hr class="help">

<a name="pay_scheduled"> </a>
<h3> Efectuar un pago agendado </h3>
Esta ayuda trata sobre los <a href="#scheduled"><u>pagos agendados</u></a>, 
y describe los campos especiales con los que cuenta la ventana de <a href="#payments"><u>pago</u></a>.
<br>
El menú desplegable "Agendado" posee tres(3) posibles valores:
<ul>
	<li> <b> No agendar (pagar inmediatamente): </b> Elija esta opción si NO desea utilizar pagos agendados. El pago se realizará de forma inmediata.
	
	<li> <b> Agendado para una fecha futura: </b> Si selecciona esta opción, el pago se realizará en la fecha que usted especifique. 
	La fecha se debe especificar en el campo "Agendado para", el cual debe aparecer al seleccionar esta opción. 
	Si lo desea, puede elegir la fecha a través del ícono del calendario <img border ="0" src ="${images}/calendar.gif" width="16" height="16">.
	
	<li> <b> Agendado para pagos en cuotas (pago agendado múltiple): </b> Esta es la forma más sofisticada que pueden adoptar 
	los pagos agendados. Puede dividir un pago único en múltiples sub-pagos (cuotas). 
	Cada sub-pago, poseerá una fecha e importe, datos que podrán ser ajustados individualmente. <br>
	<br>
	Los siguientes elementos están disponibles en el formulario:
	<ul>
		<li> <b> Cantidad de pagos: </b> El número de sub-pagos (cuotas) que usted desea realizar.
		Por ejemplo: 10 pagos, uno cada semana. 
		El monto por defecto, será el especificado anteriormente dividido en partes iguales.
		
		<li> <b> Primera fecha de pago: </b> Si lo desea, puede utilizar el selector de fechas a través del
		ícono <img border="0" src="${images}/calendar.gif" width="16" height="16">.
		
		<li> <b> Pagos cada: </b> Utilice estos dos campos para elegir el período deseado entre cuotas.

		<li> <b> Calcular (botón): </b> Es posible que desee utilizar este botón, para ver/calcular
		cuánto se pagará exactamente en cada fecha. 
		Tan pronto como haga click en este botón, se mostrará el detalle de las fechas e importes de los pagos (cuotas). 
		Usted podrá cambiar estas fechas y montos, siempre cuidando que la suma de las cuotas coincida con el importe total.<br>
		
		Nota: Esta opción no realiza ningún proceso. 
		Se trata simplemente de una vista previa de los montos y fechas de las cuotas.
	</ul>
</ul>
<hr class="help">


<A NAME="scheduled_payments_by_admin"></a>
<A NAME="scheduled_payments_by_member"></a>
<h3> Búsqueda de pagos agendados </h3>
Aquí usted puede buscar los <a href="#scheduled"><u>pagos agendados</u></a> existentes en el sistema.<br> 
Los siguientes campos (filtros) en el formulario de búsqueda pueden ser especificados. <br>
Tenga en cuenta que un filtro en blanco, devolverá como resultado todos los valores posibles para dicho campo.
<ul>
	<li> <b> Tipo de búsqueda: </b> En este punto usted puede especificar si desea buscar pagos de "Salida" o de "Entrada".<br> 
	<ul>
		<li>Un pago de Salida es un pago normal. </li>
		<li>Un pago de Entrada, se refiere a una <a href="${pagePrefix}invoices"><u>factura</u></a>,
		para la cual, el remitente especifica que puede ser paga con pagos agendados.</li>
	</ul>
	<li> <b> Cuenta: </b> Seleccione un tipo de cuenta en el menú desplegable. 
	Esto sólo será visible si existe más de una posibilidad disponible.
	<li> <b> Estado: </b> "Abierto" significa que todavía no se pagó, el resto de las opciones se explican por sí mismas.
	<li> <b> Código de miembro / Nombre: </b> Mediante estos dos campos es posible especificar el miembro al que corresponden los pagos a buscar. 
	Los campos son auto-completados.
	<li> <b> Fecha (desde/hasta): </b> Usted puede especificar un rango de fechas aquí. 
	Si lo desea, puede seleccionar las fechas a través del ícono del calendario 
	<img border = "0" src="${images}/calendar.gif" width="16" height="16">&nbsp;.
</ul>
<hr class="help">

<a name="view_scheduled_payment"></a>
<h3> Detalles de pagos agendados </h3>
Esta ventana mostrará los detalles de los <a href="#scheduled"><u>pagos agendados</u></a>. 
Usted puede hacer click en los nombres de los miembros involucrados para acceder a sus perfiles.<br>

Puede hacer click en el ícono de impresión <img border="0" src="${images}/print.gif" width="16" height="16"> 
para imprimir los detalles.
<br><br> 
Si usted posee los permisos correspondientes, haciendo click en el link "Acciones" 
ubicado en la parte inferior derecha de la ventana, 
dos(2) botones estarán disponibles en la parte inferior de la ventana:
<ul>
	<li> <b> Bloquear: </b> Esta opción le permite bloquear temporalmente el pago.<br> 
	El bloqueo permanecerá hasta que el pago se cancele o desbloquee. <br>
	Un bloqueo de pago puede ser desbloqueado.</li>
	<li> <b> Desbloquear: </b> Debloquea el pago, por lo que el mismo se llevará a cabo en la fecha prevista originalmente. <br>
	Este botón sólo se mostrará si el pago agendado se encuentra bloqueado.<br>
	Si la fecha de pago ha pasado, este botón NO estará visible. <br>
	En este caso, usted puede efectuar el pago a través de la ventana de 
	<a href="#sheduled_payment_transfers">transferencias de pagos agendados</a>, 
	haciendo click en el ícono de visualización	
	<img border="0" src="${images}/view.gif" width="16" height="16"> de la transferencia.</li>
	<li> <b> Cancelar: </b> La diferencia con el "Bloquear", es que esta opción cancela un pago agendado de forma definitiva. <br>
	Los pagos agendados que están abiertos, no serán pagos y se eliminarán definitivamente.<br>
	Los pagos agendados que ya fueron pagos, no podrán ser cancelados.</li>
</ul>
<hr class="help">

<br><br><A NAME="sheduled_payment_transfers"></A><!-- Link is correct, but with Typo -->
<h3>Transferencias de pagos agendados </h3>
Esta página muestra todos los sub-pagos (cuotas) que forman parte de un 
<a href="#pay_scheduled"><u>pago agendado múltiple</u></a>. 
<br>
Puede hacer click en el ícono de visualización
<img border="0" src="${images}/view.gif" width="16" height="16"> 
para ir a los detalles de un pago.
<hr class="help">

<A NAME="scheduled_payments_result"></A>
<h3> Resultado de búsqueda de pagos agendados </h3>
Esta ventana muestra la lista de los <a href="#scheduled"><u>pagos agendados</u></a> existentes en el sistema, 
de acuerdo a los criterios de búsqueda especificados.<br>
<br><br> 
Los datos mostrados son:
<ul>
	<li> <b> Fecha:</b> Fecha de la programación de pagos.
	<li> <b> Código de miembro:</b> Puede hacer click sobre el mismo para acceder al perfil del miembro.
	<li> <b> Monto:</b> Importe del pago.
	<li> <b> Pagos:</b> El primer número indica el número de cuotas que ya han sido pagas (abonadas), 
	el segundo número muestra el número total de cuotas. Si el pago no es en cuotas, y corresponde un único pago, 
	entonces el segundo número será "1".

	<li> <b> Estado:</b> Puede ser "Agendado", "Bloqueado","En espera de autorización" 
	(ver <a href="#authorized"><u>pagos autorizados</u></a>).
	
	<li> Haga click en el ícono <img border="0" src="${images}/view.gif" width="16" height="16"> correspondiente, 
	para visualizar los detalles de la transferencia. 
	Allí usted tendrá la opción de imprimir la información, bloquear, desbloquear o realizar un pago 
	(siempre y cuando posea los permisos correspondientes).
</ul>
<hr>
<a name="authorized"></a>
<h2> Pagos autorizados </h2>
El sistema se puede configurar de modo tal que los pagos deban ser autorizados previamente 
a que el monto realmente sea transferido a la cuenta del receptor.
<br>
La autorización puede ser realizada por un administrador, un broker o por el miembro receptor, 
de acuerdo a la configuración establecida. 
<br>
En la medida en que el pago no haya sido autorizado aún, éste permanecerá en el estado de 
"En espera de autorización". El autorizador será notificado y podrá autorizar (activar) el pago.<br> 
Ambos miembros y el autorizador, tendrán acceso a una lista con los pagos pendientes de autorización. <br>
Tanto el emisor como el receptor (beneficiario) recibirán una notificación cuando el pago sea realizado (autorizado).


<span class="admin"> 
Los pagos autorizados son gestionados por los 
<a href="${pagePrefix}account_management#transaction_types"><u>tipos de transacción</u></a>, 
existen varias configuraciones disponibles.<br>
<br><br> 
Los pagos autorizados se pueden activar de la siguiente forma:
<ol>
	<li> <b> Permisos: </b> En primer lugar debe tener cuidado de que todos los 
	<a href="${pagePrefix}groups#permissions"><u>permisos</u></a> necesarios sean establecidos.
	
	Usted puede configurar los permisos para los administradores, brokers y miembros. 
	Para cada grupo de usuarios, existen varios permisos de autorización, puede que quiera usar
	su función de búsqueda del navegador (normalmente Ctrl-F o Ctrl-B) para localizarlos en la página correspondiente.
	
	<li> Luego autorice/habilite el <a href="${pagePrefix}account_management#transaction_types"><u>tipo de transacción</u></a>. 
	Esto se realiza mediante el parámetro "Requiere autorización", localizado en la ventana de 
	<a href="${pagePrefix}account_management#transaction_type_details"><u>detalles del tipo de transacción</u></a>. 
	
	<b> Nota: </b> Esta opción de autorización no podrá ser deshabilitada si existen operaciones a la espera de ser autorizados.
	
	<li> <b> Niveles de autorización: </b> Luego, es necesario configurar los niveles de autorización. 
	Esto se realiza en la ventana de <a href="${pagePrefix}account_management#authorized_payment_levels"><u>Niveles de autorización</u></a>, 
	ubicada en la parte inferior de los detalles del tipo de transacción. 
	<br>
	Para obtener mayor informacipon sobre este tema, por favor consulte su archivo de ayuda específico.
</ol>

</span>

<span class="member">
<br><br> 
La autorización de un miembro, simplemente significa que el receptor deberá aceptar 
el pago de otro miembro antes de que la operación se efectúe. 
<br>
En este sentido, el receptor tendrá la posibilidad de rechazar el pago (por ejemplo, si el producto no está disponible). <br>
Tanto el receptor como el emisor recibirán la correspondiente notificación. 
<br>
Esta configuración es similar a la utilización de facturas, y su uso no es muy comúm. 
Es mejor no utilizar simultáneamente pagos autorizados y facturas en el mismo sistema.

</span>
<br><br> 
Usted puede encontrar los pagos autorizados en los siguientes lugares en Cyclos:
<ul>
	<li> Menú: Cuentas> Pagos a autorizar: </b><br> 
	Esta opción ofrece una visión global de los pagos que deben ser autorizados por usted. 
	Este menú sólo estará disponible si usted posee los permisos para autorizar pagos.<br>
	<br>
	<li> Menú: Cuentas> Búsqueda de autorizaciones: </b><br> 
	Esta opción le permite buscar autorizaciones, pasadas o presentes, autorizadas, negadas o canceladas. <br>
	Todas las acciones (autorizaciones) anteriormente realizadas por usted, se pueden encontrar a través de esta ventana.<br>
	Tenga en cuenta que esta opción es sólo para la búsqueda de acciones de autorización.<br> 
	Por lo tanto, las transferencias (pagos) en espera de autorización no serán mostradas.<br>
	(aún no se han tomado medidas al respecto).<br>
	<br>
	<li class="broker"> Menú: Brokering> Pagos a autorizar: </b><br> 
	Esta opción muestra una visión del conjunto de los pagos de sus miembros, pendientes de autorización como broker.<br>
	<br>
	<li class="broker"> Menú: Brokering> Búsqueda de autorizaciones: </b><br> 
	Esta opción le permite buscar autorizaciones, pasados o presentes, autorizados, negados o cancelados de sus miembros, como broker. 
	Todos las acciones (autorizaciones) anteriormente realizadas por usted (como broker), se pueden encontrar a través de esta ventana.<br>
</ul>
<hr class="help">

<a name="transfers_awaiting_authorization_by_member"></a>
<a name="transfers_awaiting_authorization_by_admin"></a>
<h3> Búsqueda de pagos a autorizar </h3>
Esta ventana le permite obtener una visión general sobre los pagos que deben ser 
<a href="#authorized"><u>autorizados</u></a> por usted. <br>
<br>
La permanencia de campos (filtros) vacíos, significará que todos los valores posibles para ese campo 
serán incluídos en el resultado de la búsqueda. <br>
Si hace click en el botón "Búsqueda" sin fitrar ningún campo, se mostrarán todos los pagos pendientes de autorización existentes.
<br><br> 
Usted puede especificar los siguientes criterios de búsqueda:
<ul>
	<li> <b> Código de miembro / Miembro: </b> Permite indicar el Código o Nombre del miembro emisor de los pagos (pagador) 
	pendientes de autorización a buscar. Campos auto-completables.
	<li> <b> Fecha (desde/hasta): </b> Puede especificar el período deseado de búsqueda.
	Si lo desea, usted puede utilizar el calendario a través del ícono 
	<img border ="0" src="${images}/calendar.gif" width="16" height ="16">.
	<li> <b> Número de transacción: </u> </b> Permite filtrar por el número (identificativo) de transacción.
	<li span class="admin"> 
	<b> Sólo en el caso en el que el broker no pueda autorizar: </b> Chequeando
	esta casilla de verificación sólo se incluirán los resultados donde usted como administrador será el único habilitado a autorizar.
	</ul>
	Los resultados de la búsqueda se mostrarán en la ventana inferior.
<hr class="help">

<a name="transfers_awaiting_authorization_result"></a>
<h3> Resultado de búsqueda de pagos a autorizar </h3>
En esta ventana usted visualizará las transacciones en espera de 
<a href="#authorized"><u>autorización</u></a> resultantes de la búsqueda.<br>
<br>
Los importes negativos corresponden a pagos salientes en espera de su autorización, y
los positivos a pagos entrantes que se encuentran pendientes de autorización.<br>
<br>
Haga click en el ícono de visualización <img border="0" src="${images}/view.gif" width ="16" height ="16"> correspondiente 
para acceder a la ventana de detalles, donde usted podrá autorizar o rechazar el pago.
<hr class="help">

<a name="transfer_authorizations_by_admin"></a>
<a name="transfer_authorizations_by_member"></a>
<h3> Búsqueda de autorizaciones </h3>
En esta ventana usted podrá buscar transacciones previamente <a href="#authorized"><u>autorizadas</u></a>.<br>
El formulario es muy sencillo. 
Tenga en cuenta que si mantiene un campo en blanco, cualquier valor posible para el mismo será devuelto como resultado.<br>
<br>
Las siguientes opciones de búsqueda están disponibles:
<ul>
	<li> <b> Tipo de transacción: </b> Permite buscar por tipo de transacción.
	<li> <b> Acción:</b>
	<ul>
		<li> <b><i> Autorizado: </i></b> Pagos autorizados.
		<li> <b><i> Rechazado: </i></b> Pagos denegados.
		<li> <b><i> Cancelado: </i></b> Pagos cancelados (por otros).
	</ul>
	<br>
	<li class="admin"> <b> Código de administrador / Nombre: </b> Búsqueda por administrador individual.
	<li> <b> Código de miembro / Nombre: </b> Búsqueda por miembro individual.
	<li> <b> Fecha (desde/hasta): </b> Búsqueda por rango de fechas.
</ul>
Al finalizar haga click en el botón "Búsqueda" ubicado en la parte inferior derecha de la ventana. <br>
Los resultados se mostrarán en la ventana inferior.
<hr class="help">

<a name="transfers_authorizations_result"></a>
<h3> Resultado de búsqueda de autorizaciones </h3>
Esta ventana muestra los resultados de la búsqueda para los criterios especificados en la ventana superior.<br>
<br>
Utilice el ícono de visualización
<img border="0" src="${images}/view.gif" width="16" height="16"> correspondiente, para obtener más detalles sobre el elemento deseado.
<hr class="help">

<a name="transaction_authorizations_detail"></a>
<h3> Acciones de autorización </h3>
Esta ventana muestra todas las acciones de <a href="#authorized"><u>autorización</u></a> 
realizadas sobre la operación.<br> 
Dichas acciones pueden corresponder a autorizaciones, pero también a denegaciones (rechazos) o cancelaciones. <br>
<br>
Se muestra para acción, su fecha, quién la realizó y sus comentarios.
<br><br>
<hr>
<h2> Ventana de pago </h2>
A continuación se ofrecen algunas ventanas de ayuda, y otras relacionadas a los pagos.

<a name="accessing_channels"> </a>
<h3> Acceso a canales de pago </h3>
Dependiendo de la configuración del sistema, un miembro puede realizar pagos a través de los diversos canales de pago:
<ul>
	<li> El más común, es a través de la página principal del acceso WEB (Internet): <br>
	<b><i>www.dominio.com/cyclos</i></b></li>
	<br>
	<li> Otro útil canal de pago, es una simple página Web dónde los miembros sólo pueden 
	loguearse (acceder) y efectuar un pago rápido: <b><i>www.dominio.com/posweb/pay)</i></b></li>
	<br>
	<li> Los Miembros o empresas que sólo desean recibir pagos de clientes en un POS (punto de venta), 
	pueden utilizar la página POSweb: <b><i>www.dominio.com/posweb/receive</i></b><br> 
	Tenga en cuenta que los clientes necesitan poseer generado un PIN personal para validar los pagos.</li>
	<br>
	<li> Los miembros o empresas que desean estar en condiciones de pagar y recibir pagos 
	en la misma página Web, pueden utilizar el canal de acceso: <b><i>www.dominio.com/posweb</i></b></li>
	<br>
	<li> Los miembros o empresas que poseean operadores habilitados para 
	realizar pagos y aceptar pagos de clientes, pueden utilizar la página POSweb 
	para operador: <b><i>www.dominio.com/posweb/operador</i></b></li>
	<br>
	<li> Los pagos a través de teléfonos celulares pueden ser efectuados a través de la URL: <br>
	<b><i>www.dominio.com/cyclos/mobile (wap2)</i></b>
	<br>
	<b><i>www.dominio.com/cyclos/wap (wap1)</i></b></li>
</ul>
La disponibilidad de los canales de pago dependerá de la configuración del sistema. <br>
Además, algunas operaciones pueden exigir la utilización de una contraseña de transacción.
Esto también es parte de la configuración.
<hr class="help">


<a name="request"></a>
<h3> Solicitar pago a través de otros canales </h3>
En esta ventana usted puede solicitar un pago a través de otro medio de pago (<a href="#accessing_channels"><u>canal</u></a>).<br>
Funciona de forma similar a una <a href="${pagePrefix}invoices#invoices_top"><u>factura</u></a> del sistema, 
en el sentido que el pago se realiza al momento de ser aceptado.<br>
<br>
La gran diferencia es que esta solicitud/requerimiento de pago necesita para su autorización: 
<ul>
	<li>Un PIN (credencial),</li>
	<li>Una confirmación con un "código identificativo" de la operación, autogenerado por el sistema (sólo canal SMS).</li>
</ul>

La Administración deberá habilitar los canales deseados de forma previa para que esta función pueda ser utilizada, 
y sea posible enviar "Solicitudes de pago".

<br><br> 
<b><i>Ejemplo:</i></b> La solicitud de pago <b>SMS</b> funciona de la siguiente manera: <br>
<ol>
	<li>Se indica el miembro al que se le solicitará el pago, ingresando su Código o Nombre (autocompletables),
	monto (Cantidad) en su moneda correspondiente y Descripción.</li> 
	<li>Se presiona el botón "Aceptar" para su envío.</li>
	<li>La solicitud de pago se envía a través del canal SMS, y queda en estado de "Pendiente".</li>
	<li>El miembro recibe al instante un mensaje SMS con la solicitud del pago.</li>
	<li>El miembro confirma el pago, enviando un mensaje SMS incluyendo su PIN y el código de operación generado. Su estado cambia a "Exitoso".</li>
</ol>
De existir algún inconveniente en el envío o recepción de la solicitud de pago, pasará al estado de "Fallido".

<hr class="help">

<a name="search_requests"></a>
<h3> Búsqueda de solicitudes de pago </h3>
En esta ventana usted puede buscar solicitudes de pago en el sistema. <br>
Por defecto se muestran todas las solicitudes pendientes y exitosas (confirmadas y pagas).<br> 
<br>
Usted puede filtrar la búsqueda por Estado y/o por Miembro.<br>
<br>
Haga click en el botón "Búsqueda", ubicado en la parte inferior derecha de la ventana, para mostrar los resultados obtenidos. 

<br><br> 
La ventana de resultados, muestra las solicitudes de pago coincidentes con los filtros de búsqueda establecidos.<br> 
La página se actualiza automáticamente cada 5 segundos (su estado cambia de forma automática).
<hr class="help">

<a name="account_overview"></a>
<span class="member">
<h3> Información de cuentas </h3>
<i>("Menú: Cuentas> Información de cuentas")</i>
<br><br>
Esta ventana muestra una lista con todas las Cuentas que usted posee en el sistema.<br>
<br>
Se muestran los siguientes datos:
<ul> 
	<li><b>Cuenta:</b> Nombre de la cuenta.</li> 			
	<li><b>Estado:</b> Estado actual en el que se encuentra la cuenta.</li>		 
	<li><b>Saldo de la cuenta:</b> Saldo actual de la cuenta en su respectiva moneda.</li> 
</ul>
<br>
Haga click en el ícono de visualización <img border="0" src="${images}/view.gif" width="16" height="16"> correspondiente, 
si desea acceder/visualizar los detalles de una cuenta.
</ul>
<hr class="help">
</span>

<span class="admin">
<h3> Información de cuentas del sistema </h3>
<i>("Menú: Cuentas> Cuentas de sistema")</i>
<br><br>
Esta ventana muestra una lista con todas las cuentas del sistema existentes, y sus respectivos saldos actuales ("Saldo de la cuenta").<br>
<br>
Haga click en el ícono de visualización <img border="0" src="${images}/view.gif" width="16" height="16"> correspondiente,
si desea acceder/visualizar los detalles de una cuenta.
<hr class="help">
</span>

<a name="transaction_history"></a>
<h3> Buscar transacciones </h3>
Esta ventana brinda varias opciones para la búsqueda de transacciones. <br>
<br>
<span class="member">
Haga click en el botón "Avanzada" si desea realizar una búsqueda avanzada (mayor cantidad de filtros). <br>
<br>
Haga click en el botón "Simple" si usted desea realizar una búsqueda simple.<br>
<br>
</span>

Usted puede seleccionar entre las siguientes opciones:
<ul>
	<li> <b> Estado: </b> Estado de los pagos que necesitan autorización.
	Sólo se mostrarán cuando la autorización de pagos o préstamos se encuentre habilitada. <br>
	<br>
	Las opciones son:<br>
	<ul>
		<li><b><i> Pendiente Autorización: </i></b> El pago o préstamo necesita ser autorizado antes de que la transferencia sea procesada.</li>
		<li><b><i> Procesado: </i></b> El pago o préstamo ha sido autorizado y procesado.</li>
		<li><b><i> Rechazado: </i></b> El pago o préstamo ha sido rechazado.</li>
		<li><b><i> Cancelado: </i></b> El pago o préstamo ha sido cancelado.</li>
		</li>
	</ul>
	<br>
	<li> <b> Filtro de pago: </b> Con esta lista desplegable, usted puede filtrar por un filtro de pago específico.</li>
	
	<li class="admin"> <b> Grupo de permisos: </b> Con esta lista desplegable, usted puede seleccionar 
	uno o varios grupos de miembros para filtrar la búsqueda.</li>
	
	<li> <b> Código de miembro / Nombre: </b> Muestra sólo las transacciones de un cierto
	miembro, completando su código de miembro o su nombre. Los campos serán auto-completados al escribir en ellos.</li>
	
	<li> <b> Rango de fechas: </b> Muestra sólo las transacciones en un rango de fechas, 
	indicando un inicio y/o una fecha de finalización.</li>
	
	<li> <b> Descripción: </b> Buscar una cierta descripción, mediante la utilización de una palabra clave en la 
	<i>descripción</i>. Por ejemplo: ingresando "bicicleta" se mostrarán todas las transacciones que contengan 
	la palabra bicicleta en su descripción o título.
	</li>
	
	<li> <b> Número de transacción: </b> Si el número de transacción se activa en la configuración del
	sistema, usted puede utilizar este campo para buscar por dicho número.</li>
</ul>
<hr class="help">

<a name="transaction_history_result"></a>
<h3> Resultado de búsqueda de transacciones </h3>
Esta ventana muestra como resultado el resumen de movimientos (pagos) para esta cuenta. <br>
<br>
Si hace click en el ícono de impresión <img border="0" src="${images}/print.gif" width="16" height="16"> 
(ubicado en la barra superior de la ventana, junto al ícono de ayuda), 
se le presentará una versión imprimible de sus resultados de búsqueda, 
con el detalle de las primeras ${localSettings.maxIteratorResults} transacciones involucradas y su respectivo resumen. 
<span class="admin">
El número de resultados de búsqueda podrá ser configurado en el Menú: Configuración> Configuración local> Limites.</span><br>
<br> 
Si hace click en el ícono de guardar <img border="0" src="${images}/save.gif" width="16" height="16">
(ubicado en la barra superior de la ventana, junto al ícono de impresión), 
usted podrá descargar sus resultados de búsqueda en un archivo de extensión .CSV (Excel).<br>

<br><br> La primer sección (parte superior) de la ventana muestra la siguiente información:
<ul>
	<li> <b> Saldo de la cuenta:</b> Saldo actual de la cuenta en su respectiva moneda.</li>
	
	<li class="member"> <b> Monto reservado: </b> Esta cantidad es reservada temporalmente,
	y NO podrá utilizarse para realizar pagos.
	Por lo tanto el Monto reservado es restado del Saldo disponible.<br>
	Este monto puede ser generado debido a la existencia de transacciones pendientes de autorización 
	o de una reserva para futuros intereses periódicos, comisiones, u otros cargos (tasas).</li>
	
	<li class="member"> <b> Límite mínimo de crédito: </b> Es el límite de crédito mínimo establecido para la cuenta.<br>
	Esto quiere decir que aunque se trata de un número <u>positivo</u>, de existir, 
	indicará el saldo más bajo (negativo) que la cuenta puede alcanzar.
	</li>
	<span class="member">
	<br>
	O sea:<br>
	<ul>
		<li>Si es igual a cero(0), NO se mostrará y la cuenta no podrá poseer saldo negativo.</li>
		<li>Si es mayor a cero(0), se mostrará y se constituirá en el saldo más bajo (negativo) que la cuenta puede alcanzar.</li>
	</ul>
	<br>	
	</span>

	<li class="member"> <b> Límite máximo de crédito: </b> Es el límite de crédito máximo establecido para la cuenta.<br>
	De existir, indicará el saldo más alto (positivo) que la cuenta puede alcanzar.<br>
	Si es igual a cero(0), NO se mostrará.<br>
	
	</li>
	<li class="member"> 
	<b> Saldo disponible: </b> Es el saldo disponible para gastar.<br>
	El saldo disponible es calculado, y dependerá de la existencia de un "Monto reservado" y de un "Límite mínimo de crédito".<br>
	<br>
	
	Si existe un Monto reservado, el Saldo disponible será igual al Saldo de la cuenta menos el Monto reservado.<br>
	Ejemplo: si tengo un saldo de la cuenta de 1000 y un monto reservado de 200, el saldo disponible será de 800.<br>
	<br>
	Si existe un Límite mínimo de crédito, el saldo disponible será calculado como:<br>
	<br>
	<ul>	
		<li>Si el saldo de la cuenta es positivo, el saldo disponible será igual al límite mínimo de crédito más el saldo de la cuenta.<br>
		Saldo disponible = Límite mínimo de crédito + Saldo de la cuenta.<br>
		Ejemplo: si tengo un saldo de la cuenta de 500 y un límite mínimo de crédito de 2000, el saldo disponible será de 2500.
		</li>
		<br>
		<li>Si el saldo de la cuenta es negativo, el saldo disponible será igual al límite mínimo de crédito menos el saldo de la cuenta (valor absoluto).<br>
		Saldo disponible = Límite mínimo de crédito - Saldo de la cuenta.<br>
		Ejemplo: si tengo un saldo de la cuenta de -500 y un límite mínimo de crédito de 2000, el saldo disponible será de 1500.</li>
	</ul>
	<br>
</ul>
En la parte inferior de esta sección se muestra una lista con todos los pagos realizados y recibidos.<br> 
Los pagos recibidos (entrantes), se muestran con un signo positivo (+) delante; <br>
Los pagos realizados (salientes) aparecen con el signo negativo (-) delante. <br>
La lista muestra la fecha de pago, miembro (receptor o pagador) y la descripción del pago. <br>
El código de miembro/nombre es un enlace al perfil del miembro. <br>
<br>
Al hacer click en el ícono de visualización <img border="0" src="${images}/view.gif" width="16" height="16">
de un pago, se abrirá una ventana con su descripción completa (detalles).
<hr class="help">

<a name="transaction_detail"></a>
<h3> Detalles de transacción </h3>
Esta ventana muestra los detalles del pago seleccionado.<br>
Usted puede imprimir los detalles de la transacción, presionando el ícono de impresión 
<img border="0" src="${images}/print.gif" width="16" height="16">.<br>
<br> 
De existir un pago "padre" o un pago "hijo" (por ejemplo: pago de tasas), se mostrará en la parte inferior de la ventana.
<br><br>
<span class="broker admin"> 
En el caso de que la transacción necesite autorización, usted tendrá la opción de <i>Aceptarla</i> o <i>Rechazarla</i>.<br> 
Usted deberá incluirle un comentario a su acción. Si selecciona la casilla "Mostrar en los miembros", su comentario 
será visible por el miembro. Si esta opción no es seleccionada, su comentario sólo será visible por usted y la administración.
</span>
<span class="admin">
<br><br>
Bajo ciertas condiciones (configuración y permisos), es posible "deshacer" una transacción a través de una 
<a href="#charge_back"><u>devolución del pago</u></a>.
En este caso, un botón especial "Devolución" será visible por usted en esta ventana.
</span>
<hr class="help">

<span class="admin">
<a name="charge_back"></a>
<h3> Devolución de pagos </h3>
Un Administrador (con los permisos correspondientes) puede efectuar una "devolución de pago", esto significa
que se realizará un pago, en la misma cuenta y por el mismo monto, pero con signo contrario.<br> 
En el caso de que el pago haya generado otras operaciones (por ejemplo, tasas, préstamos), 
todas estas transacciones serán "devueltas". <br>
Si la "devolución de pago" es posible, un botón especial (Devolución) será visible en la 
<a href="#transaction_detail"><u>ventana de detalles de transacción</u></a>. 
Sin embargo, este botón sólo estará visible si la transacción fue realizada dentro de los plazos establecidos. 
Usted puede definir el tiempo máximo en el que un tipo de transacción puede ser "devuelta", en el:<br> 
"Menú: Configuración> <a href="${pagePrefix}settings#local"><u>Configuración local> Devolución de pagos</u></a>".
<hr class="help">
<br>
</span>

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