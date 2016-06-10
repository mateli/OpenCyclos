<div style="page-break-after: always;">
<br><br>Cyclos possui uma categorização dos
usuários em grupos. Cada usuário do sistema pode estar apenas em um
grupo. Existem três <a href="#group_categories"><u>categorias</u></a> de
grupos.<br>
Os grupos são usados para dar <a href="#permissions"><u>permissões</u></a>
aos usuários do programa. Um usuário não pode acessar funcionalidades do
Cyclos caso o grupo dele não tenha permissões para isso. Ë possível
mudar as permissões dos grupos, ou mover os usuários se um grupo de
permissão para outro.<br>
Além das permissões um grupo pode ter também <a
	href="#edit_member_group"><u>Configurações</u></a> específicas do
grupos que definem o comportamento do grupo, como limites e o tipo de
acesso que é permitido a ele. Grupos de membros possuem mais opções de
configuração que grupos de administradores. Nas configurações do grupo
de membro você pode definir por exemplo qual conta o grupo possui bem
como o layout e itens de conteúdo para o grupo específico.
<br><br>O fato do membro poder estar apenas em um grupo não significa que
toda configuração do sistema é específica de grupos. O sistema ainda é
muito flexível. Configurações e definições existem à nível de sistema,
nível de grupo e nível individual. Se as mesmas definições existem em
vários níveis, o nível mais baixo sempre terá prioridade. Por exemplo um
limite de crédito individual irá sobrescrever um limite de grupo. E uma
página customizada para o grupo (por exemplo a página de contato) irá
sobrescrever a página de contato do sistema.<br>
Muitas configurações podem ser definidas para grupos múltiplos. Por
exemplo uma contribuições pode ser configurada para ser cobrada de
diversos grupos de membros.
<br><br>Cyclos vem com uma série de grupos padrões. Normalmente estes
grupos devem ser o suficiente para rodar o sistema.<br>
Você pode usar os grupos padrões não apenas para dar permissões, mas
também para gerenciar seus membros. Por exemplo, se um membro esta para
ser removido do sistema, você pode apenas move-lo para o grupo de
&quot;membros removidos&quot; através da função &quot;alterar
grupo&quot;. Esta função também grava todas as trocas de grupos, com
data, hora e o administrador que efetuou a troca.<br>
Embora a configuração com os grupos padrões seja suficiente para a
maioria das organizações, também é possível criar novos grupos. Contudo
isto é algo que só deve ser feito após ter alguma experiência em rodar o
sistema.
<br><br><span class="admin"> <i>Onde encontrar.</i><br>
Gerenciamento de grupo pode ser acessado através do &quot;Menu: Usuários
& grupos > Grupos de permissões&quot;.<br>
Filtros de grupos podem ser acessados através do &quot;Menu: Usuários &
grupos > Filtros de grupos&quot;.


<br><br><i>Como ativar.</i><br>
Um novo membro sempre será parte de um grupo. Contudo o grupo precisa
ser selecionado na criação do novo membro ou administrador. Isto é feito
na seção &quot;Usuários &amp; grupos > <a
	href="${pagePrefix}user_management#search_member_by_admin"><u>Gerenciar
membros</u></a>&quot; e &quot;Usuários &amp; grupos > <a
	href="${pagePrefix}user_management#search_admin"><u>Gerenciar
administradores</u></a>&quot;.<br>
</span>
<hr>

<a name="group_categories"></a>
<h2>Categorias para grupos</h2>
Existem três categorias de grupos:
<ul>
	<li><a href="#member_groups"><u>Grupos de membros</u></a> -
	Membros normais, tem acesso a seção de membro do Cyclos.
	<li><a href="#broker_groups"><u>Grupos de corretores</u></a> - Uma
	espécie de &quot;super-membros&quot;, membros com algumas funções
	administrativas sobre uma série de outros membros.
	<li><a href="#admin_groups"><u>Grupos de administradores</u></a> -
	Usuários com funções administrativas.
</ul>
Esta separação em categorias é por questão de segurança, sendo assim é
impossível dar acidentalmente à um membro alguma
<a href="#permissions"><u>permissão</u></a>
de administrador.
<br>
Todos os grupos vem com permissões padrões, mas é possível modifica-las.

<hr>

<a name="member_groups"></a>
<h2>Grupos padrões de membros</h2>
Membros desses grupos tem acesso à seção de membro do Cyclos. Este
sistema vem com os seguintes grupos padrões de membro:
<ul>
	<li><a href="#inactive_members"><u>Membros inativos</u></a> : Caso
	este grupo seja definido com um grupo inicial, os usuários não poderão
	acessar, precisarão ser &quot;ativados&quot; (colocados em um grupo
	ativo) por um administrador antes de estarem aptos a acessar o sistema.










	
	<li><a href="#full_members"><u>Membros plenos</u></a> :&nbsp; Um
	membro normal.
	<li><a href="#disabled_members"><u>Membros desativados</u></a>
	:&nbsp;Membros temporariamente inativos.
	<li><a href="#removed_members"><u>Membros removidos</u></a>
	:&nbsp; Membros que deixaram o sistema definitivamente.
</ul>

Os grupos padrões não são fixos ou &quot;hard coded&quot; mas foram
criados usando as
<a href="#permissions"><u>permissões</u></a>
de grupos e as
<a href="#edit_member_group"><u>Configurações</u></a>
que nós acreditamos serem as normalmente usadas. Mas é possível
modificar estes grupos e criar novos grupos com diferentes
configurações.
<hr class="help">

<a name="inactive_members"></a>
<h3>Grupo de membros inativos</h3>
Quando um usuário de registra através da página de registro pública, ele
será colocado automaticamente no grupo de &quot;membros inativos&quot;.
Membros deste grupo não podem acessar o sistema e não possuem uma conta
ativa. Administradores de contas podem solicitar uma lista de membros
deste grupo; após a validação destes membros, eles podem ser colocados
em um grupo &quot;ativo&quot;. Normalmente o grupo de
<a href="#full_members"> <u>Membros plenos</u></a>
.
<br>
Como os membros inativos não possuem conta relacionada, significa que é
possível remover completamente os membros do sistema. Uma vez em um
grupo &quot;ativo&quot; (com uma conta) o membro não poderá mais ser
apagado, poderá apenas ser colocado no grupo de
<a href="#removed_members"><u>membros removidos</u></a>
. Mais informações sobre o estado ativo/inativo dos grupos podem ser
encontradas nesta
<a href="#group_details"><u>seção de ajuda</u></a>
.
<hr class="help">

<a name="full_members"></a>
<h3>Grupo de membros plenos</h3>
Este é o grupo normal para membros. Um &quot;membro pleno&quot; pode
acessar o sistema e selecionar qualquer funcionalidade de membro. Quando
um membro é movido de um grupo de pendentes para o grupo de membros
plenos ele é "ativado". Isto significa que ele pode receber uma conta
com o crédito inicial padrão (se configurado) e também receberá uma
senha para acessar o sistema.
<br>
Se configurado, na ativação pode ser enviado ao membro um &quot;E-mail
de ativação&quot; com as informações para ele acessar o sistema e o
estado da sus conta.
<hr class="help">

<a name="disabled_members"></a>
<h3>Grupo de membros desativados</h3>
Quando um administrador coloca um membro no grupo de &quot;membros
desativados&quot; o membro não poderá mais acessar o sistema. A conta
esta em estado de &quot;Hibernação&quot;. Membros neste grupo não são
cobrados por pagamentos de taxas e contribuições. A única funcionalidade
ativa que um &quot;membro desativado&quot; possui é que ele ainda pode
receber pagamentos (mas ele não pode acessar para ve-los).
<br>
Os anúncios de um membro desativado não serão exibidos nas buscas de
anúncios feitas por membros. Mas o perfil do membro desativado será
mostrado nas buscas de membros. Ao ver um perfil de um membro desativado
será exibida uma mensagem que o membro esta desativado, e que (neste
momento) não pode acessar o sistema.
<br>
Para ser reativado um membro deste grupo precisa ser colocado de volta
no grupo de
<a href="#full_members"><u>membros plenos</u></a>
por um administrador.
<br>
Uma razão típica para colocar um membro no grupo de desativados pode ser
a de que o membro tenha ido morar no exterior por um período limitado de
tempo (como quatro meses). Também pode ser usado para colocar membros
suspeitos neste grupo, para evitar que eles acessem o sistema.
<hr class="help">

<a name="removed_members"></a>
<h3>Grupo de membros removidos</h3>
A razão para mover um membro para o grupo de &quot;membros
removidos&quot; é que o membro deixou o sistema. Uma vez no grupo de
removidos um membro não pode mais ser movido para outro grupo. Os dados
ainda continuarão no banco de dados e serão visíveis pelos
administradores, mas servira apenas como uma função de backup.
<br>
Qualquer dado (anúncios, perfil) do membro removido não sera visível por
outros membros. Apenas o histórico de transações mostrará transações
passadas com esse membro. No caso do membro no histórico de transações
antigas clicar no nome de um membro removido, ao invés de mostrar o
perfil deste membro, sera mostrada uma mensagem dizendo que o membro foi
removido. No caso de o membro ter um membro removido em sua lista de
contatos, ele também recebera a mesma mensagem de alerta.
<br><br>O grupo de removidos atua meramente como a função de um arquivo.
Caso após alguns anos o sistema precise de uma limpeza, o administrador
saberá que os dados do grupo removidos poderão ser apagados com
segurança. <br>
<b>Note:</b> Existe uma exceção para esta regra. Um membro que nunca
tenha pertencido ao um grupo que possua contas, pode ser removido
permanentemente do sistema. Existe uma permissão administrativa separada
para isso. <br>
<hr>

<a name="broker_groups"></a>
<h2>Grupo padrão de corretores</h2>
Corretores são um tipo de &quot;Super Membros&quot; que podem ter
permissões para efetuar certas ações administrativas para uma serie de
outros membros. Eles tem acesso à seção de membro do Cyclos.
<br>
Os seguintes grupos padrões de corretores estão disponíveis no cyclos:
<ul>
	<li><a href="#full_brokers"><u>Corretores plenos</u></a>: O tipo
	normal, padrão de corretores.
	<li><a href="#disabled_brokers"><u>Corretores desativados</u></a>:
	Para corretores temporariamente desativados.
	<li><a href="#removed_brokers"><u>Corretores removidos</u></a>:
	Corretores removidos permanentemente.
</ul>

Como os grupos padrões de membros, os grupos padrões de corretores não
são fixos ou &quot;hard coded&quot;, mas foram criados usando as
<a href="#permissions"><u>permissões</u></a>
de grupos e as
<a href="#edit_broker_group"><u>Configurações</u></a>
que nós acreditamos serem as normalmente usadas. Mas é possível
modificar estes grupos e criar novos grupos com diferentes
configurações.
<hr class="help">

<a name="full_brokers"></a>
<h3>Grupo de corretores plenos</h3>
Um corretor é um membro com funções extras. Um corretor pode registrar
outros membros, e dependendo da configuração do sistema, pode possuir
certo acesso aos membros dos quais ele é corretor. Quando um corretor
registra um membro, este membro precisa ser ativado por um
administrador. Também é possível que um corretor possa colocar os
membros diretamente em um grupo &quot;ativo&quot;, mas isto precisa ser
configurado. Ainda é possível que o corretor receba
<a href="${pagePrefix}brokering#commission"><u>comissões</u></a>
sobre a atividade (transações) destes membros. Uma comissão esta
configurada para o grupo de corretores plenos, mas não esta ativada.
<hr class="help">

<a name="disabled_brokers"></a>
<h3>Grupo de corretores desativados</h3>
Um corretor neste grupo não pode acessar o sistema, do mesmo modo que um
membro. A conta ainda continua ativa, oque significa que o corretor pode
receber pagamentos e sera cobrado contribuições (se aplicável).
<br>
Você pode usar este grupo para desativar temporariamente um corretor,
por exemplo por causa de o corretor viajar por alguns meses, ou por ele
estar pendente de um inquérito sobre suspeita de abuso ou fraude.
<br><br>Veja a <a href="${pagePrefix}brokering"><u>seção de
corretor</u></a> para mais explicações.
<hr class="help">

<a name="removed_brokers"></a>
<h3>Grupo de corretores removidos</h3>
Este grupo é como o grupo de
<a href="#removed_members"><u>&quot;membros removidos&quot;</u></a>
. Se um corretor for colocado neste grupo o histórico do &quot;corretor
- membros&quot; sera visível para administradores.
<br>
<br><br>Veja a <a href="${pagePrefix}brokering"><u>seção de
corretor</u></a> para mais explicações.
<hr>

<a name="admin_groups"></a>
<h2>Grupos padrões de administradores</h2>
Usuários em grupos de administradores pode efetuar tarefas
administrativas no sistema. Eles tem acesso à seção administrativa do
Cyclos.
<span class="admin"> Cyclos vem com os seguintes grupos padrões
de administradores:
<ul>
	<li><a href="#system_admins"><u>Administradores de sistema</u></a>:
	Pode usar qualquer funcionalidade administrativa.
	<li><a href="#account_admins"><u>Administradores de contas</u></a>:
	Para gerenciamento relacionado a membros.
	<li><a href="#disabled_admins"><u>Administradores
	desativados</u></a>: Administradores temporariamente desativados.
	<li><a href="#removed_admins"><u>Administradores removidos</u></a>:
	Administradores removidos em definitivo.
</ul>

Estes grupos vem com as <a href="#permissions"><u>permissões</u></a>
padrões, mas é possível modifica-las. </span>
<hr class="help">

<span class="admin"> <a name="system_admins"></a>
<h3>Grupo de administradores de sistema</h3>
Usuários deste grupo pode usar qualquer função administrativa, incluindo
criar novos administradores, defirnir permissões e mudar configurações
do sistema. É uma boa prática usar o grupo de administradores de sistema
apenas para configuração e não para tarefas operacionais.
<hr class="help">
</span>

<span class="admin"> <a name="account_admins"></a>
<h3>Grupos de administradores de contas</h3>
Usuários neste grupo pode usar qualquer gerenciamento relacionado a
membros e anúncios. Um administrador de contas não pode modificar
nenhuma configuração do sistema. Administradores de contas também tem
acesso a todas funções de &quot;ver&quot;, como estado do sistema,
estatísticas, etc.
<hr class="help">
</span>

<span class="admin"> <a name="disabled_admins"></a>
<h3>Grupo de administradores desativados</h3>
Administradores neste grupo simplesmente não podem fazer nada, nem mesmo
acessar o sistema. Este grupo pode ser usado para desativar
temporariamente administradores sem ter que apaga-los.
<hr class="help">
</span>

<span class="admin"> <a name="removed_admins"></a>
<h3>Grupo de administradores removidos</h3>
Este grupo é para remover em definitivo um administrador do sistema.
Tenha cuidado, pois como os <a href="#removed_members"><u>membros
removidos</u></a>, não existe volta. Uma vez removido, o administrador não pode
ser movido de volta. A única opção ainda disponível é remove-lo
completamente do sistema e do banco de dados.
<hr class="help">
</span>

<span class="admin"> <a name="change_group"></a>
<h3>Alterar grupo</h3>
Aqui você pode alterar o grupo ao qual um membro (ou <a
	href="${pagePrefix}brokering"><u>corretor</u></a>) pertence. Apenas
