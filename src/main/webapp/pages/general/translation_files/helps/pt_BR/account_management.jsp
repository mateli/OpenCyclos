<div style="page-break-after: always;">
<span class="admin broker">
<br><br>Todas as configurações relacionadas à
contas e transações pode ser feita na função de gerenciamento de contas.

<i>Onde encontrar.</i><br>
Gerenciamento de contas pode ser acessado através do &quot;Menu: Contas
> Gerenciamento de contas&quot;.
<hr>

<A NAME="currencies"></A>
<h2>Moedas</h2>
É possível criar uma nova moeda, e associar contas a essa moeda. Pode
existir uma moeda padrão definida por grupo.

<i>Onde encontrar.</i><br>
Moedas podem ser acessadas através do quot;menu: Contas > Gerenciar
moedas&quot;.
<hr>

<A NAME="currency_search"></A>
<h3>Moedas</h3>
A página de lista de moedas mostra uma lista com as moedas configuradas
no sistema. A moeda pode ser vinculada a um tipo de conta. Para
adicionar uma nova moeda clique &quot;Nova moeda&quot; no final da
página à direita. <br>
Para apagar ou editar uma moeda você precisará clicar nos ícones da
lista.
<ul>
	<li><img border="0" src="${images}/edit.gif" width="16"
		height="16"> Clique no ícone para modificar a moeda.
	<li><img border="0" src="${images}/delete.gif" width="16"
		height="16"> Clique no ícone para apagar a moeda.
</ul>
<hr class="help">

