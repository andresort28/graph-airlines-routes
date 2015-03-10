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




}
