<div style="page-break-after: always;">
<a name="guarantees_top"></a>
<br><br>

Una <b>Garantía</b> es una fianza, aval, seguro de crédito, fondo de garantía o cualquier otro instrumento idóneo de garantía, 
librado por una Institución acreditada (emisor de garantías) por la Red.<br>
Las Garantías se utilizan para la obtención de crédito.<br>


<a name="guarantee_types_list"></a>
<a name="guarantee_types"></a>
<h2> Tipos de garantías </h2>
Toda Garantía en el sistema pertenece a un único Tipo de Garantía. 
Es a través de los Tipos de Garantías que se configuran los parámetros que serán 
tomados en cuenta al momento de trabajar con las Garantías.

<br><br>
Los tipos de garantías son utilizados como plantillas para la creación de garantías.


<i>¿Dónde los encuentro?</i><br> 
Usted puede acceder a los tipos de garantías a través del "Menú: Garantías> Tipos de garantías".<br>
<hr>

<a name="guarantee_models"></a>
<h3> Modelos de tipos de garantías </h3>
Los Tipos de Garantías pueden ser de tres modelos:<br>
<ul>
	<li> <b>Con obligación de pago: </b>
		Los Tipos de Garantías con este modelo están asociados a las Garantías que se generan automáticamente a raíz 
		de la aceptación de un lote de Obligaciones de Pago por parte de un vendedor.</li><br>
	<li> <b>Con comprador: </b>
		Los Tipos de Garantías con este modelo están asociados a las Garantías registradas manualmente por parte de un administrador.</li><br>
	<li> <b>Con comprador y vendedor: </b> 
		Los Tipos de Garantías con este modelo están asociados a las Garantías registradas manualmente por parte de un administrador.</li>
</ul>

<span class="admin">
<a name="autorizadores_garantias"></a>		
<h3> Autorizadores  </h3>
Los Tipos de Garantías pueden configurarse con diferentes autorizadores dependiendo del modelo al que pertenecen: <br>

<ul>
	<b>	<li> Modelo “Con obligación de pago” </b></li> 
	<ul>
		<li> Emisor: luego de que el emisor autoriza una garantía (asociada a un Tipo de Garantía configurado de esta forma) 
		la misma pasa directamente al estado Aceptado.</li>
		
		<li> Emisor y Administrador: ídem al punto anterior pero la Garantía pasa al estado Pendiente por Administrador.</li>
	</ul>
	<br>
	<b>	<li> Modelos “Con comprador” y “Con comprador y vendedor” </b></li>
	<ul>
		<li> Emisor: ídem al modelo anterior </li>
		<li> Emisor y Administrador: ídem al modelo anterior </li>
		<li> Administrador: luego de que el administrador autoriza una garantía (asociada a un Tipo de Garantía configurado de esta forma)
		la misma pasa directamente al estado Aceptado. </li>
		<li> Ninguno: luego de que el administrador registra una nueva garantía en el sistema ésta pasa automáticamente al estado Aceptado.	</li>
	</ul>
	</ul>
</ul>

<a name="edit_guarantee_type"></a>
<a name="agregarTipoGarantia"></a>
<a name="modificarTipoGarantia"></a>
<h3> Modificar tipo de garantía / Nuevo tipo de garantía </h3>
En este formulario usted puede modificar un tipo de garantía existente, o crear uno nuevo.<br> 
<br>
Para <b> Modificar un tipo de garantía </b>, usted deberá: <br>

<ul>
<li> Primero, seleccionar el ícono de edición <img border="0" src="${images}/edit.gif" width="16" height="16"> correspondiente;</li><br>
<li> Y luego hacer click en el botón "Modificar", de otro modo los campos no estarán habilitados para su modificación.</li>
</ul>

