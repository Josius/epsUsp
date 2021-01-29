import java.io.*;
import java.util.*;

public class EP2Test8 {
	
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
		
		switch(criteria){
			case 1:
				caminho1(map, lin, col, path, path_index);				
				break;
			case 2:
				
				System.out.println("Caminho mais longo");
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
	
	private static List<No> rota(Map map, No atual, int[] path, int path_index) {
		
        No transitivo = atual;
		LinkedList<No> temp = new LinkedList<No>();
//	Como a entrada do caminho eh inversa, do final para o comeco, armazenamos em um arraylist para depois retirar na ordem correta	
		while (transitivo != null) {
			temp.add(transitivo);
            transitivo = transitivo.getPosAnterior();
//            System.out.println(transitivo.getPosAtual().getX());
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
//        return Collections.emptyList();
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
			
			if((map.verificaCelula(atual.getPosAtualX(), atual.getPosAtualY())) || (map.celulaVisitada(atual.getPosAtualX(), atual.getPosAtualY())) || map.blocked(atual.getPosAtualX(), atual.getPosAtualY())){ 

				continue;
			}
			
			
            for(int i = 0; i < sentidos.length; i++){

				No sentido = new No(new Posicao(atual.getPosAtualX() + sentidos[i][0], atual.getPosAtualY() + sentidos[i][1]), atual);

				fila.add(sentido);
//				map.step(atual.getPosAtualX(), atual.getPosAtualY());
//				map.print();
			}
//			map.step(atual.getX(), atual.getY());
//			map.print();
        }		
		if(DEBUG){ 
			map.print(); 
			System.out.println("---------------------------------------------------------------");
		}
		
//		return Collections.emptyList();
		return fila;
	}
	
// Para caminhar no labirinto	
	public static boolean caminho4(Map map, int lin, int col, int[] path, int path_index){
		
		if(map.verificaCelula(lin, col) || map.blocked(lin, col) || map.celulaVisitada(lin, col)){
			//System.out.println("Sem chance");
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
			//map.print();
			//System.out.println("i:" + i);
			
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
//		int criteria = Integer.parseInt(args[1]);
//		int [] path = findPath(map, criteria);
//		printSolution(map, path);	
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