<A NAME="currency_details"></A>
<h3>Modificar moeda / Inserir nova moeda</h3>
Na página de edição de moeda você pode definir a moeda. Os seguintes
campos estão disponíveis:
<ul>
	<li><b>Nome:</b> Nome interno (não é mostrado em nenhum lugar)
	<li><b>Símbolo:</b> É mostrado nas páginas. Por exemplo após a
	moeda no campo preço de um anúncio.
	<li><b>Padrão:</b> Aqui você pode definir o nome/símbolo da moeda
	e como ele será mostrado. Você pode colocar tanto antes ou depois do
	valor. Algumas moedas (como dólar) mostrarão o símbolo antes do valor,
	e outras depois do valor. O padrão da moeda será mostrado em listas e
	mensagens.<br>
	Símbolos nem sempre são suportados pelos navegadores, um bom exemplo é
	o símbolo do Euro. Nesse caso você precisará usar o símbolo no formato
	unicode (e.g. &amp;euro;)
	<li><b>Descrição:</b> Para informação interna (não é mostrado em
</ul>
<hr class="help">
</span>


<span class="admin broker"> <a name="accounts"></a>
<h2>Contas</h2>
Contas no Cyclos pode ser do tipo &quot;Sistema&quot; ou
&quot;Membro&quot; Ambos tipos estão relacionados com uma moeda e podem
conter unidades, as quais podem ser transferidas para e desde outras
contas (Caso exista algum tipo de transação entre essas contas). <br>
Contrário à conta de Membro, a conta de Sistema não tem um proprietário
associado. Administradores que possuam permissões podem efetuar
pagamentos dessas contas de sistema para outras contas de sistema ou de
membros. <br>
Um membro pode não ter nenhuma conta, assim como pode ter uma ou mais
contas relacionada a ele, e fazer pagamentos entre suas próprias contas,
para contas de outros membros ou para contas de sistema.

Se uma nova conta do tipo &quot;Membro&quot; é criada, ela ainda não tem
nenhum membro relacionado. Você deverá seguir os seguintes passos para
que os membros existentes possam usar essa nova conta:
<ol>
	<li><b>Atribuir a conta a um grupo:</b> Somente após associar a
	conta à um grupo de membros os membros desse grupo terão acesso a essa
	conta. Isto pode ser feito através das <a
		href="${pagePrefix}groups#manage_groups"><u> configurações do
	grupo</u></a>.
	<li><b>Tipos de transações:</b> Antes dos membros poderem fazer
	pagamentos desta conta, ou receberem pagamentos nela, você precisará
	criar e associar &quot;<a href="#transaction_types"><u>tipos de
	transações</u></a>&quot;. Isto pode ser feito na janela de tipos de transações,
	que esta na mesma página que a janela para definir as propriedades da
	conta.
	<li><b>Permissões:</b> É claro que você terá que definir as <a
		href="${pagePrefix}groups#manage_groups"><u>permissões</u></a>.
</ol>
Além dos tipos de transações, existem muitas coisas que são relacionadas
diretamente com as contas, como empréstimos, taxas de transações, taxas
de contas e filtros de pagamentos.
<br><br>Cyclos Vem com um banco de dados com varias <a
	href="#standard_accounts"><u> contas padrões</u></a> que atende a
maioria dos sistemas/usuários.
<br><br><i>Onde encontrar.</i><br>
Contas podem ser gerenciadas (criadas, apagadas, etc) através do:
&quot;Menu: Contas > Gerenciamento de contas&quot;.<br>
Contas de sistema pode ser vistas no &quot;Menu: Contas > Contas de
sistema&quot;.
<hr>

<a name="standard_accounts"></a>
<h3>Tipos padrões de contas</h3>
Embora seja possível criar uma nova estrutura de contas a partir do
zero, nós provemos um banco de dados com definições que devem ser os
padrões para a maioria dos sistemas de moeda complementar. O banco de
dados padrão pode ser estendido com mais contas e tipos de transações.
Nós criamos uma conta de membro e várias contas de sistema. As <a
	href="#account_fees"><u>Taxas de contas</u></a> (automáticas, manuais e
taxas de liquidez = &quot;sobre volume&quot;) e <a
	href="#transaction_fees"><u> Taxas de transações</u></a> estão
desativadas por padrão, mas é fácil trocar os valores padrões e
ativa-las.
<br><br>O banco de dados vêm com as seguintes Contas de sistema padrões:











<ul>
	<li><b>Conta de empréstimo (débito):</b> A conta de débito ou
	conta de empréstimo é usada apenas para os empréstimos e para os
	créditos iniciais (que podem ser um empréstimo ou um donativo). Esta é
	a principal conta de sistema e é chamada de conta de empréstimo por
	razões claras (algumas vezes este tipo de conta é chamado de
	&quot;Conta flutuante&quot;, ou &quot;Conta de débito&quot;). É comum
	deixar a conta de débito como a única conta que não tem limite
	negativo. Esta conta é necessária para a criação das unidades. Quando
	as unidades são criadas, a conta de empréstimo vai a negativo, e o
	recebedor (normalmente uma conta de membro) vai a positivo com o mesmo
	valor de unidades.<br>
	A administração da conta de empréstimo é portanto muito crítica, pode
	não ser muito para sistemas do tipo LETS, mas para um Sistema BARTER
	(negócios) ou um sistema respaldado com dinheiro, esta administração
	deve ser muito controlada e segura.<br>
	<br>
	<li><b>Conta da comunidade:</b> A conta da comunidade é a conta
	cujo proprietário é a comunidade, ela pode receber as taxas (caso
	estejam ativadas) e o pagamento de contribuições dos membros. Um
	administrador pode efetuar um pagamento da conta da comunidade para a
	conta de um membro (ex. por um trabalho comunitário feito por um
	membro). Igualmente a uma conta de membro, a conta da comunidade não
	pode ir abaixo do limite inferior de crédito.<br>
	<br>
	<li><b>Conta de vale:</b> A conta de vale é a conta que contêm as
	unidades (digitais) que foram colocadas em circulação na forma de vales
	(unidades físicas). Quando um membro quer comprar um vale ele precisa
	pagar para a conta de vale. A organização pode verificar o pagamento e
	lhe entregar os vales. Quando um membro quer descontar um vale, o
	membro precisará devolver os vales de volta a organização, e um
	administrador terá de efetuar um pagamento da conta de vale para a
	conta deste membro. <br>
	No caso de sistemas onde as unidades são (parcialmente) respaldadas em
	dinheiro convencional, os vales podem ser trocados por dinheiro
	convencional. Neste caso não é necessário ser um membro do sistema,
	você apenas compra os vales como um tipo de &quot;Cupom Bônus&quot;.
	Neste caso um administrador terá de fazer um pagamento da conta de
	empréstimo para a conta de vales.<br>
	<br>
	<li><b>Conta da organização:</b> A conta da organização serve como
	uma conta extra para a organização. Se necessário o nome pode ser
	alterado de acordo com a sua função (ex. conta social ou conta de
	investimentos).
</ul>
<hr class="help">

<A NAME="account_search"></A>
<h3>Lista de contas</h3>
A página da lista de tipos de contas mostra uma visão geral com uma
lista de contas e seus tipos. (portanto nós muitas vezes nos referimos a
isto como tipo de conta).
<br><br>Para criar uma nova conta de membro ou de sistema você deverá
clicar no botão abaixo da janela chamado &quot;inserir novo tipo de
conta&quot;.
<ul>
	<li><img border="0" src="${images}/edit.gif" width="16"
		height="16"> Clique no ícone para modificar a conta.
	<li><img border="0" src="${images}/delete.gif" width="16"
		height="16"> Clique no ícone caso você queira apagar a conta.
	(Apagar uma conta só pode ser feito caso não exista nenhuma transação
	no sistema envolvendo esta conta).
</ul>
<hr class="help">

<A NAME="account_details"></A>
<h3>Modificar um tipo de conta / Nova conta</h3>
Na janela de detalhes da conta você pode criar ou modificar uma conta. <br>
Se você estiver criando uma nova conta você pode especificar se ela será
uma conta de sistema ou de membro. As seguintes opções estão
disponíveis:
<UL>
	<li><b>Nome:</b> Nome da conta. Isto será mostrado na <a
		href="#account_search"><u>lista de gerenciamento de contas</u></a>, e
	(caso esta seja uma ocnta de sistema) na <a
		href="${pagePrefix}payments#account_overview"><u>lista de
	visão geral de contas de sistemas</u></a>.
	<li><b>Descrição:</b> Explicação sobre a conta (legível apenas
	pela administração).
	<li><b>Moeda:</b> Aqui você define a <a href="#currencies"><u>moeda</u></a>
	para esta conta.
	<li><b>Tipo de limite (Apenas para contas de sistemas):</b> Uma
	conta pode ser ilimitada, isto significa que ela pode ir
	indefinidamente a negativo (Estas são geralmente contas de
	&quot;débito&quot;, &quot;flutuante&quot; ou &quot;empréstimo&quot;)<br>
	Se a conta for limitada, você pode especificar o limite superior e
	inferior da conta. O tipo de limite pode ser especificado apenas na
	criação da conta (não pode ser alterado depois).
</UL>
Nota: Muitas configurações da conta são específicas do grupo (por
exemplo os limites de crédito). Estas definições podem ser modificadas
nas <a href="${pagePrefix}groups#manage_groups"><u> definições
dos grupos.</u></a>.
<hr class="help">

<A NAME="transaction_types"></A>
<h2>Tipos de transações</h2>
Cada pagamento (também chamado de transação) possui um &quot;tipo de
transação&quot;. O tipo de transação define a conta de origem e de
destino do pagamento. Se um tipo de conta não tem tipos de transações
associados a ela, nenhum pagamento pode ser feito. O tipo de transação
deve ser associado a conta de origem (= a conta do pagante).


<i>Onde encontrar.</i><br>
Tipos de transações podem ser definidos e modificados na janela de
gerenciamento de conta; Para chegar la, você deve acessar o &quot;Menu:
Contas > Gerenciamento de contas&quot;, e clicar no ícone de edição para
modificar um tipo de conta. Na próxima tela existirá uma janela especial
com uma visão geral dos tipos de transações associados.
<hr class="help">

<A NAME="transaction_type_search"></A>
<h3>Lista de tipos de transações</h3>
A janela de tipos de transações mostra uma lista com os tipos de
transações relacionados a conta selecionada.
<ul>
	<li><img border="0" src="${images}/edit.gif" width="16"
		height="16"> Clique no ícone para modificar um tipo de
	transação.
	<li><img border="0" src="${images}/delete.gif" width="16"
		height="16"> Clique no ícone caso queira apagar um tipo de
	transação.
</ul>
Use o botão abaixo &quot;inserir novo tipo de transação&quot; para
inserir um novo tipo de transação.
<br><br>Nota1: As permissões de um tipo de transação são definidas por
grupo e podem ser configuradas no &quot;Menu: Usuários & grupos > Grupos
de permissões > editar permissões do grupo.&quot;
<br><br>Nota2: Se a conta selecionada for uma conta de membro as
configurações específicas (limite de crédito, etc) são definidas por
grupo. Isto também pode ser modificado na seção &quot;Usuários & grupos
> Grupos de permissões&quot; (ícone de edição - conta selecionada).
<hr class='help'>

<A NAME="transaction_type_details"></A>
<h3>Modificar / Inserir um tipo de transação</h3>
Nesta janela você pode configurar as propriedades de um tipo de
transação específico. Os tipos de transações possuem os seguintes campos
(nem todos estão visíveis, dependem do tipo e da configuração):
<ul>
	<li><b>Nome:</b> Nome do tipo de transação.<br>
	<br>
	<li><b>Descrição:</b> Descrição interna do tipo de transação. Esta
	descrição pode aparecer nos detalhes da transação.<br>
	Nota: No caso de pagamento de empréstimos e taxas periódicas, você pode
	usar alguns códigos para incluir alguns dados na descrição. Você pode
	usar &quot;nomes de variáveis&quot; - eles serão substituídos pelos
	seus valores correspondentes na descrição de uma eventual transação.. <a
		href="#placeholders"><u> Clique aqui</u></a> para uma visão geral.<br>
	<br>
	<li><b>Mensagem de confirmação:</b> Esta mensagem sera mostrada
	abaixo da informação da transação em uma janela pop-up de confirmação.
	Desta maneira é possível definir mensagens para tipos de pagamentos
	específicos.<br>
	<br>
	<li><b>De:</b> O tipo de conta do pagante.<br>
	<br>
	<li><b>Para:</b> O tipo de conta do recebedor.<br>
	<br>
	<li><b>Disponibilidade:</b> (Apenas para pagamentos entre contas
	de membros - esta opção não estará visível caso seus membros possuam
	apenas uma conta para esta moeda. Neste caso, a caixa de seleção
	&quot;ativado&quot; (próximo item) será mostrada,) A disponibilidade
	define de onde o pagamento pode ser efetuado.
	<ul>
		<li><b>Desativado:</b> O pagamento esta inativo e não sera
		mostrado em nenhum lugar.
		<li><b>Pagamento direto:</b> Este é o tipo de pagamento mais
		comum. Caso esta opção seja selecionada, um membro pode usar este tipo
		de transação para efetuar pagamentos a outros membros ou para uma
		conta de sistema.
		<li><b>Auto- pagamento:</b> Caso exista mais de uma conta de
		membro ( por exemplo conta corrente e conta poupança) você pode criar
		um tipo de transação para efetuar pagamentos entre essas contas. No
		caso de um auto-pagamento você provavelmente não quer permitir
		pagamentos da conta corrente de um membro para a conta de poupança de
		outro membro. Isto pode ser feito desmarcando a caixa de seleção de
		pagamento direto.
	</ul>
	Nota: Tipos de transações que podem ser gerados automaticamente, como
	taxas de contas e taxas de transações, serão sempre cobrados, mesmo que
	nenhuma opção seja marcada e nenhuma permissão seja definida para o
	grupo. O mesmo vale para o próximo item, a caixa de seleção
	&quot;habilitado&quot;.<br>
	<br>
	<li><b>Habilitado:</b> Pagamentos podem ser feitos usando este
	tipo de transação, e o tipo de pagamento é visível nas janelas de
	pagamentos extratos de transações. Note que este item é diretamente
	relacionado com o anterior: Caso &quot;Disponibilidade&quot; seja
	mostrado, &quot;habilitado&quot; não sera visível e vice versa.<br>
	<br>
	<li><b>canais:</b> Este item permite que você defina os <a
		href="${pagePrefix}settings#channels"><u>canais</u></a> pelos quais
	este tipo de transação pode ser usado. O canal canal padrão é
	&quot;Acesso principal pela web&quot;, mas outros (como acesso móvel)
	estão disponíveis.<br>
	<br>
	<li><b>Prioridade:</b> Se a opção de prioridade é selecionada, o
	tipo de transação terá prioridade sobre outros tipos de transações.
	Isto significa que quando um pagamento pode mostrar mais de um tipo de
	transação, apenas os tipos que tiverem configurados como prioritários
	serão mostrados.<br>
	A configuração de prioridade será usada apenas com sistemas
	relativamente complexos, onde vários grupos tem permissão de
	transacionar com outros grupos apenas em certas circunstâncias.<br>
	Com o grupo e o tipo de transação é possível configurar um sistema onde
	varias comunidades podem transacionar de forma independente mas também
	entre comunidades diferentes, todas no mesmo sistema.<br>
	<br>
	<li><b>Valor máximo por dia:</b> Isto é o máximo que pode ser pago
	por um membro em um dia com este tipo de transação. Por exemplo isto
	pode ser usado para limitar pagamentos feitos através de telefones
	celulares.<br>
	<br>
	<li><b>Requer autorização:</b> Caso selecionado, uma janela extra
	de autorização sera mostrada abaixo desta janela assim que esta opção
	seja gravada pela primeira vez. Mais informações sobre <a
		href="${pagePrefix}payments#authorized"><u>pagamentos
	autorizados</u></a> podem ser encontradas na ajuda <a
		href="#authorized_payment_levels"><u>desta janela</u></a> .<br>
	Se você desmarcar o campo de autorização, todos os níveis de
	autorizações serão cancelados para os novos pagamentos. <br>
	<br>
	<li><b>Permite pagamentos agendados (parcelamentos):</b> Se esta
	opção é selecionada, significa que este tipo de transação pode ser <a
		href="${pagePrefix}payments#scheduled"><u>agendado</u></a>. Isso não
	significa que com esse tipo de transação, qualquer membro ou
	administrador esta apto a usar a opção de agendamento. Você ainda
	necessitará definir as permissões dos grupos ( ver e permitir
	pagamentos agendados). Para mais informações vá para a seção Pagamentos
	- Pagamentos agendados do manual.
	<ul>
		<li><b>Reservar o valor total nos pagamentos agendados:</b>
		Quando esta opção é selecionada, um membro pode apenas agendar
		pagamentos caso ele possui o valor total de todas as parcelas
		disponível em seu saldo. O valor total será reservado e não poderá ser
		gasto.<br>
		Para pagamentos comuns entre membros, em um sistema de comércio, esta
		opção provavélmente não é usada. Para pagamentos de consumidores para
		empresas, por exemplo pagamentos através de dispositos POS (ponto de
		venda PDV) esta opção pode ser mais comum.
		<li><b>Permitir que o pagante cancele pagamentos agendados:</b>
		Quando esta opção é selecionada é permitido ao pagante cancelar
		pagamentos agendados. Observe que além desta opção o grupo do pagante
		também precisa ter permissão para cancelar pagamentos agendados.
		<li><b>Permitir que o pagante bloqueie pagamentos agendados:</b>
		Quando esta opção é selecionada é permitido ao pagante bloquear
		pagamentos agendados. Observe que além desta opção o grupo do pagante
		também precisa ter permissão para bloquear pagamentos agendados.
		<li><b>Exibir pagamentos agendados ao destinatário:</b>
		Pagamentos agendados são iniciados pelo pagante. Para o recebedor cada
		parcela aparece como um pagamento isolado. O recebedor não é alertado
		que a parcela é parte de um grupo de pagamentos.<br>
		Se você deseja que o recebedor veja os pagamentos agendados futuros
		(de entrada) esta opção precisa ser marcada.<br>
		Esta opção também é mais comum no caso de pagamentos de consumidores
		para empresas em um POS (ponto de venda).<br>
		Observe que pagamentos agendados resultantes da aceitação de uma
		fatura eletrônica sempre serão exibidos ao recebedor (emissor da
		fatura)
	</ul>
	<br>
	<li><b>É conciliável:</b> Caso esta opção esteja marcada, este
	tipo de transação fará parte da funcionalidade de conciliação. Para
	mais informações acesse o <a href="${pagePrefix}bookkeeping"><u>
	Arquivo de ajuda</u></a> da função de Contabilidade / conciliação. <br>
	<br>
	<li><b>É empréstimo:</b> (apenas habilitado para pagamentos de
	sistema para membros) Selecione esta caixa para indicar que este tipo
	de transação é um empréstimo. As configurações do empréstimo aparecerão
	abaixo, assim que você selecionar a caixa, para mais informações <a
		href="${pagePrefix}loans#make_loan_type"><u>clique aqui</u></a>. <br>
	<br>
	<li><b>Requer qualificação de transações:</b> Esta opção esta
	disponível apenas para pagamentos entre membros. Se esta opção é
	marcada, membros podem qualificar as transações efetuadas com este tipo
	de transação. Para mais informações sobre qualificações acesse a <a
		href="${pagePrefix}transaction_feedback">página de ajuda </a> de
	qualificações<br>
	Os seguintes elementos estão visíveis apenas se &quot;requer
	qualificação de transações&quot; está selecionado:
	<ul>
		<li><b>Tempo limite para qualificação:</b> Quando um pagamento é
		feito, o pagante (=comprador) tem um período máximo para definir uma
		qualificação. Este período pode ser especificado com esta
		configuração.
		<li><b>Tempo limite para réplica de qualificação:</b> O recebedor
		da qualificação (=vendedor) pode replicar esta qualificação. Este
		tempo limite pode ser definido com esta configuração.
		<li><b>Valor da qualificação quando expirado:</b> Uma vez
		expirado o período para qualificação, uma qualificação padrão será
		criada. Você pode especificar o valor padrão nesta configuração (isto
		costuma ser algo como &quot;Neutro&quot;).
		<li><b>Comentário da qualificação quando expirado:</b> Uma vez
		expirado o período para qualificação, uma qualificação padrão será
		criada. Você pode especificar um comentário padrão nesta configuração
		(isto costuma ser algo como &quot;Nenhuma qualificação
		adicionada&quot;).
	</ul>
</ul>
<hr class="help">

<a name="placeholders"></a>
<h3>Nomes de variáveis para campos de descrição</h3>
No caso de um pagamento de empréstimo e taxas periódicas, você pode usar
certos códigos para incluir dados na descrição de um <a
	href="#transaction_types"><u> tipo de transação</u></a> . Você pode
usar &quot;nomes de variáveis&quot; - eles serão substituídos por seus
valores correspondentes na descrição de uma eventual transação. <b>Pagamento
de empréstimo:</b>
<ul>
	<li><b>#loanAmount#:</b> Valor original do empréstimo
	<li><b>#loanTotalAmount#:</b> Valor do empréstimo com taxas
	(juros, taxas de conceção de empréstimos)
	<li><b>#parcelAmount#:</b> Valor da parcela do empréstimo, o
	empréstimo é dividido em um número fixo de parcelas.
	<li><b>#parcelNumber#:</b> Número da parcela. O empréstimo é
	dividido em um número fixo de parcelas, cada parcela tem seu número
	correspondente.
</ul>
Para mais informações sobre empréstimos, veja a <a
	href="${pagePrefix}loans"><u>ajuda de empréstimos</u></a>.
<br><br><b><a href="#account_fees"><u>Taxas periódicas</u></a> (=
Contribuições e taxas de liquidez):</b>
<ul>
	<li><b>#begin_date#:</b> Data de início (apenas no caso de <a
		href="#account_fees"><u> taxas de liquidez</u></a>)
	<li><b>#end_date#:</b> Data final (apenas no caso de taxa de
	liquidez)
	<li><b>#tax#:</b> = Valor
	<li><b>#freebase#:</b> Base livre, valor básico sobre o qual a
	taxa não é cobrada (apenas no caso de taxa de liquidez)
	<li><b>#volume#:</b> = Volume total transacionado (apenas no caso
	de taxa de liquidez)
	<li><b>#result#:</b> = Resultado
