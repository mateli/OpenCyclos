<div style="page-break-after: always;">
<a name="loans_top"></a>
<br><br>
Los préstamos son la base para el mecanismo de crédito en Cyclos.<br> 
Cyclos soporta las cualidades esenciales de un préstamo, incluídos los pagos programados 
(cuotas), sus intereses, y permite a los administradores aplicar una variedad de tasas.

<span class="admin">
<br><br><i> ¿Dónde los encuentro? ¿Cómo hacerlos funcionar? </i> <br>
<br>
Para el uso de préstamos en Cyclos, usted debe realizar los siguientes pasos:
<ol>
<li><b> Crear tipos de transacciones:</b> Antes de poder utilizar los préstamos, 
deben ser creados los <a href="${pagePrefix}account_management#transaction_types"><u> tipos de transacciones </u></a> 
para los préstamos, ya que cada préstamo debe tener su propio tipo de transacción. <br>
En general, usted deberá crear dos (2) tipos de transacciones para los préstamos en Cyclos: <br>
una correspondiente a la entrega del préstamo a los miembros (procedentes de una cuenta de sistema); 
y otro tipo que el miembro utilizará para el pago del préstamo.<br> 

Usted no puede crear la primera (otorgamiento del préstamo) sin haber especificado previamente la última (pago de préstamo), 
por lo que el mejor inicio es creando el tipo de transacción correspondiente al retorno de la inversión (de miembro al sistema).<br> 
Para obtener sistemas más complicados de débito, usted puede crear tipos de transacciones de honorarios o intereses también.

<br><br> Un nuevo tipo de transacción <b><i> para el pago de los préstamos </i></b> se crea de la siguiente forma:<br>
	<ul>
		<li> Ir a la sección correspondiente en el "Menú: Cuentas> Administrar cuentas";
		<li> Seleccione un tipo de cuenta mediante la cual los miembros recibirán los préstamos.
		Normalmente, esta sería "Cuenta de miembro"; 
		Haga click en el ícono editar <img border ="0" src ="${images}/edit.gif" width ="16" height ="16">,
		de este tipo de cuenta;
		<li> En la siguiente ventana, vaya a la ventana de los "<a href = "${pagePrefix}account_management#transaction_type_search"><u>
		tipos de transacción </u></a>", y haga click en el botón "Agregar nuevo tipo de transacción" en la parte inferior.<br>
		Pero antes de hacerlo, debe comprobar que actualmente no exista un tipo de transacción para la cancelación de los préstamos disponibles.
		<li> En la siguiente ventana, usted deberá elegir un tipo de cuenta en el cuadro desplegable "A". 
		Esta suele ser la "cuenta de débito", pero debería pensar en particular los tipos de cuenta 
		que el sistema utilizará para el manejo de los préstamos.
		Simplemente complete el resto de los campos, y si es necesario consulte la ayuda del sistema para este módulo.</li>
	</ul>
	<br>
	Luego, usted podrá crear el nuevo tipo de transacción <b><i> para el otorgamiento del préstamo </i></b>:<br>
	<br>
	<ul>
		<li> Una vez más, vaya a la sección de transacciones, "Menú: Cuentas> Administrar cuentas".
		<li> Seleccione el tipo de cuenta que haya especificado antes en el cuadro desplegable "A" (véase más arriba), q
		que suele ser la "cuenta de débito", y haga click en el ícono <img border ="0" src="${images}/edit.gif "width ="16" height = "16"> 
		editar correspondiente a este tipo de cuenta.
		<li> Compruebe que actualmente no exista un tipo de transacción para el otorgamiento de préstamos disponible.
		Haga click en el botón "Agregar nuevo tipo de transacción".
		<li> En la siguiente ventana, usted deberá elegir un tipo de cuenta en el cuadro desplegable "A". 
		Esta suele ser la "cuenta del miembro" que haya utilizado antes, a la hora de crear el tipo de transacción 
		para el pago del préstamo.
		
		Al elegir un tipo de transacción, la casilla de verificación "¿Es un préstamo?"
		ubicada en la parte inferior de esta ventana se hará visible. 
		Marque esta casilla de verificación, y varios nuevos campos (parámetros) se harán visibles. 
		Haga click <a href="#make_loan_type"><u> aquí </u></a> 
		para obtener más detalles, o consulte la ayuda del sistema para este módulo.
	</ul>
	
	<br>
	<br>
	<li><b> Establecer los permisos adecuados: </b> Asegúrese de que los <a
	href = "${pagePrefix}groups#manage_groups"><u> permisos </u></a> 
	para los préstamos están definidos. 
	Administradores y probablemente, los brokers, deberán poseer
	<a href = "${pagePrefix}groups#manage_group_permissions_admin_member"><u>
	permisos </u></a> para la concesión de préstamos, usted puede también si lo desea, establecer los 
	<a href = "${pagePrefix}groups#manage_group_permissions_member"><u> permisos para el miembro </u></a> 
	para ver y reembolsar sus préstamos (y más). 
	Tenga en cuenta que el permiso para pagar un préstamo (por el administrador o miembro) debe establecerse explícitamente. <br>
	Además, es posible que desee establecer los permisos para los <a href="${pagePrefix}loan_groups"><u> grupos de préstamo </u></a>, 
	si desea utilizarlos. <br>
	<br>
	<li><b> Otorgar el préstamo: </b> Para conceder un préstamo, usted puede optar por ir al 
	<a href = "${pagePrefix}profiles"><u> perfil </u></a> del miembro beneficiario, 
	y hacer click en el botón "Otorgar préstamo". <br>
	<br>
	<li> <b> Gestión de préstamos: </b> Los préstamos pueden ser gestionados en el 
	"Menú: Cuentas> Administrar préstamos". <br>
	Los pagos pueden ser gestionados a través del "Menú: Cuentas> Pagos de Préstamo".
