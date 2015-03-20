package util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;


public class AlgorithmsGraph<V, P>
{
	//Atributes for Disjktra Algorithm
	private int[] fathersD;
	private int[] distancesD;
	private boolean[] visitedD;
	private PriorityQueue<int[]> queueD;
	
	
	public AlgorithmsGraph ()
	{
		this.fathersD = null;
		this.distancesD = null;
		queueD = null;
		this.visitedD = null;
	}
	
	/**
	 * The method return a boolean if the vertex end is reachable from vertex source with DFS
	 * @param g
	 * @param source
	 * @param end
	 * @return
	 */
	public boolean isReachableDFS (Graph<V, P> g, V source, V end)
	{
		ArrayList<V> list = depthFirstSearch(g, source);
		for(V v : list) {
			if(v == end)
				return true;
		}		
		return false;
	}
	
	
	/**
	 * The method return a boolean if the vertex end is reachable from vertex source with BFS
	 * @param g
	 * @param source
	 * @param end
	 * @return
	 */
	public boolean isReachableBFS (Graph<V, P> g, V source, V end)
	{
		ArrayList<V> list = breadthFirstSearch(g, source);
		for(V v : list) {
			if(v == end)
				return true;
		}		
		return false;
	}
	
	/**
	 * Depth Search of the Graph<T> from the vertice both given as parameters.
	 * @param g
	 * @param source
	 * @return
	 */
	public ArrayList<V> depthFirstSearch (Graph<V, P> g, V source)
	{
		ArrayList<V> list = new ArrayList<V>();		
		boolean[] visited = new boolean[g.getNumberOfVertexes()];
		LinkedList<Integer> stack = new LinkedList<Integer>();
		stack.push(g.getPosition(source));
		int c = 0;
		int actual;
		while ( !stack.isEmpty() && c != g.getNumberOfVertexes())
		{
			//System.out.println("Stack: " + stack);
			//System.out.println("Pop to:  " + g.getVertex(actual).toString());
			actual = stack.pop();			
			if(!visited[actual])
			{	
				list.add(g.getVertex(actual));				
				visited[actual] = true;											
				c++;
				ArrayList<Integer> adj = g.getAdjacencies(g.getVertex(actual));
				
				//System.out.println("Visited: " + Arrays.asList(visited) );
				//System.out.println("Adjacent to " + g.getVertex(actual) + "  positions:  " + adj );
				for (int i = adj.size()-1; i >= 0; i--) 
				{
					if(!visited[adj.get(i)])
						stack.push(adj.get(i));						
				}
				//System.out.println("Stack: " + stack);
				//System.out.println();
			}
		}
		return list;
	}
	
