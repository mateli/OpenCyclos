<div style="page-break-after: always;">
<a name="top"></a>
<br><br>Um perfil é um conjunto de informações
associadas a um membro em particular.<br>
<span class="admin"> Existem perfis de administradores e de
membros. Um acesso de administrador é apenas para propósitos de
administração e não possui uma conta, nem outras funções de membro como
contatos, referências, etc, por isso um perfil de administrador é muito
mais simples que o de um membro.<br>
O mais importante é que, junto a cada perfil esta a janela de <a
	href="#actions"><u>ações para...</u></a>, que permite a você iniciar
várias ações a partir do perfil de alguém.
</span>
O &quot;nome de usuário&quot; é o nome com o qual alguém acessa o
sistema, em contraste com o &quot;nome&quot;, que é o nome real de
alguém.
<br><br><i>Onde encontrar.</i><br>
Você pode acessar seu próprio perfil através do &quot;Menu: Pessoal >
perfil&quot;.<br>
<span class="member"> Você pode acessar o perfil de outro membro
de duas maneiras:
<ul>
	<li><b>Através de uma busca:</b> Efetue uma <a
		href="${pagePrefix}user_management#search_member_by_member"><u>procura
	por membros</u></a> através do &quot;Menu: Procurar > Membros&quot;.
	<li><b>Através dos seus contatos:</b> Use a sua <a
		href="${pagePrefix}contacts"><u>lista de contatos</u></a> para ir
	direto ao perifl de um membro, através do &quot;Menu: Pessoal >
	Contatos&quot;.
</ul>
</span> <span class="admin">Você pode acessar o perfil de outro
administrador através do &quot;menu: Usuários & grupos > Gerenciar
administradores&quot;.<br>
Você pode acessar o perfil de um membro através do &quot;Menu: Usuários
& grupos > Gerenciar membros&quot;. </span> <span class="broker">Como um
corretor, você pode acessar o perfil dos seus membros através do
&quot;Menu: Corretagem > membros&quot;. </span> <span class="admin">
<br><br><i>Como ativar.</i><br>
Qualquer um sempre pode ver o seu próprio perfil. Para grupos de membros
e corretores, você deve definir explicitamente quais perfis de outros
grupos podem ser vistos pelos membros deste grupos (primeiro item
abaixo). Existem várias permissões relativas aos perfis:
<ul>
	<li>Para controlar quais outros grupos um membro pode ver, defina
	estas <a href="${pagePrefix}groups#manage_group_permissions_member"><u>permissões</u></a>
	para o grupo. Cada grupo marcado na caixa de seleção do bloco
	&quot;Perfil de membros&quot; será visível para este grupo de membros,
	significando que o membro pode ver o perfil de outro membro deste
	grupo. A mesma permissão pode ser definida para grupos de corretores.
	<li>Para um administrador poder alterar o perfil de um membro,
	você precisa configurar as <a
		href="${pagePrefix}groups#manage_group_permissions_admin_member"><u>permissões</u></a>
	para isso dentro do bloco &quot;membros&quot;, e marcar a opção
	&quot;alterar perfil&quot;.
	<li>Existe também a <a
		href="${pagePrefix}groups#manage_group_permissions_admin_admin"><u>permissão</u></a>
	para alterar o perfil de um administrador. dentro do bloco
	&quot;administradores&quot;, a opção &quot;alterar perfil&quot;.
	<li>Para corretores, você precisa definir as <a
		href="${pagePrefix}groups#manage_group_permissions_broker"><u>permissões</u></a>
	para permitir que eles alterem o perfil dos seus membros. Isto pode ser
	feito no bloco &quot;corretagem&quot;, marcando a opção &quot;alterar
	perfil&quot;.
</ul>
</span>
<hr>

<span class="admin"> <a NAME="admin_profile"></a>
<h3>Perfil de administrador</h3>
Nesta página você pode modificar um <a href="#top"><u>perfil de
administrador</u></a>; tanto o seus perfil quanto o perfil de outro
administrador (este último depende das suas permissões).<br>
Você deve clicar em &quot;alterar&quot; para poder fazer as alterações.
Clique em &quot;enviar&quot; para salvar as alterações que você fez.
<br><br>A maioria dos itens é bastante simples. Se você esta vendo o
perfil de um outro administrador, a página irá exibir o grupo ao qual
este administrador pertence.
<hr class="help">
</span>