</ol>
</span>
<span class="member">
<br><br> <i> ¿Dónde los encuentro? </i> <br>
	Usted puede acceder a sus préstamos a través del "Menú: Cuenta> Préstamos". <br>
	<br>
	Aquí usted puede obtener una visión general de sus préstamos (abiertos y cerrados), 
	y es el punto de inicio para su reembolso (pago).
	</span>
	<span class="broker"> Puede acceder a los préstamos de sus miembros a través del 
	<a href = "${pagePrefix}profiles"><u> perfil de un miembro </u></a>, entre sus acciones,
	encontrará una sección especial para la gestión de préstamos y un botón para "Ver préstamos".</span>
	<hr>
	<span class="admin"> <a name="make_loan_type"></a>
	<h3> Crear un tipo de transacción préstamo </h3>
	(<i> Sugerencia: Pueden existir enlaces a otras secciones de ayuda. 
	Utilice el botón de retroceso para regresar, cuando el navegador no muestre el botón "atrás")</i>
	
	<br><br> Si usted marcó la casilla de verificación "¿Es un préstamo?" significa que el <a
	href = "${pagePrefix}account_management#transaction_types"><u> tipo de transacción
	</u></a> que está creando es un <a href="#loans_top"><u> préstamo </u></a>. 
	Aparecerán nuevos ajustes para los préstamos cuando usted marque esta casilla. <br>
	
	En el caso de un préstamo, a continuación, deberá especificar algunos otros campos. 
	El campo más importante es el primero, el "Tipo de préstamo".
	Al realizar esta primer elección, serán determinados qué otros campos serán visibles y se le solicitarán. <br>
	<br>
	Existen tres tipos de préstamos diferentes,
	disponibles, los campos visibles se discutirán en el marco de cada tipo de préstamo:

<ul>
	<li><b> Pago único: </b> Debe ser pago con anterioridad a una fecha específica.
	Si su préstamo es de este tipo, usted debe especificar los siguientes campos:
	<ul>
	<li><b> Tipo de pago: </b> Ver la explicación un poco más arriba en esta ayuda;

	<li><b> Días de pago por defecto: </b> Este es el período de caducidad, después de este
	período, el préstamo se mostrará como "vencido" en el ventana de préstamos
	del miembro, y la Administración de préstamos de la administración. 
	El miembro se supone que deberá pagar el préstamo antes de su vencimiento.
	</ul>
	<li><b> Pagos múltiples:</b> Los préstamos de este tipo poseen pagos dividos en periódos
	(mensuales), cada pago poseerá su propia fecha de vencimiento. Usted deberá especificar el tipo de reembolso.
	<li> <b> Con cargos: </b> Este es un préstamo que puede tener diferentes tipos de tasas y reembolsos periódicos. 
	<br>
	Los siguientes cargos pueden ser configurados:
	<ul>
	<li> <b> Tasa de interés mensual: </b> Se tratá de un interés (compuesto) y calculado por mes. 
	El importe total del préstamo y otros gastos (intereses, subvención de tasas, etc.)
	son prorrateados de acuerdo al periódo y la cantidad de cuotas del préstamo.
	<li> <b> Tasa de subvención: </b> Esta tasa deberá ser pagada por única vez, y es generada 
	en el momento del otorgamiento del péstamo.	
	Esta cantidad será distribuída (incluída) en todos los pagos periódicos.
	El pago puede ser un porcentaje del importe total del préstamo, o una cantidad fija.
	<li> <b> Tasa de caducidad: </b> Esta es la cantidad fija que se generará cuando
	un pago no se ha efectuado en el tiempo (antes de la fecha de vencimiento) acordado.
	<li> <b> Interés de caducidad: </b> Este es el interés generado por los días de retraso, 
	cuando un pago no se ha efectuado en el tiempo acordado.
</ul>
</ul>
<hr class="help">
</span>

