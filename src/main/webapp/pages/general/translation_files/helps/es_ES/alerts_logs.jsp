<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>

<div style="page-break-after: always;">
<a name="alerts_logs_top"></a>
<span class="admin"> 
<br><br>

Las Alertas son utilizadas con el fin de notificar a los administradores sobre diversos eventos ocurridos en Cyclos, 
como errores de sistema o acontecimientos en relación a los miembros 
(cantidad máxima de errores permitidos en el inicio de sesión, préstamo vencido, código de seguridad bloqueado, etc.) 


<i>¿Dónde las encuentro?</i><br>
Las alertas y registros pueden ser accededidos a través del "Menú: Alertas". <br>
<br><br> 
<i> ¿Cómo hacerlas funcionar?</i><br>
Las <a href="${pagePrefix}alerts_logs#system_alerts"><u>Alertas de sistema</u></a> siempre se encuentran habilitadas.<br>
Las	<a href="${pagePrefix}alerts_logs#member_alerts"><u>Alertas de miembro</u></a> pueden ser configuradas en la página de 
<a href="${pagePrefix}settings#alerts"><u>configuración de alertas</u></a>.

<hr>

<a NAME="system_alerts"></a>
<h3>Alertas de sistema</h3>
Esta ventana muestra una lista con las alertas existentes en el Sistema.<br>
Son alertas relacionadas con el sistema (no directamente relacionados con cuentas de miembros).<br>
<br>
Las siguientes alertas de sistema (y sus respectivas notificaciones) se encuentran disponibles:	
<ul>
	<li><b><bean:message key='alert.system.ACCOUNT_FEE_CANCELLED'/></b>: Cargo de cuenta cancelado.<br>
	<!-- <i><u>Notificación</u>: El cargo en la cuenta {?} fue cancelado.</i>. -->
	
	<li><b><bean:message key='alert.system.ACCOUNT_FEE_FAILED'/></b>: Cargo de cuenta fallido.<br>
	<!--<i><u>Notificación</u>: El cargo en la cuenta {?} ha fallado.</i>.-->
	
	<li><b><bean:message key='alert.system.ACCOUNT_FEE_FINISHED'/></b>: Cargo de cuenta finalizado.<br>
	<!--<i><u>Notificación</u>: El cargo en la cuenta {?} ha finalizado.</i>.-->
	
	<li><b><bean:message key='alert.system.ACCOUNT_FEE_RECOVERED'/></b>: Cargo de cuenta no ejecutado fue recuperado.<br>
	<!--<i><u>Notificación</u>: El cargo en la cuenta {?} no fue ejecutado en el momento esperado. </i>.-->
	
	<li><b><bean:message key='alert.system.ACCOUNT_FEE_RUNNING'/></b>: Cargo de cuenta iniciado.<br>
	<!--<i><u>Notificación</u>: El cargo en la cuenta {?} se está procesando. </i>.-->
	
	<li><b><bean:message key='alert.system.ADMIN_LOGIN_BLOCKED_BY_PERMISSION_DENIEDS'/></b>: 
	Se ha alcanzado la cantidad máxima de permisos denegados para un administrador.<br>
	<!--<i><u>Notificación</u>: El acceso del administrador '{?}' al sistema está temporalmente bloqueado por exceder el máximo numero de permisos denegados. 
	La dirección IP es {?}</i>.-->
	
	<li><b><bean:message key='alert.system.ADMIN_LOGIN_BLOCKED_BY_TRIES'/></b>: Se ha alcanzado la cantidad máxima de intentos de login como administrador.<br>
	<!--<i><u>Notificación</u>: El acceso del administrador '{?}' al sistema está temporalmente bloqueado por exceder el máximo número de intentos. 
	La dirección IP es {?}</i>.-->
	
	<li><b><bean:message key='alert.system.ADMIN_TRANSACTION_PASSWORD_BLOCKED_BY_TRIES'/></b>: 
	La contraseña de transacción de administrador fue bloqueada por alcanzar el máximo de intentos inválidos.<br>
	<!--<i><u>Notificación</u>: La contraseña de transacción del administrador '{?}' está bloqueada por alcanzar la máxima cantidad de intentos inválidos ({?}). 
	La dirección IP es {?}</i>.-->
	
	<li><b><bean:message key='alert.system.APPLICATION_RESTARTED'/></b>: Aplicación reiniciada.<br>
	<!--<i><u>Notificación</u>: El programa se inició</i>.-->
	
	<li><b><bean:message key='alert.system.APPLICATION_SHUTDOWN'/></b>: Aplicación finalizada.<br>
	<!--<i><u>Notificación</u>: El programa finalizó</i>.-->
	
	<li><b><bean:message key='alert.system.ERROR_PROCESSING_ACCOUNT_STATUS'/></b>: Error crítico en el procesamiento del estado de cuenta.<br>
	<!--<i><u>Notificación</u>: Error crítico procesando el estado de cuenta. Detalle del pago\: en\: {?}, desde {?}, a {?}, monto {?}</i>.-->
	
	<li><b><bean:message key='alert.system.INDEX_REBUILD_END'/></b>: Reconstrucción del índice de búsqueda finalizada.<br>
	<!--<i><u>Notificación</u>: La reconstrucción de los índices de búsqueda para {?} finalizó</i>.-->
	
	<li><b><bean:message key='alert.system.INDEX_REBUILD_START'/></b>: Inicio de reconstrucción de índice de búsqueda.<br>
	<!--<i><u>Notificación</u>: La reconstrucción de los índices de búsqueda para {?} fue iniciada</i>.-->
	
	<li><b><bean:message key='alert.system.MAX_INCORRECT_LOGIN_ATTEMPTS'/></b>: 
	Se ha alcanzado la cantidad máxima de intentos de login como usuario inválido.<br>
	<!--<i><u>Notificación</u>: Se alcanzó la cantidad máxima de intentos de acceso al sistema ({?}) con el código de usuario incorrecto desde IP {?}</i>.-->
	
	<li><b><bean:message key='alert.system.NEW_VERSION_OF_APPLICATION_PAGE'/></b>: Nueva versión de página de aplicación.<br>
	<!--<i><u>Notificación</u>: Hay una nueva versión de una página de la aplicación personalizada, {?}. 
	Por favor verifique la personalización para resolver posibles conflictos</i>.-->
	
	<li><b><bean:message key='alert.system.NEW_VERSION_OF_HELP_FILE'/></b>: Nueva versión de archivo de ayuda.<br>
	<!--<i><u>Notificación</u>: Hay una nueva versión del archivo de ayuda personalizado, {?}. 
	Por favor verifique la personalización para resolver posibles conflictos </i>.-->
	
	<li><b><bean:message key='alert.system.NEW_VERSION_OF_STATIC_FILE'/></b>: Nueva versión de archivo estático.<br>
	<!--<i><u>Notificación</u>: Hay una nueva versión de un archivo estático personalizado, {?}. 	
	Por favor verifique la personalización para resolver posibles conflictos </i>.-->
	
	<li><b><bean:message key='alert.system.NULL_RATE'/></b>: Se encontró un ratio nulo.<br>
	<!--<i><u>Notificación</u>: Campo de base de datos {?} para las tasas contiene valor nulo no esperado para {?}</i>.-->
