<div style="page-break-after: always;">
<br><br>Cyclos possui uma seção para análise
estatística dos seus dados><br>
As estatísticas são tudo sobre tentativa de quantificar exatamente como
os seus resultados são. Fazemos isso realizando testes estatísticos, de
onde nunca é possível a exatidão dos números.
<i>Onde encontrar.</i>
<br>
Você pode acessar esta seção através do &quot;Menu: Relatório > Análise
estatística&quot;.
<br><br><i>Como ativar.</i> As estatísticas possuem uma <a
	href="${pagePrefix}groups#manage_group_permissions_admin_system"><u>permissão</u></a>
que deve ser ativada antes dela ser visível. As permissões podem ser
encontradas em um bloco intitulado &quot;Relatórios&quot;, opção
&quot;estatísticas&quot;.
<br><br>Se você quiser pode usar certos <a
	href="${pagePrefix}groups#group_filters"><u>filtros de grupos</u></a>
ou <a href="${pagePrefix}account_management#payment_filters"><u>
filtros de pagamentos</u></a> em suas estatísticas, você terá que marcar os
filtros como visíveis para as estatísticas. Isto pode ser feito na
página de configuração destes filtros.
<hr>

<a name="choose_category"></a>
<h3>Escolher categoria de estatística</h3>
Esta janela da a opção de escolher a categoria da estatística. Clicar em
um dos botões irá leva-lo ao formulário correspondente, onde você pode
especificar os detalhes para a estatística ser calculada. Assim o
formulário atual é apenas para escolher a categoria principal.
<br>
Você pode escolher apenas uma categoria por vez. Como os cálculos
estatísticos podem ser muito pesados, permitir que todos os testes e
cálculos sejam realizados de uma só vez poderia causar uma sobrecarga no
servidor.
<br>
Você pode escolher uma das seguintes categorias:
<ul>
	<li><b>Progressos-chaves</b>: Os parâmetros principais,
	determinando a saúde e o crescimento do sistema (como número de
	membros, volume de transações, etc.).<br>
	<li><b>Atividade dos membros</b>: Estatísticas envolvendo cada
	atividade comercial dos membros.<br>
	<li><b>Estatísticas financeiras</b>: Estatísticas nas contas de
	sistema: todas os fluxos de entrada e saída para qualquer conta do
	sistema. <br>
</ul>
&nbsp; Mais opções estarão disponíveis em versões futuras do programa.
<hr class="help">

<a name="forms"></a>
<h2>Formulário das estatísticas</h2>
Após ter feito a escolha de qual
<a href="#choose_category"><u>categoria</u></a>
de estatísticas você quer ver, você será levado ao formulário onde você
pode especificar quais estatísticas você quer ver. Estes formulários
sempre possuem uma seção específica (que é diferente para cada
categoria) onde você pode escolher os itens em caixa de seleção, e uma
seção comum onde você pode definir parâmetros para as estatísticas, como
os
<a href="#periods"><u>períodos</u></a>
e diversos
<a href="#filters"><u>filtros</u></a>
.

Em cada formulário de estatísticas, você primeiro escolhe o tipo de
estatística que deseja ver, com a caixa de seleção
<a href="#whattoshow"><u>&quot;oque exibir&quot;</u></a>
.
<br>
Com as caixas à esquerda, você pode escolher quais itens deseja ver. O
nome dos itens são suficientemente explicativos, mas se você quiser
verificar a definição precisa deles, você pode consultar o
<a href="#glossary"><u>glossário</u></a>
de termos. Se você marcar uma caixa de gráfico sobre um assunto, então
normalmente será exibido a tabela e o gráfico, caso contrário apenas a
tabela será exibida.
<br>
Após ter selecionado uma ou mais opções, você deve especificar, na
janela abaixo, os
<a href="#parameters"><u>parâmetros comuns</u></a>
para a sua seleção. Quando pronto, marcado todas as opções desejadas,
clique no botão &quot;enviar&quot; no final da página, para ter tudo
calculado.

<br><br><b><u>Cuidado:</u> Por favor lembre-se que cálculos
estatísticos em bancos de dados grandes podem demorar algum tempo.</b>.
<hr>

<a name="key_development"></a>
<h2>Progressos-chave
</h3>
Aqui você pode especificar quais progressos-chave você quer que seja
calculado. Estatísticas de progressos-chave se destinam a obter uma
visão geral generalizada dos progressos do seus sistema. Você pode
comparar períodos e verificar por tendências, mas nenhuma análise
estatística é realizada.

Para uma descrição do formulário, <a href="#forms"><u>clique
aqui</u></a>.
<hr>

<a name="member_activity"></a>
<h2>Atividade dos membros
</h3>
Aqui você pode especificar quais estatísticas das atividades dos membros
você quer que seja calculada. Estatísticas das atividades dos membros
lhe dará conhecimento das atividades dos membros. Estatísticas das
atividades dos membros podem ser realizadas em diferentes conjuntos de
membros.

Para uma descrição do formulário, <a href="#forms"><u>clique
aqui</u></a>.
<hr>

<a name="finances"></a>
<h2>Finanças</h2>
Aqui você pode especificar quais estatísticas financeiras você quer que
seja calculada. Estatísticas financeiras podem lhe dar um visão geral
precisa de todos os fluxos de entrada e saída para/de qualquer conta do
sistema.

Para uma descrição do formulário, <a href="#forms"><u>clique
aqui</u></a>.
<hr>

<a name="taxes_fees"></a>
<h2>Taxas & Impostos</h2>
Aqui você pode especificar quais estatísticas de taxas e impostos você
deseja que seja calculada. Estas estatísticas dão a você os resultados
de qualquer taxa ou imposto de entrada. (provavelmente ainda em
construção)

Para uma descrição do formulário, <a href="#forms"><u>clique
aqui</u></a>.
<hr>

<a name="parameters"></a>
<h2>Parâmetros comuns e campos para o formulário de estatísticas</h2>
Antes de você ver os resultado das estatísticas, você deve especificar o
que deseja ver. Em geral, o Cyclos precisa saber poucas coisas antes de
começar os cálculos e exibir os resultado da estatística.
<ul>
	<li>&quot;<a href="#whattoshow"><u>Oque exibir</u></a>&quot;: Esta
	caixa de seleção está no topo do formulário, o primeiro item. Aqui você
	escolhe o método para exibir as estatísticas.<br>
	<li>&quot;<a href="#periods"><u>Períodos</u></a>&quot;: Esta caixa
	é exibida abaixo das caixas de opções. Aqui você especifica o período
	sobre o qual o resultado será calculado. Nem todos os dado são dados
	periódicos; Alguns dados não podem ser calculados sobre um período.
	Nestes casos o último dia do período é usado. Isto é sempre indicado no
	arquivo de ajuda.<br>
	<li>&quot;<a href="#filters"><u>Filtros</u></a>&quot;: Abaixo da
	caixa do período, existe uma caixa de filtros. Como o Cyclos não possui
	nenhum noção pré-definida sobre quais membros são, nem qual tipo de
	comércio é, você deve especificar isto através de filtros.<br>
