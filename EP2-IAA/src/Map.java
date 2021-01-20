import java.io.*;
import java.util.*;

class Map {

	public static final char FREE = '.';

	private char [][] map;
	private Item [] items;
	private int nLin, nCol, nItems, startLin, startCol, endLin, endCol, size;// size = qtde de células para andar no labirinto (excluíndo paredes)
	public int linha, coluna;	
	
	public Map(String fileName){

		try{

			BufferedReader in = new BufferedReader(new FileReader(fileName));

			Scanner scanner = new Scanner(new File(fileName));

			
			nLin = scanner.nextInt();
			nCol = scanner.nextInt();

			map = new char[nLin][nCol];
			size = 0;

			for(int i = 0; i < nLin; i++){

				String line = scanner.next();
			
				for(int j = 0; j < nCol; j++){

					map[i][j] = line.charAt(j);
//					System.out.print(map[i][j]);
					if(free(i, j)) size++;
				}
//				System.out.println();
			}

			nItems = scanner.nextInt();
			items = new Item[nItems];

			for(int i = 0; i < nItems; i++){

				items[i] = new Item(scanner.nextInt(), scanner.nextInt(), scanner.nextInt(), scanner.nextInt());
			}

			startLin = scanner.nextInt();
			startCol = scanner.nextInt();
			endLin = scanner.nextInt();
			endCol = scanner.nextInt();
		}
		catch(IOException e){

			System.out.println("Error loading map... :(");
			e.printStackTrace();
		}
	}

	public void print(){

		System.out.println("Map size (lines x columns): " + nLin + " x " + nCol);

		for(int i = 0; i < nLin; i++){

			for(int j = 0; j < nCol; j++){

				System.out.print(map[i][j]);
			}

			System.out.println();
		}

		System.out.println("Number of items: " + nItems);

		for(int i = 0; i < nItems; i++){

			System.out.println(items[i]);
		}
	}
// Retorna true se estiver bloqueado
	public boolean blocked(int lin, int col){

		return !free(lin, col);
	}
// Retorna true se estiver livre
	public boolean free(int lin, int col){

		return map[lin][col] == FREE; 
	}

	public void step(int lin, int col){

		map[lin][col] = '*';
	}

	public boolean finished(int lin, int col){

		return (lin == endLin && col == endCol); 
	}

	public int getStartLin(){

		return startLin;
	}

	public int getStartCol(){

		return startCol;
	}

	public int getSize(){

		return size;
	}

	public int nLines(){

		return nLin;
	}

	public int nColumns(){

		return nCol;
	}

	public Item getItem(int lin, int col){

		for(int i = 0; i < items.length; i++){

			Item item = items[i];

			if(item.getLin() == lin && item.getCol() == col) return item;
		}

		return null;
	}
	
//metodos criados
	public void step2(int lin, int col){

		map[lin][col] = 'F';
	}
	
// Verifica se já passamos por essa célula e retorna true 
	public boolean celulaVisitada(int lin, int col){
		
		return map[lin][col] == '*';
	}
	
// Verifica linhas/colunas para não ultrapassar os limites da matriz, além de ver se a celula da matriz é válida
// Ficou complicado o entendimento do código
/*	public boolean verificaCelula(int lin, int col){
		
			if (lin < 0 || lin > nLines() || col < 0 || col > nColumns() || blocked(lin, col) || celulaVisitada(lin, col)){
			return true;
		}else 
			return false;
	}
*/	
// Não funcionou como esperado	
/*
	public static int dirHoriz(int lin, int col, int i, int j){
		
		return lin + i;
	}
	public static int dirVert(int lin, int col, int i, int j){
		
		return col + j;
	}
*/
}
