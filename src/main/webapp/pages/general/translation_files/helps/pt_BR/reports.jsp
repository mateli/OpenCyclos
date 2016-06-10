<div style="page-break-after: always;">
<br><br>Relatórios permitem a você ter uma visão
geral do estado do sistema. Você pode ver vários tipos de relatórios:
<ul>
	<li><b>Relatório da atividade individual de um membro</b>
	<li><b>Relatórios para um conjunto de membros</b>
	<li><b>Relatórios gerais do sistema</b>
</ul>
Os relatórios pontuam o número de transações, o volume transacionado, o
número de anúncios e o número de referências estabelecidas.

Em contraste aos relatórios, Cyclos também oferece
<a href="${pagePrefix}statistics"><u>estatísticas</u></a>
, que são relatórios estendidos com análises estatísticas.
<br><br><i>Onde encontrar.</i><br>
Você pode acessar os relatórios das seguintes maneiras: <span
	class="admin"> Existe um item de menu principal para relatórios:
&quot;Menu: Relatórios&quot;. Este contém vários sub-itens. </span> Relatórios
individuais de um membro, podem ser visualizados através do <a
	href="${pagePrefix}profiles"><u>perfil</u></a> deste membro. <span
	class="admin">
<br><br><i>Como ativar.</i><br>
Todos os tipos de relatórios possuem uma <a
	href="${pagePrefix}groups#manage_group_permissions_admin_system"><u>permissão</u></a>,
que deve ser ativada para que eles sejam visíveis. As permissões podem
ser encontradas no bloco intitulado &quot;Relatórios&quot;.
</span>
<hr>

<a name="member_activities"></a>
<h3>Meus relatórios / Atividade do membro</h3>
Aqui você pode ver um pequeno relatório com os seus dados atuais ou os
dados de um outro membro. Você ver dados em
<a href="${pagePrefix}references"><u> referências</u></a>
,
<a href="${pagePrefix}advertisements"><u>anúncios</u></a>
(=&quot;produtos & serviços&quot;), e em
<a href="${pagePrefix}invoices"><u>faturas eletrônicas</u></a>
.
<br>
Se forem seus próprios dados, você também verá dados sobre suas
<a href="${pagePrefix}invoices"><u>faturas eletrônicas</u></a>
.
<hr class="help">

<span class="admin"> <a name="current_state"></a>
<h3>Relatório de estado atual</h3>
&quot;Relatório de estado atual&quot; permitem que você veja o estado
presente do sistema. <a href="#current_result"><u>Clique aqui</u></a>
para uma visão geral do que será exibido.
<br><br>Use the checkboxes to select what you want to view, and then
click &quot;submit&quot;. <a name="current_result"></a>
<h3>Resultado da visão geral do estado atual</h3>
Os seguintes serão exibidos nesta página:
<ul>
	<li><b>Informações de <a href="${pagePrefix}groups"><u>Grupos</u></a>
	de membros</b>: Esta seção mostrará o número de membros ativados; Estes são
	todos os membros que estão aptos para acessar este sistema. Este ao
	contrário do resto da página, mostrará todos os grupos de membros e a
	quantidade de membros em cada grupo.
	<br><br>
	<li><b>Informações de <a href="${pagePrefix}advertisements"><u>anúncios</u></a></b>:
	<ul>
		<li><b>Número de membros ativos com anúncios:</b> Todas as contas
		(membros) que podem acessar o sistema, e tenham publicado um ou mais
		anúncios.
		<li><b>Número de anúncios ativos:</b> Todos os anúncios válidos
		no momento.
		<li><b>Número de anúncios vencidos:</b> Todos os anúncios que
		estão vencidos.
		<li><b>Número de anúncios agendados:</b> Todos os anúncios que já
		estão gravados e serão ativos no futuro.
	</ul>
	<br><br>
	<li><b>Contas de sistema</b>: Esta seção irá mostrar todas as
	contas de sistema e seus sados atuais.
	<br><br>
	<li><b>Contas de membro</b>: Esta seção irá mostrar todos os tipos
	de conta de membros e a soma de seus balanços. Normalmente existe
	apenas um tipo de conta de membro no sistema. Todos os membros possuem
	uma conta desse tipo.
	<br><br>
	<li><b><a href="${pagePrefix}invoices"><u>Faturas
	eletrônicas</u></a></b>:
	<ul>
		<li><b>Número de faturas de membros:</b> Número total de faturas
		de entrada e saída entre membros.
		<li><b>Soma total de faturas de membros:</b> Soma total de
		faturas de entrada e saída entre membros.
		<li><b>Número de faturas de entrada de sistema:</b> Número total
		de faturas de membros para o sistema.
		<li><b>Soma das faturas de entrada de sistema:</b> Soma total das
		faturas de membros para contas de sistema.
		<li><b>Número de faturas de saída de sistema:</b> Número total de
		faturas de sistema para membros.
		<li><b>Soma das faturas de saída de sistema:</b> Soma total das
		faturas de sistema para membros.
	</ul>
	<br><br>
	<li><b><a href="${pagePrefix}loans"><u>Empréstimos</u></a></b>:
	<ul>
		<li><b>Número de empréstimos abertos:</b> Todos os empréstimos de
		membros que ainda não foram totalmente pagos.
		<li><b>Quantia restante de empréstimos abertos:</b> Soma de todos
		os valores restantes dos empréstimos abertos.
	</ul>
	<br><br>
	<li><b><a href="${pagePrefix}references"><u>Referências</u></a></b>:
	<br><br>Para todas categorias diferentes de referências, a tabela mostra
	quantas vezes esta categoria em particular foi atribuída.
