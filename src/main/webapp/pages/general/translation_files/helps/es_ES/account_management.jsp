<div style="page-break-after: always;">
<a name="account_management_top"></a>
<span class="admin broker">
<br><br>
Toda la información relativa a las cuentas y transacciones, puede ser accedida a través de las funciones de la Administración de cuentas.

<i>¿Dónde las encuentro?</i><br>
La Administración de cuentas puede ser accedida en el &quot;Menú: Cuentas> Administrar cuentas&quot;.

<hr>

<A NAME="currencies"></A>
<h2>Monedas</h2>
Es posible crear nuevas monedas, y asociar luego dichas monedas a tipos de cuentas del sistema.<br> 
No puede establecerse una moneda por defecto por grupo.

<i>¿Dónde las encuentro?</i><br>
Usted puede acceder a las Monedas a través del "Menú: Cuentas> Administrar monedas".
<hr>

<A NAME="currency_search"></A>
<h3>Monedas</h3>
Esta ventana muestra la lista de monedas existentes en el sistema.
Una moneda puede ser asociada a un tipo de cuenta.<br>
<ul>
<li>Para Agregar una nueva moneda, haga click en el botón "Nueva moneda", ubicado en la parte inferior derecha de la ventana.</li>
<li>Para Modificar una moneda, haga click en el ícono <img border="0" src="${images}/edit.gif" width="16" height="16"> 
correspondiente.</li>
<li>Para Eliminar una moneda, haga click en el ícono <img border="0" src="${images}/delete.gif" width="16" height="16"> 
correspondiente.</li>
</ul>
<hr class="help">

<A NAME="currency_details"></A>
<h3>Modificar moneda / Crear nueva moneda</h3>
Al modificar o crear una nueva moneda, los campos disponibles
son:
<ul>
	<li><b>Nombre:</b> Nombre interno (no siempre es visualizado)
	<li><b>Símbolo:</b> Se mostrará en varios lugares/páginas. Por ejemplo, en el campo precio 
	de un anuncio.
	<li><b>Patrón:</b> Aquí puede ajustar la moneda nombre/símbolo y el lugar donde aparecerá.
	Puede ser colocada delante o después de la cantidad. Para algunas
	monedas (como el dólar) se mostrará el símbolo antes de la cantidad y para otras después. 
	El patrón de la moneda se mostrará en las listas y en los mensajes.<br>
	Los símbolos no son siempre soportados por los navegadores (browsers), un buen ejemplo es el
	símbolo del Euro. En estos casos puede ser conveniente utilizar el símbolo de Unicode.
	<li><b>Descripción:</b> Utilizada para información interna (no siempre es visualizada).
	<li><b>Habilitar ratio-A</b>  
	<li><b>Habilitar ratio-D</b> 
</ul>

<%-- FER RATIOS --%>
<a name="rates"></a>
<h2>Ratios</h2>
Cyclos implementa el modelo de ratios de STRO.<br>
Este modelo le otorga a las unidades (moneda electrónica) un cierto "ratio" o índice, basado en la "edad" de las unidades. <br>
Los ratios generan un promedio cuando las unidades son transferidas entre cuentas.<br> 
Los ratios permiten a la administración penalizar la conversión temprana, 
y hacer más rentable la conversión de las unidades "más antiguas", estimulando que las unidades permanezcan en el sistema por más tiempo.<br> 
En la actualidad existen dos tipos diferentes de ratios:<br> 
<a href="#a-rate"><u>Ratio-A</u></a>, indica la edad desde la creación de las unidades, y <br>
<a href="#d-rate"><u>Ratio-D</u></a>, indica el tiempo restante para la expiración de la garantía que generó (respalda) las unidades.
<br><br>

<span class="admin broker"> 
<i>¿Dónde los encuentro?</i><br>
Los Ratios son habilitados en la <a href="#currencies"><u>moneda</u></a>.<br>
Por lo tanto, para habilitar un ratio en una moneda, debe acceder al 
"Menú: Cuentas> Administrar monedas" y luego hacer click en el 
ícono de Modificación <img border="0" src="${images}/edit.gif" width="16" height="16"> correspondiente.<br>

Para habilitar una tasa de conversión basado en estos ratios, usted debe realizar los siguientes pasos:
<ol>
	<li>Habilitar el ratio en la moneda a través del "Menú: Cuentas> Administrar monedas", 
	y luego hacer click en el ícono Modificación <img border="0" src="${images}/edit.gif" width="16" height="16"> correspondiente.
	<li>Asegúrese de que exista un tipo de transacción para la destrucción de unidades (moneda electrónica), 
	desde la cuenta de miembro a la cuenta de Débito. Esto debe realizarse a través del "Menú: Cuentas> Administrar cuentas", 
	modificando el tipo de cuenta de miembro.
	<li>Asegúrese de que exista un tipo de transacción para la tasa (de conversión) que se cobrará sobre la transacción definida en el punto anterior. 
	Esta tasa la pagará el miembro, y puede tener como destino cualquier cuenta del sistema.
	<li>Usted está en condiciones de crear una nueva tasa de transacción sobre el tipo de transacción para la destrucción de unidades (moneda electrónica). 
	El "tipo de transacción generado" correspondiente a esta nueva tasa es el tipo de transacción del punto anterior. 
	Esta tasa de transacción puede basarse en uno de los ratios existentes.
</ol>
</span>
<hr>

<a name="a-rate"></a>
<h3>Ratio-A</h3>
<a href="#rates"><u>Ratio</u></a>-A es un modelo que permite a Cyclos transferir la "edad" de un conjunto de unidades (moneda electrónica), 
en una transferencia, de una cuenta a otra.<br>
Ratio-A se basa en la fecha de creación de las unidades y simplemente indica el número de días que han pasado desde que las unidades fueron creadas. 
Cada vez que se efectúa un pago, el Ratio-A es transferido, y es mezclado con el Ratio-A de las unidades ya existentes en la cuenta destino/receptora; 
creando así un promedio del Ratio-A. 
Por lo tanto, el Ratio-A indica el número promedio de días desde su creación, para todas las unidades de la cuenta.<br>
Ratio-A se puede utilizar para aumentar una tasa extra sobre una conversión temprana. 
Cuanto más temprano el miembro convierte sus unidades (moneda electrónica) en dinero físico, más alta es la tasa. 
Este tipo de tasa de conversión es independiente de otros costos de conversión, malus, o tasas de conversión basadas en Ratio-D.
<hr class="help">

<a name="d-rate"></a>
<h3>Ratio-D</h3>
<a href="#rates"><u>Ratio</u></a>-D es un modelo que permite a Cyclos transferir la "edad" de un conjunto de unidades (moneda electrónica), 
en una transferencia, de una cuenta a otra.<br> 
Ratio-D se basa en el sistema de garantías, en el que se crean las unidades (moneda electrónica) 
a través de un pago respaldado por una garantía en moneda nacional, que debe ser reembolsado (pago) en una fecha futura. 
El número de días hasta (para alcanzar) esta fecha futura es el Ratio-D del conjunto de unidades (moneda electrónica) recién creada.
Cada vez que se efectúa un pago, el Ratio-D es transferido, y es mezclado con el Ratio-D de las unidades ya existentes en la cuenta destino/receptora; 
creando así un promedio del Ratio-D. 
Por lo tanto, el Ratio-D indica el número promedio de días restantes hasta la expiración/vencimiento de la garantía que respaldó su creación, 
para todas las unidades de la cuenta.<br>
El Ratio-D es utilizado también para calcular los costos de las conversiones tempranas. 
Normalmente, el dinero para la conversión de unidades (moneda electrónica) a dinero físico es obtenido en la fecha de vencimiento de la garantía. 
Si el usuario desea convertir antes, el dinero no estará aún disponible, por lo que la organización deberá pedir prestado el dinero físico 
con su respectivo interés.<br>
El Ratio-D permitirá a la organización calcular estos costos de interés para el miembro. 
Cuanto más temprano el miembro convierte sus unidades en dinero físico, mayores serán los costos de la conversión que deberán ser abonados.
Ratio-D puede ser negativo; cuando la fecha de vencimiento de garantía ha sido alcanzada (y se puede asumir que el reembolso se ha realizado), 
el Ratio-D será negativo.
<hr class="help">

<a name="i-rate"></a>
<h3>Ratio-I</h3>
<a href="#rates"><u>Ratio</u></a>-I 
es un modelo que permite transferir la "edad" de un conjunto de unidades en una transacción, desde una cuenta a otra.<br>
En contraste con el <a href="#a-rate"><u>ratio-A</u></a>, la "edad" no se basa en el tiempo, 
sino en el número de transacciones (iteraciones) que un conjunto de unidades ha sido transferido desde su creación.<br>
Así que un Ratio-I de 4 indica simplemente que esta es la cuarta cuenta donde se encuentran las unidades desde que fueron creadas, 
y han pasado (sido transferidas) cuatro veces de una cuenta a otra.
<br>
Cada vez que un pago es realizado, el Ratio-I es transferido y se mezcla con el Ratio-I de las unidades 
que ya se encontraban presentes en la cuenta receptora, creando así un promedio de Ratio-I. 
Por lo tanto, el Ratio-I indica el promedio de número de transacciones desde la creación, para todas las unidades de la cuenta.
<br>
El Ratio-I no puede ser utilizado para cualquier política tributaria; 
es simplemente un interesante contador que puede ayudarlo a usted a estimar durante cuanto tiempo se encuentran circulando las unidades 
en su sistema antes de su conversión (o destruidas de otra manera).
<br> 
Actualmente, no existen otras acciones posibles habilitadas con Ratio-I.
Futuras versiones de Cyclos implementarán estadísticas sobre Ratio-I. 
Por el momento, usted deberá realizar el análisis del Ratio-I directamente sobre su base de datos.
<hr class="help">

<a name="rate_mix"></a>
<h3>Ratio-A y Ratio-D mixto</h3>
<a href="#a-rate"><u>Ratio-A</u></a> y <a href="#d-rate"><u>Ratio-D</u></a> 
se pueden utilizar en conjunto para determinar una tasa sobre las conversiones tempranas.<br> 
Donde Ratio-D es el número de días restantes hasta el vencimiento de garantía, y Ratio-A es el número de días transcurridos 
desde la creación de las unidades (moneda electrónica); se deduce lógicamente que la suma de Ratio-D y Ratio-A 
es igual a la longitud de período total de la garantía (D + A = G).<br>
Este hecho es utilizado para determinar una tasa para la conversión a dinero físico. 
En este caso, la edad de las unidades relacionada con el período total para expiración de la garantía es utilizada para determinar el porcentaje de la tasa, 
en lugar de utilizar solamente la edad absoluta cuando la tasa es basada en Ratio-A.<br>
Por ejemplo: el sistema puede ser configurado de manera que por cada conjunto de unidades (moneda electrónica), 
la tasa de conversión pueda alcanzar el 3% cuando el 25% del período de la garantía ha expirado, y el 0% cuando el 50% del período ha expirado. 
<br>
Cyclos permite configurar una relación lineal entre la tasa porcentual y la tasa relativa, o una relación de una curva en pendiente hacia una asíntota.
<hr class="help">
</span>


