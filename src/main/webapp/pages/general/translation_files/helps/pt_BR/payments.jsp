<div style="page-break-after: always;">
<h2>Pagamentos</h2>
Um membro pode fazer pagamentos para um outro membro ou para uma conta
de sistema (comunidade, organização, etc.). Também, caso disponível, um
membro pode transferir unidades entre suas próprias contas, caso cada
membro possua mais de uma conta com a mesma moeda. Os pagamentos também
podem ser agendados para datas futuras. Todos pagamentos possuem um
botão de imprimir, para que seja impresso o comprovante da transação.

<span class="admin">Os pagamentos também podem ser desfeitos (<a
	href="#charge_back"><u>estornados</u></a>) em certas condições.</span>

<br><br><i>Onde encontrar.</i><br>
<br><br><span class="member">Pagamentos no acesso principal pela
web podem ser iniciados a partir de três localizações. A partir do menu:
</b>Este possuem dois tipos:
<ul>
	<li><b>Pagamentos para membros:</b> &quot;Menu: Conta > Pagamento
	para membro&quot;
	<li><b>Pagamentos para sistema:</b> &quot;Menu: Conta > Pagamento
	para sistema&quot;
	<li><b>A partir do perfil:</b> Pagamentos para outros membros
	também podem ser feitos a partir da página de <a
		href="${pagePrefix}profiles"><u>perfil</u></a> deste membro.
</ul>
</span> <span class="broker"> Um corretor pode efetuar pagamentos para
seus membros através do <a href="${pagePrefix}profiles"><u>
perfil</u></a> destes membros. Isto inclui pagamentos para outros membros, e
para contas de sistemas.
<br><br>O corretor também pode <a href="#authorized"><u>autorizar</u></a>
pagamentos dos membros; Isto pode ser feito através do &quot;Menu:
Corretagem > Aguardando autorização&quot; e &quot;Menu: Corretagem >
Histórico de autorizações&quot;.<br>
</span> <span class="admin"> Pagamentos podem ser feitos a partir de
várias localizações. A disponibilidade das opções mencionadas depende é
claro da configuração da sua organização e das permissões dos diversos
grupos:
<ul>
	<li><b>Perfil:</b> A partir do <a href="${pagePrefix}profiles"><u>perfil</u></a>
	de um membro, você pode efetuar pagamentos para outros membros, bem
	como para contas do sistema.
	<li><b>Pagamentos de sistema para sistema:</b> Pode ser feito a
	partir do &quot;Menu: Contas > Pagamento para sistema&quot;. Estas são
	transferências de uma conta de sistema para uma outra conta de sistema.



	
	<li><b>Pagamentos de sistema para membro:</b> Pode ser feito a
	partir do &quot;Menu: Contas > Pagamento de membro&quot;. Estes são
	pagamentos de uma conta de sistema para um membro.
</ul>
Também, vários tipos de pagamentos especiais existem, e estes
normalmente são acessados através do menu:
<ul>
	<li><b><a href="#authorized"><u>Autorizações</u></a>:</b> podem
	ser acessados através do &quot;Menu: Contas > Aguardando
	autorização&quot; e &quot;Menu: Contas > Histórico de
	autorizações&quot;.
	<li><b><a href="#scheduled"><u>Pagamentos agendados</u></a>:</b>
	podem ser acessados através do &quot;Menu: Contas > Pagamentos
	agendados&quot;.
	<li><b>Pagamento de empréstimos:</b> podem ser acessados através
	do &quot;Menu: Contas > Pagamentos de empréstimos&quot;; este assunto é
	abordado no <a href="${pagePrefix}loans"><u>arquivo de ajuda de
	empréstimos</u></a>.
</ul>
<br>
</span> observe que, além de fazer pagamentos diretos, você também pode pagar
respondendo a uma <a href="${pagePrefix}invoices"><u>fatura
eletrônica</u></a>. <span class="admin">
<br><br><i>Como ativar.</i><br>
O ponto mais importante, é que para cada pagamento deve existir um tipo
de pagamento. Se você não definir um tipo de pagamento para um certo
pagamento, então o pagamento não existirá. Você pode gerenciar tipos de
transações, gerenciando a conta através da qual o pagamento é feito.
Para fazer isso, você deve ir ao &quot;menu: Contas > Gerenciar
contas&quot;, e escolher o tipo de conta da qual sairá o pagamento. Você
obterá uma <a
	href="${pagePrefix}account_management#transaction_type_search"><u>