</ul>
Usted puede eliminar alertas del sistema:	
<ul> 
<li>Presionando el ícono de Eliminación 
<img border="0" src="${images}/delete.gif" width="16" height="16"> correspondiente;</li> 
<li>Seleccionando una o más alertas, y presionando el botón "Eliminar elemento seleccionado", 
ubicado en la parte inferior derecha de la ventana.</li>
</ul>
<br>
Sin embargo, existe un <a href="#alerts_history"><u>Historial de Alertas</u></a>, donde usted 
podrá buscar antiguas Alertas eliminadas de la lista. 
De esta forma es posible detectar alertas y patrones repetidos.
<hr class='help'>

<a name="member_alerts"></a>
<h3>Alertas de miembro</h3>
Esta ventana muestra una lista con las alertas relacionadas con el comportamiento de los miembros en el sistema.<br>
Los parámetros pueden establecerse en el "Menú: Configuración> <a href="${pagePrefix}settings#alerts"><u>Configuración de alertas</u></a>".<br>
<br>
Las siguientes alertas de miembro (y sus respectivas notificaciones) se encuentran disponibles:
<ul>
	<li><b><bean:message key='alert.member.LOGIN_BLOCKED_BY_TRIES'/></b>: El miembro es bloqueado por alcanzar el número máximo de intentos inválidos.<br>
	<!--<i><u>Notificación</u>: El acceso al sistema está temporalmente bloqueado por exceder la cantidad máxima de intentos. La IP del miembro es {?}</i>.--> 
	
	<li><b><bean:message key='alert.member.LOGIN_BLOCKED_BY_PERMISSION_DENIEDS'/></b>: Se ha alcanzado la cantidad máxima de permisos denegados para el miembro.<br>
	<!--<i><u>Notificación</u>: El acceso del miembro al sistema está temporalmente bloqueado por exceder la cantidad máxima de permisos denegados. 
	La dirección IP es {?}</i>.-->
	
	<li><b><bean:message key='alert.member.PIN_BLOCKED_BY_TRIES'/></b>: El PIN es bloqueado por alcanzar el número máximo de intentos inválidos.<br>
	<!--<i><u>Notificación</u>: El PIN fue bloqueado por exceder {?} intentos inválidos, en el canal {?}, en el miembro {?}</i>.-->
	
	<li><b><bean:message key='alert.member.TRANSACTION_PASSWORD_BLOCKED_BY_TRIES'/></b>: 
	La contraseña de transacción es bloqueada por alcanzar el número máximo de intentos inválidos.<br>
	<!--<i><u>Notificación</u>: La contraseña de transacción fue bloqueada por alcanzar el máximo de intentos inválidos</i>.-->
	
	<li><b><bean:message key='alert.member.INVOICE_IDLE_TIME_EXCEEDED'/></b>: Un Factura enviada de sistema a miembro ha vencido.<br>
	<!--<i><u>Notificación</u>: Una factura de {?}, enviada a {?}, ha excedido el tiempo máximo de espera</i>.-->
	
	<li><b><bean:message key='alert.member.DENIED_INVOICES'/></b>: El miembro rechazó demasiadas facturas.<br>
	<!--<i><u>Notificación</u>: El Miembro ha rechazado {?} facturas</i>.-->
	
	<li><b><bean:message key='alert.member.GIVEN_VERY_BAD_REFS'/></b>: El miembro dió demasiadas malas referencias ('Muy mala').<br>
	<!--<i><u>Notificación</u>: El Miembro ha dado {?} malas referencias</i>.-->
	
	<li><b><bean:message key='alert.member.RECEIVED_VERY_BAD_REFS'/></b>: El miembro recibió demasiadas malas referencias ('Muy mala').<br>
	<!--<i><u>Notificación</u>: El Miembro {?} ha recibido malas referencias</i>.-->
	
	<li><b><bean:message key='alert.member.EXPIRED_LOAN'/></b>: Préstamo vencido.<br>
	<!--<i><u>Notificación</u>: Préstamo vencido</i>.-->
	
	<li><b><bean:message key='alert.member.NULL_RATE'/></b>: Ratio nulo encontrado.<br>
	<!--<i><u>Notificación</u>: Campo de base de datos {?} para las tasas contiene valor nulo no esperado para {?}</i>.-->
	
	<li><b><bean:message key='alert.member.SCHEDULED_PAYMENT_FAILED'/></b> Pago agendado fallido.<br>
	<!--<i><u>Notificación</u>: Un pago agendado de {?} del tipo {?} ha fallado</i>.-->
	
	<li><b><bean:message key='alert.member.BLOCKED_POS_USED'/></b> Se intenta utilizar un dispositivo POS marcado como bloqueado.<br>
	<!--<i><u>Notificación</u>: Intento de utilizar un dispositivo POS bloqueado con id {?} desde IP {?}</i>.-->
	
	<li><b><bean:message key='alert.member.CARD_SECURITY_CODE_BLOCKED_BY_TRIES'/></b> 
	El código de seguridad de la tarjeta es bloqueado por alcanzar el número máximo de intentos inválidos.<br>
	<!--<i><u>Notificación</u>: El código de seguridad de la tarjeta fue bloqueado por exceder {?} intentos, para la tarjeta {?}</i>.-->
