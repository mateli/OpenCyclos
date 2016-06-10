<div style="page-break-after: always;">
<a name="top"></a>
<br><br>Um dispositivo de acesso é um meio de
pagamento que pode ser usado para fazer pagamentos externos ao cyclos.
Este pode ser um dispositivo PDV (ponto de venda) ou um cartão de
crédito.<br>
Um membro com um cartão de crédito ativo pode fazer pagamentos através
do WebPOS, de um dispositivo POS (leitor de cartões) ou um programa POS
instalado em um computador. Pagamentos com cartão são tipicamente
pagamentos de consumidores para empresas. Um membro pode ter mais de um
cartão, mas apenas um ativo.<br>
<br>
<span class="broker admin"> É possível solicitar ao invés da
senha PIN, a senha de transação ou a de acesso do usuário.<br>
O cartão é baseado em um <a href="#edit_card_type"><u>tipo de
cartão</u></a> que é como um modelo para o cartão.
</span>


<span class="member"> <i>Onde encontrar?</i><br>
Os dispositivos de acesso podem ser acessados através do &quot;Menu:
Pessoal > dispositivosPOS / Cartões<br>
</span>
<span class="broker"> Os dispositivos de acesso dos usuário
"corretados" podem ser acessados através das acões de corretagem na
página do perfil. </span>

<span class="admin"> <i>Onde encontrar?</i><br>
Os dispositivos de acesso podem ser acessados através do perfil do
membro ou pelo "Menu: Dispositivos de acesso".<br>
<br>
<i>Como ativar.</i><br>
Para gerar um cartão, é necessário existir um "tipo de cartão", e ele
precisa ser relacionado a um ou mais grupos de membros ou corretores.
Após isso você pode gerar um cartão para o membro através das ações na
página de perfil do membro. Também é possível gerar múltiplos cartões
para um grupo de membros através da função de <a
	href="${pagePrefix}user_management#bulk_actions"><u>Ações em
massa</u></a>. </span>


<a name="card_details"></a>
<h3>Detalhes do cartão</h3>
Esta janela mostra os detalhes do cartão e as ações que você pode
executar.
<br>
<br>
Detalhes do cartão
<ul>
	<li><b>Número do cartão</b>
	<li><b>Data de ativação</b>
	<li><b>Data de criação</b>
	<li><b>Data de expiração</b>
	<li><b>Estado</b>
</ul>

Ações para o cartão
<ul>
	<li><b>Bloquear cartão</b> Esta opção só irá aparecer caso o
	estado do cartão seja "ativo". Quando esta ação é executada, o cartão
	entrará no estado "bloqueado", significando que ele não pode ser usado.
	Ele pode retornar ao estado ativo.
	<li><b>Desbloquear cartão</b> Esta opção só irá aparecer caso o
	estado do cartão seja "bloqueado". Quando esta ação é executada o
	cartão retornará ao estado "ativo".
	<li><b>Ativar cartão</b> Esta opção só ira aparecer caso o estado
	do cartão seja "pendente". Quando esta ação for executada o cartão
	entrará no estado "ativo". Tenha cuidado, caso o membro já possua um
	cartão ativo, e você ativa um novo cartão, o cartão ativo ja existente
	será cancelado.
	<li><b>Cancelar cartão</b> Quando esta ação é executada, o cartão
	irá entra no estado "cancelado", significando que ele não pdoerá ser
	usado novamente.
	<li><b>Alterar código de segurança do cartão</b> Esta opção só ira
	aparecer caso um código de segurança seja usado.
</ul>
<span class="admin"> <b>Obs:</b> Mais informações sobre o código
de segurança do cartão pode ser encontrada na ajuda da página <a
	href="#edit_card_type"><u>editar tipo de cartão</u></a>. </span>
<br><br><b>Obs:</b> As ações para cartões irão solicitar a senha de
transação caso ela esteja ativada. Observer que todas as ações precisam
ser ativadas para os membros, assim pode ser que alguma não seja exibida
por não ter sido ativada. <span class="admin"> <a
	name="card_logs"></a>
<h3>Registro de cartão</h3>
Esta janela irá exibir uma lista com todas as mudanças (caso existam) de
estado do cartão. As ações que geram registros do cartão são.
<ul>
	<li>Bloquear cartão
	<li>Desbloquear cartão
	<li>Ativar cartão
	<li>Cancelar cartão
</ul>
<hr class="help">
</span> <a name="search_cards"></a> <span class="broker admin">
<h3>Procurar cartões</h3>
Nesta janela você pode procurar por cartões com os seguintes critérios:
<ul>
	<li>Estado do cartão
	<li>Grupos
	<li>Data de expiração
	<li>Membro
	<li>Número do cartão
	<li>Tipo de cartão. O unico campo obrigatório. Caso exista apenas
	um tipo de cartão esta opção não sera exibida.