<a name="loan_types"></a>
<h3> Tipos de préstamos </h3>
<br><br> Existen tres(3) tipos de préstamos diferentes disponibles:
<ul>
	<a name="simple_loan"></a>
	<li> <b> Única cuota: </b> <br>
	Este tipo de préstamo posee una única <a href="#component"><u>cuota</u></a>, y por lo tanto una única fecha de vencimiento.<br>
	El miembro puede pagar un préstamo de "única cuota", en uno o en varios pagos. <br>
	Lo único que cuenta es que la totalidad del préstamo debe ser reembolsado (pago) antes de su fecha de vencimiento.<br>
	Si pasada la fecha de vencimiento el préstamo no ha sido reembolsado, pasará a estar en estado "Vencido". </li>
	<br>
	<a name="multiple_payments"></a>
	<li> <b> Múltiples cuotas: </b> <br>
	Este tipo de préstamo, se divide en reembolsos periódicos (cuotas).<br>
	Al conceder el préstamo a los miembros, se puede especificar la fecha del primer pago y
	el número de <a href="#component"><u>cuotas</u></a> del préstamo. <br>
	Cada cuota de préstamo poseerá su propia fecha de vencimiento. <br>
	Cuando el plazo para el pago de una cuota expira, el préstamo se mostrará como "Vencido" 
	en la visión general del miembro y en la sección de gestión de préstamos de la administración.</li>
	<br>
	<a name="with_fees"></a>
	<li> <b> Múltiples cuotas con cargos: </b> <br>
	Este es un préstamo que puede poseer diferentes tipos de tasas y reembolsos periódicos. <br>
	Cada tasa aplicada puede poseer su propio tipo de pago. <br>
	Es igual al anterior tipo de préstamo (múltiples cuotas), pero con mayor cantidad de tasas posibles.</li>
</ul>
<hr class="help">

<span class="admin broker"> <a NAME="loan"></a>
<h3> Otorgamiento de préstamos </h3>
	Con esta función usted puede dar <a href="#loans_top"><u> préstamos </u></a> a un miembro. 
	Para poder dar un préstamo, deben reunirse determinados requisitos, en primer lugar haga click <a href="#loans_top"><u>
	aquí </u></a> para conocerlos.
	
	<br><br> Los siguientes campos deben ser completados al otorgar un préstamo:
	<ul>
	<li> <b> Grupo de préstamo: </b> Esta opción mostrará si el miembro es el
	miembro responsable o parte, de uno o más <a href="${pagePrefix}loan_groups"><u> grupos de préstamo </u></a>. 
	Si no desea otorgar un préstamo a un grupo, pero desea otorgarlo al miembro de forma personal, seleccione la opción "Personal". <br>
	
	<li> <b> Identificador: </b> Este es el nombre con el que el préstamo será identificado. 
	Usted podrá elegir el identificador que desee. <br>
	
	Nota: El campo identificador es un <a href="${pagePrefix}custom_fields"><u> campo personalizado
	del préstamo </u></a> provisto con la base de datos predeterminada. 
	Usted puede eliminar o crear otros campos personalizados para préstamos, respetando determinadas normas. <br>
	
	<li> <b> Tipo de préstamo: </b> Este es el campo más importante en el formulario. 
	Aquí usted puede seleccionar a qué tipo de transacción de préstamo pertenece. 
	Cada uno de estos tipos de transacción implica uno de los tres posibles 
	<a href="#loan_types"><u> tipos de préstamo </u></a>. 
	
	Dependiendo del tipo de préstamo seleccionado, se mostrarán el resto de los campos del formulario.
	A continuación se detallan los <b> campos específicos del tipo de préstamo </b>. <br>
	
		<li> <b> Descripción: </b> Introduzca una descripción para el préstamo; <br>
		<li> <b> Monto: </b> Es la cantidad total que el miembro recibirá en su cuenta. 
		Es el importe inicial o "principal" del préstamo. <br>
		
		<li> <b> Subvención en el pasado: </b> Marque esta casilla si la fecha del préstamo
		<b> NO </b> debería ser hoy, sino una una fecha en el pasado. 
		Si usted marca esta opción, se le pedirá que especifique la fecha en un campo adicional mostrado en la parte superior.<br>
		
		<li> <i> Tipos de campos específicos de préstamo: </i> El resto de los campos del formulario
		dependerán de lo que usted eligió en el  menú desplegable "tipo de préstamo".
		<ul>
		<li> <b> Préstamo simple: </b> Si usted eligió el tipo de "préstamo simple", los siguientes campos serán visibles:
		<ul>
		<li> <b> Fecha de Pago: </b> La fecha en que el préstamo deberá ser reembolsado.
		En esta fecha, se generará una notificación y el préstamo cambiará al estado de "vencido" 
		(en la sección de administración de préstamos).
		</ul>
		<br>
		<br>
		<li> <b> Múltiples pagos de préstamo: </b> Si el tipo de préstamo que usted eligió
		implica "múltiples pagos", los siguientes opciones serán visibles:
		<ul>
			<li> <b> Primera fecha de vencimiento: </b> El pago del préstamo se divide en varias
			"<a href="#component"><u> cuotas </u></a>". 
			Aquí ingrese la fecha de vencimiento de la primer cuota del préstamo, 
			antes de la cual el miembro deberará realizar el pago. 
			 En esta fecha se generará una notificación y el préstamo pasará al estado de "vencido".
			
			<li> <b> Número de pagos: </b> El número de pagos mensuales (cuotas del préstamo).
			<li> <b> Calcular: </b> El botón Calcular mostrará los diferentes pagos del préstamo
			y sus respectivas fechas de vencimiento. 
			Los valores y fechas pueden ser cambiados.
			Si cambia los valores, debe asegurarse de que la suma total de las
			cuotas será el mismo que el importe total del préstamo.
		</ul>
		<br>
		<li><b>Tasas de préstamo: </b> Si el tipo de transacción indica que se trata de un
		"préstamo con tasas", los siguientes campos serán visibles:
		<ul>
		<li> <b> Todos los ajustes y las tasas de interés: </b> Estos campos por encima de la
		"descripción", indican que las tasas que se deben. 
		Sólo son a modo informativo y no se pueden cambiar. 
		Para obtener más información <a href = "#make_loan_type"><u> haga click aquí </u></a>.
		
		<li> <b> Primera fecha de vencimiento: </b> El pago del préstamo se divide en varias
		"<a href="#component"><u> cuotas </u></a>". 
		Aquí ingrese la fecha de vencimiento de la primer cuota del préstamo, 
		antes de la cual el miembro deberará realizar el pago. 
		En esta fecha se generará una notificación y el préstamo pasará al estado de "vencido".
		<li> <b> Número de pagos: </b> El número de pagos mensuales (cuotas del préstamo).
		
		<li> <b> Mostrar: </b> Este botón mostrará las diferentes cuotas del préstamo
		y sus fechas de vencimiento. Esas fechas y valores no se podrán modificar directamente,
		sólo se podrá mediante la modificación de la cantidad total o el número de pagos. 
		A los importes indicados se le incluirán las distintas tasas.
		</ul>
		<br>
	</ul>
