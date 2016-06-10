<div style="page-break-after: always;">
<br><br>Frequentemente, organizações que usam o
Cyclos, precisam que informações específicas suas sejam gravadas no
banco de dados do cyclos. Por esse motivo, é possível gerenciar os
campos que serão mantidos no banco de dados, e que estarão visíveis na
aplicação.<br>
Um administrador pode adicionar novos campos, modificar campos
existentes e remover campos. Isto é possível para perfis de membros,
administradores e <a href="${pagePrefix}operators"><u>operadores</u></a>,
para anúncios, <a href="${pagePrefix}loans"><u>empréstimos</u></a> e <a
	href="${pagePrefix}loan_groups"><u>grupos de empréstimos</u></a> e para
<a href="${pagePrefix}member_records"><u>registros de membros</u></a>.<br>
Por exemplo, se uma organização precisa de um campo extra no <a
	href="${pagePrefix}profiles"><u>perfil</u></a> indicando o número de
sapato que o membro calça, o administrador pode criar um novo campo e
definir várias propriedades para esse campo, como: nome, tipo, tamanho,
visibilidade, permissões, localização, validação, entre outros
comportamentos e definições.<br>
Os campos podem ser relacionados com <a href="${pagePrefix}groups"><u>grupos</u></a>.
Isto permite que você tenha diferentes formulários de registro e perfis
para consumidores e empresas. No exemplo acima, sera possível para a
organização criar politicas diferentes para os membros, baseadas no
tamanho do sapato.<br>
Cyclos vem por padrão com uma série de campos customizados, os quais
obviamente são gerenciáveis. É claro que nem todos os campos do banco de
dados são customizáveis, alguns campos são tão importantes, que torna
impossível que sejam removidos ou modificados.<br>

<i>Onde encontrar.</i>
<br>
Os campos customizados podem ser acessados através do item de menu
&quot;Campos customizados&quot;.
<br><br><i>Como ativar.</i><br>
Para gerenciar campos customizados, você precisa ter as <a
	href="${pagePrefix}groups#manage_group_permissions_admin_system"><u>permissões</u></a>;
Estas podem ser atribuídas apenas à administradores, e podem ser
encontradas no bloco &quot;Campos customizados&quot;.
<hr>

<a name="list_custom_fields"></a>
<h3>Listar campos customizados</h3>
Esta ajuda se aplica para campos customizados de membros,
administradores,
<a href="${pagePrefix}operators"><u>operadores</u></a>
,
<a href="${pagePrefix}advertisements"><u>anúncios</u></a>
,
<a href="${pagePrefix}loans"><u>empréstimos</u></a>
,
<a href="${pagePrefix}loan_groups"><u>grupos de empréstimos</u></a>
, e para
<a href="${pagePrefix}member_records"><u>registros de membros</u></a>
.
<br><br>A lista mostra todos os campos customizados que estão definidos
para o tipo escolhido.<br>
Campos de membro, administradores e operadores serão exibidos em seus
perfis. O banco de dados padrão que vem com o cyclos possui alguns
campos customizados para membros.<br>
Campos de empréstimos e grupos de empréstimos, serão exibidos nas
páginas de empréstimos e grupo de empréstimos, e campos de anúncios
serão exibidos nas páginas dos anúncios.
<br><br>O nome e configuração dos campos pode ser modificado, clicando no
ícone de edição. Na arquivo de ajuda daquela página você terá
informações mais detalhadas sobre a configuração dos campos
customizados.<br>
Você pode apagar um campo, clicando no ícone de apagar (ícone vermelho).
Observe que apagar um campo customizado, só é possível caso ele ainda
não tenha sido usado, uma vez que qualquer campo no banco de dados
possua informações, não sera mais possível remove-lo. Neste caso você
pode apenas ocultar o campo, desmarcando todos os grupos na caixa de
visibilidade.
<br><br>Clicando no botão &quot;Alterar ordem dos campos&quot; você terá
a opção de modificar a ordem em que os campos serão exibidos na página.<br>
Clicando no botão &quot;Novo campo customizado&quot; você poderá inserir
um novo campo customizado.<br>
<hr class="help">