<a NAME="member_profile"></a>
<h3>Perfil de membro</h3>
A página de
<a href="#top"><u>perfil</u></a>
mostra informações que
<span class="admin broker">o membro</span>
<span class="member">você</span>
preencheu quando se registrou.
<span class="member">No caso do seu próprio perfil, </span>
você pode adicionar ou modificar informações clicando em
&quot;Alterar&quot; no final da página. Não se esqueça de clicar no
botão &quot;enviar&quot; quando estiver terminado, para salvar as
alterações.
<span class="member">
<br><br>É claro que o perfil de outros membros não pode ser modificado, e
o membro pode ter escolhido ocultar algumas informações para outros
membros. Dessa forma o restante deste texto de ajuda se refere ao seu
próprio perfil, o qual pode ser alterado.
</span>
<br><br>Algumas informações gerais do formulário:
<ul>
	<li><b>*:</b> informações que são obrigatórias, são marcadas com
	um asterisco (*) vermelho do lado direito da caixa de edição.
	<li><b>Ocultar: </b> Alguns campos podem ser ocultados para outros
	membros, marcando a caixa &quot;ocultar&quot; exibida após o campo que
	você quer ocultar.
</ul>
<br><br>Este formulário é bastante simples, e a maioria dos itens são
auto-explicativos. Algumas informações sobre os seguintes campos
específicos, é dada abaixo (observe que os campos mencionados podem não
estar visíveis, dependem da configuração que a sua organização
escolheu):
<ul>
	<span class="admin">
	<li><b>Último acesso:</b> Este campo exibe &quot;esta
	conectado&quot; no caso do membro estar usando o cyclos neste momento.
	Neste caso você pode forçar a desconexão do membro através de um botão
	na janela abaixo.
	</span>
	<li><b>Aniversário:</b> Você pode usar o botão &quot;seletor de
	data&quot; (<img border="0" src="${images}/calendar.gif" width="16"
		height="16">&nbsp;) para obter um calendário, que pode ajuda-lo
	a preencher a data.
	<li><b>Corretor:</b> Este campo só é visível quando a sua
	organização utiliza o sistema de <a href="${pagePrefix}brokering"><u>corretagem</u></a>.
	O campo não é alterável, você pode usar o link &quot;Abrir perfil&quot;
	ao lado do campo para ir ao perfil do seu corretor.
	<li><b>Data de registro:</b> Se um membro estiver em um grupo de
	membros &quot;pendentes&quot; (grupo sem uma conta relacionada), este
	campo mostrará a data que o membro foi registrado (tanto por um
	administrador, corretor ou pelo próprio membro através da página de
	registro público).<br>
	Esta data não sera exibida se o membro for parte de um grupo ativo (mas
	a data de ativação é mostrada na página de relatórios de membro).
	<li><b>Adicionar imagem:</b> Marque esta opção e você poderá <a
		href="#picture"><u>adicionar uma imagem</u></a> ao seu perfil.
</ul>
<hr class="help">

<a name="picture"></a>
<h3>Imagens no seu perfil ou anúncio</h3>
Cyclos permite que você envie imagens para o seu
<a href="#top"><u>perfil</u></a>
ou anúncio. Siga estes passos se você deseja que outros membros possam
ver sua imagem:
<ol>
	<li>Clique em &quot;alterar&quot; para poder fazer alterações no
	formulário
	<li>Marque a caixa &quot;adicionar imagem&quot;, campos extras
	serão visíveis.
	<li>Clique no botão &quot;Arquivo&quot; para escolher uma foto em
	seu computador, que você queira enviar.
	<li>Clicando no botão &quot;Arquivo&quot; para escolher o arquivo
	em seu computador, escolha o arquivo e após clique em
	&quot;abrir&quot;. Observe que existe um tamanho máximo para o arquivo
	(que será exibido na janela) e apenas os formatos JPG, GIF e PNG são
	suportados.
	<li>Após isso, o nome do arquivo é colocado na caixa
	&quot;Imagem&quot;.
	<li>Quando você clicar em &quot;enviar&quot; o arquivo será
	enviado.