</ul>
<br><br><b><a href="#transaction_fees"><u>Taxas de transação</u></a></b>.
Observe que isto deve ser informado na descrição do tipo de transação da
taxa, e não na descrição da taxa.Note that these should be entered in
the description of the transfer type of the fee, not in the description
of the fee itself.
<ul>
	<li><b>#fee#</b>: valor da taxa, o qual uma porcentagem (incluindo
	o sinal %) no caso de uma taxa com porcentagem, ou o valor no caso de
	uma taxa fixa.
	<li><b>#fee_amount#</b>: O mesmo.
	<li><b>#member#</b>: O membro pagante.
	<li><b>#transfer#</b>: O valor total da transação original que
	gerou esta taxa.
	<li><b>#original_amount#</b>: o mesmo.
	<li><b>#amount#</b>: Uma apresentação localizada so valor a ser
	pago nesta taxa. No caso de uma taxa fixa isto é o mesmo que #fee#, mas
	com em taxas com porcentagem, isto é #fee# X vezes valor da transação.








	
	<li><b>#d_rate#</b>: O <a href="#d-rate"><u>Índice-D</u></a> do
	valor da transação.
	<li><b>#a_rate#</b>: O <a href="#a-rate"><u>Índice-A</u></a> do
	valor da transação.
</ul>
<hr class="help">

