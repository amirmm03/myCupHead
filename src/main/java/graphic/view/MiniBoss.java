package graphic.view;

import graphic.controller.GameController;
import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;

class MiniBossTransition extends Transition {
    private static ArrayList<ImagePattern> imagePatterns = new ArrayList<>();
    private MiniBoss miniBoss;

    public MiniBossTransition(MiniBoss miniBoss) {
        this.miniBoss = miniBoss;
        if (imagePatterns.isEmpty())
            loadImages();
        this.setCycleCount(-1);
        this.setCycleDuration(Duration.millis(500));
    }

    private void loadImages() {
        for (int i = 1; i <= 4; i++) {
            Image image = new Image(getClass().getResource("/images/Miniboss/" + i + ".png").toExternalForm());
            imagePatterns.add(new ImagePattern(image));
        }
    }

    @Override
    protected void interpolate(double v) {
        int number = (int) Math.floor(v * 4) % 4;
        miniBoss.setFill(imagePatterns.get(number));
        miniBoss.move();
        checkCollisions();
    }

    private void checkCollisions() {
        for (Bullet bullet : Bullet.getBullets()) {
            if (miniBoss.hasCollision(bullet)) {
                GameController.miniBossHitBullet(miniBoss);
                bullet.remove();
                break;
            }
        }
    }
}

public class MiniBoss extends Rectangle {
    private int health = 2;
    private static ArrayList<MiniBoss> miniBosses = new ArrayList<>();
    private MiniBossTransition miniBossTransition;

    public static ArrayList<MiniBoss> getMiniBosses() {
        return miniBosses;
    }

    MiniBoss(double y) {
        super(1500, y, 155, 110);
        miniBosses.add(this);
        miniBossTransition = new MiniBossTransition(this);
        miniBossTransition.play();
    }

    public static void reset() {
        miniBosses = new ArrayList<>();
    }

    public void move() {
        if (getX() <= -150)
            this.remove();
        setX(getX() - 5);
    }

    public void remove() {
        miniBosses.remove(this);
        miniBossTransition.stop();
        ((AnchorPane) this.getParent()).getChildren().remove(this);
    }

    public boolean hasCollision(Rectangle rectangle) {
        return this.getLayoutBounds().intersects(rectangle.getLayoutBounds());
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
