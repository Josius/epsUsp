//Josimar Amaro de Sousa - 11270715

import java.util.*;

public class DijkstraMenor{
	
	private int[][] matAdj;
	private static int[][] mapaVert;
	private int vertInicial;
	private int vertFinal;
	private int[] anteriores;
	private LinkedList<Integer> caminho;
	private static No rota2;
	private static No rotAnt;
				
	public DijkstraMenor(int size, int nLines, int nColumns, int lin, int col, int linFinal, int colFinal, Map map){
		
		this.matAdj = new int[size][size];
		this.mapaVert = new int[nLines][nColumns];
		
		constroiMapaVert(mapaVert); // todas as posicoes da matriz = -1
		constroiCaminhoMatAdj(map, matAdj, mapaVert);	
		espelhaMatrizAdj(matAdj);
		
		this.vertInicial = mapaVert[lin][col];
		this.vertFinal = mapaVert[linFinal][colFinal];
		this.anteriores = new int[matAdj.length];
		
		dijkstra(matAdj, vertInicial, anteriores);			
		caminho = maiorCaminho(vertInicial, vertFinal, anteriores);
		determCam(mapaVert, caminho, map);
	}
	
	public DijkstraMenor(int size, int nLines, int nColumns, int lin, int col, int linFinal, int colFinal, Map map, No rotAnt){
		
		this.matAdj = new int[size][size];
		this.mapaVert = new int[nLines][nColumns];
		
		constroiMapaVert(mapaVert); // todas as posicoes da matriz = -1
		constroiCaminhoMatAdj(map, matAdj, mapaVert);	
		espelhaMatrizAdj(matAdj);
		
		this.vertInicial = mapaVert[lin][col];
		this.vertFinal = mapaVert[linFinal][colFinal];
		this.anteriores = new int[matAdj.length];
		this.rotAnt = rotAnt;
		
		dijkstra(matAdj, vertInicial, anteriores);			
		caminho = maiorCaminho(vertInicial, vertFinal, anteriores);
		determCam2(mapaVert, caminho, map, rotAnt);
	}	
	public void verifica(){
		System.out.println("mapaVert");
		for(int i = 0; i < mapaVert.length; i++){
			for(int j = 0; j < mapaVert[i].length; j++){
				System.out.print(mapaVert[i][j] +" ");
			}
			System.out.println();
		}
		System.out.println("matAdj");
		for(int i = 0; i < matAdj.length; i++){
			for(int j = 0; j < matAdj[i].length; j++){
				System.out.print(matAdj[j][i] +" ");
			}
			System.out.println();
		}
		System.out.println("anteriores");
		for(int i = 0; i < anteriores.length; i++){
			System.out.print(anteriores[i] +" ");
		}
		System.out.println();
		System.out.println("caminho " + caminho);
		System.out.println();
		System.out.println("verifica " + vertInicial + " " + vertFinal + " " + anteriores);
	}
	
	private static void constroiMapaVert(int[][] mapaVert){
		for(int i = 0; i < mapaVert.length; i++){
			for(int j = 0; j < mapaVert[i].length; j++){
				mapaVert[i][j] = -1;
			}
		}
	}
	
	private static void espelhaMatrizAdj(int[][] matAdj){
		for(int i = 0; i < matAdj.length; i++){
			for(int j = 0; j < matAdj[i].length; j++){
				if(matAdj[i][j] != 0) matAdj[j][i] = matAdj[i][j];
			}
		}
	}
	
