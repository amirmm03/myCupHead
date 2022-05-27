package graphic.view;

import javafx.animation.Transition;
import javafx.scene.control.TableRow;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;

class BulletTransition extends Transition {
    private Bullet bullet;

    public BulletTransition(Bullet bullet) {
        this.bullet = bullet;
        setCycleCount(-1);
        setCycleDuration(Duration.millis(1000));
    }

    @Override
    protected void interpolate(double v) {
        bullet.move();
    }
}

public class Bullet extends Rectangle {
    private BulletTransition bulletTransition;
    private static ArrayList<Bullet> bullets = new ArrayList<>();
    private static ImagePattern imagePattern = new ImagePattern(new Image(Bullet.class.getResource("/images/bullet.png").toExternalForm()));

    public Bullet(double xStart, double yStart) {
        super(xStart, yStart, 70, 15);
        bullets.add(this);
        bulletTransition = new BulletTransition(this);
        bulletTransition.play();
        this.setFill(imagePattern);
    }

    public static ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public static void reset() {
        bullets = new ArrayList<>();
    }

    public void move() {
        if (getX() > 1500)
            this.remove();
        setX(getX() + 8);
    }

    public void remove() {
        bullets.remove(this);
        bulletTransition.stop();
        ((AnchorPane) this.getParent()).getChildren().remove(this);
    }
}
