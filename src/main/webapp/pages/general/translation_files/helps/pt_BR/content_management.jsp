<div style="page-break-after: always;">
<span class="admin"> <a name="top"></a>
<br><br>Cyclos permite a você mudar a interface
do usuário, modificando qualquer arquivo de sistema do cyclos.
Adicionalmente é possível empacotar essas mudanças em arquivos .theme, e
compartilhar esses arquivos com outros usuários do cyclos.<br>
Isto significa na prática que você pode basicamente controlar tudo sobre
as páginas que você ou seus membros irão ver no navegador. Os conteúdos
e aparências podem ser manipulados.
<a name="type_list"></a> Você pode customizar os seguintes tipos de
arquivos:
<ul>
	<li><b><a href="#statics"><u>Arquivos estáticos</u></a> </b>
	<li><b><a href="#helps"><u>Arquivos de ajuda</u></a> </b>
	<li><b><a href="#jsp"><u>Páginas da aplicação</u></a></b>
	<li><b><a href="#css"><u>Arquivos CSS</u></a></b>
	<li>Junto com isso você também pode customizar <a href="#images"><u>imagens</u></a>.


	
	<li><b><a href="#themes"><u>Temas</u></a>:</b> permitem a você
	trocar para um outro layout pré-definido sem precisar mudar um monte de
	arquivos.
</ul>
<b>Importante:</b> Observe que editar esses tipos de arquivos pode ser
um trabalho complicado. Você necessita de conhecimento em CSS e html
para faze-lo.
<br><br>Note que você não pode apenas customizar arquivos a nível de
sistema (o que é tratado nesse arquivo de ajuda); Você também pode
customizar arquivos a <a
	href="${pagePrefix}groups#manage_group_customized_files"><u>nível
de grupo</u></a> e <a
	href="${pagePrefix}groups#manage_group_filter_customized_files"> <u>nível
de filtros de grupos</u></a>.
<br><br><i>Onde encontrar.</i><br>
Gerenciamento de conteúdo à nível de sistema pode ser acessado através
do item de menu &quot;Gerenciamento de conteúdo&quot;.<br>
Para fazer o gerenciamento a nível de grupo, você deve ir a <a
	href="${pagePrefix}groups#manage_groups"><u>janela de
gerenciamento de grupos</u></a> e clicar no ícone <img border="0"
	src="${images}/edit.gif" width="16" height="16">&nbsp; de edição
do grupo. Este tema é tratado no arquivo de auda de grupos. <br>
Para fazer o gerenciamento a nível de filtro de grupo, você deve ir ao
&quot;Menu: Usuários & grupos > Filtros de grupos&quot;, e clicar no
ícone <img border="0" src="${images}/edit.gif" width="16" height="16">&nbsp;
de edição do filtro de grupo. Novamente este tema é tratado no arquivo
de ajuda de grupos.
<br><br><i>Como ativar.</i><br>
Você precisa definir as <a
	href="${pagePrefix}groups#manage_group_permissions_admin_system"><u>permissões
do administrador</u></a> para os itens de menu de gerenciamento de conteúdo
serem exibidos. Existem alguns itens que afetam a visibilidade dos itens
do &quot;Menu: Gerenciamento de conteúdo&quot;; os seguintes blocos se
aplicam:
<ul>
	<li>&quot;Imagens customizadas&quot;
	<li>&quot;Arquivos de sistema customizados&quot;
	<li>&quot;Temas&quot;
</ul>

<hr>

