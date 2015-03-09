package util;


public class Edge<V, P> implements Comparable<Edge<V, P>>
{
	/**
	 * begin vertex of the edge
	 */
	private V begin;
	
	/**
	 * End vertex of the edge
	 */
	private V end;
	
	/**
	 * Weight of this edge
	 */
	private P path;
	
	
	public Edge (V begin, V end, P path)
	{
		this.begin = begin;
		this.end = end;
		this.path = path;
	}

	public V getbegin() {
		return begin;
	}


	public V getEnd() {
		return end;
	}

	public P getPath () {
		return path;
	}
	
	public int getWeight() {
		return path.hashCode();
	}
	

	public String toString ()
	{
		return "<"+begin.toString() + ", " + end.toString() + ", " + getWeight() +">";
	}

	@Override
	public int compareTo(Edge<V, P> e) 
	{
		if(getWeight()  > e.getWeight())
			return 1;
		else if(getWeight() < e.getWeight())
			return -1;
		else
			return new String(begin.toString() + end.toString()).compareTo(new String(e.getbegin().toString() + e.getEnd().toString()));
	}
	
	
	
}
