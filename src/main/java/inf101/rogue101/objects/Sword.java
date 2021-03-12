package inf101.rogue101.objects;

public class Sword implements IItem{

    public static final char SYMBOL = '/';
    public static final int attack = 15;
    public static final int defence = 3;
    public static final int damage = 15;


    private int hp = getMaxHealth();


    @Override
    public int getCurrentHealth() {
        return hp;
    }

    @Override
    public int getDefence() {
        return 0;
    }


    public int getDamage(){
        return 1;
    }

    @Override
    public int getMaxHealth() {
        return 15;
    }

    @Override
    public String getLongName() {
        return "Long Sword";
    }

    @Override
    public String getShortName() {
        return "Sword";
    }

    @Override
    public int getSize() {
        return 4;
    }

    @Override
    public int handleDamage(int amount) {
        int damage = Math.min(amount, hp);
        hp -= damage;
        return damage;
    }

    @Override
    public char getSymbol() {
        return SYMBOL;
    }
}
