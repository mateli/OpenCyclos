<div style="page-break-after: always;">
<a name="top"></a>
<br><br>Empréstimos podem prover um mecanismo
básico de crédito para o Cyclos. Cyclos suporta os tipos essenciais de
empréstimos, incluindo pagamentos agendados (parcelas) e juros, e
permite aos administradores aplicarem uma variedade de taxas.<br>
Empréstimos podem ser pagos internamente no Cyclos ou externamente,
neste último caso o empréstimo precisa ser &quot;descartado&quot;
administrativamente no Cyclos. Um empréstimo pode consistir em um
pagamento ou em empréstimo com pagamentos múltiplos (parcelas). Caso o
empréstimo consista em múltiplos pagamentos, cada pagamento pode ter uma
data de expiração e um estado. Um empréstimo pode ser dado a um membro
individualmente ou a um grupo de empréstimo. Um grupo de empréstimo é um
grupo de membros no qual um desses membros possui permissão para
controlar o empréstimo. Isto é um função comum em projetos de
microfinanças.

<span class="admin">
<br><br><i>Onde encontrar / Como ativar.</i><br>
Para usar empréstimos no cyclos, você deve fazer os seguintes passos:
<ol>
	<li><b>Criar o tipo de transação:</b> Antes de poder usar
	empréstimos, o <a
		href="${pagePrefix}account_management#transaction_types"><u>tipo
	de transação</u></a> apropriado para empréstimos deve ser criado, pois cada
	empréstimo deve ter o seu próprio tipo de transação.<br>
	Em geral, você terá que criar dois tipos de transações para empréstimo:
	um para conceder o empréstimo ao membro (vindo de uma conta de
	sistema), e um outro tipo com o qual o membro irá usar para pagar o
	empréstimo (um da conta de membro para uma conta de sistema). Você não
	pode criar o primeiro tipo de transação sem que antes tenha criado o
	que será usado para fazer o pagamento de volta (este da conta do membro
	para uma conta de sistema), assim é melhor começar criando o tipo de
	transação para o pagamento do empréstimo. Para sistemas de débito mais
	complicados, você terá que criar tipos de transações para taxas e juros
	também.
	<br><br>Um novo tipo de transação para o <b>pagamento de volta do
	empréstimo</b> é criado da seguinte forma:
	<ul>
		<li>Vá até a seção de transações, &quot;Menu: Contas >
		Gerenciamento de contas&quot;.
		<li>Escolha um tipo de conta, na qual o membro receberá o
		empréstimo. Normalmente esta costuma ser &quot;Conta de membro&quot;.
		Clique no ícone de edição <img border="0" src="${images}/edit.gif"
			width="16" height="16">&nbsp; para esta conta.
		<li>Na próxima janela, vá para a janela dos &quot;<a
			href="${pagePrefix}account_management#transaction_type_search"><u>tipos
		de transações</u></a>&quot;, e clique no botão &quot;inserir novo tipo de
		transação&quot;, abaixo da janela. Antes de criar este novo tipo de
		transação, você pode verificar na lista de tipos de transações se ja
		não existe um tipo de transação que possa ser usado.
		<li>Na janela seguinte você deve escolher uma conta na caixa de
		seleção &quot;Para&quot;. Normalmente esta é a &quot;conta de
		débito&quot;, mas você deve pensar de qual conta de sistema sairá o
		tipo de transação que será usado pelo empréstimo.<br>
		Simplesmente preencha o resto dos campos, e se necessário consulte a
		sistema de ajuda do local.
	</ul>
	<br>
	Quando feito, você pode criar o novo tipo de transação para <b>conceder
	o empréstimo</b>:
	<ul>
		<li>Novamente, vá até a seção de transações, &quot;Menu: Contas >
		Gerenciamento de contas&quot;.
		<li>Escolha um tipo de conta, de acordo com o que você
		especificou anteriormente, que normalmente é a &quot;conta de
		débito&quot;. Clique no ícone de edição <img border="0"
			src="${images}/edit.gif" width="16" height="16">&nbsp; para
		esta conta.
		<li>Verifique na lista de tipos de transações se ja não existe um
		tipo de transação para conceder empréstimos. Se não existir clique no
		botão &quot;inserir novo tipo de transação&quot;.
		<li>Na janela seguinte você deve escolher um tipo de conta na
		caixa de seleção &quot;Para&quot;. Esta normalmente é a &quot;conta de
		membro&quot; que você usou antes para criar o tipo de transação para o
		pagamento de volta. Escolhendo a conta, uma caixa &quot;é
		empréstimo&quot; no final da janela será visível. Marque esta caixa, e
		uma série de outros campos serão visíveis. <a href="#make_loan_type"><u>Clique
		aqui</u></a> para mais detalhes, ou consulte a ajuda local.
	</ul>
	<br>
	<br>
	<li><b>Defina as permissões apropriadas:</b> Tenha certeza de que
	as <a href="${pagePrefix}groups#manage_groups"><u>permissões</u></a>
	apropriadas para empréstimos estão definidas. Administradores e
	provavelmente corretores podem possuir <a
		href="${pagePrefix}groups#manage_group_permissions_admin_member"><u>permissões</u></a>
	para conceder empréstimos; Você também necessitará definir as <a
		href="${pagePrefix}groups#manage_group_permissions_member"><u>
	permissões do membro</u></a> para ver e pagar o empréstimos.<br>
	Você também pode definir permissões para o <a
		href="${pagePrefix}loan_groups"><u>grupo de empréstimos</u></a>, caso
	queira usa-los.<br>
	<br>
	<li><b>Conceder o empréstimo:</b> Um empréstimo é concedido indo
	até o <a href="${pagePrefix}profiles"><u>perfil</u></a> do membro e
	clicando em &quot;Conceder empréstimo&quot;.<br>
	<br>
	<li><b>Gerenciando empréstimos:</b> Empréstimos podem ser
	gerenciados através do &quot;Menu: Contas > Gerenciar
	empréstimos&quot;. Pagamentos podem ser gerenciados através do
	&quot;Menu: Contas > Pagamentos de empréstimos&quot;.