</ul>

<hr>

<a name="whattoshow"></a>
<h3>Caixa de seleção &quot;Oque exibir&quot;</h3>
Com esta caixa de seleção, você especifica o método para exibir as
estatísticas. Você pode escolher entre os seguintes métodos:
<ul>
	<li>&quot;Período simples:&quot; Isto dá a você um resumo de um <a
		href="#periods"><u>período</u></a> simples. Normalmente, nenhuma
	análises estatística será realizada, portanto não é possível exibir
	nenhum gráfico.<br>
	<li>&quot;Comparar períodos:&quot; Isto compara os resultados para
	dois períodos, os quais podem ser apresentados em um gráfico de barra.
	Escolha isso se você desejar ver se um certo resultado cresceu ou
	decresceu comparado a um período anterior. Uma análise estatística é
	realizada para calcular se a diferença entre os dois períodos é <a
		href="#p"><u>estatisticamente significante</u></a>.<br>
	<li>&quot;Ao longo do tempo&quot;: faz o mesmo, mas através de um
	intervalo de tempo.<br>
	<li>&quot;Distribuição&quot;: Dá geralmente um <a href="#histo"><u>histograma</u></a>,
	um gráfico mostrando como um certo resultado é distribuído sobre a
	população.<br>
</ul>
Nem todas estas opções podem estar visíveis, elas dependem da categoria
de estatísticas escolhida.
<hr class="help">

<a name="periods"></a>
<h3>Períodos</h3>
Você deve especificar o período sobre o qual as estatísticas devem ser
calculadas.<br>
Dependendo do item que você escolheu, você pode especificar um ou dois
períodos, ou mesmo um intervalo de períodos.
<br><br>O &quot;período principal&quot; é o período sobre o qual as
estatísticas são calculadas. Este pode ser comparado ao &quot;Período
com o qual comparar&quot;. <br>
Para cada um dos dois períodos você deve fornecer um nome. Este nome
será usado no cabeçalho das colunas das tabelas e nas legendas dos
gráficos.<br>
Se você selecionou &quot;Através do tempo&quot;, a caixa de período será
um pouco diferente. Neste caso, você deve primeiro selecionar se você
deseja ver os resultados ordenados por ano, trimestre ou mês, após isso
você deve selecionar o intervalo de tempo sobre o qual as estatísticas
serão calculadas. <br>
<br>
Tenha cuidado para não selecionar um intervalo muito amplo: escolhendo
ver o resultado a cada mês sobre um período de 10 anos, não apenas
resultará em um gráfico superlotado, mas também irá gerar uma carga
bastante intensa no servidor, resultando em um tempo de espera elevado,
ou até mesmo em uma parada do servidor.<br>
Neste caso, uma janela popup lhe informará quantos &quot;pontos de
dados&quot; você pode solicitar no máximo, e solicita a você limitar a
sua requisição. Um &quot;ponto de dado&quot; refere-se a uma calculação
separada. Exemplo: Se você selecionar 5 itens nas caixa de seleção, e um
intervalo de 13 meses, isto irá resultar em 5 x 13 = 65 pontos de dados.
As caixas de opção para gráfico não contam neste caso, pois normalmente
elas não demandam calculações extras.
<hr class="help">

<a name="filters"></a>
<h3>Filtros</h3>
Antes de estar apto a calcular quantos membros exitem, e qual o volume
transacionado, o programa precisa saber oque você considera como
&quot;membro&quot; e oque você considera ser &quot;comércio&quot;. Como
Cyclos trabalha com uma multiplicidade de grupos de usuários
customizados e tipos de transações, a aplicação não possui uma noção
pre-definida do que é uma &quot;membro&quot; bem como um
&quot;comércio&quot;. Você deve especificar isto nesta janela, usando
&quot;filtros&quot;.
<br><br>Dependendo de quais itens você escolher, nem todos os filtros
podem estar visíveis. Os seguintes filtros podem estar visíveis:
<ul>
	<li><b>Filtro de membros: </b> Com este filtro, você especifica
	quais <a href="${pagePrefix}groups#member_groups"><u>grupos de
	membros</u></a> você considera ser membros. Você pode especificar mais de um
	grupo. Você <b>deve</b> especificar pelo menos um grupo.<br>
	Na página de resultados, os grupos que você selecionou aqui, serão
	indicados como &quot;membros&quot;.
	<br><br>
	<li><b><a
		href="${pagePrefix}account_management#payment_filters"><u>Filtro
	de pagamentos</u></a>: </b> Com este filtro, você especifica quais tipos de
	transações você deseja que sejam incluídos nos resultados. Normalmente
	isto é o que você considera ser &quot;comércio&quot;.<br>
	Você pode especificar apenas um filtro de pagamento, mas note a
	diferença com as caixas de &quot;Filtro(s) de pagamentos&quot;, que são
	descritas abaixo, e que nas quais você pode definir mais de um filtro.
	Caso não exista nenhum filtro disponível que se encaixe ao seu
	propósito, você sempre pode criar um novo filtro de pagamento
	específico. (veja abaixo)<br>
	<br>
	<b>Atenção:</b><br>
	Frequentemente, as caixas de filtros de pagamentos contêm apenas os
	pagamentos que são relevantes ao filtro do grupo escolhido. Pode
	acontecer de a caixa de filtro de pagamento estar vazia. Neste caso,
	você provavelmente selecionou um grupo que não possui pagamentos
	definidos. Neste caso, você não pode continuar com as estatísticas.
	isto só pode ser solucionado de duas maneiras: Ou selecionando um outro
	grupo, ou criando um novo filtro de pagamento para este grupo.
	<br><br>Para criar um novo filtro de pagamento para estatísticas:
	<ol>
		<li>Vá até &quot;Contas&quot; > &quot;Gerenciar contas&quot; no
		menu principal;
		<li>Então clique no ícone de edição da &quot;conta de
		membro&quot;;
		<li>Clique no botão no final da página, que é exibido com o
		título &quot;inserir novo filtro de pagamento&quot;.
		<li>Aqui você preenche as especificações do novo filtro de
		pagamento (veja também a ajuda desta página). Informe um nome lógico,
		não esqueça de marcar a opção &quot;exibir em relatórios&quot;, caso
		contrário ele não será exibido na página de estatísticas (você não
		precisa selecionar nada em &quot;visibilidade de grupo&quot;). <br>
		Você pode selecionar as transações que você quer incluir neste filtro
		nas caixa de &quot;tipos de transações&quot;.<br>
		<li>O filtro de pagamento deve ser exibido nas caixas de filtro
		de pagamentos na página selecionada para estatísticas.
	</ol>
	Na página de resultados de estatísticas, o tipo de pagamento que você
	especificou será indicado como &quot;comércio&quot;.
	<br><br>
	<li><b>FiltroS de pagamentos:</b> (observe o S). Este é exatamente
	o mesmo que o item anterior, apenas aqui você pode selecionar mais de
	um filtro. Se você selecionar mais de um filtro de pagamento, os
	resultados para os filtros de pagamentos selecionados são mostrados e
	comparados. Se você selecionar apenas um filtro, então este filtro é
	repartido nos tipos de transações que contêm, e o resultado para cada
	tipo de transação é mostrado.<br>
	<b>Atenção:</b> Quando selecionar mais de um filtro de pegamento aqui,
	é importante que todos os itens selecionados sejam distintos e não se
	sobreponham. Como cada filtro de pagamento pode conter vários tipos de
	transações, e possível que alguns filtros possuam tipos de transações
	em comum. Se este for o caso, não será possível criar gráficos como
	"gráficos de pizza", onde todas as seções juntas precisam somar 100%.
	Por essa razão, o programa efetua uma verificação para os tipos de
	transferências não se sobreponham.
	<br><br>
	<li><b>Filtro de corretor: </b> Com este filtro, você especifica
	quais grupos de usuários você considera ser de <a
		href="${pagePrefix}brokering"><u> corretores</u></a>. Como o filtro de
	membro, você pode especificar mais de um grupo, e você <b>deve</b>
	especificar pelo menos um. Nas páginas de resultado, estes grupos serão
	indicados como &quot;corretores&quot;.
