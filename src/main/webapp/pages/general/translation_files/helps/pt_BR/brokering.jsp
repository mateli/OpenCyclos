<div style="page-break-after: always;">
<br><br>O membro de um tipo de grupo de
&quot;Corretor&quot; pode registrar novos membros e possuir algum nível
de acesso e controle sobre esses membros ( dependendo da configuração do
grupo de corretor ). O nome &quot;corretor&quot; não é o mais adequado
pois esta função pode ser usada para muitos propósitos diferentes.<br>
Um função comum de corretor é que ele pode receber <a href="#commission"><u>
comissões</u></a> quando registra membros. A comissão pode ser configurada com
base no valor transacionado por esse novo membro. A idéia é que isto
sirva como incentivo ao corretor para fazer o &quot;acompanhamento&quot;
desses membros com maior empenho.<br>
O corretor pode também estar apto a fazer parte da administração pessoal
de grupos de membros que não tenham segurança em usar o computador para
fazer certas tarefas no cyclos.<br>
<span class="broker admin"> A função de corretor também pode ser
usada por agentes de empréstimos em sistemas de micro finanças. O agente
de empréstimo como um corretor também pode registrar novos membros e
buscar informações sobre os estado dos empréstimos dos membros. Sistemas
comunitários como LETS podem usar a função de corretor para
administradores do bairro que podem ajudar outros membros que não tenham
acesso ou habilidade para usar a internet/celular. Diferentes tipos de
grupos de corretor podem existir no mesmo sistema. </span>
É possível aos corretores configurar
<a href="#commission_contracts"><u>contratos</u></a>
de comissões individuais para cada membro.

<br><br><span class="broker"> <i>Onde encontrar.</i><br>
Você pode acessar todas as funções de corretagem através do &quot;Menu:
Corretagem&quot;.<br>
Informações de corretagem e funções relacionadas a um de seus membros
&quot;corretados&quot; podem ser encontradas na <a
	href="${pagePrefix}profiles#actions_for_member_by_broker"><u>página
de ações do corretor</u></a> abaixo do <a
	href="${pagePrefix}profiles#member_profile"><u>perfil</u></a> deste
membro.</span>
<br><br><span class="admin"> <i>Onde encontrar.</i> <br>
Administradores não tem acesso a função de corretor, mas eles podem
possuir um certo nível de acesso a funções de corretor tanto para o
corretor quanto para o membro atribuído a esse corretor. Isto pode ser
feito através da <a
	href="${pagePrefix}profiles#actions_for_member_by_admin"><u>página
de ações</u></a> abaixo do <a href="${pagePrefix}profiles#member_profile"><u>perfil</u></a>
do membro ou do corretor.<br>
Como explicado a função de corretagem pode ser usada para muitos
propósitos. Portanto buscar e funções relacionadas a corretores podem
aparecer em outros módulos. Por exemplo uma busca de empréstimo pode
possuir um filtro de corretor e um administrador pode efetuar varias
ações relacionadas ao corretor. Estas funções são explicadas no contexto
da própria função.

<br><br><i>Como ativar.</i><br>
Para poder ativar a corretagem você deve configurar as <a
	href="${pagePrefix}groups#manage_group_permissions_broker"><u>permissões
de corretor</u></a> no bloco &quot;corretagem&quot; e <a
	href="${pagePrefix}groups#edit_broker_group"><u>configurações
do grupo de corretor</u></a> no bloco &quot;corretagem&quot;. Este último tem
apenas um item, se você quer que os corretores registrem novos membros
você deve definir aqui.<br>
Comissões do corretor podem ser ativadas no nível do <a
	href="${pagePrefix}account_management#transaction_type_details"><u>tipo
de transação</u></a>na configuração <a
	href="${pagePrefix}brokering#broker_commission_list"><u>
comissões de de corretor</u></a>.<br>
Você também precisa definir as <a
	href="${pagePrefix}groups#manage_group_permissions_broker"><u>permissões
do corretor</u></a> dentro do bloco &quot;corretagem&quot;). Para um membro ver
as comissões as <a
	href="${pagePrefix}groups#manage_group_permissions_member"><u>permissões</u></a>
também devem ser definidas (bloco &quot;Comissões&quot;).
<br><br><b>Observe</b> que não faz sentido dar acesso as comissões aos
membros e corretores, sem definir e habilitar as comissões como
administrador na configuração do tipo de transação, como apontado acima.
Neste caso os membros e corretores serão confrontados com caixas vazias
porque eles terão acesso às comissões de corretor e contratos sem estar
aptos a adaptar ou definir estes, pois eles simplesmente não existirão.<br>
Tem certeza de não criar apenas a taxa de comissão do corretor, você
deve ativar ela também.
</span>
<hr>