	/**
	 * Breadth Search of the Graph<T> from the vertice both given as parameters.
	 * @param g
	 * @param source
	 * @return
	 */
	public ArrayList<V> breadthFirstSearch (Graph<V, P> g, V source)
	{
		ArrayList<V> list = new ArrayList<V>();		
		boolean[] visited = new boolean[g.getNumberOfVertexes()];
		LinkedList<Integer> queue = new LinkedList<Integer>();
		queue.addLast(g.getPosition(source));
		int c = 0;
		int actual;
		while ( !queue.isEmpty() && c != g.getNumberOfVertexes())
		{
			//System.out.println("Queue: " + queue);
			//System.out.println("Poll to:  " + g.getVertex(actual).toString());
			actual = queue.poll();				
			if(!visited[actual])
			{	
				list.add(g.getVertex(actual));				
				visited[actual] = true;												
				c++;
				ArrayList<Integer> adj = g.getAdjacencies(g.getVertex(actual));
				
				//System.out.println("Visited: " + Arrays.asList(visited) );
				//System.out.println("Adjacent to " + g.getVertex(actual) + "  positions:  " + adj );
				for (int i = 0; i < adj.size(); i++) 
				{
					if(!visited[adj.get(i)])
						queue.addLast(adj.get(i));
				}
				//System.out.println("Queue: " + queue);
				//System.out.println();			
			}
		}
		return list;
	}
	
	
	/**
	 * Kruskal Algorithms that return a Minimum Spanning Tree (MST)
	 * @param g
	 * @return
	 * @throws Exception
	 */
	public Graph<V, P> getMST_Kruskal (Graph<V, P> g) throws Exception
	{
		ArrayList<Edge<V, P>> edges = g.getEdges();
		//System.out.println("Edges sorted:  " + edges + "\n");
		Graph<V, P> graph = new Graph<V, P>(g.isDirected(), g.getNumberOfVertexes());
		graph.loadGraphManually(g.getNumberOfVertexes(), g.getNumberOfEdges());	
		Object[] vertexes = g.getVertexes(); 
		for (int i = 0; i < vertexes.length; i++) 
		{
			graph.addVertex((V)vertexes[i]);
			//System.out.println("Vertex " + vertexes[i].toString() + " added to MST");
		}
		ArrayList<V> connected = new ArrayList<V>();
		Iterator<Edge<V, P>> iter = edges.iterator();
		while(iter.hasNext() )
		{
			Edge<V, P> e = iter.next();
			V u = e.getbegin();
			V v = e.getEnd();
			
//			System.out.println("Edge: <" + u.getVertice() + ", " + v.getVertice() + ", " + e.getWeight() + ">");
//			System.out.println("Vertex<T> connected in the MST: " + connected);
//			System.out.println("Total of connected vertexes in the MST: " + connected.size());
//			System.out.println("Edges in the MST:  " + graph.getEdges());
//			System.out.println();	
			
			boolean uC = connected.contains(u);
			boolean vC = connected.contains(v);
			
			if(uC && vC)
			{
				if(!isReachableDFS(graph, u, v))
					graph.addEdge(u, v, e.getPath());	
			}
			else
			{
				graph.addEdge(u, v, e.getPath());
				if(!uC)
					connected.add(u);
				if(!vC)
					connected.add(v);
			}
//			System.out.println("Vertex<T> connected in the MST: " + connected);
//			System.out.println("Total of vertexes in the MST: " + connected.size());
//			System.out.println("Edges in the MST:  " + graph.getEdges());
//			System.out.println("***************************************************************");
//			System.out.println();
		}
		return graph;
	}
	
		
	public Graph<V, P> getMST_Prim (Graph<V, P> g)
	{

//		Graph<T> graph = new Graph<T>(g.isDirected(), g.getNumberOfVertexes());
//		graph.loadGraphManually();
//
//		ArrayList<Edge<Vertex<T>>> edges = g.getEdges();
//		
//		for(Edge<Vertex<T>> e : edges)
//		{
//			if( !graph.existVertex( e.getbegin( ) ) && !graph.existVertex( e.getEnd( ) ) )
//			{
//				graph.addVertex( e.getbegin( ) );
//				graph.addVertex( e.getEnd( ) );
//				graph.addEdge( e.getbegin( ), e.getEnd( ), e.getWeight( ) );				
//			}
//			else if( !graph.existVertex( e.getbegin( ) ) && graph.existVertex( e.getEnd( ) ) )
//			{
//				graph.addVertex( e.getbegin( ) );
//				graph.addEdge( e.getbegin( ), e.getEnd( ), e.getWeight( ) );				
//			}
//			else if( !graph.existVertex( e.getbegin( ) ) && graph.existVertex( e.getEnd( ) ) )
//			{
//				graph.addVertex( e.getEnd( ) );
//				graph.addEdge( e.getbegin( ), e.getEnd( ), e.getWeight( ) );				
//			}
//		}
//		return graph;
		return null;
	}
	
