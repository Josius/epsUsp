public static boolean caminho4(Map map, int lin, int col, int[] path, int path_index){
		
		boolean[][] mapaExplorado = new boolean[lin][col];
		for(int i=0; i<mapaExplorado.length; i++){
			for(int j=0; j<mapaExplorado[i]; j++){
				mapaExplorado[i][j] = false;
			}
		}
		int[] fila = new int[path.length]; //LinkedList<Posicao> nextToVisit = new LinkedList<>();
		int iniX = 0; 
		int iniY = iniX + 1; 
		fila[iniX] = lin; //Posicao inicio = new Posicao(lin, col);
		fila[iniy] = col; //Posicao inicio = new Posicao(lin, col);
				
		mapaExplorado[lin][col] = true; //nextToVisit.add(inicio);
		
		while(fila[0] != 0){
			//Posicao atual = nextToVisit.remove();
			int linX = fila[iniX];
			int colY = fila[iniY];
			fila[iniX] = 0;
			fila[iniY] = 0;
			iniX += 2;
			//Posicao atual = nextToVisit.remove();
			
			if(map.verificaCelula(linX, colY) || map.celulaVisitada(linX, colY)){ 
				
				continue;
			}
			if(map.blocked(linX, colY)){
				
				continue;
			}
			if(map.finished(linX, colY)){
//				
				backtrackPath(map, linX, colY, path, path_index);
			}
			
			for(int i = 0; i < sentidos.length; i++){
//				map.print();
//				System.out.println("i:" + i);
				int direcaoHorizontal = linX + sentidos[i][0];
				int direcaoVertical = colY + sentidos[i][1];
				fila[iniX] = direcaoHorizontal;
				fila[iniY] = direcaoVertical;
				
				//map.step(atual.getX(), atual.getY());
			}
			
        }		
		
		if(DEBUG){ 
			map.print(); 
			System.out.println("---------------------------------------------------------------");
		}
		
		return true;
	}
	
	private static void backtrackPath(Map map, int lin, int col, int[] path, int path_index) {
		
        int linTemp = lin;
		int colTemp = col;
		
		int i = 0;
	
		path[path_index] = linTemp;
		path[path_index + 1] = colTemp;	
		path_index += 2;
		path[0] = path_index;
		map.step(linTemp(), colTemp());      
    }