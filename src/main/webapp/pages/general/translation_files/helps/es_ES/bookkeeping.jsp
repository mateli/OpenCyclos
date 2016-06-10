<div style="page-break-after: always;">
<a name="bookkeeping_top"></a>
<br><br>

La función de cuentas externas puede ser utilizada
como enlace a &quot;cuentas externas&quot;, como ser cuentas bancarias o de otros
sistemas monetarios con cuentas en un sistema Cyclos.
<br><br>
Esta funcionalidad le permite replicar completamente una cuenta externa (y sus 
transacciones) localmente en Cyclos, por lo que es posible tener un &quot;enlace&quot; 
administrativo de las transacciones de Cyclos con las importadas. <br>
Esto significa que ambas transacciones tienen referencias entre sí, y por ende, poseen un estado extra. 
Este estado muestra un ícono específico en el resumen de transacciones.<br> 
Es también posible buscar e imprimir listados con resultados de transacciones por estado.

<br><br>Por lo general este módulo se utiliza para controlar saldos de sistemas donde las
unidades internas (de Cyclos) son respaldadas con dinero convencional en cuentas bancarias.
En estos casos las transacciones bancarias tienen una relación directa con transacciones 
específicas en Cyclos. 

Por ejemplo, un depósito (crédito = pago entrante) en el Banco puede estar
relacionado con un pago de sistema-a-miembro, que puede ser una &quot;compra&quot; de Unidades
de Cyclos o la acreditación de un préstamo. 

Y en el caso contrario, un pago (débito = pago saliente) de una cuenta en el Banco 
a una cuenta bancaria del miembro puede estar relacionada a un pago miembro-a-sistema
en Cyclos (como el caso de una conversión). La función de conciliación ayuda al control del
&quot;respaldo&quot; de las Unidades de Cyclos. Por ejemplo, &quot;la conciliación de saldo&quot; 
de un sistema donde las Unidades internas son 100% respaldadas con moneda convencional serán
de uno a uno.

<br><br>Es posible generar automáticamente tipo de transacciones (Cyclos)
específicas en transacciones importadas. Por ejemplo un pago que ingresa de
una cuenta bancaria externa puede generar un pago de sistema-a-miembro en Cyclos.<br>
Esto se explica con detalles en la sección configuración de importación del módulo
cuentas externas.

<br><br>El control del &quot;respaldo&quot; es solamente un ejemplo del uso de este módulo.
Puede utilizarse también para otros casos donde se necesitan transacciones externas 
para generar transacciones locales o cambio de estado de préstamos en el sistema local.

<i>¿Dónde las encuentro?</i>
<br>
El módulo de cuentas externas se encuentra en &quot;Menú: Cuentas Externas&quot; (para
ver este menú se necesita tener configurados permisos para el grupo administrador)

<br><br><i>¿Cómo hacerlas funcionar?</i><br>
Para que el módulo sea visible debe tener configurado los 
<a href="${pagePrefix}groups#manage_group_permissions_admin_system"><u>permisos de administración</u></a> 
correctos para las &quot;Cuentas Externas&quot;.
<br><br>
Antes de poder importar transacciones externas a cyclos, debe realizar los siguientes pasos:
<ol>
	<li>Verifique que los <a href="${pagePrefix}account_management#transaction_types"><u>tipos de transacciones</u></a> 
	que necesita para estas transacciones existan.
	<li>Cree una cuenta externa en &quot;Menú: Cuentas externas> Administrar cuentas externas> Nueva cuenta externa&quot;.
	
	<li><b>Mapeo de archivo:</b> Cree una definición del archivo de importación (se desplegará la ventana al crear la nueva cuenta externa).
	<li><b>Archivo de campos de mapeo:</b> Especifique en Cyclos cómo tratar los campos de este archivo.
	<li><b>Tipo de pago:</b> Especifique en Cyclos cómo se mapean los valores del campo que define el "tipo de pago" 
	con los tipos de transacción de Cyclos.
</ol>
Sólo luego de realizar estos pasos (con uno es suficiente) usted podrá realizar la importación de transacciones externas 
mediante el archivo de transacción recibido.
<a href="#using"><u>Haga click aquí</u></a> para ver como importar transacciones desde un archivo.<hr>

