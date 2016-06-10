<div style="page-break-after: always;">
<br><br>As configurações do Cyclos definem
qualquer configuração a nível de sistema.
<i>Onde encontrar.</i>
<br>
O módulo de configurações pode ser acessado através do &quot;Menu:
Configurações&quot;.
<br>
Os seguintes tipos de configurações existem:
<ul>
	<li><b> <a href="#local"><u>Configurações locais:</u></a></b>
	Qualquer configuração local, como idioma, fuso horário e formatos.
	<li><b><a href="#alerts"><u>Configurações de alertas:</u></a></b>
	Limiares e outras configurações relativas ao sistema de alertas.
	<li><b><a href="#access"><u>Configurações de acesso:</u></a></b>
	Configurações relativas a acesso e nível de segurança.
	<li><b><a href="#mail"><u>Configurações de e-mail:</u></a></b>
	Configurações do servidor de e-mail.
	<li><b><a href="#log"><u>Configurações de Log:</u></a></b> As
	configurações dos arquivos de registros.
	<li><b><a href="#channels"><u>Canais:</u></a></b> Configurações de
	canais (meios de acesso).
	<li><b><a href="#web_services_clients"><u>Clientes de
	serviços</u></a></b> Configurações de serviços web.
	<li><b>Tarefas de sistema:</b> Existem dois tipos de tarefas de
	sistema. <b><a href="#search_indexes"><u> Gerenciamento dos
	índices </u></a></b> e <b> <a href="#online_state"><u> Disponibilidade
	do sistema </u></a></b>
	<li><b><a href="#import_export"><u>Exportar & importar</u></a></b>
	Configurações de importar e exportar.
</ul>
<hr>

<A NAME="local"></A>
<h2>Configurações locais</h2>
Estas são configurações relacionadas a localidade e ao sistema. Para
poder fazer alterações clique no botão &quot;alterar&quot;; Para salvar
as alterações clique em &quot;enviar&quot;.
<b>Identificação do Cyclos</b>
<ul>
	<li><b>Nome do aplicativo:</b> Este é o título da aplicação, que
	aparece na barra superior do seu navegador.
	<li><b>Nome de usuário do aplicativo:</b> Este é o nome usado para
	as mensagens geradas automaticamente do sistema para o membro. Por
	exemplo: A &quot;Administração do Cyclos&quot; aceitou a sua fatura
	eletrônica de ...
	<li><b>Identificação para canais externos:</b> Este nome é usado
	no caso de programas de terceiros que acessem o cyclos e precisem
	identificar a instância ao qual se conecta. Normalmente este é o nome
	da instalação.
	<li><b>URL da página container global</b> Este campo é necessário
	caso você queira acessar o Cyclos a partir de um site web (como o demo
	no site do projeto Cyclos). O site web deve ter um Iframe ou um frame
	set que inclua a página de acesso do Cyclos. Se você fizer isso, você
	pode querer customizar o &quot;cabeçalho&quot; (Em Gerenciamento de
	conteúdo - Arquivos estáticos - top.jsp) para que ele não seja mais
	exibido.<br>
	A url deverá ser a url completa com http:// ou https:// na frente.
	Esteja ciente que se você colocar a url, uma solicitação a página
	normal de acesso será diretamente direcionada para a página de
	container. Caso a página de container não funcione corretamente,
	significa que você não conseguirá acessar o cyclos com a url normal.
	Caso isso aconteça você sempre pode acessar o cyclos com a página
	original colocando após a url /do/login. Por exemplo
	http://www.seudominio.org/cyclos/do/login<br>
	Cyclos suporta múltiplas comunidades em uma instância. Cada comunidade
	pode ser acessada através de sua própria (customizada) página de
	acesso, ou a apartir do seu próprio site web. Para poder ativar isso,
	você precisa definir uma url de página container por grupo ou filtro de
	grupo. Neste tipo de configuração a url container global será o
	container padrão para os grupos que não possuam o seu próprio web site
	para acessar o cyclos.<br>
	Mais informações podem ser encontradas nas configurações de <a
		href="${pagePrefix}groups#group_details"><u>grupo</u></a> e <a
		href="${pagePrefix}groups#group_filter"><u>filtro de grupo</u></a>.
</ul>

