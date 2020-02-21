package inf101.v20.rogue101.game;

import inf101.v20.rogue101.objects.Amulet;
import inf101.v20.rogue101.objects.Carrot;
import inf101.v20.rogue101.objects.Dust;
import inf101.v20.rogue101.objects.IItem;
import inf101.v20.rogue101.objects.Player;
import inf101.v20.rogue101.objects.Portal;
import inf101.v20.rogue101.objects.Rabbit;
import inf101.v20.rogue101.objects.Spider;
import inf101.v20.rogue101.objects.Wall;

public class ItemFactory {

	public static IItem createItem(char symbol) {
		switch(symbol) {
		case '#':
			return new Wall();
		case 'R':
			return new Rabbit();
		case 'C':
			return new Carrot();
		case '@':
			return new Player();
		case 'S':
			return new Spider();
		case 'A':
			return Amulet.getInstance();
		case '*':
			return new Portal();
		case ' ':
			return null;
		default : 	
			throw new IllegalArgumentException("createItem: Don't know how to create a '" + symbol + "'");
		}
	}
}
