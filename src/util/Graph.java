package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Graph implemented with adjacencies matrix
 * @author Andres Ortiz
 *
 * @param <String>
 */
public class Graph<V, P> implements IGraph<V, P>
{
	private boolean directed;
	private int index;
	
	protected int numVertexes;
	protected int numEdges;
	protected Object[] vertexes;
	protected Edge<V, P>[][] edges;
	
	/**
	 * Constructor Method
	 * @param directed
	 * @param path
	 * @throws Exception
	 */
	public Graph (boolean directed, int numVertexes)
	{			
		this.directed = directed;
		this.index = 0;
		this.numVertexes = numVertexes;
		this.numEdges = 0;
		this.vertexes = null;
		this.edges = null;
	}	
	
	@Override
	public boolean isDirected () {
		return directed;
	}		
	
	@Override
	public int getNumberOfVertexes() { 
		return numVertexes;
	}

	@Override
	public int getNumberOfEdges() 
	{
		int n = getEdges().size();
		if(directed)
			return n;
		return n/2;
	}

	@Override
	public Object[] getVertexes() 
	{
		return vertexes;
	}

	@Override
	public ArrayList<Edge<V, P>> getEdges() 
	{
		ArrayList<Edge<V, P>> list = new ArrayList<Edge<V, P>>();
		for (int i = 0; i < edges.length; i++) {
			for (int j = 0; j < edges[i].length; j++) {
				if(edges[i][j] != null  && edges[i][j].getPath() != null)
					list.add(edges[i][j]);
			}			
		}
		Collections.sort(list);
		return list;
	}

	@Override
	public void addVertex(V vertex) throws Exception
	{		
		if(index == numVertexes)
			throw new Exception("The number of vertexes of the Graph has been exceeded");
		else
		{
			vertexes[index] = vertex;
			edges[index][index] = new Edge<V, P>(vertex, vertex, null);
			index++;
			//Arrays.sort(vertexes);
		}	
	}

	@Override
	public boolean addEdge(V begin, V end, P path) 
	{
		if(searchEgde(begin, end, path) != null)
			return false;
		edges[getPosition(begin)][getPosition(end)] = new Edge<V, P>(begin, end, path);
		if(!directed)
			edges[getPosition(end)][getPosition(begin)] = new Edge<V, P>(end, begin, path);
		return true;
	}

	@Override
	public boolean isEmpty() 
	{
		if(numVertexes == 0)
			return true;
		return false;
	}

	@Override
	public boolean existVertex(V vertex) 
	{
		int pos = searchVertex(vertex.toString());
		if(pos != -1)
			return true;
		return false;
	}

	@Override
	public Edge<V, P> searchEgde(V u, V v, P path) 
	{
		int i = getPosition(u);
		int j = getPosition(v);
		if(i<numVertexes && j<numVertexes && edges[i][j] != null && edges[i][j].getWeight() == path.hashCode())
			return edges[i][j];
		return null;
	}
	
	@Override
	public P calculatePath (V u, V v)
	{
		int i = getPosition(u);
		int j = getPosition(v);
		if(i<numVertexes && j<numVertexes && edges[i][j] != null)
			return edges[i][j].getPath();
		return null;
	}

	@Override
	public ArrayList<Integer> getAdjacencies(V vertex) 
	{
		ArrayList<Integer> list = new ArrayList<Integer>();
		if(vertex == null)
			return list;
		int i = getPosition(vertex);
		for (int j = 0; j < edges[i].length; j++) 
		{
			Edge<V, P> edge = edges[i][j];
			if(edge != null && edge.getPath() != null)
				list.add(j);
		}
		return list;
	}
	
	@Override
	public int searchVertex (String vertex)
	{
		int begin = 0;
		int end = numVertexes-1;
		int pos;
		while(begin <= end)
		{
			pos = (int)((begin+end)/2);
			if(vertex.equals(vertexes[pos].toString()))
				return pos;
			else if (vertex.compareTo(vertexes[pos].toString()) < 0)
				end = pos-1;
			else
				begin = pos+1;
		}
		return -1;
	}
	

		
	@Override
	public void loadGraph(String path) throws Exception
	{
			
	}
	

	
	
	//-------------------------------------------- Others Methods -----------------------------------------

	
	public int getPosition (V v) 
	{
		return searchVertex(v.toString());
	}
	
	public V getVertex (int pos)
	{
		return (V)vertexes[pos];
	}
	
	public void loadGraphManually (int numVertexes, int numEdges)
	{
		this.numVertexes = numVertexes;
		this.numEdges = numEdges;
		vertexes = new Object[this.numVertexes];
		edges = new Edge[this.numVertexes][this.numVertexes];
	}
	
	
	/**
	 * Print by console the matrix of adjacencies
	 */
	public void printMatrixAdjacencies ()
	{
		System.out.println("********** Matrix of Adjacencies ********** \n");
		
		System.out.print("\t");
		for (int i = 0; i < numVertexes; i++) {
			System.out.print(i + "\t");			
		}
		System.out.println("\n");
		
		for (int i = 0; i < edges.length; i++) 
		{
			System.out.print(i + "\t");
			for (int j = 0; j < edges[i].length; j++) 
			{
				if(edges[i][j] == null)
					System.out.print("*\t");
				else if(edges[i][j].getPath() == null)
					System.out.print("0\t");
				else if(edges[i][j].getPath() != null)
					System.out.print(edges[i][j].getWeight() +"\t");
			}
			System.out.println();
		}	
	}
	
	//----------------------------------------------------------------------

	
	
	
}
