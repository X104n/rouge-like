package inf101.rogue101.objects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import inf101.gfx.textmode.Printer;
import inf101.grid.GridDirection;
import inf101.rogue101.game.IGameView;
import javafx.scene.paint.Color;

/**
 * Kaniner i spillet Rogue 101.
 * Kaniner hopper rundt i labyrinten på jakt etter gulrøtter.
 * De har god luktesans og kan lukte gulrøtter på lang avstand.
 * <p>
 * De forbrenner energi raskt og må være effektiv
 *
 * @author Knut Anders Stokke
 * @author Martin Vatshelle
 */
public class Rabbit implements IActor {
    /**
     * char representation of this type
     */
    public static final char SYMBOL = 'R';
    private int hp = getMaxHealth();

    @Override
    public void doTurn(IGameView game) {
        boolean isHungry = getMaxHealth() - getCurrentHealth() > 2;
        if (isHungry) {
            boolean gotFood = eatIfPossible(game);
            if (gotFood) {
                //Rabbit is full and prefers to rest
                return;
            }
        }


        GridDirection dir = selectMove(game);

        performMove(game, dir);
    }

    /**
     * This method selects which move the Rabbit want to make.
     */
    private GridDirection selectMove(IGameView game) {
        List<GridDirection> possibleMoves = game.getPossibleMoves();
        List<GridDirection> CarrotMoves = new ArrayList<>();
        List<IItem> NearbyItems = game.getNearbyItems(5);
        boolean carrotBool = false;

        // If there is a carrot right next to the rabbit, this code executes
        for (GridDirection direction : GridDirection.EIGHT_DIRECTIONS) {
            if (game.containsItem(direction, Carrot.class)) {
                CarrotMoves.add(direction);
                carrotBool = true;
            }
        }

        // if there isn't a carrot next to the rabbit, this code executes to look for any carrots in a distance of 5
        if (!carrotBool) {
            for (IItem NearbyCarrots : NearbyItems) {

                if (NearbyCarrots instanceof Carrot) {

                    GridDirection directionToCarrot = game.getDirectionTo(NearbyCarrots);

                    // We just go for the first and best carrot here
                    // Here i also implemented that the rabbit doesn't jump in the wall
                    if (game.canGo(directionToCarrot)){
                        return directionToCarrot;
                    }


                }

            }


        }

        // if the first for loop executes we will pick a random carrot that is beside the rabbit if there is multiple
        if (carrotBool) {
            Collections.shuffle(CarrotMoves);

            return CarrotMoves.get(0);
        }

        // if the rabbit cannot go anywhere he just stay in the place (very unlikely)
        if (possibleMoves.isEmpty()) {
            return GridDirection.CENTER;
        }

        // Here I also implements that the rabbit doesn't jump in the wall
        List<GridDirection> dirReturn = new ArrayList<>();
        for (GridDirection direction : possibleMoves){
            if (game.canGo(direction)){
                dirReturn.add(direction);
            }
        }
        Collections.shuffle(dirReturn);

        return dirReturn.get(0);
    }

    /**
     * Burns one round of energy
     * Rabbits only burn energy if they move, not when laying still.
     */
    private void burnEnergy() {
        hp--;
    }

    /**
     * Performs a move
     * Every time a Rabbit moves it burns energy
     * GridDirection.CENTER means the Rabbit is resting and not burning energy
     *
     * @param game
     */
    private void performMove(IGameView game, GridDirection dir) {
        if (!dir.equals(GridDirection.CENTER)) {
            burnEnergy();
            boolean moved = game.move(dir);
            if (!moved) {
                game.displayMessage("Rabbit wasted energy on move");
            }
        }
    }

    /**
     * Eats carrot if any exist at current location
     *
     * @param game The game the object exists in
     * @return true if it spend the turn eating
     */

    private boolean eatIfPossible(IGameView game) {
        for (IItem item : game.getLocalNonActorItems()) {
            if (item instanceof Carrot) {
                System.out.println("found carrot!");
                int eaten = item.handleDamage(Math.min(5, getMaxHealth() - getCurrentHealth()));
                if (eaten > 0) {
                    System.out.println("ate carrot worth " + eaten + "!");
                    hp += eaten;
                    //game.displayMessage("You hear a faint crunching (" + getLongName() + " eats " + item.getArticle() + " " + item.getLongName() + ")");
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int getAttack() {
        return 5;
    }

    @Override
    public int getCurrentHealth() {
        return hp;
    }

    @Override
    public int getDamage() {
        return 8;
    }

    @Override
    public int getDefence() {
        return 2;
    }

    @Override
    public int getMaxHealth() {
        return 10;
    }

    @Override
    public String getShortName() {
        return getLongName();
    }

    public String getLongName() {
        return "rabbit";
    }

    @Override
    public int getSize() {
        return 4;
    }

    @Override
    public String getGraphicTextSymbol() {
        return hp > 0 ? "" + SYMBOL : "¤";
    }

    @Override
    public String getEmoji() {
        return hp > 0 ? Printer.coloured("🐰", Color.LIGHTPINK) : "💀"; // 🐇
    }

    @Override
    public int handleDamage(int amount) {
        amount -= Math.min(amount, getDefence());
        int damage = Math.min(amount, hp);
        hp -= damage;
        return damage;
    }

    @Override
    public char getSymbol() {
        return SYMBOL;
    }

}