</ul>
<br><br>Nota: Es posible crear <a href="${pagePrefix}custom_fields"><u> campos personalizados </u></a> 
adicionales para los préstamos, si es necesario. 
Por ejemplo, un número de contrato de préstamo.
<hr class="help">
</span>

<span class="admin broker"> <a name="loan_confirm"></a>
<h3> Confirmar préstamo </h3>
Esta pantalla simplemente verifica la información sobre el préstamo antes de el mismo sea otorgado.
Revise/compruebe la información y haga click en el botón "Aceptar" para la expedición del préstamo.
<hr class="help">
</span>

<span class="admin"> 
<a NAME="search_loans_by_admin"></a>
<h3> Búsqueda de préstamos </h3>
Esta función permite obtener una visión general de todos los <a href="#loans_top"><u>préstamos</u></a> de miembros en el sistema.<br>
Existen varias opciones de búsqueda. <br>
Tenga en cuenta que un filtro en blanco, devolverá como resultado todos los valores posibles para dicho campo.
<ul>
	<li> <b> Filtro: </b> Esta opción le permite <a href="#status"><u>filtrar préstamos</u></a> por el 
	estado de sus cuotas componentes.<br>
	Para un miembro, el préstamo siempre estará "Abierto" o "Cerrado"; sin embargo el Administrador podrá también filtrar préstamos 
	por el <a href="#statusCuotas"><u>estado de sus cuotas</u></a>.<br>
	
	<li> <b> Tipo de préstamo: </b> Si existe más de un tipo de préstamo, puede seleccionarlo
	en este cuadro desplegable. En este caso, el tipo de préstamo se refiere al 
	<a href = "${pagePrefix}account_management#transaction_types"><u>tipo de transacción</u></a> al que pertenece el préstamo.
	<li> <b> Código de miembro / Nombre: </b> Con esta opción usted puede buscar préstamos para un
	miembro específico. Son campos auto-completables al escribir. </li>
	<li> <b> Código de broker / Nombre: </b> Esta opción le permite buscar préstamos para los
	los miembros relacionados con un <a href = "${pagePrefix}brokering"><u>broker</u></a>. 
	Son campos auto-completables al escribir.
	
	<li> <b> Grupo de préstamo: </b> Con esta opción usted puede buscar los préstamos concedidos
	a un <a href="${pagePrefix}loan_groups"><u>grupo de préstamo</u></a>. 
	Esta opción sólo estará visible si existe grupos de préstamo creados y habilitados en el sistema.
	<li> <b> Número de transacción: </b> Si el número de transacciones se activa en el
	Sistema, usted puede buscar por este número de transacción.
	<li> <b> Identificador: </b> Si la opción es especificada, usted puede buscar por el identificador.
	
	<li> <b> Otorgado (Desde/Hasta): </b> Esta opción permite la búsqueda de préstamos que
	se concedieron dentro de un plazo específico.
	<li> <b> Fecha de vencimiento (Desde y Hasta): </b> Esta opción permite la búsqueda de préstamos que
	vencen dentro de un plazo específico.
	<li> <b> Pago (Desde y Hasta): </b> Esta opción le permite buscar préstamos con
	cuotas que han sido pagadas en un período de tiempo determinado.
	</ul>
	Haga click en el botón "Aceptar" para ejecutar la consulta.
	<hr class="help">