<br><br><A NAME="payment_fields_list"></A>
<h3>Campos customizados para pagamentos</h3>
É possível adicionar campos customizados para um tipo de transação
(pagamento) da mesma forma que você pode definir um campo para o perfil
de um membro ou anúncios. O campo do pagamento será visível apenas para
o tipo de transação atual (a que você esta editando no momento).<br>
Você pode criar os seguintes tipos de campos customizados para
pagamentos:
<ul>
	<li><b>Inserir novo campo customizado:</b> Com esta opção você
	pode definir um campo customizado para um tipo de pagamento, da mesma
	forma que você pode fazer isso para o perfil de um membro (por
	exemplo).
	<li><b>Víncular campos customizados existentes:</b> Com esta opção
	você pode víncular o tipo de pagamento com um campo customizado
	existente. Mais informações sobre isto pode ser encontrada em víncular
	campo customizado de pagamento.
</ul>
<hr class="help">

<br><br><A NAME="payment_fields_link"></A>
<h3>Víncular campo customizado de pagamento</h3>
É comum em sistemas mais complexos que transações sejam
&quot;encaminhadas&quot;. Isto normalmente é feito usando uma taxa de
transação que cobra 100% e possui como destino uma terceira conta. Neste
caso você possívelmente pode querer usar o mesmo campo customizado para
ambos pagamentos. Você pode fazer isso criando o campo customizado no
tipo de transação de origem, e no segundo tipo de transação (gerado)
crie um vínculo com o campo customizado da origem (usando a opção &quot;
Víncular campo customizado existente&quot;)
</ul>
<hr class="help">

<br><br><A NAME="authorized_payment_levels"></A>
<h3>Níveis de autorizações</h3>
Esta função permitirá definir níveis de autorizações para um tipo de
pagamento que necessita <a href="${pagePrefix}payments#authorized"><u>
autorização</u></a>.
<br><br>Existem vários níveis possíveis de autorização, e pode existir
mais de um nível de autorização por tipo de pagamento, significando que
varias pessoas precisarão autorizar um pagamento (possivelmente) com
critérios diferentes. Ambos membros e autorizador (corretor ou
administrador) terão acesso a uma lista com os pagamentos pendentes que
necessitam autorização.
<br><br>Esta janela mostrará todos os níveis de autorização para o tipo
de transação. Se nenhum esta disponível você terá de clicar em &quot;
Novo nivel de autorização&quot;, porque você terá de definir pelo menos
um nível de autorização para cada tipo de pagamento autorizado. Quando
você adiciona um novo nivel ele é mostrado na lista.<br>
Se os niveis já estão definidos, você tem as seguintes opções:
<ul>
	<li><img border="0" src="${images}/edit.gif" width="16"
		height="16"> Clique no ícone para modificar o nivel se
	autorização do pagamento.
	<li><img border="0" src="${images}/delete.gif" width="16"
		height="16"> Clique no ícone para apagar o nivel de autorização
	do pagamento.
</ul>
<br><br>Se você desmarcar a opção de autorização no tipo de transação,
significa que todas as autorizações do tipo de transação estão
desativadas. Os níveis de autorizações irão desaparecer, mas continuarão
no histórico. Se a caixa de autorização é marcada novamente na
configuração do tipo de transação, os níveis de autorização voltarão a
aparecer.
<br><br>Se um pagamento autorizado gera outros pagamentos ( ex.
empréstimos com parcelas, taxas de transações, impostos, etc) todo o
grupo de pagamento pode ser autorizado de uma forma geral (e o valor
total bloqueado / reservado até a autorização).
<hr class="help">

<A NAME="edit_authorization_level"></A>
<h3>Editar níveis de autorização</h3>
Nesta janela você pode definir o <a href="#authorized_payment_levels"><u>
nivel de autorização</u></a>. Você pode definir um máximo de cinco níveis de
autorização. Todos níveis tem um &quot;tipo&quot; de autorização. Isso
depende no nível de autorização de quais tipos de autorizações estão
disponíveis.
<ul>
	<li><b>Vendedor:</b> Quando o tipo de autorização é
	&quot;Vendedor&quot; significa que o (destinatário) membro que irá
	receber o pagamento terá que autorizar esse pagamento primeiro. Quando
	o vendedor autoriza o pagamento (clicando &quot;aceitar&quot; na lista
	de pagamentos pendentes de autorização) o valor será transferido e o
	estado passará de &quot;pendente&quot; para &quot;autorizado&quot;.<br>
	Este tipo de autorização é bastante raro. Nós sugerimos não usar esta
	opção caso <a href="${pagePrefix}invoices"><b>faturas
	eletrônicas</b></a> estejam em uso no sistema. Faturas eletrônicas oferecem uma
	função semelhante.
	<li><b>Comprador:</b>Esta opção esta disponível apenas se existir
	um primeiro nível de autorização cujo tipo seja &quot;vendedor&quot;.<br>
	Isso oferece um nível extra (opcional) de autorização após a
	autorização do vendedor. Caso este tipo seja configurado o pagamento
	permanecerá como pendente após a autorização do vendedor. Agora o
	comprador (pagante) terá que aceitar o pagamento. Quando feito o valor
	será transferido.
	<li><b>Corretor / Administrador:</b> Este nível de autorização é
	definido como um primeiro nível, ou segundo após o nível do tipo
	vendedor. Isso significa que o <a href="${pagePrefix}brokering"><u>corretor</u></a>
	do membro que fez o pagamento terá que autorizar e também opcionalmente
	um administrador.<br>
	Você pode selecionar o grupo de administradores que podem autorizar os
	pagamentos junto com os corretores. Não existe prioridade sobre quem
	vai autorizar, tanto o corretor quanto o administrador pode autorizar.










	
	<li><b>Administrador:</b> Este nível de autorização esta
	disponivel como primeiro nível, segundo nível após corretor /
	administrador, terceiro nível após comprador e qualquer outro nível do
	tipo de administrador.<br>
	Significa que você pode encadear vários níveis com níveis de
	autorização do tipo administrador e diferentes valores e grupos. Você
	terá de selecionar o grupo de administrador que pode autorizar o
	pagamento.
</ul>
Você terá que especificar um valor para cada tipo e nível de
autorização. Se você quiser que o pagamento tenha sempre que ser
autorizado, você pode colocar zero no campo valor. Se você colocar um
valor, como 1000, significa que se o membro pagar mais que 1000 durante
24 horas, o valor excedente necessitará de autorização. <br>
O valor de um nível de autorização pode ter também o mesmo valor que seu
nivel anterior ou um valor maior.
<hr class="help">


