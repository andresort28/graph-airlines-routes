package view;

import java.util.ArrayList;

import util.Edge;

import model.Airline;
import model.Airport;
import model.ColombiaTravel;
import model.Path;

public class InterfaceTravel 
{
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception
	{
		ColombiaTravel app = new ColombiaTravel();
		app.load();
		Airline a = app.getAirlines()[1];
		System.out.println("Airline : " + a.getName());
		System.out.println("-------------------------------------------------------");
		String city1 = "Cali";
		String city2 = "Bogotá";
		System.out.println("Ciudades a las cuales llega la aerolinea " + a.getName() + " son: " + a.getCities());
		System.out.println("\n\n");
		
		
		// - Point 1 -------------------------------------------------------------------------------
		System.out.println("1 Punto");
		System.out.println("Ciudades alcanzables de la ciudad " + city1 + " por la Aerolinea " + a.getName() + " son: ");
		System.out.println(app.reachableCities(city1, a));
		System.out.println("\n\n");
		
		
		// - Point 2 -------------------------------------------------------------------------------
		System.out.println("2 Punto");
		System.out.println("Para recorrer todas las ciudades, por la ruta MAS CORTA, por la Aerolinea " + a.getName() + "  estas son las opciones de rutas: ");		
		System.out.println(app.getShortestPath_MST(a, ColombiaTravel.DISTANCE));
		System.out.println("\n\n");
		
		
		
		// - Point 3 -------------------------------------------------------------------------------
		System.out.println("3 Punto");
		System.out.println("La ruta mas economica entre la ciudad " + city1 + " y la ciudad " + city2 + ", por la aerolinea" + a.getName() + " es:");
		System.out.println(app.getCheapestPath1(city1, city2, a));
		System.out.println("\n\n");
		
		
		
		// - Point 4 -------------------------------------------------------------------------------
		System.out.println("4 Punto");
		System.out.println("Las rutas mas economicas de la aerolinea " + a.getName() + " de todo destino a todo destino son: ");	
		ArrayList<Object[]> list = app.getBestPath_AllCities(a, app.COST);
		for(Object[] o : list)
		{
			System.out.println((String)o[0] + " a " + (String)o[1] + "    " + (ArrayList<Edge<Airport, Path>>)o[2]);
		}		
		System.out.println("\n\n");
	}

}
