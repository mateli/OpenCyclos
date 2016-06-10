<div style="page-break-after: always;">
<a name="loan_groups_top"></a>
<br><br>

Los grupos de préstamo permitirán a los miembros ser colectivamente responsables de un préstamo. <br>
Dar un préstamo para un grupo de préstamo significa, que el miembro responsable del grupo de préstamo recibirá el préstamo. 
Todos los miembros también podrán ver y pagar el préstamo. 

<i> ¿Dónde los encuentro? </i>
<br>
A los grupos de préstamo se puede acceder a través del
<span class="admin"> "Menú: Usuarios & Grupos> Grupos de préstamos". <br>
Para visualizar este tema, el administrador debe tener los correspondientes 
<a href="${pagePrefix}groups#manage_group_permissions_admin_member"><u>permisos</u></a> (bloque grupos de préstamos).

<br><br>
<i> ¿Cómo hacerlos funcionar? </i> <br>
Una vez que haya creado un grupo de préstamo, usted podrá agregar los miembros; tal como se describe a continuación en: <br>
¿Cómo <a href="#create_loan_group"><u>crear grupo de préstamo</u></a>?<br>
Con el fin de dar un préstamo a un grupo de préstamo, debe existir el grupo y el administrador tener los 
<a href="${pagePrefix}groups#manage_group_permissions_admin_member"><u>permisos</u></a> 
para dar el préstamo (en el bloque permisos de "préstamos"). <br>

Existen otros <a href="${pagePrefix}groups#edit_member_group"><u>ajustes a los grupos de préstamo</u></a> 
que pueden ser definidos para cada miembro del grupo. <br>

Puede dar un préstamo a través del "Menú: Usuarios & Grupos> Grupos de préstamos"> Editar grupo de préstamo> Otorgar préstamo, 
o a través de las 
<a href="${pagePrefix}profiles#actions_for_member_by_admin"><u>acciones para miembro</u></a> 
del administrador (bloque Préstamos> Otorgar préstamo). </span>

<span class="member"> "Menú: Cuenta> Grupos de préstamos". </span>
<hr>
<span class="admin">
<a NAME="search_loans_group"> </a>
<h3> Buscar grupos de préstamos </h3>
Esta página permite buscar <a href="#loan_groups_top"><u>grupos de préstamos</u></a> en el sistema.<br>
Usted puede buscar por el nombre del grupo de préstamo, descripción o por un miembro perteneciente al grupo 
(por código de miembro o por su nombre).
<br><br> 
Luego de completar los campos deseados, haga click en el botón "Búsqueda". 
Los grupos se mostrarán en la ventana de <a href="#search_loans_group_result"><u>resultados de la búsqueda</u></a>, ubicada en la parte inferior.
<br><br>
Un nuevo grupo de préstamo puede ser creaado haciendo click en el botón "<a href="#create_loan_group"><u>Crear grupo de préstamo</u></a>".
<hr class="help">
</span>

<span class="admin">
<a name="create_loan_group"></a>
<h3> Nuevo grupo de préstamo </h3>
Esta ventana le permite crear un nuevo grupo de préstamo.<br> 
Sólo debe introducir un Nombre para el nuevo grupo, una Descripción, y presionar el botón "Aceptar".<br>
<br><br> 
El grupo recién creado se mostrará en la ventana de <a href="#search_loans_group_result"><u>resultado de búsqueda de grupos de préstamo</u></a>.<br>
El grupo se encuentra creado, pero aún vacío. Usted puede agregar los miembros integrantes mediante la modificación
del grupo de préstamo. Para ello, haga click en el ícono de Modificación 
<img border="0" src="${images}/edit.gif" width="16" height="16"> correspondiente, 
ubicado en la ventana de resultados de búsqueda de grupos de préstamo.
<hr class="help">
</span>

<span class="admin">
<a name="search_loans_group_result"></a>
<h3> Resultado de búsqueda de grupos de préstamo </h3>
Esta ventana muestra los resultados de la <a href="#search_loans_group"><u>búsqueda de grupos de préstamo</u></a>.<br>
El Nombre de grupo y la Descripción del <a href="#loan_groups_top"><u>grupo de préstamo</u></a> son desplegados.<br> 
<br>
Presionando el ícono de Modificación <img border="0" src="${images}/edit.gif" width="16" height="16"> correspondiente, 
usted puede editar las propiedades del grupo de préstamo, agregar miembros o administrar los préstamos del grupo. 
<br><br> 
Para eliminar un grupo de préstamo, haga click en el ícono de Eliminación 
<img border="0" src="${images}/delete.gif" width="16" height="16"> correspondiente. 
Esta acción sólo es posible si el grupo no posee ningún préstamo abierto asignado.
<hr class="help">
</span>

