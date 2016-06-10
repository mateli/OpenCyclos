<div style="page-break-after: always;">
<br><br>Cyclos possui uma senha para acesso, e
também pode ser configurado para usar uma senha especial para
transações.
<i>Onde encontrar.</i>
<br>
Você pode alterar sua senha de acesso através do &quot;Menu: Pessoal >
Alterar senha&quot;.
<br>
Para a senha de transação consulte a
<a href="#transaction_password"><u>seção de ajuda</u></a>
.
<br><br><span class="broker admin"> A senha de acesso de um membro
pode ser alterada através do perfil deste membro, seção &quot;Acesso >
Gerenciar senhas&quot;. </span>
<hr>


<a NAME="change"></a>
<h3>Alterar senha de acesso</h3>
Para alterar a senha, informe a sua senha atual e após informe duas
vezes a nova senha de acesso, para confirmar a alteração clique no botão
&quot;Enviar&quot;. É possível que políticas senha como proibir senhas
fáceis ou óbvias estejam ativadas. Neste caso uma mensagem de erro irá
aparecer quando você escolher uma senha que não satisfaça as políticas
de segurança definidas. A mensagem informará você sobre a política e o
formato permitido para a senha.
<br>
Não é permitido escolher uma senha que você já tenha usado no passado..

<span class="admin">Caso &quot;Forçar alteração no próximo
acesso&quot; seja marcado, o membro solicitado a alterar a senha no
próximo acesso.<br>
Caso a configuração do grupo &quot;Enviar senha por e-mail&quot; esteja
ativada, você terá uma opção extra, para automaticamente gerar uma nova
senha e envia-la por e-mail ao membro, clicando no botão &quot;Reiniciar
senha de acesso&quot;. Neste caso a senha de acesso será gerada
automaticamente sem que você precise preencher nada.</span>
<hr class="help">

<a name="transaction_password"></a>
<h2>Senha de transação</h2>
Uma senha de transação,é uma senha que é solicitada sempre que o membro
necessite fazer um pagamento.

<span class="admin"> De acordo com as configurações do sistema
(acesso) (&quot;Menu: Configurações > Configurações de acesso&quot;) a
senha de transação pode ser informada usando um campo de formulário
simples ou um teclado virtual. Você também pode escolher os caracteres
que farão parte da senha de transação.
<br><br>A senha de transação é gerenciada na página de gerenciamento do
usuário (&quot;perfil > acesso > gerenciamento de senhas&quot;). Esta
opção é disponível apenas se a senha de transação tiver sido ativada
para o grupo (nas permissões do grupo).
</span>
<hr>

<a NAME="transaction_password_generation"></a>
<h3>Senha de transação</h3>
Aqui você pode recuperar a sua
<a href="#transaction_password"><u>senha de transação</u></a>
. Após clicar em &quot;Obter senha de transação&quot; sua senha será
exibida. Tenha certeza de lembrar a sua senha de transação.
<hr class="help">

<span class="admin"> <a NAME="manage_transaction_password"></a>
<h3>Gerenciar senha de transação</h3>
<br>
Quando a <a href="#transaction_password"><u>senha de transação</u></a>
esta ativada para o grupo ela pode ter quatro estados diferentes.
<ul>
	<li><b>Inativa:</b> A senha de transação nunca foi gerada.
	<li><b>Pendente:</b> A senha de transação esta aguardando a
	geração pelo membro.
	<li><b>Ativa:</b> A senha de transação foi gerada pelo membro e
	esta ativa.
	<li><b>Bloqueada:</b> A senha de transação foi bloqueada pelo
	administrador.
</ul>
O estado é exibido nesta janela.
<br><br>Um administrador (com as permissões apropriadas) pode reiniciar
ou bloquear a senha de transação clicando no botão &quot;bloquear a
senha de transação&quot;. Neste caso a senha de transação é invalidada e
o membro não receberá uma nova (até que o administrador a reinicie).
<br><br>Você também pode reiniciar a senha de transação através do botão
&quot;reiniciar senha de transação&quot;. Neste caso o membro receberá
uma nova senha de transação.
<hr class="help">
</span>

