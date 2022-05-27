package graphic.view;

import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;

class BossBombTransition extends Transition {
    private static ImagePattern imagePattern;
    private BossBomb bossBomb;

    public BossBombTransition(BossBomb bossBomb) {
        this.bossBomb = bossBomb;
        if (imagePattern == null)
            loadImages();
        bossBomb.setFill(imagePattern);
        this.setCycleCount(-1);
        this.setCycleDuration(Duration.millis(500));
    }

    private void loadImages() {

        Image image = new Image(getClass().getResource("/images/egg.png").toExternalForm());
        imagePattern = (new ImagePattern(image));
    }

    @Override
    protected void interpolate(double v) {
        bossBomb.move();
    }
}

public class BossBomb extends Rectangle {
    private static ArrayList<BossBomb> bossBombs = new ArrayList<>();
    private BossBombTransition bossBombTransition;

    public static ArrayList<BossBomb> getBossBombs() {
        return bossBombs;
    }

    BossBomb() {
        super(Boss.getInstance().getX() - 100, Boss.getInstance().getY() + 100, 130, 110);
        bossBombs.add(this);
        bossBombTransition = new BossBombTransition(this);
        bossBombTransition.play();
    }

    public static void reset() {
        bossBombs = new ArrayList<>();
    }

    public void move() {
        if (getX() <= -150)
            this.remove();
        setX(getX() - 5);
    }

    public void remove() {
        bossBombs.remove(this);
        bossBombTransition.stop();
        ((AnchorPane) this.getParent()).getChildren().remove(this);
    }

}
