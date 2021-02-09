Josimar Amaro de Sousa - 11270715

Para executar o c�digo

javac *.java
java EP2 map[x].txt [y]

x = um valor inteiro para identificar o mapa (se houver mais de um)
y = um valor inteiro para identificar o criterio para percorrer o mapa

SOBRE A CLASSE EP2
Foram criados diversas vari�veis e m�todos. Sobre as vari�veis n�o irei me estender.
Para o crit�rio 1 usei uma busca em largura, mas tamb�m deixei funcional a busca por dijkstra, precisa somente descomentar.
Para o crit�rio 2 usei dijkstra alterado para encontrar o maior caminho, h� bugs relatados abaixo.
Para o crit�rio 3, primeiramente, usei dois m�todos para encontrar as posi��es de todos os itens no labirinto e armazenei seus dados. Depois usei dijkstra para encontrar o caminho para cada item, e assim sucessivamente, pulando de um item at� outro e at� o final. Iria armazenar todas as poss�veis rotas para alcan�ar cada item at� o final; por fim compararia todas essas rotas e usaria a que correspondesse com o crit�rio. 
Infelizmente n�o consegui terminar esse crit�rio.
N�o apaguei o c�digo no arquivo do EP2, mas pelos testes que fiz, ele s� funcionou nos mapas 1, 5, 6, 11 que estou enviando em anexo. 
NOTA: quando digo que funcionou, quer dizer que ele procurou alguns itens, mas n�o houve compara��o com as v�rias rotas, ou seja, ele usa somente a primeira e chega no final do labirinto.

SOBRE A CLASSE MAPA
Foram criados 7 m�todos para funcionamento do c�digo ou para verifica��o do mesmo, s�o elas:

celulaVisitada - Verifica se j� passamos por essa c�lula e retorna true. Poderia ter usado o m�todo free(), mas acho que ia ficar confuso o  
entendimento do c�d., por isso optei criar este m�todo.


verificaCelula - Verifica linhas/colunas para n�o ultrapassar os limites da matriz. Eh a mesma verificacao de 'if-else' que se encontra no  
while do arquivo EP2.java, entretanto, ao inv�s de verificar se os dados est�o em um certo intervalo, preferi verificar se os dados est�o  
ultrapassando o intervalo.

setMap - Criada para limpar o caminho marcado pelos m�todos, como caminho1 em EP2.

getEndLin - Usada em ambos Dijkstra, retorna o �ndice da linha do v�rtice final.

getEndCol - Usada em ambos Dijkstra, retorna o �ndice da coluna do v�rtice final.

limpaMap() - Usada para limpar o caminho efetuado por diversas chamadas de dijkstra menor

getNItems

getCharMap

SOBRE A CLASSE POSI��O
Uma classe para armazenar a linha e a coluna de um v�rtice.

SOBRE A CLASSE NO
Uma classe para armazenar um v�rtice, armazenar o v�rtice anterior/posterior, acessar a linha/coluna do v�rtice e acessar a linha/coluna do  v�rtice anterior/posterior. 
NOTA: Antes a classe n� era somente sobre isso, entretanto, conforme fui desenvolvendo o c�digo, ela aumentou suas fun��es. Agora ela armazena um item se no n� houver um item, armazena tamb�m o valor do item e o n� de sua posi��o na matriz de adjacentes (todos usados na classe DijkstraMenor).

SOBRE A CLASSE DIJKSTRA MAIOR
Nesta classe eu alterei dijkstra para retornar o maior caminho. Nos casos testados, somente um n�o deu resultado esperado (BUG 1 DIJKSTRA  
descrito abaixo), e tamb�m N�O � UTILIZ�VEL em um labirinto sem sa�da (BUG 3 DIJKSTRA descrito abaixo).
Al�m disso, ela funciona da seguinte forma:

Recebe os seguintes dados:
	linha e coluna inicial e final do mapa, o mapa, a qtde de linhas e colunas do mapa e o tamanhdo do mapa;
Ela cria as seguintes vari�veis e arranjos:
	matriz de adjac�ncia, um mapa de v�rtices, v�rtice inicial e final, array de v�rtices anteriores, uma lista ligada para guardar somente  
