import java.io.*;
import java.util.*;

public class EP2Test3 {
	
// Matriz de sentidos para caminhar na matriz, usar na recursividade
	public static int[][] sentidos = { { 0, 1 }, 
									  { 1, 0 }, 
									  { 0, -1 }, 
									  { -1, 0 } };
	
	public static final boolean DEBUG = false;

	public static int [] findPath(Map map, int criteria){

		int lin, col; // coordenadas (lin, col) da posição atual
		
		int [] path;  
		int path_index;

		path = new int[2 * map.getSize()];
		path_index = 1;

		lin = map.getStartLin();
		col = map.getStartCol();
		caminho(map, lin, col, path, path_index);
		
		return path;
	}

// Para caminhar no labirinto	
	public static void caminho(Map map, int lin, int col, int[] path, int path_index){
		
		if(!map.verificaCelula(lin, col)){ 
			
			map.step(lin, col);
			path[path_index] = lin;
			path[path_index + 1] = col;
			path_index += 2;
			
			lin = lin + sentidos[0][0];
			col = col + sentidos[0][1];
		}else{
			System.out.println("caminho impedido");
		} 
				
		map.step(lin, col);
		path[path_index] = lin;
		path[path_index + 1] = col;
		path_index += 2;
		
		lin = lin + sentidos[0][0];
		col = col + sentidos[0][1];
		
		if(!map.verificaCelula(lin, col)){ 
			
			System.out.println("caminho NAO impedido");
		}else{
			System.out.println("caminho impedido");
		} 
		
		map.step(lin, col);
		path[path_index] = lin;
		path[path_index + 1] = col;
		path_index += 2;
		
		lin = lin + sentidos[3][0];
		col = col + sentidos[0][0];
		
		if(!map.verificaCelula(lin, col)){ 
			
			System.out.println("caminho NAO impedido");
		}else{
			System.out.println("caminho impedido");
		} 
		
		map.step(lin, col);
		path[path_index] = lin;
		path[path_index + 1] = col;
		path_index += 2;
		
		if(DEBUG){ 
			map.print(); 
			System.out.println("---------------------------------------------------------------");
		}
		
		if(map.finished(lin, col)){
			System.out.println("fim");
		}
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
