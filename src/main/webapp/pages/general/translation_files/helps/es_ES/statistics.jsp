<div style="page-break-after: always;">
<a name="statistics_top"></a>
<br><br>

Cyclos tiene una sección para análisis estadísticos de sus datos. <br>
Las estadísticas son para tratar de cuantificar que tan exactos son tus resultados.

<i>¿Dónde las encuentro?</i><br>
Para ingresar a esta sección vaya a &quot;Menú: Reportes > Análisis estadísticos&quot;.

<br><br><i>¿Cómo hacerlas funcionar?</i><br>
Se necesita tener un <a href="${pagePrefix}groups#manage_group_permissions_admin_system"><u>
permiso</u></a> para ingresar en esta sección, el cual hay que habilitar previamente. Los
permisos se encuentran en un bloque titulado &quot;Reportes&quot;, casilla de verificación
&quot;estadísticas&quot;.<br><br>
Si desea utilizar ciertos <a href="${pagePrefix}groups#group_filters"><u>grupos de
filtros</u></a> o <a href="${pagePrefix}account_management#payment_filters"><u>
filtros de pagos</u></a> en sus estadísticas, deberá marcarlos como visibles para las estadísticas. <br>
Esto se realiza en la página de configuración de estos filtros (siga los enlaces). 
<hr>

<a name="choose_category"></a>
<h3> Selección de categoría de estadísticas </h3>
Esta ventana le permite seleccionar la categoría de estadísticas a mostar.<br> 
Haciendo click en el botón Aceptar perteneciente a la opción deseada, será redireccionado al formulario correspondiente, 
donde usted podrá especificar los detalles/criterios de las estadísticas a generar.<br>
Es posible seleccionar solamente una categoría a la vez. <br>
<br>
Usted puede seleccionar entre las siguientes categorías:
<ul>
	<li><b>Desarrollos clave:</b>Son los parámetros principales que determinan
	la salud y crecimiento del sistema (como: cantidad de miembros, volumen de transacciones, etc.).<br>
	
	<li><b>Actividad de miembros:</b> Estadísticas que involucran la actividad (transacciones) de los miembros.<br>
	
	<li><b>Finanzas:</b> Estadísticas sobre las cuentas del sistema: flujos de entrada y salida de cualquier cuenta del sistema. <br>
	
	<li><b>Impuestos y cargos:</b> Estadísticas sobre impuestos y tasas del sistema. 
	Permite visualizar por período, volúmen, número de miembros, monto por miembro y una cantidad de opciones más. <br>
</ul>
Nota: En futuras versiones de Cyclos se agregarán más opciones.
<hr class="help">

<a name="forms"></a>
<h2>Formularios estadísticos</h2>
Después de seleccionar la <a href="#choose_category"><u>categoría</u></a> de estadísticas que desea visualizar, 
el sistema lo redireccionará al formulario donde usted podrá especificar qué estadísticas desea ver.<br> 
Estos formularios siempre poseen una sección específica (diferente por cada categoría), donde es posible elegir diferentes opciones, 
y una sección en común (prácticamente la misma para las diferentes categorías), donde usted puede definir parámetros para las estadísticas, 
como ser el <a href="#periods"><u>período</u></a> deseado y otros <a href="#filters"><u>filtros</u></a>.

En cada formulario de estadísticas, primero debe elegir el tipo de estadísticas que desea ver en la lista desplegable
<a href="#whattoshow"><u>&quot;¿Qué desea mostrar?&quot;</u></a>.
<br>
En las casillas de verificación existentes, usted puede seleccionar las opciones que desea visualizar. <br>
El nombre de cada opción es auto-explicativo, pero si desea o necesita conocer la definición precisa, puede revisar el 
<a href="#glossary"><u>glosario</u></a>. <br>
Si selecciona un gráfico, se mostrarán los resultados en gráficas y tablas, de lo contrario, la información será mostrada únicamente en tablas.
<br>
Luego de seleccionar una o más opciones, debe especificar los <a href="#parameters"><u>parámetros</u></a> 
en común para su selección en la ventana inferior. 
<br><br>
<b>Importante:</b> Por favor recuerde que los cálculos estadísticos se 
realizan completamente en la base de datos, y que pueden resultar pesados. 
Especialmente en grandes bases de datos, los cálculos pueden llevar mucho tiempo, y consumir muchos recursos del servidor.
<hr>

<a name="key_development"></a>
<h2> Estadísticas de desarrollos clave </h3>
Aquí usted puede especificar qué estadísticas de desarrollo clave desea calcular.<br> 
Estas estadísticas tienen como objetivo obtener una visión general del desarrollo de su sistema.<br>
Es posible comparar distintos períodos y verificar tendencias, pero no es realizado ningún análisis estadístico.

Para una descripción del formulario, haga <a href="#forms"><u>click aquí</u></a>.
<hr>

<a name="member_activity"></a>
<h2> Estadísticas de actividad de miembros</h3>
Aquí puede especificar que estadística de actividad del miembro desea calcular.
Las estadísticas de la actividad del miembro le dará elementos para comprender
la actividad de los miembros.
También le brinda algunas estadísticas para un grupo de miembros.

Para una descripción del formulario, haga <a href="#forms"><u>click aquí</u></a>.
<hr>

<a name="finances"></a>
<h2> Estadísticas financieras </h2>
Las estadísticas financieras le dan un vistazo preciso de todos los flujos de 
entrada y salida, desde y hacia una cuenta de sistema.

Para una descripción del formulario, haga <a href="#forms"><u>click aquí</u></a>.
<hr>

<a name="taxes_fees"></a>
<h2>Impuestos & Tasas</h2>
Aquí se especifican que estadísticas sobre tasas e impuestos calcular. Estas
estadísticas le dan resultados de las tasas e impuestos cobradas en el sistema.

Para una descripción del formulario haga <a href="#forms"><u>click aquí</u></a>.
<hr>

<a name="parameters"></a>
<h2>Parámetros y campos en común de los formularios de estadísticas</h2>
Antes de poder ver los resultados, debe especificar que es lo que desea ver.
En general, Cyclos necesita saber un par de cosas antes de poder comenzar 
con los cálculos y mostrar los resultados estadísticos.
<ul>
	<li>&quot;<a href="#whattoshow"><u>¿qué desea mostrar?</u></a>&quot;: es una lista
	desplegable al comienzo del formulario, es la primer opción disponible. Aquí se
	elije el método para mostrar las estadísticas.<br>
	<li>&quot;<a href="#periods"><u>períodos</u></a>&quot;: Esta ventana esta debajo
	de las casillas de verificación. Aquí se especifica el período para el cual se
	calculan las estadísticas. No toda la información se puede calcular para un 
	período; en estos casos el último día del período es utilizado. Esto se indica
	siempre en el archivo de ayuda.<br>
	<li>&quot;<a href="#filters"><u>filtros</u></a>&quot;: Debajo de la ventana de
	períodos, esta la de filtros. Aquí se puede especificar aún más que información
	utilizar para los cálculos, como ser fechas, usuarios, etc. <br>
