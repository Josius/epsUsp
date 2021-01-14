import java.io.*;
import java.util.*;

public class EP2Test2 {
	//public static int[][] DIRECTIONS = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };
	
	public static final boolean DEBUG = false;

	public static int [] findPath(Map map, int criteria){

		int lin, col; // coordenadas (lin, col) da posição atual
		
		int [] path;  
		int path_index;

		path = new int[2 * map.getSize()];
		path_index = 1;

		lin = map.getStartLin();
		col = map.getStartCol();
		
		// efetivação de um passo
		map.step(lin, col);		// marcamos no mapa que a posição está sendo ocupada.
		path[path_index] = lin;		// adicionamos as coordenadas da posição (lin, col) no path 
		path[path_index + 1] = col;
		path_index += 2;

		if(DEBUG){

			map.print(); 
			System.out.println("---------------------------------------------------------------");
		}

//CASO BASE - PROVAVELMENTE Ha MAIS DE UM CASO BASE, COMO SE NÃO HOUVER SAIDA OU SE HOUVER SOMENTE PAREDES NO MAPA		
		while(!map.finished(lin, col)){

			if(lin - 1 >= 0 && map.free(lin - 1, col)){			// cima
				
				//System.out.println("UP");	
				lin = lin - 1;
			}
			else if(col + 1 < map.nColumns() && map.free(lin, col + 1)){	// direita

				//System.out.println("RIGHT");	
				col = col + 1;
			}
			else if(lin + 1 < map.nLines() && map.free(lin + 1, col)){	// baixo

				//System.out.println("DOWN");	
				lin = lin + 1;
			}
			else if(col - 1 >= 0 && map.free(lin, col - 1)){		// esquerda

				//System.out.println("LEFT");	
				col = col - 1;
			}
			else{
				//System.out.println("BREAK!");	
				break; // não existe passo a ser dado a partir da posição atual... 
			}

			map.step(lin, col);
			path[path_index] = lin;
			path[path_index + 1] = col;
			path_index += 2;

			if(DEBUG){ 
				map.print(); 
				System.out.println("---------------------------------------------------------------");
			}
		}
		
		path[0] = path_index;
		return path;
	}

	public static void printSolution(Map map, int [] path){

		// A partir do mapa e do path contendo a solução, imprime a saída conforme especificações do EP.

		int totalItems = 0;
		int totalValue = 0;
		int totalWeight = 0;

		int path_size = path[0];

		System.out.println((path_size - 1)/2 + " " + 0.0);

		for(int i = 1; i < path_size; i += 2){

			int lin = path[i];
			int col = path[i + 1];
			Item item = map.getItem(lin, col);

			System.out.println(lin + " " + col);

			if(item != null){

				totalItems++;
				totalValue += item.getValue();
				totalWeight += item.getWeight();
			}
		}

		// Estamos ignorando os itens que são coletados no caminho. Isso precisa ser modificado para a versão final.
		System.out.println("0 0 0");
		map.print();
	}

	public static void main(String [] args) throws IOException {
		/*
		for(int i = 0; i < DIRECTIONS.length; i++){
			for(int j = 0; j < DIRECTIONS[i].length; j++){
				System.out.print(" i: " + i + " j: " + j + " dir: " + DIRECTIONS[i][j]);
			}	
			System.out.println();
		}
		*/
		/*
		não funciona
		for(int i = 0; i < DIRECTIONS.length; i++){
			for(int j = 0; j < DIRECTIONS[i].length; j++){
				int[] direction = DIRECTIONS[i][j];
				System.out.print(direction);
			}	
			System.out.println();
		}
		
		
		int[][] DIRECTIONS = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };
		
		for (int[] direction : DIRECTIONS) {
			System.out.print(direction);
		}
		
		System.out.println(DIRECTIONS[0][0]);
		System.out.println(DIRECTIONS[0][1]);
		System.out.println(DIRECTIONS[1][0]);
		System.out.println(DIRECTIONS[1][1]);
		System.out.println(DIRECTIONS[2][0]);
		System.out.println(DIRECTIONS[2][1]);
		System.out.println(DIRECTIONS[3][0]);
		System.out.println(DIRECTIONS[3][1]);
		*/
		Map map = new Map(args[0]);

		if(DEBUG){ 
			map.print(); 
			System.out.println("---------------------------------------------------------------");
		}

		int criteria = Integer.parseInt(args[1]);
		int [] path = findPath(map, criteria);
		printSolution(map, path);
	}
}