<span class="admin">
<a name="loan_group_members_by_admin"></a>
<h3> Miembros en este grupo de préstamo (Administrador) </h3>
Esta ventana muestra los miembros integrantes de un <a href="#loan_groups_top"><u>grupo de préstamo</u></a>.<br>
El Código de miembro y Nombre son mostrados en la pantalla.<br>
<br>
Para eliminar un miembro del grupo, haga click en el ícono de Eliminación 
<img border="0" src="${images}/delete.gif" width="16" height="16"> correspondiente.<br>
Para agregar un miembro al grupo, ingrese el código de miembro o nombre (campos auto-completables) 
en los respectivos cuadros de edición y haga click en el botón "Agregar".
<hr class="help">
</span>

<span class="admin">
<a name="loan_group_detail_by_admin"></a>
<h3> Modificar grupo de préstamo </h3>
Esta ventana permite gestionar el <a href="#loan_groups_top"><u>grupo de préstamo</u></a> seleccionado.
Usted puede realizar las siguientes acciones:
<ul>
	<li> <b> Modificar: </b> Haga click en este botón para cambiar el nombre o descripción del grupo de préstamo. 
	Luego de realizar los cambios, haga click en el botón "Aceptar" para su confirmación.
	<li> <b> Ver préstamos: </b> Haga click en este botón para obtener una visión general de los
	<a href="${pagePrefix}loans"><u>préstamos</u></a> del grupo.
	<li> <b> Otorgar préstamo: </b> Haga click en este botón para otorgar un nuevo préstamo al grupo. 
	El miembro responsable del grupo recibirá el préstamo. Si los permisos son establecidos, todos los
	miembros del grupo podrán visualizar el contrato del préstamo, y pagarlo.
</ul>
<br><br>
<font color="#FF0000"><b>Importante:</b></font> 
El administrador sólo podrá "visualizar" los préstamos en su gestión a través de los grupos de préstamo. 
Las acciones mencionadas aquí, sólo pueden ser realizadas directamente en la página de gestión de grupos de préstamo 
("Menú: Usuarios & Grupos> Grupos de préstamos").
<hr class="help">
</span>

<a name="search_loans"></a>
<h3> Grupos de préstamo ... </h3>
Aquí puede obtener una visión general de los préstamos de los <a href="#loan_groups_top"><u>grupos de préstamo</u></a>. 
La forma es muy simple: sólo se puede seleccionar uno de los dos botones a la vista, préstamos "abiertos" o "cerrados".
<hr class="help">

<span class="member">
<a NAME="member_loan_groups_by_member"></a>
<h3> Mis grupos de préstamo </h3>
Aquí puede ver los <a href="#loan_groups_top"><u>grupos de préstamo</u></a> a los que pertenece. 
Para ver más información sobre un grupo de préstamo, haga click en el ícono
<img border="0" src="${images}/view.gif" width="16" height="16"> de visualización.
<hr class="help">
</span>

<span class="admin">
<a NAME="member_loan_groups_by_admin"></a>
<h3> Grupos de préstamo de miembro </h3>
Aquí usted puede visualizar los <a href="#loan_groups_top"><u>grupos de préstamo</u></a> a los que pertenecen los miembros.<br>
Para ver más información sobre un grupo de préstamo, haga click en el ícono de Visualización 
<img border="0" src="${images}/view.gif" width="16" height="16"> correspondiente.<br>
<br>
Para quitar un grupo de préstamo, haga click en el ícono de Eliminación 
<img border="0" src="${images}/delete.gif" width="16" height="16"> correspondiente.
Esta acción sólo será posible si NO existen préstamos abiertos para el grupo. 
<br><br>
Tenga en cuenta que los préstamos no los podrá visualizar aquí. 
Para ello, deberá acceder al Perfil del usuario> Ver préstamos; o al 
"Menú: Cuentas> Administrar préstamos".
<hr class="help">
</span>

<span class="member">
<a name="loan_group_detail_by_member"></a>
<h3> Detalles de grupo de préstamo </h3>
En este caso, se muestran el nombre del grupo de préstamo y la descripción. <br>
Los miembros de este grupo son mostrados en la <a href="#loan_group_members_by_member"> <u>
ventana inferior </u> </a>.
<hr class="help">
</span>

<span class="admin">
<a name="add_member_loan_groups"></a>
<h3> Agregar miembro al grupo de préstamo</h3>
En esta ventana se puede añadir un miembro a un <a href="#loan_groups_top"> <u> grupo de préstamo </u> </a>.
Un miembro puede pertenecer a más de un grupo de préstamo. 
La forma es muy simple: basta con seleccionar un grupo de préstamo y hacer click en el botón "Aceptar".

<hr class="help">
</span>

<span class="member">
<a NAME="loan_group_members_by_member"></a>
<h3> Miembros de grupo de préstamo </h3>
Aquí son mostrados los integrantes de este grupo de préstamo, con su código de usuario y nombre.
Puede hacer click en cada uno de ellos para acceder a su <a href="${pagePrefix}profiles"> <u> perfil </u> </a>.
Usted no puede ver aquí sus préstamos (o los préstamos para el grupo de préstamo). 
Para ello, hay que ir al "Menú: Cuenta> Préstamos".

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