visão geral</u></a> de todos os tipos de transações disponíveis para esta conta,
permitindo também que você adicione um novo tipo (caso você tenha
permissões).<br>
Assim que existir o tipo de pagamento, você ainda precisará configurar
as permissões para usa-lo em vários grupos.
<ul>
	<li>Administradores podem ter <a
		href="${pagePrefix}groups#manage_group_permissions_admin_system"><u>permissões</u></a>
	para efetuar pagamentos de sistema: bloco &quot;pagamentos de
	sistemas&quot; possui várias configurações.
	<li>Administradores também podem ter <a
		href="${pagePrefix}groups#manage_group_permissions_admin_member"><u>permissões</u></a>
	para efetuar pagamentos para membros: bloco &quot;pagamentos de
	membro&quot; possui várias configurações.
	<li>Para membros, você também necessitará configurar as <a
		href="${pagePrefix}groups#manage_group_permissions_member"><u>permissões</u></a>
	para efetuarem pagamentos; isto é feito com várias configurações no
	bloco &quot;pagamentos&quot;. Este bloco também esta disponível para
	corretores.
	<li>Corretores podem ter <a
		href="${pagePrefix}groups#manage_group_permissions_broker"><u>permissões</u></a>
	para efetuar pagamentos para membros, no bloco &quot;Pagamentos de
	membro&quot;.
	<li>Para pagamentos autorizados e pagamentos agendados, existe
	para cada grupo (membros, corretores, administradores) uma configuração
	no bloco &quot;Contas&quot; que permite a este grupo ver pagamentos
	autorizados e pagamentos agendados.
</ul>

</span>
<hr>

<a name="payments"></a>
<br><br>
<h3>Fazendo pagamentos</h3>
Os formulários para fazer pagamentos no cyclos também possuem alguns
elementos comuns. Nesta introdução rápida, nós trataremos os elementos
comuns que podem aparecer na sua janela de pagamentos. Na maioria dos
casos, você apenas preenche o valor e a descrição, e clica em
&quot;enviar&quot;. Em outros casos você terá que preencher alguns
outros campos também.
<br>
Observe que é importante escolher todos os campos e opções na ordem
correta, que é de cima para baixo. Por exemplo, escolher uma moeda antes
de você ter informado os campos anteriores, solicitando o nome do
membro, não irá funcionar.
<br><br>
<ul>
	<li><b>Nome (de acesso):</b> Se o pagamento for para um outro
	membro, e isto ainda não é claro para o contexto, você terá que
	preencher ou o nome de usuário ou o nome do membro ao qual você quer
	pagar. Os campos são de auto-preenchimento: na maioria dos casos
	informar apenas as primeiras letras é o suficiente.
	<li><b>Valor:</b> Apenas informe o valor.
	<li><b>Moeda:</b> Este campo aparece apenas após o campo de valor,
	ele só é visível caso mais de uma moeda seja possível. Isto depende da
	configuração local da sua organização.
	<li><b>Tipo de transação:</b> Pode ser que mais de um tipo de
	transação seja possível. Neste caso você terá que escolher o tipo de
	transação na caixa de seleção.
	<li class="admin"><b>Pagar no passado:</b> Um administrador pode
	escolher efetuar um pagamento no passado. Isto normalmente é feito por
	questões de contabilidade e deve ser usado apenas em casos raros. Este
	deve ser ativado nas <a
		href="${pagePrefix}groups#manage_group_permissions_admin_member"><u>
	permissões de membro para administradores</u></a>.
	<li><b>Agendamento:</b> Caso pagamentos agendados sejam permitidos
	para este tipo de pagamento, você pode escolher ter o pagamento
	processado (automaticamente) em uma data futura, ou em múltiplas
	parcelas em datas recorrentes (para mais informações veja <a
		href="#pay_scheduled"><u>Pagamentos agendados</u></a>).
</ul>
<br><br>Após submeter o pagamento, será solicitado a você uma
confirmação. As unidades são transferidas diretamente da sua conta para
a conta da outra parte. A transação será exibida no histórico de conta
de ambas as parte, para o pagante em vermelho com um sinal de "-" e para
o recebedor em azul com um sinal de "+".
<hr class="help">

<a name="transaction_confirm"></a>
<h3>Confirmação de transação</h3>
Nesta janela você é solicitado à confirmar o pagamento que você
submeteu. Confira todas as informações, e caso esteja tudo correto
clique em &quot;enviar&quot;.
<br>
Caso exista algum erro, você pode usar o botão de &quot;voltar&quot;.
<hr class="help">

<A NAME="to_member"></A>
<h3>Pagamento para membro</h3>
Esta janela permite a você fazer um pagamento para um membro. Na maioria
dos casos você pode apenas preencher a descrição, o valor e o nome do
membro, caso este já não seja conhecido, e clicar em
&quot;Enviart&quot;.
<br>
<a href="#payments"><u>Clique aqui</u></a>
para uma descrição geral da janela de pagamento.
<hr class="help">

<A NAME="to_system"></A>
<h3 class="admin">Transferência entre contas de sistema</h3>
<h3 class="member">Pagamento para sistema</h3>
<span class="admin"> Nesta janela você pode fazer um pagamento
entre contas de sistema.
<br><br>
</span>
<span class="member"> Nesta janela você pode fazer um pagamento
para uma das contas da organização ou &quot;contas de sistema&quot;.</span>
<a href="#payments"><u>Clique aqui</u></a>
para uma descrição geral da janela de pagamento.
<hr class="help">