</ul>

<hr>

<a name="whattoshow"></a>
<h3>&quot;¿Qué desea mostrar?&quot;</h3>
Con esta lista desplegable, se especifica el método para mostrar las estadísticas. Puede
elegir entre los siguientes métodos:
<ul>
	<li>&quot;Mostrar un período:&quot; Esto le brinda un resumen para un solo <a
		href="#periods"><u>período</u></a>. Normalmente, no se realizan análisis estadísticos, 
	ni es posible de presentar en forma gráfica este método. <br>
	<li>&quot;Comparar dos períodos:&quot; Esto compara el resultado de dos períodos, los
	cuales se pueden presentar en un gráfico de barras. Seleccione esto si desea ver si
	cierto resultado aumento o disminuyo comparado con un período anterior. Aquí se realiza
	un análisis estadístico para calcular la diferencia entre ambos períodos que es bastante
    <a href="#p"><u>significativo</u></a>.<br>
	<li>&quot;A través del tiempo&quot;: hace lo mismo, pero para un rango de tiempo.<br>
	<li>&quot;Distribución&quot;: usualmente da como resultado un
	<a href="#histo"><u>histograma</u></a>, un gráfico que muestra ciertos resultados
	distribuidos entre la población.<br>
</ul>
No todas las opciones son visibles, esto depende de la categoría de estadísticas que se
haya seleccionado previamente.
<hr class="help">

<a name="periods"></a>
<h3> Períodos comparados </h3>
Aquí usted debe especificar los períodos sobre los cuales calcular las estadísticas. <br>
Dependiendo de la opción seleccionada, puede especificar uno, dos períodos, o hasta un rango de períodos.
<br><br>
El <b>Período principal</b> es sobre el cual se calculan las estadísticas.
Este puede ser comparado con el <b>Período para comparar con</b>.<br>
Para cada uno de estos períodos, usted debe ingresar un nombre, el cual será utilizado 
como cabezal en las columnas de las tablas y/o como leyendas en las gráficas.<br>
<br>
Si en el campo <b>¿Que desea mostrar?</b> selecciona la opción &quot;A través del tiempo&quot;, la ventana de períodos lucirá de forma un poco diferente.<br> 
En dicho caso, usted deberá seleccionar primero si quiere visualizar los resultados ordenados por año, trimestre o mes (<b>Mostrar</b>); 
para luego deberá seleccionar el rango de fechas deseado (Seleccionar meses y años) para el cálculo de las estadísticas. <br>
<br>
Asegúrese de no seleccionar un rango de fechas muy extenso:
seleccionar visualizar resultados mensuales para un período por ejemplo de 10 años, puede generar gráficas superpobladas 
y sobrecargar al servidor.<br>
En estos casos, una ventana emergente le dirá que limite su pedido (sus "puntos de datos").<br>
Los &quot;puntos de datos&quot; se refieren a cálculos separados. 
Ejemplo: si selecciona 5 opciones, un rango de 13 meses, esto resulta en 5 x 13 = 65 puntos de datos.<br> 
Las casillas de gráficas no cuentan paa estos casos, ya que no demandan cálculos extra.
<hr class="help">

<a name="filters"></a>
<h3>Filtros</h3>
Antes de poder realizar el cálculo de cuantos miembros existen y cuál es el volúmen de transacciones,
el programa debe saber por supuesto, qué considera Ud. es un &quot;miembro&quot;? 
y que es una &quot;transacción&quot;?. <br>

Como Cyclos trabaja con gran cantidad de grupos de usuarios personalizados y tipos de transacciones, la aplicación no tiene
una noción predefinida de que es un &quot;miembro&quot;, ni que es una &quot;transacción&quot;.<br> 
Usted debe especificar esto en la ventana de &quot;filtros&quot;.
<br><br>
Dependiendo de las opciones seleccionadas, se visualizarán distintos filtros.<br>
Las siguientes opciones están disponibles:
<ul>
	<li><b>Filtro de miembro: </b> Mediante este menú desplegable, se especifican los 
	<a	href="${pagePrefix}groups#member_groups"><u>grupos de miembros</u></a> que Ud. desea filtrar.<br>
	Usted puede seleccionar más de un grupo, debiendo al menos seleccionar uno.<br>
	En la página de resultados, los grupos seleccionados son indicados como &quot;Miembros&quot;.
	<br><br>
	<li><b><a href="${pagePrefix}account_management#payment_filters"><u>Filtro de pago</u></a>:</b> 
	Con este filtro, se especifica el filtro de pago (tipos de transacción) que usted desea incluir en los resultados.<br> 
	Usualmente esto es lo que considera son las	&quot;transacciones&quot;.<br>
	Debe especificar un solo filtro de pago, a diferencia con la lista desplegable de
	&quot;filtros de pago<b></b>&quot;, que se describe abajo, en la que puede definir más de
	un filtro. Si ningún tipo disponible le es útil, siempre puede crear filtros de pago específicos para este propósito (ver abajo).<br>
	<br>
	<b>Advertencia:</b> Por lo general la lista desplegable de filtros de pago, contiene solamente pagos relevantes al filtro de grupo seleccionado. 
	Puede suceder que la lista se encuentre vacía: probablemente debido a que usted haya seleccionado un grupo que no posee pagos definidos. 
	En ese caso, no podrá obtener estadísticas.<br> 
	Para solucionar esto existen dos(2) maneras:<br> 
	Seleccionar otro grupo, o crear un nuevo filtro de pago para el grupo. <!-- Put this below in the section on payment filters, in another file -->
	<br><br>
	Para crear un nuevo filtro de pago para las estadísticas, usted debe:
	<ol>
		<li>Acceder al Menú: Cuentas> Administrar cuentas.
		
		<li>Hacer click el ícono de Edición de la &quot;Cuenta de miembro&quot;.
		
		<li>Ir al final de la página (cuadro Filtros de pago) y hacer click en el botón "Agregar un nuevo filtro de pago".
		
		<li>Aquí usted podrá completar las especificaciones del nuevo filtro de pago (ver
		ayuda en esta página). Defina un nombre identificativo, y no se olvide de
		seleccionar la casilla de verificación &quot;Mostrar en reportes&quot;, 
		sino dicho filtro no se mostrará en la página de estadísticas.<br>
		Puede seleccionar las transacciones a incluir en este filtro en la lista desplegable &quot;Tipos de transacción&quot;.<br>
		
		<li>El nuevo filtro de pago debería mostrarse ahora en la lista desplegable de filtros de pago de la página de estadísticas.
	</ol>
	En las páginas de resultados de las estadísticas, el tipo de transacción especificado será indicado como &quot;transacción&quot;.
	<br><br>
	<li><b>Filtros de pago:</b> (note la s al final). Esta es la misma opción que
	la anterior, solamente que se puede seleccionar más de un filtro. Si selecciona más
	de un filtro de pago, entonces los resultados de los filtros de pago seleccionados
	son comparados; y si selecciona solamente uno, es separado en los tipos de
	transacciones que lo componen, y se muestran los resultados para cada uno.<br>
	<br>
	<b>Advertencia:</b> al seleccionar más de un filtro de pago, es importante que las otras 
	opciones seleccionadas sean distintas y no se superpongan. Como cada filtro de
	pago contiene varios tipos de transacción, es posible que algunos filtros compartan
	tipos de transacción. En estos casos, no sería posible crear gráficas de torta,
	donde todas las secciones sumen 100%. Es por esto, que el programa realiza un
	chequeo para que los filtros no se superpongan.
	<br><br>
	<li><b>Filtro de broker: </b> Con este filtro, puede especificar que grupo(s)
	de usuario quiere considerar como <a href="${pagePrefix}brokering"><u>brokers</u></a>.
	Así como el filtro de miembro, puede especificar más de un grupo, pero al menos uno.<br>
	En la página de resultados a estos miembros se los indica como &quot;brokers&quot;.