</ul>
Usted puede utilizar el ícono de Eliminación <img border="0" src="${images}/delete.gif" width="16" height="16"><b></b> 
correspondiente, para eliminar una alerta de la lista.<br>
<br>
Sin embargo, existe un <a href="#alerts_history"><u>Historial de Alertas</u></a>, donde usted podrá buscar antiguas Alertas, eliminadas de la lista. 
De esta forma es posible detectar alertas y patrones repetidos.
<hr class='help'>


<A NAME="alerts_history"></A>
<h3>Historial de Alertas</h3>
Esta ventana le permite buscar antiguas alertas, aunque las mismas hayan sido eliminadas.<br> 
<br>
Si usted no completa los filtros disponibles, obtendrá una visión global 
de todas las alertas o avisos existentes (de Sistema o de Miembro).<br> 
<br>
Cuando se selecciona el tipo de alerta de "Miembro", se podrá filtrar por un miembro en particular y 
en la lista de alertas resultante de la búsqueda, se mostrará la identificación del miembro. <br>
También es posible buscar alertas por un rango de fechas, utilizando la selección de <i>Fecha desde</i> y <i>Fecha hasta</i>.<br>
<br>
Si desea visualizar las últimas alertas, puede hacerlo a través del 
"Menú: Alertas> Alertas de Sistema" y "Menú: Alertas> Alertas de miembro".<br>
<hr class='help'>

