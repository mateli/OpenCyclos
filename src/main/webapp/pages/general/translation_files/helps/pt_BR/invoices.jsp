<div style="page-break-after: always;">
<a name="top"></a>
<br><br>Cyclos permite a você &quot;enviar
cobranças&quot; para alguém através de uma &quot;fatura
eletrônica&quot;, solicitando o pagamento por bens enviados ou serviços
prestados.<br>
Existem muitos recursos para abertura e gerenciamento de faturas
eletrônicas entre membros e a administração. Membros também podem enviar
faturas eletrônicas para outros membros.<br>
Faturas eletrônicas podem ser aceitas ou rejeitadas pelo recebedor. O
remetente também pode cancelar a fatura eletrônica. A outra parte sempre
receberá uma notificação relativa a essas ações. Contudo os membros
nunca podem rejeitar uma fatura enviada de contas de sistema.
<br><br><i>Onde encontrar.</i><br>
Você pode acessar as faturas eletrônicas das seguintes formas:
<ul>
	<span class="admin">
	<li><b>Menu: Contas > Enviar fatura para membro:</b> Permite que
	você envie uma fatura eletrônica da organização (de uma conta de
	sistema) para um membro.</li>
	<li><b>Menu: Contas > Gerenciar faturas:</b> Permite que você
	gerencie as faturas de entrada e saída das contas de sistema.</li>
	</span>
	<span class="admin broker">
	<li><b><a href="${pagePrefix}profiles"><u>Perfil</u></a> do
	membro > Contas: ver faturas eletrônicas:</b> Permite que você veja as
	faturas eletrônicas de um membro específico.</li>
	</span>
	<span class="member">
	<li><b>Menu: Conta > Fatura para o sistema: </b> Permite que você
	envie faturas eletrônicas para a organização.</li>
	<li><b>Menu: Conta > Fatura para membro:</b> Permite que você
	envie fatura eletrônicas para outros membros.</li>
	<li><b>Menu: Conta > Faturas eletrônicas:</b> Permite que você
	veja e gerencie todos as suas faturas de entrada e saída.</li>
	<li><b><a href="${pagePrefix}profiles"><u>Perfil</u></a> do
	membro > Enviar fatura eletrônica:</b> Permite que você envie um fatura
	para o membro diretamente do perfil deste membro.</li>
	</span>
</ul>
<hr>

<span class="member"> <A NAME="send_invoice_member_to_system"></A>
<h3>Enviar fatura de membro para sistema</h3>
É possível para o membro enviar uma <a href="#top"><u>fatura
eletrônica</u></a> para o sistema (organização). O Administrador da organização
receberá a fatura e poderá aceitar ou rejeita-la. Quando um
administrador aceita ou rejeita uma fatura que você enviou, você recebe
uma notificação em sua seção de mensagens pessoais informando sobre a
ação.<br>
Para enviar a fatura, preencha os campos e clique no botão
&quot;enviar&quot;.
<hr class="help">
</span>

<A NAME="send_invoice_system_to_member"></A>
<A NAME="send_invoice_member_to_member"></A>
<A NAME="send_invoice_member_to_member_select"></A>
<h3>Enviar fatura para membro</h3>
Aqui você pode enviar uma fatura eletrônica para um membro. O membro
receberá uma &quot;fatura de entrada&quot;, e poderá pagar você clicando
no botão &quot;Aceitar&quot;. Se a fatura foi aceita ela torna-se um
pagamento normal, como qualquer outro pagamento, e será exibida como tal
no histórico de transações.
<br>
<span class="member">Por outro lado o membro pode escolher
rejeitar a sua fatura, significando que o pagamento esta cancelado. O
remetente receberá uma notificação de que a fatura foi rejeitada.
<br><br>No &quot;Menu Conta > Faturas eletrônicas&quot; você pode ver e
gerenciar faturas de entrada e saída.
</span>
<span class="admin">Uma visão geral de todos as faturas de
sistema para membro e de membro para sistema pode ser acessada no Menu:
&quot;Contas > Gerenciar faturas&quot;.</span>
<br><br>Você terá que preencher os seguintes campos:
<ul>
	<li><b>Nome/Nome de usuário:</b> Você pode selecionar o membro que
	deverá receber a fatura preenchendo o campo &quot;Nome&quot; ou o campo
	&quot;Nome de usuário&quot;. Se você digitar uma parte do nome ele será
	auto-completado. (Este campo não estará visível caso já esteja definido
	para qual membro você enviará a fatura.)
	<li><b>Valor:</b> Caso mais de uma moeda esteja disponível em seu
	sistema, você deverá escolher a moeda numa caixa de seleção após o
	valor. Se apenas uma moeda estiver disponível esta caixa não estará
	visível.
	<li><b>Agendamento:</b> Esta opção aparecerá se estiver ativada
	pela administração. Com a opção de agendamento você permitirá ao
	destinatário pagar a fatura com pagamentos agendados (parcelas). O
	destinatário da fatura será informado sobre as datas das parcelas e
	valores, e quando o destinatário aceitar a fatura, os pagamentos
	agendados estarão visíveis em seus pagamentos agendados de saída.
	<li><b>Tipo de pagamento:</b> Você terá que escolher o tipo de
	pagamento. Este campo apenas é visível caso exista mais de um tipo de
	pagamento apropriado. Se existir apenas um o campo não será exibido.
	<li><b>Descrição:</b> Quando a fatura é aceita, esta descrição
	será exibida como descrição da transação na visão geral de transações.
	
