//Josimar Amaro de Sousa - 11270715

import java.io.*;
import java.util.*;

public class EP2Test10 {
	
// Matriz de sentidos para caminhar no mapa, usar na recursividade
//	public static int[][] sentidos = {{0, 1},{1, 0},{0,-1},{-1,0}};
//	public static int[][] sentidos = {{1, 0},{0,-1},{-1,0},{0, 1}};
	public static int[][] sentidos = {{-1, 0},{0, -1},{1, 0},{0, 1}};
//	public static int[][] sentidos = {{0, 1},{1, 0},{0,-1},{-1,0}};
	
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

//	Matriz de Adjacencia

		switch(criteria){
			case 1:
				caminho1(map, lin, col, path, path_index);				
				/*
				DijkstraMenor dijMenor = new DijkstraMenor(sizeMap, nLines, nColumns, lin, col, linFinal, colFinal, map);
				No rotaMenor = dijMenor.getRota2();
				rota2(map, rotaMenor, path, path_index);
				*/
				break;
			case 2:								
				DijkstraMaior dijMaior = new DijkstraMaior(sizeMap, nLines, nColumns, lin, col, linFinal, colFinal, map);
				No rotaMaior = dijMaior.getRota2();
				rota2(map, rotaMaior, path, path_index);
				break;
			case 3:
				
				caminho3(map, lin, col, path, path_index);
				/*
				DijkstraMV dijk = new DijkstraMV(sizeMap, nLines, nColumns, lin, col, linFinal, colFinal, map);
//				dijk.verifica();
				No rotaValio = dijk.getRota2();
				rota2(map, rotaValio, path, path_index);
				System.out.println("Caminho mais valioso");
				*/
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
	
	public static boolean caminho3(Map map, int lin, int col, int[] path, int path_index){
//	PRECISA COLOCAR UMA VERIFICAÇÃO AQUI PARA -> if(map.verificaCelula(dirHoriz, dirVert) || map.celulaVisitada(dirHoriz, dirVert) || map.blocked(dirHoriz, dirVert)) return false;		
		
		int cont = 0;
		No[] noSaida = new No[4];// usado para guardar o ultimo vertice/no do caminho encontrado
		int vlrTtlItens = 0;// usar para comparacao com o valor do ultimo vertic/no encontrado
		Item verifItem;
		int atualX, atualY, dirHoriz, dirVert;
		//No prox;
		
//	for funcionando		
		for(int i = 0; i < map.nLines(); i++){
			for(int j = 0; j < map.nColumns(); j++){
				verifItem = map.getItem(i, j);
				if(verifItem != null){
					vlrTtlItens += verifItem.getValue();
				}
//System.out.println("verifItem no for " + verifItem);
			}
		}
//System.out.println("linha 178 - vlrTtlItens " + vlrTtlItens);
		LinkedList<No> filaItens = new LinkedList<No>();
		No noIniItem;
		verifItem = map.getItem(lin, col);
//System.out.println("linha 182 - verifItem apos for " + verifItem);		
		if(verifItem != null){
			noIniItem = new No(new Posicao(lin, col), null, verifItem, verifItem.getValue()); //	criar construtor sem o campo posAnterior e com campo item
		}else noIniItem = new No(new Posicao(lin, col));
System.out.println("linha 186 - noIniItem: nx " + noIniItem.getPosAtualX() + " ny " + noIniItem.getPosAtualY() + " npA " + noIniItem.getPosAnterior() + " nI " + noIniItem.getItem() + " nIV " + noIniItem.getValorItem());
		filaItens.add(noIniItem);
				
		while (filaItens.size() != 0) {
System.out.print("filaItens ");
	
for(int i = 0; i<filaItens.size(); i++){
	System.out.print("(" + filaItens.get(i).getPosAtualX() + " " + filaItens.get(i).getPosAtualY()+")");
}	
System.out.println();
						
			No atual = filaItens.remove(0);            			
			atualX = atual.getPosAtualX();
			atualY = atual.getPosAtualY();
			

			
System.out.println("linha 195 - atual: ax " + atual.getPosAtualX() + " ay " + atual.getPosAtualY()  + " aI " + atual.getItem() + " aVI " + atual.getValorItem());
//System.out.println("cont " + cont);
			/*
			if(cont==3){
				System.out.println("linha 197 - atual: ax " + atual.getPosAtualX() + " ay " + atual.getPosAtualY() + " apaX " + atual.getPosAnteriorX() + " apaY " + atual.getPosAnteriorY() + " aI " + atual.getItem() + " aVI " + atual.getValorItem());
				map.limpaMap();
				rota(map, atual, path, path_index);
			}
			*/
			
			if(map.finished(atualX, atualY)){

System.out.println("FINISHED - linha 199 - 	atual: ax " + atual.getPosAtualX() + " ay " + atual.getPosAtualY() + " apAx " + atual.getPosAnteriorX() + " apAy " + atual.getPosAnteriorY() + " aI " + atual.getItem() + " aVI " + atual.getValorItem());

				rota(map, atual, path, path_index);
			}
	
			
			/*
			if(map.finished(atualX, atualY)){
System.out.println("	1-cont " + cont);
System.out.println("atual " + atual.getPosAtualX() + " " + atual.getPosAtualY() + " " + atual.getPosAnteriorX() + " " + atual.getPosAnteriorY() + " " + atual.getItem() + " " + atual.getValorItem());
	System.out.println("1-map.finished");
				if(atual.getValorItem() != vlrTtlItens){
System.out.println("2-map.finished");					
					if(cont<4){
System.out.println("3-map.finished");
						noSaida[cont] = atual;
System.out.println("4-map.finished");
						cont++;
System.out.println("	2-cont " + cont);						
System.out.println("5-map.finished");
						continue;
//						return false;
					}
					else{
System.out.println("6-map.finished");
						return true; // NAO ESQUECER DE FAZER COMPARACAO DOS 4 VLRS DE atual.getValorItem NO FINAL DESTA FUNCAO, APOS SAIR DO WHILE
					}	
				}
				else{
System.out.println("7-map.finished");					
					noSaida[cont] = atual;
System.out.println("noSaida[0]"+noSaida[0]);
System.out.println("noSaida[1]"+noSaida[1]);
System.out.println("noSaida[2]"+noSaida[2]);
System.out.println("noSaida[3]"+noSaida[3]);
System.out.println("8-map.finished");

//	NESTE ELSE EU PRECISO FAZER A VERIFICACAO DOS 4 VLRS E JOGAR NO METODO ABAIXO
					rota(map, noSaida[cont], path, path_index);
System.out.println("9-map.finished");					
System.out.println("	3-cont " + cont);					
					return true;
				}	
			}
			*/
			
			
//	USAR IF ABAIXO PARA CAMINHO MAIS CURTO COM MENOS ITERACOES DO FOR - pois ela está verificando a celula que estava na 1ª posicao da fila
			if(map.celulaVisitada(atualX, atualY) || map.blocked(atualX, atualY)){
				continue;
			}
				
            for(int i = 0; i < sentidos.length; i++){
            	dirHoriz = atualX + sentidos[i][0];
				dirVert = atualY + sentidos[i][1];
//	USAR IF ABAIXO PARA CAMINHO MAIS CURTO COM MENOS ITERACOES DO FOR	
System.out.println("	linha 255 - i: " + i + " atual: " + " x " + atualX + " x " + atualY + " - proxPosVer: x " + dirHoriz + " y " + dirVert);
				if(map.verificaCelula(dirHoriz, dirVert) || map.celulaVisitada(dirHoriz, dirVert) || map.blocked(dirHoriz, dirVert)){
					continue;
				}
				
				map.step(atualX, atualY);
				
				No prox = procuraItem(map, dirHoriz, dirVert, path, path_index, atual);
				filaItens.add(prox);
				cont++;
				
//				map.step(atualX, atualY);// marcando a celula para que nao passe novamente por ela e faca todo o processo
			}
				
//System.out.println("linha 270 - 	i: " + i + " Entra na fila => prox: x " + prox.getPosAtualX() + " y " + prox.getPosAtualY() + " proxpAx " + prox.getPosAnteriorX() + " proxpAy " + prox.getPosAnteriorY() + " proxI " + atual.getItem() + " proxVI " + atual.getValorItem());				
				
		}
		return true;			
	}			
		
	

	public static No procuraItem(Map map, int lin, int col, int[] path, int path_index, No antProcItem){
		
		int atualX, atualY, dirHoriz, dirVert;		
		Item verifItem;
		No atual, sentido;
		
		LinkedList<No> no = new LinkedList<No>();
		No procItem = new No(new Posicao(lin, col), antProcItem);
		no.add(procItem);
		boolean flag = true;
//System.out.println(procItem.getPosAnterior());
//System.out.println("linha 291 - verifItem: x " + lin + " y " + col + " = " + verifItem);		
		
//		while(no.size() != 0){
		while(flag==true){

			atual = no.remove(0);            			
			atualX = atual.getPosAtualX();
			atualY = atual.getPosAtualY();
			verifItem = map.getItem(atualX, atualY);
			
			System.out.println("297 atual(x: " + atual.getPosAtualX() + " y: " +  atual.getPosAtualY() + ")" + " ant(x: " + atual.getPosAnteriorX() + " y: " + atual.getPosAnteriorY() + ")");
			
			if(verifItem != null){
				map.step(atualX, atualY);
				System.out.println(atual.getPosAtualX() + " " + atual.getPosAtualY());
				System.out.println(atual.getPosAnteriorX() + " " + atual.getPosAnteriorY());
				flag = false;
				return new No(new Posicao(atualX, atualY), atual.getPosAnterior(), verifItem, verifItem.getValue());
			}
			
			if(map.celulaVisitada(atualX, atualY) || map.blocked(atualX, atualY)){
				continue;
			}
			
	System.out.println("	linha 300 - 	atual: x " + atual.getPosAtualX() + " y " + atual.getPosAtualY() + " procI " + atual.getItem() + " procVI " + procItem.getValorItem());
			
			for(int i = 0; i < sentidos.length; i++){			
				dirHoriz = atualX + sentidos[i][0];
				dirVert = atualY	 + sentidos[i][1];

	System.out.println("		linha 306 - procItemAtual: x " + (dirHoriz-sentidos[i][0]) + " y " + (dirVert-sentidos[i][1]) + " --- i: " + i + " ---> prox: x " + dirHoriz + " y " + dirVert);

				if(map.verificaCelula(dirHoriz, dirVert) || map.blocked(dirHoriz, dirVert) || map.celulaVisitada(dirHoriz, dirVert)){

					continue;
				}
	//System.out.println("i: " + i + " proxX " + dirHoriz + " proxY " + dirVert);
				sentido = new No(new Posicao(dirHoriz, dirVert), atual);
				System.out.println("sentido.anterior " + sentido.getPosAnteriorX());
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
/*

public static No procuraItem(Map map, int lin, int col, int[] path, int path_index, No antProcItem){
		
		int dirHoriz, dirVert;		
		map.step(lin, col);
		path[path_index] = lin;
		path[path_index + 1] = col;
		path_index += 2;
		path[0] = path_index;
		
		Item verifItem = map.getItem(lin, col);
		
System.out.println("linha 291 - verifItem: x " + lin + " y " + col + " = " + verifItem);		
		if(verifItem != null){
//	Acho que precisa limpar o mapa
//			map.limpaMap();
			map.completaMap();			
			return new No(new Posicao(lin, col), antProcItem, verifItem, verifItem.getValue());
		}
		
		No procItem = new No(new Posicao(lin, col), antProcItem, verifItem);

System.out.println("linha 300 - 	procItem: x " + procItem.getPosAtualX() + " y " + procItem.getPosAtualY() + " procpAx " + procItem.getPosAnteriorX() + " procpAy " + procItem.getPosAnteriorY() + " procI " + procItem.getItem() + " procVI " + procItem.getValorItem());

		for(int i = 0; i < sentidos.length; i++){			
			dirHoriz = lin + sentidos[i][0];
			dirVert = col + sentidos[i][1];

System.out.println("linha 306 - procItemAtual: x " + (dirHoriz-sentidos[i][0]) + " y " + (dirVert-sentidos[i][1]) + " --- i: " + i + " ---> prox: x " + dirHoriz + " y " + dirVert);

			if(map.verificaCelula(dirHoriz, dirVert) || map.blocked(dirHoriz, dirVert) || map.celulaVisitada(dirHoriz, dirVert)){

				continue;
			}
//System.out.println("i: " + i + " proxX " + dirHoriz + " proxY " + dirVert);
			procuraItem(map, dirHoriz, dirVert, path, path_index, procItem);
		}
		
		if(DEBUG){ 
			map.print(); 
			System.out.println("---------------------------------------------------------------");
		}
		return procItem;
	}

*/
/*
	public static boolean procuraItem(Map map, int lin, int col, int[] path, int path_index){
		
		int dirHoriz, dirVert;
		
		if(map.verificaCelula(lin, col) || map.blocked(lin, col) || map.celulaVisitada(lin, col)){
			return false;
		}
				
		map.step(lin, col);
		path[path_index] = lin;
		path[path_index + 1] = col;
		path_index += 2;
		path[0] = path_index;
		
		Item verifItem = map.getItem(lin, col);
System.out.println("verifItemX " + lin + " verifItemY " + col + " = " + verifItem);		
		if(verifItem != null){
			
			return true;
		}
		
		for(int i = 0; i < sentidos.length; i++){			
			dirHoriz = lin + sentidos[i][0];
			dirVert = col + sentidos[i][1];
System.out.println("atualX " + (dirHoriz-sentidos[i][0]) + " atualY " + (dirVert-sentidos[i][1]) + " --- i: " + i + " ---> proxX " + dirHoriz + " proxY " + dirVert);			
			if(map.verificaCelula(dirHoriz, dirVert) || map.blocked(dirHoriz, dirVert) || map.celulaVisitada(dirHoriz, dirVert)){

				continue;
			}

//System.out.println("i: " + i + " proxX " + dirHoriz + " proxY " + dirVert);
			
			if(procuraItem(map, dirHoriz, dirVert, path, path_index)){
				
				System.out.println(i + " dentro da chamada");
				return true;
			}
		}
		
		if(DEBUG){ 
			map.print(); 
			System.out.println("---------------------------------------------------------------");
		}
		return false;
	}
*/
/*	
// Para caminhar no labirinto	
	public static boolean caminho4(Map map, int lin, int col, int[] path, int path_index){
		
		int dirHoriz, dirVert;
		
		if(map.verificaCelula(lin, col) || map.blocked(lin, col) || map.celulaVisitada(lin, col)){
//			System.out.println("saiu da recursao");
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
//	public static int[][] sentidos = {{1, 0},{0,-1},{-1,0},{0, 1}};		
		for(int i = 0; i < sentidos.length; i++){			
			dirHoriz = lin + sentidos[i][0];
			dirVert = col + sentidos[i][1];
System.out.println("atualX " + (dirHoriz-sentidos[i][0]) + " atualY " + (dirVert-sentidos[i][1]) + " --- i: " + i + " ---> proxX " + dirHoriz + " proxY " + dirVert);			
			if(map.verificaCelula(dirHoriz, dirVert) || map.blocked(dirHoriz, dirVert) || map.celulaVisitada(dirHoriz, dirVert)){

				continue;
			}

//System.out.println("i: " + i + " proxX " + dirHoriz + " proxY " + dirVert);
			
			if(caminho4(map, dirHoriz, dirVert, path, path_index)){
				return true;
			}
		}
		
		if(DEBUG){ 
			map.print(); 
			System.out.println("---------------------------------------------------------------");
		}
		return false;
	}
*/	
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
	}
}