<b>Internacionalização</b>
<ul>
	<li><b>Idioma: </b> Você pode escolher entre vários idiomas. Neste
	momento sete idiomas estão disponíveis.
	<li><b>Formato de número: </b> Escolha o formata para apresentar a
	o caractere separador dos números, vírgula ou ponto. No momento nós
	suportamos a formatação numérica americana e européia.
	<li><b>Precisão numérica: </b> Esta configuração apresenta o
	número de dígitos após a vírgula (ou ponto decimal). Se esta for
	definida como zero, a aplicação irá trabalhar apenas com número
	inteiros. Para a maioria dos sistemas esta é defina para 2.
	<li><b>Alta precisão: </b> Esta configuração apresenta o número de
	dígitos após a virgula (ou ponto decimal) usado apenas nas taxas de
	contas. Normalmente este número é o mesmo que o da precisão numérica.
	Mas em alguns casos pode ser necessário uma precisão mais alta para os
	cálculos das taxas de contas.
	<li><b>Método de digitação de números decimais: </b> Com esta
	configuração você pode definir se o separador decimal (ex. vírgula) é
	preenchido automaticamente, oque significa que o usuário digitará
	apenas números (primeira opção &quot;direita para esquerda&quot;) A
	segunda opção (esquerda para direita) significa que o usuário deverá
	digitar o separador decimal.
	<li><b>Fuso horário: </b> Aqui você pode definir o fuso horário.
	Isto só precisa ser definido caso o servidor estiver localizado em uma
	região com o fuso horário diferente da região onde estão os usuários da
	instância.
	<li><b>Formato da data: </b> Escolha o formato para imprimir as
	datas na tela.
	<li><b>Formato da hora: </b> Escolha o formato da hora. A hora é
	usada para exibir o momento exato (data/hora) das transações, alertas,
	faturas e observações.
</ul>

<b>Limites </b>
<ul>
	<li><b>Número máximo de resultados por página: </b> O número
	máximo de itens colocados em uma página de resultados.
	<li><b>Número máximo de resultados de Ajax: </b> O número máximo
	de itens colocados nos campos de auto completar, como o de &quot;Ir
	para o perfil&quot; e de pagamentos diretos e faturas.
	<li><b>Tamanho máximo de arquivo: </b> Este é o tamanho máximo
	para o envio de imagens de anúncios e perfil.
	<li><b>Tamanho máximo das imagens: </b> Este é o tamanho máximo
	(largura e altura) das imagens (ex. anúncios e perfil). Caso o tamanho
	enviado seja maior, cyclos irá reduzir automaticamente as imagens.
	<li><b>Tamanho máximo de miniatura das imagens: </b> Este é o
	tamanho máximo (largura e altura) de uma miniatura (imagem clicável
	para anúncios e membros). A miniatura será exibida com as mesmas
	dimensões da imagem original. Contudo o tamanho pode ser menor caso as
	dimensões sejam em formato &quot;retrato&quot;.
	<li><b>Vencimento da corretagem: </b> Se este período for definido
	o membro registrado de um corretor irá desaparecer da lista do corretor
	quando este período acabar. Se o seu sistema trabalha com <a
		href="${pagePrefix}brokering#commission"><u>commissões</u></a> de
	corretor você deve ter certeza de não ter definido o período de
	comissão menor que o período de vencimento.
	<li><b>Remover mensagens da lixeira após: </b> Este é o período
	máximo que as mensagens dos membros permanecerão na lixeira. Após esse
	período as mensagens serão removidas.
	<li><b>Tempo máximo para confirmação de e-mail no cadastro de
	membros: </b> Se um membro de registrar externamente e a opção de
	confirmação do e-mail estiver ativada (nas configurações do grupo
	&quot;Validar endereço de e-mail no cadastro público&quot;) o membro
	terá que confirmar o email neste período.
</ul>

<b>Exibição de informações: </b>
<ul>
	<li><b>E-mail obrigatório para membros: </b> Se esta opção for
	marcada, o campo de e-mail será obrigatório (no registro de membros
	pelo administrador ou no cadastro público de membros)
	<li><b>Exibir na lista de resultados de membros: </b> Com esta
	configuração você pode definir se o resultado da busca por anúncios ou
	membros mostrará o nome do membro ou o nome de usuário. Sistemas
	comunitários como LETS normalmente usam o nome de usuário como
	&quot;Apelido&quot;, e preferem que estes sejam exibidos na lista de
	resultados. Em uma rede de negócios como barters é mais comum usar o
	nome do membro.
	<li><b>Formato da descrição dos anúncios: </b> Aqui você pode
	escolher o formato da descrição dos anúncios (área de texto). Esta pode
	ser uma caixa de texto normal ou um editor wysiwyg - (what you see is
	what you get). Também é possível deixar as duas opções ao usuário, e
	ter uma definida como opção padrão.
	<li><b>Formato de mensagens simples: </b> Aqui você define o tipo
	de editor para usar com mensagens normais entre membros e de
	administradores para membros.
	<li><b>Formato de mensagens para grupos: </b> Aqui você define o
	formato do editor de mensagens para grupos (apenas administradores).
	<li><b>Formato de mensagens de corretor para seus membros
	registrados: </b> Aqui você pode definir o editor para as mensagens que um
	corretor envia para seus membros registrados.
	<li><b>Exibir contadores nas categorias de anúncio: </b> Se esta
	opção for selecionada, os contadores (números) ao lado das categorias
	de anúncios (nas páginas de busca por anúncios quando listam as
	categorias) serão exibidos. A razão pela qual alguém usaria isto, seria
	o caso de uma instância com vários grupos /comunidades que funcionem
	como grupos isolados e que não possam ver um ao outro (assim como os
	anúncios).<br>
	Como os contadores são globais, eles sempre representam o número total
	de anúncios no sistema (para cada categoria específica). Isto poderia
	ser confuso, pelo fato dos contadores mostrarem um número mais alto do
	que o número de anúncios disponíveis para uma determinada comunidade.<br>
	Assim no caso de uma configuração de multi-instâncias é melhor
	desativar os contadores.