</ol>
</span>
<span class="member">
<br><br><i>Onde encontrar.</i><br>
Você pode acessar os seus empréstimos através do &quot;Menu: Conta >
Empréstimos&quot;. Onde você obterá uma visão geral de todos os
empréstimos, e este também é o ponto de partida para efetuar o pagamento
de um empréstimo.
</span>
<span class="broker">Você pode acessar os empréstimos de seus
membros através do <a href="${pagePrefix}profiles"><u> perfil do
membro</u></a>; dentro das ações, existe uma seção especial para gerenciamento
de empréstimos.</span>
<hr>

<span class="admin"> <a name="make_loan_type"></a>
<h3>Criar um tipo de transação para empréstimo</h3>
(<i>Dica: Podem existir alguns links para outras seções da ajuda.
Use a tecla backspace para voltar, quando o seu navegador não exibir o
ícone de voltar.)</i>
<br><br>Se você marcou a caixa &quot;é empréstimo&quot; significa que o <a
	href="${pagePrefix}account_management#transaction_types"><u>tipo
de transação</u></a> que você esta criando é um <a href="#top"><u>empréstimo</u></a>.
As configurações do empréstimo irão aparecer abaixo assim que você
marcar a caixa.<br>
No caso de empréstimo, alguns outros campos precisam ser especificados
também. O mais importante destes campos é o primeiro, o campo &quot;tipo
de empréstimo&quot;. Primeiro faça a sua escolha para este tipo: a
escolha determinará quais outros campos estarão visíveis.<br>
O tipo de transação atual que você esta criando agora ou editando, é o
tipo de transação para colocar as unidades na conta do membro na forma
de um empréstimo. É claro que um tipo de transação correspondente para
fazer o pagamento deste empréstimo é necessário. Este tipo de transação
correspondente deve ser especifico no campo &quot;tipo de
pagamento&quot;, este será um tipo de pagamento da conta do membro para
uma conta de sistema. Caso este não exista, você terá que cria-lo
primeiro.
<br><br>Existem três <a href="#loan_types"><u>tipos de
empréstimos</u></a> disponíveis; O resto dos campos será discutido dentro de
cada tipo de empréstimo:
<ul>
	<li><b>Empréstimo simples (pagamento único):</b> Deve ser pagado
	de volta na data (ou antes) específica. Se o seu empréstimo for desse
	tipo, você deve especificar os seguintes campos:
	<ul>
		<li><b>Tipo de pagamento: </b> Para explicação leia um pouco
		acima.
		<li><b>Dias de pagamento padrão:</b> Este é o período de
		expiração; Após este período o empréstimo será exibido como
		&quot;vencido&quot; na visão geral de empréstimo do membro e na função
		de gerenciamento de empréstimos da seção de administração. O membro
		supostamente pagará o empréstimo antes do vencimento.
	</ul>
	<br>
	<br>
	<li><b>Pagamentos múltiplos:</b> Empréstimos desse tipo são
	divididos em pagamentos periódicos (mensais), cada pagamento possui sua
	própria data de vencimento. Você terá que especificar o tipo de
	pagamento.
	<li><b>Com taxas:</b> Este é um empréstimos que pode ter
	diferentes tipos de taxas e pagamentos periódicos. As seguintes taxas
	podem ser configuradas:
	<ul>
		<li><b>Juros mensais:</b> Estes são juros (compostos) calculados
		por mês. O valor total do empréstimo e outros custos (juros, taxas de
		concessão) são distribuídos em um número fixo de pagamento periódicos
		de valores iguais (empréstimo parcelado).
		<li><b>Taxa de concessão: </b> Esta é uma taxa única que precisa
		ser paga para o empréstimo. Este valor é distribuído (incluído) em
		todos os pagamentos periódicos. A taxa pode ser uma porcentagem do
		valor total do empréstimo ou um valor fixo.
		<li><b>Taxa de vencimento:</b> Este é o valor fixo que tem que
		ser pago quando o pagamento não é feito na data correta (antes do
		vencimento).
		<li><b>Juros de vencimento: </b> Este é o juros cobrados por dia
		de atraso, quando o pagamento não é feito na data correta.
	</ul>
