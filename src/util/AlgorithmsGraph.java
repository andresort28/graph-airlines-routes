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
	
	
	




}
