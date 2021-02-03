import java.io.*;
import java.util.*;

public class EP2Test9 {
	
// Matriz de sentidos para caminhar no mapa, usar na recursividade
	public static int[][] sentidos = {{0, 1},{1, 0},{0,-1},{-1,0}};
	
	public static final boolean DEBUG = false;

	public static int [] findPath(Map map, int criteria){

		int lin, col, linFinal, colFinal; 
		
		int [] path;  
		int path_index;

		path = new int[2 * map.getSize()];
		path_index = 1;

		lin = map.getStartLin();
		col = map.getStartCol();
		linFinal = map.getEndLin();
		colFinal = map.getEndCol();
//	Matriz de Adjacencia
		int[][] matAdj = new int[map.getSize()][map.getSize()];
		int[][] mapaVert = new int[map.nLines()][map.nColumns()];
		
		constroiMapaVert(mapaVert); // todas as posicoes da matriz = -1
		constroiCaminhoMatAdj(map, matAdj, mapaVert);	
		espelhaMatrizAdj(matAdj); // iguala os valores superiores da diagonal com os inferiores

//	Printando a matriz		
/*
		for(int i = 0; i < matAdj.length; i++){
			for(int j = 0; j < matAdj[i].length; j++){
				System.out.print(matAdj[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
		for(int i = 0; i < mapaVert.length; i++){
			for(int j = 0; j < mapaVert[i].length; j++){
				System.out.print(mapaVert[i][j] + " ");
			}
			System.out.println();
		}
*/		
		switch(criteria){
			case 1:
				caminho1(map, lin, col, path, path_index);				
				break;
			case 2:				
				int vertInicial = mapaVert[lin][col];
				int vertFinal = mapaVert[linFinal][colFinal];
				int[] anteriores = new int[matAdj.length];
				
				dijkstra(matAdj, vertInicial, anteriores);			
				LinkedList<Integer> caminho = maiorCaminho(vertInicial, vertFinal, anteriores);
				determCam(mapaVert, caminho, map, path, path_index);
				/*
				for(int i = 0; i < anteriores.length; i++){
					System.out.println("pos: " + i +" ant: " + anteriores[i]);
				}
				*/
				break;
			case 3:
				System.out.println("Caminho mais valioso");
				break;
			case 4:
				if(caminho4(map, lin, col, path, path_index)){
					return path;
				}
				break;
		}
		return path;
	}
	
	public static void constroiMapaVert(int[][] mapaVert){
		for(int i = 0; i < mapaVert.length; i++){
			for(int j = 0; j < mapaVert[i].length; j++){
				mapaVert[i][j] = -1;
			}
		}
	}
	
	public static void espelhaMatrizAdj(int[][] matAdj){
		for(int i = 0; i < matAdj.length; i++){
			for(int j = 0; j < matAdj[i].length; j++){
				if(matAdj[i][j] == 1) matAdj[j][i] = 1;
			}
		}
	}
	
	public static void constroiCaminhoMatAdj(Map map, int[][] matAdj, int[][] mapaVert){
		int posVert = 0;
		int numVert = 0;
		int vert = 0;
						
		for(int i = 0; i < map.nLines(); i++){
			for(int j = 0; j < map.nColumns(); j++){
				if(map.free(i, j)){
					mapaVert[i][j] = vert;
					vert++;
				}
			}
		}
		
		for(int i = 0; i < map.nColumns(); i++){
			if(map.free(0, i))	numVert++;
		}
//	Verificar se precisa inicializar a matAdj com todas as posicoes igual a zero ou se por padrao ela ja vem com zero		
/*
		for(int i = 0; i < matAdj.length; i++){
			for(int j = 0; j < matAdj[i].length; j++){
				matAdj[i][j] = 0;
			}
		}
*/		
		for(int i = 0; i < map.nLines(); i++){
			for(int j = 0; j < map.nColumns(); j++){

				if(map.free(i, j)){
					if(map.verificaCelula(i, j + 1) == false && map.blocked(i, j + 1)==false){	
						matAdj[posVert][posVert+1] = 1;
						posVert++;
					}
					else posVert++;
				}
			}
		}

		posVert = 0;
		for(int i = 0; i < map.nLines(); i++){
			for(int j = 0; j < map.nColumns(); j++){

				if(map.free(i, j)){
					if(map.verificaCelula(i + 1, j) == false && map.blocked(i + 1, j)==false){	
						matAdj[posVert][numVert+posVert] = 1;					
						posVert++;
					}
					else{
						posVert++;
						numVert--;
					}	
				}else{
					if(map.verificaCelula(i + 1, j) == false && map.blocked(i + 1, j)==false){	
						numVert++;
					}
				}
			}
		}
	}	
	
