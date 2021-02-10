public static boolean caminho3(Map map, int lin, int col, int[] path, int path_index){
		
		int cont = 0;
		No[] noSaida = new No[4];// usado para guardar o ultimo vertice/no do caminho encontrado
		int vlrTtlItens = 0;// usar para comparacao com o valor do ultimo vertic/no encontrado
		Item verifItem;
		int atualX, atualY, dirHorizon, dirVert;
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
            	dirHorizon = sentidos[i][0];
				dirVert = sentidos[i][1];
//	USAR IF ABAIXO PARA CAMINHO MAIS CURTO COM MENOS ITERACOES DO FOR	
				if(map.verificaCelula(atualX + dirHorizon, atualY + dirVert) || map.celulaVisitada(atualX + dirHorizon, atualY + dirVert) || map.blocked(atualX + dirHorizon, atualY + dirVert)){
					continue;
				} 
				
				Item atualItem = map.getItem(atualX + dirHorizon, atualY + dirVert);
				
				No prox;
				
				if(atualItem != null){
					prox = new No(new Posicao(atualX + dirHorizon, atualY + dirVert), atual, atualItem, atualItem.getValue());
					contValor += itemAtual.getValue();
				}else prox = new No(new Posicao(atualX + dirHorizon, atualY + dirVert), atual, atualItem, atualItem.getValue());
				
				filaItens.add(prox);
			}
        }		
		if(DEBUG){ 
			map.print(); 
			System.out.println("---------------------------------------------------------------");
		}
		return fila;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