</ul>

<b>Exportação para arquivos CSV: </b>
<ul>
	<li><b>Exibir cabeçalho: </b> Se esta opção for selecionada a
	primeira linha do <a href="${pagePrefix}loans#csv"><u>arquivo
	CSV</u></a> irá exibir o nome dos campos para cada coluna (por exemplo: nome do
	membro, endereço, etc...).
	<li><b>Delimitador de texto: </b> Quando esta opção for
	selecionada, os campos de &quot;textos&quot;, como descrição e título,
	serão colocados entre aspas.<br>
	Note: Cuidado, se você deixar esta opção como &quot;nenhum&quot; os
	campos de texto que contenham um valor separador (vírgula, tabulação ou
	ponto e vírgula) irão quebrar e irão ser colocados na próxima coluna
	(errada). Portanto é extremamente recomendado usar um delimitador de
	texto.
	<li><b>Separador de valores: </b> Este é o caractere separador
	para o arquivo CSV. Você pode precisar especificar este separador
	quando importar o arquivo em um programa de planilha eletrônica ou
	editor de texto.
	<li><b>Quebra de linha: </b> Este é o caractere usado para
	informar o &quot;final da linha&quot;. O padrão de quebra de linha unix
	é o padrão mais usado. O tipo &quot;DOS&quot; pode ser usado para
	sistemas windows.
</ul>

<b>SMS: </b>
<br><br>Se você ativar SMS, o cyclos irá permitir pagamento, faturas e
consulta de saldos através de SMS. O módulo de SMS no cyclos requer um
controlador SMS externo. Este software ainda esta sendo desenvolvido, e
neste momento nenhum plano de publicação esta definido. Você sempre é
bem-vindo a usar o seu próprio controlador SMS.<br>
A comunicação entre o Cyclos e o controlador SMS irá acontecer através
de serviços-web. Dessa forma você precisará informar uma URL para o
serviço-web. Você também precisa adicionar um canal para pagamentos SMS
(no &quot;Menu: Configurações - Canais&quot;). Uma vez que a opção SMS
estiver ativada, ela será exibida nas configurações do grupo e como uma
canal nas configurações de notificações.
<br><br><b>Número de transação: </b>
<ul>
	<li><b>Número de transação: </b> Se esta opção estiver ativada,
	cada transação no sistema irá gerar um número único de transação
	(identificador). O formato deste identificador pode ser definido nos
	seguintes campos:
	<ul>
		<li><b>Prefixo: </b> Prefixo do identificador (números ou letras)













		<li><b>tamanho do identificador: </b> O tamanho do identificador
		(sequencial)
		<li><b>Sufixo: </b> Sufixo do identificador (números ou letras)
	</ul>
	Por exemplo: O número de transação da primeira transação no sistema que
	a configuração for: Prefixo=abc, Tamanho=5, Sufixo=xyz será
	&quot;abc00001xyz&quot;
</ul>


