package inf101.rogue101.game;

import inf101.rogue101.objects.*;

public class ItemFactory {

    public static IItem createItem(char symbol) {
        switch (symbol) {
            case Wall.SYMBOL:
                return new Wall();
            case Rabbit.SYMBOL:
                return new Rabbit();
            case Carrot.SYMBOL:
                return new Carrot();
            case Sword.SYMBOL:
                return new Sword();
            case Player.SYMBOL:
                return new Player();
            case Spider.SYMBOL:
                return new Spider();
            case Amulet.SYMBOL:
                return Amulet.getInstance();
            case Gold.SYMBOL:
                return new Gold();
            case Dust.SYMBOL:
                return new Dust();
            case FaceMask.SYMBOL:
                return new FaceMask();
            case Portal.SYMBOL:
                return new Portal();
            case ' ':
                return null;
            default:
                throw new IllegalArgumentException("createItem: Don't know how to create a '" + symbol + "'");
        }
    }
}