<a name="rate_reinit"></a>
<h3>Reinicialización de Ratio</h3>
Los <a href="#rates"><u>Ratios</u></a> se aplican normalmente a partir del momento en que son habilitados.
Esto significa que en un sistema con transacciones y cuentas existentes de forma previa a la habilitación de los ratios, los ratios serán nulos.
Los ratios nulos no pueden ser utilizados para cálculos, por supuesto. Cyclos utiliza dos soluciones posibles para esto.
<br>
En primer lugar, para <a href="#a-rate"><u>Ratio-A</u></a> y <a href="#d-rate"><u>Ratio-D</u></a> 
usted puede ingresar valores de inicialización en la ventana de <a href="#currency_details"><u>detalles de moneda</u></a>.
Cada vez que se encuentre un ratio nulo, se le asignará el valor de inicialización.
<br>
Un solución más robusta es reinicializar el ratio para una fecha determinada.
Esto significa que todas las transacciones disponibles son utilizadas para recalcular el ratio, 
como si los ratios fueran aplicados en el momento en que se realizan las transacciones originales.
Esta ventana es para la reinicialización de los ratios.
<br>
La reinicialización de ratios es por moneda: sólo son consideradas las transacciones en esa moneda. 
Si usted tiene otras monedas con ratios, debe realizar la reinicialización para esa moneda por separado.
<br>
<br>

<b>Cuándo se utiliza</b><br>
Es posible que usted desee utilizar esta función en dos circunstancias diferentes:
<ol>
	<li>Desea habilitar los ratios para su sistema "retroactivo", es decir,
	desde hace algún tiempo en el pasado. 
	Tenga en cuenta que si habilita <a href="#i-rate"><u>Ratio-I</u></a>, 
	esto, por definición, deber ser habilitado hacia atrás en el tiempo, 
	desde el inicio de su sistema.
	
	<li>Su sistema ha tenido una gran cantidad de <a href="#rates_chargebacks"><u>devoluciones de ratios</u></a>, 
	que se aplicaron para las transacciones relativamente lejos en el pasado. 
	<br>
	
	<i><b>
	Cuanto más transacciones ocurran entre una cuenta a devolver (chargedback) y sus correspondientes devoluciones (cargeback), 
	más dificultades existirán en los cálculos de ratios. 
	</i></b>
	<br>
	La única forma de restaurar esto se ejecutando una reinicialización de ratio.
</ol>
Tenga en cuenta que una reinicialización tiene costos: el sistema debe ser puesto fuera de línea (offline),
y los usuarios pueden encontrarse con cambios en sus ratios 
(sólo en caso de que existan devoluciones involucradas). 
<br>
Para Ratio-I, una reinicialización se aconseja en todo momento antes de ejecutar cualquier estadística relacionada - 
especialmente porque el usuario no notará nada en el caso de cambios en los Ratio-I.
<br>
<br>

<b>Procedimiento</b><br>
Las transacciones son recorridas para recalcular los ratios.
Las siguientes reglas son aplicadas:

<ul>
	<li>Las transacciones son recorridas en orden cronológico.
	Si dos transacciones se produjeron exactamente en el mismo segundo, 
	la transacción con el menor id es procesada primero.
	Tenga en cuenta que en casos excepcionales, este puede no ser el mismo orden en el que 
	las transacciones se llevaron a cabo originalmente. 
	Se puede producir un orden cambiado en el caso de que pagos programados o autorizados 
	sean procesados en el mismo segundo que otros pagos.
	
	<li>Sólo transacciones que son procesadas son consideradas. 
	Transacciones canceladas, o otras transacciones no procesadas son ignoradas.
	
	<li>Transacciones devueltas (chargedback), o transacciones que son devoluciones (chargeback), son ignoradas, 
	son tratadas como si la transacción devuelta originalmente (chargedback) nunca existió.
</ul>
<br>

<b>Elementos de la ventana</b><br>
La ventana solo será visible si cualquier ratio se encuentra habilitado, 
y si no existe en el momento una reinicialización de ratio ejecutándose.
<br>
La ventana contiene los siguientes elementos.

<ul>
<li>La ventana inicia con una visión general de todos los ratios habilitados actualmente.
Utilice las casillas de selección (checkboxes) para marcar el Ratio que desea reinicializar.
Puede marcar más de un tipo por trabajo.

<li><b>Fecha de reinicialización:</b> 
Usted puede ingresar una fecha aquí.
Todas las transacciones desde esta fecha hasta el presente serán reprocesadas para recalcular los ratios.
Si usted no completa una fecha, se tomará la fecha de la primera transacción existente, 
por lo que todas las transacciones serán incluidas.
<br>	
Si usted no reinicializa los ratios desde el principio de la base de datos (es decir, si no mantiene este campo abierto), 
recuerde que los ratios nulos aún pueden ser encontrados durante el proceso de reinicialización del ratio - si los ratios no existían antes de la fecha de inicio.
<br>
En ese caso, los ratios se inicializan en su <a href="#currency_details"><u>inicialización de fecha/valor</u></a>.
Puede ser necesario volver a considerar la configuración del ratio aquí. 
La reinicialización de un ratio-A especial en fechas anteriores al valor de inicialización del ratio-A, 
dará lugar a ratios-A negativos - esto por lo general no tiene sentido. <br>
<br>
Ejemplo: 
<ul>
	<li>Valor inicial de Ratio-A: 0
	<li>Fecha inicial de Ratio-A: 1/1/2008
	<li>Ratio-A reinicializado desde: 1/1/2007
	<li>Primer fecha en Base de datos es anterior: 1/1/2007
</ul> 
Un Ratio-A nulo encontrado el 2/1/2007 ahora tendrá fecha de emisión 1/1/2008, es decir, un Ratio-A negativo de 364 días.


<li><b>Maintain past settings:</b>
The specified rates will be marked as enabled for the complete period between the
given reinitialization date and now. If a rate was already enabled for part of that 
period, then you can choose to keep the original rate parameters as they were then
valid. In such a case, you should check this checkbox. Unchecking the checkbox
means that all specified rates will use the present parameter settings.<br>
<u>Example:</u><br>
Suppose A-rate was enabled on january 1st 2008, with a creation value parameter of 4.
Then suppose it was decided on august 1st 2009 to change that creation value parameter to
the value of 0. This setting is still valid on february 1st 2010. On this day you decide
to reinitialize the A-rate from 2005 up to now.<br>
If you check the &quot;Maintain past settings&quot; checkbox, it means that A-rate will
be enabled from 2005 off. Up to august 1st 2009 the creation value of 4 will be used for
the recalculations; from august 1st 2009 up to now the creation value of 0 will be used
for the recalculations.<br>
If you had the &quot;Maintain past settings&quot; checkbox unchecked, then for the whole
period only the present settings will be used. A-rate will be recalculated with a creation
value of 0 for over the complete period from 2005 up to now. 
</ul>
<br>

<b>Starting the reinitialization job</b><br>
<div class="fieldDecoration">Consider also that reinitialization of rates is a heavy process.
The application will put itself offline if you
start a rate reinitialization.</div>
This is to prevent interferance with new incoming
transfers. After the job is finished it will put itself back online again.
<br>
You can check the progress of the job by refreshing the edit currency window.
A red text below this window will indicate the progress. At the start and
at the finishing of the job a system alert will be send. 
<hr class="help">













<span class="member"> 
<a name="conversion_simulation"></a>
<h3>Simulación de conversión</h3>
Esta ventana le permite simular una conversión, lo que significa que usted puede ingresar una cantidad/monto de su cuenta, 
y visualizar cuanto recibiría en dinero físico en el caso de que usted deseara convertir.
<br>
El formulario está especialmente pensado para simular una conversión en los sistemas que utilizan <a href="#rates"><u>ratios</u></a>, 
ya que en este caso las tasas dependen de los ratios de las unidades (moneda electrónica).<br> 
Sin embargo, se puede utilizar el formulario para la conversión de cualquier tipo de transacción.<br>
Tenga en cuenta que el módulo no controla si usted posee permisos o saldo disponible suficiente; 
sólo calcula lo que se obtendría si se desea convertir el monto ingresado en el formulario.<br> 
Cyclos entonces asume que este monto debería poseer el mismo ratio de su saldo actual. 
Existe entonces un problema si su cuenta posee saldo cero (o menos); véase el comentario ubicado al final de esta sección.
<br><br>
El formulario posee dos versiones: "Simple" y "Avanzada", sin embargo, la versión avanzada sólo tiene sentido si los ratios se encuentran habilitados. 
De lo contrario, sólo la versión simple será mostrada.<br>
Los siguientes elementos pueden ser encontrados en el formulario:
<ul>
	<li><b>Cuenta:</b> Especifique aquí la cuenta del miembro.<br> 
	Las cuentas que no poseen tipos de transacciones aptas y habilitadas (con permisos) para esta funcionalidad, no serán listadas. 
	Si sólo existe un tipo de cuenta disponible, este cuadro de selección no será invisible.

	<li><b>Tipo de transacción:</b> 
	Seleccione el tipo de transacción que se utilizará para la conversión. 
	Si sólo existe un tipo de transacción disponible, este campo será invisible.<br>
	Tenga en cuenta que para cada tipo de transacción, deben configurarse explícitamente los 
	<a href="groups#permissions"><u>permisos</u></a> para que aparezcan en este cuadro de selección.
	
	<li><b>Cantidad a convertir:</b> Ingrese aquí el monto que usted desea convertir. 
	Por defecto el campo mostrará el saldo total disponible para la cuenta seleccionada.  
	
	<li><b>Indices:</b> 
	Solo visible en el formulario “Avanzada”.<br>
	Este cuadro solo estará visible si el tipo de transacción que usted ha seleccionado tiene habilitados la utilización de ratios, 
	es decir: si existen tasas (cargos) en el tipo de transacción que se basan en <a href="#a-rate"><u>Ratio-A</u></a>, 
	<a href="#d-rate"><u>Ratio-D</u></a>, o en ambos.<br>
	El cuadro puede contener los siguientes elementos:
	
	<ul>
		<li><b>Usar tasas actuales:</b> 
		Solo visible en el formulario “Avanzada”.<br>
		Esta casilla de verificación posee (naturalmente) dos posibles valores: 
			<ul>
				<li><b>Seleccionada:</b> 
				Si esta opción es seleccionada, el formulario utilizará el estado actual de su cuenta para el cálculo de las tasas 
				correspondientes a la conversión. Esto significa que los ratios actuales son utilizados para realizar este cálculo. 
				Estos ratios son mostrados en los campos (deshabilitados) a continuación.<br> 
				Los ratios pueden ser extrapolados a una nueva fecha, que deberá ingresarse en el campo fecha. 
				El sistema asume que entre hoy y esa fecha, ninguna transacción adicional de entrada o salida se producirá.<br>
				<i>Ejemplo:</i> El Ratio-D es 10,00. En el campo de fecha usted ingresa el día pasado mañana, o sea en dos días. 
				Como el Ratio-D disminuye en 1 cada día, el sistema utilizará un Ratio-D de 8,00 para el cálculo, 
				siendo Ratio-D como será en la fecha ingresada (con la condición de que existan más transacciones de entrada o salida).
				
				<li><b>No seleccionada:</b> 
				En este caso, usted puede ingresar un Ratio-D o un Ratio-A y descubrir las tasas de conversión resultantes.
			</ul>
			<br>
		
		<li><b>Ratio-D actual:</b> 
		Solo visible en el formulario “Avanzada”.<br>
		Este es el Ratio-D que será utilizado. Si el "Usar tasas actuales" es seleccionado, usted no podrá modificarlo, 
		y será obligatoriamente el Ratio-D actual de su cuenta. De lo contrario, usted puede introducir un ratio.
		
		<li><b>Ratio-A actual:</b> Análogo al Ratio-D. 
	</ul>
	<br>
	
	<li><b>Fecha de conversión:</b> 
	Esta fecha se utilizará como la fecha de conversión. Usted puede utilizar el ícono del Calendario 
	<img border="0" src="${images}/calendar.gif" width="16" height="16">&nbsp; ubicado a la derecha, 
	para realizar su selección. Este campo sólo es visible en el formulario "Simple", 
	o en el formulario "Avanzada" cuando la opción “Usar tasas actuales" se encuentre seleccionada.
	
	<li><b>Graficar en rango de tiempo:</b> 
	Si esta opción es seleccionada, en la ventana de resultados también se desplegará una gráfica 
	mostrando los importes para las tasas para la fecha indicada.
	
	<li><b>Botón Simple / Avanzada:</b> 
	Cuando usted haga click sobre este botón, el formulario cambiará entre las versiones “Simple” y “Avanzada”.
