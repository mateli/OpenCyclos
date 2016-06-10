<div style="page-break-after: always;">
<a name="invoices_top"></a>
<br><br>

Cyclos le permite enviar una "Factura" (solicitud de pago) a un miembro.<br> 
Se trata de una factura, solicitando el pago de productos o servicios entregados.<br>
Existen varias características para la puesta en marcha y gestión de las facturas entre miembros y la administración.
Asimismo, los miembros pueden enviarse facturas entre ellos. <br>
<br>
Las facturas pueden ser rechazadas o aceptadas por el receptor. <br>
El emisor puede también cancelar una factura. <br>
La otra parte (contraparte) siempre recibirá una notificación respecto a las acciones tomadas sobre las facturas. <br>
Sin embargo, los miembros NO pueden rechazar facturas procedentes de cuentas del sistema. 
<br><br>

<i> ¿Dónde las encuentro? </i><br>
Puede acceder a las facturas de las siguientes maneras:
	<ul>
	<span Class="admin">
	<li>"Menú": Cuentas> Factura a miembro: </b> Le permite enviar una factura
	de la organización (de una cuenta de sistema) a un miembro. </li>
	<li>"Menú": Cuentas> Administrar Facturas: </b> Le permite gestionar las 
	facturas entrantes y salientes de las cuentas del sistema. </li>
	</span>
	<span Class="admin broker">
	<li><a href="${pagePrefix}profiles"><u> Perfil de miembro</u></a>> Ver facturas: 
	Le permite ver las facturas de un determinado miembro.
	</li>
	</span>
	<span Class="member">
	
	<li>"Menú": Cuentas> Factura al sistema: </b> Le permite enviar facturas a
	la organización.
	</li>
	
	<li>"Menú": Cuentas> Factura a miembro: </b> Le permite enviar facturas a
	otro miembro. </li>
	
	<li>"Menú": Cuentas> Facturas: </b> Le permite ver y gestionar todas sus facturas entrantes y salientes.
	</li>
	<li> <a href="${pagePrefix}profiles"><u> Perfil de miembro</u></a>> Enviar factura: 
	Le permite enviar una factura a un miembro directamente a través de su perfil.
	</li>
	</span>
</ul>
<hr>

<span class="member">
<A NAME="send_invoice_member_to_system"></A>
<h3> Enviar factura al sistema </h3>
Es posible que un miembro le envíe una <a href="#invoices_top"><u>factura</u></a> al sistema (organización). 
El administrador de la organización recibirá la factura y podrá aceptarla o rechazarla. 
Cuando un administrador acepte o rechace una factura, usted recibirá
una notificación en su sección de noticias, informándole sobre la acción. <br>
Para enviar la factura, complete los campos requeridos y haga click en el botón "Aceptar".
<hr class="help">
</span>
<A NAME="send_invoice_system_to_member"></A>
<A NAME="send_invoice_member_to_member"></A>
<A NAME="send_invoice_member_to_member_select"></A>

<h3> Enviar factura al miembro </h3>
Aquí usted puede enviar una factura a un miembro. <br>
El miembro recibirá una "factura", y podrá pagarla haciendo click en el botón "Aceptar".<br> 
Si la factura es aceptada, automáticamente se convertirá en un pago normal al miembro emisor. 
Como cualquier pago, se mostrará en el historial de transacciones.<br>
<br>
<span class="member"> Por otra parte, el miembro también puede optar por
rechazar la factura, lo que significa que el pago será cancelado. <br>
El emisor, recibirá la notificación de que la factura ha sido rechazada.
<br><br> 
En el "Menú": Cuentas> Facturas, se pueden visualizar y gestionar las facturas entrantes y salientes.
</span>
<span class="admin">
Una visión general de todas las facturas del - sistema a los miembros - y
de los - miembros al sistema - puede ser accedida en el "Menú": Cuentas> Administrar facturas. 
</span>
<br><br> 
Usted deberá ingresar los siguientes campos:
<ul>
	<li> <b> Código de miembro / Nombre: </b> Usted debe indicar el miembro destinatario de la
	factura, completando su "Nombre" o su "Código de Miembro" (campos auto-completados). 
	<li> <b> Monto: </b> En caso de que en su sistema estén disponibles múltiples monedas,
	usted deberá seleccionar la moneda en el menú desplegable e ingresar el monto correspondiente. 
	Si sólo existe una moneda disponible, este menú desplegable no se mostrará y sólo deberá ingresar el monto deseado.
	<li> <b> Agendado: </b> Esta opción aparecerá si es activada por la Administración. <br>
	Con la opción de agendado, se permite que el receptor pueda pagar
	la factura con pagos agendados o programados (cuotas). <br>
	El receptor de la factura será informado sobre plazos, fechas y montos, y cuando acepte
	la factura, los pagos programados se mostrarán en sus pagos agendados "salientes".
	<li> <b> Tipo de pago: </b> Luego deberá elegir el tipo de pago.
	Esto sólo si existe más de un tipo de pago en el sistema. Si sólo existe un tipo, 
	este campo no será mostrado.
	<li> <b> Descripción: </b> Cuando la factura es aceptada, esta descripción
	aparecerá como descripción de la transacción.