escolha o novo a partir da caixa de seleção. Você deve escrever um
comentário sobre esta troca de troca na área de texto
&quot;Descrição&quot;. Você salva sua alteração clicando no botão
&quot;enviar&quot;.
<br><br>Clique aqui para uma visão geral sobre<a href="#member_groups"><u>grupos
de membros</u></a>.
<br><br>Após enviar sua troca de grupo, ela é colocada na caixa de
histórico, em ordem cronológica, a mais nova no topo. O histórico de
comentários mostra acima qualquer comentário, uma linha de estado com o
nome do administrador que efetuou a troca, a data e a atual troca de
grupo que foi feita (&quot; do grupo X para o Y&quot;).<br>
Deste forma os administradores pode ter uma rápida visão geral sobre o
que aconteceu com a conta de um membro e ler quais trocas foram feitas.
Qualquer outra informação adicional sobre um cliente deve ser incluída
nas <a href="${pagePrefix}profiles#member_info_actions"><u>observações</u></a>.










<br><br>Algumas observações em alterações de grupos:
<ul>
	<li>Quando um membro esta no grupo de <a href="#inactive_members"><u>&quot;membros
	inativos&quot;</u></a> você tem a opção de apaga-lo completamente do sistema.
	Isto pode ser útil para registros duplicados ou fraudulentos. Uma vez
	ativada a conta de um membro não pode mais ser apagada, mas você pode
	colocar o membro no grupo de &quot;membros removidos&quot;.
	<li>Quando você move um membro de um grupo de <a
		href="#full_brokers"><u>corretores</u></a> para um grupo de membro
	normal, todos os membros dos quais este membro é corretor ficarão sem
	corretor. (Isto não acontece se você mover o corretor para um outro
	grupo de corretores, por exemplo <a href="#disabled_brokers"><u>corretores
	desabilitados</u></a>). Se você não quer deixar os membros sem corretor, pode
	ser interessante primeiro alterar o corretor de todos esses membros, e
	só dai fazer a alteração de grupo do corretor antigo. isto pode ser
	feito através da funcionalidade de <a
		href="${pagePrefix}user_management#bulk_actions"><u>ações em
	massa</u></a>.
</ul>
<hr class="help">
</span>

<span class="admin"> <a name="change_group_admin"></a>
<h3>Alterar grupo de administrador</h3>
Aqui você pode alterar o <a href="#admin_groups"><u>grupo</u></a> ao
qual um administrador pertence. Apenas selecione o novo grupo, você pode
escrever um comentário sobre a alteração de grupo (por exemplo o porque
da troca) na área de texto &quot;descrição&quot;. Após terminar a
descrição clique no botão enviar para efetuar a alteração
<br><br>Após ter efetuado a alteração, ela é colocada na caixa de
histórico, em ordem cronológica, a mais nova no topo. O histórico de
comentários mostra acima qualquer comentário, uma linha de estado com o
nome do administrador que efetuou a troca, a data e a atual troca de
grupo que foi feita (&quot; do grupo X para o Y&quot;).
<br><br>Deste forma os administradores pode ter uma rápida visão geral
sobre o que aconteceu com a conta de um administrador e ler quais trocas
foram feitas. Qualquer outra informação adicional sobre um administrador
deve ser incluída nas observações do administrador.
<br><br>Nesta janela existe a opção de remover um administrador
completamente do sistema caso seja necessário. Contudo, a forma
preferida deve ser a alteração de grupo do administrador para um grupo
de &quot;Administradores desativados&quot;.
<hr class="help">
</span>

<span class="admin"> <a name="group_management"></a>
<h2>Gerenciamento de grupo</h2>
Você pode efetuar varias ações de gerenciamento de grupo no Cyclos. Você
pode alterar propriedades de um grupo, modificar permissões, e e você
pode adicionar ou remover grupos de permissões.

Gerenciamento de grupos pode ser acessado através do &quot;Menu:
Usuários & grupos > Grupos de permissões&quot;.
<hr>
</span>

<span class="admin"> <a name="search_groups"></a>
<h3>Procurar grupos</h3>
Você pode procurar por uma das <a href="#group_categories"><u>categorias</u></a>
na caixa de seleção &quot;tipo&quot; e caso o sistema possua <a
	href="#group_filters"><u>filtros de grupos</u></a> uma opção de busca
por esses filtros também estará disponível.
<hr class="help">
</span>

<span class="admin"> <a name="manage_groups"></a>
<h3>Gerenciar grupos de permissões</h3>
Com esta janela você pode gerenciar os vários grupos de permissões. Esta
janela da uma visão geral dos grupos disponíveis e a possibilidade de
criar novos grupos.
<br><br>Você pode clicar nos seguintes itens para cada grupo listado:
<ul>
	<li><img border="0" src="${images}/edit.gif" width="16"
		height="16">&nbsp;Clicando no ícone de edição, você abrirá uma
	página onde poderá definir as propriedades dos grupos.
	<li><img border="0" src="${images}/permissions.gif" width="16"
		height="16">&nbsp;Clicando no ícone de permissões, você abrirá
	uma página onde poderá definir as permissões para o grupos.
	<li><img border="0" src="${images}/permissions_gray.gif"
		width="16" height="16">&nbsp;Se o ícone de permissões for cinza,
	significa que nenhuma permissão pode ser definida para o grupo, pois
	ele é um grupo &quot;removido&quot;. Membros que são colocados neste
	grupo serão removidos do sistema, mas alguns de seus dados (ex.
	transações) ainda permanecerão. Para mais informações sobre este
	assunto acesse o <a href="#insert_group"><u>arquivo de ajuda</u></a> de
	&quot;inserir&quot; novo grupo.
	<li><img border="0" src="${images}/delete.gif" width="16"
		height="16">&nbsp;Clicando no ícone remover você poderá remover
	o grupo. Grupos só podem ser removidos caso não exista mais nenhum
	membro nele.
</ul>
Você pode clicar no botão &quot;Inserir novo grupo&quot; para adicionar
um novo grupo. Nós recomendamos para apenas fazer isso quando já possuir
mais experiência trabalhando com os grupos padrões. <br>
Clique aqui para uma visão geral sobre <a href="#group_categories"><u>categorias
de grupos</u></a>.
<hr class="help">
</span>

<span class="admin"> <a name="edit_admin_group"></a>
<h3>Modificar grupo de administrador</h3>
Aqui você pode editar/modificar as propriedades de um <a
	href="#admin_groups"><u>grupo de administrador</u></a>. Após clicar no
botão alterar, você pode alterar o nome, a descrição e as
&quot;definições de acesso&quot;. O estado pode ser definido apenas no
momento da criação do grupo.
<br><br>Observe que você não pode definir permissões aqui; Propriedades
do grupo e configurações não são o mesmo que permissões. Você pode
alterar as permissões do grupo clicando no <img border="0"
	src="${images}/permissions.gif" width="16" height="16"> ícone de
permissão na visão geral dos <a href="#manage_groups"><u>grupos</u></a>,
mas você também pode usar o botão de atalho abaixo da janela chamado
&quot;Permissões do grupo&quot;.<br>
<br><br>As seguintes configurações do grupo estão disponíveis:
<ul>
	<li><b>Tamanho da senha:</b> Tamanho mínimo e máximo da senha.<br>
	<li><b>Política de senha:</b> Você pode escolher entre 3 opções;
	os significados destas opções são óbvios (aniversários, sequencia de
	números, etc).<br>
	Caso uma política de senha seja escolhida, os usuários não poderão
	escolher uma nova senha que ja tenha sido usada no passado.
	<li><b>Número máximo de tentativas de senha:</b> Quando o usuário
	atinge o valor máximo de tentativas, o usuário não consegue mais ter
	acesso até que o tempo de desativação tenha se passado. (veja a próxima
	opção).<br>
	<li><b>Tempo de desativação após número máximo de tentativas
	de senha:</b> Este é o tempo em que o usuário não estará apto a acessar o
	sistema após ter atingido o número máximo de tentativas de senha.<br>
	<li><b>Senha de acesso vencerá após:</b> Com essa definição, você
	pode definir o período em que a senha de acesso será válida. se você
	colocar &quot;0&quot;, a senha nunca expirará.<br>
	<li><b>Senha de transação:</b> Aqui você pode definir o uso de uma
	senha especial para transações. Você tem as seguintes opções:
	<ul>
		<li><b>&quot;Não usada&quot;</b>: A senha de transação não é
		usada, e os membros podem fazer transações (caso tenham permissões é
		claro) sem ter que primeiro informar a senha de transação.
		<li><b>&quot;Automática&quot;</b>: Se esta opção é escolhida, o
		sistema irá gerar a senha de transação na criação da conta do novo
		membro (ou agora, para membros existentes). O membro irá receber a
		senha (apenas uma vez) na sua caixa de mensagens pessoais.
		<li><b>&quot;Manual&quot;</b>: Se escolhida esta opção, a senha
		de transação só sera gerada manualmente através da ação de <a
			href="${pagePrefix}profiles#access_actions"><u>
		&quot;Gerenciar senha de transação&quot;</u></a> na página de perfil do
		membro. Mais informações sobre senha de transação pode ser encontrada
		naquela página.
	</ul>
	<li><b>Tamanho da senha de transação:</b> Define o tamanho da
	senha de transação. Esta senha sempre terá um tamanho fixo.
	<li><b>Número máximo de tentativas de senha de transação:</b> Após
	este número de tentativas, a senha de transação sera bloqueada. Um
	administrador pode reiniciar a senha através da ação de <a
		href="${pagePrefix}profiles#access_actions"><u>&quot;Gerenciar
	senha de transação&quot;</u></a>.</li>
</ul>
Não esqueça de clicar no botão &quot;enviar&quot; apos ter feito suas
adaptações.
<hr class="help">
</span>

<span class="admin"> <a name="edit_member_group"></a>
<h3>Modificar grupo de membro</h3>
Você pode editar/modificar as propriedades de um <a
	href="#member_groups"><u>grupo de membro</u></a> aqui. Após clicar no
botão &quot;alterar&quot;, você pode trocar o nome, a descrição e uma
série de categorias de definições.
<br><br>Observe que você não pode definir permissões aqui; Propriedades
do grupo e configurações não são o mesmo que permissões. Você pode
alterar as permissões do grupo clicando no <img border="0"
	src="${images}/permissions.gif" width="16" height="16"> ícone de
permissão na visão geral dos <a href="#manage_groups"><u>grupos</u></a>,
mas você também pode usar o botão de atalho abaixo da janela chamado
&quot;Permissões do grupo&quot;.
<br><br>As configurações do grupo de membro estão ordenadas por
categorias. As seguintes categorias estão disponíveis; Você pode clicar
nos links para obter detalhes sobre os campos destas categorias:
<ul>
	<li><b><a href="#group_details"><u>Detalhes do grupo</u></a></b>
	Da o resumo principal.
	<li><b><a href="#group_registration_settings"><u>Configurações
	de registro</u></a></b> São configurações definindo o comportamento do grupo
	conectado ao registro do membro. Ela também contém algumas
	configurações diversas.
	<li><b><a href="#group_access_settings"><u>Configurações
	de acesso</u></a></b> são configurações definindo o acesso do grupo.
	<li><b><a href="#group_notification_settings"><u>Configurações
	de notificação</u></a></b> Tudo sobre notificação por email para este grupo.
	<li><b><a href="#group_ad_settings"><u>Configurações
	de anúncios</u></a></b> são configurações definindo o comportamento do grupo em
	relação aos anúncios.
	<li><b><a href="#group_scheduled_payment_settings"><u>Configurações
	de pagamentos agendados</u></a></b> são configurações sobre <a
		href="${pagePrefix}payments#scheduled"><u> pagamentos
	agendados</u></a> para esse grupo.
	<li><b><a href="#group_loans_settings"><u>Configurações
	de empréstimo do grupo</u></a></b> são configurações definindo o comportamento do
	grupo conectado a empréstimos.
</ul>
<hr class="help">
</span>

<span class="admin"> <a name="group_details"></a>
<h3>Detalhes do grupo</h3>
Aqui você pode definir algumas configurações gerais de grupo. As
seguintes opções estão disponíveis:
<ul>
	<li><b>Tipo:</b> O tipo do grupo (membro, corretor,
	administrador). Isto é definido na <a href="#insert_group"><u>criação
	do grupo</u></a> e não pode ser alterado.
	<li><b>Removido</b> Este campo mostra se o grupo é um &quot;grupo
	removido&quot;. Isto também é definido na <a href="#insert_group"><u>criação
	do grupo</u></a> e não pode ser alterado.
	<li><b>Nome:</b> O nome do grupo. também definido na criação do
	grupo, mas pode ser alterado.
	<li><b>Nome da página de acesso:</b> Esta opção será exibida
	apenas se você cutomizar a página de acesso para este grupo (em
	arquivos customizados, janela abaixo). A Página de acesso customizada
	(grupo) pode ser acessada colocando o nome da página de login após a
	página de acesso &quot;global&quot; com um caractér de interrogação. O
	nome da página de acesso não pode possuir espaços. Um exemplo pode ser
	:<br>
	http://www.seudominio.org/cyclos?nomedapaginadeacessodogrupo.<br>
	Observe que é possível definir um nome de página de acesso por <a
		href="${pagePrefix}groups#group_filter"><u>filtro de grupo</u></a>.
	<li><b>Url da página container</b> Esta configuração é usada caso
	você queira acessar o Cyclos a partir de um site web. Esta configuração
	funciona da mesma forma que a página container global (veja <a
		href="${pagePrefix}settings#local"><u>Configurações -
	Configurações locais</u></a>) mas apenas para este grupo. Neste campo você deve
	informar a página que abre o iframe ou frame-set que inclui o Cyclos.
	Por exemplo: http://www.seudominio.org/cycloswrapper.php.<br>
	Observe que também é possível especificar uma Url de página container
	por <a href="${pagePrefix}groups#group_filter"><u>filtro de
	grupo</u></a>.
	<li><b>Descrição</b> Aqui você pode colocar a descrição do grupo.
	O campo serve apenas para informação extra nas configurações do grupo e
	não é usado em nenhum outro lugar do Cyclos.
	<li><b>Ativar grupo</b> Esta opção só será exibida para grupos que
	não possuam uma conta. No caso dos membros deste grupo não serem
	visíveis vovê desve deixar esta opção desmarcada.<br>
	Em alguns casos você pode querer que os membros sejam exibidos para
	outros usuários mesmo que eles não possuam uma conta, e neste caso você
	deve marcar esta opção. Por exemplo corretores que precisem efetuar
	tarefas meramente administrativas (e por isso não podem comercializar
	consigo mesmo) ou usuários demo que apenas acessam para dar uma olhada
	no que o sistema esta oferecendo, e não estarão aptos a transacionar..<br>
	Ao alterar esta configuração ela será aplicada para todos os membros
	existentes e para os novos membros do grupo.

	<hr class="help">
</span>