</ul>
<br>
Apesar do cyclos mostrar apenas filtros relevantes, pode acontecer de
certas combinações de filtros não fazerem sentido. Por exemplo, se você
selecionar um filtro de grupo e um filtro de pagamento que não se
combinem ( ex. grupo de membros e pagamentos de sistema para comunidade
) então resultados estranhos podem acontecer.
<br><br>Nem todos os filtros são usados para todos os tipos de
estatísticas. Normalmente, na página de resultados, cada tabela ou
gráfico mostrará quais filtros foram <a href="#filtersUsed"><u>usados</u></a>
exatamente para o cálculo dos resultados.
<hr class="help">


<a name="results"></a>
<h2>Resultados estatísticos</h2>
Existem algumas convenções usados para apresentar os resultados
estatísticos do cyclos. Esta visão geral explica sobre essas convenções:
<ul>
	<li><a href="#tables"><u>Tabelas</u></a>: É forma padrão para
	apresentar as estatísticas. Clique no link para ir a descrição geral de
	tabelas.
	<li><a href="#graphs"><u>Gráficos</u></a>: São mostrados apenas se
	você selecionar a caixa gráfico. Clique no link para ir a descrição
	geral de gráficos.
	<li><a href="#tests"><u>teste estatísticos</u></a>: São realizados
	sempre que razoaveis e possíveis.
	<li><a href="#calculation"><u>Seno geral sobre as
	estatísticas</u></a>: Cálculos das estatísticas podem ser encontrados aqui.
	<li><a href="#numbers"><u>Apresentação e exatidão dos
	números</u></a>: Geralmente um resultado de 3, significa algo diferente que um
	resultado de 3.00. Clique no link para ir a descrição das convenções
	usadas no cyclos para exatidão e apresentação dos números.
	<li>Como o cyclos se comporta quando exitem <a href="#nodata"><u>muito
	poucos dados</u></a> disponíveis.
</ul>

<hr>

<a name="tables"></a>
<h3>Tabelas em estatísticas</h3>
A forma padrão de apresentar os dados nas estatísticas do cyclos, é em
tabelas. Muitas tabelas (não todas) tem a seguinte forma:
<ul>
	<li><b>Primeira coluna:</b> O <a href="#periods"><u>período</u></a>
	principal como você definiu na página anterior.
	<li><b>Segunda coluna:</b> O <a href="#periods"><u>período</u></a>
	para comparação, se você definiu isto na página anterior.
	<li><b>Terceira coluna:</b> O crescimento relativo (em %) do
	segundo período para o período principal.
	<li><b>Quarta coluna:</b> O <a href="#p"><u>valor-p</u></a> de um
	teste estatístico, testando quanto diferente são os números. Esta
	coluna não é sempre exibida.
</ul>
As colunas acima se referem normalmente para &quot;comparar
períodos&quot;; Se você escolher outro método, as tabelas podem ser
diferentes.<br>
Se você não solicitou um gráfico, então abaixo da tabela uma pequena <a
	href="#filtersUsed"><u>tabela</u></a> é mostrada, exibindo os <a
	href="#filters"><u>filtros</u></a> que você especificou para este
resultado. Caso contrário, esta informação é exibida abaixo do <a
	href="#graphs"><u>gráfico</u></a>.
<hr class="help">

<a name="graphs"></a>
<h3>Gráficos em estatísticas</h3>
Normalmente, um gráfico mostra os mesmos dados, que são exibidos na
tabela acima. Contudo, algumas colunas de dados secundários (dados que
são derivados de outras colunas) podem não ser exibidas na tabela, por
exemplo porcentagem de crescimento entre dois <a href="#periods"><u>períodos</u></a>
ou um <a href="#p"><u>valor-p</u></a>. Para explicação dos dados, por
favor clique no ícone de ajuda da tabela correspondente (a janela acima
do gráfico). Esta seção apenas explica sobre as convenções gerais usadas
nos gráficos.
<br><br>Se você posicionar o mouse sobre uma barra da gráfico, o valor
numérico dos dados correspondentes é mostrado no ponteiro do seu mouse.<br>
Abaixo do gráfico uma pequena <a href="#filtersUsed"><u>tabela</u></a> é
mostrada, exibindo os <a href="#filters"><u>filtros</u></a> que você
especificou para este resultado.
<hr class="help">


<a name="filtersUsed"></a>
<h3>Filtros usados</h3>
Abaixo do gráfico ou tabela, existe uma tabela adicional mostrando os <a
	href="#filters"><u>filtros</u></a> que você especificou para este
resultado. Para realizar estes cálculos, o cyclos precisa saber quais
tipos de membros devem ser incluídos no gráfico - isto é filtro de <a
	href="${pagePrefix}groups#group_filters"><u>grupo</u></a>. Também