<span class="broker admin"> <A NAME="broker_search_member"></A>
<h3>Procurar membros do corretor</h3>
Esta função gera uma lista de membros relacionados ao corretor. <br>
Você pode buscar por:
<ul>
	<li><b>Nome de usuário / membro:</b> Busca por um membro
	determinado.
	<li><b>Grupo de permissões:</b> Busca por grupo.
	<li><b>Estado:</b>
	<ul>
		<li><b>Ativo: </b>Estes são os membros que estão ativos no
		sistema (em um grupo &quot;ativo&quot;).
		<li><b>Comissão terminada: </b> Mostra os membros para os quais a
		<a href="#commission"><u>comissão</u></a> foi recebida e terminou.
		<li><b>Aguardando ativação: </b>Estes são membros que você
		registrou mas ainda não estão ativos (porque eles devem ser ativados
		primeiros por um corretor ou administrador).
	</ul>
</ul>
<hr class="help">
</span>

<span class="broker"> <A NAME="broker_search_member_result"></A>
<h3>Resultado da busca de membros - corretor</h3>
Esta janela mostra uma lista de membros que foram registrados com você
como corretor. <br>
Na lista de membros você pode selecionar o membro ou o nome de usuário
para abrir a página de <a href="${pagePrefix}profiles"><u>perfil</u></a>
do membro.
<hr class="help">
</span>

<span class="admin"> <A NAME="admin_brokering_list"></A>
<h3>Lista de membros (do corretor)</h3>
Esta página mostra uma lista de membros relacionados com um corretor em
particular. Você pode selecionar o nome e ir para o perfil deste membro.
<br>
Clicando no ícone <img border="0" src="${images}/delete.gif" width="16"
	height="16">apagar irá remover a relação a relação do broker com
o membro (você será questionado para confirmar).
<hr class="help">
</span>


<span class="admin"> <A NAME="add_member_to_broker"></A>
<h3>Adicionar membro ao corretor</h3>
Nesta página você pode adicionar um membro à lista de corretagem de um
corretor. Os campos de nome de usuário e nome do corretor serão
preenchidos automaticamente quando você digitar os caracteres iniciais.
Se o membro já é relacionado a outro corretor e existe uma <a
	href="#commission"><u>comissão</u></a> ativa, você pode suspende-la
marcando "suspender comissão".<br>
Observe que você pode reatribuir uma série de membros à outro corretor
através das <a href="${pagePrefix}user_management#bulk_actions"><u>ações
em massa</u></a>.
<hr class="help">
</span>

<span class="admin"> <A NAME="change_broker"></A>
<h3>Definir / trocar corretor</h3>
Nesta página você pode definir o corretor ao qual o membro é
relacionado. O campo &quot;Corretor atual&quot; mostrará o corretor ao
qual o membro esta atualmente relacionado. Este campo pode estar vazio
&quot;nenhum&quot; por que o membro pode não estar relacionado a nenhum
corretor. Se você quer relacionar um membro com um corretor você pode
digitar o novo corretor no campo de nome de usuário ou nome do corretor.
Quando o corretor é mostrado você pode relacionar o corretor escrevendo
um comentário (obrigatório) e clicando no botão enviar abaixo.<br>
Se você quiser para alguma <a href="#commission"><u>comissão</u></a> que
já esta ativa para o corretor atual você pode selecionar o campo
&quot;suspender comissão&quot;. Se você não suspender a comissão o novo
corretor irá herdar as definições de comissão do corretor antigo. isto
significa que a comissão sera cobrada a partir do dia que o corretor
recebeu o novo membro até a data de fim que esta configurada nas
definições da comissão.
<hr class="help">
</span>

<span class="admin"> <a name="remove_member_to_broker"></a>
<h3>Remover membro</h3>
O título desta janela pode ser um pouco alarmante, mas nesta janela a
única coisa que ira acontecer se você clicar em enviar, é que o membro
não estará mais relacionado com o corretor.<br>
Antes de clicar no botão &quot;Enviar&quot; você pode adicionar um
comentário sobre a razão para esta remoção.<br>
Observe que você pode re-atribuir uma série de membros a outro corretor
através das <a href="${pagePrefix}user_management#bulk_actions"><u>ações
em massa</u></a>.
<hr class="help">
</span>