<A NAME="payment_filters"></A>
<h2>Filtros de pagamentos</h2>
É possível agrupar tipos de transações em &quot;Filtros de
pagamentos&quot;. Estes filtros permitem agrupamentos convenientes de
certos tipos de transações relacionadas, por exemplo para a janela de
extrato de transações ou para estatísticas. Por exemplo: diferentes
tipos de contribuições e outros pagamentos específicos da comunidade
podem ser agrupados em um filtro com o nome &quot;Pagamentos da
comunidade&quot;. Os filtros de pagamentos também podem ser usados para
criar relatórios customizados. Para o administrador os filtros de
pagamentos oferecem uma boa ferramenta para acompanhar pagamentos e
obter relatórios específicos.

<i>Onde encontrar.</i><br>
Filtros de pagamentos estão sempre relacionados com tipos de contas,
então eles devem ser acessados através do &quot;Menu: Contas >
Gerenciamento de contas&quot; > selecionar um tipo de conta e clicar no
ícone de edição <img border="0" src="${images}/edit.gif" width="16"
	height="16">&nbsp;. Além de outras janelas, existirá uma janela
específica com filtros de pagamentos associados.
<hr>

<A NAME="payment_filter_search"></A>
<h3>Lista de filtros de pagamentos</h3>
Esta janela lista os <a href="#payment_filters"><u>filtros de
pagamentos</u></a> associados com a conta.
<ul>
	<li><img border="0" src="${images}/edit.gif" width="16"
		height="16"> Clique no ícone para modificar o filtro de
	pagamento.
	<li><img border="0" src="${images}/delete.gif" width="16"
		height="16"> Clique no ícone se você quiser apagar o filtro de
	pagamento.
</ul>
Se você quiser criar um novo filtro, você terá que clicar no botão
abaixo da janela, chamado &quot;Inserir novo filtro de pagamento&quot;.
<hr class="help">

<A NAME="payment_filter_details"></A>
<h3>Modificar / inserir filtro de pagamento</h3>
Aqui você pode modificar ou inserir um novo <a href="#payment_filters"><u>filtro
de pagamento</u></a>. Os seguintes campos podem ser definidos:
<ul>
	<li><b>Nome</b>: O nome do filtro.
	<li><b>Descrição</b>: A descrição do filtro.
	<li><b>Exibir no histórico da conta</b>: Se esta caixa for
	marcada, o filtro aparecera no histórico da conta (da conta
	selecionada).
	<li><b>Exibir em relatórios</b>: Se você marcar a caixa
	&quot;exibir em relatórios&quot; a <a href="${pagePrefix}reports"><u>função
	de relatório</u></a> incluirá relatórios sobre este filtro. Mostrará a soma dos
	valores de todos os tipos de transações relacionados ao filtro de
	pagamento, também estará disponivél para o <a
		href="${pagePrefix}statistics"><u>modulo de estatísticas</u></a>.<br>
	Você pode criar filtros de pagamentos apenas para a funcionalidade de
	relatórios. Para fazer isso você terá que desmarcar a caixa
	&quot;Exibir no histórico da conta&quot;.
	<li><b>Tipos de transações</b>: Aqui você deve escolher os <a
		href="#transaction_types"><u>tipos de transações</u></a> que serão
	incluidos no filtro.
	<li><b>Visibilidade de grupo</b>: Aqui você pode selecionar os
	grupos que estarão aptos a ver os filtros de pagamentos. Deste modo é
	possível criar diferentes filtros para grupos diferentes. Por exemplo o
	grupo de corretores poderia ter um filtro de &quot;Pagamentos de
	comissões&quot;. Administradores normalmente necessitarão de filtros
	mais específicos e grupos de membros terão filtros mais básicos, como
	por exemplo: transações de comércio - taxas & impostos.
</ul>
<hr class="help">

<A NAME="transaction_fees"></A>
<h2>Taxas de transações</h2>
Um tipo de transação pode conter uma ou mais &quot;Taxas de
transações&quot;. Sempre que um usuário faz um pagamento com o tipo de
transação, e uma taxa esta configurada, a taxa pode ser cobrada
(dependendo do critério na configuração da taxa). Uma taxa de transação
comum poderia ser uma taxa que é cobrada toda vez que o membro faz um
pagamento com o tipo de transação &quot;Comércio&quot;. Existe várias
formas de calcular uma taxa (fixa, porcentagem, etc) e definir quem é
cobrado.<br>
<br>
Um exemplo de taxa é a taxa de transação &quot;comum&quot; no comércio.
Sempre que uma transação de comérico é efetuada, a taxa será aplicada
(caso configurada). O detalhe da transação da taxa irá exibir os
detalhes da transação original.<br>
Pode existir mais de uma taxa anexada em uma transação. Uma taxa de
transação pode ter muitas opções de configuração. Taxas de conta podem
ter sentidos de pagamentos diferentes (membro para membro, membro pra
sistema, sistema para membro, etc) e existe varios caminhos para definir
quem será cobrado e quem irá receber a taxa. Por causa desses muitos
caminhos as taxas nem sempre são usadas como "taxas". Por exemplo é
possível usar uma taxa para remeter e distribuir pagamentos pra outras
contas (usando a opção de porcentagem). Outra funcionalidade que pode
ser alcançada com as taxas de transação são os índices D e A. Isto só
irá aparecer caso os índices D e A estejam configurados na moeda em uso.
Os índices D e A são quase um módulo aparte, mas usam as taxas de
transação e portanto devem ser testados com este módulo. Um exemplo
típico de taxa é a &quot;ordinária&quot; taxa de transação de comércio.
Sempre que uma transação de &quot;comércio&quot; é efetuada a taxa é
aplicada (se configurada). Como esta taxa também é um pagamento, ela tem
seu próprio tipo de transação, e deste modo ela será cobrada
separadamente e será mostrada como um tipo de pagamento diferente no
histórico de transações. O detalhe da transação de uma taxa, mostrará o
pagamento original (gerador) da taxa.

<i>Onde encontrar.</i><br>
Uma taxa de transação sempre &quot;pertencerá&quot; a um <a
	href="#transaction_types"><u>tipo de transação</u></a>; portanto a
configuração de uma taxa de transação é localizada dentro da
configuração de um tipo de transação: acesse &quot;Menu: Contas >
Gerenciamento de contas&quot;, selecione uma conta (através do ícone de
edição), vá a janela do tipo de transação, selecione um tipo de
transação (através do ícone de edição). Aqui você encontrará uma janela
com as taxas de transações associadas.
<hr>

<A NAME="transaction_fee_search"></A>
<h3>Taxas de transações</h3>
Esta janela mostra uma lista com as <a href="#transaction_fees"><u>
taxas de transações</u></a> associadas ao <a href="#transaction_types"><u>tipo
de transação</u></a>.
<ul>
	<li><e img border="0" src="${images}/edit.gif" width="16"
		height="16"> Clique no ícone para modificar uma taxa de transação.










	
	<li><img border="0" src="${images}/delete.gif" width="16"
		height="16"> Clique no ícone se você quiser apagar uma taxa de
	transação.
</ul>
Se você quiser criar uma nova taxa de transação, você terá de clicar no
botão chamado &quot;Inserir nova taxa de transação&quot; localizado
abaixo da janela.
<hr class="help">