<span class="admin"> <a name="group_registration_settings"></a>
<h3>Configurações de registro para o grupo</h3>
Estas são configurações definindo o comportamento do grupo conectado ao
registro dos membros. Estas configurações são parte do formulário de
&quot; <a href="#edit_member_group"><u>modificar grupo de membro</u></a>&quot;.
As seguintes estão disponíveis:
<ul>
	<li><b>Grupo inicial</b>: Se você quer que este grupo seja um
	grupo inicial, onde os usuários possam ser colocados após terem se
	registrados pela página de registro público, você deve marcar esta
	opção. Pode existir um ou mais grupos iniciais no Cyclos.<br>
	Você também pode especificar um nome de exibição diferente para o
	grupo. Ao se registrar o usuário terá que escolher o grupo em um caixa
	de seleção contendo os nomes de grupo conforme você definir neste
	campo.<br>
	<br>
	<li><b>Validação de E-mail</b>: Quando esta opção é selecionada, a
	validação de e-mail é solcitada. Após enviar o registro o usuário
	receberá uma mensagem de que ele receberá um e-mail de confirmação que
	precisa ser respondido para que o registro seja processado.<br>
	Até o registro ser confirmado e o período máximo para confirmação não
	ser expirado, os membros estão em uma estado pendente. O período máximo
	de confirmação pode ser definido na seção de &quot;limites&quot; das <a
		href="${pagePrefix}settings#local"><u>configurações locais</u></a>. Os
	membros pendentes podem ser vistos na página de <a
		href="${pagePrefix}user_management#search_pending_member"><u>Membros
	pendentes</u></a>.<br>
	As seguintes opções estão disponíveis:
	<ul>
		<li><b>Não validar:</b> O e-mail não é validado. O membro
		diretamente fará parte do grupo inicial que for definido para o
		cadastro público. (ou no caso de um registro feito pelo administrador
		/ corretor, parte do grupo selecionado por eles).
		<li><b>Validar apenas no cadastro público:</b> O cadastro é
		validado apenas quando o membro se registra na página de cadastro
		pública.
		<li><b>Validar no cadastro público e por corretores:</b> O
		cadastro é validado quando o membro se registra na página de cadastro
		pública, mas também quando o corretor cadastra um membro.
		<li><b>Validar em todos os cadastros (administrador /
		corretor / público):</b> (auto-explicativo)
	</ul>
	<br>
	<li><b>Termo de adesão</b>: Aqui você pode escolher o termo de
	adesão que será exibido na página de registro. Você pode adicionar
	novos termos de adesão na página <a
		href="#list_registration_agreements"><u>Lista de termos de
	adesão</u></a>.<br>
	Se um termo for definido para um grupo, todo os membros daquele grupo
	precisam aceitar o termo de adesão para poderem acessar o Cyclos. Se o
	membro for cadastrado por um administrador ou corretor, o termo de
	adesão será exibido na primeira vez que o membro acessar o sistema.<br>
	Caso membros que já estejam cadastrados no sistema sejam movidos para
	um grupo que possua um termo de adesão definido, o termo será exibido
	ao membro na próxima vez que ele acessar o sistema (e ele terá que
	aceitar o termo para poder acessar).<br>
	Ao alterar para um novo termo de adesão, a opção adicional &quot;Forçar
	a aceitação no próximo login&quot; irá aparecer. Quando esta opção é
	selecionada todos os novos membros, mas também os membros existentes do
	grupo terão o termo exibido. Caos não seja marcada o termo será exibido
	somente para os novos membros. <br>
	<li><b>Enviar senha por e-mail:</b> Quando esta opção é marcada, o
	membro irá receber a senha por email após o cadastro. Se esta opção não
	for marcada, o membro (e corretor /administrador dependendo das
	permissões) pode definir a senha no formulário de registro. <br>
	<br>
	A mensagem pode ser definida nas <a href="${pagePrefix}settings#mail"><u>configurações
	de e-mail</u></a>. Se você usar esta opção tenha certeza de que o campo de
	e-mail seja obrigatório. Este pode ser definido nas <a
		href="${pagePrefix}settings#local"><u>configurações locais</u></a>.<br>
	<br>
	<li><b>Número máximo de imagens de perfil por membro:</b> O número
	máximo de imagens que o membro pode colocar em seu <a
		href="${pagePrefix}profiles"><u>perfile</u></a>.<br>
	<br>
	<li><b>Expirar membros após</b>: cadastros de membros neste grupo
	podem ser automaticamente expirados se você colocar neste campo
	qualquer valor diferente de &quot;0&quot;. Após este tempo ter passado
	desde a entrada do membro neste grupo, o membro será colocado
	automaticamente em outro grupo (veja o próximo item).<br>
	<br>
	<li><b>Grupo após expiração</b>: se você definir uma tempo de
	expiração para um cadastro num grupo, aqui você deve informar o grupo
	para o qual o membro sera movido.
</ul>
<hr class="help">
</span>

<span class="admin"> <a name="list_registration_agreements"></a>
<h3>Termos de adesão</h3>
Esta janela mostra uma lista com os termos de adesão. Mais informações
sobre termos de adesão podem ser encontradas na <a
	href="#group_registration_agreement"><u>página de ajuda</u></a> da
página de modificar termo de adesão . <br>
Você pode inserir um novo ou modificar um termo de adesão existente. <br>
Termos de adesão só podem ser apagados caso não exista nenhum grupo
relacionado com ele, e quando nenhum membro tenha aceitado o termo.<br>
<br>
<hr class="help">
</span>

<span class="admin"> <a name="registration_agreement"></a>
<h3>Novo / modificar termo de adesão</h3>
Um termo de adesão é um texto que pode ser exibido na página de
registro, na qual membros que queiram se registrar DEVEM marcar a caixa
de seleção indicando que eles aceitam os termos, para poder estar apto a
enviar o registro.<br>
Os Termos de adessão podem ser relacionados com um ou mais grupos. Isto
pode ser configurado nas <a href="#group_registration_settings"><u>
Configurações de registro do grupo</u></a>. <br>
Quando você faz alterações em um termo de adesão e necessita que os
usuários existentes (que já aceitaram este termo) aceitem novamente,
você precisa criar um novo termo de adesão e altera-lo nas configurações
do grupo. (você também terá que marcar a opção &quot;Forçar a aceitação
no próximo login&quot;).
<hr class="help">
</span>


<span class="admin"> <a name="group_access_settings"></a>
<h3>Configurações de acesso do grupo</h3>
Estas são configurações que definem o comportamento de acesso do grupo.
Estas configurações são parte do formulário de &quot;<a
	href="#edit_member_group"><u>modificar grupo de membro</u></a>&quot;.
As seguintes estão disponíveis:
<ul>
	<li><b>Acesso aos canais:</b> Canais significam o meio de acesso
	ao Cyclos. Aqui você pode selecionar quais meios de acesso ao cyclos
	podem ser acessados por este grupo. Veja também <a
		href="${pagePrefix}settings#channels"> <u>a seção de canais</u></a>
	desta ajuda. Uma ou mais destas opções podem ser escolhidas.
	<ul>
		<li><b>Acesso principal pela web:</b> Acesso ao cyclos através da
		página web normal em um navegador.
		<li><b>Pagamentos pelo POS web:</b> <b>P</b>oint <b>O</b>f <b>S</b>ale,
		acesso para lojas permitindo pagamentos através do cyclos. Se esta
		opção estiver ativa, o grupo dos pagantes (compradores) necessita
		desta configuração de acesso.
		<li><b>SMS:</b> Este módulo esta em desenvolvimento.
		<li><b>Acesso WAP 1 e WAP2:</b> Acesso ao cyclos através de
		telefones móveis.
		<li><b>Pagamentos para lojas virtuais:</b> Acesso através de um
		botão &quot;pagar com cyclos&quot; em uma loja virtual.
	</ul>
	<br>
	<br>
	<li><b>Acesso padrão dos membros:</b> Cada membro deste grupo pode
	definir suas preferencias pessoais para canais de acesso: todos os
	itens que estiverem selecionados na opção anterior pode ser desativa
	pelo membro através das suas preferencias pessoais. Se você marcar por
	exemplo o acesso &quot;WAP1&quot; e &quot;WAP2&quot; no &quot;acesso
	padrão dos membros&quot;, significa que para cada membro deste grupo os
	canais WAP1 e WAP2 estarão marcados como padrão na página de
	configurações pessoais desses membros. Contudo os membros podem
	desativar estas opções caso queiram.<br>
	<br>
	<li><b>Tamanho da senha externa (PIN):</b> Para alguns canais de
	pagamentos como SMS lojas virtuais o uso de uma senha externa (apenas
	números) pode ser configurada. Nesta opção você pode definir o tamanho
	mínimo e máximo desta senha.<br>
	Observe que para esta opção ser exibida, um canal que utilize a senha
	externa PIN precisa ser selecionado no &quot;acesso aos canais&quot;.
	Quando adicionar o primeiro canal de acesso com PIN, você terá que
	salvar antes as configurações do grupo para que a opção de tamanho da
	senha externa PIN seja exibida.<br>
	<br>
	<li><b>Tamanho da senha:</b> Define o tamanho mínimo e máximo da
	senha que o membro utiliza para acessar o sistema.<br>
	<br>
	<li><b>Política de senhas:</b> Você pode escolher entre 3 opções;
	o significado destas opções é um tanto obvio.<br>
	<br>
	<li><b>Número máximo de tentativas de senha:</b> Quando o usuário
	atinge o valor máximo de tentativas, o usuário não consegue mais ter
	acesso até que o tempo de desativação tenha se passado. (veja a próxima
	opção).<br>
	<br>
	<li><b>Tempo de desativação após número máximo de tentativas
	de senha:</b> Este é o tempo em que o usuário não estará apto a acessar o
	sistema após ter atingido o número máximo de tentativas de senha.<br>
	<br>
	<li><b>Senha de acesso vencerá após:</b> Com essa definição, você
	pode definir o período em que a senha de acesso será válida. Quando o
	período expirar o membro sera forçado a definir uma nova senha. Se você
	colocar &quot;0&quot;, a senha nunca expirará.<br>
	<br>
	<li><b>Senha de transação:</b> Aqui você pode definir o uso de uma
	senha especial para transações. Você tem as seguintes opções:
	<ul>
		<li><b>&quot;Não usada&quot;</b>: A senha de transação não é
		usada, e os membros podem fazer transações (caso tenham permissões é
		claro) sem ter que primeiro informar a senha de transação.
		<li><b>&quot;Automática&quot;</b>: Se esta opção é escolhida, o
		sistema irá gerar a senha de transação na criação da conta do novo
		membro (ou agora, para membros existentes). O membro irá receber a
		senha (apenas uma vez) na sua caixa de mensagens pessoais.
		<li><b>&quot;Manual&quot;</b>: Se escolhida esta opção, a senha
		de transação só sera gerada manualmente através da ação de <a
			href="${pagePrefix}profiles#access_actions"><u>
		&quot;Gerenciar senha de transação&quot;</u></a> na página de perfil do
		membro. Mais informações sobre senha de transação pode ser encontrada
		naquela página.
	</ul>
	<br>
	<br>
	<li><b>Tamanho da senha de transação:</b> Define o tamanho da
	senha de transação. Esta senha sempre terá um tamanho fixo.<br>
	<br>
	<li><b>Número máximo de tentativas de senha de transação:</b> Após
	este número de tentativas, a senha de transação sera bloqueada. Um
	administrador pode reiniciar a senha através da ação de <a
		href="${pagePrefix}profiles#access_actions"><u>&quot;Gerenciar
	senha de transação&quot;</u></a>.
</ul>
<hr class="help">
</span>

<span class="admin"> <a name="group_notification_settings"></a>
<h3>Configurações de notificações</h3>
Estas configurações são sobre as notificações pessoais que os membros de
um grupo pode ativar.
<ul>
	<li><b>Mensagens enviadas por e-mail por padrão:</b> Estas são as
	notificações que irão marcadas por padrão no momento da criação do
	membro (para esse grupo).<br>
	Se você marcar um item nesta caixa de seleção, significa que este item
	esta marcado por padrão na página de preferências pessoais do membro.
	Contudo o membro pode desmarcar essa preferência e sobrescrever esta
	regra.
	<li><b>Mensagens permitidas por SMS:</b> Estas são as mensagens
	que estarão disponíveis para notificação via SMS.<br>
	Note: Esta opção só estará ativa se um canal SMS é ativo (Configurações
	locais - Canais).
	<li><b>Mensagens enviadas por SMS por padrão:</b> Estas são as
	notificações que irão marcadas por padrão no momento da criação do
	membro (para esse grupo).<br>
	Se você marcar um item nesta caixa de seleção, significa que este item
	esta marcado por padrão na página de preferências pessoais do membro.
	Contudo o membro pode desmarcar essa preferência e sobrescrever esta
	regra.
	<li><b>Tipo de transação para cobrança de SMS:</b> Este é o tipo
	de transação que será usado para cobrança de SMS enviados (do sistema
	para o membro ex. notificações).
	<li><b>Valor cobrado por SMS:</b> Este é o valor que sera cobrado
	por cada SMS enviado.
</ul>
Muitos itens desta lista são óbvios e auto explicativos. Esta ajuda
trata apenas de itens que precisem mais esclarecimentos.<br>
A ajuda das <a
	href="${pagePrefix}notifications#notification_preferences"><u>Notificações</u></a>
lhe dará mais informações sobre opções específicas.
<hr class="help">
</span>

<span class="admin"> <a name="group_ad_settings"></a>
<h3>Configurações de anúncios</h3>
Estas são as configurações que definem o comportamento do grupo em
relação aos anúncios. Estas configurações são parte do formulário de
&quot;<a href="#edit_member_group"><u>modificar grupo de membro</u></a>&quot;.
As seguintes estão disponíveis:
<ul>
	<li><b>Número máximo de anúncios por membro:</b> auto explicativo.<br>
	<br>
	<li><b>Permitir anúncios permanentes</b>: Quando marcado, isto
	permite <a href="${pagePrefix}advertisements#ad_modify"><u>anúncios
	permanentes &quot;não expiráveis&quot;</u></a> para este grupo de membros.<br>
	<br>
	<li><b>Tempo de publicação padrão do anúncio:</b> O período de
	publicação padrão para um novo anúncio. O membro pode alterar o período
	de ativação para cada anúncio.<br>
	<br>
	<li><b>Tempo máximo de publicação do anúncio:</b> O período máximo
	de publicação que um membro pode atribuir a um anúncio.<br>
	<br>
	<li><b>Publicação externa de anúncios</b>: Isto permite a
	publicação externa dos anúncios. Significa que os anúncios do grupo
	podem ser publicados automaticamente no website da organização,
	tornando-os visíveis a não-membros. Todas as opções são auto
	explicativas. &quot;Permitir escolha&quot; significa que o membro
	poderá escolher se o anúncio poderá ser publicado externamente.<br>
	<br>
	<li><b>Quantidade. máxima de imagens por anúncio:</b> O número
	máximo de imagens que um membro pode colocar em um anúncio.<br>
	<br>
	<li><b>Tamanho máximo para descrição do anúncio</b>: O tamanho
	máximo que a descrição de um anúncio pode conter em bytes/caracteres.
</ul>
<hr class="help">
</span>

<a name="group_scheduled_payment_settings"></a>
<h3>Configurações de pagamentos agendados</h3>
Estas são as configurações relacionadas aos
<a href="${pagePrefix}payments#scheduled"><u> Pagamentos
agendados</u></a>
. Estas configurações são parte do formulário de &quot;
<a href="#edit_member_group"><u>modificar grupo de membro </u></a>
&quot;.
<br><br>Todas essas configurações e referem aos <a
	href="${pagePrefix}payments#pay_scheduled"><u>Pagamentos
agendados em parcelas</u></a>. As seguintes estão disponíveis:
<ul>
	<li><b>Máximo de parcelas agendadas:</b> O número máximo de
	parcelas em que um pagamento pode ser dividido e agendado. Por exemplo:
	10 parcelas, uma a cada 2 semanas.
	<li><b>Máximo de tempo para agendamento:</b> O tempo total máximo
	para o agendamento de um pagamento ou parcela. O período entre hoje e a
	data do último pagamento.
</ul>
<hr class="help">


<span class="admin"> <a name="group_loans_settings"></a>
<h3>Configurações de empréstimo</h3>
Estas são as configurações relacionadas aos <a href="${pagePrefix}loans"><u>Empréstimos</u></a>.
Estas configurações são parte do formulário de &quot;<a
	href="#edit_member_group"><u>modificar grupo de membro </u></a>&quot;.
