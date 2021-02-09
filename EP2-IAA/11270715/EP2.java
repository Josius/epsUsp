//Josimar Amaro de Sousa - 11270715

import java.io.*;
import java.util.*;

public class EP2 {
	
	public static int[][] sentidos = {{-1, 0},{0, -1},{1, 0},{0, 1}};
	
	public static final boolean DEBUG = false;

	public static int [] findPath(Map map, int criteria){

		int lin, col, linFinal, colFinal, sizeMap, nLines, nColumns; 
		
		int [] path;  
		int path_index;

		path = new int[2 * map.getSize()];
		path_index = 1;

		lin = map.getStartLin();
		col = map.getStartCol();
//	Variaveis criadas
		linFinal = map.getEndLin();
		colFinal = map.getEndCol();
		sizeMap = map.getSize();
		nLines = map.nLines();
		nColumns = map.nColumns();

		switch(criteria){
			case 1:
				caminho1(map, lin, col, path, path_index);				
				/*
				DijkstraMenor dijMenor = new DijkstraMenor(sizeMap, nLines, nColumns, lin, col, linFinal, colFinal, map);
				No rotaMenor = dijMenor.getRota2();
				rota(map, rotaMenor, path, path_index);
				*/
				break;
			case 2:								
				DijkstraMaior dijMaior = new DijkstraMaior(sizeMap, nLines, nColumns, lin, col, linFinal, colFinal, map);
				No rotaMaior = dijMaior.getRota2();
				rota2(map, rotaMaior, path, path_index);
				break;
			case 3:
				
				LinkedList<No> itens = listItens(map, lin, col, path, path_index);
				map.limpaMap();
				caminho3(map, sizeMap, nLines, nColumns, lin, col, linFinal, colFinal, path, path_index, itens);

				break;
			case 4:
				break;
		}
		return path;
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
        map.limpaMap();
        
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
		
		int atualX, atualY, dirHoriz, dirVert;
		LinkedList<No> filaNos = new LinkedList<No>();
		No noIni = new No(new Posicao(lin, col));
		filaNos.add(noIni);
		No atual, sentido;		
		
		while (filaNos.size() != 0) {
                   
			atual = filaNos.remove(0);            			
			atualX = atual.getPosAtualX();
			atualY = atual.getPosAtualY();
			
			if(map.finished(atualX, atualY)){
				return rota(map, atual, path, path_index);
			}
			
//	USAR IF ABAIXO PARA CAMINHO MAIS CURTO COM MENOS ITERACOES DO FOR			
			if(map.celulaVisitada(atualX, atualY) || map.blocked(atualX, atualY)){
				continue;
			}
						
            for(int i = 0; i < sentidos.length; i++){
				dirHoriz = atualX + sentidos[i][0];
				dirVert = atualY + sentidos[i][1];
//	USAR IF ABAIXO PARA CAMINHO MAIS CURTO COM MENOS ITERACOES DO FOR	
				if(map.verificaCelula(dirHoriz, dirVert) || map.celulaVisitada(dirHoriz, dirVert) || map.blocked(dirHoriz, dirVert)){
					continue;
				} 
				
				sentido = new No(new Posicao(dirHoriz, dirVert), atual);

				filaNos.add(sentido);
				map.step(atualX, atualY);// marcando a celula para que nao passe novamente por ela e faca todo o processo
			}
        }		
		if(DEBUG){ 
			map.print(); 
			System.out.println("---------------------------------------------------------------");
		}
		return filaNos;
	}
	