<A NAME="transaction_fee_details"></A>
<h3>Modificar / inserir uma taxa de transação</h3>
Esta janela é usada para modificar <a href="#transaction_fees"><u>
taxas de transações</u></a> existentes, ou definir uma nova.<br>
A taxa de transação sera gerada quando uma transação específica ocorrer.
Contudo, taxas são também transações, e assim elas também precisarão
tipos de transações para elas mesmas. Antes de configurar uma nova taxa,
você terá que criar primeiro um <a href="#transaction_types"><u>tipo
de transação</u></a> para a taxa.
<br><br><b>Campos:</b>
<ul>
	<li><b>Tipo de transação</b>: Este é o tipo de transação no qual a
	taxa será gerada. Para uma taxa nas transações de comércio, você
	tipicamente selecionaria algo como &quot;Comércio&quot;.<br>
	<br>
	<li><b>Nome</b>: Nome da taxa.<br>
	<br>
	<li><b>Descrição</b>: Descrição da taxa. Observe que esta
	descrição não é a que o cliente/membro irá ver nos detalhes da sua
	transação; Para esta descrição veja o campo de descrição do tipo de
	transação pertencente a esta taxa.<br>
	<br>
	<li><b>Habilitada</b>: A taxa estará ativa quando marcado. Caso
	contrário, a taxa não será cobrada, e o sistema se comportará como se
	ela não existisse.<br>
	<br>
	<li><b>Quem será cobrado</b>: Aqui você define quem será cobrado.
	Pode ser o comprador, vendedor, uma conta de sistema ou um membro fixo.<br>
	<br>
	<li><b>Quem receberá</b>: Aqui você pode definir quem recebera a
	taxa. Pode ser o comprador, vendedor, uma conta de sistema ou um membro
	fixo.<br>
	<br>
	<li><b>Permitir qualquer conta</b>: Normalmente, no próximo campo
	(&quot;tipo de transação gerado&quot;) apenas tipos de transações
	relevantes na mesma moeda são mostrados. Se você marcar esta caixa de
	seleção, não terá limitação de moeda na qual a taxa de transação sera
	aplicada: todos os tipos de transações são mostrados, mesmo em outra
	moeda.<br>
	<br>
	<li><b>Tipo de transação gerado</b>: Aqui você define qual tipo de
	transação sera gerado - o tipo de transação da própria taxa. É comum
	criar tipos de transações específicos para isso. Assim você pode
	filtrar as taxas (ex. no histórico das contas: taxas & impostos). O
	banco de dados padrão vem com uma taxa de transação e um tipo de
	transação para esta taxa. Uma taxa em transações de comércio poderá
	normalmente se chamada de &quot;taxa de transação&quot;.<br>
	NOTA é absolutamente necessário que você crie este tipo de transação,
	antes de estar apto a criar a taxa (veja nota acima).<br>
	<br>
	<li><b>Tipo de cobrança</b>: Isto especifica como a taxa sera
	calculada. Os seguintes tipos estão disponíveis:
	<ul>
		<li><b>Valor fixo</b>: A taxa tem sempre o mesmo valor. Você pode
		informar este valor no próximo campo chamado &quot;valor
		cobrado&quot;.
		<li><b>Porcentagem</b>: A taxa sera cobrada com uma porcentagem
		da transação geradora. Voce pode informar a porcentagem no próximo
		campo chamado &quot;valor cobrado&quot;.
		<li><b>Índice-A</b>: A taxa será cobrada com base no <a
			href="#a-rate"><u>Índice-A</u></a>. Quando esta opção é escolhida,
		uma série de opções extras de configurações serão exibidas.
		<li><b>Índice-D</b>: A taxa será cobrada com base no <a
			href="#d-rate"><u>Índice-D</u></a>.
		<li><b>Misto de Índice-A e D</b>: A taxa será cobrada com base em
		ambos <a href="#rate_mix"><u>índices A e D</u></a>. Quando esta opção
		é escolhida, uma serie de opções extras de configurações serão
		exibidas.
	</ul>
	<br>
	<br>
	<li><b>Valor cobrado</b>: Aqui você pode informar o valor da taxa
	(no caso de ter escolhido taxa "fixa" no campo anterior), ou você pode
	preencher a porcentagem (no caso de ter escolhido "porcentagem" no
	campo anterior). Se você escolheu qualquer outra opção este campo não
	será visível.<br>
	<br>
	<li><b>Dedução</b>: Este campo só é visível no caso de um "tipo de
	cobrança" "fixo" ou "porcentagem".<br>
	Com esta configuração você define se a taxa de transação será calculada
	como um pagamento "extra" baseado no pagamento original ou se ela será
	"deduzida" do valor total "original" do pagamento.<br>
	Isto pode ser explicado com um simples exemplo. Se um pagamento de 100
	é feito e existe uma taxa de 3 que é definida para ser deduzida do
	valor total, os seguintes pagamentos serão gerados:
	<ul>
		<li>Transação principal (geradora): 97
		<li>Taxa: 3
	</ul>
	O caso acima é um tanto raro. Usualmente taxas não são deduzidas do
	valor total. Um exemplo com os mesmo valores com uma taxa não deduzida:
	<ul>
		<li>Transação principal (geradora): 100
		<li>Taxa: 3
	</ul>
	<br>
	<br>
	<li><b>Parâmetros do Índice A</b>: Este conjunto de campos só é
	visível caso a opção "Índice-A" ou "Misto de Índice A e D" tenha sido
	escolhido no campo "Tipo de cobrança". Para explicações <a
		href="#a-rate_params"><u>clique aqui</u></a>. <br>
	<br>
	<li><b>Condições de aplicabilidade</b>: Aqui você pode definir sob
	quais condições a taxa sera aplicada. A taxa sera aplicada apenas se as
	condições forem satisfeitas. As condições podem ser combinadas.
	<ul>
		<li><b>Valor é maior ou iqual</b>: A taxa só sera cobrada se o
		valor da transação geradora for maior ou igual ao valor especificado.








		
		<li><b>Valor é menor ou iqual</b>: A taxa só sera cobrada se o
		valor da transação geradora for menor ou igual ao valor especificado.








		
		<li><b>De todos os grupos</b>: Se de todos os grupos for
		selecionado, a taxa sera aplicada aos membros de qualquer grupo, que
		forem considerados como pagador da transação geradora. Caso você
		deseje aplicar a taxa apenas para grupos específicos, você deve
		desmarcar esta opção, e escolher os grupos na caixa de seleção
		múltipla que irá aparecer.
		<li><b>Para todos os grupos</b>: Igual ao itém anterior, mas para
		membros que forem considerados como recebedores da transação geradora.








		
	</ul>
</ul>
<hr class="help">

<A NAME="a-rate_params"></A>
<h3>Parâmetros do índice-A</h3>
Este conjunto de campos em <a href="#transaction_fee_details"><u>modificar/nova
taxa de transação</u></a> permite que você defina a relação entre o <a
	href="#a-rate"><u>Índice-A</u></a> e a taxa de transação que