<a name="order_custom_fields"></a>
<h3>Definir ordem dos campos customizados</h3>
Esta ajuda se aplica para campos customizados de membros,
administradores,
<a href="${pagePrefix}operators"><u>operadores</u></a>
,
<a href="${pagePrefix}advertisements"><u>anúncios</u></a>
,
<a href="${pagePrefix}loans"><u>empréstimos</u></a>
,
<a href="${pagePrefix}loan_groups"><u>grupos de empréstimos</u></a>
, e para
<a href="${pagePrefix}member_records"><u>registros de membros</u></a>
.
<br><br>Aqui você pode definir a ordem em que os campos customizados irão
aparecer na página. Para fazer isso, coloque o ponteiro do mouse sobre o
nome do campo, pressione o botão esquerdo e mouse e mantenha
pressionado, arraste o nome para a posição desejada.<br>
Após terminar clique em &quot;enviar&quot; para salvar as alterações.
<hr class="help">

<a name="edit_custom_fields"></a>
<h3>Novo / editar campo customizado</h3>
Nesta janela você pode definir as propriedades no campo customizado.
Tenha cuidado para fazer isso corretamente, pois algumas opções só podem
ser definidas no momento da criação do campo. Após o campo estar em uso,
algumas mudanças não podem ser feitas.
<br>
Esteja ciente que nem todas as opções estão disponíveis para todos os
tipos de campos customizados. Atualmente os campos de membro (perfil)
possuem a maioria das opções possíveis.
<br><br>As seguintes opções estão disponíveis:
<ul>
	<li><b>Nome:</b> Este é o nome ou o &quot;título&quot; do campo,
	que será visível no Cyclos.
	<li><b>Nome interno:</b> Este é o nome interno do campo, é usado
	apenas para propósitos de desenvolvimento.
	<li><b>Tipo de dado:</b> Com esta opção você pode especificar o
	tipo do campo. Existem sete tipos de campos.
	<ul>
		<li><b>Texto:</b> Pode ser texto com qualquer caractere. Se você
		quiser especificar algum "padrão de entrada" obrigatório, como e-mail
		ou CEP, você pode criar uma máscara no campo abaixo. Na máscara você
		pode forçar o usuário a colocar a informação no formato correto. Ao
		enviar, os dados serão validados para assegurar que o formato de
		entrada esta correto. <br>
		Documentação sobre o padrão de entrada pode ser encontrada no site do
		projeto <a
			href="http://javascriptools.sourceforge.net/docs/manual/InputMask_mask.html"
			target="_blank"> JavaScript tools </a>.
		<li><b>Enumerado:</b> A tipo enumerado significa que você tem uma
		lista de valores, como área (&quot;norte&quot;, &quot;sul&quot;,
		&quot;leste&quot;, &quot;oeste&quot;). Uma lista enumerada pode ser
		apresentada como uma caixa de seleção ou campos de seleção (drop down
		box ou radio select). Quando enumerado é selecionado, sera apresentado
		um campo extra chamado &quot;Todas opções&quot; Esta opção sera
		exibida por padrão no topo da caixa de seleção. No exemplo acima com
		áreas esta opção mostraria &quot;Todas as áreas&quot;.
		<li><b>Número inteiro:</b> Este tipo significa que o campo pode
		conter apenas números, sem a casa decimal vírgula ou ponto (depende do
		idioma).
		<li><b>Número decimal:</b> Isto significa que o campo conterá
		números com a casa decimal. A precisão e o formato são definidos na <a
			href="${pagePrefix}settings#local"><u>&quot;Configurações
		locais > Formato de número&quot;</u></a>.
		<li><b>Data</b> Este campo só pode conter datas. O formato da
		data pode ser definido em <a href="${pagePrefix}settings#local"><u>&quot;Configurações
		locais > Internacionalização > Formato da data&quot;</u></a>.
		<li><b>Booleano:</b> Booleano é apenas uma caixa de seleção com
		dois valores possíveis: &quot;selecionado&quot; e &quot;não
		selecionado&quot; (ou &quot;verdadeiro&quot; e &quot;falso&quot;).
		<li><b>Endereço web:</b> Este campo é usado para o preenchimento
		do endereço web do membro, o endereço preenchido será exibido
		automaticamente na forma de um link.
	</ul>
	<li><b>Campo pai:</b> Aqui você define se os valores de escolha
	possíveis para este campo dependem dos valores de outros campos. Para
	mais explicações <a href="#parent_field"><u>clique aqui</u></a>.
	<li><b>Tipo de campo:</b> Existem diferentes tipos, de acordo com
	o tipo de dado. As seguintes opções existem:
	<ul>
		<li>Caixa de texto (uma linha), Área de texto (5 linhas) ou
		editor de texto formatado para o tipo de dado texto.
		<li>Caixa de seleção ou Botão seletor para o tipo de dado
		Enumerado
		<li>Apenas Caixa de texto para Número inteiro, decimais, datas e
		endereço web.
		<li>Apenas Caixa de seleção para o tipo booleano.
	</ul>
	<li><b>Tamanho do campo:</b> O tamanho do campo pode ser
	&quot;muito pequeno&quot;, &quot;pequeno&quot;, &quot;médio&quot;e
	&quot;grande&quot;. Os seus tamanhos exatos podem ser definidos no
	arquivo de folha de estilo. Você também pode escolher o tamanho
	&quot;completo&quot; o qual é a largura total da janela. A opção
	&quot;padrão&quot; pode ser diferente para cada tipo de campo, mas
	geralmente significa cerca de 80% do espaço disponível. Por exemplo o
	campo &quot;Nome&quot; nesta janela possui o tamanho
	&quot;padrão&quot;.
	<li><b>Exibir na busca (apenas campos de pagamento):</b> Se esta
	opção for marcada, o campo customizado de pagamento será exibido como
	um filtro no histórico da conta. No caso do tipo de pagamento ser um
	empréstimo, ele será exibido na busca de empréstimos para
	administradores.
	<li><b>Exibir na lista de resultados (apenas campos de
	pagamento):</b> Se esta opção for marcada o campo customizado de pagamento
	será exibido como uma coluna na lista de resultados da busca. No caso
	do tipo de pagamento ser um empréstimo, ele será exibido na lista de
	resultados da busca por empréstimos para administradores.<br>
	Observe que o campo sempre será incluído na função de exportar para
	arquivo csv e na de impressão. Mesmo que esta opção não esteja marcada.

	
	<li><b>Exibir no perfil para:</b> Aqui você pode definir quem pode
	ver o campo. Essa permissão funciona de modo hierárquico. Se um membro
	pode ver o campo, o corretor e o administrador também podem ver este
	campo. Se corretor é selecionado o administrador também poderá ver, e
	se administrador for selecionado apenas o administrador poderá ver.
	<li><b>Editável por:</b> Aqui você especifica quem pode modificar
	o campo (as permissões funcionam com a mesma estrutura hierárquica da
	opção anterior).
	<li><b>Busca por membros:</b> Aqui você define para quem o campo
	será exibido na página de busca por membros (as permissões funcionam
	com a mesma estrutura hierárquica da opção anterior).
	<li><b>Busca por anúncios:</b> Aqui você define para quem o campo
	será exibido na página de busca por anúncios (as permissões funcionam
	com a mesma estrutura hierárquica da opção anterior).
	<li><b>Incluir na busca por palavras-chave:</b> Com esta opção
	você torna o campo disponível para a opção de busca através de
	palavras-chaves nas buscas por membros e anúncios.<br>
	A busca por palavras-chave de membro possui a opção de incluir apenas
	na busca por membro (apenas membros) ou para ambos, busca por membros e
	anúncios. Caso a última opção seja selecionada o membros podem buscar
	anúncios usando campos dos membros (perfil). Note que isto é útil
	apenas para buscar combinadas. Uma busca por anúncios que corresponda
	ao campo de perfil de apenas um membro, exibirá todos os anúncios deste
	membro.
	<li><b>Busca por empréstimos:</b> Aqui você define para quem o
	campo será exibido na página de busca por empréstimos (as permissões
	funcionam com a mesma estrutura hierárquica da opção anterior).
	<li><b>O membro pode ocultar:</b> Aqui você define se o membro
	pode ocultar o conteúdo do campo para não ser visível à outros membros.
	
	<li><b>Exibir na impressão de membros:</b> Aqui você define se o
	campo será incluído no momento da impressão do resultado de uma busca
	de membros.
	<li><b>Validação:</b> Você pode definir as seguintes validações:
	<ul>
		<li><b>Obrigatório:</b> Se esta opção é marcada, o campo terá
		preenchimento obrigatório e um asterisco vermelho irá aparecer ao lado
		do campo.
		<li><b>Único:</b> Se esta opção for marcada, significa que o
		conteúdo do campo é único. Não pode existir nenhum outro registro com
		o mesmo valor. Isto pode ser usado para assegurar que os campos sejam
		únicos, como CPF, RG, Passaporte, etc.
		<li><b>Tamanho mínimo e máximo:</b> Se o campo for do tipo texto,
		você pode definir o tamanho mínimo e máximo do conteúdo. O usuário só
		poderá entrar dados que tenha o tamanho entre essa faixa.
		<li><b>Classe de validação:</b> Se você necessitar uma validação
		mais complexa, que não pode ser tratada por uma expressão regular (no
		campo de padrão de entrada acima) você pode escrever a sua própria
		classe de validação. <br>
		Uma situação típica pode ser no caso de você necessitar efetuar uma
		validação baseada em um cálculo do que foi informado no campo, ou uma
		validação remota.<br>
		Informações sobre como implementar uma classe de validação podem ser
		encontradas no site <a
			href="http://project.cyclos.org/wiki/index.php?title=Setup_%26_configuration#Custom_fields"
			target="_blank"><u>Wiki</u></a>.
	</ul>
