//Josimar Amaro de Sousa - 11270715

import java.io.*;
import java.util.*;

public class EP2Test10 {
	
// Matriz de sentidos para caminhar no mapa, usar na recursividade
//	public static int[][] sentidos = {{0, 1},{1, 0},{0,-1},{-1,0}};
	public static int[][] sentidos = {{1, 0},{0,-1},{-1,0},{0, 1}};
//	public static int[][] sentidos = {{0, 1},{1, 0},{0,-1},{-1,0}};
//	public static int[][] sentidos = {{0, 1},{1, 0},{0,-1},{-1,0}};
	
	public static final boolean DEBUG = false;

	public static int [] findPath(Map map, int criteria){

		int lin, col, linFinal, colFinal, sizeMap, nLines, nColumns; 
		
		int [] path;  
		int path_index;

		path = new int[2 * map.getSize()];
		path_index = 1;

		lin = map.getStartLin();
		col = map.getStartCol();
//	Variaveis criadas
		linFinal = map.getEndLin();
		colFinal = map.getEndCol();
		sizeMap = map.getSize();
		nLines = map.nLines();
		nColumns = map.nColumns();

//	Matriz de Adjacencia

		switch(criteria){
			case 1:
				caminho1(map, lin, col, path, path_index);				
				break;
			case 2:								
				Dijkstra dij = new Dijkstra(sizeMap, nLines, nColumns, lin, col, linFinal, colFinal, map);
				No rotaMaior = dij.getRota2();
				rota2(map, rotaMaior, path, path_index);
				break;
			case 3:
				
				caminho3(map, lin, col, path, path_index);
				/*
				DijkstraMV dijk = new DijkstraMV(sizeMap, nLines, nColumns, lin, col, linFinal, colFinal, map);
//				dijk.verifica();
				No rotaValio = dijk.getRota2();
				rota2(map, rotaValio, path, path_index);
				System.out.println("Caminho mais valioso");
				*/
				break;
			case 4:

				if(caminho4(map, lin, col, path, path_index)){
					return path;
				}
				break;
		}
		return path;
	}
	
	private static List<No> rota2(Map map, No atual, int[] path, int path_index) {

        No transitivo = atual;
		LinkedList<No> temp = new LinkedList<No>();     		
		while (transitivo != null) {
			temp.add(transitivo);
            transitivo = transitivo.getPosAnterior();
        }        
//	Colocando as posicoes corretas no path		
		while (temp.size() != 0) {
			transitivo = temp.remove();			
			path[path_index] = transitivo.getPosAtualX();
			path[path_index + 1] = transitivo.getPosAtualY();	
			path_index += 2;
			path[0] = path_index;
			map.step(transitivo.getPosAtualX(), transitivo.getPosAtualY());
        }		
		return temp;
    }
	
	private static List<No> rota(Map map, No atual, int[] path, int path_index) {
		
        No transitivo = atual;
		LinkedList<No> temp = new LinkedList<No>();
//	Como a entrada do caminho eh inversa, do final para o comeco, armazenamos em um arraylist para depois retirar na ordem correta	
		while (transitivo != null) {
			temp.add(transitivo);
            transitivo = transitivo.getPosAnterior();
        }
        
// Limpando o mapa das posicoes marcadas pelo metodo caminho1     
        for(int i = 0; i < map.nLines(); i++){
        	for(int j = 0; j < map.nColumns(); j++){
        		if(map.celulaVisitada(i,j) == true)
        			map.setMap(i, j);
        	}
        }
        
//	Colocando as posicoes corretas no path		
		while (temp.size() != 0) {
			transitivo = temp.removeLast();			
			path[path_index] = transitivo.getPosAtualX();
			path[path_index + 1] = transitivo.getPosAtualY();	
			path_index += 2;
			path[0] = path_index;
			map.step(transitivo.getPosAtualX(), transitivo.getPosAtualY());
        }
		
		return temp;
    }
    
	public static List<No> caminho1(Map map, int lin, int col, int[] path, int path_index){
		
		LinkedList<No> fila = new LinkedList<No>();
		No inicio = new No(new Posicao(lin, col));
		fila.add(inicio);
				
		while (fila.size() != 0) {
                   
			No atual = fila.remove(0);            			
			
			if(map.finished(atual.getPosAtualX(), atual.getPosAtualY())){
				return rota(map, atual, path, path_index);
			}
			
//	USAR IF ABAIXO PARA CAMINHO MAIS CURTO COM MENOS ITERACOES DO FOR			
			if((map.celulaVisitada(atual.getPosAtualX(), atual.getPosAtualY())) || map.blocked(atual.getPosAtualX(), atual.getPosAtualY())){
				continue;
			}
						
            for(int i = 0; i < sentidos.length; i++){
//	USAR IF ABAIXO PARA CAMINHO MAIS CURTO COM MENOS ITERACOES DO FOR	
				if((map.verificaCelula(atual.getPosAtualX() + sentidos[i][0], atual.getPosAtualY() + sentidos[i][1])) || (map.celulaVisitada(atual.getPosAtualX() + sentidos[i][0], atual.getPosAtualY() + sentidos[i][1])) || map.blocked(atual.getPosAtualX() + sentidos[i][0], atual.getPosAtualY() + sentidos[i][1])){
					continue;
				} 
				
				No sentido = new No(new Posicao(atual.getPosAtualX() + sentidos[i][0], atual.getPosAtualY() + sentidos[i][1]), atual);

				fila.add(sentido);
			}
        }		
		if(DEBUG){ 
			map.print(); 
			System.out.println("---------------------------------------------------------------");
		}
		return fila;
	}
	