<a name="commission"></a>
<h2>Comissões de corretor</h2>
Para este trabalho, o corretor pode receber uma comissão; isto é uma
pagamento pequeno, relacionado à atividade dos membros deste corretor.
Quando um corretor registra um novo membro, normalmente este membro se
torna um dos membros do corretor. O corretor pode receber um pagamento
pequeno para cada transação que este membro estiver envolvido. A idéia
por trás disto é estimular os corretores a fazer o acompanhamento destes
membros com bastante empenho.

Quando um corretor é movido para um outro grupo todos os contratos e
comissões ativas serão fechadas.
<hr>

<span class="admin broker"> <A NAME="broker_commission_list"></A>
<h3>Lista de comissões do corretor</h3>
Esta janela mostra uma lista com todas as <a href="#commission"><u>comissões</u></a>
configuradas para o corretor (tanto ativas quando desativadas).
<ul>
	<li><img border="0" src="${images}/edit.gif" width="16"
		height="16"> Clique no ícone para modificar a corretagem.
	<li><img border="0" src="${images}/delete.gif" width="16"
		height="16"> Clique no ícone se você quiser apagar a corretagem.



	
</ul>
Para adicionar uma nova comissão clique em &quot;Inserir nova comissão
de corretor&quot;.
<hr class="help">
</span>


<span class="admin broker"> <A NAME="broker_commission_details"></A>
<h3>Detalhes da comissão do corretor</h3>
Da mesma forma que um taxa de transação, a <a href="#commission"><u>comissão</u></a>
de corretor é creditado/debitada quando a transação &quot;pai&quot; é
efetuada e o critério para a comissão do corretor é atingido.<br>
Sempre use o botão &quot;Alterar&quot; abaixo para poder efetuar trocas;
Clique em &quot;enviar&quot; para salvar suas mudanças.
<br><br>A comissão tem as seguintes configurações:
<ul>
	<li><b>Tipo de transação:</b> Este é o tipo de transação na qual a
	comissão do corretor é invocada.
	<li><b>Nome:</b> Nome da comissão do corretor.
	<li><b>Descrição:</b> Descrição da comissão.
	<li><b>Habilitada:</b> A comissão é ativa quando marcada esta
	caixa. Tenha certeza de marcar esta caixa, ou do contrário a comissão
	não funcionará.
	<li><b>Quem irá pagar:</b> Aqui você pode definir quem será
	cobrado. Este pode ser o comprador (pagante), o vendedor (recebedor) ou
	uma conta de sistema.
	<li><b>Quem irá receber:</b> Aqui você pode definir quem irá
	receber a taxa. Este pode ser o corretor do comprador (pagante) ou do
	vendedor (recebedor).
	<li><b>Permitir qualquer conta:</b> Se esta caixa estiver marcada,
	não existirá limitação do domínio no qual a taxa de transação pode ser
	aplicada. Se você cobrar a taxa de uma conta diferente da conta do tipo
	de transação que invoca este taxa você deve marcar esta opção. Por
	exemplo se você quiser tipos de transações em outra moeda para
	comissão.
	<li><b>Tipo de transação gerado:</b> Aqui você define qual tipo de
	transação sera gerado. É comum criar um tipo de transação específico
	para isso, pois assim você pode filtrar estas. (ex. no histórico da
	conta: Taxas & Impostos). O banco de dados padrão vem com uma taxa de
	transação e com um tipo de transação para ela.
	<li><b>Valor:</b> Aqui você pode definir o valor da comissão. O
	corretor receberá a comissão cada vez que o seu membro fizer um
	pagamento (caso a condição de aplicabilidade tiver sido satisfeita).<br>
	No caso de <a href="#commission_contract"><u>contratos</u></a> de
	corretor, o valor da comissão do corretor pode ser modificado pelo
	corretor. Neste caso , o valor será usado como valor padrão da comissão
	para o contrato. O corretor pode alterar esses valores na criação dos
	&quot;contratos de corretor&quot; para cada um de seus membros. Veja
	também o próximo item.
	<li><b>Valor máximo fixo e %:</b> Esta opção esta relacionada com
	os contratos de corretor e apenas sera mostrada caso no campo
	&quot;Quem irá pagar&quot; seja selecionado a opção membro (e não uma
	conta de sistema). O valor irá definir o valor máximo que um corretor
	pode colocar em um contrato. (O corretor precisa ter permissões para
	gerenciar contratos de corretor).
