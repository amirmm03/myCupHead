package graphic.model;

public class Boss {
    private static Boss boss;
    private int health;
    private int totalHealth;
    private int damage;

    public static Boss getInstance() {
        if (boss == null)
            boss = new Boss();
        return boss;
    }

    private Boss() {
        totalHealth = 80;
        health = 80;
        damage = 1;
    }

    public static void reset() {
        boss = null;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getTotalHealth() {
        return totalHealth;
    }

    public int getDamage() {
        return damage;
    }
}
