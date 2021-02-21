package inf101.rogue101.objects;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import inf101.grid.GridDirection;
import inf101.grid.Location;
import inf101.rogue101.game.Game;
import inf101.rogue101.game.GameView;
import inf101.rogue101.game.IllegalMoveException;
import inf101.rogue101.map.IGameMap;
import inf101.rogue101.map.MapReader;

/**
 * @author Martin Vatshelle
 *
 */
class IActorTest {

	/**
	 * This method returns several instances of IActors
	 * Most tests are run on each of these instances.
	 * @return
	 */
	public static List<IActor> getInstances(){
		ArrayList<IActor> list = new ArrayList<IActor>();
		list.add(new Rabbit());
		list.add(new Spider());
		Spider weakSpider = new Spider();
		try {
			weakSpider.handleDamage(10);
			list.add(weakSpider);			
		} catch (NullPointerException e) {
			//Spider must have an implementation that more
			//advanced than before ant this test no longer works
		}
				
		return list;
	}

	/**
	 * This method runs a specified test on a list of different IActors
	 * @param test the method to test
	 */
	void runTest(Consumer<IActor> test) {
		for(IActor actor : getInstances())
			test.accept(actor);
	}
	
	@Test
	void testGetAttack() {
		runTest(this::testGetAttack);
	}
	
	/**
	 * Tests that attack is non-negative
	 * @param actor the actor to test
	 */
	void testGetAttack(IActor actor) {
		assertTrue(actor.getAttack()>=0);
	}

	@Test
	void testGetDamage() {
		runTest(this::testGetDamage);
	}

	/**
	 * Tests that getDamage is non-negative
	 * @param actor the actor to test
	 */
	void testGetDamage(IActor actor) {
		assertTrue(actor.getDamage()>=0);
	}

	@Test
	void testDoTurn() {
		runTest(this::testDoTurn);
	}
	/**
	 * Tests that after calling doTurn the actor is in 
	 * one of the locations given by {@link game.Game#getPossibleMoves()}
	 * @param actor
	 */
	void testDoTurn(IActor actor) {
		Game game = new Game(MapReader.BUILTIN_MAP);
		Location loc = new Location(5, 5);
		IGameMap map = game.getMap();
		map.add(loc, actor);
		game.setCurrent(loc);
		List<GridDirection> moves = game.getPossibleMoves(actor);
		ArrayList<Location> locations = new ArrayList<Location>();
		for(GridDirection dir : moves) {
			locations.add(loc.getNeighbor(dir));
		}
		try {
			actor.doTurn(new GameView(game, loc));
		} catch (IllegalMoveException e) {
			fail("You can only move once");
		}
		game.setCurrent(actor);
		assertEquals(actor, game.getCurrentActor());
		assertTrue(locations.contains(game.getCurrentLocation()));
	}

	@Test
	void testDoTurn_NoMove() {
		testDoTurn_NoMove(Spider.SYMBOL);
		testDoTurn_NoMove(Rabbit.SYMBOL);
	}
	/**
	 * Tests that when put in a situation with no possible moves,
	 * the actor stays at same position.
	 * @param symbol
	 */
	void testDoTurn_NoMove(Character symbol) {
		Game game = new Game(MapReader.mapTrap(symbol));
		Location loc = new Location(1, 1);
		IActor actor = game.setCurrent(loc);
		assertTrue(game.containsItem(loc, actor.getClass()));
		assertNotEquals(null, game.getCurrentLocation());
		try {
			actor.doTurn(new GameView(game, loc));			
		} catch (IllegalMoveException e) {
			fail("You can only move once");
		}
		game.setCurrent(loc);
		assertEquals(loc,game.getCurrentLocation());
	}
}
