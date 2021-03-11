package inf101.rogue101.objects;

public class FaceMask implements IItem{

    public static final char SYMBOL = 'M';

    private int hp = getMaxHealth();

    @Override
    public int getCurrentHealth() {
        return hp;
    }

    @Override
    public int getDefence() {
        return 0;
    }

    @Override
    public int getMaxHealth() {
        return 1;
    }

    @Override
    public String getLongName() {
        return "Face Mask";
    }

    @Override
    public String getShortName() {
        return "Mask";
    }

    @Override
    public int getSize() {
        return 1;
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