</ul>
<hr class="help">
</span>

<a name="loan_types"></a>
<h3>Tipos de empréstimos</h3>
<br><br>Existem três <a href="#top"><u>tipos de empréstimos</u></a>
disponíveis;
<ul>
	<li><b>Empréstimo simples (pagamento único):</b> Deve ser pagado
	de volta na data (ou antes) específica. Se o seu empréstimo for desse
	tipo, você deve especificar os seguintes campos:
	<ul>
		<li><b>Tipo de pagamento: </b> Para explicação leia um pouco
		acima.
		<li><b>Dias de pagamento padrão:</b> Este é o período de
		expiração; Após este período o empréstimo será exibido como
		&quot;vencido&quot; na visão geral de empréstimo do membro e na função
		de gerenciamento de empréstimos da seção de administração. O membro
		supostamente pagará o empréstimo antes do vencimento.
	</ul>
	<br>
	<br>
	<li><b>Pagamentos múltiplos:</b> Empréstimos desse tipo são
	divididos em pagamentos periódicos (mensais), cada pagamento possui sua
	própria data de vencimento. Você terá que especificar o tipo de
	pagamento.
	<li><b>Com taxas:</b> Este é um empréstimos que pode ter
	diferentes tipos de taxas e pagamentos periódicos. As seguintes taxas
	podem ser configuradas:
	<ul>
		<li><b>Juros mensais:</b> Estes são juros (compostos) calculados
		por mês. O valor total do empréstimo e outros custos (juros, taxas de
		concessão) são distribuídos em um número fixo de pagamento periódicos
		de valores iguais (empréstimo parcelado).
		<li><b>Taxa de concessão: </b> Esta é uma taxa única que precisa
		ser paga para o empréstimo. Este valor é distribuído (incluído) em
		todos os pagamentos periódicos. A taxa pode ser uma porcentagem do
		valor total do empréstimo ou um valor fixo.
		<li><b>Taxa de vencimento:</b> Este é o valor fixo que tem que
		ser pago quando o pagamento não é feito na data correta (antes do
		vencimento).
		<li><b>Juros de vencimento: </b> Este é o juros cobrados por dia
		de atraso, quando o pagamento não é feito na data correta.
	</ul>
</ul>

<hr class="help">