</ul>
Luego de completar los campos requeridos, haga click en "Aceptar" para enviar la factura. 
Se le solicitará una confirmación.
<hr class="help">

<span class="admin">
<A NAME="manage_invoices_by_admin"></A>
<h3> Facturas del sistema </h3><br><br>
Esta ventana le permite obtener una visión general de las 
<a href="#invoices_top"><u>facturas</u></a> enviadas desde cuentas del sistema a cuentas de miembros (facturas salientes), 
y de las facturas enviadas desde las cuentas de miembros a cuentas del sistema (facturas entrantes).
<br><br> 
Usted puede filtrar las facturas por los siguientes criterios:
<ul>
	<li><b>Estado:</b> <a href="#status"><u>Estado</u></a> actual de la factura.
	<li><b>Tipo de factura:</b> Indica si desea buscar facturas de "Entrada" o de "Salida".
	<li><b>Fecha de inicio / Fecha de fin:</b> Rango de fechas de las facturas a buscar.
	<li><b>Código de miembro / Nombre:</b> Código o nombre del miembro.
	<li><b>Tipo de pago:</b> 
	Sólo funciona para facturas de "Salida".
	Para facturas de "Entrada" el <a href="${pagePrefix}account_management#transaction_types"><u>tipo de pago</u></a> 
	no es definido. Un administrador deberá seleccionar el tipo de pago 
	al aceptar la factura (ver <a href="#incoming_invoice_detail_by_admin"><u>detalles de factura</u></a>).
	<li><b>Descripción:</b> Descripción de la factura.
	<li><b>Número de transacción:</b> Número identificador de la factura a buscar.
</ul>
Luego de completar el formulario, haga click en el botón "Búsqueda", los resultados se mostrarán
en la <a href="#invoices_result_by_admin"><u>ventana inferior</u></a>.
<hr class="help">
</span>

<span class="member">
<A NAME="manage_invoices_by_member"></A>
<h3> Mis Facturas </h3>
Con el botón de opción "Tipo de factura" en esta ventana, usted puede visualizar una lista con las
<a href="#invoices_top"><u>facturas</u></a> "entrantes" o "salientes" en el sistema.<br>
Los resultados se mostrarán en la <a href="#invoices_result_by_member"><u>ventana inferior</u></a>.
<br><br> 
Presionando el botón "Avanzada", usted podrá refinar su búsqueda, usando algunos o todos los
filtros disponibles en el formulario. <br>
La mayoría de los campos son bastante claros y autoexplicativos. <br>

El <a href="#status"><u>Estado</u></a> puede poseer diferentes valores; haga click en
el enlace para obtener más información.
<hr class="help">
</span>

<span class="admin broker">
<A NAME="manage_member_invoices_by_admin"></A>
<A NAME="manage_member_invoices_by_broker"></A>
<h3> Facturas de miembro </h3><br><br>
Esta ventana le permite obtener una visión general de las <a href="#invoices_top"><u>facturas</u></a> del miembro (de Entrada o Salida).
<br><br>
Usted puede filtrar las facturas por los siguientes criterios:
<ul>
	<li><b>Estado:</b> <a href="#status"><u>Estado</u></a> actual de la factura.
	<li><b>Tipo de factura:</b> Indica si desea buscar facturas de "Entrada" o de "Salida".
	<li><b>Fecha de inicio / Fecha de fin:</b> Rango de fechas de las facturas a buscar.
	<li><b>Código de miembro / Nombre:</b> Código o nombre del miembro.
	<li><b>Tipo de pago:</b> 
	Sólo funciona para facturas de "Salida".
	Para facturas de "Entrada" el <a href="${pagePrefix}account_management#transaction_types"><u>tipo de pago</u></a> 
	no es definido. Un administrador deberá seleccionar el tipo de pago 
	al aceptar la factura (ver <a href="#incoming_invoice_detail_by_admin"><u>detalles de factura</u></a>).
	<li><b>Descripción:</b> Descripción de la factura.
	<li><b>Número de transacción:</b> Número identificador de la factura a buscar.