Cuando termine, haga click en el botón "Aceptar" para confirmar el tipo de garantía.<br>
<br>
Los campos disponibles son:
<ul>
	<li> <b> Nombre: </b> Nombre del tipo de garantía.<br> 
	<li> <b> Moneda: </b> Moneda manejada por el tipo de garantía.
	<li> <b> Modelo: </b> Es el <a href="#guarantee_types_list"><u>modelo</u></a> utilizado por el tipo de garantía:
	<ul>
		<li>Con obligación de pago</li>
		<li>Con comprador</li>
		<li>Con comprador y vendedor</li>
	</ul>
	<br>
	<li> <b> Habilitar: </b> Esta casilla de verificación, permite habilitar o deshabilitar el tipo de garantía.
	<li> <b> Autorizado por: </b> Indica quien o quienes deberán autorizar las garantías de este tipo, las opciones son:<br>
		<ul>
			<li> Emisor </li>
			<li> Administrador </li>
			<li> Emisor y administrador </li>
			<li> Ninguno </li>
		</ul>
	<br>
	<li> <b> Período de respuesta para garantías pendientes:</b> 
	Es el período de tiempo (días calendario o corridos) dentro del cual un emisor (o el administrador) debe autorizar o rechazar 
	una <a href="#guarantees"><u>Garantía</u></a> en estado "Pendiente por Emisor/Administrador".<br> 
	Excedido el mismo, la Garantía pasa automáticamente al estado "Sin acción".<br>
	Por ejemplo: si para una Garantía se define un "período de respuesta para garantías pendientes" de tres (3) días, 
	esta pasará al estado de "Sin acción" a partir del cuarto día corrido.
		
	<li> <b> Período entre obligaciones de pago:</b> 
	Es el período de tiempo máximo dentro del cual deben encontrarse los vencimientos de las
	<a href="#payment_obligations"><u>Obligaciones de pago</u></a> 
	seleccionadas por parte del vendedor (lote), para ser aceptadas.<br>
	Este período aparecerá y es utilizado únicamente para el modelo "Con obligación de pago".
	
	<li> <b> Tasa de crédito: </b> Valor estipulado para la <u>tasa de crédito</u> correspondiente a las garantías de este tipo.
		<br> Puede ser: <br>
		<ul>
			<li> un valor fijo </li>
			<li> un porcentaje </li>
			<li> un porcentaje anual </li>
		</ul>
		<br>
		Si selecciona la casilla de verificación "Sólo lectura", dicho valor no podrá ser modificado.<br>
	<br>
	<li> <b> Tasa de emisión: </b> Valor estipulado para la <u>tasa de emisión </u> correspondiente a las garantías de este tipo. 
		<br> Puede ser: <br> 
		<ul>
			<li> un valor fijo </li>
			<li> un porcentaje </li>
			<li> un porcentaje anual </li>
		</ul>
		<br>
		Si selecciona la casilla de verificación "Sólo lectura", dicho valor no podrá ser modificado.<br>
	<br>
	
	<li> <b> Descripción: </b> Descripción del tipo de garantía.
	
	<li> <b> Tipos de transacción: </b> 
	Aquí se indican los tipos de transacciones involucrados y generados por este tipo de garantía.<br>
	Los tipos de transacciones a especificar son los correspondientes a:
		<ul>
			<li><b><u>Tasa de crédito:</u></b> Tipo de transacción correspondiente al pago de la Tasa de crédito generada. </li>
			<li><b><u>Tasa de emisión:</u></b> Tipo de transacción correspondiente al pago de la Tasa de emisión generada. </li>
			<li><b><u>Préstamo:</u></b> Tipo de transacción correspondiente al Préstamo generado por la Garantía. </li> 
			<li><b><u>Traspaso:</u></b> Tipo de transacción correspondiente al Traspaso del préstamo, desde la cuenta del comprador a la cuenta del vendedor. 
			Este tipo de transacción sólo será solicitado para el modelo "Con obligación de pago".</li>
		</ul>
</ul> 

Para <b> Crear un nuevo Tipo de garantía </b>, usted deberá seleccionar el botón Aceptar correspondiente a la opción 
"Nuevo tipo de garantía" ubicado en la parte inferior derecha de la ventana.

<h3> Notificaciones </h3>
No se envían notificaciones para el caso de Tipos de Garantías.

<br>
<br>
</span>

<hr>
<a name="certifications"></a>
<h2> Certificaciones </h2>
<br><br>
Las Certificaciones son una herramienta necesaria para permitir al comprador publicar 
<a href="#payment_obligations"><u>Obligaciones de pago</u></a>.<br>

Cyclos permite que sus miembros posean Certificaciones, las cuales serán generadas y emitidas por los Emisores de garantías.<br>
Los miembros certificados tendrán la posibilidad de publicar <a href="#payment_obligations"><u>Obligaciones de pago</u></a> 
a sus proveedores.<br>

No pueden existir en el sistema dos Certificaciones en estado "Activo" emitidas por el mismo emisor, 
para el mismo comprador y en la misma moneda.<br>
No es posible crear una Certificación en la cual todo su período de validez sea anterior a la fecha actual.<br>
Si se crea una Certificación en la cual la fecha de inicio y fin es igual a la fecha actual, la misma queda registrada en el estado "Activo".<br>


<i>¿Dónde las encuentro?</i><br> 
Usted puede acceder a las Certificaciones a través del "Menú: Garantías> Certificaciones".<br>
<hr>

<a name="new_certification"></a>
<a name="edit_certification"></a>
<h3> Ver / Nueva Certificación </h3>
De poseer los permisos correspondientes, al ingresar a través del "Menú: Garantías> Certificaciones", 
usted podrá crear una nueva Certificación.<br>
Para ello, haga click en el botón "Nueva certificación", ubicado en la parte inferior izquierda de la ventana de búsqueda.<br> 
<br>
De poseer los permisos correspondientes, usted también podrá "Suspender" o "Activar" sus Certificaciones publicadas.<br>
<br>

