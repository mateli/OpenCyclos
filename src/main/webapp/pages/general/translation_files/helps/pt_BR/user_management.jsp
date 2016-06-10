<div style="page-break-after: always;">
<span class="admin">
<br><br>Estas páginas são as funções de
gerenciamento de usuários. Elas podem ser usadas para procurar por
membros, registrar novos membros, obter uma visão geral dos membros
conectados atualmente, ou ver outras informações sobre o estado de
membros.

<i>Onde encontrar.</i><br>
As seguintes listam as funcionalidades disponíveis e onde podem ser
encontradas:
<ul>
	<li><b>Procurar e criar membros:</b> Pode ser feito através do
	&quot;Menu: Usuários & grupos > Gerenciar membros&quot;. Isto inclui
	procurar corretores.
	<li><b>Procurar e criar administradores:</b> Pode ser feito
	através do &quot;Menu: Usuários & grupos > Gerenciar
	administradores&quot;.
	<li><b><a href="#connected"><u>Usuários conectados</u></a>:</b>
	Pode ser encontrado através do &quot;Menu: Usuários & grupos > Usuários
	conectados&quot;.
	<li><b><a href="#bulk_actions"><u>Ações em massa</u></a>:</b> Pode
	ser encontrado através do &quot;Menu: Usuários & grupos > Ações em
	massa&quot;.
</ul>
</span>


<span class="broker">
<ul>
	<li><b>Criar/registrar membros:</b> Fornecido caso você tenha
	permissões para isso, pode ser encontrado através do &quot;Menu:
	Corretagem > Registrar membro&quot;.
</ul>
</span>

<span class="member">
<ul>
	<li><b>Procurar membros:</b> Você pode procurar membros através do
	&quot;Menu: Procurar > Membros&quot;.
</ul>
</span>
<hr>

<span class="admin"> <A NAME="create_user"></A>
<h3>Criar usuário</h3>
Aqui você informa os dados sobre o novo membro. Esta janela é bastante
simples, os campos mostrados neste formulário dependem da sua
configuração.<br>
O membro será parte do <a href="${pagePrefix}groups"><u> grupo</u></a>
que é listado no primeiro campo.
<br><br>Caso exista um grupo de corretores que possua permissões de
corretor para o grupo no qual você esta criando o membro, você pode
(opcionalmente) atribuir o membro a um corretor deste grupo. Para isso,
você deve marcar a opção no final do formulário, chamada &quot;atribuir
corretor&quot;. <br>
Você pode especificar uma senha para o usuário e determinar se o usuário
pode usar a senha diretamente ou deve altera-la no próximo acesso.
<br><br>Após preencher os dados, você tem a opção de salvar o membro e
criar um novo membro (botão &quot;Salvar e inserir novo membro&quot;) ou
abrir diretamente o <a href="${pagePrefix}profiles"><u>perfil</u></a> do
novo membro (botão &quot;Salvar e abrir perfil&quot;).
<hr class="help">
</span>

<span class="broker"> <a name="create_user_for_broker"></a>
<h3>Corretagem - criar novo membro</h3>
Aqui você informa os dados sobre o novo membro. Esta janela é bastante
simples, os campos mostrados neste formulário dependem da sua
configuração.<br>
O membro será automaticamente atribuído como um dos seus membros
corretados, após você completar o registro.<br>
Você pode especificar uma senha para o usuário e determinar se o usuário
pode usar a senha diretamente ou deve altera-la no próximo acesso.
<br><br>Após preencher os dados, você tem a opção de salvar o membro e
criar um novo membro (botão &quot;Salvar e inserir novo membro&quot;) ou
abrir diretamente o <a href="${pagePrefix}profiles"><u>perfil</u></a> do
novo membro (botão &quot;Salvar e abrir perfil&quot;).
<br><br>Para registrar um novo membro, você pode receber uma <a
	href="${pagePrefix}brokering#commission"><u>comissão</u></a>
automática, dependendo das regras em seu sistema. A comissão depende do
volume transacionado pelo membro que você registrou. <br>
Através do &quot;Menu: Corretagem > Membros&quot; você pode gerenciar os
membros que você registrou, e conferir como eles estão indo.
<hr class="help">
</span>


