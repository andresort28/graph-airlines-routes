package model;



public class Airport implements Comparable<Airport>
{
	private String iata;
	private String city;
	private String name;
	
	/**
	 * 
	 * @param iata
	 * @param city
	 * @param name
	 */
	public Airport (String iata, String city, String name)
	{
		this.iata = iata;
		this.city = city;
		this.name = name;
	}

	public String getIATA() {
		return iata;
	}

	public String getCity () {
		return city;
	}
	
	public String getName () {
		return name;
	}
	
	@Override
	public String toString () {
		return this.iata;
	}

	@Override
	public int compareTo(Airport a) 
	{
		return this.iata.compareTo(a.getIATA());
	}

}