</ul>
<li><b>Descrição:</b> Aqui você pode colocar uma descrição do
campo. O administrador pode explicar o uso do campo. A descrição é
visível apenas na janela de edição do campo.
<li><b>Habilitar campos para os grupos:</b> Aqui você seleciona os
grupos que irão possuir o campo em seu perfil.
</ul>
<hr class="help">

<a name="parent_field"></a>
<h3>Campo pai</h3>
Quando um campo possui um campo pai, isto significa que os valores
possíveis para este campo, dependem do valor escolhido no campo pai.
<br>
Por exemplo, Você pode ter um campo customizado &quot;Estado&quot;, e um
&quot;cidade&quot;. Se o usuário escolher o estado &quot;X&quot;, então
o campo cidade terá uma lista com todas as cidades do estado
&quot;X&quot;. Neste caso você deve marcar o campo &quot;estado&quot;
como sendo
<i>campo pai</i>
para o campo &quot;cidade&quot;.
<br>
No campo &quot;campo pai&quot; você pode escolher outros campos
existentes para atribui-lo como campo pai do campo atual. Observe que a
opção campo pai só é visível em campos do tipo enumerado.
<!--  check this: is dit waar?? -->
Para cada valor do campo pai você pode definir uma serie de valores
possíveis para o campo filho, através da janela
<a href="#possible_values"><u>Valores possíveis</u></a>
(disponível após clicar no botão &quot;enviar&quot;).
<hr class="help">