</ul>

<li><b>Condição de aplicabilidade</b><br>
Aqui você pode definir sob quais condições a taxa sera aplicada. Apenas
se estas condições forem satisfeitas a taxa sera aplicada. As condições
podem ser combinadas.
<ul>
	<li><b>Valor maior ou igual:</b> A taxa apenas sera aplicada se o
	valor da transação for maior ou igual ao valor especificado.
	<li><b>Valor menor ou igual:</b><br>
	A taxa apenas sera aplicada se o valor da transação for menor ou igual
	ao valor especificado.
	<li><b>De todos os grupos:</b> Se esta opção estiver marcada, a
	taxa sera aplicada para membros de qualquer grupo fazendo um pagamento
	do tipo de transação. Se você quiser aplicar a taxa apenas para grupos
	específicos, então você precisara desmarcar esta opção, e uma caixa de
	seleção irá aparecer para você especificar os grupos.
	<li><b>Para todos os grupos</b><br>
	Se esta opção estiver marcada, a taxa sera aplicada para membros de
	qualquer grupo recebendo um pagamento do tipo de transação. Se você
	quiser aplicar a taxa apenas para grupos específicos, então você
	precisara desmarcar esta opção, e uma caixa de seleção irá aparecer
	para você especificar os grupos.
	<li><b>Todos grupos de corretores</b><br>
	Se esta opção estiver marcada, a taxa sera aplicada para corretores de
	qualquer grupo envolvido. Se você quiser aplicar esta taxa à grupos
	específicos de corretores, então você precisará desmarcar esta opção, e
	uma caixa de seleção irá aparecer para você especificar quais grupos de
	corretores estão envolvidos.
	<li><b>Quando a comissão é paga:</b> Aqui você pode definir quando
	a comissão será cobrada. Esta opção pode ser:
	<ul>
		<li><b>Sempre:</b> A comissão do corretor será sempre
		(infinitamente) cobrada. (A comissão pode ser parada através da
		página: perfil do membro - definir corretor).
		<li><b>Transações:</b> A comissão do corretor ira parar após um
		número de transações. A quantidade pode ser inserida no campo que se
		abrirá após esta caixa, caso você escolha esta opção.
		<li><b>Dias:</b> A comissão do corretor ira parar após um número
		de dias. A quantidade pode ser inserida no campo que se abrirá após
		esta caixa, caso você escolha esta opção.
	</ul>
</ul>
<hr class="help">
</span>

<span class="admin broker"> <A NAME="commission_settings"></A>
<h3>Configurações da comissão</h3>
Nesta janela você pode verificar as configurações padrões da <a
	href="#commission"><u>comissão</u></a>. As configurações serão
aplicadas para todos os novos membros registrados, A menos que elas
tenham sido anuladas pelas mudanças feitas pelo corretor nas suas
configurações ou em <a href="#commission_contract"><u>contratos</u></a>
individuais entre membro e corretor. <br>
Se a administração não definir nenhuma configuração para comissão, esta
janela estará vazia. Se você tiver ativado o gerenciamento de comissão
para membros e corretores, você deve também definir a comissão padrão.
Veja o início deste documento. </span>
<span class="broker"> Nesta janela você pode definir as
configurações padrões da <a href="#commission"><u>comissão</u></a>.
Estas definições serão aplicadas para todos os seus novos membros
registrados. Enquanto você não definir a comissão padrão nesta janela, a
administração pode mudar as configurações padrões da comissão. Um vez
que você tenha definido suas configurações de comissão nesta janela,
elas irão anular as configurações da administração. <br>
Observe que <a href="#commission_contract"><u>contratos</u></a>
individuais que você definiu com seus membros, irão novamente anular as
definições que você especificou aqui.
<br><br>Se a administração não definiu nenhuma configuração de comissão,
esta janela estará vazia. Neste caso você deve notifica-los.
<br><br>Você pode mudar suas configurações clicando no botão
&quot;alterar&quot; abaixo; use o botão &quot;enviar&quot; para salvar
suas mudanças. Isto apenas sera visível no caso de você ter permissões
para alterar as configurações padrão. <br>

