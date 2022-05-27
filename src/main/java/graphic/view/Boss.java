package graphic.view;

import graphic.controller.GameController;
import javafx.animation.Transition;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.Random;
import java.util.Vector;

class BossTransition extends Transition {
    private Boss boss;
    private Vector<ImagePattern> imagePatterns = new Vector<>();

    public BossTransition(Boss boss) {
        this.boss = boss;
        setCycleCount(-1);
        setCycleDuration(Duration.millis(700 * 6));
        loadImages();
    }

    private void loadImages() {
        for (int i = 1; i < 7; i++) {
            Image image = new Image(getClass().getResource("/images/BossFly/" + i + ".png").toExternalForm());
            imagePatterns.add(new ImagePattern(image));
        }
    }

    @Override
    protected void interpolate(double v) {
        int number = (int) Math.floor(v * 6 * 6) % 6;
        boss.setFill(imagePatterns.get(number));
        boss.move(v);
        checkCollision();
    }

    private void checkCollision() {
        for (Bullet bullet : Bullet.getBullets()) {
            if (boss.hasCollision(bullet)) {
                String message = GameController.bossHitBullet();

                GameController.updateBossHealth(boss.getProgressBar(), boss.getProgressLabel());
                bullet.remove();
                if (message.equals("halfDone")) {
                    boss.change();
                }
                break;
            }
        }
    }

}

public class Boss extends Rectangle {
    private static Boss boss;
    private Transition bossTransition;
    private ProgressBar progressBar;
    private Label progressLabel;
    private Random random = new Random();
    private int next;

    public static Boss getInstance() {
        if (boss == null)
            boss = new Boss();
        return boss;
    }

    public static Boss getInstance(ProgressBar progressBar, Label label) {
        if (boss == null)
            boss = new Boss();
        boss.progressBar = progressBar;
        boss.progressLabel = label;
        return boss;
    }

    private Boss() {
        super(1000, 10, 305, 255);
        bossTransition = new BossTransition(this);
        bossTransition.play();
    }

    public static void reset() {
        boss.bossTransition.stop();
        boss.progressLabel = null;
        boss.progressBar = null;
        boss = null;
    }

    public void move(double v) {
        //10 to 425
        if (v <= 0.5)
            setY(10 + 415 * (2 * v));
        else
            setY(425 - 415 * (2 * (v - 0.5)));
    }

    public void secondMove(double v) {

        if (v >= 0.999)
            next = random.nextInt(0, 3);

        if (v <= 0.5) {
            setY(10 + 415 * (2 * v));
        } else {
            setY(425 - 415 * (2 * (v - 0.5)));
        }
        if (next == 0) {
            if (v <= 0.5) {
                setX(getX() - 3);
            } else {
                setX(getX() + 3);
            }
        } else if (next == 1) {
            if (v <= 0.25)
                setX(getX() - 3);
            else if (v <= 0.5)
                setX(getX() + 3);
            else if (v <= 0.75)
                setX(getX() - 3);
            else
                setX(getX() + 3);
        } else {
            if (v <= 0.25)
                setX(getX() - 3);
            else if (v > 0.5 & v <= 0.75)
                setX(getX() + 3);
        }


    }

    public boolean hasCollision(Rectangle rectangle) {
        return this.getLayoutBounds().intersects(rectangle.getLayoutBounds());
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public Label getProgressLabel() {
        return progressLabel;
    }

    public void change() {
        bossTransition.stop();
        setWidth(190);
        setHeight(230);
        bossTransition = new SecondBossTransition(this);
        bossTransition.play();
    }


}

class SecondBossTransition extends Transition {
    private Boss boss;
    private Vector<ImagePattern> imagePatterns = new Vector<>();

    public SecondBossTransition(Boss boss) {
        this.boss = boss;
        setCycleCount(-1);
        setCycleDuration(Duration.millis(4000));
        loadImages();
    }

    private void loadImages() {
        for (int i = 1; i <= 16; i++) {
            Image image = new Image(getClass().getResource("/images/Boss2/egghead_idle_" + String.format("%04d", i) + ".png").toExternalForm());
            imagePatterns.add(new ImagePattern(image));
        }
    }

    @Override
    protected void interpolate(double v) {
        int number = (int) Math.floor(v * 16) % 16;
        boss.setFill(imagePatterns.get(number));
        boss.secondMove(v);
        checkCollision();
    }

    private void checkCollision() {
        for (Bullet bullet : Bullet.getBullets()) {
            if (boss.hasCollision(bullet)) {
                String message = GameController.bossHitBullet();
                if (message.equals("dead")) {
                    GameController.endGame(true);
                }
                GameController.updateBossHealth(boss.getProgressBar(), boss.getProgressLabel());
                bullet.remove();
                break;
            }
        }
    }
}
