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
}