	public static LinkedList<No> listItens(Map map, int lin, int col, int[] path, int path_index){	
		
		Item verifItem;
		int atualX, atualY, dirHoriz, dirVert;
		No atual;
		
		LinkedList<No> filaItens = new LinkedList<No>();
		LinkedList<No> listaItens = new LinkedList<No>();
		No noIniItem;
		verifItem = map.getItem(lin, col);

		if(verifItem != null){
			noIniItem = new No(new Posicao(lin, col), null, verifItem, verifItem.getValue());
		}else noIniItem = new No(new Posicao(lin, col));
		filaItens.add(noIniItem);
				
		while (filaItens.size() != 0) {			
			listaItens.add(filaItens.peek());			
			atual = filaItens.remove(0);            			
			atualX = atual.getPosAtualX();
			atualY = atual.getPosAtualY();
			
			if(map.celulaVisitada(atualX, atualY) || map.blocked(atualX, atualY)){
				continue;
			}
				
            for(int i = 0; i < sentidos.length; i++){
            	dirHoriz = atualX + sentidos[i][0];
				dirVert = atualY + sentidos[i][1];

				if(map.verificaCelula(dirHoriz, dirVert) || map.celulaVisitada(dirHoriz, dirVert) || map.blocked(dirHoriz, dirVert)){
					continue;
				}
				
				map.step(atualX, atualY); // marcando a celula para que nao passe novamente por ela e faca todo o processo
				
				No prox = procuraItem(map, dirHoriz, dirVert, path, path_index, atual);
				filaItens.add(prox);

			}	
		}
		listaItens.remove(0);
		return listaItens;			
	}			

	public static No procuraItem(Map map, int lin, int col, int[] path, int path_index, No antProcItem){
		
		int atualX, atualY, dirHoriz, dirVert;		
		Item verifItem;
		No atual, sentido;
		
		LinkedList<No> no = new LinkedList<No>();
		No procItem = new No(new Posicao(lin, col), antProcItem);
		no.add(procItem);
		boolean flag = true;	
		
		while(flag==true){

			atual = no.remove(0);            			
			atualX = atual.getPosAtualX();
			atualY = atual.getPosAtualY();
			verifItem = map.getItem(atualX, atualY);
						
			if(verifItem != null){
				map.step(atualX, atualY);
				flag = false;
				return new No(new Posicao(atualX, atualY), atual.getPosAnterior(), verifItem, verifItem.getValue());
			}
			
			if(map.celulaVisitada(atualX, atualY) || map.blocked(atualX, atualY)){
				continue;
			}
			
			for(int i = 0; i < sentidos.length; i++){			
				dirHoriz = atualX + sentidos[i][0];
				dirVert = atualY	 + sentidos[i][1];

				if(map.verificaCelula(dirHoriz, dirVert) || map.blocked(dirHoriz, dirVert) || map.celulaVisitada(dirHoriz, dirVert)){
					continue;
				}
				sentido = new No(new Posicao(dirHoriz, dirVert), atual);
				no.add(sentido);
				map.step(atualX, atualY);
			}
		}
		if(DEBUG){ 
			map.print(); 
			System.out.println("---------------------------------------------------------------");
		}
		return procItem;
	}
	
	public static void caminho3(Map map, int sizeMap, int nLines, int nColumns, int lin, int col, int linFinal, int colFinal, int[] path, int path_index, LinkedList<No> itens){

		DijkstraMenor dijMenor;
		LinkedList<No> rotasItens = new LinkedList<No>(); // armazenando os nos em que se encontram cada item
		
		for(int i = 0; i < itens.size(); i++){
							
			dijMenor = new DijkstraMenor(sizeMap, nLines, nColumns, lin, col, itens.get(i).getPosAtualX(), itens.get(i).getPosAtualY(), map);
			rotasItens.add(dijMenor.getRota2());
		}
		
		No finaly = new No(new Posicao(linFinal, colFinal), DijkstraMenor.devolveVertice(map, linFinal, colFinal));
		rotasItens.addLast(finaly);

		for(int i = 0; i < rotasItens.size(); i++){
			
			System.out.println("i:" + i + " ("+ rotasItens.get(i).getPosAtualX() + " "+ rotasItens.get(i).getPosAtualY() + ")" + " vertice " + rotasItens.get(i).getVertice());
							
		} 
		
		System.out.println("rotasItens.size " + rotasItens.size());
		System.out.println("rotasItens.getlast x" + rotasItens.getLast().getPosAtualX() + "y" + rotasItens.getLast().getPosAtualY());

		rotaFinal(map, sizeMap, nLines, nColumns, lin, col, linFinal, colFinal, path, path_index, rotasItens, 0, 1);

		}
		
		
		
