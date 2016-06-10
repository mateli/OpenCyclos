<div style="page-break-after: always;">
<a NAME="top"></a>
<br><br>Registros de membros são tipos de
informações atribuídas a um membro em particular. Existem muitas
funcionalidades para definir e gerenciar os registros de membros.<br>
Você deve usar os registros de membros caso necessite gravar informações
sobre membros em uma base um-para-muitos (significando: um membro pode
ter muitos valores diferentes gravados em seus &quot;registros de
membro&quot;). Isto distingue os registros de membros dos <a
	href="${pagePrefix}custom_fields"><u>campos customizados</u></a>, onde
você também pode anexar informações customizadas a um membro, mas nesse
caso dos campos customizados, um membro pode ter apenas um valor em seu
campo customizado, e não vários.<br>
<br>
Você pode definir tipos diferentes e campos diferentes nos registros de
membros. Um exemplo simples é as &quot;observações&quot; de membros que
as versões anteriores do cyclos possuem. Uma observação é apenas uma
lista de campos de texto, que pode ser adicionado a um membro. Agora é
possível adicionar mais campos a um registro, e existe diferentes
maneiras deles serem exibidos. (explicado depois).
<span class="admin">
<br><br>Este manual possui um pequeno menu de <a href="#guidelines"><u>orientação</u></a>
que você pode selecionar para obter um <a href="#example"><u>exemplo</u></a>
mais ilustrativo de como usar os registros de membros.
<br><br><i>Onde encontrar.</i><br>
Registros de membros pode ser acessado através do <a
	href="${pagePrefix}profiles"><u>perfil</u></a> de um membro, bloco
&quot;Informações do membro&quot;. Você pode configurar registros de
membros através do &quot;Menu: Usuários & Grupos > Tipos de registros de
membro&quot;.
<br><br><i>Como ativar.</i><br>
Por favor verifique nossas <a href="#guidelines"><u>orientações</u></a>
para isso.
</span>
<span class="broker">
<br><br>Esses registros de membros são informações adicionais associadas
com cada membro. Você pode ver, adicionar, editar ou apagar esses
registros, dependendo das permissões definidas pela administração.
<br><br><i>Onde encontrar.</i><br>
Registros de membros podem ser acessados através do <a
	href="${pagePrefix}profiles"><u>perfil</u></a> de um membro. Os
registros de membros serão listados dentro do bloco &quot;Ações de
corretor para...&quot;.<br>
Existe também uma busca de registros de membros para cada tipo de
registro no menu &quot;Corretagem&quot;. Por exemplo o banco de dados
padrão possui um tipo de registro chamado &quot;Observações&quot; e este
será exibido no menu Corretagem. Nesta busca você pode <a
	href="#search_member_records"><u>procurar</u> por registros. 
</span>
<hr>

<span class="admin"> <a name="guidelines"></a>
<h3>Orientações para definir registros de membros</h3>
Para criar um novo registro de membro, os seguintes passos devem ser
feitos:
<ol>
	<li>Primeiro, pense claramente sobre oque você quer com o seu
	registro de membro. Que tipo de informação você gostaria de guardar?
	Não pode ser feito com um simples <a href="${pagePrefix}custom_fields"><u>campo
	customizado</u></a>?
	<li>Se você deseja criar um novo tipo de registro de membro, você
	precisa ter as permissões. Estas podem ser encontradas no bloco
	&quot;tipos de registros de membro&quot; nas <a
		href="${pagePrefix}groups#manage_group_permissions_admin_system"><u>
	permissões</u></a> de administração; Você precisa marcar a caixa
	&quot;gerenciar&quot;.
	<li>Após isso, você pode criar um novo tipo de registro de membro
	através do &quot;Menu: Usuários & grupos > <a
		href="#member_record_types_list"><u>Tipos de registros de
	membros</u></a>&quot; com o botão &quot;inserir novo tipo de registro de
	membro&quot;.
	<li>Uma vez criado o novo tipo de registro de membro, na <a
		href="#member_record_type_fields_list"><u>próxima tela</u></a> você
	terá a possibilidade de adicionar campos ao tipo de registro. Você deve
	adicionar pelo menos um campo para cada tipo de registro de membro, do
	contrário o tipo não manterá nenhuma informação, e será completamente
	inútil. Para alguns campos, você também necessitará criar valores
	possíveis (veja o <a href="#example"><u>exemplo</u></a>).
	<li><b>Configurar permissões:</b> Criado o tipo de registro, você
	precisa definir quem pode ver, modificar e apagar o registro de membro
	através do &quot;Menu: Usuários & grupos > Grupos de permissão&quot;,
	bloco &quot;Registros de membros&quot;. Você pode configurar isso tanto
	para grupos de administradores quanto para grupos de corretores.
	<li>O registro de membro será exibido como um botão abaixo do <a
		href="${pagePrefix}profiles"><u>perfil</u></a> do membro, na seção de
	&quot;Informações do membro&quot; das <a
		href="${pagePrefix}profiles#actions_for_member_by_admin"><u>Ações
	para...</u></a>. Isto será visível apenas para corretores ou administradores.
	Aqui você adicionar quantos itens deste registro você quiser.
	<li>Se a configuração do registro de membro possuir a opção
	&quot;Exibir item de menu&quot; marcada, você pode procurar valores
	deste registro de membro a partir do &quot;menu: Usuários &
	grupos&quot;.
	<li><b>Procurando:</b> Todos os registros de membros podem ser
	procurados através da página de &quot;ações&quot; abaixo do perfil de
	um membro. Você também pode procurar todos os registros de membros (não
	apenas relacionados a um membro) a partir do menu.
