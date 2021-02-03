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
	
//METODOS CRIADOS

// Verifica se ja passamos por essa celula e retorna true. Poderia ter usado o metodo free(), mas acho que ia ficar confuso o entendimento do cod., por isso optei criar este metodo.
	public boolean celulaVisitada(int lin, int col){
		
		return map[lin][col] == '*';
	}
	
// Verifica linhas/colunas para não ultrapassar os limites da matriz. Eh a mesma verificacao de if-else if que se encontra no while do arquivo EP2.java, soh que ao inves de verificar se os dados estao em um certo intervalo, preferi verificar se os dados estao ultrapassando o intervalo

	public boolean verificaCelula(int lin, int col){
		
		if((lin < 0) || (col + 1 > nColumns()) || (lin + 1 > nLines() ) || (col < 0 )){
			return true;
		}
		return false;
	}

// Criada para limpar caminho marcado pelos metodos, como caminho1	
	public void setMap(int lin, int col){
		
		this.map[lin][col] = FREE;
	}
	
	public int getEndLin(){

		return endLin;
	}

	public int getEndCol(){

		return endCol;
	}
	
	public boolean verificaMapa(){
		
		for(int i = 0; i < nLines(); i++){
        	for(int j = 0; j < nColumns(); j++){
        		if(celulaVisitada(i,j))
        			return true;
        	}
        }
        return false;
	}

	public int getNItems(){
		
		return nItems;
	}
	public char getCharMap(int lin, int col){
		
		return map[lin][col];
	}
}
