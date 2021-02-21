package inf101.rogue101.game;

import java.util.List;
import java.util.Random;

import inf101.grid.GridDirection;
import inf101.grid.Location;
import inf101.rogue101.map.IMapView;
import inf101.rogue101.objects.IActor;
import inf101.rogue101.objects.IItem;
import inf101.rogue101.objects.IPlayer;

/**
 * Game interface
 * <p>
 * The game has a map and a current {@link IActor} (the player or non-player
 * whose turn it currently is). The game also knows the current location of the
 * actor. Most methods that deal with the map will use this current location –
 * they are meant to be used by the current actor for exploring or interacting
 * with its surroundings.
 * <p>
 * In other words, you should avoid calling most of these methods if you're not
 * the current actor. You know you're the current actor when you're inside your
 * {@link IPlayer#keyPressed()} or {@link INonPlayer#doTurn()} method.
 *
 * @author anya, Anna Eilertsen - anna.eilertsen@uib.no
 *
 */
public interface IGame extends IGameView{


	/**
	 * @return The height of the map
	 */
	int getHeight();


	/**
	 * Get the current actor's location.
	 * <p>
	 * You should only call this from an IActor that is currently doing its move.
	 *
	 * @return Location of the current actor
	 */
	Location getLocation();

	/**
	 * Get the current actor
	 * <p>
	 * You can check if it's your move by doing game.getActor()==this.
	 *
	 * @return The current actor (i.e., the (IPlayer/INonPlayer) player who's turn
	 *         it currently is)
	 */
	IActor getActor();

	/**
	 * Get the neighbouring map location in direction <code>dir</code>
	 * <p>
	 * Same as <code>getLocation().go(dir)</code>
	 *
	 * @param dir A direction
	 * @return A location, or <code>null</code> if the location would be outside the
	 *         map
	 */
	Location getLocation(GridDirection dir);

	/**
	 * @return The map
	 */
	IMapView getMap();

	/**
	 * Get a list of all locations that are visible from the current location.
	 * <p>
	 * The location list is sorted so that nearby locations come earlier in the
	 * list. E.g., if <code>l = getVisible()<code> and <code>i < j</code>then
	 * <code>getLocation().gridDistanceTo(l.get(i)) < getLocation().gridDistanceTo(l.get(j))</code>
	 *
	 * @return A list of grid cells visible from the {@link #getLocation()}
	 */
	List<Location> getReachable();

	/**
	 * @return Width of the map
	 */
	int getWidth();


	/**
	 * Checks if a location contains an actor of a specific class
	 * @param <T> class of the actor
	 * @param loc the location
	 * @param c class of the actor
	 * @return true if location contains an actor of that class 
	 */
	<T extends IActor> boolean containsActor(Location loc, Class<T> c);


}