</ul>
<br>
Aunque usualmente Cyclos muestra solamente los filtros relevantes, podría suceder que
ciertas combinaciones de filtros no tengan sentido. Por ejemplo, si selecciona un filtro
de pago y un filtro de grupo que no tienen nada que ver (por ejemplo, grupo: 
&quot;miembros&quot; y pagos: &quot;sistema a comunidad&quot;), entonces puede que se obtengan resultados extraños.
<br><br>
No todos los filtros son utilizados en todas las estadísticas. 
Usualmente, en la página de resultados, cada tabla o gráfica mostrará que filtros fueron 
<a href="#filtersUsed"><u>utilizados</u></a> exactamente para calcular los resultados.
<hr class="help">

<a name="results"></a>
<h2>Resultados Estadísticos</h2>
Estas son algunas convenciones utilizadas para presentar resultados estadísticos en cyclos.
Esta vista general explica estas convenciones:
<ul>
	<li><a href="#tables"><u>tablas</u></a> es la forma por defecto de presentación 
	de las estadísticas. Haga click en el enlace para ir a la descripción general de
	las tablas.
	<li><a href="#graphs"><u>gráficas</u></a> son solamente mostradas si se seleccionó
	la casilla de gráfica correspondiente. Haga click en el enlace para ir a la
	descripción general de las gráficas.
	<li><a href="#tests"><u>test estadísticos</u></a> se realizan cuando es
	razonablemente posible.
	<li><a href="#calculation"><u>convenciones generales</u></a> sobre el cálculo de las
	estadísticas pueden ser encontrados aquí.
	<li><a href="#numbers"><u>presentación y exactitud de los números</u></a>:
	generalmente un resultado de 3 significa algo diferente que uno de 3.00. Haga click
	en el enlace para ir a la descripción de las convenciones utilizadas en cyclos sobre
	la exactitud y presentación de números.
	<li>Como se comporta cyclos cuando no hay <a href="#nodata"><u>información suficiente
	</u></a> disponible.
</ul>

<hr>

<a name="tables"></a>
<h3>Tablas en Estadísticas</h3>
La forma de presentar información estadística por defecto en Cyclos, es con tablas. La
mayoría de las tablas tienen la siguiente forma:
<ul>
	<li><b>primera columna:</b> el <a href="#periods"><u>período</u></a> principal como
	fue definido en la página previa.
	<li><b>segunda columna:</b> el <a href="#periods"><u>período</u></a> para comparar
	con, si se lo definió en la página previa.
	<li><b>tercera columna:</b> el crecimiento relativo (en %) del segundo período con
	respecto al principal.
	<li><b>cuarta columna:</b> el <a href="#p"><u>valor-p</u></a> de un test
	estadístico, mostrando la diferencia de los números. Esta columna no se muestra en
	todos los casos.
</ul>
Las opciones de arriba son por lo general para &quot;comparar períodos&quot;; si selecciona
otro método, las tablas podrían ser diferentes. <br>
Si no especificó una gráfica, entonces debajo de la tabla, una pequeña <a
	href="#filtersUsed"><u>tabla</u></a> es mostrada, desplegando los <a href="#filters"><u>filtros</u></a>
que fueron especificados para este resultado. De lo contrario, esta información es mostrada debajo de la <a
	href="#graphs"><u>gráfica</u></a>.
<hr class="help">

<a name="graphs"></a>
<h3>Gráficas en estadísticas</h3>
Usualmente, la gráfica muestra la misma información que esta en la tabla de arriba.
Sin embargo, algunas columnas con información secundaria (que deriva de otras columnas) podrían no ser
visualizadas en la tabla, como ser el porcentaje de crecimiento entre dos <a href="#periods"><u>períodos</u></a>
o un <a href="#p"><u>valor-p</u></a>.
Por más información de los datos, por favor haga click en el ícono de ayuda de la página para la
tabla correspondiente (en la ventana arriba de la gráfica). Esta sección explica solamente lo relacionado
con convenciones generales utilizadas en gráficas.
<br><br>Si pasa el ratón por encima de una barra de la gráfica, el valor numérico correspondiente a los
datos es mostrado en el puntero del ratón <br>
Debajo de la gráfica una pequeña <a href="#filtersUsed"><u>tabla</u></a> es mostrada, conteniendo
los <a href="#filters"><u>filtros</u></a> que se especificaron para este resultado.
<hr class="help">