</ol>
Você pode enviar mais de uma imagem. O número de imagens que é possível
enviar é definido pela administração.
<br>
Você pode navegar pelas imagens usando os links de navegação abaixo da
imagem &quot;< 1/2/3 >&quot;. Você pode alterar a ordem de exibição, e
pode colocar um texto que será exibido abaixo da imagem, clicando no
link &quot;Detalhes&quot; abaixo da imagem.
<br>
Você pode remover uma imagem, vendo a imagem e clicando no link
&quot;remover&quot; abaixo da imagem. Outros membros poderão ver suas
imagens da mesmo forma (mas não poderão remove-las).
<hr class="help">

<a name="actions"></a>
<h2>Ações para...</h2>
Abaixo de cada
<a href="#top"><u>perfil</u></a>
existe normalmente uma janela com botões para todas as ações que você
pode efetuar em relação ao membro. Estas ações variam, desde fazer
pagamentos para
<span class="admin broker">ou como</span>
o membro, dar referências ao membro, enviar mensagens para este membro,
etc. etc.

A disponibilidade das ações pode variar, dependem das configurações e
regras da sua organização. Também algumas das ações listadas nesta
janela de ajuda, pode não estar disponível para você quando você não
tiver as permissões necessárias.
<br><br>As janelas de ajuda dão um resumo do máximo de ações disponíveis.
Como dito, é muito provável que você não verá todas essas ações,
dependem das suas configurações e permissões. Para uma explicação mais
detalhada sobre a ação, você pode consultar a ajuda da janela que se
abrirá após você clicar em um botão específico. Você também pode seguir
os links, que lhe darão mais algumas explicações gerais sobre o assunto.
(Os itens são explicados da esquerda para direita e linha por linha de
cima para baixo.)
<br><br>Ações e perfis para operadores são explicados no arquivo de ajuda
de operadores.
<hr>

<a name="actions_for_member_by_operator"></a>
<a name="actions_for_member"></a>
<span class="member">
<h3>Ações para membror</h3>
Nesta janela, você pode efetuar várias ações em relação ao este membro.
<UL>
	<li><b>Fazer pagamento</b>
	<lI><b>Ver & dar referências:</b> Veja as experiências de outras
	pessoas com este membro, e também atribua a sua <a
		href="${pagePrefix}references"><u>referência</u></a> sobre este
	membro.
	<li><b>Qualificação de transações:</b> Permite que você de uma <a
		href="${pagePrefix}transaction_feedback"><u> qualificação</u></a> para
	uma transação.
	<lI><b>Enviar <a href="${pagePrefix}invoices"><u>faturas
	eletrônicas</u></a></b>
	<li><b>Ver <a href="${pagePrefix}advertisements"><u>anúncios</u></a></b>
	<li><b>Enviar e-mail</b>
	<li><b>Enviar mensagem</b> com o <a href="${pagePrefix}messages"><u>sistema
	interno de mensagem</u></a> do cyclos.
	<li><b>Ver relatórios</b> O levará até o <a
		href="${pagePrefix}reports#member_activities"><u>relatório</u></a> com
	informações sobre a atividade do membro.
	<LI><b>Adicionar à <a href="${pagePrefix}contacts"><u>lista
	de contato</u></a></b>
</UL>
<hr class="help">
</span>

<span class="broker"> <a name="actions_for_member_by_broker"></a>
<h3>Ações de corretor para membro</h3>
Esta janela é o ponto de partida para todas as ações de <a
	href="${pagePrefix}brokering"><u>corretor</u></a> em relação a este