</ul>

Cuando usted haga click sobre el botón Aceptar, los resultados de la conversión se mostrarán en la 
<a href="#conversion_simulation_result"><u>ventana inferior</u></a>.
<br><br>
<u>Observación especial sobre las cuentas con saldo cero o por debajo de cero:</u><br>
Si su cuenta tiene saldo cero o menor a cero, este módulo pierde su sentido. 
El comportamiento por defecto del formulario es calcular la conversión para el monto ingresado, 
dados los ratios correspondientes al saldo actual de su cuenta. 
Pero si usted no posee saldo, su cuenta tampoco tendrá ningún ratio asociado. 
En este caso Cyclos utiliza los valores de creación para los ratios. 
Pero si cualquier cantidad/monto es recibida en su cuenta, los valores de ratio se adaptarán a los valores de ratio de la cantidad de entrada (que ingresa).
<br>
Esto significa que cualquier importe ingresado en el campo cantidad, probablemente le retorne resultados poco realistas 
si usted tiene la cuenta con saldo cero: 
Si usted recibe 10 unidades la próxima semana, el resultado de la conversión probablemente sea muy diferente al resultado 
que obtendrá al introducir 10 unidades en este formulario con saldo cero en su cuenta. 
Si usted realmente quiere tener una idea del resultado que obtendría de una conversión, 
se aconseja utilizar la versión “Avanzada” del formulario, desactivar la casilla de verificación "Usar tasas actuales", 
e introducir los valores esperados para los ratios.
<hr class="help">

<a name="conversion_simulation_result"></a>
<h3>Resultados de simulación de conversión</h3>
Esta ventana muestra los resultados de una <a href="#conversion_simulation"><u>simulación de conversión</u></a>.<br> 
La ventana muestra todas las tasas sobre la conversión, que deber ser pagas por el miembro.
<br><br>
<ul>
	<li><b>Ingreso:</b><br>
	(Note que no todos los campos listados pueden no ser visibles, dependiendo de sus ajustes en la configuración).
	<ul>
		<li><b>Ratio-A aplicada:</b> 
		Este es el <a href="#a-rate"><u>Ratio-A</u></a> calculado para la fecha de conversión. <br>
		Si usted ha ingresado una fecha en el formulario superior, 
		este se recalculará para el actual Ratio-A. Este campo sólo es visible si usted ha utilizado la versión "Avanzada" del formulario.
		
		<li><b>Ratio-D aplicada:</b> Análogo al <a href="#a-rate"><u>Ratio-A</u></a>.
		
		<li><b>Fecha de conversión aplicada:</b> Esta es la fecha que se utiliza para el cálculo. 
		Es la fecha que usted ingresó en la formulario, pero con el tiempo actual anexo.
	</ul>
	<br>
	<li><b>Resultado:</b><br>
	<ul>
		<li><b>Cantidad a convertir:</b> Es el monto original ingresado en el formulario.
		
		<li><b>(Fees):</b> 
		Las siguientes filas en el cuadro, muestran las tasas que se pagan por esta conversión, una fila por cada tasa (cargo). 
		La columna "Monto" es impresa en rojo y entre paréntesis "(...)" si la tasa NO es 
		<a href="#transaction_fee_details"><u>deducida</u></a> del importe total; 
		este tipo de tasas (cargos) deben ser pagas como extra, por encima del monto.<br>
		Existiendo tasas para un tipo de transacción de conversión que NO se deduzcan del monto de la transacción, 
		se consideran un error de configuración; le recomendamos que todas las tasas (cargos) de conversión definidos, 
		sean deducidos del monto de la transacción.
		
		<li><b>Total:</b> 
		Este es el resultado final de la conversión. Dos columnas son desplegadas: 
		<ul>
			<li><b>Columna central:</b> Brinda todas las tasas (cargos) planteadas sobre el monto original de la conversión, expresada en porcentaje. 
			Esto incluye las tasas que no se calculan como un porcentaje (tasa fija). 
			Debido a errores de redondeo, puede diferir de la suma de todos los porcentajes listados por encima.
			
			<li><b>Columna final:</b> Este es el monto final en dinero físico que el miembro recibirá, luego de pagar (deducir) todas las tasas.  
		</ul>
	</ul>
</ul>

<u>Algunos comentarios de alto nivel sobre los resultados</u><br>
Tenga en cuenta que para las fechas en el pasado, Ratios-D son extrapoladas al valor actual. 
Esto significa que por cada día anterior a la fecha actual, el Ratio-D se asume aumentará en uno. 
Sin embargo, esto podría no reflejar la realidad, como Ratio-D posee un valor mínimo. 
Si la cuenta se encuentra durante varios días sobre este Ratio-D mínimo, el verdadero Ratio-D 
no se incrementa en 1 por cada día que se retrocede en el tiempo (permanecen en su mínimo). 
Como una historia exacta del desarrollo del Ratio-D no es almacenada, Cyclos no tiene manera de conocer el actual Ratio-D de la cuenta en el pasado, 
por lo que sólo supone el normal desarrollo numérico en el tiempo.<br>
También tenga en cuenta que para las fechas en el pasado, Cyclos tiene en cuenta las fechas habilitadas de los ratios. 
Si para un ratio en el pasado, Ratio-D o Ratio-A aún no fueran habilitados, se reflejará en el resultado.
Sin embargo, Cyclos no comprueba si la configuración de la tasa (cargo) de la cuenta fue modificada; 
sino que siempre utiliza la configuración actual de la tasa de la cuenta.<br>
Nuevamente, esto se debe a que no es utilizada la historia de las configuraciones de las tasas de cuenta, 
por lo que Cyclos no tiene manera de conocer si los parámetros de una tasa de cuenta en el pasado fueron diferentes (cambiaron).<br>
Además, la fecha en que los ratios fueron habilitados en la moneda, es solo comprobada por la fecha actual solicitada por el formulario. 
Esto no está comprobado para las fechas previas y posteriores a la fecha central que se muestra en el gráfico. 
Esto significa que si los ratios son habilitados en la moneda un día después de la fecha ingresada por usted en el formulario, 
el gráfico seguirá mostrando datos como si los ratios fueron habilitados para todo el rango de fechas ingresado.
<hr class="help">

<A NAME="conversionSimulationResultGraph"></A>
<h3> Curva de montos de cargos vs. tiempo </h3> 
Esta ventana muestra los importes de las tasas a pagar en esta conversión, para la fecha indicada.
<br><br>
El eje vertical muestra los importes de las tasas a pagar. 
Los diferentes tipos de tasas son apiladas unos sobre otros en el gráfico. 
El eje horizontal muestra las fechas alrededor de la fecha de conversión especificada en el formulario, 
que es indicada con una línea vertical de color naranja.
<br><br>
Para visualizar los valores exactos de los puntos de datos, posicione su ratón (mouse) encima de un punto en el gráfico, 
y se mostrará el valor correspondiente.
<hr class="help">
<%-- TERMINA RATIOS FER --%>

</span>

<a name="accounts"></a>
<h2>Cuentas</h2>
Las cuentas en Cyclos pueden ser del tipo &quot;Sistema&quot; o &quot;Miembro&quot;.<br>
Ambos tipos de cuentas están relacionados con una moneda y pueden contener saldos (moneda electrónica),
que pueden ser transferidos hacia y desde otras cuentas (si existe un tipo de transacción que lo permita).<br>
Contrariamente a la cuenta de un miembro, la cuenta de sistema no tiene asociado un propietario.<br>
Los administradores que poseen permisos, pueden realizar pagos desde las cuentas del sistema
hacia otras cuentas del sistema o hacia cuentas de miembros. <br>
<br>
Un miembro puede tener cero, una o más cuentas, y efectuar pagos
entre sus propias cuentas, a cuentas de otros miembros o a cuentas del sistema.

Al crear una nueva cuenta del tipo "Miembro", inicialmente la misma no estará relacionada con ningún miembro.
Usted deberá seguir los siguientes pasos para permitir que los miembros puedan utilizar el tipo de cuenta: 
<ol>	
	<li><b>Asignar la cuenta a un grupo:</b> Sólo después de asignar la cuenta a un grupo, 
	los miembros del mismo podrán tener acceso a la cuenta.<br>
	Esto puede realizarse a través de la <a href="${pagePrefix}groups#manage_groups"><u>configuración de grupos</u></a>.
	
	<li><b>Tipos de transacciones:</b> Antes de que los miembros comiencen a realizar 
	pagos desde la cuenta o recibir pagos en ella, usted deberá crear y asociar sus <a href="#transaction_types"><u>tipos de transacciones</u></a>.<br>
	Esto puede ser realizado a través del cuadro "Tipos de transacción", ubicado en la página de configuración del tipo de cuenta.
	
	<li><b>Permisos:</b> Por supuesto, usted también deberá establecer sus 
	<a href="${pagePrefix}groups#manage_groups"><u>permisos</u></a> correspondientes.	
</ol>
Además de los tipos de transacción, existen otras entidades directamente relacionadas con 
las cuentas, como los préstamos, cargos de transacción y el pago de cargos de cuentas.
<br><br> 
Cyclos posee una base de datos con una serie de 
<a href="#standard_accounts"><u>cuentas estándar</u></a> por defecto, que serán apropiadas para la mayoría de los usuarios/sistemas.
<br><br>
<i> ¿Dónde las encuentro? </i> <br>
Las cuentas pueden ser gestionadas (creadas, eliminadas, modificadas, etc.) a través del: 
"Menú: Cuentas> Administrar cuentas". <br>
<br>
Las cuentas del sistema pueden ser visitadas en "Menú: Cuentas> Cuentas de sistema".
<hr>

<a name="standard_accounts"></a>
<h3> Tipos de cuenta estándar </h3>
Aunque es posible crear toda una nueva estructura de tipos de cuenta, 
por defecto el sistema proporciona una base de datos con valores predeterminados, que son los usados 
habitualmente para la mayoría de los sistemas de monedas complementarias.<br> 