Los datos solicitados/desplegados son:
<ul>
	<li><b> Tipo de garantía: </b> <a href="#guarantee_types_list"><u>tipo de garantía</u></a> para la nueva certificación.</li>
	<li><b> Usuario del comprador: </b> código de usuario al cual se le publicará la certificación.</li>
	<li><b> Nombre del comprador: </b> nombre del usuario al cual se le publicará la certificación.</li>
	<li><b> Validez (desde/hasta): </b> plazo de validez de la certificación.</li>
	<a name="certifitacion_amount"></a>
	<li><b> Monto:</b> El monto de una certificación es el monto (máximo) inicial que tiene un comprador para que puedan aceptarle 
			Obligaciones de pago. <br>
			A medida que el comprador publica Obligaciones de pago y éstas van siendo aceptadas por los vendedores, 
			el monto disponible se va reduciendo. En la misma medida, cuando el comprador va realizando pagos sobre las garantías generadas, 
			el monto disponible va aumentado y en caso de pagar todas las garantías, llegará como máximo al monto definido para la certificación.<br>
			Para calcular el monto disponible de una certificación son tomadas en cuenta las garantías en estado 
			<i>Aceptado</i> y <i>Pendiente</i> (por administrador ó por emisor).</li> 
</ul>
Luego de completar los datos requeridos, presione el botón "Aceptar" para confirmar la nueva Certificación.<br>
   
<!--
<a name="edit_certification"></a>
<h3> Modificar Certificaciones </h3>
De poseer los permisos correspondientes, usted sólo podrá "Suspender" o "Activar" sus Certificaciones publicadas.<br>
-->

<a name="certifications_search"></a>
<h3> Buscar certificaciones </h3>
Las Certificaciones existentes, podrán ser buscadas con los siguientes filtros:<br>
<ul>
	<li> <b>Estado</b> de la certificación a buscar: </li>
	<ul>
		<li>Activa</li>
		<li>Cancelada</li>
		<li>Suspendida</li>
		<li>Vencida</li>
		<li>Agendada</li><br>
	</ul>
	<li> <b>Usuario del emisor:</b> es el código de usuario del emisor de garantías.</li>
	<li> <b>Nombre del emisor:</b> es el nombre del emisor que emite la certificación.</li>
	<li> <b>Inicio (desde y hasta):</b> son las fechas de inicio de la certificación.</li>
	<li> <b>Expira (desde y hasta):</b> son las fechas de expiración de la certificación.</li>
</ul>
Luego de completar los filtros deseados, presione el botón "Búsqueda"; debajo de la ventana aparecerán los resultados obtenidos.
<br>

<a name="certifications_search_results"></a>
<h3> Resultado de búsqueda de certificaciones </h3>
Esta página muestra la lista de las Certificaciones resultantes de la búsqueda. 
<ul>
	<li><b> Estado:</b> es el <a href="#statusC"><u>estado</u></a> en que se encuentra la certificación.</li>
	<li><b> Nombre del comprador:</b> es el usuario beneficiario de la certificación.</li>
	<li><b> Nombre del emisor:</b> es el usuario emisor de la certificación.</li>
	<li><b> Período (desde/hasta):</b> es el período de vigencia/validez de la certificación.</li>
	<li><b> Monto:</b> es el <a href="#certifitacion_amount"><u>monto</u></a>(tope) de la certificación emitida.</li>
	<li><b> Monto utilizado:</b> esta columna muestra la suma de los montos 
			de todas las garantías que están asociadas a la certificación, esto es, todas las garantías 
			generadas debido a las Obligaciones de pago que fueron aceptadas por parte de un vendedor.
			Para calcular este monto son tomadas en cuenta sólo las garantías en estado <i>Aceptado</i>.</li>
 </ul>
Para visualizar los detalles de una certificación, seleccione el ícono de Visualización 
<img border="0" src="${images}/view.gif" width="16" height="16"> 
correspondiente.<br>


<a name="certification_logs"></a>
<h3> Detalle de cambios </h3>
En esta ventana, además de mostrarse los datos de la Certificación deseada, 
se despliega en su parte inferior un cuadro con el detalle de sus correspondientes cambios de 
<a href="#statusC"><u>estado</u></a> (trazabilidad).<br> 


<a name="statusC"></a>
<h3> Estados de Certificaciones </h3>
La forma de alcanzar los diferentes estados de una Certificación puede deberse 
a una acción manual (realizada por un usuario) o automática (realizada a través de una tarea de rutina del sistema).

<ul>
	<li><b>Agendado</b></li>
	<ul>	
		<li>Manual</li>
		<ul>	
			<li>Si el emisor crea una nueva Certificación en la cual la fecha de comienzo es posterior a la fecha actual.</li>
			<li>Si el emisor activa una Certificación en estado Suspendido y la fecha de comienzo es posterior a la fecha actual .</li>
		</ul>
	</ul>
	<li><b>Activo</b></li>
	<ul>	
		<li>Manual</li>
		<ul>
			<li>Si el emisor crea una nueva Certificación en la cual la fecha de comienzo es anterior o igual a la fecha actual.</li>
			<li>Si el emisor activa una Certificación en estado Suspendido y la fecha de comienzo es anterior o igual a la fecha actual.</li>
		</ul>
		<li>Automático</li>
		<ul>
			<li>Si se alcanzó la fecha de comienzo de una Certificación en estado Agendado.<l/i>
		</ul>
	</ul>
	<li><b>Suspendido</b></li>
	<ul>
		<li>Manual</li>
		<ul>
			<li>Si el emisor suspende una Certificación en estado Activo.</li>
			<li>Si el emisor suspende una Certificación en estado Agendado.</li>
		</ul>
		<li>Automático</li>
		<ul>	
			<li>Si se alcanzó la fecha de comienzo de una Certificación en estado Agendado pero ya existe 
			otra Certificación en estado Activo para la terna: moneda ,emisor, comprador.</li>
		</ul>
	</ul>
	<li><b>Vencido</b></li>
	<ul>
		<li>Automático</li>
		<ul>	
			<li>Si se alcanzó la fecha de finalización de una Certificación en estado Activo.</li>
			<li>Si se alcanzó la fecha de finalización de una Certificación en estado Suspendido.</li>
		</ul>
	</ul>
	<li><b>Cancelado</b></li>
	<ul>	
		<li>Manual</li>
		<ul>	
			<li>Si el Administrador cancela una Certificación en alguno de los siguientes estados: Activo, Suspendido o Agendado.</li>
		</ul>
	</ul>