membro. Assim, esta deve ser a página principal para as suas tartefas
como corretor.<br>
Você pode querer ler primeiro as <a href="#actions"><u>notas
gerais</u></a> nesta janela.
<UL>
	<LI><b>Gerenciar anúncios:</b> Adicionar, remover ou editar os <a
		href="${pagePrefix}advertisements"><u>anúncios</u></a> para esse
	membro.
	<li><b>Informação de conta:</b> Leva você ao histórico de conta
	deste membro, exibindo o balanço e o histórico de transações.
	<li><b>Ver <a href="${pagePrefix}payments#scheduled"><u>pagamentos
	agendados</u></a></b>
	<li><b><a href="${pagePrefix}payments#authorized"><u>Pagamentos
	autorizados</u></a></b> Isto o levará aos pagamentos que o membro deve autorizar
	como recebedor do pagamento. Isto é aplicável para uma configuração
	onde o recebedor do pagamento (o membro) deve autorizar o pagamento
	antes que ele seja adicionado ao balanço de sua conta. Como corretor,
	você pode atuar como se você fosse este membro, e autorizar este
	pagamento, resultando no adicionamento dele ao balanço da conta do
	membro. Isto só é visível caso seja possível o seu uso.
	<li><b>Ver <a href="${pagePrefix}payments#authorized"><u>autorizações</u></a>
	de pagamentos:</b> Aqui você vai até a visão geral dos pagamentos, os quais
	você, como corretor, deve autorizar para o membro, para que eles sejam
	pagos da conta do membro. Isto é aplicável em situações onde o membro
	não pode fazer certos tipos de pagamentos (ou todos pagamentos), por
	precisar primeiro da autorização de um administrador ou corretor.
	<li><b>Pagamento de membro para membro:</b> Faça um pagamento para
	outro membro como se você fosse o seu membro.
	<li><b>Auto-pagamento:</b> Faça uma transferência entre contas
	diferentes deste mesmo membro. Isto só é possível caso o membro possua
	mais de uma conta.
	<li><b>Pagamento de membro para sistema:</b> Faça um pagamento
	para a organização/sistema como se você fosse o seu membro.
	<li><b>Gerenciar referências:</b> Aqui você vê como os outros
	membros avaliam este membro, e você pode quais tipos de experiência
	este membro ja teve com outros membros. As <a
		href="${pagePrefix}references"><u> referências</u></a> dadas e
	recebidas são exibidas. Um corretor pode alterar ou apagar uma
	referência dada pelo membro (caso tenha permissões).
	<li><b>Ver empréstimos:</b> A visão geral dos <a
		href="${pagePrefix}loans"><u>empréstimos</u></a> deste membro.
	<li><b>Ver faturas:</b> Ver todas as <a
		href="${pagePrefix}invoices"><u>faturas eletrônicas</u></a> que este
	membro enviou e recebeu.
	<li><b>Fatura de membro para membro:</b> Envie uma fatura
	eletrônica para outro membro como se fosse o seu membro.
	<li><b>Fatura de membro para sistema:</b> Envie uma fatura
	eletrônica para o sistema como se fosse o seu membro.
	<li><b>Grupos de empréstimos:</b> Exibe do <a
		href="${pagePrefix}loan_groups"><u>grupo de empréstimo</u></a> do qual
	este membro faz parte.
	<li><b>Gerenciar senhas:</b> Permite que você reinicie a <a
		href="${pagePrefix}passwords"><u>senha</u></a> do membro.
	<li><b>Acesso externo:</b> Permite que você gerencie através de
	quais <a href="${pagePrefix}settings#channels"><u>canais</u></a> o
	membro pode acessar o cyclos. Isto também lhe da a possibilidade de
	alterar o <a href="${pagePrefix}passwords#pin"><u>número pin</u></a> do
	membro (uma senha numérica utilizada para acesso em alguns canais como
	por exemplo os pagamentos em lojas virtuais).
	<LI><b>Observações:</b> Qualquer comentário sobre este membro pode
	ser colocado aqui. Estas observações são significativas para você ou
	para outro administrador e corretor. Nenhum membro pode ver essas
	observações. Se o texto observações ao lado do botão de enviar estiver
	em vermelho, significa que existe uma observação para este membro. Caso
	não exista nenhuma observação, o texto ao lado do botão vai estar na
	cor padrão da aplicação.
	<li><b>...:</b> Qualquer outro tipo de <a
		href="${pagePrefix}member_records"><u>registro de membro</u></a> que
	você definiu também será listado aqui na forma de um botão.
	<LI><b>Documentos de membro:</b> Este botão da acesso à página com
	os <a href="${pagePrefix}documents"><u>documentos</u></a> do membro que
	podem ser impressos.
	<li><b>Contratos de comissão:</b> Da acesso a página onde você
	pode revisar ou configurar <a
		href="${pagePrefix}brokering#commission_contract"><u>contratos</u></a>
	sobre os serviços de corretagem entre você e o seu membro.
	<li><b>Registros de SMS:</b> Da acesso aos <a
		href="${pagePrefix}reports#sms_log"><u>Registros de SMS</u></a>, onde
	mensagens SMS enviadas a este membro são guardadas. O sistema pode ser
	configurado para enviar mensagens SMS em várias ocasiões.
