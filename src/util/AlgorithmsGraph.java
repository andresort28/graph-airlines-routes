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




}