<a name="filtersUsed"></a>
<h3>Filtros utilizados</h3>
Bajo la gráfica o tabla, se despliega una tabla que contiene los <a href="#filters"><u>filtros</u></a>
seleccionados para obtener este resultado. Para hacer estos cálculos, cyclos necesita saber que tipo
de miembros deben ser incluidosen la gráfica - este es el filtro de
<a href="${pagePrefix}groups#group_filters"><u>grupo</u></a> . También debe saber que
&quot;transacción&quot; considerar - esto se especifica en el &quot;filtro de pago&quot;. 
También otros filtros pueden ser utilizados. <br>
Si un filtro no fue utilizado para el cálculo, se imprime &quot;no utilizado&quot;. Si por el
contrario el filtro fue utilizado, pero no se especificó ningún grupo o pago particular, 
entonces todos los miembros y pagos son incluidos. Solo los filtros relevantes son impresos en
esta tabla.
<hr class="help">

<a name="calculation"></a>
<h3>¿Cómo se calcula?</h3>
Generalmente, los resultados son calculados <i>sobre el <a href="#periods"><u>período</u></a></i>
especificado. Esto significa, que por ejemplo cualquier miembro que fue miembro en algún
momento en el período es incluído. En raras ocasiones, el resultado es solo sobre el 
estado al final del día del período; esto es siempre establecido en la ayuda específica. 
Note que no hay corrección para &quot;miembros parciales&quot;. Por ejemplo: el producto
bruto de cualquier miembro dentro del período es contado sin corrección para el
hecho que el miembro pudo haber sido miembro por solo un día dentro del período.<br>
La mayoría del tiempo, una <a href="#median"><u>mediana</u></a> es utilizada en los
resultados y no el promedio.
<hr class="help">

<a name="numbers"></a>
<h3>Presentación y exactitud de los números</h3>
Los números son generalmente presentados con un rango de ser posible. 
Este rango es un <a href="#range"><u>intervalo de confianza</u></a> de un 95% del
promedio o la <a href="#median"><u>mediana</u></a>. En las tablas, estos números 
se presentes en tres formas diferentes:
<ul>
	<li><b>12.0</b>significa que el intervalo de confianza no pudo ser creado,
	por falta de datos o porque el número no se baso en un grupo de números.
	<li><b>12.0&nbsp;&#177;&nbsp;3.4</b> significa que el intervalo de confianza
	es simétrico. 12.0 - 3.4 es el límite inferior del intervalo de confianza; 12.0 +
	3.4 es el límite superior.
	<li><b>12.0 (9.7 - 19.2)</b> significa que el intervalo de confianza es asimétrico,
	y la <a href="#distribution"><u>distribución distorsionada</u></a>.
	Los números entre paréntesis son el límite inferior y superior del intervalo de
	confianza.
	<li><b>-</b>&nbsp; significa que no se pudo calcular el valor, probablemente debido
	a que no hay información suficiente para hacer un cálculo confiable.
</ul>
En las gráfias, los intervalos de confianza son indicados como barras de error alrededor
de los puntos de datos.
<hr class="help">

<a name="nodata"></a>
<h3>Poca información</h3>
El análisis estadístico se basa en el principio de que un grupo de observaciones es juntado
y representado por un número o punto en una gráfica. <br>
<br>
¿Pero que sucede si la cantidad de observaciones es muy pequeña? <br>
Teóricamente, un test estadístico puede ser realizado con 3 o más observaciones en un punto
de datos. Sin embargo, esto hace al punto de datos poco confiable: cuanto más observaciones,
más confiables es el promedio, la mediana o el rango. <br>
<br>
Cyclos, no muestra resultados estadísticos con menos de 15 observaciones, ya que pensamos
que con menos de esa cantidad de información los resultados no son lo suficientemente confiables.
<hr class="help">

<h2>Resultados de Desarrollo Clave</h2>

<br><br>
<a name="reportsStatsKeydevelopmentsNumberOfMembers"></a>
<h3>Tabla para número de miembros</h3>
Por favor vea la <a href="#results"><u>sección general en resultados</u></a> para convenciones
generales en estadísticas de cyclos.
<br><br>Las siguientes filas son mostradas en esta tabla:
<ul>
	<li><b>Miembros</b>: el número de miembros según el <a href="#filters"><u>filtro</u></a> 
	seleccionado en la página anterior. Cuenta cualquier miembro durante el
	<a href="#periods"><u>período</u></a> especificado, aunque haya sido eliminado.
	<li><b>Nuevos miembros</b>: cuenta los miembros que han sido movidos al grupo de
	miembros especificado para el período ingresado.
	<li><b>Miembros desaparecidos</b>: cuenta todos los miembros que durante el período fueron
	removidos de otros grupos de miembros que no están en el filtro de grupo seleccionado.
</ul>
<hr class="help">

<a name="reportsStatsKeydevelopmentsGrossProduct"></a>
<h3>Tabla de Producto Bruto</h3>
Por favor vea la <a href="#results"><u>sección general en resultados</u></a> para convenciones
generales en estadísticas de cyclos.
<br><br>La única fila muestra el <a href="#grossProduct"><u>Producto Bruto</u></a>
para los <a href="#filters"><u>filtros</u></a> especificados.
<hr class="help">

<a name="reportsStatsKeydevelopmentsNumberOfTransactions"></a>
<h3>Tabla de Número de Transacciones</h3>
Por favor vea la <a href="#results"><u>sección general en resultados</u></a> para convenciones
generales en estadísticas de cyclos.
<br><br>La única fila muestra el <a href="#numberOfTransactions"><u>número de
transacciones</u></a> correspondientes a los <a href="#filters"><u>filtros</u></a> especificados.
<hr class="help">

<a name="reportsStatsKeydevelopmentsAverageAmountPerTransaction"></a>
<h3>Tabla de Monto por Transacción</h3>
Por favor vea la <a href="#results"><u>sección general en resultados</u></a> para convenciones
generales en estadísticas de cyclos.
<br><br>La única fila muestra el monto <a href="#median"><u>medio</u></a> por transacción 
de acuerdo a los <a href="#filters"><u>filtros</u></a> especificados.
<hr class="help">

<a name="reportsStatsKeydevelopmentsHighestAmountPerTransaction"></a>
<h3>Tabla de Monto Máximo por Transacción</h3>
Por favor vea la <a href="#results"><u>sección general en resultados</u></a> para convenciones
generales en estadísticas de cyclos.
<br><br>La única fila muestra el monto máximo por transacción de acuerdo a los
<a href="#filters"><u>filtros</u></a> especificados.
<hr class="help">

<a name="reportsStatsKeydevelopmentsNumberOfAds"></a>
<h3>Tabla de Número de Anuncios</h3>
Por favor vea la <a href="#results"><u>sección general en resultados</u></a> para convenciones
generales en estadísticas de cyclos.
<br><br>Se muestran las siguientes filas:
<ul>
	<li><b>Anuncios activos</b>: es el número de anuncios que no están vencidos ni
	agendados, sino activos al final del <a href="#periods"><u>período</u></a>.
	<li><b>Anuncios agendados</b>: al número de futuros anuncios (agendados) al final
	del período.
	<li><b>Anuncios vencidos</b>: el número de anuncios que vencieron (no son más validos)
	al final del período.
	<li><b>Anuncios creados</b>: el número de anuncios que han sido creados recientemente
	durante el período.