<A NAME="as_member_to_system"></A>
<span class="broker admin">
<h3>Pagamento como membro para sistema</h3>
Nesta janela você pode fazer um pagamento como o membro, a partir da
conta do membro para a conta de sistema.
<br><br><a href="#payments"><u>Clique aqui</u></a> para uma descrição
geral da janela de pagamento.
<hr class="help">
</span>

<span class="broker admin"> <A NAME="as_member_to_member"></A>
<h3>Pagamento como membro para membro</h3>
Nesta janela você pode fazer um pagamento como o membro, a partir da
conta do membro para a conta de outro membro.
<br><br><a href="#payments"><u>Clique aqui</u></a> para uma descrição
geral da janela de pagamento.
<hr class="help">
</span>

<A NAME="member_self_payments"></A>
<h3 class='member'>Transferência entre minhas contas</h3>
<h3 class='admin'>Transferências entre contas do membro (pelo
administrador)</h3>
<span class="admin"> É possível a um administrador (com
permissões) efetuar um auto-pagamento como se ele, o administrador,
fosse o membro.</span>
<br>
Um auto-pagamento permite fazer uma transferência a partir de uma conta
para uma outra conta do mesmo membro. Um auto-pagamento funciona da
mesma maneira que um pagamento para outro membro.
<br>
<br><br><a href="#payments"><u>Clique aqui</u></a> para uma descrição
geral da janela de pagamento.
<hr>

<a name="scheduled"></a>
<h2>Pagamentos agendados</h2>
A função de pagamentos agendados permitem ao membro criar pagamentos
agendados para uma data futura (parcelas) para outras contas. Este pode
ser um pagamento agendado simples, o qual é pago de uma só vez,
pagamentos múltiplos como um &quot;pacote&quot;, ou pagamentos
recorrentes (periódicos) (ex. mensais). Os pagamentos serão feitos
automaticamente nas datas especificadas.
<br>
Os pagamentos agendados também podem ser combinados com faturas
eletrônicas. Um membro que envia uma fatura para outro membro, será
capaz (caso possua permissões) de especificar se a fatura precisa ser
paga diretamente ou pode ser paga em uma data agendada (futura) ou em
múltiplas datas de pagamentos. Uma vez que o membro recebedor da fatura
aceita-la, os pagamentos agendados irão aparecer em sua lista de
pagamentos agendados (e cobrados nas datas especificadas pelo membro que
enviou a fatura).

<span class="admin"> É possível (nas configurações do sistema)
permitir que qualquer tipo de pagamento possa ser agendado. Para ativar
pagamentos agendados, você precisa fazer o seguinte:
<ol>
	<li><b>Permissões:</b> Primeiro você precisa definir todas as <a
		href="${pagePrefix}groups#manage_group_permissions_member"><u>permissões</u></a>
	para o grupo do membro. No momento, existem três permissões para o
	grupo do membro, que podem ser ativadas, mas você pode não querer
	ativar todas. Se você quer permitir que <a
		href="${pagePrefix}brokering"><u>corretores</u></a> ou administradores
	façam pagamentos agendados para o membro, você deve marcar as
	permissões para esses grupos também.
	<li><b>Configurações do grupo:</b> Para grupos de membro, existe
	uma <a href="${pagePrefix}groups#edit_member_group"><u>configuração
	especial de grupo</u></a> (&quot;Menu: Usuários & Grupos > Grupos de
	permissões&quot; e clique no ícone de edição <img border="0"
		src="${images}/edit.gif" width="16" height="16">&nbsp; do grupo
	do membro) para pagamentos agendados. Esta é muitas vezes esquecida,
	então não se esqueça de defini-la.
	<li><b>Tipo de transação:</b> No <a
		href="${pagePrefix}account_management#transaction_types"><u>tipo
	de transação</u></a> o agendamento deve ser ativado. Na <a
		href="${pagePrefix}account_management#transaction_type_details"><u>janela
	de propriedades do tipo de transação</u></a> é a caixa para a opção
	&quot;permitir pagamentos agendados&quot;.<br>
	<b>Note:</b> Para alguns tipos de pagamentos, o agendamento não esta
	disponível. Estes são tipos de transação de membro para sistema, e
	tipos de transação para auto-pagamento.
</ol>
Isto deve configurar o funcionamento dos pagamentos agendados. Neste
caso, cada <a href="#payments"><u>janela de pagamento</u></a> irá exibir
uma caixa de seleção para o &quot;Agendamento&quot;, quando for
relevante.
<br><br>Pagamentos agendados podem ser procurados através do &quot;Menu:
Contas > Pagamentos agendados&quot;.
</span>
<span class="member"> Os seus pagamentos agendados podem ser
procurados através do &quot;Menu: Conta > Pagamentos agendados&quot;. </span>
<hr class="help">

