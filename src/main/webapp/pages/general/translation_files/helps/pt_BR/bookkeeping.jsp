<div style="page-break-after: always;">
<br><br>A função de contabilidade pode ser usada
para unir &quot;contas externas&quot;, como contas bancárias ou contas
de outros sistemas monetários, à contas do sistema Cyclos.
<br><br>Como a função de contabilidade permite ter um espelhamento
completo de contas externas (e suas transações) localmente no cyclos, é
possível administrativamente &quot;ligar&quot; as transações do cyclos
às transações importadas. Isto significa que ambas transações terão uma
referência entre elas e receberão um estado extra. Este estado é
mostrado como um ícone específico na janela de extrato de transações.
Também é possível procurar pelo estado e imprimir resultados na lista de
transações.
<br><br>Um uso típico do módulo de contabilidade é para controlar o
balanço do sistema onde as unidades internas (cyclos) são respaldadas
com moeda convencional em contas bancárias. Neste caso as transações na
conta bancária tem uma relação direta com transações de unidades
específicas no cyclos. Por exemplo, um depósito (pagamento de entrada)
no banco pode ser relacionado com um pagamento de sistema para membro,
que pode ser a &quot;compra&quot; de unidades do cyclos ou o pagamento
de um empréstimo no Cyclos. E, de uma outra forma, um pagamento a partir
da conta bancária (débito) para a conta bancária de um membro, pode ser
relacionado com um pagamento de membro para sistema (ex. conversão) no
cyclos. A função de conciliação ajudará a controlar o
&quot;respaldo&quot; das unidades do cyclos. Por exemplo, &quot;o
balanço de conciliação&quot; de um sistema onde as unidades internas são
100% respaldadas com moeda convencional será de um para um.
<br><br>É possível gerar automaticamente tipos de transações específicas
(cyclos) à partir de transações importadas. Por exemplo um pagamento de
entrada em uma conta bancária pode gerar no Cyclos um pagamento de
sistema para membro.<br>
Isto é explicado em detalhes na seção de configuração de importação do
módulo de contabilidade.
<br><br>O controle do &quot;respaldo&quot; é apenas um exemplo de uso do
módulo de contabilidade. O módulo pode ser implementado para outros
casos onde uma transação externa necessita gerar transações locais, ou
trocar o estado de um empréstimo em um sistema local.

<i>Onde encontrar.</i>
<br>
O módulo de contabilidade pode ser acessado através do &quot;Menu:
Contabilidade&quot; (para esse menu ser exibido é preciso configurar as
permissões para o grupo de administradores.)

<br><br><i>Como ativar.</i><br>
Para o módulo ser visível você necessitará as <a
	href="${pagePrefix}groups#manage_group_permissions_admin_system"><u>permissões
de administrador</u></a> corretas no bloco &quot;Contas externas
(Contabilidade)&quot;.
<br><br>Antes de estar apto a importar novas transações externas no
cyclos, você deve efetuar os seguintes passos:
<ol>
	<li>Verifique se <a
		href="${pagePrefix}account_management#transaction_types"><u>
	tipos de transações</u></a> que você necessitará para essas transações existem.

	
	<li>Crie uma conta externa através do &quot;Menu: Gerenciar Contas
	externas > nova conta externa&quot;.
	<li><b>Mapeamento de arquivo:</b> Crie uma definição para o
	arquivo a ser importado através da janela abaixo da que você criou a
	conta externa
	<li><b>Mapeamento de campo:</b> Diga como o cyclos deve tratar
	todos os campos desse arquivo na próxima janela.
	<li><b>Mapeamento de tipo de pagamento:</b> Diga ao cyclos quais
	valores no campo que definem tipos de pagamentos devem ser mapeados a
	tipos de pagamentos, isto é feito na próxima janela.
</ol>
Apenas após você ter feito esses passos você estará apto a importar
novas transações externas, através do arquivo de transações que você
recebeu da sua conta bancária.
<a href="#using"><u>Clique aqui</u></a>
para ter uma visão geral de como importar novas transações a partir do
arquivo.
<hr>


