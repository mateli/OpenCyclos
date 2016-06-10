<div style="page-break-after: always;">
<a name="top"></a>
<br><br>A função de operadores permite aos
membros definirem operadores: um conjunto de sub-nível de membros que
são permitidos a fazer algumas tarefas para membros no cyclos.
Operadores não possuem uma conta própria, tem apenas acesso à conta de
seu membro onde ele pode fazer algumas operações. Pense em uma pequena
empresa com três empregados, onde cada empregado é um operador para a
conta da companhia.<br>
Da mesma forma que o membros, os operadores são organizados em grupos.
Cada membro pode definir seus próprios grupos de operadores, permitindo
a um membro criar diferentes níveis de operadores com diferentes
permissões. Você pode por exemplo criar um super operador ao qual é
permitido efetuar pagamentos, e alguns operadores simples que podem
apenas gerenciar anúncios. Todo pagamento feito por um operador terá um
campo extra &quot;Feito por&quot;, e o membro possui a possibilidade de
procurar por pagamentos feitos por operadores.
<span class="member notOperator"> <i>Onde encontrar.</i><br>
A função de operadores pode ser acessada através do &quot;Menu:
Operadores&quot;. Dentro deste menu, existem vários sub-menus dando
acesso às funcionalidades dos operadores:
<ul>
	<li><b>Operadores:</b> Leva você até a janela de procura de
	operadores. Você também pode criar um novo operador aqui.
	<li><b>Operadores conectados:</b> Mostra quais operadores estão
	conectados.
	<li><b>Grupos de operadores:</b> Permite que você defina
	diferentes níveis de operadores.
	<li><b>Campos customizados:</b> Permite a você criar campos
	especiais para operadores.
