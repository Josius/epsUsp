//PSEUDO CODIGO
/*
function dijkstra(G, S)
    for each vertex V in G
        distance[V] <- infinite
        previous[V] <- NULL
        If V != S, add V to Priority Queue Q
    distance[S] <- 0
	
    while Q IS NOT EMPTY
        U <- Extract MIN from Q
        for each unvisited neighbour V of U
            tempDistance <- distance[U] + edge_weight(U, V)
            if tempDistance < distance[V]
                distance[V] <- tempDistance
                previous[V] <- U
    return distance[], previous[]
*/

public class Dijkstra {

  public static void dijkstra(int[][] graph, int source) {//Map e lin, col
    int count = graph.length; // 'graph.length' talvez trocar por 'map.size()'
    boolean[] visitedVertex = new boolean[count]; // Por padrao, a todos os indices eh atribuido o valor 'false'
    int[] distance = new int[count]; // Por padrao, a todos os indices eh atribuido o valor '1'
	
    for (int i = 0; i < count; i++) {
      visitedVertex[i] = false;
      distance[i] = Integer.MAX_VALUE;
    }

    // Distance of self loop is zero
    distance[source] = 0;
    for (int i = 0; i < count; i++) {
	  System.out.println("2º for i: " + i);
      // Update the distance between neighbouring vertex and source vertex
      int u = findMinDistance(distance, visitedVertex);
	  System.out.println("u do 2º for: " + u);
      visitedVertex[u] = true;

      // Update all the neighbouring vertex distances
      for (int v = 0; v < count; v++) {
		System.out.println("3º for u: "+ u +" v: " + v);
//        if (!visitedVertex[v] && graph[u][v] != 0 && (distance[u] + graph[u][v] < distance[v])) {
        System.out.println("visitedVertex["+v+"] "+visitedVertex[v]);
		if (visitedVertex[v]==false && graph[u][v] != 0 && (distance[u] + graph[u][v] < distance[v])) { //'graph[u][v] != 0' quer dizer que ha ligacao entre o vertice[u] e o vertice[v] e ha um peso nessa ligacao
          System.out.println("distance["+u+"] "+distance[u]);
		  System.out.println("graph["+u+"]["+v+"] " + graph[u][v]);
		  System.out.println("distance["+v+"] "+distance[v]);
		  distance[v] = distance[u] + graph[u][v];
		  System.out.println("apos atribuicao distance["+v+"] "+distance[v]);
        }
      }
    }
    for (int i = 0; i < distance.length; i++) {
      System.out.println(String.format("Distance from %s to %s is %s", source, i, distance[i]));
    }

  }

  // Finding the minimum distance
  private static int findMinDistance(int[] distance, boolean[] visitedVertex) {
    int minDistance = Integer.MAX_VALUE;
    int minDistanceVertex = -1;
//	System.out.println(minDistance);
//	System.out.println(minDistanceVertex);
    for (int i = 0; i < distance.length; i++) {
		System.out.println("i for findMinDistance " + i);
		System.out.println("visitedVertex["+i+"] " + visitedVertex[i]);
		System.out.println("distance["+i+"] " + distance[i]);
		System.out.println("minDistance antes do if " + minDistance);
		System.out.println("minDistanceVertex antes do if "+minDistanceVertex);
//      if (!visitedVertex[i] && distance[i] < minDistance) {
      if (visitedVertex[i]==false && distance[i] < minDistance) {
		
        System.out.println("minDistance dentro do if " + minDistance);
		System.out.println("minDistanceVertex dentro do if "+minDistanceVertex);
		
		minDistance = distance[i];
        minDistanceVertex = i;
		
		System.out.println("minDistance apos atribuicao " + minDistance);
		System.out.println("minDistanceVertex apos atribuicao "+minDistanceVertex);
      }
    }
    return minDistanceVertex;
  }

  public static void main(String[] args) {
    int graph[][] = new int[][] { 
	{ 0, 0, 1, 2, 0, 0, 0 },
	{ 0, 0, 2, 0, 0, 3, 0 }, 
	{ 1, 2, 0, 1, 3, 0, 0 },
    { 2, 0, 1, 0, 0, 0, 1 }, 
	{ 0, 0, 3, 0, 0, 2, 0 }, 
	{ 0, 3, 0, 0, 2, 0, 1 }, 
	{ 0, 0, 0, 1, 0, 1, 0 } };
    Dijkstra T = new Dijkstra();
    T.dijkstra(graph, 0);
  }
}