</UL>
<hr class="help">
</span>

<span class="admin"> <a name="actions_for_member_by_admin"></a>
<h3>Ações para membros (de administradores)</h3>
Esta janela é o ponto de partida para todas ações administrativas em
relação ao membro. Assim, esta deve ser a página principal para as suas
tarefas como um <a href="${pagePrefix}groups#account_admins"><u>administrador
de contas</u></a>.<br>
Você pode querer ler primeiro as <a href="#actions"><u>notas
gerais</u></a> nesta janela.<br>

Ações de administradores são organizadas em seções. Cada seção é
colocada em uma seção diferente da ajuda, para manter os blocos de
textos gerenciáveis e mais fáceis de ler.
<br><br>As seguintes seções existem. Clique nos links para ir à descrição
dos botões de cada seção.
<ul>
	<li><b><a href="#access_actions"><u>Seção de acesso</u></a>:</b>
	tudo sobre o controle de acesso do membro ao sistema.
	<li><b><a href="#ads_actions"><u>Seção de anúncios</u></a>:</b>
	Sobre o gerenciamento de anúncios do membro.
	<li><b><a href="#accounts_actions"><u>Seção de contas</u></a>:</b>
	Tudo sobre as contas do membro, incluindo pagamentos e faturas
	eletrônicas.
	<li><b><a href="#member_info_actions"><u>Seção de
	informações do membro</u></a>:</b> Informações sobre o membro, sua atividade, etc.

	
	<li><b><a href="#brokering_actions"><u>Seção de
	corretagem</u></a>:</b> Ações relativas ao sistema de <a
		href="${pagePrefix}brokering"><u>corretagem</u></a> do cyclos.
	<li><b><a href="#loans_actions"><u>Seção de
	empréstimos</u></a>:</b> Ações relacionadas aos <a href="${pagePrefix}loans"><u>empréstimos</u></a>
	deste membro.
</ul>
<hr class="help">
</span>

<span class="admin"> <a name="access_actions"></a>
<h3>Ações para membro: Acesso</h3>
(Esta visão geral é para da janela de &quot;<a
	href="#actions_for_member_by_admin"><u>ações para membro</u></a>&quot;.)
<ul>
	<li><b>Permitir acesso do usuário agora:</b> Este só é visível se
	o acesso do membro estiver bloqueado, por exemplo por tentativas
	seguidas de acesso com uma senha errada. Tal ação pode bloquear o
	acesso do membro por um determinado período de time, de acordo com as <a
		href="${pagePrefix}settings"><u>configurações</u></a>. Se o tal membro
	se comunicar a administração relatando tal bloqueio, você pode
	desbloquear imediatamente o acesso do membro através deste botão. Após
	clicar neste botão o membro estará apto a tentar o acesso.<br>
	<b>Note:</b> É claro que isso não reinicia a <a
		href="${pagePrefix}passwords"><u>senha</u></a> de acesso do membro. Se
	o membro não se recordar da senha, você pode reinicia-la através do
	botão &quot;gerenciar senhas&quot; na mesma seção desta janela.
	<li><b>Desconectar usuário conectado:</b> Caso o membro esteja
	conectado neste momento, você pode forçar a desconexão do membro
	clicando neste botão. Este botão é visível <b>apenas</b> se o membro
	estiver conectado neste exato momento.<br>
	Uma razão para desconectar o membro, pode ser em um caso de emergência,
	como uma suspeita de abuso ou o hackeamento de uma conta. É recomendado
	nestes casos a desativação temporária deste membro após ter forçado a
	sua desconexão (você pode fazer isso através do botão &quot;Alterar
	grupo de permissões&quot; na mesma seção desta página).
	<li><b>Gerenciar senhas:</b> Permite que você reinicie a <a
		href="${pagePrefix}passwords"><u>senha</u></a> do membro.
	<li><b>Acesso externo:</b> Permite que você gerencie através de
	quais <a href="${pagePrefix}settings#channels"><u>canais</u></a> o
	membro pode acessar o cyclos. Isto também lhe da a possibilidade de
	alterar o <a href="${pagePrefix}passwords#pin"><u>número pin</u></a> do
	membro (uma senha numérica utilizada para acesso em alguns canais como
	pro exemplo os pagamentos em lojas virtuais).
	<li><b>Alterar grupo de permissão:</b> Permite que você altere o <a
		href="${pagePrefix}groups"><u>grupo</u></a> ao qual o membro pertence.
	Todo membro pertence a um grupo. Esta página permitirá alterar o grupo
	ao qual o membro pertence, e enviar um email de ativação. A maneira
	básica de controlar o acesso ao cyclos é através de grupos. Se você
	deseja remover um membro do sistema/organização, você deve usar este
	botão, e mover o membro para o grupo de &quot;membros removidos&quot;.
	<li><b>Registros de SMS:</b> Da a você acesso aos <a
		href="${pagePrefix}reports#sms_log"><u>Registros de SMS</u></a>, onde
	as mensagens SMS enviadas para este membro são gravadas. O sistema pode
	ser configurado para enviar mensagens SMS em diversas ocasiões.
	<li><b>Gerenciar cartões:</b> Da a você acesso ao <a
		href="${pagePrefix}cards#search_card_results"><u>
	gerenciamento de cartões</u></a>, onde será listado todos os carões de membros.
	