</ul>

<h3> Notificaciones </h3>
En el caso de las Certificaciones, se envían las siguientes notificaciones:<br>
<ul>
	<li> Al comprador cuando una nueva Certificación es creada y se encuentra en el estado Activo.</li>
	<li> Al comprador cuando una Certificación existente cambia a alguno de los siguientes estados: Activo, Suspendido, Vencido o Cancelado.</li>
	<li> Al emisor cuando una Certificación existente cambia al estado Vencido.</li>
</ul>

<hr>
<a name="guarantees"></a>
<h2> Garantías </h2>
<br><br>


<i>¿Dónde las encuentro?</i><br> 
Usted puede acceder a las Garantías a través del "Menú: Garantías> Garantías".<br>
<hr>

<a name="guarantee_register"></a>
<h3> Registrar garantía </h3>
Las Garantías pueden ser registradas en el sistema de dos formas:

<ul>
	<li> De forma Manual por un administrador; </li>
	<li> De forma Automática por el sistema, a partir de la aceptación de una o varias (lote) 
	<a href="#payment_obligations"><u>Obligaciones de pago</u></a>.</li>
</ul>
Sólo las garantías registradas manualmente pueden ser borradas del sistema por el administrador que las creó, 
sí y solo sí, cumplan la condición de que se encuentren en estado "Pendiente por Administrador" y sea éste además, 
el único estado por el que han pasado (que no vengan de estar "Pendiente por Emisor").<br>
<br>
Luego de que una garantía es "Aceptada", será generado el préstamo sólo cuando se alcance la fecha de comienzo de la misma 
(realizado por una tarea de rutina del sistema).<br>
<br>
El registro manual se realiza a través del "Menú: Garantías> Garantías".<br>
De poseer los permisos correspondientes, usted podrá generar una nueva Garantía.<br>
Para ello, en el menú desplegable "Registrar garantía" ubicado en la parte inferior izquierda de la ventana de búsqueda, 
deberá seleccionar el tipo de garantía al que pertenecerá la nueva Garantía.<br>
<br>
Los datos solicitados son:
<ul>
	<li><b> Usuario del emisor:</b> código de usuario del miembro emisor de la garantía.</li>
	<li><b> Nombre del emisor: </b> nombre de usuario emisor de la garantía.</li>
	<li><b> Usuario del comprador: </b> código del usuario al que se le emite la garantía.</li>
	<li><b> Nombre del comprador:</b> nombre del usuario al que se le emite la garantía.</li>
	<li><b> Identificador:</b> es un código único que identifica la operación para el usuario emisor.</li>
	<li><b> Validez: </b> plazo de validez de la garantía.</li>
	<li><b> Tasa de crédito:</b> valor de la tasa de crédito a aplicar.<br>
			Este campo no será modificable en el caso de que haya sido definido como de "sólo lectura".<br>
			Su valor puede ser:
		<ul>
			<li> un valor fijo </li>
			<li> un porcentaje </li>
			<li> un porcentaje anual </li>
		</ul></li>
		<br>
	<li><b> Tasa de emisión: </b> valor de la tasa de emisión a aplicar.<br>
			Este campo no será modificable en el caso de que haya sido definido como de "sólo lectura".<br>
			Su valor puede ser:
		<ul>
			<li> un valor fijo </li>
			<li> un porcentaje </li>
			<li> un porcentaje anual </li>
		</ul></li>
		<br>
</ul>
Luego de completar los datos requeridos, presione el botón "Aceptar" para confirmar la nueva Garantía.<br>
Será mostrado un completo detalle de la nueva Garantía, así como las posibles acciones a realizar con la misma.<br>

<a name="guarantees_search"></a>
<h3> Búsqueda de garantías </h3>
Esta opción posibilita buscar y visualizar la información correspondiente a las Garantías existentes.<br>