	public static void rotaFinal(Map map, int sizeMap, int nLines, int nColumns, int lin, int col, int linFinal, int colFinal, int[] path, int path_index, LinkedList<No> rotasItens, int indA, int indB){
	
		int atualX, atualY, proxX, proxY, vlrMaior, somaValor;
		int a = 0; //=0
		int b = 1; //=1
		No atual, prox, fim;
		DijkstraMenor dijFinal;


	
//	      while(rotasItens.size() > 2){
			atual = rotasItens.get(a);
			atualX = atual.getPosAtualX();
			atualY = atual.getPosAtualY();
			
			prox = rotasItens.get(b);
			proxX = prox.getPosAtualX();
			proxY = prox.getPosAtualY();
			vlrMaior = prox.getValorItem();

			System.out.println("proxx " + proxX + " prxy " + proxY + " vlrm " + vlrMaior);
			for (int i = 2; i < rotasItens.size(); i++) {
				for (int j = 1; j < i; j++) {
					if (rotasItens.get(i).getValorItem() > rotasItens.get(j).getValorItem()) {
						b=i;
						prox = rotasItens.get(b);
						proxX = prox.getPosAtualX();
						proxY = prox.getPosAtualY();
						vlrMaior = prox.getValorItem();
					}
				}
			}	
			System.out.println("proxx " + proxX + " prxy " + proxY + " vlrm " + vlrMaior);
			dijFinal = new DijkstraMenor(sizeMap, nLines, nColumns, atualX, atualY, proxX, proxY, map, atual);
			fim = dijFinal.getRota2();
System.out.println("FIM ("+ fim.getPosAtualX() + " "+ fim.getPosAtualY() + ")"+ " posAntX " + fim.getPosAnteriorX() + " posAntY " + fim.getPosAnteriorY() +" item " + fim.getItem() + " vlr " + fim.getValorItem() + " vert " + fim.getVertice());			
			rotasItens.remove(b);
			atual = fim;
			atualX = atual.getPosAtualX();
			atualY = atual.getPosAtualY();
			
			prox = rotasItens.get(b);
			proxX = prox.getPosAtualX();
			proxY = prox.getPosAtualY();
			vlrMaior = prox.getValorItem();
			System.out.println("linaha 389 proxx " + proxX + " prxy " + proxY + " vlrm " + vlrMaior + " proxVEr " + prox.getVertice());
			for (int i = 2; i < rotasItens.size(); i++) {
				for (int j = 1; j < i; j++) {
					if (rotasItens.get(i).getValorItem() > rotasItens.get(j).getValorItem()) {
						b=i;
						prox = rotasItens.get(b);
						proxX = prox.getPosAtualX();
						proxY = prox.getPosAtualY();
						vlrMaior = prox.getValorItem();
					}
				}
			}
			System.out.println("atualx " + atualX + " atualy " + atualY + " vlrmatual " + atual.getValorItem() + " atualVEr " + atual.getVertice());
			System.out.println("proxx " + proxX + " prxy " + proxY + " vlrm " + vlrMaior + " proxVEr " + prox.getVertice());
			
			for(int i = 0; i < rotasItens.size(); i++){
							
				System.out.println("i:" + i + " ("+ rotasItens.get(i).getPosAtualX() + " "+ rotasItens.get(i).getPosAtualY()+")");
			} 
			System.out.println("atualx " + atualX + " atualy " + atualY + " vlrmatual " + atual.getValorItem() + " atualVEr " + atual.getVertice());
			System.out.println("proxx " + proxX + " prxy " + proxY + " vlrm " + vlrMaior + " proxVEr " + prox.getVertice());
			if(prox.getVertice() < atual.getVertice()){
				dijFinal = new DijkstraMenor(sizeMap, nLines, nColumns, atualX, atualY, linFinal, colFinal, map, atual);
			}

			fim = dijFinal.getRota2();
System.out.println("FIM 222  ("+ fim.getPosAtualX() + " "+ fim.getPosAtualY() + ")"+ " posAntX " + fim.getPosAnteriorX() + " posAntY " + fim.getPosAnteriorY() +" item " + fim.getItem() + " vlr " + fim.getValorItem() + " vert " + fim.getVertice());
			rota(map, fim, path, path_index);
//		}	
	}
	
	public static void printSolution(Map map, int [] path){

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
		System.out.printf("%d %.2f %n", (path_size - 1)/2, tempo);

		for(int i = 1; i < path_size; i += 2){

			int lin = path[i];
			int col = path[i + 1];

			System.out.println(lin + " " + col);
		}

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
	}
}