As seguintes estão disponíveis:
<ul>
	<li><b>Ver empréstimos cedidos ao grupo de empréstimos</b>: O
	membro pode ver um <a href="${pagePrefix}loans"><u>empréstimo</u></a>
	concedido ao seu <a href="${pagePrefix}loan_groups"><u>grupo de
	empréstimo</u></a>.
	<li><b>Pagamento do empréstimo permitido por qualquer membro
	do grupo:</b> Se esta opção estiver selecionada, qualquer membro do grupo
	do empréstimo pode pagar o empréstimo. Caso contrário apenas o membro
	que estiver selecionado como responsável do grupo pode faze-lo.
</ul>
<hr class="help">
</span>

<span class="admin"> <a name="group_brokering_settings"></a>
<h3>Configurações de corretagem</h3>
Estas são configurações relacionadas à funcionalidade de <a
	href="${pagePrefix}brokering"><u>corretagem</u></a>. Estas
configurações são parte do formulário de &quot;<a
	href="#edit_member_group"><u>modificar grupo de membro </u></a>&quot;.
As seguintes estão disponíveis:
<ul>
	<li><b>Grupos iniciais possíveis:</b> Aqui você pode escolher o
	grupo inicial do membro que é registrado por um corretor. Este pode ser
	diferente de acordo com o uso do corretor. Pode ser um grupo inativo,
	como o grupo de membros pendentes, que necessitam ser ativados por um
	administrador, ou um grupo ativo.<br>
	Se você escolher um ou mais grupos, o corretor poderá escolher em uma
	lista com os grupos onde ele pode registrar membros.<br>
	Observe que o corretor não poderá cadastrar nenhum membro caso você não
	selecione nenhum grupo nesta opção.
</ul>
<hr class="help">
</span>

<span class="admin"> <a name="edit_broker_group"></a>
<h3>Alterar grupo de corretor</h3>
Você pode editar as propriedades de um <a href="#broker_groups"><u>grupo
de corretor</u></a> aqui. Após clicar no botão &quot;alterar&quot;, você pode
alterar o nome, a descrição e um serie de categorias de
&quot;configurações&quot;. <br>
<br><br>Observe que você não pode definir permissões aqui; Propriedades
do grupo e configurações não são o mesmo que permissões. Você pode
alterar as permissões do grupo clicando no <img border="0"
	src="${images}/permissions.gif" width="16" height="16"> ícone de
permissão na visão geral dos <a href="#manage_groups"><u>grupos</u></a>,
mas você também pode usar o botão de atalho abaixo da janela chamado
&quot;Permissões do grupo&quot;.
<br><br>As configurações do grupo de corretor estão ordenadas por
categorias. As seguintes categorias estão disponíveis; Você pode clicar
nos links para obter detalhes sobre os campos destas categorias:
<ul>
	<li><b><a href="#group_details"><u>Detalhes do grupo</u></a></b>
	Da o resumo principal. Aqui você pode alterar o &quot;nome&quot; de
	exibição e a &quot;descrição&quot; do grupo.
	<li><b><a href="#group_registration_settings"><u>Configurações
	de registro</u></a></b> São configurações definindo o comportamento do grupo
	conectado ao registro do membro. Ela também contém algumas
	configurações diversas.
	<li><b><a href="#group_access_settings"><u>Configurações
	de acesso</u></a></b> são configurações definindo o acesso do grupo.
	<li><b><a href="#group_notification_settings"><u>Configurações
	de notificação</u></a></b> Tudo sobre notificação por email para este grupo.
	<li><b><a href="#group_brokering_settings"><u>Configurações
	de corretagem</u></a></b> são configurações relativas a corretagem de outros
	membros.
	<li><b><a href="#group_ad_settings"><u>Configurações
	de anúncios</u></a></b> são configurações definindo o comportamento do grupo em
	relação aos anúncios.
	<li><b><a href="#group_loans_settings"><u>Configurações
	de empréstimo do grupo</u></a></b> são configurações definindo o comportamento do
	grupo conectado a empréstimos.
</ul>
<hr class="help">
</span>

<span class="admin"> <a name="insert_group"></a>
<h3>Inserir novo grupo</h3>
Esta janela permite que você crie um novo grupo. <br>
Você tem as seguintes opções:
<ul>
	<li><b>Tipo:</b> O <a href="#group_categories"><u>tipo</u></a> do
	grupo. Este pode ser &quot;membro&quot;, &quot;corretor&quot; ou &quot;
	administrador&quot;.
	<li><b>Removido:</b> Quando um grupo é marcado como
	&quot;removido&quot; significa que os membros deste grupo
	definitivamente deixaram o sistema. Uma vez no grupo
	&quot;removido&quot; o membro não pode mais ser colocado em outro
	grupo. Os dados continuarão no banco de dados e serão visíveis apenas
	pelos administradores, serve apenas como uma função de backup.
	<li><b>Nome:</b> Nome do grupo, como ele vai ser exibido aos
	usuários.
	<li><b>Descrição:</b> Descrição do grupo.
	<li><b>Copiar dados de:</b> Você pode copiar todos as
	configurações de um grupo para este novo grupo. Configurações e
	permissões serão copiados.
</ul>
Após incluir as informações, você deve clicar no botão
&quot;enviar&quot; para salvar as alterações. <br>
<b>Importante:</b> Após criar o novo membro, você deve definir as
permissões e propriedades através da página de lista de grupos.
<br><br>Note: Após criar um grupo não é possível alterar o tipo e o
estado do grupo.
<hr class="help">
</span>

<span class="admin"> <a name="manage_group_accounts"></a>
<h3>Gerenciar contar do grupo</h3>
<a href="#member_groups"><u>Grupos de membros</u></a> (e <a
	href="#broker_groups"><u>grupos de corretores</u></a>) podem ter várias
<a href="${pagePrefix}account_management#accounts"><u>contas</u></a>
associadas. A lista abaixo mostra os tipos de contas de membro
associadas com este grupo. As contas nesta lista serão exibidas na visão
geral de conta pessoal dos membros deste grupo. Um tipo de conta pode
ser compartilhado com grupos diferentes, significando que estes grupos
diferentes podem ter o mesmo tipo de conta associada. Neste caso você
ainda pode definir diferentes configurações de conta e permissões para
cada grupo.
<br><br>Você pode modificar as configurações de da conta clicando no
ícone <img border="0" src="${images}/edit.gif" width="16" height="16">&nbsp;
de edição.<br>
<b>NOTE</b>: Para estar apto a fazer e receber pagamentos para esta
conta apenas criar ou modificar não é o suficiente. Você também
precisara definir as <a href="#permissions"><u>permissões</u></a> para o
grupo, do contrário a conta não pode ser usada.
<br><br>Você também pode remover um tipo de conta deste grupo clicando no
ícone <img border="0" src="${images}/delete.gif" width="16" height="16">&nbsp;
apagar. Se você remover uma conta de um grupo, isso significa que a
conta ainda sera visível para os membros, e eles poderão ver as
transações da conta, mas a conta estará em um estado
&quot;inativo&quot;. Não poderá mais ser usada para pagamentos.
<br><br>Você pode associar um novo tipo de conta para este grupo,
clicando no botão &quot;Associar nova conta&quot; abaixo desta caixa.
Isto levará você para a janela de <a href="#insert_group_account"><u>&quot;inserir
conta do grupo&quot;</u></a>.
<hr class="help">
</span>

<span class="admin"> <a name="edit_group_account"></a> <a
	name="insert_group_account"></a>
<h3>Modificar configurações de conta / adicionar uma conta ao grupo</h3>
Nesta página você pode definir as configurações da conta, para um tipo
de conta do grupo selecionado. Isto é possível para uma conta associada
já existente, ou para um tipo de conta que você queira associar com este
grupo. <br>
Ao modificar uma conta existente, você deve clicar no botão
&quot;alterar&quot; para poder fazer as modificações. Ao associar um
novo tipo de conta você pode preencher diretamente os campos. Quando
pronto clique em &quot;enviar&quot; para salvar as alterações. <br>
As seguintes configurações podem ser definidas:
<ul>
	<li><b>Conta:</b> O tipo de conta que você quer associar. Isto não
	pode mais ser alterado, assim isto só é disponível para novos tipos de
	conta. Você pode apenas associar um tipo de conta que já tenha sido
	criado; Se você não criou a conta apropriada, você deve fazer isso
	antes de poder associa-la.
	<li><b>É padrão:</b> Como é possível criar mais de uma conta de
	membro, o sistema precisará saber qual das contas sera a padrão. Isto
	tem duas razões. Primeiro o sistema pode ser definido para enviar
	emails informando o membro sobre o balanço de sua conta padrão.
	Segundo, pagamentos efetuados por acesso móvel usarão a conta padrão.
	<li><b>Requer senha de transação:</b> Se esta opção é marcada,
	sera solicitado ao membro a sua senha de transação.<br>
	Note: Esta opção só pode ser ativada se a senha de transação estiver
	ativada para o grupo (nas configurações do grupo). Do contrário ela não
	estará visível.
	<li><b>Ocultar quando não há limite de crédito:</b> Para alguns
	produtos financeiros, você pode querer não mostrar uma conta de membro
	que não possua um um limite de crédito individual definido. Por exemplo
	quando um conta de membro adicional é usada para pagamentos de cartão
	de crédito. Normalmente esta conta tem um limite de crédito negativo
	que o membro pode usar para fazer pagamentos com o cartão.
	Periodicamente o membro é cobrado, para pagar seus débitos. Nem todos
	os membros precisam possuir um limite de crédito negativo definido, e
	você não quer que esta conta de crédito seja exibida para estes
	membros.<br>
	É claro que isto poderia ser solucionado criando um grupo separado para
	&quot;Membros de cartão de crédito&quot; e atribuindo a conta de
	crśdito à este novo grupo, mas isto resultaria em mais
	complexibilidade. Para evitar a criação de diferentes grupos, é
	possível que uma conta seja exibida apenas se o membro possuir um
	limite de crédito inferior definido. É claro que isto de aplica para
	limites de crédito individuais. Se todo o grupo possui um limite de
	crédito inferior, a conta sera exibida para todos, e marcar esta opção
	não será necessário.<br>
	Note: A regra geral no Cyclos é que qualquer conta com transações irá
	sobrescrever esta opção. Significa que caso esta opção seja ativada e o
	membro não possua limite de crédito inferior mas possui transações
	nesta conta, a conta será exibida.
	<li><b>Limite de crédito inferior:</b> O limite de crédito padrão
	para conta. O valor sera tipicamente um zero ou uma valor negativo.
	Alterando esta opção poderá afetar contas existentes, dependendo da
	caixa de &quot;atualizar limite de crédito para os membros
	existentes&quot;, um pouco mais abaixo nesta página. Junto as limite de
	crédito padrão que é valido para o grupo completo, também é possível
	definir um <a href="${pagePrefix}account_management#credit_limit"><u>limite
	de crédito</u></a> individual para um membro. A definição individual irá
	sobrescrever o limite de crédito do grupo.
	<li><b>Limite de crédito superior:</b> Se você definir uma limite
	de crédito superior, significa que a conta não receberá mais pagamentos
	quando atingir este limite. O pagante receberá uma mensagem informando
	que o pagamento falhou.
	<li><b>Atualizar limite de crédito para os membros existentes:</b>
	Se você alterar o limite de crédito de um tipo de conta ja associado,
	você pode definir com esta caixa de seleção se o limite de crédito para
	os membros existente (tanto limite individual quanto do grupo) sera
	alterado. Se você não marcar esta opção o novo limite de crédito só se
	aplicara aos novos membros do grupo. Esta caixa não é visível se você
	estiver associando este tipo de conta pela primeira vez.
	<li><b>Crédito inicial:</b> Esta é a quantia que os novos membros
	irão receber automaticamente. Este pode ser zero ou um valor positivo.
	
	<li><b>Tipo de transação para o crédito inicial:</b> Se um crédito
	inicial diferente de 0 é definido, você deve especificar a natureza do
	<a href="${pagePrefix}account_management#transaction_types"><u>tipo
	de transação</u></a> para este crédito. Como o tipo de transação possui conta
	de origem e destino, é possível especificar de qual conta o crédito
	inicial é tirado. O banco de dados padrão do sistema vem com um tipo de
	transação &quot;crédito inicial&quot; da conta de débito para o conta
	de membro, mas é claro que você pode escolher outro tipo de transação.
	
	<li><b>Alerta de poucas unidades:</b> Se uma conta de membro
	atingir esta quantia, uma mensagem de alerta pessoal sera enviado. Você
	só pode informar um valor positivo. O valor que você informar é
	relativo ao limite de crédito. Isto significa por exemplo que se o
	limite de crédito é -200 e o valor do alerta é 10, o membro ira receber
	o alerta quando o seu saldo chegar a -190 unidades. Se o limite de
	crédito é 0 e o alerta é 10, o alerta sera gerado quando o saldo do
	membro chegar a 10 unidade.
	<li><b>Mensagem de poucas unidades:</b> A mensagem que o membro
	recebe quando o valor do alerta de poucas unidades é atingido.
</ul>
<hr class="help">
</span>

<span class="admin"> <a name="manage_group_customized_files"></a>
<h3>Gerenciar arquivos customizados</h3>
Cyclos é bastante customizavel. Você não pode apenas especificar os seus
estilos e textos para serem exibidos através do <a
	href="${pagePrefix}content_management"><u>&quot;Gerenciamento
de conteúdo&quot;</u></a>, você também pode definir estas customizações a nível
de grupo. Isto significa que cada grupo pode ter os seus estilos e
textos para exibição específicos. Estes são chamados de &quot;Arquivos
customizados&quot;.
<br><br>Esta janela mostra uma visão geral dos arquivos customizados para
este grupo, caso nenhum arquivo esteja customizado ainda, uma mensagem é
mostrada informando que não existem arquivos disponíveis. Você tem as
seguintes opções:
<ul>
	<li><b>Inserir</b> um novo arquivo customizado através do botão
	&quot;customizar novo arquivo&quot;. O arquivo customizado do grupo ira
	sobrescrever o arquivo customizado do sistema (o qual você pode definir
	através do <a href="${pagePrefix}content_management"><u>&quot;Gerenciamento
	de conteúdo&quot;</u></a> ).
	<li><b>Modificar</b> um arquivo customizado existente através do
	ícone de edição<img border="0" src="${images}/edit.gif" width="16"
		height="16">&nbsp;.
	<li><b>Ver</b> o resultado da customização do arquivo através do
	ícone ver <img border="0" src="${images}/view.gif" width="16"
		height="16">&nbsp;.
	<li><b>Apagare</b> uma definição de arquivo customizado através do
	ícone de apagar <img border="0" src="${images}/delete.gif" width="16"
		height="16">&nbsp;. Quando este é apagado o arquivo customizado
	padrão do sistema sera usado. Se disponível.
</ul>
<hr class="help">
</span>

<span class="admin"> <a name="customize_group_file"></a>
<h3>Modificar arquivo customizado para ... / Customizar novo
arquivo para...</h3>
Nesta página você pode customizar um arquivo estático ou um arquivo CSS.
Isto funciona da mesma maneira do <a
	href="${pagePrefix}content_management"><u>Gerenciamento de
conteúdo</u></a>, a única diferença é que este arquivo só se aplica a este
grupo. <br>
Para instruções específicas sobre o que informar ,<a
	href="${pagePrefix}content_management#edit_customized_file"><u>clique
