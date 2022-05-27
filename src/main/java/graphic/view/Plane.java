package graphic.view;

import graphic.controller.GameController;
import javafx.animation.FadeTransition;
import javafx.animation.Transition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.BitSet;
import java.util.Vector;

class PlaneTransition extends Transition {
    private Plane plane;
    private Vector<ImagePattern> imagePatterns = new Vector<>();

    public PlaneTransition(Plane plane) {
        this.plane = plane;
        setCycleCount(-1);
        setCycleDuration(Duration.millis(1200));
        loadImages();
    }

    private void loadImages() {
        for (int i = 1; i <= 12; i++) {
            Image image = new Image(getClass().getResource("/images/Plane/" + i + ".png").toExternalForm());
            imagePatterns.add(new ImagePattern(image));
        }
    }

    @Override
    protected void interpolate(double v) {
        int number = (int) Math.floor(v * 24) % 24;
        if (number > 11)
            number = 23 - number;
        plane.setFill(imagePatterns.get(number));
        plane.doWorks();
        checkCollisions();
    }

    private void loseHealth() {
        GameController.updateHealth(plane.getHealthLabel());
        plane.setFading(true);
        FadeTransition fd = new FadeTransition(Duration.millis(400));
        fd.setFromValue(0.6);
        fd.setToValue(0);
        fd.setCycleCount(3);
        fd.setNode(plane);
        fd.play();
        fd.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                plane.setOpacity(1);
                plane.setFading(false);
            }
        });
    }

    private void checkCollisions() {
        if (plane.isFading())
            return;
        if (plane.hasCollision(Boss.getInstance())) {
            if (GameController.planeHitBoss().equals("dead"))
                GameController.endGame(false);
            loseHealth();
        }
        for (BossBomb bossBomb : BossBomb.getBossBombs()) {
            if (plane.hasCollision(bossBomb)) {
                if (GameController.planeHitBoss().equals("dead"))
                    GameController.endGame(false);
                bossBomb.remove();
                loseHealth();
                break;
            }
        }
        for (MiniBoss miniBoss : MiniBoss.getMiniBosses()) {
            if (plane.hasCollision(miniBoss)) {
                if (GameController.planeHitBoss().equals("dead"))
                    GameController.endGame(false);
                miniBoss.remove();
                loseHealth();
                break;
            }
        }
    }

}

public class Plane extends Rectangle {
    private static Plane plane = null;

    private BooleanProperty wKey = new SimpleBooleanProperty(false);
    private BooleanProperty sKey = new SimpleBooleanProperty(false);
    private BooleanProperty aKey = new SimpleBooleanProperty(false);
    private BooleanProperty dKey = new SimpleBooleanProperty(false);
    private BooleanProperty spaceKey = new SimpleBooleanProperty(false);

    private int fireTurn = 0;
    PlaneTransition planeTransition;
    private boolean isFading = false;
    private Label healthLabel;

    public static Plane getInstance() {
        if (plane == null)
            plane = new Plane();
        return plane;
    }

    public static Plane getInstance(Label label) {
        if (plane == null)
            plane = new Plane();
        plane.healthLabel = label;
        return plane;
    }

    private Plane() {
        super(60, 20, 100, 100);
        planeTransition = new PlaneTransition(this);
        planeTransition.play();
        handleKeys();

    }

    public static void reset() {
        plane.planeTransition.stop();
        plane = null;
    }

    private void handleKeys() {
        this.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getText()) {
                    case "s":
                        sKey.set(true);
                        break;
                    case "w":
                        wKey.set(true);
                        break;
                    case "a":
                        aKey.set(true);
                        break;
                    case "d":
                        dKey.set(true);
                        break;
                    case " ":
                        spaceKey.set(true);
                        break;
                }
            }
        });
        this.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getText()) {
                    case "s":
                        sKey.set(false);
                        break;
                    case "w":
                        wKey.set(false);
                        break;
                    case "a":
                        aKey.set(false);
                        break;
                    case "d":
                        dKey.set(false);
                        break;
                    case " ":
                        spaceKey.set(false);
                        break;
                }
            }
        });


//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while(true){
//                    try {
//                        Thread.sleep(50);
//                    } catch (InterruptedException a) {
//                        a.printStackTrace();
//                    }
//                    if (sKey.get())
//                        moveDown();
//                    if (dKey.get())
//                        moveRight();
//                    if (aKey.get())
//                        moveLeft();
//                    if (wKey.get())
//                        moveUp();
//                    if(spaceKey.get())
//                        Plane.this.fire();
//                }
//            }
//        });
//        thread.start();
    }

    public boolean isFading() {
        return isFading;
    }

    public void setFading(boolean fading) {
        isFading = fading;
    }

    private void moveRight() {
        if (getX() + 120 < 1400)
            setX(getX() + 10);
    }

    private void moveLeft() {
        if (getX() > 10)
            setX(getX() - 10);
    }

    private void moveUp() {
        if (getY() > 10)
            setY(getY() - 10);
    }

    private void moveDown() {
        if (getY() + 130 < 700)
            setY(getY() + 10);
    }

    public boolean hasCollision(Rectangle rectangle) {
        return this.getLayoutBounds().intersects(rectangle.getLayoutBounds());
    }

    private void fire() {
        Bullet bullet = new Bullet(this.getX() + 50, this.getY() + 50);
        ((AnchorPane) this.getParent()).getChildren().add(bullet);
    }


    public void doWorks() {
        fireTurn++;
        if (sKey.get())
            moveDown();
        if (dKey.get())
            moveRight();
        if (aKey.get())
            moveLeft();
        if (wKey.get())
            moveUp();
        if (spaceKey.get() & fireTurn % 5 == 0)
            fire();
    }

    public Label getHealthLabel() {
        return healthLabel;
    }
}