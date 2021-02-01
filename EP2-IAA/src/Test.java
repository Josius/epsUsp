import java.util.*;

public class Test{
	public static void main(String[] args){
		
		List<No> v = new ArrayList<No>();
		int n = 4;
		
		for(int i = 0; i < n; i++){
			No a = new No(i);
			v.add(a);
		}


		v.get(0).adicionaAresta(1, 1);
//		v.get(1).adicionaAresta(0, 5);
		v.get(0).adicionaAresta(2, 4);
		v.get(1).adicionaAresta(2, 2);
		v.get(1).adicionaAresta(3, 6);
		v.get(2).adicionaAresta(3, 3);
		
		/*
		int graph[][] = new int[][] { 
		{ 0, 0, 1, 2, 0, 0, 0 },
		{ 0, 0, 2, 0, 0, 3, 0 }, 
		{ 1, 2, 0, 1, 3, 0, 0 },
		{ 2, 0, 1, 0, 0, 0, 1 }, 
		{ 0, 0, 3, 0, 0, 2, 0 }, 
		{ 0, 3, 0, 0, 2, 0, 1 }, 
		{ 0, 0, 0, 1, 0, 1, 0 } };
		
		for(int i = 0; i < graph.length; i++){
			for(int f = 0; f < graph[0].length; f++){
			
				System.out.print(graph[i][f] + " ");

			}
			System.out.println();
							break;
		}
		*/
	}
	
	
}
