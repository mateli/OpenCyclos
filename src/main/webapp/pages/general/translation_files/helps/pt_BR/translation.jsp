<div style="page-break-after: always;">
<span class="admin">
<br><br>Cyclos suporta diferentes idiomas, e
permite que você gerencie as traduções. Todo texto exibido ao usuário
está contido em um arquivo específico do idioma (um para cada idioma), e
adicionalmente um número limitado de arquivos estáticos para blocos de
texto maiores.<br>
Cyclos vem com um número de idiomas para serem escolhidos. Através do
menu de configurações você pode escolher o idioma da usa instalação com
apenas alguns cliques do mouse.<br>
Se você não estiver satisfeito com a tradução vinda com o cyclos, você
pode modificar cada texto ou termo no cyclos. Este módulo também contém
uma seção para manusear todas as mensagens enviadas através do sistema
cyclos, e todas as notificações enviadas por email.
<i>Onde encontrar.</i><br>
O módulo de tradução pode ser acessado através do &quot;Menu:
Tradução&quot;. Os seguintes submenus estão disponíveis:
<ul>
	<li><b><a href="#translation_keys"><u>Aplicação</u></a>:</b>
	Permite a você gerenciar a tradução de todas os pequenos textos e
	termos que aparecem nas janelas da aplicação.
	<li><b><a href="#notifications"><u>Notificações</u></a>:</b>
	Permite a você gerenciar as notificações enviadas pelo sistema.
	<li><b>e-mails:</b> Permite a você gerenciar o texto dos e-mails
	enviados pelo sistema.
	<li><b>Importar/exportar:</b> Permite a você importar ou exportar
	arquivos de tradução. Isto permite que as comunidades do cyclos
	compartilhem suas traduções com outros usuários do cyclos.
</ul>
Nota1: Arquivos estáticos como a página de contatos e de notícias, não
são gerenciados através do módulo de tradução. Este pode ser feito
através do <a href="${pagePrefix}content_management"><u>gerenciamento
de conteúdo</u></a>.
<br><br>Nota2: Se você quiser alterar o idioma do cyclos, este não é o
lugar. O idioma pode ser alterado no bloco de
&quot;internacionalização&quot; das <a
	href="${pagePrefix}settings#local"><u>Menu: Configurações >
configurações locais</u></a>.<br>
<hr>

<a name="translation_keys"></a>
<h2>Chaves de tradução</h2>
Qualquer texto escrito que apareça na interface do Cyclos, é retirado de
um arquivo de idioma; Existe um para cada idioma. A interface do cyclos
permite que você substitua todo os arquivo do idioma, ou edite cada
chave do arquivo individualmente.