</ul>
<hr class="help">
</span> <a name="search_card_results"></a> <span class="member">
<h3>Listar cartões</h3>
Esta página mostra uma lista com todos os seus cartões. Você pode
acessar o cartão clicando no ícone da lupa, para poder efetuar ações no
cartão. </span> <span class="broker admin">
<h3>Resultado da busca por cartões</h3>
Esta página mostra uma lista com o resultado da busca. Você pode acessar
o cartão clicando no ícone da lupa para poder efetuar ações no cartão. </span>
<span class="admin"> Você pode exportar a lista para um arquivo
csv ou imprimi-la usando os ícones no cabeçalho.</span> <span class="admin">
<br><br><b>Criar cartão</b><br>
Quando a lista é acessada através do perfil de um membro, você terá a
opção de criar um novo cartão, clicando no botão "Criar cartão". Se você
usar esta opção um novo cartão sera criado com o estado pendente. O
cartão sera baseado ao tipo de cartão associado ao grupo do membro.
(caso o grupo não possua nenhum tipo de cartão associado, nenhum cartão
serã criado).<br>
Caso já exista um cartão no estado pendente, ele será cancelado em favor
do novo cartão.
</span>
<hr class="help">


<span class="admin"> <a name="list_card_type"></a>
<h3>Tipos de cartão</h3>
Esta janela mostra uma lista com todos os tipos de cartões do sistema.
Um tipo de cartão é um modelo para um cartão ( da mesma forma que um
tipo de transação é um modelo para uma transação ). Você pode editar ou
deletar um tipo de cartão existente através dos ícones de editar e
apagar.<br>
Observe que você não pode apagar um tipo de cartão quando transaçoes com
cartão baseadas neste tipo de cartão existirem.
<hr class="help">
</span>

<span class="admin"> <a name="edit_card_type"></a>
<h3>Editar tipo de cartão</h3>
Nesta página você pode criar ou editar um tipo de cartão. Um tipo de
cartão só pode ser editado quando não exista cartões gerados com este
tipo. Os seguintes campos existem:
<ul>
	<li><b>Nome</b>: O nome do tipo de cartão. O nome é usado apenas
	para as buscas.
	<li><b>Formato do número</b>: Representa como o número do cartão
	será formatado.<br>
	Os caracteres possíveis são:
	<ul>
		<li>&quot;#&quot; Um número
		<li>&quot;-&quot; Um separador
		<li>&quot;/&quot; Um separador
		<li>&quot;\&quot; Um separador
		<li>&quot;.&quot; Um separador
	</ul>
	Exemplos para um formato de número: &quot;#### #### #### ####&quot; ou
	&quot;####&quot; &quot;##/##&quot;
	<li><b>Data de expiração</b>: Nesta data o cartão irá entrar em um
	estado expirado (vencido, e não poderá mais ser usado nem ativado).
	<li><b>Ignorar dia na data de expiração</b>: Caso esta opção seja
	marcada o cartão irá expirar no último dia do mês.
	<li><b>Código de segurança</b>: O código de segurança do cartão é
	uma senha definida para o cartão. Funciona semelhante a senha PIN com a
	diferença de que o PIN no cyclos não é definido por cartão e sim por
	usuário ( o usuário pode usar a senha PIN para para de um canal de
	acesso).<br>
	Valores possíveis são:
	<ul>
		<li><b>Não usado</b>: O cartão não usará o código de segurança
		<li><b>Manual</b>: O membro e o corretor / administrador (com
		permissões) podem alterar o código de segurança.
		<li><b>Automático</b>: O sistema irá gerar o código de segurança.


		
	</ul>
	<b>Número máximo de tentativas/ erro do código de segurança do
	cartão</b>: O cartão será bloqueado após este número de tentativas
	incorretas.<br>
	<b>Tempo de bloqueio do código de segurança do cartão</b>: O tempo que
	o cartão permanecerá bloqueado após o número máximo de tentativas/erro
	do código de segurança do cartão..<br>
	<b>Tamanho do código de segurança</b> : O tamanho mínimo e máximo do
	código de segurança.<br>
</ul>
<hr class="help">
</span>


<a name="POS"></a>
<h3>POS (Ponto de venda)</h3>
Um dispositivo POS pode ser tanto uma maquina leitora de cartões ou um
programa instalado em um computador. Um POS normalmente é localizado em
comércios locais. Um membro pode possuir mais de um dispositivo POS.
Tipicamente um POS irá identificar o usuário (pagante) quando ele/ela
passar o cartão no leitor, (isso é opcional o POS também pode permitir
que o número seja informado manualmente). O usuário terá que validar o
pagamento com o cartão digitando o seu PIN.
<br>
<span class="admin"> Para habilitar pagamentos POS, o canal POS
precisa estar ativado. Mais informações sobre o POS pode ser encontrada
na ajuda da seção de <a href="${pagePrefix}settings#channels"><u>Configurações
- canais</u></a>. </span>