</ul>
Após completar o preenchimento do formulário, clique no botão
&quot;enviar&quot; para enviar a fatura. Após enviar será solicitado a
você uma confirmação.
<hr class="help">

<span class="admin"> <A NAME="manage_invoices_by_admin"></A>
<h3>Faturas eletrônicas de sistema</h3>
<br><br>Esta janela da a você a opção de ter uma visão geral das <a
	href="#top"><u>faturas eletrônicas</u></a> enviadas de contas de
sistema para membros (faturas de saída) e faturas de contas de membros
para contas de sistema (faturas de entrada).
<br><br>Você pode filtrar pelos seguintes campos:
<ul>
	<li><b><a href="#status"><u>Estado</u></a></b>
	<li><b>Tipo:</b> &quot;entrada&quot; ou &quot;saída&quot;
	<li><b>Período de tempo</b>
	<li><b>Nome:</b> nome de usuário ou nome do membro
	<li><b>Descrição:</b>
	<li><b><a
		href="${pagePrefix}account_management#transaction_types"><u>Tipo
	de pagamento</u></a>:</b> Este funciona apenas para faturas de saída. Para faturas
	de entrada o tipo de pagamento não esta definido. Um administrador terá
	que escolher o tipo de pagamento quando aceitar a fatura. (veja <a
		href="#incoming_invoice_detail_by_admin"><u>detalhes da fatura</u></a>).
	
</ul>
Após preencher o formulário clique em &quot;procurar&quot; e os
resultados serão exibidos na <a href="#invoices_result_by_admin"><u>janela
abaixo</u></a>.
<br><br>Note: Procurar por faturas com um estado diferente de
&quot;Aberta&quot; será exibido faturas do histórico que já tenham sido
canceladas, rejeitadas ou aceitas.
<hr class="help">
</span>

<span class="member"> <A NAME="manage_invoices_by_member"></A>
<h3>Minhas faturas</h3>
Com a opção &quot;tipo&quot; nesta janela você pode ver uma lista com <a
	href="#top"><u>faturas eletrônicas</u></a> tanto de &quot;entrada&quot;
quanto de &quot;saída&quot;. Os resultados serão exibidos na <a
	href="#invoices_result_by_member"><u>janela abaixo</u></a>.
<br><br>Na opção avançado você refina a busca usando alguns ou todos os
campos do formulário. Muitos campos são bastante simples. &quot;Nome de
usuário&quot; é o nome que o membro usa para acessar o sistema, e
&quot;Nome do membro&quot; é o nome real do membro.<br>
O <a href="#status"><u>estado</u></a> pode ter diversos valores
diferentes; Clique no link para aprender mais sobre isso.
<hr class="help">
</span>

<span class="admin broker"> <A
	NAME="manage_member_invoices_by_admin"></A> <A
	NAME="manage_member_invoices_by_broker"></A>
<h3>Faturas eletrônicas do membro</h3>
<br><br>Esta janela da a você uma visão geral das <a href="#top"><u>faturas
eletrônicas</u></a> de um membro, tanto faturas de entrada quando de saída.
<br><br>Você pode filtrar pelos seguintes campos:
<ul>
	<li><b><a href="#status"><u>estado</u></a></b>
	<li><b>tipo:</b> &quot;entrada&quot; ou &quot;saída&quot;
	<li><b>Período de tempo</b>
	<li><b>Nome:</b> Nome de usuário ou nome do membro
	<li><b>Descrição</b>
	<li><b>Tipo de pagamento</u></a></b>