Se uma <a href="#key"><u>chave</u></a>, que esta colocada numa página da
aplicação, não puder ser encontrada no <a href="#language_file"><u>arquivo
de idioma</u></a>, a chave que aparece na página é exibida entre pontos de
interrogação. Normalmente irá se parecer com algo tipo:
&quot;???translationMessage.search.showOnlyEmpty???&quot;. Neste caso
você mesmo pode adicionar a chave (e seu valor) ao arquivo de linguagem,
através do &quot;Menu: tradução > aplicação&quot;.
<br><br>Se você não estiver contente com uma tradução, como ela aparece
na janela do navegador, você pode adapta-la. Siga este procedimento:
<ol>
	<li>Vá até a página de busca por termos da tradução (através do
	&quot;Menu: Tradução > Aplicação&quot;, la você pode digitar o termo
	que quiser adaptar no campo &quot;valor&quot;. A procura não é sensível
	a maiúscula ou minúsculas, e o programa também irá procurar por partes
	de termos que coincidam com o que você digitou. Clique em
	&quot;Procurar&quot; para mostrar os resultados.
	<li>Na página de resultados, use o ícone de modificar (<img
		border="0" src="${images}/edit.gif" width="16" height="16">&nbsp;)
	para alterar o valor.
</ol>
<br><br>Você também pode adicionar ou remover completamente um par de
chave/valor do arquivo. Isto é um pouco mais complicado, você deve ler
as <a href="#caution"><u>notas de precaução</u></a> nesta ajuda.
<hr>

<a name="caution"></a>
<h3>Precaução ao adicionar/remover chaves de idioma</h3>
A interface do cyclos permite que você modifique, adicione ou remove uma
<a href="#key"><u>chave</u></a> de idioma do <a href="#language_file"><u>arquivo
de idioma</u></a>. Modificar estas chaves é uma tarefa segura, mas adicionar ou
remover chaves pode ser uma pouco mais complicado. Você só deve fazer
isso, se você entender suficientemente como o sistema com chaves de
idioma funciona.<br>
Uma chave removida, é removida apenas do arquivo de idioma ao qual ela
pertence. Ela não é removida das páginas da aplicação. Se a chave ainda
estiver em uso em uma página da aplicação, na próxima vez que a página
for exibida aparecerá apenas o nome da chave entre pontos de
interrogação, o que normalmente não é muito bonito (exemplo:
&quot;???about.bla.something.title???&quot;). <br>
Vice versa: adicionar uma chave ao arquivo de idioma, não irá ajuda-lo
em nada se você não comerçar a usa-la em algum lugar. Você pode fazer
isso <a href="${pagePrefix}content_management"><u>customizando
páginas da aplicação</u></a>.<br>
Também pode acontecer de uma chave se perder após uma atualização -
embora isso seja muito raro. Uma atualização normal do cyclos,
adicionará as novas chaves de traduções. Nesse cado você pode adicionar
com segurança a chave.
<hr class="help">


<A NAME="application"></A>
<h3>Procurar chave de tradução</h3>
Nesta janela você pode procurar por <a href="#key"><u>chaves de
tradução</u></a>.<br>
Informe a chave ou o <a href="#value"><u>valor</u></a> no campo
apropriado. Observe que a busca não é sensível a maiúscula e minúscula,
e você não precisa informar o termo completo. A busca também é efetuada
por partes. Como sempre você não deve clicar em &quot;procurar&quot; sem
informar nenhum critério para a busca, isto retornará na caixa de
resultado todas os pares de chaves/valores disponíveis.<br>
Marcando a opção &quot;apenas valores vazios&quot; será exibido apenas
as chaves que não possuem uma tradução.<br>
O resultado da busca aparecerá na <a href="#application_results"><u>janela
de resultados da busca</u></a> abaixo. Nesta janela você têm a possibilidade de
alterar a tradução, uma chave por vez.
<br><br>É possível também adicionar uma nova chave de tradução; caso você
queira fazer, clique no botão de enviar chamado &quot;inserir nova
chave&quot;. Você deve ler as <a href="#caution"><u>notas de
precaução</u></a> deste documento antes.
<br><br>Note: Se você quiser alterar o idioma do cyclos, este não é o
lugar. O idioma pode ser alterado no bloco de
&quot;internacionalização&quot; das <a
	href="${pagePrefix}settings#local"><u>Menu: Configurações >
configurações locais</u></a>.<br>
<hr class="help">

<a name="application_results"></a>
<h3>Resultado da busca (por chave de tradução/valor)</h3>
Esta janela mostra o resultado da busca como você definiu na <a
	href="#application"><u>janela acima</u></a>.<br>
Nesta janela você pode selecionar uma chave e apaga-la (através do ícone
<img border="0" src="${images}/delete.gif" width="16" height="16">&nbsp;
apagar), ou edita-la (através do ícone <img border="0"
	src="${images}/edit.gif" width="16" height="16">&nbsp; de
edição). Se você quiser apagar várias chaves de uma vez, selecione uma
ou mais chaves nas caixas de seleção, e após use o botão &quot;remover
selecionadas&quot;.
<br><br>Observe que remover chaves pode ser complicado, você deve ler
antes as <a href="#caution"><u>notas de precaução</u></a> sobre isso.
<hr class="help">

<A NAME="edit_key"></A>
<h3>Modificar chave de tradução</h3>
Nesta janela você pode modificar o <a href="#value"><u>valor</u></a> de
uma <a href="#key"><u>chave</u></a> de tradução. Primeiro clique em
&quot;alterar&quot;, e faça as suas modificações, após clique em
&quot;enviar&quot; para salvar as mudanças.<br>
Você pode usar várias linhas, mas normalmente isto é ignorado e o
resultado é exibido em apenas uma linha.
<hr class="help">

<A NAME="insert_key"></A>
<h3>Nova chave de tradução</h3>
Aqui você pode adicionar uma nova <a href="#key"><u>chave</u></a> de
tradução e o <a href="#value"><u>valor</u></a>. Apenas informe os dados
nos campos apropriados, e clique em &quot;enviar&quot;. Antes de
adicionar novas chaves de tradução, aconselhamos a ler as <a
	href="#caution"><u>notas de precaução</u></a> sobre isso.
<hr class="help">

<A NAME="import_file"></A>
<h3>Importar / exportar arquivo de tradução</h3>
Nesta janela você pode <a href="#import"><u>importar</u></a> ou <a
	href="#export"><u> exportar</u></a> um <a href="#language_file"><u>arquivo
de idioma</u></a>. Siga os links para mais informações.
<hr class="help">

<a name="import"></a>
<h3>Importar arquivo de idioma</h3>
O retângulo acima nesta janela, é para importar um novo <a
	href="#language_file"><u>arquivo de idioma</u></a>. Isto deve ser um
caso raro, como por exemplo quando adicionar um novo idioma ao Cyclos.
Uma atualização normal do Cyclos irá adicionar automaticamente as novas
<a href="#key"><u>chaves</u></a> de tradução (caso exista alguma).
<br><br>Primeiro você deve decidir sobre &quot;oque fazer&quot;. Existe
quatro opções:
<ul>
	<li><b>Importar apenas chaves novas:</b> Isto irá importar apenas
	as novas chaves e deixar as chaves existentes como estão.
	<li><b>Importar apenas chaves novas e vazias:</b> O mesmo que a
	primeira opção, mas agora também será importada as chaves vazias (que
	são chaves cujos valores estão vazios, provavelmente por causa de uma
	tradução não terminada).
	<li><b>Importar chaves novas e modificadas:</b> Isto importa as
	chaves novas modificadas. Significa que se você alterou o valor de
	alguma chave, eles serão substituídos pelos valores "padrão". Chaves
	que não são mais usadas serão removidas.
	<li><b>Substituir todo o arquivo</b> Isto irá sobrescrever todo o
	arquivo do idioma. Todas customizações feitas no passado serão
	perdidas.
</ul>
Após isso você deve &quot;localizar&quot; o arquivo de tradução que esta
gravado no seu computador e clicar em &quot;enviar&quot;.
<br><br>Note: Não é necessário que o arquivo a ser importado contenha
todas as chaves, a não ser que você tenha escolhido a opção de
substituir todo o arquivo. Em todos os outros casos o arquivo pode
conter qualquer quantidade de chaves (mas deve estar no formato
correto).<br>
Quando você substituir o arquivo inteiro, tenha certeza de que o envio
do arquivo seja completado. Do contrário você corre o risco de perder as
chaves existentes.
<h3>Exportar arquivo de idioma</h3>
A parte do formulário para exportar o <a href="#language_file"><u>arquivo
de idioma</u></a> atual é muito simples: apenas use o botão &quot;enviar&quot;
chamado &quot;exportar como arquivo de propriedades&quot;. Se você
clicar neste botão, o navegador receberá o arquivo e normalmente
solicitará a você um local para salva-lo.<br>
Exportar o arquivo de idioma deve ser um caso raro; você deve fazer isso
quando você quiser tornar a sua tradução do cyclos, disponível a outras
comunidades de usuários do cyclos. Caso você faça sua própria tradução,
encorajamos você a nos contactar e tornar sua tradução disponível. Assim
podemos adicionar sua tradução à distribuição oficial do Cyclos. Acesse
o site do projeto para o endereço de contato. (<a
	href="http://project.cyclos.org"><u>http://project.cyclos.org</u></a>).
<hr class="help">

<a name="notifications"></a>
<h2>Notificações</h2>
Cyclos permite a você gerenciar o conteúdo de várias notificações com as
seguintes janelas.
<hr>

<A NAME="general_notifications"></A>
<h3>Notificações gerais</h3>
Esta janela mostra as suas <a href="${pagePrefix}notifications"><u>notificações</u></a>
gerais. Normalmente estas são prefixos e sufixos que são adicionados aos
e-mails de saída. Você pode alterar o conteúdo clicando no ícone de
edição (<img border="0" src="${images}/edit.gif" width="16" height="16">&nbsp;).
<hr class="help">

<A NAME="member_notifications"></A>
<h3>Notificações de membro</h3>
Esta janela mostra a você as <a href="${pagePrefix}notifications"><u>notificações</u></a>
que são enviadas as membros em várias ocasiões. Você pode alterar o
conteúdo clicando no ícone de edição (<img border="0"
	src="${images}/edit.gif" width="16" height="16">&nbsp;).
<hr class="help">

<A NAME="admin_notifications"></A>
<h3>Notificações de administrador</h3>
Esta janela mostra a você as <a href="${pagePrefix}notifications"><u>notificações</u></a>
que são enviadas aos administradores em várias ocasiões. Você pode
alterar o conteúdo clicando no ícone de edição (<img border="0"
	src="${images}/edit.gif" width="16" height="16">&nbsp;).
<hr class="help">

<A NAME="edit_notifications"></A>
<h3>Editar notificações</h3>
Esta janela permite a você alterar o conteúdo das notificações. Para
fazer isso, você deve clicar primeiro em &quot;alterar&quot;; Quando
pronto, você pode salvar as suas alterações clicando em
&quot;enviar&quot;.<br>
Um editor rich text irá aparecer, permitindo a você usar alguns recursos
de layout. Você também pode usar algumas variáveis, dependendo da
notificação que estiver modificando. <%-- Hoe kan ik weten welke?? --%>
<hr class="help">


<A NAME="mail_translation"></A>
<h3>Tradução de e-mail</h3>
Esta janela permite a você alterar o conteúdo das mensagens de e-mail
enviadas em certas ocasiões. Para fazer isso, você deve clicar primeiro
em &quot;alterar&quot;; Quando pronto, você pode salvar as suas
alterações clicando em &quot;enviar&quot;.<br>
Atualmente os seguintes e-mails podem ser alterados:
<ul>
	<li><b>E-mail de convite:</b> E-mail enviado ao usar a opção para
	<a href="${pagePrefix}home#home_invite"><u>convidar um amigo</u></a>
	através do &quot;Menu: Página principal > Convite&quot;.
	<li><b>E-mail de ativação:</b> E-mail enviado à um membro quando a
	conta dele é ativada. Isto acontece após o registro, quando um
	administrador ativa a conta movendo o membro do grupo de &quot;<a
		href="${pagePrefix}groups#inactive_members"><u> membros
	pendentes</u></a>&quot; para um outro grupo (normalmente o de membros plenos).

	
	<li><b>E-mail de validação do registro público:</b> O e-mail
	enviado para um membro em potencial, quando ele tenta se registrar.
	Para que este email seja enviado o cyclos deve ser <a
		href="${pagePrefix}notifications"><u>configurado para isso</u></a>.
	<li><b>E-mail de reinicialização de senha:</b> O e-mail enviado
	quando alguém necessita reiniciar a sua senha.
</ul>
Em todas essas definições, você pode usar variáveis para exibir dados no
texto. <%-- hoe?? welke?? --%>
<hr class="help">

<a name="imexport_notifications_mails"></a>
<h3>Importar / exportar notificações e traduções de e-mails</h3>
Com esta janela, você pode importar e exportar os textos traduzidos dos
e-mails e notificações para um arquivo. O arquivo é no formato XML, e
pode ser lido por qualquer instância do cyclos desta (ou futura) versão.<br>
Você pode usar isso para compartilhar traduções entre instâncias do
cyclos, ou por razões de segurança.<br>
O formulário é bastante simples. Quando você escolher
&quot;importar&quot;, você deve especificar o arquivo através do botão
&quot;arquivo&quot;. No caso de exportar um arquivo, o navegador
receberá e irá solicitar a você um local para salvar o arquivo baixado.
<hr class="help">

<br><br><a name="glossary"></a>
<h2>Glossário de termos</h2>
<br><br>

<a name="language_file"></a><b>Arquivo de idioma</b>
<br><br>No cyclos, para cada idioma existe um arquivo de idioma. Este
arquivo contem todos os textos escritos que aparecem na interface do
cyclos. (com excessão para blocos grandes de textos que que ficam em
arquivos próprios).<br>
Um arquivo de idioma é sempre nomeado de uma forma específica:
&quot;ApplicationResources_xx_XX.properties&quot;, onde o xx é
substituído pelo código do idioma e XX é substituido pelo código do
país. Exemplo: &quot;ApplicationResources_en_US.properties&quot; este é
o arquivo para o idioma Inglês dos Estados Unidos.<br>
Um arquivo de idioma contêm <a href="#key"><u>chaves</u></a> e <a
	href="#value"><u>valores</u></a>; Estes são separados pelo símbolo
&quot;=&quot;, sem nenhum espaço.
<hr class='help'>

<a name="key"></a><b>Chaves de tradução</b>
<br><br>As chaves de tradução são colocadas nas páginas da aplicação;
Quando estas páginas são exibidas no seu navegador, as chaves são
"olhadas" no <a href="#language_file"><u>arquivo de idioma</u></a>, e
substituídas pelo <a href="#value"><u>valor</u></a> correspondente
encontrado neste arquivo.
<hr class='help'>

<a name="value"></a> <b>Valores de tradução</b>
<br><br>Os valores de tradução são palavras e termos em seu próprio
idioma que você verá em seu navegador nas páginas do cyclos. As páginas
originais não contem esses valores. Ao contrário disso, <a href="#key"><u>chaves</u></a>
de tradução são colocadas nestas páginas; Quando estas páginas são
exibidas no seu navegador, as chaves são verificadas no <a
	href="#language_file"><u>arquivo de idioma</u></a>, e substituídas
pelos valores correspondentes encontrados neste arquivo.<br>
Todos os valores de tradução (tradução da aplicação, notificações e
e-mails) podem conter &quot;variáveis&quot;. Variáveis são sempre
cercadas por dois #, como #member#, #title# e #amount#. As variáveis
serão substituídas pelos valores corretos quando vistas no Cyclos. Os
nomes das variáveis são auto-explicativos e todas variáveis possíveis
são todas as usadas nos valores de tradução padrão.
<hr class='help'>

</span>

</div> <%--  page-break end --%>