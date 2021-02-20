package inf101.rogue101.map;

import java.util.List;
import java.util.Set;

import inf101.grid.GridDirection;
import inf101.grid.Location;
import inf101.rogue101.objects.IItem;

/**
 * Extra map methods that are for the game class only!
 *
 * @author anya
 *
 */
public interface IGameMap extends IMapView {

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
	 * Gets all values of GridDirection that is a valid move from currentLocation
	 * @param currentLocation
	 * @return
	 */
	List<GridDirection> getPossibleMoves(Location currentLocation);

	Iterable<Location> locations();

}
