<div style="page-break-after: always;">
<span class="admin">
<br><br> Alertas são para notificar
administradores sobre vários eventos incluindo erros do sistema e
eventos relativos aos membros (número de tentativas de início de sessão
com uma determinada conta, atingindo o limite de crédito, etc).

<i>Onde encontrar.</i><br>
Alertas e logs podem ser acessados através do &quot;Menu: Alertas&quot;.<br>
<br><br><i>Como ativar.</i><br>
<a href="${pagePrefix}alerts_logs#system_alerts"><u>Alertas de
sistema</u></a> estão sempre ativos.<br>
<a href="${pagePrefix}alerts_logs#member_alerts"><u>Alertas de
membros</u></a> podem ser configurados na página de <a
	href="${pagePrefix}settings#alerts"><u>configuração de alertas.</u></a>
<hr>

<a NAME="system_alerts"></a>
<h3>Alertas de sistema</h3>
A janela de alertas de sistema mostrará uma lista com alertas de
sistema. Estes podem ser alertas relativos ao sistema em que o programa
esta rodando e alertas sem relação direta a contas de membros. Os
seguintes alertas estão disponíveis:
<ul>
	<li>Aplicação iniciada
	<li>Aplicação terminada
	<li>Taxa de conta iniciada
	<li>Taxa de conta cancelada
	<li>Taxa de conta terminada
	<li>Taxa de conta falhada
	<li>Taxa de conta que não rodou recuperada
	<li>Campo de banco de dados ... para os índices contêm nulo
	inesperado (null) para a ... conta
</ul>
O último item só é exibido caso os índices estejam ativados, mais
informações podem ser encontradas <a href="#rateAlert"><u>aqui</u></a>.
Você pode apagar remover cada alerta com o ícone <img border="0"
	src="${images}/delete.gif" width="16" height="16">&nbsp;Apagar
localizado ao lado do alerta, ou selecionando um ou mais alertas e
clicando no botão &quot;Remover selecionados&quot;. Isto removerá os
alertas da lista. Porém exite uma janela de <a href="#alerts_history"><u>Histórico
de alertas</u></a>, onde você pode efetuar uma busca em alertas antigos já
apagados da lista. Deste modo é possível identificar padrões e alertas
recorrentes.
<hr class='help'>

<a name="member_alerts"></a>
<h3>Alertas de membro</h3>
A janela de alertas de membros mostrará uma lista de alertas
relacionados ao comportamento do membro. Os limiares podem ser definidos
na página &quot;Menu: Configurações > <a
	href="${pagePrefix}settings#alerts"><u>Configurações de alertas</u></a>&quot;.
No momento os seguintes alertas estão disponíveis:
<ul>
	<li>Pessoas recebendo um número de <a
		href="${pagePrefix}references"><u> referências</u></a> muito ruins
	<li>Pessoas dando um número de referências muito ruins
	<li>Número de dias que alguém não respondeu a uma fatura
	eletrônica recebida (sistema para membro apenas).
	<li>Número de faturas eletrônicas rejeitadas.
	<li>Máximo de tentativas de acesso com nome se usuário incorreto
	(Alguém tentou várias vezes acesso com um usuário errado).
	<li>Usuários temporariamente bloqueados por exceder o número
	máximo de tentativas de acesso (Alguém tentou diversas vezes acesso com
	um usuário correto mas com a senha errada).
	<li>Novos membros (auto-registro) na página de acesso.
	<li>Expiração de empréstimo que não foi pago.
</ul>
Você pode usar o ícone <img border="0" src="${images}/delete.gif"
	width="16" height="16">&nbsp;<b>Apagar</b> para remover o alerta
da lista. Porém existe uma janela de <a href="#alerts_history"><u>Histórico
de alertas</u></a> onde você pode efetuar uma busca por alertas antigos já
apagados da lista. Deste modo é possível identificar padrões e alertas
recorrentes.
<hr class='help'>