<a name="alerts_history_result"></a>
<h3> Resultado de búsqueda en historial de alertas </h3>
Esta ventana muestra todas las antiguas alertas que cumplan con los criterios de búsqueda
especificados por usted en la ventana superior.<br> 
<br> 
Pueden estar disponibles varias páginas de resultados, 
en la parte inferior derecha de la ventana usted podrá acceder a cada una de ellas. <br>
<br>
Si desea visualizar las últimas alertas, por favor, vaya al 
"Menú: Alertas> Alertas de sistema" y " Menú: Alertas> Alertas de miembro ". <br>
<hr class="help">

<A NAME="error_log"></A>
<h3> Resultado de búsqueda en el log de errores</h3>
Esta página muestra una lista con todos los errores de aplicación producidos en el sistema. <br>
Usted puede: 
<ul>
	<li>Abrir y visualizar los detalles del error, presionando el ícono de Visualización 
	<img border="0" src="${images}/view.gif" width="16" height="16"> correspondiente.</li>
	<li>Eliminar un error de la lista, presionando el ícono de Eliminación 
	<img border="0" src="${images}/delete.gif" width="16" height="16"> correspondiente.</li>
</ul>
Los errores eliminados permanecerán accesibles en el <a href="#error_history"><u>historial de errores</u></a>.
<hr class='help'>

<a name="error_history"></a>
<h3> Búsqueda en el historial de log de errores </h3>
Esta página le permite especificar un rango de fechas, con el fin de limitar los 
<a href="#error_history_result"><u>resultados de la búsqueda</u></a>. 
<hr class="help">

<A NAME="error_history_result"></A>
<h3>Resultado de búsqueda en el historial de log de errores </h3>
Esta página muestra una lista con todos los errores de aplicación registrados en el período de tiempo 
(rango de fechas) especificado en la 
<a href="#error_history"><u>búsqueda en el historial de log de errores</u></a>.<br>
Si no es especificado un rango de fechas, se mostrará la lista completa de los errores de aplicación producidos.<br>
<br>
Usted puede abrir/visualizar directamente un error de la lista, haciendo click en el ícono de Visualización 
<img border="0" src="${images}/view.gif" width="16" height="16"> correspondiente. 
<br>
Pueden estar disponibles varias páginas de resultados, 
en la parte inferior derecha de la ventana usted podrá acceder a cada una de ellas.<br>
<br>
Los errores sólo aparecen en esta ventana si han sido eliminados del
<a href="#error_log"><u>log de errores</u></a> ("Menú: Alertas> Log de errores").
<hr class='help'>

<a name="error_log_details"></a>
<h3> Detalles de error </h3>
Esta página muestra los detalles del error de aplicación. 
Esta información ayudará al administrador de sistemas y desarrolladores de Cyclos para visualizar las causas del error.
<br><br>
Nota:</b> Un error de aplicación no es necesariamente un error.
Debido a la flexibilidad de la configuración de Cyclos, es posible configurar un
conflicto con la configuración de funciones. 
La mayoría de este tipo de errores son "atrapados" en Cyclos con un mensaje de error específico, 
pero no es posible prever todas las configuraciones posibles de errores.
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