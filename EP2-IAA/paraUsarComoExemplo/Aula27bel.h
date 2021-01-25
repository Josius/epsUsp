#include <stdio.h>
#include <stdlib.h>
#define true 1
#define false 0

typedef int bool;
typedef int TIPOPESO;
typedef int TIPOCHAVE;

typedef struct adjacencia{
	int vertice;
	TIPOPESO peso;
	struct adjacencia *prox;
}ADJACENCIA;

typedef struct vertice{
	/*Outros dados vão aqui*/
	ADJACENCIA *cabeca;
}VERTICE;

typedef struct grafo{
	int vertices;
	int arestas;
	VERTICE *adj;
}GRAFO;

//FILA
typedef struct {
  TIPOCHAVE chave;
  // outros campos...
} REGISTRO;

typedef struct aux {
  REGISTRO reg;
  struct aux* prox;
} ELEMENTO, *PONT;;

typedef struct {
  PONT inicio;
  PONT fim;
} FILA;

void inicializarFila(FILA* f){
  f->inicio = NULL;
  f->fim = NULL;
}

bool inserirNaFila(FILA* f,REGISTRO reg) {
  PONT novo = (PONT) malloc(sizeof(ELEMENTO));
  novo->reg = reg;
  novo->prox = NULL;
  if (f->inicio==NULL){
     f->inicio = novo;
  }else{
     f->fim->prox = novo;
  }
  f->fim = novo;
  return true;
}

bool excluirDaFila(FILA* f, REGISTRO* reg) {
  if (f->inicio==NULL){
    return false;                     
  }
  *reg = f->inicio->reg;
  PONT apagar = f->inicio;
  f->inicio = f->inicio->prox;
  free(apagar);
  if (f->inicio == NULL){
    f->fim = NULL;
  }
  return true;
}


void exibirFila(FILA* f){
  PONT end = f->inicio;
  printf("Fila: \" ");
  while (end != NULL){
    printf("%d ", end->reg.chave); // soh lembrando TIPOCHAVE = int
    end = end->prox;
  }
  printf("\"\n");
} 

PONT buscaSeq(FILA* f,TIPOCHAVE ch){
  PONT pos = f->inicio;
  while (pos != NULL){
    if (pos->reg.chave == ch) printf("ch: %d\n", pos->reg.chave);//return pos;
    pos = pos->prox;
  }
  return NULL;
}

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


//Função auxiliar de busca em largura
void visitaL(GRAFO *g, int s, bool *explorado){
	FILA f;
	inicializarFila(&f);
	explorado[s] = true;
	REGISTRO *u = (REGISTRO *) malloc(sizeof(REGISTRO));
	u->chave = s;
	inserirNaFila(&f, *u);
	
	exibirFila(&f);
	buscaSeq(&f, u->chave);
	printf("u: %d\n", u->chave);
	printf("teste1\n\n");
	
	while(f.inicio != NULL){
		excluirDaFila(&f, u);
		ADJACENCIA *v = g->adj[u->chave].cabeca;
		
		exibirFila(&f);
		buscaSeq(&f, u->chave);
		printf("u: %d\n", u->chave);
		printf("teste2\n\n");
		
		while(v != NULL){
			if(explorado[v->vertice] == false){ //cod orig = !explorado[v->vertice]
				explorado[v->vertice] = true;
				u->chave = v->vertice;
				inserirNaFila(&f, *u);
				
				exibirFila(&f);
				buscaSeq(&f, u->chave);
				printf("u: %d\n", u->chave);
				printf("teste3\n");
			}
			v = v->prox;
			
			exibirFila(&f);
			buscaSeq(&f, u->chave);
			printf("u: %d\n", u->chave);
			printf("teste4\n");
		}
		exibirFila(&f);
		buscaSeq(&f, u->chave);
		printf("u: %d\n", u->chave);
		printf("teste5\n");
	}
	exibirFila(&f);
	buscaSeq(&f, u->chave);
	printf("u: %d\n", u->chave);
	printf("teste6\n");
	free(u);
}
//Busca em Largura
void largura(GRAFO *g){
	bool explorado[g->vertices];
	int u;
	for(u=0; u < g->vertices; u++)
		explorado[u] = false; //deixando todos como 'false'
	for(u=0; u< g->vertices; u++)
		if(explorado[u]== false) //[cod orig (!explorado[u])] - verificando se todos são 'false', ou seja, se explorado[u] for false, execute o código abaixo
			visitaL(g, u, explorado);
}