<b>Estorno de pagamentos: </b>
Nesta configuração você pode definir o tempo máximo em que um
administrador pode estornar um pagamento. Estornar um pagamento,
significa que um pagamento sera feito no sentido contrário. No caso de o
pagamento ter gerado outras transações (ex. taxas e empréstimos) todas
as transações geradas irão gerar transações opostas também. O tempo
máximo em que o pagamento pode ser estornado pode ser definido. A
descrição dos pagamentos opostos gerados podem conter as variáveis
#date# e #description#. Onde a descrição é a descrição da transação
original.
<br><br>Tenha muito cuidado, se você estornar uma transação que é
relativa a um empréstimo, isto pode resultar em erros caso o empréstimo
tenha sido pago.
<br><br><b>Tarefas agendadas: </b> Esta configuração é normalmente usada
para propósitos de performance. Com esta configuração você pode definir
quando as tarefas irão ser executadas. Esta configuração é normalmente
usada por razões de performance no caso de mais de uma instância de
Cyclos rodar no mesmo sistema. Cyclos possui tarefas agendadas que rodão
a cada hora e diariamente.
<ul>
	<li><b>Hora das tarefas agendadas: </b> Aqui você pode definir a
	hora (0-24) para a tarefa que roda diariamente. Um exemplo de tarefa
	diária é a verificação por anúncios vencidos.
	<li><b>Minuto das tarefas agendadas: </b> Aqui você pode definir
	os minutos (0-60) para as tarefas que rodão diariamente e a cada hora
	(as tarefas diárias irão adicionar os minutos à hora configurada
	acima). Um exemplo de tarefa que roda a cada hora é a verificação de
	taxas de contas.
	<li><b>Reconstruir índices de busca a cada: </b> Com esta
	configuração você pode definir o tempo e a frequência que os índices do
	Cyclos são reconstruídos. No cyclos 3.5 os membros e anúncios são
	indexados, que ajuda a tornar as buscas mais rápidas. O indexamento
	também permite buscas por palavras-chaves múltiplas e todos os campos
	(perfil do membro e anúncios) serão procurados.<br>
	Como os índices podem de corromper com o tempo, é uma boa idéia
	reconstruí-los frequentemente. Nós sugerimos que os índices sejam
	reconstruídos semanalmente, em uma hora mais tranquila (à noite ou pela
	manhã). Dependendo do número de membros e de anúncios isto pode levar
	um pouco de tempo. O processo roda em uma thread diferente e não afeta
	o funcionamento do Cyclos.<br>
	A reindexação também pode ser feita removendo os índices manualmente no
	servidor (apenas remova o diretório indexes dentro do diretório
	WEB-INF) e reinicie o servidor ou a instância.
	<br><br>
</ul>
<hr>

<A NAME="alerts"></A>
<h2>Configurações de alertas</h2>
Nas configurações de alertas você pode definir os limites e limiares
para alertas que são relacionados ao comportamento de membros. Para
poder fazer alterações clique no botão &quot;alterar&quot;; para salvar
as alterações clique em &quot;enviar&quot;.
<br>
No momento os seguintes limiares de alertas podem ser definidos:
<ul>
	<li><b>Novo membro com ativação pendente: </b> Se esta opção for
	selecionada, um alerta de conta é gerado quando um novo usuário se
	registra (na página de login).
	<li><b>Referências "Péssimo" dadas: </b> Quando alguém tem dado
	mais do que &quot;x&quot; referências &quot;péssimo&quot; um alerta é
	gerado.
	<li><b>Referências "Péssimo" recebidas: </b> Quando alguém recebe
	mais do que x referências &quot;péssimo&quot; um alerta é gerado.
	<li><b>Vencimento de fatura eletrônica: </b> Quando alguém recebe
	uma fatura eletrônica, mas não toma nenhuma ação (não aceita nem
	rejeita a fatura), então após um período &quot;x&quot; um alerta é
	gerado.
	<li><b>Faturas eletrônicas negadas: </b> Quando alguém rejeita
	mais do que um &quot;x&quot; de faturas, um alerta é gerado.
	<li><b>Tentativas de acesso incorretas: </b> Após &quot;x&quot;
	tentativas de acesso falhadas, um alerta é gerado. No caso do usuário
	existir um alerta de membro será gerado. Caso o membro não exista será
	gerado um alerta de sistema.
</ul>
<hr>