</ul>
<br><br>Note que los tres primeros ítems son <i>al final del período</i>,
mientras que el último <i>es para todo el período</i>.
<hr class="help">

<a name="reportsStatsKeydevelopmentsThroughTimeMonths"></a>
<a name="reportsStatsKeydevelopmentsThroughTimeQuarters"></a>
<a name="reportsStatsKeydevelopmentsThroughTimeYears"></a>

<h3>Tabla para &quot;A través del tiempo&quot;</h3>
Por favor vea la <a href="#results"><u>sección general en resultados</u></a> para convenciones
generales en estadísticas de Cyclos.
<br><br>Si selecciono &quot;a través del tiempo&quot; se mostrara un vista histórica general
para las opciones seleccionadas dentro del rango de tiempo ingresado. <br>
Las opciones son las mismas que las de &quot;comparar periodos&quot;. <br>
Resumiendo:
<ul>
	<li><b>número de miembros</b>: el número de miembros para cada <a
		href="#periods"><u>período</u></a>.
	<li><b>producto bruto</b>: para cada período.
	<li><b>número de transacciones</b>: para cada período.
	<li><b>monto por transacción</b>: monto promedio por transacción para cada
	período.
	<li><b>número de anuncios</b>: número de anuncios activos al último día de cada
	período.
</ul>
Donde el período puede ser meses, trimestres o años. Y por supuesto, todo relacionado con los
<a href="#filters"><u>filtros</u></a> especificados.
<hr class="help">

<h2>Resultados de Actividad del Miembro</h2>

<br><br>

<a name="reportsStatsActivitySinglePeriodGrossProduct"></a> <a
	name="reportsStatsActivityComparePeriodsGrossProduct"></a>
<h3>Tabla de Producto Bruto por miembro</h3>
Por favor vea la <a href="#results"><u>sección general en resultados</u></a> para convenciones
generales en estadísticas de cyclos.
<br><br>Esta tabla muestra la <a href="#median"><u>mediana</u></a> del <a href="#grossProduct">
<u>producto bruto</u></a> por miembro de dos formas:
<ul>
	<li>&quot;por miembros con rendimiento&quot;: solo los miembros que recibieron transacciones
	dentro del <a href="#periods"><u>período</u></a> son incluidos.
	<li><i>sobre todos los miembros</i>: se incluyen todos los miembros. Debido a la naturaleza
	de la <a href="#median"><u>mediana</u></a> este valor será cero (0) si más de la mitad de
	los miembros no tiene ingresos.
</ul>
Por supuesto que de acuerdo a los <a href="#filters"><u>filtros</u></a> especificados.
<br><br>Para &quot;comparar períodos&quot;, las columnas muestran el resultado para ambos períodos, 
luego dos columnas con el número de miembros en los cuales están basados los resultados, seguido
del crecimiento entre ambos períodos, y el <a href="#p"><u>valor-p</u></a> para probar que los dos
resultados no son diferentes. <br>
Para &quot;un período&quot;, solo se muestra el resultado y el número de miembros.<br>
<hr class="help">

<a name="reportsStatsActivitySinglePeriodNumberTransactions"></a> <a
	name="reportsStatsActivityComparePeriodsNumberTransactions"></a>
<h3>Tablea de Número de transacciones por miembro</h3>
Por favor vea la <a href="#results"><u>sección general en resultados</u></a> para convenciones
generales en estadísticas de cyclos.
<br><br>Esta tabla muestra la <a href="#median"><u>mediana</u></a> del <a
	href="#numberOfTransactions"><u>número de transacciones</u></a> por miembro en dos filas:<br>
<ul>
	<li>&quot;por miembros transaccionantes&quot;: solo los miembros que transaccionaron durante
	el <a href="#periods"><u>período</u></a> son inlcuidos - ya sea transferencias de entrada
	como de salida.
	<li>&quot;sobre todos los miembros&quot;: se incluyen todos los miembros. Debido a la naturaleza
	de la <a href="#median"><u>mediana</u></a> este valor será cero (0) si más de la mitad de
	los miembros no transaccionó.
</ul>
Por supuesto que de acuerdo a los <a href="#filters"><u>filtros</u></a> especificados.
<br><br>Para &quot;comparar períodos&quot;, las columnas muestran el resultado para ambos períodos, 
luego dos columnas con el número de miembros en los cuales están basados los resultados, seguido
del crecimiento entre ambos períodos, y el <a href="#p"><u>valor-p</u></a> para probar que los dos
resultados no son diferentes. <br>
Para &quot;un período&quot;, solo se muestra el resultado y el número de miembros.<br>
<hr class="help">

<a name="reportsStatsActivitySinglePeriodPercentageNoTrade"></a> <a
	name="reportsStatsActivityComparePeriodsPercentageNoTrade"></a>
<h3>Tabla de porcentaje de miembros que no están comercializando</h3>
Por favor vea la <a href="#results"><u>sección general en resultados</u></a> para convenciones
generales en estadísticas de cyclos.
<br><br>Se muestra solo una fila, que indica el porcentaje de los miembros que no comercializaron o
transaccionaron durante el <a href="#periods"><u>período</u></a> indicado (significando que
no pagaron ni cobraron nada). Como siempre sobre los &quot;miembros&quot; y
&quot;pagos&quot; definidos en los <a href="#filters"><u>filtros</u></a>
seleccionados.
<br><br>Para &quot;comparar períodos&quot;, las columnas muestran el resultado para ambos períodos, 
luego dos columnas con el número de miembros en los cuales están basados los resultados, seguido
del crecimiento entre ambos períodos, y el <a href="#p"><u>valor-p</u></a> para probar que los dos
resultados no son diferentes. <br>

<a name="reportsStatsActivitySinglePeriodLoginTimes"></a> <a
	name="reportsStatsActivityComparePeriodsLoginTimes"></a>
<h3>Tabla de conexiones por miembro, un período.</h3>
Por favor vea la <a href="#results"><u>sección general en resultados</u></a> para convenciones
generales en estadísticas de cyclos.
<br><br>Se muestra solo una fila, indicando el número de accesos por miembro para el <a
	href="#periods"><u>período</u></a>. Por supuesto para los &quot;Miembros&quot; según el