</ul>
<hr class="help">

<a name="member_lists"></a>
<h2>Lista de membros</h2>
Esta função leva você a ver uma lista com membros, contendo vários dados
para cada membro. Você pode solicitar os dados sobre o estado atual, ou
pode escolher uma data determinada do histórico.<br>
Você pode solicitar dados dos seguintes assuntos:
<ul>
	<li>Nome do membro e do corretor
	<li>anúncios
	<li>referências
	<li>limites das contas e balanços.
</ul>

Note que tudo isso são &quot;pontos de dados&quot;, significa que os
dados são sobre um ponto específico no tempo. A consequência disto é que
as informações sobre as atividades de comércio não podem ser dadas (como
o comércio em um ponto de tempo, é sem sentido). Se você quiser dados de
um intervalo de tempo, você deve ir para o &quot;Menu: <a
	href="#member_reports"><u>Relatórios de membros</u></a>&quot; ou ao
&quot;Menu: <a href="${pagePrefix}statistics"><u>Estatísticas</u></a>&quot;.
<br><br>Se você marcar muitas opções, este pode demorar alguns segundos
para realizar os cálculos, Portanto tenha paciência.
<br><br>No final da página você pode <a href="#results"><u>imprimir
ou baixar (gravar)</u></a> os resultados.
<br><br>As seguintes opções estão disponíveis:
<ul>
	<li><b>Tempo:</b> Primeiro você deve selecionar um ponto do tempo
	do qual você quer a lista. Existe duas opções:
	<ul>
		<li><b>Estado atual:</b> Retorna uma lista com os dados atuais do
		sistema.
		<li><b>Histórico:</b> Retorna uma lista com os dados de uma data
		passada, Você deve especificar a data se escolher esta opção. Para
		escolher a data você pode clicar no ícone de calendário ( <img
			border="0" src="${images}/calendar.gif" width="16" height="16">&nbsp;).

		
	</ul>
	<br>
	<br>
	<li><b>Corretor:</b> Nesta seção, você pode especificar o nome ou
	o nome de usuário de um <a href="${pagePrefix}brokering"><u>corretor</u></a>.
	Assim, a lista com os dados, será limitada apenas aos membros deste
	corretor.<br>
	<br>
	<li><b>Membros:</b> Nesta seção você pode especificar o seguinte:
	<ul>
		<li><b>Nome do membro:</b> Marque esta opção se você quiser que o
		nome dos membros sejam imprimidos em cada linha da lista. Caso
		contrário os dados serão imprimidos de forma anônima.
		<li><b>Nome de usuário do corretor:</b> Marque esta opção e o
		nome de usuário do corretor imprimido em cada item da lista.
		<li><b>Nome do corretor:</b> Marque esta opção e o nome do
		corretor imprimido em cada item da lista.
		<li><b>Grupos de permissões:</b> Use esta caixa de seleção
		múltipla para selecionar os grupos para os quais você quer esta lista.
		Se por exemplo você selecionar &quot;membros plenos&quot;, todos os
		membros deste grupo serão incluídos.
	</ul>
	<br>
	<br>
	<li><b>Anúncios:</b> Nesta seção você determina quais dados você
	quer incluir sobre <a href="${pagePrefix}advertisements"><u>anúncios</u></a>.
	Para cada seleção, o número de anúncios com os seus <a
		href="${pagePrefix}advertisements#ad_status"><u>estados</u></a>
	específicos serão imprimidos.
	<ul>
		<li><b>Anúncios ativos</b>
		<li><b>Anúncios vencidos</b>
		<li><b>Anúncios permanentes</b>
		<li><b>Anúncios agendados</b>
	</ul>
	<br>
	<br>
	<li><b>Referências:</b> Nesta seção você pode especificar se você
	que ver informações sobre <a href="${pagePrefix}references"><u>referências</u></a>
	dadas e recebidas. <br>
	<br>
	<li><b>Contas:</b> Nesta seção você pode especificar quais
	informações das contas serão imprimidas:
	<ul>
		<li><b>Limite inferior de crédito</b>
		<li><b>Limite superior de crédito</b>
		<li><b>Balanço das contas</b>
	</ul>
</ul>
<hr>

<a name="member_reports"></a>
<h2>Relatórios do membro</h2>
Esta função permite que você imprima um relatório com as informações de
transações e faturas de um membro.