<A NAME="access"></A>
<h2>Configurações de acesso</h2>
Aqui você pode definir as configurações relacionadas ao acesso ao
Cyclos. Para poder fazer alterações, clique no botão
&quot;alterar&quot;; Para salvar as alterações clique no botão
&quot;enviar&quot;.
<br>
As seguintes opções estão disponíveis:
<ul>
	<li><b>Usar teclado virtual: </b> Quando esta opção é marcada, os
	usuários (tanto membro quanto administrador) serão apresentados a um
	teclado virtual na página de acesso. A senha precisa ser informada
	usando o teclado virtual. O teclado virtual previne softwares
	maliciosos que capturam senhas.
	<li><b>Senha numérica: </b> Se esta opção é selecionada, membros
	pode apenas ter senhas numéricas. Esta opção pode ser necessária se os
	membros fazem pagamentos com cartões e um número PIN. Esta configuração
	não se aplica a administradores.
	<li><b>Permitir acesso de operador:</b> Se você tiver <a
		href="${pagePrefix}operators"><u>operadores</u></a> ativados, esta
	opção deve ser marcada, para permitir o acesso destes operadores.
	<li><b>Tempo de expiração da seção do administrador: </b> O tempo
	em que um administrador será desconectado após estar inativo.
	<li><b>Tempo de expiração da seção do membro: </b> O tempo em que
	um membro será desconectado após estar inativo.
	<li><b>Tempo de expiração da seção POSweb: </b> O tempo em que um
	membro ou operador será desconectado da página POSweb após estar
	inativo.
	<li><b>Whitelist para acesso à administração: </b> Aqui você pode
	colocar os endereços ip ou hostnames dos usuários que podem acessar a
	seção de administração. Por favor coloque qualquer hostname ou ip em
	uma nova linha (enter). Se você deixar a entrada &quot;#Any host&quot;
	vazia, qualquer host terá permissão de acesso à administração.
	<li><b>Geração de nome de usuário: </b>
	<ul>
		<li><b>Manual pelo membro: </b> Para redes comunitárias é comum
		que os usuários escolham seus próprios nomes de usuário ou
		&quot;apelido&quot;. Neste caso a opção &quot;Manual pelo membro&quot;
		precisa ser selecionada.<br>
		Se esta opção esta selecionada você pode especificar o tamanho mínimo
		e máximo para o nome de usuário, e uma expressão regular para forçar o
		formato do nome de usuário.<br>
		http://java.sun.com/j2se/1.5.0/docs/api/java/util/regex/Pattern.htm<br>
		<li><b>Número aleatório gerador (número de acesso): </b> Para
		redes de negócios é comum usar nomes de usuários gerados ou
		&quot;números de contas&quot;. Quando este módulo é ativado, o
		formulário de registro não possui um campo para informar o nome de
		usuário. Quando o formulário é submetido, um nome de usuário (número
		aleatório) será criado automaticamente. Abaixo desta opção você pode
		especificar o tamanho do código de acesso gerado.
	</ul>
	<li><b>Caracteres possíveis na senha de transação: </b> Aqui você
	pode definir os caracteres que serão usados (em ordem randômica) para a
	geração da senha de transação. (para as configurações da senha de
	transação consulte as <a href="${pagePrefix}groups#edit_member_group"><u>configurações
	do grupo</u></a>).
	</ul>
<hr>

<A NAME="mail"></A>
<h2>Configurações de e-mail</h2>
Nesta página você pode definir as preferências de e-mail. Você pode
definir o &quot;Endereço de remetente&quot; e os parâmetros do servidor
SMTP. Caso o seu servidor de email necessite TLS, você terá que marcar a
opção TLS.
<br>
Para poder fazer alterações, clique no botão &quot;alterar&quot;; Para
salvar as alterações clique em &quot;enviar&quot;.
<hr>