precisa saber o que você considera ser &quot;comércio&quot; - isto é
especificado pelo &quot;filtro de pagamento&quot;. Também outros tipos
de filtros podem ser usados. <br>
Se um filtro não for usado para os cálculos, então &quot;não usado&quot;
é informado. Se o filtro foi usado, mas você não especificou grupo ou
pagamento em particular, então todos os membros ou todos os pagamentos
são incluídos. Apenas filtros relevantes são exibidos nesta tabela.
<hr class="help">

<a name="calculation"></a>
<h3>Como isto é calculado?</h3>
Geralmente, resultados são calculados <i>sobre o <a href="#periods"><u>período</u></a></i>
especificado. Isto significa, que por exemplo qualquer membro que foi um
membro em algum momento do período é incluído. Em ocasiões raras o
resultado é sobre o estado no último dia do período. Isto sempre é
afirmado na ajuda especifica. Observe que não há nenhuma correção para
&quot;membros parciais&quot;. For exemplo: O produto bruto de qualquer
membro dentro do período é contado sem nenhuma correção para o fato de
que o membro pode ter sido membro por apenas 1 dia do período.<br>
Na maior parte do tempo, o <a href="#median"><u>mediano</u></a> é usado
nos resultados, e não a média.
<hr class="help">

<a name="numbers"></a>
<h3>Apresentação e exatidão dos números</h3>
Números são geralmente apresentados com um intervalo em volta, se
possível. Este intervalo é um <a href="#range"><u>intervalo de
confiança</u></a> de 95% em torno da média ou <a href="#median"><u>mediana</u></a>.
Nas tabelas, esses números podem ser apresentados em três formas
diferentes:
<ul>
	<li><b>12.0</b> Significam que o intervalo de confiança não pode
	ser criado, por causa de existir poucos dados ou por causa do número
	não estar baseado em um conjunto de números.
	<li><b>12.0&nbsp;&#177;&nbsp;3.4</b> Significa que o intervalo de
	confiança é simétrico. 12.0 - 3.4 é o limite inferior do intervalo de
	confiança; 12.0 + 3.4 é o limite superior.
	<li><b>12.0 (9.7 - 19.2)</b> significa que o intervalo de
	confiança é assimétrico, e a <a href="#distribution"><u>distribuição
	é desviada</u></a>. Os números entre parênteses são os limites inferiores e
	superiores do intervalo de confiança.
	<li><b>-</b>&nbsp; significa que o número não pode ser calculado,
	provavelmente pelo fato de não existir dados suficientes disponíveis
	para tornar possível um calculo confiável.
</ul>
Em gráficos, os intervalos de confiança são indicados pelas barras de
erros em volta dos pontos de dados.
<hr class="help">

<a name="nodata"></a>
<h3>Muito poucos dados</h3>
Análise estatística é baseada no princípio de que um conjunto de
observações é embrulhado e representado por um número ou pontos em um
gráfico. Mas o que se este conjunto de observações é muito pequeno?<br>
Teoricamente, um teste estatístico pode ser efetuado com 3 ou mais
observações em um ponto de dados. Contudo, isso ainda torna o ponto de
dados muito sem confiança. Quanto maior o número de observações é (n),
melhor a confiabilidade da média, mediana ou intervalo.<br>
No Cyclos, não mostramos resultados estatísticos com menos de 15
observações, pois pensamos que a análise estatística não é confiável o
suficiente além deste ponto.
<hr class="help">

<h2>Resultados dos progressos-chave</h2>

<br><br>

<a name="reportsStatsKeydevelopmentsNumberOfMembers"></a>
<h3>Tabela para números de membros</h3>
Por favor veja a <a href="#results"><u>seção de resultados
gerais</u></a> para convenções gerais nas estatísticas do cyclos.
<br><br>As seguintes linhas são exibidas nesta tabela:
<ul>
	<li><b>Membros</b>: O número de membros de acordo com o <a
		href="#filters"><u>filtro</u></a> de membros que você definiu na
	página anterior. Isto conta qualquer membro que foi um membro durante o
	<a href="#periods"><u>período</u></a> que você especificou, mesmo que
	este tenha sido removido da associação durante o período, ou tornou-se
	um novo membro durante o período especificado.
	<li><b>Novos membros</b>: Qualquer membro que foi movido para o
	grupo de membro especificado durante o período informado.
	<li><b>Membros desaparecidos</b>: qualquer membro que foi -
	durante o período - movido do grupo especificado de membros para um
	grupo fora do filtro de grupos.
</ul>
<hr class="help">

<a name="reportsStatsKeydevelopmentsGrossProduct"></a>
<h3>Tabela para produto bruto</h3>
Por favor veja a <a href="#results"><u>seção de resultados
gerais</u></a> para convenções gerais nas estatísticas do cyclos.
<br><br>As únicas linhas mostram o <a href="#grossProduct"><u>produto
bruto</u></a> para os <a href="#filters"><u>filtros</u></a> especificados por
você.
<hr class="help">

<a name="reportsStatsKeydevelopmentsNumberOfTransactions"></a>
<h3>Tabela para número de transações</h3>
Por favor veja a <a href="#results"><u>seção de resultados
gerais</u></a> para convenções gerais nas estatísticas do cyclos.
<br><br>As únicas linhas mostram o <a href="#numberOfTransactions"><u>número
de transações</u></a> para os <a href="#filters"><u>filtros</u></a>
especificados por você.
<hr class="help">

<a name="reportsStatsKeydevelopmentsAverageAmountPerTransaction"></a>
<h3>Tabela para o valor mediano das transações</h3>
Por favor veja a <a href="#results"><u>seção de resultados
gerais</u></a> para convenções gerais nas estatísticas do cyclos.
<br><br>As únicas linhas mostram o valor <a href="#median"><u>mediano</u></a>
por transação, de acordo com os <a href="#filters"> <u>filtros</u></a>
que você especificou.
<hr class="help">

<a name="reportsStatsKeydevelopmentsHighestAmountPerTransaction"></a>
<h3>Tabela para o valor máximo das transações</h3>
Por favor veja a <a href="#results"><u>seção de resultados
gerais</u></a> para convenções gerais nas estatísticas do cyclos.
<br><br>As únicas linhas mostram o valor máximo por transação, de acordo
com os <a href="#filters"> <u>filtros</u></a> que você especificou.
<hr class="help">

<a name="reportsStatsKeydevelopmentsNumberOfAds"></a>
<h3>Tabela para número de anúncios</h3>
Por favor veja a <a href="#results"><u>seção de resultados
gerais</u></a> para convenções gerais nas estatísticas do cyclos.
<br><br>As seguintes colunas são exibidas:
<ul>
	<li><b>Anúncios ativos</b>: O número de anúncios que são vencidos,
	nem agendados, mas ativos no final do <a href="#periods"><u>período</u></a>.

	
	<li><b>Anúncios agendados</b>: O número de anúncios futuros
	(agendados) no final do período.
	<li><b>Anúncios vencidos</b>: O número de anúncios que não são
	mais válidos (vencidos) no final do período.
	<li><b>Anúncios criados</b>: O número de anúncios que são novos,
	foram criados durante o período.