<span class="member"> <A NAME="search_member_by_member"></A>
<h3>Procurar membros</h3>
Nesta página você pode procurar por membros. A procura por membros, será
feita em todos os campos do perfil. Você pode usar mais de uma
palavra-chave na busca.<br>
Diversos &quot;operadores&quot; podem ser usados com a busca de membro
(e anúncios). Os mais comuns são:
<ul>
	<li><b>*</b> O asterisco &quot;curinga&quot; permite que você
	busque por partes de palavras. Por exemplo uma busca por ba* retornará
	todos os usuários que tiverem a letra A combinada com a letra B em
	algum de seus campos, como Bárbara, Baltazar (pode ser um campo de
	endereço).
	<li><b>- not</b> Uma busca com uma palavra-chave precedida pelo
	sinal menos( - ) ou precedida por &quot;not&quot; com um espaço
	retornará todos os resultados que não contenham essas palavras.
	<li><b>or</b> A busca deve retornar membros tanto com a
	palavra-chave que precede o &quot;or&quot; quanto a que vem depois.
	<li><b>and</b> A busca retornará resultados que contenham as
	palavras antes e depois do &quot;and&quot;.
</ul>

<hr class="help">
</span>

<span class="member"> <A NAME="search_member_result"></A>
<h3>Resultado da busca (por membros)</h3>
Esta janela mostra o resultado da procura por membros. Clicando no
&quot;Nome de usuário&quot; ou &quot;Nome do membro&quot; abrirá o <a
	href="${pagePrefix}profiles"><u> perfil</u></a> deste membro. Clicando
na miniatura da imagem, ela será exibida em uma janela pop-up em seu
tamanho original.
<hr class="help">
</span>

<span class="admin"> <A NAME="search_member_by_admin"></A>
<h3>Procurar membros</h3>
Na página de procura por membros (Menu: Usuários & grupos > Gerenciar
membros) você pode procurar por membros e registrar novos membros.
<br><br>Se você deseja procurar por um membro, você pode preencher vários
critérios de procura (mas nenhum deles é obrigatório). Se você apenas
clicar no botão &quot;Procurar&quot; sem preencher nenhum critério, você
obterá uma lista com todos os membros.<br>
<ul>
	<li><b>Filtros de grupo:</b> Aqui você pode especificar um <a
		href="${pagePrefix}groups#group_filters"><u>filtro de grupo</u></a>.
	<li><b><a href="${pagePrefix}groups"><u>Grupo de
	permissão</u></a></b>
	<li><b>Nome / nome de usuário do corretor:</b> Informe aqui o nome
	real ou o nome de usuário do corretor, e a página de resultado mostrará
	todos os membros relacionados a ele.
	<li><b>Ativação De / até:</b> Com estes campos de data, você pode
	procurar por usuários que se tornarão membros da organização em um
	determinado intervalo de tempo. Você pode usar os seletores de data
	através do ícone de calendário (<img border="0"
		src="${images}/calendar.gif" width="16" height="16">&nbsp;).
</ul>
<br><br>Para registrar um novo membro, você terá que usar a caixa de
seleção final do formulário a esquerda. Aqui você seleciona o grupo no
qual o novo membro será criado, e você será levado ao <a
	href="#create_user"><u>formulário de registro</u></a>.
<hr class="help">
</span>

<span class="admin"> <A NAME="admin_search_member_result"></A>
<h3>Resultado da busca (por membros)</h3>
Esta janela mostra o resultado da procura por membros. Clicando no
&quot;Nome de usuário&quot; ou &quot;Nome do membro&quot; abrirá o <a
	href="${pagePrefix}profiles"><u> perfil</u></a> deste membro. Clicando
no botão voltar da página do perfil, você será remetido de volta ao
resultado da busca.
<br><br>Você possui a opção de imprimir o resultado, clicando no ícone de
impressão (<img border="0" src="${images}/print.gif" width="16"
	height="16">&nbsp;) no canto superior direto da janela, próximo
ao ícone de ajuda. Isto o levará a uma janela "amigável" para impressão,
onde você poderá clicar no botão imprimir para iniciar a impressão.
<br><br>Outra possibilidade é baixar o resultado em um arquivo, clicando
no ícone de salvar (<img border="0" src="${images}/save.gif" width="16"
	height="16">&nbsp;). Os resultados serão baixados em arquivo no <a
	href="${pagePrefix}loans#csv"><u>formato CSV</u></a>, o qual conterá