<A NAME="log"></A>
<h2>Configurações de Log</h2>
As configurações de log definem onde e como os logs são escritos. Os
arquivos de log do Cyclos não são escritos no banco de dados, são
escritos em arquivos log de texto no servidor. Portanto é importante que
esta configuração seja gerenciada pelo (ou em colaboração com)
administrador do servidor.
<br>
Para poder fazer alterações clique no botão &quot;alterar&quot;; Para
salvar as alterações clique em &quot;enviar&quot;.
<br><br>As seguintes configurações são possíveis:
<ul>
	<li><b>Nível de log de ações: </b> Este log irá conter informações
	sobre qualquer ação do cyclos, com a informação completa (rastro) sobre
	a ação, como data, membro, etc.<br>
	As seguintes opções são possíveis.
	<ul>
		<li><b>Desligado: </b> Log de ação não é usado.
		<li><b>Apenas erros: </b> Log de ação conterá apenas erros.
		<li><b>Rastreamento simples: </b> Log de ação conterá qualquer
		ação bem sucedida e ações com erros. Ele mostrará os métodos de
		chamadas consequentes.
		<li><b>Rastreamento detalhado:</b> O mesmo que o anterior, mas
		também mostrará os valores que são passados (parâmetros) e retornados.












	</ul>
	<li><b>Endereço/nome do arquivo de log de ações: </b> Aqui você
	pode especificar o caminho e o nome do arquivo. Se mais de uma
	instância esta instalada no servidor, é uma boa prática ter um
	diretório de log separado para cada instância.
	<ul>
		<li>"/" o separador local de caminho
		<li>"%t" diretório temporário do sistema
		<li>"%h" o valor para a propriedade "user.home" do sistema
		<li>"%g" Geração de número para distinguir logs rotativos
		<li>"%u" um número único para resolver conflitos
	</ul>
	<li><b>Nível de log de transações: </b> Este log conterá apenas
	transações com todas informações relacionadas como de membro/sistema
	para membro/sistema, valor, data, etc.<br>
	As seguintes opções são possíveis.
	<ul>
		<li><b>Desligado: </b> Log de transações não é usado.
		<li><b>Normal: </b> Quando esta opção é selecionada, o log irá
		conter todas as transações com a data, de membro/sistema para
		membro/sistema e o valor.
		<li><b>Detalhado: </b> Esta opção da a mesma informação que a
		opção normal, mais a descrição e o tipo de transação das transações.
	</ul>
	<li><b>Endereço/nome do arquivo de log de transações</b> <br>
	Aqui você pode especificar o caminho e o nome do arquivo da mesma
	maneira que a opção do arquivo de logs de ações (explicado acima).
	<li><b>Nível de log de taxas de conta: </b> Este log da
	informações sobre as taxas de conta (contribuições). A seção de
	administração do cyclos também vem com um <a
		href="${pagePrefix}account_management#account_fee_history"><u>histórico
	de taxas de conta</u></a>. A razão pela qual as taxas de conta foram incluídas
	nos logs foi para ser possível ter um registro completo de qualquer
	coisa que acontece com o Cyclos nos arquivos de log (fora do banco de
	dados). Além disso o registro das taxas de conta é mais estendido que o
	histórico das taxas de conta na seção de administração.<br>
	As seguintes opções são possíveis:
	<ul>
		<li><b>Desligado:</b> nenhum log é gravado
		<li><b>Apenas erros:</b> Mostrará apenas problemas
		<li><b>Mudanças de estado e erros:</b> Estados de alertas e de
		sucesso são registrados
		<li><b>Detalhado:</b> Registra todas as transações.
	</ul>
	<li><b>Endereço/nome do arquivo de log de taxas de conta</b> <br>
	Aqui você pode especificar o caminho e o nome do arquivo da mesma
	maneira que a opção do arquivo de logs de ações (explicado acima).
	<li><b>Nível do log de tarefas agendadas</b> <br>
	O log das tarefas agendadas contem o log das taxas de conta.
	Informações sobre taxas de contas agendadas também podem ser acessadas
	na função de <a
		href="${pagePrefix}account_management#account_fee_overview"><u>Gerenciamento
	de taxas de conta</u></a>. A razão pela elas também foram incluídas nos logs
	foi para ter um registro completo de qualquer ação no Cyclos
	separadamente do banco de dados.
	<ul>
		<li><b>Desligado:</b> nenhum log é gravado.
		<li><b>Apenas erros:</b> Mostrará apenas problemas
		<li><b>Execução sumarizada:</b> Apenas a informação de que a
		tarefa rodou com um intervalo de tempo.
		<li><b>Execução detalhada:</b> Informações detalhadas sobre a
		tarefa. (Esteja ciente que isto pode se tornar um pouco extenso, uma
		taxa de conta que cobre muitos membros, irá registrar todas transações
		em separado).
	</ul>
	<li><b>Endereço/nome do arquivo de log de tarefas agendadas</b> <br>
	Aqui você pode especificar o caminho e o nome do arquivo da mesma
	maneira que a opção do arquivo de logs de ações (explicado acima).
	<li><b>Número máximo de arquivos por log: </b> Aqui você pode
	especificar a quantidade máxima de arquivos de log. Quando o máximo de
	arquivos de log é atingido o arquivo de log mais antigo será apagado e
	um novo será criado. É uma boa prática ter certeza de que um backup dos
	arquivos de log é feito antes deles serem apagados.
	<li><b>Tamanho máximo do arquivo: </b> Quando o tamanho máximo do
	arquivo é atingido um novo arquivo de log é criado. É claro que quanto
	mais intenso for o seu registro, o valor para o tamanho máximo do
	arquivo deve ser aumentado.
</ul>
<hr>

<A NAME="channels"></A>
<h2>Canais</h2>
Cyclos pode ser controlado através de diferentes meios de comunicação ou
&quot;canais&quot;. Estes podem ser canais internos (Cyclos) como Web,
POSweb, telefone celular, e canais externos (pagamentos) usados para
acesso a partir de programas de terceiros como sites de e-commerce,
maquinas ATM e dispositivos POS.

Para poder fazer ser mais fácil de adicionar novos canais no futuro,
todos os canais são listados dinamicamente. Os canais existentes são
internos e para configurações normais do Cyclos, não é necessário
alterar as configurações deles.
<br>
Os canais (internos) disponíveis atualmente são:
<ul>
	<li><b>Acesso principal pela web: </b> Este é o seu acesso
	principal através do seu navegador web. Tipicamente com uma URL como
	www.seudominio.org/cyclos
	<li><b>Pagamentos Posweb: </b> O acesso POSweb (ponto de venda),
	como um consumidor em uma loja. Usado por <a
		href="${pagePrefix}operators"><u>operadores</u></a> ou diretamente por
	membros. Este pode ser acessado colocando /posweb ou /operator após a
	URL da instância. Por exemplo www.seudominio.org/cyclos/operator.<br>
	<li><b>Acesso WAP 1: </b> (wireless application protocol) é
	normalmente usado por modelos antigos de telefones móveis, que não
	suportam wap 2. O módulo pode ser acessado colocando /wap após o
	domínio.
	<li><b>Acesso WAP 2: </b> WAP 2 permite acesso à web através de
	telefones móveis. O módulo pode ser acessado colocando /mobile após o
	domínio.
	<li><b>Pagamentos de loja virtual: </b> O canal de pagamento de
	loja virtual permite pagamentos feitos desde softwares de e-commerce.