	public static void dijkstra(int[][] matAdj, int vertInicial, int[] anteriores){
		
		int[] dist = new int[matAdj.length];
		boolean[] vertVisitado = new boolean[matAdj.length];
		
		for(int i = 0; i < dist.length; i++){
			dist[i] = Integer.MIN_VALUE;
			vertVisitado[i] = false;
		}
		
		dist[vertInicial] = 0;
		for(int i = 0; i < dist.length; i++){
			int u = distMax(dist, vertVisitado);
			vertVisitado[u] = true;
//			System.out.println("u " + u);
			for(int v = 0; v < dist.length; v++){
				if((vertVisitado[v]==false && matAdj[u][v] != 0) && (dist[u] + matAdj[u][v] >= dist[v])){
					dist[v] = dist[u] + matAdj[u][v];
					anteriores[v] = u;
				}
			}
		}
		/*
		for (int i = 0; i < dist.length; i++) {
      		System.out.println(String.format("Distance from %s to %s is %s", vertInicial, i, dist[i]));
    	}
    	*/				
	}
	
	public static int distMax(int[] dist, boolean[] vertVisitado){
		int maxDist = Integer.MIN_VALUE;
		int distVert = Integer.MAX_VALUE;
		
		for(int i = 0; i < dist.length; i++){
			if(vertVisitado[i]==false && dist[i] >= maxDist){
				maxDist = dist[i];
				distVert = i;
			}
		}
		return distVert;
	}
	
	public static LinkedList<Integer> maiorCaminho(int vertInicial, int vertFinal, int[] anteriores){
		
		int i = vertFinal;
		int temp;
		int j = 0;
		LinkedList <Integer> tempAnt = new LinkedList<Integer>();
		tempAnt.add(i);
		while (anteriores[i] != vertInicial){
			j++;
			temp=anteriores[i];
			tempAnt.add(temp);
			i=temp;
		}
		tempAnt.add(vertInicial);
		return tempAnt;
	}
	
	public static void determCam(int[][] mapaVert, LinkedList<Integer> caminho, Map map, int[] path, int path_index){
		
		No temp = null;
		No temp2 = temp;
		while(caminho.size() != 0){
			
			int u = caminho.remove();
			for(int x = 0; x < mapaVert.length; x++){
				for(int y = 0; y < mapaVert[x].length; y++){
					if(u == mapaVert[x][y]){					
						temp = new No(new Posicao(x, y), temp2);;
					}
				}
			}
			temp2 = temp;
		}
		rota2(map, temp, path, path_index);
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
	
// Para caminhar no labirinto	
	public static boolean caminho4(Map map, int lin, int col, int[] path, int path_index){
		
		if(map.verificaCelula(lin, col) || map.blocked(lin, col) || map.celulaVisitada(lin, col)){
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
		
		for(int i = 0; i < sentidos.length; i++){			
			int direcaoHorizontal = lin + sentidos[i][0];
			int direcaoVertical = col + sentidos[i][1];
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
//		System.out.println((path_size - 1)/2 + " " + tempo);
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
		/*
		int[][] mapInt = new int[map.nLines()][map.nColumns()];
		for(int i=0; i< map.nLines(); i++){
			for(int j=0; j < map.nColumns(); j++){
				if(map.free(i,j)) mapInt[i][j] = 1;
				else mapInt[i][j] = 0;
//				System.out.print(map.free(i, j) + " ");
			}
		}
		
		for(int i=0; i< map.nLines(); i++){
			for(int j=0; j < map.nColumns(); j++){

				System.out.print(mapInt[i][j] + " ");
			}
			System.out.println();
		}
*/
		int criteria = Integer.parseInt(args[1]);
		int [] path = findPath(map, criteria);
		printSolution(map, path);
//		System.out.println(map.verificaMapa());
			
/*
		System.out.println(map.getSize());
		System.out.println(map.getCharMap(6,2));
		System.out.println(map.getCharMap(5,1));

		//impressão da matriz dos sentidos
		
		for(int i = 0; i < sentidos.length; i++){
			System.out.print(sentidos[i][0] + " " + sentidos[i][1] + " ");
			System.out.println();
		}
		*/
	
		
		
	}
}

//OBS DIJKSTRA: Aparentemente, usar dijkstra para caminho mais longo funcionou, porem em uma entrada 6 2 e saida 6 4, ele dá o menor caminho (6 2 - 6 3 - 6 4). O porque nao sei.
