<div style="page-break-after: always;">
<a name="top"></a>
<br><br>Documentos são páginas com informações que
podem ser exibidas na seção do membro no Cyclos. O membro pode
selecionar um documento a partir de uma lista. Existem dois tipos de
documentos, &quot;Estáticos&quot; e &quot;Dinâmicos&quot;. Documentos
estáticos são apenas arquivos, como um pdf que podem ser atribuídos a um
membro específico ou a um grupo de membros. Documentos dinâmicos são
documentos em html que podem ser atribuídos a um ou mais grupos de
membros. Estes documentos podem se tornar um trunfo para os membros,
pois eles podem recuperar dados do perfil do membro que esta
visualizando o documento.
<span class="admin"> Um documento dinâmico típico pode ser um
contrato de empréstimo ou qualquer tipo de requerimento que o membro use
para solicitar alguma coisa da administração.<br>
Um documento dinâmico pode ser exibido diretamente quando o membro abre
ele, mas também é possível que primeiro seja exibido um formulário que
precise ser preenchido pelo membro. Quando o membro envia o formulário,
o documento resultante pode incluir os dados que o membro informou no
formulário, bem como qualquer campo do seu perfil.

<br><br><i>Onde encontrar.</i><br>
Documentos podem ser encontrados através do &quot;Menu: Gerenciamento de
conteúdo > Documentos&quot;. Um exemplo de criação de documento pode ser
encontrado no site Wiki do Cyclos, dentro da seção &quot;configuração -
documentos dinâmicos&quot;. <br>
Documentos individuais de membros podem ser acessados através do <a
	href="${pagePrefix}profiles"><u> perfil</u></a> deste membro (bloco
&quot;informações do membro&quot;).
<br><br><i>Como ativar.</i><br>
Antes de você poder criar um documento, você precisará configurar as <a
	href="${pagePrefix}groups#manage_group_permissions_admin_member"><u>
permissões</u></a> primeiro. Isto pode ser feito no bloco &quot;Documentos&quot;.
Tendo essas permissões você pode criar novos documentos através do
&quot;Menu: Gerenciamento de conteúdo > Documentos&quot;.<br>
<br><br>Para cada documento criado, a visibilidade tem que ser definida
nas <a href="${pagePrefix}groups#manage_group_permissions_member"><u>
permissões</u></a> do grupo, bloco &quot;documentos&quot;. Assim isso significa
que documentos são atribuídos a certos grupos de membros. É possível
definir o documento como visível apenas por administradores, por
administradores e corretores, e por administradores, corretores e o
próprio membro (os membros nunca podem ver os documentos de outros
membros).<br>
<b>Note:</b> Não existe documentos de administradores no cyclos.
</span>
<span class="member">
<br><br><i>Onde encontrar.</i><br>
Documentos podem ser vistos através do &quot;Menu: Pessoal >
Documentos&quot;.
</span>
<hr>

<span class="admin"> <a name="document_list"></a>
<h3>Lista de documentos dinâmicos</h3>
Esta página mostra uma lista com <a href="#top"><u>documentos
dinâmicos</u></a> que foram definidos no sistema, a lista mostra o seguinte:
<ul>
	<li><b>tipo:</b> Mostra o <a href="#top"><u>tipo</u></a> do
	documento.
	<li><img border="0" src="${images}/edit.gif" width="16"
		height="16">&nbsp; clique no ícone editar de um documento para
	modifica-lo.
	<li><img border="0" src="${images}/view.gif" width="16"
		height="16">&nbsp; clique neste ícone para ver o documento.
	<li><img border="0" src="${images}/delete.gif" width="16"
		height="16">&nbsp; clicando neste ícone irá apagar o documento.
	
</ul>
Para criar um novo documento, você deve clicar e um dos dois botões
abaixo da janela, &quot;novo documento estático&quot; ou &quot;novo
documento dinâmico&quot;)
<hr class="help">
</span>

<span class="admin"> <a name="new_edit_static_document"></a>
<h3>Inserir / modificar novo documento estático</h3>
Isto permite que você insira um <a href="#top"><u>novo documento
estático</u></a>. Este formulário é muito simples: Apenas insira o nome e a
descrição para o documento, e preencha no nome do arquivo o &quot;para
envio&quot;, você pode usar o botão &quot;Arquivo...&quot; para
localizar o arquivo a ser enviado.<br>
Quando terminado clique no botão &quot;enviar&quot; para salvar o
arquivo.
<br><br>O arquivo do documento pode ser em qualquer formato. No caso de
alterar um arquivo existente, o arquivo atual é colocado sob o link
&quot;Arquivo atual&quot;; Você pode clicar neste link para ver a versão
atual do documento.
<br><br><b>Atenção</b>: Apenas criando um documento não quer dizer que os
seus membros/usuários poderão vê-lo. Após a criação você deve definir as
<a href="${pagePrefix}groups#manage_group_permissions_member"><u>permissões
para os membros</u></a> verem o documento, selecionando o novo documento na
caixa de seleção, na seção documentos, das permissões.
<hr class="help">
</span>


