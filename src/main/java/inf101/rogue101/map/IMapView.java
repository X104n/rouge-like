package inf101.rogue101.map;

import java.util.List;

import inf101.grid.GridDirection;
import inf101.grid.Location;
import inf101.rogue101.game.IllegalMoveException;
import inf101.rogue101.objects.IActor;
import inf101.rogue101.objects.IItem;
import inf101.rogue101.objects.Wall;

public interface IMapView {
	/**
	 * Add an item to the map.
	 *
	 * @param loc  A location
	 * @param item the item
	 */
	void add(Location loc, IItem item);

	/**
	 * Check if it's legal for an IActor to go into the given location
	 *
	 * @param to A location
	 * @return True if the location isn't already occupied
	 */
	boolean canGo(Location to);

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
	 * Get all IActors at the given location
	 * <p>
	 * The returned list either can't be modified, or modifying it won't affect the
	 * map.
	 *
	 * @param loc
	 * @return A list of actors
	 */
	List<IActor> getActors(Location loc);

	/**
	 * Get all items (both IActors and other IItems) at the given location
	 * <p>
	 * The returned list either can't be modified, or modifying it won't affect the
	 * map.
	 *
	 * @param loc
	 * @return A list of items
	 */
	List<IItem> getAll(Location loc);

	/**
	 * Get all non-IActor items at the given location
	 * <p>
	 * The returned list either can't be modified, or modifying it won't affect the
	 * map.
	 *
	 * @param loc
	 * @return A list of items, non of which are instanceof IActor
	 */
	List<IItem> getItems(Location loc);

	/**
	 * @return Height of the map, same as
	 *         {@link #getArea()}.{@link IArea#getHeight()}
	 */
	int getHeight();

	/**
	 * @return Width of the map, same as {@link #getArea()}.{@link IArea#getWidth()}
	 */
	int getWidth();

	/**
	 * Find location of an item
	 *
	 * @param item The item
	 * @return It's location, or <code>null</code> if it's not on the map
	 */
	Location getLocation(IItem item);

	/**
	 * Translate (x,y)-coordinates to ILocation
	 *
	 * @param x
	 * @param y
	 * @return an ILocation
	 * @throws IndexOutOfBoundsException if (x,y) is outside {@link #getArea()}
	 */
	Location getLocation(int x, int y);

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
	 * Check if an item exists at a location
	 *
	 * @param loc    The location
	 * @param target The item we're interested in
	 * @return True if target would appear in {@link #getAll(loc)}
	 */
	boolean has(Location loc, IItem target);

	/**
	 * Check for actors.
	 *
	 * @param loc
	 * @return True if {@link #getActors(loc)} would be non-empty
	 */
	boolean hasActors(Location loc);

	/**
	 * Check for non-actors
	 *
	 * @param loc
	 * @return True if {@link #getItem(loc)} would be non-empty
	 */
	boolean hasItems(Location loc);

	/**
	 * Check for walls
	 *
	 * @param loc
	 * @return True if there is a wall at the given location ({@link #getAll(loc)}
	 *         would contain an instance of {@link Wall})
	 */
	boolean hasWall(Location loc);

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