</ul>
<hr class="help">
</span>

<span class="admin"> <a name="ads_actions"></a>
<h3>Ações para membro: Anúncios</h3>
(Esta visão geral é para da janela de &quot;<a
	href="#actions_for_member_by_admin"><u>ações para membro</u></a>&quot;.)
<ul>
	<LI><b>Gerenciar anúncios:</b> Adicionar, apagar ou editar os <a
		href="${pagePrefix}advertisements"><u>anúncios</u></a> deste membro.
</ul>
<hr class="help">
</span>

<span class="admin"> <a name="accounts_actions"></a>
<h3>Ações para membro: Contas</h3>
(Esta visão geral é para da janela de &quot;<a
	href="#actions_for_member_by_admin"><u>ações para membro</u></a>&quot;.)

<ul>
	<li><b>Informações de conta:</b> Leva você ao histórico de conta
	deste membro, exibindo o balanço e o histórico de transações.
	<li><b>Ver <a href="${pagePrefix}payments#scheduled"><u>pagamentos
	agendados</u></a></b> li><b><a href="${pagePrefix}payments#authorized"><u>Pagamentos
	autorizados</u></a></b> Isto o levará aos pagamentos que o membro deve autorizar
	como recebedor do pagamento. Isto é aplicável para uma configuração
	onde o recebedor do pagamento (o membro) deve autorizar o pagamento
	antes que ele seja adicionado ao balanço de sua conta. Como
	administrador, você pode atuar como se você fosse este membro, e
	autorizar este pagamento, resultando no adicionamento dele ao balanço
	da conta do membro. Isto só é visível caso seja possível o seu uso.<br>
	Isto corresponde com as permissões de pagamentos: &quot;<a
		href="${pagePrefix}groups#manage_group_permissions_member"><u>
	Autorizar quando recebedor do pagamento</u></a>&quot;.
	<li><b>Ver <a href="${pagePrefix}payments#authorized"><u>autorizações</u></a>
	de pagamentos:</b> Aqui você vai até a visão geral dos pagamentos, os quais
	você, como administrador, deve autorizar para o membro, para que eles
	sejam pagos da conta do membro. Isto é aplicável em situações onde o
	membro não pode fazer certos tipos de pagamentos (ou todos pagamentos),
	por precisar primeiro da autorização de um administrador ou corretor.<br>
	Isto corresponde com as permissõess: &quot;<a
		href="${pagePrefix}groups#manage_group_permissions_member"><u>Contas
	> ver pagamentos autorizados</u></a>&quot;.
	<li><b>Pagamento de sistema para membro:</b> Pagar o membro a
	partir de uma conta de sistema.
	<li><b>Pagamento de membro para membro:</b> Efetue um pagamento
	para outro membro, como se você fosse o membro.
	<li><b>Pagamento de membro para sistema:</b> Faça um pagamento
	para a organização/sistema como se você fosse o membro.
	<li><b>Auto-pagamento:</b> Faça uma transferência entre contas
	diferentes deste mesmo membro. Isto só é possível caso o membro possua
	mais de uma conta.
	<li><b>Ver faturas:</b> Ver todas as <a
		href="${pagePrefix}invoices"><u>faturas eletrônicas</u></a> que este
	membro enviou e recebeu.
	<li><b>Fatura de sistema para membro:</b> Envie a este membro, uma
	fatura eletrônica a partir de uma conta de sistema. Isto significa que
	a organização está enviando ao membro uma conta, e o membro terá
	paga-la.
	<li><b>Fatura de membro para membro:</b> Envie uma fatura
	eletrônica para outro membro como se fosse o membro.
	<li><b>Fatura de membro para sistema:</b> Envie uma fatura
	eletrônica para o sistema como se fosse o membro.
	<li><b>Limite de crédito:</b> Aqui você pode definir o limite de
	crédito individual para este membro. Observe que este é o limite de
	crédito apenas para este membro em particular, e não o limite de
	crédito do grupo, que é definido a nível de grupo.