La base de datos por defecto siempre puede ser ampliada con más tipos de cuentas (y tipos de transacciones). 
Hemos creado una cuenta de miembro y varias cuentas de sistema. <br>
Los <a href="#account_fees"><u>cargos de cuentas</u></a> (automáticos/manuales) y los 
<a href="#transaction_fees"><u>cargos de transacción</u></a> están desactivados por defecto,
pero es muy sencillo cambiar sus valores por defecto y activarlos.
<br><br>
La base de datos por defecto contiene las siguientes cuentas del sistema:
<ul>
	<li> <b> Cuenta de débito (préstamos): </b> La cuenta de débito sólo es utilizada
	para los préstamos y el crédito inicial. <br>
	Es la principal cuenta del sistema y se llama cuenta de débito por razones de claridad
	(a veces denominada "cuenta de préstamo", o "cuenta de flotación"). 
	Es común dejar la cuenta de débito como la ÚNICA cuenta que no posee límite negativo. 
	Esta cuenta es necesaria para la creación de "moneda electrónica" (unidades).<br>
	Cuando se crea "moneda electrónica", la misma se transfiere, restándose de la cuenta de débito (saldo negativo) y 
	acreditándose en el receptor (en su mayoría cuentas de miembro) con saldo positivo. <br>
	La administración de la cuenta de débito, por lo tanto, es crítica.<br> 
	Tal vez no tanto en un tipo de sistema LETS, pero para trueque (entre empresas) o para un sistema de software
	respaldado con dinero en efectivo, esta debe ser muy segura y controlada. <br>
	<li> <b> Cuenta de la comunidad: </b> Cuenta que es propiedad de la comunidad, 
	puede recibir el pago de los impuestos (si está configurada) y de las mensualidades de miembros. <br>
	Un administrador puede efectuar un pago desde la cuenta del sistema a la cuenta de un miembro
	(por ejemplo, por el trabajo comunitario realizado por un miembro). 
	Al igual que la cuenta de un miembro, las cuentas del sistema no pueden estar por debajo del límite de crédito. <br>
	<li> <b> Cuenta de vales: </b> La cuenta de vales contiene las unidades (electrónicas) 
	que se han puesto en circulación como vales (unidades físicas). <br>
	Cuando un miembro quiere comprar vales, deberá pagar a la cuenta de vales. 
	La organización puede comprobar el pago y entregar luego los vales. <br>
	Cuando un miembro quiere cobrar los vales, deberá entregar los vales a la organización, 
	y un Administrador realizará el pago, de la cuenta de vales a la cuenta del miembro.
	<br>
	En el caso de un sistema donde las unidades son (parcialmente) devueltas con dinero convencional,
	el vale puede ser vendido por dinero convencional. 
	En este caso no es necesario ser un miembro del sistema. 
	Usted sólo puede comprar vales como una especie de "Bono". En este caso, un administrador deberá 
	hacer un pago de la cuenta de débito a la cuenta de vales. <br>
	
	<li> <b> Cuenta de la Organización: </b> Esta cuenta cumple la función de ser una cuenta 
	adicional para la Organización. Si es necesario, el nombre puede ser cambiado
	de acuerdo a su función (por ejemplo, cuenta social o fondo de inversión).
</ul>
<hr class="help">

<A NAME="account_search"></A>
<h3>Lista de cuentas</h3>
Esta página muestra la lista de todas las Cuentas (y su "Tipo" correspondiente) existentes en el sistema.
Por lo tanto podemos referirnos a ellas como "Tipos de cuentas".
<ul>
	<li>Para Crear una nueva cuenta (de miembro o sistema), haga click en el botón Aceptar correspondiente
	 a la etiqueta "Ingrese una cuenta nueva", ubicado en la parte inferior derecha de la pantalla.</li>
	
	<li>Para Modificar una cuenta, haga click en el ícono <img border="0" src="${images}/edit.gif" width="16" height="16"> 
	correspondiente.</li>

	<li>Para Eliminar una cuenta, haga click en el ícono <img border="0" src="${images}/delete.gif" width="16" height="16"> 
	correspondiente.<br>
	(solo puede eliminarse si no existen transacciones para esta cuenta en el sistema)
	</li>
</ul>
<hr class="help">

<A NAME="account_details"></A>
<h3>Modificar tipo de cuenta / Nueva cuenta</h3>
Esta página le permite crear o modificar un tipo de cuenta.<br>
<br>
Si usted está creando una nueva cuenta, debe especificar si será una cuenta del <b>Tipo</b> "Sistema" o "Miembro".<br>
<br>
Las siguientes opciones están disponibles:
<ul>
	<li><b>Nombre:</b> Nombre de la cuenta. Este se mostrará siempre en la <a href="#account_search"><u>lista de cuentas</u></a> (Administrar cuentas); 
	y si la cuenta es de sistema, también en la lista de <a href="${pagePrefix}payments#account_overview"><u>Cuentas del sistema</u></a>.
	
	<li><b>Descripción:</b> Explicación de la cuenta (solo visible por el administrador).
	<li><b>Moneda:</b> Aquí se puede configurar la <a href="#currencies"><u>moneda</u></a> para la cuenta.
	<li><b>Tipo de límite </b> (sólo disponible para cuentas de Sistema): Una cuenta puede ser ilimitada, lo
	que significa que puede ser indefinidamente negativa (principalmente para cuentas de 
	&quot;débito&quot; o &quot;préstamo&quot;).<br>
	Si la cuenta es limitada, se puede especificar el "Límite mínimo de crédito" y "Límite máximo de crédito". El tipo de
	límite puede ser solamente especificado en la creación de la cuenta (y no se puede modificar).
</ul>
<b>Nota:</b> Muchas configuraciones de las cuentas son específicas de cada grupo de usuarios (por ejemplo el límite de crédito).<br> 
Estas configuraciones pueden ser modificadas en la 
<a href="${pagePrefix}groups#manage_groups"><u>administración de permisos de grupo</u></a>.
<hr>
<A NAME="transaction_types"></A>
<h2>Tipos de transacción</h2>
Cada pago (también llamado transacción) posee un &quot;tipo de transacción&quot;. <br>
El tipo de transacción define el tipo de cuenta origen y destino del pago.<br> 
Si un tipo de cuenta no tiene asociado al menos un tipo de transacción asociado, no es posible realizar pagos. <br>
<br>
Un tipo de transacción debe estar asociado a la cuenta de origen (la cuenta del pagador).


<i> ¿Dónde los encuentro?</i><br> 
Los Tipos de transacción pueden ser definidos y modificados en la ventana de administración de cuentas;
para llegar allí, debe acceder al &quot;Menú: Cuentas> Administrar cuentas&quot;, y hacer
click en el ícono de edición <img border="0" src="${images}/edit.gif" width="16" height="16"> correspondiente al tipo de cuenta deseado.<br> 
<br>
En la siguiente pantalla, se desplegará una ventana (bloque) con la vista de los "Tipos de transacción" asociados a la cuenta.
<hr>

<a name="transaction_type_search"></a>
<h3>Lista de tipos de transacción</h3>
Esta ventana muestra la lista de los tipos de transacción relacionados con la cuenta seleccionada.
<ul>
	<li>Para Agregar un nuevo tipo de transacción, haga click en el botón Aceptar correspondiente
	 a la etiqueta "Agregar nuevo tipo de transacción", ubicado en la parte inferior derecha de la ventana.</li>
	<li>Para Modificar un tipo de transacción, haga click en el ícono <img border="0" src="${images}/edit.gif" width="16" height="16"> 
	correspondiente.</li>
	<li>Para Eliminar un tipo de transacción, haga click en el ícono <img border="0" src="${images}/delete.gif" width="16" height="16"> 
	correspondiente.<br>
	</li>		
</ul>
<br><br>Notas: <br>
Los permisos de un tipo de transacción son definidos por grupo, y pueden ser
configurados en el &quot;Menú: Usuarios & Grupos> Permisos de grupo> editar permisos del grupo.&quot;
<br><br>
Si la cuenta seleccionada es de Miembro, la configuración específica de
la cuenta (límite de crédito, etc.) es definida por grupo. Esta puede también ser modificada
en la sección &quot;Usuarios & Grupos> Permisos de grupo&quot; (ícono Editar - seleccionar cuenta).
<hr class='help'>