<a name="pin"></a>
<h2>PIN</h2>
Um PIN é uma senha usada para alguns tipos de pagamentos externos, como
POS (Point of Sale), paypal ou pagamentos por SMS. Um pin possui apenas
números.
<br><br>
<span class="admin"> Para ativar a senha PIN, os seguintes passos
devem ser feitos:
<ul>
	<li><b>Canais:</b> O PIN deve ser ativado nos <a
		href="${pagePrefix}settings#channels_detail"><u>canais</u></a>
	(&quot;Menu: Configurações > canais&quot;, e clique no ícone de edição
	para modificar um canal).
	<li><b><a href="${pagePrefix}groups#edit_member_group"><u>Configurações
	do grupo</u></a>:</b> nas &quot;configurações de acesso&quot; o tamanho da senha
	PIN pode ser definida.
	<li><b><a
		href="${pagePrefix}account_management#transaction_types"><u>Tipo
	de transação</u></a>:</b> no tipo de transação apropriado, o canal deve ser
	ativado.
</ul>
</span>
Pin e acesso a
<a href="${pagePrefix}settings#channels"><u>canais</u></a>
podem ser acessados através do
<span class="admin"> <a href="${pagePrefix}profiles"><u>perfil
do membro</u></a> > canais externos. </span>
<span class="member"> &quot;Menu: Pessoal > Acesso externo&quot;.
</span>
<hr>

<a NAME="change_pin"></a>
<h3>Alterar / desbloquear PIN</h3>
A senha
<a href="#pin"><u>pin</u></a>
é usada por alguns tipos de pagamentos externos, como POS (point of
sale), paypal e pagamentos por SMS.
<br>
Uma senha PIN pode apenas ser numérica (letras não são permitidas). Para
alterar o seu PIN você deverá informar a sua senha de acesso antes. No
caso da
<a href="#transaction_password"><u>senha de transação</u></a>
ser usada, você terá que informa-la antes (ao invés da senha de acesso).
O pin deve ser informado duas vezes e confirmado com o botão de
&quot;Enviar&quot;.
<br><br>Quando a senha PIN é informada de forma errada, ela pode ser
bloqueada após um número de tentativas (por padrão 3). Você pode
aguardar até que o tempo de bloqueio espire ou pode desbloquea-la
manualmente clicando no botão desbloquear abaixo da janela para mudança
de PIN.
<hr class="help">

<a NAME="select_channels"></a>
<h3>Canais</h3>
Aqui os
<span class="admin"> <a href="${pagePrefix}settings#channels"><u>canais</u></a>
possíveis são exibidos.</span>
<span class="member">canais são exibidos; um canal é o meio pelo
qual o cyclos é acessado, por exemplo a web através de um navegador, ou
um telefone móvel.</span>
<br>
Nem todos podem estar disponíveis, dependem da configuração de sua
organização Você pode selecionar os canais que você quer usar
marcando-os na caixa de seleção.
<ul>
	<li><b>Pagamentos posweb:</b> Pagamentos Ponto de venda
	(pagamentos como consumidor na loja).
	<li><b>Acesso WAP 1:</b> Acesso para modelos antigos de telefones
	móveis que suportam WAP 1 (Wireless Application Protocol 1). O
	pagamento móvel suporta a visualização do saldo, e navegar no histórico
	de pagamentos.
	<li><b>Acesso WAP 2:</b> Acesso para modelos mais modernos de
	telefones móveis que suportam WAP 2 (Wireless Application Protocol 2).
	O pagamento móvel suporta a visualização do saldo, navegar no histórico
	de pagamentos, e pagamentos.
	<li><b>Pagamentos de loja virtual:</b> Permitem pagamentos
	externos em sites (e-commerce).</u> Não se esqueça de clicar em
	&quot;enviar&quot; após ter feito as suas escolhas, do contrário as
	alterações não serão salvas.</li>
</ul>
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