</ul>
<hr class="help">
</span>

<span class="admin"> <a name="member_info_actions"></a>
<h3>Ações para membro: Informações de membro</h3>
(Esta visão geral é para da janela de &quot;<a
	href="#actions_for_member_by_admin"><u>ações para membro</u></a>&quot;.)
<ul>
	<li><b>Ver relatórios</b> O levará até o <a
		href="${pagePrefix}reports#member_activities"><u>relatório</u></a> com
	informações sobre a atividade do membro.
	<LI><b>Observações:</b> Qualquer comentário sobre este membro pode
	ser colocado aqui. Estas observações são significativas para você ou
	para outro administrador e corretor. Nenhum membro pode ver essas
	observações. Se o texto observações ao lado do botão de enviar estiver
	em vermelho, significa que existe uma observação para este membro. Caso
	não exista nenhuma observação, o texto ao lado do botão vai estar na
	cor padrão da aplicação.
	<li><b>...:</b> Qualquer outro tipo de <a
		href="${pagePrefix}member_records"><u>registro de membro</u></a> que
	você definiu também será listado aqui na forma de um botão.
	<li><b>Gerenciar referências:</b> Aqui você vê como os outros
	membros avaliam este membro, e você pode quais tipos de experiência
	este membro ja teve com outros membros. As <a
		href="${pagePrefix}references"><u> referências</u></a> dadas e
	recebidas são exibidas. Um administrador pode alterar ou apagar uma
	referência dada pelo membro (caso tenha permissões).
	<li><b>Qualificação de transações:</b> Permite que você de uma <a
		href="${pagePrefix}transaction_feedback"><u> qualificação</u></a> para
	uma transação.
	<li><b>Enviar mensagem</b> com o <a href="${pagePrefix}messages"><u>sistema
	interno de mensagem</u></a> do cyclos.
	<li><b>Enviar e-mail</b>
	<LI><b>Documentos de membro:</b> Este botão da acesso à página com
	os <a href="${pagePrefix}documents"><u>documentos</u></a> do membro que
	podem ser impressos.
	<li><b>Contratos de comissão:</b> Da acesso a página onde você
	pode revisar ou configurar <a
		href="${pagePrefix}brokering#commission_contract"><u>contratos</u></a>
	sobre os serviços de corretagem entre você e o seu membro.
</ul>
<hr class="help">
</span>

<span class="admin"> <a name="brokering_actions"></a>
<h3>Ações para membro: Corretagem</h3>
(Esta visão geral é para da janela de &quot;<a
	href="#actions_for_member_by_admin"><u>ações para membro</u></a>&quot;.)
<ul>
	<li><b>Selecionar corretor:</b> Nesta página você pode alterar o <a
		href="${pagePrefix}brokering"><u>corretor</u></a> relacionado a este
	membro.
	<li><b>Lista de membros (como corretor):</b> Este estará visível
	apenas se o membro que você esta visualizando o perfil for um <a
		href="${pagePrefix}brokering"><u> corretor</u></a>. Neste caso, este
	mostrará uma lista de membros que estão relacionados com este corretor,
	e da a você a opção de adicionar um novo membro para este corretor.
	<li><b>Configurações de comissões:</b> Este estará visível apenas
	se o membro que você esta visualizando o perfil for um corretor. Aqui
	você pode ver os <a href="${pagePrefix}brokering#commission_contract"><u>contratos
	de comissões</u></a> dos quais este corretor faz parte, e você pode procurar
	por contratos de comissões específicos entre o corretor e seus membros.

	