normalmente é usada para converter unidades para moeda corrente. A idéia
é permitir uma taxa de conversão baixa para unidades antigas (assim,
unidades com um índice-A elevado).<br>
As seguintes opções estão disponíveis:
<ul>
	<li><b>Relação entre taxa e índice-A</b>: Esta opção tem duas
	opções disponíveis:
	<ul>
		<li><b>Linear</b>: A relação é linear inclinada para baixo: Um
		índice-A de 0 irá resultar em uma taxa com porcentagem alta; Índices-A
		altos resultarão em taxas com porcentagem baixas.
		<li><b>Assintótico</b>: A relação é inclinada para baixo para uma
		assimptota horizontal: Um índice-A de 0 resultará em uma taxa com
		porcentagem alta: Índices-A elevados resultarão em taxas com
		porcentagens baixas mas este efeito é assintótico decrescendo para um
		determinado valor mínimo.
	</ul>
	<li><b>Porcentagem máxima</b>: Esta é a porcentagem máxima
	possível para a taxa, a qual será cobrada para um índice-A de 0 dias.
	Assim, esta taxa sera cobrada caso alguém queira converter para moeda
	corrente um conjunto de unidades no segundo após a sua criação. Na
	documentação modelo este parâmetro é chamado "H".
	<li><b>Valor em uma assimptota infinita</b>: Este campo só é
	visível no caso de um relacionamento assimptótico. Esta é a porcentagem
	da taxa em valores positivos infinitos para o índice-A. O Gráfico da
	relação entre a porcentagem da taxa e o índice-A desce para esta
	assimptota. Note que este valor também pode ser negativo, isto é
	meramente usado para determinar a forma da curva. O campo de "valor
	mínimo" determina a porcentagem mínima real da taxa. Na documentação
	modelo este parâmetro é chamado "Infinito-F".
	<li><b>0% no Índice-A = ...</b>: Este parâmetro determina para
	qual valor do índice-A a porcentagem da taxa alcança 0%. Assim isto
	determina onde a curva cruza o eixo-X. Este campo não é sempre visível.
	Ele é invisível nos seguintes casos:
	<ul>
		<li>O "Tipo de cobrança" é "Misto de índice A e D".
		<li>No caso de uma relação assimptotica, onde o "valor em uma
		assimptota infinita" é positivo.
	</ul>
	Na documentação modelo este parâmetro é chamado &quot;A (F=0)&quot;.
	<li><b>Valor após 1 dia</b>: No caso de uma relação assimptótica,
	onde o "Valor em uma assimptota infinita" é positivo, não existe pontos
	onde a curva cruza o eixo-X, por razões obvias. Neste caso, precisamos
	outro ponto na curva para determinar a forma da curva assimptótica. A
	porcentagem após 1 dia ( onde o índice-A é exatamente 1.0 dias ) é
	usada para isso. Assim este campo só é visível nas seguintes situações:
	<ul>
		<li>O "Tipo de cobrança" é "Índice-A".
		<li>No caso de uma relação assimptótica.
		<li>&quot;Valor em uma assimptota infinita" é positivo.
	</ul>
	Na documentação modelo este parâmetro é chamado &quot;F1&quot;.
	<li><b>Alcança 0% após x % da garantia ter passado</b>: Este campo
	é visível apenas no caso de um "tipo de cobrança" "misto de índice-A e
	D". Neste caso isto toma o lugar do parâmetro "&quot;0% de Índice-A =
	...&quot;. O ponto onde a curva cruza o eixo-X é determinado por este
	parâmetro. Isto é expresso como um índice-A relativo ao período total
	da garantia. Assim, por exemplo, quando 50% do período total da
	garantia tenha passado, a porcentagem da taxa alcança 0%.<br>
	Na documentação modelo este parâmetro é chamado &quot;G (F=0)&quot;.
	<li><b>Valor mínimo</b>: Este parâmetro é sempre visível. Ele
	permite que você especifique um valor mínmo para a taxa. Caso a
	porcantagem calculada da taxa (com base em todos os outros parãmetros
	informados neste formulário) seja menor que este valor mínimo, a
	porcentagem da taxa é definida como sendo o valor mínimo. Um valor
	comum para este campo é 0%.<br>
	Na documentação modelo este parâmetro é chamado &quot;F-mínmo&quot;.
</ul>
<hr class="help">

<A NAME="account_fees"></A>
<h2>Taxas de conta</h2>
&quot;Taxas de contas&quot;, frequentemente referenciadas como
&quot;contribuições&quot; são periodicamente agendadas ou manualmente
cobradas por uma administrador. Taxas de conta estão relacionadas com
uma conta, e podem ser ativadas para um ou mais grupos de membros.
Quando uma taxa de conta é cobrada, todos os grupos de membros, que
foram selecionados na configuração da taxa, serão cobrados. No entanto,
embora a palavra "taxa" sugira que membros estão pagando, uma taxa de
conta pode também ser configurado como parte pagante uma conta de
sistema, e estes membros receberam a taxa. Uma taxa de conta típica é um
pagamento de contribuição mensal dos membros para uma conta de sistema
(mas pode ser ao contrário também). Outro exemplo é &quot;sobre
volume&quot; ou &quot;taxa de liquidez&quot;, onde usuários pagam sobre
seus saldos positivos através do tempo, como uma espécie de
&quot;interesse negativo&quot;. A idéia por trás disso é que as pessoas
sejam estimuladas as usar seus saldos, ou invés de deixa-los em suas
contas.

<i>Onde encontrar.</i><br>
Como uma taxa de conta esta associada a um tipo de conta, esta
configuração é feita através do gerenciamento de conta: &quot;Menu:
Contas > Gerenciamento de contas&quot;, escolha um tipo de conta e
clique no ícone de edição <img border="0" src="${images}/edit.gif"
	width="16" height="16">&nbsp;.
<br><br>A administração e visão geral das taxas de contas é feita através
da página de visão geral das taxas de contas (&quot;Menu: Contas > Taxas
de contas&quot;). Nesta página você obtém uma visão geral de todas as
taxas de contas e seus estados; você também pode cobrar manualmente uma
taxa.
<hr>

<A NAME="account_fee_overview"></A>
<h3>Taxas de conta</h3>
A página de visão geral das taxas de contas, mostra todas as <a
	href="#account_fees"><u> taxas de contas</u></a> que estão ativadas em
qualquer tipo de conta.
<br><br>A janela mostrará uma lista com as taxas de contas ativas
(habilitadas), a última e a próxima data de execução e o seu estado.
(&quot;falhou&quot;, &quot;concluída&quot;).
<br><br>Cada taxa de conta pode ser automática (agendada) ou manual.
Taxas de contas manuais são cobradas apenas quando o link &quot;Cobrar
agora&quot; é acessado. Enquanto a taxa de conta é cobrada é possível
cancelar a cobrança, clicando no link &quot;cancelar&quot;. <br>
Uma taxa cancelada ou uma taxa que falhou por causa de problemas
técnicos tera sempre a sua cobrança completamente &quot;revertida&quot;.
Isto significa que você nunca terá cobranças de taxas concluídas
parcialmente. Após a cobrança da taxa ter sido concluída com sucesso a
data de última execução sera definida e uma entrada sera mostrada na
lista de <a href="#account_fee_history"><u>histórico de taxas de
contas</u></a> (janela abaixo).
<br><br>Se uma taxa de conta falhou, um alerta sera mostrado nos alertas
de sistema e o estado mostrará que aquela taxa falhou. Uma taxa falhada
pode ser concluída clicando no link &quot;repetir cobrança&quot; no
histórico de taxas de conta.
<hr class="help">

<a name="account_fee_history"></a>
<h3>Histórico de taxas de conta</h3>
Esta página mostra no histórico todas as cobranças de <a
	href="#account_fees"><u>taxas de contas</a></u> realizadas. Se uma
cobrança de taxa de conta falhou, o estado mostrará que a cobrança
daquela taxa falhou. Uma taxa falhada pode ser concluída clicando no
link &quot;repetir cobrança&quot;.
<hr class="help">

<A NAME="account_fee_list"> </A>
<h3>Taxas de conta</h3>
Esta janela mostra uma lista com todas as <a href="#account_fees"><u>
taxas de conta</u></a> configuradas (tanto ativadas quanto desativadas) para
este tipo de conta.
<ul>
	<li><img border="0" src="${images}/edit.gif" width="16"
		height="16"> Clique no ícone para modificar a taxa de conta.
	<li><img border="0" src="${images}/delete.gif" width="16"
		height="16"> Clique no ícone se você quiser apagar uma taxa de
	conta.
</ul>
Para adicionar uma nova taxa de conta clique em &quot;Inserir nova taxa
de conta&quot; abaixo da lista.
<hr class="help">

