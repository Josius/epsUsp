//Josimar Amaro de Sousa - 11270715

import java.io.*;
import java.util.*;

public class EP2Test11 {
	
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
				
				LinkedList<No> itens = listItens(map, lin, col, path, path_index);
				map.limpaMap();
				caminho3(map, sizeMap, nLines, nColumns, lin, col, linFinal, colFinal, path, path_index, itens);

//				DijkstraMenor dijMenor = new DijkstraMenor(sizeMap, nLines, nColumns, lin, col, itens.get(0).getPosAtualX(), itens.get(0).getPosAtualY(), map);
//				No rotaMenor = dijMenor.getRota2();
//				rota2(map, rotaMenor, path, path_index);


				/*
				for(int i = 0; i<itens.size(); i++){
					
					DijkstraMenor dijMenor = new DijkstraMenor(sizeMap, nLines, nColumns, lin, col, itens.get(i).getPosAtualX(), itens.get(i).getPosAtualY(), map);
					No rotaMenor = dijMenor.getRota2();
					rota2(map, rotaMenor, path, path_index);
					
					map.print();
					map.limpaMap();
				}
				*/
/*				System.out.print("itens\n");	
				for(int i = 0; i<itens.size(); i++){
					System.out.print("(" + itens.get(i).getPosAtualX() + " " + itens.get(i).getPosAtualY()+")" + " posAnterior "+itens.get(i).getPosAnterior() + "antx (" +itens.get(i).getPosAnteriorX() + " "+itens.get(i).getPosAnteriorY() +") "+ itens.get(i).getItem() + " vlr item "+itens.get(i).getValorItem() + "\n");
				}	
				System.out.println();
*/
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
	
//	public static boolean caminho3(Map map, int lin, int col, int[] path, int path_index){
	public static LinkedList<No> listItens(Map map, int lin, int col, int[] path, int path_index){	
//	PRECISA COLOCAR UMA VERIFICAÇÃO AQUI PARA -> if(map.verificaCelula(dirHoriz, dirVert) || map.celulaVisitada(dirHoriz, dirVert) || map.blocked(dirHoriz, dirVert)) return false;		
		
		int qtdItens = 0;
		int vlrTtlItens = 0;// usar para comparacao com o valor do ultimo vertic/no encontrado
		Item verifItem;
		int atualX, atualY, dirHoriz, dirVert;
		No atual;
		
//	for funcionando		
		for(int i = 0; i < map.nLines(); i++){
			for(int j = 0; j < map.nColumns(); j++){
				verifItem = map.getItem(i, j);
				if(verifItem != null){
					vlrTtlItens += verifItem.getValue();
					qtdItens++;
				}
//System.out.println("verifItem no for " + verifItem);
			}
		}
//System.out.println("linha 178 - vlrTtlItens " + vlrTtlItens);
		LinkedList<No> filaItens = new LinkedList<No>();
		LinkedList<No> listaItens = new LinkedList<No>();
		No noIniItem;
		verifItem = map.getItem(lin, col);
//System.out.println("linha 182 - verifItem apos for " + verifItem);		
		if(verifItem != null){
			noIniItem = new No(new Posicao(lin, col), null, verifItem, verifItem.getValue()); //	criar construtor sem o campo posAnterior e com campo item
		}else noIniItem = new No(new Posicao(lin, col));
//System.out.println("linha 186 - noIniItem: nx " + noIniItem.getPosAtualX() + " ny " + noIniItem.getPosAtualY() + " npA " + noIniItem.getPosAnterior() + " nI " + noIniItem.getItem() + " nIV " + noIniItem.getValorItem());
		filaItens.add(noIniItem);
				
		while (filaItens.size() != 0) {
//System.out.print("filaItens ");
	
//for(int i = 0; i<filaItens.size(); i++){
//	System.out.print("(" + filaItens.get(i).getPosAtualX() + " " + filaItens.get(i).getPosAtualY()+")");
//}	
//System.out.println();
			
			listaItens.add(filaItens.peek());			
			atual = filaItens.remove(0);            			
			atualX = atual.getPosAtualX();
			atualY = atual.getPosAtualY();
			

			
//System.out.println("linha 195 - atual: ax " + atual.getPosAtualX() + " ay " + atual.getPosAtualY()  + " aI " + atual.getItem() + " aVI " + atual.getValorItem());
//System.out.println("qtdItens " + qtdItens);

//	USAR IF ABAIXO PARA CAMINHO MAIS CURTO COM MENOS ITERACOES DO FOR - pois ela está verificando a celula que estava na 1ª posicao da fila
			if(map.celulaVisitada(atualX, atualY) || map.blocked(atualX, atualY)){
				continue;
			}
				
            for(int i = 0; i < sentidos.length; i++){
            	dirHoriz = atualX + sentidos[i][0];
				dirVert = atualY + sentidos[i][1];
//	USAR IF ABAIXO PARA CAMINHO MAIS CURTO COM MENOS ITERACOES DO FOR	
//System.out.println("	linha 255 - i: " + i + " atual: " + " x " + atualX + " x " + atualY + " - proxPosVer: x " + dirHoriz + " y " + dirVert);
				if(map.verificaCelula(dirHoriz, dirVert) || map.celulaVisitada(dirHoriz, dirVert) || map.blocked(dirHoriz, dirVert)){
					continue;
				}
				
				map.step(atualX, atualY); // marcando a celula para que nao passe novamente por ela e faca todo o processo
				
				No prox = procuraItem(map, dirHoriz, dirVert, path, path_index, atual);
				filaItens.add(prox);
//				qtdItens++;
			}
//System.out.println("linha 270 - 	i: " + i + " Entra na fila => prox: x " + prox.getPosAtualX() + " y " + prox.getPosAtualY() + " proxpAx " + prox.getPosAnteriorX() + " proxpAy " + prox.getPosAnteriorY() + " proxI " + atual.getItem() + " proxVI " + atual.getValorItem());				
				
		}
		listaItens.remove(0);
//		System.out.print("listaItens ");		
//for(int i = 0; i<listaItens.size(); i++){
//	System.out.print("(" + listaItens.get(i).getPosAtualX() + " " + listaItens.get(i).getPosAtualY()+")");
//}	
//System.out.println();
//		System.out.println("qtdItens " + qtdItens);
		return listaItens;			
//		return true;
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
			
//			System.out.println("297 atual(x: " + atual.getPosAtualX() + " y: " +  atual.getPosAtualY() + ")" + " ant(x: " + atual.getPosAnteriorX() + " y: " + atual.getPosAnteriorY() + ")");
			
			if(verifItem != null){
				map.step(atualX, atualY);
//				System.out.println(atual.getPosAtualX() + " " + atual.getPosAtualY());
//				System.out.println(atual.getPosAnteriorX() + " " + atual.getPosAnteriorY());
				flag = false;
				return new No(new Posicao(atualX, atualY), atual.getPosAnterior(), verifItem, verifItem.getValue());
			}
			
			if(map.celulaVisitada(atualX, atualY) || map.blocked(atualX, atualY)){
				continue;
			}
			
//	System.out.println("	linha 300 - 	atual: x " + atual.getPosAtualX() + " y " + atual.getPosAtualY() + " procI " + atual.getItem() + " procVI " + procItem.getValorItem());
			
			for(int i = 0; i < sentidos.length; i++){			
				dirHoriz = atualX + sentidos[i][0];
				dirVert = atualY	 + sentidos[i][1];

//	System.out.println("		linha 306 - procItemAtual: x " + (dirHoriz-sentidos[i][0]) + " y " + (dirVert-sentidos[i][1]) + " --- i: " + i + " ---> prox: x " + dirHoriz + " y " + dirVert);

				if(map.verificaCelula(dirHoriz, dirVert) || map.blocked(dirHoriz, dirVert) || map.celulaVisitada(dirHoriz, dirVert)){

					continue;
				}
	//System.out.println("i: " + i + " proxX " + dirHoriz + " proxY " + dirVert);
				sentido = new No(new Posicao(dirHoriz, dirVert), atual);
//				System.out.println("sentido.anterior " + sentido.getPosAnteriorX());
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
		for(int i = 0; i<copiaItens.size(); i++){
			System.out.print("caminho3 (" + copiaItens.get(i).getPosAtualX() + " " + copiaItens.get(i).getPosAtualY()+")" + " posAnterior "+copiaItens.get(i).getPosAnterior() + "antx (" +copiaItens.get(i).getPosAnteriorX() + " "+copiaItens.get(i).getPosAnteriorY() +") "+ copiaItens.get(i).getItem() + " vlr item "+copiaItens.get(i).getValorItem() + "\n");
		}
		
		for(int i = 0; i<itens.size(); i++){
			System.out.print("itens (" + itens.get(i).getPosAtualX() + " " + itens.get(i).getPosAtualY()+")" + " posAnterior "+itens.get(i).getPosAnterior() + "antx (" +itens.get(i).getPosAnteriorX() + " "+itens.get(i).getPosAnteriorY() +") "+ itens.get(i).getItem() + " vlr item "+itens.get(i).getValorItem() + "\n");
		}
		
		for(int i = 0; i<copiaItens.length;  i++){
			System.out.print("i: " +i + " 2 - caminho3 (" + copiaItens[i].getPosAtualX() + " " + copiaItens[i].getPosAtualY()+")" + " posAnterior "+copiaItens[i].getPosAnterior() + "antx (" +copiaItens[i].getPosAnteriorX() + " "+copiaItens[i].getPosAnteriorY() +") "+ copiaItens[i].getItem() + " vlr item "+copiaItens[i].getValorItem() + "\n");
		}
		
		for(int i = 0; i<copiaItens.length;  i++){
			System.out.print("i: " +i + " 1 - caminho3 (" + copiaItens[i].getPosAtualX() + " " + copiaItens[i].getPosAtualY()+")" + " posAnterior "+copiaItens[i].getPosAnterior() + "antx (" +copiaItens[i].getPosAnteriorX() + " "+copiaItens[i].getPosAnteriorY() +") "+ copiaItens[i].getItem() + " vlr item "+copiaItens[i].getValorItem() + "\n");
		}
*/

	public static void caminho3(Map map, int sizeMap, int nLines, int nColumns, int lin, int col, int linFinal, int colFinal, int[] path, int path_index, LinkedList<No> itens){

		DijkstraMenor dijMenor;
		LinkedList<No> rotasItens = new LinkedList<No>();
		
		for(int i = 0; i < itens.size(); i++){
							
			dijMenor = new DijkstraMenor(sizeMap, nLines, nColumns, lin, col, itens.get(i).getPosAtualX(), itens.get(i).getPosAtualY(), map);
			rotasItens.add(dijMenor.getRota2());
		}
		
		No transitivo = rotasItens.get(0);
		LinkedList<No> temp = new LinkedList<No>();     		
		while (transitivo != null) {
			temp.add(transitivo);
            transitivo = transitivo.getPosAnterior();
        }
        
//        System.out.println(temp.size());
        /*
        for(int i = 0; i < temp.size(); i++){
							
			System.out.println("i:" + i + " ("+ temp.get(i).getPosAtualX() + " "+ temp.get(i).getPosAtualY() + ")"+ " posAntX " + temp.get(i).getPosAnteriorX() + " posAntY " + temp.get(i).getPosAnteriorY() +" item " + temp.get(i).getItem() + " vlr " + temp.get(i).getValorItem());
		} 
		System.out.println(temp.getLast().getPosAtualX() + " , " + temp.getLast().getPosAtualY());
		*/
		    /*
//	Colocando as posicoes corretas no path		
		while (temp.size() != 0) {

        }		*/
		

		/*
		for(int i = 0; i < rotasItens.size(); i++){
			System.out.print("rotasItens (" + rotasItens.get(i).getPosAtualX() + " " + rotasItens.get(i).getPosAtualY()+")" + " posAnterior "+rotasItens.get(i).getPosAnterior() + "antx (" +rotasItens.get(i).getPosAnteriorX() + " "+rotasItens.get(i).getPosAnteriorY() +") "+ rotasItens.get(i).getItem() + " vlr item "+rotasItens.get(i).getValorItem() + "\n");
		}	*/	
		/*
		DijkstraMenor dijMenor = new DijkstraMenor(map.getSize(), map.nLines(), map.nColumns(), lin, col, itens.get(0).getPosAtualX(), itens.get(0).getPosAtualY(), map);
		No rotaMenor = dijMenor.getRota2();
		rota2(map, rotaMenor, path, path_index);*/
		/*itens.remove(0);
		
		No[] copiaItens = new No[itens.size()];
		No temp;
//		LinkedList<No> temp = new LinkedList<No>();
		for(int i = 0; i<itens.size(); i++){
//			copiaItens.add(itens.get(i));
			copiaItens[i] = itens.get(i);
		}
//Colocando em ordem decrescente os valores dos itens		
		for (int i = 1; i < copiaItens.length; i++) {
			for (int j = 0; j < i; j++) {
				if (copiaItens[i].getValorItem() > copiaItens[j].getValorItem()) {
				    temp = copiaItens[i];
				    copiaItens[i] = copiaItens[j];
				    copiaItens[j] = temp;
				}
			}
		}
		
		DijkstraMenor dijMenor2 = new DijkstraMenor(map.getSize(), map.nLines(), map.nColumns(), rotaMenor.getPosAtualX(), rotaMenor.getPosAtualY(), copiaItens[0].getPosAtualX(), copiaItens[0].getPosAtualY(), map);
		rotaMenor = dijMenor.getRota2();
		rota2(map, rotaMenor, path, path_index);
		*/
		
//		System.out.println("ivlr " + ivlrMaior.getValorItem());
	}

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
