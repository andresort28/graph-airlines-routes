package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

import util.AlgorithmsGraph;
import util.Edge;
import util.Graph;

public class ColombiaTravel 
{
	public final static int COST = 0;
	public final static int DISTANCE = 1;
	public final static String AIRPORTS = "./aeropuertos.txt"; 
	public final static String AIRLINES = "./aerolineas.txt";
	
	public  static int weight;
	private int numAirports;
	private int numAirlines;
	private Airline[] airlines;
	private Airport[] airports;
	private AlgorithmsGraph<Airport, Path> algorithms;
	
	
	public ColombiaTravel ()
	{
		this.airlines = null;
		this.airports = null;
		weight = DISTANCE;
		algorithms = new AlgorithmsGraph<>();
	}
	
	public int getNumAirports () {
		return numAirports;
	}
	
	public int getNumAirlines () {
		return numAirlines;
	}
	
	public void changeWeight (int w)
	{
		switch (w)
		{
			case COST:
				weight = COST;
				break;
			case DISTANCE:
				weight = DISTANCE;
				break;
		}	
	}
	
	public Airline[] getAirlines () {
		return airlines;
	}
	
	public Airport[] getAirports () {
		return airports;
	}
	
	//- 1--------------------------------------------------------------------------------------------------------------
	
	public ArrayList<String> reachableCities (String city, Airline airline)
	{
		ArrayList<String> cities  = new ArrayList<String>();
		ArrayList<String> iatas = getCodesIata(city);
		for(String i : iatas)
		{
			ArrayList<Airport> possibles = algorithms.breadthFirstSearch(airline, airline.getVertex(airline.searchVertex(i)));
			for(Airport a : possibles)
			{
				if(!a.getCity().equalsIgnoreCase(city) && !cities.contains(a.getCity()))
					cities.add(a.getCity());
			}		
		}	
		return cities;
	}
	
	
	public ArrayList<String> getCodesIata (String city)
	{
		ArrayList<String> codes = new ArrayList<String>();
		for (int i = 0; i < airports.length; i++) 
		{
			Airport air = (Airport)airports[i];			
			if(air.getCity().equalsIgnoreCase(city))
				codes.add(air.getIATA());
		}
		return codes;
	}
	
	
	//- 2--------------------------------------------------------------------------------------------------------------
	
	public ArrayList<Edge<Airport, Path>> getShortestPath_MST (Airline airline, int weight) throws Exception
	{
		changeWeight(weight);
		Graph<Airport, Path> g = algorithms.getMST_Kruskal(airline);
		return avoidRepeats(g.getEdges());
	}
	
	public ArrayList<Edge<Airport, Path>> avoidRepeats (ArrayList<Edge<Airport, Path>> list)
	{
		ArrayList<Edge<Airport, Path>> list2 = new ArrayList<Edge<Airport, Path>>();
		ArrayList<String> visited = new ArrayList<String>();
		for(Edge<Airport, Path> e : list)
		{
			String a = e.getbegin().toString() + e.getEnd().toString();
			String b = e.getEnd().toString() + e.getbegin().toString();			
			if(!visited.contains(a) && !visited.contains(b))
			{
				list2.add(e);
				visited.add(a);
				visited.add(b);
			}
		}		
		return list2;
	}
	
	//- 3--------------------------------------------------------------------------------------------------------------
	
	public ArrayList<Edge<Airport, Path>> getCheapestPath1 (String city, String city2, Airline airline)
	{
		int[][][] fw = algorithms.floydWarshall(airline);
		return getCheapestPath2(city, city2, airline, fw, COST);
	}
	