</ul>
<br><br>Observe que os primeiros três itens são <i>no final do
período</i>, enquanto o último item é <i>sobre o período completo</i>.
<hr class="help">

<a name="reportsStatsKeydevelopmentsThroughTimeMonths"></a><br>
<a name="reportsStatsKeydevelopmentsThroughTimeQuarters"></a><br>
<a name="reportsStatsKeydevelopmentsThroughTimeYears"></a>
<h3>Tabela para &quot;Através do tempo&quot;</h3>
Por favor veja a <a href="#results"><u>seção de resultados
gerais</u></a> para convenções gerais nas estatísticas do cyclos.
<br><br>Se você escolheu &quot;através do tempo&quot; uma visão geral
histórica dos itens selecionados será exibida sobre o intervalo de tempo
que você selecionou.<br>
O itens são os mesmos itens da opção &quot;comparar períodos&quot;. <br>
Resumindo:
<ul>
	<li><b>Número de membros</b>: o número de membros sobre cada <a
		href="#periods"><u>período</u></a>.
	<li><b>Produto bruto</b>: sobre cada período.
	<li><b>Número de transações</b>: sobre cada período.
	<li><b>Valor das transações</b>: Media do valor das transações
	sobre cada período.
	<li><b>Número de anúncios</b>: Número de anúncios ativos no último
	dia de cada período.
</ul>
Onde o período pode ser meses, trimestres ou anos. E obviamente tudo de
acordo com os <a href="#filters"><u>filtros</u></a> que você
especificou.
<hr class="help">

<h2>Resultado da atividade dos membros</h2>

<br><br>

<a name="reportsStatsActivitySinglePeriodGrossProduct"></a> <a
	name="reportsStatsActivityComparePeriodsGrossProduct"></a>
<h3>Tabela para produto bruto por membro</h3>
Por favor veja a <a href="#results"><u>seção de resultados
gerais</u></a> para convenções gerais nas estatísticas do cyclos.
<br><br>Esta tabela mostra a <a href="#median"><u>mediana</u></a> do <a
	href="#grossProduct"><u>produto bruto</u></a> por membro em duas
linhas:
<ul>
	<li>&quot;por membro com rendimentos&quot;: Apenas os membros que
	possuam qualquer transação de entrada dentro do <a href="#periods"><u>
	período</u></a> solicitado são incluídos.
	<li><i>sobre todos os membros</i>: Todos os membros são incluídos.
	De acordo com a natureza da <a href="#median"><u>mediana</u></a> este
	valor será zero (0) caso mais que a metade dos membros não tenham
	rendimento.
</ul>
Obviamente, tudo de acordo com os <a href="#filters"><u>filtros</u></a>
que você escolheu.
<br><br>Para &quot;comparação de períodos&quot;, as colunas mostram o
resultado para os dois períodos solicitados, então duas colunas com o
número de membros nos quais o resultado é baseado, seguidas pelo
crescimento realizado entre estes dois períodos, e o <a href="#p"><u>valor-p</u></a>
para o teste de que os dois resultados não são diferentes.<br>
Para &quot;um período&quot;, apenas o resultado e o número de membros é
exibido.<br>
<hr class="help">

<a name="reportsStatsActivitySinglePeriodNumberTransactions"></a> <a
	name="reportsStatsActivityComparePeriodsNumberTransactions"></a>
<h3>Tabela para número de transações por membros</h3>
Por favor veja a <a href="#results"><u>seção de resultados
gerais</u></a> para convenções gerais nas estatísticas do cyclos.
<br><br>Esta tabela mostra a <a href="#median"><u>mediana</u></a> do <a
	href="#numberOfTransactions"><u>número de transações</u></a> por
membros em duas linhas:<br>
<ul>
	<li>&quot;por membro transacionando&quot;: Apenas os membros que
	tiveram transações dentro do <a href="#periods"><u>período</u></a>
	solicitado são incluídos, ambas transações de entrada e saída são
	contadas.
	<li>&quot;sobre todos os membros&quot;: Todos os membros são
	incluídos. De acordo com a natureza da <a href="#median"><u>mediana</u></a>,
	este valor será zero (0) caso mais da metade dos membros não tenham
	transacionado.
</ul>
Obviamente, tudo de acordo com os <a href="#filters"><u>filtros</u></a>
que você escolheu.
<br><br>Para &quot;comparação de períodos&quot;, as colunas mostram os
resultados para os dois períodos solicitados, então duas colunas com os
números de membros nos quais o resulta é baseado, seguidas pelo
crescimento realizado entre estes dois períodos, e o <a href="#p"><u>valor-p</u></a>
para o teste de que os dois resultados não são diferentes.<br>
Para &quot;um período&quot;, apenas o resultado e o número de membros é
exibido.<br>
<hr class="help">

<a name="reportsStatsActivitySinglePeriodPercentageNoTrade"></a> <a
	name="reportsStatsActivityComparePeriodsPercentageNoTrade"></a>
<h3>Tabela para porcentagem de membros não transacionando</h3>
Por favor veja a <a href="#results"><u>seção de resultados
gerais</u></a> para convenções gerais nas estatísticas do cyclos.
<br><br>Apenas uma linha é exibida, indicando a porcentagem de membros
que não transacionaram durante o <a href="#periods"><u>período</u></a>
que você solicitou (significando que não pagou ou recebeu nenhuma soma).
Como sempre, &quot;membros&quot; e &quot;transações&quot; definidas de
acordo com os <a href="#filters"><u>filtros</u></a> que você selecionou.


<br><br>Para &quot;comparação de períodos&quot;, as colunas mostram os
resultados para os dois períodos solicitados, o crescimento realizado
entre estes dois períodos, e o <a href="#p"><u>valor-p</u></a> para o
teste de que os dois resultados não são diferentes.
<hr class="help">

<a name="reportsStatsActivitySinglePeriodLoginTimes"></a> <a
	name="reportsStatsActivityComparePeriodsLoginTimes"></a>
