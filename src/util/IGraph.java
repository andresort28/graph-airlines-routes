package util;

import java.util.ArrayList;


/**
 * Generic Interface of a Graph
 * @author Andres Ortiz - 10207000 ICESI
 *
 * @param <V> Data type of a Vertex
 * @param <P> Data type of the Path of each edge
 */
public interface IGraph<V, P>
{
	
	public abstract boolean isDirected ();
	/*
	 *
	 */
	public abstract int getNumberOfVertexes ();
	
	/*
	 * 
	 */
	public abstract int getNumberOfEdges ();
	
	/**
	 * 
	 * @return
	 */
	public abstract Object[] getVertexes ();
	
	/**
	 * 
	 * @return
	 */
	public abstract ArrayList<Edge<V,P>> getEdges ();
	
	/**
	 * 
	 * @param vertex
	 * @throws Exception
	 */
	public abstract void addVertex (V vertex) throws Exception;
	
	/**
	 * 
	 * @param begin
	 * @param end
	 * @param weight
	 * @return
	 */
	public abstract boolean addEdge (V begin, V end, P path);
	
	/**
	 * 
	 * @return
	 */
	public abstract boolean isEmpty ();
	
	/**
	 * 
	 * @param vertex
	 * @return
	 */
	public abstract boolean existVertex (V vertex);
	
	/**
	 * 
	 * @param begin
	 * @param end
	 * @param weight
	 * @return
	 */
	public abstract Edge<V, P> searchEgde (V begin, V end, P path);
	
	/**
	 * 
	 * @param vertex
	 * @return
	 */
	public abstract ArrayList<Integer> getAdjacencies (V vertex);
	
	
	
	/**
	 * Method that make a binary search to find the position of an especific vertice
	 * @param vertice
	 * @return
	 */
	public abstract int searchVertex (String vertex);
	
	/**
	 * 
	 * @param u
	 * @param v
	 * @return
	 */
	public abstract P calculatePath (V begin, V end);
	
	/**
	 * 
	 * @param path
	 * @throws Exception
	 */
	public abstract void loadGraph (String path) throws Exception;
	
	
}