Los filtros disponibles para la búsqueda de Garantías son:
<ul>
	<li><b> Estado: </b> es el <a href="#statusG"><u>estado</u></a> de las garantías a buscar.</li> 

	<li><b> Usuario/Nombre del emisor: </b> son los datos identificativos del usuario emisor de las garantías.</li> 

	<li><b> Usuario/Nombre del comprador: </b> son los datos identificativos del usuario comprador de las garantías.</li> 

	<li><b> Usuario/Nombre del vendedor: </b> son los datos identificativos del usuario vendedor.</li> 

	<li><b> Inicio Desde/Hasta: </b> son las fechas de inicio de las garantías a buscar.</li> 

	<li><b> Expira Desde/Hasta: </b> son las fechas de expiración de las garantías a buscar.</li> 

	<li><b> Monto Desde/Hasta: </b> es el monto de las garantías a buscar.</li> 
</ul>
Para realizar la "Búsqueda", haga click en el botón correspondiente; debajo serán desplegados los resultados.<br>

<a name="guarantees_search_results"></a>
<h3> Resultado de búsqueda de garantías </h3>
Esta página muestra la lista de las Garantías resultantes de la búsqueda.<br>
<br>
Para ingresar y visualizar los detalles de una Garantía, haga click en el ícono 
correspondiente a su visualización <img border="0" src="${images}/view.gif" width="16" height="16">. <br>  

<a name="guarantee_details"></a>
<h3> Detalle de garantía </h3>
Esta opción permite visualizar los detalles de una garantía.<br>
Los campos mostrados son los siguientes:<br>
<ul>
	<li>Estado</li>
	<li>Tipo de garantía</li>	
	<li>Identificador</li> 	
	<li>Monto</li>
	<li>Nombre del emisor</li> 	
	<li>Nombre del comprador</li> 	
	<li>Fecha de registro</li> 	
	<li>Validez Desde y Hasta</li>
	<li>Tasa de crédito (%)</li> 
	<li>Tasa de emisión (%)</li>
	<li>Tasa de crédito cobrado</li> 
	<li>Tasa de emisión cobrado</li>
</ul>

<a name="guarantee_logs"></a>
<h3> Detalle de cambios </h3>
En esta ventana, además de mostrarse los datos de la Garantía deseada, 
se despliega en su parte inferior un cuadro con el detalle de sus cambios de 
<a href="#statusG"><u>estado</u></a> (trazabilidad).<br> 

<a name="statusG"></a>
<h3> Estado de garantías </h3>
La forma de alcanzar los diferentes estados de una Garantía puede deberse a una acción manual (realizada por un usuario) 
o automática (realizada a través de una tarea de rutina del sistema).
<ul>
	<li><b> Pendiente por Emisor </b></li> 
		<ul>
			<li>Manual</li>
			<ul>
				<li> Si el administrador registra una nueva garantía, cuyo tipo está configurado para que deba ser autorizada 
				por un emisor solamente o por un emisor y un administrador. </li>
			</ul>
			<li>Automático</li>
			<ul>
				<li> Si se aceptó, por parte de un vendedor, un lote de Obligaciones de Pago.</li>
			</ul>
		</ul>
	<li><b> Pendiente por Administrador </b></li> 
		<ul>
			<li>Manual</li>
			<ul>
				<li> Si el administrador registra una nueva garantía, cuyo tipo está configurado para que deba 
				ser autorizada por un administrador solamente.</li>
				<li> Si un emisor autorizó una garantía en estado Pendiente por Emisor y el tipo está configurado para 
				que sea autorizada por un emisor y un administrador.</li>
			</ul>
		</ul>
	<li><b> Aceptado </b></li> 
		<ul>
			<li>Manual</li>
			<ul>
				<li> Si el administrador registra una nueva garantía, cuyo tipo está configurado para que no deba ser autorizado.</li>
				<li> Si el administrador acepta una garantía en estado Pendiente por Administrador.</li>
				<li> Si el emisor acepta una garantía en estado Pendiente por Emisor.</li>
			</ul>
		</ul>
	<li><b> Rechazado </b></li> 
		<ul>
			<li>Manual</li>
			<ul>	
				<li>Si el emisor no autoriza una garantía en estado Pendiente por Emisor.</li>
				<li>Si el administrador no autoriza una garantía en estado Pendiente por Administrador.</li>
			</ul>	
		</ul>
	<li><b> Cancelado </b></li> 
		<ul>
			<li>Manual</li>
			<ul>	
				<li>Si un administrador cancela una garantía en estado Pendiente por Emisor.</li>
				<li>Si un administrador cancela una garantía en estado Pendiente por Administrador.</li>
				<li>Si un administrador cancela una garantía en estado Aceptado para la cual aún no se le ha generado el préstamo.</li>
			</ul>
		</ul>
	<li><b> Sin acción </b></li> 
		<ul>
			<li>Automático</li>
			<ul>
				<li>Si se excedió el tiempo de respuesta del emisor para una garantía en estado Pendiente por Emisor.</li>
				<li>Si se excedió el tiempo de respuesta del administrador para una garantía en estado Pendiente por Administrador.</li>
			</ul>
		</ul>
</ul>

