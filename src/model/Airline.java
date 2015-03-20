package model;

import java.util.ArrayList;
import java.util.Collections;

import util.Edge;
import util.Graph;


public class Airline extends Graph<Airport, Path>
{
	private String name;
	
	
	/**
	 * Method constructor of an airline as a Graph
	 * @param name
	 * @param maxVertexes
	 * @param vertexes
	 */
	public Airline (String name)
	{
		super(false, 0);
		this.name = name;
	}	

	
	public String getName () {
		return name;
	}
	
	public void loadVertexes (Object[] vertexes)
	{
		this.vertexes = vertexes;
	}
	
	public ArrayList<String> getCities ()
	{
		ArrayList<String> cities = new ArrayList<String>();
		for(Edge<Airport, Path> e : getEdges())
		{
			if(!cities.contains(e.getbegin().getCity()))
				cities.add(e.getbegin().getCity());
			if(!cities.contains(e.getEnd().getCity()))
				cities.add(e.getEnd().getCity());
		}		
		Collections.sort(cities);
		return cities;
	}
	
	

	
	
	
	
}
