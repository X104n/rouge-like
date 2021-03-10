package inf101.rogue101.objects;

import java.util.*;

import inf101.grid.GridDirection;
import inf101.rogue101.game.IGameView;
import javafx.scene.input.KeyCode;
/**
 * En spiller i Rogue 101
 *
 * Spilleren navigerer labyrinten og slåss med monster. For å komme seg ut
 * og vinne spille må spilleren gå gjennom portalen; for å åpne portalen
 * må den finne amuletten og bære den med seg.
 *
 * På veien kan den plukke opp gull og slåss med monster
 *
 * @author Anna Eilertsen - anna.eilertsen@uib.no
 *
 */
public class Player implements IPlayer {
	/**
	 * char representation of this type
	 */
	public static final char SYMBOL = '@';
	private static final int MAXHEALTH = 100;
	private int attack;
	private int defence;
	private int damage;
	private int hp;
	private String name;

	public Player() {
		attack = 10;
		defence = 2;
		damage = 3;
		hp = Player.MAXHEALTH;
		name = System.getProperty("user.name");
	}

	private List<IItem> inventory = new ArrayList<>();

	@Override
	public int getAttack() {
		return attack;
	}

	@Override
	public int getDamage() {
		return damage;
	}

	@Override
	public int getCurrentHealth() {
		return hp;
	}

	@Override
	public int getDefence() {
		return defence;
	}

	@Override
	public int getMaxHealth() {
		return Player.MAXHEALTH;
	}

	@Override
	public String getShortName() {
		return getLongName();
	}

	@Override
	public String getLongName() {
		return name;
	}

	@Override
	public int getSize() {
		return 5;
	}

	@Override
	public String getGraphicTextSymbol() {
			return "" + SYMBOL;
	}

	@Override
	public String getEmoji() {
		return hp > 0 ? "👸" : "⚱️"; // 🤴  ⚰️
	}

	@Override
	public int handleDamage(int amount) {
		amount = Math.max(0, amount - defence);
		amount = Math.min(hp + 1, amount);
		hp -= amount;
		return amount;
	}

	@Override
	public void keyPressed(IGameView game, KeyCode key) {
		System.err.println("Player moves");
		switch (key) {
		case LEFT:
			tryToMove(game, GridDirection.WEST);
			break;
		case RIGHT:
			tryToMove(game, GridDirection.EAST);
			break;
		case UP:
			tryToMove(game, GridDirection.NORTH);
			break;
		case DOWN:
			tryToMove(game, GridDirection.SOUTH);
			break;
		case P:
			pickUp(game);
			break;
		case D:
			drop(game);
			break;
		default:
			System.err.printf("Illegal key pressed. Key: %s", key);
		}
		showStatus(game);
	}

	private void tryToMove(IGameView game, GridDirection dir) {
		if (game.canGo(dir)) {
			game.displayDebug("Moving.");
			game.move(dir);
		}
		else {
			if(game.attack(dir))
				game.displayDebug("Victory!");
			else
				game.displayDebug("Ouch! Can't go there.");
		}
	}

	private void showStatus(IGameView game) {

        if (!inventory.isEmpty()) {

            String outputString = " ";

            for (IItem itemInInventory : inventory){

                String temp = itemInInventory.getLongName();
                outputString.concat(temp);

            }

            //System.out.println("This is the output " + outputString);

            game.displayMessage("Player has " + this.hp + " hp left holding items(s) " + outputString);

        }
        else{
            game.displayMessage("Player has " + this.hp + " hp left");
        }


	}

	private void pickUp(IGameView game) {
		List<IItem> items = game.getLocalNonActorItems();
		if(!items.isEmpty()) {
			Collections.sort(items,new IItemComparator());
			Optional<IItem> found = game.pickUp(items.get(items.size()-1));
			if(found.isPresent())
				System.out.println(items.get(items.size()-1));
				game.displayMessage("Picked up "+found.get().getLongName());
				inventory.add(items.get(items.size()-1));
		}
	}

	private void drop(IGameView game) {
		List<IItem> items = game.getLocalNonActorItems();
		if(!inventory.isEmpty()){
			if (items.isEmpty()){
				game.drop(inventory.get(0));
				inventory.remove(0);

			}

		}

	}

	@Override
	public void doTurn(IGameView game) {
	}

	@Override
	public boolean isDestroyed() {
		return false; //Even when dead, the player should remain on the map
	}

	@Override
	public boolean hasItem(IItem item) {
		for (IItem inventoryItem : inventory){
			if (inventoryItem == item){
				return true;
			}
		}
		return false;
	}

	@Override
	public char getSymbol() {
		return SYMBOL;
	}
}