<a name="statics"></a>
<h3>Arquivos estáticos</h3>
Estes são todas as páginas da aplicação que podem ser customizadas para
sua organização <br>
Para modificar estes arquivos é necessário conhecimento de html. Tenha
cuidado de deixar as tags html completas e de não modificar conteúdos
com tags da aplicação (tags sempre estão dentro de &lt; e &gt;).
<br><br>A função permite modificar os seguintes arquivos estáticos (esta
lista pode estar desatualizada, e mais arquivos podem ter sido
adicionados):
<ul>
	<li><b>contact.jsp:</b> Página exibida na seção do membro no
	&quot;Menu: Ajuda > Contato&quot;; Deve ser dada informações sobre como
	contactar a sua organização.
	<li><b>general_news.jsp:</b> Notícias gerais, exibidas na janela <a
		href="${pagePrefix}home#home_news"><u>Notícias gerais (quadro
	de mensagens)</u></a> na página inicial da seção do membro do Cyclos.
	<li><b>login.jsp:</b> Layout da página de acesso. (O texto da
	página de acesso pode ser modificado na função de tradução).
	<li><b>member_about.jsp:</b> Página exibida na seção do membro e
	do administrador no &quot;Menu: Ajuda > Sobre&quot;.
	<li><b>posweb_footer.jsp:</b> Rodapé da página de pagamento
	externo posweb. (pode se customizada por membro)
	<li><b>posweb_header.jsp:</b> Cabeçalho da página de pagamento
	externo posweb. (pode se customizada por membro)
	<li><b>posweb_login.jsp:</b> Cabeçalho da página de acesso do
	módulo de pagamento externo posweb.
	<li><b>top.jsp:</b> Cabeçalho geral da aplicação.
	<li><b>webshop_footer.jsp:</b> Rodapé da página de pagamento
	externo webshop.
	<li><b>webshop_header.jsp:</b> Cabeçalho da página de pagamento
	externo webshop.
</ul>
Você também pode <a href="#insert_images"><u>inserir imagens</u></a> no
conteúdo dos arquivos estáticos.
<hr class="help">

<a name="helps"></a>
<h3>Arquivos de ajuda</h3>
Você pode querer mudar o arquivo de ajuda, no caso de ter trocado uma <a
	href="#jsp"><u>página da aplicação</u></a>, ao qual a ajuda referencia,
ou apenas no caso de você achar que o texto original não esta
suficientemente claro..<br>
Se você quiser modificar um arquivo de ajuda, você precisa saber o seu
nome e a tag dentro do arquivo para chegar a ele. Arquivos de ajuda, são
organizados por temas principais; Existe cerca de 30 arquivos de ajuda,
cada um contêm o texto da ajuda de várias janelas, saltando para essas
janelas via tags.<br>
Para descobrir o nome do arquivo e o nome da tag você tem que passar o
ponteiro do mouse sobre o ícone de ajuda (no canto superior direito de
cada janela). Na barra de status do seu navegador irá aparecer a
localização : &quot;Help: file_name#tag_name&quot;; Neste caso o arquivo
deve ser &quot;file_name.jsp&quot; e a seção (tag) no arquivo de ajuda
deve ser &quot;tag_name&quot;. Dentro do arquivo de ajuda este nome de
tag é colocado dentro de &lt;a name="tag_name"&gt;...&lt;/a&gt; tag.<br>
Observe que o seu navegador pode ocultar a barra de status; No caso dele
não mostrar a localização do arquivo de ajuda, você deve mudar as
configurações do navegador para ter certeza que &quot;mostrar a barra de
status&quot; esta configurado.
<br><br>Você também pode inserir <a href="#insert_images"><u>imagens</u></a>
no conteúdo do arquivo de ajuda, mas tenha cuidado com o tamanho da
imagem, pois a janela de ajuda possui apenas o tamanho de 300 por 400
pixels.
<hr class="help">

<a name="jsp"></a>
<h3>Páginas da aplicação</h3>
Uma página da aplicação é um arquivo .jsp no cyclos, contendo código
para colocar elementos nas páginas, mas sem conter texto. Na prática
isto significa que é qualquer arquivo jsp que não contenha um texto de
ajuda. Estes arquivos .jsp definem oque vai aonde na página que você ve
no seu navegadorr. <br>
A estrutura de layout atual pode ser modificada, mas isso pode afetar
seriamente o sistema interno e o funcionamento da aplicação. Portanto,
tenha cuidada ao modificar as páginas da aplicação. É recomendado
modifica-las apenas para mudanças de layout pequenas - por exemplo mudar
a ordem dos elementos, ou caso não queira um elemento visível na página.
Antes de modificar a página veja se a sua finalidade não pode ser
alcançada através da configuração normal do cyclos pela seção de
administração do programa.
<br><br>Para evitar problemas o Cyclos sempre irá manter uma cópia da <a
	href="#edit_customized_file"><u> página original</u></a> que pode ser