todos os campos que existem nos <a href="${pagePrefix}profiles"><u>perfis</u></a>
dos membros (assim incluindo muitos campos não visíveis nesta janela de
resultados). <br>
Nas <a href="${pagePrefix}settings#local"><u>configurações
locais</u></a> você pode especificar se você quer exibir os nomes dos campos no
cabeçalho (primeira linha) das colunas.
<br><br>Note: Na função de relatórios você poderá obter <a
	href="${pagePrefix}reports#member_lists"><u>listas de membros</u></a>
mais específicas. Por exemplo, listas de membros com dados adicionais
com o balanço das contas e número de anúncios.
<hr class="help">
</span>

<span class="broker admin"> <a name="search_pending_member"></a>
<h3>Procurar membros pendentes</h3>
Membros pendentes são membros que foram registrados mas ainda não
confirmaram seus registros, respondendo à mensagem de e-mail. Somente
após a confirmação o membro pode acessar o sistema.<br>
Após um tempo específico, os pendentes serão removidos automaticamente
do sistema (e da lista). Todas as 3 opções de cadastramento podem
demandar confirmação por e-mail. <br>
Nesta página você pode procurar por membros pendentes. Você pode
procurar por nome, grupo e data de registro.<br>
A busca por grupo não significa que o membro pertence ao grupo, mas
indica o grupo ao qual o membro fará parte após a validação por e-mail.<br>
</span>
<span class="admin"> Você também pode procurar por corretor.
Significa que a busca irá exibir apenas os membros registrados pelo
corretor selecionado. Observe que isto só irá funcionar caso os membros
que sejam registrados pelo corretor necessitem de confirmação. Isto é
uma <a href="${pagePrefix}groups#group_registration_settings"><u>
Configuração de grupo</u></a> opcional. O período máximo para o registro é
definido nas configurações locais. <br>
<br>
<hr class="help">
</span>

<span class="broker admin"> <a
	name="search_pending_member_result"></a>
<h3>Lista de membros pendentes</h3>
Esta janela mostra o resultado de uma busca por membros. Esteja ciente
de que estes membros não são membro &quot;ativos&quot; (eles não podem
acessar e não são visíveis no sistema). Apenas em casos raros você pode
querer apagar um membro da lista. Apagar um membro pendente deve
significar que o membro não esta apto a confirmar o seu registro.<br>
Se você editar o membro voê pode ver e modificar os dados do perfil e
caso necessário enviar novamente o e-mail de validação.
<hr class="help">
</span>

<span class="broker admin"> <a name="pending_member"></a>
<h3>Detalhes do membro pendente</h3>
Nesta página você pode ver e modificar os dados de perfil e caso
necessário enviar novamente o e-mail de validação.
<hr class="help">
</span>

<span class="admin"> <a name="search_admin"></a>
<h3>Procurar administradores</h3>
Na página de procura por administradores (Menu: Usuários & grupos >
Gerenciar administradores) você pode procurar por administradores e
registrar novos administradores.
<br><br>O formulário é bastante simples: Você pode especificar apenas uma
palavra-chave, e/ou limitar a busca em um grupo específico de
administradores. Se você apenas clicar no botão &quot;Procurar&quot;,
sem preencher nenhum campo, você obterá uma lista de resultado com todos
os administradores existentes.<br>
<br><br>Para registrar um novo administrador você terá que usar a caixa
de seleção no final do formulário. Aqui você escolhe o <a
	href="${pagePrefix}groups#admin_groups"><u>grupo</u></a> no qual você
deseja criar o novo administrador, e você será levado ao <a
	href="#create_user"><u>formulário de registro</u></a> para o novo
administrador.
</span>

<span class="admin"> <a name="search_admin_result"></a>
<h3>Resultado da busca (por administradores)</h3>
Esta caixa dá o resultado da sua procura por administradores. Você pode
clicar no nome de usuário do administrador, ou no nome real, para obter
os detalhes sobre este administrador.
<hr class="help">
</span>