</span>

<a NAME="search_loans_result"></a>
<h3> Resultado de búsqueda de préstamos </h3>
Esta ventana muestra el resultado de su búsqueda de <a href="#loans_top"><u>préstamos</u></a>.<br>
Despliega una lista con la siguiente información (puede que no todas las columnas sean visibles):
<ul>
	<li span class="admin"> <b> Código de miembro: </b> Miembro receptor del préstamo. Haga click en su código para acceder a su 
	<a href="${pagePrefix}profiles"><u>perfil</u></a>.
	<li> <b> Descripción: </b> Descripción del préstamo.
	<li> <b> Monto: </b> Monto total del préstamo en su respectiva moneda.
	<li> <b> Saldo restante: </b> Es el monto total del préstamo que el miembro todavía tiene por pagar, en su respectiva moneda.
	<li> <b> Cuotas: </b> El número de <a href="#component"><u>cuotas</u></a> del préstamo.
	El primer número es la cantidad de pagos ya realizados. 
	El segundo número (después de la barra) es el número total de cuotas del préstamo.
	Este campo no será visible si la lista contiene solamente préstamos simples, sin
	plazos.
	<li> Utilice el ícono <img border ="0" src="${images}/view.gif "width ="16" height = "16"></b> 
	correspondiente para visualizar los detalles del préstamo e información adicional.
</ul>
En la parte superior derecha de la ventana se encuentran otros dos (2) íconos disponibles:<br> 
<br>
El ícono <img border="0" src="${images}/save.gif" width="16" height="16"> exporta la lista de resultados a un archivo en formato 
<a href="#csv"><u>csv</u></a>. <br>
<br>
El ícono <img border="0" src="${images}/print.gif" width="16" height="16"> 
abrirá una página de impresión con los detalles de todos los préstamos obtenidos.
<hr class="help">

<a NAME="search_loans_member_by_admin"></a>
<a NAME="search_loans_by_member"></a>
<a NAME="search_loans_member_by_broker"></a>
<span class="admin broker">
<h3> Búsqueda de préstamos de miembro </h3>
</span>

<span class="member">
<h3> Mis préstamos </h3>
</span>
Esta función le permite obtener una visión general de los <a href="#loans_top"><u>préstamos</u></a>
<span class="admin broker">del miembro</span> en el sistema.<br>
Usted puede visualizar los préstamos en "Estado" <a href="#anyOpen">Abierto</a> o
<a href="#anyClosed">Cerrado</a>, seleccionando el correspondiente botón de opción.<br> 
<br>
La ventana de <a href="#search_loans_result"><u>resultado de búsqueda</u></a>, ubicada en la parte inferior, 
mostrará a continuación los resultados obtenidos.
<hr class="help">

<span class="admin"> 
<a NAME="search_loan_payments"></a>
<h3> Buscar cuotas de préstamos </h3>
Esta página le permite buscar información sobre las cuotas de <a href="#loans_top"><u> préstamos</u></a> existentes,
incluso sobre cuotas que aún no han recibido pagos.<br> 
<br> 
Los siguientes campos estarán disponibles; como siempre, si usted no especifica ningún filtro, 
obtendrá todos los resultados posibles:
<ul>
	<li> <b> Estado: </b> Indica el <a href="#statusCuotas"><u>estado</u></a> actual de la cuota.
	<li> <b> Tipo de transacción: </b> Es el <a href="${pagePrefix}account_management#transaction_types"><u>tipo de transacción</u></a> del préstamo.
	<li> <b> Código de Miembro / Nombre: </b> Código y nombre del miembro prestatario.
	<li> <b> Código de broker / Nombre: </b> Es el código y nombre del	<a href = "${pagePrefix}brokering"><u>broker</u></a> de los prestatarios.
	
	<li> <b> Grupo de préstamo: </b> Con esta opción usted puede buscar cuotas correspondientes a préstamos concedidos
	a un <a href="${pagePrefix}loan_groups"><u>grupo de préstamo</u></a>. 
	Esta opción sólo estará visible si existe grupos de préstamo creados y habilitados en el sistema.
	<li> <b> Indentificador: </b> Es el identificador del préstamo, por parte de su emisor cuando concedió el préstamo.
	
	<li> <b> Fecha de vencimiento (Desde y Hasta): </b> Esta opción permite buscar <a href="#component"><u>cuota</u></a> de préstamos que
	vencen dentro de un plazo específico.
	<li> <b> Pago (Desde y Hasta): </b> Esta opción le permite buscar cuotas de préstamos 
	que han sido pagas en un período de tiempo determinado.
</ul>
Haga click en el botón "Aceptar" para ejecutar la consulta.