revertida facilmente.
<br><br>Você também pode inserir <a href="#insert_images"><u>imagens</u></a>
no conteúdo de um arquivo jsp.
<hr class="help">

<a name="css"></a>
<h3>Arquivos CSS</h3>
Arquivos CSS (cascading style sheet) definem como os elementos na página
irão parecer. Você pode querer modificar esses arquivos para dar a
certos elementos outro aspecto. Por exemplo aumentar botões, clarear
itens de menu, etc. para modificar o arquivo de estilo, conhecimento em
CSS é necessário.

<br><br>Cyclos tem os seguintes arquivos de estilo:
<ul>
	<li><b>style.css</b><br>
	O arquivo de estilo principal para o layout do cyclos (menu, janelas e
	cabeçalho)
	<li><b>login.css</b><br>
	Arquivo de estilo para a página de acesso.
	<li><b>mobile.css</b><br>
	Arquivo de estilo para a página de acesso via telefone móvel.
	<li><b>posweb.css</b><br>
	Arquivo de estilo para a página do módulo de pagamento posweb.
	<li><b>ieAdjust.css</b><br>
	Usado para resolver problemas de compatibilidade com o navegador
	Internet Explorer
</ul>
<br><br>Para ver o efeito das mudanças do arquivo CSS você vai precisar
recarregar a página no seu navegador. Isto pode ser feito indo para a
url &quot;www.seudominio.org/cyclos/pages/styles/style.css&quot;.<br>
O conteúdo do arquivo css será exibido na forma de texto. Para ter
certeza que a página sera atualizada com o novo conteúdo você pode
clicar algumas vezes em recarregar no seu navegador.
<br><br>Se você quiser usar um novo arquivo css, você pode apenas copiar
todo o seu conteúdo e coloca-lo sobre o conteúdo existente do css (na
janela de edição de estilo). E recarregue a página conforme descrito
acima.
<br><br>Se você tiver conseguido fazer um arquivo CSS agradável, você é
bem vindo em envia-lo para nós, para que ele seja compartilhado com
todos. O arquivo de estilo sera publicado no sourceforge e no site do
projeto.
<hr class="help">

<a name="insert_images"></a>
<h3>Inserir imagens no conteúdo dos arquivos</h3>
É possível inserir imagens em arquivos de texto como os <a
	href="#statics"><u>arquivos estáticos</u></a> e as páginas de <a
	href="#helps"><u>ajuda</u></a>. Para fazer isso as imagens devem estar
disponíveis na aplicação. Você pode verificar qual <a
	href="#system_images"><u>Imagem de sistem</u></a> está disponível, ou
você pode enviar sua própria image em &quot;Menu: Gerenciamento de
conteúdo > Imagens customizadas&quot;. Para inserir uma imagem em uma
página as seguintes tags devem ser colocadas no inicio do arquivo
estático:
<br><br><i>&lt;%@ taglib
uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html"
%&gt;</i>
<br><br>e a localização da imagem sera algo como:
<br><br><i>&lt;img src="&lt;html:rewrite
page="/pages/images/some_image.jpg"/&gt;"&gt;</i>
<br><br>A página precisa apenas da tag 'html:rewrite' para a localização
da imagem. Você pode usar os argumentos normais do html como: border,
align, width and height.
<hr class="help">

<a name="customized_files"></a>
<h3>Arquivos customizados</h3>
<b>Note:</b> Este arquivo de ajuda mostra apenas uma informação geral do
funcionamento deste formulário. Você pode querer verificar <a
	href="#type_list"><u>esta lista</u></a> e seguir o link do tipo de
arquivo, para informações específicas e dicas do tipo de arquivo que
você esta customizando.
<br><br>Esta janela mostra uma lista com os arquivos que ja foram <a
	href="#top"><u>customizados</u></a>. Você tem as seguintes opções:
