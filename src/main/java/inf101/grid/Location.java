package inf101.grid;

import java.util.ArrayList;
import java.util.Collection;

/**
 * This class represents a Location on a grid.
 * That means indices for row and column.
 * 
 * @author Martin Vatshelle - martin.vatshelle@uib.no
 *
 */
public class Location {

	public final int row;
	public final int col;
	
	/**
	 * Constructor for location, note that a Location is independent of the Grid implementation,
	 * hence can have values not corresponding to a cell in the grid.
	 * 
	 * @param row
	 * @param col
	 */
	public Location(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	public Location getNeighbor(GridDirection dir) {
		return dir.getNeighbor(this);
	}

	public int gridDistanceTo(Location loc) {
		return Math.abs(row-loc.row)+Math.abs(col-loc.col);
	}

	public Collection<Location> allNeighbours() {
		ArrayList<Location> neighbours = new ArrayList<Location>();
		for(GridDirection dir : GridDirection.EIGHT_DIRECTIONS)
			neighbours.add(this.getNeighbor(dir));
		return neighbours;
	}
	
	public boolean equals(Location loc) {
		return this.row == loc.row && this.col == loc.col;
	}
	
	@Override
	public String toString() {
		return "("+row+","+col+")";
	}
}
