import java.io.*;
import java.util.*;

public class EP2Test8 {
	
// Matriz de sentidos para caminhar na matriz, usar na recursividade
	public static int[][] sentidos = {{0, 1},{1, 0},{0,-1},{-1,0}};
	
	public static final boolean DEBUG = false;

	public static int [] findPath(Map map, int criteria){

		int lin, col; 
		
		int [] path;  
		int path_index;

		path = new int[2 * map.getSize()];
		path_index = 1;

		lin = map.getStartLin();
		col = map.getStartCol();
		
//	Lista de Adjacencia		
/*		List<No> nozes = new ArrayList<No>();
		constroiCaminhoListAdj(map, nozes);

		for(int i = 0; i < nozes.size(); i++){
			for(int j = 0; j < nozes.size(); j++){
				System.out.println(nozes.get(i));
			}
		}
*/

//	Matriz de Adjacencia
		int[][] matAdj = new int[map.getSize()][map.getSize()];
		boolean[][] mapaBoolean = new boolean[map.nLines()][map.nColumns()];
		
		constroiCaminhoMatAdj(map, matAdj, mapaBoolean);

//	Espelhando a matriz		
		for(int i = 0; i < matAdj.length; i++){
			for(int j = 0; j < matAdj[i].length; j++){
				if(matAdj[i][j] == 1) matAdj[j][i] = 1;
			}

		}

//	Printando a matriz		
/*
		for(int i = 0; i < matAdj.length; i++){
			for(int j = 0; j < matAdj[i].length; j++){
				System.out.print(matAdj[i][j] + ", ");
			}
			System.out.println();
		}
		for(int i = 0; i < mapaBoolean.length; i++){
			for(int j = 0; j < mapaBoolean[i].length; j++){
				System.out.print(mapaBoolean[i][j] + ", ");
			}
			System.out.println();
		}
*/		
		
		

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
	
	public static void constroiCaminhoMatAdj(Map map, int[][] matAdj, boolean[][] mapaBoolean){
		
		int posVert = 0;
		int numVert = 0;
						
		for(int i = 0; i < map.nLines(); i++){
			for(int j = 0; j < map.nColumns(); j++){
				if(map.free(i, j)){
					mapaBoolean[i][j] = true;

				}else mapaBoolean[i][j] = false;
			}
		}
			
		//mapaBoolean[0][3] = false;
		for(int i = 0; i < mapaBoolean[0].length; i++){
			if(mapaBoolean[0][i] == true) numVert++;
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
						//nozes.get(cont).adicionaAresta(cont+1, 1);
						posVert++;
					}
					else posVert++;
				}

			}
		}
		/*
		posVert = map.getSize()-1;
		
		for(int i = map.nLines()-1; i >= 0; i--){
			for(int j = map.nColumns()-1; j >= 0; j--){

				if(map.free(i, j)){
					if(map.verificaCelula(i, j - 1) == false && map.blocked(i, j - 1)==false){	
						matAdj[posVert][posVert-1] = 1;
						//nozes.get(cont).adicionaAresta(cont-1, 1);
						posVert--;
					}
					else posVert--;
				}
			}
		}
		*/
		posVert = 0;
		
		numVert = 0;

		for(int i = 0; i < mapaBoolean[0].length; i++){
			if(mapaBoolean[0][i] == true) numVert++;
		}

		
		for(int i = 0; i < map.nLines(); i++){
			for(int j = 0; j < map.nColumns(); j++){

				if(map.free(i, j)){
					if(map.verificaCelula(i + 1, j) == false && map.blocked(i + 1, j)==false){	
						matAdj[posVert][numVert+posVert] = 1;
						//nozes.get(cont).adicionaAresta(numVert+cont, 1);					
						posVert++;
					}
					else{
						posVert++;
						numVert--;
//						System.out.println("numvert+cont else" + (numVert+cont));
					}	
				}else{
					if(map.verificaCelula(i + 1, j) == false && map.blocked(i + 1, j)==false){	
						numVert++;
					}
					
				}

			}
		}
		/*
		posVert = map.getSize()-1;
		
		numVert = 0;
		
		for(int i = 0; i < mapaBoolean[0].length; i++){
			if(mapaBoolean[0][i] == true) numVert++;
		}

		
		for(int i = map.nLines()-1; i >= 0 ; i--){
			for(int j = map.nColumns()-1; j >= 0 ; j--){
				if(map.free(i, j)){
					if(map.verificaCelula(i - 1, j) == false && map.blocked(i - 1, j)==false){	
						matAdj[posVert][posVert-numVert] = 1;
						//nozes.get(cont).adicionaAresta(cont-numVert, 1);				
						posVert--;
					}else{
						posVert--;
						numVert--;
					}	
				}else{
					if(map.verificaCelula(i - 1, j) == false && map.blocked(i - 1, j)==false){	
						numVert++;
					}
				}
			}
		}
		*/
		