	public static boolean caminho3(Map map, int lin, int col, int[] path, int path_index){
		
		int cont = 0;
		No[] noSaida = new No[4];// usado para guardar o ultimo vertice/no do caminho encontrado
		int vlrTtlItens = 0;// usar para comparacao com o valor do ultimo vertic/no encontrado
		Item verifItem;
		int atualX, atualY, dirHorizon, dirVert;
		for(int i = 0; i < map.nLines(); i++){
			for(int j = 0; j < map.nColumns(); j++){
				verifItem = map.getItem(i, j);
				if(verifItem != null) vlrTtlItens += verifItem.getValue();	
			}
		}
		
		LinkedList<No> filaItens = new LinkedList<No>();
		No noIniItem;
		verifItem = map.getItem(lin, col);
		
		if(verifItem != null){
			noIniItem = new No(new Posicao(lin, col), null, verifItem, verifItem.getValue()); //	criar construtor sem o campo posAnterior e com campo item
		}else noIniItem = new No(new Posicao(lin, col));

		filaItens.add(noIniItem);
				
		while (filaItens.size() != 0) {
                   
			No atual = filaItens.remove(0);            			
			atualX = atual.getPosAtualX();
			atualY = atual.getPosAtualY();
			
			if(map.finished(atualX, atualY)){
				
				if(atual.getValorItem() != vlrTtlItens){
					
					if(cont<4){
						noSaida[cont] = atual;
						cont++;
						return false;
					}else return true; // NAO ESQUECER DE FAZER COMPARACAO DOS 4 VLRS DE atual.getValorItem NO FINAL DESTA FUNCAO, APOS SAIR DO WHILE
				}else{
					noSaida[cont] = atual;
					return true;
				}	
			}
			
//	USAR IF ABAIXO PARA CAMINHO MAIS CURTO COM MENOS ITERACOES DO FOR			
			if(map.celulaVisitada(atualX, atualY) || map.blocked(atualX, atualY)){
				continue;
			}
						
            for(int i = 0; i < sentidos.length; i++){
            	dirHorizon = sentidos[i][0];
				dirVert = sentidos[i][1];
//	USAR IF ABAIXO PARA CAMINHO MAIS CURTO COM MENOS ITERACOES DO FOR	
				if(map.verificaCelula(atualX + dirHorizon, atualY + dirVert) || map.celulaVisitada(atualX + dirHorizon, atualY + dirVert) || map.blocked(atualX + dirHorizon, atualY + dirVert)){
					continue;
				} 
				
				Item atualItem = map.getItem(atualX + dirHorizon, atualY + dirVert);
				
				No prox;
				
				if(atualItem != null){
					prox = new No(new Posicao(atualX + dirHorizon, atualY + dirVert), atual, atualItem, atualItem.getValue());
				}else prox = new No(new Posicao(atualX + dirHorizon, atualY + dirVert), atual, atualItem, 0);
				
				filaItens.add(prox);
			}
			
			System.out.println(noSaida[0]);
		    System.out.println(noSaida[1]);
		    System.out.println(noSaida[2]);
		    System.out.println(noSaida[3]);
        }		
        
        
		
		return true;
	}
	
/*	
	public static boolean caminho3(Map map, int lin, int col, int[] path, int path_index){
				
		int contValor = -1;
		No itemIni;
		LinkedList<No> noItem = new LinkedList<No>();
		Item item = map.getItem(lin, col);
		
//System.out.println("item out-while: " + item);
		
		if(item != null){
			itemIni = new No(new Posicao(lin, col), null, item, item.getValue()); //	criar construtor sem o campo posAnterior e com campo item
			contValor += item.getValue();
		}else itemIni = new No(new Posicao(lin, col));
					
		noItem.add(itemIni);
		

//		while(!map.finished(lin, col)){
		while(noItem.size() != 0){ //	talvez precuse retirar nos.size != 0

			No atual = noItem.remove(0);
						
			if(map.finished(atual.getPosAtualX(), atual.getPosAtualY())){
//	Fazer uma nova verificacao sobre contValor (que e atualizado a cada item adquirido) e o valorFinal dos itens, se valorFinal >= contValor, finaliza, caso contrário, continua (retorn false)

				if(atual.valorItem > contValor){
					rota(map, atual, path, path_index);
					return true;
				}
				else return false;
//				return rota(map, atual, path, path_index);
			}
			
			for(int i = 0; i < sentidos.length; i++){
				if((map.verificaCelula(atual.getPosAtualX() + sentidos[i][0], atual.getPosAtualY() + sentidos[i][1])) || (map.celulaVisitada(atual.getPosAtualX() + sentidos[i][0], atual.getPosAtualY() + sentidos[i][1])) || map.blocked(atual.getPosAtualX() + sentidos[i][0], atual.getPosAtualY() + sentidos[i][1])){
System.out.println("Nao valido - i: " + i);
					continue;
				}
				int dirHorizon = atual.getPosAtualX() + sentidos[i][0];
				int dirVert = atual.getPosAtualY() + sentidos[i][1];

System.out.println("dirHorizon: " + dirHorizon + " dirVert: " + dirVert);

				Item itemAtual = map.getItem(dirHorizon, dirVert);
System.out.println("itemAtual " + itemAtual);
//System.out.println("itemAtual.getValue " + itemAtual.getValue()); resulta em NullPointerException por causa que o valor de um item não inicializado não aponta para nenhum espaço de memória


				No itemNovo;
				
				if(itemAtual != null){
					itemNovo = new No(new Posicao(dirHorizon, dirVert), atual, itemAtual, itemAtual.getValue());
					contValor += itemAtual.getValue();
				}else itemNovo = new No(new Posicao(dirHorizon, dirVert), atual, itemAtual, atual.valorItem);
				
				noItem.add(itemNovo);
				
			}
		}
		return true;
//		return noItem;
	}
*/
	
// Para caminhar no labirinto	
	public static boolean caminho4(Map map, int lin, int col, int[] path, int path_index){
		
		if(map.verificaCelula(lin, col) || map.blocked(lin, col) || map.celulaVisitada(lin, col)){
//			System.out.println("saiu da recursao");
			return false;
		}
				
		map.step(lin, col);
		path[path_index] = lin;
		path[path_index + 1] = col;
		path_index += 2;
		path[0] = path_index;
		
		if(map.finished(lin, col)){
			
			return true;
		}
//	public static int[][] sentidos = {{1, 0},{0,-1},{-1,0},{0, 1}};		
		for(int i = 0; i < sentidos.length; i++){			
			int direcaoHorizontal = lin + sentidos[i][0];
			int direcaoVertical = col + sentidos[i][1];
			/*
//			System.out.println("i: " + i);
//			System.out.println("dirHor " + direcaoHorizontal + " dirVer " + direcaoVertical);
//			System.out.println("	senX " + sentidos[i][0] + " senY " + sentidos[i][1]);
			*/
			if(caminho4(map, direcaoHorizontal, direcaoVertical, path, path_index)){
				return true;
			}
		}
		
		if(DEBUG){ 
			map.print(); 
			System.out.println("---------------------------------------------------------------");
		}
		return false;
	}
	