<a name="guarantee_authorization"></a>
<h3> Autorización de garantías </h3>
En esta sección se explica quiénes y de qué forma se realizan las autorizaciones de Garantías.<br>
<br>
<i>¿Quién o quiénes deben autorizar una Garantía?</i><br>
Dependerá de la configuración del <a href="#guarantee_types"><u>tipo de garantía</u></a> correspondiente,<br> 
del <a href="#guarantee_models"><u>modelo</u></a> al que pertenece, y específicamente de 
los usuarios definidos como autorizadores <br>(en el campo "Autorizado por").<br>
<br>
<i>¿Cómo autorizar una Garantía?</i><br>
<ul>	
	<li> Acceda a través del "Menú: Garantías> Garantías"
	<li> Realice una "Búsqueda", con el fin de localizar la Garantía a autorizar <b>(*)</b>
	<li> Haga click en el ícono de visualización <img border="0" src="${images}/view.gif" width="16" height="16"> 
		 correspondiente a la Garantía a autorizar<br>
	<li> Serán desplegados los detalles de la garantía y su trazabilidad:</li>
	<ul>	
		<li>Haga click en el botón Autorizar, si desea autorizar la Garantía</li>
		<li>Haga click en el botón Denegar, si desea denegar la Garantía</li>
	</ul>
</ul>
<b>(*) Importante:</b> Para la búsqueda de Garantías, tenga en cuenta que usted deberá <br>
especificar el "Tipo de garantía" deseado.  	
<br>

<h3> Notificaciones </h3>
En el caso de las Garantías, se envían las siguientes notificaciones:
<ul>
	<li> Al emisor cuando una nueva Garantía es creada y se encuentra en el estado Pendiente por Emisor.</li>
	<li> Al emisor cuando una Garantía existente cambia a alguno de los siguientes estados: <br>
	Sin acción (se excedió el tiempo de respuesta permitido) o Cancelado (por un administrador).</li>
	<li> Al comprador cuando una Garantía existente cambia a alguno de los siguientes estados: Aceptado, Rechazado o Cancelado.</li>
	<li> En el caso de Garantías con un Tipo de Garantía de modelo diferente a “Con comprador”, 
	se envían las mismas notificaciones al vendedor que en el punto anterior.</li>
	<li> Al administrador cuando una nueva Garantía es creada y se encuentra en el estado Pendiente por Administrador.</li>
</ul>

<hr>
<a name="payment_obligations"></a>
<h2> Obligaciones de pago </h2>
<br><br>
Una Obligación de pago es un medio a través del cual las grandes o medianas empresas (compradores), 
públicas o privadas, facilitarán a sus proveedores (vendedores) el respaldo que necesitan para obtener 
una <a href="#guarantees"><u>Garantía</u></a> y poder de esta forma acceder al crédito para capital de giro/trabajo.<br>
<br>
El "comprador" le publica Obligaciones de pago al "vendedor".<br>
<br>
Los miembros de la Red que posean <a href="#certifications"><u>Certificaciones</u></a>, 
podrán emitir una o varias Obligaciones de pago a sus proveedores. 
Para que se puedan crear Obligaciones de Pago debe existir en el sistema al menos una Certificación activa emitida para el comprador.<br>
<br>
Una Obligación de Pago sólo puede ser borrada por el comprador que la creó, y además debe encontrarse en el estado "Registrado".<br>
Si se crea una Obligación de pago en la cual su fecha máxima de publicación es igual a la fecha actual, 
no se permitirá su publicación (la fecha máxima de publicación debe ser posterior a la fecha actual). 
Esta fecha máxima de publicación puede ser anterior o igual a la fecha de vencimiento de la Obligación de pago.


<i>¿Dónde las encuentro?</i><br> 
Usted puede acceder a las Obligaciones de pago a través del "Menú: Garantías> Obligaciones de pago".<br>
<hr>

<a name="register_payment_obligation"></a>
<h3> Publicar nueva Obligación de pago</h3>
La empresas certificadas (compradores), podrán emitir una o varias Obligaciones de pago a sus proveedores (vendedores).<br>
<br>
Para publicar una nueva obligación de pago:
<ul>	
	<li> Acceda a través del "Menú: Garantías> Obligaciones de pago"
	<li> Haga click en el botón <b>Nueva obligación de pago</b>, ubicado en la parte inferior izquierda de la ventana de búsqueda
</ul>
Los datos solicitados son:<br>
<ul>
	<li><b>Usuario/Nombre del vendedor:</b> son los datos identificativos del usuario receptor (vendedor) de la obligación de pago a emitir.
	<li><b>Usuario/Nombre del comprador:</b> son los datos identificativos del usuario emisor (comprador) de la obligación de pago a emitir.
	<li><b>Fecha de Vencimiento:</b> es la fecha de vencimiento de la obligación de pago.
	<li><b>Publicado hasta:</b> es la fecha hasta la que estará publicada la obligación de pago a emitir.
	<li><b>Monto:</b> es el monto de la obligación de pago a emitir.
	<li><b>Descripción:</b> es la descripción de la obligación de pago a emitir.</li>
</ul>