<A NAME="external_accounts_list"></A>

<h3>Listar contas externas</h3>
Esta janela mostra uma lista com todas as contas externas.
<ul>
	<li><img border="0" src="${images}/edit.gif" width="16"
		height="16"> Clique no ícone para modificar a conta externa.
	<li><img border="0" src="${images}/delete.gif" width="16"
		height="16"> Clique no ícone para apagar a conta externa.
</ul>
Você pode adicionar uma nova conta externa através do botão &quot;Nova
conta externa&quot;.
<hr class="help">


<A NAME="new_edit_external_account"></A>

<h3>Nova / editar conta externa</h3>
Nesta página você pode definir uma conta externa, o sistema interno e as
contas de membros relacionadas a ela.
<br>
Transações nas contas externas serão geralmente relacionadas a
transações que envolvem a criação de unidades (ex. empréstimos) e a
destruição (ex. conversão). Portanto a conta de sistema relacionada a
uma conta externa normalmente deve ser do tipo &quot;ilimitada&quot;.
Mais informações podem ser encontradas no
<a href="${pagePrefix}account_management#account_details"><u>
arquivo de ajuda</u></a>
da &quot;criação de conta&quot;.
<hr class="help">


<A NAME="edit_file_mapping"></A>

<h3>Editar mapeamento de arquivo</h3>
Para importar a informação da transação para uma conta externa você
precisará definir o mapeamento entre os campos da conta externa e os
campos no cyclos. Na primeira vez após criar uma conta externa você sera
questionado para criar um novo mapeamento de arquivo. Os seguintes
mapeamentos estão disponíveis.
<ul>
	<li><b>Tipo: </b>CSV ou customizado (arquivo de classe)<br>
	Normalmente arquivos de transações contém texto puro onde os valores
	são separado por um caractere separador. Neste caso você pode usar a
	opção <a href="${pagePrefix}loans#csv"> <u>CSV</u></a>.<br>
	No caso do arquivo de transação ter um formato mais complexo, por
	exemplo xml, é possível programar uma classe customizada para manusear
	este formato. Esta documentação não se destina a detalhes técnicos, mas
	você necessitará fazer o seguinte:
	<ol>
		<li>Crie uma classe Java que implemente a interface
		TransactionFileImport do cyclos (no pacote utils).
		<li>Coloque esta classe no classpath e na pasta WEB-INF/classes
		do servidor ou no diretório e bibliotecas compartilhadas caso esta
		seja um pacote jar.
	</ol>
	<li><b>Coluna separadora: </b><br>
	O caractere separador usado no arquivo, normalmente uma vírgula
	&quot;,&quot;.
	<li><b>Linhas de cabeçalho: </b><br>
	O número de linhas do cabeçalho (que não contenham os valores atuais).
	Estas linhas serão ignoradas.
	<li><b>Formato do número: </b><br>
	Este tem duas opções possíveis:
	<ul>
		<li><b>Posição fixa:</b> Em alguns casos o formato do valor no
		arquivo de transações não possui separador mas o separador é fixo e
		calculado a partir da direita. Por exemplo um valor de 50000 com duas
		casas decimais resultará em 500 ou (500,00 com a vírgula).<br>
		Se você selecionar esta opção, o campo a direita deste será chamado
		&quot;Casas decimais&quot;, e aqui você normalmente colocará
		&quot;2&quot;.
		<li><b>Com separador:</b> Normalmente um separador é usado; você
		pode definir este campo &quot;Separador decimal&quot; (à direita).
		Este é normalmente um ponto &quot;.&quot; ou uma vírgula.
	</ul>
	<li><b>Caractere Negativo: </b><br>
	Em alguns casos o formato do campo valor no arquivo de transações nunca
	é negativo, mas para números negativos existe um caractere especial em
	uma coluna separada. Este pode ser por exemplo &quot;-&quot; ou um D
	(débito). Por exemplo a coluna valor |-500,00| pode ser o mesmo que a
	coluna |D|500,00| (onde o | é o caractere separador de coluna). <br>
	Normalmente o valor tem seu caractere negativo no mesmo campo e a
	coluna caractere negativo não é necessária.
	<li><b>Delimitador de texto: </b><br>
	Arquivos de transações e arquivos CSV frequentemente contêm campos para
	texto (para prevenir quebras de linhas e colunas). Aqui você pode
	definir os delimitadores de texto (normalmente ").
	<li><b>Formato da data: </b><br>
	Aqui você pode definir o formato da data. Você pode usar y para ano, M
	para mês (deve ser maiúsculo) e d para dia. Você pode usar qualquer
	separador entre os valores, como dd/mm/yy ou yyyy-MM-dd etc.
</ul>
Clique &quot;Enviar&quot; para submeter os mapeamentos de arquivo.
Clicando o botão &quot;Limpar&quot; irá remover todos os mapeamentos e
você sera avisado para criar um novo. Os tipos de pagamentos (janela
abaixo)não serão removidos quando limpar os mapeamentos.
<hr class="help">


<A NAME="file_mapping_fields_list"></A>

<h3>Campos de arquivo mapeado</h3>
Uma vez que o formato do mapeamento de arquivo é definido (janela acima)
você pode mapear campos do arquivo de transações à campos do Cyclos.
Todas as linhas na lista representarão uma linha no arquivo de transação
e todo campo é uma coluna.
<br><br>Quando você ver esta janela pela primeiro vez não terá nenhum
campo na lista. Para inserir um novo mapeamento você clica em
&quot;Inserir novo mapeamento de campo&quot;. Você terá que repetir isto
para todos os campos do arquivo com transações.<br>
O primeiro campo (em ordem o número 1) será a primeira (à esquerda)
coluna no arquivo de transação. Tenha certeza de que a ordem dos campos
corresponda a ordem das colunas.
<br><br>Uma vez que exista itens na lista você pode fazer algumas
operações.
<ul>
	<li>Você pode mudar a ordem dos campos clicando no botão
	&quot;Alterar ordem de mapeamento dos campos&quot;.
	<li><img border="0" src="${images}/edit.gif" width="16"
		height="16">&nbsp; clicando neste ícone permite a você alterar a
	entrada para este campo.
	<li><img border="0" src="${images}/delete.gif" width="16"
		height="16">&nbsp;clicando neste ícone você apagara o campo da
	lista.
</ul>
<hr class="help">


<A NAME="edit_field_mapping"></A>

<h3>Novo /editar mapeamento de campo</h3>
Aqui você pode definir o mapeamento por campo. Você pode colocar um
nome, o qual é apenas um rótulo de texto que será exibido na lista. Ele
não terá nenhuma função. No campo da caixa de seleção você pode
selecionar as seguintes opções.
<ul>
	<li><b>Ignorado: </b> Se o arquivo contiver colunas extras que não
	necessitem ser mapeadas aos campos do cyclos, você terá que criar
	mapeamentos para ela, mas configura-la com a opção
	&quot;ignorado&quot;.
	<li><b>Identificação: </b> Esta opção pode ser usada para mapear
	ao número interno de identificação usado no Cyclos. É pouco provável
	que um arquivo de transação irá conter o número de identificação
	interno (membro) do cyclos, mas colocamos esta opção para oferecer uma
	lista completa de opções.
	<li><b>Nome de usuário: </b> Se o arquivo de transação especificar
	o nome de usuário (cyclos) você pode definir a coluna com esta opção.
	<li><b>Campo customizado de membro: </b> Se o arquivo de transação
	não especificar um nome de usuário, é possível mapear um campo
	customizado (perfil) para poder identificar um usuário. Este deve ser
	um campo customizado único. Por exemplo um número fiscal.
	<li><b>Tipo de pagamento: </b> Se você quiser importar transações
	e gerar pagamentos a partir do arquivo de transação, você deve definir
	um tipo de pagamento. Normalmente as transações do arquivo de
	transações serão de mais de um tipo de pagamento. Por exemplo depósito,
	pagamento de empréstimo, etc. O tipo de pagamento é especificado com um
	código em uma coluna separada. Com esta opção você define qual coluna
	representa o tipo de pagamento. Os valores possíveis de diferentes
	tipos de pagamentos podem ser definidos na função de tipo de pagamento
	abaixo desta janela.
	<li><b>Data de pagamento: </b> Com esta opção você pode
	especificar a coluna que contêm a data do pagamento da transação.
	<li><b>Valor do pagamento: </b> Com esta opção você pode
	especificar a coluna que contêm o valor do pagamento da transação.
	<li><b>Indicador de valor negativo: </b> Pode acontecer do valor
	do pagamento não indicar se ele é negativo ou positivo, mas este é
	especificado em uma coluna separada. Com este campo você pode definir
	se o campo do valor do pagamento é negativo ou não. Este pode ser um
	sinal (ex &quot;-&quot;) ou uma palavra (ex. &quot;D&quot; ou
	&quot;débito&quot;)
</ul>
Clique &quot;enviar&quot; para salvar.
<br><br><b>Obs:</b> Você pode usar um campo apenas uma vez. Isto
significa que quando você adiciona um tipo de campo (ex. data de
pagamento) você não pode adiciona-lo novamente (ele não será mais
exibido como opção). Uma exceção é o campo &quot;ignorado&quot; pois
pode existir várias colunas que você deseje ignorar (não importar).<br>
Como as opções Identificação, Nome de usuário e Campo customizado de
membro são usadas para definir o membro, você pode apenas usar uma
destas três opções no mapeamento do seu arquivo.
<hr class="help">


<a name="set_field_mappings_order"></a>
<h3>Definir ordem do mapeamento de campo</h3>
Nesta janela você pode alterar a ordem do mapeamento de campo. Os
mapeamentos para campos que você definiu precisa ter exatamente a mesma
ordem dos campos do arquivo de transação que você quer importar.
<br><br>A janela funciona de uma maneira simples. Você pode apenas clicar
no nome de um campo, e arrastar ele com o mouse ao local onde você quer
ele. Quando pronto clique no botão &quot;enviar&quot; para salvar as
alterações.
<hr class="help">


<A NAME="external_transfer_type_list"></A>
<h3>Tipo de pagamento (ação de mapeamento)</h3>
Esta janela mostra uma lista com possíveis tipos de pagamentos que o
arquivo de transações pode conter. Para adicionar tipos de pagamentos
deve existir um mapeamento de campo com o campo &quot;tipo de
pagamento&quot. Com esta janela, você diz ao Cyclos quais códigos neste
campo mapeiam a tipos de pagamentos reais no cyclos. Lembre-se que você
deve mapear todos valores possíveis que possa existir neste campo;
Valores que não correspondem a um tipo se transação são mapeados como
&quot;nenhum&quot;.
<ul>
	<li><img border="0" src="${images}/edit.gif" width="16"
		height="16"> Clique no ícone para modificar o mapeamento do tipo
	de pagamento.
	<li><img border="0" src="${images}/delete.gif" width="16"
		height="16"> Clique no ícone se você quiser apagar o mapeamento
	do tipo de pagamento.
</ul>
Para adicionar um mapeamento de tipo de pagamento clique no botão
chamado &quot;Inserir novo tipo de pagamento&quot;.
<hr class="help">

<A NAME="edit_external_transfer_type"></A>
<h3>Inserir /modificar tipos de transações</h3>
Nesta página você define como o Cyclos deve interpretar um código no
campo tipo de pagamento do arquivo de transações do seu banco. Aqui você
define para quais tipos de transações o código específico deve mapear.
Você deve repetir este procedimento para todos os códigos possíveis
neste campo.
<br>
O nome e descrição são para uso interno e não tem nenhuma função. O
código é um dos valores possíveis para o campo contendo o
<a href="#edit_field_mapping"> <u>mapeamento do campo tipo de
pagamento.</u></a>
Veja a lista abaixo para um exemplo.
<br>
As seguintes ações são possíveis.
<ul>
	<li><b>Nenhum: </b> Aqui você diz que um código específico não
	deve fazer nenhuma ação. A transação irá aparecer na sua visão geral da
	conta externa, mas não irá gerar nenhum pagamento no cyclos.
	<li><b>Gerar pagamento para membro: </b> Aqui um exemplo pode
	ajudar. Se uma coluna de tipo de pagamento no arquivo de transação pode
	ter vários valores possíveis (códigos) e um deles é &quot;DEP1&quot; o
	qual indica que a transação é um depósito na conta bancária. Se você
	quer que este tipo de pagamento em particular gere automaticamente um
	pagamento de uma conta de sistema para um membro, esta opção deve ser
	selecionada.
	<li><b>Gerar pagamento para o sistema: </b> Este deve ser um caso
	raro mas esta disponível para oferecer qualquer opção possível. Isto
	significa que uma transação de entrada em uma conta externa deve gerar
	um pagamento de membro para sistema no Cyclos.<br>
	Um exemplo pode ser que você queira importar o livro caixa da moeda
	nacional no cyclos, para verificar se o membro pagou em dia suas cotas
	de impostos. Neste caso você deve precisar criar contas para a
	organização em moeda nacional (para refletir a conta bancária do
	sistema), e adicionalmente contas para membros em moeda nacional. Isto
	deve permitir a você refletir as transações da conta bancária por estas
	contas criadas, e verificar se os membros pagaram suas cotas de
	impostos (junto com isso você também pode gerar estatísticas).
	<li><b>Descartar empréstimo: </b> Alguns sistemas tem <a
		href="${pagePrefix}loans"><u>empréstimos</u></a> no Cyclos que podem
	ser pagos externamente (ex. com moeda convencional). Quando o
	empréstimo é pago externamente você não quer que o estado dele vá para
	pago, porque este estado é reservado para pagamentos com a moeda
	interna do cyclos. Neste caso o novo estado será
	&quot;Descartado&quot;. Quando o pagamento do empréstimo é feito, o
	estado do empréstimo pode ser trocado automaticamente de acordo com
	esses tipos de pagamentos.
	<li><b>Conciliado: </b> O estado de conciliado é um estado dado a
	um pagamento interno específico no cyclos que possui um pagamento
	externo &quot;contra&quot; e foi confirmado administrativamente. Este
	estado é usado para controlar o balanço de uma série de contas cyclos
	(sistema e membro) e o balanço com uma conta externa.
</ul>
<hr class="help">

<a name="using"></a>
<h2>Usando a conta externa</h2>
Você pode importar transações externas na conta externa, e
posteriormente processa-las. Você também pode buscar por transações na
conta externa.

<i>Onde encontrar.</i>
<br>
A visão geral da conta externa pode ser acessada através do &quot;menu:
Contabilidade > Contas externas&quot;.
<br><br><i>Como ativar</i><br>
Uma vez que você tenha configurado o mapeamento para um arquivo externo
(veja o início do arquivo), você pode começar a importar transações de
um arquivo do seu banco. Você deve efetuar as seguintes ações para que a
importação funcione:
<ol>
	<li>Vá ao &quot;Menu: Contabilidade > Contas externas&quot;, e
	clique no ícone <img border="0" src="${images}/import.gif" width="16"
		height="16">&nbsp;importar para ir ao módulo de importação.
	<li>Importar o arquivo. Se isto gerar algum erro, corrija-os para
	que a importação seja concluída com sucesso.
	<li>Vá para a visão geral das transações importadas. Isto pode ser
	feito através da janela de resultados da busca (abaixo da janela com o
	botão de importar), e clicando no ícone <img border="0"
		src="${images}/preview.gif" width="16" height="16">&nbsp; ver.
	<li>Remover ou restaurar transações incompletas; verifique as
	transações pendentes e modifique-as para o estado
	&quot;verificado&quot;. Tudo isso pode ser feito através do ícone <img
		border="0" src="${images}/preview.gif" width="16" height="16">&nbsp;ver
	de cada transação.
	<li>Processar as transações verificadas através do botão
	&quot;processar&quot; abaixo desta visão geral da transação.
</ol>
<hr>

<A NAME="external_accounts_overview"></A>
<h3>Visão geral das contas externas</h3>
Esta página lista todas as contas externas configuradas no sistema. A
coluna nome mostrará o nome da conta externa e o balanço da conta a soma
de todas as transações importadas.
<ul>
	<li><img border="0" src="${images}/import.gif" width="16"
		height="16"> Clique no ícone seta para entrar na função de
	importação para a conta, permitindo a você importar as transações
	externas de um arquivo. Você sempre pode obter uma visão geral de
	importações passadas.
	<li><img border="0" src="${images}/preview.gif" width="16"
		height="16"> Clique no ícone lupa para ver e processar
	transações que já tenham sido importadas.
</ul>
<hr class="help">

<A NAME="external_transfer_import_new"></A>
<h3>Nova importação de transação externa</h3>
Nesta janela você pode importar novas transações externas. Apenas
selecione o arquivo e clique &quot;Enviar&quot;. Se o arquivo não poder
ser lido, devido a erros de sintaxe, aparecerá um relatório de erro,
especificando a linha e o campo causador do erro.
<hr class="help">

<A NAME="external_transfer_import_list"></A>
<h3>Lista de importação de transação externa</h3>
Nesta janela você pode buscar arquivos importados por período. Você pode
usar o ícone de calendário
<img border="0" src="${images}/calendar.gif" width="16" height="16">
para selecionar as datas.
<hr class="help">

<A NAME="external_transfer_import_result"></A>
<h3>Resultado da importação de transação externa</h3>
Esta janela mostra uma lista com todos os arquivos de transações
importados.
<ul>
	<li><img border="0" src="${images}/preview.gif" width="16"
		height="16"> Clique no ícone lupa para ver e processar
	transações importadas.<br>
	<li><img border="0" src="${images}/delete.gif" width="16"
		height="16"> Clique no ícone apagar para remover novamente as
	transações importadas.<br>
	Nota: Você não pode apagar importações que já possuam o estado
	&quot;verificado&quot; ou &quot;processado&quot;.
</ul>
<hr class="help">

<A NAME="external_account_history"></A>
<h3>Histórico da conta externa</h3>
Nesta janela você pode buscar por transações importadas. Esta função
procura em todas os arquivos importados. Uma busca com nenhuma opção
selecionada irá mostrar todas as transações importadas para a conta
externa escolhida. As seguintes opções de busca estão disponíveis:
<ul>
	<li><b>Tipo: </b> Com esta opção você pode buscar por tipo de
	pagamento (definidos na configuração de importação).
	<li><b>Estado: </b>
	<ul>
		Uma transação importada pode ter os seguintes estados.
		<li><b>Pendente: </b> A transação foi importada mas ainda não foi
		verificada. Ela ainda não gera efeito no &quot;balanço de
		importações&quot; (na página principal da função de contabilidade) e
		não gerou nenhuma ação.<br>
		Nota: ë possível apagar uma transação importada que possui o estado
		pendente.
		<li><b>Verificada: </b> Estas são transações que foram verificas
		e colocadas com no <a href="#external_transfers_history_result">estado</a>
		de &quot;verificada&quot; .
		<li><b>Processada: </b> Esta são transações que já foram
		processadas após terem sido colocadas no estado verificada.
	</ul>
	<li><b>Nome de usuário / Membro: </b> Busque por transações de um
	usuário específicos.
	<li><b>De valor / até valor: </b> Busque pela faixa de valor.
	<li><b>Da data / até a data:</b> Busque por período.
</ul>
Sob a janela exitem ações (três botões); Estes são, da esquerda para a
direita:
<ul>
	<li><b>Para lista de importação:</b> Este irá leva-lo a visão
	geral de arquivos importados, onde você também pode importar um novo
	arquivo de transações.
	<li><b>Processar pagamentos: </b> Este irá abrir uma janela de
	processamento onde você terá a opção de processar uma ou mais
	transações. <br>
	O processo pode ser
	<ul>
		<li>para conciliar um pagamento de saída (da conta externa)
		<li>para gerar um pagamento interno no cyclos, relacionado a um
		pagamento de entrada na conta bancária.
		<li>para descartar um pagamento de empréstimo (Cyclos)
		relacionado a um pagamento de entrada na conta externa.
	</ul>
	O botão processar se aplica a todas as transações na lista de
	resultados (Não importa de estão selecionadas ou não). Observe que uma
	transação só pode ser processada se o seu estado for
	&quot;verificada&quot;.
	<li><b>Novo pagamento: </b> É possível inserir uma transação
	manualmente no caso dela não ter sido importada corretamente.
</ul>
Mais informações sobre esta função pode ser encontrada na janela da
própria função.
<hr class="help">

<a name="status"></a>
<h3>Estado da transação</h3>
Cada transação importada tem um estado. O estado pode ter os seguintes
valores:
<ul>
	<li><b>Pendente</b> <img border="0" src="${images}/pending.gif"
		width="16" height="16">: A transação foi importada não tem
	nenhum efeito. Ela esta pendente para ações futuras.<br>
	Este estado também será mostrado na visão geral da conta de sistema, se
	o tipo de transação é marcado como &quot;conciliável&quot;. Desta
	maneira você pode localizar o estado conciliável diretamente na página
	de visão geral da conta.<br>
	Apenas transações com o estado pendente podem ser apagadas.
	<li><b>Verificada</b> <img border="0" src="${images}/checked.gif"
		width="16" height="16">: A transação foi verificada e
	&quot;marcada&quot;. Isto significa que ela entrará no balanço da conta
	externa.<br>
	É possível colocar uma transação com o estado &quot;verificada&quot;
	de volta ao estado &quot;pendente&quot;.
	<li><b>Incompleta</b> <img border="0"
		src="${images}/incomplete.gif" width="16" height="16">: A
	transação foi importada mas um ou mais campos não foram mapeados
	corretamente. Por exemplo se um membro na transação importada não
	existir no cyclos.<br>
	<li><b>Conciliada</b> <img border="0"
		src="${images}/conciliated.gif" width="16" height="16">: A
	transação foi processada. Isto significa que ela é [arte do balanço da
	conta externa e isto causa uma ação no cyclos (ex. um pagamento interno
	no cyclos ou o descarte de um empréstimo).<br>
	Este estado também sera exibido na visão geral da conta de sistema se o
	tipo de transação for marcado como &quot;conciliável&quot;. Desta
	maneira você pode localizar o estado conciliável diretamente na página
	de visão geral da conta.<br>
	Transações processadas não podem receber outro estado e também não
	podem ser apagadas.
</ul>
<hr class="help">


<A NAME="external_transfers_history_result"></A>
<h3>Transferências externas</h3>
Esta janela mostra o resultado da janela de busca acima. Por padrão esta
mostra todas as transações importadas. A coluna tipo mostrará o ícone de
estado na frente.
<a href="#status"><u>Clique aqui</u></a>
para uma visão geral de valores possíveis para o estado. As colunas
valores e data são auto-explicativas.
<br><br>As seguintes ações são possíveis em cada transação.
<ul>
	<li><img border="0" src="${images}/preview.gif" width="16"
		height="16"> Você pode entrar em pagamentos conciliados e
	processados selecionando o ícone prever. Estes pagamentos não podem ser
	trocados. Contudo é possível definir uma transação verificada novamente
	como pendente.
	<li><img border="0" src="${images}/edit.gif" width="16"
		height="16"> Você pode modificar a data da transação de uma
	transação no estado pendente selecionando o ícone editar.<br>
	<li><img border="0" src="${images}/delete.gif" width="16"
		height="16"> Clique no ícone apagar para apagar transações.<br>
</ul>
<hr class="help">


<A NAME="external_transfers_history_summary"></A>
<h3>Sumário das transações externas</h3>
Esta janela mostra uma visão geral e relatórios do total de transações
listadas.
<br><br>Nota: Apenas as transações que são resultados de uma busca da
janela no topo serão contadas. Uma busca sem opções selecionadas irá
mostrar todas as transações importadas para a conta externa escolhida.
<hr class="help">

<A NAME="new_external_transfer"></A>
<h3>Nova transação externa</h3>
É possível adicionar uma transação manualmente. Normalmente isto não é
necessário como a importação de transação pode ser configurada para
importar todas as transações corretamente. Mas em caso (raro) de ser
necessário isto pode ser feito nesta janela.
<br>
Os campos de entrada são auto-explicativos.
<hr class="help">

<A NAME="edit_external_transfer"></A>
<h3>Ver e modificar transações externas</h3>
Nesta página você pode ver os detalhes das transações importadas. Se a
transação esta no
<a href="#status"><u>estado</u></a>
pendente você pode modificar suas propriedades.
<br>
Os campos de entrada são auto-explicativos.
<hr class="help">

<A NAME="external_transfer_process"></A>
<h3>Processar transação externa</h3>
Nesta página você pode processar pagamentos. Observe que o pagamento
deve estar no estado
<a href="#status"><u>&quot;verificado&quot;</u></a>
ou ele não sera mostrado nesta lista de transações, que estão prontas
para o processamento.
<br><br>Esta janela da uma visão geral dos pagamentos processados. Em
cada item, a linha acima mostra a linha original em que ela foi lida do
arquivo de transações, e a linha abaixo mostra as transações como elas
foram processadas após você ter clicado em enviar. Selecione as
transações que você deseja processar clicando na caixa de seleção na
primeira coluna. Isto também permitirá a você modificar a data ou o
valor caso estes pareçam estar incorretos. <br>
Clicando &quot;enviar&quot; processará as transações selecionadas.
<br><br>Existe três tipos de processamento.
<ul>
	<li><b>Conciliado: </b> Esta opção é comum em sistemas onde as
	unidades internas (Cyclos) são respaldadas externamente (ex. dinheiro
	convencional na conta bancária). Conciliando uma série de transações
	(transações externas e transações no cyclos) significa que ambas
	transações estarão relacionadas, porque elas estão relacionadas
	administrativamente. Esta será mostrada com o ícone <img border="0"
		src="${images}/conciliated.gif" width="16" height="16"> na
	frente do pagamento na visão geral da conta da funcionalidade de contas
	externas e na visão geral da conta de sistema. Nas janelas de busca
	destas funções existirá uma opção de buscar pelo estado de conciliação.
	Para estar apto à conciliar transações com os tipos de transações no
	cyclos que podem ser conciliados, a opção &quot;é conciliável&quot;
	deve estar marcada na configuração dos tipos de transações.<br>
	<li><b>Pagamento gerado: </b><br>
	Um pagamento (externo) de entrada (valores positivo apenas) pode ser
	configurado para gerar um pagamento cyclos (sistema para membro ou da
	forma contraria). Para processar um pagamento ele deverá estar
	selecionado. Uma vez que a transação é selecionada é possível modificar
	o valor e a data mas isto sera necessário apenas em casos raros.<br>
	Um pagamento gerado terá automaticamente o estado conciliado.
	<li><b>Descartar pagamento de empréstimo: </b><br>
	Um empréstimo no cyclos entrará em um estado &quot;pago&quot;
	administrativamente quando for pago. Em sistemas onde os empréstimos
	Cyclos podem ser pagos externamente será possível dar a estes
	empréstimos um estado indicando que eles foram pagos. Este estado é
	chamado &quot;Descartado&quot;. É possível ter um pagamento externo de
	entrada gerando um pagamento de descarte de empréstimo.<br>
	Existe um tipo específico para a configuração na conta externa. Quando
	esta transação externa é processada, Cyclos irá buscar por empréstimos
	com pagamentos em aberto com o mesmo valor e data e irá apresentar a
	correspondência mais próxima. Caso exista mais pagamentos de empréstimo
	que correspondam, eles serão mostrados e o administrador pode escolher
	o correto. Após a geração de um pagamento de descarte, o estado
	conciliado sera mostrado na visão geral da conta externa.
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
