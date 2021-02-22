package inf101.rogue101;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import inf101.grid.Location;
import inf101.rogue101.game.Game;
import inf101.rogue101.map.MapReader;
import inf101.rogue101.objects.Carrot;
import inf101.rogue101.objects.Rabbit;

@TestInstance(Lifecycle.PER_CLASS)
class TestRabbitStrategy {

	double movesAvg = 0;
	int movesTotal = 0;
	int NUMBER_OF_SIMULATIONS = 10;

	/**
	 * This test sets up a game with lots of Carrots and 1 rabbit The game runs as
	 * long as the Rabbit has more energy. The object of the rabbit is to
	 * survive as many moves as possible.
	 * 
	 * @throws Exception
	 */
	@BeforeAll
	void setUp() throws Exception {
		movesAvg = 0;
		for (int i = 0; i < NUMBER_OF_SIMULATIONS; i++) {
			runSimulation();
		}		
		movesAvg = movesTotal / NUMBER_OF_SIMULATIONS;
	}

	private void runSimulation() {
		Game game = new Game(MapReader.CARROT_HUNT);
		game.doTurn();
		Rabbit rabbit = (Rabbit) game.getCurrentActor();
		Location previous = game.getCurrentLocation();
		int carrots = 0;
		int moves = 0;
		int turnsLeft = 1000;
		while (rabbit.getCurrentHealth() > 0 && turnsLeft>0) {
			game.doTurn();
			turnsLeft--;
			if (game.getCurrentActor() == rabbit) {
				if (game.containsItem(game.getCurrentLocation(),Carrot.class)) {
					carrots++;
				}
				if (!game.getCurrentLocation().equals(previous)) {
					moves++;
					previous = game.getCurrentLocation();
				}

			} else {
				System.err.println("Strange, how has other actors entered the game?");
				fail("Not allowed to reproduce");
			}
			assertTrue(moves<=5*carrots+10,"Rabbit is moving without burning energy. Made "+moves+" moves and ate "+carrots+" carrots.");
		}
		movesTotal += moves;
	}

	@Test
	void level1() {
		assertTrue(movesAvg > 10,"Rabbit made "+movesAvg+" moves.");
	}

	@Test
	void level2() {
		assertTrue(movesAvg > 20,"Rabbit made "+movesAvg+" moves.");
	}

	@Test
	void level3() {
		assertTrue(movesAvg > 30,"Rabbit made "+movesAvg+" moves.");
	}

	@Test
	void level4() {
		assertTrue(movesAvg > 40,"Rabbit made "+movesAvg+" moves.");
	}

	@Test
	void level5() {
		assertTrue(movesAvg > 50,"Rabbit made "+movesAvg+" moves.");
	}

	@Test
	void level6() {
		assertTrue(movesAvg > 60,"Rabbit made "+movesAvg+" moves.");
	}

	@Test
	void level7() {
		assertTrue(movesAvg > 70,"Rabbit made "+movesAvg+" moves.");
	}

	@Test
	void level8() {
		assertTrue(movesAvg > 80,"Rabbit made "+movesAvg+" moves.");
	}

	@Test
	void level9() {
		assertTrue(movesAvg > 90,"Rabbit made "+movesAvg+" moves.");
	}

	@Test
	void level10() {
		assertTrue(movesAvg > 100,"Rabbit made "+movesAvg+" moves.");
	}

	@Test
	void level11() {
		assertTrue(movesAvg > 200);
	}
	@Test
	void level12() {
		assertTrue(movesAvg > 300,"Num moves was "+movesAvg);
	}

}