</ul>
Após preencher o formulário, clique em &quot;procurar&quot; e o
resultado será exibido na página de gerenciamento de faturas.
<br><br>Note: Procurar por faturas com o estado diferente de
&quot;Aberta&quot;, será exibido faturas do histórico que ja tenham sido
canceladas, rejeitadas ou aceitas.
<hr class="help">
</span>

<a name="status"></a>
<h3>Estado da fatura eletrônica</h3>
O &quot;estado&quot; de uma
<a href="#top"><u>fatura eletrônica</u></a>
pode ser um dos seguintes:
<ul>
	<li><b>Aberta:</b> Faturas enviadas, mas ainda não pagas ou
	rejeitadas pelo destinatário
	<li><b>Aceita:</b> Faturas pagas pelo destinatário.
	<li><b>Negadas:</b> Faturas negadas (rejeitadas) pelo
	destinatário.
	<li><b>Cancelada:</b> Faturas que você mesmo cancelou.
	<li><b>Vencida:</b> Faturas as quais o destinatário não teve
	nenhuma reação (nem aceitas, nem rejeitadas) e passaram da data de
	vencimento.
</ul>
<hr class="help">

<A NAME="accept_invoice"></A>
<h3>Aceitar fatura eletrônica</h3>
Esta é uma janela de confirmação após você ter clicado no botão
&quot;aceitar&quot; de uma
<a href="#top"><u>fatura eletrônica</u></a>
.
<br>
Após esta, não tem volta. Quando você clicar em &quot;enviar&quot; o
valor será retirado da conta, e a cobrança será paga.
<hr class="help">

<A NAME="invoices_result_by_admin"></A>
<A NAME="invoices_result_by_member"></A>
<h3>Resultado da busca</h3>
Esta página exibe a lista de resultados da procura por
<a href="#top"><u>faturas eletrônicas</u></a>
. Clicando no ícone de edição (
<img border="0" src="${images}/edit.gif" width="16" height="16">
) irá abrir os detalhes da fatura eletrônica.
<br>
Se a fatura é &quot;aberta&quot; você pode efetuar uma ação de acordo
com o tipo de fatura eletrônica (aceitar, rejeitar, cancelar).
<hr class="help">

<A NAME="invoice_details"></A>
<A NAME="outgoing_invoice_detail_by_admin"></A>
<A NAME="incoming_invoice_detail_by_admin"></A>
<A NAME="outgoing_invoice_detail_by_member"></A>
<A NAME="incoming_invoice_detail_by_member"></A>
<h3>Detalhes da fatura eletrônica</h3>
Esta janela mostra os detalhes da fatura eletrônica. Dependendo das
permissões e do tipo da fatura, você pode tomar as seguintes ações:
<ul>
	<li><b>Aceitar:</b> <span class="member">Se você é</span> <span
		class="admin broker">Se este membro for</span> o destinatário desta
	fatura eletrônica, você pode aceita-la. Se você o fizer, o valor será
	retirado da <span class="admin broker">conta do membro</span><span
		class="member">sua conta</span>, e transferido para a conta do
	remetente da fatura. Em linguagem normal você terá pago a cobrança. Se
	você clicar neste botão, uma tela será exibida solicitando a sua
	confirmação.
	<li><b>Negar:</b> <span class="member">Se você é</span> <span
		class="admin broker">Se este membro for</span> o destinatário desta
	fatura eletrônica, você também pode negar (rejeitar) esta fatura. Isto
	significa que você recusa o pagamento deste valor. O pagamento não será
	feito, e a outra parte receberá uma notificação sobre esta ação.<br>
	Não é possível negar uma fatura enviada do sistema / organização.
	<li><b>Cancelar:</b> Se <span class="admin broker">este
	membro é</span> <span class="member">você for</span> o remetente desta fatura
	eletrônica, você pode cancela-la a qualquer momento, antes que o
	destinatário tenha aceitado ela. Se você cancelar uma fatura, a outra
	parte receberá uma notificação que a fatura foi cancelada em sua janela
	de mensagens pessoais.
</ul>
Para faturas eletrônicas de entrada, você pode ter que especificar o
tipo de pagamento primeiro, caso exista mais do que um tipo de pagamento
possível para este pagamento. Neste caso uma caixa para a seleção do
&quot;tipo de pagamento&quot; será visível. Em muitos casos esta caixa
não é visível.
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