O estado não pode ser alterado.
</span>
O Estado pode ter os seguintes valores:
<br>
<ul>
	<li><b>Ativa:</b> Significa que todas as comissões de corretor
	configuradas serão cobradas se as condições forem satisfeitas.
	<li><b>Inativa:</b> Este estado significa que nenhuma comissão
	pode ser cobrada. Se este for o caso, isso significa que foi
	configurada pelo sistema, e foi definida por um administrador.
	<li><b>Suspendida:</b> A comissão do corretor esta temporariamente
	suspensa.
</ul>
<span class="admin">Um administrador tem a opção de suspender
(parar temporariamente) todas comissões ativas. Mesmo quando os <a
	href="#commission_contract"><u>contratos</u></a> de corretor estão
ativados, o corretor pode adicionar um novo contrato de comissão, mas
ele irá entrar diretamente com o estado de suspenso. </span>
<hr class="help">

<a name="commission_contract"></a>
<h2>Contrato de comissão</h2>
Um contrato de comissão é um arranjo entre o corretor e o membro.
Normalmente o corretor recebe ou uma porcentagem de cada pagamento do
membro ou um valor fixo em cada pagamento. Esta
<a href="#commission"><u>comissão</u></a>
pode ser paga pelo comprador (pagante) ou pelo vendedor (recebedor), ou
pode ser paga pela organização (de uma conta de sistema).
<br>
Dependendo da configuração, os corretores são livres para configurar um
contrato individual diferente com cada membro relacionado a ele. O
membro tem que aceitar este contrato antes que ele posso ser aplicado. O
membro também pode aceitar ou negar um novo contrato de corretor
proposto.

O membro pode ver os detalhes da comissão e ambos membro e corretor
receberão uma notificação quando o estado da comissão for trocada.
<br><br><span class="broker"> Dependendo da configuração do
sistema, um corretor pode definir a comissão por membro. Pode existir
apenas uma comissão ativa por período (é possível ter mais de uma
comissão se os períodos destas não se coincidirem).<br>
<br>
Nota: Quando o corretor é movido para qualquer outro grupo, todos os
contratos e comissões serão fechados.
<br><br><i>Onde encontrar.</i><br>
Contratos de comissões podem ser acessados através do &quot;Menu:
Corretagem > Contratos de comissão&quot;. Você pode adicionar um novo
contrato de comissão para um membro através do perfil do membro, na
janela de &quot;ações de corretagem&quot;, botão &quot;Contratos de
comissões&quot;. No final da página que você irá chegar, ira existir um
botão para criar um novo contrato.
</span> <span class="member"><%-- dit is ook zichtbaar voor brokers. Als broker zie ik nu 2x where to find --%>
<i>Onde encontrar</i><br>
Você pode entrar os contratos de corretagem no &quot;Menu: Pessoal >
Estado da cobrança de comissões&quot;. Ele é visível apenas se você
tiver permissões para ele. </span>
<hr>

<span class="admin broker"> <A
	NAME="commission_contracts_search_filters"></A>
<h3>Busca por contratos de comissão do corretor</h3>
Nesta página você pode buscar por <a href="#commission_contract"><u>contratos
de comissões</u></a> existentes.<br>
A maioria das opções de busca são auto-explicativas. O estado é
explicado <a href="#commission_contract_status"><u>aqui</u></a>.
<hr class="help">
</span>

<span class="admin broker"> <A
	NAME="commission_contracts_search_results"></A>
<h3>Resultado da busca por contratos de comissão</h3>
Esta janela irá mostrar uma lista com todas os <a
	href="#commission_contract"><u>contratos de comissão</u></a> e seus <a
	href="#commission_contract_status"><u>estados</u></a>.
<ul>
	<li><img border="0" src="${images}/edit.gif" width="16"
		height="16"> Clique no ícone lupa para entrar nos detalhes.
	<li><img border="0" src="${images}/delete.gif" width="16"
		height="16"> Clique no ícone apagar para apagar o contrato. Este
	só é visível se você tiver permissões, e se o contrato não estiver no
	estado ativo.
</ul>
<hr class="help">
</span>