<h3>Tabela para número de acesso por membros, um período.</h3>
Por favor veja a <a href="#results"><u>seção de resultados
gerais</u></a> para convenções gerais nas estatísticas do cyclos.
<br><br>Apenas uma linha é exibida, indicando o número de acesso por
membro durante o <a href="#periods"><u>período</u></a>.
&quot;Membros&quot; obviamente de acordo com o <a href="#filters"><u>filtro</u></a>
de grupo que você especificou - um membro é contado como membro caso
tenha sido membro em qualquer momento dentro do período especificado.
<br><br>Para &quot;comparação de períodos&quot;, as colunas mostram os
resultados para os dois períodos solicitados, o crescimento realizado
entre este dois períodos, e o <a href="#p">valor-p</a> para o teste de
que os dois resultados não são diferentes.
<hr class="help">

<a name="reportsStatsActivityThroughTimeGrossProduct"></a>
<h3>Tabela para produto bruto por membro através do tempo</h3>
Por favor veja a <a href="#results"><u>seção de resultados
gerais</u></a> para convenções gerais nas estatísticas do cyclos.
<br><br>Esta tabela da uma visão geral da <a href="#median"><u>mediana</u></a>
do <a href="#grossProduct"><u>produto bruto</u></a> por membro para cada
ponto no intervalo de tempo que você especificou. É feita para dois
tipos de membros:
<ul>
	<li>&quot;com rendimentos&quot;: este é o produto bruto para
	membros que tiveram rendimentos no mês, trimestre ou ano especificado.

	
	<li>&quot;todos&quot;: isto incluí todos os membros disponíveis no
	mês, trimestre ou ano especificado.
</ul>
Para ambos grupos, o número de membros nos quais o produto bruto é
baseado, é mostrado nas duas últimas colunas.<br>
Note que naturalmente, quanto menor o tempo para cada ponto do gráfico
ou tabela, menor o resultado será.
<hr class="help">

<a name="reportsStatsActivityThroughTimeNumberTransactions"></a>
<h3>Tabela para número de transações por membro através do tempo</h3>
Por favor veja a <a href="#results"><u>seção de resultados
gerais</u></a> para convenções gerais nas estatísticas do cyclos.
<br><br>Esta tabela mostra a <a href="#median"><u>mediana</u></a> do <a
	href="#numberOfTransactions"><u>número de transações</u></a> por membro
sobre o intervalo de tempo especificado, onde ambas transações, de
entrada e saída, são contadas. <br>
É calculada para dois tipos de membros:
<ul>
	<li>&quot;comerciantes&quot;: Esta da o número de transações para
	os membros que fizeram comércio dentro o mês, trimestre ou ano
	especificado (comércio significa: recebeu ou pagou qualquer quantia).
	<li>&quot;todos&quot;: Esta inclui todos os membros disponíveis no
	mês, trimestre ou ano especificado.
</ul>
Para ambos grupos, o número de membros nos quais o produto bruto é
baseado, é mostrado nas duas últimas colunas.<br>
Note que naturalmente, quanto menor o tempo para cada ponto do gráfico
ou tabela, menor o resultado será.
<hr class="help">

<a name="reportsStatsActivityThroughTimePercentageNoTrade"></a>
<h3>Tabela para porcentagem de membros não transacionando, através
do tempo</h3>
Por favor veja a <a href="#results"><u>seção de resultados
gerais</u></a> para convenções gerais nas estatísticas do cyclos.
<br><br>Esta tabela da uma visãp geral da porcentagem de membros não
transacionando durante um <a href="#periods"><u>período</u></a>, sobre o
intervalo de tempo especificado.
<br><br>Um membro é considerado &quot;não transacionando&quot; caso ele
não realize nenhuma transação (não pagou nem recebeu, e de acordo com o
<a href="#filters"><u>filtro</u></a> de pagamento que você especificou)
no mês, trimestre ou ano especificado. Obviamente, quanto menor é o
tempo para cada ponto do gráfico, maior será a porcentagem de membros
não transacionando. Em um gráfico com os resultados dados por mês, a
porcentagem de membros não transacionando será naturalmente muito maior
do que em um gráfico com os resultados dados por ano.<br>
A última coluna da tabela da um número de membros nos quais o valor
resultado é baseado.
<hr class="help">

<a name="reportsStatsActivityThroughTimeLoginTimes"></a>
<h3>Tabela de acesso por membros, através do tempo</h3>
Por favor veja a <a href="#results"><u>seção de resultados
gerais</u></a> para convenções gerais nas estatísticas do cyclos.
<br><br>Esta tabela da uma visão geral histórica da <a href="#median"><u>mediana</u></a>
do número de acessos que um membro fez no sistema, sobre o intervalo de
tempo especificado. Não apenas o número de acesso em cada mês, trimestre
ou ano é dado, na última coluna também é dado o número de membros nos
quais o cálculo é baseado. <br>
Note que naturalmente, quanto menor o tempo para cada ponto do gráfico,
menor será o número de acessos por membro.
<hr class="help">

<a name="reportsStatsActivityHistogramGrossProduct"></a>
<h3>Histograma para produto bruto por membro</h3>
Por favor veja a <a href="#results"><u>seção de resultados
gerais</u></a> para convenções gerais nas estatísticas do cyclos.
<br><br>Este gráfico da um <a href="#histo"><u>histograma</u></a> do <a
	href="#grossProduct"><u>produto bruto</u></a> por membro sobre o
período principal, de acordo com os <a href="#filters"><u>filtros</u></a>
que você definiu.
<hr class="help">

<a name="reportsStatsActivityHistogramNumberTransactions"></a>
<h3>Histograma para número de transações por membro</h3>
Por favor veja a <a href="#results"><u>seção de resultados
gerais</u></a> para convenções gerais nas estatísticas do cyclos.
<br><br>Este gráfico da um <a href="#histo"><u>histograma</u></a> do <a
	href="#numberOfTransactions"><u>número de transações</u></a> por membro
para o período principal, de acordo com os <a href="#filters"><u>filtros</u></a>
que você definiu.
<hr class="help">

<a name="reportsStatsActivityHistogramLogins"></a>
<h3>Histograma para número de acessos por membro</h3>
Por favor veja a <a href="#results"><u>seção de resultados
gerais</u></a> para convenções gerais nas estatísticas do cyclos.
<br><br>Este gráfico da um <a href="#histo"><u>histograma</u></a> do
número de acessos por membro para o <a href="#periods"><u>período</u></a>
principal, de acordo com os <a href="#filters"><u>filtros</u></a> que
você definiu.
<hr class="help">

<a name="reportsStatsActivityToptenGrossProduct"></a>
<h3>Os dez mais ativos por produto bruto</h3>
Por favor veja a <a href="#results"><u>seção de resultados
gerais</u></a> para convenções gerais nas estatísticas do cyclos.
<br><br>Esta tabela da os dez membros com os maiores <a
	href="#grossProduct"><u>produtos brutos</u></a> durante o <a
	href="#periods"><u>período</u></a> principal especificado.
<hr class="help">

