public static boolean caminho3(Map map, int lin, int col, int[] path, int path_index){
		
		int cont = 0;
		No[] noSaida = new No[4];// usado para guardar o ultimo vertice/no do caminho encontrado
		int vlrTtlItens = 0;// usar para comparacao com o valor do ultimo vertic/no encontrado
		Item verifItem;
		int atualX, atualY; 
		for(int i = 0; i < map.nLines(); i++){
			for(int j = 0; j < map.nColumns(); j++){
				verifItem = map.getItem(i, j);
				if(verifItem != null) vlrTtlItens += verifItem.getValue();	
			}
		}
		
		LinkedList<No> filaItens = new LinkedList<No>();
		No noIniItem;
		verifItem = map.getItem(lin, col);
		
		if(verifItem != null){
			noIniItem = new No(new Posicao(lin, col), null, verifItem, verifItem.getValue()); //	criar construtor sem o campo posAnterior e com campo item
		}else noIniItem = new No(new Posicao(lin, col));

		filaItens.add(noIniItem);
				
		while (filaItens.size() != 0) {
                   
			No atual = filaItens.remove(0);            			
			atualX = atual.getPosAtualX();
			atualY = atual.getPosAtualY();
			
			if(map.finished(atualX, atualY){
				
				if(atual.getValorItem() != vlrTtlItens){
					
					if(cont<4){
						noSaida[cont] = atual;
						cont++;
						return false;
					}else return true; // NAO ESQUECER DE FAZER COMPARACAO DOS 4 VLRS DE atual.getValorItem NO FINAL DESTA FUNCAO, APOS SAIR DO WHILE
				}else{
					noSaida[cont] = atual;
					return true;
				}	
			}
			
//	USAR IF ABAIXO PARA CAMINHO MAIS CURTO COM MENOS ITERACOES DO FOR			
			if(map.celulaVisitada(atualX, atualY) || map.blocked(atualX, atualY)){
				continue;
			}
						
            for(int i = 0; i < sentidos.length; i++){
//	USAR IF ABAIXO PARA CAMINHO MAIS CURTO COM MENOS ITERACOES DO FOR	
				if(map.verificaCelula(atualX + sentidos[i][0], atualY + sentidos[i][1]) || map.celulaVisitada(atualX + sentidos[i][0], atualY + sentidos[i][1]) || map.blocked(atualX + sentidos[i][0], atualY + sentidos[i][1])){
					continue;
				} 
				
				No sentido = new No(new Posicao(atualX + sentidos[i][0], atualY + sentidos[i][1]), atual);

				filaItens.add(sentido);
			}
        }		
		if(DEBUG){ 
			map.print(); 
			System.out.println("---------------------------------------------------------------");
		}
		return fila;
	}