<A NAME="alerts_history"></A>
<h3>Histórico de alertas</h3>
Esta janela permite que você procure alertas antigos que já foram
apagados. Se você quiser ver os alertas recentes, por favor acesse
&quot;Menu: Alertas > Alertas de sistema&quot; e &quot;Menu: Alertas >
Alertas de membros&quot;. Os novos alertas (alertas que não foram
removidos da lista) não serão exibidos no histórico de alertas.
<br><br>Se você não preencher nenhum campo, você obterá uma visão geral
de todos os alertas de conta ou de sistema. Quando você selecionar o
tipo de alerta como &quot;Membro&quot; os alertas mostrarão o nome do
membro na lista de alertas. Também é possível procurar por alertas de um
membro específico. Você pode fazer isso preenchendo os campos de nome de
usuário e nome de membro.
<hr class='help'>

<a name="alerts_history_result"></a>
<h3>Resultado da busca por alertas</h3>
Esta janela mostra todos os alertas antigos conforme os critérios que
você especificou na janela acima. Mais de uma página pode estar
disponível; veja abaixo da janela para acessar outras páginas.<br>
Se você quiser ver os alertas recentes, por favor acesse &quot;Menu:
Alertas > Alertas de sistema&quot; e &quot;Menu: Alertas > Alertas de
membros&quot;. Os novos alertas (alertas que não foram removidos da
lista) não serão exibidos no histórico de alertas.

<hr class="help">

<A NAME="rateAlert"></A>
<h3>Campo de banco de dados ... para os índices contêm "null"
inesperado</h3>
Este alerta pode ser exibido tanto para alertas de sistema quanto de
membros. Isto só é possível caso os <a
	href="${pagePrefix}account_management#rates"><u>índices</u></a> estejam
ativados. O alerta significa que um índice desconhecido (null) foi
encontrado para uma conta específica. <br>
Normalmente se o sistema ja estiver em funcionamento antes da ativação
dos índices, é comum existir contas com índices "null". O sistema
substitui estes valores null assim que eles são encontrados. O alerta é
apenas disparado caso o sistema não consiga substituir este valor null
para esta conta. Isto é uma situação séria, minando o sistema de
índices.


<hr class="help">


<A NAME="error_log"></A>
<h3>Registro de erros</h3>
Esta página mostrará uma lista com todos os erros do sistema. Você pode
abrir ou apagar uma erro diretamente da lista. Quando você apaga um
erro, ele permanecerá disponível na página de <a href="#error_history"><u>Histórico
do registro de erros</u></a>.
<hr class='help'>

<a name="error_history"></a>
<h3>Procurar no histórico do registro de erros</h3>
Esta página permite a você especificar um período de tempo para limitar
o <a href="#error_history_result"><u>Resultado da busca</u></a>. Defina
o período selecionando a data inicial e final, digitando no formato
especificado ou selecionando através do ícone de calendário.
<hr class="help">

<A NAME="error_history_result"></A>
<h3>Resultado da busca no histórico do registro de erros</h3>
Esta página mostrará uma lista com todos os erros do sistema no período
especificado na <a href="#error_history"><u>Procura no histórico
do registro de erros</u></a> janela acima. Se nada é especificado, uma lista
completa será mostrada. Você pode abrir um erro diretamente da lista. Os
resultados são paginados e você pode folhea-los clicando nos números a
direita de &quot;Ir para a página:&quot;. Os erros apenas apareceram
nessa janela se eles tiverem sido apagados da página de <a
	href="#error_log"><u>Registro de erros</u></a> (&quot;Menu : Alertas >
Registro de erros&quot;).
<hr class='help'>

<a name="error_log_details"></a>
<h3>Detalhes do registro de erro</h3>
Esta página mostrará uma lista com os detalhes dos erros do sistema.
Esta informação irá ajudar o administrador do sistema e os
desenvolvedores do Cyclos a ver o que causou o erro.
<br><br><b>Nota:</b> Uma erro de sistema não é necessariamente um bug.
Por causa da flexibilidade de configuração do Cyclos, é possível de se
definir uma configuração com funcionalidades conflitantes. A maior parte
desses erros são tratados diretamente no Cyclos com mensagens
específicas de erro, porém não é possível prever todos os erros de
configurações possíveis.
<hr class="help">

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