<a name="commission_contract_status"></a>
<h3>Estado do contrato de comissão</h3>
Um
<a href="#commission_contract"><u>contrato de comissão</u></a>
pode ter os seguintes estados:
<ul>
	<li><b>Pendente: </b> O contrato estará no estado pendente até que
	o membro confirme e aceite o este contrato de comissão.
	<li><b>Aceitado: </b> Este estado significa que o contrato foi
	aceito pelo membro mas a data de início dele ainda não foi atingida.
	<li><b>Ativo: </b> Assim que o contrato foi aceito e a data de
	início foi atingida, o contrato de comissão irá para o estado ativo,
	significando que a comissão sera cobrada de acordo com a definição
	deste contrato.
	<li><b>Negado: </b> Se o contrato for negado pelo membro ele irá
	para o estado negado.
	<li><b>Expirado: </b> Se o contrato não for aceito antes da data
	de início ele irá para o estado de expirado.
	<li><b>Cancelado: </b> Um corretor pode cancelar um contrato de
	comissão, oque significa que pagamentos futuros não irão mais gerar
	comissão.
	<li><b>Fechado: </b> O contrato de comissão terminou, porque a
	data de fim foi alcançada e as comissões foram cobradas.
</ul>
<hr class="help">

<A NAME="commission_charge_status"></A>
<h3>Estado da comissão</h3>
Esta janela mostra o resultado de uma busca rápida com informações sobre
as
<a href="#commission"><u>comissões</u></a>
atuais. Esta pode ser a comissão padrão ou um
<a href="#commission_contract"><u>contrato de comissão</u></a>
individual do corretor. Esta informação é auto-explicativa. Um contrato
pode ter várias opções de
<a href="#commission_contract_status"><u>estado</u></a>
.
<hr class="help">

<A NAME="commission_contracts_list"></A>
<h3>Lista de contratos de comissão</h3>
Esta janela mostra uma lista com todos os
<a href="#commission_contract"><u>contratos de comissão</u></a>
para este membro, e seus
<a href="#commission_contract_status"><u>estados</u></a>
.
<span class="admin broker">
<ul>
	<li><img border="0" src="${images}/view.gif" width="16"
		height="16">&nbsp; Clique no ícone lupa para entrar nos
	detalhes.
	<li><img border="0" src="${images}/delete.gif" width="16"
		height="16">&nbsp;e no ícone apagar, para apagar um contrato.
	Este só é visível se você tiver permissões, e se o contrato não estiver
	no estado ativo.
	<li>Você pode <b>adicionar</b> um novo contrato de comissão
	fazendo uma escolha na caixa de opções com o título &quot;Novo
	contrato&quot;, no canto inferior direito desta janela. Esta caixa de
	opções estará vazia caso a administração não tenha configurado nenhuma
	comissão. Neste caso notifique-a sobre isso.
</ul>
</span>
<span class="member"> Você pode clicar no ícone <img border="0"
	src="${images}/view.gif" width="16" height="16">&nbsp;lupa para
ver os detalhes. Isto levará você à uma janela onde você pode aceitar ou
negar o contrato proposto. No caso dele estar no estado
&quot;pendente&quot; </span>
<hr class="help">

<A NAME="commission_contract_edit"></A>
<h3>Editar / modificar contratos de comissão</h3>
A janela mostra os detalhes do
<a href="#commission_contract"><u>contrato de comissão</u></a>
selecionado.
<a href="#commission_contract_status"><u>Clique aqui</u></a>
para uma visão geral dos valores possíveis para o campo estado.
<br>
Uma vez ativo, um contrato de comissão não pode mais ser apagado.
Contudo, você pode
<a href="#commission_contract_status"><u>cancelar</u></a>
o contrato clicando no botão &quot;cancelar&quot;.
<br><br><span class="member">Se o estado for &quot;pendente&quot;,
isto significa que este é um novo contrato, proposto pelo seu corretor.
Neste caso você pode aceitar ou negar o arranjo proposto, clicando em um
dos dois botões abaixo. Um contrato de corretor proposto não sera
aplicado antes que você clique em aceitar.</span>
<hr class="help">

<A NAME="commission_contract_insert"></A>
<h3>Inserir contrato de comissão do corretor</h3>
Esta janela permite que você crie um novo
<a href="#commission_contract"><u>contrato de comissão</u></a>
para o membro selecionado.
<br>
O estado é sempre &quot;pendente&quot;, desde que o contrato só pode ir
para outro estado quando o membro aceitar ou negar ele. - O que é
impossível neste momento pois você esta apenas criando-o.
<br><br>Você deve definir a &quot;data de início&quot;, &quot;data de
término&quot; e o valor para o novo contrato. Para as datas você pode
usar o ícone <img border="0" src="${images}/calendar.gif" width="16"
	height="16">&nbsp;calendário.
<br><br>Uma vez ativo, o contrato de comissão não pode ser apagado.
contudo você pode <a href="#commission_contract_status"><u>cancelar</u></a>
o contrato clicando no botão &quot;cancelar&quot;.
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