<a name="pay_scheduled"></a>
<h3>Fazer um pagamento agendado</h3>
Este item da ajuda é sobre
<a href="#scheduled"><u>Pagamentos agendados</u></a>
e descreve os campos especiais para isso, dentro da janela de
<a href="#payments"><u>pagamento</u></a>
.
<br><br>A caixa de &quot;agendamento&quot; possui três valores possíveis:




<ul>
	<li><b>Não agendar (processar imediatamente):</b> Escolha isso
	caso não deseje usar o agendamento.
	<li><b>Agendado para uma data futura:</b> Se você escolher esta
	opção, o pagamento será efetuado na data que você especificar. A data
	deve ser especificada no campo &quot;Agendado para&quot; que deve
	aparecer após você escolher a opção. Você pode usar o seletor de data
	através do ícone de calendário <img border="0"
		src="${images}/calendar.gif" width="16" height="16">&nbsp;.
	<li><b>Agendado para pagamento parcelado:</b> Esta é a forma mais
	sofisticada dos pagamentos agendados. Você pode dividir um simples
	pagamento em múltiplo sub-pagamentos (parcelas) escolhendo esta opção.
	Para cada sub-pagamento (parcela), a data e o valor podem ser definidos
	individualmente.<br>
	Os seguintes elementos estão disponíveis no formulário:
	<ul>
		<li><b>Nº de parcelas:</b> O número de sub-pagamentos (parcelas)
		que você deseja fazer. Por exemplo: 10 pagamentos, um por semana. O
		valor que você especificou acima é dividido em parcelas iguais.
		<li><b>Data da primeira parcela:</b> Você pode usar o seletor de
		data através do ícone de calendário <img border="0"
			src="${images}/calendar.gif" width="16" height="16">&nbsp;.
		<li><b>Parcela a cada:</b> Use estas duas caixas para escolher o
		período.
		<li><b>Calcular:</b> Você pode usar este botão, para ver quanto
		será pago exatamente em cada data. Assim que você clicar neste botão,
		uma visão geral das datas das parcelas e valores é exibida. Você pode
		alterar estas datas e valores, mas deve ter cuidado para que a soma de
		todas as parcelas corresponda ao valor total informado acima.<br>
		Note: esta opção não faz o processamento de nada. É meramente usada
		para visualizar os valores e datas.
	</ul>
</ul>
<hr class="help">

<A NAME="scheduled_payments_by_admin"></a>
<A NAME="scheduled_payments_by_member"></a>
<h3>Procurar pagamentos agendados</h3>
Aqui você pode procurar por
<a href="#scheduled"><u>pagamentos agendados</u></a>
. Os seguintes elementos do formulário podem ser informados. Observe que
deixando um campo em branco, a busca irá retornar todos os valores
possíveis para o campo.
<ul>
	<li><b>Tipo de procura:</b> Aqui você pode especificar pagamentos
	de &quot;saída&quot; ou pagamentos de &quot;entrada&quot;. Um pagamento
	de saída é apenas um pagamento normal; de pagamento de
	&quot;entrada&quot; significa uma <a href="${pagePrefix}invoices"><u>fatura
	eletrônica</u></a> a qual o remetente especificou que pode ser paga com
	pagamentos agendados.
	<li><b>Conta:</b> Escolha um tipo de conta na caixa de seleção.
	Esta só é visível se mais de uma possibilidade estiver disponível.
	<li><b>Estado:</b> &quot;Aberto&quot; significa &quot;ainda não
	pago&quot;; o restante é auto-explicativo.
	<li><b>Nome / Nome de usuário:</b> Neste dois campos você pode
	especificar um membro para o qual o pagamento foi feito. Este campos
	são auto-completáveis.
	<li><b>Da data / até a data:</b> Você pode especificar um período
	de tempo aqui. Você pode usar o seletor de data através do ícone de
	calendário <img border="0" src="${images}/calendar.gif" width="16"
		height="16">&nbsp;.
</ul>
<hr class="help">

<a name="view_scheduled_payment"></a>
<h3>Detalhes do pagamento agendado</h3>
Esta janela irá mostrar os detalhes do
<a href="#scheduled"><u>pagamento agendado</u></a>
. Você pode clicar no nome das pessoas para ao perfil delas.
<br>
Você pode clicar no ícone de impressão
<img border="0" src="${images}/print.gif" width="16" height="16">
&nbsp; para imprimir os detalhes.
<br><br>Se você tiver as permissões, dois botões podem estar disponíveis
na parte de baixo da janela:
<ul>
	<li><b>Bloquear:</b> Isto permite que você bloqueie o pagamento.
	Ele não será efetuado até você desbloqueá-lo.
	<li><b>Desbloquear:</b> Desbloqueia o pagamento, assim ele será
	efetuado nas datas originais de agendamento. Caso a data do agendamento
	já tenha passado, este botão não será visível. Neste caso você ainda
	pode pagar indo a janela de <a href="#sheduled_payment_transfers">transferências
	do pagamento agendado</a> abaixo, e clique no ícone ver <img border="0"
		src="${images}/view.gif" width="16" height="16">&nbsp; da
	transferência.
	<li><b>Cancelar:</b> A diferença entre o botão de bloquear é que
	cancelar um pagamento agendado é definitivo. Os pagamentos agendados
	que estão abertos não serão pagos, e serão removidos definitivamente,
	sem ter a opção de "descancelar". O pagamentos agendados que já foram
	pagos não serão desfeitos.