grupo de <a href="#filters"><u>filtro</u></a> especificado - se toma en cuenta todos los que
fueron miembros en algún momento dentro del período.
<br><br>Para &quot;comparar períodos&quot;, las columnas muestran el resultado para ambos períodos, 
luego dos columnas con el número de miembros en los cuales están basados los resultados, seguido
del crecimiento entre ambos períodos, y el <a href="#p"><u>valor-p</u></a> para probar que los dos
resultados no son diferentes. <br>

<a name="reportsStatsActivityThroughTimeGrossProduct"></a>
<h3>Tabla de Producto Bruto a través del tiempo</h3>
Por favor vea la <a href="#results"><u>sección general en resultados</u></a> para convenciones
generales en estadísticas de cyclos.
<br><br>Esta tabla le brinda una vista de la <a href="#median"><u>mediana</u></a> del <a
	href="#grossProduct"><u>producto bruto</u></a> por miembro para cada punto en el rango de tiempo
especificado. Hace esto para dos tipos de miembros:
<ul>
	<li>&quot;con ingresos&quot;: este es el producto bruto de los miembros que tuvieron ingresos
	dentro del mes, trimestre o año especificado.
	<li>&quot;todos&quot;: esto incluye a todos los miembros dentro del mes, trimestre o año
	especificado.
</ul>
Para ambos grupos, el número de miembros en el que se basa el producto bruto, esta dado por
el de las últimas dos columnas.<br>
Note que naturalmente, que cuanto mas corto es el lapso de tiempo de cada punto en la gráfica
o tabla, mas bajo será el resultado.
<hr class="help">

<a name="reportsStatsActivityThroughTimeNumberTransactions"></a>
<h3>Tabla para Número de transacciones por miembro a través del tiempo</h3>
Por favor vea la <a href="#results"><u>sección general en resultados</u></a> para convenciones
generales en estadísticas de cyclos.
<br><br>Esta tabla muestra la <a href="#median"><u>mediana</u></a> de <a
	href="#numberOfTransactions"><u>número de transacciones</u></a> por miembro sobre el rango
de tiempo especificado, donde se cuentan transacciones de entrada y salida.
<br>
Hace esto para dos tipos de miembro:
<ul>
	<li>&quot;comerciales&quot;: esto da el número de transacciones de los miembros que
	comercializaron en el mes, trimestre o año especificado (comercializar significa que 
	hicieron o recibieron pagos).
	<li>&quot;todos&quot;: esto incluye a todos los miembros disponibles en el mes,
	trimestre o año especificado.
</ul>
Para ambos grupos, el número de miembros en el que se basa el producto bruto, esta dado por
el de las últimas dos columnas.<br>
Note que naturalmente, que cuanto mas corto es el lapso de tiempo de cada punto en la gráfica
o tabla, mas bajo será el resultado.
<hr class="help">

<a name="reportsStatsActivityThroughTimePercentageNoTrade"></a>
<h3>Tabla de Porcentaje de miembros que no están comercializando, a través del tiempo</h3>
Por favor vea la <a href="#results"><u>sección general en resultados</u></a> para convenciones
generales en estadísticas de cyclos.
<br><br>Esta tabla da una vista general del porcentaje de miembros que no comercializaron durante el
 <a href="#periods"><u>período</u></a>, sobre el rango de tiempo especificado.
<br><br>Se considera que un miembro &quot;no comercializa&quot; si no realizó ninguna transacción
(hacer o recibir pagos de acuerdo al <a href="#filters"><u>filtro</u></a> especificado) 
en el mes, trimestre o año seleccionado. Por supuesto, cuando más corto el lapso de tiempo de
cada punto de la gráfica, será mayor el porcentaje de quienes no comercializaron. En una gráfica
con resultados mensuales, el porcentaje de quienes no comercializaron será mayor que en una
anualizada. <br>
La última columna de la tabla muestra el número de miembros sobre los que se basa el resultado.
<hr class="help">

<a name="reportsStatsActivityThroughTimeLoginTimes"></a>
<h3>Tabla de conexiones de miembros a través del tiempo</h3>
Por favor vea la <a href="#results"><u>sección general en resultados</u></a> para convenciones
generales en estadísticas de cyclos.
<br><br>Esta tabla da una vista histórica del número <a href="#median"><u>mediana</u></a> de accesos
que un miembro hizo al sistema sobre el rango de tiempo especificado.
No solo el número de accesos en cada mes, trimestre o año, sino que también se especifica en
la última columna el número de miembros sobre el cual se basa este cálculo.<br>
Note que cuanto más corto el lapso de tiempo entre cada punto de la gráfica, menor será el
número de accesos por miembro.
<hr class="help">

<a name="reportsStatsActivityHistogramGrossProduct"></a>
<h3>Distribución de Producto Bruto por miembro</h3>
Por favor vea la <a href="#results"><u>sección general en resultados</u></a> para convenciones
generales en estadísticas de cyclos.
<br><br>Esta gráfica proporciona un <a href="#histo"><u>histograma</u></a> del <a
	href="#grossProduct"><u>producto bruto</u></a> por miembro sobre el período principal,
según los <a href="#filters"><u>filtros</u></a> definidos.
<hr class="help">

<a name="reportsStatsActivityHistogramNumberTransactions"></a>
<h3>Distribución de Número de transacciones por miembro</h3>
Por favor vea la <a href="#results"><u>sección general en resultados</u></a> para convenciones
generales en estadísticas de cyclos.
<br><br>Esta gráfica proporciona un <a href="#histo"><u>histograma</u></a> del <a
	href="#grossProduct"><u>número de transacciones</u></a> por miembro sobre el período principal,
según los <a href="#filters"><u>filtros</u></a> definidos.
<hr class="help">

<a name="reportsStatsActivityHistogramLogins"></a>
<h3>Distribución de conexiones por miembro</h3>
Por favor vea la <a href="#results"><u>sección general en resultados</u></a> para convenciones
generales en estadísticas de cyclos.
<br><br>Esta gráfica proporciona un <a href="#histo"><u>histograma</u></a> de los accesos al sistema
por miembro sobre el período principal, según los <a href="#filters"><u>filtros</u></a> definidos.
<hr class="help">

<a name="reportsStatsActivityToptenGrossProduct"></a>
<h3>Los diez miembros más activos por Producto Bruto</h3>
Por favor vea la <a href="#results"><u>sección general en resultados</u></a> para convenciones
generales en estadísticas de cyclos.
<br><br>Esta tabla muestra los diez miembros con el mayor <a href="#grossProduct"><u>producto bruto</u>
</a> durante el principal <a href="#periods"><u>período</u> especificado</a>.
<hr class="help">