<ul>
	<li><b>Customizar novo arquivo:</b> customize um novo arquivo
	clicando no botão chamado &quot;customizar novo arquivo&quot;.
	<li><img border="0" src="${images}/preview.gif" width="16"
		height="16">&nbsp; Mostra uma pré-visualização do resultado para
	você.
	<li><img border="0" src="${images}/edit.gif" width="16"
		height="16">&nbsp; Permite que você edite um arquivo
	customizado.
	<li><img border="0" src="${images}/delete.gif" width="16"
		height="16">&nbsp;Clicando no ícone apagar, o arquivo
	customizado desaparecerá da lista. Isto significa que as últimas
	modificações ainda serão visíveis, mas na próxima atualização do cyclos
	elas serão substituídas pela página original.
</ul>
Obs1: Quando o nome de um arquivo é exibido com a cor vermelha, isto
significa que existe um conflito. Uma explicação de como lidar com
conflitos pode ser encontrada na página de ajuda de <a
	href="#edit_customized_file"><u>Editar arquivo customizado</u></a>.<br>
Obs2: Quando você usar esta funcionalidade pela primeira vez, não
existirá nenhum arquivo customizado na lista, assim nenhum ícone estará
visível. Isto depende do tipo de arquivo que você quer customizar.
<hr class="help">

<a name="edit_customized_file"></a>
<h3>Editar arquivo customizado</h3>
<b>Note:</b> Esta seção da ajuda mostra apenas uma informação geral
sobre o funcionamento deste formulário. Você pode querer verificar <a
	href="#type_list"><u>esta lista</u></a> e seguir o link do tipo de
arquivo, para informações específicas e dicas do tipo de arquivo que
você esta customizando.
<br><br>Esta janela mostra uma lista com os arquivos que ja foram <a
	href="#top"><u>customizados</u></a>.
<br><br>Nesta janela você pode modificar novamente um arquivo que já
tenha sido customizado. Como de costume, clique no botão
&quot;alterar&quot; para começar a modificar o arquivo, e no botão
&quot;enviar&quot; para salvar as suas alterações.
<ul>
	<li><b>Nome do arquivo:</b> Mostra o nome do arquivo. Este não
	pode ser modificado.
	<li><b>Conteúdo:</b> Mostra o conteúdo atual do arquivo. Você pode
	criar ou modificar o conteúdo do arquivo. Você pode usar tags html e
	xml, o que dá a você uma série de possibilidades, como por exemplo a
	página de &quot;notícias gerais&quot; com uma barra de links rápidos
	para outras seções do cyclos. Javascripts também podem ser adicionados
	para definir comportamentos. Entretanto, esta é uma programação
	sofisticada, para a qual você deve se referenciar no <a
		href="http://project.cyclos.org/wiki/index.php?title=Programming_guide#JSP"><u>Guia
	de programação do cyclos</u></a>.
	<li><b>Conteúdo original:</b> Mostra a você o conteúdo original do
	arquivo, como ele era antes de ser customizado. Esta versão original
	será válida na próxima atualização do cyclos, caso você decida apagar o
	arquivo customizado da <a href="#customized_files"><u>lista</u></a>. É
	claro que você pode apenas copiar e colar o texto do &quot;conteúdo
	original&quot; na caixa de &quot;conteúdo&quot;, no caso do conteúdo
	que você definiu parecer não estar funcionando.
	<li><b>Novo conteúdo:</b> Se você atualizou/atualizar o Cyclos e
	existir uma nova versão do arquivo, Cyclos não irá substituir o arquivo
	com o novo conteúdo, mas irá gerar um alerta de sistema. O novo
	conteúdo estará disponível nesta seção.
	<li><b>Resolver conflitos:</b> Quando existir uma nova versão de
	um arquivo que foi customizado, Cyclos irá gerar um alerta de sistema e
	colocar a nova versão na seção de &quot;novo conteúdo&quot;. Além disso
	o nome do arquivo &quot;conflitante&quot; que foi customizado irá
	aparecer em vermelho na lista de arquivos customizados.<br>
	Uma vez que o conflito esteja resolvido e tudo esteja funcionando
	corretamente você pode marcar a opção &quot;resolver conflito&quot; e
	salvar o arquivo. Após fazer isto o nome não será mais exibido em
	vermelho na lista de arquivos, e o novo conteúdo da versão do arquivo
	será movido para a seção de conteúdo original.