</ul>
<hr class="help">

<br><br><A NAME="sheduled_payment_transfers"></A><!-- Link is correct, but with Typo -->
<h3>Transferências do pagamento agendado</h3>
Esta página mostra todas as transferências (sub-pagamentos) que são
parte de um
<a href="#pay_scheduled"><u>pagamento agendado múltiplo</u></a>
. Você pode clicar no ícone de visualizar
<img border="0" src="${images}/view.gif" width="16" height="16">
&nbsp; para ir aos detalhes do pagamento.
<hr class="help">


<A NAME="scheduled_payments_result"></A>
<h3>Resultado da busca (pagamentos agendados)</h3>
Esta janela de resultado mostra uma lista com os
<a href="#scheduled"><u>pagamentos agendados</u></a>
de acordo com os critérios especificados acime.
<br><br>O seguinte é exibido:
<ul>
	<li><b>Data:</b> A data do agendamento
	<li><b>Nome de usuário:</b> Você pode clicar nele para ir direto
	ao perfil do membro.
	<li><b>Valor:</b>
	<li><b>Parcelas:</b> O primeiro número mostra a quantidade de
	parcelas deste pagamento agendado que já foi pago; o segundo número
	mostra o número total de parcelas deste pagamento agendado. Se o
	pagamento não é dividido em parcelas, então este segundo número será
	&quot;1&quot;.
	<li><b>Estado:</b> Pode ser &quot;agendado&quot;,
	&quot;bloqueado&quot;, &quot;aguardando autorização&quot; (veja <a
		href="#authorized"><u> pagamentos autorizados</u></a>).
	<li><img border="0" src="${images}/view.gif" width="16"
		height="16">&nbsp;use este ícone para ver os detalhes desta
	transferência. Lá você tem a opção de imprimir os detalhes, e bloquear,
	desbloquear ou pagar o pagamento (caso você tenha as permissões
	corretas para isso).
</ul>
<hr>

<a name="authorized"></a>
<h2>Pagamentos autorizados</h2>
Cyclos pode ser configurado para que os pagamentos necessitem de
autorização antes que o valor seja transferido realmente para a conta do
recebedor. A autorização pode ser feita por um administrador, corretor
ou o membro recebedor, de acordo com a configuração. Pode existir mais
de um nível de autorização, significando que mais do que uma das pessoas
acima necessitarão autorizar; Para cada nível de autorização diferentes
critérios podem ser configurados.
<br>
Enquanto o pagamento não for autorizado, ele ficará no estado de
&quot;aguardando autorização&quot;. O autorizador será notificado e
poderá autorizar o pagamento. Ambos membro e autorizador terão acesso a
lista com pagamentos pendentes que necessitem de autorização. Ambos
pagantes e recebedores receberão uma notificação quando o pagamento é
feito (autorizado).

<span class="admin"> Pagamentos autorizados são gerenciados por <a
	href="${pagePrefix}account_management#transaction_types"><u>tipo
de transação</u></a>; Existem diversas configurações disponíveis.
<br><br>Pagamentos autorizados podem ser ativados da seguinte maneira:
<ol>
	<li><b>permissões:</b> Primeiro você deve ter cuidado para que
	todas <a href="${pagePrefix}groups#permissions"><u>permissões</u></a>
	relevantes estejam definidas. Você pode definir as permissões para
	administradores, corretores e membros. Para cada um desses grupos
	existem diversas permissões em autorizações.
	<li><b>configurar autorização no tipo de transação:</b> No <a
		href="${pagePrefix}account_management#transaction_types"><u>tipo
	de transação</u></a>, você precisa ativar autorizações. Isto é feito através do
	campo &quot;requer autorização&quot; na <a
		href="${pagePrefix}account_management#transaction_type_details"><u>
	janela de detalhes do tipo de transação</u></a>. <b>Note:</b> esta opção de
	autorização não pode ser desmarcada enquanto existir pagamentos
	aguardando para serem autorizados.
	<li><b>Definir nível de autorização:</b> Você precisa configurar
	os níveis de autorizações. Isto é feito em uma janela abaixo, na <a
		href="${pagePrefix}account_management#authorized_payment_levels"><u>janela
	de níveis de autorização do pagamento</u></a>. Por favor consulte a ajuda local
	para detalhes.