<A NAME="transaction_type_details"></A>
<h3>Modificar / Agregar tipo de transacción</h3>
En esta ventana, usted puede configurar las propiedades de un tipo de transacción específico. <br>
Los tipos de transacción tienen los siguientes campos (visibles dependiendo de su tipo y configuración):
<ul>
	<li><b>Nombre:</b> Nombre del tipo de transacción.<br>
	<br>
	<li><b>Descripción:</b> Descripción interna del tipo de transacción. La misma aparece en los detalles de la transacción.<br>
	Nota: En el caso de pagos de préstamos y cargos periódicos (tasas), se deberían utilizar ciertos códigos
	para incluír información específica en la descripción. 
	Usted puede utilizar <a href="#placeholders"><u>"parámetros de sustitución"</u></a>, 
	que serán reemplazados por su correspondiente valor en la descripción de la transacción.<br>
	<br>
	<li><b>Mensaje de confirmación:</b> Este mensaje se mostrará debajo de la
	información de la transacción, en la ventana de confirmación emergente. De esta manera es
	posible configurar mensajes para tipos de transacción específicos.<br>
	<br>
	<li><b>Desde:</b> Tipo de cuenta del usuario que efectúa el pago (pagador).<br>
	<br>
	<li><b>A:</b> Tipo de cuenta del usuario destinatario del pago (cobrador).<br>
	<br>
	<li><b> Miembro de destino fijo:</b> Si usted desea que el destino de
	este tipo de transacción sea siempre el mismo miembro, debe seleccionarlo aquí.<br> 
	Tenga en cuenta este no es un caso común. Es utilizado generalmente cuando existen reglas y/o campos personalizados asociados en el pago 
	a un miembro determinado, y no a un grupo de miembros. <br>
	Si usted posee más de un tipo de transacción (miembro a miembro), puede ocultar
	los pagos utilizando la opción de "prioridad" (se explica más adelante). 
	En el caso de un miembro de destino fijo, puede desear ocultar el pago normal de miembro a miembro, 
	definiendo la configuración de su "prioridad" en blanco.<br>
	<br>
	<li><b>Disponibilidad:</b> Solo para pagos entre cuentas de miembro. <br>
	Esta opción no será visible si sus miembros solo poseen una cuenta para esta moneda. 
	En dicho caso, la casilla de verificación "Habilitado" (próxima opción), será
	mostrada. La disponibilidad define desde donde puede realizarse el pago.
	<ul>
		<li><b>Deshabilitado:</b> El pago es inactivo y no se mostrará en ningún lugar.
		
		<li><b>Pago a otros:</b> Es el tipo de transacción más común. Si la opción
		es seleccionada, el miembro puede usar este tipo de transacción para realizar
		pagos a otros miembros o a cuentas del sistema.
		
		<li><b>Auto pago:</b> Si el miembro posee más de una cuenta (por ejemplo
		cuenta corriente y caja de ahorro), es posible crear una transacción que le permita
		al miembro realizar pagos entre sus propias cuentas. En caso de un autopago, es probable
		que no se desee permitir pagos de la cuenta corriente de un miembro a la caja de
		ahorro de otro. Esto puede lograrse si no se selecciona la casilla de verificación
		de pago directo.
	</ul>
	<br>
	Nota: Los tipos de transacción que son generados automáticamente, como ser cargos de cuenta
	y cargos de transacción, serán siempre cobrados, aunque ninguna opción sea activada en esta ventana de configuración 
	y no sean establecidos los permisos para el grupo de miembros.<br> 
	Lo mismo ocurre con la opción siguiente, la casilla de verificación "Habilitado".<br>
	<br>
	<li><b>Habilitado:</b> Los pagos pueden realizarse con este tipo de transacción, 
	este tipo de transacción es visible en la ventana de pagos y en el resumen de transacciones. <br>
	Note que esta opción depende y está directamente relacionada con la opción anterior "Disponibilidad":<br>
	Si &quot;Disponibilidad&quot; es mostrada, la opción &quot;Habilitado&quot; no lo será, y viceversa.<br>
	<br>
	<li><b>Canales:</b> Esta opción le permite definir los 
	<a href="${pagePrefix}settings#channels"><u>canales</u></a> sobre los cuales este tipo de transacción puede ser utilizado.<br>
	El canal por defecto es &quot;Acceso internet&quot;, pero otros (como SMS) están disponibles.<br>
	<br>
	<li><b>Prioridad:</b> Si esta opción es seleccionada, el tipo de transacción
	tendrá prioridad sobre otros tipos de transacciones. 
	Esto significa que cuando un pago posee más de un posible tipo de transacción, 
	solo los tipos de transacciones con la "prioridad" seleccionada se mostrarán.
	<br>
	Pero si ninguno de los tipos de transacción posee seleccionada la "prioridad", todos se mostrarán.<br>
	El establecimiento de prioridades sólo es utilizado en sistemas relativamente complejos,
	donde a varios grupos de usuarios se les habilita para transaccionar solo bajo determinadas circunstancias con otros grupos.<br>
	Con los grupos y los tipos de transacción es posible establecer un sistema 
	donde diversas comunidades pueden comercializar de forma independiente, pero también entre ellas,
	todo dentro del mismo sistema. <br>
	La prioridad también es utilizada en combinación con el "miembro de destino fijo", explicado anteriormente.<br>
	<br>
	
	<li><b>Monto máximo por día:</b> Es el monto máximo que puede ser pago por un miembro, por día, con este tipo de transacción. 
	Por ejemplo puede ser utilizado para limitar los pagos realizados con teléfonos celulares.<br>
	<br>
	
	<li><b>Monto mínimo:</b> Este es el monto mínimo permitido por pago para este tipo de transacción. 
	Un ejemplo podrían ser los pagos de donaciones que requieren una cantidad mínima.<br>
	
	<li><b>Invocar clase Java:</b> Si es necesario un comportamiento o una funcionalidad específica que no se puede lograr a través de configuración, usted puede crear su propia clase Java. 
	Dicha clase Java se invoca cada vez que un pago con este tipo de transacción se procesa.<br>
	<br>
	
	<b><i>Nota 1:</i></b> 
	La clase Java sólo se invocará en los pagos "procesados". 
	Esto significa que no se invocará cuando los pagos se encuentren programados/agendados para una fecha futura o pendientes de autorización. 
	(sin embargo, una vez que un pago programado o autorizado es procesado, la clase será invocada).
	<br><br>
	
	<b><i>Nota 2:</i></b>
	Asegúrese de que la clase se encuentre disponible en la ruta (path) de clase del servidor, por ejemplo WEB-INF/lib.
	<br><br>
	
	<b><i>Nota 3:</i></b> 
	También es posible invocar una clase Java en todos los tipos de pago/transacción. 
	Esto se puede configurar en <a href="${pagePrefix}settings#local"><u>Configuración local</u></a> - Otros - Clase Java para notificaciones de pagos procesados.
	<br><br>
	
	<li><b>Mostrar transacciones padre/generadas:</b> 
	Algunos sistemas utilizan fuertemente transacciones generadas (cargos).
	Puede ser confuso para los usuarios finales si dichos cargos se muestran en los detalles de la transacción.
	Con esta opción, usted puede definir qué usuarios podrán ver los cargos, en los detalles de la transacción.<br>
	<br>
	Las opciones disponibles son:
	<ul>
		<li>Solamente para administradores,</li>
		<li>Para administradores y brokers,</li>
		<li>Para todos los usuarios.</li>
	</ul>
	<br><br>
	
	<li><b>Permite pagos agendados:</b> 
	Si selecciona esta opción, este tipo de transacción puede ser un pago <a href="${pagePrefix}payments#scheduled"><u>agendado</u></a>.<br> 
	Esto no significa que con solo seleccionar esta opción, los miembros o el administrador puedan con este tipo de transacción agendar/programar pagos.<br> 
	Aún es necesario configurar los permisos correspondientes para el grupo (Ver y Permitir pagos agendados).<br> 
	Por más información consule el manual de: Pagos - Pagos agendados. <br><br>
		<ul>	
			<li><b>Reservar monto total en pagos agendados:</b>
			Cuando esta opción es seleccionada, un miembro sólo podrá agendar/programar pagos si posee el monto total de todas las cuotas en su saldo disponible. 
			El importe total será reservado y no podrá ser utilizado (gastado).<br>
			Para los pagos comunes entre miembros en un sistema comercial, esta configuración seguramente no será utilizada. <br>
			Para los pagos entre empresas y consumidores, por ejemplo, pagos a través de un POS (punto de venta), esta configuración es más comunmente utilizada.
			
			<li><b>Habilitar al pagador la cancelación de los pagos agendados: </b>
			Cuando esta opción es seleccionada, al deudor (usuario pagador) se le permite cancelar los pagos agendados/programados.<br> 
			Tenga en cuenta que, aparte de esta configuración, el grupo del usuario pagador también debe poseer los permisos para cancelar pagos agendados.
			
			<li><b>Habilitar al pagador el bloqueo de los pagos agendados: </b>
			Cuando esta opción es seleccionada, al deudor (usuario pagador) se le permite bloquear los pagos agendados.<br>  
			Tenga en cuenta que, aparte de esta configuración, el grupo del usuario pagador también debe poseer los permisos para bloquear pagos agendados.
			
			<li><b>Mostrar pagos agendados al destinatario: </b>
			Los pagos agendados son iniciadas por el usuario pagador. 
			Para el usuario receptor (destinatario), cada cuota le aparecerá como un pago individual. 
			El usuario receptor no sabrá que las cuotas son parte de un conjunto de pagos.<br> 
			Si usted desea que el usuario receptor del pago agendado, pueda ver los futuros pagos (entrantes) programados, debe seleccionar esta opción.<br> 
			Esta configuración también es más comunmente utilizada en el caso de pagos entre consumidores y negocios, 
			como por ejemplo pagos a través de un POS (punto de venta).<br> 
			Tenga en cuenta que los pagos agendados que son el resultado de una Factura (requerimiento de pago) aceptada, 
			siempre se mostrarán al usuario receptor (emisor de la factura).
			</li>
		</ul>
	<br>
	<li><b>Es conciliable:</b> Si selecciona esta opción, el tipo de transacción será parte
	de la función de conciliación de cuentas del sistema. <br>
	Por más información consulte el archivo de ayuda de 
	<a href="${pagePrefix}bookkeeping"><u>Registro de ingresos y egresos (cuentas externas).</u></a><br>
	<br>
	<li><b>Requiere calificación de transacciones:</b> (solo visible para pagos de miembro a miembro)<br>
	Si esta opción es seleccionada, los miembros pueden calificar este tipo de transacciones. 
	Por más información, consulte la página de ayuda sobre <a href="${pagePrefix}transaction_feedback">calificaciones</a>.<br>
	<br>
	Los siguientes elementos son visibles si la casilla de verificación es seleccionada:<br>
	<br>	
	<ul>
		<li><b>Tiempo límite para la calificación:</b> Cuando un pago es realizado, el usuario pagador posee 
		un período de tiempo máximo en el cual calificar la transacción. Dicho límite se puede configurar a través de esta opción.
		
		<li><b>Tiempo límite para réplica de la calificación:</b> Quien recibe una calificación,
		puede replicarla. El tiempo máximo que tiene para la réplica, se define a través de esta opción.
		
		<li><b>Valor de la calificación cuando fuere vencido:</b> Una vez expirado el tiempo para efectuar la calificación, 
		una por defecto es creada. Aquí usted puede especificar su nivel/valor por defecto (por lo general como "Neutral").
		
		<li><b>Comentario de calificación cuando fuere vencido:</b> Una vez expirado el tiempo para
		efectuar la calificación, una por defecto es creada. Aquí puede especificar el comentario por
		defecto de la misma (por lo general "No se ingresó calificación").
	</ul>
</ul>
<hr class="help">

<a name="placeholders"></a>
<h3>Parámetros de sustitución para campos de descripción</h3>
En el caso de pagos de préstamo y cargos periódicos (tasas), usted podría utilizar ciertos códigos 
para incluír información específica en la descripción de un 
<a href="#transaction_types"><u>tipo de transacción</u></a>. 
Puede utilizar &quot;Parámetros de sustitución&quot;, que serán
reemplazados con su correspondiente valor en la descripción de la transacción. <br>
<br>
<b>Pago de préstamo:</b>
<ul>
	<li><b>#montoPrestamo#:</b> Monto original del préstamo.
	<li><b>#montoTotalPrestamo#:</b> Monto del préstamo más costos (intereses, cargos, tasas, etc).
	<li><b>#montoCuota#:</b> Monto de la cuota del préstamo, para un tiempo determinado. 
	El préstamo es dividido en un número fijo de cuotas a pagar.
	<li><b>#numeroCuota#:</b> Número de cuotas. 
	El préstamo se divide en &quot;cuotas&quot;. Cada cuota tiene su propio número.
</ul>
Por más información sobre préstamos, consulte su <a href="${pagePrefix}loans"><u>ayuda</u></a> correspondiente.
<br><br>
<b><a href="#account_fees"><u>Cargos de cuenta</u></a> (tasa de mensualidad y liquidez):</b>
<ul>
	<li><b>#fecha_comienzo#:</b> Fecha de comienzo (solo para <a href="#account_fees"><u>tasa de liquidez</u></a>).
	<li><b>#fecha_fin#:</b> Fecha de fin (solo para tasa de liquidez).
	<li><b>#impuesto#:</b> Monto.
	<li><b>#baselibre#:</b> Base libre, es el monto básico sobre el que no se aplican cargos
	(solo en el caso de la tasa de liquidez).
	<li><b>#volúmen#:</b> Volúmen total de transacciones (solo en el caso de tasa de liquidez).
	<li><b>#resultado#:</b> Resultado.	
</ul>
<hr class="help">

<br><br><A NAME="payment_fields_list"></A>
<h3> Campos personalizados de pagos </h3>
Es posible agregar campos personalizados a un tipo de transacción (pago), 
de la misma forma que usted establece un campo personalizado para los perfiles de usuarios o de anuncios (de productos o servicios).<br>
<br> 
El campo personalizado del pago, sólo será visible para el tipo de transacción actual (editado por usted en el momento). <br>
Para este tipo de transacción, usted puede:
<ul>
	<li><b> Agregar nuevo campo personalizado:</b> Seleccionando el botón Aceptar correspondiente a la etiqueta "Agregar nuevo campo", 
	usted puede definir un nuevo campo personalizado para el tipo de transacción (pago).</li>
	<li><b> Relacionar campo existente:</b> Seleccionando el botón Aceptar correspondiente a la etiqueta "Relacionar campo existente", 
	usted puede vincularlo con un campo personalizado ya existente.</li>
</ul>
<hr class="help">

<br><br><A NAME="payment_fields_link"></A>
<h3> Relacionar campo existente </h3>
Es común en sistemas complejos, donde las transacciones son "transmitidas" (forwarded).<br>
Esto se realiza normalmente mediante la utilización de un cargo de transacción, que carga el 100% y tiene una
cuenta de terceros como destino.<br> 
En este caso, usted posiblemente desee utilizar el mismo campo personalizado para ambos pagos.<br> 
Usted puede hacerlo creando el campo personalizado del pago en el tipo de transacción origen, y en el segundo (generado) 
tipo de transacción, establecer el vínculo con el campo personalizado anteriormente definido
(mediante la opción "Relacionar campo existente").
</ul>
<hr class="help">
<br><br>
<A NAME="authorized_payment_levels"></A>
<h3>Niveles de autorización</h3>
Esta función le permite definir niveles de 
<a href="${pagePrefix}payments#authorized"><u>autorización</u></a> para un tipo de transacción.
<br><br>
Existen hasta cinco(5) niveles de autorización posibles, y puede existir más de uno por tipo
de transacción, lo que significa que varias personas deberán autorizar un pago (normalmente) por diferentes motivos.<br> 
Tanto el miembro como el autorizador (broker o administrador) tendrán acceso a la lista de pagos pendientes de autorización.<br>
<br><br>
Esta ventana muestra todos los niveles de autorización para el tipo de transacción.<br>
Si no se visualiza ninguno, debe hacer click en "Nuevo nivel de autorización", 
porque deberá definir al menos un nivel para cada tipo de transacción autorizado.<br>
Al agregar el primer nivel, el mismo se mostrará en la lista.<br>
<br>
Si ya fueron definidos niveles, las siguientes opciones están disponibles:
<ul>
	<li>Haga click en el ícono <img border="0" src="${images}/edit.gif" width="16" height="16"> para modificar el nivel de autorización del pago.</li>
	<li>Haga click en el ícono <img border="0" src="${images}/delete.gif" width="16" height="16"> para eliminar el nivel de autorización del pago.</li>
