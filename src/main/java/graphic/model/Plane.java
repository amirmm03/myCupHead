package graphic.model;

public class Plane {
    private static Plane plane;
    private int health;
    private int totalHealth;
    private int damage;

    public static Plane getInstance() {
        if (plane == null)
            plane = new Plane();
        return plane;
    }

    private Plane() {
        health = 4;
        totalHealth = 4;
        damage = 2;
    }

    public static void reset() {
        plane = null;
    }

    public int getHealth() {
        return health;
    }

    public int getTotalHealth() {
        return totalHealth;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getDamage() {
        return damage;
    }
}