<span class="admin"> <a name="create_admin"></a>
<h3>Registrar novo administrador</h3>
Aqui você pode registrar um novo administrador. Nós recomendamos <b>fortemente</b>
que todas as pessoas trabalhando como administrador possuam seus
próprios usuários para acesso, assim pessoas diferentes não
compartilharão do mesmo usuário para acesso. Isto torna mais fácil
manter as permissões, localizar possíveis erros, e cancelar o acesso
quando as pessoas deixarem a organização.
<br><br>O formulário é bastante simples. Qualquer campo com um asterisco
(*) vermelho é obrigatório.
<br><br>
<br><br>Após preencher os dados você terá a opção de salvar o
administrador e criar um novo administrador (botão &quot;Salvar e
inserir um novo administrador&quot;) ou de abrir diretamente o <a
	href="${pagePrefix}profiles"><u>perfil</u></a> do novo administrador
(botão &quot;Salvar e abrir perfil&quot;).
<hr class="help">
</span>

<span class="admin"> <a name="connected"></a>
<h2>Usuários conectados</h2>
Na página de usuários conectados (Menu: Usuários & grupos > Usuários
conectados) você pode obter uma visão geral de quais usuários (membros,
administradores, corretores) estão atualmente conectados e usando o
sistema.

<hr>
</span>

<span class="admin"> <A NAME="connected_users"></A>
<h3>Usuários conectados</h3>
Esta janela permite a você especificar quais <a
	href="${pagePrefix}groups#group_categories"><u>tipos</u></a> de
usuários conectados você deseja ver na janela abaixo. A caixa de seleção
&quot;exibir&quot; permite que você escolha entre
&quot;administradores&quot;, &quot;corretores&quot;, &quot;membros&quot;
e &quot; <a href="${pagePrefix}operators"><u>operadores</u></a>&quot;.<br>
Clique &quot;enviar&quot; para exibir os resultados.
<hr class="help">
</span>

<A NAME="connected_users_result"></A>
<span class="admin">
<h3>Resultado da busca por usuários conectados</h3>
Esta função mostrará uma lista com os usuários conectados, de acordo com
a sua seleção na janela acima.<br>
Na lista você pode clicar no membro para abrir o perfil. Administradores
com permissões podem desconectar um membro clicando no ícone de apagar (<img
	border="0" src="${images}/delete.gif" width="16" height="16">&nbsp;)
na última coluna. </span>
<span class="member">
<h3>Resultado da busca por operadores conectados</h3>
Esta função mostrará uma lista com os <a href="${pagePrefix}operators"><u>operadores</u></a>
conectados.<br>
Na lista você pode clicar no operador para abrir o perfil. Se você
possuir permissões poderá desconectar um operador clicando no ícone de
apagar (<img border="0" src="${images}/delete.gif" width="16"
	height="16">&nbsp;) na última coluna.</span>

<span class="admin"> <a name="bulk_actions"></a>
<h2>Ações em massa</h2>
A função de ações em massa (Menu: Usuários & grupos > Ações em massa)
permitem a um administrador efetuar ações em conjuntos de usuários.

<hr>
</span>

<span class="admin"> <A NAME="bulk_actions_filter"></A>
<h3>Filtro para ações em massa</h3>
Esta janela permite ao administrador especificar o grupo de usuários com
os quais a <a href="#bulk_actions"><u>ação em massa</u></a> é efetuada.
Os campos são combinados em uma lógica de "AND-search", assim tenha
cuidado para não especificar uma quantidade grande de critérios, pois
poderá resultar em um resultado vazio.
<ul>
	<li><b>Grupo:</b> Aqui você especifica o <a
		href="${pagePrefix}groups#group_filters"><u>filtro de grupo</u></a>.
	<li><b>Grupo de permissões:</b> Aqui você especifica o <a
		href="${pagePrefix}groups"><u>grupo</u></a>. Tenha cuidado para isto
	não conflitar com o filtro de grupo especificado acima. Normalmente
	deve-se especificar um filtro de grupo ou um grupo de permissão, mas
	não ambos.
	<li><b>Nome / nome de usuário do corretor:</b> Se você quiser que
	a ação seja feita sobre todos os membros de um corretor, especifique o
	corretor aqui, pelo nome de usuário ou pelo nome real.
	<li><b>...:</b> O restante do formulário lista alguns <a
		href="${pagePrefix}custom_fields"><u>campos customizados</u></a>
	definidos para o perfil de seus membros.
</ul>
Uma vez especificado o seu critério para as ações em massa, você pode
ver quais membros estão incluí dos clicando no botão
&quot;pre-visualizar&quot; no final do formulário à direita. Isto
resultará na apresentação de uma lista com os membros incluídos.
<hr class="help">
</span>

