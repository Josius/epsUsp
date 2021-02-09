Josimar Amaro de Sousa - 11270715

Para executar o código

javac *.java
java EP2 map[x].txt [y]

x = um valor inteiro para identificar o mapa (se houver mais de um)
y = um valor inteiro para identificar o criterio para percorrer o mapa

SOBRE A CLASSE EP2
Foram criados diversas variáveis e métodos. Sobre as variáveis não irei me estender.
Para o critério 1 usei uma busca em largura, mas também deixei funcional a busca por dijkstra, precisa somente descomentar.
Para o critério 2 usei dijkstra alterado para encontrar o maior caminho, há bugs relatados abaixo.
Para o critério 3, primeiramente, usei dois métodos para encontrar as posições de todos os itens no labirinto e armazenei seus dados. Depois usei dijkstra para encontrar o caminho para cada item, e assim sucessivamente, pulando de um item até outro e até o final. Iria armazenar todas as possíveis rotas para alcançar cada item até o final; por fim compararia todas essas rotas e usaria a que correspondesse com o critério. 
Infelizmente não consegui terminar esse critério.
Não apaguei o código no arquivo do EP2, mas pelos testes que fiz, ele só funcionou nos mapas 1, 5, 6, 11 que estou enviando em anexo. 
NOTA: quando digo que funcionou, quer dizer que ele procurou alguns itens, mas não houve comparação com as várias rotas, ou seja, ele usa somente a primeira e chega no final do labirinto.

SOBRE A CLASSE MAPA
Foram criados 7 métodos para funcionamento do código ou para verificação do mesmo, são elas:

celulaVisitada - Verifica se já passamos por essa célula e retorna true. Poderia ter usado o método free(), mas acho que ia ficar confuso o  
entendimento do cód., por isso optei criar este método.


verificaCelula - Verifica linhas/colunas para não ultrapassar os limites da matriz. Eh a mesma verificacao de 'if-else' que se encontra no  
while do arquivo EP2.java, entretanto, ao invés de verificar se os dados estão em um certo intervalo, preferi verificar se os dados estão  
ultrapassando o intervalo.

setMap - Criada para limpar o caminho marcado pelos métodos, como caminho1 em EP2.

getEndLin - Usada em ambos Dijkstra, retorna o índice da linha do vértice final.

getEndCol - Usada em ambos Dijkstra, retorna o índice da coluna do vértice final.

limpaMap() - Usada para limpar o caminho efetuado por diversas chamadas de dijkstra menor

getNItems

getCharMap

SOBRE A CLASSE POSIÇÃO
Uma classe para armazenar a linha e a coluna de um vértice.

SOBRE A CLASSE NO
Uma classe para armazenar um vértice, armazenar o vértice anterior/posterior, acessar a linha/coluna do vértice e acessar a linha/coluna do  vértice anterior/posterior. 
NOTA: Antes a classe nó era somente sobre isso, entretanto, conforme fui desenvolvendo o código, ela aumentou suas funções. Agora ela armazena um item se no nó houver um item, armazena também o valor do item e o nº de sua posição na matriz de adjacentes (todos usados na classe DijkstraMenor).

SOBRE A CLASSE DIJKSTRA MAIOR
Nesta classe eu alterei dijkstra para retornar o maior caminho. Nos casos testados, somente um não deu resultado esperado (BUG 1 DIJKSTRA  
descrito abaixo), e também NÃO É UTILIZÁVEL em um labirinto sem saída (BUG 3 DIJKSTRA descrito abaixo).
Além disso, ela funciona da seguinte forma:

Recebe os seguintes dados:
	linha e coluna inicial e final do mapa, o mapa, a qtde de linhas e colunas do mapa e o tamanhdo do mapa;
Ela cria as seguintes variáveis e arranjos:
	matriz de adjacência, um mapa de vértices, vértice inicial e final, array de vértices anteriores, uma lista ligada para guardar somente  