aqui</u></a>.
<br><br>Note: Quando você quiser inserir imagens em arquivos estáticos,
estas imagens precisam ser <a
	href="${pagePrefix}content_management#custom_images"><u>enviadas</u></a>
para o cyclos.
<hr class="help">
</span>

<h2>Permissões</h2>

<br><br>
<hr>

<a name="permissions"></a>
<br><br>No cyclos, as permissões são organizadas
por grupo. Para cada grupo você pode gerenciar as permissões de acesso
às funcionalidades do cyclos. Normalmente, seções do cyclos que o membro
não possui acesso, estarão invisíveis para os membros deste grupo. <br>
<span class="admin"> O sistema é muito flexível. Por exemplo, ele
permite a você criar diferentes grupos de administradores, onde cado
grupo de administrador possui permissões para gerenciar grupos
específicos de membros. Para sistemas complexos é possível criar
administradores extras, para atribuir tarefas específicas, como
administradores de conta, administradores de sistema, administradores de
contabilidade, administradores de vales, etc.. 
Você pode modificar grupos de permissões através do &quot;Menu: Grupos >
Grupos de permissões&quot;, e após clique no ícone de permissões (
<img border="0" src="${images}/permissions.gif" width="16" height="16">
&nbsp;).
</span>
<hr class="help">

<a name="manage_group_permissions_basic"></a>
<h3>Permissões básicas do grupo</h3>
Nesta janela você pode definir as permissões básicas, que são as mesmas
para todos os
<a href="#group_categories"><u>tipos de grupos</u></a>
(membro, administrador, corretor).
<br>
Estas permissões básicas não afetam outras funções, ex. se um membro não
pode acessar o sistema, ele continua podendo receber pagamentos.
<br>
As seguintes permissões podem ser definidas:
<ul>
	<li><b>Acesso</b>: Se esta não for marcada, os membros deste grupo
	não poderão acessar o sistema.
	<li><b>Convidar membro</b>: Se marcada, os membros deste grupo
	irão ver uma janela em sua página principal, com a qual eles poderão
	enviar um convite a um amigo para <a
		href="${pagePrefix}home#home_invite"><u>testar o sistema</u></a>.
	<li><b>Exibir acesso rápido</b>: Esta opção (disponível apenas
	para grupos de membros e corretores) mostrara uma janela, na página
	principal, com ícones para acesso rápido as funções mais usadas
</ul>
<hr class="help">

<span class="admin"> <a
	name="manage_group_permissions_admin_system"></a>
<h3>Permissões de administração do sistema</h3>
Nesta janela você pode definir as permissões das funcionalidades do
sistema para uma grupo de administradores. Assim esta janela de
permissões não possui nenhuma permissão relacionada a grupos de membros.
Para poder fazer alterações você deve ir até o fim da página e clicar no
botão &quot;alterar&quot;. As alterações são gravadas clicando no botão
&quot;enviar&quot;, também no final da página.
<br><br>A estrutura das permissões é bastante simples. A maioria das
funções possui duas permissões, &quot;Ver&quot; e &quot;Gerenciar&quot;.
<br>
Se a permissão ver não for marcada, o item não aparecerá no menu do
grupo de administradores.<br>
A permissão &quot;Gerenciar&quot; concede permissões para criar, editar
e apagar. <br>
<br>
As seguintes permissões estão disponíveis (você pode clicar nos links
para mais informações):
<ul>
	<li><b>Taxas de conta:</b> A permissão ver, permite que o
	administrador acesse a página de <a
		ref="${pagePrefix}account_management#account_fee_overview"><u>
	Taxas de contas</u></a>.<br>
	A permissão &quot;Cobrar&quot; permite cobrar uma taxa de conta
	&quot;manual&quot; ou falhada, na página de <a
		href="${pagePrefix}account_management#account_fee_history"><u>Histórico
	da taxa de conta</u></a>.<br>
	<br>
	<li>A seção <b>Contas:</b> permite a você definir as seguintes
	opções:
	<ul>
		<li><b><a
			href="${pagePrefix}account_management#account_search"><u>Gerenciar
		contas</u></a>:</b>, permite criar e modificar a estrutura de contas.
		<li><b>Ver gerenciamento de contas:</b> O mesmo que a opção
		anterior, porém o administrador apenas pode ver a estrutura de contas,
		não pode fazer nenhuma alteração.
		<li><b>Ver informações de contas de sistema:</b> Aqui você pode
		definir quais sumários de contas de sistema são visíveis ao
		administrador.
		<li><b>Ver <a href="${pagePrefix}payments#authorized"><u>pagamentos
		autorizados</u></a>:</b> Ver o item de menu pagamentos autorizados.
		<li><b>Ver <a href="${pagePrefix}payments#scheduled"><u>pagamentos
		agendados</u></a>:</b> Ver o item de menu pagamentos agendados.
	</ul>
	<br>
	<br>
	<li><b>Categorias de anúncios:</b> Define permissões para <a
		href="${pagePrefix}advertisements#categories"><u>categorias de
	anúncios</u></a>.<br>
	<br>
	<li><b>Grupos de administradores:</b>
	<ul>
		<li>Se <b>ver</b> esta selecionado, o administrador pode ver
		diferentes grupos de administradores na janela <a
			href="#manage_groups"><u>grupos de permissões</u></a>.
		<li><b>Gerenciar arquivos customizados:</b> Permite ao
		administrador gerenciar <a href="#manage_group_customized_files"><u>arquivos
		customizados</u></a> para outros administradores.
	</ul>
	<br>
	<br>
	<li><b><a href="${pagePrefix}alerts_logs#system_alerts"><u>Alertas</u></a>:</b>
	São avisos do sistema em ocasiões especiais.<br>
	<br>
	<li><b><a href="${pagePrefix}settings#channels"><u>Canais</u></a>:</b>
	Define por qual meio o usuário acessa o cyclos (por exemplo: web,
	telefone móvel, etc.).<br>
	<br>
	<li><b><a href="${pagePrefix}account_management#currencies"><u>Moedas</u></a>:</b>
	Permite ao administrador ver / ou gerenciar diferentes moedas no
	cyclos.<br>
	<br>
	<li><b><a href="${pagePrefix}custom_fields"><u>Campos
	customizados</u></a>:</b> Permite ao administrador ver / ou gerenciar campos
	customizados.<br>
	<br>
	<li><b><a href="${pagePrefix}content_management#custom_images"><u>Imagens
	customizadas</u></a></b> Permite ao administrador ver / ou gerenciar imagens
	customizadas. Isto afeta a visibilidade dos seguintes itens de menu
	dentro do menu principal &quot;Gerenciamento de conteúdo&quot;:
	<ul>
		<li>Imagens de sistema
		<li>Imagens customizadas
		<li>Imagens de folha de estilo
	</ul>
	<br>
	<br>
	<li><b><a href="${pagePrefix}content_management"><u>Arquivos
	customizados</u></a> :</b> Permite ao administrador definir arquivos customizados a
	nível de sistema. Isto afeta a visibilidade dos seguintes itens de menu
	dentro do menu principal &quot;Gerenciamento de conteúdo&quot;:
	<ul>
		<li>Arquivos estáticos
		<li>Arquivos de ajuda
		<li>Arquivos CSS
		<li>Páginas da aplicação
	</ul>
	<br>
	<br>
	<li><b><a href="${pagePrefix}alerts_logs#error_log"><u>Registro
	de erros</u></a></b> Ver e gerenciar os registros de erros.<br>
	<br>
	<li><b><a href="${pagePrefix}bookkeeping"><u>Contas
	externas (contabilidade)</u></a>:</b> A permissão de gerenciar, permite que um
	administrador configure o módulo, criando e modificando contas
	externas, e definindo campos e tipos de transações.<br>
	A permissão ver, permite ver as configurações das contas externas.<br>
	A permissão detalhes, permite ver os pagamentos, mas não permite
	efetuar nenhuma ação neles.<br>
	As outras permissões (processar, conferir e gerenciar pagamentos) são
	permissões operacionais para pagamentos externos.<br>
	<br>
	<li><b><a href="#group_filters"><u>Filtros de grupos</u></a></b>
	Permite a você agrupar uma serie de grupos, e atribuir um nome
	específico para esse agrupamento. Desta forma você pode criar uma serie
	de &quot;super grupos&quot; , os quais você pode usar para diversas
	funções no cyclos.<br>
	Você pode escolher entre &quot;gerenciar&quot; e &quot;ver&quot; para
	definir as permissões, mas você também pode marcar &quot;gerenciar
	arquivos customizados&quot;. Isto permitira que um administrador defina
	arquivos customizados em um filtro de grupos, ou seja para uma série de
	grupos de uma só vez.<br>
	<br>
	<li><b><a href="${pagePrefix}groups"><u>Grupos de
	permissões:</u></a></b> Permite <a href="#manage_groups"><u>gerenciar</u></a>
	diferentes <a href="#group_categories"><u>categorias de grupos</u></a>.<br>
	<br>
	<li><b>Tipos de garantias: </b> No sistema de garantias, você pode
	definir diferentes <a href="${pagePrefix}guarantees"><u>tipos
	de garantias</u></a>. <br>
	<br>
	<li><b><a href="${pagePrefix}loan_groups"><u>Grupos de
	empréstimos</u></a>:</b> - Ver e gerenciar grupos de empréstimos. Grupos de
	empréstimos são grupos de membros que pode receber e pagar empréstimos
	na forma de um microcrédito.<br>
	<br>
	<li><b><a href="${pagePrefix}member_records"><u>Tipos
	de registros de membros:</u></a></b> Registros de membros permitem que você defina
	informações que serão reunidas nos membros. Estas permissões permitem
	que o administrador crie/defina tais tipos de registros de membros.<br>
	<br>
	<li><b>Categorias de mensagem:</b> Permite ao administrador ver e
	/ ou gerenciar diferentes categorias de <a href="${pagePrefix}messages"><u>mensagens</u></a>
	internas do sistema.<br>
	<br>
	<li><b>Pagamentos de sistema:</b> Aqui você pode atribuir
	permissões para pagamentos de <a
		href="${pagePrefix}account_management#standard_accounts"><u>contas
	de sistema</u></a>.
	<ul>
		<li><b>Pagamento de sistema:</b> O administrador pode efetuar
		pagamentos de conforme os<a
			href="${pagePrefix}account_management#transaction_types"><u>tipos
		de transações</u></a> selecionados.
		<li><b>Autorizar:</b> Permissões para <a
			href="${pagePrefix}payments#authorized"><u>autorizar</u></a>
		pagamentos de sistema.
		<li><b>Cancelar:</b> Permissões para cancelar um <a
			href="${pagePrefix}payments#scheduled"><u>pagamento agendado</u></a>.




		
		<li><b>Estornar pagamento:</b> Um administrador com esta
		permissão pode &quot;estornar&quot; um pagamento, significa que um
		pagamento oposto será feito com o mesmo valor. No caso do pagamento
		gerar outras transações, todas elas serão estornadas.<br>
		Você pode definir o tempo máximo para uma transação ser estornada nas
		<a href="${pagePrefix}settings#local"><u>Configurações locais</u></a>.




		
	</ul>
	<br>
	<br>
	<li><b>Relatórios:</b> Cada item corresponde a um item de menu
	principal:
	<ul>
		<li><b><a href="${pagePrefix}reports#current_state"><u>Estado
		atual</u></a></b>
		<li><b><a href="${pagePrefix}reports#dRate_config_simulation"><u>
		Índice-D simulação da configuração</u></a></b> permite aos usuários mostrar uma
		simulação da configuração do <a
			href="${pagePrefix}account_management#d-rate"><u>Índice-d</u></a> em
		um gráfico. Advertimos para definir estas permissões para
		administradores se você possuir os índices ativados na moeda.
		<li><b><a href="${pagePrefix}reports#member_lists"><u>Lista
		de membros</u></a></b>
		<li><b>Mensagens SMS enviadas:</b> O sistema pode enviar diversas
		mensagens SMS em várias ocasiões, dependendo da configuração. Esta
		permissão da acesso ao administrador ao relatório de mensagens SMS
		enviadas.
		<li><b><a href="${pagePrefix}statistics"><u>Estatísticas</u></a>:</b>
		Da acesso ao módulo de estatísticas.
		<li><b><a href="${pagePrefix}reports#simulations"><u>Simulações</u></a></b>
		permite aos usuários mostrar simulações das configurações de <a
			href="${pagePrefix}account_management#rates"><u>índices</u></a>.
		Advertimos para definir estas permissões para administradores se você
		possuir os índices ativados na moeda.
	</ul>
	<br>
	<br>
	<li><b><a href="${pagePrefix}settings#web_services_clients"><u>Clientes
	de serviço web</u></a>:</b> Define níveis de acesso para softwares externos
	acessarem os serviços web do cyclos.<br>
	<br>
	<li><b>Configurações:</b> Esta dará acesso ao administrador ao
	menu de configurações.
	<ul>
		<li><b><a href="${pagePrefix}settings#local"><u>Gerenciar
		configurações locais</u></a></b>
		<li><b><a href="${pagePrefix}settings#alerts"><u>Gerenciar
		configurações de alertas</u></a></b>
		<li><b><a href="${pagePrefix}settings#access"><u>Gerenciar
		configurações de acesso</u></a></b>
		<li><b><a href="${pagePrefix}settings#mail"><u>Gerenciar
		configurações de e-mail</u></a></b>
		<li><b><a href="${pagePrefix}settings#log"><u>Gerenciar
		configurações de log</u></a></b>
		<li><b><a href="${pagePrefix}settings#channels"><u>Gerenciar
		canais</u></a></b>
		<li><b><a href="${pagePrefix}settings#web_services_clients"><u>Gerenciar
		clientes de serviço web</u></a></b>
		<li><b><a href="${pagePrefix}settings#import_export"><u>Exportar
		/ importar configurações</u></a></b>
	</ul>
	<br>
	<br>
	<li><b>Estado do sistema:</b>Permite ao administrador ver o estado
	do sistema:
	<ul>
		<li><b>Ver o estado do sistema:</b> Quando selecionado, o
		administrador vera em sua página principal uma janela de <a
			href="${pagePrefix}home#home_status"><u>ver estado do sistema</u></a>.

		
		<li><b>Ver administradores conectados:</b> Selecione aqui os <a
			href="#admin_groups"><u>grupos de administradores</u></a> que o
		administrador pode ver na janela de <a
			href="${pagePrefix}user_management#connected_users"> <u>usuários
		conectados</u></a>.
		<li><b>Ver membros conectados:</b> Funciona da mesma maneira.
		Existe apenas uma caixa de seleção para todos os <a
			href="#member_groups"><u>grupos de membros</u></a>. O administrador
		vera apenas os membros para os quais possua permissão de visualização.

		
		<li><b>Ver corretores conectados:</b> Funciona da mesma maneira.
		O administrador vera apenas os corretores para os quais possua
		permissão de visualização.
		<li><b>Ver operadores conectados:</b> Funciona da mesma maneira.
		Ira mostra o operador e o membro ao qual este operador pertence.
	</ul>
	<br>
	<li><b>Tarefas adminstrativas:</b> permitem ao administrador ver o
	estado do sistema:
	<ul>
		<li><b>Definir disponibilidade do sistema:</b> quando selecionado
		o administrador pode controlar a <a
			href="${pagePrefix}settings#system_availability"><u>Disponibilidade
		do sistema</u></a>
		<li><b>Gerenciar índices:</b> quando selecionado o administrador
		pode <a href="${pagePrefix}settings#search_indexes"><u>Gerenciar
		os índices</u></a>
	</ul>
	<br>
	<li><b>Temas:</b> Para gerenciar <a
		href="${pagePrefix}content_management#themes"><u> temas</u></a>, no
	menu <a href="${pagePrefix}content_management"><u>gerenciamento
	de conteúdo</u></a>.<br>
	<br>
	<li><b>Tradução:</b> Permite o acesso ao menu principal de <a
		href="${pagePrefix}translation"><u> Tradução</u></a>, onde a tradução
	para o seu idioma pode ser visto /gerenciado.