<a name="edit_pos"></a>
<h3>Criar / editar POS</h3>
Nesta janela você pode definir a configuração para o dispositivo POS e
executar ações relacionadas ao POS. Cada POS possui os seguintes
detalhes:
<ul>
	<li><b>Identificador</b>: Este número pode ser usado para
	gerenciar o dispositivo POS. Este é normalmente o número de série do
	dispositivo POS. Um identificador não pode ser alterado uma vez que
	tenha sido definido. <span class="admin"> O identificador é
	usado para identificar o POS quando ele se comunica com o Cyclos.</span>
	<li><b>Descrição do POS</b>: Descrição (opcional).
	<li><b>Nome de acesso do membro</b>: O nome de acesso do membro
	que o POS é relacionado.
	<li><b>Nome do membro</b>: O nome completo do membro que o POS é
	relacionado.
	<li><b>Nome do POS</b>: Este é um nome que pode ser dado ao POS.
	Contrário ao identificador este pode ser alterado. (ex: loja1, POS1,
	etc.)
	<li><b>Atribuido em</b>: Data em que o POS foi atribuido ao
	membro.
	<li><b>Estado</b>: O estado do POS. Pode ser Não atribuido,
	Atribuido e Ativo.
	<li><b>Permitir fazer pagamentos</b>: Um POS normalmente é usado
	para receber pagamentos de consumidores. Caso você queira permitir
	pagamentos feitos do dono do POS para outro membro, então esta opção
	precisa ser marcada.
	<li><b>Tamanho da página de resultados</b>: Este mostrará o máximo
	de resultados para a página do extrato da conta. O padrão é 5,
	significa que o histórico da conta mostrará o valor do saldo e as
	últimas 5 transações.
	<li><b>Número de recibos</b>: O número de recibos da transação a
	ser imprimido. É comum imprimir 2 recibos ( um para a loja e um para o
	consumidor).
	<li><b>Máximo de pagamentos agendados</b>: O máximos de pagamentos
	agendados permitido.
</ul>

As seguintes ações podem ser feitas para dispositivos POS. (Nem todos as
ações podem estar disposníveis, elas dependem de permissões).
<ul>
	<li><b>Atribuir</b>: Atualmente o POS não esta atribuido, e pode
	ser atribuido executando esta ação.
	<li><b>Desatribuir</b>: O POS esta atualmente atribuido e pode ser
	desatribuido executando esta ação.
	<li><b>Desbloquear</b>: O POS esta atualmente bloqueado e pode ser
	desbloqueado executando esta ação.
	<li><b>Bloqueado</b>O POS atualmente não esta ativo e pode ser
	bloqueado executando esta ação.
	<li><b>Alterar PIN</b>: Alterar o PIN do POS. O PIN é usado para
	inicializar o POS e para qualquer outra ação executada por ele, como
	solicitar extrato de conta e histórico, fazer pagamentos. Receber
	pagamentos de clientes com cartões não necessita do PIN do POS (poi sé
	o cliente que provê o PIN). Será exibido somente se tiver permissões.<span
		class="admin">
	<li><b>Descartar</b>: Quando um POS é descartado, ele não pode
	mais ser usado. Isto significa que não é mais possível criar um novo
	POS com o mesmo POS ID. Normalmente você irá descartar um POS quando o
	dispositivo POS (máquina) não puder mais ser usada (por ter quebrado,
	sido perdida ou roubada por exemplo).
	</span>
</ul>
<hr class="help">
</span>

<span class="admin"> <a name="pos_logs"></a>
<h3>Registros dos POS</h3>
Caso exista algum, mostra as mudanças de estado dos POS. As ações que
geram registros são:
<ul>
	<li>Atribuir um POS
	<li>Desatribuir um POS
	<li>Bloquear um POS
	<li>Desbloquear um POS
	<li>Descartar um POS
</ul>
<hr class="help">
</span>

<span class="broker admin"> <a name="search_pos"></a>
<h3>Procurar POS</h3>
Procurar por um POS baseado nos critérios dados, você pode usar as
seguintes opções.
<ul>
	<li>Estado do POS
	<li>Identificador
	<li>Membro
</ul>
<hr class="help">
</span>

<span class="broker admin"> <a name="search_pos_results"></a>
<h3>Resultado da busca por POS</h3>
Mostra uma lista com o resultado da busca.
<ul>
	<li><img border="0" src="${images}/edit.gif" width="16"
		height="16"> Clique no ícone para modificar o POS.
	<li><img border="0" src="${images}/delete.gif" width="16"
		height="16"> Clique no ícone se você desejar apagar o POS.<br>
	Obs: Você só pode apagar um POS caso ele nunca tenha sido atribuido a
	um membro.
</ul>
<hr class="help">
</span>

<span class="member"> <a name="member_pos"></a>
<h3>Lista de POS</h3>
Esta janela mostra uma lista com os POS que foram atribuidos a você,
Você pode clicar no ícone "Ver" <img border="0" src="${images}/view.gif"
	width="16" height="16"> para modificar o POS.
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
</div>
