package inf101.rogue101.game;

import java.util.List;

import inf101.grid.GridDirection;
import inf101.grid.Location;
import inf101.rogue101.objects.IItem;

public class GameView implements IGameView {

	private Game game;

	public GameView(Game game) {
		this.game = game;
	}
	
	@Override
	public void addItem(IItem item) {
		game.addItem(item);
	}

	@Override
	public void addItem(char sym) {
		game.addItem(sym);
	}

	@Override
	public boolean attack(GridDirection dir) {
		return game.attack(dir);
	}

	@Override
	public Location attack(GridDirection dir, IItem target) throws IllegalMoveException {
		return game.attack(dir, target);
	}

	@Override
	public boolean canGo(GridDirection dir) {
		return game.canGo(dir);
	}

	@Override
	public void displayDebug(String s) {
		game.displayDebug(s);
	}

	@Override
	public void displayMessage(String s) {
		game.displayMessage(s);
	}

	@Override
	public void displayStatus(String s) {
		game.displayStatus(s);
	}

	@Override
	public void formatDebug(String s, Object... args) {
		game.formatDebug(s, args);
	}

	@Override
	public void formatMessage(String s, Object... args) {
		game.formatMessage(s, args);
	}

	@Override
	public void formatStatus(String s, Object... args) {
		game.formatStatus(s, args);
	}

	@Override
	public IItem pickUp(IItem item) {
		return game.pickUp(item);
	}

	@Override
	public boolean drop(IItem item) {
		return game.drop(item);
	}

	@Override
	public List<GridDirection> getPossibleMoves() {
		return game.getPossibleMoves();
	}

	@Override
	public Location move(GridDirection dir) {
		return game.move(dir);
	}

	@Override
	public Location rangedAttack(GridDirection dir, IItem target) {
		return game.rangedAttack(dir, target);
	}

	@Override
	public <T extends IItem> boolean containsItem(GridDirection dir, Class<T> c) {
		return game.containsItem(dir,c);
	}

	@Override
	public List<IItem> getLocalNonActorItems() {
		return game.getLocalNonActorItems();
	}
}