</ul>
<br><br>
Si desmarca la opción de autorización ("Requiere autorización") en el tipo de transacción, 
todas los niveles de autorización definidos para el tipo de transacción serán deshabilitadas.<br>
Los niveles de autorización desaparecen, pero permanecerán en el historial. <br>
Si se vuelve a seleccionar la casilla de verificación en el tipo de transacción, 
los niveles de autorización se volverán a mostrar.
<br><br>
Si una autorización de pago genera otros pagos (por ejemplo un préstamo con cuotas, cargos, tasas e impuestos), 
todo el grupo de pagos puede autorizarse de forma global (y el monto total bloqueado/reservado hasta la autorización).
<hr class="help">

<A NAME="edit_authorization_level"></A>
<h3>Editar niveles de autorización</h3>
En esta ventana usted puede definir los <a href="#authorized_payment_levels"><u>niveles de autorización</u></a>.<br>
Puede definir un máximo de cinco(5) niveles.<br>
<br>
Cada nivel puede poseer un "Autorizador", dependiendo de los tipos de autorizaciones disponibles.
<ul>
	<li><b>Vendedor:</b> Cuando el tipo de autorización es &quot;Vendedor&quot;,
	significa que el miembro que recibe el pago (destinatario) deberá autorizarlo primero. 
	Cuando el receptor autoriza el pago (haciendo click en el botón 
	&quot;Aceptar&quot; desde su lista de pagos pendientes de autorización), 
	el monto es transferido y el estado cambiado de &quot;Pendiente&quot; a &quot;Autorizado&quot;.
	<br>
	Este tipo de autorización es poco común. Sugerimos no utilizarlo si 
	se utilizan <a href="${pagePrefix}invoices"><b>facturas</b></a> en el sistema. 
	Las Facturas ofrecen una funcionalidad similar.
	
	<li><b>Comprador:</b> Esta opción estará disponible solamente si existe definido 
	un primer nivel de autorización del tipo &quot;Vendedor&quot;.<br>
	Ofrece un nivel extra (opcional) de autorización luego del tipo	&quot;Vendedor&quot;.<br>
	Si este tipo es configurado, un pago permanece como pendiente luego de ser autorizado por
	el Vendedor, hasta que el usuario pagador (comprador) autoriza el pago. <br>
	Al finalizar el monto es transferido.
	
	<li><b>Broker/administrador:</b> Este nivel de autorización se configura tanto como de
	primer como segundo nivel luego del tipo Vendedor. 
	Significa que el <a href="${pagePrefix}brokering"><u>broker</u></a> del miembro que realizo el pago, 
	debe autorizarlo, así como opcionalmente el administrador.<br>
	Usted puede seleccionar el grupo de administradores que puede autorizar los pagos, además del broker.<br>
	No hay prioridad en quien puede autorizar, tanto uno como el otro pueden hacerlo.
	
	<li><b>Administrador:</b> Este tipo de autorización está disponible como primer nivel,
	segundo nivel luego de Broker/Administrador, tercer nivel luego del comprador y cualquier otro tipo administrador.<br>
	Esto significa que puede encadenar varios niveles con el nivel de tipo de autorización 
	del administrador, diferentes montos y grupos. Necesitará seleccionar el grupo de
	administradores que pueden autorizar el pago.
</ul>
Se deberá especificar el monto de cada tipo de autorización y nivel. <br>
Si quiere que el pago sea autorizado siempre, puede poner un cero(0) en el campo del monto.
Si pone un monto, digamos 1000, significa que si el miembro paga más de 1000
durante 24 horas, el pago que excede dicho monto deberá ser autorizado. <br>
El monto de un nivel de autorización puede tener el mismo o mayor monto que 
el de su nivel padre.

<hr>
<A NAME="payment_filters"></A>
<h2>Filtros de pago</h2>
Es posible agrupar tipos de transacciones en "Filtros de Pago".<br>
Estos filtros permiten manejar conjuntos de tipos de transacciones, relacionados entre si por determinadas características en común. 
Son utilizados para filtrar y especificar resúmenes/búsquedas de transacciones (información de cuentas) o para la generación de estadísticas.<br>
<br>
Por ejemplo:<br>
Diferentes tipos de mensualidades y otros pagos comunitarios específicos pueden
agruparse en un filtro de pago denominado "Pagos comunitarios". 
Al consultar la información de cuentas, usted puede seleccionarse este filtro de pago 
y solo los movimientos correspondientes a pagos comunitarios serán desplegados.<br>
<br>
Los filtros de pago pueden ser utilizados también para la creación de reportes personalizados. 
Para el administrador, los filtros de pago ofrecen una buena herramienta para mantener
registro de los pagos y generar reportes específicos.

<i> ¿Dónde los encuentro? </i><br>
Los filtros de pago están siempre relacionados a un tipo de cuenta, por lo tanto se 
accede a ellos a través del "Menú: Cuentas> Administrar Cuentas> seleccione un
tipo de cuenta y haga click en el ícono de Edición <img border="0" src="${images}/edit.gif" width="16" height="16">&nbsp;. <br>
Entre otras ventanas ubicadas en la parte inferior, existe una especial correspondiente a los Filtros de pago.
<hr>

<A NAME="payment_filter_search"></A>
<h3>Lista de Filtros de pago</h3>
Esta ventana muestra los <a href="#payment_filters"><u>filtros de pago</u></a> asociados a la cuenta.
<ul>
	<li>Haga click en el ícono <img border="0" src="${images}/edit.gif" width="16" height="16"> para Modificar el filtro de pago.</li>
	<li>Haga click en el ícono <img border="0" src="${images}/delete.gif" width="16" height="16"> para Eliminar el filtro de pago.</li>
</ul>
Si desea Crear un nuevo Filtro de pago, haga click en el botón Aceptar correspondiente a la etiqueta "Agregar nuevo filtro de pago", 
ubicado en la parte inferior derecha de la ventana.
<hr class="help">

	<A NAME="payment_filter_details"></A>
<h3>Modificar / Agregar filtro de pago</h3>
Aquí usted puede modificar o agregar un nuevo <a href="#payment_filters"><u>filtro de pago</u></a>. <br>
Los siguientes campos pueden ser configurados:
<ul>
	<li><b>Nombre</b>: Es el nombre del filtro de pago.
	<li><b>Descripción</b>: Descripción del filtro.
	<li><b>Mostrar en historial de cuenta</b>: Al seleccionar esta opción el filtro de pago 
	será mostrado (estará disponible) en el historial de la cuenta seleccionada.
	<li><b>Mostrar en reportes</b>: Al seleccionar esta opción la función 
	<a href="${pagePrefix}reports"><u>reportes</u></a> incluirá reportes
	sobre este filtro. Mostrará la suma de los montos de todas las transacciones
	que están relacionadas al filtro de pago. También estará disponible pare el módulo de 
	<a href="${pagePrefix}statistics"><u>estadísticas</u></a>.<br>
	Puede crear un filtro de pago solamente para la función de reportes. Para hacer esto
	debe desmarcar la casilla de verificación "Mostrar en historial de cuenta".
	
	<li><b>Tipos de Transacción</b>: Aquí usted debe seleccionar los 
	<a href="#transaction_types"><u>tipos de transacción</u></a> que serán incluidos en el filtro de pago.
	
	<li><b>Filtro de grupo</b>: Aquí usted puede seleccionar los grupos de usuarios que podrán visualizar el filtro de pago. 
	De esta forma es posible crear diferentes filtros para diferentes grupos.<br> 
	Por ejemplo, un grupo de brokers puede tener un filtro "Pagos de comisión".<br>
	Los administradores por lo general necesitan filtros de pago más específicos que los grupos de miembros.
</ul>
<hr>
<A NAME="transaction_fees"></A>
<h2>Cargos de Transacción</h2>
Contrariamente a un cargo de cuenta, un cargo de transacción no puede ser generado manualmente
o de forma programada. Es generado automáticamente cuando otra transacción se produce. 
Por lo tanto un cargo por transacción está configurado "dentro" de otra transacción.<br>
<br>
Un tipo de transacción puede contener uno o más "Cargos de transacción".<br>
Cada vez que un usuario realiza un pago con este tipo de transacción, y posee
un cargo configurado, el cargo es cobrado/ejecutado (dependiendo del criterio en su configuración).<br> 
<br>
Existen diversas formas de calcular el cargo (fijo, porcentaje, etc.) y es posible definir a quien le será cobrado.<br>
<br>
Un ejemplo típico de un cargo de transacción es la "tasa de transacción" aplicada a un pago comercial.<br>
Cada vez que un usuario realiza una transacción (pago) comercial, un cargo es cobrado (si se encuentra configurada).<br>
<br>
Un cargo de transacción es un pago, posee su propio tipo de transacción, 
y por ende será cobrado de forma separada y mostrado como un tipo de transacción diferente en 
el historial de transacciones. 
El detalle de la transacción correspondiente a un cargo, se muestra en el pago original (quien lo genera).

<i> ¿Dónde los encuentro?</i><br>
Un cargo de transacción siempre &quot;pertenece&quot; a un 
<a href="#transaction_types"><u>tipo de transacción</u></a>; por lo tanto la
configuración del cargo de transacción se encuentra dentro de la configuración del
tipo de transacción: "Menú: Cuentas> Administrar cuentas", seleccione
una cuenta (con el ícono editar), vaya a la ventana de tipo de transacción y seleccione
un tipo de transacción (con el ícono editar).
Aquí encontrará una ventana especial con los Cargos de transacción asociados.
<hr>

<A NAME="transaction_fee_search"></A>
<h3>Cargos de transacción</h3>
Esta ventana muestra una lista de los 
<a href="#transaction_fees"><u>cargos de transacción</u></a> asociados al 
<a href="#transaction_types"><u>tipo de transacción</u></a> seleccionado.
<ul>
	<li>Haga click en el ícono <img border="0" src="${images}/edit.gif" width="16" height="16"> para Modificar el cargo de transacción.
	<li>Haga click en el ícono <img border="0" src="${images}/delete.gif" width="16" height="16"> para Eliminar el cargo de transacción.
</ul>
Si desea Crear un uevo cargo de transacción, haga click en el botón 
Aceptar correspondiente a la etiqueta "Agregar nuevo cargo de transacción", 
ubicado en la parte inferior derecha de la ventana.
<hr class="help">