<span class="admin broker"> <a NAME="loan"></a>
<h3>Conceder empréstimo</h3>
Com esta função você pode conceder <a href="#top"><u>empréstimos</u></a>
a um membro. Para poder fazer isso, certas condições devem ser cumpridas
antes, <a href="#top"><u>clique aqui</u></a> para vê-las.
<br><br>Os seguintes campos devem ser preenchidos para conceder um
empréstimo:
<ul>
	<li><b>Grupo de empréstimo:</b> Esta opção será exibida caso o
	membro seja o responsável ou parte de um <a
		href="${pagePrefix}loan_groups"><u>grupo de empréstimos</u></a>. Se
	você não quer ter nenhum grupo de empréstimo envolvido, mas quer passar
	um empréstimo a um membro em pessoa, selecione a opção
	&quot;Pessoal&quot;. <br>
	<li><b>Identificador:</b> Este é o número pelo qual o empréstimo
	será identificado. Você pode escolher o que quiser.<br>
	Note: O campo identificador é um <a href="${pagePrefix}custom_fields"><u>campo
	customizado de empréstimo</u></a> incluído no banco de dados padrão. Você pode
	remove-lo ou criar outros campos customizados com diferentes regras.<br>
	<li><b>Tipo de empréstimo:</b> Este é o campo mais importante do
	formulário. Aqui você seleciona a qual tipo de transação o empréstimo
	pertence. Cada tipos de transação implica em três <a href="#loan_types"><u>tipos
	de empréstimos</u></a> possíveis. Dependendo do tipos de empréstimo implícito,
	o resto do formulário mostrará os seus campos. Nós vamos explicar os <b>campos
	específicos dos tipos de empréstimos</b> abaixo.<br>
	<li><b>Descrição:</b> Informe uma descrição para o empréstimos.<br>
	<li><b>Valor:</b> Este é o valor total que o membro receberá em
	sua conta. Este é o valor debitado inicial ou &quot;principal&quot; do
	empréstimo.<br>
	<li><b>Conceder no passado:</b> Marque esta caixa se a data do
	empréstimo <b>NÂO</b> deve ser a data atual, mas sim uma data passada.
	Ao marcar esta caixa você deverá especificar a data em um campo extra
	que aparecerá.<br>
	<li><i>Campos específicos dos tipos de empréstimos:</i> O resto
	dos campos no formulário são dependentes de qual &quot;tipo de
	empréstimo&quot; foi escolhido.
	<ul>
		<li><b>Empréstimo simples (pagamento único):</b> Caso o tipo de
		transação que você escolheu implique um &quot;Empréstimo
		simples&quot;, Os seguintes campos serão visíveis:
		<ul>
			<li><b>Data de pagamento:</b> A data em que o empréstimo
			precisará ser pago. Nesta data um alerta será gerado e o estado do
			empréstimo será alterado para &quot;vencido&quot; (na seção de
			administração de empréstimos).
		</ul>
		<br>
		<br>
		<li><b>Pagamentos múltiplos:</b> Caso o tipo de transação que
		você escolheu implique um &quot;Empréstimo com múltiplos
		pagamentos&quot;, Os seguintes campos serão visíveis:
		<ul>
			<li><b>Primeira data de vencimento:</b> O pagamento do
			empréstimo é dividido em várias &quot;<a href="#component"><u>parcelas</u></a>
			do empréstimo&quot;. Aqui você informa a data em que a primeira
			parcela do empréstimo precisa ser paga (assim <b>não</b> todo o
			empréstimo). Nesta data um alerta sera gerado e o estado da parcela
			mudará para &quot;vencida&quot;.
			<li><b>Número de parcelas:</b> O número de parcelas no qual o
			empréstimo será dividido.
			<li><b>Calcular:</b> O botão de calcular mostrará o valor das
			parcelas para o pagamento do empréstimo e suas datas de vencimento.
			Estas datas e valores podem ser alterados. Caso você altere os
			valores tenha cuidado para que o valor total das parcelas seja o
			mesmo do valor total do empréstimo.
		</ul>
		<br>
		<li><b>Empréstimo com taxas:</b> Caso o tipo de transação que
		você escolheu implique um &quot;Empréstimo com taxas&quot;, Os
		seguintes campos serão visíveis:
		<ul>
			<li><b>Todos os juros e taxas configurados:</b> Estes campos
			acima da &quot;descrição&quot; indicam as taxas que são cobradas.
			Eles são apenas para informação e não podem ser alterados. Para mais
			informações <a href="#make_loan_type"> <u>clique aqui</u></a>.
			<li><b>Primeira data de vencimento:</b> O pagamento do
			empréstimo é dividido em várias &quot;<a href="#component"><u>parcelas</u></a>
			do empréstimo&quot;. Aqui você informa a data em que a primeira
			parcela do empréstimo precisa ser paga (assim <b>não</b> todo o
			empréstimo). Nesta data um alerta sera gerado e o estado da parcela
			mudará para &quot;vencida&quot;.
			<li><b>Número de parcelas:</b> O número de parcelas no qual o
			empréstimo será dividido.
			<li><b>Mostrar:</b> Este botão irá mostrar as diferentes
			parcelas do empréstimo e suas datas de vencimento. Estas datas e
			valores não podem ser alterados diretamente, você pode alterar as
			parcelas apenas modificando o valor total do empréstimo ou o número
			de parcelas. Os valores exibidos possuem todas as taxas incluídas.
		</ul>
		<br>
	</ul>