	public static void printSolution(Map map, int [] path){

		// A partir do mapa e do path contendo a solução, imprime a saída conforme especificações do EP.

		int totalItems = 0;
		int totalValue = 0;
		int totalWeight = 0;

		int path_size = path[0];
		double tempo = 0.0;
		
		
		for(int i = 1; i < path_size; i += 2){
			int lin = path[i];
			int col = path[i + 1];
			Item item = map.getItem(lin, col);
			
			if(i>1){
				
				tempo += Math.pow((1.0 + totalWeight/10.0), 2);	
			}
			if(item != null){

				totalItems++;
				totalValue += item.getValue();
				totalWeight += item.getWeight();
			}
			
		}

//Alterei o println abaixo para printf, para que a saída do tempo percorrido tenha somente duas casas decimais
		System.out.printf("%d %.2f %n", (path_size - 1)/2, tempo);

		for(int i = 1; i < path_size; i += 2){

			int lin = path[i];
			int col = path[i + 1];

			System.out.println(lin + " " + col);
		}

		// Estamos ignorando os itens que são coletados no caminho. Isso precisa ser modificado para a versão final.
//		System.out.println("0 0 0");
		System.out.println(totalItems + " " + totalValue + " " + totalWeight);
		
		for(int i = 1; i < path_size; i+=2){

			int lin = path[i];
			int col = path[i + 1];
			Item item = map.getItem(lin, col);

						
			if(item != null){

				System.out.println(item.getLin() + " " + item.getCol());
			}
		}
		map.print();
	}

	public static void main(String [] args) throws IOException {
		
		Map map = new Map(args[0]);

		if(DEBUG){ 
			map.print(); 
			System.out.println("---------------------------------------------------------------");
		}

		int criteria = Integer.parseInt(args[1]);
		int [] path = findPath(map, criteria);
//		printSolution(map, path);		
	}
}