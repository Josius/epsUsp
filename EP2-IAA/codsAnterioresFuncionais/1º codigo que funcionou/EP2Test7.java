import java.io.*;
import java.util.*;

public class EP2Test7 {
	
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
				Posicao pos = new Posicao(lin, col);
				if(caminho1(map, pos.getX(), pos.getY(), path, path_index)){
				
					return path;
				}
				break;
			case 2:				
				System.out.println("Caminho mais longo");
				break;
			case 3:
				System.out.println("Caminho mais valioso");
				break;
			case 4:
//	NOTA: NÃO FUNCIONA COM UM MAPA SEM SAIDA
				caminho4(map, lin, col, path, path_index);
				System.out.println("Caminho mais rapido");
				break;
		}
				
		
		
		
		return path;
	}
		
	private static Posicao proxPosicao(int lin, int col, int i, int j){
		
		return new Posicao(lin+i, col+j);
	}

// Para caminhar no labirinto	
	public static boolean caminho1(Map map, int lin, int col, int[] path, int path_index){
		
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
			Posicao sentido = proxPosicao(lin, col, sentidos[i][0], sentidos[i][1]);
			if(caminho1(map, sentido.getX(), sentido.getY(), path, path_index)){
				return true;
			}
		}
		
		if(DEBUG){ 
			map.print(); 
			System.out.println("---------------------------------------------------------------");
		}
		
		return false;
	}
	
	private static List<Posicao> rota(Map map, Posicao atual, int[] path, int path_index) {
		
        Posicao transitivo = atual;
		LinkedList<Posicao> temp = new LinkedList<Posicao>();
	
		while (transitivo != null) {
			temp.add(transitivo);
            transitivo = transitivo.getAnterior();
        }
		
		while (temp.size() != 0) {
			transitivo = temp.removeLast();
			path[path_index] = transitivo.getX();
			path[path_index + 1] = transitivo.getY();	
			path_index += 2;
			path[0] = path_index;
			map.step(transitivo.getX(), transitivo.getY());
        }
		
        return Collections.emptyList();
    }
    
  
	
	public static List<Posicao> caminho4(Map map, int lin, int col, int[] path, int path_index){
		
		LinkedList<Posicao> fila = new LinkedList<Posicao>();
		Posicao inicio = new Posicao(lin, col);
		fila.add(inicio);
		
		while (fila.size() != 0) {
            
			Posicao atual = fila.remove(0);            			

			if(map.finished(atual.getX(), atual.getY())){
				
				return rota(map, atual, path, path_index);
			}
			
			if((map.verificaCelula(atual.getX(), atual.getY())) || (map.celulaVisitada(atual.getX(), atual.getY())) || map.blocked(atual.getX(), atual.getY())){ 
				
				continue;
			}
			
			
            for(int i = 0; i < sentidos.length; i++){
				System.out.println(i);
				Posicao sentido = new Posicao(atual.getX() + sentidos[i][0], atual.getY() + sentidos[i][1], atual);
				fila.add(sentido);
				map.step(atual.getX(), atual.getY());
				map.print();
			}
//			map.step(atual.getX(), atual.getY());
//			map.print();
        }		
		if(DEBUG){ 
			map.print(); 
			System.out.println("---------------------------------------------------------------");
		}
		
		return Collections.emptyList();
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

