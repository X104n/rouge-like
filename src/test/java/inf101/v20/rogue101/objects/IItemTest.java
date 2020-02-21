package inf101.v20.rogue101.objects;

import static org.junit.jupiter.api.Assertions.*;

import inf101.v20.rogue101.game.ItemFactory;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

class IItemTest {

	/**
	 * Tests that getArticle returns one of the three possible values.
	 * This test does not test that the grammatical choice of a vs. an is correct
	 */
	@Test
	void testGetArticle() {
		runTest(this::testGetArticle);
	}
	void testGetArticle(IItem item) {
		assertTrue(item.getArticle().equals("a") || item.getArticle().equals("an") || item.getArticle().equals(""),"for "+item);
	}

	/**
	 * This tests that newly created items have full health (are new)
	 */
	@Test
	void testNewGetCurrentHealth() {
		runTestNewOnly(this::testNewGetCurrentHealth);
	}
	void testNewGetCurrentHealth(IItem item) {
		assertTrue(item.getCurrentHealth()==item.getMaxHealth(),"for "+item);
	}
	
	/**
	 * This test tests that defence is a non negative integer
	 */
	@Test
	void testGetDefence() {
		runTest(this::testGetDefence);
	}
	void testGetDefence(IItem item) {
		assertTrue(item.getDefence()>=0,"for "+item);
	}

	/**
	 * This test checks that health status is a number between 0.0 and 1.0
	 */
	@Test
	void testGetHealthStatus() {
		runTest(this::testGetHealthStatus);
	}
	void testGetHealthStatus(IItem item) {
		if(item.getCurrentHealth() == item.getMaxHealth())
			assertTrue(item.getHealthStatus()==1.0,"for "+item);
		else if(item.getCurrentHealth() <= 0)
			assertTrue(item.getHealthStatus()>=0.0,"for "+item);
		else
			assertTrue(item.getHealthStatus()>0.0 && item.getHealthStatus()<1.0,"for "+item);
	}

	/**
	 * Tests that max health is at least 1
	 */
	@Test
	void testGetMaxHealth() {
		runTest(this::testGetMaxHealth);
	}
	void testGetMaxHealth(IItem item) {
		assertTrue(item.getMaxHealth()>=1,"Item "+item.getShortName()+" has <=0 max health");
	}

	/**
	 * Checks that this item returns a non empty name
	 */
	@Test
	void testGetName() {
		runTest(this::testGetShortName);
		runTest(this::testGetLongName);
	}
	void testGetShortName(IItem item) {
		assertFalse(item.getShortName().isEmpty(),"Short name is empty for "+item);
	}
	void testGetLongName(IItem item) {
		assertFalse(item.getLongName().isEmpty(),"Long name is empty for "+item);
	}

	/**
	 * Tests that size is larger than 0
	 */
	@Test
	void testGetSize() {
		runTest(this::testGetSize);
	}
	void testGetSize(IItem item) {
		assertTrue(item.getSize()>0,"for "+item);
	}

	/**
	 * Tests that handle damage never increases the health
	 */
	@Test
	void testHandleDamage() {
		runTest(this::testHandleDamage);
	}
	void testHandleDamage(IItem item) {
		for(IActor source : IActorTest.getInstances()) {
			int hp = item.getCurrentHealth();
			if(hp > 0) {
				int damage = item.handleDamage(source.getDamage());
				assertTrue(damage >= 0, "damage >= 0");
				assertTrue(item.getCurrentHealth()<=hp,"for "+item);
				assertTrue(damage <= hp,"Item "+item+" took "+damage+" damage with "+hp+" health");
			}
		}
	}

	@Test
	void testIsDestroyed() {
		runTest(this::testIsDestroyed);
	}
	void testIsDestroyed(IItem item) {
		assertNotEquals(item.getCurrentHealth()>=0,item.isDestroyed(),"for "+item);
	}

	@Test
	void testCompareToReverse() {
		runTest(this::testCompareToReverse);
	}
	void testCompareToReverse(IItem item) {
		for(IItem other : getTestData(false)) {
			assertEquals(-other.compareTo(item), item.compareTo(other),"for "+item);
		}
	}

	@Test
	void testCompareToCycle() {
		runTest(this::testCompareToCycle);
	}
	void testCompareToCycle(IItem item) {
		for(IItem a : getTestData(false)) {
			for(IItem b : getTestData(false)) {
				if(a.compareTo(b)==0)
					assertEquals(item.compareTo(b), item.compareTo(a),"for "+item);
				else if(item.compareTo(a)>=0 && item.compareTo(b)<=0)
					assertTrue(a.compareTo(item)<=0,"for "+item);
			}
		}
	}

	@Test
	void testItemFactoryCreatesDust() {
		assertTrue(ItemFactory.createItem('.') instanceof Dust);
	}

	/**
	 * This method runs a test on a list of IItems
	 * @param test
	 */
	void runTest(Consumer<IItem> test) {
		for(IItem item : getTestData(false))
			test.accept(item);
	}

	/**
	 * This method runs a test on a list of IItems
	 * @param test
	 */
	void runTestNewOnly(Consumer<IItem> test) {
		for(IItem item : getTestData(true))
			test.accept(item);
	}
	
	/**
	 * This method returns a list of new IItems of different types
	 * @return
	 */
	List<IItem> getTestData(boolean newItemsOnly) {
		char[] itemSymbols = { Dust.SYMBOL, Wall.SYMBOL, Carrot.SYMBOL, Rabbit.SYMBOL, Spider.SYMBOL };

		List<IItem> list = new ArrayList<>();

		try {
			for (char symbol : itemSymbols) {
				IItem item = ItemFactory.createItem(symbol);
				list.add(item);
	
				if(!newItemsOnly) {
					// create a damaged version
					item = ItemFactory.createItem(symbol);
					item.handleDamage(item.getMaxHealth() / 2);
					list.add(item);
		
					// create a dead version
					item = ItemFactory.createItem(symbol);
					item.handleDamage(item.getMaxHealth() * 2);
					list.add(item);
				}
			}
		} catch (IllegalArgumentException e) {
			
		}
		return list;
	}
	
	@Test
	void testAddedItem() {
		assertEquals(getTestData(true).size(), 7, "Glemte du å legge til et item i getTestData?");
	}
	
	@Test
	void testAddedGold() {
		for(IItem i : getTestData(false)) {
			if(i.getClass().getName().toLowerCase().contains("gold"))
				return;
		}
		fail("Glemte du å implementere Gold.java?");
	}
}