</ul>
<br><br>Note: É possível criar <a href="${pagePrefix}custom_fields"><u>campos
extra para os empréstimos</u></a> caso seja necessário. Por exemplo um campo
para o número de contrato do empréstimo.
<hr class="help">
</span>

<span class="admin broker"> <a name="loan_confirm"></a>
<h3>Confirmar empréstimo</h3>
Esta tela simplesmente verifica as informações do empréstimo antes que o
mesmo seja concedido. Verifique as informações e caso ok, clique no
botão &quot;Enviar&quot; para emitir o empréstimo.
<hr class="help">
</span>

<span class="admin"> <a NAME="search_loans_by_admin"></a>
<h3>Procurar empréstimos</h3>
Com esta função você obtém uma visão geral dos <a href="#top"><u>empréstimos</u></a>
de todos os membros. Existem várias opções de busca. Como sempre
deixando um campo em branco significa que você procura por todas as
possibilidades para o campo.
<ul>
	<li><b>Filtro:</b> As duas primeiras opções de filtro são as
	combinações de estados dos empréstimos, onde &quot;Qualquer
	aberto&quot; são todos os empréstimos que ainda não foram inteiramente
	pagos ou descartados e &quot;Qualquer fechado&quot; todos os
	empréstimos que foram inteiramente pagos ou descartados.<br>
	As opções de filtro restante são os diferentes <a href="#status"><u>Estados</u></a>
	dos empréstimos.
	<li><b>Campo customizado:</b> Se um campo customizado de pagamento
	existente está definido para o tipo de empréstimo, e esta configurado
	para ser incluído na busca de empréstimos, ele será exibido após a
	opção de filtro.
	<li><b>Tipo de empréstimo:</b> Caso mais de um tipo de empréstimo
	exista, você pode escolher um tipo nesta caixa de seleção. Aqui, tipos
	de empréstimos se referem ao <a
		href="${pagePrefix}account_management#transaction_types"><u>tipo
	de transação</u></a> ao qual o empréstimo pertence.
	<li><b>Nome / nome de usuário:</b> Com esta opção você pode
	procurar por empréstimos de um membro específico.</li>
	<li><b>Nome / nome de usuário do corretor:</b> Esta opção permite
	que você procure por empréstimos de membros relacionado a um <a
		href="${pagePrefix}brokering"><u>corretor</u></a> específico.
	<li><b>Número da transação:</b> Caso os números de transações
	estejam ativados no sistema, você pode procurar por esse número.
	<li><b>Grupo de empréstimo:</b> Com esta opção você pode procurar
	por empréstimos concedidos à um <a href="${pagePrefix}loan_groups"><u>grupo
	de empréstimo</u></a> específico. Esta opção só é visível caso exista algum
	grupo de empréstimo no sistema.
	<li><b>Período de concessão:</b> Esta opção permite procurar por
	empréstimos que foram concedidos em um período específico.
	<li><b>Período de vencimento:</b> Esta opção permite procurar por
	empréstimos que com vencimento em um período específico.
	<li><b>Período de pagamento:</b> Esta opção permite que você
	procure por empréstimos cujas parcelas tenham sido pagas em um período
	específico.
</ul>
Clique em &quot;Enviar&quot; para submeter a pesquisa.
<hr class="help">
</span>