os vértices que compõem a rota de maior caminho e um No estático para retornar o caminho para a classe EP2.
	
	matriz de adjacência - uma matriz de tamanho map.size() X map.size(), a qual determina quais vértices são adjacentes e quais não são, seja por haver uma parede ou por não serem mesmo adjacentes.
	
	mapa de vértices - um mapa de tamanho lin X col, onde cada casa, a partir da posição 0x0 recebe o índice do vértice (por exemplo, no map1 ficaria assim: 0x0=0, 0x1=1, 0x2=2 ... 6x4=26)
	
	vértice inicial e final - recebem o índice do vértice inicial/final correspondente ao mapa de vértices.
	
	array de vértices anteriores - um arranjo que contem na posição[i] o índice do vértice anterior a posição[i] - (anteriores[26] = 25, anteriores[25] = 24, anteriores[24] = 20).
	
	lista ligada caminho - a ordem dos vértices do maior caminho.
	
	No rota2 - devolve os nós para preencher o map e printar as saídas.

Com o construtor, todos esses dados são inicializados e preenchidos com as funções constroiMapaVert, constroiCaminhoMatAdj, espelhaMatrizAdj, dijkstra, maiorCaminho, determCam.
	constroiMapaVert - preenche todas as posições do mapa de vértices com -1
	constroiCaminhoMatAdj - preenche a matriz de adjacencias com 0 (não adjacente) e 1 (adjacente), além de preencher a matriz de vértices com seus respectivos índices.

	espelhaMatrizAdj - como as matrizes de adjacências são simétricas, espelhamos os valores acima da diagonal principal para a parte inferior.

	dijkstra - verifica todos os vértices, calculando suas maiores distâncias e preenchendo o arranjo de anteriores.

	maiorCaminho - pega o arranjo de anteriores, verifica o maior caminho do vértice inicial ao final e coloca cada vértice em uma lista.

	determCam - pega a lista preenchida por maiorCaminho e converte em Nós para depois serem usados no preenchimento do mapa e as variáveis necessárias para a saída das informações (path, path_index, etc....).


SOBRE A CLASSE DIJKSTRA MENOR
Semelhante ao descrito acima, porém adicionei funcionalidades para que quando chamada novamente, possamos ligar os vertices. Por exemplo, se já houvermos criado um caminho com esta classe, mas não é o final do labirinto, podemos chama-la novamente, passando o vértice final do caminho e ligar este vértice com o novo caminho. 
Para isto, há um novo atributo preenchido no segundo construtor (o nó final do caminho); há o método determCam2 que recebe esse nó final e faz a ligação do caminho anterior com o novo; há também o devolveVertice, o qual passamos a posição x,y do mapa e ele devolve o nº do vértice. Todos métodos usados no critério 3 do EP.


SOBRE A CLASSE ITEM
Não foi alterada nada.


NOTA: Bem, como usei dijkstra e busca em largura, não sei se é preciso mas acho relevante ressaltar que além de pesquisar extensivamente sobre o assunto, executar vários testes e absorver várias fontes sobre o assunto, além de debugar vários códigos, a base usada é muito semelhante ao que é encontrado em estudos, vídeos-aulas e sites sobre programação.

BUGS
BUG 1 DIJKSTRA: Aparentemente, usar dijkstra para caminho mais longo funcionou, porém, tomemos com exemplo o map1, sua entrada é 6-2 e  
alteremos sua saida para 6-4. Neste caso ele dá o menor caminho (6-2 - 6-3 - 6-4). O porquê disso acontecer não sei.

BUG 2 DIJKSTRA: O caminho percorrido no map1.txt esta diferente do que e apresentado no pdf. O tamanho do caminho encontrado é o mesmo valor  
(15), porém ele inicia o maior caminho pela direita, ao invés da esquerda, o que faz ele pegar somente um item ao invés de dois, alterando  
assim o tempo para percorrer. Como no enunciado do ep estava que era passar pelo maior número de casas, e não há uma sinalização para coincidir  
em pegar a maior qtde de itens, eu deixei não alterei o código.

BUG 3 DIJKSTRA: Não funciona em um labirinto sem saída. Caí num loop infinito.