	private static void constroiCaminhoMatAdj(Map map, int[][] matAdj, int[][] mapaVert){
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
		for(int i = 0; i < matAdj.length; i++){
			for(int j = 0; j < matAdj[i].length; j++){
				matAdj[i][j] = 0;
			}
		}
		
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
	
	private static void addValorItem(Map map, int[][] matAdj, int[][] mapaVert){
		
		for(int i = 0; i < map.nLines(); i++){
			for(int j = 0; j < map.nColumns(); j++){
				
				Item item = map.getItem(i, j);
				if(item != null){
					int posVertItem = mapaVert[i][j];
					/*
					System.out.println(posVertItem);
					System.out.println(item.getValue());
					*/
					for(int k = 0; k < matAdj.length; k++){
						if(matAdj[k][posVertItem] != 0){
							matAdj[k][posVertItem] = item.getValue();
							matAdj[posVertItem][k] = item.getValue();
						}
					}
				}
			}
		}
	}
	
	private static void dijkstra(int[][] matAdj, int vertInicial, int[] anteriores){
		
		int[] dist = new int[matAdj.length];
		boolean[] vertVisitado = new boolean[matAdj.length];
		
		for(int i = 0; i < dist.length; i++){
			dist[i] = Integer.MAX_VALUE;
			vertVisitado[i] = false;
		}
		
		dist[vertInicial] = 0;
		for(int i = 0; i < dist.length; i++){
			int u = distMin(dist, vertVisitado);
			vertVisitado[u] = true;
//			System.out.println("u " + u);
			for(int v = 0; v < dist.length; v++){
				if((vertVisitado[v]==false && matAdj[u][v] != 0) && (dist[u] + matAdj[u][v] < dist[v])){
					dist[v] = dist[u] + matAdj[u][v];
					anteriores[v] = u;
				}
			}
		}
	}
	
	private static int distMin(int[] dist, boolean[] vertVisitado){
		int minDist = Integer.MAX_VALUE;
		int distVert = -1;
		
		for(int i = 0; i < dist.length; i++){
			if(vertVisitado[i]==false && dist[i] < minDist){
				minDist = dist[i];
				distVert = i;
			}
		}
		return distVert;
	}
	
	private static LinkedList<Integer> maiorCaminho(int vertInicial, int vertFinal, int[] anteriores){
		
		int i = vertFinal;
		int temp;
//		int j = 0;
		LinkedList <Integer> tempAnt = new LinkedList<Integer>();
		tempAnt.add(i);
		
		while (anteriores[i] != vertInicial){
			
//			j++;
			temp=anteriores[i];
			tempAnt.add(temp);
			i=temp;
		}
		
		tempAnt.add(vertInicial);
		/*
		for(int k=0; k < tempAnt.size(); k++){
			System.out.println("tempAnt " + tempAnt.get(k)); 
		}*/
		return tempAnt;
	}
	
	public static int devolveVertice(Map map, int lin, int col){
	
		for(int x = 0; x < mapaVert.length; x++){
			for(int y = 0; y < mapaVert[x].length; y++){
				if(x==lin && y==col) return mapaVert[x][y];
			}
		}

		return -1;	
	
	}
	
	private static void determCam(int[][] mapaVert, LinkedList<Integer> caminho, Map map){
		
//		System.out.println("caminho " + caminho);
		No temp = null;
		No temp2 = temp;
		int u;
		Item verifItem;
/*
		for(int i = 0; i < caminho.size(); i++){
			System.out.println("i: " + i + " caminho " + caminho.get(i));
		}
*/		
		while(caminho.size() != 0){
//	Qlqr coisa, se der errado, é só trocar remove por uma função para remover o ultimo da lista, assim criamos uma lista de anteriores correta, pois abaixo esta criando uma lista de posteriores. NOTA: se fizer isso, precisa alterar no EP2 o metodo rota2 para rota para preencher o mapa 
//NOTA2: Nao precisou alterar a chamada do metodo rota2 para rota, estranho...
//			u = caminho.remove();
			u = caminho.removeLast();
			for(int x = 0; x < mapaVert.length; x++){
				for(int y = 0; y < mapaVert[x].length; y++){
					verifItem = map.getItem(x, y);
//					System.out.println("i"+x+"j"+y);
					if(verifItem != null && u == mapaVert[x][y]){
//						System.out.println(x + " " + y + " getValue " + verifItem.getValue());
						temp = new No(new Posicao(x, y), temp2, u, verifItem, verifItem.getValue());
					}else if(u == mapaVert[x][y]){					
						temp = new No(new Posicao(x, y), temp2, u);
					}
				}
			}
			temp2 = temp;
		}
		rota2 = temp;
	}
	private static void determCam2(int[][] mapaVert, LinkedList<Integer> caminho, Map map, No rotAnt){
		
//		System.out.println("caminho " + caminho);
		No temp = rotAnt;
		No temp2 = temp;
		int u;
		Item verifItem;
/*
		for(int i = 0; i < caminho.size(); i++){
			System.out.println("i: " + i + " caminho " + caminho.get(i));
		}
*/		
		while(caminho.size() != 0){
//	Qlqr coisa, se der errado, é só trocar remove por uma função para remover o ultimo da lista, assim criamos uma lista de anteriores correta, pois abaixo esta criando uma lista de posteriores. NOTA: se fizer isso, precisa alterar no EP2 o metodo rota2 para rota para preencher o mapa 
//NOTA2: Nao precisou alterar a chamada do metodo rota2 para rota, estranho...
//			u = caminho.remove();
			u = caminho.removeLast();
			for(int x = 0; x < mapaVert.length; x++){
				for(int y = 0; y < mapaVert[x].length; y++){
					verifItem = map.getItem(x, y);
//					System.out.println("i"+x+"j"+y);
					if(verifItem != null && u == mapaVert[x][y]){
//						System.out.println(x + " " + y + " getValue " + verifItem.getValue());
						temp = new No(new Posicao(x, y), temp2, u, verifItem, verifItem.getValue());
					}else if(u == mapaVert[x][y]){					
						temp = new No(new Posicao(x, y), temp2, u);
					}
				}
			}
			temp2 = temp;
		}
		rota2 = temp;
	}
	
	public static No getRota2(){
		
		return rota2;
	}
}