<a name="reportsStatsActivityToptenNumberTransactions"></a>
<h3>Los diez miembros más activos por Número de Transacciones</h3>
Por favor vea la <a href="#results"><u>sección general en resultados</u></a> para convenciones
generales en estadísticas de cyclos.
<br><br>Esta tabla muestra los diez miembros con el mayor <a
	href="#numberOfTransactions"><u>número de transacciones</u></a> durante el principal 
	<a href="#periods"><u>período</u> especificado</a>.
<hr class="help">

<a name="reportsStatsActivityToptenLogin"></a>
<h3>Los diez miembros más activos por Número de Conexiones</h3>
Por favor vea la <a href="#results"><u>sección general en resultados</u></a> para convenciones
generales en estadísticas de cyclos.
<br><br>Esta tabla muestra los diez miembros con el mayor número de accesos al sistema durante
el principal <a href="#periods"><u>período</u> especificado</a>.
<hr class="help">

<h2>Resultados de Actividad del Miembro</h2>

<br><br>

<a name="reportsStatsFinancesSinglePeriodOverview"></a>
<h3>Visión general para un período</h3>
Por favor vea la <a href="#results"><u>sección general en resultados</u></a> para convenciones
generales en estadísticas de cyclos.
<br><br>Esta tabla muestra una vista general de los pagos recibidos y realizados para la cuenta de
sistema especificada. La columna final muestra la diferencia entre gastos e ingresos.
<br><br>Los <a href="#filters"><u>filtros</u></a> de pago especificados son desplegados en la tabla,
mientras que los no especificados pero que son relevantes para esta cuenta son juntados y 
sumados como &quot;otros&quot;. Note que esta categoría de &quot;otros&quot; no es mostrada en
la gráfica, ya que es un número grande y anularía el resto de cuadro.
<br><br>Si selecciona solamente un filtro de pago, entonces este filtro se divide entre los tipos de
transacción que lo conforman, los cuales son mostrados en la tabla. De lo contrario se muestran
los filtros de pago.<br>
Si también se muestra una gráfica, debajo de la misma se muestran los filtros seleccionados;
de lo contrario se muestran debajo de la tabla.
<hr class="help">

<a name="reportsStatsFinancesSinglePeriodIncome"></a> <a
	name="reportsStatsFinancesSinglePeriodExpenditure"></a>
<h3>Ingresos o gastos para un período</h3>
Por favor vea la <a href="#results"><u>sección general en resultados</u></a> para convenciones
generales en estadísticas de cyclos.
<br><br>Esta tabla o gráfica da una vista general de los ingresos (en caso de haber seleccionado
ingresos) o egresos (en caso de haber seleccionado egresos) de una cuenta de sistema.<br>
La tabla muestra en las columnas los dos <a href="#periods"><u>período</u></a>s especificados,
, donde la tercer columna muestra el crecimiento del período II respecto al período I. <br>
Las filas de la tabla muestran los <a href="#filters"><u>filtros</u></a> de pago especificados.
<br><br>Es aconsejable no seleccionar demasiados filtros de pago para generar la tabla o gráfica.<br>
<hr class="help">

<a name="reportsStatsFinancesThroughTimeIncome"></a> <a
	name="reportsStatsFinancesThroughTimeExpenditure"></a>
<h3>Ingresos o egresos a través del tiempo</h3>
Por favor vea la <a href="#results"><u>sección general en resultados</u></a> para convenciones
generales en estadísticas de cyclos.
<br><br>Esta tabla muestra una vista general de los ingresos (o egresos) a través del tiempo. Le 
puede  interesar esta vista si desea ver como se desarrollaron ciertos gastos en el tiempo. <br>
Cada filtro de pago requerido tiene su propia columna en la tabla y sus series en la gráfica.
Se advierte no seleccionar muchos filtros de pago para la gráfica, ya que puede presentarse
superpoblada.
<hr class="help">

<br><br><a name="glossary"></a>
<h2>Glosario</h2>
<br><br>

<a name="range"></a><b>Intervalo de confianza</b><br>
El intervalo de confianza es un indicador de que tan precisa es la información. Significa
que hay una chance del 95% de que el miembro encontrado caiga dentro de estos rangos. Usualmente
la desviación estándar es utilizada para indicar la precisión de los datos. No utilizamos esto
mucho porque creemos que en un intervalo de confianza del 95% es mucho más intuitivo, y tiene
un significado en el mundo real (en contraste con la desviación estándar, que es abstracta).
<br><br>Los intervalos de confianza usualmente se calculan asumiendo que cierta 
<a href="#distribution"><u>distribución</u></a> de los datos subyacentes; en nuestro caso, como
la mayoría del tiempo no asumiremos una distribución normal, utilizamos una distribución de suma
de rangos sobre la mediana para calcular el intervalo de confianza. Esto significa que los valores
absolutos no son usados directamente, sino ranqueados de mayor a menor, y estos números utilizados
para el cálculo final. Esto puede llevar en algunas ocasiones a intervalos de confianza asimétricos.
<hr class='help'>

<br><br>
<a name="distribution"></a> <b>Distribución y desviamiento</b><br>
En las estadísticas, el resultado se basa en un conjunto de observaciones, de las cuales de 
calcula una media o <a href="#median"><u>mediana</u></a>. Todas estas observaciones juntas suelen
seguir cierto patrón, que es llamado distribución de los números. La distribución más natural
y frecuentemente encontrada es la llamada normal, donde el valor central es el más repetido, y los
más alejados del centro los menos abundantes. La distribución normal es perfectamente simétrica. <br>
La mayoría de los métodos estadísticos se basan en el supuesto de la distribución normal.
Sin embargo, la vida cotidiana no es siempre tan perfecta; la experiencia indica que los datos de la
mayoría de la base de datos no están normalmente distribuidos del todo. Los datos de Cyclos por lo
general parecen estar desviados, con distribuciones asimétricas. A modo de ejemplo: esto puede 
significar que los miembros que tienen un producto bruto por encima de la mediana, están más lejos
de la mediana de los que tienen producto bruto por debajo de la mediana.<br>
Para mostrar distribución de algo, utilizamos los &quot;<a href="#histo"><u>histogramas</u></a>&quot;.<br>
<hr class='help'>

<br><br>
<a name="grossProduct"></a> <b>Producto Bruto</b><br>
Esta es la suma de los montos de todas las transacciones que <b>ingresan</b> en cierto
<a href="#periods"><u>período</u></a>. <br>
O sea, todas las unidades ganadas en un período.
<hr class='help'>

