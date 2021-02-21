package inf101.rogue101.game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import inf101.grid.GridDirection;
import inf101.grid.IGrid;
import inf101.grid.Location;
import inf101.rogue101.map.GameMap;
import inf101.rogue101.map.IGameMap;
import inf101.rogue101.map.IMapView;
import inf101.rogue101.map.MapReader;
import inf101.rogue101.objects.Amulet;
import inf101.rogue101.objects.IActor;
import inf101.rogue101.objects.IItem;
import inf101.rogue101.objects.IPlayer;
import inf101.rogue101.objects.Portal;
import javafx.scene.input.KeyCode;

/**
 * A game implementation for Rogue 101
 * 
 * @author anya
 * @author Anna Eilertsen - anna.eilertsen@uib.no
 * @author Martin Vatshelle - martin.vatshelle@uib.no
 *
 */
public class Game implements IGame {
	/**
	 * All the IActors that have things left to do this turn
	 */
	private List<IActor> actors = new ArrayList<IActor>();

	/**
	 * This game's random generator
	 */
	private Random random = new Random();
	
	/**
	 * The game map. {@link IGameMap} gives us a few more details than
	 * {@link IMapView} (write access to item lists); the game needs this but
	 * individual items don't.
	 */
	private IGameMap map;
	
	/**
	 * The actor who gets to perform an action this turn
	 */
	private IActor currentActor;
	
	/**
	 * The current location of the current actor
	 */
	private Location currentLocation;
	
	private int movePoints = 0;
	private int numPlayers = 0;
	GameGraphics graphics;

	public Game() {
		this(getDefaultMap());
	}

	public Game(String mapString) {
		this(MapReader.loadString(mapString));
	}

	public Game(IGrid<IItem> inputGrid) {
		this.map = new GameMap(inputGrid);
		this.graphics = new GameGraphics();
	}
	
	public Game(GameGraphics graphics) {
		this.graphics = graphics;
		this.map = new GameMap(getDefaultMap());
	}


	static IGrid<IItem> getDefaultMap() {
		// NOTE: in a more realistic situation, we will have multiple levels (one map
		// per level), and (at least for a Roguelike game) the levels should be
		// generated
		//
		// inputGrid will be filled with characters indicating what (if anything)
		// should be placed at that map square

		IGrid<IItem> grid;
		try {
			grid = MapReader.loadFile("maps/level1.txt");
		} catch (Exception e) {
			System.err.println("Map not found – falling back to builtin map");
			grid = MapReader.loadString(MapReader.BUILTIN_MAP);
		} 
		return grid;
	}

	@Override
	public void addItem(IItem item) {
		map.add(currentLocation, item);
		// also keep track of whether we need to redraw this cell
		graphics.reportChange(currentLocation);
	}

	@Override
	public void addItem(char symbol) {
		IItem item = ItemFactory.createItem(symbol);
		if (item != null) {
			map.add(currentLocation, item);
			// also keep track of whether we need to redraw this cell
			graphics.reportChange(currentLocation);
		}
	}

	public boolean attack(GridDirection dir) {
		Location loc = map.getNeighbour(getLocation(), dir);
		graphics.reportChange(loc);
		graphics.reportChange(currentLocation);
		List<IActor> actorsOnTargetLoc = map.getActors(loc);
		if (actorsOnTargetLoc.isEmpty())
			return true;
		actorsOnTargetLoc.sort(null);
		IActor target = actorsOnTargetLoc.get(0);
		if (attack(dir, target).equals(currentLocation))
			return false;
		return true;
	}

	@Override
	public Location attack(GridDirection dir, IItem target) {
		Location loc = map.getNeighbour(getLocation(), dir);
		if (!map.has(loc, target))
			throw new IllegalMoveException("Target isn't there!");

		graphics.reportChange(loc);
		graphics.reportChange(currentLocation);

		if (Game.attackSucceeds(currentActor, target))
			target.handleDamage(currentActor.getDamage());

		map.clean(loc);
		if (target.isDestroyed()) {
			return move(dir);
		} else {
			movePoints--;
			return currentLocation;
		}
	}