os v�rtices que comp�em a rota de maior caminho e um No est�tico para retornar o caminho para a classe EP2.
	
	matriz de adjac�ncia - uma matriz de tamanho map.size() X map.size(), a qual determina quais v�rtices s�o adjacentes e quais n�o s�o, seja por haver uma parede ou por n�o serem mesmo adjacentes.
	
	mapa de v�rtices - um mapa de tamanho lin X col, onde cada casa, a partir da posi��o 0x0 recebe o �ndice do v�rtice (por exemplo, no map1 ficaria assim: 0x0=0, 0x1=1, 0x2=2 ... 6x4=26)
	
	v�rtice inicial e final - recebem o �ndice do v�rtice inicial/final correspondente ao mapa de v�rtices.
	
	array de v�rtices anteriores - um arranjo que contem na posi��o[i] o �ndice do v�rtice anterior a posi��o[i] - (anteriores[26] = 25, anteriores[25] = 24, anteriores[24] = 20).
	
	lista ligada caminho - a ordem dos v�rtices do maior caminho.
	
	No rota2 - devolve os n�s para preencher o map e printar as sa�das.

Com o construtor, todos esses dados s�o inicializados e preenchidos com as fun��es constroiMapaVert, constroiCaminhoMatAdj, espelhaMatrizAdj, dijkstra, maiorCaminho, determCam.
	constroiMapaVert - preenche todas as posi��es do mapa de v�rtices com -1
	constroiCaminhoMatAdj - preenche a matriz de adjacencias com 0 (n�o adjacente) e 1 (adjacente), al�m de preencher a matriz de v�rtices com seus respectivos �ndices.

	espelhaMatrizAdj - como as matrizes de adjac�ncias s�o sim�tricas, espelhamos os valores acima da diagonal principal para a parte inferior.

	dijkstra - verifica todos os v�rtices, calculando suas maiores dist�ncias e preenchendo o arranjo de anteriores.

	maiorCaminho - pega o arranjo de anteriores, verifica o maior caminho do v�rtice inicial ao final e coloca cada v�rtice em uma lista.

	determCam - pega a lista preenchida por maiorCaminho e converte em N�s para depois serem usados no preenchimento do mapa e as vari�veis necess�rias para a sa�da das informa��es (path, path_index, etc....).


SOBRE A CLASSE DIJKSTRA MENOR
Semelhante ao descrito acima, por�m adicionei funcionalidades para que quando chamada novamente, possamos ligar os vertices. Por exemplo, se j� houvermos criado um caminho com esta classe, mas n�o � o final do labirinto, podemos chama-la novamente, passando o v�rtice final do caminho e ligar este v�rtice com o novo caminho. 
Para isto, h� um novo atributo preenchido no segundo construtor (o n� final do caminho); h� o m�todo determCam2 que recebe esse n� final e faz a liga��o do caminho anterior com o novo; h� tamb�m o devolveVertice, o qual passamos a posi��o x,y do mapa e ele devolve o n� do v�rtice. Todos m�todos usados no crit�rio 3 do EP.


SOBRE A CLASSE ITEM
N�o foi alterada nada.


NOTA: Bem, como usei dijkstra e busca em largura, n�o sei se � preciso mas acho relevante ressaltar que al�m de pesquisar extensivamente sobre o assunto, executar v�rios testes e absorver v�rias fontes sobre o assunto, al�m de debugar v�rios c�digos, a base usada � muito semelhante ao que � encontrado em estudos, v�deos-aulas e sites sobre programa��o.

BUGS
BUG 1 DIJKSTRA: Aparentemente, usar dijkstra para caminho mais longo funcionou, por�m, tomemos com exemplo o map1, sua entrada � 6-2 e  
alteremos sua saida para 6-4. Neste caso ele d� o menor caminho (6-2 - 6-3 - 6-4). O porqu� disso acontecer n�o sei.

BUG 2 DIJKSTRA: O caminho percorrido no map1.txt esta diferente do que e apresentado no pdf. O tamanho do caminho encontrado � o mesmo valor  
(15), por�m ele inicia o maior caminho pela direita, ao inv�s da esquerda, o que faz ele pegar somente um item ao inv�s de dois, alterando  
assim o tempo para percorrer. Como no enunciado do ep estava que era passar pelo maior n�mero de casas, e n�o h� uma sinaliza��o para coincidir  
em pegar a maior qtde de itens, eu deixei n�o alterei o c�digo.

BUG 3 DIJKSTRA: N�o funciona em um labirinto sem sa�da. Ca� num loop infinito.