</ol>
<hr class="help">

<a name="example"></a>
<h3>Exemplo de registro de membro</h3>
As descrições de ajuda irão se referenciar a um exemplo específico de
tipo de registro de membro, para tornar o conceito de registro de membro
mais compreendivel. O exemplo é apenas um exemplo simples, e sem dúvida
uma configuração melhor de registros de membros é possível.
<ol>
	<li><b>Pense o que você precisa:</b> Neste exemplo, nós iremos
	criar um novo tipo de registro de membro, que é chamado "chamadas de
	ajuda por telefone" para acompanhar quantos membros estão ligando para
	o atendimento de ajuda, e com quais tipos de problemas.<br>
	<br>
	<li><b>Configurar as permissões:</b> &quot;menu: usuários & grupos
	> grupos de permissões&quot;, e consulte a ajuda destas telas, para
	como <a href="${pagePrefix}groups#manage_groups"><u>Configurar
	permissões</u></a>.<br>
	<br>
	<li><b>Criar novo tipo de registro de membro:</b> No &quot;Menu:
	Usuários & Grupos > <a href="#member_record_types_list"><u>Registros
	de membros</u></a>&quot; você tem que clicar no botão &quot;inserir novo tipo
	de registro de membro&quot;. Na <a href="#edit_member_record_type"><u>janela</u></a>
	seguinte, você irá preencher o seguinte:
	<ul>
		<li><b>Nome:</b> &quot;Chamadas de ajuda por telefone&quot;.
		<li><b>Rótulo:</b> &quot;Chamadas de ajuda por telefone&quot;.
		<li><b>Grupos:</b> Escolha os grupos de membros para os quais
		você deseja usar o novo tipo de registro de membro. Por exemplo
		&quot;Membros Plenos&quot;.
		<li><b>Layout do resultado da busca:</b> Como nós não estamos
		usando isso para nenhuma análise numérica, nós iremos selecionar
		apenas &quot;Plano&quot;.
		<li><b>Exibir item de menu:</b>Isto significa que o tipo de
		registro será exibido no &quot;menu: usuários & grupos&quot;. Nesta
		página você pode procurar por valores de registros com várias opções
		de busca.
		<li><b>Editável:</b> Não existe necessidade de alterar o registro
		após ele ser criado, assim nós não precisamos marcar esta opção.
	</ul>
	Após isso, nós clicamos em &quot;enviar&quot; para criar o novo tipo de
	registro de membro. Ele será exibido em nossa visão geral de <a
		href="#member_record_types_list"><u>tipos de registros de
	membro</u></a>. <br>
	<br>
	<li><b>Criar campos no tipo de registro de membro:</b> Você
	precisa criar campos no registro do membro, caso contrário o novo
	registro não tem sentido. Na visão geral dos <a
		href="#member_record_types_list"><u>tipos de registros de
	membro</u></a>, você deve clicar no ícone de edição <img border="0"
		src="${images}/edit.gif" width="16" height="16">&nbsp;, levando
	você à <a href="#edit_member_record_type"><u>janela de edição
	do tipo de registro de membro</u></a>. Use o botão &quot;inserir novo campo
	customizado&quot; para cada novo campo que precisamos criar. Isto nos
	levará à tela da <a href="#member_record_type_fields_list"><u>lista
	de campos de tipo de registro de membro</u></a>.<br>
	Os campos são um tanto bobos, mas servem apenas como um exemplo.<br>
	<ul>
		<li><b>Campo de data:</b> Clicando no botão &quot;inserir novo
		campo customizado&quot; na &quot;lista de tipos de registro de
		membro&quot; nós chegamos na tela para criar um <a
			href="${pagePrefix}custom_fields#edit_custom_fields"><u>novo
		campo customizado</u></a>. Aqui nós preencheremos os seguintes (Campos de
		formulários não listados não são essenciais para o funcionamento do
		novo campo):
		<ul>
			<li><b>nome:</b> &quot;data&quot;
			<li><b>Tipo de dado:</b> &quot;data&quot;
		</ul>
		Preencha qualquer coisa nos outros campos do formulário, e clique em
		&quot;enviar&quot; para salvar.<br>
		<b>NOTE:</b> De fato, criar o campo de data não é sempre necessário,
		pois o cyclos automaticamente guarda a data de criação para cada
		entrada do registro de membro, assim você pode procurar baseado nessa
		data.
		<li><b>Campo tipo:</b> novamente clique no botão &quot;inserir
		novo campo customizado&quot; na &quot;lista de campos do tipo de
		registro de membro&quot;. Agora preencha o seguinte:
		<ul>
			<li><b>nome:</b> &quot;tipo&quot;
			<li><b>tipo de dado:</b> &quot;enumerado&quot;
			<li><b>tipo de campo:</b> &quot;seletor&quot;
			<li><b>Obrigatório:</b> deve ser marcado.
		</ul>
		Após clicar em enviar, você verá novamente a janela da &quot;lista de
		campos do tipo de registro de membro&quot;. Agora, nós ainda temos que
		definir valores possíveis para este novo campo, e você faz isso
		clicando no ícone de edição <img border="0" src="${images}/edit.gif"
			width="16" height="16">&nbsp; do novo campo &quot;tipo&quot;
		listado.<br>
		Isto o levará de volta ao formulário para &quot;editar campo
		customizado&quot;. Aqui, abaixo, clique no botão &quot;novo valor
		possível&quot; para informar os novos valores ( &quot;denúncia de
		outros membros&quot;, &quot;não compreende o cyclos&quot; e &quot;quer
		dizer que esta feliz&quot;. Terminado, estes valores devem aparecer na
		lista de <a href="${pagePrefix}custom_fields#possible_values"><u>valores
		possíveis</u></a>. No fim, clique em &quot;voltar&quot; para retornar a visão
		geral de campos.
		<li><b>Campo observações:</b> Finalmente nós adicionaremos o
		campo observações da mesma maneira:
		<ul>
			<li><b>nome:</b> &quot;observação&quot;
			<li><b>tipo de dado:</b> &quot;texto&quot;
			<li><b>tipo de campo:</b> &quot;editor de texto formatado&quot;

			
		</ul>
		Agora o novo tipo de registro esta pronto. Ele não será visível no
		menu principal até que você se desconecte e acesse novamente o
		sistema.
	</ul>
	<li><b>Adicionar dados:</b> Agora você pode começar a usar o
	registro de membro. Abaixo do perfil do membro, na seção de
	&quot;Informações do membro&quot; da janela de <a
		href="${pagePrefix}profiles#actions_for_member_by_admin"><u>Ações
	para...</u></a>, existirá um botão especial chamado &quot;chamadas de ajuda por
	telefone&quot; que o levará à janela para informar dados no seu novo
	registro de membro.
	<li><b>Você pode procurar dados no seu registro de membro
	através do &quot;menu: Usuários & grupos > chamadas de ajuda por
	telefone&quot;:</b>
</ol>
<hr class="help">
</span>


<span class="admin"> <a NAME="member_records"></a>
<h3>Editar registro de membro</h3>
Esta tela mostra os dados de um <a href="#top"><u>registro de
membro</u></a> de uma <a href="#edit_member_record_type"><u>lista</u></a> de
tipos de registros de membro.<br>
Ela exibe o usuário que criou o registro, quando este ja tiver sido
criado, e o conteúdo dos campos customizados que foram definidos no
&quot;( Menu: Usuários & grupos > <a href="#member_record_types_list"><u>tipos
de registros de membro</u></a> )&quot;. No campo do registro que foi definido
como &quot;Editável&quot;, a opção de modificar os dados do registro
também esta disponível.<br>
Se você possuir as permissões, você também pode criar um novo registro
clicando no botão &quot;enviar&quot; abaixo da página chamado &quot;
criar novo...&quot;
<hr class="help">
</span>

<span class="broker"> <a NAME="member_records"></a>
<h3>Editar registro de membro</h3>
Esta tela mostra os dados do <a href="#top"><u>registro de
membro</u></a>. Alguns tipos de registros de membros são editáveis. Registros de
membros editáveis podem ser modificados clicando no botão
&quot;Alterar&quot;, editando os campos, e clicando em
&quot;Enviar&quot;.<br>
Se você tiver permissões, você também pode criar um novo registro
clicando no botão de &quot;enviar&quot;, abaixo da página, chamado
&quot;criar novo...&quot;.
<hr class="help">
</span>

<span class="admin"> <a NAME="member_record_types_list"></a>
<h3>Listar tipo de registro de membro</h3>
Nesta janela, os tipos de <a href="#top"><u>registros de membro</u></a>
disponíveis são exibidos.
<ul>
	<li>Clicando no ícone de edição <img border="0"
		src="${images}/edit.gif" width="16" height="16">&nbsp;, as
	propriedades do tipo de registro de membro podem ser modificadas.
	<li>Clicando no ícone de apagar <img border="0"
		src="${images}/delete.gif" width="16" height="16">&nbsp;, o tipo
	de registro de membro é removido. Observe que isto só é possível caso o
	tipo de registro de membro ainda não tenha sido usado. Caso algum
	membro já possua informações nesse tipo de registro de membro, ele não
	poderá ser removido.
	<li>Para criar um novo tipo de registro de membro, clique no botão
	de &quot;Enviar&quot; chamado &quot;Inserir novo tipo de registro de
	membro&quot;. Se você precisar criar um novo tipo de registro de
	membro, você deve se remeter a estas pequenas <a href="#guidelines"><u>orientações</u></a>.

	
</ul>
<hr class="help">
</span>

<span class="admin broker"> <a NAME="remarks"></a>
<h3>Registro de membros do tipo plano</h3>
A informação que você pode informar nesta tela é um &quot;<a href="#top"><u>registro
de membro</u></a>&quot; do tipo plano. Na maior parte do tempo isto é definido
para a possibilidade de adicionar informações ao perfil de um membro. Os
campos são definidos pela administração. Para adicionar um registro
preencha os campos (os campos com um asterisco vermelho são
obrigatórios) e clique em &quot;enviar&quot;. Os registros existentes
para uma membro são listados na área de baixo.
<hr class="help">
</span>

<span class="admin"> <a NAME="search_member_records"></a>
<h3>Procurar registros de membros</h3>
Aqui você pode procurar nos registros de membros, preenchendo os campos
e clicando em &quot;enviar&quot;.
<ul>
	<li><b>Palavras-chaves:</b> permite que você procure em qualquer
	campo do tipo de <a href="#top"><u>registro de membro</u></a>.
	<li><b>Nome de usuário:</b> e...
	<li><b>Membro:</b> permite que você procure por registros através
	do usuário ao qual ele foi atribuído.
	<li><b>Data de criação:</b> pode ser usada para procurar registros
	pela data que ele foi criado. Cada tipo de registro de membro vem
	automaticamente com um campo de data de criação; Você não tem que criar
	este campo.
</ul>
Em adicional a estes campos esta qualquer campo customizado que você
tenha definido para o tipo de registro de membro (no &quot;Menu:
Usuários & Grupos > <a href="#member_record_types_list"><u>tipos
de registros de membros</u></a> &quot;.
<hr class="help">
</span>

<span class="admin broker"> <a NAME="search_records_of_member"></a>
<h3>Procurar registros de membros de um membro</h3>
Aqui você pode procurar nos registros de um membro específico,
preenchendo os campos e clicando em &quot;procurar&quot;.
<ul>
	<li><b>Palavras-chaves:</b> pode ser usado para procurar em
	qualquer campo do registro.
	<li><b>Data de criação:</b> é gravada para qualquer entrada nos
	registros; este campo é criado automaticamente para cada tipo de
	registro de membro.
	<li>Em adicional a estes campos esta qualquer campo customizado
	associado com o tipo de registro de membro em particular que esta
	definido por um administrador.
	<li>Você pode adicionar novos dados no registro de membro deste
	membro, clicando no botão &quot;enviar&quot;. Este botão é chamado
	&quot;criar novo &quot;, seguido pelo nome do tipo de registro de
	membro.
</ul>
<hr class="help">
</span>

<span class="broker admin"> <a
	NAME="member_records_search_results"></a>
<h3>Resultados da busca de registros de membros</h3>
Aqui estão os resultados da procura por registros de membro. O nome de
usuário, e os campos específicos do registro de membro, são exibidos no
resultado.
<ul>
	<li>Para ver todo o registro clique no ícone de visualizar <img
		border="0" src="${images}/view.gif" width="16" height="16">
	&nbsp;.
	<li>Para modifica-lo clique no ícone de edição <img border="0"
		src="${images}/edit.gif" width="16" height="16">.
	<li>Para remove-lo clique no ícone de apagar <img border="0"
		src="${images}/delete.gif" width="16" height="16">. &nbsp;
	&nbsp;
</ul>
<hr class="help">
</span>

<span class="admin"> <a NAME="edit_member_record_type"></a>
<h3>Modificar ou criar tipo de registro de membro</h3>
Aqui você pode modificar a estrutura do <a href="#top"><u>registro
de membro</u></a>. Para modificar o registro, selecione &quot;Alterar&quot;,
edite os campos e clique em &quot;Enviar&quot;. Se você esta criando um
novo tipo, você não precisa clicar primeiro em &quot;alterar&quot;. Para
criar um novo tipo, você pode querer verificar estas <a
	href="#guidelines"><u>orientações</u></a> e nosso <a href="#example"><u>exemplo</u></a>.
<ul>
	<li><b>Nome:</b> é auto-explicativo.
	<li><b>Rótulo:</b> é usado pela interface do usuário e na maior
	parte do tempo deve ser o nome do registro de membro na forma plural.
	<li><b>Descrição:</b> é um texto de descritivo do propósito e
	significado do registro. Você pode preencher qualquer coisa que quiser.

	
	<li><b>Grupos:</b> esta caixa permite a você selecionar quais
	grupos de usuários pode ter este tipo de registro de membro atribuído a
	ele. Se um registro é atribuído a um grupo de membros, então o novo
	tipo de registro de membro é mostrado como um botão na seção de
	&quot;Informações do membro&quot; na janela de <a
		href="${pagePrefix}profiles#actions_for_member_by_admin"><u>Ações
	para...</u></a> abaixo do perfil do membro (visível apenas para
	administradores).
	<li><b>Layout do resultado da busca:</b> isto permite a você
	escolher como os dados do registro aparece no resultado da busca. As
	opções são:
	<ul>
		<li><b>Plano:</b> cada item é listado abaixo do item anterior,
		separados por uma linha. Isto é mais apropriado para observações.
		<li><b>Lista:</b> itens são exibidos no formato de uma tabela,
		com colunas e linhas. Cada registro é uma linha da tabela.
	</ul>
	<li><b>Exibir item de menu:</b> Se marcado, existirá um item de
	menu com o nome do tipo na interface do administrador, dentro da seção
	&quot;Usuários & Grupos&quot;. Você pode usar este item de menu para <a
		href="#search_member_records"><u>procurar</u></a> no tipo de registro
	de membro por valores específicos.
	<li><b>Editável:</b> se marcado, os dados do registro podem ser
	modificados após terem sido criados (por um administrador ou corretor).
	Caso esta opção não seja marcada, os dados do registro não poderão ser
	editados após a criação.
</ul>
<br><br>Em adicional para modificar as propriedades de um registro, você
também pode criar e modificar &quot;Campos customizados&quot; na <a
	href="#member_record_type_fields_list"><u>janela abaixo</u></a>, caso
contrário o registro de membro será vazio e sem significado.
<hr class="help">

<a NAME="member_record_type_fields_list"></a>
<h3>Lista de campos do tipo de registro de membro</h3>
Aqui os campos do registro de membro são listados. Os campos são onde os
dados do registro são gravados e indexados. Para que um registro de
membro seja útil, ao menos um campo customizado deve estar presente.
<ul>
	<li>Para criar um novo campo customizado clique no botão de
	&quot;Enviar&quot; chamado &quot;Inserir novo campo customizado&quot;.

	
	<li>Para alterar a ordem que os campos aparecem clique no botão
	chamado &quot;Alterar ordem dos campos&quot;. Este botão é visível
	apenas quando aplicável.
	<li>Para editar o campo clique no ícone de edição <img border="0"
		src="${images}/edit.gif" width="16" height="16"> &nbsp;.
	<li>Para apagar um campo clique no ícone de apagar <img border="0"
		src="${images}/delete.gif" width="16" height="16"> &nbsp;.
</ul>
<hr class="help">
</span>
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