	/**
	 * Begin a new game turn, or continue to the previous turn
	 *
	 * @return True if the game should wait for more user input
	 */
	public boolean doTurn() {
		do {
			if (actors.isEmpty()) {
				// System.err.println("new turn: " + turnCount++);

				// no one in the queue, we're starting a new turn!
				// first collect all the actors:
				beginTurn();
			}

			// process actors one by one; for the IPlayer, we return and wait for keypresses
			while (!actors.isEmpty()) {
				// get the next player or non-player in the queue
				currentActor = actors.remove(0);
				currentLocation = map.getLocation(currentActor);
				graphics.reportChange(currentLocation);
				if (currentActor.isDestroyed()) { // skip if it's dead
					continue;
				}

				if (currentLocation == null) {
					displayDebug("doTurn(): Whoops! Actor has disappeared from the map: " + currentActor);
				}
				movePoints = 1; // everyone gets to do one thing

				if (currentActor instanceof IPlayer) {
					if (currentActor.getCurrentHealth() <= 0) {
						// a dead human player gets removed from the game
						// TODO: you might want to be more clever here
						displayStatus(currentActor.getShortName() + " died.");
						// map.remove(currentLocation, currentActor);
						// currentActor = null;
						// currentLocation = null;
					} else {
						currentActor.doTurn(this);
						Location newLocation = map.getLocation(currentActor);
						graphics.reportChange(newLocation);
					}
					// For the human player, we need to wait for input, so we just return.
					// Further keypresses will cause keyPressed() to be called, and once the human
					// makes a move, it'll lose its movement point and doTurn() will be called again
					//
					// NOTE: currentActor and currentLocation are set to the IPlayer (above),
					// so the game remembers who the player is whenever new keypresses occur. This
					// is also how e.g., getLocalItems() work – the game always keeps track of
					// whose turn it is.
					return true;
				} else if (currentActor instanceof IActor) {

					try {
						// computer-controlled players do their stuff right away
						currentActor.doTurn(this);
					} catch (Exception e) {
						// actor did something wrong
						// do nothing, leave this IActor
					}
					Location newLocation = map.getLocation(currentActor);
					graphics.reportChange(newLocation);
					
					// remove any dead items from current location
					map.clean(currentLocation);
					map.clean(newLocation);
				} else {
					displayDebug("doTurn(): Hmm, this is a very strange actor: " + currentActor);
				}
			}

		} while (numPlayers > 0); // we can safely repeat if we have players, since we'll return (and break out of
		// the loop) once we hit the player

		return true;
	}

	/**
	 * Go through the map and collect all the actors.
	 */
	private void beginTurn() {
		numPlayers = 0;

		for(Location loc : map.locations()) {
			List<IItem> list = map.getAllModifiable(loc);
			Iterator<IItem> li = list.iterator(); // manual iterator lets us remove() items
			List<IItem> toRemove = new ArrayList<IItem>();
			while (li.hasNext()) { // this is what "for(IItem item : list)" looks like on the inside
				IItem item = li.next();
				if (item.getCurrentHealth() < 0) {
					// normally, we expect these things to be removed when they are destroyed, so
					// this shouldn't happen
					formatDebug("beginTurn(): found and removed leftover destroyed item %s '%s' at %s%n",
							item.getLongName(), item.getGraphicTextSymbol(), loc);

					toRemove.add(item);
				} else if (item instanceof IPlayer) {
					actors.add(0, (IPlayer) item); // we let the human player go first
					numPlayers++;
				} else if (item instanceof IActor) {
					actors.add((IActor) item); // add other actors to the end of the list
				}
			}		
		}
	}

	@Override
	public boolean canGo(GridDirection dir) {
		Location location = getLocation(dir);
		if (containsActor(location, Portal.class) && currentActor instanceof IPlayer) {
			IPlayer currentPlayer = (IPlayer) currentActor;
			Portal portal = (Portal) map.getActors(location).get(0);
			if (currentPlayer.hasItem(Amulet.getInstance())) {
				displayStatus("Congratulations, you reached the portal with the " + Amulet.getInstance().getShortName()
						+ " and won the game!");
				portal.open();
				map.remove(location, portal);
				return true;
			}
		}
		return map.canGo(currentLocation, dir);
	}

	@Override
	public void displayDebug(String s) {
		graphics.displayDebug(s);
	}

	@Override
	public void displayMessage(String s) {
		graphics.displayMessage(s);
	}

	@Override
	public void displayStatus(String s) {
		graphics.displayStatus(s);
	}

	public void draw() {
		graphics.drawDirty(map);
	}

	@Override
	public boolean drop(IItem item) {
		if (item != null) {
			map.add(currentLocation, item);
			return true;
		} else
			return false;
	}

	@Override
	public void formatDebug(String s, Object... args) {
		graphics.formatDebug(s, args);
	}

	@Override
	public void formatMessage(String s, Object... args) {
		graphics.formatMessage(s, args);
	}

	@Override
	public void formatStatus(String s, Object... args) {
		graphics.formatStatus(s, args);
	}

