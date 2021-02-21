package inf101.rogue101.map;

import java.util.List;
import inf101.grid.GridDirection;
import inf101.grid.Location;
import inf101.rogue101.game.IllegalMoveException;
import inf101.rogue101.objects.IItem;

/**
 * Extra map methods that are for the game class only!
 *
 * @author anya
 *
 */
public interface IGameMap extends IMapView {

	/**
	 * Add an item to the map.
	 *
	 * @param loc  A location
	 * @param item the item
	 */
	void add(Location loc, IItem item);

	/**
	 * Get a modifiable list of items
	 *
	 * @param loc
	 * @return
	 */
	List<IItem> getAllModifiable(Location loc);

	/**
	 * Remove any destroyed items at the given location (items where
	 * {@link IItem#isDestroyed()} is true)
	 *
	 * @param loc
	 */
	void clean(Location loc);

	/**
	 * Remove an item
	 * 
	 * @param loc
	 * @param item
	 */
	void remove(Location loc, IItem item);

	/**
	 * Get the neighbouring location in the given direction
	 *
	 * @param from A location
	 * @param dir  the Direction
	 * @return from's neighbour in direction dir, or null, if this would be outside
	 *         the map
	 */
	Location getNeighbour(Location from, GridDirection dir);

	/**
	 * Compute new location of an IActor moving the given direction
	 *
	 * @param from Original location
	 * @param dir  Direction we're moving in
	 * @return The new location
	 * @throws IllegalMoveException if !{@link #canGo(ILocation, GridDirection)}
	 */
	Location go(Location from, GridDirection dir) throws IllegalMoveException;

	/**
	 * Check if it's legal for an IActor to go in the given direction from the given
	 * location
	 *
	 * @param from Current location
	 * @param dir  Direction we want to move in
	 * @return True if the next location exists and isn't occupied
	 */
	boolean canGo(Location from, GridDirection dir);

	/**
	 * Gets all values of GridDirection that is a valid move from currentLocation
	 * Moves are GridDirections that lead to available grid locations.
	 * 
	 * A grid Location is unavailable if: 
	 * -The location is outside the board
	 * -The location is a Wall
	 * -Another IActor occupies the cell
	 * 
	 * @param currentLocation - location of current actor
	 * @return A list of all directions current actor may choose to go.
	 */
	List<GridDirection> getPossibleMoves(Location currentLocation);

	/**
	 * Check if a neighbour exists on the map
	 *
	 * @param from A location
	 * @param dir  A direction
	 * @return True if {@link #getNeighbour(from, dir)} would return non-null
	 */
	boolean hasNeighbour(Location from, GridDirection dir);

	/**
	 * Get all locations within i steps from the given centre
	 * 
	 * @param centre
	 * @param dist
	 * @return A list of locations, all at most i grid cells away from centre
	 */
	List<Location> getNeighbourhood(Location centre, int dist);
	
	/**
	 * In a game some cells can be unreachable because other IItem may block the way
	 * The method canGo() defines which directions it is possible to go in
	 * @param centre
	 * @param dist
	 * @return
	 */
	List<Location> getReachable(Location centre, int dist);
}
