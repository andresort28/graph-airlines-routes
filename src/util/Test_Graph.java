package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

import model.Path;




public class Test_Graph {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception
	{
		long a = System.nanoTime();
		
		AlgorithmsGraph<String, Path> algorithms = new AlgorithmsGraph<String, Path>();
		
		System.out.println("Loading...");
		Graph<String, Path> g = new Graph<String, Path>(false, 0);
		System.out.println("Graph created without info.");
		loadGraph("./test2.txt", g);
		System.out.println("Directed Graph Created!\n");
		System.out.println("The graph is DIRECTED:  " + g.isDirected() + "\n");
		g.printMatrixAdjacencies();
		
				
		//-------------------------
		String u = g.getVertex(g.searchVertex("A"));
		String v = g.getVertex(g.searchVertex("G"));
		//-------------------------
				
		
		System.out.println("\n------------------------------------------------------------");
		System.out.println("Table of vertexes: \n");
		algorithms.printVertices(g);
		System.out.println("\n------------------------------------------------------------\n");
		
		
		
		System.out.println("Adjacent vertexes to vertex " + u + ":  " + g.getAdjacencies(u));
		System.out.println();
		System.out.println("Number of vertexes: " + g.getNumberOfVertexes());
		System.out.println("Vertexes:  " + Arrays.asList(g.getVertexes()));
		System.out.println();
		System.out.println("Number of Edges:  " + g.getNumberOfEdges());
		System.out.println("Edges:  " + g.getEdges());
		
		System.out.println("\n");
		
		
		
		
		
		System.out.println("********************** Test of Deph and Breadth Search **********************\n");
		
		System.out.println("Test to check if the Vertex " + v.toString() + "  is reachable from Vertex " + u.toString() + "\n");	
		System.out.println("The Vertex " + v.toString() + "  is reachable from Vertex " + u.toString() + "  with DFS:  " + algorithms.isReachableDFS(g, u, v));
		System.out.println("The Vertex " + v.toString() + "  is reachable from Vertex " + u.toString() + "  with BFS:  " + algorithms.isReachableDFS(g, u, v));
		System.out.println();
		System.out.println("Deph Search:  " + algorithms.depthFirstSearch(g, u) );
		System.out.println("Breadth search:  " + algorithms.breadthFirstSearch(g, u));
		
		System.out.println("\n\n");	
		
		
		
		
		
		System.out.println("*************************** Test of MST (Kruskal) ******************************\n");		
		
		Graph<String, Path> mst = algorithms.getMST_Kruskal(g);
		System.out.println("\nEdges of MST:  " + mst.getEdges());
		System.out.println();
		System.out.println("Weight of the Normal Graph: " + algorithms.calculateWeight(g));
		System.out.println("Weight of the MST:  " + algorithms.calculateWeight(mst));
		System.out.println();
		System.out.println("Total of Vertexes MST:  " + mst.getNumberOfVertexes());
		System.out.println("Total of Edges MST:  " + mst.getNumberOfEdges());
		
		System.out.println("\n\n");
		
		
		
		
		
		System.out.println("************************** Test of Bellmand Ford ****************************\n");
		
		Object[] bf = algorithms.bellmanFord(g, u);		
		System.out.println("Bellman Ford Algorithm applied with source : " + u);
		System.out.println("The Graph is SAFE, do not have negative cycle : " + bf[0]);
		System.out.println();
		int[] distancesBF = (int[])bf[1];
		int[] fathersBF = (int[])bf[2];
		algorithms.printDistancesDijkstra(g, distancesBF);
		System.out.println();
		algorithms.printFathersDijkstra(g, fathersBF);
		System.out.println();
		
		System.out.println("\n\n");
		
		
		
		
		
		System.out.println("******************************* Test of Disjktra *******************************\n");
		
		Object[] dijkstra = algorithms.dijkstra(g, u);
		int[] distances = (int[])dijkstra[0];
		int[] fathers = (int[])dijkstra[1];
		
		System.out.println("Source of Dijkstra Algorithm:  " + u.toString());
		System.out.println("Disjktra Algorithm applied...\n");
		algorithms.printDistancesDijkstra(g, distances);
		System.out.println();
		algorithms.printFathersDijkstra(g, fathers);
		System.out.println();
		System.out.println();
		System.out.println("The Shortest distances from " + u + "  to  " + v + "  is:  " + algorithms.getShortestDistance(g, v, distances));
		System.out.println();
		System.out.println("The Shortest path from " + u + "  to  " + v + "  is:  " + algorithms.getShortestPath(g, v, fathers));
		
		System.out.println("\n\n");
		
		
		
		
		
		System.out.println("************************** Test of Floyd Warshall ****************************\n");
		
		int[][][] fw = algorithms.floydWarshall(g);
		System.out.println("Floyd Warshall Algorithm applied...\n");		
		System.out.println("The Shortest distances from " + u + "  to  " + v + "  is:  " + algorithms.getShortestDistance_FloydWarshall(g, u, v, fw));
		System.out.println();				
		System.out.println("Table of vertexes: \n");
		algorithms.printVertices(g);
		System.out.println();
		System.out.println("Vertexes between vertex " + u + " and vertex " + v + " : " + algorithms.getVertexesBetween_FloydWarshall(g, u, v, fw, new ArrayList<Integer>()));
		System.out.println("The Shortest path from " + u + "  to  " + v + "  is:  " + algorithms.getShortestPath_FloydWarshall(g, u, v, fw));
		
		System.out.println("\n\n");
		
		
		
		
		
		System.out.println("\n\n-------------------------------------------------------------------------------------");
		long b = System.nanoTime();
		System.out.println("TIME IN NANOSECONDS:  "  + (b-a));
	}
	
	
	static void loadGraph (String path, Graph<String, Path> g) throws Exception
	{
		BufferedReader in = new BufferedReader(new FileReader(path));
		StringTokenizer tokens = new StringTokenizer(in.readLine());
		int numVertexes = Integer.valueOf(tokens.nextToken());
		int numEdges = Integer.valueOf(tokens.nextToken());
		g.loadGraphManually(numVertexes, numEdges);
		for (int i = 0; i < numVertexes; i++) 
		{
			g.addVertex(in.readLine());
		}
		Arrays.sort(g.getVertexes());	//Sort the array of vertexes to the binary search
		System.out.println("Sorted Vertexes : " + Arrays.asList(g.getVertexes()));
		Object[] vertexes = g.getVertexes();
		for (int i = 0; i < numEdges; i++) 
		{
			tokens = new StringTokenizer(in.readLine());
			g.addEdge((String)vertexes[g.searchVertex(tokens.nextToken())], (String)vertexes[g.searchVertex(tokens.nextToken())], new Path (Integer.valueOf(tokens.nextToken()), 0));
		}
		System.out.println("Sorted Edges: " + g.getEdges());
		in.close();	
	}
	

}
