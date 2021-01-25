#include <stdio.h>
#include <stdlib.h>
#define true 1
#define false 0

#define BRANCO 0
#define AMARELO 1
#define VERMELHO 2

typedef int bool;
typedef int TIPOPESO;

typedef struct adjacencia{
	int vertice;
	TIPOPESO peso;
	struct adjacencia *prox;
}ADJACENCIA;

typedef struct vertice{
	//outros dados vao aqui, por exemplo, chave de busca
	ADJACENCIA *cabeca;
}VERTICE;

typedef struct grafo{
	int vertices;
	int arestas;
	VERTICE *adj;
}GRAFO;

GRAFO *criaGrafo(int v){
	
	GRAFO *g = (GRAFO *) malloc(sizeof(GRAFO));
	g->vertices = v;
	g->arestas = 0;
	
	g->adj = (VERTICE *) malloc(v*sizeof(VERTICE)); //Alocando o arranjo com o nº de vértices desejado
	int i;
	for(i=0; i<v; i++)
		g->adj[i].cabeca = NULL; //precisa colocar null na lista
	return(g);
}

//ADICIONANDO ARESTAS 
ADJACENCIA *criaAdj(int v, int peso){ //'v' é o nó final da aresta
	
	ADJACENCIA *temp = (ADJACENCIA *) malloc(sizeof(ADJACENCIA)); //alocando espaco para um nó na lista de adjacências
	temp->vertice = v;
	temp->peso = peso;
	temp->prox = NULL;
	return(temp);
}
//CRIANDO UMA ARESTA DIRIGIDA
bool criaAresta(GRAFO *gr, int vi, int vf, TIPOPESO p){
	
	if(!gr) return false;
	if((vf < 0) || (vf >= gr->vertices)) return false;
	if((vi < 0) || (vi >= gr->vertices)) return false;
	
	ADJACENCIA *novo = criaAdj(vf, p);
	novo->prox = gr->adj[vi].cabeca; //inserindo no início da lista de adjacências do vértice inicial
	gr->adj[vi].cabeca = novo;
	gr->arestas++;
	
	return true;
}
//PARA CRIAR UM GRAFO NAO DIRIGIDO, CHAMAMOS O PROCEDIMENTO 2X ((vi, vf) e (vf, vi))

//IMPRIMINDO GRAFO
void imprime(GRAFO *gr){
	printf("Vertices: %d. Arestas: %d.\n", gr->vertices, gr->arestas); //PRECISA DE UM TESTE DE PARAMETROS, PELO CASO DE ESTAREM NULL
	int i;
	for(i=0; i < gr->vertices; i++){
		printf("v%d:", i);
		ADJACENCIA *ad = gr->adj[i].cabeca;
		while(ad){
			printf("v%d(%d) ", ad->vertice, ad->peso);
			//if(!ad->vertice) printf("v%d: VAZIO", i); //NAO FUNCIONA
			ad = ad->prox;
		}
		printf("\n");
	}	
	
}

void visitaP(GRAFO *g, int u, int *cor){ //recebe o grafo, no inicial, e o arranjo de cor
	cor[u] = AMARELO; //no inicial agora eh amarelo
	ADJACENCIA *v = g->adj[u].cabeca; //comecamos a visitar a adjacencia a esse no inicial...
	//na linha acima, podemos usar para procurar pela chave
	while(v){ //...colocando a cabeca(o pai) da lista em v...
		if(cor[v->vertice] == BRANCO) //...se a cor dela for branco...
			visitaP(g, v->vertice, cor);//...visitamos a outra recursivamente(os filhos do pai), ate visitarmos a ultima(o ultimo filho)...
		v = v->prox; //...ao termino das visitas dos filhos, vamos para a proxima adjacencia do pai recursivamente
	}
	cor[u] = VERMELHO; //visitada toda a adjacencia de u, o finalizamos, marcando de vermelho 
}

//CAMINHADA NO GRAFO - BUSCA EM PROFUNDIDADE
void profundidade(GRAFO *g){
	int cor[g->vertices];
	
	int u;
	for(u=0; u < g->vertices; u++){
		cor[u] = BRANCO;
	}
	for(u=0; u < g->vertices; u++){
		if(cor[u] == BRANCO)
			visitaP(g, u, cor);
	}
}