</ol>
</span>
<span class="member">
<br><br>Autorização para membros, simplesmente significa que o recebedor
terá que aceitar o pagamento de outros membros, antes que a transação
seja feita. Neste pagamento o recebedor tem a possibilidade de negar o
pagamento (ex. o produto não está disponível). Ambos, recebedor e
pagador, receberão uma notificação. Esta configuração é similar ao uso
de faturas e muito rara. É melhor não utilizar a autorização de
recebedor e faturas eletrônicas no mesmo sistema.
</span>
<br><br>Você pode encontrar os pagamentos autorizados nas seguintes
localizações do programa:
<ul>
	<li><b>Menu: Contas > Aguardando autorização:</b> Dará uma visão
	geral dos pagamentos que necessitam ser autorizados por você. Este menu
	só é visível se você possuir autorização para autorizar pagamentos de
	entrada.
	<li><b>Menu: Contas > Autorizações:</b> Permite que você procure
	por qualquer autorização, passada ou presente, autorizada, negada ou
	cancelada. Todas ações de autorizações passadas, feitas por você, podem
	ser encontradas através desta janela. Aqui você pode procurar por ações
	de autorizações feitas pelo sistema, assim não apenas pagamentos
	autorizados por um administrador.<br>
	Observe que esta opção é para procurar uma ação de autorização.
	Portanto transações aguardando autorização não serão encontradas (pois
	nenhuma ação foi efetuada nelas ainda).
	<li class="broker"><b> Menu: Corretagem > Aguardando
	autorização: </b> Esta é a visão geral de pagamentos dos seus membros que
	você precisa autorizar como corretor ( ao contrário do Menu: Contas >
	Aguardando autorização, onde você irá encontrar seus pagamentos
	autorizados pessoais).
	<li class="broker"><b> Menu: Corretagem > Autorizações: </b> O
	mesmo que o Menu: Conta > Autorizações, mas relativas às suas
	autorizações como corretor.
</ul>
<hr class="help">

<a name="transfers_awaiting_authorization_by_member"></a>
<a name="transfers_awaiting_authorization_by_admin"></a>
<h3>Transferências para autorizar</h3>
Use esta janela para obter uma visão geral das transferências que
precisam ser
<a href="#authorized"><u>autorizadas</u></a>
por você. Como sempre, deixando um campo em branco, a busca retornará
todos os valores possíveis para o campo. Assim clicando no botão
&quot;procurar&quot; sem especificar nenhum campo, resultará em todos os
pagamentos aguardando autorização.
<br><br>Você pode especificar os seguintes critérios para busca:
<ul>
	<li><b>Nome / Nome de usuário:</b> Estes campos são
	auto-completáveis, assim apenas informar as primeiras letras é o
	suficiente.
	<li><b>Da data / até a data:</b> Você pode especificar um período
	aqui, e você pode usar o seletor de data através do ícone de calendário
	<img border="0" src="${images}/calendar.gif" width="16" height="16">&nbsp;.



	
	<li><b>Tipo de transação:e</u></b> Procure pelo tipo de transação.
	<li span class="admin"><b>Apenas se o corretor não puder
	autorizar:</b> Marcando esta opção a busca retornará apenas os pagamentos
	onde apenas você, o administrador, pode autorizar.
</ul>
O resultado será exibido na janela abaixo.
<hr class="help">

<a name="transfers_awaiting_authorization_result"></a>
<h3>Resultado da busca (transferências aguardando autorização)</h3>
Nesta visão geral você verá as transações que ainda estão aguardando a
sua
<a href="#authorized"><u>autorização</u></a>
. Os valores negativos são pagamentos de saida que precisam de
autorização, e os positivos são pagamentos de entrada que estão
aguardando por autorização.
<br><br>Clique no ícone de visualizar <img border="0"
	src="${images}/view.gif" width="16" height="16">&nbsp; para abrir
a janela de detalhes, onde você pode autorizar ou negar o pagamento.
<hr class="help">

<a name="transfer_authorizations_by_admin"></a>
<a name="transfer_authorizations_by_member"></a>
<h3>Ações de transações autorizadas</h3>
Com esta janela você pode procurar por ações de
<a href="#authorized"><u>autorizaões</u></a>
. O formulário é bastante simples. Se você deixar um campo em branco,
significa que todos os valores possíveis para o campo será retornado no
resultado.
<br>
As seguintes opções de procura estão disponíveis:
<ul>
	<li><b>Tipo de transação:</b> Procure pelo tipo de transação.
	<li><b>Procure por ação:</b>
	<ul>
		<li><b>autorizados:</b> Pagamentos aprovados.
		<li><b>negados:</b> Pagamentos negados.
		<li><b>cancelados:</b> Pagamentos cancelados (por outros).
	</ul>
	<li><b>Procure por membro:</b> Procure por um membro individual.
	<li><b>Procure por período:</b> Procure por intervalo de tempo.
</ul>
Quando pronto, você pode clicar no botão &quot;procurar&quot; no final
da página. Os resultados serão exibidos na janela abaixo.
<hr class="help">

<a name="transfers_authorizations_result"></a>
<h3>Resultado da busca no histórico de autorizações</h3>
Mostra o resultado da busca para os critérios especificados na janela
acima. Use o ícone de visualizar
<img border="0" src="${images}/view.gif" width="16" height="16">
&nbsp; para obter os detalhes para o ítem.
<hr class="help">