<a NAME="search_loans_result"></a>
<h3>Resultado da busca por empréstimos</h3>
Esta janela mostra o resultado da pesquisa por
<a href="#top"><u>empréstimos</u></a>
. Esta janela mostrará uma lista com as seguintes informações (nem todas
as colunas podem ser visíveis, dependem por onde você esta acessando a
página e de algumas configurações):
<ul>
	<li><b>Membro:</b> Membro que recebeu o empréstimo. Clique no nome
	para ir ao <a href="${pagePrefix}profiles"><u>perfil</u></a> do membro.



	
	<li><b>Descrição:</b> Descrição do empréstimo.
	<li><b>Valor:</b> O valor total do empréstimo.
	<li><b>Valor restante:</b> O valor total do empréstimo que o
	membro ainda precisa pagar.
	<li><b>Pagamentos:</b> O número de <a href="#component"><u>parcelas</u></a>
	do empréstimo. O primeiro número é a quantidade de pagamentos que já
	foram feitos. O segundo número (após a /) é o número total de parcelas
	do empréstimo. Isto não será visível caso a lista possua apenas
	empréstimos simples sem parcelas.
	<li><b>Ícone de edição (<img border="0"
		src="${images}/edit.gif" width="16" height="16">&nbsp;): </b> use
	este ícone para ver os detalhes do empréstimo, junto de algumas
	informações adicionais.
</ul>
No topo direito da janela, existe alguns outros ícones disponíveis. O
ícone
<img border="0" src="${images}/save.gif" width="16" height="16">
&nbsp; irá exportar a lista para um arquivo
<a href="#csv"><u>csv</u></a>
. O ícone
<img border="0" src="${images}/print.gif" width="16" height="16">
&nbsp; abrirá uma página de impressão com os detalhes de todos os
empréstimos listados.
<hr class="help">

<a NAME="search_loans_member_by_admin"></a>
<a NAME="search_loans_by_member"></a>
<a NAME="search_loans_member_by_broker"></a>
<span class="admin broker">
<h3>Procurar empréstimos do membro</h3>
</span>
<span class="member">
<h3>Procurar meus empréstimos</h3>
</span>
Com esta função você pode obter uma visão geral dos
<a href="#top"><u>empréstimos</u></a>
<span class="admin broker"> do membro</span>
. Faça sua escolha entre empréstimos &quot;
<a href="#open"><u>abertos</u></a>
&quot; ou &quot;
<a href="#closed"><u>fechados</u></a>
&quot;. A
<a href="#search_loans_result"><u>janela de resultado da busca</u></a>
abaixo mostrará os resultados.
<hr class="help">

<span class="admin"> <a NAME="search_loan_payments"></a>
<h3>Procurar pagamentos de empréstimos</h3>
Esta página permite que você procure por informações de pagamentos de <a
	href="#top"><u>empréstimos</u></a> - também para informações de
pagamentos de empréstimos que ainda não foram pagos. Os seguintes campos
estão disponíveis:
<ul>
	<li><b>Estado:</b> Aqui você pode buscar pelos <a href="#status"><u>estados</u></a>
	possíveis dos empréstimos.
	<li><b>Campo customizado:</b> Se um campo customizado de pagamento
	existente está definido para o tipo de empréstimo, e esta configurado
	para ser incluído na busca de empréstimos, ele será exibido após a
	opção de filtro.
	<li><b>Tipo de transação:</b> Este é o <a
		href="${pagePrefix}account_management#transaction_types"><u>tipo
	de transação</u></a> do empréstimo. (exibido apenas caso exista mais de um tipo
	de transação de empréstimo).
	<li><b>Nome / nome de usuário:</b> Este é o nome de usuário e o
	nome do mutuário.
	<li><b>Nome / nome de usuário do corretor:</b> Este é o nome de
	usuário e o nome do <a ref="${pagePrefix}brokering"><u>corretor</u></a>
	do mutuário.
	<li><b>Data/período de expiração:</b> Esta opção permite que você
	busque por empréstimos que expirarão em um período específico.
	<li><b>Data/período de pagamento:</b> Esta opção permite que você
	busque por pagamentos que foram pagos em um período específico.
</ul>
<hr class="help">
</span>