</ul>
<hr class="help">

<a name="edit_new_customized_file"></a>
<h3>Customizar (novo) arquivo</h3>
Nesta janela você pode escolher um arquivo para começar a <a href="#top"><u>customização</u></a>.
Escolha o arquivo que você quer customizar na &quot;caixa de
seleção&quot;. Você deve ver os arquivos listados diretamente, ou os
diretórios onde esses arquivos estão. Os diretórios serão listados no
campo de &quot;caminho&quot; acima da caixa de seleção. Você pode voltar
para o diretório anterior clicando no link &quot;acima&quot; ao lado da
caixa de seleção. Quando você chegar num diretório que contenha
arquivos, você pode selecionar o arquivo e o seu conteúdo sera mostrado
na área de texto abaixo. <br>
Então você pode editar o arquivo após ter clicado no botão
&quot;alterar&quot;. Salve suas mudanças clicando no botão
&quot;enviar&quot;.
<br><br>Quando você salvar as alterações do arquivo customizado, o
conteúdo original do arquivo sera salvo e exibido abaixo da
customização. Quando uma atualização é instalada, o cyclos irá manter o
arquivo customizado, mas irá verificar se existe diferenças entre o
arquivo original antigo e o arquivo atualizado. Se for o caso ele
colocará o novo conteúdo abaixo do conteúdo original. Clicando em
&quot;Resolver conflito&quot; você moverá o novo conteúdo para o
conteúdo original.
<br><br>Quando você para de customizar um arquivo (removendo ele da
lista) o conteúdo original sera usado.
<hr class="help">

<a name="images"></a>
<h2>Imagens customizadas</h2>
Você também pode customizar imagens no cyclos. Existe uma série de
imagens de sistema, mas você pode enviar suas próprias imagens e começar
a usa-las em qualquer arquivo customizado.

Você pode enviar sua imagem através do &quot;Menu: Gerenciamento de
conteúdo&quot;, onde você pode escolher alterar <a href="#system_images"><u>imagens
de sistema</u></a>, enviar suas próprias <a href="#custom_images"><u>imagens
customizadas</u></a>, ou alterar <a href="#style_images"><u>imagens de
folha de estilo</u></a>.
<hr>

<A NAME="system_images"></A>
<h3>Imagens de sistema</h3>
<br><br>Esta janela mostrará uma lista com as <a href="#images"><u>imagens</u></a>
de sistema atuais do cyclos. Se você clicar na miniatura de uma imagem,
ela sera exibida em seu tamanho original numa janela pop-up. Você pode
substituir uma imagem de sistema através da janela <a
	href="#images_upload"><u> atualizar imagem de sistema</u></a> abaixo.
<hr class="help">

<A NAME="custom_images"></A>
<h3>Imagens customizadas</h3>
<br><br>Esta janela mostrará uma lista com as <a href="#images"><u>imagens</u></a>
customizadas atuais do cyclos. Se você clicar na miniatura de uma
imagem, ela sera exibida em seu tamanho original numa janela pop-up. As
imagens customizadas podem ser usadas nos <a href="#statics"><u>arquivos
estáticos</u></a>, <a href="#helps"><u>arquivos de ajuda</u></a>, e também em
mensagens.
<br><br>Você pode apagar uma imagem clicando no ícone <img border="0"
	src="${images}/delete.gif" width="16" height="16">&nbsp;apagar.<br>
Você pode adicionar uma imagem customizada através da janela <a
	href="#images_upload"><u>nova imagem customizada</u></a> abaixo.
<hr class="help">

<A NAME="style_images"></A>
<h3>Imagens de folha de estilo</h3>
<br><br>Imagens de folha de estilo são <a href="#images"><u>imagens</u></a>
que podem ser usadas no layout do Cyclos, como cabeçalho das janelas,
itens de menu, botões e planos de fundo. Você deve usar estas imagens em
um <a href="#css"><u>arquivo css</u></a>.
<br><br>Você pode apagar uma imagem clicando no ícone <img border="0"
	src="${images}/delete.gif" width="16" height="16">&nbsp;apagar.<br>
