public static boolean caminho4(Map map, int lin, int col, int[] path, int path_index){
		
		boolean[][] mapaExplorado = new boolean[map.nLines()][map.nColumns()];
		mapaExplorado[lin][col] = true;
		
		Queue<Posicao> fila = new LinkedList<Posicao>();
		Posicao inicio = new Posicao(lin, col);
		
		fila.add(inicio);
		
		while(!fila.isEmpty){
			
			Posicao atual = fila.peek;
			Posicao prox = new Posicao(atual.getX(), atual.getY());
			
			if(map.finished(prox.getX(), prox.getY())){
				return true;
//				backtrackPath(map, linX, colY, path, path_index);//precisa alterar
			}
			else fila.remove();
			
			for(int i = 0; i < sentidos.length; i++){
//				map.print();
//				System.out.println("i:" + i);
				int linha = prox.getX() + sentidos[i][0];
				int coluna = prox.getY() + sentidos[i][1];
				
				if(map.verificaCelula(linha, coluna) && map.celulaVisitada(linha, coluna) && map.blocked(linha, coluna) && !mapaExplorado[linha][coluna]){ 
					//map.step(atual.getX(), atual.getY());
					mapaExplorado[linha][coluna] = true;
					Posicao adjacente = new Posicao(linha, coluna);
					fila.add(adjacente);
				}
			
			
			}

			
			for(int i = 0; i < mapaExplorado.length; i++){
				for(int j = 0; j < mapaExplorado[0].length; j++){
					System.out.print(mapaExplorado[i][j] + " ");
				}
				System.out.println();
			}
			
        }		
		
		if(DEBUG){ 
			map.print(); 
			System.out.println("---------------------------------------------------------------");
		}
		
		return true;
	}
	
	private static void caminhoCurto(Map map, Posicao atual, int[] path, int path_index) {
		
        Posicao iter = atual;
		
		int i = 0;
        while (iter != null) {
			System.out.println(i);
			i++;
            path[path_index] = iter.getX();
			path[path_index + 1] = iter.getY();	
			path_index += 2;
			path[0] = path_index;
			map.step(atual.getX(), atual.getY());
			System.out.println(i);	
            iter = iter.getParent();
			System.out.println(i);
        }

        return Collections.emptyList();
    }