<a name="transaction_authorizations_detail"></a>
<h3>Ações de autorizações</h3>
Esta janela mostra todas as ações de
<a href="#authorized"><u>autorização</u></a>
que já foram efetuadas na transação acima. Estas podem ser autorizações,
mas também negações ou cancelamentos. Ela mostra a data e quem efetuou a
ação.
<hr class="help">

<h2>Janela de pagamentos diversos</h2>
Abaixo são dadas algumas janelas de ajuda gerais e diversas relativas a
pagamentos.
<hr>

<a name="accessing_channels"></a>
<h3>Acessando canais de pagamentos</h3>
Dependendo da configuração, um membro pode fazer pagamentos através de
vários canais.
<ul>
	<li>O mais comum é o acesso principal pela web (por padrão
	www.dominio.com/cyclos);
	<li>Outro canal útil é uma página simples onde os membros podem
	apenas acessar e efetuar um pagamento rápido
	(www.dominio.com/posweb/pay).
	<li>Membros / empresas que desejam receber pagamentos de
	consumnidores/clientes na forma de um PDV (ponto de venda) podem usar a
	página POSweb (www.dominio.com/posweb/receive). Observe que os clientes
	necessitam gerar uma senha externa (PIN) pessoal para validar o
	pagamento.
	<li>Para membros / empresas que querem ter a possibilidade de
	pagar e receber na mesma página podem usar o canal de acesso
	(www.dominio.com/posweb).<br>
	Esta é uma página comumente usada por &quot;caixas&quot; locais onde os
	clientes podem recuperar ou resgatar vales (títulos) ou dinheiro
	físico.
	<li>Para membros / empresas que possuam operadores de caixa, que
	podem fazer e aceitar pagamentos de consumidores /clientes, podem usar
	a página POSweb para operadores (www.dominio.com/posweb/operator).<br>
	<li>Pagamentos móveis podem ser feitos a partir da URL
	www.dominio.com/cyclos/mobile (wap2) e www.dominio.com/cyclos/wap
	(wap1).
</ul>
A disponibilidade dos canais de pagamentos dependem da configuração do
sistema. Em geral receber pagamentos irá demandar o uso da senha externa
(PIN) pelo cliente. Fazer pagamentos através do canal POSweb funciona da
mesma maneira que o acesso principal, solicitando a senha de acesso ou a
senha de transação, dependendo da configuração.

<hr class="help">

<a name="request"></a>
<h3>Solicitação de pagamento através de outro canal</h3>
Nesta janela você pode solicitar um pagamento através de outro pagamento
intermediário (
<a href="#accessing_channels"><u>canal</u></a>
). Isto funciona como uma fatura, na forma em que o pagamento será
ativado assim que for aceito. A diferença é que esta solicitação de
pagamento precisa de um PIN e uma confirmação com o ID da solicitação de
pagamento (fornecido no SMS). Atualmente apenas solicitações de
pagamentos por SMS estão disponíveis. A administração precisará ativar o
canal SMS antes que essa função possa ser usada.
<br><br>Isto funciona assim: Quando colocar um membro no campo de nome de
usuário /nome do membro, informar um valor e descrição, e clicar em
&quot;enviart&quot;, uma solicitação de pagamento será enviada através
do canal SMS. Isto significa que o membro receberá instantaneamente um
SMS que pode ser confirmado através da resposta com a informação do PIN.
Após a solicitação de pagamento ter sido enviada o estado será alterado
(veja ajuda abaixo). Quando o membro confirmar o pagamento através da
resposta por SMS, o estado será alterado para &quot;Pago&quot;, (e o
produto pode ser entregue ou enviado).
<hr class="help">

<a name="search_requests"></a>
<h3>Procurar solicitação de pagamento</h3>
Nesta janela você pode procurar por solicitações de pagamento. Por
padrão esta janela mostrará todas as solicitações pendentes e bem
sucedidas (confirmadas e pagas). Você também pode filtrar por outros
estados e bem como procurar por membro.
<br><br>A janela de resultado mostrará as solicitações de pagamento de
acordo com os filtros de busca. A página é recarregada automaticamente a
cada 5 segundos (e um estado mudará automaticamente).
<hr class="help">

<a name="account_overview"></a>
<h3>Visão geral de conta</h3>
Esta janela mostra uma lista com todas as contas acessíveis.
<ul>
	<li><img border="0" src="${images}/view.gif" width="16"
		height="16">&nbsp; Clique no ícone se você quiser entrar na
	conta;
</ul>
<hr class="help">

