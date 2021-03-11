package inf101.rogue101.objects;

import java.util.*;

import inf101.grid.GridDirection;
import inf101.grid.Location;
import inf101.rogue101.game.IGameView;
import javafx.scene.input.KeyCode;

/**
 * En spiller i Rogue 101
 * <p>
 * Spilleren navigerer labyrinten og slåss med monster. For å komme seg ut
 * og vinne spille må spilleren gå gjennom portalen; for å åpne portalen
 * må den finne amuletten og bære den med seg.
 * <p>
 * På veien kan den plukke opp gull og slåss med monster
 *
 * @author Anna Eilertsen - anna.eilertsen@uib.no
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
        attack = 5;
        defence = 2;
        damage = 5;
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
        return 10;
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
            case A:
                tryToMove(game, GridDirection.WEST);
                break;
            case D:
                tryToMove(game, GridDirection.EAST);
                break;
            case W:
                tryToMove(game, GridDirection.NORTH);
                break;
            case S:
                tryToMove(game, GridDirection.SOUTH);
                break;
            case E:
                pickUp(game);
                break;
            case Q:
                drop(game);
                break;
            case ESCAPE:
                System.exit(0);
            default:
                System.err.printf("Illegal key pressed. Key: %s", key);
        }
        showStatus(game);
    }

    private void tryToMove(IGameView game, GridDirection dir) {

        if (game.canGo(dir)) {
            game.displayDebug("Moving.");
            game.move(dir);
        } else {

            // Makes so that the player doesn't see the wall as an enemy and displays Victory!
            if (game.getPossibleMoves().contains(game.attack(dir))) {
                game.displayDebug("Combat victory!");
            }
            else
                game.displayDebug("Ouch! Can't go there.");
        }
    }

    private void showStatus(IGameView game) {

        if (game.containsItem(GridDirection.CENTER, Dust.class)){
            boolean takeDamage = true;
            for (IItem ting : inventory) {
                if (ting instanceof FaceMask){
                    takeDamage = false;
                }
            }
            if (takeDamage){
                hp -= 1;
                game.displayMessage("You choked on some fucking dust you looser. Man actually lost a HP! LMFAO!");
            }
        }

        //As long as there is an item in the inventory it executes this code, if not it just shows the "HP" of the player
        if (!inventory.isEmpty()) {

            // In this code i make an empty outputString, then i add the longName of every item on that string (concat). Then add that string after the players hp.

            String outputString = "";

            boolean randomBool = false;

            for (IItem itemInInventory : inventory) {

                if (randomBool) {
                    outputString = outputString.concat(",");
                }

                String temp = itemInInventory.getLongName();
                outputString = outputString.concat(" " + temp);

                randomBool = true;

            }

            //System.out.println("This is the output " + outputString);

            game.displayMessage("Player has " + this.hp + " hp left holding items(s) " + outputString);

        } else {
            game.displayMessage("Player has " + this.hp + " hp left");
        }


    }

    private void pickUp(IGameView game) {
        List<IItem> items = game.getLocalNonActorItems();
        if (!items.isEmpty()) {
            Collections.sort(items, new IItemComparator());
            Optional<IItem> found = game.pickUp(items.get(items.size() - 1));
            if (found.isPresent())
                System.out.println(items.get(items.size() - 1));
            game.displayMessage("Picked up " + found.get().getLongName());

            // The only code i added here was to add the item that is picked up to an inventory.
            inventory.add(items.get(items.size() - 1));
        }
    }

    private void drop(IGameView game) {

        // Here i check if the inventory contains something, if it does the player drops the item and then erases the item from the inventory
        if (!inventory.isEmpty()) {
            game.drop(inventory.get(0));
            inventory.remove(0);

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
        for (IItem inventoryItem : inventory) {
            if (inventoryItem == item) {
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