<a name="reportsStatsActivityToptenNumberTransactions"></a>
<h3>Os dez membros mais ativos por número de transações</h3>
Por favor veja a <a href="#results"><u>seção de resultados
gerais</u></a> para convenções gerais nas estatísticas do cyclos.
<br><br>Esta tabela da os dez membros com os maiores <a
	href="#numberOfTransactions"><u>números de transações</u></a> durante o
<a href="#periods"><u>período</u></a> principal especificado.
<hr class="help">

<a name="reportsStatsActivityToptenLogin"></a>
<h3>Os dez membros mais ativos por número de acessos</h3>
Por favor veja a <a href="#results"><u>seção de resultados
gerais</u></a> para convenções gerais nas estatísticas do cyclos.
<br><br>Esta tabela da os dez membros com os maiores números de acessos
durante o <a href="#periods"><u>período</u></a> principal especificado.


<hr class="help">

<h2>Resultado da atividade dos membros</h2>

<br><br>

<a name="reportsStatsFinancesSinglePeriodOverview"></a>
<h3>Visão geral das finanças em um período</h3>
Por favor veja a <a href="#results"><u>seção de resultados
gerais</u></a> para convenções gerais nas estatísticas do cyclos.
<br><br>Esta tabela da uma visão geral dos pagamentos de entrada e saída
para a conta de sistema especificada. A coluna final mostra a diferença
entre receita e despesa.
<br><br>Os <a href="#filters"><u>filtros</u></a> de pagamentos que você
especificou são mostrados na tabela. Filtros de pagamentos que você não
especificou, mas que são relevantes para esta conta, são coletados e
resumidos como &quot;outros&quot;. Observe que esta categoria
&quot;outros&quot; não é exibida no gráfico, pois caso esta seja um
número grande, irá ignorar o resto da imagem.
<br><br>Se você selecionar apenas um filtro de pagamento, então este
filtro é repartido nos tipos de transferências que contêm, e os tipos de
transferências são exibidos na tabela. Caso contrário os filtros de
pagamentos são exibidos.<br>
Se um gráfico também for exibido, então abaixo do gráfico os filtros
selecionados são exibidos, caso contrário estes são exibidos abaixo da
tabela.
<hr class="help">

<a name="reportsStatsFinancesSinglePeriodIncome"></a> <a
	name="reportsStatsFinancesSinglePeriodExpenditure"></a>
<h3>Receita e despesa sobre um período</h3>
Por favor veja a <a href="#results"><u>seção de resultados
gerais</u></a> para convenções gerais nas estatísticas do cyclos.
<br><br>Esta tabela ou gráfico da uma visão geral da receita (no caso de
ter escolhido receita) ou despesa (no caso de ter escolhido despesa)
para uma conta de sistema.<br>
A tabela da os dois <a href="#periods"><u>períodos</u></a> especificados
nas colunas, onde a terceira coluna mostra o crescimento do período II
para o período I.<br>
As linhas da tabela dão os <a href="#filters"><u>filtros</u></a> de
pagamentos que você especificou.
<br><br>Para manter uma visão geral, sugerimos não selecionar muitos
filtros de pagamentos ao gerar a tabela/gráfico.<br>
<hr class="help">

<a name="reportsStatsFinancesThroughTimeIncome"></a> <a
	name="reportsStatsFinancesThroughTimeExpenditure"></a>
<h3>Receita e despesa através do tempo</h3>
Por favor veja a <a href="#results"><u>seção de resultados
gerais</u></a> para convenções gerais nas estatísticas do cyclos.
<br><br>Esta tabela da uma visão geral das categorias de receitas (ou
despesas) através do tempo. Você pode querer ver isso se estiver
interessado em como certas despesas aumentaram através do tempo.<br>
Cada filtro de pagamento solicitado possui sua coluna na tabela e seu
conjunto no gráfico, como gráfico se tornará facilmente super populado.

<hr class="help">


<br><br><a name="glossary"></a>
<h2>Glossário de termos</h2>
<br><br>


<a name="range"></a><b>Intervalo de confiança</b>
<br><br>Um intervalo de confiança é uma indicação da precisão dos dados.
Isso significa que existe uma chance de 95% de que os números
encontrados se situem nesse intervalo. Frequentemente, desvios padrões
ou erros padrões do significado são usados para indicar a precisão dos
dados. Nós não usamos isso pois acreditamos que um intervalo de
confiança de 95% é muito mais intuitivo, e possui um significado no
mundo real (ao contrário do desvio padrão, que é um tanto abstrato).
<br><br>Intervalos de confiança são normalmente calculados na suposição
de alguma <a href="#distribution"><u>distribuição</u></a> dos dados
subjacentes. No nosso caso, na maior parte do tempo não supomos
distribuição normal, usamos uma distribuição binominal da fila das somas
envolta da mediana para calcular o intervalo de confiança. Isso
significa que os valores absolutos não são usados diretamente, mas
enfileirados do menor ao maior, e esta fila de números é usada para
calcular o intervalo de confiança. Isto pode algumas vezes conduzir a
intervalos de confiança assimétricos.
<hr class='help'>

<a name="distribution"></a> <b>Distribuição e desenviesados</b>
<br><br>Em estatísticas, um resultado é baseado em um conjunto de
observações, a partir dos quais é calculada um significado ou <a
	href="#median"> <u>mediana</u></a>. Todas essas observações simples
juntas, normalmente. seguem um certo padrão, o qual é chamado de uma
distribuição dos números. A distribuição mais natural e frequente
encontrada é a distribuição normal, onde o valor central é o maior
abundante, e os valores ao redor tornam-se menos abundante conforme for
maior a distancia do valor central. Uma distribuição normal é
perfeitamente simétrica. <br>
A maior parte dos métodos estatísticos são baseados na suposição de uma
distribuição normal. Contudo, o cotidiano frequentemente não é tão
perfeito. A experiência aprende que os dados da maior parte dos bancos
de dados do cyclos não são em todos distribuídos normalmente. O dados do
Cyclos frequentemente parecem ser enviesados, com distribuições
assimétricas. Para dar um exemplo: isso pode significar que membros
tendo um produto bruto maior que a mediana, frequentemente são mais
distantes da mediana do que membros tendo um produto bruto menor.<br>
Para mostrar a distribuição de alguma coisa, usamos &quot;<a
	href="#histo"><u>histogramas</u></a>&quot;.<br>
<hr class='help'>

<a name="grossProduct"></a> <b>Produto bruto</b>
<br><br>Produto bruto é a soma dos valores de todas as transações de <b>entrada</b>
sobre um certo <a href="#periods"><u>período</u></a>. Assim todas as
unidades ganhas em um período.
<hr class='help'>

