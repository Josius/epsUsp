import java.io.*;
import java.util.*;

public class EP2Test5 {
	
// Matriz de sentidos para caminhar na matriz, usar na recursividade
	public static int[][] sentidos = {{-1,0}, 
									  {0,-1}, 
									  {1, 0}, 
									  {0, 1}};
	
	public static final boolean DEBUG = false;

	public static int [] findPath(Map map, int criteria){

		int lin, col; 
		
		int [] path;  
		int path_index;

		path = new int[2 * map.getSize()];
		path_index = 1;

		lin = map.getStartLin();
		col = map.getStartCol();
		
		Posicao pos = new Posicao(lin, col);
		
		if(caminho(map, pos.getX(), pos.getY(), path, path_index)){
			System.out.println("deu certo!!! EBA!!!");
			return path;
		}
		
		return path;
	}
		
	private static Posicao proxPosicao(int lin, int col, int i, int j){
		
		return new Posicao(lin+i, col+j);
	}

// Para caminhar no labirinto	
	public static boolean caminho(Map map, int lin, int col, int[] path, int path_index){
		
		if(map.verificaCelula(lin, col) || map.blocked(lin, col) || map.celulaVisitada(lin, col)){
			//System.out.println("Sem chance");
			return false;
		}
		
		
		map.step(lin, col);
		path[path_index] = lin;
		path[path_index + 1] = col;
		path_index += 2;
		
		if(map.finished(lin, col)){
			return true;
		}
		
		for(int i = 0; i < sentidos.length; i++){
			//map.print();
			//System.out.println("i:" + i);
			Posicao sentido = proxPosicao(lin, col, sentidos[i][0], sentidos[i][1]);
			if(caminho(map, sentido.getX(), sentido.getY(), path, path_index)){
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
		System.out.println("printSolution");
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
		
		//impressão da matriz dos sentidos
		/*
		for(int i = 0; i < sentidos.length; i++){
			System.out.print(sentidos[i][0] + " " + sentidos[i][1] + " ");
			System.out.println();
		}
		*/
	
		
		
	}
}