<b>ATENÇÃO</b>: Por favor esteja ciente de que esta função é muito
pesada. Ela permite que você liste <b>todas</b> as transações de <b>todos</b>
os membros. Isto pode causar uma sobre carga no servidor, e pode
eventualmente causar uma falha no servidor, se você solicitar muitos
dados.
<br><br>Observe que você deve ir através do formulário na ordem dada, de
cima para baixa. A caixa para filtro de pagamento pode estar vazia, caso
você não tenha escolhido um tipo de conta antes.
<br><br>No final da página, você pode <a href="#results"><u>imprimir
ou baixar (gravar)</u></a> os resultados.
<br><br>No formulário, você pode especificar os seguintes elementos:
<ul>
	<li><b>Nome do membro</b>: O nome de usuário é sempre exibido. Se
	você quer que o nome do membro seja exibido junto, você deve selecionar
	esta opção.
	<li><b>Nome e nome de usuário do <a
		href="${pagePrefix}brokering"><u>corretor</u></a></b>: Este informação só
	sera exibida se você marcar este opção.
	<li><b>Grupos de membross</b>: Aqui você determina quais <a
		href="${pagePrefix}groups"><u>grupo(s) de membros</u></a> você deseja
	ver.
	<li><b>Tipo de conta</b>: Normalmente, um grupo de membro possui
	um <a href="${pagePrefix}account_management#accounts"><u>tipo
	de conta</u></a>. Contudo, é possível que os membros possuam múltiplos tipos de
	conta. Especifique aqui quais tipos você quer ver.
	<li><b>Campos De e Até</b>: Para especificar o intervalo de data.

	
	<li><b>Oque exibir?</b>: Este é provavelmente o campo mais
	importante. Aqui você determina se você quer ver transações ou faturas
	eletrônicas<br>
	Se você selecionar transações:
	<ul>
		<li><b>Filtros de pagamentos</b> permitem a você especificar
		quais tipos de transações você deseja que sejão listados. Os <a
			href="${pagePrefix}account_management#transaction_types"><u>filtros
		de pagamentos</u></a> podem ser especificados na seção de <a
			href="${pagePrefix}account_management#account_search"><u>&quot;Gerenciar
		conta&quot;</u></a>. Para que o filtro de pagamento seja exibido nas funções
		de relatórios, ele precisar que a opção &quot;Exibir em
		relatórios&quot; seja marcada.
		<li><b>Transações de débito ou crédito</b>: Após escolher um
		filtro de pagamento, esta opção será visível. Você <b>deve</b>
		escolher pelo menos uma destas opções.
		<li><b>Incluir membros sem transações:</b> Se marcada esta opção,
		será incluído membros que ainda não transacionaram.
		<li><b>Nível de detalhe</b> permite que você determine quanto de
		detalhe você quer ver.
		<ul>
			<li><b>Resumo</b> retorna apenas a soma total de todas as
			transações do período, resultando em uma linha por membro.
			<li><b>Resumo e transações</b> lista todas as transações do
			período, para cada membro, com uma cabeçalho adicional resumindo a
			informação. Isto resulta em um relatório longo, com uma visão geral
			das transações e balanços de cada membro, o qual pode ser usado para
			um extrato pessoal de conta para ser enviado aos membros. Contudo,
			calcular isto não leva mais tempo nem recursos do servidor do que
			apenas a opção de resumo.
		</ul>
	</ul>
	Se você selecionou <a href="${pagePrefix}invoices"><u>faturas
	eletrônicas</u></a>, a página de resultados mostrará a você uma lista de
	membros, e para cada membro da lista a quantidade de faturas e a soma
	total delas. Você possui as seguintes opções:
	<ul>
		<li><b>Incluir membros sem faturas eletrônicas</b>
		<li><b>Faturas de membros:</b> Você pode escolher incluir faturas
		de entrada e de saída.
		<li><b>Faturas de sistema:</b> (funciona da mesma maneira).
	</ul>
</ul>
<hr>


<a name="results"></a>
<h3>Resultados dos relatórios</h3>
Você poder escolher duas opções de ações nestes relatórios (botões no
final da página):
<ul>
	<li><b>Imprimir relatório:</b> Imprime o relatório na tela, uma um
	formato amigável. Esta tela também incluirá uma botão para enviar o
	relatório para a impressora.
	<li><b>Baixa relatório:</b> Resultará na exportação dos resultados
	para um arquivo no <a href="${pagePrefix}loans#csv"><u>formato
	CSV</u></a>.
</ul>
<hr class="help">
</span>


<a name="sms_log"></a>
<h3>Mensagens SMS enviadas</h3>
O sistema pode enviar diversas mensagens SMS em várias ocasiões (por
exemplo pagamentos através de SMS), dependendo da configuração.
<span class="admin"> Veja a <a href="${pagePrefix}settings#local"><u>ajuda
das configurações locais</u></a> para mais informações sobre esta configuração.</span>
<br><br>Esta janela permite que você obtenha uma visão geral das
mensagens SMS enviadas. O formulário é muito simples, e a maioria dos
elementos não necessitam de explicação. A caixa de &quot;estado&quot;
permite que você especifique se o envio das mensagens SMS foi feito com
sucesso ou se falhou. <br>
Após clicar em &quot;procurar&quot; os resultados serão exibidos na
janela abaixo.
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