<hr class="help">
<a NAME="search_loan_payments_result"></a>
<h3> Resultado de búsqueda de cuotas </h3>
Esta ventana muestra el resultado de la búsqueda de cuotas de préstamos.<br>
<br>
La ventana le mostrará una lista con la siguiente información (puede que no todas las columnas sean visibles):
<ul>
	<li> <b> Código de miembro: </b> Miembro receptor del préstamo. 
	Haga click en su código para acceder a su <a href="${pagePrefix}profiles"><u>perfil</u></a>.
	<li> <b> Fecha de vencimiento: </b> Fecha de vencimiento de la cuota.
	<li> <b> Monto: </b> Cantidad/importe total de la cuota.
	<li> <b> Estado: </b> Estado actual de la cuota.
	<li> <b> Paga: </b> Es la cantidad reembolsada de la cuota, de forma interna (unidades).
	<li> <b> Paga externamente: </b> Es la cantidad reembolsada de la cuota, de forma externa.
</ul>
En la parte superior derecha de la ventana se encuentran otros íconos disponibles:<br> 
El ícono <img border="0" src="${images}/save.gif" width="16" height="16">&nbsp; 
exporta la lista a un archivo en formato <a href="#csv"><u>csv</u></a>. 
<br>
El ícono <img border="0" src="${images}/print.gif" width="16" height="16">
&nbsp; abrirá una página de impresión con los detalles de todos los pagos de préstamos obtenidos.
<hr class="help">
</span>

<a NAME="loan_detail"></a>
<h3> Detalles de préstamo </h3>
Esta página muestra los detalles del <a href="#loans_top"><u>préstamo</u></a> seleccionado.<br>
Dependiendo del tipo de préstamo, se mostrarán los valores correspondientes al préstamo.<br>
<br>
El icono <img border="0" src="${images}/print.gif" width="16" height="16"> 
abrirá una página de impresión con los detalles del préstamo y sus <a href="#component"><u>cuotas</u></a> componentes.
<br> 
<span class = "admin"> 
En algunos estados específicos del préstamo ("vencido" o "en proceso"), 
usted podrá cambiar su <a href = "#status"><u>estado</u></a> 
haciendo click en el botón "Marcar este préstamo como ...". </span>
<hr class="help">

<a NAME="loan_parcels_detail"></a>
<h3> Cuotas </h3>
Esta ventana muestra los detalles sobre el <a href="#loans_top"><u>préstamo</u></a> y sus 
<a href="#component"><u>cuotas</u></a> correspondientes.<br>
<br>
Todas las cuotas del préstamo se enumeran en esta ventana. 
El cuadro es muy sencillo.<br>
Su <a href="#statusCuotas"><u>estado</u></a> puede adoptar varios valores.
<hr class="help">

<span class="admin"> <a NAME="loan_to_members"></a>
<h3> Préstamo para miembros </h3>
Esta página le mostrará una lista de los miembros que pertenecen al 
<a href="${pagePrefix}loan_groups"><u>grupo de préstamo</u></a> del 
<a href="#loans_top"><u>préstamo</u></a> seleccionado. 

El nombre del miembro "responsable" (miembro que recibió el préstamo) se mostrará en rojo. 
Al hacer click sobre los nombres, se le redireccionará a los <a href="${pagePrefix}profiles"><u> perfiles </u></a> 
de los miembros.
<hr class="help">
</span>

<a NAME="loan_repayment_by_admin"> </a>
<a NAME="loan_repayment_by_member"> </a>
<h3> Pago de préstamo </h3>
Esta ventana muestra la información sobre las cuotas del <a href="#loans_top"><u>préstamo</u></a>, 
y brinda la posibilidad de realizar el pago <span class="admin"> o <a href="#discard"><u>pago externo</u></a> de dichas </span> 
<a href="#component"><u>cuotas</u></a>.<br>

<span class="admin"> 
<br>
Si usted selecciona la opción "Fecha de pago", el pago del préstamo podrá ser registrado en una fecha pasada; 
usted deberá especificar dicha fecha en un campo extra.
</span>
<br><br>
Usted podrá pagar cuotas (o préstamos) de forma parcial o total:<br>
<ul>
	<li>Si el préstamo posee una <a href="#simple_loan"><u>única cuota</u></a>, simplemente deberá ingresar el <b>Monto a pagar</b>.
	<br>
	<br>
	<li>Si el préstamo posee <a href="#multiple_payments"><u>múltiples cuotas</u></a> 
	(también incluído para préstamos de "múltiples cuotas con cargos"), se desplegarán los siguientes campos:<br>
	<ul>
		<li><b>Cuota:</b> Se refiere a las cuotas del préstamo, especificadas en el resumen anterior.
		Usted normalmente pagaría la próxima cuota correspondiente (cronológicamente), pero puede optar por pagar la cuota que desee.</li>
		<li><b><i>Monto:</i></b> Monto total de la cuota. </li>
		<li><b><i>Pago:</i></b> Monto ya pago (reembolsado) de la cuota.</li>
		<li><b><i>Remanente:</i></b> Monto pendiente de la cuota.</li>
		<li><b>Monto a pagar:</b> Monto por el cual se realizará el pago.</li>
	</ul>
	</li>
	<br>
	Usted deberá seleccionar el número de <b>Cuota</b> y el <b>Monto a pagar</b>, por el cual realizará el pago.