Luego de insertar la información requerida, presione el botón Aceptar para "guardar" la nueva obligación de pago.<br>
Será mostrado un mensaje indicando que la obligación de pago fue guardada con éxito y se desplegará una nueva ventana, 
donde se mostrará su información, el detalle de sus cambios y las posibles acciones a realizar sobre la misma:<br>
<ul>
	<li> Si presiona el botón <b>Borrar</b> y su correspondiente confirmación, la obligación de pago será eliminada del sistema<br>
	<br>
	<li> Si presiona el botón <b>Modificar</b>, se mostrarán los datos de la obligación de pago y usted podrá modificarlos; 
		 presionando el botón Aceptar para su confirmación<br>
	<br>
	<li> Si presiona el botón <b>Publicar</b>, se mostrará un mensaje indicando la acción realizada,
		 se incrementará esta acción al detalle de los cambios, de modo tal que la obligación de pago 
		 pasará a estar “publicada” y quedará a la espera de que el proveedor (vendedor) la acepte  <br>
	<br>
	<li> Si presiona el botón <b>Ocultar</b>, la obligación de pago volverá a su estado anterior (Registrada), 
		 y podrá ser modificada, eliminada o publicada nuevamente. <br>
</ul>

<a name="payment_obligations_search"></a>
<h3> Buscar obligaciones de pago </h3>
En esta página, usted puede buscar las Obligaciones de pago existentes.<b>(*)</b><br>
<br>
<b>(*) Importante:</b> Si desea que la búsqueda de Obligaciones de pago sea filtrada por comprador, 
y de este modo poder seleccionar/procesar varias a la vez, deberá sí o sí, 
indicar un comprador y una moneda para la misma. <br>

De lo contrario, se desplegarán todas las Obligaciones de pago existentes, sin ningún filtrado, 
debiendo visualizar/procesar cada una de forma individual.<br>
<br>
Los filtros disponibles para la búsqueda son:
<ul>
	<li><b> Estado: </b> es el <a href="#statusOP"><u>estado</u></a> de las obligaciones de pago a buscar.</li>

	<li><b> Moneda:</b> es la moneda de las obligaciones de pago a buscar.</li>

	<li><b> Usuario/Nombre del comprador:</b> son los datos identificativos del usuario emisor (comprador) de la obligación de pago.</li>
	
	<li><b> Usuario/Nombre del vendedor:</b> son los datos identificativos del usuario receptor (vendedor) de la obligación de pago.</li>

	<li><b> Vencimiento Desde/Hasta:</b> son las fechas de vencimiento de las obligaciones de pago a buscar.</li>

	<li><b> Monto Desde/Hasta:</b> es el monto de las obligaciones de pago.</li>
</ul>
Para ejecutar la "Búsqueda", presione el botón correspondiente.
<br>

<a name="payment_obligations_search_results"></a>
<h3> Resultado de búsqueda de obligaciones de pago </h3>
Esta página muestra la lista de las Obligaciones de pago resultantes de la búsqueda.<br>
<br>
Para ingresar y visualizar los detalles de una Obligación de pago, haga click en el ícono 
correspondiente a su visualización <img border="0" src="${images}/view.gif" width="16" height="16">.<br>  

<a name="edit_payment_obligation"></a>
<h3> Detalle de obligación de pago </h3>
Esta opción permite visualizar los detalles de una Obligación de pago.<br>
Los campos mostrados son los siguientes:<br>
<ul>
	<li> Estado</li>
	<li> Código de usuario del vendedor</li>
	<li> Nombre del vendedor</li>
	<li> Código de usuario del comprador</li>
	<li> Nombre del comprador</li>
	<li> Fecha de vencimiento</li>
	<li> Publicado hasta</li>
	<li> Monto</b> indicado en su moneda correspondiente</li>
	<li> Descripción</li>
</ul>

<a name="payment_obligation_logs"></a>
<h3> Detalle de cambios </h3>
En esta ventana, además de mostrarse los datos de la Obligación de pago seleccionada, 
se despliega en su parte inferior un cuadro con el detalle de sus cambios de 
<a href="#statusOP"><u>estado</u></a> (trazabilidad).<br>


<a name="accept_payment_obligation"></a>
<h3>Aceptación de obligación de pago </h3>
Una vez realizada la búsqueda, el vendedor deberá aceptar la o las Obligaciones de pago que le fueron publicadas.<br>
<br>
Existen dos opciones:<br>
<br>
<b> A) Sin filtrar información (procesar una por una cada Obligación de pago) en la búsqueda:</b><br>
Para seleccionar/visualizar una única Obligación de pago, presione el ícono de visualización 
<img border="0" src="${images}/view.gif" width="16" height="16"> correspondiente.<br>
Se mostrará su detalle y su trazabilidad (cambios de estado).<br> 
<br>
	<ul>
		<li>Para <b>Aceptar</b> la Obligación de pago seleccionada, presione el botón correspondiente, <br>
		indicando previamente el Emisor de garantías involucrado, en el sector de "Emisor seleccionado". <br>
		A partir de la aceptación de dicha Obligación de pago, se generará automáticamente una Garantía,
		la cuál tendrá como responsable al comprador; sus tasas de emisión y crédito estarán a cargo del vendedor;
		deberá ser autorizada por el Emisor de garantías y posteriormente por el Administrador de la Red.<br>
		<br>
		Luego de pasar por ambas autorizaciones, el sistema automáticamente: <br>
		<ul>
			<li> Dejará la Garantía en estado de Aceptada.</li>
			<li> Generará un Préstamo a nombre del comprador.</li>
			<li> Traspasará el saldo de la cuenta del comprador a la cuenta del vendedor.</li>
			<li> Cobrará las tasas de emisión y crédito al vendedor.</li>
		</ul>
		</li>
		<br>
		<li>Para <b>Rechazar</b> la Obligación de pago seleccionada, presione el botón correspondiente.<br>
		Todos los cambios de estado efectuados sobre la Obligación de pago y su correspondiente Garantía, 
		serán registrados y notificados tanto al comprador como al vendedor involucrado.
		</li>
	</ul>