<A NAME="account_fee_details"></A>
<h3>Modificar / Inserir uma taxa de conta</h3>
Nesta janela você pode configurar uma <a href="#account_fees"><u>taxa
de conta</u></a> (nova ou existente). Note que uma taxa é em si é uma transação;
então ela necessitará um <a href="#transaction_types"><u>tipo de
transação</u></a>. Você precisará criar este tipo de transação antes de poder
criar e configurar a taxa de conta.
<br><br>As seguintes opções de configurações estão disponíveis:</b>
<ul>
	<li><b>Nome</b>: O nome da taxa de conta.<br>
	<br>
	<li><b>Descrição</b>: A descrição da taxa de conta.<br>
	<br>
	<li><b>habilitado</b>: A taxa de conta sera cobrada apenas se esta
	caixa for selecionada. Não importa se a taxa for agendada ou manual, a
	cobrança não sera possível se esta caixa não for selecionada.<br>
	<br>
	<li><b>Primeiro período cobrado inicia em:</b> escolha aqui quando
	o primeiro periodo de cobrança deve iniciar. Você pode selecionar a
	data usando o ícone de calendário <img border="0"
		src="${images}/calendar.gif" width="16" height="16">&nbsp;. <br>
	Terminado, você pode verificar a próxima cobrança no &quot;Menu: Contas
	> taxas de contas&quot;. <br>
	<br>
	<li><b>Modo de cobrança</b>: Existem cinco tipos de cobrança
	possível:
	<ul>
		<li><b>Valor fixo</b>: O valor fixo pode ser uma contribuição (do
		membro para uma conta de sistema) ou uma taxa única ou um pagamento
		agendado de uma conta de sistema para um grupo de membros.
		<li><b>Porcentagem sobre volume positivo</b>: A porcentagem sobre
		volume positivo é calculado da mesma maneira que os juros. Atualmente
		quando a direção do pagamento é definida (ver abaixo) como
		&quot;sistema para conta de membro&quot; isso será juros. Se a direção
		for &quot;conta de membro para sistema&quot; a taxa será convertida em
		juros (sobre volume).<br>
		O valor devido é baseado no período e no valor. Um juros/sobre volume
		de 1% cobrado sobre o balanço do membro que tem 100 unidades durante
		todo o período será 1 unidade.
		<li><b>Porcentagem sobre volume negativo</b>: A porcentagem sobre
		volume negativo funciona da mesma maneira, mas apenas sobre balanços
		negativos.
		<li><b>Porcentagem sobre balanço positivo</b>: A taxa de
		porcentagem do balanço não calcula o volume (tempo + balanço), apenas
		o balanço. Quando a taxa é cobrada (manual ou agendada) a cobrança
		levará em conta apenas o balanço no momento da cobrança. Isto
		significaria que no exemplo acima o membro diminuiria seu balanço a
		zero apenas antes da cobrança da taxa e não sera cobrado em nada. A
		porcentagem sobre balanço pode causar um comportamento não natural em
		um sistema mas isto é uma questão de metodologia.
		<li><b>Porcentagem sobre balanço negativo</b>: A porcentagem
		sobre balanço negativo funciona da mesma forma porém apenas com
		balanços negativos.
	</ul>
	Note que o tipo de cobrança não pode ser alterado em taxas existentes;
	Ele só pode ser definido em novas taxas de conta.<br>
	<br>
	<li><b>Tolerância</b>: Este item é visível apenas quando no
	&quot;tipo de cobrança&quot; o valor &quot;volume positivo &quot; é
	selecionado. A tolerância é uma configuração que pode ser usada para
	evitar que membros que tenham recebido um pagamento de sistema (ex.
	empréstimo) e gastaram em um período curto (mesmo convertendo ou
	comprando alguma coisa) sejam cobrados sobre o volume deste valor no
	período. Por exemplo, se um membro recebe 100 unidades e gasta elas em
	24 horas, o membro não será cobrado nesse tempo/valor se o período de
	tolerância for definido em 24 horas. Se , no mesmo caso, o membro gasta
	as 100 unidades em dois dias, ele será cobrado pelo período inteiro dos
	dois dias (e não por um).<br>
	<br>
	<li><b>Direção do pagamento</b>: Isto define a direção da taxa de
	conta, de membro para sistema ou no sentido contrário.<br>
	<br>
	<li><b>Tipo de transação gerado</b>: A taxa de conta necessitará
	de um tipo de transação. A transação será do tipo de transação
	escolhido.<br>
	<br>
	<li><b>Valor</b>: No caso de você escolher &quot;valor fixo&quot;
	no &quot;tipo de cobrança&quot;, este será o valor com o qual a taxa
	será cobrada. Caso contrário, este será a porcentagem cobrado do volume
	ou do balanço.<br>
	<br>
	<li><b>Base livre</b>: Esta configuração não será mostrada se você
	escolher &quot;valor fixo&quot; no &quot;modo de cobrança&quot;. A
	porcentagem da taxa será calculada somente acima do valor da base
	livre.<br>
	No exemplo mencionado anteriormente:
	<ul>
		<li><b>Modo de cobrança:</b> &quot;porcentagem sobre volume
		positivo&quot;
		<li><b>Valor:</b> 1%
		<li><b>Base livre:</b> 40
		<li><b>Volume na conta do membro:</b> 100 Unidades durante todo o
		período.
	</ul>
	Neste caso sem a base livre (base livre = 0) a taxa cobrada seria 1
	unidade; por causa da base livre, as primeiras 40 unidades não serão
	cobradas, então a taxa cobrada será 0,60 unidades.<br>
	<br>
	<li><b>Enviar fatura eletrônica</b>: Este item é visível apenas se
	a direção da taxa for de &quot;membro para sistema&quot;.<br>
	Isso determina oque acontecera se caso o membro que foi cobrado não
	tenha crédito suficiente para pagar a taxa de conta. As seguintes
	opções estão disponíveis:
	<ul>
		<li><b>Apenas quando o membro não possui crédito:</b> Nesse caso
		apenas os membros que não tiverem créditos receberão uma fatura
		eletrônica; O restante será cobrado normalmente.
		<li><b>Nunca (Pode ser que a conta do membro fique negativa):</b>
		Neste caso todos os membros serão cobrados e todos pagarão, mesmo que
		isso faça com que sua conta fique abaixo do limite de crédito.
		<li><b>Sempre (não cobra os membros automaticamente):</b> Neste
		caso a taxa de conta não cobrará nenhum membro, apenas enviará faturas
		eletrônicas, não importa se os membros tenham atingido seus limites de
		crédito.
	</ul>
	<br>
	<br>
	<li><b>Modo de cobrança</b>: Aqui você pode definir se a taxa de
	conta sera agendada ou cobrada manualmente. Note que isto não pode ser
	mudado uma vez que a taxa foi criada. Só pode ser definido para novas
	taxas.<br>
	Se cobrada manualmente, um administrador precisará iniciar a cobrança
	da taxa a partir da janela de <a href="#account_fee_overview"><u>
	visão geral das taxas de contas</u></a>. Se o modo de cobrança for
	&quot;Agendado&quot; a taxa será cobrada automaticamente no horário
	configurado. Neste caso você tera as seguintes opções extras:
	<ul>
		<li><b>Periódica</b>: Este é o intervalo de período que a taxa
		será cobrada. Por exemplo, todo mês ou todo ano.<br>
		Por exemplo: Se o período é definido para&quot;3&quot;
		&quot;meses&quot; a taxa de conta sera cobrada a cada 3 meses. Se o
		período for definido em &quot;0&quot; a taxa será cobrada apenas uma
		vez.
		<li><b>Dia</b>: Dia do mês ou semana quando a taxa será cobrada.
		Obviamente isso não estará visível se você escolher um período base
		diário.
		<li><b>Hora</b>: A hora (1-24) que a taxa de conta será cobrada.










		
	</ul>
	<br>
	<br>
	<li><b>Grupos</b>: Aqui você seleciona os grupos que serão
	cobrados ou que receberão a taxa de conta.
</ul>
<hr class="help">

<A NAME="credit_limit"></A>
<h3>Limite de crédito pessoal</h3>
<br><br><i>Onde encontrar.</i><br>
O limite de crédito pessoal pode ser acessado desde as <a
	href="${pagePrefix}profiles#accounts_actions"><u> ações de
conta do membro</u></a> (janela abaixo do perfil do membro).
<br><br>Com esta função, um limite de crédito individual, pode ser
definido para um membro. No momento em que a conta do membro é criada,
ela receberá o limite padrão de crédito, que é configurado nas <a
	href="${pagePrefix}groups#manage_groups"><u> configurações do
grupo</u></a>. <br>
Com esta janela você pode exceder esse limite de crédito do grupo. Você
pode definir aqui o limite superior e o inferior individualmente para um
membro, para todas as contas. Quando o membro atinge o limite de crédito
inferior ele não poderá mais fazer pagamentos.
<br><br>O limite superior de crédito é uma função raramente usada. Quando
o membro atinge o limite superior ele não poderá mais receber
pagamentos. O comprador receberá a mensagem que o vendedor não pode mais
receber pagamentos. A exceção é uma contribuição agendada na qual o
membro recebe unidades. Este pagamento sempre acontecerá.
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