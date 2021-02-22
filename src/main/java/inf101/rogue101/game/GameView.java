package inf101.rogue101.game;

import java.util.List;
import java.util.Optional;

import inf101.grid.GridDirection;
import inf101.grid.Location;
import inf101.rogue101.objects.IActor;
import inf101.rogue101.objects.IItem;

/**
 * This class is very simple, it just passes method calls to a game object.
 * Since Game implements GameView we could have just given a Game to the IActors
 * with a variable type IGameView, but then type casting this object to Game gets tempting.
 * 
 * Each gameView is accosieted with a current location.
 * When passed to an IActor this current location should be sat to the location at which this IActor is.
 * 
 * @author Martin Vatshelle - martin.vatshelle@uib.no
 *
 */
public class GameView implements IGameView {

	private IGame game;
	private Location currentLocation;
	private IActor currentActor;
	
	public GameView(Game game,Location currentLocation) {
		this.game = game;
	}

	@Override
	public boolean attack(GridDirection dir) {
		return game.attack(currentActor, dir);
	}

	@Override
	public boolean attack(GridDirection dir, IItem target) throws IllegalMoveException {
		Location before = game.getCurrentLocation();
		Location after = game.attack(currentActor, dir, target);
		return !before.equals(after);
	}

	@Override
	public boolean canGo(GridDirection dir) {
		return game.canGo(currentActor, dir);
	}

	@Override
	public void displayDebug(String s) {
		game.getIMessageView().displayDebug(s);
	}

	@Override
	public void displayMessage(String s) {
		game.getIMessageView().displayMessage(s);
	}

	@Override
	public void displayStatus(String s) {
		game.getIMessageView().displayStatus(s);
	}

	@Override
	public void formatDebug(String s, Object... args) {
		game.getIMessageView().formatDebug(s, args);
	}

	@Override
	public void formatMessage(String s, Object... args) {
		game.getIMessageView().formatMessage(s, args);
	}

	@Override
	public void formatStatus(String s, Object... args) {
		game.getIMessageView().formatStatus(s, args);
	}

	@Override
	public Optional<IItem> pickUp(IItem item) {
		
		return game.pickUpItem(currentActor, item);
	}

	@Override
	public boolean drop(IItem item) {
		return game.addItem(item, currentLocation);
	}

	@Override
	public List<GridDirection> getPossibleMoves() {
		return game.getPossibleMoves(currentActor);
	}

	@Override
	public boolean move(GridDirection dir) {
		Location before = game.getCurrentLocation();
		Location after = game.move(currentActor, dir);
		return !before.equals(after);
	}

	@Override
	public Location rangedAttack(GridDirection dir, IItem target) {
		return game.rangedAttack(currentActor, dir, target);
	}

	@Override
	public <T extends IItem> boolean containsItem(GridDirection dir, Class<T> c) {
		return game.getMap().containsItem(currentLocation.getNeighbor(dir),c);
	}

	@Override
	public List<IItem> getLocalNonActorItems() {
		return game.getMap().getItems(currentLocation);
	}

	@Override
	public List<Location> getNeighbourhood(int dist) {
		return game.getMap().getNeighbourhood(currentLocation, dist);
	}
}