<b> B) Filtrando por moneda y comprador en la búsqueda:</b><br>
Para seleccionar/procesar varias (lote) Obligaciones de pago a la vez, usted deberá en la búsqueda filtrar 
las garantías correspondientes a un <i>comprador</i> y a una <i>moneda</i> en particular.<br>
De este modo, en el resultado de la búsqueda, cada garantía obtenida estará precedia por una caja de chequeo <b>(*)</b> para su selección.<br>
<br>
<b>(*)</b> Cajas de chequeo: permiten marcar/procesar una o varias Obligaciones de pago juntas.<br>
<ul>
	<li>Para ingresar y visualizar los detalles de una Obligación de pago, 
		debe presionar el ícono <img border="0" src="${images}/view.gif" width="16" height="16"> correspondiente a su visualización.
	<br>		
	<li>Para procesar/aceptar una o varias Obligaciones de pago a la vez, 
		seleccione las Obligaciones de pago deseadas, marcando su caja de chequeo correspondiente y presione el botón <b>Siguiente</b>:
		<br>
		<br>
		<ul>
			<li>Para <b>Aceptar</b> las Obligaciones de pago seleccionadas, presione el botón correspondiente, 
			indicando previamente el Emisor de garantías involucrado, en el sector de "Emisor seleccionado".
			<br> 
			<li>Para <b>Eliminar</b> de la selección una Obligación de pago, presione el botón correspondiente.</li>
		</ul>
</ul>
A partir de la aceptación de dichas Obligaciones de pago, será generada automáticamente una Garantía, 
la cuál tendrá como responsable al comprador, sus tasas de emisión y crédito estarán a cargo del vendedor; 
deberá ser autorizada por el Emisor de garantías y posteriormente por el Administrador de la Red.<br>
<br>
Luego de pasar por ambas autorizaciones, el sistema automáticamente: <br>
<ul>
	<li> Dejará la Garantía en estado de Aceptada.</li>
	<li> Generará un Préstamo a nombre del comprador.</li>
	<li> Traspasará el saldo de la cuenta del comprador a la cuenta del vendedor.</li>
	<li> Cobrará las tasas de emisión y crédito al vendedor.</li>
</ul>
Todos los cambios de estado efectuados sobre la Obligación de pago, y su correspondiente Garantía, 
serán registrados y notificados tanto al comprador como al vendedor involucrado.<br>


<a name="statusOP"></a>
<h3> Estados de las Obligaciones de pago </h3>
La forma de alcanzar los diferentes estados de una Obligación de pago puede deberse 
a una acción manual (realizada por un usuario), 
o automática (realizada a través de una tarea de rutina del sistema).

<ul>
	<li><b>Registrado</b></li>
	<ul>	
		<li>Manual</li>
		<ul>
			<li>Si el comprador crea una nueva Obligación de Pago.</li>
			<li>Si el comprador oculta una Obligación de Pago en estado Registrado.</li>
		</ul>
	</ul>
	<li><b>Publicado</b></li>
	<ul>
		<li>Manual</li>
		<ul>	
			<li>Si el comprador publica un Obligación de Pago en estado Registrado.</li>
		</ul>
	</ul>
	<li><b>Aceptado</b></li>
	<ul>
		<li>Manual</li>
		<ul>	
			<li>Si el vendedor acepta una Obligación de Pago en estado Publicado.</li>
		</ul>
	</ul>
	<li><b>Rechazado</b></li>
	<ul>
		<li>Manual</li>
		<ul>	
			<li>Si el vendedor rechaza una Obligación de Pago en estado Publicado.</li>
		</ul>
	</ul>
	<li><b>Vencido</b></li>
	<ul>
		<li>Automático</li>
		<ul>	
			<li>Si se alcanzó la fecha de vencimiento o de publicación de una obligación de Pago en estado Registrado.</li>
			<li>Si se alcanzó la fecha de vencimiento o de publicación de una obligación de Pago en estado Publicado.</li>
		</ul>
	</ul>
</ul>

<h3> Notificaciones </h3>
En el caso de las Obligaciones de Pago se envían las siguientes notificaciones:<br>

<ul>
	<li> Al vendedor cuando una Obligación de Pago existente pasa a estado "Publicado". </li>
	<li> Al comprador cuando una Obligación de Pago existente pasa al estado "Rechazado". </li>
</ul>

<hr class="help">

</div> <%--  page-break end --%>

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

