<br><br>
<a name="histo"></a> <b>Histograma</b><br>
Un Histograma es una gráfica que muestra como de distribuyen las observaciones
de una población. Horizontalmente (en el eje x) en la gráfica está el parámetro que
a Ud. le interesa (por ejemplo: producto bruto por miembro). Esto se divide entre
grupos lógicos, por ejemplo: producto bruto entre 0 y 100 unidades por mes, 100 a 200 
unidades por mes, etc. Verticalmente, en el eje y, está el número de observaciones
de cada grupo en el eje de las x. Entonces para nuestro ejemplo: 5 miembros en el
grupo de 0 a 100 unidades, 20 miembros en el de 100 a 200 unidades, etc. <br>
La gráfica resultante muestra la <a href="#distribution"><u><u>distribución</u></u></a>
del producto bruto personal de los miembros.<br>
Note que en nuestros histogramas, las observaciones que caen exactamente en el
borde entre dos barras, son contadas como pertenecientes a la barra de la categoría
de mayor valor.

<br><br><b>Nota:</b> El programa calcula automáticamente la división óptima en el eje de
la x en bonitas &quot;clases&quot;. En caso de una distribución muy extraña (por 
ejemplo donde la mayoría de los miembros no han hecho nada) de datos, la división
de clases a lo largo del eje x no será visualmente óptimo.
<hr class='help'>

<br><br>
<a name="median"></a> <b>Mediana</b><br>
Normalmente, el promedio o la media se utilizan para indicar el valor de un número
basado en un conjunto de observaciones. Sin embargo, la media es muy sensible a 
valores extremos: si un miembro tiene una actividad 20 veces mayor al resto de los
miembros, este miembro influye fuertemente en el valor en la media, aún cuando el mismo
no sea del todo representativo. Una solución a este problema es el uso de la
mediana en vez del promedio. La mediana es el centro de la 
<a href="#distribution"><u>distribución</u></a>: 50% de todas las observaciones es 
menor a la mediana y el otro 50% mayor. La mediana no es sensible a los valores extremos. <br>
Como Cyclos trabaja con datos que usualmente tienen valores extremos o con distribución
desviada, para todas las estadísticas de Cyclos se utiliza la mediana - a no ser que se
indique lo contrario. El uso de la mediana en tales casos es estándar en el mundo de las
estadísticas.
<br><br>En caso de calcular una mediana de un grupo de números enteros, usamos
balanceo o corrección imparcial. El valor de la mediana perteneciente al conjunto
{0,1,2,2,3,3,3,3,4} y al {2,3,3,3,3,4,5,6,7} es en ambos casos 3, aunque en la primer lista
, el primer es tomado, y en la segunda lista, el último3. Aunque esto no parezca ser 
&quot;justo&quot;, todos los elementos con valor 3 se esparsen igualmente sobre un rango de
2.5 a 3.5, y luego el valor es recuperado de este rango por interpolación. Esto por supuesto
no tiene sentido con una lista de números decimales, por lo tanto esta aproximación es usada
solamente en números enteros.
<br><br>Usar la mediana en vez del promedio tiene unas pocas <b>consecuencias</b>:
<ul>
	<li>El resultado pueden ser números redondeados, especialmente en casos de una mediana
	en base a pocas observaciones. <br>
	<li>Como los rangos se calculan sobre números ranqueados, ciertos rangos sobre las
	medianas pueden ser asimétricos. Esto es bastante natural, aunque, considerando el hecho
	de que la distribución subyacente tampoco es simétrica.<br>
	<li>En caso de distribuciones con muchos ceros (más del 50%), la mediana será también 0. <br>
</ul>
<hr class='help'>

<br><br>
<a name="membersNotTrading"></a> <b>Miembros que no comercializan</b><br>
Estos son miembros que tienen cero (0) transacciones realizadas.
<hr class='help'>

<br><br>
<a name="N"></a> <b>n</b><br>
El número de ítems en un conjunto de números.
<hr class='help'>

<br><br>
<a name="numberOfTransactions"></a> <b>Número de transacciones</b><br>
En contraste al <a href="#grossProduct"><u>producto bruto</u></a>, para la
suma de algunas transacciones, tanto las que ingresan como las que egresan son
contabilizadas.
<hr class='help'>

<br><br>
<a name="p"></a> <b>P-valor</b><br>
Cuando se compara uno o más <a href="#periods"><u>períodos</u></a>, un test
estadístico es realizado cuando es posible. La meta de estos tests, es calcular
cuando diferente son dos valores.<br>
El resultado del test es representado por un número: el &quot;p-valor&quot;.
Este valor indica la chance de que dos medias (o medianas) son de una misma 
población. En lenguaje sencillo: Es la chance de que los números sean realmente
iguales. Cuando menor es el p-valor, más diferentes los dos números son. <br>
Por convención, decimos que los números son realmente diferentes si el p es menor que 5%.<br>
Esto es llamado &quot;diferencia estadística significativa&quot;. <br> 
En lenguaje sencillo: &quot;si p es menor que 0.05, decimos que los números son realmente diferentes.<br> 
Si p es mayor, entonces no podemos asegurar que sean o no diferentes, pero para estar en el lado seguro, 
asumimos que no lo son.&quot; <br>
Cualquier p-valor menor a 5% es escrito en <b>negrita</b>. <br>
<hr class='help'>

<br><br>
<a name="tests"></a><b>Test estadísticos</b><br>
Un test estadístico es usualmente realizado cuando se quiere saber si dos (o más)
resultados son realmente diferentes o no - por ejemplo, podría querer saber si la actividad
de miembros del año en curso se incrementó, en relación a la del año pasado. Es una
diferencia del 5% realmente una diferencia? Y una de 10%? 20% Donde comienza a llamar
diferencia a una diferencia real, y donde consideramos que es solo una coincidencia?<br>
Los test estadísticos pueden decirnos que tan <b>real</b> es esta diferencia, o si la misma
cae dentro de los rangos normales de variación y coincidencia, y eso no debería ser
considerado como una diferencia real. Esto depende de la 
<a href="#distribution"><u>distribución</u></a> subyacente, en el tamaño de la muestra y en
la variación de la población.<br>
El tipo de test es mencionado por lo general en al archivo de ayuda. Los más comúnmente
utilizados en Cyclos los de Wilcoxon RankSumTest, y el test binominal para dos muestras
iguales. Estos test no asumen ninguna distribución subyacente, porque en la mayoría de los
casos, no asumiremos distribuciones normales. <br>
El resultado de estos test se expresa como un <a href="#p"><u>p-valor</u></a>.<br>
<hr class="help">
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