	@Override
	public int getHeight() {
		return map.getHeight();
	}

	@Override
	public List<IItem> getLocalNonActorItems() {
		return map.getItems(currentLocation);
	}

	@Override
	public Location getLocation() {
		return currentLocation;
	}

	public Location getLocation(IItem item) {
		for(Location loc : map.locations()) {
			for(IItem cur : map.getAll(loc)) {
				if(item == cur) {
					return loc;
				}
			}
		}
		return null;
	}

	@Override
	public Location getLocation(GridDirection dir) {
		if (this.map.canGo(currentLocation,dir))
			return currentLocation.getNeighbor(dir);
		else
			return null;
	}

	/**
	 * Return the game map. {@link IGameMap} gives us a few more details than
	 * {@link IMapView} (write access to item lists); the game needs this but
	 * individual items don't.
	 */
	@Override
	public IMapView getMap() {
		return map;
	}

	@Override
	public List<GridDirection> getPossibleMoves() {
		return map.getPossibleMoves(currentLocation);
	}

	@Override
	public List<Location> getReachable() {
		return map.getReachable(currentLocation, 5);
	}

	@Override
	public int getWidth() {
		return map.getWidth();
	}

	public void keyPressed(KeyCode code) {
		// only an IPlayer/human can handle keypresses, and only if it's the human's
		// turn
		// NB: all codes are for the large letter, even if a small one was pushed
		if (currentActor instanceof IPlayer) {
			if (currentActor.getCurrentHealth() <= 0) {
				graphics.displayMessage("Sorry, you're dead!");
			} else {
				((IPlayer) currentActor).keyPressed(this, code); // do your thing
			}
		}
	}

	@Override
	public Location move(GridDirection dir) {
		if (movePoints < 1)
			throw new IllegalMoveException("You're out of moves!");
		if (!canGo(dir))
			throw new IllegalMoveException("You cannot go there!");
		Location newLoc = map.go(currentLocation, dir);
		map.remove(currentLocation, currentActor);
		map.add(newLoc, currentActor);
		currentLocation = newLoc;
		movePoints--;
		return currentLocation;
	}

	@Override
	public IItem pickUp(IItem item) {
		if (item == null || !map.has(currentLocation, item))
			return null;
		if (!Game.pickUpSucceeds(currentActor, item))
			return null;
		map.remove(currentLocation, item);
		return item;
	}

	@Override
	public Location rangedAttack(GridDirection dir, IItem target) {
		return currentLocation;
	}

	@Override
	public IActor getActor() {
		return currentActor;
	}

	public Location setCurrent(IActor actor) {
		currentLocation = map.getLocation(actor);
		if (currentLocation != null) {
			currentActor = actor;
			movePoints = 1;
		}
		return currentLocation;
	}

	public IActor setCurrent(Location loc) {
		List<IActor> list = map.getActors(loc);
		if (!list.isEmpty()) {
			currentActor = list.get(0);
			currentLocation = loc;
			movePoints = 1;
		}
		return currentActor;
	}

	@Override
	public <T extends IActor> boolean containsActor(Location loc, Class<T> c) {
		for (IActor actor : map.getActors(loc)) {
			if (c.isInstance(actor))
				return true;
		}
		return false;
	}

	public <T extends IItem> boolean containsItem(Class<T> c) {
		List<IItem> items = getLocalNonActorItems();
		for (IItem item : items) {
			if (c.isInstance(item))
				return true;
		}
		return false;
	}

	@Override
	public <T extends IItem> boolean containsItem(GridDirection dir, Class<T> c) {
		Location loc = currentLocation.getNeighbor(dir);
		for (IItem item : map.getItems(loc)) {
			if (c.isInstance(item))
				return true;
		}
		return false;
	}

	/**
	 * An {@link IActor} succeeds at picking up a target item if the actors attack score 
	 * is strictly larger than the target's size
	 * 
	 * @param actor the {@link IActor} trying to pick up the item 
	 * @param item   the {@link IItem} to be picked up
	 * @return true if picking up succeeds, false otherwise
	 */
	public static boolean pickUpSucceeds(IActor actor, IItem item) {
		return actor.getAttack() > item.getSize();
	}

	/**
	 * An attack succeeds if the attacker's attack score is strictly larger than,
	 * the target's defense score.
	 * 
	 * @param attacker the attacking {@link IActor}
	 * @param target   the target {@link IItem}
	 * @return true if the attack succeeds, false otherwise
	 */
	public static boolean attackSucceeds(IActor attacker, IItem target) {
		return attacker.getAttack() > target.getDefence();
	}
}