<span class="admin"> <a NAME="search_loan_payments_result"></a>
<h3>Resultado da busca por pagamentos de empréstimos</h3>
Esta janela mostra o resultado da busca por pagamentos de empréstimos.
<ul>
	<li><b>Membro:</b> Membro que recebeu o empréstimos. Clique no
	nome para ir ao <a href="${pagePrefix}profiles"><u>perfil</u></a>.
	<li><b>Data:</b> Data de expiração do pagamento do empréstimo.
	<li><b>Valor:</b> Valor total do empréstimo.
	<li><b>Estado:</b> O <a href="#status"><u>estado</u></a> do
	pagamento do empréstimo.
	<li><b>Pago:</b> O valor do empréstimo que ja foi pago
	internamente.
	<li><b>Descartado:</b> O valor do empréstimo que foi descartado.
	<li><b>Ícone ver <img border="0" src="${images}/view.gif"
		width="16" height="16">&nbsp;: </b> Use este ícone para ver os
	detalhes do pagamento do empréstimo, junto com algumas informações
	adicionais.
</ul>
No topo à direita da janela, existe algusn outros ícones disponíveis. O
ícone <img border="0" src="${images}/save.gif" width="16" height="16">
irá exportar a lista para um arquivo <a href="#csv"><u>csv</u></a>. O
Ícone <img border="0" src="${images}/print.gif" width="16" height="16">
&nbsp;irá abrir uma página para impressão com os detalhes dos
empréstimos listados.
<hr class="help">
</span>


<a NAME="loan_detail"></a>
<h3>Detalhe do empréstimo</h3>
Esta pagina mostra os detalhes sobre o
<a href="#top"><u>empréstimo</u></a>
. Dependendo do tipo do empréstimo a página exibirá vários valores do
empréstimo.
<br><br>O ícone imprimir (<img border="0" src="${images}/print.gif"
	width="16" height="16">&nbsp;) abrirá uma página de impressão com
os detalhes do empréstimo todas as <a href="#component"><u>parcelas</u></a>
do empréstimo. <span class="admin"> Em alguns estados especiais
do empréstimo (quando este tiver o estado &quot;vencido&quot; ou
&quot;em processo&quot;) você pode alterar o <a href="#status"><u>estado</u></a>
clicando no botão abaixo &quot;Marcar este empréstimo como...&quot;. </span>
<hr class="help">

<a NAME="loan_parcels_detail"></a>
<h3>Pagamentos</h3>
Esta página mostra os detalhes sobre as
<a href="#component"><u>parcelas</u></a>
do
<a href="#top"><u>empréstimo</u></a>
. Todos os componentes do empréstimo são listados nesta visão geral. A
tabela é muito simples. O
<a href="#status"><u>Estado</u></a>
pode ser um de vários valores.
<hr class="help">

<span class="admin"> <a NAME="loan_to_members"></a>
<h3>Empréstimo para membros</h3>
Esta página mostrará um lista de membros que pertencem ao <a
	href="${pagePrefix}loan_groups"><u>grupo de empréstimo</u></a> do <a
	href="#top"><u>empréstimo</u></a> selecionado. O nome do membro
&quot;responsável&quot; (membro que recebeu o empréstimo) será exibido
em vermelho. Clicar nos nomes levará você ao <a
	href="${pagePrefix}profiles"><u> perfil</u></a> destes membros.
<hr class="help">
</span>

<a NAME="loan_repayment_by_admin"></a>
<a NAME="loan_repayment_by_member"></a>
<h3>Pagamento do empréstimo</h3>
Esta página mostrará informações sobre a parcela do
<a href="#top"><u>empréstimo</u></a>
e a possibilidade de paga-la
<span class="admin">ou <a href="#discard"><u> descarta-la</u></a></span>
.
<br>
Você pode adaptar o valor, mas este é pré-preenchido com o valor que
você deve pagar.
<span class="admin"> Se você marcar a caixa &quot;pagar no
passado&quot;, o pagamento do empréstimo será reservado em uma data
passada; Você sera solicitado a especificar esta data em um campo extra.</span>
<br><br>Se o empréstimo for um empréstimo com <a href="#loan_types"><u>pagamentos
múltiplos</u></a> (estes podem incluir um empréstimo com taxas), então alguns
campos extras são disponíveis. Estes campos não estão disponíveis para
empréstimos simples. O &quot;número do pagamento&quot; se refere a
parcela do empréstimo na visão geral acima; Normalmente você deve pagar
a próxima parcela em linha (o número da parcela mais baixa e ainda não
paga), mas você pode escolher pagar outra parcela.
<br><br><span class="admin">Use um dos botões para pagar ou
descartar o empréstimo.</span> <span class="member">clique no botão
&quot;pagar&quot; para pagar (parte do) o empréstimo.</span>
<hr class="help">