/*		
		for(int i = 0; i < map.nLines(); i++){
			for(int j = 0; j < map.nColumns(); j++){
//				System.out.println(i + " " + j);
//				System.out.println("antes do if " + cont);
				if(map.free(i, j)){
					for(int k=0; k < sentidos.length; k++){
						
						if(map.verificaCelula(posVert + sentidos[k][0], posVert + sentidos[k][1]) == false && map.blocked(posVert + sentidos[k][0], posVert + sentidos[k][1]) == false){
//VERIFICA SE EH PARA DIREITA							
							if(posVert + sentidos[k][0]==0 && posVert + sentidos[k][1]==1){
								matAdj[posVert][posVert+sentidos[k][1]] = 1;
							}
//VERIFICA SE EH PARA BAIXO							
							if(posVert + sentidos[k][0]==1 && posVert + sentidos[k][1]==0){
								matAdj[posVert][posVert+numVert] = 1;
							}
//VERIFICA SE EH PARA ESQUERDA							
							if(posVert + sentidos[k][0]==0 && posVert + sentidos[k][1]==-1){
								matAdj[posVert][posVert-1] = 1;
							}
//VERIFICA SE EH PARA CIMA							
							if(posVert + sentidos[k][0]==-1 && posVert + sentidos[k][1]==0){
								matAdj[posVert][posVert-numVert] = 1;
							}
						}else{
//VERIFICA SE EH PARA BAIXO							
							if(posVert + sentidos[k][0]==1 && posVert + sentidos[k][1]==0){
								posVert++;
								numVert--;
							}
//VERIFICA SE EH PARA CIMA							
							if(posVert + sentidos[k][0]==-1 && posVert + sentidos[k][1]==0){
								posVert--;
								numVert--;
							}
						}
				
					}
				
				}else{
//	Talvez de erro					
					if((map.verificaCelula(i + 1, j) == false && map.blocked(i + 1, j)==false) || (map.verificaCelula(i - 1, j) == false && map.blocked(i - 1, j)==false)){	
						numVert++;
					}
					
				}
				posVert++;
			}
		}*/
	}
	
	public static void constroiCaminhoListAdj(Map map, List<No> nozes){
		
		int cont = 0;
		
		boolean[][] mapaBoolean = new boolean[map.nLines()][map.nColumns()];
		
				
		for(int i = 0; i < map.nLines(); i++){
			for(int j = 0; j < map.nColumns(); j++){
//				System.out.println("i :" + i + " j: " + j);
//				System.out.println("map.free :" + map.free(i, j));
				if(map.free(i, j)){
					No variavel = new No(cont);
					nozes.add(variavel);
					cont++;
					mapaBoolean[i][j] = true;
//					System.out.println(nozes);
				}else mapaBoolean[i][j] = false;
//				System.out.println("cont: " + cont);
			}
		}
		
		cont = 0;
//									{{0, 1},{1, 0},{0,-1},{-1,0}};		
		
		for(int i = 0; i < map.nLines(); i++){
			for(int j = 0; j < map.nColumns(); j++){
//				System.out.println(i + " " + j);
//				System.out.println("antes do if " + cont);
				if(map.free(i, j)){
					if(map.verificaCelula(i, j + 1) == false && map.blocked(i, j + 1)==false){	
//						System.out.println("if " + cont);
//						System.out.println("1 " + nozes.get(cont));
						nozes.get(cont).adicionaAresta(cont+1, 1);
//						System.out.println("2 " + nozes.get(cont));
						cont++;
					}
					else cont++;
				}
//				System.out.println("else " + cont + "\n");
			}
		}
		
		cont = map.getSize()-1;
		
		for(int i = map.nLines()-1; i >= 0; i--){
			for(int j = map.nColumns()-1; j >= 0; j--){
//				System.out.println(i + " " + j);
//				System.out.println("antes do if " + cont);
				if(map.free(i, j)){
					if(map.verificaCelula(i, j - 1) == false && map.blocked(i, j - 1)==false){	
//						System.out.println("if " + cont);
//						System.out.println("1 " + nozes.get(cont));
						nozes.get(cont).adicionaAresta(cont-1, 1);
//						System.out.println("2 " + nozes.get(cont));
						cont--;
					}
					else cont--;
				}
//				System.out.println("else " + cont + "\n");
			}
		}
		
		cont = 0;
		
		int numVert = 0;
		//mapaBoolean[0][3] = false;
		for(int i = 0; i < mapaBoolean[0].length; i++){
			if(mapaBoolean[0][i] == true) numVert++;
		}
//		System.out.println(numVert);
		
		for(int i = 0; i < map.nLines(); i++){
			for(int j = 0; j < map.nColumns(); j++){
//				System.out.println(i + " " + j);
//				System.out.println("cont antes do if " + cont);
//				System.out.println("numVert antes do if " + numVert);
//				System.out.println("numvert+cont antes do if " + (numVert+cont));
				if(map.free(i, j)){
					if(map.verificaCelula(i + 1, j) == false && map.blocked(i + 1, j)==false){	
//						System.out.println("if " + cont);
//						System.out.println("1 " + nozes.get(cont));
//						System.out.println("numvert+cont dentro do if " + (numVert+cont));
						nozes.get(cont).adicionaAresta(numVert+cont, 1);
//						System.out.println("2 " + nozes.get(cont));
						
						cont++;
					}
					else{
						cont++;
						numVert--;
//						System.out.println("numvert+cont else" + (numVert+cont));
					}	
				}else{
					if(map.verificaCelula(i + 1, j) == false && map.blocked(i + 1, j)==false){	
						numVert++;
					}
					
				}
//				System.out.println("numvert+cont ultimo " + (numVert+cont) + "\n");
//				System.out.println("else " + cont + "\n");
			}
		}
		
		cont = map.getSize()-1;
		
		numVert = 0;
		//mapaBoolean[0][3] = false;
		for(int i = 0; i < mapaBoolean[0].length; i++){
			if(mapaBoolean[0][i] == true) numVert++;
		}
//		System.out.println(numVert);
		
		for(int i = map.nLines()-1; i >= 0 ; i--){
			for(int j = map.nColumns()-1; j >= 0 ; j--){
//				System.out.println(i + " " + j);
//				System.out.println("cont antes do if " + cont);
//				System.out.println("numVert antes do if " + numVert);
//				System.out.println("cont-numvert antes do if " + (cont-numVert));
				if(map.free(i, j)){
					if(map.verificaCelula(i - 1, j) == false && map.blocked(i - 1, j)==false){	
//						System.out.println("if " + cont);
//						System.out.println("1 " + nozes.get(cont));
//						System.out.println("cont-numvert dentro do if " + (cont-numVert));
						nozes.get(cont).adicionaAresta(cont-numVert, 1);
//						System.out.println("2 " + nozes.get(cont));
						
						cont--;
					}else{
						cont--;
						numVert--;
//						System.out.println("cont-numvert else" + (cont-numVert));
					}	
				}else{
					if(map.verificaCelula(i - 1, j) == false && map.blocked(i - 1, j)==false){	
						numVert++;
					}
					
				}
//				System.out.println("cont-numvert ultimo " + (cont-numVert) + "\n");
//				System.out.println("else " + cont + "\n");
			}
		}
		
	}
		
	
	
	
	/*
	private static List<No> rota2(Map map, No atual, int[] path, int path_index) {
		
        No transitivo = atual;
		LinkedList<No> temp = new LinkedList<No>();
//	Como a entrada do caminho eh inversa, do final para o comeco, armazenamos em um arraylist para depois retirar na ordem correta	
		while (transitivo != null) {
			temp.add(transitivo);
            transitivo = transitivo.getPosAnterior();
//            System.out.println(transitivo.getPosAtual().getX());
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
//        return Collections.emptyList();
    }
	*/
	private static List<No> rota(Map map, No atual, int[] path, int path_index) {
		
        No transitivo = atual;
		LinkedList<No> temp = new LinkedList<No>();
//	Como a entrada do caminho eh inversa, do final para o comeco, armazenamos em um arraylist para depois retirar na ordem correta	
		while (transitivo != null) {
			temp.add(transitivo);
            transitivo = transitivo.getPosAnterior();
//            System.out.println(transitivo.getPosAtual().getX());
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
//        return Collections.emptyList();
    }
    
//	public static List<No> caminho1(Map map, int lin, int col, int[] path, int path_index, LinkedList<No> arrayNo){
	public static List<No> caminho1(Map map, int lin, int col, int[] path, int path_index){
		
		LinkedList<No> fila = new LinkedList<No>();
		No inicio = new No(new Posicao(lin, col));
		fila.add(inicio);
		
//		System.out.println("fila antes do while (x: " + fila.get(0).getPosAtualX() + ", y: " + fila.get(0).getPosAtualY() + ")");

		
		while (fila.size() != 0) {
                   
			No atual = fila.remove(0);            			
//			System.out.println("fila.size() - while : " + fila.size());
//			System.out.println("\tatual dentro do while (x: " + atual.getPosAtualX() + ", y: " + atual.getPosAtualY()+")");
/*			
			for(int i = 0; i < fila.size(); i++){
			
				if(fila.size() > 0)
					System.out.println("fila dentro do while (x: " + fila.get(i).getPosAtualX() + ", y: " + fila.get(i).getPosAtualY() + ")");				
			}
*/			
			
			
			

// Se nao houver o if abaixo, ele percorre todo o mapa
			
			if(map.finished(atual.getPosAtualX(), atual.getPosAtualY())){
				
//				System.out.println("\t\t\tatual dentro do finished (x: " + atual.getPosAtualX() + ", y: " + atual.getPosAtualY() + ")");
				
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
//				arrayNo.add(sentido);

				fila.add(sentido);
/*
				System.out.println("i: " + i);
				if(atual.getPosAnterior() != null){
					System.out.println("\t\tsentido dentro do for (x: " + sentido.getPosAtualX() + ", y: " + sentido.getPosAtualY() + ") anterior " + atual.getPosAnteriorX() + " " + atual.getPosAnteriorY());
				}else System.out.println("\t\tsentido dentro do for (x: " + sentido.getPosAtualX() + ", y: " + sentido.getPosAtualY() + ")");
*/
				map.step(atual.getPosAtualX(), atual.getPosAtualY());// marcando a celula para que nao passe novamente por ela e faca todo o processo
							
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
//		printSolution(map, path);
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