<A NAME="external_accounts_list"></A>
<h3>Lista de cuentas externas</h3>
Esta ventana muestra el listado de todas las cuentas externas en el sistema.
<ul>
	<li>Haga click en el ícono <img border="0" src="${images}/edit.gif" width="16" height="16"> para Modificar las cuentas externas.
	<li>Haga click en el ícono <img border="0" src="${images}/delete.gif" width="16" height="16"> para Eliminar las cuentas externas.
</ul>
Puede Crear una nueva cuenta externa seleccione el botón "Nueva cuenta externa", ubicado en la parte inferior derecha de la ventana.
<hr class="help">	

<A NAME="new_edit_external_account"></A>
<h3>Insertar / Modificar cuenta externa</h3>
En esta página usted puede modificar o crear una nueva cuenta externa, 
seleccionando la Cuenta de sistema y la Cuenta de miembro (en Cyclos) que estarán relacionadas a la misma.<br>
<br>
Las transacciones en cuentas externas estarán mayormente asociadas a transacciones que
involucran creación (préstamo, depósito) y destrucción (conversión) de moneda electrónica.<br> 
Por lo tanto, la cuenta de sistema relacionada a una cuenta externa normalmente será de tipo 
&quot;ilimitada&quot;. Por más información consulte el 
<a href="${pagePrefix}account_management#account_details"><u>archivo de ayuda</u></a> sobre &quot;creación de cuentas&quot;. 
<hr class="help">