<span class="admin"> <A NAME="bulk_action"></A>
<h3>Ação</h3>
Aqui é especificada a <a href="#bulk_actions"><u>ação em massa</u></a> a
ser executada. Você tem as seguintes possibilidades:
<ul>
	<li><b>Alterar grupo:</b> Isto irá alterar o grupo dos membros
	agrupados.<br>
	Uma vez que você escolher isto, será solicitado a você o novo <a
		href="${pagePrefix}groups"><u>grupo de permissão</u></a>, e um
	comentário. Após clicar em &quot;enviar&quot;, todos os membros
	envolvidos serão movidos para o novo grupo.
	<li><b>Alterar corretor:</b> Isto alterará o <a
		href="${pagePrefix}brokering"><u>corretor</u></a> do agrupamento
	selecionado. <br>
	Uma vez que você escolher isso, será solicitado a você o nome de
	usuário ou o nome real do novo corretor.<br>
	Marcando a caixa &quot;suspender comissão&quot; resultará em descartar
	todos <a href="${pagePrefix}brokering#commission"><u>pagamentos
	e comissão</u></a> em execução ou abertos. Você pode usar esta caixa caso você
	pense que o novo corretor não possua os direitos para receber os
	pagamentos de comissões envolvidos nas ações do corretor anterior.<br>
	Você deve também informar um comentário. Após clicar no botão
	&quot;enviar&quot;, todos os membros envolvidos terão o novo corretor
	relacionado.
</ul>
<hr class="help">

<A NAME="import_members"></A>
<h3>Importar membros</h3>
Com esta função é possível importar membros (perfis) e opcionalmente
definir um crédito inicial por membro, e gerar um pagamento inicial de
ou para uma conta de sistema. Números e datas devem ser formatados de
acordo com as definições das configurações locais.<br>
Os campos são identificados pelo nome da coluna. Os nomes das colunas
não são sensíveis a caixa (maiúsculas e minúsculas) e precisam estar na
primeira linha. As colunas podem ser em qualquer ordem (não existe
coluna 1, 2, os nomes estando corretos irá funcionar). Se um campo for
opcional você pode ou omitir toda a coluna ou deixar os valores da
coluna vazios.<br>
As seguintes colunas/valores são suportadas:
<ul>
	<li>name (obrigatório)<br>
	O nome completo do membro.
	<li>username (obrigatório)<br>
	Este é o nome de usuário, e é único, o que significa que dará um erro
	caso um usuário com esse nome já exista no sistema. No caso de o nome
	de usuário ser um número (conta) gerado automaticamente pelo sistema, a
	coluna não é necessária e o Cyclos irá gerar os nomes de usuários.
	<li>password (opcional)<br>
	A senha do membro. Eles terão que altera-la no primeiro acesso.
	<li>email (obrigatório ou opcional de acordo com a configuração do
	cyclos). Deve ser um endereço de e-mail correto.
	<li>balance (opcional)<br>
	O balanço inicial da conta. Usado apenas se um tipo de conta esta
	selecionado. No caso de você selecionar um tipo de conta, você pode
	especificar os tipos de pagamentos (membro para sistema para balanços
	negativos e sistema para membro para balanços positivos).<br>
	Se você usar esta opção, tenha certeza de que exista créditos
	suficientes na conta que será debitada.
	<li>creditlimit (opcional)<br>
	O limite de crédito (negativo) da conta. Caso vazio, será usado o
	limite padrão do grupo.
	<li>uppercreditlimit (opcional)<br>
	O limite de crédito (positivo) da conta. Caso vazio, será usado o
	limite padrão do grupo.
	<li>nome interno do campo customizado (opcional)<br>
	O nome interno do campo customizado relacionado com o grupo
	selecionado. A validação necessária é feita. No caso do campo ser uma
	lista (enumerado) a importação do membro só sucederá caso exista o
	valor do campo. Por exemplo, se você possuir o campo customizado
	&quot;região&quot; com três valores possíveis &quot;centro&quot;
	&quot;sul&quot; e &quot;norte&quot; os membros da importação com outras
	regiões não serão importados. Membros com o campo região vazio serão
	importados (caso o campo seja definido como &quot;obrigatório&quot;
	eles não serão importado).
	<li>tipo de registro de membro.nome interno do campo customizado
	(opcional)<br>
	Valores para registros de membros. Um exmeplo para o banco de dados
	padrão é &quot;Remark.comments&quot;. Onde comments é um campo do
	registro Remark (observações). Este campo deve ser o nome interno
	(especificado na configuração do campo).<br>
	Tenha cuidado, se você deseja importar um campo de um tipo de registro,
	todos os campos que estão definidos para este tipo de registro precisam
	ser especificados no arquivo CSV (como uma coluna).