</ul>
Canais internos só podem ser adicionados com programação. Ao adicionar
um canal externo e você quer permitir pagamentos através deste canal,
você precisará adicionar o canal a um
<a href="${pagePrefix}account_management#transaction_types"><u>tipo
de transação</u></a>
(o que irá usar o canal). É uma boa prática ter apenas um tipo de
transação por canal.
<br>
Os grupos de membros que usarão o canal, também precisam ter o canal
habilitado (nas
<a href="${pagePrefix}groups#edit_member_group"><u>
configurações dos grupos</u></a>
) e o grupo também precisará de permissões para efetuar o tipo de
transação específica.
<hr>

<A NAME="channels_detail"></A>
<h3>Detalhes do canal (novo ou modificar)</h3>
É improvável que você vá precisar configurar canais. A única opção que
pode ser útil é definir se o canal irá solicitar por uma
<a href="${pagePrefix}passwords#pin"><u>Senha PIN</u></a>
ou uma senha de transação/acesso. Isto pode ser definido para Wap1/2 e
pagamentos de lojas virtuais (webshops). Os canais SMS e POSweb
trabalham exclusivamente com a senha PIN.
<hr>

<A NAME="web_services_clients"></A>
<h2>Clientes de serviço Web</h2>
Nos clientes de serviço web você pode definir qual programa externo pode
acessar o cyclos através dos serviços web, e você pode definir qual
serviço pode ser acessado.
<br><br>Você pode editar uma cliente de serviço clicando no ícone de
edição, e remove-lo clicando no ícone de apagar. <br>
Você pode clicar no ícone de edição <img border="0"
	src="${images}/edit.gif" width="16" height="16"> &nbsp; para
modificar o cliente de serviço web. Se você quiser adicionar um novo
cliente de serviço web clique no botão de &quot;enviar&quot; chamado
&quot;Novo cliente de serviço web&quot;. <br>
Caso você queira adicionar um novo cliente de serviço web, clique no
botão de &quot;enviar&quot; chamado &quot;Novo cliente de serviço
web&quot;.
<hr>