<a name="transaction_history"></a>
<h3>Resumo de transações</h3>
Esta janela tem várias opções para procurar por transações. Você pode
selecionar a partir das seguintes opções:
<UL>
	<LI><b>Estado:</b> Este é o estado dos pagamentos que necessitam
	autorização. Este só será exibido quando autorizações estão ativados
	para pagamentos de membros ou empréstimos. Ele mostrará os estados:
	<ul>
		<LI><b>Aguardando autorização:</b> O pagamento ou o empréstimo
		precisa ser autorizada antes que a transferência seja processada.
		<LI><b>Processado:</b> O pagamento ou empréstimo foi autorizado e
		processado.
		<LI><b>Negado:</b> O pagamento ou empréstimo foi negado.
	</ul>
	<LI><b>Tipo de pagamento:</b> Com esta caixa de seleção você pode
	filtrar por um tipo de pagamento específico.
	<LI><b>Nome / nome de usuário:</b> Exibe apenas transações para
	certas pessoas, preenchendo o nome de usuário ou de membro desta
	pessoa.
	<LI><b>Intervalo de tempo:</b> Exibe apenas transações do
	intervalo de tempo, preenchendo a data inicial e final.
	<LI><b>Descrição:</b> Procura por certas descrições, preenchendo
	um termo (palavra-chave) na caixa de <I>descrição</I>. Preenchendo
	&quot;bicicleta&quot; mostra todas as transações com a palavra
	&quot;bicicleta&quot; na descrição ou no título.
	<LI><b>Número da transação:</b> Se os números de transações
	estiverem ativados no sistema, você pode usar este campo para procurar
	por um número de transação.
</UL>
<hr class="help">


<a name="transaction_history_result"></a>
<h3>Resultado do resumo de transações</h3>
Esta janela exibe o resultado do resumo de pagamentos.
<br>
Se você clicar no ícone de impressão
<img border="0" src="${images}/print.gif" width="16" height="16">
&nbsp; (localizado na barra superior, próximo ao ícone de ajuda), será
exibida uma versão imprimível do resultado da busca e um relatório de
resumo.
<br>
Se você clicar no ícone salvar
<img border="0" src="${images}/save.gif" width="16" height="16">
&nbsp; (localizado na barra superior, próximo ao ícone de ajuda), você
pode baixar o resultado no formato de um arquivo CSV.
<br><br>A primeira seção da janela mostra as seguintes informações:
<ul>
	<li class="member"><b>Limite inferior de crédito:</b> O limite
	inferiro de crédito (tanto 0 ou negativo).
	<li class="member"><b>Balanço disponível:</b> O balanço disponível
	que pode ser gasto.
	<li class="member"><b>Valor reservado:</b> Este é o valor que esta
	temporariamente reservado e não pode ser usado para pagamentos. Estes
	valores podem estar reservados pela existência de transações pendentes
	de autorização, ou por uma reserva para cobrança de taxas de juros ou
	cobrança de taxa de liquidez.
</ul>
Abaixo desta seção uma lista é exibida com todos os pagamentos (feitos e
recebidos). Pagamentos recebidos (entrada) são exibidos com o símbolo
"+" na frente; Pagamentos de saída são exibidos como símbolo "-" na
frente. A lista exibe a data do pagamento, o membro (recebedor ou
pagante) e a descrição do pagamento. O nome de usuário / nome do membro
são links para seus perfis.
<br>
Ao clicar no ícone de visualizar
<img border="0" src="${images}/view.gif" width="16" height="16">
de um pagamento, uma janela será aberta com os detalhes daquele
pagamento.
<hr class="help">

<a name="transaction_detail"></a>
<h3>Detalhes da transação</h3>
Esta janela mostra os detalhes do pagamento selecionado. Você pode
imprimir os detalhes da transação clicando no ícone de impressão. Caso
um pagamento &quot;Pai&quot; ou &quot;Filho&quot; exista, ele será
exibido abaixo desta janela.
<br><br><span class="broker admin"> No caso da transação
necessitar de autorização, você terá a opção de aceitar ou negar a
transação. Você precisará preencher um comentário. Caso a caixa de
seleção &quot;Exibir ao membro&quot; for selecionada, o comentário será
visível pelo membro. Caso esta opção não for selecionada, o comentário
será visível apenas por você e pela administração. </span> <span class="admin">
Em certas condições, é possível se desfazer uma transação através da
função de <a href="#charge_back"><u>estorno</u></a>. Neste caso, um
botão especial para estorno será visível nesta janela. </span>
<hr class="help">

<span class="admin"> <a name="charge_back"></a>
<h3>Estornar</h3>
Um administrador (com as corretas permissões) pode &quot;estornar&quot;
um pagamento, o que significa que um pagamento oposto será feito com o
mesmo valor. No caso do pagamento ter gerado outras transações (ex.
taxas e empréstimos), todas as transações geradas serão estornadas. Se o
estorno for possível, um botão especial para estorno será visível na <a
	href="#transaction_detail"><u>janela de detalhes da transação</u></a>.
Contudo, este botão só é visível se a transação não tiver sido feita a
muito tempo. Você pode definir o tempo máximo para um estorno de um tipo
de transação ser feito no &quot;Menu: Configurações > <a
	href="${pagePrefix}settings#local"><u>Configurações locais >
Estorno</u></a>&quot;.
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