<A NAME="transaction_fee_details"></A>
<h3>Modificar / Agregar cargo de transacción</h3>
En esta ventana usted puede modificar o crear un nuevo 
<a href="#transaction_fees"><u>cargo de transacción</u></a>.
<br>
Un cargo de transacción será generado cuando se produzca una transacción específica.<br>
Sin embargo, los cargos también son transacciones, y por lo tanto necesitan también tener asociado un tipo de transacción.<br> 
Antes de configurar un nuevo cargo de transacción, deberá crear primero el 
<a href="#transaction"><u>tipo de transacción</u></a> correspondiente a dicho cargo.
<br><br>
<b>Campos disponibles:</b>
<ul>
	<li><b>Tipo de transacción</b>: Es el tipo de transacción por el cual el cargo 
	de transacción será generado (invocado).<br>
	<li><b>Nombre</b>: Nombre del cargo de transacción.<br>
	<li><b>Descripción</b>: Descripción del cargo.<br>
	<li><b>Habilitado</b>: El cargo está activo/habilitado cuando esta opción es seleccionada. 
	De lo contrario, el cargo no es cobrado, y el sistema actuará como si no existiera.<br>
	<li><b>Será cobrado a</b>: Aquí puede definir a quien se le cobrará el cargo. 
	Puede ser a quien paga, a quien recibe el pago o a una cuenta del sistema.<br>
	<li><b>Será pagado a</b>: Aquí puede definir a quien se le pagará el cargo. 
	Puede ser a quien paga, a quien recibe el pago o a una cuenta del sistema.<br>
	
	<li><b>Permitir cualquier cuenta</b>: Si esta opción NO es seleccionada, en el próximo campo 
	("Tipo de transacción generado") solo los tipos de transacción correspondientes a la moneda serán mostradas y estarán disponibles.<br> 
	Si selecciona esta opción, no habrá limitante de la moneda en la cual puede ser aplicado el cargo de transacción; 
	todos los tipos de transacción estarán disponibles, aunque sean de otras monedas. <br>
	
	<li><b>Tipo de transacción generado</b>: Aquí se define el tipo de transacción 
	que será generado, por tanto, el tipo de transacción del cargo. <br>
	Es común crear un tipo de transacción específico para el cargo, para luego poder filtrarlo con facilidad.<br>
	Nota: Es absolutamente necesario que exista creado un tipo de transacción de forma previa, para poder crear el cargo de transacción.<br>
	
	<li><b>Tipo de cargo</b>: Aquí se especifica el modo de cálculo del cargo de transacción.<br>
	Las siguientes opciones están disponibles:
	<ul>
		<li><b>Valor fijo</b>: El cargo posee siempre el mismo valor. 
		Usted puede introducir dicho valor en el próxima campo "Cambiar valor".</li>
		<li><b>Porcentaje</b>: El cargo se cobra como un porcentaje del monto de la transacción que lo genera. 
		Usted puede introducir dicho valor en el próxima campo "Cambiar valor". </li>
	</ul>
	<br>
	<li><b>Cambiar valor</b>: Aquí usted debe ingresar el importe o porcentaje del cargo. 
	
	<li><b>Deducción</b>: En este campo se define si el cargo de transacción
	será calculado como un pago extra sobre el pago original (no deducir),
	o será deducido del monto total del pago original (deducir).<br>
	Esto se puede explicar con el siguiente ejemplo:<br> 
	<ul>
	<li><b>Deducir de monto total:</b> Si se realiza un pago de 100 y existe configurado un cargo de 3 a ser deducido del monto total, 
	los siguientes pagos serán generados:
		<ul>
			<li>Transacción principal (original): 97
			<li>Cargo: 3
		</ul>
	</li>
	<li><b>No Deduzca del monto total:</b> El mismo pago de 100 con un cargo de 3 pero no deducido del monto total, genera:
		<ul>
			<li>Transacción principal (original): 100
			<li>Cargo: 3
		</ul>
	</li>
	</ul>
	<br>
	<li><b>Condiciones de aplicabilidad</b>: Aquí se puede definir bajo que condiciones el cargo será aplicado. 
	El cargo se aplicará solamente si se cumplen las condiciones.<br>
	Las condiciones son las siguientes:
	<ul>
		<li><b>Valor mayor o igual a</b>: El cargo se aplica si el monto de la transacción 
		original es mayor o igual al monto especificado.
		
		<li><b>Valor menor o igual</b>: El cargo se aplica si el monto de la transacción 
		original es menor o igual al monto especificado.
		
		<li><b>Desde todos los grupos</b>: En caso de seleccionar esta opción, el cargo se
		aplicará para los miembros de cualquier grupo que son considerados pagadores en 
		el tipo de transacción original. Si quiere que el cargo se aplique solamente para 
		grupos específicos, no seleccione la opción &quot;Desde todos los grupos&quot;, 
		y seleccione los grupos deseados en la lista "Desde grupos" que aparecerá.
		
		<li><b>Para todos los grupos</b>: Funciona de la misma manera que en la opción anterior, pero para miembros
		que son receptores del tipo de transacción original.
	</ul>
</ul>
<hr>
<A NAME="account_fees"></A>
<h2>Cargos de cuenta</h2>
Los Cargos de cuenta son generalmente llamados &quot;mensualidades&quot;,
son periódicamente agendados o ejecutados manualmente por un Administrador.<br> 
Estos cargos están relacionados con una cuenta y pueden ser activados para uno o más grupos 
de miembros. <br>
Cuando se ejecuta un cargo de cuenta, este será cobrado a todos los miembros de los grupos que fueron seleccionados en su configuración.<br>
Sin embargo, aunque la palabra "cargo" lo sugiere y normalmente son utilizados para pagos de los miembros, 
el cargo puede ser configurado para que el pago se realice desde una cuenta del sistema y los miembros sean los receptores/beneficiarios del cargo.<br>
<br>
Un cargo de cuenta típico es un pago mensual de los miembros a la cuenta del sistema (pero puede ser también a la inversa). <br>
Otro ejemplo es la "tasa de liquidez", donde los usuarios pagan por su saldo positivo
en sus cuentas a través del tiempo, una especie de &quot;interés negativo&quot;. 
Su objetivo es que la gente sea estimulada a utilizar su moneda electrónica, transaccionar, 
en vez de mantenerla en su cuenta.


<i>¿Dónde los encuentro?</i><br>
Los cargos de cuenta están asociados a una cuenta, por lo que la configuración
se realiza en la administración de cuentas: &quot;Menú: Cuentas> Administrar cuentas&quot;.<br> 
Seleccione el tipo de cuenta y haga click en el ícono de edición 
<img border="0" src="${images}/edit.gif"width="16" height="16">&nbsp;.
<br><br>
La visión general y administración de los cargos de cuenta se accede a través del bloque 
&quot;Menú: Cuentas> Cargos en la cuenta&quot;. <br>
<hr>

<A NAME="account_fee_overview"></A>
<h3>Cargos de cuenta</h3>
Esta página muestra los <a href="#account_fees"><u>cargos de cuenta</u></a> existentes y habilitados para cada tipo de cuenta.<br>
<br>
Es desplegada la lista con los cargos de cuenta activos (habilitados), la última y próxima fecha de ejecución y su estado ("Ejecutando","Finalizado") correspondiente.<br>
<br>
Los cargos de cuenta pueden ser tanto automáticos (programados) como manuales. <br>
Los manuales solo se ejecutan al hacer click en el enlace &quot;Ejecutar ahora&quot;.<br>
Luego de ejecutarse correctamente el cargo, se marca la fecha de ejecución y se agrega el mismo en el 
<a href="#account_fee_history"><u>historial de cargos de cuenta</u></a> (ventana inferior).
<hr class="help">

<a name="account_fee_history"></a>
<h3>Historial de cargos de cuenta</h3>
Esta ventana muestra todos los <a href="#account_fees"><u>cargos de cuenta</u></a> aplicados históricamente.<br> 
Seleccionando la lupa correspondiente usted puede visualizar los detallas sobre el cargo de cuenta.<br>
<br>
La columna "Estado" puede mostrar la palabra Finalizado (en color azul), lo que significa que el cargo de cuenta se ejecutó con éxito.<br>
En caso de que la palabra Finalizado se muestre en color rojo, significa que se encontraron problemas durante la ejecución del cargo de cuenta.<br> 
Los problemas encontrados se pueden visualizar y corregir en la página de detalles del cargo de cuenta.
<hr class="help">

<a name="account_fee_log_details"></a>
<h3>Detalles de ejecución de cargo de cuenta</h3>
Esta ventana muestra los detalles del cargo de cuenta.<br>
La mayoría de los detalles se explican por sí mismos.<br>
Si el cargo de cuenta es del tipo "Programado", un campo "Período" mostrará la fecha de inicio y fin del período a ser cobrado (mensual, semanal o diario).<br>
En el caso de que el cargo sea programado diariamente, solo se mostrará una Fecha. <br>
<br>
El campo "Monto total cobrado" muestra el monto acumulado total de los pagos, y de las facturas aceptadas (que luego generarán pagos).
Por este motivo el valor del "Monto total cobrado" puede aumentar con el tiempo, a medida que los miembros aceptan facturas.<br> 
Por supuesto, esto sólo sucederá si la opción "Enviar factura" se encuentra habilitada en la 
<a href="#account_fee_details"><u>configuración del cargo de cuenta</u></a>.<br>
<br>
Los diferentes estados (procesado, omitido, cobrado, etc.) se describen en la sección de ayuda del 
<a href="#account_fee_log_member_search"><u>cargo de cuenta (búsqueda de miembros)</u></a> (ver debajo). <br>
Si el campo "Errores" muestra un número (distinto de cero), significa que durante la ejecución del cargo, a uno o más miembros no se le/s imputó correctamente.<br> 
Si esto sucede, un botón se mostrará en esta ventana con el texto "Cobrar"(miembros fallidos). 
Al seleccionar este botón, se les cobrará a todos los miembros fallidos y deberá dejar el cargo de cuenta consistente. 
El número de errores deberá ser cero (0) luego de "cobrar" nuevamente el cargo, y en el 
<a href="#account_fee_history"><u>Historial de cargos de cuenta</u></a> el Estado deberá mostrar "Finalizado" en color azul (y no más en rojo).
<hr class="help">

<a name="account_fee_log_member_search"></a>
<h3>Búsqueda de miembros (cargos de cuenta)</h3>
Aquí es posible filtrar por estado, grupos, y miembros individuales para un cargo de cuenta.<br> 
Existen los siguientes estados:
<ul>
	<li><b>Error:</b>
	Este filtro de búsqueda muestra una lista de todos los miembros a los que NO se les pudo cobrar el cargo, debido a un error.
	Tenga en cuenta que esto no debería suceder.<br> 
	Probablemente se trate de un problema interno de Cyclos, y sería necesario informar/reportar al equipo de Cyclos.<br>
	Hemos proporcionado un mecanismo de error y recarga, con el fin de arreglar los cargos de cuenta, que puedan ser cerrados y 
	y no permanezcan en un estado inconsistente.
	
	<li><b>Procesado:</b> 
	Este estado mostrará todos los miembros procesados.
	Básicamente el estado Procesado incluye todos los otros estados (explicados más adelante), menos el estado de "Error".
	
	<li><b>Omitido:</b> 
	Los miembros con este estado fueron "procesados", pero no se les cobró el cargo de cuenta 
	debido a que el monto del cargo era inferior al mínimo (0,01).
	
	<li><b>Cobrado:</b> Este filtro de búsqueda muestra todos los miembros a los que se les cobró directamente el cargo.
	
	<li><b>Factura:</b> Este filtro de búsqueda muestra todos los miembros a los que se les enviaron Facturas. 
	Por lo tanto muestra la suma de todas facturas "abiertas" y "cerradas". 
	
	<li><b>Factura abierta:</b> 
	Este filtro de búsqueda muestra todas las facturas enviadas a miembros y que no fueron aceptadas (pagas) aún.
	
	<li><b>Factura aceptada:</b>
	Este filtro de búsqueda muestra todas las facturas enviadas a miembros que fueron aceptadas (pagas).
