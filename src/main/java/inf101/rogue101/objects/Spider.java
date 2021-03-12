package inf101.rogue101.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import inf101.gfx.gfxmode.DrawHelper;
import inf101.gfx.gfxmode.IBrush;
import inf101.grid.GridDirection;
import inf101.rogue101.game.EmojiFactory;
import inf101.rogue101.game.IGameView;

/**
 * En edderkopp i spillet Rogue 101. 
 * 
 * Edderkopper angriper spilleren hvis de kan. 
 * De er vanskelige å drepe. 
 * 
 * @author Martin Vatshelle 
 *
 */
public class Spider implements IActor {
	/**
	 * char representation of this type 
	 */
	public static final char SYMBOL = 'S';
	public static Spider first = null;
	final static int MAXHEALTH = 10;
	private int hp;
	private int defence;
	private int attack;
	private int damage;
	private GridDirection lastDir;

	public Spider() {
		attack = 10; //they sneak in so almost impossible to resist attack
		defence = 2; //they are easy to kill
		damage = 10;
		hp = Spider.MAXHEALTH;
		lastDir = GridDirection.NORTHEAST;
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
		return Spider.MAXHEALTH;
	}

	@Override
	public String getShortName() {
		return getLongName();
	}

	@Override
	public String getLongName() {
		return "Spider";
	}

	@Override
	public int getSize() {
		return 2;
	}
	
	@Override
	public String getEmoji() {
		return "🕷️";
	}

	@Override
	public int handleDamage(int amount) {
		amount -= Math.min(amount, getDefence());
		int damage = Math.min(amount, hp);
		hp -= amount;
		return damage;
	}

	@Override
	public boolean draw(IBrush painter, double w, double h) {
		if (!EmojiFactory.USE_EMOJI) {
			DrawHelper.drawSpider(painter, h, w, hp / getMaxHealth());
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void doTurn(IGameView game) {

		// Looking for a player in a distance of one if it finds one it attacks it
		for(GridDirection dir : GridDirection.EIGHT_DIRECTIONS) {
			if(dir.equals(GridDirection.CENTER))
				continue;
			if(game.containsItem(dir, IPlayer.class)) {
				game.attack(dir);
				return;
			}
		}

		List<IActor> temp = new ArrayList<>();


		// if it doesn't have anywhere it wants to go it continues in the same direction
		List<GridDirection> moves = game.getPossibleMoves();
		if (game.canGo(lastDir)) {
			game.move(lastDir);
		} else {
			moves.remove(GridDirection.CENTER);
			if (!moves.isEmpty()) {
				Random r = new Random();
				lastDir = moves.get(r.nextInt(moves.size()));
				game.move(lastDir);
			}
		}
	}

	@Override
	public int getAttack() {
		return this.attack;
	}

	@Override
	public int getDamage() {
		return this.damage;
	}
	
	@Override
	public char getSymbol() {
		return SYMBOL;
	}
}