<span class="admin"> <a name="new_edit_dynamic_document"></a>
<h3>Inserir / modificar novo documento dinâmico</h3>
Isto permite que você insira um <a href="#top"><u>novo documento
dinâmico</u></a>. O formulário possui os seguintes elementos:
<ul>
	<li><b>Nome:</b> O nome do documento.
	<li><b>Descrição:</b> A descrição do documento (apenas para uso da
	administração)
	<li><b>Página do formulário:</b> Pode ser que o documento precise
	primeiro que o membro preencha alguma informação antes de imprimir o
	documento. Nesta página você pode escrever uma página html com um
	formulário requerendo as informações necessárias. Se você não
	necessitar desse tipo de informação, você pode deixar este campo vazio.
	
	<li><b>Página do documento:</b> Aqui você pode escrever a página
	do documento em formato html. Se você criou um formulário no campo
	anterior então você pode incluir no seu documento os dados que o
	usuário informou. A página do documento será aberta em uma janela
	pop-up com botões para imprimir e fechar. Você também pode inserir
	imagens, mas você deve fazer o envio delas antes para as &quot;<a
		href="${pagePrefix}content_management#custom_images"><u>Imagens
	customizadas</u></a>&quot;.
</ul>
<br><br>Nota 2: Existem exemplos de documentos dinâmicos disponíveis no
site wiki do cyclos, dentro da seção &quot;configuração - documentos
dinâmicos&quot;. Veja <a href="http://project.cyclos.org/wiki">project.cyclos.org/wiki</a>.
Após a criação do documento, você deve definir as <a
	href="${pagePrefix}groups#manage_group_permissions_member"><u>permissões
para os membros</u></a> verem os documentos, selecionando o novo documento,
dentro da seção documentos na página de permissões.
<hr class="help">
</span>

<a name="member_document"></a>
<h3>Baixar documento</h3>
Esta janela mostra uma lista de documentos que o administrador
disponibilizou para os membros. Este documentos podem ser baixados e
imprimidos.
<br><br>Se especificado pela administração, o documento pode mostrar
primeiro um formulário requerendo alguma informação adicional, para ser
incluída no documento. <span class="broker admin"> Para
administradores e corretores, o tipo de documento também é listado.
Documentos estáticos e dinâmicos podem apenas serem vistos nesta janela
(você deve ir ao &quot;Menu: Gerenciamento de conteúdo >
Documentos&quot; para gerencia-los); Contudo os documentos, específicos
do membro são gerenciados nesta janela. Neste caso você tem as seguintes
opções:
<ul>
	<li><img border="0" src="${images}/view.gif" width="16"
		height="16">&nbsp; permite que você veja o documento
	<li><img border="0" src="${images}/edit.gif" width="16"
		height="16">&nbsp; permite que você modifique o documento
	<li><img border="0" src="${images}/delete.gif" width="16"
		height="16">&nbsp; permite que você apague o documento.
</ul>
</span>
<hr class="help">

<span class="broker admin"> <a name="edit_member_document"></a>
<h3>Inserir /modificar documento de membro</h3>
Nesta janela você pode definir um novo documento &quot;Estático&quot;
para um membro específico. Este pode ser qualquer tipo de arquivo, com
um pdf ou uma figura. Se você quer modifica-lo, você pode apenas
sobrescrever o arquivo clicando no botão &quot;alterar&quot; primeiro.
Quando fito, use o botão &quot;enviar&quot; para salvar as alterações.
<ul>
	<li><b>Nome:</b> Apenas um nome descritivo
	<li><b>Descrição:</b> Visível apenas para administradores.
	<li><b>Visibilidade:</b> Aqui você define para qual tipo de
	usuário este documento estará visível. Se você selecionar
	&quot;membro&quot;, O membro pode ver o documento. Se corretor for
	selecionado apenas o corretor pode ver (e administradores com
	permissões) o documento. E obviamente se administrador for selecionado,
	apenas administradores poderão ver o documento.
	<li><b>Arquivo atual:</b> É o arquivo atual do documento. Você
	pode clicar no link para vê-lo. Este não estará disponível se você
	estiver criando um novo documento.
	<li><b>Enviar arquivo:</b> Apenas informe o nome do arquivo com o
	caminho completo para ele. Você pode usar o botão &quot;Arquivo&quot;
	para localizar o arquivo escolhido.
</ul>
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