</ul>
<hr class="help">

<a name="account_fee_log_member_list"></a>
<h3>Resultado de búsqueda (cargos de cuenta)</h3>
Esta ventana muestra la lista de miembros resultante de la búsqueda de cargos de cuenta solicitada (en la ventana superior).<br> 
Puede hacer click sobre el código de miembro para acceder a la página del perfil del miembro.<br> 
La columna "Estado" mostrará el estado del cargo de cuenta. 
Si el estado es Cobrado o Factura, usted puede directamente acceder al detalle de la transacción haciendo click sobre el mismo.
<hr class="help">

<A NAME="account_fee_list"> </A>
<h3>Cargos en la cuenta</h3>
Esta ventana muestra la lista de todos los 
<a href="#account_fees"><u>cargos de cuenta</u></a> 
configurados para este tipo de cuenta (habilitados y deshabilitados).
<ul>
	<li>Haga click en el ícono <img border="0" src="${images}/edit.gif" width="16" height="16"> para Modificar el cargo de cuenta.
	<li>Haga click en el ícono <img border="0" src="${images}/delete.gif" width="16" height="16"> para Eliminar el cargo de cuenta.
</ul>
Para Crear un nuevo cargo de cuenta haga click en el botón Aceptar correspondiente a la etiqueta &quot;Ingrese un nuevo cargo&quot;, 
ubicado en la parte inferior derecha de la lista.
<hr class="help">

<A NAME="account_fee_details"></A>
<h3>Modificar / Agregar cargo de cuenta</h3>
En esta ventana usted puede configurar un <a href="#account_fees"><u>cargo de cuenta</u></a> (nuevo o existente). 
<br>
Note que el cargo de cuenta es en sí mismo una transacción; por lo que necesitará un 
<a href="#transaction_types"><u>tipo de transacción</u></a>.<br>
Usted deberá crear un tipo de transacción antes de poder configurar un cargo de cuenta.
<br><br>
Las siguientes opciones de configuración están disponibles:
<ul>
	<li><b>Nombre</b>: El nombre del cargo de cuenta.<br>
	<li><b>Descripción</b>: La descripción del cargo.<br>
	<li><b>Habilitado</b>: El cargo de cuenta será aplicado solamente si esta casilla de 
	verificación es seleccionada. Si el cargo es agendado o manual no importa en este caso:
	el cobro no es posible sin seleccionar esta opción.<br>
	Al finalizar, puede verificar la próxima ejecución en el 
	&quot;Menú: Cuentas> Cargos de cuenta&quot;. <br>
	<li><b>Modo de cobro</b>: Hay cinco(5) formas posibles de cobro:
	<ul>
		<li><b>Monto fijo</b>: El monto fijo puede ser una mensualidad (de un
		miembro a cuenta del sistema) o un pago agendado por única vez de una cuenta
		del sistema a un grupo de miembros.
		
		<li><b>Porcentaje sobre volúmen positivo</b>: Este porcentaje se calcula de la
		misma forma que los intereses. Es más, si se define la dirección del pago (ver abajo) 
		como de &quot;Cuenta de sistema al miembro&quot;, es como el pago de intereses.<br> 
		El miembro recibe en su cuenta el porcentaje calculado. Si la dirección es del &quot;Miembro a
		cuenta del sistema&quot; es como la "tasa de liquidez". Se debita el porcentaje calculado
		de la cuenta del miembro.<br>
		El cálculo del porcentaje se hace sobre un período de tiempo y monto. Es el promedio del
		saldo de la cuenta. Por ejemplo si la cuenta tuvo la mitad del mes 100 unidades, y la otra
		mitad 50, el promedio es de 75 unidades. El porcentaje se calcula sobre dicho promedio y no
		sobre el saldo de la cuenta al momento de ejecutarse el cargo.
		
		<li><b>Porcentaje sobre volúmen negativo</b>: Trabaja de la misma forma que el
		anterior pero sobre volúmen negativo.
		
		<li><b>Porcentaje sobre saldo positivo</b>: El cargo de porcentaje sobre saldo
		NO calcula el volúmen (tiempo + saldo) sino el saldo solamente. Cuando se ejecuta
		el cargo (manual o automáticamente), el cargo considerará solamente el saldo al momento
		del cobro. Esto significa que si en el ejemplo anterior el miembro pasa a tener un
		saldo de cero justo antes de que se ejecute el cargo no se le va a cobrar nada. <br>
		El porcentaje sobre saldo puede causar un comportamiento antinatural en un sistema.
		
		<li><b>Porcentaje sobre saldo negativo</b>: Trabaja de la misma forma que el 
		anterior pero sobre saldos negativos.
	</ul>
	<br>
	Note que la forma de cobro no puede ser cambiada sobre cargos existentes, solamente
	puede ser configurada en nuevos cargos de cuenta.<br>
	<br>
	<li><b>Tolerancia</b>: Esta opción es visible solamente si en el valor de 
	&quot;Forma de cobro&quot; fue seleccionado &quot;Volúmen positivo&quot;. <br>
	La tolerancia es una configuración que se utiliza para evitar que los miembros
	que reciben un pago del sistema (como el préstamo) y lo gastan en poco tiempo
	(ya sea convirtiendo o comprando algo) se les cobre tasa de liquidez sobre ese
	tiempo y monto. Por ejemplo si un miembro recibe 100 unidades y las gasta dentro
	de 24 hrs, al miembro no se le cobra tasa de liquidez si la tolerancia está 
	configurada para ser de 24 horas. Si en el mismo caso, el miembro gasta el dinero
	en 48 horas, se le cobra solo por las 24 hrs que se pasa del período de tolerancia
	(se le cobra solamente por 24 hrs y no por 48).<br>
	
	<li><b>Origen-destino del pago</b>: Define la dirección de pago del cargo de cuenta,
	ya sea de "Miembro al sistema" o en sentido contrario ("Sistema a miembro").<br>
	
	<li><b>Tipo de transacción generado</b>: El cargo de cuenta necesita un tipo de
	transacción. La transacción será del tipo de transacción elegido.<br>
	<li><b>Monto</b>: En caso de seleccionar &quot;Monto fijo&quot; en el &quot;Modo de cobro&quot;, 
	este será el monto que se cobrará. De lo contrario, será el porcentaje a cobrar sobre el volúmen o el saldo.<br>
	
	<li><b>Base libre</b>: Esta configuración no se mostrará en caso de haber seleccionado
	&quot;Monto fijo&quot; en el &quot;Modo de cobro&quot;. El porcentaje del cargo se
	calculará solamente por encima del monto ingresado en la base libre.<br>
	
	En el ejemplo anterior se aplicaría:
	<ul>
		<li><b>Modo de cobro:</b> &quot;Porcentaje sobre volúmen positivo&quot;
		<li><b>Monto:</b> 1%
		<li><b>Base libre:</b> 40
		<li><b>Volúmen en la cuenta del miembro:</b> 100 Unidades durante el mes entero
	</ul>
	<br>
	En este caso sin base libre (el valor de base libre en 0), el cargo a cobrar sería de
	1 unidad; con la base libre, las primeras 40 unidades no se cuentan para el cálculo, por
	lo que se cobrarán 0.60 unidades (1% de 100 - 40).<br>
	<br>
	<li><b>Enviar factura</b>: Esta opción es visible solamente si está seleccionado 
	&quot;Miembro a sistema&quot; en "Origen-destino del pago".<br>
	Esto determina que sucede si se le cobra a un miembro que no posee suficiente crédito para pagar el cargo.<br>
	Las siguientes opciones están disponibles:
	<ul>
		<li><b>Solo cuando el miembro no tiene crédito suficiente:</b> En este caso solo
		los miembros sin saldo suficiente reciben la factura; al resto se le cobra el cargo.
		
		<li><b>Nunca (las cuentas del miembro pueden quedar negativas):</b> En este caso se le cobra a todos los
		miembros, aunque no tengan saldo suficiente.
		
		<li><b>Siempre (no cobrar al miembro automáticamente):</b> En este caso no se le
		cobra nunca el cargo a los miembros, y se les manda siempre la factura, sin importar
		si están llegando al límite de su crédito o no.
	</ul>
	<br>
	
	<b>Nota</b>: Usted deberá crear un tipo de transacción para el pago de miembro a sistema relacionado con la factura.<br>
	Si usted no quiere que este pago sea visualizado por los miembros y administradores como un posible pago (manual), 
	puede definir el tipo de transacción como inhabilitado, manteniendo sin marcar la opción "habilitado". 
	El miembro puede aceptar facturas, pero miembros y administradores no pueden otorgar permisos para realizar un pago manual con este tipo de transacción.
	<br>
	
	<li><b>Modo de ejecución</b>: Aquí puede definir si el cargo de cuenta será agendado o ejecutado manualmente. 
	Note que no se puede cambiar esta opción una vez creado el cargo, es solamente para nuevos cargos.<br>
	Si se ejecuta manualmente, un administrador necesita iniciar el cobro del cargo desde
	la ventana de <a href="#account_fee_overview"><u>cargos de cuenta</u></a>. <br>
	Si el modo es &quot;Agendado&quot;, el cobro se realiza automáticamente al momento configurado. <br>
	En ese caso se presentan las siguientes opciones extra:
	<ul>
		<li><b>Recurrencia</b>: Es el tiempo de intervalo entre que se ejecuta el cargo.<br>
		Por ejemplo: todos los meses del año.<br>
		Por ejemplo: si aquí se configura como &quot;3&quot; &quot;meses&quot;, el cargo
		se ejecuta cada tres meses.<br> 
		Si se pone &quot;0&quot;, el cargo de cuenta se ejecuta solo una vez.
		
		<li><b>Día</b>: El día del mes o semana en que el cargo de cuenta se ejecutará. <br>
		Esto es por supuesto visible solamente si el período de intervalo es diario.
		
		<li><b>Hora</b>: La hora (1-24) en que el cargo se ejecuta.
	</ul>
	<br>
	<li><b>Grupos</b>: Aquí se seleccionan los grupos que pueden generar o recibir el cargo de la cuenta.
</ul>
<hr class="help">

<A NAME="credit_limit"></A>
<h3>Límite de crédito personal</h3>
<br><br>
<i>¿Dónde lo encuentro?</i><br>
El límite de crédito personal puede ser accedido a través de las
<a href="${pagePrefix}profiles#accounts_actions"><u>acciones de cuenta de miembro</u></a> 
(ventana ubicada bajo el perfil del miembro).
<br><br>
Con esta función, usted puede establecer el límite de crédito individual. <br>
En el momento en que una nueva cuenta de miembro es creada, el miembro recibirá el límite de crédito por defecto, 
definido en la <a href="${pagePrefix}groups#manage_groups"><u>configuración de grupos</u></a>.<br>
<br>
En esta ventana se pueden sobreescribir los límites de crédito del grupo. Se puede definir el
límite inferior y superior particular del miembro, para todas sus cuentas. <br>
<br>
Cuando el miembro alcance el límite inferior, no podrá realizar más pagos.<br>
El límite superior es una función no utilizada frecuentemente. Cuando el miembro alcanza dicho límite,
no podrá recibir más pagos. El usuario pagador recibirá un mensaje de notificación al respecto. <br>
Una excepción es el pago correspondiente a una mensualidad (pago agendado), en la que el miembro recibe moneda electrónica. 
Estos pagos son permitidos siempre.

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