</ul>
<hr class="help">


<A NAME="imported_members_summary"></A>
<h3>Resumo de membros importados</h3>
Esta página da uma visão geral dos membros importados. Até esta página
nada esta processado ainda. Você pode clicar no link (número) para ver o
estado dos membros importados com sucesso e falhados (ou ver ambos
clicando no número após &quot;total de membros&quot;).<br>
Se você clicar em &quot;Importar&quot; os membros com sucesso serão
importados. Então é uma boa prática ver o estado dos membros importados.
<hr class="help">


<A NAME="imported_member_details"></A>
<h3>Procura por membros importados</h3>
Nesta janela você pode procurar por membros específicos na lista de
importados. Você pode procurar pelo número da linha ou pelo nome do
membro. A opção de procura pelo nome, busca nos campos de nome de
usuário e nome do membro.<br>
<hr class="help">


<A NAME="imported_member_details_result"></A>
<h3>Resultado da procura por membros importados</h3>
Esta janela mostrará o resultado das importações. No caso de erros de
importação ela informará o tipo de erro (ex. campo faltando, nome já em
uso) e no caso de importação bem sucedida ele mostra o limite de crédito
(inferior) e o balanço da conta.<br>
Para processar os membros você pode clicar em voltar e após no botão
&quot;Importar&quot;. </span>

<span class="member">
<hr>
<br><br><A NAME="contacts"></A>
<h3>Contatos</h3>
<br><br>Esta tela lhe permite gerenciar seus
contatos.

Na lista de contatos (Menu: Pessoal > Contatos) você pode fazer diversas
ações, selecionando com o seu mouse uma das seguintes opções da lista:
<ul>
	<li><b>Nome de usuário - nome do membro:</b> Abre a página de
	perfil do membro.
	<li><b>e-mail:</b> Envia um e-mail ao membro.
	<li><b><img border="0" src="${images}/edit.gif" width="16"
		height="16">&nbsp;</b> Ver / Adicionar / Editar informações
	adicionais sobre o membro.
	<li><b><img border="0" src="${images}/edit_gray.gif"
		width="16" height="16">&nbsp;</b> Se o ícone não possuir cor,
	significa que o campo de informações não possui nenhum dado.
	<li><b><img border="0" src="${images}/delete.gif" width="16"
		height="16">&nbsp;</b> Apaga o membro da sua lista de contatos.
</ul>
No inicio, você não possui nenhum membro nesta lista. Você pode
adicionar contatos de duas maneiras:
<ul>
	<li>Use a janela &quot;<a href="#add_contact"><u>adicionar
	novo contato</u></a> &quot; abaixo.
	<li>Através de uma <a href="${pagePrefix}#search_member_by_member"><u>procura
	por membros</u></a> (&quot;Menu: Procurar&quot;). Na lista de resultados da
	busca, você pode entrar no <a href="${pagePrefix}profiles"><u>perfil</u></a>
	de um membro clicando no nome do membro. No perfil você deve clicar em
	&quot;adicionar aos contatos&quot; para adicionar o membro a sua lista
	de contatos.
</ul>
<hr class="help">

<br><br><A NAME="add_contact"></A>
<h3>Adicionar contato</h3>
Aqui você pode adicionar um novo contato. Você pode fazer isso
informando o nome de usuário ou de membro nos campo e clicando em
&quot;Enviar&quot;.
<hr class="help">

<br><br><A NAME="contact_note"></A>
<h3>Informações do contato</h3>
Nesta página você pode inserir informações adicionais sobre o usuário.
Esta informação é visível apenas por você, e será apagada caso você
remova o contato da sua lista de contatos.
<hr class="help">

<a name="contact_us"></a>
<h3>Contato</h3>
Esta página contêm o endereço de contato caso você tenha dúvidas sobre o
software.
<hr class="help">
</span>

<br><br>

</div> <%--  page-break end --%>