</ul>
<span class="member"> Haga click en el botón <b>"Pago"</b> para efectuar el pago (de parte) de la cuota.</span>
<span class="admin"> Haga click en el botón <b>"Pago"</b> para efectuar el pago (interno) de la cuota;</span> <br>
<span class="admin">  Haga click en el botón <b>"Pago externo"</b> para efectuar el pago (externo) de la cuota.</span> 
<hr class="help">

<a name="status"></a>
<span class="member">
<h3> Estados de préstamos </h3>
Los estados disponibles son:
<ul>
	<a name="anyOpen"></a>
	<li> <b> Abierto: </b>Significa que un préstamo se encuentra administrativamente "abierto".<br>
	Un préstamo se encuentra abierto si posee al menos una cuota en estado 
	<a href="#openPayment"><u>Abierta</u></a>, 
	<a href="#expired"><u>Vencida</u></a> 
	ó <a href="#inprocess"><u>En proceso</u></a>.
	<br> 
	Son préstamos que aún no han sido reembolsados (pagos) en su totalidad por el miembro.<br>
	<br>
	<a name="anyClosed"></a>
	<li> <b> Cerrado: </b> Significa que un préstamo se encuentra administrativamente "cerrado".<br>
	Un préstamo se encuentra cerrado si NO posee cuotas
	<a href="#openPayment"><u>Abiertas</u></a>, 
	<a href="#expired"><u>Vencidas</u></a> 
	ó <a href="#inprocess"><u>En proceso</u></a>.<br>
	Esto significa que son préstamos cuyas cuotas se encuentran en alguno de los siguientes estados:<br> 
	<a href="#payment"><u>Paga</u></a>, 
	<a href="#external_payment"><u>Paga externamente</u></a>, 
	<a href="#recovered"><u>Recuperada</u></a> ó 
	<a href="#unrecoverable"><u>Irrecuperable</u></a>.<br>
	Son préstamos para los cuales no se efectuarán más pagos.<br>
	<br>
	Por ejemplo, si tenemos un préstamo con 5 cuotas en los siguientes estados:<br>
<br>
<table border="1">
<tr>
	<th style="font-size:12px;">Cuota</th>
	<th style="font-size:12px;">Estado</th>
</tr>
<tr> 
	<td style="text-align:center">1</td>
    <td>Paga</td>
</tr>
<tr> 
    <td style="text-align:center">2</td>
    <td>Paga externamente</td>  
</tr>
<tr> 
    <td style="text-align:center">3</td>
    <td>Recuperada</td>
</tr>
<tr> 
    <td style="text-align:center">4</td>
    <td>Irrecuperable</td>
</tr>
<tr> 
    <td style="text-align:center">5 </td>
    <td>Paga</td>
</tr>
</table>
<br>
El estado del préstamo es <b>Cerrado</b>.<br>
<br><br>
<br>
<table border="1">
<tr style="font-size:12px;">
	<th>Cuota</th>
	<th>Estado</th>
</tr>
<tr> 
	<td style="text-align:center">1</td>
    <td>Paga</td>
</tr>
<tr> 
    <td style="text-align:center">2</td>
    <td>Paga externamente</td>  
</tr>
<tr> 
    <td style="text-align:center">3</td>
    <td>Recuperada</td>
</tr>
<tr> 
    <td style="text-align:center">4</td>
    <td>Irrecuperable</td>
</tr>
<tr> 
    <td style="text-align:center">5</td>
    <td>Abierta/Vencida/En proceso</td>
</tr>
</table>
<br>
El estado del préstamo es <b>Abierto</b>.<br>
<br>
</ul>
</span>

<span class="admin">
<h3> Filtros de préstamos </h3>
Este campo permite filtrar los préstamos por el <a href="#statusCuotas"><u>Estado</u></a> en que se encuentran sus cuotas componentes.<br>
<br>
Los filtros disponibles son:
<ul>
	<li> <b> Todos abiertos: </b> Se filtrarán todos aquellos préstamos que se encuentran administrativamente "abiertos".
    Son incluídos aquellos préstamos filtrados como: <i>Abierto, Vencido y En proceso</i>.<br>
	Son préstamos que aún no han sido reembolsados (pagos) en su totalidad por el miembro.<br></li>
	<li> <b> Todos cerrados: </b> Se filtrarán todos aquellos préstamos que se encuentran administrativamente "cerrados".
	Son incluídos aquellos préstamos filtrados como: <i> Cerrado, Recuperado e Irrecuperable </i>.<br>
	Son préstamos para los cuales no se efectuarán más pagos.<br></li>
	<a name="open"></a>
	<li> <b> Abierto: </b> Préstamo que aún no ha sido reembolsado (pago) en su totalidad.<br> 
	El miembro posee al menos una cuota <a href="#openPayment"><u>abierta</u></a> para el préstamo.</li>
	<a name="closed"></a>
	<li> <b> Cerrado: </b> Todas las cuotas del préstamo han sido 
	<a href="#payment"><u>pagas</u></a> (internamente), 
	o <a href="#external_payment"><u>pagas externamente</u></a>.<br>
	El miembro NO posee más cuotas abiertas para el préstamo.</li>
	<li> <b> Vencido: </b> Préstamo que posee al menos una cuota <a href="#expired"><u>vencida</u></a>.</li>
	<li> <b> En proceso: </b> Préstamo que posee al menos una cuota <a href="#inprocess"><u>en proceso</u></a>.</li>
	<li> <b> Recuperado: </b> Préstamo para el cual al menos una de sus cuotas ha sido <a href="#recovered"><u>recuperada</u></a>.</li>
	<li> <b> Irrecuperable: </b> Préstamo para el cual al menos una de sus cuotas ha sido establecida como <a href="#unrecoverable"><u>irrecuperable</u></a>.</li>
	<li> <b> Autorización pendiente: </b> El pago del préstamo debe ser autorizado. <br>
	Una vez que el pago del préstamo sea autorizado, la transferencia se hará automáticamente.</li>
	<li> <b> Autorización denegada: </b> El pago del préstamo se le ha negado.
	Esto significa que el préstamo será cancelado administrativamente.</li>