Você pode adicionar uma imagem de folha de estilo através da janela <a
	href="#images_upload"><u>nova imagem de folha de estilo</u></a> abaixo.
<hr class="help">

<A NAME="images_upload"></A>
<h3>Envio de imagens</h3>
<br><br>No caso de uma <a href="#system_images"><u>imagem de
sistema</u></a>, você deve primeiro selecionar qual imagem você quer substituir,
selecionando o nome na lista acima, na caixa de seleção &quot;carregar
nova&quot;. Observe que esta caixa de seleção não estará visível quando
estiver enviando uma <a href="#custom_images"><u>imagem
customizada</u></a> ou uma <a href="#style_images"><u>imagem de folha de
estilo</u></a>.<br>
Então clique no botão &quot;arquivo...&quot; e encontre no seu
computador a imagem que você quer enviar. Tenha certeza de que ela seja
do tipo jpg, jpeg, gif or png. Após clique em &quot;enviar&quot;. A nova
imagem irá aparecer na janela acima.
<hr class="help">

<a name="themes"></a>
<h2>Temas</h2>
Um tema, muitas vezes chamado de &quot;skin&quot;, define o layout do
Cyclos (na página de login, barra superior de status, menu esquerdo, e
janelas de funções). A função dos temas é apenas uma forma rápida para
se trocar entre layouts sem a necessidade de editar o arquivo geral de
folha de estilo e imagens de folha de estilo.

A função tema pode ser acessada através do &quot;Menu: Gerenciamento de
conteúdo > Temas&quot;.
<hr>


<A NAME="select_theme"></A>
<h3>Selecionar tema</h3>
<br><br>Para selecionar outro <a href="#themes"><u>tema</u></a> você deve
escolher um na caixa de seleção &quot;temas&quot;, e então clicar no
botão &quot;enviar&quot;. Você precisará recarregar a página em seu
navegador para o novo tema ser mostrado. Em alguns casos é necessário
limpar o cache do navegador.<br>
<br><br>Quando você clicar no botão &quot;Remover&quot; o tema
selecionado irá ser apagado da lista de seleção. A função tema apenas
afeta as imagens de folha de estilo e os arquivos css.
<hr class="help">

<A NAME="import_theme"></A>
<h3>Importar novo tema</h3>
<br><br>Com esta função você pode importar um <a href="#themes"><u>tema</u></a>.
Um tema do cyclos possui a extensão .theme. Use apenas o botão
&quot;arquivo...&quot; para localizar o arquivo de tema e clique em
&quot;enviar&quot;.
<hr class="help">

<A NAME="export_theme"></A>
<h3>Exportar as definições atuais como tema</h3>
<br><br>Se você criou o seu próprio <a href="#themes"><u>tema</u></a>
(modificando o <a href="#css"><u>arquivo de folha de estilo</u></a> e/ou
as <a href="#style_images"><u>imagens de folha de estilo</u></a>) você
pode exporta-las como tema na form a de uma arquivo .theme.<br>
Esta função permitirá que você exporte o tema atualmente ativo. Apenas
preencha os campos e clique no botão &quot;enviar&quot; abaixo.<br>
Você também pode exportar partes do tema, marcando as caixas de seleção
abaixo. Três opções estão disponíveis:
<ul>
	<li><b>Sistema</b> Estes são os arquivos <a href="#css"><u>.css</u></a>
	da aplicação e as imagens de folha de estilo</li>
	<li><b>Página de login</b> O arquivo css que define o estilo da
	página de login e as imagens usadas nele</li>
	<li><b>Dispositivo móvel</b> O arquivo css que define o estilo da
	página para o acesso móvel.</li>
</ul>
<br><br><i>Se você desenvolveu o seu próprio tema, considere envia-lo
para o time de desenvolvimento do Cyclos.</i> Nós podemos torna-lo
disponível para outros usuários do Cyclos.
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

</span>
