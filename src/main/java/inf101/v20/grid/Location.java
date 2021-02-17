package inf101.v20.grid;

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

	public int gridDistanceTo(Location loc1) {
		// TODO Auto-generated method stub
		return 0;
	}

	public Collection<Location> allNeighbours() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public boolean equals(Location loc) {
		return this.row == loc.row && this.col == loc.col;
	}
	
	@Override
	public String toString() {
		return "("+row+","+col+")";
	}
}
