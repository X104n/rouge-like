package inf101.rogue101.map;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import inf101.grid.GridDirection;
import inf101.grid.IGrid;
import inf101.grid.IMultiGrid;
import inf101.grid.Location;
import inf101.grid.MultiGrid;
import inf101.rogue101.game.IllegalMoveException;
import inf101.rogue101.objects.IActor;
import inf101.rogue101.objects.IItem;
import inf101.rogue101.objects.Wall;

public class GameMap implements IGameMap {
	/**
	 * The grid that makes up our map
	 */
	private final IMultiGrid<IItem> grid;

	public GameMap(int rows, int columns) {
		grid = new MultiGrid<>(rows, columns);
	}

	public GameMap(IGrid<IItem> inputGrid) {
		this(inputGrid.numRows(),inputGrid.numColumns());
		for(Location loc : inputGrid.locations()) {
			IItem item = inputGrid.get(loc);
			if(item != null)
				add(loc, item);
		}
	}

	/**
	 * This method adds an IItem to the given location.
	 * Since the draw method only draws one element in each cell,
	 * the first element is the one drawn
	 * In this implementation of IGameMap IItem's at same location 
	 * should be sorted such that the IItem with largest size() is first.
	 * 
	 */
	@Override
	public void add(Location loc, IItem item) {

		// do the actual adding
		List<IItem> list = grid.get(loc);
		list.add(item);
		Collections.sort(list, Collections.reverseOrder());
	}

	/**
	 * In this implementation of IGame an IActor can not go through Walls 
	 * and only one IActor can be at each cell
	 */
	@Override
	public boolean canGo(Location to) {
		return !grid.contains(to, (i) -> (i instanceof Wall || i instanceof IActor));
	}

	@Override
	public boolean hasNeighbour(Location from, GridDirection dir) {
		return grid.canGo(from,dir);
	}

	@Override
	public boolean canGo(Location from, GridDirection dir) {
		if (!grid.canGo(from,dir))
			return false;
		Location loc = from.getNeighbor(dir);
		return canGo(loc);
	}

	/**
	 *  In this implementation only 1 IActor can occupy each cell,
	 *  hence the size() of the list returned is at most 1.
	 */
	@Override
	public List<IActor> getActors(Location loc) {
		List<IActor> items = new ArrayList<>();
		for (IItem item : grid.get(loc)) {
			if (item instanceof IActor)
				items.add((IActor) item);
		}

		return items;
	}

	@Override
	public List<IItem> getAll(Location loc) {
		return Collections.unmodifiableList(grid.get(loc));
	}

	@Override
	public List<IItem> getAllModifiable(Location loc) {
		return grid.get(loc);
	}

	@Override
	public void clean(Location loc) {
		// remove any items that have health < 0:
		grid.get(loc).removeIf((item) -> {
			if (item.isDestroyed()) {
				return true;
			} else {
				return false;
			}
		});

	}

	@Override
	public int getHeight() {
		return grid.numRows();
	}

	@Override
	public List<IItem> getItems(Location loc) {
		List<IItem> items = new ArrayList<>(grid.get(loc));
		items.removeIf((i) -> i instanceof IActor);
		return items;
	}

	@Override
	public Location getLocation(IItem item) {
		for(Location loc : locations()) {
			for(IItem cur : getAll(loc)) {
				if(cur==item) {
					return loc;
				}
			}
		}
		return null;
	}

	@Override
	public Location getNeighbour(Location from, GridDirection dir) {
		if (!hasNeighbour(from, dir))
			return null;
		else
			return from.getNeighbor(dir);
	}

	@Override
	public int getWidth() {
		return grid.numColumns();
	}

	@Override
	public Location go(Location from, GridDirection dir) throws IllegalMoveException {
		if (!grid.canGo(from,dir))
			throw new IllegalMoveException("Cannot move outside map!");
		Location loc = from.getNeighbor(dir);
		if (!canGo(loc))
			throw new IllegalMoveException("Occupied!");
		return loc;
	}

	@Override
	public boolean has(Location loc, IItem target) {
		return grid.contains(loc, target);
	}

	@Override
	public boolean hasActors(Location loc) {
		return grid.contains(loc, (i) -> i instanceof IActor);
	}

	@Override
	public boolean hasItems(Location loc) {
		// true if grid cell contains an item which is not an IActor
		return grid.contains(loc, (i) -> !(i instanceof IActor));
	}

	@Override
	public boolean hasWall(Location loc) {
		return grid.contains(loc, (i) -> i instanceof Wall);
	}

	@Override
	public void remove(Location loc, IItem item) {
		grid.remove(loc, item);
	}

	@Override
	public List<Location> getNeighbourhood(Location loc, int dist) {
		if (dist < 0 || loc == null)
			throw new IllegalArgumentException();
		else if (dist == 0)
			return new ArrayList<>(); // empty!

		throw new UnsupportedOperationException();
	}
	

	@Override
	public List<GridDirection> getPossibleMoves(Location currentLocation) {
		return List.of(GridDirection.EAST); // TODO: Improve this
	}

	@Override
	public List<Location> getReachable(Location loc, int dist) {
		return new ArrayList<>();
	}

	@Override
	public Iterable<Location> locations() {
		return grid.locations();
	}

}