</ul>
<hr class="help">
</span>

<span class="admin"> <a name="loans_actions"></a>
<h3>Ações para membro: Empréstimos</h3>
(Esta visão geral é para da janela de &quot;<a
	href="#actions_for_member_by_admin"><u>ações para membro</u></a>&quot;.)
<ul>
	<li><b>Ver empréstimos:</b> leva você à visão geral dos <a
		href="${pagePrefix}loans"><u>empréstimos</u></a> deste membro.
	<li><b>Conceder empréstimo:</b> Aqui você da um empréstimo para o
	membro.
	<li><b>Grupos de empréstimos:</b> Exibe do <a
		href="${pagePrefix}loan_groups"><u>grupo de empréstimo</u></a> do qual
	este membro faz parte.
</ul>
<hr class="help">
</span>

<span class="admin"> <a name="actions_for_admin"></a>
<h3>Ações para administrador</h3>
Aqui você pode fazer várias açÕes em relação a este administrador. Você
pode querer ler primeiro as <a href="#actions"><u>notas gerais</u></a>
nesta janela.
<ul>
	<li><b>Enviar e-mail</b>
	<li><b>Observações:</b> Qualquer observação sobre este
	administrador pode ser colocada aqui. A observação é um registro de
	membro que vem por padrão com o banco de dados. Qualquer outro tipo de
	<a href="${pagePrefix}member_records"><u>registro de membro</u></a> que
	você definiu para administradores serão listados aqui também, na forma
	de um botão. Como você pode atribuir registros de membros a <a
		href="${pagePrefix}groups"> <u>grupos</u></a> de permissões
	específicos, é possível que exista registros de membros definidos para
	grupos de administradores.
	<li><b>Alterar grupo de permissão:</b> Permite que você altere o
	grupo ao qual este administrador pertence. Todo administrador pertence
	a um grupo. Esta página permitirá que você altere o grupo do
	administrador. A maneira básica de controlar o acesso ao cyclos é
	através de grupos. Se você deseja remover um administrador do
	sistema/organização, você deve usar este botão, e mover o administrador
	para o grupo de &quot;administradores removidos&quot;.
	<li><b>Permitir acesso do usuário agora:</b> Este só é visível se
	o acesso do administrador estiver bloqueado, por exemplo por tentativas
	seguidas de acesso com uma senha errada. Tal ação pode bloquear o
	acesso do administrador por um determinado período de time, de acordo
	com as <a href="${pagePrefix}settings"><u>configurações</u></a>. Se o
	tal administrador se comunicar a administração relatando tal bloqueio,
	você pode desbloquear imediatamente o acesso do administrador através
	deste botão. Após clicar neste botão o administrador estará apto a
	tentar o acesso.<br>
	<b>Note:</b> É claro que isso não reinicia a <a
		href="${pagePrefix}passwords"><u>senha</u></a> de acesso do
	administrador. Se o administrador não se recordar da senha, você pode
	reinicia-la através do botão &quot;gerenciar senhas&quot; na mesma
	seção desta janela.
	<li><b>Desconectar usuário conectado:</b> Caso o administrador
	esteja conectado neste momento, você pode forçar a desconexão dele
	clicando neste botão. Este botão é visível <b>apenas</b> se o
	administrador estiver conectado neste exato momento.<br>
	Uma razão para desconectar o administrador, pode ser em um caso de
	emergência, como uma suspeita de abuso ou o hackeamento de uma conta. É
	recomendado nestes casos a desativação temporária deste administrador
	após ter forçado a sua desconexão (você pode fazer isso através do
	botão &quot;Alterar grupo de permissões&quot; na mesma seção desta
	página).
	<li><b>Gerenciar senhas:</b> Permite que você reinicie a <a
		href="${pagePrefix}passwords"><u>senha</u></a> do administrador.
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