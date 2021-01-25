#include "Aula26bcp.h"
#include <stdio.h>
#include <stdlib.h>
#define true 1
#define false 0

int main(void){
	
	GRAFO * gr = criaGrafo(5);
	
	criaAresta(gr, 0, 1, 2);
	criaAresta(gr, 1, 0, 3);
	criaAresta(gr, 1, 2, 4);
	criaAresta(gr, 2, 0, 12);
	criaAresta(gr, 2, 4, 40);
	criaAresta(gr, 3, 1, 3);
	criaAresta(gr, 4, 3, 8);
	criaAresta(gr, 3, 4, 9); 
	
	profundidade(gr);
	
	imprime(gr);
//	apagaVert(gr, 2);
//	imprime(gr);
	return 0;
}