<a name="possible_values"></a>
<h3>Valores para campos customizados</h3>
Esta janela mostra uma lista de valores possíveis para o campo.
<br><br>Você pode apagar um valor clicando no ícone apagar. Isto funciona
apenas quando o valor específico não esta em uso. É possível
&quot;limpar&quot; valores atribuindo todos os valores usados para algum
outro da lista. (Isto é explicado mais adiante em editar valores).
<br><br>Se o seu campo possuir um <a href="#parent_field"><u>campo
pai</u></a>, antes de clicar no botão &quot;novo valor&quot; você deve primeiro
selecionar o valor apropriado do campo pai para o qual você quer definir
o novo valor. Você deve usar a caixa de seleção entre os botões
&quot;voltar&quot; e &quot;novo valor&quot; para escolher o valor do
campo pai.
<hr class="help">

<a name="edit_possible_value"></a>
<h3>Inserir valor / modificar valor para o campo</h3>
<br><br>As seguintes opções estão disponíveis:
<ul>
	<li><b>Nome do campo pai:</b> No caso do seu campo possuir um <a
		href="#parent_field"><u>campo pai</u></a> , o valor do campo pai onde
	os seus novos valores serão relacionados para exibição, é exibido neste
	rótulo. <br>
	(Se você quer definir novos valores para outros valores do campo pai,
	você deve voltar a <a href="#possible_values"><u>tela anterior</u></a>.
	
	<li><b>Valor:</b> Aqui você pode especificar o nome de um valor.
	Escreva o valor e selecione &quot;Enviar&quot;. O valor será exibido na
	lista de valores em ordem alfabética.
	<li><b>Padrão:</b> Caso selecionado, este valor será
	pré-selecionado quando o formulário for exibido. Pode existir apenas um
	valor padrão por campo customizado.
	<li><b>Ativo:</b> Se esta opção for marcada, o valor será exibido
	como uma opção possível de seleção. Caso não seja marcada o valor será
	exibido apenas onde exista dados no valor. Desta maneira você pode
	decidir não usar mais um valor que foi usado no passado, prevenindo que
	valores antigos não sejam perdidos.
	<li><b>Substituir ocorrências por: (modo de edição apenas)</b> Ao
	editar um valor, você pode mover os valores de todos os campos que
	contêm dados para um outro valor. Desta forma é possível remover um
	valor na página de lista de valores (permite que apenas valores que não
	estejam em uso seja removido). Se você quer prevenir valores
	existentes, você pode optar por desativar o valor, como explicado
	acima.<br>
	(Remover valores pode ser feito na página de lista dos valores, <a
		href="#possible_values"><u>tela anterior</u></a>).
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
<br><br>
</div>