<a name="histo"></a> <b>Histograma</b>
<br><br>Um histograma é um gráfico mostrando quantas observações são
distribuídas por uma população. Horizontalmente (no eixo-x) no gráfico é
o parâmetro no qual você esta interessado (por exemplo: produto bruto de
cada membro). Isto é dividido em grupos lógicos, por exemplo: produtos
brutos variando de 0 a 100 unidades por mês, 100 a 200 unidades por mês,
etc, etc. Verticalmente, no eixo-y, existe o número de observações
contadas em cada grupo do eixo-x. Assim no nosso exemplo: 5 membros no
grupo de 0 a 100 unidades, 20 membros no grupo de 100 a 200 unidades por
mês, etc, etc.<br>
O gráfico resultante disto mostra a <a href="#distribution"><u><u>distribuição</u></u></a>
do produto bruto pessoal sobre membros.<br>
Observe que em nossos histogramas, observações que se enquadram
exatamente na fronteira entre duas barras, são contadas como pertencendo
à barra com o maior valor da categoria.
<br><br><b>Nota:</b> O programa calcula automaticamente a melhor divisão
do eixo-x em &quot;classes&quot; atraentes. No caso de um distribuição
muito estranha (por exemplo, a maioria ds membros não fazendo nada) de
dados subjacentes, a divisão de classes ao longo do eixo-x pode não ser
a melhor, visualmente.
<hr class='help'>

<a name="median"></a> <b>Mediana</b>
<br><br>Normalmente, o meio ou a média seria usado para indicar o valor
de um número baseado em um conjunto de observações. Contudo, a média é
muito sensível à extremas ao redor: se um membro tem uma atividade a
qual é 20 vezes maior que o resto dos membros, este membro influência
muito a média, mesmo embora este membro não é em todo representativo. A
solução para superar isso é usar a mediana em vez da média. A mediana é
o centro da <a href="#distribution"><u>distribuição</u></a>: 50% de
todas as observações é menor que a mediana, e 50% de todas as
observações é maior que a mediana. A mediana não é sensível a valores
distantes.<br>
Como cyclos lida com dados que frequentemente possui distâncias extremas
ou distribuições enviasadas, em toda parte das estatísticas do cyclos a
mediana é usada - salvo indicações diferentes. O uso da mediana em
muitos casos é geralmente padrão no mundo das estatísticas.
<br><br>No caso do cálculo da mediana de um conjunto de inteiros (= todos
números), usamos o equilíbrio ou correção de justiça. O valor mediano
pertencente a um conjunto {0,1,2,2,3,3,3,3,4} e para {2,3,3,3,3,4,5,6,7}
são ambos 3, embora na primeira lista, os 3 primeiros são tomados, e na
segunda lista os últimos 3. Como isso não parece &quot;justo&quot;,
todos os elementos com valor 3 são espalhados igualmente sobre o
intervalo de 2.5 a 3.5, e então o valor é recuperado deste intervalo
através da interpolação. Isto é óbvio não faz sentido com listas de
números quebrados, assim esta confrontação é usada apenas no caso de
inteiros.
<br><br>Usar a mediana em vez da média tem poucas <b>consequências</b>:
<ul>
	<li>Números redondos podem ser o resultado, especialmente no caso
	de uma mediana baseada em poucas observações.<br>
	<li>Como intervalos são calculados em números enfileirados, dar
	intervalos em volta de medianas, frequentemente pode ser assimétrico.
	Isto considera natural, embora considerando o fato que distribuições
	subjacentes não são simétricas também.<br>
	<li>No caso de distribuições subjacentes com muitos zeros (mais
	que 50%), a mediana será obviamente 0 também. <br>
</ul>
<hr class='help'>

<a name="membersNotTrading"></a> <b>Membros não transacionando</b>
<br><br>Estes são membros que tem 0 transações. Assim nenhuma transação
de entrada bem como nenhuma de saída.
<hr class='help'>

<a name="N"></a> <b>n</b>
<br><br>o número de itens em um conjunto de números.
<hr class='help'>

<a name="numberOfTransactions"></a> <b>Número de transações</b>
<br><br>Ao contrário do <a href="#grossProduct"><u>produto bruto</u></a>,
para a soma de algumas transações, ambas transações de entrada e saída
de um membro são contadas.
<hr class='help'>

<a name="p"></a> <b>Valor-p</b>
<br><br>Ao comparar dois ou mais <a href="#periods"><u>períodos</u></a>,
um teste estatístico é realizado sempre que possível. O objetivo de
qualquer um desses testes é, calcular quão diferentes os dois valores
são.<br>
O resultado do teste é representado por um número: o
&quot;valor-p&quot;. Este valor indica a chance destas duas médias (ou
medianas) ser de uma população igual. Em linguagem clara: É a chance dos
números serem realmente iguais. Quanto menor o valor-p, mais diferentes
os números são.<br>
Por convenção, dizemos que estes números são realmente diferentes caso o
valor-p seja menor que 5%. Isto é chamado &quot;diferença significante
estatisticamente&quot;. Em linguagem clara: &quot;Se p < 0.05, dizemos
que os números são realmente diferentes. Se p é maior, então não podemos
estar claros se os números são diferentes ou não, mas para ser do lado
claro, assumimos que eles não são diferentes.&quot; <br>
Qualquer valor-p menor que 5% é imprimido em <b>negrito</b>. <br>
<hr class='help'>

<a name="tests"></a><b>Testes estatísticos</b>
<br><br>Um teste estatístico é normalmente realizado se você quiser saber
se dois (ou mais) resultados são realmente diferentes ou não - por
exemplo, você pode querer saber se a atividade dos membros neste ano
cresceu, comparada com a atividade do ano passado. Se uma diferença de
5% é realmente diferente? E uma diferença de 10%? 20? Onde começamos a
chamar uma diferença de diferença real, e quando devemos considerar isto
mera coincidência?<br>
Os testes estatísticos pode dizer se as diferenças são diferenças <b>reais</b>,
ou se a diferença noticia cai apenas dentro dos intervalos normais de
variação e coincidência, e assim não deve ser considerada realmente como
diferença. Isto depende da <a href="#distribution"><u>
distribuição</u></a> subjacente, sobre o tamanho da amostra, e sobre a variação
da população.<br>
O tipo do teste é normalmente mencionado no arquivo de ajuda. Os testes
usados no Cyclos geralmente são o Wilcoxon RankSumTest, e o teste
binominal para duas amostras proporcionais. Estes testes não assumem
nenhuma distribuição subjacente, como, na maioria dos casos, não podemos
assumir distribuições normais.<br>
O resultado de um teste estatístico é expressado como um <a href="#p"><u>valor-p</u></a>.<br>
<hr class="help">
</div> <%--  page-break end --%>