</ul>
</span>
Os operadores podem acessar o sistema através da página login normal, a
qual ira exibir um link extra &quot; Acesso como operador&quot; (o
módulo de operador precisa ser ativado nas configurações do sistema,
para que este link seja exibido).
<br>
Operadores também podem ter acesso ao módulo de pagamentos POSweb module
(informações sobre o POSweb podem ser encontradas na
<a href="${pagePrefix}payments#accesing_channels"><u>página de
ajuda</u> dos canais</a>
.
<br>
<span class="member"> Os operadores podem efetuar suas ações em
relação aos membros a partir de um item principal de menu, que só é
visível para operadores, e que é chamado &quot;operações de
membro&quot;. Através deste, o operador pode acessar todas
funcionalidades que normalmente são encontradas dentro do item de
&quot;menu: Pessoal&quot; do membro. </span>
<br><br><span class="admin"> <i>Como ativar.</i><br>
Operadores devem primeiro ser ativados através das <a
	href="${pagePrefix}groups#manage_group_permissions_member"><u>permissões</u></a>
de um grupo, bloco &quot;Operadores&quot;, na caixa de seleção chamada
&quot;gerenciar operadores&quot;.<br>
Junto às configurações de permissões, para os operadores poderem acessar
o sistema, tenha certeza de que o módulo de operadores esteja ativado
nas <a href="${pagePrefix}settings#access"><u>configurações de
acesso</u></a> através da caixa de seleção &quot;permitir acesso de
operadores&quot;.
<br><br><b>Note:</b> Se você customizar a página de acesso, tenha cuidado
para manter o código que é usado para permitir o acesso de operadores.
Caso contrário o link de acesso pode não ser visível.
<br><br><b>Note 2:</b> O módulo de operadores e as suas operações são
inteiramente de responsabilidade do seu membro. Um administrador não
pode gerenciar os operadores para um membro. A única administração que
um administrador pode fazer em relação aos operadores é desconecta-los
através da página de <a
	href="${pagePrefix}user_management#connected_users_result"><u>Usuários
conectados</u></a>.
</span>
<hr>

<span class="member notOperator"> <a name="search_operator"></a>
<h3>Procurar operadores</h3>
Nesta página você pode procurar por operadores (que você tenha
registrado). A função funciona da mesma maneira que uma procura por um
membro comum. Na caixa de seleção de grupo você pode deixar a opção de
filtro em &quot;Todos os grupos&quot; ou selecionar um ou mais grupos em
que você deseje procurar.<br>
Clique em &quot;procurar&quot; para exibir o resultado de sua busca.
<br><br>Você também pode criar um novo operador. Isto é feito
selecionando um grupo de operador na caixa de seleção abaixo desta
janela (&quot;criar um novo operador&quot;). Esta caixa só é visível
caso não exista nenhum resultado na janela.
<hr class='help'>
</span>

<span class="member notOperator"> <a
	name="search_operator_result"></a>
<h3>Resultado da busca por operadores</h3>
Esta página mostrará uma lista de resultados para a busca de operadores.
Clicando no nome ou nome de usuário do operador irá abrir a página de
perfil.
<hr class="help">
</span>

<span class="member notOperator"> <A NAME="create_operator"></A>
<h3>Criar operador</h3>
Nesta página você pode criar um novo operador. Todos os campos marcados
com um (*) asterisco vermelho são obrigatórios.<br>
Após preencher os campos você pode ir diretamente ao perfil (botão
&quot;Salvar e abrir perfil do operador&quot;) ou adicionar um novo
operador (botão &quot;Salvar e inserir um novo operador&quot;).
<hr class='help'>
</span>

<a name="operator_profile"></a>
<span class="member">
<h3>Perfil do operador</h3>
Esta janela mostra o perfil do operador. Muitos campos não podem ser
alterados, mas alguns podem. Clique no botão &quot;alterar&quot; para
poder fazer alterações; Quando pronto, clique em &quot;enviar&quot; para
salvar as alterações. </span>
<span class="member notOperator">
<br><br>Caso este operador esteja conectado neste momento, isto será
notificado, o campo &quot;último acesso&quot; exibirá (em vermelho) as
palavras &quot;esta conectado&quot;.
<hr class='help'>
</span>

<span class="member notOperator"> <A
	NAME="actions_for_operator_by_member"></A>
<h3>Ações para operadores</h3>
Aqui você pode efetuar várias ações neste operador. Esta ajuda dá um
resumo de todas as ações. Para uma explicação mais detalhada das ações
você pode consultar a ajuda da ação específica.
<br><br>As seguintes ações estão disponíveis:
<UL>
	<LI><b>Alterar grupo de permissões:</b> Alterar o grupo de
	operador ao qual este operador pertence.
	<LI><b>Enviar e-mail:</b> Enviar um e-mail ao operador. Isto
	abrirá o seu programa local de e-mail.
	<LI><b>Gerenciar senha de acesso:</b> Alterar a senha do operador.

	
	<li><b>Permitir o acesso do usuário agora:</b> Isto só é visível
	no caso do operador ter tentado acessar algumas vezes com a senha
	errada, e teve sua conta bloqueada temporariamente para o acesso.
	Normalmente, existe uma número máximo de tentativas permitidas; Se você
	tentar mais vezes com a senha errada, sua conta será bloqueada
	temporariamente por um período de tempo que é definido pelo
	administrador. Se você tem certeza de que este operador é quem diz ser,
	você pode liberar imediatamente o seu acesso clicando neste botão.
	Neste caso, o operador não precisará esperar que o tempo normal de
	espera tenha passado.
	<li><b>Desconectar usuário conectado:</b> Isto só é visível quando
	o operador esta conectado neste momento. Isto também é indicado pelo
	campo &quot;último acesso&quot; na janela de perfil acima; este
	mostrará as palavras &quot;Está conectado&quot;. Neste caso, você pode
	imediatamente forçar o operador a sair do programa, clicando neste
	botão. Você pode fazer isso quando por exemplo, existe uma investigação
	pendente de abuso.
</UL>
<hr class='help'>
</span>

<span class="member notOperator"> <A
	NAME="manage_operator_groups"></A>
<h3>Gerenciar grupo de operador</h3>
Esta página mostra uma lista com os grupos de <a href="#top"><u>operadores</u></a>.
Você pode efetuar as seguintes ações aqui:
<ul>
	<li><img border="0" src="${images}/edit.gif" width="16"
		height="16">&nbsp; O ícone de edição levará você à página de
	configurações para este grupo.
	<li><img border="0" src="${images}/permissions.gif" width="16"
		height="16">&nbsp; O ícone de permissões levará voce à página
	onde você pode definir as permissões para este grupo. Este ícone será
	desativado (acinzentado, <img border="0"
		src="${images}/permissions_gray.gif" width="16" height="16">&nbsp;)
	quando o grupo tiver o estado &quot;Removido&quot;.
	<li><img border="0" src="${images}/delete.gif" width="16"
		height="16">&nbsp; Clicando no ícone de apagar irá remover o
	grupo. Você apenas pode apagar um grupo caso não exista nenhum membro
	(operador) nele.
	<li><b>Adicionar:</b> Para adicionar um novo grupo de operadores
	você terá que clicar no botão de enviar chamado &quot;Inserir novo
	grupo&quot;.
</ul>
<hr class='help'>
</span>

<span class="member notOperator"> <A
	NAME="manage_group_permissions_basic"></A>
<h3>Permissões básicas de grupo</h3>
Nesta janela você pode definir as permissões básicas. As permissões
básicas não afetam outras funções. As seguintes permissões podem ser
definidas:
<ul>
	<li><b>Acesso: </b><br>
	Se este não for marcado, operadores deste grupo não poderão acessar o
	sistema.
	<li><b>Mensagem de convite: </b><br>
	Caso marcado, os membros deste grupo verão uma janela em sua página
	principal (após conectarem), com a qual eles podem convidar um amigo a
	testar sua organização.
</ul>
<hr class='help'>
</span>

<span class="member notOperator"> <A
	NAME="manage_operator_group_permissions"></A>
<h3>Gerenciar permissões do grupo de operador</h3>
Nesta janela você pode definir as <a
	href="${pagePrefix}groups#permissions"><u>permissões</u></a> para um
grupo de <a href="#top"><u>operadores</u></a>. Estas permissões são
derivadas de seu próprio grupo de permissões: um operador nunca pode
fazer mais do que você mesmo é permitido; Um operador pode fazer igual
ou menos.<br>
Por essa razão, muito provavelmente você não verá todas as opções
listadas nesta ajuda. Use os links para obter mais informações sobre o
item.
<br><br>O operador que pertence a este grupo pode receber estas
permissões (dependem das configurações do sistema e das suas próprias
permissões):
<br><br><b>Conta de membro</b>
<ul>
	<li><b>Ver pagamentos autorizados</b>
	<li><b>Ver pagamentos agendados</b>
	<li><b>Ver informações de conta:</b> use a caixa de seleção para
	selecionar para qual conta o operador pode ver as informações
	(pagamentos, balanços, etc).
</ul>

<b>Anúncios</b>
<ul>
	<li><b>Publicar</b> Se &quot;Publicar&quot; for marcado, um
	operador pode publicar anúncios e o item de menu &quot;Pessoal -
	Anúncios&quot; será exibido no menu do operador.
</ul>

<b>Lista de contatos</b>
<ul>
	<li><b>Gerenciar:</b> Permite ao operador gerenciar a <a
		href="${pagePrefix}user_management#contacts"><u>lista de
	contatos</u></a>, que é adicionar, editar ou apagar membros da lista.
	<li><b>Ver:</b> Permite ao operador apenas ver a lista de contatos
	(e usar isso), mas não possui permissão para alterar isso.
</ul>

<b>Garantias</b><br>
Isto é parte do sistema de garantias do cyclos, onde cada balanço de
conta no cyclos é garantido por uma soma de dinheiro apoiadora. Você
pode escolher as seguintes permissões:
<ul>
	<li><b>Vender com obrigações de pagamentos:</b>
</ul>

<b>Faturas eletrônicas</b> <br>
Nesta seção você pode definir se um operador pode enviar <a
	href="${pagePrefix}invoices"><u>faturas eletrônicas</u></a> para outros
membros, tanto a partir do perfil de um membro quanto diretamente no
menu &quot;Conta&quot;. Quando &quot;faturas de sistema&quot; é
selecionado um operador pode enviar faturas para contas de sistema a
partir do &quot;Menu conta&quot;.
<ul>
	<li><b>Opção de fatura eletrônica a partir do menu:</b> Irá exibir
	a opção de faturas eletrônica para membro a partir do menu.
	<li><b>Enviar para membro:</b> Permite enviar faturas para outros
	membros.
	<li><b>Enviar para sistema:</b>Permite enviar faturas para contas
	de sistema.
	<li><b>Ver:</b> Ver faturas eletrônicas.
</ul>

<li><b>Empréstimos:</b> Nesta seção você pode definir as permissões
de <a href="${pagePrefix}loans"><u>empréstimos</u></a> para operadores.
<ul>
	<li><b>Ver:</b> Se marcada esta opção, os operadores deste grupo
	podem ver os empréstimos de seu membro. Caso contrário o item de menu
	não será exibido.
	<li><b>Pagar:</b> Selecione esta opção para permitir que o
	operador efetue o pagamento de empréstimos.
</ul>
<li><b>Mensagens:</b> Nesta seção você pode definir até onde os
operadores podem usar os sistema de <a href="${pagePrefix}messages"><u>mensagens</u></a>
do cyclos.
<ul>
	<li><b>Ver:</b> O operador pode ver o sistema de mensagens.
	<li><b>Enviar para membro:</b> Permite ao operador enviar
	mensagens para outros membros.
	<li><b>Enviar para administração:</b> Permite ao operador enviar
	mensagens para a administração.
	<li><b>Gerenciar:</b> O operador pode mover e apagar mensagens.
</ul>


<b>Pagamentos:</b> Aqui você pode especificar quais tipos de pagamentos
são permitidos ao grupo de operador. Normalmente você irá selecionar
apenas um ou alguns tipo.
<ul>
	<li><b>Auto pagamento:</b> Se esta opção for selecionada o
	operador pode fazer pagamentos entre suas contas. na caixa de seleção
	você pode especificar os possíveis tipos de transações. Esta opção só
	faz sentido caso você possua mais de uma conta.
	<li><b>Member payments:</b> Se selecionado o operador pode pagar
	outros membros.
	<li><b>Opção de pagamento de membros a partir do menu:</b> Se esta
	opção for marcada, os operadores podem efetuar pagamentos para outros
	membros diretamente a partir do menu &quot;Conta&quot;.
	<li><b>Pagamentos de sistema:</b> Se selecionado o operador pode
	pagar para um conta de sistema. Se esta opção não estiver selecionada o
	item de menu &quot;Pagamentos de sistema&quot; não será exibido.
	<li><b>Fazer pagamento POSweb:</b> Permite que um operador faça um
	pagamento na página POSweb (ponto de venda).
	<li><b>Receber pagamento POSweb:</b> Escolha esta opção no caso de
	você querer permitir aos operadores receber pagamentos através do
	&quot;Ponto de venda&quot; POSweb (POS). Isto é a situação típica de
	uma loja. O operador da loja, acessa o sistema com seu nome de usuário
	(normalmente através do endereço http://..cyclos/posweb) a interface do
	posweb; na janela seguinte o cliente pode informar sua senha PIN para
	fazer uma pagamento para loja.<br>
	Marcando esta opção permitirá este procedimento. (normalmente, você
	deve desmarcar qualquer outra permissão para o operador.)
	<li><b>Autorizar ou negar:</b> Permite ao operador autorizar ou
	negar um pagamento caso você seja o vendedor (recebedor).
	<li><b>Cancelar pagamento autorizado:</b> Quando pagamentos
	autorizados são usados, esta opção permitirá a operadores cancelar seus
	pagamentos autorizados desde que eles tenham sido criados, mas ainda
	não autorizado.
	<li><b>Cancelar pagamento agendado:</b> Quando pagamentos
	agendados são usados, isto permitirá aos operadores cancelar seus
	pagamentos agendados antes que a data planejada seja alcançada.
	<li><b>Bloquear pagamento agendado:</b> Permite ao operador
	bloquear temporariamente seus pagamentos agendados.
	<li><b>Solicitar pagamentos de outros canais:</b> Quando este é
	marcado, o operador pode enviar solicitações de pagamento (faturas
	eletrônicas externas) através de outros canais; você pode escolher
	estes canais na caixa de seleção.
</ul>

<b>Referências</b> <br>
Isto permite aos operadores ver ou gerenciar as <a
	href="${pagePrefix}references"><u>referências</u></a>.
<ul>
	<li><b>Ver:</b> ver as referências
	<li><b>Gerenciar minhas referencias:</b> Permite ao operador usar
	o sistema de referências, incluindo a permissão de dar uma referência a
	outro membro.
	<li><b>Gerenciar minhas qualificações de transações:</b> Permite
	ao operador gerenciar suas <a
		href="${pagePrefix}transaction_feedback#feedbacks_summary"><u>
	qualificações de transações</u></a>, incluindo a permissão de dar qualificação
	em uma transação.
</ul>

<b>Relatórios</b><br>
Se &quot;Ver meus relatórios&quot; for selecionado, o operador pode ver
suas próprias <a href="${pagePrefix}reports#member_activities"><u>páginas
de relatórios</u></a>.
</ul>
<hr class='help'>
</span>

<span class="member notOperator"> <a name="edit_operator_group"></a>
<h3>Modificar grupo de operador</h3>
A configuração do grupo de <a href="#top"><u>operador</u></a> é dividida
em duas partes:
<ul>
	<li><b>Detalhes do grupo:</b> Aqui você pode apenas alterar o nome
	e a descrição do grupo de operadores.
	<li><b>Valor máximo por dia por tipo de pagamento:</b> Com esta
	configuração você pode definir o valor máximo por dia por tipo de
	pagamento. Todos os tipos de pagamentos disponíveis estão listados
	aqui; para cada tipo você pode especificar de existe um limite de valor
	que um operador pode pagar com este tipo de pagamento.
</ul>
Você pode ir direto para as permissões deste grupo clicando no botão
&quot;Permissões do grupo&quot;.
<hr class='help'>
</span>

<span class="member notOperator"> <A NAME="insert_operator_group"></A>
<h3>Inserir novo grupo de operador</h3>
Esta janela permite a você criar um novo grupo de <a href="#top"><u>operadores</u></a>.
<br>
Você tem as seguintes opções:
<ul>
	<li><b>Removido:</b> Se você criar um grupo removido, significa
	que os operadores que forem colocados neste grupo deixaram realmente o
	sistema. Uma vez no grupo de removido uma operador não pode ser
	colocado e nenhum outro grupo. Os dados continuarão no banco de dados,
	e serão visíveis por você, mas servirão apenas como uma função de
	backup.
	<li><b>Nome:</b> Nome do grupo.
	<li><b>Descrição:</b> Descrição do grupo.
	<li><b>Copiar dados de:</b> Isto só estará visível se já existir
	um outro grupo de operadores configurado. Você pode especificar um novo
	grupo de operadores aqui; assim as configurações para o novo grupo
	criado serão copiadas do grupo especificado.
</ul>
Após ter criado os novo grupo, você deve definir as propriedades do
grupo na próxima tela, e você também deve configurar as permissões do
grupo.
<hr class='help'>
</span>

<span class="member notOperator"> <a
	name="manage_group_customized_files"></a>
<h3>POSweb customizado</h3>
Você pode customizador o cabeçalho e rodapé para o POSweb. Esta janela
mostra uma lista de customizações para este grupo. Você tem as seguintes
opções:
<ul>
	<li><b>Modificar</b> um arquivo customizado existente através do
	ícone de edição <img border="0" src="${images}/edit.gif" width="16"
		height="16">&nbsp;.
	<li><b>Ver</b> como o resultado irá parecer para o membro deste
	grupo através do ícone visualizar <img border="0"
		src="${images}/view.gif" width="16" height="16">&nbsp;.
	<li><b>Apagar</b> uma definição de arquivo customizado através do
	ícone <img border="0" src="${images}/delete.gif" width="16" height="16">&nbsp;.

	
	<li><b>Inserir</b> um novo arquivo customizado através no botão
	&quot;customizar novo arquivo&quot;.
</ul>
<hr class="help">
</span>


<span class="member notOperator"> <a name="customize_group_file"></a>
<h3>Modificar cabeçalho e rodapé do POSweb</h3>
Nesta página você pode customizar o cabeçalho e rodapé da página do
POSweb. A página que pode ser acessada por operadores para fazer e
receber pagamentos. (O endereço de acesso ao POSweb é normalmente algo
como www.domain.com/cyclos/posweb)<br>
Após o operador se conectar, o cabeçalho e rodapé serão exibidos acima e
abaixo da janela de pagamentos.
<hr class="help">
</span>


<span class="member notOperator"> <A NAME="change_group_operator"></A>
<h3>Alterar grupo de operador</h3>
Nesta janela você pode colocar um <a href="#top"><u>operador</u></a> em
outro grupo. Isto significa que o operador receberá as permissões para
outros grupos. Após ter preenchido o formulário, clique no botão
&quot;Alterar grupo&quot; para salvar suas alterações. <br>
<br><br>Clicando na opção &quot;Remover permanentemente&quot; irá remover
o operador. Isto só é possível se o operador ainda não efetuou nenhuma
transação.<br>
Caso contrário, você terá que move-lo para um grupo de
&quot;Removidos&quot;; Isto significa que este operador não poderá
efetuar mais nenhuma ação (nem mesmo acessar o sistema), mas as suas
ações no passado continuarão visíveis por você.
<hr class='help'>
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