<br><br><a name="glossary"></a>
<h2>Glossário de termos</h2>
<br><br>

<a name="component"></a>
<b>Parcela</b>
de um empréstimo
<br><br>Um de um número sucessivo de pagamentos em liquidação de um
débito. Quando o pagamento de um empréstimo é dividido em partes, cada
uma dessas partes é chamada de parcela.
<hr class='help'>

<a name="csv"></a>
<br>
Arquivos
<b>CSV</b>
<br><br>CSV significa &quot;comma separated values&quot; valores
separados por vírgula; Este é o formato para o arquivo com dados que
pode ser baixado de várias janelas com resultados de buscas no cyclos.
Neste formato, os valores dos campos, como o título sugere, são
separados por vírgulas (embora qualquer outro caractere possa ser usado
como separador). <br>
Este formato pode ser normalmente aberto por um programa de planilha
eletrônica, como o Open Office Calc ou o Microsoft Excel. Você também
pode processar o arquivo CSV com um editor de texto em combinação com
macros. Programas como Word ou o WordPerfect possuem excelentes
facilidades macro para processar automaticamente arquivos de entrada
para documentos apresentáveis editados satisfatoriamente.
<hr class='help'>

<a name="status"></a>
<b>Estados do empréstimo</b>
<br>
Os estados do empréstimo podem ser aplicados para empréstimos ou
<a href="#component"><u>parcelas</u></a>
de empréstimos. Eles podem ser um dos seguintes:
<ul>
	<li><b>Aberto:</b> O empréstimo é aberto, o que significa que ele
	ainda não foi pago, mas ainda não alcançou a data de vencimento. Ainda
	existem obrigações de pagamentos para o membro.
	<li><b>Vencido:</b> A data de pagamento passou mas não foi pago.
	<li><b>Fechado / pago:</b> O empréstimo foi pago ou descartado e é
	administrativamente fechado. O membro não possui mais obrigações de
	pagamento.
	<li><b>Descartado:</b> Uma parcela do empréstimo ou o próprio
	empréstimo normalmente é descartado caso o empréstimo tenha sido pago
	por outros meios, por exemplo com bens ou dinheiro convencional. Uma
	parcela de um empréstimo descartado pode ser considerada como fechada.<br>
	<li><b>Em processo:</b> Quando um empréstimo atinge a data de
	vencimento, um administrador pode alterar o seu estado para &quot;em
	processo&quot;. Principalmente por causa de uma re-negociação do
	empréstimo. Após este estado um administrador pode tanto coloca-lo no
	estado de &quot;recuperado&quot; ou &quot;Irrecuperável&quot;. Este
	estado apenas pode ser alcançado de um empréstimo vencido. Isto
	significa que o empréstimo é vencido, mas as partes estão negociando o
	que fazer com ele.<br>
	<li><b>Recuperado:</b> Este é um estado &quot;final&quot; pode ser
	alcançado apenas a partir de um estado &quot;em processo&quot;.
	Significa que o empréstimo foi recuperado.
	<li><b>Irrecuperável:</b> Este estado pode ser alcançado apenas a
	partir de um estado &quot;em processo&quot;. Estritamente visto,
	significa que este empréstimo ainda está em dívida, mas todas as partes
	consideram ele como não pagável pelo membro, e não aguarda nenhum
	pagamento. O empréstimo esta em um tipo de estado
	&quot;congelado&quot;.
	<li><b>Pendente de autorização:</b> O pagamento do empréstimo
	precisa de autorização. Uma vez que o pagamento do empréstimo seja
	autorizado a transferência será feita automaticamente. (Esta opção só
	será exibida no filtro da busca caso o administrador possua permissões
	para ver os pagamentos autorizados)
	<li><b>Autorização Negada:</b> O pagamento do empréstimo foi
	negado. Isto significa que o empréstimo é administrativamente
	cancelado. (Esta opção só será exibida no filtro da busca caso o
	administrador possua permissões para ver os pagamentos autorizados)
</ul>

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