<A NAME="web_services_clients_detail"></A>
<h3>Inserir/modificar cliente de serviço web</h3>
Aqui você pode inserir um novo cliente de serviço web, ou editar um
existente. Quando pronto, clique no botão &quot;enviar&quot; para salvar
as suas alterações. Se você estiver modificando um cliente existente,
você deve primeiro clicar no botão &quot;alterar&quot; para fazer as
alterações.
<ul>
	<li><b>Nome: </b> Aqui você pode especificar o nome. Este é apenas
	para uso interno.
	<li><b>Endereço na internet: </b> Aqui você pode especificar um
	endereço IP ou nome de domínio (o qual será resolvido para uma endereço
	IP) ao qual é permitido acessar o serviço web.<br>
	Tenha cuidado que se você deseja conectar a um servidor através de um
	ISP o mesmo IP é provavelmente usado por outros sites web (usando
	hostheaders). Isto significa que todos estes sites podem ter acesso a
	instância web. Muitas vezes o IP pelo qual é resolvido o domínio de um
	site web é diferente do IP pelo qual este site se conecta. Neste caso
	você pode contactar o seu provedor para saber qual intervalo de IP é
	usado pelo seu site web para fazer conexões externas.<br>
	É possível especificar um intervalo de IP (ex. 77.88.45.0-255). Observe
	que especificar um intervalo de IP pode ser um problema de segurança.
	No caso de acessos menos críticos como visualização de anúncios este
	não deve ser o caso, mas para acessos mais sérios como pagamentos ou
	ver dados dos membros é preferível liberar apenas um endereço ip
	estático. Se você especificar uma url como endereço de host, você deve
	fazer sem colocar o prefixo http (ex. para o hostname 'http://www.mydomain.com'
	você deve informar apenas 'www.mydomain.com').<br>
	<li><b>Canal : </b> Aqui você pode selecionar um canal. Este pode
	ser um canal que você tenha adicionado ou o canal
	&quot;pré-definido&quot; para &quot;pagamentos em lojas virtuais&quot;.
	(O canal de pagamentos em loja virtual permite pagamentos a partir de
	sites de comércio eletrônico, mais informações podem ser encontradas no
	site Wiki do Cyclos, seção : webservices - webshop).
	<li><b>Restrito ao membro: </b> Aqui você define se o serviço web
	é restrito a um membro específico. O tipo de acesso irá depender das
	permissões (veja abaixo).
	<li><b>Nome de usuário / senha HTTP: </b> Aqui você pode
	(opcional) especificar um nome de usuário. Toda requisição http(s)
	feita pelo serviço web precisará ser autenticada com este nome e senha.<br>
	Caso esta opção seja utilizada, é uma boa prática habilitar a segurança
	https para que o nome de usuário / senha trafeguem encriptados.
	<li><b>Permissões: </b>
	<ul>
		<li><b>Efetuar pagamentos: </b> Esta opção só esta disponível
		caso um canal esteja selecionado. O serviço permite pagamentos através
		de canais de pagamentos (externos). Você pode selecionar um ou mais
		tipos de pagamentos. Caso o serviço web seja restrito por membro, este
		membro pode apenas efetuar pagamentos (não pode receber) através do
		canal.
		<li><b>Receber pagamentos: </b> Esta opção também só esta
		disponivel caso um canal e um membro (restrito) esteja selecionado. O
		serviço permite pagamentos através de canais de pagamentos (externos).
		Você pode selecionar um ou mais tipos de pagamentos. O membro restrito
		selecionado pode apenas receber pagamentos através do canal
		selecionado. O pagamento irá solicitar a senha <a
			href="${pagePrefix}passwords#pin"><u>PIN</u></a> do pagante.
		<li><b>Procurar anúncios: </b> Permite procurar por anúncios
		(normalmente em um site web). Se o serviço web for restrito por
		membro, o serviço pode apenas recuperar anúncios que o membro (grupo)
		pode ver.<br>
		Esta opção não esta disponível quando um canal é selecionado.
		<li><b>Procurar membros: </b> Permite procurar por membros
		(normalmente em um site web). Se o serviço web for restrito por
		membro, o serviço pode apenas recuperar listas de membros e campos de
		perfil que o membro (grupo) pode ver. <br>
		Esta opção não esta disponível quando um canal é selecionado.
		<li><b>Pagamentos de loja virtual: </b> Permite receber
		pagamentos de usuários do cyclos através de lojas virtuais. O
		pagamento de loja virtual possui apenas uma permissão que pode ser
		marcada (Pagamentos de loja virtual). A autenticação do membro é feita
		através do sistema de ticket. Isto é explicado no Cyclos wiki site >
		web services ( <a
			href="http://project.cyclos.org/wiki/index.php?title=Web_services"><u>
		http://project.cyclos.org/wiki/index.php?title=Web_services</u></a>).
		<li><b>Acesso a detalhes de conta: </b> Todas as buscas por
		detalhes de contas e transações. Se o serviço web for restrito por
		membro, o serviço pode apenas recuperar transações do membro.
	</ul>
</ul>
<hr>

<A NAME="search_indexes"></A>
<h2>Gerenciar índices</h2>
Índices são usados para facilitar buscas rápidas por usuários, anúncios
e registros de membros. Os Índices permitem buscar por palavra-chave ou
por uma combinação de palavras-chaves. Os Índices não estão gravados no
banco de dados, mas sim em arquivos separados no servidor. Caso nenhum
índice seja encontrado na inicialização do Cyclos, o processo de
inicialização ira cria-los. Com o tempo você pode querer otimizar os
índices após muito tempo de uso ( e muitos dados).
<br>
A opção recriar irá reconstruir os índices. Isto é apenas uma opção de
retrocesso e só deve ser usada no caso de problemas. Você pode
reconstruir e otimizar os índices conforme o tipo de índice. Caso queira
você pode recriar todos os índices de uma só vez clicando na opção
&quot;Reconstruir todos&quot;.
<hr>

<A NAME="online_state"></A>
<h2>Disponibilidade do sistema</h2>
Em alguns casos você pode querer evitar que usuários de conectem, para
fazer alguma manutenção ou configuração na seção de administração. Para
que não seja necessário desligar o Cyclos e exibir uma página de erro,
você pode desabilitar que os usuários de conectem e desconectar todos os
usuáriso atualmente conectados com esta opção. Apenas administradores
que possuam permissões para tornar o sistema disponível novamente
poderão efetuar o acesso.
<br>
As permissões podem ser encontradas em : Permissões do grupo de
administradores > Tarefas administrativas > Definir a disponibilidade do
sistema
<hr>

<A NAME="import_export"></A>
<h2>Importar & Exportar configurações</h2>
Com esta função, você pode exportar e importar configurações, para poder
compartilhar-las entre instâncias do cyclos. Todas as configurações
podem ser exportados e importados com exceção das configurações para
canais e serviços web (Porque estas são únicas para cada instância). A
função de importar e exportar são bastante simples. A exportação gera um
arquivo (legível) settings.xml com as configurações. A importação aplica
as configurações do arquivo que é importado.
<hr>

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