	/**
	 * Return the total weight of an especific graph
	 * @param g
	 * @return
	 * @throws Exception
	 */
	public int calculateWeight (Graph<V, P> g) throws Exception
	{
		ArrayList<String> visited = new ArrayList<String>();
		int weight = 0;
		ArrayList<Edge<V, P>> edges = g.getEdges();
		for (int i = 0; i < edges.size(); i++) 
		{
			Edge<V, P> e = edges.get(i);
			String a = e.getbegin().toString() + e.getEnd().toString();
			String b = e.getEnd().toString() + e.getbegin().toString();			
			if(!visited.contains(a) && !visited.contains(b))
			{
				weight += e.getWeight();
				visited.add(a);
				visited.add(b);
			}
		}	
		return weight;
	}
	
	
	//-------------------------------------------------------------------------------------------------------------------
	// FLOYD -WARSHALL WITH PATH
	//-------------------------------------------------------------------------------------------------------------------
	
	
	public int[][][] floydWarshall (Graph<V, P> g)
	{
		int n = g.getNumberOfVertexes();
		int[][][] fw = new int[n][n][2];
		
		for (int i = 0; i < fw.length; i++) {
			for (int j = 0; j < fw.length; j++) {				
				if(i == j)
					fw[i][j][0] = 0;
				else
				{
					P path = g.calculatePath(g.getVertex(i), g.getVertex(j));
					int w = Integer.MAX_VALUE;
					if(path != null)
						w = path.hashCode();
					fw[i][j][0] = w; 
				}
				fw[i][j][1] = -1;
			}
		}
		
		for( int k = 0; k < n; k++ ) {
			for( int i = 0; i < fw.length; i++ ) {
				for( int j = 0; j < fw[i].length; j++ ) {
					if( fw[i][k][0] != Integer.MAX_VALUE && fw[k][j][0] != Integer.MAX_VALUE && fw[i][k][0] + fw[k][j][0] < fw[i][j][0])
					{
						fw[i][j][0] = fw[i][k][0] + fw[k][j][0];
						fw[i][j][1] = k;
					}
				}
			}
		}
		return fw;
	}
	
	
	public int getShortestDistance_FloydWarshall (Graph<V, P> g, V u, V v, int[][][] fw )
	{
		int i = g.getPosition(u);
		int j = g.getPosition(v);	
		return fw[i][j][0];
	}

	public ArrayList<Integer> getVertexesBetween_FloydWarshall (Graph<V, P> g, V u, V v, int[][][] fw, ArrayList<Integer> list)
	{
		int i = g.getPosition(u);
		int j = g.getPosition(v);
		if(fw[i][j][0] == Integer.MAX_VALUE) // The distance is infinity
			return list;
		if(fw[i][j][1] == -1)
		{
			if(!list.contains(i))
				list.add(i);
			list.add(j);
			return list;
		}
		this.getVertexesBetween_FloydWarshall(g, u, g.getVertex( fw[i][j][1] ), fw, list);
		this.getVertexesBetween_FloydWarshall(g, g.getVertex( fw[i][j][1] ), v, fw, list);		
		return list;
	}
	
	public ArrayList<Edge<V, P>> getShortestPath_FloydWarshall (Graph<V, P> g, V u, V v, int[][][] fw)
	{
		ArrayList<Integer> list = getVertexesBetween_FloydWarshall(g, u, v, fw, new ArrayList<Integer>());
		ArrayList<Edge<V, P>> edges = new ArrayList<Edge<V, P>>(); 
		for (int i = 0; i < list.size()-1; i++) 
		{
			V a = g.getVertex(list.get(i));
			V b = g.getVertex(list.get(i+1));			
			edges.add(g.searchEgde(a, b, g.calculatePath(a, b)));
		}		
		return edges;
	}	
	//-------------------------------------------------------------------------------------------------------------------
	
	
	
	
	//-------------------------------------------------------------------------------------------------------------------
	// DIJKSTRA
	//-------------------------------------------------------------------------------------------------------------------
		
	public Object[] dijkstra (Graph<V, P> g, V source)
	{
		initializeDijkstra(g, source);
		while(!allVertexesVisited())
		{
			int u = queueD.poll()[0];
			visitedD[u] = true;
			ArrayList<Integer> adj = g.getAdjacencies(g.getVertex(u));
			for(int v : adj)
			{
				relaxEdge(u, v, g.calculatePath(g.getVertex(u), g.getVertex(v)));
			}
			refreshPriorityQueue(g);
		}
		return new Object[]{distancesD, fathersD};
	}
	
	public void relaxEdge (int u, int v, P path)
	{
		if(distancesD[u] != Integer.MAX_VALUE && distancesD[v] > distancesD[u] + path.hashCode())
		{
			distancesD[v] = distancesD[u] + path.hashCode();
			fathersD[v] = u;
		}
	}
	
	public void refreshPriorityQueue (Graph<V, P> g)
	{
		queueD.clear();
		for (int i = 0; i < distancesD.length; i++) 
		{
			if(!visitedD[i])
				queueD.add(new int[]{i,distancesD[i]});
		}
	}
	