</ul>
<br><br>
</span>

<hr>
<a name="statusCuotas"></a>
<h3> Estado de la cuota </h3>
Este campo permite filtrar las cuotas de los préstamos por el Estado en el que se encuentran.<br>
Puede ser uno de los siguientes:
<ul>
<a name="openPayment"></a>
<li> <b> Abierta: </b> Cuota que aún no han sido reembolsada (paga) en su totalidad.<br>
Existen pagos pendientes para esta cuota del préstamo.</li>

<a name="payment"></a>
<li> <b> Paga: </b> Cuota que ha sido reembolsada (paga) en su totalidad mediante pagos internos (unidades).<br>
El miembro no posee más pagos pendientes para esta cuota del préstamo.</li>

<a name="external_payment"></a>
<li> <b> Paga externamente: </b> Cuota que ha sido reembolsada (externamente) por otros medios, 
por ejemplo: bienes o dinero convencional. <br></li>

<a name="expired"></a>
<li> <b> Vencida: </b> La fecha de vencimiento de la cuota ha sido alcanzada y aún no ha sido reembolsada.</li>

<a name="inprocess"></a>
<li> <b> En proceso: </b> Cuando la cuota llega a su fecha de vencimiento, un administrador puede cambiar su estado a "En proceso".
Fundamentalmente esta acción es provocada por una re-negociación del préstamo (cuota).<br>
Luego de dicha negociación, un administrador puede hacer que la cuota pase al estado de "Recuperada" o 
se transforme en "Irrecuperable" (ver estado siguiente).<br>
A esta condición ("En proceso") sólo se puede llegar si se trata de una cuota del préstamo "Vencida". <br>
Esto significa que la cuota está vencida, pero las partes están negociando las acciones a tomar.</li> 

<a name="recovered"></a>
<li> <b> Recuperada: </b> Esto significa que la cuota del préstamo ha sido recuperada. 
A esta condición sólo se puede llegar a partir del estado "En proceso".</li>

<a name="unrecoverable"></a>
<li> <b> Irrecuperable: </b> La cuota del préstamo ha sido considerada como "Irrecuperable".
A esta condición sólo se puede llegar a partir del estado "En proceso".
Significa que esta cuota del préstamo todavía no ha sido reembolsada, pero todas las partes 
involucradas consideran no va a existir ningún pago más por parte del miembro. <br>
La cuota del préstamo se encuentra en una especie de estado "congelado".</li>
</ul>
<br><br>
<hr>


<br><br><a name="glossary"></a>
<h2> Glosario de términos </h2>

<a name="component"> </a>
<b> Cuotas de un préstamo </b><br>
Le llamaremos Cuota a cada uno de los pagos periódicos que deberán efectuarse para el reembolso (pago) de un préstamo.<br>
<ul>
	<li>Un préstamo poseerá una(1) única cuota si se trata de un préstamo de <a href="#simple_loan"><u>única cuota</u></a>.</li>
	<li>Un préstamo puede poseerá varias cuotas si se trata de un préstamo con 
	<a href="#multiple_payments"><u>múltiples cuotas</u></a> o 
	<a href="#with_fees"><u>múltiples cuotas con cargos</u></a>.</li> 
</ul>	
<br>
<a name="reembolso"> </a>
<b> Reembolso </b><br>
Pago o reintegro de un préstamo, o de una cuota de un préstamo.<br>
<br>
<a name="csv"> </a>
<b> Achivo CSV </b><br>
Archivo CSV significa en español "valores separados por comas".<br> 
Este es el formato con el que son descargados los archivos de datos desde diversas ventanas de resultados de búsqueda en Cyclos.<br>
Este formato podrá ser abierto por lo general por un programa de hoja de cálculo, como el Open Office Calc o Microsoft Excel. <br>
También puede procesar el archivo CSV con un editor de texto en combinación con las macros. <br>
Programas como Word o WordPerfect tienen excelentes macros para procesar automáticamente los archivos de entrada 
y mejorar la presentación de los documentos.

<hr class='help'>
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
