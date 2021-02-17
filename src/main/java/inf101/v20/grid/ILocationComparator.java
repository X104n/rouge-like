package inf101.v20.grid;

import java.util.Comparator;

public class ILocationComparator implements Comparator<Location> {
	
	private Location loc;

	public ILocationComparator(Location loc) {
		this.loc = loc;
	}

	@Override
	public int compare(Location loc1, Location loc2) {
		int dist1 = loc.gridDistanceTo(loc1);
		int dist2 = loc.gridDistanceTo(loc2);
		return dist1 - dist2;
	}

}