<A NAME="edit_file_mapping"></A>
<h3>Mapeo de archivo</h3>
Para importar información de transacciones de una cuenta externa, usted deberá 
definir el mapeo entre los campos de una cuenta externa y los de la cuenta en Cyclos.<br> 
Luego de crear la cuenta externa se le pedirá crear el Mapeo de archivo, donde podrá ingresar las siguientes opciones:
<ul>
	<li><b>Tipo:</b> CSV o Personalizado.<br>
	Normalmente, los archivos de transacción contienen texto plano, donde los valores son
	separados por algún caracter especial. En este caso se utiliza la opción
	<a href="${pagePrefix}loans#csv"><u>CSV</u></a>.<br> 
	En el caso de que el archivo sea más complejo, por ejemplo un XML, es posible programar un archivo personalizado que
	pueda manejar dicho formato. Esta documentación no pretende entrar en detalles técnicos pero necesitará realizar lo siguiente:
	<ol>
		<li>Crear una clase Java que implemente la interfaz	TransactionFileImport de Cyclos (en el paquete Utils).
		<li>Poner la clase en el camino de clases (classpath) y en el servidor, en el directorio
		WEB-INF/classes o en un directorio de librerías compartidas, si está dentro de un paquete JAR.
	</ol>
	<br>
	<li><b>Separador de columna: </b><br>
	Es el caracter que se utiliza para separar valores en el archivo, por lo general la coma &quot;,&quot;.
	
	<li><b>Comilla de cadena de caracteres: </b><br>
	Los archivos de transacciones y los archivos CSV poseen generalmente valores de tipo cadena de 
	caracteres. Como por ejemplo un nombre ("José Pintos"). 
	Aquí se definen los separadores de las cadenas de caracteres, que por lo general son las comillas dobles (").
	
	<li><b>Líneas de encabezado: </b><br>
	Cantidad de líneas destinadas al cabezal (estas líneas no contienen valores). Estas líneas son ignoradas.
	
	<li><b>Formato de fecha: </b><br>
	Aquí usted puede establecer el formato de la fecha a utilizar. Puede usar "a" para años, "M" para mes
	(en mayúsculas) y "d" para día. También agregar separadores entre los valores como ser
	dd/mm/aa, aaaa-MM-dd, etc.
	
	<li><b>Formato de número: </b><br>
	Existen dos opciones posibles:
	<ul>
		<li><b>Posición fija:</b> En algunos casos el formato del monto en los archivos
		de transacción no poseen separador, sino que éste es fijo y se calcula desde la
		derecha. Por ejemplo: en un monto de 50000 con lugares decimales de 2, se lee
		como 500 (o 500,00 con la coma).<br>
		Si selecciona esta opción, el campo ubicado a la derecha se llamará
		&quot;Lugares decimales&quot;, y por lo general se utiliza con valor &quot;2&quot;.
		
		<li><b>Con separador:</b> Por lo general se utiliza con separador; puede definirlo
		en el campo &quot;Separador decimal&quot; (a la derecha). Normalmente es un punto o una coma.
	</ul>
	<br>
	<li><b>Caracter negativo: </b><br>
	En algunos casos el formato del monto en el archivo de transacciones nunca es negativo,
	pero si los acepta. Existe un caracter especial en una columna por separado. Como ejemplo
	puede haber un &quot;-&quot; o una D (Débito): |-500,00| o |D|500,00| (donde el | 
	es el separador de columna). En este caso, ingrese ese caracter en este campo.<br>
	Normalmente el monto tiene el caracter negativo en el mismo campo y la columna negativa
	no es necesaria.
</ul>
Haga click en el botón &quot;Aceptar&quot; para confirmar los cambios. <br>
Si hace click en el botón &quot;Inicializar&quot;, se eliminarán todos los archivos de mapeo y se le
solicitará la creación de uno nuevo. 
Los tipos de pago (ventana ubicada debajo) no serán eliminados al inicializar el mapeo de archivo.
<hr class="help">

<A NAME="file_mapping_fields_list"></A>
<h3>Archivo de campos de mapeo</h3>
Una vez definido el formato del mapeo de archivo (ventana superior), se pueden mapear los campos del archivo de transacciones a campos de Cyclos.<br> 
Todas la opciones de la lista representan filas del archivo de transacciones y cada campo es una columna.
<br><br>
Al visualizar esta ventana por primera vez, no existirán campos en la lista. 
Para ir agregando campos, usted debe hacer click en &quot;Insertar nuevo mapeo de campo&quot;.<br> 
Debe repetir esto para mapear TODOS los campos existentes en el archivo de transacciones.
<br>
El primer campo (con número de orden 1) será la primer columna (a la izquierda) del archivo de
transacciones. Asegúrese que el orden de los campos corresponda con el de las columnas.
<br><br>
Una vez ingresado un archivo a la lista, varias acciones serán posibles:
<ul>
	<li>Puede modificar el orden del campo haciendo click en el botón &quot;Cambiar orden de mapeos de campo&quot;.
	<li>Puede modificar el valor de este campo haciendo click en el ícono 
	<img border="0" src="${images}/edit.gif" width="16" height="16"> correspondiente.
	<li>Puede eliminar el campo de la lista haciendo click en el ícono 
	<img border="0" src="${images}/delete.gif" width="16" height="16"> correspondiente.
</ul>
<hr class="help">

<A NAME="edit_field_mapping"></A>
<h3>Nuevo / Modificar mapeo de campo</h3>
Aquí usted puede definir el mapeo por campo.<br> 
Debe ingresar un Nombre, que es solo una etiqueta que se mostrará en la lista (no posee ninguna función).<br> 
En la lista desplegable de campos (Campo), puede seleccionar alguna de las siguientes opciones:
<ul>
	<li><b>Ignorado: </b>
	Si el archivo contiene columnas extra que no se necesitan mapear con Cyclos, igualmente
	se necesita mapear estos campos, poniéndoles la opción de &quot;Ignorado&quot;.
	<li><b>Id de usuario: </b>
	Esta opción se utiliza para mapear con el número de Id. interno utilizado por Cyclos. Es casi
	seguro que el archivo contenga el ID (del miembro) interno de Cyclos, por lo que ofrecemos se pueda agregar.
	<li><b>Código de miembro: </b>
	Si el archivo de transacciones especifica el código de miembro (de Cyclos), puede definir en esta columna dicha opción.
	<li><b>Campo personalizado: </b>
	Si el archivo de transacción no especifica el nombre de usuario es posible mapear un campo (perfil)
	personalizado para identificar al usuario. Debe ser un campo con valor único, como ser la cédula
	de identidad (o celular).
	<li><b>Tipo de pago: </b>
	Si desea importar transacciones y generar pagos del archivo de transacciones, debe definir un tipo
	de pago. La mayoría de las transacciones del archivo será de más de un tipo de pago. Por ejemplo
	depósitos, pagos de préstamo, conversiones, etc.<br>
	El tipo de pago es especificado con un código en una columna separada. 
	Con esta opción se puede definir la columna que representa el tipo de pago. 
	Los valores posibles de los diferentes tipos de pago se pueden definir en la función "Tipo de pago", 
	ubicada en la ventana inferior.
	<li><b>Descripción de pago</b>.
	<li><b>Fecha de pago: </b> Con esta opción puede especificar la columna que contiene la fecha de la transacción.
	<li><b>Monto de pago: </b> Aquí se especifica la columna que contiene el monto del pago en la transacción.
	<li><b>Indicador de monto negativo: </b> Como en el monto no se especifica si el valor
	del mismo es positivo o negativo, esto debe ser especificado en una columna separada.
	Con este campo se define si el pago es negativo o no. Puede utilizar un signo (por
	ejemplo &quot;-&quot;) o una palabra (por ejemplo &quot;D&quot; o &quot;débito&quot;)
</ul>

Haga click en el botón &quot;Aceptar&quot; para guardar los cambios.
<hr class="help">

<a name="set_field_mappings_order"></a>
<h3>Defina el orden del mapeo de los campos</h3>
En esta ventana usted puede cambiar el orden de los campos de mapeo.<br> 
Los campos mapeados que se definieron, deben estar en el mismo orden que los campos en los archivos de transacciones que se desea importar.
<br><br>
Esta ventana es muy sencilla de utilizar. Simplemente haga click en el nombre y arrástrelo con 
el mouse a la posición deseada. Al finalizar haga click en el botón &quot;Aceptar&quot; para confirmar los cambios.
<hr class="help">

<A NAME="external_transfer_type_list"></A>
<h3>Tipo de pago (acción de mapeo)</h3>
Esta ventana muestra una lista con los tipos de pago posibles que contiene el archivo de transacciones.<br>
Paga agregar un tipo de pago, debe existir el campo de mapeo correspondiente al Campo: "Tipo de pago". <br>
Con esta ventana, se le indica al sistema que valores en este campo mapeado se corresponden con los tipos de pago de Cyclos. 
Recuerde que debe mapear todos los valores posibles que pueden existir en este campo; 
los códigos que no se mapean con ningún tipo de transacción, se definen como &quot;Ignorar&quot;.
<ul>
	<li>Haga click en el ícono <img border="0" src="${images}/edit.gif" width="16" height="16"> para Modificar un mapeo de tipo de pago.
	<li>Haga click en el ícono <img border="0" src="${images}/delete.gif" width="16" height="16"> para Eliminar un mapeo de tipo de pago.
</ul>
Para Agregar un nuevo mapeo de tipo de pago, haga click en el botón Aceptar etiquetado &quot;Insertar nuevo tipo de pago&quot;.
<hr class="help">

<A NAME="edit_external_transfer_type"></A>
<h3>Insertar / Modificar tipo de pago</h3>
En esta página usted puede definir como Cyclos debe interpretar el código del campo "tipo de pago" del archivo de transacciones.<br> 
Aquí se especifica que tipo de transacción específica se mapea con cada código. 
Debe repetir este procedimiento para todos los diferentes códigos de este campo.<br>
<br>
El Nombre y la Descripción son usados internamente, y no poseen una función específica.<br> 
El Código es uno de los valores posibles del campo que contiene los 
<a href="#edit_field_mapping"><u>mapeos del campo tipo de pago</u></a>. 
Vea la lista ubicada debajo con un ejemplo.<br>
<br>
Las siguientes acciones (campo Acción) son permitidas:
<ul>
	<li><b>Ignorar: </b>
	Indica que este código específico no debería realizar ninguna acción.<br>
	La transacción aparecerá en la vista de cuentas externas, para que el saldo sea el
	correcto, pero no generará pago alguno dentro de Cyclos.
	
	<li><b>Generar pago para sistema: </b>
	Esta es una acción poco utilizada, pero se permite realizar. Significa que una transacción que proviene	
	de una cuenta externa, generará un pago del miembro al sistema en Cyclos.<br>
	Un ejemplo podría ser que desee importar la contabilidad en moneda nacional a Cyclos, 
	para verificar si los miembros pagaron su membresía en moneda nacional en fecha. 
	En este caso, va a necesitar crear cuentas de la organización en moneda nacional, y otra cuenta 
	de miembros en moneda nacional. Esto le permitirá reflejar las transacciones de la
	cuenta bancaria en estas cuentas creadas, y verificar si los miembros pagaron su cuota
	de membresía (aparte de eso, también puede generar estadísticas con esta información).
	
	<li><b>Generar pago para miembro: </b>
	Aquí un ejemplo puede ayudar: si una columna de tipo de pago en el archivo de
	transacciones tuviera varios valores posibles (códigos) y uno de ellos fuera
	&quot;DEP1&quot;, indicando que la transacción es un depósito en la cuenta bancaria. 
	Si Ud. quiere que este tipo de pago en particular genere un pago automático de la
	cuenta del sistema a la del miembro, debería seleccionar esta opción.
	
	<li><b>Descartar préstamo: </b>
	Algunos sistemas utilizan <a href="${pagePrefix}loans"><u>préstamos</u></a> en Cyclos, 
	que pueden ser pagos externamente (con dinero convencional). Cuando se paga el 
	préstamo externamente, usted no querrá que el estado en Cyclos se cambie a "Pago", ya que
	dicho estado esta reservado para pagos internos con moneda electrónica. 
	En estos casos, el nuevo estado podría ser &quot;Descartado&quot;. 
	Cuando se paga un préstamo, el estado se cambia automáticamente dependiendo de estos tipos de pagos.
	
	<li><b>Conciliar pago: </b>
	El estado de conciliación se da en casos de pagos internos en Cyclos, que poseen un pago
	externo que ha sido confirmado. Es utilizado para controlar el saldo de un grupo de
	cuentas de Cyclos (de sistema y de miembro), así como el saldo de cuentas externas.
</ul>

<hr>
<a name="using"></a>
<h2>Utilizando cuentas externas</h2>
Puede importar transacciones externas a la cuenta interna, y luego procesarlas.
También puede buscar transacciones en la cuenta externa.

<i> ¿Dónde las encuentro?</i>
<br>
A la vista de cuentas externas se accede a través del &quot;Menú: Cuentas externas> Lista de cuentas externas&quot;.
<br><br><i> ¿Cómo hacerlas funcionar?</i><br>
Una vez configurado el mapeo del archivo externo (ver arriba), usted puede comenzar a
importar transacciones desde el archivo de transacciones. <br>
Debe realizar las siguientes acciones para que funciones correctamente:
<ol>
	<li>Ir a &quot;Menú: Cuentas externas > Resumen de cuentas externas&quot;, y hacer 
	click en el ícono
	<img border="0" src="${images}/import.gif" width="16" height="16">&nbsp; para
	ir al módulo de importación.
	<li>importe el archivo. Si sucede algún error, corríjalo hasta que la importación se
	realice satisfactoriamente.
	<li>Vaya a la ventana de transacciones importadas. Se puede acceder a través de la
	ventana de resultados (arriba de esta ventana con el botón importar), y haga click
	en el ícono ver <img border="0" src="${images}/preview.gif" width="16" height="16">&nbsp;
	.
	<li>remover o restaurar transacciones incompletas; verifique las transacciones pendientes
	y cambie su estado a &quot;verificada&quot;. Todo esto se puede hacer con el ícono
	<img border="0" src="${images}/preview.gif" width="16" height="16">&nbsp;ver de cada
	transacción.
	<li>procesar las transacciones verificadas mediante el botón de &quot;procesar&quot; 
	arriba de esta vista de transacción.
</ol>
<hr>

<A NAME="external_accounts_overview"></A>
<h3>Resúmen de cuentas externas</h3>
Esta página lista todas las cuentas externas configuradas en el sistema.<br> 
Se muestra el Nombre de la cuenta externa y el Saldo de la cuenta, formado por la suma de todas las transacciones importadas.
<ul>
	<li>Haga click en el ícono <img border="0" src="${images}/import.gif" width="16" height="16"> (flecha) 
	para ingresar en la función Importar, permitiéndole importar transacciones externas desde un archivo. <br>
	Usted podrá visualizar también las importaciones anteriormente en esta pantalla.
	
	<li>Haga click en el ícono <img border="0" src="${images}/preview.gif" width="16" height="16"> (lupa) 
	para visualizar y procesar las transacciones ya importadas.
</ul>
<hr class="help">

<A NAME="external_transfer_import_new"></A>
<h3> Importar transacciones </h3>
En esta ventana usted puede importar transacciones externas. <br>
Simplemente seleccione el archivo a importar y haga click en el botón "Aceptar".<br>
<br>
Si no es posible leer el archivo por errores de sintaxis, se mostrará un reporte especificando la línea causante del error.
<hr class="help">

<A NAME="external_transfer_import_list"></A>
<h3> Buscar archivos importados </h3>
En esta ventana usted puede buscar los archivos importados en un período determinado.<br>
Puede utilizar el ícono <img border="0" src="${images}/calendar.gif" width="16" height="16"> para ingresar el rango de fechas deseado.
<hr class="help">

<A NAME="external_transfer_import_result"></A>
<h3>Resultado de búsqueda de importaciones</h3>
Esta ventana muestra la lista de todos los archivos de transacciones importados.
<ul>
	<li>Haga click en el ícono <img border="0" src="${images}/preview.gif" width="16" height="16"> (lupa) 
	para visualizar y procesar las transacciones importadas.<br>
	<li>Haga click en el ícono <img border="0" src="${images}/delete.gif" width="16" height="16">, para Eliminar las transacciones importadas.<br>
	Nota: No es posible eliminar importaciones que tengan transacciones con el estado de &quot;verificado&quot; o &quot;procesado&quot;.
</ul>
<hr class="help">

<A NAME="external_account_history"></A>
<h3>Transacciones de Cuenta externa</h3>
En esta ventana se pueden buscar transacciones importadas. <br>
Esta función busca en todos los archivos importados.<br> 
Si no es ingresado ningun filtro, se mostrarán todas las transacciones importadas para una cuenta externa determinada.<br>
<br>
Las siguientes opciones de búsqueda se encuentran disponibles:
<ul>
	<li><b>Tipo:</b> Aquí se selecciona el tipo de pago (definido en la configuración de la cuenta externa).
	<li><b>Estado:</b> Una transacción importada puede poseer uno de los siguientes estados:
	<ul>
		<li><b>Pendiente:</b> La transacción fue importada pero aún no fue verificada. 
		Por lo tanto no afecta el &quot;saldo importado&quot; aún (en la función principal de cuentas externas) ni 
		tampoco ha generado acción alguna.<br>
		Nota: Es también posible borrar transacciones importadas que tengan el estado pendiente.
		<li><b>Verificado:</b> Estas transacciones han sido verificadas y puestas en estado de
		&quot;verificado&quot; <a href="#external_transfers_history_result"><u></u></a>. 
		<li><b>Procesado:</b> Estas transacciones han sido procesadas luego de que fueron puestas en estado "verificado".
	</ul>
	<br>
	<li><b>Código de miembro / Nombre:</b> Busca transacciones para un miembro específico.
	<li><b>Monto (desde / hasta):</b> Busca transacciones por rango del monto.
	<li><b>Fecha (desde / hasta):</b> Busca transacciones para un determinado período de tiempo.
</ul>
Debajo de la ventana, se encuentran tres(3) botones, correspondientes a tres(3) acciones;<br>
De izquierda a derecha:
<ul>
	<li><b>Archivos importados:</b> 
	Esta opción lo redireccionará a una vista de los archivos ya importados, 
	donde usted podrá también importar un nuevo archivo.
	<li><b>Procesar pagos: </b>
	Esta opción abrirá una ventana donde usted tendrá la opción de procesar una o más transacciones. <br>
	<br>
	Las opciones de procesamiento son:
	<ul>
		<li>Conciliar un pago saliente (desde la cuenta externa).
		<li>Generar un pago interno de Cyclos relacionado a un pago que ingresa a la cuenta externa.
		<li>Descartar un pago de préstamo (de Cyclos) relacionado a un pago que ingresa en la cuenta externa.
	</ul>
	<br>
	El botón "Procesar pagos" aplica a todas las transacciones resultantes de la búsqueda (sin importar si están seleccionadas o no).<br> 
	Note que una transacción puede ser procesada solamente si su estado es "verificado".
	</li>
	<br>
	<li><b>Nuevo pago: </b>
	Es posible insertar una transacción manualmente en caso de que no se la haya importado correctamente.
</ul>
Más información sobre estas funciones puede encontrarse en la ventana correspondiente a cada función.
<hr class="help">

<a name="status"></a>
<h3>Estado de transacción</h3>
Cada transacción importada tiene un estado, que puede tener alguno de los siguientes valores::
<ul>
	<li><b>Pendiente</b> <img border="0" src="${images}/pending.gif" width="16" height="16">:
	La transacción fue importada pero aun no tiene efecto alguno. Está pendiente de acciones
	futuras.<br>
	Este estado se muestra en la vista de cuenta del sistema si el tipo de transacción es 
	definido como &quot;Es conciliable&quot;. De esta forma se puede seguir el estado de la
	conciliación directamente de la cuenta en la página de vista.<br>
	Solo las transacciones con estado pendiente pueden ser eliminadas.
	<li><b>Verificado</b> <img border="0" src="${images}/checked.gif" width="16" height="16">:
	La transacción ha sido verificada y puesta como &quot;verificado&quot;. Esto significa que
	se ingresará en el saldo de cuenta externa.<br>
	Es posible poner una transacción con estado &quot;verificado&quot; nuevamente con estado
	&quot;pendiente&quot;.
	<li><b>Incompleto</b> <img border="0" src="${images}/incomplete.gif" width="16" height="16">:
	La transacción fue importada pero uno o más archivos no pudo ser mapeado correctamente. 
	Por ejemplo un miembro ingresado en el archivo de transacción no existe en Cyclos.<br>
	<li><b>Conciliado</b> <img border="0" src="${images}/conciliated.gif" width="16" height="16">:
	La transacción fue procesada. Esto significa que es parte del saldo de cuenta externa y 
	provoca acciones en Cyclos (por ejemplo pago interno en Cyclos o descartar préstamo, etc).<br>
	Este estado también se muestra en la vista de cuenta de sistema si es que el tipo de transacción
	es definido como &quot;Es conciliable&quot;. De esta forma se puede hacer seguimiento del estado
	de conciliación directamente desde la página de vista de cuenta.<br>
	Las transacciones procesadas no se pueden eliminar ni cambiar de estado.
</ul>
<hr class="help">


<A NAME="external_transfers_history_result"></A>
<h3> Resultado de búsqueda de transferencias externas</h3>
Esta ventana muestra los resultados de la búsqueda realizada en la ventana superior. <br>
Por defecto muestra todas las transacciones importadas en el sistema.<br>
<br> 
La columna Tipo muestra el ícono con el <a href="#status"><u>estado</u></a> de la transferencia.<br>
Las columnas Monto y Fecha se explican por sí mismas.
<br><br>
Las siguientes acciones están disponibles para cada transacción:
<ul>
	<li><img border="0" src="${images}/preview.gif" width="16" height="16">
	Puede conciliar y procesar pagos seleccionando el ícono de Visualización (lupa).
	Estos pagos no pueden ser modificados, sin embargo es posible cambiar
	el estado de una transacción verificada nuevamente a estado pendiente.
	
	<li><img border="0" src="${images}/edit.gif" width="16" height="16">
	Puede modificar los datos de una transacción que se encuentra en estado pendiente, seleccionando el ícono de Edición.<br>
	
	<li><img border="0" src="${images}/delete.gif" width="16" height="16">
	Haga click en el ícono de Eliminación para eliminar la transacción.<br>
</ul>
<hr class="help">

<A NAME="external_transfers_history_summary"></A>
<h3>Resúmen de transacciones </h3>
Esta ventana le permite visualizar las transacciones y realizar reportes de las mismas.
<br><br>
Nota: Solo las transacciones que son resultado de la ventana de búsqueda (arriba) son tomadas en cuenta. <br>
Una búsqueda sin filtros ingresados, mostrará todas las transacciones importadas para determinada cuenta externa.
<hr class="help">

<A NAME="new_external_transfer"></A>
<h3>Nueva transferencia externa</h3>
Es posible agregar una transacción manualmente. Normalmente esto no es necesario
ya que la importación de una transacción puede ser configurada para importar todas
las transacciones correctamente. Pero en casos excepcionales puede que se necesite
hacer esto a través de esta ventana.
<br>
Los campos a ingresar son bastante sencillos y auto explicativos.
<hr class="help">

<A NAME="edit_external_transfer"></A>
<h3> Modificar transferencia externa </h3>
En esta página usted puede visualizar los detalles de una transacción importada.<br> 
Si el <a href="#status"><u>estado</u></a> es pendiente, puede modificar sus datos.<br>
Los campos a ingresar son bastante simples y auto-explicativos.
<hr class="help">
	
<A NAME="external_transfer_process"></A>
<h3>Procesar pagos</h3>
En esta página usted puede procesar pagos. Note que un pago debe encontrarse en estado
<a href="#status"><u>&quot;verificado&quot;</u></a> o no será mostrado en la lista de transacciones a ser procesadas.
<br><br>
La ventana muestra una vista de los pagos que pueden ser procesados.<br> 
Para cada item, la fila superior muestra la línea original como es leída del archivo de transacción, 
y la inferior como será procesada luego de hacer click en el botón "Aceptar". <br>
Seleccione las transacciones que desee procesar marcando (click) su correspondiente casilla de verificación, 
ubicada en la primer columna. 
Esto también le permitirá modificar la fecha o el monto, si es que no son correctos.
<br>
Haciendo click en el botón &quot;Aceptar&quot;, procesará las transacciones seleccionadas.
<br><br>
Existen tres tipos de procesamientos posibles:
<ul>
	<li><b>Conciliar: </b>
	Esta opción es común en sistemas donde unidades internas (Cyclos) son respaldadas
	externamente (como ser dinero convencional en una cuenta bancaria). Conciliar un
	grupo de transacciones (transacción externa y transacción de Cyclos) significa que
	ambas serán enlazadas porque están administrativamente relacionadas. Esto se mostrará
	con el ícono <img border="0" src="${images}/conciliated.gif"
		width="16" height="16"> delante del pago en vista general de la cuenta en la función
	de cuenta externa y en la función de vista general de cuenta del sistema. En la ventana
	de búsqueda de estas funciones hay una opción de buscar por el estado de la conciliación.
	Para que sea posible conciliar transacciones, el tipo de transacción en Cyclos debe 
	estar configurado con la opción &quot;Es conciliable&quot; en su configuración. <br>
	<li><b>Generar pago: </b><br>
	Un pago que ingresa (externo y solamente positivo) puede ser configurado para generar
	un pago en Cyclos (del sistema a miembro o viceversa). Para procesar el pago se lo
	debe seleccionar, y si se desea modificar previamente la transacción (fecha y monto).<br>
	Un pago generado se pone automáticamente en estado conciliado
	<li><b>Descartar repago de préstamo: </b><br>
	Un préstamo en Cyclos pasa a estado &quot;pago&quot; al ser repago. En sistemas
	donde préstamos de Cyclos pueden ser pagos externamente será posible dar estado a
	dichos préstamos para indicar que fueron pagos. Este estado es llamado &quot;Descartado&quot;. 
	Es posible tener un pago externo que ingresa y genera un pago de préstamo descartado.<br>
	Hay un tipo específico para la configuración de cuentas externas. Cuando estas transacciones
	externas son procesadas, Cyclos busca todos los pagos de préstamo abiertos con el mismo
	monto y fecha y muestra los que más se asemejan. Si hay más de uno se los muestra al
	administrador para que seleccione el correcto. Luego de generado el pago descartado del
	préstamo, el estado de conciliado se ingresa en dicha transacción en la vista de cuenta externa.
</ul>
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
