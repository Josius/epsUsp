public class Exemplo{
/*
Primeiro, precisamos definir as quatro direções. Podemos definir isso em termos de coordenadas. Essas coordenadas, quando adicionadas a qualquer coordenada, retornarão uma das coordenadas vizinhas:
*/
	private static int[][] DIRECTIONS = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };
	
/*
Agora podemos definir a assinatura do método solve.* A lógica aqui é simples *- se houver um caminho da entrada para a saída, retorne o caminho, caso contrário, retorne uma lista vazia:
*/

	public List<Coordinate> solve(Maze maze) {
		
		List<Coordinate> path = new ArrayList<>();
		if (explore(maze, maze.getEntry().getX(), maze.getEntry().getY(), path)) {
			return path;
		}
		return Collections.emptyList();
	}

/*
Também precisamos de um método utilitário que adicione duas coordenadas:
*/

	private Coordinate getNextCoordinate(int row, int col, int i, int j){
		
		return new Coordinate(row + i, col + j);
	}

/*
Vamos definir o método explore mencionado acima. Se houver um caminho, retorne true, com a lista de coordenadas no argumento path. Este método possui três blocos principais.

Primeiro, descartamos nós inválidos, ou seja, os nós que estão fora do labirinto ou fazem parte da parede. Depois disso, marcamos o nó atual como visitado, para que não visitemos o mesmo nó repetidamente.

Por fim, movemos recursivamente em todas as direções se a saída não for encontrada:
*/

	private boolean explore(Maze maze, int row, int col, List<Coordinate> path) {
		
		if (!maze.isValidLocation(row, col) || maze.isWall(row, col) || maze.isExplored(row, col)) {
			return false;
		}

		path.add(new Coordinate(row, col));
		maze.setVisited(row, col, true); // map.step()

		if (maze.isExit(row, col)) {
			return true;
		}

		for (int[] direction : DIRECTIONS) {
			Coordinate coordinate = getNextCoordinate(row, col, direction[0], direction[1]);
			if (explore(maze, coordinate.getX(), coordinate.getY(), path)){
				return true;
			}
		}

		path.remove(path.size() - 1);
		return false;
	}
	
}