	public ArrayList<Edge<Airport, Path>> getCheapestPath2 (String city, String city2, Airline airline, int[][][] fw, int w)
	{
		if(city.equals(city2))
			return new ArrayList<Edge<Airport, Path>>();
		
		changeWeight(w);
		int cost = Integer.MAX_VALUE;
		Airport[] path = new Airport[]{null, null};
		ArrayList<String> iatas1 = getCodesIata(city);
		ArrayList<String> iatas2 = getCodesIata(city2);	
		for(String i : iatas1)
		{
			Airport a1 = airline.getVertex(airline.searchVertex(i));
			for(String j : iatas2)
			{
				Airport a2 = airline.getVertex(airline.searchVertex(j));
				int d = algorithms.getShortestDistance_FloydWarshall(airline, a1, a2, fw);
				if( d < cost )
				{
					cost = d;
					path = new Airport[]{a1, a2};
				}
			}
		}
		if(path[0] == null && path[1] == null)
			return new ArrayList<Edge<Airport, Path>>();		
		return algorithms.getShortestPath_FloydWarshall(airline, path[0], path[1], fw);
	}
	
	

	
	public ArrayList<Object[]> getBestPath_AllCities (Airline airline, int w)
	{
		changeWeight(w);
		int[][][] fw = algorithms.floydWarshall(airline);
		ArrayList<Object[]> result = new ArrayList<Object[]>();
		ArrayList<String> list = airline.getCities();
		for (int i = 0; i < list.size(); i++) 
		{
			String c1 = list.get(i);
			for (int j = 0; j < list.size(); j++) 
			{			
				if(i != j)
				{
					String c2 = list.get(j);
					Object[] o = new Object[]{ c1, c2, getCheapestPath2(c1, c2, airline, fw, w)  };
					result.add(o);
				}
			}
		}
		return result; 
	}
	
	/**
	 * The method sum the weight of all edges. It is totally necesary that there are not repeats edges (No directed graph)
	 * @param list
	 * @return
	 */
	public int sumWeight (ArrayList<Edge<Airport, Path>> list)
	{
		int n = 0;
		for(Edge<Airport, Path> e : list)
		{
			n += e.getWeight();
		}
		return n;
	}
	

	
	/**
	 * Method that make a binary search and return the position of the element in the array, otherwise return -1.
	 * @param airport
	 * @param list
	 * @return
	 */
	public int binarySearch (Airport airport, Airport[]  list)
	{
		int begin = 0;
		int end = numAirports-1;
		int pos;
		while(begin <= end)
		{
			pos = (int)((begin+end)/2);
			if(airport.toString().equalsIgnoreCase(list[pos].toString()))
				return pos;
			else if (airport.compareTo(list[pos]) < 0)
				end = pos-1;
			else
				begin = pos+1;
		}
		return -1;
	}
	
	
	//----------------- Load Application -------
	
	public void load () throws Exception
	{
		//Load the airports
		BufferedReader in = new BufferedReader(new FileReader(AIRPORTS));
		numAirports = Integer.valueOf(in.readLine());
		StringTokenizer tokens;
		airports = new Airport[numAirports];
		for (int i = 0; i < airports.length; i++) 
		{
			tokens = new StringTokenizer(in.readLine());
			String iata = tokens.nextToken();
			String city = tokens.nextToken();
			String name = tokens.nextToken();
			while (tokens.hasMoreElements())
			{
				name += " " + tokens.nextToken();
			}
			airports[i] = new Airport(iata, city, name);
		}
		Arrays.sort(airports);
		in.close();
		
		//Load the airlines
		in = new BufferedReader(new FileReader(AIRLINES));
		numAirlines = Integer.valueOf(in.readLine());
		airlines = new Airline[numAirlines];
		
		for (int i = 0; i < airlines.length; i++) 
		{
			tokens = new StringTokenizer(in.readLine());
			String name = tokens.nextToken();
			int numEdges = Integer.valueOf(tokens.nextToken());
			Airline airline = new Airline(name);		
			airline.loadGraphManually(numAirports, numEdges);
			airline.loadVertexes(airports);
			for (int j = 0; j < numEdges; j++) 
			{
				tokens = new StringTokenizer(in.readLine());
				String u = tokens.nextToken();
				String v = tokens.nextToken();
				Path p = new Path(Integer.valueOf(tokens.nextToken()), Integer.valueOf(tokens.nextToken()));
				airline.addEdge(airline.getVertex(airline.searchVertex(u)), airline.getVertex(airline.searchVertex(v)), p);
			}
			airlines[i] = airline;
//			System.out.println(airline.getName());
//			airline.printMatrixAdjacencies();
		}
		in.close();		
	}
	
	

}