</ul>
Luego de completar el formulario, haga click en el botón "Búsqueda", los resultados se mostrarán
en la <a href="#invoices_result_by_admin"><u>ventana inferior</u></a>.
<hr class="help">
</span>

<a name="status"></a>
<h3> Estado de Factura </h3>
El "estado" de una <a href="#invoices_top"><u>factura</u></a> puede ser:
<ul>
	<li> <b> Abierto: </b> Factura que ha sido enviada pero aún no ha sido aceptada, rechazada ó cancelada por el receptor.
	<li> <b> Aceptado: </b> Factura paga por el receptor.
	<li> <b> Rechazado: </b> Factura rechazada por el receptor.
	<li> <b> Cancelado: </b> Factura cancelada por el emisor (antes de ser aceptada o rechazada por el receptor).
	<li> <b> Vencido: </b> Factura frente a la cual el receptor no ha tomado ninguna acción (aceptar, rechazar) y ha superado su fecha de vencimiento.
</ul>
<hr class="help">

<A NAME="accept_invoice"></A>
<h3> Confirmación de aceptación de la factura </h3>
En esta ventana usted puede confirmar una <a href="#invoices_top"><u>factura</u></a> y generar su pago correspondiente.<br>
<br>
Son desplegados los detalles del pago a generar.<br>
Usted debe presionar el botón "Aceptar" para su confirmación.<br>
<br>
Luego de esta acción, no existe vuelta atrás: al hacer click en el botón "Aceptar", la cantidad
se debitará de su cuenta y la factura se pagará. 
<hr class="help">
	
<A NAME="invoices_result_by_admin"></A>
<A NAME="invoices_result_by_member"></A>
<h3> Resultado de búsqueda </h3>
Esta página muestra la lista de <a href="#invoices_top"><u>facturas</u></a> resultante de la búsqueda.<br>
<br>
Si desea visualizar los detalles de una factura, haga click en su ícono de visualización 
<img border="0" src="${images}/view.gif" width="16" height="16"> correspondiente.<br>
<br>
Si la factura se encuentra "abierta", y dependiendo del tipo de factura, usted podrá realizar acciones sobre la misma (Aceptar, Rechazar o Cancelar).
<hr class="help">

<A NAME="invoice_details"></A>
<A NAME="outgoing_invoice_detail_by_admin"></A>
<A NAME="incoming_invoice_detail_by_admin"></A>
<A NAME="outgoing_invoice_detail_by_member"></A>
<A NAME="incoming_invoice_detail_by_member"></A>
<h3> Detalles de factura </h3>
Esta ventana muestra los detalles de la factura seleccionada.<br>
Dependiendo de los permisos y del tipo de factura, usted podrá ejecutar una de las siguientes acciones:
<ul>
	<li> <b> Aceptar: </b> 
	<span class="member"> si usted es </span> 
	<span class = "admin agente"> 
	Si este miembro es </span> el receptor de la factura, usted	puede aceptarla. 
	Si lo hace, el importe correspondiente se debitará de su 
	<span class = "admin agente"></span> 
	<span class="member"> su </span>
	cuenta, y se transferirá a la cuenta del emisor de la factura.<br> 
	En condiciones normales, usted habrá realizado el pago de la factura. <br>
	Si hace click en este botón, una nueva ventana de confirmación se mostrará en pantalla y se le solicitará su aceptación (botón Aceptar).
	
	<li> <b> Rechazar: </b> <span class="member"> si es </span> 
	<span class = "admin agente"> Si este miembro es </span> el receptor de la factura, usted también puede rechazarla. 
	Esto significa que se niega a pagar el monto solicitado. 
	El pago no se realizará, y la otra parte (emisor de la factura) recibirá la notificiación correspondiente. <br>
	Es imposible rechazar una factura proveniente del sistema/organización.
	
	<li> <b>Cancelar: </b> <span class="member"> si usted es </span> 
	<span class="admin broker"> Si el miembro es </span> <span
	class = "miembro"></span> el emisor de la factura, usted puede cancelarla en cualquier momento, antes de que el receptor la acepte.<br> 
	Si usted cancela una factura, la otra parte (receptor de la factura) recibirá una notificación, indicándole que la factura ha sido cancelada.
</ul>
<br><br>
Para las facturas entrantes, usted podría tener que especificar el tipo de pago a utilizar, siempre y cuando existan más de un tipo definidos en el sistema.<br> 
En este caso, se mostrará el menú desplegable "Tipo de pago", donde usted deberá realizar la selección.

<hr class="help">
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