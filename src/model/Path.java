package model;

public class Path 
{
	private int cost;
	private int distance;
	
	public Path (int distance, int cost)
	{
		this.cost = cost;
		this.distance = distance;
	}
	
	@Override
	public int hashCode ()
	{
		switch (ColombiaTravel.weight)
		{
			case ColombiaTravel.COST:
				return cost;
			case ColombiaTravel.DISTANCE:
				return distance;
		}		
		return -1;
	}
}