	public boolean allVertexesVisited ()
	{
		for (int i = 0; i < visitedD.length; i++) {
			if(!visitedD[i])
				return false;
		}
		return true;
	}
	
	public void initializeDijkstra (Graph<V, P> g, V source)
	{
		int n = g.getNumberOfVertexes();
		visitedD = new boolean[g.getNumberOfVertexes()];
		
		distancesD = new int[n];		
		for (int i = 0; i < distancesD.length; i++) {
			distancesD[i] = Integer.MAX_VALUE;
		}
		int pos = g.getPosition(source);
		distancesD[pos] = 0;	
		
		fathersD = new int[n];
		fathersD[pos] = pos;
		
		queueD = new PriorityQueue<int[]>(n, new Comparator<int[]>() {
			@Override
			public int compare(int[] d1, int[] d2) 
			{
				if(d1[1] > d2[1])
					return 1;
				if(d1[1] < d2[1])
					return -1;
				return 0;
			}
		});
		refreshPriorityQueue(g);
	}
	
		
	public ArrayList<Edge<V, P>> getShortestPath (Graph<V, P> g, V v, int[] fathers)
	{		
		ArrayList<Edge<V, P>> path = new ArrayList<Edge<V, P>>();
		int pos = g.getPosition(v);
		while(true)
		{
			if(fathersD[pos] == pos)
				break;	
			else
			{
				V begin = g.getVertex(fathers[pos]);
				V end = g.getVertex(pos);
				Edge<V, P> edge = g.searchEgde(begin, end, g.calculatePath(begin, end));
				path.add(0,edge);
				pos = fathers[pos];
			}
		}
		return path;
	}
	
	public int getShortestDistance (Graph<V, P> g, V v, int[] distances)
	{
		int pos = g.getPosition(v);
		if(pos != -1)
			return distances[pos];	
		return -1;
	}
	
	//-----------------------------------------------------------------------------------------------------------------
	
	/**
	 * Method that return if the graph do not have negative cycle also with the information of fathers and distances of each vertex
	 * @param g
	 * @param source
	 * @return
	 */
	public Object[] bellmanFord (Graph<V, P> g, V source)
	{
		initializeDijkstra(g, source);
		int n = g.getNumberOfVertexes();
		ArrayList<Edge<V, P>> edges = g.getEdges();
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < edges.size(); j++) {
				Edge<V, P> e = edges.get(j);
				relaxEdge(g.getPosition(e.getbegin()), g.getPosition(e.getEnd()), e.getPath());
			}
		}
		for (int i = 0; i < edges.size(); i++) {
			Edge<V, P> e = edges.get(i);
			if(distancesD[g.getPosition(e.getEnd())] > distancesD[g.getPosition(e.getbegin())] + e.getWeight())
				return new Object[]{false, distancesD, fathersD};
		}
		return new Object[]{true, distancesD, fathersD};
	}
	
	
	
	
	
	//-------------------------------------------- Print Methods ------------------------------------------------
	
	public void printVertices (Graph<V, P> g)
	{
		Object[] list = g.getVertexes();
		ArrayList<String> list2 = new ArrayList<String>();
		for(Object key : list)
		{
			list2.add(g.getPosition((V)key) + key.toString());
		}
		Collections.sort(list2);
		for(String i : list2)
		{
			System.out.println(i.substring(0, 1) + "  " + i.substring(1));
		}
	}
	
	public void printDistancesDijkstra (Graph<V, P> g, int[] distances)
	{
		System.out.println("---------- Distances of Dijkstra Algorithm ----------\n");
		for (int i = 0; i < distances.length; i++) 
		{
			System.out.println(i + "  " + g.getVertex(i) + "  distance:  " + distances[i]);
		}
	}
	
	public void printFathersDijkstra (Graph<V, P> g, int[] fathers)
	{
		System.out.println("---------- Fathers Table of Dijkstra Algorithm ----------\n");
		for (int i = 0; i < fathers.length; i++) 
		{		
			V v = g.getVertex(fathers[i]);
			System.out.println(i + "  " + v.toString() + "  is father of:  " + g.getVertex(i));
		}
	}
	

}
