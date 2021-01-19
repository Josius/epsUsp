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
			
			if(map.finished(lin, col)){
				System.out.println("fim");
			}
				
			for(int[] sentido : sentidos){
				
				caminho(map, lin+sentido[0], col+sentido[1], path, path_index);
			}
		}
			
		
		if(DEBUG){ 
			map.print(); 
			System.out.println("---------------------------------------------------------------");
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