</ul>
<hr class="help">
</span>

<span class="admin"> <a
	name="manage_group_permissions_admin_member"></a>
<h3>Permissões de administração de membros</h3>
Nesta janela você pode definir as permissões para as funções de membro
de um grupo de administrador. As estrutura de permissões é bastante
simples. Muitas funções tem permissões para &quot;Ver&quot; e
&quot;Gerenciar&quot; e algumas vezes permissões extras específicas. <br>
Se &quot;Ver&quot; não for selecionado, a função não sera exibida no
menu ou como um botão de ação no perfil do membro (caso o administrador
digite o endereço da função diretamente na barra de endereços do
navegador, o comportamento da estrutura de permissões é exibir uma
página com a mensagem de &quot;sem permissão&quot;). <br>
A opção de &quot;Gerenciar&quot; da permissões para criar, editar e
apagar.
<br><br>Na primeira caixa de seleção (&quot;Gerenciar grupos:&quot;) você
pode selecionar umou mais grupos de membros. Isto significa que o
administrador pode apenas gerenciar membros destes grupos e ver
informações apenas de membros destes grupos. Qualquer informação
relacionada ao membro, como empréstimos, relatórios, alertas, usuários
conectados, etc, só sera obtida dos grupos selecionados.<br>
Esta opção permite que você defina grupos específicos de membros para
serem administrados por grupos específicos de administradores.
<br><br>As seguintes permissões podem ser definidas - você pode usar os
links para mais informações:
<ul>
	<li><b>Acesso:</b> Permissões para controlar o acesso dos membros
	de um grupo. Este contem os seguintes sub-itens:
	<ul>
		<li><b>Alterar senha de acesso:</b> Permite que o administrador
		altere a senha do membro a partir do perfil deste membro.<br>
		Observe que se esta permissão não for marcada, um adminstrador pode
		ainda definir uma senha de acesso temporária no cadastro do membro (o
		membro será forçado a altera-la no primeiro acesso). Mas isto é apenas
		no caso de a <a href="#edit_member_group"><u>configuração de
		grupo</u></a> &quot;Enviar senha por e-mail&quot; não estar marcada.<br>
		Caso a configuração de grupo &quot;Enviar senha por e-mail&quot;
		esteja marcada, o administrador não pode definir uma senha (o membro
		receberá uma senha temporária por e-mail, que precisará ser alterada
		no primeiro acesso).<br>
		Caso ambas as configurações de grupo &quot;Enviar senha por
		e-mail&quot; e &quot;Alterar senha de acesso&quot; estejam marcadas, o
		administrador pode escolher no momento do registro entre definir uma
		senha definitiva (não temporária) ou forçar que o membro altere a
		senha no primeiro acesso.
		<li><b>Reiniciar senha de acesso:</b> Permite que o administrador
		reinicie a senha do membro, que (dependendo da configuração) sera
		automaticamente gerada novamente e enviada por e-mail.
		<li><b>Gerenciar <a
			href="${pagePrefix}passwords#transaction_password"><u>senha
		de transação</u></a>:</b> Permite o gerenciamento da senha especial que pode ser
		definida para transações.
		<li><b>Desconectar membro conectado:</b> Permite ao administrador
		desconectar imediatamente um membro que esta usando o sistema no
		momento.
		<li><b>Desconectar operador conectado:</b> Permite ao
		administrador desconectar imediatamente um <a
			href="${pagePrefix}operators"><u>operador</u></a> que esta usando o
		sistema no momento.
		<li><b>Permitir o acesso de membros desativados (por
		tentativas de senha):</b> Se um membro esquecer sua senha e tentar acessar
		algumas vezes com uma senha errada, ele sera temporariamente
		desativado. Se esta permissão for definida, uma administrador pode
		permitir que este membro faça o acesso imediatamente. Neste caso o
		botão &quot;permitir acesso do membro agora&quot; aparecerá no perfil
		deste membro.
		<li><b>Alterar pin:</b> Permite ao administrador alterar <a
			href="${pagePrefix}passwords#pin"><u>número pin</u></a>, que é uma
		senha numérica para acesso a certos <a
			href="${pagePrefix}settings#channels"><u>canais</u></a>, como a loja
		virtual.
		<li><b>Alterar canais de acesso:</b> Alterar os métodos de acesso
		a canais, como web, telefone móvel, etc.
		<li><b>Desbloquear senha externa PIN:</b>Desbloquear a senha PIN
		quando o membro exceder o máximo de tentativas.
	</ul>
	<br>
	<br>
	<li><b>Contas:</b> Permissões relativas as permissões para o grupo
	de administrador gerenciar ou ver as contas do membro. Esta contêm os
	seguintes sub-itens:
	<ul>
		<li><b>Ver informações:</b> Permite ao administrador ver as
		informações de conta (balanço, transações, etc).
		<li><b>Ver pagamentos autorizados:</b> Permite ao administrador <a
			href="${pagePrefix}payments#authorized"><u>ver pagamentos
		autorizados</u></a>.
		<li><b>Ver pagamentos agendados:</b> Permite ao administrador <a
			href="${pagePrefix}payments#scheduled"><u>ver pagamentos
		agendados</u></a>.
		<li><b>Definir limite de crédito:</b> Permite ao administrador
		definir um limite de crédito individual; Este irá sobrescrever o
		limite de crédito definido para o grupo.
	</ul>
	<br>
	<br>
	<li><b>Produtos e serviços:</b> Estas permissões permitem ao
	administrador ver e/ou gerenciar os anúncios de um membro.<br>
	Existe também uma permissão para <a
		href="${pagePrefix}advertisements#import_ads"><u>importar
	anúncios</u></a>. <br>
	<br>
	<li><b>Corretagens:</b> Permissões relativas a corretagem dos
	membros pelos <a href="${pagePrefix}brokering"> <u>corretores</u></a>.
	<ul>
		<li><b>Alterar corretor:</b> Permite ao administrador alterar o
		corretor do membro.
		<li><b>Ver lista de membros (como corretor):</b> Permite ao
		administrador ver a lista de membros de um corretor.
		<li><b>Ver dados de empréstimos na impressão da lista de
		membros:</b> Permite ver os empréstimos na lista de membros que é
		gerenciada ou disponibilizada pelo corretor.
		<li><b>Gerenciar </b><a href="${pagePrefix}brokering#commission"><u>comissões</u></a>:
		Gerenciar as comissões que o corretor pode ganhar.
	</ul>
	<br>
	<br>
	<li><b>Ações em massa:</b> Com as <a
		href="${pagePrefix}user_management#bulk_actions"><u>ações em
	massa</u></a> você pode fazer ações específicas em um conjunto de membros.
	<ul>
		<li><b>Alterar grupo:</b> Alterar o grupo de um conjunto de
		membros.
		<li><b>Alterar corretor:</b> Alterar o corretor de um conjunto de
		membros. Você pode usar isso quando uma corretor deixa seu trabalho, e
		seus membros necessitam outro corretor.
	</ul>
	<br>
	<br>
	<li><b>Documentos:</b> Permite ao administrador gerenciar e ver os
	<a href="${pagePrefix}documents"> <u>documentos</u></a> do membro. Os
	seguintes sub-itens se aplicam:
	<ul>
		<li><b>Ver documentos:</b> Aqui você pode selecionar os
		documentos que o administrador pode ver. Caso nenhum documento esteja
		disponível a caixa de seleção estará vazia.
		<li><b>Ver <a href="${pagePrefix}documents"><u>documentos
		dinâmicos</u></a></b>
		<li><b>Ver <a href="${pagePrefix}documents"><u>documentos
		estáticos</u></a></b>
		<li><b>Ver <a href="${pagePrefix}documents#member_document"><u>documentos
		do membro</u></a></b>
	</ul>
	<br>
	<br>
	<li><b>Grupos de membros:</b>
	<ul>
		<li><b>Ver:</b>Se esta for definida, a <a href="#manage_groups"><u>visão
		geral</u></a> dos grupos de membros pode ser visível pelo administrador.
		<li><b>Gerenciar configurações de contas:</b> Permite ao
		administrador <a href="#manage_group_accounts"><u>gerenciar as
		configurações de contas dos grupos</u></a>. Se esta não for definida, o
		administrador poderá ver as configurações (dependendo do item
		anterior), mas não poderá altera-las.
		<li><b>Gerenciar arquivos customizados:</b> Permite ao
		administrador gerenciar os <a
			href="${pagePrefix}content_management#customized_files"><u>
		arquivos customizados</u></a> para os grupos de membros.
	</ul>
	<br>
	<br>
	<li><b>Garantias:</b> Varias permissões relativas ao sistema de <a
		href="${pagePrefix}guarantees"><u>garantias</u></a>. Contem os
	seguintes sub-itens:
	<ul>
		<%-- TO DO --%>
		<li><b>Ver obrigações de pagamento:</b>
		<li><b>Ver certificações:</b>
		<li><b>Ver garantias:</b>
		<li><b>Processar o empréstimo de garantias:</b>
		<li><b>Registrar garantias:</b>
		<li><b>Cancelar obrigações de pagamento como membro:</b>
		<li><b>Cancelar certificações como membro:</b>
		<li><b>Cancelar garantias como membro:</b>
		<li><b>Aceitar garantias:</b>
	</ul>
	<br>
	<br>
	<li><b>Faturas eletrônicas de membros:</b> Várias configurações
	para permitir ao administrador acessar as <a
		href="${pagePrefix}invoices"><u> faturas eletrônicas</u></a> de um
	membro. Todas as opções são auto-explicativas o suficiente, assim nós
	não proveremos sub-itens aqui.<br>
	<br>
	<li><b><a href="${pagePrefix}loan_groups"><u>Associação
	a grupo de empréstimos:</u></a></b> Aqui você pode definir se um administrador pode
	adicionar e remover membros de um grupo de empréstimos, ou apenas ver
	os grupos de empréstimos. <br>
	<br>
	<li><b><a href="${pagePrefix}loans"><u>Empréstimos</u></a>:</b>
	Várias configurações para permitir acesso aos empréstimos dos membros.
	Os seguintes sub-itens existem:
	<ul>
		<li><b>Ver empréstimos do membro:</b> Ver empréstimos normais dos
		membros.
		<li><b>Ver empréstimos autorizados:</b> Para alguns empréstimos
		autorização especial é necessária.
		<li><b>Conceder empréstimo:</b> Com a caixa de seleção, você pode
		especificar diferentes tipos de empréstimos, os quais é permitido ao
		administrador conceder aos membros.
		<li><b>Conceder empréstimo em data passada:</b> Para ocasiões
		<li><b><a href="${pagePrefix}loans#discard"><u>Descartar
		empréstimo</u></a>: </b> Permite &quot;cancelar&quot; um empréstimo colocando sua
		quantia restante em zero.
		<li><b>Pagar empréstimo:</b> Permite ao administrador pagar um
		empréstimo para o membro.
		<li><b>pagar empréstimo em data passada:</b> O mesmo que o item
		anterior, mas agora ao administrador é permitido definir a data de
		<li><b>Gerenciar o estado de empréstimos vencidos:</b> Permite a
		você dar a um empréstimo vencido um <a
			href="${pagePrefix}loans#status"><u> estado extra</u></a>.
	</ul>
	<br>
	<br>
	<li><b>Mensagens:</b> Permite o acesso do administrador as <a
		href="${pagePrefix}messages"> <u>mensagens</u></a> de sistema do
	Cyclos.
	<ul>
		<li><b>Ver:</b> possui uma caixa de seleção onde você pode
		escolher quais tipos de categorias de mensagens o administrador pode
		ver. Você pode criar novos tipos de mensagens através das <a
			href="${pagePrefix}messages#categories"><u>categorias de
		mensagens</u></a>.
		<li><b>Enviar para o membro:</b> Permite ao administrador enviar
		uma mensagem para um membro individual.
		<li><b>Enviar para o grupo:</b> Permite ao administrador enviar
		uma mensagem para todos os membros de um grupo.
		<li><b>Gerenciar:</b> Permite o gerenciamento das mensagens.
		Permite por exemplo, procurar por mensagens antigas, e criar
		categorias de mensagens.
	</ul>
	<br>
	<br>
	<li><b>Pagamentos de membros:</b> Este é o conjunto de permissões
	relativo a <a href="${pagePrefix}payments"><u>pagamentos</u></a>. Os
	sub-itens são:
	<ul>
		<li><b>Pagamento de sistema para membros:</b> Selecione aqui os
		pagamentos de sistema que o administrador pode usar para pagar o
		membro a partir de uma conta de sistema.
		<li><b>Mostrar pagamentos de membros no menu:</b> Permite ao
		administrador efetuar um pagamento de sistema para membro a partir do
		menu principal
		<li><b>Pagamentos de membros em data no passado:</b> Permite ao
		administrador efetuar um pagamento de sistema para membro, porém
		definindo a data do pagamento como uma data no passado.
		<li><b>Pagamento de membro para membro:</b> Selecione aqui os
		tipos de pagamentos que o administrador pode efetuar como se fosse o
		membro pagando outro membro.
		<li><b>Auto-pagamento de membro:</b> Estes são pagamentos de uma
		conta do membro para outra conta do mesmo membro. O administrador pode
		fazer este pagamento como se fosse o próprio membro.
		<li><b>Pagamento de membro para sistema:</b> O administrador pode
		fazer um pagamento para uma conta de sistema, a partir da conta de
		membro, como se fosse o membro. Selecione aqui os tipos de pagamentos
		para os quais você quer permitir isto.
		<li><b><a href="${pagePrefix}payments#authorized"><u>Autorizar
		pagamentos</u></a></b> São pagamentos para os quais é necessário uma autorização
		especial; Aqui você define que o administrador pode autorizar o
		pagamento como se fosse o membro.
		<li><b>Cancelar pagamento autorizado como membro</b>
		<li><b>Cancelar <a href="${pagePrefix}payments#scheduled"><u>pagamento
		agendado</u></a> como membro</b><br>
		Para que esta configuração funcione, o tipo de transação deve permitir
		o cancelamento de pagamentos agendados.
		<li><b>Bloquear pagamento agendado como membro:</b> Permite a
		você bloquear um pagamento agendado. A diferença entre bloquear e
		cancelar é que o pagamento agendado bloqueado pode ser desbloqueado.
		Cancelamento é definitivo.
		<li><b><a href="${pagePrefix}payments#charge_back"><u>Estornar
		pagamento</u></a>:</b> Permite ao administrador estornar um pagamento para o
		membro.
	</ul>
	<br>
	<br>
	<li><b><a href="${pagePrefix}member_records"><u>Registros
	de membros</u></a>:</b> Permite que o administrador gerencie os registros de
	membros - estes permitem que você defina informações que serão reunidas
	nos membros. Sub-itens são auto-explicativos.<br>
	<br>
	<li><b><a href="${pagePrefix}references"><u>Referências</u></a>:</b>
	Permite ao administrador gerenciar ou ver referências - o sistema no
	qual o membro pode atribuir qualificações boas ou ruins para outros
	membros.<br>
	<br>
	<li><b><a href="${pagePrefix}reports#member_activities"><u>Relatório
	de atividades</u></a>:</b> São relatórios sobre a atividade de uma membro
	específico, que são acessíveis dos botões de ações abaixo do perfil do
	membro. Sub-itens são suficientemente auto-explicativos.<br>
	<br>
	<li><b><a href="${pagePrefix}reports#sms_log"><u>Registros
	de SMS</u></a>:</b> Da acesso aos registros de mensagens SMS enviadas para esse
	membro. O sistema pode ser configurado para enviar mensagens SMS em
	diversas ocasiões.<br>
	<br>
	<li><b><a href="${pagePrefix}transaction_feedback"><u>Qualificação
	de transações</u></a></b>: Estas são qualificações de outros membros em transações
	específicas. Estas permissões permitem que o administrador veja ou veja
	e gerencie as qualificações de transações.<br>
	Qualificação de transações pode ser ativado na configuração do <a
		href="${pagePrefix}account_management#transaction_type_details"><u>tipo
	de transação</u></a>. <br>
	<br>
	<li><b>Membros:</b> Algumas permissões diversas nas quais o
	administrador pode usar com membros. Sub-itens:
	<ul>
		<li><b>Ver:</b> Se esta não estiver marcada, o administrador não
		poderá ver os membros.
		<li><b>Registrar:</b> Se esta for marcada, o administrador pode
		registrar um novo membro, a partir da página de busca de membros.
		<li><b>Alterar perfil:</b> Se esta estiver marcada, o
		administrador pode fazer alterações nos campos do <a
			href="${pagePrefix}profiles"><u> perfil</u></a> de um membro.
		<li><b>Remover permanentemente:</b> O administrador pode remover
		membros permanentemente do banco de dados. Isto pode ser feito apenas
		se o membro nunca pertenceu a um grupo que possuía contas (Após ser
		ativado, um membro sempre pode ser colocado em um grupo de <a
			href="#removed_members"><u>membros removidos</u></a>.
		<li><b>Alterar grupo:</b> Permite ao administrador alterar o
		grupo de um membro.
		<li><b>Importar:</b> Permite ao administrador importar listas de
		membros no Cyclos (normalmente migração de outros sistemas). <br>
		Mais informações estão disponíveis no <a
			href="${pagePrefix}user_management#import_members"><u>Arquivo
		de ajuda</u></a>.
		<li><b>Gerenciar membros pendentes:</b> O administrador pode ver
		membros que se regsitraram mas ainda não validaram (por e-mail) seus
		cadastros. <br>
		Mais informações estão disponíveis no <a
			href="${pagePrefix}user_management#search_pending_member"><u>Arquivo
		de ajuda</u></a>.
	</ul>
</ul>
<hr class="help">
</span>

<span class="admin"> <a
	name="manage_group_permissions_admin_admin"></a>
<h3>Permissões de administração de administradores</h3>
Nesta janela você define as permissões das funções administrativas de um
grupo de administradores. Significa que você pode definir o nível de
acesso que este grupo de administradores terá no sistema. É uma prática
comum ter um número limitado de administradores com este tipo de
permissões, pois estas são permissões de alto nível. <br>
A estrutura de permissões é bastante simples. Muitas funções tem
permissões para &quot;Ver&quot; e &quot;Gerenciar&quot; e algumas vezes
permissões extras específicas. <br>
Se &quot;Ver&quot; não for selecionado, a função não sera exibida no
menu ou como um botão de ação no perfil do membro (caso o administrador
digite o endereço da função diretamente na barra de endereços do
navegador, o comportamento da estrutura de permissões é exibir uma
página com a mensagem de &quot;sem permissão&quot;). <br>
A opção &quot;gerenciar&quot; da permissões para criar, editar e apagar.
<br><br>Se a função possuir permissões específicas (além das de ver e
gerenciar) a permissão terá um nome, que indica o tipo de permissão (ex:
desconectar usuário conectados).<br>
<br>
As seguintes estão disponíveis; Nós apenas mencionamos itens que
precisem explicação:
<ul>
	<li><b>Acesso:</b>
	<ul>
		<li><b>Desconectar:</b> Permite desconectar imediatamente do
		sistema outro administrador.
		<li><b>Permitir o acesso de administradores desativados (por
		tentativas de senha):</b> Se um administrador for desativado por não se
		lembrar da sua senha, você pode liberar o acesso dele novamente.
	</ul>
	<br>
	<br>
	<li><b>Registros de administradores:</b> São como os <a
		href="${pagePrefix}member_records"><u>registros de membros</u></a>,
	mas permitem guardar informações para administradores. <br>
	<br>
	<li><b>Administradores:</b> Estas são permissões relativas a
	outros administradores, como registra-los e coloca-los em outro grupo.
	Existem menas funções para administrador do que para membro.
	Administradores não possuem contas mas podem ter um certo nível de
	acesso a contas de sistema.<br>
	Os itens são suficientemente auto-explicativos.
</ul>
<hr class="help">
</span>

<span class="admin"> <a name="manage_group_permissions_member"></a>
<h3>Permissões de membro de grupo membro</h3>
Os membros que pertencem a este grupo receberão estas permissões.
<ul>
	<li><b>Acesso:</b>Desbloquear a senha externa PIN quando o membro
	exceder o máximo de tentativas.<br>
	<br>
	<li><b>Conta:</b> Um membro sempre possui acesso para sua conta,
	assim não existe permissão para ver ou gerenciar a conta. Nesta seção
	você pode definir apenas se é possível que eles vejam :
	<ul>
		<li><b><a href="${pagePrefix}payments#authorized"><u>Pagamentos
		autorizados</u></a></b>
		<li><b><a href="${pagePrefix}payments#scheduled"><u>Pagamentos
		agendados</u></a></b>
	</ul>
	<br>
	<br>
	<li><b>Anúncios:</b><br>
	<ul>
		<li><b>Publicar:</b> Se &quot;Publicar&quot; é selecionado, o
		membro pode publicar anúncios e o item de menu &quot;Pessoal -
		Anúncios&quot; sera exibido no menu do membro.<br>
		<br>
		<li><b>Ver:</b> Se nenhum grupo for selecionado, a função de
		anúncio não estará disponível para este grupo de membros. Tampouco
		aparecera no menu de busca ou na página de <a
			href="${pagePrefix}profiles#actions_for_member"><u>
		&quot;Ações do membro&quot;</u></a> abaixo do perfil.<br>
		Se um ou mais grupos forem selecionados a função de <a
			href="${pagePrefix}advertisements#advertisement_search"><u>
		&quot;Procurar Produtos & serviços&quot;</u></a> mostrara apenas resultados
		dos membros dos grupos selecionados.<br>
		Normalmente você deve selecionar todos os grupos. Se você possuir
		grupos que precisem operar totalmente em separado mas no mesmo
		sistema, então você deve limitar a visibilidade e selecionar apenas os
		grupos específicos. Um exemplo pode ser que exista um grupo de
		empresas e você não quer mostrar anúncios de consumidores para esse
		grupo.
	</ul>
	<br>
	<br>
	<li><b>Comissões</b>: Se &quot;ver&quot; é marcado, permite aos
	membros ver as <a href="${pagePrefix}brokering#commission"><u>comissões</u></a>
	para o seu corretor. <br>
	<br>
	<li><b>Documentos:</b> Com esta opção você pode determinar os <a
		href="${pagePrefix}documents"><u>documentos</u></a> que serão exibidos
	no menu &quot;Pessoal - documentos&quot; do membro. Se nenhum documento
	for selecionado o item de menu não sera exibido (para esse grupo de
	membro).<br>
	<br>
	<li><b>Garantias:</b> Estas são parte do sistema de <a
		href="${pagePrefix}guarantees"><u>garantias </u></a>do cyclos, onde
	cada balanço de conta no cyclos é garantido por uma quantia de
	dinheiro. Você pode escolher as seguintes permissões:
	<ul>
		<li><b>Comprar com obrigações de pagamento:</b> Aqui você pode
		selecionar de quais grupos o membro pode comprar com <a
			href="${pagePrefix}guarantees"><u>obrigações de pagamentos</u></a>.
		<li><b>Emitir certificações:</b> <%-- ?? LATER DOOR HUGO ?? --%>
		<li><b>Emitir garantias:</b> <%-- ?? LATER DOOR HUGO ?? --%>
		<li><b>Vender com obrigações de pagamento:</b> <%-- ?? LATER DOOR HUGO ?? --%>
	</ul>
	<br>
	<br>
	<li><b>Faturas eletrônicas:</b> Nesta seção você pode definir se o
	membro pode enviar <a href="${pagePrefix}invoices"><u>faturas
	eletrônicas</u></a> para outros membros, tanto a partir do perfil do membro
	quanto do menu &quot;Conta&quot;. Quando &quot;Enviar para
	sistema&quot; é selecionado o membro pode enviar faturas eletrônicas
	para contas de sistema a partir do &quot;menu Conta&quot;.<br>
	<br>
	<li><b>Empréstimos:</b> Nesta seção você pode definir as
	permissões dos membros para <a href="${pagePrefix}loans"><u>empréstimos</u></a>.
	<ul>
		<li><b>Ver:</b> Se a opção &quot;Ver&quot; estiver selecionada,
		os membros do grupo poderão ver seus empréstimos. Caso contrário o
		item de menu não sera exibido.
		<li><b>Pagar:</b> Selecione esta para permitir que o membro possa
		pagar os empréstimos.
	</ul>
	<br>
	<br>
	<li><b>Mensagens:</b> Nesta seção você pode definir até onde se
	estende o uso do sistema de <a href="${pagePrefix}messages"><u>mensagens</u></a>
	do cyclos pelo membro. Cada categoria exibida na opção &quot;Enviar
	para administração&quot; primeiro precisa ser criada nas <a
		href="${pagePrefix}messages#categories"><u>categorias de
	mensagens</u></a>.<br>
	Note: Para a mensagem ser exibida ao administrador, o grupo deste
	administrador precisa ter as permissões para ver a categoria desta
	mensagem. <br>
	<br>
	<li><b>Operadores:</b> Aqui você define se os membros pode fazer
	uso do sistema de <a href="${pagePrefix}operators"><u>operadores</u></a>
	do cyclos, o qual permite que o membro defina uma série de sub-membros
	para a sua conta. Existe apenas uma opção, ativado ou desativado.<br>
	<br>
	<li><b>Pagamentos:</b> Aqui você define quais <a
		href="${pagePrefix}account_management#transaction_type_details"><u>
	tipos de pagamentos</u></a> é permitido para este grupos de membros. As opções
	dependem de quantos tipos de pagamentos você definiu.
	<ul>
		<li><b>Auto-pagamento:</b> Se esta for selecionada, o membro
		poderá fazer pagamento entre suas contas. Na caixa de seleção você
		pode especificar os possíveis tipos de transações. Esta opção apenas
		terá sentido se este grupo do membro possuir mais de uma conta.
		<li><b>Pagamento para membro:</b> Aqui você pode selecionar quais
		tipos de pagamentos o membro poderá usar ao fazer um pagamento para
		outro membro.
		<li><b>Opção de pagamento de membro a partir do menu:</b> Se esta
		opção for marcada, os membros poderão efetuar pagamentos para outros
		membros a partir do menu &quot;Conta&quot;.
		<li><b>Pagamentos de sistema:</b> Aqui você pode especificar
		quais tipos de pagamentos o membro pode escolher para fazer um
		pagamento para uma conta de sistema. Se nenhum for selecionado, o item
		de menu &quot;Pagamentos de sistema&quot; não sera exibido.
		<li><b>Gerar tickets de pagamento externo:</b><br>
		Esta é sobre o sistema de tickets (bilhetagem) usado por lojas de
		membros. É mais usado por lojas virtuais que precisem usar o cyclos
		como um sistema de pagamento externo. Se esta opção é selecionada, o
		membro (loja) pode gerar tickets. O sistema de tickets é transparente
		para os usuários. Ele possua uma estrutura onde lojas virtuais podem
		autenticar e validar consumidores e dados de pagamentos sem ter acesso
		aos dados do consumidor. Os detalhes técnicos podem ser encontrados na
		página wiki do Cyclos (webservices - tickets).
		<li><b>Autorizar quando receber pagamentos:</b> Permite ao membro
		<a href="${pagePrefix}payments#authorized"><u>Autorizar</u></a> um
		pagamento quando ele é o recebedor (vendedor). <br>
		Note: Esta opção permite uma confirmação extra pelo recebedor.
		Funciona similar as faturas eletrônicas, e portanto não podem ser
		combinadas.
		<li><b>Cancelar pagamento pendente de autorização:</b> Quando <a
			href="${pagePrefix}payments#authorized"><u>pagamentos
		autorizados</u></a> estão em uso, esta permite as membros cancelar seus
		pagamentos autorizados uma vez que tenham sido criados, mas ainda não
		tenham sido autorizados.
		<li><b>Cancelar pagamento agendado:</b> Quando <a
			href="${pagePrefix}payments#scheduled"><u>pagamentos
		agendados</u></a> estão em uso, esta permite aos membros cancelar seus
		pagamentos agendados antes que a data planejada tenha chegado.
		<li><b>Bloquear pagamento agendado:</b> Permite aos membros
		bloquear temporariamente seus pagamentos agendados.
		<li><b>Solicitar pagamentos de outros canais:</b> Quando esta é
		marcada, o membro pode enviar uma &quot;<a
			href="${pagePrefix}payments#request"><u>solicitação de
		pagamento</u></a>&quot; (faturas) através de outros <a
			href="${pagePrefix}settings#channels"><u>canais</u></a>; Você pode
		escolher estes canais na caixa de seleção. Neste momento apenas SMS
		esta disponível, mas futuramente outros canais podem ser adicionados.


		
	</ul>
	<br>
	<li><b>Preferencias:</b> Isto permite ao membro do grupo acesso ao
	item de menu principal preferências. Existe apenas dois itens neste
	menu, portando apenas estas duas permissões:
	<ul>
		<li><b>Notificações:</b> permite ao membro gerenciar suas <a
			href="${pagePrefix}notifications"><u>notificações</u></a> por e-mail.


		
		<li><b>Interesses em anúncios:</b> permite ao membro gerenciar
		seus <a href="${pagePrefix}ads_interest"><u>interesses em
		anúncios</u></a>.
	</ul>
	<br>
	<li><b>Perfil de membro:</b> Aqui você pode especificar os membros
	que serão exibidos no resultado da <a
		href="${pagePrefix}user_management#search_member_by_member"><u>&quot;Procura
	de membros;</u></a> para este grupo de membros. Normalmente todos os grupos são
	selecionados (com exceção dos de removidos e desabilitados). Você só
	deve querer permitir que grupos específicos sejam vistos no caso de ter
	grupos trabalhando independentemente no sistema. Por exemplo grupos de
	consumidores e de empresas que não devem ver um ao outro. Se você usar
	grupos específicos, você precisara definir as mesmas permissões para a
	visualização de anúncios.<br>
	<br>
	<li><b>Referências:</b> Permite ao membro ver as <a
		href="${pagePrefix}references"><u> referências</u></a>, ou dar
	referências a outros membros. Se você não quiser usar a função de
	referências (para este ou outros grupos) você deve deixar a opção
	&quot;ver&quot; desmarcada. Neste caso o menu referências e outros
	botões de referências não serão exibidos.<br>
	<br>
	<li><b>Relatórios de membro:</b> Se &quot;Ver&quot; é selecionado,
	o grupo de membro pode ver as <a
		href="${pagePrefix}reports#member_reports"><u>páginas de
	relatórios</u></a> de outros membro. Se você selecionar um tipo de conta na
	opção &quot;Exibir informações de conta&quot; o membro também poderá
	ver informações de contas (balanço) de outros membros nestes
	relatórios.<br>
	<br>
	<li><b><a href="${pagePrefix}reports#sms_log"><u>Registros
	de SMS</u></a>:</b> Da acesso ao membro ao registro de mensagens SMS enviadas por
	ele. O sistema pode ser configurado para enviar mensagens SMS em várias
	ocasiões.<br>
	<br>
</ul>
<hr class="help">
</span>

<span class="admin"> <a name="manage_group_permissions_broker"></a>
<h3>Permissões de corretor</h3>
Estas são permissões típicas sobre a função de <a
	href="${pagePrefix}brokering"> <u>corretagem</u></a> que um corretor
pode fazer. Significa que você pode definir os tipos de funções que o
corretores deste grupo podem exercer em relação aos seus membros. A
estrutura das permissões é bastante simples. Muitas funções possuem
permissões de &quot;Ver&quot; e &quot;Gerenciar&quot; e algumas vezes
permissões extras específicas. <br>
Se ver não for selecionado, a função não sera exibida no menu do
corretor, tampouco os botões de ações no perfil do membro. <br>
A opção de &quot;Gerenciar&quot; da permissões para criar, editar e
apagar. <br>
<br>
Você pode permitir que o corretor tenha acesso as seguintes funções para
seus membros corretados:
<ul>
	<li><b>Conta:</b>
	<ul>
		<li><b>Ver informações de conta:</b> O corretor pode ver as
		informações da conta de seus membros corretados.
		<li><b>Ver <a href="${pagePrefix}payments#authorized"><u>pagamentos
		autorizados</u></a></b> dos seus membros corretados é permitido se esta for
		marcada.
		<li><b>Ver <a href="${pagePrefix}payments#scheduled"><u>pagamentos
		agendados</u></a></b>.
	</ul>
	<br>
	<br>
	<li><b>Produtos e Serviços:</b> Permite ao corretor ver ou
	gerenciar os anúncios dos seus membros corretados.<br>
	<br>
	<li><b><a href="${pagePrefix}documents">Documentos:</a></b>
	<ul>
		<li><b>Ver:</b> Aqui você pode escolher quais documentos do
		sistema o corretor pode ver.
		<li><b>Ver documentos individuais de membro:</b> Permite ao
		corretor ver os <u><a
			href="${pagePrefix}documents#member_document">documentos
		individuais</a></u> do membro.
		<li><b>Gerenciar documentos individuais de membro:</b> O mesmo
		que a anterior, mas permissões de gerenciamento.
	</ul>

	<br>
	<li><b>Faturas eletrônicas:</b> O significado destes itens são
	óbvios e auto-explicativos.<br>
	<br>
	<li><b><a href="${pagePrefix}loan_groups"><u>Grupos de
	empréstimos</u></a></b> <br>
	Ver o item de menu grupos de empréstimos. <br>
	<br>
	<li><b><a href="${pagePrefix}loans"><u>Empréstimos</u></a></b><br>
	Ver o item de menu empréstimos. <br>
	<br>
	<li><b>Acesso:</b> Permissões para que o corretor controle o
	acesso de seus membros. Estas contem os seguintes sub-itens:
	<ul>
		<li><b>Alterar senha:</b> Permite que o corretor altere a senha
		de acesso do membro, através do perfil do membro.<br>
		Observe que se esta permissão não for marcada, o corretor ainda
		consegue definir uma senha temporária no cadastramento do membro (o
		membro será forçado a alterar a senha no primeiro acesso). Mas isto é
		apenas no caso de a <a href="#edit_member_group"><u>configuração
		de grupo</u></a> &quot;Enviar senha por e-mail&quot; não estar marcada.<br>
		Caso a configuração de grupo &quot;Enviar senha por e-mail&quot;
		esteja marcada, o corretor não pode definir uma senha (o membro
		receberá uma senha temporária por e-mail, que precisará ser alterada
		no primeiro acesso).<br>
		Caso ambas as configurações de grupo &quot;Enviar senha por
		e-mail&quot; e &quot;Alterar senha de acesso&quot; estejam marcadas, o
		corretor pode escolher no momento do registro entre definir uma senha
		definitiva (não temporária) ou forçar que o membro altere a senha no
		primeiro acesso.
		<li><b>Reiniciar senha:</b> Permite que o corretor reinicie a
		senha de acesso do membro, oque (dependendo da configuração do
		sistema) geralmente significa que ela sera automaticamente gerada e
		enviada por e-mail para o membro.
		<li><b>Gerenciar <a
			href="${pagePrefix}passwords#transaction_password"><u>senha
		de transação</u></a>:</b> Permite gerenciar a senha especial que pode de
		configurada para transações.
		<li><b>Alterar senha externa:</b> Permite que o corretor altere o
		<a href="${pagePrefix}passwords#pin"><u>número pin</u></a>, que é uma
		senha numérica para acesso a certos <a
			href="${pagePrefix}settings#channels"> <u>canais</u></a>, como lojas
		virtuais.
		<li><b>Alterar acesso a canais:</b> Alterar os métodos de acesso
		a <a href="${pagePrefix}settings#channels"><u>canais</u></a>, como
		web, telefone móvel, etc.
		<li><b>Desbloquear senha externa PIN:</b> Desbloquear a senha
		externa PIN quando o membro exceder o máximo de tentativas.<br>
		<br>
	</ul>
	<br>
	<br>
	<li><b>Pagamentos de membros:</b> Controla quais pagamentos do
	membro o corretor tem acesso.<br>
	Sub-itens são:
	<ul>
		<li><b>Pagamento como membro para membro:</b> Selecione aqui os
		tipos de pagamentos que o corretor pode efetuar como se fosse o
		próprio membro, pagando outro membro.
		<li><b>Pagamento como membro para sistema:</b> O corretor pode
		fazer pagamentos para contas de sistema, desde a conta do membro, como
		se fosse o membro. Selecione aqui os tipos de pagamentos para os quais
		você quer permitir isso.
		<li><b>Autorizar:</b> O corretor pode autorizar pagamentos do
		membro, para o sistema de <a href="${pagePrefix}payments#authorized"><u>pagamentos
		autorizados</u></a>.
		<li><b>Cancelar pagamento autorizado como membro</b>
		<li><b>Cancelar <a href="${pagePrefix}payments#scheduled"><u>pagamento
		agendado</u></a> como membro</b>.
		<li><b>Bloquear pagamento agendado como membro:</b> Bloquear
		temporariamente um pagamento agendado. A diferença entre bloquear e
		cancelar é que o pagamento bloqueado pode ser desbloqueado.
		cancelamento é definitivo.
		<li><b>Auto-pagamento como membro:</b> Estes são pagamentos de
		uma conta do membro para uma outra conta do mesmo membro. O corretor
		pode efetuar estes pagamentos como de fosse o próprio membro.
	</ul>
	<br>
	<br>
	<li><b><a href="${pagePrefix}member_records"><u>Registros
	de membros</u></a>:</b> Permite ao corretor gerenciar os registros de membros.
	Estes registros permitem que você defina informações que serão
	agrupadas nos membros. Os sub-itens são auto-explicativos.<br>
	<br>
	<li><b><a href="${pagePrefix}reports#sms_log"><u>Registros
	de SMS</u></a>:</b> Da acesso aos registros de mensagens SMS enviadas pelos membros
	corretados. O sistema pode ser configurado para enviar mensagens SMS em
	várias ocasiões.<br>
	<br>
	<li><b>Corretagem:</b>
	<ul>
		<li><b>Cadastrar:</b> Permite ao corretor registrar um novo
		membro.
		<li><b>Alterar perfil:</b> Permite ao corretor alterar o <a
			href="${pagePrefix}profiles"><u> perfil</u></a> dos seus membros
		corretados.
		<li><b>Gerenciar comissões padrões:</b> Esta permite ao corretor
		definir os valores padrões para a sua própria <a
			href="${pagePrefix}brokering#commission"><u>comissão</u></a>.
		<li><b>Gerenciar contratos de comissões:</b> Se este for
		selecionado, o corretor pode gerenciar os <a
			href="${pagePrefix}brokering#commission_contract"><u>contratos
		de comissões</u></a> com o membro. Isto é feito através de um botão de ação
		abaixo do <a href="${pagePrefix}profiles"><u>perfil</u></a> do membro.

		
		<li><b>Gerenciar membros pendentes:</b> O corretor pode ver
		membros registrados que ainda não validaram (por e-mail) seus
		cadastros, dos quais ele seja corretor. <br>
		Mais informações estão disponíveis no <a
			href="${pagePrefix}user_management#search_pending_member"><u>Arquivo
		de ajuda</u></a>.
	</ul>
	<br>
	<br>
	<li><b>Mensagens pessoais:</b> Existe um item aqui, permitindo o
	corretor a enviar <a href="${pagePrefix}messages"><u>mensagens
	pessoais</u></a> para todos os membros dos quais ele é corretor.<br>
	<br>
	<li><b>Referências:</b> Permite ao corretor gerenciar (dar,
	apagar, modificar) as <a href="${pagePrefix}references"><u>referências</u></a>
	para o membro. <br>
	<br>
	<li><b>Relatórios:</b>
	<ul>
		<li><b>Ver:</b> Se esta for selecionada o corretor pode ver as <a
			href="${pagePrefix}reports#member_reports"><u>páginas de
		relatórios</u></a> dos membros que ele é corretor.
		<li><b>Exibir informações de conta:</b> Se você selecionar um
		tipo de conta aqui, o corretor também poderá ver o balanço das contas
		dos seus membros nos relatórios.
	</ul>
	<br>
	<br>
</ul>
<hr class="help">
</span>

<a name="group_filters"></a>
<h2>Filtros de grupos</h2>
Filtros de grupos são um tipo de &quot;super grupos&quot;: Uma série de
grupos agrupados com um único nome, com os quais você pode efetuar
certas ações. Resumindo um filtro de grupos é um &quot;Grupo de
grupos&quot;.
<br>
Filtros de grupos tornam-se bastante uteis para várias tarefas no
cyclos. Você pode efetuar cálculos estatísticos com filtros de grupos, e
membros e administradores podem efetuar buscas baseando-se nos filtros
de grupos.

<i>Onde encontrar.</i>
<br>
Os filtros de grupos podem ser acessados através do &quot;Menu: Usuários
& grupos > Filtros de grupos&quot;.
<hr>

<span class="admin"> <a name="group_filter"></a>
<h3>Modificar / criar um filtro de grupo</h3>
Nesta janela os <a href="#group_filters"><u>filtros de grupos</u></a>
podem ser definidos ou modificados. Se você modificar um filtro
existente, você deve primeiro clicar no botão de &quot;alterar&quot;
para poder alterar os campos do formulário. <br>
Não esqueça de clicar no botão &quot;enviar&quot; para salvar as suas
alterações.
<ul>
	<li><b>Nome:</b> Escolha um nome para o novo filtro de grupo.
	<li><b>Nome da página de acesso:</b> Esta opção será exibida
	apenas se você cutomizar a página de acesso para este filtrde ogrupo
	(em arquivos customizados, janela abaixo). A Página de acesso
	customizada (filtro de grupo) pode ser acessada colocando o nome da
	página de login após a página de acesso &quot;global&quot; com um
	caractér de interrogação. O nome da página de acesso não pode possuir
	espaços. Um exemplo pode ser:<br>
	http://www.seudominio.org/cyclos?nomedapaginadeacessodofiltrodegrupo.<br>
	Observe que é possível definir um nome de página de acesso por <a
		href="${pagePrefix}groups#group_details"><u>grupo</u></a>.
	<li><b>Url da página container</b> Esta configuração é usada caso
	você queira acessar o Cyclos a partir de um site web. Esta configuração
	funciona da mesma forma que a página container global (veja <a
		href="${pagePrefix}settings#local"><u>Configurações -
	Configurações locais</u></a>) mas apenas para este filtro de grupo. Neste campo
	você deve informar a página que abre o iframe ou frame-set que inclui o
	Cyclos. Por exemplo: http://www.seudominio.org/cycloswrapper.php.<br>
	Observe que também é possível especificar uma Url de página container
	por <a href="${pagePrefix}groups#group_details"><u>grupo</u></a>.
	<li><b>Descrição:</b> Uma descrição para os administradores, para
	deixar claro o uso do filtro.
	<li><b>Exibir no perfil:</b> Quando é selecionada, esta opção
	tornará o filtro de grupo visível no campo grupo de <a
		href="${pagePrefix}profiles"><u>perfil</u></a> do membro.
	<li><b>Grupos:</b> è o elemento mais importante deste formulário.
	Aqui você seleciona os grupos que você quer agrupar neste filtro de
	grupo.
	<li><b>Visualizável por:</b> Permite a você escolher quais grupos
	podem ver os filtros de grupos. Neste caso, o filtro do grupo sera
	exibido na busca dos membros e dos anúncios. Note: Se você escolher
	&quot;exibir no perfil&quot;, este sempre estará visível no perfil,
	independente do grupos que esteja visualizando. Assim estas
	configurações afetam apenas a função de busca.
</ul>
<hr class="help">
</span>

<span class="admin"> <a name="manage_group_filters"></a>
<h3>Gerenciar filtros de grupos</h3>
Esta janela mostra uma lista com os <a href="#group_filters"><u>filtros
de grupos</u></a> e da a opção de gerencia-los.
<ul>
	<li><b>Modificar</b> (ou ver) um filtro de grupo existente através
	do ícone de edição <img border="0" src="${images}/edit.gif" width="16"
		height="16">.
	<li><b>Apagar</b> um filtro de grupo através do ícone apagar <img
		border="0" src="${images}/delete.gif" width="16" height="16">.
	<li><b>Criar</b> um novo filtro de grupo clicando no botão de
	&quot;enviar&quot; chamado &quot;Inserir novo filtro de grupos&quot;.
</ul>
<hr class="help">
</span>

<span class="admin"> <a
	name="manage_group_filter_customized_files"></a>
<h3>Customizar arquivo (para o filtro de grupo)</h3>
Com esta função você pode definir um <a
	href="${pagePrefix}content_management"><u>arquivo customizado</u></a>
para um <a href="#group_filters"><u>filtro de grupo</u></a>. Significa
que cada filtro de grupo pode ter sua própria customização, como layout
(cores, estilos), logo e páginas como notícias, contato, manuais, etc.
Se nenhuma customização a nível de grupo ou de filtro de grupo for
feita, o layout principal sera usado. <br>
Customizações de grupo sobrescrevem as customizações de filtros de
grupos, assim se você definiu ambas, o sistema primeiro ira verificar se
existe customizações para o grupo e ira exibi-las. Caso não encontre irá
verificar se existe customizações para o filtro de grupo, e caso também
não exista usará a customização padrão do sistema. <br>
Elementos no formulário:
<ul>
	<li><b>Ver</b> uma pré-visualização do resultado da customização,
	através do ícone <img border="0" src="${images}/view.gif" width="16"
		height="16">&nbsp;visualizar.
	<li><b>Modificar</b> um arquivo customizado existente através do
	ícone <img border="0" src="${images}/edit.gif" width="16" height="16">&nbsp;editar.













	
	<li><b>Apagar</b> um arquivo customizado através do ícone <img
		border="	src=" ${images}/delete.gif"  idth="16" height="16">&nbsp;apagar.













	
	<li><b>Criar</b> um novo arquivo customizado através do botão
	chamado &quot;customizar novo arquivo&quot;.
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

