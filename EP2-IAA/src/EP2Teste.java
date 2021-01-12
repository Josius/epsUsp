import java.io.*;
import java.util.*;

public class EP2Teste {

	public static final boolean DEBUG = false;

	public static int[] findPath(Map map, int criterio){
		
		boolean[][] matriz = new boolean[map.nLines()][map.nColumns()];
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
		
		for(int i=0; i<map.nLines(); i++){
			for(int j=0; j<map.nColumns(); j++){
				if(map.free(i, j)){
					matriz[i][j] = true;
				}else{
					matriz[i][j] = false;
				}
			}
		}
		matriz[map.getStartLin()][map.getStartCol()] = true;
		/*
		for(int i=0; i<map.nLines(); i++){
			for(int j=0; j<map.nColumns(); j++){
				
				System.out.print(matriz[i][j] + " ");
			}
			System.out.println();
		}
		*/
		caminho(matriz, map, lin, col, path, path_index);
		//caminho(map, lin, col, path, path_index);
		//System.out.println("map.print no findPath:");
		//map.print();
		/*
		System.out.println("lin " + lin);
		System.out.println("col " + col);
		System.out.println("pathIndex " + path_index);
		
		
		/*for(int i = 0; i< path.length; i++){
			System.out.print("i " + i + " path[i] " + path[i] + " ");
		}*/
		return path;
	}
	
	public static boolean verificar(Map map,int lin, int col){
		
	//	return ((lin && col >=0) && (lin < map.nLines) && (col < nColumns) && (map.free(lin, col))){
		if (lin < 0 || lin > map.nLines() || col < 0 || col > map.nColumns() || map.free(lin, col)){
			return false;
		}else 
			return true;
	}
	
	public static boolean livre(boolean[][] matriz, int lin, int col){
		
		return (matriz[lin][col]);
	}
	
	public static int[] caminho(boolean[][] matriz, Map map, int lin, int col, int[] path, int path_index){
//	public static int[] caminho(Map map, int lin, int col, int[] path, int path_index){
		
		//CASO BASE
		if(map.finished(lin, col)) map.step2(lin, col);
		if(!verificar(map, lin, col)) return path;
		
		//System.out.println(livre(matriz, lin, col));
		livre(matriz, lin, col);
		
		
		
		map.step(lin, col);
		path[path_index] = lin;
		path[path_index + 1] = col;
		path_index += 2;
		
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
		map.print();
	}
}
